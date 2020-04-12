/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.mark;

import com.ibd.businessViews.IStudentProgressCardService;
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
import java.util.Map;
import java.util.concurrent.Future;
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
public class StudentMarkProcessing implements IStudentMarkProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public StudentMarkProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
      public void processing (String instituteID,String studentID,String standard,String section,String exam,String subjectID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       BatchUtil batchUtil=null;
       boolean l_session_created_now=false;
       ITransactionControlService tc=null;
       try{
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();  
        dbg("inside student mark processing ");
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
          column_to_Update.put("9", startTime);
          String[]l_pkey={instituteID,standard,section,exam,subjectID,studentID,l_businessDate};
          dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_MARK_BATCH_STATUS", l_pkey, column_to_Update,session); 
          tc.commit(session, dbSession);
          //start time update ends
          DBRecord studentMarkRec;
          String versionNumber=null;
          boolean recordExistence=true;
          
          try{
        
            String[]l_primaryKey={studentID,exam};  
            studentMarkRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_PRORESS_CARD", l_primaryKey, session, dbSession,true);
            versionNumber=studentMarkRec.getRecord().get(10).trim();
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
              
              buildRequestAndCallStudentMark(studentID,standard,section,exam,subjectID,1,instituteID);
          }else{
              
              buildRequestAndCallStudentMark(studentID,standard,section,exam,subjectID,Integer.parseInt(versionNumber)+1,instituteID);
          }
          

         batchUtil.studentMarkProcessingSuccessHandler(instituteID, l_businessDate, standard,section,exam, subjectID,studentID, inject, session, dbSession);
        
         dbg("end of student exam processing");
        }catch(DBValidationException ex){
          batchUtil.studentMarkProcessingErrorHandler(instituteID, l_businessDate, standard,section,exam,subjectID, studentID, ex, inject, session, dbSession);
        }catch(BSValidationException ex){
          batchUtil.studentMarkProcessingErrorHandler(instituteID, l_businessDate, standard,section,exam,subjectID, studentID, ex, inject, session, dbSession);
      
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.studentMarkProcessingErrorHandler(instituteID, l_businessDate, standard,section,exam,subjectID, studentID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.studentMarkProcessingErrorHandler(instituteID, l_businessDate, standard,section,exam,subjectID, studentID, ex, inject, session, dbSession);
     }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
}

    public void processing(String instituteID,String studentID,String p_standard,String p_section,String p_exam,String p_subjctID,String l_businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(instituteID,studentID,p_standard,p_section,p_exam,p_subjctID,l_businessDate);
       
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
    
    
   
   
    private void buildRequestAndCallStudentMark(String l_studentID,String p_standard,String p_section,String p_exam,String subjectID,int p_versionNumber,String instituteID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
     
        try{
        dbg("inside buildRequestAndCallStudentMark") ;   
        dbg("versionNumber"+p_versionNumber);
        IStudentProgressCardService stt=inject.getStudentProgressCardService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=instituteID;
        JsonObject studentMark;
        String l_msgID="";
        String l_correlationID="";
        String l_service="StudentProgressCard";
        String l_operation="AutoAuth";
        JsonArray l_businessEntity=Json.createArrayBuilder().add(Json.createObjectBuilder().add("entityName", "studentID")
                                                                                             .add("entityValue", l_studentID)).build();
        String l_userID="system";
        String l_source="cohesive_backend";
        String l_status=" ";
        
        String[] l_pkey={p_standard,p_section,p_exam,subjectID};
        DBRecord markRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section,"CLASS","CLASS_MARK_ENTRY", l_pkey, session, dbSession);
        String[] l_primaryKey={p_standard,p_section,p_exam,subjectID,l_studentID};
        DBRecord detailMap= readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section,"CLASS","STUDENT_MARKS", l_primaryKey,session, dbSession);
        
        ClassMark classMark=getBOfromDB(markRec,detailMap,subjectID);
        
        String l_recordStatus=classMark.getRecordStatus();
        String l_authStatus=classMark.getAuthStatus();
        String l_makerRemarks=classMark.getMakerRemarks();
        String l_checkerRemarks=classMark.getCheckerRemarks();

        JsonObject body=getBodyFromBO(classMark,l_studentID,p_exam,subjectID);
       


          studentMark=Json.createObjectBuilder().add("header", Json.createObjectBuilder()
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
          
          dbg("studentFeerequest"+studentMark.toString());
          dbg("before  studentMark call");
          stt.processing(studentMark, session);
          dbg("after studentMark call");
          
          
          
          
          
          
        dbg("end of buildRequestAndCallStudentMark");  
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
    
    
   
    
    
    private ClassMark getBOfromDB(DBRecord p_ClassMarkRecord, DBRecord detailRecord,String subjectID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           ClassMark classMark =new ClassMark();
           ArrayList<String>l_ClassMarkList= p_ClassMarkRecord.getRecord();
           
           if(l_ClassMarkList!=null&&!l_ClassMarkList.isEmpty()){
               
              classMark.setRecordStatus(l_ClassMarkList.get(8).trim());
              classMark.setAuthStatus(l_ClassMarkList.get(9).trim());
              classMark.setMakerRemarks(l_ClassMarkList.get(11).trim());
              classMark.setCheckerRemarks(l_ClassMarkList.get(12).trim());
            }
            
            
            String grade=detailRecord.getRecord().get(5).trim();
            String mark=detailRecord.getRecord().get(6).trim();
            String teacherFeedBack=detailRecord.getRecord().get(7).trim();
           
                   classMark.mark= new Marks[1]   ;
                   classMark.mark[0]=new Marks();
                   classMark.mark[0].setSubjectID(subjectID);
                   classMark.mark[0].setGrade(grade);
                   classMark.mark[0].setMark(mark);
                   classMark.mark[0].setTeacherFeedback(teacherFeedBack);
            
            
          dbg("end of  buildBOfromDB"); 
        return classMark;
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
 }
   
    private JsonObject getBodyFromBO(ClassMark classMark,String studentID,String p_exam,String p_subjectID)throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside getBodyAndAudiFromBO");
        JsonArrayBuilder mark=Json.createArrayBuilder();
        for(int i=0;i<classMark.mark.length;i++){
            
            mark.add(Json.createObjectBuilder().add("subjectID", classMark.mark[i].getSubjectID())
                                               .add("grade", classMark.mark[i].getGrade())
                                               .add("mark", classMark.mark[i].getMark())
                                               .add("teacherFeedback", classMark.mark[i].getTeacherFeedback()));
            }
            

            body=Json.createObjectBuilder().add("studentID",  studentID)
                                           .add("exam", p_exam)
                                           .add("marks", mark)
                                           .add("total", " ")
                                           .add("rank", " ")
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
   public Future<String> parallelProcessing(String instituteID,String studentID,String standard,String section,String exam,String subjectID,String l_businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    try{   
    
        processing(instituteID,studentID,standard,section,exam,subjectID,l_businessDate);
        
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
