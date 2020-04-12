/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.defragmentation;

import com.ibd.cohesive.app.business.batch.archival.profile.IUProfileArchProcessing;
import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.scheduler.WorkDispatch;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Stateless
public class DefragmentationBatchProcessing implements IDefragmentationBatchProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public DefragmentationBatchProcessing() throws NamingException {
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
        dbg("inside defragmentation batch processing");
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
            
            fileIdentification(businessDate);
           
            
            dbg("no_of_threads"+no_of_threads);
            if(no_of_threads==0){

                sequentialProcessing(businessDate,batchName);
            }else{

                parallelProcessing(businessDate,batchName,no_of_threads);
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
           IFileDefragmentationProcessing fileDefragmentation=inject.getFileDefragmentationProcessing();
           batchUtil=inject.getBatchUtil(session);
           tc=inject.getTransactionControlService();
           
           Map<String,DBRecord>defragmentationMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", "DEFRAGMENTATION_BATCH_STATUS", session, dbSession);
           List<DBRecord>fileList=defragmentationMap.values().stream().filter(rec->rec.getRecord().get(2).trim().equals("F")||rec.getRecord().get(2).trim().equals("W")).collect(Collectors.toList());
           
           for(int i=0;i<fileList.size();i++){
              DBRecord defragRec=fileList.get(i);
              String fileName=defragRec.getRecord().get(0).trim();
              String l_status=defragRec.getRecord().get(2).trim();
               
               dbg("inside fileList iteration");
               dbg("l_status"+l_status);
               
                if(l_status.equals("F")){
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update=new HashMap();
                    column_to_Update.put("3", "W");
                    String[]l_pkey={fileName,businessDate};
                    dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", "DEFRAGMENTATION_BATCH_STATUS", l_pkey, column_to_Update,session); 
                    batchUtil.moveDefragmentationArchRecordToHistory(fileName,businessDate,defragRec, session, dbSession, inject);
                    tc.commit(session, dbSession);
                }
                
                fileDefragmentation.processing(fileName, businessDate, session);
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
   
     private void parallelProcessing(String businessDate,String batchName,int no_of_threads)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      BatchUtil batchUtil=null; 
      ITransactionControlService tc=null;
      try{
           dbg("inside user profile archival Processing -->parallel");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IFileDefragmentationProcessing fileDefragmentation=inject.getFileDefragmentationProcessing();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBTransactionService dbts=inject.getDBTransactionService();
           tc=inject.getTransactionControlService();
           batchUtil=inject.getBatchUtil(session);
           Map<String,DBRecord>userAssArchMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", "DEFRAGMENTATION_BATCH_STATUS", session, dbSession);
           List<DBRecord>userList=userAssArchMap.values().stream().filter(rec->rec.getRecord().get(2).trim().equals("F")||rec.getRecord().get(2).trim().equals("W")).collect(Collectors.toList());
           dbg("userList"+userList.size());
           Future<String>[] Result;
           Map<Integer,WorkDispatch>dispatchedJobs=new HashMap();
           boolean dispatchFailed=false;
           int no_of_execution;
           
           if(userList.size()%no_of_threads==0){
               
               no_of_execution=userList.size()/no_of_threads;
               
           }else{
               
               no_of_execution=(userList.size()/no_of_threads)+1;
           }
           
           dbg("no_of_execution"+no_of_execution);
           int executionCount=0;
           
           while(executionCount<no_of_execution){
           
           int startIndex=executionCount*no_of_threads;
           int endIndex=startIndex+no_of_threads-1;
           dbg("start index"+startIndex);
           dbg("endIndex"+endIndex);
           Result=new  Future[no_of_threads];
           
           //job submission starts
           for(int i=startIndex;i<=endIndex;i++){
               int j=0;
               if(i<userList.size()){
                if(startIndex==i){
                       j=0;
                   }else{
                       j++;
                   }
                DBRecord stdentProfileRec=userList.get(i);
                String fileName=stdentProfileRec.getRecord().get(0).trim();
                String l_status=stdentProfileRec.getRecord().get(2).trim();
                dbg("fileName"+fileName);
                dbg("l_status"+l_status);
                         
                        if(l_status.equals("F")){
                            Map<String,String>column_to_Update=new HashMap();
                            column_to_Update=new HashMap();
                            column_to_Update.put("3", "W");
                            String[]l_pkey={fileName,businessDate};
                            dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", "DEFRAGMENTATION_BATCH_STATUS", l_pkey, column_to_Update,session); 
                            batchUtil.moveDefragmentationArchRecordToHistory(fileName,businessDate,stdentProfileRec, session, dbSession, inject);
                            tc.commit(session, dbSession);
                }
                         
                
                try{
                    
                  Result[j]= (Future<String>)fileDefragmentation.parallelProcessing(fileName, businessDate);
               
                } catch (EJBException ex) {
                   dispatchFailed=true;
                }
                
                
                if( !dispatchFailed){
                    
                     WorkDispatch dispatch=new WorkDispatch(fileName,"WIP");
                     dispatchedJobs.put(j, dispatch);
                }
                
                dbg("dispatchFailed"+dispatchFailed);
             dispatchFailed=false;   
           
           }
           } 
           //job submission ends
           
             //job status monitoring starts   
             
              int threadCount=0;
               boolean comeOutLoop=false;
               dbg("dispatchedJobs.size()"+dispatchedJobs.size());
               if(dispatchedJobs.size()>0){
               
               while(comeOutLoop==false){
                    
                 dbg("while(comeOutLoop==false)");  
                 Iterator<Integer> keyIterator=  dispatchedJobs.keySet().iterator();
                 WorkDispatch dispatch=null;
                   
                 
                 while(keyIterator.hasNext()){
                     
                     Integer key= keyIterator.next();
                    
                     dbg("key"+key);
                     dbg("dispatchedJobs.get(key).getResult()"+dispatchedJobs.get(key).getResult());
                     
                     if(dispatchedJobs.get(key).getResult().equals("WIP")){
                     
                         dbg("Result[key].isDone()");
                     if(Result[key].isDone()){
                         
                            try {
                                  dispatch=  dispatchedJobs.get(key);
                                  String result=Result[key].get();
                                  dispatch.setResult(result);
                                  
                                } catch (ExecutionException ex) {
                                    dbg(ex);
                                  dispatch.setResult("Fail");
                                  
                                  throw ex;
                                  
                                }
                         threadCount++;
                     }
                     }
                 }
                 
                 if(threadCount==dispatchedJobs.size())
                  comeOutLoop=true;
               }
               Result=null;
               dispatchedJobs.clear();
               } 
           executionCount++;
               }
           
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
     
     

     
     
     
     private void fileIdentification(String businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       
       try{
           dbg("inside fileIdentification");
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBTransactionService dbts=inject.getDBTransactionService();
           BusinessService bs=inject.getBusinessService(session);
           ITransactionControlService tc=inject.getTransactionControlService();
           ArrayList<String>fileList=bs.getAllFiles();
           IBDFileUtil fileUtil=dbSession.getIibd_file_util();
           dbg("fileList"+fileList.size());        
           IMetaDataService mds=inject.getMetadataservice();
           int tableId=mds.getTableMetaData("BATCH", "DEFRAGMENTATION_BATCH_STATUS",session).getI_Tableid();
           dbg("tableId"+tableId);
           
                for(int j=0;j<fileList.size();j++){

                     String l_fileName=fileList.get(j).trim();
                     dbg("l_fileName"+l_fileName);
                     String l_fileType=fileUtil.getFileType(l_fileName);
                     dbg("l_fileType"+l_fileType);
                   
                    try{ 
                     
                     dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", tableId,l_fileName,businessDate,"W"," "," ",l_fileType);   
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
