/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentprogresscard;

import com.ibd.businessViews.IStudentProgressCardService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.classentity.classmark.GradeDescription;
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
//@Local(IStudentProgressCardService.class)
@Remote(IStudentProgressCardService.class)
@Stateless
public class StudentProgressCardService implements IStudentProgressCardService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentProgressCardService(){
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
       dbg("inside StudentProgressCardService--->processing");
       dbg("StudentProgressCardService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<StudentProgressCard> reqBody = request.getReqBody();
       StudentProgressCard studentProgressCard =reqBody.get();
       businessLock=inject.getBusinessLockService();
       String l_studentID=studentProgressCard.getStudentID();
       String l_exam=studentProgressCard.getExam();
       l_lockKey=l_studentID+"~"+l_exam;
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       BusinessEJB<IStudentProgressCardService>studentProgressCardEJB=new BusinessEJB();
       studentProgressCardEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentProgressCardEJB);
       if(request.getReqHeader().getOperation().equals("View")){
           
         String studentID=  bs.studentValidation(studentProgressCard.getStudentID(), studentProgressCard.getStudentName(), request.getReqHeader().getInstituteID(), session, dbSession, inject);
         
          
         if(studentID==null||studentID.isEmpty()){
             
             errhandler.log_app_error("BS_VAL_002","Student ID or Name");  
             throw new BSValidationException("BSValidationException");
         }
         
         studentProgressCard.setStudentID(studentID);
         bs.setStudentIDInBusinessEntity(studentID, request);
           
       }
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,studentProgressCardEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentProgressCardEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student progressCard");
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
      StudentProgressCard studentProgressCard=new StudentProgressCard();
      RequestBody<StudentProgressCard> reqBody = new RequestBody<StudentProgressCard>(); 
           
      try{
      dbg("inside student progressCard buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      studentProgressCard.setStudentID(l_body.getString("studentID"));
      studentProgressCard.setStudentName(l_body.getString("studentName"));
      studentProgressCard.setExam(l_body.getString("exam"));
      if(!l_operation.equals("View")){
          studentProgressCard.setTotal(l_body.getString("total"));
          studentProgressCard.setRank(l_body.getString("rank"));
          JsonArray l_marks=l_body.getJsonArray("marks");
          studentProgressCard.mark=new Marks[l_marks.size()];
          for(int i=0;i<l_marks.size();i++){
              studentProgressCard.mark[i]=new Marks();
              JsonObject l_markObject=l_marks.getJsonObject(i);
              studentProgressCard.mark[i].setSubjectID(l_markObject.getString("subjectID"));
              studentProgressCard.mark[i].setGrade(l_markObject.getString("grade"));
              studentProgressCard.mark[i].setMark(l_markObject.getString("mark"));
              studentProgressCard.mark[i].setTeacherFeedback(l_markObject.getString("teacherFeedback"));
          }
        
      }
        reqBody.set(studentProgressCard);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
    try{ 
        dbg("inside stident progressCard create"); 
        RequestBody<StudentProgressCard> reqBody = request.getReqBody();
        StudentProgressCard studentProgressCard =reqBody.get();
        IPDataService pds=inject.getPdataservice();
        BusinessService bs=inject.getBusinessService(session);
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_userId=request.getReqHeader().getUserID();
        String userType=bs.getUserType(l_userId, session, dbSession, inject);
        String l_studentID=studentProgressCard.getStudentID();
        String l_exam=studentProgressCard.getExam();
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
//        String l_total=studentProgressCard.getTotal();
//        String l_rank=studentProgressCard.getRank();
//        
//        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",45,l_studentID,l_exam,l_total,l_rank,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
//
//      
//         for(int i=0;i<studentProgressCard.mark.length;i++){
//             String l_subjectID=studentProgressCard.mark[i].getSubjectID();
//             String l_grade=studentProgressCard.mark[i].getGrade();
//             String l_mark=studentProgressCard.mark[i].getMark();
//             String l_teacherFeedback=studentProgressCard.mark[i].getTeacherFeedback();
//             
//             dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",2,l_studentID,l_exam,l_subjectID,l_grade,l_mark,l_teacherFeedback,l_versionNumber);
//  
//         }

                 
                 String signatureStatus="Y";
                 
                 dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS",342,l_studentID,signatureStatus,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
                 
 

        
        
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
        
     try{ 
        dbg("inside student progressCard--->auth update"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<StudentProgressCard> reqBody = request.getReqBody();
        StudentProgressCard studentProgressCard =reqBody.get();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_studentID=studentProgressCard.getStudentID();
        String l_exam=studentProgressCard.getExam();
        String[] l_primaryKey={l_studentID,l_exam,};
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("6", l_checkerID);
         l_column_to_update.put("8", l_checkerDateStamp);
         l_column_to_update.put("10", l_authStatus);
         l_column_to_update.put("13", l_checkerRemarks);
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_PRORESS_CARD", l_primaryKey, l_column_to_update, session);
         dbg("end of student progressCard--->auth update");          
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
        dbg("inside student progressCard--->full update");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentProgressCard> reqBody = request.getReqBody();
        StudentProgressCard studentProgressCard =reqBody.get();
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
        String l_studentID=studentProgressCard.getStudentID();
        String l_exam=studentProgressCard.getExam();
        String l_total=studentProgressCard.getTotal();
        String l_rank=studentProgressCard.getRank();
        
         l_column_to_update= new HashMap();
         l_column_to_update.put("1", l_studentID);
         l_column_to_update.put("2", l_exam);
         l_column_to_update.put("3", l_total);
         l_column_to_update.put("4", l_rank);
         l_column_to_update.put("5", l_makerID);
         l_column_to_update.put("6", l_checkerID);
         l_column_to_update.put("7", l_makerDateStamp);
         l_column_to_update.put("8", l_checkerDateStamp);
         l_column_to_update.put("9", l_recordStatus);
         l_column_to_update.put("10", l_authStatus);
         l_column_to_update.put("11", l_versionNumber);
         l_column_to_update.put("12", l_makerRemarks);
         l_column_to_update.put("13", l_checkerRemarks);
         String[] l_primaryKey={l_studentID,l_exam};

           dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_PRORESS_CARD", l_primaryKey,l_column_to_update, session);

            
	 
	 IDBReadBufferService readBuffer = inject.getDBReadBufferService();
         Map<String,DBRecord>l_examScheduleMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_MARKS", session, dbSession);
         setOperationsOfTheRecord("SVW_STUDENT_MARKS",l_examScheduleMap);  
      
         for(int i=0;i<studentProgressCard.mark.length;i++){
             String l_subjectID=studentProgressCard.mark[i].getSubjectID();
             String l_grade=studentProgressCard.mark[i].getGrade();
             String l_mark=studentProgressCard.mark[i].getMark();
             String l_teacherFeedback=studentProgressCard.mark[i].getTeacherFeedback();
             
             if(studentProgressCard.mark[i].getOperation().equals("U")){
             
                     l_column_to_update= new HashMap();
                     l_column_to_update.put("1", l_studentID);
                     l_column_to_update.put("2", l_exam);
                     l_column_to_update.put("3", l_subjectID);
                     l_column_to_update.put("4", l_grade);
                     l_column_to_update.put("5", l_mark);
                     l_column_to_update.put("6", l_teacherFeedback);
                     l_column_to_update.put("7", l_versionNumber);
                     String[] l_markPrimaryKey={l_studentID,l_exam,l_subjectID};

                              dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_MARKS", l_markPrimaryKey,l_column_to_update, session);
  
             }else{
                 
                 
                 dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",2,l_studentID,l_exam,l_subjectID,l_grade,l_mark,l_teacherFeedback,l_versionNumber);
                 
             }             
         }
         
         ArrayList<String>cpList=getRecordsToDelete("SVW_STUDENT_MARKS",l_examScheduleMap);
         
                  for(int i=0;i<cpList.size();i++){
                      String pkey=cpList.get(i);
                      deleteRecordsInTheList("SVW_STUDENT_MARKS",pkey);
                  }
        
         dbg("end of student progressCard--->full update");
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
            RequestBody<StudentProgressCard> reqBody = request.getReqBody();
            StudentProgressCard studentProgress =reqBody.get();
            String l_studentID=studentProgress.getStudentID();
            String l_exam=studentProgress.getExam();
            dbg("tableName"+tableName);
            
            switch(tableName){
                
                case "SVW_STUDENT_MARKS":  
                
                         for(int i=0;i<studentProgress.mark.length;i++){
                                String l_subjectID=studentProgress.mark[i].getSubjectID();
                                String l_pkey=l_studentID+"~"+l_exam+"~"+l_subjectID;
                               if(tableMap.containsKey(l_pkey)){

                                    studentProgress.mark[i].setOperation("U");
                                }else{

                                    studentProgress.mark[i].setOperation("C");
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
           RequestBody<StudentProgressCard> reqBody = request.getReqBody();
           StudentProgressCard studentProgress =reqBody.get();
           String l_studentID=studentProgress.getStudentID();
           String l_exam=studentProgress.getExam();
           ArrayList<String>recordsToDelete=new ArrayList();
//           Iterator<String>keyIterator=tableMap.keySet().iterator();

           List<DBRecord>filteredRecords=tableMap.values().stream().filter(rec->rec.getRecord().get(0).trim().equals(l_studentID)&&rec.getRecord().get(1).trim().equals(l_exam)).collect(Collectors.toList());
        
           dbg("tableName"+tableName);
           switch(tableName){
           
                 case "SVW_STUDENT_MARKS":   
                   
                   for(int j=0;j<filteredRecords.size();j++){
                        String subjectID=filteredRecords.get(j).getRecord().get(1).trim();
                        String tablePkey=l_studentID+"~"+l_exam+"~"+subjectID;
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                       for(int i=0;i<studentProgress.mark.length;i++){
                           String l_subjectID=studentProgress.mark[i].getSubjectID(); 
                           String l_requestPkey=l_studentID+"~"+l_exam+"~"+l_subjectID;
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
             RequestBody<StudentProgressCard> reqBody = request.getReqBody();
             StudentProgressCard studentProgress =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_studentID=studentProgress.getStudentID();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IDBTransactionService dbts=inject.getDBTransactionService();
             String[] pkArr=pkey.split("~");
             
             
             dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT",tableName, pkArr, session);
             
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
        dbg("inside student progressCard delete");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentProgressCard> reqBody = request.getReqBody();
        StudentProgressCard studentProgressCard =reqBody.get();
        String l_studentID=studentProgressCard.getStudentID();
        String l_exam=studentProgressCard.getExam();
        String[] l_primaryKey={l_studentID,l_exam};

        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_PRORESS_CARD", l_primaryKey, session);
       
        for(int i=0;i<studentProgressCard.mark.length;i++){
             String l_subjectID=studentProgressCard.mark[i].getSubjectID();
             String[] l_markPrimaryKey={l_studentID,l_exam,l_subjectID};

             dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_MARKS", l_markPrimaryKey, session);
        }               
            dbg("end of student progressCard delete");
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
        dbg("inside student progressCard--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IPDataService pds=inject.getPdataservice();
        RequestBody<StudentProgressCard> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        StudentProgressCard studentProgressCard =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_studentID=studentProgressCard.getStudentID();
        String l_exam=studentProgressCard.getExam();
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
            
            markMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS","STUDENT_MARKS", session, dbSession);
     
            markRecords=markMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals(l_studentID)).collect(Collectors.toList());            
            
            if(markRecords==null||markRecords.isEmpty()){
                
                session.getErrorhandler().log_app_error("BS_VAL_013",l_studentID+l_exam);
                throw new BSValidationException("BSValidationException");
            }
            
            rankRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam+"_"+"Rank", "CLASS", "CLASS_EXAM_RANK", l_pkey, session, dbSession);
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
        
        buildBOfromDB(rankRecord,markRecords,standard,section);
        
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
      private void buildBOfromDB(DBRecord rankRecord, List<DBRecord>markRecords,String standard,String section)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           String instituteID=request.getReqHeader().getInstituteID();
           RequestBody<StudentProgressCard> reqBody = request.getReqBody();
           BusinessService bs=inject.getBusinessService(session);
           StudentProgressCard studentProgressCard =reqBody.get();
           String l_exam=studentProgressCard.getExam();
           String l_studentID=studentProgressCard.getStudentID();
           ArrayList<String>l_studentProgressCardList= rankRecord.getRecord();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IPDataService pds=inject.getPdataservice();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           String l_instituteID=request.getReqHeader().getInstituteID();
           String studentName=bs.getStudentName(l_studentID, l_instituteID, session, dbSession, inject);
           studentProgressCard.setStudentName(studentName);
           if(l_studentProgressCardList!=null&&!l_studentProgressCardList.isEmpty()){
               
            studentProgressCard.setTotal(l_studentProgressCardList.get(2).trim());
            studentProgressCard.setRank(l_studentProgressCardList.get(3).trim());
            String[] l_pkey={l_studentID};
            boolean recordExistence=false;
            DBRecord signRecord=null;
            try{
                
                
                signRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam, "CLASS", "STUDENT_PROGRESS_CARD_SIGNATURE", l_pkey, session, dbSession);
                
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
            }
           
           
            studentProgressCard.mark=new Marks[markRecords.size()];
            int i=0;
                for(DBRecord l_markRecords:markRecords){
                   studentProgressCard.mark[i]=new Marks();
                   studentProgressCard.mark[i].setSubjectID(l_markRecords.getRecord().get(3).trim());
                   String subjectName=bs.getSubjectName(studentProgressCard.mark[i].getSubjectID(), instituteID, session, dbSession, inject);
                   studentProgressCard.mark[i].setSubjectName(subjectName);
                   studentProgressCard.mark[i].setMark(l_markRecords.getRecord().get(6).trim());
                   String grade=bs.getGrade(studentProgressCard.mark[i].getMark(), instituteID, session, dbSession, inject);
                   studentProgressCard.mark[i].setGrade(grade);
                   studentProgressCard.mark[i].setTeacherFeedback(l_markRecords.getRecord().get(7).trim());
                   i++;
                }
           Map<String,ArrayList<String>>l_gradeMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE","IVW_SUBJECT_GRADE_MASTER", session, dbSession);
            String masterVersion=bs.getMaxVersionOfTheInstitute(instituteID, session, dbSession, inject);
            int versionIndex=bs.getVersionIndexOfTheTable("IVW_SUBJECT_GRADE_MASTER", "INSTITUTE", session, dbSession, inject);
            
            List<ArrayList<String>>filteredList=l_gradeMap.values().stream().filter(rec->rec.get(versionIndex).equals(masterVersion)).collect(Collectors.toList());
            
            
            Iterator<ArrayList<String>> valueIterator= filteredList.iterator();
            
            studentProgressCard.gradeDescription=new GradeDescription[filteredList.size()];
            int j=0;
            while(valueIterator.hasNext()){
                
                ArrayList<String>value=valueIterator.next();
                String grade=value.get(1).trim();
                String from=value.get(2).trim();
                String to=value.get(3).trim();
                studentProgressCard.gradeDescription[j]=new GradeDescription();
                studentProgressCard.gradeDescription[j].setGrade(grade);
                studentProgressCard.gradeDescription[j].setDescription(from+" - "+to);
                
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
        dbg("inside student progressCard buildJsonResFromBO");
        RequestBody<StudentProgressCard> reqBody = request.getReqBody();
        StudentProgressCard studentProgressCard =reqBody.get();
        JsonArrayBuilder mark=Json.createArrayBuilder();
        JsonArrayBuilder gradeDescription=Json.createArrayBuilder();
        
        for(int i=0;i<studentProgressCard.mark.length;i++){
            
            mark.add(Json.createObjectBuilder().add("subjectID", studentProgressCard.mark[i].getSubjectID())
                                               .add("subjectName", studentProgressCard.mark[i].getSubjectName())
                                               .add("grade", studentProgressCard.mark[i].getGrade())
                                               .add("mark", studentProgressCard.mark[i].getMark())
                                               .add("teacherFeedback", studentProgressCard.mark[i].getTeacherFeedback()));
            }
            
        if(studentProgressCard.gradeDescription!=null){
        
            for(int i=0;i<studentProgressCard.gradeDescription.length;i++){
            
                gradeDescription.add(Json.createObjectBuilder().add("grade", studentProgressCard.gradeDescription[i].getGrade())
                                                               .add("description", studentProgressCard.gradeDescription[i].getDescription()));
            }
        }
        
        
            body=Json.createObjectBuilder().add("studentID",  studentProgressCard.getStudentID())
                                           .add("studentName",  studentProgressCard.getStudentName())
                                           .add("exam", studentProgressCard.getExam())
                                           .add("marks", mark)
                                           .add("total", studentProgressCard.getTotal())
                                           .add("rank", studentProgressCard.getRank())
                                           .add("gradeDescription", gradeDescription)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of student progressCard buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student progressCard--->businessValidation");    
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
       dbg("end of student progressCard--->businessValidation"); 
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
        dbg("inside student progressCard master mandatory validation");
        RequestBody<StudentProgressCard> reqBody = request.getReqBody();
        StudentProgressCard studentProgressCard =reqBody.get();
        
         if(studentProgressCard.getStudentID()==null||studentProgressCard.getStudentID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","Student ID");  
         }
          if(studentProgressCard.getExam()==null||studentProgressCard.getExam().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","Exam");  
         }
         
          
        dbg("end of student progressCard master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student progressCard masterDataValidation");
             RequestBody<StudentProgressCard> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             StudentProgressCard studentProgressCard =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_studentID=studentProgressCard.getStudentID();
             String l_exam=studentProgressCard.getExam();
           
             
             if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Student ID");
             }
             if(!bsv.examValidation(l_exam, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","exam");
             }
            
             
             
          
             
            dbg("end of student progressCard masterDataValidation");
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
        RequestBody<StudentProgressCard> reqBody = request.getReqBody();
        StudentProgressCard studentProgressCard =reqBody.get();
        
        try{
            
            dbg("inside student progressCard detailMandatoryValidation");
           
//            if(studentProgressCard.getMark()==null||studentProgressCard.getMark().length==0){
//                status=false;
//                errhandler.log_app_error("BS_VAL_002","marks");
//            }else{
//               
//                for(int i=0;i<studentProgressCard.getMark().length;i++){
//                
//                    if(studentProgressCard.getMark()[i].getSubjectID()==null||studentProgressCard.getMark()[i].getSubjectID().isEmpty()){
//                       status=false;  
//                       errhandler.log_app_error("BS_VAL_002","Subject ID");  
//                    }
//                    if(studentProgressCard.getMark()[i].getMark()==null||studentProgressCard.getMark()[i].getMark().isEmpty()){
//                       status=false;  
//                       errhandler.log_app_error("BS_VAL_002","Mark");  
//                    }
//                    if(studentProgressCard.getMark()[i].getGrade()==null||studentProgressCard.getMark()[i].getGrade().isEmpty()){
//                       status=false;  
//                       errhandler.log_app_error("BS_VAL_002","Grade");  
//                    }
//                    if(studentProgressCard.getMark()[i].getTeacherFeedback()==null||studentProgressCard.getMark()[i].getTeacherFeedback().isEmpty()){
//                       status=false;  
//                       errhandler.log_app_error("BS_VAL_002","Teacher FeedBack");  
//                    }
//                
//                }
//                
//            }
            
            
            
           dbg("end of student progressCard detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student progressCard detailDataValidation");
             RequestBody<StudentProgressCard> reqBody = request.getReqBody();
             StudentProgressCard studentProgressCard =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             BSValidation bsv=inject.getBsv(session);
             String l_exam=studentProgressCard.getExam();
             String l_studentID=studentProgressCard.getStudentID();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IPDataService pds=inject.getPdataservice();
             String[] l_pkey={l_studentID};
             ArrayList<String>l_studentList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",l_pkey);
             String l_standard=l_studentList.get(2).trim();
             String l_section=l_studentList.get(3).trim();
             IDBReadBufferService readBuffer=inject.getDBReadBufferService();
             boolean signatureStatus=false;
             
             
             try{
             
             DBRecord studentRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS", "STUDENT_PROGRESS_CARD_SIGNATURE", l_pkey, session, dbSession);
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
             
//             for(int i=0;i<studentProgressCard.getMark().length;i++){
//             
//                 String l_subjectID=studentProgressCard.getMark()[i].getSubjectID();
//                 String l_grade=studentProgressCard.getMark()[i].getGrade();
//                 String l_mark=studentProgressCard.getMark()[i].getMark();
//
//                 if(!bsv.subjectIDValidation(l_subjectID,l_instituteID, session, dbSession, inject)){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_003","SubjectID");
//                 }
//                 if(!bsv.gradeValidation(l_subjectID,l_grade,l_instituteID, session, dbSession, inject)){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_003","grade");
//                 }
//                 if(!bsv.markValidation(l_mark,l_instituteID, session, dbSession, inject)){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_003","mark");
//                 }
//             }
            dbg("end of student progressCard detailDataValidation");
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
        dbg("inside StudentProgressCard--->getExistingAudit") ;
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
               dbg("inside StudentProgressCard--->getExistingAudit--->Service--->UserProgressCard");  
               RequestBody<StudentProgressCard> studentProgressCardBody = request.getReqBody();
               StudentProgressCard studentProgressCard =studentProgressCardBody.get();
               String l_studentID=studentProgressCard.getStudentID();
               String l_exam=studentProgressCard.getExam();
               String[] l_pkey={l_studentID};
               ArrayList<String>l_studentList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",l_pkey);
               String standard=l_studentList.get(2).trim();
               String section=l_studentList.get(3).trim();
               
//               DBRecord l_studentProgressCardRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT", "SVW_STUDENT_PRORESS_CARD", l_pkey, session, dbSession);
              boolean recordExistence=false;

           DBRecord signRecord=null;
            try{
                
                
                signRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam, "CLASS", "STUDENT_PROGRESS_CARD_SIGNATURE", l_pkey, session, dbSession);
                
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
        
        dbg("end of StudentProgressCard--->getExistingAudit") ;
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


