
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.classtimetable;

import com.ibd.businessViews.IClassTimeTableService;
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
import java.util.Collections;
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
//@Local(IClassTimeTableService.class)
@Remote(IClassTimeTableService.class)
@Stateless
public class ClassTimeTableService implements IClassTimeTableService {
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public ClassTimeTableService(){
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
       dbg("inside ClassTimeTableService--->processing");
       dbg("ClassTimeTableService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<ClassTimeTable> reqBody = request.getReqBody();
       ClassTimeTable classTimeTable =reqBody.get();
       String l_standard=classTimeTable.getStandard();
       String l_section=classTimeTable.getSection();
       l_lockKey=l_standard+"~"+l_section;
       String l_operation=request.getReqHeader().getOperation();
       if(!l_operation.equals("Create-Default")){
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       }
       BusinessEJB<IClassTimeTableService>ClassTimeTableEJB=new BusinessEJB();
       ClassTimeTableEJB.set(this);
      
       exAudit=bs.getExistingAudit(ClassTimeTableEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,ClassTimeTableEJB);
       
       if(l_operation.equals("Create-Default")){
           
           createDefault();
       }

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,ClassTimeTableEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in Class timeTable");
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
                dbg("Response"+jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
            //    if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                      clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes  
            //BSProcessingException ex=new BSProcessingException("response contains special characters");
              //     dbg(ex);
                //   session.clearSessionObject();
                  // dbSession.clearSessionObject();
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
      ClassTimeTable classTimeTable=new ClassTimeTable();
      RequestBody<ClassTimeTable> reqBody = new RequestBody<ClassTimeTable>(); 
           
      try{
      dbg("inside Class timeTable buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      BSValidation bsv=inject.getBsv(session);
      String l_operation=request.getReqHeader().getOperation();
//      classTimeTable.setStandard(l_body.getString("standard"));
//      classTimeTable.setSection(l_body.getString("section"));
      String l_class=l_body.getString("class");
       bsv.classValidation(l_class,session);

      classTimeTable.setStandard(l_class.split("/")[0]);
      classTimeTable.setSection(l_class.split("/")[1]);
      dbg("l_body"+l_body.toString());
      if(!l_operation.equals("View")&&!l_operation.equals("Create-Default")){
          JsonArray l_timeTable=l_body.getJsonArray("timeTable");
           dbg("l_timeTable"+l_timeTable.toString());
          dbg("l_timeTable size"+l_timeTable.size());
          classTimeTable.timeTable=new TimeTable[l_timeTable.size()];
          
            for(int i=0;i<l_timeTable.size();i++){
                classTimeTable.timeTable[i]=new TimeTable();
                JsonObject l_day_object=l_timeTable.getJsonObject(i);
                classTimeTable.timeTable[i].setDay(l_day_object.getString("day"));
                JsonArray l_period=l_day_object.getJsonArray("period");
                classTimeTable.timeTable[i].period=new Period[l_period.size()];
                
                    for(int j=0;j<l_period.size();j++){
                        classTimeTable.timeTable[i].period[j]=new Period();
                        JsonObject l_period_object=l_period.getJsonObject(j);
                        classTimeTable.timeTable[i].period[j].setPeriodNumber(l_period_object.getString("periodNumber"));
                        classTimeTable.timeTable[i].period[j].setSubjectID(l_period_object.getString("subjectID"));
                        classTimeTable.timeTable[i].period[j].setTeacherID(l_period_object.getString("teacherID"));
                        classTimeTable.timeTable[i].period[j].setTeacherName(l_period_object.getString("teacherName"));
                    
                    }
            }
        }
        reqBody.set(classTimeTable);
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
        dbg("inside stident timeTable create"); 
        RequestBody<ClassTimeTable> reqBody = request.getReqBody();
        ClassTimeTable classTimeTable =reqBody.get();
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
        String l_standard=classTimeTable.getStandard();
        String l_section=classTimeTable.getSection();
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS",29,l_standard,l_section,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);

        
        for(int i=0;i<classTimeTable.timeTable.length;i++){
            String l_day=classTimeTable.timeTable[i].getDay();
            
                   for(int j=0;j<classTimeTable.timeTable[i].period.length;j++){
                       String l_periodNumber=classTimeTable.timeTable[i].period[j].getPeriodNumber();
                       String l_subjectID=classTimeTable.timeTable[i].period[j].getSubjectID();
                       String p_teacherID=classTimeTable.timeTable[i].period[j].getTeacherID();
                       
                       dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS",48,l_standard,l_section,l_day,l_periodNumber,l_subjectID,p_teacherID,l_versionNumber);
           
                      // dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TimeTable","INSTITUTE",19,l_standard,l_section,l_periodNumber,l_day,l_subjectID,p_teacherID);
  
                   }
                                 
               }
      
        dbg("end of Class timeTable create"); 
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
        dbg("inside Class timeTable--->auth update"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<ClassTimeTable> reqBody = request.getReqBody();
        ClassTimeTable classTimeTable =reqBody.get();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_standard=classTimeTable.getStandard();
        String l_section=classTimeTable.getSection();
        String[] l_primaryKey={l_standard,l_section};
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("4", l_checkerID);
         l_column_to_update.put("6", l_checkerDateStamp);
         l_column_to_update.put("8", l_authStatus);
         l_column_to_update.put("11", l_checkerRemarks);
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS","CLASS_TIMETABLE_MASTER", l_primaryKey, l_column_to_update, session);
         dbg("end of Class timeTable--->auth update");          
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
        RequestBody<ClassTimeTable> reqBody = request.getReqBody();
        ClassTimeTable classTimeTable =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();   
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();   
        String l_standard=classTimeTable.getStandard();
        String l_section=classTimeTable.getSection();
        String[] l_master_pkey={l_standard,l_section};
        l_column_to_update= new HashMap();
        l_column_to_update.put("1", l_standard);
        l_column_to_update.put("2", l_section);
        l_column_to_update.put("3", l_makerID);
        l_column_to_update.put("4", l_checkerID);
        l_column_to_update.put("5", l_makerDateStamp);
        l_column_to_update.put("6", l_checkerDateStamp);
        l_column_to_update.put("7", l_recordStatus);
        l_column_to_update.put("8", l_authStatus);
        l_column_to_update.put("10", l_makerRemarks);
        l_column_to_update.put("11", l_checkerRemarks);             
        
        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS","CLASS_TIMETABLE_MASTER", l_master_pkey, l_column_to_update, session);                
        
        IDBReadBufferService readBuffer = inject.getDBReadBufferService();
        Map<String,DBRecord>l_timeTableMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS","CLASS_TIMETABLE_DETAIL", session, dbSession);
        setOperationsOfTheRecord("CLASS_TIMETABLE_DETAIL",l_timeTableMap);
        
        
        for(int i=0;i<classTimeTable.timeTable.length;i++){
                                      
                   String l_day=classTimeTable.timeTable[i].getDay();
                   for(int j=0;j<classTimeTable.timeTable[i].period.length;j++){
                       
                       String l_periodNumber=classTimeTable.timeTable[i].period[j].getPeriodNumber();
                       String l_subjectID=classTimeTable.timeTable[i].period[j].getSubjectID();
                       String p_teacherID=classTimeTable.timeTable[i].period[j].getTeacherID();
                       
                       if(classTimeTable.timeTable[i].period[j].getOperation().equals("U")){
                       
                            l_column_to_update= new HashMap();
                            l_column_to_update.put("3", l_day);
                            l_column_to_update.put("4", l_periodNumber);
                            l_column_to_update.put("5", l_subjectID);
                            l_column_to_update.put("6", p_teacherID);
                            String[] l_primaryKey={l_standard,l_section,l_day,l_periodNumber};

                            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS","CLASS_TIMETABLE_DETAIL", l_primaryKey, l_column_to_update, session);                      
                       }else{
                           
                           dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS",48,l_standard,l_section,l_day,l_periodNumber,l_subjectID,p_teacherID,l_versionumber);
                           
                       }
                   }
            }
        
        ArrayList<String>cpList=getRecordsToDelete("CLASS_TIMETABLE_DETAIL",l_timeTableMap);
         
          for(int i=0;i<cpList.size();i++){
            String pkey=cpList.get(i);
            deleteRecordsInTheList("CLASS_TIMETABLE_DETAIL",pkey);
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
            RequestBody<ClassTimeTable> reqBody = request.getReqBody();
            ClassTimeTable timeTable =reqBody.get();
            String l_standard=timeTable.getStandard();
            String l_section=timeTable.getSection();  
            dbg("tableName"+tableName);
            
            switch(tableName){
                
                case "CLASS_TIMETABLE_DETAIL":  
                
                         for(int i=0;i<timeTable.timeTable.length;i++){
                             
                             String l_day=timeTable.timeTable[i].getDay();
                             
                             for(int j=0;j<timeTable.timeTable[i].period.length;j++){
                       
                                String l_periodNumber=timeTable.timeTable[i].period[j].getPeriodNumber();
                                String l_pkey=l_standard+"~"+l_section+"~"+l_day+"~"+l_periodNumber;
                                
                                   if(tableMap.containsKey(l_pkey)){

                                        timeTable.timeTable[i].period[j].setOperation("U");
                                    }else{

                                        timeTable.timeTable[i].period[j].setOperation("C");
                                    }
                         } 
                  //break;      

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
           RequestBody<ClassTimeTable> reqBody = request.getReqBody();
           ClassTimeTable timeTable =reqBody.get();
           String l_standard=timeTable.getStandard();
           String l_section=timeTable.getSection();  
           ArrayList<String>recordsToDelete=new ArrayList();
//           Iterator<String>keyIterator=tableMap.keySet().iterator();
           List<DBRecord>filteredRecords=tableMap.values().stream().filter(rec->rec.getRecord().get(0).trim().equals(l_standard)&&rec.getRecord().get(1).trim().equals(l_section)).collect(Collectors.toList());
        
           dbg("tableName"+tableName);
           switch(tableName){
           
                 case "CLASS_TIMETABLE_DETAIL":   
                   
//                   while(keyIterator.hasNext()){
                     for(int k=0;k<filteredRecords.size();k++){
                        String day=filteredRecords.get(k).getRecord().get(2).trim(); 
                        String periodNumber=filteredRecords.get(k).getRecord().get(3).trim(); 
                        String tablePkey=l_standard+"~"+l_section+"~"+day+"~"+periodNumber;
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                       for(int i=0;i<timeTable.timeTable.length;i++){
                             
                             String l_day=timeTable.timeTable[i].getDay();
                             
                             for(int j=0;j<timeTable.timeTable[i].period.length;j++){
                       
                               String l_periodNumber=timeTable.timeTable[i].period[j].getPeriodNumber(); 
                               String l_requestPkey=l_standard+"~"+l_section+"~"+l_day+"~"+l_periodNumber;
                               dbg("l_requestPkey"+l_requestPkey);
                               if(tablePkey.equals(l_requestPkey)){
                                   recordExistence=true;
                               }
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
             RequestBody<ClassTimeTable> reqBody = request.getReqBody();
             ClassTimeTable timeTable =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_standard=timeTable.getStandard();
             String l_section=timeTable.getSection();  
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IDBTransactionService dbts=inject.getDBTransactionService();
             String[] pkArr=pkey.split("~");
             
             
             dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS",tableName, pkArr, session);
             
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
        dbg("inside Class timeTable delete");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassTimeTable> reqBody = request.getReqBody();
        ClassTimeTable classTimeTable =reqBody.get();
        String l_standard=classTimeTable.getStandard();
        String l_section=classTimeTable.getSection();
             
             String[] l_primaryKey={l_standard,l_section};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS","CLASS_TIMETABLE_MASTER", l_primaryKey, session);
          
                        
        for(int i=0;i<classTimeTable.timeTable.length;i++){
            String l_day=classTimeTable.timeTable[i].getDay();
                   
                   for(int j=0;j<classTimeTable.timeTable[i].period.length;j++){
                       String l_periodNumber=classTimeTable.timeTable[i].period[j].getPeriodNumber();
                       String[] l_detailprimaryKey={l_standard,l_section,l_day,l_periodNumber};
                        
                       dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS","CLASS_TIMETABLE_DETAIL",l_detailprimaryKey,session);
                      }
                   }                
                        
            dbg("end of Class timeTable delete");
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
        dbg("inside Class timeTable--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassTimeTable> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        ClassTimeTable classTimeTable =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_standard=classTimeTable.getStandard();
        String l_section=classTimeTable.getSection();
        String[] l_pkey={l_standard,l_section};
        DBRecord timeTableRec;
        Map<String,DBRecord>detailMap;
         try{
            
        timeTableRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS","CLASS_TIMETABLE_MASTER", l_pkey, session, dbSession);
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
        
          dbg("end of  completed Class timeTable--->view");                
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
      private void buildBOfromDB(DBRecord p_ClassTimeTableRecord, Map<String,DBRecord>detailMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<ClassTimeTable> reqBody = request.getReqBody();
           ClassTimeTable ClassTimeTable =reqBody.get();
           String instituteID=request.getReqHeader().getInstituteID();
           BusinessService bs=inject.getBusinessService(session);
           ArrayList<String>l_ClassTimeTableList= p_ClassTimeTableRecord.getRecord();
           
           if(l_ClassTimeTableList!=null&&!l_ClassTimeTableList.isEmpty()){
               
            request.getReqAudit().setMakerID(l_ClassTimeTableList.get(2).trim());
            request.getReqAudit().setCheckerID(l_ClassTimeTableList.get(3).trim());
            request.getReqAudit().setMakerDateStamp(l_ClassTimeTableList.get(4).trim());
            request.getReqAudit().setCheckerDateStamp(l_ClassTimeTableList.get(5).trim());
            request.getReqAudit().setRecordStatus(l_ClassTimeTableList.get(6).trim());
            request.getReqAudit().setAuthStatus(l_ClassTimeTableList.get(7).trim());
            request.getReqAudit().setVersionNumber(l_ClassTimeTableList.get(8).trim());
            request.getReqAudit().setMakerRemarks(l_ClassTimeTableList.get(9).trim());
            request.getReqAudit().setCheckerRemarks(l_ClassTimeTableList.get(10).trim());
            }
           
           int versionIndex=bs.getVersionIndexOfTheTable("CLASS_TIMETABLE_DETAIL", "CLASS", session, dbSession, inject);
           
            Map<String,List<DBRecord>>dayWiseGroup=detailMap.values().stream().filter(rec->rec.getRecord().get(versionIndex).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.groupingBy(rec->rec.getRecord().get(2).trim()));
            
            ClassTimeTable.timeTable=new TimeTable[dayWiseGroup.keySet().size()];
            Iterator<String> dayIterator=dayWiseGroup.keySet().iterator();
            int i=0;
            
            while(dayIterator.hasNext()){
                String day=dayIterator.next();
                ClassTimeTable.timeTable[i]=new TimeTable();
                ClassTimeTable.timeTable[i].setDay(day);
                dbg("day"+day);
                dbg("dayWiseGroup.get(day).size()"+dayWiseGroup.get(day).size());
                ClassTimeTable.timeTable[i].period=new Period[dayWiseGroup.get(day).size()];
                int j=0;
                for(DBRecord periodRecords: dayWiseGroup.get(day)){
                   ClassTimeTable.timeTable[i].period[j]=new Period();
                   ClassTimeTable.timeTable[i].period[j].setPeriodNumber(periodRecords.getRecord().get(3).trim());
                   ClassTimeTable.timeTable[i].period[j].setSubjectID(periodRecords.getRecord().get(4).trim());
                   String l_subjectName=bs.getSubjectName(ClassTimeTable.timeTable[i].period[j].getSubjectID(), instituteID, session, dbSession, inject);
                   ClassTimeTable.timeTable[i].period[j].setSubjectName(l_subjectName);
                   ClassTimeTable.timeTable[i].period[j].setTeacherID(periodRecords.getRecord().get(5).trim());
                   String l_teacherName=bs.getTeacherName(ClassTimeTable.timeTable[i].period[j].getTeacherID(), instituteID, session, dbSession, inject);
                   ClassTimeTable.timeTable[i].period[j].setTeacherName(l_teacherName);
                   j++;
                  dbg("periodArraySize"+j);
                }
                 Arrays.sort(ClassTimeTable.timeTable[i].getPeriod());
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
        dbg("inside Class timeTable buildJsonResFromBO");
        BusinessService bs=inject.getBusinessService(session);
        IPDataService pds=inject.getPdataservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassTimeTable> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        ClassTimeTable ClassTimeTable =reqBody.get();
        JsonArrayBuilder period=Json.createArrayBuilder();
        JsonArrayBuilder timeTable=Json.createArrayBuilder();
        String standard=ClassTimeTable.getStandard();
        String section=ClassTimeTable.getSection();
        
        String l_operation=request.getReqHeader().getOperation();
        dbg("l_operation"+l_operation);
        
        
        dbg("Time table size"+ClassTimeTable.timeTable.length);
        
           for(int i=0;i<ClassTimeTable.timeTable.length;i++){
               dbg("ClassTimeTable.timeTable[i].period"+ClassTimeTable.timeTable[i]);
               dbg("period size"+ClassTimeTable.timeTable[i].period.length);
               for(int j=0;j<ClassTimeTable.timeTable[i].period.length;j++){
                   
                   String periodNumber=ClassTimeTable.timeTable[i].period[j].getPeriodNumber();
                   String startTimeHour;
                   String startTimeMin;
                   String endTimeHour;
                   String endTimeMin;
                   if(!l_operation.equals("Create-Default")){
         
                   
                   String[] pkey={l_instituteID,standard,section,periodNumber};
                   ArrayList<String>periodlist= pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_PERIOD_MASTER",pkey);
                   
                   startTimeHour=periodlist.get(4).trim();
                   startTimeMin=periodlist.get(5).trim();
                   endTimeHour=periodlist.get(6).trim();
                   endTimeMin=periodlist.get(7).trim();
                   }
                   else
                   {   
                   startTimeHour=ClassTimeTable.timeTable[i].period[j].getStartHour();
                   startTimeMin=ClassTimeTable.timeTable[i].period[j].getStartMin();
                   endTimeHour=ClassTimeTable.timeTable[i].period[j].getEndHour();
                   endTimeMin=ClassTimeTable.timeTable[i].period[j].getEndMin();
                   }
                   period.add(Json.createObjectBuilder().add("periodNumber", ClassTimeTable.timeTable[i].period[j].getPeriodNumber())
                                                        .add("subjectID", ClassTimeTable.timeTable[i].period[j].getSubjectID())
                                                        .add("subjectName",ClassTimeTable.timeTable[i].period[j].getSubjectName())
                                                        .add("teacherID", ClassTimeTable.timeTable[i].period[j].getTeacherID())
                                                        .add("teacherName",ClassTimeTable.timeTable[i].period[j].getTeacherName())
                                                        .add("startTime", Json.createObjectBuilder().add("hour", startTimeHour).add("min", startTimeMin))
                                                        .add("endTime", Json.createObjectBuilder().add("hour", endTimeHour).add("min", endTimeMin)));
                                                  
                   
                }
                timeTable.add(Json.createObjectBuilder().add("day", ClassTimeTable.timeTable[i].getDay())
                                                        .add("dayNumber", bs.getDayNumber(ClassTimeTable.timeTable[i].getDay()))
                                                        .add("period",period));
           }

              
                 
//            body=Json.createObjectBuilder()
//                                           .add("standard", ClassTimeTable.getStandard())
//                                           .add("section", ClassTimeTable.getSection())
//                                           .add("timeTable", timeTable)
//                                           .build();

              body=Json.createObjectBuilder()
                                           .add("class", ClassTimeTable.getStandard()+"/"+ClassTimeTable.getSection())
                                           .add("timeTable", timeTable)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of Class timeTable buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside Class timeTable--->businessValidation");    
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
       dbg("end of Class timeTable--->businessValidation"); 
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
        dbg("inside Class timeTable master mandatory validation");
        RequestBody<ClassTimeTable> reqBody = request.getReqBody();
        ClassTimeTable ClassTimeTable =reqBody.get();
        
//         if(ClassTimeTable.getStandard()==null){
//             status=false;  
//             errhandler.log_app_error("BS_VAL_002","Standard");  
//         }
//         if(ClassTimeTable.getSection()==null){
//             status=false;  
//             errhandler.log_app_error("BS_VAL_002","Section");  
//         }
          
        dbg("end of Class timeTable master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside Class timeTable masterDataValidation");
             RequestBody<ClassTimeTable> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             ClassTimeTable classTimeTable =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_standard=classTimeTable.getStandard();
             String l_section=classTimeTable.getSection();
             
             if(!bsv.standardSectionValidation(l_standard,l_section, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Standard or section");
             }
             
            
            dbg("end of Class timeTable masterDataValidation");
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
        RequestBody<ClassTimeTable> reqBody = request.getReqBody();
        ClassTimeTable ClassTimeTable =reqBody.get();
        
        try{
            
            dbg("inside Class timeTable detailMandatoryValidation");
           
            if(ClassTimeTable.getTimeTable()==null||ClassTimeTable.getTimeTable().length==0){
                status=false;
                errhandler.log_app_error("BS_VAL_002","TimeTable");
            }else{
                
                for(int i=0;i<ClassTimeTable.getTimeTable().length;i++){
                    
                    if(ClassTimeTable.getTimeTable()[i].getDay()==null||ClassTimeTable.getTimeTable()[i].getDay().isEmpty()){
                        status=false; 
                        errhandler.log_app_error("BS_VAL_002","Day");
                    }
                   
                    if(ClassTimeTable.getTimeTable()[i].getPeriod()==null||ClassTimeTable.getTimeTable()[i].getPeriod().length==0){
                        status=false; 
                        errhandler.log_app_error("BS_VAL_002","Period");
                    }else{
                        
                        for(int j=0;j<ClassTimeTable.getTimeTable()[i].getPeriod().length;j++){
                            
                            if(ClassTimeTable.getTimeTable()[i].getPeriod()[j].getPeriodNumber()==null||ClassTimeTable.getTimeTable()[i].getPeriod()[j].getPeriodNumber().isEmpty()){
                               status=false; 
                               errhandler.log_app_error("BS_VAL_002","PeriodNumber");
                            }
                            if(ClassTimeTable.getTimeTable()[i].getPeriod()[j].getSubjectID()==null||ClassTimeTable.getTimeTable()[i].getPeriod()[j].getSubjectID().isEmpty()){
                               status=false; 
                               errhandler.log_app_error("BS_VAL_002","SubjectID");
                            }
                            if(ClassTimeTable.getTimeTable()[i].getPeriod()[j].getTeacherID()==null||ClassTimeTable.getTimeTable()[i].getPeriod()[j].getTeacherID().isEmpty()){
                               status=false; 
                               errhandler.log_app_error("BS_VAL_002","TeacherID");
                            }
                           
                        }
                    }
                }
            }
            
           
            
           dbg("end of Class timeTable detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         try{
             dbg("inside Class timeTable detailDataValidation");
             RequestBody<ClassTimeTable> reqBody = request.getReqBody();
             ClassTimeTable ClassTimeTable =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_standard=ClassTimeTable.getStandard();
             String l_section=ClassTimeTable.getSection();
             BSValidation bsv=inject.getBsv(session);

             int timeTableSize=ClassTimeTable.getTimeTable().length;
             
             if(!bsv.timeTableSizeValidation(timeTableSize, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Time Table Size");
             }else{
                 
                 for(int i=0;i<ClassTimeTable.getTimeTable().length;i++){
                    String day=ClassTimeTable.getTimeTable()[i].getDay();
                    int periodSize=ClassTimeTable.getTimeTable()[i].getPeriod().length;
                     
                    if(!bsv.dayValidation(day, session, dbSession, inject)){
                        status=false;
                        errhandler.log_app_error("BS_VAL_003","Day");
                    }
                   
                    if(!bsv.periodSizeValidation(periodSize, session, dbSession, inject)){
                        status=false;
                        errhandler.log_app_error("BS_VAL_003","PeriodSize");
                    }else{
                        
                        for(int j=0;j<ClassTimeTable.getTimeTable()[i].getPeriod().length;j++){
                            
                            String l_periodNumber=ClassTimeTable.getTimeTable()[i].getPeriod()[j].getPeriodNumber();
                            String l_subjectID=ClassTimeTable.getTimeTable()[i].getPeriod()[j].getSubjectID();
                            String l_teacherID=ClassTimeTable.getTimeTable()[i].getPeriod()[j].getTeacherID();
                                    
                            if(!bsv.periodNumberValidation(l_periodNumber, session, dbSession, inject)){
                               status=false;
                               errhandler.log_app_error("BS_VAL_003","PeriodNumber");
                            }
                            if(!bsv.subjectIDValidation(l_subjectID, l_instituteID, session, dbSession, inject)){
                                status=false;
                                errhandler.log_app_error("BS_VAL_003","SubjectID");
                            }
                            if(!bsv.teacherIDValidation(l_teacherID, l_instituteID, session, dbSession, inject)){
                                status=false;
                                errhandler.log_app_error("BS_VAL_003","TeacherID");
                            }
                        }
                    } 
                }
            }
             ArrayList<String> dayList=new ArrayList();
             for(int i=0;i<ClassTimeTable.getTimeTable().length;i++){
                 String day=ClassTimeTable.getTimeTable()[i].getDay();
                 
                 if(!dayList.contains(day)){

                     dayList.add(day);

                }else{
                    status=false;
                    errhandler.log_app_error("BS_VAL_031",day+"Table");
                    throw new BSValidationException("BSValidationException");

                }
             }
             
             
             //Map<String,DBRecord> l_periodMap=null;
            //IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IPDataService pds=inject.getPdataservice();
            IBDProperties i_db_properties=session.getCohesiveproperties();
                
                //l_periodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_PERIOD_MASTER", session, dbSession);
               Map<String,ArrayList<String>>l_periodMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_PERIOD_MASTER",session,dbSession);
               Map<String,List<ArrayList<String>>> periodList;
                dbg("l_periodList size"+l_periodMap.size());
                BusinessService bs=inject.getBusinessService(session);
                
                String masterVersion=bs.getMaxVersionOfTheClass(l_instituteID, l_standard, l_section, session, dbSession, inject);
                int versionIndex=bs.getVersionIndexOfTheTable("IVW_PERIOD_MASTER", "INSTITUTE", session, dbSession, inject);
                
                
               periodList=l_periodMap.values().stream().filter(rec->rec.get(1).trim().equals(l_standard)&&rec.get(2).trim().equals(l_section)&&rec.get(versionIndex).trim().equals(masterVersion)).collect(Collectors.groupingBy(rec->rec.get(3).trim()));
               
                
                //List<DBRecord>periodListRecord= l_periodMap.values().stream().filter(rec->rec.getRecord().get(1).trim().equals(l_standard)&&rec.getRecord().get(2).trim().equals(l_section)).collect(Collectors.toList());
                dbg("periodList size"+periodList.size());
                 IDBReadBufferService readBuffer=inject.getDBReadBufferService();
                  Map<String,DBRecord>l_timeTableMap =null;
                 try
                       {     
                         l_timeTableMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TimeTable","INSTITUTE","IVW_SUBJECT_DETAILS", session, dbSession);
                       }
                  catch(DBValidationException ex){
                         dbg("exception in view operation"+ex);
                         if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                         session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                         session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                         // session.getErrorhandler().log_app_error("BS_VAL_013","Period for the Class"  );
                         //throw new BSValidationException("BSValidationException");
                         }else{
                               throw ex;
                              }
                           }
                  
             for(int i=0;i<ClassTimeTable.getTimeTable().length;i++){
                 String day=ClassTimeTable.getTimeTable()[i].getDay();
                 
                 ArrayList<String> PeriodList=new ArrayList();
                 for(int j=0;j<ClassTimeTable.getTimeTable()[i].getPeriod().length;j++){
                 
                 String periodNumber=ClassTimeTable.getTimeTable()[i].getPeriod()[j].periodNumber;
                 
                 if(!PeriodList.contains(periodNumber)){
                      
                     if(periodList.containsKey(periodNumber))
                     {    
                       PeriodList.add(periodNumber);
                       String teacherID=ClassTimeTable.getTimeTable()[i].getPeriod()[j].getTeacherID();
                       String teacherName=ClassTimeTable.getTimeTable()[i].getPeriod()[j].getTeacherName();
                       
                       
                       List<DBRecord>  l_teacher_timeTable=null;
                      
                           
                       //  Map<String,DBRecord>l_timeTableMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TimeTable","INSTITUTE","IVW_SUBJECT_DETAILS", session, dbSession);
                      
                       if(l_timeTableMap!=null && !l_timeTableMap.isEmpty()) 
                       l_teacher_timeTable=l_timeTableMap.values().stream().filter(rec->rec.getRecord().get(5).trim().equals(teacherID) &&rec.getRecord().get(3).trim().equals(day)  &&!((rec.getRecord().get(0).trim()+rec.getRecord().get(1).trim()).equals(l_standard+l_section))).collect(Collectors.toList());
                      
                        
         
                        if (l_teacher_timeTable!=null && !l_teacher_timeTable.isEmpty())
                        {
                            String[] l_pkey={l_instituteID,l_standard,l_section,periodNumber};
                            DBRecord periodRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_PERIOD_MASTER", l_pkey, session, dbSession);
             
                               int currentStartTimeinmin=Integer.parseInt(periodRecord.getRecord().get(4).trim())*60+Integer.parseInt(periodRecord.getRecord().get(5).trim());
                               int currentEndTimeinmin=Integer.parseInt(periodRecord.getRecord().get(6).trim())*60+Integer.parseInt(periodRecord.getRecord().get(7).trim());
                            dbg("currentStartTimeinmin-->"+currentStartTimeinmin);
                            dbg("currentEndTimeinmin-->"+currentEndTimeinmin);
                            
                               for(int k=0;k<l_teacher_timeTable.size();k++)
                            {
                            dbg("l_pkey1-->"+l_instituteID+"~"+l_teacher_timeTable.get(k).getRecord().get(0)+"~"+l_teacher_timeTable.get(k).getRecord().get(1)+"~"+l_teacher_timeTable.get(k).getRecord().get(2));
                                String[] l_pkey1={l_instituteID,l_teacher_timeTable.get(k).getRecord().get(0),l_teacher_timeTable.get(k).getRecord().get(1),l_teacher_timeTable.get(k).getRecord().get(2)};
                            
                            DBRecord periodRecord1=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_PERIOD_MASTER", l_pkey1, session, dbSession);
                                int existingStartTimeinmin=Integer.parseInt(periodRecord1.getRecord().get(4).trim())*60+Integer.parseInt(periodRecord1.getRecord().get(5).trim());
                               int existingEndTimeinmin=Integer.parseInt(periodRecord1.getRecord().get(6).trim())*60+Integer.parseInt(periodRecord1.getRecord().get(7).trim());
                             dbg("existingStartTimeinmin-->"+existingStartTimeinmin);
                            dbg("existingStartTimeinmin-->"+existingEndTimeinmin);
                            if(currentEndTimeinmin>existingStartTimeinmin &&currentStartTimeinmin<existingStartTimeinmin)
                             {   
                              dbg("Cond1 satisfied");
                                 status=false;
                              errhandler.log_app_error("BS_VAL_047",teacherName+" "+"Current Period is "+day+periodNumber+" Allocated Period is" +l_teacher_timeTable.get(k).getRecord().get(0)+"/"+l_teacher_timeTable.get(k).getRecord().get(1)+l_teacher_timeTable.get(k).getRecord().get(3)+" "+l_teacher_timeTable.get(k).getRecord().get(2));
                                throw new BSValidationException("BSValidationException");
                              }
                            if(currentStartTimeinmin<existingEndTimeinmin && currentEndTimeinmin>=existingEndTimeinmin)
                             {   
                                  dbg("Cond2 satisfied");
                               status=false;
                              errhandler.log_app_error("BS_VAL_047",teacherName+" "+"Current Period is "+day+periodNumber+" Allocated Period is" +l_teacher_timeTable.get(k).getRecord().get(0)+"/"+l_teacher_timeTable.get(k).getRecord().get(1)+l_teacher_timeTable.get(k).getRecord().get(3)+" "+l_teacher_timeTable.get(k).getRecord().get(2));
                               throw new BSValidationException("BSValidationException");
                              }
                            
                            if(currentStartTimeinmin<=existingStartTimeinmin && currentEndTimeinmin>=existingEndTimeinmin)
                             {   
                               status=false;
                              errhandler.log_app_error("BS_VAL_047",teacherName+" "+"Current Period is "+day+periodNumber+" Allocated Period is" +l_teacher_timeTable.get(k).getRecord().get(0)+"/"+l_teacher_timeTable.get(k).getRecord().get(1)+l_teacher_timeTable.get(k).getRecord().get(3)+" "+l_teacher_timeTable.get(k).getRecord().get(2));
                                throw new BSValidationException("BSValidationException");
                              }
                            
                            if(currentStartTimeinmin>=existingStartTimeinmin && currentEndTimeinmin<=existingEndTimeinmin)
                             {   
                               status=false;
                              errhandler.log_app_error("BS_VAL_047",teacherName+" "+"Current Period is "+day+periodNumber+" Allocated Period is" +l_teacher_timeTable.get(k).getRecord().get(0)+"/"+l_teacher_timeTable.get(k).getRecord().get(1)+l_teacher_timeTable.get(k).getRecord().get(3)+" "+l_teacher_timeTable.get(k).getRecord().get(2));
                                throw new BSValidationException("BSValidationException");
                              }
                            
                            
                            
                            }   
                            
                            
                        }   
                       
                       
                       
                     }
                     else{
                    status=false;
                    errhandler.log_app_error("BS_VAL_045",day+" Table"+" "+periodNumber);
                    throw new BSValidationException("BSValidationException");
                     }
                     

                }else{
                    status=false;
                    errhandler.log_app_error("BS_VAL_031",day+" Table"+" "+periodNumber);
                    throw new BSValidationException("BSValidationException");

                }
                 }
             }
             
             
            
            dbg("end of Class timeTable detailDataValidation");
        } 
         catch(BSValidationException ex)
         {
            throw ex; 
         }   
         catch (DBProcessingException ex) {
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
        dbg("inside ClassTimeTable--->getExistingAudit") ;
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
               dbg("inside ClassTimeTable--->getExistingAudit--->Service--->UserTimeTable");  
               RequestBody<ClassTimeTable> ClassTimeTableBody = request.getReqBody();
               ClassTimeTable classTimeTable =ClassTimeTableBody.get();
               String l_standard=classTimeTable.getStandard();
               String l_section=classTimeTable.getSection();
               String[] l_pkey={l_standard,l_section};
               DBRecord l_ClassTimeTableRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS","CLASS_TIMETABLE_MASTER", l_pkey, session, dbSession);
               exAudit.setAuthStatus(l_ClassTimeTableRecord.getRecord().get(7).trim());
               exAudit.setMakerID(l_ClassTimeTableRecord.getRecord().get(2).trim());
               exAudit.setRecordStatus(l_ClassTimeTableRecord.getRecord().get(6).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_ClassTimeTableRecord.getRecord().get(8).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of ClassTimeTable--->getExistingAudit") ;
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
    
//    public  void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
//     
//     
//     try{
//        dbg("inside relationshipProcessing") ;
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        RequestBody<ClassTimeTable> ClassTimeTableBody = request.getReqBody();
//        ClassTimeTable classTimeTable =ClassTimeTableBody.get();
//        String l_standard=classTimeTable.getStandard();
//        String l_section=classTimeTable.getSection(); 
//        String  versionNumber=null;
//        DBRecord studentMasterRec;
//        
//        Map<String,DBRecord>l_classStudentMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS","CLASS_STUDENT_MAPPING", session, dbSession);
//        
//        Map<String,List<DBRecord>>l_studentGroup=l_classStudentMap.values().stream().collect(Collectors.groupingBy(rec->rec.getRecord().get(2).trim()));
//        
//        Iterator<String> studentIterator=l_studentGroup.keySet().iterator();
//        
//        while(studentIterator.hasNext()){
//            String l_studentID=studentIterator.next();
//            String[] l_pkey={l_studentID};
//        try{
//        
//            studentMasterRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_TIMETABLE_MASTER", l_pkey, session, dbSession);
//            versionNumber=studentMasterRec.getRecord().get(7).trim();
//            dbg("versionNumber"+versionNumber);
//        }catch(DBValidationException ex){
//            if(ex.toString().contains("DB_VAL_011")){
//                
//                exAudit.setRelatioshipOperation("C");
//                
//            }
//        }
//        
//        if(exAudit.getRelatioshipOperation().equals("C")){
//
//             buildRequestAndCallStudentTimeTable(1,"O",l_studentID);
//        }else if(exAudit.getRelatioshipOperation().equals("M")){
//            
//            buildRequestAndCallStudentTimeTable(Integer.parseInt(versionNumber)+1,"O",l_studentID);
//        }else if(exAudit.getRelatioshipOperation().equals("D")){
//            
//            buildRequestAndCallStudentTimeTable(Integer.parseInt(versionNumber)+1,"D",l_studentID);
//        }
//        
//        }
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
// }
//    
// private void buildRequestAndCallStudentTimeTable(int p_versionNumber,String p_recordStatus,String p_studentID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
//     
//        try{
//        dbg("inside buildRequestAndCallStudentTimeTable") ;   
//        dbg("versionNumber"+p_versionNumber);
//        dbg("p_recordStatus"+p_recordStatus);
//        RequestBody<ClassTimeTable> ClassTimeTableBody = request.getReqBody();
//        ClassTimeTable classTimeTable =ClassTimeTableBody.get(); 
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IStudentTimeTableService stt=inject.getStudentTimeTableService();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        JsonObject studentTimeTable;
//        String l_msgID=request.getReqHeader().getMsgID();
//        String l_correlationID=" ";
//        String l_service="StudentTimeTable";
//        String l_operation="AutoAuth";
//        JsonArray l_businessEntity=Json.createArrayBuilder().add(Json.createObjectBuilder().add("entityName", "studentID")
//                                                                                             .add("entityValue", p_studentID)).build();
//        String l_userID=request.getReqHeader().getUserID();
//        String l_source="cohesive_backend";
//        String l_status=" ";
//        String l_makerID=l_userID;
//        String l_checkerID=l_userID;
//        String dateformat=i_db_properties.getProperty("DATE_TIME_FORMAT");
//        SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
//        Date date = new Date();
//        String l_makerDateStamp=formatter.format(date);
//        String l_checkerDateStamp=formatter.format(date);
//        String l_authStatus="A";
//        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
//        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
//        JsonArrayBuilder period=Json.createArrayBuilder();
//        JsonArrayBuilder timeTable=Json.createArrayBuilder();
//        
//        for(int i=0;i<classTimeTable.timeTable.length;i++){
//               for(int j=0;j<classTimeTable.timeTable[i].period.length;j++){
//                   
//                   period.add(Json.createObjectBuilder().add("periodNumber", classTimeTable.timeTable[i].period[j].getPeriodNumber())
//                                                        .add("subjectID", classTimeTable.timeTable[i].period[j].getSubjectID())
//                                                        .add("teacherID", classTimeTable.timeTable[i].period[j].getTeacherID()));
//                                                     
//                }
//                timeTable.add(Json.createObjectBuilder().add("day", classTimeTable.timeTable[i].getDay())
//                                                        .add("period",period));
//           }
//
//
//          studentTimeTable=Json.createObjectBuilder().add("header", Json.createObjectBuilder()
//                                                        .add("msgID",l_msgID)
//                                                        .add("correlationID", l_correlationID)
//                                                        .add("service", l_service)
//                                                        .add("operation", l_operation)
//                                                        .add("businessEntity", l_businessEntity)
//                                                        .add("instituteID", l_instituteID)
//                                                        .add("status", l_status)
//                                                        .add("source", l_source)
//                                                        .add("userID",l_userID))
//                                                        .add("body",Json.createObjectBuilder()
//                                                        .add("studentID", p_studentID)
//                                                        .add("timeTable", timeTable))
//                                                        .add("auditLog",  Json.createObjectBuilder()
//                                                        .add("makerID", l_makerID)
//                                                        .add("checkerID", l_checkerID)
//                                                        .add("makerDateStamp", l_makerDateStamp)
//                                                        .add("checkerDateStamp", l_checkerDateStamp)
//                                                        .add("recordStatus", p_recordStatus)
//                                                        .add("authStatus", l_authStatus)
//                                                        .add("versionNumber", Integer.toString(p_versionNumber))
//                                                        .add("makerRemarks", l_makerRemarks)
//                                                        .add("checkerRemarks", l_checkerRemarks)).build();
//          
//          dbg("student timetable request"+studentTimeTable.toString());
//          dbg("before  timetable call");
//          stt.processing(studentTimeTable, session);
//          dbg("after studentmaster call");
//          
//          
//        dbg("end of buildRequestAndCallStudentMaster");  
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException(ex.toString());
//        }catch(BSValidationException ex){
//            throw ex;
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//        }
//          
// }   

    private void createDefault()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside class attendance--->createdefault");    
        BusinessService bs=inject.getBusinessService(session); 
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassTimeTable> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        ClassTimeTable classTimeTable =reqBody.get();
        String l_standard=classTimeTable.getStandard();
        String l_section=classTimeTable.getSection();
        IPDataService pds=inject.getPdataservice();
        // Map<String,DBRecord> l_periodMap=null;
         Map<String,List<ArrayList<String>>> periodList;
           try{
            //l_periodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_PERIOD_MASTER", session, dbSession);
           
               Map<String,ArrayList<String>>l_periodMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_PERIOD_MASTER",session,dbSession);
               dbg("l_periodList size"+l_periodMap.size());
               
               String masterVersion=bs.getMaxVersionOfTheClass(l_instituteID, l_standard, l_section, session, dbSession, inject);
                 int versionIndex=bs.getVersionIndexOfTheTable("IVW_PERIOD_MASTER", "INSTITUTE", session, dbSession, inject);
               
               periodList=l_periodMap.values().stream().filter(rec->rec.get(1).trim().equals(l_standard)&&rec.get(2).trim().equals(l_section)&&rec.get(versionIndex).trim().equals(masterVersion)).collect(Collectors.groupingBy(rec->rec.get(3).trim()));
                    
           }
           catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013","Period for the Class"  );
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
         
            
                            
            //List<DBRecord>periodListRecord= l_periodMap.values().stream().filter(rec->rec.getRecord().get(1).trim().equals(l_standard)&&rec.getRecord().get(2).trim().equals(l_section)).collect(Collectors.toList());
                dbg("periodList size"+periodList.size());
            
                classTimeTable.timeTable=new TimeTable[5];
            
                //Map<Integer,DBRecord>periodMap=new HashMap();
               for(int day=0;day<classTimeTable.timeTable.length;day++)
               {   
                   classTimeTable.timeTable[day]=new TimeTable();
                   switch(day)
                   {
                       case 0:   
                         classTimeTable.timeTable[day].setDay("Mon");
                         break;
                       case 1:   
                         classTimeTable.timeTable[day].setDay("Tue");
                         break;
                       case 2:
                         classTimeTable.timeTable[day].setDay("Wed");
                           
                         //classTimeTable.timeTable[day].day="Wed";
                         break;
                       case 3:
                         classTimeTable.timeTable[day].setDay("Thu");
                         //classTimeTable.timeTable[day].day="Thu";
                         break; 
                        case 4:   
                         classTimeTable.timeTable[day].setDay("Fri");
                         break; 
                          
                   }      
                   
                   Period periodArray[] = new Period[periodList.keySet().size()] ;
                   
                   Map<String,List<ArrayList<String>>>  sortedMap = periodList.entrySet().stream().sorted(comparingByKey()).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, HashMap::new));
                   
                   Iterator<String>periodIterator=sortedMap.keySet().iterator();
                  int i=0;
               while(periodIterator.hasNext()){
                     String l_periodNumber=periodIterator.next();
                    dbg("l_periodNumber"+l_periodNumber);
                   
                //for(int i=0;i<periodListRecord.size();i++){
                    
                   // DBRecord l_periodRecords=periodListRecord.get(i);
                    //String periodNumber=l_periodRecords.getRecord().get(3).trim();
                    Period period = new Period();
                    /*l_column_to_update.put("4", l_periodNo);
                l_column_to_update.put("5", l_startTimeHour);
                l_column_to_update.put("6", l_startTimeMin);
                l_column_to_update.put("7", l_endTimeHour);
                l_column_to_update.put("8", l_endTimeMin);
                l_column_to_update.put("10", l_noon);*/
                    
                    period.setPeriodNumber(l_periodNumber);
                    period.setStartHour(sortedMap.get(l_periodNumber).get(0).get(4).trim());
                    period.setStartMin(sortedMap.get(l_periodNumber).get(0).get(5).trim());
                    period.setEndMin(sortedMap.get(l_periodNumber).get(0).get(7).trim());
                    period.setEndHour(sortedMap.get(l_periodNumber).get(0).get(6).trim());
                    period.setSubjectID("");
                    period.setSubjectName("");
                    period.setTeacherID("");
                    period.setTeacherName("");
                    periodArray[i]=period;
                           //period.(l_periodRecords.getRecord().get(3).trim());
                    
                    i++;

                     // periodMap.put(Integer.parseInt(periodNumber), l_periodRecords);
                    }
             classTimeTable.timeTable[day].setPeriod(periodArray);
               }
                 
                    
                    
                    
          dbg("end of  completed teacher attendance--->view");   
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


    
    
    
    public void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
          dbg("inside stident timeTable create"); 
        try{
            Map<String,String> l_column_to_update;
        RequestBody<ClassTimeTable> reqBody = request.getReqBody();
        ClassTimeTable classTimeTable =reqBody.get();
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_standard=classTimeTable.getStandard();
        String l_section=classTimeTable.getSection();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        
       if(Integer.parseInt(l_versionNumber)==1)//Creation
       {  
         for(int i=0;i<classTimeTable.timeTable.length;i++){
            String l_day=classTimeTable.timeTable[i].getDay();
            
                   for(int j=0;j<classTimeTable.timeTable[i].period.length;j++){
                       String l_periodNumber=classTimeTable.timeTable[i].period[j].getPeriodNumber();
                       String l_subjectID=classTimeTable.timeTable[i].period[j].getSubjectID();
                       String p_teacherID=classTimeTable.timeTable[i].period[j].getTeacherID();
                       
                    //   dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS",48,l_standard,l_section,l_day,l_periodNumber,l_subjectID,p_teacherID,l_versionNumber);
           
                       dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TimeTable","INSTITUTE",19,l_standard,l_section,l_periodNumber,l_day,l_subjectID,p_teacherID);
  
                   }
                                 
               }
        
       }
       else if(Integer.parseInt(l_versionNumber)>1 && !l_recordStatus.equals("D") )//Modification
       {  
         
           for(int i=0;i<classTimeTable.timeTable.length;i++){
                                      
                   String l_day=classTimeTable.timeTable[i].getDay();
                   for(int j=0;j<classTimeTable.timeTable[i].period.length;j++){
                       
                       String l_periodNumber=classTimeTable.timeTable[i].period[j].getPeriodNumber();
                       String l_subjectID=classTimeTable.timeTable[i].period[j].getSubjectID();
                       String p_teacherID=classTimeTable.timeTable[i].period[j].getTeacherID();
                       
                     //  if(classTimeTable.timeTable[i].period[j].getOperation().equals("U")){
                       
                            l_column_to_update= new HashMap();
                            l_column_to_update.put("3", l_periodNumber);
                            l_column_to_update.put("4", l_day);
                            l_column_to_update.put("5", l_subjectID);
                            l_column_to_update.put("6", p_teacherID);
                            String[] l_primaryKey={l_standard,l_section,l_periodNumber,l_day};

                            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TimeTable","INSTITUTE","IVW_SUBJECT_DETAILS", l_primaryKey, l_column_to_update, session);                      
                      // }else{
                           
                        //   dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS",48,l_standard,l_section,l_day,l_periodNumber,l_subjectID,p_teacherID,l_versionumber);
                           
                       //}
                   }
            }
       
       }
       else if(l_recordStatus.equals("D")) //Deletion
       {   
       
       for(int i=0;i<classTimeTable.timeTable.length;i++){
            String l_day=classTimeTable.timeTable[i].getDay();
                   
                   for(int j=0;j<classTimeTable.timeTable[i].period.length;j++){
                       String l_periodNumber=classTimeTable.timeTable[i].period[j].getPeriodNumber();
                       String[] l_detailprimaryKey={l_standard,l_section,l_periodNumber,l_day,};
                        
                       dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TimeTable","INSTITUTE","IVW_SUBJECT_DETAILS",l_detailprimaryKey,session);
                      }
                   }                
        
       }
       
       
       
        
        }
        
        catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception".concat(ex.toString()));
        }
        
        
         
     }
}
