/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.feeNotification;

import com.ibd.cohesive.app.business.batch.fee.IFeeProcessing;
import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.scheduler.WorkDispatch;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class FeeNotificationBatchProcessing implements IFeeNotificationBatchProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public FeeNotificationBatchProcessing() throws NamingException {
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
        dbg("inside feeNotification batch processing");
        dbg("instituteID"+instituteID);
        dbg("batchName"+batchName);
        dbg("no_of_threads"+no_of_threads);
        String[] datePkry={instituteID};
//        DBRecord currentDateRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE", "INSTITUTE_CURRENT_DATE",datePkry ,session, dbSession);
        l_businessDate=bs.getCurrentDate();
        dbg("l_businessDate"+l_businessDate);
        boolean recordExistence=true;
        DBRecord batchEODStatusRecord=null;
        String l_eodStatus=new String();  
        String startTime=bs.getCurrentDateTime();
        int maxSequence=0;
        dbg("startTime"+startTime);
           
           try{
               
           
               maxSequence= batchUtil.getBatchMaxSequence(l_businessDate, instituteID, batchName, inject, session, dbSession);
               

               String l_eodPkey[]={instituteID,batchName,l_businessDate,Integer.toString(maxSequence)};
               batchEODStatusRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS",l_eodPkey ,session, dbSession,true);
               l_eodStatus=batchEODStatusRecord.getRecord().get(5).trim();
        }catch(DBValidationException ex){

            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){

                recordExistence=false;
            }
        }
           
        dbg("l_eodStatus"+l_eodStatus);   
           
//        if(recordExistence==false||l_eodStatus.equals("F")){
          int sequence=0; 
          
        if(recordExistence==true){   
          
            if(l_eodStatus.equals("F")){
            
                int batchHistoryMaxSequence=batchUtil.getBatchHistoryMaxSequence(l_businessDate, instituteID, batchName, inject, session, dbSession);
                
                if(batchHistoryMaxSequence>10){
                    return;
                }
                
                sequence=maxSequence;
                Map<String,String>column_to_Update=new HashMap();
                column_to_Update.put("6", "W");
                column_to_Update.put("4", startTime);
                String[] l_pkey={instituteID,batchName,l_businessDate,Integer.toString(sequence)};
                dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS", l_pkey, column_to_Update,session); 
                batchUtil.moveBatchRecordToHistory(instituteID,batchName,l_businessDate,batchEODStatusRecord, session, dbSession, inject);
            }else{ 
             
               sequence=maxSequence+1;
               dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 95,instituteID,batchName, l_businessDate,startTime," ","W"," "," "," ",Integer.toString(sequence));   
            } 
            
            
        }else{
            
            sequence=1;
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 95,instituteID,batchName, l_businessDate,startTime," ","W"," "," "," ",Integer.toString(sequence));   
            
        }
        
            tc.commit(session, dbSession);
            
         boolean notStatus=  feeIdentification(instituteID,l_businessDate);
           
           dbg("no_of_threads"+no_of_threads);
           
         if(notStatus==true){  
           
            if(no_of_threads==0){

                sequentialProcessing(instituteID,l_businessDate,batchName);
            }else{

                parallelProcessing(instituteID,l_businessDate,batchName,no_of_threads);
            }
         }else{
            String[] l_pkey={instituteID,batchName,l_businessDate,Integer.toString(sequence)};
             String endTime=bs.getCurrentDateTime();
             Map<String,String>column_to_Update=new HashMap();
             column_to_Update.put("5", endTime);
             column_to_Update.put("6", "S");
             column_to_Update.put("7", Integer.toString(0));
             column_to_Update.put("8", Integer.toString(0));
             
             
             dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS", l_pkey, column_to_Update,session); 
         }
         tc.commit(session, dbSession);
         
         
         
//        }    
        dbg("end of feeNotification batch processing");
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
     
     
     private void sequentialProcessing(String instituteID,String l_businessDate,String batchName)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      BatchUtil batchUtil=null; 
      ITransactionControlService tc=null;
      try{
          
           dbg("inside feeNotification batch--->sequentialProcessing");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IFeeNotificationProcessing feeProcessing=inject.getFeeNotificationProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           batchUtil=inject.getBatchUtil(session);
           tc=inject.getTransactionControlService();
           BusinessService bs=inject.getBusinessService(session);
           
           dbg("before reading feeNotification table");
           Map<String,DBRecord>feeEodMap=null;
           
           try{
           
              feeEodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "FEE_NOTIFICATION_EOD_STATUS", session, dbSession);
           }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                 
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_000");  
                          int batchMaxSequence=batchUtil.getBatchMaxSequence(l_businessDate, instituteID, batchName, inject, session, dbSession);
                          
                          String[] l_pkey={instituteID,batchName,l_businessDate,Integer.toString(batchMaxSequence)};
                          String endTime=bs.getCurrentDateTime();
                          Map<String,String>column_to_Update=new HashMap();
                          column_to_Update.put("5", endTime);
                          column_to_Update.put("6", "S");
                          column_to_Update.put("7", Integer.toString(0));
                          column_to_Update.put("8", Integer.toString(0));
             
                          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS", l_pkey, column_to_Update,session); 
                          
                          tc.commit(session, dbSession);
                          return;  
                        }else{

                              throw ex;
                        }
                       
                   }
           
           
//           List<DBRecord>filteredFeeList=feeEodMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
           dbg("after reading feeNotification table");
//           dbg("filteredFeeList size "+filteredFeeList.size());
           Iterator<DBRecord>feeIterator=feeEodMap.values().iterator();
           List<DBRecord>feeList=new ArrayList();
           
           while(feeIterator.hasNext()){
//           for(int i=0;i<filteredFeeList.size();i++){
               
               DBRecord feeRecord=feeIterator.next();
               String l_feeID=feeRecord.getRecord().get(1).trim();
               String l_status=feeRecord.getRecord().get(3).trim();
               int lfailCount=0;
               try{
               
                 lfailCount=Integer.parseInt(feeRecord.getRecord().get(6).trim());
               
               }catch(NumberFormatException ex){
                   
                   lfailCount=0;
               }
               
               dbg("lfailCount"+lfailCount);
               dbg("l_status"+l_status);
               if(l_status.equals("F")||lfailCount>0){
                   
                    int feeHistoryMax=batchUtil.getFeeHistoryMaxSequence(l_businessDate, instituteID, l_feeID, inject, session, dbSession);
                    
                    if(feeHistoryMax<=10){
                        
                        feeList.add(feeRecord);
                    }
                   
               }
               else if(l_status.equals("W")){
                   
                   feeList.add(feeRecord);
               }
               
           
           }
           
           for(int i=0;i<feeList.size();i++){
               DBRecord feeRecord=feeList.get(i);
               String l_feeID=feeRecord.getRecord().get(1).trim();
               String l_groupID=feeRecord.getRecord().get(7).trim();
               String l_status=feeRecord.getRecord().get(3).trim();
//               String l_sequenceNo=feeRecord.getRecord().get(9).trim();
               
               dbg("inside feelist iteration");
               dbg("l_feeID"+l_feeID);
               dbg("l_groupID"+l_groupID);
               dbg("l_status"+l_status);
               
                if(l_status.equals("F")){
            
//                    int feeHistoryMax=batchUtil.getFeeHistoryMaxSequence(l_businessDate, instituteID, l_feeID, inject, session, dbSession);
//                    
//                    if(feeHistoryMax>10){
//                        continue;
//                    }
                    
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update.put("4", "W");
                    String[] l_pkey={instituteID,l_feeID,l_businessDate};
                    dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "FEE_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
                    batchUtil.moveFeeNotificationToHistory(instituteID,l_feeID,l_businessDate,feeRecord, session, dbSession, inject);
                    tc.commit(session, dbSession);
                }
                
                    dbg("Before calling feeNotification processing for "+l_feeID);
                feeProcessing.processing(instituteID,l_feeID,l_groupID,l_businessDate,session);
           }
          dbg("end of feeNotification batch--->sequentialProcessing");  
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
        }
         
     }
     
     
     
     private void parallelProcessing(String instituteID,String l_businessDate,String batchName,int no_of_threads)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      BatchUtil batchUtil=null;   
      ITransactionControlService tc=null;
      try{
           dbg("inside feeNotification batch parallel Processing");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IFeeNotificationProcessing feeProcessing=inject.getFeeNotificationProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           tc=inject.getTransactionControlService();
           batchUtil=inject.getBatchUtil(session);
           BusinessService bs=inject.getBusinessService(session);
           
           dbg("before reading feeNotification table");
           Map<String,DBRecord>feeEodMap=null;
           try{
           
           
                feeEodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "FEE_NOTIFICATION_EOD_STATUS", session, dbSession);
          
           
           }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                 
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_000");  
                           int batchMaxSequence=batchUtil.getBatchMaxSequence(l_businessDate, instituteID, batchName, inject, session, dbSession);
                          
                          String[] l_pkey={instituteID,batchName,l_businessDate,Integer.toString(batchMaxSequence)};
                          String endTime=bs.getCurrentDateTime();
                          Map<String,String>column_to_Update=new HashMap();
                          column_to_Update.put("5", endTime);
                          column_to_Update.put("6", "S");
                          column_to_Update.put("7", Integer.toString(0));
                          column_to_Update.put("8", Integer.toString(0));
             
                          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS", l_pkey, column_to_Update,session); 
                          
                          tc.commit(session, dbSession);
                          
                          
                          return;  
                        }else{

                              throw ex;
                        }
                       
                   }
           
           
//           List<DBRecord>filteredFeeList=feeEodMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
           dbg("after reading feeNotification table");
//           dbg("filteredFeeList size "+filteredFeeList.size());
           Iterator<DBRecord>feeIterator=feeEodMap.values().iterator();
           List<DBRecord>feeList=new ArrayList();
           
           while(feeIterator.hasNext()){ 
               
               DBRecord feeRecord=feeIterator.next();
               String l_feeID=feeRecord.getRecord().get(1).trim();
               String l_status=feeRecord.getRecord().get(3).trim();
               dbg("l_feeID"+l_feeID);
               dbg("l_status"+l_status);
               
               int lfailCount=0;
               try{
               
                 lfailCount=Integer.parseInt(feeRecord.getRecord().get(6).trim());
               
                 
               }catch(NumberFormatException ex){
                   
                   lfailCount=0;
               }
               dbg("lfailCount"+lfailCount);
               
               if(l_status.equals("F")||lfailCount>0){
                   
                    int feeHistoryMax=batchUtil.getFeeHistoryMaxSequence(l_businessDate, instituteID, l_feeID, inject, session, dbSession);
                    
                    if(feeHistoryMax<=10){
                        
                        feeList.add(feeRecord);
                    }
                   
               }
               else if(l_status.equals("W")){
                   
                   feeList.add(feeRecord);
               }
               
           
           }
           
//           Future<String>[] Result=new Future[no_of_threads];
            Future<String>[] Result;
           Map<Integer,WorkDispatch>dispatchedJobs=new HashMap();
           boolean dispatchFailed=false;
           int no_of_execution;
           
           if(feeList.size()%no_of_threads==0){
               
               no_of_execution=feeList.size()/no_of_threads;
               
           }else{
               
               no_of_execution=(feeList.size()/no_of_threads)+1;
           }
           
           dbg("no_of_execution"+no_of_execution);
           
           int executionCount=0;
           
           while(executionCount<no_of_execution){
           
           dbg("inside while(executionCount<no_of_execution)");
           int startIndex=executionCount*no_of_threads;
           int endIndex=startIndex+no_of_threads-1;
           dbg("startIndex"+startIndex);
           dbg("endIndex"+endIndex);
           Result=new  Future[no_of_threads];
           
           //job submission starts
           dbg("job submission starts");
           for(int i=startIndex;i<=endIndex;i++){
               int j=0;
               if(i<feeList.size()){
                   if(startIndex==i){
                       j=0;
                   }else{
                       j++;
                   }
               DBRecord feeRecord=feeList.get(i);
               String l_feeID=feeRecord.getRecord().get(1).trim();
               String l_groupID=feeRecord.getRecord().get(7).trim();
               String l_status=feeRecord.getRecord().get(3).trim();
//               String l_sequenceNo=feeRecord.getRecord().get(9).trim();
               dbg("inside feelist iteration");
               dbg("l_feeID"+l_feeID);
               dbg("l_groupID"+l_groupID);
               dbg("l_status"+l_status); 
               
               
                
                if(l_status.equals("F")){
            
                   
                    
                    
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update.put("4", "W");
                    String[] l_pkey={instituteID,l_feeID,l_businessDate};
                    dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "FEE_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
                    batchUtil.moveFeeNotificationToHistory(instituteID,l_feeID,l_businessDate,feeRecord, session, dbSession, inject);
                    tc.commit(session, dbSession);
                }
                
                
                
                try{
                    
                  Result[j]= (Future<String>)feeProcessing.parallelProcessing(instituteID, l_feeID, l_groupID, l_businessDate);
               
                } catch (EJBException ex) {
                   dispatchFailed=true;
                }
                
                
                if( !dispatchFailed){
                    
                     WorkDispatch dispatch=new WorkDispatch(l_feeID,"WIP");
                     dispatchedJobs.put(j, dispatch);
                }
                
                dbg("dispatchFailed"+dispatchFailed);
             dispatchFailed=false;   
           
           }
           } 
           dbg("job submission starts");
           //job submission ends
           
             //job status monitoring starts   
             dbg("job status monitoring starts");
              int threadCount=0;
               boolean comeOutLoop=false;
               dbg("dispatchedJobs.size()"+dispatchedJobs.size());
               if(dispatchedJobs.size()>0){
               
               while(comeOutLoop==false){
                    
                 dbg("inside while comeOutLoop==false");  
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
                 dbg("threadCount"+threadCount);
                 dbg("no_of_threads"+no_of_threads);
//                 if(threadCount==no_of_threads)
                 if(threadCount==dispatchedJobs.size())
                  comeOutLoop=true;
                 
               }
               //result null
               Result=null;
               dispatchedJobs.clear();
               } 
           executionCount++;
               }
           
         batchUtil.batchSucessHandler(instituteID, l_businessDate, batchName, inject, session, dbSession);
         dbg("end of feeNotification batch--->parallelProcessing");  
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
        }
         
     }
     
     
     
     
     
     
   
   private boolean feeIdentification(String instituteID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       ITransactionControlService tc=null;
       try{
           dbg("inside feeNotification batch feeIdentification");
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
          // IPDataService pds=inject.getPdataservice();
           IDBTransactionService dbts=inject.getDBTransactionService();
           BusinessService bs=inject.getBusinessService(session);
           tc=inject.getTransactionControlService();
           IPDataService pds=inject.getPdataservice();
           String dateformat=i_db_properties.getProperty("DATE_FORMAT");
           SimpleDateFormat formatter = new SimpleDateFormat(dateformat); 
           Map<String,DBRecord>l_feeMap=null;
           try{
           
           l_feeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", session, dbSession);
           dbg("l_feeMap size"+l_feeMap.size());
           
           }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                 
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_000");  
                          return false;  
                        }else{

                              throw ex;
                        }
                       
                   }
           
           
           
           
           
           
           List<DBRecord>l_feeRecords=l_feeMap.values().stream().filter(rec->rec.getRecord().get(11).trim().equals("O")&&rec.getRecord().get(12).trim().equals("A")).collect(Collectors.toList());
           dbg("l_feeRecords size"+l_feeRecords.size());    
           ArrayList<DBRecord>filteredNotififications=new ArrayList();
           
           for(int i=0;i<l_feeRecords.size();i++){
               
               DBRecord feeRecord=l_feeRecords.get(i);
               
               String l_dueDateString=l_feeRecords.get(i).getRecord().get(6).trim();
               String[] pkey={instituteID};
               ArrayList<String>contractList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",pkey);
            
               int notificationDays=Integer.parseInt(contractList.get(15).trim());
               String l_currentDateString=bs.getCurrentDate();
//               if(l_businessDate.equals(checkerDate)&&l_businessDate.equals(instant)){  
               Date currentDate=formatter.parse(l_currentDateString);
               Date dueDate=formatter.parse(l_dueDateString);
               
               if(currentDate.compareTo(dueDate)<=0){
               
               long diff = dueDate.getTime() - currentDate.getTime();
               
               long diffDays=    diff / (24 * 60 * 60 * 1000);
                    if(diffDays<=notificationDays){  
                   
                          filteredNotififications.add(feeRecord);
                   
                    }
                    
               }else{
                   
                   long diff = currentDate.getTime() - dueDate.getTime();
               
                  long diffDays=    diff / (24 * 60 * 60 * 1000);
                    if(diffDays<=notificationDays){  
                   
                          filteredNotififications.add(feeRecord);
                   
                    }
                   
                   
               }
                    
                    
                    
           }
           
           
           
           if(filteredNotififications.isEmpty()){
               
               return false;
           }
           
           
           
           
           for(int i=0;i<filteredNotififications.size();i++){
               
               String feeID=filteredNotififications.get(i).getRecord().get(2).trim();
               String groupID=filteredNotififications.get(i).getRecord().get(1).trim();
//               String instant=filteredNotififications.get(i).getRecord().get(19).trim();
   
               
               dbg("inside feeNotification records iteration");
               dbg("feeID"+feeID);
//               dbg("checkerDateStamp"+checkerDateStamp);
//               dbg("checkerDate"+checkerDate);
               dbg("groupID"+groupID);
               
//               if(l_businessDate.equals(instant)){
               
               try{
                   
                 dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 333,instituteID,feeID,l_businessDate,"W"," "," "," ",groupID," "," "); 
                 
                 
                 tc.commit(session, dbSession);
               }catch(DBValidationException ex){
                   tc.rollBack(session, dbSession);
                   if(!ex.toString().contains("DB_VAL_009")){
                       throw ex;
                   }
                   
                   
//               }
           }
               
               
                try{
                   
                 dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE",312,feeID,"C"); 
                 
                 
                 tc.commit(session, dbSession);
               }catch(DBValidationException ex){
                   tc.rollBack(session, dbSession);
                   if(!ex.toString().contains("DB_VAL_009")){
                       throw ex;
                   }
                   
                }
               
               
               
               
               
           }
           
           
           
           dbg("end of feeNotification identification");
           return true;
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
     
     
    
     
     
     
     
     
//   public void processing (String instituteID,String batchName,int no_of_threads)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//       BatchUtil batchUtil=null;
//       String l_businessDate=null;
//       boolean l_session_created_now=false;
//       ITransactionControlService tc=null;
//       try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();   
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        IDBTransactionService dbts=inject.getDBTransactionService();
//        batchUtil=inject.getBatchUtil(session);
//        BusinessService bs=inject.getBusinessService(session);   
//        tc=inject.getTransactionControlService();
//        dbg("inside feeNotification batch processing");
//        dbg("instituteID"+instituteID);
//        dbg("batchName"+batchName);
//        dbg("no_of_threads"+no_of_threads);
//        String[] datePkry={instituteID};
//        DBRecord currentDateRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE", "INSTITUTE_CURRENT_DATE",datePkry ,session, dbSession);
//        l_businessDate=currentDateRecord.getRecord().get(1).trim();
//        dbg("l_businessDate"+l_businessDate);
//        boolean recordExistence=true;
//        DBRecord batchEODStatusRecord=null;
//        String l_eodStatus=new String();  
//        String startTime=bs.getCurrentDateTime();
//        dbg("startTime"+startTime);
//           
//           try{
//
//           String l_eodPkey[]={instituteID,batchName,l_businessDate};
//           batchEODStatusRecord=readBuffer.readRecord("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS",l_eodPkey ,session, dbSession,true);
//           l_eodStatus=batchEODStatusRecord.getRecord().get(5).trim();
//        }catch(DBValidationException ex){
//
//            if(ex.toString().contains("DB_VAL_011")){
//
//                recordExistence=false;
//            }
//        }
//           
//        dbg("l_eodStatus"+l_eodStatus);   
//           
//        if(recordExistence==false||l_eodStatus.equals("F")){
//         
//            if(l_eodStatus.equals("F")){
//            
//                Map<String,String>column_to_Update=new HashMap();
//                column_to_Update.put("6", "W");
//                column_to_Update.put("4", startTime);
//                String[] l_pkey={instituteID,batchName,l_businessDate};
//                dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS", l_pkey, column_to_Update,session); 
//                batchUtil.moveBatchRecordToHistory(instituteID,batchName,l_businessDate,batchEODStatusRecord, session, dbSession, inject);
//            }else{ 
//             
//               dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 95,instituteID,batchName, l_businessDate,startTime," ","W"," "," "," ");   
//            }   
//            tc.commit(session, dbSession);
//            
//           feeIdentification(instituteID,l_businessDate);
//           
//           dbg("no_of_threads"+no_of_threads);
//            if(no_of_threads==0){
//
//                sequentialProcessing(instituteID,l_businessDate,batchName);
//            }else{
//
//                parallelProcessing(instituteID,l_businessDate,batchName,no_of_threads);
//            }
//           
//        }    
//        dbg("end of feeNotification batch processing");
//        }catch(Exception ex){
//           dbg(ex);
//           batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
//        }finally{
//               if(l_session_created_now){    
//                  dbSession.clearSessionObject();
//                  session.clearSessionObject();
//               }
//           }
//       
//   }
//   
//   
//   
//   
//     public void processing(String instituteID,String batchName,int no_of_threads,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//      
//       CohesiveSession tempSession = this.session;
//       
//       try{
//           
//           this.session=session;
//           processing(instituteID,batchName,no_of_threads);
//       
//      }catch(DBValidationException ex){
//          throw ex;
//      }catch(BSValidationException ex){
//          throw ex;     
//      }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//      }catch(BSProcessingException ex){
//           dbg(ex);
//           throw new BSProcessingException(ex.toString());
//     }catch(Exception ex){
//           dbg(ex);
//           throw new BSProcessingException(ex.toString());
//     }finally {
//           this.session=tempSession;
//            
//        } 
//   }
//     
//     
//     private void sequentialProcessing(String instituteID,String l_businessDate,String batchName)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//      BatchUtil batchUtil=null; 
//      ITransactionControlService tc=null;
//      try{
//          
//           dbg("inside feeNotification batch--->sequentialProcessing");
//           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//           IBDProperties i_db_properties=session.getCohesiveproperties();
//           IFeeProcessing feeProcessing=inject.getFeeProcessing();
//           IDBTransactionService dbts=inject.getDBTransactionService();
//           batchUtil=inject.getBatchUtil(session);
//           tc=inject.getTransactionControlService();
//           
//           dbg("before reading feeNotification table");
//           Map<String,DBRecord>feeEodMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "FEE_NOTIFICATION_EOD_STATUS", session, dbSession);
//           List<DBRecord>feeList=feeEodMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
//           dbg("after reading feeNotification table");
//           dbg("feeList size "+feeList.size());
//           
//           for(int i=0;i<feeList.size();i++){
//               DBRecord feeRecord=feeList.get(i);
//               String l_feeID=feeRecord.getRecord().get(1).trim();
//               String l_groupID=feeRecord.getRecord().get(7).trim();
//               String l_status=feeRecord.getRecord().get(3).trim();
//               
//               dbg("inside feelist iteration");
//               dbg("l_feeID"+l_feeID);
//               dbg("l_groupID"+l_groupID);
//               dbg("l_status"+l_status);
//               
//                if(l_status.equals("F")){
//            
//                    Map<String,String>column_to_Update=new HashMap();
//                    column_to_Update.put("4", "W");
//                    String[] l_pkey={instituteID,l_feeID,l_businessDate};
//                    dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "FEE_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
//                    batchUtil.moveFeeRecordToHistory(instituteID,l_feeID,l_businessDate,feeRecord, session, dbSession, inject);
//                    tc.commit(session, dbSession);
//                }
//                
//                    dbg("Before calling feeNotification processing for "+l_feeID);
//                feeProcessing.processing(instituteID,l_feeID,l_groupID,l_businessDate,session);
//           }
//          dbg("end of feeNotification batch--->sequentialProcessing");  
//         batchUtil.batchSucessHandler(instituteID, l_businessDate, batchName, inject, session, dbSession);
//        }catch(DBValidationException ex){
//          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
//        }catch(BSValidationException ex){
//          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
//        }catch(DBProcessingException ex){
//          dbg(ex);
//          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
//        }catch(Exception ex){
//           dbg(ex);
//           batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
//        }
//         
//     }
//     
//     
//     
//     private void parallelProcessing(String instituteID,String l_businessDate,String batchName,int no_of_threads)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//      BatchUtil batchUtil=null;   
//      ITransactionControlService tc=null;
//      try{
//           dbg("inside feeNotification batch parallel Processing");
//           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//           IBDProperties i_db_properties=session.getCohesiveproperties();
//           IFeeProcessing feeProcessing=inject.getFeeProcessing();
//           IDBTransactionService dbts=inject.getDBTransactionService();
//           tc=inject.getTransactionControlService();
//           batchUtil=inject.getBatchUtil(session);
//           
//           dbg("before reading feeNotification table");
//           Map<String,DBRecord>feeEodMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "FEE_NOTIFICATION_EOD_STATUS", session, dbSession);
//           List<DBRecord>feeList=feeEodMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
//           dbg("after reading feeNotification table");
//           dbg("feeList size "+feeList.size());
////           Future<String>[] Result=new Future[no_of_threads];
//            Future<String>[] Result;
//           Map<Integer,WorkDispatch>dispatchedJobs=new HashMap();
//           boolean dispatchFailed=false;
//           int no_of_execution;
//           
//           if(feeList.size()%no_of_threads==0){
//               
//               no_of_execution=feeList.size()/no_of_threads;
//               
//           }else{
//               
//               no_of_execution=(feeList.size()/no_of_threads)+1;
//           }
//           
//           dbg("no_of_execution"+no_of_execution);
//           
//           int executionCount=0;
//           
//           while(executionCount<no_of_execution){
//           
//           dbg("inside while(executionCount<no_of_execution)");
//           int startIndex=executionCount*no_of_threads;
//           int endIndex=startIndex+no_of_threads-1;
//           dbg("startIndex"+startIndex);
//           dbg("endIndex"+endIndex);
//           Result=new  Future[no_of_threads];
//           
//           //job submission starts
//           dbg("job submission starts");
//           for(int i=startIndex;i<=endIndex;i++){
//               int j=0;
//               if(i<feeList.size()){
//                   if(startIndex==i){
//                       j=0;
//                   }else{
//                       j++;
//                   }
//               DBRecord feeRecord=feeList.get(i);
//               String l_feeID=feeRecord.getRecord().get(1).trim();
//               String l_groupID=feeRecord.getRecord().get(7).trim();
//               String l_status=feeRecord.getRecord().get(3).trim();
//               dbg("inside feelist iteration");
//               dbg("l_feeID"+l_feeID);
//               dbg("l_groupID"+l_groupID);
//               dbg("l_status"+l_status); 
//               
//               
//                
//                if(l_status.equals("F")){
//            
//                    Map<String,String>column_to_Update=new HashMap();
//                    column_to_Update.put("4", "W");
//                    String[] l_pkey={instituteID,l_feeID,l_businessDate};
//                    dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "FEE_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
//                    batchUtil.moveFeeRecordToHistory(instituteID,l_feeID,l_businessDate,feeRecord, session, dbSession, inject);
//                    tc.commit(session, dbSession);
//                }
//                
//                
//                
//                try{
//                    
//                  Result[j]= (Future<String>)feeProcessing.parallelProcessing(instituteID, l_feeID, l_groupID, l_businessDate);
//               
//                } catch (EJBException ex) {
//                   dispatchFailed=true;
//                }
//                
//                
//                if( !dispatchFailed){
//                    
//                     WorkDispatch dispatch=new WorkDispatch(l_feeID,"WIP");
//                     dispatchedJobs.put(j, dispatch);
//                }
//                
//                dbg("dispatchFailed"+dispatchFailed);
//             dispatchFailed=false;   
//           
//           }
//           } 
//           dbg("job submission starts");
//           //job submission ends
//           
//             //job status monitoring starts   
//             dbg("job status monitoring starts");
//              int threadCount=0;
//               boolean comeOutLoop=false;
//               dbg("dispatchedJobs.size()"+dispatchedJobs.size());
//               if(dispatchedJobs.size()>0){
//               
//               while(comeOutLoop==false){
//                    
//                 dbg("inside while comeOutLoop==false");  
//                 Iterator<Integer> keyIterator=  dispatchedJobs.keySet().iterator();
//                 WorkDispatch dispatch=null;
//                   
//                 
//                 while(keyIterator.hasNext()){
//                     
//                     Integer key= keyIterator.next();
//                     dbg("key"+key);
//                    
//                     dbg("dispatchedJobs.get(key).getResult()"+dispatchedJobs.get(key).getResult());
//                     if(dispatchedJobs.get(key).getResult().equals("WIP")){
//                     
//                     dbg("Result[key].isDone()");    
//                     if(Result[key].isDone()){
//                         
//                            try {
//                                  dispatch=  dispatchedJobs.get(key);
//                                  String result=Result[key].get();
//                                  dispatch.setResult(result);
//                                  
//                                } catch (ExecutionException ex) {
//                                    dbg(ex);
//                                  dispatch.setResult("Fail");
//                                  
//                                  throw ex;
//                                  
//                                }
//                         threadCount++;
//                     }
//                     }
//                 }
//                 dbg("threadCount"+threadCount);
//                 dbg("no_of_threads"+no_of_threads);
////                 if(threadCount==no_of_threads)
//                 if(threadCount==dispatchedJobs.size())
//                  comeOutLoop=true;
//                 
//               }
//               //result null
//               Result=null;
//               dispatchedJobs.clear();
//               } 
//           executionCount++;
//               }
//           
//         batchUtil.batchSucessHandler(instituteID, l_businessDate, batchName, inject, session, dbSession);
//         dbg("end of feeNotification batch--->parallelProcessing");  
//        }catch(DBValidationException ex){
//          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
//        }catch(BSValidationException ex){
//          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
//        }catch(DBProcessingException ex){
//          dbg(ex);
//          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
//        }catch(Exception ex){
//           dbg(ex);
//           batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
//        }
//         
//     }
//     
//     
//     
//     
//     
//     
//   
//   private void feeIdentification(String instituteID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//       ITransactionControlService tc=null;
//       try{
//           dbg("inside feeNotification batch feeIdentification");
//           IBDProperties i_db_properties=session.getCohesiveproperties();
//           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//           IDBTransactionService dbts=inject.getDBTransactionService();
//           BusinessService bs=inject.getBusinessService(session);
//           tc=inject.getTransactionControlService();
//           Map<String,DBRecord>l_feeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE","IVW_FEE_MANAGEMENT", session, dbSession);
//           dbg("l_feeMap size"+l_feeMap.size());
//           List<DBRecord>l_feeRecords=l_feeMap.values().stream().filter(rec->rec.getRecord().get(11).trim().equals("O")&&rec.getRecord().get(12).trim().equals("A")).collect(Collectors.toList());
//           dbg("l_feeRecords size"+l_feeRecords.size());           
//           
//           for(int i=0;i<l_feeRecords.size();i++){
//               
//               String feeID=l_feeRecords.get(i).getRecord().get(2).trim();
//               String checkerDateStamp=l_feeRecords.get(i).getRecord().get(10).trim();
//               String checkerDate=bs.getDateFromDateStamp(checkerDateStamp);
//               String groupID=l_feeRecords.get(i).getRecord().get(1).trim();
//               
//               dbg("inside feeNotification records iteration");
//               dbg("feeID"+feeID);
//               dbg("checkerDateStamp"+checkerDateStamp);
//               dbg("checkerDate"+checkerDate);
//               dbg("groupID"+groupID);
//               
//               if(l_businessDate.equals(checkerDate)){
//               
//               try{
//                   
//                 dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 117,instituteID,feeID,l_businessDate,"W"," "," "," ",groupID," "," "); 
//                 tc.commit(session, dbSession);
//               }catch(DBValidationException ex){
//                   
//                   if(!ex.toString().contains("DB_VAL_009")){
//                       throw ex;
//                   }
//               }
//           }
//           }
//           
//           
//           
//           dbg("end of feeNotification identification");
//        }catch(DBValidationException ex){
//          throw ex;
//        }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch(Exception ex){
//           dbg(ex);
//           throw new BSProcessingException(ex.toString());
//     }
//       
//       
//   }
//   
//  
//   
//   
//  
//   
//   
//   
     public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
}
