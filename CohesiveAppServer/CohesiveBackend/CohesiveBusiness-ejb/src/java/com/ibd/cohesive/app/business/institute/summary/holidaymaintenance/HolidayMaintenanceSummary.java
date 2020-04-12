/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.holidaymaintenance;

import com.ibd.businessViews.IHolidayMaintenanceSummary;
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
//@Local(IHolidayMaintenanceSummary.class)
@Remote(IHolidayMaintenanceSummary.class)
@Stateless
public class HolidayMaintenanceSummary implements IHolidayMaintenanceSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public HolidayMaintenanceSummary(){
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
       dbg("inside HolidayMaintenanceSummary--->processing");
       dbg("HolidayMaintenanceSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IHolidayMaintenanceSummary>holidayMaintenanceEJB=new BusinessEJB();
       holidayMaintenanceEJB.set(this);
      
       exAudit=bs.getExistingAudit(holidayMaintenanceEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,holidayMaintenanceEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,holidayMaintenanceEJB);
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
      HolidayMaintenanceBO holidayMaintenance=new HolidayMaintenanceBO();
      RequestBody<HolidayMaintenanceBO> reqBody = new RequestBody<HolidayMaintenanceBO>(); 
           
      try{
      dbg("inside student holidayMaintenance buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      holidayMaintenance.filter=new HolidayMaintenanceFilter();
//      holidayMaintenance.filter.setInstituteID(l_filterObject.getString("instituteID"));
//      holidayMaintenance.filter.setYear(l_filterObject.getString("year"));
//      holidayMaintenance.filter.setMonth(l_filterObject.getString("month"));
//      holidayMaintenance.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      holidayMaintenance.filter.setAuthStatus(l_filterObject.getString("authStat"));
      
//      holidayMaintenance.filter.setYear(l_filterObject.getString("year"));
//      holidayMaintenance.filter.setMonth(l_filterObject.getString("month"));
      
      if(l_filterObject.getString("authStat").equals("Select option")){
          
          holidayMaintenance.filter.setAuthStatus("");
      }else{
      
          holidayMaintenance.filter.setAuthStatus(l_filterObject.getString("authStat"));
      }
      
      if(l_filterObject.getString("year").equals("Select option")){
          
          holidayMaintenance.filter.setYear("");
      }else{
      
          holidayMaintenance.filter.setYear(l_filterObject.getString("year"));
      }
      
      if(l_filterObject.getString("month").equals("Select option")){
          
          holidayMaintenance.filter.setMonth("");
      }else{
      
          holidayMaintenance.filter.setMonth(l_filterObject.getString("month"));
      }
  
        reqBody.set(holidayMaintenance);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student holidayMaintenance--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_holidayMaintenanceList=new ArrayList();
        String l_instituteID=request.getReqHeader().getInstituteID();
        
        try{
        
                ArrayList<String>l_fileNames=bs.getInstituteFileNames(l_instituteID,request,session,dbSession,inject);
                for(int i=0;i<l_fileNames.size();i++){
                    dbg("inside file name iteration");
                    String l_fileName=l_fileNames.get(i);
                    dbg("l_fileName"+l_fileName);
                    Map<String,DBRecord>holidayMaintenanceMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_HOLIDAY_MAINTANENCE", session, dbSession);

                    Iterator<DBRecord>valueIterator=holidayMaintenanceMap.values().iterator();

                    while(valueIterator.hasNext()){
                       DBRecord holidayMaintenanceRec=valueIterator.next();
                       l_holidayMaintenanceList.add(holidayMaintenanceRec);

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

        buildBOfromDB(l_holidayMaintenanceList);     
        
          dbg("end of  completed student holidayMaintenance--->view");
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
    

    
    private void buildBOfromDB(ArrayList<DBRecord>l_holidayMaintenanceList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<HolidayMaintenanceBO> reqBody = request.getReqBody();
           HolidayMaintenanceBO holidayMaintenance =reqBody.get();
           String l_authStatus=holidayMaintenance.getFilter().getAuthStatus();
           String l_year=holidayMaintenance.getFilter().getYear();
           String l_month=holidayMaintenance.getFilter().getMonth();
           
           dbg("l_authStatus"+l_authStatus);
           dbg("l_year"+l_year);
           dbg("l_month"+l_month);

           if(l_year!=null&&!l_year.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_holidayMaintenanceList.stream().filter(rec->rec.getRecord().get(1).trim().equals(l_year)).collect(Collectors.toList());
             l_holidayMaintenanceList = new ArrayList<DBRecord>(l_studentList);
             dbg("l_year filter l_holidayMaintenanceList size"+l_holidayMaintenanceList.size());
           }
           
           if(l_month!=null&&!l_month.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_holidayMaintenanceList.stream().filter(rec->rec.getRecord().get(2).trim().equals(l_month)).collect(Collectors.toList());
             l_holidayMaintenanceList = new ArrayList<DBRecord>(l_studentList);
             dbg("l_month filter l_holidayMaintenanceList size"+l_holidayMaintenanceList.size());
           }
 
           if(l_authStatus!=null&&!l_authStatus.isEmpty()){
               
               List<DBRecord>  l_studentList=  l_holidayMaintenanceList.stream().filter(rec->rec.getRecord().get(9).trim().equals(l_authStatus)).collect(Collectors.toList());
               l_holidayMaintenanceList = new ArrayList<DBRecord>(l_studentList);
               dbg("authStatus filter l_holidayMaintenanceList size"+l_holidayMaintenanceList.size());
               
           }
           
           holidayMaintenance.result=new HolidayMaintenanceResult[l_holidayMaintenanceList.size()];
           for(int i=0;i<l_holidayMaintenanceList.size();i++){
               
               ArrayList<String> l_holidayMaintenanceRecords=l_holidayMaintenanceList.get(i).getRecord();
               holidayMaintenance.result[i]=new HolidayMaintenanceResult();
               holidayMaintenance.result[i].setInstituteID(l_holidayMaintenanceRecords.get(0).trim());
               holidayMaintenance.result[i].setYear(l_holidayMaintenanceRecords.get(1).trim());
               holidayMaintenance.result[i].setMonth(l_holidayMaintenanceRecords.get(2).trim());
               holidayMaintenance.result[i].setHoliday(l_holidayMaintenanceRecords.get(3).trim());
               holidayMaintenance.result[i].setMakerID(l_holidayMaintenanceRecords.get(4).trim());
               holidayMaintenance.result[i].setCheckerID(l_holidayMaintenanceRecords.get(5).trim());
               holidayMaintenance.result[i].setMakerDateStamp(l_holidayMaintenanceRecords.get(6).trim());
               holidayMaintenance.result[i].setCheckerDateStamp(l_holidayMaintenanceRecords.get(7).trim());
               holidayMaintenance.result[i].setRecordStatus(l_holidayMaintenanceRecords.get(8).trim());
               holidayMaintenance.result[i].setAuthStatus(l_holidayMaintenanceRecords.get(9).trim());
               holidayMaintenance.result[i].setVersionNumber(l_holidayMaintenanceRecords.get(10).trim());
               holidayMaintenance.result[i].setMakerRemarks(l_holidayMaintenanceRecords.get(11).trim());
               holidayMaintenance.result[i].setCheckerRemarks(l_holidayMaintenanceRecords.get(12).trim());
          }    
           
           
           if(holidayMaintenance.result==null||holidayMaintenance.result.length==0){
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
        dbg("inside student holidayMaintenance buildJsonResFromBO");    
        RequestBody<HolidayMaintenanceBO> reqBody = request.getReqBody();
        HolidayMaintenanceBO holidayMaintenance =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<holidayMaintenance.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("instituteID", holidayMaintenance.result[i].getInstituteID())
                                                      .add("year", holidayMaintenance.result[i].getYear())
                                                      .add("month",holidayMaintenance.result[i].getMonth())
                                                      .add("holiday",holidayMaintenance.result[i].getHoliday())
                                                      .add("makerID", holidayMaintenance.result[i].getMakerID())
                                                      .add("checkerID", holidayMaintenance.result[i].getCheckerID())
                                                      .add("makerDateStamp", holidayMaintenance.result[i].getMakerDateStamp())
                                                      .add("checkerDateStamp", holidayMaintenance.result[i].getCheckerDateStamp())
                                                      .add("recordStatus", holidayMaintenance.result[i].getRecordStatus())
                                                      .add("authStatus", holidayMaintenance.result[i].getAuthStatus())
                                                      .add("versionNumber", holidayMaintenance.result[i].getVersionNumber())
                                                      .add("makerRemarks", holidayMaintenance.result[i].getMakerRemarks())
                                                      .add("checkerRemarks", holidayMaintenance.result[i].getCheckerRemarks()));
        }

           filter=Json.createObjectBuilder()  .add("year", holidayMaintenance.getFilter().getYear())
                                              .add("month", holidayMaintenance.filter.getMonth())
                                              .add("authStatus", holidayMaintenance.filter.getAuthStatus())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of student holidayMaintenance buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student holidayMaintenance--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of student holidayMaintenance--->businessValidation"); 
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
        dbg("inside student holidayMaintenance master mandatory validation");
        RequestBody<HolidayMaintenanceBO> reqBody = request.getReqBody();
        HolidayMaintenanceBO holidayMaintenance =reqBody.get();
        int nullCount=0;
         
         
         if(holidayMaintenance.getFilter().getYear()==null||holidayMaintenance.getFilter().getYear().isEmpty()){
             nullCount++;
         }
         
         if(holidayMaintenance.getFilter().getMonth()==null||holidayMaintenance.getFilter().getMonth().isEmpty()){
             nullCount++;
         }

          if(holidayMaintenance.getFilter().getAuthStatus()==null||holidayMaintenance.getFilter().getAuthStatus().isEmpty()){
             nullCount++;
         }
          
         
          if(nullCount==3){
              status=false;
              errhandler.log_app_error("BS_VAL_002","One Filter value");
          }
          
        dbg("end of student holidayMaintenance master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student holidayMaintenance detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<HolidayMaintenanceBO> reqBody = request.getReqBody();
             HolidayMaintenanceBO holidayMaintenance =reqBody.get();
             String l_authStatus=holidayMaintenance.getFilter().getAuthStatus();
             String l_year=holidayMaintenance.getFilter().getYear();
             String l_month=holidayMaintenance.getFilter().getMonth();

             
               if(l_year!=null&&!l_year.isEmpty()){
             
                    if(!bsv.yearValidation(l_year, session, dbSession,inject)){
                         status=false;
                         errhandler.log_app_error("BS_VAL_003","year");
                    }
                    
               }
               
               if(l_month!=null&&!l_month.isEmpty()){
             
                    if(!bsv.monthValidation(l_month, session, dbSession,inject)){
                         status=false;
                         errhandler.log_app_error("BS_VAL_003","month");
                    }
               }
             
             if(l_authStatus!=null&&!l_authStatus.isEmpty()){
                 
                if(!bsv.authStatusValidation(l_authStatus, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","authStatus");
                }
                 
             }
   

             
            dbg("end of student holidayMaintenance detailDataValidation");
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
