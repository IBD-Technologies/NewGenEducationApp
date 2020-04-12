
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.institutepayment;

import com.ibd.businessViews.IInstitutePaymentService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
//@Local(IInstitutePaymentService.class)
@Remote(IInstitutePaymentService.class)
@Stateless
public class InstitutePaymentService implements IInstitutePaymentService {
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public InstitutePaymentService(){
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
       JsonObject replacedJson=null;
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
       dbg("inside InstitutePaymentService--->processing");
       dbg("InstitutePaymentService--->Processing--->I/P--->requestJson"+requestJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,requestJson);
       bs.requestlogging(request,requestJson, inject,session,dbSession);
       buildBOfromReq(requestJson);  
       String l_operation=request.getReqHeader().getOperation();
       RequestBody<InstitutePayment> reqBody = request.getReqBody();
           InstitutePayment payment =reqBody.get();
       if(!l_operation.equals("Create-Default")&&!l_operation.equals("View")){
       
//           RequestBody<InstitutePayment> reqBody = request.getReqBody();
//           InstitutePayment payment =reqBody.get();
           String l_paymentID=payment.getPaymentID();
           l_lockKey=l_paymentID;
           if(!businessLock.getBusinessLock(request, l_lockKey, session)){
               l_validation_status=false;
               throw new BSValidationException("BSValidationException");
            }
       String studentID=  bs.studentValidation(payment.getStudentID(), payment.getStudentName(), request.getReqHeader().getInstituteID(), session, dbSession, inject);
         
          
         if(studentID==null||studentID.isEmpty()){
             
             errhandler.log_app_error("BS_VAL_002","Student ID or Name");  
             throw new BSValidationException("BSValidationException");
         }
         
         payment.setStudentID(studentID);
         
       }
       BusinessEJB<IInstitutePaymentService>paymentEJB=new BusinessEJB();
       paymentEJB.set(this);
       exAudit=bs.getExistingAudit(paymentEJB);
           
           
      
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
      
      
       bs.businessServiceProcssing(request, exAudit, inject,paymentEJB);
       
       
       if(l_operation.equals("Create-Default")){
           
           createDefault();
       }
       if(l_operation.equals("Payment-Default")){
           
           paymentDefault();
       }

       replacedJson=replaceBalanceAmount(requestJson);
         if(l_session_created_now){
             jsonResponse= bs.buildSuccessResponse(replacedJson, request, inject, session,paymentEJB) ;
            
             tc.commit(session,dbSession);
             dbg("commit is called in  service");
        }
       dbg("InstitutePaymentService--->Processing--->O/P--->jsonResponse"+jsonResponse);     
       dbg("End of paymentService--->processing");     
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
            dbg(jsonResponse.toString());
            BSValidation bsv=inject.getBsv(session);
//            if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
            clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes              BSProcessingException ex=new BSProcessingException("response contains special characters");
               // dbg(ex);
                
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
    
    
     private  JsonObject  replaceBalanceAmount(JsonObject p_request)throws BSProcessingException,DBProcessingException{
       
       try{
       dbg("inside InstitutePaymentService--->replaceBalanceAmount"); 
       dbg("InstitutePaymentService--->replaceBalanceAmount--->I/P--->p_request"+p_request.toString()); 
       String l_operation=request.getReqHeader().getOperation();
       JsonObject body=null;
       RequestBody<InstitutePayment> reqBody = request.getReqBody();
       InstitutePayment payment =reqBody.get();
       JsonObject newRequest=null;

    if(!l_operation.equals("Payment-Default")&&!l_operation.equals("Create-Default")){
       
        JsonArray paymentArray=p_request.getJsonObject("body").getJsonArray("Payments");
        
       String balanceAmount=this.getBalanceAmount();
       
       
       body=Json.createObjectBuilder().add("instituteID", payment.getInstituteID())
                                            .add("instituteName", payment.getInstituteName())
                                            .add("studentID", payment.getStudentID())
                                            .add("studentName", payment.getStudentName())
                                            .add("paymentID", payment.getPaymentID())
                                            .add("paymentMode", payment.getPaymentMode())
                                            .add("paymentPaid", payment.getPaymentpaid())
                                            .add("paymentDate", payment.getPaymentDate())
                                            .add("balanceAmount", balanceAmount)      
                                            .add("Payments", paymentArray)
                                            .build();   
       
       
       JsonObject header=p_request.getJsonObject("header");
       
       JsonObject audit=p_request.getJsonObject("audit");
       
       
       
       newRequest=Json.createObjectBuilder().add("header", header)
                                            .add("body", body)
                                            .add("audit", audit).build();
       
       }else{
        
        newRequest=p_request;
    }
    
              return newRequest;

    
      }
        catch(Exception ex){
             dbg(ex);
            throw new BSProcessingException("BodyParsingException"+ex.toString());
        }
   }

    private  void buildBOfromReq(JsonObject p_request)throws BSValidationException,BSProcessingException,DBProcessingException{
       
       try{
       dbg("inside InstitutePaymentService--->buildBOfromReq"); 
       dbg("InstitutePaymentService--->buildBOfromReq--->I/P--->p_request"+p_request.toString()); 
       RequestBody<InstitutePayment> reqBody = new RequestBody<InstitutePayment>(); 
       InstitutePayment payment = new InstitutePayment();
       String l_operation=request.getReqHeader().getOperation();
       String l_instituteID=request.getReqHeader().getInstituteID();
       BusinessService bs=inject.getBusinessService(session);
       
       if(!l_operation.equals("Create-Default")){       
       
       JsonObject l_body=p_request.getJsonObject("body");
       payment.setInstituteID(l_body.getString("instituteID"));
       payment.setInstituteName(l_body.getString("instituteName"));
       payment.setPaymentID(l_body.getString("paymentID"));
       payment.setPaymentDate(l_body.getString("paymentDate"));
       payment.setStudentID(l_body.getString("studentID"));
       payment.setStudentName(l_body.getString("studentName"));
       payment.setPaymentMode(l_body.getString("paymentMode"));
       payment.setPaymentpaid(l_body.getString("paymentPaid"));
       
       if(!l_operation.equals("View")&&!l_operation.equals("Payment-Default")){
           
            
            
            JsonArray paymentArray=l_body.getJsonArray("Payments");
            payment.payments=new Payments[paymentArray.size()];
            
            for(int i=0;i<paymentArray.size();i++){
                
                JsonObject paymentObject=paymentArray.getJsonObject(i);
                payment.payments[i]=new Payments();
                payment.payments[i].setFeeID(paymentObject.getString("feeID"));
                String feeDescription=paymentObject.getString("feeDescription");
                
                String  paymentForFee=paymentObject.getString("paymentForFee");
                String outStanding=paymentObject.getString("outStanding");
                
                try{
                
                   paymentForFee=bs.parseCurrency(paymentForFee, l_instituteID, session, dbSession, inject);
                   payment.payments[i].setPaymentFortheFee(paymentForFee);
                }catch(ParseException ex){
                    
                    session.getErrorhandler().log_app_error("BS_VAL_003", "Payment for the fee "+feeDescription);
                    throw new BSValidationException("BSValidationException");
                }
                try{
                
                   outStanding=bs.parseCurrency(outStanding, l_instituteID, session, dbSession, inject);
                   payment.payments[i].setOutStanding(outStanding);
                }catch(ParseException ex){
                    
                    session.getErrorhandler().log_app_error("BS_VAL_003", "Due amount for the fee "+feeDescription);
                    throw new BSValidationException("BSValidationException");
                }
                
//                payment.payments[i].setPaymentFortheFee(paymentObject.getString("paymentForFee"));
//                payment.payments[i].setFeeDescription(paymentObject.getString("feeDescription"));
//                payment.payments[i].setOutStanding(paymentObject.getString("outStanding"));
//                
                
            }
            
            
            
            
            
            
            
//            payment.setAmount(l_body.getString("amount"));
//            payment.setOutStanding(l_body.getString("outStanding"));
//            payment.setRemarks(l_body.getString("remarks"));
           
        }
       }
       reqBody.set(payment);
      request.setReqBody(reqBody);
      dbg("End of InstitutePaymentService--->buildBOfromReq");
      }
        catch(BSValidationException ex){
            throw ex;
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
        String sequenceNo="P"+lock.getSequenceNo();
            
        RequestBody<InstitutePayment> reqBody = request.getReqBody();
        InstitutePayment payment =reqBody.get();
         
        payment.setPaymentID(sequenceNo);
        BusinessService bs=inject.getBusinessService(session);
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
    
    private void paymentDefault()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside Institute payment--->paymentDefault");    
        ILockService lock=inject.getLockService();
        BusinessService bs=inject.getBusinessService(session);
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();    
        RequestBody<InstitutePayment> reqBody = request.getReqBody();
        InstitutePayment payment =reqBody.get();
        String l_instituteID=payment.getInstituteID();
        String l_studentID=payment.getStudentID();
        String l_paymentID=payment.getPaymentID();
        String l_paymentMode=payment.getPaymentMode();
        String l_paymentPaid=payment.getPaymentpaid();
        String l_paymentDate=payment.getPaymentDate();
        Float paidAmout=0.0f;
       String studentName=bs.getStudentName(l_studentID, l_instituteID, session, dbSession, inject);
       payment.setStudentName(studentName);
       if(l_paymentPaid!=null&!l_paymentPaid.isEmpty()){
        
         paidAmout=Float.parseFloat(l_paymentPaid);
        
        }
        
        Map<String,DBRecord>feeMap=null;
        String dateFormat=i_db_properties.getProperty("DATE_FORMAT");
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try{
        
        
         feeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"FEE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","FEE", "STUDENT_FEE_MANAGEMENT", session, dbSession);
       
        }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_068", null);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
                }
        
        
        if(feeMap!=null){
            
            ArrayList<FeeDetail>feeDetailList=new ArrayList();
            
           Iterator<DBRecord>feeIterator= feeMap.values().iterator();
           dbg("iteration before sorting");
            while(feeIterator.hasNext()){
                
                DBRecord feeRecord=feeIterator.next();
                
                String feeID=feeRecord.getRecord().get(1);
                dbg("feeID"+feeID);
                String[] l_pkey={feeID};
                
                DBRecord feeManagementRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", l_pkey, session,dbSession);
                
                String l_dueDate=feeManagementRecord.getRecord().get(6).trim();
                String l_recStatus=feeManagementRecord.getRecord().get(11).trim();
                String l_authStatus=feeManagementRecord.getRecord().get(12).trim();
                
                dbg("l_recStatus"+l_recStatus);
                dbg("l_authStatus"+l_authStatus);
                
                if(l_recStatus.equals("O")&&l_authStatus.equals("A")){
                
                Date dueDate=formatter.parse(l_dueDate);
                dbg("dueDate"+dueDate);
                FeeDetail feeDetail=new FeeDetail();
                
                feeDetail.setDueDate(dueDate);
                feeDetail.setFeeID(feeID);
                
                feeDetailList.add(feeDetail);
                }
            }
            dbg("feeDetailList size"+feeDetailList.size());
            Collections.sort(feeDetailList);
            
            payment.payments=new Payments[feeDetailList.size()];
            int i=0;
            dbg("iteration after sorting");
            
            
            if(paidAmout!=0&&paidAmout>0){
            
            
          for(FeeDetail feeetail:feeDetailList){
                
              
                
                String feeID=feeetail.getFeeID();
                String dueDate=formatter.format(feeetail.getDueDate());
                dbg("feeID"+feeID);
                String[] l_pkey={feeID};
                DBRecord feeManagementRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", l_pkey, session,dbSession);
                String feeDescription=feeManagementRecord.getRecord().get(3).trim();
                float balaceAmount=this.getOutStanding(feeID);
                dbg("balaceAmount"+balaceAmount);
                
                if(balaceAmount!=0){
                
                payment.payments[i]=new Payments();
                payment.payments[i].setFeeID(feeID);
                payment.payments[i].setDueDate(dueDate);
                payment.payments[i].setFeeDescription(feeDescription);
                payment.payments[i].setOutStanding(Float.toString(balaceAmount));
                Float paymentForTheFee=0.0f;
                if(paidAmout!=0&&paidAmout>0){
                    dbg("inside paidAmout not equal to zero" );
                    if(balaceAmount<paidAmout){
                      dbg("inside balance amount less than paid amount");  
                        paymentForTheFee=balaceAmount;
                        
                    }else{
                        dbg("this is else");
                        paymentForTheFee=paidAmout;
                    }
                    paidAmout=paidAmout-paymentForTheFee;
                    dbg("paidAmout"+paidAmout);
                }
                dbg("paymentForTheFee"+paymentForTheFee);
                payment.payments[i].setPaymentFortheFee(Float.toString(paymentForTheFee));
                
                }
                
                i++;
            }
            }else{
                
                
                 for(FeeDetail feeetail:feeDetailList){
                
              
                
                String feeID=feeetail.getFeeID();
                String dueDate=formatter.format(feeetail.getDueDate());
                dbg("feeID"+feeID);
                String[] l_pkey={feeID};
                DBRecord feeManagementRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", l_pkey, session,dbSession);
                String feeDescription=feeManagementRecord.getRecord().get(3).trim();
                float balaceAmount=this.getOutStanding(feeID);
                dbg("balaceAmount"+balaceAmount);
                
                if(balaceAmount!=0){
                
                payment.payments[i]=new Payments();
                payment.payments[i].setFeeID(feeID);
                payment.payments[i].setDueDate(dueDate);
                payment.payments[i].setFeeDescription(feeDescription);
                 
                payment.payments[i].setOutStanding(Float.toString(balaceAmount));
                payment.payments[i].setPaymentFortheFee(Float.toString(balaceAmount));
                paidAmout=paidAmout+balaceAmount;
                i++;                

                }
                
            }
         
              payment.setPaymentpaid(Float.toString(paidAmout));
                
            }
        }
        
        if(payment.payments==null||payment.payments.length==0){
            session.getErrorhandler().log_app_error("BS_VAL_068", null);
                        throw new BSValidationException("BSValidationException");
        }
        int valueCount=0;
        for(Payments paymentObj:payment.payments){
            
            if(paymentObj!=null){
                valueCount++;
            }
            
        }
        if(valueCount==0){
            
            session.getErrorhandler().log_app_error("BS_VAL_068", null);
            throw new BSValidationException("BSValidationException");
        }
        
          dbg("end of  completed institute payment--->paymentDefault"); 
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
  
       public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
        try{
        dbg("Inside InstitutePaymentService--->create"); 
        RequestBody<InstitutePayment> reqBody = request.getReqBody();
        InstitutePayment payment =reqBody.get();
        BusinessService bs=inject.getBusinessService(session);
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
//        String currentDate=bs.getCurrentDate();
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        
        String l_instituteID=payment.getInstituteID();
        String l_studentID=payment.getStudentID();
        String l_paymentID=payment.getPaymentID();
        String l_paymentMode=payment.getPaymentMode();
        String l_paymentPaid=payment.getPaymentpaid();
        String l_paymentDate=payment.getPaymentDate();
        
        
        
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_paymentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT",355,l_instituteID,l_studentID,l_paymentID,l_paymentMode,l_paymentPaid,l_paymentDate,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
       
        
        for(int i=0;i<payment.payments.length;i++){
        
        String feeID=payment.payments[i].getFeeID();
        String paymentForTheFee=payment.payments[i].getPaymentFortheFee();
        String dueAmount=payment.payments[i].getOutStanding();
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_paymentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT",88,l_instituteID,l_studentID,l_paymentID,feeID,paymentForTheFee,l_versionNumber,dueAmount);
        
        
        
        }
//        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT",314,l_paymentID,l_paymentDate,l_authStatus);

        dbg("End of InstitutePaymentService--->create");
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
            dbg("Inside InstitutePaymentService--->update"); 
            IDBTransactionService dbts=inject.getDBTransactionService();
            BusinessService bs=inject.getBusinessService(session);
//            String currentDate=bs.getCurrentDate();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<InstitutePayment> reqBody = request.getReqBody();
            InstitutePayment payment =reqBody.get();
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
            String l_instituteID=payment.getInstituteID(); 
            String l_paymentID=payment.getPaymentID();
            String l_paymentDate=payment.getPaymentDate();
            Map<String,String>  l_column_to_update=new HashMap();
            l_column_to_update.put("8", l_checkerID);
            l_column_to_update.put("10", l_checkerDateStamp);
            l_column_to_update.put("12", l_authStatus);
            l_column_to_update.put("15", l_checkerRemarks);
            
             
             String[] l_primaryKey={l_paymentID};
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_paymentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT","INSTITUTE_PAYMENT_MASTER", l_primaryKey, l_column_to_update, session);
            
//             l_column_to_update=new HashMap();
//             l_column_to_update.put("2", l_authStatus);           
//                        
//                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT","PAYMENT_MASTER", l_primaryKey, l_column_to_update, session);
                        
             dbg("End of InstitutePaymentService--->update");          
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

     
      
      
       
    
     private float  getOutStanding(String l_feeID)throws BSProcessingException,DBValidationException,DBProcessingException{
        
        try{
        dbg("Inside getOutStanding"); 
        RequestBody<InstitutePayment> reqBody = request.getReqBody();
        InstitutePayment payment =reqBody.get();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        
        String l_instituteID=payment.getInstituteID();
        String l_studentID=payment.getStudentID();
        String l_paymentID=payment.getPaymentID();
        
        String[] l_pkey={l_feeID};
        
        DBRecord feeManagementRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", l_pkey, session,dbSession);
        
        float feeAmount=Float.parseFloat(feeManagementRecord.getRecord().get(5).trim());
        
        dbg("feeAmount"+feeAmount);
        
        Map<String,DBRecord>InstituteFeePaymentMap=null;
        
        try{
        
           InstituteFeePaymentMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"FEE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_feeID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_feeID,"FEE","INSTITITUTE_FEE_PAYMENT", session,dbSession);
        
        }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                    }else{
                        
                        throw ex;
                    }
          }
        float totalPaidAmount=0;
        if(InstituteFeePaymentMap!=null){
        
            List<DBRecord>FilteredRecords=   InstituteFeePaymentMap.values().stream().filter(rec->rec.getRecord().get(0).trim().equals(l_studentID)).collect(Collectors.toList());

            dbg("FilteredRecords size"+FilteredRecords.size());
            for(int i=0;i<FilteredRecords.size();i++){

                DBRecord feePaymentRecord=FilteredRecords.get(i);
                float paidAmount=Float.parseFloat(feePaymentRecord.getRecord().get(3).trim());
                totalPaidAmount=totalPaidAmount+paidAmount;
            }
       
        }
        dbg("totalPaidAmount");
        
        float outStanding=feeAmount-totalPaidAmount;
        
        
        dbg("End of getOutStanding--->"+outStanding);
        return outStanding;
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
       
       
      
     public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
        
       Map<String,String> l_column_to_update;
                
        try{ 
        dbg("Inside InstitutePaymentService--->fullUpdate");
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<InstitutePayment> reqBody = request.getReqBody();
        BusinessService bs=inject.getBusinessService(session);
//        String currentDate=bs.getCurrentDate();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        InstitutePayment payment =reqBody.get();
        String l_instituteID=payment.getInstituteID(); 
        String l_studentID=payment.getStudentID();
        String l_paymentID=payment.getPaymentID();
        String l_paymentMode=payment.getPaymentMode();
        String l_paymentPaid=payment.getPaymentpaid();
        String l_paymentDate=payment.getPaymentDate();
//        String l_outStanding=payment.getOutStanding();
        l_column_to_update= new HashMap();
        l_column_to_update.put("2", l_studentID);
        l_column_to_update.put("3", l_paymentID);
        l_column_to_update.put("4", l_paymentMode);
        l_column_to_update.put("5", l_paymentPaid);
        l_column_to_update.put("6", l_paymentDate);
//        l_column_to_update.put("8", l_outStanding);
        l_column_to_update.put("9", l_makerDateStamp);
        l_column_to_update.put("14", l_makerRemarks);
        
        String[] l_PKey={l_paymentID};
            
            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_paymentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT","INSTITUTE_PAYMENT_MASTER",l_PKey,l_column_to_update,session);
        
            
            for(int i=0;i<payment.payments.length;i++){
                
                String feeID=payment.payments[i].getFeeID();
                String[] detailPKey={l_paymentID,feeID};
                String paymentForTheFee=payment.payments[i].getPaymentFortheFee();
                l_column_to_update= new HashMap();
                l_column_to_update.put("5", paymentForTheFee);
                dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_paymentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT","INSTITUTE_PAYMENT",detailPKey,l_column_to_update,session);
            }
            
            
       
//         l_column_to_update=new HashMap();
//         l_column_to_update.put("1", l_paymentDate);           
//                        
//                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT","PAYMENT_MASTER", l_PKey, l_column_to_update, session);
        
                   
        dbg("end of InstitutePaymentService--->fullUpdate");                
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
        dbg("Inside InstitutePaymentService--->delete");  
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<InstitutePayment> reqBody = request.getReqBody();
        BusinessService bs=inject.getBusinessService(session);
//        String currentDate=bs.getCurrentDate();
        InstitutePayment payment =reqBody.get();
        String l_instituteID=payment.getInstituteID();   
        String l_paymentID=payment.getPaymentID();
        String l_paymentDate=payment.getPaymentDate();

        String[] l_primaryKey={l_paymentID};
                 dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_paymentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT","INSTITUTE_PAYMENT_MASTER", l_primaryKey, session);
         
//                 dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT","PAYMENT_MASTER", l_primaryKey, session);       
 
       for(int i=0;i<payment.payments.length;i++){
                
                String feeID=payment.payments[i].getFeeID();
                String[] detailPKey={l_paymentID,feeID};
                dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_paymentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT","INSTITUTE_PAYMENT",detailPKey,session);
            }







         dbg("End of InstitutePaymentService--->delete");      
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
            dbg("Inside InstitutePaymentService--->tableRead");
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<InstitutePayment> reqBody = request.getReqBody();
            BusinessService bs=inject.getBusinessService(session);
//            String currentDate=bs.getCurrentDate();
            InstitutePayment payment =reqBody.get();
            String l_instituteID=payment.getInstituteID();
            String l_paymentID=payment.getPaymentID();
            String l_paymentDate=payment.getPaymentDate();
            String[] l_pkey={l_paymentID};
            DBRecord paymentRecord=null;
            Map<String,DBRecord> paymentsMap=null;
            try{
            
            
            paymentRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_paymentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT","INSTITUTE_PAYMENT_MASTER", l_pkey, session,dbSession);
        
            paymentsMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_paymentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT","INSTITUTE_PAYMENT", session,dbSession);
            
            buildBOfromDB(paymentRecord,paymentsMap);
           
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
         
          dbg("end of  InstitutePaymentService--->tableRead");   
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
     
      private void buildBOfromDB( DBRecord p_payment,Map<String,DBRecord> paymentsMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
            dbg("inside InstitutePaymentService--->buildBOfromDB");
            
            ArrayList<String>l_paymentList=p_payment.getRecord();
            RequestBody<InstitutePayment> reqBody = request.getReqBody();
            InstitutePayment payment =reqBody.get();
            String l_instituteID=payment.getInstituteID();
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            BusinessService bs=inject.getBusinessService(session);
            String paymentID=payment.getPaymentID();
            
            if(l_paymentList!=null&&!l_paymentList.isEmpty()){
           
               payment.setInstituteName(bs.getInstituteName(l_instituteID, session, dbSession, inject));
               payment.setStudentID(l_paymentList.get(1).trim());  
               String studentID=l_paymentList.get(1).trim();
               String studentName=bs.getStudentName(studentID, l_instituteID, session, dbSession, inject);
               payment.setStudentName(studentName);
               payment.setPaymentMode(l_paymentList.get(3).trim());   
               payment.setPaymentpaid(l_paymentList.get(4).trim());
               payment.setPaymentDate(l_paymentList.get(5).trim());
               
               
               List<DBRecord>paymentsList=paymentsMap.values().stream().filter(rec->rec.getRecord().get(2).trim().equals(paymentID)).collect(Collectors.toList());
               
               payment.payments=new Payments[paymentsList.size()];
               int i=0;
               for(DBRecord paymentRec:paymentsList){
               
               payment.payments[i]=new Payments();
               payment.payments[i].setFeeID(paymentRec.getRecord().get(3).trim());
               String feeID=paymentRec.getRecord().get(3).trim();
               payment.payments[i].setPaymentFortheFee(paymentRec.getRecord().get(4).trim());
               String[] l_pkey={feeID};
               DBRecord feeRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", l_pkey, session,dbSession);
               
               payment.payments[i].setFeeDescription(feeRecord.getRecord().get(3).trim());
               payment.payments[i].setFeeAmount(feeRecord.getRecord().get(5).trim());
               payment.payments[i].setDueDate(feeRecord.getRecord().get(6).trim());
               
//                float outStanding= getOutStanding(feeID);
//               float totalOutStanding=0;
//               
//
//               
//                  totalOutStanding=outStanding;
//               
               
               
               
               payment.payments[i].setOutStanding(paymentRec.getRecord().get(6).trim());
               i++;
               }
               
               
               request.getReqAudit().setMakerID(l_paymentList.get(6).trim());
               request.getReqAudit().setCheckerID(l_paymentList.get(7).trim());
               request.getReqAudit().setMakerDateStamp(l_paymentList.get(8).trim());
               request.getReqAudit().setCheckerDateStamp(l_paymentList.get(9).trim());
               request.getReqAudit().setRecordStatus(l_paymentList.get(10).trim());
               request.getReqAudit().setAuthStatus(l_paymentList.get(11).trim());
               request.getReqAudit().setVersionNumber(l_paymentList.get(12).trim());
               request.getReqAudit().setMakerRemarks(l_paymentList.get(13).trim());
               request.getReqAudit().setCheckerRemarks(l_paymentList.get(14).trim());
               
              
            }
            
          
       
        dbg("End of InstitutePaymentService--->buildBOfromDB");
        
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
     
     
     

     
     public JsonObject buildJsonResFromBO()throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
         JsonObject body=null;
         try{
             dbg("inside buildJsonResFromBO");
            RequestBody<InstitutePayment> reqBody = request.getReqBody();
            InstitutePayment payment =reqBody.get();
            String operation=request.getReqHeader().getOperation();
            String instituteID=request.getReqHeader().getInstituteID();
            BusinessService bs=inject.getBusinessService(session);;
            if(operation.equals("Create-Default")){
                
                body=Json.createObjectBuilder().add("paymentID", payment.getPaymentID())
                                               .build();
                
                
                
            }else if(operation.equals("Payment-Default")){
                JsonArrayBuilder paymentArray=Json.createArrayBuilder();
                
                for(int i=0;i<payment.payments.length;i++){
                    
                    if(payment.payments[i]!=null){
                        String outStandingAmount=bs.formatCurrency(payment.payments[i].getOutStanding(), instituteID, session, dbSession, inject);
                        String paymentForFee=bs.formatCurrency(payment.payments[i].getPaymentFortheFee(), instituteID, session, dbSession, inject);
                        
                        
                        
                    
                    paymentArray.add(Json.createObjectBuilder().add("feeID", payment.payments[i].getFeeID())
                                                               .add("feeDescription", payment.payments[i].getFeeDescription())
                                                               .add("dueDate", payment.payments[i].getDueDate())
                                                               .add("outStanding",  outStandingAmount)
                                                               .add("paymentForFee",paymentForFee));
                    
                    }
                    
                }
                
                
                
                
                body=Json.createObjectBuilder().add("instituteID", payment.getInstituteID())
                                            .add("instituteName", payment.getInstituteName())
                                            .add("studentID", payment.getStudentID())
                                            .add("studentName", payment.getStudentName())
                                            .add("paymentID", payment.getPaymentID())
                                            .add("paymentMode", payment.getPaymentMode())
                                            .add("paymentPaid", payment.getPaymentpaid())
                                            .add("paymentDate", payment.getPaymentDate())
                                            .add("Payments",paymentArray)
                                               .build();
                
                
                
            }else{
            
             JsonArrayBuilder paymentArray=Json.createArrayBuilder();
                
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
                
                
                body=Json.createObjectBuilder().add("instituteID", payment.getInstituteID())
                                            .add("instituteName", payment.getInstituteName())
                                            .add("studentID", payment.getStudentID())
                                            .add("studentName", payment.getStudentName())
                                            .add("paymentID", payment.getPaymentID())
                                            .add("paymentMode", payment.getPaymentMode())
                                            .add("paymentPaid", payment.getPaymentpaid())
                                            .add("paymentDate", payment.getPaymentDate())
                                            .add("balanceAmount", balanceAmount)
                                            .add("Payments",paymentArray)
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
       dbg("inside InstitutePaymentService--->businessValidation");    
       String l_operation=request.getReqHeader().getOperation();
                     
       if(!masterMandatoryValidation(errhandler)){
           status=false;
       }else{
           if(!masterDataValidation(errhandler)){
              status=false;
           }
       }
       if(!(l_operation.equals("View"))&&!l_operation.equals("Create-Default")&&!l_operation.equals("Payment-Default")&&!l_operation.equals("Delete")){
           
           if(!detailMandatoryValidation(errhandler)) {
               
               status=false;
           } else{
           if(!detailDataValidation(errhandler)){
               
               status=false;
           }
           
           }
       
       }

       dbg("InstitutePaymentService--->businessValidation--->O/P--->status"+status);
       dbg("End of InstitutePaymentService--->businessValidation");
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
        dbg("inside InstitutePaymentService--->masterMandatoryValidation");
        RequestBody<InstitutePayment> reqBody = request.getReqBody();
        InstitutePayment payment =reqBody.get();
        String operation=request.getReqHeader().getOperation();
        
        if(operation.equals("Modify")&&exAudit.getAuthStatus().equals("A")){
            
            status=false;
            errhandler.log_app_error("BS_VAL_072",null);
            throw new BSValidationException("BS_VAL_072");
        }
         
         if(payment.getInstituteID()==null||payment.getInstituteID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","instituteID");
         }
         if(payment.getPaymentID()==null||payment.getPaymentID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","paymentID");
         }
         if(payment.getPaymentDate()==null||payment.getPaymentDate().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","paymentDate");  
            }
         
         if(!operation.equals("View")){
         
           if(payment.getStudentID()==null||payment.getStudentID().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","studentID");  
            }
         
            if(payment.getPaymentMode()==null||payment.getPaymentMode().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","paymentMode");  
            }
         }
//            if(payment.getPaymentpaid()==null||payment.getPaymentpaid().isEmpty()){
//               status=false;  
//               errhandler.log_app_error("BS_VAL_002","paymentPaid");  
//            }
         dbg("InstitutePaymentService--->masterMandatoryValidation--->O/P--->status"+status);
         dbg("End of InstitutePaymentService--->masterMandatoryValidation");
          } catch (BSValidationException ex) {
            throw ex; 
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
        BusinessService bs=inject.getBusinessService(session);
        String l_operation=request.getReqHeader().getOperation();
        RequestBody<InstitutePayment> reqBody = request.getReqBody();
        InstitutePayment payment =reqBody.get();
        String l_instituteID= payment.getInstituteID();
        ErrorHandler errHandler=session.getErrorhandler();
        String l_paymentDate=payment.getPaymentDate();
//        String l_studentID=payment.getStudentID();
        String currentDate=bs.getCurrentDate();
        String l_paymentMode=payment.getPaymentMode();
        String l_paymentPaid=payment.getPaymentpaid();
        String l_studentID=payment.getStudentID();
        String l_paymentID=payment.getPaymentID();
             
        if(!bsv.instituteIDValidation( l_instituteID,errHandler,inject, session, dbSession)){
            status=false;
            errhandler.log_app_error("BS_VAL_003","instituteID");
        }
        
        if(!bsv.dateFormatValidation(l_paymentDate, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","paymentDate");
             }
         if(l_operation.equals("Create")){
             
                 if(!l_paymentDate.equals(currentDate)){

                     status=false;
                     errhandler.log_app_error("BS_VAL_003","paymentDate");
                 }
             }
         
         if(!l_operation.equals("View")){
          if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","studentID");
             }

             if(!bsv.paymentModeValidation(l_paymentMode, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","paymentMode");
             }
             
             
             if(l_paymentPaid!=null&&!l_paymentPaid.isEmpty()){
             
             if(!bsv.amountValidation(l_paymentPaid, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","paymentPaid");
             }
             }
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
            dbg("inside InstitutePaymentService--->detailMandatoryValidation");
            RequestBody<InstitutePayment> reqBody = request.getReqBody();
            InstitutePayment payment =reqBody.get();
           
            if(payment.payments==null||payment.payments.length==0){
               status=false;  
               errhandler.log_app_error("BS_VAL_071",null);
            }
          
            
            
            for(int i=0;i<payment.payments.length;i++){
                
                String feeID=payment.payments[i].getFeeID();
                
                if(feeID==null||feeID.isEmpty()){
                   status=false;  
                   errhandler.log_app_error("BS_VAL_002","Fee ID");  
                }
                String paymentForFee=payment.payments[i].getPaymentFortheFee();
                if(paymentForFee==null||paymentForFee.isEmpty()){
                   status=false;  
                   errhandler.log_app_error("BS_VAL_002","Payment for the Fee "+paymentForFee);  
                }
                
            }
            
            
        
        dbg("InstitutePaymentService--->detailMandatoryValidation--->O/P--->status"+status);
        dbg("End of InstitutePaymentService--->detailMandatoryValidation");
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
  
   private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
     try{
        dbg("inside InstitutePaymentService--->detailDataValidation");   
        BSValidation bsv=inject.getBsv(session);
        RequestBody<InstitutePayment> reqBody = request.getReqBody();
        String l_operation=request.getReqHeader().getOperation();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        BusinessService bs=inject.getBusinessService(session);
//        String currentDate=bs.getCurrentDate();
        InstitutePayment payment =reqBody.get();
        String l_instituteID=payment.getInstituteID();
        String l_paymentPaid=payment.getPaymentpaid();
        
        
            
             float paymentsForTheFee=0.0f;
             for(int i=0;i<payment.payments.length;i++){
                 
                 String feeID=payment.payments[i].getFeeID();
                 String feeDescription=payment.payments[i].getFeeDescription();
                 String feeIDPayment=payment.payments[i].getPaymentFortheFee();
                 String feeDueAmount=payment.payments[i].getOutStanding();
                 
                 if(!bsv.feeIDValidation(feeID, l_instituteID, session, dbSession, inject)){
                     status=false;
                      errhandler.log_app_error("BS_VAL_003","Fee ID");
                 }
                 
                 if(!bsv.amountValidation(feeIDPayment, session, dbSession, inject)){
                     status=false;
                      errhandler.log_app_error("BS_VAL_003","Payment for the fee "+feeDescription);
                     
                 }
                         
                 
                 if(Float.parseFloat(feeDueAmount)<Float.parseFloat(feeIDPayment)){
                     
                     status=false;
                      errhandler.log_app_error("BS_VAL_070",feeDescription);
                      throw new BSValidationException("BSValidationException");
                 }
                 
                 if(feeIDPayment!=null&&!feeIDPayment.isEmpty()){
                 
                 paymentsForTheFee=paymentsForTheFee+Float.parseFloat(payment.payments[i].getPaymentFortheFee());
                 
                 
                 }
             }
             
             if(l_paymentPaid!=null&&!l_paymentPaid.isEmpty()){
             
             
            if(paymentsForTheFee>Float.parseFloat(l_paymentPaid)){
                
                status=false;
                errhandler.log_app_error("BS_VAL_069",null);
            }
             
            
             }else{
                 
                 payment.setPaymentpaid(Float.toString(paymentsForTheFee));
             }
             
             

             

        dbg("InstitutePaymentService--->detailDataValidation--->O/P--->status"+status);
        dbg("End of InstitutePaymentService--->detailDataValidation");  
       }catch(DBValidationException ex){
            throw ex;
        }catch(BSValidationException ex){
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
        RequestBody<InstitutePayment> paymentBody = request.getReqBody();
        InstitutePayment payment =paymentBody.get();
        String l_instituteID=payment.getInstituteID();
        String l_versioNumber=request.getReqAudit().getVersionNumber();
        BusinessService bs=inject.getBusinessService(session);
        String l_paymentDate=payment.getPaymentDate();
//        String currentDate=bs.getCurrentDate();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
         if(!(l_operation.equals("Create")||l_operation.equals("Create-Default")||l_operation.equals("Payment-Default")||l_operation.equals("View"))){
             
            if(l_operation.equals("AutoAuth")&&l_versioNumber.equals("1")){
                
                return null;
            }else{  
                
               dbg("inside business Service--->getExistingAudit--->Service--->InstitutePayment");  
               
               String l_paymentID=payment.getPaymentID();
               String[] l_pkey={l_paymentID};
            
               DBRecord instituteRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_paymentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT","INSTITUTE_PAYMENT_MASTER", l_pkey, session,dbSession);

               exAudit.setAuthStatus(instituteRecord.getRecord().get(11).trim());
               exAudit.setMakerID(instituteRecord.getRecord().get(6).trim());
               exAudit.setRecordStatus(instituteRecord.getRecord().get(10).trim());
               exAudit.setVersionNumber(Integer.parseInt(instituteRecord.getRecord().get(12).trim()));
            

 
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
         
      try{
             dbg("inside relationshipProcessing");
             updateInstituteFeePayment();
             updateStudentFeePayment();
             
             dbg("end of relationshipProcessing");
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
  
      private void updateInstituteFeePayment()throws BSProcessingException,DBValidationException,DBProcessingException{
        
        try{
        dbg("Inside updateInstituteFeePayment"); 
        RequestBody<InstitutePayment> reqBody = request.getReqBody();
        InstitutePayment payment =reqBody.get();
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String recordStatus=request.getReqAudit().getRecordStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        
        String l_instituteID=payment.getInstituteID();
        String l_studentID=payment.getStudentID();
        String l_paymentID=payment.getPaymentID();
        
        String l_paymentDate=payment.getPaymentDate();
        
        String[] l_pkey={l_paymentID};
        
        for(Payments paymentObj:payment.payments){
        String l_feeID=paymentObj.getFeeID();
        String l_paymentPaid=paymentObj.getPaymentFortheFee();
        if(l_versionNumber.equals("1")){
            
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"FEE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_feeID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_feeID,"FEE",310,l_studentID,l_paymentID,l_paymentDate,l_paymentPaid);
            
            
            
        }else if(!l_versionNumber.equals("1")&&!recordStatus.equals("D")){
            
             Map<String,String>column_to_update=new HashMap();
             column_to_update.put("1", l_studentID);
             column_to_update.put("3", l_paymentDate);
             column_to_update.put("4", l_paymentPaid);

             dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"FEE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_feeID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_feeID,"FEE", "INSTITITUTE_FEE_PAYMENT", l_pkey, column_to_update, session);
            
        }else{
            
            
             dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"FEE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_feeID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_feeID,"FEE", "INSTITITUTE_FEE_PAYMENT", l_pkey, session);
        }
        
        
        }
       

        dbg("End of updateInstituteFeePayment");
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
      
       private void updateStudentFeePayment()throws BSProcessingException,DBValidationException,DBProcessingException{
        
        try{
        dbg("Inside updateStudentFeePayment"); 
        RequestBody<InstitutePayment> reqBody = request.getReqBody();
        InstitutePayment payment =reqBody.get();
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String recordStatus=request.getReqAudit().getRecordStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        
        String l_instituteID=payment.getInstituteID();
        String l_studentID=payment.getStudentID();
        String l_paymentID=payment.getPaymentID();
        String l_paymentDate=payment.getPaymentDate();
        
        
        for(Payments paymentObj:payment.payments){
        String l_feeID=paymentObj.getFeeID();
        String[] l_pkey={l_paymentID,l_feeID};
        String l_paymentPaid=paymentObj.getPaymentFortheFee();
        if(l_versionNumber.equals("1")){
            
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT",64,l_studentID,l_paymentID,l_paymentDate,l_feeID,l_paymentPaid);
            
            
            
        }else if(!l_versionNumber.equals("1")&&!recordStatus.equals("D")){
            
            Map<String,String>column_to_update=new HashMap();
            column_to_update.put("3", l_paymentDate);
            column_to_update.put("4", l_feeID);
            column_to_update.put("5", l_paymentPaid);

            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT", "STUDENT_PAYMENT", l_pkey, column_to_update, session);
            
        }else{
            
            
             dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT", "STUDENT_PAYMENT", l_pkey, session);
        }
        
        }
        dbg("End of updateStudentFeePayment");
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

       
       private String getBalanceAmount()throws BSProcessingException{
           
           try{
               dbg("inside getBalanceAmount");
               float balanceAmount=0.0f;
               RequestBody<InstitutePayment> reqBody = request.getReqBody();
               InstitutePayment payment =reqBody.get();
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
       
       
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
}
