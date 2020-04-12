/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentsoftskill;

import com.ibd.businessViews.IStudentSoftSkillService;
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
 * @author DELL
 */
@Remote(IStudentSoftSkillService.class)
@Stateless
public class StudentSoftSkillService implements IStudentSoftSkillService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentSoftSkillService(){
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
       dbg("inside StudentSoftSkillService--->processing");
       dbg("StudentSoftSkillService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<StudentSoftSkill> reqBody = request.getReqBody();
       StudentSoftSkill studentSoftSkill =reqBody.get();
       businessLock=inject.getBusinessLockService();
       String l_studentID=studentSoftSkill.getStudentID();
       String l_exam=studentSoftSkill.getExam();
       l_lockKey=l_studentID+"~"+l_exam;
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<IStudentSoftSkillService>studentSoftSkillEJB=new BusinessEJB();
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
              dbg("commit is called in student softSkill");
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
      StudentSoftSkill studentSoftSkill=new StudentSoftSkill();
      RequestBody<StudentSoftSkill> reqBody = new RequestBody<StudentSoftSkill>(); 
           
      try{
      dbg("inside student softSkill buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      studentSoftSkill.setStudentID(l_body.getString("studentID"));
      studentSoftSkill.setStudentName(l_body.getString("studentName"));
      studentSoftSkill.setExam(l_body.getString("exam"));
      if(!l_operation.equals("View")){
          JsonArray l_skills=l_body.getJsonArray("skills");
          studentSoftSkill.skill=new Skills[l_skills.size()];
          for(int i=0;i<l_skills.size();i++){
              studentSoftSkill.skill[i]=new Skills();
              JsonObject l_skillObject=l_skills.getJsonObject(i);
              studentSoftSkill.skill[i].setSkillID(l_skillObject.getString("skillID"));
              studentSoftSkill.skill[i].setCategory(l_skillObject.getString("category"));
              studentSoftSkill.skill[i].setTeacherFeedback(l_skillObject.getString("teacherFeedback"));
          }
        
      }
        reqBody.set(studentSoftSkill);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
   try{ 
        dbg("inside stident Soft skill create"); 
        RequestBody<StudentSoftSkill> reqBody = request.getReqBody();
        StudentSoftSkill studentSoftSkill =reqBody.get();
        IPDataService pds=inject.getPdataservice();
        BusinessService bs=inject.getBusinessService(session);
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_userId=request.getReqHeader().getUserID();
        String userType=bs.getUserType(l_userId, session, dbSession, inject);
        String l_studentID=studentSoftSkill.getStudentID();
        String l_exam=studentSoftSkill.getExam();
        String[] studentPkey={l_studentID}; 
        ArrayList<String>l_studentList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",studentPkey);
        String l_standard=l_studentList.get(2).trim();
        String l_section=l_studentList.get(3).trim();
        
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();   
//        
//        
//        String l_total=studentSoftSkill.getTotal();
//        String l_rank=studentSoftSkill.getRank();
//        
//        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",45,l_studentID,l_exam,l_total,l_rank,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
//
//      
//         for(int i=0;i<studentSoftSkill.mark.length;i++){
//             String l_subjectID=studentSoftSkill.mark[i].getSubjectID();
//             String l_grade=studentSoftSkill.mark[i].getGrade();
//             String l_mark=studentSoftSkill.mark[i].getMark();
//             String l_teacherFeedback=studentSoftSkill.mark[i].getTeacherFeedback();
//             
//             dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",2,l_studentID,l_exam,l_subjectID,l_grade,l_mark,l_teacherFeedback,l_versionNumber);
//  
//         }

                 
                 String signatureStatus="Y";
                 
                 dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS",346,l_studentID,signatureStatus,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
                 
 

        
        
        dbg("end of student progressCard create"); 
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
      
        
       }
    public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
      
    }
    
    

    
    public void delete()throws DBValidationException,DBProcessingException,BSProcessingException{
        
        
       }

   public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student progressCard--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IPDataService pds=inject.getPdataservice();
        RequestBody<StudentSoftSkill> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        StudentSoftSkill studentSoftSkill =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_studentID=studentSoftSkill.getStudentID();
        String l_exam=studentSoftSkill.getExam();
        String[] l_pkey={l_studentID,l_exam};
        DBRecord rankRecord=null;
        Map<String,DBRecord>markMap=null;
        List<DBRecord>markRecords=null;
        String standard=null;
        String section=null;
        
        try{
           
           String[] studentPkey={l_studentID}; 
           ArrayList<String>l_studentList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",studentPkey);
           standard=l_studentList.get(2).trim();
           section=l_studentList.get(3).trim();
            
            markMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS","STUDENT_SKILLS", session, dbSession);
     
            markRecords=markMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals(l_studentID)).collect(Collectors.toList());            
            
            if(markRecords==null||markRecords.isEmpty()){
                
                session.getErrorhandler().log_app_error("BS_VAL_013",l_studentID+l_exam);
                throw new BSValidationException("BSValidationException");
            }
            
        }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013",l_studentID+l_exam);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
        
        buildBOfromDB(markRecords,standard,section);
        
          dbg("end of  completed student progressCard--->view");           
          
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
      private void buildBOfromDB( List<DBRecord>skillRecords,String standard,String section)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           String instituteID=request.getReqHeader().getInstituteID();
           RequestBody<StudentSoftSkill> reqBody = request.getReqBody();
           BusinessService bs=inject.getBusinessService(session);
           StudentSoftSkill studentSoftSkill =reqBody.get();
           String l_exam=studentSoftSkill.getExam();
           String l_studentID=studentSoftSkill.getStudentID();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IPDataService pds=inject.getPdataservice();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           String l_instituteID=request.getReqHeader().getInstituteID();
           String studentName=bs.getStudentName(l_studentID, l_instituteID, session, dbSession, inject);
           studentSoftSkill.setStudentName(studentName);
               
            String[] l_pkey={l_studentID};
            boolean recordExistence=false;
            DBRecord signRecord=null;
            try{
                
                
                 signRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam, "CLASS", "STUDENT_SOFT_SKILL_SIGNATURE", l_pkey, session, dbSession);
                
                recordExistence=true;
            }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                    }else{
                        
                        throw ex;
                    }
            }
            
            dbg("record existence"+recordExistence);
            if(recordExistence){
                
                dbg("maker id"+signRecord.getRecord().get(2).trim());
                
                
                request.getReqAudit().setMakerID(signRecord.getRecord().get(2).trim());
                request.getReqAudit().setCheckerID(signRecord.getRecord().get(3).trim());
                request.getReqAudit().setMakerDateStamp(signRecord.getRecord().get(4).trim());
                request.getReqAudit().setCheckerDateStamp(signRecord.getRecord().get(5).trim());
                request.getReqAudit().setRecordStatus(signRecord.getRecord().get(6).trim());
                request.getReqAudit().setAuthStatus(signRecord.getRecord().get(7).trim());
                request.getReqAudit().setVersionNumber(signRecord.getRecord().get(8).trim());
                request.getReqAudit().setMakerRemarks(signRecord.getRecord().get(9).trim());
                request.getReqAudit().setCheckerRemarks(signRecord.getRecord().get(10).trim());
                
                
                
            }else{
            
                request.getReqAudit().setMakerID("System");
                request.getReqAudit().setCheckerID("System");
                request.getReqAudit().setMakerDateStamp("");
                request.getReqAudit().setCheckerDateStamp("");
                request.getReqAudit().setRecordStatus("O");
                request.getReqAudit().setAuthStatus("A");
                request.getReqAudit().setVersionNumber("1");
                request.getReqAudit().setMakerRemarks("");
                request.getReqAudit().setCheckerRemarks("");
                
            }
           
           
           
            studentSoftSkill.skill=new Skills[skillRecords.size()];
            int i=0;
                for(DBRecord l_skillRecords:skillRecords){
                   studentSoftSkill.skill[i]=new Skills();
                   studentSoftSkill.skill[i].setSkillID(l_skillRecords.getRecord().get(3).trim());
                   String subjectName=this.getSkillName(l_skillRecords.getRecord().get(3).trim());
                   studentSoftSkill.skill[i].setSkillName(subjectName);
                   String categoryDescription=getCategoryDescription(l_skillRecords.getRecord().get(5).trim());
                   
                   studentSoftSkill.skill[i].setCategory(categoryDescription);
                   studentSoftSkill.skill[i].setTeacherFeedback(l_skillRecords.getRecord().get(6).trim());
                   i++;
                }
         
            
          dbg("end of  buildBOfromDB"); 
        
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
       private String getSkillName(String skillID)throws BSProcessingException{
        
        try{
            
            dbg("inside getSkillName");
            String skillName=new String();
            
            switch(skillID){
                
                case "1":
                    
                    skillName="Communication";
                    break;
                case "2":
                    
                    skillName="Leadership";
                    break;
                case "3":  
                    
                    skillName="Food Habit";
                    break;
                case "4":
                    
                    skillName="Being Social";
                    break;
                case "5":
                    
                    skillName="Team Work";
                    break;
                case "6":
                    
                    skillName="Discipline";
                    break;
                case "7":
                    
                    skillName="Dressing";
                break;
            
            
            }
            
            
            
            
            dbg("end of getSkillName-->skillName-->"+skillName);
            return skillName;
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
    }
       
      private String getCategoryDescription(String category)throws BSProcessingException{
        
        try{
            
            dbg("inside getCategoryDescription");
            String description=new String();
            
            switch(category){
                
                case "1":
                    
                    description="Outstanding";
                    break;
                case "2":
                    
                    description="Good";
                    break;
                case "3":  
                    
                    description="Improvement Required";
                    break;
                
            
            
            }
            
            
            
            
            dbg("end of getCategoryDescription-->description-->"+description);
            return description;
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
    } 
       
    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside student softSkill buildJsonResFromBO");
        RequestBody<StudentSoftSkill> reqBody = request.getReqBody();
        StudentSoftSkill studentSoftSkill =reqBody.get();
        JsonArrayBuilder skill=Json.createArrayBuilder();
        
        for(int i=0;i<studentSoftSkill.skill.length;i++){
            
            skill.add(Json.createObjectBuilder().add("skillID", studentSoftSkill.skill[i].getSkillID())
                                                .add("skillName", studentSoftSkill.skill[i].getSkillName())
                                               .add("category", studentSoftSkill.skill[i].getCategory())
                                               .add("teacherFeedback", studentSoftSkill.skill[i].getTeacherFeedback()));
            }
            
       
        
        
            body=Json.createObjectBuilder().add("studentID",  studentSoftSkill.getStudentID())
                                           .add("studentName",  studentSoftSkill.getStudentName())
                                           .add("exam", studentSoftSkill.getExam())
                                           .add("skills", skill)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of student softSkill buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student softSkill--->businessValidation");    
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
       dbg("end of student softSkill--->businessValidation"); 
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
        dbg("inside student softSkill master mandatory validation");
        RequestBody<StudentSoftSkill> reqBody = request.getReqBody();
        StudentSoftSkill studentSoftSkill =reqBody.get();
        
         if(studentSoftSkill.getStudentID()==null||studentSoftSkill.getStudentID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","Student ID");  
         }
          if(studentSoftSkill.getExam()==null||studentSoftSkill.getExam().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","Exam");  
         }
         
          
        dbg("end of student softSkill master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student softSkill masterDataValidation");
             RequestBody<StudentSoftSkill> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             StudentSoftSkill studentSoftSkill =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_studentID=studentSoftSkill.getStudentID();
             String l_exam=studentSoftSkill.getExam();
           
             
             if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Student ID");
             }
             if(!bsv.examValidation(l_exam, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","exam");
             }
            
             
             
          
             
            dbg("end of student softSkill masterDataValidation");
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
        RequestBody<StudentSoftSkill> reqBody = request.getReqBody();
        StudentSoftSkill studentSoftSkill =reqBody.get();
        
        try{
            
            dbg("inside student softSkill detailMandatoryValidation");
           
//            if(studentSoftSkill.getMark()==null||studentSoftSkill.getMark().length==0){
//                status=false;
//                errhandler.log_app_error("BS_VAL_002","skills");
//            }else{
//               
//                for(int i=0;i<studentSoftSkill.getMark().length;i++){
//                
//                    if(studentSoftSkill.getMark()[i].getSkillID()==null||studentSoftSkill.getMark()[i].getSkillID().isEmpty()){
//                       status=false;  
//                       errhandler.log_app_error("BS_VAL_002","Subject ID");  
//                    }
//                    if(studentSoftSkill.getMark()[i].getMark()==null||studentSoftSkill.getMark()[i].getMark().isEmpty()){
//                       status=false;  
//                       errhandler.log_app_error("BS_VAL_002","Mark");  
//                    }
//                    if(studentSoftSkill.getMark()[i].getCategory()==null||studentSoftSkill.getMark()[i].getCategory().isEmpty()){
//                       status=false;  
//                       errhandler.log_app_error("BS_VAL_002","Category");  
//                    }
//                    if(studentSoftSkill.getMark()[i].getTeacherFeedback()==null||studentSoftSkill.getMark()[i].getTeacherFeedback().isEmpty()){
//                       status=false;  
//                       errhandler.log_app_error("BS_VAL_002","Teacher FeedBack");  
//                    }
//                
//                }
//                
//            }
            
            
            
           dbg("end of student softSkill detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student softSkill detailDataValidation");
             RequestBody<StudentSoftSkill> reqBody = request.getReqBody();
             StudentSoftSkill studentSoftSkill =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             BSValidation bsv=inject.getBsv(session);
             String l_exam=studentSoftSkill.getExam();
             String l_studentID=studentSoftSkill.getStudentID();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IPDataService pds=inject.getPdataservice();
             String[] l_pkey={l_studentID};
             ArrayList<String>l_studentList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",l_pkey);
             String l_standard=l_studentList.get(2).trim();
             String l_section=l_studentList.get(3).trim();
             IDBReadBufferService readBuffer=inject.getDBReadBufferService();
             boolean signatureStatus=false;
             
             
             try{
             
             DBRecord studentRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS", "STUDENT_SOFT_SKILL_SIGNATURE", l_pkey, session, dbSession);
             String previousStatus=studentRec.getRecord().get(1).trim();
             
             if(previousStatus.equals("Y")){
                 
                 signatureStatus=true;
             }
             
             }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        signatureStatus=false;
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
//                        session.getErrorhandler().log_app_error("BS_VAL_013",l_studentID+l_exam);
//                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
             
             if(signatureStatus){
                 
                 session.getErrorhandler().log_app_error("BS_VAL_062",l_studentID+l_exam);
                        throw new BSValidationException("BSValidationException");
                 
                 
             }
             
//             for(int i=0;i<studentSoftSkill.getMark().length;i++){
//             
//                 String l_skillID=studentSoftSkill.getMark()[i].getSkillID();
//                 String l_category=studentSoftSkill.getMark()[i].getCategory();
//                 String l_skill=studentSoftSkill.getMark()[i].getMark();
//
//                 if(!bsv.skillIDValidation(l_skillID,l_instituteID, session, dbSession, inject)){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_003","SkillID");
//                 }
//                 if(!bsv.categoryValidation(l_skillID,l_category,l_instituteID, session, dbSession, inject)){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_003","category");
//                 }
//                 if(!bsv.skillValidation(l_skill,l_instituteID, session, dbSession, inject)){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_003","skill");
//                 }
//             }
            dbg("end of student softSkill detailDataValidation");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch(DBValidationException ex){
            throw ex;
       } catch(BSValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        
        return status;
              
    }
    public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     try{
         exAudit=new ExistingAudit();
        dbg("inside StudentSoftSkill--->getExistingAudit") ;
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IPDataService pds=inject.getPdataservice();
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
               dbg("inside StudentSoftSkill--->getExistingAudit--->Service--->UserSoftSkill");  
               RequestBody<StudentSoftSkill> studentSoftSkillBody = request.getReqBody();
               StudentSoftSkill studentSoftSkill =studentSoftSkillBody.get();
               String l_studentID=studentSoftSkill.getStudentID();
               String l_exam=studentSoftSkill.getExam();
               String[] l_pkey={l_studentID};
               ArrayList<String>l_studentList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",l_pkey);
               String standard=l_studentList.get(2).trim();
               String section=l_studentList.get(3).trim();
               
//               DBRecord l_studentSoftSkillRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_PRORESS_CARD", l_pkey, session, dbSession);
              boolean recordExistence=false;

           DBRecord signRecord=null;
            try{
                
                
                signRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam, "CLASS", "STUDENT_SOFT_SKILL_SIGNATURE", l_pkey, session, dbSession);
                
                recordExistence=true;
            }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                    }else{
                        
                        throw ex;
                    }
            }
               
            if(recordExistence){   
               
               exAudit.setAuthStatus(signRecord.getRecord().get(7).trim());
               exAudit.setMakerID(signRecord.getRecord().get(2).trim());
               exAudit.setRecordStatus(signRecord.getRecord().get(6).trim());
               exAudit.setVersionNumber(Integer.parseInt(signRecord.getRecord().get(8).trim()));
            }else{
                
               exAudit.setAuthStatus("A");
               exAudit.setMakerID("System");
               exAudit.setRecordStatus("O");
               exAudit.setVersionNumber(1);
                
            }
 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of StudentSoftSkill--->getExistingAudit") ;
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
         
         return;
         
     }
    
    
    
}
