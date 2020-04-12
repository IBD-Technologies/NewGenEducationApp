/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.summary.teacherprofile;

import com.ibd.businessViews.ITeacherProfileSummary;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
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
import java.util.ArrayList;
import java.util.List;
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
//@Local(ITeacherProfileSummary.class)
@Remote(ITeacherProfileSummary.class)
@Stateless
public class TeacherProfileSummary implements ITeacherProfileSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public TeacherProfileSummary(){
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
       dbg("inside TeacherProfileSummary--->processing");
       dbg("TeacherProfileSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
  
       BusinessEJB<ITeacherProfileSummary>teacherProfileEJB=new BusinessEJB();
       teacherProfileEJB.set(this);
      
       exAudit=bs.getExistingAudit(teacherProfileEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,teacherProfileEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,teacherProfileEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in teacher profile");
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
/*                if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
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
      TeacherProfileBO teacherProfile=new TeacherProfileBO();
      RequestBody<TeacherProfileBO> reqBody = new RequestBody<TeacherProfileBO>(); 
           
      try{
      dbg("inside teacher profile buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      teacherProfile.filter=new TeacherProfileFilter();
      teacherProfile.filter.setTeacherID(l_filterObject.getString("teacherID"));
      teacherProfile.filter.setTeacherName(l_filterObject.getString("teacherName"));
//      teacherProfile.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      teacherProfile.filter.setAuthStatus(l_filterObject.getString("authStat"));
      
      if(l_filterObject.getString("authStat").equals("Select option")){
          
          teacherProfile.filter.setAuthStatus("");
      }else{
      
          teacherProfile.filter.setAuthStatus(l_filterObject.getString("authStat"));
      }
              
      
        reqBody.set(teacherProfile);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside teacher profile--->view");  
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<TeacherProfileBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_profileList=new ArrayList();
        TeacherProfileBO teacherProfileBO=reqBody.get();
        String l_teacherID=teacherProfileBO.getFilter().getTeacherID();
        String l_teacherNameFilter=teacherProfileBO.getFilter().getTeacherName();
        
        try{
        
            ArrayList<String>l_fileNames=bs.getTeacherFileNames(l_teacherID, request, session, dbSession, inject);
            for(int i=0;i<l_fileNames.size();i++){
                dbg("inside file name iteration");
                String l_fileName=l_fileNames.get(i);
                dbg("l_fileName"+l_fileName);
                
                
                    String[] l_pkey={l_fileName};
                    DBRecord profileRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileName+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileName,"TEACHER", "TVW_TEACHER_PROFILE", l_pkey, session, dbSession);
                    l_profileList.add(profileRec);
                    dbg("file name itertion completed for "+l_fileName);
//                }
                
                
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
                
          dbg("end of  completed teacher profile--->view"); 
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
           RequestBody<TeacherProfileBO> reqBody = request.getReqBody();
           TeacherProfileBO teacherProfile =reqBody.get();
           String l_authStatus=teacherProfile.getFilter().getAuthStatus();
           String l_teacherID=teacherProfile.getFilter().getTeacherID();
           String l_teacherNameFilter=teacherProfile.getFilter().getTeacherName();
           dbg("l_authStatus"+l_authStatus);
           dbg("l_teacherID"+l_teacherID);
           
           if(l_teacherID!=null&&!l_teacherID.isEmpty()){
            
             List<DBRecord>  l_teacherList=  l_profileList.stream().filter(rec->rec.getRecord().get(0).trim().equals(l_teacherID)).collect(Collectors.toList());
             l_profileList = new ArrayList<DBRecord>(l_teacherList);
             dbg("teacher filter l_profileList size"+l_profileList.size());
           }

           if(l_teacherNameFilter!=null&&!l_teacherNameFilter.isEmpty()){
            
             List<DBRecord>  l_teacherList=  l_profileList.stream().filter(rec->rec.getRecord().get(1).trim().equals(l_teacherNameFilter)).collect(Collectors.toList());
             l_profileList = new ArrayList<DBRecord>(l_teacherList);
             dbg("teacherName filter l_profileList size"+l_profileList.size());
           }
           
           if(l_authStatus!=null&&!l_authStatus.isEmpty()){
               
               List<DBRecord>  l_teacherList=  l_profileList.stream().filter(rec->rec.getRecord().get(18).trim().equals(l_authStatus)).collect(Collectors.toList());
               l_profileList = new ArrayList<DBRecord>(l_teacherList);
               dbg("authStatus filter l_profileList size"+l_profileList.size());
               
           }
           
           teacherProfile.result=new TeacherProfileResult[l_profileList.size()];
           for(int i=0;i<l_profileList.size();i++){
               
               ArrayList<String> l_teacherProfileList=l_profileList.get(i).getRecord();
               teacherProfile.result[i]=new TeacherProfileResult();
               teacherProfile.result[i].setTeacherID(l_teacherProfileList.get(0).trim());
               teacherProfile.result[i].setTeacherNAme(l_teacherProfileList.get(1).trim());
               teacherProfile.result[i].setQualification(l_teacherProfileList.get(2).trim());
//               teacherProfile.result[i].setStandard(l_teacherProfileList.get(3).trim());
//               teacherProfile.result[i].setSection(l_teacherProfileList.get(4).trim());
               teacherProfile.result[i].setDob(l_teacherProfileList.get(3).trim());
               teacherProfile.result[i].setContactNo(l_teacherProfileList.get(4).trim());
               teacherProfile.result[i].setEmailID(l_teacherProfileList.get(5).trim());
               teacherProfile.result[i].setBloodGroup(l_teacherProfileList.get(6).trim());
               teacherProfile.result[i].setAddressLine1(l_teacherProfileList.get(7).trim());
               teacherProfile.result[i].setAddressLine2(l_teacherProfileList.get(8).trim());
               teacherProfile.result[i].setAddressLine3(l_teacherProfileList.get(9).trim());
               teacherProfile.result[i].setAddressLine4(l_teacherProfileList.get(10).trim());
               teacherProfile.result[i].setAddressLine5(l_teacherProfileList.get(11).trim());
               teacherProfile.result[i].setProfileImagePath(l_teacherProfileList.get(12).trim());
               teacherProfile.result[i].setMakerID(l_teacherProfileList.get(13).trim());
               teacherProfile.result[i].setCheckerID(l_teacherProfileList.get(14).trim());
               teacherProfile.result[i].setMakerDateStamp(l_teacherProfileList.get(15).trim());
               teacherProfile.result[i].setCheckerDateStamp(l_teacherProfileList.get(16).trim());
               teacherProfile.result[i].setRecordStatus(l_teacherProfileList.get(17).trim());
               teacherProfile.result[i].setAuthStatus(l_teacherProfileList.get(18).trim());
               teacherProfile.result[i].setVersionNumber(l_teacherProfileList.get(19).trim());
               teacherProfile.result[i].setMakerRemarks(l_teacherProfileList.get(20).trim());
               teacherProfile.result[i].setCheckerRemarks(l_teacherProfileList.get(21).trim());
               teacherProfile.result[i].setShortName(l_teacherProfileList.get(22).trim());
               teacherProfile.result[i].setExistingMedicalDetail(l_teacherProfileList.get(23).trim());
          }
           
           ErrorHandler errHandler=session.getErrorhandler();
           if(teacherProfile.result==null||teacherProfile.result.length==0){
               errHandler.log_app_error("BS_VAL_013", null);
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
        dbg("inside teacher profile buildJsonResFromBO");    
        RequestBody<TeacherProfileBO> reqBody = request.getReqBody();
        TeacherProfileBO teacherProfile =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<teacherProfile.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("teacherID", teacherProfile.result[i].getTeacherID())
                                                      .add("teacherName", teacherProfile.result[i].getTeacherNAme())
                                                      .add("qualification", teacherProfile.result[i].getQualification())
//                                                      .add("standard", teacherProfile.result[i].getStandard())
//                                                      .add("section", teacherProfile.result[i].getSection())
                                                      .add("dob", teacherProfile.result[i].getDob())
                                                      .add("contactNo", teacherProfile.result[i].getContactNo())
                                                      .add("emailID", teacherProfile.result[i].getEmailID())
                                                      .add("bloodGroup", teacherProfile.result[i].getBloodGroup())
                                                      .add("addressLine1", teacherProfile.result[i].getAddressLine1())
                                                      .add("addressLine2", teacherProfile.result[i].getAddressLine2())
                                                      .add("addressLine3", teacherProfile.result[i].getAddressLine3())
                                                      .add("addressLine4", teacherProfile.result[i].getAddressLine4())
                                                      .add("addressLine5", teacherProfile.result[i].getAddressLine5())
                                                      .add("imagePth", teacherProfile.result[i].getProfileImagePath())
                                                      .add("makerID", teacherProfile.result[i].getMakerID())
                                                      .add("checkerID", teacherProfile.result[i].getCheckerID())
                                                      .add("makerDateStamp", teacherProfile.result[i].getMakerDateStamp())
                                                      .add("checkerDateStamp", teacherProfile.result[i].getCheckerDateStamp())
                                                      .add("recordStatus", teacherProfile.result[i].getRecordStatus())
                                                      .add("authStatus", teacherProfile.result[i].getAuthStatus())
                                                      .add("versionNumber", teacherProfile.result[i].getVersionNumber())
                                                      .add("makerRemarks", teacherProfile.result[i].getMakerRemarks())
                                                      .add("checkerRemarks", teacherProfile.result[i].getCheckerRemarks())
                                                      .add("shortName", teacherProfile.result[i].getShortName())
                                                      .add("existingMedicalDetail", teacherProfile.result[i].getExistingMedicalDetail()));
        }

           filter=Json.createObjectBuilder()  .add("teacherID",teacherProfile.filter.getTeacherID())
                                              .add("teacherName",teacherProfile.filter.getTeacherName())
                                              .add("authStatus", teacherProfile.filter.getAuthStatus()).build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of teacher profile buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside teacher profile--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of teacher profile--->businessValidation"); 
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
        dbg("inside teacher profile master mandatory validation");
        RequestBody<TeacherProfileBO> reqBody = request.getReqBody();
        TeacherProfileBO teacherProfile =reqBody.get();
        int nullCount=0;
        
         if(teacherProfile.getFilter().getTeacherName()==null||teacherProfile.getFilter().getTeacherName().isEmpty()){
             nullCount++;
         }

          if(teacherProfile.getFilter().getAuthStatus()==null||teacherProfile.getFilter().getAuthStatus().isEmpty()){
             nullCount++;
         }
          
          if(nullCount==2){
              status=false;
              errhandler.log_app_error("BS_VAL_002","One Filter value");
          }
          
        dbg("end of teacher profile master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside teacher profile detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<TeacherProfileBO> reqBody = request.getReqBody();
             TeacherProfileBO teacherProfile =reqBody.get();
//             String l_teacherID=teacherProfile.getFilter().getTeacherID();
//             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_authStatus=teacherProfile.getFilter().getAuthStatus();
             
//             if(l_teacherID!=null&&!l_teacherID.isEmpty()){
//                 
//                if(!bsv.teacherIDValidation(l_teacherID, l_instituteID, session, dbSession, inject)){
//                    status=false;
//                    errhandler.log_app_error("BS_VAL_003","teacherID");
//                }
//                 
//             }
             
             if(l_authStatus!=null&&!l_authStatus.isEmpty()){
                 
                if(!bsv.authStatusValidation(l_authStatus, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","authStatus");
                }
                 
             }
             
            dbg("end of teacher profile detailDataValidation");
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
