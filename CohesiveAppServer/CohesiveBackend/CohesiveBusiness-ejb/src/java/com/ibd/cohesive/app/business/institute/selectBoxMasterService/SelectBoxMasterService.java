/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.selectBoxMasterService;

import com.ibd.businessViews.ISelectBoxMasterService;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Remote(ISelectBoxMasterService.class)
@Stateless
public class SelectBoxMasterService implements ISelectBoxMasterService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public SelectBoxMasterService(){
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
       dbg("inside SelectBoxMasterService--->processing");
       dbg("SelectBoxMasterService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<ISelectBoxMasterService>selectBoxMasterEJB=new BusinessEJB();
       selectBoxMasterEJB.set(this);
      
       exAudit=bs.getExistingAudit(selectBoxMasterEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,selectBoxMasterEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,selectBoxMasterEJB);
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
                clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);
                           
//if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                  // BSProcessingException ex=new BSProcessingException("response contains special characters");
                   //dbg(ex);
                   //session.clearSessionObject();
                   //dbSession.clearSessionObject();
                   //throw ex;
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

    private  void buildBOfromReq(JsonObject p_request)throws BSProcessingException,DBProcessingException{
      SelectBoxMaster selectBoxMaster=new SelectBoxMaster();
      RequestBody<SelectBoxMaster> reqBody = new RequestBody<SelectBoxMaster>(); 
           
      try{
      dbg("inside institute selectBoxMaster buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      selectBoxMaster.setMaster(l_body.getString("master"));
      JsonArray entityArray=l_body.getJsonArray("input");
      
      selectBoxMaster.inputMap=new HashMap();
      
      for(int i=0;i<entityArray.size();i++){
          
          JsonObject inputObj=entityArray.getJsonObject(i);
          String entityName=inputObj.getString("entityName");
          String entityValue=inputObj.getString("entityValue");
          selectBoxMaster.inputMap.put(entityName, entityValue);
      }

      
        reqBody.set(selectBoxMaster);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside institute selectBoxMaster--->view");
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IPDataService pds=inject.getPdataservice();
        RequestBody<SelectBoxMaster> reqBody = request.getReqBody();
        BusinessService bs=inject.getBusinessService(session);
        SelectBoxMaster selectBoxMaster =reqBody.get();
        String master=selectBoxMaster.getMaster();
        String instituteID=null;
        Map<String,ArrayList<String>>l_classMap=null;
        dbg("master"+master);
        boolean exceptionRised=false;
        
        switch(master){
            
            case "class":
            
                   instituteID=selectBoxMaster.getInputMap().get("instituteID");
                   dbg("instituteID"+instituteID);
                   
                   try{
                   
                   
                       l_classMap= pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE","IVW_STANDARD_MASTER", session, dbSession);
                   }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                           session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                           session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                            exceptionRised=true;
                           selectBoxMaster.classMaster=new ClassMaster[1];
                           selectBoxMaster.classMaster[0]=new ClassMaster();
                           selectBoxMaster.classMaster[0].setClasss("Select option");
//                           selectBoxMaster.classMaster[0]=new ClassMaster();
//                           selectBoxMaster.classMaster[0].setClasss("ALL");
                       }else{
                           throw ex;
                       }
                       
                   }
                   
                   if(!exceptionRised){
                   
                       List<ArrayList<String>>l_classWiseList=  l_classMap.values().stream().collect(Collectors.toList());
                       dbg("l_classWiseList"+l_classWiseList.size());

                       selectBoxMaster.classMaster=new ClassMaster[l_classWiseList.size()+1];
                       selectBoxMaster.classMaster[0]=new ClassMaster();
                       selectBoxMaster.classMaster[0].setClasss("Select option");
                       
//                       boolean integerClassName=false;
//                       
//                       for(int i=1;i<=l_classWiseList.size();i++){
//
//                           String l_standard=l_classWiseList.get(i-1).get(1).trim();
//                           dbg("l_standard"+l_standard);
//                           
//                           try{
//                           
//                              Integer.parseInt(l_standard);
//                              dbg("After parseint");
//                              integerClassName=true;
//                           }catch(NumberFormatException ex){
//                 
//                               integerClassName=false;
//                           }
//                           
//                           dbg("integerClassName inside loop"+integerClassName);
//                           if(!integerClassName){
//                           
//                           
//                           String l_section=l_classWiseList.get(i-1).get(2).trim();
//                           String classs=l_standard+"/"+l_section;
//                           dbg("classs"+classs);
//                           selectBoxMaster.classMaster[i]=new ClassMaster();
//                           selectBoxMaster.classMaster[i].setClasss(classs);
//
//                           }else
//                               break;
//                           
//                       }
//                       
//                       dbg("integerClassName outside loop"+integerClassName);
//                       
//                      if(integerClassName){
//                          
//                          ArrayList<String>sortedClasses=bs.getSortedClasses(l_classMap);
//                          
//                          for(int i=0;i<sortedClasses.size();i++){
//                              
//                              String classs=sortedClasses.get(i);
//                              selectBoxMaster.classMaster[i+1]=new ClassMaster();
//                              selectBoxMaster.classMaster[i+1].setClasss(classs);
//                          }
//                          
//                          
//                      }
                       

                       ArrayList<String>sortedClasses=bs.getSortedClasses(l_classMap);
//                          
                          for(int i=0;i<sortedClasses.size();i++){
                              
                              String classs=sortedClasses.get(i);
                              selectBoxMaster.classMaster[i+1]=new ClassMaster();
                              selectBoxMaster.classMaster[i+1].setClasss(classs);
                          }
                       
                       
                   }   
                   
               break;
                
               case "standard":
            
                   instituteID=selectBoxMaster.getInputMap().get("instituteID");
                   dbg("instituteID"+instituteID);
                   HashSet<String>standardList=null;
                   
                   try{
                   
                    standardList=bs.getStandardsOfTheInstitute(instituteID, session, dbSession, inject);
                   
                   }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                           session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                           session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                           exceptionRised=true;
                           selectBoxMaster.standardMaster=new StandardMaster[1];
                           selectBoxMaster.standardMaster[0]=new StandardMaster();
                           selectBoxMaster.standardMaster[0].setStandard("Select option");
                        }else{
                           throw ex;
                       }
                       
                   }
                    if(!exceptionRised){
                   
                       selectBoxMaster.standardMaster=new StandardMaster[standardList.size()+1];
                       selectBoxMaster.standardMaster[0]=new StandardMaster();
                       selectBoxMaster.standardMaster[0].setStandard("Select option");

                       Iterator<String>standardIterator=standardList.iterator();

                       int i=1;
                       while(standardIterator.hasNext()){

                           String l_standard=standardIterator.next();
                           dbg("l_standard"+l_standard);
                           selectBoxMaster.standardMaster[i]=new StandardMaster();
                           selectBoxMaster.standardMaster[i].setStandard(l_standard);

                       }
                   
                    }
                   
                break;
                
                case "section":
            
                   instituteID=selectBoxMaster.getInputMap().get("instituteID");
                   dbg("instituteID"+instituteID);
                   HashSet<String>sectionList=null;
                   
                   try{
                   
                         sectionList=bs.getSectionsOfTheInstitute(instituteID, session, dbSession, inject);
                   
                   }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                           session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                           session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                           exceptionRised=true;
                           selectBoxMaster.sectionMaster=new SectionMaster[1];
                           selectBoxMaster.sectionMaster[0]=new SectionMaster();
                           selectBoxMaster.sectionMaster[0].setSection("Select option");
                         }else{
                           throw ex;
                       }
                       
                   }
                   
                   if(!exceptionRised){
                   
                       selectBoxMaster.sectionMaster=new SectionMaster[sectionList.size()+1];
                       selectBoxMaster.sectionMaster[0]=new SectionMaster();
                       selectBoxMaster.sectionMaster[0].setSection("Select option");

                       Iterator<String>sectionIterator=sectionList.iterator();

                       int j=1;
                       while(sectionIterator.hasNext()){

                           String l_section=sectionIterator.next();
                           dbg("l_section"+l_section);
                           selectBoxMaster.sectionMaster[j]=new SectionMaster();
                           selectBoxMaster.sectionMaster[j].setSection(l_section);
                           j++;
                       }
                   }
                   
                break;
                
                case "subject":
            
                   instituteID=selectBoxMaster.getInputMap().get("instituteID");
                   dbg("instituteID"+instituteID);
                   Map<String,ArrayList<String>>l_subjectMap=null;
                   
                   try{
                       l_subjectMap= pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE","IVW_SUBJECT_MASTER", session, dbSession);
              
                   }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                           session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                           session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                           exceptionRised=true;
                           selectBoxMaster.subjectMaster=new SubjectMaster[1];
                           selectBoxMaster.subjectMaster[0]=new SubjectMaster();
                           selectBoxMaster.subjectMaster[0].setSubjectID("Select option");
                           selectBoxMaster.subjectMaster[0].setSubjectName("Select option");
                         }else{
                           throw ex;
                       }
                       
                   }
                   
                   if(!exceptionRised){
                       
                       String[] l_pkey={instituteID};
                       ArrayList<String> instituteRecord=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE","INSTITUTE_MASTER", l_pkey);
                   
                       String masterVersionNumber=instituteRecord.get(9).trim();
                       
                       
                       List<ArrayList<String>>l_subjectList=  l_subjectMap.values().stream().filter(rec->rec.get(3).trim().equals(masterVersionNumber)).collect(Collectors.toList());
                       dbg("l_subjectList"+l_subjectList.size());

                       selectBoxMaster.subjectMaster=new SubjectMaster[l_subjectList.size()+1];
                       selectBoxMaster.subjectMaster[0]=new SubjectMaster();
                       selectBoxMaster.subjectMaster[0].setSubjectID("Select option");
                       selectBoxMaster.subjectMaster[0].setSubjectName("Select option");
                       for(int k=1;k<=l_subjectList.size();k++){

                           String l_subjectID=l_subjectList.get(k-1).get(1).trim();
                           String l_subjectName=l_subjectList.get(k-1).get(2).trim();
                           dbg("l_subjectName"+l_subjectName);
                           dbg("l_subjectID"+l_subjectID);
                           selectBoxMaster.subjectMaster[k]=new SubjectMaster();
                           selectBoxMaster.subjectMaster[k].setSubjectID(l_subjectID);
                           selectBoxMaster.subjectMaster[k].setSubjectName(l_subjectName);

                       }
                       
                   }
                   
                 break;
                 
                 case "examType":
            
                   instituteID=selectBoxMaster.getInputMap().get("instituteID");
                   dbg("instituteID"+instituteID);
                   Map<String,ArrayList<String>>l_examMap=null;
                   
                   try{
                   
                       l_examMap= pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE","IVW_INSTITUTE_EXAM_MASTER", session, dbSession);
               
                   }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                           session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                           session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                           exceptionRised=true;
                           selectBoxMaster.examMaster=new ExamMaster[1];
                           selectBoxMaster.examMaster[0]=new ExamMaster();
                           selectBoxMaster.examMaster[0].setExam("Select option");
                         }else{
                           throw ex;
                       }
                       
                   }
                   
                   if(!exceptionRised){
                       
                       
                       String masterVersion=bs.getMaxVersionOfTheInstitute(instituteID, session, dbSession, inject);
                       int versionIndex=bs.getVersionIndexOfTheTable("IVW_INSTITUTE_EXAM_MASTER", "INSTITUTE", session, dbSession, inject);
                   
                       List<ArrayList<String>>l_examList=  l_examMap.values().stream().filter(rec->rec.get(versionIndex).trim().equals(masterVersion)).collect(Collectors.toList());
                       dbg("l_examList"+l_examList.size());

                       selectBoxMaster.examMaster=new ExamMaster[l_examList.size()+1];
                       selectBoxMaster.examMaster[0]=new ExamMaster();
                       selectBoxMaster.examMaster[0].setExam("Select option");
                       for(int k=1;k<=l_examList.size();k++){

                           String l_exam=l_examList.get(k-1).get(1).trim();
                           dbg("l_exam"+l_exam);
                           selectBoxMaster.examMaster[k]=new ExamMaster();
                           selectBoxMaster.examMaster[k].setExam(l_exam);

                       }
                   }
                 break;
                 
                 case "notificationTypes":
            
                   instituteID=selectBoxMaster.getInputMap().get("instituteID");
                   dbg("instituteID"+instituteID);
                   Map<String,ArrayList<String>>l_notificationMap=null;
                   
                   try{
                   
                       l_notificationMap= pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE","IVW_NOTIFICATION_TYPE_MASTER", session, dbSession);
                
                   }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                           session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                           session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                           exceptionRised=true;
                           selectBoxMaster.notificationMaster=new NotificationMaster[1];
                           selectBoxMaster.notificationMaster[0]=new NotificationMaster();
                           selectBoxMaster.notificationMaster[0].setNotificationType("Select option");
                         }else{
                           throw ex;
                       }
                       
                   }
                   
                   if(!exceptionRised){
                   
                       String masterVersion=bs.getMaxVersionOfTheInstitute(instituteID, session, dbSession, inject);
                       int versionIndex=bs.getVersionIndexOfTheTable("IVW_NOTIFICATION_TYPE_MASTER", "INSTITUTE", session, dbSession, inject);
                       List<ArrayList<String>>l_notificationList=  l_notificationMap.values().stream().filter(rec->rec.get(versionIndex).trim().equals(masterVersion)).collect(Collectors.toList());
                       dbg("l_notificationList"+l_notificationList.size());

                       selectBoxMaster.notificationMaster=new NotificationMaster[l_notificationList.size()+1];
                       selectBoxMaster.notificationMaster[0]=new NotificationMaster();
                       selectBoxMaster.notificationMaster[0].setNotificationType("Select option");
                       for(int k=1;k<=l_notificationList.size();k++){

                           String l_notificationType=l_notificationList.get(k-1).get(1).trim();
                           dbg("l_notificationType"+l_notificationType);
                           selectBoxMaster.notificationMaster[k]=new NotificationMaster();
                           selectBoxMaster.notificationMaster[k].setNotificationType(l_notificationType);

                       }
                   }
                   
                 break;
                 
                 case "feeType":
            
                   instituteID=selectBoxMaster.getInputMap().get("instituteID");
                   dbg("instituteID"+instituteID);
                   Map<String,ArrayList<String>>l_feeMap=null;
                   
                   try{
                   
                       
                       
                    l_feeMap= pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE","IVW_FEE_TYPE_MASTER", session, dbSession);
                  
                    }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                           session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                           session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                           exceptionRised=true;
                           selectBoxMaster.feeMaster=new FeeMaster[1];
                           selectBoxMaster.feeMaster[0]=new FeeMaster();
                           selectBoxMaster.feeMaster[0].setFeeType("Select option");
                        }else{
                           throw ex;
                       }
                       
                   }
                    
                   if(!exceptionRised){
                       
                       
                       String masterVersion=bs.getMaxVersionOfTheInstitute(instituteID, session, dbSession, inject);
                       int versionIndex=bs.getVersionIndexOfTheTable("IVW_FEE_TYPE_MASTER", "INSTITUTE", session, dbSession, inject);
                   
                       List<ArrayList<String>>l_feeList=  l_feeMap.values().stream().filter(rec->rec.get(versionIndex).trim().equals(masterVersion)).collect(Collectors.toList());
                       dbg("l_feeList"+l_feeList.size());
                       selectBoxMaster.feeMaster=new FeeMaster[l_feeList.size()+1];
                       selectBoxMaster.feeMaster[0]=new FeeMaster();
                       selectBoxMaster.feeMaster[0].setFeeType("Select option");
                       for(int k=1;k<=l_feeList.size();k++){

                           String l_feeType=l_feeList.get(k-1).get(1).trim();
                           dbg("l_feeType"+l_feeType);
                           selectBoxMaster.feeMaster[k]=new FeeMaster();
                           selectBoxMaster.feeMaster[k].setFeeType(l_feeType);

                       }
                   }
                 break;
                 
                 case "periodNumber":
            
                   String standard=selectBoxMaster.getInputMap().get("standard");
                   String section=selectBoxMaster.getInputMap().get("section");
                   dbg("standard"+standard);
                   dbg("section"+section);
                   Map<String,ArrayList<String>>l_perioMap=null;
         
                   try{
                   
                      l_perioMap= pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE","IVW_FEE_TYPE_MASTER", session, dbSession);
             
                   }catch(DBValidationException ex){
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                           session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                           session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                           exceptionRised=true;
                           selectBoxMaster.periodmaster=new PeriodMaster[1];
                           selectBoxMaster.periodmaster[0]=new PeriodMaster();
                           selectBoxMaster.periodmaster[0].setPeriodNo("Select option");
                           selectBoxMaster.periodmaster[0].setStartTimeHour("Select option");
                           selectBoxMaster.periodmaster[0].setStartTimeMin("Select option");
                           selectBoxMaster.periodmaster[0].setEndTimeHour("Select option");
                           selectBoxMaster.periodmaster[0].setEndTimeMin("Select option");
                        }else{
                           throw ex;
                       }
                       
                   }
                   
                   if(!exceptionRised){
                      
                       List<ArrayList<String>>l_periodList=  l_perioMap.values().stream().filter(rec->rec.get(1).trim().equals(standard)&&rec.get(2).trim().equals(section)).collect(Collectors.toList());
                       dbg("l_periodList"+l_periodList.size());

                       selectBoxMaster.periodmaster=new PeriodMaster[l_periodList.size()+1];
                       selectBoxMaster.periodmaster[0]=new PeriodMaster();
                       selectBoxMaster.periodmaster[0].setPeriodNo("Select option");
                       selectBoxMaster.periodmaster[0].setStartTimeHour("Select option");
                       selectBoxMaster.periodmaster[0].setStartTimeMin("Select option");
                       selectBoxMaster.periodmaster[0].setEndTimeHour("Select option");
                       selectBoxMaster.periodmaster[0].setEndTimeMin("Select option");
                       for(int k=1;k<=l_periodList.size();k++){

                           String l_periodNumber=l_periodList.get(k-1).get(3).trim();
                           String l_startTimeHour=l_periodList.get(k-1).get(4).trim();
                           String l_startTimeMin=l_periodList.get(k-1).get(5).trim();
                           String l_endTimHour=l_periodList.get(k-1).get(6).trim();
                           String l_endTimMin=l_periodList.get(k-1).get(7).trim();

                           dbg("l_periodNumber"+l_periodNumber);
                           dbg("l_startTimeHour"+l_startTimeHour);
                           dbg("l_startTimeMin"+l_startTimeMin);
                           dbg("l_endTimHour"+l_endTimHour);
                           dbg("l_endTimMin"+l_endTimMin);
                           selectBoxMaster.periodmaster[k]=new PeriodMaster();
                           selectBoxMaster.periodmaster[k].setPeriodNo(l_periodNumber);
                           selectBoxMaster.periodmaster[k].setStartTimeHour(l_startTimeHour);
                           selectBoxMaster.periodmaster[k].setStartTimeMin(l_startTimeMin);
                           selectBoxMaster.periodmaster[k].setEndTimeHour(l_endTimHour);
                           selectBoxMaster.periodmaster[k].setEndTimeMin(l_endTimMin);

                       }
                   }
                 break;
            
        }
        
        
  
            
        
          dbg("end of  completed institute selectBoxMaster--->view");                
        }catch(DBValidationException ex){
            throw ex;
//        }catch(BSValidationException ex){
//            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
    

    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObjectBuilder body=Json.createObjectBuilder();
        try{
        dbg("inside institute selectBoxMaster buildJsonResFromBO");    
        RequestBody<SelectBoxMaster> reqBody = request.getReqBody();
        SelectBoxMaster selectBoxMaster =reqBody.get();
        String master=selectBoxMaster.getMaster();
        JsonArrayBuilder inputArray=Json.createArrayBuilder();
        Iterator<String>inputIterator=selectBoxMaster.getInputMap().keySet().iterator();
        
        while(inputIterator.hasNext()){
            
            String entityName=inputIterator.next();
            String entityValue=selectBoxMaster.getInputMap().get(entityName);
            
            inputArray.add(Json.createObjectBuilder().add("entityName", entityName)
                                                      .add("entityValue", entityValue));
            
        }
             
        switch(master){
            
            case "class":
                JsonArrayBuilder classMaster=Json.createArrayBuilder();
                for(int i=0;i<selectBoxMaster.classMaster.length;i++){
            
                      classMaster.add(selectBoxMaster.classMaster[i].getClasss());
                }
               
                
                body=Json.createObjectBuilder()  .add("ClassMaster", classMaster)
                                                 .add("master", master)
                                                 .add("input", inputArray);
                break;
                
                case "standard":
                JsonArrayBuilder standardMaster=Json.createArrayBuilder();
                for(int i=0;i<selectBoxMaster.standardMaster.length;i++){
            
                      standardMaster.add(selectBoxMaster.standardMaster[i].getStandard());
                }
               
                
                body=Json.createObjectBuilder()  .add("StandardMaster", standardMaster)
                                                 .add("master", master)
                                                 .add("input", inputArray);
                break;
                
                case "section":
                JsonArrayBuilder sectionMaster=Json.createArrayBuilder();
                for(int i=0;i<selectBoxMaster.sectionMaster.length;i++){
            
                      sectionMaster.add(selectBoxMaster.sectionMaster[i].getSection());
                }
               
                
                body=Json.createObjectBuilder()  .add("SectionMaster", sectionMaster)
                                                 .add("master", master)
                                                 .add("input", inputArray);
                break;
                
                case "subject":
                    JsonArrayBuilder subjectMaster=Json.createArrayBuilder();
                    for(int i=0;i<selectBoxMaster.subjectMaster.length;i++){

                          subjectMaster.add(Json.createObjectBuilder().add("id",selectBoxMaster.subjectMaster[i].getSubjectID())
                                                                      .add("name", selectBoxMaster.subjectMaster[i].getSubjectName()));
                    }


                    body=Json.createObjectBuilder()  .add("SubjectMaster", subjectMaster)
                                                     .add("master", master)
                                                     .add("input", inputArray);
                    break;
                    
                case "examType":
                    JsonArrayBuilder examMaster=Json.createArrayBuilder();
                    for(int i=0;i<selectBoxMaster.examMaster.length;i++){

                          examMaster.add(selectBoxMaster.examMaster[i].getExam());
                    }


                    body=Json.createObjectBuilder()  .add("ExamMaster", examMaster)
                                                     .add("master", master)
                                                     .add("input", inputArray);
                    break;   
                    
                    
                case "notificationTypes":
                    JsonArrayBuilder notificationMaster=Json.createArrayBuilder();
                    for(int i=0;i<selectBoxMaster.notificationMaster.length;i++){

                          notificationMaster.add(selectBoxMaster.notificationMaster[i].getNotificationType());
                    }


                    body=Json.createObjectBuilder()  .add("NotificationMaster", notificationMaster)
                                                     .add("master", master)
                                                     .add("input", inputArray);
                    break;
                    
                case "feeType":
                    JsonArrayBuilder feeMaster=Json.createArrayBuilder();
                    for(int i=0;i<selectBoxMaster.feeMaster.length;i++){

                          feeMaster.add(selectBoxMaster.feeMaster[i].getFeeType());
                    }


                    body=Json.createObjectBuilder()  .add("FeeMaster", feeMaster)
                                                     .add("master", master)
                                                     .add("input", inputArray);
                    break;  
                    
                 case "periodNumber":
                    JsonArrayBuilder periodMaster=Json.createArrayBuilder();
                    for(int i=0;i<selectBoxMaster.periodmaster.length;i++){

                          periodMaster.add(selectBoxMaster.periodmaster[i].getPeriodNo())
                                      .add(selectBoxMaster.periodmaster[i].getStartTimeHour())
                                      .add(selectBoxMaster.periodmaster[i].getStartTimeMin())
                                      .add(selectBoxMaster.periodmaster[i].getEndTimeHour())
                                      .add(selectBoxMaster.periodmaster[i].getEndTimeMin());
                    }


                    body=Json.createObjectBuilder()  .add("PeriodMaster", periodMaster)
                                                     .add("master", master)
                                                     .add("input", inputArray);
                    break;
           }

                                            
              dbg(body.toString());  
           dbg("end of institute selectBoxMaster buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body.build();
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside institute selectBoxMaster--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }
       
       
       dbg("end of institute selectBoxMaster--->businessValidation"); 
       }catch(BSProcessingException ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
      
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
        dbg("inside institute selectBoxMaster master mandatory validation");
//        RequestBody<SelectBoxMaster> reqBody = request.getReqBody();
//        SelectBoxMaster selectBoxMaster =reqBody.get();
         
         
        
         
          
         
          
          
        dbg("end of institute selectBoxMaster master mandatory validation");
        }catch (Exception ex) {
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
