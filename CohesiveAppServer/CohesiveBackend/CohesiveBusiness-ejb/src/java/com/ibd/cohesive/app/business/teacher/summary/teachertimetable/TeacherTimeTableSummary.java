/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.summary.teachertimetable;

import com.ibd.businessViews.ITeacherTimeTableSummary;
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
//@Local(ITeacherTimeTableSummary.class)
@Remote(ITeacherTimeTableSummary.class)
@Stateless
public class TeacherTimeTableSummary implements ITeacherTimeTableSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public TeacherTimeTableSummary(){
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
       dbg("inside TeacherTimeTableSummary--->processing");
       dbg("TeacherTimeTableSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<ITeacherTimeTableSummary>teacherTimeTableEJB=new BusinessEJB();
       teacherTimeTableEJB.set(this);
      
       exAudit=bs.getExistingAudit(teacherTimeTableEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,teacherTimeTableEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,teacherTimeTableEJB);
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
                dbg("Response"+jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
               /* if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                   BSProcessingException ex=new BSProcessingException("response contains special characters");
                   dbg(ex);
                   session.clearSessionObject();
                   dbSession.clearSessionObject();
                   throw ex;
                }*/
                      clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes   
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
      TeacherTimeTableBO teacherTimeTable=new TeacherTimeTableBO();
      RequestBody<TeacherTimeTableBO> reqBody = new RequestBody<TeacherTimeTableBO>(); 
           
      try{
      dbg("inside teacher leave buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      teacherTimeTable.filter=new TeacherTimeTableFilter();
      teacherTimeTable.filter.setTeacherID(l_filterObject.getString("teacherID"));
      teacherTimeTable.filter.setTeacherName(l_filterObject.getString("teacherName"));
//      teacherTimeTable.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      teacherTimeTable.filter.setAuthStatus(l_filterObject.getString("authStat"));

      
      if(l_filterObject.getString("authStat").equals("Select option")){
          
          teacherTimeTable.filter.setAuthStatus("");
      }else{
      
          teacherTimeTable.filter.setAuthStatus(l_filterObject.getString("authStat"));
      }

      
        reqBody.set(teacherTimeTable);
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
        RequestBody<TeacherTimeTableBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_leaveList=new ArrayList();
        TeacherTimeTableBO teacherTimeTable=reqBody.get();
        String l_teacherID=teacherTimeTable.getFilter().getTeacherID();
        String l_teacherNameFilter=teacherTimeTable.getFilter().getTeacherName();
        
        try{
        
            ArrayList<String>l_fileNames=bs.getTeacherFileNames(l_teacherID,request,session,dbSession,inject);
            for(int i=0;i<l_fileNames.size();i++){
                dbg("inside file name iteration");
                String l_fileName=l_fileNames.get(i);
                dbg("l_fileName"+l_fileName);
                
                String teacherName=bs.getTeacherName(l_teacherID, l_instituteID, session, dbSession, inject);
            
               if(teacherName.equals(l_teacherNameFilter)){
                
                Map<String,DBRecord>leaveMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileName+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileName,"TEACHER", "TVW_TEACHER_TIMETABLE_MASTER", session, dbSession);

                Iterator<DBRecord>valueIterator=leaveMap.values().iterator();

                while(valueIterator.hasNext()){
                   DBRecord leaveRec=valueIterator.next();
                   l_leaveList.add(leaveRec);

                }

                dbg("file name itertion completed for "+l_fileName);
                
               }
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
           RequestBody<TeacherTimeTableBO> reqBody = request.getReqBody();
           TeacherTimeTableBO teacherTimeTable =reqBody.get();
           String l_authStatus=teacherTimeTable.getFilter().getAuthStatus();
           String l_teacherID=teacherTimeTable.getFilter().getTeacherID();
           
           dbg("l_authStatus"+l_authStatus);
           dbg("l_teacherID"+l_teacherID);
           
           if(l_teacherID!=null&&!l_teacherID.isEmpty()){
            
             List<DBRecord>  l_teacherList=  l_leaveList.stream().filter(rec->rec.getRecord().get(0).trim().equals(l_teacherID)).collect(Collectors.toList());
             l_leaveList = new ArrayList<DBRecord>(l_teacherList);
             dbg("teacherID filter l_leaveList size"+l_leaveList.size());
           }
           
           if(l_authStatus!=null&&!l_authStatus.isEmpty()){
               
               List<DBRecord>  l_teacherList=  l_leaveList.stream().filter(rec->rec.getRecord().get(6).trim().equals(l_authStatus)).collect(Collectors.toList());
               l_leaveList = new ArrayList<DBRecord>(l_teacherList);
               dbg("authStatus filter l_leaveList size"+l_leaveList.size());
               
           }
           
           teacherTimeTable.result=new TeacherTimeTableResult[l_leaveList.size()];
           for(int i=0;i<l_leaveList.size();i++){
               
               ArrayList<String> l_teacherTimeTableList=l_leaveList.get(i).getRecord();
               teacherTimeTable.result[i]=new TeacherTimeTableResult();
               teacherTimeTable.result[i].setTeacherID(l_teacherTimeTableList.get(0).trim());
               teacherTimeTable.result[i].setMakerID(l_teacherTimeTableList.get(1).trim());
               teacherTimeTable.result[i].setCheckerID(l_teacherTimeTableList.get(2).trim());
               teacherTimeTable.result[i].setMakerDateStamp(l_teacherTimeTableList.get(3).trim());
               teacherTimeTable.result[i].setCheckerDateStamp(l_teacherTimeTableList.get(4).trim());
               teacherTimeTable.result[i].setRecordStatus(l_teacherTimeTableList.get(5).trim());
               teacherTimeTable.result[i].setAuthStatus(l_teacherTimeTableList.get(6).trim());
               teacherTimeTable.result[i].setVersionNumber(l_teacherTimeTableList.get(7).trim());
               teacherTimeTable.result[i].setMakerRemarks(l_teacherTimeTableList.get(8).trim());
               teacherTimeTable.result[i].setCheckerRemarks(l_teacherTimeTableList.get(9).trim());
          }    
           
           
           ErrorHandler errHandler=session.getErrorhandler();
           if(teacherTimeTable.result==null||teacherTimeTable.result.length==0){
               errHandler.log_app_error("BS_VAL_013", null);
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
        RequestBody<TeacherTimeTableBO> reqBody = request.getReqBody();
        TeacherTimeTableBO teacherTimeTable =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<teacherTimeTable.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("teacherID", teacherTimeTable.result[i].getTeacherID())
                                                      .add("makerID", teacherTimeTable.result[i].getMakerID())
                                                      .add("checkerID", teacherTimeTable.result[i].getCheckerID())
                                                      .add("makerDateStamp", teacherTimeTable.result[i].getMakerDateStamp())
                                                      .add("checkerDateStamp", teacherTimeTable.result[i].getCheckerDateStamp())
                                                      .add("recordStatus", teacherTimeTable.result[i].getRecordStatus())
                                                      .add("authStatus", teacherTimeTable.result[i].getAuthStatus())
                                                      .add("versionNumber", teacherTimeTable.result[i].getVersionNumber())
                                                      .add("makerRemarks", teacherTimeTable.result[i].getMakerRemarks())
                                                      .add("checkerRemarks", teacherTimeTable.result[i].getCheckerRemarks()));
        }

           filter=Json.createObjectBuilder()  .add("teacherID",teacherTimeTable.filter.getTeacherID())
                                              .add("teacherName",teacherTimeTable.filter.getTeacherName())
                                              .add("authStatus", teacherTimeTable.filter.getAuthStatus())
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
        RequestBody<TeacherTimeTableBO> reqBody = request.getReqBody();
        TeacherTimeTableBO teacherTimeTable =reqBody.get();
        int nullCount=0;
        
         if(teacherTimeTable.getFilter().getTeacherName()==null||teacherTimeTable.getFilter().getTeacherName().isEmpty()){
             nullCount++;
         }

          if(teacherTimeTable.getFilter().getAuthStatus()==null||teacherTimeTable.getFilter().getAuthStatus().isEmpty()){
             nullCount++;
         }
          
         
          if(nullCount==2){
              status=false;
              errhandler.log_app_error("BS_VAL_002","One Filter value");
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
             RequestBody<TeacherTimeTableBO> reqBody = request.getReqBody();
             TeacherTimeTableBO teacherTimeTable =reqBody.get();
             String l_teacherID=teacherTimeTable.getFilter().getTeacherID();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_authStatus=teacherTimeTable.getFilter().getAuthStatus();
             
             if(l_teacherID!=null&&!l_teacherID.isEmpty()){
                 
                if(!bsv.teacherIDValidation(l_teacherID, l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","teacherID");
                }
                 
             }

             
             if(l_authStatus!=null&&!l_authStatus.isEmpty()){
                 
                if(!bsv.authStatusValidation(l_authStatus, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","authStatus");
                }
                 
             }
   

             
            dbg("end of teacher leave detailDataValidation");
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
