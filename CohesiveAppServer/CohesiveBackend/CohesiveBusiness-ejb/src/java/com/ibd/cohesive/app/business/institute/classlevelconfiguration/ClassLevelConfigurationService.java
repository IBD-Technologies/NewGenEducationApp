/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.classlevelconfiguration;

import com.ibd.businessViews.IClassLevelConfigurationService;
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
import java.util.Collections;
import java.util.HashMap;
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
 * @author IBD Technologies
 */
//@Local(IClassLevelConfigurationService.class)
@Remote(IClassLevelConfigurationService.class)
@Stateless
public class ClassLevelConfigurationService implements IClassLevelConfigurationService {
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public ClassLevelConfigurationService(){
        try {
            inject=new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession = new DBSession(session);
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
         if (session.getDebug()!=null)
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
    
    
    public JsonObject processing(JsonObject requestJson) throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException
    {
       boolean l_validation_status=true;
       boolean l_session_created_now=false;
       JsonObject jsonResponse=null;
       JsonObject clonedResponse=null;
       BusinessService  bs;
       Parsing parser;
       ExceptionHandler exHandler;
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
       dbg("inside ClassLevelConfigurationService--->processing");
       dbg("ClassLevelConfigurationService--->Processing--->I/P--->requestJson"+requestJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,requestJson);
       bs.requestlogging(request,requestJson, inject,session,dbSession);
       buildBOfromReq(requestJson);  
       RequestBody<ClassLevelConfiguration> reqBody = request.getReqBody();
       ClassLevelConfiguration classConfiguration =reqBody.get();
       l_lockKey=classConfiguration.getInstituteID();
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<IClassLevelConfigurationService>classConfigurationEJB=new BusinessEJB();
       classConfigurationEJB.set(this);
       exAudit=bs.getExistingAudit(classConfigurationEJB);
       
      if(!(bsv. businessServiceValidation(requestJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
      }
       if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");

       } 
      
      
       bs.businessServiceProcssing(request, exAudit, inject,classConfigurationEJB);
       

         if(l_session_created_now){
             jsonResponse= bs.buildSuccessResponse(requestJson, request, inject, session,classConfigurationEJB) ;
             tc.commit(session,dbSession);
             dbg("commit is called in  service");
        }
       dbg("ClassLevelConfigurationService--->Processing--->O/P--->jsonResponse"+jsonResponse);     
       dbg("End of classConfigurationService--->processing");     
       }catch(NamingException ex){
            dbg(ex);
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, requestJson,"BSProcessingException");
       }catch(BSValidationException ex){
          if(l_session_created_now){
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, requestJson,"BSValidationException");
          }else{
              throw ex;
          }
       }catch(DBValidationException ex){
           if(l_session_created_now){
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, requestJson,"DBValidationException");
          }else{
              throw ex;
          }
       }catch(DBProcessingException ex){
            dbg(ex);
            if(l_session_created_now){
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, requestJson,"DBProcessingException");
            }else{
              throw ex;
          }
       }catch(BSProcessingException ex){
           dbg(ex);
           if(l_session_created_now){
           exHandler = inject.getErrorHandle(session);
           jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, requestJson,"BSProcessingException");
          }else{
              throw ex;
          }
      }finally{
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
//            if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
          clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes  
//BSProcessingException ex=new BSProcessingException("response contains special characters");
  //              dbg(ex);
    //            
      //          session.clearSessionObject();
        //       dbSession.clearSessionObject();
          //     throw ex;
               
           // }
            session.clearSessionObject();
            dbSession.clearSessionObject();
           }
        }
        
     return clonedResponse;    
    }
    
    @Override
    public JsonObject processing(JsonObject p_request,CohesiveSession session)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException
    {
    CohesiveSession tempSession = this.session;
    try{
        this.session=session;
       return processing(p_request);
     }catch(DBValidationException ex){
         //dbg(ex);        
        throw ex;
        }catch(BSValidationException ex){
         //dbg(ex);        
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
 }

    private  void buildBOfromReq(JsonObject p_request)throws BSProcessingException,DBProcessingException,BSValidationException{
       
       try{
       dbg("inside ClassLevelConfigurationService--->buildBOfromReq"); 
       dbg("ClassLevelConfigurationService--->buildBOfromReq--->I/P--->p_request"+p_request.toString()); 
       RequestBody<ClassLevelConfiguration> reqBody = new RequestBody<ClassLevelConfiguration>(); 
       ClassLevelConfiguration classConfiguration = new ClassLevelConfiguration();
       BSValidation bsv=inject.getBsv(session);
       JsonObject l_body=p_request.getJsonObject("body");
       String l_operation=request.getReqHeader().getOperation();
       classConfiguration.setInstituteID(l_body.getString("instituteID"));
       String l_class=l_body.getString("Class");
       bsv.classValidation(l_class,session);
       classConfiguration.setStandard(l_class.split("/")[0]);
       classConfiguration.setSection(l_class.split("/")[1]);
       
       if(!(l_operation.equals("View"))){
            
//            JsonArray l_standardArray=l_body.getJsonArray("StandardMaster");
            classConfiguration.setTeacherID(l_body.getString("teacherID"));
            classConfiguration.setAttendance(l_body.getString("attendance"));
            JsonArray l_periodArray=l_body.getJsonArray("periodTimings");
            
//            classConfiguration.standardMaster=new StandardMaster[l_standardArray.size()];
            
//            for(int i=0;i<l_standardArray.size();i++){
//                JsonObject l_standardObject=l_standardArray.getJsonObject(i);
//                classConfiguration.standardMaster[i]=new StandardMaster();
//                classConfiguration.standardMaster[i].setStandard(l_standardObject.getString("standard"));
//                classConfiguration.standardMaster[i].setSection(l_standardObject.getString("section"));
//                String l_class=l_standardObject.getString("class");
//                bsv.classValidation(l_class,session);
//                classConfiguration.standardMaster[i].setStandard(l_class.split("/")[0]);
//                classConfiguration.standardMaster[i].setSection(l_class.split("/")[1]);
//                classConfiguration.standardMaster[i].setTeacherID(l_standardObject.getString("teacherID"));
                
//            }
            classConfiguration.periodTimings=new PeriodTimings[l_periodArray.size()];
            
            for(int i=0;i<l_periodArray.size();i++){
                JsonObject l_periodObject=l_periodArray.getJsonObject(i);
                classConfiguration.periodTimings[i]=new PeriodTimings();
                classConfiguration.periodTimings[i].setPeriodNumber(l_periodObject.getString("periodNumber"));
                JsonObject l_startTimeObject=l_periodObject.getJsonObject("startTime");
                String l_startTimeHour=l_startTimeObject.getString("hour");
                String l_startTimeMin=l_startTimeObject.getString("min");
                classConfiguration.periodTimings[i].setStartTimeHour(l_startTimeHour);
                classConfiguration.periodTimings[i].setStartTimeMin(l_startTimeMin);
                JsonObject l_endTimeObject=l_periodObject.getJsonObject("endTime");
                String l_endTimeHour=l_endTimeObject.getString("hour");
                String l_endTimeMin=l_endTimeObject.getString("min");
                classConfiguration.periodTimings[i].setEndTimeHour(l_endTimeHour);
                classConfiguration.periodTimings[i].setEndTimeMin(l_endTimeMin);
                classConfiguration.periodTimings[i].setNoon(l_periodObject.getString("noon"));
            }
            
            
        }
       reqBody.set(classConfiguration);
      request.setReqBody(reqBody);
      dbg("End of ClassLevelConfigurationService--->buildBOfromReq");
      }catch(BSValidationException ex){
         throw ex;
        }catch(Exception ex){
          dbg(ex);
          throw new BSProcessingException("BodyParsingException"+ex.toString());
        }
   }
    
  
     public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
        try{
        dbg("Inside ClassLevelConfigurationService--->create"); 
        RequestBody<ClassLevelConfiguration> reqBody = request.getReqBody();
        ClassLevelConfiguration classConfiguration =reqBody.get();
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
        
        String l_instituteID=classConfiguration.getInstituteID();
        
//        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",86,l_instituteID,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
       
//        for(int i=0;i<classConfiguration.standardMaster.length;i++){
            
            String l_standard=classConfiguration.getStandard();
            String l_section=classConfiguration.getSection();
            String l_teacherID=classConfiguration.getTeacherID();
            String l_attendance=classConfiguration.getAttendance();
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",18,l_instituteID,l_standard,l_section,l_teacherID,l_versionNumber,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_makerRemarks,l_checkerRemarks,l_attendance);
//        }
        
        for(int i=0;i<classConfiguration.periodTimings.length;i++){
            
//            String l_standard=classConfiguration.periodTimings[i].getStandard();
//            String l_section=classConfiguration.periodTimings[i].getSection();
            String l_periodNo=classConfiguration.periodTimings[i].getPeriodNumber();
            String l_startTimeHour=classConfiguration.periodTimings[i].getStartTimeHour();
            String l_startTimeMin=classConfiguration.periodTimings[i].getStartTimeMin();
            String l_endTimeHour=classConfiguration.periodTimings[i].getEndTimeHour();
            String l_endTimeMin=classConfiguration.periodTimings[i].getEndTimeMin();
            String l_noon=classConfiguration.periodTimings[i].getNoon();
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",54,l_instituteID,l_standard,l_section,l_periodNo,l_startTimeHour,l_startTimeMin,l_endTimeHour,l_endTimeMin,l_versionNumber,l_noon);
            
        }
        
       

        dbg("End of ClassLevelConfigurationService--->create");
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
            dbg("Inside ClassLevelConfigurationService--->update"); 
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<ClassLevelConfiguration> reqBody = request.getReqBody();
            ClassLevelConfiguration classConfiguration =reqBody.get();
            String l_instituteID=classConfiguration.getInstituteID();
            String l_standard=classConfiguration.getStandard();
            String l_section=classConfiguration.getSection();
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
            Map<String,String>  l_column_to_update=new HashMap();
            l_column_to_update.put("7", l_checkerID);
            l_column_to_update.put("9", l_checkerDateStamp);
            l_column_to_update.put("11", l_authStatus);
            l_column_to_update.put("13", l_checkerRemarks);
             
             String[] l_primaryKey={l_instituteID,l_standard,l_section};
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_primaryKey, l_column_to_update, session);

             dbg("End of ClassLevelConfigurationService--->update");          
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
        dbg("Inside ClassLevelConfigurationService--->fullUpdate");
        IDBTransactionService dbts=inject.getDBTransactionService();
        IPDataService pds=inject.getPdataservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassLevelConfiguration> reqBody = request.getReqBody();
        ClassLevelConfiguration classConfiguration =reqBody.get();
        String l_instituteID=classConfiguration.getInstituteID();
        String l_standard=classConfiguration.getStandard();
        String l_section=classConfiguration.getSection();
        String l_teacherID=classConfiguration.getTeacherID();
        String l_attendance=classConfiguration.getAttendance();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        
        
            l_column_to_update=new HashMap();
            l_column_to_update.put("4", l_teacherID);
            l_column_to_update.put("8", l_makerDateStamp);
            l_column_to_update.put("12", l_makerRemarks);
            l_column_to_update.put("14", l_attendance);
        
            String[] l_primaryKey={l_instituteID,l_standard,l_section};
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_primaryKey, l_column_to_update, session);
         
//            IDBReadBufferService readBuffer = inject.getDBReadBufferService();
//            Map<String,DBRecord>l_standardMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE", "IVW_STANDARD_MASTER", session, dbSession);
//            Map<String,DBRecord>l_periodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE", "IVW_PERIOD_MASTER", session, dbSession);
//            setOperationsOfTheRecord("IVW_STANDARD_MASTER",l_standardMap);
              Map<String,ArrayList<String>>l_periodMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE", "IVW_PERIOD_MASTER", session, dbSession);
              setOperationsOfTheRecord("IVW_PERIOD_MASTER",l_periodMap);            
                        
                        
                        
//        for(int i=0;i<classConfiguration.standardMaster.length;i++){
//            
//            String l_standard=classConfiguration.standardMaster[i].getStandard();
//            String l_section=classConfiguration.standardMaster[i].getSection();
//            String l_teacherID=classConfiguration.standardMaster[i].getTeacherID(); 
//            
//            if(classConfiguration.standardMaster[i].getOperation().equals("U")){
//            
//                l_column_to_update= new HashMap();
//                l_column_to_update.put("2", l_standard);
//                l_column_to_update.put("3", l_section);
//                l_column_to_update.put("4", l_teacherID);
//                String[] l_standardPKey={l_instituteID,l_standard,l_section};
//                
//                dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER",l_standardPKey,l_column_to_update,session);
//  
//            }else{
//                
//                dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",18,l_instituteID,l_standard,l_section,l_teacherID,l_versionNumber);
//                
//            }
//            
//         }
        
        for(int i=0;i<classConfiguration.periodTimings.length;i++){
            
//            String l_standard=classConfiguration.periodTimings[i].getStandard();
//            String l_section=classConfiguration.periodTimings[i].getSection();
            String l_periodNo=classConfiguration.periodTimings[i].getPeriodNumber();
            String l_startTimeHour=classConfiguration.periodTimings[i].getStartTimeHour();
            String l_startTimeMin=classConfiguration.periodTimings[i].getStartTimeMin();
            String l_endTimeHour=classConfiguration.periodTimings[i].getEndTimeHour();
            String l_endTimeMin=classConfiguration.periodTimings[i].getEndTimeMin(); 
            String l_noon=classConfiguration.periodTimings[i].getNoon();
            
            if(classConfiguration.periodTimings[i].getOperation().equals("U")){
            
                l_column_to_update= new HashMap();
//                l_column_to_update.put("2", l_standard);
//                l_column_to_update.put("3", l_section);
                l_column_to_update.put("4", l_periodNo);
                l_column_to_update.put("5", l_startTimeHour);
                l_column_to_update.put("6", l_startTimeMin);
                l_column_to_update.put("7", l_endTimeHour);
                l_column_to_update.put("8", l_endTimeMin);
                l_column_to_update.put("10", l_noon);
                String[] l_periodPKey={l_instituteID,l_standard,l_section,l_periodNo};

                dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_PERIOD_MASTER",l_periodPKey,l_column_to_update,session);
            }else{
                
                
                dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",54,l_instituteID,l_standard,l_section,l_periodNo,l_startTimeHour,l_startTimeMin,l_endTimeHour,l_endTimeMin,l_versionNumber);
            }
        }
//        ArrayList<String>standardList=getRecordsToDelete("IVW_STANDARD_MASTER",l_standardMap);      
        ArrayList<String>periodList=getRecordsToDelete("IVW_PERIOD_MASTER",l_periodMap); 
       
//        for(int i=0;i<standardList.size();i++){
//            String pkey=standardList.get(i);
//            deleteRecordsInTheList("IVW_STANDARD_MASTER",pkey);
//            
//        }
        
        for(int i=0;i<periodList.size();i++){
            String pkey=periodList.get(i);
            deleteRecordsInTheList("IVW_PERIOD_MASTER",pkey);
            
        }
                   
        dbg("end of ClassLevelConfigurationService--->fullUpdate");                
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
     
     
     private void setOperationsOfTheRecord(String tableName,Map<String,ArrayList<String>>tableMap)throws DBValidationException,DBProcessingException,BSProcessingException{
         
         try{
            dbg("inside getOperationsOfTheRecord"); 
            RequestBody<ClassLevelConfiguration> reqBody = request.getReqBody();
            ClassLevelConfiguration classConfiguration =reqBody.get();
            String instituteID=classConfiguration.getInstituteID();
            String l_standard=classConfiguration.getStandard();
            String l_section=classConfiguration.getSection();
            dbg("tableName"+tableName);
            
            
            switch(tableName){
                
//                case "IVW_STANDARD_MASTER":  
//                
//                         for(int i=0;i<classConfiguration.standardMaster.length;i++){
//                                String standard=classConfiguration.standardMaster[i].getStandard();
//                                String section=classConfiguration.standardMaster[i].getSection();
//                                String l_pkey=instituteID+"~"+standard+"~"+section;
//                               if(tableMap.containsKey(l_pkey)){
//
//                                    classConfiguration.standardMaster[i].setOperation("U");
//                                }else{
//
//                                    classConfiguration.standardMaster[i].setOperation("C");
//                                }
//                         } 
//                  break;      
                  
                  
                  case "IVW_PERIOD_MASTER":  
                
                         for(int i=0;i<classConfiguration.periodTimings.length;i++){
//                                String standard=classConfiguration.periodTimings[i].getStandard();
//                                String section=classConfiguration.periodTimings[i].getSection();
                                String periodNumber=classConfiguration.periodTimings[i].getPeriodNumber();
                                String l_pkey=instituteID+"~"+l_standard+"~"+l_section+"~"+periodNumber;
                               if(tableMap.containsKey(l_pkey)){

                                    classConfiguration.periodTimings[i].setOperation("U");
                                }else{

                                    classConfiguration.periodTimings[i].setOperation("C");
                                }
                         } 
                  break;
                  
            }
                  
            
             
            dbg("end of getOperationsOfTheRecord"); 
//         }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//             throw new DBProcessingException(ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
         
     }    

private ArrayList<String>getRecordsToDelete(String tableName,Map<String,ArrayList<String>>tableMap)throws DBValidationException,DBProcessingException,BSProcessingException{
         
         try{
           
           dbg("inside getRecordsToDelete");  
           RequestBody<ClassLevelConfiguration> reqBody = request.getReqBody();
           ClassLevelConfiguration classConfiguration =reqBody.get();
           ArrayList<String>recordsToDelete=new ArrayList();
//           Iterator<String>keyIterator=tableMap.keySet().iterator();
           String instituteID=classConfiguration.getInstituteID();   
           String standard=classConfiguration.getStandard();
            String section=classConfiguration.getSection();
           List<ArrayList<String>>filteredRecords=tableMap.values().stream().filter(rec->rec.get(0).trim().equals(instituteID)&&rec.get(1).trim().equals(standard)&&rec.get(2).trim().equals(section)).collect(Collectors.toList());

           
           
           
           dbg("tableName"+tableName);
           switch(tableName){
           
//                 case "IVW_STANDARD_MASTER":   
//                   
////                   while(keyIterator.hasNext()){
//                     for(int j=0;j<filteredRecords.size();j++){
//                        String l_standard=filteredRecords.get(j).getRecord().get(1).trim();
//                        String l_section=filteredRecords.get(j).getRecord().get(2).trim();
//                        String tablePkey=instituteID+"~"+l_standard+"~"+l_section;
//                        dbg("tablePkey"+tablePkey);
//                        boolean recordExistence=false;
//
//                        for(int i=0;i<classConfiguration.standardMaster.length;i++){
//                                String standard=classConfiguration.standardMaster[i].getStandard();
//                                String section=classConfiguration.standardMaster[i].getSection(); 
//                           String l_requestPkey=instituteID+"~"+standard+"~"+section;
//                           dbg("l_requestPkey"+l_requestPkey);
//                           if(tablePkey.equals(l_requestPkey)){
//                               recordExistence=true;
//                           }
//                        }   
//                        if(!recordExistence){
//                            recordsToDelete.add(tablePkey);
//                        }
//
//                    }
//                   break;
                   
                   
                case "IVW_PERIOD_MASTER":   
                   
//                   while(keyIterator.hasNext()){
                        for(int j=0;j<filteredRecords.size();j++){
                        String l_standard=filteredRecords.get(j).get(1).trim();
                        String l_section=filteredRecords.get(j).get(2).trim();
                        String l_periodNumber=filteredRecords.get(j).get(3).trim();
                        String tablePkey=instituteID+"~"+l_standard+"~"+l_section+"~"+l_periodNumber;
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                        for(int i=0;i<classConfiguration.periodTimings.length;i++){
//                            String standard=classConfiguration.periodTimings[i].getStandard();
//                            String section=classConfiguration.periodTimings[i].getSection();
                            String periodNumber=classConfiguration.periodTimings[i].getPeriodNumber(); 
                            String l_requestPkey=instituteID+"~"+standard+"~"+section+"~"+periodNumber;
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
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//             throw new DBProcessingException(ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
     }

private void deleteRecordsInTheList(String tableName,String pkey)throws DBValidationException,DBProcessingException,BSProcessingException{
         
         try{
             RequestBody<ClassLevelConfiguration> reqBody = request.getReqBody();
             ClassLevelConfiguration classConfiguration =reqBody.get();
             String l_instituteID=classConfiguration.getInstituteID();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IDBTransactionService dbts=inject.getDBTransactionService();
             String[] pkArr=pkey.split("~");
             
             
             dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",tableName, pkArr, session);
             
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
        dbg("Inside ClassLevelConfigurationService--->delete");  
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassLevelConfiguration> reqBody = request.getReqBody();
        ClassLevelConfiguration classConfiguration =reqBody.get();
        String l_instituteID=classConfiguration.getInstituteID();    
        String l_standard=classConfiguration.getStandard();
        String l_section=classConfiguration.getSection();

        String[] l_primaryKey={l_instituteID,l_standard,l_section};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_primaryKey, session);
//         for(int i=0;i<classConfiguration.standardMaster.length;i++){
//            
//            String l_standard=classConfiguration.standardMaster[i].getStandard();
//            String l_section=classConfiguration.standardMaster[i].getSection();
//            String[] l_standardPKey={l_instituteID,l_standard,l_section};
//            
//            dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER",l_standardPKey,session);
//        }
        
        for(int i=0;i<classConfiguration.periodTimings.length;i++){
            
//            String l_standard=classConfiguration.periodTimings[i].getStandard();
//            String l_section=classConfiguration.periodTimings[i].getSection();
            String l_periodNo=classConfiguration.periodTimings[i].getPeriodNumber();
            String[] l_periodPKey={l_instituteID,l_standard,l_section,l_periodNo};
            
            dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_PERIOD_MASTER",l_periodPKey,session);
            
        } 
               
 
         dbg("End of ClassLevelConfigurationService--->delete");      
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
     public void  view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
        
                
        try{
            dbg("Inside ClassLevelConfigurationService--->tableRead");
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IPDataService pds=inject.getPdataservice();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<ClassLevelConfiguration> reqBody = request.getReqBody();
            ClassLevelConfiguration classConfiguration =reqBody.get();
            String l_instituteID=classConfiguration.getInstituteID();
            String l_standard=classConfiguration.getStandard();
            String l_section=classConfiguration.getSection();
            String[] l_pkey={l_instituteID,l_standard,l_section};
            ArrayList<String> classConfigMasterRecord=null;
//            Map<String,DBRecord>l_standardMap=null;
            Map<String,ArrayList<String>>l_periodMap=null;
            
            try{
            
            
                classConfigMasterRecord=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
//                l_standardMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", session, dbSession);
                l_periodMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_PERIOD_MASTER", session, dbSession);
            
            }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", l_instituteID);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
            
            
            
            
            
            
            
            
            buildBOfromDB(classConfigMasterRecord,l_periodMap);
           
            
         
          dbg("end of  ClassLevelConfigurationService--->tableRead");    
          }catch(BSValidationException ex){
            throw ex;
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
}
     
//      private void buildBOfromDB( DBRecord masterRecord,Map<String,DBRecord>l_periodMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
//    try{
//            dbg("inside ClassLevelConfigurationService--->buildBOfromDB");
//            
//            ArrayList<String>instituteList=masterRecord.getRecord();
//            RequestBody<ClassLevelConfiguration> reqBody = request.getReqBody();
//            ClassLevelConfiguration classConfiguration =reqBody.get();
//            BusinessService bs=inject.getBusinessService(session);
//            String instituteID=classConfiguration.getInstituteID();
//            String l_standard=classConfiguration.getStandard();
//            String l_section=classConfiguration.getSection();
//            String instituteName=bs.getInstituteName(instituteID, session, dbSession, inject);
//            classConfiguration.setInstituteName(instituteName);
//            
//            
//            if(instituteList!=null&&!instituteList.isEmpty()){
//               
//               String teacherID=instituteList.get(3).trim();
//               classConfiguration.setTeacherID(teacherID);
//               String teacherName=bs.getTeacherName(teacherID, instituteID, session, dbSession, inject);
//               classConfiguration.setTeacherNAme(teacherName);
//               classConfiguration.setAttendance(instituteList.get(13).trim());
//               request.getReqAudit().setMakerID(instituteList.get(5).trim());
//               request.getReqAudit().setCheckerID(instituteList.get(6).trim());
//               request.getReqAudit().setMakerDateStamp(instituteList.get(7).trim());
//               request.getReqAudit().setCheckerDateStamp(instituteList.get(8).trim());
//               request.getReqAudit().setRecordStatus(instituteList.get(9).trim());
//               request.getReqAudit().setAuthStatus(instituteList.get(10).trim());
//               request.getReqAudit().setVersionNumber(instituteList.get(4).trim());
//               request.getReqAudit().setMakerRemarks(instituteList.get(11).trim());
//               request.getReqAudit().setCheckerRemarks(instituteList.get(12).trim());
//            }
//            
////            if(p_standardMap!=null&&!p_standardMap.isEmpty()){
////                List<DBRecord>standardList= p_standardMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.toList());
////                classConfiguration.standardMaster=new StandardMaster[p_standardMap.size()];
////                
////                for(int i=0;i<standardList.size();i++){
////                   DBRecord l_standardRecords=standardList.get(i);
////                   classConfiguration.standardMaster[i]=new StandardMaster();
////                   classConfiguration.standardMaster[i].setStandard(l_standardRecords.getRecord().get(1).trim());
////                   classConfiguration.standardMaster[i].setSection(l_standardRecords.getRecord().get(2).trim());
////                   classConfiguration.standardMaster[i].setTeacherID(l_standardRecords.getRecord().get(3).trim());
////                i++;
////               } 
////            }
//            
//            if(l_periodMap!=null&&!l_periodMap.isEmpty()){
//                List<DBRecord>periodList= l_periodMap.values().stream().filter(rec->rec.getRecord().get(8).trim().equals(request.getReqAudit().getVersionNumber())&&rec.getRecord().get(1).trim().equals(l_standard)&&rec.getRecord().get(2).trim().equals(l_section)).collect(Collectors.toList());
//                dbg("periodList size"+periodList.size());
//                
//                Map<Integer,DBRecord>periodMap=new HashMap();
//                
//                for(int i=0;i<periodList.size();i++){
//                    
//                    DBRecord l_periodRecords=periodList.get(i);
//                    String periodNumber=l_periodRecords.getRecord().get(3).trim();
//                    periodMap.put(Integer.parseInt(periodNumber), l_periodRecords);
//                    
//                }
//                List<Integer> sortedKeys=new ArrayList(periodMap.keySet());
//                Collections.sort(sortedKeys);
//                
//                classConfiguration.periodTimings=new PeriodTimings[sortedKeys.size()];
//                
//                for(int i=0;i<sortedKeys.size();i++){
//                    
//                   Integer key=sortedKeys.get(i);
//                   DBRecord l_periodRecords=periodMap.get(key);
//                   classConfiguration.periodTimings[i]=new PeriodTimings();
////                   classConfiguration.periodTimings[i].setStandard(l_periodRecords.getRecord().get(1).trim());
////                   classConfiguration.periodTimings[i].setSection(l_periodRecords.getRecord().get(2).trim());
//                   classConfiguration.periodTimings[i].setPeriodNumber(l_periodRecords.getRecord().get(3).trim());
//                   classConfiguration.periodTimings[i].setStartTimeHour(l_periodRecords.getRecord().get(4).trim());
//                   classConfiguration.periodTimings[i].setStartTimeMin(l_periodRecords.getRecord().get(5).trim());
//                   classConfiguration.periodTimings[i].setEndTimeHour(l_periodRecords.getRecord().get(6).trim());
//                   classConfiguration.periodTimings[i].setEndTimeMin(l_periodRecords.getRecord().get(7).trim());
//                   classConfiguration.periodTimings[i].setNoon(l_periodRecords.getRecord().get(9).trim());
////                i++;
//               } 
//            }
//            
//         
//       
//        dbg("End of ClassLevelConfigurationService--->buildBOfromDB");
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }
//        catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());   
//        }
// } 
     
      private void buildBOfromDB( ArrayList<String> masterRecord,Map<String,ArrayList<String>>l_periodMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
            dbg("inside ClassLevelConfigurationService--->buildBOfromDB");
            
            ArrayList<String>instituteList=masterRecord;
            RequestBody<ClassLevelConfiguration> reqBody = request.getReqBody();
            ClassLevelConfiguration classConfiguration =reqBody.get();
            BusinessService bs=inject.getBusinessService(session);
            String instituteID=classConfiguration.getInstituteID();
            String l_standard=classConfiguration.getStandard();
            String l_section=classConfiguration.getSection();
            String instituteName=bs.getInstituteName(instituteID, session, dbSession, inject);
            classConfiguration.setInstituteName(instituteName);
            
            
            if(instituteList!=null&&!instituteList.isEmpty()){
               
               String teacherID=instituteList.get(3).trim();
               classConfiguration.setTeacherID(teacherID);
               String teacherName=bs.getTeacherName(teacherID, instituteID, session, dbSession, inject);
               classConfiguration.setTeacherNAme(teacherName);
               classConfiguration.setAttendance(instituteList.get(13).trim());
               request.getReqAudit().setMakerID(instituteList.get(5).trim());
               request.getReqAudit().setCheckerID(instituteList.get(6).trim());
               request.getReqAudit().setMakerDateStamp(instituteList.get(7).trim());
               request.getReqAudit().setCheckerDateStamp(instituteList.get(8).trim());
               request.getReqAudit().setRecordStatus(instituteList.get(9).trim());
               request.getReqAudit().setAuthStatus(instituteList.get(10).trim());
               request.getReqAudit().setVersionNumber(instituteList.get(4).trim());
               request.getReqAudit().setMakerRemarks(instituteList.get(11).trim());
               request.getReqAudit().setCheckerRemarks(instituteList.get(12).trim());
            }
            
//            if(p_standardMap!=null&&!p_standardMap.isEmpty()){
//                List<DBRecord>standardList= p_standardMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.toList());
//                classConfiguration.standardMaster=new StandardMaster[p_standardMap.size()];
//                
//                for(int i=0;i<standardList.size();i++){
//                   DBRecord l_standardRecords=standardList.get(i);
//                   classConfiguration.standardMaster[i]=new StandardMaster();
//                   classConfiguration.standardMaster[i].setStandard(l_standardRecords.getRecord().get(1).trim());
//                   classConfiguration.standardMaster[i].setSection(l_standardRecords.getRecord().get(2).trim());
//                   classConfiguration.standardMaster[i].setTeacherID(l_standardRecords.getRecord().get(3).trim());
//                i++;
//               } 
//            }
            
            if(l_periodMap!=null&&!l_periodMap.isEmpty()){
                List<ArrayList<String>>periodList= l_periodMap.values().stream().filter(rec->rec.get(8).trim().equals(request.getReqAudit().getVersionNumber())&&rec.get(1).trim().equals(l_standard)&&rec.get(2).trim().equals(l_section)).collect(Collectors.toList());
                dbg("periodList size"+periodList.size());
                
                Map<Integer,ArrayList<String>>periodMap=new HashMap();
                
                for(int i=0;i<periodList.size();i++){
                    
                    ArrayList<String> l_periodRecords=periodList.get(i);
                    String periodNumber=l_periodRecords.get(3).trim();
                    periodMap.put(Integer.parseInt(periodNumber), l_periodRecords);
                    
                }
                List<Integer> sortedKeys=new ArrayList(periodMap.keySet());
                Collections.sort(sortedKeys);
                
                classConfiguration.periodTimings=new PeriodTimings[sortedKeys.size()];
                
                for(int i=0;i<sortedKeys.size();i++){
                    
                   Integer key=sortedKeys.get(i);
                   ArrayList<String> l_periodRecords=periodMap.get(key);
                   classConfiguration.periodTimings[i]=new PeriodTimings();
//                   classConfiguration.periodTimings[i].setStandard(l_periodRecords.getRecord().get(1).trim());
//                   classConfiguration.periodTimings[i].setSection(l_periodRecords.getRecord().get(2).trim());
                   classConfiguration.periodTimings[i].setPeriodNumber(l_periodRecords.get(3).trim());
                   classConfiguration.periodTimings[i].setStartTimeHour(l_periodRecords.get(4).trim());
                   classConfiguration.periodTimings[i].setStartTimeMin(l_periodRecords.get(5).trim());
                   classConfiguration.periodTimings[i].setEndTimeHour(l_periodRecords.get(6).trim());
                   classConfiguration.periodTimings[i].setEndTimeMin(l_periodRecords.get(7).trim());
                   classConfiguration.periodTimings[i].setNoon(l_periodRecords.get(9).trim());
//                i++;
               } 
            }
            
         
       
        dbg("End of ClassLevelConfigurationService--->buildBOfromDB");
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }
        catch (Exception ex) {
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
            RequestBody<ClassLevelConfiguration> reqBody = request.getReqBody();
            ClassLevelConfiguration classConfiguration =reqBody.get();
//            JsonArrayBuilder standardMaster=Json.createArrayBuilder();
            JsonArrayBuilder periodMaster=Json.createArrayBuilder();
            
//            for(int i=0;i<classConfiguration.standardMaster.length;i++){
//                
//                 standardMaster.add(Json.createObjectBuilder().add("class", classConfiguration.standardMaster[i].getStandard()+"/"+classConfiguration.standardMaster[i].getSection())
//                                                              .add("teacherID", classConfiguration.standardMaster[i].getTeacherID()));
//                
//            }
            
            for(int i=0;i<classConfiguration.periodTimings.length;i++){
                
                 periodMaster.add(Json.createObjectBuilder()
//                                                           .add("class", classConfiguration.periodTimings[i].getStandard()+"/"+classConfiguration.periodTimings[i].getSection())
                                                            .add("periodNumber", classConfiguration.periodTimings[i].getPeriodNumber())
                                                            .add("noon", classConfiguration.periodTimings[i].getNoon())
                                                            .add("startTime", Json.createObjectBuilder().add("hour", classConfiguration.periodTimings[i].getStartTimeHour()).add("min", classConfiguration.periodTimings[i].getStartTimeMin()))
                                                            .add("endTime", Json.createObjectBuilder().add("hour", classConfiguration.periodTimings[i].getEndTimeHour()).add("min", classConfiguration.periodTimings[i].getEndTimeMin())));
                
            }
            
            
             body=Json.createObjectBuilder().add("instituteID", classConfiguration.getInstituteID())
                                            .add("Class", classConfiguration.getStandard()+"/"+classConfiguration.getSection())
                                            .add("instituteName", classConfiguration.getInstituteName())
                                            .add("teacherID", classConfiguration.getTeacherID())
                                            .add("teacherName", classConfiguration.getTeacherNAme())
                                            .add("attendance", classConfiguration.getAttendance())
//                                            .add("StandardMaster", standardMaster)
                                            .add("periodTimings",periodMaster )
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
       dbg("inside ClassLevelConfigurationService--->businessValidation");    
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

       dbg("ClassLevelConfigurationService--->businessValidation--->O/P--->status"+status);
       dbg("End of ClassLevelConfigurationService--->businessValidation");
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
        dbg("inside ClassLevelConfigurationService--->masterMandatoryValidation");
        RequestBody<ClassLevelConfiguration> reqBody = request.getReqBody();
        ClassLevelConfiguration classConfiguration =reqBody.get();
         
         if(classConfiguration.getInstituteID()==null||classConfiguration.getInstituteID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","instituteID");
         }
         

         
         dbg("ClassLevelConfigurationService--->masterMandatoryValidation--->O/P--->status"+status);
         dbg("End of ClassLevelConfigurationService--->masterMandatoryValidation");
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
        RequestBody<ClassLevelConfiguration> reqBody = request.getReqBody();
        ClassLevelConfiguration classConfiguration =reqBody.get();
        String l_instituteID= classConfiguration.getInstituteID();
        ErrorHandler errHandler=session.getErrorhandler();
             
        if(!bsv.instituteIDValidation( l_instituteID,errHandler,inject, session, dbSession)){
            status=false;
            errhandler.log_app_error("BS_VAL_003","instituteID");
            throw new BSValidationException("BSValidationException");
        }
             

        dbg("ClassExamScheduleServiceService--->masterDataValidation--->O/P--->status"+status);
        dbg("End of ClassExamScheduleServiceService--->masterDataValidation");  
        
       }catch(BSValidationException ex){
            throw ex; 
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
             dbg("inside ClassLevelConfigurationService--->detailMandatoryValidation");
            RequestBody<ClassLevelConfiguration> reqBody = request.getReqBody();
            ClassLevelConfiguration classConfiguration =reqBody.get();
        
            if(classConfiguration.getTeacherID()==null||classConfiguration.getTeacherID().isEmpty()) {   
               status=false;
               errhandler.log_app_error("BS_VAL_002","teacher Id");
               throw new BSValidationException("BSValidationException");
             }
            
            if(classConfiguration.getAttendance()==null||classConfiguration.getAttendance().isEmpty()) {   
               status=false;
               errhandler.log_app_error("BS_VAL_002","attendance");
               throw new BSValidationException("BSValidationException");
             }
            
//        if(classConfiguration.standardMaster==null||classConfiguration.standardMaster.length==0){
//             status=false;
//             errhandler.log_app_error("BS_VAL_002","Standard master");
//         }else{
//             
//             for(int i=0;i<classConfiguration.standardMaster.length;i++){
//             
//
//                 if(classConfiguration.standardMaster[i].getTeacherID()==null||classConfiguration.standardMaster[i].getTeacherID().isEmpty()){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_002","teacherID");
//                 }
//             
//             }
//         }
         
         if(classConfiguration.periodTimings==null||classConfiguration.periodTimings.length==0){
             status=false;
             errhandler.log_app_error("BS_VAL_002","periodTimings");
             throw new BSValidationException("BSValidationException");
         }else{
             
             for(int i=0;i<classConfiguration.periodTimings.length;i++){
             

                 if(classConfiguration.periodTimings[i].getPeriodNumber()==null||classConfiguration.periodTimings[i].getPeriodNumber().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","periodNumber");
                     throw new BSValidationException("BSValidationException");
                 }
                 if(classConfiguration.periodTimings[i].getStartTimeHour()==null||classConfiguration.periodTimings[i].getStartTimeHour().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","startTimeHour");
                     throw new BSValidationException("BSValidationException");
                 }
                 if(classConfiguration.periodTimings[i].getStartTimeMin()==null||classConfiguration.periodTimings[i].getStartTimeMin().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","startTimeMin");
                     throw new BSValidationException("BSValidationException");
                 }
                 if(classConfiguration.periodTimings[i].getEndTimeHour()==null||classConfiguration.periodTimings[i].getEndTimeHour().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","endTimeHour");
                     throw new BSValidationException("BSValidationException");
                 }
                 if(classConfiguration.periodTimings[i].getEndTimeMin()==null||classConfiguration.periodTimings[i].getEndTimeMin().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","endTimeMin");
                     throw new BSValidationException("BSValidationException");
                 }
                 
                 if(classConfiguration.periodTimings[i].getNoon()==null||classConfiguration.periodTimings[i].getNoon().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","noon");
                     throw new BSValidationException("BSValidationException");
                 }
             
             }
         }
        
        
        
        dbg("ClassLevelConfigurationService--->detailMandatoryValidation--->O/P--->status"+status);
        dbg("End of ClassLevelConfigurationService--->detailMandatoryValidation");
        }catch(BSValidationException ex){
            throw ex;
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
  
   private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
     try{
        dbg("inside ClassConfigService--->detailDataValidation");   
        BSValidation bsv=inject.getBsv(session);
        RequestBody<ClassLevelConfiguration> reqBody = request.getReqBody();
        ClassLevelConfiguration classConfiguration =reqBody.get();
        String l_instituteID=classConfiguration.getInstituteID();
        String teacherID=classConfiguration.getTeacherID();
        String attendance=classConfiguration.getAttendance();
        IPDataService pds=inject.getPdataservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String standard=classConfiguration.getStandard();
        String section=classConfiguration.getSection();
        Map<String,ArrayList<String>>  classMap=null;
        
//        ArrayList<String>standardMasterList=new ArrayList();
//        ArrayList<String>classList=new ArrayList();
        
        
        if(!bsv.teacherIDValidation(teacherID, l_instituteID, session, dbSession, inject)){
            status=false;
            errhandler.log_app_error("BS_VAL_003","teacherID");
            throw new BSValidationException("BSValidationException");
        }
        
        
        try{
        
        
        classMap= pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", session, dbSession);
            
        }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", l_instituteID);
                        
                    }else{
                        
                        throw ex;
                    }
            }
        
        if(classMap!=null){
        
            List<ArrayList<String>>teacherFilteredList=classMap.values().stream().filter(rec->rec.get(3).trim().equals(teacherID)&&!rec.get(1).trim().equals(standard)&&!rec.get(2).trim().equals(section)).collect(Collectors.toList());

            if(!teacherFilteredList.isEmpty()){

                status=false;
                String l_standard=teacherFilteredList.get(0).get(1).trim();
                String l_section=teacherFilteredList.get(0).get(2).trim();
                errhandler.log_app_error("BS_VAL_057",l_standard+l_section);
                throw new BSValidationException("BSValidationException");

            }
        
        }
        
        
        if(!(attendance.equals("P")||attendance.equals("N")||attendance.equals("D"))){
            
            status=false;
            errhandler.log_app_error("BS_VAL_003","attendance");
            throw new BSValidationException("BSValidationException");
        }
        
//        for(int i=0;i<classConfiguration.standardMaster.length;i++){
//            
//            String l_standard=classConfiguration.standardMaster[i].getStandard();
//            String l_section=classConfiguration.standardMaster[i].getSection();
//            classList.add(l_standard+l_section);
//            
//            
//            String l_teacherID=classConfiguration.standardMaster[i].getTeacherID();
//           
//            if(!standardMasterList.contains(l_standard+l_section+l_teacherID)){
//            
//                 standardMasterList.add(l_standard+l_section+l_teacherID);
//            
//            }else{
//                status=false;
//                errhandler.log_app_error("BS_VAL_031","standardMaster");
//                throw new BSValidationException("BSValidationException");
//               
//            }
//                 
//            if(!bsv.teacherIDValidation(l_teacherID, l_instituteID, session, dbSession, inject)){
//                 status=false;
//                 errhandler.log_app_error("BS_VAL_003","teacherID");
//             }
//            
//        }
        
        ArrayList<String>periodList=new ArrayList();
        ArrayList<Integer>foreNoonPeriodList=new ArrayList();
        ArrayList<Integer>afterNoonPeriodList=new ArrayList();
        ArrayList<Integer>timeList=new ArrayList();
        
        for(int i=0;i<classConfiguration.periodTimings.length;i++){
            
            String l_startTimeHour=classConfiguration.periodTimings[i].getStartTimeHour();
            String l_startTimeMin=classConfiguration.periodTimings[i].getStartTimeMin();
            String l_endTimeHour=classConfiguration.periodTimings[i].getEndTimeHour();
            String l_endTimeMin=classConfiguration.periodTimings[i].getEndTimeMin();
//            String l_standard=classConfiguration.periodTimings[i].getStandard();
//            String l_section=classConfiguration.periodTimings[i].getSection();
            String l_periodNumber=classConfiguration.periodTimings[i].getPeriodNumber();
            String l_noon=classConfiguration.periodTimings[i].getNoon();
            
            
            
//                 if(!classList.contains(l_standard+l_section)){
//
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_034",l_standard+"/"+l_section);
//
//                 }

                 if(!periodList.contains(l_periodNumber)){

                     periodList.add(l_periodNumber);

                }else{
                    status=false;
                    errhandler.log_app_error("BS_VAL_031","Period Timings");
                    throw new BSValidationException("BSValidationException");

                }
                 
               if(!bsv.periodNumberValidation(l_periodNumber, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","periodNumber");
                    throw new BSValidationException("BSValidationException");
                }  
                 
               if(!bsv.hourValidation(l_startTimeHour,l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","startTimeHour");
                    throw new BSValidationException("BSValidationException");
                }
                 
                if(!bsv.minValidation(l_startTimeMin,l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","startTimeMin");
                    throw new BSValidationException("BSValidationException");
                }
                if(!bsv.hourValidation(l_endTimeHour,l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","endTimeHour");
                    throw new BSValidationException("BSValidationException");
                }
                if(!bsv.minValidation(l_endTimeMin,l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","endTimeMin");
                    throw new BSValidationException("BSValidationException");
                }

                if(!(l_noon.equals("A")||l_noon.equals("F"))){
            
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","noon");
                    throw new BSValidationException("BSValidationException");
                }
                
                Calendar start = Calendar.getInstance();
 
                start.set(Calendar.HOUR_OF_DAY, Integer.parseInt(l_startTimeHour));
                start.set(Calendar.MINUTE,  Integer.parseInt(l_startTimeMin));
                start.set(Calendar.SECOND, 0);
                start.set(Calendar.MILLISECOND, 0);

                Calendar end = Calendar.getInstance();

                end.set(Calendar.HOUR_OF_DAY, Integer.parseInt(l_endTimeHour));
//                end.set(Calendar.MINUTE, Integer.parseInt(l_endTimeHour));
                end.set(Calendar.MINUTE, Integer.parseInt(l_endTimeMin));
                end.set(Calendar.SECOND, 0);
                end.set(Calendar.MILLISECOND, 0);
                
                if(start.after(end)){
                    
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","start and end time");
                    throw new BSValidationException("BSValidationException");
                }
                
                if(start.equals(end)){
                    
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","start and end time");
                    throw new BSValidationException("BSValidationException");
                }
                
                
                int startTimeMinutes=Integer.parseInt(l_startTimeHour)*60+Integer.parseInt(l_startTimeMin);
                int endTimeMinutes=Integer.parseInt(l_endTimeHour)*60+Integer.parseInt(l_endTimeMin);
                
                dbg("startTimeMinutes"+startTimeMinutes);
                dbg("endTimeMinutes"+endTimeMinutes);
                
                for (int j = startTimeMinutes ; j <=endTimeMinutes; j++){
                   
                       if(!timeList.contains(j)){

                           timeList.add(j);

                       }else{

                           status=false;
                           errhandler.log_app_error("BS_VAL_003","start and end time");
                           throw new BSValidationException("BSValidationException");
                       }
                }
                
                
                
                if(l_noon.equals("F")){
                    
                    foreNoonPeriodList.add(Integer.parseInt(l_periodNumber));
                    
                }else if(l_noon.equals("A")){
                    
                    afterNoonPeriodList.add(Integer.parseInt(l_periodNumber));
                }
        }
        
        if(attendance.equals("P")&&afterNoonPeriodList.isEmpty()){
            
            errhandler.log_app_error("BS_VAL_002","A Period in after noon");
            throw new BSValidationException("BSValidationException");
            
        }
        
        
        
        dbg("periodList.size()"+periodList.size());
        dbg("foreNoonPeriodList.size()"+foreNoonPeriodList.size());
        
        
        if(periodList.size()!=foreNoonPeriodList.size()+afterNoonPeriodList.size()){
            
             status=false;
             errhandler.log_app_error("BS_VAL_003","period configuration");
             throw new BSValidationException("BSValidationException");
        }
        
        if(!checkElementsAreContinuous(foreNoonPeriodList)){
            
             status=false;
             errhandler.log_app_error("BS_VAL_003","fore noon period configuration");
             throw new BSValidationException("BSValidationException");
        }
        
        if(!checkElementsAreContinuous(afterNoonPeriodList)){
            
             status=false;
             errhandler.log_app_error("BS_VAL_003","after noon period configuration");
             throw new BSValidationException("BSValidationException");
        }
        
        
        
            
        
        
        
        
        
        

        dbg("ClassconfigServiceService--->detailDataValidation--->O/P--->status"+status);
        dbg("End of ClassExamScheduleServiceService--->detailDataValidation");  
        }catch(BSValidationException ex){
            throw ex;
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
  
   
   private boolean checkElementsAreContinuous(ArrayList<Integer>values)throws BSProcessingException{
       boolean status=false;
       try{
           dbg("inside checkElementsAreContinuous");
           Collections.sort(values);
           
           int size=values.size();
           
           for (int i = 1; i < size; i++){
               
               if (values.get(i) - values.get(i-1)!= 1) {
                
                   dbg("ststus false in checkElementsAreContinuous");
                   return false; 
               }else{
       
                 dbg("ststus false in checkElementsAreContinuous");
               }
         }
     
           
       dbg("end of checkElementsAreContinuous");
        return true;   
          }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
   }
   
   
  public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     ExistingAudit exAudit=new ExistingAudit();
     try{
        dbg("inside BusinessService--->getExistingAudit") ;
        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IPDataService pds=inject.getPdataservice();
        String l_operation=request.getReqHeader().getOperation();
        String l_service=request.getReqHeader().getService();
        RequestBody<ClassLevelConfiguration> classConfigurationBody = request.getReqBody();
        ClassLevelConfiguration classConfiguration =classConfigurationBody.get();
        String l_instituteID=classConfiguration.getInstituteID();
        String l_standard=classConfiguration.getStandard();
        String l_section=classConfiguration.getSection();
        String l_versioNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
        if(!(l_operation.equals("Create")||l_operation.equals("View"))){
             
            if(l_operation.equals("AutoAuth")&&l_versioNumber.equals("1")){
                
                return null;
            }else{  
                
               dbg("inside business Service--->getExistingAudit--->Service--->ClassLevelConfiguration");  
               
               String[] l_pkey={l_instituteID,l_standard,l_section};
            
               ArrayList<String> instituteRecord=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);

               exAudit.setAuthStatus(instituteRecord.get(10).trim());
               exAudit.setMakerID(instituteRecord.get(5).trim());
               exAudit.setRecordStatus(instituteRecord.get(9).trim());
               exAudit.setVersionNumber(Integer.parseInt(instituteRecord.get(4).trim()));
            

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
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
