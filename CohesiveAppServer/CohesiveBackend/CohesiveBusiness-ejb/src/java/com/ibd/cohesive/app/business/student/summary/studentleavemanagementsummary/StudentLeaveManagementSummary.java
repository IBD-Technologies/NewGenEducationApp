/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentleavemanagementsummary;

import com.ibd.businessViews.IStudentLeaveManagementSummary;
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
//@Local(IStudentLeaveManagementSummary.class)
@Remote(IStudentLeaveManagementSummary.class)
@Stateless
public class StudentLeaveManagementSummary implements IStudentLeaveManagementSummary{
       AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentLeaveManagementSummary(){
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
       dbg("inside StudentLeaveManagementSummary--->processing");
       dbg("StudentLeaveManagementSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IStudentLeaveManagementSummary>studentLeaveManagementEJB=new BusinessEJB();
       studentLeaveManagementEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentLeaveManagementEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,studentLeaveManagementEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentLeaveManagementEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student leave");
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
                dbg(jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
               // if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
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
      StudentLeaveManagementBO studentLeaveManagement=new StudentLeaveManagementBO();
      RequestBody<StudentLeaveManagementBO> reqBody = new RequestBody<StudentLeaveManagementBO>(); 
           
      try{
      dbg("inside student leave buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      BSValidation bsv=inject.getBsv(session);
      studentLeaveManagement.filter=new StudentLeaveManagementFilter();
//      studentLeaveManagement.filter.setStudentID(l_filterObject.getString("studentID"));
//      studentLeaveManagement.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      studentLeaveManagement.filter.setAuthStatus(l_filterObject.getString("authStat"));
//      studentLeaveManagement.filter.setFrom(l_filterObject.getString("from"));
//      studentLeaveManagement.filter.setTo(l_filterObject.getString("to"));
//      studentLeaveManagement.filter.setFromNoon(l_filterObject.getString("fromNoon"));
//      studentLeaveManagement.filter.setToNoon(l_filterObject.getString("toNoon"));
//      studentLeaveManagement.filter.setType(l_filterObject.getString("type"));
//      studentLeaveManagement.filter.setStatus(l_filterObject.getString("status"));

      
      studentLeaveManagement.filter.setStudentID(l_filterObject.getString("studentID"));
      studentLeaveManagement.filter.setStudentName(l_filterObject.getString("studentName"));
      studentLeaveManagement.filter.setFrom(l_filterObject.getString("from"));
      studentLeaveManagement.filter.setTo(l_filterObject.getString("to"));
      
      if(l_filterObject.getString("leaveStatus").equals("Select option")){
          
          studentLeaveManagement.filter.setLeaveStatus("");
      }else{
      
          studentLeaveManagement.filter.setLeaveStatus(l_filterObject.getString("leaveStatus"));
      }

       if(l_filterObject.getString("class").equals("Select option")||l_filterObject.getString("class").equals("")){
          studentLeaveManagement.filter.setStandard("");
          studentLeaveManagement.filter.setSection("");
      }else{

           
          String l_class=l_filterObject.getString("class");
          dbg("class"+l_class);
          bsv.classValidation(l_class,session);
          studentLeaveManagement.filter.setStandard(l_class.split("/")[0]);
          studentLeaveManagement.filter.setSection(l_class.split("/")[1]);
      
      }
      
        reqBody.set(studentLeaveManagement);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student leave--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentLeaveManagementBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_leaveList=new ArrayList();
        StudentLeaveManagementBO studentLeaveManagement=reqBody.get();
        String l_studentID=studentLeaveManagement.getFilter().getStudentID();
        String l_standard=studentLeaveManagement.getFilter().getStandard();
        String l_section=studentLeaveManagement.getFilter().getSection();
        
        ArrayList<String>l_fileNames=bs.getStudentFileNames(l_studentID,request,session,dbSession,inject,l_standard,l_section);
        for(int i=0;i<l_fileNames.size();i++){
            dbg("inside file name iteration");
            String l_fileName=l_fileNames.get(i);
            dbg("l_fileName"+l_fileName);
        
            try{


                Map<String,DBRecord>leaveMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileName+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE", "SVW_STUDENT_LEAVE_MANAGEMENT", session, dbSession);

                Iterator<DBRecord>valueIterator=leaveMap.values().iterator();

                while(valueIterator.hasNext()){
                   DBRecord leaveRec=valueIterator.next();
                   l_leaveList.add(leaveRec);

                }

            }catch(DBValidationException ex){

                if(ex.toString().contains("DB_VAL_000")||ex.toString().contains("DB_VAL_011")){

                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
//                    session.getErrorhandler().log_app_error("BS_VAL_013", null);
//                    throw new BSValidationException("BSValidationException");

                }else{
                    throw ex;
                }

            }
        
        }
        
        
        if(l_leaveList.isEmpty()){
            
            session.getErrorhandler().log_app_error("BS_VAL_013", null);
            throw new BSValidationException("BSValidationException");
        }
        
        

        buildBOfromDB(l_leaveList);     
        
          dbg("end of  completed student leave--->view");  
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
    

    
    private void buildBOfromDB(ArrayList<DBRecord>l_leaveList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<StudentLeaveManagementBO> reqBody = request.getReqBody();
           StudentLeaveManagementBO studentLeaveManagement =reqBody.get();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           String l_instituteID=request.getReqHeader().getInstituteID();
           String l_leaveStatus=studentLeaveManagement.getFilter().getLeaveStatus();
           String l_studentID=studentLeaveManagement.getFilter().getStudentID();
           String l_from=studentLeaveManagement.getFilter().getFrom();
           String l_to=studentLeaveManagement.getFilter().getTo();
           String dateFormat=i_db_properties.getProperty("DATE_FORMAT");
           SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
           ArrayList<DBRecord>filteredList=new ArrayList();
           BusinessService bs=inject.getBusinessService(session);
           
           if(l_studentID!=null&&!l_studentID.isEmpty()){
           
           String studentName=bs.getStudentName(l_studentID, l_instituteID, session, dbSession, inject);
           studentLeaveManagement.getFilter().setStudentName(studentName);
           
           }else{
               
               studentLeaveManagement.getFilter().setStudentName("");
           }
           String userID=request.getReqHeader().getUserID();
           String userType=bs.getUserType(userID, session, dbSession, inject);
           
           dbg("l_leaveStatus"+l_leaveStatus);
           dbg("l_studentID"+l_studentID);
           dbg("l_from"+l_from);
           dbg("l_to"+l_to);
           
           
           
           
           if(l_studentID!=null&&!l_studentID.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_leaveList.stream().filter(rec->rec.getRecord().get(0).trim().equals(l_studentID)).collect(Collectors.toList());
             l_leaveList = new ArrayList<DBRecord>(l_studentList);
             dbg("studentID filter l_leaveList size"+l_leaveList.size());
           }
           
           if(l_leaveStatus!=null&&!l_leaveStatus.isEmpty()){
               
               List<DBRecord>  l_studentList=  l_leaveList.stream().filter(rec->rec.getRecord().get(10).trim().equals(l_leaveStatus)).collect(Collectors.toList());
               l_leaveList = new ArrayList<DBRecord>(l_studentList);
               dbg("l_leaveStatus filter l_leaveList size"+l_leaveList.size());
               
           }
           
           
           if(userType.equals("P")){
               
               filteredList=l_leaveList;
           }else{
           
           
               for(int i=0;i<l_leaveList.size();i++){

                   DBRecord l_studentRecord=l_leaveList.get(i);

//                   String l_dbFromDate=l_studentRecord.getRecord().get(1).trim();
                   String l_dbFromDate=l_studentRecord.getRecord().get(7).trim();
                   Date dbFromDate=formatter.parse(l_dbFromDate);
                   Date fromDate=formatter.parse(l_from);
                   Date toDate=formatter.parse(l_to);
                   dbg("dbFromDate"+dbFromDate);
                   dbg("fromDate"+fromDate);
                   dbg("toDate"+toDate);

                    if(dbFromDate.compareTo(fromDate)>=0){

                        dbg("eventDate.compareTo(fromDate)>=0");

                        if(dbFromDate.compareTo(toDate)<=0){


                               dbg("paymentDate.compareTo(toDate)<=0");


                                   filteredList.add(l_studentRecord);


                        }

                    }
               }
           
           }
          
           
           
           
           studentLeaveManagement.result=new StudentLeaveManagementResult[filteredList.size()];
           for(int i=0;i<filteredList.size();i++){
               
               ArrayList<String> l_studentLeaveManagementList=filteredList.get(i).getRecord();
               studentLeaveManagement.result[i]=new StudentLeaveManagementResult();
               studentLeaveManagement.result[i].setStudentID(l_studentLeaveManagementList.get(0).trim());
               studentLeaveManagement.result[i].setFrom(l_studentLeaveManagementList.get(1).trim());
               studentLeaveManagement.result[i].setTo(l_studentLeaveManagementList.get(2).trim());
               
               String type=l_studentLeaveManagementList.get(3).trim();
               
               if(type.equals("S")){
                   
                   studentLeaveManagement.result[i].setType("Sick");
               }else if(type.equals("P")){
                   
                   studentLeaveManagement.result[i].setType("Planned");
               }else if(type.equals("C")){
                   
                   studentLeaveManagement.result[i].setType("Casual");
               }
               
               
               
//               studentLeaveManagement.result[i].setType(l_studentLeaveManagementList.get(3).trim());
               
               String authStatus=l_studentLeaveManagementList.get(10).trim();
               
               if(authStatus.equals("U")){
                   
                   studentLeaveManagement.result[i].setLeaveStatus("Pending");
               }else if(authStatus.equals("A")){
                   
                   studentLeaveManagement.result[i].setLeaveStatus("Approved");
               }else if(authStatus.equals("R")){
                   
                   studentLeaveManagement.result[i].setLeaveStatus("Rejected");
               }
               
          }    
           
           
           if(studentLeaveManagement.result==null||studentLeaveManagement.result.length==0){
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
        dbg("inside student leave buildJsonResFromBO");    
        RequestBody<StudentLeaveManagementBO> reqBody = request.getReqBody();
        StudentLeaveManagementBO studentLeaveManagement =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<studentLeaveManagement.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("studentID", studentLeaveManagement.result[i].getStudentID())
                                                      .add("from", studentLeaveManagement.result[i].getFrom())
                                                      .add("to", studentLeaveManagement.result[i].getTo())
                                                      .add("leaveStatus", studentLeaveManagement.result[i].getLeaveStatus())
                                                      .add("type", studentLeaveManagement.result[i].getType()));
        }

           filter=Json.createObjectBuilder()  .add("studentID",studentLeaveManagement.filter.getStudentID())
                                              .add("leaveStatus", studentLeaveManagement.filter.getLeaveStatus())
                                              .add("from", studentLeaveManagement.filter.getFrom())
                                              .add("to",  studentLeaveManagement.filter.getTo())
                                              .add("studentName",studentLeaveManagement.filter.getStudentName())
                                              .add("class",studentLeaveManagement.filter.getStandard()+"/"+studentLeaveManagement.filter.getSection())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of student leave buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student leave--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of student leave--->businessValidation"); 
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
        dbg("inside student leave master mandatory validation");
        BusinessService bs=inject.getBusinessService(session);
        RequestBody<StudentLeaveManagementBO> reqBody = request.getReqBody();
        StudentLeaveManagementBO studentLeaveManagement =reqBody.get();
        boolean fromDateEmpty=false;
        boolean toDateEmpty=false;
        String p_studentID=studentLeaveManagement.getFilter().getStudentID();
        String section=studentLeaveManagement.getFilter().getSection();
        String standard=studentLeaveManagement.getFilter().getStandard();
        String userID=request.getReqHeader().getUserID();
        String userType=bs.getUserType(userID, session, dbSession, inject);
        String studentID=studentLeaveManagement.getFilter().getStudentID();
        String studentName=studentLeaveManagement.getFilter().getStudentName();
        String instituteID=request.getReqHeader().getInstituteID();
        studentID=bs.studentValidation(studentID, studentName, instituteID, session, dbSession, inject);

        studentLeaveManagement.getFilter().setStudentID(studentID);
        
        
        if(userType.equals("P")){
            
             if(studentID==null||studentID.isEmpty()){
                
                    session.getErrorhandler().log_app_error("BS_VAL_002", "Student Name");
                    throw new BSValidationException("BSValidationException");
                
                }
            
        }else{
        
        
         if(standard==null||standard.isEmpty()||section==null||section.isEmpty()){
                
                if(studentID==null||studentID.isEmpty()){
                
                    session.getErrorhandler().log_app_error("BS_VAL_027", p_studentID);
                    throw new BSValidationException("BSValidationException");
                
                }
                
            }
          
           if(studentLeaveManagement.getFilter().getFrom()==null||studentLeaveManagement.getFilter().getFrom().isEmpty()){
              fromDateEmpty=true;
         }
          
          if(studentLeaveManagement.getFilter().getTo()==null||studentLeaveManagement.getFilter().getTo().isEmpty()){
             toDateEmpty=true;
         }
          
          if(fromDateEmpty==true){
              
             status=false;  
             errhandler.log_app_error("BS_VAL_002","From Date");
          }
          
          if(toDateEmpty==true){
              
             status=false;  
             errhandler.log_app_error("BS_VAL_002","To Date");
          }
        }
        dbg("end of student leave master mandatory validation");
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
             dbg("inside student leave detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<StudentLeaveManagementBO> reqBody = request.getReqBody();
             BusinessService bs=inject.getBusinessService(session);
             StudentLeaveManagementBO studentLeaveManagement =reqBody.get();
             String l_studentID=studentLeaveManagement.getFilter().getStudentID();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_leaveStatus=studentLeaveManagement.getFilter().getLeaveStatus();
             String l_fromDate=studentLeaveManagement.getFilter().getFrom();
             String l_toDate=studentLeaveManagement.getFilter().getTo();
             
             if(l_studentID!=null&&!l_studentID.isEmpty()){
                 
                if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","Student Id");
                }
                 
             }
             
             
             if(l_leaveStatus!=null&&!l_leaveStatus.isEmpty()){
                 
                if(!bsv.authStatusValidation(l_leaveStatus, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","Leave status");
                }
                 
             }
             
             if(l_fromDate!=null&&!l_fromDate.isEmpty()&&l_toDate!=null&&!l_toDate.isEmpty()){
             
             
                 if(!bsv.dateFormatValidation(l_fromDate, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","From Date");
                     throw new BSValidationException("BSValidationException");
                 }
                 
                 if(!bsv.dateFormatValidation(l_toDate, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","To Date");
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


                   int dateSize= bs.getLeaveDates(l_fromDate, l_toDate, session, dbSession, inject).size();


                   if(dateSize>31){

                        status=false;
                        errhandler.log_app_error("BS_VAL_049","Date range");
                        throw new BSValidationException("BSValidationException");
                    }

             }
             
             
            dbg("end of student leave detailDataValidation");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
         } catch(BSValidationException ex){
            throw ex;    
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
