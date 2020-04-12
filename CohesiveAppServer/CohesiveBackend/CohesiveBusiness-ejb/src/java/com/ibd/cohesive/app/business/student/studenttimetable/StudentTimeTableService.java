/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studenttimetable;

import com.ibd.businessViews.IStudentTimeTableService;
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
import java.util.Arrays;
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
//@Local(IStudentTimeTableService.class)
@Remote(IStudentTimeTableService.class)
@Stateless
public class StudentTimeTableService implements IStudentTimeTableService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentTimeTableService(){
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
       dbg("inside StudentTimeTableService--->processing");
       dbg("StudentTimeTableService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<StudentTimeTable> reqBody = request.getReqBody();
       StudentTimeTable studentTimeTable =reqBody.get();
       l_lockKey=studentTimeTable.getStudentID();
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<IStudentTimeTableService>studentTimeTableEJB=new BusinessEJB();
       studentTimeTableEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentTimeTableEJB);
       
       if(request.getReqHeader().getOperation().equals("View")){
           
         String studentID=  bs.studentValidation(studentTimeTable.getStudentID(), studentTimeTable.getStudentName(), request.getReqHeader().getInstituteID(), session, dbSession, inject);
         
          
         if(studentID==null||studentID.isEmpty()){
             
             errhandler.log_app_error("BS_VAL_002","Student ID or Name");  
             throw new BSValidationException("BSValidationException");
         }
         
         studentTimeTable.setStudentID(studentID);
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
      
       bs.businessServiceProcssing(request, exAudit, inject,studentTimeTableEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentTimeTableEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student timeTable");
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
                dbg(jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
//                if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
          clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes  
/*BSProcessingException ex=new BSProcessingException("response contains special characters");
                   dbg(ex);
                   session.clearSessionObject();
                   dbSession.clearSessionObject();
                   throw ex;
                }*/
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
      StudentTimeTable studentTimeTable=new StudentTimeTable();
      RequestBody<StudentTimeTable> reqBody = new RequestBody<StudentTimeTable>(); 
           
      try{
      dbg("inside student timeTable buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      studentTimeTable.setStudentID(l_body.getString("studentID"));
      studentTimeTable.setStudentName(l_body.getString("studentName"));
      
      dbg("l_body"+l_body.toString());
      if(!l_operation.equals("View")){
          JsonArray l_timeTable=l_body.getJsonArray("timeTable");
           dbg("l_timeTable"+l_timeTable.toString());
          dbg("l_timeTable size"+l_timeTable.size());
          studentTimeTable.timeTable=new TimeTable[l_timeTable.size()];
          
            for(int i=0;i<l_timeTable.size();i++){
                studentTimeTable.timeTable[i]=new TimeTable();
                JsonObject l_day_object=l_timeTable.getJsonObject(i);
                studentTimeTable.timeTable[i].setDay(l_day_object.getString("day"));
                JsonArray l_period=l_day_object.getJsonArray("period");
                studentTimeTable.timeTable[i].period=new Period[l_period.size()];
                
                    for(int j=0;j<l_period.size();j++){
                        studentTimeTable.timeTable[i].period[j]=new Period();
                        JsonObject l_period_object=l_period.getJsonObject(j);
                        studentTimeTable.timeTable[i].period[j].setPeriodNumber(l_period_object.getString("periodNumber"));
                        studentTimeTable.timeTable[i].period[j].setSubjectID(l_period_object.getString("subjectID"));
                        studentTimeTable.timeTable[i].period[j].setTeacherID(l_period_object.getString("teacherID"));
                    }
            }
        }
        reqBody.set(studentTimeTable);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
    try{ 
        dbg("inside stident timeTable create"); 
        RequestBody<StudentTimeTable> reqBody = request.getReqBody();
        StudentTimeTable studentTimeTable =reqBody.get();
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
        String l_studentID=studentTimeTable.getStudentID();
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",24,l_studentID,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);

        
        for(int i=0;i<studentTimeTable.timeTable.length;i++){
            String l_day=studentTimeTable.timeTable[i].getDay();
            
                   for(int j=0;j<studentTimeTable.timeTable[i].period.length;j++){
                       String l_periodNumber=studentTimeTable.timeTable[i].period[j].getPeriodNumber();
                       String l_subjectID=studentTimeTable.timeTable[i].period[j].getSubjectID();
                       String p_teacherID=studentTimeTable.timeTable[i].period[j].getTeacherID();
                       
                       dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",49,l_studentID,l_day,l_periodNumber,l_subjectID,p_teacherID,l_versionNumber);
           
                        
                   }
                                 
               }
        
        dbg("end of student timeTable create"); 
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
        dbg("inside student timeTable--->auth update"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<StudentTimeTable> reqBody = request.getReqBody();
        StudentTimeTable studentTimeTable =reqBody.get();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_studentID=studentTimeTable.getStudentID();
        String[] l_primaryKey={l_studentID};
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("3", l_checkerID);
         l_column_to_update.put("5", l_checkerDateStamp);
         l_column_to_update.put("7", l_authStatus);
         l_column_to_update.put("10", l_checkerRemarks);
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_TIMETABLE_MASTER", l_primaryKey, l_column_to_update, session);
         dbg("end of student timeTable--->auth update");          
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
        RequestBody<StudentTimeTable> reqBody = request.getReqBody();
        StudentTimeTable studentTimeTable =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();   
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();   
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_studentID=studentTimeTable.getStudentID();
        String[] l_master_pkey={l_studentID};
        l_column_to_update= new HashMap();
        l_column_to_update.put("2", l_makerID);
        l_column_to_update.put("3", l_checkerID);
        l_column_to_update.put("4", l_makerDateStamp);
        l_column_to_update.put("5", l_checkerDateStamp);
        l_column_to_update.put("6", l_recordStatus);
        l_column_to_update.put("7", l_authStatus);
        l_column_to_update.put("9", l_makerRemarks);
        l_column_to_update.put("10", l_checkerRemarks);        
        
        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_TIMETABLE_MASTER", l_master_pkey, l_column_to_update, session);                      
        
        IDBReadBufferService readBuffer = inject.getDBReadBufferService();
        Map<String,DBRecord>l_timeTableMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_TIMETABLE_MASTER", session, dbSession);
        setOperationsOfTheRecord("SVW_STUDENT_TIMETABLE_DETAIL",l_timeTableMap);
        
        for(int i=0;i<studentTimeTable.timeTable.length;i++){
                                      
                   String l_day=studentTimeTable.timeTable[i].getDay();
                   for(int j=0;j<studentTimeTable.timeTable[i].period.length;j++){
                       
                       String l_periodNumber=studentTimeTable.timeTable[i].period[j].getPeriodNumber();
                       String l_subjectID=studentTimeTable.timeTable[i].period[j].getSubjectID();
                       String p_teacherID=studentTimeTable.timeTable[i].period[j].getTeacherID();
                       
                       if(studentTimeTable.timeTable[i].period[j].getOperation().equals("U")){
                       
                            l_column_to_update= new HashMap();
                            l_column_to_update.put("2", l_day);
                            l_column_to_update.put("3", l_periodNumber);
                            l_column_to_update.put("4", l_subjectID);
                            l_column_to_update.put("5", p_teacherID);
                            String[] l_primaryKey={l_studentID,l_day,l_periodNumber};

                            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_TIMETABLE_DETAIL", l_primaryKey, l_column_to_update, session);                      
                       
                       }else{
                           
                           dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",49,l_studentID,l_day,l_periodNumber,l_subjectID,p_teacherID,l_versionNumber);
                       }
                   }
                                 
               }
        
          ArrayList<String>cpList=getRecordsToDelete("SVW_STUDENT_TIMETABLE_DETAIL",l_timeTableMap);
         
          for(int i=0;i<cpList.size();i++){
            String pkey=cpList.get(i);
            deleteRecordsInTheList("SVW_STUDENT_TIMETABLE_DETAIL",pkey);
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
            RequestBody<StudentTimeTable> reqBody = request.getReqBody();
            StudentTimeTable timeTable =reqBody.get();
            String l_studentID=timeTable.getStudentID();
            dbg("tableName"+tableName);
            
            switch(tableName){
                
                case "SVW_STUDENT_TIMETABLE_DETAIL":  
                
                         for(int i=0;i<timeTable.timeTable.length;i++){
                             
                             String l_day=timeTable.timeTable[i].getDay();
                             
                             for(int j=0;j<timeTable.timeTable[i].period.length;j++){
                       
                                String l_periodNumber=timeTable.timeTable[i].period[j].getPeriodNumber();
                                String l_pkey=l_studentID+"~"+l_day+"~"+l_periodNumber;
                                
                                   if(tableMap.containsKey(l_pkey)){

                                        timeTable.timeTable[i].period[j].setOperation("U");
                                    }else{

                                        timeTable.timeTable[i].period[j].setOperation("C");
                                    }
                         } 
                  break;      

               }
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
           RequestBody<StudentTimeTable> reqBody = request.getReqBody();
           StudentTimeTable timeTable =reqBody.get();
           String l_studentID=timeTable.getStudentID();
           ArrayList<String>recordsToDelete=new ArrayList();
//         Iterator<String>keyIterator=tableMap.keySet().iterator();

           List<DBRecord>filteredRecords=tableMap.values().stream().filter(rec->rec.getRecord().get(0).trim().equals(l_studentID)).collect(Collectors.toList());

        
           dbg("tableName"+tableName);
           switch(tableName){
           
                 case "SVW_STUDENT_TIMETABLE_DETAIL":   
                   
                   for(int k=0;k<filteredRecords.size();k++){
                        String day=filteredRecords.get(k).getRecord().get(1).trim();
                        String periodNumber=filteredRecords.get(k).getRecord().get(2).trim();
                        String tablePkey=l_studentID+"~"+day+"~"+periodNumber;
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                       for(int i=0;i<timeTable.timeTable.length;i++){
                             
                             String l_day=timeTable.timeTable[i].getDay();
                             
                             for(int j=0;j<timeTable.timeTable[i].period.length;j++){
                       
                               String l_periodNumber=timeTable.timeTable[i].period[j].getPeriodNumber(); 
                               String l_requestPkey=l_studentID+"~"+l_day+"~"+l_periodNumber;
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
             RequestBody<StudentTimeTable> reqBody = request.getReqBody();
             StudentTimeTable timeTable =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_studentID=timeTable.getStudentID();
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
        dbg("inside student timeTable delete");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentTimeTable> reqBody = request.getReqBody();
        StudentTimeTable studentTimeTable =reqBody.get();
        String l_studentID=studentTimeTable.getStudentID();
             
             String[] l_primaryKey={l_studentID};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_TIMETABLE_MASTER", l_primaryKey, session);
          
                        
        for(int i=0;i<studentTimeTable.timeTable.length;i++){
            String l_day=studentTimeTable.timeTable[i].getDay();
                   
                   for(int j=0;j<studentTimeTable.timeTable[i].period.length;j++){
                       String l_periodNumber=studentTimeTable.timeTable[i].period[j].getPeriodNumber();
                       String[] l_detailprimaryKey={l_studentID,l_day,l_periodNumber};
                        
                       dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_TIMETABLE_DETAIL",l_detailprimaryKey,session);
                      }
                   }                
                        
            dbg("end of student timeTable delete");
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
        dbg("inside student timeTable--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentTimeTable> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        StudentTimeTable studentTimeTable =reqBody.get();
        IPDataService pds=inject.getPdataservice();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_studentID=studentTimeTable.getStudentID();
         String[] studentPKey={l_studentID};
         ArrayList<String>studentMasterlist= pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",studentPKey);
        String l_standard=studentMasterlist.get(2).trim();
        String l_section=studentMasterlist.get(3).trim();
       String[] l_pkey1={l_standard,l_section};
       Map<String,DBRecord>detailMap;
        DBRecord timeTableRec;
        try
        {   
           timeTableRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS", "CLASS_TIMETABLE_MASTER", l_pkey1, session, dbSession);
        
            detailMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS","CLASS_TIMETABLE_DETAIL", session, dbSession);
         }
         catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013",l_standard+l_section );
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
     
        buildBOfromDB(timeTableRec,detailMap);
        
          dbg("end of  completed student timeTable--->view");                
        }
        catch(BSValidationException ex){
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
      private void buildBOfromDB(DBRecord p_studentTimeTableRecord, Map<String,DBRecord>detailMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           String instituteID=request.getReqHeader().getInstituteID();
           BusinessService bs=inject.getBusinessService(session);
           RequestBody<StudentTimeTable> reqBody = request.getReqBody();
           StudentTimeTable studentTimeTable =reqBody.get();
           ArrayList<String>l_studentTimeTableList= p_studentTimeTableRecord.getRecord();
           
           if(l_studentTimeTableList!=null&&!l_studentTimeTableList.isEmpty()){
               
            request.getReqAudit().setMakerID(l_studentTimeTableList.get(2).trim());
            request.getReqAudit().setCheckerID(l_studentTimeTableList.get(3).trim());
            request.getReqAudit().setMakerDateStamp(l_studentTimeTableList.get(4).trim());
            request.getReqAudit().setCheckerDateStamp(l_studentTimeTableList.get(5).trim());
            request.getReqAudit().setRecordStatus(l_studentTimeTableList.get(6).trim());
            request.getReqAudit().setAuthStatus(l_studentTimeTableList.get(7).trim());
            request.getReqAudit().setVersionNumber(l_studentTimeTableList.get(8).trim());
            request.getReqAudit().setMakerRemarks(l_studentTimeTableList.get(9).trim());
            request.getReqAudit().setCheckerRemarks(l_studentTimeTableList.get(10).trim());
            }
           
            int versionIndex=bs.getVersionIndexOfTheTable("CLASS_TIMETABLE_DETAIL", "CLASS", session, dbSession, inject);
           
            Map<String,List<DBRecord>>dayWiseGroup=detailMap.values().stream().filter(rec->rec.getRecord().get(versionIndex).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.groupingBy(rec->rec.getRecord().get(2).trim()));
            studentTimeTable.timeTable=new TimeTable[dayWiseGroup.keySet().size()];
            Iterator<String> dayIterator=dayWiseGroup.keySet().iterator();
            int i=0;
            
            while(dayIterator.hasNext()){
                String day=dayIterator.next();
                studentTimeTable.timeTable[i]=new TimeTable();
                studentTimeTable.timeTable[i].setDay(day);
                dbg("day"+day);
                dbg("dayWiseGroup.get(day).size()"+dayWiseGroup.get(day).size());
                studentTimeTable.timeTable[i].period=new Period[dayWiseGroup.get(day).size()];
                int j=0;
                for(DBRecord periodRecords: dayWiseGroup.get(day)){
                   studentTimeTable.timeTable[i].period[j]=new Period();
                   studentTimeTable.timeTable[i].period[j].setPeriodNumber(periodRecords.getRecord().get(3).trim());
                   studentTimeTable.timeTable[i].period[j].setSubjectID(periodRecords.getRecord().get(4).trim());
                   String l_subjectName=bs.getSubjectName(studentTimeTable.timeTable[i].period[j].getSubjectID(), instituteID, session, dbSession, inject);
                   studentTimeTable.timeTable[i].period[j].setSubjectName(l_subjectName);
                   studentTimeTable.timeTable[i].period[j].setTeacherID(periodRecords.getRecord().get(5).trim());
                   String l_teacherName=bs.getTeacherName(studentTimeTable.timeTable[i].period[j].getTeacherID(), instituteID, session, dbSession, inject);
                   studentTimeTable.timeTable[i].period[j].setTeacherName(l_teacherName);
                   
                   j++;
                  dbg("periodArraySize"+j);
                }
                Arrays.sort(studentTimeTable.timeTable[i].getPeriod());
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
    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside student timeTable buildJsonResFromBO");
        IPDataService pds=inject.getPdataservice();
        RequestBody<StudentTimeTable> reqBody = request.getReqBody();
        BusinessService bs=inject.getBusinessService(session);
        String l_instituteID=request.getReqHeader().getInstituteID();
        StudentTimeTable studentTimeTable =reqBody.get();
        JsonArrayBuilder period=Json.createArrayBuilder();
        JsonArrayBuilder timeTable=Json.createArrayBuilder();
        String studentID=studentTimeTable.getStudentID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        dbg("Time table size"+studentTimeTable.timeTable.length);
        
        String[] studentPKey={studentID};
                    ArrayList<String>studentMasterlist= pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",studentPKey);
        
        
        String standard=studentMasterlist.get(2).trim();
        String section=studentMasterlist.get(3).trim();
        String studentName=studentMasterlist.get(1).trim();
           for(int i=0;i<studentTimeTable.timeTable.length;i++){
               dbg("studentTimeTable.timeTable[i].period"+studentTimeTable.timeTable[i]);
               dbg("period size"+studentTimeTable.timeTable[i].period.length);
               for(int j=0;j<studentTimeTable.timeTable[i].period.length;j++){
                   
                   String periodNumber=studentTimeTable.timeTable[i].period[j].getPeriodNumber();
                   
                    String[] pkey={l_instituteID,standard,section,periodNumber};
                    ArrayList<String>periodlist= pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_PERIOD_MASTER",pkey);
                   
                    String startTimeHour=periodlist.get(4).trim();
                    String startTimeMin=periodlist.get(5).trim();
                    String endTimeHour=periodlist.get(6).trim();
                    String endTimeMin=periodlist.get(7).trim();
                   
                   
                   
                   period.add(Json.createObjectBuilder().add("periodNumber", studentTimeTable.timeTable[i].period[j].getPeriodNumber())
                                                        .add("subjectID", studentTimeTable.timeTable[i].period[j].getSubjectID())
                                                        .add("subjectName", studentTimeTable.timeTable[i].period[j].getSubjectName())
                                                        .add("teacherID", studentTimeTable.timeTable[i].period[j].getTeacherID())
                                                        .add("teacherName", studentTimeTable.timeTable[i].period[j].getTeacherName())
                                                        .add("startTime", Json.createObjectBuilder().add("hour", startTimeHour).add("min", startTimeMin))
                                                        .add("endTime", Json.createObjectBuilder().add("hour", endTimeHour).add("min", endTimeMin)));
                }
                timeTable.add(Json.createObjectBuilder().add("day", studentTimeTable.timeTable[i].getDay())
                                                        .add("period",period));
           }
              

              
                 
            body=Json.createObjectBuilder()
                                           .add("studentID", studentTimeTable.getStudentID())
                                           .add("studentName", studentName)
                                           .add("timeTable", timeTable)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of student timeTable buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student timeTable--->businessValidation");    
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
       dbg("end of student timeTable--->businessValidation"); 
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
        dbg("inside student timeTable master mandatory validation");
        RequestBody<StudentTimeTable> reqBody = request.getReqBody();
        StudentTimeTable studentTimeTable =reqBody.get();
        
         if(studentTimeTable.getStudentID()==null||studentTimeTable.getStudentID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","studentID");  
         }
          
        dbg("end of student timeTable master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student timeTable masterDataValidation");
             RequestBody<StudentTimeTable> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             StudentTimeTable studentTimeTable =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_studentID=studentTimeTable.getStudentID();
             
             if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","studentID");
             }
             
            
            dbg("end of student timeTable masterDataValidation");
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
        RequestBody<StudentTimeTable> reqBody = request.getReqBody();
        StudentTimeTable studentTimeTable =reqBody.get();
        
        try{
            
            dbg("inside student timeTable detailMandatoryValidation");
           
            if(studentTimeTable.getTimeTable()==null||studentTimeTable.getTimeTable().length==0){
                status=false;
                errhandler.log_app_error("BS_VAL_002","timeTable");
            }else{
                
                for(int i=0;i<studentTimeTable.getTimeTable().length;i++){
                    
                    if(studentTimeTable.getTimeTable()[i].getDay()==null||studentTimeTable.getTimeTable()[i].getDay().isEmpty()){
                        status=false; 
                        errhandler.log_app_error("BS_VAL_002","day");
                    }
                   
                    if(studentTimeTable.getTimeTable()[i].getPeriod()==null||studentTimeTable.getTimeTable()[i].getPeriod().length==0){
                        status=false; 
                        errhandler.log_app_error("BS_VAL_002","period");
                    }else{
                        
                        for(int j=0;j<studentTimeTable.getTimeTable()[i].getPeriod().length;j++){
                            
                            if(studentTimeTable.getTimeTable()[i].getPeriod()[j].getPeriodNumber()==null||studentTimeTable.getTimeTable()[i].getPeriod()[j].getPeriodNumber().isEmpty()){
                               status=false; 
                               errhandler.log_app_error("BS_VAL_002","periodNumber");
                            }
                            if(studentTimeTable.getTimeTable()[i].getPeriod()[j].getSubjectID()==null||studentTimeTable.getTimeTable()[i].getPeriod()[j].getSubjectID().isEmpty()){
                               status=false; 
                               errhandler.log_app_error("BS_VAL_002","subjectID");
                            }
                            if(studentTimeTable.getTimeTable()[i].getPeriod()[j].getTeacherID()==null||studentTimeTable.getTimeTable()[i].getPeriod()[j].getTeacherID().isEmpty()){
                               status=false; 
                               errhandler.log_app_error("BS_VAL_002","teacherID");
                            }
                           
                        }
                    }
                }
            }
            
           
            
           dbg("end of student timeTable detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student timeTable detailDataValidation");
             RequestBody<StudentTimeTable> reqBody = request.getReqBody();
             StudentTimeTable studentTimeTable =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             BSValidation bsv=inject.getBsv(session);

             int timeTableSize=studentTimeTable.getTimeTable().length;
             
             if(!bsv.timeTableSizeValidation(timeTableSize, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Time Table Size");
             }else{
                 
                 for(int i=0;i<studentTimeTable.getTimeTable().length;i++){
                    String day=studentTimeTable.getTimeTable()[i].getDay();
                    int periodSize=studentTimeTable.getTimeTable()[i].getPeriod().length;
                     
                    if(!bsv.dayValidation(day, session, dbSession, inject)){
                        status=false;
                        errhandler.log_app_error("BS_VAL_003","Day");
                    }
                   
                    if(!bsv.periodSizeValidation(periodSize, session, dbSession, inject)){
                        status=false;
                        errhandler.log_app_error("BS_VAL_003","PeriodSize");
                    }else{
                        
                        for(int j=0;j<studentTimeTable.getTimeTable()[i].getPeriod().length;j++){
                            
                            String l_periodNumber=studentTimeTable.getTimeTable()[i].getPeriod()[j].getPeriodNumber();
                            String l_subjectID=studentTimeTable.getTimeTable()[i].getPeriod()[j].getSubjectID();
                            String l_teacherID=studentTimeTable.getTimeTable()[i].getPeriod()[j].getTeacherID();
                                    
                            if(!bsv.periodNumberValidation(l_periodNumber, session, dbSession, inject)){
                               status=false;
                               errhandler.log_app_error("BS_VAL_003","periodNumber");
                            }
                            if(!bsv.subjectIDValidation(l_subjectID, l_instituteID, session, dbSession, inject)){
                                status=false;
                                errhandler.log_app_error("BS_VAL_003","subjectID");
                            }
                            if(!bsv.teacherIDValidation(l_teacherID, l_instituteID, session, dbSession, inject)){
                                status=false;
                                errhandler.log_app_error("BS_VAL_003","teacherID");
                            }
                        }
                    } 
                }
            }
             
            
            dbg("end of student timeTable detailDataValidation");
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
        exAudit=new ExistingAudit(); 
        dbg("inside StudentTimeTable--->getExistingAudit") ;
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
               dbg("inside StudentTimeTable--->getExistingAudit--->Service--->UserTimeTable");  
               RequestBody<StudentTimeTable> studentTimeTableBody = request.getReqBody();
               StudentTimeTable studentTimeTable =studentTimeTableBody.get();
               String l_studentID=studentTimeTable.getStudentID();
               String[] l_pkey={l_studentID};
               DBRecord l_studentTimeTableRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_TIMETABLE_MASTER", l_pkey, session, dbSession);
               exAudit.setAuthStatus(l_studentTimeTableRecord.getRecord().get(6).trim());
               exAudit.setMakerID(l_studentTimeTableRecord.getRecord().get(1).trim());
               exAudit.setRecordStatus(l_studentTimeTableRecord.getRecord().get(5).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_studentTimeTableRecord.getRecord().get(7).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of StudentTimeTable--->getExistingAudit") ;
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
    
    public void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
         
         return;
         
     }
}
