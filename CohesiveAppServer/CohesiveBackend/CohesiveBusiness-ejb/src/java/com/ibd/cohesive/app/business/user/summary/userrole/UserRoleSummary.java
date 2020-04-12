/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.user.summary.userrole;

import com.ibd.businessViews.IUserRoleSummary;
import com.ibd.businessViews.businessUtils.ExistingAudit;
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
//@Local(IUserRoleSummary.class)
@Remote(IUserRoleSummary.class)
@Stateless
public class UserRoleSummary implements IUserRoleSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public UserRoleSummary(){
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
       dbg("inside UserRoleSummary--->processing");
       dbg("UserRoleSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IUserRoleSummary>userRoleEJB=new BusinessEJB();
       userRoleEJB.set(this);
      
       exAudit=bs.getExistingAudit(userRoleEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,userRoleEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,userRoleEJB);
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
      UserRoleBO userRole=new UserRoleBO();
      RequestBody<UserRoleBO> reqBody = new RequestBody<UserRoleBO>(); 
           
      try{
      dbg("inside user profile buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      userRole.filter=new UserRoleFilter();
      userRole.filter.setRoleID(l_filterObject.getString("roleID"));
//      userRole.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      userRole.filter.setAuthStatus(l_filterObject.getString("authStat"));

      if(l_filterObject.getString("authStat").equals("Select option")){
          
          userRole.filter.setAuthStatus("");
      }else{
      
          userRole.filter.setAuthStatus(l_filterObject.getString("authStat"));
      }


      
        reqBody.set(userRole);
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
        String l_instituteID=request.getReqHeader().getInstituteID();
//        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
//        ArrayList<DBRecord>l_profileList=new ArrayList();
        ArrayList<DBRecord>l_profileList=new ArrayList();
        IPDataService pds=inject.getPdataservice();
        
        try{

//            Map<String,DBRecord>profileMap=readBuffer.readTable("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER", "UVW_USER_ROLE_MASTER", session, dbSession);
//            
//            Iterator<DBRecord>valueIterator=profileMap.values().iterator();
//            
//            while(valueIterator.hasNext()){
//               DBRecord profileRec=valueIterator.next();
//               l_profileList.add(profileRec);
//            }

          if(l_instituteID.equals("System")){
              
              Map<String,ArrayList<String>> roleInstituteMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_ROLE_MASTER", session, dbSession);
              
              Iterator<ArrayList<String>>valueIterator=roleInstituteMap.values().iterator();
              
              while(valueIterator.hasNext()){
                  
                  ArrayList<String> roleRecord=valueIterator.next();
                  DBRecord dbRec=new DBRecord(roleRecord);
                  l_profileList.add(dbRec);
                  
              }
              
              
          }else{


              Map<String,ArrayList<String>> roleInstituteMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_ROLE_INSTITUTE", session, dbSession);
              dbg("roleInstituteMap"+roleInstituteMap.size());
              Map<String,List<ArrayList<String>>>instituteFilteredMap= roleInstituteMap.values().stream().filter(rec->rec.get(1).trim().equals(l_instituteID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
              dbg("instituteFilteredMap"+instituteFilteredMap.size());  
              Iterator<String>roleIterator=instituteFilteredMap.keySet().iterator();

              while(roleIterator.hasNext()){

                  String roleID=roleIterator.next();
                  dbg("roleID"+roleID);
                  String[] pkey={roleID};

                  ArrayList<String>roleRecord=pds.readRecordPData(session, dbSession, "USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_ROLE_MASTER", pkey);

                  DBRecord dbRec=new DBRecord(roleRecord);
                  l_profileList.add(dbRec);

              }

              dbg("l_profileList"+l_profileList.size());  
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
    

    
    private void buildBOfromDB(ArrayList<DBRecord>l_profileList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<UserRoleBO> reqBody = request.getReqBody();
           UserRoleBO userRole =reqBody.get();
           String l_authStatus=userRole.getFilter().getAuthStatus();
           String l_roleID=userRole.getFilter().getRoleID();
           
           dbg("l_authStatus"+l_authStatus);
           dbg("l_roleID"+l_roleID);
           
           if(l_roleID!=null&&!l_roleID.isEmpty()){
            
             List<DBRecord>  l_userList=  l_profileList.stream().filter(rec->rec.getRecord().get(0).trim().equals(l_roleID)).collect(Collectors.toList());
             l_profileList = new ArrayList<DBRecord>(l_userList);
             dbg("l_roleID filter l_profileList size"+l_profileList.size());
           }

           
           if(l_authStatus!=null&&!l_authStatus.isEmpty()){
               
               List<DBRecord>  l_userList=  l_profileList.stream().filter(rec->rec.getRecord().get(7).trim().equals(l_authStatus)).collect(Collectors.toList());
               l_profileList = new ArrayList<DBRecord>(l_userList);
               dbg("authStatus filter l_profileList size"+l_profileList.size());
               
           }
           
           userRole.result=new UserRoleResult[l_profileList.size()];
           for(int i=0;i<l_profileList.size();i++){
               
               ArrayList<String> l_userRoleList=l_profileList.get(i).getRecord();
               userRole.result[i]=new UserRoleResult();
               userRole.result[i].setRoleID(l_userRoleList.get(0).trim());
               userRole.result[i].setRoleDescription(l_userRoleList.get(1).trim());
               userRole.result[i].setMakerID(l_userRoleList.get(2).trim());
               userRole.result[i].setCheckerID(l_userRoleList.get(3).trim());
               userRole.result[i].setMakerDateStamp(l_userRoleList.get(4).trim());
               userRole.result[i].setCheckerDateStamp(l_userRoleList.get(5).trim());
               userRole.result[i].setRecordStatus(l_userRoleList.get(6).trim());
               userRole.result[i].setAuthStatus(l_userRoleList.get(7).trim());
               userRole.result[i].setVersionNumber(l_userRoleList.get(8).trim());
               userRole.result[i].setMakerRemarks(l_userRoleList.get(9).trim());
               userRole.result[i].setCheckerRemarks(l_userRoleList.get(10).trim());
          }    
           
           
           if(userRole.result==null||userRole.result.length==0){
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
        dbg("inside user profile buildJsonResFromBO");    
        RequestBody<UserRoleBO> reqBody = request.getReqBody();
        UserRoleBO userRole =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<userRole.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("roleID", userRole.result[i].getRoleID())
                                                      .add("roleDescription", userRole.result[i].getRoleDescription())
                                                      .add("makerID", userRole.result[i].getMakerID())
                                                      .add("checkerID", userRole.result[i].getCheckerID())
                                                      .add("makerDateStamp", userRole.result[i].getMakerDateStamp())
                                                      .add("checkerDateStamp", userRole.result[i].getCheckerDateStamp())
                                                      .add("recordStatus", userRole.result[i].getRecordStatus())
                                                      .add("authStatus", userRole.result[i].getAuthStatus())
                                                      .add("versionNumber", userRole.result[i].getVersionNumber())
                                                      .add("makerRemarks", userRole.result[i].getMakerRemarks())
                                                      .add("checkerRemarks", userRole.result[i].getCheckerRemarks()));
        }

           filter=Json.createObjectBuilder()  .add("roleID",userRole.filter.getRoleID())
                                              .add("authStat", userRole.filter.getAuthStatus())
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
        RequestBody<UserRoleBO> reqBody = request.getReqBody();
        UserRoleBO userRole =reqBody.get();
        int nullCount=0;
        
         if(userRole.getFilter().getRoleID()==null||userRole.getFilter().getRoleID().isEmpty()){
             nullCount++;
         }

          if(userRole.getFilter().getAuthStatus()==null||userRole.getFilter().getAuthStatus().isEmpty()){
             nullCount++;
         }
          
         
          if(nullCount==2){
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
             RequestBody<UserRoleBO> reqBody = request.getReqBody();
             UserRoleBO userRole =reqBody.get();
             String l_roleID=userRole.getFilter().getRoleID();
             String l_authStatus=userRole.getFilter().getAuthStatus();
             
//             if(l_roleID!=null&&!l_roleID.isEmpty()){
//                 
//                if(!bsv.roleIDValidation(l_roleID, session, dbSession, inject)){
//                    status=false;
//                    errhandler.log_app_error("BS_VAL_003","roleID");
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
