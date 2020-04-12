/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.teacherDashBoard;

import com.ibd.businessViews.ITeacherDashBoardService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.institute.holidaymaintanence.HolidayMaintanenceService;
import com.ibd.cohesive.app.business.institute.managementDashBoard.ManagementDashBoardService;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.student.parentDashBoard.WeeklyCalender;
import com.ibd.cohesive.app.business.student.studentcalender.Event;
import com.ibd.cohesive.app.business.student.studentcalender.EventAttributes;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.ConvertedDate;
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
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@Remote(ITeacherDashBoardService.class)
@Stateless
public class TeacherDashBoardService implements ITeacherDashBoardService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public TeacherDashBoardService(){
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
       dbg("inside TeacherDashBoardService--->processing");
       dbg("TeacherDashBoardService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 

       BusinessEJB<ITeacherDashBoardService>teacherDashBoardEJB=new BusinessEJB();
       teacherDashBoardEJB.set(this);
      
       exAudit=bs.getExistingAudit(teacherDashBoardEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,teacherDashBoardEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,teacherDashBoardEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in teacher dash board");
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
            BSValidation bsv=inject.getBsv(session);
            //if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                      clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes  
            //BSProcessingException ex=new BSProcessingException("response contains special characters");
              //  dbg(ex);
                
                //session.clearSessionObject();
               //dbSession.clearSessionObject();
               //throw ex;
               
            //}
            session.clearSessionObject();
            dbSession.clearSessionObject();
           }
           }
       
        
       return jsonResponse; 
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
      RequestBody<TeacherDashBoard> reqBody = new RequestBody<TeacherDashBoard>(); 
           
      try{
      dbg("inside teacher assignment buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      TeacherDashBoard dashBoard=new TeacherDashBoard();
      dashBoard.setUserID(l_body.getString("userID"));
      
      
      
        reqBody.set(dashBoard);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException{
                
        try{      
        dbg("inside teacher assignment--->view");
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<TeacherDashBoard> reqBody = request.getReqBody();
        TeacherDashBoard teacherDashBoard=reqBody.get();
        String teacherID=teacherDashBoard.getUserID();
        
            
            dbg("selfAttendance processing starts");
            String percentage=getAttendancePercentage(teacherID);
            teacherDashBoard.setSelfAttendance(percentage);
            dbg("selfAttendance processing ends");
            
            dbg("classAttendance processing starts");
            ArrayList<String>classList=getAccessibleClasses(teacherID);
            teacherDashBoard.classWiseAttendance=new ClassWiseAttendance[classList.size()];
            
            for(int i=0;i<classList.size();i++){
                
                String classs=classList.get(i).trim();
                String standard=classs.split("~")[0];
                String section=classs.split("~")[1];
                dbg("standard"+standard);
                dbg("section"+section);
                String attendanePercentage=getAttendancePercentageOfTheClass(standard,section);
                
                teacherDashBoard.classWiseAttendance[i]=new ClassWiseAttendance();
                teacherDashBoard.classWiseAttendance[i].setStandard(standard);
                teacherDashBoard.classWiseAttendance[i].setSection(section);
                teacherDashBoard.classWiseAttendance[i].setAttendance(attendanePercentage);
            }
            dbg("classAttendance processing ends");
            
            
            dbg("weekly calender  processing starts");
            ArrayList<Date>weekDates=getDatesOfTheWeek();
            teacherDashBoard.weeklyCalender=new WeeklyCalender[weekDates.size()];

            for(int j=0;j<weekDates.size();j++){
                
                Date date=weekDates.get(j);
                String dateformat=i_db_properties.getProperty("DATE_FORMAT");
                SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
                String dateString=formatter.format(date);
                dbg("dateString"+dateString);
                teacherDashBoard.weeklyCalender[j]=new WeeklyCalender();
                teacherDashBoard.weeklyCalender[j].setDate(dateString);
                
                Map<String,Map<String,String>>eventMap=getCalenderDetails(teacherID,date);
                teacherDashBoard.weeklyCalender[j].event=new Event[eventMap.size()];
                
                Iterator<String> eventIterator=eventMap.keySet().iterator();
                
                int k=0;
                while(eventIterator.hasNext()){
                    String eventType_and_key=eventIterator.next();
                    dbg("eventType_and_key"+eventType_and_key);
                    String eventType=eventType_and_key.split("~")[0];
                    String key=eventType_and_key.split("~")[1];
                    teacherDashBoard.weeklyCalender[j].event[k]=new Event();
                    teacherDashBoard.weeklyCalender[j].event[k].setEventType(eventType);
                    teacherDashBoard.weeklyCalender[j].event[k].setKey(key);
                    
                    Map<String,String>attributeMap=eventMap.get(eventType_and_key);
                    teacherDashBoard.weeklyCalender[j].event[k].eventAttributes=new EventAttributes[attributeMap.size()];
                    
                    Iterator<String> attributeIterator=attributeMap.keySet().iterator();
                    
                    int l=0;
                    while(attributeIterator.hasNext()){
                   
                          String attributeName=attributeIterator.next();
                          String attributeValue=attributeMap.get(attributeName);
                          dbg("attributeName"+attributeName);
                          dbg("attributeValue"+attributeValue);
                          teacherDashBoard.weeklyCalender[j].event[k].eventAttributes[l]=new EventAttributes();
                          teacherDashBoard.weeklyCalender[j].event[k].eventAttributes[l].setAttrName(attributeName);
                          teacherDashBoard.weeklyCalender[j].event[k].eventAttributes[l].setAttrValue(attributeValue);
                          l++;     
                    }      
                    
                    k++;
                }
                
            }
        dbg("weekly calender  processing ends");
       
        ManagementDashBoardService managementDash=new ManagementDashBoardService();
        ArrayList<Pending>entryPendingList=managementDash.getSelfRequestPendingList(teacherID, session,request);
        ArrayList<Pending>unAuthPendingList=managementDash.getUnAuthPendingList(teacherID, session,request);
        teacherDashBoard.entryPendingList=entryPendingList;
        teacherDashBoard.unAuthPendingList=unAuthPendingList;
        
        
        
        
          dbg("end of  teacher dashboard --->view");                
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
    
    private ArrayList<String>getAccessibleClasses(String userID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
        
        try{
            
            dbg("inside getAccessibleClasses");
            ArrayList<String>classList=new ArrayList();
            dbg("userID"+userID);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IPDataService pds=inject.getPdataservice();
            Map<String,ArrayList<String>>teacherRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User", "USER","UVW_CLASS_ENTITY_ROLEMAPPING",session,dbSession);
            dbg("teacherRoleMap size"+teacherRoleMap.size());
            Map<String,List<ArrayList<String>>>classGroup=teacherRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec->rec.get(2).trim()+"~"+rec.get(3).trim()));
            dbg("classGroup size"+classGroup.size());
            Iterator<String>teacherIterator=classGroup.keySet().iterator();
            
            while(teacherIterator.hasNext()){
                String classs=teacherIterator.next().trim();
                dbg("classs"+classs);
                classList.add(classs);
            }
            
            
            
            dbg("end of getAccessibleClasses");
            return classList;
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
    
    private String getAttendancePercentage(String teacherID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
        
        try{
            
            dbg("inside getAttendancePercentage");
            dbg("teacherID"+teacherID);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IDBReadBufferService readBuffer= inject.getDBReadBufferService();
            String l_instituteID=request.getReqHeader().getInstituteID();
            
            Map<String,DBRecord>attendanceMap=readBuffer.readTable("REPORT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID,"TEACHER", "TVW_TEACHER_ATTENDANCE_REPORT", session, dbSession);
            int totalWorkingDays= attendanceMap.values().stream().mapToInt(rec->Integer.parseInt(rec.getRecord().get(4).trim())).sum();
            int totalPresentDays= attendanceMap.values().stream().mapToInt(rec->Integer.parseInt(rec.getRecord().get(5).trim())).sum();
            dbg("totalWorkingDays"+totalWorkingDays);
            dbg("totalPresentDays"+totalPresentDays);
            int percentage=(totalPresentDays/totalWorkingDays)*100;
            dbg("percentage"+percentage);
            

            dbg("end of getAttendancePercentage");
            return Integer.toString(percentage);
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
    
    private String getAttendancePercentageOfTheClass(String standard,String section)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
        
        try{
            
            dbg("inside getAttendancePercentageOfTheClass");
            dbg("standard"+standard);
            dbg("section"+section);
            DecimalFormat df = new DecimalFormat("###.##");
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IDBReadBufferService readBuffer= inject.getDBReadBufferService();
            String l_instituteID=request.getReqHeader().getInstituteID();
            
            Map<String,DBRecord>attendanceMap=readBuffer.readTable("REPORT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section,"CLASS", "CLASS_ATTENDANCE_REPORT", session, dbSession);
            Double average= attendanceMap.values().stream().mapToInt(rec->Integer.parseInt(rec.getRecord().get(7).trim())).average().getAsDouble();
            String avgString=df.format(Math.round(average));
            
            dbg("average"+average);
            dbg("avgString"+avgString);
            

            dbg("end of getAttendancePercentageOfTheClass");
            return avgString;
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
    
     
    private ArrayList<Date>getDatesOfTheWeek()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
        
        try{
            
           dbg("inside getDatesOfTheWeek");
           IBDProperties i_db_properties=session.getCohesiveproperties();
           String dateformat=i_db_properties.getProperty("DATE_FORMAT");
           String instituteID=request.getReqHeader().getInstituteID();
           SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
//           IHolidayMaintanenceService holiday=inject.getHolidayMaintanenceService();
           ArrayList<Date> dates = new ArrayList();
           BusinessService bs=inject.getBusinessService(session);
           String currentDate=bs.getCurrentDate();
           Date  startDate = (Date)formatter.parse(currentDate); 
          
           long interval = 24*1000 * 60 * 60; // 1 hour in millis
           long curTime = startDate.getTime();
           dbg("interval"+interval);
           dbg("curTime"+curTime);
            while (dates.size()<=7) {
                 
                 Date date=new Date(curTime);
                 dbg("date before holiday calling"+date);
                 System.out.println("date before holiday calling"+date);
                 HolidayMaintanenceService holiday=new HolidayMaintanenceService();
                 
                 if(holiday.checkWorkingDay(date, instituteID, session)){
                   dates.add(date);
                 }
                   curTime += interval;
            }
            
            
            dbg("end of getDatesOfTheWeek")    ; 
             return dates;
            
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
    
    private Map<String,Map<String,String>>getCalenderDetails(String l_teacherID,Date date)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
        
        try{
            
            dbg("inside getCalenderDetails");
            dbg("teacherID"+l_teacherID);
            dbg("date"+date);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IDBReadBufferService readBuffer= inject.getDBReadBufferService();
            String l_instituteID=request.getReqHeader().getInstituteID();
            BusinessService bs=inject.getBusinessService(session);

                String dateformat=i_db_properties.getProperty("DATE_FORMAT");
                SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
                String dateString=formatter.format(date);
                dbg("dateString"+dateString);
                ConvertedDate convertedDate=bs.getYearMonthandDay(dateString);
                String l_year=convertedDate.getYear();
                String l_month=convertedDate.getMonth();
                String[] l_pkey={l_teacherID,l_year,l_month};
                DBRecord calenderRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_TEACHER_CALENDER", l_pkey, session, dbSession);
                ArrayList<String>l_teacherCalenderList= calenderRec.getRecord();
                String l_monthEvent=l_teacherCalenderList.get(3).trim();
                dbg("l_monthEvent"+l_monthEvent);
                Map<String,Map<String,String>>eventMap=getEventMap(l_monthEvent,dateString);
                
            
            dbg("end of getCalenderDetails");
            return eventMap;
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
    
    
     private Map<String,Map<String,String>>getEventMap(String p_monthEvent,String date)throws BSProcessingException{
        
      try{  
               dbg("inside getEventMap");
               dbg("p_monthEvent"+p_monthEvent);
               BusinessService bs=inject.getBusinessService(session);
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
     
     
     
     
     
     
     
     
 
    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside teacher dash board buildJsonResFromBO");    
        RequestBody<TeacherDashBoard> reqBody = request.getReqBody();
        TeacherDashBoard teacherDashBoard =reqBody.get();
        JsonArrayBuilder classAttendanceArray=Json.createArrayBuilder();     
        JsonArrayBuilder attributeArray=Json.createArrayBuilder();
        JsonArrayBuilder eventArray=Json.createArrayBuilder();
        JsonArrayBuilder weeklyCalenderArray=Json.createArrayBuilder();
        JsonArrayBuilder selfEntryArray=Json.createArrayBuilder();
        JsonArrayBuilder unAuthArray=Json.createArrayBuilder();       
        JsonArrayBuilder pkArray=Json.createArrayBuilder();
            
             if(teacherDashBoard.classWiseAttendance!=null){
         
                for(int i=0;i<teacherDashBoard.classWiseAttendance.length;i++){

                    classAttendanceArray.add(Json.createObjectBuilder().add("standard", teacherDashBoard.classWiseAttendance[i].getStandard())
                                                                       .add("section", teacherDashBoard.classWiseAttendance[i].getSection())
                                                                       .add("attendance", teacherDashBoard.classWiseAttendance[i].getAttendance()));

                }
             }

             if(teacherDashBoard.weeklyCalender!=null){
                    for(int j=0;j<teacherDashBoard.weeklyCalender.length;j++){

                                 for(int k=0;k<teacherDashBoard.weeklyCalender[j].event.length;k++){

                                        for(int l=0;l<teacherDashBoard.weeklyCalender[j].event[k].eventAttributes.length;l++){

                                            attributeArray.add(Json.createObjectBuilder().add("attrName", teacherDashBoard.weeklyCalender[j].event[k].eventAttributes[l].getAttrName())
                                                                                         .add("attrValue", teacherDashBoard.weeklyCalender[j].event[k].eventAttributes[l].getAttrValue()));

                                       }


                                    eventArray.add(Json.createObjectBuilder().add("eventType", teacherDashBoard.weeklyCalender[j].event[k].getEventType())
                                                                             .add("key", teacherDashBoard.weeklyCalender[j].event[k].getKey())
                                                                             .add("eventAttributes", attributeArray));

                                   }
                                 
                                 weeklyCalenderArray.add(Json.createObjectBuilder().add("date", teacherDashBoard.weeklyCalender[j].getDate())
                                                                                   .add("eventArray", eventArray));
                                  
                              }
            
                    }
                    for(int i=0;i<teacherDashBoard.entryPendingList.size();i++){
                        
                        
                        for(int j=0;j<teacherDashBoard.entryPendingList.get(i).getPrimaryKey().size();j++){
                            
                            pkArray.add(Json.createObjectBuilder().add("primaryKey", teacherDashBoard.entryPendingList.get(i).getPrimaryKey().get(j)));
                            
                            
                        }
                        
                        
                        selfEntryArray.add(Json.createObjectBuilder().add("serviceType", teacherDashBoard.entryPendingList.get(i).getServiceType())
                                                                     .add("serviceName", teacherDashBoard.entryPendingList.get(i).getServiceName())
                                                                     .add("count", teacherDashBoard.entryPendingList.get(i).getCount())
                                                                     .add("pkArray", pkArray));
                        
                        
                    }
        
             for(int i=0;i<teacherDashBoard.unAuthPendingList.size();i++){
                        
                        
                        for(int j=0;j<teacherDashBoard.unAuthPendingList.get(i).getPrimaryKey().size();j++){
                            
                            pkArray.add(Json.createObjectBuilder().add("primaryKey", teacherDashBoard.unAuthPendingList.get(i).getPrimaryKey().get(j)));
                            
                            
                        }
                        
                        unAuthArray.add(Json.createObjectBuilder().add("serviceType", teacherDashBoard.unAuthPendingList.get(i).getServiceType())
                                                                     .add("serviceName", teacherDashBoard.unAuthPendingList.get(i).getServiceName())
                                                                     .add("count", teacherDashBoard.unAuthPendingList.get(i).getCount())
                                                                     .add("pkArray", pkArray));
                        
                        
                    }
        
            body=Json.createObjectBuilder().add("userID", teacherDashBoard.getUserID())
                                           .add("selfAttendance", teacherDashBoard.getSelfAttendance())
                                           .add("weeklyCalender", weeklyCalenderArray)
                                           .add("selfEntryPending", selfEntryArray)
                                           .add("unAuthPending", unAuthArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of teacher dash board buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside teacher assignment--->businessValidation");    
       if(!masterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!masterDataValidation(errhandler)){
               status=false;
            }
       }
       
       
       dbg("end of teacher assignment--->businessValidation"); 
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
        dbg("inside teacher assignment master mandatory validation");
        RequestBody<TeacherDashBoard> reqBody = request.getReqBody();
        TeacherDashBoard teacherDashBoard =reqBody.get();
        dbg("userID"+teacherDashBoard.getUserID());
         if(teacherDashBoard.getUserID()==null||teacherDashBoard.getUserID().isEmpty()){
             status=false;
             errhandler.log_app_error("BS_VAL_002","User ID");
         }
         
          
          
        dbg("end of teacher assignment master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
      boolean status=true;
        try{
        dbg("inside  master data validation");
        RequestBody<TeacherDashBoard> reqBody = request.getReqBody();
        TeacherDashBoard teacherDashBoard =reqBody.get();
        String userID=teacherDashBoard.getUserID();
        String instituteID=request.getReqHeader().getInstituteID();
        BSValidation bsv=inject.getBsv(session);
        
        
        if(!bsv.userIDValidation(userID,errhandler,instituteID,inject,session,dbSession)){
           
             status=false;
             errhandler.log_app_error("BS_VAL_003","User ID");
        }
        
          
          
        dbg("end of master data validation");
        }catch (Exception ex) {
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
