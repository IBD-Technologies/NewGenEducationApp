/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.Institutefeemanagement;

import com.ibd.businessViews.IInstituteFeeManagementSummary;
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
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
//@Local(IInstituteFeeManagementSummary.class)
@Remote(IInstituteFeeManagementSummary.class)
@Stateless
public class InstituteFeeManagementSummary implements IInstituteFeeManagementSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public InstituteFeeManagementSummary(){
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
       dbg("inside InstituteFeeManagementSummary--->processing");
       dbg("InstituteFeeManagementSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IInstituteFeeManagementSummary>instituteFeeManagementEJB=new BusinessEJB();
       instituteFeeManagementEJB.set(this);
      
       exAudit=bs.getExistingAudit(instituteFeeManagementEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,instituteFeeManagementEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,instituteFeeManagementEJB);
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
                dbg(jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
                //if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
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
      InstituteFeeManagementBO instituteFeeManagement=new InstituteFeeManagementBO();
      RequestBody<InstituteFeeManagementBO> reqBody = new RequestBody<InstituteFeeManagementBO>(); 
           
      try{
      dbg("inside student instituteFeeManagement buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      instituteFeeManagement.filter=new InstituteFeeManagementFilter();
//      instituteFeeManagement.filter.setInstituteID(l_filterObject.getString("instituteID"));
//      instituteFeeManagement.filter.setFeeID(l_filterObject.getString("feeID"));
//      instituteFeeManagement.filter.setFeeType(l_filterObject.getString("feeType"));
//      instituteFeeManagement.filter.setAmount(l_filterObject.getString("amount"));
//      instituteFeeManagement.filter.setDueDate(l_filterObject.getString("dueDate"));
//      instituteFeeManagement.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      instituteFeeManagement.filter.setAuthStatus(l_filterObject.getString("authStat"));
        
        if(l_filterObject.getString("feeType").equals("Select option")){

           instituteFeeManagement.filter.setFeeType("");
        }else{

           instituteFeeManagement.filter.setFeeType(l_filterObject.getString("feeType"));
        }
        instituteFeeManagement.filter.setDueDate(l_filterObject.getString("dueDate"));
        
        if(l_filterObject.getString("authStat").equals("Select option")){
          
          instituteFeeManagement.filter.setAuthStatus("");
        }else{
      
          instituteFeeManagement.filter.setAuthStatus(l_filterObject.getString("authStat"));
        }

        
        reqBody.set(instituteFeeManagement);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     
private  ArrayList<String> getFeeIDs(String instID) throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException
{
            DirectoryStream<Path> stream =null;
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
        try
        {
         ArrayList<String> feeidList=new ArrayList();
         
          Path ArchFolderPath=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instID+i_db_properties.getProperty("FOLDER_DELIMITER")+"FEE"+i_db_properties.getProperty("FOLDER_DELIMITER"));

        if(Files.notExists(ArchFolderPath)){
               
            return null;
            
            }
        else
        {
         stream = Files.newDirectoryStream(ArchFolderPath);
             
            for (Path file: stream) {    
                 
             if(Files.isDirectory(file))
             { 
             dbg("Fee ID-->"+file.getFileName().toString());   
             
                feeidList.add(file.getFileName().toString());
             }            
            }
         } 
        
        return feeidList;
        }
     catch(Exception ex){
         throw new BSProcessingException("BSProcessingException"+ex.toString());
     }
        finally{
            
           try{ 
                if(stream!=null){
                    stream.close();
                }
                
           }catch(Exception ex){
             throw new DBProcessingException("DBProcessingException"+ex.toString());
           }
    
        }     
}    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student instituteFeeManagement--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_instituteFeeManagementList=new ArrayList();
        String l_instituteID=request.getReqHeader().getInstituteID();
        Map<String,DBRecord>instituteFeeManagementMap=null;
        try{
                ArrayList<String>l_fileNames=bs.getInstituteFileNames(l_instituteID,request,session,dbSession,inject);
                
                for(int i=0;i<l_fileNames.size();i++){
                    dbg("inside file name iteration");
                    String l_fileName=l_fileNames.get(i);
                    dbg("l_fileName"+l_fileName);
                  
                    try
                     {    
                     instituteFeeManagementMap =readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", session, dbSession);
                     }
                   catch(DBValidationException ex)
                       {
                               dbg("Inside No record found Exception");
                             if(ex.toString().contains("DB_VAL_000")||ex.toString().contains("DB_VAL_011")){
                                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                                session.getErrorhandler().log_app_error("BS_VAL_013", null);
                                throw new BSValidationException("BSValidationException");
                              }else{
                                   throw ex;
                                    }
                              }
                         
                   if(instituteFeeManagementMap!=null && !instituteFeeManagementMap.isEmpty())
                    {
                        Iterator<DBRecord>valueIterator=instituteFeeManagementMap.values().iterator();

                          while(valueIterator.hasNext()){
                             DBRecord feeManagementRec=valueIterator.next();
                            l_instituteFeeManagementList.add(feeManagementRec);

                                 }  
                    }
                    dbg("file name itertion completed for "+l_fileName);
                }
                
        }catch(Exception ex){
            
            throw ex;
            
           }
        if(l_instituteFeeManagementList.size()>0)
          buildBOfromDB(l_instituteFeeManagementList);     
        else	 
         {  session.getErrorhandler().log_app_error("BS_VAL_013", null);
            throw new BSValidationException("BSValidationException");
          }    
          dbg("end of  completed student instituteFeeManagement--->view");   
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
    

    
    private void buildBOfromDB(ArrayList<DBRecord>l_instituteFeeManagementList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<InstituteFeeManagementBO> reqBody = request.getReqBody();
           InstituteFeeManagementBO instituteFeeManagement =reqBody.get();
           String l_authStatus=instituteFeeManagement.getFilter().getAuthStatus();
           String l_feeType=instituteFeeManagement.getFilter().getFeeType();
           String l_dueDate=instituteFeeManagement.getFilter().getDueDate();
           
           dbg("l_authStatus"+l_authStatus);
           dbg("l_dueDate"+l_dueDate);
           dbg("l_feeType"+l_feeType);

           
           if(l_feeType!=null&&!l_feeType.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_instituteFeeManagementList.stream().filter(rec->rec.getRecord().get(4).trim().equals(l_feeType)).collect(Collectors.toList());
             l_instituteFeeManagementList = new ArrayList<DBRecord>(l_studentList);
             dbg("l_feeType filter l_instituteFeeManagementList size"+l_instituteFeeManagementList.size());
           }
           
           if(l_dueDate!=null&&!l_dueDate.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_instituteFeeManagementList.stream().filter(rec->rec.getRecord().get(6).trim().equals(l_dueDate)).collect(Collectors.toList());
             l_instituteFeeManagementList = new ArrayList<DBRecord>(l_studentList);
             dbg("l_feeManagementDate filter l_instituteFeeManagementList size"+l_instituteFeeManagementList.size());
           }
           
           if(l_authStatus!=null&&!l_authStatus.isEmpty()){
               
               List<DBRecord>  l_studentList=  l_instituteFeeManagementList.stream().filter(rec->rec.getRecord().get(12).trim().equals(l_authStatus)).collect(Collectors.toList());
               l_instituteFeeManagementList = new ArrayList<DBRecord>(l_studentList);
               dbg("authStatus filter l_instituteFeeManagementList size"+l_instituteFeeManagementList.size());
               
           }
           
           instituteFeeManagement.result=new InstituteFeeManagementResult[l_instituteFeeManagementList.size()];
           for(int i=0;i<l_instituteFeeManagementList.size();i++){
               
               ArrayList<String> l_instituteFeeManagementRecords=l_instituteFeeManagementList.get(i).getRecord();
               instituteFeeManagement.result[i]=new InstituteFeeManagementResult();
               instituteFeeManagement.result[i].setGroupID(l_instituteFeeManagementRecords.get(1).trim());
               instituteFeeManagement.result[i].setFeeID(l_instituteFeeManagementRecords.get(2).trim());
               instituteFeeManagement.result[i].setFeeType(l_instituteFeeManagementRecords.get(4).trim());
               instituteFeeManagement.result[i].setAmount(l_instituteFeeManagementRecords.get(5).trim());
               instituteFeeManagement.result[i].setDueDate(l_instituteFeeManagementRecords.get(6).trim());
          }    
           
           if(instituteFeeManagement.result==null||instituteFeeManagement.result.length==0){
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
        dbg("inside student instituteFeeManagement buildJsonResFromBO");    
        RequestBody<InstituteFeeManagementBO> reqBody = request.getReqBody();
        InstituteFeeManagementBO instituteFeeManagement =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<instituteFeeManagement.result.length;i++){
            dbg("assignee"+ instituteFeeManagement.result[i].getGroupID());
            resultArray.add(Json.createObjectBuilder().add("feeID", instituteFeeManagement.result[i].getFeeID())
                                                      .add("assignee", instituteFeeManagement.result[i].getGroupID())
                                                      .add("feeType", instituteFeeManagement.result[i].getFeeType())
                                                      .add("amount", instituteFeeManagement.result[i].getAmount())
                                                      .add("dueDate", instituteFeeManagement.result[i].getDueDate()));
        }

           filter=Json.createObjectBuilder() .add("feeType", instituteFeeManagement.filter.getFeeType())
                                              .add("dueDate", instituteFeeManagement.filter.getDueDate())
                                              .add("authStatus", instituteFeeManagement.filter.getAuthStatus())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of student instituteFeeManagement buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student instituteFeeManagement--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of student instituteFeeManagement--->businessValidation"); 
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
        dbg("inside student instituteFeeManagement master mandatory validation");
        RequestBody<InstituteFeeManagementBO> reqBody = request.getReqBody();
        InstituteFeeManagementBO instituteFeeManagement =reqBody.get();
        int nullCount=0;
         

         if(instituteFeeManagement.getFilter().getFeeType()==null||instituteFeeManagement.getFilter().getFeeType().isEmpty()){
             nullCount++;
         }
         
         if(instituteFeeManagement.getFilter().getDueDate()==null||instituteFeeManagement.getFilter().getDueDate().isEmpty()){
             nullCount++;
         }

          if(instituteFeeManagement.getFilter().getAuthStatus()==null||instituteFeeManagement.getFilter().getAuthStatus().isEmpty()){
             nullCount++;
         }
          
         
          if(nullCount==7){
              status=false;
              errhandler.log_app_error("BS_VAL_002","One Filter value");
          }
          
        dbg("end of student instituteFeeManagement master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student instituteFeeManagement detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<InstituteFeeManagementBO> reqBody = request.getReqBody();
             InstituteFeeManagementBO instituteFeeManagement =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_authStatus=instituteFeeManagement.getFilter().getAuthStatus();
             String l_feeType=instituteFeeManagement.getFilter().getFeeType();
             String l_dueDate=instituteFeeManagement.getFilter().getDueDate();
             
             
              if(l_instituteID!=null&&!l_instituteID.isEmpty()){
             
                    if(!bsv.instituteIDValidation( l_instituteID,errhandler,inject, session, dbSession)){
                         status=false;
                         errhandler.log_app_error("BS_VAL_003","instituteID");
                    }
            
              }
               
               if(l_feeType!=null&&!l_feeType.isEmpty()){
             
                    if(!bsv.feeTypeValidation(l_feeType, l_instituteID, session, dbSession, inject)){
                        status=false;
                        errhandler.log_app_error("BS_VAL_003","feeType");
                    }
                    
               }
               
               if(l_dueDate!=null&&!l_dueDate.isEmpty()){
             
                    if(!bsv.dateFormatValidation(l_dueDate, session, dbSession, inject)){
                        status=false;
                        errhandler.log_app_error("BS_VAL_003","feeManagementDate");
                    }
                    
               }
               
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
   

             
            dbg("end of student instituteFeeManagement detailDataValidation");
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
