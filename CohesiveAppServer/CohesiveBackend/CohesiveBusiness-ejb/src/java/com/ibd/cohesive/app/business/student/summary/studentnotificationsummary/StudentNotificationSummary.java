/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentnotificationsummary;

import com.ibd.businessViews.IStudentNotificationSummary;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.ConvertedDate;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
@Remote(IStudentNotificationSummary.class)
@Stateless
public class StudentNotificationSummary implements IStudentNotificationSummary{
     AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentNotificationSummary(){
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
       dbg("inside StudentNotificationSummary--->processing");
       dbg("StudentNotificationSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 

       BusinessEJB<IStudentNotificationSummary>studentNotificationEJB=new BusinessEJB();
       studentNotificationEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentNotificationEJB);
       
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
           request=null;
            bs=inject.getBusinessService(session);
            if(l_session_created_now){
                bs.responselogging(jsonResponse, inject,session,dbSession);
                dbg("Response"+jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
                          clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes
                //if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                  /* BSProcessingException ex=new BSProcessingException("response contains special characters");
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
      StudentNotificationBO studentNotification=new StudentNotificationBO();
      RequestBody<StudentNotificationBO> reqBody = new RequestBody<StudentNotificationBO>(); 
           
      try{
      dbg("inside student notification buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      studentNotification.filter=new StudentNotificationFilter();
      BSValidation bsv=inject.getBsv(session);
      
      studentNotification.filter.setStudentID(l_filterObject.getString("studentID"));
      studentNotification.filter.setStudentName(l_filterObject.getString("studentName"));
      studentNotification.filter.setFromDate(l_filterObject.getString("fromDate"));
      studentNotification.filter.setToDate(l_filterObject.getString("toDate"));
      
//      if(l_filterObject.getString("notificationType").equals("Select option")){
//          
//          studentNotification.filter.setNotificationType("");
//      }else{
//      
//          studentNotification.filter.setNotificationType(l_filterObject.getString("notificationType"));
//      }
//      
        reqBody.set(studentNotification);
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
        RequestBody<StudentNotificationBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_notificationList=new ArrayList();
        StudentNotificationBO studentNotification=reqBody.get();
        String l_studentID=studentNotification.getFilter().getStudentID();
//        IMetaDataService mds=inject.getMetadataservice();
//        int recStatusColId=mds.getColumnMetaData("FEE", "IVW_NOTIFICATION_MASTER", "RECORD_STATUS", session).getI_ColumnID()-1;
//        int authStatusColId=mds.getColumnMetaData("FEE", "IVW_NOTIFICATION_MASTER", "AUTH_STATUS", session).getI_ColumnID()-1;
        try{
        

            
                Map<String,DBRecord>notificationMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT", "STUDENT_NOTIFICATION_STATUS", session, dbSession);
//                 Map<String,DBRecord>instituteFeeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","INSTITUTE","IVW_NOTIFICATION_MASTER", session, dbSession);
                
//                Map<String,List<DBRecord>>aauthorizedRecords=instituteFeeMap.values().stream().filter(rec->rec.getRecord().get(recStatusColId).trim().equals("O")&&rec.getRecord().get(authStatusColId).trim().equals("A")).collect(Collectors.groupingBy(rec->rec.getRecord().get(2).trim()));
                
                
                
                Iterator<DBRecord>valueIterator=notificationMap.values().iterator();

                while(valueIterator.hasNext()){
                   DBRecord leaveRec=valueIterator.next();
//                   String feeID=leaveRec.getRecord().get(1).trim();
//                 if(aauthorizedRecords.containsKey(feeID))  {
                   
                   l_notificationList.add(leaveRec);
                   
//                 }
                }
//                Iterator<DBRecord>valueIterator=notificationMap.values().iterator();
//
//                while(valueIterator.hasNext()){
//                   DBRecord notificationRec=valueIterator.next();
//                   l_notificationList.add(notificationRec);
//
//                }
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
           IBDProperties i_db_properties=session.getCohesiveproperties();
           BusinessService bs=inject.getBusinessService(session);
           RequestBody<StudentNotificationBO> reqBody = request.getReqBody();
           String l_instituteID=request.getReqHeader().getInstituteID();
           StudentNotificationBO studentNotification =reqBody.get();
           String l_studentID=studentNotification.getFilter().getStudentID();
//           String l_notificationType=studentNotification.getFilter().getNotificationType();
           String l_fromDate=studentNotification.getFilter().getFromDate();
           String l_toDate=studentNotification.getFilter().getToDate();
           String dateFormat=i_db_properties.getProperty("DATE_FORMAT");
           SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           ArrayList<DBRecord>filteredList=new ArrayList();
           
           if(l_fromDate.isEmpty()&&l_toDate.isEmpty()){
               
               l_toDate=bs.getCurrentDate();

               LocalDate date2 =  LocalDate.now().minusMonths(1);
               Date date1 = Date.from(date2.atStartOfDay(ZoneId.systemDefault()).toInstant());
               l_fromDate=formatter.format(date1);
           }
           
           
           for(int i=0;i<l_notificationList.size();i++){
               
               DBRecord l_notificationRecord=l_notificationList.get(i);
               String l_notificationDate=l_notificationRecord.getRecord().get(2).trim();
               Date notificationDate=formatter.parse(l_notificationDate);
               Date fromDate=formatter.parse(l_fromDate);
               Date toDate=formatter.parse(l_toDate);
               dbg("l_notificationDate"+l_notificationDate);
               dbg("fromDate"+fromDate);
               dbg("toDate"+toDate);
//               dbg("l_notificationType"+l_notificationType);
               
                if(notificationDate.compareTo(fromDate)>=0){
                    
                    dbg("notificationDate.compareTo(fromDate)>=0");
                    
                    if(notificationDate.compareTo(toDate)<=0){
                       
//                       if(!l_notificationType.isEmpty()){
//                           
//                           dbg("notificationDate.compareTo(toDate)<=0");
//                           String notificationID= l_notificationRecord.getRecord().get(1).trim();
//                           dbg("notificationID"+notificationID);
//                           String insNotificationType;
//                           if(notificationID.equals("Event")){
//                               
//                               insNotificationType="Event";
//                           }else{
//                           
//                               String[] l_pkey={notificationID};
//                               DBRecord notificationRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","INSTITUTE","IVW_NOTIFICATION_MASTER", l_pkey, session,dbSession);
//                                insNotificationType= notificationRecord.getRecord().get(2).trim();
//                               dbg("insNotificationType"+insNotificationType);  
//                           }
//                           
//                       
//                           if(insNotificationType.equals(l_notificationType)){
//
//                               filteredList.add(l_notificationRecord);
//                           }
//                       
//                       }else{
                           
                           filteredList.add(l_notificationRecord);
                           
//                       }
                    }
                    
                }
           }
           
           dbg("filteredList size"+filteredList.size());
           
           
           dbg("l_studentID"+l_studentID);
          Map<String, DBRecord>finalFilteredList=new HashMap();
           
           for(int i=0;i<filteredList.size();i++){
               
               
               ArrayList<String> l_studentNotificationList=filteredList.get(i).getRecord();
               String notificationID=l_studentNotificationList.get(1).trim();
               String date=l_studentNotificationList.get(2).trim();
//               String newStatus=l_studentNotificationList.get(4).trim();
               String key=notificationID+"~"+date;
               dbg("key"+key);
               
               if(finalFilteredList.containsKey(key)){
                   
                   String existingStatus=finalFilteredList.get(key).getRecord().get(4).trim();
                   
                   if(!existingStatus.equals("F")){
                       
                       finalFilteredList.put(key, filteredList.get(i));
                   }
               
               }else{
                   
                   finalFilteredList.put(key, filteredList.get(i));
               }
               
               
               
           }
           
           dbg("finalFilteredList size"+finalFilteredList.size());
           
 
           studentNotification.result=new StudentNotificationResult[finalFilteredList.size()];
//           for(int i=0;i<filteredList.size();i++){
           Iterator<DBRecord>notificationIterator=finalFilteredList.values().iterator();
           int i=0;
           
           while(notificationIterator.hasNext()){

               ArrayList<String> l_studentNotificationList=notificationIterator.next().getRecord();
               
               studentNotification.result[i]=new StudentNotificationResult();
               studentNotification.result[i].setStudentID(l_studentNotificationList.get(0).trim());
               studentNotification.result[i].setNotificationID(l_studentNotificationList.get(1).trim());
               studentNotification.result[i].setStatus(l_studentNotificationList.get(4).trim());
               studentNotification.result[i].setDate(l_studentNotificationList.get(2).trim());
               
               String notificationID= l_studentNotificationList.get(1).trim();
               dbg("notificationID"+notificationID);
               
               if(!notificationID.equals("Event")){
               
                   String[] l_pkey={notificationID};
                   DBRecord notificationRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","INSTITUTE","IVW_NOTIFICATION_MASTER", l_pkey, session,dbSession);
                   String insNotificationType= notificationRecord.getRecord().get(2).trim();
                   dbg("insNotificationType"+insNotificationType);  
                    studentNotification.result[i].setNotificationType(insNotificationType);
               }else{
                   
                   studentNotification.result[i].setNotificationType("Event");
               }
               
               
               
               i++;
          }
           
           if(studentNotification.result==null||studentNotification.result.length==0){
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
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        JsonObject filter;
        try{
        dbg("inside student notification buildJsonResFromBO");    
        RequestBody<StudentNotificationBO> reqBody = request.getReqBody();
        StudentNotificationBO studentNotification =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<studentNotification.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("studentID", studentNotification.result[i].getStudentID())
                                                      .add("notificationID",studentNotification.result[i].getNotificationID())
                                                      .add("notificationType", studentNotification.result[i].getNotificationType())
                                                      .add("date",studentNotification.result[i].getDate())
                                                      .add("status", studentNotification.result[i].getStatus()));
        }

           filter=Json.createObjectBuilder()  .add("studentID",studentNotification.filter.getStudentID())
//                                              .add("notificationType", studentNotification.filter.getNotificationType())
                                              .add("fromDate",  studentNotification.filter.getFromDate())
                                              .add("toDate", studentNotification.filter.getToDate())
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
        RequestBody<StudentNotificationBO> reqBody = request.getReqBody();
        StudentNotificationBO studentNotification =reqBody.get();
        boolean fromDateEmpty=false;
        boolean toDateEmpty=false;
        
        BusinessService bs=inject.getBusinessService(session);
        String studentID=studentNotification.getFilter().getStudentID();
        String studentName=studentNotification.getFilter().getStudentName();
        String instituteID=request.getReqHeader().getInstituteID();
        studentID=bs.studentValidation(studentID, studentName, instituteID, session, dbSession, inject);

        studentNotification.getFilter().setStudentID(studentID);
        
        
         if(studentNotification.getFilter().getStudentID()==null||studentNotification.getFilter().getStudentID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","studentID");  
         }

          if(studentNotification.getFilter().getFromDate()==null||studentNotification.getFilter().getFromDate().isEmpty()){
             fromDateEmpty=true;
         }
          
          if(studentNotification.getFilter().getToDate()==null||studentNotification.getFilter().getToDate().isEmpty()){
             toDateEmpty=true;
         }
          
          if(fromDateEmpty==true){
              
             status=false;  
             errhandler.log_app_error("BS_VAL_002","fromDate");
          }
          
          if(toDateEmpty==true){
              
             status=false;  
             errhandler.log_app_error("BS_VAL_002","toDate");
          }
  
          
        dbg("end of student notification master mandatory validation");
         }catch(BSValidationException ex){
           throw ex;
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
             BusinessService bs=inject.getBusinessService(session);
             RequestBody<StudentNotificationBO> reqBody = request.getReqBody();
             StudentNotificationBO studentNotification =reqBody.get();

             String l_fromDate=studentNotification.getFilter().getFromDate();
             String l_toDate=studentNotification.getFilter().getToDate();
             
             
             if(l_fromDate!=null&&!l_fromDate.isEmpty()&&l_toDate!=null&&!l_toDate.isEmpty()){
             
                 if(!bsv.dateFormatValidation(l_fromDate, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","from Date");
                     throw new BSValidationException("BSValidationException");
                 }
                 
                 if(!bsv.dateFormatValidation(l_toDate, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","to Date");
                     throw new BSValidationException("BSValidationException");
                 }
                 
                 
                 
                 
                 if(!bsv.futureDateValidation(l_fromDate, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_048",null);
                     throw new BSValidationException("BSValidationException");
                 }
                 
                 if(!bsv.futureDateValidation(l_toDate, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_048",null);
                     throw new BSValidationException("BSValidationException");
                 }
             
             ConvertedDate fromDate=bs.getYearMonthandDay(l_fromDate);
             Calendar start = Calendar.getInstance();

             start.set(Calendar.YEAR, Integer.parseInt(fromDate.getYear()));
             start.set(Calendar.MONTH,  Integer.parseInt(fromDate.getMonth()));
             start.set(Calendar.DAY_OF_MONTH,  Integer.parseInt(fromDate.getDay()));

                
             ConvertedDate toDate=bs.getYearMonthandDay(l_toDate);
             Calendar end = Calendar.getInstance();

             end.set(Calendar.YEAR, Integer.parseInt(toDate.getYear()));
             end.set(Calendar.MONTH,  Integer.parseInt(toDate.getMonth()));
             end.set(Calendar.DAY_OF_MONTH,  Integer.parseInt(toDate.getDay()));
                
                if(start.after(end)){
                    
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","Date range");
                    throw new BSValidationException("BSValidationException");
                }


               int dateSize= bs.getLeaveDates(l_fromDate, l_toDate, session, dbSession, inject).size();
               
            
               if(dateSize>7){
                    
                    status=false;
                    errhandler.log_app_error("BS_VAL_049","Date range");
                    throw new BSValidationException("BSValidationException");
                }
               
             }
            dbg("end of student notification detailDataValidation");
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
