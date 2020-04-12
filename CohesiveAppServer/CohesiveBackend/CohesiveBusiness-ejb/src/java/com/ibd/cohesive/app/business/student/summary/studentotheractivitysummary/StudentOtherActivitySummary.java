/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentotheractivitysummary;

import com.ibd.businessViews.IStudentOtherActivitySummary;
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
//@Local(IStudentOtherActivitySummary.class)
@Remote(IStudentOtherActivitySummary.class)
@Stateless
public class StudentOtherActivitySummary implements IStudentOtherActivitySummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentOtherActivitySummary(){
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
       dbg("inside StudentOtherActivitySummary--->processing");
       dbg("StudentOtherActivitySummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IStudentOtherActivitySummary>studentOtherActivityEJB=new BusinessEJB();
       studentOtherActivityEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentOtherActivityEJB);
       
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
      StudentOtherActivityBO studentOtherActivity=new StudentOtherActivityBO();
      RequestBody<StudentOtherActivityBO> reqBody = new RequestBody<StudentOtherActivityBO>(); 
           
      try{
      dbg("inside student leave buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      studentOtherActivity.filter=new StudentOtherActivityFilter();
      BSValidation bsv=inject.getBsv(session);
//      studentOtherActivity.filter.setStudentID(l_filterObject.getString("studentID"));
//      studentOtherActivity.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      studentOtherActivity.filter.setAuthStatus(l_filterObject.getString("authStat"));
//      studentOtherActivity.filter.setActivityName(l_filterObject.getString("activityName"));
//      studentOtherActivity.filter.setActivityType(l_filterObject.getString("activityType"));
//      studentOtherActivity.filter.setLevel(l_filterObject.getString("level"));
//      studentOtherActivity.filter.setVenue(l_filterObject.getString("venue"));
//      studentOtherActivity.filter.setDate(l_filterObject.getString("date"));
      
      studentOtherActivity.filter.setStudentID(l_filterObject.getString("studentID"));
      studentOtherActivity.filter.setStudentName(l_filterObject.getString("studentName"));
      studentOtherActivity.filter.setFromDate(l_filterObject.getString("fromDate"));
      studentOtherActivity.filter.setToDate(l_filterObject.getString("toDate"));
      if(l_filterObject.getString("authStat").equals("Select option")){
          
          studentOtherActivity.filter.setAuthStatus("");
      }else{
      
          studentOtherActivity.filter.setAuthStatus(l_filterObject.getString("authStat"));
      }
      if(l_filterObject.getString("class").equals("Select option")||l_filterObject.getString("class").equals("")){
          studentOtherActivity.filter.setStandard("");
          studentOtherActivity.filter.setSection("");
      }else{

          String l_class=l_filterObject.getString("class");
          bsv.classValidation(l_class,session);
          studentOtherActivity.filter.setStandard(l_class.split("/")[0]);
          studentOtherActivity.filter.setSection(l_class.split("/")[1]);
      
      }
      
        reqBody.set(studentOtherActivity);
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
        RequestBody<StudentOtherActivityBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_leaveList=new ArrayList();
        StudentOtherActivityBO studentOtherActivity=reqBody.get();
        String l_studentID=studentOtherActivity.getFilter().getStudentID();
        String l_standard=studentOtherActivity.getFilter().getStandard();
        String l_section=studentOtherActivity.getFilter().getSection();
        
        
        ArrayList<String>l_fileNames=bs.getStudentFileNames(l_studentID,request,session,dbSession,inject,l_standard,l_section);
        for(int i=0;i<l_fileNames.size();i++){
            dbg("inside file name iteration");
            String l_fileName=l_fileNames.get(i);
            dbg("l_fileName"+l_fileName);
        
        
        try{
        

                
                Map<String,DBRecord>leaveMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY", "SVW_STUDENT_OTHER_ACTIVITY", session, dbSession);

                Iterator<DBRecord>valueIterator=leaveMap.values().iterator();

                while(valueIterator.hasNext()){
                   DBRecord leaveRec=valueIterator.next();
                   l_leaveList.add(leaveRec);

                }

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
    

    
    private void buildBOfromDB(ArrayList<DBRecord>l_otherActivityList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           String l_instituteID=request.getReqHeader().getInstituteID();
           RequestBody<StudentOtherActivityBO> reqBody = request.getReqBody();
           StudentOtherActivityBO studentOtherActivity =reqBody.get();
           String l_studentID=studentOtherActivity.getFilter().getStudentID();
           String l_fromDate=studentOtherActivity.getFilter().getFromDate();
           String l_toDate=studentOtherActivity.getFilter().getToDate();
           String authStatusFilter=studentOtherActivity.getFilter().getAuthStatus();
           String dateFormat=i_db_properties.getProperty("DATE_FORMAT");
           SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
           ArrayList<DBRecord>filteredList=new ArrayList();
           BusinessService bs=inject.getBusinessService(session);
           
           
           String studentName=bs.getStudentName(l_studentID, l_instituteID, session, dbSession, inject);
           
           studentOtherActivity.getFilter().setStudentName(studentName);
           
           dbg("l_fromDate"+l_fromDate);
           dbg("l_studentID"+l_studentID);
           dbg("l_toDate"+l_toDate);
           
  
           for(int i=0;i<l_otherActivityList.size();i++){
               
               DBRecord l_studentRecord=l_otherActivityList.get(i);
               String activityID=l_studentRecord.getRecord().get(1).trim();
               String authStatus=l_studentRecord.getRecord().get(10).trim();
               
               String[] l_pkey={activityID};
               
               DBRecord instituteRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY","IVW_OTHER_ACTIVITY", l_pkey, session, dbSession);
               String l_eventDate=instituteRec.getRecord().get(7).trim();
               Date eventDate=formatter.parse(l_eventDate);
               Date fromDate=formatter.parse(l_fromDate);
               Date toDate=formatter.parse(l_toDate);
               dbg("eventDate"+eventDate);
               dbg("fromDate"+fromDate);
               dbg("toDate"+toDate);
               
                if(eventDate.compareTo(fromDate)>=0){
                    
                    dbg("eventDate.compareTo(fromDate)>=0");
                    
                    if(eventDate.compareTo(toDate)<=0){
                       
                           
                           dbg("paymentDate.compareTo(toDate)<=0");
                       
                           if(authStatusFilter.isEmpty()){
                           

                               filteredList.add(l_studentRecord);
                           }else{
                               
                               if(authStatusFilter.equals(authStatus)){
                                   
                                   filteredList.add(l_studentRecord);
                               }
                               
                           }
                       
                    }
                    
                }
           }
           
           
           
           
           studentOtherActivity.result=new StudentOtherActivityResult[filteredList.size()];
           for(int i=0;i<filteredList.size();i++){
               
               ArrayList<String> l_studentOtherActivityList=filteredList.get(i).getRecord();
               studentOtherActivity.result[i]=new StudentOtherActivityResult();
               studentOtherActivity.result[i].setStudentID(l_studentOtherActivityList.get(0).trim());
               studentOtherActivity.result[i].setActivityID(l_studentOtherActivityList.get(1).trim());
               studentOtherActivity.result[i].setResult(l_studentOtherActivityList.get(4).trim());
               
               String[] l_pkey={studentOtherActivity.result[i].getActivityID()};
               
               DBRecord instituteRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY","IVW_OTHER_ACTIVITY", l_pkey, session, dbSession);
               String l_Date=instituteRec.getRecord().get(7).trim();
               
               studentOtherActivity.result[i].setActivityName(instituteRec.getRecord().get(3).trim());
               
               String activityType=bs.getActivityType(instituteRec.getRecord().get(4).trim());
               
               studentOtherActivity.result[i].setActivityType(activityType);
               studentOtherActivity.result[i].setDate(l_Date);
              
          }    
           
           
           if(studentOtherActivity.result==null||studentOtherActivity.result.length==0){
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
        RequestBody<StudentOtherActivityBO> reqBody = request.getReqBody();
        StudentOtherActivityBO studentOtherActivity =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<studentOtherActivity.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("studentID", studentOtherActivity.result[i].getStudentID())
                                                      .add("activityID", studentOtherActivity.result[i].getActivityID())
                                                      .add("activityName", studentOtherActivity.result[i].getActivityName())
                                                      .add("activityType", studentOtherActivity.result[i].getActivityType())
                                                      .add("date", studentOtherActivity.result[i].getDate())
                                                      .add("result", studentOtherActivity.result[i].getResult()));
        }

           filter=Json.createObjectBuilder()  .add("studentID",studentOtherActivity.filter.getStudentID())
                                              .add("studentName",studentOtherActivity.filter.getStudentName())
                                              .add("fromdate", studentOtherActivity.filter.getFromDate())
                                              .add("toDate", studentOtherActivity.filter.getToDate())
                                              .add("authStat", studentOtherActivity.filter.getAuthStatus())
                                              .add("class",studentOtherActivity.filter.getStandard()+"/"+studentOtherActivity.filter.getSection())
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
        RequestBody<StudentOtherActivityBO> reqBody = request.getReqBody();
        StudentOtherActivityBO studentOtherActivity =reqBody.get();
        boolean fromDateEmpty=false;
        boolean toDateEmpty=false;
        
        
        String p_studentID=studentOtherActivity.getFilter().getStudentID();
        String section=studentOtherActivity.getFilter().getSection();
        String standard=studentOtherActivity.getFilter().getStandard();
        BusinessService bs=inject.getBusinessService(session);
        String userID=request.getReqHeader().getUserID();
        String userType=bs.getUserType(userID, session, dbSession, inject);  
        String studentID=studentOtherActivity.getFilter().getStudentID();
        String studentName=studentOtherActivity.getFilter().getStudentName();
        String instituteID=request.getReqHeader().getInstituteID();
        studentID=bs.studentValidation(studentID, studentName, instituteID, session, dbSession, inject);

        studentOtherActivity.getFilter().setStudentID(studentID);
        
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
         
       }
          
           if(studentOtherActivity.getFilter().getFromDate()==null||studentOtherActivity.getFilter().getFromDate().isEmpty()){
              fromDateEmpty=true;
         }
          
          if(studentOtherActivity.getFilter().getToDate()==null||studentOtherActivity.getFilter().getToDate().isEmpty()){
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
             BusinessService bs=inject.getBusinessService(session);
             RequestBody<StudentOtherActivityBO> reqBody = request.getReqBody();
             StudentOtherActivityBO studentOtherActivity =reqBody.get();
             String l_studentID=studentOtherActivity.getFilter().getStudentID();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_fromDate=studentOtherActivity.getFilter().getFromDate();
             String l_toDate=studentOtherActivity.getFilter().getToDate();
             
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
