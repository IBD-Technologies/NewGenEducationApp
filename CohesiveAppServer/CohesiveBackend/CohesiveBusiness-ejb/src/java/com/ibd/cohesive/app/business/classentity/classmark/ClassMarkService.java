/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.classmark;

import com.ibd.businessViews.IClassMarkService;
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
 * @author IBD Technologies
 */
//@Local(IClassMarkService.class)
@Remote(IClassMarkService.class)
@Stateless
public class ClassMarkService implements IClassMarkService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public ClassMarkService(){
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
       dbg("inside ClassMarkService--->processing");
       dbg("ClassMarkService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<ClassMark> reqBody = request.getReqBody();
       ClassMark classMark =reqBody.get();
       businessLock=inject.getBusinessLockService();
       String l_operation=request.getReqHeader().getOperation();
       if(!l_operation.equals("Create-Default")){
       
       String l_standard=classMark.getStandard();
       String l_section=classMark.getSection();
       String l_exam=classMark.getExam();
       String l_subjectID=classMark.getSubjectID();
       l_lockKey=l_standard+"~"+l_section+"~"+l_exam+"~"+l_subjectID;
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       
       }
       BusinessEJB<IClassMarkService>classMarkEJB=new BusinessEJB();
       classMarkEJB.set(this);
      
       exAudit=bs.getExistingAudit(classMarkEJB);
       
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
      
       bs.businessServiceProcssing(request, exAudit, inject,classMarkEJB);
       

       if(l_operation.equals("Create-Default")){
           
           createDefault();
       }
       
       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,classMarkEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in class Mark");
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
      ClassMark classMark=new ClassMark();
      RequestBody<ClassMark> reqBody = new RequestBody<ClassMark>(); 
           
      try{
      dbg("inside class Mark buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      BSValidation bsv=inject.getBsv(session);
      String l_operation=request.getReqHeader().getOperation();
//      classMark.setStandard(l_body.getString("standard"));
//      classMark.setSection(l_body.getString("section"));
      String l_class=l_body.getString("class");
      bsv.classValidation(l_class,session);

      classMark.setStandard(l_class.split("/")[0]);
      classMark.setSection(l_class.split("/")[1]);
      classMark.setExam(l_body.getString("exam"));
      classMark.setSubjectID(l_body.getString("subjectID"));
      classMark.setSubjectName(l_body.getString("subjectName"));
      if(!l_operation.equals("View")&&!l_operation.equals("Create-Default")){
          JsonArray l_marks=l_body.getJsonArray("marks");
          classMark.mark=new Marks[l_marks.size()];
          for(int i=0;i<l_marks.size();i++){
              classMark.mark[i]=new Marks();
              JsonObject l_markObject=l_marks.getJsonObject(i);
              classMark.mark[i].setStudentID(l_markObject.getString("studentID"));
              classMark.mark[i].setGrade(l_markObject.getString("grade"));
              classMark.mark[i].setMark(l_markObject.getString("mark"));
              classMark.mark[i].setTeacherFeedback(l_markObject.getString("teacherFeedback"));
          }
        
      }
        reqBody.set(classMark);
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
        RequestBody<ClassMark> reqBody = request.getReqBody();
        ClassMark classMark =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_standard=classMark.getStandard();
        String l_section=classMark.getSection();
        
        Map<String,ArrayList<String>>studentMasterMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", session, dbSession);
        dbg("studentMasterMap size"+studentMasterMap.size());
        Map<String,List<ArrayList<String>>>l_studentGroup=studentMasterMap.values().stream().filter(rec->rec.get(2).trim().equals(l_standard)&&rec.get(3).trim().equals(l_section)&&rec.get(8).trim().equals("O")).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
        dbg("l_studentGroup size"+l_studentGroup.size());
        Iterator<String>studentIterator=l_studentGroup.keySet().iterator();
        
        
        classMark.mark=new Marks[l_studentGroup.size()];
        
         int i=0;           
        while(studentIterator.hasNext()){
            
            classMark.mark[i]=new Marks();
            String studentID=studentIterator.next();
            classMark.mark[i].setStudentID(studentID);
            String studentName=bs.getStudentName(studentID, l_instituteID, session, dbSession, inject);
            classMark.mark[i].setStudentName(studentName);
            classMark.mark[i].setGrade("");
            classMark.mark[i].setMark("");
            classMark.mark[i].setTeacherFeedback("");
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
        RequestBody<ClassMark> reqBody = request.getReqBody();
        ClassMark classMark =reqBody.get();
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
        String l_standard=classMark.getStandard();
        String l_section=classMark.getSection();  
        String l_exam=classMark.getExam();
        String l_subjectID=classMark.getSubjectID();
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamMarkMaster","CLASS",43,l_standard,l_section,l_exam,l_subjectID,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);

      
         for(int i=0;i<classMark.mark.length;i++){
             String l_studentID=classMark.mark[i].getStudentID();
             String l_grade=classMark.mark[i].getGrade();
             String l_mark=classMark.mark[i].getMark();
             String l_teacherFeedback=classMark.mark[i].getTeacherFeedback();
             
             dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS",46,l_standard,l_section,l_exam,l_subjectID,l_studentID,l_grade,l_mark,l_teacherFeedback,l_versionNumber);
  
         }

        
        
        dbg("end of class Mark create"); 
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
        dbg("inside class Mark--->auth update"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<ClassMark> reqBody = request.getReqBody();
        ClassMark classMark =reqBody.get();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_standard=classMark.getStandard();
        String l_section=classMark.getSection();  
        String l_exam=classMark.getExam();
        String l_subjectID=classMark.getSubjectID();
        String[] l_primaryKey={l_standard,l_section,l_exam,l_subjectID};
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("6", l_checkerID);
         l_column_to_update.put("8", l_checkerDateStamp);
         l_column_to_update.put("10", l_authStatus);
         l_column_to_update.put("13", l_checkerRemarks);
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamMarkMaster","CLASS","CLASS_MARK_ENTRY",l_primaryKey,l_column_to_update,session);
         dbg("end of class Mark--->auth update");          
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
        dbg("inside class Mark--->full update");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassMark> reqBody = request.getReqBody();
        ClassMark classMark =reqBody.get();
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
        String l_standard=classMark.getStandard();
        String l_section=classMark.getSection();  
        String l_exam=classMark.getExam();
        String l_subjectID=classMark.getSubjectID();
        
                        l_column_to_update= new HashMap();
                        l_column_to_update.put("1", l_standard);
                        l_column_to_update.put("2", l_section);
                        l_column_to_update.put("3", l_exam);
                        l_column_to_update.put("4", l_subjectID);
                        l_column_to_update.put("5", l_makerID);
                        l_column_to_update.put("6", l_checkerID);
                        l_column_to_update.put("7", l_makerDateStamp);
                        l_column_to_update.put("8", l_checkerDateStamp);
                        l_column_to_update.put("9", l_recordStatus);
                        l_column_to_update.put("10", l_authStatus);
                        l_column_to_update.put("11", l_versionNumber);
                        l_column_to_update.put("12", l_makerRemarks);
                        l_column_to_update.put("13", l_checkerRemarks);
                        String[] l_primaryKey={l_standard,l_section,l_exam,l_subjectID};
                        
                       dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamMarkMaster","CLASS","CLASS_MARK_ENTRY",l_primaryKey,l_column_to_update,session);

        IDBReadBufferService readBuffer = inject.getDBReadBufferService();
        Map<String,DBRecord>l_studentMarkMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS","STUDENT_MARKS", session, dbSession);
        setOperationsOfTheRecord("STUDENT_MARKS",l_studentMarkMap);                    
                       
      
         for(int i=0;i<classMark.mark.length;i++){
             String l_studentID=classMark.mark[i].getStudentID();
             String l_grade=classMark.mark[i].getGrade();
             String l_mark=classMark.mark[i].getMark();
             String l_teacherFeedback=classMark.mark[i].getTeacherFeedback();
             
             if(classMark.mark[i].getOperation().equals("U")){
             
                            l_column_to_update= new HashMap();
                            l_column_to_update.put("1", l_standard);
                            l_column_to_update.put("2", l_section);
                            l_column_to_update.put("3", l_exam);
                            l_column_to_update.put("4", l_subjectID);
                            l_column_to_update.put("5", l_studentID);
                            l_column_to_update.put("6", l_grade);
                            l_column_to_update.put("7", l_mark);
                            l_column_to_update.put("8", l_teacherFeedback);
                            l_column_to_update.put("9", l_versionNumber);

                            String[] l_markPKey={l_standard,l_section,l_exam,l_subjectID,l_studentID};

                           dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS","STUDENT_MARKS",l_markPKey,l_column_to_update,session);
             }else{
                 
                 
                  dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS",46,l_standard,l_section,l_exam,l_subjectID,l_studentID,l_grade,l_mark,l_teacherFeedback,l_versionNumber);
                 
             }
         }
         
          ArrayList<String>cpList=getRecordsToDelete("STUDENT_MARKS",l_studentMarkMap);
         
          for(int i=0;i<cpList.size();i++){
            String pkey=cpList.get(i);
            deleteRecordsInTheList("STUDENT_MARKS",pkey);
          }
         
        
         dbg("end of class Mark--->full update");
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
            RequestBody<ClassMark> reqBody = request.getReqBody();
            ClassMark classMark =reqBody.get();
            String l_standard=classMark.getStandard();
            String l_section=classMark.getSection();  
            String l_exam=classMark.getExam();
            String subjectID=classMark.getSubjectID();
            dbg("tableName"+tableName);
            
            switch(tableName){
                
                case "STUDENT_MARKS":  
                
                         for(int i=0;i<classMark.mark.length;i++){
                                String l_studentID=classMark.mark[i].getStudentID();
                                String l_pkey=l_standard+"~"+l_section+"~"+l_exam+"~"+subjectID+"~"+l_studentID;
                               if(tableMap.containsKey(l_pkey)){

                                    classMark.mark[i].setOperation("U");
                                }else{

                                    classMark.mark[i].setOperation("C");
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
           RequestBody<ClassMark> reqBody = request.getReqBody();
           ClassMark classMark =reqBody.get();
           String l_standard=classMark.getStandard();
           String l_section=classMark.getSection();  
           String l_exam=classMark.getExam();
           String subjectID=classMark.getSubjectID();
           ArrayList<String>recordsToDelete=new ArrayList();
//           Iterator<String>keyIterator=tableMap.keySet().iterator();
        
           List<DBRecord>filteredRecords=tableMap.values().stream().filter(rec->rec.getRecord().get(0).trim().equals(l_standard)&&rec.getRecord().get(1).trim().equals(l_section)&&rec.getRecord().get(2).trim().equals(l_exam)&&rec.getRecord().get(3).trim().equals(subjectID)).collect(Collectors.toList());
           
           
           
           dbg("tableName"+tableName);
           switch(tableName){
           
                 case "STUDENT_MARKS":   
                   
                     for(int j=0;j<filteredRecords.size();j++){
                        String studentID= filteredRecords.get(j).getRecord().get(4).trim();
                        String tablePkey=l_standard+"~"+l_section+"~"+l_exam+"~"+subjectID+"~"+studentID;
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                       for(int i=0;i<classMark.mark.length;i++){
                           String l_studentID=classMark.mark[i].getStudentID();
                           String l_requestPkey=l_standard+"~"+l_section+"~"+l_exam+"~"+subjectID+"~"+l_studentID;
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
             RequestBody<ClassMark> reqBody = request.getReqBody();
             ClassMark classMark =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_standard=classMark.getStandard();
             String l_section=classMark.getSection();  
             String l_exam=classMark.getExam();
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
        dbg("inside class Mark delete");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassMark> reqBody = request.getReqBody();
        ClassMark classMark =reqBody.get();
        String l_standard=classMark.getStandard();
        String l_section=classMark.getSection();  
        String l_exam=classMark.getExam();
        String l_subjectID=classMark.getSubjectID();
        
        String[] l_primaryKey={l_standard,l_section,l_exam,l_subjectID};

        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamMarkMaster","CLASS","CLASS_MARK_ENTRY",l_primaryKey,session);

      
         for(int i=0;i<classMark.mark.length;i++){
             String l_studentID=classMark.mark[i].getStudentID();
              String[] l_markPKey={l_standard,l_section,l_exam,l_subjectID,l_studentID};
                        
                    dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS","STUDENT_MARKS",l_markPKey,session);
          }
                         
            dbg("end of class Mark delete");
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
        dbg("inside class Mark--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassMark> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        ClassMark classMark =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_standard=classMark.getStandard();
        String l_section=classMark.getSection();  
        String l_exam=classMark.getExam();
        String l_subjectID=classMark.getSubjectID();
        String[] l_primaryKey={l_standard,l_section,l_exam,l_subjectID};
        DBRecord classMarkRec=null;
        Map<String,DBRecord>markMap=null;
        
        try{
        
                classMarkRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamMarkMaster","CLASS","CLASS_MARK_ENTRY", l_primaryKey, session, dbSession);
                markMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS","STUDENT_MARKS", session, dbSession);
    
         }catch(DBValidationException ex){
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
        
        
        buildBOfromDB(classMarkRec,markMap);
        
          dbg("end of  completed class Mark--->view");       
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
      private void buildBOfromDB(DBRecord p_classMarkRecord,Map<String,DBRecord>p_markMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<ClassMark> reqBody = request.getReqBody();
           IPDataService pds=inject.getPdataservice();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           String instituteID=request.getReqHeader().getInstituteID();
           ClassMark classMark =reqBody.get();
           String l_exam=classMark.getExam();
           String l_subjectID=classMark.getSubjectID();
           BusinessService bs=inject.getBusinessService(session);
           ArrayList<String>l_classMarkList= p_classMarkRecord.getRecord();
           
           
           
           if(l_classMarkList!=null&&!l_classMarkList.isEmpty()){
               
            request.getReqAudit().setMakerID(l_classMarkList.get(4).trim());
            request.getReqAudit().setCheckerID(l_classMarkList.get(5).trim());
            request.getReqAudit().setMakerDateStamp(l_classMarkList.get(6).trim());
            request.getReqAudit().setCheckerDateStamp(l_classMarkList.get(7).trim());
            request.getReqAudit().setRecordStatus(l_classMarkList.get(8).trim());
            request.getReqAudit().setAuthStatus(l_classMarkList.get(9).trim());
            request.getReqAudit().setVersionNumber(l_classMarkList.get(10).trim());
            request.getReqAudit().setMakerRemarks(l_classMarkList.get(11).trim());
            request.getReqAudit().setCheckerRemarks(l_classMarkList.get(12).trim());
            }
            Map<String,List<DBRecord>> examWiseGroup=p_markMap.values().stream().filter(rec->rec.getRecord().get(2).trim().equals(l_exam)&&rec.getRecord().get(3).trim().equals(l_subjectID)&&rec.getRecord().get(8).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.groupingBy(rec->rec.getRecord().get(2).trim()+rec.getRecord().get(3).trim()));
            classMark.mark=new Marks[examWiseGroup.get(l_exam+l_subjectID).size()];
            int i=0;
                for(DBRecord l_markRecords: examWiseGroup.get(l_exam+l_subjectID)){
                   classMark.mark[i]=new Marks();
                   classMark.mark[i].setStudentID(l_markRecords.getRecord().get(4).trim());
                   String studentName=bs.getStudentName(l_markRecords.getRecord().get(4).trim(), instituteID, session, dbSession, inject);
                   classMark.mark[i].setStudentName(studentName);
                   classMark.mark[i].setMark(l_markRecords.getRecord().get(6).trim());
                   String mark=classMark.mark[i].getMark();
                   String grade=bs.getGrade(mark, instituteID, session, dbSession, inject);
                   classMark.mark[i].setGrade(grade);
                   classMark.mark[i].setTeacherFeedback(l_markRecords.getRecord().get(7).trim());
                   i++;
                }
           
            Map<String,ArrayList<String>>l_gradeMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE","IVW_SUBJECT_GRADE_MASTER", session, dbSession);
            String masterVersion=bs.getMaxVersionOfTheInstitute(instituteID, session, dbSession, inject);
            int versionIndex=bs.getVersionIndexOfTheTable("IVW_SUBJECT_GRADE_MASTER", "INSTITUTE", session, dbSession, inject);
            
            List<ArrayList<String>>filteredList=l_gradeMap.values().stream().filter(rec->rec.get(versionIndex).equals(masterVersion)).collect(Collectors.toList());
            
            
            Iterator<ArrayList<String>> valueIterator= filteredList.iterator();
            
            classMark.gradeDescription=new GradeDescription[filteredList.size()];
            int j=0;
            while(valueIterator.hasNext()){
                
                ArrayList<String>value=valueIterator.next();
                String grade=value.get(1).trim();
                String from=value.get(2).trim();
                String to=value.get(3).trim();
                classMark.gradeDescription[j]=new GradeDescription();
                classMark.gradeDescription[j].setGrade(grade);
                classMark.gradeDescription[j].setDescription(from+" - "+to);
                
              j++;
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
        dbg("inside class Mark buildJsonResFromBO");
        RequestBody<ClassMark> reqBody = request.getReqBody();
        ClassMark classMark =reqBody.get();
        JsonArrayBuilder mark=Json.createArrayBuilder();
        JsonArrayBuilder gradeDescription=Json.createArrayBuilder();
        
        for(int i=0;i<classMark.mark.length;i++){
            
            mark.add(Json.createObjectBuilder().add("studentID", classMark.mark[i].getStudentID())
                                               .add("studentName", classMark.mark[i].getStudentName())
                                               .add("grade", classMark.mark[i].getGrade())
                                               .add("mark", classMark.mark[i].getMark())
                                               .add("teacherFeedback", classMark.mark[i].getTeacherFeedback()));
            }
            
        if(classMark.gradeDescription!=null){
        
            for(int i=0;i<classMark.gradeDescription.length;i++){
            
                gradeDescription.add(Json.createObjectBuilder().add("grade", classMark.gradeDescription[i].getGrade())
                                                               .add("description", classMark.gradeDescription[i].getDescription()));
            }
        }
//            body=Json.createObjectBuilder().add("standard",  classMark.getStandard())
//                                           .add("section",  classMark.getSection())
//                                           .add("exam", classMark.getExam())
//                                           .add("subjectID",  classMark.getSubjectID())
//                                           .add("subjectName",  classMark.getSubjectName())
//                                           .add("marks", mark)
//                                           .build();
                                            
              body=Json.createObjectBuilder().add("class",   classMark.getStandard()+"/"+classMark.getSection())
                                           .add("exam", classMark.getExam())
                                           .add("subjectID",  classMark.getSubjectID())
                                           .add("subjectName",  classMark.getSubjectName())
                                           .add("marks", mark)
                                           .add("GradeDescription", gradeDescription)
                                           .build();
              dbg(body.toString());  
           dbg("end of class Mark buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside class Mark--->businessValidation");    
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
       dbg("end of class Mark--->businessValidation"); 
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
        dbg("inside class Mark master mandatory validation");
        RequestBody<ClassMark> reqBody = request.getReqBody();
        ClassMark classMark =reqBody.get();
        

          if(classMark.getExam()==null||classMark.getExam().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","Exam");  
         }
         if(classMark.getSubjectID()==null||classMark.getSubjectID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","SubjectID");  
         }
          
        dbg("end of class Mark master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside class Mark masterDataValidation");
             RequestBody<ClassMark> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             ClassMark classMark =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_standard=classMark.getStandard();
             String l_section=classMark.getSection();
             String l_exam=classMark.getExam();
             String l_subjectID=classMark.getSubjectID();
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
             if(!bsv.subjectIDValidation(l_subjectID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","SubjectID");
             }
             
             String[] l_pkey={l_standard,l_section,l_exam,l_subjectID};
             
             DBRecord l_examScheduleRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS","CLASS_EXAM_SCHEDULE_DETAIL", l_pkey, session, dbSession);
             
             String examDate=l_examScheduleRecord.getRecord().get(4).trim();
             
             if(!bsv.futureDateValidation(examDate, session, dbSession, inject)){
                 
                 status=false;
                 errhandler.log_app_error("BS_VAL_055",null);
                 
             }
            
             if(!bsv.classSubjectIDValidation(l_subjectID, l_standard, l_section, l_instituteID, session, dbSession, inject)){
                 
                 status=false;
                 errhandler.log_app_error("BS_VAL_056",l_subjectID);
             }
             
             
            dbg("end of class Mark masterDataValidation");
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
        RequestBody<ClassMark> reqBody = request.getReqBody();
        ClassMark classMark =reqBody.get();
        
        try{
            
            dbg("inside class Mark detailMandatoryValidation");
           
            if(classMark.getMark()==null||classMark.getMark().length==0){
                status=false;
                errhandler.log_app_error("BS_VAL_002","Marks");
            }else{
               
                for(int i=0;i<classMark.getMark().length;i++){
                
              
                    if(classMark.getMark()[i].getStudentID()==null||classMark.getMark()[i].getStudentID().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","StudentID");  
                    }
                    if(classMark.getMark()[i].getMark()==null||classMark.getMark()[i].getMark().isEmpty()){
                       status=false;  
                       errhandler.log_app_error("BS_VAL_002","Mark");  
                    }

                
                }
                
            }
            
            
            
           dbg("end of class Mark detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside class Mark detailDataValidation");
             RequestBody<ClassMark> reqBody = request.getReqBody();
             ClassMark classMark =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             BSValidation bsv=inject.getBsv(session);
             ArrayList<String>studentList=new ArrayList();
             
             
             for(int i=0;i<classMark.getMark().length;i++){
             
                 String l_studentID=classMark.getMark()[i].getStudentID();
                 
                 
                 if(!studentList.contains(l_studentID)){
                     
                     studentList.add(l_studentID);
                     
                 }else{
                     
                     status=false;
                     errhandler.log_app_error("BS_VAL_031","StudenID");
                     throw new BSValidationException("BSValidationException");
                 }
                 
                 
                 
                 String l_mark=classMark.getMark()[i].getMark();
                 if(!bsv.studentIDValidation(l_studentID,l_instituteID, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","studentID");
                 }
//                 if(!bsv.gradeValidation(l_subjectID,l_grade,l_instituteID, session, dbSession, inject)){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_003","grade");
//                 }
                 if(!bsv.markValidation(l_mark,l_instituteID, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","mark");
                 }
             }
            dbg("end of class Mark detailDataValidation");
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
        dbg("inside ClassMark--->getExistingAudit") ;
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
               dbg("inside ClassMark--->getExistingAudit--->Service--->UserProgressCard");  
               RequestBody<ClassMark> classMarkBody = request.getReqBody();
               ClassMark classMark =classMarkBody.get();
               String l_standard=classMark.getStandard();
               String l_section=classMark.getSection();  
               String l_exam=classMark.getExam();
               String l_subjectID=classMark.getSubjectID();
               String[] l_primaryKey={l_standard,l_section,l_exam,l_subjectID};
               DBRecord l_classMarkRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamMarkMaster","CLASS","CLASS_MARK_ENTRY", l_primaryKey, session, dbSession);
               exAudit.setAuthStatus(l_classMarkRecord.getRecord().get(9).trim());
               exAudit.setMakerID(l_classMarkRecord.getRecord().get(4).trim());
               exAudit.setRecordStatus(l_classMarkRecord.getRecord().get(8).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_classMarkRecord.getRecord().get(10).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of ClassMark--->getExistingAudit") ;
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
