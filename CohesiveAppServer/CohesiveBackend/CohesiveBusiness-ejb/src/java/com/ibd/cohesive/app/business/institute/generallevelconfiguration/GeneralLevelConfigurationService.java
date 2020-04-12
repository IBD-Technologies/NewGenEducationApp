/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.generallevelconfiguration;

import com.ibd.businessViews.IGeneralLevelConfigurationService;
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
//@Local(IGeneralLevelConfigurationService.class)
@Remote(IGeneralLevelConfigurationService.class)
@Stateless
public class GeneralLevelConfigurationService implements IGeneralLevelConfigurationService  {
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public GeneralLevelConfigurationService(){
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
       dbg("inside GeneralLevelConfigurationService--->processing");
       
//       InitialContext contxt;
//       final Context remoteContxt;
//       Properties props = new Properties();
//       
//       contxt = new InitialContext();
//       props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
//       props.put("jboss.naming.client.ejb.context", "true"); 
//       remoteContxt= new InitialContext(props);
//       
//       
//       ISelectBoxMasterService select= (ISelectBoxMasterService)remoteContxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/SelectBoxMasterService!com.ibd.businessViews.ISelectBoxMasterService");
//       
//       select.processing(requestJson, session);
       
       dbg("GeneralLevelConfigurationService--->Processing--->I/P--->requestJson"+requestJson.toString());
     
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,requestJson);
       String l_operation=request.getReqHeader().getOperation();
       String l_userID=request.getReqHeader().getUserID();
       bs.requestlogging(request,requestJson, inject,session,dbSession);
       buildBOfromReq(requestJson);  
       
        if(!l_operation.equals("Create-Default")){
       
           RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
           GeneralLevelConfiguration generalConfiguration =reqBody.get();
           l_lockKey=generalConfiguration.getInstituteID();
           if(!businessLock.getBusinessLock(request, l_lockKey, session)){
               l_validation_status=false;
               throw new BSValidationException("BSValidationException");
            }
        }
       
       BusinessEJB<IGeneralLevelConfigurationService>generalConfigurationEJB=new BusinessEJB();
       generalConfigurationEJB.set(this);
       exAudit=bs.getExistingAudit(generalConfigurationEJB);
       
      if(!(bsv. businessServiceValidation(requestJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
      }
        if(!l_operation.equals("Create-Default")){
      
           if(!businessValidation(errhandler)){
               l_validation_status=false;
               throw new BSValidationException("BSValidationException");

           } 
      }else{
            
            if(!l_userID.equals("System")){
                errhandler.log_app_error("BS_VAL_014","Creation");
                throw new BSValidationException("BSValidationException");
            }
            
            
        }

        
        bs.businessServiceProcssing(request, exAudit, inject,generalConfigurationEJB);
       
       if(l_operation.equals("Create-Default")){
           
           createDefault();
       }
       
       

         if(l_session_created_now){
             jsonResponse= bs.buildSuccessResponse(requestJson, request, inject, session,generalConfigurationEJB) ;
             tc.commit(session,dbSession);
             dbg("commit is called in  service");
        }
       dbg("GeneralLevelConfigurationService--->Processing--->O/P--->jsonResponse"+jsonResponse);     
       dbg("End of generalConfigurationService--->processing");     
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

    private  void buildBOfromReq(JsonObject p_request)throws BSProcessingException,DBProcessingException{
       
       try{
       dbg("inside GeneralLevelConfigurationService--->buildBOfromReq"); 
       dbg("GeneralLevelConfigurationService--->buildBOfromReq--->I/P--->p_request"+p_request.toString()); 
       RequestBody<GeneralLevelConfiguration> reqBody = new RequestBody<GeneralLevelConfiguration>(); 
       GeneralLevelConfiguration generalConfiguration = new GeneralLevelConfiguration();
       String l_operation=request.getReqHeader().getOperation();
       
       if(!l_operation.equals("Create-Default")){
       
       JsonObject l_body=p_request.getJsonObject("body");
       generalConfiguration.setInstituteID(l_body.getString("instituteID"));
        generalConfiguration.setInstituteName(l_body.getString("instituteName"));
       if(!(l_operation.equals("View"))){
            generalConfiguration.setProfileImgPath(l_body.getString("profileImgPath"));
            JsonArray l_subjectArray=l_body.getJsonArray("SubjectMaster");
            JsonArray l_gradeArray=l_body.getJsonArray("GradeMaster");
            JsonArray l_examArray=l_body.getJsonArray("ExamMaster");
            JsonArray l_notificationArray=l_body.getJsonArray("NotificationMaster");
            JsonArray l_feeArray=l_body.getJsonArray("FeeTypeMaster");
            
            generalConfiguration.subjectMaster=new SubjectMaster[l_subjectArray.size()];
            
            for(int i=0;i<l_subjectArray.size();i++){
                JsonObject l_subjectObject=l_subjectArray.getJsonObject(i);
                generalConfiguration.subjectMaster[i]=new SubjectMaster();
                generalConfiguration.subjectMaster[i].setSubjectID(l_subjectObject.getString("subjectID"));
                generalConfiguration.subjectMaster[i].setSubjectName(l_subjectObject.getString("subjectName"));
                generalConfiguration.subjectMaster[i].setOtherLanguageDescription(l_subjectObject.getString("otherLangDescription"));
                
            }
            generalConfiguration.gradeMaster=new GradeMaster[l_gradeArray.size()];
            
            for(int i=0;i<l_gradeArray.size();i++){
                JsonObject l_gradeObject=l_gradeArray.getJsonObject(i);
                generalConfiguration.gradeMaster[i]=new GradeMaster();
                generalConfiguration.gradeMaster[i].setFrom(l_gradeObject.getString("from"));
                generalConfiguration.gradeMaster[i].setTo(l_gradeObject.getString("to"));
                generalConfiguration.gradeMaster[i].setGrade(l_gradeObject.getString("grade"));
            }
            
            generalConfiguration.examMaster=new ExamMaster[l_examArray.size()];
            for(int i=0;i<l_examArray.size();i++){
                JsonObject l_examObject=l_examArray.getJsonObject(i);
                generalConfiguration.examMaster[i]=new ExamMaster();
                generalConfiguration.examMaster[i].setExamType(l_examObject.getString("examType"));
                generalConfiguration.examMaster[i].setDescription(l_examObject.getString("description"));
                generalConfiguration.examMaster[i].setOtherLanguageDescription(l_examObject.getString("otherLangDescription"));
                
            }
            generalConfiguration.notificationMaster=new NotificationMaster[l_notificationArray.size()];
            
            for(int i=0;i<l_notificationArray.size();i++){
                JsonObject l_notificationObject=l_notificationArray.getJsonObject(i);
                generalConfiguration.notificationMaster[i]=new NotificationMaster();
                generalConfiguration.notificationMaster[i].setNotificationType(l_notificationObject.getString("notificationType"));
                generalConfiguration.notificationMaster[i].setDescription(l_notificationObject.getString("description"));
                generalConfiguration.notificationMaster[i].setOtherLanguageDescription(l_notificationObject.getString("otherLangDescription"));
                
            }
            
             generalConfiguration.feeMaster=new FeeTypeMaster[l_feeArray.size()];
            
            for(int i=0;i<l_feeArray.size();i++){
                JsonObject l_feeObject=l_feeArray.getJsonObject(i);
                generalConfiguration.feeMaster[i]=new FeeTypeMaster();
                generalConfiguration.feeMaster[i].setFeeType(l_feeObject.getString("feeType"));
                generalConfiguration.feeMaster[i].setFeeDescription(l_feeObject.getString("feeDescription"));
                generalConfiguration.feeMaster[i].setOtherLanguageDescription(l_feeObject.getString("otherLangDescription"));
            }
            
        }
       }
       reqBody.set(generalConfiguration);
      request.setReqBody(reqBody);
      dbg("End of GeneralLevelConfigurationService--->buildBOfromReq");
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
        String sequenceNo="I"+lock.getSequenceNo();
            
        RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
        GeneralLevelConfiguration generalConfig =reqBody.get();
        
        generalConfig.setInstituteID(sequenceNo);
        
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
        dbg("Inside GeneralLevelConfigurationService--->create"); 
        RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
        GeneralLevelConfiguration generalConfiguration =reqBody.get();
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
        
        String l_instituteID=generalConfiguration.getInstituteID();
        String l_instituteName=generalConfiguration.getInstituteName();
        String l_imgPath=generalConfiguration.getProfileImgPath();
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE",51,l_instituteID,l_instituteName,l_imgPath,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
       
        for(int i=0;i<generalConfiguration.subjectMaster.length;i++){
            
            String l_subjectID=generalConfiguration.subjectMaster[i].getSubjectID();
            String l_subjectName=generalConfiguration.subjectMaster[i].getSubjectName();
            String l_otherLangDescription=generalConfiguration.subjectMaster[i].getOtherLanguageDescription();
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",20,l_instituteID,l_subjectID,l_subjectName,l_versionNumber,l_otherLangDescription);
        }
        
        for(int i=0;i<generalConfiguration.gradeMaster.length;i++){
            
            String l_grade=generalConfiguration.gradeMaster[i].getGrade();
            String l_from=generalConfiguration.gradeMaster[i].getFrom();
            String l_to=generalConfiguration.gradeMaster[i].getTo();
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",44,l_instituteID,l_grade,l_from,l_to,l_versionNumber);
            
        }
        
        for(int i=0;i<generalConfiguration.examMaster.length;i++){
            
            String l_examType=generalConfiguration.examMaster[i].getExamType();
            String l_description=generalConfiguration.examMaster[i].getDescription();
            String l_otherLangDescription=generalConfiguration.examMaster[i].getOtherLanguageDescription();
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",17,l_instituteID,l_examType,l_description,l_versionNumber,l_otherLangDescription);
            
        }
        for(int i=0;i<generalConfiguration.notificationMaster.length;i++){
            
            String l_notificationType=generalConfiguration.notificationMaster[i].getNotificationType();
            String l_description=generalConfiguration.notificationMaster[i].getDescription();
            String l_otherLangDescription=generalConfiguration.notificationMaster[i].getOtherLanguageDescription();
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",68,l_instituteID,l_notificationType,l_description,l_versionNumber,l_otherLangDescription);
            
        }
        for(int i=0;i<generalConfiguration.feeMaster.length;i++){
            
            String l_feeType=generalConfiguration.feeMaster[i].getFeeType();
            String l_description=generalConfiguration.feeMaster[i].getFeeDescription();
            String l_otherLangDescription=generalConfiguration.feeMaster[i].getOtherLanguageDescription();
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",62,l_instituteID,l_feeType,l_description,l_versionNumber,l_otherLangDescription);
            
        }

        dbg("End of GeneralLevelConfigurationService--->create");
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
            dbg("Inside GeneralLevelConfigurationService--->update"); 
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            //String l_instituteID=request.getReqHeader().getInstituteID(); //Integration change
             RequestBody<GeneralLevelConfiguration> generalConfigurationBody = request.getReqBody();
               GeneralLevelConfiguration generalConfiguration =generalConfigurationBody.get();
              String l_instituteID=generalConfiguration.getInstituteID();//Integration fix
              
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
            Map<String,String>  l_column_to_update=new HashMap();
            l_column_to_update.put("5", l_checkerID);
            l_column_to_update.put("7", l_checkerDateStamp);
            l_column_to_update.put("9", l_authStatus);
            l_column_to_update.put("12", l_checkerRemarks);
             
             String[] l_primaryKey={l_instituteID};
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE","INSTITUTE_MASTER", l_primaryKey, l_column_to_update, session);

             dbg("End of GeneralLevelConfigurationService--->update");          
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
        dbg("Inside GeneralLevelConfigurationService--->fullUpdate");
        IDBTransactionService dbts=inject.getDBTransactionService();
        IPDataService pds=inject.getPdataservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
        GeneralLevelConfiguration generalConfiguration =reqBody.get();
       // String l_instituteID=request.getReqHeader().getInstituteID();  //Integration change
        String l_instituteID=generalConfiguration.getInstituteID();//Integration fix
            
       
        
        String l_instituteName=generalConfiguration.getInstituteName();
        String l_imagePath=generalConfiguration.getProfileImgPath();
		String makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        
        l_column_to_update= new HashMap();
        l_column_to_update.put("2", l_instituteName);
        l_column_to_update.put("3", l_imagePath);
		l_column_to_update.put("6", makerDateStamp);
        l_column_to_update.put("11", makerRemarks);
        
        String[] l_primaryKey={l_instituteID};
        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE","INSTITUTE_MASTER", l_primaryKey, l_column_to_update, session);
//        IDBReadBufferService readBuffer = inject.getDBReadBufferService();
        Map<String,ArrayList<String>>subjectMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SUBJECT_MASTER", session, dbSession);
        Map<String,ArrayList<String>>gradeMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SUBJECT_GRADE_MASTER", session, dbSession);   
        Map<String,ArrayList<String>>examMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_INSTITUTE_EXAM_MASTER", session, dbSession);   
        Map<String,ArrayList<String>>notificationMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_NOTIFICATION_TYPE_MASTER", session, dbSession);   
        Map<String,ArrayList<String>>feeMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_FEE_TYPE_MASTER", session, dbSession);   
        setOperationsOfTheRecord("IVW_SUBJECT_MASTER",subjectMap);      
        setOperationsOfTheRecord("IVW_SUBJECT_GRADE_MASTER",gradeMap);
        setOperationsOfTheRecord("IVW_INSTITUTE_EXAM_MASTER",examMap);
        setOperationsOfTheRecord("IVW_NOTIFICATION_TYPE_MASTER",notificationMap);
        setOperationsOfTheRecord("IVW_FEE_TYPE_MASTER",feeMap);
                        
                        
                        
        for(int i=0;i<generalConfiguration.subjectMaster.length;i++){
            
            String l_subjectID=generalConfiguration.subjectMaster[i].getSubjectID();
            String l_subjectName=generalConfiguration.subjectMaster[i].getSubjectName();
            String l_otherLangDescription=generalConfiguration.subjectMaster[i].getOtherLanguageDescription();
            
            
            if(generalConfiguration.subjectMaster[i].getOperation().equals("U")){
                    l_column_to_update= new HashMap();
                    l_column_to_update.put("3", l_subjectName);
                    l_column_to_update.put("5", l_otherLangDescription);
                    String[] l_subjectPKey={l_instituteID,l_subjectID};
                    dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SUBJECT_MASTER",l_subjectPKey,l_column_to_update,session);
            }else{
                   dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",20,l_instituteID,l_subjectID,l_subjectName,l_versionNumber,l_otherLangDescription);
            }

        }
        
        for(int i=0;i<generalConfiguration.gradeMaster.length;i++){
            
            String l_grade=generalConfiguration.gradeMaster[i].getGrade();
            String l_from=generalConfiguration.gradeMaster[i].getFrom();
            String l_to=generalConfiguration.gradeMaster[i].getTo();
            
            if(generalConfiguration.gradeMaster[i].getOperation().equals("U")){
            
                    l_column_to_update= new HashMap();
                    l_column_to_update.put("3", l_from);
                    l_column_to_update.put("4", l_to);
                    String[] l_gradePKey={l_instituteID,l_grade};
                    dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SUBJECT_GRADE_MASTER",l_gradePKey,l_column_to_update,session);
            }else{
                
                   dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",44,l_instituteID,l_grade,l_from,l_to,l_versionNumber);
            }
        }
        
        for(int i=0;i<generalConfiguration.examMaster.length;i++){
            
            String l_examType=generalConfiguration.examMaster[i].getExamType();
            String l_description=generalConfiguration.examMaster[i].getDescription();
            String l_otherLangDescription=generalConfiguration.examMaster[i].getOtherLanguageDescription();
            
            if(generalConfiguration.examMaster[i].getOperation().equals("U")){
                l_column_to_update= new HashMap();
                l_column_to_update.put("3", l_description);
                l_column_to_update.put("5", l_otherLangDescription);
                String[] l_examPKey={l_instituteID,l_examType};
                dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_INSTITUTE_EXAM_MASTER",l_examPKey,l_column_to_update,session);
            }else{
                dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",17,l_instituteID,l_examType,l_description,l_versionNumber,l_otherLangDescription);
            }
        }
        
        for(int i=0;i<generalConfiguration.notificationMaster.length;i++){
            
            String l_notificationType=generalConfiguration.notificationMaster[i].getNotificationType();
            String l_description=generalConfiguration.notificationMaster[i].getDescription();
            String l_otherLangDescription=generalConfiguration.notificationMaster[i].getOtherLanguageDescription();
            
            if(generalConfiguration.notificationMaster[i].getOperation().equals("U")){
                l_column_to_update= new HashMap();
                l_column_to_update.put("3", l_description);
                l_column_to_update.put("5", l_otherLangDescription);
                String[] l_notificationPKey={l_instituteID,l_notificationType};
                dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_NOTIFICATION_TYPE_MASTER",l_notificationPKey,l_column_to_update,session);
            }else{
                
                dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",68,l_instituteID,l_notificationType,l_description,l_versionNumber,l_otherLangDescription);
            }
        }
        for(int i=0;i<generalConfiguration.feeMaster.length;i++){
            
            String l_feeType=generalConfiguration.feeMaster[i].getFeeType();
            String l_description=generalConfiguration.feeMaster[i].getFeeDescription();
            String l_otherLangDescription=generalConfiguration.feeMaster[i].getOtherLanguageDescription();
            
            if(generalConfiguration.feeMaster[i].getOperation().equals("U")){
                l_column_to_update= new HashMap();
                l_column_to_update.put("3", l_description);
                l_column_to_update.put("5", l_otherLangDescription);
                String[] l_feePKey={l_instituteID,l_feeType};
                dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_FEE_TYPE_MASTER",l_feePKey,l_column_to_update,session);
            }else{
                dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",62,l_instituteID,l_feeType,l_description,l_versionNumber,l_otherLangDescription);
            }
        }
         ArrayList<String>subjectList=getRecordsToDelete("IVW_SUBJECT_MASTER",subjectMap);      
         ArrayList<String>gradeList=getRecordsToDelete("IVW_SUBJECT_GRADE_MASTER",gradeMap);
         ArrayList<String>examList=getRecordsToDelete("IVW_INSTITUTE_EXAM_MASTER",examMap);
         ArrayList<String>notificationList=getRecordsToDelete("IVW_NOTIFICATION_TYPE_MASTER",notificationMap);
         ArrayList<String>feeList=getRecordsToDelete("IVW_FEE_TYPE_MASTER",feeMap);
        
        
        for(int i=0;i<subjectList.size();i++){
            String pkey=subjectList.get(i);
            deleteRecordsInTheList("IVW_SUBJECT_MASTER",pkey);
            
        }
        
        for(int i=0;i<gradeList.size();i++){
            String pkey=gradeList.get(i);
            deleteRecordsInTheList("IVW_SUBJECT_GRADE_MASTER",pkey);
            
        }
        for(int i=0;i<examList.size();i++){
            String pkey=examList.get(i);
            deleteRecordsInTheList("IVW_INSTITUTE_EXAM_MASTER",pkey);
            
        }
        
        for(int i=0;i<notificationList.size();i++){
            String pkey=notificationList.get(i);
            deleteRecordsInTheList("IVW_NOTIFICATION_TYPE_MASTER",pkey);
            
        }
        
        for(int i=0;i<feeList.size();i++){
            String pkey=feeList.get(i);
            deleteRecordsInTheList("IVW_FEE_TYPE_MASTER",pkey);
            
        }
        
                   
        dbg("end of GeneralLevelConfigurationService--->fullUpdate");                
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
	   
//   private void setOperationsOfTheRecord(String tableName,Map<String,DBRecord>tableMap)throws DBValidationException,DBProcessingException,BSProcessingException{
//         
//         try{
//            dbg("inside getOperationsOfTheRecord"); 
//            RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
//            GeneralLevelConfiguration generalConfiguration =reqBody.get();
//            String l_instituteID=generalConfiguration.getInstituteID();
//            dbg("tableName"+tableName);
//            
//            switch(tableName){
//                
//                case "IVW_SUBJECT_MASTER":  
//                
//                         for(int i=0;i<generalConfiguration.subjectMaster.length;i++){
//                                String l_subjectID=generalConfiguration.subjectMaster[i].getSubjectID();
//                                String l_pkey=l_instituteID+"~"+l_subjectID;
//                               if(tableMap.containsKey(l_pkey)){
//
//                                    generalConfiguration.subjectMaster[i].setOperation("U");
//                                }else{
//
//                                    generalConfiguration.subjectMaster[i].setOperation("C");
//                                }
//                         } 
//                  break;      
//                  
//                  
//                  case "IVW_SUBJECT_GRADE_MASTER":  
//                
//                         for(int i=0;i<generalConfiguration.gradeMaster.length;i++){
//                                String l_grade=generalConfiguration.gradeMaster[i].getGrade();
//                                String l_pkey=l_instituteID+"~"+l_grade;
//                               if(tableMap.containsKey(l_pkey)){
//
//                                    generalConfiguration.gradeMaster[i].setOperation("U");
//                                }else{
//
//                                    generalConfiguration.gradeMaster[i].setOperation("C");
//                                }
//                         } 
//                  break;
//                  
//                  
//                  case "IVW_INSTITUTE_EXAM_MASTER":  
//                
//                         for(int i=0;i<generalConfiguration.examMaster.length;i++){
//                                String l_examType=generalConfiguration.examMaster[i].getExamType();
//                                String l_pkey=l_instituteID+"~"+l_examType;
//                               if(tableMap.containsKey(l_pkey)){
//
//                                    generalConfiguration.examMaster[i].setOperation("U");
//                                }else{
//
//                                    generalConfiguration.examMaster[i].setOperation("C");
//                                }
//                         } 
//                   break;
//                  
//                   
//                   case "IVW_NOTIFICATION_TYPE_MASTER":  
//                
//                         for(int i=0;i<generalConfiguration.notificationMaster.length;i++){
//                                String l_notificationType=generalConfiguration.notificationMaster[i].getNotificationType();
//                                String l_pkey=l_instituteID+"~"+l_notificationType;
//                               if(tableMap.containsKey(l_pkey)){
//
//                                    generalConfiguration.notificationMaster[i].setOperation("U");
//                                }else{
//
//                                    generalConfiguration.notificationMaster[i].setOperation("C");
//                                }
//                         } 
//                   break;
//                
//                   case "IVW_FEE_TYPE_MASTER":  
//                
//                         for(int i=0;i<generalConfiguration.feeMaster.length;i++){
//                                String l_feeType=generalConfiguration.feeMaster[i].getFeeType();
//                                String l_pkey=l_instituteID+"~"+l_feeType;
//                               if(tableMap.containsKey(l_pkey)){
//
//                                    generalConfiguration.feeMaster[i].setOperation("U");
//                                }else{
//
//                                    generalConfiguration.feeMaster[i].setOperation("C");
//                                }
//                         } 
//                   break;
//                
//            }
//            
//                       
//            
//            
//            
//            
//            
//            
//            
//             
//            dbg("end of getOperationsOfTheRecord"); 
////         }catch(DBValidationException ex){
////            throw ex;
////        }catch(DBProcessingException ex){
////            dbg(ex);
////             throw new DBProcessingException(ex.toString());
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//     
//        }
//         
//     }
//     
//     
//     private ArrayList<String>getRecordsToDelete(String tableName,Map<String,DBRecord>tableMap)throws DBValidationException,DBProcessingException,BSProcessingException{
//         
//         try{
//           
//           dbg("inside getRecordsToDelete");  
//           RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
//           GeneralLevelConfiguration generalConfiguration =reqBody.get();
//           String l_instituteID=generalConfiguration.getInstituteID();
//           ArrayList<String>recordsToDelete=new ArrayList();
////           Iterator<String>keyIterator=tableMap.keySet().iterator();
//
//           List<DBRecord>filteredRecords=tableMap.values().stream().filter(rec->rec.getRecord().get(0).trim().equals(l_instituteID)).collect(Collectors.toList());
//        
//           dbg("tableName"+tableName);
//           switch(tableName){
//           
//                 case "IVW_SUBJECT_MASTER":   
//                   
//                   for(int j=0;j<filteredRecords.size();j++){
//                       String subjectID=filteredRecords.get(j).getRecord().get(1).trim();
//                        String tablePkey=l_instituteID+"~"+subjectID;
//                        dbg("tablePkey"+tablePkey);
//                        boolean recordExistence=false;
//
//                        for(int i=0;i<generalConfiguration.subjectMaster.length;i++){
//                           String l_subjectIDRequest=generalConfiguration.subjectMaster[i].getSubjectID();
//                           String l_requestPkey=l_instituteID+"~"+l_subjectIDRequest;
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
//                   
//                   
//                case "IVW_SUBJECT_GRADE_MASTER":   
//                   
////                   while(keyIterator.hasNext()){
//                    for(int j=0;j<filteredRecords.size();j++){
//                        String grade=filteredRecords.get(j).getRecord().get(1).trim(); 
//                        String tablePkey=l_instituteID+"~"+grade;
//                        dbg("tablePkey"+tablePkey);
//                        boolean recordExistence=false;
//
//                        for(int i=0;i<generalConfiguration.gradeMaster.length;i++){
//                           String l_grade=generalConfiguration.gradeMaster[i].getGrade();
//                           String l_requestPkey=l_instituteID+"~"+l_grade;
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
//                           
//                   case "IVW_INSTITUTE_EXAM_MASTER":   
//                   
//                       
//                       for(int j=0;j<filteredRecords.size();j++){
//                            String examType=filteredRecords.get(j).getRecord().get(1).trim(); 
//                            String tablePkey=l_instituteID+"~"+examType;
//                            dbg("tablePkey"+tablePkey);
//                            boolean recordExistence=false;
//
//                           for(int i=0;i<generalConfiguration.examMaster.length;i++){
//                               String l_examType=generalConfiguration.examMaster[i].getExamType();
//                               String l_requestPkey=l_instituteID+"~"+l_examType;
//                               dbg("l_requestPkey"+l_requestPkey);
//                               if(tablePkey.equals(l_requestPkey)){
//                                   recordExistence=true;
//                               }
//                            }   
//                            if(!recordExistence){
//                                recordsToDelete.add(tablePkey);
//                            }
//
//                        }
//                       break;   
//                       
//                     case "IVW_NOTIFICATION_TYPE_MASTER":   
//                   
//                       for(int j=0;j<filteredRecords.size();j++){
//                            String notificationType=filteredRecords.get(j).getRecord().get(1).trim();
//                            String tablePkey=l_instituteID+"~"+notificationType;
//                            dbg("tablePkey"+tablePkey);
//                            boolean recordExistence=false;
//
//                           for(int i=0;i<generalConfiguration.notificationMaster.length;i++){
//                               String l_notificationType=generalConfiguration.notificationMaster[i].getNotificationType();
//                               String l_requestPkey=l_instituteID+"~"+l_notificationType;
//                               dbg("l_requestPkey"+l_requestPkey);
//                               if(tablePkey.equals(l_requestPkey)){
//                                   recordExistence=true;
//                               }
//                            }   
//                            if(!recordExistence){
//                                recordsToDelete.add(tablePkey);
//                            }
//
//                        }
//                       break;
//                       
//                       case "IVW_FEE_TYPE_MASTER":   
//                   
//                           for(int j=0;j<filteredRecords.size();j++){
//                            String feeType=filteredRecords.get(j).getRecord().get(1).trim();
//                                String tablePkey=l_instituteID+"~"+feeType;
//                                dbg("tablePkey"+tablePkey);
//                                boolean recordExistence=false;
//
//                               for(int i=0;i<generalConfiguration.feeMaster.length;i++){
//                                   String l_feeType=generalConfiguration.feeMaster[i].getFeeType();
//                                   String l_requestPkey=l_instituteID+"~"+l_feeType;
//                                   dbg("l_requestPkey"+l_requestPkey);
//                                   if(tablePkey.equals(l_requestPkey)){
//                                       recordExistence=true;
//                                   }
//                                }   
//                                if(!recordExistence){
//                                    recordsToDelete.add(tablePkey);
//                                }
//
//                            }
//                           break;
//                    }
//             
//           dbg("records to delete"+recordsToDelete.size());
//           dbg("end of getRecordsToDelete");
//           return recordsToDelete;
////        }catch(DBValidationException ex){
////            throw ex;
////        }catch(DBProcessingException ex){
////            dbg(ex);
////             throw new DBProcessingException(ex.toString());
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//     
//        }
//     }
      private void setOperationsOfTheRecord(String tableName,Map<String,ArrayList<String>>tableMap)throws DBValidationException,DBProcessingException,BSProcessingException{
         
         try{
            dbg("inside getOperationsOfTheRecord"); 
            RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
            GeneralLevelConfiguration generalConfiguration =reqBody.get();
            String l_instituteID=generalConfiguration.getInstituteID();
            dbg("tableName"+tableName);
            
            switch(tableName){
                
                case "IVW_SUBJECT_MASTER":  
                
                         for(int i=0;i<generalConfiguration.subjectMaster.length;i++){
                                String l_subjectID=generalConfiguration.subjectMaster[i].getSubjectID();
                                String l_pkey=l_instituteID+"~"+l_subjectID;
                               if(tableMap.containsKey(l_pkey)){

                                    generalConfiguration.subjectMaster[i].setOperation("U");
                                }else{

                                    generalConfiguration.subjectMaster[i].setOperation("C");
                                }
                         } 
                  break;      
                  
                  
                  case "IVW_SUBJECT_GRADE_MASTER":  
                
                         for(int i=0;i<generalConfiguration.gradeMaster.length;i++){
                                String l_grade=generalConfiguration.gradeMaster[i].getGrade();
                                String l_pkey=l_instituteID+"~"+l_grade;
                               if(tableMap.containsKey(l_pkey)){

                                    generalConfiguration.gradeMaster[i].setOperation("U");
                                }else{

                                    generalConfiguration.gradeMaster[i].setOperation("C");
                                }
                         } 
                  break;
                  
                  
                  case "IVW_INSTITUTE_EXAM_MASTER":  
                
                         for(int i=0;i<generalConfiguration.examMaster.length;i++){
                                String l_examType=generalConfiguration.examMaster[i].getExamType();
                                String l_pkey=l_instituteID+"~"+l_examType;
                               if(tableMap.containsKey(l_pkey)){

                                    generalConfiguration.examMaster[i].setOperation("U");
                                }else{

                                    generalConfiguration.examMaster[i].setOperation("C");
                                }
                         } 
                   break;
                  
                   
                   case "IVW_NOTIFICATION_TYPE_MASTER":  
                
                         for(int i=0;i<generalConfiguration.notificationMaster.length;i++){
                                String l_notificationType=generalConfiguration.notificationMaster[i].getNotificationType();
                                String l_pkey=l_instituteID+"~"+l_notificationType;
                               if(tableMap.containsKey(l_pkey)){

                                    generalConfiguration.notificationMaster[i].setOperation("U");
                                }else{

                                    generalConfiguration.notificationMaster[i].setOperation("C");
                                }
                         } 
                   break;
                
                   case "IVW_FEE_TYPE_MASTER":  
                
                         for(int i=0;i<generalConfiguration.feeMaster.length;i++){
                                String l_feeType=generalConfiguration.feeMaster[i].getFeeType();
                                String l_pkey=l_instituteID+"~"+l_feeType;
                               if(tableMap.containsKey(l_pkey)){

                                    generalConfiguration.feeMaster[i].setOperation("U");
                                }else{

                                    generalConfiguration.feeMaster[i].setOperation("C");
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
           RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
           GeneralLevelConfiguration generalConfiguration =reqBody.get();
           String l_instituteID=generalConfiguration.getInstituteID();
           ArrayList<String>recordsToDelete=new ArrayList();
//           Iterator<String>keyIterator=tableMap.keySet().iterator();

           List<ArrayList<String>>filteredRecords=tableMap.values().stream().filter(rec->rec.get(0).trim().equals(l_instituteID)).collect(Collectors.toList());
        
           dbg("tableName"+tableName);
           switch(tableName){
           
                 case "IVW_SUBJECT_MASTER":   
                   
                   for(int j=0;j<filteredRecords.size();j++){
                       String subjectID=filteredRecords.get(j).get(1).trim();
                        String tablePkey=l_instituteID+"~"+subjectID;
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                        for(int i=0;i<generalConfiguration.subjectMaster.length;i++){
                           String l_subjectIDRequest=generalConfiguration.subjectMaster[i].getSubjectID();
                           String l_requestPkey=l_instituteID+"~"+l_subjectIDRequest;
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
                   
                   
                case "IVW_SUBJECT_GRADE_MASTER":   
                   
//                   while(keyIterator.hasNext()){
                    for(int j=0;j<filteredRecords.size();j++){
                        String grade=filteredRecords.get(j).get(1).trim(); 
                        String tablePkey=l_instituteID+"~"+grade;
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                        for(int i=0;i<generalConfiguration.gradeMaster.length;i++){
                           String l_grade=generalConfiguration.gradeMaster[i].getGrade();
                           String l_requestPkey=l_instituteID+"~"+l_grade;
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
                           
                   case "IVW_INSTITUTE_EXAM_MASTER":   
                   
                       
                       for(int j=0;j<filteredRecords.size();j++){
                            String examType=filteredRecords.get(j).get(1).trim(); 
                            String tablePkey=l_instituteID+"~"+examType;
                            dbg("tablePkey"+tablePkey);
                            boolean recordExistence=false;

                           for(int i=0;i<generalConfiguration.examMaster.length;i++){
                               String l_examType=generalConfiguration.examMaster[i].getExamType();
                               String l_requestPkey=l_instituteID+"~"+l_examType;
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
                       
                     case "IVW_NOTIFICATION_TYPE_MASTER":   
                   
                       for(int j=0;j<filteredRecords.size();j++){
                            String notificationType=filteredRecords.get(j).get(1).trim();
                            String tablePkey=l_instituteID+"~"+notificationType;
                            dbg("tablePkey"+tablePkey);
                            boolean recordExistence=false;

                           for(int i=0;i<generalConfiguration.notificationMaster.length;i++){
                               String l_notificationType=generalConfiguration.notificationMaster[i].getNotificationType();
                               String l_requestPkey=l_instituteID+"~"+l_notificationType;
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
                       
                       case "IVW_FEE_TYPE_MASTER":   
                   
                           for(int j=0;j<filteredRecords.size();j++){
                            String feeType=filteredRecords.get(j).get(1).trim();
                                String tablePkey=l_instituteID+"~"+feeType;
                                dbg("tablePkey"+tablePkey);
                                boolean recordExistence=false;

                               for(int i=0;i<generalConfiguration.feeMaster.length;i++){
                                   String l_feeType=generalConfiguration.feeMaster[i].getFeeType();
                                   String l_requestPkey=l_instituteID+"~"+l_feeType;
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
             RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
             GeneralLevelConfiguration generalConfiguration =reqBody.get();
             String l_instituteID=generalConfiguration.getInstituteID();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IDBTransactionService dbts=inject.getDBTransactionService();
             String[] pkArr=pkey.split("~");
             
             
             dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",tableName,pkArr,session);
             
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
        dbg("Inside GeneralLevelConfigurationService--->delete");  
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
        GeneralLevelConfiguration generalConfiguration =reqBody.get();
        //String l_instituteID=request.getReqHeader().getInstituteID();  //Integragion change  
         String l_instituteID=generalConfiguration.getInstituteID();
        String[] l_primaryKey={l_instituteID};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE","INSTITUTE_MASTER", l_primaryKey, session);
         
        for(int i=0;i<generalConfiguration.subjectMaster.length;i++){
            
            String l_subjectID=generalConfiguration.subjectMaster[i].getSubjectID();
            String[] l_subjectPKey={l_instituteID,l_subjectID};
            
            dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SUBJECT_MASTER",l_subjectPKey,session);
        }
        
        for(int i=0;i<generalConfiguration.gradeMaster.length;i++){
            
            String l_grade=generalConfiguration.gradeMaster[i].getGrade();
            String[] l_gradePKey={l_instituteID,l_grade};
            
            dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SUBJECT_GRADE_MASTER",l_gradePKey,session);
            
        }
        
        for(int i=0;i<generalConfiguration.examMaster.length;i++){
            
            String l_examType=generalConfiguration.examMaster[i].getExamType();
            String[] l_examPKey={l_instituteID,l_examType};
            
            dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_INSTITUTE_EXAM_MASTER",l_examPKey,session);
            
        }
        
        for(int i=0;i<generalConfiguration.notificationMaster.length;i++){
            
            String l_notificationType=generalConfiguration.notificationMaster[i].getNotificationType();
            String[] l_notificationPKey={l_instituteID,l_notificationType};
            
            dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_NOTIFICATION_TYPE_MASTER",l_notificationPKey,session);
            
        }
        for(int i=0;i<generalConfiguration.feeMaster.length;i++){
            
            String l_feeType=generalConfiguration.feeMaster[i].getFeeType();
            String[] l_feePKey={l_instituteID,l_feeType};
            
            dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_FEE_TYPE_MASTER",l_feePKey,session);
            
        }           
 
         dbg("End of GeneralLevelConfigurationService--->delete");      
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
     public void  view()throws DBValidationException,DBProcessingException,BSProcessingException{
        
                
        try{
            dbg("Inside GeneralLevelConfigurationService--->tableRead");
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IPDataService pds=inject.getPdataservice();
            RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
           // String l_instituteID=request.getReqHeader().getInstituteID(); //Integration change
            GeneralLevelConfiguration generalConfiguration =reqBody.get();
            String l_instituteID=generalConfiguration.getInstituteID();
            String[] l_pkey={l_instituteID};
            
//            DBRecord instituteRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE","INSTITUTE_MASTER", l_pkey, session,dbSession);
//            Map<String,DBRecord>l_subjectMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SUBJECT_MASTER", session, dbSession);
//            Map<String,DBRecord>l_gradeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SUBJECT_GRADE_MASTER", session, dbSession);
//            Map<String,DBRecord>l_examMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_INSTITUTE_EXAM_MASTER", session, dbSession);
//            Map<String,DBRecord>l_notificationMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_NOTIFICATION_TYPE_MASTER", session, dbSession);
//            Map<String,DBRecord>l_feeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_FEE_TYPE_MASTER", session, dbSession);
//            buildBOfromDB(instituteRecord,l_subjectMap,l_gradeMap,l_examMap,l_notificationMap,l_feeMap);
           
            ArrayList<String> instituteRecord=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE","INSTITUTE_MASTER", l_pkey);
            Map<String,ArrayList<String>>l_subjectMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SUBJECT_MASTER", session, dbSession);
            Map<String,ArrayList<String>>l_gradeMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SUBJECT_GRADE_MASTER", session, dbSession);
            Map<String,ArrayList<String>>l_examMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_INSTITUTE_EXAM_MASTER", session, dbSession);
            Map<String,ArrayList<String>>l_notificationMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_NOTIFICATION_TYPE_MASTER", session, dbSession);
            Map<String,ArrayList<String>>l_feeMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_FEE_TYPE_MASTER", session, dbSession);
            buildBOfromDB(instituteRecord,l_subjectMap,l_gradeMap,l_examMap,l_notificationMap,l_feeMap);  

         
          dbg("end of  GeneralLevelConfigurationService--->tableRead");               
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
     
//      private void buildBOfromDB( DBRecord instituteRecord,Map<String,DBRecord>p_subjectMap,Map<String,DBRecord>l_gradeMap,Map<String,DBRecord>l_examMap,Map<String,DBRecord>l_notificationMap,Map<String,DBRecord>l_feeMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
//    try{
//            dbg("inside GeneralLevelConfigurationService--->buildBOfromDB");
//            
//            ArrayList<String>instituteList=instituteRecord.getRecord();
//            RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
//            GeneralLevelConfiguration generalConfiguration =reqBody.get();
//            String l_instituteID=generalConfiguration.getInstituteID();
//            if(instituteList!=null&&!instituteList.isEmpty()){
//           
//               generalConfiguration.setInstituteName(instituteList.get(1).trim());
//               generalConfiguration.setProfileImgPath(instituteList.get(2).trim());
//               request.getReqAudit().setMakerID(instituteList.get(3).trim());
//               request.getReqAudit().setCheckerID(instituteList.get(4).trim());
//               request.getReqAudit().setMakerDateStamp(instituteList.get(5).trim());
//               request.getReqAudit().setCheckerDateStamp(instituteList.get(6).trim());
//               request.getReqAudit().setRecordStatus(instituteList.get(7).trim());
//               request.getReqAudit().setAuthStatus(instituteList.get(8).trim());
//               request.getReqAudit().setVersionNumber(instituteList.get(9).trim());
//               request.getReqAudit().setMakerRemarks(instituteList.get(10).trim());
//               request.getReqAudit().setCheckerRemarks(instituteList.get(11).trim());
//            }
//            
//            if(p_subjectMap!=null&&!p_subjectMap.isEmpty()){
//                Iterator<DBRecord> valueIterator= p_subjectMap.values().iterator();
//                generalConfiguration.subjectMaster=new SubjectMaster[p_subjectMap.size()];
//                int i=0;
//                 while(valueIterator.hasNext()){
//                   DBRecord l_subjectRecords=valueIterator.next();  
//                   generalConfiguration.subjectMaster[i]=new SubjectMaster();
//                   generalConfiguration.subjectMaster[i].setSubjectID(l_subjectRecords.getRecord().get(1).trim());
//                   generalConfiguration.subjectMaster[i].setSubjectName(l_subjectRecords.getRecord().get(2).trim());
//                i++;
//               } 
//            }
//            
//            if(l_gradeMap!=null&&!l_gradeMap.isEmpty()){
//                Iterator<DBRecord> valueIterator= l_gradeMap.values().iterator();
//                generalConfiguration.gradeMaster=new GradeMaster[l_gradeMap.size()];
//                int i=0;
//                 while(valueIterator.hasNext()){
//                   DBRecord l_subjectRecords=valueIterator.next();  
//                   generalConfiguration.gradeMaster[i]=new GradeMaster();
//                   generalConfiguration.gradeMaster[i].setGrade(l_subjectRecords.getRecord().get(1).trim());
//                   generalConfiguration.gradeMaster[i].setFrom(l_subjectRecords.getRecord().get(2).trim());
//                   generalConfiguration.gradeMaster[i].setTo(l_subjectRecords.getRecord().get(3).trim());
//                i++;
//               } 
//            }
//            
//            if(l_examMap!=null&&!l_examMap.isEmpty()){
//                Iterator<DBRecord> valueIterator= l_examMap.values().iterator();
//                generalConfiguration.examMaster=new ExamMaster[l_examMap.size()];
//                int i=0;
//                 while(valueIterator.hasNext()){
//                   DBRecord l_subjectRecords=valueIterator.next();  
//                   generalConfiguration.examMaster[i]=new ExamMaster();
//                   generalConfiguration.examMaster[i].setExamType(l_subjectRecords.getRecord().get(1).trim());
//                   generalConfiguration.examMaster[i].setDescription(l_subjectRecords.getRecord().get(2).trim());
//                i++;
//               } 
//            }
//            
//            if(l_notificationMap!=null&&!l_notificationMap.isEmpty()){
//                Iterator<DBRecord> valueIterator= l_notificationMap.values().iterator();
//                generalConfiguration.notificationMaster=new NotificationMaster[l_notificationMap.size()];
//                int i=0;
//                 while(valueIterator.hasNext()){
//                   DBRecord l_subjectRecords=valueIterator.next();  
//                   generalConfiguration.notificationMaster[i]=new NotificationMaster();
//                   generalConfiguration.notificationMaster[i].setNotificationType(l_subjectRecords.getRecord().get(1).trim());
//                   generalConfiguration.notificationMaster[i].setDescription(l_subjectRecords.getRecord().get(2).trim());
//                i++;
//               } 
//            }
//            
//            if(l_feeMap!=null&&!l_feeMap.isEmpty()){
//                Iterator<DBRecord> valueIterator= l_feeMap.values().iterator();
//                generalConfiguration.feeMaster=new FeeTypeMaster[l_feeMap.size()];
//                int i=0;
//                 while(valueIterator.hasNext()){
//                   DBRecord l_subjectRecords=valueIterator.next();  
//                   generalConfiguration.feeMaster[i]=new FeeTypeMaster();
//                   generalConfiguration.feeMaster[i].setFeeType(l_subjectRecords.getRecord().get(1).trim());
//                   generalConfiguration.feeMaster[i].setFeeDescription(l_subjectRecords.getRecord().get(2).trim());
//                i++;
//               } 
//            }
//       
//        dbg("End of GeneralLevelConfigurationService--->buildBOfromDB");
//        
//        }
//        catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());   
//        }
// } 
     
     
      private void buildBOfromDB( ArrayList<String> instituteRecord,Map<String,ArrayList<String>>p_subjectMap,Map<String,ArrayList<String>>l_gradeMap,Map<String,ArrayList<String>>l_examMap,Map<String,ArrayList<String>>l_notificationMap,Map<String,ArrayList<String>>l_feeMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
            dbg("inside GeneralLevelConfigurationService--->buildBOfromDB");
            
            ArrayList<String>instituteList=instituteRecord;
            RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
            GeneralLevelConfiguration generalConfiguration =reqBody.get();
            if(instituteList!=null&&!instituteList.isEmpty()){
           
               generalConfiguration.setInstituteName(instituteList.get(1).trim());
               generalConfiguration.setProfileImgPath(instituteList.get(2).trim());
               request.getReqAudit().setMakerID(instituteList.get(3).trim());
               request.getReqAudit().setCheckerID(instituteList.get(4).trim());
               request.getReqAudit().setMakerDateStamp(instituteList.get(5).trim());
               request.getReqAudit().setCheckerDateStamp(instituteList.get(6).trim());
               request.getReqAudit().setRecordStatus(instituteList.get(7).trim());
               request.getReqAudit().setAuthStatus(instituteList.get(8).trim());
               request.getReqAudit().setVersionNumber(instituteList.get(9).trim());
               request.getReqAudit().setMakerRemarks(instituteList.get(10).trim());
               request.getReqAudit().setCheckerRemarks(instituteList.get(11).trim());
            }
            
            if(p_subjectMap!=null&&!p_subjectMap.isEmpty()){
                
                List<ArrayList<String>> filteredList=p_subjectMap.values().stream().filter(rec->rec.get(3).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.toList());
                
                
                    Iterator<ArrayList<String>> valueIterator= filteredList.iterator();
                    generalConfiguration.subjectMaster=new SubjectMaster[filteredList.size()];
                    int i=0;
                     while(valueIterator.hasNext()){
                       ArrayList<String> l_subjectRecords=valueIterator.next();  
                       generalConfiguration.subjectMaster[i]=new SubjectMaster();
                       generalConfiguration.subjectMaster[i].setSubjectID(l_subjectRecords.get(1).trim());
                       generalConfiguration.subjectMaster[i].setSubjectName(l_subjectRecords.get(2).trim());
                       generalConfiguration.subjectMaster[i].setOtherLanguageDescription(l_subjectRecords.get(4).trim());
                    i++;
                   }
                 
                 
            }
            
            if(l_gradeMap!=null&&!l_gradeMap.isEmpty()){
                
                 List<ArrayList<String>> filteredList=l_gradeMap.values().stream().filter(rec->rec.get(4).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.toList());
                
                 
                    Iterator<ArrayList<String>> valueIterator= filteredList.iterator();
                    generalConfiguration.gradeMaster=new GradeMaster[filteredList.size()];
                    int i=0;
                     while(valueIterator.hasNext()){
                       ArrayList<String> l_subjectRecords=valueIterator.next();  
                       generalConfiguration.gradeMaster[i]=new GradeMaster();
                       generalConfiguration.gradeMaster[i].setGrade(l_subjectRecords.get(1).trim());
                       generalConfiguration.gradeMaster[i].setFrom(l_subjectRecords.get(2).trim());
                       generalConfiguration.gradeMaster[i].setTo(l_subjectRecords.get(3).trim());
                    i++;
                   }
                 
            }
            
            if(l_examMap!=null&&!l_examMap.isEmpty()){
                
                 List<ArrayList<String>> filteredList=l_examMap.values().stream().filter(rec->rec.get(3).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.toList());
                
                
                
                    Iterator<ArrayList<String>> valueIterator= filteredList.iterator();
                    generalConfiguration.examMaster=new ExamMaster[filteredList.size()];
                    int i=0;
                     while(valueIterator.hasNext()){
                       ArrayList<String> l_subjectRecords=valueIterator.next();  
                       generalConfiguration.examMaster[i]=new ExamMaster();
                       generalConfiguration.examMaster[i].setExamType(l_subjectRecords.get(1).trim());
                       generalConfiguration.examMaster[i].setDescription(l_subjectRecords.get(2).trim());
                       generalConfiguration.examMaster[i].setOtherLanguageDescription(l_subjectRecords.get(4).trim());
                    i++;
                   } 
            }
            
            if(l_notificationMap!=null&&!l_notificationMap.isEmpty()){
                
                
                List<ArrayList<String>> filteredList=l_notificationMap.values().stream().filter(rec->rec.get(3).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.toList());
                
                
                
                Iterator<ArrayList<String>> valueIterator=filteredList.iterator();
                generalConfiguration.notificationMaster=new NotificationMaster[filteredList.size()];
                int i=0;
                 while(valueIterator.hasNext()){
                   ArrayList<String> l_subjectRecords=valueIterator.next();  
                   generalConfiguration.notificationMaster[i]=new NotificationMaster();
                   generalConfiguration.notificationMaster[i].setNotificationType(l_subjectRecords.get(1).trim());
                   generalConfiguration.notificationMaster[i].setDescription(l_subjectRecords.get(2).trim());
                   generalConfiguration.notificationMaster[i].setOtherLanguageDescription(l_subjectRecords.get(4).trim());
                i++;
               } 
                 
            }
            
            if(l_feeMap!=null&&!l_feeMap.isEmpty()){
                
                List<ArrayList<String>> filteredList=l_feeMap.values().stream().filter(rec->rec.get(3).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.toList());
                
                
                
                Iterator<ArrayList<String>> valueIterator= filteredList.iterator();
                generalConfiguration.feeMaster=new FeeTypeMaster[filteredList.size()];
                int i=0;
                 while(valueIterator.hasNext()){
                   ArrayList<String> l_subjectRecords=valueIterator.next();  
                   generalConfiguration.feeMaster[i]=new FeeTypeMaster();
                   generalConfiguration.feeMaster[i].setFeeType(l_subjectRecords.get(1).trim());
                   generalConfiguration.feeMaster[i].setFeeDescription(l_subjectRecords.get(2).trim());
                   generalConfiguration.feeMaster[i].setOtherLanguageDescription(l_subjectRecords.get(4).trim());
                i++;
            }
            }
        dbg("End of GeneralLevelConfigurationService--->buildBOfromDB");
        
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
            RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
            GeneralLevelConfiguration generalConfiguration =reqBody.get();
            JsonArrayBuilder subjectMaster=Json.createArrayBuilder();
            JsonArrayBuilder gradeMaster=Json.createArrayBuilder();
            JsonArrayBuilder examMaster=Json.createArrayBuilder();
            JsonArrayBuilder notificationMaster=Json.createArrayBuilder();
            JsonArrayBuilder feeMaster=Json.createArrayBuilder();
            
            
             String operation=request.getReqHeader().getOperation();
            
            if(operation.equals("Create-Default")){
                
                body=Json.createObjectBuilder().add("instituteID", generalConfiguration.getInstituteID()).build();
                
                
                
            }else{
            
            
            for(int i=0;i<generalConfiguration.subjectMaster.length;i++){
                
                 subjectMaster.add(Json.createObjectBuilder().add("subjectID", generalConfiguration.subjectMaster[i].getSubjectID())
                                                             .add("otherLangDescription", generalConfiguration.subjectMaster[i].getOtherLanguageDescription())
                                                                    .add("subjectName", generalConfiguration.subjectMaster[i].getSubjectName()));
                
            }
            
            for(int i=0;i<generalConfiguration.gradeMaster.length;i++){
                
                 gradeMaster.add(Json.createObjectBuilder().add("grade", generalConfiguration.gradeMaster[i].getGrade())
                                                           .add("from", generalConfiguration.gradeMaster[i].getFrom())
                                                           .add("to", generalConfiguration.gradeMaster[i].getTo()));
                
            }
            
            for(int i=0;i<generalConfiguration.examMaster.length;i++){
                
                 examMaster.add(Json.createObjectBuilder().add("examType", generalConfiguration.examMaster[i].getExamType())
                                                         //  .add("description", generalConfiguration.gradeMaster[i].getTo())); //Integration changes
                                                           .add("otherLangDescription", generalConfiguration.examMaster[i].getOtherLanguageDescription())
                                                            .add("description", generalConfiguration.examMaster[i].getDescription())); //Integration changes
                
            }
            
            for(int i=0;i<generalConfiguration.notificationMaster.length;i++){
                
                 notificationMaster.add(Json.createObjectBuilder().add("notificationType", generalConfiguration.notificationMaster[i].getNotificationType())
                                                              .add("otherLangDescription", generalConfiguration.notificationMaster[i].getOtherLanguageDescription())
                                                              .add("description", generalConfiguration.notificationMaster[i].getDescription()));
                
            }
            
             for(int i=0;i<generalConfiguration.feeMaster.length;i++){
                
                 feeMaster.add(Json.createObjectBuilder().add("feeType", generalConfiguration.feeMaster[i].getFeeType())
                                                          // .add("description", generalConfiguration.feeMaster[i].getFeeDescription()));
                                                           .add("otherLangDescription", generalConfiguration.feeMaster[i].getOtherLanguageDescription())
                                                           .add("feeDescription", generalConfiguration.feeMaster[i].getFeeDescription()));
                   
            }
            
             body=Json.createObjectBuilder().add("instituteID", generalConfiguration.getInstituteID())
                                            .add("instituteName", generalConfiguration.getInstituteName())
                                            .add("profileImgPath", generalConfiguration.getProfileImgPath())
                                            .add("SubjectMaster", subjectMaster)
                                            .add("GradeMaster",gradeMaster )
                                            .add("ExamMaster", examMaster)
                                            .add("NotificationMaster", notificationMaster)
                                            .add("FeeTypeMaster", feeMaster)
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
       dbg("inside GeneralLevelConfigurationService--->businessValidation"); 
       RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
       GeneralLevelConfiguration generalConfiguration =reqBody.get();
       String l_operation=request.getReqHeader().getOperation();
       String l_instituteID=generalConfiguration.getInstituteID();
       String l_userID=request.getReqHeader().getUserID();
       
       if(l_operation.equals("Create")||l_operation.equals("Create-Default")){
           
           if(!l_userID.equals("System")){
               
               status=false;
               errhandler.log_app_error("BS_VAL_014","Creation");
               throw new BSValidationException("BSValidationException");
           }
       }
       
                     
       if(!masterMandatoryValidation(errhandler)){
           status=false;
       }
       if(!(l_operation.equals("View"))&&!l_operation.equals("Create-Default")&&!l_operation.equals("Delete")){
           
           if(!detailMandatoryValidation(errhandler)) {
               
               status=false;
           } else{
           if(!detailDataValidation(errhandler,l_instituteID)){
               
               status=false;
           }
           
           }
       
       }

       dbg("GeneralLevelConfigurationService--->businessValidation--->O/P--->status"+status);
       dbg("End of GeneralLevelConfigurationService--->businessValidation");
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
        dbg("inside GeneralLevelConfigurationService--->masterMandatoryValidation");
        RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
        GeneralLevelConfiguration generalConfiguration =reqBody.get();
         
         if(generalConfiguration.getInstituteID()==null||generalConfiguration.getInstituteID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","instituteID");
         }
         
         if(!request.getReqHeader().getOperation().equals("View"))
         {
//         if(generalConfiguration.getProfileImgPath()==null||generalConfiguration.getProfileImgPath().isEmpty()) {   
//            status=false;
//            errhandler.log_app_error("BS_VAL_002","profileImgPath");
//         }
         if(generalConfiguration.getInstituteName()==null||generalConfiguration.getInstituteName().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","instituteName");
         }
         
         }
         
         dbg("GeneralLevelConfigurationService--->masterMandatoryValidation--->O/P--->status"+status);
         dbg("End of GeneralLevelConfigurationService--->masterMandatoryValidation");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
     private boolean detailMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
        boolean status=true;
         try{
             dbg("inside GeneralLevelConfigurationService--->detailMandatoryValidation");
            RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
            GeneralLevelConfiguration generalConfiguration =reqBody.get();
            IBDProperties i_db_properties=session.getCohesiveproperties();
//            String instituteID=request.getReqHeader().getInstituteID();
            IPDataService pds=inject.getPdataservice();
            String instituteID=generalConfiguration.getInstituteID();
            String operation=request.getReqHeader().getOperation();
            String language=null;
            
            if(operation.equals("Modify")){
            
            String[] pkey={instituteID};
            ArrayList<String>contractList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",pkey);
            
            language=contractList.get(12).trim();
            }
            
            
             
            
        
           if(generalConfiguration.subjectMaster==null||generalConfiguration.subjectMaster.length==0){
             status=false;
             errhandler.log_app_error("BS_VAL_002","subjectMaster");
         }else{
             
             for(int i=0;i<generalConfiguration.subjectMaster.length;i++){
             
                 if(generalConfiguration.subjectMaster[i].getSubjectID()==null||generalConfiguration.subjectMaster[i].getSubjectID().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","subjectID");
                 }
                 if(generalConfiguration.subjectMaster[i].getSubjectName()==null||generalConfiguration.subjectMaster[i].getSubjectName().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","subjectName");
                 }
             if(operation.equals("Modify")){
                 
               if(!language.equals("EN")){  
                 
                  if(generalConfiguration.subjectMaster[i].getOtherLanguageDescription()==null||generalConfiguration.subjectMaster[i].getOtherLanguageDescription().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","Other Language Description for Subject");
                 }
                  
               }
               
             } 
                  
             }
         }
         
         if(generalConfiguration.gradeMaster==null||generalConfiguration.gradeMaster.length==0){
             status=false;
             errhandler.log_app_error("BS_VAL_002","gradeMaster");
         }else{
             
             for(int i=0;i<generalConfiguration.gradeMaster.length;i++){
             
                 if(generalConfiguration.gradeMaster[i].getGrade()==null||generalConfiguration.gradeMaster[i].getGrade().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","grade");
                 }
                 if(generalConfiguration.gradeMaster[i].getFrom()==null||generalConfiguration.gradeMaster[i].getFrom().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","from");
                 }
                 if(generalConfiguration.gradeMaster[i].getTo()==null||generalConfiguration.gradeMaster[i].getTo().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","to");
                 }
             
             }
         }
        
         if(generalConfiguration.examMaster==null||generalConfiguration.examMaster.length==0){
             status=false;
             errhandler.log_app_error("BS_VAL_002","examMaster");
         }else{
             
             for(int i=0;i<generalConfiguration.examMaster.length;i++){
             
                 if(generalConfiguration.examMaster[i].getExamType()==null||generalConfiguration.examMaster[i].getExamType().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","examType");
                 }
                 if(generalConfiguration.examMaster[i].getDescription()==null||generalConfiguration.examMaster[i].getDescription().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","exam description");
                 }
                 
               if(operation.equals("Modify")){  
                 
                 if(!language.equals("EN")){
                 
                     if(generalConfiguration.examMaster[i].getOtherLanguageDescription()==null||generalConfiguration.examMaster[i].getOtherLanguageDescription().isEmpty()){
                         status=false;
                         errhandler.log_app_error("BS_VAL_002","Other Language Description for Exam");
                     }
                 
                 }
               }
             
             }
         }
         
         if(generalConfiguration.notificationMaster==null||generalConfiguration.notificationMaster.length==0){
             status=false;
             errhandler.log_app_error("BS_VAL_002","notificationMaster");
         }else{
             
             for(int i=0;i<generalConfiguration.notificationMaster.length;i++){
             
                 if(generalConfiguration.notificationMaster[i].getNotificationType()==null||generalConfiguration.notificationMaster[i].getNotificationType().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","notificationType");
                 }
                 if(generalConfiguration.notificationMaster[i].getDescription()==null||generalConfiguration.notificationMaster[i].getDescription().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","notification description");
                 }
                
                if(operation.equals("Modify")){ 
                 
                     if(!language.equals("EN")){

                         if(generalConfiguration.notificationMaster[i].getOtherLanguageDescription()==null||generalConfiguration.notificationMaster[i].getOtherLanguageDescription().isEmpty()){
                             status=false;
                             errhandler.log_app_error("BS_VAL_002","Other Language Description for Notification");
                         }

                     }
             
                }
             }
         }
         
         if(generalConfiguration.feeMaster==null||generalConfiguration.feeMaster.length==0){
             status=false;
             errhandler.log_app_error("BS_VAL_002","feeMaster");
         }else{
             
             for(int i=0;i<generalConfiguration.feeMaster.length;i++){
             
                 if(generalConfiguration.feeMaster[i].getFeeType()==null||generalConfiguration.feeMaster[i].getFeeType().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","feeType");
                 }
                 if(generalConfiguration.feeMaster[i].getFeeDescription()==null||generalConfiguration.feeMaster[i].getFeeDescription().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","fee description");
                 }
                 
                 if(operation.equals("Modify")){
                 
                     if(!language.equals("EN")){

                         if(generalConfiguration.feeMaster[i].getOtherLanguageDescription()==null||generalConfiguration.feeMaster[i].getOtherLanguageDescription().isEmpty()){
                             status=false;
                             errhandler.log_app_error("BS_VAL_002","Other Language Description for Fee");
                         }


                     }
                 }
             
             }
         }
        
        dbg("GeneralLevelConfigurationService--->detailMandatoryValidation--->O/P--->status"+status);
        dbg("End of GeneralLevelConfigurationService--->detailMandatoryValidation");
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
  
  private boolean detailDataValidation(ErrorHandler errhandler,String p_instituteID)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
        boolean status=true;
         try{
            dbg("inside GeneralLevelConfigurationService--->detailMandatoryValidation");
            RequestBody<GeneralLevelConfiguration> reqBody = request.getReqBody();
            GeneralLevelConfiguration generalConfiguration =reqBody.get();
        
             ArrayList<Integer>marks=new ArrayList();
             ArrayList<String>grades=new ArrayList();
             
             for(int i=0;i<generalConfiguration.gradeMaster.length;i++){
                 int from=0,to=0;
                 
                 String grade=generalConfiguration.gradeMaster[i].getGrade();
                 
                 if(!grades.contains(grade)){
                     
                     grades.add(grade);
                 }else{
                      status=false;
                      errhandler.log_app_error("BS_VAL_031","Grade");
                      throw new BSValidationException("BSValidationException");
                 }
                 
                 
                 
                 try{
                 
                   from=Integer.parseInt(generalConfiguration.gradeMaster[i].getFrom());
                   
                 }catch(NumberFormatException ex){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","From");
                     throw new BSValidationException("BSValidationException");
                 }  
                   
                 try{
                 
                   to=Integer.parseInt(generalConfiguration.gradeMaster[i].getTo());
                   
                   }catch(NumberFormatException ex){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","To");
                     throw new BSValidationException("BSValidationException");
                  }  
 
                   dbg("from"+from);
                   dbg("to"+to);
                   
                   for (int j = from ; j <=to; j++){
                   
                       if(!marks.contains(j)){

                           marks.add(j);

                       }else{

                           status=false;
                           errhandler.log_app_error("BS_VAL_003","Mark range");
                           throw new BSValidationException("BSValidationException");
                       }
                }
         }
             
             
         ArrayList<String>examTypes=new ArrayList();    
        
         for(int i=0;i<generalConfiguration.examMaster.length;i++){
                 
                 
                 String examType=generalConfiguration.examMaster[i].getExamType();
                 
                 if(!examTypes.contains(examType)){
                     
                     examTypes.add(examType);
                 }else{
                      status=false;
                      errhandler.log_app_error("BS_VAL_031","Exam type");
                      throw new BSValidationException("BSValidationException");
                 }
                 
                 
         }
         
         
         ArrayList<String>subjectList=new ArrayList();
         for(int i=0;i<generalConfiguration.subjectMaster.length;i++){
                 
                 
                 String subjectID=generalConfiguration.subjectMaster[i].getSubjectID();
                 
                 if(!subjectList.contains(subjectID)){
                     
                     subjectList.add(subjectID);
                 }else{
                      status=false;
                      errhandler.log_app_error("BS_VAL_031","Subject ID");
                      throw new BSValidationException("BSValidationException");
                 }
                 
         }
         
         ArrayList<String>notificationList=new ArrayList();
         for(int i=0;i<generalConfiguration.notificationMaster.length;i++){
                 
                 
                 String notificationType=generalConfiguration.notificationMaster[i].getNotificationType();
                 
                 if(!notificationList.contains(notificationType)){
                     
                     notificationList.add(notificationType);
                 }else{
                      status=false;
                      errhandler.log_app_error("BS_VAL_031","Notification type");
                      throw new BSValidationException("BSValidationException");
                 }
                 
         }
             
         ArrayList<String>feeList=new ArrayList();
         for(int i=0;i<generalConfiguration.feeMaster.length;i++){
                 
                 
                 String feeType=generalConfiguration.feeMaster[i].getFeeType();
                 
                 if(!feeList.contains(feeType)){
                     
                     feeList.add(feeType);
                 }else{
                      status=false;
                      errhandler.log_app_error("BS_VAL_031","Fee type");
                      throw new BSValidationException("BSValidationException");
                 }
                 
         }
             
        
        dbg("GeneralLevelConfigurationService--->detailMandatoryValidation--->O/P--->status"+status);
        dbg("End of GeneralLevelConfigurationService--->detailMandatoryValidation");
        }catch(BSValidationException ex){
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
        
         
         dbg("inside BusinessService--->getExistingAudit") ;
        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IPDataService pds=inject.getPdataservice();
        String l_operation=request.getReqHeader().getOperation();
        //Integration fix starts
         if((l_operation.equals("Create")) ||(l_operation.equals("View")))
         {
            return null;
         } 
        //Integration fix ends
        
        String l_service=request.getReqHeader().getService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_versioNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
        if(!(l_operation.equals("Create")||l_operation.equals("Create-Default"))){
             
            if(l_operation.equals("AutoAuth")&&l_versioNumber.equals("1")){
                
                return null;
            }else{  
                
               dbg("inside business Service--->getExistingAudit--->Service--->GeneralLevelConfiguration");  
               RequestBody<GeneralLevelConfiguration> generalConfigurationBody = request.getReqBody();
               GeneralLevelConfiguration generalConfiguration =generalConfigurationBody.get();
               l_instituteID=generalConfiguration.getInstituteID();//Integration fix
               String[] l_pkey={l_instituteID};
            
//              DBRecord instituteRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE","INSTITUTE_MASTER", l_pkey, session,dbSession);

               ArrayList<String> instituteRecord=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE","INSTITUTE_MASTER", l_pkey);

               exAudit.setAuthStatus(instituteRecord.get(8).trim());
               exAudit.setMakerID(instituteRecord.get(3).trim());
               exAudit.setRecordStatus(instituteRecord.get(7).trim());
               exAudit.setVersionNumber(Integer.parseInt(instituteRecord.get(9).trim()));


 
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