/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.notification;

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
public class StudentNotificationProcessing implements IStudentNotificationProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public StudentNotificationProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
      public void processing (String instituteID,String studentID,String notificationID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       BatchUtil batchUtil=null;
       boolean l_session_created_now=false;
       ITransactionControlService tc=null;
       try{
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();  
        dbg("inside student notification processing ");
        dbg("instituteID"+instituteID);
        dbg("notificationID"+notificationID);
        dbg("l_businessDate"+l_businessDate);
        dbg("studentID"+studentID);
        
        tc=inject.getTransactionControlService();
        BusinessService bs=inject.getBusinessService(session);
        String startTime=bs.getCurrentDateTime();
        batchUtil=inject.getBatchUtil(session);
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBTransactionService dbts=inject.getDBTransactionService();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        NotificationUtil notificationUtil=inject.getNotificationUtil(session);

                 //start time update starts
          Map<String,String>column_to_Update=new HashMap();
          column_to_Update.put("7", startTime);
          String[]l_pkey={instituteID,notificationID,studentID,l_businessDate};
          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification"+i_db_properties.getProperty("FOLDER_DELIMITER")+notificationID, "BATCH", "STUDENT_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
          tc.commit(session, dbSession);
          //start time update ends
//          DBRecord studentNotificationRec;
//          String versionNumber=null;
//          boolean recordExistence=true;
          
//          try{
//        
//            String[]l_primaryKey={notificationID,l_businessDate};  
//            studentNotificationRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_NOTIFICATION", l_primaryKey, session, dbSession,true);
//            versionNumber=studentNotificationRec.getRecord().get(15).trim();
//            dbg("versionNumber"+versionNumber);
//         }catch(DBValidationException ex){
//            if(ex.toString().contains("DB_VAL_011")){
//                
//                dbg("printing error inside student notification processing"+ex.toString());
//                recordExistence=false;
//                
//            }else{
//                throw ex;
//            }
//         }catch(Exception ex){
//             
//             dbg("printing error inside student notification processing"+ex.toString());
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
          
//         dbg("recordExistence"+recordExistence); 
//          if(!recordExistence){
              
        String[] pkey={notificationID};
        ArrayList<String> notificationList=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","INSTITUTE","IVW_NOTIFICATION_MASTER", pkey, session,dbSession).getRecord();
             
        String notificationType=notificationList.get(2).trim();
        
        dbg("notificationType"+notificationType);
        if(!notificationType.equals("Emergency")){
            
            String l_message=notificationList.get(7).trim();
        String l_otherLangMessage=notificationList.get(22).trim();
        String l_mode=notificationList.get(8).trim();
//           MessageInput messageInput= notificationUtil.getNotificationMessageInput(studentID, l_message,);
            
           notificationUtil.updateInstantRecordInTodayNotification(instituteID, studentID, notificationType,l_mode,l_message,l_otherLangMessage, session, dbSession, inject);
           
           
           
        }else{
        
           buildRequestAndCallStudentNotification(studentID,notificationID,1,instituteID,l_businessDate);

        } 

//          }else{
              
//              buildRequestAndCallStudentNotification(studentID,notificationID,Integer.parseInt(versionNumber)+1,instituteID);
//          }
          

         batchUtil.studentNotificationProcessingSuccessHandler(instituteID, l_businessDate, notificationID, studentID, inject, session, dbSession);
        
         dbg("end of student notification processing");
        }catch(DBValidationException ex){
          batchUtil.studentNotificationProcessingErrorHandler(instituteID, l_businessDate, notificationID, studentID, ex, inject, session, dbSession);
        }catch(BSValidationException ex){
          batchUtil.studentNotificationProcessingErrorHandler(instituteID, l_businessDate, notificationID, studentID, ex, inject, session, dbSession);
      
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.studentNotificationProcessingErrorHandler(instituteID, l_businessDate, notificationID, studentID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.studentNotificationProcessingErrorHandler(instituteID, l_businessDate, notificationID, studentID, ex, inject, session, dbSession);
     }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
}

    public void processing(String instituteID,String studentID,String notificationID,String l_businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(instituteID,studentID,notificationID,l_businessDate);
       
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
   public Future<String> parallelProcessing(String instituteID,String studentID,String notificationID,String l_businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    try{   
    
        processing(instituteID,studentID,notificationID,l_businessDate);
        
              return new AsyncResult<String>("Success");

       
        }catch(Exception ex){
           dbg(ex);
           return new AsyncResult<String>("Fail");
     }
    
}
   
    private void buildRequestAndCallStudentNotification(String l_studentID,String l_notificationID,int p_versionNumber,String instituteID,String businessDate)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
     ITransactionControlService tc=null;
        try{
        dbg("inside buildRequestAndCallStudentNotification") ;   
        dbg("versionNumber"+p_versionNumber);
//        IStudentNotificationService sns=inject.getStudentNotificationService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=instituteID;
        IDBTransactionService dbts=inject.getDBTransactionService();
         tc=inject.getTransactionControlService();
         NotificationUtil notificationUtil=inject.getNotificationUtil(session);
//        JsonObject studentNotification;
//        String l_msgID="";
//        String l_correlationID="";
//        String l_service="StudentNotification";
//        String l_operation="AutoAuth";
//        JsonArray l_businessEntity=Json.createArrayBuilder().add(Json.createObjectBuilder().add("entityName", "studentID")
//                                                                                             .add("entityValue", l_studentID)).build();
//        String l_userID="system";
//        String l_source="cohesive_backend";
//        String l_status=" ";
        
        String[]l_pkey={l_notificationID};
        ArrayList<String> notificationList=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","INSTITUTE","IVW_NOTIFICATION_MASTER", l_pkey, session,dbSession).getRecord();
        
        
//        String l_notificationType=notificationList.get(2).trim();
//        String l_date=notificationList.get(4).trim();

        String l_message;
        EndPoint endPoints=notificationUtil.getEndPoints(instituteID, l_studentID, session, dbSession, inject);
        
        String language=endPoints.getLanguage();
        
        if(language.equals("EN")){
            
            l_message=notificationList.get(7).trim();
            
        }else{
            
            
            l_message=notificationList.get(22).trim();
        }
        
        dbg("l_message-->"+l_message);
//        String l_message=notificationList.get(7).trim();
        String l_mode=notificationList.get(8).trim();
        String l_instant=notificationList.get(19).trim();
        
//        String l_makerID=notificationList.get(10).trim();
//        String l_checkerID=notificationList.get(11).trim();
//        String l_makerDateStamp=notificationList.get(12).trim();
//        String l_checkerDateStamp=notificationList.get(13).trim();
//        String l_recordStatus=notificationList.get(14).trim();
//        String l_authStatus=notificationList.get(15).trim();
//        String l_versionNumber=notificationList.get(16).trim();
//        String l_makerRemarks=notificationList.get(17).trim();
//        String l_checkerRemarks=notificationList.get(18).trim();
      
           
         try{


              dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT",123,l_studentID,l_notificationID,l_instant);
              tc.commit(session, dbSession);
           }catch(DBValidationException ex){
                tc.rollBack(session, dbSession);
                if(!ex.toString().contains("DB_VAL_009")){
                    dbg(ex);
                    throw ex;
                }
            }
          
          
          
          
         sendNotification(l_studentID,instituteID,l_message,businessDate,l_notificationID,l_mode);

        
        
        
//          studentNotification=Json.createObjectBuilder().add("header", Json.createObjectBuilder()
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
//                                                        .add("notificationID", l_notificationID)
//                                                        .add("notificationType", l_notificationType)
//                                                        .add("date", l_date)        
//                                                        .add("message", l_message))
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
//          dbg("studentNotificationrequest"+studentNotification.toString());
//          dbg("before  studentNotification call");
//          sns.processing(studentNotification, session);
//          dbg("after studentNotification call");
          
          
          
          
          
          
        dbg("end of buildRequestAndCallstudentNotification");  
        }catch(DBValidationException ex){
            dbg(ex);
            tc.rollBack(session, dbSession);
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            tc.rollBack(session, dbSession);
            throw new DBProcessingException(ex.toString());
//        }catch(BSProcessingException ex){
//            dbg(ex);
//            throw new BSProcessingException(ex.toString());    
//        }catch(BSValidationException ex){
//            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            tc.rollBack(session, dbSession);
            throw new BSProcessingException("Exception" + ex.toString());
        }
          
 }
   
    private void sendNotification(String studentID,String instituteID,String message,String l_businessDate,String notificationID,String mode)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
        
        try{
            
            dbg("inside sendNotification");
            
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            ILockService lock=inject.getLockService();
//            INotificationTransferService not=inject.getNotificationTransferService();
            IAmazonSMSService smsService=inject.getAmazonSMSService();
            IAmazonEmailService emailService=inject.getAmazonEmailService();
            IDBTransactionService dbts=inject.getDBTransactionService();
            BatchUtil batchUtil=inject.getBatchUtil(session);
            BusinessService bs=inject.getBusinessService(session);
            BSValidation bsv=inject.getBsv(session);
            String mobileNo=null;
            ArrayList<String>mailList=new ArrayList();
            ArrayList<String>mobileNoList=new ArrayList();
            ITransactionControlService tc=inject.getTransactionControlService();
            
            Map<String,DBRecord>l_contactPersonMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_FAMILY_DETAILS", session, dbSession);
            
            String[] studentPkey={studentID};
            
            DBRecord profileRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_PROFILE", studentPkey, session, dbSession);
            
            String masterVersion=profileRec.getRecord().get(19).trim();
            
            List<DBRecord>filteredRecordsList=l_contactPersonMap.values().stream().filter(rec->rec.getRecord().get(8).trim().equals(masterVersion)&&rec.getRecord().get(9).trim().equals("Y")).collect(Collectors.toList());
            
            Iterator<DBRecord>valueIterator= filteredRecordsList.iterator();
             
            while(valueIterator.hasNext()){
                
                DBRecord cpRecord=valueIterator.next();
                String emailId=cpRecord.getRecord().get(5).trim().replace("AATT;", "@");
//                String notificationrequired=cpRecord.getRecord().get(9).trim().replace("AATT;", "@");
//                dbg("notificationrequired"+notificationrequired);
                
                String emailStatus=null;
                if(emailId!=null&&!emailId.isEmpty()){
                
                String[] l_primaryKey={studentID,notificationID,l_businessDate,emailId};
                boolean emailrecordExistence=true;    
                    try{
                       
                    DBRecord emailStatusRecord=  readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT", "STUDENT_NOTIFICATION_STATUS", l_primaryKey, session, dbSession,true);
                       
                    emailStatus=emailStatusRecord.getRecord().get(4).trim();
                    
                   }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                 
			  emailrecordExistence=false;
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_000");  
              
                        }else{

                              throw ex;
                        }
                       
                   }
                    
                    if(!emailrecordExistence){
                    
                        
                        mailList.add(emailId);
                    
                    }else if(emailStatus.equals("F")){
                        
                        mailList.add(emailId);
                    }
                    
                }
//                String smsStatus=cpRecord.getRecord().get(9).trim();
                    
//                if(smsStatus.equals("Y")){
                    
                    mobileNo=cpRecord.getRecord().get(6).trim();
//                }

                String mobilNoStatus=null;
                if(mobileNo!=null&&!mobileNo.isEmpty()){
                
                   String[] l_primaryKey={studentID,notificationID,l_businessDate,mobileNo};
                boolean mobilerecordExistence=true;    
                    try{
                       
                    DBRecord mobileNoRecord=   readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT", "STUDENT_NOTIFICATION_STATUS", l_primaryKey, session, dbSession,true);
                    mobilNoStatus=   mobileNoRecord.getRecord().get(4).trim();
                   }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                 
			  mobilerecordExistence=false;
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_000");  
              
                        }else{

                              throw ex;
                        }
                       
                   }
                    
                    if(!mobilerecordExistence){
                    
                        mobileNoList.add(mobileNo);
                    
                    }else if(mobilNoStatus.equals("F")){
                        
                        mobileNoList.add(mobileNo);
                    }
                }
            }
            String[] contractPkey={instituteID};
            String pkey=dbSession.getIibd_file_util().formingPrimaryKey(contractPkey);
            String fileName="APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive";
            String tableName="CONTRACT_MASTER";
            if(mode.equals("B")||mode.equals("S")){
            
                for(int i=0;i<mobileNoList.size();i++){

                    String l_mobilNo=mobileNoList.get(i);

                    try{

                        
                        
                      if(dbts.getImplictRecordLock(fileName,tableName,pkey,this.session)==true){ 
              
                          if( lock.isSameSessionRecordLock(fileName,tableName,pkey, this.session)){
                        
                         if(bsv.smsUsageValidation(instituteID, session, dbSession, inject)) {  

                             NotificationUtil notificationUtil=inject.getNotificationUtil(session);
                             IPDataService pds=inject.getPdataservice();
                            String mshheader=notificationUtil.getMessageHeader(instituteID, studentID, session, dbSession, inject);
                           dbg("mshheader-->"+mshheader);
                           ArrayList<String> contract=pds.readRecordPData(session, dbSession, fileName, "APP", tableName, contractPkey);

                           String plan=contract.get(14).trim();

                           String messageWithHead;

                           if(!plan.equals("B")){

                               String msgFooter=notificationUtil.getMessageFooter();
                               messageWithHead=mshheader.concat(message).concat(msgFooter);

                           }else{
                               messageWithHead=mshheader.concat(message);
                           } 
                             
                           ByteBuffer copy=ByteBuffer.wrap(messageWithHead.getBytes(Charset.forName("UTF-8")));
                 String correctLengthMsg=null;
                
                             if(copy.limit()>280){

            //                     correctLengthMsg=newMessage.substring(0,319);
                                   byte[] dst=new byte[280];
                             for (int j=0;j<=279;j++)
                             {    
                                 dst[j] = copy.get();

                             }

                             correctLengthMsg = new String(dst, Charset.forName("UTF-8"));

                             }else{
                                correctLengthMsg= messageWithHead;
                             }   
                            smsService.sendSMS(correctLengthMsg, l_mobilNo, session,instituteID);
                            bs.updateSMSUsage(instituteID, session, dbSession, inject,correctLengthMsg);
                            batchUtil.updateStudentNotificationStatusTable(studentID, notificationID, l_businessDate, l_mobilNo, "S", " ", instituteID, inject, session, dbSession);
                            tc.commit(session, dbSession);
                         }else{

                             tc.rollBack(session, dbSession);
                             batchUtil.updateStudentNotificationStatusTable(studentID, notificationID, l_businessDate, l_mobilNo, "F", "Limit exhausted", instituteID, inject, session, dbSession);
 
                             batchUtil.updateStudentNotificationEMAILAndSMSTable(instituteID, notificationID, studentID, l_businessDate, l_mobilNo, "Limit exhausted", "STUDENT_NOTIFICATION_SMS_ERROR", inject, session, dbSession);
                        
                              tc.commit(session, dbSession);
                         }
                          }
                      }
                         
                    }catch(Exception ex){
                        dbg(ex);
                        tc.rollBack(session, dbSession);
                        StringWriter sw = new StringWriter();
                        ex.printStackTrace(new PrintWriter(sw));
                        String exceptionAsString = sw.toString();
                        String l_replacedException=batchUtil.getReplacedException(exceptionAsString);
//                        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT",309,studentID,notificationID,l_businessDate,l_mobilNo,"F",l_replacedException.substring(0,50));
//                        dbts.createRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification"+i_db_properties.getProperty("FOLDER_DELIMITER")+notificationID, "BATCH", 308, instituteID,notificationID,studentID,l_businessDate,l_mobilNo,l_replacedException);
                        batchUtil.updateStudentNotificationStatusTable(studentID, notificationID, l_businessDate, l_mobilNo, "F", l_replacedException.substring(0,50), instituteID, inject, session, dbSession);
                        batchUtil.updateStudentNotificationEMAILAndSMSTable(instituteID, notificationID, studentID, l_businessDate, l_mobilNo,l_replacedException, "STUDENT_NOTIFICATION_SMS_ERROR", inject, session, dbSession); 
                        tc.commit(session, dbSession);
                    }

                }
            
            }
            
            
            if(mode.equals("B")||mode.equals("M")){
            
                for(int i=0;i<mailList.size();i++){

                    String emailId=mailList.get(i);
                    Email emailObj=getEmailObject(instituteID,message,emailId);


                    try{

                     if(dbts.getImplictRecordLock(fileName,tableName,pkey,this.session)==true){ 
              
                          if( lock.isSameSessionRecordLock(fileName,tableName,pkey, this.session)){    
                        
                        if(bsv.emailUsageValidation(instituteID, session, dbSession, inject)){
                        
                            emailService.sendEmail(emailObj, session);
                            bs.updateEmailUsage(instituteID, session, dbSession, inject);
//                            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT",309,studentID,notificationID,l_businessDate,emailId.replace("@", "AATT;"),"S"," ");
                            batchUtil.updateStudentNotificationStatusTable(studentID, notificationID, l_businessDate, emailId.replace("@", "AATT;"), "S", " ", instituteID, inject, session, dbSession);

                           tc.commit(session, dbSession);
                        }else{
                            
//                            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT",309,studentID,notificationID,l_businessDate,emailId.replace("@", "AATT;"),"F","Limit exhausted");
//                            dbts.createRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification"+i_db_properties.getProperty("FOLDER_DELIMITER")+notificationID, "BATCH", 307, instituteID,notificationID.replace("@", "AATT;"),studentID,l_businessDate,emailId,"Limit exhausted");
                           tc.rollBack(session, dbSession);
                            batchUtil.updateStudentNotificationStatusTable(studentID, notificationID, l_businessDate, emailId.replace("@", "AATT;"), "F", "Limit exhausted", instituteID, inject, session, dbSession);
                            batchUtil.updateStudentNotificationEMAILAndSMSTable(instituteID, notificationID, studentID, l_businessDate, emailId.replace("@", "AATT;"), "Limit exhausted", "STUDENT_NOTIFICATION_EMAIL_ERROR", inject, session, dbSession);

                          tc.commit(session, dbSession); 
                        }
                          }
                     }
                    
                    }catch(Exception ex){
                        dbg(ex);
                        tc.rollBack(session, dbSession);
                        StringWriter sw = new StringWriter();
                        ex.printStackTrace(new PrintWriter(sw));
                        String exceptionAsString = sw.toString();
                        String l_replacedException=batchUtil.getReplacedException(exceptionAsString);
//                        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT",309,studentID,notificationID,l_businessDate,emailId.replace("@", "AATT;"),"F",l_replacedException.substring(0,50));
//                        dbts.createRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification"+i_db_properties.getProperty("FOLDER_DELIMITER")+notificationID, "BATCH", 307, instituteID,notificationID,studentID,l_businessDate,emailId.replace("@", "AATT;"),l_replacedException);
                       batchUtil.updateStudentNotificationStatusTable(studentID, notificationID, l_businessDate, emailId.replace("@", "AATT;"), "F", l_replacedException.substring(0,50), instituteID, inject, session, dbSession);
                       batchUtil.updateStudentNotificationEMAILAndSMSTable(instituteID, notificationID, studentID, l_businessDate, emailId.replace("@", "AATT;"), l_replacedException, "STUDENT_NOTIFICATION_EMAIL_ERROR", inject, session, dbSession);

                        tc.commit(session, dbSession);
                    
                    }

                }
            
            }
            
         }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException(ex.toString());
//        }catch(BSProcessingException ex){
//            dbg(ex);
//            throw new BSProcessingException(ex.toString());    
//        }catch(BSValidationException ex){
//            throw ex;
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
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
}
