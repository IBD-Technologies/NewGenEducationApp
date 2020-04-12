/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.exam;

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
public class ClassExamProcessing implements IClassExamProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public ClassExamProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
    public void processing (String instituteID,String standard,String section,String exam,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
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
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBTransactionService dbts=inject.getDBTransactionService();
            
        dbg("inside class Exam processing");
        
        Map<String,String>column_to_Update=new HashMap();
        column_to_Update.put("9", startTime);
        String[] l_pkeyy={instituteID,standard,section,exam,l_businessDate};
        dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "EXAM_BATCH_STATUS", l_pkeyy, column_to_Update,session); 
        tc.commit(session, dbSession);
                
              studentIdentification(instituteID,l_businessDate,standard,section,exam) ;   
              
              String[] l_pkey={"Exam"};
              DBRecord batchConfigRecord= readBuffer.readRecord("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_CONFIG",l_pkey ,session, dbSession);
              int no_of_threads=Integer.parseInt(batchConfigRecord.getRecord().get(8).trim());
              
              dbg("no_of_threads"+no_of_threads);
              
            if(no_of_threads==0){

                sequentialProcessing(instituteID,standard,section,exam,l_businessDate);
            }else{

                parallelProcessing(instituteID,standard,section,exam,l_businessDate,no_of_threads);
            }
                    
                    
         batchUtil.classExamProcessingSuccessHandler(instituteID, l_businessDate, standard,section,exam, inject, session, dbSession);
        dbg("end of class exam processing");
        }catch(DBValidationException ex){
//          tc.rollBack(session, dbSession);
          batchUtil.classExamProcessingErrorHandler(instituteID, l_businessDate, standard,section,exam, ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
//          tc.rollBack(session, dbSession);
          batchUtil.classExamProcessingErrorHandler(instituteID, l_businessDate, standard,section,exam, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
//           tc.rollBack(session, dbSession);
           batchUtil.classExamProcessingErrorHandler(instituteID, l_businessDate, standard,section,exam, ex, inject, session, dbSession);
        }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
}

    public void processing(String instituteID,String standard,String section,String exam,String l_businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(instituteID,standard,section,exam,l_businessDate);
       
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
    
     private void sequentialProcessing(String instituteID,String standard,String section,String exam,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      BatchUtil batchUtil=null;   
      ITransactionControlService tc=null;
      try{
           dbg("inside ClassExambatch -->sequential");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           tc=inject.getTransactionControlService();
           IStudentExamProcessing studentExamProcessing=inject.getStudentExamProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           batchUtil=inject.getBatchUtil(session);
      
            Map<String,DBRecord>studentExamBatchMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_EXAM_BATCH_STATUS", session, dbSession);
            List<DBRecord>studentList=studentExamBatchMap.values().stream().filter(rec->rec.getRecord().get(6).trim().equals("F")||rec.getRecord().get(6).trim().equals("W")).collect(Collectors.toList());
            
            dbg("studentList"+studentList.size());
            
                    for(int j=0;j<studentList.size();j++){
                         DBRecord studentFeeRecord=studentList.get(j);
                         String l_studentID=studentList.get(j).getRecord().get(4).trim();
                         String l_status=studentList.get(j).getRecord().get(6).trim();
                         
                         dbg("l_studentID"+l_studentID);
                         dbg("l_status"+l_status);
                         
                         if(l_status.equals("F")){
                            Map<String,String>column_to_Update=new HashMap();
                            column_to_Update=new HashMap();
                            column_to_Update.put("7", "W");
                            String[]l_pkey={instituteID,standard,section,exam,l_studentID,l_businessDate};
                            dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_EXAM_BATCH_STATUS", l_pkey, column_to_Update,session); 
                            batchUtil.moveStudentExamRecordToHistory(instituteID,standard,section,exam,l_studentID,l_businessDate,studentFeeRecord, session, dbSession, inject);
                         }
                         tc.commit(session, dbSession);
                         
                         dbg("before studentExamProcessing call");
                         studentExamProcessing.processing(instituteID,l_studentID,standard,section,exam,l_businessDate,session);
                         dbg("after studentExamProcessing call");
                         
                   }
    
    
         batchUtil.classExamProcessingSuccessHandler(instituteID, l_businessDate, standard,section,exam, inject, session, dbSession);
        dbg("end of feeProcessing -->sequential");
        }catch(DBValidationException ex){
//          tc.rollBack(session, dbSession);
          batchUtil.classExamProcessingErrorHandler(instituteID, l_businessDate, standard,section, exam,ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
//          tc.rollBack(session, dbSession);
          batchUtil.classExamProcessingErrorHandler(instituteID, l_businessDate, standard,section,exam, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
//           tc.rollBack(session, dbSession);
           batchUtil.classExamProcessingErrorHandler(instituteID, l_businessDate, standard,section,exam, ex, inject, session, dbSession);
        }
     }
    private void parallelProcessing(String instituteID,String standard,String section,String exam,String l_businessDate,int no_of_threads)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      BatchUtil batchUtil=null; 
      ITransactionControlService tc=null;
      try{
           dbg("inside examProcessing -->parallel");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IStudentExamProcessing studentExamProcessing=inject.getStudentExamProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           tc=inject.getTransactionControlService();
           batchUtil=inject.getBatchUtil(session);
          Map<String,DBRecord>studentExamBatchMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_EXAM_BATCH_STATUS", session, dbSession);
           List<DBRecord>studentList=studentExamBatchMap.values().stream().filter(rec->rec.getRecord().get(6).trim().equals("F")||rec.getRecord().get(6).trim().equals("W")).collect(Collectors.toList());
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
               DBRecord studentFeeRecord=studentList.get(i);
               String l_studentID=studentList.get(i).getRecord().get(4).trim();
               String l_status=studentList.get(i).getRecord().get(6).trim();
                dbg("l_studentID"+l_studentID);
                dbg("l_status"+l_status);
                         
                        if(l_status.equals("F")){
                            Map<String,String>column_to_Update=new HashMap();
                            column_to_Update=new HashMap();
                            column_to_Update.put("7", "W");
                            String[]l_pkey={instituteID,standard,section,exam,l_studentID,l_businessDate};
                            dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_EXAM_BATCH_STATUS", l_pkey, column_to_Update,session); 
                            batchUtil.moveStudentExamRecordToHistory(instituteID,standard,section,exam,l_studentID,l_businessDate,studentFeeRecord, session, dbSession, inject);
                            tc.commit(session, dbSession);
                        }
                         
                
                try{
                    
                  Result[j]= (Future<String>)studentExamProcessing.parallelProcessing(instituteID, l_studentID, standard, section,exam, l_businessDate);
               
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
           
          batchUtil.classExamProcessingSuccessHandler(instituteID, l_businessDate, standard,section,exam, inject, session, dbSession);
        
          dbg("end of examProcessing -->parellel");
        }catch(DBValidationException ex){
//          tc.rollBack(session, dbSession);
          batchUtil.classExamProcessingErrorHandler(instituteID, l_businessDate, standard,section, exam,ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
//          tc.rollBack(session, dbSession);
          batchUtil.classExamProcessingErrorHandler(instituteID, l_businessDate, standard,section,exam, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
//           tc.rollBack(session, dbSession);
           batchUtil.classExamProcessingErrorHandler(instituteID, l_businessDate, standard,section,exam, ex, inject, session, dbSession);
        }
         
     }
   
    
    

 private void studentIdentification(String instituteID,String l_businessDate,String p_standard,String p_section,String p_exam)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       
       try{
           dbg("inside studentIdentification");
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBTransactionService dbts=inject.getDBTransactionService();
           BusinessService bs=inject.getBusinessService(session);
           ITransactionControlService tc=inject.getTransactionControlService();
           ArrayList<String>l_studentOfTheGroup=bs.getStudentsOfTheClass(instituteID, p_standard,p_section, session, dbSession, inject);
           dbg("l_studentOfTheGroup"+l_studentOfTheGroup.size());        
           
           
                for(int j=0;j<l_studentOfTheGroup.size();j++){

                     String l_studentID=l_studentOfTheGroup.get(j).trim();
                     dbg("l_studentID"+l_studentID);
                   
                    try{ 
                     
                     dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 139,instituteID,p_standard,p_section,p_exam,l_studentID,l_businessDate,"W"," "," ");   
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
 @Asynchronous
   public Future<String> parallelProcessing(String instituteID,String standard,String section,String exam,String l_businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    try{   
    
        processing(instituteID,standard,section,exam,l_businessDate);
        
              return new AsyncResult<String>("Success");

       
        }catch(Exception ex){
           dbg(ex);
           return new AsyncResult<String>("Fail");
     }
    
}
  public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
}
