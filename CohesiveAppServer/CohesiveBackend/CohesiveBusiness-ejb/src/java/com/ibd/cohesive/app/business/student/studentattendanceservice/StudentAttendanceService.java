/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentattendanceservice;

import com.ibd.businessViews.IStudentAttendanceService;
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
import java.util.ArrayList;
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
 * @author IBD Technologies
 */
//@Local(IStudentAttendanceService.class)
@Remote(IStudentAttendanceService.class)
@Stateless
public class StudentAttendanceService implements  IStudentAttendanceService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    Map<String,String>filtermap_dummy;
    String filterkey_dummy;
    Map<String,String>audit_filtermap_dummy;
    String audit_filterkey_dummy;
    public StudentAttendanceService(){
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
       dbg("inside StudentAttendanceService--->processing");
       dbg("StudentAttendanceService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<StudentAttendance> reqBody = request.getReqBody();
       StudentAttendance studentAttendance =reqBody.get();
       String l_studentID=studentAttendance.getStudentID();
       String l_date=studentAttendance.getDate();
       ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
       String l_year=convertedDate.getYear();
       String l_month=convertedDate.getMonth();
       l_lockKey=l_studentID.concat("~").concat(l_year).concat("~").concat(l_month);
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<IStudentAttendanceService>studentAttendanceEJB=new BusinessEJB();
       studentAttendanceEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentAttendanceEJB);
       
       if(request.getReqHeader().getOperation().equals("View")){
           
         String studentID=  bs.studentValidation(studentAttendance.getStudentID(), studentAttendance.getStudentName(), request.getReqHeader().getInstituteID(), session, dbSession, inject);
         
          
         if(studentID==null||studentID.isEmpty()){
             
             errhandler.log_app_error("BS_VAL_002","Student ID or Name");  
             throw new BSValidationException("BSValidationException");
         }
         
         studentAttendance.setStudentID(studentID);
         bs.setStudentIDInBusinessEntity(studentID, request);
           
       }
       
       
       
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,studentAttendanceEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentAttendanceEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student attendance");
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
                //if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                  // BSProcessingException ex=new BSProcessingException("response contains special characters");
                   clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);
            
                  //dbg(ex);
                   //session.clearSessionObject();
                   //dbSession.clearSessionObject();
                //   throw ex;
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
      StudentAttendance studentAttendance=new StudentAttendance();
      RequestBody<StudentAttendance> reqBody = new RequestBody<StudentAttendance>(); 
           
      try{
      dbg("inside student attendance buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      studentAttendance.setStudentID(l_body.getString("studentID"));
      studentAttendance.setStudentName(l_body.getString("studentName"));
      
      studentAttendance.setDate(l_body.getString("date"));
      if(!l_operation.equals("View")){
//          JsonArray foreNoonArray=l_body.getJsonArray("foreNoon");
//          JsonArray afterNoonArray=l_body.getJsonArray("afterNoon");
//
//          studentAttendance.periodAttendance=new  PeriodAttendance[foreNoonArray.size()+afterNoonArray.size()];
//          
//          for(int i=0;i<foreNoonArray.size();i++){
//              JsonObject l_periodObject=foreNoonArray.getJsonObject(i);
//              studentAttendance.periodAttendance[i]=new PeriodAttendance();
//              studentAttendance.periodAttendance[i].setPeriodNumber(l_periodObject.getString("periodNumber"));
//              studentAttendance.periodAttendance[i].setAttendance(l_periodObject.getString("attendance"));
//          }
//          
//          
//          for(int j=0;j<afterNoonArray.size();j++){
//              JsonObject l_periodObject=afterNoonArray.getJsonObject(j);
//              studentAttendance.periodAttendance[foreNoonArray.size()+j]=new PeriodAttendance();
//              studentAttendance.periodAttendance[foreNoonArray.size()+j].setPeriodNumber(l_periodObject.getString("periodNumber"));
//              studentAttendance.periodAttendance[foreNoonArray.size()+j].setAttendance(l_periodObject.getString("attendance"));
//          }
      }
        reqBody.set(studentAttendance);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
//        
//    try{ 
//        dbg("inside stident attendance create"); 
//        boolean recordExistence=true;
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        ErrorHandler errHandler=session.getErrorhandler();
//        BusinessService bs=inject.getBusinessService(session);
//        RequestBody<StudentAttendance> reqBody = request.getReqBody();
//        StudentAttendance studentAttendance =reqBody.get();
//        IDBTransactionService dbts=inject.getDBTransactionService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        String l_studentID=studentAttendance.getStudentID();
//        String l_date=studentAttendance.getDate();
//        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//        String l_year=convertedDate.getYear();
//        String l_month=convertedDate.getMonth();
//        String l_day=convertedDate.getDay();
//        String dbFormatDayAttendance=dbFormatConversion(l_day);
//        String previousMonthAttendance=null;
//        String[] l_primaryKey={l_studentID,l_year,l_month};
//        try{
//        DBRecord attendanceRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_ATTENDANCE", l_primaryKey, session, dbSession);
//        previousMonthAttendance=attendanceRecord.getRecord().get(3).trim();
//
//
//            
//        }catch(DBValidationException ex){ 
//                 if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
//                     errHandler.removeSessionErrCode("DB_VAL_011");
//                     errHandler.removeSessionErrCode("DB_VAL_000");
//                     recordExistence=false;
//                 }else{
//                     throw ex;
//                 }
//             }
//       if(recordExistence) {
//           createDBFormatConversion(previousMonthAttendance,l_day);
//       }else{
//          String monthAttendance= createMonthAttendanceRecord();
//          monthAttendance= setDayDBFromatAttendanceRecord(monthAttendance,dbFormatDayAttendance,l_day,"C");
//          dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",41,l_studentID,l_year,l_month,monthAttendance);
//       }
//        
//        dbg("end of student attendance create"); 
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//            
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception".concat(ex.toString()));
//        }
    }
    
     

    
    public void authUpdate()throws DBValidationException,DBProcessingException,BSProcessingException{
//        
//     try{ 
//        dbg("inside student attendance--->auth update");
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        BusinessService bs=inject.getBusinessService(session);
//        IDBTransactionService dbts=inject.getDBTransactionService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        RequestBody<StudentAttendance> reqBody = request.getReqBody();
//        StudentAttendance studentAttendance =reqBody.get();
//        String l_studentID=studentAttendance.getStudentID();
//        String l_date=studentAttendance.getDate();
//        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//        String l_year=convertedDate.getYear();
//        String l_month=convertedDate.getMonth(); 
//        String l_day=convertedDate.getDay();
//        String[] l_primaryKey={l_studentID,l_year,l_month};
//        
//        DBRecord attendanceRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_ATTENDANCE", l_primaryKey, session, dbSession);
//        String previousAttendance=attendanceRecord.getRecord().get(3).trim();
//        updateDBFormatConversion(previousAttendance,l_day,"U");
//        
//        
//         dbg("end of student attendance--->auth update");          
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//             throw new DBProcessingException(ex.toString());
//            
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//     
//        }
        
       }
    public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
//        
//       try{ 
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();   
//        BusinessService bs=inject.getBusinessService(session);   
//        IDBTransactionService dbts=inject.getDBTransactionService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        RequestBody<StudentAttendance> reqBody = request.getReqBody();
//        StudentAttendance studentAttendance =reqBody.get();
//        String l_instituteID=request.getReqHeader().getInstituteID();   
//        String l_studentID=studentAttendance.getStudentID();
//        String l_date=studentAttendance.getDate();
//        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//        String l_year=convertedDate.getYear();
//        String l_month=convertedDate.getMonth(); 
//        String l_day=convertedDate.getDay();
//        String monthAttendance;
//        String[] l_primaryKey={l_studentID,l_year,l_month};
//        
//        DBRecord attendanceRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_ATTENDANCE", l_primaryKey, session, dbSession);
//        monthAttendance=attendanceRecord.getRecord().get(3).trim();
//        
//        updateDBFormatConversion(monthAttendance,l_day,"U");
//                  
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//            
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception".concat(ex.toString()));
//        }
    }

    
    public void delete()throws DBValidationException,DBProcessingException,BSProcessingException{
//        try{
//        dbg("inside student attendance delete");    
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService(); 
//        BusinessService bs=inject.getBusinessService(session); 
//        IDBTransactionService dbts=inject.getDBTransactionService();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        RequestBody<StudentAttendance> reqBody = request.getReqBody();
//        StudentAttendance studentAttendance =reqBody.get();
//        String l_studentID=studentAttendance.getStudentID();
//        String l_date=studentAttendance.getDate();
//        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//        String l_year=convertedDate.getYear();
//        String l_month=convertedDate.getMonth(); 
//        String l_day=convertedDate.getDay();
//        String[] l_primaryKey={l_studentID,l_year,l_month};
//        String previousAttendance;
//        
//        DBRecord attendanceRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_ATTENDANCE", l_primaryKey, session, dbSession);
//        previousAttendance=attendanceRecord.getRecord().get(3).trim();
//        deleteDBFormatConversion(previousAttendance,l_day);
//        
//            dbg("end of student attendance delete");
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//             throw new DBProcessingException(ex.toString());
//            
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//     
//        }
        
       }

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student attendance--->view");    
        BusinessService bs=inject.getBusinessService(session); 
        IPDataService pds=inject.getPdataservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentAttendance> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        StudentAttendance studentAttendance =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_studentID=studentAttendance.getStudentID();
        String l_date=studentAttendance.getDate();
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth(); 
        DBRecord masterRecord=null;
        Map<String,DBRecord>l_attendanceMap=null;
        String l_referenceID=null;
         AttendanceDetails attendanceDetail=new AttendanceDetails();
        try{
        
           String[] pkey={l_studentID};
           ArrayList<String>studentList=    pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",pkey);
           String l_standard=studentList.get(2).trim();
           String l_section=studentList.get(3).trim();
           String[] l_masterPkey={l_standard,l_section,l_year,l_month};
           masterRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS", "CLASS_ATTENDANCE_MASTER", l_masterPkey, session, dbSession);
           l_referenceID=l_standard+"*"+l_section+"*"+l_year+"*"+l_month;
           String[] l_pkey={l_instituteID,l_standard,l_section};
           ArrayList<String>classConfigList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
           String attendancetype=classConfigList.get(13).trim();
           
          
           attendanceDetail.setStandard(l_standard);
           attendanceDetail.setSection(l_section);
           attendanceDetail.setAttendanceType(attendancetype);
           attendanceDetail.setReferenceID(l_referenceID);
           
           l_attendanceMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS", "CLASS_ATTENDANCE_DETAIL", session, dbSession);
           
         }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", l_date);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
        
        
        
        
        
        
        
        buildBOfromDB(masterRecord,l_attendanceMap,attendanceDetail);
        
          dbg("end of  completed student attendance--->view");  
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
      private void buildBOfromDB(DBRecord p_masterRecord,Map<String,DBRecord>l_attendanceMap,AttendanceDetails attendanceDetail)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
//           IPDataService pds=inject.getPdataservice();
//           IBDProperties i_db_properties=session.getCohesiveproperties();
           String l_instituteID=request.getReqHeader().getInstituteID();
           BusinessService bs=inject.getBusinessService(session);
           RequestBody<StudentAttendance> reqBody = request.getReqBody();
           StudentAttendance studentAttendance =reqBody.get();
           String l_studentID=studentAttendance.getStudentID();
           ArrayList<String>l_masterList= p_masterRecord.getRecord();
           String monthAudit=l_masterList.get(4).trim();
           String l_date=studentAttendance.getDate();
           ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
           String l_day=convertedDate.getDay();
//           String[] pkey={l_studentID};
//           ArrayList<String>studentList=    pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",pkey);
//           String l_standard=attendanceDetail.getStandard();
//           String l_section=attendanceDetail.getSection();
//           String[] l_pkey={l_instituteID,l_standard,l_section};
//           ArrayList<String>classConfigList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
           String attendancetype=attendanceDetail.getAttendanceType();
           AuditDetails audit=getAuditDetails(monthAudit,l_day);
           request.getReqAudit().setMakerID(audit.getMakerID());
           request.getReqAudit().setCheckerID(audit.getCheckerID());
           request.getReqAudit().setMakerDateStamp(audit.getMakerDateStamp());
           request.getReqAudit().setCheckerDateStamp(audit.getCheckerDateStamp());
           request.getReqAudit().setRecordStatus(audit.getRecordStatus());
           request.getReqAudit().setAuthStatus(audit.getAuthStatus());
           request.getReqAudit().setVersionNumber(audit.getVersionNo());
           request.getReqAudit().setMakerRemarks(audit.getMakerRemarks());
           request.getReqAudit().setCheckerRemarks(audit.getCheckerRemarks());
           studentAttendance.setStudentName(bs.getStudentName(l_studentID, l_instituteID, session, dbSession, inject));
           
           
            Map<String,Map<String,String[]>>l_parsedMap=parseAttendanceFromBO(l_attendanceMap,l_day,attendanceDetail);
           
           Map<String,String[]>l_foreNoonMap=l_parsedMap.get("ForeNoon");
           dbg("l_foreNoonMap size"+l_foreNoonMap.size());
           
           dbg("fore noon iteration starts");

               String[] periodArray=l_foreNoonMap.get(l_studentID);
               dbg("periodArray length"+periodArray.length);
               
            studentAttendance.foreNoon=new PeriodAttendance[periodArray.length-1];
               
               for(int j=1;j<periodArray.length;j++){
                   studentAttendance.foreNoon[j-1]=new PeriodAttendance();
                   String periodNumber=null;
                   String attendance=null;
                   if(attendancetype.equals("P")){
                   
                       periodNumber=periodArray[j].substring(0, 1);
                       attendance=periodArray[j].substring(1);
                   
                   }else{
                       
                       periodNumber="";
                       attendance=periodArray[j].substring(0, 1);
                   }
                   
                   dbg("periodNumber"+periodNumber);
                   dbg("attendance"+attendance);
                   studentAttendance.foreNoon[j-1].setPeriodNumber(periodNumber);
                   studentAttendance.foreNoon[j-1].setAttendance(attendance);
                   
               }
              
           
           
          if(!attendancetype.equals("D")){
            Map<String,String[]>l_afterNoonMap=l_parsedMap.get("AfterNoon");
            periodArray=l_afterNoonMap.get(l_studentID);
               dbg("periodArray length"+periodArray.length);
               
            studentAttendance.afterNoon=new PeriodAttendance[periodArray.length-1];
               
               for(int j=1;j<periodArray.length;j++){
                   studentAttendance.afterNoon[j-1]=new PeriodAttendance();
                   String periodNumber=null;
                   String attendance=null;
                   if(attendancetype.equals("P")){
                   
                       periodNumber=periodArray[j].substring(0, 1);
                       attendance=periodArray[j].substring(1);
                   
                   }else{
                       
                       periodNumber="";
                       attendance=periodArray[j].substring(0, 1);
                   }
                   
                   dbg("periodNumber"+periodNumber);
                   dbg("attendance"+attendance);
                   studentAttendance.afterNoon[j-1].setPeriodNumber(periodNumber);
                   studentAttendance.afterNoon[j-1].setAttendance(attendance);
                   
               }
           dbg("after noon iteration ends");
        
            }
        }catch(DBValidationException ex){
            
           throw ex;
        }catch(BSValidationException ex){
            
           throw ex;
         }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());   
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
 }
 
 private Map<String,Map<String,String[]>> parseAttendanceFromBO(Map<String,DBRecord>l_attendanceMap,String l_day,AttendanceDetails attendanceDetails)throws BSProcessingException{
          
      try{
          Map<String,Map<String,String[]>>parsedMap=new HashMap();
          Map<String,List<DBRecord>> studentWiseGroup=l_attendanceMap.values().stream().filter(rec->rec.getRecord().get(0).trim().equals(attendanceDetails.getReferenceID())).collect(Collectors.groupingBy(rec->rec.getRecord().get(1).trim()));
          dbg("studentWiseGroup size"+studentWiseGroup.size());
          Iterator<String>studentIterator=studentWiseGroup.keySet().iterator();
          Map<String,String[]>studentForeNoonMap=new HashMap();
           Map<String,String[]>studentAfterNoonMap=new HashMap();
//           IPDataService pds=inject.getPdataservice();
//           IBDProperties i_db_properties=session.getCohesiveproperties();
//           String l_instituteID=request.getReqHeader().getInstituteID();
//           RequestBody<StudentAttendance> reqBody = request.getReqBody();
//           StudentAttendance studentAttendance =reqBody.get();
//           String studentID=studentAttendance.getStudentID();
//           String[] pkey={studentID};
//           ArrayList<String>studentList=    pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",pkey);
//           String l_standard=studentList.get(2).trim();
//           String l_section=studentList.get(3).trim();
//           String[] l_pkey={l_instituteID,l_standard,l_section};
//           ArrayList<String>classConfigList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
           String attendancetype=attendanceDetails.getAttendanceType();
           while(studentIterator.hasNext()){
                   
                    String l_studentID=studentIterator.next();
                    dbg("l_studentID"+l_studentID);
                    ArrayList<String>l_studentAttendanceList=studentWiseGroup.get(l_studentID).get(0).getRecord();
                    String monthAttendance=l_studentAttendanceList.get(2).trim();
                    dbg("monthAttendance"+monthAttendance);
                    getMaxVersionAttendanceOftheDay (monthAttendance,l_day) ; 
                    String max_version_Attendance=filtermap_dummy.get(l_day);
                    dbg("max_version_Attendance"+max_version_Attendance);
                    String[] dayArray=max_version_Attendance.split(",");
                    String attendanceRecord= dayArray[0];
                    dbg("attendanceRecord"+attendanceRecord);
                    String l_foreNoonAttendance=attendanceRecord.split("n")[0];
                    dbg("l_foreNoonAttendance"+l_foreNoonAttendance);
                    
                    String[] foreNoonArray=  l_foreNoonAttendance.split("p");
                    studentForeNoonMap.put(l_studentID, foreNoonArray);
                    
                    if(!attendancetype.equals("D")){
                    
                        String l_afterNoonAttendance=attendanceRecord.split("n")[1];
                        dbg("l_afterNoonAttendance"+l_afterNoonAttendance);
                        String[] afterNoonArray=  l_afterNoonAttendance.split("p");
                        studentAfterNoonMap.put(l_studentID, afterNoonArray);
                    
                    }
                    
                    dbg("foreNoonArray size"+foreNoonArray.length);
                    dbg("period Iteration starts");

                  }
                  Iterator<String>foreNoonIterator=studentForeNoonMap.keySet().iterator();
           
                  while(foreNoonIterator.hasNext()){
                      
                      String l_studentID=foreNoonIterator.next();
                       dbg("fore noon l_studentID"+l_studentID);
                      String[] periodArray=studentForeNoonMap.get(l_studentID);
                      
                      for(int i=0;i<periodArray.length;i++){
                          
                          String periodNo_attendance=periodArray[i];
                          dbg("foreNoon periodNo_attendance"+periodNo_attendance);
                      }
                      
                  }
                  
                  
                   if(!attendancetype.equals("D")){
                  
                  Iterator<String>afterNoonIterator=studentAfterNoonMap.keySet().iterator();
                  
                  while(afterNoonIterator.hasNext()){
                      
                      String l_studentID=afterNoonIterator.next();
                      dbg("after noon l_studentID"+l_studentID);
                      String[] periodArray=studentAfterNoonMap.get(l_studentID);
                      
                      for(int i=0;i<periodArray.length;i++){
                          
                          String periodNo_attendance=periodArray[i];
                          dbg("after noon periodNo_attendance"+periodNo_attendance);
                      }
                      
                  }
                  
                   }
           
                   parsedMap.put("ForeNoon", studentForeNoonMap);
                   parsedMap.put("AfterNoon", studentAfterNoonMap);
          
          return parsedMap;
  
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
          
          
      }      
      
      private AuditDetails getAuditDetails(String monthAudit,String p_day)throws BSProcessingException,BSValidationException{
    
    try{
      dbg("inside getAuditDetails");
      getMaxVersionAuditOftheDay(monthAudit,p_day);
      String existingDayAudit=audit_filtermap_dummy.get(p_day);
      String[] attArr=  existingDayAudit.split(",");
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
      
      dbg("end of getAuditDetails");
      return audit;
      
     }catch(BSValidationException ex){
          throw ex;
    }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException("BSProcessingException"+ex.toString());
    }
    
    
    
}
       private Map<String,String> getMaxVersionAuditOftheDay(String p_monthAudit,String p_day)throws BSProcessingException,BSValidationException{
        
        try{
            dbg("inside getMaxVersionAttendanceOftheDay");
            dbg("p_monthAttendance"+p_monthAudit);
            dbg("p_day"+p_day);
            String[] attendanceArray=p_monthAudit.split("d");
            dbg("attendanceArray"+attendanceArray.length);
            ArrayList<String>recordsFor_a_day=new ArrayList();
            for(int i=1;i<attendanceArray.length;i++){
                String dayRecord=attendanceArray[i];
                dbg("dayRecord"+dayRecord);
                String l_day=null;
                if(dayRecord.contains(",")){
                
                  l_day=dayRecord.split(",")[0];
         
                }else{
                    
                  l_day=dayRecord.trim();
                }
                
                dbg("l_day"+l_day);
                if(l_day.equals(p_day)){
                    
                    dbg("l_day.equals(p_day)");
                    
                    if(dayRecord.contains(",")){
                    
                        dbg("dayRecord.contains ,");
                        
                       recordsFor_a_day.add(dayRecord);
                    
                    
                    }
                }
                
            }
            dbg("recordsFor_a_day size"+recordsFor_a_day.size());
            
            if(recordsFor_a_day.isEmpty()){
                
                session.getErrorhandler().log_app_error("BS_VAL_013", null);
                throw new BSValidationException("BSValidationException");
            }
            
            
            audit_filtermap_dummy=new HashMap();
            if(recordsFor_a_day.size()>1){
                audit_filterkey_dummy=p_day;
                audit_filtermap_dummy=new HashMap();
                int max_vesion=recordsFor_a_day.stream().mapToInt(rec->Integer.parseInt(rec.split(",")[7])).max().getAsInt();
                recordsFor_a_day.stream().filter(rec->Integer.parseInt(rec.split(",")[7])==max_vesion).forEach(rec->audit_filtermap_dummy.put(audit_filterkey_dummy, rec));           
                dbg("max_vesion"+max_vesion);
            }else{
                
                audit_filtermap_dummy.put(p_day, recordsFor_a_day.get(0));
            }
            
            dbg("end of  getMaxVersionAttendanceOftheDay");
            return audit_filtermap_dummy;
            
        }catch(BSValidationException ex){
            throw ex;    
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
    }
    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside student attendance buildJsonResFromBO");   
        IPDataService pds=inject.getPdataservice();
        RequestBody<StudentAttendance> reqBody = request.getReqBody();
        StudentAttendance studentAttendance =reqBody.get();
        JsonArrayBuilder foreNoonAttendance=Json.createArrayBuilder();
        JsonArrayBuilder afterNoonAttendance=Json.createArrayBuilder();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        String l_studentID=studentAttendance.getStudentID();
        
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        String[] pkey={l_studentID};
//        ArrayList<String>l_studentList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",pkey);
//        String l_standard=l_studentList.get(2).trim();
//        String l_section=l_studentList.get(3).trim();
//        dbg("standard"+l_standard);
//        dbg("section"+l_section);
           
       
        
        
        for(int i=0;i<studentAttendance.foreNoon.length;i++){
            

            
               foreNoonAttendance.add(Json.createObjectBuilder().add("periodNumber", studentAttendance.foreNoon[i].getPeriodNumber())
                                                                .add("attendance", studentAttendance.foreNoon[i].getAttendance()));
            
              
        }
        
        if(studentAttendance.afterNoon!=null){
        
        for(int i=0;i<studentAttendance.afterNoon.length;i++){
            

            
               afterNoonAttendance.add(Json.createObjectBuilder().add("periodNumber", studentAttendance.afterNoon[i].getPeriodNumber())
                                                                .add("attendance", studentAttendance.afterNoon[i].getAttendance()));
            
              
        }
        
        }
        
        body=Json.createObjectBuilder().add("studentID", studentAttendance.getStudentID())
                                       .add("studentName", studentAttendance.getStudentName())
                                       .add("date", studentAttendance.getDate())
                                       .add("foreNoon",foreNoonAttendance)
                                       .add("afterNoon",afterNoonAttendance)
                                       .build();
                                            
              dbg(body.toString());  
           dbg("end of student attendance buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student attendance--->businessValidation");    
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
       dbg("end of student attendance--->businessValidation"); 
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
        dbg("inside student attendance master mandatory validation");
        RequestBody<StudentAttendance> reqBody = request.getReqBody();
        StudentAttendance studentAttendance =reqBody.get();
        
         if(studentAttendance.getStudentID()==null||studentAttendance.getStudentID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","studentID");  
         }
          if(studentAttendance.getDate()==null||studentAttendance.getDate().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","date");  
         }
          
          
        dbg("end of student attendance master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student attendance masterDataValidation");
             RequestBody<StudentAttendance> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             StudentAttendance studentAttendance =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_studentID=studentAttendance.getStudentID();
             String l_date=studentAttendance.getDate();
             
             if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","studentID");
             }
             if(!bsv.dateFormatValidation(l_date, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Date");
             }
//             if(!bsv.pastDateValidation(l_date, session, dbSession, inject)){
//                 status=false;
//                 errhandler.log_app_error("BS_VAL_003","Date");
//             }
            
            dbg("end of student attendance masterDataValidation");
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
        RequestBody<StudentAttendance> reqBody = request.getReqBody();
        StudentAttendance studentAttendance =reqBody.get();
        
        try{
            
            dbg("inside student attendance detailMandatoryValidation");
           
//            if(studentAttendance.getPeriodAttendance()==null||studentAttendance.getPeriodAttendance().length==0){
//               status=false;  
//               errhandler.log_app_error("BS_VAL_002","Period Attendance");  
//            }else{
//                
//                for(int i=0;i<studentAttendance.getPeriodAttendance().length;i++){
//                    
//                    if(studentAttendance.getPeriodAttendance()[i].getPeriodNumber()==null||studentAttendance.getPeriodAttendance()[i].getPeriodNumber().isEmpty()){
//                       status=false;  
//                       errhandler.log_app_error("BS_VAL_002","periodNumber");  
//                    }
//                    if(studentAttendance.getPeriodAttendance()[i].getAttendance()==null||studentAttendance.getPeriodAttendance()[i].getAttendance().isEmpty()){
//                       status=false;  
//                       errhandler.log_app_error("BS_VAL_002","attendance");  
//                    }
//                    
//                }
//                
//              
//            }
          
            
           dbg("end of student attendance detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student attendance detailDataValidation");
             RequestBody<StudentAttendance> reqBody = request.getReqBody();
             StudentAttendance studentAttendance =reqBody.get();
             BSValidation bsv=inject.getBsv(session);
////             int periodSize=studentAttendance.getPeriodAttendance().length;
////             
////             if(!bsv.periodSizeValidation(periodSize, session, dbSession, inject)){
////                 status=false;
////                 errhandler.log_app_error("BS_VAL_003","Period Size");
////             }
////             
////             
////             for(int i=0;i<studentAttendance.getPeriodAttendance().length;i++){
////                 
////                 String l_periodNo=studentAttendance.getPeriodAttendance()[i].getPeriodNumber();
////                 String l_attendance=studentAttendance.getPeriodAttendance()[i].getAttendance();
////                 
////                 if(!bsv.periodNumberValidation(l_periodNo, session, dbSession, inject)){
////                     status=false;
////                    errhandler.log_app_error("BS_VAL_003","PeriodNo");
////                 }
////                 if(!bsv.attendanceCharachterValidation(l_attendance, session, dbSession, inject)){
////                     status=false;
////                    errhandler.log_app_error("BS_VAL_003","Attendance");
////                 }
////             }
////             
//
//            dbg("end of student attendance detailDataValidation");
//        } catch (DBProcessingException ex) {
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException" + ex.toString());
//        } catch(DBValidationException ex){
//            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        
        return status;
              
    }
    public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     try{
        dbg("inside StudentAttendance--->getExistingAudit") ;
        exAudit=new ExistingAudit();
//        BusinessService bs=inject.getBusinessService(session);
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        String l_operation=request.getReqHeader().getOperation();
//        String l_service=request.getReqHeader().getService();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        String l_versionNumber=request.getReqAudit().getVersionNumber();
//        dbg("l_operation"+l_operation);
//        dbg("l_service"+l_service);
//        dbg("l_instituteID"+l_instituteID);
//        if(!(l_operation.equals("Create")||l_operation.equals("View"))){
//             
//              if(l_operation.equals("AutoAuth")&&l_versionNumber.equals("1")){
//                return null;
//              }else{  
////               dbg("inside StudentAttendance--->getExistingAudit--->Service--->UserAttendance");  
////               RequestBody<StudentAttendance> studentAttendanceBody = request.getReqBody();
////               StudentAttendance studentAttendance =studentAttendanceBody.get();
////               String l_studentID=studentAttendance.getStudentID();
////               String l_date=studentAttendance.getDate();
////               ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
////               String l_year=convertedDate.getYear();
////               String l_month=convertedDate.getMonth(); 
////               String l_day=convertedDate.getDay();
////               String[] l_primaryKey={l_studentID,l_year,l_month};
////               DBRecord l_studentAttendanceRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_ATTENDANCE", l_primaryKey, session, dbSession);
////               String previousMonthAttendance=l_studentAttendanceRecord.getRecord().get(3).trim();
////               
////               getMaxVersionAttendanceOftheDay (previousMonthAttendance,l_day) ; 
////               String max_version_Attendance=filtermap_dummy.get(l_day);
////               
////               AuditDetails audit= getAuditDetailsFromDayAttendance (max_version_Attendance)  ;            
////               
////               
////               exAudit.setAuthStatus(audit.getAuthStatus());
////               exAudit.setMakerID(audit.getMakerID());
////               exAudit.setRecordStatus(audit.getRecordStatus());
////               exAudit.setVersionNumber(Integer.parseInt(audit.getVersionNo()));
//
// 
//         dbg("exAuthStatus"+exAudit.getAuthStatus());
//         dbg("exMakerID"+exAudit.getMakerID());
//         dbg("exRecordStatus"+exAudit.getRecordStatus());
//         dbg("exVersionNumber"+exAudit.getVersionNumber());
//        
//        dbg("end of StudentAttendance--->getExistingAudit") ;
//        }
//        }else{
//            return null;
//        }
//        
//    }catch(DBValidationException ex){
//      throw ex;
//     
//     }catch(DBProcessingException ex){   
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
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
    
//    private String getAttendanceFromBO(StudentAttendance studentAttendance)throws BSProcessingException{
//        
//        try{
//            dbg("inside getAttendanceFromBO");
//            String mergedAttendance=new String();
//            for(int i=0;i<studentAttendance.periodAttendance.length;i++){
//                String periodNumber=studentAttendance.periodAttendance[i].getPeriodNumber();
//                String attendance=studentAttendance.periodAttendance[i].getAttendance();
//                String periodNoandAttendance=periodNumber.concat(attendance);
//                mergedAttendance=mergedAttendance.concat("p").concat(periodNoandAttendance);
//            }
//            
//            dbg("mergedAttendance"+mergedAttendance);
//            dbg("end of getAttendanceFromBO");
//            return mergedAttendance;
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//
//        }
//        
//    }
    
    
//     private String createMonthAttendanceRecord()throws BSProcessingException{
//        
//        try{
//            dbg("inside createMonthAttendanceRecord");
//            String monthAttendanceRecord=new String();
//            BusinessService bs=inject.getBusinessService(session);
//            RequestBody<StudentAttendance> studentAttendanceBody = request.getReqBody();
//            StudentAttendance studentAttendance =studentAttendanceBody.get();
//            String l_date=studentAttendance.getDate();
//            ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//            String l_year=convertedDate.getYear();
//            String l_month=convertedDate.getMonth();
//            
//            int no_of_Days=bs.getNo_of_daysInMonth(l_year, l_month);
//            
//            for(int i=1;i<=no_of_Days;i++){
//                
//                if(i==1){
//                    
//                    monthAttendanceRecord=monthAttendanceRecord+"d"+i+" "+"d";
//                }else{    
//                
//                    monthAttendanceRecord=monthAttendanceRecord+i+" "+"d";
//                
//                }
//            }
//            dbg("monthAttendanceRecord "+monthAttendanceRecord);
//            dbg("end of createMonthAttendanceRecord");
//            return monthAttendanceRecord;
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//
//        }
//        
//    }
//     private Map<String,String> getMaxVersionAttendanceOftheDay(String p_monthAttendance,String p_day)throws BSProcessingException{
//        
//        try{
//            dbg("inside getMaxVersionAttendanceOftheDay");
//            dbg("p_monthAttendance"+p_monthAttendance);
//            dbg("p_day"+p_day);
//            String[] attendanceArray=p_monthAttendance.split("d");
//            dbg("attendanceArray"+attendanceArray.length);
//            ArrayList<String>recordsFor_a_day=new ArrayList();
//            for(int i=1;i<attendanceArray.length;i++){
//                String dayRecord=attendanceArray[i];
//                dbg("dayRecord"+dayRecord);
//                String l_day=null;
//                
//                String dayAttendance=dayRecord.split(",")[0];
//         
//                if(dayAttendance.contains(" ")){
//                    
//                    l_day=dayAttendance.split(" ")[0];
//                }else if(dayAttendance.contains("p")){
//                    
//                    l_day=dayAttendance.split("p")[0];
//                }
//                
//                dbg("l_day"+l_day);
//                if(l_day.equals(p_day)){
//                    
//                    dbg("l_day.equals(p_day)");
//                    recordsFor_a_day.add(dayRecord);
//                }
//                
//            }
//            dbg("recordsFor_a_day size"+recordsFor_a_day.size());
//            filtermap_dummy=new HashMap();
//            if(recordsFor_a_day.size()>1){
//                filterkey_dummy=p_day;
//                filtermap_dummy=new HashMap();
//                int max_vesion=recordsFor_a_day.stream().mapToInt(rec->Integer.parseInt(rec.split(",")[7])).max().getAsInt();
//                recordsFor_a_day.stream().filter(rec->Integer.parseInt(rec.split(",")[7])==max_vesion).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));           
//                dbg("max_vesion"+max_vesion);
//            }else{
//                
//                filtermap_dummy.put(p_day, recordsFor_a_day.get(0).trim());
//            }
//            
//            dbg("end of  getMaxVersionAttendanceOftheDay");
//            return filtermap_dummy;
//            
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//
//        }
//        
//    }
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
                int max_vesion=recordsFor_a_day.stream().mapToInt(rec->Integer.parseInt(rec.split(",")[1])).max().getAsInt();
                recordsFor_a_day.stream().filter(rec->Integer.parseInt(rec.split(",")[1])==max_vesion).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));           
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
//    private Map<Integer ,Map<Integer,String>> attendanceParsing(String p_attendance)throws DBProcessingException{
//        
//        try{
//         
//         Map<Integer ,Map<Integer,String>> attendanceMap=new HashMap(); //outerMap key-->dayNymber   InnerMAp key--->periodNumber   value--->Attendance 
//         String[] dayArray=   p_attendance.split("d");
//            
//         for(int i=0;i<dayArray.length;i++){
//             
//             String dayRecord=dayArray[i];
//             
//             String attendanceRecord= dayRecord.split(",")[0];
//             
//             
//            String[] periodArray=  attendanceRecord.split("p");
//            Integer day= Integer.parseInt(periodArray[0]) ;  
//            
//            
//            Map<Integer,String>periodMap=new HashMap();
//           for(int j=1;j<periodArray.length;j++){
//               
//               Integer periodNumber=Integer.parseInt(periodArray[j].substring(0, 1));
//               String attendance=periodArray[i].substring(1);
//               
//               periodMap.put(periodNumber, attendance);
//               
//           }
//            attendanceMap.put(day, periodMap);
//           
//         }
//         
//            
//            return attendanceMap;
//         }catch(Exception ex){
//            dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//       }
//        
//        
//    } 

    
//    private String dbFormatConversion(String p_day)throws BSProcessingException {
//     
//         try{
//             dbg("inside dbFormatConversion");
//            String dbFormat; 
//            RequestBody<StudentAttendance> studentAttendanceBody = request.getReqBody();
//            StudentAttendance studentAttendance =studentAttendanceBody.get();
//            String attendanceString=  getAttendanceFromBO(studentAttendance);
//            String l_makerID=request.getReqAudit().getMakerID();
//            String l_checkerID=request.getReqAudit().getCheckerID();
//            String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
//            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
//            String l_recordStatus=request.getReqAudit().getRecordStatus();
//            String l_authStatus=request.getReqAudit().getAuthStatus();
//            String l_versionNumber=request.getReqAudit().getVersionNumber();
//            String l_makerRemarks=request.getReqAudit().getMakerRemarks();
//            String l_checkerRemarks=request.getReqAudit().getCheckerRemarks(); 
//            
//            String auditString=",".concat(l_makerID).concat(",").concat(l_checkerID).concat(",").concat(l_makerDateStamp).concat(",").concat(l_checkerDateStamp).concat(",").concat(l_recordStatus).concat(",").concat(l_authStatus).concat(",").concat(l_versionNumber).concat(",").concat(l_makerRemarks).concat(",").concat(l_checkerRemarks);
//            dbg("auditString"+auditString);
//            dbFormat= p_day.concat(attendanceString).concat(auditString);
//            dbg("dbFormat"+dbFormat);
//            
//            return dbFormat;
//          }catch(Exception ex){
//            dbg(ex);
//          throw new BSProcessingException("BSProcessingException"+ex.toString());
//       }
// 
//}
//    
//private String setDayDBFromatAttendanceRecord(String monthAttendance,String dbFormatDayAttendance,String p_day,String p_operation) throws BSProcessingException {
//    
//    try{
//        dbg("inside setDayDBFromatAttendanceRecord");
//        dbg("monthAttendance"+monthAttendance);
//        dbg("dbFormatDayAttendance"+dbFormatDayAttendance);
//        dbg("p_day"+p_day);
//        dbg("p_operation"+p_operation);
//        String dbFormatMonthAttendance;
//        if(p_operation.equals("C")){
//            
//            dbFormatMonthAttendance=monthAttendance.replace("d"+p_day+" "+"d", "d"+dbFormatDayAttendance+"d");
//        }else{
//        
//            getMaxVersionAttendanceOftheDay (monthAttendance,p_day) ; 
//            String max_version_Attendance=filtermap_dummy.get(p_day);
//            dbFormatMonthAttendance=  monthAttendance.replace(max_version_Attendance+"d", dbFormatDayAttendance+"d");
//        
//        }
//        
//         dbg("dbFormatMonthAttendance"+dbFormatMonthAttendance);
//         dbg("end of setDayDBFromatAttendanceRecord");
//                return dbFormatMonthAttendance;
//
//         }catch(Exception ex){
//            dbg(ex);
//          throw new BSProcessingException("BSProcessingException"+ex.toString());
//       }
//        
//        
//    }
//    private void createDBFormatConversion(String p_previousAttendance,String p_day) throws BSProcessingException,DBValidationException,DBProcessingException  {
//    
//    try{
//          dbg("inside createDBFormatConversion");
//          dbg("p_previousAttendance"+p_previousAttendance);
//          dbg("p_day"+p_day);
//          IBDProperties i_db_properties=session.getCohesiveproperties();
//          BusinessService bs=inject.getBusinessService(session);
//          IDBTransactionService dbts=inject.getDBTransactionService();
//          RequestBody<StudentAttendance> studentAttendanceBody = request.getReqBody();
//          String l_instituteID=request.getReqHeader().getInstituteID();
//          StudentAttendance studentAttendance =studentAttendanceBody.get();
//          String dbFormatDayAttendance=dbFormatConversion(p_day);
//          String versionNumber=request.getReqAudit().getVersionNumber();
//          dbg("versionNumber"+versionNumber);
//          String newAttendance;
//          if(versionNumber.equals("1")){
//              
//              newAttendance=p_previousAttendance.replace("d"+p_day+" "+"d", "d"+dbFormatDayAttendance+"d");
//          }else{
//          
//          newAttendance=p_previousAttendance.concat(dbFormatDayAttendance+"d");
//          }
//          
//          dbg("newAttendance"+newAttendance);
//          Map<String,String>l_columnToUpdate=new HashMap();
//          l_columnToUpdate.put("4", newAttendance);
//           
//          String l_studentID=studentAttendance.getStudentID();
//          String l_date=studentAttendance.getDate();
//          ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//          String l_year=convertedDate.getYear();
//          String l_month=convertedDate.getMonth();
//          String[] l_primaryKey={l_studentID,l_year,l_month};
//           
//          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_ATTENDANCE", l_primaryKey, l_columnToUpdate, session);
//            
//          dbg("end of createDBFormatConversion");
//          }catch(DBValidationException ex){
//          throw ex;
//         }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//         }catch(BSProcessingException ex){
//           dbg(ex);
//           throw new BSProcessingException(ex.toString());
//         }catch(Exception ex){
//            dbg(ex);
//          throw new BSProcessingException("BSProcessingException"+ex.toString());
//       }
//        
//        
//    }
//
//private void updateDBFormatConversion(String p_previousAttendance,String p_day,String p_operation) throws BSProcessingException,DBValidationException,DBProcessingException  {
//    
//    try{
//          dbg("inside updateDBFormatConversion");
//          dbg("p_previousAttendance"+p_previousAttendance);
//          dbg("p_day"+p_day);
//          dbg("p_operation"+p_operation);
//          IBDProperties i_db_properties=session.getCohesiveproperties();
//          BusinessService bs=inject.getBusinessService(session);
//          IDBTransactionService dbts=inject.getDBTransactionService();
//          RequestBody<StudentAttendance> studentAttendanceBody = request.getReqBody();
//          String l_instituteID=request.getReqHeader().getInstituteID();
//          StudentAttendance studentAttendance =studentAttendanceBody.get();
//          String dbFormatDayAttendance=dbFormatConversion(p_day);
//          String monthAttendance= setDayDBFromatAttendanceRecord(p_previousAttendance,dbFormatDayAttendance,p_day,p_operation);
//            
//          Map<String,String>l_columnToUpdate=new HashMap();
//          l_columnToUpdate.put("4", monthAttendance);
//           
//          String l_studentID=studentAttendance.getStudentID();
//          String l_date=studentAttendance.getDate();
//          ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//          String l_year=convertedDate.getYear();
//          String l_month=convertedDate.getMonth();
//          String[] l_primaryKey={l_studentID,l_year,l_month};
//           
//          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_ATTENDANCE", l_primaryKey, l_columnToUpdate, session);
//            
//        dbg("end of updateDBFormatConversion");
//         }catch(DBValidationException ex){
//          throw ex;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//      }catch(BSProcessingException ex){
//           dbg(ex);
//           throw new BSProcessingException(ex.toString());
//     }catch(Exception ex){
//            dbg(ex);
//          throw new BSProcessingException("BSProcessingException"+ex.toString());
//       }
//        
//        
//    }
//    
//private void deleteDBFormatConversion(String p_previousAttendance,String p_day) throws BSProcessingException,DBValidationException,DBProcessingException {
//    
//    try{
//          dbg("inside deleteDBFormatConversion");
//          dbg("p_previousAttendance"+p_previousAttendance);
//          dbg("p_day"+p_day);
//          IBDProperties i_db_properties=session.getCohesiveproperties();
//          BusinessService bs=inject.getBusinessService(session);
//          IDBTransactionService dbts=inject.getDBTransactionService();
//          RequestBody<StudentAttendance> studentAttendanceBody = request.getReqBody();
//          String l_instituteID=request.getReqHeader().getInstituteID();
//          StudentAttendance studentAttendance =studentAttendanceBody.get();
//          getMaxVersionAttendanceOftheDay (p_previousAttendance,p_day) ; 
//          String max_version_Attendance=filtermap_dummy.get(p_day);
//          dbg("max_version_Attendance"+max_version_Attendance);
//          String dbFormatMonthAttendance=  p_previousAttendance.replace(max_version_Attendance+"d", p_day+" "+"d");            
//          dbg("dbFormatMonthAttendance"+dbFormatMonthAttendance);
//          Map<String,String>l_columnToUpdate=new HashMap();
//          l_columnToUpdate.put("4", dbFormatMonthAttendance);
//           
//          String l_studentID=studentAttendance.getStudentID();
//          String l_date=studentAttendance.getDate();
//          ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//          String l_year=convertedDate.getYear();
//          String l_month=convertedDate.getMonth();
//          String[] l_primaryKey={l_studentID,l_year,l_month};
//           
//          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_ATTENDANCE", l_primaryKey, l_columnToUpdate, session);
//            
//        dbg("end of updateDBFormatConversion");
//     }catch(DBValidationException ex){
//          throw ex;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//      }catch(BSProcessingException ex){
//           dbg(ex);
//           throw new BSProcessingException(ex.toString());
//          }catch(Exception ex){
//            dbg(ex);
//          throw new BSProcessingException("BSProcessingException"+ex.toString());
//       }
//        
//        
//    }
//
//
//private AuditDetails getAuditDetailsFromDayAttendance(String dayAttendance)throws BSProcessingException{
//    
//    try{
//      dbg("inside getAuditDetailsFromDayAttendance");
//      String[] attArr=  dayAttendance.split(",");
//      AuditDetails audit =new AuditDetails();
//      audit.setMakerID(attArr[1]);
//      audit.setCheckerID(attArr[2]);
//      audit.setMakerDateStamp(attArr[3]);
//      audit.setCheckerDateStamp(attArr[4]);
//      audit.setRecordStatus(attArr[5]);
//      audit.setAuthStatus(attArr[6]);
//      audit.setVersionNo(attArr[7]);
//      audit.setMakerRemarks(attArr[8]);
//      audit.setCheckerRemarks(attArr[9]);
//        
//      dbg("makerID"+audit.getMakerID());
//      dbg("checkerID"+audit.getCheckerID());
//      dbg("makerDateStamp"+audit.getMakerDateStamp());
//      dbg("checkerDateStamp"+audit.getCheckerDateStamp());
//      dbg("RecordStatus"+audit.getRecordStatus());
//      dbg("AuthStatus"+audit.getAuthStatus());
//      dbg("versionNumber"+audit.getVersionNo());
//      dbg("makerRemarks"+audit.getMakerRemarks());
//      dbg("checkerRemarks"+audit.getCheckerRemarks());
//      
//      dbg("end of getAuditDetailsFromDayAttendance");
//      return audit;
//    }catch(Exception ex){
//           dbg(ex);
//           throw new BSProcessingException("BSProcessingException"+ex.toString());
//    }
//    
//    
//    
//}
//
//
public  void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     
//     try{
//        
//        dbg("inside relationshipProcessing") ;
//        
//        
//        if(exAudit.getRelatioshipOperation().equals("C")){
//
//             createOrUpdateStudentCalender();
//        }else if(exAudit.getRelatioshipOperation().equals("M")){
//            
//             createOrUpdateStudentCalender();
//        }else if(exAudit.getRelatioshipOperation().equals("D")){
//            
//            deleteStudentCalender();
//        }
//        
//   
//         dbg("end of relationshipProcessing");
//         }catch(DBValidationException ex){
//             throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//             throw new DBProcessingException(ex.toString());
//        }catch(BSValidationException ex){
//            throw ex;
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//     
//        }
 }
//    
// private void createOrUpdateStudentCalender()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
//     
//        try{
//        dbg("inside createOrUpdateStudentCalender") ;  
//        boolean recordExistence=true;
//        IDBTransactionService dbts=inject.getDBTransactionService();
//        ErrorHandler errHandler=session.getErrorhandler();
//        BusinessService bs=inject.getBusinessService(session);
//        RequestBody<StudentAttendance> reqBody = request.getReqBody();
//        StudentAttendance studentAttendance =reqBody.get();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        String l_studentID=studentAttendance.getStudentID();
//        String l_date=studentAttendance.getDate();
//        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//        String l_year=convertedDate.getYear();
//        String l_month=convertedDate.getMonth();
//        String l_day=convertedDate.getDay();
//        int day=Integer.parseInt(l_day);
//        String[] l_pkey={l_studentID,l_year,l_month};
//        DBRecord studentCalenderRec;
//        String l_previousMonthEvent=null;
//        String newDayEvent=null;
//        String currentEvent=getCurrentEventFromBO();
//        String pKeyOfCurrentEvent=l_studentID+","+l_year+","+l_month;
//        String previousEvent=null;
//        try{
//        
//            studentCalenderRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_CALENDER", l_pkey, session, dbSession);
//            l_previousMonthEvent=studentCalenderRec.getRecord().get(3).trim();
//            dbg("l_previousMonthEvent"+l_previousMonthEvent);
//        }catch(DBValidationException ex){
//            if(ex.toString().contains("DB_VAL_011")){
//               
//                errHandler.removeSessionErrCode("DB_VAL_011");
//               recordExistence=false;                
//            }
//        }
//        
//        dbg("recordExistence"+recordExistence);
//        
//        if(recordExistence){
//            
//            String l_previousDayEvents=l_previousMonthEvent.split("d")[day];
//            dbg("l_previousDayEvents"+l_previousDayEvents);
//            if(l_previousDayEvents.contains("AT")){
//                dbg("l_previousDayEvents.contains AT");
//                String[]l_eventArray=l_previousDayEvents.split("//");
//                dbg("l_eventArray size"+l_eventArray.length);
//                boolean pkExistence=false;
//                for(int i=1;i<l_eventArray.length;i++){
//                    previousEvent=l_eventArray[i];
//                    dbg("previousEvent"+previousEvent);
//                    String l_pkColumnsWithoutVersion=bs.getPKcolumnswithoutVersion("STUDENT","SVW_STUDENT_ATTENDANCE",session,inject);
//                    int l_lengthOfPkWithoutVersion=l_pkColumnsWithoutVersion.split("~").length;
//                    String l_pkey_of_ExistingEvent=bs.getPkeyOfExistingEvent(l_lengthOfPkWithoutVersion,previousEvent);
//                    dbg("l_pkey_of_ExistingEvent"+l_pkey_of_ExistingEvent);
//                    if(pKeyOfCurrentEvent.equals(l_pkey_of_ExistingEvent)){
//                        dbg("pKeyOfCurrentEvent.equals(l_pkey_of_ExistingEvent)");
//                        pkExistence=true;
//                        break;
//                    }
//                    
//                }
//                
//                dbg("after for loop pk existence"+pkExistence);
//                if(pkExistence){
//                
//                     newDayEvent=l_previousDayEvents.replace(previousEvent+"//", currentEvent);
//                     dbg("newDayEvent"+newDayEvent);
//                }else{
//                    
//                    newDayEvent=l_previousDayEvents.concat(currentEvent);
//                    dbg("newDayEvent"+newDayEvent);
//                }
//            
//            
//            }else{
//                dbg("l_previousDayEvents not contains AT");
//                newDayEvent=l_previousDayEvents.concat(currentEvent);
//            }
//            
//            String newMonthEvent=bs.setDayEventinMonthEvent(l_previousMonthEvent,l_previousDayEvents,newDayEvent,l_day);
//            Map<String,String>l_column_to_update=new HashMap();
//            l_column_to_update.put("4", newMonthEvent);
//             
//             dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_CALENDER", l_pkey, l_column_to_update, session);   
//            
//
//        }else{
//            String l_monthEvent=bs.createMonthEvent(l_date);
//            dbg(l_day+"//"+" "+"d");
//            dbg(l_day+"//"+currentEvent+"d");
//            
//            newDayEvent =l_monthEvent.replace("d"+l_day+"//"+" "+"d", "d"+l_day+"//"+currentEvent+"d");
//            
//            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",73,l_studentID,l_year,l_month,newDayEvent);
//        }
//        
//        
//        
//        
//          
//        dbg("end of createOrUpdateStudentCalender");  
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException(ex.toString());
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//        }
//          
// }   
//    
// private void deleteStudentCalender()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
//     
//        try{
//        dbg("inside deleteStudentCalender");    
//        IDBTransactionService dbts=inject.getDBTransactionService();
//        BusinessService bs=inject.getBusinessService(session);
//        RequestBody<StudentAttendance> reqBody = request.getReqBody();
//        StudentAttendance studentAttendance =reqBody.get();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        String l_studentID=studentAttendance.getStudentID();
//        String l_date=studentAttendance.getDate();
//        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//        String l_year=convertedDate.getYear();
//        String l_month=convertedDate.getMonth();
//        String l_day=convertedDate.getDay();
//        int day=Integer.parseInt(l_day);
//        String[] l_pkey={l_studentID,l_year,l_month};
//        DBRecord studentCalenderRec;
//        String l_previousMonthEvent=null;
//        String newDayEvent=null;
//        
//        String currentEvent=getCurrentEventFromBO();
//
//        
//            studentCalenderRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_CALENDER", l_pkey, session, dbSession);
//            l_previousMonthEvent=studentCalenderRec.getRecord().get(3).trim();
//            dbg("l_previousMonthEvent"+l_previousMonthEvent);
//            String l_previousDayEvents=l_previousMonthEvent.split("d")[day];
//            dbg("l_previousDayEvents"+l_previousDayEvents);
//            
//            if(l_previousDayEvents.split("//").length==2){
//               newDayEvent=l_previousDayEvents.replace(currentEvent, " ");
//            
//            }else{
//                
//                newDayEvent=l_previousDayEvents.replace(currentEvent, "");
//            } 
//            String newMonthEvent=bs.setDayEventinMonthEvent(l_previousMonthEvent,l_previousDayEvents,newDayEvent,l_day);
//            
//            Map<String,String>l_column_to_update=new HashMap();
//            l_column_to_update.put("4", newMonthEvent);
//             
//            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_CALENDER", l_pkey, l_column_to_update, session);   
//            
//
//        
//        
//        
//        
//        
//          
//       dbg("inside deleteStudentCalender");
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException(ex.toString());
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//        }
//          
// }   
// 
//
// 
// private String getCurrentEventFromBO()throws BSProcessingException{
//     
//     try{
//         
//        dbg("inside StudentAttendance--->getCurrentEventFromBO");
//        BusinessService bs=inject.getBusinessService(session);
//        RequestBody<StudentAttendance> reqBody = request.getReqBody();
//        StudentAttendance studentAttendance =reqBody.get(); 
//        String l_studentID=studentAttendance.getStudentID();
//        String l_date=studentAttendance.getDate();
//        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//        String l_year=convertedDate.getYear();
//        String l_month=convertedDate.getMonth();
//        DayAttendance dayAttendance=calculateAttendanceForaDay();
//        String present=Float.toString(dayAttendance.getPresent());
//        String absent=Float.toString(dayAttendance.getAbsent());
//        String leave=Float.toString(dayAttendance.getLeave());
//        
//        String currentEvent="AT"+","+l_studentID+","+l_year+","+l_month+","+"P"+present+","+"A"+absent+","+"L"+leave+"//";
//         
//        dbg("currentEvent"+currentEvent); 
//        return  currentEvent;
//     }catch (Exception ex) {
//       dbg(ex);
//       throw new BSProcessingException("Exception" + ex.toString());
//     }
//     
// }
//
//     private DayAttendance calculateAttendanceForaDay()throws BSProcessingException{
//         
//         try{
//             RequestBody<StudentAttendance> reqBody = request.getReqBody();
//             StudentAttendance studentAttendance =reqBody.get(); 
//             DayAttendance dayAtt=new DayAttendance();   
//             int halfDayAttendanceNoOfPeroids = Integer.parseInt( session.getCohesiveproperties().getProperty("HALFDAY_ATTENDANCE_NO_OF_PERIODS")) ;
//             int fullDayAttendanceNoOfPeroids = Integer.parseInt( session.getCohesiveproperties().getProperty("FULLDAY_ATTENDANCE_NO_OF_PERIODS")) ;
//             int presentPeriodCount=0;
//             int absentPeriodCount=0;
//             int leavePeriodCount=0;
//             for(int i=0;i<studentAttendance.periodAttendance.length;i++){
//                 
//                 String l_attendance=studentAttendance.periodAttendance[i].getAttendance();
//                 
//                 if(l_attendance.equals("P")){
//                  
//                  presentPeriodCount++;
//                }else if(l_attendance.equals("A")){
//                  
//                  absentPeriodCount++;
//                }else if(l_attendance.equals("L")){
//                  
//                  leavePeriodCount++;
//              }        
//                 
//             }
//             
//             if(leavePeriodCount>halfDayAttendanceNoOfPeroids){
//              
//              dayAtt.setAbsent(0);
//              dayAtt.setLeave(1);
//              dayAtt.setPresent(0);
//              
//              return dayAtt;
//          }
//          
//          if(absentPeriodCount>halfDayAttendanceNoOfPeroids){
//              
//              dayAtt.setAbsent(1);
//              dayAtt.setLeave(0);
//              dayAtt.setPresent(0);
//              return dayAtt;
//              
//          }
//          
//          if(presentPeriodCount==fullDayAttendanceNoOfPeroids){
//              
//              dayAtt.setAbsent(0);
//              dayAtt.setLeave(0);
//              dayAtt.setPresent(1);
//              
//              return dayAtt;
//          }
//          
//          
//          if(presentPeriodCount>=halfDayAttendanceNoOfPeroids){
//              
//            dayAtt.setPresent(0.5f);
//              
//              if(absentPeriodCount<halfDayAttendanceNoOfPeroids){
//                  
//                  dayAtt.setAbsent(0.5f);
//                  dayAtt.setLeave(0);
//                  
//                  
//                  return dayAtt;
//              }else if(leavePeriodCount<halfDayAttendanceNoOfPeroids){
//                  
//                  dayAtt.setAbsent(0);
//                  dayAtt.setLeave(0.5f);
//                  
//                  return dayAtt;
//              }
//              
//          }
//         
//          
//          
//                  return dayAtt;
//             
//         }catch (Exception ex) {
//          dbg(ex);
//          throw new BSProcessingException("Exception" + ex.toString());
//     }
//         
//         
//     }
//     
     
 
    
    
}    






    
    
    
    



