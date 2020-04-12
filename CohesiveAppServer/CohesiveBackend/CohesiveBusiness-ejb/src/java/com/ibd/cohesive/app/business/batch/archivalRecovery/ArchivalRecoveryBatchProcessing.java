/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.archivalRecovery;

import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.db.util.IBDFileUtil;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Stateless
public class ArchivalRecoveryBatchProcessing implements IArchivalRecoveryBatchProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public ArchivalRecoveryBatchProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
   public void processing (String businessDate,String batchName,int no_of_threads)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       BatchUtil batchUtil=null;
       boolean l_session_created_now=false;
       ITransactionControlService tc=null;
       try{
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IDBTransactionService dbts=inject.getDBTransactionService();
        batchUtil=inject.getBatchUtil(session);
        BusinessService bs=inject.getBusinessService(session);
        tc=inject.getTransactionControlService();
        String DateAndTime=i_db_properties.getProperty("RECOVERY_TIME");
        dbg("inside archivalRecovery batch processing");
        dbg("batchName"+batchName);
        dbg("no_of_threads"+no_of_threads);
        dbg("businessDate"+businessDate);
        boolean recordExistence=true;
        DBRecord batchEODStatusRecord=null;
        String l_eodStatus=new String();  
        String startTime=bs.getCurrentDateTime();
        dbg("startTime"+startTime);
           
           try{

           String l_eodPkey[]={batchName,businessDate};
           batchEODStatusRecord=readBuffer.readRecord("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", "APP_BATCH_STATUS",l_eodPkey ,session, dbSession,true);
           l_eodStatus=batchEODStatusRecord.getRecord().get(4).trim();
        }catch(DBValidationException ex){

            if(ex.toString().contains("DB_VAL_011")){

                recordExistence=false;
            }
        }
           
        dbg("l_eodStatus"+l_eodStatus);   
           
        if(recordExistence==false||l_eodStatus.equals("F")){
         
            if(l_eodStatus.equals("F")){
            
                Map<String,String>column_to_Update=new HashMap();
                column_to_Update.put("5", "W");
                column_to_Update.put("3", startTime);
                String[] l_pkey={batchName,businessDate};
                dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", "APP_BATCH_STATUS", l_pkey, column_to_Update,session); 
                batchUtil.moveAppBatchRecordToHistory(batchName,businessDate,batchEODStatusRecord, session, dbSession, inject);
            }else{ 
             
               dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", 239,batchName, businessDate,startTime," ","W"," "," "," ");   
            }   
            tc.commit(session, dbSession);
            
            fileIdentification(businessDate,DateAndTime);
            
            
            dbg("no_of_threads"+no_of_threads);
            if(no_of_threads==0){

                sequentialProcessing(businessDate,batchName);
            }else{

//                parallelProcessing(businessDate,batchName,no_of_threads);
            }
            
            
            
        }    
        dbg("end of defragmentProfileArch batch processing");
         batchUtil.appBatchSucessHandler(businessDate, batchName, inject, session, dbSession);
        }catch(DBValidationException ex){
          batchUtil.appBatchErrorHandler(businessDate, ex, batchName, inject, session, dbSession);
        }catch(BSValidationException ex){
          batchUtil.appBatchErrorHandler(businessDate, ex, batchName, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.appBatchErrorHandler(businessDate, ex, batchName, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.appBatchErrorHandler(businessDate, ex, batchName, inject, session, dbSession);
        }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
       
   }
   
   
    private void sequentialProcessing(String businessDate,String batchName)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      BatchUtil batchUtil=null; 
      ITransactionControlService tc=null;
      try{
          
           dbg("inside defragment arch batch--->sequentialProcessing");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBTransactionService dbts=inject.getDBTransactionService();
           IArchivalRecoveryProcessing fileArchivalRecovery=inject.getArchivalRecoveryProcessing();
           batchUtil=inject.getBatchUtil(session);
           tc=inject.getTransactionControlService();
           
           Map<String,DBRecord>archivalRecoveryMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", "ARCHIVAL_RECOVERY_BATCH_STATUS", session, dbSession);
           List<DBRecord>fileList=archivalRecoveryMap.values().stream().filter(rec->rec.getRecord().get(1).trim().equals("F")||rec.getRecord().get(1).trim().equals("W")).collect(Collectors.toList());
           
           for(int i=0;i<fileList.size();i++){
              DBRecord defragRec=fileList.get(i);
              String fileName=defragRec.getRecord().get(0).trim();
              String l_status=defragRec.getRecord().get(1).trim();
               
               dbg("inside fileList iteration");
               dbg("l_status"+l_status);
               
                if(l_status.equals("F")){
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update.put("2", "W");
                    String[]l_pkey={fileName};
                    dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", "ARCHIVAL_RECOVERY_BATCH_STATUS", l_pkey, column_to_Update,session); 
                    batchUtil.moveArchivalRecoveryRecordToHistory(fileName,businessDate,defragRec, session, dbSession, inject);
                    tc.commit(session, dbSession);
                }
                
                fileArchivalRecovery.processing(fileName, businessDate, session);
           }
          dbg("end of profile batch--->sequentialProcessing");  
         batchUtil.appBatchSucessHandler(businessDate, batchName, inject, session, dbSession);
        }catch(DBValidationException ex){
          batchUtil.appBatchErrorHandler(businessDate, ex, batchName, inject, session, dbSession);
        }catch(BSValidationException ex){
          batchUtil.appBatchErrorHandler(businessDate, ex, batchName, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.appBatchErrorHandler(businessDate, ex, batchName, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.appBatchErrorHandler(businessDate, ex, batchName, inject, session, dbSession);
        }
         
     }
   
    
   
     public void processing(String businessDate,String batchName,int no_of_threads,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(businessDate,batchName,no_of_threads);
       
      }catch(DBValidationException ex){
          throw ex;
      }catch(BSValidationException ex){
          throw ex;     
      }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(BSProcessingException ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }finally {
           this.session=tempSession;
            
        } 
   }
     
     

     
     
     
     private void fileIdentification(String businessDate,String DateAndTime)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       
       try{
           dbg("inside fileIdentification");
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBTransactionService dbts=inject.getDBTransactionService();
           BusinessService bs=inject.getBusinessService(session);
           ITransactionControlService tc=inject.getTransactionControlService();
           ArrayList<String>fileList=bs.getShippedArchivalFiles(DateAndTime, session, dbSession, inject);
           dbg("fileList"+fileList.size());        
           IMetaDataService mds=inject.getMetadataservice();
           int tableId=mds.getTableMetaData("BATCH", "ARCHIVAL_RECOVERY_BATCH_STATUS",session).getI_Tableid();
           dbg("tableId"+tableId);
           
                for(int j=0;j<fileList.size();j++){

                     String l_fileName=fileList.get(j).trim();
                     dbg("l_fileName"+l_fileName);
                   
                    try{ 
                     
                     dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", tableId,l_fileName,"W"," "," ");   
                      tc.commit(session, dbSession);
                     }catch(DBValidationException ex){
                   
                       if(!ex.toString().contains("DB_VAL_009")){
                           throw ex;
                       }
                    }
                }
          
           
          dbg("end of fileIdentification");
        }catch(DBValidationException ex){
          throw ex;
        }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
       
       
   }
     

   
     public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
}
