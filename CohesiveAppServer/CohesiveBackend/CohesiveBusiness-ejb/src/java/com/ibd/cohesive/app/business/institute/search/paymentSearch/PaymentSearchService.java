/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.paymentSearch;

import com.ibd.businessViews.IPaymentSearchService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.exception.ExceptionHandler;
import com.ibd.cohesive.app.business.util.message.request.Parsing;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
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
@Remote(IPaymentSearchService.class)
@Stateless
public class PaymentSearchService implements  IPaymentSearchService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public PaymentSearchService(){
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
       try{
       session.createSessionObject();
       dbSession.createDBsession(session);
       l_session_created_now=session.isI_session_created_now();
       ErrorHandler errhandler = session.getErrorhandler();
       BSValidation bsv=inject.getBsv(session);
       bs=inject.getBusinessService(session);
       ITransactionControlService tc=inject.getTransactionControlService();
       dbg("inside PaymentSearchService--->processing");
       dbg("PaymentSearchService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IPaymentSearchService>paymentSearchEJB=new BusinessEJB();
       paymentSearchEJB.set(this);
      
       exAudit=bs.getExistingAudit(paymentSearchEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,paymentSearchEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,paymentSearchEJB);
              tc.commit(session,dbSession);
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

           request=null;
            bs=inject.getBusinessService(session);
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
      PaymentSearch paymentSearch=new PaymentSearch();
      RequestBody<PaymentSearch> reqBody = new RequestBody<PaymentSearch>(); 
           
      try{
      dbg("inside payment paymentSearch buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      paymentSearch.setSearchFilter(l_body.getString("searchFilter"));
      BSValidation bsv=inject.getBsv(session);
      String instituteID=request.getReqHeader().getInstituteID();

      
       String searchFilter=paymentSearch.getSearchFilter();
       String date=null;
       String amount=null;
       String studentID=null;
       
       
       String[] searchArr=searchFilter.split(" ");
      
       if(searchArr.length>0){
       
          date=searchArr[0];
       
       }
       
       if(searchArr.length>1){
           
           studentID= searchArr[1];
       }
       
       if(searchArr.length>2){
           
           amount= searchArr[2];
       }else if(searchArr.length==2){
           
           if(!bsv.studentIDValidation(searchArr[1], instituteID, session, dbSession, inject)){
               
               amount=searchArr[1];
           }
           
       }
      
       
       
       if(date==null){
           
           paymentSearch.setDate("");
       }else{
           
           paymentSearch.setDate(date);
       }
       
       if(amount==null){
           
           paymentSearch.setAmount("");
       }else{
           
           paymentSearch.setAmount(amount);
       }
       
       
       if(studentID==null){
           
           paymentSearch.setStudentID("");
       }else{
           
           paymentSearch.setStudentID(studentID);
       }
      
        reqBody.set(paymentSearch);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside payment paymentSearch--->view");
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IPDataService pds=inject.getPdataservice();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        Map<String,DBRecord>paymentSearchMap=null;
        RequestBody<PaymentSearch> reqBody = request.getReqBody();
        ErrorHandler errorHandler=session.getErrorhandler();
        PaymentSearch paymentSearch =reqBody.get();
        String searchFilter=paymentSearch.getSearchFilter();
        String paymentDate=paymentSearch.getDate();
        
        try{

            paymentSearchMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+paymentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT","INSTITUTE_PAYMENT_MASTER", session, dbSession);
            
        
        }catch(DBValidationException  ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                errorHandler.removeSessionErrCode("DB_VAL_011");
                errorHandler.removeSessionErrCode("DB_VAL_000");
                errorHandler.log_app_error("BS_VAL_020", searchFilter);
                throw new BSValidationException("BSValidationException");
                
            }else{
                throw ex;
            }
            
        }  
        
        
        
        dbg("paymentSearchMap size"+paymentSearchMap.size());
        buildBOfromDB(paymentSearchMap);     
        
          dbg("end of  completed payment paymentSearch--->view");                
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
        }
    }
    

    
    private void buildBOfromDB(Map<String,DBRecord>paymentSearchMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           IMetaDataService mds=inject.getMetadataservice();
           RequestBody<PaymentSearch> reqBody = request.getReqBody();
           ErrorHandler errorHandler=session.getErrorhandler();
           PaymentSearch paymentSearch =reqBody.get();
           String searchFilter=paymentSearch.getSearchFilter();
           String amount=paymentSearch.getAmount();
           String studentID=paymentSearch.getStudentID();
           dbg("searchFilter"+searchFilter);
           
//           int recStatusColId=mds.getColumnMetaData("INSTITUTE", "IVW_INSTITUTE_PAYMENT", "RECORD_STATUS", session).getI_ColumnID();
//           int authStatusColId=mds.getColumnMetaData("INSTITUTE","IVW_INSTITUTE_PAYMENT", "AUTH_STATUS", session).getI_ColumnID();
           List<DBRecord>authorizedRecords=paymentSearchMap.values().stream().collect(Collectors.toList());   
      
           if(authorizedRecords==null||authorizedRecords.isEmpty()){
               errorHandler.log_app_error("BS_VAL_020", searchFilter);
               throw new BSValidationException("BSValidationException");
           }
           
           
           if(!amount.isEmpty()){
           
              authorizedRecords=  authorizedRecords.stream().filter(rec->rec.getRecord().get(4).trim().equals(amount)).collect(Collectors.toList()); 
             
           }
           
           if(!studentID.isEmpty()){
           
              authorizedRecords=  authorizedRecords.stream().filter(rec->rec.getRecord().get(1).trim().equals(studentID)).collect(Collectors.toList()); 
             
           }
           
           
//           dbg("recStatusColId"+recStatusColId);
//           dbg("authStatusColId"+authStatusColId);
           dbg("authorizedRecords"+authorizedRecords.size());
//           if(filteredRecords!=null)
//              dbg("filteredRecords size"+filteredRecords.size());
//           else
//               dbg("filteredRecords is null");
           
        if(authorizedRecords!=null&&!authorizedRecords.isEmpty()){
               
               setRecordInPaymentSearch(authorizedRecords);
               
           }else{
               
               errorHandler.log_app_error("BS_VAL_020", searchFilter);
               throw new BSValidationException("BSValidationException");
           }
           
         
           
          dbg("end of  buildBOfromDB"); 
           
//        }catch(DBValidationException ex){
//            throw ex;
        }catch(BSValidationException ex){
            throw ex;    
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
 }
    
    private void setRecordInPaymentSearch(List<DBRecord>paymentSearchList)throws BSProcessingException{
        
        try{
           dbg("inside setRecordInPaymentSearch"); 
           RequestBody<PaymentSearch> reqBody = request.getReqBody();
           PaymentSearch paymentSearch =reqBody.get();
           int maxResultSize=Integer.parseInt(session.getCohesiveproperties().getProperty("MAX_SEARCH_RESULTS"));
            int resultSize=0;
               if(paymentSearchList.size()<=maxResultSize){
                   
                   resultSize=paymentSearchList.size();
               }else{
                   resultSize=maxResultSize;
               }
               
               paymentSearch.result=new PaymentSearchResult[resultSize];
               
               for(int i=0;i<resultSize;i++){
                   
                   String paymentID=paymentSearchList.get(i).getRecord().get(2).trim();
                   String date=paymentSearchList.get(i).getRecord().get(5).trim();
                   String paymentAmount=paymentSearchList.get(i).getRecord().get(4).trim();
                   String studentID=paymentSearchList.get(i).getRecord().get(1).trim();
                   dbg("setRecordInPaymentSearch-->paymentID"+paymentID);
                   paymentSearch.result[i]=new PaymentSearchResult();
                   paymentSearch.result[i].setPaymentID(paymentID);
                   paymentSearch.result[i].setDate(date);
                   paymentSearch.result[i].setAmount(paymentAmount);
                   paymentSearch.result[i].setStudentID(studentID);
               }
            
            
        
        dbg("end of setRecordInPaymentSearch");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
    
    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside payment paymentSearch buildJsonResFromBO");    
        RequestBody<PaymentSearch> reqBody = request.getReqBody();
        PaymentSearch paymentSearch =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<paymentSearch.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("paymentID", paymentSearch.result[i].getPaymentID())
                                                      .add("amount", paymentSearch.result[i].getAmount())
                                                      .add("studentID", paymentSearch.result[i].getStudentID())
                                                      .add("paymentDate", paymentSearch.result[i].getDate()));
        }

             body=Json.createObjectBuilder()  .add("searchFilter", paymentSearch.getSearchFilter())
                                              .add("searchResults", resultArray)
                                              .build();
               
           
                                            
              dbg(body.toString());  
           dbg("end of payment paymentSearch buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside payment paymentSearch--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }
       
       
       dbg("end of payment paymentSearch--->businessValidation"); 
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
    private boolean filterMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
      boolean status=true;
        try{
        dbg("inside payment paymentSearch master mandatory validation");
        BSValidation bsv=inject.getBsv(session);
        RequestBody<PaymentSearch> reqBody = request.getReqBody();
        PaymentSearch paymentSearch =reqBody.get();
        String paymentDate= paymentSearch.getDate();
        String amount=paymentSearch.getAmount();
        String studentID= paymentSearch.getStudentID();
          
        if(paymentDate.isEmpty()){
            
            status=false;  
            errhandler.log_app_error("BS_VAL_073","Payment date");  
        }else{
            
            if(!bsv.dateFormatValidation(paymentDate, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Payment date");
             }
        } 
        
        
          
          
        dbg("end of payment paymentSearch master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
   
 public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     return null;
 }   
 public  void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
 }
 public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
 }
 public void authUpdate()throws DBValidationException,DBProcessingException,BSProcessingException{
 }
 public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
 }
 public void delete()throws DBValidationException,DBProcessingException,BSProcessingException{
 }   
 
 public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }  
}
