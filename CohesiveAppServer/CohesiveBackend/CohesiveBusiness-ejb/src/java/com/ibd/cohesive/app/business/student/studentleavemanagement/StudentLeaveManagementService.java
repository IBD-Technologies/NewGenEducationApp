/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentleavemanagement;

import com.ibd.businessViews.IStudentLeaveManagementService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.exception.ExceptionHandler;
import com.ibd.cohesive.app.business.util.message.request.Parsing;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.core.pdata.IPDataService;
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
//@Local(IStudentLeaveManagementService.class)
@Remote(IStudentLeaveManagementService.class)
@Stateless
public class StudentLeaveManagementService implements IStudentLeaveManagementService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentLeaveManagementService(){
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
       dbg("inside StudentLeaveManagementService--->processing");
       dbg("StudentLeaveManagementService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<StudentLeaveManagement> reqBody = request.getReqBody();
       StudentLeaveManagement studentLeaveManagement =reqBody.get();
       String l_studentID=studentLeaveManagement.getStudentID();
       String l_from=studentLeaveManagement.getFrom();
       String l_to=studentLeaveManagement.getTo();
       l_lockKey=l_studentID+"~"+l_from+"~"+l_to;
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       
       BusinessEJB<IStudentLeaveManagementService>studentLeaveManagementEJB=new BusinessEJB();
       studentLeaveManagementEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentLeaveManagementEJB);
       if(request.getReqHeader().getOperation().equals("View")){
           
         String studentID=  bs.studentValidation(studentLeaveManagement.getStudentID(), studentLeaveManagement.getStudentName(), request.getReqHeader().getInstituteID(), session, dbSession, inject);
         
          
         if(studentID==null||studentID.isEmpty()){
             
             errhandler.log_app_error("BS_VAL_002","Student ID or Name");  
             throw new BSValidationException("BSValidationException");
         }
         
         studentLeaveManagement.setStudentID(studentID);
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
      
       bs.businessServiceProcssing(request, exAudit, inject,studentLeaveManagementEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentLeaveManagementEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student leaveManagement");
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
            if(l_lockKey!=null){
               businessLock.removeBusinessLock(request, l_lockKey, session);
            }
           request=null;
            bs=inject.getBusinessService(session);
            if(l_session_created_now){
                bs.responselogging(jsonResponse, inject,session,dbSession);
                dbg("Response"+jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
//                if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
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
      StudentLeaveManagement studentLeaveManagement=new StudentLeaveManagement();
      RequestBody<StudentLeaveManagement> reqBody = new RequestBody<StudentLeaveManagement>(); 
           
      try{
      dbg("inside student leaveManagement buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      studentLeaveManagement.setStudentID(l_body.getString("studentID"));
      studentLeaveManagement.setStudentName(l_body.getString("studentName"));
      studentLeaveManagement.setFrom(l_body.getString("from"));
      studentLeaveManagement.setTo(l_body.getString("to"));

      
      if(!(l_operation.equals("View"))){
          studentLeaveManagement.setType(l_body.getString("type"));
          studentLeaveManagement.setReason(l_body.getString("reason"));
          studentLeaveManagement.setFromNoon(l_body.getString("fromNoon"));
          studentLeaveManagement.setToNoon(l_body.getString("toNoon"));
      
      }
        reqBody.set(studentLeaveManagement);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
    try{ 
        dbg("inside stident leaveManagement create"); 
        RequestBody<StudentLeaveManagement> reqBody = request.getReqBody();
        StudentLeaveManagement studentLeaveManagement =reqBody.get();
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();   
        String l_studentID=studentLeaveManagement.getStudentID();
        String l_from=studentLeaveManagement.getFrom();
        String l_to=studentLeaveManagement.getTo();
        String l_type=studentLeaveManagement.getType();
        String l_reason=studentLeaveManagement.getReason();
        String l_fromNoon=studentLeaveManagement.getFromNoon();
        String l_toNoon=studentLeaveManagement.getToNoon();
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE",55,l_studentID,l_from,l_to,l_type,l_reason,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks,l_fromNoon,l_toNoon);

        
        
        dbg("end of student leaveManagement create"); 
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
    
     

    
    public void authUpdate()throws DBValidationException,DBProcessingException,BSProcessingException{
        
     try{ 
        dbg("inside student leaveManagement--->auth update"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<StudentLeaveManagement> reqBody = request.getReqBody();
        StudentLeaveManagement studentLeaveManagement =reqBody.get();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_studentID=studentLeaveManagement.getStudentID();
        String l_from=studentLeaveManagement.getFrom();
        String l_to=studentLeaveManagement.getTo(); 
        String[] l_primaryKey={l_studentID,l_from,l_to};
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("7", l_checkerID);
         l_column_to_update.put("9", l_checkerDateStamp);
         l_column_to_update.put("11", l_authStatus);
         l_column_to_update.put("14", l_checkerRemarks);
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE","SVW_STUDENT_LEAVE_MANAGEMENT", l_primaryKey, l_column_to_update, session);
         dbg("end of student leaveManagement--->auth update");          
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
    public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
        
       Map<String,String> l_column_to_update;
                
       try{ 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentLeaveManagement> reqBody = request.getReqBody();
        StudentLeaveManagement studentLeaveManagement =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();   
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();   
        
        String l_studentID=studentLeaveManagement.getStudentID();
        String l_from=studentLeaveManagement.getFrom();
        String l_to=studentLeaveManagement.getTo();
        String l_type=studentLeaveManagement.getType();
        String l_reason=studentLeaveManagement.getReason();
        String l_fromNoon=studentLeaveManagement.getFromNoon();
        String l_toNoon=studentLeaveManagement.getToNoon();
        
        l_column_to_update= new HashMap();
        l_column_to_update.put("1", l_studentID);
        l_column_to_update.put("2", l_from);
        l_column_to_update.put("3", l_to);
        l_column_to_update.put("4", l_type);
        l_column_to_update.put("5", l_reason);
        l_column_to_update.put("6", l_makerID);
        l_column_to_update.put("7", l_checkerID);
        l_column_to_update.put("8", l_makerDateStamp);
        l_column_to_update.put("9", l_checkerDateStamp);
        l_column_to_update.put("10", l_recordStatus);
        l_column_to_update.put("11", l_authStatus);
        l_column_to_update.put("12", l_versionNumber);
        l_column_to_update.put("13", l_makerRemarks);
        l_column_to_update.put("14", l_checkerRemarks);
        l_column_to_update.put("15", l_fromNoon);
        l_column_to_update.put("16", l_toNoon);
         
                   String[] l_primaryKey={l_studentID,l_from,l_to};
                   dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE","SVW_STUDENT_LEAVE_MANAGEMENT", l_primaryKey, l_column_to_update, session);   
                  
        
        
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
        dbg("inside student leaveManagement delete");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentLeaveManagement> reqBody = request.getReqBody();
        StudentLeaveManagement studentLeaveManagement =reqBody.get();
        String l_studentID=studentLeaveManagement.getStudentID();
        String l_from=studentLeaveManagement.getFrom();
        String l_to=studentLeaveManagement.getTo(); 
        String[] l_primaryKey={l_studentID,l_from,l_to};
        
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE","SVW_STUDENT_LEAVE_MANAGEMENT", l_primaryKey, session);
            dbg("end of student leaveManagement delete");
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

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student leaveManagement--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentLeaveManagement> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        StudentLeaveManagement studentLeaveManagement =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_studentID=studentLeaveManagement.getStudentID();
        String l_from=studentLeaveManagement.getFrom();
        String l_to=studentLeaveManagement.getTo(); 
        String[] l_pkey={l_studentID,l_from,l_to};
        DBRecord leaveManagementRec=null;
        
        try{
        
        
           leaveManagementRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE", "SVW_STUDENT_LEAVE_MANAGEMENT", l_pkey, session, dbSession);
       
         
         }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", "");
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
                }
         
         
        buildBOfromDB(leaveManagementRec);
        
          dbg("end of  completed student leaveManagement--->view");  
        }catch(BSValidationException ex){
            throw ex;  
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
      private void buildBOfromDB(DBRecord p_studentLeaveManagementRecord)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<StudentLeaveManagement> reqBody = request.getReqBody();
           StudentLeaveManagement studentLeaveManagement =reqBody.get();
           BusinessService bs=inject.getBusinessService(session);
           String l_studentID=studentLeaveManagement.getStudentID();
           String l_instituteID=request.getReqHeader().getInstituteID();
           ArrayList<String>l_studentLeaveManagementList= p_studentLeaveManagementRecord.getRecord();
           
           if(l_studentLeaveManagementList!=null&&!l_studentLeaveManagementList.isEmpty()){
               
            String studentName=bs.getStudentName(l_studentID, l_instituteID, session, dbSession, inject);
            studentLeaveManagement.setStudentName(studentName);
            studentLeaveManagement.setType(l_studentLeaveManagementList.get(3).trim());
            studentLeaveManagement.setReason(l_studentLeaveManagementList.get(4).trim());
            request.getReqAudit().setMakerID(l_studentLeaveManagementList.get(5).trim());
            request.getReqAudit().setCheckerID(l_studentLeaveManagementList.get(6).trim());
            request.getReqAudit().setMakerDateStamp(l_studentLeaveManagementList.get(7).trim());
            request.getReqAudit().setCheckerDateStamp(l_studentLeaveManagementList.get(8).trim());
            request.getReqAudit().setRecordStatus(l_studentLeaveManagementList.get(9).trim());
            request.getReqAudit().setAuthStatus(l_studentLeaveManagementList.get(10).trim());
            request.getReqAudit().setVersionNumber(l_studentLeaveManagementList.get(11).trim());
            request.getReqAudit().setMakerRemarks(l_studentLeaveManagementList.get(12).trim());
            request.getReqAudit().setCheckerRemarks(l_studentLeaveManagementList.get(13).trim());
            studentLeaveManagement.setFromNoon(l_studentLeaveManagementList.get(14).trim());
            studentLeaveManagement.setToNoon(l_studentLeaveManagementList.get(15).trim());
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
        dbg("inside student leaveManagement buildJsonResFromBO");    
        RequestBody<StudentLeaveManagement> reqBody = request.getReqBody();
        StudentLeaveManagement studentLeaveManagement =reqBody.get();
         body=Json.createObjectBuilder().add("studentID", studentLeaveManagement.getStudentID())
                                        .add("studentName", studentLeaveManagement.getStudentName())
                                        .add("from", studentLeaveManagement.getFrom())
                                        .add("to", studentLeaveManagement.getTo())
                                        .add("fromNoon", studentLeaveManagement.getFromNoon())
                                        .add("toNoon", studentLeaveManagement.getToNoon())
                                        .add("type", studentLeaveManagement.getType())
                                        .add("reason", studentLeaveManagement.getReason()).build();
                                            
              dbg(body.toString());  
           dbg("end of student leaveManagement buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student leaveManagement--->businessValidation");    
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
       dbg("end of student leaveManagement--->businessValidation"); 
       }catch(BSProcessingException ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
            
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
        dbg("inside student leaveManagement master mandatory validation");
        RequestBody<StudentLeaveManagement> reqBody = request.getReqBody();
        StudentLeaveManagement studentLeaveManagement =reqBody.get();
        
         if(studentLeaveManagement.getStudentID()==null||studentLeaveManagement.getStudentID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","studentID");  
         }
          if(studentLeaveManagement.getFrom()==null||studentLeaveManagement.getFrom().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","from Date");  
         }
          if(studentLeaveManagement.getTo()==null||studentLeaveManagement.getTo().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","To Date");  
         }
          
          
        dbg("end of student leaveManagement master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student leaveManagement masterDataValidation");
             RequestBody<StudentLeaveManagement> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             BusinessService bs=inject.getBusinessService(session);
             StudentLeaveManagement studentLeaveManagement =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_studentID=studentLeaveManagement.getStudentID();
             String l_from=studentLeaveManagement.getFrom();
             String l_to=studentLeaveManagement.getTo();
             String l_operation=request.getReqHeader().getOperation();
             
             if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","studentID");
             }
             if(!bsv.dateFormatValidation(l_from, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","from date");
             }
             if(!bsv.dateFormatValidation(l_to, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","To date");
             }
             if(!bsv.leaveDateValidation(l_from,l_to, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","From and To Dates");
             }
            
             
                 
                 
                 if(l_operation.equals("Create")){    

                     if(!bsv.pastDateValidation(l_from, session, dbSession, inject)){
                         status=false;
                         errhandler.log_app_error("BS_VAL_003","From Date");
                     }
                     if(!bsv.pastDateValidation(l_to, session, dbSession, inject)){
                         status=false;
                         errhandler.log_app_error("BS_VAL_003","To Date");
                     }

                 }
                 int dateSize= bs.getLeaveDates(l_from, l_to, session, dbSession, inject).size();
                 
                 if(dateSize>3){
                     
                     status=false;
                     errhandler.log_app_error("BS_VAL_053",null);
                     
                     
                 }
             
            
            dbg("end of student leaveManagement masterDataValidation");
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
        RequestBody<StudentLeaveManagement> reqBody = request.getReqBody();
        StudentLeaveManagement studentLeaveManagement =reqBody.get();
        
        try{
            
            dbg("inside student leaveManagement detailMandatoryValidation");
           
            if(studentLeaveManagement.getFromNoon()==null||studentLeaveManagement.getFromNoon().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","fromNoon");  
            }
//            if(studentLeaveManagement.getToNoon()==null||studentLeaveManagement.getToNoon().isEmpty()){
//               status=false;  
//               errhandler.log_app_error("BS_VAL_002","toNoon");  
//            } 
            
            if(studentLeaveManagement.getType()==null||studentLeaveManagement.getType().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","Type");  
            }
            if(studentLeaveManagement.getReason()==null||studentLeaveManagement.getReason().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","Reason");  
            }

            
            
            
           dbg("end of student leaveManagement detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student leaveManagement detailDataValidation");
             RequestBody<StudentLeaveManagement> reqBody = request.getReqBody();
             StudentLeaveManagement studentLeaveManagement =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             BSValidation bsv=inject.getBsv(session);
             String l_leaveType=studentLeaveManagement.getType();
             String l_fromNoon=studentLeaveManagement.getFromNoon();
             String l_toNoon=studentLeaveManagement.getToNoon();
             String fromDate=studentLeaveManagement.getFrom();
             String toDate=studentLeaveManagement.getTo();
            
             if(!bsv.leaveTypeValidation(l_leaveType,l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Leave Type");
             }
             
             if(!bsv.leaveNoonValidation(l_fromNoon,l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","From Noon");
             }
             
//             if(!bsv.leaveNoonValidation(l_toNoon,l_instituteID, session, dbSession, inject)){
//                 status=false;
//                 errhandler.log_app_error("BS_VAL_003","To Noon");
//             }
//             
             
             
             if(fromDate.equals(toDate)){
                 
                 if(l_toNoon!=null&&!l_toNoon.isEmpty()){
                     
                     
                     if(!l_fromNoon.equals(l_toNoon)){
                         
                         status=false; 
                         errhandler.log_app_error("BS_VAL_074",null);
                         throw new BSValidationException("BSValidationException");                     }
                 }else{
                     
                     studentLeaveManagement.setToNoon(l_fromNoon);
                 }
                 
             }else{
                 
                if(l_toNoon==null||l_toNoon.isEmpty()){
                    
                    status=false;
                    errhandler.log_app_error("BS_VAL_002","To Noon");
                    throw new BSValidationException("BSValidationException");
                    
                } 
                 
                  
                  
                  
             }
             
             
            
             
             
             
            dbg("end of student leaveManagement detailDataValidation");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch(BSValidationException ex){
            throw ex;    
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
        dbg("inside StudentLeaveManagement--->getExistingAudit") ;
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
               dbg("inside StudentLeaveManagement--->getExistingAudit--->Service--->UserLeaveManagement");  
               RequestBody<StudentLeaveManagement> studentLeaveManagementBody = request.getReqBody();
               StudentLeaveManagement studentLeaveManagement =studentLeaveManagementBody.get();
               String l_studentID=studentLeaveManagement.getStudentID();
               String l_from=studentLeaveManagement.getFrom();
               String l_to=studentLeaveManagement.getTo();
               String[] l_pkey={l_studentID,l_from,l_to};
               DBRecord l_studentLeaveManagementRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE", "SVW_STUDENT_LEAVE_MANAGEMENT", l_pkey, session, dbSession);
               exAudit.setAuthStatus(l_studentLeaveManagementRecord.getRecord().get(10).trim());
               exAudit.setMakerID(l_studentLeaveManagementRecord.getRecord().get(5).trim());
               exAudit.setRecordStatus(l_studentLeaveManagementRecord.getRecord().get(9).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_studentLeaveManagementRecord.getRecord().get(11).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of StudentLeaveManagement--->getExistingAudit") ;
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
    
   
 
 public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }   
    
    public void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     try{
        dbg("Inside relationshipProcessing"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IPDataService pds=inject.getPdataservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String recordStatus=request.getReqAudit().getRecordStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<StudentLeaveManagement> studentLeaveManagementBody = request.getReqBody();
        StudentLeaveManagement studentLeaveManagement =studentLeaveManagementBody.get();
        String l_studentID=studentLeaveManagement.getStudentID();
        String[] pkey={l_studentID};
        ArrayList<String>studentList=    pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",pkey);
        String l_standard=studentList.get(2).trim();
        String l_section=studentList.get(3).trim();
        String l_from=studentLeaveManagement.getFrom();
        String l_to=studentLeaveManagement.getTo();
        String l_fromNoon=studentLeaveManagement.getFromNoon();
        String l_toNoon=studentLeaveManagement.getToNoon();
        String[] l_pkey={l_studentID,l_from,l_to};
        
        
        
        if(l_versionNumber.equals("1")){
            
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE",317,l_studentID,l_from,l_to,l_fromNoon,l_toNoon);
            
            
            
        }else if(!l_versionNumber.equals("1")&&!recordStatus.equals("D")){
            
             Map<String,String>column_to_update=new HashMap();
             column_to_update.put("4", l_fromNoon);
             column_to_update.put("5", l_toNoon);

             dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE", "CLASS_LEAVE_MANAGEMENT", l_pkey, column_to_update, session);
            
        }else{
            
            
             dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE", "CLASS_LEAVE_MANAGEMENT", l_pkey, session);
        }
        
        
       
       

        dbg("End of relationshipProcessing");
         
         
         
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
}