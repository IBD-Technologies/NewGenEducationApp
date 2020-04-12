/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.user.summary.userprofile;

import com.ibd.businessViews.IUserProfileSummary;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.institute.search.userSearch.UserSearch;
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
//@Local(IUserProfileSummary.class)
@Remote(IUserProfileSummary.class)
@Stateless
public class UserProfileSummary implements IUserProfileSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public UserProfileSummary(){
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
       dbg("inside UserProfileSummary--->processing");
       dbg("UserProfileSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IUserProfileSummary>userProfileEJB=new BusinessEJB();
       userProfileEJB.set(this);
      
       exAudit=bs.getExistingAudit(userProfileEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,userProfileEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,userProfileEJB);
              tc.commit(session,dbSession);
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
                /*if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
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
      UserProfileBO userProfile=new UserProfileBO();
      RequestBody<UserProfileBO> reqBody = new RequestBody<UserProfileBO>(); 
           
      try{
      dbg("inside user profile buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      userProfile.filter=new UserProfileFilter();
      userProfile.filter.setUserID(l_filterObject.getString("userID"));
      userProfile.filter.setUserName(l_filterObject.getString("userName"));
//      userProfile.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      userProfile.filter.setAuthStatus(l_filterObject.getString("authStat"));
      if(l_filterObject.getString("authStat").equals("Select option")){
          
          userProfile.filter.setAuthStatus("");
      }else{
      
          userProfile.filter.setAuthStatus(l_filterObject.getString("authStat"));
      }

      
        reqBody.set(userProfile);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside user profile--->view");
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IPDataService pds=inject.getPdataservice();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<ArrayList<String>>l_profileList=new ArrayList();
        
        try{
        
            Map<String,ArrayList<String>>profileMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER", "UVW_USER_PROFILE", session, dbSession);
            
            Iterator<ArrayList<String>>valueIterator=profileMap.values().iterator();
            
            while(valueIterator.hasNext()){
               ArrayList<String> profileRec=valueIterator.next();
               l_profileList.add(profileRec);
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
        
          dbg("end of  completed user profile--->view");   
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
    

    
    private void buildBOfromDB(ArrayList<ArrayList<String>>l_profileList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<UserProfileBO> reqBody = request.getReqBody();
           UserProfileBO userProfile =reqBody.get();
           String l_authStatus=userProfile.getFilter().getAuthStatus();
           String l_userID=userProfile.getFilter().getUserID();
           String l_userName=userProfile.getFilter().getUserName();
           
           dbg("l_authStatus"+l_authStatus);
           dbg("l_userID"+l_userID);
           
           if(l_userID!=null&&!l_userID.isEmpty()){
            
             List<ArrayList<String>>  l_userList=  l_profileList.stream().filter(rec->rec.get(0).trim().equals(l_userID)).collect(Collectors.toList());
             l_profileList = new ArrayList<ArrayList<String>>(l_userList);
             dbg("userID filter l_profileList size"+l_profileList.size());
           }
           
           if(l_userName!=null&&!l_userName.isEmpty()){
            
             List<ArrayList<String>>  l_userList=  l_profileList.stream().filter(rec->rec.get(1).trim().equals(l_userName)).collect(Collectors.toList());
             l_profileList = new ArrayList<ArrayList<String>>(l_userList);
             dbg("l_userName filter l_profileList size"+l_profileList.size());
           }

           
           if(l_authStatus!=null&&!l_authStatus.isEmpty()){
               
               List<ArrayList<String>>  l_userList=  l_profileList.stream().filter(rec->rec.get(9).trim().equals(l_authStatus)).collect(Collectors.toList());
               l_profileList = new ArrayList<ArrayList<String>>(l_userList);
               dbg("authStatus filter l_profileList size"+l_profileList.size());
               
           }
           
           
              l_profileList=   getInstituteFilteredRecords(l_profileList);
           
           
           userProfile.result=new UserProfileResult[l_profileList.size()];
           for(int i=0;i<l_profileList.size();i++){
               
               ArrayList<String> l_userProfileList=l_profileList.get(i);
               userProfile.result[i]=new UserProfileResult();
               userProfile.result[i].setUserID(l_userProfileList.get(0).trim());
               userProfile.result[i].setUserName(l_userProfileList.get(1).trim());
               userProfile.result[i].setEmailID(l_userProfileList.get(2).trim());
               userProfile.result[i].setMobileNo(l_userProfileList.get(3).trim());
               userProfile.result[i].setMakerID(l_userProfileList.get(4).trim());
               userProfile.result[i].setCheckerID(l_userProfileList.get(5).trim());
               userProfile.result[i].setMakerDateStamp(l_userProfileList.get(6).trim());
               userProfile.result[i].setCheckerDateStamp(l_userProfileList.get(7).trim());
               userProfile.result[i].setRecordStatus(l_userProfileList.get(8).trim());
               userProfile.result[i].setAuthStatus(l_userProfileList.get(9).trim());
               userProfile.result[i].setVersionNumber(l_userProfileList.get(10).trim());
               userProfile.result[i].setMakerRemarks(l_userProfileList.get(11).trim());
               userProfile.result[i].setCheckerRemarks(l_userProfileList.get(12).trim());
               userProfile.result[i].setUserType(l_userProfileList.get(13).trim());
          }    
           
           if(userProfile.result==null||userProfile.result.length==0){
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
    
    
    private ArrayList<ArrayList<String>>getInstituteFilteredRecords(ArrayList<ArrayList<String>>userSummayList)throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
        
        
        try{
           RequestBody<UserSearch> reqBody = request.getReqBody();
           String headerIns=request.getReqHeader().getInstituteID();
           IPDataService pds=inject.getPdataservice();
//           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           ArrayList<ArrayList<String>>filteredSearchList=new ArrayList();
            
           for(int i=0;i<userSummayList.size();i++){
               
               String homeIns=userSummayList.get(i).get(15).trim();
               String userType=userSummayList.get(i).get(13).trim();
               String userID=userSummayList.get(i).get(0).trim();
               dbg("homeIns"+homeIns);
               dbg("userType"+userType);
               dbg("userID"+userID);
               
               if(homeIns.equals(headerIns)){
                   
                   dbg("homeIns.equals(headerIns)");
                   filteredSearchList.add(userSummayList.get(i));
                   
                }else if(userType.equals("P")){
                       
                    Map<String,ArrayList<String>> l_parentStudentRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_PARENT_STUDENT_ROLEMAPPING",session,dbSession);
                    Map<String,List<ArrayList<String>>>parentFilteredRec=l_parentStudentRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec->rec.get(4).trim()));
                    dbg("parentFilteredRec  size"+parentFilteredRec.size());   
                    
                    if(parentFilteredRec.containsKey(headerIns)){
                    
                        dbg("parentFilteredRec.containsKey(headerIns)");
                        filteredSearchList.add(userSummayList.get(i));
                    }
                    
                }else if(userType.equals("A")||userType.equals("T")){
                    
                    Map<String,ArrayList<String>> l_classStudentRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_CLASS_ENTITY_ROLEMAPPING",session,dbSession);
                    Map<String,List<ArrayList<String>>>classFilteredRec=l_classStudentRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec->rec.get(5).trim()));
                    dbg("classFilteredRec size"+classFilteredRec.size());   
                    
                    
                    if(classFilteredRec.containsKey(headerIns)){
                    
                       dbg("classFilteredRec.containsKey(headerIns)");
                       filteredSearchList.add(userSummayList.get(i));
                      
                      
                    }else{
                        
                        Map<String,ArrayList<String>> l_teacherRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_TEACHER_ENTITY_ROLEMAPPING",session,dbSession);
                        Map<String,List<ArrayList<String>>>teacherFilteredRec=l_teacherRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec->rec.get(2).trim()));
                        dbg("teacherFilteredRec size"+teacherFilteredRec.size()); 
                        
                        if(teacherFilteredRec.containsKey(headerIns)){
                    
                           dbg("teacherFilteredRec.containsKey(headerIns)");
                            
                           filteredSearchList.add(userSummayList.get(i));
                        }else{
                            
                            Map<String,ArrayList<String>> l_instituteRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_INSTITUTE_ENTITY_ROLEMAPPING",session,dbSession);
                            Map<String,List<ArrayList<String>>>instituteFilteredRec=l_instituteRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec->rec.get(2).trim()));
                        
                            dbg("instituteFilteredRec size"+instituteFilteredRec.size()); 
                        
                            if(instituteFilteredRec.containsKey(headerIns)){
                    
                               dbg("instituteFilteredRec.containsKey(headerIns)");
                                
                               filteredSearchList.add(userSummayList.get(i));
                            }
                            
                            
                        }
                        
                    }
                    
                    
                    
                }
                   
              
               
           }
            
            
            dbg("end of instituteFilter-->filteredSearchList--->"+filteredSearchList.size());
            
            return filteredSearchList;
 
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
        JsonObject filter;
        try{
        dbg("inside user profile buildJsonResFromBO");    
        RequestBody<UserProfileBO> reqBody = request.getReqBody();
        UserProfileBO userProfile =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<userProfile.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("userID", userProfile.result[i].getUserID())
                                                      .add("userName", userProfile.result[i].getUserName())
                                                      .add("emailID", userProfile.result[i].getEmailID())
                                                      .add("mobileNo", userProfile.result[i].getMobileNo())
                                                      .add("userType", userProfile.result[i].getUserType())
                                                      .add("makerID", userProfile.result[i].getMakerID())
                                                      .add("checkerID", userProfile.result[i].getCheckerID())
                                                      .add("makerDateStamp", userProfile.result[i].getMakerDateStamp())
                                                      .add("checkerDateStamp", userProfile.result[i].getCheckerDateStamp())
                                                      .add("recordStatus", userProfile.result[i].getRecordStatus())
                                                      .add("authStatus", userProfile.result[i].getAuthStatus())
                                                      .add("versionNumber", userProfile.result[i].getVersionNumber())
                                                      .add("makerRemarks", userProfile.result[i].getMakerRemarks())
                                                      .add("checkerRemarks", userProfile.result[i].getCheckerRemarks()));
        }

           filter=Json.createObjectBuilder()  .add("userID",userProfile.filter.getUserID())
                                              .add("userName",userProfile.filter.getUserName())
                                              .add("authStat", userProfile.filter.getAuthStatus())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of user profile buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside user profile--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of user profile--->businessValidation"); 
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
        dbg("inside user profile master mandatory validation");
        RequestBody<UserProfileBO> reqBody = request.getReqBody();
        UserProfileBO userProfile =reqBody.get();
        int nullCount=0;
        
         if(userProfile.getFilter().getUserID()==null||userProfile.getFilter().getUserID().isEmpty()){
             nullCount++;
         }
         if(userProfile.getFilter().getUserName()==null||userProfile.getFilter().getUserName().isEmpty()){
             nullCount++;
         }

          if(userProfile.getFilter().getAuthStatus()==null||userProfile.getFilter().getAuthStatus().isEmpty()){
             nullCount++;
         }
          
         
          if(nullCount==4){
              status=false;
              errhandler.log_app_error("BS_VAL_002","One Filter value");
          }
          
        dbg("end of user profile master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside user profile detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<UserProfileBO> reqBody = request.getReqBody();
             UserProfileBO userProfile =reqBody.get();
             String l_userID=userProfile.getFilter().getUserID();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_authStatus=userProfile.getFilter().getAuthStatus();
             
//             if(l_userID!=null&&!l_userID.isEmpty()){
//                 
//                if(!bsv.userIDValidation(l_userID,errhandler, l_instituteID, inject, session, dbSession)){
//                    status=false;
//                    errhandler.log_app_error("BS_VAL_003","userID");
//                }
//                 
//             }

             
             if(l_authStatus!=null&&!l_authStatus.isEmpty()){
                 
                if(!bsv.authStatusValidation(l_authStatus, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","authStatus");
                }
                 
             }
   

             
            dbg("end of user profile detailDataValidation");
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
