/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.groupMapping;

import com.ibd.businessViews.IGroupMappingSummary;
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
//@Local(IGroupMappingSummary.class)
@Remote(IGroupMappingSummary.class)
@Stateless
public class GroupMappingSummary implements IGroupMappingSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public GroupMappingSummary(){
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
       dbg("inside GroupMappingSummary--->processing");
       dbg("GroupMappingSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IGroupMappingSummary>groupMappingEJB=new BusinessEJB();
       groupMappingEJB.set(this);
      
       exAudit=bs.getExistingAudit(groupMappingEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,groupMappingEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,groupMappingEJB);
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
      GroupMappingBO groupMapping=new GroupMappingBO();
      RequestBody<GroupMappingBO> reqBody = new RequestBody<GroupMappingBO>(); 
           
      try{
      dbg("inside student groupMapping buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      groupMapping.filter=new GroupMappingFilter();
//      groupMapping.filter.setInstituteID(l_filterObject.getString("instituteID"));
//      groupMapping.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      groupMapping.filter.setAuthStatus(l_filterObject.getString("authStat"));

        if(l_filterObject.getString("authStat").equals("Select option")){
          
          groupMapping.filter.setAuthStatus("");
        }else{
      
          groupMapping.filter.setAuthStatus(l_filterObject.getString("authStat"));
        }
      
      
        reqBody.set(groupMapping);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student groupMapping--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<GroupMappingBO> reqBody = request.getReqBody();
//        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<ArrayList<String>>l_groupMappingList=new ArrayList();
        IPDataService pds=inject.getPdataservice();
//        GroupMappingBO groupMapping=reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();
        
        try{
                ArrayList<String>l_fileNames=bs.getInstituteFileNames(l_instituteID,request,session,dbSession,inject);
                for(int i=0;i<l_fileNames.size();i++){
                    dbg("inside file name iteration");
                    String l_fileName=l_fileNames.get(i);
                    dbg("l_fileName"+l_fileName);
                    Map<String,ArrayList<String>>groupMappingMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_GROUP_MAPPING_MASTER", session, dbSession);

                    Iterator<ArrayList<String>>valueIterator=groupMappingMap.values().iterator();

                    while(valueIterator.hasNext()){
                       ArrayList<String> groupMappingRec=valueIterator.next();
                       l_groupMappingList.add(groupMappingRec);

                    }

                    dbg("file name itertion completed for "+l_fileName);
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

        buildBOfromDB(l_groupMappingList);     
        
          dbg("end of  completed student groupMapping--->view"); 
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
    

    
    private void buildBOfromDB(ArrayList<ArrayList<String>>l_groupMappingList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<GroupMappingBO> reqBody = request.getReqBody();
           GroupMappingBO groupMapping =reqBody.get();
           String l_authStatus=groupMapping.getFilter().getAuthStatus();

           dbg("l_authStatus"+l_authStatus);
           
              
           if(l_authStatus!=null&&!l_authStatus.isEmpty()){
               
               List<ArrayList<String>>  l_studentList=  l_groupMappingList.stream().filter(rec->rec.get(8).trim().equals(l_authStatus)).collect(Collectors.toList());
               l_groupMappingList = new ArrayList<ArrayList<String>>(l_studentList);
               dbg("authStatus filter l_groupMappingList size"+l_groupMappingList.size());
               
           }
           
           groupMapping.result=new GroupMappingResult[l_groupMappingList.size()];
           for(int i=0;i<l_groupMappingList.size();i++){
               
               ArrayList<String> l_groupMappingRecords=l_groupMappingList.get(i);
               groupMapping.result[i]=new GroupMappingResult();
               groupMapping.result[i].setInstituteID(l_groupMappingRecords.get(0).trim());
               groupMapping.result[i].setGroupID(l_groupMappingRecords.get(1).trim());
               groupMapping.result[i].setGroupDescription(l_groupMappingRecords.get(2).trim());
               groupMapping.result[i].setMakerID(l_groupMappingRecords.get(3).trim());
               groupMapping.result[i].setCheckerID(l_groupMappingRecords.get(4).trim());
               groupMapping.result[i].setMakerDateStamp(l_groupMappingRecords.get(5).trim());
               groupMapping.result[i].setCheckerDateStamp(l_groupMappingRecords.get(6).trim());
               groupMapping.result[i].setRecordStatus(l_groupMappingRecords.get(7).trim());
               groupMapping.result[i].setAuthStatus(l_groupMappingRecords.get(8).trim());
               groupMapping.result[i].setVersionNumber(l_groupMappingRecords.get(9).trim());
               groupMapping.result[i].setMakerRemarks(l_groupMappingRecords.get(10).trim());
               groupMapping.result[i].setCheckerRemarks(l_groupMappingRecords.get(11).trim());
          }    
           
           if(groupMapping.result==null||groupMapping.result.length==0){
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
        dbg("inside student groupMapping buildJsonResFromBO");    
        RequestBody<GroupMappingBO> reqBody = request.getReqBody();
        GroupMappingBO groupMapping =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<groupMapping.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("instituteID", groupMapping.result[i].getInstituteID())
                                                      .add("groupID", groupMapping.result[i].getGroupID())
                                                      .add("groupDescription",groupMapping.result[i].getGroupDescription())
                                                      .add("makerID", groupMapping.result[i].getMakerID())
                                                      .add("checkerID", groupMapping.result[i].getCheckerID())
                                                      .add("makerDateStamp", groupMapping.result[i].getMakerDateStamp())
                                                      .add("checkerDateStamp", groupMapping.result[i].getCheckerDateStamp())
                                                      .add("recordStatus", groupMapping.result[i].getRecordStatus())
                                                      .add("authStatus", groupMapping.result[i].getAuthStatus())
                                                      .add("versionNumber", groupMapping.result[i].getVersionNumber())
                                                      .add("makerRemarks", groupMapping.result[i].getMakerRemarks())
                                                      .add("checkerRemarks", groupMapping.result[i].getCheckerRemarks()));
        }

           filter=Json.createObjectBuilder()  .add("authStatus", groupMapping.filter.getAuthStatus())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of student groupMapping buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student groupMapping--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of student groupMapping--->businessValidation"); 
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
        dbg("inside student groupMapping master mandatory validation");
        RequestBody<GroupMappingBO> reqBody = request.getReqBody();
        GroupMappingBO groupMapping =reqBody.get();
        int nullCount=0;
         

          if(groupMapping.getFilter().getAuthStatus()==null||groupMapping.getFilter().getAuthStatus().isEmpty()){
             nullCount++;
         }
          
         
          if(nullCount==1){
              status=false;
              errhandler.log_app_error("BS_VAL_002","One Filter value");
          }
          
        dbg("end of student groupMapping master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student groupMapping detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<GroupMappingBO> reqBody = request.getReqBody();
             GroupMappingBO groupMapping =reqBody.get();
             String l_authStatus=groupMapping.getFilter().getAuthStatus();


             if(l_authStatus!=null&&!l_authStatus.isEmpty()){
                 
                if(!bsv.authStatusValidation(l_authStatus, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","authStatus");
                }
                 
             }
   

             
            dbg("end of student groupMapping detailDataValidation");
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
