/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.util;

import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.debugger.Debug;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author DELL
 */
public class NotificationUtil {
    Debug debug;
    IBDProperties i_db_properties;

    public IBDProperties getI_db_properties() {
        return i_db_properties;
    }

    public void setI_db_properties(IBDProperties i_db_properties) {
        this.i_db_properties = i_db_properties;
    }
    
    public Debug getDebug() {
        return debug;
    }

    public void setDebug(Debug debug) {
        this.debug = debug;
    }
    
   
    public NotificationUtil(){
        
    }
    
    
     public EndPoint getEndPoints(String instituteID,String studentID,CohesiveSession session,DBSession dbSession,AppDependencyInjection inject)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
        
        try{
            
            dbg("inside getEndPoints");
            IPDataService pds=inject.getPdataservice();
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            
            String[] l_pkey={studentID};
            DBRecord profileRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_PROFILE", l_pkey, session, dbSession);
            String masterversion=profileRec.getRecord().get(19).trim();
            
            Map<String,DBRecord>l_contactPersonMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_FAMILY_DETAILS", session, dbSession);
            
            List<DBRecord>filteredList=l_contactPersonMap.values().stream().filter(rec->rec.getRecord().get(8).trim().equals(masterversion)).collect(Collectors.toList());
            
            
            Iterator<DBRecord>valueIterator=filteredList.iterator();
            String email=null;
            String mobileNo=null;
            
            while(valueIterator.hasNext()){
                
                DBRecord value=valueIterator.next();
                
                String smsRequired=value.getRecord().get(9).trim();
                
                if(smsRequired.equals("Y")){
                    
                     email=value.getRecord().get(5).trim();
                     mobileNo=value.getRecord().get(6).trim();
                }
                
                
            }
            
            
            String[] pkey={instituteID};
            ArrayList<String>contractList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",pkey);
            
            String mode=contractList.get(11).trim();
            
            
            
            String language=profileRec.getRecord().get(23).trim();
            
            
            EndPoint endPoint=new EndPoint();
            
            endPoint.setEmailID(email);
            endPoint.setMobileNo(mobileNo);
            endPoint.setLanguage(language);
            endPoint.setMode(mode);
            dbg("inside getEndPoints-->email"+email);
            dbg("inside getEndPoints-->mobileNo"+mobileNo);
            dbg("inside getEndPoints-->language"+language);
            dbg("inside getEndPoints-->mode"+mode);
            
             dbg("end of getEndPoints");
            return endPoint;
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
    
    
    
    public void messageGeneration(String instituteID,String studentID,MessageInput messageInput,CohesiveSession session,DBSession dbSession,AppDependencyInjection inject)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
        
        try{
            
            dbg("inside messageGeneration");
            
            EndPoint endPoint=this.getEndPoints(instituteID, studentID, session, dbSession, inject);
            String language=endPoint.getLanguage();
            String mode=endPoint.getMode();
            dbg("language"+language);
            dbg("mode"+mode);
            
            if(mode.equals("B")){
                
                String smsMessage=this.smsMessageGeneration(messageInput, language, instituteID, session, dbSession, inject);
                dbg("inside message messageGeneration--->smsMessage--->"+smsMessage);
                this.updateRecordInTodayNotification(instituteID, studentID, endPoint.getMobileNo(), "S", smsMessage, session, dbSession, inject,messageInput.getTitle());
                
                String emailMessage=this.emailMessageGeneration(messageInput, language, instituteID, session, dbSession, inject);
                dbg("inside message messageGeneration--->emailMessage--->"+emailMessage);
                this.updateRecordInTodayNotification(instituteID, studentID, endPoint.getEmailID(), "M", emailMessage, session, dbSession, inject,messageInput.getTitle());
                
            }else if(mode.equals("M")){
                
                String emailMessage=this.emailMessageGeneration(messageInput, language, instituteID, session, dbSession, inject);
                
                dbg("inside message messageGeneration--->emailMessage--->"+emailMessage);
                
                
                this.updateRecordInTodayNotification(instituteID, studentID, endPoint.getEmailID(), "M", emailMessage, session, dbSession, inject,messageInput.getTitle());
                
                
            }else{
                
                String smsMessage=this.smsMessageGeneration(messageInput, language, instituteID, session, dbSession, inject);
                
                dbg("inside message messageGeneration--->smsMessage--->"+smsMessage);
                
                
                this.updateRecordInTodayNotification(instituteID, studentID, endPoint.getMobileNo(), "S", smsMessage, session, dbSession, inject,messageInput.getTitle());
 
            }
            
            
            
            
           
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
    
    
     public String smsMessageGeneration(MessageInput messageInput,String language,String instituteID,CohesiveSession session,DBSession dbSession,AppDependencyInjection inject)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
        
        try{
            
            dbg("inside messageGeneration");
            IPDataService pds=inject.getPdataservice();
            String message=new String();
            
            if(language.equals("EN")){
                
                
//                message=messageInput.getTitle().concat(":");
                
                
                
                
                for(int i=0;i<messageInput.attributes.length;i++){
                    
                    String messageTag;
                    String attributeName=messageInput.attributes[i].getAttributeName();
                    
                    
                    if(messageInput.attributes[i].isID()){
                        
                        messageTag=this.getTagDescription(attributeName, messageInput.getMessageType(), instituteID, "EN", session, dbSession, inject,messageInput.attributes[i].getAttributeValue());
                        
                    }else{
                        
                        messageTag=attributeName;
                    }
                    
                    
                     if(messageInput.attributes[i].getAttributeValue()==null||messageInput.attributes[i].getAttributeValue().isEmpty()){
                    
                        if(i==messageInput.attributes.length-1){

                            message=  message.concat(messageTag);

                        }else{

                            message=  message.concat(messageTag).concat(" ");
                        }
                        
                     }  else {
                    
                        if(i==messageInput.attributes.length-1){

                            message=  message.concat(messageTag.concat("-").concat(messageInput.attributes[i].getAttributeValue()));

                        }else{

                            message=  message.concat(messageTag.concat("-").concat(messageInput.attributes[i].getAttributeValue())).concat(" ");
                        }
                        
                     }
                        
                        
                }
                
                message=message.concat(";");
            }else{
                ArrayList<String>translationList;
                if(messageInput.titleAlreadyTranslated==false){
                
                String[] pkey={messageInput.getTitle(),language};
                translationList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "SMS_TERMS_TRANSALATOR",pkey);
                String otherLangTitle=translationList.get(2).trim();
                messageInput.setTitle(otherLangTitle);
                messageInput.setTitleAlreadyTranslated(true);
                }
                
                
//                message=otherLangTitle.concat(":");
                
                
                for(int i=0;i<messageInput.attributes.length;i++){
                    
                    
                    String messageTag;
                    String attributeName=messageInput.attributes[i].getAttributeName();
                    
                    if(messageInput.attributes[i].isID()){
                        
                        messageTag=this.getTagDescription(attributeName, messageInput.getMessageType(), instituteID, language, session, dbSession, inject,messageInput.attributes[i].getAttributeValue());
                        
                    }else{
                        
                        if(messageInput.getTitle().equals("Video")||messageInput.getTitle().equals("வீடியோ")){
                            
                            messageTag=attributeName;
                            
                        }else{
                        
                        
                         String[] l_pkey={attributeName,language};
                         translationList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "SMS_TERMS_TRANSALATOR",l_pkey);
                         String otherLangAttName=translationList.get(2).trim();
                         messageTag=otherLangAttName;
                        
                        }
                    }
                    
                    
                    
                    if(messageInput.attributes[i].getAttributeValue()==null||messageInput.attributes[i].getAttributeValue().isEmpty()){
                        
                        
                        if(i==messageInput.attributes.length-1){
                        
                           message=  message.concat(messageTag);
                         
                        }else{
                            
                            message=  message.concat(messageTag).concat(" ");
                        }
                         
                         
                    }else{
                    
                        if(i==messageInput.attributes.length-1){
                        
                        
                              message=  message.concat(messageTag.concat("-").concat(messageInput.attributes[i].getAttributeValue()));
                    
                        }else{
                            
                            message=  message.concat(messageTag.concat("-").concat(messageInput.attributes[i].getAttributeValue())).concat(" ");
                            
                        }
                        
                        
                        
                    }
                    
                    
                }
                
                message=message.concat(";");
                
            }
            
            
            
            
            
            
            
            return message;
           
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
    
     
     public String emailMessageGeneration(MessageInput messageInput,String language,String instituteID,CohesiveSession session,DBSession dbSession,AppDependencyInjection inject)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
        
        try{
            
            dbg("inside messageGeneration");
            IPDataService pds=inject.getPdataservice();
            String message=new String();
            
            if(language.equals("EN")){
                
                
//                message="<h5> <u>"+messageInput.getTitle()+" </u></h5>";
                
                
                
                
                for(int i=0;i<messageInput.attributes.length;i++){
                    
                    String messageTag;
                    String attributeName=messageInput.attributes[i].getAttributeName();
                    
                    
                    if(messageInput.attributes[i].isID()){
                        
                        messageTag=this.getTagDescription(attributeName, messageInput.getMessageType(), instituteID, "EN", session, dbSession, inject,messageInput.attributes[i].getAttributeValue());
                        
                    }else{
                        
                        messageTag=attributeName;
                    }
                    
                    
                     if(messageInput.attributes[i].getAttributeValue()==null||messageInput.attributes[i].getAttributeValue().isEmpty()){
                    

                            message=  message.concat("<p>"+messageTag+"</p>");

                        
                     }  else {
                    

                            message=  message.concat("<p>"+messageTag.concat("   -   ").concat(messageInput.attributes[i].getAttributeValue())+"</p>");

                        
                        
                     }
                        
                        
                }
                
            }else{
                
                
                
//                String[] pkey={messageInput.getTitle(),language};
//                ArrayList<String>translationList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "SMS_TERMS_TRANSALATOR",pkey);
//                String otherLangTitle=translationList.get(2).trim();
////                message="<h5> <u>"+otherLangTitle+"</u> </h5>";
//                messageInput.setTitle(otherLangTitle);
//                messageInput.setTitleAlreadyTranslated(true);
                ArrayList<String>translationList=null;
                
                if(messageInput.titleAlreadyTranslated==false){
                
                    String[] pkey={messageInput.getTitle(),language};
                    translationList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "SMS_TERMS_TRANSALATOR",pkey);
                    String otherLangTitle=translationList.get(2).trim();
                    messageInput.setTitle(otherLangTitle);
                    messageInput.setTitleAlreadyTranslated(true);
                }
                
                
                for(int i=0;i<messageInput.attributes.length;i++){
                    
                    
                    String messageTag;
                    String attributeName=messageInput.attributes[i].getAttributeName();
                    
                    if(messageInput.attributes[i].isID()){
                        
                        messageTag=this.getTagDescription(attributeName, messageInput.getMessageType(), instituteID, language, session, dbSession, inject,messageInput.attributes[i].getAttributeValue());
                        
                    }else{
                        
                        if(messageInput.getTitle().equals("Video")||messageInput.getTitle().equals("வீடியோ")){
                            
                            messageTag=attributeName;
                            
                        }else{
                        
                         String[] l_pkey={attributeName,language};
                         translationList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "SMS_TERMS_TRANSALATOR",l_pkey);
                         String otherLangAttName=translationList.get(2).trim();
                          messageTag=otherLangAttName;
                        }
                        
                    }
                    
                    
                    
                    if(messageInput.attributes[i].getAttributeValue()==null||messageInput.attributes[i].getAttributeValue().isEmpty()){
                        
                        
                           message=  message.concat("<p>"+messageTag+"</p>");
                         
                         
                    }else{
                    
                        
                         message=  message.concat("<p>"+messageTag.concat("   -   ").concat(messageInput.attributes[i].getAttributeValue())+"</p>");
                    
                    }
                    
                    
                }
                
                
            }
            
            
            
            
            
            
            
            return message;
           
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
     
     
     
     
     
     
     
    
     private String getTagDescription(String tagID,String messageType,String instituteID,String language,CohesiveSession session,DBSession dbSession,AppDependencyInjection inject,String tagValue)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
         
         
         try{
             dbg("inside getTagDescription");
             dbg("inside getTagDescription-->messageType-->"+messageType);
             
             String[] l_pkey={instituteID,tagID};
             IPDataService pds=inject.getPdataservice();
             String tagDescription=null;
             
             switch(messageType){
                 
                 
                 case "ExamResult":
                     
                     if(tagValue==null||tagValue.isEmpty()){
                         
                         
                          ArrayList<String>examList= pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID, "INSTITUTE", "IVW_INSTITUTE_EXAM_MASTER",l_pkey);
                     
                            if(language.equals("EN")){
                         
                              tagDescription=  examList.get(2).trim();
                         
                            }else{

                                tagDescription=  examList.get(4).trim();

                            }
                     
                     }else{
                     
                     
                         ArrayList<String>subjectList=    pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID, "INSTITUTE", "IVW_SUBJECT_MASTER",l_pkey);


                         if(language.equals("EN")){

                             tagDescription=  subjectList.get(2).trim();

                         }else{

                             tagDescription=  subjectList.get(4).trim();

                         }
                     
                     
                     }
                     break;
                 
                 case "Fees":
                     
                     
                     ArrayList<String>feeList=    pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID, "INSTITUTE", "IVW_FEE_TYPE_MASTER",l_pkey);
                     
                     if(language.equals("EN")){
                         
                         tagDescription=  feeList.get(2).trim();
                         
                     }else{
                         
                         tagDescription=  feeList.get(4).trim();
                         
                     }
                     break;
                 
                 case "Note":
                     
                     
                     ArrayList<String>notificationList=    pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID, "INSTITUTE", "IVW_NOTIFICATION_TYPE_MASTER",l_pkey);
                     
                     if(language.equals("EN")){
                         
                         tagDescription=  notificationList.get(2).trim();
                         
                     }else{
                         
                         tagDescription=  notificationList.get(4).trim();
                         
                     }
                     break;
                 case "Video":
                     
  
                         
                         tagDescription= tagID;
                         
                     break;
             }
             
             
             
             
                    
             
             
             return tagDescription;
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
    
    
     public MessageInput getExamMessageInput(String studentID,List<DBRecord>value,String exam)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
         
         try{
             
            dbg("inside getExamMessageInput");
            dbg("inside getExamMessageInput-->exam-->"+exam);
            dbg("inside getExamMessageInput-->studentID-->"+studentID);
            dbg("inside getExamMessageInput-->value-->"+value.size());
            
            String messageTitle="Exam Result";
            String messageType="ExamResult";
            MessageInput messageInput=new MessageInput();
            messageInput.setTitle(messageTitle);
            messageInput.setMessageType(messageType);
            messageInput.attributes=new MessageAttributes[value.size()+1];
            
            messageInput.attributes[0]=new MessageAttributes();
            messageInput.attributes[0].setAttributeName(exam);
            messageInput.attributes[0].setID(true);
            
//            for(int i=1;i<value.size();i++){
//                
//                String subjectID=value.get(i).getRecord().get(3).trim();
//                String mark=value.get(i).getRecord().get(4).trim();
//                
//                messageInput.attributes[i]=new MessageAttributes();
//                messageInput.attributes[i].setAttributeName(subjectID);
//                messageInput.attributes[i].setAttributeValue(mark);
//                messageInput.attributes[i].setID(true);
//            }
            
             for(int i=0;i<value.size();i++){
                
                String subjectID=value.get(i).getRecord().get(3).trim();
//                String mark=value.get(i).getRecord().get(4).trim();
                String mark=value.get(i).getRecord().get(6).trim();
                messageInput.attributes[i+1]=new MessageAttributes();
                messageInput.attributes[i+1].setAttributeName(subjectID);
                messageInput.attributes[i+1].setAttributeValue(mark);
                messageInput.attributes[i+1].setID(true);
            }
             
             dbg("end of getExamMessageInput");
             return messageInput;
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(Exception ex){
            dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
     }
     
     
     
     public MessageInput getNotificationMessageInput(String studentID,String message,String notificationType)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
         
         try{
             
             
            String messageTitle="Note";
            String messageType="Note";
            MessageInput messageInput=new MessageInput();
            messageInput.setTitle(messageTitle);
            messageInput.setMessageType(messageType);
            messageInput.attributes=new MessageAttributes[1];
            
            messageInput.attributes[0]=new MessageAttributes();
            messageInput.attributes[0].setAttributeName(notificationType);
            messageInput.attributes[0].setAttributeValue(message);
            messageInput.attributes[0].setID(true);
             
             return messageInput;
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(Exception ex){
            dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
     }
    
     
      
     public MessageInput getAttendanceMessageInput(String studentID)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
         
         try{
             
             
            String messageTitle="Attendance";
            String messageType="Attendance";
            MessageInput messageInput=new MessageInput();
            messageInput.setTitle(messageTitle);
            messageInput.setMessageType(messageType);
            messageInput.attributes=new MessageAttributes[1];
            
            messageInput.attributes[0]=new MessageAttributes();    
            messageInput.attributes[0].setAttributeName("Absent");
            
             
             return messageInput;
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(Exception ex){
            dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
     }
      
        public void updateRecordInTodayNotification(String instituteID,String studentID,String endPoint,String notificationType,String message,CohesiveSession session,DBSession dbSession,AppDependencyInjection inject,String title)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
        
        try{
            
            dbg("inside updateRecordInTodayNotification");
            
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IDBTransactionService dbts=inject.getDBTransactionService();
            BusinessService bs=inject.getBusinessService(session);
            String currentDate=bs.getCurrentDate();
            ByteBuffer titleByteBuffer=ByteBuffer.wrap(title.getBytes(Charset.forName("UTF-8")));
            String newTitle=null;                

             if(titleByteBuffer.limit()>50){

                    byte[] dst=new byte[50];
                     for (int i=0;i<=49;i++)
                     {    
                         dst[i] = titleByteBuffer.get();

                     }

                     newTitle = new String(dst, Charset.forName("UTF-8"));

             }else{

                 newTitle=title;
             }
            String[] pkey={studentID,endPoint,newTitle};
            boolean recordExistence=false;
            String previousMessage=null;

             try{

                     
                 DBRecord notificationRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"INSTITUTE", "TODAY_NOTIFICATION", pkey, session, dbSession);

                 previousMessage=notificationRecord.getRecord().get(3).trim();
                 dbg("inside updateRecordInTodayNotification-->previousMessage-->"+previousMessage);
                 recordExistence=true;
             }catch(DBValidationException ex){

                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){

                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        recordExistence=false;
                    }else{

                        throw ex;
                    }


                }  

              dbg("inside updateRecordInTodayNotification-->recordExistence-->"+recordExistence);

               
              
              
              
             if(recordExistence){

                 Map<String,String>l_columnToUpdate=new HashMap();
                 
                 String newMessage=previousMessage.replace(";", ",").concat(message);;
                  ByteBuffer copy=ByteBuffer.wrap(newMessage.getBytes(Charset.forName("UTF-8")));
                 String correctLengthMsg=null;
                
                 if(copy.limit()>280){
                     
//                     correctLengthMsg=newMessage.substring(0,319);
                       byte[] dst=new byte[280];
                 for (int i=0;i<=279;i++)
                 {    
                     dst[i] = copy.get();

                 }

                 correctLengthMsg = new String(dst, Charset.forName("UTF-8"));
                 
                 
                 dbg("copy.limit()"+copy.limit());
                 }else{
                     correctLengthMsg=newMessage;
                 }
                 l_columnToUpdate.put("4", correctLengthMsg);
                 dbg("inside updateRecordInTodayNotification-->newMessage-->"+newMessage);

                
                 

                 dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"INSTITUTE", "TODAY_NOTIFICATION", pkey, l_columnToUpdate, session);

             }else{

               ByteBuffer copy=ByteBuffer.wrap(message.getBytes(Charset.forName("UTF-8")));
                 String correctLengthMsg=null;
                 dbg("copy.limit()"+copy.limit());
                 if(copy.limit()>280){
                     
//                     correctLengthMsg=newMessage.substring(0,319);
                       byte[] dst=new byte[280];
                 for (int i=0;i<=279;i++)
                 {    
                     dst[i] = copy.get();

                 }

                 correctLengthMsg = new String(dst, Charset.forName("UTF-8"));
                 
                 
                 dbg("copy.limit()"+copy.limit());
                 }else{
                     correctLengthMsg=message;
                 }
               

                 dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"INSTITUTE",332,studentID,endPoint,notificationType,correctLengthMsg,"U",newTitle);


             }
             dbg("end of updateRecordInTodayNotification");
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
    
        
        private String formInstantNotificatiomMessage(String instituteID,String language,String notificationType,String engMessage,String otherLangMessage,CohesiveSession session,DBSession dbSession,AppDependencyInjection inject)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
            
            try{
                dbg("inside formInstantNotificatiomMessage");
                String message=null;
                String tagDescription=this.getTagDescription(notificationType, "Note", instituteID, language, session, dbSession, inject, "");
                if(language.equals("EN")){
                    
                    
                  message=  tagDescription.concat(":").concat(engMessage);
                    
                    
                }else{
                    
                    
                  message=  tagDescription.concat(":").concat(otherLangMessage);
                }
                

                
                dbg("end of formInstantNotificatiomMessage-->message-->"+message);
                return message;
                
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
        
        
        
        public void updateInstantRecordInTodayNotification(String instituteID,String studentID,String notificationType,String mode,String message,String otherLanMessage,CohesiveSession session,DBSession dbSession,AppDependencyInjection inject)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
        
        try{
            
            dbg("inside updateInstantRecordInTodayNotification");
            dbg("instituteID-->"+instituteID);
            dbg("studentID-->"+studentID);
            dbg("notificationType-->"+notificationType);
            dbg("mode-->"+mode);
            dbg("mode-->"+mode);
            dbg("message-->"+message);
            dbg("otherLanMessage-->"+otherLanMessage);
            EndPoint endPoints=this.getEndPoints(instituteID, studentID, session, dbSession, inject);
            
            String language=endPoints.getLanguage();
            String newMessage=this.formInstantNotificatiomMessage(instituteID, language, notificationType, message, otherLanMessage, session, dbSession, inject);
            String mobileNo=endPoints.getMobileNo();
            String emailID=endPoints.getEmailID();
            String title=newMessage.split(":")[0];
            String messageToSend=newMessage.split(":")[1];
            
            if(mode.equals("B")){
                
                this.updateRecordInTodayNotification(instituteID, studentID, mobileNo, "S", messageToSend, session, dbSession, inject,title);
                
                this.updateRecordInTodayNotification(instituteID, studentID, emailID, "M", messageToSend, session, dbSession, inject,title);
            }else if(mode.equals("M")){
                
                this.updateRecordInTodayNotification(instituteID, studentID, emailID, "M", messageToSend, session, dbSession, inject,title);
                
            }else{
                
                this.updateRecordInTodayNotification(instituteID, studentID, mobileNo, "S", messageToSend, session, dbSession, inject,title);
                
            }
            
            
            
            
            
       dbg("inside updateInstantRecordInTodayNotification");
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
        
        
        
        
     
        
        
        
        
        
        
        
        
        public boolean checkAtteendanceMessageExistence(String instituteID,String studentID,CohesiveSession session,DBSession dbSession,AppDependencyInjection inject)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
        
        try{
            
            dbg("inside updateRecordInTodayNotification");
            
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IDBTransactionService dbts=inject.getDBTransactionService();
            BusinessService bs=inject.getBusinessService(session);
            String currentDate=bs.getCurrentDate();
            EndPoint endPoint=this.getEndPoints(instituteID, studentID, session, dbSession, inject);
            String[] pkey={studentID,endPoint.getMobileNo()};
            

             try{

                     
                DBRecord notificationRecord= readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"INSTITUTE", "TODAY_NOTIFICATION", pkey, session, dbSession);

                String smsMessage=notificationRecord.getRecord().get(3);
                 
                if(smsMessage.contains("Attendance")){
                 
                  return true;
                }
                 
                 
             }catch(DBValidationException ex){

                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){

                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    }else{

                        throw ex;
                    }


                }  

            String[] email_pkey={studentID,endPoint.getEmailID()};
             
              try{

                     
                DBRecord notificationRecord= readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"INSTITUTE", "TODAY_NOTIFICATION", email_pkey, session, dbSession);

                String emailMessage=notificationRecord.getRecord().get(3);
                 
                if(emailMessage.contains("Attendance")){
                 
                  return true;
                }
                 
                 
             }catch(DBValidationException ex){

                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){

                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        return false;
                    }else{

                        throw ex;
                    }


                }  
             

           return false;
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
        
        
        public MessageInput getFeeMessageInput(String instituteID,String studentID,String feeID,CohesiveSession session,DBSession dbSession,AppDependencyInjection inject)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
         
         try{
             
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            String messageTitle="School Fees";
            String messageType="Fees";
            MessageInput messageInput=new MessageInput();
            messageInput.setTitle(messageTitle);
            messageInput.setMessageType(messageType);
            messageInput.attributes=new MessageAttributes[2];
            String[] l_pkey={feeID};
            
            
            DBRecord feeManagementRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", l_pkey, session,dbSession);
                
            
                String feeType=feeManagementRecord.getRecord().get(4).trim();
                String amount=feeManagementRecord.getRecord().get(5).trim();
                String dueDate=feeManagementRecord.getRecord().get(6).trim();
                
                messageInput.attributes[0]=new MessageAttributes();
                messageInput.attributes[0].setAttributeName(feeType);
                messageInput.attributes[0].setAttributeValue("Rs."+amount);
                messageInput.attributes[0].setID(true);
                messageInput.attributes[1]=new MessageAttributes();
                messageInput.attributes[1].setAttributeName("Due Date");
                messageInput.attributes[1].setAttributeValue(dueDate);
            
             
             return messageInput;
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
        
        public MessageInput getAssignmentMessageInput(String instituteID,String studentID,String feeID,CohesiveSession session,DBSession dbSession,AppDependencyInjection inject)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
         
         try{
             
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            String messageTitle="Video";
            String messageType="Video";
            MessageInput messageInput=new MessageInput();
            messageInput.setTitle(messageTitle);
            messageInput.setMessageType(messageType);
            messageInput.attributes=new MessageAttributes[1];
            String[] l_pkey={feeID};
            
            
            DBRecord assignmentRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT","IVW_ASSIGNMENT", l_pkey, session,dbSession);
                
            
                String url=assignmentRecord.getRecord().get(17).trim();
                
                messageInput.attributes[0]=new MessageAttributes();
                messageInput.attributes[0].setAttributeName(url);
                
            
             
             return messageInput;
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
        
         
        public String getMessageHeader(String instituteID,String studentID,CohesiveSession session,DBSession dbSession,AppDependencyInjection inject)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
         
         try{
             
            BusinessService bs=inject.getBusinessService(session);
            String messageHeader;
            
//            String instituteName=bs.getInstituteName(instituteID, session, dbSession, inject);
                 
            String instituteShortName=bs.getInstituteShortName(instituteID, session, dbSession, inject);
            String studentName=bs.getStudentName(studentID, instituteID, session, dbSession, inject);
            
            messageHeader=instituteShortName+","+"Student: "+studentName.split(" ")[0]+",";
             
            
            
            
             return messageHeader;
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
        
         public String getMessageFooter()throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
         
         try{
             
           
             
             
             return "you can login https://cohesive.ibdtechnologies.com";
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(Exception ex){
            dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
     }
     
    public void dbg(String p_Value) {

        this.debug.dbg(p_Value);

    }

    public void dbg(Exception ex) {

        this.debug.exceptionDbg(ex);

    }
}
