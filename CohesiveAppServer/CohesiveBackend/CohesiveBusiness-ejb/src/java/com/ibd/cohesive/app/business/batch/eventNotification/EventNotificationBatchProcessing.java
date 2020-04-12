/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.eventNotification;

import com.ibd.businessViews.IAmazonEmailService;
import com.ibd.businessViews.IAmazonSMSService;
import com.ibd.cohesive.app.business.batch.notification.INotificationProcessing;
import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.scheduler.WorkDispatch;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.lock.ILockService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.Email;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
public class EventNotificationBatchProcessing implements IEventNotificationBatchProcessing{
    
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public EventNotificationBatchProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
       public void processing (String instituteID,String batchName,int no_of_threads)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       BatchUtil batchUtil=null;
       String l_businessDate=null;
       boolean l_session_created_now=false;
       ITransactionControlService tc=null;
       Properties batch=null;
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
         dbg("inside eventNotification batch processing");
        
        batch  = new Properties();
        batch.load(new FileInputStream("/cohesive/dbhome/BATCH/batch.properties"));
        String eventNotificationTime=batch.getProperty("EVENT_NOTIFICATION_TIME");
        int eventNotificationHour=Integer.parseInt(eventNotificationTime.split(":")[0]);
        int eventNotificationMin=Integer.parseInt(eventNotificationTime.split(":")[1]);
        String currentDateTime=bs.getCurrentDateTime();
        String time=currentDateTime.split(" ")[1];
        dbg("time"+time);
        int hour=Integer.parseInt(time.split(":")[0]);
        dbg("hour"+hour);
        int min=Integer.parseInt(time.split(":")[1]);
        dbg("min"+min);
        boolean batchRun=false;
        if(hour>=eventNotificationHour){
            
            if(hour==eventNotificationHour){
                
                if(min>=eventNotificationMin){
                    
                    batchRun=true;
                }else{
                    batchRun=false;
                }
                
            }else{
                
                batchRun=true;
            }
            
        }
        dbg("batchRun"+batchRun);
//        String dateformat=i_db_properties.getProperty("DATE_TIME_FORMAT");
        
       if(batchRun){


        dbg("instituteID"+instituteID);
        dbg("batchName"+batchName);
        dbg("no_of_threads"+no_of_threads);
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
                
                if(batchHistoryMaxSequence>20){
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
            
         studentIdentification(instituteID,l_businessDate);
           
           dbg("no_of_threads"+no_of_threads);
           

           
            if(no_of_threads==0){

                sequentialProcessing(instituteID,l_businessDate,batchName);
            }else{

                parallelProcessing(instituteID,l_businessDate,batchName,no_of_threads);
            }
         
         tc.commit(session, dbSession);
         
       }
         
//        }    
        dbg("end of eventNotification batch processing");
        }catch(Exception ex){
           dbg(ex);
           batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }finally{
           batch.clear();
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
//       
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
          
           dbg("inside eventNotification batch--->sequentialProcessing");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
            IStudentEventNotificationProcessing eventNotificationProcessing=inject.getStudentEventNotificationProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           batchUtil=inject.getBatchUtil(session);
           tc=inject.getTransactionControlService();
           BusinessService bs=inject.getBusinessService(session);
           
           dbg("before reading eventNotification table");
           Map<String,DBRecord>eventNotificationEodMap=null;
           
           try{
           
              eventNotificationEodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_EVENT_NOTIFICATION_EOD_STATUS", session, dbSession);
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
           
           
//           List<DBRecord>filteredEventNotificationList=eventNotificationEodMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
           dbg("after reading eventNotification table");
//           dbg("filteredEventNotificationList size "+filteredEventNotificationList.size());
           Iterator<DBRecord>eventNotificationIterator=eventNotificationEodMap.values().iterator();
           List<DBRecord>eventNotificationList=new ArrayList();
           
           while(eventNotificationIterator.hasNext()){
//           for(int i=0;i<filteredEventNotificationList.size();i++){
               
               DBRecord eventNotificationRecord=eventNotificationIterator.next();
               String l_studentID=eventNotificationRecord.getRecord().get(1).trim();
               String l_status=eventNotificationRecord.getRecord().get(3).trim();
               
               
               dbg("l_status"+l_status);
               if(l_status.equals("F")){
                   
                    int eventNotificationHistoryMax=batchUtil.getStudentEventNotificationHistoryMaxSequence(l_businessDate, instituteID, l_studentID, inject, session, dbSession);
                    
                    if(eventNotificationHistoryMax<=10){
                        
                        eventNotificationList.add(eventNotificationRecord);
                    }
                   
               }
               else if(l_status.equals("W")){
                   
                   eventNotificationList.add(eventNotificationRecord);
               }
               
           
           }
           
           for(int i=0;i<eventNotificationList.size();i++){
               DBRecord eventNotificationRecord=eventNotificationList.get(i);
               String l_studentID=eventNotificationRecord.getRecord().get(1).trim();
               String l_status=eventNotificationRecord.getRecord().get(3).trim();
//               String l_sequenceNo=eventNotificationRecord.getRecord().get(9).trim();
               
               dbg("inside eventNotificationlist iteration");
               dbg("studentID"+l_studentID);
               
                if(l_status.equals("F")){
            
//                    int eventNotificationHistoryMax=batchUtil.getEventNotificationHistoryMaxSequence(l_businessDate, instituteID, l_eventNotificationID, inject, session, dbSession);
//                    
//                    if(eventNotificationHistoryMax>10){
//                        continue;
//                    }
                    
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update.put("4", "W");
                    String[] l_pkey={instituteID,l_studentID,l_businessDate};
                    dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_EVENT_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
                    batchUtil.moveStudentEventNotificationRecordToHistory(instituteID,l_studentID,l_businessDate,eventNotificationRecord, session, dbSession, inject);
                    tc.commit(session, dbSession);
                }
                
                eventNotificationProcessing.processing(instituteID,  l_studentID, l_businessDate, session);
           }
          dbg("end of eventNotification batch--->sequentialProcessing");  
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
           dbg("inside eventNotification batch parallel Processing");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IStudentEventNotificationProcessing eventNotificationProcessing=inject.getStudentEventNotificationProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           tc=inject.getTransactionControlService();
           batchUtil=inject.getBatchUtil(session);
           BusinessService bs=inject.getBusinessService(session);
           
           dbg("before reading eventNotification table");
           Map<String,DBRecord>eventNotificationEodMap=null;
           try{
           
           
                eventNotificationEodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_EVENT_NOTIFICATION_EOD_STATUS", session, dbSession);
          
           
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
           
           
//           List<DBRecord>filteredEventNotificationList=eventNotificationEodMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
           dbg("after reading eventNotification table");
//           dbg("filteredEventNotificationList size "+filteredEventNotificationList.size());
           Iterator<DBRecord>eventNotificationIterator=eventNotificationEodMap.values().iterator();
           List<DBRecord>eventNotificationList=new ArrayList();
           
           while(eventNotificationIterator.hasNext()){
//           for(int i=0;i<filteredEventNotificationList.size();i++){
               
               DBRecord eventNotificationRecord=eventNotificationIterator.next();
               String l_studentID=eventNotificationRecord.getRecord().get(1).trim();
               String l_status=eventNotificationRecord.getRecord().get(3).trim();
               
               
               dbg("l_status"+l_status);
               if(l_status.equals("F")){
                   
                    int eventNotificationHistoryMax=batchUtil.getStudentEventNotificationHistoryMaxSequence(l_businessDate, instituteID, l_studentID, inject, session, dbSession);
                    
                    if(eventNotificationHistoryMax<=10){
                        
                        eventNotificationList.add(eventNotificationRecord);
                    }
                   
               }
               else if(l_status.equals("W")){
                   
                   eventNotificationList.add(eventNotificationRecord);
               }
               
           
           }
           
//           Future<String>[] Result=new Future[no_of_threads];
            Future<String>[] Result;
           Map<Integer,WorkDispatch>dispatchedJobs=new HashMap();
           boolean dispatchFailed=false;
           int no_of_execution;
           
           if(eventNotificationList.size()%no_of_threads==0){
               
               no_of_execution=eventNotificationList.size()/no_of_threads;
               
           }else{
               
               no_of_execution=(eventNotificationList.size()/no_of_threads)+1;
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
               if(i<eventNotificationList.size()){
                   if(startIndex==i){
                       j=0;
                   }else{
                       j++;
                   }
               DBRecord eventNotificationRecord=eventNotificationList.get(i);
               String l_studentID=eventNotificationRecord.getRecord().get(1).trim();
               String l_status=eventNotificationRecord.getRecord().get(3).trim();
//               String l_sequenceNo=eventNotificationRecord.getRecord().get(9).trim();
               dbg("inside eventNotificationlist iteration");
               dbg("l_status"+l_status); 
               
               
                
                if(l_status.equals("F")){
            
                   
                    
                    
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update.put("6", "W");
                    String[] l_pkey={instituteID,l_studentID,l_businessDate};
//                    dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "MARK_BATCH_STATUS", l_pkey, column_to_Update,session); 
                    dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_EVENT_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
                     batchUtil.moveStudentEventNotificationRecordToHistory(instituteID, l_studentID, l_businessDate, eventNotificationRecord, session, dbSession, inject);
                    tc.commit(session, dbSession);
                }
                
                
                
                try{
                    
                  Result[j]= (Future<String>)eventNotificationProcessing.parallelProcessing(instituteID, l_studentID, l_businessDate);
               
                } catch (EJBException ex) {
                   dispatchFailed=true;
                }
                
                
                if( !dispatchFailed){
                    
                     WorkDispatch dispatch=new WorkDispatch(l_studentID,"WIP");
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
         dbg("end of eventNotification batch--->parallelProcessing");  
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
     
     
     
     
     
     
   
  private void studentIdentification(String instituteID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       ITransactionControlService tc=null;
       try{
           dbg("inside event notification batch studentIdentification");
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IDBTransactionService dbts=inject.getDBTransactionService();
           BusinessService bs=inject.getBusinessService(session);
           tc=inject.getTransactionControlService();
           ArrayList<String>studentList=null;
           
           try{
           
              studentList=bs.getStudentsOfTheInstitute(instituteID, session, dbSession, inject);
           
         }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                 
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_000");  
                          return;
                          
                       }else{
                           throw ex;
                       }
              }
           
           
           dbg("studentList size"+studentList.size());           
           
    
           for(int i=0;i<studentList.size();i++){
               
               String studentID=studentList.get(i);
               
               
               
               try{
//
                             dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 339,instituteID,studentID,l_businessDate,"W"," "," "); 
                             tc.commit(session, dbSession);
               }catch(DBValidationException ex){
                   tc.rollBack(session, dbSession);
                   if(!ex.toString().contains("DB_VAL_009")){
                       throw ex;
                   }else{
                       session.getErrorhandler().removeSessionErrCode("DB_VAL_009");
                   }
               }
               
           }
           
           
           
           dbg("end of class identification");
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
     
     
//     public void processing (String instituteID,String batchName,int no_of_threads)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
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
//        dbg("inside eventNotification batch processing");
//        dbg("instituteID"+instituteID);
//        dbg("batchName"+batchName);
//        dbg("no_of_threads"+no_of_threads);
//        l_businessDate=bs.getCurrentDate();
//        dbg("l_businessDate"+l_businessDate);
//        boolean recordExistence=true;
//        DBRecord batchEODStatusRecord=null;
//        String l_eodStatus=new String();  
//        String startTime=bs.getCurrentDateTime();
//        int maxSequence=0;
//        dbg("startTime"+startTime);
//           
//           try{
//               
//           
//               maxSequence= batchUtil.getBatchMaxSequence(l_businessDate, instituteID, batchName, inject, session, dbSession);
//               
//
//               String l_eodPkey[]={instituteID,batchName,l_businessDate,Integer.toString(maxSequence)};
//               batchEODStatusRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS",l_eodPkey ,session, dbSession,true);
//               l_eodStatus=batchEODStatusRecord.getRecord().get(5).trim();
//        }catch(DBValidationException ex){
//
//            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
//
//                recordExistence=false;
//            }
//        }
//           
//        dbg("l_eodStatus"+l_eodStatus);   
//           
////        if(recordExistence==false||l_eodStatus.equals("F")){
//          int sequence=0; 
//          
//        if(recordExistence==true){   
//          
//            if(l_eodStatus.equals("F")){
//            
//                int batchHistoryMaxSequence=batchUtil.getBatchHistoryMaxSequence(l_businessDate, instituteID, batchName, inject, session, dbSession);
//                
//                if(batchHistoryMaxSequence>10){
//                    return;
//                }
//                
//                sequence=maxSequence;
//                Map<String,String>column_to_Update=new HashMap();
//                column_to_Update.put("6", "W");
//                column_to_Update.put("4", startTime);
//                String[] l_pkey={instituteID,batchName,l_businessDate,Integer.toString(sequence)};
//                dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS", l_pkey, column_to_Update,session); 
//                batchUtil.moveBatchRecordToHistory(instituteID,batchName,l_businessDate,batchEODStatusRecord, session, dbSession, inject);
//            }else{ 
//             
////                int batchMaxSequence=batchUtil.getBatchMaxSequence(l_businessDate, instituteID, batchName, inject, session, dbSession);
////                          
////                String[] l_pkey={instituteID,batchName,l_businessDate,Integer.toString(batchMaxSequence)};
////                String endTime=bs.getCurrentDateTime();
////                Map<String,String>column_to_Update=new HashMap();
////                column_to_Update.put("5", endTime);
////                column_to_Update.put("6", "S");
////                column_to_Update.put("7", Integer.toString(0));
////                column_to_Update.put("8", Integer.toString(0));
////
////                dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS", l_pkey, column_to_Update,session); 
////
////                tc.commit(session, dbSession);
//                return;  
//                
//                
//            } 
//            
//            
//        }else{
//            
//            sequence=1;
//            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 95,instituteID,batchName, l_businessDate,startTime," ","W"," "," "," ",Integer.toString(sequence));   
//            
//        }
//        
//            tc.commit(session, dbSession);
//            
//           
//           
//
//           
//
//           sequentialProcessing(instituteID,l_businessDate,batchName);
//         
//           tc.commit(session, dbSession);
//         
//         
//         
////        }    
//        dbg("end of eventNotification batch processing");
//        }catch(Exception ex){
//           dbg(ex);
//           batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
//        }finally{
//               if(l_session_created_now){    
//                  dbSession.clearSessionObject();
//                  session.clearSessionObject();
//               }
//           }
////       
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
//           dbg("inside eventNotification batch--->sequentialProcessing");
//           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//           IBDProperties i_db_properties=session.getCohesiveproperties();
//           IDBTransactionService dbts=inject.getDBTransactionService();
//           batchUtil=inject.getBatchUtil(session);
//           tc=inject.getTransactionControlService();
//           BusinessService bs=inject.getBusinessService(session);
//           String previousDate=bs.getPreviousDate();
//           String currentDate=bs.getCurrentDate();
//           dbg("before reading eventNotification table");
//           Map<String,DBRecord>eventNotificationEodMap=null;
//           IAmazonSMSService smsService=inject.getAmazonSMSService();
//           IAmazonEmailService emailService=inject.getAmazonEmailService();
//           ILockService lock=inject.getLockService();
//           BSValidation bsv=inject.getBsv(session);
//           List<DBRecord>unProcessedRecords=null;
//           
//           
//           try{
//           
//              eventNotificationEodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"INSTITUTE", "TODAY_NOTIFICATION", session, dbSession);
//              
//              unProcessedRecords=eventNotificationEodMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals("U")).collect(Collectors.toList());
//              
//              
//              if(unProcessedRecords.isEmpty()){
//                  
//                  
//                  int batchMaxSequence=batchUtil.getBatchMaxSequence(l_businessDate, instituteID, batchName, inject, session, dbSession);
//                          
//                          String[] l_pkey={instituteID,batchName,l_businessDate,Integer.toString(batchMaxSequence)};
//                          String endTime=bs.getCurrentDateTime();
//                          Map<String,String>column_to_Update=new HashMap();
//                          column_to_Update.put("5", endTime);
//                          column_to_Update.put("6", "S");
//                          column_to_Update.put("7", Integer.toString(0));
//                          column_to_Update.put("8", Integer.toString(0));
//             
//                          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS", l_pkey, column_to_Update,session); 
//                          
//                          tc.commit(session, dbSession);
//                          return;  
//                  
//              }
//              
//              
//              
//              
//           }catch(DBValidationException ex){
//                       
//                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
//                 
//                          session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
//                          session.getErrorhandler().removeSessionErrCode("DB_VAL_000");  
//                          int batchMaxSequence=batchUtil.getBatchMaxSequence(l_businessDate, instituteID, batchName, inject, session, dbSession);
//                          
//                          String[] l_pkey={instituteID,batchName,l_businessDate,Integer.toString(batchMaxSequence)};
//                          String endTime=bs.getCurrentDateTime();
//                          Map<String,String>column_to_Update=new HashMap();
//                          column_to_Update.put("5", endTime);
//                          column_to_Update.put("6", "S");
//                          column_to_Update.put("7", Integer.toString(0));
//                          column_to_Update.put("8", Integer.toString(0));
//             
//                          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS", l_pkey, column_to_Update,session); 
//                          
//                          tc.commit(session, dbSession);
//                          return;  
//                        }else{
//
//                              throw ex;
//                        }
//                       
//                   }
//           
//           
////           List<DBRecord>filteredEventNotificationList=eventNotificationEodMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
//           dbg("after reading eventNotification table");
////           dbg("filteredEventNotificationList size "+filteredEventNotificationList.size());
//           dbg("eventNotificationEodMap size"+eventNotificationEodMap.size()); 
//           Iterator<DBRecord>eventNotificationIterator=eventNotificationEodMap.values().iterator();
//           
//           while(eventNotificationIterator.hasNext()){
//               
//               ArrayList<String>value=eventNotificationIterator.next().getRecord();
//               
//               String studentID=value.get(0).trim();
//               String endPoint=value.get(1).trim();
//               String media=value.get(2).trim();
//               String message=value.get(3).trim();
//               String[] contractPkey={instituteID};
//            String pkey=dbSession.getIibd_file_util().formingPrimaryKey(contractPkey);
//            String fileName="APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive";
//            String tableName="CONTRACT_MASTER";
//            
//            
//            try{
//            
//                  if(media.equals("S")){
//
//                      if(dbts.getImplictRecordLock(fileName,tableName,pkey,this.session)==true){ 
//
//                              if( lock.isSameSessionRecordLock(fileName,tableName,pkey, this.session)){
//
//                             if(bsv.smsUsageValidation(instituteID, session, dbSession, inject)) {  
//
//                                smsService.sendSMS(message, endPoint, session,instituteID);
//                                bs.updateSMSUsage(instituteID, session, dbSession, inject);
//
//                                this.updateEventNotificationStatus(instituteID, studentID, endPoint, "S", session, dbSession, inject);
//
//                             }else {
//
//                                 this.updateEventNotificationStatus(instituteID, studentID, endPoint, "F", session, dbSession, inject);
//                             }
//                              }
//                          }
//
//
//                  }else{
//
//                    if(dbts.getImplictRecordLock(fileName,tableName,pkey,this.session)==true){ 
//
//                        if( lock.isSameSessionRecordLock(fileName,tableName,pkey, this.session)){
//
//                             if(bsv.emailUsageValidation(instituteID, session, dbSession, inject)) {    
//
//                                      Email emailObj=this.getEmailObject(instituteID, message, endPoint);
//                                      emailService.sendEmail(emailObj, session);
//                                      bs.updateEmailUsage(instituteID, session, dbSession, inject);
//                                      this.updateEventNotificationStatus(instituteID, studentID, endPoint, "S", session, dbSession, inject);
//
//                             }else{
//                                 
//                                 this.updateEventNotificationStatus(instituteID, studentID, endPoint, "F", session, dbSession, inject);
//                                 
//                             }
//
//                     }
//
//                    }
//
//                  }
//               
//           
//            }catch(Exception ex){
//                dbg(ex);
//                tc.rollBack(session, dbSession);
//                
//                this.updateEventNotificationStatus(instituteID, studentID, endPoint, "F", session, dbSession, inject);
//                
//            }
//              
//              
//              
//              
//              
//              
//           }
//           
//           
//           
//           
//           
// 
//          dbg("end of eventNotification batch--->sequentialProcessing");  
//         batchUtil.batchSucessHandler(instituteID, l_businessDate, batchName, inject, session, dbSession);
//        }catch(DBValidationException ex){
//          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
////        }catch(BSValidationException ex){
////          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
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
//  private Email getEmailObject(String instituteID,String message,String toEmail)throws BSProcessingException,BSValidationException{
//         try{
//             
//           dbg("inside NotificationService--->getEmailObject");
//           IPDataService pds=inject.getPdataservice();
//           IBDProperties i_db_properties=session.getCohesiveproperties();
//           BusinessService bs=inject.getBusinessService(session);
//           String instituteName=bs.getInstituteName(instituteID, session, dbSession, inject);
//           dbg("message"+message);
//
//                String[] pkey={instituteID};
//                ArrayList<String>contractList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",pkey);
//           
//           String fromEmail=contractList.get(9).trim().replace("AATT;", "@");
//           String subject="Email Notification from "+instituteName;
//           String textBody="This is Email Notification from "+instituteName;
//           String htmlBody="<h1> Email Notification from "+instituteName+"</h1>"
//                          + "<p>"+message
//                              +"</p>" 
//                              +"<p> <u>This is Auto generated email , please do not reply</u></p>"
//                              ;
//                
//            Email email=new  Email();
//            
//            email.setFromEmail(fromEmail);
//            email.setToEmail(toEmail);
//            email.setHtmlBody(htmlBody);
//            email.setSubject(subject);
//            email.setTextBody(textBody);
//                
//            
//        dbg("End of NotificationService--->getEmailObject");
//        
//           return email;
//         }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//
//        }
//        
//        
//    }
//  
//  
//    public void updateEventNotificationStatus(String instituteID,String studentID,String endPoint,String status,CohesiveSession session,DBSession dbSession,AppDependencyInjection inject)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
//        
//        try{
//            
//            dbg("inside updateRecordInTodayNotification");
//            ITransactionControlService tc=inject.getTransactionControlService();
//            IBDProperties i_db_properties=session.getCohesiveproperties();
//            IDBTransactionService dbts=inject.getDBTransactionService();
//            BusinessService bs=inject.getBusinessService(session);
//            String currentDate=bs.getCurrentDate();
//            String[] pkey={studentID,endPoint};
//
//           
//
//
//
//
//                 Map<String,String>l_columnToUpdate=new HashMap();
//                 
//                 l_columnToUpdate.put("5", status);
//
//
//
//                 dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"INSTITUTE", "TODAY_NOTIFICATION", pkey, l_columnToUpdate, session);
//
//            tc.commit(session, dbSession);
//        }catch(DBValidationException ex){
//            dbg(ex);
//            throw ex;
//        }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch(Exception ex){
//            dbg(ex);
//           throw new BSProcessingException(ex.toString());
//        }
//    }
    
  
  
  
  
  
  
  
   
     public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
    
}
