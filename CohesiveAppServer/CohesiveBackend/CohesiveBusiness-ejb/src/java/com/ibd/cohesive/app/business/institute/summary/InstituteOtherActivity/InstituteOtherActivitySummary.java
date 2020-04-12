/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.InstituteOtherActivity;

import com.ibd.businessViews.IInstituteOtherActivitySummary;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
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
 * @author IBD Technologies
 */
//@Local(IInstituteOtherActivitySummary.class)
@Remote(IInstituteOtherActivitySummary.class)
@Stateless
public class InstituteOtherActivitySummary implements IInstituteOtherActivitySummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public InstituteOtherActivitySummary(){
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
       dbg("inside InstituteOtherActivitySummary--->processing");
       dbg("InstituteOtherActivitySummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IInstituteOtherActivitySummary>instituteOtherActivityEJB=new BusinessEJB();
       instituteOtherActivityEJB.set(this);
      
       exAudit=bs.getExistingAudit(instituteOtherActivityEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,instituteOtherActivityEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,instituteOtherActivityEJB);
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
      InstituteOtherActivityBO instituteOtherActivity=new InstituteOtherActivityBO();
      RequestBody<InstituteOtherActivityBO> reqBody = new RequestBody<InstituteOtherActivityBO>(); 
           
      try{
      dbg("inside student instituteOtherActivity buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      instituteOtherActivity.filter=new InstituteOtherActivityFilter();
//      instituteOtherActivity.filter.setInstituteID(l_filterObject.getString("instituteID"));
//      instituteOtherActivity.filter.setActivityName(l_filterObject.getString("activityName"));
//      instituteOtherActivity.filter.setActivityType(l_filterObject.getString("activityType"));
//      instituteOtherActivity.filter.setDate(l_filterObject.getString("date"));
//      instituteOtherActivity.filter.setDueDate(l_filterObject.getString("dueDate"));
//      instituteOtherActivity.filter.setLevel(l_filterObject.getString("level"));
//      instituteOtherActivity.filter.setVenue(l_filterObject.getString("venue"));
//      instituteOtherActivity.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      instituteOtherActivity.filter.setAuthStatus(l_filterObject.getString("authStat"));
      
       
       if(l_filterObject.getString("activityType").equals("Select option")){

          instituteOtherActivity.filter.setActivityType("");
      }else{

          instituteOtherActivity.filter.setActivityType(l_filterObject.getString("activityType"));
      }

      if(l_filterObject.getString("level").equals("Select option")){

          instituteOtherActivity.filter.setLevel("");
      }else{

          instituteOtherActivity.filter.setLevel(l_filterObject.getString("level"));
      }
      
      if(l_filterObject.getString("authStat").equals("Select option")){
          
          instituteOtherActivity.filter.setAuthStatus("");
      }else{
      
          instituteOtherActivity.filter.setAuthStatus(l_filterObject.getString("authStat"));
      }

      
        reqBody.set(instituteOtherActivity);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student instituteOtherActivity--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_instituteOtherActivityList=new ArrayList();
        String l_instituteID=request.getReqHeader().getInstituteID();
        
        try{
        
            ArrayList<String>l_fileNames=bs.getInstituteFileNames(l_instituteID,request,session,dbSession,inject);
            for(int i=0;i<l_fileNames.size();i++){
                dbg("inside file name iteration");
                String l_fileName=l_fileNames.get(i);
                dbg("l_fileName"+l_fileName);
                Map<String,DBRecord>instituteOtherActivityMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY","IVW_OTHER_ACTIVITY", session, dbSession);

                Iterator<DBRecord>valueIterator=instituteOtherActivityMap.values().iterator();

                while(valueIterator.hasNext()){
                   DBRecord instituteOtherActivityRec=valueIterator.next();
                   l_instituteOtherActivityList.add(instituteOtherActivityRec);

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

        buildBOfromDB(l_instituteOtherActivityList);     
        
          dbg("end of  completed student instituteOtherActivity--->view"); 
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
    

    
    private void buildBOfromDB(ArrayList<DBRecord>l_instituteOtherActivityList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<InstituteOtherActivityBO> reqBody = request.getReqBody();
           InstituteOtherActivityBO instituteOtherActivity =reqBody.get();
           String l_authStatus=instituteOtherActivity.getFilter().getAuthStatus();
           String l_activityType=instituteOtherActivity.getFilter().getActivityType();
           BusinessService bs=inject.getBusinessService(session);
//           String l_date=instituteOtherActivity.getFilter().getDate();
           String l_level=instituteOtherActivity.getFilter().getLevel();
           
           dbg("l_authStatus"+l_authStatus);
           dbg("l_activityType"+l_activityType);
//           dbg("l_date"+l_date);
           dbg("l_level"+l_level);
           

           
           if(l_activityType!=null&&!l_activityType.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_instituteOtherActivityList.stream().filter(rec->rec.getRecord().get(4).trim().equals(l_activityType)).collect(Collectors.toList());
             l_instituteOtherActivityList = new ArrayList<DBRecord>(l_studentList);
             dbg("l_activityType filter l_instituteOtherActivityList size"+l_instituteOtherActivityList.size());
           }
           
//           if(l_date!=null&&!l_date.isEmpty()){
//            
//             List<DBRecord>  l_studentList=  l_instituteOtherActivityList.stream().filter(rec->rec.getRecord().get(7).trim().equals(l_date)).collect(Collectors.toList());
//             l_instituteOtherActivityList = new ArrayList<DBRecord>(l_studentList);
//             dbg("l_date filter l_instituteOtherActivityList size"+l_instituteOtherActivityList.size());
//           }

                 
           if(l_level!=null&&!l_level.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_instituteOtherActivityList.stream().filter(rec->rec.getRecord().get(5).trim().equals(l_level)).collect(Collectors.toList());
             l_instituteOtherActivityList = new ArrayList<DBRecord>(l_studentList);
             dbg("l_level filter l_instituteOtherActivityList size"+l_instituteOtherActivityList.size());
           }
           
           if(l_authStatus!=null&&!l_authStatus.isEmpty()){
               
               List<DBRecord>  l_studentList=  l_instituteOtherActivityList.stream().filter(rec->rec.getRecord().get(14).trim().equals(l_authStatus)).collect(Collectors.toList());
               l_instituteOtherActivityList = new ArrayList<DBRecord>(l_studentList);
               dbg("authStatus filter l_instituteOtherActivityList size"+l_instituteOtherActivityList.size());
               
           }
           
           instituteOtherActivity.result=new InstituteOtherActivityResult[l_instituteOtherActivityList.size()];
           for(int i=0;i<l_instituteOtherActivityList.size();i++){
               
               ArrayList<String> l_instituteOtherActivityRecords=l_instituteOtherActivityList.get(i).getRecord();
               instituteOtherActivity.result[i]=new InstituteOtherActivityResult();
               instituteOtherActivity.result[i].setActivityID(l_instituteOtherActivityRecords.get(2).trim());
               instituteOtherActivity.result[i].setActivityName(l_instituteOtherActivityRecords.get(3).trim());
               
               String activityType=bs.getActivityType(l_instituteOtherActivityRecords.get(4).trim());
               dbg("activityType"+activityType);
               instituteOtherActivity.result[i].setActivityType(activityType);
               String activityLevel=bs.getActivityLeavel(l_instituteOtherActivityRecords.get(5).trim());
               dbg("activityLevel"+activityLevel);
               
               instituteOtherActivity.result[i].setLevel(activityLevel);
               instituteOtherActivity.result[i].setDate(l_instituteOtherActivityRecords.get(7).trim());

          }    
           
           if(instituteOtherActivity.result==null||instituteOtherActivity.result.length==0){
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
        dbg("inside student instituteOtherActivity buildJsonResFromBO");    
        RequestBody<InstituteOtherActivityBO> reqBody = request.getReqBody();
        InstituteOtherActivityBO instituteOtherActivity =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<instituteOtherActivity.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder()
                                                      .add("activityID", instituteOtherActivity.result[i].getActivityID())
                                                      .add("activityName", instituteOtherActivity.result[i].getActivityName())
                                                      .add("activityType", instituteOtherActivity.result[i].getActivityType())
                                                      .add("level", instituteOtherActivity.result[i].getLevel())
                                                      .add("date", instituteOtherActivity.result[i].getDate()));
        }

           filter=Json.createObjectBuilder()  .add("activityType", instituteOtherActivity.filter.getActivityType())
                                              .add("level", instituteOtherActivity.filter.getLevel())
                                              .add("authStatus", instituteOtherActivity.filter.getAuthStatus())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of student instituteOtherActivity buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student instituteOtherActivity--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of student instituteOtherActivity--->businessValidation"); 
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
        dbg("inside student instituteOtherActivity master mandatory validation");
        RequestBody<InstituteOtherActivityBO> reqBody = request.getReqBody();
        InstituteOtherActivityBO instituteOtherActivity =reqBody.get();
        int nullCount=0;
         
         
         if(instituteOtherActivity.getFilter().getActivityType()==null||instituteOtherActivity.getFilter().getActivityType().isEmpty()){
             nullCount++;
         }
         
         if(instituteOtherActivity.getFilter().getLevel()==null||instituteOtherActivity.getFilter().getLevel().isEmpty()){
             nullCount++;
         }
 
          if(instituteOtherActivity.getFilter().getAuthStatus()==null||instituteOtherActivity.getFilter().getAuthStatus().isEmpty()){
             nullCount++;
         }
          
         
          if(nullCount==3){
              status=false;
              errhandler.log_app_error("BS_VAL_002","One Filter value");
          }
          
        dbg("end of student instituteOtherActivity master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student instituteOtherActivity detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<InstituteOtherActivityBO> reqBody = request.getReqBody();
             InstituteOtherActivityBO instituteOtherActivity =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_authStatus=instituteOtherActivity.getFilter().getAuthStatus();
             String l_activityType=instituteOtherActivity.getFilter().getActivityType();
             String l_level=instituteOtherActivity.getFilter().getLevel();
             
             
              if(l_instituteID!=null&&!l_instituteID.isEmpty()){
             
                    if(!bsv.instituteIDValidation( l_instituteID,errhandler,inject, session, dbSession)){
                         status=false;
                         errhandler.log_app_error("BS_VAL_003","instituteID");
                    }
            
              }
             

             if(l_authStatus!=null&&!l_authStatus.isEmpty()){
                 
                if(!bsv.authStatusValidation(l_authStatus, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","authStatus");
                }
                 
             }
   
             if(l_activityType!=null&&!l_activityType.isEmpty()){
                 
                 if(!(l_activityType.equals("S")||l_activityType.equals("C"))){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","Activity type");
                 
                 }
             }
             
             if(l_level!=null&&!l_level.isEmpty()){
                 
                 if(!(l_level.equals("S")||l_level.equals("D")||l_level.equals("I")||l_level.equals("E"))){
                      status=false;
                      errhandler.log_app_error("BS_VAL_003","Level");
                 }
                 
             }

             
            dbg("end of student instituteOtherActivity detailDataValidation");
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
