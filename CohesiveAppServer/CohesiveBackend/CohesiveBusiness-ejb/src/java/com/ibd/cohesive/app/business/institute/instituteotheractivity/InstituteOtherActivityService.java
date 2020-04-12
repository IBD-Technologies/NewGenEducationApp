/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.instituteotheractivity;

import com.ibd.businessViews.IInstituteOtherActivityService;
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
import com.ibd.cohesive.db.transaction.lock.ILockService;
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
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
//@Local(IInstituteOtherActivityService.class)
@Remote(IInstituteOtherActivityService.class)
@Stateless
public class InstituteOtherActivityService implements IInstituteOtherActivityService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public InstituteOtherActivityService(){
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
       dbg("inside InstituteOtherActivityService--->processing");
       dbg("InstituteOtherActivityService--->Processing--->I/P--->requestJson"+requestJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,requestJson);
       bs.requestlogging(request,requestJson, inject,session,dbSession);
       buildBOfromReq(requestJson);  
       String l_operation=request.getReqHeader().getOperation();
       
       if(!l_operation.equals("Create-Default")){
       
           RequestBody<InstituteOtherActivity> reqBody = request.getReqBody();
           InstituteOtherActivity otherActivity =reqBody.get();
           String l_otherActivityID=otherActivity.getActivityID();
           l_lockKey=l_otherActivityID;
           if(!businessLock.getBusinessLock(request, l_lockKey, session)){
               l_validation_status=false;
               throw new BSValidationException("BSValidationException");
            }
       
       }
       BusinessEJB<IInstituteOtherActivityService>otherActivityEJB=new BusinessEJB();
       otherActivityEJB.set(this);
       exAudit=bs.getExistingAudit(otherActivityEJB);
       
      if(!(bsv. businessServiceValidation(requestJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
      }
       if(!l_operation.equals("Create-Default")){
      
           if(!businessValidation(errhandler)){
               l_validation_status=false;
               throw new BSValidationException("BSValidationException");

           } 
       }
      
      
       bs.businessServiceProcssing(request, exAudit, inject,otherActivityEJB);
       
        if(l_operation.equals("Create-Default")){
           
           createDefault();
       }

         if(l_session_created_now){
             jsonResponse= bs.buildSuccessResponse(requestJson, request, inject, session,otherActivityEJB) ;
             tc.commit(session,dbSession);
             dbg("commit is called in  service");
        }
       dbg("InstituteOtherActivityService--->Processing--->O/P--->jsonResponse"+jsonResponse);     
       dbg("End of otherActivityService--->processing");     
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
      }catch(Exception ex){
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
            clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
            dbg("response"+jsonResponse.toString());
            BSValidation bsv=inject.getBsv(session);
//            if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
          clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes  
//BSProcessingException ex=new BSProcessingException("response contains special characters");
  //              dbg(ex);
                
    //            session.clearSessionObject();
      //         dbSession.clearSessionObject();
        //       throw ex;
               
          //  }
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

    private  void buildBOfromReq(JsonObject p_request)throws BSProcessingException,DBProcessingException{
       
       try{
       dbg("inside InstituteOtherActivityService--->buildBOfromReq"); 
       dbg("InstituteOtherActivityService--->buildBOfromReq--->I/P--->p_request"+p_request.toString()); 
       RequestBody<InstituteOtherActivity> reqBody = new RequestBody<InstituteOtherActivity>(); 
       InstituteOtherActivity otherActivity = new InstituteOtherActivity();
       String l_operation=request.getReqHeader().getOperation();

       if(!l_operation.equals("Create-Default")){       
       
       JsonObject l_body=p_request.getJsonObject("body");
       otherActivity.setInstituteID(l_body.getString("instituteID"));
       otherActivity.setInstituteName(l_body.getString("instituteName"));
       otherActivity.setGroupID(l_body.getString("groupID"));
       otherActivity.setActivityID(l_body.getString("activityID"));
       otherActivity.setActivityName(l_body.getString("activityName"));
       otherActivity.setActivityType(l_body.getString("activityType"));
       if(!(l_operation.equals("View"))){
            otherActivity.setLevel(l_body.getString("level"));
            otherActivity.setVenue(l_body.getString("venue"));
            otherActivity.setDate(l_body.getString("date"));
            otherActivity.setDueDate(l_body.getString("dueDate"));
        }
       
       }
       reqBody.set(otherActivity);
      request.setReqBody(reqBody);
      dbg("End of InstituteOtherActivityService--->buildBOfromReq");
      }
        catch(Exception ex){
             dbg(ex);
            throw new BSProcessingException("BodyParsingException"+ex.toString());
        }
   }
    
     private void createDefault()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside teacher attendance--->createdefault");    
        ILockService lock=inject.getLockService();
        String sequenceNo="O"+lock.getSequenceNo();
            
        RequestBody<InstituteOtherActivity> reqBody = request.getReqBody();
        InstituteOtherActivity assignment =reqBody.get();
        
        assignment.setActivityID(sequenceNo);
        
          dbg("end of  completed teacher attendance--->createdefault");                
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
  
     public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
        try{
        dbg("Inside InstituteOtherActivityService--->create"); 
        RequestBody<InstituteOtherActivity> reqBody = request.getReqBody();
        InstituteOtherActivity otherActivity =reqBody.get();
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
        
        String l_instituteID=otherActivity.getInstituteID();
        String l_groupID=otherActivity.getGroupID();
        String l_activityID=otherActivity.getActivityID();
        String l_activityName=otherActivity.getActivityName();
        String l_activityType=otherActivity.getActivityType();
        String l_level=otherActivity.getLevel();
        String l_venue=otherActivity.getVenue();
        String l_date=otherActivity.getDate(); 
        String l_dueDate=otherActivity.getDueDate();
        
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY",91,l_instituteID,l_groupID,l_activityID,l_activityName,l_activityType,l_level,l_venue,l_date,l_dueDate,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
       
        
       

        dbg("End of InstituteOtherActivityService--->create");
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
            dbg("Inside InstituteOtherActivityService--->update"); 
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<InstituteOtherActivity> reqBody = request.getReqBody();
            InstituteOtherActivity otherActivity =reqBody.get();
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
            String l_instituteID=otherActivity.getInstituteID(); 
            String l_activityID=otherActivity.getActivityID();
            Map<String,String>  l_column_to_update=new HashMap();
            l_column_to_update.put("11", l_checkerID);
            l_column_to_update.put("13", l_checkerDateStamp);
            l_column_to_update.put("15", l_authStatus);
            l_column_to_update.put("18", l_checkerRemarks);
            
             
             String[] l_primaryKey={l_activityID};
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY","IVW_OTHER_ACTIVITY", l_primaryKey, l_column_to_update, session);

             dbg("End of InstituteOtherActivityService--->update");          
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
        dbg("Inside InstituteOtherActivityService--->fullUpdate");
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<InstituteOtherActivity> reqBody = request.getReqBody();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        InstituteOtherActivity otherActivity =reqBody.get();
        String l_instituteID=otherActivity.getInstituteID(); 
        String l_groupID=otherActivity.getGroupID();
        String l_activityID=otherActivity.getActivityID();
        String l_activityName=otherActivity.getActivityName();
        String l_activityType=otherActivity.getActivityType();
        String l_level=otherActivity.getLevel();
        String l_venue=otherActivity.getVenue();
        String l_date=otherActivity.getDate();
        String l_dueDate=otherActivity.getDueDate();
        l_column_to_update= new HashMap();
        l_column_to_update.put("2", l_groupID);
        l_column_to_update.put("3", l_activityID);
        l_column_to_update.put("4", l_activityName);
        l_column_to_update.put("5", l_activityType);
        l_column_to_update.put("6", l_level);
        l_column_to_update.put("7", l_venue);
        l_column_to_update.put("8", l_date);
        l_column_to_update.put("9", l_dueDate);
        l_column_to_update.put("12", l_makerDateStamp);
        l_column_to_update.put("17", l_makerRemarks);
        
        String[] l_PKey={l_activityID};
            
            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY","IVW_OTHER_ACTIVITY",l_PKey,l_column_to_update,session);
        
       
       
        
                   
        dbg("end of InstituteOtherActivityService--->fullUpdate");                
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
        dbg("Inside InstituteOtherActivityService--->delete");  
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<InstituteOtherActivity> reqBody = request.getReqBody();
        InstituteOtherActivity otherActivity =reqBody.get();
        String l_instituteID=otherActivity.getInstituteID();   
        String l_otherActivityID=otherActivity.getActivityID();

        String[] l_primaryKey={l_otherActivityID};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY","IVW_OTHER_ACTIVITY", l_primaryKey, session);
         
               
 
         dbg("End of InstituteOtherActivityService--->delete");      
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
            dbg("Inside InstituteOtherActivityService--->tableRead");
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IPDataService pds=inject.getPdataservice();
            String l_userID=request.getReqHeader().getUserID();
            RequestBody<InstituteOtherActivity> reqBody = request.getReqBody();
            InstituteOtherActivity otherActivity =reqBody.get();
            String l_otherActivityID=otherActivity.getActivityID();
            String l_instituteID=otherActivity.getInstituteID();
            String[] l_pkey={l_otherActivityID};
            DBRecord otherActivityRecord=null;
           
            
            try{
            
            
                otherActivityRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY","IVW_OTHER_ACTIVITY", l_pkey, session,dbSession);
                buildBOfromDB(otherActivityRecord);
           
            }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", l_otherActivityID);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
            
            
            
         
          dbg("end of  InstituteOtherActivityService--->tableRead");  
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
     
      private void buildBOfromDB( DBRecord p_otherActivity)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
            dbg("inside InstituteOtherActivityService--->buildBOfromDB");
            
            ArrayList<String>l_otherActivityList=p_otherActivity.getRecord();
            RequestBody<InstituteOtherActivity> reqBody = request.getReqBody();
            InstituteOtherActivity otherActivity =reqBody.get();
            BusinessService bs=inject.getBusinessService(session);
            String l_instituteID=otherActivity.getInstituteID();
            if(l_otherActivityList!=null&&!l_otherActivityList.isEmpty()){
                
               String instituteName=bs.getInstituteName(l_instituteID, session, dbSession, inject);
               otherActivity.setInstituteName(instituteName);
               otherActivity.setGroupID(l_otherActivityList.get(1).trim()); 
               otherActivity.setActivityName(l_otherActivityList.get(3).trim()); 
               otherActivity.setActivityType(l_otherActivityList.get(4).trim()); 
               otherActivity.setLevel(l_otherActivityList.get(5).trim());
               otherActivity.setVenue(l_otherActivityList.get(6).trim());
               otherActivity.setDate(l_otherActivityList.get(7).trim());
               otherActivity.setDueDate(l_otherActivityList.get(8).trim());
               request.getReqAudit().setMakerID(l_otherActivityList.get(9).trim());
               request.getReqAudit().setCheckerID(l_otherActivityList.get(10).trim());
               request.getReqAudit().setMakerDateStamp(l_otherActivityList.get(11).trim());
               request.getReqAudit().setCheckerDateStamp(l_otherActivityList.get(12).trim());
               request.getReqAudit().setRecordStatus(l_otherActivityList.get(13).trim());
               request.getReqAudit().setAuthStatus(l_otherActivityList.get(14).trim());
               request.getReqAudit().setVersionNumber(l_otherActivityList.get(15).trim());
               request.getReqAudit().setMakerRemarks(l_otherActivityList.get(16).trim());
               request.getReqAudit().setCheckerRemarks(l_otherActivityList.get(17).trim());
            }
            
          
       
        dbg("End of InstituteOtherActivityService--->buildBOfromDB");
        
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
            RequestBody<InstituteOtherActivity> reqBody = request.getReqBody();
            InstituteOtherActivity otherActivity =reqBody.get();
            String operation=request.getReqHeader().getOperation();
            
            if(operation.equals("Create-Default")){
                
                body=Json.createObjectBuilder().add("activityID", otherActivity.getActivityID())
                                               .build();
                
                
                
            }else{
            
             body=Json.createObjectBuilder().add("instituteID", otherActivity.getInstituteID())
                                            .add("instituteName", otherActivity.getInstituteName())
                                            .add("groupID", otherActivity.getGroupID())
                                            .add("activityID", otherActivity.getActivityID())
                                            .add("activityName", otherActivity.getActivityName())
                                            .add("activityType", otherActivity.getActivityType())
                                            .add("level", otherActivity.getLevel())
                                            .add("venue", otherActivity.getVenue())
                                            .add("date", otherActivity.getDate())
                                            .add("dueDate", otherActivity.getDueDate())
                                            .build(); 
            }
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
       dbg("inside InstituteOtherActivityService--->businessValidation");    
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

       dbg("InstituteOtherActivityService--->businessValidation--->O/P--->status"+status);
       dbg("End of InstituteOtherActivityService--->businessValidation");
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
        dbg("inside InstituteOtherActivityService--->masterMandatoryValidation");
        RequestBody<InstituteOtherActivity> reqBody = request.getReqBody();
        InstituteOtherActivity otherActivity =reqBody.get();
         
         if(otherActivity.getInstituteID()==null||otherActivity.getInstituteID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","instituteID");
         }
         if(otherActivity.getActivityID()==null||otherActivity.getActivityID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","activityID");
         }
        
         
         
         dbg("InstituteOtherActivityService--->masterMandatoryValidation--->O/P--->status"+status);
         dbg("End of InstituteOtherActivityService--->masterMandatoryValidation");
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
        RequestBody<InstituteOtherActivity> reqBody = request.getReqBody();
        InstituteOtherActivity otherActivity =reqBody.get();
        String l_instituteID= otherActivity.getInstituteID();
        ErrorHandler errHandler=session.getErrorhandler();
             
        if(!bsv.instituteIDValidation( l_instituteID,errHandler,inject, session, dbSession)){
            status=false;
            errhandler.log_app_error("BS_VAL_003","instituteID");
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
            dbg("inside InstituteOtherActivityService--->detailMandatoryValidation");
            RequestBody<InstituteOtherActivity> reqBody = request.getReqBody();
            InstituteOtherActivity otherActivity =reqBody.get();
           
            
             if(otherActivity.getActivityName()==null||otherActivity.getActivityName().isEmpty()) {   
                status=false;
                errhandler.log_app_error("BS_VAL_002","activityName");
            }
            if(otherActivity.getActivityType()==null||otherActivity.getActivityType().isEmpty()) {   
               status=false;
               errhandler.log_app_error("BS_VAL_002","activityType");
            }
            
            if(otherActivity.getGroupID()==null||otherActivity.getGroupID().isEmpty()) {   
               status=false;
               errhandler.log_app_error("BS_VAL_002","groupID");
            }
            
           if(otherActivity.getLevel()==null||otherActivity.getLevel().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","level");  
            }
            if(otherActivity.getVenue()==null||otherActivity.getVenue().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","venue");  
            }
            if(otherActivity.getDate()==null||otherActivity.getDate().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","date");  
            }
            if(otherActivity.getDueDate()==null||otherActivity.getDueDate().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","dueDate");  
            }
           
        dbg("InstituteOtherActivityService--->detailMandatoryValidation--->O/P--->status"+status);
        dbg("End of InstituteOtherActivityService--->detailMandatoryValidation");
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
  
   private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
     try{
        dbg("inside InstituteOtherActivityService--->detailDataValidation");   
        BSValidation bsv=inject.getBsv(session);
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        BusinessService bs=inject.getBusinessService(session);
        String currentDate=bs.getCurrentDate();
        RequestBody<InstituteOtherActivity> reqBody = request.getReqBody();
        InstituteOtherActivity otherActivity =reqBody.get();
        String l_date=otherActivity.getDate();
        String l_dueDate=otherActivity.getDueDate();
        String activityType=otherActivity.getActivityType();
        String l_instituteID=otherActivity.getInstituteID();
        String l_groupID=otherActivity.getGroupID();
        String l_operation=request.getReqHeader().getOperation();
        String l_activityID=otherActivity.getActivityID();
        String l_level=otherActivity.getLevel();
        
             if(!bsv.groupIDValidation( l_groupID,l_instituteID, session, dbSession,inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","groupID");
             } 
             
             if(!(activityType.equals("S")||activityType.equals("C"))){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Activity type");
             }
        
             if(!(l_level.equals("S")||l_level.equals("D")||l_level.equals("I")||l_level.equals("E"))){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Level");
             }
             
             
             
             
             
             if(l_operation.equals("Create")){
             
                 if(!bsv.pastDateValidation(l_date, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","Event date");
                 }
             
             
                 if(!bsv.pastDateValidation(l_dueDate, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","Confirmation Date");
                 }
             
             }else if(l_operation.equals("Modify")){
                 
                 
                 String[] l_pkey={l_activityID};
                 DBRecord otherActivityRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY","IVW_OTHER_ACTIVITY", l_pkey, session,dbSession);
                 
                 String eventDate=otherActivityRecord.getRecord().get(7).trim();
                 String dueDate=otherActivityRecord.getRecord().get(8).trim();
                 
                 if(!eventDate.equals(l_date)){
                     
                     if(!bsv.pastDateValidation(l_date, session, dbSession, inject)){
                         status=false;
                         errhandler.log_app_error("BS_VAL_003","Event date");
                     }
                 }
                 
                 if(!l_dueDate.equals(dueDate)){
                     
                      if(!bsv.pastDateValidation(l_dueDate, session, dbSession, inject)){
                         status=false;
                         errhandler.log_app_error("BS_VAL_003","Confirmation Date");
                 }
                 }
             } 
             
             
             
//             if(!bsv.pastDateValidation(l_date, session, dbSession, inject)){
//                 status=false;
//                 errhandler.log_app_error("BS_VAL_003","Event date");
//             }
//             
//             
//             
//             
//             if(!bsv.pastDateValidation(l_dueDate, session, dbSession, inject)){
//                 status=false;
//                 errhandler.log_app_error("BS_VAL_003","Confirmation date");
//             }
      
         if(l_operation.equals("Modify")||l_operation.equals("Delete")){
             boolean recordExistence=false;
            
            try{
            
                String[] l_pkey={l_activityID};
                readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY", "OTHER_ACTIVITY_BATCH_INDICATOR", l_pkey,session, dbSession);
                recordExistence=true;
            }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                          recordExistence=false;
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_000"); 
                       }else{
                           throw ex;
                       }
            
            }
            
            
            if(recordExistence){
                
                status=false;
                errhandler.log_app_error("BS_VAL_042","Other activity");
            }
        }    
             
             
        dbg("InstituteOtherActivityService--->detailDataValidation--->O/P--->status"+status);
        dbg("End of InstituteOtherActivityService--->detailDataValidation");  
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
  
  public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     ExistingAudit exAudit=new ExistingAudit();
     try{
        dbg("inside BusinessService--->getExistingAudit") ;
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_operation=request.getReqHeader().getOperation();
        String l_service=request.getReqHeader().getService();
        RequestBody<InstituteOtherActivity> otherActivityBody = request.getReqBody();
        InstituteOtherActivity otherActivity =otherActivityBody.get();
        String l_instituteID=otherActivity.getInstituteID();
        String l_versioNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
         if(!(l_operation.equals("Create")||l_operation.equals("Create-Default")||l_operation.equals("View"))){
             
            if(l_operation.equals("AutoAuth")&&l_versioNumber.equals("1")){
                
                return null;
            }else{  
                
               dbg("inside business Service--->getExistingAudit--->Service--->InstituteOtherActivity");  
               
               String l_otherActivityID=otherActivity.getActivityID();
               String[] l_pkey={l_otherActivityID};
            
               DBRecord instituteRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY","IVW_OTHER_ACTIVITY", l_pkey, session,dbSession);

               exAudit.setAuthStatus(instituteRecord.getRecord().get(14).trim());
               exAudit.setMakerID(instituteRecord.getRecord().get(9).trim());
               exAudit.setRecordStatus(instituteRecord.getRecord().get(13).trim());
               exAudit.setVersionNumber(Integer.parseInt(instituteRecord.getRecord().get(15).trim()));
            

 
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
