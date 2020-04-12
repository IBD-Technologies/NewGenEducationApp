/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.ecircularService;

import com.ibd.businessViews.IECircularService;
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
 * @author DELL
 */
@Remote(IECircularService.class)
@Stateless
public class ECircularService implements IECircularService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public ECircularService(){
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
       dbg("inside ECircularService--->processing");
       dbg("ECircularService--->Processing--->I/P--->requestJson"+requestJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,requestJson);
       bs.requestlogging(request,requestJson, inject,session,dbSession);
       buildBOfromReq(requestJson);  
       String l_operation=request.getReqHeader().getOperation();
       
       if(!l_operation.equals("Create-Default")){
       
           RequestBody<ECircular> reqBody = request.getReqBody();
           ECircular circular =reqBody.get();
           String l_circularID=circular.getE_circularID();
           l_lockKey=l_circularID;
           if(!businessLock.getBusinessLock(request, l_lockKey, session)){
               l_validation_status=false;
               throw new BSValidationException("BSValidationException");
            }
       
       }
       BusinessEJB<IECircularService>circularEJB=new BusinessEJB();
       circularEJB.set(this);
       exAudit=bs.getExistingAudit(circularEJB);
       
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
      
       bs.businessServiceProcssing(request, exAudit, inject,circularEJB);
       
       
       if(l_operation.equals("Create-Default")){
           
           createDefault();
       }
       

         if(l_session_created_now){
             jsonResponse= bs.buildSuccessResponse(requestJson, request, inject, session,circularEJB) ;
             tc.commit(session,dbSession);
             dbg("commit is called in  service");
        }
       dbg("ECircularService--->Processing--->O/P--->jsonResponse"+jsonResponse);     
       dbg("End of circularService--->processing");     
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
            dbg("Response"+jsonResponse.toString());
            clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
            BSValidation bsv=inject.getBsv(session);
            //if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                      clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes  
            //BSProcessingException ex=new BSProcessingException("response contains special characters");
              //  dbg(ex);
                
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
       dbg("inside ECircularService--->buildBOfromReq"); 
       dbg("ECircularService--->buildBOfromReq--->I/P--->p_request"+p_request.toString()); 
       RequestBody<ECircular> reqBody = new RequestBody<ECircular>(); 
       ECircular circular = new ECircular();
       String l_operation=request.getReqHeader().getOperation();
       
       if(!l_operation.equals("Create-Default")){
       
           JsonObject l_body=p_request.getJsonObject("body");
           circular.setInstituteID(l_body.getString("instituteID"));
           circular.setInstituteName(l_body.getString("instituteName"));
          
           circular.setE_circularID(l_body.getString("circularID"));
           

           if(!(l_operation.equals("View"))){
                circular.setDescription(l_body.getString("circularDescription"));
                circular.setGroupID(l_body.getString("groupID"));
                circular.setContentPath(l_body.getString("contentPath"));
                circular.setMessage(l_body.getString("message"));
                circular.setCircularDate(l_body.getString("circularDate"));
                circular.setCircularType(l_body.getString("circularType"));
            }
       
       }
       reqBody.set(circular);
      request.setReqBody(reqBody);
      dbg("End of ECircularService--->buildBOfromReq");
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
        String sequenceNo="E"+lock.getSequenceNo();
            
        RequestBody<ECircular> reqBody = request.getReqBody();
        ECircular eCircular =reqBody.get();
        
        eCircular.setE_circularID(sequenceNo);
        
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
        dbg("Inside ECircularService--->create"); 
        RequestBody<ECircular> reqBody = request.getReqBody();
        ECircular circular =reqBody.get();
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
        
        String l_instituteID=circular.getInstituteID();
        String l_groupID=circular.getGroupID();
        String l_circularID=circular.getE_circularID();
        String l_circularDescription=circular.getDescription();
        String l_contentPath=circular.getContentPath();
        String l_message=circular.getMessage();
        String l_circularDate=circular.getCircularDate();
        String l_circularType=circular.getCircularType();
        

            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular","INSTITUTE",291,l_instituteID,l_groupID,l_circularID,l_circularDescription,l_contentPath,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks,l_message,l_circularDate,l_circularType);
       
        
       

        dbg("End of ECircularService--->create");
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
            dbg("Inside ECircularService--->update"); 
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<ECircular> reqBody = request.getReqBody();
            ECircular circular =reqBody.get();
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
            String l_instituteID=circular.getInstituteID(); 
            String l_circularID=circular.getE_circularID();
            Map<String,String>  l_column_to_update=new HashMap();
            l_column_to_update.put("7", l_checkerID);
            l_column_to_update.put("9", l_checkerDateStamp);
            l_column_to_update.put("11", l_authStatus);
            l_column_to_update.put("14", l_checkerRemarks);
            
             
             String[] l_primaryKey={l_circularID};
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular","INSTITUTE","IVW_E_CIRCULAR", l_primaryKey, l_column_to_update, session);

             dbg("End of ECircularService--->update");          
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
        dbg("Inside ECircularService--->fullUpdate");
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        RequestBody<ECircular> reqBody = request.getReqBody();
        ECircular circular =reqBody.get();
        String l_instituteID=circular.getInstituteID(); 
        String l_groupID=circular.getGroupID();
        String l_circularID=circular.getE_circularID();
        String l_circularDescription=circular.getDescription();
        String l_contentPath=circular.getContentPath();
        String l_message=circular.getMessage();
        String l_circularDate=circular.getCircularDate();
        String l_circularType=circular.getCircularType();
        l_column_to_update= new HashMap();
        l_column_to_update.put("2", l_groupID);
        l_column_to_update.put("3", l_circularID);
        l_column_to_update.put("4", l_circularDescription);
        l_column_to_update.put("5", l_contentPath);
        l_column_to_update.put("8", l_makerDateStamp);
        l_column_to_update.put("13", l_makerRemarks);
        l_column_to_update.put("15", l_message);
        l_column_to_update.put("16", l_circularDate);
        l_column_to_update.put("17", l_circularType);
        
        String[] l_PKey={l_circularID};
            
            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular","INSTITUTE","IVW_E_CIRCULAR",l_PKey,l_column_to_update,session);
        
       
       
        
                   
        dbg("end of ECircularService--->fullUpdate");                
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
        dbg("Inside ECircularService--->delete");  
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ECircular> reqBody = request.getReqBody();
        ECircular circular =reqBody.get();
        String l_instituteID=circular.getInstituteID();   
        String l_circularID=circular.getE_circularID();

        String[] l_primaryKey={l_circularID};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular","INSTITUTE","IVW_E_CIRCULAR", l_primaryKey, session);
         
               
 
         dbg("End of ECircularService--->delete");      
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
            dbg("Inside ECircularService--->tableRead");
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<ECircular> reqBody = request.getReqBody();
            ECircular circular =reqBody.get();
            String l_instituteID=circular.getInstituteID();
            String l_circularID=circular.getE_circularID();
            String[] l_pkey={l_circularID};
            DBRecord circularRecord=null;
            
            try{
            
                circularRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular","INSTITUTE","IVW_E_CIRCULAR", l_pkey, session,dbSession);
                buildBOfromDB(circularRecord);
           
            }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", l_circularID);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
                }
         
          dbg("end of  ECircularService--->tableRead");               
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
     
      private void buildBOfromDB( DBRecord p_circular)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
            dbg("inside ECircularService--->buildBOfromDB");
            
            ArrayList<String>l_circularList=p_circular.getRecord();
            RequestBody<ECircular> reqBody = request.getReqBody();
            ECircular circular =reqBody.get();
            String l_instituteID=circular.getInstituteID();
            BusinessService bs=inject.getBusinessService(session);
            
            if(l_circularList!=null&&!l_circularList.isEmpty()){
                
               circular.setInstituteName(bs.getInstituteName(l_instituteID, session, dbSession, inject));
               circular.setGroupID(l_circularList.get(1).trim());
               circular.setDescription(l_circularList.get(3).trim()); 
               circular.setContentPath(l_circularList.get(4).trim());
               circular.setMessage(l_circularList.get(14).trim());
               circular.setCircularDate(l_circularList.get(15).trim());
               circular.setCircularType(l_circularList.get(16).trim());
               request.getReqAudit().setMakerID(l_circularList.get(5).trim());
               request.getReqAudit().setCheckerID(l_circularList.get(6).trim());
               request.getReqAudit().setMakerDateStamp(l_circularList.get(7).trim());
               request.getReqAudit().setCheckerDateStamp(l_circularList.get(8).trim());
               request.getReqAudit().setRecordStatus(l_circularList.get(9).trim());
               request.getReqAudit().setAuthStatus(l_circularList.get(10).trim());
               request.getReqAudit().setVersionNumber(l_circularList.get(11).trim());
               request.getReqAudit().setMakerRemarks(l_circularList.get(12).trim());
               request.getReqAudit().setCheckerRemarks(l_circularList.get(13).trim());
            }
            
          
       
        dbg("End of ECircularService--->buildBOfromDB");
        
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
            RequestBody<ECircular> reqBody = request.getReqBody();
            ECircular circular =reqBody.get();
            String operation=request.getReqHeader().getOperation();
            
            if(operation.equals("Create-Default")){
                
                body=Json.createObjectBuilder().add("circularID", circular.getE_circularID())
                                               .build();
                
                
                
            }else{
            
            
            
             body=Json.createObjectBuilder().add("instituteID", circular.getInstituteID())
                                            .add("instituteName", circular.getInstituteName())
                                            .add("groupID", circular.getGroupID())
                                            .add("circularID", circular.getE_circularID())
                                            .add("message", circular.getMessage())
                                            .add("circularDescription", circular.getDescription())
                                            .add("contentPath", circular.getContentPath())
                                            .add("circularDate", circular.getCircularDate())
                                            .add("circularType", circular.getCircularType())
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
       dbg("inside ECircularService--->businessValidation");    
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

       dbg("ECircularService--->businessValidation--->O/P--->status"+status);
       dbg("End of ECircularService--->businessValidation");
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
        dbg("inside ECircularService--->masterMandatoryValidation");
        RequestBody<ECircular> reqBody = request.getReqBody();
        ECircular circular =reqBody.get();
         
         if(circular.getInstituteID()==null||circular.getInstituteID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","instituteID");
         }
        
         if(circular.getE_circularID()==null||circular.getE_circularID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","circularID");
         }
        
        
         
         
         
         dbg("ECircularService--->masterMandatoryValidation--->O/P--->status"+status);
         dbg("End of ECircularService--->masterMandatoryValidation");
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
        RequestBody<ECircular> reqBody = request.getReqBody();
        ECircular circular =reqBody.get();
        String l_instituteID= circular.getInstituteID();
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
            dbg("inside ECircularService--->detailMandatoryValidation");
            RequestBody<ECircular> reqBody = request.getReqBody();
            ECircular circular =reqBody.get();
            int nullCount=0;
            
             if(circular.getDescription()==null||circular.getDescription().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","description");
         }
            
            
            if(circular.getCircularDate()==null||circular.getCircularDate().isEmpty()){
                status=false;  
                errhandler.log_app_error("BS_VAL_002","Circular date"); 
            }
            
            if(circular.getCircularType()==null||circular.getCircularType().isEmpty()){
                status=false;  
                errhandler.log_app_error("BS_VAL_002","Circular Type"); 
            }
            
            if(circular.getCircularType().equals("S")){
                
                if(circular.getGroupID()==null||circular.getGroupID().isEmpty()){
                   status=false;  
                   errhandler.log_app_error("BS_VAL_002","Group ID"); 
               }
                
                
            }else{
                
                if(circular.getGroupID()!=null&&!circular.getGroupID().isEmpty()){
                   status=false;  
                   errhandler.log_app_error("BS_VAL_066",null); 
               }
            }
            
            
 
            if(circular.getContentPath()==null||circular.getContentPath().isEmpty()){
                nullCount=nullCount+1;
            }
            
            if(circular.getMessage()==null||circular.getMessage().isEmpty()){
                nullCount=nullCount+1;
                
            }
            
            
            if(nullCount!=1){
                
                status=false;  
                errhandler.log_app_error("BS_VAL_065",null); 
                
            }
            
            
        dbg("ECircularService--->detailMandatoryValidation--->O/P--->status"+status);
        dbg("End of ECircularService--->detailMandatoryValidation");
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
  
   private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
     try{
        dbg("inside ECircularService--->detailDataValidation");   
        BSValidation bsv=inject.getBsv(session);
        RequestBody<ECircular> reqBody = request.getReqBody();
        ECircular circular =reqBody.get();
        String l_circularDate=circular.getCircularDate();
        String l_circularType=circular.getCircularType();
        String groupID=circular.getGroupID();
        String l_instituteID=circular.getInstituteID();
//            
//             
             if(!bsv.dateFormatValidation(l_circularDate, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Circular Date");
             }
             
             if(!(l_circularType.equals("S")||l_circularType.equals("T"))){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Circular Date");
             }
             
             if(l_circularType.equals("S")){
                 
                 if(!bsv.groupIDValidation(groupID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Group ID");
             }
                 
                 
             }
             

        dbg("ECircularService--->detailDataValidation--->O/P--->status"+status);
        dbg("End of ECircularService--->detailDataValidation");  
//       }catch(DBValidationException ex){
//            throw ex;
//       }catch(DBProcessingException ex){
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
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
        RequestBody<ECircular> circularBody = request.getReqBody();
        ECircular circular =circularBody.get();
        String l_operation=request.getReqHeader().getOperation();
        String l_service=request.getReqHeader().getService();
        String l_instituteID=circular.getInstituteID();
        String l_versioNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
         if(!(l_operation.equals("Create")||l_operation.equals("Create-Default")||l_operation.equals("View"))){
             
            if(l_operation.equals("AutoAuth")&&l_versioNumber.equals("1")){
                
                return null;
            }else{  
                
               dbg("inside business Service--->getExistingAudit--->Service--->ECircular");  
               
               String l_circularID=circular.getE_circularID();
               String[] l_pkey={l_circularID};
            
               DBRecord instituteRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular","INSTITUTE","IVW_E_CIRCULAR", l_pkey, session,dbSession);

               exAudit.setAuthStatus(instituteRecord.getRecord().get(10).trim());
               exAudit.setMakerID(instituteRecord.getRecord().get(5).trim());
               exAudit.setRecordStatus(instituteRecord.getRecord().get(9).trim());
               exAudit.setVersionNumber(Integer.parseInt(instituteRecord.getRecord().get(11).trim()));
            

 
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
