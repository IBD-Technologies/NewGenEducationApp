/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentprofile;

import com.ibd.businessViews.IStudentProfileService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.app.business.util.exception.ExceptionHandler;
import com.ibd.cohesive.app.business.util.message.request.Parsing;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
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
 * @author IBD Technologies
 */
//@Local(IStudentProfileService.class)
@Remote(IStudentProfileService.class)
@Stateless
public class StudentProfileService implements IStudentProfileService {

    
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentProfileService(){
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
       dbg("inside StudentProfileService--->processing");
       dbg("StudentProfileService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<StudentProfile> reqBody = request.getReqBody();
       StudentProfile studentProfile =reqBody.get();
       l_lockKey=studentProfile.getStudentID();
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<IStudentProfileService>studentProfileEJB=new BusinessEJB();
       studentProfileEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentProfileEJB);
       
       if(request.getReqHeader().getOperation().equals("View")){
           
         String studentID=  bs.studentValidation(studentProfile.getStudentID(), studentProfile.getStudentName(), request.getReqHeader().getInstituteID(), session, dbSession, inject);
         
          
         if(studentID==null||studentID.isEmpty()){
             
             errhandler.log_app_error("BS_VAL_002","Student ID or Name");  
             throw new BSValidationException("BSValidationException");
         }
         
         studentProfile.setStudentID(studentID);
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
      
       bs.businessServiceProcssing(request, exAudit, inject,studentProfileEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentProfileEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student profile");
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
      StudentProfile studentProfile=new StudentProfile();
      RequestBody<StudentProfile> reqBody = new RequestBody<StudentProfile>(); 
           
      try{
      dbg("inside student profile buildBOfromReq");    
//      IBDProperties i_db_properties=session.getCohesiveproperties();
//      String l_instituteID=request.getReqHeader().getInstituteID();
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
//      String l_studentID=l_body.getString("studentID");
      studentProfile.setStudentID(l_body.getString("studentID"));
      studentProfile.setStudentName(l_body.getString("studentName"));
      
      if(!l_operation.equals("View")){
//       studentProfile.setStudentName(l_body.getString("studentName"));   
       studentProfile.setNote(l_body.getString("note"));
       JsonObject l_general=l_body.getJsonObject("general");
       studentProfile.gen=new General();
       String l_profileImgPath=l_body.getString("profileImgPath");
//       String l_profileImgPath="INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"img"+i_db_properties.getProperty("FOLDER_DELIMITER")+"profileImg";
       studentProfile.setProfileImagePath(l_profileImgPath);
       String l_class=l_general.getString("class");
//       studentProfile.gen.classs=new Class();
       studentProfile.gen.setStandard(l_class.split("/")[0]);
       studentProfile.gen.setSection(l_class.split("/")[1]);
       studentProfile.gen.setDob(l_general.getString("dob"));
       studentProfile.gen.setBloodGroup(l_general.getString("bloodGroup"));
       studentProfile.gen.setGender(l_general.getString("gender"));
       
       studentProfile.gen.setNationalID(l_general.getString("nationalID"));
       JsonObject l_address=l_general.getJsonObject("address");
       studentProfile.gen.address=new Address();
       studentProfile.gen.address.setAddressLine1(l_address.getString("addressLine1"));
       studentProfile.gen.address.setAddressLine2(l_address.getString("addressLine2"));
       studentProfile.gen.address.setAddressLine3(l_address.getString("addressLine3"));
       studentProfile.gen.address.setAddressLine4(l_address.getString("addressLine4"));
       /*if (l_address.getString("addressLine5")!=null)
       studentProfile.gen.address.setAddressLine5(l_address.getString("addressLine5"));
       else*/
       studentProfile.gen.address.setAddressLine5(" ");
       JsonObject l_emergency=l_body.getJsonObject("emergency");
       studentProfile.emer=new Emergency();
        
       if(l_emergency.containsKey("existingMedicalDetails")){
          studentProfile.emer.setExistingMedicalDetails(l_emergency.getString("existingMedicalDetails"));
      }
//       JsonArray l_cpArray=l_emergency.getJsonArray("contactPerson");
//       studentProfile.emer.cp=new ContactPerson[l_cpArray.size()];
//       int j=0;
//       for(int i=0;i<l_cpArray.size();i++){
//           studentProfile.emer.cp[i]=new ContactPerson();
//           JsonObject l_cpObject=l_cpArray.getJsonObject(i);
//           
////           if(l_operation.equals("Create")){ 
//              //String l_cpID=CohesiveSession.dataToUUID().toString();
//              j=j+1;
//              studentProfile.emer.cp[i].setCp_ID(Integer.toString(j));
////           }else{
////               studentProfile.emer.cp[i].setCp_ID(l_cpObject.getString("cp_ID"));
////           }
//           
//           studentProfile.emer.cp[i].setCp_name(l_cpObject.getString("cp_Name"));
//           studentProfile.emer.cp[i].setCp_relationship(l_cpObject.getString("cp_relationship"));
//           studentProfile.emer.cp[i].setCp_occupation(l_cpObject.getString("cp_occupation"));
//           studentProfile.emer.cp[i].setCp_mailID(l_cpObject.getString("cp_emailID"));
//           studentProfile.emer.cp[i].setCp_contactNo(l_cpObject.getString("cp_contactNo"));
//           
////           String cpImg="INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"img"+i_db_properties.getProperty("FOLDER_DELIMITER")+"contactPersonImg";
//           String cpImg=l_cpObject.getString("cp_imgPath");
//           studentProfile.emer.cp[i].setCp_imgPath(cpImg);
//       }
       JsonArray l_familyArray=l_body.getJsonArray("family");
       studentProfile.fam=new Family[l_familyArray.size()];
       int j=0;
        for(int i=0;i<l_familyArray.size();i++){
           j=j+1;
            studentProfile.fam[i]=new Family();
            JsonObject l_familyObject=l_familyArray.getJsonObject(i);
//            if(l_operation.equals("Create")){ 
//               String l_memberID=CohesiveSession.dataToUUID().toString();
               studentProfile.fam[i].setMemberID(Integer.toString(j));
//            }else{
//               studentProfile.fam[i].setMemberID(l_familyObject.getString("memberID"));
//            }
            studentProfile.fam[i].setMemberName(l_familyObject.getString("memberName"));
            studentProfile.fam[i].setMemberRelationship(l_familyObject.getString("memberRelationship"));
            studentProfile.fam[i].setMemberOccupation(l_familyObject.getString("memberOccupation"));
            studentProfile.fam[i].setMemberEmailID(l_familyObject.getString("memberEmailID"));
            studentProfile.fam[i].setMemberContactNo(l_familyObject.getString("memberContactNo"));
            
            boolean notificationRequired=l_familyObject.getBoolean("notificationRequired");
            
            if(notificationRequired){
                
                studentProfile.fam[i].setNotificationRequired("Y");
                studentProfile.gen.setCommunicationLanguage(l_familyObject.getString("language"));
            }else{
                
                studentProfile.fam[i].setNotificationRequired("N");
            }
            
            
//            studentProfile.fam[i].setNotificationRequired(l_familyObject.getBoolean("notificationRequired"));
//            String memberImg="INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"img"+i_db_properties.getProperty("FOLDER_DELIMITER")+"familyMemberImg";
           String memberImg=l_familyObject.getString("memberImgPath");
           studentProfile.fam[i].setMemberImgPath(memberImg);
        }
        
              
      }
        reqBody.set(studentProfile);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
    try{ 
        dbg("inside stident profile create"); 
        RequestBody<StudentProfile> reqBody = request.getReqBody();
        StudentProfile studentProfile =reqBody.get();
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
        String l_studentID=studentProfile.getStudentID();
        String l_studentName=studentProfile.getStudentName();
        String l_standard=studentProfile.gen.getStandard();
        String l_section=studentProfile.gen.getSection();
        String l_dob=studentProfile.gen.getDob();
        String l_bloodGroup=studentProfile.gen.getBloodGroup();
        String l_communicationLang=studentProfile.gen.getCommunicationLanguage();
        String l_nationalID=studentProfile.gen.getNationalID();
        String l_gender=studentProfile.gen.getGender();
        String l_addressLine1=studentProfile.gen.address.getAddressLine1();
        String l_addressLine2=studentProfile.gen.address.getAddressLine2();
        String l_addressLine3=studentProfile.gen.address.getAddressLine3();
        String l_addressLine4=studentProfile.gen.address.getAddressLine4(); 
        String l_addressLine5=studentProfile.gen.address.getAddressLine5();
        String l_notes=studentProfile.getNote();
        String l_profileImgPath=studentProfile.getProfileImagePath();
        String l_existingMedicalDetail=studentProfile.emer.getExistingMedicalDetails();
                
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",1,l_studentID,l_studentName,l_standard,l_section,l_dob,l_bloodGroup,l_addressLine1,l_addressLine2,l_addressLine3,l_addressLine4,l_addressLine5,l_profileImgPath,l_notes,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks,l_existingMedicalDetail,l_communicationLang,l_nationalID,l_gender);

        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",36,l_studentID,l_studentName,l_standard,l_section,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
        
        
//        for(int j=0;j<studentProfile.emer.cp.length;j++){
//            String l_cpID=studentProfile.emer.cp[j].getCp_ID();
//            String l_cpName=studentProfile.emer.cp[j].getCp_name();
//            String l_cpRelationship=studentProfile.emer.cp[j].getCp_relationship();
//            String l_cpOccupation=studentProfile.emer.cp[j].getCp_occupation();
//            String l_cpEmailID=studentProfile.emer.cp[j].getCp_mailID();
//            String l_cpcontactNo= studentProfile.emer.cp[j].getCp_contactNo();
//            String l_cpImgPath= studentProfile.emer.cp[j].getCp_imgPath();
//             dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",38,l_studentID,l_cpID,l_cpName,l_cpRelationship,l_cpOccupation,l_cpEmailID,l_cpcontactNo,l_cpImgPath,l_versionNumber);
//
//        }
        
        for(int i=0;i<studentProfile.fam.length;i++){
            String l_memberID=studentProfile.fam[i].getMemberID();
            String l_memberName=studentProfile.fam[i].getMemberName();
            String l_memberRelationship=studentProfile.fam[i].getMemberRelationship();
            String l_memberOccupation=studentProfile.fam[i].getMemberOccupation();
            String l_memberEmailID=studentProfile.fam[i].getMemberEmailID();
            String l_memberContactNo=studentProfile.fam[i].getMemberContactNo();
            String l_memberImgPath=studentProfile.fam[i].getMemberImgPath();
            String l_notificationRequired=studentProfile.fam[i].getNotificationRequired();
 
                  dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",39,l_studentID,l_memberID,l_memberName,l_memberRelationship,l_memberOccupation,l_memberEmailID,l_memberContactNo,l_memberImgPath,l_versionNumber,l_notificationRequired);

        }
        
        dbg("end of student profile create"); 
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
        dbg("inside student profile--->auth update"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<StudentProfile> reqBody = request.getReqBody();
        StudentProfile studentProfile =reqBody.get();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_studentID=studentProfile.getStudentID();
        String[] l_primaryKey={l_studentID};
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("15", l_checkerID);
         l_column_to_update.put("17", l_checkerDateStamp);
         l_column_to_update.put("19", l_authStatus);
         l_column_to_update.put("22", l_checkerRemarks);
        
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_PROFILE", l_primaryKey, l_column_to_update, session);
     
             l_column_to_update=new HashMap();
             l_column_to_update.put("6", l_checkerID);
             l_column_to_update.put("8", l_checkerDateStamp);
             l_column_to_update.put("10", l_authStatus);
             l_column_to_update.put("13", l_checkerRemarks);
             
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STUDENT_MASTER", l_primaryKey, l_column_to_update, session);                
                        
                        
        dbg("end of student profile--->auth update");          
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
        RequestBody<StudentProfile> reqBody = request.getReqBody();
        StudentProfile studentProfile =reqBody.get();
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
        String l_studentID=studentProfile.getStudentID();
        String l_studentName=studentProfile.getStudentName();
        String l_standard=studentProfile.gen.getStandard();
        String l_section=studentProfile.gen.getSection();
        String l_dob=studentProfile.gen.getDob();
        String l_bloodGroup=studentProfile.gen.getBloodGroup();
        String l_addressLine1=studentProfile.gen.address.getAddressLine1();
        String l_addressLine2=studentProfile.gen.address.getAddressLine2();
        String l_addressLine3=studentProfile.gen.address.getAddressLine3();
        String l_addressLine4=studentProfile.gen.address.getAddressLine4();
        String l_addressLine5=studentProfile.gen.address.getAddressLine5();
        String l_profileImgPath=studentProfile.getProfileImagePath();
        String l_notes=studentProfile.getNote();
        String l_existingMedicalDetail=studentProfile.emer.getExistingMedicalDetails();
        String l_communicationLang=studentProfile.gen.getCommunicationLanguage();
        String l_nationalID=studentProfile.gen.getNationalID();
        String l_gender=studentProfile.gen.getGender();
        l_column_to_update= new HashMap();
        l_column_to_update.put("1", l_studentID);
        l_column_to_update.put("2", l_studentName);
        l_column_to_update.put("3", l_standard);
        l_column_to_update.put("4", l_section);
        l_column_to_update.put("5", l_dob);
        l_column_to_update.put("6", l_bloodGroup);
        l_column_to_update.put("7", l_addressLine1);
        l_column_to_update.put("8", l_addressLine2);
        l_column_to_update.put("9", l_addressLine3);
        l_column_to_update.put("10", l_addressLine4);
        l_column_to_update.put("11", l_addressLine5);
        l_column_to_update.put("12", l_profileImgPath);
        l_column_to_update.put("13", l_notes);
        l_column_to_update.put("14", l_makerID);
        l_column_to_update.put("15", l_checkerID);
        l_column_to_update.put("16", l_makerDateStamp);
        l_column_to_update.put("17", l_checkerDateStamp);
        l_column_to_update.put("18", l_recordStatus);
        l_column_to_update.put("19", l_authStatus);
        l_column_to_update.put("20", l_versionNumber);
        l_column_to_update.put("21", l_makerRemarks);
        l_column_to_update.put("22", l_checkerRemarks);
        l_column_to_update.put("23", l_existingMedicalDetail);
        l_column_to_update.put("24", l_communicationLang);
        l_column_to_update.put("25", l_nationalID);
        l_column_to_update.put("26", l_gender);
         
        String[] l_primaryKey={l_studentID};
      
          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_PROFILE", l_primaryKey, l_column_to_update, session);

        l_column_to_update= new HashMap();
        l_column_to_update.put("2", l_studentName);
        l_column_to_update.put("3", l_standard);
        l_column_to_update.put("4", l_section);
        l_column_to_update.put("5", l_makerID);
        l_column_to_update.put("6", l_checkerID);
        l_column_to_update.put("7", l_makerDateStamp);
        l_column_to_update.put("8", l_checkerDateStamp);
        l_column_to_update.put("9", l_recordStatus);
        l_column_to_update.put("10", l_authStatus);
        l_column_to_update.put("11", l_versionNumber);
        l_column_to_update.put("12", l_makerRemarks);
        l_column_to_update.put("13", l_checkerRemarks);
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STUDENT_MASTER", l_primaryKey, l_column_to_update, session);  
          

        IDBReadBufferService readBuffer = inject.getDBReadBufferService();
//        Map<String,DBRecord>l_contactPersonMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_CONTACT_PERSON_DETAILS", session, dbSession);
        Map<String,DBRecord>l_familyMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_FAMILY_DETAILS", session, dbSession);
//        setOperationsOfTheRecord("SVW_CONTACT_PERSON_DETAILS",l_contactPersonMap);
        setOperationsOfTheRecord("SVW_FAMILY_DETAILS",l_familyMap);
        
//        for(int j=0;j<studentProfile.emer.cp.length;j++){
//            String l_cpID=studentProfile.emer.cp[j].getCp_ID();
//            String l_cpName=studentProfile.emer.cp[j].getCp_name();
//            String l_cpRelationship=studentProfile.emer.cp[j].getCp_relationship();
//            String l_cpOccupation=studentProfile.emer.cp[j].getCp_occupation();
//            String l_cpEmailID=studentProfile.emer.cp[j].getCp_mailID();
//            String l_cpcontactNo= studentProfile.emer.cp[j].getCp_contactNo();
//            String l_cpImgPath= studentProfile.emer.cp[j].getCp_imgPath();
//            
//            if(studentProfile.emer.cp[j].getOperation().equals("U")){
//            
//                    l_column_to_update= new HashMap();
//                    l_column_to_update.put("1", l_studentID);
//                    l_column_to_update.put("2", l_cpID);
//                    l_column_to_update.put("3", l_cpName);
//                    l_column_to_update.put("4", l_cpRelationship);
//                    l_column_to_update.put("5", l_cpOccupation);
//                    l_column_to_update.put("6", l_cpEmailID);
//                    l_column_to_update.put("7", l_cpcontactNo);
//                    l_column_to_update.put("8", l_cpImgPath);
//                    l_column_to_update.put("9", l_versionNumber);
//                    String[] l_cpPKey={l_cpID};
//
//                  dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_CONTACT_PERSON_DETAILS", l_cpPKey, l_column_to_update, session);
//            }else{
//                
//                dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",38,l_studentID,l_cpID,l_cpName,l_cpRelationship,l_cpOccupation,l_cpEmailID,l_cpcontactNo,l_cpImgPath,l_versionNumber);
//            }  
//        }
        
        for(int i=0;i<studentProfile.fam.length;i++){
            String l_memberID=studentProfile.fam[i].getMemberID();
            dbg("l_memberID"+l_memberID);
            String l_memberName=studentProfile.fam[i].getMemberName();
            String l_memberRelationship=studentProfile.fam[i].getMemberRelationship();
            String l_memberOccupation=studentProfile.fam[i].getMemberOccupation();
            String l_memberEmailID=studentProfile.fam[i].getMemberEmailID();
            String l_memberContactNo=studentProfile.fam[i].getMemberContactNo();
            String l_memberImgPath=studentProfile.fam[i].getMemberImgPath();
            String l_notificationRequired=studentProfile.fam[i].getNotificationRequired();
            if(studentProfile.fam[i].getOperation().equals("U")){
            
            l_column_to_update= new HashMap();
            l_column_to_update.put("1", l_studentID);
            l_column_to_update.put("2", l_memberID);
            l_column_to_update.put("3", l_memberName);
            l_column_to_update.put("4", l_memberRelationship);
            l_column_to_update.put("5", l_memberOccupation);
            l_column_to_update.put("6", l_memberEmailID);
            l_column_to_update.put("7", l_memberContactNo);
            l_column_to_update.put("8", l_memberImgPath);
            l_column_to_update.put("9", l_versionNumber);
            l_column_to_update.put("10", l_notificationRequired);
            String[] l_familyPKey={l_memberID};

                   dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_FAMILY_DETAILS", l_familyPKey, l_column_to_update, session);

            }else{
                
                  dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",39,l_studentID,l_memberID,l_memberName,l_memberRelationship,l_memberOccupation,l_memberEmailID,l_memberContactNo,l_memberImgPath,l_versionNumber);         
            }
        }           
                  
//    ArrayList<String>cpList=getRecordsToDelete("SVW_CONTACT_PERSON_DETAILS",l_contactPersonMap);      
    ArrayList<String>familyListList=getRecordsToDelete("SVW_FAMILY_DETAILS",l_familyMap); 
    
//        for(int i=0;i<cpList.size();i++){
//            String pkey=cpList.get(i);
//            deleteRecordsInTheList("SVW_CONTACT_PERSON_DETAILS",pkey);
//            
//        }
        
        for(int i=0;i<familyListList.size();i++){
            String pkey=familyListList.get(i);
            deleteRecordsInTheList("SVW_FAMILY_DETAILS",pkey);
            
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
            RequestBody<StudentProfile> reqBody = request.getReqBody();
            StudentProfile studentProfile =reqBody.get();
            dbg("tableName"+tableName);
            
            switch(tableName){
                
//                case "SVW_CONTACT_PERSON_DETAILS":  
//                
//                         for(int i=0;i<studentProfile.emer.cp.length;i++){
//                                String l_contactPersonID=studentProfile.emer.cp[i].cp_ID;
//                                String l_pkey=l_contactPersonID;
//                               if(tableMap.containsKey(l_pkey)){
//
//                                    studentProfile.emer.cp[i].setOperation("U");
//                                }else{
//
//                                    studentProfile.emer.cp[i].setOperation("C");
//                                }
//                         } 
//                  break;      
                  
                  
                  case "SVW_FAMILY_DETAILS":  
                
                         for(int i=0;i<studentProfile.fam.length;i++){
                                String l_familyMemberID=studentProfile.fam[i].getMemberID();
                                String l_pkey=l_familyMemberID;
                               if(tableMap.containsKey(l_pkey)){

                                    studentProfile.fam[i].setOperation("U");
                                }else{

                                    studentProfile.fam[i].setOperation("C");
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
           RequestBody<StudentProfile> reqBody = request.getReqBody();
           StudentProfile studentProfile =reqBody.get();
           ArrayList<String>recordsToDelete=new ArrayList();
           String studentID=studentProfile.getStudentID();
//           Iterator<String>keyIterator=tableMap.keySet().iterator();
        
           List<DBRecord>filteredRecords=tableMap.values().stream().filter(rec->rec.getRecord().get(0).trim().equals(studentID)).collect(Collectors.toList());

           dbg("tableName"+tableName);
           switch(tableName){
           
//                 case "SVW_CONTACT_PERSON_DETAILS":   
//                   
////                   while(keyIterator.hasNext()){
//                      for(int j=0;j<filteredRecords.size();j++){
//                        String contavtPersonID=filteredRecords.get(j).getRecord().get(1).trim();
//                        String tablePkey=contavtPersonID;
//                        dbg("tablePkey"+tablePkey);
//                        boolean recordExistence=false;
//
//                        for(int i=0;i<studentProfile.emer.cp.length;i++){
//                           String l_contactPersonID=studentProfile.emer.cp[i].cp_ID;
//                           String l_requestPkey=l_contactPersonID;
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
                   
                   
                case "SVW_FAMILY_DETAILS":   
                   
                   for(int j=0;j<filteredRecords.size();j++){
                        String memberID=filteredRecords.get(j).getRecord().get(1).trim();
                        String tablePkey=memberID;
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                        for(int i=0;i<studentProfile.fam.length;i++){
                           String l_familyMemberID=studentProfile.fam[i].getMemberID(); 
                           String l_requestPkey=l_familyMemberID;
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
             RequestBody<StudentProfile> reqBody = request.getReqBody();
             StudentProfile studentProfile =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_studentID=studentProfile.getStudentID();
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
        dbg("inside student profile delete");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentProfile> reqBody = request.getReqBody();
        StudentProfile studentProfile =reqBody.get();
        String l_studentID=studentProfile.getStudentID();
        String[] l_primaryKey={l_studentID};
              dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_PROFILE", l_primaryKey, session);
                  
              dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STUDENT_MASTER", l_primaryKey, session);
            
//            for(int j=0;j<studentProfile.emer.cp.length;j++){
//            String l_cpID=studentProfile.emer.cp[j].getCp_ID();
//             String[] l_contactPersonPK={l_cpID};
//                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_CONTACT_PERSON_DETAILS", l_contactPersonPK, session);
//
//            }
            for(int j=0;j<studentProfile.fam.length;j++){
            String l_memberID=studentProfile.fam[j].getMemberID();
             String[] l_familyPK={l_memberID};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_FAMILY_DETAILS", l_familyPK, session);

            }
            dbg("end of student profile delete");
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

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException{
                
        try{      
        dbg("inside student profile--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentProfile> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        StudentProfile studentProfile =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_studentID=studentProfile.getStudentID();
        String[] l_pkey={l_studentID};
        DBRecord profileRec=null;
        Map<String,DBRecord>l_familyMap=null;
        
        
            try{
                profileRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_PROFILE", l_pkey, session, dbSession);
        //        Map<String,DBRecord>l_contactPersonMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_CONTACT_PERSON_DETAILS", session, dbSession);
                l_familyMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_FAMILY_DETAILS", session, dbSession);
        
           }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", l_studentID);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
                }
        
        
        
        buildBOfromDB(profileRec,l_familyMap);     
        
          dbg("end of  completed student profile--->view");                
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
    private void buildBOfromDB(DBRecord p_studentProfileRecord,Map<String, DBRecord> p_familyMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<StudentProfile> reqBody = request.getReqBody();
           StudentProfile studentProfile =reqBody.get();
           ArrayList<String>l_studentProfileList= p_studentProfileRecord.getRecord();
           
           if(l_studentProfileList!=null&&!l_studentProfileList.isEmpty()){
               
            studentProfile.gen=new General();
//            studentProfile.gen.classs=new Class();
            studentProfile.setStudentName(l_studentProfileList.get(1).trim());
            studentProfile.gen.setStandard(l_studentProfileList.get(2).trim());
            studentProfile.gen.setSection(l_studentProfileList.get(3).trim());
            studentProfile.gen.setDob(l_studentProfileList.get(4).trim());
            studentProfile.gen.setBloodGroup(l_studentProfileList.get(5).trim());
            studentProfile.gen.address=new Address();
            studentProfile.gen.address.setAddressLine1(l_studentProfileList.get(6).trim());
            studentProfile.gen.address.setAddressLine2(l_studentProfileList.get(7).trim());
            studentProfile.gen.address.setAddressLine3(l_studentProfileList.get(8).trim());
            studentProfile.gen.address.setAddressLine4(l_studentProfileList.get(9).trim());
            studentProfile.gen.address.setAddressLine5(l_studentProfileList.get(10).trim());
            studentProfile.setProfileImagePath(l_studentProfileList.get(11).trim());
            studentProfile.setNote(l_studentProfileList.get(12).trim());
            request.getReqAudit().setMakerID(l_studentProfileList.get(13).trim());
            request.getReqAudit().setCheckerID(l_studentProfileList.get(14).trim());
            request.getReqAudit().setMakerDateStamp(l_studentProfileList.get(15).trim());
            request.getReqAudit().setCheckerDateStamp(l_studentProfileList.get(16).trim());
            request.getReqAudit().setRecordStatus(l_studentProfileList.get(17).trim());
            request.getReqAudit().setAuthStatus(l_studentProfileList.get(18).trim());
            request.getReqAudit().setVersionNumber(l_studentProfileList.get(19).trim());
            request.getReqAudit().setMakerRemarks(l_studentProfileList.get(20).trim());
            request.getReqAudit().setCheckerRemarks(l_studentProfileList.get(21).trim());
            studentProfile.gen.setCommunicationLanguage(l_studentProfileList.get(23).trim());
            studentProfile.gen.setNationalID(l_studentProfileList.get(24).trim());
            studentProfile.gen.setGender(l_studentProfileList.get(25).trim());
            } 
           
               
            studentProfile.emer=new Emergency();   
               
            studentProfile.emer.setExistingMedicalDetails(l_studentProfileList.get(22).trim());
               
//             if(p_contactPersonMap!=null&&!p_contactPersonMap.isEmpty()){
//                
//                Iterator<DBRecord> valueIterator= p_contactPersonMap.values().iterator();
//                 
//                studentProfile.emer.cp=new ContactPerson[p_contactPersonMap.size()];
//                int i=0;
//                while(valueIterator.hasNext()){
//                    DBRecord l_cpRecords=valueIterator.next();
//                    studentProfile.emer.cp[i]=new ContactPerson();
//                    studentProfile.emer.cp[i].setCp_ID(l_cpRecords.getRecord().get(1).trim());
//                    studentProfile.emer.cp[i].setCp_name(l_cpRecords.getRecord().get(2).trim());
//                    studentProfile.emer.cp[i].setCp_relationship(l_cpRecords.getRecord().get(3).trim());
//                    studentProfile.emer.cp[i].setCp_occupation(l_cpRecords.getRecord().get(4).trim());
//                    studentProfile.emer.cp[i].setCp_mailID(l_cpRecords.getRecord().get(5).trim());
//                    studentProfile.emer.cp[i].setCp_contactNo(l_cpRecords.getRecord().get(6).trim());
//                    studentProfile.emer.cp[i].setCp_imgPath(l_cpRecords.getRecord().get(7).trim());
//                    i++;
//                }
//            }
             
             if(p_familyMap!=null&&!p_familyMap.isEmpty()){
                 List<DBRecord>contactPersonList= p_familyMap.values().stream().filter(rec->rec.getRecord().get(8).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.toList());
                studentProfile.fam=new Family[contactPersonList.size()];
                for(int k=0;k<contactPersonList.size();k++){
                  DBRecord l_familyRecords=contactPersonList.get(k);
                   studentProfile.fam[k]=new Family();
                   studentProfile.fam[k].setMemberID(l_familyRecords.getRecord().get(1).trim());
                   studentProfile.fam[k].setMemberName(l_familyRecords.getRecord().get(2).trim());
                   studentProfile.fam[k].setMemberRelationship(l_familyRecords.getRecord().get(3).trim());
                   studentProfile.fam[k].setMemberOccupation(l_familyRecords.getRecord().get(4).trim());
                   studentProfile.fam[k].setMemberEmailID(l_familyRecords.getRecord().get(5).trim());
                   studentProfile.fam[k].setMemberContactNo(l_familyRecords.getRecord().get(6).trim());
                   studentProfile.fam[k].setMemberImgPath(l_familyRecords.getRecord().get(7).trim());
                   studentProfile.fam[k].setNotificationRequired(l_familyRecords.getRecord().get(9).trim());
               } 
            }
            
          dbg("end of  buildBOfromDB"); 
           
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
 }
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        JsonObject general;
        JsonObject emergency;
        try{
        dbg("inside student profile buildJsonResFromBO");    
        RequestBody<StudentProfile> reqBody = request.getReqBody();
        StudentProfile studentProfile =reqBody.get();
//        JsonArrayBuilder cpArr=Json.createArrayBuilder();
        JsonArrayBuilder familyArr=  Json.createArrayBuilder();
             
//           for(int j=0;j<studentProfile.emer.cp.length;j++){
//               cpArr.add(Json.createObjectBuilder().add("cp_ID", studentProfile.emer.cp[j].getCp_ID())
//                                                   .add("cp_Name", studentProfile.emer.cp[j].getCp_name())
//                                                   .add("cp_relationship", studentProfile.emer.cp[j].getCp_relationship())
//                                                   .add("cp_occupation", studentProfile.emer.cp[j].getCp_occupation())
//                                                   .add("cp_emailID", studentProfile.emer.cp[j].getCp_mailID())
//                                                   .add("cp_contactNo", studentProfile.emer.cp[j].getCp_contactNo())
//                                                   .add("cp_imgPath", studentProfile.emer.cp[j].getCp_imgPath()));
//           }
               emergency=Json.createObjectBuilder().add("existingMedicalDetails", studentProfile.getEmer().getExistingMedicalDetails())
//                                                   .add("contactPerson", cpArr)
                                                   .build();
               general=Json.createObjectBuilder()  .add("class",studentProfile.gen.getStandard()+"/"+studentProfile.gen.getSection())
                                                   .add("dob", studentProfile.gen.getDob())
                                                   .add("bloodGroup", studentProfile.gen.getBloodGroup())
                                                   
                                                   .add("nationalID", studentProfile.gen.getNationalID())
                                                   .add("gender", studentProfile.gen.getGender())
                                                   .add("address",Json.createObjectBuilder().add("addressLine1", studentProfile.gen.address.getAddressLine1()).add("addressLine2", studentProfile.gen.address.getAddressLine2()).add("addressLine3", studentProfile.gen.address.getAddressLine3()).add("addressLine4", studentProfile.gen.address.getAddressLine4()).add("addressLine5", studentProfile.gen.address.getAddressLine5())).build();
           for(int i=0;i<studentProfile.fam.length;i++){
               
               boolean notificationRequired=false;
               if(studentProfile.fam[i].getNotificationRequired().equals("Y")){
                   
                   notificationRequired=true;
                   
               }
               
               
               
               
               familyArr.add(Json.createObjectBuilder().add("memberID", studentProfile.fam[i].getMemberID())
                                                       .add("memberName", studentProfile.fam[i].getMemberName())
                                                       .add("memberRelationship", studentProfile.fam[i].getMemberRelationship())
                                                       .add("memberOccupation", studentProfile.fam[i].getMemberOccupation())
                                                       .add("memberEmailID", studentProfile.fam[i].getMemberEmailID())
                                                       .add("memberContactNo", studentProfile.fam[i].getMemberContactNo()) 
                                                       .add("notificationRequired", notificationRequired)
                                                       .add("language", studentProfile.gen.getCommunicationLanguage())
                                                       .add("memberImgPath", studentProfile.fam[i].getMemberImgPath()));

           }
            body=Json.createObjectBuilder().add("studentID", studentProfile.getStudentID())
                                           .add("studentName", studentProfile.getStudentName())
                                           .add("profileImgPath", studentProfile.getProfileImagePath())
                                           .add("general", general)
                                           .add("emergency", emergency)
                                           .add("family", familyArr)
                                           .add("note", studentProfile.getNote()).build();
                                            
              dbg(body.toString());  
           dbg("end of student profile buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student profile--->businessValidation");    
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
       dbg("end of student profile--->businessValidation"); 
       }catch(BSProcessingException ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
       }catch(DBValidationException ex){
           throw ex;
            
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
        dbg("inside student profile master mandatory validation");
        RequestBody<StudentProfile> reqBody = request.getReqBody();
        StudentProfile studentProfile =reqBody.get();
        String instituteID=request.getReqHeader().getInstituteID();
        BusinessService bs=inject.getBusinessService(session);
        
        
//       studentProfile.StudentID=bs.studentValidation(studentProfile.getStudentID(), studentProfile.getStudentName(), instituteID, session, dbSession, inject);
        
        
        
         if(studentProfile.StudentID==null||studentProfile.StudentID.isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","studentID");  
         }
          
          
        dbg("end of student profile master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
     private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
      return true;
        
    }
    private boolean detailMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
        boolean status=true;
        RequestBody<StudentProfile> reqBody = request.getReqBody();
        StudentProfile student =reqBody.get();
        
        try{
            if(student.StudentName==null||student.StudentName.isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","studentName");  
         }
            dbg("inside student profile detailMandatoryValidation");
//            if(student.getProfileImagePath()==null||student.getProfileImagePath().isEmpty()){
//                    status=false;
//                    errhandler.log_app_error("BS_VAL_002","profile Image");
//                }
            
            if(student.gen==null){
                status=false;
                errhandler.log_app_error("BS_VAL_002","general");
            }else{
//                if(student.gen.getClasss()==null){
//                    status=false;
//                    errhandler.log_app_error("BS_VAL_002","class");
//                }else{
                     if(student.gen.getStandard()==null||student.gen.getStandard().equals("")){
                          status=false;
                          errhandler.log_app_error("BS_VAL_002","standard");
                      }
                      if(student.gen.getSection()==null || student.gen.getSection().equals("") ){
                           status=false;
                           errhandler.log_app_error("BS_VAL_002","section");
                      }
//                }
                
//                if(student.gen.getDob()==null || student.gen.getDob().equals("") ){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_002","dob");
//                }
//                if(student.gen.getBloodGroup()==null || student.gen.getBloodGroup().equals("")){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_002","bloodGroup");
//                }
//                if(student.gen.getCommunicationLanguage()==null || student.gen.getCommunicationLanguage().equals("")){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_002","Communication Language");
//                }
//                if(student.gen.getNationalID()==null || student.gen.getNationalID().equals("")){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_002","National ID");
//                }
//                if(student.gen.getGender()==null || student.gen.getGender().equals("")){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_002","Gender");
//                }
//                if(student.gen.getAddress()==null){
//                    status=false;
//                    errhandler.log_app_error("BS_VAL_002","address");
//                }else{
////                     if(student.getGen().getAddress().getAddressLine1()==null || student.getGen().getAddress().getAddressLine1().equals("") ){
////                          status=false;
////                          errhandler.log_app_error("BS_VAL_002","addressLine1");
////                     }
//                     if(student.getGen().getAddress().getAddressLine2()==null || student.getGen().getAddress().getAddressLine2().equals("") ){
//                          status=false;
//                          errhandler.log_app_error("BS_VAL_002","addressLine2");
//                     }
//                     if(student.getGen().getAddress().getAddressLine3()==null || student.getGen().getAddress().getAddressLine3().equals("") ){
//                          status=false;
//                          errhandler.log_app_error("BS_VAL_002","addressLine3");
//                     }
//                     if(student.getGen().getAddress().getAddressLine4()==null || student.getGen().getAddress().getAddressLine4().equals("") ){
//                          status=false;
//                          errhandler.log_app_error("BS_VAL_002","addressLine4");
//                     }
//                     if(student.getGen().getAddress().getAddressLine5()==null || student.getGen().getAddress().getAddressLine5().equals("") ){
//                          status=false;
//                          errhandler.log_app_error("BS_VAL_002","addressLine5");
//                     }
//                 }
            }

//           if(student.getEmer()==null){
//                status=false;
//                errhandler.log_app_error("BS_VAL_002","emergency");
//            }else{
//             if(student.getEmer().getCp()==null || student.getEmer().getCp().length==0){
//                   status=false;
//                   errhandler.log_app_error("BS_VAL_002","contact Person");
//              }else{
//                  for(int i=0;i<student.getEmer().getCp().length;i++){
//                      
//                      if(student.getEmer().getCp()[i].getCp_name()==null || student.getEmer().getCp()[i].getCp_name().equals("")){
//                             status=false;
//                             errhandler.log_app_error("BS_VAL_002","contact person's name");
//                      }
//                      if(student.getEmer().getCp()[i].getCp_relationship()==null || student.getEmer().getCp()[i].getCp_relationship().equals("") ){
//                              status=false;
//                              errhandler.log_app_error("BS_VAL_002","contact person's relationship");
//                      } 
//                      if(student.getEmer().getCp()[i].getCp_contactNo()==null || student.getEmer().getCp()[i].getCp_contactNo().equals("") ){
//                               status=false;
//                               errhandler.log_app_error("BS_VAL_002","contact person's contact no");
//                      }
//                      if(student.getEmer().getCp()[i].getCp_mailID()==null || student.getEmer().getCp()[i].getCp_mailID().equals("") ){
//                               status=false;
//                               errhandler.log_app_error("BS_VAL_002","contact person's email id");
//                      }
//                      if(student.getEmer().getCp()[i].getCp_occupation()==null || student.getEmer().getCp()[i].getCp_occupation().equals("") ){
//                               status=false;
//                               errhandler.log_app_error("BS_VAL_002","contact person's occupation");
//                      }
//                   }  
//                }
//            }

                   if(student.getFam().length==0){
                        status=false;
                        errhandler.log_app_error("BS_VAL_002","family");
                   }else{
                        for(int i=0;i<student.getFam().length;i++){
                            
                                if(student.getFam()[i].getMemberName()==null || student.getFam()[i].getMemberName().equals("") ){
                                    status=false;
                                    errhandler.log_app_error("BS_VAL_002","family member's name");
                                }
         
                                if(student.getFam()[i].getMemberRelationship()==null || student.getFam()[i].getMemberRelationship().equals("")){
                                    status=false;
                                    errhandler.log_app_error("BS_VAL_002","family member's relationship");
                                }

//                                if(student.getFam()[i].getMemberOccupation()==null){
//                                    status=false;
//                                    errhandler.log_app_error("BS_VAL_002","family member's Occupation");
//                                }

                                if(student.getFam()[i].getMemberContactNo()==null ||student.getFam()[i].getMemberContactNo().equals("") ){
                                    status=false;
                                    errhandler.log_app_error("BS_VAL_002","family member's ContactNo");
                                }
                             
//                                if(student.getFam()[i].getMemberEmailID()==null ||student.getFam()[i].getMemberEmailID().equals("") ){
//                                    status=false;
//                                    errhandler.log_app_error("BS_VAL_002","family member's Email id");
//                                }
//                             
//                                if(student.getFam()[i].getMemberImgPath()==null ||student.getFam()[i].getMemberImgPath().equals("") ){
//                                    status=false;
//                                    errhandler.log_app_error("BS_VAL_002","family member's Image");
//                                }
                                
                                if(student.getFam()[i].getNotificationRequired()==null ||student.getFam()[i].getNotificationRequired().equals("") ){
                                    status=false;
                                    errhandler.log_app_error("BS_VAL_002","Notification required");
                                }
                        }
                   }
           dbg("end of student profile detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student profile detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             IPDataService pds=inject.getPdataservice();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             RequestBody<StudentProfile> reqBody = request.getReqBody();
             StudentProfile student =reqBody.get();
             String l_standard=student.getGen().getStandard();
             String l_section=student.getGen().getSection();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_dob=student.getGen().getDob();
             String l_bloodGroup=student.getGen().getBloodGroup();
             String l_gender=student.getGen().getGender();
             
             String[] pkey={l_instituteID};
             ArrayList<String>contractList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",pkey);
            
             String contractLanguage=contractList.get(12).trim();
             dbg("contract language"+contractLanguage);
             
             if(!bsv.standardSectionValidation(l_standard,l_section, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","standard or section");
             }
             
             if(l_dob!=null&&!l_dob.isEmpty()){
             
             if(!bsv.dateFormatValidation(l_dob, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","DOB");
             }
             
             }
             
             if(l_bloodGroup!=null&&!l_bloodGroup.isEmpty()&&!l_bloodGroup.equals("Others")){
             
             if(!bsv.bloodGroupValidation(l_bloodGroup, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Blood group");
             }
             
             }
             
             if(l_gender!=null&&!l_gender.isEmpty()){
             
             if((!(l_gender.equals("M")||l_gender.equals("F")||l_gender.equals("O")))){
                 
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Gender");
             }
             }
             
//           for(int i=0;i<student.getEmer().getCp().length;i++){
//               String l_cp_contactNo=student.getEmer().getCp()[i].getCp_contactNo();
//               String l_cpEmailID=student.getEmer().getCp()[i].getCp_mailID();
//               
//             if(!bsv.contactNoValidation(l_cp_contactNo, session, dbSession, inject)){
//                 status=false;
//                 errhandler.log_app_error("BS_VAL_003","contact person's contact no");
//             }
//             if(!bsv.emailValidation(l_cpEmailID, session, dbSession, inject)){
//                 status=false;
//                 errhandler.log_app_error("BS_VAL_003","contact person's mail id");
//             }
//           }
           
           int notificationReqCount=0;
            for(int i=0;i<student.getFam().length;i++){
               String l_memberContactNo=student.getFam()[i].getMemberContactNo();
               String l_memberEmailID=student.getFam()[i].getMemberEmailID();
               String l_notificationRequired=student.getFam()[i].getNotificationRequired();
             
               if(!bsv.contactNoValidation(l_memberContactNo, session, dbSession, inject)){
                   status=false;
                   errhandler.log_app_error("BS_VAL_003","family member's contact no If there is no country code please enter country code before mobile no and + prefixed");
               }
               
               if(l_memberEmailID!=null&&!l_memberEmailID.isEmpty()){ 
              if(!bsv.emailValidation(l_memberEmailID, session, dbSession, inject)){
                  status=false;
                  errhandler.log_app_error("BS_VAL_003","family member's email id");
               }
               }
              
              if((!(l_notificationRequired.equals("Y")||l_notificationRequired.equals("N")))){
                 
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Notifification required");
             }else if(l_notificationRequired.equals("Y")){
                  
                  notificationReqCount=notificationReqCount+1;
                  
                  if(notificationReqCount>1){
                      
                       status=false;
                       errhandler.log_app_error("BS_VAL_002","Notification required check box for only one contact person");
                       throw new BSValidationException("BSValidationException");
                  }
                  
                  
                  String notificationLanguage=student.getGen().getCommunicationLanguage();
                            
                  if(notificationLanguage.isEmpty()||notificationLanguage.equals("")){
                      
                       status=false;
                       errhandler.log_app_error("BS_VAL_002","Communication language");
                       throw new BSValidationException("BSValidationException");
                  }
                  
                  
                  
                  if(contractLanguage.equals("EN")){
                      
                        if(!notificationLanguage.equals("EN")){
                 
                             status=false;
                             errhandler.log_app_error("BS_VAL_002","Communication language as english");
                        }
                      
                  }else if(contractLanguage.equals("TN")){
                      
                        if(!notificationLanguage.equals("TN")){
                 
                             status=false;
                             errhandler.log_app_error("BS_VAL_002","Communication language as tamil");
                        }
                      
                  }
                  
              }
               
           }
            
           if(notificationReqCount!=1){
                      
                       status=false;
                       errhandler.log_app_error("BS_VAL_002","Notification required check box for only one contact person");
                       throw new BSValidationException("BSValidationException");
                  }
            
            
            dbg("end of student profile detailDataValidation");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch(DBValidationException ex){
            throw ex;
         } catch(BSValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        
        return status;
              
    }
 public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     exAudit=new ExistingAudit();
     try{
        dbg("inside StudentProfile--->getExistingAudit") ;
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_operation=request.getReqHeader().getOperation();
        String l_service=request.getReqHeader().getService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
        if(!(l_operation.equals("Create")||l_operation.equals("View"))){
             
               dbg("inside UserProfile--->getExistingAudit--->Service--->UserProfile");  
               RequestBody<StudentProfile> studentProfileBody = request.getReqBody();
               StudentProfile studentProfile =studentProfileBody.get();
               String l_studentID=studentProfile.getStudentID();
               String[] l_pkey={l_studentID};
               DBRecord l_studentProfileRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_PROFILE", l_pkey, session, dbSession);
               exAudit.setAuthStatus(l_studentProfileRecord.getRecord().get(18).trim());
               exAudit.setMakerID(l_studentProfileRecord.getRecord().get(13).trim());
               exAudit.setRecordStatus(l_studentProfileRecord.getRecord().get(17).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_studentProfileRecord.getRecord().get(19).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of StudentProfile--->getExistingAudit") ;
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
 public  void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     
     
//     try{
//        dbg("inside relationshipProcessing") ;
//        RequestBody<StudentProfile> reqBody = request.getReqBody();
//        StudentProfile studentProfile =reqBody.get();
//        String l_studentID=studentProfile.getStudentID();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        String  versionNumber=null;
//        String[] l_pkey={l_studentID};
//        DBRecord studentMasterRec;
//        try{
//        
//            studentMasterRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STUDENT_MASTER", l_pkey, session,dbSession,true);
//            versionNumber=studentMasterRec.getRecord().get(10).trim();
//            dbg("versionNumber"+versionNumber);
//        }catch(DBValidationException ex){
//            if(ex.toString().contains("DB_VAL_011")){
//                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
//                exAudit.setRelatioshipOperation("C");
//                
//            }
//        }
//        
//        if(exAudit.getRelatioshipOperation().equals("C")){
//
//             buildRequestAndCallStudentMaster(1,"O");
//        }else if(exAudit.getRelatioshipOperation().equals("M")){
//            
//            buildRequestAndCallStudentMaster(Integer.parseInt(versionNumber)+1,"O");
//        }else if(exAudit.getRelatioshipOperation().equals("D")){
//            
//            buildRequestAndCallStudentMaster(Integer.parseInt(versionNumber)+1,"D");
//        }
//        
//   
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
 }
    
 private void buildRequestAndCallStudentMaster(int p_versionNumber,String p_recordStatus)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
     
//        try{
//        dbg("inside buildRequestAndCallStudentMaster") ;   
//        dbg("versionNumber"+p_versionNumber);
//        dbg("p_recordStatus"+p_recordStatus);
//        RequestBody<StudentProfile> reqBody = request.getReqBody();
//        StudentProfile studentProfile =reqBody.get();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IStudentMasterService sms=inject.getStudentMasterService();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        JsonObject studentMaster;
//        String l_msgID=request.getReqHeader().getMsgID();
//        String l_correlationID=" ";
//        String l_service="StudentMaster";
//        String l_operation="AutoAuth";
//        JsonArray l_businessEntity=Json.createArrayBuilder().add(Json.createObjectBuilder().add("entityName", "instituteID")
//                                                                                             .add("entityValue", l_instituteID)).build();
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
//        String l_studentID=studentProfile.getStudentID();
//        String l_studentName=studentProfile.getStudentName();
//        String l_standard=studentProfile.gen.getStandard();
//        String l_section=studentProfile.gen.getSection();
//
//
//          studentMaster=Json.createObjectBuilder().add("header", Json.createObjectBuilder()
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
//                                                        .add("studentID", l_studentID)
//                                                        .add("studentName", l_studentName)
//                                                        .add("standard", l_standard)
//                                                        .add("section", l_section))
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
//          dbg("student master request"+studentMaster.toString());
//          dbg("before  studentmaster call");
//          sms.processing(studentMaster, session);
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
          
 }   

 
 public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }   

}