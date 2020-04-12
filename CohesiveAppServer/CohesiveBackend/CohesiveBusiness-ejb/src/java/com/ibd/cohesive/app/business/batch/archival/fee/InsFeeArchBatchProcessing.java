/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.archival.fee;

import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Stateless
public class InsFeeArchBatchProcessing implements IInsFeeArchBatchProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public InsFeeArchBatchProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
   public void processing (String instituteID,String batchName,int no_of_threads)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       BatchUtil batchUtil=null;
       String l_businessDate=null;
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
        dbg("inside instituteFee batch processing");
        dbg("instituteID"+instituteID);
        dbg("batchName"+batchName);
        dbg("no_of_threads"+no_of_threads);
        String[] datePkry={instituteID};
        DBRecord currentDateRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE", "INSTITUTE_CURRENT_DATE",datePkry ,session, dbSession);
        l_businessDate=currentDateRecord.getRecord().get(1).trim();
        dbg("l_businessDate"+l_businessDate);
        boolean recordExistence=true;
        DBRecord batchEODStatusRecord=null;
        String l_eodStatus=new String();  
        String startTime=bs.getCurrentDateTime();
        dbg("startTime"+startTime);
           
           try{

           String l_eodPkey[]={instituteID,batchName,l_businessDate};
           batchEODStatusRecord=readBuffer.readRecord("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS",l_eodPkey ,session, dbSession,true);
           l_eodStatus=batchEODStatusRecord.getRecord().get(5).trim();
        }catch(DBValidationException ex){

            if(ex.toString().contains("DB_VAL_011")){

                recordExistence=false;
            }
        }
           
        dbg("l_eodStatus"+l_eodStatus);   
           
        if(recordExistence==false||l_eodStatus.equals("F")){
         
            if(l_eodStatus.equals("F")){
            
                Map<String,String>column_to_Update=new HashMap();
                column_to_Update.put("6", "W");
                column_to_Update.put("4", startTime);
                String[] l_pkey={instituteID,batchName,l_businessDate};
                dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS", l_pkey, column_to_Update,session); 
                batchUtil.moveBatchRecordToHistory(instituteID,batchName,l_businessDate,batchEODStatusRecord, session, dbSession, inject);
            }else{ 
             
               dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 95,instituteID,batchName, l_businessDate,startTime," ","W"," "," "," ");   
            }   
            tc.commit(session, dbSession);
            
            
            addRecordForInstituteStatusTable(instituteID,l_businessDate);
            String[] l_pkey={instituteID,l_businessDate};
            DBRecord insRec=readBuffer.readRecord("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "INSTITUTE_FEE_ARCH_BATCH_STATUS", l_pkey, session, dbSession);
            String l_status=insRec.getRecord().get(2).trim();
       
                
                       if(l_status.equals("F")){
                            Map<String,String>column_to_Update=new HashMap();
                            column_to_Update=new HashMap();
                            column_to_Update.put("3", "W");
                            dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "INSTITUTE_FEE_ARCH_BATCH_STATUS", l_pkey, column_to_Update,session); 
                            batchUtil.moveInstituteFeeArchRecordToHistory(instituteID,l_businessDate,insRec, session, dbSession, inject);
                            tc.commit(session, dbSession);
                         }
                
                   instituteProcessing(instituteID,l_businessDate) ;
                       
            
        }    
        dbg("end of studentAssignmenArch batch processing");
         batchUtil.batchSucessHandler(instituteID, l_businessDate, batchName, inject, session, dbSession);
        }catch(DBValidationException ex){
          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }catch(BSValidationException ex){
          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
       
   }
   
   
   
   
     public void processing(String instituteID,String batchName,int no_of_threads,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(instituteID,batchName,no_of_threads);
       
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
     
     
     private void instituteProcessing(String instituteID,String businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
         BatchUtil batchUtil=null;
        try{
             dbg("inside institute wise processing");
             IDBReadBufferService readBuffer=inject.getDBReadBufferService();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IDBTransactionService dbts=inject.getDBTransactionService();
             batchUtil=inject.getBatchUtil(session);
             BusinessService bs=inject.getBusinessService(session);
             ITransactionControlService tc=inject.getTransactionControlService();
             IMetaDataService mds=inject.getMetadataservice();
             String currentDate=bs.getCurrentDate();
             int tableId=mds.getTableMetaData("INSTITUTE", "IVW_FEE_MANAGEMENT", session).getI_Tableid();
             Map<String,DBRecord> instituteFeeMap=null;
                    String startTime=bs.getCurrentDateTime();
                    String[] l_primaryKey={instituteID,businessDate};
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update=new HashMap();
                    column_to_Update.put("4", startTime);
                    dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", "INSTITUTE_FEE_ARCH_BATCH_STATUS", l_primaryKey, column_to_Update,session); 
                    tc.commit(session, dbSession);
             
             
             
             boolean tableExistence=true;
             int checkerDateColId=mds.getColumnMetaData("INSTITUTE", "IVW_FEE_MANAGEMENT", "CHECKER_DATE_STAMP", session).getI_ColumnID();
             dbg("checkerDateColId"+checkerDateColId);       
             
             try{
                 
                 
               instituteFeeMap=  readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE", "IVW_FEE_MANAGEMENT", session, dbSession, "Yes");
                 
                 
             }catch(DBValidationException ex){
                 
                 if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                     
                     tableExistence=false;
                 }else{
                     throw ex;
                 }
             }
             dbg("tableExistence"+tableExistence); 
             
             if(tableExistence){
                 
                Iterator<String>keyIterator= instituteFeeMap.keySet().iterator();
                
                while(keyIterator.hasNext()){
                    
                    String key=keyIterator.next();
                    ArrayList<String>value=instituteFeeMap.get(key).getRecord();
                    String checkerDateStamp=value.get(checkerDateColId-1).trim();
                    dbg("key"+key);
                    dbg("checkerDateStamp"+checkerDateStamp);
                    
                    if(bs.compareCheckerDate(checkerDateStamp,"Fee",instituteID,session,dbSession,inject)){
                        
                        String[] l_pkey=key.split("~");
                        
                        dbg("before delete record call for key"+key);
                        
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE", "IVW_FEE_MANAGEMENT", l_pkey, session);
                        
                        dbg("after delete record call for key"+key);
                        
                        String[] recordValues=getRecordValues(value);
                        
                        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH_"+instituteID+"_"+currentDate,"INSTITUTE",tableId, recordValues);
                        
                    }
                    
                }
                 
             }
             
             tc.commit(session, dbSession);
             dbg("end of institute wise processing");
        batchUtil.instituteFeeArchProcessingSuccessHandler(instituteID, businessDate, inject, session, dbSession);
        }catch(DBValidationException ex){
          batchUtil.instituteFeeArchProcessingErrorHandler(instituteID, businessDate, ex, inject, session, dbSession);
//        }catch(BSValidationException ex){
//         batchUtil.studentFeeArchProcessingErrorHandler(instituteID, businessDate, studentID, ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.instituteFeeArchProcessingErrorHandler(instituteID, businessDate, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.instituteFeeArchProcessingErrorHandler(instituteID, businessDate, ex, inject, session, dbSession);
        }
         
         
         
     }
      private String[] getRecordValues(ArrayList<String> recordList)throws BSProcessingException{
      
      try{
          dbg("inside getRecordValues");
          
          
          String[] recordValues={recordList.get(0),recordList.get(1),recordList.get(2),recordList.get(3),recordList.get(4),recordList.get(5),recordList.get(6),recordList.get(7),recordList.get(8),recordList.get(9),recordList.get(10),recordList.get(11),recordList.get(12),recordList.get(13),recordList.get(14),recordList.get(15)};
          
          
          
          dbg("end of getRecordValues");
          return recordValues;
      }catch(Exception ex){
           dbg(ex);
        throw new BSProcessingException(ex.toString());   
      }
      
  }
     
     
     private void addRecordForInstituteStatusTable(String instituteID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       
       try{
           dbg("inside addRecordForInstituteStatusTable");
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBTransactionService dbts=inject.getDBTransactionService();
           BusinessService bs=inject.getBusinessService(session);
           ITransactionControlService tc=inject.getTransactionControlService();
           IMetaDataService mds=inject.getMetadataservice();
           int tableId=mds.getTableMetaData("BATCH", "INSTITUTE_FEE_ARCH_BATCH_STATUS",session).getI_Tableid();
           dbg("tableId"+tableId);
                   
                    try{ 
                     
                     dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", tableId,instituteID,l_businessDate,"W"," "," ");   
                      tc.commit(session, dbSession);
                     }catch(DBValidationException ex){
                   
                       if(!ex.toString().contains("DB_VAL_009")){
                           throw ex;
                       }
                    }
                
          
           
          dbg("end of addRecordForInstituteStatusTable");
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
