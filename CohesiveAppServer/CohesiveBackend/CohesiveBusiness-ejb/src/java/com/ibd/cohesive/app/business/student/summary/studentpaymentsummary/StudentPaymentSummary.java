/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentpaymentsummary;

import com.ibd.businessViews.IStudentPaymentSummary;
import com.ibd.businessViews.businessUtils.ExistingAudit;
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
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
//@Local(IStudentPaymentSummary.class)
@Remote(IStudentPaymentSummary.class)
@Stateless
public class StudentPaymentSummary implements IStudentPaymentSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentPaymentSummary(){
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
       dbg("inside StudentPaymentSummary--->processing");
       dbg("StudentPaymentSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
 
       BusinessEJB<IStudentPaymentSummary>studentPaymentEJB=new BusinessEJB();
       studentPaymentEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentPaymentEJB);
       
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
      StudentPaymentBO studentPayment=new StudentPaymentBO();
      RequestBody<StudentPaymentBO> reqBody = new RequestBody<StudentPaymentBO>(); 
           
      try{
      dbg("inside student payment buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      studentPayment.filter=new StudentPaymentFilter();
      BSValidation bsv=inject.getBsv(session);
//      studentPayment.filter.setStudentID(l_filterObject.getString("studentID"));
//      studentPayment.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      studentPayment.filter.setAuthStatus(l_filterObject.getString("authStat"));
//      studentPayment.filter.setFeeID(l_filterObject.getString("feeID"));
//      studentPayment.filter.setFeeType(l_filterObject.getString("feeType"));
//      studentPayment.filter.setAmount(l_filterObject.getString("amount"));
//      studentPayment.filter.setPaymentID(l_filterObject.getString("paymentID"));
//      studentPayment.filter.setPaymentDate(l_filterObject.getString("paymentDate"));
//      studentPayment.filter.setPaymentMode(l_filterObject.getString("paymentMode"));
//      studentPayment.filter.setPaymentPaid(l_filterObject.getString("paymentPaid"));


      
      studentPayment.filter.setStudentID(l_filterObject.getString("studentID"));
      studentPayment.filter.setStudentName(l_filterObject.getString("studentName"));
      studentPayment.filter.setFromDate(l_filterObject.getString("fromDate"));
      studentPayment.filter.setToDate(l_filterObject.getString("toDate"));
      
     
      
      
        reqBody.set(studentPayment);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

     public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student payment--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentPaymentBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_paymentList=new ArrayList();
        StudentPaymentBO studentPayment=reqBody.get();
        String l_studentID=studentPayment.getFilter().getStudentID();
        String l_fromDate=studentPayment.getFilter().getFromDate();
        String l_toDate=studentPayment.getFilter().getToDate();
        String dateFormat = i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                ArrayList<Date>dateList= bs.getLeaveDates(l_fromDate, l_toDate, session, dbSession, inject);
        
                
                 for(int i=0;i<dateList.size();i++){
                
        try{
        
            

            
                
               
                    
                    String paymentDate= formatter.format(dateList.get(i));;
                    
                    
                    
                    
                     Map<String,DBRecord>institutePaymentMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+paymentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT","INSTITUTE_PAYMENT_MASTER", session, dbSession);

                
                
                        Iterator<DBRecord>valueIterator=institutePaymentMap.values().iterator();

                        while(valueIterator.hasNext()){
                           DBRecord paymentRec=valueIterator.next();
                           l_paymentList.add(paymentRec);

                        }
                
                
                
                
                               
//            
//            
//                
//                Map<String,DBRecord>paymentMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT", "STUDENT_PAYMENT", session, dbSession);
//                dbg("paymentMap size"+paymentMap.size());
                

        
        }catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_000")||ex.toString().contains("DB_VAL_011")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
//                session.getErrorhandler().log_app_error("BS_VAL_013", null);
//                throw new BSValidationException("BSValidationException");
                
            }else{
                throw ex;
            }
            
        }
                 }

            if(l_paymentList.isEmpty()){
                session.getErrorhandler().log_app_error("BS_VAL_013", null);
                throw new BSValidationException("BSValidationException");
            }     
                 
                 
        buildBOfromDB(l_paymentList);     
        
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
    

    
    private void buildBOfromDB(ArrayList<DBRecord>l_paymentList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           IBDProperties i_db_properties=session.getCohesiveproperties();
           String l_instituteID=request.getReqHeader().getInstituteID();
           BusinessService bs=inject.getBusinessService(session);
           RequestBody<StudentPaymentBO> reqBody = request.getReqBody();
           StudentPaymentBO studentPayment =reqBody.get();
           String l_studentID=studentPayment.getFilter().getStudentID();
           String l_fromDate=studentPayment.getFilter().getFromDate();
           String l_toDate=studentPayment.getFilter().getToDate();
           String dateFormat=i_db_properties.getProperty("DATE_FORMAT");
           SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//           ArrayList<DBRecord>filteredList=new ArrayList();
           dbg("l_studentID"+l_studentID);
           
           if(l_studentID!=null&&!l_studentID.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_paymentList.stream().filter(rec->rec.getRecord().get(1).trim().equals(l_studentID)).collect(Collectors.toList());
             l_paymentList = new ArrayList<DBRecord>(l_studentList);
             dbg("paymentAmount filter l_institutePaymentList size"+l_paymentList.size());
           }
            
            
            
           
          
           
           studentPayment.result=new StudentPaymentResult[l_paymentList.size()];
           for(int i=0;i<l_paymentList.size();i++){
               
               ArrayList<String> l_studentPaymentList=l_paymentList.get(i).getRecord();
               studentPayment.result[i]=new StudentPaymentResult();
               studentPayment.result[i].setStudentID(l_studentPaymentList.get(1).trim());
               
               studentPayment.result[i].setPaymentID(l_studentPaymentList.get(2).trim());
               studentPayment.result[i].setPaymentPaid(l_studentPaymentList.get(4).trim());
               studentPayment.result[i].setPaymentDate(l_studentPaymentList.get(5).trim());
               
          

               
          }
           
           if(studentPayment.result==null||studentPayment.result.length==0){
               session.getErrorhandler().log_app_error("BS_VAL_013", null);
               throw new BSValidationException("BSValidationException");
           }
           
           
           
          dbg("end of  buildBOfromDB"); 
       }catch(BSValidationException ex){
          throw ex;  
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
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        JsonObject filter;
        try{
        dbg("inside student payment buildJsonResFromBO");    
        RequestBody<StudentPaymentBO> reqBody = request.getReqBody();
        StudentPaymentBO studentPayment =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<studentPayment.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("studentID", studentPayment.result[i].getStudentID())
                                                      .add("paymentID", studentPayment.result[i].getPaymentID())
                                                      .add("paymentPaid", studentPayment.result[i].getPaymentPaid())
                                                      .add("paymentDate", studentPayment.result[i].getPaymentDate()));
        }

           filter=Json.createObjectBuilder()  .add("studentID",studentPayment.filter.getStudentID())
                                              .add("studentName",studentPayment.filter.getStudentName())
                                              .add("fromDate",studentPayment.filter.getFromDate())
                                              .add("toDate", studentPayment.filter.getToDate())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of student payment buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student payment--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of student payment--->businessValidation"); 
       }catch(BSProcessingException ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
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
    private boolean filterMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
      boolean status=true;
        try{
        dbg("inside student payment master mandatory validation");
        RequestBody<StudentPaymentBO> reqBody = request.getReqBody();
        StudentPaymentBO studentPayment =reqBody.get();
        boolean fromDateEmpty=false;
        boolean toDateEmpty=false;
        BusinessService bs=inject.getBusinessService(session);
        String studentID=studentPayment.getFilter().getStudentID();
        String studentName=studentPayment.getFilter().getStudentName();
        String instituteID=request.getReqHeader().getInstituteID();
        studentID=bs.studentValidation(studentID, studentName, instituteID, session, dbSession, inject);

        studentPayment.getFilter().setStudentID(studentID);
        
        
         if(studentPayment.getFilter().getStudentID()==null||studentPayment.getFilter().getStudentID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","studentID");
         }
          
           if(studentPayment.getFilter().getFromDate()==null||studentPayment.getFilter().getFromDate().isEmpty()){
              fromDateEmpty=true;
         }
          
          if(studentPayment.getFilter().getToDate()==null||studentPayment.getFilter().getToDate().isEmpty()){
             toDateEmpty=true;
         }
          
          if(fromDateEmpty==true){
              
             status=false;  
             errhandler.log_app_error("BS_VAL_002","fromDate");
          }
          
          if(toDateEmpty==true){
              
             status=false;  
             errhandler.log_app_error("BS_VAL_002","toDate");
          }
          
        dbg("end of student payment master mandatory validation");
         }catch(BSValidationException ex){
           throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student payment detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             BusinessService bs=inject.getBusinessService(session);
             RequestBody<StudentPaymentBO> reqBody = request.getReqBody();
             StudentPaymentBO studentPayment =reqBody.get();
             String l_studentID=studentPayment.getFilter().getStudentID();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_fromDate=studentPayment.getFilter().getFromDate();
             String l_toDate=studentPayment.getFilter().getToDate();
             
             
             if(l_studentID!=null&&!l_studentID.isEmpty()){
                 
                if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","studentID");
                }
                 
             }
     
             
             if(l_fromDate!=null&&!l_fromDate.isEmpty()&&l_toDate!=null&&!l_toDate.isEmpty()){
             
             
                 if(!bsv.dateFormatValidation(l_fromDate, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","from Date");
                     throw new BSValidationException("BSValidationException");
                 }
                 
                 if(!bsv.dateFormatValidation(l_toDate, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","to Date");
                     throw new BSValidationException("BSValidationException");
                 }
                 
                 if(!bsv.futureDateValidation(l_fromDate, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_048",null);
                     throw new BSValidationException("BSValidationException");
                 }
                 
                 if(!bsv.futureDateValidation(l_toDate, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_048",null);
                     throw new BSValidationException("BSValidationException");
                 }
                 
                 ConvertedDate fromDate=bs.getYearMonthandDay(l_fromDate);
                 Calendar start = Calendar.getInstance();

                 start.set(Calendar.YEAR, Integer.parseInt(fromDate.getYear()));
                 start.set(Calendar.MONTH,  Integer.parseInt(fromDate.getMonth()));
                 start.set(Calendar.DAY_OF_MONTH,  Integer.parseInt(fromDate.getDay()));


                 ConvertedDate toDate=bs.getYearMonthandDay(l_toDate);
                 Calendar end = Calendar.getInstance();

                 end.set(Calendar.YEAR, Integer.parseInt(toDate.getYear()));
                 end.set(Calendar.MONTH,  Integer.parseInt(toDate.getMonth()));
                 end.set(Calendar.DAY_OF_MONTH,  Integer.parseInt(toDate.getDay()));

                    if(start.after(end)){

                        status=false;
                        errhandler.log_app_error("BS_VAL_003","Date range");
                        throw new BSValidationException("BSValidationException");
                    }


                   int dateSize= bs.getLeaveDates(l_fromDate, l_toDate, session, dbSession, inject).size();


                   if(dateSize>7){

                        status=false;
                        errhandler.log_app_error("BS_VAL_049","Date range");
                        throw new BSValidationException("BSValidationException");
                    }

             }

            dbg("end of student payment detailDataValidation");
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
