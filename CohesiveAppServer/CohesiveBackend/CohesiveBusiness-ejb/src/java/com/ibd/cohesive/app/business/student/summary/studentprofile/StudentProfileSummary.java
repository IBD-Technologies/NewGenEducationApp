/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentprofile;

import com.ibd.businessViews.IStudentProfileSummary;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.student.summary.studentpaymentsummary.StudentPaymentBO;
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
//@Local(IStudentProfileSummary.class)
@Remote(IStudentProfileSummary.class)
@Stateless
public class StudentProfileSummary implements IStudentProfileSummary{
     AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentProfileSummary(){
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
       dbg("inside StudentProfileSummary--->processing");
       dbg("StudentProfileSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IStudentProfileSummary>studentProfileEJB=new BusinessEJB();
       studentProfileEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentProfileEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,studentProfileEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentProfileEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student profile");
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
                dbg("response"+jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
               /* if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                   BSProcessingException ex=new BSProcessingException("response contains special characters");
                   dbg(ex);
                   session.clearSessionObject();
                   dbSession.clearSessionObject();
                   throw ex;
                }*/
            clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes
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
      StudentProfileBO studentProfile=new StudentProfileBO();
      RequestBody<StudentProfileBO> reqBody = new RequestBody<StudentProfileBO>(); 
           
      try{
      dbg("inside student profile buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      BSValidation bsv=inject.getBsv(session);
      studentProfile.filter=new StudentProfileFilter();
      studentProfile.filter.setStudentID(l_filterObject.getString("studentID"));
      studentProfile.filter.setStudentName(l_filterObject.getString("studentName"));
//      studentProfile.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      studentProfile.filter.setAuthStatus(l_filterObject.getString("authStat"));

      if(l_filterObject.getString("class").equals("Select option")){
          studentProfile.filter.setStandard("");
          studentProfile.filter.setSection("");
      }else{

          String l_class=l_filterObject.getString("class");
          bsv.classValidation(l_class,session);
          studentProfile.filter.setStandard(l_class.split("/")[0]);
          studentProfile.filter.setSection(l_class.split("/")[1]);
      
      }


      if(l_filterObject.getString("authStat").equals("Select option")){
          
          studentProfile.filter.setAuthStatus("");
      }else{
      
          studentProfile.filter.setAuthStatus(l_filterObject.getString("authStat"));
      }

      
        reqBody.set(studentProfile);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student profile--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentProfileBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_profileList=new ArrayList();
        StudentProfileBO studentPayment=reqBody.get();
        String l_studentID=studentPayment.getFilter().getStudentID();
        String l_standard=studentPayment.getFilter().getStandard();
        String l_section=studentPayment.getFilter().getSection();
        BusinessService bs=inject.getBusinessService(session);
        String studentNameFilter=studentPayment.getFilter().getStudentName();
        
        try{
        ArrayList<String>l_fileNames=bs.getStudentFileNames(l_studentID,request,session,dbSession,inject,l_standard,l_section);
        for(int i=0;i<l_fileNames.size();i++){
            dbg("inside file name iteration");
            String l_fileName=l_fileNames.get(i);
            dbg("l_fileName"+l_fileName);
            
//            String studentName=bs.getStudentName(l_fileName, l_instituteID, session, dbSession, inject);
            
//            if(studentName.equals(studentNameFilter)){
            
                String[] l_pkey={l_fileName};
                DBRecord profileRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileName+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileName,"STUDENT", "SVW_STUDENT_PROFILE", l_pkey, session, dbSession);
                l_profileList.add(profileRec);
                dbg("file name itertion completed for "+l_fileName);
            
//            }
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

        buildBOfromDB(l_profileList);     
        
          dbg("end of  completed student profile--->view"); 
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
    
    
    
    
    
    private void buildBOfromDB(ArrayList<DBRecord>l_profileList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<StudentProfileBO> reqBody = request.getReqBody();
           StudentProfileBO studentProfile =reqBody.get();
           String l_authStatus=studentProfile.getFilter().getAuthStatus();
           String l_studentID=studentProfile.getFilter().getStudentID();
           String l_studentName=studentProfile.getFilter().getStudentName();
           dbg("l_authStatus"+l_authStatus);
           dbg("l_studentID"+l_studentID);
           
           if(l_studentID!=null&&!l_studentID.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_profileList.stream().filter(rec->rec.getRecord().get(0).trim().equals(l_studentID)).collect(Collectors.toList());
             l_profileList = new ArrayList<DBRecord>(l_studentList);
             dbg("student filter l_profileList size"+l_profileList.size());
           }

           if(l_studentName!=null&&!l_studentName.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_profileList.stream().filter(rec->rec.getRecord().get(1).trim().equals(l_studentName)).collect(Collectors.toList());
             l_profileList = new ArrayList<DBRecord>(l_studentList);
             dbg("studentName filter l_profileList size"+l_profileList.size());
           }
           
           if(l_authStatus!=null&&!l_authStatus.isEmpty()){
               
               List<DBRecord>  l_studentList=  l_profileList.stream().filter(rec->rec.getRecord().get(18).trim().equals(l_authStatus)).collect(Collectors.toList());
               l_profileList = new ArrayList<DBRecord>(l_studentList);
               dbg("authStatus filter l_profileList size"+l_profileList.size());
               
           }
           
           studentProfile.result=new StudentProfileResult[l_profileList.size()];
           for(int i=0;i<l_profileList.size();i++){
               
               ArrayList<String> l_studentProfileList=l_profileList.get(i).getRecord();
               studentProfile.result[i]=new StudentProfileResult();
               studentProfile.result[i].setStudentID(l_studentProfileList.get(0).trim());
               studentProfile.result[i].setStudentName(l_studentProfileList.get(1).trim());
               studentProfile.result[i].setStandard(l_studentProfileList.get(2).trim());
               studentProfile.result[i].setSection(l_studentProfileList.get(3).trim());
               studentProfile.result[i].setDob(l_studentProfileList.get(4).trim());
               studentProfile.result[i].setBloodGroup(l_studentProfileList.get(5).trim());
               studentProfile.result[i].setAddressLine1(l_studentProfileList.get(6).trim());
               studentProfile.result[i].setAddressLine2(l_studentProfileList.get(7).trim());
               studentProfile.result[i].setAddressLine3(l_studentProfileList.get(8).trim());
               studentProfile.result[i].setAddressLine4(l_studentProfileList.get(9).trim());
               studentProfile.result[i].setAddressLine5(l_studentProfileList.get(10).trim());
               studentProfile.result[i].setProfileImagePath(l_studentProfileList.get(11).trim());
               studentProfile.result[i].setNotes(l_studentProfileList.get(12).trim());
               studentProfile.result[i].setMakerID(l_studentProfileList.get(13).trim());
               studentProfile.result[i].setCheckerID(l_studentProfileList.get(14).trim());
               studentProfile.result[i].setMakerDateStamp(l_studentProfileList.get(15).trim());
               studentProfile.result[i].setCheckerDateStamp(l_studentProfileList.get(16).trim());
               studentProfile.result[i].setRecordStatus(l_studentProfileList.get(17).trim());
               studentProfile.result[i].setAuthStatus(l_studentProfileList.get(18).trim());
               studentProfile.result[i].setVersionNumber(l_studentProfileList.get(19).trim());
               studentProfile.result[i].setMakerRemarks(l_studentProfileList.get(20).trim());
               studentProfile.result[i].setCheckerRemarks(l_studentProfileList.get(21).trim());
               studentProfile.result[i].setExistingMedicalDetails(l_studentProfileList.get(22).trim());
          }
           
           if(studentProfile.result==null||studentProfile.result.length==0){
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
        dbg("inside student profile buildJsonResFromBO");    
        RequestBody<StudentProfileBO> reqBody = request.getReqBody();
        StudentProfileBO studentProfile =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<studentProfile.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("studentID", studentProfile.result[i].getStudentID())
                                                      .add("studentName", studentProfile.result[i].getStudentName())
                                                      .add("class", studentProfile.result[i].getStandard()+"/"+studentProfile.result[i].getSection())
                                                      .add("dob", studentProfile.result[i].getDob())
                                                      .add("bloodGroup", studentProfile.result[i].getBloodGroup())
                                                      .add("addressLine1", studentProfile.result[i].getAddressLine1())
                                                      .add("addressLine2", studentProfile.result[i].getAddressLine2())
                                                      .add("addressLine3", studentProfile.result[i].getAddressLine3())
                                                      .add("addressLine4", studentProfile.result[i].getAddressLine4())
                                                      .add("addressLine5", studentProfile.result[i].getAddressLine5())
                                                      .add("imagePth", studentProfile.result[i].getProfileImagePath())
                                                      .add("notes", studentProfile.result[i].getNotes())
                                                      .add("makerID", studentProfile.result[i].getMakerID())
                                                      .add("checkerID", studentProfile.result[i].getCheckerID())
                                                      .add("makerDateStamp", studentProfile.result[i].getMakerDateStamp())
                                                      .add("checkerDateStamp", studentProfile.result[i].getCheckerDateStamp())
                                                      .add("recordStatus", studentProfile.result[i].getRecordStatus())
                                                      .add("authStatus", studentProfile.result[i].getAuthStatus())
                                                      .add("versionNumber", studentProfile.result[i].getVersionNumber())
                                                      .add("makerRemarks", studentProfile.result[i].getMakerRemarks())
                                                      .add("checkerRemarks", studentProfile.result[i].getCheckerRemarks())
                                                      .add("existingMedicalDetail", studentProfile.result[i].getExistingMedicalDetail()));
        }

           filter=Json.createObjectBuilder()  .add("studentID",studentProfile.filter.getStudentID())
                                              .add("studentName",studentProfile.filter.getStudentName())
                                              .add("class",studentProfile.filter.getStandard()+"/"+studentProfile.filter.getSection())
                                              .add("authStatus", studentProfile.filter.getAuthStatus()).build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of student profile buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student profile--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of student profile--->businessValidation"); 
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
        dbg("inside student profile master mandatory validation");
        RequestBody<StudentProfileBO> reqBody = request.getReqBody();
        StudentProfileBO studentProfile =reqBody.get();
//        int nullCount=0;
        BusinessService bs=inject.getBusinessService(session);
        String userID=request.getReqHeader().getUserID();
        String userType=bs.getUserType(userID, session, dbSession, inject);  
        String standard=studentProfile.getFilter().getStandard();
        String section=studentProfile.getFilter().getSection();
        String studentID=studentProfile.getFilter().getStudentID();
        String studentName=studentProfile.getFilter().getStudentName();
        String instituteID=request.getReqHeader().getInstituteID();
        
//         if(studentProfile.getFilter().getStudentID()==null||studentProfile.getFilter().getStudentID().isEmpty()){
//             nullCount++;
//         }
//
//          if(studentProfile.getFilter().getAuthStatus()==null||studentProfile.getFilter().getAuthStatus().isEmpty()){
//             nullCount++;
//         }
//          
//          if(studentProfile.getFilter().getStandard()==null||studentProfile.getFilter().getStandard().isEmpty()){
//             nullCount++;
//         }
//
//          if(studentProfile.getFilter().getSection()==null||studentProfile.getFilter().getSection().isEmpty()){
//             nullCount++;
//         }
//          if(studentProfile.getFilter().getStudentName()==null||studentProfile.getFilter().getStudentName().isEmpty()){
//             nullCount++;
//         }
//          
//          if(nullCount==5){
//              status=false;
//              errhandler.log_app_error("BS_VAL_002","One Filter value");
//          }


        studentID=bs.studentValidation(studentID, studentName, instituteID, session, dbSession, inject);

        studentProfile.getFilter().setStudentID(studentID);
        
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


          
        dbg("end of student profile master mandatory validation");
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
             dbg("inside student profile detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<StudentProfileBO> reqBody = request.getReqBody();
             StudentProfileBO studentProfile =reqBody.get();
             String l_studentID=studentProfile.getFilter().getStudentID();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_authStatus=studentProfile.getFilter().getAuthStatus();
             
             if(l_studentID!=null&&!l_studentID.isEmpty()){
                 
                if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","studentID");
                }
                 
             }
             
             if(l_authStatus!=null&&!l_authStatus.isEmpty()){
                 
                if(!bsv.authStatusValidation(l_authStatus, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","authStatus");
                }
                 
             }
             
            dbg("end of student profile detailDataValidation");
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
