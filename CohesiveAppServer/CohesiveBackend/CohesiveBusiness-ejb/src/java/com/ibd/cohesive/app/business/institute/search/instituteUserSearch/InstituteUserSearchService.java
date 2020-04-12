/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.instituteUserSearch;

import com.ibd.businessViews.IInstituteUserSearchService;
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
import java.util.HashMap;
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
@Remote(IInstituteUserSearchService.class)
@Stateless
public class InstituteUserSearchService implements IInstituteUserSearchService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public InstituteUserSearchService(){
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
       //String l_lockKey=null;
       //IBusinessLockService businessLock=null;
       try{
       session.createSessionObject();
       dbSession.createDBsession(session);
       l_session_created_now=session.isI_session_created_now();
       ErrorHandler errhandler = session.getErrorhandler();
       BSValidation bsv=inject.getBsv(session);
       bs=inject.getBusinessService(session);
       ITransactionControlService tc=inject.getTransactionControlService();
       //businessLock=inject.getBusinessLockService();
       dbg("inside InstituteUserSearchService--->processing");
       dbg("InstituteUserSearchService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IInstituteUserSearchService>instituteSearchEJB=new BusinessEJB();
       instituteSearchEJB.set(this);
      
       exAudit=bs.getExistingAudit(instituteSearchEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,instituteSearchEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,instituteSearchEJB);
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
      InstituteUserSearch instituteSearch=new InstituteUserSearch();
      RequestBody<InstituteUserSearch> reqBody = new RequestBody<InstituteUserSearch>(); 
           
      try{
      dbg("inside institute instituteSearch buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      instituteSearch.setSearchFilter(l_body.getString("searchFilter"));

      
        reqBody.set(instituteSearch);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside institute instituteSearch--->view");
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IPDataService pds=inject.getPdataservice();
        RequestBody<InstituteUserSearch> reqBody = request.getReqBody();
        ErrorHandler errorHandler=session.getErrorhandler();
        InstituteUserSearch instituteSearch =reqBody.get();
        String searchFilter=instituteSearch.getSearchFilter();
        BusinessService bs=inject.getBusinessService(session);
        ArrayList<String>instituteSearchList=new ArrayList();
        String l_userID=request.getReqHeader().getUserID();
        
        try{

            if(!l_userID.equals("System")){
            
            
                   Map<String,ArrayList<String>>userContactMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_CONTRACT_MASTER", session, dbSession);
                   dbg("userContactMap size"+userContactMap.size());
                   Map<String,List<ArrayList<String>>>contactGroup=   userContactMap.values().stream().filter(rec->rec.get(0).trim().equals(l_userID)).collect(Collectors.groupingBy(rec->rec.get(1).trim()));
                   dbg("contactGroup size"+contactGroup.size());
                   Iterator<String>contractIterator=contactGroup.keySet().iterator();

                   while(contractIterator.hasNext()){

                       String contractID=contractIterator.next();
                       dbg("contractID"+contractID);

                       Map<String,ArrayList<String>>instituteContactMap=pds.readTablePData("APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive","APP","CONTRACT_MASTER", session, dbSession);
                       dbg("instituteContactMap size"+instituteContactMap.size());
                       Map<String,List<ArrayList<String>>>instituteContactGroup=   instituteContactMap.values().stream().filter(rec->rec.get(1).trim().equals(contractID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
                       dbg("instituteContactGroup size"+instituteContactGroup.size());

                       Iterator<String>instituteIterator=instituteContactGroup.keySet().iterator();

                       while(instituteIterator.hasNext()){

                           String instituteID=instituteIterator.next();
                           dbg("instituteID"+instituteID);
                           String instituteName=bs.getInstituteName(instituteID, session, dbSession, inject);
                           instituteSearchList.add(instituteID+"~"+instituteName);

                       }
                   }
            }else{
                
               ArrayList<String>institutes= bs.getAllInstitutes(session, dbSession, inject);
                
               for(int i=0;i<institutes.size();i++){
                   
                   String instituteID=institutes.get(i);
                   String instituteName=bs.getInstituteName(instituteID, session, dbSession, inject);
                   instituteSearchList.add(instituteID+"~"+instituteName);
               } 
                
            }
        
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
        
        dbg("instituteSearchList size"+instituteSearchList.size());
        buildBOfromDB(instituteSearchList);     
        
          dbg("end of  completed institute instituteSearch--->view");                
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
    

    
    private void buildBOfromDB(ArrayList<String>instituteSearchList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<InstituteUserSearch> reqBody = request.getReqBody();
           ErrorHandler errorHandler=session.getErrorhandler();
           InstituteUserSearch instituteSearch =reqBody.get();
           String searchFilter=instituteSearch.getSearchFilter();
           dbg("searchFilter"+searchFilter);
           
           List<String>authorizedRecords=instituteSearchList.stream().collect(Collectors.toList());//Integration changes   
           
           List<String>filteredRecords=  authorizedRecords.stream().filter(rec->rec.split("~")[0].contains(searchFilter)||rec.split("~")[1].contains(searchFilter)).collect(Collectors.toList()); 
           dbg("authorizedRecords"+authorizedRecords.size());
           if(filteredRecords!=null)
              dbg("filteredRecords size"+filteredRecords.size());
           else
               dbg("filteredRecords is null");
           
           if(searchFilter.equals("")){
           
               setRecordInInstituteUserSearch(authorizedRecords);
               
           }else if(filteredRecords!=null&&!filteredRecords.isEmpty()){
               
               setRecordInInstituteUserSearch(filteredRecords);
               
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
    
    private void setRecordInInstituteUserSearch(List<String>instituteSearchList)throws BSProcessingException,DBValidationException,BSValidationException,DBProcessingException{
        
        try{
           dbg("inside setRecordInInstituteUserSearch"); 
           RequestBody<InstituteUserSearch> reqBody = request.getReqBody();
           ErrorHandler errorHandler=session.getErrorhandler();
           InstituteUserSearch instituteSearch =reqBody.get();
           String searchFilter=instituteSearch.getSearchFilter();
           
           
           ArrayList<String>filteredList=new ArrayList();
           
           for(int i=0;i<instituteSearchList.size();i++){
               
               String instituteID=instituteSearchList.get(i).split("~")[0].trim();
               String instituteName=instituteSearchList.get(i).split("~")[1].trim();
               dbg("instituteID"+instituteID);
               dbg("instituteName"+instituteName);
               
//               if(accessibleInstitutes.containsKey(instituteID)){
                   
                   filteredList.add(instituteID+"~"+instituteName);
                   
//               }
               
           }
           
           if(filteredList.isEmpty()){
               errorHandler.log_app_error("BS_VAL_020", searchFilter);
               throw new BSValidationException("BSValidationException");
           }
           
           
           dbg("filteredList size"+filteredList.size());
           
           if(!filteredList.isEmpty()){
           
                   int maxResultSize=Integer.parseInt(session.getCohesiveproperties().getProperty("MAX_SEARCH_RESULTS"));
                    int resultSize=0;
                       if(filteredList.size()<=maxResultSize){

                           resultSize=filteredList.size();
                       }else{
                           resultSize=maxResultSize;
                       }

                       instituteSearch.result=new InstituteUserSearchResult[resultSize];

                       for(int i=0;i<resultSize;i++){
                            
                           String institute=filteredList.get(i);
                           String instituteID=institute.split("~")[0].trim();
                           String instituteName=institute.split("~")[1].trim();
                           dbg("setRecordInInstituteUserSearch-->instituteID"+instituteID);
                           dbg("setRecordInInstituteUserSearch-->instituteName"+instituteName);
                           instituteSearch.result[i]=new InstituteUserSearchResult();
                           instituteSearch.result[i].setInstituteID(instituteID);
                           instituteSearch.result[i].setInstituteName(instituteName);
                       }
           }else{
               
                 errorHandler.log_app_error("BS_VAL_020", searchFilter);
                 throw new BSValidationException("BSValidationException");
           }
            
        
        dbg("end of setRecordInInstituteUserSearch");
//        }catch(DBValidationException ex){
//            throw ex;
        }catch(BSValidationException ex){
            throw ex;    
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
    
    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside institute instituteSearch buildJsonResFromBO");    
        RequestBody<InstituteUserSearch> reqBody = request.getReqBody();
        InstituteUserSearch instituteSearch =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<instituteSearch.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("instituteID", instituteSearch.result[i].getInstituteID())
                                                      .add("instituteName", instituteSearch.result[i].getInstituteName()));
        }

             body=Json.createObjectBuilder()  .add("searchFilter", instituteSearch.getSearchFilter())
                                              .add("searchResults", resultArray)
                                              .build();
               
           
                                            
              dbg(body.toString());  
           dbg("end of institute instituteSearch buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside institute instituteSearch--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }
       
       
       dbg("end of institute instituteSearch--->businessValidation"); 
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
        dbg("inside institute instituteSearch master mandatory validation");
        RequestBody<InstituteUserSearch> reqBody = request.getReqBody();
        InstituteUserSearch instituteSearch =reqBody.get();
         
         
        
         
          
         
          
          
        dbg("end of institute instituteSearch master mandatory validation");
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
