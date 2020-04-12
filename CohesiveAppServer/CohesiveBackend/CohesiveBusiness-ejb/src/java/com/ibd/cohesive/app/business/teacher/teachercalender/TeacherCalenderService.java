/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.teachercalender;

import com.ibd.businessViews.ITeacherCalenderService;
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
 * @author IBD Technologies
 */
//@Local(ITeacherCalenderService.class)
@Remote(ITeacherCalenderService.class)
@Stateless
public class TeacherCalenderService implements ITeacherCalenderService{
     AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public TeacherCalenderService(){
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
       dbg("inside TeacherCalenderService--->processing");
       dbg("TeacherCalenderService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<TeacherCalender> reqBody = request.getReqBody();
       TeacherCalender teacherCalender =reqBody.get();
       String l_teacherID=teacherCalender.getTeacherID();
       String l_date=teacherCalender.getDate(); 
       l_lockKey=l_teacherID+"~"+l_date;
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<ITeacherCalenderService>teacherCalenderEJB=new BusinessEJB();
       teacherCalenderEJB.set(this);
      
       exAudit=bs.getExistingAudit(teacherCalenderEJB);
       
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
      
       bs.businessServiceProcssing(request, exAudit, inject,teacherCalenderEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,teacherCalenderEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in teacher calender");
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
                /*if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                   BSProcessingException ex=new BSProcessingException("response contains special characters");
                   dbg(ex);
                   session.clearSessionObject();
                   dbSession.clearSessionObject();
                   throw ex;
                }*/
                      clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes    
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
      TeacherCalender teacherCalender=new TeacherCalender();
      RequestBody<TeacherCalender> reqBody = new RequestBody<TeacherCalender>(); 
           
      try{
      dbg("inside teacher calender buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      teacherCalender.setTeacherID(l_body.getString("teacherID"));
      teacherCalender.setTeacherName(l_body.getString("teacherName"));
      teacherCalender.setDate(l_body.getString("date"));
      
      
        reqBody.set(teacherCalender);
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
        dbg("inside teacher calender--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        BusinessService bs=inject.getBusinessService(session);
        RequestBody<TeacherCalender> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        TeacherCalender teacherCalender =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_teacherID=teacherCalender.getTeacherID();
        String l_date=teacherCalender.getDate(); 
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth();
        String[] l_pkey={l_teacherID,l_year,l_month};
        
        DBRecord calenderRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_TEACHER_CALENDER", l_pkey, session, dbSession);
       
        buildBOfromDB(calenderRec);
        
          dbg("end of  completed teacher calender--->view");                
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
      private void buildBOfromDB(DBRecord p_teacherCalenderRecord)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<TeacherCalender> reqBody = request.getReqBody();
           TeacherCalender teacherCalender =reqBody.get();
           ArrayList<String>l_teacherCalenderList= p_teacherCalenderRecord.getRecord();
           String l_monthEvent=l_teacherCalenderList.get(3).trim();
           dbg("buildBOfromDB--->l_monthEvent"+l_monthEvent);
           Map<String,Map<String,String>>eventMap=getEventMap(l_monthEvent);
           dbg("eventMap size"+eventMap.size());
           Iterator<String> eventIterator=eventMap.keySet().iterator();
           teacherCalender.event=new Event[eventMap.size()];
           
           int i=0;
           while(eventIterator.hasNext()){
               String eventType_and_key=eventIterator.next();
               dbg("eventType_and_key"+eventType_and_key);
               String eventType=eventType_and_key.split("~")[0];
               String key=eventType_and_key.split("~")[1];
               dbg("eventType"+eventType);
               dbg("key"+key);
               
               teacherCalender.event[i]=new Event();
               teacherCalender.event[i].setEventType(eventType);
               teacherCalender.event[i].setKey(key);
               
               Map<String,String>attributeMap=eventMap.get(eventType_and_key);
               dbg("attributeMap size"+attributeMap.size());
               Iterator<String> attributeIterator=attributeMap.keySet().iterator();
               teacherCalender.event[i].eventAttributes=new EventAttributes[attributeMap.size()];
               
               int j=0;
               while(attributeIterator.hasNext()){
                   
                   String attributeName=attributeIterator.next();
                   String attributeValue=attributeMap.get(attributeName);
                   dbg("attributeName"+attributeName);
                   dbg("attributeValue"+attributeValue);
                   teacherCalender.event[i].eventAttributes[j]=new com.ibd.cohesive.app.business.teacher.teachercalender.EventAttributes();
                   teacherCalender.event[i].eventAttributes[j].setAttrName(attributeName);
                   teacherCalender.event[i].eventAttributes[j].setAttrValue(attributeValue);
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
        dbg("inside teacher calender buildJsonResFromBO");    
        RequestBody<TeacherCalender> reqBody = request.getReqBody();
        TeacherCalender teacherCalender =reqBody.get();
        JsonArrayBuilder attributeArray=Json.createArrayBuilder();
        JsonArrayBuilder eventArray=Json.createArrayBuilder();
        
        for(int i=0;i<teacherCalender.event.length;i++){
            
            for(int j=0;j<teacherCalender.event[i].eventAttributes.length;j++){
                
                attributeArray.add(Json.createObjectBuilder().add("attrName", teacherCalender.event[i].eventAttributes[j].getAttrName())
                                                             .add("attrValue", teacherCalender.event[i].eventAttributes[j].getAttrValue()));
                
           }
            
            
            eventArray.add(Json.createObjectBuilder().add("eventType", teacherCalender.event[i].getEventType())
                                                     .add("key", teacherCalender.event[i].getKey())
                                                     .add("eventAttributes", attributeArray));
            
        }
        
        
        body=Json.createObjectBuilder().add("teacherID", teacherCalender.getTeacherID())
                                       .add("teacherName", teacherCalender.getTeacherName())
                                       .add("date", teacherCalender.getDate())
                                       .add("events", eventArray)
                                       .build();
                                            
              dbg(body.toString());  
           dbg("end of teacher calender buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside teacher calender--->businessValidation");    
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
       dbg("end of teacher calender--->businessValidation"); 
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
        dbg("inside teacher calender master mandatory validation");
        RequestBody<TeacherCalender> reqBody = request.getReqBody();
        TeacherCalender teacherCalender =reqBody.get();
        
         if(teacherCalender.getTeacherID()==null||teacherCalender.getTeacherID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","teacherID");  
         }
         
          if(teacherCalender.getDate()==null||teacherCalender.getDate().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","Date");  
         }
          
        dbg("end of teacher calender master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside teacher calender masterDataValidation");
             RequestBody<TeacherCalender> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             TeacherCalender teacherCalender =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_teacherID=teacherCalender.getTeacherID();
             String l_date=teacherCalender.getDate(); 
             
             if(!bsv.teacherIDValidation(l_teacherID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","teacherID");
             }
             
             if(!bsv.dateFormatValidation(l_date, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Date");
             }
            
            dbg("end of teacher calender masterDataValidation");
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
           
           dbg("inside teacherCalender--->operation validation"); 
           String l_operation=request.getReqHeader().getOperation();
           dbg("l_operation"+l_operation);
           ErrorHandler errhandler= session.getErrorhandler();
           if(!(l_operation.equals("View"))){
               status=false;  
               errhandler.log_app_error("BS_VAL_000",null);  
           } 
            dbg("end of teacherCalender--->operation validation--->status"+status);
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
               RequestBody<TeacherCalender> reqBody = request.getReqBody();
               TeacherCalender teacherCalender =reqBody.get();
               String date=teacherCalender.getDate();
               ConvertedDate convertedDate=bs.getYearMonthandDay(date);
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
                   String teacherID;
                   Map<String,String>attributeMap=new HashMap();
                     
                           eventName="Attendance";
                           teacherID=eventValues[1];
                           String year=eventValues[2];
                           String month=eventValues[3];
                           key=teacherID+","+year+","+month;
                           dbg("key");
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
