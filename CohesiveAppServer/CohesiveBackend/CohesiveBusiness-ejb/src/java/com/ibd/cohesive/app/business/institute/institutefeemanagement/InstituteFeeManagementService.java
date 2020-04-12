/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.institutefeemanagement;

import com.ibd.businessViews.IInstituteFeeManagementService;
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
//@Local(IInstituteFeeManagementService.class)
@Remote(IInstituteFeeManagementService.class)
@Stateless
public class InstituteFeeManagementService implements IInstituteFeeManagementService {
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public InstituteFeeManagementService(){
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
       dbg("inside InstituteFeeManagementService--->processing");
       dbg("InstituteFeeManagementService--->Processing--->I/P--->requestJson"+requestJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,requestJson);
       bs.requestlogging(request,requestJson, inject,session,dbSession);
       buildBOfromReq(requestJson);  
       String l_operation=request.getReqHeader().getOperation();
       
       if(!l_operation.equals("Create-Default")){
       
           RequestBody<InstituteFeeManagement> reqBody = request.getReqBody();
           InstituteFeeManagement feeManagement =reqBody.get();
           String l_feeID=feeManagement.getFeeID();
           l_lockKey=l_feeID;
           if(!businessLock.getBusinessLock(request, l_lockKey, session)){
               l_validation_status=false;
               throw new BSValidationException("BSValidationException");
            }
           
       }
       BusinessEJB<IInstituteFeeManagementService>feeManagementEJB=new BusinessEJB();
       feeManagementEJB.set(this);
       exAudit=bs.getExistingAudit(feeManagementEJB);
       
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
      
      
       bs.businessServiceProcssing(request, exAudit, inject,feeManagementEJB);
       
        if(l_operation.equals("Create-Default")){
           
           createDefault();
       }

         if(l_session_created_now){
             jsonResponse= bs.buildSuccessResponse(requestJson, request, inject, session,feeManagementEJB) ;
             tc.commit(session,dbSession);
             dbg("commit is called in  service");
        }
       dbg("InstituteFeeManagementService--->Processing--->O/P--->jsonResponse"+jsonResponse);     
       dbg("End of feeManagementService--->processing");     
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
       dbg("inside InstituteFeeManagementService--->buildBOfromReq"); 
       dbg("InstituteFeeManagementService--->buildBOfromReq--->I/P--->p_request"+p_request.toString()); 
       RequestBody<InstituteFeeManagement> reqBody = new RequestBody<InstituteFeeManagement>(); 
       InstituteFeeManagement feeManagement = new InstituteFeeManagement();
       JsonObject l_body=p_request.getJsonObject("body");
       String l_operation=request.getReqHeader().getOperation();
       
       if(!l_operation.equals("Create-Default")){
       
       feeManagement.setInstituteID(l_body.getString("instituteID"));
       feeManagement.setFeeID(l_body.getString("feeID"));
       if(!(l_operation.equals("View"))){
           
            feeManagement.setFeeDescription(l_body.getString("feeDescription"));
            feeManagement.setFeeType(l_body.getString("feeType"));
            feeManagement.setAmount(l_body.getString("amount"));
            feeManagement.setGroupID(l_body.getString("groupID"));
            feeManagement.setDueDate(l_body.getString("dueDate"));
        }
       
       }
       reqBody.set(feeManagement);
      request.setReqBody(reqBody);
      dbg("End of InstituteFeeManagementService--->buildBOfromReq");
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
        String sequenceNo="F"+lock.getSequenceNo();
            
        RequestBody<InstituteFeeManagement> reqBody = request.getReqBody();
        InstituteFeeManagement assignment =reqBody.get();
        assignment.setFeeID(sequenceNo);
        
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
        dbg("Inside InstituteFeeManagementService--->create"); 
        RequestBody<InstituteFeeManagement> reqBody = request.getReqBody();
        InstituteFeeManagement feeManagement =reqBody.get();
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
        
        String l_instituteID=feeManagement.getInstituteID();
        String l_groupID=feeManagement.getGroupID();
        String l_feeID=feeManagement.getFeeID();
        String l_feeType=feeManagement.getFeeType();
        String l_amount=feeManagement.getAmount();
        String l_dueDate=feeManagement.getDueDate();
        String l_feeDescription=feeManagement.getFeeDescription();

           // dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"FEE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_feeID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_feeID,"FEE",92,l_instituteID,l_groupID,l_feeID,l_feeDescription,l_feeType,l_amount,l_dueDate,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
       
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE",92,l_instituteID,l_groupID,l_feeID,l_feeDescription,l_feeType,l_amount,l_dueDate,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
       
       

        dbg("End of InstituteFeeManagementService--->create");
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
            dbg("Inside InstituteFeeManagementService--->update"); 
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<InstituteFeeManagement> reqBody = request.getReqBody();
            InstituteFeeManagement feeManagement =reqBody.get();
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
            String l_instituteID=feeManagement.getInstituteID(); 
            String l_feeID=feeManagement.getFeeID();
            Map<String,String>  l_column_to_update=new HashMap();
            l_column_to_update.put("9", l_checkerID);
            l_column_to_update.put("11", l_checkerDateStamp);
            l_column_to_update.put("13", l_authStatus);
            l_column_to_update.put("16", l_checkerRemarks);
            
             
             String[] l_primaryKey={l_feeID};
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", l_primaryKey, l_column_to_update, session);

             dbg("End of InstituteFeeManagementService--->update");          
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
        dbg("Inside InstituteFeeManagementService--->fullUpdate");
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<InstituteFeeManagement> reqBody = request.getReqBody();
        InstituteFeeManagement feeManagement =reqBody.get();
        String l_makerDateStamp=request.getReqAudit().getVersionNumber();
        String l_instituteID=feeManagement.getInstituteID(); 
        String l_groupID=feeManagement.getGroupID();
        String l_feeID=feeManagement.getFeeID();
        String l_feeType=feeManagement.getFeeType();
        String l_amount=feeManagement.getAmount();
        String l_dueDate=feeManagement.getDueDate();
        String l_feeDescription=feeManagement.getFeeDescription(); 
        l_column_to_update= new HashMap();
        l_column_to_update.put("2", l_groupID);
        l_column_to_update.put("3", l_feeID);
        l_column_to_update.put("4", l_feeDescription);
        l_column_to_update.put("5", l_feeType);
        l_column_to_update.put("6", l_amount);
        l_column_to_update.put("7", l_dueDate);
        l_column_to_update.put("10", l_makerDateStamp);
        
        String[] l_PKey={l_feeID};
            
            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT",l_PKey,l_column_to_update,session);
        
       
       
        
                   
        dbg("end of InstituteFeeManagementService--->fullUpdate");                
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
        dbg("Inside InstituteFeeManagementService--->delete");  
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<InstituteFeeManagement> reqBody = request.getReqBody();
        InstituteFeeManagement feeManagement =reqBody.get();
        String l_instituteID=feeManagement.getInstituteID();   
        String l_feeManagementID=feeManagement.getFeeID();

        String[] l_primaryKey={l_feeManagementID};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", l_primaryKey, session);
         
               
 
         dbg("End of InstituteFeeManagementService--->delete");      
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
            dbg("Inside InstituteFeeManagementService--->tableRead");
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<InstituteFeeManagement> reqBody = request.getReqBody();
            InstituteFeeManagement feeManagement =reqBody.get();
            String l_instituteID=feeManagement.getInstituteID();
            String l_feeManagementID=feeManagement.getFeeID();
            String[] l_pkey={l_feeManagementID};
            DBRecord feeManagementRecord=null;
            
            try{
            
            
                feeManagementRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", l_pkey, session,dbSession);
         
             }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", l_feeManagementID);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
                }
            
            
            
            buildBOfromDB(feeManagementRecord);
           
            
         
          dbg("end of  InstituteFeeManagementService--->tableRead");  
        }catch(DBValidationException ex){
            throw ex;  
        }catch(BSValidationException ex){
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
     
      private void buildBOfromDB( DBRecord p_feeManagement)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
            dbg("inside InstituteFeeManagementService--->buildBOfromDB");
            
            ArrayList<String>l_feeManagementList=p_feeManagement.getRecord();
            RequestBody<InstituteFeeManagement> reqBody = request.getReqBody();
            InstituteFeeManagement feeManagement =reqBody.get();
            String l_instituteID=feeManagement.getInstituteID();
            BusinessService bs=inject.getBusinessService(session);
            
            if(l_feeManagementList!=null&&!l_feeManagementList.isEmpty()){
                
               String instituteName=bs.getInstituteName(l_instituteID, session, dbSession, inject);
               feeManagement.setInstituteName(instituteName);
               feeManagement.setGroupID(l_feeManagementList.get(1).trim());
               feeManagement.setFeeDescription(l_feeManagementList.get(3).trim());
               feeManagement.setFeeType(l_feeManagementList.get(4).trim());
               feeManagement.setAmount(l_feeManagementList.get(5).trim());
               feeManagement.setDueDate(l_feeManagementList.get(6).trim());
               request.getReqAudit().setMakerID(l_feeManagementList.get(7).trim());
               request.getReqAudit().setCheckerID(l_feeManagementList.get(8).trim());
               request.getReqAudit().setMakerDateStamp(l_feeManagementList.get(9).trim());
               request.getReqAudit().setCheckerDateStamp(l_feeManagementList.get(10).trim());
               request.getReqAudit().setRecordStatus(l_feeManagementList.get(11).trim());
               request.getReqAudit().setAuthStatus(l_feeManagementList.get(12).trim());
               request.getReqAudit().setVersionNumber(l_feeManagementList.get(13).trim());
               request.getReqAudit().setMakerRemarks(l_feeManagementList.get(14).trim());
               request.getReqAudit().setCheckerRemarks(l_feeManagementList.get(15).trim());
            }
            
          
       
        dbg("End of InstituteFeeManagementService--->buildBOfromDB");
        
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
            RequestBody<InstituteFeeManagement> reqBody = request.getReqBody();
            InstituteFeeManagement feeManagement =reqBody.get();
            String operation=request.getReqHeader().getOperation();
            
            if(operation.equals("Create-Default")){
                
                body=Json.createObjectBuilder().add("feeID", feeManagement.getFeeID())
                                               .build();
                
                
                
            }else{
            
             body=Json.createObjectBuilder().add("instituteID", feeManagement.getInstituteID())
                                            .add("instituteName", feeManagement.getInstituteName())
                                            .add("groupID", feeManagement.getGroupID())
                                            .add("feeID", feeManagement.getFeeID())
                                            .add("feeType", feeManagement.getFeeType())
                                            .add("amount", feeManagement.getAmount())
                                            .add("dueDate", feeManagement.getDueDate())
                                            .add("feeDescription", feeManagement.getFeeDescription())
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
       dbg("inside InstituteFeeManagementService--->businessValidation");    
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

       dbg("InstituteFeeManagementService--->businessValidation--->O/P--->status"+status);
       dbg("End of InstituteFeeManagementService--->businessValidation");
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
        dbg("inside InstituteFeeManagementService--->masterMandatoryValidation");
        RequestBody<InstituteFeeManagement> reqBody = request.getReqBody();
        InstituteFeeManagement feeManagement =reqBody.get();
         
         if(feeManagement.getInstituteID()==null||feeManagement.getInstituteID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","instituteID");
         }
         
         if(feeManagement.getFeeID()==null||feeManagement.getFeeID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","feeID");
         }
        
         
         
         dbg("InstituteFeeManagementService--->masterMandatoryValidation--->O/P--->status"+status);
         dbg("End of InstituteFeeManagementService--->masterMandatoryValidation");
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
        RequestBody<InstituteFeeManagement> reqBody = request.getReqBody();
        InstituteFeeManagement feeManagement =reqBody.get();
        String l_instituteID= feeManagement.getInstituteID();
//        String l_feeType=feeManagement.getFeeType();
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
            dbg("inside InstituteFeeManagementService--->detailMandatoryValidation");
            RequestBody<InstituteFeeManagement> reqBody = request.getReqBody();
            InstituteFeeManagement feeManagement =reqBody.get();
           
            if(feeManagement.getGroupID()==null||feeManagement.getGroupID().isEmpty()) {   
               status=false;
               errhandler.log_app_error("BS_VAL_002","groupID");
            }
            
           if(feeManagement.getAmount()==null||feeManagement.getAmount().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","amount");  
            }
            if(feeManagement.getDueDate()==null||feeManagement.getDueDate().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","dueDate");  
            }
//            if(feeManagement.getRemarks()==null||feeManagement.getRemarks().isEmpty()){
//               status=false;  
//               errhandler.log_app_error("BS_VAL_002","remarks");  
//            }
            
            if(feeManagement.getFeeType()==null||feeManagement.getFeeType().isEmpty()) {   
               status=false;
               errhandler.log_app_error("BS_VAL_002","feeType");
            }
            
        dbg("InstituteFeeManagementService--->detailMandatoryValidation--->O/P--->status"+status);
        dbg("End of InstituteFeeManagementService--->detailMandatoryValidation");
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
  
   private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
     try{
        dbg("inside InstituteFeeManagementService--->detailDataValidation");   
        BSValidation bsv=inject.getBsv(session);
        RequestBody<InstituteFeeManagement> reqBody = request.getReqBody();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        InstituteFeeManagement feeManagement =reqBody.get();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        BusinessService bs=inject.getBusinessService(session);
        String currentDate=bs.getCurrentDate();
        String l_dueDate=feeManagement.getDueDate();
        String l_amount=feeManagement.getAmount();
        String l_feeType=feeManagement.getFeeType();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String groupID=feeManagement.getGroupID();
        String l_feeID=feeManagement.getFeeID();
        
        String l_operation=request.getReqHeader().getOperation();
            
        
             if(!bsv.feeTypeValidation(l_feeType, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","feeType");
             }
             
             if(!bsv.groupIDValidation(groupID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Assignee group");
             }
        
             if(!bsv.amountValidation(l_amount, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","amount");
             }
             if(!bsv.dateFormatValidation(l_dueDate, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","dueDate");
             }
             
             
         if(l_operation.equals("Modify")||l_operation.equals("Delete")){
            boolean recordExistence=false;
            
            try{
            
                String[] l_pkey={l_feeID};
                readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE", "FEE_BATCH_INDICATOR", l_pkey,session, dbSession);
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
                errhandler.log_app_error("BS_VAL_042","Fee");
            }
        }    
             
             

        dbg("InstituteFeeManagementService--->detailDataValidation--->O/P--->status"+status);
        dbg("End of InstituteFeeManagementService--->detailDataValidation");  
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
        RequestBody<InstituteFeeManagement> feeManagementBody = request.getReqBody();
        InstituteFeeManagement feeManagement =feeManagementBody.get();
        String l_instituteID=feeManagement.getInstituteID();
        String l_versioNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
         if(!(l_operation.equals("Create")||l_operation.equals("Create-Default")||l_operation.equals("View"))){
             
            if(l_operation.equals("AutoAuth")&&l_versioNumber.equals("1")){
                
                return null;
            }else{  
                
               dbg("inside business Service--->getExistingAudit--->Service--->InstituteFeeManagement");  
               
               String l_feeManagementID=feeManagement.getFeeID();
               String[] l_pkey={l_feeManagementID};
            
               DBRecord instituteRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", l_pkey, session,dbSession);

               exAudit.setAuthStatus(instituteRecord.getRecord().get(12).trim());
               exAudit.setMakerID(instituteRecord.getRecord().get(7).trim());
               exAudit.setRecordStatus(instituteRecord.getRecord().get(11).trim());
               exAudit.setVersionNumber(Integer.parseInt(instituteRecord.getRecord().get(13).trim()));
            

 
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
