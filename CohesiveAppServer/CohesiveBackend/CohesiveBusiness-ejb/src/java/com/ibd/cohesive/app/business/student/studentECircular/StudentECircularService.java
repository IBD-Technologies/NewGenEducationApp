/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentECircular;

import com.ibd.businessViews.IStudentECircularService;
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
@Remote(IStudentECircularService.class)
@Stateless
public class StudentECircularService implements IStudentECircularService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentECircularService(){
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
       dbg("inside StudentECircularService--->processing");
       dbg("StudentECircularService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<StudentECircular> reqBody = request.getReqBody();
       StudentECircular studentECircular =reqBody.get();
       
       l_lockKey=studentECircular.geteCircularID()+"~"+studentECircular.getStudentID();
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       
       BusinessEJB<IStudentECircularService>studentECircularEJB=new BusinessEJB();
       studentECircularEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentECircularEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
        

      
       bs.businessServiceProcssing(request, exAudit, inject,studentECircularEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentECircularEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student e circular");
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
            bs=inject.getBusinessService(session);
            dbg("before calling remove businessLock");
            
            if(l_lockKey!=null){
               businessLock.removeBusinessLock(request, l_lockKey, session);
            }
            request=null;
            if(l_session_created_now){
                bs.responselogging(jsonResponse, inject,session,dbSession);
            clonedResponse=bs.cloneResponseJsonObject(jsonResponse);  
            dbg("Response"+jsonResponse.toString());
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
      StudentECircular studentECircular=new StudentECircular();
      RequestBody<StudentECircular> reqBody = new RequestBody<StudentECircular>(); 
           
      try{
      dbg("inside student eCircular buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      studentECircular.setStudentID(l_body.getString("studentID"));
      studentECircular.setStudentName(l_body.getString("studentName"));
      
      studentECircular.seteCircularID(l_body.getString("circularID"));
      if(!l_operation.equals("View")){
          
          studentECircular.seteCircularDescription(l_body.getString("circularDescription"));
          studentECircular.setContentPath(l_body.getString("contentPath"));
          studentECircular.setCircularDate(l_body.getString("circularDate"));
          studentECircular.setCircularType(l_body.getString("circularType"));
          studentECircular.setMessage(l_body.getString("message"));
      }
        reqBody.set(studentECircular);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
    try{ 
        dbg("inside stident eCircular create"); 
        RequestBody<StudentECircular> reqBody = request.getReqBody();
        StudentECircular studentECircular =reqBody.get();
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IPDataService pds=inject.getPdataservice();
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
        String l_studentID=studentECircular.getStudentID();
        String l_eCircularID=studentECircular.geteCircularID();
//        String l_eCircularDescription=studentECircular.geteCircularDescription();
//        String l_contentPath=studentECircular.getContentPath();
//        String l_url=studentECircular.getUrl();
        
       String signatureStatus="Y";
                 
                 dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_eCircularID,"INSTITUTE",352,l_studentID,signatureStatus,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);

        
                 Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("3", "Y");
         String[] l_pkey={l_eCircularID};
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular","STUDENT", "SVW_STUDENT_E_CIRCULAR", l_pkey, l_column_to_update, session);
                 
                 
        
        dbg("end of student eCircular create"); 
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
        dbg("inside student eCircular--->auth update"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<StudentECircular> reqBody = request.getReqBody();
        StudentECircular studentECircular =reqBody.get();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_studentID=studentECircular.getStudentID();
        String l_eCircularID=studentECircular.geteCircularID();
        String[] l_primaryKey={l_eCircularID};
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("6", l_checkerID);
         l_column_to_update.put("8", l_checkerDateStamp);
         l_column_to_update.put("10", l_authStatus);
         l_column_to_update.put("13", l_checkerRemarks);
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_E_CIRCULAR", l_primaryKey, l_column_to_update, session);
         dbg("end of student eCircular--->auth update");          
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
        
//       Map<String,String> l_column_to_update;
//                
//       try{ 
//        IDBTransactionService dbts=inject.getDBTransactionService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        RequestBody<StudentECircular> reqBody = request.getReqBody();
//        StudentECircular studentECircular =reqBody.get();
//        String l_instituteID=request.getReqHeader().getInstituteID();   
//        String l_makerID=request.getReqAudit().getMakerID();
//        String l_checkerID=request.getReqAudit().getCheckerID();
//        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
//        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
//        String l_recordStatus=request.getReqAudit().getRecordStatus();
//        String l_authStatus=request.getReqAudit().getAuthStatus();
//        String l_versionNumber=request.getReqAudit().getVersionNumber();
//        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
//        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();   
//        String l_studentID=studentECircular.getStudentID();
//        String l_eCircularID=studentECircular.geteCircularID();
//        String l_eCircularDescription=studentECircular.geteCircularDescription();
//        String l_contentPath=studentECircular.getContentPath();
//        String l_url=studentECircular.getUrl();
//                        l_column_to_update= new HashMap();
//                        l_column_to_update.put("1", l_studentID);
//                        l_column_to_update.put("2", l_eCircularID);
//                        l_column_to_update.put("3", l_eCircularDescription);
//                        l_column_to_update.put("4", l_contentPath);
//                        l_column_to_update.put("5", l_makerID);
//                        l_column_to_update.put("6", l_checkerID);
//                        l_column_to_update.put("7", l_makerDateStamp);
//                        l_column_to_update.put("8", l_checkerDateStamp);
//                        l_column_to_update.put("9", l_recordStatus);
//                        l_column_to_update.put("10", l_authStatus);
//                        l_column_to_update.put("11", l_versionNumber);
//                        l_column_to_update.put("12", l_makerRemarks);
//                        l_column_to_update.put("13", l_checkerRemarks);
//                        l_column_to_update.put("14", l_url);
//         
//                        String[] l_primaryKey={l_eCircularID};
//                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_E_CIRCULAR", l_primaryKey, l_column_to_update, session);   
//                  
//        
//        
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//            
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception".concat(ex.toString()));
//        }
    }

    
    public void delete()throws DBValidationException,DBProcessingException,BSProcessingException{
        try{
        dbg("inside student eCircular delete");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentECircular> reqBody = request.getReqBody();
        StudentECircular studentECircular =reqBody.get();
        String l_studentID=studentECircular.getStudentID();
        String l_eCircularID=studentECircular.geteCircularID();
             
             String[] l_primaryKey={l_eCircularID};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_E_CIRCULAR", l_primaryKey, session);
            dbg("end of student eCircular delete");
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

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student eCircular--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentECircular> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        StudentECircular studentECircular =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_studentID=studentECircular.getStudentID();
        String l_eCircularID=studentECircular.geteCircularID();
        String[] l_pkey={l_eCircularID};
        DBRecord eCircularRec=null;
        
        
        try{
        
         eCircularRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular","STUDENT", "SVW_STUDENT_E_CIRCULAR", l_pkey, session, dbSession);
       
        
        }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", l_eCircularID);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
        
        
        
        buildBOfromDB(eCircularRec);
        
          dbg("end of  completed student eCircular--->view");  
          }catch(BSValidationException ex){
            throw ex;
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
      private void buildBOfromDB(DBRecord p_studentECircularRecord)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<StudentECircular> reqBody = request.getReqBody();
           StudentECircular studentECircular =reqBody.get();
           ArrayList<String>l_studentECircularList= p_studentECircularRecord.getRecord();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           String l_instituteID=request.getReqHeader().getInstituteID();
           String l_circularID=studentECircular.geteCircularID();
           BusinessService bs=inject.getBusinessService(session);
           String[] l_primaryKey={l_circularID};
           String l_studentID=studentECircular.getStudentID();
           
           if(l_studentECircularList!=null&&!l_studentECircularList.isEmpty()){
               
           DBRecord circularRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular","INSTITUTE","IVW_E_CIRCULAR", l_primaryKey, session,dbSession);   
               
           studentECircular.setStudentName(bs.getInstituteName(l_instituteID, session, dbSession, inject));
           studentECircular.seteCircularDescription(circularRecord.getRecord().get(3).trim()); 
           studentECircular.setContentPath(circularRecord.getRecord().get(4).trim());
           studentECircular.setMessage(circularRecord.getRecord().get(14).trim());
           studentECircular.setCircularDate(circularRecord.getRecord().get(15).trim());
           studentECircular.setCircularType(circularRecord.getRecord().get(16).trim());    
           boolean recordExistence=false;
            DBRecord signRecord=null;
            String[] l_pkey={l_studentID};
            try{
                 signRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_circularID,"INSTITUTE", "IVW_STUDENT_E_CIRCULAR_SIGNATURE", l_pkey, session, dbSession);
                
                
                recordExistence=true;
            }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                    }else{
                        
                        throw ex;
                    }
            }
            
            if(recordExistence){
                studentECircular.setSignStatus("Y");
                request.getReqAudit().setMakerID(signRecord.getRecord().get(2).trim());
                request.getReqAudit().setCheckerID(signRecord.getRecord().get(3).trim());
                request.getReqAudit().setMakerDateStamp(signRecord.getRecord().get(4).trim());
                request.getReqAudit().setCheckerDateStamp(signRecord.getRecord().get(5).trim());
                request.getReqAudit().setRecordStatus(signRecord.getRecord().get(6).trim());
                request.getReqAudit().setAuthStatus(signRecord.getRecord().get(7).trim());
                request.getReqAudit().setVersionNumber(signRecord.getRecord().get(8).trim());
                request.getReqAudit().setMakerRemarks(signRecord.getRecord().get(9).trim());
                request.getReqAudit().setCheckerRemarks(signRecord.getRecord().get(10).trim());
                
                
                
            }else{
            
                studentECircular.setSignStatus("N");
                request.getReqAudit().setMakerID("System");
                request.getReqAudit().setCheckerID("System");
                request.getReqAudit().setMakerDateStamp("");
                request.getReqAudit().setCheckerDateStamp("");
                request.getReqAudit().setRecordStatus("O");
                request.getReqAudit().setAuthStatus("A");
                request.getReqAudit().setVersionNumber("1");
                request.getReqAudit().setMakerRemarks("");
                request.getReqAudit().setCheckerRemarks("");
                
            }
                
               
//            request.getReqAudit().setMakerID(l_studentECircularList.get(3).trim());
//            request.getReqAudit().setCheckerID(l_studentECircularList.get(4).trim());
//            request.getReqAudit().setMakerDateStamp(l_studentECircularList.get(5).trim());
//            request.getReqAudit().setCheckerDateStamp(l_studentECircularList.get(6).trim());
//            request.getReqAudit().setRecordStatus(l_studentECircularList.get(7).trim());
//            request.getReqAudit().setAuthStatus(l_studentECircularList.get(8).trim());
//            request.getReqAudit().setVersionNumber(l_studentECircularList.get(9).trim());
//            request.getReqAudit().setMakerRemarks(l_studentECircularList.get(10).trim());
//            request.getReqAudit().setCheckerRemarks(l_studentECircularList.get(11).trim());
//            studentECircular.setUrl(l_studentECircularList.get(13).trim());
            }
            
          dbg("end of  buildBOfromDB"); 
        
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
 }
    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside student eCircular buildJsonResFromBO");    
        RequestBody<StudentECircular> reqBody = request.getReqBody();
        StudentECircular studentECircular =reqBody.get();
        body=Json.createObjectBuilder().add("studentID", studentECircular.getStudentID())
                                       .add("studentName", studentECircular.getStudentName())
                                       .add("circularID",studentECircular.geteCircularID())
                                       .add("circularDescription", studentECircular.geteCircularDescription())
                                       .add("contentPath", studentECircular.getContentPath())
                                       .add("circularDate", studentECircular.getCircularDate())
                                       .add("circularType", studentECircular.getCircularType())
                                       .add("message", studentECircular.getMessage())
                                      .add("signStatus", studentECircular.getSignStatus())
//                                       .add("url", studentECircular.getUrl())
                                       .build();
                                            
              dbg(body.toString());  
           dbg("end of student eCircular buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student eCircular--->businessValidation");    
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
       dbg("end of student eCircular--->businessValidation"); 
       }catch(BSProcessingException ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
            
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException(ex.toString());    
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
        dbg("inside student eCircular master mandatory validation");
        RequestBody<StudentECircular> reqBody = request.getReqBody();
        StudentECircular studentECircular =reqBody.get();
        
         if(studentECircular.getStudentID()==null||studentECircular.getStudentID().equals("")){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","studentID");  
         }
          if(studentECircular.geteCircularID()==null||studentECircular.geteCircularID().equals("")){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","CircularID");  
         }
          
          
        dbg("end of student eCircular master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student eCircular masterDataValidation");
             RequestBody<StudentECircular> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             StudentECircular studentECircular =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_studentID=studentECircular.getStudentID();
             
             if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","studentID");
             }
             
            dbg("end of student eCircular masterDataValidation");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        
        return status;
              
    }
    
    private boolean detailMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBProcessingException,DBValidationException{
        boolean status=true;
       
        try{
            dbg("inside student eCircular detailMandatoryValidation");
            RequestBody<StudentECircular> reqBody = request.getReqBody();
            StudentECircular studentECircular =reqBody.get();
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            String l_eCircularID=studentECircular.geteCircularID();
            String l_instituteID=request.getReqHeader().getInstituteID();
            String l_studentID=studentECircular.getStudentID();
             boolean signatureStatus=false;
             String[] l_pkey={l_studentID};
            try{
             
             DBRecord studentRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_eCircularID,"INSTITUTE", "IVW_STUDENT_E_CIRCULAR_SIGNATURE", l_pkey, session, dbSession);
             String previousStatus=studentRec.getRecord().get(1).trim();
             dbg("previousStatus"+previousStatus);
             if(previousStatus.equals("Y")){
                 
                 signatureStatus=true;
             }
             
             }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        signatureStatus=false;
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
//                        session.getErrorhandler().log_app_error("BS_VAL_013",l_studentID+l_exam);
//                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
             
             if(signatureStatus){
                 
                 session.getErrorhandler().log_app_error("BS_VAL_062",null);
                        throw new BSValidationException("BSValidationException");
                 
                 
             }
            
            
            
            
           dbg("end of student eCircular detailMandatoryValidation");  
         } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch(DBValidationException ex){
            throw ex;   
        } catch(BSValidationException ex){
            throw ex;   
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         return true;
              
    }
    public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     try{
        dbg("inside StudentECircular--->getExistingAudit") ;
        exAudit=new ExistingAudit();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_operation=request.getReqHeader().getOperation();
        String l_service=request.getReqHeader().getService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
        if(!(l_operation.equals("Create")||l_operation.equals("View"))){
             
              if(l_operation.equals("AutoAuth")&&l_versionNumber.equals("1")){
                return null;
              }else{  
               dbg("inside StudentECircular--->getExistingAudit--->Service--->UserECircular");  
               RequestBody<StudentECircular> studentECircularBody = request.getReqBody();
               StudentECircular studentECircular =studentECircularBody.get();
               String l_eCircularID=studentECircular.geteCircularID();
               String l_studentID=studentECircular.getStudentID();
               String[] l_pkey={l_studentID};
//               DBRecord l_studentECircularRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_E_CIRCULAR", l_pkey, session, dbSession);
               
               boolean recordExistence=false;

           DBRecord signRecord=null;
            try{
                
                
                signRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_eCircularID,"INSTITUTE", "IVW_STUDENT_E_CIRCULAR_SIGNATURE", l_pkey, session, dbSession);
                
                recordExistence=true;
            }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                    }else{
                        
                        throw ex;
                    }
            }
               

             if(recordExistence){   
               
               exAudit.setAuthStatus(signRecord.getRecord().get(7).trim());
               exAudit.setMakerID(signRecord.getRecord().get(2).trim());
               exAudit.setRecordStatus(signRecord.getRecord().get(6).trim());
               exAudit.setVersionNumber(Integer.parseInt(signRecord.getRecord().get(8).trim()));
            }else{
                
               exAudit.setAuthStatus("A");
               exAudit.setMakerID("System");
               exAudit.setRecordStatus("O");
               exAudit.setVersionNumber(1);
                
            }

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of StudentECircular--->getExistingAudit") ;
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
    
    public  void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     
 }
    
 
   
 
 public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }   
}
