/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentpayment;

import com.ibd.businessViews.IStudentPaymentService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.institute.institutepayment.InstitutePayment;
import com.ibd.cohesive.app.business.institute.institutepayment.Payments;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.exception.ExceptionHandler;
import com.ibd.cohesive.app.business.util.message.request.Parsing;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.read.IDBReadService;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
//@Local(IStudentPaymentService.class)
@Remote(IStudentPaymentService.class)
@Stateless
public class StudentPaymentService implements IStudentPaymentService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentPaymentService(){
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
       dbg("inside StudentPaymentService--->processing");
       dbg("StudentPaymentService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<StudentPayment> reqBody = request.getReqBody();
       StudentPayment studentPayment =reqBody.get();
       l_lockKey=studentPayment.getPaymentID();
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<IStudentPaymentService>studentPaymentEJB=new BusinessEJB();
       studentPaymentEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentPaymentEJB);
       if(request.getReqHeader().getOperation().equals("View")){
           
         String studentID=  bs.studentValidation(studentPayment.getStudentID(), studentPayment.getStudentName(), request.getReqHeader().getInstituteID(), session, dbSession, inject);
         
          
         if(studentID==null||studentID.isEmpty()){
             
             errhandler.log_app_error("BS_VAL_002","Student ID or Name");  
             throw new BSValidationException("BSValidationException");
         }
         
         studentPayment.setStudentID(studentID);
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
      
       bs.businessServiceProcssing(request, exAudit, inject,studentPaymentEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentPaymentEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student payment");
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
      StudentPayment studentPayment=new StudentPayment();
      RequestBody<StudentPayment> reqBody = new RequestBody<StudentPayment>(); 
           
      try{
      dbg("inside student payment buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      studentPayment.setStudentID(l_body.getString("studentID"));
      studentPayment.setStudentName(l_body.getString("studentName"));
      studentPayment.setPaymentID(l_body.getString("paymentID"));
      studentPayment.setPaymentDate(l_body.getString("paymentDate"));
      if(!l_operation.equals("View")){
          studentPayment.setPaymentMode(l_body.getString("paymentMode"));
          studentPayment.setPaymentpaid(l_body.getString("paymentPaid"));
          
          
      }
        reqBody.set(studentPayment);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
    }
    
     

    
    public void authUpdate()throws DBValidationException,DBProcessingException,BSProcessingException{
        
     try{ 
        dbg("inside student payment--->auth update"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<StudentPayment> reqBody = request.getReqBody();
        StudentPayment studentPayment =reqBody.get();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_studentID=studentPayment.getStudentID();
        String l_paymentID=studentPayment.getPaymentID();
        String[] l_primaryKey={l_paymentID};
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("12", l_checkerID);
         l_column_to_update.put("14", l_checkerDateStamp);
         l_column_to_update.put("16", l_authStatus);
         l_column_to_update.put("19", l_checkerRemarks);
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_PAYMENT", l_primaryKey, l_column_to_update, session);
         dbg("end of student payment--->auth update");          
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
         }

    
    public void delete()throws DBValidationException,DBProcessingException,BSProcessingException{
        try{
        dbg("inside student payment delete");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentPayment> reqBody = request.getReqBody();
        StudentPayment studentPayment =reqBody.get();
        String l_studentID=studentPayment.getStudentID();
        String l_paymentID=studentPayment.getPaymentID();
             
             String[] l_primaryKey={l_paymentID};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_PAYMENT", l_primaryKey, session);
            dbg("end of student payment delete");
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
        dbg("inside student payment--->view");    
        IDBReadService dbrs=inject.getDbreadservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentPayment> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        StudentPayment studentPayment =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_studentID=studentPayment.getStudentID();
        String l_paymentID=studentPayment.getPaymentID();
        String l_paymentDate=studentPayment.getPaymentDate();
        String[] l_pkey={l_paymentID};
        DBRecord paymentRec=null;
        Map<String,DBRecord>paymentsMap=null;
        List<DBRecord>paymentsList=null;
        
            try{

        paymentRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_paymentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT","INSTITUTE_PAYMENT_MASTER", l_pkey, session,dbSession);
                 
          String studentID= paymentRec.getRecord().get(1).trim();
                 
          dbg("studentID"+studentID);
          dbg("l_studentID"+l_studentID);
          if(!studentID.equals(l_studentID)){
              session.getErrorhandler().log_app_error("BS_VAL_013", l_paymentID);
              throw new BSValidationException("BSValidationException");
          }
             
          
            paymentsMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_paymentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT","INSTITUTE_PAYMENT", session,dbSession);
           
            dbg("paymentsMap"+paymentsMap.size());
            
             paymentsList=paymentsMap.values().stream().filter(rec->rec.getRecord().get(2).trim().equals(l_paymentID)).collect(Collectors.toList());

             dbg("paymentsList"+paymentsList.size());
             
           if(paymentsList.isEmpty()){
               session.getErrorhandler().log_app_error("BS_VAL_013", l_paymentID);
              throw new BSValidationException("BSValidationException");
           }          
          
            }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", l_paymentID);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
        
        
                buildBOfromDB(paymentRec,paymentsList);

        
          dbg("end of  completed student payment--->view");                
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
      private void buildBOfromDB(DBRecord p_paymentRecord,List<DBRecord>paymentsList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           RequestBody<StudentPayment> reqBody = request.getReqBody();
           String l_instituteID=request.getReqHeader().getInstituteID();
           StudentPayment studentPayment =reqBody.get();
            ArrayList<String>l_paymentList=p_paymentRecord.getRecord();
           BusinessService bs=inject.getBusinessService(session);
           
           if(l_paymentList!=null&&!l_paymentList.isEmpty()){
               
            studentPayment.setStudentID(l_paymentList.get(1).trim());  
               String studentID=l_paymentList.get(1).trim();
               String studentName=bs.getStudentName(studentID, l_instituteID, session, dbSession, inject);
               studentPayment.setStudentName(studentName);
               studentPayment.setPaymentMode(l_paymentList.get(3).trim());   
               studentPayment.setPaymentpaid(l_paymentList.get(4).trim());
               studentPayment.setPaymentDate(l_paymentList.get(5).trim());
            
               
               studentPayment.payments=new Payments[paymentsList.size()];
               int i=0;
               for(DBRecord paymentRec:paymentsList){
               
               studentPayment.payments[i]=new Payments();
               studentPayment.payments[i].setFeeID(paymentRec.getRecord().get(3).trim());
               String feeID=paymentRec.getRecord().get(3).trim();
               studentPayment.payments[i].setPaymentFortheFee(paymentRec.getRecord().get(4).trim());
               String[] l_pkey={feeID};
               DBRecord feeRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", l_pkey, session,dbSession);
               
               studentPayment.payments[i].setFeeDescription(feeRecord.getRecord().get(3).trim());
               studentPayment.payments[i].setFeeAmount(feeRecord.getRecord().get(5).trim());
               studentPayment.payments[i].setDueDate(feeRecord.getRecord().get(6).trim());
               
//                float outStanding= getOutStanding(feeID);
//               float totalOutStanding=0;
//               
//
//               
//                  totalOutStanding=outStanding;
//               
               
               
               
               studentPayment.payments[i].setOutStanding(paymentRec.getRecord().get(6).trim());
               i++;
               }
               
               
               
               
             }
            
          dbg("end of  buildBOfromDB"); 
        
//        }catch(BSValidationException ex){
//          throw ex;  
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
    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside student payment buildJsonResFromBO");    
        RequestBody<StudentPayment> reqBody = request.getReqBody();
        StudentPayment payment =reqBody.get();
        BusinessService bs=inject.getBusinessService(session);
         JsonArrayBuilder paymentArray=Json.createArrayBuilder();
         String instituteID=request.getReqHeader().getInstituteID();
                
                for(int i=0;i<payment.payments.length;i++){
                    String outStandingAmount=bs.formatCurrency(payment.payments[i].getOutStanding(), instituteID, session, dbSession, inject);
                        String paymentForFee=bs.formatCurrency(payment.payments[i].getPaymentFortheFee(), instituteID, session, dbSession, inject);
                    paymentArray.add(Json.createObjectBuilder().add("feeID", payment.payments[i].getFeeID())
                                                               .add("feeDescription", payment.payments[i].getFeeDescription())
                                                               .add("dueDate", payment.payments[i].getDueDate())
                                                               .add("outStanding", outStandingAmount)
                                                               .add("paymentForFee",paymentForFee));
                    
                }
                
                String balanceAmount=this.getBalanceAmount();
                
                
                body=Json.createObjectBuilder()
                                            .add("studentID", payment.getStudentID())
                                            .add("studentName", payment.getStudentName())
                                            .add("paymentID", payment.getPaymentID())
                                            .add("paymentMode", payment.getPaymentMode())
                                            .add("paymentPaid", payment.getPaymentpaid())
                                            .add("paymentDate", payment.getPaymentDate())
                                            .add("balanceAmount", balanceAmount)
                                            .add("Payments",paymentArray)
                                               .build();
           
                                            
              dbg(body.toString());  
           dbg("end of student payment buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
    
    
    
    private String getBalanceAmount()throws BSProcessingException{
           
           try{
               dbg("inside getBalanceAmount");
               float balanceAmount=0.0f;
               RequestBody<StudentPayment> reqBody = request.getReqBody();
               StudentPayment payment =reqBody.get();
               float totalPaymentAmount=Float.parseFloat(payment.getPaymentpaid());
               dbg("totalPaymentAmount"+totalPaymentAmount);
               float paymentsOfAllFee=0.0f;
               for(int i=0;i<payment.payments.length;i++){
                   
                   paymentsOfAllFee=paymentsOfAllFee+Float.parseFloat(payment.payments[i].getPaymentFortheFee());
               
                   
               }
               dbg("paymentsOfAllFee"+paymentsOfAllFee);
               balanceAmount=totalPaymentAmount-paymentsOfAllFee;
               
               
               dbg("balanceAmount"+balanceAmount);
               
               dbg("end of getBalanceAmount");
               
               
               return Float.toString(balanceAmount);
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception".concat(ex.toString()));
        }
       }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student payment--->businessValidation");    
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
       dbg("end of student payment--->businessValidation"); 
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
        dbg("inside student payment master mandatory validation");
        RequestBody<StudentPayment> reqBody = request.getReqBody();
        StudentPayment studentPayment =reqBody.get();
        
         if(studentPayment.getStudentID()==null||studentPayment.getStudentID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","studentID");  
         }
          if(studentPayment.getPaymentID()==null||studentPayment.getPaymentID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","paymentID");  
         }
          
          if(studentPayment.getPaymentDate()==null||studentPayment.getPaymentDate().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","payment Date");  
         } 
        dbg("end of student payment master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student payment masterDataValidation");
             RequestBody<StudentPayment> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             StudentPayment studentPayment =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_studentID=studentPayment.getStudentID();
             
             if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","studentID");
             }
             
            
            dbg("end of student payment masterDataValidation");
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
        RequestBody<StudentPayment> reqBody = request.getReqBody();
        StudentPayment studentPayment =reqBody.get();
        
        try{
            
            dbg("inside student payment detailMandatoryValidation");
            if(studentPayment.getPaymentMode()==null||studentPayment.getPaymentMode().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","paymentMode");  
            }
            if(studentPayment.getPaymentpaid()==null||studentPayment.getPaymentpaid().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","paymentPaid");  
            }
            if(studentPayment.getPaymentDate()==null||studentPayment.getPaymentDate().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","paymentDate");  
            }
            
            
           dbg("end of student payment detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student payment detailDataValidation");
             RequestBody<StudentPayment> reqBody = request.getReqBody();
             StudentPayment studentPayment =reqBody.get();
             BSValidation bsv=inject.getBsv(session);
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_paymentMode=studentPayment.getPaymentMode();
             String l_paymentPaid=studentPayment.getPaymentpaid();
             String l_paymentDate=studentPayment.getPaymentDate();

             if(!bsv.paymentModeValidation(l_paymentMode, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","feeType");
             }
             if(!bsv.amountValidation(l_paymentPaid, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","paymentPaid");
             }
             if(!bsv.dateFormatValidation(l_paymentDate, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","paymentDate");
             }
              
            dbg("end of student payment detailDataValidation");
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
    public boolean feeIDValidation(String feeID,String p_instituteID)throws DBValidationException,DBProcessingException,BSProcessingException{
       boolean status=true;
       try{
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           RequestBody<StudentPayment> reqBody = request.getReqBody();
           StudentPayment studentPayment =reqBody.get();
           String l_studentID=studentPayment.getStudentID();
           String l_instituteID=request.getReqHeader().getInstituteID();
           try{
                String[] pkey={feeID};
                readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_FEE_MANAGEMENT", pkey, session, dbSession);
             }catch(DBValidationException ex){ 
                 if(ex.toString().contains("DB_VAL_011")){
                     session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                     status=false;
                 }else{
                     throw ex;
                 }
             }
           
           return status;
       }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(Exception ex){
            dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
       
   }
    
    
    public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     try{
        dbg("inside StudentPayment--->getExistingAudit") ;
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
        exAudit=new ExistingAudit();
        if(!(l_operation.equals("Create")||l_operation.equals("View"))){
             
              if(l_operation.equals("AutoAuth")&&l_versionNumber.equals("1")){
                return null;
              }else{  
               dbg("inside StudentPayment--->getExistingAudit--->Service--->UserPayment");  
               RequestBody<StudentPayment> studentPaymentBody = request.getReqBody();
               StudentPayment studentPayment =studentPaymentBody.get();
               String l_studentID=studentPayment.getStudentID();
               String l_paymentID=studentPayment.getPaymentID();
               String[] l_pkey={l_paymentID};
               DBRecord l_studentPaymentRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_PAYMENT", l_pkey, session, dbSession);
               exAudit.setAuthStatus(l_studentPaymentRecord.getRecord().get(15).trim());
               exAudit.setMakerID(l_studentPaymentRecord.getRecord().get(10).trim());
               exAudit.setRecordStatus(l_studentPaymentRecord.getRecord().get(14).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_studentPaymentRecord.getRecord().get(16).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of StudentPayment--->getExistingAudit") ;
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
        
      
}
    
    
    
   
}
