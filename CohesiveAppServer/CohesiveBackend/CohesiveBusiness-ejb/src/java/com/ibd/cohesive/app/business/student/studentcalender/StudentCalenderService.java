/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentcalender;

import com.ibd.businessViews.IStudentCalenderService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.ConvertedDate;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.exception.ExceptionHandler;
import com.ibd.cohesive.app.business.util.message.request.Parsing;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
//@Local(IStudentCalenderService.class)
@Remote(IStudentCalenderService.class)
@Stateless
public class StudentCalenderService implements IStudentCalenderService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentCalenderService(){
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
       dbg("inside StudentCalenderService--->processing");
       dbg("StudentCalenderService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<StudentCalender> reqBody = request.getReqBody();
       StudentCalender studentCalender =reqBody.get();
       String l_studentID=studentCalender.getStudentID();
       String l_date=studentCalender.getDate(); 
       l_lockKey=l_studentID+"~"+l_date;
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<IStudentCalenderService>studentCalenderEJB=new BusinessEJB();
       studentCalenderEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentCalenderEJB);
       
       if(!operationValidation()){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
       }
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,studentCalenderEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentCalenderEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student calender");
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
                dbg(jsonResponse.toString());
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
      StudentCalender studentCalender=new StudentCalender();
      RequestBody<StudentCalender> reqBody = new RequestBody<StudentCalender>(); 
           
      try{
      dbg("inside student calender buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      studentCalender.setStudentID(l_body.getString("studentID"));
      studentCalender.setStudentName(l_body.getString("studentName"));
      studentCalender.setDate(l_body.getString("date"));
      
      
        reqBody.set(studentCalender);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
    
    return;
     

    }
    public void authUpdate()throws DBValidationException,DBProcessingException,BSProcessingException{
        
     return;
        
       }
    public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
       return;
    }

    
    public void delete()throws DBValidationException,DBProcessingException,BSProcessingException{
       
        return;
    }

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException{
                
        try{      
        dbg("inside student calender--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentCalender> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        StudentCalender studentCalender =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_studentID=studentCalender.getStudentID();
        String l_date=studentCalender.getDate();
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth();
        String[] l_pkey={l_studentID,l_year,l_month};
        
        DBRecord calenderRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_CALENDER", l_pkey, session, dbSession);
       
        buildBOfromDB(calenderRec);
        
          dbg("end of  completed student calender--->view");                
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
      private void buildBOfromDB(DBRecord p_studentCalenderRecord)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<StudentCalender> reqBody = request.getReqBody();
           StudentCalender studentCalender =reqBody.get();
           ArrayList<String>l_studentCalenderList= p_studentCalenderRecord.getRecord();
           String l_monthEvent=l_studentCalenderList.get(3).trim();
           dbg("buildBOfromDB--->l_monthEvent"+l_monthEvent);
           Map<String,Map<String,String>>eventMap=getEventMap(l_monthEvent);
           dbg("eventMap size"+eventMap.size());
           Iterator<String> eventIterator=eventMap.keySet().iterator();
           studentCalender.event=new Event[eventMap.size()];
           
           int i=0;
           while(eventIterator.hasNext()){
               String eventType_and_key=eventIterator.next();
               dbg("eventType_and_key"+eventType_and_key);
               String eventType=eventType_and_key.split("~")[0];
               String key=eventType_and_key.split("~")[1];
               
               studentCalender.event[i]=new Event();
               studentCalender.event[i].setEventType(eventType);
               studentCalender.event[i].setKey(key);
               
               Map<String,String>attributeMap=eventMap.get(eventType_and_key);
               Iterator<String> attributeIterator=attributeMap.keySet().iterator();
               studentCalender.event[i].eventAttributes=new EventAttributes[attributeMap.size()];
               
               int j=0;
               while(attributeIterator.hasNext()){
                   
                   String attributeName=attributeIterator.next();
                   String attributeValue=attributeMap.get(attributeName);
                   studentCalender.event[i].eventAttributes[j]=new EventAttributes();
                   studentCalender.event[i].eventAttributes[j].setAttrName(attributeName);
                   studentCalender.event[i].eventAttributes[j].setAttrValue(attributeValue);
                   j++;
               }
             i++;  
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
        dbg("inside student calender buildJsonResFromBO");    
        RequestBody<StudentCalender> reqBody = request.getReqBody();
        StudentCalender studentCalender =reqBody.get();
        JsonArrayBuilder attributeArray=Json.createArrayBuilder();
        JsonArrayBuilder eventArray=Json.createArrayBuilder();
        
        for(int i=0;i<studentCalender.event.length;i++){
            
            for(int j=0;j<studentCalender.event[i].eventAttributes.length;j++){
                
                attributeArray.add(Json.createObjectBuilder().add("attrName", studentCalender.event[i].eventAttributes[j].getAttrName())
                                                             .add("attrValue", studentCalender.event[i].eventAttributes[j].getAttrValue()));
                
           }
            
            
            eventArray.add(Json.createObjectBuilder().add("eventType", studentCalender.event[i].getEventType())
                                                     .add("key", studentCalender.event[i].getKey())
                                                     .add("eventAttributes", attributeArray));
            
        }
        
        
        body=Json.createObjectBuilder().add("studentID", studentCalender.getStudentID())
                                       .add("studentName", studentCalender.getStudentName())
                                       .add("date", studentCalender.getDate())
                                       .add("events", eventArray)
                                       .build();
                                            
              dbg(body.toString());  
           dbg("end of student calender buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student calender--->businessValidation");    
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
       dbg("end of student calender--->businessValidation"); 
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
        dbg("inside student calender master mandatory validation");
        RequestBody<StudentCalender> reqBody = request.getReqBody();
        StudentCalender studentCalender =reqBody.get();
        
         if(studentCalender.getStudentID()==null||studentCalender.getStudentID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","studentID");  
         }

          if(studentCalender.getDate()==null||studentCalender.getDate().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","Date");  
         }
          
        dbg("end of student calender master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student calender masterDataValidation");
             RequestBody<StudentCalender> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             StudentCalender studentCalender =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_studentID=studentCalender.getStudentID();
             String l_date=studentCalender.getDate(); 
             
             if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","studentID");
             }
             
             if(!bsv.dateFormatValidation(l_date, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Date");
             }
            
            dbg("end of student calender masterDataValidation");
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
        return true;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
        return true;
              
    }
    
    private boolean operationValidation()throws BSProcessingException{
        boolean status=true;
        try{
           
           dbg("inside studentCalender--->operation validation"); 
           String l_operation=request.getReqHeader().getOperation();
           dbg("l_operation"+l_operation);
           ErrorHandler errhandler= session.getErrorhandler();
           if(!(l_operation.equals("View"))){
               status=false;  
               errhandler.log_app_error("BS_VAL_000",null);  
           } 
            dbg("end of studentCalender--->operation validation--->status"+status);
            return status;
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        
        
        
    }
    
    private Map<String,Map<String,String>>getEventMap(String p_monthEvent)throws BSProcessingException{
        
      try{  
               dbg("inside getEventMap");
               dbg("p_monthEvent"+p_monthEvent);
               BusinessService bs=inject.getBusinessService(session);
               RequestBody<StudentCalender> reqBody = request.getReqBody();
               StudentCalender studentCalender =reqBody.get();
               String l_date=studentCalender.getDate();
               ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
               String day=convertedDate.getDay();
               dbg("day"+day);
               String dayEvent=p_monthEvent.split("d")[Integer.parseInt(day)];
               dbg("dayEvent"+dayEvent);
               String[] eventArray=dayEvent.split("//");
               dbg("eventArray size"+eventArray.length);
               Map<String,Map<String,String>>eventMap=new HashMap();
               
               for(int i=1;i<eventArray.length;i++){
                   
                   String eventRecord=eventArray[i];
                   dbg("eventRecord"+eventRecord);
                   String eventValues[]=eventRecord.split(",");
                   String eventType=eventValues[0];
                   dbg("eventType"+eventType);
                   String eventName;
                   String key;
                   String studentID;
                   Map<String,String>attributeMap=new HashMap();
                   switch(eventType){
                       
                       case "A":
                           eventName="Assignment";
                           key=eventValues[1];
                           String subjectID=eventValues[2];
                           String assignmentDescription=eventValues[3];
                           dbg("key"+key);
                           dbg("subjectID"+subjectID);
                           dbg("assignmentDescription"+assignmentDescription);
                           attributeMap.put("Subject", subjectID);
                           attributeMap.put("AssignmentDescription", assignmentDescription);
                           eventMap.put(eventName+","+key, attributeMap);
                        break;
                       case "O":
                           eventName="OtherActivity";
                           key=eventValues[1];
                           String activityName=eventValues[2];
                           String venue=eventValues[3];
                           dbg("key"+key);
                           dbg("activityName"+activityName);
                           dbg("venue"+venue);
                           attributeMap.put("ActivityName", activityName);
                           attributeMap.put("Venue", venue);
                           eventMap.put(eventName+"~"+key, attributeMap);
                        break;
                        case "F":
                           eventName="FeeManagement";
                           key=eventValues[1];
                           String feeType=eventValues[2];
                           String amount=eventValues[3];
                           dbg("key"+key);
                           dbg("feeType"+feeType);
                           dbg("amount"+amount);
                           attributeMap.put("FeeType", feeType);
                           attributeMap.put("Amount", amount);
                           eventMap.put(eventName+"~"+key, attributeMap);
                        break;
                        case "AT":
                           eventName="Attendance";
                           studentID=eventValues[1];
                           String year=eventValues[2];
                           String month=eventValues[3];
                           key=studentID+","+year+","+month;
                           String present=eventValues[4].substring(1);
                           String absent=eventValues[5].substring(1);
                           String leave=eventValues[6].substring(1);
                           dbg("key"+key);
                           dbg("present"+present);
                           dbg("absent"+absent);
                           dbg("leave"+leave);
                           attributeMap.put("Present", present);
                           attributeMap.put("Absent", absent);
                           attributeMap.put("Leave", leave);
                           eventMap.put(eventName+"~"+key, attributeMap);
                        break;
                        case "E":
                           eventName="ExamSchedule";
                           studentID=eventValues[1];
                           String exam=eventValues[2];
                           subjectID=eventValues[3];
                           key=studentID+","+exam+","+subjectID;
                           dbg("key"+key);
                           attributeMap.put("Exam", exam);
                           attributeMap.put("SubjectID", subjectID);
                           eventMap.put(eventName+"~"+key, attributeMap);
                        break;
                   }
                   
               }
          
          
          
          
          
          dbg("end of getEventMap");
          return eventMap;
       }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        } 
        
    }
    
    public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     
        return null;
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
