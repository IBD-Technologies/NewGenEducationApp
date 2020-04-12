/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.summary.classattendance;

import com.ibd.businessViews.IClassAttendanceSummary;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.student.studentattendanceservice.AuditDetails;
import com.ibd.cohesive.app.business.student.summary.studentattendancesummary.MonthRecords;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
@Remote(IClassAttendanceSummary.class)
@Stateless
public class ClassAttendanceSummary implements IClassAttendanceSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    Map<String,String>audit_filtermap_dummy;
    String audit_filterkey_dummy;
    
    public ClassAttendanceSummary(){
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
       dbg("inside ClassAttendanceSummary--->processing");
       dbg("ClassAttendanceSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IClassAttendanceSummary>classAttendanceEJB=new BusinessEJB();
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
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,classAttendanceEJB);
              tc.commit(session,dbSession);
//              dbg("commit is called in class leave");
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
           audit_filtermap_dummy=null;
           audit_filterkey_dummy=null;
           request=null;
            bs=inject.getBusinessService(session);
            if(l_session_created_now){
                bs.responselogging(jsonResponse, inject,session,dbSession);
                dbg("Response-->"+jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
              clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes
//           if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
  //                 BSProcessingException ex=new BSProcessingException("response contains special characters");
    //               dbg(ex);
      //             session.clearSessionObject();
        //           dbSession.clearSessionObject();
          //         throw ex;
            //    }
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
      ClassAttendanceBO classAttendance=new ClassAttendanceBO();
      RequestBody<ClassAttendanceBO> reqBody = new RequestBody<ClassAttendanceBO>(); 
           
      try{
      dbg("inside class leave buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      classAttendance.filter=new ClassAttendanceFilter();
      BSValidation bsv=inject.getBsv(session);
//      classAttendance.filter.setStandard(l_filterObject.getString("standard"));
//      classAttendance.filter.setSection(l_filterObject.getString("section"));
      if(l_filterObject.getString("class").equals("Select option")){
          classAttendance.filter.setStandard("");
          classAttendance.filter.setSection("");
      }else{

          String l_class=l_filterObject.getString("class");
          bsv.classValidation(l_class,session);
          classAttendance.filter.setStandard(l_class.split("/")[0]);
          classAttendance.filter.setSection(l_class.split("/")[1]);
      
      }
      
      if(l_filterObject.getString("authStat").equals("Select option")){
          
          classAttendance.filter.setAuthStatus("");
      }else{
      
          classAttendance.filter.setAuthStatus(l_filterObject.getString("authStat"));
      }
      classAttendance.filter.setFromDate(l_filterObject.getString("fromDate"));
      classAttendance.filter.setToDate(l_filterObject.getString("toDate"));
      
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
     

    

//    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
//                
//        try{      
//        dbg("inside class leave--->view");
//        BusinessService bs=inject.getBusinessService(session);
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        RequestBody<ClassAttendanceBO> reqBody = request.getReqBody();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
//        ClassAttendanceBO classAttendance=reqBody.get();
//        String l_standard=classAttendance.getFilter().getStandard();
//        String l_section=classAttendance.getFilter().getSection();
//        String l_date=classAttendance.getFilter().getDate();
//        ArrayList<AttendanceResult>attResultList=new ArrayList();
//        
//        try{
//        
//            ArrayList<String>l_fileNames=bs.getClassFileNames(l_standard,l_section,request,session,dbSession,inject);
//            for(int i=0;i<l_fileNames.size();i++){
//            try{
//                dbg("inside file name iteration");
//               ArrayList<MonthRecords> l_monthRecords=new ArrayList();
//              String standard=l_fileNames.get(i).split("~")[0];
//              String section=l_fileNames.get(i).split("~")[1]; 
//               
//               if(l_date!=null&&!l_date.isEmpty()){
//                   
//                     ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//                     String l_year=convertedDate.getYear();
//                     String l_month=convertedDate.getMonth();
//                     String[] l_primaryKey={standard,section,l_year,l_month};
//                     DBRecord dbmonthRecord=null;
//                     try{
//
//                       dbmonthRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section,"CLASS", "CLASS_ATTENDANCE_MASTER",l_primaryKey, session, dbSession);
//
//                     }catch(DBValidationException ex){
//
//                        if(ex.toString().contains("DB_VAL_000")||ex.toString().contains("DB_VAL_011")){
//                
//                            session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
//                            session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
//
//                        }else{
//                            throw ex;
//                        }
//                     }
//                   
//                    if(dbmonthRecord!=null){ 
//                       MonthRecords monthRecord=new MonthRecords();
//                       monthRecord.setYear(l_year);
//                       monthRecord.setMonth(l_month);
//                       monthRecord.setMonthRecord(dbmonthRecord);
//                       l_monthRecords.add(monthRecord);
//                    }   
//                   
//               }else{
//                   
//                   String currentYearMonth=bs.getCurrentYearMonth();
//                   String year=currentYearMonth.split("~")[0];
//                   String month=currentYearMonth.split("~")[1];
//                   String currentDay=currentYearMonth.split("~")[2];
//                   
//                   String[] l_primaryKey={standard,section,year,month};
//                   DBRecord dbMonthRecord=null;
//                   
//                   try{
//                   
//                      dbMonthRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section,"CLASS", "CLASS_ATTENDANCE_MASTER",l_primaryKey, session, dbSession);
//                  
//                   }catch(DBValidationException ex){
//                     
//                     if(ex.toString().contains("DB_VAL_000")||ex.toString().contains("DB_VAL_011")){
//                
//                            session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
//                            session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
//
//                        }else{
//                            throw ex;
//                        }
//                 }
//                   
//                  if(dbMonthRecord!=null){   
//                       MonthRecords monthRecord=new MonthRecords();
//                       monthRecord.setYear(year);
//                       monthRecord.setMonth(month);
//                       monthRecord.setMonthRecord(dbMonthRecord);
//                       l_monthRecords.add(monthRecord);
//                  }
//                   
//                   
//                   if(Integer.parseInt(currentDay)<6){
//                   
//                       String previousYearMonth=bs.getPreviousYearMonth(year, month);
//                       String previousYear=previousYearMonth.split("~")[0];
//                       String previousMonth=previousYearMonth.split("~")[1];
//
//                       String[] l_pkey={standard,section,previousYear,previousMonth};
//                       DBRecord previousMonthRecord=null;
//                       
//                       try{
//                        
//                            previousMonthRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section,"CLASS", "CLASS_ATTENDANCE_MASTER",l_pkey, session, dbSession);
//
//                       }catch(DBValidationException ex){
//
//                             if(!(ex.toString().contains("DB_VAL_011"))){
//
//                                throw ex; 
//                             }
//                       }
//                      if(previousMonthRecord!=null){   
//                           MonthRecords monthRecord=new MonthRecords();
//                           monthRecord.setYear(previousYear);
//                           monthRecord.setMonth(previousYear);
//                           monthRecord.setMonthRecord(previousMonthRecord);
//                           l_monthRecords.add(monthRecord);
//                      }
//               
//                   }
//                   
//                   }
//                   
//                   
//                for(int k=0;k<l_monthRecords.size();k++){
//                  
//                   String l_monthAudit=l_monthRecords.get(k).getMonthRecord().getRecord().get(4).trim();
//                   String l_year=l_monthRecords.get(k).getYear();
//                   String l_month=l_monthRecords.get(k).getMonth();
//                   dbg("l_monthAudit"+l_monthAudit);
//                   String[] l_dayAuditArr=l_monthAudit.split("d");
//                   dbg("l_dayAuditArr size"+l_dayAuditArr.length);
//                   ArrayList<String>processedDays=new ArrayList();
//                   
//                   
//                   for(int j=1;j<l_dayAuditArr.length;j++){
//                       dbg("l_dayAttendanceArr[j]"+l_dayAuditArr[j]);
//                       
//                       if(!l_dayAuditArr[j].split(",")[0].contains(" ")){
//                       
//                           String day=l_dayAuditArr[j].split(",")[0];
//                           
//                           if(!processedDays.contains(day)){
//                           
//                               processedDays.add(day);
//
//                               AuditDetails audit= getAuditDetails(l_monthAudit,day);
//                               AttendanceResult attResult=new  AttendanceResult();
//                               attResult.setStandard(standard);
//                               attResult.setSection(section);
//                               attResult.setAuthStatus(audit.getAuthStatus());
//                               attResult.setYear(l_year);
//                               attResult.setMonth(l_month);
//                               attResult.setDay(day);
//                               attResultList.add(attResult);
//                           
//                       }
//                          
//                       }
//                       
//                   }
//               
//                 }
//               
//            }  catch(DBValidationException ex){
//            
//            if(ex.toString().contains("DB_VAL_000")||ex.toString().contains("DB_VAL_011")){
//                
//                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
//                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
//                //session.getErrorhandler().log_app_error("BS_VAL_013", null);
//              //  throw new BSValidationException("BSValidationException");
//                
//            }else{
//                throw ex;
//            }
//            
//        }   
//            }
//        }catch(DBValidationException ex){
//            
//            if(ex.toString().contains("DB_VAL_000")||ex.toString().contains("DB_VAL_011")){
//                
//                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
//                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
//                session.getErrorhandler().log_app_error("BS_VAL_013", null);
//                throw new BSValidationException("BSValidationException");
//                
//            }else{
//                throw ex;
//            }
//            
//        }
//        
//        
//           if(attResultList.isEmpty()){
//               session.getErrorhandler().log_app_error("BS_VAL_013", null);
//               throw new BSValidationException("BSValidationException");
//           }
//
//        
//                buildBOfromDB(attResultList);
//
//          dbg("end of  completed class leave--->view");   
//          }catch(BSValidationException ex){
//          throw ex;
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
        dbg("inside class leave--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassAttendanceBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ClassAttendanceBO classAttendance=reqBody.get();
        String l_standard=classAttendance.getFilter().getStandard();
        String l_section=classAttendance.getFilter().getSection();
        String fromDate=classAttendance.getFilter().getFromDate();
        String toDate=classAttendance.getFilter().getToDate();
        ArrayList<Date>dateList=bs.getLeaveDates(fromDate, toDate, session, dbSession, inject);
        String dateformat=i_db_properties.getProperty("DATE_FORMAT");
        SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
        ArrayList<AttendanceResult>attResultList=new ArrayList();
        
        try{
        
            ArrayList<String>l_fileNames=bs.getClassFileNames(l_standard,l_section,request,session,dbSession,inject);
            for(int i=0;i<l_fileNames.size();i++){
            try{
               dbg("inside file name iteration");
              ArrayList<MonthRecords> l_monthRecords=new ArrayList();
              String standard=l_fileNames.get(i).split("~")[0];
              String section=l_fileNames.get(i).split("~")[1]; 
              ArrayList<String>processedMonths=new ArrayList();
              
              for(int j=0;j<dateList.size();j++){
               
                     String l_date=formatter.format(dateList.get(j));
                  
                     ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
                     String l_year=convertedDate.getYear();
                     String l_month=convertedDate.getMonth();
                     
                     if(!processedMonths.contains(standard+section+l_year+l_month)){
                     
                         processedMonths.add(standard+section+l_year+l_month);
                         String[] l_primaryKey={standard,section,l_year,l_month};
                         DBRecord dbmonthRecord=null;
                         try{

                           dbmonthRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section,"CLASS", "CLASS_ATTENDANCE_MASTER",l_primaryKey, session, dbSession);

                         }catch(DBValidationException ex){

                            if(ex.toString().contains("DB_VAL_000")||ex.toString().contains("DB_VAL_011")){

                                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");

                            }else{
                                throw ex;
                            }
                         }

                        if(dbmonthRecord!=null){ 
                           MonthRecords monthRecord=new MonthRecords();
                           monthRecord.setYear(l_year);
                           monthRecord.setMonth(l_month);
                           monthRecord.setMonthRecord(dbmonthRecord);
                           l_monthRecords.add(monthRecord);
                        }   
                   
                     }
              }
                   
                   
                for(int k=0;k<l_monthRecords.size();k++){
                  
                   String l_monthAudit=l_monthRecords.get(k).getMonthRecord().getRecord().get(4).trim();
                   String l_year=l_monthRecords.get(k).getYear();
                   String l_month=l_monthRecords.get(k).getMonth();
                   dbg("l_monthAudit"+l_monthAudit);
                   String[] l_dayAuditArr=l_monthAudit.split("d");
                   dbg("l_dayAuditArr size"+l_dayAuditArr.length);
                   ArrayList<String>processedDays=new ArrayList();
                   
                   
                   for(int j=1;j<l_dayAuditArr.length;j++){
                       dbg("l_dayAttendanceArr[j]"+l_dayAuditArr[j]);
                       
                       if(!l_dayAuditArr[j].split(",")[0].contains(" ")){
                       
                           String day=l_dayAuditArr[j].split(",")[0];
                           
                           if(!processedDays.contains(day)){
                           
                               processedDays.add(day);

                               AuditDetails audit= getAuditDetails(l_monthAudit,day);
                               AttendanceResult attResult=new  AttendanceResult();
                               attResult.setStandard(standard);
                               attResult.setSection(section);
                               attResult.setAuthStatus(audit.getAuthStatus());
                               attResult.setYear(l_year);
                               attResult.setMonth(l_month);
                               attResult.setDay(day);
                               attResultList.add(attResult);
                           
                       }
                          
                       }
                       
                   }
               
                 }
               
            }  catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_000")||ex.toString().contains("DB_VAL_011")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                //session.getErrorhandler().log_app_error("BS_VAL_013", null);
              //  throw new BSValidationException("BSValidationException");
                
            }else{
                throw ex;
            }
            
        }   
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
        
        
           if(attResultList.isEmpty()){
               session.getErrorhandler().log_app_error("BS_VAL_013", null);
               throw new BSValidationException("BSValidationException");
           }

        
                buildBOfromDB(attResultList);

          dbg("end of  completed class leave--->view");   
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

    
    private void buildBOfromDB(ArrayList<AttendanceResult>attendanceList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<ClassAttendanceBO> reqBody = request.getReqBody();
           ClassAttendanceBO classAttendance =reqBody.get();
           BusinessService bs=inject.getBusinessService(session);
           String l_authStatusFilter=classAttendance.getFilter().getAuthStatus();
           String l_standard=classAttendance.getFilter().getStandard();
           String l_section=classAttendance.getFilter().getSection();
           String l_fromDate=classAttendance.getFilter().getFromDate();
           String l_toDate=classAttendance.getFilter().getToDate();
           String dateformat=session.getCohesiveproperties().getProperty("DATE_FORMAT");
           SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
           ArrayList<AttendanceResult>filteredList=new ArrayList();
           
           dbg("l_authStatusFilter"+l_authStatusFilter);
           dbg("l_standard"+l_standard);
           dbg("l_section"+l_section);
           
           
           for(int i=0;i<attendanceList.size();i++){
               
               
               AttendanceResult attResult=attendanceList.get(i);
               String year=attResult.getYear();
               String month=attResult.getMonth();
               String day=attResult.getDay();
               String authStatus=attResult.getAuthStatus();
               String dateString=bs.getDate(year, month, day);
               Date attendanceDate=formatter.parse(dateString);
               Date fromDate=formatter.parse(l_fromDate);
               Date toDate=formatter.parse(l_toDate);
               dbg("attendanceDate"+attendanceDate);
               dbg("fromDate"+fromDate);
               dbg("toDate"+toDate);
               dbg("authStatus"+authStatus);
               
               
               if(attendanceDate.compareTo(fromDate)>=0){
                    
                    dbg("attendanceDate.compareTo(fromDate)>=0");
                    
                    if(attendanceDate.compareTo(toDate)<=0){
               
                         if(!l_authStatusFilter.isEmpty()){
                             
                             
                             if(l_authStatusFilter.equals(authStatus)){
                             
                                filteredList.add(attResult);
                             
                             }
                         }else{
                             
                             filteredList.add(attResult);
                         }
               
               
                    }
                    
               }
           }
           
 
           
         classAttendance.result=new ClassAttendanceResult[filteredList.size()];

          for (int i=0;i<filteredList.size();i++){
              
              AttendanceResult result=filteredList.get(i);
              classAttendance.result[i]=new ClassAttendanceResult();
              classAttendance.result[i].setStandard(result.getStandard());
              classAttendance.result[i].setSection(result.getSection());
              
              String year=result.getYear();
              String month=result.getMonth();
              String day=result.getDay();
              String date=bs.getDate(year, month, day);
              classAttendance.result[i].setDate(date);
              
          }           
           
           
           
           if(classAttendance.result==null||classAttendance.result.length==0){
               session.getErrorhandler().log_app_error("BS_VAL_013", null);
               throw new BSValidationException("BSValidationException");
           }
           
           
           
          dbg("end of  buildBOfromDB"); 
           

        }catch(BSValidationException ex){
          throw ex;

        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
 }
    
    
    private ArrayList<AttendanceResult> dayFilter(ArrayList<AttendanceResult>attendanceResult,String p_day)throws BSProcessingException{
        
        try{
            dbg("inside getMaxVersionAttendanceOftheDay");
            dbg("p_day"+p_day);
            ArrayList<AttendanceResult>recordsFor_a_day=new ArrayList();
            for(int i=0;i<attendanceResult.size();i++){
                
                AttendanceResult attResult=attendanceResult.get(i);
                
                if(attResult.getDay().equals(p_day)){
                    
                    recordsFor_a_day.add(attResult);
                }
            }
            
//            filterlist_dummy=new ArrayList();
//            if(recordsFor_a_day.size()>1){
//                filterkey_dummy=p_day;
//
//                int max_vesion=recordsFor_a_day.stream().mapToInt(rec->Integer.parseInt(rec.split(",")[7])).max().getAsInt();
//                recordsFor_a_day.stream().filter(rec->Integer.parseInt(rec.split(",")[7])==max_vesion).forEach(rec->filterlist_dummy.add(rec));           
//                dbg("max_vesion"+max_vesion);
//            }else{
//                
//                filterlist_dummy.add(recordsFor_a_day.get(0).trim());
//            }
            
            
            
            
            dbg("recordsFor_a_day size"+recordsFor_a_day.size());
           
            return recordsFor_a_day;
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
    }  
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        JsonObject filter;
        try{
        dbg("inside class leave buildJsonResFromBO");    
        RequestBody<ClassAttendanceBO> reqBody = request.getReqBody();
        ClassAttendanceBO classAttendance =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<classAttendance.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("class", classAttendance.result[i].getStandard()+"/"+classAttendance.result[i].getSection())
                                                      .add("date", classAttendance.result[i].getDate()));
        }

           filter=Json.createObjectBuilder()   .add("class", classAttendance.filter.getStandard()+"/"+ classAttendance.filter.getSection())
//                                              .add("recordStatus", classAttendance.filter.getRecordStatus())
                                              .add("authStatus", classAttendance.filter.getAuthStatus())
                                              .add("fromDate", classAttendance.filter.getFromDate())
                                              .add("toDate", classAttendance.filter.getToDate())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of class leave buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside class leave--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of class leave--->businessValidation"); 
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
        dbg("inside class leave master mandatory validation");
        RequestBody<ClassAttendanceBO> reqBody = request.getReqBody();
        ClassAttendanceBO classAttendance =reqBody.get();
        boolean fromDateEmpty=false;
        boolean toDateEmpty=false;
        
           if(classAttendance.getFilter().getFromDate()==null||classAttendance.getFilter().getFromDate().isEmpty()){
              fromDateEmpty=true;
         }
          
          if(classAttendance.getFilter().getToDate()==null||classAttendance.getFilter().getToDate().isEmpty()){
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
          
        dbg("end of class leave master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside class leave detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<ClassAttendanceBO> reqBody = request.getReqBody();
             ClassAttendanceBO classAttendance =reqBody.get();
             BusinessService bs=inject.getBusinessService(session);
             String l_standard=classAttendance.getFilter().getStandard();
             String l_section=classAttendance.getFilter().getSection();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_authStatus=classAttendance.getFilter().getAuthStatus();
             String l_fromDate=classAttendance.getFilter().getFromDate();
             String l_toDate=classAttendance.getFilter().getToDate();
             
             if(l_standard!=null&&!l_standard.isEmpty()&&l_section!=null&&!l_section.isEmpty()){
                 
                if(!bsv.standardSectionValidation(l_standard,l_section, l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","standard or section");
                }
                 
             }
             
//             if(l_recordStatus!=null&&!l_recordStatus.isEmpty()){
//                 
//                if(!bsv.recordStatusValidation(l_recordStatus, session, dbSession, inject)){
//                    status=false;
//                    errhandler.log_app_error("BS_VAL_003","recordStatus");
//                }
//                 
//             }
             
             if(l_authStatus!=null&&!l_authStatus.isEmpty()){
                 
                if(!bsv.authStatusValidation(l_authStatus, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","authStatus");
                }
                 
             }
             
             
             
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
             
            dbg("end of class leave detailDataValidation");
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
 
 public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }  
}
