/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentfeemanagement;

import com.ibd.businessViews.IStudentFeeManagementSummary;
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
import java.util.ArrayList;
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
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
//@Local(IStudentFeeManagementSummary.class)
@Remote(IStudentFeeManagementSummary.class)
@Stateless
public class StudentFeeManagementSummary implements IStudentFeeManagementSummary{
     AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentFeeManagementSummary(){
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
       dbg("inside StudentFeeManagementSummary--->processing");
       dbg("StudentFeeManagementSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
 
       BusinessEJB<IStudentFeeManagementSummary>studentFeeManagementEJB=new BusinessEJB();
       studentFeeManagementEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentFeeManagementEJB);
       
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
           request=null;
            bs=inject.getBusinessService(session);
            if(l_session_created_now){
                bs.responselogging(jsonResponse, inject,session,dbSession);
                dbg("Response"+jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
                //if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                          clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes
                /*          
                BSProcessingException ex=new BSProcessingException("response contains special characters");
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
      StudentFeeManagementBO studentFeeManagement=new StudentFeeManagementBO();
      RequestBody<StudentFeeManagementBO> reqBody = new RequestBody<StudentFeeManagementBO>(); 
           
      try{
      dbg("inside student feeManagement buildBOfromReq");    
      BSValidation bsv=inject.getBsv(session);
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      studentFeeManagement.filter=new StudentFeeManagementFilter();
//      studentFeeManagement.filter.setStudentID(l_filterObject.getString("studentID"));
//      studentFeeManagement.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      studentFeeManagement.filter.setAuthStatus(l_filterObject.getString("authStat"));
//      studentFeeManagement.filter.setFeeID(l_filterObject.getString("feeID"));
//      studentFeeManagement.filter.setFeeType(l_filterObject.getString("feeType"));
//      studentFeeManagement.filter.setDueDate(l_filterObject.getString("dueDate"));
//      studentFeeManagement.filter.setPaidDate(l_filterObject.getString("paidDate"));
//      studentFeeManagement.filter.setStatus(l_filterObject.getString("status"));
//       if(l_filterObject.getString("class").equals("Select option")){
//          studentFeeManagement.filter.setStandard("");
//          studentFeeManagement.filter.setSection("");
//      }else{
//
//          String l_class=l_filterObject.getString("class");
//          bsv.classValidation(l_class,session);
//          studentFeeManagement.filter.setStandard(l_class.split("/")[0]);
//          studentFeeManagement.filter.setSection(l_class.split("/")[1]);
//      
//      }
      
      studentFeeManagement.filter.setStudentName(l_filterObject.getString("studentName"));
      studentFeeManagement.filter.setStudentID(l_filterObject.getString("studentID"));
//      studentFeeManagement.filter.setPaidDate(l_filterObject.getString("paidDate"));
      
//      if(l_filterObject.getString("authStat").equals("Select option")){
//          
//          studentFeeManagement.filter.setAuthStatus("");
//      }else{
//      
//          studentFeeManagement.filter.setAuthStatus(l_filterObject.getString("authStat"));
//      }
  
      if(l_filterObject.getString("feeType").equals("Select option")){
          
          studentFeeManagement.filter.setFeeType("");
      }else{
      
          studentFeeManagement.filter.setFeeType(l_filterObject.getString("feeType"));
      }
      
      if(l_filterObject.getString("status").equals("Select option")){
          
          studentFeeManagement.filter.setStatus("");
      }else{
      
          studentFeeManagement.filter.setStatus(l_filterObject.getString("status"));
      
      }
      
      
        reqBody.set(studentFeeManagement);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student feeManagement--->view");
//        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentFeeManagementBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_feeManagementList=new ArrayList();
        StudentFeeManagementBO studentFeeManagement=reqBody.get();
        String l_studentID=studentFeeManagement.getFilter().getStudentID();
        IMetaDataService mds=inject.getMetadataservice();
        int recStatusColId=mds.getColumnMetaData("INSTITUTE", "INSTITUTE_FEE_MANAGEMENT", "RECORD_STATUS", session).getI_ColumnID()-1;
        int authStatusColId=mds.getColumnMetaData("INSTITUTE", "INSTITUTE_FEE_MANAGEMENT", "AUTH_STATUS", session).getI_ColumnID()-1;
//        String l_standard=studentFeeManagement.getFilter().getStandard();
//        String l_section=studentFeeManagement.getFilter().getSection();
//        String studentNameFilter=studentFeeManagement.getFilter().getStudentName();

        try{
        
//            ArrayList<String>l_fileNames=bs.getStudentFileNames(l_studentID,request,session,dbSession,inject,l_standard,l_section);
//            for(int i=0;i<l_fileNames.size();i++){
//                dbg("inside file name iteration");
//                String l_fileName=l_fileNames.get(i);
//                dbg("l_fileName"+l_fileName);
//                
//                String studentName=bs.getStudentName(l_fileName, l_instituteID, session, dbSession, inject);
//                
//                if(studentName.equals(studentNameFilter)){
                
                Map<String,DBRecord>feeManagementMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"FEE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","FEE", "STUDENT_FEE_MANAGEMENT", session, dbSession);
                 Map<String,DBRecord>instituteFeeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", session, dbSession);
                
                Map<String,List<DBRecord>>aauthorizedRecords=instituteFeeMap.values().stream().filter(rec->rec.getRecord().get(recStatusColId).trim().equals("O")&&rec.getRecord().get(authStatusColId).trim().equals("A")).collect(Collectors.groupingBy(rec->rec.getRecord().get(2).trim()));
                
                Iterator<DBRecord>valueIterator=feeManagementMap.values().iterator();

                while(valueIterator.hasNext()){
                   DBRecord leaveRec=valueIterator.next();
                   String feeID=leaveRec.getRecord().get(1).trim();
                 if(aauthorizedRecords.containsKey(feeID))  {
                   
                   l_feeManagementList.add(leaveRec);
                   
                 }
                }
//                Iterator<DBRecord>valueIterator=feeManagementMap.values().iterator();
//
//                while(valueIterator.hasNext()){
//                   DBRecord feeManagementRec=valueIterator.next();
//                   l_feeManagementList.add(feeManagementRec);
//
//                }
//                }
//                dbg("file name itertion completed for "+l_fileName);
//            }
        }catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_000")||ex.toString().contains("DB_VAL_011")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().log_app_error("BS_VAL_013", null);
                throw new BSValidationException("BSValidationException");
                
            }else{
                throw ex;
            }
            
        }

        buildBOfromDB(l_feeManagementList);     
        
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
    

    
    private void buildBOfromDB(ArrayList<DBRecord>l_feeManagementList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<StudentFeeManagementBO> reqBody = request.getReqBody();
           StudentFeeManagementBO studentFeeManagement =reqBody.get();
//           String l_authStatus=studentFeeManagement.getFilter().getAuthStatus();
           String l_studentID=studentFeeManagement.getFilter().getStudentID();
           String l_feeTypeFilter=studentFeeManagement.getFilter().getFeeType();
////           String l_paidDate=studentFeeManagement.getFilter().getPaidDate();
           String l_statusFilter=studentFeeManagement.getFilter().getStatus();
           
           dbg("l_studentID"+l_studentID);
           dbg("l_feeTypeFilter"+l_feeTypeFilter);
           dbg("l_statusFilter"+l_statusFilter);
           
           ArrayList<FeeDetails>feeDetailList=getFeeDetails(l_feeManagementList);

           if(!l_feeTypeFilter.isEmpty()){
               
            
             List<FeeDetails>  l_studentList=  feeDetailList.stream().filter(rec->rec.getFeeType().equals(l_feeTypeFilter)).collect(Collectors.toList());
             feeDetailList = new ArrayList<FeeDetails>(l_studentList);
             dbg("fee type filter feeDetailList size"+feeDetailList.size());
               
           }
           
            if(!l_statusFilter.isEmpty()){
               
            
             List<FeeDetails>  l_studentList=  feeDetailList.stream().filter(rec->rec.getStatus().equals(l_statusFilter)).collect(Collectors.toList());
             feeDetailList = new ArrayList<FeeDetails>(l_studentList);
             dbg("status filter feeDetailList size"+feeDetailList.size());
               
           }

           
           
           studentFeeManagement.result=new StudentFeeManagementResult[feeDetailList.size()];
           for(int i=0;i<feeDetailList.size();i++){
               
               FeeDetails feeDetails=feeDetailList.get(i);
               studentFeeManagement.result[i]=new StudentFeeManagementResult();
               studentFeeManagement.result[i].setStudentID(l_studentID);
               studentFeeManagement.result[i].setFeeID(feeDetails.getFeeID());
               studentFeeManagement.result[i].setFeeType(feeDetails.getFeeType());
               studentFeeManagement.result[i].setAmount(feeDetails.getFeeAmount());
               studentFeeManagement.result[i].setDueDate(feeDetails.getDueDate());
               studentFeeManagement.result[i].setStatus(feeDetails.getStatus());
               studentFeeManagement.result[i].setFeePaid(feeDetails.getPaidAmount());
            }
           
           if(studentFeeManagement.result==null||studentFeeManagement.result.length==0){
               session.getErrorhandler().log_app_error("BS_VAL_013", null);
               throw new BSValidationException("BSValidationException");
           }
           
           
           
          dbg("end of  buildBOfromDB"); 
        }catch(BSValidationException ex){
          throw ex;   
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
 }
    
    
  
    private ArrayList<FeeDetails>getFeeDetails(ArrayList<DBRecord>l_feeManagementList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
        
        
        try{
            dbg("inside get fee details");
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            BSValidation bsv=inject.getBsv(session);
            RequestBody<StudentFeeManagementBO> reqBody = request.getReqBody();
            StudentFeeManagementBO studentFeeManagement =reqBody.get();
            String l_instituteID=request.getReqHeader().getInstituteID();
            String l_studentID=studentFeeManagement.getFilter().getStudentID();
            dbg("l_studentID"+l_studentID);
            Map<String,DBRecord>paymentMap=null;
            ArrayList<FeeDetails>feeDetailList=new ArrayList();
           
           
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
            
             for(int i=0;i<l_feeManagementList.size();i++){
               
               String feeID=l_feeManagementList.get(i).getRecord().get(1).trim();
               dbg("feeID"+feeID);
               String[] pkey={feeID}; 
               DBRecord instituteFeeRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", pkey, session,dbSession);
               String feeType=instituteFeeRecord.getRecord().get(4).trim();
               String dueDate=instituteFeeRecord.getRecord().get(6).trim();
               dbg("feeType"+feeType);
               dbg("dueDate"+dueDate);
               float totalPaymentAmount=0;     
               float feeAmount=Float.parseFloat(instituteFeeRecord.getRecord().get(5).trim());
               dbg("feeAmount"+dueDate);
               String status="P";
               
               if(paymentMap!=null){
               
                   List<DBRecord>filteredRecords=paymentMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals(feeID)).collect(Collectors.toList());

                   for(int j=0;j<filteredRecords.size();j++){

                       float paidAmount=Float.parseFloat(filteredRecords.get(j).getRecord().get(4).trim());
                       totalPaymentAmount=totalPaymentAmount+paidAmount;

                    }
               
               }
                float balanceAmount=feeAmount-totalPaymentAmount;

                    if(balanceAmount==0){
                        
                         status="C";
                         
                     }else{
                        
                        if(!bsv.pastDateValidation(dueDate, session, dbSession, inject)){
                            status="O";
                        }
                   }

                 dbg("status"+status);   
                 dbg("totalPaymentAmount"+totalPaymentAmount); 
                 FeeDetails feeDetail=new FeeDetails();   
                 feeDetail.setStudentID(l_studentID);
                 feeDetail.setFeeID(feeID);
                 feeDetail.setFeeType(feeType);
                 feeDetail.setFeeAmount(Float.toString(feeAmount));
                 feeDetail.setStatus(status);
                 feeDetail.setDueDate(dueDate);
                 feeDetail.setPaidAmount(Float.toString(totalPaymentAmount));
                 feeDetailList.add(feeDetail);
           }
            
            dbg("end of get Fee vdetails--->feeDetailList"+feeDetailList.size());
            return feeDetailList;
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
        JsonObject filter;
        try{
        dbg("inside student feeManagement buildJsonResFromBO");    
        RequestBody<StudentFeeManagementBO> reqBody = request.getReqBody();
        StudentFeeManagementBO studentFeeManagement =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<studentFeeManagement.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("studentID", studentFeeManagement.result[i].getStudentID())
                                                      .add("feeID", studentFeeManagement.result[i].getFeeID())
                                                      .add("feeType",studentFeeManagement.result[i].getFeeType())
                                                      .add("feeAmount", studentFeeManagement.result[i].getAmount())
                                                      .add("dueDate", studentFeeManagement.result[i].getDueDate())
                                                      .add("status", studentFeeManagement.result[i].getStatus())
                                                      .add("paidAmount",studentFeeManagement.result[i].getFeePaid()));
        }

           filter=Json.createObjectBuilder()  .add("studentID",studentFeeManagement.filter.getStudentID())
                                              .add("studentName",studentFeeManagement.filter.getStudentName())
                                              .add("feeType",  studentFeeManagement.filter.getFeeType())
                                              .add("status", studentFeeManagement.filter.getStatus())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
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
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of student feeManagement--->businessValidation"); 
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
        dbg("inside student feeManagement master mandatory validation");
        RequestBody<StudentFeeManagementBO> reqBody = request.getReqBody();
        StudentFeeManagementBO studentFeeManagement =reqBody.get();
        int nullCount=0;
        
        BusinessService bs=inject.getBusinessService(session);
        String studentID=studentFeeManagement.getFilter().getStudentID();
        String studentName=studentFeeManagement.getFilter().getStudentName();
        String instituteID=request.getReqHeader().getInstituteID();
        studentID=bs.studentValidation(studentID, studentName, instituteID, session, dbSession, inject);

        studentFeeManagement.getFilter().setStudentID(studentID);
        
        
        
        
        
        
         if(studentFeeManagement.getFilter().getStudentID()==null||studentFeeManagement.getFilter().getStudentID().isEmpty()){
             status=false;
             errhandler.log_app_error("BS_VAL_002","Student ID or Name");
         }
          
          
//          if(nullCount==8){
//              status=false;
//              errhandler.log_app_error("BS_VAL_002","One Filter value");
//          }
          
        dbg("end of student feeManagement master mandatory validation");
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
             dbg("inside student feeManagement detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<StudentFeeManagementBO> reqBody = request.getReqBody();
             StudentFeeManagementBO studentFeeManagement =reqBody.get();
             String l_studentID=studentFeeManagement.getFilter().getStudentID();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_feeType=studentFeeManagement.getFilter().getFeeType();
             String l_status=studentFeeManagement.getFilter().getStatus();
             
             if(l_studentID!=null&&!l_studentID.isEmpty()){
                 
                if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","studentID");
                }
                 
             }
             
//             if(l_authStatus!=null&&!l_authStatus.isEmpty()){
//                 
//                if(!bsv.authStatusValidation(l_authStatus, session, dbSession, inject)){
//                    status=false;
//                    errhandler.log_app_error("BS_VAL_003","authStatus");
//                }
//                 
//             }
             
             if(l_feeType!=null&&!l_feeType.isEmpty()){
             
                 if(!bsv.feeTypeValidation(l_feeType, l_instituteID, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","feeType");
                 }
             }
             
//             if(l_paidDate!=null&&!l_paidDate.isEmpty()){
//             
//                 if(!bsv.dateFormatValidation(l_paidDate, session, dbSession, inject)){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_003","paid Date");
//                 }
//             
//             }
             if(l_status!=null&&!l_status.isEmpty()){
             
                 if(!bsv.feeStatusValidation(l_status, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","status");
                 }
             }
             
            dbg("end of student feeManagement detailDataValidation");
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
