/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.ClassSummary;

import com.ibd.businessViews.IClassSummary;
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
@Remote(IClassSummary.class)
@Stateless
public class ClassSummary implements IClassSummary{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public ClassSummary(){
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
       dbg("inside ClassSummary--->processing");
       dbg("ClassSummary--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IClassSummary>ClassEJB=new BusinessEJB();
       ClassEJB.set(this);
      
       exAudit=bs.getExistingAudit(ClassEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,ClassEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,ClassEJB);
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
      ClassBO Class=new ClassBO();
      RequestBody<ClassBO> reqBody = new RequestBody<ClassBO>(); 
           
      try{
      dbg("inside student Class buildBOfromReq");    
      BSValidation bsv=inject.getBsv(session);
      JsonObject l_body=p_request.getJsonObject("body");
      JsonObject l_filterObject=l_body.getJsonObject("filter");
      Class.filter=new ClassFilter();
//      Class.filter.setInstituteID(l_filterObject.getString("instituteID"));
//      Class.filter.setYear(l_filterObject.getString("year"));
//      Class.filter.setMonth(l_filterObject.getString("month"));
//      Class.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      Class.filter.setAuthStatus(l_filterObject.getString("authStat"));
      
//      Class.filter.setYear(l_filterObject.getString("year"));
//      Class.filter.setMonth(l_filterObject.getString("month"));
      if(l_filterObject.getString("Class").equals("Select option")){
          Class.filter.setStandard("");
          Class.filter.setSection("");
      }else{

          String l_class=l_filterObject.getString("Class");
          bsv.classValidation(l_class,session);
          Class.filter.setStandard(l_class.split("/")[0]);
          Class.filter.setSection(l_class.split("/")[1]);
      
      }
      if(l_filterObject.getString("authStat").equals("Select option")){
          
          Class.filter.setAuthStatus("");
      }else{
      
          Class.filter.setAuthStatus(l_filterObject.getString("authStat"));
      }
      
     
  
        reqBody.set(Class);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside student Class--->view");
        BusinessService bs=inject.getBusinessService(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IPDataService pds=inject.getPdataservice();
//        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        ArrayList<ArrayList<String>>l_ClassList=new ArrayList();
        String l_instituteID=request.getReqHeader().getInstituteID();
        
        try{
        
                ArrayList<String>l_fileNames=bs.getInstituteFileNames(l_instituteID,request,session,dbSession,inject);
                for(int i=0;i<l_fileNames.size();i++){
                    dbg("inside file name iteration");
                    String l_fileName=l_fileNames.get(i);
                    dbg("l_fileName"+l_fileName);
                    Map<String,ArrayList<String>>ClassMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", session, dbSession);

                    Iterator<ArrayList<String>>valueIterator=ClassMap.values().iterator();

                    while(valueIterator.hasNext()){
                       ArrayList<String> ClassRec=valueIterator.next();
                       l_ClassList.add(ClassRec);

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

        buildBOfromDB(l_ClassList);     
        
          dbg("end of  completed student Class--->view");
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
    

    
    private void buildBOfromDB(ArrayList<ArrayList<String>>l_ClassList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           BusinessService bs=inject.getBusinessService(session);
           RequestBody<ClassBO> reqBody = request.getReqBody();
           ClassBO Class =reqBody.get();
           String l_authStatus=Class.getFilter().getAuthStatus();
           String l_standard=Class.getFilter().getStandard();
           String l_section=Class.getFilter().getSection();
           String instituteID=request.getReqHeader().getInstituteID();
           
           dbg("l_authStatus"+l_authStatus);
  
           if(l_standard!=null&&!l_standard.isEmpty()&&l_section!=null&&!l_section.isEmpty()){
            
             List<ArrayList<String>>  l_classList=  l_ClassList.stream().filter(rec->rec.get(1).trim().equals(l_standard)&&rec.get(2).trim().equals(l_section)).collect(Collectors.toList());
             l_ClassList = new ArrayList<ArrayList<String>>(l_classList);
             dbg("l_standard  l_section filter l_markList size"+l_ClassList.size());
           }

           
 
           if(l_authStatus!=null&&!l_authStatus.isEmpty()){
               
               List<ArrayList<String>>  l_studentList=  l_ClassList.stream().filter(rec->rec.get(10).trim().equals(l_authStatus)).collect(Collectors.toList());
                l_ClassList= new ArrayList<ArrayList<String>>(l_studentList);
               dbg("authStatus filter l_ClassList size"+l_ClassList.size());
               
           }
           
           Class.result=new ClassResult[l_ClassList.size()];
           for(int i=0;i<l_ClassList.size();i++){
               
               ArrayList<String> l_ClassRecords=l_ClassList.get(i);
               Class.result[i]=new ClassResult();
               Class.result[i].setStandard(l_ClassRecords.get(1).trim());
               Class.result[i].setSection(l_ClassRecords.get(2).trim());
               String teacherID=l_ClassRecords.get(3).trim();
               Class.result[i].setTeacherID(teacherID);
               String teacherName=bs.getTeacherName(teacherID, instituteID, session, dbSession, inject);
               Class.result[i].setTeacherName(teacherName);
               
          }    
           
           
           if(Class.result==null||Class.result.length==0){
               session.getErrorhandler().log_app_error("BS_VAL_013", null);
               throw new BSValidationException("BSValidationException");
           }
           
           
          dbg("end of  buildBOfromDB"); 
          }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
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
        dbg("inside student Class buildJsonResFromBO");    
        RequestBody<ClassBO> reqBody = request.getReqBody();
        ClassBO Class =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<Class.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("Class", Class.result[i].getStandard()+"/"+Class.result[i].getSection())
                                                      .add("teacherID", Class.result[i].getTeacherID())
                                                      .add("teacherName",Class.result[i].getTeacherName()));
        }

           filter=Json.createObjectBuilder()  .add("Class", Class.filter.getStandard()+"/"+Class.filter.getSection())
                                              .add("authStatus", Class.filter.getAuthStatus())
                                              .build();
               
               
            body=Json.createObjectBuilder().add("filter", filter)
                                           .add("SummaryResult", resultArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of student Class buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside student Class--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!filterDataValidation(errhandler)){
             status=false;
            }
       }
       
       
       dbg("end of student Class--->businessValidation"); 
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
        dbg("inside student Class master mandatory validation");
        RequestBody<ClassBO> reqBody = request.getReqBody();
        ClassBO Class =reqBody.get();
        int nullCount=0;
         
         
         if(Class.getFilter().getStandard()==null||Class.getFilter().getStandard().isEmpty()){
             nullCount++;
         }
         if(Class.getFilter().getSection()==null||Class.getFilter().getSection().isEmpty()){
             nullCount++;
         }

          if(Class.getFilter().getAuthStatus()==null||Class.getFilter().getAuthStatus().isEmpty()){
             nullCount++;
         }
          
         
          if(nullCount==3){
              status=false;
              errhandler.log_app_error("BS_VAL_002","One Filter value");
          }
          
        dbg("end of student Class master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean filterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside student Class detailDataValidation");
             BSValidation bsv=inject.getBsv(session);
             RequestBody<ClassBO> reqBody = request.getReqBody();
             ClassBO Class =reqBody.get();
             String l_authStatus=Class.getFilter().getAuthStatus();
//             String l_standard=Class.getFilter().getStandard();
//             String l_section=Class.getFilter().getSection();
//             String l_instituteID=request.getReqHeader().getInstituteID();
//             if(l_standard!=null&&!l_standard.isEmpty()&&l_section!=null&&!l_section.isEmpty()){
//                 
//                if(!bsv.standardSectionValidation(l_standard,l_section, l_instituteID, session, dbSession, inject)){
//                    status=false;
//                    errhandler.log_app_error("BS_VAL_003","standard or section");
//                }
//                 
//             } 

               
             
             if(l_authStatus!=null&&!l_authStatus.isEmpty()){
                 
                if(!bsv.authStatusValidation(l_authStatus, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","authStatus");
                }
                 
             }
   

             
            dbg("end of student Class detailDataValidation");
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
