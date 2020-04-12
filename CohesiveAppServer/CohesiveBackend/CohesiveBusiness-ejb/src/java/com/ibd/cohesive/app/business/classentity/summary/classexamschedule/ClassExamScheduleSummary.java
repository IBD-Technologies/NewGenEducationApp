/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.summary.classexamschedule;

import com.ibd.businessViews.IClassExamScheduleSummary;
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
//@Local(IClassExamScheduleSummary.class)
@Remote(IClassExamScheduleSummary.class)
@Stateless
public class ClassExamScheduleSummary implements IClassExamScheduleSummary{
    
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public ClassExamScheduleSummary(){
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
       dbg("inside ClassExamScheduleSummary--->processing");
       dbg("ClassExamScheduleSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IClassExamScheduleSummary>classExamScheduleEJB=new BusinessEJB();
       classExamScheduleEJB.set(this);
      
       exAudit=bs.getExistingAudit(classExamScheduleEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,classExamScheduleEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,classExamScheduleEJB);
              tc.commit(session,dbSession);
//              dbg("commit is called in class leave");
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
                dbg("Response-->"+jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
              clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes
//           if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
  //                 BSProcessingException ex=new BSProcessingException("response contains special characters");
    //               dbg(ex);
      //             session.clearSessionObject();
        //           dbSession.clearSessionObject();
          //         throw ex;
            //    }
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

    private  void buildBOfromReq(JsonObject p_request)throws BSProcessingException,DBProcessingException,BSValidationException{
      ClassExamScheduleBO classExamSchedule=new ClassExamScheduleBO();
      RequestBody<ClassExamScheduleBO> reqBody = new RequestBody<ClassExamScheduleBO>(); 
           
      try{
      dbg("inside class leave buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      classExamSchedule.filter=new ClassExamScheduleFilter();
      BSValidation bsv=inject.getBsv(session);
//      classExamSchedule.filter.setStandard(l_filterObject.getString("standard"));
//      classExamSchedule.filter.setSection(l_filterObject.getString("section"));
      if(l_filterObject.getString("class").equals("Select option")){
          classExamSchedule.filter.setStandard("");
          classExamSchedule.filter.setSection("");
      }else{

          String l_class=l_filterObject.getString("class");
          bsv.classValidation(l_class,session);
          classExamSchedule.filter.setStandard(l_class.split("/")[0]);
          classExamSchedule.filter.setSection(l_class.split("/")[1]);
      
      }
      
      if(l_filterObject.getString("authStat").equals("Select option")){
          
          classExamSchedule.filter.setAuthStatus("");
      }else{
      
          classExamSchedule.filter.setAuthStatus(l_filterObject.getString("authStat"));
      }
      
      
      if(l_filterObject.getString("exam").equals("Select option")){
          
          classExamSchedule.filter.setExam("");
      }else{
      
          classExamSchedule.filter.setExam(l_filterObject.getString("exam"));

      }
      
        reqBody.set(classExamSchedule);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(BSValidationException ex){
         throw ex;   
     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside class leave--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassExamScheduleBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_leaveList=new ArrayList();
        ClassExamScheduleBO classExamSchedule=reqBody.get();
        String l_standard=classExamSchedule.getFilter().getStandard();
        String l_section=classExamSchedule.getFilter().getSection();
        
        
        try{
        
            ArrayList<String>l_fileNames=bs.getClassFileNames(l_standard,l_section,request,session,dbSession,inject);
            Map<String,DBRecord>leaveMap=null;
            for(int i=0;i<l_fileNames.size();i++){
                dbg("inside file name iteration");
                String l_fileName=l_fileNames.get(i);
                dbg("l_fileName"+l_fileName);
                
                String standard=l_fileName.split("~")[0];
                String section=l_fileName.split("~")[1];
                
                try{
                leaveMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS", "CLASS_EXAM_SCHEDULE_MASTER", session, dbSession);
                }
                catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_000")||ex.toString().contains("DB_VAL_011")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                //session.getErrorhandler().log_app_error("BS_VAL_013", null);
              //  throw new BSValidationException("BSValidationException");
                
            }else{
                throw ex;
            }

                }   
                if(leaveMap!=null &&!leaveMap.isEmpty())
                {   
                Iterator<DBRecord>valueIterator=leaveMap.values().iterator();

                while(valueIterator.hasNext()){
                   DBRecord leaveRec=valueIterator.next();
                   l_leaveList.add(leaveRec);

                }
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
        
         if(l_leaveList.isEmpty()){
               session.getErrorhandler().log_app_error("BS_VAL_013", null);
               throw new BSValidationException("BSValidationException");
           }


        buildBOfromDB(l_leaveList);     
        
          dbg("end of  completed class leave--->view");   
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
           RequestBody<ClassExamScheduleBO> reqBody = request.getReqBody();
           ClassExamScheduleBO classExamSchedule =reqBody.get();
           String l_recordStatus=classExamSchedule.getFilter().getRecordStatus();
           String l_authStatus=classExamSchedule.getFilter().getAuthStatus();
           String l_standard=classExamSchedule.getFilter().getStandard();
           String l_section=classExamSchedule.getFilter().getSection();
           String l_exam=classExamSchedule.getFilter().getExam();
           
           dbg("l_recordStatus"+l_recordStatus);
           dbg("l_authStatus"+l_authStatus);
           dbg("l_standard"+l_standard);
           dbg("l_section"+l_section);
           dbg("l_exam"+l_exam);

           
           
           
           
           if(l_standard!=null&&!l_standard.isEmpty()&&l_section!=null&&!l_section.isEmpty()){
            
             List<DBRecord>  l_classList=  l_leaveList.stream().filter(rec->rec.getRecord().get(0).trim().equals(l_standard)&&rec.getRecord().get(1).trim().equals(l_section)).collect(Collectors.toList());
             l_leaveList = new ArrayList<DBRecord>(l_classList);
             dbg("l_standard  l_section filter l_leaveList size"+l_leaveList.size());
           }
           
           if(l_exam!=null&&!l_exam.isEmpty()){
            
             List<DBRecord>  l_classList=  l_leaveList.stream().filter(rec->rec.getRecord().get(2).trim().equals(l_exam)).collect(Collectors.toList());
             l_leaveList = new ArrayList<DBRecord>(l_classList);
             dbg("l_exam filter l_leaveList size"+l_leaveList.size());
           }
           
//           
//           if(l_recordStatus!=null&&!l_recordStatus.isEmpty()){
//               
//               List<DBRecord>  l_classList=  l_leaveList.stream().filter(rec->rec.getRecord().get(7).trim().equals(l_recordStatus)).collect(Collectors.toList());
//               l_leaveList = new ArrayList<DBRecord>(l_classList);
//               dbg("recordStatus filter l_leaveList size"+l_leaveList.size());
//           }
           
           if(l_authStatus!=null&&!l_authStatus.isEmpty()){
               
               List<DBRecord>  l_classList=  l_leaveList.stream().filter(rec->rec.getRecord().get(8).trim().equals(l_authStatus)).collect(Collectors.toList());
               l_leaveList = new ArrayList<DBRecord>(l_classList);
               dbg("authStatus filter l_leaveList size"+l_leaveList.size());
               
           }
           
           classExamSchedule.result=new ClassExamScheduleResult[l_leaveList.size()];
           for(int i=0;i<l_leaveList.size();i++){
               
               ArrayList<String> l_classExamScheduleList=l_leaveList.get(i).getRecord();
               classExamSchedule.result[i]=new ClassExamScheduleResult();
               classExamSchedule.result[i].setStandard(l_classExamScheduleList.get(0).trim());
               classExamSchedule.result[i].setSection(l_classExamScheduleList.get(1).trim());
               classExamSchedule.result[i].setExam(l_classExamScheduleList.get(2).trim());
               classExamSchedule.result[i].setMakerID(l_classExamScheduleList.get(3).trim());
               classExamSchedule.result[i].setCheckerID(l_classExamScheduleList.get(4).trim());
               classExamSchedule.result[i].setMakerDateStamp(l_classExamScheduleList.get(5).trim());
               classExamSchedule.result[i].setCheckerDateStamp(l_classExamScheduleList.get(6).trim());
               classExamSchedule.result[i].setRecordStatus(l_classExamScheduleList.get(7).trim());
               classExamSchedule.result[i].setAuthStatus(l_classExamScheduleList.get(8).trim());
               classExamSchedule.result[i].setVersionNumber(l_classExamScheduleList.get(9).trim());
               classExamSchedule.result[i].setMakerRemarks(l_classExamScheduleList.get(10).trim());
               classExamSchedule.result[i].setCheckerRemarks(l_classExamScheduleList.get(11).trim());
          }    
           
           if(classExamSchedule.result==null||classExamSchedule.result.length==0){
               session.getErrorhandler().log_app_error("BS_VAL_013", null);
               throw new BSValidationException("BSValidationException");
           }
           
           
           
          dbg("end of  buildBOfromDB"); 
           

        }catch(BSValidationException ex){
          throw ex;

        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
 }
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        JsonObject filter;
        try{
        dbg("inside class leave buildJsonResFromBO");    
        RequestBody<ClassExamScheduleBO> reqBody = request.getReqBody();
        ClassExamScheduleBO classExamSchedule =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<classExamSchedule.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("class", classExamSchedule.result[i].getStandard()+"/"+classExamSchedule.result[i].getSection())
                                                      .add("exam", classExamSchedule.result[i].getExam())
                                                      .add("makerID", classExamSchedule.result[i].getMakerID())
                                                      .add("checkerID", classExamSchedule.result[i].getCheckerID())
                                                      .add("makerDateStamp", classExamSchedule.result[i].getMakerDateStamp())
                                                      .add("checkerDateStamp", classExamSchedule.result[i].getCheckerDateStamp())
                                                      .add("recordStatus", classExamSchedule.result[i].getRecordStatus())
                                                      .add("authStatus", classExamSchedule.result[i].getAuthStatus())
                                                      .add("versionNumber", classExamSchedule.result[i].getVersionNumber())
                                                      .add("makerRemarks", classExamSchedule.result[i].getMakerRemarks())
                                                      .add("checkerRemarks", classExamSchedule.result[i].getCheckerRemarks()));
        }

           filter=Json.createObjectBuilder()   .add("class", classExamSchedule.filter.getStandard()+"/"+ classExamSchedule.filter.getSection())
//                                              .add("recordStatus", classExamSchedule.filter.getRecordStatus())
                                              .add("authStatus", classExamSchedule.filter.getAuthStatus())
                                              .add("exam", classExamSchedule.filter.getExam())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of class leave buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside class leave--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of class leave--->businessValidation"); 
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
        dbg("inside class leave master mandatory validation");
        RequestBody<ClassExamScheduleBO> reqBody = request.getReqBody();
        ClassExamScheduleBO classExamSchedule =reqBody.get();
        int nullCount=0;
        
          if(classExamSchedule.getFilter().getStandard()==null||classExamSchedule.getFilter().getStandard().isEmpty()){
             nullCount++;
         }
         if(classExamSchedule.getFilter().getSection()==null||classExamSchedule.getFilter().getSection().isEmpty()){
             nullCount++;
         }
          if(classExamSchedule.getFilter().getAuthStatus()==null||classExamSchedule.getFilter().getAuthStatus().isEmpty()){
             nullCount++;
         }
          if(classExamSchedule.getFilter().getExam()==null||classExamSchedule.getFilter().getExam().isEmpty()){
             nullCount++;
         }
         
          if(nullCount==4){
              status=false;
              errhandler.log_app_error("BS_VAL_002","One Filter value");
          }
          
        dbg("end of class leave master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside class leave detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<ClassExamScheduleBO> reqBody = request.getReqBody();
             ClassExamScheduleBO classExamSchedule =reqBody.get();
             String l_standard=classExamSchedule.getFilter().getStandard();
             String l_section=classExamSchedule.getFilter().getSection();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_authStatus=classExamSchedule.getFilter().getAuthStatus();
             String l_exam=classExamSchedule.getFilter().getExam();
             
             if(l_standard!=null&&!l_standard.isEmpty()&&l_section!=null&&!l_section.isEmpty()){
                 
                if(!bsv.standardSectionValidation(l_standard,l_section, l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","standard or section");
                }
                 
             }
             
//             if(l_recordStatus!=null&&!l_recordStatus.isEmpty()){
//                 
//                if(!bsv.recordStatusValidation(l_recordStatus, session, dbSession, inject)){
//                    status=false;
//                    errhandler.log_app_error("BS_VAL_003","recordStatus");
//                }
//                 
//             }
             
             if(l_authStatus!=null&&!l_authStatus.isEmpty()){
                 
                if(!bsv.authStatusValidation(l_authStatus, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","authStatus");
                }
                 
             }
             
             
             
             if(l_exam!=null&&!l_exam.isEmpty()){
             
                 if(!bsv.examValidation(l_exam,l_instituteID, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","exam");
                 }
             
             }
             
            dbg("end of class leave detailDataValidation");
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
