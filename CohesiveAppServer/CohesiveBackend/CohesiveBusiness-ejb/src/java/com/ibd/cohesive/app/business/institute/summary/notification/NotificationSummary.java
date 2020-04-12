/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.notification;

import com.ibd.businessViews.INotificationSummary;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.exception.ExceptionHandler;
import com.ibd.cohesive.app.business.util.message.request.Parsing;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
//@Local(INotificationSummary.class)
@Remote(INotificationSummary.class)
@Stateless
public class NotificationSummary implements INotificationSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public NotificationSummary(){
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
       try{
       session.createSessionObject();
       dbSession.createDBsession(session);
       l_session_created_now=session.isI_session_created_now();
       ErrorHandler errhandler = session.getErrorhandler();
       BSValidation bsv=inject.getBsv(session);
       bs=inject.getBusinessService(session);
       ITransactionControlService tc=inject.getTransactionControlService();
       dbg("inside NotificationSummary--->processing");
       dbg("NotificationSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<INotificationSummary>notificationEJB=new BusinessEJB();
       notificationEJB.set(this);
      
       exAudit=bs.getExistingAudit(notificationEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,notificationEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,notificationEJB);
              tc.commit(session,dbSession);
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
           request=null;
            bs=inject.getBusinessService(session);
            if(l_session_created_now){
                bs.responselogging(jsonResponse, inject,session,dbSession);
                dbg("Response"+jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
               // if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
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
      NotificationBO notification=new NotificationBO();
      RequestBody<NotificationBO> reqBody = new RequestBody<NotificationBO>(); 
           
      try{
      dbg("inside student notification buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      notification.filter=new NotificationFilter();
//      notification.filter.setInstituteID(l_filterObject.getString("instituteID"));
//      notification.filter.setNotificationID(l_filterObject.getString("notificationID"));
//      notification.filter.setNotificationType(l_filterObject.getString("notificationType"));
//      notification.filter.setNotificationFrequency(l_filterObject.getString("notificationFrequency"));
//      notification.filter.setMediaCommunication(l_filterObject.getString("mediaCommunication"));
//      notification.filter.setAssignee(l_filterObject.getString("assignee"));
//      notification.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      notification.filter.setAuthStatus(l_filterObject.getString("authStat"));

      
//      notification.filter.setInstituteID(l_filterObject.getString("instituteID"));
//      notification.filter.setNotificationID(l_filterObject.getString("notificationID"));
//      notification.filter.setNotificationFrequency(l_filterObject.getString("notificationFrequency"));
//      notification.filter.setAssignee(l_filterObject.getString("assignee"));
      
      notification.filter.setCreationDate(l_filterObject.getString("instant"));
      if(l_filterObject.getString("notificationType").equals("Select option")){
          
          notification.filter.setNotificationType("");
      }else{
      
          notification.filter.setNotificationType(l_filterObject.getString("notificationType"));
      }
      
      if(l_filterObject.getString("mediaCommunication").equals("Select option")){
          
          notification.filter.setMediaCommunication("");
      }else{
      
          notification.filter.setMediaCommunication(l_filterObject.getString("mediaCommunication"));
      
      }
      if(l_filterObject.getString("authStat").equals("Select option")){
          
          notification.filter.setAuthStatus("");
      }else{
      
          notification.filter.setAuthStatus(l_filterObject.getString("authStat"));
      }
      
//      if(l_filterObject.getString("recordStat").equals("Select option")){
//          
//          notification.filter.setRecordStatus("");
//      }else{
//      
//          notification.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      
//      }
      
      
      
        reqBody.set(notification);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student notification--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_notificationList=new ArrayList();
        String l_instituteID=request.getReqHeader().getInstituteID();
        
        try{
        
            ArrayList<String>l_fileNames=bs.getInstituteFileNames(l_instituteID,request,session,dbSession,inject);
            for(int i=0;i<l_fileNames.size();i++){
                dbg("inside file name iteration");
                String l_fileName=l_fileNames.get(i);
                dbg("l_fileName"+l_fileName);
                Map<String,DBRecord>notificationMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","INSTITUTE","IVW_NOTIFICATION_MASTER", session, dbSession);

                Iterator<DBRecord>valueIterator=notificationMap.values().iterator();

                while(valueIterator.hasNext()){
                   DBRecord notificationRec=valueIterator.next();
                   l_notificationList.add(notificationRec);

                }

                dbg("file name itertion completed for "+l_fileName);
            }
        }catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_000")||ex.toString().contains("DB_VAL_011")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().log_app_error("BS_VAL_013", null);
                throw new BSValidationException("BSValidationException");
                
            }else{
                throw ex;
            }
            
        }

        buildBOfromDB(l_notificationList);     
        
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
    

    
    private void buildBOfromDB(ArrayList<DBRecord>l_notificationList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<NotificationBO> reqBody = request.getReqBody();
           NotificationBO notification =reqBody.get();
           IMetaDataService mds=inject.getMetadataservice();
           BusinessService bs=inject.getBusinessService(session);
//           String l_recordStatus=notification.getFilter().getRecordStatus();
           String l_authStatus=notification.getFilter().getAuthStatus();
//           String l_instituteID=notification.getFilter().getInstituteID();
//           String l_notificationID=notification.getFilter().getNotificationID();
           String l_notificationType=notification.getFilter().getNotificationType();
//           String l_notificationFrequency=notification.getFilter().getNotificationFrequency();
           String l_mediaCommunication=notification.getFilter().getMediaCommunication();
//           String l_assignee=notification.getFilter().getAssignee();
           String creationDate=notification.getFilter().getCreationDate();
           int makerDtStampID=mds.getColumnMetaData("INSTITUTE", "IVW_NOTIFICATION_MASTER", "MAKER_DATE_STAMP", session).getI_ColumnID();
           

           
           dbg("l_authStatus"+l_authStatus);
           dbg("l_notificationType"+l_notificationType);
           dbg("l_mediaCommunication"+l_mediaCommunication);
           dbg("creationDate"+creationDate);
           
           
//           if(l_notificationID!=null&&!l_notificationID.isEmpty()){
//            
//             List<DBRecord>  l_studentList=  l_notificationList.stream().filter(rec->rec.getRecord().get(1).trim().equals(l_notificationID)).collect(Collectors.toList());
//             l_notificationList = new ArrayList<DBRecord>(l_studentList);
//             dbg("l_notificationID filter l_notificationList size"+l_notificationList.size());
//           }
//           
//           if(l_instituteID!=null&&!l_instituteID.isEmpty()){
//            
//             List<DBRecord>  l_studentList=  l_notificationList.stream().filter(rec->rec.getRecord().get(0).trim().equals(l_instituteID)).collect(Collectors.toList());
//             l_notificationList = new ArrayList<DBRecord>(l_studentList);
//             dbg("l_instituteID filter l_notificationList size"+l_notificationList.size());
//           }
           
           if(l_notificationType!=null&&!l_notificationType.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_notificationList.stream().filter(rec->rec.getRecord().get(2).trim().equals(l_notificationType)).collect(Collectors.toList());
             l_notificationList = new ArrayList<DBRecord>(l_studentList);
             dbg("l_notificationType filter l_notificationList size"+l_notificationList.size());
           }
           
//           if(l_notificationFrequency!=null&&!l_notificationFrequency.isEmpty()){
//            
//             List<DBRecord>  l_studentList=  l_notificationList.stream().filter(rec->rec.getRecord().get(3).trim().equals(l_notificationFrequency)).collect(Collectors.toList());
//             l_notificationList = new ArrayList<DBRecord>(l_studentList);
//             dbg("l_notificationFrequency filter l_notificationList size"+l_notificationList.size());
//           }
//           
           if(l_mediaCommunication!=null&&!l_mediaCommunication.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_notificationList.stream().filter(rec->rec.getRecord().get(8).trim().equals(l_mediaCommunication)).collect(Collectors.toList());
             l_notificationList = new ArrayList<DBRecord>(l_studentList);
             dbg("l_mediaCommunication filter l_notificationList size"+l_notificationList.size());
           }
           
//           if(l_assignee!=null&&!l_assignee.isEmpty()){
//            
//             List<DBRecord>  l_studentList=  l_notificationList.stream().filter(rec->rec.getRecord().get(9).trim().equals(l_assignee)).collect(Collectors.toList());
//             l_notificationList = new ArrayList<DBRecord>(l_studentList);
//             dbg("l_assignee filter l_notificationList size"+l_notificationList.size());
//           }
//           
//           if(l_recordStatus!=null&&!l_recordStatus.isEmpty()){
//               
//               List<DBRecord>  l_studentList=  l_notificationList.stream().filter(rec->rec.getRecord().get(5).trim().equals(l_recordStatus)).collect(Collectors.toList());
//               l_notificationList = new ArrayList<DBRecord>(l_studentList);
//               dbg("recordStatus filter l_notificationList size"+l_notificationList.size());
//           }
           
           if(l_authStatus!=null&&!l_authStatus.isEmpty()){
               
               List<DBRecord>  l_studentList=  l_notificationList.stream().filter(rec->rec.getRecord().get(15).trim().equals(l_authStatus)).collect(Collectors.toList());
               l_notificationList = new ArrayList<DBRecord>(l_studentList);
               dbg("authStatus filter l_notificationList size"+l_notificationList.size());
               
           }
           
           if(creationDate!=null&&!creationDate.isEmpty()){
               
               List<DBRecord>  l_studentList=  l_notificationList.stream().filter(rec->getDate(rec.getRecord().get(makerDtStampID-1).trim()).equals(creationDate)).collect(Collectors.toList());
               l_notificationList = new ArrayList<DBRecord>(l_studentList);
               dbg("authStatus filter l_notificationList size"+l_notificationList.size());
               
           }
           
           
           
           
           notification.result=new NotificationResult[l_notificationList.size()];
           for(int i=0;i<l_notificationList.size();i++){
               
               ArrayList<String> l_notificationRecords=l_notificationList.get(i).getRecord();
               notification.result[i]=new NotificationResult();
               notification.result[i].setInstituteID(l_notificationRecords.get(0).trim());
               notification.result[i].setNotificationID(l_notificationRecords.get(1).trim());
               notification.result[i].setNotificationType(l_notificationRecords.get(2).trim());
               notification.result[i].setNotificationFrequency(l_notificationRecords.get(3).trim());
               notification.result[i].setDate(l_notificationRecords.get(4).trim());
               notification.result[i].setDay(l_notificationRecords.get(5).trim());
               notification.result[i].setMonth(l_notificationRecords.get(6).trim());
               notification.result[i].setMessage(l_notificationRecords.get(7).trim());
               notification.result[i].setMediaCommunication(l_notificationRecords.get(8).trim());
               notification.result[i].setAssignee(l_notificationRecords.get(9).trim());
               notification.result[i].setMakerID(l_notificationRecords.get(10).trim());
               notification.result[i].setCheckerID(l_notificationRecords.get(11).trim());
               notification.result[i].setMakerDateStamp(l_notificationRecords.get(12).trim());
               notification.result[i].setCheckerDateStamp(l_notificationRecords.get(13).trim());
               notification.result[i].setRecordStatus(l_notificationRecords.get(14).trim());
               notification.result[i].setAuthStatus(l_notificationRecords.get(15).trim());
               notification.result[i].setVersionNumber(l_notificationRecords.get(16).trim());
               notification.result[i].setMakerRemarks(l_notificationRecords.get(17).trim());
               notification.result[i].setCheckerRemarks(l_notificationRecords.get(18).trim());
               
               String makerID=bs.getDateFromDateStamp(l_notificationRecords.get(12).trim());
               
               notification.result[i].setInstant(makerID);
          }    
           
           
           if(notification.result==null||notification.result.length==0){
               session.getErrorhandler().log_app_error("BS_VAL_013", null);
               throw new BSValidationException("BSValidationException");
           }
           
           
          dbg("end of  buildBOfromDB"); 
        }catch(BSValidationException ex){
          throw ex;   
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
 }
    
    
    private static String getDate(String dateStamp){
      
        try{ 
         
       String dateFormat="dd-MM-yyyy";
       Date date1=new SimpleDateFormat(dateFormat).parse(dateStamp);  
       String date=new SimpleDateFormat(dateFormat).format(date1);
       
          return date;
        }catch(ParseException ex){
           return null;        
        }
      
      
  }
    
    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        JsonObject filter;
        try{
        dbg("inside student notification buildJsonResFromBO");    
        RequestBody<NotificationBO> reqBody = request.getReqBody();
        NotificationBO notification =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<notification.result.length;i++){
            
            String mode=null;
            
            if(notification.result[i].getMediaCommunication().equals("M")){
                
                mode="Mail";
            }else if(notification.result[i].getMediaCommunication().equals("S")){
                
                mode="SMS";
            }else{
                mode="Both";
            }
            
            
            resultArray.add(Json.createObjectBuilder().add("instituteID", notification.result[i].getInstituteID())
                                                      .add("notificationID", notification.result[i].getNotificationID())
                                                      .add("notificationType",notification.result[i].getNotificationType())
                                                      .add("notificationFrequency",notification.result[i].getNotificationFrequency() )
                                                      .add("date",notification.result[i].getDate() )
                                                      .add("instant",notification.result[i].getInstant() )
                                                      .add("day",notification.result[i].getDay()) 
                                                      .add("month",notification.result[i].getMonth() )
                                                      .add("message",notification.result[i].getMessage())
                                                      .add("mediaCommunication",notification.result[i].getMediaCommunication() )
                                                      .add("mode",mode)
                                                      .add("assignee",notification.result[i].getAssignee() )
                                                      .add("makerID", notification.result[i].getMakerID())
                                                      .add("checkerID", notification.result[i].getCheckerID())
                                                      .add("makerDateStamp", notification.result[i].getMakerDateStamp())
                                                      .add("checkerDateStamp", notification.result[i].getCheckerDateStamp())
                                                      .add("recordStatus", notification.result[i].getRecordStatus())
                                                      .add("authStatus", notification.result[i].getAuthStatus())
                                                      .add("versionNumber", notification.result[i].getVersionNumber())
                                                      .add("makerRemarks", notification.result[i].getMakerRemarks())
                                                      .add("checkerRemarks", notification.result[i].getCheckerRemarks()));
        }

           filter=Json.createObjectBuilder()//  .add("instituteID", notification.getFilter().getInstituteID())
//                                              .add("notificationID", notification.getFilter().getNotificationID())
                                              .add("notificationType",notification.getFilter().getNotificationType())
//                                              .add("notificationFrequency",notification.getFilter().getNotificationFrequency() )
                                              .add("mediaCommunication",notification.getFilter().getMediaCommunication() )
//                                              .add("assignee",notification.getFilter().getAssignee() )
//                                              .add("recordStatus", notification.filter.getRecordStatus())
                                              .add("authStatus", notification.filter.getAuthStatus())
                                              .add("instant", notification.filter.getCreationDate())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
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
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of student notification--->businessValidation"); 
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
    private boolean filterMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
      boolean status=true;
        try{
        dbg("inside student notification master mandatory validation");
        RequestBody<NotificationBO> reqBody = request.getReqBody();
        NotificationBO notification =reqBody.get();
        int nullCount=0;
         
//         if(notification.getFilter().getInstituteID()==null||notification.getFilter().getInstituteID().isEmpty()){
//             nullCount++;
//         }
//        
//         if(notification.getFilter().getNotificationID()==null||notification.getFilter().getNotificationID().isEmpty()){
//             nullCount++;
//         }
         if(notification.getFilter().getNotificationType()==null||notification.getFilter().getNotificationType().isEmpty()){
             nullCount++;
         }
//         if(notification.getFilter().getNotificationFrequency()==null||notification.getFilter().getNotificationFrequency().isEmpty()){
//             nullCount++;
//         }
         if(notification.getFilter().getMediaCommunication()==null||notification.getFilter().getMediaCommunication().isEmpty()){
             nullCount++;
         }
//         if(notification.getFilter().getAssignee()==null||notification.getFilter().getAssignee().isEmpty()){
//             nullCount++;
//         }
//         if(notification.getFilter().getRecordStatus()==null||notification.getFilter().getRecordStatus().isEmpty()){
//             nullCount++;
//         }
          if(notification.getFilter().getAuthStatus()==null||notification.getFilter().getAuthStatus().isEmpty()){
             nullCount++;
         }
          if(notification.getFilter().getCreationDate()==null||notification.getFilter().getCreationDate().isEmpty()){
             nullCount++;
         }
         
          if(nullCount==4){
              status=false;
              errhandler.log_app_error("BS_VAL_002","One Filter value");
          }
          
        dbg("end of student notification master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student notification detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<NotificationBO> reqBody = request.getReqBody();
             NotificationBO notification =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_authStatus=notification.getFilter().getAuthStatus();
             String l_notificationType=notification.getFilter().getNotificationType();
             String creationDate=notification.getFilter().getCreationDate();
             
             
              if(l_notificationType!=null&&!l_notificationType.isEmpty()){
              
                    if(!bsv.notificationTypeValidation(l_notificationType,l_instituteID, session, dbSession,inject)){
                        status=false;
                        errhandler.log_app_error("BS_VAL_003","notificationType");
                    }
            
              }
             
             if(l_authStatus!=null&&!l_authStatus.isEmpty()){
                 
                if(!bsv.authStatusValidation(l_authStatus, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","authStatus");
                }
                 
             }
   
              if(creationDate!=null&&!creationDate.isEmpty()){
                 
                if(!bsv.dateFormatValidation(creationDate, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","authStatus");
                }
                 
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
     return null;
 }   
 public  void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
 }
 public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
 }
 public void authUpdate()throws DBValidationException,DBProcessingException,BSProcessingException{
 }
 public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
 }
 public void delete()throws DBValidationException,DBProcessingException,BSProcessingException{
 }   
 
 public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }  
}
