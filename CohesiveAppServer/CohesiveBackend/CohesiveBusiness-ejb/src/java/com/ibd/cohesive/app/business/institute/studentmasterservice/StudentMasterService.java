
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.studentmasterservice;

import com.ibd.businessViews.IStudentMasterService;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.message.request.Parsing;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.app.business.util.exception.ExceptionHandler;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
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
 

//@Local(IStudentMasterService.class)
@Remote(IStudentMasterService.class)
@Stateless
public class StudentMasterService implements IStudentMasterService {

    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentMasterService(){
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
       dbg("inside StudentMasterService--->processing");
       dbg("StudentMasterService--->Processing--->I/P--->requestJson"+requestJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,requestJson);
       bs.requestlogging(request,requestJson, inject,session,dbSession);
       buildBOfromReq(requestJson);  
       RequestBody<StudentMaster> reqBody = request.getReqBody();
       StudentMaster studentMaster =reqBody.get();
       l_lockKey=studentMaster.getStudentID();
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<IStudentMasterService>studentMasterEJB=new BusinessEJB();
       studentMasterEJB.set(this);
       exAudit=bs.getExistingAudit(studentMasterEJB);
       
      if(!(bsv. businessServiceValidation(requestJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
      }
       if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");

       } 
      
      
       bs.businessServiceProcssing(request, exAudit, inject,studentMasterEJB);
       

         if(l_session_created_now){
             jsonResponse= bs.buildSuccessResponse(requestJson, request, inject, session,studentMasterEJB) ;
             tc.commit(session,dbSession);
             dbg("commit is called in student master service");
        }
       dbg("StudentMasterService--->Processing--->O/P--->jsonResponse"+jsonResponse);     
       dbg("End of studentMasterService--->processing");     
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
            dbg("Response"+jsonResponse.toString());
            BSValidation bsv=inject.getBsv(session);
//            if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
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
       dbg("inside StudentMasterService--->buildBOfromReq"); 
       dbg("StudentMasterService--->buildBOfromReq--->I/P--->p_request"+p_request.toString()); 
       RequestBody<StudentMaster> reqBody = new RequestBody<StudentMaster>(); 
       StudentMaster studentMaster = new StudentMaster();
       JsonObject l_body=p_request.getJsonObject("body");
       String l_operation=request.getReqHeader().getOperation();
       studentMaster.setStudentID(l_body.getString("studentID"));
       if(!(l_operation.equals("View"))){
            studentMaster.setStudentName(l_body.getString("studentName"));
            studentMaster.setStandard(l_body.getString("standard"));
            studentMaster.setSection(l_body.getString("section"));
        }
       reqBody.set(studentMaster);
      request.setReqBody(reqBody);
      dbg("End of StudentMasterService--->buildBOfromReq");
      }
        catch(Exception ex){
             dbg(ex);
            throw new BSProcessingException("BodyParsingException"+ex.toString());
        }
   }
    
    public void buildBOfromDB( DBRecord studentMasterRec)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
            dbg("inside StudentMasterService--->buildBOfromDB");
            
            ErrorHandler errhandler=session.getErrorhandler();
            ArrayList<String>p_studentMasterList=studentMasterRec.getRecord();
            RequestBody<StudentMaster> reqBody = request.getReqBody();
            StudentMaster studentMaster =reqBody.get();
            String l_studentID=studentMaster.getStudentID();
            if(p_studentMasterList!=null&&!p_studentMasterList.isEmpty()){
           
           studentMaster.setStudentName(p_studentMasterList.get(1).trim());
           studentMaster.setStandard(p_studentMasterList.get(2).trim());
           studentMaster.setSection(p_studentMasterList.get(3).trim());
           request.getReqAudit().setMakerID(p_studentMasterList.get(4).trim());
           request.getReqAudit().setCheckerID(p_studentMasterList.get(5).trim());
           request.getReqAudit().setMakerDateStamp(p_studentMasterList.get(6).trim());
           request.getReqAudit().setCheckerDateStamp(p_studentMasterList.get(7).trim());
           request.getReqAudit().setRecordStatus(p_studentMasterList.get(8).trim());
           request.getReqAudit().setAuthStatus(p_studentMasterList.get(9).trim());
           request.getReqAudit().setVersionNumber(p_studentMasterList.get(10).trim());
           request.getReqAudit().setMakerRemarks(p_studentMasterList.get(11).trim());
           request.getReqAudit().setCheckerRemarks(p_studentMasterList.get(12).trim());
            
            }
            else{
             errhandler.log_app_error("BS_VAL_013", "IVW_STUDENT_MASTER,"+l_studentID);
             throw new BSValidationException("BSValidationException");
            } 
            
       
        dbg("End of StudentMasterService--->buildBOfromDB");
        }catch(BSValidationException ex){
           throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
 }
     public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
        try{
        dbg("Inside StudentMasterService--->create"); 
        RequestBody<StudentMaster> reqBody = request.getReqBody();
        StudentMaster studentMaster =reqBody.get();
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
        String l_studentID=studentMaster.getStudentID();
        String l_studentName=studentMaster.getStudentName();
        String l_standard=studentMaster.getStandard();
        String l_section=studentMaster.getSection();
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",36,l_studentID,l_studentName,l_standard,l_section,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
       
      

        dbg("End of StudentMasterService--->create");
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
            dbg("Inside StudentMasterService--->update"); 
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            String l_instituteID=request.getReqHeader().getInstituteID();
            RequestBody<StudentMaster> reqBody = request.getReqBody();
            StudentMaster studentMaster =reqBody.get();
            String l_studentID=studentMaster.getStudentID();
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
            Map<String,String>  l_column_to_update=new HashMap();
             l_column_to_update.put("6", l_checkerID);
             l_column_to_update.put("8", l_checkerDateStamp);
             l_column_to_update.put("10", l_authStatus);
             l_column_to_update.put("13", l_checkerRemarks);
             
             String[] l_primaryKey={l_studentID};
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STUDENT_MASTER", l_primaryKey, l_column_to_update, session);

             dbg("End of StudentMasterService--->update");          
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
        dbg("Inside StudentMasterService--->fullUpdate");
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentMaster> reqBody = request.getReqBody();
        StudentMaster studentMaster =reqBody.get();
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
        
        String l_studentID=studentMaster.getStudentID();
        String l_studentName=studentMaster.getStudentName();
        String l_standard=studentMaster.getStandard();
        String l_section=studentMaster.getSection();
        
        l_column_to_update= new HashMap();
        l_column_to_update.put("2", l_studentName);
        l_column_to_update.put("3", l_standard);
        l_column_to_update.put("4", l_section);
        l_column_to_update.put("5", l_makerID);
        l_column_to_update.put("6", l_checkerID);
        l_column_to_update.put("7", l_makerDateStamp);
        l_column_to_update.put("8", l_checkerDateStamp);
        l_column_to_update.put("9", l_recordStatus);
        l_column_to_update.put("10", l_authStatus);
        l_column_to_update.put("11", l_versionNumber);
        l_column_to_update.put("12", l_makerRemarks);
        l_column_to_update.put("13", l_checkerRemarks);
        
        String[] l_primaryKey={l_studentID};
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STUDENT_MASTER", l_primaryKey, l_column_to_update, session);
                   
        dbg("end of StudentMasterService--->fullUpdate");                
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
        dbg("Inside StudentMasterService--->delete");  
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentMaster> reqBody = request.getReqBody();
        StudentMaster studentMaster =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();    
        String l_studentID=studentMaster.getStudentID();

        String[] l_primaryKey={l_studentID};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STUDENT_MASTER", l_primaryKey, session);
                   
 
         dbg("End of StudentMasterService--->delete");      
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
            dbg("Inside StudentMasterService--->tableRead");
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<StudentMaster> reqBody = request.getReqBody();
            String l_instituteID=request.getReqHeader().getInstituteID();
            StudentMaster studentMaster =reqBody.get();
            String l_studentID=studentMaster.getStudentID();
            String[] l_pkey={l_studentID};
            
            DBRecord dbrec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STUDENT_MASTER", l_pkey, session,dbSession);
            buildBOfromDB(dbrec);
           
            
         
          dbg("end of  StudentMasterService--->tableRead");               
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
     
     public void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
         
         return;
         
     }
     
     public JsonObject buildJsonResFromBO()throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
         JsonObject body=null;
         try{
             dbg("inside buildJsonResFromBO");
            RequestBody<StudentMaster> reqBody = request.getReqBody();
            StudentMaster studentMaster =reqBody.get();
            
             body=Json.createObjectBuilder().add("studentID", studentMaster.getStudentID())
                                            .add("studentName", studentMaster.getStudentName())
                                            .add("standard", studentMaster.getStandard())
                                            .add("section", studentMaster.getSection())
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
       dbg("inside StudentMasterService--->businessValidation");    
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

       dbg("StudentMasterService--->businessValidation--->O/P--->status"+status);
       dbg("End of StudentMasterService--->businessValidation");
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
        dbg("inside StudentMasterService--->masterMandatoryValidation");
        RequestBody<StudentMaster> reqBody = request.getReqBody();
        StudentMaster student =reqBody.get();
         
         if(student.getStudentID()==null||student.getStudentID().isEmpty())
         {   
            status=false;
            dbg("status false in studentID");
            errhandler.log_app_error("BS_VAL_002","studentID");
         }
         
         
         dbg("StudentMasterService--->masterMandatoryValidation--->O/P--->status"+status);
         dbg("End of StudentMasterService--->masterMandatoryValidation");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
     private boolean detailMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
        boolean status=true;
         try{
             dbg("inside StudentMasterService--->detailMandatoryValidation");
            RequestBody<StudentMaster> reqBody = request.getReqBody();
            StudentMaster student =reqBody.get();
        
           if(student.getStudentName()==null||student.getStudentName().isEmpty()){
               
             status=false;  
             errhandler.log_app_error("BS_VAL_002","studentName");  
           }
 
          if(student.getStandard()==null||student.getStandard().isEmpty()){
               
             status=false;  
             errhandler.log_app_error("BS_VAL_002","standard");  
           }
      
         if(student.getSection()==null||student.getSection().isEmpty()){
               
             status=false;  
             errhandler.log_app_error("BS_VAL_002","section");  
           }
        
        dbg("StudentMasterService--->detailMandatoryValidation--->O/P--->status"+status);
        dbg("End of StudentMasterService--->detailMandatoryValidation");
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
  
  private boolean detailDataValidation(ErrorHandler errhandler,String p_instituteID)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
      try{
        dbg("inside StudentMasterService--->detailDataValidation");   
        dbg("inside StudentMasterService--->detailDataValidation--->I/P--->p_instituteID"+p_instituteID);
        BSValidation bsv=inject.getBsv(session);
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<StudentMaster> reqBody = request.getReqBody();
        StudentMaster student =reqBody.get();
        String l_standard=student.getStandard();
        String l_section=student.getSection();
             
             if(!bsv.standardSectionValidation(l_standard,l_section, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","standard or section");
             }
             
             

        dbg("StudentMasterService--->detailDataValidation--->O/P--->status"+status);
        dbg("End of StudentMasterService--->detailDataValidation");  
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
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_versioNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
        if(!(l_operation.equals("Create")||l_operation.equals("View"))){
             
            if(l_operation.equals("AutoAuth")&&l_versioNumber.equals("1")){
                
                return null;
            }else{  
                
               dbg("inside business Service--->getExistingAudit--->Service--->StudentMaster");  
               RequestBody<StudentMaster> studentMasterBody = request.getReqBody();
               StudentMaster studentMaster =studentMasterBody.get();
               String l_studentID=studentMaster.getStudentID();
               dbg("studentID"+l_studentID);
               String[] l_studentMasterpkey={l_studentID};
               DBRecord studentMasterRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STUDENT_MASTER", l_studentMasterpkey, session,dbSession);

               exAudit.setAuthStatus(studentMasterRec.getRecord().get(9).trim());
               exAudit.setMakerID(studentMasterRec.getRecord().get(4).trim());
               exAudit.setRecordStatus(studentMasterRec.getRecord().get(8).trim());
               exAudit.setVersionNumber(Integer.parseInt(studentMasterRec.getRecord().get(10).trim()));
            

 
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
