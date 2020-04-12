/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.summary.teacherattendance;

import com.ibd.businessViews.ITeacherAttendanceSummary;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.student.studentattendanceservice.AuditDetails;
import com.ibd.cohesive.app.business.student.studentattendanceservice.PeriodAttendance;
import com.ibd.cohesive.app.business.student.summary.studentattendancesummary.DateAttendance;
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
 * @author DELL
 */
//@Local(ITeacherAttendanceSummary.class)
@Remote(ITeacherAttendanceSummary.class)
@Stateless
public class TeacherAttendanceSummary implements ITeacherAttendanceSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    String filterkey_dummy;
        ArrayList<String>filterlist_dummy;
    
    public TeacherAttendanceSummary(){
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
       dbg("inside TeacherAttendanceSummary--->processing");
       dbg("TeacherAttendanceSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 

       BusinessEJB<ITeacherAttendanceSummary>teacherAttendanceEJB=new BusinessEJB();
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
      TeacherAttendanceBO teacherAttendance=new TeacherAttendanceBO();
      RequestBody<TeacherAttendanceBO> reqBody = new RequestBody<TeacherAttendanceBO>(); 
           
      try{
      dbg("inside teacher attendance buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      teacherAttendance.filter=new TeacherAttendanceFilter();
      teacherAttendance.filter.setTeacherID(l_filterObject.getString("teacherID"));
      teacherAttendance.filter.setTeacherName(l_filterObject.getString("teacherName"));
//      teacherAttendance.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      teacherAttendance.filter.setAuthStatus(l_filterObject.getString("authStat"));
      teacherAttendance.filter.setDate(l_filterObject.getString("date"));
      
      if(l_filterObject.getString("authStat").equals("Select option")){
          
          teacherAttendance.filter.setAuthStatus("");
      }else{
      
          teacherAttendance.filter.setAuthStatus(l_filterObject.getString("authStat"));
      }
      
        reqBody.set(teacherAttendance);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside teacher attendance--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<TeacherAttendanceBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        TeacherAttendanceBO teacherAttendance=reqBody.get();
        String l_teacherID=teacherAttendance.getFilter().getTeacherID();
        String l_teacherNameFilter=teacherAttendance.getFilter().getTeacherName();
        
        try{
        
        
        ArrayList<String>l_fileNames=bs.getTeacherFileNames(l_teacherID,request,session,dbSession,inject);
        dbg("l_fileNames size "+l_fileNames.size());
        teacherAttendance.result=new TeacherAttendanceResult[l_fileNames.size()];
        for(int i=0;i<l_fileNames.size();i++){
            dbg("inside file name iteration");
            String teacherID=l_fileNames.get(i);
            String teacherName=bs.getTeacherName(teacherID, l_instituteID, session, dbSession, inject);
            
            if(teacherName.equals(l_teacherNameFilter)){
            
            dbg("teacherID"+teacherID);
            String l_date=teacherAttendance.getFilter().getDate();
            dbg("l_date"+l_date);
//            MonthRecords[] monthRecords;
            ArrayList<MonthRecords> l_monthRecords=new ArrayList();
            
               if(l_date!=null&&!l_date.isEmpty()){
                   
                     ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
                     String l_year=convertedDate.getYear();
                     String l_month=convertedDate.getMonth();
                     String[] l_primaryKey={teacherID,l_year,l_month};
                     DBRecord dbmonthRecord=null;
                     try{

                       dbmonthRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID,"TEACHER", "TVW_TEACHER_ATTENDANCE",l_primaryKey, session, dbSession);

                     }catch(DBValidationException ex){

                         if(!(ex.toString().contains("DB_VAL_011"))){

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
                   
               }else{
                   
                   String currentYearMonth=bs.getCurrentYearMonth();
                   String year=currentYearMonth.split("~")[0];
                   String month=currentYearMonth.split("~")[1];
                   String currentDay=currentYearMonth.split("~")[2];
                   
                   String[] l_primaryKey={teacherID,year,month};
                   DBRecord dbMonthRecord=null;
                   
                   try{
                   
                      dbMonthRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID,"TEACHER", "TVW_TEACHER_ATTENDANCE",l_primaryKey, session, dbSession);
                  
                   }catch(DBValidationException ex){
                     
                     if(!(ex.toString().contains("DB_VAL_011"))){
                         
                        throw ex; 
                     }
                 }
                   
                  if(dbMonthRecord!=null){   
                       MonthRecords monthRecord=new MonthRecords();
                       monthRecord.setYear(year);
                       monthRecord.setMonth(month);
                       monthRecord.setMonthRecord(dbMonthRecord);
                       l_monthRecords.add(monthRecord);
                  }
                   
                   
                   if(Integer.parseInt(currentDay)<6){
                   
                       String previousYearMonth=bs.getPreviousYearMonth(year, month);
                       String previousYear=previousYearMonth.split("~")[0];
                       String previousMonth=previousYearMonth.split("~")[1];

                       String[] l_pkey={teacherID,previousYear,previousMonth};
                       DBRecord previousMonthRecord=null;
                       
                       try{
                        
                            previousMonthRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID,"TEACHER", "TVW_TEACHER_ATTENDANCE",l_pkey, session, dbSession);

                       }catch(DBValidationException ex){

                             if(!(ex.toString().contains("DB_VAL_011"))){

                                throw ex; 
                             }
                       }
                      if(previousMonthRecord!=null){   
                           MonthRecords monthRecord=new MonthRecords();
                           monthRecord.setYear(previousYear);
                           monthRecord.setMonth(previousYear);
                           monthRecord.setMonthRecord(previousMonthRecord);
                           l_monthRecords.add(monthRecord);
                      }
               
                   }
               }
               
               dbg("l_monthRecords size"+l_monthRecords.size());
               for(int k=0;k<l_monthRecords.size();k++){
            
                   String l_monthAttendance=l_monthRecords.get(k).getMonthRecord().getRecord().get(3).trim();
                   dbg("l_monthAttendance"+l_monthAttendance);
                   String[] l_dayAttendanceArr=l_monthAttendance.split("d");
                   dbg("l_dayAttendanceArr size"+l_dayAttendanceArr.length);
                   ArrayList<String>l_dayAttendanceList=new ArrayList();
                   for(int j=1;j<l_dayAttendanceArr.length;j++){
                       dbg("l_dayAttendanceArr[j]"+l_dayAttendanceArr[j]);
                       
                       if(!l_dayAttendanceArr[j].split(",")[0].contains(" ")){
                       
                          l_dayAttendanceList.add(l_dayAttendanceArr[j]);
                       }
                       
                   }
               
               buildBOfromDB(l_dayAttendanceList,teacherID,i,k,l_monthRecords.get(k).getYear(),l_monthRecords.get(k).getMonth());
               
               
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
          if(teacherAttendance.result==null||teacherAttendance.result.length==0){
                session.getErrorhandler().log_app_error("BS_VAL_013", null);
               throw new BSValidationException("BSValidationException");
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
    

    
    private void buildBOfromDB(ArrayList<String>l_attendanceList,String teacherID,int index, int monthCount,String year,String month)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           BusinessService bs=inject.getBusinessService(session);
           RequestBody<TeacherAttendanceBO> reqBody = request.getReqBody();
           TeacherAttendanceBO teacherAttendance =reqBody.get();
           String l_authStatus=teacherAttendance.getFilter().getAuthStatus();
           String l_date=teacherAttendance.getFilter().getDate();
           
           dbg("l_authStatus"+l_authStatus);
           dbg("l_date"+l_date);
           dbg("l_attendanceList size"+l_attendanceList.size());
           dbg("teacherID"+teacherID);
           dbg("index"+index);
           dbg("monthCount"+monthCount);
           dbg("year"+year);
           dbg("month"+month);
           
           if(l_date!=null&&!l_date.isEmpty()){
             ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
             String l_day=convertedDate.getDay();
//             List<String>  l_teacherList=  l_attendanceList.stream().filter(rec->rec.split("p")[0].equals(l_day)).collect(Collectors.toList());
//             l_attendanceList = new ArrayList(l_teacherList);
               l_attendanceList = dayFilter(l_attendanceList,l_day);
             dbg("l_date filter l_attendanceList size"+l_attendanceList.size());
           }
           
           if(l_authStatus!=null&&!l_authStatus.isEmpty()){
               List<String>  l_teacherList=  l_attendanceList.stream().filter(rec->rec.split(",")[6].equals(l_authStatus)).collect(Collectors.toList());
               l_attendanceList = new ArrayList(l_teacherList);
               dbg("l_authStatus filter l_attendanceList size"+l_attendanceList.size());
           }
           
           dbg("filter completed;");
           l_attendanceList= getMaxVersionRecord(l_attendanceList);
           
           if(monthCount!=1){
               teacherAttendance.result[index]=new TeacherAttendanceResult();
               teacherAttendance.result[index].setTeacherID(teacherID);
           
           }
           
           dbg("bo building started");
           DateAttendance[] dateAttendance=new DateAttendance[l_attendanceList.size()];
           for(int i=0;i<l_attendanceList.size();i++){
              
              String dayAttendance= l_attendanceList.get(i);
              dbg("dayAttendance");
              String[] dayArray=dayAttendance.split(",");
              String attendanceRecord= dayArray[0];
              dbg("attendanceRecord"+attendanceRecord);
              String[] periodArray=  attendanceRecord.split("p");
              
              String day=periodArray[0];
              String date=bs.getDate(year,month,day);
              dateAttendance[i]=new DateAttendance();
              dateAttendance[i].setDate(date);
              dateAttendance[i].period=new PeriodAttendance[periodArray.length-1];
              
              dbg("period iteration starts");
              dbg("periodArray length"+periodArray.length);
              for(int j=1;j<periodArray.length;j++){
                  String periodNumber=periodArray[j].substring(0, 1);
                  String attendance=periodArray[j].substring(1);
                  dbg("periodNumber"+periodNumber);
                  dbg("attendance"+attendance);
                  dateAttendance[i].period[j-1]=new PeriodAttendance();
                  dateAttendance[i].period[j-1].setPeriodNumber(periodNumber);
                  dateAttendance[i].period[j-1].setAttendance(attendance);
                
              }
              dbg("period iteration completed");
              
              dbg("audit log building starts");
              AuditDetails audit= getAuditDetailsFromDayAttendance (dayAttendance)  ;
              dateAttendance[i].setMakerID(audit.getMakerID());
              dateAttendance[i].setCheckerID(audit.getCheckerID());
              dateAttendance[i].setMakerDateStamp(audit.getMakerDateStamp());
              dateAttendance[i].setCheckerDateStamp(audit.getCheckerDateStamp());
              dateAttendance[i].setRecordStatus(audit.getRecordStatus());
              dateAttendance[i].setAuthStatus(audit.getAuthStatus());
              dateAttendance[i].setVersionNumber(audit.getVersionNo());
              dateAttendance[i].setMakerRemarks(audit.getMakerRemarks());
              dateAttendance[i].setCheckerRemarks(audit.getCheckerRemarks());
              
              dbg("audit log building endss");
           }
           
           if(monthCount==0){
               
               teacherAttendance.result[index].dateAttendance=dateAttendance;
               dbg("inside monthCount==0");
           }else{
               dbg("inside monthCount==1");
               DateAttendance[] previousMonthAttendance=teacherAttendance.result[index].getDateAttendance();
               dbg("previousMonthAttendance size"+previousMonthAttendance.length);
               dbg("dateAttendance size"+dateAttendance.length);
               
               DateAttendance[] mergedAttendance=new DateAttendance[previousMonthAttendance.length+dateAttendance.length];
               
               dbg("previous iteration starts");
               for(int i=0;i<previousMonthAttendance.length;i++){
                   
                   mergedAttendance[i]=previousMonthAttendance[i];
               }  
               dbg("previous iteration ends");
               
               dbg("current iteration starts");
               for(int i=0;i<dateAttendance.length;i++){
                   
                   mergedAttendance[previousMonthAttendance.length+i]=dateAttendance[i];
               } 
               dbg("current iteration ends");
               dbg("mergedAttendance size"+mergedAttendance.length );
               
               teacherAttendance.result[index].dateAttendance=mergedAttendance;
               
           }
            ErrorHandler errHandler=session.getErrorhandler();
           if(teacherAttendance.result==null||teacherAttendance.result.length==0){
               errHandler.log_app_error("BS_VAL_013", null);
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
    private ArrayList<String> getMaxVersionRecord(ArrayList<String>monthAttendance)throws BSProcessingException{
      
      try{
          dbg("get max version record");
          Map<String,ArrayList<String>>l_attenceForEachDay=new HashMap();
          for(int i=0;i<monthAttendance.size();i++){
              
              String dayRecord=monthAttendance.get(i);
                dbg("dayRecord"+dayRecord);
                String l_day=null;
                
                String dayAttendance=dayRecord.split(",")[0];
         
                if(dayAttendance.contains(" ")){
                    
                    l_day=dayAttendance.split(" ")[0];
                }else if(dayAttendance.contains("p")){
                    
                    l_day=dayAttendance.split("p")[0];
                }
                dbg("l_day");
                if(l_attenceForEachDay.containsKey(l_day)){
                 
                   dbg("l_attenceForEachDay contains l_day");
                   l_attenceForEachDay.get(l_day).add(dayRecord);
              
                }else{
                    dbg("l_attenceForEachDay not contains l_day");
                    l_attenceForEachDay.put(l_day, new ArrayList());
                    l_attenceForEachDay.get(l_day).add(dayRecord);
                }
              
          }
          dbg("l_attenceForEachDay size"+l_attenceForEachDay.size());
          Iterator<String>dayIterator=l_attenceForEachDay.keySet().iterator();
          
          while(dayIterator.hasNext()){
             
            String day= dayIterator.next();
            dbg("day"+day);
            ArrayList<String>dayAttendanceList=l_attenceForEachDay.get(day);
            dbg(" dayAttendanceList size"+dayAttendanceList.size());
            
            filterlist_dummy=new ArrayList();
            if(dayAttendanceList.size()>1){
                filterkey_dummy=day;
                
                int max_vesion=dayAttendanceList.stream().mapToInt(rec->Integer.parseInt(rec.split(",")[7])).max().getAsInt();
                dayAttendanceList.stream().filter(rec->Integer.parseInt(rec.split(",")[7])==max_vesion).forEach(rec->filterlist_dummy.add(rec));           
                dbg("max_vesion"+max_vesion);
            }else{
                
                filterlist_dummy.add(dayAttendanceList.get(0).trim());
            }
              
          }
          
          dbg("filterlist_dummy size"+filterlist_dummy.size());
          
          return filterlist_dummy;
      }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

      }
      
      
      
  }
  private ArrayList<String> dayFilter(ArrayList<String>monthAttendance,String p_day)throws BSProcessingException{
        
        try{
            dbg("inside getMaxVersionAttendanceOftheDay");
            dbg("p_day"+p_day);
            ArrayList<String>recordsFor_a_day=new ArrayList();
            for(int i=0;i<monthAttendance.size();i++){
                String dayRecord=monthAttendance.get(i);
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
           
            return recordsFor_a_day;
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

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
    

    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        JsonObject filter;
        try{
        dbg("inside teacher attendance buildJsonResFromBO");    
        RequestBody<TeacherAttendanceBO> reqBody = request.getReqBody();
        TeacherAttendanceBO teacherAttendance =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
        JsonArrayBuilder dateAttendanceArr=Json.createArrayBuilder();
        JsonArrayBuilder periodAttendanceArr=Json.createArrayBuilder();
        
             
        for(int i=0;i<teacherAttendance.result.length;i++){
            
            if(teacherAttendance.result[i]!=null){
                   if(teacherAttendance.result[i].dateAttendance!=null){
            
            for(int j=0;j<teacherAttendance.result[i].dateAttendance.length;j++){
                
                if(teacherAttendance.result[i].dateAttendance[j].period!=null){
                
                    for(int k=0;k<teacherAttendance.result[i].dateAttendance[j].period.length;k++){

                        periodAttendanceArr.add(Json.createObjectBuilder().add("periodNo",teacherAttendance.result[i].dateAttendance[j].period[k].getPeriodNumber())
                                                                    .add("attendance", teacherAttendance.result[i].dateAttendance[j].period[k].getAttendance()));

                    }
                }
                
                dateAttendanceArr.add(Json.createObjectBuilder().add("date",teacherAttendance.result[i].dateAttendance[j].getDate())
                                                                .add("periodAttendance", periodAttendanceArr));
                
              }
            } 
            
            resultArray.add(Json.createObjectBuilder().add("teacherID", teacherAttendance.result[i].getTeacherID())
                                                      .add("attendance", dateAttendanceArr));
            }                                            
        }

           filter=Json.createObjectBuilder()  .add("teacherID",teacherAttendance.filter.getTeacherID())
                                              .add("teacherName",teacherAttendance.filter.getTeacherName())
                                              .add("authStatus", teacherAttendance.filter.getAuthStatus())
                                              .add("date", teacherAttendance.filter.getDate())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
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
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of teacher attendance--->businessValidation"); 
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
        dbg("inside teacher attendance master mandatory validation");
        RequestBody<TeacherAttendanceBO> reqBody = request.getReqBody();
        TeacherAttendanceBO teacherAttendance =reqBody.get();
        int nullCount=0;
        
         if(teacherAttendance.getFilter().getTeacherName()==null||teacherAttendance.getFilter().getTeacherName().isEmpty()){
             nullCount++;
         }

         if(teacherAttendance.getFilter().getAuthStatus()==null||teacherAttendance.getFilter().getAuthStatus().isEmpty()){
             nullCount++;
         }
         if(teacherAttendance.getFilter().getDate()==null||teacherAttendance.getFilter().getDate().isEmpty()){
             nullCount++;
         }
          
          if(nullCount==3){
              status=false;
              errhandler.log_app_error("BS_VAL_002","One Filter value");
          }
          
        dbg("end of teacher attendance master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside teacher attendance detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<TeacherAttendanceBO> reqBody = request.getReqBody();
             TeacherAttendanceBO teacherAttendance =reqBody.get();
             String l_teacherID=teacherAttendance.getFilter().getTeacherID();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_authStatus=teacherAttendance.getFilter().getAuthStatus();
             String l_date=teacherAttendance.getFilter().getDate();
             
             if(l_teacherID!=null&&!l_teacherID.isEmpty()){
                 
                if(!bsv.teacherIDValidation(l_teacherID, l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","teacherID");
                }
                 
             }

             
             if(l_authStatus!=null&&!l_authStatus.isEmpty()){
                 
                if(!bsv.authStatusValidation(l_authStatus, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","authStatus");
                }
                 
             }
             
             
             
             if(l_date!=null&&!l_date.isEmpty()){
             
                 if(!bsv.dateFormatValidation(l_date, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","Due Date");
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
