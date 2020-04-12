/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.managementDashBoard;

import com.ibd.businessViews.IManagementDashBoardService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.student.studentattendanceservice.AuditDetails;
import com.ibd.cohesive.app.business.teacher.teacherDashBoard.Pending;
import com.ibd.cohesive.app.business.teacher.teacherDashBoard.TeacherDashBoardService;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.exception.ExceptionHandler;
import com.ibd.cohesive.app.business.util.message.request.Parsing;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
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
@Remote(IManagementDashBoardService.class)
@Stateless
public class ManagementDashBoardService implements IManagementDashBoardService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public ManagementDashBoardService(){
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
       String l_lockKey=null;
       IBusinessLockService businessLock=null;
       try{
       session.createSessionObject();
       dbSession.createDBsession(session);
       l_session_created_now=session.isI_session_created_now();
       ErrorHandler errhandler = session.getErrorhandler();
       BSValidation bsv=inject.getBsv(session);
       bs=inject.getBusinessService(session);
       ITransactionControlService tc=inject.getTransactionControlService();
       businessLock=inject.getBusinessLockService();
       dbg("inside ManagementDashBoardService--->processing");
       dbg("ManagementDashBoardService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 

       BusinessEJB<IManagementDashBoardService>managementDashBoardEJB=new BusinessEJB();
       managementDashBoardEJB.set(this);
      
       exAudit=bs.getExistingAudit(managementDashBoardEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,managementDashBoardEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,managementDashBoardEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in teacher dash board");
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
           if(l_lockKey!=null){
               businessLock.removeBusinessLock(request, l_lockKey, session);
            }
           request=null;
            bs=inject.getBusinessService(session);
            if(l_session_created_now){
                bs.responselogging(jsonResponse, inject,session,dbSession);
             clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
            dbg("Response"+jsonResponse.toString());
            BSValidation bsv=inject.getBsv(session);
            //if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                      clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes  
            //BSProcessingException ex=new BSProcessingException("response contains special characters");
              //  dbg(ex);
                
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
      RequestBody<ManagementDashBoard> reqBody = new RequestBody<ManagementDashBoard>(); 
           
      try{
      dbg("inside teacher assignment buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      ManagementDashBoard dashBoard=new ManagementDashBoard();
      dashBoard.setUserID(l_body.getString("userID"));
      
      
      
        reqBody.set(dashBoard);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException{
                
        try{      
        dbg("inside teacher assignment--->view");
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<ManagementDashBoard> reqBody = request.getReqBody();
        BusinessService bs=inject.getBusinessService(session);
        ManagementDashBoard managementDashBoard=reqBody.get();
        String userID=managementDashBoard.getUserID();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        
        dbg("fee processing starts");
        
        int totalFeeAmount=0;
        int totalPaidAmount=0;
        int totalBalanceAmount=0;
        
        ArrayList<String> classesOfTheInstitutes=bs.getClassesOfTheInstitute(userID, session, dbSession, inject);
        
        
        for(int i=0;i<classesOfTheInstitutes.size();i++){
            
            String classs=classesOfTheInstitutes.get(i);
            String standard=classs.split("~")[0];
            String section=classs.split("~")[1];
            
            Map<String,DBRecord>attendanceMap=readBuffer.readTable("REPORT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+userID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section,"CLASS", "CLASS_FEE_AMOUNT_REPORT", session, dbSession);
            
            int feeAmount= attendanceMap.values().stream().mapToInt(rec->Integer.parseInt(rec.getRecord().get(3).trim())).sum();
            int paidAmount= attendanceMap.values().stream().mapToInt(rec->Integer.parseInt(rec.getRecord().get(4).trim())).sum();
            int balanceAmount= attendanceMap.values().stream().mapToInt(rec->Integer.parseInt(rec.getRecord().get(5).trim())).sum();
            
            totalFeeAmount=totalFeeAmount+feeAmount;
            totalPaidAmount=totalPaidAmount+paidAmount;
            totalBalanceAmount=totalBalanceAmount+balanceAmount;
        }
        dbg("totalFeeAmount"+totalFeeAmount);
        dbg("totalPaidAmount"+totalPaidAmount);
        dbg("totalBalanceAmount"+totalBalanceAmount);
     
        managementDashBoard.setTotalFeeAmount(Integer.toString(totalFeeAmount));
        managementDashBoard.setTotalPaidAmount(Integer.toString(totalPaidAmount));
        managementDashBoard.setTotalBalanceAmount(Integer.toString(totalBalanceAmount));
        
        dbg("fee processing ends");
       
        
        dbg("un auth processing starts");
        
        TeacherDashBoardService teacherDash=new TeacherDashBoardService();
        ArrayList<Pending>entryPendingList=getSelfRequestPendingList(userID);
        ArrayList<Pending>unAuthPendingList=getUnAuthPendingList(userID);
        managementDashBoard.entryPendingList=entryPendingList;
        managementDashBoard.unAuthPendingList=unAuthPendingList;
        
        dbg("un auth processing ends");
        
        
          dbg("end of  teacher dashboard --->view");                
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
    
   private ArrayList<Pending> getSelfRequestPendingList(String userID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
      boolean l_session_created_now=false;
       try{
           session.createSessionObject();
           dbSession.createDBsession(session);
           dbg("inside getSelfRequestPendingList");
           dbg("userID"+userID);
           ArrayList<Pending>pendingList=new ArrayList();
           BusinessService bs=inject.getBusinessService(session);
           String instituteID=request.getReqHeader().getInstituteID();
          //Key - role id - Values(RoleMap) -- Key ServiceName-- Values(Type) 
           Role[] roleArray=bs.getRolesOfTheUser(userID, session, dbSession, inject,"CUD");
//           dbg("inside getSelfRequestPendingList servicesList size"+servicesList.size());
           dbg("role iteration starts in getSelfRequestPendingList");
           for(int i=0;i<roleArray.length;i++){
               
             String roleId=  roleArray[i].getRoleID();
             dbg("roleId"+roleId);
             Entity[] entityArr=roleArray[i].getEntity();
             roleArray[i].teacherIds=new HashSet();
             roleArray[i].institutes=new HashSet();
             roleArray[i].studentIds=new HashSet();
             roleArray[i].classes=new HashSet();
//             roleArray[i].classes=new HashSet();
 
             for(int j=0;j<entityArr.length;j++){

               Entity entityObj=entityArr[j];
               String standard=entityObj.getStandard();
               String section=entityObj.getSection();
               String teacherID=entityObj.getTeacherID();
               String l_instituteID=entityObj.getInstituteID();
               dbg("inside getSelfRequestPendingList-->standard"+standard);
               dbg("inside getSelfRequestPendingList-->section"+section);
               dbg("inside getSelfRequestPendingList-->teacherID"+teacherID);
               dbg("inside getSelfRequestPendingList-->l_instituteID"+l_instituteID);
               
               if(entityObj.getStandard()!=null&&entityObj.getSection()!=null){
                   
                    bs.getStudentListFromClass(entityObj.getStandard(),entityObj.getSection(),instituteID,session,dbSession,inject,roleArray[i].studentIds,roleArray[i].classes);
                   
                  
               }
               
               if(entityObj.getTeacherID()!=null&&entityObj.getInstituteID()!=null){
                   
                   
                   if(!entityObj.getTeacherID().equals("ALL")){
                       
                       roleArray[i].teacherIds.add(entityObj.getTeacherID());
                       
                   }else{
                       
                       bs.getTeachersOfTheInstitute(entityObj.getInstituteID(), session, dbSession, inject, roleArray[i].teacherIds);
                   }
                   
                   
               }else if(entityObj.getTeacherID()==null&&entityObj.getInstituteID()!=null){
                   
                   
                   roleArray[i].institutes.add(entityObj.getInstituteID());
                   
               }
               
             }
             
             dbg("inside getSelfRequestPendingList-->studentIds"+roleArray[i].studentIds.size());
             dbg("inside getSelfRequestPendingList-->teacherIds"+roleArray[i].teacherIds.size());
             dbg("inside getSelfRequestPendingList-->classes"+roleArray[i].classes.size());
             dbg("inside getSelfRequestPendingList-->institutes"+roleArray[i].institutes.size());
             Map<String,String>serviceMap=roleArray[i].getService();
             Iterator<String>serviceIterator=serviceMap.keySet().iterator();
             dbg("inside getSelfRequestPendingList--->serviceIteration");
             while(serviceIterator.hasNext()){
               
               String service=  serviceIterator.next();
               String serviceType=serviceMap.get(service);
               dbg("serviceName"+service);
               dbg("serviceType"+serviceType);
               String tableName=bs.getMasterTableOfTheService(service, session, dbSession, inject);
               
               switch(serviceType){
                   
                   case "Student":
                   
    //                    ArrayList<String>studentsList= bs.getAccessibleStudents(userID,instituteID,session,dbSession,inject);
//                    if(!roleArray[i].studentIds.isEmpty()){  
                     if(roleArray[i].studentIds!=null&&!roleArray[i].studentIds.isEmpty()){  
                        Pending studentPending=getStudentPendingDetail(userID,service,tableName,roleArray[i].studentIds,"ENTRY");
                         pendingList.add(studentPending);
                    }
                    break;
                   
                  case "Teacher":
                   
//                     ArrayList<String>teachersList= bs.getAccessibleTeachers(userID,instituteID,session,dbSession,inject);
//                    if(!roleArray[i].teacherIds.isEmpty()){  
                      if(roleArray[i].teacherIds!=null&&!roleArray[i].teacherIds.isEmpty()){  
                       Pending teacherPending=getTeacherPendingDetail(userID,service,tableName,roleArray[i].teacherIds,"ENTRY");
                       pendingList.add(teacherPending);
                       
                    }
                    break;  
                    
                  case "Class":
                   
//                     ArrayList<String>classList= bs.getAccessibleClasses(userID,instituteID,session,dbSession,inject);
//                  if(!roleArray[i].classes.isEmpty()){
                    if(roleArray[i].classes!=null&&!roleArray[i].classes.isEmpty()){  
                      
                      Pending classPending=getClassPendingDetail(userID,service,tableName,roleArray[i].classes,"ENTRY");
                      pendingList.add(classPending);
                      
                  }
                    break; 
                    
                  case "Institute":
                   
//                     ArrayList<String>instituteList= bs.getAccessibleInstitutes(userID,session,dbSession,inject);
//                if(!roleArray[i].institutes.isEmpty()){
                  if(roleArray[i].institutes!=null&&!roleArray[i].institutes.isEmpty()){  
                     Pending institutePending=getInstitutePendingDetail(userID,service,tableName,roleArray[i].institutes,"ENTRY");
                     pendingList.add(institutePending);
                  }
                    break;  
               }
           
           
             }
           }
           dbg("end of getSelfRequestPendingList");
           return pendingList;
       }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }finally{
          if(l_session_created_now){  
            session.clearSessionObject();
            dbSession.clearSessionObject();
          }
        }
       
   }
   
  public ArrayList<Pending> getSelfRequestPendingList(String userID,CohesiveSession session,Request request)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
    System.out.print("inside cross call of getSelfRequestPendingList");
    CohesiveSession tempSession = this.session;
    Request tempRequest=this.request;
    ArrayList<Pending> response =null;
    try{
        this.session=session;
        this.request=request;
        response =getSelfRequestPendingList(userID);
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
           this.request=tempRequest;
        }
 return response;
    }
     
    private ArrayList<Pending> getUnAuthPendingList(String userID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    
        boolean l_session_created_now=false;
        try{
           session.createSessionObject();
           dbSession.createDBsession(session);
           l_session_created_now=session.isI_session_created_now();
           dbg("inside getPendingPendingList");
           dbg("userID"+userID);
           
           ArrayList<Pending>pendingList=new ArrayList();
           BusinessService bs=inject.getBusinessService(session);
           String instituteID=request.getReqHeader().getInstituteID();
          //Key - role id - Values(RoleMap) -- Key ServiceName-- Values(Type) 
           Role[] roleArray=bs.getRolesOfTheUser(userID, session, dbSession, inject,"A");
//           dbg("inside getUnAuthPendingList servicesList size"+servicesList.size());
           dbg("role iteration starts in getUnAuthPendingList");
           for(int i=0;i<roleArray.length;i++){
               
             Entity[] entityArr=roleArray[i].getEntity();
             roleArray[i].teacherIds=new HashSet();
             roleArray[i].institutes=new HashSet();
             roleArray[i].studentIds=new HashSet();
             roleArray[i].classes=new HashSet();
//             roleArray[i].classes=new HashSet();
 
             for(int j=0;j<entityArr.length;j++){

               Entity entityObj=entityArr[j];
               String standard=entityObj.getStandard();
               String section=entityObj.getSection();
               String teacherID=entityObj.getTeacherID();
               String l_instituteID=entityObj.getInstituteID();
               dbg("inside getUnAuthPendingList-->standard"+standard);
               dbg("inside getUnAuthPendingList-->section"+section);
               dbg("inside getUnAuthPendingList-->teacherID"+teacherID);
               dbg("inside getUnAuthPendingList-->l_instituteID"+l_instituteID);
               
               if(entityObj.getStandard()!=null&&entityObj.getSection()!=null){
                   
                    bs.getStudentListFromClass(entityObj.getStandard(),entityObj.getSection(),instituteID,session,dbSession,inject,roleArray[i].studentIds,roleArray[i].classes);
                   
                  
               }
               
               if(entityObj.getTeacherID()!=null&&entityObj.getInstituteID()!=null){
                   
                   
                   if(!entityObj.getTeacherID().equals("ALL")){
                       
                       roleArray[i].teacherIds.add(entityObj.getTeacherID());
                       
                   }else{
                       
                       bs.getTeachersOfTheInstitute(entityObj.getInstituteID(), session, dbSession, inject, roleArray[i].teacherIds);
                   }
                   
                   
               }else if(entityObj.getTeacherID()==null&&entityObj.getInstituteID()!=null){
                   
                   
                   roleArray[i].institutes.add(entityObj.getInstituteID());
                   
               }
               
             }
 
             Map<String,String>serviceMap=roleArray[i].getService();
             Iterator<String>serviceIterator=serviceMap.keySet().iterator();
             dbg("inside getUnAuthPendingList--->serviceIteration");
             while(serviceIterator.hasNext()){
               
               String service=  serviceIterator.next();
               String serviceType=serviceMap.get(service);
               dbg("serviceType"+serviceType);
               String tableName=bs.getMasterTableOfTheService(service, session, dbSession, inject);
               
               switch(serviceType){
                   
                   case "Student":
                   
//                     ArrayList<String>studentsList= bs.getAccessibleStudents(userID,instituteID,session,dbSession,inject);
                     if(!roleArray[i].studentIds.isEmpty()){  
                       
                         Pending studentPending=getStudentPendingDetail(userID,service,tableName,roleArray[i].studentIds,"UN_AUTH");
                         pendingList.add(studentPending);
                     
                     }
                    break;
                   
                  case "Teacher":
                   
//                     ArrayList<String>teachersList= bs.getAccessibleTeachers(userID,instituteID,session,dbSession,inject);
                   if(!roleArray[i].teacherIds.isEmpty()){  
                      
                      Pending teacherPending=getTeacherPendingDetail(userID,service,tableName,roleArray[i].teacherIds,"UN_AUTH");
                     pendingList.add(teacherPending);
                     
                   }
                    break;  
                    
                  case "Class":
                   
                      if(!roleArray[i].classes.isEmpty()){
    //                   ArrayList<String>classList= bs.getAccessibleClasses(userID,instituteID,session,dbSession,inject);
                         Pending classPending=getClassPendingDetail(userID,service,tableName,roleArray[i].classes,"UN_AUTH");
                         pendingList.add(classPending);
                         
                      }  
                    break; 
                    
                  case "Institute":
                   
                      if(!roleArray[i].institutes.isEmpty()){
    //                     ArrayList<String>instituteList= bs.getAccessibleInstitutes(userID,session,dbSession,inject);
                         Pending institutePending=getInstitutePendingDetail(userID,service,tableName,roleArray[i].institutes,"UN_AUTH");
                         pendingList.add(institutePending);
                         
                      }
                    break;  
               }
           
           
             }
           }
           
          
           dbg("end of getPendingPendingList");
           return pendingList;
       }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }finally{
          if(l_session_created_now){  
            session.clearSessionObject();
            dbSession.clearSessionObject();
          }
        }
       
   } 
   public ArrayList<Pending> getUnAuthPendingList(String userID,CohesiveSession session,Request request)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
    System.out.print("inside cross call of getUnAuthPendingList");
    CohesiveSession tempSession = this.session;
    Request tempRequest=this.request;
    ArrayList<Pending> response =null;
    try{
        this.session=session;
        this.request=request;
        response =getUnAuthPendingList(userID);
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
           this.request=tempRequest;
        }
 return response;
    }
   
     private Pending getStudentPendingDetail(String userID,String  serviceName,String tableName,HashSet<String> StudentList ,String filter)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
        
      try{  
           dbg("inside getStudentPendingDetail");
           dbg("filter"+filter);
           dbg("ServiceName"+serviceName);
           dbg("tableName"+tableName);
           dbg("StudentList"+StudentList.size());
           BusinessService bs=inject.getBusinessService(session);
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IMetaDataService mds=inject.getMetadataservice();
           String instituteID=request.getReqHeader().getInstituteID();
           int makerColID=0;
           int authStatColID=0;
           
           if(!serviceName.contains("Attendance")){
               makerColID=mds.getColumnMetaData("STUDENT", tableName, "MAKER_ID", session).getI_ColumnID();
               authStatColID=mds.getColumnMetaData("STUDENT", tableName, "AUTH_STATUS", session).getI_ColumnID();
           }
           ArrayList<String>primaryKeys=new ArrayList();
           int count=0;
           Map<String,DBRecord>tableMap=null;
    
           Iterator<String>studentIterator=StudentList.iterator();
           
//            for(int i=0;i<StudentList.size();i++){
            while(studentIterator.hasNext()){
                
                String studentID=studentIterator.next();
                dbg("inside getStudentPendingDetail studentID"+studentID);
                
                try{
                
                    tableMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", tableName, session, dbSession,"true");
                 
                }catch(DBValidationException ex){
                    
                    if(!ex.toString().contains("DB_VAL_011")){
                        throw ex;
                    }
                }
                 
                 
                 if(tableMap!=null){
                 
                     if(serviceName.contains("Attendance")){
                         return  getSTAttendanceCount(userID,serviceName,"Student",tableMap,filter );
                        
                   }
                     
                     
                     
                 Iterator<String>keyIterator=tableMap.keySet().iterator();
                 
                 while(keyIterator.hasNext()){
                     
                     String key=keyIterator.next();
                     ArrayList<String> value=tableMap.get(key).getRecord();
                  
                     if(filter.equals("UN_AUTH")){
                         
                         if(value.get(authStatColID-1).trim().equals("U")){
                         
                             primaryKeys.add(key.replace("~", "_"));
                             count++;
                         }
                         
                     }else{
                     
                         if(value.get(makerColID-1).trim().equals(userID)&&value.get(authStatColID-1).trim().equals("U")){

                             primaryKeys.add(key.replace("~", "_"));
                             count++;
                         }
                     }
                     
                 }
                 
                 } 
            }
               
                Pending pending=new Pending();               
                pending.setServiceName(serviceName);
                pending.setServiceType("Student");
                pending.setCount(Integer.toString(count));
                pending.setPrimaryKey(primaryKeys);
               
          dbg("end of getStudentRecords count"+count);
          return pending;
       }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        } 
        
    }
     
     private Pending getTeacherPendingDetail(String userID,String  serviceName,String tableName,HashSet<String> teacherList ,String filter)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
        
      try{  
           dbg("inside getTeacherRecords");
           dbg("filter"+filter);
           dbg("ServiceName"+serviceName);
           dbg("tableName"+tableName);
           BusinessService bs=inject.getBusinessService(session);
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IMetaDataService mds=inject.getMetadataservice();
           String instituteID=request.getReqHeader().getInstituteID();
           int makerColID=0;
           int authStatColID=0;
           
           if(!serviceName.contains("Attendance")){
                   makerColID=mds.getColumnMetaData("TEACHER", tableName, "MAKER_ID", session).getI_ColumnID();
                   authStatColID=mds.getColumnMetaData("TEACHER", tableName, "AUTH_STATUS", session).getI_ColumnID();
           }
           ArrayList<String>primaryKeys=new ArrayList();
           Map<String,DBRecord>tableMap=null;
           int count=0;
    
            Iterator<String>teacherIterator=teacherList.iterator();
           
//            for(int i=0;i<StudentList.size();i++){
            while(teacherIterator.hasNext()){
                
                String teacherID=teacherIterator.next();
                
                try{
                
                 tableMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID,"TEACHER", tableName, session, dbSession,"true");
                 
                 }catch(DBValidationException ex){
                    
                    if(!ex.toString().contains("DB_VAL_011")){
                        throw ex;
                    }
                }
                 
                 if(tableMap!=null){
                 
                 Iterator<String>keyIterator=tableMap.keySet().iterator();
                 
                 if(serviceName.contains("Attendance")){
                      return  getSTAttendanceCount(userID,serviceName,"Teacher",tableMap,filter );
                        
                   }
                 
                 
                 while(keyIterator.hasNext()){
                     
                     String key=keyIterator.next();
                     ArrayList<String> value=tableMap.get(key).getRecord();
                     
                     if(filter.equals("UN_AUTH")){
                     
                         if(value.get(authStatColID-1).trim().equals("U")){

                             primaryKeys.add(key.replace("~", "_"));
                             count++;
                         }
                     }else{
                         
                         if(value.get(makerColID-1).trim().equals(userID)&&value.get(authStatColID-1).trim().equals("U")){

                             primaryKeys.add(key.replace("~", "_"));
                             count++;
                         }
                         
                     }
                 }
                 }
                 
            }
               
               
                Pending pending=new Pending();               
                pending.setServiceName(serviceName);
                pending.setServiceType("Teacher");
                pending.setCount(Integer.toString(count));
                pending.setPrimaryKey(primaryKeys);
               
          dbg("end of getTeacherRecords count"+count);
          return pending;
       }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        } 
        
    }
     
     
    private Pending getClassPendingDetail(String userID,String  serviceName,String tableName,HashSet<String> classList,String filter )throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
        
      try{  
           dbg("inside getClassPendingDetail");
           dbg("filter"+filter);
           dbg("ServiceName"+serviceName);
           dbg("tableName"+tableName);
           
           BusinessService bs=inject.getBusinessService(session);
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IMetaDataService mds=inject.getMetadataservice();
           String instituteID=request.getReqHeader().getInstituteID();
           int makerColID=0;
           int authStatColID=0;
           if(!serviceName.contains("Attendance")){
           
               makerColID=mds.getColumnMetaData("CLASS", tableName, "MAKER_ID", session).getI_ColumnID();
               authStatColID=mds.getColumnMetaData("CLASS", tableName, "AUTH_STATUS", session).getI_ColumnID();
           
           }
           ArrayList<String>primaryKeys=new ArrayList();
           int count=0;
           Map<String,DBRecord>tableMap=null;
    
            Iterator<String>classIterator=classList.iterator();
           
//            for(int i=0;i<StudentList.size();i++){
            while(classIterator.hasNext()){
                
                String classs=classIterator.next();
                String standard=classs.split("~")[0];
		String section=classs.split("~")[1];
                
                try{
                
                   tableMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section,"CLASS", tableName, session, dbSession,"true");
                 
                 }catch(DBValidationException ex){
                    
                    if(!ex.toString().contains("DB_VAL_011")){
                        throw ex;
                    }
                }
                 
                 dbg("tableMap"+tableMap);
                 if(tableMap!=null){
                 
                   if(serviceName.contains("Attendance")){
                      return  getCAttendanceCount(userID,serviceName,"Class",tableMap,filter );
                        
                   }   
                     
                     
                     
                 Iterator<String>keyIterator=tableMap.keySet().iterator();
                 
                 while(keyIterator.hasNext()){
                     
                     String key=keyIterator.next();
                     ArrayList<String> value=tableMap.get(key).getRecord();
                     
                     if(filter.equals("UN_AUTH")){
                     
                         if(value.get(authStatColID-1).trim().equals("U")){

                             primaryKeys.add(key.replace("~", "_"));
                             count++;
                         }
                     }else{
                         
                         if(value.get(makerColID-1).trim().equals(userID)&&value.get(authStatColID-1).trim().equals("U")){

                             primaryKeys.add(key.replace("~", "_"));
                             count++;
                         }
                         
                     }
                 }
                 
                 }
            }
               
               
                Pending pending=new Pending();               
                pending.setServiceName(serviceName);
                pending.setServiceType("Class");
                pending.setCount(Integer.toString(count));
                pending.setPrimaryKey(primaryKeys);
               
          dbg("end of getClassPendingDetail count"+count);
          return pending;
       }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        } 
        
    }
    
    private Pending getInstitutePendingDetail(String userID,String  serviceName,String tableName,HashSet<String> instituteList,String filter )throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
        
      try{  
           dbg("inside getinstituteRecords");
           dbg("filter"+filter);
           dbg("ServiceName"+serviceName);
           dbg("tableName"+tableName);
           BusinessService bs=inject.getBusinessService(session);
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IMetaDataService mds=inject.getMetadataservice();
           String instituteID=request.getReqHeader().getInstituteID();
           int makerColID;
           int authStatColID;
           if(serviceName.equals("GeneralLevelConfiguration")){
               makerColID=mds.getColumnMetaData("APP", tableName, "MAKER_ID", session).getI_ColumnID();
               authStatColID=mds.getColumnMetaData("APP", tableName, "AUTH_STATUS", session).getI_ColumnID();
               
           }else{
           
               makerColID=mds.getColumnMetaData("INSTITUTE", tableName, "MAKER_ID", session).getI_ColumnID();
               authStatColID=mds.getColumnMetaData("INSTITUTE", tableName, "AUTH_STATUS", session).getI_ColumnID();
           }
           
           ArrayList<String>primaryKeys=new ArrayList();
           int count=0;
           Map<String,DBRecord>tableMap=null;
    
            Iterator<String>instituteIterator=instituteList.iterator();
           
            while(instituteIterator.hasNext()){
                
                String l_instituteID=instituteIterator.next(); 
                
                try{
                
                 if(serviceName.equals("GeneralLevelConfiguration")){
                     
                     
                     tableMap=readBuffer.readTable("APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive","APP", tableName, session, dbSession,"true");
                     
                 }else{   
                    
                    
                 tableMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE", tableName, session, dbSession,"true");
                 
                 
                 }
                 }catch(DBValidationException ex){
                    
                    if(!ex.toString().contains("DB_VAL_011")){
                        throw ex;
                    }
                }
                 
                 
                 if(tableMap!=null){
                 
                 Iterator<String>keyIterator=tableMap.keySet().iterator();
                 
                 while(keyIterator.hasNext()){
                     
                     String key=keyIterator.next();
                     ArrayList<String> value=tableMap.get(key).getRecord();
                     
                     if(filter.equals("UN_AUTH")){
                     
                         if(value.get(authStatColID-1).trim().equals("U")){

                             primaryKeys.add(key.replace("~", "_"));
                             count++;
                         }
                     }else{
                         
                         if(value.get(makerColID-1).trim().equals(userID)&&value.get(authStatColID-1).trim().equals("U")){

                             primaryKeys.add(key.replace("~", "_"));
                             count++;
                         }
                         
                     }
                     
                 }
                 
                 }
            }
               
               
                Pending pending=new Pending();               
                pending.setServiceName(serviceName);
                pending.setServiceType("institute");
                pending.setCount(Integer.toString(count));
                pending.setPrimaryKey(primaryKeys);
               
          dbg("end of getinstituteRecords count"+count);
          return pending;
       }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        } 
        
    }
    
    private Pending getSTAttendanceCount(String userID,String serviceName,String serviceType,Map<String,DBRecord>tableMap,String filter )throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
        
      try{  
           dbg("inside getSTAttendanceCount");
           dbg("serviceName"+serviceName);
           dbg("filter"+filter);
           BusinessService bs=inject.getBusinessService(session);
           int count=0;
            Iterator<String>keyIterator=tableMap.keySet().iterator();
            ArrayList<String>primaryKeys=new ArrayList();
                 
                 while(keyIterator.hasNext()){
                     
                     String key=keyIterator.next();
                     dbg("key"+key);
                     ArrayList<String> value=tableMap.get(key).getRecord();
                     String monthAttendance=value.get(3).trim();
                     dbg("monthAttendance"+monthAttendance);
                     String[] dayArray=   monthAttendance.split("d");
                     for(int i=1;i<dayArray.length;i++){

                     String[] periodArray=  dayArray[i].split("p");
           
                        if(!periodArray[0].contains(" ")){
            
                            String day= periodArray[0];
                            dbg("day"+day);
                            String maxAttendance=bs.getMaxVersionAttendanceOftheDay(monthAttendance, day).get(day);
                            dbg("maxAttendance"+maxAttendance);
                            AuditDetails audit=bs.getAuditDetailsFromDayAttendance(maxAttendance);
                            
                            if(filter.equals("UN_AUTH")){
                                
                                if(audit.getAuthStatus().equals("U")){
                                
                                    primaryKeys.add(key.replace("~", "_")+"_"+day);
                                    count++;
                                }
                            }else{
                            
                                if(audit.getAuthStatus().equals("U")&&audit.getMakerID().equals(userID)){
                                     primaryKeys.add(key.replace("~", "_")+"_"+day);
                                    count++;
                                }
                            }
                        }
                     
                 }
           
           
               
                 }
               
                Pending pending=new Pending();               
                pending.setServiceName(serviceName);
                pending.setServiceType(serviceType);
                pending.setCount(Integer.toString(count));
                pending.setPrimaryKey(primaryKeys);
          dbg("end of getSTAttendanceCount "+count);
          return pending;
       }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        } 
        
    }
    
    private Pending getCAttendanceCount(String userID,String serviceName,String serviceType,Map<String,DBRecord>tableMap,String filter )throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
        
      try{  
           dbg("inside getinstituteRecords");
           BusinessService bs=inject.getBusinessService(session);
           int count=0;
            Iterator<String>keyIterator=tableMap.keySet().iterator();
            ArrayList<String>primaryKeys=new ArrayList();
                 
                 while(keyIterator.hasNext()){
                     
                     String key=keyIterator.next();
                     ArrayList<String> value=tableMap.get(key).getRecord();
                     String monthAttendance=value.get(4).trim();
                     String[] dayArray=   monthAttendance.split("d");
                     for(int i=1;i<dayArray.length;i++){

                     String[] periodArray=  dayArray[i].split("p");
           
                        if(!periodArray[0].contains(" ")){
            
                            String day= periodArray[0];
                            AuditDetails audit=bs.getClassAuditDetails(monthAttendance,day,session);
                            
                            if(filter.equals("UN_AUTH")){
                                
                                if(audit.getAuthStatus().equals("U")){
                                
                                     primaryKeys.add(key.replace("~", "_")+"_"+day);
                                    count++;
                                }
                            }else{
                            
                                if(audit.getAuthStatus().equals("U")&&audit.getMakerID().equals(userID)){
                                     primaryKeys.add(key.replace("~", "_")+"_"+day);
                                    count++;
                                }
                            }
                        }
                     
                 }
           
           
               
                 }
               
                Pending pending=new Pending();               
                pending.setServiceName(serviceName);
                pending.setServiceType(serviceType);
                pending.setCount(Integer.toString(count));
                pending.setPrimaryKey(primaryKeys);
          dbg("end of getinstituteRecords count"+count);
          return pending;
       }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        } 
        
    }
    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside teacher dash board buildJsonResFromBO");    
        RequestBody<ManagementDashBoard> reqBody = request.getReqBody();
        ManagementDashBoard managementDashBoard =reqBody.get();
        
        JsonArrayBuilder selfEntryArray=Json.createArrayBuilder();
        JsonArrayBuilder unAuthArray=Json.createArrayBuilder();       
        JsonArrayBuilder pkArray=Json.createArrayBuilder();
            
                
                    for(int i=0;i<managementDashBoard.entryPendingList.size();i++){
                        
                        for(int j=0;j<managementDashBoard.entryPendingList.get(i).getPrimaryKey().size();j++){
                            
                            pkArray.add(Json.createObjectBuilder().add("primaryKey", managementDashBoard.entryPendingList.get(i).getPrimaryKey().get(j)));
                            
                        }
                        
                        selfEntryArray.add(Json.createObjectBuilder().add("serviceType", managementDashBoard.entryPendingList.get(i).getServiceType())
                                                                     .add("serviceName", managementDashBoard.entryPendingList.get(i).getServiceName())
                                                                     .add("count", managementDashBoard.entryPendingList.get(i).getCount())
                                                                     .add("pkArray", pkArray));
                        
                    }
        
                    for(int i=0;i<managementDashBoard.unAuthPendingList.size();i++){
                        
                        for(int j=0;j<managementDashBoard.unAuthPendingList.get(i).getPrimaryKey().size();j++){
                            
                            pkArray.add(Json.createObjectBuilder().add("primaryKey", managementDashBoard.unAuthPendingList.get(i).getPrimaryKey().get(j)));
                            
                        }
                        
                        unAuthArray.add(Json.createObjectBuilder().add("serviceType", managementDashBoard.unAuthPendingList.get(i).getServiceType())
                                                                  .add("serviceName", managementDashBoard.unAuthPendingList.get(i).getServiceName())
                                                                  .add("count", managementDashBoard.unAuthPendingList.get(i).getCount())
                                                                  .add("pkArray", pkArray));
                        
                    }
                        
        
            body=Json.createObjectBuilder().add("userID", managementDashBoard.getUserID())
                                           .add("totalFeeAmount", managementDashBoard.getTotalFeeAmount())
                                           .add("totalPaidAmount", managementDashBoard.getTotalPaidAmount())
                                           .add("totalBalanceAmount", managementDashBoard.getTotalBalanceAmount())
                                           .add("selfEntryPending", selfEntryArray)
                                           .add("unAuthPending", unAuthArray)
                                           .build();
                                            
              dbg(body.toString());  
           dbg("end of teacher dash board buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside teacher assignment--->businessValidation");    
       if(!masterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!masterDataValidation(errhandler)){
               status=false;
            }
       }
       
       
       dbg("end of teacher assignment--->businessValidation"); 
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
    private boolean masterMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
      boolean status=true;
        try{
        dbg("inside teacher assignment master mandatory validation");
        RequestBody<ManagementDashBoard> reqBody = request.getReqBody();
        ManagementDashBoard managementDashBoard =reqBody.get();
        dbg("userID"+managementDashBoard.getUserID());
         if(managementDashBoard.getUserID()==null||managementDashBoard.getUserID().isEmpty()){
             status=false;
             errhandler.log_app_error("BS_VAL_002","User ID");
         }
         
          
          
        dbg("end of teacher assignment master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
      boolean status=true;
        try{
        dbg("inside  master data validation");
        RequestBody<ManagementDashBoard> reqBody = request.getReqBody();
        ManagementDashBoard managementDashBoard =reqBody.get();
        String userID=managementDashBoard.getUserID();
        String instituteID=request.getReqHeader().getInstituteID();
        BSValidation bsv=inject.getBsv(session);
        
        
        if(!bsv.userIDValidation(userID,errhandler,instituteID,inject,session,dbSession)){
           
             status=false;
             errhandler.log_app_error("BS_VAL_003","User ID");
        }
        
          
          
        dbg("end of master data validation");
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
