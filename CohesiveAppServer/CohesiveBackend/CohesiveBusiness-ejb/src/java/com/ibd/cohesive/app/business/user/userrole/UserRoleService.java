/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.user.userrole;
import com.ibd.businessViews.IUserRoleService;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
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
//@Local(IUserRoleService.class)
@Remote(IUserRoleService.class)
@Stateless
public class UserRoleService implements IUserRoleService {
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
//    int maxversion_dummy;//dummy for lambda expression
//    Map<String,ArrayList<String>>filtermap_dummy;
//    String filterkey_dummy;
    public UserRoleService() {
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
    
   public JsonObject processing(JsonObject requestJson) throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
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
       dbg("inside UserRoleService--->processing");
       dbg("UserRoleService--->Processing--->I/P--->requestJson"+requestJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,requestJson);
       String l_operation=request.getReqHeader().getOperation();
       bs.requestlogging(request,requestJson, inject,session,dbSession);
       buildBOfromReq(requestJson);  
       
       if(!l_operation.equals("Create-Default")){
       
           RequestBody<UserRole> reqBody = request.getReqBody();
           UserRole userRole =reqBody.get();
           String instituteID=userRole.getInstituteID();
           
           l_lockKey=userRole.getRoleID()+"~"+instituteID;
           if(!businessLock.getBusinessLock(request, l_lockKey, session)){
               l_validation_status=false;
               throw new BSValidationException("BSValidationException");
            }
        }
       
       
       BusinessEJB<IUserRoleService> userRoleEjb=new BusinessEJB();
       userRoleEjb.set(this);
       exAudit=bs.getExistingAudit(userRoleEjb);
       
       if(!(bsv.businessServiceValidation(requestJson, exAudit, request, errhandler, inject, session, dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
       }
       
       if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");

       } 
     
        
       bs.businessServiceProcssing(request, exAudit, inject,userRoleEjb);
       

           if(l_session_created_now){
              jsonResponse=  bs.buildSuccessResponse(requestJson, request, inject, session,userRoleEjb) ;
              tc.commit(session,dbSession);
              dbg("commit is called in user role service");
        }
       dbg("UserRoleService--->Processing--->O/P--->jsonResponse"+jsonResponse);     
       dbg("End of UserRoleService--->processing");
       }catch(NamingException ex){
            dbg(ex);
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, requestJson,"BSProcessingException");
       }catch(BSValidationException ex){
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, requestJson,"BSValidationException");
       }catch(DBValidationException ex){
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, requestJson,"DBValidationException");
       }catch(DBProcessingException ex){
            dbg(ex);
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, requestJson,"DBProcessingException");
       }catch(BSProcessingException ex){
           dbg(ex);
           exHandler = inject.getErrorHandle(session);
           jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, requestJson,"BSProcessingException");
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
            //if(!bsv.responseSpecialCharacterValidation(jsonResponse)){//Integration Change
            clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);
              //BSProcessingException ex=new BSProcessingException("response contains special characters");
              //dbg(ex);
                
             //   session.clearSessionObject();
               //dbSession.clearSessionObject();
               //throw ex;
               
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
    //This method builds json response if the process completed sucessfully  
//  private JsonObject buildSuccessResponse(JsonObject requestJson)throws BSProcessingException{
//     JsonObjectBuilder jsonResponse=Json.createObjectBuilder();
//     BusinessService  bs=inject.getBusinessService(session);
//      try{
//      dbg("inside UserroleService--->buildSuccessResponse");    
//      dbg("UserRoleService--->buildSuccessResponse--->I/P--->requestJson"+requestJson);
//      String l_msgID;
//      l_msgID=request.getReqHeader().getMsgID();
//      String l_service=request.getReqHeader().getService();
//      String l_operation=request.getReqHeader().getOperation();
//      Map<String,String>l_businessEntityMap=request.getReqHeader().getBusinessEntity();
//      JsonArrayBuilder l_businessEntityBuilder=Json.createArrayBuilder();
//      Iterator<String> keyIterator=l_businessEntityMap.keySet().iterator();
//      Iterator<String> valueIterator=l_businessEntityMap.values().iterator();
//      while(keyIterator.hasNext()&&valueIterator.hasNext()){
//          String l_entityName=keyIterator.next();
//          String l_entityValue=valueIterator.next();
//          l_businessEntityBuilder.add(Json.createObjectBuilder().add("entityName", l_entityName)
//                                                                .add("entityValue", l_entityValue));
//      }
//      String l_instituteID=request.getReqHeader().getInstituteID();
//      String l_userID=request.getReqHeader().getUserID();
//      String l_source=request.getReqHeader().getSource();
//      UUID r_msgID=CohesiveSession.dataToUUID("messageout");
//      JsonArray errorArray=Json.createArrayBuilder().add(Json.createObjectBuilder().add("errorCode", "SUCCESS")
//                                                                                   .add("errorMessage", "SUCCESSFULLY PROCESSED")).build();
//      
//      JsonObject r_JsonBody=getJsonBody(requestJson);
//      JsonObject l_auditLog=bs.buildAuditLogJson(request);
//       if(l_operation.equals("Delete")){
//      jsonResponse=Json.createObjectBuilder().add("header",Json.createObjectBuilder()
//                                                        .add("msgID",r_msgID.toString())
//                                                        .add("correlationID", l_msgID)
//                                                        .add("service", l_service)
//                                                        .add("operation", l_operation)
//                                                        .add("businessEntity", l_businessEntityBuilder)
//                                                        .add("instituteID", l_instituteID)
//                                                        .add("userID",l_userID)
//                                                        .add("source",l_source)
//                                                        .add("status", "success"))
//                                                        .addNull("body")
//                                                        .add("auditLog", l_auditLog)
//                                                        .add("error",errorArray);
//                                                        
//      }
//      else{
//          jsonResponse=Json.createObjectBuilder().add("header",Json.createObjectBuilder()
//                                                        .add("msgID",r_msgID.toString())
//                                                        .add("correlationID", l_msgID)
//                                                        .add("service", l_service)
//                                                        .add("operation", l_operation)
//                                                        .add("businessEntity", l_businessEntityBuilder)
//                                                        .add("instituteID", l_instituteID)
//                                                        .add("userID",l_userID)
//                                                        .add("source",l_source)
//                                                        .add("status", "success"))
//                                                        .add("body",r_JsonBody)
//                                                        .add("auditLog", l_auditLog)
//                                                        .add("error",errorArray);
//          
//          
//      }
//      
//      dbg("UserRoleService--->buildSuccessResponse--->O/P--->jsonResponse"+jsonResponse);
//      dbg("End of UserRoleService--->buildSuccessResponse");
//      }catch(Exception ex){
//          dbg(ex);
//          throw new BSProcessingException("Exception" + ex.toString());
//      }
//    return jsonResponse.build();
//  }
 
 //This method  builds business object from the json request
 private  void buildBOfromReq(JsonObject p_request)throws BSProcessingException,DBProcessingException{
       
      try{
      dbg("inside UserroleService--->buildBOfromReq"); 
      dbg("UserroleService--->buildBOfromReq--->I/P--->p_request"+p_request.toString());  
      RequestBody<UserRole> reqBody = new RequestBody<UserRole>(); 
      UserRole userRole = new UserRole();
      JsonObject l_body=p_request.getJsonObject("body");      
      String l_operation=request.getReqHeader().getOperation();
      userRole.setRoleID(l_body.getString("roleID"));
      userRole.setRoleDescription(l_body.getString("roleDescription"));
      userRole.setInstituteID(l_body.getString("instituteID"));
      
      if(!(l_operation.equals("View"))){
          JsonArray l_functionsArray= l_body.getJsonArray("functions");
          userRole.functions=new Functions[l_functionsArray.size()];
          for(int i=0;i<l_functionsArray.size();i++){
              userRole.functions[i]=new Functions();    
              JsonObject l_functionObject=l_functionsArray.getJsonObject(i);
              userRole.functions[i].setFunctionID(l_functionObject.getString("functionID"));
              
              if(l_functionObject.getBoolean("create")){
                  
                  userRole.functions[i].setCreate("true");
              }else{
                  
                  userRole.functions[i].setCreate("false");
              }
              
              if(l_functionObject.getBoolean("modify")){
                  
                  userRole.functions[i].setModify("true");
              }else{
                  
                  userRole.functions[i].setModify("false");
              }
              
              if(l_functionObject.getBoolean("delete")){
                  
                  userRole.functions[i].setDelete("true");
              }else{
                  
                  userRole.functions[i].setDelete("false");
              }
              
              if(l_functionObject.getBoolean("view")){
                  
                  userRole.functions[i].setView("true");
              }else{
                  
                  userRole.functions[i].setView("false");
              }
              
              if(l_functionObject.getBoolean("auth")){
                  
                  userRole.functions[i].setAuth("true");
              }else{
                  
                  userRole.functions[i].setAuth("false");
              }
              
              if(l_functionObject.getBoolean("autoAuth")){
                  
                  userRole.functions[i].setAutoAuth("true");
              }else{
                  
                  userRole.functions[i].setAutoAuth("false");
              }
              
              if(l_functionObject.getBoolean("reject")){
                  
                  userRole.functions[i].setReject("true");
              }else{
                  
                  userRole.functions[i].setReject("false");
              }
              
//              userRole.functions[i].setCreate(l_functionObject.getString("create"));
//              userRole.functions[i].setModify(l_functionObject.getString("modify"));
//              userRole.functions[i].setDelete(l_functionObject.getString("delete"));
//              userRole.functions[i].setView(l_functionObject.getString("view"));
//              userRole.functions[i].setAuth(l_functionObject.getString("auth"));
//              userRole.functions[i].setAutoAuth(l_functionObject.getString("autoAuth"));
//              userRole.functions[i].setReject(l_functionObject.getString("reject"));
            }
        }
        reqBody.set(userRole);
       request.setReqBody(reqBody);
        dbg("end of UserroleService--->buildBOfromReq"); 
        }catch(Exception ex){
             dbg(ex);
            throw new BSProcessingException("Exception"+ex.toString());
        }
   }
 
private void buildBOfromDB(DBRecord p_masterRecord,Map<String, DBRecord> p_detailMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
            dbg("inside UserRoleService--->buildBOfromDB");
            ErrorHandler errhandler=session.getErrorhandler();
            RequestBody<UserRole> reqBody = request.getReqBody();
            BusinessService bs=inject.getBusinessService(session);
            UserRole userRole =reqBody.get();
            String l_roleID=userRole.getRoleID(); 
            ArrayList<String>l_masterList=p_masterRecord.getRecord();
            String l_instituteID=userRole.getInstituteID();
            String l_instituteName=bs.getInstituteName(l_instituteID, session, dbSession, inject);
            userRole.setInstituteName(l_instituteName);

            if(l_masterList!=null&&!l_masterList.isEmpty()){
                userRole.setRoleDescription(l_masterList.get(1).trim());
                request.getReqAudit().setMakerID(l_masterList.get(2).trim());
                request.getReqAudit().setCheckerID(l_masterList.get(3).trim());
                request.getReqAudit().setMakerDateStamp(l_masterList.get(4).trim());
                request.getReqAudit().setCheckerDateStamp(l_masterList.get(5).trim());
                request.getReqAudit().setRecordStatus(l_masterList.get(6).trim());
                request.getReqAudit().setAuthStatus(l_masterList.get(7).trim());
                request.getReqAudit().setVersionNumber(l_masterList.get(8).trim());
                request.getReqAudit().setMakerRemarks(l_masterList.get(9).trim());
                request.getReqAudit().setCheckerRemarks(l_masterList.get(10).trim());
            }else{
                errhandler.log_app_error("BS_VAL_013", "UVW_USER_ROLE_MASTER,"+l_roleID);
                throw new BSValidationException("BSValidationException");
            }
            
//            String instituteID=p_roleInstituteRecord.getRecord().get(1).trim();
//            userRole.setInstituteID(instituteID);
            
            String masterversionNumber=request.getReqAudit().getVersionNumber();
            if(p_detailMap!=null&&!p_detailMap.isEmpty()){
              Map<String,List<DBRecord>>l_detailFilterdMap=  p_detailMap.values().stream().filter(rec->rec.getRecord().get(0).trim().equals(l_roleID)&&rec.getRecord().get(9).trim().equals(masterversionNumber)).collect(Collectors.groupingBy(rec->rec.getRecord().get(0).trim()));
                
              if(l_detailFilterdMap!=null&&!l_detailFilterdMap.isEmpty()){
                userRole.functions=new Functions[l_detailFilterdMap.get(l_roleID).size()];
                int i=0;
                for(DBRecord l_roleRecords: l_detailFilterdMap.get(l_roleID)){
                   userRole.functions[i]=new Functions();
                   userRole.functions[i].setFunctionID(l_roleRecords.getRecord().get(1).trim());
                   userRole.functions[i].setCreate(l_roleRecords.getRecord().get(2).trim());
                   userRole.functions[i].setModify(l_roleRecords.getRecord().get(3).trim());
                   userRole.functions[i].setDelete(l_roleRecords.getRecord().get(4).trim());
                   userRole.functions[i].setView(l_roleRecords.getRecord().get(5).trim());
                   userRole.functions[i].setAuth(l_roleRecords.getRecord().get(6).trim());
                   userRole.functions[i].setAutoAuth(l_roleRecords.getRecord().get(7).trim());
                   userRole.functions[i].setReject(l_roleRecords.getRecord().get(8).trim()); 
                   
                   dbg("function id"+l_roleRecords.getRecord().get(1).trim());
                   dbg("create"+l_roleRecords.getRecord().get(2).trim());
                   dbg("modify"+l_roleRecords.getRecord().get(3).trim());
                   dbg("delete"+l_roleRecords.getRecord().get(4).trim());
                   dbg("view"+l_roleRecords.getRecord().get(5).trim());
                   dbg("auth"+l_roleRecords.getRecord().get(6).trim());
                   dbg("auto auth"+l_roleRecords.getRecord().get(7).trim());
                   dbg("reject"+l_roleRecords.getRecord().get(8).trim());
                   
                   
                i++;
               }    
              }else{
                errhandler.log_app_error("BS_VAL_013", "UVW_USER_ROLE_DETAIL,"+l_roleID);
                throw new BSValidationException("BSValidationException");
              }    
            }else{
                errhandler.log_app_error("BS_VAL_013", "UVW_USER_ROLE_DETAIL");
                throw new BSValidationException("BSValidationException");
            }
            
         dbg("End of UserRoleService--->buildBOfromDB");   
       
        }catch(BSValidationException ex){
                 
        throw ex;
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
 }
 //This method calls createRecord() method in DBTransactionService for inserting a record to the database
 public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
        try{ 
        dbg("Inside UserRoleService--->create"); 
        RequestBody<UserRole> reqBody = request.getReqBody();
        UserRole userRole =reqBody.get();
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
        String l_roleID=userRole.getRoleID();
        String l_roleDescription=userRole.getRoleDescription();
        
        
              dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",75,l_roleID,l_roleDescription,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
        
        String l_instituteID=userRole.getInstituteID();
                
              dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",306,l_roleID,l_instituteID,l_versionNumber);
        
        for(int i=0;i<userRole.functions.length;i++){
            String l_functionID=userRole.functions[i].getFunctionID();
            String l_create=userRole.functions[i].getCreate();
            String l_modify=userRole.functions[i].getModify();
            String l_delete=userRole.functions[i].getDelete();
            String l_view=userRole.functions[i].getView();
            String l_auth=userRole.functions[i].getAuth();
            String l_autoAuth=userRole.functions[i].getAutoAuth();
            String l_reject=userRole.functions[i].getReject();

               dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",77,l_roleID,l_functionID,l_create,l_modify,l_delete,l_view,l_auth,l_autoAuth,l_reject,l_versionNumber);
        }
        
        dbg("End of UserRoleService--->create"); 
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
 //This method updates a record in Db.This is called only for Auth operation
 public  void authUpdate()throws DBValidationException,DBProcessingException,BSProcessingException{
        try{ 
            dbg("Inside UserRoleService--->update"); 
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<UserRole> reqBody = request.getReqBody();
            UserRole userRole =reqBody.get();
            String l_roleID=userRole.getRoleID();
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
            Map<String,String>l_column_to_update=new HashMap();
            l_column_to_update.put("4", l_checkerID);
            l_column_to_update.put("6", l_checkerDateStamp);
            l_column_to_update.put("8", l_authStatus);
            l_column_to_update.put("11", l_checkerRemarks); 
             String[] l_primaryKey={l_roleID};
                        dbts.updateColumn("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_ROLE_MASTER", l_primaryKey, l_column_to_update, session);
                   
            dbg("end of UserRoleService--->update");              
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
 
 //This method updates a record in DB. This is used for unauthorized modification
 //Here all columns should be added 
 public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
       Map<String,String> l_column_to_update;
       
        try{ 
        dbg("inside UserRoleService--->fullUpdate");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<UserRole> reqBody = request.getReqBody();
        UserRole userRole =reqBody.get();
        
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();     
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        
        String l_roleID=userRole.getRoleID();
        String l_roleDescription=userRole.getRoleDescription();
        String[] l_primaryKey={l_roleID};
        l_column_to_update= new HashMap();
        l_column_to_update.put("1", l_roleID);
        l_column_to_update.put("2", l_roleDescription);
        l_column_to_update.put("3", l_makerID);
        l_column_to_update.put("4", l_checkerID);
        l_column_to_update.put("5", l_makerDateStamp);
        l_column_to_update.put("6", l_checkerDateStamp);
        l_column_to_update.put("7", l_recordStatus);
        l_column_to_update.put("8", l_authStatus);
        l_column_to_update.put("9", l_versionNumber);
        l_column_to_update.put("10", l_makerRemarks);
        l_column_to_update.put("11", l_checkerRemarks);        
        dbts.updateColumn("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_ROLE_MASTER", l_primaryKey, l_column_to_update, session);
        
//        String l_institueID=userRole.getInstituteID();
//        l_column_to_update= new HashMap();
//        l_column_to_update.put("1", l_roleID);
//        l_column_to_update.put("2", l_institueID);
//        String[] l_pKey={l_roleID,l_institueID};
//        
//        dbts.updateColumn("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_ROLE_INSTITUTE", l_pKey, l_column_to_update, session);
        
        
        Map<String,DBRecord>l_detailMap=readBuffer.readTable("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_ROLE_DETAIL",session,dbSession);
        setOperationsOfTheRecord("UVW_USER_ROLE_DETAIL",l_detailMap);    
        
        for(int i=0;i<userRole.functions.length;i++){
            String l_functionID=userRole.functions[i].getFunctionID();
            String l_create=userRole.functions[i].getCreate();
            String l_modify=userRole.functions[i].getModify();
            String l_delete=userRole.functions[i].getDelete();
            String l_view=userRole.functions[i].getView();
            String l_auth=userRole.functions[i].getAuth();
            String l_autoAuth=userRole.functions[i].getAutoAuth();
            String l_reject=userRole.functions[i].getReject();
            
            if(userRole.functions[i].getOperation().equals("U")){
        
                l_column_to_update= new HashMap();
                l_column_to_update.put("1", l_roleID);
                l_column_to_update.put("2", l_functionID);
                l_column_to_update.put("3", l_create);
                l_column_to_update.put("4", l_modify);
                l_column_to_update.put("5", l_delete);
                l_column_to_update.put("6", l_view);
                l_column_to_update.put("7", l_auth);
                l_column_to_update.put("8", l_autoAuth);
                l_column_to_update.put("9", l_reject);

                String[] l_detailPrimaryKey={l_roleID,l_functionID};
                            dbts.updateColumn("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_ROLE_DETAIL", l_detailPrimaryKey, l_column_to_update, session);
         
            }else{
                
                 dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",77,l_roleID,l_functionID,l_create,l_modify,l_delete,l_view,l_auth,l_autoAuth,l_reject,l_versionNumber);
                
            }
            }
        
        ArrayList<String>subjectList=getRecordsToDelete("UVW_USER_ROLE_DETAIL",l_detailMap);
        
        for(int i=0;i<subjectList.size();i++){
            String pkey=subjectList.get(i);
            deleteRecordsInTheList("UVW_USER_ROLE_DETAIL",pkey);
            
         }
        
        
            dbg("end of UserRoleService--->fullUpdate");   
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
            RequestBody<UserRole> reqBody = request.getReqBody();
            UserRole userRole =reqBody.get();
            String l_roleID=userRole.getRoleID();
            dbg("tableName"+tableName);
            
            switch(tableName){
                
                case "UVW_USER_ROLE_DETAIL":  
                
                         for(int i=0;i<userRole.functions.length;i++){
                                String l_functionID=userRole.functions[i].getFunctionID();
                                String l_pkey=l_roleID+"~"+l_functionID;
                               if(tableMap.containsKey(l_pkey)){

                                    userRole.functions[i].setOperation("U");
                                }else{

                                    userRole.functions[i].setOperation("C");
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
     
     
     private ArrayList<String>getRecordsToDelete(String tableName,Map<String,DBRecord>tableMap)throws DBValidationException,DBProcessingException,BSProcessingException{
         
         try{
           
           dbg("inside getRecordsToDelete");  
           RequestBody<UserRole> reqBody = request.getReqBody();
           UserRole userRole =reqBody.get();
           String l_roleID=userRole.getRoleID();
           ArrayList<String>recordsToDelete=new ArrayList();
//           Iterator<String>keyIterator=tableMap.keySet().iterator();
           
           List<DBRecord>filteredRecords=tableMap.values().stream().filter(rec->rec.getRecord().get(0).trim().equals(l_roleID)).collect(Collectors.toList());
           
           
           dbg("tableName"+tableName);
           switch(tableName){
           
                 case "UVW_USER_ROLE_DETAIL":   
                   
//                   while(keyIterator.hasNext()){
                     for(int j=0;j<filteredRecords.size();j++){
                        String functionID=filteredRecords.get(j).getRecord().get(1).trim();
                        String tablePkey= l_roleID+"~"+functionID;            
                        
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                        for(int i=0;i<userRole.functions.length;i++){
                           String l_functionID=userRole.functions[i].getFunctionID(); 
                           String l_requestPkey=l_roleID+"~"+l_functionID;
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
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IDBTransactionService dbts=inject.getDBTransactionService();
             String[] pkArr=pkey.split("~");
             
             
             dbts.deleteRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",tableName, pkArr, session); 
             
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
            dbg("Inside StudentMasterService--->delete");  
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<UserRole> reqBody = request.getReqBody();
            UserRole userRole =reqBody.get();
            String l_roleID=userRole.getRoleID();
            String[] l_primaryKey={l_roleID};
            
                 dbts.deleteRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_ROLE_MASTER", l_primaryKey, session);
            String l_instituteID= userRole.getInstituteID();
            String[] l_pkey={l_roleID,l_instituteID};     
       
            dbts.deleteRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_ROLE_INSTITUTE", l_pkey, session);
                   
             for(int i=0;i<userRole.functions.length;i++){
                 String l_functionID=userRole.functions[i].getFunctionID();
                 String[] l_detailPrimaryKey={l_roleID,l_functionID};
                 
                 dbts.deleteRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_ROLE_DETAIL", l_detailPrimaryKey, session);
                 
             }  
             dbg("End of UserRoleService--->delete");  
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
 
 public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        
        try{
            dbg("Inside UserRoleService--->buildJsonfromBO");
            RequestBody<UserRole> reqBody = request.getReqBody();
            UserRole userRole =reqBody.get();
            JsonArrayBuilder l_functionBuilder=Json.createArrayBuilder();
            
             for(int i=0;i<userRole.functions.length;i++){
                 
                 boolean create;
                 boolean modify;
                 boolean delete;
                 boolean view;
                 boolean auth;
                 boolean autoAuth;
                 boolean reject;
                 
                 
                 if(userRole.functions[i].getCreate().equals("true")){
                     create=true;
                 }else{
                     create=false;
                 }
                 
                  if(userRole.functions[i].getModify().equals("true")){
                     modify=true;
                 }else{
                     modify=false;
                 }
                  
                   if(userRole.functions[i].getDelete().equals("true")){
                     delete=true;
                 }else{
                     delete=false;
                 }
                 
                 if(userRole.functions[i].getView().equals("true")){
                     view=true;
                 }else{
                     view=false;
                 }
                 
                 if(userRole.functions[i].getAuth().equals("true")){
                     auth=true;
                 }else{
                     auth=false;
                 }
                  
                 if(userRole.functions[i].getAutoAuth().equals("true")){
                     autoAuth=true;
                 }else{
                     autoAuth=false;
                 }
                 
                 if(userRole.functions[i].getReject().equals("true")){
                     reject=true;
                 }else{
                     reject=false;
                 }
                  
//             l_functionBuilder.add(Json.createObjectBuilder().add("functionID", userRole.functions[i].getFunctionID())
//                                                             .add("create", userRole.functions[i].getCreate())
//                                                             .add("modify", userRole.functions[i].getModify())
//                                                             .add("delete",userRole.functions[i].getDelete())
//                                                             .add("view",userRole.functions[i].getView())
//                                                             .add("auth",userRole.functions[i].getAuth())
//                                                             .add("authAuth", userRole.functions[i].getAutoAuth()) 
//                                                             .add("reject", userRole.functions[i].getReject()));

               l_functionBuilder.add(Json.createObjectBuilder().add("functionID", userRole.functions[i].getFunctionID())
                                                             .add("create", create)
                                                             .add("modify", modify)
                                                             .add("delete",delete)
                                                             .add("view",view)
                                                             .add("auth",auth)
                                                             .add("autoAuth",autoAuth) 
                                                             .add("reject", reject));

                                                             
             }
            
             body=Json.createObjectBuilder().add("roleID", userRole.getRoleID())
                                            .add("roleDescription", userRole.getRoleDescription())
                                            .add("instituteID", userRole.getInstituteID())
                                            .add("instituteName", userRole.getInstituteName())
                                            .add("functions", l_functionBuilder)
                                            .build();
             
      dbg("End of UserRoleService--->buildJsonfromBO");               
      }catch (Exception ex) {
         dbg(ex);
         throw new BSProcessingException("Exception" + ex.toString());   
      }
      return body;
    }
 
 public void  view()throws DBValidationException,DBProcessingException,BSProcessingException{
        
                
        try{
            dbg("Inside StudentMasterService--->view");
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<UserRole> reqBody = request.getReqBody();
            UserRole userRole =reqBody.get();
            String[]l_pkey={userRole.getRoleID()};
            DBRecord l_masterRecord=null;
            Map<String, DBRecord>l_detailMap=null;
            try{
            
                l_masterRecord=readBuffer.readRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_ROLE_MASTER", l_pkey, session,dbSession);
                l_detailMap=readBuffer.readTable("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_ROLE_DETAIL",session,dbSession);
               
//                DBRecord l_roleInsRecord=readBuffer.readRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_ROLE_INSTITUTE", l_pkey, session,dbSession);
                
                }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", userRole.getRoleID());
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
                }

                buildBOfromDB(l_masterRecord,l_detailMap);
            
            
          
         
          dbg("end of  StudentMasterService--->view");               
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
     
    //This method validates the data in the request
     private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside UserRoleService--->businessValidation");  
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
           }else{
              if(!detailDataValidation(errhandler)){
               status=false;
              }
           
         }
       
       }

       dbg("UserRoleService--->businessValidation--->O/P--->status"+status);
       dbg("End of UserRoleService--->businessValidation");
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
     
 //This method performs mandatory validation in master data
  private boolean masterMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
        boolean status=true;
        try{
        dbg("inside UserRoleService--->masterMandatoryValidation");    
        RequestBody<UserRole> reqBody = request.getReqBody();
        UserRole userRole =reqBody.get();
        String operation=request.getReqHeader().getOperation();
        
        if(userRole.getRoleID()==null||userRole.getRoleID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","roleID");  
        }
        
        if(!operation.equals("View")){
        
            if(userRole.getRoleDescription()==null||userRole.getRoleDescription().isEmpty()){
                 status=false;  
                 errhandler.log_app_error("BS_VAL_002","roleDescription");  
            }
        }
        if(userRole.getInstituteID()==null||userRole.getInstituteID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","institute id");  
        }
        
        dbg("end of UserRoleService--->masterMandatoryValidation"); 
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
  
  private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         try{
             dbg("inside UserRoleService--->masterDataValidation");    
             RequestBody<UserRole> reqBody = request.getReqBody();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             UserRole userRole =reqBody.get();
             BSValidation bsv=inject.getBsv(session);
             String l_instituteID=userRole.getInstituteID();
             String l_operation=request.getReqHeader().getOperation();
             String roleID=userRole.getRoleID();
             IPDataService pds=inject.getPdataservice();
             
             if(!(bsv.instituteIDValidation(l_instituteID, errhandler, inject, session, dbSession))){
                      status=false;
                      errhandler.log_app_error("BS_VAL_003","instituteID");
             }
             
             if(l_operation.equals("Create")){
                 
                if(bsv.roleIDValidation(l_operation, session, dbSession, inject)) {
                     status=false;
                     errhandler.log_app_error("BS_VAL_031","Role id");
                }
             }else{
                 
                    try{
                          String[] pkey={roleID,l_instituteID};
                           pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User", "USER", "UVW_USER_ROLE_INSTITUTE",pkey);
                         
                         }catch(DBValidationException ex){ 
                             if(ex.toString().contains("DB_VAL_011")){
                                 errhandler.removeSessionErrCode("DB_VAL_011");
                                 errhandler.log_app_error("BS_VAL_003","Role id");
                                 status=false;
                             }else{
                                 throw ex;
                             }
                       }
                 
             }
             
             
        dbg("UserRoleService--->masterDataValidation--->O/P--->status"+status);
        dbg("End of UserRoleService--->masterDataValidation");
        
//         }catch (BSValidationException ex) {
//         
//             throw ex;
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
         }
        return status;
              
    }
    
     private boolean detailMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
        boolean status=true;
         try{
           dbg("inside UserRoleService--->detailMandatoryValidation");    
           RequestBody<UserRole> reqBody = request.getReqBody();
           UserRole userRole =reqBody.get();  
           
           if(userRole.functions==null||userRole.functions.length==0){
               status=false;
               errhandler.log_app_error("BS_VAL_002","functions");
           }else{
               for(int i=0;i<userRole.functions.length;i++){
                   
                    if(userRole.functions[i].getFunctionID()==null||userRole.functions[i].getFunctionID().isEmpty()){
                       status=false;
                       errhandler.log_app_error("BS_VAL_002","functionID");
                    }
                    if(userRole.functions[i].getCreate()==null||userRole.functions[i].getCreate().isEmpty()){
                       status=false;
                       errhandler.log_app_error("BS_VAL_002","create");
                    }
                    if(userRole.functions[i].getModify()==null||userRole.functions[i].getModify().isEmpty()){
                       status=false;
                       errhandler.log_app_error("BS_VAL_002","modify");
                    }
                    if(userRole.functions[i].getDelete()==null||userRole.functions[i].getDelete().isEmpty()){
                       status=false;
                       errhandler.log_app_error("BS_VAL_002","delete");
                    }
                    if(userRole.functions[i].getView()==null||userRole.functions[i].getView().isEmpty()){
                       status=false;
                       errhandler.log_app_error("BS_VAL_002","view");
                    }
                    if(userRole.functions[i].getAuth()==null||userRole.functions[i].getAuth().isEmpty()){
                       status=false;
                       errhandler.log_app_error("BS_VAL_002","auth");
                    }
                    if(userRole.functions[i].getAutoAuth()==null||userRole.functions[i].getAutoAuth().isEmpty()){
                       status=false;
                       errhandler.log_app_error("BS_VAL_002","autoAuth");
                    }
                    if(userRole.functions[i].getReject()==null||userRole.functions[i].getReject().isEmpty()){
                       status=false;
                       errhandler.log_app_error("BS_VAL_002","reject");
                    }
               }
               
           }
           dbg("UserRole--->detailMandatoryValidation--->O/P--->status"+status);
           dbg("End of UserRole--->detailMandatoryValidation");
          }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }    
     private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         try{
             dbg("inside UserRoleService--->detailMandatoryValidation");    
             RequestBody<UserRole> reqBody = request.getReqBody();
             UserRole userRole =reqBody.get();
             ArrayList<String> serviceList=new ArrayList();
             BSValidation bsv=inject.getBsv(session);
             
             for(int i=0;i<userRole.functions.length;i++){

                  String l_functionID=userRole.functions[i].getFunctionID();
                  
                if(!serviceList.contains(l_functionID)){    
                    
                    serviceList.add(l_functionID);
                }else{
                    
                    status=false;
                    errhandler.log_app_error("BS_VAL_031","FunctionID");
                    throw new BSValidationException("BSValidationException");
                }
                  
                  
             if(!(l_functionID.equals("ALL"))){     
                  
                 if(!bsv.serviceValidation(l_functionID, errhandler, inject, session, dbSession)){
                     
                     status=false;
                 }
             }    
                 
            if(!(userRole.functions[i].getCreate().equals("true")||userRole.functions[i].getCreate().equals("false"))){
               status=false;  
               errhandler.log_app_error("BS_VAL_003","create");  
            }
            if(!(userRole.functions[i].getModify().equals("true")||userRole.functions[i].getModify().equals("false"))){
               status=false;  
               errhandler.log_app_error("BS_VAL_003","modify");  
            }
            if(!(userRole.functions[i].getDelete().equals("true")||userRole.functions[i].getDelete().equals("false"))){
               status=false;  
               errhandler.log_app_error("BS_VAL_003","delete");  
            }
            if(!(userRole.functions[i].getView().equals("true")||userRole.functions[i].getView().equals("false"))){
               status=false;  
               errhandler.log_app_error("BS_VAL_003","view");  
            }
            if(!(userRole.functions[i].getAuth().equals("true")||userRole.functions[i].getAuth().equals("false"))){
               status=false;  
               errhandler.log_app_error("BS_VAL_003","auth");  
            }
            if(!(userRole.functions[i].getAutoAuth().equals("true")||userRole.functions[i].getAutoAuth().equals("false"))){
               status=false;  
               errhandler.log_app_error("BS_VAL_003","autoAuth");  
            }
            if(!(userRole.functions[i].getReject().equals("true")||userRole.functions[i].getReject().equals("false"))){
               status=false;  
               errhandler.log_app_error("BS_VAL_003","default-Validate");  
            }
            
           }  
        dbg("UserRoleService--->detailDataValidation--->O/P--->status"+status);
        dbg("End of UserRoleService--->detailDataValidation");
        
         }catch (BSValidationException ex) {
         
             throw ex;
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
         }
        return status;
              
    }
     
     
     public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     ExistingAudit exAudit=new ExistingAudit();
     try{
        dbg("inside UserRole--->getExistingAudit") ;
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_operation=request.getReqHeader().getOperation();
        String l_service=request.getReqHeader().getService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
        if(!(l_operation.equals("Create")||l_operation.equals("View"))){
             
               dbg("inside UserRole--->getExistingAudit");  
               RequestBody<UserRole> reqBody = request.getReqBody();
               UserRole userRole =reqBody.get();
               String[]l_pkey={userRole.getRoleID()};
               DBRecord l_userRoleRecord=readBuffer.readRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_ROLE_MASTER", l_pkey, session,dbSession);
               exAudit.setAuthStatus(l_userRoleRecord.getRecord().get(7).trim());
               exAudit.setMakerID(l_userRoleRecord.getRecord().get(2).trim());
               exAudit.setRecordStatus(l_userRoleRecord.getRecord().get(6).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_userRoleRecord.getRecord().get(8).trim()));


 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        
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
