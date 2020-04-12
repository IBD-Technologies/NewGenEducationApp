/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.app.FileUploadService;

import com.ibd.businessViews.IFileUploadService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.ConvertedDate;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.message.request.Parsing;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.IOException;
import java.util.Calendar;
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
 * @author IBD Technologies
 */
@Remote(IFileUploadService.class)
@Stateless

public class FileUploadService implements IFileUploadService {

     AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
   
  public FileUploadService(){
        try {
            inject=new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession= new DBSession(session);
        } catch (NamingException ex) {
            dbg(ex);
            throw new EJBException(ex);
        }
        
    }
    
  private boolean validation(String token,String useriD,String ServiceName,String Institute)
  {
      if (token==null||token.isEmpty() )
        
            return false;
      else if(useriD==null || useriD.isEmpty()  )
           return false;  
      else if( ServiceName==null||ServiceName.isEmpty() )
           return false;  
      else if(Institute==null ||Institute.isEmpty()  )
           return false;  
      /*else if(bytes.length==0 ||bytes==null )
           return false; */ 
 
       
      
      
      return true;     
  }    
  
  private boolean fileExtnValidation(String fileName,String ServiceName)
  {
      int lastIndex = fileName.lastIndexOf('.');
    if (lastIndex == -1) {
        return false;
    }
    
    String extn=fileName.substring(lastIndex + 1);
    
    if (ServiceName.equals("GeneralLevelConfiguration"))
    {
        if(!(extn.equals("jpeg") ||extn.equals("png")||extn.equals("jpg")||extn.equals("bmp")||extn.equals("gif")||extn.endsWith("tiff")))
             return false;
        
     }
    
    if (ServiceName.contains("Report"))
    {
        if(!(extn.equals("pdf")))
             return false;
        
     }
    
    return true;  
  }
  
  @Override  
    
  public String processing(String token,String useriD,String ServiceName,String Institute,String fileName)throws IOException,BSProcessingException,BSValidationException,DBValidationException,DBProcessingException 
    {
            
  //FileChannel fc = null;
    
    try
    {
        session.createSessionObject();
        
        dbg("inside processing ");
        dbg("token-->"+token);
        dbg("useriD-->"+useriD);
        dbg("ServiceName-->"+ServiceName);
        dbg("Institute-->"+Institute);
        
    if (!validation(token,useriD,ServiceName,Institute))
        return "BS-VAL-102~Invalid Inputs";        
  
    if (!fileExtnValidation(fileName,ServiceName))
        return "BS-VAL-103~Invalid File Extension";        
  
    
    BSValidation bsval= inject.getBsv(session);
    if(!bsval.ResourceTokenValidation(token,useriD,Institute,ServiceName,session.getErrorhandler(),inject,session)){

               return "BS-VAL-101~Invalid Session Please retry";  
           }
    
    
       request = new Request();
       
  //String folderDelimiter=session.getCohesiveproperties().getProperty("FOLDER_DELIMITER");
  //String uploadPath="APP"+folderDelimiter+"upload" +folderDelimiter+session.getI_session_identifier().toString()+folderDelimiter+fileName;    
  //dbg("uploadPath -->"+uploadPath);
  /*Path filePath = Paths.get(session.getCohesiveproperties().getProperty("DATABASE_HOME_PATH") + uploadPath);
                
  fc = FileChannel.open(filePath, CREATE, WRITE);
  
  //using PosixFilePermission to set file permissions 755
       Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
       //add owners permission
       perms.add(PosixFilePermission.OWNER_READ);
       perms.add(PosixFilePermission.OWNER_WRITE);
       //perms.add(PosixFilePermission.OWNER_EXECUTE);
       //add group permissions
       perms.add(PosixFilePermission.GROUP_READ);
       //perms.add(PosixFilePermission.GROUP_EXECUTE);
       //add others permissions
       perms.add(PosixFilePermission.OTHERS_READ);
       //perms.add(PosixFilePermission.OTHERS_EXECUTE);
       
       Files.setPosixFilePermissions(filePath, perms);
  
  
  fc.write(ByteBuffer.wrap(bytes));
*/
  dbg("Successfully uploaded");
  
return "success";
}
 catch (Exception e) {

    dbg(e);
    throw new RuntimeException(e); 
//e.printStackTrace();
 }
finally{
        
     /*if (fc !=null ||fc.isOpen())
      fc.close();   
        }*/
 session.clearSessionObject();
 }
    
    }    
  
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
    
public JsonObject processing(JsonObject requestJson)throws IOException,BSProcessingException,BSValidationException,DBValidationException,DBProcessingException 
    {
            
  //FileChannel fc = null;
    BusinessService  bs;
    try
    {
        session.createSessionObject();
        dbSession.createDBsession(session);
        dbg("inside processing ");
        bs=inject.getBusinessService(session);
        BSValidation bsv=inject.getBsv(session);
        
        request = new Request();
        Parsing parser=inject.getParser(session);
        parser.parseRequest(request,requestJson);
        bs.requestlogging(request,requestJson, inject,session,dbSession);
        
        String token=request.getReqHeader().getToken();
        String useriD=request.getReqHeader().getUserID();
        String ServiceName=request.getReqHeader().getService();
        String Institute=request.getReqHeader().getInstituteID();
        
        dbg("token-->"+token);
        dbg("useriD-->"+useriD);
        dbg("ServiceName-->"+ServiceName);
        dbg("Institute-->"+Institute);
        
    if (!validation(token,useriD,ServiceName,Institute)){
        
        JsonObject response=errorResponseBuilding("BS-VAL-102~Invalid Inputs",requestJson,request);
        bs.responselogging(response, inject, session, dbSession);
       return response  ;  
       
    }
    
    
    if(ServiceName.contains("Report")){
    
        if(!bsv.userAccessValidation(request, session, dbSession, inject)){

            JsonObject response=errorResponseBuilding("BS_VAL_014~You don't have access",requestJson,request);
            bs.responselogging(response, inject, session, dbSession);
           return response  ;  

        }

        try{

        if(!this.businessValidation(request,requestJson)){

             JsonObject response=errorResponseBuilding("BS-VAL-102~Invalid Inputs",requestJson,request);
             bs.responselogging(response, inject, session, dbSession);
             return response  ;  
        }
        
        }catch(BSValidationException ex){
            
            JsonObject response=errorResponseBuilding(ex.toString(),requestJson,request);
             bs.responselogging(response, inject, session, dbSession);
             return response  ;
            
        }
    
    }
  
//    if (!fileExtnValidation(fileName,ServiceName))
//        
//        return errorResponseBuilding("BS-VAL-103~Invalid File Extension",requestJson)  ;  
    
    BSValidation bsval= inject.getBsv(session);
    if(!bsval.ResourceTokenValidation(token,useriD,Institute,ServiceName,session.getErrorhandler(),inject,session)){

        JsonObject response=errorResponseBuilding("BS-VAL-101~Invalid Session Please retry",requestJson,request)  ; 
        bs.responselogging(response, inject, session, dbSession);
       return response  ;  
//               return "BS-VAL-101~Invalid Session Please retry";  
           }
    
    
    
    
       
  //String folderDelimiter=session.getCohesiveproperties().getProperty("FOLDER_DELIMITER");
  //String uploadPath="APP"+folderDelimiter+"upload" +folderDelimiter+session.getI_session_identifier().toString()+folderDelimiter+fileName;    
  //dbg("uploadPath -->"+uploadPath);
  /*Path filePath = Paths.get(session.getCohesiveproperties().getProperty("DATABASE_HOME_PATH") + uploadPath);
                
  fc = FileChannel.open(filePath, CREATE, WRITE);
  
  //using PosixFilePermission to set file permissions 755
       Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
       //add owners permission
       perms.add(PosixFilePermission.OWNER_READ);
       perms.add(PosixFilePermission.OWNER_WRITE);
       //perms.add(PosixFilePermission.OWNER_EXECUTE);
       //add group permissions
       perms.add(PosixFilePermission.GROUP_READ);
       //perms.add(PosixFilePermission.GROUP_EXECUTE);
       //add others permissions
       perms.add(PosixFilePermission.OTHERS_READ);
       //perms.add(PosixFilePermission.OTHERS_EXECUTE);
       
       Files.setPosixFilePermissions(filePath, perms);
  
  
  fc.write(ByteBuffer.wrap(bytes));
*/
  
  
  dbg("Successfully uploaded");
  JsonObject response=successResponseBuilding(requestJson,request);
bs.responselogging(response, inject, session, dbSession);
       return response  ;
}
 catch (Exception e) {

    dbg(e);
    throw new RuntimeException(e); 
//e.printStackTrace();
 }
finally{
        
     /*if (fc !=null ||fc.isOpen())
      fc.close();   
        }*/
 session.clearSessionObject();
 }
    
    }  


private boolean businessValidation(Request request,JsonObject requestJson)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
    boolean status=true;
    try{
    
        dbg("inside FileUploadService--->businessValidation");
        String l_operation=request.getReqHeader().getOperation();
        dbg("operation is"+l_operation);
        String l_service=request.getReqHeader().getService();
        dbg("Service"+l_service);
        String l_userID=request.getReqHeader().getUserID();
        String l_instituteID=request.getReqHeader().getInstituteID();
        ErrorHandler errhandler=session.getErrorhandler();
        BSValidation bsv=inject.getBsv(session);
        BusinessService bs=inject.getBusinessService(session);
        
        
      if(!bsv.operationValidation(l_operation, errhandler,l_service)){
           
           status=false;
                     
       }
       if(!bsv.serviceValidation(l_service,errhandler,inject,session,dbSession)){
           
           status=false;
       }
       if(!bsv.userIDValidation(l_userID,errhandler,l_instituteID,inject,session,dbSession)){
           
           status=false;
           
       }
       
       if(!(l_userID.equals("System")||l_userID.equals("Admin")||l_userID.equals("Teacher")||l_userID.equals("Parent"))){
       
           if(!l_service.equals("GeneralLevelConfiguration")){

               if(!bsv.instituteIDValidation(l_instituteID,errhandler,inject,session,dbSession)){

                   status=false;
               }

           }
       
       }
       
       
       switch(l_service){
           
           case "Student360DegreeReport":
               
   
               String studentID=requestJson.getJsonObject("body").getJsonObject("Master").getString("studentID");
               
               if(!bsv.studentIDValidation(studentID, l_instituteID, session, dbSession, inject)){
                   
                   status=false;
                   
               }
               
               
               break;
           
           case "Teacher360DegreeReport":
               

               String teacherID=requestJson.getJsonObject("body").getJsonObject("Master").getString("teacherID");
               
               if(!bsv.teacherIDValidation(teacherID, l_instituteID, session, dbSession, inject)){
                   
                   status=false;
                   
               }
               
               
               break;
           case "Class360DegreeReport":
               
               String classs1 =requestJson.getJsonObject("body").getJsonObject("Master").getString("class");
               String instituteId =requestJson.getJsonObject("body").getJsonObject("Master").getString("instituteID");
               String standard=classs1.split("/")[0];
               String section=classs1.split("/")[1];
               
               if(!bsv.instituteIDValidation(instituteId,errhandler,inject,session,dbSession)){

                   status=false;
               }
               
               
               if(!bsv.standardSectionValidation(standard, section, instituteId, session, dbSession, inject)){
                   
                   status=false;
                   
               }
               
               
               break;
           case "GradeComparison":
               
               
               String instituteID=requestJson.getJsonObject("body").getJsonObject("Master").getString("instituteID");
               String l_standard=requestJson.getJsonObject("body").getJsonObject("Master").getString("class");
               
               if(!bsv.instituteIDValidation(instituteID, errhandler, inject, session, dbSession)){
                   
                   status=false;
                   
               }
               
               if(!bs.getStandardsOfTheInstitute(l_instituteID, session, dbSession, inject).contains(l_standard)){
                   
                   status=false;
               }
               
               
               break;
               
           case "SubstituteReport":
               
               
               String l_teacherID=requestJson.getJsonObject("body").getJsonObject("Master").getString("teacherID");
               String l_date=requestJson.getJsonObject("body").getJsonObject("Master").getString("date");
               dbg("l_date-->"+l_date);
               if(!bsv.teacherIDValidation(l_teacherID, l_instituteID, session, dbSession, inject)){
                   
                   status=false;
                   
               }
               
               if(!bsv.dateFormatValidation(l_date, session, dbSession, inject)){
                   
                   status=false;
                   
               }
               
               if(status){
               
               if(!bsv.pastDateValidation(l_date, session, dbSession, inject)){
                   
                   status=false;
                   
               }
               }
               
               break;
               
            case "PaymentBusinessReport":
               
               dbg("inside PaymentBusinessReport");
               String l_fromDate=requestJson.getJsonObject("body").getJsonObject("Master").getString("fromDate");
               String l_toDate=requestJson.getJsonObject("body").getJsonObject("Master").getString("toDate");
               dbg("l_fromDate-->"+l_fromDate);
               dbg("l_toDate-->"+l_toDate);
               
               
               if(!bsv.dateFormatValidation(l_fromDate, session, dbSession, inject)){
                   
                   status=false;
                   
               }
               if(!bsv.dateFormatValidation(l_toDate, session, dbSession, inject)){
                   
                   status=false;
                   
               }
               if(status){
               
               if(!bsv.futureDateValidation(l_fromDate, session, dbSession, inject)){
                   
                   status=false;
                   
               }
               if(!bsv.futureDateValidation(l_toDate, session, dbSession, inject)){
                   
                   status=false;
                   
               }
               }
               
               ConvertedDate fromDate=bs.getYearMonthandDay(l_fromDate);
                 Calendar start = Calendar.getInstance();

                 start.set(Calendar.YEAR, Integer.parseInt(fromDate.getYear()));
                 start.set(Calendar.MONTH,  Integer.parseInt(fromDate.getMonth()));
                 start.set(Calendar.DAY_OF_MONTH,  Integer.parseInt(fromDate.getDay()));


                 ConvertedDate toDate=bs.getYearMonthandDay(l_toDate);
                 Calendar end = Calendar.getInstance();

                 end.set(Calendar.YEAR, Integer.parseInt(toDate.getYear()));
                 end.set(Calendar.MONTH,  Integer.parseInt(toDate.getMonth()));
                 end.set(Calendar.DAY_OF_MONTH,  Integer.parseInt(toDate.getDay()));

                    if(start.after(end)){

                        status=false;
                        dbg("throwing error for invalid date range");
                        throw new BSValidationException("BS_VAL_003~Invalid Date Range");
                    }


                   int dateSize= bs.getLeaveDates(l_fromDate, l_toDate, session, dbSession, inject).size();


                   if(dateSize>31){

                        status=false;
                        throw new BSValidationException("BS_VAL_002~Please Enter Date Range Within One Month");
                    }
               
               break;   
               
               case "NotificationBusinessReport":
               
               
                l_fromDate=requestJson.getJsonObject("body").getJsonObject("Master").getString("fromDate");
                l_toDate=requestJson.getJsonObject("body").getJsonObject("Master").getString("toDate");
               dbg("l_fromDate-->"+l_fromDate);
               dbg("l_toDate-->"+l_toDate);
               
               
               if(!bsv.dateFormatValidation(l_fromDate, session, dbSession, inject)){
                   
                   status=false;
                   
               }
               if(!bsv.dateFormatValidation(l_toDate, session, dbSession, inject)){
                   
                   status=false;
                   
               }
               if(status){
               
               if(!bsv.futureDateValidation(l_fromDate, session, dbSession, inject)){
                   
                   status=false;
                   
               }
               if(!bsv.futureDateValidation(l_toDate, session, dbSession, inject)){
                   
                   status=false;
                   
               }
               }
               
                fromDate=bs.getYearMonthandDay(l_fromDate);
                  start = Calendar.getInstance();

                 start.set(Calendar.YEAR, Integer.parseInt(fromDate.getYear()));
                 start.set(Calendar.MONTH,  Integer.parseInt(fromDate.getMonth()));
                 start.set(Calendar.DAY_OF_MONTH,  Integer.parseInt(fromDate.getDay()));


                  toDate=bs.getYearMonthandDay(l_toDate);
                  end = Calendar.getInstance();

                 end.set(Calendar.YEAR, Integer.parseInt(toDate.getYear()));
                 end.set(Calendar.MONTH,  Integer.parseInt(toDate.getMonth()));
                 end.set(Calendar.DAY_OF_MONTH,  Integer.parseInt(toDate.getDay()));

                    if(start.after(end)){

                        status=false;
                        throw new BSValidationException("BS_VAL_003~Invalid Date Range");
                    }


                    dateSize= bs.getLeaveDates(l_fromDate, l_toDate, session, dbSession, inject).size();


                   if(dateSize>31){

                        status=false;
                        throw new BSValidationException("BS_VAL_002~Please Enter Date Range Within One Month");
                    }
               
               break;  
               
                case "StudentDetailsReport":
               
               dbg("inside StudentDetailsReport");
                l_fromDate=requestJson.getJsonObject("body").getJsonObject("Master").getString("fromDate");
                l_toDate=requestJson.getJsonObject("body").getJsonObject("Master").getString("toDate");
                String studentStatus=requestJson.getJsonObject("body").getJsonObject("Master").getString("studentStatus");
               dbg("l_fromDate-->"+l_fromDate);
               dbg("l_toDate-->"+l_toDate);
               dbg("studentStatus-->"+studentStatus);
               if(studentStatus.equals("O")){
                   
                   if(l_fromDate!=null&&!l_fromDate.isEmpty()){
                       
                       status=false;
                       throw new BSValidationException("BS_VAL_067~Date range not needed for Studying status");
                       
                   }
                   if(l_toDate!=null&&!l_toDate.isEmpty()){
                       
                       status=false;
                       throw new BSValidationException("BS_VAL_067~Date range not needed for Studying status");
                       
                   }
               }else if(studentStatus.equals("D")){
                   
                   if(l_fromDate==null||l_fromDate.isEmpty()){
                       
                       status=false;
                       throw new BSValidationException("BS_VAL_002~Please enter date range for Studied status");
                       
                   }
                   if(l_toDate==null||l_toDate.isEmpty()){
                       
                       status=false;
                       throw new BSValidationException("BS_VAL_002~Please enter date range for Studied status");
                       
                   }
               }
               
               
               
               break;  
               
       }
       
       
       
       
       
       
       
       
       
       dbg("end of  FileUploadService--->businessValidation--->status-->"+status);
       
       
       
    }catch(DBValidationException ex){
                 
          throw ex;
          }catch(BSValidationException ex){
                 
          throw ex;
        }catch(DBProcessingException ex){
            
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }   
       catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    return status;
    
    
}






 private JsonObject errorResponseBuilding(String validation,JsonObject requestJson,Request request){
        JsonObject jsonResponse;
        
        try{
        
        String errorCode=validation.split("~")[0];
        String errorMessage=validation.split("~")[1];
        JsonArrayBuilder errorBuilder=Json.createArrayBuilder();
//        JsonObject header=request.getJsonObject("header");
//        String messageID=header.getString("msgID");
//        //String correlationID=header.getString("correlationID");
//        String service =header.getString("service");
//        String operation=header.getString("operation");
//        JsonArray BusinessEntity=header.getJsonArray("businessEntity");
//        String instituteID=header.getString("instituteID");
//        String userID=header.getString("userID");
//        String source=header.getString("source");
//        JsonObject body=request.getJsonObject("body");
        //JsonObject audit=request.getJsonObject("audit");
        
         String messageID=request.getReqHeader().getMsgID();
          String service=request.getReqHeader().getService();
          String operation=request.getReqHeader().getOperation();
          Map<String,String>l_businessEntityMap=request.getReqHeader().getBusinessEntity();
          JsonArrayBuilder l_businessEntityBuilder=Json.createArrayBuilder();
          Iterator<String> keyIterator=l_businessEntityMap.keySet().iterator();
          Iterator<String> valueIterator=l_businessEntityMap.values().iterator();
          while(keyIterator.hasNext()&&valueIterator.hasNext()){
              String l_entityName=keyIterator.next();
              String l_entityValue=valueIterator.next();

              l_businessEntityBuilder.add(Json.createObjectBuilder().add("entityName", l_entityName)
                                                                    .add("entityValue", l_entityValue));
          }
          String instituteID=request.getReqHeader().getInstituteID();
          String userID=request.getReqHeader().getUserID();
          String source=request.getReqHeader().getSource();
          JsonObject body=requestJson.getJsonObject("body");
          String correlationID="";
        
        
        errorBuilder.add(Json.createObjectBuilder().add("errorCode", errorCode)
                                                  .add("errorMessage", errorMessage));
        
        
        jsonResponse=Json.createObjectBuilder().add("header",Json.createObjectBuilder()
                                                        .add("msgID",messageID)
                                                        .add("correlationID", correlationID)
                                                        .add("service", service)
                                                        .add("operation", operation)
                                                        .add("businessEntity", l_businessEntityBuilder)
                                                        .add("instituteID", instituteID)
                                                        .add("userID",userID)
                                                        .add("source", source)
                                                        .add("status", "error"))
                                                        .add("body",body)
                                                        //.add("audit", audit)//Integration change
                                                        .add("error",errorBuilder).build();
         return jsonResponse;
        }catch(Exception ex){
            dbg(ex);
            throw ex;
        }
        
    }
 
 
  private JsonObject successResponseBuilding(JsonObject requestJson,Request request){
        JsonObject jsonResponse;
        
        try{
        
//
//        JsonObject header=request.getJsonObject("header");
//        String messageID=header.getString("msgID");
//       //String correlationID=header.getString("correlationID");
//        String service =header.getString("service");
//        String operation=header.getString("operation");
//        JsonArray BusinessEntity=header.getJsonArray("businessEntity");
//        String instituteID=header.getString("instituteID");
//        String userID=header.getString("userID");
//        String source=header.getString("source");
//        JsonObject body=request.getJsonObject("body");
//        String correlationID="";


          String messageID=request.getReqHeader().getMsgID();
          String correlationID="";
          String service=request.getReqHeader().getService();
          String operation=request.getReqHeader().getOperation();
          Map<String,String>l_businessEntityMap=request.getReqHeader().getBusinessEntity();
          JsonArrayBuilder l_businessEntityBuilder=Json.createArrayBuilder();
          Iterator<String> keyIterator=l_businessEntityMap.keySet().iterator();
          Iterator<String> valueIterator=l_businessEntityMap.values().iterator();
          while(keyIterator.hasNext()&&valueIterator.hasNext()){
              String l_entityName=keyIterator.next();
              String l_entityValue=valueIterator.next();

              l_businessEntityBuilder.add(Json.createObjectBuilder().add("entityName", l_entityName)
                                                                    .add("entityValue", l_entityValue));
          }
          String instituteID=request.getReqHeader().getInstituteID();
          String userID=request.getReqHeader().getUserID();
          String l_source=request.getReqHeader().getSource();
          JsonObject body=requestJson.getJsonObject("body");
        
        //JsonObject audit=request.getJsonObject("audit");
        
        
        jsonResponse=Json.createObjectBuilder().add("header",Json.createObjectBuilder()
                                                        .add("msgID",messageID)
                                                         .add("correlationID", correlationID)
                                                        .add("service", service)
                                                        .add("operation", operation)
                                                        .add("businessEntity", l_businessEntityBuilder)
                                                        .add("instituteID", instituteID)
                                                        .add("userID",userID)
                                                        .add("source", l_source)
                                                        .add("status", "success"))
                                                        .add("body",body)
                                                        //.add("audit", "")//Integration change
                                                        .build();
         return jsonResponse;
        }catch(Exception ex){
            dbg(ex);
            throw ex;
        }
        
    }
    
public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }   
     
    
}