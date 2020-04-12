/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentsoftskill;

import com.ibd.businessViews.IStudentSoftSkillSummary;
import com.ibd.businessViews.IStudentSoftSkillSummary;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.student.summary.studentprogresscard.ProgressSignStatus;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
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
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
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
@Remote(IStudentSoftSkillSummary.class)
@Stateless
public class StudentSoftSkillSummary implements IStudentSoftSkillSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentSoftSkillSummary(){
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
       dbg("inside StudentSoftSkillSummary--->processing");
       dbg("StudentSoftSkillSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IStudentSoftSkillSummary>studentSoftSkillEJB=new BusinessEJB();
       studentSoftSkillEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentSoftSkillEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,studentSoftSkillEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentSoftSkillEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student progreass");
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
      StudentSoftSkillBO studentSoftSkill=new StudentSoftSkillBO();
      RequestBody<StudentSoftSkillBO> reqBody = new RequestBody<StudentSoftSkillBO>(); 
           
      try{
      dbg("inside student progreass buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      BSValidation bsv=inject.getBsv(session);
      studentSoftSkill.filter=new StudentSoftSkillFilter();
      studentSoftSkill.filter.setStudentID(l_filterObject.getString("studentID"));
      studentSoftSkill.filter.setStudentName(l_filterObject.getString("studentName"));
//      studentSoftSkill.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      studentSoftSkill.filter.setAuthStatus(l_filterObject.getString("authStat"));
//      studentSoftSkill.filter.setExam(l_filterObject.getString("exam"));

   

      if(l_filterObject.getString("class").equals("Select option")){
          studentSoftSkill.filter.setStandard("");
          studentSoftSkill.filter.setSection("");
      }else{

          String l_class=l_filterObject.getString("class");
          bsv.classValidation(l_class,session);
          studentSoftSkill.filter.setStandard(l_class.split("/")[0]);
          studentSoftSkill.filter.setSection(l_class.split("/")[1]);
      
      }
      


      if(l_filterObject.getString("signStatus").equals("Select option")){
          
          studentSoftSkill.filter.setSignStatus("");
      }else{
      
          studentSoftSkill.filter.setSignStatus(l_filterObject.getString("signStatus"));
      }

      
      if(l_filterObject.getString("exam").equals("Select option")){
          
          studentSoftSkill.filter.setExam("");
      }else{
      
          studentSoftSkill.filter.setExam(l_filterObject.getString("exam"));
      
      }

      
        reqBody.set(studentSoftSkill);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student progreass--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentSoftSkillBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        IPDataService pds=inject.getPdataservice();
        StudentSoftSkillBO studentSoftSkill=reqBody.get();
        String l_studentID=studentSoftSkill.getFilter().getStudentID();
        String l_standard=studentSoftSkill.getFilter().getStandard();
        String l_section=studentSoftSkill.getFilter().getSection();
        String l_exam=studentSoftSkill.getFilter().getExam();
        String l_signStatus=studentSoftSkill.getFilter().getSignStatus();
        ArrayList<ProgressSignStatus>studentsList=new ArrayList();
        

       
            String fileName;
            
            if(l_studentID==null||l_studentID.isEmpty()){
                
                fileName=l_standard+l_section;
            }else{
                
               String[] studentPkey={l_studentID}; 
               ArrayList<String>l_studentList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",studentPkey);
               String standard=l_studentList.get(2).trim();
               String section=l_studentList.get(3).trim();
                fileName=standard+section;
            }
               
        if(l_signStatus.equals("Y")){    
            
            
            try{

                Map<String,DBRecord>markMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+fileName+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS","STUDENT_SOFT_SKILL_SIGNATURE", session, dbSession);
                Iterator<String>keyIterator=markMap.keySet().iterator();
                    while(keyIterator.hasNext()){
                          
                         String studentID=keyIterator.next();
                         ProgressSignStatus progress=new ProgressSignStatus();
                         progress.setStudentID(studentID);
                         progress.setSignStatus("Yes");
                         studentsList.add(progress);

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

        }else if(l_signStatus.equals("N")){
            
            
            try{
            
                Map<String,DBRecord>markMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+fileName+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS","STUDENT_SKILLS", session, dbSession);
                dbg("markMap size"+markMap.size());
                Map<String,List<DBRecord>>studentGroup=markMap.values().stream().collect(Collectors.groupingBy(rec->rec.getRecord().get(4).trim())); 
                dbg("studentGroup size"+studentGroup.size());
                Iterator<String>keyIterator=studentGroup.keySet().iterator();


                while(keyIterator.hasNext()){

                    String studentID=keyIterator.next();
                    dbg("inside student iteration--student id"+studentID);
                    ProgressSignStatus progress=new ProgressSignStatus();
                    progress.setStudentID(studentID);
                    String[] l_pkey={studentID};
                    boolean recordExistence=false;
                    
                    try{
                    
                    
                       readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+fileName+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS","STUDENT_SOFT_SKILL_SIGNATURE", l_pkey, session, dbSession);
                    
                    recordExistence=true;
                    
                    }catch(DBValidationException ex){
            
                        if(ex.toString().contains("DB_VAL_000")||ex.toString().contains("DB_VAL_011")){
                            recordExistence=false;
                            session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                            session.getErrorhandler().removeSessionErrCode("DB_VAL_000");

                        }else{
                            throw ex;
                        }
            
                   }
                    
                    dbg("signature record existenc  "+recordExistence);

                    if(!recordExistence){
                        
              
                        progress.setSignStatus("No");
                        studentsList.add(progress);
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
            
            
        }else {
            
            
            try{
            
                Map<String,DBRecord>markMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+fileName+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS","STUDENT_SKILLS", session, dbSession);
                dbg("markMap size"+markMap.size());
                Map<String,List<DBRecord>>studentGroup=markMap.values().stream().collect(Collectors.groupingBy(rec->rec.getRecord().get(4).trim())); 
                dbg("studentGroup size"+studentGroup.size());
                Iterator<String>keyIterator=studentGroup.keySet().iterator();


                while(keyIterator.hasNext()){

                    String studentID=keyIterator.next();
                    dbg("inside student iteration--student id"+studentID);
                    ProgressSignStatus progress=new ProgressSignStatus();
                    progress.setStudentID(studentID);
                    String[] l_pkey={studentID};
                    boolean recordExistence=false;
                    
                    try{
                    
                    
                       readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+fileName+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS","STUDENT_SOFT_SKILL_SIGNATURE", l_pkey, session, dbSession);
                    
                    recordExistence=true;
                    
                    }catch(DBValidationException ex){
            
                        if(ex.toString().contains("DB_VAL_000")||ex.toString().contains("DB_VAL_011")){
                            recordExistence=false;
                            session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                            session.getErrorhandler().removeSessionErrCode("DB_VAL_000");

                        }else{
                            throw ex;
                        }
            
                   }
                    
                    dbg("signature record existenc  "+recordExistence);

                    if(recordExistence){
                        
                        progress.setSignStatus("Yes");
                    }else{
                        
                        progress.setSignStatus("No");
                    }
                    
                    studentsList.add(progress);
                    
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
            
            
        }
        
        
        
        
        
       if(l_studentID!=null&&!l_studentID.isEmpty()){
           
           if(l_studentID!=null&&!l_studentID.isEmpty()){
            
             List<ProgressSignStatus>  l_studentList=  studentsList.stream().filter(rec->rec.getStudentID().equals(l_studentID)).collect(Collectors.toList());
             studentsList = new ArrayList<ProgressSignStatus>(l_studentList);
             dbg("studentID filter studentsList size"+studentsList.size());
           }
           
       }
        
        
        
        
        if(studentsList.isEmpty()){
            
            session.getErrorhandler().log_app_error("BS_VAL_013", null);
            throw new BSValidationException("BSValidationException");
            
        }
        
        

        buildBOfromDB(studentsList);     
        
          dbg("end of  completed student progreass--->view");  
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
    

    
    private void buildBOfromDB(ArrayList<ProgressSignStatus>l_progreassList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<StudentSoftSkillBO> reqBody = request.getReqBody();
           StudentSoftSkillBO studentSoftSkill =reqBody.get();
           BusinessService bs=inject.getBusinessService(session);
           String instituteID=request.getReqHeader().getInstituteID();
//           String l_signStatus=studentSoftSkill.getFilter().getSignStatus();
//           String l_studentID=studentSoftSkill.getFilter().getStudentID();
           String l_exam=studentSoftSkill.getFilter().getExam();
           
//           dbg("l_signStatus"+l_signStatus);
//           dbg("l_studentID"+l_studentID);
//           dbg("l_exam"+l_exam);

           
           dbg("l_progreassList size"+l_progreassList.size());
           studentSoftSkill.result=new StudentSoftSkillResult[l_progreassList.size()];
           for(int i=0;i<l_progreassList.size();i++){
               
               studentSoftSkill.result[i]=new StudentSoftSkillResult();
               
               studentSoftSkill.result[i].setStudentID(l_progreassList.get(i).getStudentID());
               studentSoftSkill.result[i].setStudentName(bs.getStudentName(l_progreassList.get(i).getStudentID(), instituteID, session, dbSession, inject));
               studentSoftSkill.result[i].setSignStatus(l_progreassList.get(i).getSignStatus());
               studentSoftSkill.result[i].setExam(l_exam);
               
          }    
           
           if(studentSoftSkill.result==null||studentSoftSkill.result.length==0){
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
        dbg("inside student progreass buildJsonResFromBO");    
        RequestBody<StudentSoftSkillBO> reqBody = request.getReqBody();
        StudentSoftSkillBO studentSoftSkill =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
        
             
        for(int i=0;i<studentSoftSkill.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("studentID", studentSoftSkill.result[i].getStudentID())
                                                      .add("studentName", studentSoftSkill.result[i].getStudentName())
                                                      .add("signStatus", studentSoftSkill.result[i].getSignStatus())
                                                      .add("exam", studentSoftSkill.result[i].getExam())
                                                      );
        }

           filter=Json.createObjectBuilder()  .add("studentID",studentSoftSkill.filter.getStudentID())
                                              .add("studentName",studentSoftSkill.filter.getStudentName())
                                              .add("signStatus", studentSoftSkill.filter.getSignStatus())
                                              .add("exam", studentSoftSkill.filter.getExam())
                                              .add("class",studentSoftSkill.filter.getStandard()+"/"+studentSoftSkill.filter.getSection())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of student progreass buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student progreass--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of student progreass--->businessValidation"); 
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
        dbg("inside student progreass master mandatory validation");
        
        RequestBody<StudentSoftSkillBO> reqBody = request.getReqBody();
        StudentSoftSkillBO studentSoftSkill =reqBody.get();
        String standard=studentSoftSkill.getFilter().getStandard();
        String section=studentSoftSkill.getFilter().getSection();
        String studentID=studentSoftSkill.getFilter().getStudentID();
        BusinessService bs=inject.getBusinessService(session);
        String userID=request.getReqHeader().getUserID();
        String userType=bs.getUserType(userID, session, dbSession, inject);
        String studentName=studentSoftSkill.getFilter().getStudentName();
        String instituteID=request.getReqHeader().getInstituteID();
        studentID=bs.studentValidation(studentID, studentName, instituteID, session, dbSession, inject);

        studentSoftSkill.getFilter().setStudentID(studentID);
        
       if(userType.equals("P")){
            
             if(studentID==null||studentID.isEmpty()){
                
                    session.getErrorhandler().log_app_error("BS_VAL_002", "Student Name");
                    throw new BSValidationException("BSValidationException");
                
                }
            
        }else{ 
        
            if(standard==null||standard.isEmpty()||section==null||section.isEmpty()){
                
                if(studentID==null||studentID.isEmpty()){
                
                    session.getErrorhandler().log_app_error("BS_VAL_027", studentID);
                    throw new BSValidationException("BSValidationException");
                
                }
                
            }
            
       }
            
            if(studentSoftSkill.getFilter().getExam()==null||studentSoftSkill.getFilter().getExam().isEmpty()){
              
               status=false;  
               errhandler.log_app_error("BS_VAL_002","Exam");
          }
            
          
        dbg("end of student progreass master mandatory validation");
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
             dbg("inside student progreass detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<StudentSoftSkillBO> reqBody = request.getReqBody();
             StudentSoftSkillBO studentSoftSkill =reqBody.get();
             String l_studentID=studentSoftSkill.getFilter().getStudentID();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_exam=studentSoftSkill.getFilter().getExam();
             
             if(l_studentID!=null&&!l_studentID.isEmpty()){
                 
                if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","studentID");
                }
                 
             }
     
             
             
             
             
             if(l_exam!=null&&!l_exam.isEmpty()){
             
                 if(!bsv.examValidation(l_exam,l_instituteID, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","exam");
                 }
             
             }
             
            dbg("end of student progreass detailDataValidation");
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
