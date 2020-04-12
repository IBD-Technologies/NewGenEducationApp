/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.holidaymaintanence;

import com.ibd.businessViews.IHolidayMaintanenceService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
//@Local(IHolidayMaintanenceService.class)
@Remote(IHolidayMaintanenceService.class)
@Stateless
public class HolidayMaintanenceService implements IHolidayMaintanenceService{
     AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public HolidayMaintanenceService(){
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
       dbg("inside HolidayMaintanenceService--->processing");
       dbg("HolidayMaintanenceService--->Processing--->I/P--->requestJson"+requestJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,requestJson);
       bs.requestlogging(request,requestJson, inject,session,dbSession);
       buildBOfromReq(requestJson);  
       RequestBody<HolidayMaintanence> reqBody = request.getReqBody();
       HolidayMaintanence holidayMaintanence =reqBody.get();
       l_lockKey=holidayMaintanence.getInstituteID();
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<IHolidayMaintanenceService>holidayMaintanenceEJB=new BusinessEJB();
       holidayMaintanenceEJB.set(this);
       exAudit=bs.getExistingAudit(holidayMaintanenceEJB);
       
      if(!(bsv. businessServiceValidation(requestJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
      }
       if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");

       } 
      
      
       bs.businessServiceProcssing(request, exAudit, inject,holidayMaintanenceEJB);
       

         if(l_session_created_now){
             jsonResponse= bs.buildSuccessResponse(requestJson, request, inject, session,holidayMaintanenceEJB) ;
             tc.commit(session,dbSession);
             dbg("commit is called in  service");
        }
       dbg("HolidayMaintanenceService--->Processing--->O/P--->jsonResponse"+jsonResponse);     
       dbg("End of holidayMaintanenceService--->processing");     
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
       dbg("inside HolidayMaintanenceService--->buildBOfromReq"); 
       dbg("HolidayMaintanenceService--->buildBOfromReq--->I/P--->p_request"+p_request.toString()); 
       RequestBody<HolidayMaintanence> reqBody = new RequestBody<HolidayMaintanence>(); 
       HolidayMaintanence holidayMaintanence = new HolidayMaintanence();
       JsonObject l_body=p_request.getJsonObject("body");
       String l_operation=request.getReqHeader().getOperation();
       holidayMaintanence.setInstituteID(l_body.getString("instituteID"));
        
       holidayMaintanence.setInstituteName(l_body.getString("instituteName"));
       holidayMaintanence.setYear(l_body.getString("year"));
       holidayMaintanence.setMonth(l_body.getString("month"));
       if(!(l_operation.equals("View"))){

           
           holidayMaintanence.setHoliday(l_body.getString("holiday"));


        }
       reqBody.set(holidayMaintanence);
      request.setReqBody(reqBody);
      dbg("End of HolidayMaintanenceService--->buildBOfromReq");
      }
        catch(Exception ex){
             dbg(ex);
            throw new BSProcessingException("BodyParsingException"+ex.toString());
        }
   }
    
  
     public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
        try{
        dbg("Inside HolidayMaintanenceService--->create"); 
        RequestBody<HolidayMaintanence> reqBody = request.getReqBody();
        HolidayMaintanence holidayMaintanence =reqBody.get();
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
        
        String l_instituteID=holidayMaintanence.getInstituteID();
        String l_year=holidayMaintanence.getYear();
        String l_month=holidayMaintanence.getMonth();
        String l_holiday=holidayMaintanence.getHoliday();
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",87,l_instituteID,l_year,l_month,l_holiday,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
       
        
       

        dbg("End of HolidayMaintanenceService--->create");
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
            dbg("Inside HolidayMaintanenceService--->update"); 
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<HolidayMaintanence> reqBody = request.getReqBody();
            HolidayMaintanence holidayMaintanence =reqBody.get();
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
            String l_instituteID=holidayMaintanence.getInstituteID(); 
            String l_year=holidayMaintanence.getYear();
            String l_month=holidayMaintanence.getMonth();
            Map<String,String>  l_column_to_update=new HashMap();
            l_column_to_update.put("6", l_checkerID);
            l_column_to_update.put("8", l_checkerDateStamp);
            l_column_to_update.put("10", l_authStatus);
            l_column_to_update.put("13", l_checkerRemarks);
            
             
             String[] l_primaryKey={l_instituteID,l_year,l_month};
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_HOLIDAY_MAINTANENCE", l_primaryKey, l_column_to_update, session);

             dbg("End of HolidayMaintanenceService--->update");          
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
        dbg("Inside HolidayMaintanenceService--->fullUpdate");
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<HolidayMaintanence> reqBody = request.getReqBody();
        HolidayMaintanence holidayMaintanence =reqBody.get();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_instituteID=holidayMaintanence.getInstituteID(); 
        String l_year=holidayMaintanence.getYear();
        String l_month=holidayMaintanence.getMonth(); 
        String l_holiday=holidayMaintanence.getHoliday();
        l_column_to_update= new HashMap();
        l_column_to_update.put("4", l_holiday);
        l_column_to_update.put("7", l_makerDateStamp);
        l_column_to_update.put("12", l_makerRemarks);
        String[] l_standardPKey={l_instituteID,l_year,l_month};
            
            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_HOLIDAY_MAINTANENCE",l_standardPKey,l_column_to_update,session);
        
       
       
        
                   
        dbg("end of HolidayMaintanenceService--->fullUpdate");                
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
        dbg("Inside HolidayMaintanenceService--->delete");  
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<HolidayMaintanence> reqBody = request.getReqBody();
        HolidayMaintanence holidayMaintanence =reqBody.get();
        String l_instituteID=holidayMaintanence.getInstituteID();   
        String l_year=holidayMaintanence.getYear();
        String l_month=holidayMaintanence.getMonth();

        String[] l_primaryKey={l_instituteID,l_year,l_month};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_HOLIDAY_MAINTANENCE", l_primaryKey, session);
         
               
 
         dbg("End of HolidayMaintanenceService--->delete");      
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
            dbg("Inside HolidayMaintanenceService--->tableRead");
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<HolidayMaintanence> reqBody = request.getReqBody();
            HolidayMaintanence holidayMaintanence =reqBody.get();
            String l_instituteID=holidayMaintanence.getInstituteID();
            String l_year=holidayMaintanence.getYear();
            String l_month=holidayMaintanence.getMonth();
            String[] l_pkey={l_instituteID,l_year,l_month};
            DBRecord holidayMaintanenceRecord=null;
            
           try{
            
                holidayMaintanenceRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_HOLIDAY_MAINTANENCE", l_pkey, session,dbSession);
                buildBOfromDB(holidayMaintanenceRecord);
            
           }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", "l_year"+"-"+l_month);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
                }
            
            
         
          dbg("end of  HolidayMaintanenceService--->tableRead");               
        }catch(BSValidationException ex){
            throw ex;
        }  
        catch(DBValidationException ex){
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
     
      private void buildBOfromDB( DBRecord p_holidayMaintenance)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
            dbg("inside HolidayMaintanenceService--->buildBOfromDB");
            
            ArrayList<String>holidayList=p_holidayMaintenance.getRecord();
            RequestBody<HolidayMaintanence> reqBody = request.getReqBody();
            HolidayMaintanence holidayMaintanence =reqBody.get();
            if(holidayList!=null&&!holidayList.isEmpty()){
           
               holidayMaintanence.setYear(holidayList.get(1).trim());
               holidayMaintanence.setMonth(holidayList.get(2).trim());
               holidayMaintanence.setHoliday(holidayList.get(3).trim());
               request.getReqAudit().setMakerID(holidayList.get(4).trim());
               request.getReqAudit().setCheckerID(holidayList.get(5).trim());
               request.getReqAudit().setMakerDateStamp(holidayList.get(6).trim());
               request.getReqAudit().setCheckerDateStamp(holidayList.get(7).trim());
               request.getReqAudit().setRecordStatus(holidayList.get(8).trim());
               request.getReqAudit().setAuthStatus(holidayList.get(9).trim());
               request.getReqAudit().setVersionNumber(holidayList.get(10).trim());
               request.getReqAudit().setMakerRemarks(holidayList.get(11).trim());
               request.getReqAudit().setCheckerRemarks(holidayList.get(12).trim());
            }
            
          
       
        dbg("End of HolidayMaintanenceService--->buildBOfromDB");
        
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
            RequestBody<HolidayMaintanence> reqBody = request.getReqBody();
            HolidayMaintanence holidayMaintanence =reqBody.get();
            String insName;
            if(holidayMaintanence.getInstituteName().isEmpty())
            {
             BusinessService  bs;
             bs=inject.getBusinessService(session);
             insName=bs.getInstituteName(holidayMaintanence.getInstituteID(), session, dbSession, inject);
            }
            else
            {
                insName=holidayMaintanence.getInstituteName();
            }   
             body=Json.createObjectBuilder().add("instituteID", holidayMaintanence.getInstituteID())
                                            .add("instituteName", insName)
                                            .add("year", holidayMaintanence.getYear())
                                            .add("month",holidayMaintanence.getMonth() )
                                            .add("holiday",holidayMaintanence.getHoliday() )
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
       dbg("inside HolidayMaintanenceService--->businessValidation");    
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

       dbg("HolidayMaintanenceService--->businessValidation--->O/P--->status"+status);
       dbg("End of HolidayMaintanenceService--->businessValidation");
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
        dbg("inside HolidayMaintanenceService--->masterMandatoryValidation");
        RequestBody<HolidayMaintanence> reqBody = request.getReqBody();
        HolidayMaintanence holidayMaintanence =reqBody.get();
         
         if(holidayMaintanence.getInstituteID()==null||holidayMaintanence.getInstituteID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","InstituteID");
         }
         if(holidayMaintanence.getYear()==null||holidayMaintanence.getYear().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","Year");
         }
         if(holidayMaintanence.getMonth()==null||holidayMaintanence.getMonth().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","Month");
         }
         
         
         
         dbg("HolidayMaintanenceService--->masterMandatoryValidation--->O/P--->status"+status);
         dbg("End of HolidayMaintanenceService--->masterMandatoryValidation");
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
        RequestBody<HolidayMaintanence> reqBody = request.getReqBody();
        HolidayMaintanence holidayMaintanence =reqBody.get();
        String l_instituteID= holidayMaintanence.getInstituteID();
        ErrorHandler errHandler=session.getErrorhandler();
        String l_year=holidayMaintanence.getYear();
        String l_month=holidayMaintanence.getMonth();
             
        if(!bsv.instituteIDValidation( l_instituteID,errHandler,inject, session, dbSession)){
            status=false;
            errhandler.log_app_error("BS_VAL_003","InstituteID");
        }
        if(!bsv.yearValidation(l_year, session, dbSession, inject)){
            status=false;
            errhandler.log_app_error("BS_VAL_003","Year");
        }
        if(!bsv.monthValidation(l_month, session, dbSession, inject)){
            status=false;
            errhandler.log_app_error("BS_VAL_003","Month");
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
            dbg("inside HolidayMaintanenceService--->detailMandatoryValidation");
            RequestBody<HolidayMaintanence> reqBody = request.getReqBody();
            HolidayMaintanence holidayMaintanence =reqBody.get();
           
            if(holidayMaintanence.getHoliday()==null||holidayMaintanence.getHoliday().isEmpty()) {   
               status=false;
               errhandler.log_app_error("BS_VAL_002","Holiday");
            }
        
        
        dbg("HolidayMaintanenceService--->detailMandatoryValidation--->O/P--->status"+status);
        dbg("End of HolidayMaintanenceService--->detailMandatoryValidation");
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
  
   private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
     try{
        dbg("inside ClassConfigService--->detailDataValidation");   
        BSValidation bsv=inject.getBsv(session);
        RequestBody<HolidayMaintanence> reqBody = request.getReqBody();
        HolidayMaintanence holidayMaintenance=reqBody.get();
        String l_holiday=holidayMaintenance.getHoliday();
        char[] holidayArr=l_holiday.toCharArray();
        
        for(int i=0;i<holidayArr.length;i++){
            
            if(!(holidayArr[i]=='F'||holidayArr[i]=='H'||holidayArr[i]=='W'||holidayArr[i]=='A')){
                status=false;
                errhandler.log_app_error("BS_VAL_004","Holiday");
            }
            
        }

        dbg("ClassconfigServiceService--->detailDataValidation--->O/P--->status"+status);
        dbg("End of ClassExamScheduleServiceService--->detailDataValidation");  
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
        RequestBody<HolidayMaintanence> holidayMaintanenceBody = request.getReqBody();
        HolidayMaintanence holidayMaintanence =holidayMaintanenceBody.get();
        String l_instituteID=holidayMaintanence.getInstituteID();
        String l_versioNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
        if(!(l_operation.equals("Create")||l_operation.equals("Create-Default")||l_operation.equals("View"))){
              
            if(l_operation.equals("AutoAuth")&&l_versioNumber.equals("1")){
                
                return null;
            }else{  
                
               dbg("inside business Service--->getExistingAudit--->Service--->HolidayMaintanence");  
               
               String l_year=holidayMaintanence.getYear();
               String l_month=holidayMaintanence.getMonth();
               String[] l_pkey={l_instituteID,l_year,l_month};

            
               DBRecord instituteRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_HOLIDAY_MAINTANENCE", l_pkey, session,dbSession);

               exAudit.setAuthStatus(instituteRecord.getRecord().get(9).trim());
               exAudit.setMakerID(instituteRecord.getRecord().get(4).trim());
               exAudit.setRecordStatus(instituteRecord.getRecord().get(8).trim());
               exAudit.setVersionNumber(Integer.parseInt(instituteRecord.getRecord().get(10).trim()));
            

 
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
   private boolean  checkWorkingDay(Date date,String l_instituteID)throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
        boolean l_session_created_now=false;
        boolean status=false;        
        try{
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now=session.isI_session_created_now();
            dbg("Inside HolidayMaintanenceService--->checkWorkingDay");
            System.out.println("Inside HolidayMaintanenceService--->checkWorkingDay");
            dbg("date"+date);
            dbg("l_instituteID"+l_instituteID);
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IPDataService pds=inject.getPdataservice();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            ErrorHandler errHandler=session.getErrorhandler();
            BusinessService bs=inject.getBusinessService(session);
            String dateformat=i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
            String dateString=formatter.format(date);
            ConvertedDate converted=bs.getYearMonthandDay(dateString);
            String l_year=converted.getYear();
            String l_month=converted.getMonth();
            String l_day=converted.getDay();
            dbg("l_year"+l_year);
            dbg("l_month"+l_month);
            dbg("l_day"+l_day);
            String[] l_pkey={l_instituteID,l_year,l_month};
            
            ArrayList<String> holidayMaintanenceRecord=pds.readRecordPData( session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_HOLIDAY_MAINTANENCE", l_pkey);
           
            String holidayString=holidayMaintanenceRecord.get(3);
            dbg("holidayString"+holidayString);
            
            if(holidayString.length()>=Integer.parseInt(l_day)){
                
                dbg("holiday string has the record for the day");
                char[] holidayArr=holidayString.toCharArray();
                char charForTheDay=holidayArr[Integer.parseInt(l_day)-1];
                dbg("charForTheDay"+charForTheDay);
                
                if(charForTheDay=='W'){
                    status=true;
                }
                
            }else{
                
                errHandler.log_app_error("BS_VAL_019",dateString);
                throw new BSValidationException("BSValidationException");
            }
            
            dbg("end of  HolidayMaintanenceService--->checkWorkingDay");               
            return status;
        }catch(DBValidationException ex){
            throw ex;
        }catch(BSValidationException ex){
            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }finally{
            if(l_session_created_now){
               session.clearSessionObject();
               dbSession.clearSessionObject();
            }
        }
}
   
   @Override
   public boolean checkWorkingDay(Date date,String l_instituteID,CohesiveSession session)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException
    {
    CohesiveSession tempSession = this.session;
    try{
        this.session=session;
        System.out.println("inside cross call of checkWorkingDay");
       return checkWorkingDay(date,l_instituteID);
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
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
}
