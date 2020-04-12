/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentexamschedule;

import com.ibd.businessViews.IStudentExamScheduleService;
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
import java.util.Iterator;
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
 * @author DELL
 */
//@Local(IStudentExamScheduleService.class)
@Remote(IStudentExamScheduleService.class)
@Stateless
public class StudentExamScheduleService implements IStudentExamScheduleService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentExamScheduleService(){
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
       dbg("inside StudentExamScheduleService--->processing");
       dbg("StudentExamScheduleService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
       StudentExamSchedule studentExamSchedule =reqBody.get();
       String l_studentID=studentExamSchedule.getStudentID();
       String l_exam=studentExamSchedule.getExam();
       l_lockKey=l_studentID.concat("~").concat(l_exam);
       
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<IStudentExamScheduleService>studentExamScheduleEJB=new BusinessEJB();
       studentExamScheduleEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentExamScheduleEJB);
        if(request.getReqHeader().getOperation().equals("View")){
           
         String studentID=  bs.studentValidation(studentExamSchedule.getStudentID(), studentExamSchedule.getStudentName(), request.getReqHeader().getInstituteID(), session, dbSession, inject);
         
          
         if(studentID==null||studentID.isEmpty()){
             
             errhandler.log_app_error("BS_VAL_002","Student ID or Name");  
             throw new BSValidationException("BSValidationException");
         }
         
         studentExamSchedule.setStudentID(studentID);
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
      
       bs.businessServiceProcssing(request, exAudit, inject,studentExamScheduleEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentExamScheduleEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student examSchedule");
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
                dbg("Response-->"+jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
               // if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                         clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes  
              // BSProcessingException ex=new BSProcessingException("response contains special characters");
                //   dbg(ex);
                  // session.clearSessionObject();
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
      StudentExamSchedule studentExamSchedule=new StudentExamSchedule();
      RequestBody<StudentExamSchedule> reqBody = new RequestBody<StudentExamSchedule>(); 
           
      try{
      dbg("inside student examSchedule buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      studentExamSchedule.setStudentID(l_body.getString("studentID"));
      studentExamSchedule.setStudentName(l_body.getString("studentName"));
      
      studentExamSchedule.setExam(l_body.getString("exam"));
      if(!l_operation.equals("View")){
          JsonArray l_schedule=l_body.getJsonArray("Subjectschedules");
          studentExamSchedule.schedule=new Schedule[l_schedule.size()];
          for(int i=0;i<l_schedule.size();i++){
              studentExamSchedule.schedule[i]=new Schedule();
              JsonObject l_scheduleObject=l_schedule.getJsonObject(i);
              studentExamSchedule.schedule[i].setSubjectID(l_scheduleObject.getString("subjectID"));
              studentExamSchedule.schedule[i].setDate(l_scheduleObject.getString("date"));
              JsonObject l_startTimeObject=l_scheduleObject.getJsonObject("startTime");
              String l_startTimeHour=l_startTimeObject.getString("hour");
              String l_startTimeMin=l_startTimeObject.getString("min");
              studentExamSchedule.schedule[i].setStartTimeHour(l_startTimeHour);
              studentExamSchedule.schedule[i].setStartTimeMin(l_startTimeMin);
              JsonObject l_endTimeObject=l_scheduleObject.getJsonObject("endTime");
              String l_endTimeHour=l_endTimeObject.getString("hour");
              String l_endTimeMin=l_endTimeObject.getString("min");
              studentExamSchedule.schedule[i].setEndTimeHour(l_endTimeHour);
              studentExamSchedule.schedule[i].setEndTimeMin(l_endTimeMin);
              studentExamSchedule.schedule[i].setHall(l_scheduleObject.getString("hall"));
           }
        
      }
        reqBody.set(studentExamSchedule);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
    try{ 
        dbg("inside stident examSchedule create"); 
        RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
        StudentExamSchedule studentExamSchedule =reqBody.get();
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();   
        String l_studentID=studentExamSchedule.getStudentID();
        String l_exam=studentExamSchedule.getExam();
      
        dbg("before create record call");
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",71,l_studentID,l_exam,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
        
        for(int i=0;i<studentExamSchedule.schedule.length;i++){
             String l_subjectID=studentExamSchedule.schedule[i].getSubjectID();
             String l_date=studentExamSchedule.schedule[i].getDate();
             String l_startTimeHour=studentExamSchedule.schedule[i].getStartTimeHour();
             String l_startTimeMin=studentExamSchedule.schedule[i].getStartTimeMin();
             String l_endTimeHour=studentExamSchedule.schedule[i].getEndTimeHour();
             String l_endTimeMin=studentExamSchedule.schedule[i].getEndTimeMin();
             String l_hall=studentExamSchedule.schedule[i].getHall();
             
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",72,l_studentID,l_exam,l_subjectID,l_date,l_hall,l_versionNumber,l_startTimeHour,l_startTimeMin,l_endTimeHour,l_endTimeMin);
  
         }
        
        dbg("end of student examSchedule create"); 
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
        dbg("inside student examSchedule--->auth update"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
        StudentExamSchedule studentExamSchedule =reqBody.get();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_studentID=studentExamSchedule.getStudentID();
        String l_exam=studentExamSchedule.getExam();
        String[] l_primaryKey={l_studentID,l_exam};
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("4", l_checkerID);
         l_column_to_update.put("6", l_checkerDateStamp);
         l_column_to_update.put("8", l_authStatus);
         l_column_to_update.put("11", l_checkerRemarks);
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_EXAM_SCHEDULE_MASTER", l_primaryKey, l_column_to_update, session);
         dbg("end of student examSchedule--->auth update");          
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
        
       Map<String,String> l_column_to_update;
                
       try{ 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
        StudentExamSchedule studentExamSchedule =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();   
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();   
        String l_studentID=studentExamSchedule.getStudentID();  
        String l_exam=studentExamSchedule.getExam();
        
                        l_column_to_update= new HashMap();
                        l_column_to_update.put("1", l_studentID);
                        l_column_to_update.put("2", l_exam);
                        l_column_to_update.put("3", l_makerID);
                        l_column_to_update.put("4", l_checkerID);
                        l_column_to_update.put("5", l_makerDateStamp);
                        l_column_to_update.put("6", l_checkerDateStamp);
                        l_column_to_update.put("7", l_recordStatus);
                        l_column_to_update.put("8", l_authStatus);
                        l_column_to_update.put("9", l_versionNumber);
                        l_column_to_update.put("10", l_makerRemarks);
                        l_column_to_update.put("11", l_checkerRemarks);
                        String[] l_primaryKey={l_studentID,l_exam};
                        
                       dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_EXAM_SCHEDULE_MASTER",l_primaryKey,l_column_to_update,session);
      
            IDBReadBufferService readBuffer = inject.getDBReadBufferService();
            Map<String,DBRecord>l_examScheduleMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","STUDENT_EXAM_SCHEDULE_DETAIL", session, dbSession);
            setOperationsOfTheRecord("STUDENT_EXAM_SCHEDULE_DETAIL",l_examScheduleMap);            
                       
                       
             for(int i=0;i<studentExamSchedule.schedule.length;i++){
                 String l_subjectID=studentExamSchedule.schedule[i].getSubjectID();
                 String l_date=studentExamSchedule.schedule[i].getDate();
                 String l_startTimeHour=studentExamSchedule.schedule[i].getStartTimeHour();
                 String l_startTimeMin=studentExamSchedule.schedule[i].getStartTimeMin();
                 String l_endTimeHour=studentExamSchedule.schedule[i].getEndTimeHour();
                 String l_endTimeMin=studentExamSchedule.schedule[i].getEndTimeMin();
                 String l_hall=studentExamSchedule.schedule[i].getHall(); 

                 if(studentExamSchedule.schedule[i].getOperation().equals("U")){

                     l_column_to_update= new HashMap();
                                l_column_to_update.put("1", l_studentID);
                                l_column_to_update.put("2", l_exam);
                                l_column_to_update.put("3", l_subjectID);
                                l_column_to_update.put("4", l_date);
                                l_column_to_update.put("5", l_hall);
                                l_column_to_update.put("6", l_versionNumber);
                                l_column_to_update.put("7", l_startTimeHour);
                                l_column_to_update.put("8", l_startTimeMin);
                                l_column_to_update.put("9", l_endTimeHour);
                                l_column_to_update.put("10", l_endTimeMin);

                                String[] l_markPKey={l_studentID,l_exam,l_subjectID};

                               dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_EXAM_SCHEDULE_DETAIL",l_markPKey,l_column_to_update,session);

                     }else{


                           dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",72,l_studentID,l_exam,l_subjectID,l_date,l_hall,l_versionNumber,l_startTimeHour,l_startTimeMin,l_endTimeHour,l_endTimeMin);

                     }          
             }
             
             
             
		ArrayList<String>cpList=getRecordsToDelete("STUDENT_EXAM_SCHEDULE_DETAIL",l_examScheduleMap);
         
                  for(int i=0;i<cpList.size();i++){
                      String pkey=cpList.get(i);
                      deleteRecordsInTheList("STUDENT_EXAM_SCHEDULE_DETAIL",pkey);
                  }
             
             
             
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

    private void setOperationsOfTheRecord(String tableName,Map<String,DBRecord>tableMap)throws DBValidationException,DBProcessingException,BSProcessingException{
         
         try{
            dbg("inside getOperationsOfTheRecord"); 
            RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
            StudentExamSchedule examSchedule =reqBody.get();
            String l_studentID=examSchedule.getStudentID();
            String l_exam=examSchedule.getExam();
            dbg("tableName"+tableName);
            
            switch(tableName){
                
                case "STUDENT_EXAM_SCHEDULE_DETAIL":  
                
                         for(int i=0;i<examSchedule.schedule.length;i++){
                                String l_subjectID=examSchedule.schedule[i].getSubjectID();
                                String l_pkey=l_studentID+"~"+l_exam+"~"+l_subjectID;
                               if(tableMap.containsKey(l_pkey)){

                                    examSchedule.schedule[i].setOperation("U");
                                }else{

                                    examSchedule.schedule[i].setOperation("C");
                                }
                         } 
                  break;      

            }
                  
           dbg("end of getOperationsOfTheRecord"); 

        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
         
     }    

private ArrayList<String>getRecordsToDelete(String tableName,Map<String,DBRecord>tableMap)throws DBValidationException,DBProcessingException,BSProcessingException{
         
         try{
           
           dbg("inside getRecordsToDelete");  
           RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
           StudentExamSchedule examSchedule =reqBody.get();
           String l_studentID=examSchedule.getStudentID();
           String l_exam=examSchedule.getExam();
           ArrayList<String>recordsToDelete=new ArrayList();
//           Iterator<String>keyIterator=tableMap.keySet().iterator();
           List<DBRecord>filteredRecords=tableMap.values().stream().filter(rec->rec.getRecord().get(0).trim().equals(l_studentID)&&rec.getRecord().get(1).trim().equals(l_exam)).collect(Collectors.toList());
        
           dbg("tableName"+tableName);
           switch(tableName){
           
                 case "STUDENT_EXAM_SCHEDULE_DETAIL":   
                   
                   for(int j=0;j<filteredRecords.size();j++){
                        String subjectID=filteredRecords.get(j).getRecord().get(2).trim();
                        String tablePkey=l_studentID+"~"+l_exam+"~"+subjectID;
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                       for(int i=0;i<examSchedule.schedule.length;i++){
                           String l_subjectID=examSchedule.schedule[i].getSubjectID(); 
                           String l_requestPkey=l_studentID+"~"+l_exam+"~"+l_subjectID;
                           dbg("l_requestPkey"+l_requestPkey);
                           if(tablePkey.equals(l_requestPkey)){
                               recordExistence=true;
                           }
                        }   
                        if(!recordExistence){
                            recordsToDelete.add(tablePkey);
                        }

                    }
                   break;
                   
                   
               
                    }
             
           dbg("records to delete"+recordsToDelete.size());
           dbg("end of getRecordsToDelete");
           return recordsToDelete;

        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
     }

private void deleteRecordsInTheList(String tableName,String pkey)throws DBValidationException,DBProcessingException,BSProcessingException{
         
         try{
             RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
             StudentExamSchedule examSchedule =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_studentID=examSchedule.getStudentID();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IDBTransactionService dbts=inject.getDBTransactionService();
             String[] pkArr=pkey.split("~");
             
             
             dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",tableName, pkArr, session);
             
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
    
    public void delete()throws DBValidationException,DBProcessingException,BSProcessingException{
        try{
        dbg("inside student examSchedule delete");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
        StudentExamSchedule studentExamSchedule =reqBody.get();
        String l_studentID=studentExamSchedule.getStudentID();
        String l_exam=studentExamSchedule.getExam();
        String[] l_primaryKey={l_studentID,l_exam};
             
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_EXAM_SCHEDULE_MASTER", l_primaryKey, session);
       
        for(int i=0;i<studentExamSchedule.schedule.length;i++){
             String l_subjectID=studentExamSchedule.schedule[i].getSubjectID();
             String[] l_schedulePKey={l_studentID,l_exam,l_subjectID};
                        
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_EXAM_SCHEDULE_DETAIL",l_schedulePKey,session);
        }              
                        
          dbg("end of student examSchedule delete");
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

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student examSchedule--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        StudentExamSchedule studentExamSchedule =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_studentID=studentExamSchedule.getStudentID();
        String l_exam=studentExamSchedule.getExam();
       // String[] l_pkey={l_studentID};
        String[] studentPKey={l_studentID};
        IPDataService pds=inject.getPdataservice();
        ArrayList<String>studentMasterlist= pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",studentPKey);
        String l_standard=studentMasterlist.get(2).trim();
        String l_section=studentMasterlist.get(3).trim();
      
        String[] l_pkey={l_standard,l_section,l_exam};
        DBRecord l_examScheduleRecord;
        Map<String,DBRecord>l_scheduleMap;
        try
        {   
         l_examScheduleRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS","CLASS_EXAM_SCHEDULE_MASTER", l_pkey, session, dbSession);
         l_scheduleMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS","CLASS_EXAM_SCHEDULE_DETAIL", session, dbSession);
        }
         catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013",l_standard+l_section+l_exam);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
        buildBOfromDB(l_examScheduleRecord,l_scheduleMap,l_standard,l_section);     
     
       // buildBOfromDB(l_examScheduleRecord,l_scheduleMap);
        
          dbg("end of  completed student examSchedule--->view");                
        }
        catch(BSValidationException ex){
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
    
      private void buildBOfromDB(DBRecord p_studentExamScheduleRecord,Map<String,DBRecord>p_detailMap,String l_standard,String l_section)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           BusinessService bs=inject.getBusinessService(session);
           String instituteID=request.getReqHeader().getInstituteID();
           RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
           StudentExamSchedule studentExamSchedule =reqBody.get();
           String l_exam=studentExamSchedule.getExam();
           String l_studentID=studentExamSchedule.getStudentID();
           String studentName=bs.getStudentName(l_studentID, instituteID, session, dbSession, inject);
           studentExamSchedule.setStudentName(studentName);
          //  String l_standard=studentExamSchedule.();
           //String l_section=studentExamSchedule.getSection();
            ArrayList<String>l_studentExamScheduleList= p_studentExamScheduleRecord.getRecord();
           
           if(l_studentExamScheduleList!=null&&!l_studentExamScheduleList.isEmpty()){
            request.getReqAudit().setMakerID(l_studentExamScheduleList.get(3).trim());
            request.getReqAudit().setCheckerID(l_studentExamScheduleList.get(4).trim());
            request.getReqAudit().setMakerDateStamp(l_studentExamScheduleList.get(5).trim());
            request.getReqAudit().setCheckerDateStamp(l_studentExamScheduleList.get(6).trim());
            request.getReqAudit().setRecordStatus(l_studentExamScheduleList.get(7).trim());
            request.getReqAudit().setAuthStatus(l_studentExamScheduleList.get(8).trim());
            request.getReqAudit().setVersionNumber(l_studentExamScheduleList.get(9).trim());
            request.getReqAudit().setMakerRemarks(l_studentExamScheduleList.get(10).trim());
            request.getReqAudit().setCheckerRemarks(l_studentExamScheduleList.get(11).trim());
            }
           
             // Map<String,List<DBRecord>> examWiseGroup=p_detailMap.values().stream().filter(rec->rec.getRecord().get(2).trim().equals(l_exam) && rec.getRecord().get(0).trim().equals(l_standard) && rec.getRecord().get(1).trim().equals(l_section)).collect(Collectors.groupingBy(rec->rec.getRecord().get(2).trim()));
              int versionIndex=bs.getVersionIndexOfTheTable("CLASS_EXAM_SCHEDULE_DETAIL", "CLASS", session, dbSession, inject);
           
           
            Map<String,List<DBRecord>> examWiseGroup=p_detailMap.values().stream().filter(rec->rec.getRecord().get(versionIndex).equals(request.getReqAudit().getVersionNumber())).collect(Collectors.groupingBy(rec->rec.getRecord().get(2).trim()));
               
             studentExamSchedule.schedule=new Schedule[examWiseGroup.get(l_exam).size()];;
            int i=0;
                for(DBRecord l_scheduleRecords: examWiseGroup.get(l_exam)){
                   studentExamSchedule.schedule[i]=new Schedule(); studentExamSchedule.schedule[i].setSubjectID(l_scheduleRecords.getRecord().get(3).trim());
                   String subjectName=bs.getSubjectName(l_scheduleRecords.getRecord().get(3).trim(), instituteID, session, dbSession, inject);
                   studentExamSchedule.schedule[i].setSubjectName(subjectName);
                   studentExamSchedule.schedule[i].setDate(l_scheduleRecords.getRecord().get(4).trim());
                   studentExamSchedule.schedule[i].setHall(l_scheduleRecords.getRecord().get(5).trim());
                   studentExamSchedule.schedule[i].setStartTimeHour(l_scheduleRecords.getRecord().get(7).trim());
                   studentExamSchedule.schedule[i].setStartTimeMin(l_scheduleRecords.getRecord().get(8).trim());
                   studentExamSchedule.schedule[i].setEndTimeHour(l_scheduleRecords.getRecord().get(9).trim());
                   studentExamSchedule.schedule[i].setEndTimeMin(l_scheduleRecords.getRecord().get(10).trim());
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
        dbg("inside student examSchedule buildJsonResFromBO");    
        RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
        StudentExamSchedule studentExamSchedule =reqBody.get();
        JsonArrayBuilder schedule=Json.createArrayBuilder();
        for(int i=0;i<studentExamSchedule.schedule.length;i++){
            
            
            
            schedule.add(Json.createObjectBuilder().add("subjectID", studentExamSchedule.schedule[i].getSubjectID())
                                                   .add("subjectName", studentExamSchedule.schedule[i].getSubjectName())
                                                   .add("date", studentExamSchedule.schedule[i].getDate())
                                                   .add("startTime", Json.createObjectBuilder().add("hour", studentExamSchedule.schedule[i].getStartTimeHour()).add("min", studentExamSchedule.schedule[i].getStartTimeMin()))
                                                   .add("endTime", Json.createObjectBuilder().add("hour", studentExamSchedule.schedule[i].getEndTimeHour()).add("min", studentExamSchedule.schedule[i].getEndTimeMin()))
                                                   .add("hall", studentExamSchedule.schedule[i].getHall()));
        }
        

        body=Json.createObjectBuilder().add("studentID",  studentExamSchedule.getStudentID())
                                       .add("studentName",  studentExamSchedule.getStudentName())
                                       .add("exam", studentExamSchedule.getExam())
                                       .add("Subjectschedules", schedule)
                                       .build();
                                            
              dbg(body.toString());  
           dbg("end of student examSchedule buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student examSchedule--->businessValidation");    
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
       dbg("end of student examSchedule--->businessValidation"); 
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
        dbg("inside student examSchedule master mandatory validation");
        RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
        StudentExamSchedule studentExamSchedule =reqBody.get();
        
         if(studentExamSchedule.getStudentID()==null||studentExamSchedule.getStudentID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","studentID");  
         }
          if(studentExamSchedule.getExam()==null||studentExamSchedule.getExam().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","exam");  
         }
     
          
        dbg("end of student examSchedule master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student examSchedule masterDataValidation");
             RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             StudentExamSchedule studentExamSchedule =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_studentID=studentExamSchedule.getStudentID();
             String l_exam=studentExamSchedule.getExam();
             
             if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","studentID");
             }
             if(!bsv.examValidation(l_exam, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","exam");
             }
            
            dbg("end of student examSchedule masterDataValidation");
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
        RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
        StudentExamSchedule studentExamSchedule =reqBody.get();
        
        try{
            
            dbg("inside student examSchedule detailMandatoryValidation");
           
            if(studentExamSchedule.getSchedule()==null||studentExamSchedule.getSchedule().length==0){
                status=false;
                errhandler.log_app_error("BS_VAL_002","Subjectschedules");
            }else{
                
                for(int i=0;i<studentExamSchedule.getSchedule().length;i++){
                    
                    if(studentExamSchedule.getSchedule()[i].getDate()==null||studentExamSchedule.getSchedule()[i].getDate().isEmpty()){
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","Date");
                    }
                    if(studentExamSchedule.getSchedule()[i].getSubjectID()==null||studentExamSchedule.getSchedule()[i].getSubjectID().isEmpty()){
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","SubjectID");
                    }
                    if(studentExamSchedule.getSchedule()[i].getStartTimeHour()==null||studentExamSchedule.getSchedule()[i].getStartTimeHour().isEmpty()){
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","startTimeHour");
                    }
                    if(studentExamSchedule.getSchedule()[i].getStartTimeMin()==null||studentExamSchedule.getSchedule()[i].getStartTimeMin().isEmpty()){
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","startTimeMin");
                    }
                    if(studentExamSchedule.getSchedule()[i].getEndTimeHour()==null||studentExamSchedule.getSchedule()[i].getEndTimeHour().isEmpty()){
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","endTimeHour");
                    }
                    if(studentExamSchedule.getSchedule()[i].getEndTimeMin()==null||studentExamSchedule.getSchedule()[i].getEndTimeMin().isEmpty()){
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","endTimeMin");
                    }
                    if(studentExamSchedule.getSchedule()[i].getHall()==null||studentExamSchedule.getSchedule()[i].getHall().isEmpty()){
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","Hall");
                    }
                }
            }
            
            
           dbg("end of student examSchedule detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student examSchedule detailDataValidation");
             RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
             StudentExamSchedule studentExamSchedule =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             BSValidation bsv=inject.getBsv(session);
            
             for(int i=0;i<studentExamSchedule.getSchedule().length;i++){
                 
                String l_subjectID=studentExamSchedule.getSchedule()[i].getSubjectID();
                String l_date=studentExamSchedule.getSchedule()[i].getDate();
                String l_starTimeHour=studentExamSchedule.getSchedule()[i].getStartTimeHour();
                String l_startTimeMin=studentExamSchedule.getSchedule()[i].getStartTimeMin();
                String l_endTimeHour=studentExamSchedule.getSchedule()[i].getEndTimeHour();
                String l_endTimeMin=studentExamSchedule.getSchedule()[i].getEndTimeMin();
                String l_hall=studentExamSchedule.getSchedule()[i].getHall();
                
                if(!bsv.subjectIDValidation(l_subjectID, l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","subjectID");
                }
                if(!bsv.dateFormatValidation(l_date, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","Date");
                }
                if(!bsv.hourValidation(l_starTimeHour,l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","startTimeHour");
                }
                if(!bsv.minValidation(l_startTimeMin,l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","startTimeMin");
                }
                if(!bsv.hourValidation(l_endTimeHour,l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","endTimeHour");
                }
                if(!bsv.minValidation(l_endTimeMin,l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","endTimeMin");
                }
                if(!bsv.examHallValidation(l_hall,l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","Hall");
                }
                 
             }
             
            
            dbg("end of student examSchedule detailDataValidation");
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
        dbg("inside StudentExamSchedule--->getExistingAudit") ;
        exAudit= new ExistingAudit();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_operation=request.getReqHeader().getOperation();
        String l_service=request.getReqHeader().getService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
       if(!(l_operation.equals("Create")) && !(l_operation.equals("View"))){
              if(l_operation.equals("AutoAuth")&&l_versionNumber.equals("1")){
                return null;
              }else{  
               dbg("inside StudentExamSchedule--->getExistingAudit--->Service--->UserExamSchedule");  
               RequestBody<StudentExamSchedule> studentExamScheduleBody = request.getReqBody();
               StudentExamSchedule studentExamSchedule =studentExamScheduleBody.get();
               String l_studentID=studentExamSchedule.getStudentID();
               String l_exam=studentExamSchedule.getExam();
               String[] l_pkey={l_studentID,l_exam};
               DBRecord l_studentExamScheduleRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_EXAM_SCHEDULE_MASTER", l_pkey, session, dbSession);
               exAudit.setAuthStatus(l_studentExamScheduleRecord.getRecord().get(7).trim());
               exAudit.setMakerID(l_studentExamScheduleRecord.getRecord().get(2).trim());
               exAudit.setRecordStatus(l_studentExamScheduleRecord.getRecord().get(6).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_studentExamScheduleRecord.getRecord().get(8).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of StudentExamSchedule--->getExistingAudit") ;
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
    
     public  void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     
     
     try{
        dbg("inside relationshipProcessing") ;
//        BusinessService bs=inject.getBusinessService(session);
        RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
        StudentExamSchedule studentExamSchedule =reqBody.get();
        String l_studentID=studentExamSchedule.getStudentID();
        String l_exam=studentExamSchedule.getExam();
        
        for(int i=0;i<studentExamSchedule.getSchedule().length;i++){
            
            String l_subjectID=studentExamSchedule.getSchedule()[i].getSubjectID();
            String l_currentEvent=getCurrentEventFromBO(l_subjectID);
            String l_pkeyOfCurrentEvent=l_studentID+","+l_exam+","+l_subjectID;
            String l_date=studentExamSchedule.getSchedule()[i].getDate();
//            ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//            String l_year=convertedDate.getYear();
//            String l_month=convertedDate.getMonth();
//            String l_day=convertedDate.getDay();
//            String[] l_pkey={l_studentID,l_year,l_month,l_day};
            
           if(exAudit.getRelatioshipOperation().equals("C")){

             createOrUpdateStudentCalender(l_date,l_currentEvent,l_pkeyOfCurrentEvent);
        }else if(exAudit.getRelatioshipOperation().equals("M")){
            
             createOrUpdateStudentCalender(l_date,l_currentEvent,l_pkeyOfCurrentEvent);
        }else if(exAudit.getRelatioshipOperation().equals("D")){
            
            deleteStudentCalender(l_date,l_currentEvent);
        }
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
    private void createOrUpdateStudentCalender(String p_date,String p_currentEvent,String p_pkeyOfCurrentEvent)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
     
        try{
        dbg("inside createOrUpdateStudentCalender") ;  
        boolean recordExistence=true;
        IDBTransactionService dbts=inject.getDBTransactionService();
        BusinessService bs=inject.getBusinessService(session);
        RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
        StudentExamSchedule studentExamSchedule =reqBody.get();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_studentID=studentExamSchedule.getStudentID();
        ConvertedDate convertedDate=bs.getYearMonthandDay(p_date);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth();
        String l_day=convertedDate.getDay();
        int day=Integer.parseInt(l_day);
        String[] l_pkey={l_studentID,l_year,l_month};
        DBRecord studentCalenderRec;
        String l_previousMonthEvent=null;
        String newDayEvent=null;
        String previousEvent=null;
        
        dbg("session errorCode before readRecord"+session.getErrorhandler().getSession_error_code());
        
        try{
        
            studentCalenderRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_CALENDER", l_pkey, session, dbSession,true);
            l_previousMonthEvent=studentCalenderRec.getRecord().get(3).trim();
            dbg("l_previousMonthEvent"+l_previousMonthEvent);
        }catch(DBValidationException ex){
            dbg("no recordExistFor"+l_year+l_month+ex);
            if(ex.toString().contains("DB_VAL_011")){
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
               recordExistence=false;                
            }
        }
        dbg("session errorCode after readRecord"+session.getErrorhandler().getSession_error_code());
        dbg("recordExistence"+recordExistence);
        
        if(recordExistence){
            
            String l_previousDayEvents=l_previousMonthEvent.split("d")[day];
            dbg("l_previousDayEvents"+l_previousDayEvents);
            if(l_previousDayEvents.contains("E")){
                dbg("l_previousDayEvents.contains E");
                String[]l_eventArray=l_previousDayEvents.split("//");
                dbg("l_eventArray size"+l_eventArray.length);
                boolean pkExistence=false;
                for(int i=1;i<l_eventArray.length;i++){
                    previousEvent=l_eventArray[i];
                    dbg("previousEvent"+previousEvent);
                    String l_pkColumnsWithoutVersion=bs.getPKcolumnswithoutVersion("STUDENT","SVW_STUDENT_EXAM_SCHEDULE_DETAIL",session,inject);
                    int l_lengthOfPkWithoutVersion=l_pkColumnsWithoutVersion.split("~").length;
                    String l_pkey_of_ExistingEvent=bs.getPkeyOfExistingEvent(l_lengthOfPkWithoutVersion,previousEvent);
                    dbg("l_pkey_of_ExistingEvent"+l_pkey_of_ExistingEvent);
                    if(p_pkeyOfCurrentEvent.equals(l_pkey_of_ExistingEvent)){
                        dbg("pKeyOfCurrentEvent.equals(l_pkey_of_ExistingEvent)");
                        pkExistence=true;
                        break;
                    }
                    
                }
                
                dbg("after for loop pk existence"+pkExistence);
                if(pkExistence){
                
                     newDayEvent=l_previousDayEvents.replace(previousEvent+"//", p_currentEvent);
                     dbg("newDayEvent"+newDayEvent);
                }else{
                    
                    newDayEvent=l_previousDayEvents.concat(p_currentEvent);
                    dbg("newDayEvent"+newDayEvent);
                }
            
            
            }else{
                dbg("l_previousDayEvents not contains AS");
                newDayEvent=l_previousDayEvents.concat(p_currentEvent);
            }
            
            String newMonthEvent=bs.setDayEventinMonthEvent(l_previousMonthEvent,l_previousDayEvents,newDayEvent,l_day);
            Map<String,String>l_column_to_update=new HashMap();
            l_column_to_update.put("4", newMonthEvent);
             
             dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_CALENDER", l_pkey, l_column_to_update, session);   
            

        }else{
            String l_monthEvent=bs.createMonthEvent(p_date);
            dbg(l_day+"//"+" "+"d");
            dbg(l_day+"//"+p_currentEvent+"d");
            
            newDayEvent =l_monthEvent.replace("d"+l_day+"//"+" "+"d", "d"+l_day+"//"+p_currentEvent+"d");
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",73,l_studentID,l_year,l_month,newDayEvent);
        }
        
        
        
        
          
        dbg("end of buildRequestAndCallStudentMaster");  
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
    
 private void deleteStudentCalender(String p_date,String p_currentEvent)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
     
        try{
        dbg("inside deleteStudentCalender");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        BusinessService bs=inject.getBusinessService(session);
        RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
        StudentExamSchedule studentExamchedule =reqBody.get();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_studentID=studentExamchedule.getStudentID();
        ConvertedDate convertedDate=bs.getYearMonthandDay(p_date);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth();
        String l_day=convertedDate.getDay();
        int day=Integer.parseInt(l_day);
        String[] l_pkey={l_studentID,l_year,l_month};
        DBRecord studentCalenderRec;
        String l_previousMonthEvent=null;
        String newDayEvent=null;
        

        
            studentCalenderRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_CALENDER", l_pkey, session, dbSession);
            l_previousMonthEvent=studentCalenderRec.getRecord().get(3).trim();
            dbg("l_previousMonthEvent"+l_previousMonthEvent);
            String l_previousDayEvents=l_previousMonthEvent.split("d")[day];
            dbg("l_previousDayEvents"+l_previousDayEvents);
            
            if(l_previousDayEvents.split("//").length==2){
               newDayEvent=l_previousDayEvents.replace(p_currentEvent, " ");
            
            }else{
                
                newDayEvent=l_previousDayEvents.replace(p_currentEvent, "");
            } 
            String newMonthEvent=bs.setDayEventinMonthEvent(l_previousMonthEvent,l_previousDayEvents,newDayEvent,l_day);
            
            Map<String,String>l_column_to_update=new HashMap();
            l_column_to_update.put("4", newMonthEvent);
             
            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_CALENDER", l_pkey, l_column_to_update, session);   
            

        
        
        
        
        
          
       dbg("inside deleteStudentCalender");
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
 

 
 
 
 
 private String getCurrentEventFromBO(String p_subjectID)throws BSProcessingException{
     
     try{
         
        dbg("inside StudentExamSchedule--->getCurrentEventFromBO");
        RequestBody<StudentExamSchedule> reqBody = request.getReqBody();
        StudentExamSchedule studentExamSchedule =reqBody.get();
        String l_studentID=studentExamSchedule.getStudentID();
        String l_exam=studentExamSchedule.getExam();
        String currentEvent="E"+","+l_studentID+","+l_exam+","+p_subjectID+"//";
         
        dbg("currentEvent"+currentEvent); 
        return  currentEvent;
     }catch (Exception ex) {
       dbg(ex);
       throw new BSProcessingException("Exception" + ex.toString());
     }

 }
 
}
