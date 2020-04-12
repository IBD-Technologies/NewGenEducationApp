/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.otpService;

import com.ibd.businessViews.IAmazonSMSService;
import com.ibd.businessViews.IOTPService;
import com.ibd.cohesive.app.Oauth.AuthServer.ISecurityManagementService;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.exception.ExceptionHandler;
import com.ibd.cohesive.app.business.util.message.request.Parsing;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.errorhandling.Errors;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Remote(IOTPService.class)

@Stateless
public class OTPService implements IOTPService{
    
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
//    Request request;
    public OTPService(){
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
       boolean l_session_created_now=false;
       JsonObject jsonResponse=null;
       ITransactionControlService tc=null;
      
       try{
       session.createSessionObject();
       dbSession.createDBsession(session);
       l_session_created_now=session.isI_session_created_now();
       dbg("inside ClassSoftSkillService--->processing");
       dbg("ClassSoftSkillService--->Processing--->I/P--->requestJson"+requestJson.toString());
       tc=inject.getTransactionControlService();
       
       String operation=requestJson.getJsonObject("header").getString("operation");
       
       JsonObject l_body=requestJson.getJsonObject("body");
       String mobileNo=null;
       String otp=null;
        if(!operation.equals("ChangePwdFromMainPage")){
      
          mobileNo=l_body.getString("mobile");
          otp=l_body.getString("OTP");
        }
       
       String newPassword=l_body.getString("newPwd");
       
       
       
       switch(operation){
           
           
           case "ValidateMobile":
               
               
               this.verifyMobileNo(mobileNo);
               this.sendOTP(mobileNo);
           
               break;
           case "ValidateOTP":
               
               this.verifyOTP(mobileNo, otp);
               
               break;
           
           case "ChangePwd":
               
               this.verifyOTP(mobileNo, otp);
               this.changePassword(mobileNo,newPassword);
               
               break;
           case "ChangePwdFromMainPage":
               mobileNo=requestJson.getJsonObject("header").getString("userID");
               this.changePassword(mobileNo,newPassword);
               
               break;
       }
       
       
       
       
      jsonResponse=this.buildSuccessResponse(requestJson);
        tc.commit(session, dbSession);
       }catch(NamingException ex){
            dbg(ex);
            tc.rollBack(session, dbSession);
            jsonResponse=this.buildErrorResponse(requestJson, "BSProcessingException");
       }catch(BSValidationException ex){
            tc.rollBack(session, dbSession);
            jsonResponse=this.buildErrorResponse(requestJson, "BSValidationException");
       }catch(DBValidationException ex){
            tc.rollBack(session, dbSession);
            jsonResponse=this.buildErrorResponse(requestJson, "DBValidationException");
       }catch(DBProcessingException ex){
            dbg(ex);
            tc.rollBack(session, dbSession);
            jsonResponse=this.buildErrorResponse(requestJson, "DBProcessingException");
       }catch(BSProcessingException ex){
          dbg(ex);
          tc.rollBack(session, dbSession);
          jsonResponse=this.buildErrorResponse(requestJson, "BSProcessingException");
      
        }catch(Exception ex){
          dbg(ex);
          tc.rollBack(session, dbSession);
          jsonResponse=this.buildErrorResponse(requestJson, "BSProcessingException");
      }
        finally{
            
            if(l_session_created_now){
                dbg("Response"+jsonResponse.toString());
            session.clearSessionObject();
            dbSession.clearSessionObject();
           }
           }
       
        
       return jsonResponse; 
    }
    
    
   
  
    
    private JsonObject buildSuccessResponse(JsonObject request)throws BSProcessingException{
     JsonObjectBuilder jsonResponse=Json.createObjectBuilder();
      try{
      dbg("inside BusinessService--->buildSuccessResponse");    
//      JsonObject requestHeader=request.getJsonObject("header");
      JsonObject requestBody=request.getJsonObject("body");
      JsonObject requestAudit=request.getJsonObject("audit");
      
      JsonArray errorArray=Json.createArrayBuilder().add(Json.createObjectBuilder().add("errorCode", "SUCCESS")
                                                             .add("errorMessage", "SUCCESSFULLY PROCESSED")).build();

    
      JsonObject responseHeader=Json.createObjectBuilder().add("status", "success").build();
      
     
          jsonResponse=Json.createObjectBuilder().add("header",responseHeader)
                                                 .add("body",requestBody)
                                                 .add("audit",requestAudit)
                                                 .add("error",errorArray);
          
          
          
       
  
       
       
       
      dbg("BusinessService--->buildSuccessResponse--->O/P--->jsonResponse"+jsonResponse);
      dbg("End of BusinessService--->buildSuccessResponse");
      }catch(Exception ex){
          dbg(ex);
          throw new BSProcessingException("Exception" + ex.toString());
      }
    return jsonResponse.build();
  }
    
    
    
    
    
    
    
    private JsonObject buildErrorResponse(JsonObject request,String p_exceptionName)throws BSProcessingException{
     JsonObjectBuilder jsonResponse=Json.createObjectBuilder();
      try{
      dbg("inside BusinessService--->buildSuccessResponse");    
      JsonObject requestBody=request.getJsonObject("body");
      JsonObject requestAudit=request.getJsonObject("audit");
      
      JsonArray errorArray=this.getErrorJson(p_exceptionName);

      JsonObject responseHeader=Json.createObjectBuilder().add("status", "error").build();
     
          jsonResponse=Json.createObjectBuilder().add("header",responseHeader)
                                                        .add("body",requestBody)
                                                         .add("audit",requestAudit)
                                                        .add("error",errorArray);
          

      dbg("BusinessService--->buildSuccessResponse--->O/P--->jsonResponse"+jsonResponse);
      dbg("End of BusinessService--->buildSuccessResponse");
      }catch(Exception ex){
          dbg(ex);
          throw new BSProcessingException("Exception" + ex.toString());
      }
    return jsonResponse.build();
  }
    
    
    
    public JsonArray getErrorJson(String p_exceptionName)throws DBValidationException,DBProcessingException,BSProcessingException{
      
      JsonArrayBuilder errorBuilder=Json.createArrayBuilder();
      try{
          dbg("inside businessService--->getErrorJson ");
          dbg("businessService--->getErrorJson--->I/P--->p_exceptionName "+p_exceptionName);
          IPDataService pds=inject.getPdataservice();
          BusinessService bs=inject.getBusinessService(session);
          IBDProperties i_db_properties=session.getCohesiveproperties();
           String l_errorCode=new String();
            String l_errorMessage=new String();
           switch(p_exceptionName){
      
          case "BSValidationException":
              dbg("inside BSValidationException ");
           ArrayList<Errors>errorlist=session.getErrorhandler().getError_list();
           for(int i=0;i<errorlist.size();i++){
               
           Errors error=new Errors();
           error=errorlist.get(i);
           l_errorCode=error.getError_code();
           String l_errorParam=error.getError_param();
           
           if(l_errorParam==null){
               String l_pkey=l_errorCode;
               ArrayList<String>l_errorRecord=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive","APP","ERROR_MASTER",l_pkey);
              l_errorMessage=l_errorRecord.get(1).trim();
               errorBuilder.add(Json.createObjectBuilder().add("errorCode", l_errorCode)
                                                  .add("errorMessage", l_errorMessage));
           }else{
           if(l_errorParam.contains(",")){
           String[] l_errorParamArr=l_errorParam.split(",");
           dbg("error code"+l_errorCode);
            l_errorMessage=bs.getErrorMessage(l_errorCode,l_errorParamArr,inject,session,dbSession);
           errorBuilder.add(Json.createObjectBuilder().add("errorCode", l_errorCode)
                                                  .add("errorMessage", l_errorMessage));
           }else{
              String l_pkey=l_errorCode;
              ArrayList<String>l_errorRecord=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive","APP","ERROR_MASTER",l_pkey);
              l_errorMessage=l_errorRecord.get(1).trim();
              if(l_errorMessage.contains("$1")){
                  l_errorMessage=l_errorMessage.replace("$1", l_errorParam);
              }
               errorBuilder.add(Json.createObjectBuilder().add("errorCode", l_errorCode)
                                                  .add("errorMessage", l_errorMessage));
           }
           }

          
           }
           dbg("error message"+l_errorMessage);
           break;
           
          case "DBValidationException":
              String l_sessionErrorCode=session.getErrorhandler().getSession_error_code().toString();
              dbg("l_sessionErrorCode"+l_sessionErrorCode);
              String[] l_errorArray=l_sessionErrorCode.split("~");
              
              for(int i=0;i<l_errorArray.length;i++){
                  String l_pkey;
                   l_errorCode=l_errorArray[i];
                   if(l_errorCode.contains(",")){
                      int index=l_errorCode.indexOf(","); 
                      String l_errcode=l_errorCode.substring(0, index);
                      dbg("errorcode"+l_errcode);
                      String l_errorParam=l_errorCode.substring(index+1);
                      dbg("errorParam without split"+l_errorParam);
                      String[] l_errorParamArr=l_errorParam.split(",");
                      dbg("number of error parameters"+l_errorParamArr.length);
                      l_errorMessage=bs.getErrorMessage(l_errcode,l_errorParamArr,inject,session,dbSession);
                      errorBuilder.add(Json.createObjectBuilder().add("errorCode", l_errcode)
                                                       .add("errorMessage", l_errorMessage));
                   }else{
                      l_pkey=l_errorCode;
                      ArrayList<String>l_errorRecord=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive","APP","ERROR_MASTER",l_pkey);
                      l_errorMessage=l_errorRecord.get(1).trim();
                      errorBuilder.add(Json.createObjectBuilder().add("errorCode", l_errorCode)
                                                       .add("errorMessage", l_errorMessage));
                   }
//                                     dbg("l_errorCode"+l_errorCode);
                   
                  
              }
               
               break;
          case "DBProcessingException":
              
              l_errorCode="DB_VAL_019";
              l_errorMessage="There is Fatal Error! Please contact System Administrator";
              errorBuilder.add(Json.createObjectBuilder().add("errorCode", l_errorCode)
                                                       .add("errorMessage", l_errorMessage));
              break;
          case "BSProcessingException":
              
              l_errorCode="BS_VAL_029";
              l_errorMessage="There is Fatal Error! Please contact System Administrator";
              errorBuilder.add(Json.createObjectBuilder().add("errorCode", l_errorCode)
                                                       .add("errorMessage", l_errorMessage));
              break;    
          case "SUCCESS BUILD":    
              l_errorCode="BS_VAL_030";
              l_errorMessage="There is Fatal Error! Please contact System Administrator";
              errorBuilder.add(Json.createObjectBuilder().add("errorCode", l_errorCode)
                                                       .add("errorMessage", l_errorMessage));
              break;
              
      }
      dbg("End of businessService--->buildErrorResponse ");  
      }catch(DBValidationException ex){
        throw ex;
      }catch(DBProcessingException ex){
        throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
      }
       return errorBuilder.build();
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
    
    
    private void sendOTP(String userID)throws BSProcessingException,DBValidationException,BSValidationException,DBProcessingException{
//        boolean l_session_created_now =false;
        try{
            
//            session.createSessionObject();
//            dbSession.createDBsession(session);
//            l_session_created_now=session.isI_session_created_now();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IDBTransactionService dbts=inject.getDBTransactionService();
            BusinessService bs=inject.getBusinessService(session);
            IPDataService pds=inject.getPdataservice();
                
                int i = new Random().nextInt(900000) + 100000;
                String otp=Integer.toString(i);
                String currentTime=bs.getCurrentDateTime();
                
                try{
                
                   
                   dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTP","USER", 347,userID, otp,currentTime,"","N");
                
                }catch(DBValidationException ex){
                    
                    if(ex.toString().contains("DB_VAL_009")){
                        
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_009");
                        i = new Random().nextInt(900000) + 100000;
                        otp=Integer.toString(i);
                        dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTP","USER", 347,userID, otp,currentTime,"","N");
                    }
                    
                }
                    String[] l_pkey={userID};
            
            
            
                ArrayList<String>l_profileList=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_PROFILE", l_pkey);
                    
                
                String homeInstitute=l_profileList.get(15).trim();
                
                    this.sendOtpSMS(userID, otp,homeInstitute);
                    
               
                
            
            
            
        }catch(DBValidationException ex){
            throw ex;
        }catch(BSValidationException ex){
            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }finally
    {
//         if(l_session_created_now){
//          
//       session.clearSessionObject(); 
//         }
         }  
    }
    
    
    
   
    
    
    private void sendOtpSMS(String mobileNo,String otp,String instituteID)throws BSProcessingException,DBValidationException,BSValidationException,DBProcessingException{
        
        try{
            
            IAmazonSMSService smsService=inject.getAmazonSMSService();
            BusinessService bs=inject.getBusinessService(session);
            
            String message="Your OTP is "+otp+". DO not disclose to anyone.";
            smsService.sendSMS(message, mobileNo, session,instituteID);
            dbg("OTP send");
            
            
            
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(BSValidationException ex){
//            throw ex;    
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
    
    
//    private void sendOtpEmail(String instituteID,String otpNumber,String emailID)throws BSProcessingException,DBValidationException,BSValidationException,DBProcessingException{
//        
//        try{
//            
//            IAmazonEmailService emailService=inject.getAmazonEmailService();
//            BusinessService bs=inject.getBusinessService(session);
//            Email emailObj=getEmailObject(instituteID,otpNumber,emailID);
//            emailService.sendEmail(emailObj, session);
//            
//            
//            dbg("OTP send");
//            
//            
//            bs.updateEmailUsage(instituteID, session, dbSession, inject);
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(BSValidationException ex){
//            throw ex;    
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//        }
//    }
//    
//    
//    private Email getEmailObject(String instituteID,String otpNumber,String toEmail)throws BSProcessingException,DBValidationException,BSValidationException,DBProcessingException{
//         try{
//             
//           dbg("inside NotificationService--->getEmailObject");
//           IPDataService pds=inject.getPdataservice();
//           IBDProperties i_db_properties=session.getCohesiveproperties();
//           BusinessService bs=inject.getBusinessService(session);
//           String instituteName=bs.getInstituteName(instituteID, session, dbSession, inject);
//           dbg("otpNumber"+otpNumber);
//
//                String[] pkey={instituteID};
//                ArrayList<String>contractList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",pkey);
//           
//           String fromEmail=contractList.get(9).trim().replace("AATT;", "@");
//           String subject="OTP from "+instituteName;
//           String textBody="OTP from "+instituteName;
//           String htmlBody="<h1> OTP from "+instituteName+"</h1>"
//                          + "<p>"+"Your OTP is "+otpNumber+". DO not disclose to anyone."
//                              ;
//                
//            Email email=new  Email();
//            
//            email.setFromEmail(fromEmail);
//            email.setToEmail(toEmail);
//            email.setHtmlBody(htmlBody);
//            email.setSubject(subject);
//            email.setTextBody(textBody);
//                
//            
//        dbg("End of NotificationService--->getEmailObject");
//        
//           return email;
//          }catch(DBValidationException ex){
//            throw ex;
//        }catch(BSValidationException ex){
//            throw ex;    
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//        }
//        
//        
//    }
    
    
//    private void updateStudentLevelOTPStatus(String instituteID,String studentID,String endPoint,String otp)throws BSProcessingException,DBValidationException,BSValidationException,DBProcessingException{
//        
//        try{
//            
//            BusinessService bs=inject.getBusinessService(session);
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//            IBDProperties i_db_properties=session.getCohesiveproperties();
//            IDBTransactionService dbts=inject.getDBTransactionService();
//            String[] l_pkey={studentID,endPoint};
//            boolean recordExistence=false;
//            
//            try{
//                
//                
//              readBuffer.readRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_OTP_STATUS", l_pkey, session, dbSession);
//                
//                
//              recordExistence=true;
//            }catch(DBValidationException ex){
//                    dbg("exception in view operation"+ex);
//                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
//                        recordExistence=false;
//                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
//                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
//                    }else{
//                        
//                        throw ex;
//                    }
//                }
//            
//            
//            if(recordExistence){
//                
//                Map<String,String>column_to_update=new HashMap();
//                column_to_update.put("3", otp);
//                column_to_update.put("4", "N");
//                
//                dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Otp"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Otp","OTP", "STUDENT_OTP_STATUS", l_pkey, column_to_update, session);
//            }else{
//                
//                dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Otp"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Otp","OTP", 323, studentID,endPoint,otp,"N");
//                
//            }
//            
//            
//
//        }catch(DBValidationException ex){
//            throw ex;
////        }catch(BSValidationException ex){
////            throw ex;    
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//        }
//    }
//    
//     private void updateInstituteLevelOTPStatus(String instituteID,String studentID,String endPoint,String otp)throws BSProcessingException,DBValidationException,BSValidationException,DBProcessingException{
//        
//        try{
//            
//            BusinessService bs=inject.getBusinessService(session);
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//            IBDProperties i_db_properties=session.getCohesiveproperties();
//            IDBTransactionService dbts=inject.getDBTransactionService();
//            String currentTime=bs.getCurrentDateTime();
//            String[] l_pkey={instituteID,studentID,endPoint,currentTime};
//            boolean recordExistence=false;
//            String currentDate=bs.getCurrentDate();
//            String dateFormat=i_db_properties.getProperty("DATE_TIME_FORMAT");
//            SimpleDateFormat formatter=new SimpleDateFormat(dateFormat);
//            
//            try{
//                
//                
//              readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTP"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"OTP", "INSTITUTE_OTP_STATUS", l_pkey, session, dbSession);
//                
//                
//              recordExistence=true;
//            }catch(DBValidationException ex){
//                    dbg("exception in view operation"+ex);
//                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
//                        recordExistence=false;
//                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
//                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
//                    }else{
//                        
//                        throw ex;
//                    }
//                }
//            
//               if(recordExistence){
//            
//                    Calendar calender = Calendar.getInstance();
//                    calender.setTimeInMillis(formatter.parse(currentTime).getTime());
//                    calender.add(Calendar.SECOND,1);
//                    Date changeDate=calender.getTime();
//                    String l_changedDate=formatter.format(changeDate);
//
//
//                    dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTP"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"OTP", 324,instituteID, studentID,endPoint,otp,l_changedDate);
//                
//            
//               }else{
//                   
//                   dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTP"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"OTP", 324,instituteID, studentID,endPoint,otp,currentTime);
//               }
//
//        }catch(DBValidationException ex){
//            throw ex;
////        }catch(BSValidationException ex){
////            throw ex;    
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//        }
//    }
    
     
     
     private void verifyOTP(String userID,String otp)throws BSProcessingException,DBValidationException,BSValidationException,DBProcessingException{
//        boolean l_session_created_now =false;
        try{
            
//            session.createSessionObject();
//            dbSession.createDBsession(session);
//            l_session_created_now=session.isI_session_created_now();
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IDBTransactionService dbts=inject.getDBTransactionService();
            BusinessService bs=inject.getBusinessService(session);
            String[] l_pkey={userID,otp};
            
            
            try{
            
                readBuffer.readRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTP","USER", "UVW_USER_OTP_STATUS", l_pkey, session, dbSession);
                
                
            }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_003", "OTP");
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
                }
            
            

                
                Map<String,String>columnToUpDate=new HashMap();
                String currentTime=bs.getCurrentDateTime();
                columnToUpDate.put("4",currentTime);
                columnToUpDate.put("5","Y");
                
                dbts.updateColumn("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTP","USER", "UVW_USER_OTP_STATUS", l_pkey, columnToUpDate, session);
                
            
            
            
        }catch(DBValidationException ex){
            throw ex;
        }catch(BSValidationException ex){
            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }finally
    {
//         if(l_session_created_now){
//          
//       session.clearSessionObject(); 
//         }
         }  
    }
     
//     public boolean verifyOTP(String instituteID,String studentID,String endPoint,String otp,CohesiveSession session)throws BSProcessingException,DBValidationException,BSValidationException,DBProcessingException{
//    CohesiveSession tempSession = this.session;
//    try{
//        this.session=session;
//        return verifyOTP(instituteID,studentID,endPoint,otp);
//        
//       }catch(DBValidationException ex){
//            throw ex;
//        }catch(BSValidationException ex){
//            throw ex;    
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//        } 
//      finally{
//           this.session=tempSession;
//        }
//    }
     
     private void verifyMobileNo(String mobileNo)throws BSProcessingException,DBValidationException,BSValidationException,DBProcessingException{
//        boolean l_session_created_now =false;
        try{
            
//            session.createSessionObject();
//            dbSession.createDBsession(session);
//            l_session_created_now=session.isI_session_created_now();
            IPDataService pds=inject.getPdataservice();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            BSValidation bsv=inject.getBsv(session);
            
            if(mobileNo==null||mobileNo.isEmpty()){
                
                session.getErrorhandler().log_app_error("BS_VAL_003", "Mobile no");
                throw new BSValidationException("BSValidationException");
            }
            
            
            if(!bsv.contactNoValidation(mobileNo, session, dbSession, inject)){
                
                session.getErrorhandler().log_app_error("BS_VAL_003", "Mobile no");
                throw new BSValidationException("BSValidationException");
                
            }
            
            
            
            String[] l_pkey={mobileNo};
            
            
            try{
            
                pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_PROFILE", l_pkey);
                
            }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_003", "Mobile no");
                        throw new BSValidationException("BSValidationException");
                    }else{
                        
                        throw ex;
                    }
                }
            
            
            
            
        }catch(DBValidationException ex){
            throw ex;
        }catch(BSValidationException ex){
            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }finally
    {
//         if(l_session_created_now){
//          
//       session.clearSessionObject(); 
//         }
         }  
    }
     
//     public boolean verifyMobileNo(String mobileNo,CohesiveSession session)throws BSProcessingException,DBValidationException,BSValidationException,DBProcessingException{
//    CohesiveSession tempSession = this.session;
//    try{
//        this.session=session;
//        return verifyMobileNo(mobileNo);
//        
//       }catch(DBValidationException ex){
//            throw ex;
//        }catch(BSValidationException ex){
//            throw ex;    
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//        } 
//      finally{
//           this.session=tempSession;
//        }
//    }
//     
     
      private void changePassword(String userID,String newPassword)throws BSProcessingException,DBValidationException,BSValidationException,DBProcessingException{
//        boolean l_session_created_now =false;
        try{
            
//            session.createSessionObject();
//            dbSession.createDBsession(session);
//            l_session_created_now=session.isI_session_created_now();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IDBTransactionService dbts=inject.getDBTransactionService();
            ISecurityManagementService security=inject.getSecurityManagementService();
            BusinessService bs=inject.getBusinessService(session);
            String[] l_primaryKey={userID};
            
            if(newPassword.length()<8||newPassword.length()>10){
                     
                     session.getErrorhandler().log_app_error("BS_VAL_033", null);
                     throw new BSValidationException("BSValidationException");
                 }
            
            
            
                String l_expiryDate=bs.getExpiryDate();
                String l_salt=security.generateSalt(512, session).get();
                String l_hashPassword=security.hashPassword(newPassword, l_salt, session).get();
                Map<String,String>l_column_to_update= new HashMap();  
                l_column_to_update.put("2", l_hashPassword);
                l_column_to_update.put("3", l_expiryDate);
                l_column_to_update.put("5", l_salt);

                dbts.updateColumn("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_CREDENTIALS", l_primaryKey, l_column_to_update, session);
                
                
                
            
            
            
        }catch(DBValidationException ex){
            throw ex;
        }catch(BSValidationException ex){
            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }finally
    {
//         if(l_session_created_now){
//          
//       session.clearSessionObject(); 
//         }
         }  
    }
     
   
     private void requestlogging(JsonObject p_request)throws BSProcessingException ,DBValidationException,DBProcessingException{
 
     FileChannel fc = null;
     try{
     dbg("inside BusinessService--->responselogging");  
     JsonObject header=p_request.getJsonObject("header");
     String l_service=header.getString("service");
     BusinessService bs=inject.getBusinessService(session);
     
     if(l_service.equals("SelectBoxMasterService")||l_service.contains("Search")){
         
         return;
     }
     
     IBDProperties db_properties=session.getCohesiveproperties();
     IDBTransactionService dbts=inject.getDBTransactionService();
     IPDataService pds=inject.getPdataservice();
     ITransactionControlService tc=inject.getTransactionControlService();
     String l_msgID=header.getString("msgID");
//     String l_correalationID=header.getString("correlationID");
     String l_operation=header.getString("operation");
     JsonArray l_businessEntityArray=header.getJsonArray("businessEntity");
     StringBuffer l_businessEntity=new StringBuffer();
     for(int i=0;i<l_businessEntityArray.size();i++){
         JsonObject l_entityObject=l_businessEntityArray.getJsonObject(i);
         String l_entityValue=l_entityObject.getString("entityValue");
         l_businessEntity=l_businessEntity.append(l_entityValue).append("^");
     }

     String l_userID=header.getString("userID");
     String l_status=header.getString("status");
     String dateformat=session.getCohesiveproperties().getProperty("DATE_TIME_FORMAT");
     SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
     Date date = new Date();
     String l_dateStamp=formatter.format(date);
     String l_source=header.getString("source");
     String l_currentDate=bs.getCurrentDate();
     IBDProperties i_db_properties=session.getCohesiveproperties();
     
              
     if(p_request.toString().length()>5000){
          
               String fileName=i_db_properties.getProperty("DATABASE_HOME_PATH") + "USER"+db_properties.getProperty("FOLDER_DELIMITER")+l_userID+db_properties.getProperty("FOLDER_DELIMITER")+l_currentDate+"_"+l_msgID +".json";
               
               dbts.createRecord(session,"USER"+db_properties.getProperty("FOLDER_DELIMITER")+l_userID+db_properties.getProperty("FOLDER_DELIMITER")+l_currentDate+"_LOG","USER",27, l_msgID,l_service,l_operation,l_businessEntity.toString(),fileName,l_dateStamp,l_source);

               Path file = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH") + "USER"+db_properties.getProperty("FOLDER_DELIMITER")+l_userID+db_properties.getProperty("FOLDER_DELIMITER")+l_currentDate+"_"+l_msgID +".json");
            
                
                fc = FileChannel.open(file, CREATE, WRITE, APPEND);
                fc.write(ByteBuffer.wrap(p_request.toString().getBytes(Charset.forName("UTF-8")))); 
               
      }else{
          
           dbts.createRecord(session,"USER"+db_properties.getProperty("FOLDER_DELIMITER")+l_userID+db_properties.getProperty("FOLDER_DELIMITER")+l_currentDate+"_LOG","USER",27, l_msgID,l_service,l_operation,l_businessEntity.toString(),p_request.toString(),l_dateStamp,l_source);
      }
     
    
//     dbSession.setFileNames_To_Be_Commited("USER"+db_properties.getProperty("FOLDER_DELIMITER")+l_userID+db_properties.getProperty("FOLDER_DELIMITER")+l_userID+"_LOG");
     tc.commit(session,dbSession);
    dbg("end of BusinessService--->responselogging");
     }catch(DBValidationException ex){
            //throw ex;
          dbg("Response Logging failed in DB validation exception");
          dbg("error:"+ex.toString());
          dbg(ex);
        }catch(DBProcessingException ex){
            
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }finally{ 
         try {
                if(fc!=null){
                fc.close();
                }
            } catch (IOException ex) {
                throw new BSProcessingException(ex.toString());
            }
     }
 }     
     
     
     
     
     
     
     
     
     
     public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
    
}
