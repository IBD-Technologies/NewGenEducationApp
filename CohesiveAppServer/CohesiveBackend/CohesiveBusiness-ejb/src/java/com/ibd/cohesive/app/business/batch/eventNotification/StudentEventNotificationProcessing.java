/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.eventNotification;

import com.ibd.businessViews.IAmazonEmailService;
import com.ibd.businessViews.IAmazonSMSService;
import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
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
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
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
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Stateless
public class StudentEventNotificationProcessing implements IStudentEventNotificationProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public StudentEventNotificationProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
     
        public void processing (String instituteID,String studentID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       BatchUtil batchUtil=null;
       boolean l_session_created_now=false;
       ITransactionControlService tc=null;
       try{
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();  
        dbg("inside student eventNotification processing ");
        dbg("instituteID"+instituteID);
        dbg("l_businessDate"+l_businessDate);
        dbg("studentID"+studentID);
        
        tc=inject.getTransactionControlService();
        BusinessService bs=inject.getBusinessService(session);
        String startTime=bs.getCurrentDateTime();
        batchUtil=inject.getBatchUtil(session);
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBTransactionService dbts=inject.getDBTransactionService();
        NotificationUtil notificationUtil =inject.getNotificationUtil(session);
        Map<String,DBRecord>eventNotificationEodMap=null;
        IAmazonSMSService smsService=inject.getAmazonSMSService();
        IAmazonEmailService emailService=inject.getAmazonEmailService();
        ILockService lock=inject.getLockService();
        BSValidation bsv=inject.getBsv(session);
         List<DBRecord>unProcessedRecords=null;
         String currentDate=bs.getCurrentDate();
         IPDataService pds=inject.getPdataservice();

                 //start time update starts
          Map<String,String>l_column_to_Update=new HashMap();
          l_column_to_Update.put("5", startTime);
          String[]pkey={instituteID,studentID,l_businessDate};
          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_EVENT_NOTIFICATION_EOD_STATUS", pkey, l_column_to_Update,session); 
          tc.commit(session, dbSession);
          
          boolean recordExistence=true;

           
           try{
           
              eventNotificationEodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"INSTITUTE", "TODAY_NOTIFICATION", session, dbSession);
              
              unProcessedRecords=eventNotificationEodMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals("U")&&rec.getRecord().get(0).trim().equals(studentID)).collect(Collectors.toList());
              
              if(unProcessedRecords.isEmpty()){
                  recordExistence=false;
              }
              
              
           }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                 
                          recordExistence=false; 
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_000");  
                          
                          
                        }else{

                              throw ex;
                        }
                       
                   }
           dbg("After reading today notification table-->recordExistence-->"+recordExistence);
           
           if(!recordExistence){
               
               String[] l_pkey={instituteID,studentID,l_businessDate};
               String endTime=bs.getCurrentDateTime();
               Map<String,String>column_to_Update=new HashMap();
               column_to_Update.put("6", endTime);
               column_to_Update.put("4", "S");
               dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_EVENT_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
               tc.commit(session, dbSession);
               return;  
               
           }else{
           
           
           dbg("after reading eventNotification table");
           dbg("unProcessedRecords size"+unProcessedRecords.size()); 
           String[] contractPkey={instituteID};
           String l_pkey=dbSession.getIibd_file_util().formingPrimaryKey(contractPkey);
           String fileName="APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive";
           String tableName="CONTRACT_MASTER";
           
           String mobileNo=null;
           List<DBRecord>smsRecords=unProcessedRecords.stream().filter(rec->rec.getRecord().get(2).trim().equals("S")).collect(Collectors.toList());
               String message=new String();
          

               if(!smsRecords.isEmpty()){
               
                    try{
               
               Map<String,List<DBRecord>>titleGroup=smsRecords.stream().collect(Collectors.groupingBy(rec->rec.getRecord().get(5)));
               
               
//               if(titleGroup.containsKey("Exam Result")){
//                
//                   
//                   List<DBRecord>examResultRecord=titleGroup.get("Exam Result");
//                   
//                   message=message.concat("Exam Result:"+ getMessageForTheTitle("Exam Result",examResultRecord));
//                   
//               }
//               if(titleGroup.containsKey("School Fees")){
//                
//                   List<DBRecord>schoolFeesRecord=titleGroup.get("School Fees");
//                   
//                   message=message.concat("School Fees:"+getMessageForTheTitle("School Fees",schoolFeesRecord));
//                   
//               }
//               if(titleGroup.containsKey("Attendance")){
//                
//                   List<DBRecord>attendanceRecord=titleGroup.get("Attendance");
//                   
//                   message=message.concat("Attendance:"+getMessageForTheTitle("Attendance",attendanceRecord));
//                   
//               }
//               if(titleGroup.containsKey("Disciplinary action")){
//                
//                   List<DBRecord>disciplinaryActionRecord=titleGroup.get("Disciplinary action");
//                   
//                   message=message.concat("Disciplinary action:"+getMessageForTheTitle("Disciplinary action",disciplinaryActionRecord));
//                   
//               }
//               if(titleGroup.containsKey("Video")){
//                
//                   List<DBRecord>videoRecord=titleGroup.get("Video");
//                   
//                   message=message.concat("Video:"+getMessageForTheTitle("Video",videoRecord));
//                   
//               }
//               if(titleGroup.containsKey("Homework")){
//                
//                   List<DBRecord>homeWorkRecord=titleGroup.get("Homework");
//                   
//                   message=message.concat("Homework:"+getMessageForTheTitle("Homework",homeWorkRecord));
//                   
//               }
//               
//               
//               
//               if(titleGroup.containsKey("தேர்வு முடிவு")){
//                
//                   
//                   List<DBRecord>examResultRecord=titleGroup.get("தேர்வு முடிவு");
//                   
//                   message=message.concat("தேர்வு முடிவு:"+ getMessageForTheTitle("தேர்வு முடிவு",examResultRecord));
//                   
//               }
//               if(titleGroup.containsKey("பள்ளி கட்டணம்")){
//                
//                   List<DBRecord>schoolFeesRecord=titleGroup.get("பள்ளி கட்டணம்");
//                   
//                   message=message.concat("பள்ளி கட்டணம்:"+getMessageForTheTitle("பள்ளி கட்டணம்",schoolFeesRecord));
//                   
//               }
//               if(titleGroup.containsKey("வருகை")){
//                
//                   List<DBRecord>attendanceRecord=titleGroup.get("வருகை");
//                   
//                   message=message.concat("வருகை:"+getMessageForTheTitle("வருகை",attendanceRecord));
//                   
//               }
//               if(titleGroup.containsKey("ஒழுங்கு நடவடிக்கை")){
//                
//                   List<DBRecord>disciplinaryActionRecord=titleGroup.get("ஒழுங்கு நடவடிக்கை");
//                   
//                   message=message.concat("ஒழுங்கு நடவடிக்கை:"+getMessageForTheTitle("ஒழுங்கு நடவடிக்கை",disciplinaryActionRecord));
//                   
//               }
//               if(titleGroup.containsKey("வீடியோ")){
//                
//                   List<DBRecord>videoRecord=titleGroup.get("வீடியோ");
//                   
//                   message=message.concat("வீடியோ:"+getMessageForTheTitle("வீடியோ",videoRecord));
//                   
//               }
//               if(titleGroup.containsKey("வீட்டு பாடம்")){
//                
//                   List<DBRecord>homeWorkRecord=titleGroup.get("வீட்டு பாடம்");
//                   
//                   message=message.concat("வீட்டு பாடம்:"+getMessageForTheTitle("வீட்டு பாடம்",homeWorkRecord));
//                   
//               }
               
               Iterator<String>titleIterator=titleGroup.keySet().iterator();
                
               while(titleIterator.hasNext()){
                   
                   String title=titleIterator.next();
                   List<DBRecord>titleRecord=titleGroup.get(title);
//                   
                   message=message.concat(title+getMessageForTheTitle(title,titleRecord));
                   
               }
               
               
               String mshheader=notificationUtil.getMessageHeader(instituteID, studentID, session, dbSession, inject);
               dbg("mshheader-->"+mshheader);
               ArrayList<String> contract=pds.readRecordPData(session, dbSession, fileName, "APP", tableName, contractPkey);
               
               String plan=contract.get(14).trim();
               
               String messageToSend;
               
               if(!plan.equals("B")){
                   
                   String msgFooter=notificationUtil.getMessageFooter();
                   messageToSend=mshheader.concat(message).concat(msgFooter);
                   
               }else{
                   messageToSend=mshheader.concat(message);
               }
               
               
               mobileNo=smsRecords.get(0).getRecord().get(1).trim();
               dbg("messageToSend"+messageToSend);
               dbg("mobileNo"+mobileNo);


                        if(dbts.getImplictRecordLock(fileName,tableName,l_pkey,this.session)==true){ 

                                      if( lock.isSameSessionRecordLock(fileName,tableName,l_pkey, this.session)){

                                     if(bsv.smsUsageValidation(instituteID, session, dbSession, inject)) {  

                                        smsService.sendSMS(messageToSend, mobileNo, session,instituteID);
                                        Thread.sleep(30000);
                                        bs.updateSMSUsage(instituteID, session, dbSession, inject,messageToSend);

                                        this.updateEventNotificationStatus(instituteID, studentID, "S", session, dbSession, inject,"S");
                                         batchUtil.updateStudentNotificationStatusTable(studentID, "Event", l_businessDate, mobileNo, "S", " ", instituteID, inject, session, dbSession);
                                         this.studentLevelMessageUpdate(studentID, l_businessDate, messageToSend,instituteID);
                                         tc.commit(session, dbSession);
                                     }else {
                                         tc.rollBack(session, dbSession);
                                         
                                         
//                                         batchUtil.updateStudentNotificationStatusTable(studentID, "Event", l_businessDate, mobileNo, "F", "Limit exhausted", instituteID, inject, session, dbSession);
//                                         this.updateEventNotificationStatus(instituteID, studentID, "F", session, dbSession, inject,"S");
//                                         tc.commit(session, dbSession);
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
                     batchUtil.updateStudentNotificationStatusTable(studentID, "EventNotification", l_businessDate, mobileNo, "F", l_replacedException, instituteID, inject, session, dbSession);
                     this.updateEventNotificationStatus(instituteID, studentID, "F", session, dbSession, inject,"S");
                     tc.commit(session, dbSession);
                     throw ex;
                }
                    
           } 
            List<DBRecord>emailRecords=unProcessedRecords.stream().filter(rec->rec.getRecord().get(2).trim().equals("M")).collect(Collectors.toList());
            String emailmessage=new String();
            String emailID=null;
            
            if(!emailRecords.isEmpty()){
            
            try{
            
            
           Map<String,List<DBRecord>>emailTitleGroup=emailRecords.stream().collect(Collectors.groupingBy(rec->rec.getRecord().get(5)));
            
           Iterator<String>titleIterator=emailTitleGroup.keySet().iterator();
           
            while(titleIterator.hasNext()){
                
                String title=titleIterator.next();
                
                List<DBRecord>recordsForTheTitle=emailTitleGroup.get(title);
                
                emailmessage=emailmessage.concat("<h5> <u>"+title+" </u></h5>");
                for(int i=0;i<recordsForTheTitle.size();i++){
                    
                    String msg=recordsForTheTitle.get(i).getRecord().get(3).trim();
                    emailmessage=emailmessage.concat(msg);
                }

            }
            
            
     
           
            emailID=emailRecords.get(0).getRecord().get(1).trim();
            dbg("emailmessage"+emailmessage);
               dbg("emailID"+emailID);
           
            if(dbts.getImplictRecordLock(fileName,tableName,l_pkey,this.session)==true){ 

                            if( lock.isSameSessionRecordLock(fileName,tableName,l_pkey, this.session)){

                                 if(bsv.emailUsageValidation(instituteID, session, dbSession, inject)) {    

                                          Email emailObj=this.getEmailObject(instituteID, emailmessage, emailID);
                                          emailService.sendEmail(emailObj, session);
                                          bs.updateEmailUsage(instituteID, session, dbSession, inject);
                                          this.updateEventNotificationStatus(instituteID, studentID, "S", session, dbSession, inject,"M");
                                          batchUtil.updateStudentNotificationStatusTable(studentID, "Event", l_businessDate, emailID, "S", " ", instituteID, inject, session, dbSession);
                                          tc.commit(session, dbSession);
                                 }else{
                                     tc.rollBack(session, dbSession);

//                                     this.updateEventNotificationStatus(instituteID, studentID, "F", session, dbSession, inject,"M");
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
             batchUtil.updateStudentNotificationStatusTable(studentID, "EventNotification", l_businessDate, emailID, "F", l_replacedException, instituteID, inject, session, dbSession);
             this.updateEventNotificationStatus(instituteID, studentID, "F", session, dbSession, inject,"M");
             tc.commit(session, dbSession);
                     throw ex;
            }
           
           }
          
          
           }
         batchUtil.studentEventNotificationProcessingSuccessHandler(instituteID, l_businessDate, studentID, inject, session, dbSession);
        
         dbg("end of student eventNotification processing");
        }catch(DBValidationException ex){
          batchUtil.studentEventNotificationProcessingErrorHandler(instituteID, l_businessDate, studentID, ex, inject, session, dbSession);
        }catch(BSValidationException ex){
          batchUtil.studentEventNotificationProcessingErrorHandler(instituteID, l_businessDate, studentID, ex, inject, session, dbSession);
      
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.studentEventNotificationProcessingErrorHandler(instituteID, l_businessDate, studentID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.studentEventNotificationProcessingErrorHandler(instituteID, l_businessDate, studentID, ex, inject, session, dbSession);
     }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
}

    public void processing(String instituteID,String studentID,String l_businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           this.processing(instituteID, studentID, l_businessDate);
       
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
    
    
    @Asynchronous
   public Future<String> parallelProcessing(String instituteID,String studentID,String l_businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    try{   
    
       this.processing(instituteID, studentID, l_businessDate);
        
              return new AsyncResult<String>("Success");

       
        }catch(Exception ex){
           dbg(ex);
           return new AsyncResult<String>("Fail");
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
  
  
    public void updateEventNotificationStatus(String instituteID,String studentID,String status,CohesiveSession session,DBSession dbSession,AppDependencyInjection inject,String mode)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
        
        try{
            
            dbg("inside updateRecordInTodayNotification");
            ITransactionControlService tc=inject.getTransactionControlService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IDBTransactionService dbts=inject.getDBTransactionService();
            BusinessService bs=inject.getBusinessService(session);
            String currentDate=bs.getCurrentDate();
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            
            Map<String,DBRecord>eventNotificationEodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"INSTITUTE", "TODAY_NOTIFICATION", session, dbSession);
              
            List<DBRecord>  unProcessedRecords=eventNotificationEodMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals("U")&&rec.getRecord().get(0).trim().equals(studentID)&&rec.getRecord().get(2).trim().equals(mode)).collect(Collectors.toList());
            
            
            for(int i=0;i<unProcessedRecords.size();i++){
                
                ArrayList<String>recordValues=unProcessedRecords.get(i).getRecord();
                String endPoint=recordValues.get(1).trim();
                String title=recordValues.get(5).trim();
                
                 String[] pkey={studentID,endPoint,title};

           


                 Map<String,String>l_columnToUpdate=new HashMap();
                 
                 l_columnToUpdate.put("5", status);



                 dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"INSTITUTE", "TODAY_NOTIFICATION", pkey, l_columnToUpdate, session);
            }
            
            
            
           

//            tc.commit(session, dbSession);
        }catch(DBValidationException ex){
            dbg(ex);
            throw ex;
        }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(Exception ex){
            dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
    }
    
  
    private String messageFormation(String inputMessage,String outputMessage)throws BSProcessingException{
        
        try{
            
            dbg("inside message formation--inputMessage>"+inputMessage);
            dbg("inside message formation--outputMessage>"+outputMessage);
            
            outputMessage=outputMessage.concat(inputMessage);
            
            ByteBuffer copy=ByteBuffer.wrap(outputMessage.getBytes(Charset.forName("UTF-8")));
            
            if(copy.limit()>320){
                     
//                     correctLengthMsg=newMessage.substring(0,319);
                       byte[] dst=new byte[320];
                 for (int i=0;i<=319;i++)
                 {    
                     dst[i] = copy.get();

                 }

                 outputMessage = new String(dst, Charset.forName("UTF-8"));
                 
                 
                 dbg("copy.limit()"+copy.limit());
                 }
            
            dbg("end of message formation--outputMessage>"+outputMessage);
            return outputMessage;
        }catch(Exception ex){
            dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
    }
    
    private String getMessageForTheTitle(String title,List<DBRecord> recordsForTheTitle)throws BSProcessingException{
        
        try{
            
            dbg("inside mgetMessageForTheTitle--title>"+title);
            String messageForTheTitle=new String();
            
            for(int i=0;i<recordsForTheTitle.size();i++){
                       
                       String examResultMessage=recordsForTheTitle.get(i).getRecord().get(3).trim();
                       
                       messageForTheTitle= messageFormation(examResultMessage,messageForTheTitle);
                   }
            
            
            
            
            dbg("end of message formation--getMessageForTheTitle>"+messageForTheTitle);
            
            return messageForTheTitle;
        }catch(Exception ex){
            dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
    }
    private void studentLevelMessageUpdate(String studentID,String date,String message,String instituteID)throws  BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    
    try{
        
        
        dbg("inside studentLevelMessageUpdate");
        dbg("inside studentLevelMessageUpdate--->message-->"+message);
        
        String[] dateArr=date.split("-");
        String notificationID="E"+dateArr[0]+dateArr[1]+dateArr[2];
        
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




  
//    private void buildRequestAndCallStudentFee(String l_studentID,String l_eventNotificationID,int p_versionNumber,String instituteID,String businessDate)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
//     ITransactionControlService tc=null;
//        try{
//        dbg("inside buildRequestAndCallStudentFee") ;   
//        dbg("versionNumber"+p_versionNumber);
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        String l_instituteID=instituteID;
//        IDBTransactionService dbts=inject.getDBTransactionService();
//        tc=inject.getTransactionControlService();
//        BusinessService bs=inject.getBusinessService(session);
//        String currentDate=bs.getCurrentDate();
//           
//         try{
//              dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"FEE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","FEE",63,l_studentID,l_eventNotificationID);
//       
//              
//              
//              
////              bs.updateRecordInTodayNotification(instituteID, l_studentID, l_eventNotificationID, l_instituteID, businessDate, session, dbSession, inject);
//              
//            
//              
//              
//              
//              tc.commit(session, dbSession);
//        }catch(DBValidationException ex){
//            tc.rollBack(session, dbSession);
//            if(!ex.toString().contains("DB_VAL_009")){
//                dbg(ex);
//                throw ex;
//            }
//        }
//          
//   
//          
//        dbg("end of buildRequestAndCallstudentFee");  
//        }catch(DBValidationException ex){
//            dbg(ex);
//            tc.rollBack(session, dbSession);
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            tc.rollBack(session, dbSession);
//            throw new DBProcessingException(ex.toString());
////        }catch(BSProcessingException ex){
////            dbg(ex);
////            throw new BSProcessingException(ex.toString());    
////        }catch(BSValidationException ex){
////            throw ex;
//        }catch (Exception ex) {
//            dbg(ex);
//            tc.rollBack(session, dbSession);
//            throw new BSProcessingException("Exception" + ex.toString());
//        }
//          
// }
     
     
//      public void processing (String instituteID,String studentID,String eventNotificationID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//       BatchUtil batchUtil=null;
//       boolean l_session_created_now=false;
//       ITransactionControlService tc=null;
//       try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();  
//        dbg("inside student eventNotification processing ");
//        dbg("instituteID"+instituteID);
//        dbg("eventNotificationID"+eventNotificationID);
//        dbg("l_businessDate"+l_businessDate);
//        dbg("studentID"+studentID);
//        
//        tc=inject.getTransactionControlService();
//        BusinessService bs=inject.getBusinessService(session);
//        String startTime=bs.getCurrentDateTime();
//        batchUtil=inject.getBatchUtil(session);
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IDBTransactionService dbts=inject.getDBTransactionService();
//            
//                 //start time update starts
//          Map<String,String>column_to_Update=new HashMap();
//          column_to_Update.put("7", startTime);
//          String[]l_pkey={instituteID,eventNotificationID,studentID,l_businessDate};
//          dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_FEE_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
//          tc.commit(session, dbSession);
//          //start time update ends
//          DBRecord studentFeeRec;
//          String versionNumber=null;
//          boolean recordExistence=true;
//          
//          try{
//        
//            String[]l_primaryKey={eventNotificationID};  
//            studentFeeRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_FEE_MANAGEMENT", l_primaryKey, session, dbSession,true);
//            versionNumber=studentFeeRec.getRecord().get(15).trim();
//            dbg("versionNumber"+versionNumber);
//         }catch(DBValidationException ex){
//            if(ex.toString().contains("DB_VAL_011")){
//                
//                dbg("printing error inside student eventNotification processing"+ex.toString());
//                recordExistence=false;
//                
//            }else{
//                throw ex;
//            }
//         }catch(Exception ex){
//             
//             dbg("printing error inside student eventNotification processing"+ex.toString());
//             if(ex.toString().contains("DB_VAL_000")){
//                
//                recordExistence=false;
//                
//            }else{
//                throw ex;
//            }
//             
//             
//         }
//          
//         dbg("recordExistence"+recordExistence); 
//          if(!recordExistence){
//              
//              buildRequestAndCallStudentFee(studentID,eventNotificationID,1,instituteID);
//          }else{
//              
//              buildRequestAndCallStudentFee(studentID,eventNotificationID,Integer.parseInt(versionNumber)+1,instituteID);
//          }
//          
//
//         batchUtil.studenteventNotificationNotificationProcessingSuccessHandler(instituteID, l_businessDate, eventNotificationID, studentID, inject, session, dbSession);
//        
//         dbg("end of student eventNotification processing");
//        }catch(DBValidationException ex){
//          batchUtil.studenteventNotificationNotificationProcessingErrorHandler(instituteID, l_businessDate, eventNotificationID, studentID, ex, inject, session, dbSession);
//        }catch(BSValidationException ex){
//          batchUtil.studenteventNotificationNotificationProcessingErrorHandler(instituteID, l_businessDate, eventNotificationID, studentID, ex, inject, session, dbSession);
//      
//        }catch(DBProcessingException ex){
//          dbg(ex);
//          batchUtil.studenteventNotificationNotificationProcessingErrorHandler(instituteID, l_businessDate, eventNotificationID, studentID, ex, inject, session, dbSession);
//        }catch(Exception ex){
//           dbg(ex);
//           batchUtil.studenteventNotificationNotificationProcessingErrorHandler(instituteID, l_businessDate, eventNotificationID, studentID, ex, inject, session, dbSession);
//     }finally{
//               if(l_session_created_now){    
//                  dbSession.clearSessionObject();
//                  session.clearSessionObject();
//               }
//           }
//}
//
//    public void processing(String instituteID,String studentID,String eventNotificationID,String l_businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//      
//       CohesiveSession tempSession = this.session;
//       
//       try{
//           
//           this.session=session;
//           processing(instituteID,studentID,eventNotificationID,l_businessDate);
//       
//      }catch(DBValidationException ex){
//          throw ex;
//      }catch(BSValidationException ex){
//          throw ex;     
//      }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//      }catch(BSProcessingException ex){
//           dbg(ex);
//           throw new BSProcessingException(ex.toString());
//     }catch(Exception ex){
//           dbg(ex);
//           throw new BSProcessingException(ex.toString());
//     }finally {
//           this.session=tempSession;
//            
//        } 
//   }
//    
//    
//    @Asynchronous
//   public Future<String> parallelProcessing(String instituteID,String studentID,String eventNotificationID,String l_businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//   
//    try{   
//    
//        processing(instituteID,studentID,eventNotificationID,l_businessDate);
//        
//              return new AsyncResult<String>("Success");
//
//       
//        }catch(Exception ex){
//           dbg(ex);
//           return new AsyncResult<String>("Fail");
//     }
//    
//}
//   
//    private void buildRequestAndCallStudentFee(String l_studentID,String l_eventNotificationID,int p_versionNumber,String instituteID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
//     
//        try{
//        dbg("inside buildRequestAndCallStudentFee") ;   
//        dbg("versionNumber"+p_versionNumber);
//        IStudentFeeManagementService sfms=inject.getStudentFeeManagementService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        String l_instituteID=instituteID;
//        JsonObject studentFee;
//        String l_msgID="";
//        String l_correlationID="";
//        String l_service="StudentFeeManagement";
//        String l_operation="AutoAuth";
//        JsonArray l_businessEntity=Json.createArrayBuilder().add(Json.createObjectBuilder().add("entityName", "studentID")
//                                                                                             .add("entityValue", l_studentID)).build();
//        String l_userID="system";
//        String l_source="cohesive_backend";
//        String l_status=" ";
//        
//        String[]l_pkey={l_eventNotificationID};
//        ArrayList<String> eventNotificationList=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_FEE_MANAGEMENT", l_pkey, session,dbSession).getRecord();
//        
//        
//        String l_eventNotificationType=eventNotificationList.get(3).trim();
//        String l_amount=eventNotificationList.get(4).trim();
//        String l_dueDate=eventNotificationList.get(5).trim();
//        String l_eventNotificationstatus="U";
//        String l_eventNotificationPaid=" ";
//        String l_outStanding=" ";
//        String l_paidDate=" ";
//        String l_paymentMode=" ";
//        
//        String l_makerID="";
//        String l_checkerID="";
//        String l_makerDateStamp="";
//        String l_checkerDateStamp="";
//        String l_recordStatus=eventNotificationList.get(11).trim();
//        String l_authStatus=eventNotificationList.get(12).trim();
//        String l_makerRemarks=eventNotificationList.get(14).trim();
//        String l_checkerRemarks=eventNotificationList.get(15).trim();
//
//
//          studentFee=Json.createObjectBuilder().add("header", Json.createObjectBuilder()
//                                                        .add("msgID",l_msgID)
//                                                        .add("correlationID", l_correlationID)
//                                                        .add("service", l_service)
//                                                        .add("operation", l_operation)
//                                                        .add("businessEntity", l_businessEntity)
//                                                        .add("instituteID", l_instituteID)
//                                                        .add("status", l_status)
//                                                        .add("source", l_source)
//                                                        .add("userID",l_userID))
//                                                        .add("body",Json.createObjectBuilder()
//                                                        .add("studentID", l_studentID)
//                                                        .add("eventNotificationID", l_eventNotificationID)
//                                                        .add("eventNotificationType", l_eventNotificationType)
//                                                        .add("amount", l_amount)        
//                                                        .add("dueDate", l_dueDate)
//                                                        .add("status", l_eventNotificationstatus)
//                                                        .add("eventNotificationPaid", l_eventNotificationPaid)
//                                                        .add("outStanding", l_outStanding)
//                                                        .add("paidDate", l_paidDate)
//                                                        .add("paymentMode", l_paymentMode))
//                                                        .add("auditLog",  Json.createObjectBuilder()
//                                                        .add("makerID", l_makerID)
//                                                        .add("checkerID", l_checkerID)
//                                                        .add("makerDateStamp", l_makerDateStamp)
//                                                        .add("checkerDateStamp", l_checkerDateStamp)
//                                                        .add("recordStatus", l_recordStatus)
//                                                        .add("authStatus", l_authStatus)
//                                                        .add("versionNumber", Integer.toString(p_versionNumber))
//                                                        .add("makerRemarks", l_makerRemarks)
//                                                        .add("checkerRemarks", l_checkerRemarks)).build();
//          
//          dbg("studentFeerequest"+studentFee.toString());
//          dbg("before  studentFee call");
//          sfms.processing(studentFee, session);
//          dbg("after studentFee call");
//          
//          
//          
//          
//          
//          
//        dbg("end of buildRequestAndCallstudentFee");  
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException(ex.toString());
//        }catch(BSProcessingException ex){
//            dbg(ex);
//            throw new BSProcessingException(ex.toString());    
//        }catch(BSValidationException ex){
//            throw ex;
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//        }
//          
// }
   
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
}
