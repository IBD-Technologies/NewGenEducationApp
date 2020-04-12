/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.teachermaster;

import com.ibd.businessViews.ITeacherMasterService;
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
//@Local(ITeacherMasterService.class)
@Remote(ITeacherMasterService.class)
@Stateless
public class TeacherMasterService implements ITeacherMasterService {

    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public TeacherMasterService(){
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
       dbg("inside TeacherMasterService--->processing");
       dbg("TeacherMasterService--->Processing--->I/P--->requestJson"+requestJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,requestJson);
       bs.requestlogging(request,requestJson, inject,session,dbSession);
       buildBOfromReq(requestJson);  
       RequestBody<TeacherMaster> reqBody = request.getReqBody();
       TeacherMaster teacherMaster =reqBody.get();
       l_lockKey=teacherMaster.getTeacherID();
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<ITeacherMasterService>teacherMasterEJB=new BusinessEJB();
       teacherMasterEJB.set(this);
       exAudit=bs.getExistingAudit(teacherMasterEJB);
       
      if(!(bsv. businessServiceValidation(requestJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
      }
       if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");

       } 
      
      
       bs.businessServiceProcssing(request, exAudit, inject,teacherMasterEJB);
       

         if(l_session_created_now){
             jsonResponse= bs.buildSuccessResponse(requestJson, request, inject, session,teacherMasterEJB) ;
             tc.commit(session,dbSession);
             dbg("commit is called in teacher master service");
        }
       dbg("TeacherMasterService--->Processing--->O/P--->jsonResponse"+jsonResponse);     
       dbg("End of teacherMasterService--->processing");     
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
            dbg("Remote"+jsonResponse.toString());
            BSValidation bsv=inject.getBsv(session);
            //if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
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
       dbg("inside TeacherMasterService--->buildBOfromReq"); 
       dbg("TeacherMasterService--->buildBOfromReq--->I/P--->p_request"+p_request.toString()); 
       RequestBody<TeacherMaster> reqBody = new RequestBody<TeacherMaster>(); 
       TeacherMaster teacherMaster = new TeacherMaster();
       JsonObject l_body=p_request.getJsonObject("body");
       String l_operation=request.getReqHeader().getOperation();
       teacherMaster.setTeacherID(l_body.getString("teacherID"));
       if(!(l_operation.equals("View"))){
            teacherMaster.setTeacherName(l_body.getString("teacherName"));
            teacherMaster.setTeacherShortName(l_body.getString("shortName"));
        }
       reqBody.set(teacherMaster);
      request.setReqBody(reqBody);
      dbg("End of TeacherMasterService--->buildBOfromReq");
      }
        catch(Exception ex){
             dbg(ex);
            throw new BSProcessingException("BodyParsingException"+ex.toString());
        }
   }
    
   
     public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
        try{
        dbg("Inside TeacherMasterService--->create"); 
        RequestBody<TeacherMaster> reqBody = request.getReqBody();
        TeacherMaster teacherMaster =reqBody.get();
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
        String l_teacherID=teacherMaster.getTeacherID();
        String l_teacherName=teacherMaster.getTeacherName();
        String l_shortName=teacherMaster.getTeacherShortName();
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",21,l_teacherID,l_teacherName,l_shortName,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
       
      

        dbg("End of TeacherMasterService--->create");
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
            dbg("Inside TeacherMasterService--->update"); 
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            String l_instituteID=request.getReqHeader().getInstituteID();
            RequestBody<TeacherMaster> reqBody = request.getReqBody();
            TeacherMaster teacherMaster =reqBody.get();
            String l_teacherID=teacherMaster.getTeacherID();
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
            Map<String,String>  l_column_to_update=new HashMap();
            l_column_to_update.put("7", l_checkerID);
            l_column_to_update.put("9", l_checkerDateStamp);
            l_column_to_update.put("11", l_authStatus);
            l_column_to_update.put("14", l_checkerRemarks);
            
             String[] l_primaryKey={l_teacherID};
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_TEACHER_MASTER", l_primaryKey, l_column_to_update, session);

             dbg("End of TeacherMasterService--->update");          
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
        dbg("Inside TeacherMasterService--->fullUpdate");
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<TeacherMaster> reqBody = request.getReqBody();
        TeacherMaster teacherMaster =reqBody.get();
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
        
        String l_teacherID=teacherMaster.getTeacherID();
        String l_teacherName=teacherMaster.getTeacherName();
        String l_teacherShortName=teacherMaster.getTeacherShortName();
        
        l_column_to_update= new HashMap();
        l_column_to_update.put("2", l_teacherName);
//        l_column_to_update.put("3", l_standard);
//        l_column_to_update.put("4", l_section);
        l_column_to_update.put("3", l_teacherShortName);
        l_column_to_update.put("4", l_makerID);
        l_column_to_update.put("5", l_checkerID);
        l_column_to_update.put("6", l_makerDateStamp);
        l_column_to_update.put("7", l_checkerDateStamp);
        l_column_to_update.put("8", l_recordStatus);
        l_column_to_update.put("9", l_authStatus);
        l_column_to_update.put("10", l_versionNumber);
        l_column_to_update.put("11", l_makerRemarks);
        l_column_to_update.put("12", l_checkerRemarks);
        
        String[] l_primaryKey={l_teacherID};
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_TEACHER_MASTER", l_primaryKey, l_column_to_update, session);
                   
        dbg("end of TeacherMasterService--->fullUpdate");                
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
        dbg("Inside TeacherMasterService--->delete");  
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<TeacherMaster> reqBody = request.getReqBody();
        TeacherMaster teacherMaster =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();    
        String l_teacherID=teacherMaster.getTeacherID();

        String[] l_primaryKey={l_teacherID};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_TEACHER_MASTER", l_primaryKey, session);
                   
 
         dbg("End of TeacherMasterService--->delete");      
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
            dbg("Inside TeacherMasterService--->tableRead");
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<TeacherMaster> reqBody = request.getReqBody();
            String l_instituteID=request.getReqHeader().getInstituteID();
            TeacherMaster teacherMaster =reqBody.get();
            String l_teacherID=teacherMaster.getTeacherID();
            String[] l_pkey={l_teacherID};
            
            DBRecord dbrec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_TEACHER_MASTER", l_pkey, session,dbSession);
            buildBOfromDB(dbrec);
           
            
         
          dbg("end of  TeacherMasterService--->tableRead");               
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
      private void buildBOfromDB( DBRecord teacherMasterRec)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
            dbg("inside TeacherMasterService--->buildBOfromDB");
            
            ArrayList<String>p_teacherMasterList=teacherMasterRec.getRecord();
            RequestBody<TeacherMaster> reqBody = request.getReqBody();
            TeacherMaster teacherMaster =reqBody.get();
            if(p_teacherMasterList!=null&&!p_teacherMasterList.isEmpty()){
           
                   teacherMaster.setTeacherName(p_teacherMasterList.get(1).trim());
                   teacherMaster.setTeacherShortName(p_teacherMasterList.get(2).trim());
                   request.getReqAudit().setMakerID(p_teacherMasterList.get(3).trim());
                   request.getReqAudit().setCheckerID(p_teacherMasterList.get(4).trim());
                   request.getReqAudit().setMakerDateStamp(p_teacherMasterList.get(5).trim());
                   request.getReqAudit().setCheckerDateStamp(p_teacherMasterList.get(6).trim());
                   request.getReqAudit().setRecordStatus(p_teacherMasterList.get(7).trim());
                   request.getReqAudit().setAuthStatus(p_teacherMasterList.get(8).trim());
                   request.getReqAudit().setVersionNumber(p_teacherMasterList.get(9).trim());
                   request.getReqAudit().setMakerRemarks(p_teacherMasterList.get(10).trim());
                   request.getReqAudit().setCheckerRemarks(p_teacherMasterList.get(11).trim());
            
            }
            
            
       
        dbg("End of TeacherMasterService--->buildBOfromDB");
        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
 }
 
     public JsonObject buildJsonResFromBO()throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
         JsonObject body=null;
         try{
             dbg("inside buildJsonResFromBO");
            RequestBody<TeacherMaster> reqBody = request.getReqBody();
            TeacherMaster teacherMaster =reqBody.get();
            
             body=Json.createObjectBuilder().add("teacherID", teacherMaster.getTeacherID())
                                            .add("teacherName", teacherMaster.getTeacherName())
                                            .add("shortName", teacherMaster.getTeacherShortName())
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
       dbg("inside TeacherMasterService--->businessValidation");    
       String l_operation=request.getReqHeader().getOperation();
       String l_instituteID=request.getReqHeader().getInstituteID();
                     
       if(!masterMandatoryValidation(errhandler)){
           status=false;
       }
       if(!(l_operation.equals("View"))){
           
           if(!detailMandatoryValidation(errhandler)) {
               
               status=false;
           } else{
           if(!detailDataValidation(errhandler,l_instituteID)){
               
               status=false;
           }
           
           }
       
       }

       dbg("TeacherMasterService--->businessValidation--->O/P--->status"+status);
       dbg("End of TeacherMasterService--->businessValidation");
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
        dbg("inside TeacherMasterService--->masterMandatoryValidation");
        RequestBody<TeacherMaster> reqBody = request.getReqBody();
        TeacherMaster teacher =reqBody.get();
         
         if(teacher.getTeacherID()==null||teacher.getTeacherID().isEmpty())
         {   
            status=false;
            dbg("status false in teacherID");
            errhandler.log_app_error("BS_VAL_002","teacherID");
         }
         
         
         dbg("TeacherMasterService--->masterMandatoryValidation--->O/P--->status"+status);
         dbg("End of TeacherMasterService--->masterMandatoryValidation");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
     private boolean detailMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
        boolean status=true;
         try{
             dbg("inside TeacherMasterService--->detailMandatoryValidation");
            RequestBody<TeacherMaster> reqBody = request.getReqBody();
            TeacherMaster teacher =reqBody.get();
        
           if(teacher.getTeacherName()==null||teacher.getTeacherName().isEmpty()){
               
             status=false;  
             errhandler.log_app_error("BS_VAL_002","teacherName");  
           }
 
//          if(teacher.getStandard()==null||teacher.getStandard().isEmpty()){
//               
//             status=false;  
//             errhandler.log_app_error("BS_VAL_002","standard");  
//           }
//      
//         if(teacher.getSection()==null||teacher.getSection().isEmpty()){
//               
//             status=false;  
//             errhandler.log_app_error("BS_VAL_002","section");  
//           }
//          if(teacher.getTeacherShortName()==null||teacher.getTeacherShortName().isEmpty()){
//               
//             status=false;  
//             errhandler.log_app_error("BS_VAL_002","shortName");  
//           }
        
        dbg("TeacherMasterService--->detailMandatoryValidation--->O/P--->status"+status);
        dbg("End of TeacherMasterService--->detailMandatoryValidation");
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
  
  private boolean detailDataValidation(ErrorHandler errhandler,String p_instituteID)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
     try{
         dbg("inside TeacherMasterService--->detailDataValidation");   
         dbg("inside TeacherMasterService--->detailDataValidation--->I/P--->p_instituteID"+p_instituteID);
         BSValidation bsv=inject.getBsv(session);
         String l_instituteID=request.getReqHeader().getInstituteID();
         RequestBody<TeacherMaster> reqBody = request.getReqBody();
         TeacherMaster teacher =reqBody.get();
//         String l_standard=teacher.getStandard();
//         String l_section=teacher.getSection();
//             
//             if(!bsv.standardSectionValidation(l_standard,l_section, l_instituteID, session, dbSession, inject)){
//                 status=false;
//                 errhandler.log_app_error("BS_VAL_003","standard or section");
//             }
             

        dbg("TeacherMasterService--->detailDataValidation--->O/P--->status"+status);
        dbg("End of TeacherMasterService--->detailDataValidation");  
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
        String l_operation=request.getReqHeader().getOperation();
        String l_service=request.getReqHeader().getService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_versioNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
        if(!(l_operation.equals("Create"))){
             
            if(l_operation.equals("AutoAuth")&&l_versioNumber.equals("1")){
                
                return null;
            }else{  
                
               dbg("inside business Service--->getExistingAudit--->Service--->TeacherMaster");  
               RequestBody<TeacherMaster> teacherMasterBody = request.getReqBody();
               TeacherMaster teacherMaster =teacherMasterBody.get();
               String l_teacherID=teacherMaster.getTeacherID();
               dbg("teacherID"+l_teacherID);
               String[] l_teacherMasterpkey={l_teacherID};
               DBRecord teacherMasterRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_TEACHER_MASTER", l_teacherMasterpkey, session,dbSession);

               exAudit.setAuthStatus(teacherMasterRec.getRecord().get(8).trim());
               exAudit.setMakerID(teacherMasterRec.getRecord().get(3).trim());
               exAudit.setRecordStatus(teacherMasterRec.getRecord().get(7).trim());
               exAudit.setVersionNumber(Integer.parseInt(teacherMasterRec.getRecord().get(9).trim()));
            

 
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
  
      
     public void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
         
         return;
         
     }
     
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
}
