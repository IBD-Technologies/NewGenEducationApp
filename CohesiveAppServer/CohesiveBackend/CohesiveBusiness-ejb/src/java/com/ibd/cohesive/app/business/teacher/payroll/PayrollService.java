/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.payroll;

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
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.NamingException;
import com.ibd.businessViews.IPayrollService;
import javax.ejb.Remote;

/**
 *
 * @author IBD Technologies
 */
//@Local(IPayrollService.class)
@Remote(IPayrollService.class)
@Stateless
public class PayrollService implements IPayrollService{
  AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public PayrollService(){
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
       dbg("inside PayrollService--->processing");
       dbg("PayrollService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<Payroll> reqBody = request.getReqBody();
       Payroll payroll =reqBody.get();
       String l_teacherID=payroll.getTeacherID();
       String l_year=payroll.getYear();
       String l_month=payroll.getMonth();
       l_lockKey=l_teacherID+"~"+l_year+"~"+l_month;
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       
       BusinessEJB<IPayrollService>payrollEJB=new BusinessEJB();
       payrollEJB.set(this);
      
       exAudit=bs.getExistingAudit(payrollEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,payrollEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,payrollEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in teacher payroll");
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
                /*if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                   BSProcessingException ex=new BSProcessingException("response contains special characters");
                   dbg(ex);
                   session.clearSessionObject();
                   dbSession.clearSessionObject();
                   throw ex;
                }*/
                       clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes   
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
      Payroll payroll=new Payroll();
      RequestBody<Payroll> reqBody = new RequestBody<Payroll>(); 
           
      try{
      dbg("inside teacher payroll buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      payroll.setTeacherID(l_body.getString("teacherID"));
      payroll.setTeacherName(l_body.getString("teacherName"));
      payroll.setYear(l_body.getString("year"));
      payroll.setMonth(l_body.getString("month"));
      
      if(!(l_operation.equals("View"))){
          payroll.setPath(l_body.getString("path"));
          
      
      }
        reqBody.set(payroll);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
    try{ 
        dbg("inside stident payroll create"); 
        RequestBody<Payroll> reqBody = request.getReqBody();
        Payroll payroll =reqBody.get();
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
        String l_teacherID=payroll.getTeacherID();
        String l_year=payroll.getYear();
        String l_month=payroll.getMonth();
        String l_path=payroll.getPath();
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER",66,l_teacherID,l_year,l_month,l_path,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);

        
        
        dbg("end of teacher payroll create"); 
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
        dbg("inside teacher payroll--->auth update"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<Payroll> reqBody = request.getReqBody();
        Payroll payroll =reqBody.get();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_teacherID=payroll.getTeacherID();
        String l_year=payroll.getYear();
        String l_month=payroll.getMonth(); 
        String[] l_primaryKey={l_teacherID,l_year,l_month};
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("6", l_checkerID);
         l_column_to_update.put("8", l_checkerDateStamp);
         l_column_to_update.put("10", l_authStatus);
         l_column_to_update.put("13", l_checkerRemarks);
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_PAYROLL", l_primaryKey, l_column_to_update, session);
         dbg("end of teacher payroll--->auth update");          
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
        dbg("inside payroll full update");   
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<Payroll> reqBody = request.getReqBody();
        Payroll payroll =reqBody.get();
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
        
        String l_teacherID=payroll.getTeacherID();
        String l_year=payroll.getYear();
        String l_month=payroll.getMonth();
        String l_path=payroll.getPath(); 
        l_column_to_update= new HashMap();
        l_column_to_update.put("1", l_teacherID);
        l_column_to_update.put("2", l_year);
        l_column_to_update.put("3", l_month);
        l_column_to_update.put("4", l_path);
        l_column_to_update.put("5", l_makerID);
        l_column_to_update.put("6", l_checkerID);
        l_column_to_update.put("7", l_makerDateStamp);
        l_column_to_update.put("8", l_checkerDateStamp);
        l_column_to_update.put("9", l_recordStatus);
        l_column_to_update.put("10", l_authStatus);
        l_column_to_update.put("11", l_versionNumber);
        l_column_to_update.put("12", l_makerRemarks);
        l_column_to_update.put("13", l_checkerRemarks);
         
                  String[] l_primaryKey={l_teacherID,l_year,l_month};
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_PAYROLL", l_primaryKey, l_column_to_update, session);
                  
        
        dbg("end of payroll full update");   
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
        dbg("inside teacher payroll delete");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<Payroll> reqBody = request.getReqBody();
        Payroll payroll =reqBody.get();
        String l_teacherID=payroll.getTeacherID();
        String l_year=payroll.getYear();
        String l_month=payroll.getMonth();  
        String[] l_primaryKey={l_teacherID,l_year,l_month};
        
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_PAYROLL", l_primaryKey, session);
            dbg("end of teacher payroll delete");
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
        dbg("inside teacher payroll--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<Payroll> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        Payroll payroll =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_teacherID=payroll.getTeacherID();
        String l_year=payroll.getYear();
        String l_month=payroll.getMonth();   
        String[] l_pkey={l_teacherID,l_year,l_month};
        
        DBRecord payrollRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_PAYROLL", l_pkey, session, dbSession);
       
        buildBOfromDB(payrollRec);
        
          dbg("end of  completed teacher payroll--->view");                
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
      private void buildBOfromDB(DBRecord p_payrollRecord)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<Payroll> reqBody = request.getReqBody();
           Payroll payroll =reqBody.get();
           ArrayList<String>l_payrollList= p_payrollRecord.getRecord();
           
           if(l_payrollList!=null&&!l_payrollList.isEmpty()){
               
            payroll.setPath(l_payrollList.get(3).trim());
            request.getReqAudit().setMakerID(l_payrollList.get(4).trim());
            request.getReqAudit().setCheckerID(l_payrollList.get(5).trim());
            request.getReqAudit().setMakerDateStamp(l_payrollList.get(6).trim());
            request.getReqAudit().setCheckerDateStamp(l_payrollList.get(7).trim());
            request.getReqAudit().setRecordStatus(l_payrollList.get(8).trim());
            request.getReqAudit().setAuthStatus(l_payrollList.get(9).trim());
            request.getReqAudit().setVersionNumber(l_payrollList.get(10).trim());
            request.getReqAudit().setMakerRemarks(l_payrollList.get(11).trim());
            request.getReqAudit().setCheckerRemarks(l_payrollList.get(12).trim());
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
        dbg("inside teacher payroll buildJsonResFromBO");    
        RequestBody<Payroll> reqBody = request.getReqBody();
        Payroll payroll =reqBody.get();
         body=Json.createObjectBuilder().add("teacherID", payroll.getTeacherID())
                                        .add("teacherName", payroll.getTeacherName())
                                            .add("year", payroll.getYear())
                                            .add("month", payroll.getMonth())
                                            .add("path", payroll.getPath())
                                            .build();
                                            
              dbg(body.toString());  
           dbg("end of teacher payroll buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside teacher payroll--->businessValidation");    
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
       dbg("end of teacher payroll--->businessValidation"); 
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
        dbg("inside teacher payroll master mandatory validation");
        RequestBody<Payroll> reqBody = request.getReqBody();
        Payroll payroll =reqBody.get();
        
         if(payroll.getTeacherID()==null||payroll.getTeacherID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","teacherID");  
         }
          if(payroll.getYear()==null||payroll.getYear().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","year");  
         }
          if(payroll.getMonth()==null||payroll.getMonth().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","month");  
         }
          
        dbg("end of teacher payroll master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside teacher payroll masterDataValidation");
             RequestBody<Payroll> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             Payroll payroll =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_teacherID=payroll.getTeacherID();
             String l_year=payroll.getYear();
             String l_month=payroll.getMonth();
             
             if(!bsv.teacherIDValidation(l_teacherID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","teacherID");
             }
             if(!bsv.yearValidation(l_year, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","year");
             }
             if(!bsv.monthValidation(l_month, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","month");
             }
             
            
            dbg("end of teacher payroll masterDataValidation");
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
        RequestBody<Payroll> reqBody = request.getReqBody();
        Payroll payroll =reqBody.get();
        
        try{
            
            dbg("inside teacher payroll detailMandatoryValidation");
           
            
            if(payroll.getPath()==null||payroll.getPath().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","Type");  
            }
            
            
            
           dbg("end of teacher payroll detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         
         try{
             return true;
             
//        } catch (DBProcessingException ex) {
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException" + ex.toString());
//        } catch(DBValidationException ex){
//            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        
              
    }
    public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     try{
        dbg("inside Payroll--->getExistingAudit") ;
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
        if(!(l_operation.equals("Create"))){
             
              if(l_operation.equals("AutoAuth")&&l_versionNumber.equals("1")){
                return null;
              }else{  
               dbg("inside Payroll--->getExistingAudit--->Service--->UserLeaveManagement");  
               RequestBody<Payroll> payrollBody = request.getReqBody();
               Payroll payroll =payrollBody.get();
               String l_teacherID=payroll.getTeacherID();
               String l_year=payroll.getYear();
               String l_month=payroll.getMonth();
               String[] l_pkey={l_teacherID,l_year,l_month};
               DBRecord l_payrollRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_PAYROLL", l_pkey, session, dbSession);
               exAudit.setAuthStatus(l_payrollRecord.getRecord().get(9).trim());
               exAudit.setMakerID(l_payrollRecord.getRecord().get(4).trim());
               exAudit.setRecordStatus(l_payrollRecord.getRecord().get(8).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_payrollRecord.getRecord().get(10).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of Payroll--->getExistingAudit") ;
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
    
    public void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
         
         return;
         
     }  
}
