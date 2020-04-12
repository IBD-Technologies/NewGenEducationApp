/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.ECircularSummary;

import com.ibd.businessViews.IECircularSummary;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.ConvertedDate;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
@Remote(IECircularSummary.class)
@Stateless
public class ECircularSummary implements IECircularSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public ECircularSummary(){
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
       dbg("inside ECircularSummary--->processing");
       dbg("ECircularSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IECircularSummary>eCircularEJB=new BusinessEJB();
       eCircularEJB.set(this);
      
       exAudit=bs.getExistingAudit(eCircularEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,eCircularEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,eCircularEJB);
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
      ECircularBO eCircular=new ECircularBO();
      RequestBody<ECircularBO> reqBody = new RequestBody<ECircularBO>(); 
           
      try{
      dbg("inside student eCircular buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      eCircular.filter=new ECircularFilter();
//      eCircular.filter.setInstituteID(l_filterObject.getString("instituteID"));
//      eCircular.filter.setAssignmentID(l_filterObject.getString("assignmentID"));
//      eCircular.filter.setAssignmentType(l_filterObject.getString("assignmentType"));
//      eCircular.filter.setSubjectID(l_filterObject.getString("subjectID"));
//      eCircular.filter.setDueDate(l_filterObject.getString("dueDate"));
//      eCircular.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      eCircular.filter.setAuthStatus(l_filterObject.getString("authStat"));

       
       eCircular.filter.setCircularType(l_filterObject.getString("circularType"));
       eCircular.filter.setFromDate(l_filterObject.getString("fromDate"));
       eCircular.filter.setToDate(l_filterObject.getString("toDate"));
       
       if(l_filterObject.getString("authStat").equals("Select option")){
          
          eCircular.filter.setAuthStatus("");
      }else{
      
          eCircular.filter.setAuthStatus(l_filterObject.getString("authStat"));
      }

       
      
        reqBody.set(eCircular);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student eCircular--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_eCircularList=new ArrayList();
        String l_instituteID=request.getReqHeader().getInstituteID();
        
        
        
        try{
        
            ArrayList<String>l_fileNames=bs.getInstituteFileNames(l_instituteID,request,session,dbSession,inject);
            for(int i=0;i<l_fileNames.size();i++){
                dbg("inside file name iteration");
                String l_fileName=l_fileNames.get(i);
                dbg("l_fileName"+l_fileName);
                Map<String,DBRecord>eCircularMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular","INSTITUTE","IVW_E_CIRCULAR", session, dbSession);

                Iterator<DBRecord>valueIterator=eCircularMap.values().iterator();

                while(valueIterator.hasNext()){
                   DBRecord eCircularRec=valueIterator.next();
                   l_eCircularList.add(eCircularRec);

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

        buildBOfromDB(l_eCircularList);     
        
          dbg("end of  completed student eCircular--->view");  
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
    

    
    private void buildBOfromDB(ArrayList<DBRecord>l_eCircularList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           IBDProperties i_db_properties=session.getCohesiveproperties();
           RequestBody<ECircularBO> reqBody = request.getReqBody();
           ECircularBO eCircular =reqBody.get();
           String l_authStatus=eCircular.getFilter().getAuthStatus();
           String l_circularType=eCircular.getFilter().getCircularType();
           String l_fromDate=eCircular.getFilter().getFromDate();
           String l_toDate=eCircular.getFilter().getToDate();
           String dateFormat=i_db_properties.getProperty("DATE_FORMAT");
           SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
           ArrayList<DBRecord>filteredList=new ArrayList();

           dbg("l_authStatus"+l_authStatus);
           dbg("l_circularType"+l_circularType);
           dbg("l_fromDate"+l_fromDate);
           dbg("l_toDate"+l_toDate);

           
           if(l_circularType!=null&&!l_circularType.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_eCircularList.stream().filter(rec->rec.getRecord().get(16).trim().equals(l_circularType)).collect(Collectors.toList());
             l_eCircularList = new ArrayList<DBRecord>(l_studentList);
             dbg("l_assignmentType filter l_eCircularList size"+l_eCircularList.size());
           }
           
           if(l_authStatus!=null&&!l_authStatus.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_eCircularList.stream().filter(rec->rec.getRecord().get(10).trim().equals(l_authStatus)).collect(Collectors.toList());
             l_eCircularList = new ArrayList<DBRecord>(l_studentList);
             dbg("l_assignmentDate filter l_eCircularList size"+l_eCircularList.size());
           }
          
           
           
           for(int i=0;i<l_eCircularList.size();i++){
               
               DBRecord l_circularRecord=l_eCircularList.get(i);
               String l_circularDate=l_circularRecord.getRecord().get(15).trim();
               Date circularDate=formatter.parse(l_circularDate);
               Date fromDate=formatter.parse(l_fromDate);
               Date toDate=formatter.parse(l_toDate);
               dbg("l_checkerDate"+l_circularDate);
               dbg("fromDate"+fromDate);
               dbg("toDate"+toDate);
               
                if(circularDate.compareTo(fromDate)>=0){
                    
                    dbg("circularDate.compareTo(fromDate)>=0");
                    
                    if(circularDate.compareTo(toDate)<=0){
                       
                       
                           
                           filteredList.add(l_circularRecord);
                           
                    }
                }
           }
           
           
           
           
           
           eCircular.result=new ECircularResult[filteredList.size()];
           for(int i=0;i<filteredList.size();i++){
               
               ArrayList<String> l_eCircularRecords=filteredList.get(i).getRecord();
               eCircular.result[i]=new ECircularResult();
               eCircular.result[i].setCircularID(l_eCircularRecords.get(2).trim());
               eCircular.result[i].setDescription(l_eCircularRecords.get(3).trim());
               eCircular.result[i].setCircularDate(l_eCircularRecords.get(15).trim());
               
               if(l_eCircularRecords.get(16).trim().equals("S")){
                   
                   eCircular.result[i].setCircularType("Student");
                   
               }else{
                   
                   eCircular.result[i].setCircularType("Teacher");
               }
               
               
               
               
          }    
           
           
           if(eCircular.result==null||eCircular.result.length==0){
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
        dbg("inside student eCircular buildJsonResFromBO");    
        RequestBody<ECircularBO> reqBody = request.getReqBody();
        ECircularBO eCircular =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<eCircular.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("circularDate", eCircular.result[i].getCircularDate())
                                                      .add("circularType", eCircular.result[i].getCircularType())
                                                      .add("circularID", eCircular.result[i].getCircularID())
                                                      .add("description", eCircular.result[i].getDescription()));
        }

           filter=Json.createObjectBuilder().add("circularType", eCircular.filter.getCircularType())
                                            .add("authStat", eCircular.filter.getAuthStatus())
                                            .add("fromDate", eCircular.filter.getFromDate())
                                            .add("toDate", eCircular.filter.getToDate())
                                            .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of student eCircular buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student eCircular--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of student eCircular--->businessValidation"); 
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
        dbg("inside student eCircular master mandatory validation");
        RequestBody<ECircularBO> reqBody = request.getReqBody();
        ECircularBO eCircular =reqBody.get();
        boolean fromDateEmpty=false;
        boolean toDateEmpty=false;

         
        if(eCircular.getFilter().getFromDate()==null||eCircular.getFilter().getFromDate().isEmpty()){
              fromDateEmpty=true;
         }
          
          if(eCircular.getFilter().getToDate()==null||eCircular.getFilter().getToDate().isEmpty()){
             toDateEmpty=true;
         }
          
          if(fromDateEmpty==true){
              
             status=false;  
             errhandler.log_app_error("BS_VAL_002","fromDate");
          }
          
          if(toDateEmpty==true){
              
             status=false;  
             errhandler.log_app_error("BS_VAL_002","toDate");
          }
          
        dbg("end of student eCircular master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student eCircular detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<ECircularBO> reqBody = request.getReqBody();
             ECircularBO eCircular =reqBody.get();
             String l_fromDate=eCircular.getFilter().getFromDate();
             String l_toDate=eCircular.getFilter().getToDate();
             BusinessService bs=inject.getBusinessService(session);
             
             
             if(l_fromDate!=null&&!l_fromDate.isEmpty()&&l_toDate!=null&&!l_toDate.isEmpty()){
             
             
                 if(!bsv.dateFormatValidation(l_fromDate, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","from Date");
                     throw new BSValidationException("BSValidationException");
                 }
                 
                 if(!bsv.dateFormatValidation(l_toDate, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","to Date");
                     throw new BSValidationException("BSValidationException");
                 }
                 
                 if(!bsv.futureDateValidation(l_fromDate, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_048",null);
                     throw new BSValidationException("BSValidationException");
                 }
                 
                 if(!bsv.futureDateValidation(l_toDate, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_048",null);
                     throw new BSValidationException("BSValidationException");
                 }
                 
                 ConvertedDate fromDate=bs.getYearMonthandDay(l_fromDate);
                 Calendar start = Calendar.getInstance();

                 start.set(Calendar.YEAR, Integer.parseInt(fromDate.getYear()));
                 start.set(Calendar.MONTH,  Integer.parseInt(fromDate.getMonth()));
                 start.set(Calendar.DAY_OF_MONTH,  Integer.parseInt(fromDate.getDay()));


                 ConvertedDate toDate=bs.getYearMonthandDay(l_toDate);
                 Calendar end = Calendar.getInstance();

                 end.set(Calendar.YEAR, Integer.parseInt(toDate.getYear()));
                 end.set(Calendar.MONTH,  Integer.parseInt(toDate.getMonth()));
                 end.set(Calendar.DAY_OF_MONTH,  Integer.parseInt(toDate.getDay()));

                    if(start.after(end)){

                        status=false;
                        errhandler.log_app_error("BS_VAL_003","Date range");
                        throw new BSValidationException("BSValidationException");
                    }


                   int dateSize= bs.getLeaveDates(l_fromDate, l_toDate, session, dbSession, inject).size();


                   if(dateSize>7){

                        status=false;
                        errhandler.log_app_error("BS_VAL_049","Date range");
                        throw new BSValidationException("BSValidationException");
                    }

             }
   

             
            dbg("end of student eCircular detailDataValidation");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch(DBValidationException ex){
            throw ex;
        } catch(BSValidationException ex){
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
