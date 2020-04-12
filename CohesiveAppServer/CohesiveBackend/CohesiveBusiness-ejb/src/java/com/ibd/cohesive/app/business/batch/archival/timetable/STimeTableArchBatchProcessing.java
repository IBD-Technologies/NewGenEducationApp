/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.archival.timetable;

import com.ibd.cohesive.app.business.batch.attendance.IStudentAttendanceProcessing;
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
public class STimeTableArchBatchProcessing implements ISTimeTableArchBatchProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public STimeTableArchBatchProcessing() throws NamingException {
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
        dbg("inside studentTimeTable batch processing");
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
            
            studentIdentification(instituteID,l_businessDate);
           
            
            dbg("no_of_threads"+no_of_threads);
            if(no_of_threads==0){

                sequentialProcessing(instituteID,l_businessDate,batchName);
            }else{

                parallelProcessing(instituteID,l_businessDate,batchName,no_of_threads);
            }
            
            
            
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
   
   
    private void sequentialProcessing(String instituteID,String l_businessDate,String batchName)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      BatchUtil batchUtil=null; 
      ITransactionControlService tc=null;
      try{
          
           dbg("inside s_other activity arch batch--->sequentialProcessing");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBTransactionService dbts=inject.getDBTransactionService();
           ISTimeTableArchProcessing studentArch=inject.getStudentTimeTableArchProcessing();
           batchUtil=inject.getBatchUtil(session);
           tc=inject.getTransactionControlService();
           
           Map<String,DBRecord>studentAssArchMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_TIME_TABLE_ARCH_BATCH_STATUS", session, dbSession);
           List<DBRecord>studentList=studentAssArchMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
           
           for(int i=0;i<studentList.size();i++){
              DBRecord stdentTimeTableRec=studentList.get(i);
              String l_studentID=stdentTimeTableRec.getRecord().get(1).trim();
              String l_status=stdentTimeTableRec.getRecord().get(3).trim();
               
               dbg("inside studentList iteration");
               dbg("l_status"+l_status);
               
                if(l_status.equals("F")){
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update=new HashMap();
                    column_to_Update.put("4", "W");
                    String[]l_pkey={instituteID,l_studentID,l_businessDate};
                    dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_TIME_TABLE_ARCH_BATCH_STATUS", l_pkey, column_to_Update,session); 
                    batchUtil.moveStudentTimeTableArchRecordToHistory(instituteID,l_studentID,l_businessDate,stdentTimeTableRec, session, dbSession, inject);
                    tc.commit(session, dbSession);
                }
                
                studentArch.processing(l_studentID, instituteID, l_businessDate, session);
           }
          dbg("end of time table batch--->sequentialProcessing");  
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
           dbg("inside student assignment archival Processing -->parallel");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           ISTimeTableArchProcessing studentArch=inject.getStudentTimeTableArchProcessing();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IStudentAttendanceProcessing studentAttendanceProcessing=inject.getStudentAttendanceProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           tc=inject.getTransactionControlService();
           batchUtil=inject.getBatchUtil(session);
           Map<String,DBRecord>studentAssArchMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_TIME_TABLE_ARCH_BATCH_STATUS", session, dbSession);
           List<DBRecord>studentList=studentAssArchMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
           dbg("studentList"+studentList.size());
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
                DBRecord stdentTimeTableRec=studentList.get(i);
                String l_studentID=stdentTimeTableRec.getRecord().get(1).trim();
                String l_status=stdentTimeTableRec.getRecord().get(3).trim();
                dbg("l_studentID"+l_studentID);
                dbg("l_status"+l_status);
                         
                        if(l_status.equals("F")){
                            Map<String,String>column_to_Update=new HashMap();
                            column_to_Update=new HashMap();
                            column_to_Update.put("4", "W");
                            String[]l_pkey={instituteID,l_studentID,l_businessDate};
                            dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_TIME_TABLE_ARCH_BATCH_STATUS", l_pkey, column_to_Update,session); 
                            batchUtil.moveStudentTimeTableArchRecordToHistory(instituteID,l_studentID,l_businessDate,stdentTimeTableRec, session, dbSession, inject);
                            tc.commit(session, dbSession);
                }
                         
                
                try{
                    
                  Result[j]= (Future<String>)studentArch.parallelProcessing(l_studentID, instituteID, l_businessDate);
               
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
     
     

     
     
     
     private void studentIdentification(String instituteID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       
       try{
           dbg("inside studentIdentification");
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBTransactionService dbts=inject.getDBTransactionService();
           BusinessService bs=inject.getBusinessService(session);
           ITransactionControlService tc=inject.getTransactionControlService();
           ArrayList<String>studentList=bs.getStudentsOfTheInstitute(instituteID, session, dbSession, inject);
           dbg("l_studentOfTheGroup"+studentList.size());        
           IMetaDataService mds=inject.getMetadataservice();
           int tableId=mds.getTableMetaData("BATCH", "STUDENT_TIME_TABLE_ARCH_BATCH_STATUS",session).getI_Tableid();
           dbg("tableId"+tableId);
           
                for(int j=0;j<studentList.size();j++){

                     String l_studentID=studentList.get(j).trim();
                     dbg("l_studentID"+l_studentID);
                   
                    try{ 
                     
                     dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", tableId,instituteID,l_studentID,l_businessDate,"W"," "," ");   
                      tc.commit(session, dbSession);
                     }catch(DBValidationException ex){
                   
                       if(!ex.toString().contains("DB_VAL_009")){
                           throw ex;
                       }
                    }
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
     

   
     public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
}
