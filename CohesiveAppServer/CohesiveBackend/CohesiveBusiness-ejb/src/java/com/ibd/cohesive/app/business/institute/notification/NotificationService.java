/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.notification;

import com.ibd.businessViews.IAmazonEmailService;
import com.ibd.businessViews.IAmazonSMSService;
import com.ibd.businessViews.INotificationService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.NotificationUtil;
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
import com.ibd.cohesive.db.transaction.lock.ILockService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.Email;
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
 * @author IBD Technologies
 */
//@Local(INotificationService.class)
@Remote(INotificationService.class)
@Stateless
public class NotificationService implements INotificationService{
     AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public NotificationService(){
        try {
            inject=new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession = new DBSession(session);
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
         if (session.getDebug()!=null)
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
    
    
    public JsonObject processing(JsonObject requestJson) throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException
    {
       boolean l_validation_status=true;
       boolean l_session_created_now=false;
       JsonObject jsonResponse=null;
       JsonObject clonedResponse=null;
       JsonObject clonedJson=null;
       BusinessService  bs;
       Parsing parser;
       ExceptionHandler exHandler;
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
       dbg("inside NotificationService--->processing");
       dbg("NotificationService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson);  
       String l_operation=request.getReqHeader().getOperation();
       
       if(!l_operation.equals("Create-Default")){
       
           RequestBody<Notification> reqBody = request.getReqBody();
           Notification notification =reqBody.get();
           l_lockKey=notification.getNotificationID();

           if(!businessLock.getBusinessLock(request, l_lockKey, session)){
               l_validation_status=false;
               throw new BSValidationException("BSValidationException");
            }
       
       }
       BusinessEJB<INotificationService>notificationEJB=new BusinessEJB();
       notificationEJB.set(this);
       exAudit=bs.getExistingAudit(notificationEJB);
       
      if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
      }
       if(!l_operation.equals("Create-Default")){
      
           if(!businessValidation(errhandler)){
               l_validation_status=false;
               throw new BSValidationException("BSValidationException");

           } 
       }
      
      
       bs.businessServiceProcssing(request, exAudit, inject,notificationEJB);
       
       
       if(l_operation.equals("Create-Default")){
           
           createDefault();
       }
       
       
         if(l_session_created_now){
             jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,notificationEJB) ;
             tc.commit(session,dbSession);
             dbg("commit is called in  service");
        }
       dbg("NotificationService--->Processing--->O/P--->jsonResponse"+jsonResponse);     
       dbg("End of notificationService--->processing");     
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
       }catch(Exception ex){
           dbg(ex);
           if(l_session_created_now){
           exHandler = inject.getErrorHandle(session);
           jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson,"BSProcessingException");
          }else{
              throw ex;
          }    
      } finally{
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
    
    @Override
    public JsonObject processing(JsonObject p_request,CohesiveSession session)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException
    {
    CohesiveSession tempSession = this.session;
    try{
        this.session=session;
       return processing(p_request);
     }catch(DBValidationException ex){
         //dbg(ex);        
        throw ex;
        }catch(BSValidationException ex){
         //dbg(ex);        
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
 }

    private  void buildBOfromReq(JsonObject p_request)throws BSProcessingException,DBProcessingException{
       
       try{
       dbg("inside NotificationService--->buildBOfromReq"); 
       dbg("NotificationService--->buildBOfromReq--->I/P--->p_request"+p_request.toString()); 
       RequestBody<Notification> reqBody = new RequestBody<Notification>(); 
       Notification notification = new Notification();
       String l_operation=request.getReqHeader().getOperation();
       
       if(!l_operation.equals("Create-Default")){
       
       JsonObject l_body=p_request.getJsonObject("body");
       notification.setInstituteID(l_body.getString("instituteID"));
       notification.setInstituteName(l_body.getString("instituteName"));
       notification.setNotificationID(l_body.getString("notificationID"));
       if(!(l_operation.equals("View"))){
  
            notification.setNotificationType(l_body.getString("notificationType"));
            notification.setNotificationFrequency("I");
            
            if(l_body.getString("date").equals("Select option")){
            
               notification.setDate("");
            
            }else{
                
                notification.setDate(l_body.getString("date"));
            }
            
            notification.setDay(l_body.getString("day"));
            notification.setMonth(l_body.getString("month"));
            notification.setMessage(l_body.getString("message"));
            notification.setOtherLanguageMessage(l_body.getString("otherLanguageMessage"));
            notification.setMediaCommunication(l_body.getString("mediaCommunication"));
            notification.setAssignee(l_body.getString("assignee"));
            notification.setInstant(l_body.getString("instant"));
            notification.setEmail(l_body.getString("email"));
            notification.setMobileNo(l_body.getString("mobileNo"));
        }
       }
       reqBody.set(notification);
      request.setReqBody(reqBody);
      dbg("End of NotificationService--->buildBOfromReq");
      }
        catch(Exception ex){
             dbg(ex);
            throw new BSProcessingException("BodyParsingException"+ex.toString());
        }
   }
    
   private void createDefault()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside teacher attendance--->createdefault");    
        ILockService lock=inject.getLockService();
        String sequenceNo="N"+lock.getSequenceNo();
            
        RequestBody<Notification> reqBody = request.getReqBody();
        Notification notification =reqBody.get();
        
        notification.setNotificationID(sequenceNo);
        
          dbg("end of  completed teacher attendance--->createdefault");                
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
     public void create()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
        
        try{
        dbg("Inside NotificationService--->create"); 
        RequestBody<Notification> reqBody = request.getReqBody();
        Notification notification =reqBody.get();
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IAmazonEmailService emailService=inject.getAmazonEmailService();
        IAmazonSMSService smsService=inject.getAmazonSMSService();
       
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_instituteID=notification.getInstituteID();
        String instituteName=notification.getInstituteName();        
        String l_notificationID=notification.getNotificationID();
        String l_notificationType=notification.getNotificationType();
        String l_notificationFrequency=notification.getNotificationFrequency();
        String l_date=notification.getDate();
        String l_day=notification.getDay();
        String l_month=notification.getMonth();
        String l_message=notification.getMessage();
        String l_otherLangMessage=notification.getOtherLanguageMessage();
        String l_mediaCommunication=notification.getMediaCommunication();
        String l_assignee=notification.getAssignee();
        String l_instant=notification.getInstant();
        String l_email=notification.getEmail();
        String l_mobileNo=notification.getMobileNo();
        
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","INSTITUTE",67,l_instituteID,l_notificationID,l_notificationType,l_notificationFrequency,l_date,l_day,l_month,l_message,l_mediaCommunication,l_assignee,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks,l_instant,l_email,l_mobileNo,l_otherLangMessage);
       
        
        
        
        if(l_notificationType.equals("Emergency")){
            
         if(l_message!=null&&!l_message.equals("")){
            
                if(l_email!=null&&!l_email.isEmpty()){
             
                    Email emailObj=getEmailObject(l_message);

                    try{

                       emailService.sendEmail(emailObj, session);


                    }catch (BSProcessingException ex) {

                        session.getErrorhandler().log_app_error("BS_VAL_038",null);

                        throw new BSValidationException("BSValidationException");
                    }

                    updateEmailUsage();
                } 

                    
                if(l_mobileNo!=null&&!l_mobileNo.isEmpty()){    
                    
                    try{
                        String messageToSend=instituteName+","+l_message;

                        smsService.sendSMS(messageToSend, l_mobileNo, session,l_instituteID);


                    }catch (BSProcessingException ex) {

                        session.getErrorhandler().log_app_error("BS_VAL_039",null);

                        throw new BSValidationException("BSValidationException");
                    }

                    updateSMSUsage();
                    
                }
            
             }
            
            if(l_otherLangMessage!=null&&!l_otherLangMessage.isEmpty()){
            
                if(l_email!=null&&!l_email.isEmpty()){
                
                
                    Email otherLangemailObj=getEmailObject(l_otherLangMessage);

                    try{

                       emailService.sendEmail(otherLangemailObj, session);


                    }catch (BSProcessingException ex) {

                        session.getErrorhandler().log_app_error("BS_VAL_038",null);

                        throw new BSValidationException("BSValidationException");
                    }

                    updateEmailUsage();
                
                }
                
                
                if(l_mobileNo!=null&&!l_mobileNo.isEmpty()){ 
                
                    try{
                            String messageToSend=instituteName+","+l_otherLangMessage;

                            smsService.sendSMS(messageToSend, l_mobileNo, session,l_instituteID);


                        }catch (BSProcessingException ex) {

                            session.getErrorhandler().log_app_error("BS_VAL_039",null);

                            throw new BSValidationException("BSValidationException");
                        }

                        updateSMSUsage();
                }
            }
        }
        
        
        
        

        dbg("End of NotificationService--->create");
        
        }catch(BSValidationException | DBValidationException ex){
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
            dbg("Inside NotificationService--->update"); 
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<Notification> reqBody = request.getReqBody();
            Notification notification =reqBody.get();
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
            String l_instituteID=notification.getInstituteID(); 
            String l_notificationID=notification.getNotificationID();
            Map<String,String>  l_column_to_update=new HashMap();
            l_column_to_update.put("12", l_checkerID);
            l_column_to_update.put("14", l_checkerDateStamp);
            l_column_to_update.put("16", l_authStatus);
            l_column_to_update.put("19", l_checkerRemarks);
            
             
             String[] l_primaryKey={l_notificationID};
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","INSTITUTE","IVW_NOTIFICATION_MASTER", l_primaryKey, l_column_to_update, session);

             dbg("End of NotificationService--->update");          
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

     
     public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
        
       Map<String,String> l_column_to_update;
                
        try{ 
        dbg("Inside NotificationService--->fullUpdate");
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<Notification> reqBody = request.getReqBody();
        Notification notification =reqBody.get();
        String l_instituteID=notification.getInstituteID(); 
        String instituteName=notification.getInstituteName();  
        String l_notificationID=notification.getNotificationID();
        String l_notificationType=notification.getNotificationType();
        String l_notificationFrequency=notification.getNotificationFrequency();
        String l_date=notification.getDate();
        String l_day=notification.getDay();
        String l_month=notification.getMonth();
        String l_message=notification.getMessage();
        String l_otherLangMessage=notification.getOtherLanguageMessage();
        String l_mediaCommunication=notification.getMediaCommunication();
        String l_assignee=notification.getAssignee(); 
        String l_instant=notification.getInstant();
        String l_email=notification.getEmail();
        String l_mobileNo=notification.getMobileNo();
        IAmazonEmailService emailService=inject.getAmazonEmailService();
        IAmazonSMSService smsService=inject.getAmazonSMSService();
        l_column_to_update= new HashMap();
        l_column_to_update.put("2", l_notificationID);
        l_column_to_update.put("3", l_notificationType);
        l_column_to_update.put("4", l_notificationFrequency);
        l_column_to_update.put("5", l_date);
        l_column_to_update.put("6", l_day);
        l_column_to_update.put("7", l_month);
        l_column_to_update.put("8", l_message);
        l_column_to_update.put("9", l_mediaCommunication);
        l_column_to_update.put("10", l_assignee);
        l_column_to_update.put("20", l_instant);
        l_column_to_update.put("21", l_email);
        l_column_to_update.put("22", l_mobileNo);
        l_column_to_update.put("23", l_otherLangMessage);
        String[] l_PKey={l_notificationID};
            
            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","INSTITUTE","IVW_NOTIFICATION_MASTER",l_PKey,l_column_to_update,session);
        
       
       
//        Email emailObj=getEmailObject();
//         try{
//        
//           emailService.sendEmail(emailObj, session);
//        
//        }catch (BSProcessingException ex) {
//            
//            session.getErrorhandler().log_app_error("BS_VAL_038",null);
//            
//            throw new BSValidationException("BSValidationException");
//        }
//        
//        try{
//        String messageToSend=instituteName+","+l_message;
//             smsService.sendSMS(messageToSend, l_mobileNo, session,l_instituteID);
//        
//        
//        }catch (BSProcessingException ex) {
//            
//            session.getErrorhandler().log_app_error("BS_VAL_039",null);
//            
//            throw new BSValidationException("BSValidationException");
//        }         
//        
        if(l_notificationType.equals("Emergency")){
            
         if(l_message!=null&&!l_message.isEmpty()){
            
                if(l_email!=null&&!l_email.isEmpty()){
             
                    Email emailObj=getEmailObject(l_message);

                    try{

                       emailService.sendEmail(emailObj, session);


                    }catch (BSProcessingException ex) {

                        session.getErrorhandler().log_app_error("BS_VAL_038",null);

                        throw new BSValidationException("BSValidationException");
                    }

                    updateEmailUsage();
                } 

                    
                if(l_mobileNo!=null&&!l_mobileNo.isEmpty()){    
                    
                    try{
                        String messageToSend=instituteName+","+l_message;

                        smsService.sendSMS(messageToSend, l_mobileNo, session,l_instituteID);


                    }catch (BSProcessingException ex) {

                        session.getErrorhandler().log_app_error("BS_VAL_039",null);

                        throw new BSValidationException("BSValidationException");
                    }

                    updateSMSUsage();
                    
                }
            
             }
            
            if(l_otherLangMessage!=null&&!l_otherLangMessage.isEmpty()){
            
                if(l_email!=null&&!l_email.isEmpty()){
                
                
                    Email otherLangemailObj=getEmailObject(l_otherLangMessage);

                    try{

                       emailService.sendEmail(otherLangemailObj, session);


                    }catch (BSProcessingException ex) {

                        session.getErrorhandler().log_app_error("BS_VAL_038",null);

                        throw new BSValidationException("BSValidationException");
                    }

                    updateEmailUsage();
                
                }
                
                
                if(l_mobileNo!=null&&!l_mobileNo.isEmpty()){ 
                
                    try{
                            String messageToSend=instituteName+","+l_otherLangMessage;

                            smsService.sendSMS(messageToSend, l_mobileNo, session,l_instituteID);


                        }catch (BSProcessingException ex) {

                            session.getErrorhandler().log_app_error("BS_VAL_039",null);

                            throw new BSValidationException("BSValidationException");
                        }

                        updateSMSUsage();
                }
            }
        }
        
        dbg("end of NotificationService--->fullUpdate");      
        }catch(BSValidationException ex){
            throw ex;
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
     
     public void delete()throws DBValidationException,DBProcessingException,BSProcessingException{
        
        try{ 
        dbg("Inside NotificationService--->delete");  
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<Notification> reqBody = request.getReqBody();
        Notification notification =reqBody.get();
        String l_instituteID=notification.getInstituteID();   
        String l_notificationID=notification.getNotificationID();

        String[] l_primaryKey={l_notificationID};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","INSTITUTE","IVW_NOTIFICATION_MASTER", l_primaryKey, session);
         
               
 
         dbg("End of NotificationService--->delete");      
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
     public void  view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
        
                
        try{
            dbg("Inside NotificationService--->tableRead");
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<Notification> reqBody = request.getReqBody();
            Notification notification =reqBody.get();
            String l_instituteID=notification.getInstituteID();
            String l_notificationID=notification.getNotificationID();
            String[] l_pkey={l_notificationID};
            DBRecord notificationRecord=null;
            
            try{
            
            
                   notificationRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","INSTITUTE","IVW_NOTIFICATION_MASTER", l_pkey, session,dbSession);
        
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
            
            
            
            buildBOfromDB(notificationRecord);
           
            
            
         
          dbg("end of  NotificationService--->tableRead");   
          }catch(BSValidationException ex){
            throw ex;
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
}
     
      private void buildBOfromDB( DBRecord p_holidayMaintenance)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
            dbg("inside NotificationService--->buildBOfromDB");
            
            ArrayList<String>holidayList=p_holidayMaintenance.getRecord();
            RequestBody<Notification> reqBody = request.getReqBody();
            Notification notification =reqBody.get();
            String l_instituteID=notification.getInstituteID();
            BusinessService bs=inject.getBusinessService(session);
            if(holidayList!=null&&!holidayList.isEmpty()){
              
               String instituteName=bs.getInstituteName(l_instituteID, session, dbSession, inject);
               notification.setInstituteName(instituteName);
               notification.setNotificationType(holidayList.get(2).trim());
               notification.setNotificationFrequency(holidayList.get(3).trim());
               notification.setDate(holidayList.get(4).trim());
               notification.setDay(holidayList.get(5).trim());
               notification.setMonth(holidayList.get(6).trim());
               notification.setMessage(holidayList.get(7).trim());
               notification.setMediaCommunication(holidayList.get(8).trim());
               notification.setAssignee(holidayList.get(9).trim());
               notification.setInstant(holidayList.get(19).trim());
               notification.setEmail(holidayList.get(20).trim());
               notification.setMobileNo(holidayList.get(21).trim());
               notification.setOtherLanguageMessage(holidayList.get(22).trim());
               request.getReqAudit().setMakerID(holidayList.get(10).trim());
               request.getReqAudit().setCheckerID(holidayList.get(11).trim());
               request.getReqAudit().setMakerDateStamp(holidayList.get(12).trim());
               request.getReqAudit().setCheckerDateStamp(holidayList.get(13).trim());
               request.getReqAudit().setRecordStatus(holidayList.get(14).trim());
               request.getReqAudit().setAuthStatus(holidayList.get(15).trim());
               request.getReqAudit().setVersionNumber(holidayList.get(16).trim());
               request.getReqAudit().setMakerRemarks(holidayList.get(17).trim());
               request.getReqAudit().setCheckerRemarks(holidayList.get(18).trim());
            }
            
          
       
        dbg("End of NotificationService--->buildBOfromDB");
        
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
 } 
     
     
     
     
     public void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
         
         return;
         
     }
     
     public JsonObject buildJsonResFromBO()throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
         JsonObject body=null;
         try{
             dbg("inside buildJsonResFromBO");
            RequestBody<Notification> reqBody = request.getReqBody();
            Notification notification =reqBody.get();
            String operation=request.getReqHeader().getOperation();
            
            if(operation.equals("Create-Default")){
                
                body=Json.createObjectBuilder().add("notificationID", notification.getNotificationID())
                                               .build();
                
                
                
            }else{
            
             body=Json.createObjectBuilder().add("instituteID", notification.getInstituteID())
                                            .add("instituteName", notification.getInstituteName())
                                            .add("notificationID", notification.getNotificationID())
                                            .add("notificationType",notification.getNotificationType())
                                            .add("notificationFrequency",notification.getNotificationFrequency() )
                                            .add("date",notification.getDate() )
                                            .add("day",notification.getDay()) 
                                            .add("month",notification.getMonth() )
                                            .add("message",notification.getMessage())
                                            .add("otherLanguageMessage",notification.getOtherLanguageMessage())
                                            .add("mediaCommunication",notification.getMediaCommunication() )
                                            .add("assignee",notification.getAssignee() )
                                            .add("instant",notification.getInstant() )
                                            .add("email",notification.getEmail() )
                                            .add("mobileNo",notification.getMobileNo())
                                            .build(); 
            }
            dbg("end of buildJsonResFromBO");
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
         
         return body;
         
     }
     
     private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
       try{
       dbg("inside NotificationService--->businessValidation");    
       String l_operation=request.getReqHeader().getOperation();
                     
       if(!masterMandatoryValidation(errhandler)){
           status=false;
       }else{
           if(!masterDataValidation(errhandler)){
              status=false;
           }
       }
       if(!(l_operation.equals("View"))&&!l_operation.equals("Create-Default")&&!l_operation.equals("Delete")){
           
           if(!detailMandatoryValidation(errhandler)) {
               
               status=false;
           } else{
           if(!detailDataValidation(errhandler)){
               
               status=false;
           }
           
           }
       
       }

       dbg("NotificationService--->businessValidation--->O/P--->status"+status);
       dbg("End of NotificationService--->businessValidation");
       }catch(BSProcessingException ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
            
       }catch(BSValidationException ex){
           throw ex;
           
       }catch(DBValidationException ex){
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
        dbg("inside NotificationService--->masterMandatoryValidation");
        RequestBody<Notification> reqBody = request.getReqBody();
        Notification notification =reqBody.get();
         
         if(notification.getInstituteID()==null||notification.getInstituteID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","instituteID");
         }
         if(notification.getNotificationID()==null||notification.getNotificationID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","notificationID");
         }
         
         
         
         
         dbg("NotificationService--->masterMandatoryValidation--->O/P--->status"+status);
         dbg("End of NotificationService--->masterMandatoryValidation");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
            
        
            }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
     try{
        dbg("inside ClassExamScheduleService--->masterDataValidation");   
        BSValidation bsv=inject.getBsv(session);
        RequestBody<Notification> reqBody = request.getReqBody();
        Notification notification =reqBody.get();
        String l_instituteID= notification.getInstituteID();
        ErrorHandler errHandler=session.getErrorhandler();
//        String l_notificationType=notification.getNotificationType();
             
        if(!bsv.instituteIDValidation( l_instituteID,errHandler,inject, session, dbSession)){
            status=false;
            errhandler.log_app_error("BS_VAL_003","instituteID");
        }
        

        dbg("ClassExamScheduleServiceService--->masterDataValidation--->O/P--->status"+status);
        dbg("End of ClassExamScheduleServiceService--->masterDataValidation");  
       }catch(DBValidationException ex){
            throw ex;
       }catch(DBProcessingException ex){
            throw new DBProcessingException("DBProcessingException"+ex.toString());
       }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        
        return status;
              
    }
    
     private boolean detailMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
        boolean status=true;
         try{
            dbg("inside NotificationService--->detailMandatoryValidation");
            RequestBody<Notification> reqBody = request.getReqBody();
            Notification notification =reqBody.get();
            String instituteID=request.getReqHeader().getInstituteID();
            IPDataService pds=inject.getPdataservice();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            
            String[] pkey={instituteID};
            ArrayList<String>contractList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",pkey);
            
            String language=contractList.get(12).trim();
            
            if(notification.getNotificationType()==null||notification.getNotificationType().isEmpty()) {   
               status=false;
               errhandler.log_app_error("BS_VAL_002","notificationType");
            }
           
            if(!notification.getNotificationFrequency().equals("Select option")&&!notification.getNotificationFrequency().equals("")&&!notification.getNotificationFrequency().equals("I")) {   
               status=false;
               errhandler.log_app_error("BS_VAL_036","Frequency other than Instant");
       
            }
            if(!notification.getDate().equals("Select option")&&!notification.getDate().equals("")) {   
               status=false;
               errhandler.log_app_error("BS_VAL_036","Date");
            }
            if(!notification.getDay().equals("Select option")&&!notification.getDay().equals("")) {   
               status=false;
               errhandler.log_app_error("BS_VAL_036","Day");
            }
            if(!notification.getMonth().equals("Select option")&&!notification.getMonth().equals("")) {   
               status=false;
      
              errhandler.log_app_error("BS_VAL_036","Month");
            }
            
            if(language.equals("EN")){
            
                if(notification.getMessage()==null||notification.getMessage().isEmpty()) {   
                   status=false;
                   errhandler.log_app_error("BS_VAL_002","Message in English");
                }
            
            }else{
                
                if(notification.getOtherLanguageMessage()==null||notification.getOtherLanguageMessage().isEmpty()) {   
                   status=false;
                   errhandler.log_app_error("BS_VAL_002","Message in Other Language");
                }
                
            }
            
//             if(notification.getOtherLanguageMessage()==null||notification.getOtherLanguageMessage().isEmpty()) {   
//               status=false;
//               errhandler.log_app_error("BS_VAL_002","Other Language message");
//            }
            if(notification.getMediaCommunication()==null||notification.getMediaCommunication().isEmpty()) {   
               status=false;
               errhandler.log_app_error("BS_VAL_002","Mode");
            }
            if(notification.getAssignee()==null||notification.getAssignee().isEmpty()) {   
               status=false;
               errhandler.log_app_error("BS_VAL_002","assignee");
            }
            if(notification.getInstant()==null||notification.getInstant().isEmpty()) {   
               status=false;
               errhandler.log_app_error("BS_VAL_002","instant");
            }
        
//            if(notification.getMobileNo()==null||notification.getMobileNo().isEmpty()) {   
//               status=false;
//               errhandler.log_app_error("BS_VAL_002","Mobile no");
//            }
//            if(notification.getEmail()==null||notification.getEmail().isEmpty()) {   
//               status=false;
//               errhandler.log_app_error("BS_VAL_002","Email");
//            }
            
        dbg("NotificationService--->detailMandatoryValidation--->O/P--->status"+status);
        dbg("End of NotificationService--->detailMandatoryValidation");
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
  
   private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
     try{
        dbg("inside NotificationService--->detailDataValidation");   
        BSValidation bsv=inject.getBsv(session);
        BusinessService bs=inject.getBusinessService(session);
        RequestBody<Notification> reqBody = request.getReqBody();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IPDataService pds=inject.getPdataservice();
        Notification notification =reqBody.get();
        String l_instituteID= notification.getInstituteID();
        String l_notificationID=notification.getNotificationID();
        String l_notificationType=notification.getNotificationType();
        String l_instant=notification.getInstant();
        String l_email=notification.getEmail();
        String l_mobileNo=notification.getMobileNo();
        String l_assignee=notification.getAssignee();
        String l_mediaCommunication=notification.getMediaCommunication();
        String l_message=notification.getMessage();
        String instituteName=bs.getInstituteName(l_instituteID, session, dbSession, inject);
        String l_operation=request.getReqHeader().getOperation();
        String englishMessage=notification.getMessage();
        IDBTransactionService dbts=inject.getDBTransactionService();
        ILockService lock=inject.getLockService();
        ITransactionControlService tc=inject.getTransactionControlService();
        
        String[] pkey={l_instituteID};
             ArrayList<String>contractList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",pkey);
            
             String contractMode=contractList.get(11).trim();
            
             
        
        if(!bsv.notificationTypeValidation(l_notificationType,l_instituteID, session, dbSession,inject)){
            status=false;
            errhandler.log_app_error("BS_VAL_003","notificationType");
        }
        
        if(!bsv.groupIDValidation(l_assignee,l_instituteID, session, dbSession,inject)){
            status=false;
            errhandler.log_app_error("BS_VAL_003","Assignee");
        }
        
        if(!bsv.dateFormatValidation(l_instant, session, dbSession,inject)){
            status=false;
            errhandler.log_app_error("BS_VAL_003","Insant");
        }
        
        
        if(l_email!=null&&!l_email.isEmpty()){
        
            if(!l_notificationType.equals("Emergency")){
                
                status=false;
                errhandler.log_app_error("BS_VAL_061",null);
                
            }
            
            
            if(!bsv.emailValidation(l_email, session, dbSession,inject)){
                status=false;
                errhandler.log_app_error("BS_VAL_003","Email");
            }
        
        }
        if(l_mobileNo!=null&&!l_mobileNo.isEmpty()){
            
            if(!l_notificationType.equals("Emergency")){
                
                status=false;
                errhandler.log_app_error("BS_VAL_061",null);
                
            }
            
            
        if(!bsv.contactNoValidation(l_mobileNo, session, dbSession,inject)){
            status=false;
            errhandler.log_app_error("BS_VAL_003","Mobile no If there is no country code please enter country code before mobile no and + prefixed");
        }

     }
        if(!(l_mediaCommunication.equals("M")||l_mediaCommunication.equals("S")||l_mediaCommunication.equals("B"))){
            status=false;
            errhandler.log_app_error("BS_VAL_003","Mode");
        }
        
        
                 if(contractMode.equals("M")){
                      
                        if(!l_mediaCommunication.equals("M")){
                 
                             status=false;
                             errhandler.log_app_error("BS_VAL_002","Mode as Mail");
                        }
                      
                  }else if(contractMode.equals("S")){
                      
                        if(!l_mediaCommunication.equals("S")){
                 
                             status=false;
                             errhandler.log_app_error("BS_VAL_002","Mode as SMS");
                        }
                      
                  }
        
        
         String[] contractPkey={l_instituteID};
            String l_contractpkey=dbSession.getIibd_file_util().formingPrimaryKey(contractPkey);
            String fileName="APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive";
            String tableName="CONTRACT_MASTER";
     
    if(l_mobileNo!=null&&!l_mobileNo.isEmpty()){        
            
        
     if(dbts.getImplictRecordLock(fileName,tableName,l_contractpkey,this.session)==true){ 

               if( lock.isSameSessionRecordLock(fileName,tableName,l_contractpkey, this.session)){   
        
                                if(!bsv.smsUsageValidation(l_instituteID, session, dbSession,inject)){
                                    
                                    tc.rollBack(session, dbSession);
                                    status=false;
                                    errhandler.log_app_error("BS_VAL_037","sms");
                                }
                     tc.rollBack(session, dbSession);
                     
                }
                            
     }
     
    }
     
     
     if(l_email!=null&&!l_email.isEmpty()){
     
       if(dbts.getImplictRecordLock(fileName,tableName,l_contractpkey,this.session)==true){ 

               if( lock.isSameSessionRecordLock(fileName,tableName,l_contractpkey, this.session)){   
                   
                    if(!bsv.emailUsageValidation(l_instituteID, session, dbSession,inject)){
                        tc.rollBack(session, dbSession);
                        status=false;
                        errhandler.log_app_error("BS_VAL_037","email");
                    }
                 tc.rollBack(session, dbSession);
               }
        }
       
     }
       
       
        if(englishMessage!=null&&!englishMessage.isEmpty()){
        
            
                if(!(englishMessage.contains("A")||englishMessage.contains("a"))){

                   if(!(englishMessage.contains("E")||englishMessage.contains("e"))){
                       
                       if(!(englishMessage.contains("I")||englishMessage.contains("i"))){
                           
                           if(!(englishMessage.contains("O")||englishMessage.contains("o"))){
                               
                               if(!(englishMessage.contains("U")||englishMessage.contains("u"))){
                                   
                                   status=false;
                                   errhandler.log_app_error("BS_VAL_003","English Message");
                                   
                                   
                               }
                           }
                       }
                   }
                    

                }
        
        
        }
        
        if(l_mediaCommunication.equals("S")){
        
         
         String insShortName=bs.getInstituteShortName(l_instituteID, session, dbSession, inject);
         
         
        String messageToSend=insShortName+","+"Student: "+l_message;
         if(messageToSend.length()>280){
            status=false;
            errhandler.log_app_error("BS_VAL_003","Message length");
        }
        }
        
        
        if(l_operation.equals("Modify")||l_operation.equals("Delete")){
            boolean recordExistence=false;
            
            try{
            
                String[] l_pkey={l_instituteID,l_notificationID,l_instant};
                readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instant+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instant, "BATCH", "NOTIFICATION_EOD_STATUS", l_pkey,session, dbSession);
                recordExistence=true;
            }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                          recordExistence=false;
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_000"); 
                       }else{
                           throw ex;
                       }
            
            }
            
            
            if(recordExistence){
                
                status=false;
                errhandler.log_app_error("BS_VAL_041",null);
            }
        }
        
        
        
        dbg("ClassconfigServiceService--->detailDataValidation--->O/P--->status"+status);
        dbg("End of ClassExamScheduleServiceService--->detailDataValidation");  
       }catch(DBValidationException ex){
            throw ex;
       }catch(DBProcessingException ex){
            throw new DBProcessingException("DBProcessingException"+ex.toString());
       }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        
        return status;
              
    }
  
  public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     ExistingAudit exAudit=new ExistingAudit();
     try{
        dbg("inside BusinessService--->getExistingAudit") ;
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_operation=request.getReqHeader().getOperation();
        String l_service=request.getReqHeader().getService();
        RequestBody<Notification> notificationBody = request.getReqBody();
        Notification notification =notificationBody.get();
        String l_instituteID=notification.getInstituteID();
        String l_versioNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
        if(!(l_operation.equals("Create")||l_operation.equals("Create-Default")||l_operation.equals("View"))){
             
            if(l_operation.equals("AutoAuth")&&l_versioNumber.equals("1")){
                
                return null;
            }else{  
                
               dbg("inside business Service--->getExistingAudit--->Service--->Notification");  
               
               String l_notificationID=notification.getNotificationID();
               String[] l_pkey={l_notificationID};
            
               DBRecord instituteRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","INSTITUTE","IVW_NOTIFICATION_MASTER", l_pkey, session,dbSession);

               exAudit.setAuthStatus(instituteRecord.getRecord().get(15).trim());
               exAudit.setMakerID(instituteRecord.getRecord().get(10).trim());
               exAudit.setRecordStatus(instituteRecord.getRecord().get(14).trim());
               exAudit.setVersionNumber(Integer.parseInt(instituteRecord.getRecord().get(16).trim()));
            

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
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
  
  
   private Email getEmailObject(String message)throws BSProcessingException,BSValidationException{
         try{
             
           dbg("inside NotificationService--->getEmailObject");
           IPDataService pds=inject.getPdataservice();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           RequestBody<Notification> notificationBody = request.getReqBody();
           Notification notification =notificationBody.get();
           String instituteID=notification.getInstituteID();
           String instituteName=notification.getInstituteName();
           String appLink=i_db_properties.getProperty("APPLICATION_LINK");
           
//           String message=notification.getMessage();
           dbg("appLink"+appLink);
           dbg("message"+message);
           

                String[] pkey={instituteID};
                ArrayList<String>contractList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",pkey);
           
           String fromEmail=contractList.get(9).trim().replace("AATT;", "@");
           String toEmail=notification.getEmail().replace("AATT;", "@");
           String subject="Email Notification from "+instituteName;
           String textBody="This is Email Notification from "+instituteName;
           String htmlBody="<h1> Email Notification from "+instituteName+"</h1>"
                          + "<p>"+message
                              +"</p>" 
                              +"<p> <u>This is Auto generated email , please do not reply</u></p>"
                              ;
                
            Email email=new  Email();
            
            email.setFromEmail(fromEmail);
            email.setToEmail(toEmail);
            email.setHtmlBody(htmlBody);
            email.setSubject(subject);
            email.setTextBody(textBody);
                
            
        dbg("End of NotificationService--->getEmailObject");
        
           return email;
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        
    }
  
  private void updateEmailUsage()throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
         try{
             
           dbg("inside updateEmailUsage");
           IPDataService pds=inject.getPdataservice();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           RequestBody<Notification> notificationBody = request.getReqBody();
           Notification notification =notificationBody.get();
           String instituteID=notification.getInstituteID();
           IDBTransactionService dbts=inject.getDBTransactionService();

           String[] pkey={instituteID};
           ArrayList<String>contractList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",pkey);
           
           int emailUsed= Integer.parseInt(contractList.get(5).trim()) ;
                
           Map<String,String> l_column_to_update= new HashMap();
           l_column_to_update.put("6", Integer.toString(emailUsed+1));
           
           dbts.updateColumn("APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER", pkey, l_column_to_update, session);
           
           dbg("End of NotificationService--->updateEmailUsage");
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
  
  
   private void updateSMSUsage()throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
         try{
             
           dbg("inside updateEmailUsage");
           IPDataService pds=inject.getPdataservice();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           RequestBody<Notification> notificationBody = request.getReqBody();
           Notification notification =notificationBody.get();
           String instituteID=notification.getInstituteID();
           IDBTransactionService dbts=inject.getDBTransactionService();

           String[] pkey={instituteID};
           ArrayList<String>contractList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",pkey);
           
           int smsUsed= Integer.parseInt(contractList.get(4).trim()) ;
                
           Map<String,String> l_column_to_update= new HashMap();
           l_column_to_update.put("5", Integer.toString(smsUsed+1));
           
           dbts.updateColumn("APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER", pkey, l_column_to_update, session);
           
           dbg("End of NotificationService--->updateEmailUsage");
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
  
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
}
