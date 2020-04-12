/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.classSoftSkill;

import com.ibd.businessViews.IClassSoftSkillService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.classentity.classmark.Marks;
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


@Remote(IClassSoftSkillService.class)
@Stateless

public class ClassSoftSkillService implements IClassSoftSkillService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public ClassSoftSkillService(){
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
       dbg("inside ClassSoftSkillService--->processing");
       dbg("ClassSoftSkillService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<ClassSoftSkill> reqBody = request.getReqBody();
       ClassSoftSkill classSoftSkill =reqBody.get();
       businessLock=inject.getBusinessLockService();
       String l_operation=request.getReqHeader().getOperation();
       if(!l_operation.equals("Create-Default")){
       
       String l_standard=classSoftSkill.getStandard();
       String l_section=classSoftSkill.getSection();
       String l_exam=classSoftSkill.getExam();
       String l_skillID=classSoftSkill.getSkillID();
       l_lockKey=l_standard+"~"+l_section+"~"+l_exam+"~"+l_skillID;
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       
       }
       BusinessEJB<IClassSoftSkillService>classSoftSkillEJB=new BusinessEJB();
       classSoftSkillEJB.set(this);
      
       exAudit=bs.getExistingAudit(classSoftSkillEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        
        if(!l_operation.equals("Create-Default")){
        
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
        
        }
      
       bs.businessServiceProcssing(request, exAudit, inject,classSoftSkillEJB);
       

       if(l_operation.equals("Create-Default")){
           
           createDefault();
       }
       
       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,classSoftSkillEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in class SoftSkill");
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
//BSProcessingException ex=new BSProcessingException("response contains special characters");
  //                 dbg(ex);
    //               session.clearSessionObject();
      //             dbSession.clearSessionObject();
        //           throw ex;
          //      }
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

    private  void buildBOfromReq(JsonObject p_request)throws BSProcessingException,DBProcessingException,BSValidationException{
      ClassSoftSkill classSoftSkill=new ClassSoftSkill();
      RequestBody<ClassSoftSkill> reqBody = new RequestBody<ClassSoftSkill>(); 
           
      try{
      dbg("inside class SoftSkill buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      BSValidation bsv=inject.getBsv(session);
      String l_operation=request.getReqHeader().getOperation();
//      classSoftSkill.setStandard(l_body.getString("standard"));
//      classSoftSkill.setSection(l_body.getString("section"));
      String l_class=l_body.getString("class");
      bsv.classValidation(l_class,session);

      classSoftSkill.setStandard(l_class.split("/")[0]);
      classSoftSkill.setSection(l_class.split("/")[1]);
      classSoftSkill.setExam(l_body.getString("exam"));
      classSoftSkill.setSkillID(l_body.getString("skillID"));
      if(!l_operation.equals("View")&&!l_operation.equals("Create-Default")){
          JsonArray l_marks=l_body.getJsonArray("skills");
          classSoftSkill.skills=new Skills[l_marks.size()];
          for(int i=0;i<l_marks.size();i++){
              classSoftSkill.skills[i]=new Skills();
              JsonObject l_markObject=l_marks.getJsonObject(i);
              classSoftSkill.skills[i].setStudentID(l_markObject.getString("studentID"));
              classSoftSkill.skills[i].setCategory(l_markObject.getString("category"));
              classSoftSkill.skills[i].setTeacherFeedback(l_markObject.getString("teacherFeedback"));
          }
        
      }
        reqBody.set(classSoftSkill);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");
    }catch(BSValidationException ex){
         throw ex;
     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   
    private void createDefault()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside class attendance--->createdefault");    
        BusinessService bs=inject.getBusinessService(session); 
        IPDataService pds=inject.getPdataservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassSoftSkill> reqBody = request.getReqBody();
        ClassSoftSkill classSoftSkill =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_standard=classSoftSkill.getStandard();
        String l_section=classSoftSkill.getSection();
        
        Map<String,ArrayList<String>>studentMasterMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", session, dbSession);
        dbg("studentMasterMap size"+studentMasterMap.size());
        Map<String,List<ArrayList<String>>>l_studentGroup=studentMasterMap.values().stream().filter(rec->rec.get(2).trim().equals(l_standard)&&rec.get(3).trim().equals(l_section)&&rec.get(8).trim().equals("O")).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
        dbg("l_studentGroup size"+l_studentGroup.size());
        Iterator<String>studentIterator=l_studentGroup.keySet().iterator();
        
        
        classSoftSkill.skills=new Skills[l_studentGroup.size()];
        
         int i=0;           
        while(studentIterator.hasNext()){
            
            classSoftSkill.skills[i]=new Skills();
            String studentID=studentIterator.next();
            classSoftSkill.skills[i].setStudentID(studentID);
            String studentName=bs.getStudentName(studentID, l_instituteID, session, dbSession, inject);
            classSoftSkill.skills[i].setStudentName(studentName);
            classSoftSkill.skills[i].setCategory("");
            classSoftSkill.skills[i].setTeacherFeedback("");
          i++;  
        }
        
                    
          dbg("end of  completed teacher attendance--->view");   
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
        dbg("inside class create"); 
        RequestBody<ClassSoftSkill> reqBody = request.getReqBody();
        ClassSoftSkill classSoftSkill =reqBody.get();
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();   
        String l_standard=classSoftSkill.getStandard();
        String l_section=classSoftSkill.getSection();  
        String l_exam=classSoftSkill.getExam();
        String l_skillID=classSoftSkill.getSkillID();
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamMarkMaster","CLASS",328,l_standard,l_section,l_exam,l_skillID,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);

      
         for(int i=0;i<classSoftSkill.skills.length;i++){
             String l_studentID=classSoftSkill.skills[i].getStudentID();
             String l_category=classSoftSkill.skills[i].getCategory();
             String l_teacherFeedback=classSoftSkill.skills[i].getTeacherFeedback();
             
             dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS",329,l_standard,l_section,l_exam,l_skillID,l_studentID,l_category,l_teacherFeedback,l_versionNumber);
  
         }

        
        
        dbg("end of class SoftSkill create"); 
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
        dbg("inside class SoftSkill--->auth update"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<ClassSoftSkill> reqBody = request.getReqBody();
        ClassSoftSkill classSoftSkill =reqBody.get();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_standard=classSoftSkill.getStandard();
        String l_section=classSoftSkill.getSection();  
        String l_exam=classSoftSkill.getExam();
        String l_skillID=classSoftSkill.getSkillID();
        String[] l_primaryKey={l_standard,l_section,l_exam,l_skillID};
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("6", l_checkerID);
         l_column_to_update.put("8", l_checkerDateStamp);
         l_column_to_update.put("10", l_authStatus);
         l_column_to_update.put("13", l_checkerRemarks);
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamMarkMaster","CLASS","CLASS_SKILL_ENTRY",l_primaryKey,l_column_to_update,session);
         dbg("end of class SoftSkill--->auth update");          
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
        
       Map<String,String> l_column_to_update;
                
       try{ 
        dbg("inside class SoftSkill--->full update");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassSoftSkill> reqBody = request.getReqBody();
        ClassSoftSkill classSoftSkill =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();   
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();   
        String l_standard=classSoftSkill.getStandard();
        String l_section=classSoftSkill.getSection();  
        String l_exam=classSoftSkill.getExam();
        String l_skillID=classSoftSkill.getSkillID();
        
                        l_column_to_update= new HashMap();
                        l_column_to_update.put("1", l_standard);
                        l_column_to_update.put("2", l_section);
                        l_column_to_update.put("3", l_exam);
                        l_column_to_update.put("4", l_skillID);
                        l_column_to_update.put("5", l_makerID);
                        l_column_to_update.put("6", l_checkerID);
                        l_column_to_update.put("7", l_makerDateStamp);
                        l_column_to_update.put("8", l_checkerDateStamp);
                        l_column_to_update.put("9", l_recordStatus);
                        l_column_to_update.put("10", l_authStatus);
                        l_column_to_update.put("11", l_versionNumber);
                        l_column_to_update.put("12", l_makerRemarks);
                        l_column_to_update.put("13", l_checkerRemarks);
                        String[] l_primaryKey={l_standard,l_section,l_exam,l_skillID};
                        
                       dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamMarkMaster","CLASS","CLASS_SKILL_ENTRY",l_primaryKey,l_column_to_update,session);

        IDBReadBufferService readBuffer = inject.getDBReadBufferService();
        Map<String,DBRecord>l_studentSoftSkillMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS","STUDENT_SKILLS", session, dbSession);
        setOperationsOfTheRecord("STUDENT_SKILLS",l_studentSoftSkillMap);                    
                       
      
         for(int i=0;i<classSoftSkill.skills.length;i++){
             String l_studentID=classSoftSkill.skills[i].getStudentID();
             String l_category=classSoftSkill.skills[i].getCategory();
             String l_teacherFeedback=classSoftSkill.skills[i].getTeacherFeedback();
             
             if(classSoftSkill.skills[i].getOperation().equals("U")){
             
                            l_column_to_update= new HashMap();
                            l_column_to_update.put("1", l_standard);
                            l_column_to_update.put("2", l_section);
                            l_column_to_update.put("3", l_exam);
                            l_column_to_update.put("4", l_skillID);
                            l_column_to_update.put("5", l_studentID);
                            l_column_to_update.put("6", l_category);
                            l_column_to_update.put("7", l_teacherFeedback);
                            l_column_to_update.put("8", l_versionNumber);

                            String[] l_markPKey={l_standard,l_section,l_exam,l_skillID,l_studentID};

                           dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS","STUDENT_SKILLS",l_markPKey,l_column_to_update,session);
             }else{
                 
                 
                  dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS",329,l_standard,l_section,l_exam,l_skillID,l_studentID,l_category,l_teacherFeedback,l_versionNumber);
                 
             }
         }
         
          ArrayList<String>cpList=getRecordsToDelete("STUDENT_SKILLS",l_studentSoftSkillMap);
         
          for(int i=0;i<cpList.size();i++){
            String pkey=cpList.get(i);
            deleteRecordsInTheList("STUDENT_SKILLS",pkey);
          }
         
        
         dbg("end of class SoftSkill--->full update");
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

    private void setOperationsOfTheRecord(String tableName,Map<String,DBRecord>tableMap)throws DBValidationException,DBProcessingException,BSProcessingException{
         
         try{
            dbg("inside getOperationsOfTheRecord"); 
            RequestBody<ClassSoftSkill> reqBody = request.getReqBody();
            ClassSoftSkill classSoftSkill =reqBody.get();
            String l_standard=classSoftSkill.getStandard();
            String l_section=classSoftSkill.getSection();  
            String l_exam=classSoftSkill.getExam();
            String skillID=classSoftSkill.getSkillID();
            dbg("tableName"+tableName);
            
            switch(tableName){
                
                case "STUDENT_SKILLS":  
                
                         for(int i=0;i<classSoftSkill.skills.length;i++){
                                String l_studentID=classSoftSkill.skills[i].getStudentID();
                                String l_pkey=l_standard+"~"+l_section+"~"+l_exam+"~"+skillID+"~"+l_studentID;
                               if(tableMap.containsKey(l_pkey)){

                                    classSoftSkill.skills[i].setOperation("U");
                                }else{

                                    classSoftSkill.skills[i].setOperation("C");
                                }
                         } 
                  break;      

            }
                  
           dbg("end of getOperationsOfTheRecord"); 

        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
         
     }    

private ArrayList<String>getRecordsToDelete(String tableName,Map<String,DBRecord>tableMap)throws DBValidationException,DBProcessingException,BSProcessingException{
         
         try{
           
           dbg("inside getRecordsToDelete");  
           RequestBody<ClassSoftSkill> reqBody = request.getReqBody();
           ClassSoftSkill classSoftSkill =reqBody.get();
           String l_standard=classSoftSkill.getStandard();
           String l_section=classSoftSkill.getSection();  
           String l_exam=classSoftSkill.getExam();
           String skillID=classSoftSkill.getSkillID();
           ArrayList<String>recordsToDelete=new ArrayList();
//           Iterator<String>keyIterator=tableMap.keySet().iterator();
        
           List<DBRecord>filteredRecords=tableMap.values().stream().filter(rec->rec.getRecord().get(0).trim().equals(l_standard)&&rec.getRecord().get(1).trim().equals(l_section)&&rec.getRecord().get(2).trim().equals(l_exam)&&rec.getRecord().get(3).trim().equals(skillID)).collect(Collectors.toList());
           
           
           
           dbg("tableName"+tableName);
           switch(tableName){
           
                 case "STUDENT_SKILLS":   
                   
                     for(int j=0;j<filteredRecords.size();j++){
                        String studentID= filteredRecords.get(j).getRecord().get(4).trim();
                        String tablePkey=l_standard+"~"+l_section+"~"+l_exam+"~"+skillID+"~"+studentID;
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                       for(int i=0;i<classSoftSkill.skills.length;i++){
                           String l_studentID=classSoftSkill.skills[i].getStudentID();
                           String l_requestPkey=l_standard+"~"+l_section+"~"+l_exam+"~"+skillID+"~"+l_studentID;
                           dbg("l_requestPkey"+l_requestPkey);
                           if(tablePkey.equals(l_requestPkey)){
                               recordExistence=true;
                           }
                        }   
                        if(!recordExistence){
                            recordsToDelete.add(tablePkey);
                        }

                    }
                   break;
                   
                   
               
                    }
             
           dbg("records to delete"+recordsToDelete.size());
           dbg("end of getRecordsToDelete");
           return recordsToDelete;

        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
     }

private void deleteRecordsInTheList(String tableName,String pkey)throws DBValidationException,DBProcessingException,BSProcessingException{
         
         try{
             RequestBody<ClassSoftSkill> reqBody = request.getReqBody();
             ClassSoftSkill classSoftSkill =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_standard=classSoftSkill.getStandard();
             String l_section=classSoftSkill.getSection();  
             String l_exam=classSoftSkill.getExam();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IDBTransactionService dbts=inject.getDBTransactionService();
             String[] pkArr=pkey.split("~");
             
             
             dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS",tableName, pkArr, session);
             
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
        dbg("inside class SoftSkill delete");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassSoftSkill> reqBody = request.getReqBody();
        ClassSoftSkill classSoftSkill =reqBody.get();
        String l_standard=classSoftSkill.getStandard();
        String l_section=classSoftSkill.getSection();  
        String l_exam=classSoftSkill.getExam();
        String l_skillID=classSoftSkill.getSkillID();
        
        String[] l_primaryKey={l_standard,l_section,l_exam,l_skillID};

        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamMarkMaster","CLASS","CLASS_SKILL_ENTRY",l_primaryKey,session);

      
         for(int i=0;i<classSoftSkill.skills.length;i++){
             String l_studentID=classSoftSkill.skills[i].getStudentID();
              String[] l_markPKey={l_standard,l_section,l_exam,l_skillID,l_studentID};
                        
                    dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS","STUDENT_SKILLS",l_markPKey,session);
          }
                         
            dbg("end of class SoftSkill delete");
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
        dbg("inside class SoftSkill--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassSoftSkill> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        ClassSoftSkill classSoftSkill =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_standard=classSoftSkill.getStandard();
        String l_section=classSoftSkill.getSection();  
        String l_exam=classSoftSkill.getExam();
        String l_skillID=classSoftSkill.getSkillID();
        String[] l_primaryKey={l_standard,l_section,l_exam,l_skillID};
        DBRecord classSoftSkillRec=null;
        Map<String,DBRecord>markMap=null;
        
        try{
        
                classSoftSkillRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamMarkMaster","CLASS","CLASS_SKILL_ENTRY", l_primaryKey, session, dbSession);
                markMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS","STUDENT_SKILLS", session, dbSession);
    
         }
         catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013",l_standard+l_section+l_exam);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
        
        
        buildBOfromDB(classSoftSkillRec,markMap);
        
          dbg("end of  completed class SoftSkill--->view");       
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
      private void buildBOfromDB(DBRecord p_classSoftSkillRecord,Map<String,DBRecord>p_markMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<ClassSoftSkill> reqBody = request.getReqBody();
           IPDataService pds=inject.getPdataservice();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           String instituteID=request.getReqHeader().getInstituteID();
           ClassSoftSkill classSoftSkill =reqBody.get();
           String l_exam=classSoftSkill.getExam();
           String l_skillID=classSoftSkill.getSkillID();
           BusinessService bs=inject.getBusinessService(session);
           ArrayList<String>l_classSoftSkillList= p_classSoftSkillRecord.getRecord();
           
           
           
           if(l_classSoftSkillList!=null&&!l_classSoftSkillList.isEmpty()){
               
            request.getReqAudit().setMakerID(l_classSoftSkillList.get(4).trim());
            request.getReqAudit().setCheckerID(l_classSoftSkillList.get(5).trim());
            request.getReqAudit().setMakerDateStamp(l_classSoftSkillList.get(6).trim());
            request.getReqAudit().setCheckerDateStamp(l_classSoftSkillList.get(7).trim());
            request.getReqAudit().setRecordStatus(l_classSoftSkillList.get(8).trim());
            request.getReqAudit().setAuthStatus(l_classSoftSkillList.get(9).trim());
            request.getReqAudit().setVersionNumber(l_classSoftSkillList.get(10).trim());
            request.getReqAudit().setMakerRemarks(l_classSoftSkillList.get(11).trim());
            request.getReqAudit().setCheckerRemarks(l_classSoftSkillList.get(12).trim());
            }
            Map<String,List<DBRecord>> examWiseGroup=p_markMap.values().stream().filter(rec->rec.getRecord().get(2).trim().equals(l_exam)&&rec.getRecord().get(3).trim().equals(l_skillID)&&rec.getRecord().get(7).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.groupingBy(rec->rec.getRecord().get(2).trim()+rec.getRecord().get(3).trim()));
            classSoftSkill.skills=new Skills[examWiseGroup.get(l_exam+l_skillID).size()];
            int i=0;
                for(DBRecord l_markRecords: examWiseGroup.get(l_exam+l_skillID)){
                   classSoftSkill.skills[i]=new Skills();
                   classSoftSkill.skills[i].setStudentID(l_markRecords.getRecord().get(4).trim());
                   String studentName=bs.getStudentName(l_markRecords.getRecord().get(4).trim(), instituteID, session, dbSession, inject);
                   classSoftSkill.skills[i].setStudentName(studentName);
                   classSoftSkill.skills[i].setCategory(l_markRecords.getRecord().get(5).trim());
                   classSoftSkill.skills[i].setTeacherFeedback(l_markRecords.getRecord().get(6).trim());
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
    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside class SoftSkill buildJsonResFromBO");
        RequestBody<ClassSoftSkill> reqBody = request.getReqBody();
        ClassSoftSkill classSoftSkill =reqBody.get();
        JsonArrayBuilder mark=Json.createArrayBuilder();
        JsonArrayBuilder categoryDescription=Json.createArrayBuilder();
        
        for(int i=0;i<classSoftSkill.skills.length;i++){
            
            mark.add(Json.createObjectBuilder().add("studentID", classSoftSkill.skills[i].getStudentID())
                                               .add("studentName", classSoftSkill.skills[i].getStudentName())
                                               .add("category", classSoftSkill.skills[i].getCategory())
                                               .add("teacherFeedback", classSoftSkill.skills[i].getTeacherFeedback()));
            }
            

//            body=Json.createObjectBuilder().add("standard",  classSoftSkill.getStandard())
//                                           .add("section",  classSoftSkill.getSection())
//                                           .add("exam", classSoftSkill.getExam())
//                                           .add("skillID",  classSoftSkill.getSkillID())
//                                           .add("skillName",  classSoftSkill.getSkillName())
//                                           .add("marks", mark)
//                                           .build();
                                            
              body=Json.createObjectBuilder().add("class",   classSoftSkill.getStandard()+"/"+classSoftSkill.getSection())
                                           .add("exam", classSoftSkill.getExam())
                                           .add("skillID",  classSoftSkill.getSkillID())
                                           .add("skills", mark)
//                                           .add("CategoryDescription", categoryDescription)
                                           .build();
              dbg(body.toString());  
           dbg("end of class SoftSkill buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside class SoftSkill--->businessValidation");    
       String l_operation=request.getReqHeader().getOperation();
       
       if(!masterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!masterDataValidation(errhandler)){
             status=false;
            }
       }
       
       if(!(l_operation.equals("View"))&&!l_operation.equals("Create-Default")&&!l_operation.equals("Delete")){
           
           if(!detailMandatoryValidation(errhandler)) {
               
               status=false;
           } else{
           
           if(!detailDataValidation(errhandler)){
               
               status=false;
           }
            
           
           }
       
       }
       dbg("end of class SoftSkill--->businessValidation"); 
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
        dbg("inside class SoftSkill master mandatory validation");
        RequestBody<ClassSoftSkill> reqBody = request.getReqBody();
        ClassSoftSkill classSoftSkill =reqBody.get();
        

          if(classSoftSkill.getExam()==null||classSoftSkill.getExam().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","Exam");  
         }
         if(classSoftSkill.getSkillID()==null||classSoftSkill.getSkillID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","SkillID");  
         }
          
        dbg("end of class SoftSkill master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside class SoftSkill masterDataValidation");
             RequestBody<ClassSoftSkill> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             ClassSoftSkill classSoftSkill =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_standard=classSoftSkill.getStandard();
             String l_section=classSoftSkill.getSection();
             String l_exam=classSoftSkill.getExam();
             String l_skillID=classSoftSkill.getSkillID();
             IDBReadBufferService readBuffer=inject.getDBReadBufferService();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             
             if(!bsv.standardSectionValidation(l_standard,l_section, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Standard or Section");
             }
             if(!bsv.examValidation(l_exam, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Exam");
             }
//             if(!bsv.skillIDValidation(l_skillID, l_instituteID, session, dbSession, inject)){
//                 status=false;
//                 errhandler.log_app_error("BS_VAL_003","SkillID");
//             }
              if(!(l_skillID.equals("1")||l_skillID.equals("2")||l_skillID.equals("3")||l_skillID.equals("4")||l_skillID.equals("5")||l_skillID.equals("6")||l_skillID.equals("7"))){
                     
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","Skill");
                     
                 }
             
            

             
             
            dbg("end of class SoftSkill masterDataValidation");
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
        RequestBody<ClassSoftSkill> reqBody = request.getReqBody();
        ClassSoftSkill classSoftSkill =reqBody.get();
        
        try{
            
            dbg("inside class SoftSkill detailMandatoryValidation");
           
            if(classSoftSkill.getSkills()==null){
                status=false;
                errhandler.log_app_error("BS_VAL_002","Category");
            }else{
               
                for(int i=0;i<classSoftSkill.getSkills().length;i++){
                
                    if(classSoftSkill.getSkills()[i].getStudentID()==null||classSoftSkill.getSkills()[i].getStudentID().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","StudentID");  
                    }
                    if(classSoftSkill.getSkills()[i].getCategory()==null||classSoftSkill.getSkills()[i].getCategory().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","Category");  
                    }

                
                }
                
            }
            
            
            
           dbg("end of class SoftSkill detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside class SoftSkill detailDataValidation");
             RequestBody<ClassSoftSkill> reqBody = request.getReqBody();
             ClassSoftSkill classSoftSkill =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             BSValidation bsv=inject.getBsv(session);
             ArrayList<String>studentList=new ArrayList();
             
             
             for(int i=0;i<classSoftSkill.getSkills().length;i++){
             
                 String l_studentID=classSoftSkill.getSkills()[i].getStudentID();
                 
                 
                 if(!studentList.contains(l_studentID)){
                     
                     studentList.add(l_studentID);
                     
                 }else{
                     
                     status=false;
                     errhandler.log_app_error("BS_VAL_031","StudenID");
                     throw new BSValidationException("BSValidationException");
                 }
                 
                 
                 
                 String l_category=classSoftSkill.getSkills()[i].getCategory();
                 if(!bsv.studentIDValidation(l_studentID,l_instituteID, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","studentID");
                 }
                
                 
                 if(!(l_category.equals("1")||l_category.equals("2")||l_category.equals("3")||l_category.equals("4")||l_category.equals("5")||l_category.equals("6")||l_category.equals("7"))){
                     
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","Category");
                     
                 }
                 
  
             }
            dbg("end of class SoftSkill detailDataValidation");
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
     try{
         exAudit=new ExistingAudit();
        dbg("inside ClassSoftSkill--->getExistingAudit") ;
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_operation=request.getReqHeader().getOperation();
        String l_service=request.getReqHeader().getService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
        if(!l_operation.equals("Create")&&!l_operation.equals("Create-Default")&&!l_operation.equals("View")){
             
              if(l_operation.equals("AutoAuth")&&l_versionNumber.equals("1")){
                return null;
              }else{  
               dbg("inside ClassSoftSkill--->getExistingAudit--->Service--->UserProgressCard");  
               RequestBody<ClassSoftSkill> classSoftSkillBody = request.getReqBody();
               ClassSoftSkill classSoftSkill =classSoftSkillBody.get();
               String l_standard=classSoftSkill.getStandard();
               String l_section=classSoftSkill.getSection();  
               String l_exam=classSoftSkill.getExam();
               String l_skillID=classSoftSkill.getSkillID();
               String[] l_primaryKey={l_standard,l_section,l_exam,l_skillID};
               DBRecord l_classSoftSkillRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamMarkMaster","CLASS","CLASS_SKILL_ENTRY", l_primaryKey, session, dbSession);
               exAudit.setAuthStatus(l_classSoftSkillRecord.getRecord().get(9).trim());
               exAudit.setMakerID(l_classSoftSkillRecord.getRecord().get(4).trim());
               exAudit.setRecordStatus(l_classSoftSkillRecord.getRecord().get(8).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_classSoftSkillRecord.getRecord().get(10).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of ClassSoftSkill--->getExistingAudit") ;
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
