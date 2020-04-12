/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentotheractivity;

import com.ibd.businessViews.IStudentOtherActivityService;
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
//@Local(IStudentOtherActivityService.class)
@Remote(IStudentOtherActivityService.class)
@Stateless
public class StudentOtherActivityService implements IStudentOtherActivityService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentOtherActivityService(){
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
       dbg("inside StudentOtherActivityService--->processing");
       dbg("StudentOtherActivityService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<StudentOtherActivity> reqBody = request.getReqBody();
       StudentOtherActivity studentOtherActivity =reqBody.get();
       l_lockKey=studentOtherActivity.getActivityID()+"~"+studentOtherActivity.getStudentID();
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<IStudentOtherActivityService>studentOtherActivityEJB=new BusinessEJB();
       studentOtherActivityEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentOtherActivityEJB);
       if(request.getReqHeader().getOperation().equals("View")){
           
         String studentID=  bs.studentValidation(studentOtherActivity.getStudentID(), studentOtherActivity.getStudentName(), request.getReqHeader().getInstituteID(), session, dbSession, inject);
         
          
         if(studentID==null||studentID.isEmpty()){
             
             errhandler.log_app_error("BS_VAL_002","Student ID or Name");  
             throw new BSValidationException("BSValidationException");
         }
         
         studentOtherActivity.setStudentID(studentID);
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
      
       bs.businessServiceProcssing(request, exAudit, inject,studentOtherActivityEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentOtherActivityEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student otherActivity");
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
//                if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
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
      StudentOtherActivity studentOtherActivity=new StudentOtherActivity();
      RequestBody<StudentOtherActivity> reqBody = new RequestBody<StudentOtherActivity>(); 
           
      try{
      dbg("inside student otherActivity buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      studentOtherActivity.setStudentID(l_body.getString("studentID"));
      studentOtherActivity.setStudentName(l_body.getString("studentName"));
      studentOtherActivity.setActivityID(l_body.getString("activityID"));
      if(!l_operation.equals("View")){
          studentOtherActivity.setActivityName(l_body.getString("activityName"));
          studentOtherActivity.setActivityType(l_body.getString("activityType"));
          studentOtherActivity.setLevel(l_body.getString("level"));
          studentOtherActivity.setVenue(l_body.getString("venue"));
          studentOtherActivity.setDate(l_body.getString("date"));
          studentOtherActivity.setResult(l_body.getString("result"));
          studentOtherActivity.setIntrest(l_body.getString("participate"));
          studentOtherActivity.setDueDate(l_body.getString("dueDate"));
      }
        reqBody.set(studentOtherActivity);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
    try{ 
        dbg("inside stident otherActivity create"); 
         IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IPDataService pds=inject.getPdataservice();
        String l_userID=request.getReqHeader().getUserID();
        RequestBody<StudentOtherActivity> reqBody = request.getReqBody();
        StudentOtherActivity studentOtherActivity =reqBody.get();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        BSValidation bsv=inject.getBsv(session);
        String l_instituteID=request.getReqHeader().getInstituteID();   
        BusinessService bs=inject.getBusinessService(session);
        String[] l_userPkey={l_userID};
        ArrayList<String>l_userList=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User", "USER", "UVW_USER_PROFILE",l_userPkey);
        String l_userType=l_userList.get(13).trim();
        dbg("l_userType"+l_userType);
        String currentDate=bs.getCurrentDate();
        String l_studentID=studentOtherActivity.getStudentID();
        String l_activityID=studentOtherActivity.getActivityID();
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String result=studentOtherActivity.getResult();
        
         if(l_userType.equals("P")){
        
            String confirmationDate=currentDate;
            String participationStatus="Y";

            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY", 65, l_studentID,l_activityID,participationStatus,confirmationDate,result,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
        
         }else{

            
            String[] l_pkey={l_activityID};
            DBRecord activityRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY", "SVW_STUDENT_OTHER_ACTIVITY", l_pkey, session, dbSession);
            String previousStatus=activityRec.getRecord().get(2).trim();
            String previousConfirmationDate=activityRec.getRecord().get(3).trim();
            String currentStatus=studentOtherActivity.getIntrest();
            dbg("previousStatus"+previousStatus);
            dbg("previousConfirmationDate"+previousConfirmationDate);
            dbg("currentStatus"+currentStatus);
            
            String confirmationDate=null;
            
            if(!previousStatus.equals(currentStatus)){
                
                if(currentStatus.equals("Y")){
                
                    confirmationDate=currentDate;
                
                }else{
                    confirmationDate="";
                }
                
            }else{
                
                confirmationDate=previousConfirmationDate;
            }
            
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY", 65, l_studentID,l_activityID,currentStatus,confirmationDate,result,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
            
        
         }
        
        
        
        
      
        
                          
                  
        
        
        dbg("end of student otherActivity create"); 
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
        dbg("inside student otherActivity--->auth update"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<StudentOtherActivity> reqBody = request.getReqBody();
        StudentOtherActivity studentOtherActivity =reqBody.get();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_studentID=studentOtherActivity.getStudentID();
        String l_activityID=studentOtherActivity.getActivityID();
        String[] l_primaryKey={l_activityID};
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("7", l_checkerID);
         l_column_to_update.put("9", l_checkerDateStamp);
         l_column_to_update.put("11", l_authStatus);
         l_column_to_update.put("14", l_checkerRemarks);
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY", "SVW_STUDENT_OTHER_ACTIVITY", l_primaryKey, l_column_to_update, session);
         dbg("end of student otherActivity--->auth update");          
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
//    public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
//        
//       Map<String,String> l_column_to_update;
//                
//       try{ 
//        IDBTransactionService dbts=inject.getDBTransactionService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        RequestBody<StudentOtherActivity> reqBody = request.getReqBody();
//        StudentOtherActivity studentOtherActivity =reqBody.get();
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
//        String l_studentID=studentOtherActivity.getStudentID();
//        String l_activityID=studentOtherActivity.getActivityID();
//        String l_activityName=studentOtherActivity.getActivityName();
//        String l_activityType=studentOtherActivity.getActivityType();
//        String l_level=studentOtherActivity.getLevel();
//        String l_venue=studentOtherActivity.getVenue();
//        String l_date=studentOtherActivity.getDate();
//        String l_result=studentOtherActivity.getResult();
//        String l_intrest=studentOtherActivity.getIntrest();
//        String l_dueDate=studentOtherActivity.getDueDate();
//        l_column_to_update=new HashMap();
//        l_column_to_update.put("1", l_studentID);
//        l_column_to_update.put("2", l_activityID);
//        l_column_to_update.put("3", l_activityName);
//        l_column_to_update.put("4", l_activityType);
//        l_column_to_update.put("5", l_level);
//        l_column_to_update.put("6", l_venue);
//        l_column_to_update.put("7", l_date);
//        l_column_to_update.put("8", l_result);
//        l_column_to_update.put("9", l_intrest);
//        l_column_to_update.put("10", l_makerID);
//        l_column_to_update.put("11", l_checkerID);
//        l_column_to_update.put("12", l_makerDateStamp);
//        l_column_to_update.put("13", l_checkerDateStamp);
//        l_column_to_update.put("14", l_recordStatus);
//        l_column_to_update.put("15", l_authStatus);
//        l_column_to_update.put("16", l_versionNumber);
//        l_column_to_update.put("17", l_makerRemarks);
//        l_column_to_update.put("18", l_checkerRemarks);
//        l_column_to_update.put("19", l_dueDate);
//         
//             String[] l_primaryKey={l_activityID};
//             dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_OTHER_ACTIVITY", l_primaryKey, l_column_to_update, session);   
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
//    }

   public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
        
       Map<String,String> l_column_to_update;
                
       try{ 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IPDataService pds=inject.getPdataservice();
        String l_userID=request.getReqHeader().getUserID();
        RequestBody<StudentOtherActivity> reqBody = request.getReqBody();
        StudentOtherActivity studentOtherActivity =reqBody.get();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        BSValidation bsv=inject.getBsv(session);
        String l_instituteID=request.getReqHeader().getInstituteID();   
        BusinessService bs=inject.getBusinessService(session);
        String[] l_userPkey={l_userID};
        ArrayList<String>l_userList=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User", "USER", "UVW_USER_PROFILE",l_userPkey);
        String l_userType=l_userList.get(13).trim();
        String currentDate=bs.getCurrentDate();
        String l_studentID=studentOtherActivity.getStudentID();
        String l_activityID=studentOtherActivity.getActivityID();
        String[] l_pkey={l_activityID};
  

        if(l_userType.equals("P")){
            
            l_column_to_update= new HashMap();
            l_column_to_update.put("3", "Y");
            l_column_to_update.put("4", currentDate);
            String[] l_primaryKey={l_activityID};
            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY","SVW_STUDENT_OTHER_ACTIVITY", l_primaryKey, l_column_to_update, session);
        }else{
            
            
            DBRecord activityRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY", "SVW_STUDENT_OTHER_ACTIVITY", l_pkey, session, dbSession);
            String previousStatus=activityRec.getRecord().get(2).trim();
            String previousConfirmationDate=activityRec.getRecord().get(3).trim();
            String currentStatus=studentOtherActivity.getIntrest();
            String result=studentOtherActivity.getResult();
            String confirmationDate=null;
            
            if(previousStatus.equals(currentStatus)){
                
                confirmationDate=currentDate;
                
            }else{
                
                confirmationDate=previousConfirmationDate;
            }
            
            
            l_column_to_update= new HashMap();
            l_column_to_update.put("3", currentStatus);
            l_column_to_update.put("4", confirmationDate);
            l_column_to_update.put("5", result);
            String[] l_primaryKey={l_activityID};
            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY","SVW_STUDENT_OTHER_ACTIVITY", l_primaryKey, l_column_to_update, session);
            
            
        }
        
        
        
        
        
        
      
        
                          
                  
        
        
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
        dbg("inside student otherActivity delete");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentOtherActivity> reqBody = request.getReqBody();
        StudentOtherActivity studentOtherActivity =reqBody.get();
        String l_studentID=studentOtherActivity.getStudentID();
        String l_activityID=studentOtherActivity.getActivityID();
        String[] l_primaryKey={l_activityID};

             dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_OTHER_ACTIVITY", l_primaryKey, session);
          
             dbg("end of student otherActivity delete");
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
        dbg("inside student otherActivity--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentOtherActivity> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        StudentOtherActivity studentOtherActivity =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_studentID=studentOtherActivity.getStudentID();
        String l_activityID=studentOtherActivity.getActivityID();
        String[] l_primaryKey={l_activityID};
        DBRecord otherActivityRec=null;
        
        try{
        
        
               otherActivityRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY", "SVW_STUDENT_OTHER_ACTIVITY", l_primaryKey, session, dbSession);
       
        
        }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", l_activityID);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
        
        
        
        buildBOfromDB(otherActivityRec);
        
          dbg("end of  completed student otherActivity--->view");    
        } catch(BSValidationException ex){
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
      private void buildBOfromDB(DBRecord p_studentOtherActivityRecord)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<StudentOtherActivity> reqBody = request.getReqBody();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           BusinessService bs=inject.getBusinessService(session);
           String l_instituteID=request.getReqHeader().getInstituteID();
           StudentOtherActivity studentOtherActivity =reqBody.get();
           String activityID=studentOtherActivity.getActivityID();
           String studentID=studentOtherActivity.getStudentID();
           ArrayList<String>l_studentOtherActivityList= p_studentOtherActivityRecord.getRecord();
           
           if(l_studentOtherActivityList!=null&&!l_studentOtherActivityList.isEmpty()){
               
            studentOtherActivity.setStudentName(bs.getStudentName(studentID, l_instituteID, session, dbSession, inject));
        
            String participationStatus=l_studentOtherActivityList.get(2).trim();
            
            if(participationStatus.equals(" ")||participationStatus.equals("")){
                
                participationStatus="N";
            }
            
            
            studentOtherActivity.setIntrest(participationStatus);
          
            
            
            
            studentOtherActivity.setConfirmationDate(l_studentOtherActivityList.get(3).trim());
            studentOtherActivity.setResult(l_studentOtherActivityList.get(4).trim());
            
            String[] l_pkey={activityID};
            
            DBRecord otherActivityRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY","IVW_OTHER_ACTIVITY", l_pkey, session,dbSession);
            
            
            studentOtherActivity.setActivityName(otherActivityRecord.getRecord().get(3).trim());
            studentOtherActivity.setActivityType(otherActivityRecord.getRecord().get(4).trim());
            studentOtherActivity.setLevel(otherActivityRecord.getRecord().get(5).trim());
            studentOtherActivity.setVenue(otherActivityRecord.getRecord().get(6).trim());
            studentOtherActivity.setDate(otherActivityRecord.getRecord().get(7).trim());
            studentOtherActivity.setDueDate(otherActivityRecord.getRecord().get(8).trim());
            
            request.getReqAudit().setMakerID(l_studentOtherActivityList.get(5).trim());
            request.getReqAudit().setCheckerID(l_studentOtherActivityList.get(6).trim());
            request.getReqAudit().setMakerDateStamp(l_studentOtherActivityList.get(7).trim());
            request.getReqAudit().setCheckerDateStamp(l_studentOtherActivityList.get(8).trim());
            request.getReqAudit().setRecordStatus(l_studentOtherActivityList.get(9).trim());
            request.getReqAudit().setAuthStatus(l_studentOtherActivityList.get(10).trim());
            request.getReqAudit().setVersionNumber(l_studentOtherActivityList.get(11).trim());
            request.getReqAudit().setMakerRemarks(l_studentOtherActivityList.get(12).trim());
            request.getReqAudit().setCheckerRemarks(l_studentOtherActivityList.get(13).trim());
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
        dbg("inside student otherActivity buildJsonResFromBO");    
        RequestBody<StudentOtherActivity> reqBody = request.getReqBody();
        StudentOtherActivity studentOtherActivity =reqBody.get();
        body=Json.createObjectBuilder().add("studentID", studentOtherActivity.getStudentID())
                                       .add("studentName", studentOtherActivity.getStudentName())
                                            .add("activityID", studentOtherActivity.getActivityID())
                                            .add("activityName", studentOtherActivity.getActivityName())
                                            .add("activityType", studentOtherActivity.getActivityType())
                                            .add("participate", studentOtherActivity.getIntrest())
                                            .add("level", studentOtherActivity.getLevel())
                                            .add("venue", studentOtherActivity.getVenue())
                                            .add("date", studentOtherActivity.getDate())
                                            .add("dueDate", studentOtherActivity.getDueDate())
                                            .add("confirmationDate", studentOtherActivity.getConfirmationDate())
                                            .add("result", studentOtherActivity.getResult()).build();
                                            
              dbg(body.toString());  
           dbg("end of student otherActivity buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student otherActivity--->businessValidation");    
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
       dbg("end of student otherActivity--->businessValidation"); 
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
        dbg("inside student otherActivity master mandatory validation");
        RequestBody<StudentOtherActivity> reqBody = request.getReqBody();
        StudentOtherActivity studentOtherActivity =reqBody.get();
        
         if(studentOtherActivity.getStudentID()==null||studentOtherActivity.getStudentID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","studentID");  
         }
         if(studentOtherActivity.getActivityID()==null||studentOtherActivity.getActivityID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","activityID");  
         }
          
        dbg("end of student otherActivity master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student otherActivity masterDataValidation");
             RequestBody<StudentOtherActivity> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             StudentOtherActivity studentOtherActivity =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_studentID=studentOtherActivity.getStudentID();
             
             if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","studentID");
             }
            
            
            dbg("end of student otherActivity masterDataValidation");
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
        RequestBody<StudentOtherActivity> reqBody = request.getReqBody();
        StudentOtherActivity studentOtherActivity =reqBody.get();
        
        try{
            
            dbg("inside student otherActivity detailMandatoryValidation");
           
            
            
            
           dbg("end of student otherActivity detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student otherActivity detailDataValidation");
             RequestBody<StudentOtherActivity> reqBody = request.getReqBody();
             StudentOtherActivity studentOtherActivity =reqBody.get();
             IDBReadBufferService readBuffer=inject.getDBReadBufferService();
             String l_instituteID=request.getReqHeader().getInstituteID();
             BSValidation bsv=inject.getBsv(session);
             IPDataService pds=inject.getPdataservice();
             String l_userID=request.getReqHeader().getUserID();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             String[] l_userPkey={l_userID};
             ArrayList<String>l_userList=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User", "USER", "UVW_USER_PROFILE",l_userPkey);
             String l_userType=l_userList.get(13).trim();
             String l_studentID=studentOtherActivity.getStudentID();
             String l_activityID=studentOtherActivity.getActivityID();
             String[] l_pkey={l_activityID};
             String l_operation=request.getReqHeader().getOperation();

             DBRecord studentRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY", "SVW_STUDENT_OTHER_ACTIVITY", l_pkey, session, dbSession);
             String previousStatus=studentRec.getRecord().get(2).trim();
             String previousResult=studentRec.getRecord().get(4).trim();

             DBRecord instituteRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY","IVW_OTHER_ACTIVITY", l_pkey, session,dbSession);
             String dueDate=instituteRec.getRecord().get(8).trim();
            
             
             if(l_userType.equals("P")){
                 
                


                if(previousStatus.equals("Y")){  

                  session.getErrorhandler().log_app_error("BS_VAL_052", null);
                  throw new BSValidationException("BSValidationException");

                }
                
                
                    if(!bsv.pastDateValidation(dueDate, session, dbSession, inject)){

                        session.getErrorhandler().log_app_error("BS_VAL_044", null);
                        throw new BSValidationException("BSValidationException");
                    }

                
                
             
             }else{
                 
                String previousActivityDescription=instituteRec.getRecord().get(3).trim();
                String previousActivityType=instituteRec.getRecord().get(4).trim();
                String previousLevel =instituteRec.getRecord().get(5).trim();
                String previousVenue=instituteRec.getRecord().get(6).trim();
                String previousEventDate=instituteRec.getRecord().get(7).trim();
                String previousDueDate=instituteRec.getRecord().get(8).trim();
                 
                 
                String l_activityName=studentOtherActivity.getActivityName();
                String l_activityType=studentOtherActivity.getActivityType();
                String l_level=studentOtherActivity.getLevel();
                String l_venue=studentOtherActivity.getVenue();
                String l_date=studentOtherActivity.getDate();
                String l_dueDate= studentOtherActivity.getDueDate();
                String l_result=studentOtherActivity.getResult();
                
                if(!previousActivityDescription.equals(l_activityName)){
                    
                    session.getErrorhandler().log_app_error("BS_VAL_046", null);
                    throw new BSValidationException("BSValidationException");
                }
                
                if(!previousActivityType.equals(l_activityType)){
                    
                    session.getErrorhandler().log_app_error("BS_VAL_046", null);
                    throw new BSValidationException("BSValidationException");
                }
                
                if(!previousLevel.equals(l_level)){
                    
                    session.getErrorhandler().log_app_error("BS_VAL_046", null);
                    throw new BSValidationException("BSValidationException");
                }
                
                if(!l_venue.equals(previousVenue)){
                    
                    session.getErrorhandler().log_app_error("BS_VAL_046", null);
                    throw new BSValidationException("BSValidationException");
                }
                
                if(!l_date.equals(previousEventDate)){
                    
                    session.getErrorhandler().log_app_error("BS_VAL_046", null);
                    throw new BSValidationException("BSValidationException");
                }
                
                if(!l_dueDate.equals(previousDueDate)){
                    
                    session.getErrorhandler().log_app_error("BS_VAL_046", null);
                    throw new BSValidationException("BSValidationException");
                }
                
                
                
                if(!l_result.equals(previousResult)){
                    
                    
                    if(!bsv.futureDateValidation(previousEventDate, session, dbSession, inject)){

                        session.getErrorhandler().log_app_error("BS_VAL_051", null);
                         throw new BSValidationException("BSValidationException");
                    }
                    
                    
                }
                
                
                 
                if(previousStatus.equals(" ")||previousStatus.equals("")||previousStatus.equals("N")){
                    
                    
                    String currentStatus=studentOtherActivity.getIntrest();
                    
                    if(currentStatus.equals("Y")){
                        
                        
                        
                        if(!bsv.pastDateValidation(dueDate, session, dbSession, inject)){

                            session.getErrorhandler().log_app_error("BS_VAL_044", null);
                           throw new BSValidationException("BSValidationException");
                        }
                        
                        
                    }
                    
                    
                }
                
             }
             
             
             
             
             
            
            dbg("end of student otherActivity detailDataValidation");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch(BSValidationException ex){
            throw ex;    
        } catch(DBValidationException ex){
            throw ex;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        
        return status;
              
    }
    public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     try{
        dbg("inside StudentOtherActivity--->getExistingAudit") ;
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
               dbg("inside StudentOtherActivity--->getExistingAudit--->Service--->UserOtherActivity");  
               RequestBody<StudentOtherActivity> studentOtherActivityBody = request.getReqBody();
               StudentOtherActivity studentOtherActivity =studentOtherActivityBody.get();
               String l_studentID=studentOtherActivity.getStudentID();
               String l_activityID=studentOtherActivity.getActivityID();
               String[] l_pkey={l_activityID};
               DBRecord l_studentOtherActivityRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY", "SVW_STUDENT_OTHER_ACTIVITY", l_pkey, session, dbSession);
               exAudit.setAuthStatus(l_studentOtherActivityRecord.getRecord().get(10).trim());
               exAudit.setMakerID(l_studentOtherActivityRecord.getRecord().get(5).trim());
               exAudit.setRecordStatus(l_studentOtherActivityRecord.getRecord().get(9).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_studentOtherActivityRecord.getRecord().get(11).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of StudentOtherActivity--->getExistingAudit") ;
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
        dbg("Inside relationshipProcessing"); 
        RequestBody<StudentOtherActivity> reqBody = request.getReqBody();
        StudentOtherActivity otherActivity =reqBody.get();
        BusinessService bs=inject.getBusinessService(session);
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IPDataService pds=inject.getPdataservice();
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String recordStatus=request.getReqAudit().getRecordStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String currentDate=bs.getCurrentDate();
        String l_studentID=otherActivity.getStudentID();
        String l_activityID=otherActivity.getActivityID();
        String l_result=otherActivity.getResult();
        String l_userID=request.getReqHeader().getUserID();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String[] l_userPkey={l_userID};
        ArrayList<String>l_userList=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User", "USER", "UVW_USER_PROFILE",l_userPkey);
        String l_userType=l_userList.get(13).trim();
        String confirmationDate=null;
        String participationStatus=null;
        
        
        if(l_userType.equals("P")){
        
            confirmationDate=currentDate;
            participationStatus="Y";

        
         }else{

            String[] l_pkey={l_activityID};
            DBRecord activityRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY", "SVW_STUDENT_OTHER_ACTIVITY", l_pkey, session, dbSession);
            String previousStatus=activityRec.getRecord().get(2).trim();
            String previousConfirmationDate=activityRec.getRecord().get(3).trim();
            participationStatus=otherActivity.getIntrest();
            
            if(!previousStatus.equals(participationStatus)){
                
                
                if(participationStatus.equals("Y")){
                
                   confirmationDate=currentDate;
                   
                }else{
                    confirmationDate="";
                }
                
            }else{
                
                confirmationDate=previousConfirmationDate;
            }
            
            
         }
        
        
        
        if(l_versionNumber.equals("1")){
            
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_activityID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_activityID,"OTHER_ACTIVITY",315,l_studentID,participationStatus,confirmationDate,l_result);
            
            
            
        }else if(!l_versionNumber.equals("1")&&!recordStatus.equals("D")){
             String[] l_primaryKey={l_studentID};
             Map<String,String>column_to_update=new HashMap();
             column_to_update.put("2", participationStatus);
             column_to_update.put("3", confirmationDate);
             column_to_update.put("4", l_result);

             
             try{
             
             
               dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_activityID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_activityID,"OTHER_ACTIVITY", "INSTITUTE_OTHER_ACTIVITY_TRACKING", l_primaryKey, column_to_update, session);
            
             
             }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_activityID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_activityID,"OTHER_ACTIVITY",315,l_studentID,participationStatus,confirmationDate,l_result);
                        
                    }else{
                        
                        throw ex;
                    }
            }
             
             
        }else{
            
             String[] l_primaryKey={l_studentID};
             dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_activityID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_activityID,"OTHER_ACTIVITY", "INSTITUTE_OTHER_ACTIVITY_TRACKING", l_primaryKey, session);
        }
        
        
       
       

        dbg("End of relationshipProcessing");
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
    
 private void createOrUpdateStudentCalender()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
     
        try{
        dbg("inside createOrUpdateStudentCalender") ;  
        boolean recordExistence=true;
        IDBTransactionService dbts=inject.getDBTransactionService();
        BusinessService bs=inject.getBusinessService(session);
        RequestBody<StudentOtherActivity> reqBody = request.getReqBody();
        StudentOtherActivity studentOtherActivity =reqBody.get();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_studentID=studentOtherActivity.getStudentID();
        String l_date=studentOtherActivity.getDate();
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
        String l_year=convertedDate.getYear();
        String l_month=convertedDate.getMonth();
        String l_day=convertedDate.getDay();
        int day=Integer.parseInt(l_day);
        String[] l_pkey={l_studentID,l_year,l_month};
        DBRecord studentCalenderRec;
        String l_previousMonthEvent=null;
        String newDayEvent=null;
        String currentEvent=getCurrentEventFromBO();
        String pKeyOfCurrentEvent=studentOtherActivity.getActivityID();
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
            if(l_previousDayEvents.contains("O")){
                dbg("l_previousDayEvents.contains O");
                String[]l_eventArray=l_previousDayEvents.split("//");
                dbg("l_eventArray size"+l_eventArray.length);
                boolean pkExistence=false;
                for(int i=1;i<l_eventArray.length;i++){
                    previousEvent=l_eventArray[i];
                    dbg("previousEvent"+previousEvent);
                    String l_pkColumnsWithoutVersion=bs.getPKcolumnswithoutVersion("STUDENT","SVW_STUDENT_OTHER_ACTIVITY",session,inject);
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
                dbg("l_previousDayEvents not contains O");
                newDayEvent=l_previousDayEvents.concat(currentEvent);
            }
            
            String newMonthEvent=bs.setDayEventinMonthEvent(l_previousMonthEvent,l_previousDayEvents,newDayEvent,l_day);
            Map<String,String>l_column_to_update=new HashMap();
            l_column_to_update.put("4", newMonthEvent);
             
             dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_CALENDER", l_pkey, l_column_to_update, session);   
            

        }else{
            String l_monthEvent=bs.createMonthEvent(l_date);
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
        RequestBody<StudentOtherActivity> reqBody = request.getReqBody();
        StudentOtherActivity studentOtherActivity =reqBody.get();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_studentID=studentOtherActivity.getStudentID();
        String l_date=studentOtherActivity.getDate();
        ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
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
        RequestBody<StudentOtherActivity> reqBody = request.getReqBody();
        StudentOtherActivity studentOtherActivity =reqBody.get(); 
        String l_activityID=studentOtherActivity.getActivityID();
        String l_activityName=studentOtherActivity.getActivityName();
        String l_venue=studentOtherActivity.getVenue();
        String currentEvent="O"+","+l_activityID+","+l_activityName+","+l_venue+"//";
         
        dbg("currentEvent"+currentEvent); 
        return  currentEvent;
     }catch (Exception ex) {
       dbg(ex);
       throw new BSProcessingException("Exception" + ex.toString());
     }

 }
}
