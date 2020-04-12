/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentfeemanagement;

import com.ibd.businessViews.IStudentFeeManagementService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.ConvertedDate;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
//@Local(IStudentFeeManagementService.class)
@Remote(IStudentFeeManagementService.class)
@Stateless
 public class StudentFeeManagementService implements IStudentFeeManagementService{

    
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentFeeManagementService(){
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
       dbg("inside StudentFeeManagementService--->processing");
       dbg("StudentFeeManagementService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<StudentFeeManagement> reqBody = request.getReqBody();
       StudentFeeManagement feeManagement =reqBody.get();
       l_lockKey=feeManagement.getFeeID()+"~"+feeManagement.getStudentID();
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<IStudentFeeManagementService>studentFeeManagementEJB=new BusinessEJB();
       studentFeeManagementEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentFeeManagementEJB);
       if(request.getReqHeader().getOperation().equals("View")){
           
         String studentID=  bs.studentValidation(feeManagement.getStudentID(), feeManagement.getStudentName(), request.getReqHeader().getInstituteID(), session, dbSession, inject);
         
          
         if(studentID==null||studentID.isEmpty()){
             
             errhandler.log_app_error("BS_VAL_002","Student ID or Name");  
             throw new BSValidationException("BSValidationException");
         }
         
         feeManagement.setStudentID(studentID);
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
      
       bs.businessServiceProcssing(request, exAudit, inject,studentFeeManagementEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentFeeManagementEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student feeManagement");
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
      StudentFeeManagement studentFeeManagement=new StudentFeeManagement();
      RequestBody<StudentFeeManagement> reqBody = new RequestBody<StudentFeeManagement>(); 
           
      try{
      dbg("inside student feeManagement buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      studentFeeManagement.setStudentID(l_body.getString("studentID"));
      studentFeeManagement.setStudentName(l_body.getString("studentName"));
      studentFeeManagement.setFeeID(l_body.getString("feeID"));
//      studentFeeManagement.setFeeType(l_body.getString("feeType"));
      
      if(!l_operation.equals("View")){
          studentFeeManagement.setAmount(l_body.getString("amount"));
          studentFeeManagement.setDueDate(l_body.getString("dueDate"));
          studentFeeManagement.setStatus(l_body.getString("status"));
          studentFeeManagement.setFeePaid(l_body.getString("feePaid"));
          studentFeeManagement.setOutStanding(l_body.getString("outStanding"));
          studentFeeManagement.setPaidDate(l_body.getString("paidDate"));
          
      }
        reqBody.set(studentFeeManagement);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
    try{ 
        dbg("inside stident feeManagement create"); 
        RequestBody<StudentFeeManagement> reqBody = request.getReqBody();
        StudentFeeManagement feeManagement =reqBody.get();
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
        String l_studentID=feeManagement.getStudentID();
        String l_feeID=feeManagement.getFeeID();
        String l_feeType=feeManagement.getFeeID();
        String l_amount=feeManagement.getAmount();
        String l_dueDate=feeManagement.getDueDate();
        String l_status=feeManagement.getStatus();
        String l_feePaid=feeManagement.getFeePaid();
        String l_outStanding=feeManagement.getOutStanding();
        String l_paidDate=feeManagement.getPaidDate();
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",63,l_studentID,l_feeID,l_feeType,l_amount,l_dueDate,l_status,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks,l_feePaid,l_outStanding,l_paidDate);

        
        
        dbg("end of student feeManagement create"); 
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
        dbg("inside student feeManagement--->full update"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<StudentFeeManagement> reqBody = request.getReqBody();
        StudentFeeManagement studentFeeManagement =reqBody.get();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_studentID=studentFeeManagement.getStudentID();   
        String l_feeID=studentFeeManagement.getFeeID();
        
        String[] l_primaryKey={l_feeID};
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("8", l_checkerID);
         l_column_to_update.put("10", l_checkerDateStamp);
         l_column_to_update.put("12", l_authStatus);
         l_column_to_update.put("15", l_checkerRemarks);
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_FEE_MANAGEMENT",l_primaryKey,l_column_to_update,session);
         dbg("end of student feeManagement--->auth update");          
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
        dbg("inside student feeManagement--->full update");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentFeeManagement> reqBody = request.getReqBody();
        StudentFeeManagement studentFeeManagement =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();   
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();   
        String l_studentID=studentFeeManagement.getStudentID(); 
        String l_feeID=studentFeeManagement.getFeeID(); 
        String l_feeType=studentFeeManagement.getFeeID();
        String l_amount=studentFeeManagement.getAmount();
        String l_dueDate=studentFeeManagement.getDueDate();
        String l_status=studentFeeManagement.getStatus();
        String l_feePaid=studentFeeManagement.getFeePaid();
        String l_outStanding=studentFeeManagement.getOutStanding();
        String l_paidDate=studentFeeManagement.getPaidDate();
        l_column_to_update= new HashMap();
        l_column_to_update.put("3", l_feeType);
        l_column_to_update.put("4", l_amount);
        l_column_to_update.put("5", l_dueDate);
        l_column_to_update.put("6", l_status);
        l_column_to_update.put("7", l_makerID);
        l_column_to_update.put("8", l_checkerID);
        l_column_to_update.put("9", l_makerDateStamp);
        l_column_to_update.put("10", l_checkerDateStamp);
        l_column_to_update.put("11", l_recordStatus);
        l_column_to_update.put("12", l_authStatus);
        l_column_to_update.put("14", l_makerRemarks);
        l_column_to_update.put("15", l_checkerRemarks);
        l_column_to_update.put("16", l_feePaid);
        l_column_to_update.put("17", l_outStanding);
        l_column_to_update.put("18", l_paidDate);
        String[] l_primaryKey={l_feeID};
        
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_FEE_MANAGEMENT",l_primaryKey,l_column_to_update,session);
                  
        
        
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

    
    public void delete()throws DBValidationException,DBProcessingException,BSProcessingException{
        try{
        dbg("inside student feeManagement delete");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentFeeManagement> reqBody = request.getReqBody();
        StudentFeeManagement studentFeeManagement =reqBody.get();
        String l_studentID=studentFeeManagement.getStudentID();
        String l_feeID=studentFeeManagement.getFeeID();
             
             String[] l_primaryKey={l_feeID};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_FEE_MANAGEMENT", l_primaryKey, session);
            dbg("end of student feeManagement delete");
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
        dbg("inside student feeManagement--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentFeeManagement> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        StudentFeeManagement studentFeeManagement =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_studentID=studentFeeManagement.getStudentID();
        String l_feeID=studentFeeManagement.getFeeID();
        String[] l_pkey={l_feeID};
        DBRecord feeManagementRec=null;
        
        try{
        
        
         feeManagementRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"FEE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","FEE", "STUDENT_FEE_MANAGEMENT", l_pkey, session, dbSession);
       
        }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", l_feeID);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
                }
        
        
        
        buildBOfromDB(feeManagementRec);
        
          dbg("end of  completed student feeManagement--->view");  
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
      private void buildBOfromDB(DBRecord p_studentFeeManagementRecord)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<StudentFeeManagement> reqBody = request.getReqBody();
           String l_instituteID=request.getReqHeader().getInstituteID();
           StudentFeeManagement studentFeeManagement =reqBody.get();
           ArrayList<String>l_studentFeeManagementList= p_studentFeeManagementRecord.getRecord();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           BSValidation bsv=inject.getBsv(session);
           String l_feeID=studentFeeManagement.getFeeID();
           String l_studentID=studentFeeManagement.getStudentID();
           BusinessService bs=inject.getBusinessService(session);
           
           if(l_studentFeeManagementList!=null&&!l_studentFeeManagementList.isEmpty()){
            
               String[] l_pkey={l_feeID};
               DBRecord feeManagementRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", l_pkey, session,dbSession);
               
               
            studentFeeManagement.setStudentName(bs.getStudentName(l_studentID,l_instituteID, session, dbSession, inject));
            studentFeeManagement.setAmount(feeManagementRecord.getRecord().get(5).trim());
            studentFeeManagement.setDueDate(feeManagementRecord.getRecord().get(6).trim());
            String dueDate=studentFeeManagement.getDueDate();
            float feeAmount=Float.parseFloat(studentFeeManagement.getAmount());
            float totalPaymentAmount=0;
            String paymentDate=new String();
            String status="P";
            float balanceAmount=0;
            Map<String,DBRecord>paymentMap=null;
            
            try{
        
        
             paymentMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT", "STUDENT_PAYMENT", session, dbSession);

            }catch(DBValidationException ex){
                        dbg("exception in view operation"+ex);
                        if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                            session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                            session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
//                            session.getErrorhandler().log_app_error("BS_VAL_013", l_feeID);
//                            throw new BSValidationException("BSValidationException");

                        }else{

                            throw ex;
                        }
                    }
            
            Date maxDate;
            if(paymentMap!=null){
            
            
                List<DBRecord>filteredRecords=paymentMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals(l_feeID)).collect(Collectors.toList());
                ArrayList<Date>paymentDateList=new ArrayList();

                for(int i=0;i<filteredRecords.size();i++){

                    float paidAmount=Float.parseFloat(filteredRecords.get(i).getRecord().get(4).trim());

                    totalPaymentAmount=totalPaymentAmount+paidAmount;
                    String paidDate=filteredRecords.get(i).getRecord().get(2).trim();
                    String dateformat=i_db_properties.getProperty("DATE_FORMAT");
                    SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
                    paymentDateList.add(formatter.parse(paidDate));
                }
            
                
                 maxDate = paymentDateList.stream().map(rec-> rec).max(Date::compareTo).get();
                 paymentDate=maxDate.toString();
               }

            
            
                balanceAmount=feeAmount-totalPaymentAmount;


                if(balanceAmount==0){

                    status="C";
                }else{


                    if(!bsv.pastDateValidation(dueDate, session, dbSession, inject)){

                        status="O";

                    }

                }
               

                balanceAmount=feeAmount-totalPaymentAmount;
                
                studentFeeManagement.setFeePaid(Float.toString(totalPaymentAmount));
                studentFeeManagement.setOutStanding(Float.toString(balanceAmount));
                studentFeeManagement.setStatus(status);
                studentFeeManagement.setPaidDate(paymentDate);
                request.getReqAudit().setMakerID(feeManagementRecord.getRecord().get(7).trim());
                request.getReqAudit().setCheckerID(feeManagementRecord.getRecord().get(8).trim());
                request.getReqAudit().setMakerDateStamp(feeManagementRecord.getRecord().get(9).trim());
                request.getReqAudit().setCheckerDateStamp(feeManagementRecord.getRecord().get(10).trim());
                request.getReqAudit().setRecordStatus(feeManagementRecord.getRecord().get(11).trim());
                request.getReqAudit().setAuthStatus(feeManagementRecord.getRecord().get(12).trim());
                request.getReqAudit().setVersionNumber(feeManagementRecord.getRecord().get(13).trim());
                request.getReqAudit().setMakerRemarks(feeManagementRecord.getRecord().get(14).trim());
                request.getReqAudit().setCheckerRemarks(feeManagementRecord.getRecord().get(15).trim());
//                studentFeeManagement.setFeePaid(l_studentFeeManagementList.get(15).trim());
//                studentFeeManagement.setOutStanding(l_studentFeeManagementList.get(16).trim());
//                studentFeeManagement.setPaidDate(l_studentFeeManagementList.get(17).trim());
            
            
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
        dbg("inside student feeManagement buildJsonResFromBO");    
        RequestBody<StudentFeeManagement> reqBody = request.getReqBody();
        StudentFeeManagement studentFeeManagement =reqBody.get();
        
        dbg("studentID"+studentFeeManagement.getStudentID());
        dbg("studentName"+studentFeeManagement.getStudentName());
        dbg("feeID"+studentFeeManagement.getFeeID());
        dbg("amount"+studentFeeManagement.getAmount());
        dbg("status"+studentFeeManagement.getStatus());
        dbg("feePaid"+studentFeeManagement.getFeePaid());
        dbg("outStanding"+studentFeeManagement.getOutStanding());
        dbg("paidDate"+studentFeeManagement.getPaidDate());
        
        
        body=Json.createObjectBuilder().add("studentID",  studentFeeManagement.getStudentID())
                                       .add("studentName",  studentFeeManagement.getStudentName())
                                       .add("feeID", studentFeeManagement.getFeeID())
                                       .add("amount", studentFeeManagement.getAmount())
                                       .add("dueDate", studentFeeManagement.getDueDate())
                                       .add("status", studentFeeManagement.getStatus())
                                       .add("feePaid", studentFeeManagement.getFeePaid())
                                       .add("outStanding", studentFeeManagement.getOutStanding())
                                       .add("paidDate", studentFeeManagement.getPaidDate())
                                       .build();
                                            
              dbg(body.toString());  
           dbg("end of student feeManagement buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student feeManagement--->businessValidation");    
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
       dbg("end of student feeManagement--->businessValidation"); 
       }catch(BSProcessingException ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
            
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
        dbg("inside student feeManagement master mandatory validation");
        RequestBody<StudentFeeManagement> reqBody = request.getReqBody();
        StudentFeeManagement studentFeeManagement =reqBody.get();
        
         if(studentFeeManagement.getStudentID()==null||studentFeeManagement.getStudentID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","studentID");  
         }
          if(studentFeeManagement.getFeeID()==null||studentFeeManagement.getFeeID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","feeID");  
         }
//          if(studentFeeManagement.getFeeType()==null||studentFeeManagement.getFeeType().isEmpty()){
//             status=false;  
//             errhandler.log_app_error("BS_VAL_002","feeType");  
//         }
          
        dbg("end of student feeManagement master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student feeManagement masterDataValidation");
             RequestBody<StudentFeeManagement> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             StudentFeeManagement studentFeeManagement =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_studentID=studentFeeManagement.getStudentID();
//             String l_feeType=studentFeeManagement.getFeeType();
             
             if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","studentID");
             }
//             if(!bsv.feeTypeValidation(l_feeType, l_instituteID, session, dbSession, inject)){
//                 status=false;
//                 errhandler.log_app_error("BS_VAL_003","feeType");
//             }
            
            dbg("end of student feeManagement masterDataValidation status"+status);
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
    
    private boolean detailMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
        boolean status=true;
        RequestBody<StudentFeeManagement> reqBody = request.getReqBody();
        StudentFeeManagement studentFeeManagement =reqBody.get();
        
        try{
            
            dbg("inside student feeManagement detailMandatoryValidation");
           
            
            if(studentFeeManagement.getAmount()==null||studentFeeManagement.getAmount().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","Amount");  
            }
            
            dbg("status after amount"+status);
            if(studentFeeManagement.getDueDate()==null||studentFeeManagement.getDueDate().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","Due Date");  
            }
            dbg("status after duedate"+status);
            if(studentFeeManagement.getStatus()==null||studentFeeManagement.getStatus().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","Status");  
            }
           dbg("status after Status"+status);
            
            
           dbg("end of student feeManagement detailMandatoryValidation"+status);        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student feeManagement detailDataValidation"+status);
             RequestBody<StudentFeeManagement> reqBody = request.getReqBody();
             StudentFeeManagement studentFeeManagement =reqBody.get();
             BSValidation bsv=inject.getBsv(session);
             String l_dueDate=studentFeeManagement.getDueDate();
             String l_amount=studentFeeManagement.getAmount();
             String l_status=studentFeeManagement.getStatus();
            
             if(!bsv.dateFormatValidation(l_dueDate, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Due Date");
             }
             dbg("Status"+status);
             if(!bsv.amountValidation(l_amount, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Amount");
             }
             dbg("Status"+status);
             if(!bsv.feeStatusValidation(l_status, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","FeeStatus");
             }
            dbg("Status"+status);
            dbg("end of student feeManagement detailDataValidation"+status);
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
    public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     try{
        dbg("inside StudentFeeManagement--->getExistingAudit") ;
        exAudit= new ExistingAudit();
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
               dbg("inside StudentFeeManagement--->getExistingAudit--->Service--->UserFeeManagement");  
               RequestBody<StudentFeeManagement> studentFeeManagementBody = request.getReqBody();
               StudentFeeManagement studentFeeManagement =studentFeeManagementBody.get();
               String l_studentID=studentFeeManagement.getStudentID();
               String l_feeID=studentFeeManagement.getFeeID();
               String[] l_pkey={l_feeID};
               DBRecord l_studentFeeManagementRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_FEE_MANAGEMENT", l_pkey, session, dbSession);
               exAudit.setAuthStatus(l_studentFeeManagementRecord.getRecord().get(11).trim());
               exAudit.setMakerID(l_studentFeeManagementRecord.getRecord().get(6).trim());
               exAudit.setRecordStatus(l_studentFeeManagementRecord.getRecord().get(10).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_studentFeeManagementRecord.getRecord().get(12).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of StudentFeeManagement--->getExistingAudit") ;
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
    
      public  void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     
     try{
        
        dbg("inside relationshipProcessing") ;
        
        
        if(exAudit.getRelatioshipOperation().equals("C")){

             createOrUpdateStudentCalender();
        }else if(exAudit.getRelatioshipOperation().equals("M")){
            
             createOrUpdateStudentCalender();
        }else if(exAudit.getRelatioshipOperation().equals("D")){
            
            deleteStudentCalender();
        }
        
   
         dbg("end of relationshipProcessing");
         }catch(DBValidationException ex){
             throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
             throw new DBProcessingException(ex.toString());
        }catch(BSValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
 }
    
 private void createOrUpdateStudentCalender()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
     
        try{
        dbg("inside createOrUpdateStudentCalender") ;  
        boolean recordExistence=true;
        IDBTransactionService dbts=inject.getDBTransactionService();
        BusinessService bs=inject.getBusinessService(session);
        RequestBody<StudentFeeManagement> reqBody = request.getReqBody();
        StudentFeeManagement studentFeeManagement =reqBody.get();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_studentID=studentFeeManagement.getStudentID();
        String l_dueDate=studentFeeManagement.getDueDate();
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_dueDate);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth();
        String l_day=convertedDate.getDay();
        int day=Integer.parseInt(l_day);
        String[] l_pkey={l_studentID,l_year,l_month};
        DBRecord studentCalenderRec;
        String l_previousMonthEvent=null;
        String newDayEvent=null;
        String currentEvent=getCurrentEventFromBO();
        String pKeyOfCurrentEvent=studentFeeManagement.getFeeID();
        String previousEvent=null;
        try{
        
            studentCalenderRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_CALENDER", l_pkey, session, dbSession);
            l_previousMonthEvent=studentCalenderRec.getRecord().get(3).trim();
            dbg("l_previousMonthEvent"+l_previousMonthEvent);
        }catch(DBValidationException ex){
            if(ex.toString().contains("DB_VAL_011")){
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
               recordExistence=false;                
            }
        }
        
        dbg("recordExistence"+recordExistence);
        
        if(recordExistence){
            
            String l_previousDayEvents=l_previousMonthEvent.split("d")[day];
            dbg("l_previousDayEvents"+l_previousDayEvents);
            if(l_previousDayEvents.contains("F")){
                dbg("l_previousDayEvents.contains F");
                String[]l_eventArray=l_previousDayEvents.split("//");
                dbg("l_eventArray size"+l_eventArray.length);
                boolean pkExistence=false;
                for(int i=1;i<l_eventArray.length;i++){
                    previousEvent=l_eventArray[i];
                    dbg("previousEvent"+previousEvent);
                    String l_pkColumnsWithoutVersion=bs.getPKcolumnswithoutVersion("STUDENT","SVW_STUDENT_FEE_MANAGEMENT",session,inject);
                    int l_lengthOfPkWithoutVersion=l_pkColumnsWithoutVersion.split("~").length;
                    String l_pkey_of_ExistingEvent=bs.getPkeyOfExistingEvent(l_lengthOfPkWithoutVersion,previousEvent);
                    dbg("l_pkey_of_ExistingEvent"+l_pkey_of_ExistingEvent);
                    if(pKeyOfCurrentEvent.equals(l_pkey_of_ExistingEvent)){
                        dbg("pKeyOfCurrentEvent.equals(l_pkey_of_ExistingEvent)");
                        pkExistence=true;
                        break;
                    }
                    
                }
                
                dbg("after for loop pk existence"+pkExistence);
                if(pkExistence){
                
                     newDayEvent=l_previousDayEvents.replace(previousEvent+"//", currentEvent);
                     dbg("newDayEvent"+newDayEvent);
                }else{
                    
                    newDayEvent=l_previousDayEvents.concat(currentEvent);
                    dbg("newDayEvent"+newDayEvent);
                }
            
            
            }else{
                dbg("l_previousDayEvents not contains F");
                newDayEvent=l_previousDayEvents.concat(currentEvent);
            }
            
            String newMonthEvent=bs.setDayEventinMonthEvent(l_previousMonthEvent,l_previousDayEvents,newDayEvent,l_day);
            Map<String,String>l_column_to_update=new HashMap();
            l_column_to_update.put("4", newMonthEvent);
             
             dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_CALENDER", l_pkey, l_column_to_update, session);   
            

        }else{
            String l_monthEvent=bs.createMonthEvent(l_dueDate);
            dbg(l_day+"//"+" "+"d");
            dbg(l_day+"//"+currentEvent+"d");
            
            newDayEvent =l_monthEvent.replace("d"+l_day+"//"+" "+"d", "d"+l_day+"//"+currentEvent+"d");
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",73,l_studentID,l_year,l_month,newDayEvent);
        }
        
        
        
        
          
        dbg("end of buildRequestAndCallStudentMaster");  
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
    
 private void deleteStudentCalender()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
     
        try{
        dbg("inside deleteStudentCalender");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        BusinessService bs=inject.getBusinessService(session);
        RequestBody<StudentFeeManagement> reqBody = request.getReqBody();
        StudentFeeManagement studentFeeManagement =reqBody.get();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_studentID=studentFeeManagement.getStudentID();
        String l_dueDate=studentFeeManagement.getDueDate();
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_dueDate);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth();
        String l_day=convertedDate.getDay();
        int day=Integer.parseInt(l_day);
        String[] l_pkey={l_studentID,l_year,l_month};
        DBRecord studentCalenderRec;
        String l_previousMonthEvent=null;
        String newDayEvent=null;
        
        String currentEvent=getCurrentEventFromBO();

        
            studentCalenderRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_CALENDER", l_pkey, session, dbSession);
            l_previousMonthEvent=studentCalenderRec.getRecord().get(3).trim();
            dbg("l_previousMonthEvent"+l_previousMonthEvent);
            String l_previousDayEvents=l_previousMonthEvent.split("d")[day];
            dbg("l_previousDayEvents"+l_previousDayEvents);
            
            if(l_previousDayEvents.split("//").length==2){
               newDayEvent=l_previousDayEvents.replace(currentEvent, " ");
            
            }else{
                
                newDayEvent=l_previousDayEvents.replace(currentEvent, "");
            } 
            String newMonthEvent=bs.setDayEventinMonthEvent(l_previousMonthEvent,l_previousDayEvents,newDayEvent,l_day);
            
            Map<String,String>l_column_to_update=new HashMap();
            l_column_to_update.put("4", newMonthEvent);
             
            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_CALENDER", l_pkey, l_column_to_update, session);   
            

        
        
        
        
        
          
       dbg("inside deleteStudentCalender");
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
 

 
 
 
 private String getCurrentEventFromBO()throws BSProcessingException{
     
     try{
         
        dbg("inside StudentFeeManagement--->getCurrentEventFromBO");
        RequestBody<StudentFeeManagement> reqBody = request.getReqBody();
        StudentFeeManagement studentFeeManagement =reqBody.get(); 
        String l_feeID=studentFeeManagement.getFeeID();
        String l_feeType=studentFeeManagement.getFeePaid();
        String l_amount=studentFeeManagement.getAmount();
        String currentEvent="F"+","+l_feeID+","+l_feeType+","+l_amount+"//";
         
        dbg("currentEvent"+currentEvent); 
        return  currentEvent;
     }catch (Exception ex) {
       dbg(ex);
       throw new BSProcessingException("Exception" + ex.toString());
     }

 }
}
