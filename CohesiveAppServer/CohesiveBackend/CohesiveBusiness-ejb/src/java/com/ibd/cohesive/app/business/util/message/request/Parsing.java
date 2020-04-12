/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.util.message.request;

import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.debugger.Debug;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 *
 * @author IBD Technologies
 */
public class Parsing {
  Debug debug;
  IBDProperties i_db_properties;

    public IBDProperties getI_db_properties() {
        return i_db_properties;
    }

    public void setI_db_properties(IBDProperties i_db_properties) {
        this.i_db_properties = i_db_properties;
    }
     
   public Debug getDebug() {
        return debug;
    }

    public void setDebug(Debug debug) {
        this.debug = debug;
    }

public void parseReqHeader(Request request,JsonObject p_jsonObject)throws BSProcessingException{
        
        try{
        dbg("inside parse req header");
        JsonObject l_jsonheader=p_jsonObject.getJsonObject("header");
      if(l_jsonheader.getString("msgID")!=null)
       request.getReqHeader().setMsgID(l_jsonheader.getString("msgID"));
     
        if(request.getReqHeader().getMsgID()==null || request.getReqHeader().getMsgID().equals("")){
          request.getReqHeader().setMsgID(CohesiveSession.dataToUUID("messagein").toString());
      }
     request.getReqHeader().setService(l_jsonheader.getString("service"));
     request.getReqHeader().setSource(l_jsonheader.getString("source"));
     request.getReqHeader().setOperation(l_jsonheader.getString("operation"));
     request.getReqHeader().setUserID(l_jsonheader.getString("userID"));
     
     //request.getReqHeader().setKey(l_jsonheader.getString("key"));
     request.getReqHeader().setToken(l_jsonheader.getString("token"));
     
     dbg(l_jsonheader.getString("userID"));
     JsonArray l_businessEntityArray=l_jsonheader.getJsonArray("businessEntity");
     Map<String,String>l_businessEntityMap=new HashMap();
     for(int i=0;i<l_businessEntityArray.size();i++){
         JsonObject l_businessEntityObject=l_businessEntityArray.getJsonObject(i);
         String l_entityName=l_businessEntityObject.getString("entityName");
         String l_entityValue=l_businessEntityObject.getString("entityValue");
         l_businessEntityMap.put(l_entityName, l_entityValue);
     }
     request.getReqHeader().setBusinessEntity(l_businessEntityMap);
//     request.getReqHeader().setBusinessEntity(l_jsonheader.getString("businessEntity"));
     request.getReqHeader().setInstituteID(l_jsonheader.getString("instituteID"));
     request.getReqHeader().setUserID(l_jsonheader.getString("userID"));
        dbg("end of parse req header");
        }
       catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Parsing Exception" + ex.toString());   
        } 
    }  
public void parseReqAudit(Request request,JsonObject p_jsonObject)throws BSProcessingException{
        
        try{
        dbg("inside parse Audit");
        //JsonObject l_auditLog=p_jsonObject.getJsonObject("auditLog"); Integration changes
        JsonObject l_auditLog=p_jsonObject.getJsonObject("audit");
        dbg(request.getReqHeader().getUserID());
        String l_makerID=new String();
        String l_checkerID=new String();
        String l_makerDateStamp=new String();
        String l_checkerDateStamp=new String();
//        String l_recordStatus=new String();
//        String l_authStatus=new String();
//        String l_versionNumber=new String();
//        String l_makerRemarks=new String();
//        String l_checkerRemarks=new String();
//        int versionNumber;
//        String dateformat=getI_db_properties().getProperty("DATE_TIME_FORMAT");
//        SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
//        Date date = new Date();
         Instant timeStamp= Instant.now();
 
       ZonedDateTime date= timeStamp.atZone(ZoneId.of("Asia/Kolkata"));
       String dateformat=i_db_properties.getProperty("DATE_TIME_FORMAT");
       DateTimeFormatter formatter=DateTimeFormatter.ofPattern(dateformat);
       
       String l_dateStamp=date.format(formatter);
        dbg("before switch case in build auditlog");
        switch(request.getReqHeader().getOperation()){
            
          case "Create":
              dbg("case is create");
              l_makerID=request.getReqHeader().getUserID();
              l_checkerID=" ";
//              l_makerDateStamp=formatter.format(date);
              l_makerDateStamp=l_dateStamp;
              l_checkerDateStamp=" ";
//              l_recordStatus="O";
//              l_authStatus="U";
//              l_versionNumber="1";
//              l_makerRemarks=l_auditLog.getString("makerRemarks");
//              l_checkerRemarks=" ";
              break;
              
              
          case "Auth":
              
              l_checkerID=request.getReqHeader().getUserID();
//              l_checkerDateStamp=formatter.format(date);
              l_checkerDateStamp=l_dateStamp;
              l_makerID=l_auditLog.getString("MakerID");
              l_makerDateStamp=l_auditLog.getString("MakerDtStamp");
              
//              l_checkerRemarks=l_auditLog.getString("checkerRemarks");
              break;    
          case "Reject":
              
              l_checkerID=request.getReqHeader().getUserID();
              l_checkerDateStamp=l_dateStamp;
              l_makerID=l_auditLog.getString("MakerID");
              l_makerDateStamp=l_auditLog.getString("MakerDtStamp");
//              l_checkerRemarks=l_auditLog.getString("checkerRemarks");
              break;
//          case "Create-Auth":
//              
//             // l_makerID=l_auditLog.getString("makerID");
//              l_checkerID=request.getReqHeader().getUserID();
//             // l_makerDateStamp=l_auditLog.getString("makerDateStamp");
//              l_checkerDateStamp=formatter.format(date);
//             // l_recordStatus="O";
////              l_authStatus="A";
//             // l_versionNumber=l_auditLog.getString("versionNumber");
//             // l_makerRemarks=l_auditLog.getString("makerRemarks");
//              l_checkerRemarks=l_auditLog.getString("checkerRemarks");
//              break;
         
//          case "Create-Delete":
              
//              //l_makerID=l_auditLog.getString("makerID");
//             // l_checkerID=l_auditLog.getString("checkerID");
//              l_makerDateStamp=l_auditLog.getString("makerDateStamp");
//              l_checkerDateStamp=l_auditLog.getString("checkerDateStamp");
//              l_recordStatus="D";
//              l_authStatus="U";
//              l_versionNumber=l_auditLog.getString("versionNumber");
//              l_makerRemarks=l_auditLog.getString("makerRemarks");
//              l_checkerRemarks=l_auditLog.getString("checkerRemarks");
//              break;
            
          case "Modify":
              dbg("case is modify");
              l_makerID=request.getReqHeader().getUserID();
              l_checkerID=" ";
              l_makerDateStamp=l_dateStamp;
              l_checkerDateStamp=" ";
//              l_recordStatus="O";
//              l_authStatus="U";
//              if(l_auditLog.getString("authStatus").equals("U")){
//              l_versionNumber=l_auditLog.getString("versionNumber");
//              }else{
//              versionNumber=Integer.parseInt(l_auditLog.getString("versionNumber"))+1;
//              l_versionNumber=Integer.toString(versionNumber);
//              }
//              dbg("l_versionNumber"+l_versionNumber);
//              l_makerRemarks=l_auditLog.getString("makerRemarks");
//              l_checkerRemarks=" ";
              break;
              
//          case "Modify-Auth":
//              //l_makerID=l_auditLog.getString("makerID");
//              l_checkerID=request.getReqHeader().getUserID();
//              //l_makerDateStamp=l_auditLog.getString("makerDateStamp");
//              l_checkerDateStamp=formatter.format(date);
//              //l_recordStatus="O";
////              l_authStatus="A";
//             // l_versionNumber=l_auditLog.getString("versionNumber");
//             // l_makerRemarks=l_auditLog.getString("makerRemarks");
//              l_checkerRemarks=l_auditLog.getString("checkerRemarks");
//              break;
              
//          case "Modify-Delete":
              
//              l_makerID=l_auditLog.getString("makerID");
//              l_checkerID=l_auditLog.getString("checkerID");
//              l_makerDateStamp=l_auditLog.getString("makerDateStamp");
//              l_checkerDateStamp=l_auditLog.getString("checkerDateStamp");
//              l_recordStatus="D";
//              l_authStatus="U";
//              l_versionNumber=l_auditLog.getString("versionNumber");
//              l_makerRemarks=l_auditLog.getString("makerRemarks");
//              l_checkerRemarks=l_auditLog.getString("checkerRemarks");
//              break;
              
          case "Delete":
              
              l_makerID=request.getReqHeader().getUserID();
              l_checkerID=" ";
              l_makerDateStamp=l_dateStamp;
              l_checkerDateStamp=" ";
//              l_recordStatus="D";
//              l_authStatus="U";
//              versionNumber=Integer.parseInt(l_auditLog.getString("versionNumber"))+1;
//              l_versionNumber=Integer.toString(versionNumber);
//              l_makerRemarks=l_auditLog.getString("makerRemarks");
//              l_checkerRemarks=" ";
              break;
          case "Create-Default":
              l_makerID=request.getReqHeader().getUserID();
              
              
              break;
          case "Payment-Default":
              l_makerID=request.getReqHeader().getUserID();
              
              
              break;    
          case "AutoAuth":
              
              l_checkerID=request.getReqHeader().getUserID();
              l_makerID=request.getReqHeader().getUserID();
              l_makerDateStamp=l_dateStamp;
              l_checkerDateStamp=l_dateStamp;
//              l_checkerRemarks=l_auditLog.getString("checkerRemarks");
              break;  
          
          
              
//          case "Delete-Auth":
////              l_makerID=l_auditLog.getString("makerID");
//              l_checkerID=l_auditLog.getString("checkerID");
////              l_makerDateStamp=l_auditLog.getString("makerDateStamp");
//              l_checkerDateStamp=l_auditLog.getString("checkerDateStamp");
////              l_recordStatus="D";
////              l_authStatus="A";
////              l_versionNumber=l_auditLog.getString("versionNumber");
////              l_makerRemarks=l_auditLog.getString("makerRemarks");
//              l_checkerRemarks=l_auditLog.getString("checkerRemarks");
//              break;
              
            }
        
//        if(!request.getReqHeader().getOperation().equals("View")){
            
            //if(l_auditLog.getString("makerID").isEmpty()){
//        if(l_makerID==null||l_makerID.isEmpty()){
             request.getReqAudit().setMakerID( l_makerID);
//            request.getReqAudit().setMakerID(l_auditLog.getString("makerID"));
        /*}else{
            
            request.getReqAudit().setMakerID(l_auditLog.getString("makerID"));
            
        }*/
         //if(l_auditLog.getString("checkerID").isEmpty()){
//        if(l_makerID==null||l_makerID.isEmpty()){
             request.getReqAudit().setCheckerID( l_checkerID);
//            request.getReqAudit().setMakerID(l_auditLog.getString("makerID"));
        /*}else{
            
            request.getReqAudit().setCheckerID(l_auditLog.getString("checkerID"));
            
        }*/   
//        if(l_checkerID==null||l_checkerID.isEmpty()){
//            
//            request.getReqAudit().setCheckerID(l_auditLog.getString("checkerID"));
//        }
//        else{
//          request.getReqAudit().setCheckerID(l_checkerID);
//        }
           //if(l_auditLog.getString("makerDateStamp").isEmpty()){
            request.getReqAudit().setMakerDateStamp(l_makerDateStamp);
        /*}
        else
        {
            request.getReqAudit().setMakerDateStamp(l_auditLog.getString("makerDateStamp"));
        }*/
//        if(l_makerDateStamp==null||l_makerDateStamp.isEmpty()){
//            request.getReqAudit().setMakerDateStamp(l_auditLog.getString("makerDateStamp"));
//        }
//        else
//        {
//            request.getReqAudit().setMakerDateStamp(l_makerDateStamp);
//        }
          //if(l_auditLog.getString("checkerDateStamp").isEmpty()){
            request.getReqAudit().setCheckerDateStamp(l_checkerDateStamp);
        /*}
        else
        {
            request.getReqAudit().setCheckerDateStamp(l_auditLog.getString("checkerDateStamp"));
        }*/
//        if(l_checkerDateStamp.isEmpty()){
//            request.getReqAudit().setCheckerDateStamp(l_auditLog.getString("checkerDateStamp"));
//        }
//        else
//        {
//            request.getReqAudit().setCheckerDateStamp(l_checkerDateStamp);
//        }
//        if(l_recordStatus==null||l_recordStatus.isEmpty()){
            //request.getReqAudit().setRecordStatus(l_auditLog.getString("recordStatus"));
            String l_recordStatus =l_auditLog.getString("RecordStat");
            if(l_recordStatus.equals("Open"))
              l_recordStatus="O";
           else if(l_recordStatus.equals("Deleted"))
               l_recordStatus="D";
           String l_authStatus=l_auditLog.getString("AuthStat");
           if(l_authStatus.equals("Unauthorised"))
              l_authStatus="U";
           else if(l_authStatus.equals("Authorised"))
               l_authStatus="A";
           else if(l_authStatus.equals("Rejected")){
               l_authStatus="R";
               
//               if(l_recordStatus.equals("D")){
//                   
//                   l_recordStatus="O";
//                   l_authStatus="A";
//                   
//               }
                
           }
            
            request.getReqAudit().setRecordStatus(l_recordStatus);
//        }
//        else{
//            request.getReqAudit().setRecordStatus(l_recordStatus);
//        }
//        if(l_authStatus==null||l_authStatus.isEmpty()){
            //request.getReqAudit().setAuthStatus(l_auditLog.getString("authStatus"));
            request.getReqAudit().setAuthStatus(l_authStatus);
//        }
//        else{
//            request.getReqAudit().setAuthStatus(l_authStatus);
//        }
//        if(l_versionNumber==null||l_versionNumber.isEmpty()){
//            dbg("l_versionNumber is null");
            //request.getReqAudit().setVersionNumber(l_auditLog.getString("versionNumber"));
//request.getReqAudit().setVersionNumber(Integer.toString(l_auditLog.getInt("Version")));//Integration 
request.getReqAudit().setVersionNumber(l_auditLog.getString("Version"));//Integration 

//        }
//        else{
//            dbg("l_versionNumber is not null");
//            request.getReqAudit().setVersionNumber(l_versionNumber);
//        }
//        if(l_makerRemarks==null||l_makerRemarks.isEmpty()){
            
            //request.getReqAudit().setMakerRemarks(l_auditLog.getString("makerRemarks"));
            if(!l_auditLog.getString("MakerRemarks").isEmpty()){
            
              request.getReqAudit().setMakerRemarks(l_auditLog.getString("MakerRemarks"));
            
            }else{
                
              request.getReqAudit().setMakerRemarks(" ");  
            }
//        }
//        else{
//            request.getReqAudit().setMakerRemarks(l_makerRemarks);
//        }
//        if(l_checkerRemarks==null||l_checkerRemarks.isEmpty()){
//            dbg("checker remarks is null");
//            dbg(l_auditLog.getString("checkerRemarks"));
           // request.getReqAudit().setCheckerRemarks(l_auditLog.getString("checkerRemarks"));
           
           if(!l_auditLog.getString("CheckerRemarks").isEmpty()){
           
                request.getReqAudit().setCheckerRemarks(l_auditLog.getString("CheckerRemarks"));
                
           }else{
               
               request.getReqAudit().setCheckerRemarks(" ");
           }
//        }
//        else{
//            dbg("checker remarks is not null");
//            request.getReqAudit().setCheckerRemarks(l_checkerRemarks);
//        }
//        }  
     
      dbg(request.getReqAudit().getMakerID());  
      dbg(request.getReqAudit().getCheckerID());
      dbg(request.getReqAudit().getMakerRemarks());
      dbg(request.getReqAudit().getCheckerRemarks());
      dbg(request.getReqAudit().getMakerDateStamp());
      dbg(request.getReqAudit().getCheckerDateStamp());
      dbg(request.getReqAudit().getVersionNumber());
      dbg(request.getReqAudit().getRecordStatus());
      dbg(request.getReqAudit().getAuthStatus());
      
              dbg("end of parse Audit");

        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Parsing Exception" + ex.toString());   
        }
        
}
public void parseRequest(Request request,JsonObject p_jsonObject)throws BSProcessingException{
        
        try{
        dbg("inside parse request");
         parseReqHeader(request,p_jsonObject);
         if(!request.getReqHeader().getOperation().equals("View"))//Integration Fix
            parseReqAudit(request,p_jsonObject);
         dbg("end of parse request");
         }
catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Parsing Exception" + ex.toString());   
        }
}




public void dbg(String p_Value) {

        this.debug.dbg(p_Value);

    }

    public void dbg(Exception ex) {

        this.debug.exceptionDbg(ex);

    }
}
