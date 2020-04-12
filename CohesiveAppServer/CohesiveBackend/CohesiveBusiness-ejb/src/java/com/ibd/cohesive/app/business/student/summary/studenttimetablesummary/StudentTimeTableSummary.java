/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studenttimetablesummary;

import com.ibd.businessViews.IStudentTimeTableSummary;
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
//@Local(IStudentTimeTableSummary.class)
@Remote(IStudentTimeTableSummary.class)
@Stateless
public class StudentTimeTableSummary implements IStudentTimeTableSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentTimeTableSummary(){
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
       dbg("inside StudentTimeTableSummary--->processing");
       dbg("StudentTimeTableSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IStudentTimeTableSummary>studentTimeTableEJB=new BusinessEJB();
       studentTimeTableEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentTimeTableEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,studentTimeTableEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentTimeTableEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student leave");
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
                /*if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
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
      StudentTimeTableBO studentTimeTable=new StudentTimeTableBO();
      RequestBody<StudentTimeTableBO> reqBody = new RequestBody<StudentTimeTableBO>(); 
           
      try{
      dbg("inside student leave buildBOfromReq");    
      BSValidation bsv=inject.getBsv(session);
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      studentTimeTable.filter=new StudentTimeTableFilter();
      studentTimeTable.filter.setStudentID(l_filterObject.getString("studentID"));
      studentTimeTable.filter.setStudentName(l_filterObject.getString("studentName"));
//      studentTimeTable.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      studentTimeTable.filter.setAuthStatus(l_filterObject.getString("authStat"));
      if(l_filterObject.getString("class").equals("Select option")){
          studentTimeTable.filter.setStandard("");
          studentTimeTable.filter.setSection("");
      }else{

          String l_class=l_filterObject.getString("class");
          bsv.classValidation(l_class,session);
          studentTimeTable.filter.setStandard(l_class.split("/")[0]);
          studentTimeTable.filter.setSection(l_class.split("/")[1]);
      
      }
      
      if(l_filterObject.getString("authStat").equals("Select option")){
          
          studentTimeTable.filter.setAuthStatus("");
      }else{
      
          studentTimeTable.filter.setAuthStatus(l_filterObject.getString("authStat"));
      }
      
        reqBody.set(studentTimeTable);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student leave--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentTimeTableBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<DBRecord>l_leaveList=new ArrayList();
        StudentTimeTableBO studentTimeTable=reqBody.get();
        String l_studentID=studentTimeTable.getFilter().getStudentID();
        String l_standard=studentTimeTable.getFilter().getStandard();
        String l_section=studentTimeTable.getFilter().getSection();
        String studentNameFilter=studentTimeTable.getFilter().getStudentName();
        
        try{
        
        
            ArrayList<String>l_fileNames=bs.getStudentFileNames(l_studentID,request,session,dbSession,inject,l_standard,l_section
            );
            for(int i=0;i<l_fileNames.size();i++){
                dbg("inside file name iteration");
                String l_fileName=l_fileNames.get(i);
                dbg("l_fileName"+l_fileName);
                String studentName=bs.getStudentName(l_fileName, l_instituteID, session, dbSession, inject);
                
                if(studentName.equals(studentNameFilter)){
                
                Map<String,DBRecord>leaveMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileName+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileName,"STUDENT", "SVW_STUDENT_TIMETABLE_MASTER", session, dbSession);

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
        
          dbg("end of  completed student leave--->view"); 
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
           RequestBody<StudentTimeTableBO> reqBody = request.getReqBody();
           StudentTimeTableBO studentTimeTable =reqBody.get();
           String l_authStatus=studentTimeTable.getFilter().getAuthStatus();
           String l_studentID=studentTimeTable.getFilter().getStudentID();
           
           dbg("l_authStatus"+l_authStatus);
           dbg("l_studentID"+l_studentID);
           
           if(l_studentID!=null&&!l_studentID.isEmpty()){
            
             List<DBRecord>  l_studentList=  l_leaveList.stream().filter(rec->rec.getRecord().get(0).trim().equals(l_studentID)).collect(Collectors.toList());
             l_leaveList = new ArrayList<DBRecord>(l_studentList);
             dbg("studentID filter l_leaveList size"+l_leaveList.size());
           }
           
           
           if(l_authStatus!=null&&!l_authStatus.isEmpty()){
               
               List<DBRecord>  l_studentList=  l_leaveList.stream().filter(rec->rec.getRecord().get(6).trim().equals(l_authStatus)).collect(Collectors.toList());
               l_leaveList = new ArrayList<DBRecord>(l_studentList);
               dbg("authStatus filter l_leaveList size"+l_leaveList.size());
               
           }
           
           studentTimeTable.result=new StudentTimeTableResult[l_leaveList.size()];
           for(int i=0;i<l_leaveList.size();i++){
               
               ArrayList<String> l_studentTimeTableList=l_leaveList.get(i).getRecord();
               studentTimeTable.result[i]=new StudentTimeTableResult();
               studentTimeTable.result[i].setStudentID(l_studentTimeTableList.get(0).trim());
               studentTimeTable.result[i].setMakerID(l_studentTimeTableList.get(1).trim());
               studentTimeTable.result[i].setCheckerID(l_studentTimeTableList.get(2).trim());
               studentTimeTable.result[i].setMakerDateStamp(l_studentTimeTableList.get(3).trim());
               studentTimeTable.result[i].setCheckerDateStamp(l_studentTimeTableList.get(4).trim());
               studentTimeTable.result[i].setRecordStatus(l_studentTimeTableList.get(5).trim());
               studentTimeTable.result[i].setAuthStatus(l_studentTimeTableList.get(6).trim());
               studentTimeTable.result[i].setVersionNumber(l_studentTimeTableList.get(7).trim());
               studentTimeTable.result[i].setMakerRemarks(l_studentTimeTableList.get(8).trim());
               studentTimeTable.result[i].setCheckerRemarks(l_studentTimeTableList.get(9).trim());
          }    
           
           if(studentTimeTable.result==null||studentTimeTable.result.length==0){
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
        dbg("inside student leave buildJsonResFromBO");    
        RequestBody<StudentTimeTableBO> reqBody = request.getReqBody();
        StudentTimeTableBO studentTimeTable =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<studentTimeTable.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("studentID", studentTimeTable.result[i].getStudentID())
                                                      .add("makerID", studentTimeTable.result[i].getMakerID())
                                                      .add("checkerID", studentTimeTable.result[i].getCheckerID())
                                                      .add("makerDateStamp", studentTimeTable.result[i].getMakerDateStamp())
                                                      .add("checkerDateStamp", studentTimeTable.result[i].getCheckerDateStamp())
                                                      .add("recordStatus", studentTimeTable.result[i].getRecordStatus())
                                                      .add("authStatus", studentTimeTable.result[i].getAuthStatus())
                                                      .add("versionNumber", studentTimeTable.result[i].getVersionNumber())
                                                      .add("makerRemarks", studentTimeTable.result[i].getMakerRemarks())
                                                      .add("checkerRemarks", studentTimeTable.result[i].getCheckerRemarks()));
        }

           filter=Json.createObjectBuilder()  .add("studentID",studentTimeTable.filter.getStudentID())
                                              .add("studentName",studentTimeTable.filter.getStudentName())
                                              .add("authStatus", studentTimeTable.filter.getAuthStatus())
                                              .add("class",studentTimeTable.filter.getStandard()+"/"+studentTimeTable.filter.getSection())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of student leave buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student leave--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of student leave--->businessValidation"); 
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
        dbg("inside student leave master mandatory validation");
        RequestBody<StudentTimeTableBO> reqBody = request.getReqBody();
        StudentTimeTableBO studentTimeTable =reqBody.get();
        int nullCount=0;
        
         if(studentTimeTable.getFilter().getStudentID()==null||studentTimeTable.getFilter().getStudentID().isEmpty()){
             nullCount++;
         }
          if(studentTimeTable.getFilter().getAuthStatus()==null||studentTimeTable.getFilter().getAuthStatus().isEmpty()){
             nullCount++;
         }
          
         if(studentTimeTable.getFilter().getStandard()==null||studentTimeTable.getFilter().getStandard().isEmpty()){
             nullCount++;
         }

          if(studentTimeTable.getFilter().getSection()==null||studentTimeTable.getFilter().getSection().isEmpty()){
             nullCount++;
         }
          
          if(studentTimeTable.getFilter().getStudentName()==null||studentTimeTable.getFilter().getStudentName().isEmpty()){
             nullCount++;
         }
          if(nullCount==5){
              status=false;
              errhandler.log_app_error("BS_VAL_002","One Filter value");
          }
          
        dbg("end of student leave master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student leave detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<StudentTimeTableBO> reqBody = request.getReqBody();
             StudentTimeTableBO studentTimeTable =reqBody.get();
             String l_studentID=studentTimeTable.getFilter().getStudentID();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_authStatus=studentTimeTable.getFilter().getAuthStatus();
             
             if(l_studentID!=null&&!l_studentID.isEmpty()){
                 
                if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","studentID");
                }
                 
             }

             
             if(l_authStatus!=null&&!l_authStatus.isEmpty()){
                 
                if(!bsv.authStatusValidation(l_authStatus, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","authStatus");
                }
                 
             }
   

             
            dbg("end of student leave detailDataValidation");
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
