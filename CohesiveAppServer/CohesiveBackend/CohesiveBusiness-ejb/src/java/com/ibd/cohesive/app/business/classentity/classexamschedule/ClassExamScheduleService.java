/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.classexamschedule;

import com.ibd.businessViews.IClassExamScheduleService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.exception.ExceptionHandler;
import com.ibd.cohesive.app.business.util.message.request.Parsing;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
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
import java.util.Calendar;
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
//@Local(IClassExamScheduleService.class)
@Remote(IClassExamScheduleService.class)
@Stateless
public class ClassExamScheduleService implements IClassExamScheduleService {
    
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public ClassExamScheduleService(){
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
       dbg("inside ClassExamScheduleService--->processing");
       dbg("ClassExamScheduleService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       try{
           dbg("before request logging");
           bs.requestlogging(request,clonedJson, inject,session,dbSession);
           dbg("after request logging");
       }catch(Exception ex){
           
           dbg("exception in request logging"+ex.toString());
       }
       buildBOfromReq(clonedJson);
       RequestBody<ClassExamSchedule> reqBody = request.getReqBody();
       ClassExamSchedule classExamSchedule =reqBody.get();
       String l_standard=classExamSchedule.getStandard();
       String l_section=classExamSchedule.getSection();
       String l_exam=classExamSchedule.getExam();
       l_lockKey=l_standard+","+l_section+","+l_exam;
       dbg("l_lockKey"+l_lockKey);
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<IClassExamScheduleService>classExamScheduleEJB=new BusinessEJB();
       classExamScheduleEJB.set(this);
      
       exAudit=bs.getExistingAudit(classExamScheduleEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,classExamScheduleEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,classExamScheduleEJB);
              tc.commit(session,dbSession);
//              dbg("commit is called in student profile");
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
    
   private  void buildBOfromReq(JsonObject p_request)throws BSProcessingException,DBProcessingException,BSValidationException{
      ClassExamSchedule examSchedule=new ClassExamSchedule();
      RequestBody<ClassExamSchedule> reqBody = new RequestBody<ClassExamSchedule>(); 
      BusinessService bs=inject.getBusinessService(session);    
      try{
      dbg("inside class exam schedule buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      String instituteID=request.getReqHeader().getInstituteID();
       BSValidation bsv=inject.getBsv(session);
//      examSchedule.setStandard(l_body.getString("standard"));
//      examSchedule.setSection(l_body.getString("section"));
      String l_class=l_body.getString("class");
      bsv.classValidation(l_class,session);
      examSchedule.setStandard(l_class.split("/")[0]);
      examSchedule.setSection(l_class.split("/")[1]);
      examSchedule.setExam(l_body.getString("exam"));
      
      if(!l_operation.equals("View")){
        JsonArray l_schedule=l_body.getJsonArray("Subjectschedules");
          examSchedule.schedule=new Schedule[l_schedule.size()];
          for(int i=0;i<l_schedule.size();i++){
               examSchedule.schedule[i]=new Schedule();
              JsonObject l_scheduleObject=l_schedule.getJsonObject(i);
              examSchedule.schedule[i].setSubjectID(l_scheduleObject.getString("subjectID"));
              String subjectName=bs.getSubjectName(l_scheduleObject.getString("subjectID"),instituteID, session, dbSession, inject);
              examSchedule.schedule[i].setSubjectName(subjectName);
                 
              examSchedule.schedule[i].setDate(l_scheduleObject.getString("date"));
              examSchedule.schedule[i].setHall(l_scheduleObject.getString("hall"));
              JsonObject l_startTimeObject=l_scheduleObject.getJsonObject("startTime");
              String l_startTimeHour=l_startTimeObject.getString("hour");
              String l_startTimeMin=l_startTimeObject.getString("min");
              examSchedule.schedule[i].setStartTimeHour(l_startTimeHour);
              examSchedule.schedule[i].setStartTimeMin(l_startTimeMin);
              JsonObject l_endTimeObject=l_scheduleObject.getJsonObject("endTime");
              String l_endTimeHour=l_endTimeObject.getString("hour");
              String l_endTimeMin=l_endTimeObject.getString("min");
              examSchedule.schedule[i].setEndTimeHour(l_endTimeHour);
              examSchedule.schedule[i].setEndTimeMin(l_endTimeMin);
              
          }
   
        
      }
           reqBody.set(examSchedule);
        request.setReqBody(reqBody);
     dbg("End of build bo from request");
    }catch(BSValidationException ex){
         throw ex; 
     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
    
  public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
        try{
        dbg("Inside ClassExamScheduleService--->create"); 
        RequestBody<ClassExamSchedule> reqBody = request.getReqBody();
        ClassExamSchedule examSchedule =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID(); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
       
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_standard=examSchedule.getStandard();
        String l_section=examSchedule.getSection();  
        String l_exam=examSchedule.getExam();
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS",69,l_standard,l_section,l_exam,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
       
        for(int i=0;i<examSchedule.schedule.length;i++){
             String l_subjectID=examSchedule.schedule[i].getSubjectID();
             String l_date=examSchedule.schedule[i].getDate();
             String l_hall=examSchedule.schedule[i].getHall();
             String l_startTimeHour=examSchedule.schedule[i].getStartTimeHour();
             String l_startTimeMin=examSchedule.schedule[i].getStartTimeMin();
             String l_endTimeHour=examSchedule.schedule[i].getEndTimeHour();
             String l_endTimeMin=examSchedule.schedule[i].getEndTimeMin();
             dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS",70,l_standard,l_section,l_exam,l_subjectID,l_date,l_hall,l_versionNumber,l_startTimeHour,l_startTimeMin,l_endTimeHour,l_endTimeMin);
  
         }

        dbg("End of ClassExamScheduleService--->create");
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
            dbg("Inside ClassExamScheduleService--->update"); 
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            String l_instituteID=request.getReqHeader().getInstituteID();
            RequestBody<ClassExamSchedule> reqBody = request.getReqBody();
            ClassExamSchedule examSchedule =reqBody.get();
            String l_standard=examSchedule.getStandard();
            String l_section=examSchedule.getSection();  
            String l_exam=examSchedule.getExam();
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
            Map<String,String>  l_column_to_update=new HashMap();
            l_column_to_update.put("5", l_checkerID);
            l_column_to_update.put("7", l_checkerDateStamp);
            l_column_to_update.put("9", l_authStatus);
            l_column_to_update.put("12", l_checkerRemarks);
             
             String[] l_primaryKey={l_standard,l_section,l_exam};
             
              dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS","CLASS_EXAM_SCHEDULE_MASTER",l_primaryKey,l_column_to_update,session);

             dbg("End of ClassExamScheduleService--->update");          
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
        dbg("Inside ClassExamScheduleService--->fullUpdate");
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassExamSchedule> reqBody = request.getReqBody();
        ClassExamSchedule examSchedule =reqBody.get();
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
        
        String l_standard=examSchedule.getStandard();
        String l_section=examSchedule.getSection();  
        String l_exam=examSchedule.getExam();
        
        l_column_to_update= new HashMap();
        l_column_to_update.put("1", l_standard);
        l_column_to_update.put("2", l_section);
        l_column_to_update.put("3", l_exam);
        l_column_to_update.put("4", l_makerID);
        l_column_to_update.put("5", l_checkerID);
        l_column_to_update.put("6", l_makerDateStamp);
        l_column_to_update.put("7", l_checkerDateStamp);
        l_column_to_update.put("8", l_recordStatus);
        l_column_to_update.put("9", l_authStatus);
        l_column_to_update.put("10", l_versionNumber);
        l_column_to_update.put("11", l_makerRemarks);
        l_column_to_update.put("12", l_checkerRemarks);
        String[] l_primaryKey={l_standard,l_section,l_exam};
        
                       dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS","CLASS_EXAM_SCHEDULE_MASTER",l_primaryKey,l_column_to_update,session);
         
        IDBReadBufferService readBuffer = inject.getDBReadBufferService();
        Map<String,DBRecord>l_examScheduleMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS","CLASS_EXAM_SCHEDULE_DETAIL", session, dbSession);
        setOperationsOfTheRecord("CLASS_EXAM_SCHEDULE_DETAIL",l_examScheduleMap);            
                       
        for(int i=0;i<examSchedule.schedule.length;i++){
             String l_subjectID=examSchedule.schedule[i].getSubjectID();
             String l_date=examSchedule.schedule[i].getDate();
             String l_hall=examSchedule.schedule[i].getHall(); 
             String l_startTimeHour=examSchedule.schedule[i].getStartTimeHour();
             String l_startTimeMin=examSchedule.schedule[i].getStartTimeMin();
             String l_endTimeHour=examSchedule.schedule[i].getEndTimeHour();
             String l_endTimeMin=examSchedule.schedule[i].getEndTimeMin();
             
             if(examSchedule.schedule[i].getOperation().equals("U")){
             
                     l_column_to_update= new HashMap();
                     l_column_to_update.put("1", l_standard);
                     l_column_to_update.put("2", l_section);
                     l_column_to_update.put("3", l_exam);
                     l_column_to_update.put("4", l_subjectID);
                     l_column_to_update.put("5", l_date);
                     l_column_to_update.put("6", l_hall);
                     l_column_to_update.put("7", l_versionNumber);
                     l_column_to_update.put("8", l_startTimeHour);
                     l_column_to_update.put("9", l_startTimeMin);
                     l_column_to_update.put("10", l_endTimeHour);
                     l_column_to_update.put("11", l_endTimeMin);

                      String[] l_markPKey={l_standard,l_section,l_exam,l_subjectID};

                               dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS","CLASS_EXAM_SCHEDULE_DETAIL",l_markPKey,l_column_to_update,session);
  
             }else{
                 
                    dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS",70,l_standard,l_section,l_exam,l_subjectID,l_date,l_hall,l_versionNumber,l_startTimeHour,l_startTimeMin,l_endTimeHour,l_endTimeMin);
                 
             }
         }               
                       
          ArrayList<String>cpList=getRecordsToDelete("CLASS_EXAM_SCHEDULE_DETAIL",l_examScheduleMap);
         
          for(int i=0;i<cpList.size();i++){
            String pkey=cpList.get(i);
            deleteRecordsInTheList("CLASS_EXAM_SCHEDULE_DETAIL",pkey);
          }

        dbg("end of ClassExamScheduleService--->fullUpdate");                
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
    
     private void setOperationsOfTheRecord(String tableName,Map<String,DBRecord>tableMap)throws DBValidationException,DBProcessingException,BSProcessingException{
         
         try{
            dbg("inside getOperationsOfTheRecord"); 
            RequestBody<ClassExamSchedule> reqBody = request.getReqBody();
            ClassExamSchedule examSchedule =reqBody.get();
            String l_standard=examSchedule.getStandard();
            String l_section=examSchedule.getSection();  
            String l_exam=examSchedule.getExam();
            dbg("tableName"+tableName);
            
            switch(tableName){
                
                case "CLASS_EXAM_SCHEDULE_DETAIL":  
                
                         for(int i=0;i<examSchedule.schedule.length;i++){
                                String l_subjectID=examSchedule.schedule[i].getSubjectID();
                                String l_pkey=l_standard+"~"+l_section+"~"+l_exam+"~"+l_subjectID;
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
           RequestBody<ClassExamSchedule> reqBody = request.getReqBody();
           ClassExamSchedule examSchedule =reqBody.get();
           String l_standard=examSchedule.getStandard();
           String l_section=examSchedule.getSection();  
           String l_exam=examSchedule.getExam();
           ArrayList<String>recordsToDelete=new ArrayList();
//           Iterator<String>keyIterator=tableMap.keySet().iterator();

          List<DBRecord>filteredRecords=tableMap.values().stream().filter(rec->rec.getRecord().get(0).trim().equals(l_standard)&&rec.getRecord().get(1).trim().equals(l_section)&&rec.getRecord().get(2).trim().equals(l_exam)).collect(Collectors.toList());
          
           dbg("tableName"+tableName);
           switch(tableName){
           
                 case "CLASS_EXAM_SCHEDULE_DETAIL":   
                   
                   for(int j=0;j<filteredRecords.size();j++){
                        String subjectID=filteredRecords.get(j).getRecord().get(3).trim();
                        String tablePkey=l_standard+"~"+l_section+"~"+l_exam+"~"+subjectID;
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                       for(int i=0;i<examSchedule.schedule.length;i++){
                           String l_subjectID=examSchedule.schedule[i].getSubjectID(); 
                           String l_requestPkey=l_standard+"~"+l_section+"~"+l_exam+"~"+l_subjectID;
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
             RequestBody<ClassExamSchedule> reqBody = request.getReqBody();
             ClassExamSchedule examSchedule =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_standard=examSchedule.getStandard();
             String l_section=examSchedule.getSection();  
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IDBTransactionService dbts=inject.getDBTransactionService();
             String[] pkArr=pkey.split("~");
             
             
             dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS",tableName, pkArr, session);
             
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
        dbg("Inside ClassExamScheduleService--->delete");  
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassExamSchedule> reqBody = request.getReqBody();
        ClassExamSchedule examSchedule =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();    
         String l_standard=examSchedule.getStandard();
        String l_section=examSchedule.getSection();  
        String l_exam=examSchedule.getExam();
        
        String[] l_primaryKey={l_standard,l_section,l_exam};
        
        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS","CLASS_EXAM_SCHEDULE_MASTER",l_primaryKey,session);
            
        for(int i=0;i<examSchedule.schedule.length;i++){
             
             String l_subjectID=examSchedule.schedule[i].getSubjectID();
             String[] l_schedulePKey={l_standard,l_section,l_exam,l_subjectID};
                        
                       dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS","CLASS_EXAM_SCHEDULE_DETAIL",l_schedulePKey,session);
        }
 
         dbg("End of ClassExamScheduleService--->delete");      
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
        dbg("inside student profile--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassExamSchedule> reqBody = request.getReqBody();
        ClassExamSchedule examSchedule =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();   
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_standard=examSchedule.getStandard();
        String l_section=examSchedule.getSection();  
        String l_exam=examSchedule.getExam();
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
        buildBOfromDB(l_examScheduleRecord,l_scheduleMap);     
        
          dbg("end of  completed student profile--->view");                
        }catch(BSValidationException ex){
            throw ex;
        }
        catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
    
     private void buildBOfromDB(DBRecord p_studentExamScheduleRecord,Map<String,DBRecord>p_detailMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           String instituteID=request.getReqHeader().getInstituteID();
           RequestBody<ClassExamSchedule> reqBody = request.getReqBody();
           BusinessService bs=inject.getBusinessService(session);
           ClassExamSchedule examSchedule =reqBody.get();
           String l_exam=examSchedule.getExam();
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
           
           int versionIndex=bs.getVersionIndexOfTheTable("CLASS_EXAM_SCHEDULE_DETAIL", "CLASS", session, dbSession, inject);
           
           
            Map<String,List<DBRecord>> examWiseGroup=p_detailMap.values().stream().filter(rec->rec.getRecord().get(versionIndex).equals(request.getReqAudit().getVersionNumber())).collect(Collectors.groupingBy(rec->rec.getRecord().get(2).trim()));
            examSchedule.schedule=new Schedule[examWiseGroup.get(l_exam).size()];
            int i=0;
                for(DBRecord l_scheduleRecords: examWiseGroup.get(l_exam)){
                   examSchedule.schedule[i]=new Schedule();
                   examSchedule.schedule[i].setSubjectID(l_scheduleRecords.getRecord().get(3).trim());
                   String subjectName=bs.getSubjectName(l_scheduleRecords.getRecord().get(3).trim(), instituteID, session, dbSession, inject);
                   examSchedule.schedule[i].setSubjectName(subjectName);
                   examSchedule.schedule[i].setDate(l_scheduleRecords.getRecord().get(4).trim());
                   examSchedule.schedule[i].setHall(l_scheduleRecords.getRecord().get(5).trim());
                   examSchedule.schedule[i].setStartTimeHour(l_scheduleRecords.getRecord().get(7).trim());
                   examSchedule.schedule[i].setStartTimeMin(l_scheduleRecords.getRecord().get(8).trim());
                   examSchedule.schedule[i].setEndTimeHour(l_scheduleRecords.getRecord().get(9).trim());
                   examSchedule.schedule[i].setEndTimeMin(l_scheduleRecords.getRecord().get(10).trim());
                   i++;
                }
           
           
            
          dbg("end of  buildBOfromDB"); 
        
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
         
         return;
         
     }
    
     public JsonObject buildJsonResFromBO()throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
         JsonObject body=null;
         try{
            dbg("inside buildJsonResFromBO");
            RequestBody<ClassExamSchedule> reqBody = request.getReqBody();
            ClassExamSchedule examSchedule =reqBody.get();
            
            JsonArrayBuilder schedule=Json.createArrayBuilder();
            for(int i=0;i<examSchedule.schedule.length;i++){
              schedule.add(Json.createObjectBuilder().add("subjectID", examSchedule.schedule[i].getSubjectID())
                                                     .add("subjectName", examSchedule.schedule[i].getSubjectName())
                                                     .add("date", examSchedule.schedule[i].getDate())
                                                     .add("startTime", Json.createObjectBuilder().add("hour", examSchedule.schedule[i].getStartTimeHour()).add("min", examSchedule.schedule[i].getStartTimeMin()))
                                                     .add("endTime", Json.createObjectBuilder().add("hour", examSchedule.schedule[i].getEndTimeHour()).add("min", examSchedule.schedule[i].getEndTimeMin()))
                                                     .add("hall", examSchedule.schedule[i].getHall()));
            }
        

//            body=Json.createObjectBuilder().add("standard",  examSchedule.getStandard())
//                                           .add("section", examSchedule.getSection())
//                                           .add("exam", examSchedule.getExam())
//                                           .add("Subjectschedules", schedule)
//                                           .build();
              body=Json.createObjectBuilder().add("class",   examSchedule.getStandard()+"/"+examSchedule.getSection())
                                             .add("exam", examSchedule.getExam())
                                             .add("Subjectschedules", schedule)
                                             .build();
             
            dbg("end of buildJsonResFromBO");
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
         
         return body;
         
     }
    
     
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
       try{
       dbg("inside ClassExamScheduleService--->businessValidation");    
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

       dbg("ClassExamScheduleService--->businessValidation--->O/P--->status"+status);
       dbg("End of ClassExamScheduleService--->businessValidation");
       }catch(BSProcessingException ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
            
       }catch(BSValidationException ex){
           throw ex;
           
       }catch(DBValidationException ex){
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
        dbg("inside ClassExamScheduleService--->masterMandatoryValidation");
        RequestBody<ClassExamSchedule> reqBody = request.getReqBody();
        ClassExamSchedule examSchedule =reqBody.get();
         
//         if(examSchedule.getStandard()==null){   
//             
//            status=false;
//            errhandler.log_app_error("BS_VAL_002","Class");
//         }
         
         if(examSchedule.getExam()==null||examSchedule.getExam().isEmpty()){   
             
            status=false;
            errhandler.log_app_error("BS_VAL_002","Exam");
         }
         
//         if(examSchedule.getSection()==null){   
//             
//            status=false;
//            errhandler.log_app_error("BS_VAL_002","Section");
//         }
         
         
         dbg("ClassExamScheduleService--->masterMandatoryValidation--->O/P--->status"+status);
         dbg("End of ClassExamScheduleService--->masterMandatoryValidation");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }   
    
     private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
     try{
        dbg("inside ClassExamScheduleService--->masterDataValidation");   
        BSValidation bsv=inject.getBsv(session);
        RequestBody<ClassExamSchedule> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        ClassExamSchedule examSchedule =reqBody.get();
        String l_standard=examSchedule.getStandard();
        String l_section=examSchedule.getSection();
        String l_exam= examSchedule.getExam();
             
        if(!bsv.standardSectionValidation(l_standard,l_section, l_instituteID, session, dbSession, inject)){
            status=false;
            errhandler.log_app_error("BS_VAL_003","Class");
        }
        if(!bsv.examValidation(l_exam, l_instituteID, session, dbSession, inject)){
            status=false;
            errhandler.log_app_error("BS_VAL_003","Exam");
        }
             

        dbg("ClassExamScheduleServiceService--->masterDataValidation--->O/P--->status"+status);
        dbg("End of ClassExamScheduleServiceService--->masterDataValidation");  
       }catch(DBValidationException ex){
            throw ex;
       }catch(DBProcessingException ex){
            throw new DBProcessingException("DBProcessingException"+ex.toString());
       }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        
        return status;
              
    }
     
     private boolean detailMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
        boolean status=true;
         try{
             dbg("inside ClassExamScheduleService--->detailMandatoryValidation");
            RequestBody<ClassExamSchedule> reqBody = request.getReqBody();
            ClassExamSchedule examSchedule =reqBody.get();
        
             if(examSchedule.schedule==null||examSchedule.schedule.length==0){
                 status=false;
                 errhandler.log_app_error("BS_VAL_002","Schedule");
             }else{
                 
                 for(int i=0;i<examSchedule.schedule.length;i++){
                     
                     if(examSchedule.schedule[i].getSubjectID()==null||examSchedule.schedule[i].getSubjectID().isEmpty()){   
                         status=false;
                         errhandler.log_app_error("BS_VAL_002","Subject ID");
                     }
                     
                     if(examSchedule.getSchedule()[i].getStartTimeHour()==null||examSchedule.getSchedule()[i].getStartTimeHour().isEmpty()){
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","StartTimeHour");
                    }
                    if(examSchedule.getSchedule()[i].getStartTimeMin()==null||examSchedule.getSchedule()[i].getStartTimeMin().isEmpty()){
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","StartTimeMin");
                    }
                    if(examSchedule.getSchedule()[i].getEndTimeHour()==null||examSchedule.getSchedule()[i].getEndTimeHour().isEmpty()){
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","EndTimeHour");
                    }
                    if(examSchedule.getSchedule()[i].getEndTimeMin()==null||examSchedule.getSchedule()[i].getEndTimeMin().isEmpty()){
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","EndTimeMin");
                    }
                     
                     if(examSchedule.schedule[i].getHall()==null||examSchedule.schedule[i].getHall().isEmpty()){   
                         status=false;
                         errhandler.log_app_error("BS_VAL_002","hall");
                     }
                     
                 }
                 
                 
                 
             }      
            

        
        dbg("ClassExamScheduleService--->detailMandatoryValidation--->O/P--->status"+status);
        dbg("End of ClassExamScheduleService--->detailMandatoryValidation");
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
     private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
     try{
        dbg("inside ClassExamScheduleService--->detailDataValidation");   
        BSValidation bsv=inject.getBsv(session);
        RequestBody<ClassExamSchedule> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        ClassExamSchedule examSchedule =reqBody.get();
        String l_standard=examSchedule.getStandard();
        String l_section=examSchedule.getSection();
         
        for(int i=0;i<examSchedule.schedule.length;i++){
            String l_subjectID=examSchedule.schedule[i].getSubjectID();
            String l_date=examSchedule.schedule[i].getDate();
            
            if(!bsv.subjectIDValidation(l_subjectID, l_instituteID, session, dbSession, inject)){
                status=false;
                errhandler.log_app_error("BS_VAL_003","SubjectID");
            }
            
             if(!bsv.classSubjectIDValidation(l_subjectID, l_standard, l_section, l_instituteID, session, dbSession, inject)){
                 
                 status=false;
                 errhandler.log_app_error("BS_VAL_056",l_subjectID);
                 
             }
            
            if(!bsv.standardSectionValidation(examSchedule.schedule[i].getHall().split("/")[0],examSchedule.schedule[i].getHall().split("/")[1], l_instituteID, session, dbSession, inject)){
              status=false;
            errhandler.log_app_error("BS_VAL_003","Hall");
        }
            if(!bsv.dateFormatValidation(l_date, session, dbSession, inject)){
                status=false;
                errhandler.log_app_error("BS_VAL_003","Date");
            }
            
            
            
        }
 ArrayList<String> subList=new ArrayList();
             for(int i=0;i<examSchedule.getSchedule().length;i++){
                 String subjectID=examSchedule.getSchedule()[i].getSubjectID();
                 
                 if(!subList.contains(subjectID)){

                     subList.add(subjectID);

                }else{
                    status=false;
                    errhandler.log_app_error("BS_VAL_031","Subject " +examSchedule.getSchedule()[i].getSubjectName());
                    throw new BSValidationException("BSValidationException");

                }
             }
            ArrayList<String> HallList=new ArrayList();
             for(int i=0;i<examSchedule.getSchedule().length;i++){
                 String hallID=examSchedule.getSchedule()[i].getHall()+examSchedule.getSchedule()[i].getDate()+examSchedule.getSchedule()[i].getStartTimeHour()+examSchedule.getSchedule()[i].getStartTimeMin()+examSchedule.getSchedule()[i].endTimeHour+examSchedule.getSchedule()[i].endTimeMin;
                 
                 if(!HallList.contains(hallID)){

                     HallList.add(hallID);

                }else{
                    status=false;
                    errhandler.log_app_error("BS_VAL_031","Hall " +hallID);
                    throw new BSValidationException("BSValidationException");

                }
             }
   
             for(int i=0;i<examSchedule.getSchedule().length;i++){
                
                 if(!bsv.hourValidation(examSchedule.getSchedule()[i].getStartTimeHour(),l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","startTimeHour");
                    throw new BSValidationException("BSValidationException");
                }
                 
                if(!bsv.minValidation(examSchedule.getSchedule()[i].getStartTimeMin(),l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","startTimeMin");
                    throw new BSValidationException("BSValidationException");
                }
                if(!bsv.hourValidation(examSchedule.getSchedule()[i].getEndTimeHour(),l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","endTimeHour");
                    throw new BSValidationException("BSValidationException");
                }
                if(!bsv.minValidation(examSchedule.getSchedule()[i].getEndTimeMin(),l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","endTimeMin");
                    throw new BSValidationException("BSValidationException");
                }

              
              
              
               Calendar start = Calendar.getInstance();
          
                start.set(Calendar.HOUR_OF_DAY, Integer.parseInt(examSchedule.getSchedule()[i].getStartTimeHour()));
                start.set(Calendar.MINUTE,Integer.parseInt(examSchedule.getSchedule()[i].getStartTimeMin()));
                start.set(Calendar.SECOND, 0);
                start.set(Calendar.MILLISECOND, 0);

                Calendar end = Calendar.getInstance();

                end.set(Calendar.HOUR_OF_DAY, Integer.parseInt(examSchedule.getSchedule()[i].getEndTimeHour()));
                end.set(Calendar.MINUTE,Integer.parseInt(examSchedule.getSchedule()[i].getEndTimeMin()));
                end.set(Calendar.SECOND, 0);
                end.set(Calendar.MILLISECOND, 0);
                
                if(start.after(end)){
                    
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","start and end time for"+examSchedule.getSchedule()[i].getSubjectName());
                    throw new BSValidationException("BSValidationException");
                }
                
                if(start.equals(end)){
                    
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","start and end time" +examSchedule.getSchedule()[i].getSubjectName());
                    throw new BSValidationException("BSValidationException");
                }
                
               /*
                int startTimeMinutes=Integer.parseInt((examSchedule.getSchedule()[i].getStartTimeHour()))*60+Integer.parseInt(examSchedule.getSchedule()[i].getStartTimeMin());
                int endTimeMinutes=Integer.parseInt((examSchedule.getSchedule()[i].getEndTimeHour()))*60+Integer.parseInt(examSchedule.getSchedule()[i].getEndTimeMin());
                
                dbg("startTimeMinutes"+startTimeMinutes);
                dbg("endTimeMinutes"+endTimeMinutes);
                ArrayList<Integer>timeList=new ArrayList();
                for (int j = startTimeMinutes ; j <=endTimeMinutes; j++){
                   
                       if(!timeList.contains(j)){

                           timeList.add(j);

                       }else{

                           status=false;
                           errhandler.log_app_error("BS_VAL_003","start and end time");
                           throw new BSValidationException("BSValidationException");
                       }
                }
                */
          } 
                  
             
             
             
             
               
                           for(int i=0;i<examSchedule.schedule.length;i++){
                               int currentStartTimeinmin=Integer.parseInt(examSchedule.schedule[i].getStartTimeHour())*60+Integer.parseInt(examSchedule.schedule[i].getStartTimeMin());
                               int currentEndTimeinmin=Integer.parseInt(examSchedule.schedule[i].getEndTimeHour())*60+Integer.parseInt(examSchedule.schedule[i].getEndTimeMin());
                            dbg("currentStartTimeinmin-->"+currentStartTimeinmin);
                            dbg("currentEndTimeinmin-->"+currentEndTimeinmin);
                            
                               for(int k=0;k<examSchedule.schedule.length;k++){
                                 if(k!=i)
                                 {    
                               if(examSchedule.schedule[i].getDate().equals(examSchedule.schedule[k].getDate()))
                                {        
                               int existingStartTimeinmin=Integer.parseInt(examSchedule.schedule[k].getStartTimeHour())*60+Integer.parseInt(examSchedule.schedule[k].getStartTimeMin());
                               int existingEndTimeinmin=Integer.parseInt(examSchedule.schedule[k].getEndTimeHour())*60+Integer.parseInt(examSchedule.schedule[k].getEndTimeMin());
                             dbg("existingStartTimeinmin-->"+existingStartTimeinmin);
                            dbg("existingStartTimeinmin-->"+existingEndTimeinmin);
                              
                            if(currentEndTimeinmin>existingStartTimeinmin &&currentStartTimeinmin<existingStartTimeinmin)
                             {   
                              dbg("Cond1 satisfied");
                                 status=false;
                              errhandler.log_app_error("BS_VAL_054",""+examSchedule.schedule[i].getSubjectName()+"and"+examSchedule.schedule[k].getSubjectName());
                                throw new BSValidationException("BSValidationException");
                              }
                            if(currentStartTimeinmin<existingEndTimeinmin && currentEndTimeinmin>=existingEndTimeinmin)
                             {   
                                  dbg("Cond2 satisfied");
                               status=false;
                                errhandler.log_app_error("BS_VAL_054",""+examSchedule.schedule[i].getSubjectName()+"and"+examSchedule.schedule[k].getSubjectName());
                             throw new BSValidationException("BSValidationException");
                              }
                            
                            if(currentStartTimeinmin<=existingStartTimeinmin && currentEndTimeinmin>=existingEndTimeinmin)
                             {   
                               status=false;
                                errhandler.log_app_error("BS_VAL_054",""+examSchedule.schedule[i].getSubjectName()+"and"+examSchedule.schedule[k].getSubjectName());
                              throw new BSValidationException("BSValidationException");
                              }
                            
                            if(currentStartTimeinmin>=existingStartTimeinmin && currentEndTimeinmin<=existingEndTimeinmin)
                             {   
                               status=false;
                                errhandler.log_app_error("BS_VAL_054",""+examSchedule.schedule[i].getSubjectName()+"and"+examSchedule.schedule[k].getSubjectName());
                              throw new BSValidationException("BSValidationException");
                              }
                            
                            
                            
                            
                            }   
                                 }
                               }
                        }  
         IBDProperties i_db_properties=session.getCohesiveproperties();   
         //DBRecord l_examScheduleRecord;
        Map<String,DBRecord>l_scheduleMap=null;
        
       IDBReadBufferService readBuffer= inject.getDBReadBufferService();
       BusinessService bs=inject.getBusinessService(session);
      ArrayList<String> classList= bs.getClassesOfTheInstitute(l_instituteID,session,dbSession,inject);
       
      for(int classidx=0;classidx<classList.size();classidx++)
      {
       try
        {   
       //  l_examScheduleRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+examSchedule.getStandard()+examSchedule.getSection()+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS","CLASS_EXAM_SCHEDULE_MASTER", l_pkey, session, dbSession);
         l_scheduleMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+classList.get(classidx).split("~")[0]+classList.get(classidx).split("~")[1]+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS","CLASS_EXAM_SCHEDULE_DETAIL", session, dbSession);
        }
         catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                       // session.getErrorhandler().log_app_error("BS_VAL_013",l_standard+l_section+l_exam);
                        //throw new BSValidationException("BSValidationException");
                        l_scheduleMap=null;
                    }else{
                        
                        throw ex;
                    }
            } 
       
        if(l_scheduleMap!=null && !l_scheduleMap.isEmpty() )
        {   
                     
                   for(int i=0;i<examSchedule.schedule.length;i++){
                       // ClassExamSchedule lexamSchedule=examSchedule;
                       String l_date= examSchedule.schedule[i].getDate();
                        Map<String,List<DBRecord>> hallwiseGroup=l_scheduleMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals(l_date)&&!((rec.getRecord().get(0).trim()+rec.getRecord().get(1).trim()).equals(examSchedule.getStandard()+examSchedule.getSection()))).collect(Collectors.groupingBy(rec->rec.getRecord().get(5).trim()));
              
                            int currentStartTimeinmin=Integer.parseInt(examSchedule.schedule[i].getStartTimeHour())*60+Integer.parseInt(examSchedule.schedule[i].getStartTimeMin());
                               int currentEndTimeinmin=Integer.parseInt(examSchedule.schedule[i].getEndTimeHour())*60+Integer.parseInt(examSchedule.schedule[i].getEndTimeMin());
                            dbg("currentStartTimeinmin-->"+currentStartTimeinmin);
                            dbg("currentEndTimeinmin-->"+currentEndTimeinmin);
                            Iterator<String> hallIterator=hallwiseGroup.keySet().iterator();
                           // int k=0;
                            while(hallIterator.hasNext()){
                                String existinHall=hallIterator.next();
                                   dbg("Existing Hall-->"+existinHall);
                            dbg("currentHall-->"+examSchedule.schedule[i].getHall());
                              // for(int k=0;k<examSchedule.schedule.length;k++){
                              if(examSchedule.schedule[i].getHall().equals(existinHall))
                                {
                                 for(int k=0;k<hallwiseGroup.get(existinHall).size();k++){
                   
                               int existingStartTimeinmin=Integer.parseInt(hallwiseGroup.get(existinHall).get(k).getRecord().get(7))*60+Integer.parseInt(hallwiseGroup.get(existinHall).get(k).getRecord().get(8));
                               int existingEndTimeinmin=Integer.parseInt(hallwiseGroup.get(existinHall).get(k).getRecord().get(9))*60+Integer.parseInt(hallwiseGroup.get(existinHall).get(k).getRecord().get(10));
                             dbg("existingStartTimeinmin-->"+existingStartTimeinmin);
                            dbg("existingStartTimeinmin-->"+existingEndTimeinmin);
                           
                            if(currentEndTimeinmin>existingStartTimeinmin &&currentStartTimeinmin<existingStartTimeinmin)
                             {   
                              dbg("Cond1 satisfied");
                                 status=false;
                              errhandler.log_app_error("BS_VAL_050",examSchedule.getStandard()+examSchedule.getSection()+" "+examSchedule.getExam()+" "+examSchedule.schedule[i].getDate()+" "+examSchedule.schedule[i].getHall()+"and"+hallwiseGroup.get(existinHall).get(k).getRecord().get(0)+" "+hallwiseGroup.get(existinHall).get(k).getRecord().get(1)+" "+hallwiseGroup.get(existinHall).get(k).getRecord().get(2)+" "+hallwiseGroup.get(existinHall).get(k).getRecord().get(4)+hallwiseGroup.get(existinHall).get(k).getRecord().get(5));
                                throw new BSValidationException("BSValidationException");
                              }
                            if(currentStartTimeinmin<existingEndTimeinmin && currentEndTimeinmin>=existingEndTimeinmin)
                             {   
                                  dbg("Cond2 satisfied");
                               status=false;
                              errhandler.log_app_error("BS_VAL_050",examSchedule.getStandard()+examSchedule.getSection()+" "+examSchedule.getExam()+" "+examSchedule.schedule[i].getDate()+" "+examSchedule.schedule[i].getHall()+"and"+hallwiseGroup.get(existinHall).get(k).getRecord().get(0)+" "+hallwiseGroup.get(existinHall).get(k).getRecord().get(1)+" "+hallwiseGroup.get(existinHall).get(k).getRecord().get(2)+" "+hallwiseGroup.get(existinHall).get(k).getRecord().get(4)+hallwiseGroup.get(existinHall).get(k).getRecord().get(5));
                             throw new BSValidationException("BSValidationException");
                              }
                            
                            if(currentStartTimeinmin<=existingStartTimeinmin && currentEndTimeinmin>=existingEndTimeinmin)
                             {   dbg("Cond3 satisfied");
                               status=false;
                              errhandler.log_app_error("BS_VAL_050",examSchedule.getStandard()+examSchedule.getSection()+" "+examSchedule.getExam()+" "+examSchedule.schedule[i].getDate()+" "+examSchedule.schedule[i].getHall()+"and"+hallwiseGroup.get(existinHall).get(k).getRecord().get(0)+" "+hallwiseGroup.get(existinHall).get(k).getRecord().get(1)+" "+hallwiseGroup.get(existinHall).get(k).getRecord().get(2)+" "+hallwiseGroup.get(existinHall).get(k).getRecord().get(4)+hallwiseGroup.get(existinHall).get(k).getRecord().get(5));
                              throw new BSValidationException("BSValidationException");
                              }
                            
                            if(currentStartTimeinmin>=existingStartTimeinmin && currentEndTimeinmin<=existingEndTimeinmin)
                             {   dbg("Cond4 satisfied");
                               status=false;
                              errhandler.log_app_error("BS_VAL_050",examSchedule.getStandard()+examSchedule.getSection()+" "+examSchedule.getExam()+" "+examSchedule.schedule[i].getDate()+" "+examSchedule.schedule[i].getHall()+"and"+hallwiseGroup.get(existinHall).get(k).getRecord().get(0)+" "+hallwiseGroup.get(existinHall).get(k).getRecord().get(1)+" "+hallwiseGroup.get(existinHall).get(k).getRecord().get(2)+" "+hallwiseGroup.get(existinHall).get(k).getRecord().get(4)+hallwiseGroup.get(existinHall).get(k).getRecord().get(5));
                              throw new BSValidationException("BSValidationException");
                              }
                            
                            
                            
                            }   
                            
                               }
                        } 
                   } 
        }
      }
        dbg("ClassExamScheduleServiceService--->detailDataValidation--->O/P--->status"+status);
        dbg("End of ClassExamScheduleServiceService--->detailDataValidation");  
       }catch(BSValidationException ex){
            throw ex;
       }
       catch(DBValidationException ex){
            throw ex;
       }catch(DBProcessingException ex){
            throw new DBProcessingException("DBProcessingException"+ex.toString());
       }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        
        return status;
              
    }
    
    public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     ExistingAudit exAudit=new ExistingAudit();
     try{
        dbg("inside ClassExamSchedule--->getExistingAudit") ;
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_operation=request.getReqHeader().getOperation();
        String l_service=request.getReqHeader().getService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
          if(!l_operation.equals("Create")&&!l_operation.equals("View")&&!l_operation.equals("Create-Default")){
             if(l_operation.equals("AutoAuth")&&l_versionNumber.equals("1")){
                return null;
              }else{
               RequestBody<ClassExamSchedule> reqBody = request.getReqBody();
               ClassExamSchedule examSchedule =reqBody.get();
               String l_standard=examSchedule.getStandard();
               String l_section=examSchedule.getSection();  
               String l_exam=examSchedule.getExam();
               String[] l_pkey={l_standard,l_section,l_exam};
               DBRecord l_examScheduleRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS","CLASS_EXAM_SCHEDULE_MASTER", l_pkey, session, dbSession);
               exAudit.setAuthStatus(l_examScheduleRecord.getRecord().get(8).trim());
               exAudit.setMakerID(l_examScheduleRecord.getRecord().get(3).trim());
               exAudit.setRecordStatus(l_examScheduleRecord.getRecord().get(7).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_examScheduleRecord.getRecord().get(9).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of ClassExamSchedule--->getExistingAudit") ;
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
    
    
    
    
    
    
}
