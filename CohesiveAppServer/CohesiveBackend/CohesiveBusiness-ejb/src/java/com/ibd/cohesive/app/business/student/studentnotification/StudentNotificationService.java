/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentnotification;

import com.ibd.businessViews.IStudentNotificationService;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
//@Local(IStudentNotificationService.class)
@Remote(IStudentNotificationService.class)
@Stateless
public class StudentNotificationService implements IStudentNotificationService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentNotificationService(){
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
       dbg("inside StudentNotificationService--->processing");
       dbg("StudentNotificationService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson);
       RequestBody<StudentNotification> reqBody = request.getReqBody();
       StudentNotification studentNotification =reqBody.get();
       String l_studentID=studentNotification.getStudentID();
       String l_notificationID=studentNotification.getNotificationID();
       l_lockKey=l_studentID+"~"+l_notificationID;
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       
       BusinessEJB<IStudentNotificationService>studentNotificationEJB=new BusinessEJB();
       studentNotificationEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentNotificationEJB);
       if(request.getReqHeader().getOperation().equals("View")){
           
         String studentID=  bs.studentValidation(studentNotification.getStudentID(), studentNotification.getStudentName(), request.getReqHeader().getInstituteID(), session, dbSession, inject);
         
          
         if(studentID==null||studentID.isEmpty()){
             
             errhandler.log_app_error("BS_VAL_002","Student ID or Name");  
             throw new BSValidationException("BSValidationException");
         }
         
         studentNotification.setStudentID(studentID);
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
      
       bs.businessServiceProcssing(request, exAudit, inject,studentNotificationEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentNotificationEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student notification");
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
      StudentNotification studentNotification=new StudentNotification();
      RequestBody<StudentNotification> reqBody = new RequestBody<StudentNotification>(); 
           
      try{
      dbg("inside student notification buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      studentNotification.setStudentID(l_body.getString("studentID"));
      studentNotification.setStudentName(l_body.getString("studentName"));
      studentNotification.setNotificationID(l_body.getString("notificationID"));
      studentNotification.setNotificationDate(l_body.getString("notificationDate"));
      
      if(!(l_operation.equals("View"))){
           studentNotification.setNotificationType(l_body.getString("notificationType"));
           studentNotification.setMessage(l_body.getString("message"));
           
      
      }
        reqBody.set(studentNotification);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
    try{ 
        dbg("inside stident notification create"); 
        RequestBody<StudentNotification> reqBody = request.getReqBody();
        StudentNotification studentNotification =reqBody.get();
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
        String l_studentID=studentNotification.getStudentID();
        String l_notificationID=studentNotification.getNotificationID();
        String l_notificationType=studentNotification.getNotificationType();
        String l_message=studentNotification.getMessage();
        
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",123,l_studentID,l_notificationID,l_notificationType,l_message,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);

        
        
        dbg("end of student notification create"); 
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
        dbg("inside student notification--->auth update"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<StudentNotification> reqBody = request.getReqBody();
        StudentNotification studentNotification =reqBody.get();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_studentID=studentNotification.getStudentID();
        String l_notificationID=studentNotification.getNotificationID();
        String[] l_primaryKey={l_notificationID};
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("6", l_checkerID);
         l_column_to_update.put("8", l_checkerDateStamp);
         l_column_to_update.put("10", l_authStatus);
         l_column_to_update.put("13", l_checkerRemarks);
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_NOTIFICATION", l_primaryKey, l_column_to_update, session);
         dbg("end of student notification--->auth update");          
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
        RequestBody<StudentNotification> reqBody = request.getReqBody();
        StudentNotification studentNotification =reqBody.get();
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
        
        String l_studentID=studentNotification.getStudentID();
        String l_notificationID=studentNotification.getNotificationID();
        String l_notificationType=studentNotification.getNotificationType();
        String l_message=studentNotification.getMessage();
        
        l_column_to_update= new HashMap();
        l_column_to_update.put("1", l_studentID);
        l_column_to_update.put("2", l_notificationID);
        l_column_to_update.put("3", l_notificationType);
        l_column_to_update.put("4", l_message);
        l_column_to_update.put("5", l_makerID);
        l_column_to_update.put("6", l_checkerID);
        l_column_to_update.put("7", l_makerDateStamp);
        l_column_to_update.put("8", l_checkerDateStamp);
        l_column_to_update.put("9", l_recordStatus);
        l_column_to_update.put("10", l_authStatus);
        l_column_to_update.put("11", l_versionNumber);
        l_column_to_update.put("12", l_makerRemarks);
        l_column_to_update.put("13", l_checkerRemarks);
         
                   String[] l_primaryKey={l_notificationID};
                   dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_NOTIFICATION", l_primaryKey, l_column_to_update, session);   
                  
        
        
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
        dbg("inside student notification delete");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentNotification> reqBody = request.getReqBody();
        StudentNotification studentNotification =reqBody.get();
        String l_studentID=studentNotification.getStudentID();
        String l_notificationID=studentNotification.getNotificationID();
        String[] l_primaryKey={l_notificationID};
        
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_NOTIFICATION", l_primaryKey, session);
            dbg("end of student notification delete");
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
        dbg("inside student notification--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentNotification> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        StudentNotification studentNotification =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_studentID=studentNotification.getStudentID();
        String l_notificationID=studentNotification.getNotificationID();
        String l_notificationDate=studentNotification.getNotificationDate();
        String[] l_pkey={l_studentID,l_notificationID,l_notificationDate};
        DBRecord notificationRec=null;
        Map<String,DBRecord>notificationStatusMap=null;
        
          try{
        
        
                notificationRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT", "SVW_STUDENT_NOTIFICATION", l_pkey, session, dbSession);
                
                notificationStatusMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT", "STUDENT_NOTIFICATION_STATUS", session, dbSession);
        
        }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", l_notificationID);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
                }
        
        buildBOfromDB(notificationRec,notificationStatusMap);
        
          dbg("end of  completed student notification--->view");   
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
      private void buildBOfromDB(DBRecord p_studentNotificationRecord,Map<String,DBRecord>notificationStatusMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<StudentNotification> reqBody = request.getReqBody();
           String l_instituteID=request.getReqHeader().getInstituteID();
           StudentNotification studentNotification =reqBody.get();
           String notificationID=studentNotification.getNotificationID();
           ArrayList<String>l_studentNotificationList= p_studentNotificationRecord.getRecord();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           String studentID=studentNotification.getStudentID();
           String notificationDate=studentNotification.getNotificationDate();
           BusinessService bs=inject.getBusinessService(session);
           
           if(l_studentNotificationList!=null&&!l_studentNotificationList.isEmpty()){
               
               
               String[] l_pkey={notificationID};
               DBRecord notificationRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","INSTITUTE","IVW_NOTIFICATION_MASTER", l_pkey, session,dbSession);
               String studentName=bs.getStudentName(studentID, l_instituteID, session, dbSession, inject);
               
               studentNotification.setStudentName(studentName);
               studentNotification.setNotificationType(notificationRecord.getRecord().get(2).trim());
               studentNotification.setFrequency(notificationRecord.getRecord().get(3).trim());
               studentNotification.setDate(notificationRecord.getRecord().get(4).trim());
               studentNotification.setDay(notificationRecord.getRecord().get(5).trim());
               studentNotification.setMonth(notificationRecord.getRecord().get(6).trim());
               studentNotification.setMessage(notificationRecord.getRecord().get(7).trim());
               studentNotification.setMediaCommunication(notificationRecord.getRecord().get(8).trim());
               studentNotification.setInstant(notificationRecord.getRecord().get(19).trim());
               request.getReqAudit().setMakerID(notificationRecord.getRecord().get(10).trim());
               request.getReqAudit().setCheckerID(notificationRecord.getRecord().get(11).trim());
               request.getReqAudit().setMakerDateStamp(notificationRecord.getRecord().get(12).trim());
               request.getReqAudit().setCheckerDateStamp(notificationRecord.getRecord().get(13).trim());
               request.getReqAudit().setRecordStatus(notificationRecord.getRecord().get(14).trim());
               request.getReqAudit().setAuthStatus(notificationRecord.getRecord().get(15).trim());
               request.getReqAudit().setVersionNumber(notificationRecord.getRecord().get(16).trim());
               request.getReqAudit().setMakerRemarks(notificationRecord.getRecord().get(17).trim());
               request.getReqAudit().setCheckerRemarks(notificationRecord.getRecord().get(18).trim());
          
               List<DBRecord>notificationStatusList=notificationStatusMap.values().stream().filter(rec->rec.getRecord().get(0).trim().equals(studentID)&&rec.getRecord().get(1).trim().equals(notificationID)&&rec.getRecord().get(2).trim().equals(notificationDate)).collect(Collectors.toList());
           
               studentNotification.status=new NotificationStatus[notificationStatusList.size()];
               for(int i=0;i<notificationStatusList.size();i++){
                   
                   DBRecord notificationStatusRecord=notificationStatusList.get(i);
                   
                   studentNotification.status[i]=new NotificationStatus();
                   studentNotification.status[i].setDate(notificationStatusRecord.getRecord().get(2).trim());
                   studentNotification.status[i].setEndPoint(notificationStatusRecord.getRecord().get(3).trim());
                   studentNotification.status[i].setStatus(notificationStatusRecord.getRecord().get(4).trim());
                   studentNotification.status[i].setError(notificationStatusRecord.getRecord().get(5).trim());
               }
              
           }
            
           
           
           
           
          dbg("end of  buildBOfromDB"); 
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
    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside student notification buildJsonResFromBO");    
        RequestBody<StudentNotification> reqBody = request.getReqBody();
        StudentNotification studentNotification =reqBody.get();
        JsonArrayBuilder statusArray=Json.createArrayBuilder();
        
        for(int i=0;i<studentNotification.status.length;i++){
            
            statusArray.add(Json.createObjectBuilder().add("date", studentNotification.status[i].getDate())
                                                      .add("endPoint", studentNotification.status[i].getEndPoint())
                                                      .add("error",studentNotification.status[i].getError())
                                                      .add("status", studentNotification.status[i].getStatus())); 
        }
        
        
        
        
        
        
        
             body=Json.createObjectBuilder().add("studentID", studentNotification.getStudentID())
                                            .add("studentName", studentNotification.getStudentName())
                                            .add("notificationID", studentNotification.getNotificationID())
                                            .add("notificationDate", studentNotification.getNotificationDate())
                                            .add("notificationType", studentNotification.getNotificationType())
                                            .add("notificationFrequency",studentNotification.getFrequency())
                                            .add("date",studentNotification.getDate() )
                                            .add("day",studentNotification.getDay()) 
                                            .add("month",studentNotification.getMonth() )
                                            .add("message",studentNotification.getMessage())
                                            .add("mediaCommunication",studentNotification.getMediaCommunication() )
                                            .add("instant",studentNotification.getInstant() )
                                            .add("status",statusArray )
                                            .build();
                                            
              dbg(body.toString());  
           dbg("end of student notification buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student notification--->businessValidation");    
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
       dbg("end of student notification--->businessValidation"); 
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
        dbg("inside student notification master mandatory validation");
        RequestBody<StudentNotification> reqBody = request.getReqBody();
        StudentNotification studentNotification =reqBody.get();
        
         if(studentNotification.getStudentID()==null||studentNotification.getStudentID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","studentID");  
         }
          if(studentNotification.getNotificationID()==null||studentNotification.getNotificationID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","notificationID");  
         }
          
          if(studentNotification.getNotificationDate()==null||studentNotification.getNotificationDate().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","notification Date");  
         }
        dbg("end of student notification master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student notification masterDataValidation");
             RequestBody<StudentNotification> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             StudentNotification studentNotification =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_studentID=studentNotification.getStudentID();
             
             if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","studentID");
             }

             
            
            dbg("end of student notification masterDataValidation");
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
        RequestBody<StudentNotification> reqBody = request.getReqBody();
        StudentNotification studentNotification =reqBody.get();
        
        try{
            
            dbg("inside student notification detailMandatoryValidation");
           
            
            if(studentNotification.getNotificationType()==null||studentNotification.getNotificationType().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","notificationType");  
            }

            
            
            
           dbg("end of student notification detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student notification detailDataValidation");
             RequestBody<StudentNotification> reqBody = request.getReqBody();
             StudentNotification studentNotification =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             BSValidation bsv=inject.getBsv(session);
             String l_notificationType=studentNotification.getNotificationType();
            
             if(!bsv.notificationTypeValidation(l_notificationType,l_instituteID, session, dbSession,inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","notificationType");
             }
             
            dbg("end of student notification detailDataValidation");
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
        dbg("inside StudentNotification--->getExistingAudit") ;
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
               dbg("inside StudentNotification--->getExistingAudit--->Service--->UserNotification");  
               RequestBody<StudentNotification> studentNotificationBody = request.getReqBody();
               StudentNotification studentNotification =studentNotificationBody.get();
               String l_studentID=studentNotification.getStudentID();
               String l_notificatiionID=studentNotification.getNotificationID();
               String[] l_pkey={l_notificatiionID};
               DBRecord l_studentNotificationRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_NOTIFICATION", l_pkey, session, dbSession);
               exAudit.setAuthStatus(l_studentNotificationRecord.getRecord().get(10).trim());
               exAudit.setMakerID(l_studentNotificationRecord.getRecord().get(5).trim());
               exAudit.setRecordStatus(l_studentNotificationRecord.getRecord().get(9).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_studentNotificationRecord.getRecord().get(11).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of StudentNotification--->getExistingAudit") ;
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
         
         return;
         
     }
}
