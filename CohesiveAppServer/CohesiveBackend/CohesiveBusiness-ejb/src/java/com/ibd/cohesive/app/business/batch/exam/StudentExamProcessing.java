/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.exam;

import com.ibd.businessViews.IStudentExamScheduleService;
import com.ibd.cohesive.app.business.batch.timetable.Period;
import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Stateless
public class StudentExamProcessing implements IStudentExamProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public StudentExamProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
      public void processing (String instituteID,String studentID,String standard,String section,String exam,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       BatchUtil batchUtil=null;
       boolean l_session_created_now=false;
       ITransactionControlService tc=null;
       try{
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();  
        dbg("inside student exam processing ");
        dbg("instituteID"+instituteID);
        dbg("standard"+standard);
        dbg("section"+section);
        dbg("l_businessDate"+l_businessDate);
        dbg("studentID"+studentID);
        
        tc=inject.getTransactionControlService();
        BusinessService bs=inject.getBusinessService(session);
        String startTime=bs.getCurrentDateTime();
        batchUtil=inject.getBatchUtil(session);
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBTransactionService dbts=inject.getDBTransactionService();
            
                 //start time update starts
          Map<String,String>column_to_Update=new HashMap();
          column_to_Update.put("8", startTime);
          String[]l_pkey={instituteID,standard,section,exam,studentID,l_businessDate};
          dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_EXAM_BATCH_STATUS", l_pkey, column_to_Update,session); 
          tc.commit(session, dbSession);
          //start time update ends
          DBRecord studentExamRec;
          String versionNumber=null;
          boolean recordExistence=true;
          
          try{
        
            String[]l_primaryKey={studentID,exam};  
            studentExamRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_EXAM_SCHEDULE_MASTER", l_primaryKey, session, dbSession,true);
            versionNumber=studentExamRec.getRecord().get(8).trim();
            dbg("versionNumber"+versionNumber);
         }catch(DBValidationException ex){
            if(ex.toString().contains("DB_VAL_011")){
                
                dbg("printing error inside student exam processing"+ex.toString());
                recordExistence=false;
                
            }else{
                throw ex;
            }
         }catch(Exception ex){
             
             dbg("printing error inside student exam processing"+ex.toString());
             if(ex.toString().contains("DB_VAL_000")){
                
                recordExistence=false;
                
            }else{
                throw ex;
            }
             
             
         }
          
         dbg("recordExistence"+recordExistence); 
          if(!recordExistence){
              
              buildRequestAndCallStudentExam(studentID,standard,section,exam,1,instituteID);
          }else{
              
              buildRequestAndCallStudentExam(studentID,standard,section,exam,Integer.parseInt(versionNumber)+1,instituteID);
          }
          

         batchUtil.studentExamProcessingSuccessHandler(instituteID, l_businessDate, standard,section,exam, studentID, inject, session, dbSession);
        
         dbg("end of student exam processing");
        }catch(DBValidationException ex){
          batchUtil.studentExamProcessingErrorHandler(instituteID, l_businessDate, standard,section,exam, studentID, ex, inject, session, dbSession);
        }catch(BSValidationException ex){
          batchUtil.studentExamProcessingErrorHandler(instituteID, l_businessDate, standard,section,exam, studentID, ex, inject, session, dbSession);
      
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.studentExamProcessingErrorHandler(instituteID, l_businessDate, standard,section,exam, studentID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.studentExamProcessingErrorHandler(instituteID, l_businessDate, standard,section,exam, studentID, ex, inject, session, dbSession);
     }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
}

    public void processing(String instituteID,String studentID,String p_standard,String p_section,String p_exam,String l_businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(instituteID,studentID,p_standard,p_section,p_exam,l_businessDate);
       
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
    
    
   
   
    private void buildRequestAndCallStudentExam(String l_studentID,String p_standard,String p_section,String p_exam,int p_versionNumber,String instituteID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
     
        try{
        dbg("inside buildRequestAndCallStudentExam") ;   
        dbg("versionNumber"+p_versionNumber);
        IStudentExamScheduleService stt=inject.getStudentExamScheduleService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=instituteID;
        JsonObject studentExam;
        String l_msgID="";
        String l_correlationID="";
        String l_service="StudentExamSchedule";
        String l_operation="AutoAuth";
        JsonArray l_businessEntity=Json.createArrayBuilder().add(Json.createObjectBuilder().add("entityName", "studentID")
                                                                                             .add("entityValue", l_studentID)).build();
        String l_userID="system";
        String l_source="cohesive_backend";
        String l_status=" ";
        
        String[] l_pkey={p_standard,p_section,p_exam};
        DBRecord examRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section,"CLASS","CLASS_EXAM_SCHEDULE_MASTER", l_pkey, session, dbSession);
        Map<String,DBRecord>detailMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section,"CLASS","CLASS_EXAM_SCHEDULE_DETAIL", session, dbSession);
        
        ClassExamSchedule classExam=getBOfromDB(examRec,detailMap,p_exam);
        
        String l_recordStatus=classExam.getRecordStatus();
        String l_authStatus=classExam.getAuthStatus();
        String l_makerRemarks=classExam.getMakerRemarks();
        String l_checkerRemarks=classExam.getCheckerRemarks();

        JsonObject body=getBodyFromBO(classExam,l_studentID,p_exam);
       


          studentExam=Json.createObjectBuilder().add("header", Json.createObjectBuilder()
                                                        .add("msgID",l_msgID)
                                                        .add("correlationID", l_correlationID)
                                                        .add("service", l_service)
                                                        .add("operation", l_operation)
                                                        .add("businessEntity", l_businessEntity)
                                                        .add("instituteID", l_instituteID)
                                                        .add("status", l_status)
                                                        .add("source", l_source)
                                                        .add("userID",l_userID))
                                                        .add("body",body)
                                                        .add("auditLog",  Json.createObjectBuilder()
                                                        .add("makerID", "")
                                                        .add("checkerID", "")
                                                        .add("makerDateStamp", "")
                                                        .add("checkerDateStamp", "")
                                                        .add("recordStatus", l_recordStatus)
                                                        .add("authStatus", l_authStatus)
                                                        .add("versionNumber", Integer.toString(p_versionNumber))
                                                        .add("makerRemarks", l_makerRemarks)
                                                        .add("checkerRemarks", l_checkerRemarks)).build();
          
          dbg("studentFeerequest"+studentExam.toString());
          dbg("before  studentExam call");
          stt.processing(studentExam, session);
          dbg("after studentExam call");
          
          
          
          
          
          
        dbg("end of buildRequestAndCallStudentExam");  
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }catch(BSProcessingException ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());    
        }catch(BSValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
          
 }
    
    
   
    
    
    private ClassExamSchedule getBOfromDB(DBRecord p_ClassExamRecord, Map<String,DBRecord>detailMap,String p_exam)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           ClassExamSchedule examSchedule =new ClassExamSchedule();
           ArrayList<String>l_ClassExamList= p_ClassExamRecord.getRecord();
           
           if(l_ClassExamList!=null&&!l_ClassExamList.isEmpty()){
               
              examSchedule.setRecordStatus(l_ClassExamList.get(7).trim());
              examSchedule.setAuthStatus(l_ClassExamList.get(8).trim());
              examSchedule.setMakerRemarks(l_ClassExamList.get(10).trim());
              examSchedule.setCheckerRemarks(l_ClassExamList.get(11).trim());
            }
           
            Map<String,List<DBRecord>> examWiseGroup=detailMap.values().stream().filter(rec->rec.getRecord().get(2).trim().equals(p_exam)).collect(Collectors.groupingBy(rec->rec.getRecord().get(2).trim()));
            examSchedule.schedule=new Schedule[examWiseGroup.get(p_exam).size()];
            int i=0;
                for(DBRecord l_scheduleRecords: examWiseGroup.get(p_exam)){
                   examSchedule.schedule[i]=new Schedule();
                   examSchedule.schedule[i].setSubjectID(l_scheduleRecords.getRecord().get(3).trim());
                   examSchedule.schedule[i].setDate(l_scheduleRecords.getRecord().get(4).trim());
                   examSchedule.schedule[i].setHall(l_scheduleRecords.getRecord().get(5).trim());
                   examSchedule.schedule[i].setStartTimeHour(l_scheduleRecords.getRecord().get(6).trim());
                   examSchedule.schedule[i].setStartTimeMin(l_scheduleRecords.getRecord().get(7).trim());
                   examSchedule.schedule[i].setEndTimeHour(l_scheduleRecords.getRecord().get(8).trim());
                   examSchedule.schedule[i].setEndTimeMin(l_scheduleRecords.getRecord().get(9).trim());
                   i++;
                }
            
            
          dbg("end of  buildBOfromDB"); 
        return examSchedule;
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
 }
   
    private JsonObject getBodyFromBO(ClassExamSchedule examSchedule,String studentID,String exam)throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside getBodyAndAudiFromBO");
        JsonArrayBuilder schedule=Json.createArrayBuilder();
            for(int i=0;i<examSchedule.schedule.length;i++){
              schedule.add(Json.createObjectBuilder().add("subjectID", examSchedule.schedule[i].getSubjectID())
                                                     .add("date", examSchedule.schedule[i].getDate())
                                                     .add("startTime", Json.createObjectBuilder().add("hour", examSchedule.schedule[i].getStartTimeHour()).add("min", examSchedule.schedule[i].getStartTimeMin()))
                                                     .add("endTime", Json.createObjectBuilder().add("hour", examSchedule.schedule[i].getEndTimeHour()).add("min", examSchedule.schedule[i].getEndTimeMin()))
                                                     .add("hall", examSchedule.schedule[i].getHall()));
            }
        

            body=Json.createObjectBuilder().add("studentID",  studentID)
                                           .add("exam", exam)
                                           .add("Subjectschedules", schedule)
                                           .build();
           
           
            
              dbg(body.toString());  
           dbg("end of getBodyAndAudiFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
    
    @Asynchronous
   public Future<String> parallelProcessing(String instituteID,String studentID,String standard,String section,String exam,String l_businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    try{   
    
        processing(instituteID,studentID,standard,section,exam,l_businessDate);
        
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
