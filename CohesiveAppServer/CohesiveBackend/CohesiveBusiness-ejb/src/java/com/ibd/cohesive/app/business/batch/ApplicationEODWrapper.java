/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch;

import com.ibd.cohesive.app.business.batch.archival.profile.IUProfileArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archivalRecovery.IArchivalRecoveryBatchProcessing;
import com.ibd.cohesive.app.business.batch.defragmentation.IDefragmentationBatchProcessing;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.naming.NamingException;
//import org.jboss.ejb3.annotation.TransactionTimeout;
/**
 *
 * @author DELL
 */
@Startup
@Singleton
//@TransactionTimeout(value = 10, unit = TimeUnit.SECONDS)
public class ApplicationEODWrapper implements IApplicationEODWrapper{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    @Resource
    TimerService timerService;
    private AtomicBoolean busy = new AtomicBoolean(false);
    private Object lock1 = new Object();
    
    public ApplicationEODWrapper() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
    
    @PostConstruct
    public void setTimer() {
//        dbg("Setting a programmatic timeout for "   + " milliseconds from now.");
     boolean l_session_created_now=false;
      try{
        session.createSessionObject();
        l_session_created_now=session.isI_session_created_now();
        dbg("inside setTimer");
        String reportinDB=session.getCohesiveproperties().getProperty("REPORTING_DB");
        
        //        
        if(reportinDB.equals("NO")){
        
//            String eodTimeHour=session.getCohesiveproperties().getProperty("EOD_TIMING_HOUR");
//            String eodTimeMin=session.getCohesiveproperties().getProperty("EOD_TIMING_MIN");
//            dbg("eodTimeHour"+eodTimeHour);
            ScheduleExpression schedule = new ScheduleExpression();
            schedule.dayOfWeek("1-7");
//            schedule.hour(Integer.parseInt(eodTimeHour));
//            schedule.minute(Integer.parseInt(eodTimeMin));
            

            String batchStartingHour=session.getCohesiveproperties().getProperty("BATCH_STARTING_HOUR");
            String batchEndingHour=session.getCohesiveproperties().getProperty("BATCH_ENDING_HOUR");
            String hourFrequency=session.getCohesiveproperties().getProperty("HOUR_FREQUENCY");
            String minFrequency=session.getCohesiveproperties().getProperty("MIN_FREQUENCY");
            
            if(!minFrequency.equals("NO")){

                schedule.hour(batchStartingHour+"-"+batchEndingHour);

                schedule.minute("*/"+minFrequency);
            
            }else{
                
                schedule.hour(batchStartingHour+"/"+hourFrequency);
                
            }
            
                  
      TimerConfig config=new TimerConfig();
            config.setPersistent(false);
            Timer timer = timerService.createCalendarTimer(schedule,config);
        
        }
      }catch(Exception ex){
          dbg(ex);
      }finally{
          if(l_session_created_now){    
                  session.clearSessionObject();
               }
      }
    }
    
    @Timeout
   public void batchWrapper() {
    BatchUtil batchUtil=null;   
    String currentDate=null;
    ITransactionControlService tc=null;
    boolean l_session_created_now=false;
    Properties batch=null;
    try{  
        
    synchronized(lock1){
            if (!busy.compareAndSet(false, true)) {
               return;
           }
        }
//    System.out.println("TimerBean: timeout occurred");
   session.createSessionObject();
    dbSession.createDBsession(session);
    l_session_created_now=session.isI_session_created_now();
   batch  = new Properties();
   batch.load(new FileInputStream("/cohesive/dbhome/BATCH/batch.properties"));
    if (batch.getProperty("STOP").equals("NO"))
    {
         
    
    IBDProperties i_db_properties=session.getCohesiveproperties();
    IDBReadBufferService readBuffer=inject.getDBReadBufferService();
    BusinessService bs=inject.getBusinessService(session);
    batchUtil=inject.getBatchUtil(session);
    IDBTransactionService dbts=inject.getDBTransactionService();
    IUProfileArchBatchProcessing userProfile=inject.getUserProfileArchBatch();
    IDefragmentationBatchProcessing defragment=inject.getDefragmentationBatch();
    IArchivalRecoveryBatchProcessing archRecovery=inject.getArchivalRecoveryBatch();
    tc=inject.getTransactionControlService();
    dbg("inside app eod wrapper");
    currentDate=bs.getCurrentDate();
    String startTime=bs.getCurrentDateTime();
    DBRecord appStatusRecord=null;
    String l_eodStatus=new String();
    boolean recordExistence=true;
    int maxSequence=0;
       try{
          
           
           maxSequence= batchUtil.getAppMaxSequence(currentDate, inject, session, dbSession);
           
           String[] appPkey={currentDate,Integer.toString(maxSequence)};
           appStatusRecord=readBuffer.readRecord("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate, "BATCH", "APP_EOD_STATUS",appPkey ,session, dbSession,true);
           l_eodStatus=appStatusRecord.getRecord().get(3).trim();
        }catch(DBValidationException ex){

            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){

                recordExistence=false;
            }
        }
    
       dbg("recordExistence"+recordExistence);
       dbg("eodStatus"+l_eodStatus);
       
       
//    if(recordExistence==false||l_eodStatus.equals("F")){
        
int sequence=0; 
     if(recordExistence==true){

        
                
        if(l_eodStatus.equals("F")){
            
            int appHistoryMaxSequence=batchUtil.getAppHistoryMaxSequence(currentDate, inject, session, dbSession);
            
            if(appHistoryMaxSequence>10){
                dbg("max retry completed");
                return;
            }
            
            
            sequence=maxSequence;
            Map<String,String>column_to_Update=new HashMap();
            column_to_Update.put("4", "W");
            String[] l_pkey={currentDate,Integer.toString(sequence)};
            dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate, "BATCH", "APP_EOD_STATUS", l_pkey, column_to_Update,session); 
            //delete
            
            batchUtil.moveAppRecordToHistory(currentDate,appStatusRecord, session, dbSession, inject);
            
            
            
            
            
        }else{
        
            sequence=maxSequence+1;
            
            dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate, "BATCH", 97, currentDate,startTime," ","W"," "," "," ",Integer.toString(sequence));
        }
        
     }else{
        
            sequence=1;
            
            dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate, "BATCH", 97, currentDate,startTime," ","W"," "," "," ",Integer.toString(sequence));
        } 
        
        
        tc.commit(session, dbSession);
        int noOfThreads=Integer.parseInt(i_db_properties.getProperty("INS_EOD_PARALLEL_NO_OF_THREADS"));
        dbg("noOfThreads"+noOfThreads);
            if(noOfThreads==0){

                sequentialProcessing();
            }else{

                parellelProcessing(noOfThreads);
            }
        
//        }
//          String[] l_pkey={"ArchivalRecovery"};
//          DBRecord batchConfigRecord=readBuffer.readRecord("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate, "BATCH", "BATCH_CONFIG",l_pkey ,session, dbSession,true);
//    
//          int no_of_threads= Integer.parseInt(batchConfigRecord.getRecord().get(7).trim());    
//
//
//        archRecovery.processing(currentDate, "ArchivalRecovery", no_of_threads,session);
        dbg("end of  app eod wrapper");
    }
    
    
        }catch(Exception ex){
           dbg(ex);
           batchUtil.appErrorHandler(currentDate, ex, inject, session, dbSession);
        }finally {
               batch.clear();
               busy.set(false);
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
            } 
            
    }
   
   public void sequentialProcessing(){
       BatchUtil batchUtil=null;   
       String currentDate=null;
       ITransactionControlService tc=null;
       try{
           dbg("inside applicationEODWrapper--->sequentialProcessing");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IPDataService pds=inject.getPdataservice();
           IInstituteEODWrapper instituteWrapper=inject.getInstituteEODWrapper();
           BusinessService bs=inject.getBusinessService(session);
           IBDProperties i_db_properties=session.getCohesiveproperties();
           batchUtil=inject.getBatchUtil(session);
           currentDate=bs.getCurrentDate();
           dbg("before institute table read");
         
//         Map<String,DBRecord>instituteMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE", "INSTITUTE_MASTER", session, dbSession);
     
           Map<String,ArrayList<String>>instituteMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE", "INSTITUTE_MASTER", session, dbSession);
           
           List<ArrayList<String>>instituteList=instituteMap.values().stream().filter(rec->!rec.get(0).trim().equals("System")&&rec.get(7).trim().equals("O")&&rec.get(8).trim().equals("A")).collect(Collectors.toList());
           dbg("after institute table read");
           dbg("instituteList size"+instituteList.size());
           
            for(int i=0;i<instituteList.size();i++){
               
                String l_instituteID=instituteList.get(i).get(0).trim();
                dbg("inside instituteIteration l_instituteID"+l_instituteID);

//                if(!l_instituteID.equals("System")){
                
                
                   instituteWrapper.batchWrapper(l_instituteID,session);
                   
//                }

             }
        
        batchUtil.appSuccessHandler(currentDate, inject, session, dbSession);
        dbg("end of applicationEODWrapper--->sequentialProcessing");
       }catch(DBValidationException ex){
            batchUtil.appErrorHandler(currentDate, ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.appErrorHandler(currentDate, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.appErrorHandler(currentDate, ex, inject, session, dbSession);
        }

   }
   
   
   
   public void parellelProcessing(int noOfThreads) {

       BatchUtil batchUtil=null;   
       String currentDate=null;
       ITransactionControlService tc=null;
       try{
           dbg("inside AppEodWrapper parellel processing");
           IBDProperties i_db_properties=session.getCohesiveproperties();   
           Future<String>[] Result;
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IPDataService pds=inject.getPdataservice();
           IInstituteEODWrapper instituteWrapper=inject.getInstituteEODWrapper();
           BusinessService bs=inject.getBusinessService(session);
           batchUtil=inject.getBatchUtil(session);
           IDBTransactionService dbts=inject.getDBTransactionService();
           tc=inject.getTransactionControlService();
           currentDate=bs.getCurrentDate();
           dbg("currentDate"+currentDate);
           boolean dispatchFailed=false;
           Map<String,ArrayList<String>>instituteMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE", "INSTITUTE_MASTER", session, dbSession);
           List<ArrayList<String>>instituteList=instituteMap.values().stream().filter(rec->!rec.get(0).trim().equals("System")&&rec.get(7).trim().equals("O")&&rec.get(8).trim().equals("A")).collect(Collectors.toList());
           dbg("instituteList size"+instituteList.size());
           
           Map<Integer,WorkDispatch>dispatchedJobs=new HashMap();
           int no_of_execution;
           
           if(instituteList.size()%noOfThreads==0){
               
               no_of_execution=instituteList.size()/noOfThreads;
               
           }else{
               
               no_of_execution=(instituteList.size()/noOfThreads)+1;
           }
           
           dbg("no_of_execution"+no_of_execution);
           int executionCount=0;
           
           while(executionCount<no_of_execution){
           
           int startIndex=executionCount*noOfThreads;
           int endIndex=startIndex+noOfThreads-1;
           dbg("startIndex"+startIndex);
           dbg("endIndex"+endIndex);
           Result=new  Future[noOfThreads];
           
           //job submission starts
           dbg("job submission starts");
           for(int i=startIndex;i<=endIndex;i++){
               int j=0;
               if(i<instituteList.size()){
               
                   if(startIndex==i){
                       j=0;
                   }else{
                       j++;
                   }
                   
                String l_instituteID=instituteList.get(i).get(0).trim();
                dbg("l_instituteID"+l_instituteID);
                
                try{
                    
                    
//                     if(!l_instituteID.equals("System")){
                    
                    
                  Result[j]= (Future<String>)instituteWrapper.parellelBatchWrapper(l_instituteID);
               
//                     }
                  
                  
                } catch (EJBException ex) {
                   dispatchFailed=true;
                }
                
                dbg("dispatchFailed"+dispatchFailed);
                if( !dispatchFailed){
                    
                     WorkDispatch dispatch=new WorkDispatch(l_instituteID,"WIP");
                     dispatchedJobs.put(j, dispatch);
                }
                
                
             dispatchFailed=false;   
           
           }
           } 
           dbg("job submission ends");
           //job submission ends
           
             //job status monitoring starts   
             
             dbg("job status monitoring starts");
              int threadCount=0;
               boolean comeOutLoop=false;
               
               dbg("dispatchedJobs.size()"+dispatchedJobs.size());
               if(dispatchedJobs.size()>0){
               
               while(comeOutLoop==false){
                 
                 dbg("inside while comeOutLoop==false")  ;
                   
                 Iterator<Integer> keyIterator=  dispatchedJobs.keySet().iterator();
                 WorkDispatch dispatch=null;
                   
                 
                 while(keyIterator.hasNext()){
                     
                     Integer key= keyIterator.next();
                     dbg("key"+key);
                    
                     dbg("dispatchedJobs.get(key).getResult()");
                     if(dispatchedJobs.get(key).getResult().equals("WIP")){
                     
                     if(Result[key].isDone()){
                         dbg("Result[key].isDone()");
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
                    
                     dbg("end of  while(keyIterator.hasNext())");
                 }
                 
                 dbg("threadCount"+threadCount);
                 dbg("noOfThreads"+noOfThreads);
                if(threadCount==dispatchedJobs.size())
                     comeOutLoop=true;
               }
               Result=null;
               dispatchedJobs.clear();
               } 
           executionCount++;
               }
           dbg("job status monitoring ends");
           
           dbg("end of applicationEODWrapper--->parallelProcessing");
           batchUtil.appSuccessHandler(currentDate, inject, session, dbSession);
        }catch(DBValidationException ex){
            dbg(ex);
            batchUtil.appErrorHandler(currentDate, ex, inject, session, dbSession);
        }catch (DBProcessingException ex) {
            dbg(ex);
            batchUtil.appErrorHandler(currentDate, ex, inject, session, dbSession);
        }catch (Exception ex) {
            dbg(ex);
           batchUtil.appErrorHandler(currentDate, ex, inject, session, dbSession);

        } 
        
    }
   
    
    private void dbg(String p_Value) {
        session.getDebug().dbg(p_Value);

    }

    private void dbg(Exception ex) {
        session.getDebug().exceptionDbg(ex);

    }
}
