

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.groupmapping;

import com.ibd.businessViews.IGroupMappingService;
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
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.lock.ILockService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.HashMap;
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
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
//@Local(IGroupMappingService.class)
@Remote(IGroupMappingService.class)
@Stateless
public class GroupMappingService implements  IGroupMappingService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public GroupMappingService(){
        try {
            inject=new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession = new DBSession(session);
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
         if (session.getDebug()!=null)
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
    
    
    public JsonObject processing(JsonObject requestJson) throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException
    {
       boolean l_validation_status=true;
       boolean l_session_created_now=false;
       JsonObject jsonResponse=null;
       JsonObject clonedResponse=null;
       BusinessService  bs;
       Parsing parser;
       ExceptionHandler exHandler;
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
       dbg("inside GroupMappingService--->processing");
       dbg("GroupMappingService--->Processing--->I/P--->requestJson"+requestJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,requestJson);
       bs.requestlogging(request,requestJson, inject,session,dbSession);
       String l_operation=request.getReqHeader().getOperation();
       buildBOfromReq(requestJson);  
       
       if(!l_operation.equals("Create-Default")){
       
           RequestBody<GroupMapping> reqBody = request.getReqBody();
           GroupMapping groupMapping =reqBody.get();
           String l_groupID=groupMapping.getGroupID();
           l_lockKey=l_groupID;
           if(!businessLock.getBusinessLock(request, l_lockKey, session)){
               l_validation_status=false;
               throw new BSValidationException("BSValidationException");
            }
       
       }
       BusinessEJB<IGroupMappingService>groupMappingEJB=new BusinessEJB();
       groupMappingEJB.set(this);
       exAudit=bs.getExistingAudit(groupMappingEJB);
       
      if(!(bsv. businessServiceValidation(requestJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
      }
      
       if(!l_operation.equals("Create-Default")){
      
           if(!businessValidation(errhandler)){
               l_validation_status=false;
               throw new BSValidationException("BSValidationException");

           } 
       }
      
       bs.businessServiceProcssing(request, exAudit, inject,groupMappingEJB);
       

       
       if(l_operation.equals("Create-Default")){
           
           createDefault();
       }
         if(l_session_created_now){
             jsonResponse= bs.buildSuccessResponse(requestJson, request, inject, session,groupMappingEJB) ;
             tc.commit(session,dbSession);
             dbg("commit is called in  service");
        }
       dbg("GroupMappingService--->Processing--->O/P--->jsonResponse"+jsonResponse);     
       dbg("End of groupMappingService--->processing");     
       }catch(NamingException ex){
            dbg(ex);
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, requestJson,"BSProcessingException");
       }catch(BSValidationException ex){
          if(l_session_created_now){
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, requestJson,"BSValidationException");
          }else{
              throw ex;
          }
       }catch(DBValidationException ex){
           if(l_session_created_now){
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, requestJson,"DBValidationException");
          }else{
              throw ex;
          }
       }catch(DBProcessingException ex){
            dbg(ex);
            if(l_session_created_now){
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, requestJson,"DBProcessingException");
            }else{
              throw ex;
          }
       }catch(BSProcessingException ex){
           dbg(ex);
           if(l_session_created_now){
           exHandler = inject.getErrorHandle(session);
           jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, requestJson,"BSProcessingException");
          }else{
              throw ex;
          }
       }catch(Exception ex){
           dbg(ex);
           if(l_session_created_now){
           exHandler = inject.getErrorHandle(session);
           jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, requestJson,"BSProcessingException");
          }else{
              throw ex;
          }    
      }finally{
            exAudit=null;
            if(l_lockKey!=null){
               businessLock.removeBusinessLock(request, l_lockKey, session);
            }
            request=null;
            bs=inject.getBusinessService(session);
           if(l_session_created_now){
            bs.responselogging(jsonResponse, inject,session,dbSession);
            dbg("Response"+jsonResponse.toString());
            clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
            BSValidation bsv=inject.getBsv(session);
//            if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
          clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes  
//BSProcessingException ex=new BSProcessingException("response contains special characters");
  //              dbg(ex);
                
    //            session.clearSessionObject();
      //         dbSession.clearSessionObject();
        //       throw ex;
               
          //  }
            session.clearSessionObject();
            dbSession.clearSessionObject();
           }
        }
        
     return clonedResponse;    
    }
    
    
    @Override
    public JsonObject processing(JsonObject p_request,CohesiveSession session)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException
    {
    CohesiveSession tempSession = this.session;
    try{
        this.session=session;
       return processing(p_request);
     }catch(DBValidationException ex){
         //dbg(ex);        
        throw ex;
        }catch(BSValidationException ex){
         //dbg(ex);        
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
 }

    private  void buildBOfromReq(JsonObject p_request)throws BSProcessingException,DBProcessingException,BSValidationException{
       
       try{
       dbg("inside GroupMappingService--->buildBOfromReq"); 
       dbg("GroupMappingService--->buildBOfromReq--->I/P--->p_request"+p_request.toString()); 
       BSValidation bsv=inject.getBsv(session);
       RequestBody<GroupMapping> reqBody = new RequestBody<GroupMapping>(); 
       GroupMapping groupMapping = new GroupMapping();
       String l_operation=request.getReqHeader().getOperation();
       
       if(!l_operation.equals("Create-Default")){
       
       JsonObject l_body=p_request.getJsonObject("body");
       groupMapping.setInstituteID(l_body.getString("instituteID"));
       groupMapping.setInstituteName(l_body.getString("instituteName"));
       groupMapping.setGroupID(l_body.getString("groupID"));
       
       if(!(l_operation.equals("View"))){
            
           groupMapping.setGroupDescription(l_body.getString("groupDescription"));
            JsonArray groupArray= l_body.getJsonArray("group");
            groupMapping.group=new Group[groupArray.size()];
            
            for(int i=0;i<groupArray.size();i++){
                
                JsonObject groupObject=groupArray.getJsonObject(i);
                groupMapping.group[i]=new Group();
//                groupMapping.group[i].setStandard(groupObject.getString("standard"));
//                groupMapping.group[i].setSection(groupObject.getString("section"));

                if(groupObject.getString("class").equals("Select option")){
                    
                  groupMapping.group[i].setStandard("dum");
                  groupMapping.group[i].setSection("dum");
                  
              }else{

                  String l_class=groupObject.getString("class");
                  bsv.classValidation(l_class,session);
                  groupMapping.group[i].setStandard(l_class.split("/")[0]);
                  groupMapping.group[i].setSection(l_class.split("/")[1]);

              }

//                String l_class=groupObject.getString("class");   
//                bsv.classValidation(l_class,session);
//                

//                
//                
//                groupMapping.group[i].setStandard(l_class.split("/")[0]);
//                groupMapping.group[i].setSection(l_class.split("/")[1]);

                if(!groupObject.getString("studentID").isEmpty()){

                   groupMapping.group[i].setStudentID(groupObject.getString("studentID"));
                
                }else{
                    
                    groupMapping.group[i].setStudentID("dum");
                }
            }
           
        }
       
       }
       reqBody.set(groupMapping);
      request.setReqBody(reqBody);
      dbg("End of GroupMappingService--->buildBOfromReq");
      }catch(BSValidationException ex){
         throw ex;
      }catch(Exception ex){
          dbg(ex);
          throw new BSProcessingException("BodyParsingException"+ex.toString());
        }
   }
    
  
    private void createDefault()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside teacher attendance--->createdefault");    
        ILockService lock=inject.getLockService();
        String sequenceNo="G"+lock.getSequenceNo();
            
        RequestBody<GroupMapping> reqBody = request.getReqBody();
        GroupMapping groupMapping =reqBody.get();
        
        groupMapping.setGroupID(sequenceNo);
        
          dbg("end of  completed teacher attendance--->createdefault");                
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
     public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
        try{
        dbg("Inside GroupMappingService--->create"); 
        RequestBody<GroupMapping> reqBody = request.getReqBody();
        GroupMapping groupMapping =reqBody.get();
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
       
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        
        String l_instituteID=groupMapping.getInstituteID();
        String l_groupID=groupMapping.getGroupID();
        String l_groupDescription=groupMapping.getGroupDescription();
        
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",89,l_instituteID,l_groupID,l_groupDescription,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks);
       
        for(int i=0;i<groupMapping.group.length;i++){
            
            String l_standard=groupMapping.group[i].getStandard();
            String l_section=groupMapping.group[i].getSection();
            String l_studentID=groupMapping.group[i].getStudentID();
            
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",90,l_instituteID,l_groupID,l_standard,l_section,l_studentID,l_versionNumber);
        }
       

        dbg("End of GroupMappingService--->create");
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception".concat(ex.toString()));
        }
    }

      public void authUpdate()throws DBValidationException,DBProcessingException,BSProcessingException{
        
        try{ 
            dbg("Inside GroupMappingService--->update"); 
            IDBTransactionService dbts=inject.getDBTransactionService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<GroupMapping> reqBody = request.getReqBody();
            GroupMapping groupMapping =reqBody.get();
            String l_authStatus=request.getReqAudit().getAuthStatus();
            String l_checkerID=request.getReqAudit().getCheckerID();
            String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
            String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
            String l_instituteID=groupMapping.getInstituteID(); 
            String l_groupMappingID=groupMapping.getGroupID();
            Map<String,String>  l_column_to_update=new HashMap();
            l_column_to_update.put("5", l_checkerID);
            l_column_to_update.put("7", l_checkerDateStamp);
            l_column_to_update.put("9", l_authStatus);
            l_column_to_update.put("12", l_checkerRemarks);
            
             
             String[] l_primaryKey={l_groupMappingID};
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_GROUP_MAPPING_MASTER", l_primaryKey, l_column_to_update, session);

             dbg("End of GroupMappingService--->update");          
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
             throw new DBProcessingException(ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
        
       }

     
     public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
        
       Map<String,String> l_column_to_update;
                
        try{ 
        dbg("Inside GroupMappingService--->fullUpdate");
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<GroupMapping> reqBody = request.getReqBody();
        IPDataService pds=inject.getPdataservice();
        GroupMapping groupMapping =reqBody.get();
        String l_instituteID=groupMapping.getInstituteID(); 
        String l_groupID=groupMapping.getGroupID();
        String l_groupDescription=groupMapping.getGroupDescription();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String versionNumber=request.getReqAudit().getVersionNumber();
        l_column_to_update= new HashMap();
        l_column_to_update.put("3", l_groupDescription);
        
        String[] l_PKey={l_groupID};
            
            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_GROUP_MAPPING_MASTER",l_PKey,l_column_to_update,session);
        
       
        Map<String,ArrayList<String>>l_detailMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_GROUP_MAPPING_DETAIL",session,dbSession);
        setOperationsOfTheRecord("IVW_GROUP_MAPPING_DETAIL",l_detailMap);    
        
        for(int i=0;i<groupMapping.group.length;i++){
            
            String l_standard=groupMapping.group[i].getStandard();
            String l_section=groupMapping.group[i].getSection();
            String l_studentID=groupMapping.group[i].getStudentID(); 
            
            if(groupMapping.group[i].getOperation().equals("U")){
        
                l_column_to_update= new HashMap();
                l_column_to_update.put("3", l_standard);
                l_column_to_update.put("4", l_section);
                l_column_to_update.put("5", l_studentID);
//                l_column_to_update.put("3", l_studentID);
//                l_column_to_update.put("3", l_studentID);
               

                String[] l_detailPrimaryKey={l_groupID,l_standard,l_section,l_studentID};
                            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_GROUP_MAPPING_DETAIL", l_detailPrimaryKey, l_column_to_update, session);
         
            }else{
                
                 dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",90,l_instituteID,l_groupID,l_standard,l_section,l_studentID,versionNumber);
                
            }
            }
        
        ArrayList<String>subjectList=getRecordsToDelete("IVW_GROUP_MAPPING_DETAIL",l_detailMap);
        
        for(int i=0;i<subjectList.size();i++){
            String pkey=subjectList.get(i);
            deleteRecordsInTheList("IVW_GROUP_MAPPING_DETAIL",pkey);
            
         }
        
                   
        dbg("end of GroupMappingService--->fullUpdate");                
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
             throw new DBProcessingException(ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
        
       }
     
     private void setOperationsOfTheRecord(String tableName,Map<String,ArrayList<String>>tableMap)throws DBValidationException,DBProcessingException,BSProcessingException{
         
         try{
            dbg("inside getOperationsOfTheRecord"); 
            RequestBody<GroupMapping> reqBody = request.getReqBody();
            GroupMapping groupMapping =reqBody.get();
            String l_groupID=groupMapping.getGroupID();
            dbg("tableName"+tableName);
            
//            tableMap.forEach((String k1,DBRecord v1)->{
//              dbg("key"+k1);
//              v1.getRecord().forEach((String l)->{
//               dbg("values"+l);
//              });
//           });
            
            
            
            
            
            switch(tableName){
                
                case "IVW_GROUP_MAPPING_DETAIL":  
                
                         for(int i=0;i<groupMapping.group.length;i++){
                                String l_standard=groupMapping.group[i].getStandard();
                                String l_section=groupMapping.group[i].getSection();
                                String l_studentID=groupMapping.group[i].getStudentID();
                                String l_pkey=l_groupID+"~"+l_standard+"~"+l_section+"~"+l_studentID;
                               if(tableMap.containsKey(l_pkey)){

                                    groupMapping.group[i].setOperation("U");
                                }else{

                                    groupMapping.group[i].setOperation("C");
                                }
                         } 
                  break;      
                  
                  
                  
                
                   
                
            }
            
                       

            dbg("end of getOperationsOfTheRecord"); 
//         }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//             throw new DBProcessingException(ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
         
     }
     
     
     private ArrayList<String>getRecordsToDelete(String tableName,Map<String,ArrayList<String>>tableMap)throws DBValidationException,DBProcessingException,BSProcessingException{
         
         try{
           
           dbg("inside getRecordsToDelete");  
           RequestBody<GroupMapping> reqBody = request.getReqBody();
           GroupMapping groupMapping =reqBody.get();
           String l_groupID=groupMapping.getGroupID();
           ArrayList<String>recordsToDelete=new ArrayList();
//           Iterator<String>keyIterator=tableMap.keySet().iterator();
           
           List<ArrayList<String>>filteredRecords=tableMap.values().stream().filter(rec->rec.get(1).trim().equals(l_groupID)).collect(Collectors.toList());
           
           
           dbg("tableName"+tableName);
           switch(tableName){
           
                 case "IVW_GROUP_MAPPING_DETAIL":   
                   
//                   while(keyIterator.hasNext()){
                     for(int j=0;j<filteredRecords.size();j++){
                        String standard=filteredRecords.get(j).get(2).trim();
                        String section=filteredRecords.get(j).get(3).trim();
                        String studentID=filteredRecords.get(j).get(4).trim();
                        String tablePkey=l_groupID+"~"+standard+"~"+section+"~"+studentID;            
                        
                        dbg("tablePkey"+tablePkey);
                        boolean recordExistence=false;

                        for(int i=0;i<groupMapping.group.length;i++){
                            String l_standard=groupMapping.group[i].getStandard();
                            String l_section=groupMapping.group[i].getSection();
                            String l_studentID=groupMapping.group[i].getStudentID();
                            String l_requestPkey=l_groupID+"~"+l_standard+"~"+l_section+"~"+l_studentID;
                           dbg("l_requestPkey"+l_requestPkey);
                           if(tablePkey.equals(l_requestPkey)){
                               recordExistence=true;
                           }
                        }   
                        if(!recordExistence){
                            recordsToDelete.add(tablePkey);
                        }

                    }
                   break;
                   
                       
                      
                    }
             
           dbg("records to delete"+recordsToDelete.size());
           dbg("end of getRecordsToDelete");
           return recordsToDelete;
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//             throw new DBProcessingException(ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
     }
     
     private void deleteRecordsInTheList(String tableName,String pkey)throws DBValidationException,DBProcessingException,BSProcessingException{
         
         try{
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IDBTransactionService dbts=inject.getDBTransactionService();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String[] pkArr=pkey.split("~");
             
             
             dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE",tableName, pkArr, session); 
             
         }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
             throw new DBProcessingException(ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
         
     }
     
     
     
     public void delete()throws DBValidationException,DBProcessingException,BSProcessingException{
        
        try{ 
        dbg("Inside GroupMappingService--->delete");  
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<GroupMapping> reqBody = request.getReqBody();
        GroupMapping groupMapping =reqBody.get();
        String l_instituteID=groupMapping.getInstituteID();   
        String l_groupMappingID=groupMapping.getGroupID();

        String[] l_primaryKey={l_groupMappingID};
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_GROUP_MAPPING_MASTER", l_primaryKey, session);
         
        for(int i=0;i<groupMapping.group.length;i++){
            
            String l_standard=groupMapping.group[i].getStandard();
            String l_section=groupMapping.group[i].getSection();
            String l_studentID=groupMapping.group[i].getStudentID();
            String[] l_pkey={l_groupMappingID,l_standard,l_section,l_studentID};
            
                         dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_GROUP_MAPPING_DETAIL", l_pkey, session);
        }        
 
         dbg("End of GroupMappingService--->delete");      
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
             throw new DBProcessingException(ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
        
       }
     public void  view()throws DBValidationException,DBProcessingException,BSProcessingException{
        
                
        try{
            dbg("Inside GroupMappingService--->tableRead");
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IPDataService pds=inject.getPdataservice();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<GroupMapping> reqBody = request.getReqBody();
            GroupMapping groupMapping =reqBody.get();
            String l_instituteID=groupMapping.getInstituteID();
            String l_groupMappingID=groupMapping.getGroupID();
            String[] l_pkey={l_groupMappingID};
            ArrayList<String> masterRecord=null;
            Map<String,ArrayList<String>>l_detailMap=null;
            
            try{
            
            masterRecord=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_GROUP_MAPPING_MASTER", l_pkey );
            l_detailMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_GROUP_MAPPING_DETAIL", session, dbSession);
       
            
            }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", l_groupMappingID);
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
                }
            
            
            buildBOfromDB(masterRecord,l_detailMap);
           
            
            
         
          dbg("end of  GroupMappingService--->tableRead");               
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
}
     
      private void buildBOfromDB( ArrayList<String> p_masterRecord,Map<String,ArrayList<String>>p_detailMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
            dbg("inside GroupMappingService--->buildBOfromDB");
            
            ArrayList<String>l_groupMappingList=p_masterRecord;
            RequestBody<GroupMapping> reqBody = request.getReqBody();
            GroupMapping groupMapping =reqBody.get();
            String l_instituteID=groupMapping.getInstituteID();
            BusinessService bs=inject.getBusinessService(session);
            String l_group_ID=groupMapping.getGroupID();
            if(l_groupMappingList!=null&&!l_groupMappingList.isEmpty()){
           
               String instituteName=bs.getInstituteName(l_instituteID, session, dbSession, inject);
               groupMapping.setInstituteName(instituteName);
               groupMapping.setGroupDescription(l_groupMappingList.get(2).trim());   
               request.getReqAudit().setMakerID(l_groupMappingList.get(3).trim());
               request.getReqAudit().setCheckerID(l_groupMappingList.get(4).trim());
               request.getReqAudit().setMakerDateStamp(l_groupMappingList.get(5).trim());
               request.getReqAudit().setCheckerDateStamp(l_groupMappingList.get(6).trim());
               request.getReqAudit().setRecordStatus(l_groupMappingList.get(7).trim());
               request.getReqAudit().setAuthStatus(l_groupMappingList.get(8).trim());
               request.getReqAudit().setVersionNumber(l_groupMappingList.get(9).trim());
               request.getReqAudit().setMakerRemarks(l_groupMappingList.get(10).trim());
               request.getReqAudit().setCheckerRemarks(l_groupMappingList.get(11).trim());
            }
            
            List<ArrayList<String>> groupWiseList=p_detailMap.values().stream().filter(rec->rec.get(1).trim().equals(l_group_ID)&&rec.get(5).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.toList());
            groupMapping.group=new Group[groupWiseList.size()];
//            int i=0;
                for(int i=0;i<groupWiseList.size();i++){
                   ArrayList<String>  l_scheduleRecords=groupWiseList.get(i);
                   groupMapping.group[i]=new Group();
                   
                   String standard=l_scheduleRecords.get(2).trim();
                   String section=l_scheduleRecords.get(3).trim();
                   String studentID=l_scheduleRecords.get(4).trim();
                   
                   if(!standard.equals("dum")){
                   
                     groupMapping.group[i].setStandard(standard);
                   
                   }else{
                       
                       groupMapping.group[i].setStandard("");
                   }
                   
                   if(!section.equals("dum")){
                   
                     groupMapping.group[i].setSection(section);
                   
                   }else{
                       
                       groupMapping.group[i].setSection("");
                   }
                   
                   if(!studentID.equals("dum")){
                   
                     groupMapping.group[i].setStudentID(studentID);
                   
                   }else{
                       
                       groupMapping.group[i].setStudentID("");
                   }
                   
//                   groupMapping.group[i].setSection(l_scheduleRecords.getRecord().get(3).trim());
//                   groupMapping.group[i].setStudentID(l_scheduleRecords.getRecord().get(4).trim());
                   String studentName;
                   if(!studentID.equals("dum")){
                   
                      studentName=bs.getStudentName(groupMapping.group[i].getStudentID(), l_instituteID, session, dbSession, inject);
                   
                   }else{
                       
                       studentName="";
                   }
                   
                   groupMapping.group[i].setStudentNAme(studentName);
                   
//                   i++;
                }
          
       
        dbg("End of GroupMappingService--->buildBOfromDB");
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }
        
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
 } 
     
     
     
     
     public void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
         
         return;
         
     }
     
     public JsonObject buildJsonResFromBO()throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
         JsonObject body=null;
         try{
             dbg("inside buildJsonResFromBO");
            RequestBody<GroupMapping> reqBody = request.getReqBody();
            GroupMapping groupMapping =reqBody.get();
            JsonArrayBuilder groupArray=Json.createArrayBuilder();
            String operation=request.getReqHeader().getOperation();
            
            if(operation.equals("Create-Default")){
                
                body=Json.createObjectBuilder().add("groupID", groupMapping.getGroupID()).build();
                
                
                
            }else{
            
            for(int i=0;i<groupMapping.group.length;i++){
                
                String standard=groupMapping.group[i].getStandard();
                String section=groupMapping.group[i].getSection();
                String classs;
                if(standard.isEmpty()&&section.isEmpty()){
                    
                    classs="Select option";
                }else{
                    classs=groupMapping.group[i].getStandard()+"/"+groupMapping.group[i].getSection();
                }
                
                
                           groupArray.add(Json.createObjectBuilder().add("class", classs)
                                                                    .add("studentName", groupMapping.group[i].getStudentNAme())
                                                                    .add("studentID", groupMapping.group[i].getStudentID()));
                           
                           
            }
            
            
             body=Json.createObjectBuilder().add("instituteID", groupMapping.getInstituteID())
                                            .add("instituteName", groupMapping.getInstituteName())
                                            .add("groupID", groupMapping.getGroupID())
                                            .add("groupDescription", groupMapping.getGroupDescription())
                                            .add("group", groupArray)
                                            .build(); 
            }
            dbg("end of buildJsonResFromBO");
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
         
         return body;
         
     }
     
     private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
       try{
       dbg("inside GroupMappingService--->businessValidation");    
       String l_operation=request.getReqHeader().getOperation();
                     
       if(!masterMandatoryValidation(errhandler)){
           status=false;
       }else{
           if(!masterDataValidation(errhandler)){
              status=false;
           }
       }
       if(!(l_operation.equals("View"))&&!l_operation.equals("Create-Default")&&!l_operation.equals("Delete")){
           
           if(!detailMandatoryValidation(errhandler)) {
               
               status=false;
           } else{
           if(!detailDataValidation(errhandler)){
               
               status=false;
           }
           
           }
       
       }

       dbg("GroupMappingService--->businessValidation--->O/P--->status"+status);
       dbg("End of GroupMappingService--->businessValidation");
       }catch(BSProcessingException ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
            
       }catch(BSValidationException ex){
           throw ex;
           
       }catch(DBValidationException ex){
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
        dbg("inside GroupMappingService--->masterMandatoryValidation");
        RequestBody<GroupMapping> reqBody = request.getReqBody();
        GroupMapping groupMapping =reqBody.get();
        
         
         if(groupMapping.getInstituteID()==null||groupMapping.getInstituteID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","InstituteID");
         }
         if(groupMapping.getGroupID()==null||groupMapping.getGroupID().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","GroupID");
         }
         
        
         
         
         dbg("GroupMappingService--->masterMandatoryValidation--->O/P--->status"+status);
         dbg("End of GroupMappingService--->masterMandatoryValidation");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
            
        
            }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
     try{
        dbg("inside ClassExamScheduleService--->masterDataValidation");   
        BSValidation bsv=inject.getBsv(session);
        RequestBody<GroupMapping> reqBody = request.getReqBody();
        GroupMapping groupMapping =reqBody.get();
        String l_instituteID= groupMapping.getInstituteID();
        ErrorHandler errHandler=session.getErrorhandler();
             
        if(!bsv.instituteIDValidation( l_instituteID,errHandler,inject, session, dbSession)){
            status=false;
            errhandler.log_app_error("BS_VAL_003","InstituteID");
        }
        

        dbg("ClassExamScheduleServiceService--->masterDataValidation--->O/P--->status"+status);
        dbg("End of ClassExamScheduleServiceService--->masterDataValidation");  
       }catch(DBValidationException ex){
            throw ex;
       }catch(DBProcessingException ex){
            throw new DBProcessingException("DBProcessingException"+ex.toString());
       }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        
        return status;
              
    }
    
     private boolean detailMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
        boolean status=true;
         try{
            dbg("inside GroupMappingService--->detailMandatoryValidation");
            RequestBody<GroupMapping> reqBody = request.getReqBody();
            GroupMapping groupMapping =reqBody.get();
           
            if(groupMapping.getGroupDescription()==null||groupMapping.getGroupDescription().isEmpty()) {   
            status=false;
            errhandler.log_app_error("BS_VAL_002","GroupDescription");
         }
            
            if(groupMapping.getGroup()==null||groupMapping.getGroup().length==0){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","Group");  
            }else{
                
                for(int i=0;i<groupMapping.group.length;i++){
                    
                    int nullCount=0;
                    dbg("studentID-->"+groupMapping.getGroup()[i].getStudentID());
                    dbg("standard-->"+groupMapping.getGroup()[i].getStandard());
                    dbg("section-->"+groupMapping.getGroup()[i].getSection());
                    
                    if(groupMapping.getGroup()[i].getStandard().equals("dum")){
//                       status=false;  
//                       errhandler.log_app_error("BS_VAL_002","Standard");
                         nullCount++;
                    }
                    if(groupMapping.getGroup()[i].getSection().equals("dum")){
//                       status=false;  
//                       errhandler.log_app_error("BS_VAL_002","Section");  
                         nullCount++;
                    }
                    if(groupMapping.getGroup()[i].getStudentID().equals("dum")){
//                       status=false;  
//                       errhandler.log_app_error("BS_VAL_002","StudentID");  
                         nullCount++;
                    }
                    
                    if(nullCount==0||nullCount==3){
                         status=false;  
                        errhandler.log_app_error("BS_VAL_002","Either student or class"); 
                    }
                    
                }
 
            }
       
        
        dbg("GroupMappingService--->detailMandatoryValidation--->O/P--->status"+status);
        dbg("End of GroupMappingService--->detailMandatoryValidation");
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
  
   private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
     try{
        dbg("inside GroupMappingService--->detailDataValidation");   
        BSValidation bsv=inject.getBsv(session);
        RequestBody<GroupMapping> reqBody = request.getReqBody();
        GroupMapping groupMapping =reqBody.get();
        String l_instituteID=groupMapping.getInstituteID();
        ArrayList<String>groupMappingList=new ArrayList();
        
        for(int i=0;i<groupMapping.group.length;i++){
            
            String l_standard=groupMapping.group[i].getStandard();
            String l_section=groupMapping.group[i].getSection();
            String l_studentID=groupMapping.group[i].getStudentID();
            
            if(!groupMappingList.contains(l_standard+l_section+l_studentID)){
                     
                     groupMappingList.add(l_standard+l_section+l_studentID);
                 }else{
                      status=false;
                      errhandler.log_app_error("BS_VAL_031","Grade");
                      throw new BSValidationException("BSValidationException");
                 }
            if(!l_standard.equals("dum")&&!l_section.equals("dum")){
            
                if(!bsv.standardSectionValidation(l_standard,l_section, l_instituteID, session, dbSession, inject)){
                    status=false;
                    errhandler.log_app_error("BS_VAL_003","Class");
                }
            
            }
            
            if(!l_studentID.equals("dum")){
            
                if(!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","StudentID");
                }
            
            }
            
        }
        
        dbg("GroupMappingService--->detailDataValidation--->O/P--->status"+status);
        dbg("End of GroupMappingService--->detailDataValidation");  
        }catch(BSValidationException ex){
            throw ex;
       }catch(DBValidationException ex){
            throw ex;
       }catch(DBProcessingException ex){
            throw new DBProcessingException("DBProcessingException"+ex.toString());
       }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        
        return status;
              
    }
  
  public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     ExistingAudit exAudit=new ExistingAudit();
     try{
        dbg("inside BusinessService--->getExistingAudit") ;
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IPDataService pds=inject.getPdataservice();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_operation=request.getReqHeader().getOperation();
        String l_service=request.getReqHeader().getService();
        RequestBody<GroupMapping> groupMappingBody = request.getReqBody();
        GroupMapping groupMapping =groupMappingBody.get();
        String l_instituteID=groupMapping.getInstituteID();
        String l_versioNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
         if(!(l_operation.equals("Create")||l_operation.equals("Create-Default")||l_operation.equals("View"))){
             
            if(l_operation.equals("AutoAuth")&&l_versioNumber.equals("1")){
                
                return null;
            }else{  
                
               dbg("inside business Service--->getExistingAudit--->Service--->GroupMapping");  
               
               String l_groupMappingID=groupMapping.getGroupID();
               String[] l_pkey={l_groupMappingID};
            
               ArrayList<String> instituteRecord=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_GROUP_MAPPING_MASTER", l_pkey);

               exAudit.setAuthStatus(instituteRecord.get(8).trim());
               exAudit.setMakerID(instituteRecord.get(3).trim());
               exAudit.setRecordStatus(instituteRecord.get(7).trim());
               exAudit.setVersionNumber(Integer.parseInt(instituteRecord.get(9).trim()));
            

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
            }
        
        }else{
            return null;
        } 
    }catch(DBValidationException ex){
      throw ex;
     
     }catch(DBProcessingException ex){   
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
     return exAudit;
 }
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
}
