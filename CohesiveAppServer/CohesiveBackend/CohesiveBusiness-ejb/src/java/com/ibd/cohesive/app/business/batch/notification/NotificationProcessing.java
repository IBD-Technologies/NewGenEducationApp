/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.notification;

import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Stateless
public class NotificationProcessing implements INotificationProcessing {
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public NotificationProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
    public void processing (String instituteID,String notificationID,String groupID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       BatchUtil batchUtil=null;
       boolean l_session_created_now=false;
       ITransactionControlService tc=null;
       try{
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();   
        tc=inject.getTransactionControlService();
        BusinessService bs=inject.getBusinessService(session);
        String startTime=bs.getCurrentDateTime();
        dbg("startTime"+startTime);
        batchUtil=inject.getBatchUtil(session);
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IPDataService pds=inject.getPdataservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBTransactionService dbts=inject.getDBTransactionService();
            
        dbg("inside notification processing");
        

        Map<String,String>column_to_Update=new HashMap();
        column_to_Update.put("9", startTime);
        String[] l_pkeyy={instituteID,notificationID,l_businessDate};
        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "NOTIFICATION_EOD_STATUS", l_pkeyy, column_to_Update,session); 
        tc.commit(session, dbSession);
                
              studentIdentification(instituteID,l_businessDate,notificationID,groupID) ;   
              
              String[] l_pkey={"Notification"};
              ArrayList<String> batchConfigRecord= pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "BATCH_CONFIG",l_pkey);
              int no_of_threads=Integer.parseInt(batchConfigRecord.get(8).trim());
              
              dbg("no_of_threads"+no_of_threads);
              
            if(no_of_threads==0){

                
                sequentialProcessing(instituteID,notificationID,l_businessDate);
            }else{

                parallelProcessing(instituteID,notificationID,l_businessDate,no_of_threads);
            }
                    
                    
         batchUtil.notificationProcessingSuccessHandler(instituteID, l_businessDate, notificationID, inject, session, dbSession);
        dbg("end of notification processing");
        }catch(DBValidationException ex){
//          tc.rollBack(session, dbSession);
          batchUtil.notificationProcessingErrorHandler(instituteID, l_businessDate, notificationID, ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
//          tc.rollBack(session, dbSession);
          batchUtil.notificationProcessingErrorHandler(instituteID, l_businessDate, notificationID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
//           tc.rollBack(session, dbSession);
           batchUtil.notificationProcessingErrorHandler(instituteID, l_businessDate, notificationID, ex, inject, session, dbSession);
        }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
}

    public void processing(String instituteID,String notificationID,String groupID,String l_businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(instituteID,notificationID,groupID,l_businessDate);
       
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
    
     private void sequentialProcessing(String instituteID,String notificationID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      BatchUtil batchUtil=null;   
      ITransactionControlService tc=null;
      try{
           dbg("inside notificationProcessing -->sequential");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           tc=inject.getTransactionControlService();
           IStudentNotificationProcessing studentOtherActProcessing=inject.getStudentNotificationProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           batchUtil=inject.getBatchUtil(session);
      
            Map<String,DBRecord>studentEodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification"+i_db_properties.getProperty("FOLDER_DELIMITER")+notificationID, "BATCH", "STUDENT_NOTIFICATION_EOD_STATUS", session, dbSession);
//            List<DBRecord>filteredStudentList=studentEodMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals("F")||rec.getRecord().get(4).trim().equals("W")).collect(Collectors.toList());

//            dbg("studentList"+filteredStudentList.size());
            
             List<DBRecord>studentList=new ArrayList();
             Iterator<DBRecord>studentIterator=studentEodMap.values().iterator();
                     
           
//               for(int i=0;i<filteredStudentList.size();i++){
              while(studentIterator.hasNext()){

                   DBRecord notificationRecord=studentIterator.next();
                   String l_studentID=notificationRecord.getRecord().get(2).trim();
                   String l_status=notificationRecord.getRecord().get(4).trim();
                   dbg("l_studentID"+l_studentID);
                   dbg("l_status"+l_status);

                   if(l_status.equals("F")){

                        int notificationHistoryMax=batchUtil.getStudentNotificationHistoryMaxSequence(l_businessDate, instituteID, notificationID, l_studentID, inject, session, dbSession);

                        if(notificationHistoryMax<=5){

                            studentList.add(notificationRecord);
                        }

                   }
                   else if(l_status.equals("W")){

                       studentList.add(notificationRecord);
                   }


               }
            
            
            
                    for(int j=0;j<studentList.size();j++){
                         DBRecord studentNotificationRecord=studentList.get(j);
                         String l_studentID=studentList.get(j).getRecord().get(2).trim();
                         String l_status=studentList.get(j).getRecord().get(4).trim();
                         
                         
                         dbg("l_studentID"+l_studentID);
                         dbg("l_status"+l_status);
                         
                         if(l_status.equals("F")){
                             
//                             batchUtil.getStudentNotificationHistoryMaxSequence(l_businessDate, instituteID, notificationID, l_studentID, inject, session, dbSession);
                             
                             
                            Map<String,String>column_to_Update=new HashMap();
                            column_to_Update=new HashMap();
                            column_to_Update.put("5", "W");
                            String[]l_pkey={instituteID,notificationID,l_studentID,l_businessDate};
                            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification"+i_db_properties.getProperty("FOLDER_DELIMITER")+notificationID, "BATCH", "STUDENT_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
                            batchUtil.moveStudentNotificationRecordToHistory(instituteID,notificationID,l_studentID,l_businessDate,studentNotificationRecord, session, dbSession, inject);
                         }
                         tc.commit(session, dbSession);
                         
                         dbg("before studentAssProcessing call");
                         studentOtherActProcessing.processing(instituteID,l_studentID,notificationID,l_businessDate,session);
                         dbg("after studentAssProcessing call");
                         
                   }
    
    
         batchUtil.notificationProcessingSuccessHandler(instituteID, l_businessDate, notificationID, inject, session, dbSession);
        dbg("end of notificationProcessing -->sequential");
        }catch(DBValidationException ex){
//          tc.rollBack(session, dbSession);
          batchUtil.notificationProcessingErrorHandler(instituteID, l_businessDate, notificationID, ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
//          tc.rollBack(session, dbSession);
          batchUtil.notificationProcessingErrorHandler(instituteID, l_businessDate, notificationID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
//           tc.rollBack(session, dbSession);
           batchUtil.notificationProcessingErrorHandler(instituteID, l_businessDate, notificationID, ex, inject, session, dbSession);
        }
     }
    
    private void parallelProcessing(String instituteID,String notificationID,String l_businessDate,int no_of_threads)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      BatchUtil batchUtil=null; 
      ITransactionControlService tc=null;
      try{
           dbg("inside notificationProcessing -->parallel");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
            IStudentNotificationProcessing studentOtherActProcessing=inject.getStudentNotificationProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           tc=inject.getTransactionControlService();
           batchUtil=inject.getBatchUtil(session);
          Map<String,DBRecord>studentEodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification"+i_db_properties.getProperty("FOLDER_DELIMITER")+notificationID, "BATCH", "STUDENT_NOTIFICATION_EOD_STATUS", session, dbSession);
//            List<DBRecord>filteredStudentList=studentEodMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals("F")||rec.getRecord().get(4).trim().equals("W")).collect(Collectors.toList());

//            dbg("studentList"+filteredStudentList.size());
            
             List<DBRecord>studentList=new ArrayList();
             Iterator<DBRecord>studentIterator=studentEodMap.values().iterator();
                     
           
//               for(int i=0;i<filteredStudentList.size();i++){
              while(studentIterator.hasNext()){

                   DBRecord notificationRecord=studentIterator.next();
                   String l_studentID=notificationRecord.getRecord().get(2).trim();
                   String l_status=notificationRecord.getRecord().get(4).trim();
                   dbg("l_studentID"+l_studentID);
                   dbg("l_status"+l_status);

                   if(l_status.equals("F")){

                        int notificationHistoryMax=batchUtil.getStudentNotificationHistoryMaxSequence(l_businessDate, instituteID, notificationID, l_studentID, inject, session, dbSession);

                        if(notificationHistoryMax<=5){

                            studentList.add(notificationRecord);
                        }

                   }
                   else if(l_status.equals("W")){

                       studentList.add(notificationRecord);
                   }


               }
           
           
           
           
           
           
           Future<String>[] Result;
           Map<Integer,WorkDispatch>dispatchedJobs=new HashMap();
           boolean dispatchFailed=false;
           int no_of_execution;
           
           if(studentList.size()%no_of_threads==0){
               
               no_of_execution=studentList.size()/no_of_threads;
               
           }else{
               
               no_of_execution=(studentList.size()/no_of_threads)+1;
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
               if(i<studentList.size()){
                if(startIndex==i){
                       j=0;
                   }else{
                       j++;
                   }
                DBRecord studentNotificationRecord=studentList.get(i);
                String l_studentID=studentList.get(i).getRecord().get(2).trim();
                String l_status=studentList.get(i).getRecord().get(4).trim();
                dbg("l_studentID"+l_studentID);
                dbg("l_status"+l_status);
                         
                         if(l_status.equals("F")){
                            Map<String,String>column_to_Update=new HashMap();
                            column_to_Update=new HashMap();
                            column_to_Update.put("5", "W");
                            String[]l_pkey={instituteID,notificationID,l_studentID,l_businessDate};
                            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification"+i_db_properties.getProperty("FOLDER_DELIMITER")+notificationID, "BATCH", "STUDENT_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
                            
                            batchUtil.moveStudentNotificationRecordToHistory(instituteID,notificationID,l_studentID,l_businessDate,studentNotificationRecord, session, dbSession, inject);
                            tc.commit(session, dbSession);
                         }
                         
                
                try{
                    
                  Result[j]= (Future<String>)studentOtherActProcessing.parallelProcessing(instituteID,l_studentID, notificationID, l_businessDate);
               
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
           
          batchUtil.notificationProcessingSuccessHandler(instituteID, l_businessDate, notificationID, inject, session, dbSession);
        
          dbg("end of notificationProcessing -->parellel");
        }catch(DBValidationException ex){
//          tc.rollBack(session, dbSession);
          batchUtil.notificationProcessingErrorHandler(instituteID, l_businessDate, notificationID, ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
//          tc.rollBack(session, dbSession);
          batchUtil.notificationProcessingErrorHandler(instituteID, l_businessDate, notificationID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
//           tc.rollBack(session, dbSession);
           batchUtil.notificationProcessingErrorHandler(instituteID, l_businessDate, notificationID, ex, inject, session, dbSession);
        }
         
     }
    
    
    @Asynchronous
   public Future<String> parallelProcessing(String instituteID,String notificationID,String groupID,String l_businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    try{   
    
        processing(instituteID,notificationID,groupID,l_businessDate);
        
              return new AsyncResult<String>("Success");

       
        }catch(Exception ex){
           dbg(ex);
           return new AsyncResult<String>("Fail");
     }
    
}

 private void studentIdentification(String instituteID,String l_businessDate,String notificationID,String p_groupID)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       
       try{
           dbg("inside studentIdentification");
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBTransactionService dbts=inject.getDBTransactionService();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IMetaDataService mds=inject.getMetadataservice();
           BusinessService bs=inject.getBusinessService(session);
           ITransactionControlService tc=inject.getTransactionControlService();
           ArrayList<String>l_studentOfTheGroup=bs.getStudentsOfTheGroup(instituteID, p_groupID, session, dbSession, inject);
           dbg("l_studentOfTheGroup"+l_studentOfTheGroup.size());        
           
//           int tableID=mds.getTableMetaData("BATCH", "STUDENT_NOTIFICATION_EOD_STATUS", session).getI_Tableid();
                for(int j=0;j<l_studentOfTheGroup.size();j++){

                     String l_studentID=l_studentOfTheGroup.get(j).trim();
                     dbg("l_studentID"+l_studentID);
                   
//                     boolean recordExistence=true;
//                     
//                     try{
//                         
//                         String[]l_pkey={instituteID,notificationID,l_studentID,l_businessDate};
//                         readBuffer.readRecord("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_NOTIFICATION_EOD_STATUS", l_pkey, session, dbSession);
//                    
//                     }catch(DBValidationException ex){
//                         
//                         if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
//                             
//                             session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
//                              session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
//                              recordExistence=false;
//                         }
//                     }
//                     
//                     if(!recordExistence){
                     
                     
                    try{ 
                     
                         dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification"+i_db_properties.getProperty("FOLDER_DELIMITER")+notificationID, "BATCH", 127,instituteID,notificationID,l_studentID,l_businessDate,"W"," "," "," ");   
                          tc.commit(session, dbSession);
                     }catch(DBValidationException ex){
                        tc.rollBack(session, dbSession);
                       if(!ex.toString().contains("DB_VAL_009")){
                           throw ex;
                       }
                    }
                    
//                  }
                }
          
           
          dbg("end of studentIdentification");
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

//private boolean studentIdentification(String instituteID,String l_businessDate,String notificationID,String p_groupID)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//ITransactionControlService tc=null;
//try{
//    dbg("inside notification batch notificationIdentification");
//    IBDProperties i_db_properties=session.getCohesiveproperties();
//    IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//    IDBTransactionService dbts=inject.getDBTransactionService();
//    BusinessService bs=inject.getBusinessService(session);
//    tc=inject.getTransactionControlService();
//    BatchUtil batchUtil=inject.getBatchUtil(session);
//    ArrayList<String>l_studentOfTheGroup=bs.getStudentsOfTheGroup(instituteID, p_groupID, session, dbSession, inject);   
//
//         for(int j=0;j<l_studentOfTheGroup.size();j++){
//
//            String l_studentID=l_studentOfTheGroup.get(j).trim();
//            dbg("l_studentID"+l_studentID); 
//            dbg("notificationID"+notificationID);
//            dbg("l_businessDate"+l_businessDate);
//            boolean recordExistence=true;
//            String l_eodStatus=new String();
//            String startTime=bs.getCurrentDateTime();
//            int maxSequence=0;
//            dbg("startTime"+startTime);
//            DBRecord NotificationEODStatusRecord=null;
//
//            try{
//                maxSequence=batchUtil.getStudentNotificationMaxSequence(l_businessDate, instituteID, notificationID, l_studentID, inject, session, dbSession);
//                String l_eodPkey[]={instituteID,notificationID,l_studentID,l_businessDate,Integer.toString(maxSequence)};
//                NotificationEODStatusRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification"+i_db_properties.getProperty("FOLDER_DELIMITER")+notificationID, "BATCH", "STUDENT_NOTIFICATION_EOD_STATUS",l_eodPkey ,session, dbSession,true);
//                l_eodStatus=NotificationEODStatusRecord.getRecord().get(4).trim();
//                dbg("l_eodStatus-->"+l_eodStatus);
//
//            }catch(DBValidationException ex){
//                if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
//                   recordExistence=false;
//                }
//            }
//
//            dbg("l_eodStatus"+l_eodStatus);
//            dbg("recordExistence"+recordExistence);
//            int sequence=0; 
//
//
//            if(recordExistence==true){
//
//               if(l_eodStatus.equals("F")){
//                   
//                    int studentNotificationHistMaxSequence=batchUtil.getStudentNotificationHistoryMaxSequence(l_businessDate, instituteID, notificationID, l_studentID, inject, session, dbSession);
//
//                        if(studentNotificationHistMaxSequence<=10){
//
//                            sequence=maxSequence;
//                            Map<String,String>column_to_Update=new HashMap();
//                            column_to_Update.put("5", "W");
//                            column_to_Update.put("7", startTime);
//                            String[] l_pkey={instituteID,notificationID,l_studentID,l_businessDate,Integer.toString(sequence)};
//                            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification"+i_db_properties.getProperty("FOLDER_DELIMITER")+notificationID, "BATCH", "STUDENT_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
//                            batchUtil.moveNotificationRecordToHistory(l_businessDate,instituteID,notificationID,NotificationEODStatusRecord, session, dbSession, inject);
//                        }
//                        
//                    }
////               else{
////                   
////                        dbg("l_eodStatus is not f");
////                        sequence=maxSequence+1;
////                        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification"+i_db_properties.getProperty("FOLDER_DELIMITER")+notificationID, "BATCH", 127,instituteID,notificationID,l_studentID,l_businessDate,"W"," "," "," ",Integer.toString(sequence)); 
////
////                    }
//            }else{
//
//                sequence=1;
//                dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification"+i_db_properties.getProperty("FOLDER_DELIMITER")+notificationID, "BATCH", 127,instituteID,notificationID,l_studentID,l_businessDate,"W"," "," "," ",Integer.toString(sequence)); 
//
//            }
//
//        /* 
//
//        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 124,instituteID,notificationID,l_businessDate,"W"," "," "," ",groupID," "," "); 
//        tc.commit(session, dbSession);
//        }catch(DBValidationException ex){
//
//        if(!ex.toString().contains("DB_VAL_009")){
//        throw ex;
//        }
//        // }
//        */
//        }
//
//            tc.commit(session, dbSession);
//            dbg("end of notification identification");
//             return true;
//
//        }catch(DBValidationException ex){
//           tc.rollBack(session, dbSession);
//           throw ex;
//        }catch(DBProcessingException ex){
//           tc.rollBack(session, dbSession);
//           dbg(ex);
//           throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch(Exception ex){
//           tc.rollBack(session, dbSession);
//           dbg(ex);
//           throw new BSProcessingException(ex.toString());
//        }
//
//
//}

 public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
}
