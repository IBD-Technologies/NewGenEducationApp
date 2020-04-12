/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.teacherattendance;

import com.ibd.businessViews.ITeacherAttendanceService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.student.studentattendanceservice.AuditDetails;
import com.ibd.cohesive.app.business.student.studentattendanceservice.DayAttendance;
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
import com.ibd.cohesive.db.read.IDBReadService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
//@Local(ITeacherAttendanceService.class)
@Remote(ITeacherAttendanceService.class)
@Stateless
public class TeacherAttendanceService implements ITeacherAttendanceService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    Map<String,String>filtermap_dummy;
    String filterkey_dummy;
    
    public TeacherAttendanceService(){
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
       dbg("inside TeacherAttendanceService--->processing");
       dbg("TeacherAttendanceService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<TeacherAttendance> reqBody = request.getReqBody();
       TeacherAttendance teacherAttendance =reqBody.get();
       String l_teacherID=teacherAttendance.getTeacherID();
       String l_date=teacherAttendance.getDate();
       ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
       String l_year=convertedDate.getYear();
       String l_month=convertedDate.getMonth();
       l_lockKey=l_teacherID.concat("~").concat(l_year).concat("~").concat(l_month);
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<ITeacherAttendanceService>teacherAttendanceEJB=new BusinessEJB();
       teacherAttendanceEJB.set(this);
      
       exAudit=bs.getExistingAudit(teacherAttendanceEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,teacherAttendanceEJB);
       String l_operation=request.getReqHeader().getOperation();
       
       if(l_operation.equals("Create-Default")){
           
           createDefault();
       }
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,teacherAttendanceEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in teacher attendance");
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
           filtermap_dummy=null;
           filterkey_dummy=null;
           request=null;
            bs=inject.getBusinessService(session);
            if(l_session_created_now){
                bs.responselogging(jsonResponse, inject,session,dbSession);
                dbg("Response"+jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
              /*  if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
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
      TeacherAttendance teacherAttendance=new TeacherAttendance();
      RequestBody<TeacherAttendance> reqBody = new RequestBody<TeacherAttendance>(); 
           
      try{
      dbg("inside teacher attendance buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      teacherAttendance.setTeacherID(l_body.getString("teacherID"));
      teacherAttendance.setTeacherName(l_body.getString("teacherName"));
      teacherAttendance.setDate(l_body.getString("date"));
      if(!l_operation.equals("View")&&!l_operation.equals("Create-Default")){
          JsonArray attendanceArray=l_body.getJsonArray("attendance");
          teacherAttendance.periodAttendance=new  PeriodAttendance[attendanceArray.size()];
          
          for(int i=0;i<attendanceArray.size();i++){
              JsonObject l_periodObject=attendanceArray.getJsonObject(i);
              teacherAttendance.periodAttendance[i]=new PeriodAttendance();
              teacherAttendance.periodAttendance[i].setPeriodNumber(l_periodObject.getString("periodNumber"));
              teacherAttendance.periodAttendance[i].setAttendance(l_periodObject.getString("attendance"));
          }
          
      }
        reqBody.set(teacherAttendance);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
    try{ 
        dbg("inside TEACHER attendance create"); 
        boolean recordExistence=true;
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        BusinessService bs=inject.getBusinessService(session);
        RequestBody<TeacherAttendance> reqBody = request.getReqBody();
        TeacherAttendance teacherAttendance =reqBody.get();
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_teacherID=teacherAttendance.getTeacherID();
        String l_date=teacherAttendance.getDate();
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth();
        String l_day=convertedDate.getDay();
        String dbFormatDayAttendance=dbFormatConversion(l_day);
        String previousMonthAttendance=null;
        String[] l_primaryKey={l_teacherID,l_year,l_month};
        try{
        DBRecord attendanceRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_TEACHER_ATTENDANCE", l_primaryKey, session, dbSession);
        previousMonthAttendance=attendanceRecord.getRecord().get(3).trim();


            
        }catch(DBValidationException ex){ 
                 if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                     session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                     session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                     recordExistence=false;
                 }else{
                     throw ex;
                 }
             }
       if(recordExistence) {
           createDBFormatConversion(previousMonthAttendance,l_day);
       }else{
          String monthAttendance= createMonthAttendanceRecord();
          monthAttendance= setDayDBFromatAttendanceRecord(monthAttendance,dbFormatDayAttendance,l_day,"C");
          dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER",42,l_teacherID,l_year,l_month,monthAttendance);
       }
        
        dbg("end of teacher attendance create"); 
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception".concat(ex.toString()));
        }
    }
    
     

    
    public void authUpdate()throws DBValidationException,DBProcessingException,BSProcessingException{
        
     try{ 
        dbg("inside teacher attendance--->auth update");
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        BusinessService bs=inject.getBusinessService(session);
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<TeacherAttendance> reqBody = request.getReqBody();
        TeacherAttendance teacherAttendance =reqBody.get();
        String l_teacherID=teacherAttendance.getTeacherID();
        String l_date=teacherAttendance.getDate();
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth(); 
        String l_day=convertedDate.getDay();
        String[] l_primaryKey={l_teacherID,l_year,l_month};
        
        DBRecord attendanceRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_TEACHER_ATTENDANCE", l_primaryKey, session, dbSession);
        String previousAttendance=attendanceRecord.getRecord().get(3).trim();
        updateDBFormatConversion(previousAttendance,l_day,"U");
        
        
         dbg("end of teacher attendance--->auth update");          
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
             throw new DBProcessingException(ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
        
       }
    public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
        
       try{ 
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();   
        BusinessService bs=inject.getBusinessService(session);   
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<TeacherAttendance> reqBody = request.getReqBody();
        TeacherAttendance teacherAttendance =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();   
        String l_teacherID=teacherAttendance.getTeacherID();
        String l_date=teacherAttendance.getDate();
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth(); 
        String l_day=convertedDate.getDay();
        String monthAttendance;
        String[] l_primaryKey={l_teacherID,l_year,l_month};
        
        DBRecord attendanceRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_TEACHER_ATTENDANCE", l_primaryKey, session, dbSession);
        monthAttendance=attendanceRecord.getRecord().get(3).trim();
        
        updateDBFormatConversion(monthAttendance,l_day,"U");
                  
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception".concat(ex.toString()));
        }
    }

    
    public void delete()throws DBValidationException,DBProcessingException,BSProcessingException{
        try{
        dbg("inside teacher attendance delete");    
        IDBReadBufferService readBuffer=inject.getDBReadBufferService(); 
        BusinessService bs=inject.getBusinessService(session); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<TeacherAttendance> reqBody = request.getReqBody();
        TeacherAttendance teacherAttendance =reqBody.get();
        String l_teacherID=teacherAttendance.getTeacherID();
        String l_date=teacherAttendance.getDate();
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth(); 
        String l_day=convertedDate.getDay();
        String[] l_primaryKey={l_teacherID,l_year,l_month};
        String previousAttendance;
        
        DBRecord attendanceRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_TEACHER_ATTENDANCE", l_primaryKey, session, dbSession);
        previousAttendance=attendanceRecord.getRecord().get(3).trim();
        deleteDBFormatConversion(previousAttendance,l_day);
        
            dbg("end of teacher attendance delete");
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
             throw new DBProcessingException(ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
        
       }
    
    private void createDefault()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside teacher attendance--->createdefault");    
        BusinessService bs=inject.getBusinessService(session); 
        IDBReadService dbrs=inject.getDbreadservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<TeacherAttendance> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        TeacherAttendance teacherAttendance =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_teacherID=teacherAttendance.getTeacherID();
        String l_date=teacherAttendance.getDate();
        String l_dayOfWeek=bs.getDay(l_date);
        dbg("l_dayOfWeek"+l_dayOfWeek);
         Map<String,DBRecord>detailMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_TEACHER_TIMETABLE_DETAIL", session, dbSession);
        
            Map<String,List<DBRecord>>dayWiseGroup=detailMap.values().stream().collect(Collectors.groupingBy(rec->rec.getRecord().get(1).trim()));
            List<DBRecord>recordForTheDay=dayWiseGroup.get(l_dayOfWeek);
            
            teacherAttendance.periodAttendance=new  PeriodAttendance[recordForTheDay.size()];
            
            for(int i=0;i<recordForTheDay.size();i++){
                
                ArrayList<String>periodRecords=recordForTheDay.get(i).getRecord();
                teacherAttendance.periodAttendance[i]=new PeriodAttendance();
                teacherAttendance.periodAttendance[i].setPeriodNumber(periodRecords.get(2).trim());
                teacherAttendance.periodAttendance[i].setSubjectID(periodRecords.get(3).trim());
                teacherAttendance.periodAttendance[i].setStandard(periodRecords.get(4).trim());
                teacherAttendance.periodAttendance[i].setSection(periodRecords.get(5).trim());
            }
            
            
        
          dbg("end of  completed teacher attendance--->view");                
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

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException{
                
        try{      
        dbg("inside teacher attendance--->view");    
        BusinessService bs=inject.getBusinessService(session); 
        IDBReadService dbrs=inject.getDbreadservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<TeacherAttendance> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        TeacherAttendance teacherAttendance =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_teacherID=teacherAttendance.getTeacherID();
        String l_date=teacherAttendance.getDate();
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth(); 
        String[] l_primaryKey={l_teacherID,l_year,l_month};
        
        DBRecord attendanceRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_TEACHER_ATTENDANCE", l_primaryKey, session, dbSession);
       
        buildBOfromDB(attendanceRecord);
        
          dbg("end of  completed teacher attendance--->view");                
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
      private void buildBOfromDB(DBRecord p_teacherAttendanceRecord)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           IPDataService pds=inject.getPdataservice();
           BusinessService bs=inject.getBusinessService(session);
           RequestBody<TeacherAttendance> reqBody = request.getReqBody();
           TeacherAttendance teacherAttendance =reqBody.get();
           ArrayList<String>l_teacherAttendanceList= p_teacherAttendanceRecord.getRecord();
           String l_date=teacherAttendance.getDate();
           ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
           String l_day=convertedDate.getDay();
           
           
           
           if(l_teacherAttendanceList!=null&&!l_teacherAttendanceList.isEmpty()){
               
            String monthAttendance=l_teacherAttendanceList.get(3).trim();
            
            getMaxVersionAttendanceOftheDay (monthAttendance,l_day) ; 
            String max_version_Attendance=filtermap_dummy.get(l_day);
            String[] dayArray=max_version_Attendance.split(",");
            String attendanceRecord= dayArray[0];
            String[] periodArray=  attendanceRecord.split("p");
            teacherAttendance.periodAttendance=new  PeriodAttendance[periodArray.length-1];
            int j=0;
            for(int i=1;i<periodArray.length;i++){
                String periodNumber=periodArray[i].substring(0, 1);
                String attendance=periodArray[i].substring(1);
                teacherAttendance.periodAttendance[j]=new PeriodAttendance();
                
                
                
                
                
                teacherAttendance.periodAttendance[j].setPeriodNumber(periodNumber);
                teacherAttendance.periodAttendance[j].setAttendance(attendance);
            j++;    
            } 
            
            AuditDetails audit= getAuditDetailsFromDayAttendance (max_version_Attendance)  ;
            
             
            request.getReqAudit().setMakerID(audit.getMakerID());
            request.getReqAudit().setCheckerID(audit.getCheckerID());
            request.getReqAudit().setMakerDateStamp(audit.getMakerDateStamp());
            request.getReqAudit().setCheckerDateStamp(audit.getCheckerDateStamp());
            request.getReqAudit().setRecordStatus(audit.getRecordStatus());
            request.getReqAudit().setAuthStatus(audit.getAuthStatus());
            request.getReqAudit().setVersionNumber(audit.getVersionNo());
            request.getReqAudit().setMakerRemarks(audit.getMakerRemarks());
            request.getReqAudit().setCheckerRemarks(audit.getCheckerRemarks());
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
        dbg("inside teacher attendance buildJsonResFromBO");   
        RequestBody<TeacherAttendance> reqBody = request.getReqBody();
        TeacherAttendance teacherAttendance =reqBody.get();
        JsonArrayBuilder attendance=Json.createArrayBuilder();
        String l_operation=request.getReqHeader().getOperation();
        if(l_operation.equals("Create-Default")){
            
            for(int i=0;i<teacherAttendance.periodAttendance.length;i++){
               String l_standard=teacherAttendance.periodAttendance[i].getStandard();
               String l_section=teacherAttendance.periodAttendance[i].getSection();
            
               attendance.add(Json.createObjectBuilder().add("periodNo", teacherAttendance.periodAttendance[i].getPeriodNumber())
                                                        .add("attendance", "-")
                                                        .add("subjectID", teacherAttendance.periodAttendance[i].getSubjectID())
                                                        .add("class",l_standard+"/"+l_section));
              }
            
            
        }else{

        for(int i=0;i<teacherAttendance.periodAttendance.length;i++){
            
            
               attendance.add(Json.createObjectBuilder().add("periodNo", teacherAttendance.periodAttendance[i].getPeriodNumber())
                                                        .add("attendance", teacherAttendance.periodAttendance[i].getAttendance()));
            
        }
        
        }
        
        body=Json.createObjectBuilder().add("teacherID", teacherAttendance.getTeacherID())
                                       .add("teacherName", teacherAttendance.getTeacherName())
                                       .add("date", teacherAttendance.getDate())
                                       .add("attendance",attendance)
                                       .build();
                                            
              dbg(body.toString());  
           dbg("end of teacher attendance buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside teacher attendance--->businessValidation");    
       String l_operation=request.getReqHeader().getOperation();
       
       if(!masterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!masterDataValidation(errhandler)){
             status=false;
            }
       }
       
       if(!(l_operation.equals("View"))&&!l_operation.equals("Create-Default")){
           
           if(!detailMandatoryValidation(errhandler)) {
               
               status=false;
           } else{
           
           if(!detailDataValidation(errhandler)){
               
               status=false;
           }
            
           
           }
       
       }
       dbg("end of teacher attendance--->businessValidation"); 
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
        dbg("inside teacher attendance master mandatory validation");
        RequestBody<TeacherAttendance> reqBody = request.getReqBody();
        TeacherAttendance teacherAttendance =reqBody.get();
        
         if(teacherAttendance.getTeacherID()==null||teacherAttendance.getTeacherID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","teacherID");  
         }
          if(teacherAttendance.getDate()==null||teacherAttendance.getDate().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","date");  
         }
          
          
        dbg("end of teacher attendance master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside teacher attendance masterDataValidation");
             RequestBody<TeacherAttendance> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             TeacherAttendance teacherAttendance =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_teacherID=teacherAttendance.getTeacherID();
             String l_date=teacherAttendance.getDate();
             
             if(!bsv.teacherIDValidation(l_teacherID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","teacherID");
             }
             if(!bsv.dateFormatValidation(l_date, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Date");
             }
             if(!bsv.pastDateValidation(l_date, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Date");
             }
            
            dbg("end of teacher attendance masterDataValidation");
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
        boolean status=true;
        RequestBody<TeacherAttendance> reqBody = request.getReqBody();
        TeacherAttendance teacherAttendance =reqBody.get();
        
        try{
            
            dbg("inside teacher attendance detailMandatoryValidation");
           
            if(teacherAttendance.getPeriodAttendance()==null||teacherAttendance.getPeriodAttendance().length==0){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","AttendanceTopic");  
            }else{
                
                for(int i=0;i<teacherAttendance.getPeriodAttendance().length;i++){
                    
                    if(teacherAttendance.getPeriodAttendance()[i].getPeriodNumber()==null||teacherAttendance.getPeriodAttendance()[i].getPeriodNumber().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","periodNumber");  
                    }
                    if(teacherAttendance.getPeriodAttendance()[i].getAttendance()==null||teacherAttendance.getPeriodAttendance()[i].getAttendance().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","attendance");  
                    }
                    
                }
                
              
            }
          
            
           dbg("end of teacher attendance detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside teacher attendance detailDataValidation");
             RequestBody<TeacherAttendance> reqBody = request.getReqBody();
             TeacherAttendance teacherAttendance =reqBody.get();
             BSValidation bsv=inject.getBsv(session);
             int periodSize=teacherAttendance.getPeriodAttendance().length;
             
             if(!bsv.periodSizeValidation(periodSize, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Period Size");
             }
             
             
             for(int i=0;i<teacherAttendance.getPeriodAttendance().length;i++){
                 
                 String l_periodNo=teacherAttendance.getPeriodAttendance()[i].getPeriodNumber();
                 String l_attendance=teacherAttendance.getPeriodAttendance()[i].getAttendance();
                 
                 if(!bsv.periodNumberValidation(l_periodNo, session, dbSession, inject)){
                     status=false;
                    errhandler.log_app_error("BS_VAL_003","PeriodNo");
                 }
                 if(!bsv.attendanceCharachterValidation(l_attendance, session, dbSession, inject)){
                     status=false;
                    errhandler.log_app_error("BS_VAL_003","Attendance");
                 }
             }
             

            dbg("end of teacher attendance detailDataValidation");
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
     try{
        dbg("inside TeacherAttendance--->getExistingAudit") ;
        exAudit=new ExistingAudit();
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_operation=request.getReqHeader().getOperation();
        String l_service=request.getReqHeader().getService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
        if(!l_operation.equals("Create")&&!l_operation.equals("Create-Default")){
             
              if(l_operation.equals("AutoAuth")&&l_versionNumber.equals("1")){
                return null;
              }else{  
               dbg("inside TeacherAttendance--->getExistingAudit--->Service--->UserAttendance");  
               RequestBody<TeacherAttendance> teacherAttendanceBody = request.getReqBody();
               TeacherAttendance teacherAttendance =teacherAttendanceBody.get();
               String l_teacherID=teacherAttendance.getTeacherID();
               String l_date=teacherAttendance.getDate();
               ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
               String l_year=convertedDate.getYear();
               String l_month=convertedDate.getMonth(); 
               String l_day=convertedDate.getDay();
               String[] l_primaryKey={l_teacherID,l_year,l_month};
               DBRecord l_teacherAttendanceRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_TEACHER_ATTENDANCE", l_primaryKey, session, dbSession);
               String previousMonthAttendance=l_teacherAttendanceRecord.getRecord().get(3).trim();
               
               getMaxVersionAttendanceOftheDay (previousMonthAttendance,l_day) ; 
               String max_version_Attendance=filtermap_dummy.get(l_day);
               
               AuditDetails audit= getAuditDetailsFromDayAttendance (max_version_Attendance)  ;            
               
               
               exAudit.setAuthStatus(audit.getAuthStatus());
               exAudit.setMakerID(audit.getMakerID());
               exAudit.setRecordStatus(audit.getRecordStatus());
               exAudit.setVersionNumber(Integer.parseInt(audit.getVersionNo()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of TeacherAttendance--->getExistingAudit") ;
        }
        }else{
            return null;
        }
        
    }catch(DBValidationException ex){
      throw ex;
     
     }catch(DBProcessingException ex){   
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
     return exAudit;
 }   
    
   
 
 public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }   
    
    private String getAttendanceFromBO(TeacherAttendance teacherAttendance)throws BSProcessingException{
        
        try{
            dbg("inside getAttendanceFromBO");
            String mergedAttendance=new String();
            for(int i=0;i<teacherAttendance.periodAttendance.length;i++){
                String periodNumber=teacherAttendance.periodAttendance[i].getPeriodNumber();
                String attendance=teacherAttendance.periodAttendance[i].getAttendance();
                String periodNoandAttendance=periodNumber.concat(attendance);
                mergedAttendance=mergedAttendance.concat("p").concat(periodNoandAttendance);
            }
            
            dbg("mergedAttendance"+mergedAttendance);
            dbg("end of getAttendanceFromBO");
            return mergedAttendance;
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
    }
    
    
     private String createMonthAttendanceRecord()throws BSProcessingException{
        
        try{
            dbg("inside createMonthAttendanceRecord");
            String monthAttendanceRecord=new String();
            BusinessService bs=inject.getBusinessService(session);
            RequestBody<TeacherAttendance> teacherAttendanceBody = request.getReqBody();
            TeacherAttendance teacherAttendance =teacherAttendanceBody.get();
            String l_date=teacherAttendance.getDate();
            ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
            String l_year=convertedDate.getYear();
            String l_month=convertedDate.getMonth();
            
            int no_of_Days=bs.getNo_of_daysInMonth(l_year, l_month);
            
            for(int i=1;i<=no_of_Days;i++){
                
                if(i==1){
                    
                    monthAttendanceRecord=monthAttendanceRecord+"d"+i+" "+"d";
                }else{    
                
                    monthAttendanceRecord=monthAttendanceRecord+i+" "+"d";
                
                }
            }
            dbg("monthAttendanceRecord "+monthAttendanceRecord);
            dbg("end of createMonthAttendanceRecord");
            return monthAttendanceRecord;
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
    }
     private Map<String,String> getMaxVersionAttendanceOftheDay(String p_monthAttendance,String p_day)throws BSProcessingException{
        
        try{
            dbg("inside getMaxVersionAttendanceOftheDay");
            dbg("p_monthAttendance"+p_monthAttendance);
            dbg("p_day"+p_day);
            String[] attendanceArray=p_monthAttendance.split("d");
            dbg("attendanceArray"+attendanceArray.length);
            ArrayList<String>recordsFor_a_day=new ArrayList();
            for(int i=1;i<attendanceArray.length;i++){
                String dayRecord=attendanceArray[i];
                dbg("dayRecord"+dayRecord);
                String l_day=null;
                
                String dayAttendance=dayRecord.split(",")[0];
         
                if(dayAttendance.contains(" ")){
                    
                    l_day=dayAttendance.split(" ")[0];
                }else if(dayAttendance.contains("p")){
                    
                    l_day=dayAttendance.split("p")[0];
                }
                
                dbg("l_day"+l_day);
                if(l_day.equals(p_day)){
                    
                    dbg("l_day.equals(p_day)");
                    recordsFor_a_day.add(dayRecord);
                }
                
            }
            dbg("recordsFor_a_day size"+recordsFor_a_day.size());
            filtermap_dummy=new HashMap();
            if(recordsFor_a_day.size()>1){
                filterkey_dummy=p_day;
                filtermap_dummy=new HashMap();
                int max_vesion=recordsFor_a_day.stream().mapToInt(rec->Integer.parseInt(rec.split(",")[7])).max().getAsInt();
                recordsFor_a_day.stream().filter(rec->Integer.parseInt(rec.split(",")[7])==max_vesion).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));           
                dbg("max_vesion"+max_vesion);
            }else{
                
                filtermap_dummy.put(p_day, recordsFor_a_day.get(0).trim());
            }
            
            dbg("end of  getMaxVersionAttendanceOftheDay");
            return filtermap_dummy;
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
    }
     

    private String dbFormatConversion(String p_day)throws BSProcessingException {
     
         try{
             dbg("inside dbFormatConversion");
            String dbFormat; 
            RequestBody<TeacherAttendance> teacherAttendanceBody = request.getReqBody();
            TeacherAttendance teacherAttendance =teacherAttendanceBody.get();
            String attendanceString=  getAttendanceFromBO(teacherAttendance);
            String l_makerID=request.getReqAudit().getMakerID();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String l_recordStatus=request.getReqAudit().getRecordStatus();
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_versionNumber=request.getReqAudit().getVersionNumber();
            String l_makerRemarks=request.getReqAudit().getMakerRemarks();
            String l_checkerRemarks=request.getReqAudit().getCheckerRemarks(); 
            
            String auditString=",".concat(l_makerID).concat(",").concat(l_checkerID).concat(",").concat(l_makerDateStamp).concat(",").concat(l_checkerDateStamp).concat(",").concat(l_recordStatus).concat(",").concat(l_authStatus).concat(",").concat(l_versionNumber).concat(",").concat(l_makerRemarks).concat(",").concat(l_checkerRemarks);
            dbg("auditString"+auditString);
            dbFormat= p_day.concat(attendanceString).concat(auditString);
            dbg("dbFormat"+dbFormat);
            
            return dbFormat;
          }catch(Exception ex){
            dbg(ex);
          throw new BSProcessingException("BSProcessingException"+ex.toString());
       }
 
}
    
private String setDayDBFromatAttendanceRecord(String monthAttendance,String dbFormatDayAttendance,String p_day,String p_operation) throws BSProcessingException {
    
    try{
        dbg("inside setDayDBFromatAttendanceRecord");
        dbg("monthAttendance"+monthAttendance);
        dbg("dbFormatDayAttendance"+dbFormatDayAttendance);
        dbg("p_day"+p_day);
        dbg("p_operation"+p_operation);
        String dbFormatMonthAttendance;
        if(p_operation.equals("C")){
            
            dbFormatMonthAttendance=monthAttendance.replace("d"+p_day+" "+"d", "d"+dbFormatDayAttendance+"d");
        }else{
        
            getMaxVersionAttendanceOftheDay (monthAttendance,p_day) ; 
            String max_version_Attendance=filtermap_dummy.get(p_day);
            dbFormatMonthAttendance=  monthAttendance.replace(max_version_Attendance+"d", dbFormatDayAttendance+"d");
        
        }
        
         dbg("dbFormatMonthAttendance"+dbFormatMonthAttendance);
         dbg("end of setDayDBFromatAttendanceRecord");
                return dbFormatMonthAttendance;

         }catch(Exception ex){
            dbg(ex);
          throw new BSProcessingException("BSProcessingException"+ex.toString());
       }
        
        
    }
    private void createDBFormatConversion(String p_previousAttendance,String p_day) throws BSProcessingException,DBValidationException,DBProcessingException  {
    
    try{
          dbg("inside createDBFormatConversion");
          dbg("p_previousAttendance"+p_previousAttendance);
          dbg("p_day"+p_day);
          IBDProperties i_db_properties=session.getCohesiveproperties();
          BusinessService bs=inject.getBusinessService(session);
          IDBTransactionService dbts=inject.getDBTransactionService();
          RequestBody<TeacherAttendance> teacherAttendanceBody = request.getReqBody();
          String l_instituteID=request.getReqHeader().getInstituteID();
          TeacherAttendance teacherAttendance =teacherAttendanceBody.get();
          String dbFormatDayAttendance=dbFormatConversion(p_day);
          String versionNumber=request.getReqAudit().getVersionNumber();
          dbg("versionNumber"+versionNumber);
          String newAttendance;
          if(versionNumber.equals("1")){
              
              newAttendance=p_previousAttendance.replace("d"+p_day+" "+"d","d"+ dbFormatDayAttendance+"d");
          }else{
          
          newAttendance=p_previousAttendance.concat(dbFormatDayAttendance+"d");
          }
          
          dbg("newAttendance"+newAttendance);
          Map<String,String>l_columnToUpdate=new HashMap();
          l_columnToUpdate.put("4", newAttendance);
           
          String l_teacherID=teacherAttendance.getTeacherID();
          String l_date=teacherAttendance.getDate();
          ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
          String l_year=convertedDate.getYear();
          String l_month=convertedDate.getMonth();
          String[] l_primaryKey={l_teacherID,l_year,l_month};
           
          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_TEACHER_ATTENDANCE", l_primaryKey, l_columnToUpdate, session);
            
          dbg("end of createDBFormatConversion");
          }catch(DBValidationException ex){
          throw ex;
         }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
         }catch(BSProcessingException ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
         }catch(Exception ex){
            dbg(ex);
          throw new BSProcessingException("BSProcessingException"+ex.toString());
       }
        
        
    }

private void updateDBFormatConversion(String p_previousAttendance,String p_day,String p_operation) throws BSProcessingException,DBValidationException,DBProcessingException  {
    
    try{
          dbg("inside updateDBFormatConversion");
          dbg("p_previousAttendance"+p_previousAttendance);
          dbg("p_day"+p_day);
          dbg("p_operation"+p_operation);
          IBDProperties i_db_properties=session.getCohesiveproperties();
          BusinessService bs=inject.getBusinessService(session);
          IDBTransactionService dbts=inject.getDBTransactionService();
          RequestBody<TeacherAttendance> teacherAttendanceBody = request.getReqBody();
          String l_instituteID=request.getReqHeader().getInstituteID();
          TeacherAttendance teacherAttendance =teacherAttendanceBody.get();
          String dbFormatDayAttendance=dbFormatConversion(p_day);
          String monthAttendance= setDayDBFromatAttendanceRecord(p_previousAttendance,dbFormatDayAttendance,p_day,p_operation);
            
          Map<String,String>l_columnToUpdate=new HashMap();
          l_columnToUpdate.put("4", monthAttendance);
           
          String l_teacherID=teacherAttendance.getTeacherID();
          String l_date=teacherAttendance.getDate();
          ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
          String l_year=convertedDate.getYear();
          String l_month=convertedDate.getMonth();
          String[] l_primaryKey={l_teacherID,l_year,l_month};
           
          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_TEACHER_ATTENDANCE", l_primaryKey, l_columnToUpdate, session);
            
        dbg("end of updateDBFormatConversion");
         }catch(DBValidationException ex){
          throw ex;
     }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(BSProcessingException ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }catch(Exception ex){
            dbg(ex);
          throw new BSProcessingException("BSProcessingException"+ex.toString());
       }
        
        
    }
    
private void deleteDBFormatConversion(String p_previousAttendance,String p_day) throws BSProcessingException,DBValidationException,DBProcessingException {
    
    try{
          dbg("inside deleteDBFormatConversion");
          dbg("p_previousAttendance"+p_previousAttendance);
          dbg("p_day"+p_day);
          IBDProperties i_db_properties=session.getCohesiveproperties();
          BusinessService bs=inject.getBusinessService(session);
          IDBTransactionService dbts=inject.getDBTransactionService();
          RequestBody<TeacherAttendance> teacherAttendanceBody = request.getReqBody();
          String l_instituteID=request.getReqHeader().getInstituteID();
          TeacherAttendance teacherAttendance =teacherAttendanceBody.get();
          getMaxVersionAttendanceOftheDay (p_previousAttendance,p_day) ; 
          String max_version_Attendance=filtermap_dummy.get(p_day);
          dbg("max_version_Attendance"+max_version_Attendance);
          String dbFormatMonthAttendance=  p_previousAttendance.replace(max_version_Attendance+"d", p_day+" "+"d");            
          dbg("dbFormatMonthAttendance"+dbFormatMonthAttendance);
          Map<String,String>l_columnToUpdate=new HashMap();
          l_columnToUpdate.put("4", dbFormatMonthAttendance);
           
          String l_teacherID=teacherAttendance.getTeacherID();
          String l_date=teacherAttendance.getDate();
          ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
          String l_year=convertedDate.getYear();
          String l_month=convertedDate.getMonth();
          String[] l_primaryKey={l_teacherID,l_year,l_month};
           
          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_TEACHER_ATTENDANCE", l_primaryKey, l_columnToUpdate, session);
            
        dbg("end of updateDBFormatConversion");
     }catch(DBValidationException ex){
          throw ex;
     }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(BSProcessingException ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
          }catch(Exception ex){
            dbg(ex);
          throw new BSProcessingException("BSProcessingException"+ex.toString());
       }
        
        
    }


private AuditDetails getAuditDetailsFromDayAttendance(String dayAttendance)throws BSProcessingException{
    
    try{
      dbg("inside getAuditDetailsFromDayAttendance");
      String[] attArr=  dayAttendance.split(",");
      AuditDetails audit =new AuditDetails();
      audit.setMakerID(attArr[1]);
      audit.setCheckerID(attArr[2]);
      audit.setMakerDateStamp(attArr[3]);
      audit.setCheckerDateStamp(attArr[4]);
      audit.setRecordStatus(attArr[5]);
      audit.setAuthStatus(attArr[6]);
      audit.setVersionNo(attArr[7]);
      audit.setMakerRemarks(attArr[8]);
      audit.setCheckerRemarks(attArr[9]);
        
      dbg("makerID"+audit.getMakerID());
      dbg("checkerID"+audit.getCheckerID());
      dbg("makerDateStamp"+audit.getMakerDateStamp());
      dbg("checkerDateStamp"+audit.getCheckerDateStamp());
      dbg("RecordStatus"+audit.getRecordStatus());
      dbg("AuthStatus"+audit.getAuthStatus());
      dbg("versionNumber"+audit.getVersionNo());
      dbg("makerRemarks"+audit.getMakerRemarks());
      dbg("checkerRemarks"+audit.getCheckerRemarks());
      
      dbg("end of getAuditDetailsFromDayAttendance");
      return audit;
    }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException("BSProcessingException"+ex.toString());
    }
    
    
    
}


public  void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     
     try{
        
        dbg("inside relationshipProcessing") ;
        
        
        if(exAudit.getRelatioshipOperation().equals("C")){

             createOrUpdateTeacherCalender();
        }else if(exAudit.getRelatioshipOperation().equals("M")){
            
             createOrUpdateTeacherCalender();
        }else if(exAudit.getRelatioshipOperation().equals("D")){
            
            deleteTeacherCalender();
        }
        
   
         dbg("end of relationshipProcessing");
         }catch(DBValidationException ex){
             throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
             throw new DBProcessingException(ex.toString());
        }catch(BSValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
 }
    
 private void createOrUpdateTeacherCalender()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
     
        try{
        dbg("inside createOrUpdateTeacherCalender") ;  
        boolean recordExistence=true;
        IDBTransactionService dbts=inject.getDBTransactionService();
        BusinessService bs=inject.getBusinessService(session);
        RequestBody<TeacherAttendance> reqBody = request.getReqBody();
        TeacherAttendance teacherAttendance =reqBody.get();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_teacherID=teacherAttendance.getTeacherID();
        String l_date=teacherAttendance.getDate();
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth();
        String l_day=convertedDate.getDay();
        int day=Integer.parseInt(l_day);
        String[] l_pkey={l_teacherID,l_year,l_month};
        DBRecord teacherCalenderRec;
        String l_previousMonthEvent=null;
        String newDayEvent=null;
        String currentEvent=getCurrentEventFromBO();
        String pKeyOfCurrentEvent=l_teacherID+","+l_year+","+l_month;
        String previousEvent=null;
        try{
        
            teacherCalenderRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_TEACHER_CALENDER", l_pkey, session, dbSession);
            l_previousMonthEvent=teacherCalenderRec.getRecord().get(3).trim();
            dbg("l_previousMonthEvent"+l_previousMonthEvent);
        }catch(DBValidationException ex){
            if(ex.toString().contains("DB_VAL_011")){
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
               recordExistence=false;                
            }
        }
        
        dbg("recordExistence"+recordExistence);
        
        if(recordExistence){
            
            String l_previousDayEvents=l_previousMonthEvent.split("d")[day];
            dbg("l_previousDayEvents"+l_previousDayEvents);
            if(l_previousDayEvents.contains("AT")){
                dbg("l_previousDayEvents.contains AT");
                String[]l_eventArray=l_previousDayEvents.split("//");
                dbg("l_eventArray size"+l_eventArray.length);
                boolean pkExistence=false;
                for(int i=1;i<l_eventArray.length;i++){
                    previousEvent=l_eventArray[i];
                    dbg("previousEvent"+previousEvent);
                    String l_pkColumnsWithoutVersion=bs.getPKcolumnswithoutVersion("TEACHER","TVW_TEACHER_ATTENDANCE",session,inject);
                    int l_lengthOfPkWithoutVersion=l_pkColumnsWithoutVersion.split("~").length;
                    String l_pkey_of_ExistingEvent=bs.getPkeyOfExistingEvent(l_lengthOfPkWithoutVersion,previousEvent);
                    dbg("l_pkey_of_ExistingEvent"+l_pkey_of_ExistingEvent);
                    if(pKeyOfCurrentEvent.equals(l_pkey_of_ExistingEvent)){
                        dbg("pKeyOfCurrentEvent.equals(l_pkey_of_ExistingEvent)");
                        pkExistence=true;
                        break;
                    }
                    
                }
                
                dbg("after for loop pk existence"+pkExistence);
                if(pkExistence){
                
                     newDayEvent=l_previousDayEvents.replace(previousEvent+"//", currentEvent);
                     dbg("newDayEvent"+newDayEvent);
                }else{
                    
                    newDayEvent=l_previousDayEvents.concat(currentEvent);
                    dbg("newDayEvent"+newDayEvent);
                }
            
            
            }else{
                dbg("l_previousDayEvents not contains AT");
                newDayEvent=l_previousDayEvents.concat(currentEvent);
            }
            
            String newMonthEvent=bs.setDayEventinMonthEvent(l_previousMonthEvent,l_previousDayEvents,newDayEvent,l_day);
            Map<String,String>l_column_to_update=new HashMap();
            l_column_to_update.put("4", newMonthEvent);
             
             dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_TEACHER_CALENDER", l_pkey, l_column_to_update, session);   
            

        }else{
            String l_monthEvent=bs.createMonthEvent(l_date);
            dbg(l_day+"//"+" "+"d");
            dbg(l_day+"//"+currentEvent+"d");
            
            newDayEvent =l_monthEvent.replace("d"+l_day+"//"+" "+"d", "d"+l_day+"//"+currentEvent+"d");
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER",74,l_teacherID,l_year,l_month,newDayEvent);
        }
        
        
        
        
          
        dbg("end of createOrUpdateTeacherCalender");  
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
          
 }   
    
 private void deleteTeacherCalender()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
     
        try{
        dbg("inside deleteTeacherCalender");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        BusinessService bs=inject.getBusinessService(session);
        RequestBody<TeacherAttendance> reqBody = request.getReqBody();
        TeacherAttendance teacherAttendance =reqBody.get();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_teacherID=teacherAttendance.getTeacherID();
        String l_date=teacherAttendance.getDate();
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth();
        String l_day=convertedDate.getDay();
        int day=Integer.parseInt(l_day);
        String[] l_pkey={l_teacherID,l_year,l_month};
        DBRecord teacherCalenderRec;
        String l_previousMonthEvent=null;
        String newDayEvent=null;
        
        String currentEvent=getCurrentEventFromBO();

        
            teacherCalenderRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_TEACHER_CALENDER", l_pkey, session, dbSession);
            l_previousMonthEvent=teacherCalenderRec.getRecord().get(3).trim();
            dbg("l_previousMonthEvent"+l_previousMonthEvent);
            String l_previousDayEvents=l_previousMonthEvent.split("d")[day];
            dbg("l_previousDayEvents"+l_previousDayEvents);
            
            if(l_previousDayEvents.split("//").length==2){
               newDayEvent=l_previousDayEvents.replace(currentEvent, " ");
            
            }else{
                
                newDayEvent=l_previousDayEvents.replace(currentEvent, "");
            } 
            String newMonthEvent=bs.setDayEventinMonthEvent(l_previousMonthEvent,l_previousDayEvents,newDayEvent,l_day);
            
            Map<String,String>l_column_to_update=new HashMap();
            l_column_to_update.put("4", newMonthEvent);
             
            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_TEACHER_CALENDER", l_pkey, l_column_to_update, session);   
            

        
        
        
        
        
          
       dbg("inside deleteTeacherCalender");
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
          
 }   
 

 
 private String getCurrentEventFromBO()throws BSProcessingException{
     
     try{
         
        dbg("inside TeacherAttendance--->getCurrentEventFromBO");
        BusinessService bs=inject.getBusinessService(session);
        RequestBody<TeacherAttendance> reqBody = request.getReqBody();
        TeacherAttendance teacherAttendance =reqBody.get(); 
        String l_teacherID=teacherAttendance.getTeacherID();
        String l_date=teacherAttendance.getDate();
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth();
        DayAttendance dayAttendance=calculateAttendanceForaDay();
        String present=Float.toString(dayAttendance.getPresent());
        String absent=Float.toString(dayAttendance.getAbsent());
        String leave=Float.toString(dayAttendance.getLeave());
        
        String currentEvent="AT"+","+l_teacherID+","+l_year+","+l_month+","+"P"+present+","+"A"+absent+","+"L"+leave+"//";
         
        dbg("currentEvent"+currentEvent); 
        return  currentEvent;
     }catch (Exception ex) {
       dbg(ex);
       throw new BSProcessingException("Exception" + ex.toString());
     }
     
 }

     private DayAttendance calculateAttendanceForaDay()throws BSProcessingException{
         
         try{
             RequestBody<TeacherAttendance> reqBody = request.getReqBody();
             TeacherAttendance teacherAttendance =reqBody.get(); 
             DayAttendance dayAtt=new DayAttendance();   
             int halfDayAttendanceNoOfPeroids = Integer.parseInt( session.getCohesiveproperties().getProperty("HALFDAY_ATTENDANCE_NO_OF_PERIODS")) ;
             int fullDayAttendanceNoOfPeroids = Integer.parseInt( session.getCohesiveproperties().getProperty("FULLDAY_ATTENDANCE_NO_OF_PERIODS")) ;
             int presentPeriodCount=0;
             int absentPeriodCount=0;
             int leavePeriodCount=0;
             for(int i=0;i<teacherAttendance.periodAttendance.length;i++){
                 
                 String l_attendance=teacherAttendance.periodAttendance[i].getAttendance();
                 
                 if(l_attendance.equals("P")){
                  
                  presentPeriodCount++;
                }else if(l_attendance.equals("A")){
                  
                  absentPeriodCount++;
                }else if(l_attendance.equals("L")){
                  
                  leavePeriodCount++;
              }        
                 
             }
             
             if(leavePeriodCount>halfDayAttendanceNoOfPeroids){
              
              dayAtt.setAbsent(0);
              dayAtt.setLeave(1);
              dayAtt.setPresent(0);
              
              return dayAtt;
          }
          
          if(absentPeriodCount>halfDayAttendanceNoOfPeroids){
              
              dayAtt.setAbsent(1);
              dayAtt.setLeave(0);
              dayAtt.setPresent(0);
              return dayAtt;
              
          }
          
          if(presentPeriodCount==fullDayAttendanceNoOfPeroids){
              
              dayAtt.setAbsent(0);
              dayAtt.setLeave(0);
              dayAtt.setPresent(1);
              
              return dayAtt;
          }
          
          
          if(presentPeriodCount>=halfDayAttendanceNoOfPeroids){
              
            dayAtt.setPresent(0.5f);
              
              if(absentPeriodCount<halfDayAttendanceNoOfPeroids){
                  
                  dayAtt.setAbsent(0.5f);
                  dayAtt.setLeave(0);
                  
                  
                  return dayAtt;
              }else if(leavePeriodCount<halfDayAttendanceNoOfPeroids){
                  
                  dayAtt.setAbsent(0);
                  dayAtt.setLeave(0.5f);
                  
                  return dayAtt;
              }
              
          }
         
          
          
                  return dayAtt;
             
         }catch (Exception ex) {
          dbg(ex);
          throw new BSProcessingException("Exception" + ex.toString());
     }
         
         
     }
     
     
 
    
    
}
