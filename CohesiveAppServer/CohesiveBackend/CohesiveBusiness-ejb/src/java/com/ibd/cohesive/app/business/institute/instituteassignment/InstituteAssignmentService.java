/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.instituteassignment;

import com.ibd.businessViews.IInstituteAssignmentService;
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
//@Local(IInstituteAssignmentService.class)
@Remote(IInstituteAssignmentService.class)
@Stateless
public class InstituteAssignmentService implements IInstituteAssignmentService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public InstituteAssignmentService(){
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
       dbg("inside InstituteAssignmentService--->processing");
       dbg("InstituteAssignmentService--->Processing--->I/P--->requestJson"+requestJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,requestJson);
       bs.requestlogging(request,requestJson, inject,session,dbSession);
       buildBOfromReq(requestJson);  
       String l_operation=request.getReqHeader().getOperation();
       if(!l_operation.equals("Create-Default")){
       
           RequestBody<InstituteAssignment> reqBody = request.getReqBody();
           InstituteAssignment assignment =reqBody.get();
           String l_assignmentID=assignment.getAssignmentID();
           l_lockKey=l_assignmentID;
           if(!businessLock.getBusinessLock(request, l_lockKey, session)){
               l_validation_status=false;
               throw new BSValidationException("BSValidationException");
            }
       
       }
       BusinessEJB<IInstituteAssignmentService>assignmentEJB=new BusinessEJB();
       assignmentEJB.set(this);
       exAudit=bs.getExistingAudit(assignmentEJB);
       
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
       bs.businessServiceProcssing(request, exAudit, inject,assignmentEJB);
       
       
       if(l_operation.equals("Create-Default")){
           
           createDefault();
       }

         if(l_session_created_now){
             jsonResponse= bs.buildSuccessResponse(requestJson, request, inject, session,assignmentEJB) ;
             tc.commit(session,dbSession);
             dbg("commit is called in  service");
        }
       dbg("InstituteAssignmentService--->Processing--->O/P--->jsonResponse"+jsonResponse);     
       dbg("End of assignmentService--->processing");     
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
//            if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
          clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes  
 //BSProcessingException ex=new BSProcessingException("response contains special characters");
   //             dbg(ex);
                
     //           session.clearSessionObject();
       //        dbSession.clearSessionObject();
         //      throw ex;
               
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
       dbg("inside InstituteAssignmentService--->buildBOfromReq"); 
       dbg("InstituteAssignmentService--->buildBOfromReq--->I/P--->p_request"+p_request.toString()); 
       RequestBody<InstituteAssignment> reqBody = new RequestBody<InstituteAssignment>(); 
       InstituteAssignment assignment = new InstituteAssignment();
       String l_operation=request.getReqHeader().getOperation();
       
       if(!l_operation.equals("Create-Default")){
       
       JsonObject l_body=p_request.getJsonObject("body");
       assignment.setInstituteID(l_body.getString("instituteID"));
       assignment.setInstituteName(l_body.getString("instituteName"));
     
       assignment.setAssignmentID(l_body.getString("assignmentID"));
       
       
       if(!(l_operation.equals("View"))){
            assignment.setAssignmentDescription(l_body.getString("assignmentDescription"));
            assignment.setSubjectID(l_body.getString("subjectID"));
            assignment.setGroupID(l_body.getString("groupID"));
            assignment.setAssignmentType(l_body.getString("assignmentType"));
            assignment.setTeacherComments(l_body.getString("teacherComments"));
            assignment.setUrl(l_body.getString("URL"));
            assignment.setDueDate(l_body.getString("dueDate"));
        }
       
       }
       reqBody.set(assignment);
      request.setReqBody(reqBody);
      dbg("End of InstituteAssignmentService--->buildBOfromReq");
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
        String sequenceNo="A"+lock.getSequenceNo();
            
        RequestBody<InstituteAssignment> reqBody = request.getReqBody();
        InstituteAssignment assignment =reqBody.get();
        
        assignment.setAssignmentID(sequenceNo);
        
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
        dbg("Inside InstituteAssignmentService--->create"); 
        RequestBody<InstituteAssignment> reqBody = request.getReqBody();
        InstituteAssignment assignment =reqBody.get();
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
        
        String l_instituteID=assignment.getInstituteID();
        String l_groupID=assignment.getGroupID();
        String l_assignmentID=assignment.getAssignmentID();
        String l_assignmentDescription=assignment.getAssignmentDescription();
        String l_subjectID=assignment.getSubjectID();
        String l_assignmentType=assignment.getAssignmentType();
        String l_dueDate=assignment.getDueDate();
        String l_teacherComments=assignment.getTeacherComments();
        String l_url=assignment.getUrl();

            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT",93,l_instituteID,l_groupID,l_assignmentID,l_assignmentDescription,l_subjectID,l_assignmentType,l_dueDate,l_teacherComments,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks,l_url);
       
        
       

        dbg("End of InstituteAssignmentService--->create");
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
            dbg("Inside InstituteAssignmentService--->update"); 
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<InstituteAssignment> reqBody = request.getReqBody();
            InstituteAssignment assignment =reqBody.get();
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
            String l_instituteID=assignment.getInstituteID(); 
            String l_assignmentID=assignment.getAssignmentID();
            Map<String,String>  l_column_to_update=new HashMap();
            l_column_to_update.put("10", l_checkerID);
            l_column_to_update.put("12", l_checkerDateStamp);
            l_column_to_update.put("14", l_authStatus);
            l_column_to_update.put("17", l_checkerRemarks);
            
             
             String[] l_primaryKey={l_assignmentID};
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT","IVW_ASSIGNMENT", l_primaryKey, l_column_to_update, session);

             dbg("End of InstituteAssignmentService--->update");          
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
        dbg("Inside InstituteAssignmentService--->fullUpdate");
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<InstituteAssignment> reqBody = request.getReqBody();
        InstituteAssignment assignment =reqBody.get();
        String l_instituteID=assignment.getInstituteID(); 
        String l_groupID=assignment.getGroupID();
        String l_assignmentID=assignment.getAssignmentID();
        String l_assignmentDescription=assignment.getAssignmentDescription();
        String l_subjectID=assignment.getSubjectID();
        String l_assignmentType=assignment.getAssignmentType();
        String l_dueDate=assignment.getDueDate();
        String l_teacherComments=assignment.getTeacherComments();
        String l_url=assignment.getUrl();
        l_column_to_update= new HashMap();
        l_column_to_update.put("2", l_groupID);
        l_column_to_update.put("3", l_assignmentID);
        l_column_to_update.put("4", l_assignmentDescription);
        l_column_to_update.put("5", l_subjectID);
        l_column_to_update.put("6", l_assignmentType);
        l_column_to_update.put("7", l_dueDate);
        l_column_to_update.put("8", l_teacherComments);
        l_column_to_update.put("18", l_url);
        
        String[] l_PKey={l_assignmentID};
            
            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT","IVW_ASSIGNMENT",l_PKey,l_column_to_update,session);
        
       
       
        
                   
        dbg("end of InstituteAssignmentService--->fullUpdate");                
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
        dbg("Inside InstituteAssignmentService--->delete");  
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<InstituteAssignment> reqBody = request.getReqBody();
        InstituteAssignment assignment =reqBody.get();
        String l_instituteID=assignment.getInstituteID();   
        String l_assignmentID=assignment.getAssignmentID();

        String[] l_primaryKey={l_assignmentID};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT","IVW_ASSIGNMENT", l_primaryKey, session);
         
               
 
         dbg("End of InstituteAssignmentService--->delete");      
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
            dbg("Inside InstituteAssignmentService--->tableRead");
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<InstituteAssignment> reqBody = request.getReqBody();
            InstituteAssignment assignment =reqBody.get();
            String l_instituteID=assignment.getInstituteID();
            String l_assignmentID=assignment.getAssignmentID();
            String[] l_pkey={l_assignmentID};
            DBRecord assignmentRecord=null;
            
            try{
            
                assignmentRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT","IVW_ASSIGNMENT", l_pkey, session,dbSession);
                buildBOfromDB(assignmentRecord);
           
            }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", l_assignmentID);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
         
          dbg("end of  InstituteAssignmentService--->tableRead");               
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
     
      private void buildBOfromDB( DBRecord p_assignment)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
            dbg("inside InstituteAssignmentService--->buildBOfromDB");
            
            ArrayList<String>l_assignmentList=p_assignment.getRecord();
            RequestBody<InstituteAssignment> reqBody = request.getReqBody();
            InstituteAssignment assignment =reqBody.get();
            String l_instituteID=assignment.getInstituteID();
            BusinessService bs=inject.getBusinessService(session);
            if(l_assignmentList!=null&&!l_assignmentList.isEmpty()){
                
               assignment.setInstituteName(bs.getInstituteName(l_instituteID, session, dbSession, inject));
               assignment.setGroupID(l_assignmentList.get(1).trim());
               String subjectID=l_assignmentList.get(4).trim();
               String subjectName=bs.getSubjectName(subjectID, l_instituteID, session, dbSession, inject);
               assignment.setSubjectID(subjectID);
               assignment.setSubjectName(subjectName);
               assignment.setAssignmentDescription(l_assignmentList.get(3).trim());
               assignment.setAssignmentType(l_assignmentList.get(5).trim());
               assignment.setDueDate(l_assignmentList.get(6).trim());
               assignment.setTeacherComments(l_assignmentList.get(7).trim());
               assignment.setUrl(l_assignmentList.get(17).trim());
               request.getReqAudit().setMakerID(l_assignmentList.get(8).trim());
               request.getReqAudit().setCheckerID(l_assignmentList.get(9).trim());
               request.getReqAudit().setMakerDateStamp(l_assignmentList.get(10).trim());
               request.getReqAudit().setCheckerDateStamp(l_assignmentList.get(11).trim());
               request.getReqAudit().setRecordStatus(l_assignmentList.get(12).trim());
               request.getReqAudit().setAuthStatus(l_assignmentList.get(13).trim());
               request.getReqAudit().setVersionNumber(l_assignmentList.get(14).trim());
               request.getReqAudit().setMakerRemarks(l_assignmentList.get(15).trim());
               request.getReqAudit().setCheckerRemarks(l_assignmentList.get(16).trim());
            }
            
          
       
        dbg("End of InstituteAssignmentService--->buildBOfromDB");
        
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
            RequestBody<InstituteAssignment> reqBody = request.getReqBody();
            InstituteAssignment assignment =reqBody.get();
            
            String operation=request.getReqHeader().getOperation();
            
            if(operation.equals("Create-Default")){
                
                body=Json.createObjectBuilder().add("assignmentID",  assignment.getAssignmentID())
                                                .build();
                
                
                
            }else{
            
            
             body=Json.createObjectBuilder().add("instituteID", assignment.getInstituteID())
                                            .add("instituteName", assignment.getInstituteName())
                                            .add("groupID", assignment.getGroupID())
                                            .add("assignmentID", assignment.getAssignmentID())
                                            .add("assignmentDescription", assignment.getAssignmentDescription())
                                            .add("subjectID", assignment.getSubjectID())
                                            .add("subjectName", assignment.getSubjectName())
                                            .add("assignmentType", assignment.getAssignmentType())
                                            .add("teacherComments", assignment.getTeacherComments())
                                            .add("dueDate", assignment.getDueDate())
                                            .add("URL", assignment.getUrl())
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
       dbg("inside InstituteAssignmentService--->businessValidation");    
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

       dbg("InstituteAssignmentService--->businessValidation--->O/P--->status"+status);
       dbg("End of InstituteAssignmentService--->businessValidation");
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
        dbg("inside InstituteAssignmentService--->masterMandatoryValidation");
        RequestBody<InstituteAssignment> reqBody = request.getReqBody();
        InstituteAssignment assignment =reqBody.get();
         
         if(assignment.getInstituteID()==null||assignment.getInstituteID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","instituteID");
         }
         
         if(assignment.getAssignmentID()==null||assignment.getAssignmentID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","assignmentID");
         }
        
         
         
         dbg("InstituteAssignmentService--->masterMandatoryValidation--->O/P--->status"+status);
         dbg("End of InstituteAssignmentService--->masterMandatoryValidation");
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
        RequestBody<InstituteAssignment> reqBody = request.getReqBody();
        InstituteAssignment assignment =reqBody.get();
        String l_instituteID= assignment.getInstituteID();
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
            dbg("inside InstituteAssignmentService--->detailMandatoryValidation");
            RequestBody<InstituteAssignment> reqBody = request.getReqBody();
            InstituteAssignment assignment =reqBody.get();
            
            if(assignment.getGroupID()==null||assignment.getGroupID().isEmpty()) {   
               status=false;
               errhandler.log_app_error("BS_VAL_002","groupID");
            }
            
             if(assignment.getAssignmentDescription()==null||assignment.getAssignmentDescription().isEmpty()) {   
                status=false;
                errhandler.log_app_error("BS_VAL_002","assignmentDescription");
             }
             if(assignment.getSubjectID()==null||assignment.getSubjectID().isEmpty()) {   
                status=false;
                errhandler.log_app_error("BS_VAL_002","subjectID");
             }
            if(assignment.getAssignmentType()==null||assignment.getAssignmentType().isEmpty()) {   
               status=false;
               errhandler.log_app_error("BS_VAL_002","assignmentType");
            }

            if(assignment.getDueDate()==null||assignment.getDueDate().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","dueDate");  
            }
            
  
            if(assignment.getUrl()==null||assignment.getUrl().isEmpty()){
           
               status=false;  
                errhandler.log_app_error("BS_VAL_002","URL or File");
            }
            
           
            
            
            
        dbg("InstituteAssignmentService--->detailMandatoryValidation--->O/P--->status"+status);
        dbg("End of InstituteAssignmentService--->detailMandatoryValidation");
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
  
   private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
     try{
        dbg("inside InstituteAssignmentService--->detailDataValidation");   
        BSValidation bsv=inject.getBsv(session);
        RequestBody<InstituteAssignment> reqBody = request.getReqBody();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_operation=request.getReqAudit().getVersionNumber();
        BusinessService bs=inject.getBusinessService(session);
        String currentDate=bs.getCurrentDate();
        InstituteAssignment assignment =reqBody.get();
        String l_dueDate=assignment.getDueDate();
        String l_subjectID=assignment.getSubjectID();
        String l_instituteID=assignment.getInstituteID();
        String l_groupID=assignment.getGroupID();
        String l_assignmentType=assignment.getAssignmentType();
        String l_assignmentID=assignment.getAssignmentID();
        String l_url=assignment.getUrl();
        
        
             if(!bsv.groupIDValidation(l_groupID, l_instituteID, session, dbSession, inject)){
                status=false;
                errhandler.log_app_error("BS_VAL_003","groupID");
             }
            
             if(!(l_assignmentType.equals("H")||l_assignmentType.equals("T")||l_assignmentType.equals("P")||l_assignmentType.equals("I"))){
                 
                status=false;
                errhandler.log_app_error("BS_VAL_003","assignemntType");
             }
             
             if(!bsv.subjectIDValidation(l_subjectID, l_instituteID, session, dbSession, inject)){
                status=false;
                errhandler.log_app_error("BS_VAL_003","subjectID");
             }
             
             if(!bsv.dateFormatValidation(l_dueDate, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","dueDate");
             }
             
             if(!bsv.pastDateValidation(l_dueDate, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","dueDate");
             }

        if(l_operation.equals("Modify")){
            boolean recordExistence=false;
            
            try{
            
                String[] l_pkey={l_instituteID,l_assignmentID};
                readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate, "BATCH", "ASSIGNMENT_EOD_STATUS", l_pkey,session, dbSession);
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
             
             
        if(l_url.contains("youtube")||l_url.contains("youtu.be")){
            
            if(!l_url.contains("embed")){
                
                 int startPosofID = l_url.lastIndexOf("/");
                 dbg("startPosofID"+startPosofID);
                 String ID=l_url.substring(startPosofID+1,l_url.length());
                 dbg("ID"+ID);
                 String dummy ="https://www.youtube.com/embed/";
                 String youtubeURL=dummy.concat(ID);
                 dbg("youtubeURL"+youtubeURL);
                 assignment.setUrl(youtubeURL);
            }

        }else{
             status=false;
             errhandler.log_app_error("BS_VAL_059",null);
        }
             
        dbg("InstituteAssignmentService--->detailDataValidation--->O/P--->status"+status);
        dbg("End of InstituteAssignmentService--->detailDataValidation");  
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
        RequestBody<InstituteAssignment> assignmentBody = request.getReqBody();
        InstituteAssignment assignment =assignmentBody.get();
        String l_instituteID=assignment.getInstituteID();
        String l_versioNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
         if(!(l_operation.equals("Create")||l_operation.equals("Create-Default")||l_operation.equals("View"))){
             
            if(l_operation.equals("AutoAuth")&&l_versioNumber.equals("1")){
                
                return null;
            }else{  
                
               dbg("inside business Service--->getExistingAudit--->Service--->InstituteAssignment");  
               
               String l_assignmentID=assignment.getAssignmentID();
               String[] l_pkey={l_assignmentID};
            
               DBRecord instituteRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT","IVW_ASSIGNMENT", l_pkey, session,dbSession);

               exAudit.setAuthStatus(instituteRecord.getRecord().get(13).trim());
               exAudit.setMakerID(instituteRecord.getRecord().get(8).trim());
               exAudit.setRecordStatus(instituteRecord.getRecord().get(12).trim());
               exAudit.setVersionNumber(Integer.parseInt(instituteRecord.getRecord().get(14).trim()));
            

 
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
