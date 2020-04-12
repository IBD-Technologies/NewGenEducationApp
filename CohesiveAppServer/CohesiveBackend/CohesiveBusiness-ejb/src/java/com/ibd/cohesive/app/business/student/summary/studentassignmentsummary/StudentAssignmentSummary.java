/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentassignmentsummary;

import com.ibd.businessViews.IStudentAssignmentSummary;
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
//@Local(IStudentAssignmentSummary.class)
@Remote(IStudentAssignmentSummary.class)
@Stateless
public class StudentAssignmentSummary implements IStudentAssignmentSummary{
     AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentAssignmentSummary(){
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
       dbg("inside StudentAssignmentSummary--->processing");
       dbg("StudentAssignmentSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 

       BusinessEJB<IStudentAssignmentSummary>studentAssignmentEJB=new BusinessEJB();
       studentAssignmentEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentAssignmentEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,studentAssignmentEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentAssignmentEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student assignment");
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
                          clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes
                //if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                  /* BSProcessingException ex=new BSProcessingException("response contains special characters");
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
      StudentAssignmentBO studentAssignment=new StudentAssignmentBO();
      RequestBody<StudentAssignmentBO> reqBody = new RequestBody<StudentAssignmentBO>(); 
           
      try{
      dbg("inside student assignment buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      studentAssignment.filter=new StudentAssignmentFilter();
      BSValidation bsv=inject.getBsv(session);
//      studentAssignment.filter.setStudentID(l_filterObject.getString("studentID"));
//      studentAssignment.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      studentAssignment.filter.setAuthStatus(l_filterObject.getString("authStat"));
//      studentAssignment.filter.setAssignmentID(l_filterObject.getString("assignmentID"));
//      studentAssignment.filter.setSubjectID(l_filterObject.getString("subjectID"));
//      studentAssignment.filter.setDueDate(l_filterObject.getString("dueDate"));
//      studentAssignment.filter.setCompletedDate(l_filterObject.getString("completedDate"));
//      studentAssignment.filter.setAssignmentType(l_filterObject.getString("assignmentType"));
      
      studentAssignment.filter.setStudentID(l_filterObject.getString("studentID"));
      studentAssignment.filter.setStudentName(l_filterObject.getString("studentName"));
      studentAssignment.filter.setFromDate(l_filterObject.getString("fromDate"));
      studentAssignment.filter.setToDate(l_filterObject.getString("toDate"));
      
//      if(l_filterObject.getString("subjectID").equals("Select option")){
//          
//          studentAssignment.filter.setSubjectID("");
//      }else{
//      
//          studentAssignment.filter.setSubjectID(l_filterObject.getString("subjectID"));
//      }
//      
//      if(l_filterObject.getString("assignmentType").equals("Select option")){
//          
//          studentAssignment.filter.setAssignmentType("");
//      }else{
//      
//          studentAssignment.filter.setAssignmentType(l_filterObject.getString("assignmentType"));
//      
//      }
//      
//       if(l_filterObject.getString("authStat").equals("Select option")){
//          
//          studentAssignment.filter.setAuthStatus("");
//      }else{
//      
//          studentAssignment.filter.setAuthStatus(l_filterObject.getString("authStat"));
//      }
//      
//      if(l_filterObject.getString("status").equals("Select option")){
//          
//          studentAssignment.filter.setStatus("");
//      }else{
//      
//          studentAssignment.filter.setStatus(l_filterObject.getString("status"));
//      }
//      
        reqBody.set(studentAssignment);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    
    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student assignment--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentAssignmentBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_assignmentList=new ArrayList();
        StudentAssignmentBO studentAssignment=reqBody.get();
        String l_studentID=studentAssignment.getFilter().getStudentID();
        IMetaDataService mds=inject.getMetadataservice();
           int recStatusColId=mds.getColumnMetaData("ASSIGNMENT", "IVW_ASSIGNMENT", "RECORD_STATUS", session).getI_ColumnID()-1;
           int authStatusColId=mds.getColumnMetaData("ASSIGNMENT", "IVW_ASSIGNMENT", "AUTH_STATUS", session).getI_ColumnID()-1;
        
        try{
        
                
                
                Map<String,DBRecord>leaveMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT", "SVW_STUDENT_ASSIGNMENT", session, dbSession);

                Map<String,DBRecord>instituteAssignmentMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT","IVW_ASSIGNMENT", session, dbSession);
                
                Map<String,List<DBRecord>>aauthorizedRecords=instituteAssignmentMap.values().stream().filter(rec->rec.getRecord().get(recStatusColId).trim().equals("O")&&rec.getRecord().get(authStatusColId).trim().equals("A")).collect(Collectors.groupingBy(rec->rec.getRecord().get(2).trim()));
                dbg("aauthorizedRecords size"+aauthorizedRecords.size());
                Iterator<DBRecord>valueIterator=leaveMap.values().iterator();

                while(valueIterator.hasNext()){
                   DBRecord leaveRec=valueIterator.next();
                   String assignmentID=leaveRec.getRecord().get(1).trim();
                   dbg("assignmentID"+assignmentID);
                     if(aauthorizedRecords.containsKey(assignmentID))  {

                         dbg("aauthorizedRecords contains "+assignmentID);

                       l_assignmentList.add(leaveRec);

                     }
                }

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

        buildBOfromDB(l_assignmentList);     
        
          dbg("end of  completed student assignment--->view"); 
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
    

    
    private void buildBOfromDB(ArrayList<DBRecord>l_assignmentList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           String l_instituteID=request.getReqHeader().getInstituteID();
           RequestBody<StudentAssignmentBO> reqBody = request.getReqBody();
           StudentAssignmentBO studentAssignment =reqBody.get();
           String l_studentID=studentAssignment.getFilter().getStudentID();
           String l_fromDate=studentAssignment.getFilter().getFromDate();
           String l_toDate=studentAssignment.getFilter().getToDate();
           String dateFormat=i_db_properties.getProperty("DATE_FORMAT");
           SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
           ArrayList<DBRecord>filteredList=new ArrayList();
           BusinessService bs=inject.getBusinessService(session);
           dbg("l_studentID"+l_studentID);
           dbg("l_fromDate"+l_fromDate);
           dbg("l_toDate"+l_toDate);
           String studentName=bs.getStudentName(l_studentID, l_instituteID, session, dbSession, inject);
           studentAssignment.getFilter().setStudentName(studentName);
           

           
           
           for(int i=0;i<l_assignmentList.size();i++){
               
               DBRecord l_studentRecord=l_assignmentList.get(i);
               String activityID=l_studentRecord.getRecord().get(1).trim();
               String dueDate=l_studentRecord.getRecord().get(2).trim();
               
               
               Date l_dueDate=formatter.parse(dueDate);
               Date fromDate=formatter.parse(l_fromDate);
               Date toDate=formatter.parse(l_toDate);
               dbg("l_dueDate"+l_dueDate);
               dbg("fromDate"+fromDate);
               dbg("toDate"+toDate);
               
                if(l_dueDate.compareTo(fromDate)>=0){
                    
                    dbg("eventDate.compareTo(fromDate)>=0");
                    
                    if(l_dueDate.compareTo(toDate)<=0){
                       
                           
                           dbg("paymentDate.compareTo(toDate)<=0");
                       

                               filteredList.add(l_studentRecord);
                          
                       
                    }
                    
                }
           }
  
         
           
           studentAssignment.result=new StudentAssignmentResult[filteredList.size()];
           for(int i=0;i<filteredList.size();i++){
               
               ArrayList<String> l_studentAssignmentList=filteredList.get(i).getRecord();
               studentAssignment.result[i]=new StudentAssignmentResult();
               studentAssignment.result[i].setStudentID(l_studentAssignmentList.get(0).trim());
               studentAssignment.result[i].setAssignmentID(l_studentAssignmentList.get(1).trim());
               studentAssignment.result[i].setDueDate(l_studentAssignmentList.get(2).trim());
               
               String[] l_pkey={l_studentAssignmentList.get(1).trim()};
               DBRecord instituteAssignmentRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT","IVW_ASSIGNMENT", l_pkey, session,dbSession);
               String assignmentType=bs.getAssignmentType(instituteAssignmentRecord.getRecord().get(5).trim());
               dbg("assignmentType"+assignmentType);
               studentAssignment.result[i].setAssignmentType(assignmentType);
          }
           
           if(studentAssignment.result==null||studentAssignment.result.length==0){
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
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        JsonObject filter;
        try{
        dbg("inside student assignment buildJsonResFromBO");    
        RequestBody<StudentAssignmentBO> reqBody = request.getReqBody();
        StudentAssignmentBO studentAssignment =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<studentAssignment.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("studentID", studentAssignment.result[i].getStudentID())
                                                      .add("assignmentID",studentAssignment.result[i].getAssignmentID())
                                                      .add("assignmentType", studentAssignment.result[i].getAssignmentType())
                                                      .add("dueDate", studentAssignment.result[i].getDueDate())
);
        }

           filter=Json.createObjectBuilder()  .add("studentID",studentAssignment.filter.getStudentID())
                                              .add("studentName",studentAssignment.filter.getStudentName())
                                              .add("fromDate", studentAssignment.filter.getFromDate())
                                              .add("toDate",studentAssignment.filter.getToDate())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of student assignment buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student assignment--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of student assignment--->businessValidation"); 
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
        dbg("inside student assignment master mandatory validation");
        RequestBody<StudentAssignmentBO> reqBody = request.getReqBody();
        StudentAssignmentBO studentAssignment =reqBody.get();
        boolean fromDateEmpty=false;
        boolean toDateEmpty=false;
        BusinessService bs=inject.getBusinessService(session);
        String studentID=studentAssignment.getFilter().getStudentID();
        String studentName=studentAssignment.getFilter().getStudentName();
        String instituteID=request.getReqHeader().getInstituteID();
        studentID=bs.studentValidation(studentID, studentName, instituteID, session, dbSession, inject);

        studentAssignment.getFilter().setStudentID(studentID);
        
        
        
        
         if(studentAssignment.getFilter().getStudentID()==null||studentAssignment.getFilter().getStudentID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","studentID");
         }
          
           if(studentAssignment.getFilter().getFromDate()==null||studentAssignment.getFilter().getFromDate().isEmpty()){
              fromDateEmpty=true;
         }
          
          if(studentAssignment.getFilter().getToDate()==null||studentAssignment.getFilter().getToDate().isEmpty()){
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
          
        dbg("end of student assignment master mandatory validation");
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
             dbg("inside student assignment detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<StudentAssignmentBO> reqBody = request.getReqBody();
             StudentAssignmentBO studentAssignment =reqBody.get();
             String l_studentID=studentAssignment.getFilter().getStudentID();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_fromDate=studentAssignment.getFilter().getFromDate();
             String l_toDate=studentAssignment.getFilter().getToDate();
             BusinessService bs=inject.getBusinessService(session);
             
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
                 
//                 if(!bsv.futureDateValidation(l_fromDate, session, dbSession, inject)){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_048",null);
//                     throw new BSValidationException("BSValidationException");
//                 }
//                 
//                 if(!bsv.futureDateValidation(l_toDate, session, dbSession, inject)){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_048",null);
//                     throw new BSValidationException("BSValidationException");
//                 }
                 
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


//                   int dateSize= bs.getLeaveDates(l_fromDate, l_toDate, session, dbSession, inject).size();
//
//
//                   if(dateSize>7){
//
//                        status=false;
//                        errhandler.log_app_error("BS_VAL_049","Date range");
//                        throw new BSValidationException("BSValidationException");
//                    }

             }

             
            dbg("end of student assignment detailDataValidation");
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
