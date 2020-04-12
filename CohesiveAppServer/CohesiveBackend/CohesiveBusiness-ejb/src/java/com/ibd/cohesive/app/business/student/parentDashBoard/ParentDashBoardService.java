/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.parentDashBoard;

import com.ibd.businessViews.IParentDashBoardService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.institute.holidaymaintanence.HolidayMaintanenceService;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
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
@Remote(IParentDashBoardService.class)
@Stateless
public class ParentDashBoardService implements IParentDashBoardService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public ParentDashBoardService(){
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
       dbg("inside ParentDashBoardService--->processing");
       dbg("ParentDashBoardService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 

       BusinessEJB<IParentDashBoardService>parentDashBoardEJB=new BusinessEJB();
       parentDashBoardEJB.set(this);
      
       exAudit=bs.getExistingAudit(parentDashBoardEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,parentDashBoardEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,parentDashBoardEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in parent dash board");
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
            clonedResponse=bs.cloneResponseJsonObject(jsonResponse);  
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
      RequestBody<ParentDashBoard> reqBody = new RequestBody<ParentDashBoard>(); 
           
      try{
      dbg("inside student assignment buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      ParentDashBoard dashBoard=new ParentDashBoard();
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
        dbg("inside student assignment--->view");
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ParentDashBoard> reqBody = request.getReqBody();
        ParentDashBoard parentDashBoard=reqBody.get();
        String userID=parentDashBoard.getUserID();
        
        ArrayList<String>studentsList=getAccessibleStudents(userID);
        
        parentDashBoard.studentDetails=new StudentDetails[studentsList.size()];
        for(int i=0;i<studentsList.size();i++){
            
            parentDashBoard.studentDetails[i]=new StudentDetails();
            String studentID=studentsList.get(i);
            dbg("studentID"+studentID);
            parentDashBoard.studentDetails[i].setStudentID(studentID);
            
            dbg("Attendance processing starts");
            String percentage=getAttendancePercentage(studentID);
            parentDashBoard.studentDetails[i].setAttendance(percentage);
            dbg("Attendance processing ends");
            
            dbg("exam processing starts");
            ArrayList<String>examList=getExamDetails(studentID);  
            parentDashBoard.studentDetails[i].examdetails=new ExamDetails[examList.size()];
            
            for(int j=0;j<examList.size();j++){
                
                String examDetail=examList.get(j);
                dbg("examDetail"+examDetail);
                String exam=examDetail.split("~")[0];
                String subjectID=examDetail.split("~")[0];
                String mark=examDetail.split("~")[0];
                dbg("exam"+exam);
                dbg("subjectID"+subjectID);
                dbg("mark"+mark);
                parentDashBoard.studentDetails[i].examdetails[j]=new ExamDetails();
                parentDashBoard.studentDetails[i].examdetails[j].setExam(exam);
                parentDashBoard.studentDetails[i].examdetails[j].setSubjectID(subjectID);
                parentDashBoard.studentDetails[i].examdetails[j].setMark(mark);
                
            }
            dbg("exam processing ends");
            
            
            dbg("weekly calender  processing starts");
            ArrayList<Date>weekDates=getDatesOfTheWeek();
            parentDashBoard.studentDetails[i].weeklyCalender=new WeeklyCalender[weekDates.size()];

            for(int j=0;j<weekDates.size();j++){
                
                Date date=weekDates.get(j);
                String dateformat=i_db_properties.getProperty("DATE_FORMAT");
                SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
                String dateString=formatter.format(date);
                dbg("dateString"+dateString);
                parentDashBoard.studentDetails[i].weeklyCalender[j]=new WeeklyCalender();
                parentDashBoard.studentDetails[i].weeklyCalender[j].setDate(dateString);
                
                Map<String,Map<String,String>>eventMap=getCalenderDetails(studentID,date);
                parentDashBoard.studentDetails[i].weeklyCalender[j].event=new Event[eventMap.size()];
                
                Iterator<String> eventIterator=eventMap.keySet().iterator();
                
                int k=0;
                while(eventIterator.hasNext()){
                    String eventType_and_key=eventIterator.next();
                    dbg("eventType_and_key"+eventType_and_key);
                    String eventType=eventType_and_key.split("~")[0];
                    String key=eventType_and_key.split("~")[1];
                    parentDashBoard.studentDetails[i].weeklyCalender[j].event[k]=new Event();
                    parentDashBoard.studentDetails[i].weeklyCalender[j].event[k].setEventType(eventType);
                    parentDashBoard.studentDetails[i].weeklyCalender[j].event[k].setKey(key);
                    
                    Map<String,String>attributeMap=eventMap.get(eventType_and_key);
                    parentDashBoard.studentDetails[i].weeklyCalender[j].event[k].eventAttributes=new EventAttributes[attributeMap.size()];
                    
                    Iterator<String> attributeIterator=attributeMap.keySet().iterator();
                    
                    int l=0;
                    while(attributeIterator.hasNext()){
                   
                          String attributeName=attributeIterator.next();
                          String attributeValue=attributeMap.get(attributeName);
                          dbg("attributeName"+attributeName);
                          dbg("attributeValue"+attributeValue);
                          parentDashBoard.studentDetails[i].weeklyCalender[j].event[k].eventAttributes[l]=new EventAttributes();
                          parentDashBoard.studentDetails[i].weeklyCalender[j].event[k].eventAttributes[l].setAttrName(attributeName);
                          parentDashBoard.studentDetails[i].weeklyCalender[j].event[k].eventAttributes[l].setAttrValue(attributeValue);
                          l++;     
                    }      
                    
                    k++;
                }
                
            }
        }
        dbg("weekly calender  processing ends");
       
        
          dbg("end of  paren dashboard --->view");                
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
    
    private ArrayList<String>getAccessibleStudents(String userID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
        
        try{
            
            dbg("inside getAccessibleStudents");
            ArrayList<String>studentList=new ArrayList();
            dbg("userID"+userID);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IPDataService pds=inject.getPdataservice();
            String[] l_pkey={userID};
            
            Map<String,ArrayList<String>>parentRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User", "USER","UVW_PARENT_STUDENT_ROLEMAPPING",session,dbSession);
            dbg("parentRoleMap size"+parentRoleMap.size());
            
            Map<String,List<ArrayList<String>>>studentGroup=parentRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec->rec.get(2)));
            dbg("studentGroup size"+studentGroup.size());
            
            Iterator<String>studentIterator=studentGroup.keySet().iterator();
            
            while(studentIterator.hasNext()){
                
                String studentID=studentIterator.next().trim();
                dbg("studentID"+studentID);
                studentList.add(studentID);
                
            }
            
            
            
            dbg("end of getAccessibleStudents");
            return studentList;
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
    
    private String getAttendancePercentage(String studentID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
        
        try{
            
            dbg("inside getAttendancePercentage");
            dbg("studentID"+studentID);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IDBReadBufferService readBuffer= inject.getDBReadBufferService();
            String l_instituteID=request.getReqHeader().getInstituteID();
            
            Map<String,DBRecord>attendanceMap=readBuffer.readTable("REPORT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_ATTENDANCE_REPORT", session, dbSession);
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
    
     private ArrayList<String>getExamDetails(String studentID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
        
        try{
            
            dbg("inside getExamDetails");
            ArrayList<String>examList=new ArrayList();
            dbg("studentID"+studentID);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IDBReadBufferService readBuffer= inject.getDBReadBufferService();
            String l_instituteID=request.getReqHeader().getInstituteID();
            
            Map<String,DBRecord>progressCardMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_PRORESS_CARD", session, dbSession);
            dbg("progressCardMap size"+progressCardMap.size());
            Map<String,List<DBRecord>>authorizedRecords=progressCardMap.values().stream().filter(rec->rec.getRecord().get(8).trim().equals("O")&&rec.getRecord().get(9).trim().equals("A")).collect(Collectors.groupingBy(rec->rec.getRecord().get(0).trim()+rec.getRecord().get(1).trim()));
            dbg("authorizedRecords size"+authorizedRecords.size());
            Map<String,DBRecord>studentMarksMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_MARKS", session, dbSession);
            dbg("studentMarksMap size"+studentMarksMap.size());
            Iterator<DBRecord>studentMarkIterator=studentMarksMap.values().iterator();
            
            while(studentMarkIterator.hasNext()){
                
                ArrayList<String>studentMarkList=studentMarkIterator.next().getRecord();
                String exam=studentMarkList.get(1).trim();
                dbg("exam"+exam);
                if(authorizedRecords.containsKey(studentID+exam)){
                    
                    dbg("Authorized records student id and exam");
                    String subjectID=studentMarkList.get(2).trim();
                    String mark=studentMarkList.get(4).trim();
                    dbg("subjectID"+subjectID);
                    dbg("mark"+mark);
                    examList.add(exam+"~"+subjectID+"~"+mark);
                }
                
                
            }
            

            dbg("end of getExamDetails");
            return examList;
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
    
    private Map<String,Map<String,String>>getCalenderDetails(String l_studentID,Date date)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
        
        try{
            
            dbg("inside getCalenderDetails");
            dbg("studentID"+l_studentID);
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
                String[] l_pkey={l_studentID,l_year,l_month};
                DBRecord calenderRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_CALENDER", l_pkey, session, dbSession);
                ArrayList<String>l_studentCalenderList= calenderRec.getRecord();
                String l_monthEvent=l_studentCalenderList.get(3).trim();
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
     
    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside parent dash board buildJsonResFromBO");    
        RequestBody<ParentDashBoard> reqBody = request.getReqBody();
        ParentDashBoard parentDashBoard =reqBody.get();
        JsonArrayBuilder studentArray=Json.createArrayBuilder();
        JsonArrayBuilder examArray=Json.createArrayBuilder();     
        JsonArrayBuilder attributeArray=Json.createArrayBuilder();
        JsonArrayBuilder eventArray=Json.createArrayBuilder();
        JsonArrayBuilder weeklyCalenderArray=Json.createArrayBuilder();
               
        for(int i=0;i<parentDashBoard.studentDetails.length;i++){
            
                    for(int j=0;j<parentDashBoard.studentDetails[i].examdetails.length;j++){

                        examArray.add(Json.createObjectBuilder().add("exam", parentDashBoard.studentDetails[i].examdetails[j].getExam())
                                                                .add("subjectID", parentDashBoard.studentDetails[i].examdetails[j].getSubjectID())
                                                                .add("mark", parentDashBoard.studentDetails[i].examdetails[j].getMark()));

                    }

                    for(int j=0;j<parentDashBoard.studentDetails[i].weeklyCalender.length;j++){

                                 for(int k=0;k<parentDashBoard.studentDetails[i].weeklyCalender[j].event.length;k++){

                                        for(int l=0;l<parentDashBoard.studentDetails[i].weeklyCalender[j].event[k].eventAttributes.length;l++){

                                            attributeArray.add(Json.createObjectBuilder().add("attrName", parentDashBoard.studentDetails[i].weeklyCalender[j].event[k].eventAttributes[l].getAttrName())
                                                                                         .add("attrValue", parentDashBoard.studentDetails[i].weeklyCalender[j].event[k].eventAttributes[l].getAttrValue()));

                                       }


                                    eventArray.add(Json.createObjectBuilder().add("eventType", parentDashBoard.studentDetails[i].weeklyCalender[j].event[k].getEventType())
                                                                             .add("key", parentDashBoard.studentDetails[i].weeklyCalender[j].event[k].getKey())
                                                                             .add("eventAttributes", attributeArray));

                                   }
                                 
                                 weeklyCalenderArray.add(Json.createObjectBuilder().add("date", parentDashBoard.studentDetails[i].weeklyCalender[j].getDate())
                                                                                   .add("eventArray", eventArray));
                                  
                              }
            
                          studentArray.add(Json.createObjectBuilder().add("studentID", parentDashBoard.studentDetails[i].getStudentID())
                                                                     .add("attendance", parentDashBoard.studentDetails[i].getAttendance())
                                                                     .add("examDetails", examArray)
                                                                     .add("weeklyCalender", weeklyCalenderArray));
            
        }       
        
        
        
        
            body=Json.createObjectBuilder().add("userID", parentDashBoard.getUserID())
                                           .add("studentDetails", studentArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of parent dash board buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student assignment--->businessValidation");    
       if(!masterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!masterDataValidation(errhandler)){
               status=false;
            }
       }
       
       
       dbg("end of student assignment--->businessValidation"); 
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
        dbg("inside student assignment master mandatory validation");
        RequestBody<ParentDashBoard> reqBody = request.getReqBody();
        ParentDashBoard parentDashBoard =reqBody.get();
        dbg("userID"+parentDashBoard.getUserID());
         if(parentDashBoard.getUserID()==null||parentDashBoard.getUserID().isEmpty()){
             status=false;
             errhandler.log_app_error("BS_VAL_002","User ID");
         }
         
          
          
        dbg("end of student assignment master mandatory validation");
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
        RequestBody<ParentDashBoard> reqBody = request.getReqBody();
        ParentDashBoard parentDashBoard =reqBody.get();
        String userID=parentDashBoard.getUserID();
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
