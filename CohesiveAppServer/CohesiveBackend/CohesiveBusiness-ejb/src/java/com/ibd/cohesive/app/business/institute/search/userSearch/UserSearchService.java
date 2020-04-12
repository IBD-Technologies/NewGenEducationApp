/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.userSearch;

import com.ibd.businessViews.IUserSearchService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.exception.ExceptionHandler;
import com.ibd.cohesive.app.business.util.message.request.Parsing;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
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
@Remote(IUserSearchService.class)
@Stateless
public class UserSearchService implements IUserSearchService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public UserSearchService(){
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
       dbg("inside UserSearchService--->processing");
       dbg("UserSearchService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IUserSearchService>userSearchEJB=new BusinessEJB();
       userSearchEJB.set(this);
      
       exAudit=bs.getExistingAudit(userSearchEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,userSearchEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,userSearchEJB);
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
            clonedResponse=bs.cloneResponseJsonObject(jsonResponse);  
            dbg("Response"+jsonResponse.toString());
            BSValidation bsv=inject.getBsv(session);
            //if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                      clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes  
            //BSProcessingException ex=new BSProcessingException("response contains special characters");
              //  dbg(ex);
                
                //session.clearSessionObject();
               //dbSession.clearSessionObject();
               //throw ex;
               
            //}
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
      UserSearch userSearch=new UserSearch();
      RequestBody<UserSearch> reqBody = new RequestBody<UserSearch>(); 
           
      try{
      dbg("inside user userSearch buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      userSearch.setSearchFilter(l_body.getString("searchFilter"));

      
        reqBody.set(userSearch);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside user userSearch--->view");
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IPDataService pds=inject.getPdataservice();
        RequestBody<UserSearch> reqBody = request.getReqBody();
        ErrorHandler errorHandler=session.getErrorhandler();
        UserSearch userSearch =reqBody.get();
        String searchFilter=userSearch.getSearchFilter();
        Map<String,ArrayList<String>>userSearchMap=null;

        try{
        
           userSearchMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_PROFILE", session, dbSession);
            
           
         }catch(DBValidationException  ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                errorHandler.removeSessionErrCode("DB_VAL_011");
                errorHandler.removeSessionErrCode("DB_VAL_000");
                errorHandler.log_app_error("BS_VAL_020", searchFilter);
                throw new BSValidationException("BSValidationException");
                
            }else{
                throw ex;
            }
            
        }   
           
        dbg("userSearchMap size"+userSearchMap.size());
        buildBOfromDB(userSearchMap);     
        
          dbg("end of  completed user userSearch--->view");                
        }catch(DBValidationException ex){
            throw ex;
        }catch(BSValidationException ex){
            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
    

    
    private void buildBOfromDB(Map<String,ArrayList<String>>userSearchMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           IMetaDataService mds=inject.getMetadataservice();
           RequestBody<UserSearch> reqBody = request.getReqBody();
           ErrorHandler errorHandler=session.getErrorhandler();
           UserSearch userSearch =reqBody.get();
           String searchFilter=userSearch.getSearchFilter();
           dbg("searchFilter"+searchFilter);
           
           int recStatusColId=mds.getColumnMetaData("USER", "UVW_USER_PROFILE", "RECORD_STATUS", session).getI_ColumnID();
           int authStatusColId=mds.getColumnMetaData("USER","UVW_USER_PROFILE", "AUTH_STATUS", session).getI_ColumnID();
           List<ArrayList<String>>authorizedRecords=userSearchMap.values().stream().filter(rec->rec.get(recStatusColId-1).trim().equals("O")).collect(Collectors.toList());   
      
           if(authorizedRecords==null||authorizedRecords.isEmpty()){
               errorHandler.log_app_error("BS_VAL_020", searchFilter);
               throw new BSValidationException("BSValidationException");
           }
           
           
           List<ArrayList<String>>filteredRecords=  authorizedRecords.stream().filter(rec->rec.get(0).trim().contains(searchFilter)||rec.get(1).trim().contains(searchFilter)).collect(Collectors.toList()); 
           dbg("recStatusColId"+recStatusColId);
           dbg("authStatusColId"+authStatusColId);
           dbg("authorizedRecords"+authorizedRecords.size());
           if(filteredRecords!=null)
              dbg("filteredRecords size"+filteredRecords.size());
           else
               dbg("filteredRecords is null");
           
           if(searchFilter.equals("")){
           
               setRecordInUserSearch(authorizedRecords);
               
           }else if(filteredRecords!=null&&!filteredRecords.isEmpty()){
               
               setRecordInUserSearch(filteredRecords);
               
           }else{
               
               errorHandler.log_app_error("BS_VAL_020", searchFilter);
               throw new BSValidationException("BSValidationException");
           }
           
         
           
          dbg("end of  buildBOfromDB"); 
           
        }catch(DBValidationException ex){
            throw ex;
        }catch(BSValidationException ex){
            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
 }
    
    private void setRecordInUserSearch(List<ArrayList<String>>userSearchList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
        
        try{
           dbg("inside setRecordInUserSearch"); 
           RequestBody<UserSearch> reqBody = request.getReqBody();
           UserSearch userSearch =reqBody.get();
           String headerIns=request.getReqHeader().getInstituteID();
           IPDataService pds=inject.getPdataservice();
//           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           List<ArrayList<String>>filteredSearchList=new ArrayList();
           
           
           for(int i=0;i<userSearchList.size();i++){
               
               String homeIns=userSearchList.get(i).get(15).trim();
               String userType=userSearchList.get(i).get(13).trim();
               String userID=userSearchList.get(i).get(0).trim();
               dbg("homeIns"+homeIns);
               dbg("userType"+userType);
               dbg("userID"+userID);
               
               if(homeIns.equals(headerIns)){
                   
                   dbg("homeIns.equals(headerIns)");
                   filteredSearchList.add(userSearchList.get(i));
                   
                }else if(userType.equals("P")){
                       
                    Map<String,ArrayList<String>> l_parentStudentRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_PARENT_STUDENT_ROLEMAPPING",session,dbSession);
                    Map<String,List<ArrayList<String>>>parentFilteredRec=l_parentStudentRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec->rec.get(4).trim()));
                    dbg("parentFilteredRec  size"+parentFilteredRec.size());   
                    
                    if(parentFilteredRec.containsKey(headerIns)){
                    
                        dbg("parentFilteredRec.containsKey(headerIns)");
                        filteredSearchList.add(userSearchList.get(i));
                    }
                    
                }else if(userType.equals("A")||userType.equals("T")){
                    
                    Map<String,ArrayList<String>> l_classStudentRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_CLASS_ENTITY_ROLEMAPPING",session,dbSession);
                    Map<String,List<ArrayList<String>>>classFilteredRec=l_classStudentRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec->rec.get(5).trim()));
                    dbg("classFilteredRec size"+classFilteredRec.size());   
                    
                    
                    if(classFilteredRec.containsKey(headerIns)){
                    
                       dbg("classFilteredRec.containsKey(headerIns)");
                       filteredSearchList.add(userSearchList.get(i));
                      
                      
                    }else{
                        
                        Map<String,ArrayList<String>> l_teacherRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_TEACHER_ENTITY_ROLEMAPPING",session,dbSession);
                        Map<String,List<ArrayList<String>>>teacherFilteredRec=l_teacherRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec->rec.get(2).trim()));
                        dbg("teacherFilteredRec size"+teacherFilteredRec.size()); 
                        
                        if(teacherFilteredRec.containsKey(headerIns)){
                    
                           dbg("teacherFilteredRec.containsKey(headerIns)");
                            
                           filteredSearchList.add(userSearchList.get(i));
                        }else{
                            
                            Map<String,ArrayList<String>> l_instituteRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_INSTITUTE_ENTITY_ROLEMAPPING",session,dbSession);
                            Map<String,List<ArrayList<String>>>instituteFilteredRec=l_instituteRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec->rec.get(2).trim()));
                        
                            dbg("instituteFilteredRec size"+instituteFilteredRec.size()); 
                        
                            if(instituteFilteredRec.containsKey(headerIns)){
                    
                               dbg("instituteFilteredRec.containsKey(headerIns)");
                                
                               filteredSearchList.add(userSearchList.get(i));
                            }
                            
                            
                        }
                        
                    }
                    
                    
                    
                }
                   
              
               
           }
                             
           
           dbg("filteredSearchList size"+filteredSearchList.size());
           
           if(filteredSearchList==null||filteredSearchList.isEmpty()){
               session.getErrorhandler().log_app_error("BS_VAL_020", "");
               throw new BSValidationException("BSValidationException");
           }
             
           int maxResultSize=Integer.parseInt(session.getCohesiveproperties().getProperty("MAX_SEARCH_RESULTS"));
            int resultSize=0;
               if(filteredSearchList.size()<=maxResultSize){
                   
                   resultSize=filteredSearchList.size();
               }else{
                   resultSize=maxResultSize;
               }
               
               userSearch.result=new UserSearchResult[resultSize];
               
               for(int i=0;i<resultSize;i++){
                   
                   String userID=filteredSearchList.get(i).get(0).trim();
                   String userName=filteredSearchList.get(i).get(1).trim();
                   dbg("setRecordInUserSearch-->userID"+userID);
                   dbg("setRecordInUserSearch-->userName"+userName);
                   userSearch.result[i]=new UserSearchResult();
                   userSearch.result[i].setUserID(userID);
                   userSearch.result[i].setUserName(userName);
               }
            
            
        
        dbg("end of setRecordInUserSearch");
        }catch(DBValidationException ex){
            throw ex;
        }catch(BSValidationException ex){
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
        dbg("inside user userSearch buildJsonResFromBO");    
        RequestBody<UserSearch> reqBody = request.getReqBody();
        UserSearch userSearch =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<userSearch.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("userID", userSearch.result[i].getUserID())
                                                      .add("userName", userSearch.result[i].getUserName()));
        }

             body=Json.createObjectBuilder()  .add("searchFilter", userSearch.getSearchFilter())
                                              .add("searchResults", resultArray)
                                              .build();
               
           
                                            
              dbg(body.toString());  
           dbg("end of user userSearch buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside user userSearch--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }
       
       
       dbg("end of user userSearch--->businessValidation"); 
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
    private boolean filterMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
      boolean status=true;
        try{
        dbg("inside user userSearch master mandatory validation");
        RequestBody<UserSearch> reqBody = request.getReqBody();
        UserSearch userSearch =reqBody.get();
         
         
        
         
          
         
          
          
        dbg("end of user userSearch master mandatory validation");
        }catch (Exception ex) {
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
