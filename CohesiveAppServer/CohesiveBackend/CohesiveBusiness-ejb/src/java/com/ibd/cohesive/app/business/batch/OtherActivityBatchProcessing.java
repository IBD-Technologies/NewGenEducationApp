/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch;

import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
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
public class OtherActivityBatchProcessing implements IOtherActivityBatchProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public OtherActivityBatchProcessing() throws NamingException {
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
        dbg("inside otherActivity batch processing");
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
            
         boolean notStatus=  otherActivityIdentification(instituteID,l_businessDate);
           
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
        dbg("end of otherActivity batch processing");
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
          
           dbg("inside otherActivity batch--->sequentialProcessing");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IOtherActivityProcessing otherActivityProcessing=inject.getOtherActivityProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           batchUtil=inject.getBatchUtil(session);
           tc=inject.getTransactionControlService();
           BusinessService bs=inject.getBusinessService(session);
           
           dbg("before reading otherActivity table");
           Map<String,DBRecord>otherActivityEodMap=null;
           
           try{
           
              otherActivityEodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "OTHER_ACTIVITY_EOD_STATUS", session, dbSession);
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
           
           
//           List<DBRecord>filteredOtherActivityList=otherActivityEodMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
           dbg("after reading otherActivity table");
//           dbg("filteredOtherActivityList size "+filteredOtherActivityList.size());
           Iterator<DBRecord>otherActivityIterator=otherActivityEodMap.values().iterator();
           List<DBRecord>otherActivityList=new ArrayList();
           
           while(otherActivityIterator.hasNext()){
//           for(int i=0;i<filteredOtherActivityList.size();i++){
               
               DBRecord otherActivityRecord=otherActivityIterator.next();
               String l_otherActivityID=otherActivityRecord.getRecord().get(1).trim();
               String l_status=otherActivityRecord.getRecord().get(3).trim();
               int lfailCount=0;
               try{
               
                 lfailCount=Integer.parseInt(otherActivityRecord.getRecord().get(6).trim());
               
               }catch(NumberFormatException ex){
                   
                   lfailCount=0;
               }
               
               dbg("lfailCount"+lfailCount);
               dbg("l_status"+l_status);
               if(l_status.equals("F")||lfailCount>0){
                   
                    int otherActivityHistoryMax=batchUtil.getOtherActivityHistoryMaxSequence(l_businessDate, instituteID, l_otherActivityID, inject, session, dbSession);
                    
                    if(otherActivityHistoryMax<=10){
                        
                        otherActivityList.add(otherActivityRecord);
                    }
                   
               }
               else if(l_status.equals("W")){
                   
                   otherActivityList.add(otherActivityRecord);
               }
               
           
           }
           
           for(int i=0;i<otherActivityList.size();i++){
               DBRecord otherActivityRecord=otherActivityList.get(i);
               String l_otherActivityID=otherActivityRecord.getRecord().get(1).trim();
               String l_groupID=otherActivityRecord.getRecord().get(7).trim();
               String l_status=otherActivityRecord.getRecord().get(3).trim();
//               String l_sequenceNo=otherActivityRecord.getRecord().get(9).trim();
               
               dbg("inside otherActivitylist iteration");
               dbg("l_otherActivityID"+l_otherActivityID);
               dbg("l_groupID"+l_groupID);
               dbg("l_status"+l_status);
               
                if(l_status.equals("F")){
            
//                    int otherActivityHistoryMax=batchUtil.getOtherActivityHistoryMaxSequence(l_businessDate, instituteID, l_otherActivityID, inject, session, dbSession);
//                    
//                    if(otherActivityHistoryMax>10){
//                        continue;
//                    }
                    
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update.put("4", "W");
                    String[] l_pkey={instituteID,l_otherActivityID,l_businessDate};
                    dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "OTHER_ACTIVITY_EOD_STATUS", l_pkey, column_to_Update,session); 
                    batchUtil.moveOtherActivityRecordToHistory(instituteID,l_otherActivityID,l_businessDate,otherActivityRecord, session, dbSession, inject);
                    tc.commit(session, dbSession);
                }
                
                    dbg("Before calling otherActivity processing for "+l_otherActivityID);
                otherActivityProcessing.processing(instituteID,l_otherActivityID,l_groupID,l_businessDate,session);
           }
          dbg("end of otherActivity batch--->sequentialProcessing");  
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
           dbg("inside otherActivity batch parallel Processing");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IOtherActivityProcessing otherActivityProcessing=inject.getOtherActivityProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           tc=inject.getTransactionControlService();
           batchUtil=inject.getBatchUtil(session);
           BusinessService bs=inject.getBusinessService(session);
           
           dbg("before reading otherActivity table");
           Map<String,DBRecord>otherActivityEodMap=null;
           try{
           
           
                otherActivityEodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "OTHER_ACTIVITY_EOD_STATUS", session, dbSession);
          
           
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
           
           
//           List<DBRecord>filteredOtherActivityList=otherActivityEodMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
           dbg("after reading otherActivity table");
//           dbg("filteredOtherActivityList size "+filteredOtherActivityList.size());
           Iterator<DBRecord>otherActivityIterator=otherActivityEodMap.values().iterator();
           List<DBRecord>otherActivityList=new ArrayList();
           
           while(otherActivityIterator.hasNext()){ 
               
               DBRecord otherActivityRecord=otherActivityIterator.next();
               String l_otherActivityID=otherActivityRecord.getRecord().get(1).trim();
               String l_status=otherActivityRecord.getRecord().get(3).trim();
               dbg("l_otherActivityID"+l_otherActivityID);
               dbg("l_status"+l_status);
               
               int lfailCount=0;
               try{
               
                 lfailCount=Integer.parseInt(otherActivityRecord.getRecord().get(6).trim());
               
                 
               }catch(NumberFormatException ex){
                   
                   lfailCount=0;
               }
               dbg("lfailCount"+lfailCount);
               
               if(l_status.equals("F")||lfailCount>0){
                   
                    int otherActivityHistoryMax=batchUtil.getOtherActivityHistoryMaxSequence(l_businessDate, instituteID, l_otherActivityID, inject, session, dbSession);
                    
                    if(otherActivityHistoryMax<=10){
                        
                        otherActivityList.add(otherActivityRecord);
                    }
                   
               }
               else if(l_status.equals("W")){
                   
                   otherActivityList.add(otherActivityRecord);
               }
               
           
           }
           
//           Future<String>[] Result=new Future[no_of_threads];
            Future<String>[] Result;
           Map<Integer,WorkDispatch>dispatchedJobs=new HashMap();
           boolean dispatchFailed=false;
           int no_of_execution;
           
           if(otherActivityList.size()%no_of_threads==0){
               
               no_of_execution=otherActivityList.size()/no_of_threads;
               
           }else{
               
               no_of_execution=(otherActivityList.size()/no_of_threads)+1;
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
               if(i<otherActivityList.size()){
                   if(startIndex==i){
                       j=0;
                   }else{
                       j++;
                   }
               DBRecord otherActivityRecord=otherActivityList.get(i);
               String l_otherActivityID=otherActivityRecord.getRecord().get(1).trim();
               String l_groupID=otherActivityRecord.getRecord().get(7).trim();
               String l_status=otherActivityRecord.getRecord().get(3).trim();
//               String l_sequenceNo=otherActivityRecord.getRecord().get(9).trim();
               dbg("inside otherActivitylist iteration");
               dbg("l_otherActivityID"+l_otherActivityID);
               dbg("l_groupID"+l_groupID);
               dbg("l_status"+l_status); 
               
               
                
                if(l_status.equals("F")){
            
                   
                    
                    
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update.put("4", "W");
                    String[] l_pkey={instituteID,l_otherActivityID,l_businessDate};
                    dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "OTHER_ACTIVITY_EOD_STATUS", l_pkey, column_to_Update,session); 
                    batchUtil.moveOtherActivityRecordToHistory(instituteID,l_otherActivityID,l_businessDate,otherActivityRecord, session, dbSession, inject);
                    tc.commit(session, dbSession);
                }
                
                
                
                try{
                    
                  Result[j]= (Future<String>)otherActivityProcessing.parallelProcessing(instituteID, l_otherActivityID, l_groupID, l_businessDate);
               
                } catch (EJBException ex) {
                   dispatchFailed=true;
                }
                
                
                if( !dispatchFailed){
                    
                     WorkDispatch dispatch=new WorkDispatch(l_otherActivityID,"WIP");
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
         dbg("end of otherActivity batch--->parallelProcessing");  
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
     
     
     
     
     
     
   
   private boolean otherActivityIdentification(String instituteID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       ITransactionControlService tc=null;
       try{
           dbg("inside otherActivity batch otherActivityIdentification");
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
          // IPDataService pds=inject.getPdataservice();
           IDBTransactionService dbts=inject.getDBTransactionService();
           BusinessService bs=inject.getBusinessService(session);
           tc=inject.getTransactionControlService();
           Map<String,DBRecord>l_otherActivityMap=null;
           try{
           
           l_otherActivityMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY","IVW_OTHER_ACTIVITY", session, dbSession);
           dbg("l_otherActivityMap size"+l_otherActivityMap.size());
           
           }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                 
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_000");  
                          return false;  
                        }else{

                              throw ex;
                        }
                       
                   }
           
           
           
           
           
           
           List<DBRecord>l_otherActivityRecords=l_otherActivityMap.values().stream().filter(rec->rec.getRecord().get(13).trim().equals("O")&&rec.getRecord().get(14).trim().equals("A")).collect(Collectors.toList());
           dbg("l_otherActivityRecords size"+l_otherActivityRecords.size());    
           ArrayList<DBRecord>filteredNotififications=new ArrayList();
           
           for(int i=0;i<l_otherActivityRecords.size();i++){
               
               DBRecord otherActivityRecord=l_otherActivityRecords.get(i);
               
               String checkerDateStamp=l_otherActivityRecords.get(i).getRecord().get(12).trim();
               String checkerDate=bs.getDateFromDateStamp(checkerDateStamp);
               
//               if(l_businessDate.equals(checkerDate)&&l_businessDate.equals(instant)){  
                    if(l_businessDate.equals(checkerDate)){  
                   
                   
                   
                   filteredNotififications.add(otherActivityRecord);
                   
                   
                   
                   
               }
           }
           
           
           
           if(filteredNotififications.isEmpty()){
               
               return false;
           }
           
           
           
           
           for(int i=0;i<filteredNotififications.size();i++){
               
               String otherActivityID=filteredNotififications.get(i).getRecord().get(2).trim();
               String groupID=filteredNotififications.get(i).getRecord().get(1).trim();
//               String instant=filteredNotififications.get(i).getRecord().get(19).trim();
   
               
               dbg("inside otherActivity records iteration");
               dbg("otherActivityID"+otherActivityID);
//               dbg("checkerDateStamp"+checkerDateStamp);
//               dbg("checkerDate"+checkerDate);
               dbg("groupID"+groupID);
               
//               if(l_businessDate.equals(instant)){
               
               try{
                   
                 dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 111,instituteID,otherActivityID,l_businessDate,"W"," "," "," ",groupID," "," "); 
                 
                 
                 tc.commit(session, dbSession);
               }catch(DBValidationException ex){
                   tc.rollBack(session, dbSession);
                   if(!ex.toString().contains("DB_VAL_009")){
                       throw ex;
                   }
                   
                   
//               }
           }
               
               
                try{
                   
                 dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","INSTITUTE",316,otherActivityID,"C"); 
                 
                 
                 tc.commit(session, dbSession);
               }catch(DBValidationException ex){
                   tc.rollBack(session, dbSession);
                   if(!ex.toString().contains("DB_VAL_009")){
                       throw ex;
                   }
                   
                }
               
               
               
               
               
           }
           
           
           
           dbg("end of otherActivity identification");
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
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
  /* public void processing (String instituteID,String batchName,int no_of_threads)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
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
        dbg("inside otherActivity batch processing");
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
            
           otherActivityIdentification(instituteID,l_businessDate);
           
           dbg("no_of_threads"+no_of_threads);
            if(no_of_threads==0){

                sequentialProcessing(instituteID,l_businessDate,batchName);
            }else{

                parallelProcessing(instituteID,l_businessDate,batchName,no_of_threads);
            }
           
        }    
        dbg("end of other activity batch processing");
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
          
           dbg("inside otherActivity batch--->sequentialProcessing");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IOtherActivityProcessing otherActivityProcessing=inject.getOtherActivityProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           batchUtil=inject.getBatchUtil(session);
           tc=inject.getTransactionControlService();
           
           dbg("before reading otherActivity table");
           Map<String,DBRecord>otherActivityEodMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "OTHER_ACTIVITY_EOD_STATUS", session, dbSession);
           List<DBRecord>otherActivityList=otherActivityEodMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
           dbg("after reading otherActivity table");
           dbg("otherActivityList size "+otherActivityList.size());
           
           for(int i=0;i<otherActivityList.size();i++){
               DBRecord otherActivityRecord=otherActivityList.get(i);
               String l_otherActivityID=otherActivityRecord.getRecord().get(1).trim();
               String l_groupID=otherActivityRecord.getRecord().get(7).trim();
               String l_status=otherActivityRecord.getRecord().get(3).trim();
               
               dbg("inside otherActivitylist iteration");
               dbg("l_otherActivityID"+l_otherActivityID);
               dbg("l_groupID"+l_groupID);
               dbg("l_status"+l_status);
               
                if(l_status.equals("F")){
            
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update.put("4", "W");
                    String[] l_pkey={instituteID,l_otherActivityID,l_businessDate};
                    dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "OTHER_ACTIVITY_EOD_STATUS", l_pkey, column_to_Update,session); 
                    batchUtil.moveOtherActivityRecordToHistory(instituteID,l_otherActivityID,l_businessDate,otherActivityRecord, session, dbSession, inject);
                    tc.commit(session, dbSession);
                }
                
                    dbg("Before calling otherActivity processing for "+l_otherActivityID);
                otherActivityProcessing.processing(instituteID,l_otherActivityID,l_groupID,l_businessDate,session);
           }
          dbg("end of otherActivity batch--->sequentialProcessing");  
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
           dbg("inside otherActivity batch parallel Processing");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
            IOtherActivityProcessing otherActivityProcessing=inject.getOtherActivityProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           tc=inject.getTransactionControlService();
           batchUtil=inject.getBatchUtil(session);
           
           dbg("before reading otherActivity table");
           Map<String,DBRecord>otherActivityEodMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "OTHER_ACTIVITY_EOD_STATUS", session, dbSession);
           List<DBRecord>otherActivityList=otherActivityEodMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
           dbg("after reading otherActivity table");
           dbg("otherActivityList size "+otherActivityList.size());
//           Future<String>[] Result=new Future[no_of_threads];
            Future<String>[] Result;
           Map<Integer,WorkDispatch>dispatchedJobs=new HashMap();
           boolean dispatchFailed=false;
           int no_of_execution;
           
           if(otherActivityList.size()%no_of_threads==0){
               
               no_of_execution=otherActivityList.size()/no_of_threads;
               
           }else{
               
               no_of_execution=(otherActivityList.size()/no_of_threads)+1;
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
               if(i<otherActivityList.size()){
                   if(startIndex==i){
                       j=0;
                   }else{
                       j++;
                   }
               DBRecord otherActivityRecord=otherActivityList.get(i);
               String l_otherActivityID=otherActivityRecord.getRecord().get(1).trim();
               String l_groupID=otherActivityRecord.getRecord().get(7).trim();
               String l_status=otherActivityRecord.getRecord().get(3).trim();
               dbg("inside otherActivitylist iteration");
               dbg("l_otherActivityID"+l_otherActivityID);
               dbg("l_groupID"+l_groupID);
               dbg("l_status"+l_status); 
               
               
                
                if(l_status.equals("F")){
            
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update.put("4", "W");
                    String[] l_pkey={instituteID,l_otherActivityID,l_businessDate};
                    dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "OTHER_ACTIVITY_EOD_STATUS", l_pkey, column_to_Update,session); 
                    batchUtil.moveOtherActivityRecordToHistory(instituteID,l_otherActivityID,l_businessDate,otherActivityRecord, session, dbSession, inject);
                    tc.commit(session, dbSession);
                }
                
                
                
                try{
                    
                  Result[j]= (Future<String>)otherActivityProcessing.parallelProcessing(instituteID, l_otherActivityID, l_groupID, l_businessDate);
               
                } catch (EJBException ex) {
                   dispatchFailed=true;
                }
                
                
                if( !dispatchFailed){
                    
                     WorkDispatch dispatch=new WorkDispatch(l_otherActivityID,"WIP");
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
         dbg("end of otherActivity batch--->parallelProcessing");  
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
     
     
     
     
     
     
   
   private void otherActivityIdentification(String instituteID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       ITransactionControlService tc=null;
       try{
           dbg("inside otherActivity batch otherActivityIdentification");
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IDBTransactionService dbts=inject.getDBTransactionService();
           BusinessService bs=inject.getBusinessService(session);
           tc=inject.getTransactionControlService();
           Map<String,DBRecord>l_otherActivityMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE","IVW_OTHER_ACTIVITY", session, dbSession);
           dbg("l_otherActivityMap size"+l_otherActivityMap.size());
           List<DBRecord>l_otherActivityRecords=l_otherActivityMap.values().stream().filter(rec->rec.getRecord().get(13).trim().equals("O")&&rec.getRecord().get(14).trim().equals("A")).collect(Collectors.toList());
           dbg("l_otherActivityRecords size"+l_otherActivityRecords.size());           
           
           for(int i=0;i<l_otherActivityRecords.size();i++){
               
               String otherActivityID=l_otherActivityRecords.get(i).getRecord().get(2).trim();
               String checkerDateStamp=l_otherActivityRecords.get(i).getRecord().get(12).trim();
               String checkerDate=bs.getDateFromDateStamp(checkerDateStamp);
               String groupID=l_otherActivityRecords.get(i).getRecord().get(1).trim();
               
               dbg("inside otherActivity records iteration");
               dbg("otherActivityID"+otherActivityID);
               dbg("checkerDateStamp"+checkerDateStamp);
               dbg("checkerDate"+checkerDate);
               dbg("groupID"+groupID);
               
               if(l_businessDate.equals(checkerDate)){
               
               try{
                   
                 dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 111,instituteID,otherActivityID,l_businessDate,"W"," "," "," ",groupID," "," "); 
                 tc.commit(session, dbSession);
               }catch(DBValidationException ex){
                   
                   if(!ex.toString().contains("DB_VAL_009")){
                       throw ex;
                   }
               }
           }
           }
           
           
           
           dbg("end of otherActivity identification");
        }catch(DBValidationException ex){
          throw ex;
        }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
       
       
   }*/
   
  
   
   
  
   
   
   
     public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
}
