
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.instituteSearch;

import com.ibd.businessViews.IInstituteSearchService;
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
@Remote(IInstituteSearchService.class)
@Stateless
public class InstituteSearchService implements IInstituteSearchService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public InstituteSearchService(){
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
       dbg("inside InstituteSearchService--->processing");
       dbg("InstituteSearchService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IInstituteSearchService>instituteSearchEJB=new BusinessEJB();
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
      InstituteSearch instituteSearch=new InstituteSearch();
      RequestBody<InstituteSearch> reqBody = new RequestBody<InstituteSearch>(); 
           
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
        RequestBody<InstituteSearch> reqBody = request.getReqBody();
        BusinessService bs=inject.getBusinessService(session);
        ErrorHandler errorHandler=session.getErrorhandler();
        InstituteSearch instituteSearch =reqBody.get();
        String searchFilter=instituteSearch.getSearchFilter();
        Map<String,ArrayList<String>>instituteSearchMap=null;
        String l_userID=request.getReqHeader().getUserID();
        
        String[] l_pkey={l_userID};
        ArrayList<String>l_userList=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User", "USER", "UVW_USER_PROFILE",l_pkey);
        String l_userType=l_userList.get(13).trim();
        
        
        try{

           Map<String,ArrayList<String>>fullMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE","INSTITUTE_MASTER", session, dbSession);
           

          if(l_userID.equals("System")||l_userID.equals("Admin")||l_userID.equals("Teacher")||l_userID.equals("Parent")){
              
              
              instituteSearchMap=fullMap;
              
          }else{
           
              
              String userMasterVersion=bs.getMaxVersionOfTheUser(l_userID, session, dbSession, inject);
              
              instituteSearchMap=new HashMap();
           if(l_userType.equals("P")){
               
              Map<String,ArrayList<String>> l_parentRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_PARENT_STUDENT_ROLEMAPPING",session,dbSession); 
               
              int versionIndex=bs.getVersionIndexOfTheTable("UVW_PARENT_STUDENT_ROLEMAPPING", "USER", session, dbSession, inject);
              
              
              Iterator<String>keyIterator=l_parentRoleMap.keySet().iterator();
              
              while(keyIterator.hasNext()){
                  
                  String key=keyIterator.next();
                  String instituteID=key.split("~")[2];
                  instituteSearchMap.put(instituteID, fullMap.get(instituteID));
                  
              }
              
           }else if(l_userType.equals("T")){
               
               Map<String,ArrayList<String>> l_teacherMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_TEACHER_ENTITY_ROLEMAPPING",session,dbSession); 
               
               
               dbg("printing of l_teacherMap starts");
               l_teacherMap.forEach((String k1,ArrayList<String> v1)->{
              dbg("key"+k1);
              v1.forEach((String l)->{
               dbg("values"+l);
              });
           });
               dbg("printing of l_teacherMap ends");
               
               
               List<ArrayList<String>> userFilteredList=  l_teacherMap.values().stream().filter(rec->rec.get(0).trim().equals(l_userID)).collect(Collectors.toList());
               
              
//              while(keyIterator.hasNext()){
//                  
//                  String key=keyIterator.next();
//                  String instituteID=key.split("~")[2];
//                  instituteSearchMap.put(instituteID, fullMap.get(instituteID));
//                  
//              }
               for(int i=0;i<userFilteredList.size();i++){
                   
                   ArrayList<String> instList=userFilteredList.get(i);
                      String instituteID=instList.get(2).trim();
                      dbg("user type A instituteID"+instituteID);
                      dbg(" fullMap.get(instituteID)"+ fullMap.get(instituteID));
                      instituteSearchMap.put(instituteID, fullMap.get(instituteID));

               }

               
           }else if(l_userType.equals("A")){
               
               Map<String,ArrayList<String>> l_instituteMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_INSTITUTE_ENTITY_ROLEMAPPING",session,dbSession); 
               
//              Iterator<String>keyIterator=l_instituteMap.keySet().iterator();
//              
//              while(keyIterator.hasNext()){
//                  
//                  String key=keyIterator.next();
//                  String instituteID=key.split("~")[2];
//                  dbg("user type A instituteID"+instituteID);
//                  instituteSearchMap.put(instituteID, fullMap.get(instituteID));
//                  
//              }

               List<ArrayList<String>> userFilteredList=  l_instituteMap.values().stream().filter(rec->rec.get(0).trim().equals(l_userID)).collect(Collectors.toList());
               
                  for(int i=0;i<userFilteredList.size();i++){
                      
                      
                      ArrayList<String> instList=userFilteredList.get(i);
                      String instituteID=instList.get(2).trim();
                      dbg("user type A instituteID"+instituteID);
                      dbg(" fullMap.get(instituteID)"+ fullMap.get(instituteID));
                      instituteSearchMap.put(instituteID, fullMap.get(instituteID));
                  
                 }
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
        
        dbg("instituteSearchMap size"+instituteSearchMap.size());
        buildBOfromDB(instituteSearchMap);     
        
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
    

    
    private void buildBOfromDB(Map<String,ArrayList<String>>instituteSearchMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           IMetaDataService mds=inject.getMetadataservice();
           RequestBody<InstituteSearch> reqBody = request.getReqBody();
           ErrorHandler errorHandler=session.getErrorhandler();
           InstituteSearch instituteSearch =reqBody.get();
           String searchFilter=instituteSearch.getSearchFilter();
           dbg("searchFilter"+searchFilter);
           
           int recStatusColId=mds.getColumnMetaData("INSTITUTE", "INSTITUTE_MASTER", "RECORD_STATUS", session).getI_ColumnID();
           
           instituteSearchMap.forEach((String k1,ArrayList<String> v1)->{
              dbg("key"+k1);
              v1.forEach((String l)->{
               dbg("values"+l);
              });
           });
           
           //List<ArrayList<String>>authorizedRecords=instituteSearchMap.values().stream().filter(rec->rec.get(recStatusColId-1).trim().equals("O")&&rec.get(authStatusColId-1).trim().equals("A")).collect(Collectors.toList());   
           List<ArrayList<String>>authorizedRecords=instituteSearchMap.values().stream().filter(rec->rec.get(recStatusColId-1).trim().equals("O")).collect(Collectors.toList());//Integration changes   
           
           if(authorizedRecords!=null){
               
               dbg("authorizedRecords"+authorizedRecords.size());
           }else{
               
               
               dbg("authorizedRecords is null");
           }
           
           
           List<ArrayList<String>>filteredRecords=  authorizedRecords.stream().filter(rec->rec.get(0).trim().contains(searchFilter)||rec.get(1).trim().contains(searchFilter)).collect(Collectors.toList()); 
           dbg("recStatusColId"+recStatusColId);
           
           
           
           
           
           if(filteredRecords!=null)
              dbg("filteredRecords size"+filteredRecords.size());
           else
               dbg("filteredRecords is null");
           
           if(searchFilter.equals("")){
           
               setRecordInInstituteSearch(authorizedRecords);
               
           }else if(filteredRecords!=null&&!filteredRecords.isEmpty()){
               
               setRecordInInstituteSearch(filteredRecords);
               
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
    
    private void setRecordInInstituteSearch(List<ArrayList<String>>instituteSearchList)throws BSProcessingException,DBValidationException,BSValidationException,DBProcessingException{
        
        try{
           dbg("inside setRecordInInstituteSearch"); 
           IPDataService pds=inject.getPdataservice();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           String userID=request.getReqHeader().getUserID();
           RequestBody<InstituteSearch> reqBody = request.getReqBody();
           ErrorHandler errorHandler=session.getErrorhandler();
           InstituteSearch instituteSearch =reqBody.get();
           String searchFilter=instituteSearch.getSearchFilter();
           Map<String,ArrayList<String>> l_instituteRoleMap=null;
           
//           try{
//           
//             l_instituteRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_INSTITUTE_ENTITY_ROLEMAPPING",session,dbSession); 
//        
//           }catch(DBValidationException  ex){
//            
//            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
//                
//                errorHandler.removeSessionErrCode("DB_VAL_011");
//                errorHandler.removeSessionErrCode("DB_VAL_000");
//                errorHandler.log_app_error("BS_VAL_020", searchFilter);
//                throw new BSValidationException("BSValidationException");
//                
//            }else{
//                throw ex;
//            }
//            
//        }
//           
//           Map<String,List<ArrayList<String>>> accessibleInstitutes=l_instituteRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec->rec.get(2).trim()));
//          
//           if(accessibleInstitutes==null||accessibleInstitutes.isEmpty()){
//               errorHandler.log_app_error("BS_VAL_020", searchFilter);
//               throw new BSValidationException("BSValidationException");
//           }
           
           
           ArrayList<String>filteredList=new ArrayList();
//           dbg("accessibleInstitutes size"+accessibleInstitutes.keySet().size());
           
           for(int i=0;i<instituteSearchList.size();i++){
               
               String instituteID=instituteSearchList.get(i).get(0).trim();
               String instituteName=instituteSearchList.get(i).get(1).trim();
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

                       instituteSearch.result=new InstituteSearchResult[resultSize];

                       for(int i=0;i<resultSize;i++){
                            
                           String institute=filteredList.get(i);
                           String instituteID=institute.split("~")[0].trim();
                           String instituteName=institute.split("~")[1].trim();
                           dbg("setRecordInInstituteSearch-->instituteID"+instituteID);
                           dbg("setRecordInInstituteSearch-->instituteName"+instituteName);
                           instituteSearch.result[i]=new InstituteSearchResult();
                           instituteSearch.result[i].setInstituteID(instituteID);
                           instituteSearch.result[i].setInstituteName(instituteName);
                       }
           }else{
               
                 errorHandler.log_app_error("BS_VAL_020", searchFilter);
                 throw new BSValidationException("BSValidationException");
           }
            
        
        dbg("end of setRecordInInstituteSearch");
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
        RequestBody<InstituteSearch> reqBody = request.getReqBody();
        InstituteSearch instituteSearch =reqBody.get();
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
        RequestBody<InstituteSearch> reqBody = request.getReqBody();
        InstituteSearch instituteSearch =reqBody.get();
         
         
        
         
          
         
          
          
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
