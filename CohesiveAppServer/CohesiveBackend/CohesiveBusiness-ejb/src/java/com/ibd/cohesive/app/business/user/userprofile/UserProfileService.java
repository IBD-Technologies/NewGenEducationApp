
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.user.userprofile;

import com.ibd.businessViews.IUserProfileService;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.Oauth.AuthServer.ISecurityManagementService;
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
import javax.json.JsonObjectBuilder;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
//@Local(IUserProfileService.class)
@Remote(IUserProfileService.class)
@Stateless
public class UserProfileService implements IUserProfileService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    public UserProfileService() {
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
       BusinessService  bs;
       Parsing parser;
       ExceptionHandler exHandler;
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
       dbg("inside UserProfileService--->processing");
       dbg("UserProfileService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       String l_operation=request.getReqHeader().getOperation();
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson);  
       
       if(!l_operation.equals("Create-Default")){
       
           RequestBody<UserProfile> reqBody = request.getReqBody();
           UserProfile userProfile =reqBody.get();
           l_lockKey=userProfile.getUserID();
           if(!businessLock.getBusinessLock(request, l_lockKey, session)){
               l_validation_status=false;
               throw new BSValidationException("BSValidationException");
            }
        }
       
       
       BusinessEJB<IUserProfileService> userProfileEjb=new BusinessEJB();
       userProfileEjb.set(this);
       exAudit=bs.getExistingAudit(userProfileEjb);
       
       
       if(!(bsv.businessServiceValidation(clonedJson, exAudit, request, errhandler, inject, session, dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
       }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");

       } 
        
       
        
       bs.businessServiceProcssing(request, exAudit, inject,userProfileEjb);

       
       
        if(l_session_created_now){
           jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,userProfileEjb);
            tc.commit(session,dbSession);
        }
       dbg("UserProfileService--->Processing--->O/P--->jsonResponse"+jsonResponse);     
       dbg("End of UserProfileService--->processing");
       }catch(NamingException ex){
            dbg(ex);
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson,"BSProcessingException");
       }catch(BSValidationException ex){
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson,"BSValidationException");
       }catch(DBValidationException ex){
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson,"DBValidationException");
       }catch(DBProcessingException ex){
            dbg(ex);
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson,"DBProcessingException");
       }catch(BSProcessingException ex){
           dbg(ex);
           exHandler = inject.getErrorHandle(session);
           jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson,"BSProcessingException");
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
  
  
 
  //This method  builds business object from the json request
 private  void buildBOfromReq(JsonObject p_request)throws BSProcessingException,DBProcessingException,BSValidationException{
       
   try{
      dbg("inside UserprofileService--->buildBOfromReq"); 
      dbg("UserprofileService--->buildBOfromReq--->I/P--->p_request"+p_request.toString());  
      RequestBody<UserProfile> reqBody = new RequestBody<UserProfile>(); 
      BSValidation bsv=inject.getBsv(session);
      BusinessService bs=inject.getBusinessService(session);
      UserProfile userProfile = new UserProfile();
      JsonObject l_body=p_request.getJsonObject("body");      
      String l_operation=request.getReqHeader().getOperation();
      userProfile.setUserID(l_body.getString("userID"));
      if(!(l_operation.equals("View"))){
          
           userProfile.setUserName(l_body.getString("userName"));
           userProfile.setUserType(l_body.getString("userType"));
           userProfile.setEmailID("");
           userProfile.setMobileNo("");
           userProfile.setPassword(l_body.getString("password"));
           String expiryDate=bs.getExpiryDate();
           userProfile.setExpiryDate(expiryDate);
           userProfile.setStatus(l_body.getString("status"));
           userProfile.setHomeInstitue(l_body.getString("instituteID"));
           userProfile.setTeacherID(l_body.getString("teacherID"));
    
      if(userProfile.getUserType().equals("P")){
          JsonArray l_parentStudentMappingArray=l_body.getJsonArray("parentRoleMapping");
          userProfile.parentStudentMapping=new ParentStudentRoleMapping[l_parentStudentMappingArray.size()];
          for(int i=0;i<l_parentStudentMappingArray.size();i++){
               JsonObject l_parentRoleObject=l_parentStudentMappingArray.getJsonObject(i);
               userProfile.parentStudentMapping[i]=new ParentStudentRoleMapping();
               userProfile.parentStudentMapping[i].setRoleID(l_parentRoleObject.getString("roleID"));
               userProfile.parentStudentMapping[i].setStudentID(l_parentRoleObject.getString("studentID"));
               userProfile.parentStudentMapping[i].setInstituteID(l_parentRoleObject.getString("instituteID"));
            }
      }
          
     if(userProfile.getUserType().equals("T")||userProfile.getUserType().equals("A")){ 
      JsonArray l_classEntityRoleMappingArray=l_body.getJsonArray("studentClassRoleMapping");
      
          userProfile.classEntityRoleMapping=new ClassEntityRoleMapping[l_classEntityRoleMappingArray.size()]; 
          for(int i=0;i<l_classEntityRoleMappingArray.size();i++){
              JsonObject l_studentRoleObject=l_classEntityRoleMappingArray.getJsonObject(i);
              userProfile.classEntityRoleMapping[i]=new ClassEntityRoleMapping();
              userProfile.classEntityRoleMapping[i].setRoleID(l_studentRoleObject.getString("roleID"));
    //          userProfile.classEntityRoleMapping[i].setStandard(l_studentRoleObject.getString("standard"));
    //          userProfile.classEntityRoleMapping[i].setSection(l_studentRoleObject.getString("section"));
              String l_class=l_studentRoleObject.getString("class");
              bsv.classValidation(l_class,session);
              userProfile.classEntityRoleMapping[i].setStandard(l_class.split("/")[0]);
              userProfile.classEntityRoleMapping[i].setSection(l_class.split("/")[1]);
              userProfile.classEntityRoleMapping[i].setInstituteID(l_studentRoleObject.getString("instituteID"));
          }
     }
     
     
     if(userProfile.getUserType().equals("T")||userProfile.getUserType().equals("A")){ 
         JsonArray l_teacherMappingArray=l_body.getJsonArray("teacherRoleMapping");
           
         userProfile.teacherEntityMapping=new TeacherEntityRoleMapping[l_teacherMappingArray.size()]; 
         for(int i=0;i<l_teacherMappingArray.size();i++){
              JsonObject l_teacherRoleObject=l_teacherMappingArray.getJsonObject(i);
              userProfile.teacherEntityMapping[i]=new TeacherEntityRoleMapping();
              userProfile.teacherEntityMapping[i].setRoleID(l_teacherRoleObject.getString("roleID"));
              userProfile.teacherEntityMapping[i].setInstituteID(l_teacherRoleObject.getString("instituteID"));
              userProfile.teacherEntityMapping[i].setTeacherID(l_teacherRoleObject.getString("teacherID"));
         }
         
     }
      
     if(userProfile.getUserType().equals("T")||userProfile.getUserType().equals("A")){  
       JsonArray l_instituteMappingArray=l_body.getJsonArray("instituteRoleMapping");
       userProfile.instituteEntityMapping=new InstituteEntityRoleMapping[l_instituteMappingArray.size()]; 
       for(int i=0;i<l_instituteMappingArray.size();i++){
          JsonObject l_instituteRoleObject=l_instituteMappingArray.getJsonObject(i);
          userProfile.instituteEntityMapping[i]=new InstituteEntityRoleMapping();
          userProfile.instituteEntityMapping[i].setRoleID(l_instituteRoleObject.getString("roleID"));
          userProfile.instituteEntityMapping[i].setInstituteID(l_instituteRoleObject.getString("instituteID"));
          }
        }
     
      }
       reqBody.set(userProfile);
       request.setReqBody(reqBody);
      dbg("End of UserProfileService--->buildBOfromReq");
        }catch(BSValidationException ex){
         throw ex;
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException("Exception"+ex.toString());
        }
   }
 
 //This method builds business object from the database.This is called only for view operation
 private void buildBOfromDB(ArrayList<String> p_profileRecord,ArrayList<String> p_credentialRecord,Map<String,ArrayList<String>>p_parentStudentRoleMap,Map<String,ArrayList<String>>p_classEntityRoleMap,Map<String,ArrayList<String>>p_teacherEntityRoleMap,Map<String,ArrayList<String>>p_instituteEntityRoleMap,ArrayList<String> teacherIDMappingRecord)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{    
            dbg("inside UserProfileService--->buildBOfromDB");
            ErrorHandler errhandler=session.getErrorhandler();
            RequestBody<UserProfile> reqBody = request.getReqBody();
            UserProfile userProfile =reqBody.get();
            String l_userID=userProfile.getUserID();
            ArrayList<String>l_profileList=p_profileRecord;
            BusinessService bs=inject.getBusinessService(session);
            String l_instituteID=request.getReqHeader().getInstituteID();
            
            if(l_profileList!=null&&!l_profileList.isEmpty()){
                
                userProfile.setUserName(l_profileList.get(1).trim());
                userProfile.setEmailID(l_profileList.get(2).trim());
                userProfile.setMobileNo(l_profileList.get(3).trim());
                userProfile.setUserType(l_profileList.get(13).trim());
                userProfile.setStatus(l_profileList.get(14).trim());
                userProfile.setHomeInstitue(l_profileList.get(15).trim());
                String homeInstituteName=bs.getInstituteName(userProfile.getHomeInstitue(), session, dbSession, inject);
                userProfile.setHomeInstituteName(homeInstituteName);
                request.getReqAudit().setMakerID(l_profileList.get(4).trim());
                request.getReqAudit().setCheckerID(l_profileList.get(5).trim());
                request.getReqAudit().setMakerDateStamp(l_profileList.get(6).trim());
                request.getReqAudit().setCheckerDateStamp(l_profileList.get(7).trim());
                request.getReqAudit().setRecordStatus(l_profileList.get(8).trim());
                request.getReqAudit().setAuthStatus(l_profileList.get(9).trim());
                request.getReqAudit().setVersionNumber(l_profileList.get(10).trim());
                request.getReqAudit().setMakerRemarks(l_profileList.get(11).trim());
                request.getReqAudit().setCheckerRemarks(l_profileList.get(12).trim());
            }else{
                errhandler.log_app_error("BS_VAL_013", "UVW_USER_PROFILE,"+l_userID);
                throw new BSValidationException("BSValidationException");
            }
            
            ArrayList<String>l_credentialList=p_credentialRecord;
            
            if(l_credentialList!=null&&!l_credentialList.isEmpty()){
                userProfile.setPassword("**********");
                userProfile.setExpiryDate(l_credentialList.get(2).trim());
            }else{
                errhandler.log_app_error("BS_VAL_013", "UVW_USER_CREDENTIALS,"+l_userID);
                throw new BSValidationException("BSValidationException");
            }
            
            
            if(teacherIDMappingRecord!=null&&!teacherIDMappingRecord.isEmpty()){
                userProfile.setTeacherID(teacherIDMappingRecord.get(1).trim());
                dbg("teacherID of the user-->"+userProfile.getTeacherID());
                userProfile.setTeacherName(bs.getTeacherName(userProfile.getTeacherID(), l_instituteID, session, dbSession, inject));
            }else{
                userProfile.setTeacherID("");
                userProfile.setTeacherName("");
            }
            
            
            
            if(p_parentStudentRoleMap!=null&&!p_parentStudentRoleMap.isEmpty()){
              Map<String,List<ArrayList<String>>>l_parentStudentFilterdMap=  p_parentStudentRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(l_userID)&&rec.get(3).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
                
              if(l_parentStudentFilterdMap!=null&&!l_parentStudentFilterdMap.isEmpty()){
                userProfile.parentStudentMapping=new ParentStudentRoleMapping[l_parentStudentFilterdMap.get(l_userID).size()];
                int i=0;
                for(ArrayList<String> l_userRecords: l_parentStudentFilterdMap.get(l_userID)){
                   userProfile.parentStudentMapping[i]=new ParentStudentRoleMapping();
                   userProfile.parentStudentMapping[i].setRoleID(l_userRecords.get(1).trim());
                   userProfile.parentStudentMapping[i].setStudentID(l_userRecords.get(2).trim());
                   userProfile.parentStudentMapping[i].setInstituteID(l_userRecords.get(4).trim());
                   String instituteName=bs.getInstituteName(userProfile.parentStudentMapping[i].getInstituteID(), session, dbSession, inject);
                   userProfile.parentStudentMapping[i].setInstituteName(instituteName);
                   
                   i++;
               }    
              }    
            }      
            
            if(p_classEntityRoleMap!=null&&!p_classEntityRoleMap.isEmpty()){
              Map<String,List<ArrayList<String>>>l_classEntityFilterdMap=  p_classEntityRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(l_userID)&&rec.get(4).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
                
              if(l_classEntityFilterdMap!=null&&!l_classEntityFilterdMap.isEmpty()){
                userProfile.classEntityRoleMapping=new ClassEntityRoleMapping[l_classEntityFilterdMap.get(l_userID).size()];
                int i=0;
                for(ArrayList<String> l_userRecords: l_classEntityFilterdMap.get(l_userID)){
                   userProfile.classEntityRoleMapping[i]=new ClassEntityRoleMapping();
                   userProfile.classEntityRoleMapping[i].setRoleID(l_userRecords.get(1).trim());
                   userProfile.classEntityRoleMapping[i].setStandard(l_userRecords.get(2).trim());
                   userProfile.classEntityRoleMapping[i].setSection(l_userRecords.get(3).trim());
                   userProfile.classEntityRoleMapping[i].setInstituteID(l_userRecords.get(5).trim());
                   
                   String instituteName=bs.getInstituteName(userProfile.classEntityRoleMapping[i].getInstituteID(), session, dbSession, inject);
                   userProfile.classEntityRoleMapping[i].setInstituteName(instituteName);
                i++;
               }    
              }    
            } 

            if(p_teacherEntityRoleMap!=null&&!p_teacherEntityRoleMap.isEmpty()){
              Map<String,List<ArrayList<String>>>l_teacherFilterdMap=  p_teacherEntityRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(l_userID)&&rec.get(4).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
                
             if(l_teacherFilterdMap!=null&&!l_teacherFilterdMap.isEmpty()){
                userProfile.teacherEntityMapping=new TeacherEntityRoleMapping[l_teacherFilterdMap.get(l_userID).size()];
                int i=0;
                for(ArrayList<String> l_userRecords: l_teacherFilterdMap.get(l_userID)){
                   userProfile.teacherEntityMapping[i]=new TeacherEntityRoleMapping();
                   userProfile.teacherEntityMapping[i].setRoleID(l_userRecords.get(1).trim());
                   userProfile.teacherEntityMapping[i].setInstituteID(l_userRecords.get(2).trim());
                   userProfile.teacherEntityMapping[i].setTeacherID(l_userRecords.get(3).trim());
                   
                   if(userProfile.teacherEntityMapping[i].getTeacherID().equals("ALL")){
                       userProfile.teacherEntityMapping[i].setTeacherName("ALL");
                   }
                   
                   String instituteName=bs.getInstituteName(userProfile.teacherEntityMapping[i].getInstituteID(), session, dbSession, inject);
                   userProfile.teacherEntityMapping[i].setInstituteName(instituteName);
                i++;
               }    
              }    
            } 
            
            if(p_instituteEntityRoleMap!=null&&!p_instituteEntityRoleMap.isEmpty()){
              Map<String,List<ArrayList<String>>>l_instituteFilterdMap=  p_instituteEntityRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(l_userID)&&rec.get(3).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
                
             if(l_instituteFilterdMap!=null&&!l_instituteFilterdMap.isEmpty()){
                userProfile.instituteEntityMapping=new InstituteEntityRoleMapping[l_instituteFilterdMap.get(l_userID).size()];
                int i=0;
                for(ArrayList<String> l_userRecords: l_instituteFilterdMap.get(l_userID)){
                   userProfile.instituteEntityMapping[i]=new InstituteEntityRoleMapping();
                   userProfile.instituteEntityMapping[i].setRoleID(l_userRecords.get(1).trim());
                   userProfile.instituteEntityMapping[i].setInstituteID(l_userRecords.get(2).trim());
                   String instituteName=bs.getInstituteName(userProfile.instituteEntityMapping[i].getInstituteID(), session, dbSession, inject);
                   userProfile.instituteEntityMapping[i].setInstituteName(instituteName);
                   
                   i++;
               }    
              }    
            } 
           
           dbg("End of UserProfileService--->buildBOfromDB");       
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
        dbg("Inside UserProfileService--->create"); 
        RequestBody<UserProfile> reqBody = request.getReqBody();
        UserProfile userProfile =reqBody.get();
        String headerUserID=request.getReqHeader().getUserID();
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        ISecurityManagementService security=inject.getSecurityManagementService();
        IPDataService pds=inject.getPdataservice();
        
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_userID=userProfile.getUserID();
        String l_userName=userProfile.getUserName();
        String l_emailID=userProfile.getEmailID();
        String l_mobileNo=userProfile.getMobileNo();
        String l_userType=userProfile.getUserType();
        String l_status=userProfile.getStatus();
        String l_homeInstitue=userProfile.getHomeInstitue();
        
               dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",5,l_userID,l_userName,l_emailID,l_mobileNo,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks,l_userType,l_status,l_homeInstitue);
        String teacherID=userProfile.getTeacherID();
               
               dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",343,l_userID,teacherID,l_versionNumber);
               
        if(!headerUserID.equals("System")){       
               
            if(l_versionNumber.equals("1")){
            
             Map<String,ArrayList<String>>userContactMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_CONTRACT_MASTER", session, dbSession);
                   dbg("userContactMap size"+userContactMap.size());
                   Map<String,List<ArrayList<String>>>contactGroup=   userContactMap.values().stream().filter(rec->rec.get(0).trim().equals(headerUserID)).collect(Collectors.groupingBy(rec->rec.get(1).trim()));
                   dbg("contactGroup size"+contactGroup.size());
                   Iterator<String>contractIterator=contactGroup.keySet().iterator();

                   while(contractIterator.hasNext()){

                       String contractID=contractIterator.next();
                       dbg("contractID"+contractID);
            
                       dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",305,l_userID,contractID);
                   }
            }
        }       
               
       if(l_versionNumber.equals("1")){        
        String l_password=userProfile.getPassword();
        String l_salt=security.generateSalt(512, session).get();
        String l_hashPassword=security.hashPassword(l_password, l_salt, session).get();
        
        String l_expiryDate=userProfile.getExpiryDate();
               dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",76,l_userID,l_hashPassword,l_expiryDate,l_versionNumber,l_salt);
        
       }
        if(userProfile.parentStudentMapping!=null){
           for(int i=0;i<userProfile.parentStudentMapping.length;i++){
               String l_roleID=userProfile.parentStudentMapping[i].getRoleID();
               String l_studentID=userProfile.parentStudentMapping[i].getStudentID();
               String l_instituteID=userProfile.parentStudentMapping[i].getInstituteID();
            
                dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",80,l_userID,l_roleID,l_studentID,l_versionNumber,l_instituteID);
            }
        }
            
        if(userProfile.classEntityRoleMapping!=null){    
            for(int i=0;i<userProfile.classEntityRoleMapping.length;i++){
               String l_roleID=userProfile.classEntityRoleMapping[i].getRoleID();
               String l_standard=userProfile.classEntityRoleMapping[i].getStandard();
               String l_section=userProfile.classEntityRoleMapping[i].getSection();
               String l_instituteID=userProfile.classEntityRoleMapping[i].getInstituteID();
            
                  dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",81,l_userID,l_roleID,l_standard,l_section,l_versionNumber,l_instituteID);
        }
        }
          
        if(userProfile.teacherEntityMapping!=null){
            for(int i=0;i<userProfile.teacherEntityMapping.length;i++){
                String l_roleID=userProfile.teacherEntityMapping[i].getRoleID();
                String l_instituteID=userProfile.teacherEntityMapping[i].getInstituteID();
                String l_teacherID=userProfile.teacherEntityMapping[i].getTeacherID();
                dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",82,l_userID,l_roleID,l_instituteID,l_teacherID,l_versionNumber);
            }
        }
         
        if(userProfile.instituteEntityMapping!=null){
            for(int i=0;i<userProfile.instituteEntityMapping.length;i++){
                String l_roleID=userProfile.instituteEntityMapping[i].getRoleID();
                String l_instituteID=userProfile.instituteEntityMapping[i].getInstituteID();
                dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",83,l_userID,l_roleID,l_instituteID,l_versionNumber);
            }
        }

        dbg("End of UserProfileService--->create"); 
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
 public void authUpdate()throws DBValidationException,DBProcessingException,BSProcessingException{
        try{ 
            dbg("Inside UserProfileService--->update"); 
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<UserProfile> reqBody = request.getReqBody();
            UserProfile userProfile =reqBody.get();
            String l_userID=userProfile.getUserID();
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
            Map<String,String>  l_column_to_update=new HashMap();
             l_column_to_update.put("6", l_checkerID);
             l_column_to_update.put("8", l_checkerDateStamp);
             l_column_to_update.put("10", l_authStatus);
             l_column_to_update.put("13", l_checkerRemarks); 
            String[] l_primaryKey={l_userID};//Shouldn't not include version number in primary key
                        dbts.updateColumn("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_PROFILE", l_primaryKey, l_column_to_update, session);

                        
           dbg("end of UserProfileService--->update");             
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
        dbg("inside UserProfileService--->fullUpdate");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IPDataService pds=inject.getPdataservice();
        ISecurityManagementService security=inject.getSecurityManagementService();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        RequestBody<UserProfile> reqBody = request.getReqBody();
        UserProfile userProfile =reqBody.get();
        
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();        

        String l_userID=userProfile.getUserID();
        String l_userName=userProfile.getUserName();
        String l_emailID=userProfile.getEmailID();
        String l_mobileNo=userProfile.getMobileNo(); 
        String l_userType=userProfile.getUserType();
        String l_status=userProfile.getStatus();
        String l_homeInstitute=userProfile.getHomeInstitue();
        
        //include all columns of the table
        l_column_to_update= new HashMap();
        l_column_to_update.put("1", l_userID);
        l_column_to_update.put("2", l_userName);
        l_column_to_update.put("3", l_emailID);
        l_column_to_update.put("4", l_mobileNo);
        l_column_to_update.put("5", l_makerID);
        l_column_to_update.put("6", l_checkerID);
        l_column_to_update.put("7", l_makerDateStamp);
        l_column_to_update.put("8", l_checkerDateStamp);
        l_column_to_update.put("9", l_recordStatus);
        l_column_to_update.put("10", l_authStatus);
        l_column_to_update.put("11", l_versionNumber);
        l_column_to_update.put("12", l_makerRemarks);
        l_column_to_update.put("13", l_checkerRemarks);
        l_column_to_update.put("14", l_userType);
        l_column_to_update.put("15", l_status);
        l_column_to_update.put("16", l_homeInstitute);
        String[] l_primaryKey={l_userID};//Shouldn't not include version number in primary key
              dbts.updateColumn("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_PROFILE", l_primaryKey, l_column_to_update, session);
      
        boolean recordExistence=false;
             try{
            
                pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_TEACHER_ID_MAPPING", l_primaryKey);
        
                recordExistence=true;
                }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        recordExistence=false;
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                    }else{
                        
                        throw ex;
                    }
                }    
              String teacherID=userProfile.getTeacherID();
              if(recordExistence){
                  
                   l_column_to_update= new HashMap();  
                   l_column_to_update.put("2", teacherID);
                   
                   dbts.updateColumn("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_TEACHER_ID_MAPPING", l_primaryKey, l_column_to_update, session);
                  
              }else{
                  
                  dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",343,l_userID,teacherID,l_versionNumber);
              }
              
//        String l_password=userProfile.getPassword();
//        String l_expiryDate=userProfile.getExpiryDate();
//        String l_salt=security.generateSalt(512, session).get();
//        String l_hashPassword=security.hashPassword(l_password, l_salt, session).get();
//        //include all columns
//        l_column_to_update= new HashMap();  
//        l_column_to_update.put("1", l_userID);
//        l_column_to_update.put("2", l_hashPassword);
//        l_column_to_update.put("3", l_expiryDate);
//        l_column_to_update.put("5", l_salt);
//        
//        dbts.updateColumn("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_CREDENTIALS", l_primaryKey, l_column_to_update, session);
     
        
        if(l_userType.equals("P")){
            
            Map<String,ArrayList<String>>l_parentStudentRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_PARENT_STUDENT_ROLEMAPPING",session,dbSession);
            setOperationsOfTheRecord("UVW_PARENT_STUDENT_ROLEMAPPING",l_parentStudentRoleMap);
            
                
                 for(int i=0;i<userProfile.parentStudentMapping.length;i++){
                    String l_studentID=userProfile.parentStudentMapping[i].getStudentID();
                    String l_roleID=userProfile.parentStudentMapping[i].getRoleID();
                    String l_instituteID=userProfile.parentStudentMapping[i].getInstituteID();

                    if(userProfile.parentStudentMapping[i].getOperation().equals("U")){

                        l_column_to_update= new HashMap();      
                        l_column_to_update.put("1", l_userID);
                        l_column_to_update.put("2", l_roleID);
                        l_column_to_update.put("3", l_studentID);
                        String[] l_parentPKey={l_userID,l_studentID,l_instituteID}; //Shouldn't not include version number in primary key

                           dbts.updateColumn("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_PARENT_STUDENT_ROLEMAPPING", l_parentPKey, l_column_to_update, session);

                    }else{

                          dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",80,l_userID,l_roleID,l_studentID,l_versionNumber);

                    }       
                  }
                 
                 
                 ArrayList<String>parentList=getRecordsToDelete("UVW_PARENT_STUDENT_ROLEMAPPING",l_parentStudentRoleMap);
                 
                 for(int i=0;i<parentList.size();i++){
                     
                    String pkey=parentList.get(i);
                    deleteRecordsInTheList("UVW_PARENT_STUDENT_ROLEMAPPING",pkey);
                 }
                 
           }
//        else if(l_userType.equals("T")){
//               
//               performOperationsInClassAndTeacherEntities();
//               
//            
//           }
           
           else{
               
               performOperationsInClassAndTeacherEntities();
               
               Map<String,ArrayList<String>>l_instituteEntityRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_INSTITUTE_ENTITY_ROLEMAPPING",session,dbSession);
               setOperationsOfTheRecord("UVW_INSTITUTE_ENTITY_ROLEMAPPING",l_instituteEntityRoleMap);
               
               for(int i=0;i<userProfile.instituteEntityMapping.length;i++){
               String l_roleID=userProfile.instituteEntityMapping[i].getRoleID();
               String l_instituteID=userProfile.instituteEntityMapping[i].getInstituteID();
               
                   if(userProfile.instituteEntityMapping[i].getOperation().equals("U")){

                           l_column_to_update= new HashMap();  
                           l_column_to_update.put("1", l_userID);
                           l_column_to_update.put("2", l_roleID);
                           l_column_to_update.put("3", l_instituteID);

                            String[] l_institutePKey={l_userID,l_roleID,l_instituteID};//Shouldn't not include version number in primary key
                            dbts.updateColumn("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_INSTITUTE_ENTITY_ROLEMAPPING", l_institutePKey, l_column_to_update, session);
                   }else{

                            dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",83,l_userID,l_roleID,l_instituteID,l_versionNumber);
                   }
               
               }
               
             ArrayList<String>institutionList=getRecordsToDelete("UVW_INSTITUTE_ENTITY_ROLEMAPPING",l_instituteEntityRoleMap);

             for(int i=0;i<institutionList.size();i++){
                 String pkey=institutionList.get(i);
                 deleteRecordsInTheList("UVW_INSTITUTE_ENTITY_ROLEMAPPING",pkey);
             }
               
           }
        

        
        dbg("end of UserProfileService--->fullUpdate");   
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
 
 
 private void performOperationsInClassAndTeacherEntities()throws BSProcessingException,DBValidationException,DBProcessingException{
     Map<String,String> l_column_to_update;
     try{
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IPDataService pds=inject.getPdataservice();
        RequestBody<UserProfile> reqBody = request.getReqBody();
        UserProfile userProfile =reqBody.get();
        String l_userID=userProfile.getUserID();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
         
          Map<String,ArrayList<String>>l_classEntitytRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_CLASS_ENTITY_ROLEMAPPING",session,dbSession);
               Map<String,ArrayList<String>>l_teacherEntityRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_TEACHER_ENTITY_ROLEMAPPING",session,dbSession);
               setOperationsOfTheRecord("UVW_CLASS_ENTITY_ROLEMAPPING",l_classEntitytRoleMap);
               setOperationsOfTheRecord("UVW_TEACHER_ENTITY_ROLEMAPPING",l_teacherEntityRoleMap);
                   
                    for(int i=0;i<userProfile.classEntityRoleMapping.length;i++){
                       String l_roleID=userProfile.classEntityRoleMapping[i].getRoleID();
                       String l_standard=userProfile.classEntityRoleMapping[i].getStandard();
                       String l_section=userProfile.classEntityRoleMapping[i].getSection();
                       String l_instituteID=userProfile.classEntityRoleMapping[i].getInstituteID();

                       if(userProfile.classEntityRoleMapping[i].getOperation().equals("U")){

                               l_column_to_update= new HashMap();  
                               l_column_to_update.put("1", l_userID);
                               l_column_to_update.put("2", l_roleID);
                               l_column_to_update.put("3", l_standard);
                               l_column_to_update.put("4", l_section);
                               l_column_to_update.put("6", l_instituteID);

                               String[] l_classStudentPKey={l_userID,l_roleID,l_instituteID,l_standard,l_section};//Shouldn't not include version number in primary key
                                  dbts.updateColumn("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_CLASS_ENTITY_ROLEMAPPING", l_classStudentPKey, l_column_to_update, session);

                       }else{

                               dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",81,l_userID,l_roleID,l_standard,l_section,l_versionNumber);

                       }          
                   }

            for(int i=0;i<userProfile.teacherEntityMapping.length;i++){
                String l_roleID=userProfile.teacherEntityMapping[i].getRoleID();
                String l_instituteID=userProfile.teacherEntityMapping[i].getInstituteID();
                String l_teacherID=userProfile.teacherEntityMapping[i].getTeacherID();
                
                if(userProfile.teacherEntityMapping[i].getOperation().equals("U")){
                
                        l_column_to_update= new HashMap();   
                        l_column_to_update.put("1", l_userID);
                        l_column_to_update.put("2", l_roleID);
                        l_column_to_update.put("3", l_instituteID);
                        l_column_to_update.put("4", l_teacherID);

                        String[] l_teacherPKey={l_userID,l_roleID,l_instituteID,l_teacherID};//Shouldn't not include version number in primary key
                           dbts.updateColumn("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_TEACHER_ENTITY_ROLEMAPPING", l_teacherPKey, l_column_to_update, session);
                }else{
                    
                       dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER",82,l_userID,l_roleID,l_instituteID,l_teacherID,l_versionNumber);
                }
            }
               
            ArrayList<String>classList=getRecordsToDelete("UVW_CLASS_ENTITY_ROLEMAPPING",l_classEntitytRoleMap);
            ArrayList<String>teacherList=getRecordsToDelete("UVW_TEACHER_ENTITY_ROLEMAPPING",l_teacherEntityRoleMap);
            
            for(int i=0;i<classList.size();i++){
                String pkey=classList.get(i);
                deleteRecordsInTheList("UVW_CLASS_ENTITY_ROLEMAPPING",pkey);
            }
            
            for(int i=0;i<teacherList.size();i++){
                String pkey=teacherList.get(i);
                deleteRecordsInTheList("UVW_TEACHER_ENTITY_ROLEMAPPING",pkey);

            }
         
         
         
         
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
            RequestBody<UserProfile> reqBody = request.getReqBody();
            UserProfile userProfile =reqBody.get();
            String l_userID=userProfile.getUserID();
            dbg("tableName"+tableName);
            
            switch(tableName){
                
                case "UVW_PARENT_STUDENT_ROLEMAPPING":  
                
                       if(userProfile.parentStudentMapping!=null){
                    
                             for(int i=0;i<userProfile.parentStudentMapping.length;i++){
                                    String l_roleID=userProfile.parentStudentMapping[i].getRoleID();
                                    String l_instituteID=userProfile.parentStudentMapping[i].getInstituteID();
                                    String l_pkey=l_userID+"~"+l_roleID+"~"+l_instituteID;

                                   if(tableMap.containsKey(l_pkey)){

                                        userProfile.parentStudentMapping[i].setOperation("U");
                                    }else{

                                        userProfile.parentStudentMapping[i].setOperation("C");
                                    }
                             } 
                       }
                         
                         
                  break;      
                  
                  
                  case "UVW_CLASS_ENTITY_ROLEMAPPING":  
                
                    if(userProfile.classEntityRoleMapping!=null){  
                         for(int i=0;i<userProfile.classEntityRoleMapping.length;i++){
                                String l_roleID=userProfile.classEntityRoleMapping[i].getRoleID();
                                String l_instituteID=userProfile.classEntityRoleMapping[i].getInstituteID();
                                String l_standard=userProfile.classEntityRoleMapping[i].getStandard();
                                String l_section=userProfile.classEntityRoleMapping[i].getSection();
                                String l_pkey=l_userID+"~"+l_roleID+"~"+l_instituteID+"~"+l_standard+"~"+l_section;
                               if(tableMap.containsKey(l_pkey)){

                                    userProfile.classEntityRoleMapping[i].setOperation("U");
                                }else{

                                    userProfile.classEntityRoleMapping[i].setOperation("C");
                                }
                         } 
                         
                    }
                  break;
                  
                  
                  case "UVW_TEACHER_ENTITY_ROLEMAPPING":  
                
                      if(userProfile.teacherEntityMapping!=null){ 
                         for(int i=0;i<userProfile.teacherEntityMapping.length;i++){
                                String l_roleID=userProfile.teacherEntityMapping[i].getRoleID();
                                String l_instituteID=userProfile.teacherEntityMapping[i].getInstituteID();
                                String l_teacherID=userProfile.teacherEntityMapping[i].getTeacherID();
                                String l_pkey=l_userID+"~"+l_roleID+"~"+l_instituteID+"~"+l_teacherID;
                               if(tableMap.containsKey(l_pkey)){

                                    userProfile.teacherEntityMapping[i].setOperation("U");
                                }else{

                                    userProfile.teacherEntityMapping[i].setOperation("C");
                                }
                         } 
                         
                      }
                   break;
                  
                   
                   case "UVW_INSTITUTE_ENTITY_ROLEMAPPING":  
                
                      if(userProfile.instituteEntityMapping!=null){ 
                         for(int i=0;i<userProfile.instituteEntityMapping.length;i++){
                                String l_roleID=userProfile.instituteEntityMapping[i].getRoleID();
                                String l_instituteID=userProfile.instituteEntityMapping[i].getInstituteID();
                                String l_pkey=l_userID+"~"+l_roleID+"~"+l_instituteID;
                               if(tableMap.containsKey(l_pkey)){

                                    userProfile.instituteEntityMapping[i].setOperation("U");
                                }else{

                                    userProfile.instituteEntityMapping[i].setOperation("C");
                                }
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
           RequestBody<UserProfile> reqBody = request.getReqBody();
           UserProfile userProfile =reqBody.get();
           String l_userID=userProfile.getUserID();
           ArrayList<String>recordsToDelete=new ArrayList();
//           Iterator<String>keyIterator=tableMap.keySet().iterator();
           
           List<ArrayList<String>>filteredRecords=tableMap.values().stream().filter(rec->rec.get(0).trim().equals(l_userID)).collect(Collectors.toList());
           
        
           dbg("tableName"+tableName);
           switch(tableName){
           
                 case "UVW_PARENT_STUDENT_ROLEMAPPING":   
                  
                 if(userProfile.parentStudentMapping!=null){   
                     
                   for(int j=0;j<filteredRecords.size();j++){
                        String roleID=filteredRecords.get(j).get(1).trim();
                        String institueID=filteredRecords.get(j).get(4).trim();
                        String tablePkey=l_userID+"~"+roleID+"~"+institueID;
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                        for(int i=0;i<userProfile.parentStudentMapping.length;i++){
                           String l_roleID=userProfile.parentStudentMapping[i].getRoleID(); 
                           String l_instituteID=userProfile.parentStudentMapping[i].getInstituteID();
                                   
                           String l_requestPkey=l_userID+"~"+l_roleID+"~"+l_instituteID;
                           dbg("l_requestPkey"+l_requestPkey);
                           if(tablePkey.equals(l_requestPkey)){
                               recordExistence=true;
                           }
                        }   
                        if(!recordExistence){
                            recordsToDelete.add(tablePkey);
                        }

                    }
                   
                  }
                   
                   break;
                   
                   
                case "UVW_CLASS_ENTITY_ROLEMAPPING":   
                
                 if(userProfile.classEntityRoleMapping!=null){    
                    
                   for(int j=0;j<filteredRecords.size();j++){
                        String roleID=filteredRecords.get(j).get(1).trim();
                        String instituteID=filteredRecords.get(j).get(5).trim();
                        String standard=filteredRecords.get(j).get(2).trim();
                        String section=filteredRecords.get(j).get(3).trim();
                        String tablePkey=l_userID+"~"+roleID+"~"+instituteID+"~"+standard+"~"+section; 
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                        for(int i=0;i<userProfile.classEntityRoleMapping.length;i++){
                           String l_roleID=userProfile.classEntityRoleMapping[i].getRoleID(); 
                           String l_instituteID=userProfile.classEntityRoleMapping[i].getInstituteID();
                           String l_standard=userProfile.classEntityRoleMapping[i].getStandard();
                           String l_section=userProfile.classEntityRoleMapping[i].getSection();
                           String l_requestPkey=l_userID+"~"+l_roleID+"~"+l_instituteID+"~"+l_standard+"~"+l_section;
                           dbg("l_requestPkey"+l_requestPkey);
                           if(tablePkey.equals(l_requestPkey)){
                               recordExistence=true;
                           }
                        }   
                        if(!recordExistence){
                            recordsToDelete.add(tablePkey);
                        }

                    }
                   
                 }
                   break;   
                           
                   case "UVW_TEACHER_ENTITY_ROLEMAPPING":   
                   
                    if(userProfile.teacherEntityMapping!=null){    
                       
                       for(int j=0;j<filteredRecords.size();j++){
                        String roleID=filteredRecords.get(j).get(1).trim();
                        String instituteID=filteredRecords.get(j).get(2).trim();
                        String teacherID=filteredRecords.get(j).get(3).trim();
                        String tablePkey=l_userID+"~"+roleID+"~"+instituteID+"~"+teacherID;
                            dbg("tablePkey"+tablePkey);
                            boolean recordExistence=false;

                           for(int i=0;i<userProfile.teacherEntityMapping.length;i++){
                               String l_roleID=userProfile.teacherEntityMapping[i].getRoleID(); 
                               String l_instituteID=userProfile.teacherEntityMapping[i].getInstituteID();
                               String l_teacherID=userProfile.teacherEntityMapping[i].getTeacherID();
                               String l_requestPkey=l_userID+"~"+l_roleID+"~"+l_instituteID+"~"+l_teacherID;
                               dbg("l_requestPkey"+l_requestPkey);
                               if(tablePkey.equals(l_requestPkey)){
                                   recordExistence=true;
                               }
                            }   
                            if(!recordExistence){
                                recordsToDelete.add(tablePkey);
                            }

                        }
                       
                    }
                       break;   
                       
                     case "UVW_INSTITUTE_ENTITY_ROLEMAPPING":   
                   
                     if(userProfile.instituteEntityMapping!=null){     
                         
                       for(int j=0;j<filteredRecords.size();j++){
                        String roleID=filteredRecords.get(j).get(1).trim();
                        String instituteID=filteredRecords.get(j).get(2).trim();
                        String tablePkey=l_userID+"~"+roleID+"~"+instituteID; 
                            dbg("tablePkey"+tablePkey);
                            boolean recordExistence=false;

                           for(int i=0;i<userProfile.instituteEntityMapping.length;i++){
                               String l_roleID=userProfile.instituteEntityMapping[i].getRoleID(); 
                               String l_instituteID=userProfile.instituteEntityMapping[i].getInstituteID();
                               String l_requestPkey=l_userID+"~"+l_roleID+"~"+l_instituteID;
                               dbg("l_requestPkey"+l_requestPkey);
                               if(tablePkey.equals(l_requestPkey)){
                                   recordExistence=true;
                               }
                            }   
                            if(!recordExistence){
                                recordsToDelete.add(tablePkey);
                            }

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
 
 
 
 //This method calls deleteRecord() in DBTransactionService for removing a record 
 public void delete()throws DBValidationException,DBProcessingException,BSProcessingException{
 
        try{ 
            dbg("Inside UserProfile--->delete");  
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<UserProfile> reqBody = request.getReqBody();
            UserProfile userProfile =reqBody.get();
            String l_userID=userProfile.getUserID();
            IPDataService pds=inject.getPdataservice();
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
             
            String[] l_primaryKey={l_userID};//Shouldn't not include version number in primary key
                     dbts.deleteRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_PROFILE", l_primaryKey, session);
                     dbts.deleteRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_CREDENTIALS", l_primaryKey, session);  
                     dbts.deleteRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_TEACHER_ID_MAPPING", l_primaryKey, session);
                     
             Map<String,ArrayList<String>>l_contractMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_CONTRACT_MASTER",session,dbSession);        
             dbg("l_contractMap size-->"+l_contractMap.size());        
             Map<String,List<ArrayList<String>>>userFilteredRecords= l_contractMap.values().stream().filter(rec->rec.get(0).trim().equals(l_userID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+"~"+rec.get(1).trim()));
             dbg("userFilteredRecords size-->"+userFilteredRecords.size());              
             Iterator<String>keyIterator=userFilteredRecords.keySet().iterator();
            
             while(keyIterator.hasNext()){
                
                String[] pkey=keyIterator.next().split("~");
                dbg("pkey"+pkey[0]+pkey[1]);
                
                dbts.deleteRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_CONTRACT_MASTER", pkey, session);
                
            }
                     
           if(userProfile.parentStudentMapping!=null){            
              for(int i=0;i<userProfile.parentStudentMapping.length;i++){
                  String l_studentID=userProfile.parentStudentMapping[i].getStudentID();
                  String l_instituteID=userProfile.parentStudentMapping[i].getInstituteID();
                  String[] l_parentPKey={l_userID,l_studentID,l_instituteID};//Shouldn't not include version number in primary key
                      dbts.deleteRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_PARENT_STUDENT_ROLEMAPPING", l_parentPKey, session); 
              }          
            }  
              
            if(userProfile.classEntityRoleMapping!=null){  
               for(int i=0;i<userProfile.classEntityRoleMapping.length;i++){
                   String l_roleID=userProfile.classEntityRoleMapping[i].getRoleID();
                   String l_instituteID=userProfile.classEntityRoleMapping[i].getInstituteID();
                   String l_standard=userProfile.classEntityRoleMapping[i].getStandard();
                   String l_section=userProfile.classEntityRoleMapping[i].getSection();
                   String[] l_studentClassPKey={l_userID,l_roleID,l_instituteID,l_standard,l_section};//Shouldn't not include version number in primary key
             
                     dbts.deleteRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_CLASS_ENTITY_ROLEMAPPING", l_studentClassPKey, session); 
             }     
            }
            
           if(userProfile.teacherEntityMapping!=null){   
              for(int i=0;i<userProfile.teacherEntityMapping.length;i++){
                  String l_roleID=userProfile.teacherEntityMapping[i].getRoleID();
                  String l_instituteID=userProfile.teacherEntityMapping[i].getInstituteID();
                  String l_teacherID=userProfile.teacherEntityMapping[i].getTeacherID();
                  String[] l_teacherPKey={l_userID,l_roleID,l_instituteID,l_teacherID};//Shouldn't not include version number in primary key
             
                    dbts.deleteRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_TEACHER_ENTITY_ROLEMAPPING", l_teacherPKey, session); 
             } 
           }
           
           if(userProfile.instituteEntityMapping!=null){
              for(int i=0;i<userProfile.instituteEntityMapping.length;i++){
                  String l_roleID=userProfile.instituteEntityMapping[i].getRoleID();
                  String l_instituteID=userProfile.instituteEntityMapping[i].getInstituteID();
                  String[] l_institutePKey={l_userID,l_roleID,l_instituteID};//Shouldn't not include version number in primary key
             
                    dbts.deleteRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_INSTITUTE_ENTITY_ROLEMAPPING", l_institutePKey, session); 
                }
            }
        dbg("End of UserProfileService--->delete");  
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
            dbg("Inside StudentMasterService--->view");
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IPDataService pds=inject.getPdataservice();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<UserProfile> reqBody = request.getReqBody();
            UserProfile userProfile =reqBody.get();
            String userID=userProfile.getUserID();
            
            String[] l_pkey={userID};
            ArrayList<String> l_profileRecord=null;
            ArrayList<String> l_credentialRecord=null;
            ArrayList<String>l_teacherIDMappingRecord=null;
            
              try{
            
                l_profileRecord=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_PROFILE", l_pkey);
                l_credentialRecord=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_CREDENTIALS", l_pkey );
        
                }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", userID);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
                }
                
                
                
                
                Map<String, ArrayList<String>>l_parentStudentRoleMap=new HashMap();
                Map<String, ArrayList<String>>l_classEntitytRoleMap=new HashMap();
                Map<String, ArrayList<String>>l_teacherEntityRoleMap=new HashMap();
                Map<String, ArrayList<String>>l_instituteEntityRoleMap=new HashMap();
                try{
                l_parentStudentRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_PARENT_STUDENT_ROLEMAPPING",session,dbSession);
                l_classEntitytRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_CLASS_ENTITY_ROLEMAPPING",session,dbSession);
                l_teacherEntityRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_TEACHER_ENTITY_ROLEMAPPING",session,dbSession);
                l_instituteEntityRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_INSTITUTE_ENTITY_ROLEMAPPING",session,dbSession);
             
                l_teacherIDMappingRecord=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_TEACHER_ID_MAPPING", l_pkey );
                
                }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(!(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000"))){
                        throw ex;
                    }else{
                        
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                    }
                }
                buildBOfromDB(l_profileRecord,l_credentialRecord,l_parentStudentRoleMap,l_classEntitytRoleMap,l_teacherEntityRoleMap,l_instituteEntityRoleMap,l_teacherIDMappingRecord);
            
            
          
         
          dbg("end of  StudentMasterService--->view");        
          
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
 
 public void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
         
         return;
         
     }
 
 //This method builds jsonObject from business object
 public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObjectBuilder body;
        try{
            dbg("Inside UserProfileService--->buildJsonfromBO");
            RequestBody<UserProfile> reqBody = request.getReqBody();
            UserProfile userProfile =reqBody.get();            
            JsonArrayBuilder l_parentRoleBuilder = Json.createArrayBuilder();
            JsonArrayBuilder l_classStudentRoleBuilder=Json.createArrayBuilder();
            JsonArrayBuilder l_teacherRoleBuilder=Json.createArrayBuilder();
            JsonArrayBuilder l_instituteRoleBuilder=Json.createArrayBuilder();

            
           if(userProfile.parentStudentMapping!=null){
             for(int i=0;i<userProfile.parentStudentMapping.length;i++){
                
                  l_parentRoleBuilder.add(Json.createObjectBuilder().add("roleID", userProfile.parentStudentMapping[i].getRoleID())
                                                                    .add("instituteID", userProfile.parentStudentMapping[i].getInstituteID())
                                                                    .add("instituteName", userProfile.parentStudentMapping[i].getInstituteName())
                                                                    .add("studentID", userProfile.parentStudentMapping[i].getStudentID()));
                
              }
            }
            
           if(userProfile.classEntityRoleMapping!=null){
             for(int i=0;i<userProfile.classEntityRoleMapping.length;i++){
                
                l_classStudentRoleBuilder.add(Json.createObjectBuilder().add("roleID", userProfile.classEntityRoleMapping[i].getRoleID())
                                                                        .add("instituteID", userProfile.classEntityRoleMapping[i].getInstituteID())
                                                                        .add("instituteName", userProfile.classEntityRoleMapping[i].getInstituteName())
                                                                        .add("class", userProfile.classEntityRoleMapping[i].getStandard()+"/"+userProfile.classEntityRoleMapping[i].getSection())
                                                                        );
                
            }
           }
           
           if(userProfile.teacherEntityMapping!=null){
            for(int i=0;i<userProfile.teacherEntityMapping.length;i++){
                
                l_teacherRoleBuilder.add(Json.createObjectBuilder().add("roleID", userProfile.teacherEntityMapping[i].getRoleID())
                                                                   .add("instituteID", userProfile.teacherEntityMapping[i].getInstituteID())
                                                                   .add("instituteName", userProfile.teacherEntityMapping[i].getInstituteName())
//                                                                   .add("teacherName", userProfile.teacherEntityMapping[i].getTeacherName())
                                                                   .add("teacherID", userProfile.teacherEntityMapping[i].getTeacherID()));
                
            }
           }
           
           if(userProfile.instituteEntityMapping!=null){
             for(int i=0;i<userProfile.instituteEntityMapping.length;i++){
                
                l_instituteRoleBuilder.add(Json.createObjectBuilder().add("roleID",userProfile.instituteEntityMapping[i].getRoleID())
                                                                     .add("instituteName", userProfile.instituteEntityMapping[i].getInstituteName())
                                                                     .add("instituteID", userProfile.instituteEntityMapping[i].getInstituteID()));
                
            }
           }

    
             body=Json.createObjectBuilder().add("userID", userProfile.getUserID())
                                            .add("userName", userProfile.getUserName())
                                            .add("userType", userProfile.getUserType())
                                            .add("status", userProfile.getStatus())
                                            .add("teacherID", userProfile.getTeacherID())
                                            .add("teacherName", userProfile.getTeacherName())
                                            .add("instituteID", userProfile.getHomeInstitue())
                                            .add("instituteName", userProfile.getHomeInstituteName())
                                            .add("emailID", userProfile.getEmailID())
                                            .add("mobileNo", userProfile.getMobileNo())
                                            .add("password", userProfile.getPassword())
                                            .add("expiryDate", userProfile.getExpiryDate())
                                            .add("parentRoleMapping",l_parentRoleBuilder)
                                            .add("studentClassRoleMapping", l_classStudentRoleBuilder)
                                            .add("teacherRoleMapping", l_teacherRoleBuilder)
                                            .add("instituteRoleMapping", l_instituteRoleBuilder);
            
             
       dbg("End of UserProfileService--->buildJsonfromBO");               
      }catch (Exception ex) {
         dbg(ex);
         throw new BSProcessingException("Exception" + ex.toString());   
      }
      return body.build();
    }
 
 
     //This method validates the data in the request
     private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside UserProfileService--->businessValidation");  
       String l_operation=request.getReqHeader().getOperation();
       String l_instituteID=request.getReqHeader().getInstituteID();
       
                     
       if(!masterMandatoryValidation(errhandler)){
           status=false;
       }
       
       //master data validation is not written in this service. This the first time user id is crated so we can't able to validate the userID
       
       
       if(!(l_operation.equals("View"))&&!l_operation.equals("Create-Default")&&!l_operation.equals("Delete")){
           
           if(!detailMandatoryValidation(errhandler)) {
                status=false;
           }else{
              if(!detailDataValidation(errhandler,l_instituteID)){
               status=false;
              }
           
         }
       
       }

       dbg("UserProfileService--->businessValidation--->O/P--->status"+status);
       dbg("End of UserProfileService--->businessValidation");
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
        dbg("inside UserProfileService--->masterMandatoryValidation");    
        RequestBody<UserProfile> reqBody = request.getReqBody();
        UserProfile userProfile =reqBody.get();
        if(userProfile.getUserID()==null||userProfile.getUserID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","userID");  
        }
        
        dbg("end of UserProfileService--->masterMandatoryValidation"); 
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
         //This method performs mandatory validation in detail data
     private boolean detailMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
        boolean status=true;
         try{
            dbg("inside UserProfileService--->detailMandatoryValidation");    
            RequestBody<UserProfile> reqBody = request.getReqBody();
            UserProfile userProfile =reqBody.get(); 
            String userType=userProfile.getUserType();
            if(userProfile.getUserName()==null||userProfile.getUserName().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","userName");  
            }
            
            if(userProfile.getUserType()==null||userProfile.getUserType().isEmpty()){
              status=false;  
              errhandler.log_app_error("BS_VAL_002","userType");  
            }
            
           
//            if(userProfile.getEmailID()==null||userProfile.getEmailID().isEmpty()){
//               status=false;  
//               errhandler.log_app_error("BS_VAL_002","emailID");  
//            }
//            
//            if(userProfile.getMobileNo()==null||userProfile.getMobileNo().isEmpty()){
//               status=false;  
//               errhandler.log_app_error("BS_VAL_002","mobileNo");  
//            }
            
            if(userProfile.getPassword()==null||userProfile.getPassword().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","password");  
            }
            
//            if(userProfile.getExpiryDate()==null){
//               status=false;  
//               errhandler.log_app_error("BS_VAL_002","expiryDate");  
//            }
            
            if(userProfile.getStatus()==null||userProfile.getStatus().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","status");  
            }
            
            if(userProfile.getHomeInstitue()==null||userProfile.getHomeInstitue().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","home institute");  
            }
            
            if(userProfile.getUserType().equals("T")){
            
            if(userProfile.getTeacherID()==null||userProfile.getTeacherID().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","Teacher ID For The User");  
            }
            
            
            }
            
            if(userType.equals("P")){
                    
                    if(userProfile.classEntityRoleMapping!=null&&userProfile.classEntityRoleMapping.length!=0){
                        
                        status=false;
                        errhandler.log_app_error("BS_VAL_032","Class entity role mapping");
                    }
                    
                    if(userProfile.instituteEntityMapping!=null&&userProfile.instituteEntityMapping.length!=0){
                        
                        status=false;
                        errhandler.log_app_error("BS_VAL_032","Institute entity role mapping");
                    }
                    
                    if(userProfile.teacherEntityMapping!=null&&userProfile.teacherEntityMapping.length!=0){
                        
                        status=false;
                        errhandler.log_app_error("BS_VAL_032","Teacher entity role mapping");
                    }
                    
                    if(userProfile.parentStudentMapping==null||userProfile.parentStudentMapping.length==0){
                        
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","Parent role mapping");
                    }
                }else if(userType.equals("T")){
                    
                    
                    if(userProfile.parentStudentMapping!=null&&userProfile.parentStudentMapping.length!=0){
                        
                        status=false;
                        errhandler.log_app_error("BS_VAL_032","Parent role mapping");
                    }
                    
//                    if(userProfile.instituteEntityMapping!=null&&userProfile.instituteEntityMapping.length!=0){
//                        
//                        status=false;
//                        errhandler.log_app_error("BS_VAL_032","Institute role mapping");
//                    }
                    
                    if(userProfile.classEntityRoleMapping==null||userProfile.classEntityRoleMapping.length==0){
                        
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","Class role mapping");
                    }
                    
                    if(userProfile.teacherEntityMapping==null||userProfile.classEntityRoleMapping.length==0){
                        
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","Teacher role mapping");
                    }
                    
                }else if(userType.equals("A")){
                    
                    if(userProfile.parentStudentMapping!=null&&userProfile.parentStudentMapping.length!=0){
                        
                        status=false;
                        errhandler.log_app_error("BS_VAL_032","Parent role mapping");
                    }
                    
                    if(userProfile.instituteEntityMapping==null||userProfile.instituteEntityMapping.length==0){
                        
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","Institute role mapping");
                    }
                    
                    if(userProfile.teacherEntityMapping==null||userProfile.teacherEntityMapping.length==0){
                        
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","Teacher role mapping");
                    }
                    
                    if(userProfile.classEntityRoleMapping==null||userProfile.classEntityRoleMapping.length==0){
                        
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","Class role mapping");
                    }
                }
            
            
            
            
            
        
            if(userProfile.parentStudentMapping!=null){
                for(int i=0;i<userProfile.parentStudentMapping.length;i++){
                    
                    if(userProfile.parentStudentMapping[i].getRoleID()==null||userProfile.parentStudentMapping[i].getRoleID().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","roleID");  
                    }
                    
                    if(userProfile.parentStudentMapping[i].getStudentID()==null||userProfile.parentStudentMapping[i].getStudentID().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","studentID");  
                    }
                    if(userProfile.parentStudentMapping[i].getInstituteID()==null||userProfile.parentStudentMapping[i].getInstituteID().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","instituteID");  
                    }
                }
                
            }
            if(userProfile.classEntityRoleMapping!=null){
                for(int i=0;i<userProfile.classEntityRoleMapping.length;i++){
                    
                    if(userProfile.classEntityRoleMapping[i].getRoleID()==null||userProfile.classEntityRoleMapping[i].getRoleID().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","roleID");  
                    }
                    
                    if(userProfile.classEntityRoleMapping[i].getStandard()==null||userProfile.classEntityRoleMapping[i].getStandard().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","standard");  
                    }
                    
                    if(userProfile.classEntityRoleMapping[i].getSection()==null||userProfile.classEntityRoleMapping[i].getSection().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","section");  
                    }
                    
                    if(userProfile.classEntityRoleMapping[i].getInstituteID()==null||userProfile.classEntityRoleMapping[i].getInstituteID().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","instituteID");  
                    }
                }
                
            }
            if(userProfile.teacherEntityMapping!=null){
                for(int i=0;i<userProfile.teacherEntityMapping.length;i++){
                    
                    if(userProfile.teacherEntityMapping[i].getRoleID()==null||userProfile.teacherEntityMapping[i].getRoleID().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","roleID");  
                    }
                    
                    if(userProfile.teacherEntityMapping[i].getInstituteID()==null||userProfile.teacherEntityMapping[i].getInstituteID().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","instituteID");  
                    }
                    
                    if(userProfile.teacherEntityMapping[i].getTeacherID()==null||userProfile.teacherEntityMapping[i].getTeacherID().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","teacherID");  
                    }
                }
                
            }
            if(userProfile.instituteEntityMapping!=null){
                for(int i=0;i<userProfile.instituteEntityMapping.length;i++){
                    
                    if(userProfile.instituteEntityMapping[i].getRoleID()==null||userProfile.instituteEntityMapping[i].getRoleID().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","roleID");  
                    }
                    
                    if(userProfile.instituteEntityMapping[i].getInstituteID()==null||userProfile.instituteEntityMapping[i].getInstituteID().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","instituteID");  
                    }
                  
                }
                
            }
             dbg("UserProfile--->detailMandatoryValidation--->O/P--->status"+status);
             dbg("End of UserProfile--->detailMandatoryValidation");   
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }    
     
     
     private boolean detailDataValidation(ErrorHandler errhandler,String p_instituteID)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         try{
            dbg("inside UserProfileService--->detailDataValidation");  
            BSValidation bsv=inject.getBsv(session);
            RequestBody<UserProfile> reqBody = request.getReqBody();
            String l_operation=request.getReqHeader().getOperation();
            String l_headerInstituteID=request.getReqHeader().getInstituteID();
            IPDataService pds=inject.getPdataservice();
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            UserProfile userProfile =reqBody.get();
            String l_userID=userProfile.getUserID();
            String l_userType=userProfile.getUserType();
            String l_emailID=userProfile.getEmailID();
            String l_mobileNo=userProfile.getMobileNo();
            String l_status=userProfile.getStatus();
            String l_homeInstitute=userProfile.getHomeInstitue();
          
            if(!(bsv.instituteIDValidation(l_homeInstitute, errhandler, inject, session, dbSession))){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","home Institute");
            }
            
            if(l_operation.equals("Modify")||l_operation.equals("Delete")){
                
                String[] pkey={l_userID};
                ArrayList<String> l_profileRecord=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_PROFILE", pkey);
                
                String previousUserType=l_profileRecord.get(13).trim();
                String previousStatus=l_profileRecord.get(14).trim();
                
                boolean generalChanged=false;
                
                if(!previousUserType.equals(l_userType)){
                    
                    generalChanged=true;
                }
                
                if(!l_status.equals(previousStatus)){
                    
                    generalChanged=true;
                }
                
                if(generalChanged){
                    
                    if(!l_homeInstitute.equals(l_headerInstituteID)){

                        status=false;
                        errhandler.log_app_error("BS_VAL_014","General");
                        throw new BSValidationException("BSValidationException");
                    }
                
                }
                
                
            }
            
            
            if(!(l_userType.equals("P")||l_userType.equals("T")||l_userType.equals("A"))){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","userType");
                }
                
                if(!(l_status.equals("D")||l_status.equals("E"))){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","status");
                }
                
                if(userProfile.getUserType().equals("T")){
                    
                    if(!bsv.teacherIDValidation(userProfile.getTeacherID(),p_instituteID, session, dbSession, inject)){

                                   status=false;
                                   errhandler.log_app_error("BS_VAL_003","Teacher ID For The User");
                     }
                }

                
              if(!l_emailID.isEmpty()){  
                
                if(!bsv.emailValidation(l_emailID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","emailID");
                }
                
              }  
              
              if(!l_mobileNo.isEmpty()){  
              
                if(!bsv.contactNoValidation(l_mobileNo, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","mobileNo If there is no country code please enter country code before mobile no and + prefixed");
                }
               
              }
                
                
             
                if(userProfile.parentStudentMapping!=null){
                    
                    ArrayList<String>parentMappingList=new ArrayList();
                    
                   for(int i=0;i<userProfile.parentStudentMapping.length;i++){ 
                       String l_roleID=userProfile.parentStudentMapping[i].getRoleID();
                       String l_studentID=userProfile.parentStudentMapping[i].getStudentID();
                       String l_instituteID=userProfile.parentStudentMapping[i].getInstituteID();
                       
                       if(!parentMappingList.contains(l_studentID+l_instituteID)){
                       
                         parentMappingList.add(l_studentID+l_instituteID);
                       }else{
                           status=false;
                           errhandler.log_app_error("BS_VAL_031","Parent role mapping");
                           throw new BSValidationException("BSValidationException");
                           
                       }
                     
                       if(!bsv.roleIDValidation(l_roleID, session, dbSession, inject)){
                           
                           status=false;
                           errhandler.log_app_error("BS_VAL_003","RoleID");
                       }
                  
                       if(!bsv.studentIDValidation(l_studentID,p_instituteID, session, dbSession, inject)){
                           
                           status=false;
                           errhandler.log_app_error("BS_VAL_003","studentID");
                       }
                       if(!bsv.instituteIDValidation(l_instituteID,errhandler, inject,session, dbSession )){
                           
                           status=false;
                           errhandler.log_app_error("BS_VAL_003","instituteID");
                       }
//                    
                }   
                }
                
                
                if(userProfile.classEntityRoleMapping!=null){
                    ArrayList<String>classRoleList=new ArrayList();
                   for(int i=0;i<userProfile.classEntityRoleMapping.length;i++){ 
                       String l_roleID=userProfile.classEntityRoleMapping[i].getRoleID();
                       String l_standard=userProfile.classEntityRoleMapping[i].getStandard();
                       String l_section=userProfile.classEntityRoleMapping[i].getSection();
                       String l_instituteID=userProfile.classEntityRoleMapping[i].getInstituteID();
                     
                       
                       if(!classRoleList.contains(l_roleID+l_standard+l_section+l_instituteID)){
                       
                         classRoleList.add(l_roleID+l_standard+l_section+l_instituteID);
                       }else{
                           status=false;
                           errhandler.log_app_error("BS_VAL_031","Class role mapping");
                           throw new BSValidationException("BSValidationException");
                           
                       }
                       
                       if(!bsv.roleIDValidation(l_roleID, session, dbSession, inject)){
                           status=false;
                           errhandler.log_app_error("BS_VAL_003","RoleID");
                       }
                    
                       if(!(l_standard.equals("ALL")||l_section.equals("ALL"))){
                       
                           if(!bsv.standardSectionValidation(l_standard,l_section,p_instituteID, session, dbSession, inject)){
                               status=false;
                               errhandler.log_app_error("BS_VAL_003","Class");
                           }
                    
                       }
                       
                       
                        if(!(bsv.instituteIDValidation(l_instituteID, errhandler, inject, session, dbSession))){
                             status=false;
                             errhandler.log_app_error("BS_VAL_003","institute id");
                        }
                    
                    }   
                } 
                if(userProfile.teacherEntityMapping!=null){
                    ArrayList<String>teacherRoleList=new ArrayList();
                   for(int i=0;i<userProfile.teacherEntityMapping.length;i++){ 
                       String l_roleID=userProfile.teacherEntityMapping[i].getRoleID();
                       String l_instituteID=userProfile.teacherEntityMapping[i].getInstituteID();
                       String l_teacherID=userProfile.teacherEntityMapping[i].getTeacherID();
                       
                       if(!teacherRoleList.contains(l_roleID+l_teacherID+l_instituteID)){
                       
                         teacherRoleList.add(l_roleID+l_teacherID+l_instituteID);
                       }else{
                           status=false;
                           errhandler.log_app_error("BS_VAL_031","Teacher role mapping");
                           throw new BSValidationException("BSValidationException");
                           
                       }
                       
                   
                       if(!bsv.roleIDValidation(l_roleID, session, dbSession, inject)){
                           status=false;
                           errhandler.log_app_error("BS_VAL_003","RoleID");
                       }
                       
                       
//                       if(l_userType.equals("T")){
//                           
//                           if(!l_teacherID.equals(l_userID)){
//                               
//                               status=false;
//                               errhandler.log_app_error("BS_VAL_014",l_teacherID);
//                           }
//                           
//                           
//                       }
                       
                       
                       if(!l_teacherID.equals("ALL")){
                       
                               if(!bsv.teacherIDValidation(l_teacherID,p_instituteID, session, dbSession, inject)){

                                   status=false;
                                   errhandler.log_app_error("BS_VAL_003","teacherID");
                               }
                           
                       }
                       
                       
                       
                        if(!(bsv.instituteIDValidation(l_instituteID, errhandler, inject, session, dbSession))){
                             status=false;
                             errhandler.log_app_error("BS_VAL_003","instituteID");
                        }

                 } 
                }
                
                if(userProfile.instituteEntityMapping!=null){
                    ArrayList<String>instituteRoleList=new ArrayList();
                   for(int i=0;i<userProfile.instituteEntityMapping.length;i++){ 
                       String l_roleID=userProfile.instituteEntityMapping[i].getRoleID();
                       String l_instituteID=userProfile.instituteEntityMapping[i].getInstituteID();
                       
                       if(!instituteRoleList.contains(l_roleID+l_instituteID)){
                       
                         instituteRoleList.add(l_roleID+l_instituteID);
                       }else{
                           status=false;
                           errhandler.log_app_error("BS_VAL_031","Institute role mapping");
                           throw new BSValidationException("BSValidationException");
                           
                       }
                       
                       if(!bsv.roleIDValidation(l_roleID, session, dbSession, inject)){
                           status=false;
                           errhandler.log_app_error("BS_VAL_003","RoleID");
                       }
                       
                       if(!(bsv.instituteIDValidation(l_instituteID, errhandler, inject, session, dbSession))){
                             status=false;
                             errhandler.log_app_error("BS_VAL_003","instituteID");
                        }
                    }   
                }
                
                String l_huserID=request.getReqHeader().getUserID();
                if(!(l_huserID.equals("System")||l_huserID.equals("Admin")||l_huserID.equals("Teacher")||l_huserID.equals("Parent"))){
                
                   instituteContractValidation();
                }
                
                
        dbg("UserProfileService--->detailDataValidation--->O/P--->status"+status);
        dbg("End of UserProfileService--->detailDataValidation");        
      }catch(DBValidationException ex){
        throw ex;
      }catch(DBProcessingException ex){
        dbg(ex);  
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
        dbg("inside UserProfile--->getExistingAudit") ;
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IPDataService pds=inject.getPdataservice();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_operation=request.getReqHeader().getOperation();
        String l_service=request.getReqHeader().getService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
        if(!(l_operation.equals("Create")||l_operation.equals("View"))){
             
               dbg("inside UserProfile--->getExistingAudit--->Service--->UserProfile");  
               RequestBody<UserProfile> userProfileBody = request.getReqBody();
               UserProfile userProfile =userProfileBody.get();
               String l_userID=userProfile.getUserID();
               dbg("l_userID"+l_userID);
               String[] l_userProfilepkey={l_userID};
               ArrayList<String> l_profileRecord=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_PROFILE", l_userProfilepkey);
               exAudit.setAuthStatus(l_profileRecord.get(9).trim());
               exAudit.setMakerID(l_profileRecord.get(4).trim());
               exAudit.setRecordStatus(l_profileRecord.get(8).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_profileRecord.get(10).trim()));


 
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
     
     
     private boolean instituteContractValidation()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
         boolean status=false;
         try{
             
             String operation=request.getReqHeader().getOperation();
             IPDataService pds=inject.getPdataservice();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             RequestBody<UserProfile> userProfileBody = request.getReqBody();
             UserProfile userProfile =userProfileBody.get();
             String userID=userProfile.getUserID();
             String headerInstituteID=request.getReqHeader().getInstituteID();
//             IDBReadBufferService readBuffer=inject.getDBReadBufferService();
             
             
             if(operation.equals("Create")||operation.equals("Delete")){
                 
                 String homeInstitute=userProfile.getHomeInstitue();
                 
                 if(!instituteExistence(homeInstitute)){
                     
                     session.getErrorhandler().log_app_error("BS_VAL_028", null);
                     throw new BSValidationException("BSValidationException");
                 }
                 
                 if(userProfile.parentStudentMapping!=null){
                 
                     for(int i=0;i<userProfile.parentStudentMapping.length;i++){ 

                         String instituteID=userProfile.parentStudentMapping[i].getInstituteID();

                         if(!instituteExistence(instituteID)){

                             session.getErrorhandler().log_app_error("BS_VAL_028", null);
                             throw new BSValidationException("BSValidationException");
                         }

                     }
                 }
                 
                 if(userProfile.classEntityRoleMapping!=null){
                 
                     for(int i=0;i<userProfile.classEntityRoleMapping.length;i++){ 

                         String instituteID=userProfile.classEntityRoleMapping[i].getInstituteID();

                         if(!instituteExistence(instituteID)){

                             session.getErrorhandler().log_app_error("BS_VAL_028", null);
                             throw new BSValidationException("BSValidationException");
                         }

                     }
                 
                 }
                 
                 
                 if(userProfile.teacherEntityMapping!=null){
                 
                     for(int i=0;i<userProfile.teacherEntityMapping.length;i++){ 

                         String instituteID=userProfile.teacherEntityMapping[i].getInstituteID();

                         if(!instituteExistence(instituteID)){

                             session.getErrorhandler().log_app_error("BS_VAL_028", null);
                             throw new BSValidationException("BSValidationException");
                         }

                     }
                 
                 }
                 
                 if(userProfile.instituteEntityMapping!=null){
                 
                     for(int i=0;i<userProfile.instituteEntityMapping.length;i++){ 

                         String instituteID=userProfile.instituteEntityMapping[i].getInstituteID();

                         if(!instituteExistence(instituteID)){

                             session.getErrorhandler().log_app_error("BS_VAL_028", null);
                             throw new BSValidationException("BSValidationException");
                         }

                     }
                     
                 }
                 
             }else if(operation.equals("Modify")){
                 
                 
                     String[] l_pkey={userID};
                     ArrayList<String> l_profileRecord=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_PROFILE", l_pkey);

                     String dbUserType=l_profileRecord.get(13).trim();
                     String dbUserStatus=l_profileRecord.get(14).trim();
                     String dbHomeInstitute=l_profileRecord.get(15).trim();

                     String userType=userProfile.getUserType();
                     String userStatus=userProfile.getStatus();
                     String homeInstitute=userProfile.getHomeInstitue();
                     boolean modified=false;

                     if(!dbUserType.equals(userType)){

                         modified=true;
                     }

                     if(!dbUserStatus.equals(userStatus)){

                         modified=true;
                     }

                     if(!homeInstitute.equals(dbHomeInstitute)){

                         modified=true;
                     }


                     if(modified){

                         if(!dbHomeInstitute.equals(headerInstituteID)){

                             session.getErrorhandler().log_app_error("BS_VAL_028", null);
                             throw new BSValidationException("BSValidationException");
                         }


                     }
                 Map<String,ArrayList<String>>l_parentStudentRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_PARENT_STUDENT_ROLEMAPPING",session,dbSession);
                 Map<String,ArrayList<String>>l_classEntitytRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_CLASS_ENTITY_ROLEMAPPING",session,dbSession);
                 Map<String,ArrayList<String>>l_teacherEntityRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_TEACHER_ENTITY_ROLEMAPPING",session,dbSession);
                 Map<String,ArrayList<String>>l_instituteEntityRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_INSTITUTE_ENTITY_ROLEMAPPING",session,dbSession);
                 
                 
                 //parent rolemapping validation starts
                 
                 if(userProfile.parentStudentMapping!=null){
                 
                      for(int i=0;i<userProfile.parentStudentMapping.length;i++){
                                String l_roleID=userProfile.parentStudentMapping[i].getRoleID();
                                String l_instituteID=userProfile.parentStudentMapping[i].getInstituteID();
                                String l_primarykey=userID+"~"+l_roleID+"~"+l_instituteID;
                               if(l_parentStudentRoleMap.containsKey(l_primarykey)){

                                   String previousInstitute=l_parentStudentRoleMap.get(l_primarykey).get(4).trim();
                                       
                                        if(!instituteExistence(previousInstitute)){
                                          
                                           session.getErrorhandler().log_app_error("BS_VAL_028", null);
                                           throw new BSValidationException("BSValidationException");
                                       }
                                    
                                }else{
                                        if(!instituteExistence(l_instituteID)){
                                          
                                           session.getErrorhandler().log_app_error("BS_VAL_028", null);
                                           throw new BSValidationException("BSValidationException");
                                        }
                                   
                                }
                         } 
                 
                       ArrayList<String>parentRecToDelete=getRecordsToDelete("UVW_PARENT_STUDENT_ROLEMAPPING",l_parentStudentRoleMap);
                       
                       for(int i=0;i<parentRecToDelete.size();i++){
                           
                           String parentPkey=parentRecToDelete.get(i);
                           String instituteID=parentPkey.split("~")[2];
                           
                            if(!instituteExistence(instituteID)){
                                          
                               session.getErrorhandler().log_app_error("BS_VAL_028", null);
                               throw new BSValidationException("BSValidationException");
                            }
                           
                       }
                       
                 }
                  //parent rolemapping validation ends
                  
                  
                  
                  
                  //class rolemapping starts
                  
                  if(userProfile.classEntityRoleMapping!=null){
                         for(int i=0;i<userProfile.classEntityRoleMapping.length;i++){
                                String l_roleID=userProfile.classEntityRoleMapping[i].getRoleID();
                                String l_instituteID=userProfile.classEntityRoleMapping[i].getInstituteID();
                                String l_standard=userProfile.classEntityRoleMapping[i].getStandard();
                                String l_section=userProfile.classEntityRoleMapping[i].getSection();
                                String l_primarykey=userID+"~"+l_roleID+"~"+l_instituteID+"~"+l_standard+"~"+l_section;
                               if(l_classEntitytRoleMap.containsKey(l_primarykey)){

                                   String previousInstitute=l_classEntitytRoleMap.get(l_primarykey).get(5).trim();
                                       
                                        if(!instituteExistence(previousInstitute)){
                                          
                                           session.getErrorhandler().log_app_error("BS_VAL_028", null);
                                           throw new BSValidationException("BSValidationException");
                                       }
                                    
                                }else{
                                        if(!instituteExistence(l_instituteID)){
                                          
                                           session.getErrorhandler().log_app_error("BS_VAL_028", null);
                                           throw new BSValidationException("BSValidationException");
                                        }
                                   
                                }
                         } 
                 
                       ArrayList<String>classRecToDelete=getRecordsToDelete("UVW_CLASS_ENTITY_ROLEMAPPING",l_classEntitytRoleMap);
                       
                       for(int i=0;i<classRecToDelete.size();i++){
                           
                           String classPkey=classRecToDelete.get(i);
                           String instituteID=classPkey.split("~")[2];
                           
                            if(!instituteExistence(instituteID)){
                                          
                               session.getErrorhandler().log_app_error("BS_VAL_028", null);
                               throw new BSValidationException("BSValidationException");
                            }
                           
                       }
                  }
                  //class rolemapping ends
                  
                  
                  //teacher rolemapping starts
                  
                  if(userProfile.teacherEntityMapping!=null){
                         for(int i=0;i<userProfile.teacherEntityMapping.length;i++){
                                String l_roleID=userProfile.teacherEntityMapping[i].getRoleID();
                                String l_instituteID=userProfile.teacherEntityMapping[i].getInstituteID();
                                String l_teacherID=userProfile.teacherEntityMapping[i].getTeacherID();
                                String l_primarykey=userID+"~"+l_roleID+"~"+l_instituteID+"~"+l_teacherID;
                               if(l_teacherEntityRoleMap.containsKey(l_primarykey)){

                                   String previousInstitute=l_teacherEntityRoleMap.get(l_primarykey).get(2).trim();
                                       
                                        if(!instituteExistence(previousInstitute)){
                                          
                                           session.getErrorhandler().log_app_error("BS_VAL_028", null);
                                           throw new BSValidationException("BSValidationException");
                                       }
                                    
                                }else{
                                        if(!instituteExistence(l_instituteID)){
                                          
                                           session.getErrorhandler().log_app_error("BS_VAL_028", null);
                                           throw new BSValidationException("BSValidationException");
                                        }
                                   
                                }
                         } 
                 
                       ArrayList<String>teacherRecToDelete=getRecordsToDelete("UVW_TEACHER_ENTITY_ROLEMAPPING",l_teacherEntityRoleMap);
                       
                       for(int i=0;i<teacherRecToDelete.size();i++){
                           
                           String teacherPkey=teacherRecToDelete.get(i);
                           String instituteID=teacherPkey.split("~")[2];
                           
                            if(!instituteExistence(instituteID)){
                                          
                               session.getErrorhandler().log_app_error("BS_VAL_028", null);
                               throw new BSValidationException("BSValidationException");
                            }
                           
                       }
                  
                  }
                  
                  
                  //teacher rolemapping ends
                  
                  
               if(userProfile.instituteEntityMapping!=null){
                  
                  for(int i=0;i<userProfile.instituteEntityMapping.length;i++){
                                String l_roleID=userProfile.instituteEntityMapping[i].getRoleID();
                                String l_instituteID=userProfile.instituteEntityMapping[i].getInstituteID();
                                String l_primarykey=userID+"~"+l_roleID+"~"+l_instituteID;
                               if(l_instituteEntityRoleMap.containsKey(l_primarykey)){

                                   String previousInstitute=l_instituteEntityRoleMap.get(l_primarykey).get(2).trim();
                                       
                                        if(!instituteExistence(previousInstitute)){
                                          
                                           session.getErrorhandler().log_app_error("BS_VAL_028", null);
                                           throw new BSValidationException("BSValidationException");
                                       }
                                    
                                }else{
                                        if(!instituteExistence(l_instituteID)){
                                          
                                           session.getErrorhandler().log_app_error("BS_VAL_028", null);
                                           throw new BSValidationException("BSValidationException");
                                        }
                                   
                                }
                         } 
                 
                       ArrayList<String>instituteRecToDelete=getRecordsToDelete("UVW_INSTITUTE_ENTITY_ROLEMAPPING",l_instituteEntityRoleMap);
                       
                       for(int i=0;i<instituteRecToDelete.size();i++){
                           
                           String institutePkey=instituteRecToDelete.get(i);
                           String instituteID=institutePkey.split("~")[2];
                           
                            if(!instituteExistence(instituteID)){
                                          
                               session.getErrorhandler().log_app_error("BS_VAL_028", null);
                               throw new BSValidationException("BSValidationException");
                            }
                           
                       }
                  }
                       
             }
             
             
             status=true;
             return status;
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
     
     
     
     private boolean instituteExistence(String institueID) throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
         try{
             
             String userID=request.getReqHeader().getUserID();
             IPDataService pds=inject.getPdataservice();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             boolean instituteExistence=false;
             dbg("inside instituteExistence");
//             String[] l_pkey={userID};
//             ArrayList<String>userContractList;
             
//             try{
//                 
//              userContractList= pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User", "USER", "UVW_USER_CONTRACT_MASTER",l_pkey);
//
//             }catch(DBValidationException ex){
//                 
//                 if(ex.toString().contains("DB_VAL_011")){
//                     
//                     session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
//                     status=false;
//                     return status;
//                 }else{
//                     
//                     throw ex;
//                 }
//                 
//             }
//             
//             String contractID=userContractList.get(1).trim();
//             
//             String[] l_contractPkey={contractID,institueID};
//             
//             try{
//                 
//                 pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",l_contractPkey);
//
//             }catch(DBValidationException ex){
//                 
//                 if(ex.toString().contains("DB_VAL_011")){
//                     
//                     session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
//                     status=false;
//                     return status;
//                 }else{
//                     
//                     throw ex;
//                 }
//                 
//             } 
              
             
                   Map<String,ArrayList<String>>userContactMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_CONTRACT_MASTER", session, dbSession);
                   dbg("userContactMap size"+userContactMap.size());
                   Map<String,List<ArrayList<String>>>contactGroup=   userContactMap.values().stream().filter(rec->rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec->rec.get(1).trim()));
                   dbg("contactGroup size"+contactGroup.size());
                   Iterator<String>contractIterator=contactGroup.keySet().iterator();

                   while(contractIterator.hasNext()){

                       String contractID=contractIterator.next();
                       dbg("contractID"+contractID);

                       Map<String,ArrayList<String>>instituteContactMap=pds.readTablePData("APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive","APP","CONTRACT_MASTER", session, dbSession);
                       dbg("instituteContactMap size"+instituteContactMap.size());
                       Map<String,List<ArrayList<String>>>instituteContactGroup=   instituteContactMap.values().stream().filter(rec->rec.get(1).trim().equals(contractID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
                       dbg("instituteContactGroup size"+instituteContactGroup.size());

                       if(instituteContactGroup.containsKey(institueID)){
                           dbg("returning true in instituteExistence");
                           return true;
                         
                       }
                       
                       
                   }



             dbg("end of instituteExistence  status--->"+instituteExistence);

             return instituteExistence;
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
     
     
    
     
     
 public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }   
}
