/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.classattendance;

import com.ibd.businessViews.IAmazonEmailService;
import com.ibd.businessViews.IAmazonSMSService;
import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.EndPoint;
import com.ibd.cohesive.app.business.util.NotificationUtil;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
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
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Asynchronous;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Local(IAttendanceNotificationService.class)
@Stateless
public class AttendanceNotificationService implements IAttendanceNotificationService{
AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;

 public AttendanceNotificationService(){
        try {
            inject=new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession= new DBSession(session);
        } catch (NamingException ex) {
            dbg(ex);
            throw new EJBException(ex);
        }
        
    }
   

    @Asynchronous
     public void notificationProcessing (String date,String instituteID,ArrayList<String>studentList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
          boolean l_session_created_now=false;
          try{
              
              session.createSessionObject();
       dbSession.createDBsession(session);
       l_session_created_now=session.isI_session_created_now();
              dbg("inside notificationProcessing");
              NotificationUtil notificationUtil=inject.getNotificationUtil(session);
              String[] contractPkey={instituteID};
              String l_pkey=dbSession.getIibd_file_util().formingPrimaryKey(contractPkey);
              IBDProperties i_db_properties=session.getCohesiveproperties();
              IDBTransactionService dbts=inject.getDBTransactionService();
              ILockService lock=inject.getLockService();
              ITransactionControlService tc=inject.getTransactionControlService();
              BusinessService bs=inject.getBusinessService(session);
              BSValidation bsv=inject.getBsv(session);
              IAmazonSMSService smsService=inject.getAmazonSMSService();
              IAmazonEmailService emailService=inject.getAmazonEmailService();
              String fileName="APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive";
              String tableName="CONTRACT_MASTER";
              String message=this.getAttendanceMessageBody(instituteID);
              BatchUtil batchUtil=inject.getBatchUtil(session);
              
              for(int i=0;i<studentList.size();i++){
                  
                  String studentID=studentList.get(i);
                  
                  dbg("sms processing started for "+ studentID);
                  
                  EndPoint endPoint=notificationUtil.getEndPoints(instituteID, studentID, session, dbSession, inject);
                  
                  
                  
                  
                      
                  if(!this.checkStudentSentStatus(studentID, instituteID, date, endPoint.getMobileNo()))  {  
                      
                      this.updateWIP(studentID, instituteID, date, endPoint.getMobileNo());
                      
                      
                 try{     
                      
                  
                  if(dbts.getImplictRecordLock(fileName,tableName,l_pkey,this.session)==true){ 

                              if( lock.isSameSessionRecordLock(fileName,tableName,l_pkey, this.session)){

                                     if(bsv.smsUsageValidation(instituteID, session, dbSession, inject)) {  

                                        dbg("sms usage validation completed for "+studentID); 
                                        
                                        
                                         String mshheader=notificationUtil.getMessageHeader(instituteID, studentID, session, dbSession, inject);
                                        
                                         
                                         String messageWithHeader=mshheader.concat(message);
                                         
                                        smsService.sendSMS(messageWithHeader, endPoint.getMobileNo(), session,instituteID);
                                        dbg("sms sent for"+studentID);
                                        bs.updateSMSUsage(instituteID, session, dbSession, inject,messageWithHeader);
                                        this.studentLevelStatusUpdate(studentID, date, endPoint.getMobileNo(), "S", "",instituteID);
                                        this.studentLevelMessageUpdate(studentID, date, messageWithHeader,instituteID);
                                         tc.commit(session, dbSession);
                                     }else {
                                         tc.rollBack(session, dbSession);
                                         
                                         throw new Exception("Limit Exhausted");
                                     }
                                      }
                                  
                  }
                  }catch(Exception ex){
                      
                      dbg(ex);
                      tc.rollBack(session, dbSession);
                      StringWriter sw = new StringWriter();
                      ex.printStackTrace(new PrintWriter(sw));
                      String exceptionAsString = sw.toString();
                      String l_replacedException=batchUtil.getReplacedException(exceptionAsString).substring(0,49);
                      
                      this.studentLevelStatusUpdate(studentID, date, endPoint.getMobileNo(), "F", l_replacedException,instituteID);
                      tc.commit(session, dbSession);
                  }
                  }
                  
                  dbg("sms processing completed For"+studentID);
                  
                  
                  dbg("email processing started For"+studentID);
                  
                  
                      
                       if(!this.checkStudentSentStatus(studentID, instituteID, date, endPoint.getEmailID()))  {  
                      
                      this.updateWIP(studentID, instituteID, date, endPoint.getEmailID());
                      
                      
                      try{
                  
                           if(dbts.getImplictRecordLock(fileName,tableName,l_pkey,this.session)==true){ 

                            if( lock.isSameSessionRecordLock(fileName,tableName,l_pkey, this.session)){

                                 if(bsv.emailUsageValidation(instituteID, session, dbSession, inject)) {    

                                     dbg("email usage validation completed for "+studentID);
                                          Email emailObj=this.getEmailObject(instituteID, message, endPoint.getEmailID());
                                          emailService.sendEmail(emailObj, session);
                                          dbg("emailsent for"+studentID);
                                          bs.updateEmailUsage(instituteID, session, dbSession, inject);
                                          this.studentLevelStatusUpdate(studentID, date, endPoint.getMobileNo(), "S", "",instituteID);
                                          this.studentLevelMessageUpdate(studentID, date, message,instituteID);
                                          tc.commit(session, dbSession);
                                 }else{
                                     tc.rollBack(session, dbSession);

                                     throw new Exception("Limit Exhausted");
                                 }

                         }

                        }
                       
                  
                  }catch(Exception ex){
                      
                      dbg(ex);
                      tc.rollBack(session, dbSession);
                      StringWriter sw = new StringWriter();
                      ex.printStackTrace(new PrintWriter(sw));
                      String exceptionAsString = sw.toString();
                      String l_replacedException=batchUtil.getReplacedException(exceptionAsString).substring(0,49);
                      this.studentLevelStatusUpdate(studentID, date, endPoint.getMobileNo(), "F", l_replacedException,instituteID);
                      tc.commit(session, dbSession);
                  }
                  
                  
                  dbg("email processing completed For"+studentID);
              }
              }
              
              
        }catch(DBValidationException ex){
            dbg(ex);
        }catch(DBProcessingException ex){
            dbg(ex);
        }catch(BSProcessingException ex){
            dbg(ex);    
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }finally{
              
              if(l_session_created_now){
                  
                  session.clearSessionObject();
                  dbSession.clearSessionObject();
                  
              }
              
          }
      }
     
     
   private boolean checkStudentSentStatus(String studentID,String instituteID,String date,String endPoint)throws  BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
       
       
       
       try{
           
           dbg("inside checkStudentSentStatus");
           
           String[] dateArr=date.split("-");
        String notificationID="A"+dateArr[0]+dateArr[1]+dateArr[2];
        String[] statusPKey={studentID,notificationID,date,endPoint};
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBTransactionService dbts=inject.getDBTransactionService();
        boolean sentStatus=false;
        
        try{
            
            
            
           
            
            DBRecord studentNotificationRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT", "STUDENT_NOTIFICATION_STATUS", statusPKey, session, dbSession);
            
            String status=studentNotificationRec.getRecord().get(4).trim();
            
            dbg("inside checkStudentSentStatus-->status"+status);
            
            if(status.equals("S")||status.equals("W")){
                sentStatus=true;
            }else if(status.equals("F")){
                sentStatus=false;
            }
            
            
        }catch(DBValidationException ex){
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        sentStatus=false;
                    }else{
                        
                        throw ex;
                    }
            }
        
           
           
           
           dbg("sentStatus"+sentStatus);
           
           return sentStatus;
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
     
     private void updateWIP(String studentID,String instituteID,String date,String endPoint)throws  BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
       
       ITransactionControlService tc=null;
       
       try{
           
           
        String[] dateArr=date.split("-");
        String notificationID="A"+dateArr[0]+dateArr[1]+dateArr[2];
        String[] statusPKey={studentID,notificationID,date,endPoint};
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBTransactionService dbts=inject.getDBTransactionService();
        boolean recordExistence=false;
        tc=inject.getTransactionControlService();
        DBRecord studentNotificationRec=null;
        
        try{
            
            
            
           
            
             studentNotificationRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT", "STUDENT_NOTIFICATION_STATUS", statusPKey, session, dbSession);
            
           recordExistence=true;
            
        }catch(DBValidationException ex){
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        recordExistence=false;
                    }else{
                        
                        throw ex;
                    }
            }
        
        if(!recordExistence){
            
            
           dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT",309,studentID,notificationID,date,endPoint,"W","");
            
            
        }else{
            
            
           String status= studentNotificationRec.getRecord().get(4).trim();
           
           
           if(status.equals("F")){
               
                 Map<String,String>l_column_to_update=new HashMap();
                 l_column_to_update.put("5", "W");
            
                dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT", "STUDENT_NOTIFICATION_STATUS", statusPKey, l_column_to_update, session);
   
               
           }
           
            
        }   
        
        
        tc.commit(session, dbSession);
           
           dbg("end of updateWIP");
           
       }catch(DBValidationException ex){
           dbg(ex);
           tc.rollBack(session, dbSession);
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            tc.rollBack(session, dbSession);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            tc.rollBack(session, dbSession);
            throw new BSProcessingException("Exception" + ex.toString());
        }
       
   } 
     
     
     

private void studentLevelStatusUpdate(String studentID,String date,String endPoint,String status,String exception,String instituteID)throws  BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    
    try{

        dbg("inside studentLevelStatusUpdate");
        String[] dateArr=date.split("-");
        String notificationID="A"+dateArr[0]+dateArr[1]+dateArr[2];
        String[] statusPKey={studentID,notificationID,date,endPoint};
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBTransactionService dbts=inject.getDBTransactionService();
        BatchUtil batchUtil=inject.getBatchUtil(session);
        
        
            Map<String,String>l_column_to_update=new HashMap();
            l_column_to_update.put("5", status);
            l_column_to_update.put("6",exception);
            
             dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT", "STUDENT_NOTIFICATION_STATUS", statusPKey, l_column_to_update, session);
        
        
        
        
        dbg("end of studentLevelStatusUpdate");
        
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












private void studentLevelMessageUpdate(String studentID,String date,String message,String instituteID)throws  BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    
    try{
        
        
        dbg("inside studentLevelMessageUpdate");
        dbg("inside studentLevelMessageUpdate--->message-->"+message);
        
        String[] dateArr=date.split("-");
        String notificationID="A"+dateArr[0]+dateArr[1]+dateArr[2];
        
        String[] statusPKey={studentID,notificationID,date};
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBTransactionService dbts=inject.getDBTransactionService();
        boolean recordExistence=false;
        
        try{
            
            
            
           
            
            readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT", "STUDENT_NOTIFICATION_MESSAGE", statusPKey, session, dbSession);
            
            recordExistence=true;
            
        }catch(DBValidationException ex){
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        recordExistence=false;
                    }else{
                        
                        throw ex;
                    }
            }
        
        dbg("inside studentLevelMessageUpdate--->recordExistence-->"+recordExistence);
        
        if(recordExistence){
            
            Map<String,String>l_column_to_update=new HashMap();
            l_column_to_update.put("4", message);
            
             dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT", "STUDENT_NOTIFICATION_MESSAGE", statusPKey, l_column_to_update, session);
        }else{
            
            
           dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT",358,studentID,notificationID,date,message);
            
        }
        
        
        
        dbg("end of student level message update");
        
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














      
  private String getAttendanceMessageBody(String instituteID)throws  BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
      
      try{
      
          String messageHeader=new String();
          String messageFooter=new String();
          String messageBody=new String();
//          IPDataService pds=inject.getPdataservice();
          IDBReadBufferService readBuffer=inject.getDBReadBufferService();
          IBDProperties i_db_properties=session.getCohesiveproperties();
          String[] l_masterpkey={instituteID,"Attendance"};
          DBRecord templateMasterRecord=null;
          boolean masterRecordExistence=false;
          String[] pkey={instituteID};
          IPDataService pds=inject.getPdataservice();
          ArrayList<String>contractList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",pkey);
           String language=contractList.get(12).trim();
           dbg("language"+language);
           try{
              
              
              templateMasterRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Template","INSTITUTE","IVW_MESSAGE_TEMPLATE_MASTER", l_masterpkey,session,dbSession);
            
              String recStatus=templateMasterRecord.getRecord().get(6).trim();
              String authStatus=templateMasterRecord.getRecord().get(7).trim();
              
              if(recStatus.equals("O")&&authStatus.equals("A")){
                  
                  masterRecordExistence=true;
              }
              
              
          }catch(DBValidationException ex){
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        masterRecordExistence=false;
                    }else{
                        
                        throw ex;
                    }
            }

          
          if(masterRecordExistence){
              
              
          DBRecord templateHeaderRecord=null;
          String[] headerPkey={instituteID,"Attendance","H"};
           try{
              
              
              templateHeaderRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Template","INSTITUTE","IVW_MESSAGE_TEMPLATE_DETAIL", headerPkey,session,dbSession);
            
              if(language.equals("EN")){
                  
                 messageHeader=   templateHeaderRecord.getRecord().get(3).trim(); 
              
              }else{
                  
                 messageHeader=   templateHeaderRecord.getRecord().get(5).trim();  
              }
              
          }catch(DBValidationException ex){
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                    }else{
                        
                        throw ex;
                    }
            }

          
          DBRecord templateBodyRecord=null;
          String[] bodyPkey={instituteID,"Attendance","B"};
           try{
              
              
              templateBodyRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Template","INSTITUTE","IVW_MESSAGE_TEMPLATE_DETAIL", bodyPkey,session,dbSession);
             if(language.equals("EN")){
                  
              messageBody=   templateBodyRecord.getRecord().get(3).trim();              
              
             }else{
                 
                 messageBody=   templateBodyRecord.getRecord().get(5).trim(); 
             }
              
          }catch(DBValidationException ex){
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                    }else{
                        
                        throw ex;
                    }
            }

             
              DBRecord templateFooterRecord=null;
          String[] footerPkey={instituteID,"Attendance","F"};
           try{
              
              
              templateFooterRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Template","INSTITUTE","IVW_MESSAGE_TEMPLATE_DETAIL", footerPkey,session,dbSession);
            
               if(language.equals("EN")){
                  
              messageFooter=   templateFooterRecord.getRecord().get(3).trim();  
               }else{
              
                   messageFooter=   templateFooterRecord.getRecord().get(5).trim();
                   
               }
              
              
          }catch(DBValidationException ex){
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                    }else{
                        
                        throw ex;
                    }
            }
             
             
          }
          
          
          
          if(messageBody.isEmpty()){
              
              
              if(language.equals("EN")){
              
                  messageBody="Absent today , please contact school";
              
              }else{
                  
                  messageBody="இன்று வருகை இல்லை, தயவுசெய்து பள்ளியை தொடர்பு கொள்ளவும்";
              }
              
              
          }
          
          String message=messageHeader.concat(messageBody).concat(messageFooter);
          
          
          dbg("message"+message);
          
          return message;
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
      
      
        private Email getEmailObject(String instituteID,String message,String toEmail)throws BSProcessingException,BSValidationException{
         try{
             
           dbg("inside NotificationService--->getEmailObject");
           IPDataService pds=inject.getPdataservice();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           BusinessService bs=inject.getBusinessService(session);
           String instituteName=bs.getInstituteName(instituteID, session, dbSession, inject);
           dbg("message"+message);

                String[] pkey={instituteID};
                ArrayList<String>contractList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",pkey);
           
           String fromEmail=contractList.get(9).trim().replace("AATT;", "@");
           String subject="Email Notification from "+instituteName;
           String textBody="This is Email Notification from "+instituteName;
           String htmlBody="<h1> Email Notification from "+instituteName+"</h1>"
                          +"<h5>Dear Parents,</h5>"
                          +"<p>Please find below today updates</p>"   
                          + "<p>"+message
                              +"</p>"
                            +"</br>"  
                            +"</br>"
                            +"<p>If you have any queries, please contact school</p>" 
                            +"<p>Thanks and Regards,<p>"
                            +"<p>"+instituteName+"<p>"
                            +"</br>"  
                            +"</br>"  
                            +"</br>"  
                            +"</br>"  
                            +"</br>"  
                   
                   +"<p> <u>This is Auto generated email , please do not reply</u></p>"
                              ;
                
            Email email=new  Email();
            
            email.setFromEmail(fromEmail);
            
            String email1=toEmail.replace("AATT;", "@");
            
            email.setToEmail(email1
            );
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
  
  
      
      

    
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }   
 
    
}
