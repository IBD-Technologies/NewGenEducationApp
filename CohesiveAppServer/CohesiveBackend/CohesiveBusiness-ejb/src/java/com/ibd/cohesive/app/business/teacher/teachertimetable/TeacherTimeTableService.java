/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.teachertimetable;

import com.ibd.businessViews.ITeacherTimeTableService;
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
import static java.util.Map.Entry.comparingByKey;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toMap;
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
//@Local(ITeacherTimeTableService.class)
@Remote(ITeacherTimeTableService.class)
@Stateless
public class TeacherTimeTableService implements ITeacherTimeTableService{
     AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public TeacherTimeTableService(){
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
       dbg("inside TeacherTimeTableService--->processing");
       dbg("TeacherTimeTableService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<TeacherTimeTable> reqBody = request.getReqBody();
       TeacherTimeTable teacherTimeTable =reqBody.get();
       l_lockKey=teacherTimeTable.getTeacherID();
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<ITeacherTimeTableService>teacherTimeTableEJB=new BusinessEJB();
       teacherTimeTableEJB.set(this);
      
       exAudit=bs.getExistingAudit(teacherTimeTableEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,teacherTimeTableEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,teacherTimeTableEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in teacher timeTable");
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
               /* if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
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
      TeacherTimeTable teacherTimeTable=new TeacherTimeTable();
      RequestBody<TeacherTimeTable> reqBody = new RequestBody<TeacherTimeTable>(); 
           
      try{
      dbg("inside teacher timeTable buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      teacherTimeTable.setTeacherID(l_body.getString("teacherID"));
      teacherTimeTable.setTeacherName(l_body.getString("teacherName"));
      dbg("l_body"+l_body.toString());
      if(!l_operation.equals("View")){
          JsonArray l_timeTable=l_body.getJsonArray("timeTable");
          teacherTimeTable.timeTable=new TimeTable[l_timeTable.size()];
          
            for(int i=0;i<l_timeTable.size();i++){
                teacherTimeTable.timeTable[i]=new TimeTable();
                JsonObject l_day_object=l_timeTable.getJsonObject(i);
                teacherTimeTable.timeTable[i].setDay(l_day_object.getString("day"));
                JsonArray l_period=l_day_object.getJsonArray("period");
                teacherTimeTable.timeTable[i].period=new Period[l_period.size()];
                
                    for(int j=0;j<l_period.size();j++){
                        teacherTimeTable.timeTable[i].period[j]=new Period();
                        JsonObject l_period_object=l_period.getJsonObject(j);
                        teacherTimeTable.timeTable[i].period[j].setPeriodNumber(l_period_object.getString("periodNumber"));
                        teacherTimeTable.timeTable[i].period[j].setSubjectID(l_period_object.getString("subjectID"));
                        String l_class=l_period_object.getString("class");
                        teacherTimeTable.timeTable[i].period[j].setStandard(l_class.split("/")[0]);
                        teacherTimeTable.timeTable[i].period[j].setSection(l_class.split("/")[1]);
                    }
            }
        }
        reqBody.set(teacherTimeTable);
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
        RequestBody<TeacherTimeTable> reqBody = request.getReqBody();
        TeacherTimeTable teacherTimeTable =reqBody.get();
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
        String l_teacherID=teacherTimeTable.getTeacherID();
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER",23,l_teacherID,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);

        
        for(int i=0;i<teacherTimeTable.timeTable.length;i++){
            String l_day=teacherTimeTable.timeTable[i].getDay();
            
                   for(int j=0;j<teacherTimeTable.timeTable[i].period.length;j++){
                       String l_periodNumber=teacherTimeTable.timeTable[i].period[j].getPeriodNumber();
                       String l_subjectID=teacherTimeTable.timeTable[i].period[j].getSubjectID();
                       String p_standard=teacherTimeTable.timeTable[i].period[j].getStandard();
                       String p_section=teacherTimeTable.timeTable[i].period[j].getSection();
                       
                       dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER",47,l_teacherID,l_day,l_periodNumber,l_subjectID,p_standard,p_section,l_versionNumber);
           
                        
                   }
                                 
               }
        
        dbg("end of teacher timeTable create"); 
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
        dbg("inside teacher timeTable--->auth update"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<TeacherTimeTable> reqBody = request.getReqBody();
        TeacherTimeTable teacherTimeTable =reqBody.get();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_teacherID=teacherTimeTable.getTeacherID();
        String[] l_primaryKey={l_teacherID};
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("3", l_checkerID);
         l_column_to_update.put("5", l_checkerDateStamp);
         l_column_to_update.put("7", l_authStatus);
         l_column_to_update.put("10", l_checkerRemarks);
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_TEACHER_TIMETABLE_MASTER", l_primaryKey, l_column_to_update, session);
         dbg("end of teacher timeTable--->auth update");          
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
        RequestBody<TeacherTimeTable> reqBody = request.getReqBody();
        TeacherTimeTable teacherTimeTable =reqBody.get();
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
        String l_teacherID=teacherTimeTable.getTeacherID();
        String[] l_master_pkey={l_teacherID};
        l_column_to_update= new HashMap();
        l_column_to_update.put("2", l_makerID);
        l_column_to_update.put("3", l_checkerID);
        l_column_to_update.put("4", l_makerDateStamp);
        l_column_to_update.put("5", l_checkerDateStamp);
        l_column_to_update.put("6", l_recordStatus);
        l_column_to_update.put("7", l_authStatus);
        l_column_to_update.put("9", l_makerRemarks);
        l_column_to_update.put("10", l_checkerRemarks);        
        
        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_TEACHER_TIMETABLE_MASTER", l_master_pkey, l_column_to_update, session);                      
        
        IDBReadBufferService readBuffer = inject.getDBReadBufferService();
        Map<String,DBRecord>l_timeTableMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_TEACHER_TIMETABLE_MASTER", session, dbSession);
        setOperationsOfTheRecord("TVW_TEACHER_TIMETABLE_DETAIL",l_timeTableMap);
        
        for(int i=0;i<teacherTimeTable.timeTable.length;i++){
                                      
                   String l_day=teacherTimeTable.timeTable[i].getDay();
                   for(int j=0;j<teacherTimeTable.timeTable[i].period.length;j++){
                       
                       String l_periodNumber=teacherTimeTable.timeTable[i].period[j].getPeriodNumber();
                       String l_subjectID=teacherTimeTable.timeTable[i].period[j].getSubjectID();
                       String p_standard=teacherTimeTable.timeTable[i].period[j].getStandard();
                       String p_section=teacherTimeTable.timeTable[i].period[j].getSection();
                       
                       if(teacherTimeTable.timeTable[i].period[j].getOperation().equals("U")){
                       
                            l_column_to_update= new HashMap();
                            l_column_to_update.put("2", l_day);
                            l_column_to_update.put("3", l_periodNumber);
                            l_column_to_update.put("4", l_subjectID);
                            l_column_to_update.put("5", p_standard);
                            l_column_to_update.put("6", p_section);
                            String[] l_primaryKey={l_teacherID,l_day,l_periodNumber};

                            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_TEACHER_TIMETABLE_DETAIL", l_primaryKey, l_column_to_update, session);                      
                        
                       }else{
                           
                           dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER",47,l_teacherID,l_day,l_periodNumber,l_subjectID,p_standard,p_section,l_versionNumber);
                       }
                   }
                                 
               }
        
            ArrayList<String>cpList=getRecordsToDelete("TVW_TEACHER_TIMETABLE_DETAIL",l_timeTableMap);
         
              for(int i=0;i<cpList.size();i++){
                String pkey=cpList.get(i);
                deleteRecordsInTheList("TVW_TEACHER_TIMETABLE_DETAIL",pkey);
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
            RequestBody<TeacherTimeTable> reqBody = request.getReqBody();
            TeacherTimeTable timeTable =reqBody.get();
            String l_teacherID=timeTable.getTeacherID();
            dbg("tableName"+tableName);
            
            switch(tableName){
                
                case "TVW_TEACHER_TIMETABLE_DETAIL":  
                
                         for(int i=0;i<timeTable.timeTable.length;i++){
                             
                             String l_day=timeTable.timeTable[i].getDay();
                             
                             for(int j=0;j<timeTable.timeTable[i].period.length;j++){
                       
                                String l_periodNumber=timeTable.timeTable[i].period[j].getPeriodNumber();
                                String l_pkey=l_teacherID+"~"+l_day+"~"+l_periodNumber;
                                
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
           RequestBody<TeacherTimeTable> reqBody = request.getReqBody();
           TeacherTimeTable timeTable =reqBody.get();
           String l_teacherID=timeTable.getTeacherID();
           ArrayList<String>recordsToDelete=new ArrayList();
           String teacherID=timeTable.getTeacherID();
//           Iterator<String>keyIterator=tableMap.keySet().iterator();

           List<DBRecord>filteredRecords=tableMap.values().stream().filter(rec->rec.getRecord().get(0).trim().equals(teacherID)).collect(Collectors.toList());
        
           dbg("tableName"+tableName);
           switch(tableName){
           
                 case "TVW_TEACHER_TIMETABLE_DETAIL":   
                   
                  for(int k=0;k<filteredRecords.size();k++){
                        String day=filteredRecords.get(k).getRecord().get(1).trim();
                        String periodNumber=filteredRecords.get(k).getRecord().get(2).trim();
                        String tablePkey=teacherID+"~"+day+"~"+periodNumber;
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                       for(int i=0;i<timeTable.timeTable.length;i++){
                             
                             String l_day=timeTable.timeTable[i].getDay();
                             
                             for(int j=0;j<timeTable.timeTable[i].period.length;j++){
                       
                               String l_periodNumber=timeTable.timeTable[i].period[j].getPeriodNumber(); 
                               String l_requestPkey=l_teacherID+"~"+l_day+"~"+l_periodNumber;
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
             RequestBody<TeacherTimeTable> reqBody = request.getReqBody();
             TeacherTimeTable timeTable =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_teacherID=timeTable.getTeacherID();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IDBTransactionService dbts=inject.getDBTransactionService();
             String[] pkArr=pkey.split("~");
             
             
             dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER",tableName, pkArr, session);
             
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
        dbg("inside teacher timeTable delete");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<TeacherTimeTable> reqBody = request.getReqBody();
        TeacherTimeTable teacherTimeTable =reqBody.get();
        String l_teacherID=teacherTimeTable.getTeacherID();
             
             String[] l_primaryKey={l_teacherID};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_TEACHER_TIMETABLE_MASTER", l_primaryKey, session);
          
                        
        for(int i=0;i<teacherTimeTable.timeTable.length;i++){
            String l_day=teacherTimeTable.timeTable[i].getDay();
                   
                   for(int j=0;j<teacherTimeTable.timeTable[i].period.length;j++){
                       String l_periodNumber=teacherTimeTable.timeTable[i].period[j].getPeriodNumber();
                       String[] l_detailprimaryKey={l_teacherID,l_day,l_periodNumber};
                        
                       dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_TEACHER_TIMETABLE_DETAIL",l_detailprimaryKey,session);
                      }
                   }                
                        
            dbg("end of teacher timeTable delete");
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
        dbg("inside teacher timeTable--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<TeacherTimeTable> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        TeacherTimeTable teacherTimeTable =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_teacherID=teacherTimeTable.getTeacherID();
        String[] l_pkey={l_teacherID};
        
        //DBRecord timeTableRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_TEACHER_TIMETABLE_MASTER", l_pkey, session, dbSession);
        //Map<String,DBRecord>detailMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_TEACHER_TIMETABLE_DETAIL", session, dbSession);
        
        //Map<String,DBRecord> timeTableRec =readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TimeTable","INSTITUTE","IVW_SUBJECT_DETAILS",session, dbSession);
        Map<String,DBRecord>detailMap;
        try
        {   
          // timeTableRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS", "CLASS_TIMETABLE_MASTER", l_pkey1, session, dbSession);
        
            detailMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TimeTable","INSTITUTE","IVW_SUBJECT_DETAILS",session, dbSession);
        
        }
         catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013",l_teacherID );
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
     
         if(detailMap.isEmpty())
         {
            session.getErrorhandler().log_app_error("BS_VAL_013",l_teacherID );
              throw new BSValidationException("BSValidationException");
                         
         }   
        buildBOfromDB(detailMap);
        
          dbg("end of  completed teacher timeTable--->view");                
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
      private void buildBOfromDB(DBRecord p_teacherTimeTableRecord, Map<String,DBRecord>detailMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<TeacherTimeTable> reqBody = request.getReqBody();
           String instituteID=request.getReqHeader().getInstituteID();
           BusinessService bs=inject.getBusinessService(session);
           TeacherTimeTable teacherTimeTable =reqBody.get();
           ArrayList<String>l_teacherTimeTableList= p_teacherTimeTableRecord.getRecord();
           
           if(l_teacherTimeTableList!=null&&!l_teacherTimeTableList.isEmpty()){
               
            request.getReqAudit().setMakerID(l_teacherTimeTableList.get(1).trim());
            request.getReqAudit().setCheckerID(l_teacherTimeTableList.get(2).trim());
            request.getReqAudit().setMakerDateStamp(l_teacherTimeTableList.get(3).trim());
            request.getReqAudit().setCheckerDateStamp(l_teacherTimeTableList.get(4).trim());
            request.getReqAudit().setRecordStatus(l_teacherTimeTableList.get(5).trim());
            request.getReqAudit().setAuthStatus(l_teacherTimeTableList.get(6).trim());
            request.getReqAudit().setVersionNumber(l_teacherTimeTableList.get(7).trim());
            request.getReqAudit().setMakerRemarks(l_teacherTimeTableList.get(8).trim());
            request.getReqAudit().setCheckerRemarks(l_teacherTimeTableList.get(9).trim());
            }
           
            Map<String,List<DBRecord>>dayWiseGroup=detailMap.values().stream().collect(Collectors.groupingBy(rec->rec.getRecord().get(1).trim()));
            teacherTimeTable.timeTable=new TimeTable[dayWiseGroup.keySet().size()];
            Iterator<String> dayIterator=dayWiseGroup.keySet().iterator();
            int i=0;
            
            while(dayIterator.hasNext()){
                String day=dayIterator.next();
                teacherTimeTable.timeTable[i]=new TimeTable();
                teacherTimeTable.timeTable[i].setDay(day);
                dbg("day"+day);
                dbg("dayWiseGroup.get(day).size()"+dayWiseGroup.get(day).size());
                teacherTimeTable.timeTable[i].period=new Period[dayWiseGroup.get(day).size()];
                int j=0;
                for(DBRecord periodRecords: dayWiseGroup.get(day)){
                   teacherTimeTable.timeTable[i].period[j]=new Period();
                   teacherTimeTable.timeTable[i].period[j].setPeriodNumber(periodRecords.getRecord().get(2).trim());
                   teacherTimeTable.timeTable[i].period[j].setSubjectID(periodRecords.getRecord().get(3).trim());
                   String l_subjectName=bs.getSubjectName(teacherTimeTable.timeTable[i].period[j].getSubjectID(), instituteID, session, dbSession, inject);
                   teacherTimeTable.timeTable[i].period[j].setSubjectName(l_subjectName);
                   teacherTimeTable.timeTable[i].period[j].setStandard(periodRecords.getRecord().get(4).trim());
                   teacherTimeTable.timeTable[i].period[j].setSection(periodRecords.getRecord().get(5).trim());
                  j++;
                  dbg("periodArraySize"+j);
                }
                
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
    
        private void buildBOfromDB(Map<String,DBRecord>detailMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<TeacherTimeTable> reqBody = request.getReqBody();
           String instituteID=request.getReqHeader().getInstituteID();
           BusinessService bs=inject.getBusinessService(session);
           TeacherTimeTable teacherTimeTable =reqBody.get();
           String l_teacherID=teacherTimeTable.getTeacherID();
           String[] l_pkey={l_teacherID};
        
           /* ArrayList<String>l_teacherTimeTableList= p_teacherTimeTableRecord.getRecord();
           
           if(l_teacherTimeTableList!=null&&!l_teacherTimeTableList.isEmpty()){
               
            request.getReqAudit().setMakerID(l_teacherTimeTableList.get(1).trim());
            request.getReqAudit().setCheckerID(l_teacherTimeTableList.get(2).trim());
            request.getReqAudit().setMakerDateStamp(l_teacherTimeTableList.get(3).trim());
            request.getReqAudit().setCheckerDateStamp(l_teacherTimeTableList.get(4).trim());
            request.getReqAudit().setRecordStatus(l_teacherTimeTableList.get(5).trim());
            request.getReqAudit().setAuthStatus(l_teacherTimeTableList.get(6).trim());
            request.getReqAudit().setVersionNumber(l_teacherTimeTableList.get(7).trim());
            request.getReqAudit().setMakerRemarks(l_teacherTimeTableList.get(8).trim());
            request.getReqAudit().setCheckerRemarks(l_teacherTimeTableList.get(9).trim());
            }*/
           Map<String,List<DBRecord>>dayWiseGroup = detailMap.values().stream().filter(rec->rec.getRecord().get(5).trim().equals(l_teacherID)).collect(Collectors.groupingBy(rec->rec.getRecord().get(3).trim()));
           
           // Map<String,DBRecord>  sortedMap = detailMap.entrySet().stream().sorted(comparingByKey()).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, HashMap::new));
                  
            //Map<String,List<DBRecord>>dayWiseGroup=detailMap.values().stream().collect(Collectors.groupingBy(rec->rec.getRecord().get(1).trim()));
            teacherTimeTable.timeTable=new TimeTable[dayWiseGroup.keySet().size()];
            Iterator<String> dayIterator=dayWiseGroup.keySet().iterator();
            int i=0;
            
            while(dayIterator.hasNext()){
                String day=dayIterator.next();
                teacherTimeTable.timeTable[i]=new TimeTable();
                teacherTimeTable.timeTable[i].setDay(day);
                dbg("day"+day);
                dbg("dayWiseGroup.get(day).size()"+dayWiseGroup.get(day).size());
                teacherTimeTable.timeTable[i].period=new Period[dayWiseGroup.get(day).size()];
                int j=0;
                for(DBRecord periodRecords: dayWiseGroup.get(day)){
                   teacherTimeTable.timeTable[i].period[j]=new Period();
                   teacherTimeTable.timeTable[i].period[j].setPeriodNumber(periodRecords.getRecord().get(2).trim());
                   teacherTimeTable.timeTable[i].period[j].setSubjectID(periodRecords.getRecord().get(4).trim());
                   String l_subjectName=bs.getSubjectName(teacherTimeTable.timeTable[i].period[j].getSubjectID(), instituteID, session, dbSession, inject);
                   teacherTimeTable.timeTable[i].period[j].setSubjectName(l_subjectName);
                   teacherTimeTable.timeTable[i].period[j].setStandard(periodRecords.getRecord().get(0).trim());
                   teacherTimeTable.timeTable[i].period[j].setSection(periodRecords.getRecord().get(1).trim());
                  j++;
                  dbg("periodArraySize"+j);
                }
                Arrays.sort(teacherTimeTable.timeTable[i].getPeriod());
                i++;
            }
               if( teacherTimeTable.timeTable.length==0)
         {
            session.getErrorhandler().log_app_error("BS_VAL_013",l_teacherID );
              throw new BSValidationException("BSValidationException");
                         
         }  
            
          dbg("end of  buildBOfromDB"); 
        
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
      
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside teacher timeTable buildJsonResFromBO");
        IPDataService pds=inject.getPdataservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<TeacherTimeTable> reqBody = request.getReqBody();
        TeacherTimeTable teacherTimeTable =reqBody.get();
        JsonArrayBuilder period=Json.createArrayBuilder();
        JsonArrayBuilder timeTable=Json.createArrayBuilder();
        String l_instituteID=request.getReqHeader().getInstituteID();
        dbg("Time table size"+teacherTimeTable.timeTable.length);
        
           for(int i=0;i<teacherTimeTable.timeTable.length;i++){
               dbg("teacherTimeTable.timeTable[i].period"+teacherTimeTable.timeTable[i]);
               dbg("period size"+teacherTimeTable.timeTable[i].period.length);
               for(int j=0;j<teacherTimeTable.timeTable[i].period.length;j++){
                   
                   String l_standard=teacherTimeTable.timeTable[i].period[j].getStandard();
                   String l_section=teacherTimeTable.timeTable[i].period[j].getSection();
                   String l_periodNumber=teacherTimeTable.timeTable[i].period[j].getPeriodNumber();
                   
                    String[] pkey={l_instituteID,l_standard,l_section,l_periodNumber};
                    ArrayList<String>periodlist= pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_PERIOD_MASTER",pkey);
                    ArrayList<String>teacherRecord= pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_PERIOD_MASTER",pkey);
                   
                    String startTimeHour=periodlist.get(4).trim();
                    String startTimeMin=periodlist.get(5).trim();
                    String endTimeHour=periodlist.get(6).trim();
                    String endTimeMin=periodlist.get(7).trim();
                   
                   period.add(Json.createObjectBuilder().add("periodNumber", teacherTimeTable.timeTable[i].period[j].getPeriodNumber())
                                                        .add("subjectID", teacherTimeTable.timeTable[i].period[j].getSubjectID())
                                                        .add("subjectName",teacherTimeTable.timeTable[i].period[j].getSubjectName())
                                                        .add("startTime", Json.createObjectBuilder().add("hour", startTimeHour).add("min", startTimeMin))
                                                        .add("endTime", Json.createObjectBuilder().add("hour", endTimeHour).add("min", endTimeMin))
                                                        .add("class",l_standard+"/"+l_section));
                                                     
                }
                timeTable.add(Json.createObjectBuilder().add("day", teacherTimeTable.timeTable[i].getDay())
                                                        .add("period",period));
           }
              
          String[] pkey={teacherTimeTable.getTeacherID()};
          ArrayList<String>teacherRec=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_TEACHER_MASTER",pkey);
              
                 
            body=Json.createObjectBuilder()
                                           .add("teacherID", teacherTimeTable.getTeacherID())
                                           .add("teacherName", teacherRec.get(1))
                                           .add("timeTable", timeTable)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of teacher timeTable buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside teacher timeTable--->businessValidation");    
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
       dbg("end of teacher timeTable--->businessValidation"); 
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
        dbg("inside teacher timeTable master mandatory validation");
        RequestBody<TeacherTimeTable> reqBody = request.getReqBody();
        TeacherTimeTable teacherTimeTable =reqBody.get();
        
         if(teacherTimeTable.getTeacherID()==null||teacherTimeTable.getTeacherID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","teacherID");  
         }
          
        dbg("end of teacher timeTable master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside teacher timeTable masterDataValidation");
             RequestBody<TeacherTimeTable> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             TeacherTimeTable teacherTimeTable =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_teacherID=teacherTimeTable.getTeacherID();
             
             if(!bsv.teacherIDValidation(l_teacherID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","teacherID");
             }
             
            
            dbg("end of teacher timeTable masterDataValidation");
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
        RequestBody<TeacherTimeTable> reqBody = request.getReqBody();
        TeacherTimeTable teacherTimeTable =reqBody.get();
        
        try{
            
            dbg("inside teacher timeTable detailMandatoryValidation");
           
            if(teacherTimeTable.getTimeTable()==null||teacherTimeTable.getTimeTable().length==0){
                status=false;
                errhandler.log_app_error("BS_VAL_002","timeTable");
            }else{
                
                for(int i=0;i<teacherTimeTable.getTimeTable().length;i++){
                    
                    if(teacherTimeTable.getTimeTable()[i].getDay()==null||teacherTimeTable.getTimeTable()[i].getDay().isEmpty()){
                        status=false; 
                        errhandler.log_app_error("BS_VAL_002","day");
                    }
                   
                    if(teacherTimeTable.getTimeTable()[i].getPeriod()==null||teacherTimeTable.getTimeTable()[i].getPeriod().length==0){
                        status=false; 
                        errhandler.log_app_error("BS_VAL_002","period");
                    }else{
                        
                        for(int j=0;j<teacherTimeTable.getTimeTable()[i].getPeriod().length;j++){
                            
                            if(teacherTimeTable.getTimeTable()[i].getPeriod()[j].getPeriodNumber()==null||teacherTimeTable.getTimeTable()[i].getPeriod()[j].getPeriodNumber().isEmpty()){
                               status=false; 
                               errhandler.log_app_error("BS_VAL_002","periodNumber");
                            }
                            if(teacherTimeTable.getTimeTable()[i].getPeriod()[j].getSubjectID()==null||teacherTimeTable.getTimeTable()[i].getPeriod()[j].getSubjectID().isEmpty()){
                               status=false; 
                               errhandler.log_app_error("BS_VAL_002","subjectID");
                            }
                            if(teacherTimeTable.getTimeTable()[i].getPeriod()[j].getStandard()==null||teacherTimeTable.getTimeTable()[i].getPeriod()[j].getStandard().isEmpty()){
                               status=false; 
                               errhandler.log_app_error("BS_VAL_002","standard");
                            }
                            if(teacherTimeTable.getTimeTable()[i].getPeriod()[j].getSection()==null||teacherTimeTable.getTimeTable()[i].getPeriod()[j].getSection().isEmpty()){
                               status=false; 
                               errhandler.log_app_error("BS_VAL_002","section");
                            }
                           
                        }
                    }
                }
            }
            
           
            
           dbg("end of teacher timeTable detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside teacher timeTable detailDataValidation");
             RequestBody<TeacherTimeTable> reqBody = request.getReqBody();
             TeacherTimeTable teacherTimeTable =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             BSValidation bsv=inject.getBsv(session);

             int timeTableSize=teacherTimeTable.getTimeTable().length;
             
             if(!bsv.timeTableSizeValidation(timeTableSize, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Time Table Size");
             }else{
                 
                 for(int i=0;i<teacherTimeTable.getTimeTable().length;i++){
                    String day=teacherTimeTable.getTimeTable()[i].getDay();
                    int periodSize=teacherTimeTable.getTimeTable()[i].getPeriod().length;
                     
                    if(!bsv.dayValidation(day, session, dbSession, inject)){
                        status=false;
                        errhandler.log_app_error("BS_VAL_003","Day");
                    }
                   
                    if(!bsv.periodSizeValidation(periodSize, session, dbSession, inject)){
                        status=false;
                        errhandler.log_app_error("BS_VAL_003","PeriodSize");
                    }else{
                        
                        for(int j=0;j<teacherTimeTable.getTimeTable()[i].getPeriod().length;j++){
                            
                            String l_periodNumber=teacherTimeTable.getTimeTable()[i].getPeriod()[j].getPeriodNumber();
                            String l_subjectID=teacherTimeTable.getTimeTable()[i].getPeriod()[j].getSubjectID();
                            String l_standard=teacherTimeTable.getTimeTable()[i].getPeriod()[j].getStandard();
                            String l_section=teacherTimeTable.getTimeTable()[i].getPeriod()[j].getSection();
                                    
                            if(!bsv.periodNumberValidation(l_periodNumber, session, dbSession, inject)){
                               status=false;
                               errhandler.log_app_error("BS_VAL_003","periodNumber");
                            }
                            if(!bsv.subjectIDValidation(l_subjectID, l_instituteID, session, dbSession, inject)){
                                status=false;
                                errhandler.log_app_error("BS_VAL_003","subjectID");
                            }
                            if(!bsv.standardSectionValidation(l_standard,l_section, l_instituteID, session, dbSession, inject)){
                                status=false;
                                errhandler.log_app_error("BS_VAL_003","standard or section");
                            }
                        }
                    } 
                }
            }
             
            
            dbg("end of teacher timeTable detailDataValidation");
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
        dbg("inside TeacherTimeTable--->getExistingAudit") ;
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
               dbg("inside TeacherTimeTable--->getExistingAudit--->Service--->UserTimeTable");  
               RequestBody<TeacherTimeTable> teacherTimeTableBody = request.getReqBody();
               TeacherTimeTable teacherTimeTable =teacherTimeTableBody.get();
               String l_teacherID=teacherTimeTable.getTeacherID();
               String[] l_pkey={l_teacherID};
               DBRecord l_teacherTimeTableRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_TEACHER_TIMETABLE_MASTER", l_pkey, session, dbSession);
               exAudit.setAuthStatus(l_teacherTimeTableRecord.getRecord().get(6).trim());
               exAudit.setMakerID(l_teacherTimeTableRecord.getRecord().get(1).trim());
               exAudit.setRecordStatus(l_teacherTimeTableRecord.getRecord().get(5).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_teacherTimeTableRecord.getRecord().get(7).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of TeacherTimeTable--->getExistingAudit") ;
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
