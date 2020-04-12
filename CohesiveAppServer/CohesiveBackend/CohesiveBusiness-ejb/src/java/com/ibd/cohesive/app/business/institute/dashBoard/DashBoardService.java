/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.dashBoard;

import com.ibd.businessViews.IDashBoardService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.EducationPeriod;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.exception.ExceptionHandler;
import com.ibd.cohesive.app.business.util.message.request.Header;
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
import javax.json.JsonObjectBuilder;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Remote(IDashBoardService.class)
@Stateless
public class DashBoardService implements IDashBoardService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public DashBoardService(){
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
       dbg("inside DashBoardService--->processing");
       dbg("DashBoardService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IDashBoardService>selectBoxMasterEJB=new BusinessEJB();
       selectBoxMasterEJB.set(this);
      
       exAudit=bs.getExistingAudit(selectBoxMasterEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,selectBoxMasterEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,selectBoxMasterEJB);
              tc.commit(session,dbSession);
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
                clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);
                           
//if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                  // BSProcessingException ex=new BSProcessingException("response contains special characters");
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

    private  void buildBOfromReq(JsonObject p_request)throws BSProcessingException,DBProcessingException{
      DashBoard selectBoxMaster=new DashBoard();
      RequestBody<DashBoard> reqBody = new RequestBody<DashBoard>(); 
           
      try{
      dbg("inside institute selectBoxMaster buildBOfromReq");    
      

      
        reqBody.set(selectBoxMaster);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside institute selectBoxMaster--->view");
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<DashBoard> reqBody = request.getReqBody();
        BusinessService bs=inject.getBusinessService(session);
        DashBoard dashBoard =reqBody.get();
        String userID=request.getReqHeader().getUserID();
        String instituteID=request.getReqHeader().getInstituteID();
        String userType=bs.getUserType(userID, session, dbSession, inject);
        String currentDateTime=bs.getCurrentDateTime();
        String dateformat=i_db_properties.getProperty("DATE_TIME_FORMAT");
        SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatter.parse(currentDateTime));
        
        if(userType.equals("A")){
           
            setTeacherLeaveDetails();
           
            setStudentLeaveDetails();
           
            setUnAuthPending();
            
            setFeeDetails();
           
            setSMSDetails();  
            
            
            
        }if(userType.equals("T")){
            
            float totalInsWorkDays=bs.getNoOfWorkingDaysTillNow(instituteID, session, dbSession, inject);
            float leaveTakenThisYear=this.getTeacherLeaveCount();
            dashBoard.setTeacherWorkingDays(Float.toString(totalInsWorkDays));
            dashBoard.setTeacherLeaveDays(Float.toString(leaveTakenThisYear));
            
            setUnAuthPending();
            setClassLeaveDetails();
//            setClassFeeDetails();
        }
        
        
  
            
        
          dbg("end of  completed institute selectBoxMaster--->view");                
        }catch(DBValidationException ex){
            throw ex;
        }catch(BSValidationException ex){
            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
    
      private void setSMSDetails()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
        
        try{
            
            RequestBody<DashBoard> reqBody = request.getReqBody();
            BusinessService bs=inject.getBusinessService(session);
            DashBoard dashBoard =reqBody.get();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            String instituteID=request.getReqHeader().getInstituteID();
            IPDataService pds=inject.getPdataservice();
            
            String[] pkey={instituteID};
            ArrayList<String>contractList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",pkey);
            int smsLimit=Integer.parseInt(contractList.get(2).trim());
            int smsUsed=Integer.parseInt(contractList.get(4).trim());
            dashBoard.setSmsLimit(Integer.toString(smsLimit));
            dashBoard.setCurrentSMSBalance(Integer.toString(smsUsed));     
            
            
            
        }catch(DBValidationException ex){
            throw ex;
//        }catch(BSValidationException ex){
//            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
    
    private void setTeacherLeaveDetails()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
        
      try{ 
        dbg("inside setTeacherLeaveDetails");
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        RequestBody<DashBoard> reqBody = request.getReqBody();
        BusinessService bs=inject.getBusinessService(session);
        DashBoard dashBoard =reqBody.get();
        String instituteID=request.getReqHeader().getInstituteID();
        String currentDate=bs.getCurrentDate();
        String currentDateTime=bs.getCurrentDateTime();
        Map<String,DBRecord>teacherLeaveMap=null;
        String dateformat=i_db_properties.getProperty("DATE_TIME_FORMAT");
        SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatter.parse(currentDateTime));
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
             
             
             try{  
            
            
              teacherLeaveMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"INSTITUTE", "IVW_TEACHER_LEAVE_DETAILS", session, dbSession);
            
            
          }catch(DBValidationException ex){
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                    }else{
                        
                        throw ex;
                    }
           }
            
             int totalTeachersSize=bs.getNoOfTeachersOfTheInstitute(instituteID, session, dbSession, inject);
             
              dashBoard.setTotalNoOfTeachers(Integer.toString(totalTeachersSize));
              int teacherLeaveSize=0;
              int teachersPresent;
              
              
           if(teacherLeaveMap!=null&&!teacherLeaveMap.isEmpty()){
              
              
               if(hours<12){
                   
                  teacherLeaveSize=  teacherLeaveMap.values().stream().filter(rec->rec.getRecord().get(1).trim().equals("Y")||rec.getRecord().get(2).trim().equals("F")).collect(Collectors.toList()).size();
                   
                 
               }else{
                   
                   teacherLeaveSize=  teacherLeaveMap.values().stream().filter(rec->rec.getRecord().get(1).trim().equals("Y")||rec.getRecord().get(2).trim().equals("A")).collect(Collectors.toList()).size(); 
               }
               

           }
                            
//               teachersPresent=totalTeachersSize-teacherLeaveSize;
               dbg("teacherLeaveSize"+teacherLeaveSize);
               dashBoard.setTeacherAttendance(Integer.toString(teacherLeaveSize));
             dbg("end of setTeacherLeaveDetails");
        }catch(DBValidationException ex){
            throw ex;
//        }catch(BSValidationException ex){
//            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }     
    }
    
    
   
    
    
    
     private void setStudentLeaveDetails()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
        
      try{ 
        
        dbg("inside  setStudentLeaveDetails");  
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        RequestBody<DashBoard> reqBody = request.getReqBody();
        BusinessService bs=inject.getBusinessService(session);
        DashBoard dashBoard =reqBody.get();
        String instituteID=request.getReqHeader().getInstituteID();
        String currentDate=bs.getCurrentDate();
        String currentDateTime=bs.getCurrentDateTime();
        String dateformat=i_db_properties.getProperty("DATE_TIME_FORMAT");
        SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatter.parse(currentDateTime));
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
             
             
             Map<String,DBRecord>  studentLeaveMap=null;
           
          try{  
            
            
              studentLeaveMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"INSTITUTE", "IVW_STUDENT_LEAVE_DETAILS", session, dbSession);
            
            
          }catch(DBValidationException ex){
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                    }else{
                        
                        throw ex;
                    }
           }
            
          int totalstudentsSize=bs.getNoOfStudentsOfTheInstitute(instituteID, session, dbSession, inject);
          
           dashBoard.setTotalNoOfStudents(Integer.toString(totalstudentsSize));

           int studentLeaveSize=0;
//           int studentsPresent;
           
           if(studentLeaveMap!=null&&!studentLeaveMap.isEmpty()){
              

               if(hours<12){
                   
                  studentLeaveSize=  studentLeaveMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("Y")||rec.getRecord().get(4).trim().equals("F")).collect(Collectors.toList()).size();
                   
                 
               }else{
                   
                   studentLeaveSize=  studentLeaveMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("Y")||rec.getRecord().get(4).trim().equals("A")).collect(Collectors.toList()).size(); 
               }
               

           } 
             
//        studentsPresent=totalstudentsSize-studentLeaveSize;
        dashBoard.setStudentAttendance(Integer.toString(studentLeaveSize));
             dbg("end of  setStudentLeaveDetails"); 
        }catch(DBValidationException ex){
            throw ex;
//        }catch(BSValidationException ex){
//            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }     
    }
     
     
    
      
      private void setClassLeaveDetails()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
        
      try{ 
        
        dbg("inside  setStudentLeaveDetails");  
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IPDataService pds=inject.getPdataservice();
        RequestBody<DashBoard> reqBody = request.getReqBody();
        BusinessService bs=inject.getBusinessService(session);
        DashBoard dashBoard =reqBody.get();
        String instituteID=request.getReqHeader().getInstituteID();
        String l_userID=request.getReqHeader().getUserID();
        String l_teacherID=bs.getTeacherIDForTheUser(l_userID, session, dbSession, inject);
        String currentDate=bs.getCurrentDate();
        String currentDateTime=bs.getCurrentDateTime();
        String dateformat=i_db_properties.getProperty("DATE_TIME_FORMAT");
        SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatter.parse(currentDateTime));
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int totalStudentsInTheClass=0;
        String classs="";
        int totalStudentsLeaveTaken=0;
             
             Map<String,DBRecord>  studentLeaveMap=null;
           
          try{  
            
            
              studentLeaveMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate,"INSTITUTE", "IVW_STUDENT_LEAVE_DETAILS", session, dbSession);
            
            
          }catch(DBValidationException ex){
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                    }else{
                        
                        throw ex;
                    }
           }
            
          
            Map<String,ArrayList<String>>  classMap= pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE","IVW_STANDARD_MASTER", session, dbSession);
            
            dbg("classMap size"+classMap.size());
            
            List<ArrayList<String>>teacherFilteredList=classMap.values().stream().filter(rec->rec.get(3).trim().equals(l_teacherID)&&rec.get(9).trim().equals("O")&&rec.get(10).trim().equals("A")).collect(Collectors.toList());
            
            
            if(teacherFilteredList!=null&&!teacherFilteredList.isEmpty()){
            
                    dbg("teacherFilteredList size"+teacherFilteredList.size());

                    String standard=teacherFilteredList.get(0).get(1).trim();
                    String section=teacherFilteredList.get(0).get(2).trim();
                    classs=standard+section;
                    dbg("standard"+standard);
                    dbg("section"+section);
                    Map<String,ArrayList<String>>studentMasterMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", session, dbSession);
                    dbg("studentMasterMap size"+studentMasterMap.size());
                    Map<String,List<ArrayList<String>>>l_studentGroup=studentMasterMap.values().stream().filter(rec->rec.get(2).trim().equals(standard)&&rec.get(3).trim().equals(section)&&rec.get(8).trim().equals("O")).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
                    dbg("l_studentGroup size"+l_studentGroup.size());
                    totalStudentsInTheClass=l_studentGroup.size();
                    
                    if(studentLeaveMap!=null&&!studentLeaveMap.isEmpty()){
                        
                        
                        if(hours<12){
                   
                          totalStudentsLeaveTaken=  studentLeaveMap.values().stream().filter(rec->(rec.getRecord().get(1).trim().equals(standard)&&rec.getRecord().get(2).trim().equals(section))&&(rec.getRecord().get(3).trim().equals("Y")||rec.getRecord().get(4).trim().equals("F"))).collect(Collectors.toList()).size();


                       }else{

                           totalStudentsLeaveTaken=  studentLeaveMap.values().stream().filter(rec->(rec.getRecord().get(1).trim().equals(standard)&&rec.getRecord().get(2).trim().equals(section))&&(rec.getRecord().get(3).trim().equals("Y")||rec.getRecord().get(4).trim().equals("A"))).collect(Collectors.toList()).size(); 
                       }
                        
                    }
                    
          
            }
             
//        studentsPresent=totalstudentsSize-studentLeaveSize;
        dashBoard.setClassOfTheTeacher(classs);
        dashBoard.setTotalNoOfStudents(Integer.toString(totalStudentsInTheClass));
        dashBoard.setStudentAttendance(Integer.toString(totalStudentsLeaveTaken));
             dbg("end of  setStudentLeaveDetails"); 
        }catch(DBValidationException ex){
            throw ex;
//        }catch(BSValidationException ex){
//            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }     
    }
     
     
    
    private float getTeacherLeaveCount()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
    
    float leaveCount=0.0f;
        try{ 
            dbg("inside getTeacherLeaveCount");
            RequestBody<DashBoard> reqBody = request.getReqBody();
            BusinessService bs=inject.getBusinessService(session);
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            String instituteID=request.getReqHeader().getInstituteID();
            Map<String,DBRecord>teacherLeaveMap=null;
            
            String l_userID=request.getReqHeader().getUserID();
            String l_teacherID=bs.getTeacherIDForTheUser(l_userID, session, dbSession, inject);
            EducationPeriod eduPeriod=bs.getEducationPeriod(instituteID, session, dbSession, inject);
            String dateformat=i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
            Date fromDate=formatter.parse(eduPeriod.getFromDate());
            Date toDate=formatter.parse(bs.getCurrentDate());
            
            try{
            
            
            teacherLeaveMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE", "TVW_TEACHER_LEAVE_MANAGEMENT", session, dbSession);
            
            
            }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                    }else{
                        
                        throw ex;
                    }
                }
            
            
            if(teacherLeaveMap!=null&&!teacherLeaveMap.isEmpty()){
                
                List<DBRecord>leaveRecords=teacherLeaveMap.values().stream().filter(rec->rec.getRecord().get(9).trim().equals("O")&&rec.getRecord().get(10).trim().equals("A")).collect(Collectors.toList());
                
                
                
                
                Iterator<DBRecord>valueIterator=leaveRecords.iterator();
                
                
                while(valueIterator.hasNext()){
                    
                    ArrayList<String>value=valueIterator.next().getRecord();
                    String leaveFrom=value.get(1).trim();
                    String leaveTo=value.get(2).trim();
                    String fromNoon=value.get(14).trim();
                    String toNoon=value.get(15).trim();
                    dbg("leaveFrom"+leaveFrom);
                    dbg("leaveTo"+leaveTo);
                    dbg("fromNoon"+fromNoon);
                    dbg("toNoon"+toNoon);
                    
                    Date leaveFromDate=formatter.parse(leaveFrom);
                    Date leaveToDate=formatter.parse(leaveTo);
                    
                    ArrayList<Date>leaveList=null;
                    
                    if(leaveFromDate.compareTo(fromDate)>=0&&leaveToDate.compareTo(toDate)<=0){
                    
                      leaveList=bs.getLeaveDates(leaveFrom, leaveTo, session, dbSession, inject);
                    
                    
                    }
                    
                    if(leaveList!=null&&!teacherLeaveMap.isEmpty()){
                    
                    for(int i=0;i<leaveList.size();i++){
                        
                        Date leaveDate=leaveList.get(i);
                        dbg("leaveDate"+leaveDate);
                        
                        if(leaveDate.compareTo(fromDate)>=0&&leaveDate.compareTo(toDate)<=0){
                            
                            dbg("leave date inside from date and to date");
                            
                            String leaveDateString=formatter.format(leaveDate);
                            char holidayChar=bs.getHolidayCharOfTheDay(instituteID, leaveDateString, session, dbSession, inject);
                            dbg("holidayChar"+holidayChar);
                            
                            if(holidayChar=='W'){
                                
                                if(leaveDateString.equals(leaveFrom)) {
                                    
                                    if(fromNoon.equals("F")){
                                           
                                           leaveCount=leaveCount+0.5f;
                                    }else if(fromNoon.equals("A")){
                                        
                                           leaveCount=leaveCount+0.5f;
                                    }else{
                                            
                                          leaveCount=leaveCount+1;
                                        
                                     }
                                    
                                }else if(leaveDateString.equals(leaveTo)) {
                                    
                                    if(toNoon.equals("F")){
                                           
                                           leaveCount=leaveCount+0.5f;
                                    }else if(toNoon.equals("A")){
                                        
                                           leaveCount=leaveCount+0.5f;
                                    }else{
                                            
                                          leaveCount=leaveCount+1;
                                        
                                     }
                                }else{
                                    
                                    leaveCount=leaveCount+1;
                                }
                    
                               
                           }else if(holidayChar!='H'){
                            
                              if(leaveDateString.equals(leaveFrom)) {
                                  
                                   if (holidayChar=='F'){
                                       
                                       if(fromNoon.equals("D")){ //Full day Leave
                                            
                                          leaveCount=leaveCount+0.5f;
                                       }
                                       if(fromNoon.equals("A")){//After Noon Leave
                                           
                                           leaveCount=leaveCount+0.5f;
                                       }
                                       
                                       
 
                                   }else if (holidayChar=='A'){
                                       
                                       if(fromNoon.equals("D")){//Full Day Leave
                                           
                                           leaveCount=leaveCount+0.5f;
                                       }
                                         if(fromNoon.equals("F")){//Fore noon Leave 
                                           
                                           leaveCount=leaveCount+0.5f;
                                       }
                                       
                                       
                                   }
        
                                   
                              }else if(leaveDateString.equals(leaveTo)) {
                               
                                   if (holidayChar=='F'){
                                       
                                       if(toNoon.equals("D")){ //Full day Leave
                                            
                                          leaveCount=leaveCount+0.5f;
                                       }
                                       if(toNoon.equals("A")){//After Noon Leave
                                           
                                           leaveCount=leaveCount+0.5f;
                                       }
                                       
                                       
 
                                   }else if (holidayChar=='A'){
                                       
                                       if(toNoon.equals("D")){//Full Day Leave
                                           
                                           leaveCount=leaveCount+0.5f;
                                       }
                                         if(toNoon.equals("F")){//Fore noon Leave 
                                           
                                           leaveCount=leaveCount+0.5f;
                                       }
                                       
                                       
                                   }
                              }else{
                                  
                                  leaveCount=leaveCount+0.5f;
                              }
                            
                           }
                            
                            
                            
                        }
                        
                    }
                    
                    }
                    
                }
                
                
                
            }
            
            
           dbg("end of getTeacherLeaveCount-->leaveCount-->"+leaveCount); 
        return leaveCount;
    }catch(DBValidationException ex){
            throw ex;
//        }catch(BSValidationException ex){
//            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
}
    
    
    
    
    
    
    
    
    
    
    private void setUnAuthPending()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
        
        try{
            dbg("inside setUnAuthPending");
            RequestBody<DashBoard> reqBody = request.getReqBody();
            BusinessService bs=inject.getBusinessService(session);
            DashBoard dashBoard =reqBody.get();
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            String instituteID=request.getReqHeader().getInstituteID();
            Map<String,DBRecord>unAuthMap=null;
            String currentDate=bs.getCurrentDate();
            String userID=request.getReqHeader().getUserID();
            BSValidation bsv=inject.getBsv(session);
            
             try{  
            
            
              unAuthMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"UnAuth_"+currentDate,"INSTITUTE", "IVW_UNAUTH_RECORDS", session, dbSession);
            
            
              }catch(DBValidationException ex){
                        if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                            session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                            session.getErrorhandler().removeSessionErrCode("DB_VAL_000");

                        }else{

                            throw ex;
                        }
               }
            
            
           if(unAuthMap!=null&&!unAuthMap.isEmpty()){
               
               dbg("unAuthMap is  not null");
               Iterator<DBRecord>valueIterator=unAuthMap.values().iterator();
               Request l_request=new Request();
               ArrayList<DBRecord>filteredRecords=new ArrayList();
               while(valueIterator.hasNext()){
                   
                   DBRecord value=valueIterator.next();
                   String service =value.getRecord().get(0).trim();
                   String operation =value.getRecord().get(1).trim();
                   String entityNames[] =value.getRecord().get(2).trim().split(";");
                   String entityValues[] =value.getRecord().get(3).trim().split(";");
                   dbg("service"+service);
                   dbg("operation"+operation);
                   dbg("entityNames"+entityNames.length);
                   
                   l_request.getReqHeader().setInstituteID(instituteID);
                   l_request.getReqHeader().setOperation("Auth");
                   l_request.getReqHeader().setService(service);
                   l_request.getReqHeader().setUserID(userID);
                   
                   Map<String,String>l_businessEntityMap=new HashMap();
                   
                   for(int i=0;i<entityNames.length;i++){
                       
                       l_businessEntityMap.put(entityNames[i], entityValues[i]);
                       
                   }
                   l_request.getReqHeader().setBusinessEntity(l_businessEntityMap);
                   
                   
                   if(bsv.userAccessValidation(l_request, session, dbSession, inject)){
                       
                       dbg("user access validation success for-->"+service+"-->"+operation);
                       filteredRecords.add(value);
                   }
                   
               }
               
            if(!filteredRecords.isEmpty()){
               
              Map<String,List<DBRecord>>serviceGroup= filteredRecords.stream().collect(Collectors.groupingBy(rec->rec.getRecord().get(0).trim()+"~"+rec.getRecord().get(1).trim()));
                       
              Iterator<String>keyIterator=serviceGroup.keySet().iterator();
              
              dashBoard.pending=new ArrayList();
              
              
              while(keyIterator.hasNext()){
                  
                  String key=keyIterator.next();
                  String service=key.split("~")[0];
                  String operation=key.split("~")[1];
                  
                  
//                  Map<String,String>l_businessEntityMap=new HashMap();
                  
//                for(int i=0;i<l_businessEntityArray.size();i++){
//                    JsonObject l_businessEntityObject=l_businessEntityArray.getJsonObject(i);
//                    String l_entityName=l_businessEntityObject.getString("entityName");
//                    String l_entityValue=l_businessEntityObject.getString("entityValue");
//                    l_businessEntityMap.put(l_entityName, l_entityValue);
//                }
                  
//                  if(bs.authAccessValidation(userID, service, operation, session, dbSession, inject)){
                  
                      
//                      dbg("auth access validation success for-->"+service+"-->"+operation);
                      int count=serviceGroup.get(key).size();
                      UnAuthPending pending=new UnAuthPending();
                      
                      pending.setService(this.getServiceString(service));
                      pending.setOperation(operation);
                      pending.setCount(Integer.toString(count));
                      dashBoard.pending.add(pending);
//              }
              }
               
               
            }
           } 
            
            
            
        }catch(DBValidationException ex){
            throw ex;
//        }catch(BSValidationException ex){
//            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
    
    
    private String getServiceString(String service)throws BSProcessingException{
       
        
        try{
            
            switch(service){
                
                
                case "GeneralLevelConfiguration":
                    
                  return "Institute/General Configuration";
                
                case "ClassLevelConfiguration":
                    
                  return "Institute/Class Configuration";
                
                case "HolidayMaintenance":
                    
                  return "Institute/Holiday Maintenance";
                
                case "GroupMapping":
                    
                  return "Institute/Group Maintenance";
                  
                case "InstituteFeeManagement":
                    
                  return "Institute/Fee Configuration";  
                
                case "InstitutePayment":
                    
                  return "Institute/Payment";   
                  
                case "Notification":
                    
                  return "Institute/Notification";  
                  
                case "InstituteOtherActivity":
                    
                  return "Institute/Extra Curricular Activity"; 
                  
                case "InstituteAssignment":
                    
                  return "Institute/Video Assignment";
                  
                case "ECircular":
                    
                  return "Institute/eCircular";  
                  
                case "ClassAttendance":
                    
                  return "Class/Attendance";   
                  
                case "ClassTimeTable":
                    
                  return "Class/TimeTable";  
                  
                case "ClassExamSchedule":
                    
                  return "Class/Exam Schedule"; 
                case "ClassMark":
                    
                  return "Class/Mark Entry";
                case "ClassSoftSkills":
                    
                  return "Class/Soft Skills";  
                  
                case "TeacherProfile":
                    
                  return "Teacher/Profile";
                  
                case "TeacherLeaveManagement":
                    
                  return "Teacher/Leave";
                  
                case "StudentProfile":
                    
                  return "Student/Profile";  
                  
                case "StudentOtherActivity":
                    
                  return "Student/Extra Curricular Activity";  
                  
                case "StudentLeaveManagement":
                    
                  return "Student/Leave";  
                  
                case "UserProfile":
                    
                  return "User/Profile";  
                  
                case "UserRole":
                    
                  return "User/Role";  
            }
            
            
            
            
            
            
            
            return service;
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
    
    
    
    
    private void setFeeDetails()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
        
        try{
            RequestBody<DashBoard> reqBody = request.getReqBody();
            DashBoard dashBoard =reqBody.get();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            BusinessService bs=inject.getBusinessService(session);
            String instituteID=request.getReqHeader().getInstituteID();
            EducationPeriod eduPeriod=bs.getEducationPeriod(instituteID, session, dbSession, inject);
            String dateformat=i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
            Date fromDate=formatter.parse(eduPeriod.getFromDate());
            Date toDate=formatter.parse(eduPeriod.getToDate());
            Map<String,DBRecord>feeMap=null;
            ArrayList<FeeDetails>feeDetailList=new ArrayList();
            String currentDate=bs.getCurrentDate();
            
             try{  
            
              feeMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", session, dbSession);
            
              }catch(DBValidationException ex){
                        if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                            session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                            session.getErrorhandler().removeSessionErrCode("DB_VAL_000");

                        }else{

                            throw ex;
                        }
               }
            
            
             if(feeMap!=null&&!feeMap.isEmpty()){
                 
                List<DBRecord>authorizedFeeRecords= feeMap.values().stream().filter(rec->rec.getRecord().get(11).trim().equals("O")&&rec.getRecord().get(12).trim().equals("A")).collect(Collectors.toList());
                 
                 
                 
                Iterator<DBRecord>valueIterator= authorizedFeeRecords.iterator();
                 
                 while(valueIterator.hasNext()){
                     
                     ArrayList<String>value=valueIterator.next().getRecord();
                     String feeID=value.get(2).trim();
                     String groupID=value.get(1).trim();
                     String feeType=value.get(4).trim();
                     String feeAmount=value.get(5).trim();
                     dbg("feeID"+feeID);
                     dbg("groupID"+groupID);
                     dbg("feeType"+feeType);
                     dbg("feeAmount"+feeAmount);
                     Date dueDate=formatter.parse(value.get(6).trim());
                     dbg("dueDate"+dueDate);
                     Date currentDatee=formatter.parse(currentDate);
                     
                     if(dueDate.compareTo(fromDate)>=0&&dueDate.compareTo(toDate)<=0){
                         
                        int studentSize= bs.getStudentsOfTheGroup(instituteID, groupID, session, dbSession, inject).size();
                        Double totalFeeAmountToCollect=studentSize*Double.parseDouble(feeAmount);
                        dbg("totalFeeAmountToCollect"+totalFeeAmountToCollect);
                        
                        Double totalAmountCollected=0.0;
                        Double pendingAmount=0.0;
                        Double overDueAmount=0.0;
                        
                        Map<String,DBRecord>paymentMap=null;
                             try{

                             paymentMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"FEE"+i_db_properties.getProperty("FOLDER_DELIMITER")+feeID+i_db_properties.getProperty("FOLDER_DELIMITER")+feeID,"FEE", "INSTITITUTE_FEE_PAYMENT", session, dbSession);


                             }catch(DBValidationException ex){
                                if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");

                                }else{

                                    throw ex;
                                }
                             }

                         if(paymentMap!=null&&!paymentMap.isEmpty()){

//                             List<DBRecord>authorizedPaymentRecords= paymentMap.values().stream().filter(rec->rec.getRecord().get(11).trim().equals("O")&&rec.getRecord().get(12).trim().equals("A")).collect(Collectors.toList());
//                             
//                             
//                             if(authorizedPaymentRecords!=null&&!authorizedPaymentRecords.isEmpty()){
                             
                            totalAmountCollected=paymentMap.values().stream().mapToDouble(rec->Double.parseDouble(rec.getRecord().get(3).trim())).sum();
                     
                         
                         
//                             }
                         
                         
                         }
                         dbg("totalAmountCollected"+totalAmountCollected);
                         
                        Double totalBalanceAmoount=totalFeeAmountToCollect-totalAmountCollected;
                       
                        dbg("totalBalanceAmoount"+totalBalanceAmoount);
                        
                        if(dueDate.compareTo(currentDatee)<=0){
                            
                            pendingAmount=totalBalanceAmoount;
                            
                        }else{
                            
                            overDueAmount=totalBalanceAmoount;
                        }     
                             
                        dbg("pendingAmount"+pendingAmount);
                        dbg("overDueAmount"+overDueAmount);
                        
                        FeeDetails feeDetail=new FeeDetails();
                        feeDetail.setFeeID(feeID);
                        feeDetail.setFeeType(feeType);
                        feeDetail.setFeeAmount(Double.toString(totalFeeAmountToCollect));
                        feeDetail.setAmountCollected(Double.toString(totalAmountCollected));
                        feeDetail.setAmountPending(Double.toString(pendingAmount));
                        feeDetail.setAmountOverDue(Double.toString(overDueAmount));
                        
                        
                        
                        feeDetailList.add(feeDetail);
                             
                     }
                 }
             }
             
             Map<String,List<FeeDetails>>feTypeFroup=feeDetailList.stream().collect(Collectors.groupingBy(rec->rec.getFeeType()));
            
            Iterator<String>feeTypeIterator=feTypeFroup.keySet().iterator();
            
            dashBoard.feeResult=new FeeResult[feTypeFroup.keySet().size()];
            int i=0;
            while(feeTypeIterator.hasNext()){
                
                String feeType=feeTypeIterator.next();
                List<FeeDetails>feeTypeList=feTypeFroup.get(feeType);
                
                Double totalFeeAmount = feeTypeList.stream().mapToDouble(rec->Double.parseDouble(rec.getFeeAmount())).sum();
                Double totalCollectedAmount= feeTypeList.stream().mapToDouble(rec->Double.parseDouble(rec.getAmountCollected())).sum();
                Double totalPendingAmount=feeTypeList.stream().mapToDouble(rec->Double.parseDouble(rec.getAmountPending())).sum();
                Double totalOverDueAmount=feeTypeList.stream().mapToDouble(rec->Double.parseDouble(rec.getAmountOverDue())).sum();
                
                dashBoard.feeResult[i]=new FeeResult();
                dashBoard.feeResult[i].setFeeType(feeType);
                dashBoard.feeResult[i].setTotalFee(Double.toString(totalFeeAmount));
                dashBoard.feeResult[i].setCollectedAmount(Double.toString(totalCollectedAmount));
                dashBoard.feeResult[i].setPendingAmount(Double.toString(totalPendingAmount));
                dashBoard.feeResult[i].setOverDueAmount(Double.toString(totalOverDueAmount));
                i++;
            }
            
        }catch(DBValidationException ex){
            throw ex;
//        }catch(BSValidationException ex){
//            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        
        
        
    }
    
    
    private void setClassFeeDetails()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
        
        try{
            dbg("inside setClassFeeDetails");
            RequestBody<DashBoard> reqBody = request.getReqBody();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            DashBoard dashBoard=reqBody.get();
            IPDataService pds=inject.getPdataservice();
            BusinessService bs=inject.getBusinessService(session);
            String instituteID=request.getReqHeader().getInstituteID();
            String dateformat=i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
            ArrayList<StudentFeeRecords>feeDetailList=new ArrayList();
            String currentDate=bs.getCurrentDate();
            String userID=request.getReqHeader().getUserID();
            Date currentDatee=formatter.parse(currentDate);
            
            Map<String,ArrayList<String>>  classMap= pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE","IVW_STANDARD_MASTER", session, dbSession);
            
            dbg("classMap size"+classMap.size());
            
            List<ArrayList<String>>teacherFilteredList=classMap.values().stream().filter(rec->rec.get(3).trim().equals(userID)&&rec.get(9).trim().equals("O")&&rec.get(10).trim().equals("A")).collect(Collectors.toList());
            
            
            if(teacherFilteredList!=null){
            
            dbg("teacherFilteredList size"+teacherFilteredList.size());
            
            String standard=teacherFilteredList.get(0).get(1).trim();
            String section=teacherFilteredList.get(0).get(2).trim();
            dbg("standard"+standard);
            dbg("section"+section);
            
            
//            ArrayList<String>studentsList=bs.getStudentsOfTheClass(instituteID, standard, section, session, dbSession, inject);
            Map<String,ArrayList<String>>studentMasterMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", session, dbSession);
            dbg("studentMasterMap size"+studentMasterMap.size());
            Map<String,List<ArrayList<String>>>l_studentGroup=studentMasterMap.values().stream().filter(rec->rec.get(2).trim().equals(standard)&&rec.get(3).trim().equals(section)&&rec.get(8).trim().equals("O")).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
            dbg("l_studentGroup size"+l_studentGroup.size());
            Iterator<String>studentIterator=l_studentGroup.keySet().iterator();
            
            while(studentIterator.hasNext()){
                
                Map<String,DBRecord>feeMap=null;
                Map<String,DBRecord>paymentMap=null;
                
                String studentID=studentIterator.next();
                dbg("studentID"+studentID);
                
                try{  
            
                  feeMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"FEE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","FEE", "STUDENT_FEE_MANAGEMENT", session, dbSession);

                }catch(DBValidationException ex){
                            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");

                            }else{

                                throw ex;
                            }
                   }
                
                try{  
            
                  paymentMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT", "STUDENT_PAYMENT", session, dbSession);

                }catch(DBValidationException ex){
                            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");

                            }else{

                                throw ex;
                            }
                   }
                
                if(feeMap!=null){

                    
                Iterator<DBRecord>valueIterator= feeMap.values().iterator();
                 
                 while(valueIterator.hasNext()){
                     
                     ArrayList<String>value=valueIterator.next().getRecord();
                     String feeID=value.get(1).trim();
                     dbg("feeID"+feeID);
                     String[] l_pkey={feeID};
                     
                     DBRecord instituteFeeRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", l_pkey, session,dbSession);
                     
                     
                     String feeType=instituteFeeRecord.getRecord().get(4).trim();
                     String feeAmount=instituteFeeRecord.getRecord().get(5).trim();
                     Date dueDate=formatter.parse(instituteFeeRecord.getRecord().get(6).trim());
                     dbg("feeType"+feeType);
                     dbg("feeAmount"+feeAmount);
                     dbg("dueDate"+dueDate);
                     
                     Double paidAmount=0.0;
                     if(dueDate.compareTo(currentDatee)<0){
                         
                         dbg("due date crosses the current date");
                         
                         if(paymentMap!=null){
                             
                             paidAmount= paymentMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals(feeID)).mapToDouble(rec->Float.parseFloat(rec.getRecord().get(4).trim())).sum();
                             
                         }
                         dbg("paidAmount-->"+paidAmount);
                         
                         Double balanceAmount=Float.parseFloat(feeAmount)-paidAmount;
                         dbg("balanceAmount-->"+balanceAmount);
                         StudentFeeRecords studentFeeRec=new StudentFeeRecords();

                         studentFeeRec.setStudentID(studentID);
                         studentFeeRec.setFeeID(feeID);
                         studentFeeRec.setFeeType(feeType);
                         studentFeeRec.setFeeAmount(feeAmount);
                         studentFeeRec.setOverDueAmount(Double.toString(balanceAmount));
                         feeDetailList.add(studentFeeRec);
                     }
                     
                 }
                
                                
            }

            }
            
           Map<String,List<StudentFeeRecords>>feeTypeList= feeDetailList.stream().collect(Collectors.groupingBy(rec->rec.getFeeType()));
            
           dbg("feeTypeList size"+feeTypeList.size());
           
           Iterator<String>feeTypeIterator=feeTypeList.keySet().iterator();
            
           dashBoard.overDue=new ClassOverDue[feeTypeList.keySet().size()];
           int i=0;
           while(feeTypeIterator.hasNext()){
               
               String feeType=feeTypeIterator.next();
               dbg("inside feeType iteration");
               dbg("feeType-->"+feeType);
               
               List<StudentFeeRecords>value=feeTypeList.get(feeType);
               
               Double overDueAmount=value.stream().mapToDouble(rec->Float.parseFloat(rec.getOverDueAmount())).sum();
               dbg("overDueAmount"+overDueAmount);
               
               
               dashBoard.overDue[i]=new ClassOverDue();
               dashBoard.overDue[i].setFeeType(feeType);
               dashBoard.overDue[i].setClassOverDue(Double.toString(overDueAmount));
               i++;
           }
            }
        }catch(DBValidationException ex){
            throw ex;
//        }catch(BSValidationException ex){
//            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        
        
        
    }
    
    
    
    
    
    

    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObjectBuilder body=Json.createObjectBuilder();
        try{
        dbg("inside institute selectBoxMaster buildJsonResFromBO");    
        RequestBody<DashBoard> reqBody = request.getReqBody();
        DashBoard dashBoard =reqBody.get();
        BusinessService bs=inject.getBusinessService(session);
        JsonArrayBuilder pendingQueueMaster=Json.createArrayBuilder();
        JsonArrayBuilder institutFeeDetails=Json.createArrayBuilder();
        JsonArrayBuilder classFeeDetails=Json.createArrayBuilder();
        String userID=request.getReqHeader().getUserID();
        String userType=bs.getUserType(userID, session, dbSession, inject);
        
        
        if(dashBoard.pending!=null){
            
            for(int i=0;i<dashBoard.pending.size();i++){
                
                
                pendingQueueMaster.add(Json.createObjectBuilder().add("service", dashBoard.pending.get(i).getService())
                                                                 .add("count", dashBoard.pending.get(i).getCount())
                                                                 .add("operation", dashBoard.pending.get(i).getOperation()));
                
                
            }
            
            
        }
        
        if(dashBoard.feeResult!=null){
            
            for(int i=0;i<dashBoard.feeResult.length;i++){
                
                
                institutFeeDetails.add(Json.createObjectBuilder().add("feeType", dashBoard.feeResult[i].getFeeType())
                                                                 .add("totalFee", dashBoard.feeResult[i].getTotalFee())
                                                                 .add("overDueAmount", dashBoard.feeResult[i].getOverDueAmount())
                                                                 .add("pendingAmount", dashBoard.feeResult[i].getPendingAmount())
                                                                 .add("collectedAmount", dashBoard.feeResult[i].getCollectedAmount()));
                
                
            }
            
            
        }
        
        if(dashBoard.overDue!=null){
            
            for(int i=0;i<dashBoard.overDue.length;i++){
                
                
                classFeeDetails.add(Json.createObjectBuilder().add("feeType", dashBoard.overDue[i].getFeeType())
                                                                 .add("classOverDueAmount", dashBoard.overDue[i].getClassOverDue()));
                
                
            }
            
            
        }
        
        
        if(userType.equals("T")){
        
        
        body=Json.createObjectBuilder()  .add("pendingQueueMaster", pendingQueueMaster)
                                         .add("classFeeDetails", classFeeDetails)
                                         .add("classOfTheTeacher", dashBoard.getClassOfTheTeacher())
                                         .add("totalStudents", dashBoard.getTotalNoOfStudents())
                                         .add("studentAttendance", dashBoard.getStudentAttendance())
                                         .add("teacherWorkingDays", dashBoard.getTeacherWorkingDays())
                                         .add("teacherLeaveDays", dashBoard.getTeacherLeaveDays());
        
        }else if(userType.equals("A")){
            
            
            
            body=Json.createObjectBuilder()  .add("pendingQueueMaster", pendingQueueMaster)
                                         .add("institutFeeDetails", institutFeeDetails)
                                         .add("totalTeachers", dashBoard.getTotalNoOfTeachers())
                                         .add("teacherAttendance", dashBoard.getTeacherAttendance())
                                         .add("totalStudents", dashBoard.getTotalNoOfStudents())
                                         .add("studentAttendance", dashBoard.getStudentAttendance())
                                         .add("smsLimit", dashBoard.getSmsLimit())
                                         .add("currentSMSBalance", dashBoard.getCurrentSMSBalance());
            
            
        }

                                            
              dbg(body.toString());  
           dbg("end of institute selectBoxMaster buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body.build();
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside institute selectBoxMaster--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }
       
       
       dbg("end of institute selectBoxMaster--->businessValidation"); 
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
    private boolean filterMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
      boolean status=true;
        try{
        dbg("inside institute selectBoxMaster master mandatory validation");
//        RequestBody<DashBoard> reqBody = request.getReqBody();
//        DashBoard selectBoxMaster =reqBody.get();
         
         
        
         
          
         
          
          
        dbg("end of institute selectBoxMaster master mandatory validation");
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
