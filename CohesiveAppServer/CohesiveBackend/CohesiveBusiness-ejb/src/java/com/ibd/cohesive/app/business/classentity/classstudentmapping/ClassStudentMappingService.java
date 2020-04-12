/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.classstudentmapping;

import com.ibd.businessViews.IClassStudentMappingService;
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
//@Local(IClassStudentMappingService.class)
@Remote(IClassStudentMappingService.class)
@Stateless
public class ClassStudentMappingService implements IClassStudentMappingService{
     AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public ClassStudentMappingService(){
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
       dbg("inside ClassStudentMappingService--->processing");
       dbg("ClassStudentMappingService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<ClassStudentMapping> reqBody = request.getReqBody();
       ClassStudentMapping classStudentMapping =reqBody.get();
       String l_standard=classStudentMapping.getStandard();
       String l_section=classStudentMapping.getSection();
       String l_studentID=classStudentMapping.getStudentID();
       l_lockKey=l_standard+","+l_section+","+l_studentID;
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<IClassStudentMappingService>classStudentMappingEJB=new BusinessEJB();
       classStudentMappingEJB.set(this);
      
       exAudit=bs.getExistingAudit(classStudentMappingEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,classStudentMappingEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,classStudentMappingEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student ");
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
                //if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                 clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes  
                //BSProcessingException ex=new BSProcessingException("response contains special characters");
                  // dbg(ex);
                   //session.clearSessionObject();
                   //dbSession.clearSessionObject();
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
    
   private  void buildBOfromReq(JsonObject p_request)throws BSProcessingException,DBProcessingException{
      ClassStudentMapping studentMapping=new ClassStudentMapping();
      RequestBody<ClassStudentMapping> reqBody = new RequestBody<ClassStudentMapping>(); 
           
      try{
      dbg("inside class exam schedule buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      studentMapping.setStandard(l_body.getString("standard"));
      studentMapping.setSection(l_body.getString("section"));
      studentMapping.setStudentID(l_body.getString("studentID"));
      
      
      reqBody.set(studentMapping);
      request.setReqBody(reqBody);
      
      dbg("End of build bo from request");
     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
    
  public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
        try{
        dbg("Inside ClassStudentMappingService--->create"); 
        RequestBody<ClassStudentMapping> reqBody = request.getReqBody();
        ClassStudentMapping studentMapping =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID(); 
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
        String l_standard=studentMapping.getStandard();
        String l_section=studentMapping.getSection();  
        String l_studentID=studentMapping.getStudentID();
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS",30,l_standard,l_section,l_studentID,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
       
        

        dbg("End of ClassStudentMappingService--->create");
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
            dbg("Inside ClassStudentMappingService--->update"); 
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            String l_instituteID=request.getReqHeader().getInstituteID();
            RequestBody<ClassStudentMapping> reqBody = request.getReqBody();
            ClassStudentMapping studentMapping =reqBody.get();
            String l_standard=studentMapping.getStandard();
            String l_section=studentMapping.getSection();  
            String l_studentID=studentMapping.getStudentID();
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
            Map<String,String>  l_column_to_update=new HashMap();
            l_column_to_update.put("5", l_checkerID);
            l_column_to_update.put("7", l_checkerDateStamp);
            l_column_to_update.put("9", l_authStatus);
            l_column_to_update.put("12", l_checkerRemarks);
             
             String[] l_primaryKey={l_standard,l_section,l_studentID};
             
              dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS","CLASS_STUDENT_MAPPING",l_primaryKey,l_column_to_update,session);

             dbg("End of ClassStudentMappingService--->update");          
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
        dbg("Inside ClassStudentMappingService--->fullUpdate");
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassStudentMapping> reqBody = request.getReqBody();
        ClassStudentMapping studentMapping =reqBody.get();
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
        
        String l_standard=studentMapping.getStandard();
        String l_section=studentMapping.getSection();  
        String l_studentID=studentMapping.getStudentID();
        
        l_column_to_update= new HashMap();
        l_column_to_update.put("1", l_standard);
        l_column_to_update.put("2", l_section);
        l_column_to_update.put("3", l_studentID);
        l_column_to_update.put("4", l_makerID);
        l_column_to_update.put("5", l_checkerID);
        l_column_to_update.put("6", l_makerDateStamp);
        l_column_to_update.put("7", l_checkerDateStamp);
        l_column_to_update.put("8", l_recordStatus);
        l_column_to_update.put("9", l_authStatus);
        l_column_to_update.put("10", l_versionNumber);
        l_column_to_update.put("11", l_makerRemarks);
        l_column_to_update.put("12", l_checkerRemarks);
        String[] l_primaryKey={l_standard,l_section,l_studentID};
        
                       dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS","CLASS_STUDENT_MAPPING",l_primaryKey,l_column_to_update,session);
         
            
                       

        dbg("end of ClassStudentMappingService--->fullUpdate");                
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
        dbg("Inside ClassStudentMappingService--->delete");  
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassStudentMapping> reqBody = request.getReqBody();
        ClassStudentMapping studentMapping =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();    
         String l_standard=studentMapping.getStandard();
        String l_section=studentMapping.getSection();  
        String l_studentID=studentMapping.getStudentID();
        
        String[] l_primaryKey={l_standard,l_section,l_studentID};
        
        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS","CLASS_STUDENT_MAPPING",l_primaryKey,session);
            
       
         dbg("End of ClassStudentMappingService--->delete");      
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
        RequestBody<ClassStudentMapping> reqBody = request.getReqBody();
        ClassStudentMapping studentMapping =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();   
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_standard=studentMapping.getStandard();
        String l_section=studentMapping.getSection();  
        String l_studentID=studentMapping.getStudentID();
        String[] l_pkey={l_standard,l_section,l_studentID};
        DBRecord l_studentMappingRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS","CLASS_STUDENT_MAPPING", l_pkey, session, dbSession);
            
         
           
        
        buildBOfromDB(l_studentMappingRecord);     
        
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
    
     private void buildBOfromDB(DBRecord p_studentStudentMappingRecord)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<ClassStudentMapping> reqBody = request.getReqBody();
           ClassStudentMapping studentMapping =reqBody.get();
           String l_studentID=studentMapping.getStudentID();
           ArrayList<String>l_studentStudentMappingList= p_studentStudentMappingRecord.getRecord();
           
           if(l_studentStudentMappingList!=null&&!l_studentStudentMappingList.isEmpty()){
            request.getReqAudit().setMakerID(l_studentStudentMappingList.get(3).trim());
            request.getReqAudit().setCheckerID(l_studentStudentMappingList.get(4).trim());
            request.getReqAudit().setMakerDateStamp(l_studentStudentMappingList.get(5).trim());
            request.getReqAudit().setCheckerDateStamp(l_studentStudentMappingList.get(6).trim());
            request.getReqAudit().setRecordStatus(l_studentStudentMappingList.get(7).trim());
            request.getReqAudit().setAuthStatus(l_studentStudentMappingList.get(8).trim());
            request.getReqAudit().setVersionNumber(l_studentStudentMappingList.get(9).trim());
            request.getReqAudit().setMakerRemarks(l_studentStudentMappingList.get(10).trim());
            request.getReqAudit().setCheckerRemarks(l_studentStudentMappingList.get(11).trim());
            }
           
           
           
            
          dbg("end of  buildBOfromDB"); 
        
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
 }
    
    public void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
         
         return;
         
     }
    
     public JsonObject buildJsonResFromBO()throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
         JsonObject body=null;
         try{
            dbg("inside buildJsonResFromBO");
            RequestBody<ClassStudentMapping> reqBody = request.getReqBody();
            ClassStudentMapping studentMapping =reqBody.get();
            

            body=Json.createObjectBuilder().add("standard",  studentMapping.getStandard())
                                           .add("section", studentMapping.getSection())
                                           .add("studentID", studentMapping.getStudentID())
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
       dbg("inside ClassStudentMappingService--->businessValidation");    
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

       dbg("ClassStudentMappingService--->businessValidation--->O/P--->status"+status);
       dbg("End of ClassStudentMappingService--->businessValidation");
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
        dbg("inside ClassStudentMappingService--->masterMandatoryValidation");
        RequestBody<ClassStudentMapping> reqBody = request.getReqBody();
        ClassStudentMapping studentMapping =reqBody.get();
         
         if(studentMapping.getStandard()==null||studentMapping.getStandard().isEmpty()){   
             
            status=false;
            errhandler.log_app_error("BS_VAL_002","Standard");
         }
         
         if(studentMapping.getSection()==null||studentMapping.getSection().isEmpty()){   
             
            status=false;
            errhandler.log_app_error("BS_VAL_002","Section");
         }
         
         
         dbg("ClassStudentMappingService--->masterMandatoryValidation--->O/P--->status"+status);
         dbg("End of ClassStudentMappingService--->masterMandatoryValidation");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }   
    
     private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
     try{
        dbg("inside ClassStudentMappingService--->masterDataValidation");   
        BSValidation bsv=inject.getBsv(session);
        RequestBody<ClassStudentMapping> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        ClassStudentMapping studentMapping =reqBody.get();
        String l_standard=studentMapping.getStandard();
        String l_section=studentMapping.getSection();
             
        if(!bsv.standardSectionValidation(l_standard,l_section, l_instituteID, session, dbSession, inject)){
            status=false;
            errhandler.log_app_error("BS_VAL_003","Standard or section");
        }
          

        dbg("ClassStudentMappingServiceService--->masterDataValidation--->O/P--->status"+status);
        dbg("End of ClassStudentMappingServiceService--->masterDataValidation");  
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
       return true;
        
    }
     private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         return true;
              
    }
    
    public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     ExistingAudit exAudit=new ExistingAudit();
     try{
        dbg("inside ClassStudentMapping--->getExistingAudit") ;
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_operation=request.getReqHeader().getOperation();
        String l_service=request.getReqHeader().getService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
        if(!(l_operation.equals("Create"))){
            
             if(l_operation.equals("AutoAuth")&&l_versionNumber.equals("1")){
                return null;
              }else{
               RequestBody<ClassStudentMapping> reqBody = request.getReqBody();
               ClassStudentMapping studentMapping =reqBody.get();
               String l_standard=studentMapping.getStandard();
               String l_section=studentMapping.getSection();  
               String l_studentID=studentMapping.getStudentID();
               String[] l_pkey={l_standard,l_section,l_studentID};
               DBRecord l_studentMappingRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS","CLASS_STUDENT_MAPPING", l_pkey, session, dbSession);
               exAudit.setAuthStatus(l_studentMappingRecord.getRecord().get(8).trim());
               exAudit.setMakerID(l_studentMappingRecord.getRecord().get(3).trim());
               exAudit.setRecordStatus(l_studentMappingRecord.getRecord().get(7).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_studentMappingRecord.getRecord().get(9).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of ClassStudentMapping--->getExistingAudit") ;
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
