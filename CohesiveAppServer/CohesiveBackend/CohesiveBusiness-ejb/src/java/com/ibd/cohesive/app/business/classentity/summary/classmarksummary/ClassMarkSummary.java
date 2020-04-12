/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.summary.classmarksummary;

import com.ibd.businessViews.IClassMarkSummary;
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
 * @author DELL
 */
//@Local(IClassMarkSummary.class)
@Remote(IClassMarkSummary.class)
@Stateless
public class ClassMarkSummary implements IClassMarkSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public ClassMarkSummary(){
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
       dbg("inside ClassMarkSummary--->processing");
       dbg("ClassMarkSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IClassMarkSummary>classMarkEJB=new BusinessEJB();
       classMarkEJB.set(this);
      
       exAudit=bs.getExistingAudit(classMarkEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,classMarkEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,classMarkEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in class mark");
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
                          clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes
//                if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
  //                 BSProcessingException ex=new BSProcessingException("response contains special characters");
    //               dbg(ex);
      //             session.clearSessionObject();
        //           dbSession.clearSessionObject();
          //         throw ex;
                //}
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
      ClassMarkBO classMark=new ClassMarkBO();
      RequestBody<ClassMarkBO> reqBody = new RequestBody<ClassMarkBO>(); 
           
      try{
      dbg("inside class mark buildBOfromReq");    
      BSValidation bsv=inject.getBsv(session);
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      classMark.filter=new ClassMarkFilter();
//      classMark.filter.setStandard(l_filterObject.getString("standard"));
//      classMark.filter.setSection(l_filterObject.getString("section"));
      if(l_filterObject.getString("class").equals("Select option")){
          classMark.filter.setStandard("");
          classMark.filter.setSection("");
      }else{

          String l_class=l_filterObject.getString("class");
          bsv.classValidation(l_class,session);
          classMark.filter.setStandard(l_class.split("/")[0]);
          classMark.filter.setSection(l_class.split("/")[1]);
      
      }
      if(l_filterObject.getString("subjectID").equals("Select option")){
          
          classMark.filter.setSubjectID("");
      }else{
      
          classMark.filter.setSubjectID(l_filterObject.getString("subjectID"));
      
      }
      
      if(l_filterObject.getString("authStat").equals("Select option")){
          
          classMark.filter.setAuthStatus("");
      }else{
      
          classMark.filter.setAuthStatus(l_filterObject.getString("authStat"));
      
      }
      
      if(l_filterObject.getString("exam").equals("Select option")){
          
          classMark.filter.setExam("");
      }else{
      
          classMark.filter.setExam(l_filterObject.getString("exam"));

      }
      

      
        reqBody.set(classMark);
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
        dbg("inside class mark--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ClassMarkBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_markList=new ArrayList();
        ClassMarkBO classMark=reqBody.get();
        String l_standard=classMark.getFilter().getStandard();
        String l_section=classMark.getFilter().getSection();
        
                  ArrayList<String>l_fileNames=bs.getClassFileNames(l_standard,l_section,request,session,dbSession,inject);
            for(int i=0;i<l_fileNames.size();i++){
        try{
        
  
                dbg("inside file name iteration");
                String l_fileName=l_fileNames.get(i);
                dbg("l_fileName"+l_fileName);
                
                String standard=l_fileName.split("~")[0];
                String section=l_fileName.split("~")[1];
                
                Map<String,DBRecord>markMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamMarkMaster","CLASS", "CLASS_MARK_ENTRY", session, dbSession);

                Iterator<DBRecord>valueIterator=markMap.values().iterator();

                while(valueIterator.hasNext()){
                   DBRecord markRec=valueIterator.next();
                   l_markList.add(markRec);

                }

                dbg("file name itertion completed for "+l_fileName);
            
        }catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_000")||ex.toString().contains("DB_VAL_011")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
//                session.getErrorhandler().log_app_error("BS_VAL_013", null);
//                throw new BSValidationException("BSValidationException");
                
            }else{
                throw ex;
            }
            
        }
        }
        
        if(l_markList.isEmpty()){
             session.getErrorhandler().log_app_error("BS_VAL_013", null);
             throw new BSValidationException("BSValidationException");
            
        }
        

        buildBOfromDB(l_markList);     
        
          dbg("end of  completed class mark--->view");      
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
    

    
    private void buildBOfromDB(ArrayList<DBRecord>l_markList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<ClassMarkBO> reqBody = request.getReqBody();
           ClassMarkBO classMark =reqBody.get();
           String instituteID=request.getReqHeader().getInstituteID();
           BusinessService bs=inject.getBusinessService(session);
           String l_authStatus=classMark.getFilter().getAuthStatus();
           String l_standard=classMark.getFilter().getStandard();
           String l_section=classMark.getFilter().getSection();
           String l_exam=classMark.getFilter().getExam();
           String l_subjectID=classMark.getFilter().getSubjectID();
           
           dbg("l_authStatus"+l_authStatus);
           dbg("l_standard"+l_standard);
           dbg("l_section"+l_section);
           dbg("l_exam"+l_exam);
           dbg("l_subjectID"+l_subjectID);
           
           
           
           
           if(l_standard!=null&&!l_standard.isEmpty()&&l_section!=null&&!l_section.isEmpty()){
            
             List<DBRecord>  l_classList=  l_markList.stream().filter(rec->rec.getRecord().get(0).trim().equals(l_standard)&&rec.getRecord().get(1).trim().equals(l_section)).collect(Collectors.toList());
             l_markList = new ArrayList<DBRecord>(l_classList);
             dbg("l_standard  l_section filter l_markList size"+l_markList.size());
           }
           
           if(l_exam!=null&&!l_exam.isEmpty()){
            
             List<DBRecord>  l_classList=  l_markList.stream().filter(rec->rec.getRecord().get(2).trim().equals(l_exam)).collect(Collectors.toList());
             l_markList = new ArrayList<DBRecord>(l_classList);
             dbg("l_exam filter l_markList size"+l_markList.size());
           }
           
           if(l_subjectID!=null&&!l_subjectID.isEmpty()){
            
             List<DBRecord>  l_classList=  l_markList.stream().filter(rec->rec.getRecord().get(3).trim().equals(l_subjectID)).collect(Collectors.toList());
             l_markList = new ArrayList<DBRecord>(l_classList);
             dbg("l_exam filter l_markList size"+l_markList.size());
           }
           
           
//           if(l_recordStatus!=null&&!l_recordStatus.isEmpty()){
//               
//               List<DBRecord>  l_classList=  l_markList.stream().filter(rec->rec.getRecord().get(8).trim().equals(l_recordStatus)).collect(Collectors.toList());
//               l_markList = new ArrayList<DBRecord>(l_classList);
//               dbg("recordStatus filter l_markList size"+l_markList.size());
//           }
           
           if(l_authStatus!=null&&!l_authStatus.isEmpty()){
               
               List<DBRecord>  l_classList=  l_markList.stream().filter(rec->rec.getRecord().get(9).trim().equals(l_authStatus)).collect(Collectors.toList());
               l_markList = new ArrayList<DBRecord>(l_classList);
               dbg("authStatus filter l_markList size"+l_markList.size());
               
           }
           
           classMark.result=new ClassMarkResult[l_markList.size()];
           for(int i=0;i<l_markList.size();i++){
               
               ArrayList<String> l_classMarkList=l_markList.get(i).getRecord();
               classMark.result[i]=new ClassMarkResult();
               classMark.result[i].setStandard(l_classMarkList.get(0).trim());
               classMark.result[i].setSection(l_classMarkList.get(1).trim());
               classMark.result[i].setExam(l_classMarkList.get(2).trim());
               classMark.result[i].setSubjectID(l_classMarkList.get(3).trim());
               String subjectName=bs.getSubjectName(l_classMarkList.get(3).trim(), instituteID, session, dbSession, inject);
               classMark.result[i].setSubjectName(subjectName);
               
               classMark.result[i].setMakerID(l_classMarkList.get(4).trim());
               classMark.result[i].setCheckerID(l_classMarkList.get(5).trim());
               classMark.result[i].setMakerDateStamp(l_classMarkList.get(6).trim());
               classMark.result[i].setCheckerDateStamp(l_classMarkList.get(7).trim());
               classMark.result[i].setRecordStatus(l_classMarkList.get(8).trim());
               classMark.result[i].setAuthStatus(l_classMarkList.get(9).trim());
               classMark.result[i].setVersionNumber(l_classMarkList.get(10).trim());
               classMark.result[i].setMakerRemarks(l_classMarkList.get(11).trim());
               classMark.result[i].setCheckerRemarks(l_classMarkList.get(12).trim());
          }    
           
           
           if(classMark.result==null||classMark.result.length==0){
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
        dbg("inside class mark buildJsonResFromBO");    
        RequestBody<ClassMarkBO> reqBody = request.getReqBody();
        ClassMarkBO classMark =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<classMark.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("class", classMark.result[i].getStandard()+"/"+classMark.result[i].getSection())
                                                      .add("exam", classMark.result[i].getExam())
                                                      .add("subjectID", classMark.result[i].getSubjectID())
                                                      .add("subjectName", classMark.result[i].getSubjectName())
                                                      .add("makerID", classMark.result[i].getMakerID())
                                                      .add("checkerID", classMark.result[i].getCheckerID())
                                                      .add("makerDateStamp", classMark.result[i].getMakerDateStamp())
                                                      .add("checkerDateStamp", classMark.result[i].getCheckerDateStamp())
                                                      .add("recordStatus", classMark.result[i].getRecordStatus())
                                                      .add("authStatus", classMark.result[i].getAuthStatus())
                                                      .add("versionNumber", classMark.result[i].getVersionNumber())
                                                      .add("makerRemarks", classMark.result[i].getMakerRemarks())
                                                      .add("checkerRemarks", classMark.result[i].getCheckerRemarks()));
        }

           filter=Json.createObjectBuilder()   .add("class", classMark.filter.getStandard()+"/"+classMark.filter.getSection())
//                                               .add("recordStatus", classMark.filter.getRecordStatus())
                                               .add("authStatus", classMark.filter.getAuthStatus())
                                               .add("exam", classMark.filter.getExam())
                                               .add("subjectID", classMark.filter.getSubjectID())
                                               .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of class mark buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside class mark--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of class mark--->businessValidation"); 
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
        dbg("inside class mark master mandatory validation");
        RequestBody<ClassMarkBO> reqBody = request.getReqBody();
        ClassMarkBO classMark =reqBody.get();
        int nullCount=0;
        
          if(classMark.getFilter().getStandard()==null||classMark.getFilter().getStandard().isEmpty()){
             nullCount++;
         }
         if(classMark.getFilter().getSection()==null||classMark.getFilter().getSection().isEmpty()){
             nullCount++;
         }
          if(classMark.getFilter().getAuthStatus()==null||classMark.getFilter().getAuthStatus().isEmpty()){
             nullCount++;
         }
          if(classMark.getFilter().getExam()==null||classMark.getFilter().getExam().isEmpty()){
             nullCount++;
         }
          
           if(classMark.getFilter().getSubjectID()==null||classMark.getFilter().getSubjectID().isEmpty()){
             nullCount++;
         }
         
          if(nullCount==5){
              status=false;
              errhandler.log_app_error("BS_VAL_002","One Filter value");
          }
          
        dbg("end of class mark master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside class mark detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<ClassMarkBO> reqBody = request.getReqBody();
             ClassMarkBO classMark =reqBody.get();
             String l_standard=classMark.getFilter().getStandard();
             String l_section=classMark.getFilter().getSection();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_authStatus=classMark.getFilter().getAuthStatus();
             String l_exam=classMark.getFilter().getExam();
             String l_subjectID=classMark.getFilter().getSubjectID();
             
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
             
             if(l_subjectID!=null&&!l_subjectID.isEmpty()){
             
                 if(!bsv.subjectIDValidation(l_subjectID,l_instituteID, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","subjectID");
                 }
             
             }
             
            dbg("end of class mark detailDataValidation");
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
