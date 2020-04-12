/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studenteCircularSummary;

import com.ibd.businessViews.IStudentECircularSummary;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.exception.ExceptionHandler;
import com.ibd.cohesive.app.business.util.message.request.Parsing;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.core.pdata.IPDataService;
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
@Remote(IStudentECircularSummary.class)
@Stateless
public class StudentECircularSummary implements IStudentECircularSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public StudentECircularSummary(){
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
       dbg("inside StudentECircularSummary--->processing");
       dbg("StudentECircularSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IStudentECircularSummary>studentECircularEJB=new BusinessEJB();
       studentECircularEJB.set(this);
      
       exAudit=bs.getExistingAudit(studentECircularEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,studentECircularEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,studentECircularEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in student progreass");
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
      StudentECircularBO studentECircular=new StudentECircularBO();
      RequestBody<StudentECircularBO> reqBody = new RequestBody<StudentECircularBO>(); 
           
      try{
      dbg("inside student progreass buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      BSValidation bsv=inject.getBsv(session);
      studentECircular.filter=new StudentECircularFilter();
      studentECircular.filter.setStudentID(l_filterObject.getString("studentID"));
      studentECircular.filter.setStudentName(l_filterObject.getString("studentName"));
      studentECircular.filter.setCircularID(l_filterObject.getString("circularID"));
      studentECircular.filter.setCircularDescription(l_filterObject.getString("circularDescription"));
//      studentECircular.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      studentECircular.filter.setAuthStatus(l_filterObject.getString("authStat"));
//      studentECircular.filter.setExam(l_filterObject.getString("exam"));

   

      if(l_filterObject.getString("class").equals("Select option")){
          studentECircular.filter.setStandard("");
          studentECircular.filter.setSection("");
      }else{

          String l_class=l_filterObject.getString("class");
          bsv.classValidation(l_class,session);
          studentECircular.filter.setStandard(l_class.split("/")[0]);
          studentECircular.filter.setSection(l_class.split("/")[1]);
      
      }
      


      if(l_filterObject.getString("signStatus").equals("Select option")){
          
          studentECircular.filter.setSignStatus("");
      }else{
      
          studentECircular.filter.setSignStatus(l_filterObject.getString("signStatus"));
      }

      
     

      
        reqBody.set(studentECircular);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student progreass--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<StudentECircularBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        IPDataService pds=inject.getPdataservice();
        StudentECircularBO studentECircular=reqBody.get();
        String l_studentID=studentECircular.getFilter().getStudentID();
        String l_standard=studentECircular.getFilter().getStandard();
        String l_section=studentECircular.getFilter().getSection();
        
//        String l_circularID=studentECircular.getFilter().getCircularID();
        ArrayList<DBRecord>circularRecords=new ArrayList();

         ArrayList<String>l_fileNames=bs.getStudentFileNames(l_studentID,request,session,dbSession,inject,l_standard,l_section);
        for(int i=0;i<l_fileNames.size();i++){
            dbg("inside file name iteration");
            String studentID=l_fileNames.get(i);
            dbg("studentID"+studentID);
//            String[] l_pkey={l_circularID};
        
        try{
        

//            DBRecord signRec=    readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_circularID,"INSTITUTE", "IVW_STUDENT_E_CIRCULAR_SIGNATURE", p_pkey, session, dbSession);
            
              Map<String,DBRecord>studentCircularMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular","STUDENT", "SVW_STUDENT_E_CIRCULAR", session, dbSession);
//                                   DBRecord signRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular","STUDENT", "SVW_STUDENT_E_CIRCULAR", l_pkey, session, dbSession);
   
                Iterator<DBRecord>valueIterator=   studentCircularMap.values().iterator();
                         
                while(valueIterator.hasNext()){
                
                   circularRecords.add(valueIterator.next());
                }
                   
                   
                   

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
        
        
         if(circularRecords.isEmpty()){
            
            session.getErrorhandler().log_app_error("BS_VAL_013", null);
            throw new BSValidationException("BSValidationException");
        }
        buildBOfromDB(circularRecords);   


        

       
           
        
        

        
          dbg("end of  completed student progreass--->view");  
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
    

    
    private void buildBOfromDB(ArrayList<DBRecord>circularRecords)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<StudentECircularBO> reqBody = request.getReqBody();
           StudentECircularBO studentECircular =reqBody.get();
           BusinessService bs=inject.getBusinessService(session);
//           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           String instituteID=request.getReqHeader().getInstituteID();
           String l_signStatus=studentECircular.getFilter().getSignStatus();
           String l_circularID=studentECircular.getFilter().getCircularID();
//           IBDProperties i_db_properties=session.getCohesiveproperties();
//           ArrayList<ProgressSignStatus>studentsList=new ArrayList();
           

           if(l_circularID!=null&&!l_circularID.isEmpty()){
               
               List<DBRecord>  l_studentList=  circularRecords.stream().filter(rec->rec.getRecord().get(1).trim().equals(l_circularID)).collect(Collectors.toList());
               circularRecords = new ArrayList<DBRecord>(l_studentList);
               dbg("l_leaveStatus filter l_leaveList size"+circularRecords.size());
               
           }


           if(l_signStatus!=null&&!l_signStatus.isEmpty()){
               
               List<DBRecord>  l_studentList=  circularRecords.stream().filter(rec->rec.getRecord().get(2).trim().equals(l_signStatus)).collect(Collectors.toList());
               circularRecords = new ArrayList<DBRecord>(l_studentList);
               dbg("l_leaveStatus filter l_leaveList size"+circularRecords.size());
               
           }
           
//           for(int i=0;i<circularRecords.size();i++){
//               
//              String studentID= circularRecords.get(i).getRecord().get(0).trim();
//               
//               
//               ProgressSignStatus progress=new ProgressSignStatus();
//                    progress.setStudentID(studentID);
//                    String[] l_pkey={studentID};
//                    boolean recordExistence=false;
//                    
//                    try{
//                    
//                    
//                      readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_circularID,"INSTITUTE", "IVW_STUDENT_E_CIRCULAR_SIGNATURE", l_pkey, session, dbSession);
//                    
//                    recordExistence=true;
//                    
//                    }catch(DBValidationException ex){
//            
//                        if(ex.toString().contains("DB_VAL_000")||ex.toString().contains("DB_VAL_011")){
//                            recordExistence=false;
//                            session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
//                            session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
//
//                        }else{
//                            throw ex;
//                        }
//            
//                   }
//                    
//                    dbg("signature record existenc  "+recordExistence);
//
//                    if(recordExistence){
//                        
//           
//                        progress.setSignStatus("Y");
//                        studentsList.add(progress);
//                    }else{
//                        progress.setSignStatus("N");
//                        studentsList.add(progress);
//                    }
//               
//           }
           
//           
//           if(l_signStatus!=null&&!l_signStatus.isEmpty()){
//               
//               List<ProgressSignStatus>  l_studentList=  studentsList.stream().filter(rec->rec.getSignStatus().equals(l_signStatus)).collect(Collectors.toList());
//               studentsList = new ArrayList<ProgressSignStatus>(l_studentList);
//               dbg("l_leaveStatus filter l_leaveList size"+circularRecords.size());
//               
//           }
           
           
           
           
           
           
           
           
           dbg("circularRecords size"+circularRecords.size());
           studentECircular.result=new StudentECircularResult[circularRecords.size()];
           for(int i=0;i<circularRecords.size();i++){
               
               studentECircular.result[i]=new StudentECircularResult();
               
               studentECircular.result[i].setStudentID(circularRecords.get(i).getRecord().get(0).trim());
               studentECircular.result[i].setStudentName(bs.getStudentName(circularRecords.get(i).getRecord().get(0).trim(), instituteID, session, dbSession, inject));
               studentECircular.result[i].setCircularID(circularRecords.get(i).getRecord().get(1).trim());
               
               
               if(circularRecords.get(i).getRecord().get(2).trim().equals("Y")){
                   
                   studentECircular.result[i].setSignStatus("Yes");
                   
               }else{
                   
                   studentECircular.result[i].setSignStatus("No");
               }
               
               
               
               
               
          }    
           
           if(studentECircular.result==null||studentECircular.result.length==0){
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
        dbg("inside student progreass buildJsonResFromBO");    
        RequestBody<StudentECircularBO> reqBody = request.getReqBody();
        StudentECircularBO studentECircular =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
//        String l_circularID=studentECircular.getFilter().getCircularID();
             
        for(int i=0;i<studentECircular.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("studentID", studentECircular.result[i].getStudentID())
                                                      .add("studentName", studentECircular.result[i].getStudentName())
                                                      .add("signStatus", studentECircular.result[i].getSignStatus())
                                                      .add("circularID", studentECircular.result[i].getCircularID())
                                                      );
        }

           filter=Json.createObjectBuilder()  .add("studentID",studentECircular.filter.getStudentID())
                                              .add("studentName",studentECircular.filter.getStudentName())
                                              .add("signStatus", studentECircular.filter.getSignStatus())
                                              .add("class",studentECircular.filter.getStandard()+"/"+studentECircular.filter.getSection())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of student progreass buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student progreass--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of student progreass--->businessValidation"); 
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
        dbg("inside student progreass master mandatory validation");
        
        RequestBody<StudentECircularBO> reqBody = request.getReqBody();
        StudentECircularBO studentECircular =reqBody.get();
        String standard=studentECircular.getFilter().getStandard();
        String section=studentECircular.getFilter().getSection();
        String studentID=studentECircular.getFilter().getStudentID();
        BusinessService bs=inject.getBusinessService(session);
        String userID=request.getReqHeader().getUserID();
        String userType=bs.getUserType(userID, session, dbSession, inject);
        String studentName=studentECircular.getFilter().getStudentName();
        String instituteID=request.getReqHeader().getInstituteID();
        String circularID=studentECircular.getFilter().getCircularID();
        
//        if(circularID==null||circularID.isEmpty()){
//                
//                    session.getErrorhandler().log_app_error("BS_VAL_002", "Circular ID");
//                    throw new BSValidationException("BSValidationException");
//                
//                }
        
        
        
        studentID=bs.studentValidation(studentID, studentName, instituteID, session, dbSession, inject);

        studentECircular.getFilter().setStudentID(studentID);
        
       if(userType.equals("P")){
            
             if(studentID==null||studentID.isEmpty()){
                
                    session.getErrorhandler().log_app_error("BS_VAL_002", "Student Name");
                    throw new BSValidationException("BSValidationException");
                
                }
            
        }else{ 
        
            if(standard==null||standard.isEmpty()||section==null||section.isEmpty()){
                
                if(studentID==null||studentID.isEmpty()){
                
                    session.getErrorhandler().log_app_error("BS_VAL_027", studentID);
                    throw new BSValidationException("BSValidationException");
                
                }
                
            }
            
       }
     
            
          
        dbg("end of student progreass master mandatory validation");
        }catch(BSValidationException ex){
           throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student progreass detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<StudentECircularBO> reqBody = request.getReqBody();
             StudentECircularBO studentECircular =reqBody.get();
             String l_studentID=studentECircular.getFilter().getStudentID();
             String l_instituteID=request.getReqHeader().getInstituteID();
             
             if(l_studentID!=null&&!l_studentID.isEmpty()){
                 
                if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","studentID");
                }
                 
             }
     
             
             
             
             
            
             
            dbg("end of student progreass detailDataValidation");
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
