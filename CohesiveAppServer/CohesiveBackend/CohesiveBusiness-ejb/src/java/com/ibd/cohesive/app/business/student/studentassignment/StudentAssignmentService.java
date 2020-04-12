/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentassignment;

import com.ibd.businessViews.IStudentAssignmentService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.ConvertedDate;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.exception.ExceptionHandler;
import com.ibd.cohesive.app.business.util.message.request.Parsing;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
//@Local(IStudentAssignmentService.class)
@Remote(IStudentAssignmentService.class)
@Stateless
public class StudentAssignmentService implements IStudentAssignmentService {
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentAssignmentService(){
        try {
            inject=new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession= new DBSession(session);
        } catch (NamingException ex) {
            dbg(ex);
            throw new EJBException(ex);
        }
        
    }
    @Override
    public JsonObject EJBprocessing(JsonObject requestJson)
    {     
        JsonObject response =null;
      try{
          response = processing(requestJson);
      }
      catch(Exception e)
      { 
         dbg(e);
         throw new EJBException(e);
      }  
      return response;
     }
    
    @Override
    public String EJBprocessing(String request)
    {     
        JsonObject responseJson =null;
        JsonObject requestJson=null;
        String response =null;
        
      try{
          requestJson=inject.getJsonUtil().getJsonObject(request);
          responseJson = processing(requestJson);
          response = responseJson.toString();
          
      }
      catch(Exception e)
      { 
         if (session.getDebug()!=null)
          dbg(e);
         throw new EJBException(e);
      }  
      return response;
     }
    
    
     public JsonObject processing(JsonObject requestJson)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean l_validation_status=true;
       boolean l_session_created_now=false;
       BusinessService  bs;
       Parsing parser;
       ExceptionHandler exHandler;
       JsonObject jsonResponse=null;
       JsonObject clonedResponse=null;
       JsonObject clonedJson=null;
       String l_lockKey=null;
       IBusinessLockService businessLock=null;
       try{
       session.createSessionObject();
       dbSession.createDBsession(session);
       l_session_created_now=session.isI_session_created_now();
       ErrorHandler errhandler = session.getErrorhandler();
       BSValidation bsv=inject.getBsv(session);
       bs=inject.getBusinessService(session);
       ITransactionControlService tc=inject.getTransactionControlService();
       businessLock=inject.getBusinessLockService();
       dbg("inside StudentAssignmentService--->processing");
       dbg("StudentAssignmentService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<StudentAssignment> reqBody = request.getReqBody();
       StudentAssignment studentAssignment =reqBody.get();
       
       l_lockKey=studentAssignment.getAssignmentID()+"~"+studentAssignment.getStudentID();
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       
       BusinessEJB<IStudentAssignmentService>studentAssignmentEJB=new BusinessEJB();
       studentAssignmentEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentAssignmentEJB);
       if(request.getReqHeader().getOperation().equals("View")){
           
         String studentID=  bs.studentValidation(studentAssignment.getStudentID(), studentAssignment.getStudentName(), request.getReqHeader().getInstituteID(), session, dbSession, inject);
         
          
         if(studentID==null||studentID.isEmpty()){
             
             errhandler.log_app_error("BS_VAL_002","Student ID or Name");  
             throw new BSValidationException("BSValidationException");
         }
         
         studentAssignment.setStudentID(studentID);
         bs.setStudentIDInBusinessEntity(studentID, request);
           
       }
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
        

      
       bs.businessServiceProcssing(request, exAudit, inject,studentAssignmentEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentAssignmentEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student assignment");
        }
       }catch(NamingException ex){
            dbg(ex);
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson,"BSProcessingException");
       }catch(BSValidationException ex){
          if(l_session_created_now){
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson,"BSValidationException");
          }else{
              throw ex;
          }
       }catch(DBValidationException ex){
           if(l_session_created_now){
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson,"DBValidationException");
          }else{
              throw ex;
          }
       }catch(DBProcessingException ex){
            dbg(ex);
            if(l_session_created_now){
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson,"DBProcessingException");
            }else{
              throw ex;
          }
       }catch(BSProcessingException ex){
           dbg(ex);
           if(l_session_created_now){
           exHandler = inject.getErrorHandle(session);
           jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson,"BSProcessingException");
          }else{
              throw ex;
          }
      }
        finally{
           exAudit=null;
            bs=inject.getBusinessService(session);
            dbg("before calling remove businessLock");
            
            if(l_lockKey!=null){
               businessLock.removeBusinessLock(request, l_lockKey, session);
            }
            request=null;
            if(l_session_created_now){
                bs.responselogging(jsonResponse, inject,session,dbSession);
                dbg("Response"+jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
                //if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                          clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes  
                /*BSProcessingException ex=new BSProcessingException("response contains special characters");
                   dbg(ex);
                   session.clearSessionObject();
                   dbSession.clearSessionObject();
                   throw ex;
                }*/
            session.clearSessionObject();
            dbSession.clearSessionObject();
           } 
           }
       
        
       return clonedResponse; 
    }
    
    public JsonObject processing(JsonObject requestJson,CohesiveSession session)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
    CohesiveSession tempSession = this.session;
    JsonObject response =null;
    try{
        this.session=session;
        response =processing(requestJson);
     }catch(DBValidationException ex){
                 
        throw ex;
        }catch(BSValidationException ex){
                 
        throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(BSProcessingException ex){
             dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }finally{
           this.session=tempSession;
        }
 return response;
    }

    private  void buildBOfromReq(JsonObject p_request)throws BSProcessingException,DBProcessingException{
      StudentAssignment studentAssignment=new StudentAssignment();
      RequestBody<StudentAssignment> reqBody = new RequestBody<StudentAssignment>(); 
           
      try{
      dbg("inside student assignment buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      studentAssignment.setStudentID(l_body.getString("studentID"));
      studentAssignment.setStudentName(l_body.getString("studentName"));
      studentAssignment.setAssignmentID(l_body.getString("assignmentID"));
      if(!l_operation.equals("View")){
          studentAssignment.setSubjectID(l_body.getString("subjectID"));
          studentAssignment.setSubjectName(l_body.getString("subjectName"));
          studentAssignment.setAssignmentDescription(l_body.getString("assignmentDescription"));
          studentAssignment.setAssignentType(l_body.getString("assignmentType"));
          studentAssignment.setDueDate(l_body.getString("dueDate"));
          studentAssignment.setTeacherComment(l_body.getString("teacherComments"));
          studentAssignment.setUrl(l_body.getString("url"));
      }
        reqBody.set(studentAssignment);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
    try{ 
//        dbg("inside stident assignment create"); 
//        RequestBody<StudentAssignment> reqBody = request.getReqBody();
//        StudentAssignment studentAssignment =reqBody.get();
//        IDBTransactionService dbts=inject.getDBTransactionService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        String l_makerID=request.getReqAudit().getMakerID();
//        String l_checkerID=request.getReqAudit().getCheckerID();
//        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
//        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
//        String l_recordStatus=request.getReqAudit().getRecordStatus();
//        String l_authStatus=request.getReqAudit().getAuthStatus();
//        String l_versionNumber=request.getReqAudit().getVersionNumber();
//        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
//        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();   
//        String l_studentID=studentAssignment.getStudentID();
//        String l_subjectID=studentAssignment.getSubjectID();
//        String l_assignmentID=studentAssignment.getAssignmentID();
//        String l_assignmentDescription=studentAssignment.getAssignmentDescription();
//        String l_dueDate=studentAssignment.getDueDate();
//        String l_teacherComment=studentAssignment.getTeacherComment();
//        String l_parentComment=studentAssignment.getParentComment();
//        String l_assignmentType=studentAssignment.getAssignentType();
//        String l_contentPath=studentAssignment.getContentPath();
//        String l_url=studentAssignment.getUrl();
//        
//        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",60,l_studentID,l_subjectID,l_assignmentID,l_assignmentDescription,l_dueDate,l_completedDate,l_status,l_teacherComment,l_parentComment,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks,l_assignmentType,l_contentPath,l_url);
//
//        
//        
//        dbg("end of student assignment create"); 
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception".concat(ex.toString()));
        }
    }
    
     

    
    public void authUpdate()throws DBValidationException,DBProcessingException,BSProcessingException{
        
     try{ 
        dbg("inside student assignment--->auth update"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<StudentAssignment> reqBody = request.getReqBody();
        StudentAssignment studentAssignment =reqBody.get();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_studentID=studentAssignment.getStudentID();
        String l_assignmentID=studentAssignment.getAssignmentID();
        String[] l_primaryKey={l_assignmentID};
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("11", l_checkerID);
         l_column_to_update.put("13", l_checkerDateStamp);
         l_column_to_update.put("15", l_authStatus);
         l_column_to_update.put("18", l_checkerRemarks);
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_ASSIGNMENT", l_primaryKey, l_column_to_update, session);
         dbg("end of student assignment--->auth update");          
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
             throw new DBProcessingException(ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
        
       }
//    public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
//        
//       Map<String,String> l_column_to_update;
//                
//       try{ 
//        IDBTransactionService dbts=inject.getDBTransactionService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        RequestBody<StudentAssignment> reqBody = request.getReqBody();
//        StudentAssignment studentAssignment =reqBody.get();
//        String l_instituteID=request.getReqHeader().getInstituteID();   
//        String l_makerID=request.getReqAudit().getMakerID();
//        String l_checkerID=request.getReqAudit().getCheckerID();
//        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
//        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
//        String l_recordStatus=request.getReqAudit().getRecordStatus();
//        String l_authStatus=request.getReqAudit().getAuthStatus();
//        String l_versionNumber=request.getReqAudit().getVersionNumber();
//        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
//        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();   
//        String l_studentID=studentAssignment.getStudentID();
//        String l_subjectID=studentAssignment.getSubjectID();
//        String l_assignmentID=studentAssignment.getAssignmentID();
//        String l_assignmentDescription=studentAssignment.getAssignmentDescription();
//        String l_dueDate=studentAssignment.getDueDate();
//        String l_completedDate=studentAssignment.getCompletedDate();
//        String l_status=studentAssignment.getStatus();
//        String l_teacherComment=studentAssignment.getTeacherComment();
//        String l_parentComment=studentAssignment.getParentComment();
//        String l_assignmentType=studentAssignment.getAssignentType();
//        String l_contentPath=studentAssignment.getContentPath();
//        String l_url=studentAssignment.getUrl();
//                        l_column_to_update= new HashMap();
//                        l_column_to_update.put("1", l_studentID);
//                        l_column_to_update.put("2", l_subjectID);
//                        l_column_to_update.put("3", l_assignmentID);
//                        l_column_to_update.put("4", l_assignmentDescription);
//                        l_column_to_update.put("5", l_dueDate);
//                        l_column_to_update.put("6", l_completedDate);
//                        l_column_to_update.put("7", l_status);
//                        l_column_to_update.put("8", l_teacherComment);
//                        l_column_to_update.put("9", l_parentComment);
//                        l_column_to_update.put("10", l_makerID);
//                        l_column_to_update.put("11", l_checkerID);
//                        l_column_to_update.put("12", l_makerDateStamp);
//                        l_column_to_update.put("13", l_checkerDateStamp);
//                        l_column_to_update.put("14", l_recordStatus);
//                        l_column_to_update.put("15", l_authStatus);
//                        l_column_to_update.put("16", l_versionNumber);
//                        l_column_to_update.put("17", l_makerRemarks);
//                        l_column_to_update.put("18", l_checkerRemarks);
//                        l_column_to_update.put("19", l_assignmentType);
//                        l_column_to_update.put("20", l_contentPath);
//                        l_column_to_update.put("21", l_url);
//                        String[] l_primaryKey={l_assignmentID};
//                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_ASSIGNMENT", l_primaryKey, l_column_to_update, session);   
//                  
//        
//        
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//            
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception".concat(ex.toString()));
//        }
//    }

    
    public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
        
       Map<String,String> l_column_to_update;
                
       try{ 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentAssignment> reqBody = request.getReqBody();
        StudentAssignment studentAssignment =reqBody.get();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=request.getReqHeader().getInstituteID();   
        
        String l_studentID=studentAssignment.getStudentID();
        String l_assignmentID=studentAssignment.getAssignmentID();
        String[] l_pkey={l_assignmentID};
        
        DBRecord assignmentRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT", "SVW_STUDENT_ASSIGNMENT", l_pkey, session, dbSession);
        
        String status=assignmentRec.getRecord().get(4).trim();
        
        if(status.equals("C")){
        
          session.getErrorhandler().log_app_error("BS_VAL_043", "Assignment");
          throw new BSValidationException("BSValidationException");
        
        }else{
            
            
            l_column_to_update= new HashMap();
            l_column_to_update.put("4", "C");
            String[] l_primaryKey={l_assignmentID};
            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT","SVW_STUDENT_ASSIGNMENT", l_primaryKey, l_column_to_update, session);
            
        }
        
                          
                  
        
        
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception".concat(ex.toString()));
        }
    }

    
    
    
    
    public void delete()throws DBValidationException,DBProcessingException,BSProcessingException{
        try{
        dbg("inside student assignment delete");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentAssignment> reqBody = request.getReqBody();
        StudentAssignment studentAssignment =reqBody.get();
        String l_studentID=studentAssignment.getStudentID();
        String l_assignmentID=studentAssignment.getAssignmentID();
             
             String[] l_primaryKey={l_assignmentID};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_ASSIGNMENT", l_primaryKey, session);
            dbg("end of student assignment delete");
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
             throw new DBProcessingException(ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
        
       }

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException{
                
        try{      
        dbg("inside student assignment--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentAssignment> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        StudentAssignment studentAssignment =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_studentID=studentAssignment.getStudentID();
        String l_assignmentID=studentAssignment.getAssignmentID();
        String[] l_pkey={l_assignmentID};
        DBRecord assignmentRec=null;
        
        
        try{
        
        
        assignmentRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT", "SVW_STUDENT_ASSIGNMENT", l_pkey, session, dbSession);
        buildBOfromDB(assignmentRec);
        
        }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", l_assignmentID);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
        
          dbg("end of  completed student assignment--->view");                
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
      private void buildBOfromDB(DBRecord p_studentAssignmentRecord)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<StudentAssignment> reqBody = request.getReqBody();
           StudentAssignment studentAssignment =reqBody.get();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           ArrayList<String>l_studentAssignmentList= p_studentAssignmentRecord.getRecord();
           String l_assignmentID=studentAssignment.getAssignmentID();
           String l_instituteID=request.getReqHeader().getInstituteID();
           BusinessService bs=inject.getBusinessService(session);
           String l_studentID=studentAssignment.getStudentID();
           
           String[] l_pkey={l_assignmentID};
           
           
           if(l_studentAssignmentList!=null&&!l_studentAssignmentList.isEmpty()){
               
            String studentName= bs.getStudentName(l_studentID, l_instituteID, session, dbSession, inject);
               
            studentAssignment.setStudentName(studentName);
            
            studentAssignment.setDueDate(l_studentAssignmentList.get(2).trim());
            
            DBRecord instituteAssignmentRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT","IVW_ASSIGNMENT", l_pkey, session,dbSession);
            
            String subjectID=instituteAssignmentRecord.getRecord().get(4).trim();
            String subjectName=bs.getSubjectName(subjectID, l_instituteID, session, dbSession, inject);
            studentAssignment.setSubjectID(subjectID);
            studentAssignment.setSubjectName(subjectName);
            studentAssignment.setAssignmentDescription(instituteAssignmentRecord.getRecord().get(3).trim());
            studentAssignment.setAssignentType(instituteAssignmentRecord.getRecord().get(5).trim());
            studentAssignment.setUrl(instituteAssignmentRecord.getRecord().get(17).trim());
            studentAssignment.setTeacherComment(instituteAssignmentRecord.getRecord().get(7).trim());
            
            request.getReqAudit().setMakerID(instituteAssignmentRecord.getRecord().get(8).trim());
            request.getReqAudit().setCheckerID(instituteAssignmentRecord.getRecord().get(9).trim());
            request.getReqAudit().setMakerDateStamp(instituteAssignmentRecord.getRecord().get(10).trim());
            request.getReqAudit().setCheckerDateStamp(instituteAssignmentRecord.getRecord().get(11).trim());
            request.getReqAudit().setRecordStatus(instituteAssignmentRecord.getRecord().get(12).trim());
            request.getReqAudit().setAuthStatus(instituteAssignmentRecord.getRecord().get(13).trim());
            request.getReqAudit().setVersionNumber(instituteAssignmentRecord.getRecord().get(14).trim());
            request.getReqAudit().setMakerRemarks(instituteAssignmentRecord.getRecord().get(15).trim());
            request.getReqAudit().setCheckerRemarks(instituteAssignmentRecord.getRecord().get(16).trim());
            
            }
            
          dbg("end of  buildBOfromDB"); 
        
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
 }
    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside student assignment buildJsonResFromBO");    
        RequestBody<StudentAssignment> reqBody = request.getReqBody();
        StudentAssignment studentAssignment =reqBody.get();
        body=Json.createObjectBuilder().add("studentID", studentAssignment.getStudentID())
                                       .add("studentName", studentAssignment.getStudentName())
                                       .add("subjectID", studentAssignment.getSubjectID())
                                       .add("subjectName", studentAssignment.getSubjectName())
                                       .add("assignmentID",studentAssignment.getAssignmentID())
                                       .add("assignmentDescription", studentAssignment.getAssignmentDescription())
                                       .add("assignmentType", studentAssignment.getAssignentType())
                                       .add("dueDate", studentAssignment.getDueDate())
                                       .add("teacherComments", studentAssignment.getTeacherComment())
                                       .add("url", studentAssignment.getUrl())
                                       .build();
                                            
              dbg(body.toString());  
           dbg("end of student assignment buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student assignment--->businessValidation");    
       String l_operation=request.getReqHeader().getOperation();
       if(!masterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!masterDataValidation(errhandler)){
             status=false;
            }
       }
       
       if(!(l_operation.equals("View"))){
           
           if(!detailMandatoryValidation(errhandler)) {
               
               status=false;
           } else{
           
           if(!detailDataValidation(errhandler)){
               
               status=false;
           }
            
           
           }
       
       }
       dbg("end of student assignment--->businessValidation"); 
       }catch(BSProcessingException ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
       }catch(DBValidationException ex){
           throw ex;
            
       }catch(BSValidationException ex){
           throw ex;

       }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    return status;
   }
    private boolean masterMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
      boolean status=true;
        try{
        dbg("inside student assignment master mandatory validation");
        RequestBody<StudentAssignment> reqBody = request.getReqBody();
        StudentAssignment studentAssignment =reqBody.get();
        
         if(studentAssignment.getStudentID()==null||studentAssignment.getStudentID().equals("")){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","studentID");  
         }
          if(studentAssignment.getAssignmentID()==null||studentAssignment.getAssignmentID().equals("")){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","assignmentID");  
         }
//          if(studentAssignment.getSubjectID()==null||studentAssignment.getSubjectID().equals("")){
//             status=false;  
//             errhandler.log_app_error("BS_VAL_002","subjectID");  
//         }
          
        dbg("end of student assignment master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student assignment masterDataValidation");
             RequestBody<StudentAssignment> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             StudentAssignment studentAssignment =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_studentID=studentAssignment.getStudentID();
//             String l_subjectID=studentAssignment.getSubjectID();
             
             if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","studentID");
             }
//             if(!bsv.subjectIDValidation(l_subjectID, l_instituteID, session, dbSession, inject)){
//                 status=false;
//                 errhandler.log_app_error("BS_VAL_003","subjectID");
//             }
            
            dbg("end of student assignment masterDataValidation");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        
        return status;
              
    }
    
    private boolean detailMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
        boolean status=true;
        RequestBody<StudentAssignment> reqBody = request.getReqBody();
        StudentAssignment studentAssignment =reqBody.get();
        
        try{
            
            dbg("inside student assignment detailMandatoryValidation");
           
            
            if(studentAssignment.getAssignmentDescription()==null||studentAssignment.getAssignmentDescription().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","AssignmentDescription");  
            }
            if(studentAssignment.getDueDate()==null||studentAssignment.getDueDate().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","Due Date");  
            }
            if(studentAssignment.getTeacherComment()==null||studentAssignment.getTeacherComment().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","Teacher Comment");  
            }
//            if(studentAssignment.getCompletedDate()==null||studentAssignment.getCompletedDate().isEmpty()){
//               status=false;  
//               errhandler.log_app_error("BS_VAL_002","Completed Date");  
//            }
//            if(studentAssignment.getStatus()==null||studentAssignment.getStatus().isEmpty()){
//               status=false;  
//               errhandler.log_app_error("BS_VAL_002","Status");  
//            }
//            if(studentAssignment.getParentComment()==null||studentAssignment.getParentComment().isEmpty()){
//               status=false;  
//               errhandler.log_app_error("BS_VAL_002","Parent Comment");  
//            }
            if(studentAssignment.getAssignentType()==null||studentAssignment.getAssignentType().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","AssignmentType");  
            }
            
            
           dbg("end of student assignment detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student assignment detailDataValidation");
             RequestBody<StudentAssignment> reqBody = request.getReqBody();
             StudentAssignment studentAssignment =reqBody.get();
             String l_source=request.getReqHeader().getSource();
             BSValidation bsv=inject.getBsv(session);
             String l_dueDate=studentAssignment.getDueDate();
//             String l_completedDate=studentAssignment.getCompletedDate();
            
             if(!bsv.dateFormatValidation(l_dueDate, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Due Date");
             }
//             if(l_source.equals("cohesive_frontend")){
//              if(!bsv.dateFormatValidation(l_completedDate, session, dbSession, inject)){
//                 status=false;
//                 errhandler.log_app_error("BS_VAL_003","Completed Date");
//             }
//             }
            dbg("end of student assignment detailDataValidation");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        
        return status;
              
    }
    public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     try{
        dbg("inside StudentAssignment--->getExistingAudit") ;
        exAudit=new ExistingAudit();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_operation=request.getReqHeader().getOperation();
        String l_service=request.getReqHeader().getService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
        if(!(l_operation.equals("Create")||l_operation.equals("View"))){
             
              if(l_operation.equals("AutoAuth")&&l_versionNumber.equals("1")){
                return null;
              }else{  
               dbg("inside StudentAssignment--->getExistingAudit--->Service--->UserAssignment");  
               RequestBody<StudentAssignment> studentAssignmentBody = request.getReqBody();
               StudentAssignment studentAssignment =studentAssignmentBody.get();
               String l_studentID=studentAssignment.getStudentID();
               String l_assignmentID=studentAssignment.getAssignmentID();
               String[] l_pkey={l_assignmentID};
               DBRecord l_studentAssignmentRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_ASSIGNMENT", l_pkey, session, dbSession);
               exAudit.setAuthStatus(l_studentAssignmentRecord.getRecord().get(14).trim());
               exAudit.setMakerID(l_studentAssignmentRecord.getRecord().get(9).trim());
               exAudit.setRecordStatus(l_studentAssignmentRecord.getRecord().get(13).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_studentAssignmentRecord.getRecord().get(15).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of StudentAssignment--->getExistingAudit") ;
        }
        }else{
            return null;
        }
        
    }catch(DBValidationException ex){
      throw ex;
     
     }catch(DBProcessingException ex){   
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
     return exAudit;
 }   
    
    public  void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     
     try{
        
        dbg("inside relationshipProcessing") ;
        
        
        if(exAudit.getRelatioshipOperation().equals("C")){

             createOrUpdateStudentCalender();
        }else if(exAudit.getRelatioshipOperation().equals("M")){
            
             createOrUpdateStudentCalender();
        }else if(exAudit.getRelatioshipOperation().equals("D")){
            
            deleteStudentCalender();
        }
        
   
         dbg("end of relationshipProcessing");
         }catch(DBValidationException ex){
             throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
             throw new DBProcessingException(ex.toString());
        }catch(BSValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
 }
    
 private void createOrUpdateStudentCalender()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
     
        try{
        dbg("inside createOrUpdateStudentCalender") ;  
        boolean recordExistence=true;
        ErrorHandler errHandler=session.getErrorhandler();
        IDBTransactionService dbts=inject.getDBTransactionService();
        BusinessService bs=inject.getBusinessService(session);
        RequestBody<StudentAssignment> reqBody = request.getReqBody();
        StudentAssignment studentAssignment =reqBody.get();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_studentID=studentAssignment.getStudentID();
        String l_dueDate=studentAssignment.getDueDate();
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_dueDate);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth();
        String l_day=convertedDate.getDay();
        int day=Integer.parseInt(l_day);
        String[] l_pkey={l_studentID,l_year,l_month};
        DBRecord studentCalenderRec;
        String l_previousMonthEvent=null;
        String newDayEvent=null;
        String currentEvent=getCurrentEventFromBO();
        String pKeyOfCurrentEvent=studentAssignment.getAssignmentID();
        String previousEvent=null;
        try{
        
            studentCalenderRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_CALENDER", l_pkey, session, dbSession);
            l_previousMonthEvent=studentCalenderRec.getRecord().get(3).trim();
            dbg("l_previousMonthEvent"+l_previousMonthEvent);
        }catch(DBValidationException ex){
            if(ex.toString().contains("DB_VAL_011")){
               
                errHandler.removeSessionErrCode("DB_VAL_011");
               recordExistence=false;                
            }
        }
        
        dbg("recordExistence"+recordExistence);
        
        if(recordExistence){
            
            String l_previousDayEvents=l_previousMonthEvent.split("d")[day];
            dbg("l_previousDayEvents"+l_previousDayEvents);
            if(l_previousDayEvents.contains("AS")){
                dbg("l_previousDayEvents.contains AS");
                String[]l_eventArray=l_previousDayEvents.split("//");
                dbg("l_eventArray size"+l_eventArray.length);
                boolean pkExistence=false;
                for(int i=1;i<l_eventArray.length;i++){
                    previousEvent=l_eventArray[i];
                    dbg("previousEvent"+previousEvent);
                    String l_pkColumnsWithoutVersion=bs.getPKcolumnswithoutVersion("STUDENT","SVW_STUDENT_ASSIGNMENT",session,inject);
                    int l_lengthOfPkWithoutVersion=l_pkColumnsWithoutVersion.split("~").length;
                    String l_pkey_of_ExistingEvent=bs.getPkeyOfExistingEvent(l_lengthOfPkWithoutVersion,previousEvent);
                    dbg("l_pkey_of_ExistingEvent"+l_pkey_of_ExistingEvent);
                    if(pKeyOfCurrentEvent.equals(l_pkey_of_ExistingEvent)){
                        dbg("pKeyOfCurrentEvent.equals(l_pkey_of_ExistingEvent)");
                        pkExistence=true;
                        break;
                    }
                    
                }
                
                dbg("after for loop pk existence"+pkExistence);
                if(pkExistence){
                
                     newDayEvent=l_previousDayEvents.replace(previousEvent+"//", currentEvent);
                     dbg("newDayEvent"+newDayEvent);
                }else{
                    
                    newDayEvent=l_previousDayEvents.concat(currentEvent);
                    dbg("newDayEvent"+newDayEvent);
                }
            
            
            }else{
                dbg("l_previousDayEvents not contains AS");
                newDayEvent=l_previousDayEvents.concat(currentEvent);
            }
            
            String newMonthEvent=bs.setDayEventinMonthEvent(l_previousMonthEvent,l_previousDayEvents,newDayEvent,l_day);
            Map<String,String>l_column_to_update=new HashMap();
            l_column_to_update.put("4", newMonthEvent);
             
             dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_CALENDER", l_pkey, l_column_to_update, session);   
            

        }else{
            String l_monthEvent=bs.createMonthEvent(l_dueDate);
            dbg(l_day+"//"+" "+"d");
            dbg(l_day+"//"+currentEvent+"d");
            
            newDayEvent =l_monthEvent.replace("d"+l_day+"//"+" "+"d", "d"+l_day+"//"+currentEvent+"d");
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",73,l_studentID,l_year,l_month,newDayEvent);
        }
        
        
        
        
          
        dbg("end of buildRequestAndCallStudentMaster");  
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
          
 }   
    
 private void deleteStudentCalender()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
     
        try{
        dbg("inside deleteStudentCalender");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        BusinessService bs=inject.getBusinessService(session);
        RequestBody<StudentAssignment> reqBody = request.getReqBody();
        StudentAssignment studentAssignment =reqBody.get();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_studentID=studentAssignment.getStudentID();
        String l_dueDate=studentAssignment.getDueDate();
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_dueDate);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth();
        String l_day=convertedDate.getDay();
        int day=Integer.parseInt(l_day);
        String[] l_pkey={l_studentID,l_year,l_month};
        DBRecord studentCalenderRec;
        String l_previousMonthEvent=null;
        String newDayEvent=null;
        
        String currentEvent=getCurrentEventFromBO();

        
            studentCalenderRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_CALENDER", l_pkey, session, dbSession);
            l_previousMonthEvent=studentCalenderRec.getRecord().get(3).trim();
            dbg("l_previousMonthEvent"+l_previousMonthEvent);
            String l_previousDayEvents=l_previousMonthEvent.split("d")[day];
            dbg("l_previousDayEvents"+l_previousDayEvents);
            
            if(l_previousDayEvents.split("//").length==2){
               newDayEvent=l_previousDayEvents.replace(currentEvent, " ");
            
            }else{
                
                newDayEvent=l_previousDayEvents.replace(currentEvent, "");
            } 
            String newMonthEvent=bs.setDayEventinMonthEvent(l_previousMonthEvent,l_previousDayEvents,newDayEvent,l_day);
            
            Map<String,String>l_column_to_update=new HashMap();
            l_column_to_update.put("4", newMonthEvent);
             
            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_CALENDER", l_pkey, l_column_to_update, session);   
            

        
        
        
        
        
          
       dbg("inside deleteStudentCalender");
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
          
 }   
 

 
 private String getCurrentEventFromBO()throws BSProcessingException{
     
     try{
         
        dbg("inside StudentAssignment--->getCurrentEventFromBO");
        RequestBody<StudentAssignment> reqBody = request.getReqBody();
        StudentAssignment studentAssignment =reqBody.get(); 
        String l_subjectID=studentAssignment.getSubjectID();
        String l_assignmentDescription=studentAssignment.getAssignmentDescription();
         String l_assignmentID=studentAssignment.getAssignmentID();
        String currentEvent="AS"+","+l_assignmentID+","+l_subjectID+","+l_assignmentDescription+"//";
         
        dbg("currentEvent"+currentEvent); 
        return  currentEvent;
     }catch (Exception ex) {
       dbg(ex);
       throw new BSProcessingException("Exception" + ex.toString());
     }

 }

 
 
   
 
 public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }   
    
    
}
