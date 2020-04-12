/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.softSkillconfiguration;

import com.ibd.businessViews.ISoftSkillConfigurationService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.institute.generallevelconfiguration.ExamMaster;
import com.ibd.cohesive.app.business.institute.generallevelconfiguration.FeeTypeMaster;
import com.ibd.cohesive.app.business.institute.generallevelconfiguration.NotificationMaster;
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
@Remote(ISoftSkillConfigurationService.class)
@Stateless
public class SoftSkillConfigurationService implements ISoftSkillConfigurationService  {
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public SoftSkillConfigurationService(){
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
       dbg("inside SoftSkillConfigurationService--->processing");
       
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
       
       dbg("SoftSkillConfigurationService--->Processing--->I/P--->requestJson"+requestJson.toString());
     
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,requestJson);
       String l_operation=request.getReqHeader().getOperation();
       String l_userID=request.getReqHeader().getUserID();
       bs.requestlogging(request,requestJson, inject,session,dbSession);
       buildBOfromReq(requestJson);  
       
        if(!l_operation.equals("Create-Default")){
       
           RequestBody<SoftSkillConfiguration> reqBody = request.getReqBody();
           SoftSkillConfiguration softSkillConfiguration =reqBody.get();
           l_lockKey=softSkillConfiguration.getInstituteID();
           if(!businessLock.getBusinessLock(request, l_lockKey, session)){
               l_validation_status=false;
               throw new BSValidationException("BSValidationException");
            }
        }
       
       BusinessEJB<ISoftSkillConfigurationService>softSkillConfigurationEJB=new BusinessEJB();
       softSkillConfigurationEJB.set(this);
       exAudit=bs.getExistingAudit(softSkillConfigurationEJB);
       
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

        
        bs.businessServiceProcssing(request, exAudit, inject,softSkillConfigurationEJB);
       
       
       
       

         if(l_session_created_now){
             jsonResponse= bs.buildSuccessResponse(requestJson, request, inject, session,softSkillConfigurationEJB) ;
             tc.commit(session,dbSession);
             dbg("commit is called in  service");
        }
       dbg("SoftSkillConfigurationService--->Processing--->O/P--->jsonResponse"+jsonResponse);     
       dbg("End of softSkillConfigurationService--->processing");     
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
       dbg("inside SoftSkillConfigurationService--->buildBOfromReq"); 
       dbg("SoftSkillConfigurationService--->buildBOfromReq--->I/P--->p_request"+p_request.toString()); 
       RequestBody<SoftSkillConfiguration> reqBody = new RequestBody<SoftSkillConfiguration>(); 
       SoftSkillConfiguration softSkillConfiguration = new SoftSkillConfiguration();
       String l_operation=request.getReqHeader().getOperation();
       
       if(!l_operation.equals("Create-Default")){
       
       JsonObject l_body=p_request.getJsonObject("body");
       softSkillConfiguration.setInstituteID(l_body.getString("instituteID"));
        softSkillConfiguration.setInstituteName(l_body.getString("instituteName"));
       if(!(l_operation.equals("View"))){
            JsonArray l_skillArray=l_body.getJsonArray("SkillMaster");
            JsonArray l_gradeArray=l_body.getJsonArray("GradeMaster");
            
            softSkillConfiguration.skillMaster=new SkillMaster[l_skillArray.size()];
            
            for(int i=0;i<l_skillArray.size();i++){
                JsonObject l_skillObject=l_skillArray.getJsonObject(i);
                softSkillConfiguration.skillMaster[i]=new SkillMaster();
                softSkillConfiguration.skillMaster[i].setSkillID(l_skillObject.getString("skillID"));
                softSkillConfiguration.skillMaster[i].setSkillName(l_skillObject.getString("skillName"));
                
            }
            softSkillConfiguration.gradeMaster=new GradeMaster[l_gradeArray.size()];
            
            for(int i=0;i<l_gradeArray.size();i++){
                JsonObject l_gradeObject=l_gradeArray.getJsonObject(i);
                softSkillConfiguration.gradeMaster[i]=new GradeMaster();
                softSkillConfiguration.gradeMaster[i].setGrade(l_gradeObject.getString("grade"));
                softSkillConfiguration.gradeMaster[i].setGradeDescription(l_gradeObject.getString("gradeDescription"));
            }
            
           
            
        }
       }
       reqBody.set(softSkillConfiguration);
      request.setReqBody(reqBody);
      dbg("End of SoftSkillConfigurationService--->buildBOfromReq");
      }
        catch(Exception ex){
             dbg(ex);
            throw new BSProcessingException("BodyParsingException"+ex.toString());
        }
   }
    
  
     public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
        try{
        dbg("Inside SoftSkillConfigurationService--->create"); 
        RequestBody<SoftSkillConfiguration> reqBody = request.getReqBody();
        SoftSkillConfiguration softSkillConfiguration =reqBody.get();
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
        
        String l_instituteID=softSkillConfiguration.getInstituteID();
        String l_instituteName=softSkillConfiguration.getInstituteName();
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE",325,l_instituteID,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
       
        for(int i=0;i<softSkillConfiguration.skillMaster.length;i++){
            
            String l_skillID=softSkillConfiguration.skillMaster[i].getSkillID();
            String l_skillName=softSkillConfiguration.skillMaster[i].getSkillName();
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",326,l_instituteID,l_skillID,l_skillName,l_versionNumber);
        }
        
        for(int i=0;i<softSkillConfiguration.gradeMaster.length;i++){
            
            String l_grade=softSkillConfiguration.gradeMaster[i].getGrade();
            String l_gradeDescription=softSkillConfiguration.gradeMaster[i].getGradeDescription();
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",327,l_instituteID,l_grade,l_gradeDescription,l_versionNumber);
            
        }
        
        

        dbg("End of SoftSkillConfigurationService--->create");
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
            dbg("Inside SoftSkillConfigurationService--->update"); 
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            //String l_instituteID=request.getReqHeader().getInstituteID(); //Integration change
             RequestBody<SoftSkillConfiguration> softSkillConfigurationBody = request.getReqBody();
               SoftSkillConfiguration softSkillConfiguration =softSkillConfigurationBody.get();
              String l_instituteID=softSkillConfiguration.getInstituteID();//Integration fix
              
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
            Map<String,String>  l_column_to_update=new HashMap();
            l_column_to_update.put("3", l_checkerID);
            l_column_to_update.put("5", l_checkerDateStamp);
            l_column_to_update.put("7", l_authStatus);
            l_column_to_update.put("10", l_checkerRemarks);
             
             String[] l_primaryKey={l_instituteID};
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE","IVW_SOFT_SKILL_CONFIGURATION_MASTER", l_primaryKey, l_column_to_update, session);

             dbg("End of SoftSkillConfigurationService--->update");          
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
        dbg("Inside SoftSkillConfigurationService--->fullUpdate");
        IDBTransactionService dbts=inject.getDBTransactionService();
        IPDataService pds=inject.getPdataservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<SoftSkillConfiguration> reqBody = request.getReqBody();
        SoftSkillConfiguration softSkillConfiguration =reqBody.get();
       // String l_instituteID=request.getReqHeader().getInstituteID();  //Integration change
        String l_instituteID=softSkillConfiguration.getInstituteID();//Integration fix
            
       
	String makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        
        l_column_to_update= new HashMap();
		l_column_to_update.put("4", makerDateStamp);
        l_column_to_update.put("9", makerRemarks);
        
        String[] l_primaryKey={l_instituteID};
        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE","IVW_SOFT_SKILL_CONFIGURATION_MASTER", l_primaryKey, l_column_to_update, session);
//        IDBReadBufferService readBuffer = inject.getDBReadBufferService();
        Map<String,ArrayList<String>>skillMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SKILL_MASTER", session, dbSession);
        Map<String,ArrayList<String>>gradeMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SKILL_GRADE_MASTER", session, dbSession);   
        setOperationsOfTheRecord("IVW_SKILL_MASTER",skillMap);      
        setOperationsOfTheRecord("IVW_SKILL_GRADE_MASTER",gradeMap);
                        
                        
                        
        for(int i=0;i<softSkillConfiguration.skillMaster.length;i++){
            
            String l_skillID=softSkillConfiguration.skillMaster[i].getSkillID();
            String l_skillName=softSkillConfiguration.skillMaster[i].getSkillName();
            
            if(softSkillConfiguration.skillMaster[i].getOperation().equals("U")){
                    l_column_to_update= new HashMap();
                    l_column_to_update.put("3", l_skillName);
                    String[] l_skillPKey={l_instituteID,l_skillID};
                    dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SKILL_MASTER",l_skillPKey,l_column_to_update,session);
            }else{
                   dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",326,l_instituteID,l_skillID,l_skillName,l_versionNumber);
            }

        }
        
        for(int i=0;i<softSkillConfiguration.gradeMaster.length;i++){
            
            String l_grade=softSkillConfiguration.gradeMaster[i].getGrade();
            String l_gradeDescription=softSkillConfiguration.gradeMaster[i].getGradeDescription();
            
            if(softSkillConfiguration.gradeMaster[i].getOperation().equals("U")){
            
                    l_column_to_update= new HashMap();
                    l_column_to_update.put("3", l_gradeDescription);
                    String[] l_gradePKey={l_instituteID,l_grade};
                    dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SKILL_GRADE_MASTER",l_gradePKey,l_column_to_update,session);
            }else{
                
                   dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",327,l_instituteID,l_grade,l_gradeDescription,l_versionNumber);
            }
        }
        
        
         ArrayList<String>skillList=getRecordsToDelete("IVW_SKILL_MASTER",skillMap);      
         ArrayList<String>gradeList=getRecordsToDelete("IVW_SKILL_GRADE_MASTER",gradeMap);
        
        
        for(int i=0;i<skillList.size();i++){
            String pkey=skillList.get(i);
            deleteRecordsInTheList("IVW_SKILL_MASTER",pkey);
            
        }
        
        for(int i=0;i<gradeList.size();i++){
            String pkey=gradeList.get(i);
            deleteRecordsInTheList("IVW_SKILL_GRADE_MASTER",pkey);
            
        }
        
        
                   
        dbg("end of SoftSkillConfigurationService--->fullUpdate");                
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
            RequestBody<SoftSkillConfiguration> reqBody = request.getReqBody();
            SoftSkillConfiguration softSkillConfiguration =reqBody.get();
            String l_instituteID=softSkillConfiguration.getInstituteID();
            dbg("tableName"+tableName);
            
            switch(tableName){
                
                case "IVW_SKILL_MASTER":  
                
                         for(int i=0;i<softSkillConfiguration.skillMaster.length;i++){
                                String l_skillID=softSkillConfiguration.skillMaster[i].getSkillID();
                                String l_pkey=l_instituteID+"~"+l_skillID;
                               if(tableMap.containsKey(l_pkey)){

                                    softSkillConfiguration.skillMaster[i].setOperation("U");
                                }else{

                                    softSkillConfiguration.skillMaster[i].setOperation("C");
                                }
                         } 
                  break;      
                  
                  
                  case "IVW_SKILL_GRADE_MASTER":  
                
                         for(int i=0;i<softSkillConfiguration.gradeMaster.length;i++){
                                String l_grade=softSkillConfiguration.gradeMaster[i].getGrade();
                                String l_pkey=l_instituteID+"~"+l_grade;
                               if(tableMap.containsKey(l_pkey)){

                                    softSkillConfiguration.gradeMaster[i].setOperation("U");
                                }else{

                                    softSkillConfiguration.gradeMaster[i].setOperation("C");
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
           RequestBody<SoftSkillConfiguration> reqBody = request.getReqBody();
           SoftSkillConfiguration softSkillConfiguration =reqBody.get();
           String l_instituteID=softSkillConfiguration.getInstituteID();
           ArrayList<String>recordsToDelete=new ArrayList();
//           Iterator<String>keyIterator=tableMap.keySet().iterator();

           List<ArrayList<String>>filteredRecords=tableMap.values().stream().filter(rec->rec.get(0).trim().equals(l_instituteID)).collect(Collectors.toList());
        
           dbg("tableName"+tableName);
           switch(tableName){
           
                 case "IVW_SKILL_MASTER":   
                   
                   for(int j=0;j<filteredRecords.size();j++){
                       String skillID=filteredRecords.get(j).get(1).trim();
                        String tablePkey=l_instituteID+"~"+skillID;
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                        for(int i=0;i<softSkillConfiguration.skillMaster.length;i++){
                           String l_skillIDRequest=softSkillConfiguration.skillMaster[i].getSkillID();
                           String l_requestPkey=l_instituteID+"~"+l_skillIDRequest;
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
                   
                   
                case "IVW_SKILL_GRADE_MASTER":   
                   
//                   while(keyIterator.hasNext()){
                    for(int j=0;j<filteredRecords.size();j++){
                        String grade=filteredRecords.get(j).get(1).trim(); 
                        String tablePkey=l_instituteID+"~"+grade;
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                        for(int i=0;i<softSkillConfiguration.gradeMaster.length;i++){
                           String l_grade=softSkillConfiguration.gradeMaster[i].getGrade();
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
             RequestBody<SoftSkillConfiguration> reqBody = request.getReqBody();
             SoftSkillConfiguration softSkillConfiguration =reqBody.get();
             String l_instituteID=softSkillConfiguration.getInstituteID();
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
        dbg("Inside SoftSkillConfigurationService--->delete");  
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<SoftSkillConfiguration> reqBody = request.getReqBody();
        SoftSkillConfiguration softSkillConfiguration =reqBody.get();
        //String l_instituteID=request.getReqHeader().getInstituteID();  //Integragion change  
         String l_instituteID=softSkillConfiguration.getInstituteID();
        String[] l_primaryKey={l_instituteID};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE","IVW_SOFT_SKILL_CONFIGURATION_MASTER", l_primaryKey, session);
         
        for(int i=0;i<softSkillConfiguration.skillMaster.length;i++){
            
            String l_skillID=softSkillConfiguration.skillMaster[i].getSkillID();
            String[] l_skillPKey={l_instituteID,l_skillID};
            
            dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SKILL_MASTER",l_skillPKey,session);
        }
        
        for(int i=0;i<softSkillConfiguration.gradeMaster.length;i++){
            
            String l_grade=softSkillConfiguration.gradeMaster[i].getGrade();
            String[] l_gradePKey={l_instituteID,l_grade};
            
            dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SKILL_GRADE_MASTER",l_gradePKey,session);
            
        }
        
         
 
         dbg("End of SoftSkillConfigurationService--->delete");      
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
            dbg("Inside SoftSkillConfigurationService--->tableRead");
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IPDataService pds=inject.getPdataservice();
            RequestBody<SoftSkillConfiguration> reqBody = request.getReqBody();
           // String l_instituteID=request.getReqHeader().getInstituteID(); //Integration change
            SoftSkillConfiguration softSkillConfiguration =reqBody.get();
            String l_instituteID=softSkillConfiguration.getInstituteID();
            String[] l_pkey={l_instituteID};
            
//            DBRecord instituteRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE","IVW_SOFT_SKILL_CONFIGURATION_MASTER", l_pkey, session,dbSession);
//            Map<String,DBRecord>l_skillMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SKILL_MASTER", session, dbSession);
//            Map<String,DBRecord>l_gradeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SKILL_GRADE_MASTER", session, dbSession);
//            Map<String,DBRecord>l_examMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_INSTITUTE_EXAM_MASTER", session, dbSession);
//            Map<String,DBRecord>l_notificationMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_NOTIFICATION_TYPE_MASTER", session, dbSession);
//            Map<String,DBRecord>l_feeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_FEE_TYPE_MASTER", session, dbSession);
//            buildBOfromDB(instituteRecord,l_skillMap,l_gradeMap,l_examMap,l_notificationMap,l_feeMap);
           
            ArrayList<String> instituteRecord=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE","IVW_SOFT_SKILL_CONFIGURATION_MASTER", l_pkey);
            Map<String,ArrayList<String>>l_skillMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SKILL_MASTER", session, dbSession);
            Map<String,ArrayList<String>>l_gradeMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_SKILL_GRADE_MASTER", session, dbSession);
            buildBOfromDB(instituteRecord,l_skillMap,l_gradeMap);  

         
          dbg("end of  SoftSkillConfigurationService--->tableRead");               
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
     
     
     
      private void buildBOfromDB( ArrayList<String> instituteRecord,Map<String,ArrayList<String>>p_skillMap,Map<String,ArrayList<String>>l_gradeMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
            dbg("inside SoftSkillConfigurationService--->buildBOfromDB");
            
            ArrayList<String>instituteList=instituteRecord;
            RequestBody<SoftSkillConfiguration> reqBody = request.getReqBody();
            SoftSkillConfiguration softSkillConfiguration =reqBody.get();
            if(instituteList!=null&&!instituteList.isEmpty()){
           
               request.getReqAudit().setMakerID(instituteList.get(1).trim());
               request.getReqAudit().setCheckerID(instituteList.get(2).trim());
               request.getReqAudit().setMakerDateStamp(instituteList.get(3).trim());
               request.getReqAudit().setCheckerDateStamp(instituteList.get(4).trim());
               request.getReqAudit().setRecordStatus(instituteList.get(5).trim());
               request.getReqAudit().setAuthStatus(instituteList.get(6).trim());
               request.getReqAudit().setVersionNumber(instituteList.get(7).trim());
               request.getReqAudit().setMakerRemarks(instituteList.get(8).trim());
               request.getReqAudit().setCheckerRemarks(instituteList.get(9).trim());
            }
            
            if(p_skillMap!=null&&!p_skillMap.isEmpty()){
                
                List<ArrayList<String>> filteredList=p_skillMap.values().stream().filter(rec->rec.get(3).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.toList());
                
                
                    Iterator<ArrayList<String>> valueIterator= filteredList.iterator();
                    softSkillConfiguration.skillMaster=new SkillMaster[filteredList.size()];
                    int i=0;
                     while(valueIterator.hasNext()){
                       ArrayList<String> l_skillRecords=valueIterator.next();  
                       softSkillConfiguration.skillMaster[i]=new SkillMaster();
                       softSkillConfiguration.skillMaster[i].setSkillID(l_skillRecords.get(1).trim());
                       softSkillConfiguration.skillMaster[i].setSkillName(l_skillRecords.get(2).trim());
                    i++;
                   }
                 
                 
            }
            
            if(l_gradeMap!=null&&!l_gradeMap.isEmpty()){
                
                 List<ArrayList<String>> filteredList=l_gradeMap.values().stream().filter(rec->rec.get(3).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.toList());
                
                 
                    Iterator<ArrayList<String>> valueIterator= filteredList.iterator();
                    softSkillConfiguration.gradeMaster=new GradeMaster[filteredList.size()];
                    int i=0;
                     while(valueIterator.hasNext()){
                       ArrayList<String> l_skillRecords=valueIterator.next();  
                       softSkillConfiguration.gradeMaster[i]=new GradeMaster();
                       softSkillConfiguration.gradeMaster[i].setGrade(l_skillRecords.get(1).trim());
                       softSkillConfiguration.gradeMaster[i].setGradeDescription(l_skillRecords.get(2).trim());
                    i++;
                   }
                 
            }
            
            
        dbg("End of SoftSkillConfigurationService--->buildBOfromDB");
        
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
            RequestBody<SoftSkillConfiguration> reqBody = request.getReqBody();
            SoftSkillConfiguration softSkillConfiguration =reqBody.get();
            JsonArrayBuilder skillMaster=Json.createArrayBuilder();
            JsonArrayBuilder gradeMaster=Json.createArrayBuilder();
            
            
             String operation=request.getReqHeader().getOperation();

            
            
            for(int i=0;i<softSkillConfiguration.skillMaster.length;i++){
                
                 skillMaster.add(Json.createObjectBuilder().add("skillID", softSkillConfiguration.skillMaster[i].getSkillID())
                                                                    .add("skillName", softSkillConfiguration.skillMaster[i].getSkillName()));
                
            }
            
            for(int i=0;i<softSkillConfiguration.gradeMaster.length;i++){
                
                 gradeMaster.add(Json.createObjectBuilder().add("grade", softSkillConfiguration.gradeMaster[i].getGrade())
                                                           .add("gradeDescription", softSkillConfiguration.gradeMaster[i].getGradeDescription()));
                
            }
 
            
           
            
             body=Json.createObjectBuilder().add("instituteID", softSkillConfiguration.getInstituteID())
                                            .add("instituteName", softSkillConfiguration.getInstituteName())
                                            .add("SkillMaster", skillMaster)
                                            .add("GradeMaster",gradeMaster )
                                            .build(); 
                         dbg("end of buildJsonResFromBO");

          }  catch (Exception ex) {
               dbg(ex);
               throw new BSProcessingException("Exception" + ex.toString());
          }
         
         return body;
         
     }
     
     private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
       try{
       dbg("inside SoftSkillConfigurationService--->businessValidation"); 
       RequestBody<SoftSkillConfiguration> reqBody = request.getReqBody();
       SoftSkillConfiguration softSkillConfiguration =reqBody.get();
       String l_operation=request.getReqHeader().getOperation();
       String l_instituteID=softSkillConfiguration.getInstituteID();
       

       
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

       dbg("SoftSkillConfigurationService--->businessValidation--->O/P--->status"+status);
       dbg("End of SoftSkillConfigurationService--->businessValidation");
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
        dbg("inside SoftSkillConfigurationService--->masterMandatoryValidation");
        RequestBody<SoftSkillConfiguration> reqBody = request.getReqBody();
        SoftSkillConfiguration softSkillConfiguration =reqBody.get();
         
         if(softSkillConfiguration.getInstituteID()==null||softSkillConfiguration.getInstituteID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","instituteID");
         }
         
        
         
         dbg("SoftSkillConfigurationService--->masterMandatoryValidation--->O/P--->status"+status);
         dbg("End of SoftSkillConfigurationService--->masterMandatoryValidation");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
     private boolean detailMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
        boolean status=true;
         try{
             dbg("inside SoftSkillConfigurationService--->detailMandatoryValidation");
            RequestBody<SoftSkillConfiguration> reqBody = request.getReqBody();
            SoftSkillConfiguration softSkillConfiguration =reqBody.get();
        
           if(softSkillConfiguration.skillMaster==null||softSkillConfiguration.skillMaster.length==0){
             status=false;
             errhandler.log_app_error("BS_VAL_002","skillMaster");
         }else{
             
             for(int i=0;i<softSkillConfiguration.skillMaster.length;i++){
             
                 if(softSkillConfiguration.skillMaster[i].getSkillID()==null||softSkillConfiguration.skillMaster[i].getSkillID().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","skillID");
                 }
                 if(softSkillConfiguration.skillMaster[i].getSkillName()==null||softSkillConfiguration.skillMaster[i].getSkillName().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","skillName");
                 }
             
             }
         }
         
         if(softSkillConfiguration.gradeMaster==null||softSkillConfiguration.gradeMaster.length==0){
             status=false;
             errhandler.log_app_error("BS_VAL_002","gradeMaster");
         }else{
             
             for(int i=0;i<softSkillConfiguration.gradeMaster.length;i++){
             
                 if(softSkillConfiguration.gradeMaster[i].getGrade()==null||softSkillConfiguration.gradeMaster[i].getGrade().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","grade");
                 }

                 if(softSkillConfiguration.gradeMaster[i].getGradeDescription()==null||softSkillConfiguration.gradeMaster[i].getGradeDescription().isEmpty()){
                     status=false;
                     errhandler.log_app_error("BS_VAL_002","Grade description");
                 }
             
             }
         }
        
       
         
         
         
         
        
        dbg("SoftSkillConfigurationService--->detailMandatoryValidation--->O/P--->status"+status);
        dbg("End of SoftSkillConfigurationService--->detailMandatoryValidation");
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
  
  private boolean detailDataValidation(ErrorHandler errhandler,String p_instituteID)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
        boolean status=true;
         try{
            dbg("inside SoftSkillConfigurationService--->detailMandatoryValidation");
            RequestBody<SoftSkillConfiguration> reqBody = request.getReqBody();
            SoftSkillConfiguration softSkillConfiguration =reqBody.get();
        
             ArrayList<String>grades=new ArrayList();
             
             for(int i=0;i<softSkillConfiguration.gradeMaster.length;i++){
                 
                 String grade=softSkillConfiguration.gradeMaster[i].getGrade();
                 
                 if(!grades.contains(grade)){
                     
                     grades.add(grade);
                 }else{
                      status=false;
                      errhandler.log_app_error("BS_VAL_031","Grade");
                      throw new BSValidationException("BSValidationException");
                 }
                 
                 
         }
             
          
         
         
         ArrayList<String>skillList=new ArrayList();
         for(int i=0;i<softSkillConfiguration.skillMaster.length;i++){
                 
                 
                 String skillID=softSkillConfiguration.skillMaster[i].getSkillID();
                 
                 if(!skillList.contains(skillID)){
                     
                     skillList.add(skillID);
                 }else{
                      status=false;
                      errhandler.log_app_error("BS_VAL_031","Skill ID");
                      throw new BSValidationException("BSValidationException");
                 }
                 
         }
         
       
             
        
        dbg("SoftSkillConfigurationService--->detailMandatoryValidation--->O/P--->status"+status);
        dbg("End of SoftSkillConfigurationService--->detailMandatoryValidation");
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
                
               dbg("inside business Service--->getExistingAudit--->Service--->SoftSkillConfiguration");  
               RequestBody<SoftSkillConfiguration> softSkillConfigurationBody = request.getReqBody();
               SoftSkillConfiguration softSkillConfiguration =softSkillConfigurationBody.get();
               l_instituteID=softSkillConfiguration.getInstituteID();//Integration fix
               String[] l_pkey={l_instituteID};
            
//              DBRecord instituteRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE","IVW_SOFT_SKILL_CONFIGURATION_MASTER", l_pkey, session,dbSession);

               ArrayList<String> instituteRecord=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE","IVW_SOFT_SKILL_CONFIGURATION_MASTER", l_pkey);

               exAudit.setAuthStatus(instituteRecord.get(6).trim());
               exAudit.setMakerID(instituteRecord.get(1).trim());
               exAudit.setRecordStatus(instituteRecord.get(5).trim());
               exAudit.setVersionNumber(Integer.parseInt(instituteRecord.get(7).trim()));


 
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
