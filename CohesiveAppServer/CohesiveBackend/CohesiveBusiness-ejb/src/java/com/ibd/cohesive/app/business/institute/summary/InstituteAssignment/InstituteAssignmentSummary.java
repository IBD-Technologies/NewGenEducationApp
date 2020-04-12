/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.InstituteAssignment;

import com.ibd.businessViews.IInstituteAssignmentSummary;
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
//@Local(IInstituteAssignmentSummary.class)
@Remote(IInstituteAssignmentSummary.class)
@Stateless
public class InstituteAssignmentSummary implements IInstituteAssignmentSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public InstituteAssignmentSummary(){
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
       dbg("inside InstituteAssignmentSummary--->processing");
       dbg("InstituteAssignmentSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IInstituteAssignmentSummary>instituteAssignmentEJB=new BusinessEJB();
       instituteAssignmentEJB.set(this);
      
       exAudit=bs.getExistingAudit(instituteAssignmentEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,instituteAssignmentEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,instituteAssignmentEJB);
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
      InstituteAssignmentBO instituteAssignment=new InstituteAssignmentBO();
      RequestBody<InstituteAssignmentBO> reqBody = new RequestBody<InstituteAssignmentBO>(); 
           
      try{
      dbg("inside student instituteAssignment buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      instituteAssignment.filter=new InstituteAssignmentFilter();
//      instituteAssignment.filter.setInstituteID(l_filterObject.getString("instituteID"));
//      instituteAssignment.filter.setAssignmentID(l_filterObject.getString("assignmentID"));
//      instituteAssignment.filter.setAssignmentType(l_filterObject.getString("assignmentType"));
//      instituteAssignment.filter.setSubjectID(l_filterObject.getString("subjectID"));
//      instituteAssignment.filter.setDueDate(l_filterObject.getString("dueDate"));
//      instituteAssignment.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      instituteAssignment.filter.setAuthStatus(l_filterObject.getString("authStat"));

       
      if(l_filterObject.getString("assignmentType").equals("Select option")){

          instituteAssignment.filter.setAssignmentType("");
      }else{

          instituteAssignment.filter.setAssignmentType(l_filterObject.getString("assignmentType"));
      }
      if(l_filterObject.getString("subjectID").equals("Select option")){
          
          instituteAssignment.filter.setSubjectID("");
      }else{
      
          instituteAssignment.filter.setSubjectID(l_filterObject.getString("subjectID"));
      }
       instituteAssignment.filter.setDueDate(l_filterObject.getString("dueDate"));
       
       if(l_filterObject.getString("authStat").equals("Select option")){
          
          instituteAssignment.filter.setAuthStatus("");
      }else{
      
          instituteAssignment.filter.setAuthStatus(l_filterObject.getString("authStat"));
      }

       
      
        reqBody.set(instituteAssignment);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student instituteAssignment--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_instituteAssignmentList=new ArrayList();
        String l_instituteID=request.getReqHeader().getInstituteID();
        
        
        
        try{
        
            ArrayList<String>l_fileNames=bs.getInstituteFileNames(l_instituteID,request,session,dbSession,inject);
            for(int i=0;i<l_fileNames.size();i++){
                dbg("inside file name iteration");
                String l_fileName=l_fileNames.get(i);
                dbg("l_fileName"+l_fileName);
                Map<String,DBRecord>instituteAssignmentMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT","IVW_ASSIGNMENT", session, dbSession);

                Iterator<DBRecord>valueIterator=instituteAssignmentMap.values().iterator();

                while(valueIterator.hasNext()){
                   DBRecord instituteAssignmentRec=valueIterator.next();
                   l_instituteAssignmentList.add(instituteAssignmentRec);

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

        buildBOfromDB(l_instituteAssignmentList);     
        
          dbg("end of  completed student instituteAssignment--->view");  
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
    

    
    private void buildBOfromDB(ArrayList<DBRecord>l_instituteAssignmentList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<InstituteAssignmentBO> reqBody = request.getReqBody();
           InstituteAssignmentBO instituteAssignment =reqBody.get();
           String l_authStatus=instituteAssignment.getFilter().getAuthStatus();
           String l_assignmentType=instituteAssignment.getFilter().getAssignmentType();
           String l_dueDate=instituteAssignment.getFilter().getDueDate();
           String l_subjectID=instituteAssignment.getFilter().getSubjectID();
           BusinessService bs=inject.getBusinessService(session);
           

           dbg("l_authStatus"+l_authStatus);
           dbg("l_dueDate"+l_dueDate);
           dbg("l_assignmentType"+l_assignmentType);
           dbg("l_subjectID"+l_subjectID);

           
           if(l_assignmentType!=null&&!l_assignmentType.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_instituteAssignmentList.stream().filter(rec->rec.getRecord().get(5).trim().equals(l_assignmentType)).collect(Collectors.toList());
             l_instituteAssignmentList = new ArrayList<DBRecord>(l_studentList);
             dbg("l_assignmentType filter l_instituteAssignmentList size"+l_instituteAssignmentList.size());
           }
           
           if(l_dueDate!=null&&!l_dueDate.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_instituteAssignmentList.stream().filter(rec->rec.getRecord().get(6).trim().equals(l_dueDate)).collect(Collectors.toList());
             l_instituteAssignmentList = new ArrayList<DBRecord>(l_studentList);
             dbg("l_assignmentDate filter l_instituteAssignmentList size"+l_instituteAssignmentList.size());
           }
           
           if(l_subjectID!=null&&!l_subjectID.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_instituteAssignmentList.stream().filter(rec->rec.getRecord().get(4).trim().equals(l_subjectID)).collect(Collectors.toList());
             l_instituteAssignmentList = new ArrayList<DBRecord>(l_studentList);
             dbg("l_subjectID filter l_instituteAssignmentList size"+l_instituteAssignmentList.size());
           }

           
           if(l_authStatus!=null&&!l_authStatus.isEmpty()){
               
               List<DBRecord>  l_studentList=  l_instituteAssignmentList.stream().filter(rec->rec.getRecord().get(13).trim().equals(l_authStatus)).collect(Collectors.toList());
               l_instituteAssignmentList = new ArrayList<DBRecord>(l_studentList);
               dbg("authStatus filter l_instituteAssignmentList size"+l_instituteAssignmentList.size());
               
           }
           
           instituteAssignment.result=new InstituteAssignmentResult[l_instituteAssignmentList.size()];
           for(int i=0;i<l_instituteAssignmentList.size();i++){
               
               ArrayList<String> l_instituteAssignmentRecords=l_instituteAssignmentList.get(i).getRecord();
               instituteAssignment.result[i]=new InstituteAssignmentResult();
               String instituteID=l_instituteAssignmentRecords.get(0).trim();
               instituteAssignment.result[i].setGroupID(l_instituteAssignmentRecords.get(1).trim());
               instituteAssignment.result[i].setAssignmentID(l_instituteAssignmentRecords.get(2).trim());
               instituteAssignment.result[i].setSubjectID(l_instituteAssignmentRecords.get(4).trim());
               instituteAssignment.result[i].setSubjectName(bs.getSubjectName(l_instituteAssignmentRecords.get(4).trim(), instituteID, session, dbSession, inject));
               String assignmentType=bs.getAssignmentType(l_instituteAssignmentRecords.get(5).trim());
               dbg("assignmentType"+assignmentType);
               instituteAssignment.result[i].setAssignmentType(assignmentType);
               instituteAssignment.result[i].setDueDate(l_instituteAssignmentRecords.get(6).trim());
          }    
           
           
           if(instituteAssignment.result==null||instituteAssignment.result.length==0){
               session.getErrorhandler().log_app_error("BS_VAL_013", null);
               throw new BSValidationException("BSValidationException");
           }
           
           
          dbg("end of  buildBOfromDB"); 
        }catch(DBValidationException ex){
          throw ex;   
        }catch(BSValidationException ex){
          throw ex;   
        }catch(DBProcessingException ex){
           dbg(ex);
           throw new DBProcessingException(ex.toString());  
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
 }
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        JsonObject filter;
        try{
        dbg("inside student instituteAssignment buildJsonResFromBO");    
        RequestBody<InstituteAssignmentBO> reqBody = request.getReqBody();
        InstituteAssignmentBO instituteAssignment =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<instituteAssignment.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("assignmentID", instituteAssignment.result[i].getAssignmentID())
                                                      .add("assignee", instituteAssignment.result[i].getGroupID())
                                                      .add("subjectID", instituteAssignment.result[i].getSubjectID())
                                                      .add("subjectName", instituteAssignment.result[i].getSubjectName())
                                                      .add("assignmentType", instituteAssignment.result[i].getAssignmentType())
                                                      .add("dueDate", instituteAssignment.result[i].getDueDate()));
        }

           filter=Json.createObjectBuilder()    .add("assignmentType", instituteAssignment.filter.getAssignmentType())
                                              .add("subjectID", instituteAssignment.filter.getSubjectID())
                                              .add("dueDate", instituteAssignment.filter.getDueDate())
                                              .add("authStatus", instituteAssignment.filter.getAuthStatus())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of student instituteAssignment buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student instituteAssignment--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of student instituteAssignment--->businessValidation"); 
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
        dbg("inside student instituteAssignment master mandatory validation");
        RequestBody<InstituteAssignmentBO> reqBody = request.getReqBody();
        InstituteAssignmentBO instituteAssignment =reqBody.get();
        int nullCount=0;
         

         
         if(instituteAssignment.getFilter().getSubjectID()==null||instituteAssignment.getFilter().getSubjectID().isEmpty()){
             nullCount++;
         }
         if(instituteAssignment.getFilter().getAssignmentType()==null||instituteAssignment.getFilter().getAssignmentType().isEmpty()){
             nullCount++;
         }
         
         if(instituteAssignment.getFilter().getDueDate()==null||instituteAssignment.getFilter().getDueDate().isEmpty()){
             nullCount++;
         }

          if(instituteAssignment.getFilter().getAuthStatus()==null||instituteAssignment.getFilter().getAuthStatus().isEmpty()){
             nullCount++;
         }
          
         
          if(nullCount==4){
              status=false;
              errhandler.log_app_error("BS_VAL_002","One Filter value");
          }
          
        dbg("end of student instituteAssignment master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student instituteAssignment detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<InstituteAssignmentBO> reqBody = request.getReqBody();
             InstituteAssignmentBO instituteAssignment =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_authStatus=instituteAssignment.getFilter().getAuthStatus();
             String l_subjectID=instituteAssignment.getFilter().getSubjectID();
             String l_dueDate=instituteAssignment.getFilter().getDueDate();
             
             
              if(l_instituteID!=null&&!l_instituteID.isEmpty()){
             
                    if(!bsv.instituteIDValidation( l_instituteID,errhandler,inject, session, dbSession)){
                         status=false;
                         errhandler.log_app_error("BS_VAL_003","instituteID");
                    }
            
              }
             
               if(l_subjectID!=null&&!l_subjectID.isEmpty()){
             
                    if(!bsv.subjectIDValidation(l_subjectID, l_instituteID, session, dbSession, inject)){
                        status=false;
                        errhandler.log_app_error("BS_VAL_003","subjectID");
                    }
                    
               }
              
               
               if(l_dueDate!=null&&!l_dueDate.isEmpty()){
             
                    if(!bsv.dateFormatValidation(l_dueDate, session, dbSession, inject)){
                        status=false;
                        errhandler.log_app_error("BS_VAL_003","assignmentDate");
                    }
                    
               }
               

             
             if(l_authStatus!=null&&!l_authStatus.isEmpty()){
                 
                if(!bsv.authStatusValidation(l_authStatus, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","authStatus");
                }
                 
             }
   

             
            dbg("end of student instituteAssignment detailDataValidation");
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
