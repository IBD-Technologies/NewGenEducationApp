/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.assignmentSearch;

import com.ibd.businessViews.IAssignmentSearchService;
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
@Remote(IAssignmentSearchService.class)
@Stateless
public class AssignmentSearchService implements IAssignmentSearchService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public AssignmentSearchService(){
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
       dbg("inside AssignmentSearchService--->processing");
       dbg("AssignmentSearchService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IAssignmentSearchService>assignmentSearchEJB=new BusinessEJB();
       assignmentSearchEJB.set(this);
      
       exAudit=bs.getExistingAudit(assignmentSearchEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,assignmentSearchEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,assignmentSearchEJB);
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
         /*  if(l_lockKey!=null){
               businessLock.removeBusinessLock(request, l_lockKey, session);
            }*/
           request=null;
            bs=inject.getBusinessService(session);
            if(l_session_created_now){
                bs.responselogging(jsonResponse, inject,session,dbSession);
                dbg(jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
                clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes              BSProcessingException ex=new BSProcessingException("response contains special characters");
            
               /* if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                   BSProcessingException ex=new BSProcessingException("response contains special characters");
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
      AssignmentSearch assignmentSearch=new AssignmentSearch();
      RequestBody<AssignmentSearch> reqBody = new RequestBody<AssignmentSearch>(); 
           
      try{
      dbg("inside assignment assignmentSearch buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      assignmentSearch.setSearchFilter(l_body.getString("searchFilter"));

      
        reqBody.set(assignmentSearch);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside assignment assignmentSearch--->view");
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IPDataService pds=inject.getPdataservice();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<AssignmentSearch> reqBody = request.getReqBody();
        ErrorHandler errorHandler=session.getErrorhandler();
        AssignmentSearch assignmentSearch =reqBody.get();
        String searchFilter=assignmentSearch.getSearchFilter();
        Map<String,DBRecord>assignmentSearchMap=null;
        
        try{
        
        
              assignmentSearchMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT","IVW_ASSIGNMENT", session, dbSession);
            
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
        
        
        
        
        
        dbg("assignmentSearchMap size"+assignmentSearchMap.size());
        buildBOfromDB(assignmentSearchMap);     
        
          dbg("end of  completed assignment assignmentSearch--->view");                
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
    

    
    private void buildBOfromDB(Map<String,DBRecord>assignmentSearchMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           IMetaDataService mds=inject.getMetadataservice();
           RequestBody<AssignmentSearch> reqBody = request.getReqBody();
           ErrorHandler errorHandler=session.getErrorhandler();
           AssignmentSearch assignmentSearch =reqBody.get();
           String searchFilter=assignmentSearch.getSearchFilter();
           dbg("searchFilter"+searchFilter);
           
           int recStatusColId=mds.getColumnMetaData("ASSIGNMENT", "IVW_ASSIGNMENT", "RECORD_STATUS", session).getI_ColumnID();
           int authStatusColId=mds.getColumnMetaData("ASSIGNMENT","IVW_ASSIGNMENT", "AUTH_STATUS", session).getI_ColumnID();
           List<DBRecord>authorizedRecords=assignmentSearchMap.values().stream().filter(rec->rec.getRecord().get(recStatusColId-1).trim().equals("O")).collect(Collectors.toList());   
           List<DBRecord>filteredRecords=  authorizedRecords.stream().filter(rec->rec.getRecord().get(2).trim().contains(searchFilter)||rec.getRecord().get(3).trim().contains(searchFilter)).collect(Collectors.toList()); 
           dbg("recStatusColId"+recStatusColId);
           dbg("authStatusColId"+authStatusColId);
           dbg("authorizedRecords"+authorizedRecords.size());
           if(filteredRecords!=null)
              dbg("filteredRecords size"+filteredRecords.size());
           else
               dbg("filteredRecords is null");
           
           if(searchFilter.equals("")){
           
               setRecordInAssignmentSearch(authorizedRecords);
               
           }else if(filteredRecords!=null&&!filteredRecords.isEmpty()){
               
               setRecordInAssignmentSearch(filteredRecords);
               
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
    
    private void setRecordInAssignmentSearch(List<DBRecord>assignmentSearchList)throws BSProcessingException{
        
        try{
           dbg("inside setRecordInAssignmentSearch"); 
           RequestBody<AssignmentSearch> reqBody = request.getReqBody();
           AssignmentSearch assignmentSearch =reqBody.get();
           int maxResultSize=Integer.parseInt(session.getCohesiveproperties().getProperty("MAX_SEARCH_RESULTS"));
            int resultSize=0;
               if(assignmentSearchList.size()<=maxResultSize){
                   
                   resultSize=assignmentSearchList.size();
               }else{
                   resultSize=maxResultSize;
               }
               
               assignmentSearch.result=new AssignmentSearchResult[resultSize];
               
               for(int i=0;i<resultSize;i++){
                   
                   String assignmentID=assignmentSearchList.get(i).getRecord().get(2).trim();
                   String assignmentDescription=assignmentSearchList.get(i).getRecord().get(3).trim();
                   dbg("setRecordInAssignmentSearch-->assignmentID"+assignmentID);
                   dbg("setRecordInAssignmentSearch-->assignmentDescription"+assignmentDescription);
                   assignmentSearch.result[i]=new AssignmentSearchResult();
                   assignmentSearch.result[i].setAssignmentID(assignmentID);
                   assignmentSearch.result[i].setDescription(assignmentDescription);
               }
            
            
        
        dbg("end of setRecordInAssignmentSearch");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
    
    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside assignment assignmentSearch buildJsonResFromBO");    
        RequestBody<AssignmentSearch> reqBody = request.getReqBody();
        AssignmentSearch assignmentSearch =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<assignmentSearch.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("assignmentID", assignmentSearch.result[i].getAssignmentID())
                                                      .add("assignmentDescription", assignmentSearch.result[i].getDescription()));
        }

             body=Json.createObjectBuilder()  .add("searchFilter", assignmentSearch.getSearchFilter())
                                              .add("searchResults", resultArray)
                                              .build();
               
           
                                            
              dbg(body.toString());  
           dbg("end of assignment assignmentSearch buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside assignment assignmentSearch--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }
       
       
       dbg("end of assignment assignmentSearch--->businessValidation"); 
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
        dbg("inside assignment assignmentSearch master mandatory validation");
        RequestBody<AssignmentSearch> reqBody = request.getReqBody();
        AssignmentSearch assignmentSearch =reqBody.get();
         
         
        
         
          
         
          
          
        dbg("end of assignment assignmentSearch master mandatory validation");
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
