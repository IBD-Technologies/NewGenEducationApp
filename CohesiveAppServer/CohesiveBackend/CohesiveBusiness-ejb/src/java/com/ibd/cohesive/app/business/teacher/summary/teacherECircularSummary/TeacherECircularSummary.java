/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.summary.teacherECircularSummary;

import com.ibd.businessViews.ITeacherECircularSummary;
import com.ibd.businessViews.ITeacherECircularSummary;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.student.summary.studentprogresscard.ProgressSignStatus;
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
@Remote(ITeacherECircularSummary.class)
@Stateless
public class TeacherECircularSummary implements ITeacherECircularSummary{
   
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public TeacherECircularSummary(){
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
       dbg("inside TeacherECircularSummary--->processing");
       dbg("TeacherECircularSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<ITeacherECircularSummary>teacherECircularEJB=new BusinessEJB();
       teacherECircularEJB.set(this);
      
       exAudit=bs.getExistingAudit(teacherECircularEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,teacherECircularEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,teacherECircularEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in teacher progreass");
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
      TeacherECircularBO teacherECircular=new TeacherECircularBO();
      RequestBody<TeacherECircularBO> reqBody = new RequestBody<TeacherECircularBO>(); 
           
      try{
      dbg("inside teacher progreass buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      BSValidation bsv=inject.getBsv(session);
      teacherECircular.filter=new TeacherECircularFilter();
      teacherECircular.filter.setTeacherID(l_filterObject.getString("teacherID"));
      teacherECircular.filter.setTeacherName(l_filterObject.getString("teacherName"));
      teacherECircular.filter.setCircularID(l_filterObject.getString("circularID"));
      teacherECircular.filter.setCircularDescription(l_filterObject.getString("circularDescription"));
//      teacherECircular.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      teacherECircular.filter.setAuthStatus(l_filterObject.getString("authStat"));
//      teacherECircular.filter.setExam(l_filterObject.getString("exam"));

   
      


      if(l_filterObject.getString("signStatus").equals("Select option")){
          
          teacherECircular.filter.setSignStatus("");
      }else{
      
          teacherECircular.filter.setSignStatus(l_filterObject.getString("signStatus"));
      }

      
     

      
        reqBody.set(teacherECircular);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside teacher progreass--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<TeacherECircularBO> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        IPDataService pds=inject.getPdataservice();
        TeacherECircularBO teacherECircular=reqBody.get();
        String l_teacherID=teacherECircular.getFilter().getTeacherID();
        
//        String l_circularID=teacherECircular.getFilter().getCircularID();
        ArrayList<DBRecord>circularRecords=new ArrayList();

         ArrayList<String>l_fileNames=bs.getTeacherFileNames(l_teacherID,request,session,dbSession,inject);
        for(int i=0;i<l_fileNames.size();i++){
            dbg("inside file name iteration");
            String teacherID=l_fileNames.get(i);
            dbg("teacherID"+teacherID);
//            String[] l_pkey={l_circularID};
        
        try{
        

//            DBRecord signRec=    readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_circularID,"INSTITUTE", "IVW_TEACHER_E_CIRCULAR_SIGNATURE", p_pkey, session, dbSession);
            
//                                   DBRecord signRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular","TEACHER", "TVW_TEACHER_E_CIRCULAR", l_pkey, session, dbSession);
   
                   Map<String,DBRecord>studentCircularMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular","TEACHER", "TVW_TEACHER_E_CIRCULAR", session, dbSession);
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


        

       
           
        
        

        
          dbg("end of  completed teacher progreass--->view");  
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
           RequestBody<TeacherECircularBO> reqBody = request.getReqBody();
           TeacherECircularBO teacherECircular =reqBody.get();
           BusinessService bs=inject.getBusinessService(session);
//           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           String instituteID=request.getReqHeader().getInstituteID();
           String l_signStatus=teacherECircular.getFilter().getSignStatus();
           String l_circularID=teacherECircular.getFilter().getCircularID();
//           IBDProperties i_db_properties=session.getCohesiveproperties();
//           ArrayList<ProgressSignStatus>teachersList=new ArrayList();
           

           if(l_circularID!=null&&!l_circularID.isEmpty()){
               
               List<DBRecord>  l_teacherList=  circularRecords.stream().filter(rec->rec.getRecord().get(1).trim().equals(l_circularID)).collect(Collectors.toList());
               circularRecords = new ArrayList<DBRecord>(l_teacherList);
               dbg("l_leaveStatus filter l_leaveList size"+circularRecords.size());
               
           }


           if(l_signStatus!=null&&!l_signStatus.isEmpty()){
               
               List<DBRecord>  l_teacherList=  circularRecords.stream().filter(rec->rec.getRecord().get(2).trim().equals(l_signStatus)).collect(Collectors.toList());
               circularRecords = new ArrayList<DBRecord>(l_teacherList);
               dbg("l_leaveStatus filter l_leaveList size"+circularRecords.size());
               
           }
           
//           for(int i=0;i<circularRecords.size();i++){
//               
//              String teacherID= circularRecords.get(i).getRecord().get(0).trim();
//               
//               
//               ProgressSignStatus progress=new ProgressSignStatus();
//                    progress.setTeacherID(teacherID);
//                    String[] l_pkey={teacherID};
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
//                        teachersList.add(progress);
//                    }else{
//                        progress.setSignStatus("N");
//                        teachersList.add(progress);
//                    }
//               
//           }
           
//           
//           if(l_signStatus!=null&&!l_signStatus.isEmpty()){
//               
//               List<ProgressSignStatus>  l_teacherList=  teachersList.stream().filter(rec->rec.getSignStatus().equals(l_signStatus)).collect(Collectors.toList());
//               teachersList = new ArrayList<ProgressSignStatus>(l_teacherList);
//               dbg("l_leaveStatus filter l_leaveList size"+circularRecords.size());
//               
//           }
           
           
           
           
           
           
           
           
           dbg("circularRecords size"+circularRecords.size());
           teacherECircular.result=new TeacherECircularResult[circularRecords.size()];
           for(int i=0;i<circularRecords.size();i++){
               
               teacherECircular.result[i]=new TeacherECircularResult();
               
               teacherECircular.result[i].setTeacherID(circularRecords.get(i).getRecord().get(0).trim());
               teacherECircular.result[i].setTeacherName(bs.getTeacherName(circularRecords.get(i).getRecord().get(0).trim(), instituteID, session, dbSession, inject));
               teacherECircular.result[i].setCircularID(circularRecords.get(i).getRecord().get(1).trim());
               
               
               if(circularRecords.get(i).getRecord().get(2).trim().equals("Y")){
                   
                   teacherECircular.result[i].setSignStatus("Yes");
                   
               }else{
                   
                   teacherECircular.result[i].setSignStatus("No");
               }
               
               
               
               
               
          }    
           
           if(teacherECircular.result==null||teacherECircular.result.length==0){
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
        dbg("inside teacher progreass buildJsonResFromBO");    
        RequestBody<TeacherECircularBO> reqBody = request.getReqBody();
        TeacherECircularBO teacherECircular =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
        String l_circularID=teacherECircular.getFilter().getCircularID();
             
        for(int i=0;i<teacherECircular.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("teacherID", teacherECircular.result[i].getTeacherID())
                                                      .add("teacherName", teacherECircular.result[i].getTeacherName())
                                                      .add("signStatus", teacherECircular.result[i].getSignStatus())
                                                      .add("circularID", teacherECircular.result[i].getCircularID())
                                                      );
        }

           filter=Json.createObjectBuilder()  .add("teacherID",teacherECircular.filter.getTeacherID())
                                              .add("teacherName",teacherECircular.filter.getTeacherName())
                                              .add("signStatus", teacherECircular.filter.getSignStatus())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of teacher progreass buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside teacher progreass--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of teacher progreass--->businessValidation"); 
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
        dbg("inside teacher progreass master mandatory validation");
        
        RequestBody<TeacherECircularBO> reqBody = request.getReqBody();
        TeacherECircularBO teacherECircular =reqBody.get();
        String teacherID=teacherECircular.getFilter().getTeacherID();
        BusinessService bs=inject.getBusinessService(session);
        String userID=request.getReqHeader().getUserID();
        String userType=bs.getUserType(userID, session, dbSession, inject);
        String teacherName=teacherECircular.getFilter().getTeacherName();
        String instituteID=request.getReqHeader().getInstituteID();
        String circularID=teacherECircular.getFilter().getCircularID();
        
//        if(circularID==null||circularID.isEmpty()){
//                
//                    session.getErrorhandler().log_app_error("BS_VAL_002", "Circular ID");
//                    throw new BSValidationException("BSValidationException");
//                
//                }
        
        

//        teacherECircular.getFilter().setTeacherID(teacherID);
//        
//       if(userType.equals("P")){
//            
//             if(teacherID==null||teacherID.isEmpty()){
//                
//                    session.getErrorhandler().log_app_error("BS_VAL_002", "Teacher Name");
//                    throw new BSValidationException("BSValidationException");
//                
//                }
//            
//        }else{ 
//        
//            if(standard==null||standard.isEmpty()||section==null||section.isEmpty()){
//                
//                if(teacherID==null||teacherID.isEmpty()){
//                
//                    session.getErrorhandler().log_app_error("BS_VAL_027", teacherID);
//                    throw new BSValidationException("BSValidationException");
//                
//                }
//                
//            }
//            
//       }
     
            
          
        dbg("end of teacher progreass master mandatory validation");
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
             dbg("inside teacher progreass detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<TeacherECircularBO> reqBody = request.getReqBody();
             TeacherECircularBO teacherECircular =reqBody.get();
             String l_teacherID=teacherECircular.getFilter().getTeacherID();
             String l_instituteID=request.getReqHeader().getInstituteID();
             
             if(l_teacherID!=null&&!l_teacherID.isEmpty()){
                 
                if(!bsv.teacherIDValidation(l_teacherID, l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","teacherID");
                }
                 
             }
     
             
             
             
             
            
             
            dbg("end of teacher progreass detailDataValidation");
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
