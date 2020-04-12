/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.classattendance;

import com.ibd.businessViews.IAmazonEmailService;
import com.ibd.businessViews.IAmazonSMSService;
import com.ibd.businessViews.IClassAttendanceService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.student.studentattendanceservice.AuditDetails;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.ConvertedDate;
import com.ibd.cohesive.app.business.util.EndPoint;
import com.ibd.cohesive.app.business.util.NotificationUtil;
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
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.lock.ILockService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.Email;
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
import java.util.concurrent.Future;
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
//@Local(IClassAttendanceService.class)
@Remote(IClassAttendanceService.class)
@Stateless
public class ClassAttendanceService implements IClassAttendanceService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    Map<String,String>filtermap_dummy;
    String filterkey_dummy;
    Map<String,String>audit_filtermap_dummy;
    String audit_filterkey_dummy;
    
    public ClassAttendanceService(){
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
       dbg("inside ClassAttendanceService--->processing");
       dbg("ClassAttendanceService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<ClassAttendance> reqBody = request.getReqBody();
       ClassAttendance classAttendance =reqBody.get();
       String l_standard=classAttendance.getStandard();
       String l_section=classAttendance.getSection();
       String l_operation=request.getReqHeader().getOperation();
       if(!l_operation.equals("Create-Default")){
       
       String l_date=classAttendance.getDate();
       ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
       String l_year=convertedDate.getYear();
       String l_month=convertedDate.getMonth();
       l_lockKey=l_standard.concat("~").concat(l_section).concat("~").concat(l_year).concat("~").concat(l_month);
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       
       }
       BusinessEJB<IClassAttendanceService>classAttendanceEJB=new BusinessEJB();
       classAttendanceEJB.set(this);
      
       exAudit=bs.getExistingAudit(classAttendanceEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,classAttendanceEJB);
       
       if(l_operation.equals("Create-Default")){
           
           createDefault();
       }
       
       
       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,classAttendanceEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in class attendance");
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
           audit_filtermap_dummy=null;
           audit_filterkey_dummy=null;
           request=null;
            bs=inject.getBusinessService(session);
            if(l_session_created_now){
                bs.responselogging(jsonResponse, inject,session,dbSession);
                dbg("Response-->"+jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
                //if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                   clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes
           
                  //  BSProcessingException ex=new BSProcessingException("response contains special characters");
                   //dbg(ex);
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

    private  void buildBOfromReq(JsonObject p_request)throws BSProcessingException,DBProcessingException,BSValidationException{
      ClassAttendance classAttendance=new ClassAttendance();
      RequestBody<ClassAttendance> reqBody = new RequestBody<ClassAttendance>(); 
           
      try{
      dbg("inside class attendance buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      BSValidation bsv=inject.getBsv(session);
//      classAttendance.setStandard(l_body.getString("standard"));
//      classAttendance.setSection(l_body.getString("section"));
       String l_class=l_body.getString("class");
       
       bsv.classValidation(l_class,session);
       classAttendance.setStandard(l_class.split("/")[0]);
       classAttendance.setSection(l_class.split("/")[1]);
       classAttendance.setDate(l_body.getString("date"));

       if(!l_operation.equals("View")&&!l_operation.equals("Create-Default")){
     
          JsonArray foreNoonArray=l_body.getJsonArray("foreNoon");
          JsonArray afterNoonArray=l_body.getJsonArray("afterNoon");

          classAttendance.foreNoonAttendance=new  StudentWiseAttendance[foreNoonArray.size()];
          
          dbg("iteration of foreNoon starts");
          for(int i=0;i<foreNoonArray.size();i++){
              JsonObject l_studentObject=foreNoonArray.getJsonObject(i);
              classAttendance.foreNoonAttendance[i]=new StudentWiseAttendance();
              String l_studentID=l_studentObject.getString("studentID");
              dbg("l_studentID"+l_studentID);
              classAttendance.foreNoonAttendance[i].setStudentID(l_studentObject.getString("studentID"));
              JsonArray l_periodArray=l_studentObject.getJsonArray("period");
              dbg("period array size"+l_periodArray.size());
              classAttendance.foreNoonAttendance[i].periodAttendance=new PeriodAttendance[l_periodArray.size()];
              
              for(int j=0;j<l_periodArray.size();j++){
                  
                  JsonObject l_periodObject=l_periodArray.getJsonObject(j);
                  classAttendance.foreNoonAttendance[i].periodAttendance[j]=new PeriodAttendance();
                  classAttendance.foreNoonAttendance[i].periodAttendance[j].setPeriodNumber(l_periodObject.getString("periodNumber"));
                  classAttendance.foreNoonAttendance[i].periodAttendance[j].setAttendance(l_periodObject.getString("attendance"));
                  dbg("periodNumber"+l_periodObject.getString("periodNumber"));
                  dbg("attendance"+l_periodObject.getString("attendance"));
                  
              }
          }
           dbg("iteration of foreNoon ends");
          
          classAttendance.afterNoonAttendance=new  StudentWiseAttendance[afterNoonArray.size()];
          dbg("iteration of afterNoon starts");
         for(int i=0;i<afterNoonArray.size();i++){
              JsonObject l_studentObject=afterNoonArray.getJsonObject(i);
              String l_studentID=l_studentObject.getString("studentID");
              dbg("l_studentID"+l_studentID);
              classAttendance.afterNoonAttendance[i]=new StudentWiseAttendance();
              classAttendance.afterNoonAttendance[i].setStudentID(l_studentObject.getString("studentID"));
              JsonArray l_periodArray=l_studentObject.getJsonArray("period");
              dbg("period array size"+l_periodArray.size());
              classAttendance.afterNoonAttendance[i].periodAttendance=new PeriodAttendance[l_periodArray.size()];
              
              for(int j=0;j<l_periodArray.size();j++){
                  
                  JsonObject l_periodObject=l_periodArray.getJsonObject(j);
                  classAttendance.afterNoonAttendance[i].periodAttendance[j]=new PeriodAttendance();
                  classAttendance.afterNoonAttendance[i].periodAttendance[j].setPeriodNumber(l_periodObject.getString("periodNumber"));
                  classAttendance.afterNoonAttendance[i].periodAttendance[j].setAttendance(l_periodObject.getString("attendance"));
                  dbg("periodNumber"+l_periodObject.getString("periodNumber"));
                  dbg("attendance"+l_periodObject.getString("attendance"));
              }
          }
         dbg("iteration of afterNoon ends");
      }
        reqBody.set(classAttendance);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");
     }catch(BSValidationException ex){
         throw ex;
     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   
    private String getReferenceID()throws BSProcessingException{
        
        try{
            dbg("inside getReferenceID");
            RequestBody<ClassAttendance> reqBody = request.getReqBody();
            BusinessService bs=inject.getBusinessService(session);
            ClassAttendance classAttendance =reqBody.get();
            String l_standard=classAttendance.getStandard();
            String l_section=classAttendance.getSection();
            String l_date=classAttendance.getDate();
            ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
            String l_year=convertedDate.getYear();
            String l_month=convertedDate.getMonth();
            
            String l_key=l_standard+"*"+l_section+"*"+l_year+"*"+l_month;
            
            
            dbg("end of getReferenceID-->l_key"+l_key);
            return l_key;
   
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
       }       
        
    }
    
    
    
    
    
    
    

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
        
    try{ 
        dbg("inside class attendance create"); 
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        BusinessService bs=inject.getBusinessService(session);
        RequestBody<ClassAttendance> reqBody = request.getReqBody();
        ErrorHandler errHandler=session.getErrorhandler();
        ClassAttendance classAttendance =reqBody.get();
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_standard=classAttendance.getStandard();
        String l_section=classAttendance.getSection();
        String l_date=classAttendance.getDate();
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();   
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Attendance_"+l_date,"CLASS",344,l_standard,l_section,l_date,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
        
        Map<String,String>studentWiseAttendance=this.getAttendanceFromBO(true);
        
        Iterator<String>studentIterator=studentWiseAttendance.keySet().iterator();
        
        while(studentIterator.hasNext()){
            
            String studentID=studentIterator.next();
            String attendanceValue=studentWiseAttendance.get(studentID);
            
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Attendance_"+l_date,"CLASS",345,l_standard,l_section,l_date,studentID,attendanceValue,l_versionNumber);
        }
        
        
        relationshipProceessingInBaseTables("C");
        dbg("end of class attendance create"); 
//        }catch(BSValidationException ex){
//         throw ex;
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
    
   private String insertDayAuditInMonthAudit(String p_previousMonthAudit,String p_day,String operation)throws BSProcessingException,BSValidationException{
       
       try{
           dbg("inside insertDayAuditInMonthAudit");
           dbg("p_previousMonthAudit"+p_previousMonthAudit);
           dbg("p_day"+p_day);
//           String l_makerID=request.getReqAudit().getMakerID();
//            String l_checkerID=request.getReqAudit().getCheckerID();
//            String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
//            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String l_recordStatus=request.getReqAudit().getRecordStatus();
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_versionNumber=request.getReqAudit().getVersionNumber();
//            String l_makerRemarks=request.getReqAudit().getMakerRemarks();
//            String l_checkerRemarks=request.getReqAudit().getCheckerRemarks(); 
            String newMonthAudit=null;
            
//            if(l_makerID.contains("d")){
//                
//                l_makerID=l_makerID.toUpperCase();
//            }
//            if(l_checkerID.contains("d")){
//                
//                l_checkerID=l_checkerID.toUpperCase();
//            }
//            if(l_makerRemarks.contains("d")){
//                
//                l_makerRemarks=l_makerRemarks.toUpperCase();
//            }
//            if(l_checkerRemarks.contains("d")){
//                
//                l_checkerRemarks=l_checkerRemarks.toUpperCase();
//            }
            String newDayAudit=null;
            
            if(!operation.equals("D")){
            
            
               newDayAudit=p_day+",".concat(" ").concat(",").concat(" ").concat(",").concat(" ").concat(",").concat(" ").concat(",").concat(l_recordStatus).concat(",").concat(l_authStatus).concat(",").concat(l_versionNumber).concat(",").concat(" ").concat(",").concat(" ");
            }else{
                newDayAudit=p_day+" ";
            }
            
            
            
            dbg("newDayAudit"+newDayAudit);
               
            
              if(l_versionNumber.equals("1")&&operation.equals("C")){
                   
                 newMonthAudit=p_previousMonthAudit.replace("d"+p_day+" "+"d", "d"+newDayAudit+"d");
              }else{
                  

                 getMaxVersionAuditOftheDay(p_previousMonthAudit,p_day);
                 String existingDayAudit=audit_filtermap_dummy.get(p_day);
                 dbg("existingDayAudit"+existingDayAudit);
                 newMonthAudit=p_previousMonthAudit.replace("d"+existingDayAudit+"d", "d"+newDayAudit+"d");
               }
              
          
           dbg("inside insertDayAuditInMonthAudi newMonthAudit-->"+newMonthAudit);
       return newMonthAudit;
       }catch(BSValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception".concat(ex.toString()));
        }
       
   }
    private String createNewMonthAudit()throws BSProcessingException{
        
        try{
            dbg("inside createNewMonthAudit");
            String monthAttendanceRecord=new String();
            BusinessService bs=inject.getBusinessService(session);
            RequestBody<ClassAttendance> classAttendanceBody = request.getReqBody();
            ClassAttendance classAttendance =classAttendanceBody.get();
            String l_date=classAttendance.getDate();
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
            dbg("end of createNewMonthAudit  monthAttendanceRecord-->"+monthAttendanceRecord);
            return monthAttendanceRecord;

        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

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

    
//    public void authUpdate()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
//        
//     try{ 
//        dbg("inside class attendance--->auth update");
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        BusinessService bs=inject.getBusinessService(session);
//        IDBTransactionService dbts=inject.getDBTransactionService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        RequestBody<ClassAttendance> reqBody = request.getReqBody();
//        ClassAttendance classAttendance =reqBody.get();
//        String l_standard=classAttendance.getStandard();
//        String l_section=classAttendance.getSection();
//        String l_date=classAttendance.getDate();
//        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//        String l_day=convertedDate.getDay();
//        String l_year=convertedDate.getYear();
//        String l_month=convertedDate.getMonth();
//        String[] l_masterPkey={l_standard,l_section,l_year,l_month};
//        
//               DBRecord attendanceRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS", "CLASS_ATTENDANCE_MASTER", l_masterPkey, session, dbSession);
//               String  previousMonthAudit=attendanceRecord.getRecord().get(4).trim();
//               dbg("previousMonthAudit"+previousMonthAudit);
//               String newMonthAudit= insertDayAuditInMonthAudit(previousMonthAudit,l_day,"U");
//               Map<String,String>l_columnToUpdate=new HashMap();
//               l_columnToUpdate.put("5", newMonthAudit);
//               dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS", "CLASS_ATTENDANCE_MASTER", l_masterPkey, l_columnToUpdate, session);
//
//        
//         dbg("end of class attendance--->auth update");  
//        }catch(BSValidationException ex){
//         throw ex; 
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
//        
//       }
    
    
     public void authUpdate()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
        
     try{ 
        dbg("inside class attendance--->auth update");
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        BusinessService bs=inject.getBusinessService(session);
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<ClassAttendance> reqBody = request.getReqBody();
        ClassAttendance classAttendance =reqBody.get();
        String l_standard=classAttendance.getStandard();
        String l_section=classAttendance.getSection();
        String l_date=classAttendance.getDate();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("5", l_checkerID);
         l_column_to_update.put("7", l_checkerDateStamp);
         l_column_to_update.put("9", l_authStatus);
         l_column_to_update.put("12", l_checkerRemarks);
        
         String[] l_primaryKey={l_standard,l_section,l_date};
         
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Attendance_"+l_date,"CLASS","CLASS_ATTENDANCE_MASTER_HISTORY",l_primaryKey,l_column_to_update,session);
        
        
        relationshipProceessingInBaseTables("U");
         dbg("end of class attendance--->auth update");  
        }catch(BSValidationException ex){
         throw ex; 
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
    
    
//    public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
//        
//       try{ 
//        dbg("inside class attendance fullUpdate");    
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();   
//        BusinessService bs=inject.getBusinessService(session);   
//        IDBTransactionService dbts=inject.getDBTransactionService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        RequestBody<ClassAttendance> reqBody = request.getReqBody();
//        ClassAttendance classAttendance =reqBody.get();
//        String l_instituteID=request.getReqHeader().getInstituteID();   
//        String l_standard=classAttendance.getStandard();
//        String l_section=classAttendance.getSection();
//        String l_date=classAttendance.getDate();
//        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//        String l_day=convertedDate.getDay();
//        String referenceID=getReferenceID();
//        String l_year=convertedDate.getYear();
//        String l_month=convertedDate.getMonth();
//        String[] l_masterPkey={l_standard,l_section,l_year,l_month};
// 
//        dbg("for loop iteration starts ");
//         Map<String,String>l_studentWiseMap=getAttendanceFromBO();
//        Iterator<String>studentIterator=l_studentWiseMap.keySet().iterator();
//        
//        while(studentIterator.hasNext()){
//            String l_studentID=studentIterator.next();
//            String dayAttendance=l_studentWiseMap.get(l_studentID);
//            String[] l_primaryKey={referenceID,l_studentID};
//            DBRecord attendanceRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS", "CLASS_ATTENDANCE_DETAIL", l_primaryKey, session, dbSession,false);
//            String   previousMonthAttendance=attendanceRecord.getRecord().get(2).trim();
//            dbg("previousMonthAttendance"+previousMonthAttendance);
//            updateDBFormatConversion(previousMonthAttendance,l_day,"U",dayAttendance,l_studentID);
//            dbg("updateDBFormatConversion called for"+l_studentID);
//        }
//       dbg("for loop iteration ends ");
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
//    }

    public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
         Map<String,String> l_column_to_update;
       try{ 
        dbg("inside class attendance fullUpdate");    
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();   
        BusinessService bs=inject.getBusinessService(session);   
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassAttendance> reqBody = request.getReqBody();
        ClassAttendance classAttendance =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();   
        String l_standard=classAttendance.getStandard();
        String l_section=classAttendance.getSection();
        String l_date=classAttendance.getDate();
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();   
        
                l_column_to_update= new HashMap();
                l_column_to_update.put("1", l_standard);
                l_column_to_update.put("2", l_section);
                l_column_to_update.put("3", l_date);
                l_column_to_update.put("4", l_makerID);
                l_column_to_update.put("5", l_checkerID);
                l_column_to_update.put("6", l_makerDateStamp);
                l_column_to_update.put("7", l_checkerDateStamp);
                l_column_to_update.put("8", l_recordStatus);
                l_column_to_update.put("9", l_authStatus);
                l_column_to_update.put("10", l_versionNumber);
                l_column_to_update.put("11", l_makerRemarks);
                l_column_to_update.put("12", l_checkerRemarks);


                String[] l_primaryKey={l_standard,l_section,l_date};

                dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Attendance_"+l_date,"CLASS","CLASS_ATTENDANCE_MASTER_HISTORY",l_primaryKey,l_column_to_update,session);

                Map<String,String>studentWiseAttendance=this.getAttendanceFromBO(true);

                Iterator<String>studentIterator=studentWiseAttendance.keySet().iterator();

                while(studentIterator.hasNext()){

                    l_column_to_update= new HashMap();
                    String studentID=studentIterator.next();
                    String attendanceValue=studentWiseAttendance.get(studentID);
                    String[] l_detailPKey={l_standard,l_section,l_date,studentID};
                    l_column_to_update.put("4", studentID);
                    l_column_to_update.put("5", attendanceValue);

                    dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Attendance_"+l_date,"CLASS","CLASS_ATTENDANCE_DETAIL_HISTORY",l_detailPKey,l_column_to_update,session);
                }
                        
                        
          relationshipProceessingInBaseTables("U");              
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
    public void delete()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
        try{
        dbg("inside class attendance delete");    
        IDBReadBufferService readBuffer=inject.getDBReadBufferService(); 
        BusinessService bs=inject.getBusinessService(session); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassAttendance> reqBody = request.getReqBody();
        ClassAttendance classAttendance =reqBody.get();
        String l_standard=classAttendance.getStandard();
        String l_section=classAttendance.getSection();    
        String l_date=classAttendance.getDate();
        
        
        String[] l_primaryKey={l_standard,l_section,l_date};

                dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Attendance_"+l_date,"CLASS","CLASS_ATTENDANCE_MASTER_HISTORY",l_primaryKey,session);

                Map<String,String>studentWiseAttendance=this.getAttendanceFromBO(true);

                Iterator<String>studentIterator=studentWiseAttendance.keySet().iterator();

                while(studentIterator.hasNext()){

                    String studentID=studentIterator.next();
                    String[] l_detailPKey={l_standard,l_section,l_date,studentID};

                    dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Attendance_"+l_date,"CLASS","CLASS_ATTENDANCE_DETAIL_HISTORY",l_detailPKey,session);
                }
        
        
        
        
        
        
        relationshipProceessingInBaseTables("D");  
         dbg("detail table opertion ends");
            dbg("end of class attendance delete");
//        }catch(BSValidationException ex){
//         throw ex;    
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

//    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
//                
//        try{      
//        dbg("inside class attendance--->view");    
//        BusinessService bs=inject.getBusinessService(session); 
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        RequestBody<ClassAttendance> reqBody = request.getReqBody();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        ClassAttendance classAttendance =reqBody.get();
//        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
//        String l_standard=classAttendance.getStandard();
//        String l_section=classAttendance.getSection();
//        String l_date=classAttendance.getDate();
//        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//        String l_year=convertedDate.getYear();
//        String l_month=convertedDate.getMonth(); 
//        String[] l_masterPkey={l_standard,l_section,l_year,l_month};
//        DBRecord attendanceMasterRecord=null;
//        String  previousMonthAudit=null;
//        Map<String,DBRecord>l_attendanceMap=null;
//        
//        try{
//        
//            attendanceMasterRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS", "CLASS_ATTENDANCE_MASTER", l_masterPkey, session, dbSession);
//            previousMonthAudit=attendanceMasterRecord.getRecord().get(4).trim();
//            l_attendanceMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS", "CLASS_ATTENDANCE_DETAIL", session, dbSession);
//        
//         }catch(DBValidationException ex){
//                    dbg("exception in view operation"+ex);
//                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
//                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
//                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
//                        session.getErrorhandler().log_app_error("BS_VAL_013", null);
//                        throw new BSValidationException("BSValidationException");
//                        
//                    }else{
//                        
//                        throw ex;
//                    }
//            }
//        
//        
//        buildBOfromDB(previousMonthAudit,l_attendanceMap);
//        
//          dbg("end of  completed student attendance--->view");   
//        }catch(BSValidationException ex){
//         throw ex;  
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//        }
//    }
    
    
    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside class attendance--->view");    
        BusinessService bs=inject.getBusinessService(session); 
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassAttendance> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        ClassAttendance classAttendance =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_standard=classAttendance.getStandard();
        String l_section=classAttendance.getSection();
        String l_date=classAttendance.getDate();
        DBRecord attendanceMasterRecord=null;
        String[] l_masterPkey={l_standard,l_section,l_date};
        Map<String,DBRecord>l_attendanceMap=null;
        
        try{
        
            attendanceMasterRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Attendance_"+l_date,"CLASS", "CLASS_ATTENDANCE_MASTER_HISTORY", l_masterPkey, session, dbSession);
            l_attendanceMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Attendance_"+l_date,"CLASS", "CLASS_ATTENDANCE_DETAIL_HISTORY", session, dbSession);
        
         }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", null);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
        
        
        buildBOfromDB(attendanceMasterRecord,l_attendanceMap);
        
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
    
    
    
    
//      private void buildBOfromDB(String p_previousMonthAudit,Map<String,DBRecord>l_attendanceMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
//    try{
//           dbg("inside buildBOfromDB"); 
////           IPDataService pds=inject.getPdataservice();
//           BusinessService bs=inject.getBusinessService(session);
//           IPDataService pds=inject.getPdataservice();
//           IBDProperties i_db_properties=session.getCohesiveproperties();
//           String l_instituteID=request.getReqHeader().getInstituteID();
//           RequestBody<ClassAttendance> reqBody = request.getReqBody();
//           ClassAttendance classAttendance =reqBody.get();
//           String l_date=classAttendance.getDate();
//           ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//           String l_day=convertedDate.getDay();
//           String l_standard=classAttendance.getStandard();
//           String l_section=classAttendance.getSection();
//           String[] l_pkey={l_instituteID,l_standard,l_section};
//           ArrayList<String>classConfigList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
//           String attendancetype=classConfigList.get(13).trim();
////           String referenceID=getReferenceID();
//           
//           AuditDetails audit=getAuditDetails(p_previousMonthAudit,l_day);
//           request.getReqAudit().setMakerID(audit.getMakerID());
//           request.getReqAudit().setCheckerID(audit.getCheckerID());
//           request.getReqAudit().setMakerDateStamp(audit.getMakerDateStamp());
//           request.getReqAudit().setCheckerDateStamp(audit.getCheckerDateStamp());
//           request.getReqAudit().setRecordStatus(audit.getRecordStatus());
//           request.getReqAudit().setAuthStatus(audit.getAuthStatus());
//           request.getReqAudit().setVersionNumber(audit.getVersionNo());
//           request.getReqAudit().setMakerRemarks(audit.getMakerRemarks());
//           request.getReqAudit().setCheckerRemarks(audit.getCheckerRemarks());
//           
//           
//           Map<String,Map<String,String[]>>l_parsedMap=parseAttendanceFromBO(l_attendanceMap,l_day);
//           
//           Map<String,String[]>l_foreNoonMap=l_parsedMap.get("ForeNoon");
//           dbg("l_foreNoonMap size"+l_foreNoonMap.size());
//           
//           dbg("fore noon iteration starts");
//           classAttendance.foreNoonAttendance=new StudentWiseAttendance[l_foreNoonMap.size()];
//           Iterator<String> studentIterator=l_foreNoonMap.keySet().iterator();
//           int i=0;
//           while(studentIterator.hasNext()){
//               
//               String l_studentID=studentIterator.next();
//               dbg("l_studentID"+l_studentID);
//               classAttendance.foreNoonAttendance[i]=new StudentWiseAttendance();
//               classAttendance.foreNoonAttendance[i].setStudentID(l_studentID);
//               String studentName=bs.getStudentName(l_studentID, l_instituteID, session, dbSession, inject);
//               classAttendance.foreNoonAttendance[i].setStudentName(studentName);
//               String[] periodArray=l_foreNoonMap.get(l_studentID);
//               dbg("periodArray length"+periodArray.length);
//               classAttendance.foreNoonAttendance[i].periodAttendance=new PeriodAttendance[periodArray.length-1];
//               
//               for(int j=1;j<periodArray.length;j++){
//                   classAttendance.foreNoonAttendance[i].periodAttendance[j-1]=new PeriodAttendance();
//                   String periodNumber=null;
//                   String attendance=null;
//                   if(attendancetype.equals("P")){
//                   
//                       periodNumber=periodArray[j].substring(0, 1);
//                       attendance=periodArray[j].substring(1);
//                   
//                   }else{
//                       
//                       periodNumber="";
//                       attendance=periodArray[j].substring(0, 1);
//                   }
//                   
//                   dbg("periodNumber"+periodNumber);
//                   dbg("attendance"+attendance);
//                   classAttendance.foreNoonAttendance[i].periodAttendance[j-1].setPeriodNumber(periodNumber);
//                   classAttendance.foreNoonAttendance[i].periodAttendance[j-1].setAttendance(attendance);
//                   
//               }
//               i++;
//           }
//            dbg("fore noon iteration ends");
//           
//           
//          if(!attendancetype.equals("D")){
//            
//           Map<String,String[]>l_afterNoonMap=l_parsedMap.get("AfterNoon");
//           dbg("l_afterNoonMap size"+l_afterNoonMap.size());
//           dbg("after noon iteration starts");
//           classAttendance.afterNoonAttendance=new StudentWiseAttendance[l_afterNoonMap.size()];
//           Iterator<String> studentIteratorAfterNoon=l_afterNoonMap.keySet().iterator();
//           int k=0;
//           while(studentIteratorAfterNoon.hasNext()){
//               
//               String l_studentID=studentIteratorAfterNoon.next();
//               dbg("l_studentID"+l_studentID);
//               classAttendance.afterNoonAttendance[k]=new StudentWiseAttendance();
//               classAttendance.afterNoonAttendance[k].setStudentID(l_studentID);
//               String studentName=bs.getStudentName(l_studentID, l_instituteID, session, dbSession, inject);
//               classAttendance.afterNoonAttendance[k].setStudentName(studentName);
//               String[] periodArray=l_afterNoonMap.get(l_studentID);
//               dbg("periodArray length"+periodArray.length);
//               classAttendance.afterNoonAttendance[k].periodAttendance=new PeriodAttendance[periodArray.length-1];
//               
//               for(int j=1;j<periodArray.length;j++){
//                   
//                   String periodNumber=null;
//                   String attendance=null;
//                   if(attendancetype.equals("P")){
//                   
//                       periodNumber=periodArray[j].substring(0, 1);
//                       attendance=periodArray[j].substring(1);
//                   
//                   }else{
//                       
//                       periodNumber="";
//                       attendance=periodArray[j].substring(0, 1);
//                   }
//                   dbg("periodNumber"+periodNumber);
//                   dbg("attendance"+attendance);
//                   classAttendance.afterNoonAttendance[k].periodAttendance[j-1]=new PeriodAttendance();
//                   classAttendance.afterNoonAttendance[k].periodAttendance[j-1].setPeriodNumber(periodNumber);
//                   classAttendance.afterNoonAttendance[k].periodAttendance[j-1].setAttendance(attendance);
//               }
//               k++;
//           }
//           dbg("after noon iteration ends");
//        
//            }
//           
//           
//           
//            
//          dbg("end of  buildBOfromDB"); 
//        }catch(BSValidationException ex){
//         throw ex;
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//        }
// }
    
     private void buildBOfromDB(DBRecord attendanceMasterRecord,Map<String,DBRecord>l_attendanceDetailMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
//           IPDataService pds=inject.getPdataservice();
           BusinessService bs=inject.getBusinessService(session);
           IPDataService pds=inject.getPdataservice();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           String l_instituteID=request.getReqHeader().getInstituteID();
           RequestBody<ClassAttendance> reqBody = request.getReqBody();
           ClassAttendance classAttendance =reqBody.get();
           String l_date=classAttendance.getDate();
           ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
           String l_day=convertedDate.getDay();
           String l_standard=classAttendance.getStandard();
           String l_section=classAttendance.getSection();
           String[] l_pkey={l_instituteID,l_standard,l_section};
           ArrayList<String>classConfigList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
           String attendancetype=classConfigList.get(13).trim();


           request.getReqAudit().setMakerID(attendanceMasterRecord.getRecord().get(3).trim());
           request.getReqAudit().setCheckerID(attendanceMasterRecord.getRecord().get(4).trim());
           request.getReqAudit().setMakerDateStamp(attendanceMasterRecord.getRecord().get(5).trim());
           request.getReqAudit().setCheckerDateStamp(attendanceMasterRecord.getRecord().get(6).trim());
           request.getReqAudit().setRecordStatus(attendanceMasterRecord.getRecord().get(7).trim());
           request.getReqAudit().setAuthStatus(attendanceMasterRecord.getRecord().get(8).trim());
           request.getReqAudit().setVersionNumber(attendanceMasterRecord.getRecord().get(9).trim());
           request.getReqAudit().setMakerRemarks(attendanceMasterRecord.getRecord().get(10).trim());
           request.getReqAudit().setCheckerRemarks(attendanceMasterRecord.getRecord().get(11).trim());
           
           
           Map<String,Map<String,String[]>>l_parsedMap=parseAttendanceFromBO(l_attendanceDetailMap,l_day);
           
           Map<String,String[]>l_foreNoonMap=l_parsedMap.get("ForeNoon");
           dbg("l_foreNoonMap size"+l_foreNoonMap.size());
           
           dbg("fore noon iteration starts");
           classAttendance.foreNoonAttendance=new StudentWiseAttendance[l_foreNoonMap.size()];
           Iterator<String> studentIterator=l_foreNoonMap.keySet().iterator();
           int i=0;
           while(studentIterator.hasNext()){
               
               String l_studentID=studentIterator.next();
               dbg("l_studentID"+l_studentID);
               classAttendance.foreNoonAttendance[i]=new StudentWiseAttendance();
               classAttendance.foreNoonAttendance[i].setStudentID(l_studentID);
               String studentName=bs.getStudentName(l_studentID, l_instituteID, session, dbSession, inject);
               classAttendance.foreNoonAttendance[i].setStudentName(studentName);
               String[] periodArray=l_foreNoonMap.get(l_studentID);
               dbg("periodArray length"+periodArray.length);
               classAttendance.foreNoonAttendance[i].periodAttendance=new PeriodAttendance[periodArray.length-1];
               
               for(int j=1;j<periodArray.length;j++){
                   classAttendance.foreNoonAttendance[i].periodAttendance[j-1]=new PeriodAttendance();
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
                   classAttendance.foreNoonAttendance[i].periodAttendance[j-1].setPeriodNumber(periodNumber);
                   classAttendance.foreNoonAttendance[i].periodAttendance[j-1].setAttendance(attendance);
                   
               }
               i++;
           }
            dbg("fore noon iteration ends");
           
           
          if(!attendancetype.equals("D")){
            
           Map<String,String[]>l_afterNoonMap=l_parsedMap.get("AfterNoon");
           dbg("l_afterNoonMap size"+l_afterNoonMap.size());
           dbg("after noon iteration starts");
           classAttendance.afterNoonAttendance=new StudentWiseAttendance[l_afterNoonMap.size()];
           Iterator<String> studentIteratorAfterNoon=l_afterNoonMap.keySet().iterator();
           int k=0;
           while(studentIteratorAfterNoon.hasNext()){
               
               String l_studentID=studentIteratorAfterNoon.next();
               dbg("l_studentID"+l_studentID);
               classAttendance.afterNoonAttendance[k]=new StudentWiseAttendance();
               classAttendance.afterNoonAttendance[k].setStudentID(l_studentID);
               String studentName=bs.getStudentName(l_studentID, l_instituteID, session, dbSession, inject);
               classAttendance.afterNoonAttendance[k].setStudentName(studentName);
               String[] periodArray=l_afterNoonMap.get(l_studentID);
               dbg("periodArray length"+periodArray.length);
               classAttendance.afterNoonAttendance[k].periodAttendance=new PeriodAttendance[periodArray.length-1];
               
               for(int j=1;j<periodArray.length;j++){
                   
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
                   classAttendance.afterNoonAttendance[k].periodAttendance[j-1]=new PeriodAttendance();
                   classAttendance.afterNoonAttendance[k].periodAttendance[j-1].setPeriodNumber(periodNumber);
                   classAttendance.afterNoonAttendance[k].periodAttendance[j-1].setAttendance(attendance);
               }
               k++;
           }
           dbg("after noon iteration ends");
        
            }
           
           
           
            
          dbg("end of  buildBOfromDB"); 
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
    
    
    
    
    
    
    
//      private Map<String,Map<String,String[]>> parseAttendanceFromBO(Map<String,DBRecord>l_attendanceMap,String l_day)throws BSProcessingException{
//          
//      try{
//          Map<String,Map<String,String[]>>parsedMap=new HashMap();
//          String referenceID=getReferenceID();
//          Map<String,List<DBRecord>> studentWiseGroup=l_attendanceMap.values().stream().filter(rec->rec.getRecord().get(0).trim().equals(referenceID)).collect(Collectors.groupingBy(rec->rec.getRecord().get(1).trim()));
//          dbg("studentWiseGroup size"+studentWiseGroup.size());
//          Iterator<String>studentIterator=studentWiseGroup.keySet().iterator();
//          Map<String,String[]>studentForeNoonMap=new HashMap();
//           Map<String,String[]>studentAfterNoonMap=new HashMap();
//           IPDataService pds=inject.getPdataservice();
//           IBDProperties i_db_properties=session.getCohesiveproperties();
//           String l_instituteID=request.getReqHeader().getInstituteID();
//           RequestBody<ClassAttendance> reqBody = request.getReqBody();
//           ClassAttendance classAttendance =reqBody.get();
//           String l_standard=classAttendance.getStandard();
//           String l_section=classAttendance.getSection();
//           String[] l_pkey={l_instituteID,l_standard,l_section};
//           ArrayList<String>classConfigList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
//           String attendancetype=classConfigList.get(13).trim();
//           while(studentIterator.hasNext()){
//                   
//                    String l_studentID=studentIterator.next();
//                    dbg("l_studentID"+l_studentID);
//                    ArrayList<String>l_studentAttendanceList=studentWiseGroup.get(l_studentID).get(0).getRecord();
//                    String monthAttendance=l_studentAttendanceList.get(2).trim();
//                    dbg("monthAttendance"+monthAttendance);
//                    getMaxVersionAttendanceOftheDay (monthAttendance,l_day) ; 
//                    String max_version_Attendance=filtermap_dummy.get(l_day);
//                    dbg("max_version_Attendance"+max_version_Attendance);
//                    String[] dayArray=max_version_Attendance.split(",");
//                    String attendanceRecord= dayArray[0];
//                    dbg("attendanceRecord"+attendanceRecord);
//                    String l_foreNoonAttendance=attendanceRecord.split("n")[0];
//                    dbg("l_foreNoonAttendance"+l_foreNoonAttendance);
//                    
//                    String[] foreNoonArray=  l_foreNoonAttendance.split("p");
//                    studentForeNoonMap.put(l_studentID, foreNoonArray);
//                    
//                    if(!attendancetype.equals("D")){
//                    
//                        String l_afterNoonAttendance=attendanceRecord.split("n")[1];
//                        dbg("l_afterNoonAttendance"+l_afterNoonAttendance);
//                        String[] afterNoonArray=  l_afterNoonAttendance.split("p");
//                        studentAfterNoonMap.put(l_studentID, afterNoonArray);
//                    
//                    }
//                    
//                    dbg("foreNoonArray size"+foreNoonArray.length);
//                    dbg("period Iteration starts");
//
//                  }
//                  Iterator<String>foreNoonIterator=studentForeNoonMap.keySet().iterator();
//           
//                  while(foreNoonIterator.hasNext()){
//                      
//                      String l_studentID=foreNoonIterator.next();
//                       dbg("fore noon l_studentID"+l_studentID);
//                      String[] periodArray=studentForeNoonMap.get(l_studentID);
//                      
//                      for(int i=0;i<periodArray.length;i++){
//                          
//                          String periodNo_attendance=periodArray[i];
//                          dbg("foreNoon periodNo_attendance"+periodNo_attendance);
//                      }
//                      
//                  }
//                  
//                  
//                   if(!attendancetype.equals("D")){
//                  
//                  Iterator<String>afterNoonIterator=studentAfterNoonMap.keySet().iterator();
//                  
//                  while(afterNoonIterator.hasNext()){
//                      
//                      String l_studentID=afterNoonIterator.next();
//                      dbg("after noon l_studentID"+l_studentID);
//                      String[] periodArray=studentAfterNoonMap.get(l_studentID);
//                      
//                      for(int i=0;i<periodArray.length;i++){
//                          
//                          String periodNo_attendance=periodArray[i];
//                          dbg("after noon periodNo_attendance"+periodNo_attendance);
//                      }
//                      
//                  }
//                  
//                   }
//           
//                   parsedMap.put("ForeNoon", studentForeNoonMap);
//                   parsedMap.put("AfterNoon", studentAfterNoonMap);
//          
//          return parsedMap;
//  
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());   
//        }
//          
//          
//      }
      
       private Map<String,Map<String,String[]>> parseAttendanceFromBO(Map<String,DBRecord>l_attendanceMap,String l_day)throws BSProcessingException{
          
      try{
          Map<String,Map<String,String[]>>parsedMap=new HashMap();
          
          Map<String,String[]>studentForeNoonMap=new HashMap();
           Map<String,String[]>studentAfterNoonMap=new HashMap();
           IPDataService pds=inject.getPdataservice();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           String l_instituteID=request.getReqHeader().getInstituteID();
           RequestBody<ClassAttendance> reqBody = request.getReqBody();
           ClassAttendance classAttendance =reqBody.get();
           String l_standard=classAttendance.getStandard();
           String l_section=classAttendance.getSection();
           String l_date=classAttendance.getDate();
           String[] l_pkey={l_instituteID,l_standard,l_section};
           ArrayList<String>classConfigList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
           String attendancetype=classConfigList.get(13).trim();
           Map<String,List<DBRecord>> studentWiseGroup=l_attendanceMap.values().stream().filter(rec->rec.getRecord().get(2).trim().equals(l_date)).collect(Collectors.groupingBy(rec->rec.getRecord().get(3).trim()));
           dbg("studentWiseGroup size"+studentWiseGroup.size());
           Iterator<String>studentIterator=studentWiseGroup.keySet().iterator();
           
           
           while(studentIterator.hasNext()){
                   
                    String l_studentID=studentIterator.next();
                    dbg("l_studentID"+l_studentID);
                    ArrayList<String>l_studentAttendanceList=studentWiseGroup.get(l_studentID).get(0).getRecord();
                    String attendanceRecord=l_studentAttendanceList.get(4).trim();
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
      
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside student attendance buildJsonResFromBO");   
//        IPDataService pds=inject.getPdataservice();
        RequestBody<ClassAttendance> reqBody = request.getReqBody();
        ClassAttendance classAttendance =reqBody.get();
//        IBDProperties i_db_properties =session.getCohesiveproperties();
        JsonArrayBuilder periodAttendane=Json.createArrayBuilder();
        JsonArrayBuilder foreNoonAttendance=Json.createArrayBuilder();
        JsonArrayBuilder afterNoonAttendance=Json.createArrayBuilder();
//        JsonArrayBuilder studentArrayBuilder=Json.createArrayBuilder();
//        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_standard=classAttendance.getStandard();
        String l_section=classAttendance.getSection();
        dbg("standard"+l_standard);
        dbg("section"+l_section);
           
//        Map<String,ArrayList<String>>l_periodList=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_PERIOD_MASTER",session,dbSession);
//           
//        Map<String,List<ArrayList<String>>>foreNoonPeriodList=l_periodList.values().stream().filter(rec->rec.get(0).trim().equals(l_standard)&&rec.get(1).trim().equals(l_section)&&rec.get(5).trim().equals("F")).collect(Collectors.groupingBy(rec->rec.get(2).trim()));
//        Map<String,List<ArrayList<String>>>afterNoonPeriodList=l_periodList.values().stream().filter(rec->rec.get(0).trim().equals(l_standard)&&rec.get(1).trim().equals(l_section)&&rec.get(5).trim().equals("A")).collect(Collectors.groupingBy(rec->rec.get(2).trim()));
//        
//        Iterator<String> foreNoonIterator=foreNoonPeriodList.keySet().iterator();
//        Iterator<String> afterNoonIterator=afterNoonPeriodList.keySet().iterator();
//        while(foreNoonIterator.hasNext()){
//            
//            String l_periodNO=foreNoonIterator.next();
//            dbg("foreNoon periodNO"+l_periodNO);
//            
//        }
//        while(afterNoonIterator.hasNext()){
//            
//            String l_periodNO=afterNoonIterator.next();
//            dbg("afterNoon periodNO"+l_periodNO);
//            
//        }
        
        dbg("foreNoonBuilding starts");
        for(int i=0;i<classAttendance.foreNoonAttendance.length;i++){
               dbg("periodAttendance"+classAttendance.foreNoonAttendance[i].periodAttendance.length);
             for(int j=0;j<classAttendance.foreNoonAttendance[i].periodAttendance.length;j++){
                  dbg("periodNo"+classAttendance.foreNoonAttendance[i].periodAttendance[j].getPeriodNumber());
                  dbg("attendance"+classAttendance.foreNoonAttendance[i].periodAttendance[j].getAttendance());
                  periodAttendane.add(Json.createObjectBuilder().add("periodNumber", classAttendance.foreNoonAttendance[i].periodAttendance[j].getPeriodNumber())
                                                                 .add("attendance", classAttendance.foreNoonAttendance[i].periodAttendance[j].getAttendance()));
            
             }
             foreNoonAttendance.add(Json.createObjectBuilder().add("studentID", classAttendance.foreNoonAttendance[i].getStudentID())
                                                              .add("studentName", classAttendance.foreNoonAttendance[i].getStudentName())
                                                              .add("period", periodAttendane));
        } 
        dbg("foreNoonBuilding ends");
        
        dbg("afterNoonBuilding starts");
        
        if(classAttendance.afterNoonAttendance!=null){
        
        for(int i=0;i<classAttendance.afterNoonAttendance.length;i++){
             periodAttendane=Json.createArrayBuilder();
             dbg("periodAttendance"+classAttendance.afterNoonAttendance[i].periodAttendance.length);
             for(int j=0;j<classAttendance.afterNoonAttendance[i].periodAttendance.length;j++){
                   dbg("periodNo"+classAttendance.afterNoonAttendance[i].periodAttendance[j].getPeriodNumber());
                   dbg("attendance"+classAttendance.afterNoonAttendance[i].periodAttendance[j].getAttendance());
                  periodAttendane.add(Json.createObjectBuilder().add("periodNumber", classAttendance.afterNoonAttendance[i].periodAttendance[j].getPeriodNumber())
                                                                 .add("attendance", classAttendance.afterNoonAttendance[i].periodAttendance[j].getAttendance()));
            
             }
             afterNoonAttendance.add(Json.createObjectBuilder().add("studentID", classAttendance.afterNoonAttendance[i].getStudentID())
                                                               .add("studentName", classAttendance.afterNoonAttendance[i].getStudentName())
                                                              .add("period", periodAttendane));
        }
        }
        dbg("afterNoonBuilding ends");
        
        
        
//        body=Json.createObjectBuilder().add("standard", classAttendance.getStandard())
//                                       .add("section", classAttendance.getSection())
//                                       .add("date", classAttendance.getDate())
//                                       .add("foreNoon",foreNoonAttendance)
//                                       .add("afterNoon",afterNoonAttendance)
//                                       .build();


          body=Json.createObjectBuilder().add("class", classAttendance.getStandard()+"/"+classAttendance.getSection())
                                         .add("date", classAttendance.getDate())
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
       
       if(!(l_operation.equals("View"))&&!l_operation.equals("Create-Default")&&!l_operation.equals("Delete")){
           
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
        RequestBody<ClassAttendance> reqBody = request.getReqBody();
        ClassAttendance classAttendance =reqBody.get();
        
//         if(classAttendance.getStandard()==null){
//             status=false;  
//             errhandler.log_app_error("BS_VAL_002","standard");  
//         }
//         if(classAttendance.getSection()==null){
//             status=false;  
//             errhandler.log_app_error("BS_VAL_002","section");  
//         }


          if(classAttendance.getDate()==null||classAttendance.getDate().isEmpty()){
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
             RequestBody<ClassAttendance> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             ClassAttendance classAttendance =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_standard=classAttendance.getStandard();
             String l_section=classAttendance.getSection();
             String l_date=classAttendance.getDate();
             
             if(!bsv.standardSectionValidation(l_standard,l_section, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Class");
             }
             
             

                 if(!bsv.dateFormatValidation(l_date, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","Date");
                 }
             
             
//             if(!bsv.pastDateValidation(l_date, session, dbSession, inject)){
//                 status=false;
//                 errhandler.log_app_error("BS_VAL_003","Date");
//             }


             if(!bsv.futureDateValidation(l_date, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Date");
             }
             
            
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
        RequestBody<ClassAttendance> reqBody = request.getReqBody();
        ClassAttendance classAttendance =reqBody.get();
        
        try{
            IBDProperties i_db_properties=session.getCohesiveproperties();
            String l_standard=classAttendance.getStandard();
            String l_section=classAttendance.getSection();
            String l_instituteID=request.getReqHeader().getInstituteID();
            IPDataService pds=inject.getPdataservice();
            String[] l_pkey={l_instituteID,l_standard,l_section};
            ArrayList<String>classConfigList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
            String attendancetype=classConfigList.get(13).trim();
            dbg("attendancetype"+attendancetype);
            dbg("inside student attendance detailMandatoryValidation");
           
            
            
            if(classAttendance.foreNoonAttendance==null||classAttendance.foreNoonAttendance.length==0){
                status=false;  
                errhandler.log_app_error("BS_VAL_002","Fore noon attendance"); 
            }else{
                
                for(int i=0;i<classAttendance.foreNoonAttendance.length;i++){
                    
                    if(classAttendance.foreNoonAttendance[i].studentID==null||classAttendance.foreNoonAttendance[i].studentID.isEmpty()){
                        status=false;  
                        errhandler.log_app_error("BS_VAL_002","Student ID"); 
                    }else{
                        
                        for(int j=0;j<classAttendance.foreNoonAttendance[i].periodAttendance.length;j++){
                            
                           if(attendancetype.equals("P")){ 
                            
                            if(classAttendance.foreNoonAttendance[i].periodAttendance[j].getPeriodNumber()==null||classAttendance.foreNoonAttendance[i].periodAttendance[j].getPeriodNumber().isEmpty()){
                               status=false;  
                               errhandler.log_app_error("BS_VAL_002","PeriodNumber"); 
                            }
                            
                           }
                            if(classAttendance.foreNoonAttendance[i].periodAttendance[j].getAttendance()==null||classAttendance.foreNoonAttendance[i].periodAttendance[j].getAttendance().isEmpty()){
                               status=false;  
                               errhandler.log_app_error("BS_VAL_002","Attendance"); 
                            }
                        }
                    }
                }
            }
            
             if(!attendancetype.equals("D")){ 
            
                    if(classAttendance.afterNoonAttendance==null||classAttendance.afterNoonAttendance.length==0){
                        status=false;  
                        errhandler.log_app_error("BS_VAL_002","After noon attendance"); 
                    }
             else{
                
                for(int i=0;i<classAttendance.afterNoonAttendance.length;i++){
                    
                    if(classAttendance.afterNoonAttendance[i].studentID==null||classAttendance.afterNoonAttendance[i].studentID.isEmpty()){
                        status=false;  
                        errhandler.log_app_error("BS_VAL_002","Student ID"); 
                    }else{
                        
                        for(int j=0;j<classAttendance.afterNoonAttendance[i].periodAttendance.length;j++){
                            
                        if(attendancetype.equals("P")){
                                       
                             if(classAttendance.afterNoonAttendance[i].periodAttendance[j].getPeriodNumber()==null||classAttendance.afterNoonAttendance[i].periodAttendance[j].getPeriodNumber().isEmpty()){
                               status=false;  
                               errhandler.log_app_error("BS_VAL_002","Period Number"); 
                             }
                            
                        }  
                            
                            
                            if(classAttendance.afterNoonAttendance[i].periodAttendance[j].getAttendance()==null||classAttendance.afterNoonAttendance[i].periodAttendance[j].getAttendance().isEmpty()){
                               status=false;  
                               errhandler.log_app_error("BS_VAL_002","Attendance"); 
                            }
                        }
                    }
                }
            }
             }
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
             RequestBody<ClassAttendance> reqBody = request.getReqBody();
             ClassAttendance classAttendance =reqBody.get();
             BSValidation bsv=inject.getBsv(session);
             String l_instituteID=request.getReqHeader().getInstituteID();
             IPDataService pds=inject.getPdataservice();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             BusinessService bs=inject.getBusinessService(session);
             String l_standard=classAttendance.getStandard();
             String l_section=classAttendance.getSection();
             ArrayList<String>foreNoonList=new ArrayList();
            
             String[] l_pkey={l_instituteID,l_standard,l_section};
             ArrayList<String>classConfigList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
             String attendancetype=classConfigList.get(13).trim();
             dbg("attendancetype"+attendancetype);
             
             Map<String,AttendanceValidation>foreNoonValidation=new HashMap();
             Map<String,AttendanceValidation>afterNoonValidation=new HashMap();
             
             
             if(attendancetype.equals("P")){
                 
                 Map<String,ArrayList<String>>l_periodList=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_PERIOD_MASTER",session,dbSession);
                 dbg("l_periodList size"+l_periodList.size());
                 
                 String masterVersion=bs.getMaxVersionOfTheClass(l_instituteID, l_standard, l_section, session, dbSession, inject);
                 int versionIndex=bs.getVersionIndexOfTheTable("IVW_PERIOD_MASTER", "INSTITUTE", session, dbSession, inject);
                 
                 Map<String,List<ArrayList<String>>>foreNoonPeriodList=l_periodList.values().stream().filter(rec->rec.get(1).trim().equals(l_standard)&&rec.get(2).trim().equals(l_section)&&rec.get(9).trim().equals("F")&&rec.get(versionIndex).trim().equals(masterVersion)).collect(Collectors.groupingBy(rec->rec.get(3).trim()));
                 dbg("foreNoonPeriodList size"+foreNoonPeriodList.size());
                 Map<String,List<ArrayList<String>>>afterNoonPeriodList=l_periodList.values().stream().filter(rec->rec.get(1).trim().equals(l_standard)&&rec.get(2).trim().equals(l_section)&&rec.get(9).trim().equals("A")&&rec.get(versionIndex).trim().equals(masterVersion)).collect(Collectors.groupingBy(rec->rec.get(3).trim()));
                 dbg("afterNoonPeriodList size"+afterNoonPeriodList.size());
                 
                 
                 Iterator<String>foreNoonIterator=foreNoonPeriodList.keySet().iterator();
                 
                 while(foreNoonIterator.hasNext()){
                     
                     String periodNumber=foreNoonIterator.next();
                     foreNoonValidation.put(periodNumber, new AttendanceValidation());
                     
                 }
                 
                 Iterator<String>afterNoonIterator=afterNoonPeriodList.keySet().iterator();
                 
                 while(afterNoonIterator.hasNext()){
                     
                     String periodNumber=afterNoonIterator.next();
                     afterNoonValidation.put(periodNumber, new AttendanceValidation());
                     
                 }
                 
             }else if(attendancetype.equals("N")){
                 
                 foreNoonValidation.put("", new AttendanceValidation());
                 afterNoonValidation.put("", new AttendanceValidation());
                 
             }else {
                 
                 foreNoonValidation.put("", new AttendanceValidation());
             }
             
             for(int k=0;k<classAttendance.foreNoonAttendance.length;k++){
             
                 String l_studentID=classAttendance.foreNoonAttendance[k].getStudentID();
                 if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","StudentID");
                 }
                  
             int periodSize=classAttendance.foreNoonAttendance[k].getPeriodAttendance().length;
             
                 if(!bsv.periodSizeValidation(periodSize, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","Period Size");
                 }
           
             
             for(int i=0;i<classAttendance.foreNoonAttendance[k].getPeriodAttendance().length;i++){
                 
                 String l_periodNo=classAttendance.foreNoonAttendance[k].getPeriodAttendance()[i].getPeriodNumber();
                 String l_attendance=classAttendance.foreNoonAttendance[k].getPeriodAttendance()[i].getAttendance();
                 
                 if(!foreNoonList.contains(l_studentID+l_periodNo)){
                     
                     foreNoonList.add(l_studentID+l_periodNo);
                 }else{
                      status=false;
                      errhandler.log_app_error("BS_VAL_031","fore noon attendance");
                      throw new BSValidationException("BSValidationException");
                 }
                 
                 if(attendancetype.equals("P")){
                 
                 if(!bsv.periodNumberValidation(l_periodNo, session, dbSession, inject)){
                     status=false;
                    errhandler.log_app_error("BS_VAL_003","PeriodNo");
                 }
                 
                 }
                 
                 if(!bsv.attendanceCharachterValidation(l_attendance, session, dbSession, inject)){
                     status=false;
                    errhandler.log_app_error("BS_VAL_003","Attendance");
                 }
                 
                AttendanceValidation attendanceValidation= foreNoonValidation.get(l_periodNo);
                 
                 if(l_attendance.equals("A")){
                     
                     attendanceValidation.setA_precence(true);
                     
                 }else if(l_attendance.equals("P")){
                     
                     attendanceValidation.setP_precence(true);
                 }else if(l_attendance.equals("-")){
                     
                     attendanceValidation.setHyf_precence(true);
                 }
                 
                 foreNoonValidation.put(l_periodNo, attendanceValidation);
                 
               }
             
             }
             
             
             ArrayList<String>afterNoonList=new ArrayList();
             
             
             if(!attendancetype.equals("D")){
             
             for(int k=0;k<classAttendance.afterNoonAttendance.length;k++){
             
                 String l_studentID=classAttendance.afterNoonAttendance[k].getStudentID();
                 

                 if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","StudentID");
                 }
                  
             int periodSize=classAttendance.afterNoonAttendance[k].getPeriodAttendance().length;
             
                 if(!bsv.periodSizeValidation(periodSize, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","Period Size");
                 }
             
             
             for(int i=0;i<classAttendance.afterNoonAttendance[k].getPeriodAttendance().length;i++){
                 
                 String l_periodNo=classAttendance.afterNoonAttendance[k].getPeriodAttendance()[i].getPeriodNumber();
                 String l_attendance=classAttendance.afterNoonAttendance[k].getPeriodAttendance()[i].getAttendance();
                 
                 if(!afterNoonList.contains(l_studentID+l_periodNo)){
                     
                     afterNoonList.add(l_studentID+l_periodNo);
                 }else{
                      status=false;
                      errhandler.log_app_error("BS_VAL_031","after noon attendance");
                      throw new BSValidationException("BSValidationException");
                 }
                 
                if(attendancetype.equals("P")){ 
                 
                 if(!bsv.periodNumberValidation(l_periodNo, session, dbSession, inject)){
                     status=false;
                    errhandler.log_app_error("BS_VAL_003","PeriodNo");
                 }
                 
                }
                 if(!bsv.attendanceCharachterValidation(l_attendance, session, dbSession, inject)){
                     status=false;
                    errhandler.log_app_error("BS_VAL_003","Attendance");
                 }
                 
                 AttendanceValidation attendanceValidation= afterNoonValidation.get(l_periodNo);
                 
                 if(l_attendance.equals("A")){
                     
                     attendanceValidation.setA_precence(true);
                     
                 }else if(l_attendance.equals("P")){
                     
                     attendanceValidation.setP_precence(true);
                 }else if(l_attendance.equals("-")){
                     
                     attendanceValidation.setHyf_precence(true);
                 }
                 
                 afterNoonValidation.put(l_periodNo, attendanceValidation);
                 
                 
                 
               }
             
             }
             }
             
             dbg("fore noon partial attendance validation starts");
                 
                 Iterator<String>foreNoonIterator=foreNoonValidation.keySet().iterator();
                 
                 while(foreNoonIterator.hasNext()){
                     
                     String periodNumber=foreNoonIterator.next();
                     
                     AttendanceValidation attendanceValidation= foreNoonValidation.get(periodNumber);
                     
                     if(attendanceValidation.isHyf_precence()){
                         
                         if(attendanceValidation.isP_precence()||attendanceValidation.isA_precence()){
                             
                             status=false;
                             errhandler.log_app_error("BS_VAL_035",periodNumber);
                         }
                     }
                     
                     if(status==false)
                     break;
                   }
                 
                 dbg("fore noon partial attendance validation ends");
                 
                 
                 if(!attendancetype.equals("D")){
                 
                     dbg("after noon partial attendance validation starts");
                     
                     Iterator<String>afterNoonIterator=afterNoonValidation.keySet().iterator();
         
                     while(afterNoonIterator.hasNext()){
                     
                     String periodNumber=afterNoonIterator.next();
                     
                     AttendanceValidation attendanceValidation= afterNoonValidation.get(periodNumber);
                     
                     if(attendanceValidation.isHyf_precence()){
                         
                         if(attendanceValidation.isP_precence()||attendanceValidation.isA_precence()){
                             
                             status=false;
                             errhandler.log_app_error("BS_VAL_035",periodNumber);
                         }
                     }
                     
                     if(status==false)
                     break;
                 }
                 
                     
                     dbg("after noon partial attendance validation ends");
                 
             }
             
             
             dbg("partial attendance validation starts");
             
             
            dbg("end of student attendance detailDataValidation");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch(DBValidationException ex){
            throw ex;
        } catch(BSValidationException ex){
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
        dbg("inside ClassAttendance--->getExistingAudit") ;
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
        if(!l_operation.equals("Create")&&!l_operation.equals("Create-Default")&&!l_operation.equals("View")){
             
              if(l_operation.equals("AutoAuth")&&l_versionNumber.equals("1")){
                return null;
              }else{  
               dbg("inside ClassAttendance--->getExistingAudit--->Service--->UserAttendance");  
               RequestBody<ClassAttendance> classAttendanceBody = request.getReqBody();
               ClassAttendance classAttendance =classAttendanceBody.get();
               String l_standard=classAttendance.getStandard();
               String l_section=classAttendance.getSection();
               String l_date=classAttendance.getDate();
//               ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//               String l_year=convertedDate.getYear();
//               String l_month=convertedDate.getMonth(); 
//               String l_day=convertedDate.getDay();
               String[] l_primaryKey={l_standard,l_section,l_date};
               
               DBRecord attendanceRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Attendance_"+l_date,"CLASS", "CLASS_ATTENDANCE_MASTER_HISTORY", l_primaryKey, session, dbSession);
//               String previousMonthAudit=attendanceRecord.getRecord().get(4).trim();
               
               
//               AuditDetails audit= getAuditDetails (previousMonthAudit,l_day)  ;            
               
               
               exAudit.setAuthStatus(attendanceRecord.getRecord().get(8).trim());
               exAudit.setMakerID(attendanceRecord.getRecord().get(3).trim());
               exAudit.setRecordStatus(attendanceRecord.getRecord().get(7).trim());
               exAudit.setVersionNumber(Integer.parseInt(attendanceRecord.getRecord().get(9).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of ClassAttendance--->getExistingAudit") ;
        }
        }else{
            return null;
        }
//      }catch(BSValidationException ex){
//         throw ex;  
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
    
    private Map<String,String> getAttendanceFromBO(boolean forHistory)throws BSProcessingException{
        
        try{
            dbg("inside getAttendanceFromBO");
            RequestBody<ClassAttendance> reqBody = request.getReqBody();
            ClassAttendance classAttendance =reqBody.get();
            Map<String,String>classAttendanceMap=new HashMap();
            String l_versionNumber=request.getReqAudit().getVersionNumber();
            String l_instituteID=request.getReqHeader().getInstituteID();
            String l_standard=classAttendance.getStandard();
            String l_section=classAttendance.getSection();
            IPDataService pds=inject.getPdataservice();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            String[] l_pkey={l_instituteID,l_standard,l_section};
             ArrayList<String>classConfigList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
             String attendancetype=classConfigList.get(13).trim();
             dbg("attendancetype"+attendancetype);
            
            dbg("foreNoon iteration starts");
            for(int i=0;i<classAttendance.foreNoonAttendance.length;i++){
                
              String l_studentID=classAttendance.foreNoonAttendance[i].getStudentID();
                String l_foreNoonattendanceString=new String();
                for(int j=0;j<classAttendance.foreNoonAttendance[i].periodAttendance.length;j++){
                    
                    String periodNo=classAttendance.foreNoonAttendance[i].periodAttendance[j].getPeriodNumber();
                    String attendance=classAttendance.foreNoonAttendance[i].periodAttendance[j].getAttendance();
                    dbg("periodNo"+periodNo);
                    dbg("attendance"+attendance);
                    
                    
                    l_foreNoonattendanceString=l_foreNoonattendanceString.concat("p").concat(periodNo).concat(attendance);
                    
                        
                }
                
                 if(!attendancetype.equals("D")){

                    classAttendanceMap.put(l_studentID,l_foreNoonattendanceString);
                
                 }else{
                     classAttendanceMap.put(l_studentID,l_foreNoonattendanceString+"n");
                 }
                
                
            }
            dbg("foreNoon iteration ends");
            
            if(!attendancetype.equals("D")){
            
            dbg("afterNoon iteration starts");
            for(int i=0;i<classAttendance.afterNoonAttendance.length;i++){
                
              String l_studentID=classAttendance.afterNoonAttendance[i].getStudentID();
                String l_afterNoonattendanceString=new String();
                for(int j=0;j<classAttendance.afterNoonAttendance[i].periodAttendance.length;j++){
                    
                    String periodNo=classAttendance.afterNoonAttendance[i].periodAttendance[j].getPeriodNumber();
                    String attendance=classAttendance.afterNoonAttendance[i].periodAttendance[j].getAttendance();
                    dbg("periodNo"+periodNo);
                    dbg("attendance"+attendance);
                    l_afterNoonattendanceString=l_afterNoonattendanceString.concat("p").concat(periodNo).concat(attendance);
                }
                
               String foreNoonString= classAttendanceMap.get(l_studentID);
//               String combinedAttendance=foreNoonString+"n"+l_afterNoonattendanceString+","+l_versionNumber;
              String combinedAttendance;
              if(forHistory){

                 combinedAttendance=foreNoonString+"n"+l_afterNoonattendanceString;
              }else{
               
                 combinedAttendance=foreNoonString+"n"+l_afterNoonattendanceString+","+l_versionNumber; 
                  
                }
                classAttendanceMap.replace(l_studentID, combinedAttendance);
               
            }
            
            
            }
            
           dbg("afterNoon iteration ends");
            
            return classAttendanceMap;

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
            RequestBody<ClassAttendance> classAttendanceBody = request.getReqBody();
            ClassAttendance classAttendance =classAttendanceBody.get();
            String l_date=classAttendance.getDate();
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
     

//    private String dbFormatConversion(String p_day, PeriodAttendance[] periodAttendance)throws BSProcessingException {
//     
//         try{
//             dbg("inside dbFormatConversion");
//            String dbFormat; 
//            String attendanceString=  getAttendanceFromBO(periodAttendance);
//            String l_versionNumber=request.getReqAudit().getVersionNumber();
//            
//            String auditString=",".concat(l_versionNumber);
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
    
private String setDayDBFromatAttendanceRecord(String monthAttendance,String dayAttendance,String p_day,String p_operation) throws BSProcessingException {
    
    try{
        dbg("inside setDayDBFromatAttendanceRecord");
        dbg("monthAttendance"+monthAttendance);
        dbg("dayAttendance"+dayAttendance);
        dbg("p_day"+p_day);
        dbg("p_operation"+p_operation);
        String dbFormatMonthAttendance;
        if(p_operation.equals("C")){
            
            dbFormatMonthAttendance=monthAttendance.replace("d"+p_day+" "+"d", "d"+p_day+dayAttendance+"d");
        }else{
        
            getMaxVersionAttendanceOftheDay (monthAttendance,p_day) ; 
            String max_version_Attendance=filtermap_dummy.get(p_day);
            dbFormatMonthAttendance=  monthAttendance.replace(max_version_Attendance+"d", p_day+dayAttendance+"d");
        
        }
        
         dbg("dbFormatMonthAttendance"+dbFormatMonthAttendance);
         dbg("end of setDayDBFromatAttendanceRecord");
                return dbFormatMonthAttendance;

         }catch(Exception ex){
            dbg(ex);
          throw new BSProcessingException("BSProcessingException"+ex.toString());
       }
        
        
    }
    private void createDBFormatConversion(String p_previousAttendance,String p_day,String dayAttendance,String p_studentID) throws BSProcessingException,DBValidationException,DBProcessingException  {
    
    try{
          dbg("inside createDBFormatConversion");
          dbg("p_previousAttendance"+p_previousAttendance);
          dbg("p_day"+p_day);
          IBDProperties i_db_properties=session.getCohesiveproperties();
          BusinessService bs=inject.getBusinessService(session);
          IDBTransactionService dbts=inject.getDBTransactionService();
          RequestBody<ClassAttendance> classAttendanceBody = request.getReqBody();
          String l_instituteID=request.getReqHeader().getInstituteID();
          ClassAttendance classAttendance =classAttendanceBody.get();
          String versionNumber=request.getReqAudit().getVersionNumber();
          dbg("versionNumber"+versionNumber);
          String newAttendance;
          if(versionNumber.equals("1")){
              
              newAttendance=p_previousAttendance.replace("d"+p_day+" "+"d", "d"+p_day+dayAttendance+"d");
          }else{
          
          newAttendance=p_previousAttendance.concat(p_day+dayAttendance+"d");
          }
          
          dbg("newAttendance"+newAttendance);
          Map<String,String>l_columnToUpdate=new HashMap();
          l_columnToUpdate.put("3", newAttendance);
           
          String l_standard=classAttendance.getStandard();
          String l_section=classAttendance.getSection();
          String referenceID=getReferenceID();
          String[] l_primaryKey={referenceID,p_studentID};
           
          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS", "CLASS_ATTENDANCE_DETAIL", l_primaryKey, l_columnToUpdate, session);
            
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

private void updateDBFormatConversion(String p_previousAttendance,String p_day,String p_operation,String p_dayAttendance,String p_studentID) throws BSProcessingException,DBValidationException,DBProcessingException  {
    
    try{
          dbg("inside updateDBFormatConversion");
          dbg("p_previousAttendance"+p_previousAttendance);
          dbg("p_day"+p_day);
          dbg("p_operation"+p_operation);
          IBDProperties i_db_properties=session.getCohesiveproperties();
          BusinessService bs=inject.getBusinessService(session);
          IDBTransactionService dbts=inject.getDBTransactionService();
          RequestBody<ClassAttendance> classAttendanceBody = request.getReqBody();
          String l_instituteID=request.getReqHeader().getInstituteID();
          ClassAttendance classAttendance =classAttendanceBody.get();
          String monthAttendance= setDayDBFromatAttendanceRecord(p_previousAttendance,p_dayAttendance,p_day,p_operation);
            
          Map<String,String>l_columnToUpdate=new HashMap();
          l_columnToUpdate.put("3", monthAttendance);
           
          String l_standard=classAttendance.getStandard();
          String l_section=classAttendance.getSection();
          String referenceID=getReferenceID();
          String[] l_primaryKey={referenceID,p_studentID};
           
          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS", "CLASS_ATTENDANCE_DETAIL", l_primaryKey, l_columnToUpdate, session);
            
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
    
private void deleteDBFormatConversion(String p_previousAttendance,String p_day,String p_studentID) throws BSProcessingException,DBValidationException,DBProcessingException {
    
    try{
          dbg("inside deleteDBFormatConversion");
          dbg("p_previousAttendance"+p_previousAttendance);
          dbg("p_day"+p_day);
          IBDProperties i_db_properties=session.getCohesiveproperties();
          BusinessService bs=inject.getBusinessService(session);
          IDBTransactionService dbts=inject.getDBTransactionService();
          RequestBody<ClassAttendance> classAttendanceBody = request.getReqBody();
          String l_instituteID=request.getReqHeader().getInstituteID();
          ClassAttendance classAttendance =classAttendanceBody.get();
          getMaxVersionAttendanceOftheDay (p_previousAttendance,p_day) ; 
          String max_version_Attendance=filtermap_dummy.get(p_day);
          dbg("max_version_Attendance"+max_version_Attendance);
          String dbFormatMonthAttendance=  p_previousAttendance.replace(max_version_Attendance+"d", p_day+" "+"d");            
          dbg("dbFormatMonthAttendance"+dbFormatMonthAttendance);
          Map<String,String>l_columnToUpdate=new HashMap();
          l_columnToUpdate.put("3", dbFormatMonthAttendance);
           
          String l_standard=classAttendance.getStandard();
          String l_section=classAttendance.getSection();
          String referenceID=getReferenceID();
          String[] l_primaryKey={referenceID,p_studentID};
           
          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS", "CLASS_ATTENDANCE_DETAIL", l_primaryKey, l_columnToUpdate, session);
            
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

private void createDefault()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside class attendance--->createdefault");    
        BusinessService bs=inject.getBusinessService(session); 
        IPDataService pds=inject.getPdataservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassAttendance> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        ClassAttendance classAttendance =reqBody.get();
        String l_standard=classAttendance.getStandard();
        String l_section=classAttendance.getSection();
        String l_date=classAttendance.getDate();
        
        Map<String,ArrayList<String>>studentMasterMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", session, dbSession);
        dbg("studentMasterMap size"+studentMasterMap.size());
        Map<String,List<ArrayList<String>>>l_studentGroup=studentMasterMap.values().stream().filter(rec->rec.get(2).trim().equals(l_standard)&&rec.get(3).trim().equals(l_section)&&rec.get(8).trim().equals("O")).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
        dbg("l_studentGroup size"+l_studentGroup.size());
        Iterator<String>studentIterator=l_studentGroup.keySet().iterator();
        
        String[] l_pkey={l_instituteID,l_standard,l_section};
        ArrayList<String>classConfigList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
        String attendancetype=classConfigList.get(13).trim();
        dbg("attendancetype"+attendancetype);
        
        ArrayList<String>leaveList=getLeaveDetails(l_date);
        
        if(attendancetype.equals("P")){
                    Map<String,ArrayList<String>>l_periodList=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_PERIOD_MASTER",session,dbSession);
                    dbg("l_periodList size"+l_periodList.size());
                   String masterVersion=bs.getMaxVersionOfTheClass(l_instituteID, l_standard, l_section, session, dbSession, inject);
                 int versionIndex=bs.getVersionIndexOfTheTable("IVW_PERIOD_MASTER", "INSTITUTE", session, dbSession, inject);
                 
                 Map<String,List<ArrayList<String>>>foreNoonPeriodList=l_periodList.values().stream().filter(rec->rec.get(1).trim().equals(l_standard)&&rec.get(2).trim().equals(l_section)&&rec.get(9).trim().equals("F")&&rec.get(versionIndex).trim().equals(masterVersion)).collect(Collectors.groupingBy(rec->rec.get(3).trim()));
                 dbg("foreNoonPeriodList size"+foreNoonPeriodList.size());
                 Map<String,List<ArrayList<String>>>afterNoonPeriodList=l_periodList.values().stream().filter(rec->rec.get(1).trim().equals(l_standard)&&rec.get(2).trim().equals(l_section)&&rec.get(9).trim().equals("A")&&rec.get(versionIndex).trim().equals(masterVersion)).collect(Collectors.groupingBy(rec->rec.get(3).trim()));
                 dbg("afterNoonPeriodList size"+afterNoonPeriodList.size());

                    classAttendance.foreNoonAttendance=new StudentWiseAttendance[l_studentGroup.size()];
                    dbg("fore noon iteration starts");
                    int i=0;
                    while(studentIterator.hasNext()){

                        String studentID=studentIterator.next();
                        dbg("studentID"+studentID);
                        classAttendance.foreNoonAttendance[i]=new StudentWiseAttendance();
                        classAttendance.foreNoonAttendance[i].setStudentID(studentID);
                        String l_studentName=bs.getStudentName(studentID, l_instituteID, session, dbSession, inject);
                        classAttendance.foreNoonAttendance[i].setStudentName(l_studentName);
                        Iterator<String>periodIterator=foreNoonPeriodList.keySet().iterator();
                        classAttendance.foreNoonAttendance[i].periodAttendance=new PeriodAttendance[foreNoonPeriodList.size()];
                 
                        String attendanceString=null;

                        if(leaveList!=null&&leaveList.contains(studentID+"~"+"ForeNoon")){
                            
                            attendanceString="L";
                        }else{
                            
                            attendanceString="P";
                        }
                        
                        int j=0;
                        while(periodIterator.hasNext()){

                            String l_periodNumber=periodIterator.next();
                            dbg("l_periodNumber"+l_periodNumber);
                            
                            classAttendance.foreNoonAttendance[i].periodAttendance[j]=new PeriodAttendance();
                            classAttendance.foreNoonAttendance[i].periodAttendance[j].setPeriodNumber(l_periodNumber);
                            classAttendance.foreNoonAttendance[i].periodAttendance[j].setAttendance(attendanceString);
                          j++;  
                        }
                        i++;
                    }
                    dbg("fore noon iteration ends");

                    dbg("after noon iteration starts");
                    Iterator<String>studentIteratorAfterNoon=l_studentGroup.keySet().iterator();
                    classAttendance.afterNoonAttendance=new StudentWiseAttendance[l_studentGroup.size()];
                    int k=0;
                    while(studentIteratorAfterNoon.hasNext()){

                        String studentID=studentIteratorAfterNoon.next();
                        classAttendance.afterNoonAttendance[k]=new StudentWiseAttendance();
                        classAttendance.afterNoonAttendance[k].setStudentID(studentID);
                        String l_studentName=bs.getStudentName(studentID, l_instituteID, session, dbSession, inject);
                        classAttendance.afterNoonAttendance[k].setStudentName(l_studentName);
                        Iterator<String>periodIterator=afterNoonPeriodList.keySet().iterator();
                        classAttendance.afterNoonAttendance[k].periodAttendance=new PeriodAttendance[afterNoonPeriodList.size()];
                 
                        String attendanceString=null;

                        if(leaveList!=null&&leaveList.contains(studentID+"~"+"AfterNoon")){
                            
                            attendanceString="L";
                        }else{
                            
                            attendanceString="P";
                        }
                        
                        int j=0;
                        while(periodIterator.hasNext()){

                            String l_periodNumber=periodIterator.next();
                            dbg("l_periodNumber"+l_periodNumber);
                             classAttendance.afterNoonAttendance[k].periodAttendance[j]=new PeriodAttendance();
                            classAttendance.afterNoonAttendance[k].periodAttendance[j].setPeriodNumber(l_periodNumber);
                            classAttendance.afterNoonAttendance[k].periodAttendance[j].setAttendance(attendanceString);
                          j++;  
                        }
                        k++;
                    }
      
                    dbg("after noon iteration ends");
                    
                    
        }else if(attendancetype.equals("N")){
                
                classAttendance.foreNoonAttendance=new StudentWiseAttendance[l_studentGroup.size()];
                dbg("fore noon iteration starts");
                
                int i=0;
                while(studentIterator.hasNext()){

                    String studentID=studentIterator.next();
                    dbg("studentID"+studentID);
                    classAttendance.foreNoonAttendance[i]=new StudentWiseAttendance();
                    classAttendance.foreNoonAttendance[i].setStudentID(studentID);
                    String l_studentName=bs.getStudentName(studentID, l_instituteID, session, dbSession, inject);
                    classAttendance.foreNoonAttendance[i].setStudentName(l_studentName);
                    
                        String attendanceString=null;

                        if(leaveList!=null&&leaveList.contains(studentID+"~"+"ForeNoon")){
                            
                            attendanceString="L";
                        }else{
                            
                            attendanceString="P";
                        }
                    classAttendance.foreNoonAttendance[i].periodAttendance=new PeriodAttendance[1];
                    String l_periodNumber="";
                    dbg("l_periodNumber"+l_periodNumber);
                    classAttendance.foreNoonAttendance[i].periodAttendance[0]=new PeriodAttendance();
                    classAttendance.foreNoonAttendance[i].periodAttendance[0].setPeriodNumber(l_periodNumber);
                    classAttendance.foreNoonAttendance[i].periodAttendance[0].setAttendance(attendanceString);
                    i++;
                }
            
                studentIterator= l_studentGroup.keySet().iterator();
                classAttendance.afterNoonAttendance=new StudentWiseAttendance[l_studentGroup.size()];
                dbg("fore noon iteration starts");
                int j=0;
                while(studentIterator.hasNext()){

                    String studentID=studentIterator.next();
                    dbg("studentID"+studentID);
                    classAttendance.afterNoonAttendance[j]=new StudentWiseAttendance();
                    classAttendance.afterNoonAttendance[j].setStudentID(studentID);
                    String l_studentName=bs.getStudentName(studentID, l_instituteID, session, dbSession, inject);
                    classAttendance.afterNoonAttendance[j].setStudentName(l_studentName);
                    String attendanceString=null;

                        if(leaveList!=null&&leaveList.contains(studentID+"~"+"AfterNoon")){
                            
                            attendanceString="L";
                        }else{
                            
                            attendanceString="P";
                        }
                    classAttendance.afterNoonAttendance[j].periodAttendance=new PeriodAttendance[1];
                    String l_periodNumber="";
                    dbg("l_periodNumber"+l_periodNumber);
                    classAttendance.afterNoonAttendance[j].periodAttendance[0]=new PeriodAttendance();
                    classAttendance.afterNoonAttendance[j].periodAttendance[0].setPeriodNumber(l_periodNumber);
                    classAttendance.afterNoonAttendance[j].periodAttendance[0].setAttendance(attendanceString);
                    j++;
                }
                
        }else if(attendancetype.equals("D")){
                
                classAttendance.foreNoonAttendance=new StudentWiseAttendance[l_studentGroup.size()];
                dbg("fore noon iteration starts");
                int i=0;
                while(studentIterator.hasNext()){

                    String studentID=studentIterator.next();
                    dbg("studentID"+studentID);
                    classAttendance.foreNoonAttendance[i]=new StudentWiseAttendance();
                    classAttendance.foreNoonAttendance[i].setStudentID(studentID);
                    String l_studentName=bs.getStudentName(studentID, l_instituteID, session, dbSession, inject);
                    classAttendance.foreNoonAttendance[i].setStudentName(l_studentName);
                    String attendanceString=null;

                        if(leaveList!=null&&(leaveList.contains(studentID+"~"+"AfterNoon")||leaveList.contains(studentID+"~"+"ForeNoon"))){
                            
                            attendanceString="L";
                        }else{
                            
                            attendanceString="P";
                        }
                    classAttendance.foreNoonAttendance[i].periodAttendance=new PeriodAttendance[1];
                    String l_periodNumber="";
                    dbg("l_periodNumber"+l_periodNumber);
                    classAttendance.foreNoonAttendance[i].periodAttendance[0]=new PeriodAttendance();
                    classAttendance.foreNoonAttendance[i].periodAttendance[0].setPeriodNumber(l_periodNumber);
                    classAttendance.foreNoonAttendance[i].periodAttendance[0].setAttendance(attendanceString);
                    i++;
                }

                
         }
                    
                    
                    
                    
                    
          dbg("end of  completed teacher attendance--->view");   
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


public ArrayList<String> getLeaveDetails(String date)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
    ArrayList<String>leaveList=new ArrayList();
    try{
        dbg("inside getLeaveDetails-->date-->"+date);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        RequestBody<ClassAttendance> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        ClassAttendance classAttendance =reqBody.get();
        String l_standard=classAttendance.getStandard();
        String l_section=classAttendance.getSection();
        String dateFormat=i_db_properties.getProperty("DATE_FORMAT");
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Map<String,DBRecord>classLeaveMap=null;
        
        try{
        
         
           classLeaveMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE", "CLASS_LEAVE_MANAGEMENT", session, dbSession);
        
        }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
//                        session.getErrorhandler().log_app_error("BS_VAL_013", "");
//                        throw new BSValidationException("BSValidationException");
                        return leaveList;
                    }else{
                        
                        throw ex;
                    }
                }
        
        
        
        dbg("classLeaveMap size"+classLeaveMap.size());
        
        Iterator<DBRecord>valueIterator=classLeaveMap.values().iterator();
        
        while(valueIterator.hasNext()){
            
            ArrayList<String>valueList=valueIterator.next().getRecord();
            String studentID=valueList.get(0).trim();
            String from=valueList.get(1).trim();
            String to=valueList.get(2).trim();
            String fromNoon=valueList.get(3).trim();
            String toNoon=valueList.get(4).trim();
            dbg("from"+from);
            dbg("to"+to);
            dbg("fromNoon"+fromNoon);
            dbg("toNoon"+toNoon);
            dbg("studentID"+studentID);
            
            Date attendanceDate=formatter.parse(date);
            Date fromDate=formatter.parse(from);
            Date toDate=formatter.parse(to);
            
            if(attendanceDate.compareTo(fromDate)>=0&&attendanceDate.compareTo(toDate)<=0){
                
                dbg("attendance date is in between from and to date");
                
                if(attendanceDate.compareTo(fromDate)==0){
                    
                    dbg("attendance date equal to fromDate");
                    
                                        
                     if(fromNoon.equals("F")){
                        
                        leaveList.add(studentID+"~"+"ForeNoon");
                    
                    }else if(fromNoon.equals("A")){


                        leaveList.add(studentID+"~"+"AfterNoon");
                    }else{
                        
                        leaveList.add(studentID+"~"+"ForeNoon");
                        leaveList.add(studentID+"~"+"AfterNoon");
                    }
                    
                }else if(attendanceDate.compareTo(toDate)==0){
                    
                    dbg("attendance date equal to fromDate");
                    
                    if(toNoon.equals("F")){
                    
                        
                        leaveList.add(studentID+"~"+"ForeNoon");
                    
                    }else if(toNoon.equals("A")){

                            leaveList.add(studentID+"~"+"AfterNoon");
                    }else{
                        
                        leaveList.add(studentID+"~"+"ForeNoon");
                        leaveList.add(studentID+"~"+"AfterNoon");
                    }
                    
                }else{
                    
                    dbg("attendance date not equal to fromDate");
                    
                    
                    leaveList.add(studentID+"~"+"ForeNoon");
                    
                    leaveList.add(studentID+"~"+"AfterNoon");
                    
                }
                
                
   
                
                
            }
            
        }
        
        dbg("leaveList size"+leaveList.size());
        
        for(int i=0;i<leaveList.size();i++){
            
            
            dbg("leave-->"+leaveList.get(i));
            
        }
        
        dbg("end of getLeaveDetails");
        return leaveList;
//    }catch(BSValidationException ex){
//         throw ex;  
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

private Map<String,String> getLeaveStudents()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
    
    
  try{  
    dbg("foreNoon iteration starts");
    RequestBody<ClassAttendance> reqBody = request.getReqBody();
    IPDataService pds=inject.getPdataservice();
    IBDProperties i_db_properties=session.getCohesiveproperties();
    String l_instituteID=request.getReqHeader().getInstituteID();
    ClassAttendance classAttendance =reqBody.get();
    String l_standard=classAttendance.getStandard();
    String l_section=classAttendance.getSection();
    Map<String,String>leaveMap=new HashMap();
    String[] l_pkey={l_instituteID,l_standard,l_section};
    ArrayList<String>classConfigList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
    String attendancetype=classConfigList.get(13).trim();
    dbg("attendancetype"+attendancetype);
        
        
            for(int i=0;i<classAttendance.foreNoonAttendance.length;i++){
                
              String l_studentID=classAttendance.foreNoonAttendance[i].getStudentID();
              boolean isLeaveOrAbsent=false;
               for(int j=0;j<classAttendance.foreNoonAttendance[i].periodAttendance.length;j++){
                    
                    String attendance=classAttendance.foreNoonAttendance[i].periodAttendance[j].getAttendance();
                    dbg("attendance"+attendance);
                        
                    if(attendance.equals("L")||attendance.equals("A")){
                        
                        isLeaveOrAbsent=true;
                        
                    }
                }
                
                 if(isLeaveOrAbsent==true){
                     
                     if(!attendancetype.equals("D")){
                    
                      leaveMap.put(l_studentID, "F");
                            
                     }else{
                         
                       leaveMap.put(l_studentID, "FD");
                     }
                      
                  }    
                
                
            }
            dbg("foreNoon iteration ends");
            
            if(!attendancetype.equals("D")){
            
            dbg("afterNoon iteration starts");
            for(int i=0;i<classAttendance.afterNoonAttendance.length;i++){
                
              String l_studentID=classAttendance.afterNoonAttendance[i].getStudentID();
              boolean isLeaveOrAbsent=false;
                for(int j=0;j<classAttendance.afterNoonAttendance[i].periodAttendance.length;j++){
                    
                    String attendance=classAttendance.afterNoonAttendance[i].periodAttendance[j].getAttendance();
                    dbg("attendance"+attendance);
                    
                    if(attendance.equals("L")||attendance.equals("A")){
                        
                        isLeaveOrAbsent=true;
                        
                    }
                    
                }
                
                
                 if(isLeaveOrAbsent==true){
                    
                     if(!leaveMap.containsKey(l_studentID)){
                     
                        leaveMap.put(l_studentID, "A");
                     }else{
                         
                         leaveMap.replace(l_studentID, "FD");
                         
                     }
                            
                  }  
                
            }
            
            
            }
            
           dbg("afterNoon iteration ends");
    
           return leaveMap;
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


      public void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
         
        try{
            
            dbg("inside relationshipProcessing");
            RequestBody<ClassAttendance> reqBody = request.getReqBody();
            
            BusinessService bs=inject.getBusinessService(session);
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            String l_instituteID=request.getReqHeader().getInstituteID();
            ClassAttendance classAttendance =reqBody.get();
            String l_standard=classAttendance.getStandard();
            String l_section=classAttendance.getSection();
            String l_attendanceDate=classAttendance.getDate();
            String currentDate=bs.getCurrentDate();
            
            if(l_attendanceDate.equals(currentDate)){
            
            
            Map<String,String>leaveMap=getLeaveStudents();
            
            Iterator<String>keyIterator=leaveMap.keySet().iterator();
            
            while(keyIterator.hasNext()){
                
                String studentID=keyIterator.next();
                String value=leaveMap.get(studentID);
                String fullDay=null;
                String noon=null;
                
                if(value.equals("FD")){
                    
                    fullDay="Y";
                    noon="";
                }else if(value.equals("F")){
                    
                    fullDay="N";
                    noon="F";
                }else if(value.equals("A")){
                    
                    fullDay="N";
                    noon="A";
                }
                
//                if(!checkHoliday(fullDay,noon)){
                boolean recordExistence=false;
                String[] l_primaryKey={studentID};
                    try{
                        
                        
                        
                        readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"INSTITUTE","IVW_STUDENT_LEAVE_DETAILS", l_primaryKey, session, dbSession);
                        
                        recordExistence=true;
                 }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        recordExistence=false;
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                    }else{
                        
                        throw ex;
                    }
            }
                    
                   
            if(recordExistence){
                
                Map<String,String>l_columnToUpdate=new HashMap();
                l_columnToUpdate.put("4", fullDay);
                l_columnToUpdate.put("5", noon);
                
                dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"INSTITUTE","IVW_STUDENT_LEAVE_DETAILS", l_primaryKey, l_columnToUpdate, session);
                
            }else{
                
                dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"INSTITUTE",321,studentID,l_standard,l_section,fullDay,noon);
            }        
                    
                    
                
                    
//                }
                
            }
            
            
//            notificationProcessing();

         IAttendanceNotificationService notificationProcessing=inject.getAttendanceNotificationService();
         
         
         ArrayList<String>studentList=this.getAbsentStudents();
         
        notificationProcessing.notificationProcessing(currentDate, l_instituteID, studentList);
         

            }
            
            dbg("end of relationshipProcessing");
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

 
      private void relationshipProceessingInBaseTables(String operation)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
        
    try{ 
        dbg("inside class attendance create"); 
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        BusinessService bs=inject.getBusinessService(session);
        RequestBody<ClassAttendance> reqBody = request.getReqBody();
        ErrorHandler errHandler=session.getErrorhandler();
        ClassAttendance classAttendance =reqBody.get();
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_standard=classAttendance.getStandard();
        String l_section=classAttendance.getSection();
        String l_date=classAttendance.getDate();
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth();
        String l_day=convertedDate.getDay();
        String previousMonthAttendance=null;
        String previousMonthAudit=null;
        String referenceID=getReferenceID();
        boolean recordExistence=true;
        String[] l_masterPkey={l_standard,l_section,l_year,l_month};
        dbg("l_standard"+l_standard);
        dbg("l_section"+l_section);
        dbg("l_date"+l_date);
        dbg("l_date"+l_date);
        dbg("l_year"+l_year);
        dbg("l_month"+l_month);
        dbg("l_day"+l_day);
        
        dbg("create in master table starts");
        
          try{
               DBRecord attendanceRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS", "CLASS_ATTENDANCE_MASTER", l_masterPkey, session, dbSession,true);
               previousMonthAudit=attendanceRecord.getRecord().get(4).trim();
               dbg("previousMonthAudit"+previousMonthAudit);
            }catch(DBValidationException ex){ 
                 if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                     
                     recordExistence=false;
                     errHandler.removeSessionErrCode("DB_VAL_011");
                     errHandler.removeSessionErrCode("DB_VAL_000");
                 }else{
                     throw ex;
                 }
            }
        
          dbg("master table record existence"+recordExistence);
            if(recordExistence) {
              dbg("inside record existence true");  
              String newMonthAudit= insertDayAuditInMonthAudit(previousMonthAudit,l_day,operation);
              dbg("newMonthAudit"+newMonthAudit);
              Map<String,String>l_columnToUpdate=new HashMap();
              l_columnToUpdate.put("5", newMonthAudit);
              dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS", "CLASS_ATTENDANCE_MASTER", l_masterPkey, l_columnToUpdate, session);
              dbg("update column called for master table");
            }else{
               dbg("inside record existence false");   
               String newMonthAudit= createNewMonthAudit();
               dbg("new month audit"+newMonthAudit);
               String monthAudit= insertDayAuditInMonthAudit(newMonthAudit,l_day,operation);
               dbg("monthAudit"+monthAudit);
               dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS",52,l_standard,l_section,l_year,l_month,monthAudit);
               dbg("create record called for master table");
            }
           dbg("create in master table ends");
    
            
        dbg("create in detail table starts");   
        
        
        if(!(request.getReqHeader().getOperation().equals("Auth")||request.getReqHeader().getOperation().equals("Reject"))){
        
        
        Map<String,String>l_studentWiseMap=getAttendanceFromBO(false);
        Iterator<String>studentIterator=l_studentWiseMap.keySet().iterator();
        
        while(studentIterator.hasNext()){
            recordExistence=true;
            String l_studentID=studentIterator.next();
            String dayAttendance=l_studentWiseMap.get(l_studentID);
            dbg("l_studentID"+l_studentID);
            dbg("dayAttendance"+dayAttendance);
            String[] l_primaryKey={referenceID,l_studentID};
            
            try{
               DBRecord attendanceRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS", "CLASS_ATTENDANCE_DETAIL", l_primaryKey, session, dbSession,true);
               previousMonthAttendance=attendanceRecord.getRecord().get(2).trim();
               dbg("previousMonthAttendance"+previousMonthAttendance);
            }catch(DBValidationException ex){ 
                 if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                     recordExistence=false;
                     errHandler.removeSessionErrCode("DB_VAL_011");
                     errHandler.removeSessionErrCode("DB_VAL_000");
                 }else{
                     throw ex;
                 }
            }
            
            
             if(recordExistence) {
                
               detailTableDBFormatConversion(previousMonthAttendance,l_day,dayAttendance,l_studentID,operation);
               
            }else{
               String monthAttendance= createMonthAttendanceRecord();
               dbg("monthAttendance"+monthAttendance);
               monthAttendance= setDayDBFromatAttendanceRecord(monthAttendance,dayAttendance,l_day,"C");
               dbg("monthAttendance after setting day attendance"+monthAttendance);
              dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS",53,referenceID,l_studentID,monthAttendance);
               dbg("create record called for detail table"+monthAttendance);
            }
            
        }
        
        
        }
        
        
        

        dbg("end of class attendance create"); 
        }catch(BSValidationException ex){
         throw ex;
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
    
    
    private void detailTableDBFormatConversion(String p_previousAttendance,String p_day,String dayAttendance,String p_studentID,String operation) throws BSProcessingException,DBValidationException,DBProcessingException  {
    
    try{
          dbg("inside createDBFormatConversion");
          dbg("p_previousAttendance"+p_previousAttendance);
          dbg("p_day"+p_day);
          IBDProperties i_db_properties=session.getCohesiveproperties();
          BusinessService bs=inject.getBusinessService(session);
          IDBTransactionService dbts=inject.getDBTransactionService();
          RequestBody<ClassAttendance> classAttendanceBody = request.getReqBody();
          String l_instituteID=request.getReqHeader().getInstituteID();
          ClassAttendance classAttendance =classAttendanceBody.get();
          String versionNumber=request.getReqAudit().getVersionNumber();
          dbg("versionNumber"+versionNumber);
          String newAttendance;
          
          
          if(versionNumber.equals("1")&&operation.equals("C")){
              
              newAttendance=p_previousAttendance.replace("d"+p_day+" "+"d", "d"+p_day+dayAttendance+"d");
          }else{
          
              getMaxVersionAttendanceOftheDay (p_previousAttendance,p_day) ; 
              String max_version_Attendance=filtermap_dummy.get(p_day);
              
              if(!operation.equals("D")){
              
              newAttendance=  p_previousAttendance.replace(max_version_Attendance+"d", p_day+dayAttendance+"d");
      
              
              }else{
                  
                  newAttendance=  p_previousAttendance.replace(max_version_Attendance+"d", p_day+" "+"d");
              }
              
          }
          
          
          
          dbg("newAttendance"+newAttendance);
          Map<String,String>l_columnToUpdate=new HashMap();
          l_columnToUpdate.put("3", newAttendance);
           
          String l_standard=classAttendance.getStandard();
          String l_section=classAttendance.getSection();
          String referenceID=getReferenceID();
          String[] l_primaryKey={referenceID,p_studentID};
           
          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS", "CLASS_ATTENDANCE_DETAIL", l_primaryKey, l_columnToUpdate, session);
            
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
      
      
      private ArrayList<String> getAbsentStudents()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
    
    
  try{  
    dbg("foreNoon iteration starts");
    RequestBody<ClassAttendance> reqBody = request.getReqBody();
    IPDataService pds=inject.getPdataservice();
    IBDProperties i_db_properties=session.getCohesiveproperties();
    String l_instituteID=request.getReqHeader().getInstituteID();
    ClassAttendance classAttendance =reqBody.get();
    String l_standard=classAttendance.getStandard();
    String l_section=classAttendance.getSection();
    ArrayList<String>absentList=new ArrayList();
    String[] l_pkey={l_instituteID,l_standard,l_section};
    ArrayList<String>classConfigList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
    String attendancetype=classConfigList.get(13).trim();
    dbg("attendancetype"+attendancetype);
        
        
            for(int i=0;i<classAttendance.foreNoonAttendance.length;i++){
                
              String l_studentID=classAttendance.foreNoonAttendance[i].getStudentID();
              boolean isLeaveOrAbsent=false;
               for(int j=0;j<classAttendance.foreNoonAttendance[i].periodAttendance.length;j++){
                    
                    String attendance=classAttendance.foreNoonAttendance[i].periodAttendance[j].getAttendance();
                    dbg("attendance"+attendance);
                        
                    if(attendance.equals("A")){
                        
                        isLeaveOrAbsent=true;
                        
                    }
                }
                
                 if(isLeaveOrAbsent==true){
                     
                      absentList.add(l_studentID);
                            
                      
                  }    
                
                
            }
            dbg("foreNoon iteration ends");
            
            if(!attendancetype.equals("D")){
            
            dbg("afterNoon iteration starts");
            for(int i=0;i<classAttendance.afterNoonAttendance.length;i++){
                
              String l_studentID=classAttendance.afterNoonAttendance[i].getStudentID();
              boolean isLeaveOrAbsent=false;
                for(int j=0;j<classAttendance.afterNoonAttendance[i].periodAttendance.length;j++){
                    
                    String attendance=classAttendance.afterNoonAttendance[i].periodAttendance[j].getAttendance();
                    dbg("attendance"+attendance);
                    
                    if(attendance.equals("L")){
                        
                        isLeaveOrAbsent=true;
                        
                    }
                    
                }
                
                
                 if(isLeaveOrAbsent==true){
                    
                     if(!absentList.contains(l_studentID)){
                     
                        absentList.add(l_studentID);
                     }
                            
                  }  
                
            }
            
            
            }
            
           dbg("afterNoon iteration ends");
    
           return absentList;
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
      
      
//      private void notificationProcessing ()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
//          
//          try{
//              
//              NotificationUtil notificationUtil=inject.getNotificationUtil(session);
//              String instituteID= request.getReqHeader().getInstituteID();
//              
//              ArrayList<String>studentList=this.getAbsentStudents();
//              
//              for(int i=0;i<studentList.size();i++){
//                  
//                  String studentID=studentList.get(i);
//                  
//                  if(!notificationUtil.checkAtteendanceMessageExistence(instituteID, studentID, session, dbSession, inject)){
//                      
//                      
//                     MessageInput messageInput= notificationUtil.getAttendanceMessageInput(studentID);
//                      
//                     notificationUtil.messageGeneration(instituteID, studentID, messageInput, session, dbSession, inject);
//                  }
//                  
//                  
//              }
//              
//              
//              
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//        }
//      }
//      
      
      
      private void notificationProcessing ()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
          
          try{
              
              dbg("inside notificationProcessing");
              NotificationUtil notificationUtil=inject.getNotificationUtil(session);
              String instituteID= request.getReqHeader().getInstituteID();
              String[] contractPkey={instituteID};
              String l_pkey=dbSession.getIibd_file_util().formingPrimaryKey(contractPkey);
              IBDProperties i_db_properties=session.getCohesiveproperties();
              IDBTransactionService dbts=inject.getDBTransactionService();
              ILockService lock=inject.getLockService();
              ITransactionControlService tc=inject.getTransactionControlService();
              BusinessService bs=inject.getBusinessService(session);
              BSValidation bsv=inject.getBsv(session);
              IAmazonSMSService smsService=inject.getAmazonSMSService();
              IAmazonEmailService emailService=inject.getAmazonEmailService();
              String fileName="APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive";
              String tableName="CONTRACT_MASTER";
              String message=this.getAttendanceMessageBody();
              RequestBody<ClassAttendance> reqBody = request.getReqBody();
              ClassAttendance classAttendance =reqBody.get();
              String date=classAttendance.getDate();
              
              ArrayList<String>studentList=this.getAbsentStudents();
              
              for(int i=0;i<studentList.size();i++){
                  
                  String studentID=studentList.get(i);
                  
                  dbg("sms processing started for "+ studentID);
                  
                  EndPoint endPoint=notificationUtil.getEndPoints(instituteID, studentID, session, dbSession, inject);
                  
                  
                  try{
                  
                  
                  if(dbts.getImplictRecordLock(fileName,tableName,l_pkey,this.session)==true){ 

                              if( lock.isSameSessionRecordLock(fileName,tableName,l_pkey, this.session)){

                                     if(bsv.smsUsageValidation(instituteID, session, dbSession, inject)) {  

                                        dbg("sms usage validation completed for "+studentID); 
                                        smsService.sendSMS(message, endPoint.getMobileNo(), session,instituteID);
                                        dbg("sms sent for"+studentID);
                                        bs.updateSMSUsage(instituteID, session, dbSession, inject,message);
                                        this.studentLevelStatusUpdate(studentID, date, endPoint.getMobileNo(), "S", "");
                                        this.studentLevelMessageUpdate(studentID, date, message);
                                         tc.commit(session, dbSession);
                                     }else {
                                         tc.rollBack(session, dbSession);
                                         
                                         throw new Exception("Limit Exhausted");
                                     }
                                      }
                                  }
                  
                  }catch(Exception ex){
                      
                      dbg(ex);
                      tc.rollBack(session, dbSession);
                      this.studentLevelStatusUpdate(studentID, date, endPoint.getMobileNo(), "F", ex.toString());
                      tc.commit(session, dbSession);
                  }
                  
                  
                  dbg("sms processing completed For"+studentID);
                  
                  
                  dbg("email processing started For"+studentID);
                  
                  try{
                  
                           if(dbts.getImplictRecordLock(fileName,tableName,l_pkey,this.session)==true){ 

                            if( lock.isSameSessionRecordLock(fileName,tableName,l_pkey, this.session)){

                                 if(bsv.emailUsageValidation(instituteID, session, dbSession, inject)) {    

                                     dbg("email usage validation completed for "+studentID);
                                          Email emailObj=this.getEmailObject(instituteID, message, endPoint.getEmailID());
                                          emailService.sendEmail(emailObj, session);
                                          dbg("emailsent for"+studentID);
                                          bs.updateEmailUsage(instituteID, session, dbSession, inject);
                                          this.studentLevelStatusUpdate(studentID, date, endPoint.getMobileNo(), "S", "");
                                          this.studentLevelMessageUpdate(studentID, date, message);
                                          tc.commit(session, dbSession);
                                 }else{
                                     tc.rollBack(session, dbSession);

                                     throw new Exception("Limit Exhausted");
                                 }

                         }

                        }
           
                  
                  }catch(Exception ex){
                      
                      dbg(ex);
                      tc.rollBack(session, dbSession);
                      this.studentLevelStatusUpdate(studentID, date, endPoint.getMobileNo(), "F", ex.toString());
                      tc.commit(session, dbSession);
                  }
                  
                  
                  dbg("email processing completed For"+studentID);
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
      }


private void studentLevelStatusUpdate(String studentID,String date,String endPoint,String status,String exception)throws  BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    
    try{

        dbg("inside studentLevelStatusUpdate");
        String[] dateArr=date.split("-");
        String notificationID="A"+dateArr[0]+dateArr[1]+dateArr[2];
        String[] statusPKey={studentID,notificationID,date,endPoint};
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBTransactionService dbts=inject.getDBTransactionService();
        String instituteID=request.getReqHeader().getInstituteID();
        boolean recordExistence=false;
        
        try{
            
            
            
           
            
            readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT", "STUDENT_NOTIFICATION_STATUS", statusPKey, session, dbSession);
            
            recordExistence=true;
            
        }catch(DBValidationException ex){
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        recordExistence=false;
                    }else{
                        
                        throw ex;
                    }
            }
        
        
        dbg("inside studentLevelStatusUpdate-->recordExistence-->"+recordExistence); 
        
        
        if(recordExistence){
            
            Map<String,String>l_column_to_update=new HashMap();
            l_column_to_update.put("5", status);
            l_column_to_update.put("6", exception);
            
             dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT", "STUDENT_NOTIFICATION_STATUS", statusPKey, l_column_to_update, session);
        }else{
            
            
           dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT",309,studentID,notificationID,date,endPoint,status,exception);
            
        }
        
        
        
        dbg("end of studentLevelStatusUpdate");
        
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












private void studentLevelMessageUpdate(String studentID,String date,String message)throws  BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    
    try{
        
        
        dbg("inside studentLevelMessageUpdate");
        dbg("inside studentLevelMessageUpdate--->message-->"+message);
        
        String[] dateArr=date.split("-");
        String notificationID="A"+dateArr[0]+dateArr[1]+dateArr[2];
        
        String[] statusPKey={studentID,notificationID,date};
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBTransactionService dbts=inject.getDBTransactionService();
        String instituteID=request.getReqHeader().getInstituteID();
        boolean recordExistence=false;
        
        try{
            
            
            
           
            
            readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT", "STUDENT_NOTIFICATION_MESSAGE", statusPKey, session, dbSession);
            
            recordExistence=true;
            
        }catch(DBValidationException ex){
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        recordExistence=false;
                    }else{
                        
                        throw ex;
                    }
            }
        
        dbg("inside studentLevelMessageUpdate--->recordExistence-->"+recordExistence);
        
        if(recordExistence){
            
            Map<String,String>l_column_to_update=new HashMap();
            l_column_to_update.put("4", message);
            
             dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT", "STUDENT_NOTIFICATION_MESSAGE", statusPKey, l_column_to_update, session);
        }else{
            
            
           dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","STUDENT",358,studentID,notificationID,date);
            
        }
        
        
        
        dbg("end of student level message update");
        
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














      
  private String getAttendanceMessageBody()throws  BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
      
      try{
      
          String messageHeader=new String();
          String messageFooter=new String();
          String messageBody=new String();
//          IPDataService pds=inject.getPdataservice();
          IDBReadBufferService readBuffer=inject.getDBReadBufferService();
          IBDProperties i_db_properties=session.getCohesiveproperties();
          String instituteID=request.getReqHeader().getInstituteID();
          String[] l_masterpkey={instituteID,"Attendance"};
          DBRecord templateMasterRecord=null;
          boolean masterRecordExistence=false;
          String[] pkey={instituteID};
          IPDataService pds=inject.getPdataservice();
          ArrayList<String>contractList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",pkey);
           String language=contractList.get(12).trim();
           dbg("language"+language);
           try{
              
              
              templateMasterRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Template","INSTITUTE","IVW_MESSAGE_TEMPLATE_MASTER", l_masterpkey,session,dbSession);
            
              String recStatus=templateMasterRecord.getRecord().get(6).trim();
              String authStatus=templateMasterRecord.getRecord().get(7).trim();
              
              if(recStatus.equals("O")&&authStatus.equals("A")){
                  
                  masterRecordExistence=true;
              }
              
              
          }catch(DBValidationException ex){
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        masterRecordExistence=false;
                    }else{
                        
                        throw ex;
                    }
            }

          
          if(masterRecordExistence){
              
              
          DBRecord templateHeaderRecord=null;
          String[] headerPkey={instituteID,"Attendance","H"};
           try{
              
              
              templateHeaderRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Template","INSTITUTE","IVW_MESSAGE_TEMPLATE_DETAIL", headerPkey,session,dbSession);
            
              if(language.equals("EN")){
                  
                 messageHeader=   templateHeaderRecord.getRecord().get(3).trim(); 
              
              }else{
                  
                 messageHeader=   templateHeaderRecord.getRecord().get(5).trim();  
              }
              
          }catch(DBValidationException ex){
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                    }else{
                        
                        throw ex;
                    }
            }

          
          DBRecord templateBodyRecord=null;
          String[] bodyPkey={instituteID,"Attendance","B"};
           try{
              
              
              templateBodyRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Template","INSTITUTE","IVW_MESSAGE_TEMPLATE_DETAIL", bodyPkey,session,dbSession);
             if(language.equals("EN")){
                  
              messageBody=   templateBodyRecord.getRecord().get(3).trim();              
              
             }else{
                 
                 messageBody=   templateBodyRecord.getRecord().get(5).trim(); 
             }
              
          }catch(DBValidationException ex){
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                    }else{
                        
                        throw ex;
                    }
            }

             
              DBRecord templateFooterRecord=null;
          String[] footerPkey={instituteID,"Attendance","F"};
           try{
              
              
              templateFooterRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Template","INSTITUTE","IVW_MESSAGE_TEMPLATE_DETAIL", footerPkey,session,dbSession);
            
               if(language.equals("EN")){
                  
              messageFooter=   templateFooterRecord.getRecord().get(3).trim();  
               }else{
              
                   messageFooter=   templateFooterRecord.getRecord().get(5).trim();
                   
               }
              
              
          }catch(DBValidationException ex){
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                    }else{
                        
                        throw ex;
                    }
            }
             
             
          }
          
          
          
          if(messageBody.isEmpty()){
              
              
              if(language.equals("EN")){
              
                  messageBody="Absent today , please contact school";
              
              }else{
                  
                  messageBody="இன்று வருகை இல்லை, தயவுசெய்து பள்ளியை தொடர்பு கொள்ளவும்";
              }
              
              
          }
          
          String message=messageHeader.concat(messageBody).concat(messageFooter);
          
          
          dbg("message"+message);
          
          return message;
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
                          +"<h5>Dear Parents,</h5>"
                          +"<p>Please find below today updates</p>"   
                          + "<p>"+message
                              +"</p>"
                            +"</br>"  
                            +"</br>"
                            +"<p>If you have any queries, please contact school</p>" 
                            +"<p>Thanks and Regards,<p>"
                            +"<p>"+instituteName+"<p>"
                            +"</br>"  
                            +"</br>"  
                            +"</br>"  
                            +"</br>"  
                            +"</br>"  
                   
                   +"<p> <u>This is Auto generated email , please do not reply</u></p>"
                              ;
                
            Email email=new  Email();
            
            email.setFromEmail(fromEmail);
            
            String email1=toEmail.replace("AATT;", "@");
            
            email.setToEmail(email1
            );
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
  
  
      
      
      
//    private boolean checkHoliday(String fullDay,String noon)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
//        boolean holidayStatus=false;
//      try{  
//        
//        RequestBody<ClassAttendance> reqBody = request.getReqBody();
//        ClassAttendance classAttendance =reqBody.get();  
//        BusinessService bs=inject.getBusinessService(session);
//        IPDataService pds=inject.getPdataservice();
//        String date=classAttendance.getDate();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        ConvertedDate converted=bs.getYearMonthandDay(date);
//        String l_year=converted.getYear();
//        String l_month=converted.getMonth();
//        String l_day=converted.getDay();  
//        String[] l_pkey={l_instituteID,l_year,l_month};  
//          
//        ArrayList<String> holidayMaintanenceRecord=pds.readRecordPData( session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_HOLIDAY_MAINTANENCE", l_pkey);
//           
//        String holidayString=holidayMaintanenceRecord.get(3);  
//        char[] holidayArr=holidayString.toCharArray();
//        char charForTheDay=holidayArr[Integer.parseInt(l_day)-1];
//        
//          if(fullDay.equals("Y")){
//              
//              if(charForTheDay!='W'){
//                  
//                  holidayStatus=true;
//              }
//              
//          }else if(noon.equals("F")){
//              
//              if(charForTheDay!='F'){
//                  
//                  holidayStatus=true;
//              }
//              
//          }else if(noon.equals("A")){
//              
//              if(charForTheDay!='A'){
//                  
//                  holidayStatus=true;
//              }
//          }
//        
//        return holidayStatus;
//        
////        }catch(BSValidationException ex){
////            throw ex;    
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//        } 
//    }
}
