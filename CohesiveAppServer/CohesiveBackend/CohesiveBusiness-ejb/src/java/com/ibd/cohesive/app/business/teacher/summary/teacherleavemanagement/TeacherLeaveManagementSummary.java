/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.summary.teacherleavemanagement;

import com.ibd.businessViews.ITeacherLeaveManagementSummary;
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
//@Local(ITeacherLeaveManagementSummary.class)
@Remote(ITeacherLeaveManagementSummary.class)
@Stateless
public class TeacherLeaveManagementSummary implements ITeacherLeaveManagementSummary{
       AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public TeacherLeaveManagementSummary(){
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
       dbg("inside TeacherLeaveManagementSummary--->processing");
       dbg("TeacherLeaveManagementSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<ITeacherLeaveManagementSummary>teacherLeaveManagementEJB=new BusinessEJB();
       teacherLeaveManagementEJB.set(this);
      
       exAudit=bs.getExistingAudit(teacherLeaveManagementEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,teacherLeaveManagementEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,teacherLeaveManagementEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in teacher leave");
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
               // if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
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
      TeacherLeaveManagementBO teacherLeaveManagement=new TeacherLeaveManagementBO();
      RequestBody<TeacherLeaveManagementBO> reqBody = new RequestBody<TeacherLeaveManagementBO>(); 
           
      try{
      dbg("inside teacher leave buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      BSValidation bsv=inject.getBsv(session);
      teacherLeaveManagement.filter=new TeacherLeaveManagementFilter();
//      teacherLeaveManagement.filter.setTeacherID(l_filterObject.getString("teacherID"));
//      teacherLeaveManagement.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      teacherLeaveManagement.filter.setAuthStatus(l_filterObject.getString("authStat"));
//      teacherLeaveManagement.filter.setFrom(l_filterObject.getString("from"));
//      teacherLeaveManagement.filter.setTo(l_filterObject.getString("to"));
//      teacherLeaveManagement.filter.setFromNoon(l_filterObject.getString("fromNoon"));
//      teacherLeaveManagement.filter.setToNoon(l_filterObject.getString("toNoon"));
//      teacherLeaveManagement.filter.setType(l_filterObject.getString("type"));
//      teacherLeaveManagement.filter.setStatus(l_filterObject.getString("status"));

      
      teacherLeaveManagement.filter.setTeacherID(l_filterObject.getString("teacherID"));
      teacherLeaveManagement.filter.setTeacherName(l_filterObject.getString("teacherName"));
      teacherLeaveManagement.filter.setFrom(l_filterObject.getString("from"));
      teacherLeaveManagement.filter.setTo(l_filterObject.getString("to"));
      
      if(l_filterObject.getString("leaveStatus").equals("Select option")){
          
          teacherLeaveManagement.filter.setLeaveStatus("");
      }else{
      
          teacherLeaveManagement.filter.setLeaveStatus(l_filterObject.getString("leaveStatus"));
      }


      
        reqBody.set(teacherLeaveManagement);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside teacher leave--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<TeacherLeaveManagementBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_leaveList=new ArrayList();
        TeacherLeaveManagementBO teacherLeaveManagement=reqBody.get();
        String l_teacherID=teacherLeaveManagement.getFilter().getTeacherID();
        ArrayList<String>l_fileNames=bs.getTeacherFileNames(l_teacherID, request, session, dbSession, inject);
        
        
        
          for(int i=0;i<l_fileNames.size();i++){
                dbg("inside file name iteration");
                String l_fileName=l_fileNames.get(i);
                dbg("l_fileName"+l_fileName);  
                Map<String,DBRecord>leaveMap=null;
                
                try{
            
   
                      leaveMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileName+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE", "TVW_TEACHER_LEAVE_MANAGEMENT", session, dbSession);

                       Iterator<DBRecord>valueIterator=leaveMap.values().iterator();

                        while(valueIterator.hasNext()){
                           DBRecord leaveRec=valueIterator.next();
                           l_leaveList.add(leaveRec);

                        }
                      
                }catch(DBValidationException ex){
            
                    if(ex.toString().contains("DB_VAL_000")||ex.toString().contains("DB_VAL_011")){

                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
//                        session.getErrorhandler().log_app_error("BS_VAL_013", null);
//                        throw new BSValidationException("BSValidationException");

                    }else{
                        throw ex;
                    }
            
                 }
               
           
            
          }
            
            if(l_leaveList.isEmpty()){
                
                session.getErrorhandler().log_app_error("BS_VAL_013", null);
                throw new BSValidationException("BSValidationException");
                
            }
        

        buildBOfromDB(l_leaveList);     
        
          dbg("end of  completed teacher leave--->view");  
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
    

    
    private void buildBOfromDB(ArrayList<DBRecord>l_leaveList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<TeacherLeaveManagementBO> reqBody = request.getReqBody();
           TeacherLeaveManagementBO teacherLeaveManagement =reqBody.get();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           String l_instituteID=request.getReqHeader().getInstituteID();
           String l_leaveStatus=teacherLeaveManagement.getFilter().getLeaveStatus();
           String l_teacherID=teacherLeaveManagement.getFilter().getTeacherID();
           String l_from=teacherLeaveManagement.getFilter().getFrom();
           String l_to=teacherLeaveManagement.getFilter().getTo();
           String dateFormat=i_db_properties.getProperty("DATE_FORMAT");
           SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
           ArrayList<DBRecord>filteredList=new ArrayList();
           BusinessService bs=inject.getBusinessService(session);
//           String teacherName=bs.getTeacherName(l_teacherID, l_instituteID, session, dbSession, inject);
//           teacherLeaveManagement.getFilter().setTeacherName(teacherName);
           
           
           dbg("l_leaveStatus"+l_leaveStatus);
           dbg("l_teacherID"+l_teacherID);
           dbg("l_from"+l_from);
           dbg("l_to"+l_to);
           
           
           
           
           if(l_teacherID!=null&&!l_teacherID.isEmpty()){
            
             List<DBRecord>  l_teacherList=  l_leaveList.stream().filter(rec->rec.getRecord().get(0).trim().equals(l_teacherID)).collect(Collectors.toList());
             l_leaveList = new ArrayList<DBRecord>(l_teacherList);
             dbg("teacherID filter l_leaveList size"+l_leaveList.size());
           }
           
           if(l_leaveStatus!=null&&!l_leaveStatus.isEmpty()){
               
               List<DBRecord>  l_teacherList=  l_leaveList.stream().filter(rec->rec.getRecord().get(10).trim().equals(l_leaveStatus)).collect(Collectors.toList());
               l_leaveList = new ArrayList<DBRecord>(l_teacherList);
               dbg("l_leaveStatus filter l_leaveList size"+l_leaveList.size());
               
           }
           
           for(int i=0;i<l_leaveList.size();i++){
               
               DBRecord l_teacherRecord=l_leaveList.get(i);
               
               String l_dbFromDate=l_teacherRecord.getRecord().get(1).trim();
               Date dbFromDate=formatter.parse(l_dbFromDate);
               Date fromDate=formatter.parse(l_from);
               Date toDate=formatter.parse(l_to);
               dbg("dbFromDate"+dbFromDate);
               dbg("fromDate"+fromDate);
               dbg("toDate"+toDate);
               
                if(dbFromDate.compareTo(fromDate)>=0){
                    
                    dbg("eventDate.compareTo(fromDate)>=0");
                    
                    if(dbFromDate.compareTo(toDate)<=0){
                       
                           
                           dbg("paymentDate.compareTo(toDate)<=0");
                       

                               filteredList.add(l_teacherRecord);
                       
                       
                    }
                    
                }
           }
           

          
           
           
           
           teacherLeaveManagement.result=new TeacherLeaveManagementResult[filteredList.size()];
           for(int i=0;i<filteredList.size();i++){
               
               ArrayList<String> l_teacherLeaveManagementList=filteredList.get(i).getRecord();
               teacherLeaveManagement.result[i]=new TeacherLeaveManagementResult();
               
               String teacherID=l_teacherLeaveManagementList.get(0).trim();
               teacherLeaveManagement.result[i].setTeacherID(l_teacherLeaveManagementList.get(0).trim());
               teacherLeaveManagement.result[i].setTeacherName(bs.getTeacherName(teacherID, l_instituteID, session, dbSession, inject));
               teacherLeaveManagement.result[i].setFrom(l_teacherLeaveManagementList.get(1).trim());
               teacherLeaveManagement.result[i].setTo(l_teacherLeaveManagementList.get(2).trim());
               
               String type=l_teacherLeaveManagementList.get(3).trim();
               
               if(type.equals("S")){
                   
                   teacherLeaveManagement.result[i].setType("Sick");
               }else if(type.equals("P")){
                   
                   teacherLeaveManagement.result[i].setType("Planned");
               }else if(type.equals("C")){
                   
                   teacherLeaveManagement.result[i].setType("Casual");
               }
               
               
               
//               teacherLeaveManagement.result[i].setType(l_teacherLeaveManagementList.get(3).trim());
               
               String authStatus=l_teacherLeaveManagementList.get(10).trim();
               
               if(authStatus.equals("U")){
                   
                   teacherLeaveManagement.result[i].setLeaveStatus("Pending");
               }else if(authStatus.equals("A")){
                   
                   teacherLeaveManagement.result[i].setLeaveStatus("Approved");
               }else if(authStatus.equals("R")){
                   
                   teacherLeaveManagement.result[i].setLeaveStatus("Rejected");
               }
               
          }    
           
           
           if(teacherLeaveManagement.result==null||teacherLeaveManagement.result.length==0){
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
        dbg("inside teacher leave buildJsonResFromBO");    
        RequestBody<TeacherLeaveManagementBO> reqBody = request.getReqBody();
        TeacherLeaveManagementBO teacherLeaveManagement =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<teacherLeaveManagement.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("teacherID", teacherLeaveManagement.result[i].getTeacherID())
                                                      .add("teacherName", teacherLeaveManagement.result[i].getTeacherName())
                                                      .add("from", teacherLeaveManagement.result[i].getFrom())
                                                      .add("to", teacherLeaveManagement.result[i].getTo())
                                                      .add("leaveStatus", teacherLeaveManagement.result[i].getLeaveStatus())
                                                      .add("type", teacherLeaveManagement.result[i].getType()));
        }

           filter=Json.createObjectBuilder()  .add("teacherID",teacherLeaveManagement.filter.getTeacherID())
                                              .add("leaveStatus", teacherLeaveManagement.filter.getLeaveStatus())
                                              .add("from", teacherLeaveManagement.filter.getFrom())
                                              .add("to",  teacherLeaveManagement.filter.getTo())
                                              .add("teacherName",teacherLeaveManagement.filter.getTeacherName())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of teacher leave buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside teacher leave--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of teacher leave--->businessValidation"); 
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
        dbg("inside teacher leave master mandatory validation");
        RequestBody<TeacherLeaveManagementBO> reqBody = request.getReqBody();
        TeacherLeaveManagementBO teacherLeaveManagement =reqBody.get();
       boolean fromDateEmpty=false;
        boolean toDateEmpty=false;
        
//         if(teacherLeaveManagement.getFilter().getTeacherID()==null||teacherLeaveManagement.getFilter().getTeacherID().isEmpty()){
//             status=false;  
//             errhandler.log_app_error("BS_VAL_002","teacherID");
//         }
          
           if(teacherLeaveManagement.getFilter().getFrom()==null||teacherLeaveManagement.getFilter().getFrom().isEmpty()){
              fromDateEmpty=true;
         }
          
          if(teacherLeaveManagement.getFilter().getTo()==null||teacherLeaveManagement.getFilter().getTo().isEmpty()){
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
          
        dbg("end of teacher leave master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside teacher leave detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<TeacherLeaveManagementBO> reqBody = request.getReqBody();
             BusinessService bs=inject.getBusinessService(session);
             TeacherLeaveManagementBO teacherLeaveManagement =reqBody.get();
             String l_teacherID=teacherLeaveManagement.getFilter().getTeacherID();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_leaveStatus=teacherLeaveManagement.getFilter().getLeaveStatus();
             String l_fromDate=teacherLeaveManagement.getFilter().getFrom();
             String l_toDate=teacherLeaveManagement.getFilter().getTo();
             
             if(l_teacherID!=null&&!l_teacherID.isEmpty()){
                 
                if(!bsv.teacherIDValidation(l_teacherID, l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","teacherID");
                }
                 
             }
             
             
             if(l_leaveStatus!=null&&!l_leaveStatus.isEmpty()){
                 
                if(!bsv.authStatusValidation(l_leaveStatus, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","Leave status");
                }
                 
             }
             
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
                 
//                 if(!bsv.futureDateValidation(l_fromDate, session, dbSession, inject)){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_048",null);
//                     throw new BSValidationException("BSValidationException");
//                 }
//                 
//                 if(!bsv.futureDateValidation(l_toDate, session, dbSession, inject)){
//                     status=false;
//                     errhandler.log_app_error("BS_VAL_048",null);
//                     throw new BSValidationException("BSValidationException");
//                 }
                 
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
             
             
            dbg("end of teacher leave detailDataValidation");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
         } catch(BSValidationException ex){
            throw ex;    
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
