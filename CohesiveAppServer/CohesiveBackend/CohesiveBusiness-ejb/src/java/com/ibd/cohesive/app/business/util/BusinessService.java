/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.util;

import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.classentity.classSoftSkill.ClassSoftSkill;
import com.ibd.cohesive.app.business.classentity.classattendance.ClassAttendance;
import com.ibd.cohesive.app.business.classentity.classexamschedule.ClassExamSchedule;
import com.ibd.cohesive.app.business.classentity.classmark.ClassMark;
import com.ibd.cohesive.app.business.classentity.classtimetable.ClassTimeTable;
import com.ibd.cohesive.app.business.institute.classlevelconfiguration.ClassLevelConfiguration;
import com.ibd.cohesive.app.business.institute.ecircularService.ECircular;
import com.ibd.cohesive.app.business.institute.generallevelconfiguration.GeneralLevelConfiguration;
import com.ibd.cohesive.app.business.institute.groupmapping.GroupMapping;
import com.ibd.cohesive.app.business.institute.holidaymaintanence.HolidayMaintanence;
import com.ibd.cohesive.app.business.institute.instituteassignment.InstituteAssignment;
import com.ibd.cohesive.app.business.institute.institutefeemanagement.InstituteFeeManagement;
import com.ibd.cohesive.app.business.institute.instituteotheractivity.InstituteOtherActivity;
import com.ibd.cohesive.app.business.institute.institutepayment.InstitutePayment;
import com.ibd.cohesive.app.business.institute.managementDashBoard.Entity;
import com.ibd.cohesive.app.business.institute.managementDashBoard.Role;
import com.ibd.cohesive.app.business.institute.notification.Notification;
import com.ibd.cohesive.app.business.student.studentattendanceservice.AuditDetails;
import com.ibd.cohesive.app.business.student.studentleavemanagement.StudentLeaveManagement;
import com.ibd.cohesive.app.business.student.studentotheractivity.StudentOtherActivity;
import com.ibd.cohesive.app.business.student.studentprofile.StudentProfile;
import com.ibd.cohesive.app.business.teacher.teacherleavemanagement.TeacherLeaveManagement;
import com.ibd.cohesive.app.business.teacher.teacherprofile.TeacherProfile;
import com.ibd.cohesive.app.business.user.userprofile.UserProfile;
import com.ibd.cohesive.app.business.user.userrole.UserRole;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.core.metadata.DBColumn;
import com.ibd.cohesive.db.core.metadata.DBTable;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.read.IDBReadService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.debugger.Debug;
import com.ibd.cohesive.util.errorhandling.Errors;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

/**
 *
 * @author IBD Technologies
 */
public class BusinessService {

    //AuditLog i_auditLog=new AuditLog();
//    int maxversion_dummy;
//    String preRecordStatus;
//    String preAuthStatus;
//    String preMakerID;
//    Map<String,ArrayList<String>>filtermap_dummy;
//    String filterkey_dummy;
//    public int getMaxversion_dummy() {
//        return maxversion_dummy;
//    }
//
//    public String getPreRecordStatus() {
//        return preRecordStatus;
//    }
//
//
//    public String getPreAuthStatus() {
//        return preAuthStatus;
//    }
//
//    
//    public String getPreMakerID() {
//        return preMakerID;
//    }
    /* public AuditLog getI_auditLog() {
        return i_auditLog;
    }
     */
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

    public BusinessService() {

    }

    /*public void buildAuditLog(JsonObject p_jsonObject)throws BSProcessingException{
        
        try{
        dbg("inside build auditlog");
        JsonObject l_jsonheader=p_jsonObject.getJsonObject("header");
        JsonObject l_auditLog=p_jsonObject.getJsonObject("auditLog");
        String l_operation=l_jsonheader.getString("operation");
        String l_userID=l_jsonheader.getString("userID");
        dbg(l_jsonheader.getString("userID"));
        String l_makerID=new String();
        String l_checkerID=new String();
        String l_makerDateStamp=new String();
        String l_checkerDateStamp=new String();
        String l_recordStatus=new String();
        String l_authStatus=new String();
        String l_versionNumber=new String();
        String l_makerRemarks=new String();
        String l_checkerRemarks=new String();
        int versionNumber;
        String dateformat=getI_db_properties().getProperty("DATE_TIME_FORMAT");
        SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
        Date date = new Date();
        dbg("before switch case in build auditlog");
        switch(l_operation){
            
          case "Create":
              dbg("case is create");
              l_makerID=l_userID;
              l_checkerID=" ";
              l_makerDateStamp=formatter.format(date);
              l_checkerDateStamp=" ";
//              l_recordStatus="O";
//              l_authStatus="U";
//              l_versionNumber="1";
              l_makerRemarks=l_auditLog.getString("makerRemarks");
              l_checkerRemarks=" ";
              break;
          
          case "Create-Auth":
              
             // l_makerID=l_auditLog.getString("makerID");
              l_checkerID=l_userID;
             // l_makerDateStamp=l_auditLog.getString("makerDateStamp");
              l_checkerDateStamp=formatter.format(date);
             // l_recordStatus="O";
//              l_authStatus="A";
             // l_versionNumber=l_auditLog.getString("versionNumber");
             // l_makerRemarks=l_auditLog.getString("makerRemarks");
              l_checkerRemarks=l_auditLog.getString("checkerRemarks");
              break;
         
          case "Create-Delete":
              
//              //l_makerID=l_auditLog.getString("makerID");
//             // l_checkerID=l_auditLog.getString("checkerID");
//              l_makerDateStamp=l_auditLog.getString("makerDateStamp");
//              l_checkerDateStamp=l_auditLog.getString("checkerDateStamp");
//              l_recordStatus="D";
//              l_authStatus="U";
//              l_versionNumber=l_auditLog.getString("versionNumber");
//              l_makerRemarks=l_auditLog.getString("makerRemarks");
//              l_checkerRemarks=l_auditLog.getString("checkerRemarks");
              break;
            
          case "Modify":
              dbg("case is modify");
              l_makerID=l_userID;
              l_checkerID=" ";
              l_makerDateStamp=formatter.format(date);
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
              l_makerRemarks=l_auditLog.getString("makerRemarks");
              l_checkerRemarks=" ";
              break;
              
          case "Modify-Auth":
              //l_makerID=l_auditLog.getString("makerID");
              l_checkerID=l_userID;
              //l_makerDateStamp=l_auditLog.getString("makerDateStamp");
              l_checkerDateStamp=formatter.format(date);
              //l_recordStatus="O";
//              l_authStatus="A";
             // l_versionNumber=l_auditLog.getString("versionNumber");
             // l_makerRemarks=l_auditLog.getString("makerRemarks");
              l_checkerRemarks=l_auditLog.getString("checkerRemarks");
              break;
              
          case "Modify-Delete":
              
//              l_makerID=l_auditLog.getString("makerID");
//              l_checkerID=l_auditLog.getString("checkerID");
//              l_makerDateStamp=l_auditLog.getString("makerDateStamp");
//              l_checkerDateStamp=l_auditLog.getString("checkerDateStamp");
//              l_recordStatus="D";
//              l_authStatus="U";
//              l_versionNumber=l_auditLog.getString("versionNumber");
//              l_makerRemarks=l_auditLog.getString("makerRemarks");
//              l_checkerRemarks=l_auditLog.getString("checkerRemarks");
              break;
              
          case "Delete":
              
              l_makerID=l_userID;
              l_checkerID=" ";
              l_makerDateStamp=formatter.format(date);
              l_checkerDateStamp=" ";
//              l_recordStatus="D";
//              l_authStatus="U";
//              versionNumber=Integer.parseInt(l_auditLog.getString("versionNumber"))+1;
//              l_versionNumber=Integer.toString(versionNumber);
              l_makerRemarks=l_auditLog.getString("makerRemarks");
              l_checkerRemarks=" ";
              break;
              
          case "Delete-Auth":
//              l_makerID=l_auditLog.getString("makerID");
              l_checkerID=l_auditLog.getString("checkerID");
//              l_makerDateStamp=l_auditLog.getString("makerDateStamp");
              l_checkerDateStamp=l_auditLog.getString("checkerDateStamp");
//              l_recordStatus="D";
//              l_authStatus="A";
//              l_versionNumber=l_auditLog.getString("versionNumber");
//              l_makerRemarks=l_auditLog.getString("makerRemarks");
              l_checkerRemarks=l_auditLog.getString("checkerRemarks");
              break;
              
            }
        
        
//        l_makerID=l_userID;
//         dbg("user id assigned to maker id");
//        if(l_operation.equals("Create-Auth")||l_operation.equals("Modification-Auth")||l_operation.equals("Delete-Auth")){
//        l_checkerID=l_userID;
//       
//        }else{
//        
//        l_checkerID=" ";        
//            
//        }
//        String dateformat=getI_db_properties().getProperty("DATE_TIME_FORMAT");
//        SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
//        Date date = new Date(); 
//        l_makerDateStamp=formatter.format(date);
//        if(l_operation.equals("Create-Auth")||l_operation.equals("Modify-Auth")||l_operation.equals("Delete-Auth")){
//            l_checkerDateStamp=l_makerDateStamp;
//        } else{
//            l_checkerDateStamp=" ";
//        }
//        if(l_operation.equals("Create-Delete")||l_operation.equals("Modify-Delete")||l_operation.equals("Delete-Auth")){
//            l_recordStatus="D";
//            
//        }else{
//            l_recordStatus="O";
//        }
//        
//        if(l_operation.equals("Create-Auth")||l_operation.equals("Modify-Auth")||l_operation.equals("Delete-Auth")){
//            
//            l_authStatus="A";
//        }else{
//            
//            l_authStatus="U";
//        }
//        if(l_operation.equals("Modify")||l_operation.equals("Modify-Auth")||l_operation.equals("Modify-Delete")){
//            
//            int versionNumber=Integer.parseInt(l_auditLog.getString("versionNumber"))+1;
//            l_versionNumber=Integer.toString(versionNumber);
//        }else{
//            
//            l_versionNumber=l_auditLog.getString("versionNumber");
//        }
//        
//        l_makerRemarks=l_auditLog.getString("makerRemarks");
//        l_checkerRemarks=l_auditLog.getString("checkerRemarks");
        if(!l_operation.equals("View")){
        if(l_makerID==null||l_makerID.isEmpty()){
            
            i_auditLog.setMakerID(l_auditLog.getString("makerID"));
        }else{
            
            i_auditLog.setMakerID(l_makerID);
            
        }
        if(l_checkerID==null||l_checkerID.isEmpty()){
            
            i_auditLog.setCheckerID(l_auditLog.getString("checkerID"));
        }
        else{
          i_auditLog.setCheckerID(l_checkerID);
        }
        if(l_makerDateStamp==null||l_makerDateStamp.isEmpty()){
            i_auditLog.setMakerDateStamp(l_auditLog.getString("makerDateStamp"));
        }
        else
        {
            i_auditLog.setMakerDateStamp(l_makerDateStamp);
        }
        if(l_checkerDateStamp==null||l_checkerDateStamp.isEmpty()){
            i_auditLog.setCheckerDateStamp(l_auditLog.getString("checkerDateStamp"));
        }
        else
        {
            i_auditLog.setCheckerDateStamp(l_checkerDateStamp);
        }
        if(l_recordStatus==null||l_recordStatus.isEmpty()){
            i_auditLog.setRecordStatus(l_auditLog.getString("recordStatus"));
        }
        else{
            i_auditLog.setRecordStatus(l_recordStatus);
        }
        if(l_authStatus==null||l_authStatus.isEmpty()){
            i_auditLog.setAuthStatus(l_auditLog.getString("authStatus"));
        }
        else{
            i_auditLog.setAuthStatus(l_authStatus);
        }
        if(l_versionNumber==null||l_versionNumber.isEmpty()){
            dbg("l_versionNumber is null");
            i_auditLog.setVersionNumber(l_auditLog.getString("versionNumber"));
        }
        else{
            dbg("l_versionNumber is not null");
            i_auditLog.setVersionNumber(l_versionNumber);
        }
        if(l_makerRemarks==null||l_makerRemarks.isEmpty()){
            
            i_auditLog.setMakerRemarks(l_auditLog.getString("makerRemarks"));
        }
        else{
            i_auditLog.setMakerRemarks(l_makerRemarks);
        }
        if(l_checkerRemarks==null||l_checkerRemarks.isEmpty()){
            dbg("checker remarks is null");
            dbg(l_auditLog.getString("checkerRemarks"));
            i_auditLog.setCheckerRemarks(l_auditLog.getString("checkerRemarks"));
        }
        else{
            dbg("checker remarks is not null");
            i_auditLog.setCheckerRemarks(l_checkerRemarks);
        }
        }  
     
      dbg(i_auditLog.getMakerID());  
      dbg(i_auditLog.getCheckerID());
      dbg(i_auditLog.getMakerRemarks());
      dbg(i_auditLog.getCheckerRemarks());
      dbg(i_auditLog.getMakerDateStamp());
      dbg(i_auditLog.getCheckerDateStamp());
      dbg(i_auditLog.getVersionNumber());
      dbg(i_auditLog.getRecordStatus());
      dbg(i_auditLog.getAuthStatus());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        
}
     */
    public JsonObject buildAuditLogJson(Request request) throws BSProcessingException {
        JsonObject jsonResponse;
        try {
            dbg("inside--->BusinessService--->buildAuditLogJson");
            String l_makerID = request.getReqAudit().getMakerID();
            String l_checkerID = request.getReqAudit().getCheckerID();
            String l_makerDateStamp = request.getReqAudit().getMakerDateStamp();
            String l_checkerDateStamp = request.getReqAudit().getCheckerDateStamp();
            String l_recordStatus = request.getReqAudit().getRecordStatus();
            String l_authStatus = request.getReqAudit().getAuthStatus();
            String l_versionNumber = request.getReqAudit().getVersionNumber();
            String l_makerRemarks = request.getReqAudit().getMakerRemarks();
            String l_checkerRemarks = request.getReqAudit().getCheckerRemarks();
            dbg("l_makerID" + l_makerID);
            dbg("l_checkerID" + l_checkerID);
            dbg("l_makerDateStamp" + l_makerDateStamp);
            dbg("l_checkerDateStamp" + l_checkerDateStamp);
            dbg("l_recordStatus" + l_recordStatus);
            dbg("l_authStatus" + l_authStatus);
            dbg("l_versionNumber" + l_versionNumber);
            dbg("l_makerRemarks" + l_makerRemarks);
            dbg("l_checkerRemarks" + l_checkerRemarks);

            if (l_makerID == null) {

                l_makerID = "";
            }

            if (l_checkerID == null) {

                l_checkerID = "";
            }

            if (l_makerDateStamp == null) {

                l_makerDateStamp = "";
            }
            if (l_checkerDateStamp == null) {

                l_checkerDateStamp = "";
            }

            if (l_recordStatus == null) {

                l_recordStatus = "";
            }

            if (l_authStatus == null) {

                l_authStatus = "";
            }

            if (l_versionNumber == null) {

                l_versionNumber = "";
            }

            if (l_makerRemarks == null) {

                l_makerRemarks = "";
            }

            if (l_checkerRemarks == null) {

                l_checkerRemarks = "";
            }

            if (l_recordStatus.equals("O")) {
                l_recordStatus = "Open";
            } else if (l_recordStatus.equals("D")) {
                l_recordStatus = "Deleted";
            }

            if (l_authStatus.equals("U")) {
                l_authStatus = "Unauthorised";
            } else if (l_authStatus.equals("A")) {
                l_authStatus = "Authorised";
            } else if (l_authStatus.equals("R")) {
                l_authStatus = "Rejected";
            }

            jsonResponse = Json.createObjectBuilder().add("MakerID", l_makerID) //Integration fix
                    .add("CheckerID", l_checkerID)//Integration fix
                    .add("MakerDtStamp", l_makerDateStamp)//Integration fix
                    .add("CheckerDtStamp", l_checkerDateStamp)//Integration fix
                    .add("RecordStat", l_recordStatus)//Integration fix
                    .add("AuthStat", l_authStatus)//Integration fix
                    .add("Version", l_versionNumber)//Integration fix
                    .add("MakerRemarks", l_makerRemarks)//Integration fix
                    .add("CheckerRemarks", l_checkerRemarks).build();//Integration fix
            dbg("BusinessService--->buildAuditLogJson--->O/P--->AuditLogJson" + jsonResponse);
            dbg("Endof--->BusinessService--->buildAuditLogJson");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        return jsonResponse;
    }

    public void requestlogging(Request request, JsonObject p_request, AppDependencyInjection inject, CohesiveSession session, DBSession dbSession) throws BSProcessingException, DBValidationException, DBProcessingException {
        FileChannel fc = null;
        try {
            dbg("inside BusinessService--->request logging");

            if (request.getReqHeader().getService().equals("SelectBoxMasterService") || request.getReqHeader().getService().contains("Search")) {

                return;
            }

            //IIBDFileUtil iibd=p_dbdi.getIibd_file_util();
            IBDProperties db_properties = session.getCohesiveproperties();
            IDBTransactionService dbts = inject.getDBTransactionService();
            ITransactionControlService tc = inject.getTransactionControlService();
            IPDataService pds = inject.getPdataservice();
            //JsonObject l_jsonObject=inject.getJsonUtil().getJsonObject(p_request);
            //JsonObject body=p_request.getJsonObject("body");
            //JsonObject header=p_request.getJsonObject("header");
            String l_msgID = request.getReqHeader().getMsgID();
            String l_service = request.getReqHeader().getService();
            String l_source = request.getReqHeader().getSource();

            String l_operation = request.getReqHeader().getOperation();
            Map<String, String> l_businessEntityMap = request.getReqHeader().getBusinessEntity();
            Iterator<String> valueIterator = l_businessEntityMap.values().iterator();
            StringBuffer l_businessEntity = new StringBuffer();
            while (valueIterator.hasNext()) {
                String l_entityValue = valueIterator.next();
                l_businessEntity = l_businessEntity.append(l_entityValue).append("^");
            }
            //String l_businessEntity=request.getReqHeader().getOperation();
            String dateformat = getI_db_properties().getProperty("DATE_TIME_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
            Date date = new Date();
            String l_dateStamp = formatter.format(date);
            String l_instituteID = request.getReqHeader().getInstituteID();
            String l_userID = request.getReqHeader().getUserID();
            String l_currentDate = getCurrentDate();
            ByteBuffer copy=ByteBuffer.wrap(p_request.toString().getBytes(Charset.forName("UTF-8")));

//            if (p_request.toString().length() > 5000) {
              if (copy.limit() > 5000) {
                String fileName = i_db_properties.getProperty("DATABASE_HOME_PATH") + "USER" + db_properties.getProperty("FOLDER_DELIMITER") + l_userID + db_properties.getProperty("FOLDER_DELIMITER") + l_currentDate + "_" + l_msgID + ".json";

                dbts.createRecord(session, "USER" + db_properties.getProperty("FOLDER_DELIMITER") + l_userID + db_properties.getProperty("FOLDER_DELIMITER") + l_currentDate + "_LOG", "USER", 27, l_msgID, l_service, l_operation, l_businessEntity.toString(), fileName, l_dateStamp, l_source);

                Path file = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH") + "USER" + db_properties.getProperty("FOLDER_DELIMITER") + l_userID + db_properties.getProperty("FOLDER_DELIMITER") + l_currentDate + "_" + l_msgID + ".json");

                fc = FileChannel.open(file, CREATE, WRITE, APPEND);
                fc.write(ByteBuffer.wrap(p_request.toString().getBytes(Charset.forName("UTF-8"))));

            } else {

                dbts.createRecord(session, "USER" + db_properties.getProperty("FOLDER_DELIMITER") + l_userID + db_properties.getProperty("FOLDER_DELIMITER") + l_currentDate + "_LOG", "USER", 27, l_msgID, l_service, l_operation, l_businessEntity.toString(), p_request.toString(), l_dateStamp, l_source);
            }

//     dbSession.setFileNames_To_Be_Commited("USER"+db_properties.getProperty("FOLDER_DELIMITER")+l_userID+db_properties.getProperty("FOLDER_DELIMITER")+l_userID+"_LOG");
            dbg("inside request Logging source" + l_source);
            if (!(l_source.equals("cohesive_backend"))) {
                dbg("commit is called in request logging");
                tc.commit(session, dbSession);
            }
            dbg("End of BusinessService--->request logging");
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        } finally {
            try {

                if (fc != null) {
                    fc.close();

                }
            } catch (IOException ex) {
                throw new BSProcessingException(ex.toString());
            }
        }
    }

    public void responselogging(JsonObject p_response, AppDependencyInjection inject, CohesiveSession session, DBSession dbSession) throws BSProcessingException, DBValidationException, DBProcessingException {

        FileChannel fc = null;
        try {
            dbg("inside BusinessService--->responselogging");
            JsonObject header = p_response.getJsonObject("header");
            String l_service = header.getString("service");

            if (l_service.equals("SelectBoxMasterService") || l_service.contains("Search")) {

                return;
            }

            //IIBDFileUtil iibd=p_dbdi.getIibd_file_util();
            IBDProperties db_properties = session.getCohesiveproperties();
            IDBTransactionService dbts = inject.getDBTransactionService();
            IPDataService pds = inject.getPdataservice();
            ITransactionControlService tc = inject.getTransactionControlService();
            // JsonObject l_jsonObject=inject.getJsonUtil().getJsonObject(p_response);
            String l_msgID = header.getString("msgID");
            String l_correalationID = header.getString("correlationID");
            String l_operation = header.getString("operation");
            JsonArray l_businessEntityArray = header.getJsonArray("businessEntity");
            StringBuffer l_businessEntity = new StringBuffer();
            for (int i = 0; i < l_businessEntityArray.size(); i++) {
                JsonObject l_entityObject = l_businessEntityArray.getJsonObject(i);
                String l_entityValue = l_entityObject.getString("entityValue");
                l_businessEntity = l_businessEntity.append(l_entityValue).append("^");
            }

//     String l_businessEntity=header.getString("businessEntity");
            String l_userID = header.getString("userID");
            String l_status = header.getString("status");
            String dateformat = getI_db_properties().getProperty("DATE_TIME_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
            Date date = new Date();
            String l_dateStamp = formatter.format(date);
            String l_instituteID = header.getString("instituteID");
            String l_source = header.getString("source");
            String l_currentDate = getCurrentDate();
            ByteBuffer copy=ByteBuffer.wrap(p_response.toString().getBytes(Charset.forName("UTF-8")));
//             dbts.createRecord(session,"USER"+db_properties.getProperty("FOLDER_DELIMITER")+l_userID+db_properties.getProperty("FOLDER_DELIMITER")+l_currentDate+"_LOG","USER",28, l_msgID,l_correalationID,l_service,l_operation,l_businessEntity.toString(),l_status,p_response.toString(),l_dateStamp,l_source);
//            if (p_response.toString().length() > 5000) {
               if (copy.limit() > 5000) {
                String fileName = i_db_properties.getProperty("DATABASE_HOME_PATH") + "USER" + db_properties.getProperty("FOLDER_DELIMITER") + l_userID + db_properties.getProperty("FOLDER_DELIMITER") + l_currentDate + "_" + l_msgID + ".json";

                dbts.createRecord(session, "USER" + db_properties.getProperty("FOLDER_DELIMITER") + l_userID + db_properties.getProperty("FOLDER_DELIMITER") + l_currentDate + "_LOG", "USER", 28, l_msgID, l_correalationID, l_service, l_operation, l_businessEntity.toString(), l_status, fileName, l_dateStamp, l_source);

                Path file = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH") + "USER" + db_properties.getProperty("FOLDER_DELIMITER") + l_userID + db_properties.getProperty("FOLDER_DELIMITER") + l_currentDate + "_" + l_msgID + ".json");

                fc = FileChannel.open(file, CREATE, WRITE, APPEND);
                fc.write(ByteBuffer.wrap(p_response.toString().getBytes(Charset.forName("UTF-8"))));

            } else {

                dbts.createRecord(session, "USER" + db_properties.getProperty("FOLDER_DELIMITER") + l_userID + db_properties.getProperty("FOLDER_DELIMITER") + l_currentDate + "_LOG", "USER", 28, l_msgID, l_correalationID, l_service, l_operation, l_businessEntity.toString(), l_status, p_response.toString(), l_dateStamp, l_source);
            }

//     dbSession.setFileNames_To_Be_Commited("USER"+db_properties.getProperty("FOLDER_DELIMITER")+l_userID+db_properties.getProperty("FOLDER_DELIMITER")+l_userID+"_LOG");
            tc.commit(session, dbSession);
            dbg("end of BusinessService--->responselogging");
        } catch (DBValidationException ex) {
            //throw ex;
            dbg("Response Logging failed in DB validation exception");
            dbg("error:" + ex.toString());
            dbg(ex);
        } catch (DBProcessingException ex) {

            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        } finally {
            try {
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException ex) {
                throw new BSProcessingException(ex.toString());
            }
        }
    }

    public JsonObject buildErrorResponse(Request request, JsonObject p_request, String p_exceptionName, AppDependencyInjection inject, CohesiveSession session, DBSession dbSession) throws BSProcessingException, DBValidationException, DBProcessingException {
        JsonObjectBuilder jsonResponse = Json.createObjectBuilder();
        try {
            dbg("inside businessService--->buildErrorResponse ");
            dbg("businessService--->buildErrorResponse--->I/P--->p_exceptionName ");
            BusinessService bs = inject.getBusinessService(session);
            //IIBDFileUtil iibd=p_dbdi.getIibd_file_util();
            //JsonObject p_jsonObject=inject.getJsonUtil().getJsonObject(p_request);
            //JsonObject l_jsonHeader=p_request.getJsonObject("header");
            String l_msgID = request.getReqHeader().getMsgID();
            /*if(l_jsonHeader.getString("msgID")==null){
          l_msgID=CohesiveSession.dataToUUID("messageout").toString();
      }else{
      l_msgID=l_jsonHeader.getString("msgID");
      }*/
            String l_service = request.getReqHeader().getService();
            String l_operation = request.getReqHeader().getOperation();
            Map<String, String> l_businessEntityMap = request.getReqHeader().getBusinessEntity();
            JsonArrayBuilder l_businessEntityBuilder = Json.createArrayBuilder();
            Iterator<String> keyIterator = l_businessEntityMap.keySet().iterator();
            Iterator<String> valueIterator = l_businessEntityMap.values().iterator();
            while (keyIterator.hasNext() && valueIterator.hasNext()) {
                String l_entityName = keyIterator.next();
                String l_entityValue = valueIterator.next();

                l_businessEntityBuilder.add(Json.createObjectBuilder().add("entityName", l_entityName)
                        .add("entityValue", l_entityValue));
            }

//      String l_businessEntity=request.getReqHeader().getBusinessEntity();
            String l_instituteID = request.getReqHeader().getInstituteID();
            String l_userID = request.getReqHeader().getUserID();
            String l_source = request.getReqHeader().getSource();
            dbg("l_source" + l_source);
            UUID r_msgID = CohesiveSession.dataToUUID("messageout");
            JsonObject r_JsonBody = p_request.getJsonObject("body");
            //JsonObject l_auditLog=p_request.getJsonObject("auditLog");//Integration Fix
            JsonObject l_auditLog = null;
            if (!l_service.contains("Summary")) {

                l_auditLog = p_request.getJsonObject("audit");

            }

            dbg("buildErrorResponse--->before get error json call");
            JsonArray l_errorJson = getErrorJson(p_exceptionName, l_instituteID, inject, session, dbSession);
            dbg("buildErrorResponse--->after get error json call");

            if (l_operation.equals("Default-Validate")) {
                jsonResponse = Json.createObjectBuilder().add("header", Json.createObjectBuilder()
                        .add("msgID", r_msgID.toString())
                        .add("correlationID", l_msgID)
                        .add("service", l_service)
                        .add("operation", l_operation)
                        .add("businessEntity", l_businessEntityBuilder)
                        .add("instituteID", l_instituteID)
                        .add("userID", l_userID)
                        .add("source", l_source)
                        .add("status", "error"))
                        .add("body", r_JsonBody)
                        .add("audit", l_auditLog)//Integration change
                        .add("error", l_errorJson);

            } else if (l_service.contains("Summary")) {

                jsonResponse = Json.createObjectBuilder().add("header", Json.createObjectBuilder()
                        .add("msgID", r_msgID.toString())
                        .add("correlationID", l_msgID)
                        .add("service", l_service)
                        .add("operation", l_operation)
                        .add("businessEntity", l_businessEntityBuilder)
                        //                                                        .add("businessEntity", l_businessEntity)
                        .add("instituteID", l_instituteID)
                        .add("userID", l_userID)
                        .add("source", l_source)
                        .add("status", "error"))
                        .add("body", r_JsonBody)
                        //.add("auditLog", l_auditLog)
                        //                                                        .add("audit", l_auditLog) //Integration change
                        .add("error", l_errorJson);
            } else {

                jsonResponse = Json.createObjectBuilder().add("header", Json.createObjectBuilder()
                        .add("msgID", r_msgID.toString())
                        .add("correlationID", l_msgID)
                        .add("service", l_service)
                        .add("operation", l_operation)
                        .add("businessEntity", l_businessEntityBuilder)
                        //                                                        .add("businessEntity", l_businessEntity)
                        .add("instituteID", l_instituteID)
                        .add("userID", l_userID)
                        .add("source", l_source)
                        .add("status", "error"))
                        .add("body", r_JsonBody)
                        //.add("auditLog", l_auditLog)
                        .add("audit", l_auditLog) //Integration change
                        .add("error", l_errorJson);

            }

//      jsonResponse=Json.createObjectBuilder().add("header",Json.createObjectBuilder()
//                                                        .add("msgID",r_msgID.toString())
//                                                        .add("correlationID", l_msgID)
//                                                        .add("service", l_service)
//                                                        .add("operation", l_operation)
//                                                        .add("businessEntity", l_businessEntityBuilder)
////                                                        .add("businessEntity", l_businessEntity)
//                                                        .add("instituteID", l_instituteID)
//                                                        .add("userID",l_userID)
//                                                        .add("source", l_source)
//                                                        .add("status", "error"))
//                                                        .add("body",r_JsonBody)
//                                                        .add("auditLog", l_auditLog)
//                                                        .add("error",l_errorJson);
            dbg("End of businessService--->buildErrorResponse ");
        } catch (DBValidationException ex) {

            throw ex;
        } catch (DBProcessingException ex) {

            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        return jsonResponse.build();
    }

    public JsonArray getErrorJson(String p_exceptionName, String p_instituteID, AppDependencyInjection inject, CohesiveSession session, DBSession dbSession) throws DBValidationException, DBProcessingException, BSProcessingException {

        JsonArrayBuilder errorBuilder = Json.createArrayBuilder();
        JsonObjectBuilder errorObjectBuilder = Json.createObjectBuilder();
        JsonArray errorJson;
        try {
            dbg("inside businessService--->getErrorJson ");
            dbg("businessService--->getErrorJson--->I/P--->p_exceptionName " + p_exceptionName);
            dbg("businessService--->getErrorJson--->I/P--->p_instituteID " + p_instituteID);//Integration Change
            IPDataService pds = inject.getPdataservice();
            IDBReadService dbrs = inject.getDbreadservice();
            IBDProperties db_properties = session.getCohesiveproperties();
            String l_errorCode = new String();
            String l_errorMessage = new String();
            switch (p_exceptionName) {

                case "BSValidationException":
                    dbg("inside BSValidationException ");
                    ArrayList<Errors> errorlist = session.getErrorhandler().getError_list();
                    for (int i = 0; i < errorlist.size(); i++) {

                        Errors error = new Errors();
                        error = errorlist.get(i);
                        l_errorCode = error.getError_code();
                        String l_errorParam = error.getError_param();

                        if (l_errorParam == null) {
                            String l_pkey = l_errorCode;
                            ArrayList<String> l_errorRecord = pds.readRecordPData(session, dbSession, "APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "ERROR_MASTER", l_pkey);
                            l_errorMessage = l_errorRecord.get(1).trim();
                            errorBuilder.add(Json.createObjectBuilder().add("errorCode", l_errorCode)
                                    .add("errorMessage", l_errorMessage));
                        } else {
                            if (l_errorParam.contains(",")) {
                                String[] l_errorParamArr = l_errorParam.split(",");
                                dbg("error code" + l_errorCode);
                                l_errorMessage = getErrorMessage(l_errorCode, l_errorParamArr, inject, session, dbSession);
                                errorBuilder.add(Json.createObjectBuilder().add("errorCode", l_errorCode)
                                        .add("errorMessage", l_errorMessage));
                            } else {
                                String l_pkey = l_errorCode;
                                ArrayList<String> l_errorRecord = pds.readRecordPData(session, dbSession, "APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "ERROR_MASTER", l_pkey);
                                l_errorMessage = l_errorRecord.get(1).trim();
                                if (l_errorMessage.contains("$1")) {
                                    l_errorMessage = l_errorMessage.replace("$1", l_errorParam);
                                }
                                errorBuilder.add(Json.createObjectBuilder().add("errorCode", l_errorCode)
                                        .add("errorMessage", l_errorMessage));
                            }
                        }
//          for (i=0;i<errParam.length;i++)
//	{	
//	 ErrorMessage= ErrorMessage.replace('$'+(i+1),errParam[i]);
//	}

//           if(l_samperrorMessage.contains("$1")){
//               
//               l_errorMessage=l_samperrorMessage.replace("$1", l_errorParam);
//               
//           }else{
//               l_errorMessage=l_samperrorMessage;
//           }
                    }
                    dbg("error message" + l_errorMessage);
                    break;

                case "DBValidationException":
                    String l_sessionErrorCode = session.getErrorhandler().getSession_error_code().toString();
                    dbg("l_sessionErrorCode" + l_sessionErrorCode);
                    String[] l_errorArray = l_sessionErrorCode.split("~");

                    for (int i = 0; i < l_errorArray.length; i++) {
                        String l_pkey;
                        l_errorCode = l_errorArray[i];
                        if (l_errorCode.contains(",")) {
                            int index = l_errorCode.indexOf(",");
                            String l_errcode = l_errorCode.substring(0, index);
                            dbg("errorcode" + l_errcode);
                            String l_errorParam = l_errorCode.substring(index + 1);
                            dbg("errorParam without split" + l_errorParam);
                            String[] l_errorParamArr = l_errorParam.split(",");
                            dbg("number of error parameters" + l_errorParamArr.length);
                            l_errorMessage = getErrorMessage(l_errcode, l_errorParamArr, inject, session, dbSession);
                            errorBuilder.add(Json.createObjectBuilder().add("errorCode", l_errcode)
                                    .add("errorMessage", l_errorMessage));
                        } else {
                            l_pkey = l_errorCode;
                            ArrayList<String> l_errorRecord = pds.readRecordPData(session, dbSession, "APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "ERROR_MASTER", l_pkey);
                            l_errorMessage = l_errorRecord.get(1).trim();
                            errorBuilder.add(Json.createObjectBuilder().add("errorCode", l_errorCode)
                                    .add("errorMessage", l_errorMessage));
                        }
//                                     dbg("l_errorCode"+l_errorCode);

                    }

                    break;
                case "DBProcessingException":

                    l_errorCode = "DB_VAL_019";
                    l_errorMessage = "There is Fatal Error! Please contact System Administrator";
                    errorBuilder.add(Json.createObjectBuilder().add("errorCode", l_errorCode)
                            .add("errorMessage", l_errorMessage));
                    break;
                case "BSProcessingException":

                    l_errorCode = "BS_VAL_029";
                    l_errorMessage = "There is Fatal Error! Please contact System Administrator";
                    errorBuilder.add(Json.createObjectBuilder().add("errorCode", l_errorCode)
                            .add("errorMessage", l_errorMessage));
                    break;
                case "SUCCESS BUILD":
                    l_errorCode = "BS_VAL_030";
                    l_errorMessage = "There is Fatal Error! Please contact System Administrator";
                    errorBuilder.add(Json.createObjectBuilder().add("errorCode", l_errorCode)
                            .add("errorMessage", l_errorMessage));
                    break;

            }
            dbg("End of businessService--->buildErrorResponse ");
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        return errorJson = errorBuilder.build();
    }

    public String getErrorMessage(String p_errorCode, String[] p_errorParam, AppDependencyInjection inject, CohesiveSession session, DBSession dbSession) throws BSProcessingException, DBValidationException, DBProcessingException {
        String l_errorMessage;
        try {
            dbg("inside businessService--->getErrorMessage");
            dbg("businessService--->getErrorMessage--->I/P--->p_errorCode" + p_errorCode);
            for (String s : p_errorParam) {
                dbg("businessService--->getErrorMessage--->I/P--->p_errorParam" + s);
            }
            IPDataService pds = inject.getPdataservice();
            String[] l_pkey = {p_errorCode};
            ArrayList<String> l_errorRecord = pds.readRecordPData(session, dbSession, "APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "ERROR_MASTER", l_pkey);
            l_errorMessage = l_errorRecord.get(1).trim();
            dbg("l_errorMessage" + l_errorMessage);
            for (int i = 0; i < p_errorParam.length; i++) {
                l_errorMessage = l_errorMessage.replace("$" + (i + 1), p_errorParam[i]);
            }
            dbg("businessService--->getErrorMessage--->O/P--->l_errorMessage" + l_errorMessage);
            dbg("end of businessService--->getErrorMessage");
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        dbg("err" + l_errorMessage);
        return l_errorMessage;
    }
// public void buildPreviousAudit(Request request,JsonObject p_jsonObject,AppDependencyInjection inject,CohesiveSession session,DBSession dbSession)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
//     
//     try{
//         IDBReadService dbrs=inject.getDbreadservice();
//         IMetaDataService mds=inject.getMetadataservice();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        ErrorHandler errorHandler=session.getErrorhandler();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        //JsonObject l_header=p_jsonObject.getJsonObject("header");
//        dbg("inside businessService--->getErrorMessage");
//        
//        String l_operation=request.getReqHeader().getOperation();
//        if(!(l_operation.equals("Create"))){
////        if(!(l_operation.equals("Create"))||(l_operation.equals("Create-AutoAuth"))){
//        
//        JsonObject l_body=p_jsonObject.getJsonObject("body");
//        //JsonObject l_auditLog=p_jsonObject.getJsonObject("auditLog");
//        String l_service=request.getReqHeader().getService();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        String l_teacherID;
//        String l_studentID;
//        String l_standard;
//        String l_section;
//        String l_exam;
//        String l_from;
//        String l_to;
//        String l_subjectID;
//        String l_assignmentID;
//        String l_year;
//        String l_month;
//        String l_date;
//        String l_feeID;
//        Map<String, ArrayList<String>>l_viewMap=new HashMap();
//        ArrayList<String> l_maxList;
//        Map<String,List<ArrayList<String>>>l_viewcollect;
//        switch(l_service){
//            
//           case "TeacherTimeTable":
//          l_teacherID= l_body.getString("teacherID");
//          l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_TEACHER_TIMETABLE_MASTER",session);
//          l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_teacherID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
//          if(!(l_viewcollect.isEmpty())){
//          Iterator iterator1=l_viewcollect.keySet().iterator();
//          Iterator<List<ArrayList<String>>> iterator2=l_viewcollect.values().iterator();
//          filtermap_dummy=new HashMap();
//          while(iterator1.hasNext()&&iterator2.hasNext()){
//          filterkey_dummy= (String)iterator1.next();
//          dbg(filterkey_dummy);
//          List<ArrayList<String>>list_for_filter=iterator2.next();  
//          maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(7).trim())).max().getAsInt();
//           dbg("maxversion"+maxversion_dummy);   
//          list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(7).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//          }
//          }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//                     
//          }
//           l_maxList=filtermap_dummy.get(l_teacherID); 
//           preRecordStatus=l_maxList.get(5).trim();
//           preAuthStatus=l_maxList.get(6).trim();
//           preMakerID=l_maxList.get(1).trim();
//           
//           break;
//           
//        case "ClassTimeTable":  
//            l_standard=l_body.getString("standard");
//            l_section=l_body.getString("section");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS","CLASS_TIMETABLE_MASTER",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_standard)&&rec.get(1).trim().equals(l_section)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()));
//           if(!(l_viewcollect.isEmpty())){
//            Iterator iterator3=l_viewcollect.keySet().iterator();
//           Iterator<List<ArrayList<String>>> iterator4=l_viewcollect.values().iterator();
//           filtermap_dummy=new HashMap();
//           while(iterator3.hasNext()&&iterator4.hasNext()){
//           filterkey_dummy= (String)iterator3.next();
//           dbg(filterkey_dummy);
//           List<ArrayList<String>>list_for_filter=iterator4.next();  
//           maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(8).trim())).max().getAsInt();
//           dbg("maxversion"+maxversion_dummy);   
//           list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(8).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//           }
//           }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//                     
//          }
//           l_maxList=filtermap_dummy.get(l_standard+l_section); 
//           preRecordStatus=l_maxList.get(6).trim();
//           preAuthStatus=l_maxList.get(7).trim();
//           preMakerID=l_maxList.get(2).trim();  
//            break;
//            
//           case "TeacherProfile": 
//            l_teacherID= l_body.getString("teacherID");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_TEACHER_PROFILE",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_teacherID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
//            if(!(l_viewcollect.isEmpty())){
//            Iterator iterator5=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator6=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator5.hasNext()&&iterator6.hasNext()){
//            filterkey_dummy= (String)iterator5.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>teacherProfile_list_for_filter=iterator6.next();  
//            maxversion_dummy= teacherProfile_list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(21).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            teacherProfile_list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(21).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//                     
//          }
//            l_maxList=filtermap_dummy.get(l_teacherID); 
//            preRecordStatus=l_maxList.get(19).trim();
//            preAuthStatus=l_maxList.get(20).trim();
//            preMakerID=l_maxList.get(15).trim();
//            break;
//            
//            case "StudentProfile":
//             l_studentID=l_body.getString("studentID");
//             l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_PROFILE",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_studentID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
//            if(!(l_viewcollect.isEmpty())){
//            Iterator iterator7=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator8=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator7.hasNext()&&iterator8.hasNext()){
//            filterkey_dummy= (String)iterator7.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>studentProfile_list_for_filter=iterator8.next();  
//            maxversion_dummy= studentProfile_list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(19).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy); 
//            studentProfile_list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(19).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//                     
//           }
//            l_maxList=filtermap_dummy.get(l_studentID); 
//            preRecordStatus=l_maxList.get(17).trim();
//            preAuthStatus=l_maxList.get(18).trim();
//            preMakerID=l_maxList.get(13).trim();
//            break;
//            
//            case "TeacherAttendance":
//            l_teacherID= l_body.getString("teacherID");
//            l_date=l_body.getString("date");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_TEACHER_ATTENDANCE_MASTER",session);
//             l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_teacherID)&&rec.get(1).trim().equals(l_date)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()));
//            if(!(l_viewcollect.isEmpty())){
//            Iterator iterator9=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator10=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator9.hasNext()&&iterator10.hasNext()){
//            filterkey_dummy= (String)iterator9.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>teacherAttendance_list_for_filter=iterator10.next();  
//            maxversion_dummy= teacherAttendance_list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(8).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            teacherAttendance_list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(8).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_teacherID+l_date); 
//            preRecordStatus=l_maxList.get(6).trim();
//            preAuthStatus=l_maxList.get(7).trim();
//            preMakerID=l_maxList.get(2).trim();
//            break;
//            
//            case "StudentAttendance":
//            l_studentID=l_body.getString("studentID");
//            l_year=l_body.getString("year");
//            l_month=l_body.getString("month");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_ATTENDANCE",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_studentID)&&rec.get(1).trim().equals(l_year)&&rec.get(2).trim().equals(l_month)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()+rec.get(2).trim()));
//            if(!(l_viewcollect.isEmpty())){
//            Iterator iterator11=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator12=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator11.hasNext()&&iterator12.hasNext()){
//            filterkey_dummy= (String)iterator11.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>studentAttendance_list_for_filter=iterator12.next();  
//            maxversion_dummy= studentAttendance_list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(40).trim())).max().getAsInt();
//            studentAttendance_list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(40).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_studentID+l_year+l_month); 
//            preRecordStatus=l_maxList.get(38).trim();
//            preAuthStatus=l_maxList.get(39).trim();
//            preMakerID=l_maxList.get(34).trim();
//            break;    
//                
//            case "ClassMark":    
//            l_standard=l_body.getString("standard");
//            l_section=l_body.getString("section");
//            l_exam=l_body.getString("exam");
//            l_subjectID=l_body.getString("subjectID");    
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS","CLASS_MARK_ENTRY",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_standard)&&rec.get(1).trim().equals(l_section)&&rec.get(2).trim().equals(l_exam)&&rec.get(3).trim().equals(l_subjectID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()+rec.get(2).trim()+rec.get(3).trim()));
//            if(!(l_viewcollect.isEmpty())){
//            Iterator iterator13=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator14=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator13.hasNext()&&iterator14.hasNext()){
//            filterkey_dummy= (String)iterator13.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>list_for_filter=iterator14.next();  
//            maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(10).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(10).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_standard+l_section+l_exam+l_subjectID); 
//            preRecordStatus=l_maxList.get(8).trim();
//            preAuthStatus=l_maxList.get(9).trim();
//            preMakerID=l_maxList.get(4).trim();
//            break;     
//                
//            case "StudentProgessCard":    
//            l_studentID=l_body.getString("studentID");
//            l_exam=l_body.getString("exam");    
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_PRORESS_CARD",session);    
//            Map<String,List<ArrayList<String>>>l_progressCollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_studentID)&&rec.get(1).trim().equals(l_exam)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()));
//            if(!(l_progressCollect.isEmpty())){
//            Iterator iterator15=l_progressCollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator16=l_progressCollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator15.hasNext()&&iterator16.hasNext()){
//            filterkey_dummy= (String)iterator15.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>list_for_filter=iterator16.next();  
//            maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(10).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(10).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_studentID+l_exam); 
//            preRecordStatus=l_maxList.get(8).trim();
//            preAuthStatus=l_maxList.get(9).trim();
//            preMakerID=l_maxList.get(4).trim();
//            break; 
//            
//            case "ClassAttendance":
//            l_standard=l_body.getString("standard");
//            l_section=l_body.getString("section");
//            l_date=l_body.getString("date");    
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS","CLASS_ATTENDANCE_MASTER",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_standard)&&rec.get(1).trim().equals(l_section)&&rec.get(2).trim().equals(l_date)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()+rec.get(2).trim()));
//            if(!(l_viewcollect.isEmpty())){
//            Iterator iterator17=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator18=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator17.hasNext()&&iterator18.hasNext()){
//            filterkey_dummy= (String)iterator17.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>list_for_filter=iterator18.next();  
//            maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(9).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(9).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_standard+l_section+l_date); 
//            preRecordStatus=l_maxList.get(7).trim();
//            preAuthStatus=l_maxList.get(8).trim();
//            preMakerID=l_maxList.get(3).trim();
//            break;
//            
//            case "StudentLeaveManagement":
//            l_studentID=l_body.getString("studentID");
//            l_from=l_body.getString("from");
//            l_to=l_body.getString("to");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_LEAVE_MANAGEMENT",session);
//            Map<String,List<ArrayList<String>>>l_studentLeaveCollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_studentID)&&rec.get(1).trim().equals(l_from)&&rec.get(2).trim().equals(l_to)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()+rec.get(2).trim()));
//            if(!(l_studentLeaveCollect.isEmpty())){
//            Iterator iterator19=l_studentLeaveCollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator20=l_studentLeaveCollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator19.hasNext()&&iterator20.hasNext()){
//            filterkey_dummy= (String)iterator19.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>studentLeave_list_for_filter=iterator20.next();  
//            maxversion_dummy= studentLeave_list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(13).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            studentLeave_list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(13).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_studentID+l_from+l_to); 
//            preRecordStatus=l_maxList.get(11).trim();
//            preAuthStatus=l_maxList.get(12).trim();
//            preMakerID=l_maxList.get(7).trim();
//            break;
//            
//            case "TeacherLeaveManagement":
//            l_teacherID=l_body.getString("teacherID");
//            l_from=l_body.getString("from");
//            l_to=l_body.getString("to");    
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_TEACHER_LEAVE_MANAGEMENT",session);    
//            Map<String,List<ArrayList<String>>>l_teacherLeaveCollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_teacherID)&&rec.get(1).trim().equals(l_from)&&rec.get(2).trim().equals(l_to)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()+rec.get(2).trim()));
//            if(!(l_teacherLeaveCollect.isEmpty())){
//            Iterator iterator21=l_teacherLeaveCollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator22=l_teacherLeaveCollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator21.hasNext()&&iterator22.hasNext()){
//            filterkey_dummy= (String)iterator21.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>teacherLeave_list_for_filter=iterator22.next();  
//            maxversion_dummy= teacherLeave_list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(13).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            teacherLeave_list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(13).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_teacherID+l_from+l_to); 
//            preRecordStatus=l_maxList.get(11).trim();
//            preAuthStatus=l_maxList.get(12).trim();
//            preMakerID=l_maxList.get(7).trim();
//            break;
//            
//            case "ClassAssignment":
//            l_standard=l_body.getString("standard");
//            l_section=l_body.getString("section");
//            l_subjectID=l_body.getString("subjectID");
//            l_assignmentID=l_body.getString("assignmentID");    
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS","CLASS_ASSIGNMENT",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_standard)&&rec.get(1).trim().equals(l_section)&&rec.get(2).trim().equals(l_subjectID)&&rec.get(3).trim().equals(l_assignmentID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()+rec.get(2).trim()+rec.get(3).trim()));
//            if(!(l_viewcollect.isEmpty())){
//            Iterator iterator23=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator24=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator23.hasNext()&&iterator24.hasNext()){
//            filterkey_dummy= (String)iterator23.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>list_for_filter=iterator24.next();  
//            maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(15).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(15).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_standard+l_section+l_subjectID+l_assignmentID); 
//            preRecordStatus=l_maxList.get(13).trim();
//            preAuthStatus=l_maxList.get(14).trim();
//            preMakerID=l_maxList.get(9).trim();
//            break;
//            
//            case "StudentAssignment":
//            l_studentID=l_body.getString("studentID");
//            l_subjectID=l_body.getString("subjectID");
//            l_assignmentID=l_body.getString("assignmentID");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_ASSIGNMENT",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_studentID)&&rec.get(1).trim().equals(l_subjectID)&&rec.get(2).trim().equals(l_assignmentID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()+rec.get(2).trim()));
//             if(!(l_viewcollect.isEmpty())){
//            Iterator iterator25=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator26=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator25.hasNext()&&iterator26.hasNext()){
//            filterkey_dummy= (String)iterator25.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>list_for_filter=iterator26.next();  
//            maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(15).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(15).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_studentID+l_subjectID+l_assignmentID); 
//            preRecordStatus=l_maxList.get(13).trim();
//            preAuthStatus=l_maxList.get(14).trim();
//            preMakerID=l_maxList.get(9).trim();
//            break;
//            
//            case "ClassFeeManagement":
//            l_standard=l_body.getString("standard");
//            l_section=l_body.getString("section");
//            l_feeID=l_body.getString("feeID");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS","CLASS_FEE_MANAGEMENT",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_standard)&&rec.get(1).trim().equals(l_section)&&rec.get(2).trim().equals(l_feeID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()+rec.get(2).trim()));
//             if(!(l_viewcollect.isEmpty())){
//            Iterator iterator27=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator28=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator27.hasNext()&&iterator28.hasNext()){
//            filterkey_dummy= (String)iterator27.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>list_for_filter=iterator28.next();  
//            maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(12).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(12).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_standard+l_section+l_feeID); 
//            preRecordStatus=l_maxList.get(10).trim();
//            preAuthStatus=l_maxList.get(11).trim();
//            preMakerID=l_maxList.get(6).trim();
//            break;
//            
//            case "StudentPayment":
//            l_studentID=l_body.getString("studentID");
//            String l_paymentID=l_body.getString("paymentID");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_PAYMENT",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_studentID)&&rec.get(1).trim().equals(l_paymentID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()));
//             if(!(l_viewcollect.isEmpty())){
//            Iterator iterator29=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator30=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator29.hasNext()&&iterator30.hasNext()){
//            filterkey_dummy= (String)iterator29.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>studentPayment_list_for_filter=iterator30.next();  
//            maxversion_dummy= studentPayment_list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(11).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            studentPayment_list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(11).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_studentID+l_paymentID); 
//            preRecordStatus=l_maxList.get(9).trim();
//            preAuthStatus=l_maxList.get(10).trim();
//            preMakerID=l_maxList.get(5).trim();
//            break;
//            
//            case "StudentOtherActivity":
//            l_studentID=l_body.getString("studentID");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_OTHER_ACTIVITY",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_studentID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
//             if(!(l_viewcollect.isEmpty())){
//            Iterator iterator31=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator32=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator31.hasNext()&&iterator32.hasNext()){
//            filterkey_dummy= (String)iterator31.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>studentActivity_list_for_filter=iterator32.next();  
//            maxversion_dummy= studentActivity_list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(13).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            studentActivity_list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(13).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_studentID); 
//            preRecordStatus=l_maxList.get(11).trim();
//            preAuthStatus=l_maxList.get(12).trim();
//            preMakerID=l_maxList.get(7).trim();
//            break;
//            
//            case "Payroll":
//            l_teacherID=l_body.getString("teacherID");
//            l_year=l_body.getString("year");
//            l_month=l_body.getString("month");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_PAYROLL",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_teacherID)&&rec.get(1).trim().equals(l_year)&&rec.get(2).trim().equals(l_month)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()+rec.get(2).trim())); 
//             if(!(l_viewcollect.isEmpty())){
//            Iterator iterator33=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator34=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator33.hasNext()&&iterator34.hasNext()){
//            filterkey_dummy= (String)iterator33.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>payroll_list_for_filter=iterator34.next();  
//            maxversion_dummy= payroll_list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(10).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            payroll_list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(10).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_teacherID+l_year+l_month); 
//            preRecordStatus=l_maxList.get(8).trim();
//            preAuthStatus=l_maxList.get(9).trim();
//            preMakerID=l_maxList.get(4).trim();
//            break;
//            
//            case "Notification":
//            String l_notificationID=l_body.getString("notificationID");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"NOTIFICATION"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_notificationID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_notificationID,"NOTIFICATION","NOTIFICATION_MASTER",session);        
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_notificationID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
//             if(!(l_viewcollect.isEmpty())){
//            Iterator iterator35=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator36=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator35.hasNext()&&iterator36.hasNext()){
//            filterkey_dummy= (String)iterator35.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>notification_list_for_filter=iterator36.next();  
//            maxversion_dummy= notification_list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(15).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            notification_list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(15).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_notificationID); 
//            preRecordStatus=l_maxList.get(13).trim();
//            preAuthStatus=l_maxList.get(14).trim();
//            preMakerID=l_maxList.get(9).trim();
//            break;
//            
//            case "ClassExamSchedule" :
//            l_standard=l_body.getString("standard");
//            l_section=l_body.getString("section");
//            l_exam=l_body.getString("exam");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS","CLASS_EXAM_SCHEDULE_MASTER",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_standard)&&rec.get(1).trim().equals(l_section)&&rec.get(2).trim().equals(l_exam)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()+rec.get(2).trim()));
//             if(!(l_viewcollect.isEmpty())){
//            Iterator iterator37=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator38=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator37.hasNext()&&iterator38.hasNext()){
//            filterkey_dummy= (String)iterator37.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>list_for_filter=iterator38.next();  
//            maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(9).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(9).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_standard+l_section+l_exam); 
//            preRecordStatus=l_maxList.get(7).trim();
//            preAuthStatus=l_maxList.get(8).trim();
//            preMakerID=l_maxList.get(3).trim();
//            break;
//            
//            case "StudentCalender":
//            l_studentID=l_body.getString("studentID"); 
//            l_year=l_body.getString("year");
//            l_month=l_body.getString("month");
//            l_date=l_body.getString("date");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_CALENDER",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_studentID)&&rec.get(1).trim().equals(l_year)&&rec.get(2).trim().equals(l_month)&&rec.get(3).trim().equals(l_date)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()+rec.get(2).trim()+rec.get(3).trim()));
//             if(!(l_viewcollect.isEmpty())){
//            Iterator iterator39=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator40=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator39.hasNext()&&iterator40.hasNext()){
//            filterkey_dummy= (String)iterator39.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>list_for_filter=iterator40.next();  
//            maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(11).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(11).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_studentID+l_year+l_month+l_date); 
//            preRecordStatus=l_maxList.get(9).trim();
//            preAuthStatus=l_maxList.get(10).trim();
//            preMakerID=l_maxList.get(5).trim();
//            break;
//            
//            case "StudentExamSchedule":
//            l_studentID=l_body.getString("studentID"); 
//            l_exam=l_body.getString("exam");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_EXAM_SCHEDULE_MASTER",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_studentID)&&rec.get(1).trim().equals(l_exam)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()));
//             if(!(l_viewcollect.isEmpty())){
//            Iterator iterator41=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator42=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator41.hasNext()&&iterator42.hasNext()){
//            filterkey_dummy= (String)iterator41.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>list_for_filter=iterator42.next();  
//            maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(8).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(8).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_studentID+l_exam); 
//            preRecordStatus=l_maxList.get(6).trim();
//            preAuthStatus=l_maxList.get(7).trim();
//            preMakerID=l_maxList.get(2).trim();
//            break;
//            
//            case "StudentFeeManagement":
//            l_studentID=l_body.getString("studentID"); 
//            l_feeID=l_body.getString("feeID");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_FEE_MANAGEMENT",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_studentID)&&rec.get(1).trim().equals(l_feeID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()));
//             if(!(l_viewcollect.isEmpty())){
//            Iterator iterator43=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator44=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator43.hasNext()&&iterator44.hasNext()){
//            filterkey_dummy= (String)iterator43.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>list_for_filter=iterator44.next();  
//            maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(12).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(12).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_studentID+l_feeID); 
//            preRecordStatus=l_maxList.get(10).trim();
//            preAuthStatus=l_maxList.get(11).trim();
//            preMakerID=l_maxList.get(6).trim();
//            break;
//            
//            case "StudentTimeTable":
//            l_studentID=l_body.getString("studentID");    
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID,"STUDENT","SVW_STUDENT_TIMETABLE_MASTER",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_studentID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
//             if(!(l_viewcollect.isEmpty())){
//            Iterator iterator45=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator46=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator45.hasNext()&&iterator46.hasNext()){
//            filterkey_dummy= (String)iterator45.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>list_for_filter=iterator46.next();  
//            maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(7).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(7).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_studentID); 
//            preRecordStatus=l_maxList.get(5).trim();
//            preAuthStatus=l_maxList.get(6).trim();
//            preMakerID=l_maxList.get(1).trim();
//            break;
//            
//            case "StudentMaster":
//             dbg("inside business Service--->buildPreviousAudit--->Service--->StudentMaster");  
//             
//            l_studentID=l_body.getString("studentID");
//            dbg("studentID"+l_studentID);
//            String[] l_pkey={l_studentID};
//               DBRecord dbrec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STUDENT_MASTER", l_pkey, session,dbSession);
////            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STUDENT_MASTER",session);
////            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_studentID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
////             if(!(l_viewcollect.isEmpty())){
////            Iterator iterator47=l_viewcollect.keySet().iterator();
////            Iterator<List<ArrayList<String>>> iterator48=l_viewcollect.values().iterator();
////            filtermap_dummy=new HashMap();
////            while(iterator47.hasNext()&&iterator48.hasNext()){
////            filterkey_dummy= (String)iterator47.next();
////            dbg(filterkey_dummy);
////            List<ArrayList<String>>list_for_filter=iterator48.next();  
////            maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(10).trim())).max().getAsInt();
////            dbg("maxversion"+maxversion_dummy);   
////            list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(10).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
////            }
////            }else{
////             errorHandler.log_app_error("BS_VAL_013", null);
////             throw new BSValidationException("BSValidationException");
////            }
////            l_maxList=filtermap_dummy.get(l_studentID); 
//               ExistingAudit exAudit=new ExistingAudit();
//               exAudit.setAuthStatus(dbrec.getRecord().get(9).trim());
//               exAudit.setMakerID(dbrec.getRecord().get(4).trim());
//               exAudit.setRecordStatus(dbrec.getRecord().get(8).trim());
//               exAudit.setVersionNumber(Integer.parseInt(dbrec.getRecord().get(10).trim()));
////            preRecordStatus=dbrec.getRecord().get(8).trim();
////            preAuthStatus=dbrec.getRecord().get(9).trim();
////            preMakerID=dbrec.getRecord().get(4).trim();
////            maxversion_dummy=Integer.parseInt(dbrec.getRecord().get(10).trim());
////            dbg("previous RecordStatus"+preRecordStatus);
////            dbg("preAuthStatus"+preAuthStatus);
////            dbg("preMakerID"+preMakerID);
////            dbg("maxversion_dummy"+maxversion_dummy);
//            break;
//            
//            case "TeacherMaster":
//            l_teacherID= l_body.getString("teacherID");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_TEACHER_MASTER",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_teacherID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
//             if(!(l_viewcollect.isEmpty())){
//            Iterator iterator49=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator50=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator49.hasNext()&&iterator50.hasNext()){
//            filterkey_dummy= (String)iterator49.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>list_for_filter=iterator50.next();  
//            maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(11).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(11).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_teacherID); 
//            preRecordStatus=l_maxList.get(9).trim();
//            preAuthStatus=l_maxList.get(10).trim();
//            preMakerID=l_maxList.get(5).trim();
//            break;
//            
//            case "ClassStudentMapping":
//            l_studentID=l_body.getString("studentID"); 
//            l_standard=l_body.getString("standard");
//            l_section=l_body.getString("section");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS","CLASS_STUDENT_MAPPING",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_standard)&&rec.get(1).trim().equals(l_section)&&rec.get(2).trim().equals(l_studentID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()+rec.get(2).trim()));
//             if(!(l_viewcollect.isEmpty())){
//            Iterator iterator51=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator52=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator51.hasNext()&&iterator52.hasNext()){
//            filterkey_dummy= (String)iterator51.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>list_for_filter=iterator52.next();  
//            maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(9).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(9).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_standard+l_section+l_studentID); 
//            
//            preRecordStatus=l_maxList.get(7).trim();
//            preAuthStatus=l_maxList.get(8).trim();
//            preMakerID=l_maxList.get(3).trim();
//                        break;
//            
//            case "TeacherCalender":
//            l_teacherID=l_body.getString("teacherID"); 
//            l_year=l_body.getString("year");
//            l_month=l_body.getString("month");
//            l_date=l_body.getString("date");
//            l_viewMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER","TVW_TEACHER_CALENDER",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_teacherID)&&rec.get(1).trim().equals(l_year)&&rec.get(2).trim().equals(l_month)&&rec.get(3).trim().equals(l_date)).collect(Collectors.groupingBy(rec->rec.get(0).trim()+rec.get(1).trim()+rec.get(2).trim()+rec.get(3).trim()));
//            if(!(l_viewcollect.isEmpty())){
//            Iterator iterator53=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator54=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator53.hasNext()&&iterator54.hasNext()){
//            filterkey_dummy= (String)iterator53.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>list_for_filter=iterator54.next();  
//            maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(11).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(11).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_teacherID+l_year+l_month+l_date); 
//            preRecordStatus=l_maxList.get(9).trim();
//            preAuthStatus=l_maxList.get(10).trim();
//            preMakerID=l_maxList.get(5).trim();
//            break;
//            
//            case "UserProfile":
//            String l_userID=l_body.getString("userID");
//            String[] l_userpkey={l_userID};
//             DBRecord userRecord=  readBuffer.readRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_PROFILE", l_userpkey, session, dbSession);
////            l_viewMap=dbrs.readTable("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_PROFILE",session);
////            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_userID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
////            if(!(l_viewcollect.isEmpty())){
////            Iterator iterator55=l_viewcollect.keySet().iterator();
////            Iterator<List<ArrayList<String>>> iterator56=l_viewcollect.values().iterator();
////            filtermap_dummy=new HashMap();
////            while(iterator55.hasNext()&&iterator56.hasNext()){
////            filterkey_dummy= (String)iterator55.next();
////            dbg(filterkey_dummy);
////            List<ArrayList<String>>list_for_filter=iterator56.next();  
////            maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(10).trim())).max().getAsInt();
////            dbg("maxversion"+maxversion_dummy);   
////            list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(10).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
////            }
////            }else{
////             errorHandler.log_app_error("BS_VAL_013", null);
////             throw new BSValidationException("BSValidationException");
////            }
//            preRecordStatus=userRecord.getRecord().get(8).trim();
//            preAuthStatus=userRecord.getRecord().get(9).trim();
//            preMakerID=userRecord.getRecord().get(4).trim();
//            maxversion_dummy=Integer.parseInt(userRecord.getRecord().get(10).trim());
//            break;
//            
//            case "UserRole":
//            String l_roleID=l_body.getString("roleID");
//            l_viewMap=dbrs.readTable("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_ROLE_MASTER",session);
//            l_viewcollect=l_viewMap.values().stream().filter(rec->rec.get(0).trim().equals(l_roleID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
//            if(!(l_viewcollect.isEmpty())){
//            Iterator iterator57=l_viewcollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator58=l_viewcollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator57.hasNext()&&iterator58.hasNext()){
//            filterkey_dummy= (String)iterator57.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>list_for_filter=iterator58.next();  
//            maxversion_dummy= list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(8).trim())).max().getAsInt();
//            dbg("maxversion"+maxversion_dummy);   
//            list_for_filter.stream().filter(rec->Integer.parseInt(rec.get(8).trim())==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));
//            }
//            }else{
//             errorHandler.log_app_error("BS_VAL_013", null);
//             throw new BSValidationException("BSValidationException");
//            }
//            l_maxList=filtermap_dummy.get(l_roleID); 
//            preRecordStatus=l_maxList.get(6).trim();
//            preAuthStatus=l_maxList.get(7).trim();
//            preMakerID=l_maxList.get(2).trim();
//
//            break;
//            /*  case "TeacherTimeTable":
//                
//                 l_tableName="TVW_TEACHER_TIMETABLE_MASTER";
//                 l_fileType="TEACHER";
//                 l_teacherID= l_body.getString("teacherID");
//                 l_fileName=l_teacherID;
//                 l_dummyPK=l_teacherID;
//                 break;
//            case "StudentTimeTable":
//                l_tableName="CLASS_TIMETABLE_MASTER";
//                l_fileType="CLASS";
//                l_standard=l_body.getString("standard");
//                l_section=l_body.getString("section");
//                l_fileName=l_standard+l_section;
//                l_dummyPK=l_standard+"~"+l_section;
//                break;
//            case "TeacherProfile":
//                l_tableName="TVW_TEACHER_PROFILE";
//                l_fileType="TEACHER";
//                l_teacherID= l_body.getString("teacherID");
//                l_fileName=l_teacherID;
//                l_dummyPK=l_teacherID;
//                break;
//            case "StudentProfile":
//                l_tableName="SVW_STUDENT_PROFILE";
//                l_fileType="STUDENT";
//                l_studentID=l_body.getString("studentID");
//                l_fileName=l_studentID;
//                l_dummyPK=l_studentID;
//                break;
//            case "TeacherAttendance":
//               l_tableName="TVW_TEACHER_ATTENDANCE_MASTER";
//               l_fileType="TEACHER";
//               l_teacherID= l_body.getString("teacherID");
//               l_date=l_body.getString("date");
//               l_fileName=l_teacherID;
//               l_dummyPK=l_teacherID+"~"+l_date;
//               break;
//            case "StudentAttendance":
//                l_tableName="SVW_STUDENT_ATTENDANCE";
//                l_fileType="STUDENT";
//                l_studentID=l_body.getString("studentID");
//                l_year=l_body.getString("year");
//                l_month=l_body.getString("month");
//                l_fileName=l_studentID;
//                l_dummyPK=l_studentID+"~"+l_year+"~"+l_month;
//                break;
//            case "ClassMark":
//                l_tableName="CLASS_MARK_ENTRY";
//                l_fileType="CLASS";
//                l_standard=l_body.getString("standard");
//                l_section=l_body.getString("section");
//                l_exam=l_body.getString("exam");
//                l_subjectID=l_body.getString("subjectID");
//                l_fileName=l_standard+l_section;
//                l_dummyPK=l_standard+"~"+l_section+"~"+l_exam+"~"+l_subjectID;
//                break;
//            case "studentProgessCard":
//                l_tableName="SVW_STUDENT_PRORESS_CARD";
//                l_fileType="STUDENT";
//                l_studentID=l_body.getString("studentID");
//                l_exam=l_body.getString("exam");
//                l_fileName=l_studentID;
//                l_dummyPK=l_studentID+"~"+l_exam;
//                break;
//            case "ClassAttendance":
//                l_tableName="CLASS_ATTENDANCE_MASTER";
//                l_fileType="CLASS";
//                l_standard=l_body.getString("standard");
//                l_section=l_body.getString("section");
//                l_date=l_body.getString("date");
//                l_fileName=l_standard+l_section;
//                l_dummyPK=l_standard+"~"+l_section+"~"+l_date;
//                break;
//            case "StudentLeaveManagement":
//                l_tableName="SVW_STUDENT_LEAVE_MANAGEMENT";
//                l_fileType="STUDENT";
//                l_studentID=l_body.getString("studentID");
//                l_from=l_body.getString("from");
//                l_to=l_body.getString("to");
//                l_fileName=l_studentID;
//                l_dummyPK=l_studentID+"~"+l_from+"~"+l_to;
//                break;
//            case "TeacherLeaveManagement":
//                l_tableName="TVW_TEACHER_LEAVE_MANAGEMENT";
//                l_fileType="TEACHER";
//                l_teacherID=l_body.getString("teacherID");
//                l_from=l_body.getString("from");
//                l_to=l_body.getString("to");
//                l_fileName=l_teacherID;
//                l_dummyPK=l_teacherID+"~"+l_from+"~"+l_to;
//                break;  
//            case "ClassAssignment":
//                l_tableName="CLASS_ASSIGNMENT";
//                l_fileType="CLASS";
//                l_standard=l_body.getString("standard");
//                l_section=l_body.getString("section");
//                l_subjectID=l_body.getString("subjectID");
//                l_assignmentID=l_body.getString("assignmentID");
//                 l_fileName=l_standard+l_section;
//                l_dummyPK=l_standard+"~"+l_section+"~"+l_subjectID+"~"+l_assignmentID;
//                break;
//            case "StudentAssignment":
//                l_tableName="SVW_STUDENT_ASSIGNMENT";
//                l_fileType="STUDENT";
//                l_studentID=l_body.getString("studentID");
//                l_subjectID=l_body.getString("subjectID");
//                l_assignmentID=l_body.getString("assignmentID");
//                 l_fileName=l_studentID;
//                l_dummyPK=l_studentID+"~"+l_subjectID+"~"+l_assignmentID;
//                break;
//            case "FeeManagement":
//                l_tableName="CLASS_FEE_MANAGEMENT";
//                l_fileType="CLASS";
//                l_standard=l_body.getString("standard");
//                l_section=l_body.getString("section");
//                String l_feeID=l_body.getString("feeID");
//                l_fileName=l_standard+l_section;
//                l_dummyPK=l_standard+"~"+l_section+"~"+l_feeID;
//                break;
//            case "StudentPayment":
//                l_tableName="SVW_STUDENT_PAYMENT";
//                l_fileType="STUDENT";
//                l_studentID=l_body.getString("studentID"); 
//                String l_paymentID=l_body.getString("paymentID");
//                 l_fileName=l_studentID;
//                l_dummyPK=l_studentID+"~"+l_paymentID;
//                break;
//            case "StudentOtherActivity":
//                l_tableName="SVW_STUDENT_OTHER_ACTIVITY";
//                l_fileType="STUDENT";
//                l_studentID=l_body.getString("studentID"); 
//                 l_fileName=l_studentID;
//                l_dummyPK=l_studentID;
//                break;
//            case "Payroll":
//                l_tableName="TVW_PAYROLL";
//                l_fileType="TEACHER";
//                l_teacherID=l_body.getString("teacherID");
//                l_year=l_body.getString("year");
//                l_month=l_body.getString("month");
//                l_fileName=l_teacherID;
//                l_dummyPK=l_teacherID+"~"+l_year+"~"+l_month;
//                break;
//            case "Notification":
//                l_tableName="NOTIFICATION_MASTER";
//                l_fileType="NOTIFICATION";
//                String l_notificationID=l_body.getString("notificationID");
//                l_fileName=l_notificationID;
//                l_dummyPK=l_notificationID;
//                break;
//            case "ClassExamSchedule" :
//                l_tableName="CLASS_EXAM_SCHEDULE_MASTER";
//                l_fileType="CLASS";
//                l_standard=l_body.getString("standard");
//                l_section=l_body.getString("section");
//                l_exam=l_body.getString("exam");
//                l_fileName=l_standard+l_section;
//                l_dummyPK=l_standard+"~"+l_section+"~"+l_exam;
//                break;
//            case "StudentCalender":
//                l_tableName="SVW_STUDENT_CALENDER";
//                l_fileType="STUDENT";
//                l_studentID=l_body.getString("studentID"); 
//                 l_year=l_body.getString("year");
//                l_month=l_body.getString("month");
//                l_date=l_body.getString("date");
//                 l_fileName=l_studentID;
//                l_dummyPK=l_studentID+"~"+l_year+"~"+l_month+"~"+l_date;
//                break;
//                
//        }
//        int version_colID=mds.getColumnMetaData(l_fileType,l_tableName, "VERSION_NUMBER",p_dbdi).getI_ColumnID()-1;
//        int auth_colID=mds.getColumnMetaData(l_fileType,l_tableName, "AUTH_STATUS",p_dbdi).getI_ColumnID()-1;
//        int rec_colID=mds.getColumnMetaData(l_fileType,l_tableName, "RECORD_STATUS",p_dbdi).getI_ColumnID()-1;
//        int maker_colID=mds.getColumnMetaData(l_fileType,l_tableName, "MAKER_ID",p_dbdi).getI_ColumnID()-1;
//        
//        
//        Map<String, ArrayList<String>>l_tableMap=dbrs.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileType+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileName+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileName, l_fileType, l_tableName,p_dbdi);
//        maxversion_dummy=l_tableMap.values().stream().mapToInt(rec->Integer.parseInt(rec.get(version_colID).trim())).max().getAsInt();
//        dbg("max"+maxversion_dummy);       
//        l_primaryKey=l_dummyPK+"~"+maxversion_dummy;
//      
//        ArrayList<String> l_records=l_tableMap.get(l_primaryKey);
//        
//        
//        dbg("rec_colID"+rec_colID);
//        preRecordStatus=l_records.get(rec_colID).trim();
//        dbg("preRecordStatus"+preRecordStatus);
//        preAuthStatus=l_records.get(auth_colID).trim();
//        dbg("preAuthStatus"+preAuthStatus);
//        preMakerID=l_records.get(maker_colID).trim();
//        dbg("preMakerID"+preMakerID);*/
//        
//        }
//        
//     }
//        dbg("maxversion_dummy"+maxversion_dummy);
//        dbg("preAuthStatus"+preAuthStatus);
//        dbg("preRecordStatus"+preRecordStatus);
//        dbg("preMakerID"+preMakerID);
//        dbg("end of businessService--->build previous audit");
//     }catch(DBValidationException ex){
//      throw ex;
//     }catch(BSValidationException ex){
//        throw ex;
//     }catch(DBProcessingException ex){   
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//            
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//
//        }
//     finally
//     {
//         filtermap_dummy = null;
//         filterkey_dummy =null;
//     }  
// }    

    public ExistingAudit getExistingAudit(BusinessEJB businessejb) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {
        ExistingAudit exAudit = new ExistingAudit();
        try {
            dbg("inside BusinessService--->getExistingAudit");

            exAudit = businessejb.getExistingAudit();

            dbg("end of BusinessService--->getExistingAudit");

        } catch (DBValidationException ex) {
            throw ex;

        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        return exAudit;
    }

    public JsonObject getJsonObject(String p_request) throws BSProcessingException {

        try (InputStream is = new ByteArrayInputStream(p_request.getBytes(Charset.forName("UTF-8")));
                JsonReader jsonReader = Json.createReader(is);) {

            JsonObject jsonObject = jsonReader.readObject();

            return jsonObject;

        } catch (IOException ex) {

            throw new BSProcessingException("IOException" + ex.toString());
        }
    }

    public JsonObject cloneRequestJsonObject(JsonObject p_jsonObject) throws BSProcessingException {
        try {
            dbg("inside cloneRequestJsonObject");

            JsonObject body = replaceSpecialCharcterInRequestBody(p_jsonObject.getJsonObject("body"));
            JsonObject clonedJson;
            String service = p_jsonObject.getJsonObject("header").getString("service");

            if (service.contains("Summary")) {

                clonedJson = Json.createObjectBuilder().add("header", p_jsonObject.getJsonObject("header"))
                        .add("body", body).build();

            } else {

                clonedJson = Json.createObjectBuilder().add("header", p_jsonObject.getJsonObject("header"))
                        .add("body", body)
                        // .add("auditLog", p_jsonObject.getJsonObject("auditLog")).build();
                        .add("audit", p_jsonObject.getJsonObject("audit")).build();//Integration Fix

            }

            dbg("end of cloneRequestJsonObject");
            return clonedJson;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Parsing Exception" + ex.toString());
        }
    }

    public JsonObject replaceSpecialCharcterInRequestBody(JsonObject p_bodyObject) throws BSProcessingException {
        try {
            dbg("inside BusinessService---> replaceSpecialCharchterInRequest");
            String l_replacedBody = null;
            if (p_bodyObject.toString().contains("@")) {
                l_replacedBody = p_bodyObject.toString().replace("@", "AATT;");
                JsonObject l_replacedBodyObject = getJsonObject(l_replacedBody);
                dbg("end of BusinessService---> replaceSpecialCharchterInRequest--->p_jsonObject" + l_replacedBodyObject.toString());
                return l_replacedBodyObject;
            } else {
                return p_bodyObject;
            }

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Parsing Exception" + ex.toString());
        }

    }

    public JsonObject cloneResponseJsonObject(JsonObject p_jsonObject) throws BSProcessingException {
        try {
            dbg("inside cloneResponseJsonObject");

            String service = p_jsonObject.getJsonObject("header").getString("service");

            if (service.contains("Summary")) {

                if (!(p_jsonObject.getJsonObject("header").getString("operation").equals("Delete"))) {
                    JsonObject body = replaceSpecialCharcterInResponseBody(p_jsonObject.getJsonObject("body"));
                    JsonObject clonedJson = Json.createObjectBuilder().add("header", p_jsonObject.getJsonObject("header"))
                            .add("body", body)
                            // .add("auditLog", p_jsonObject.getJsonObject("auditLog"))
                            .add("error", p_jsonObject.getJsonArray("error")).build();

                    dbg("end of cloneResponseJsonObject");

                    return clonedJson;
                } else {
                    return p_jsonObject;
                }

            } else {

                if (!(p_jsonObject.getJsonObject("header").getString("operation").equals("Delete"))) {
                    JsonObject body = replaceSpecialCharcterInResponseBody(p_jsonObject.getJsonObject("body"));
                    JsonObject clonedJson = Json.createObjectBuilder().add("header", p_jsonObject.getJsonObject("header"))
                            .add("body", body)
                            // .add("auditLog", p_jsonObject.getJsonObject("auditLog"))
                            .add("audit", p_jsonObject.getJsonObject("audit"))//Integration changes
                            .add("error", p_jsonObject.getJsonArray("error")).build();

                    dbg("end of cloneResponseJsonObject");

                    return clonedJson;
                } else {
                    return p_jsonObject;
                }

            }

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Parsing Exception" + ex.toString());
        }
    }

    public JsonObject replaceSpecialCharcterInResponseBody(JsonObject p_bodyObject) throws BSProcessingException {
        try {
            dbg("inside BusinessService---> replaceSpecialCharcterInResponseBody");
            String l_replacedBody = null;
            if (p_bodyObject.toString().contains("AATT;")) {

                l_replacedBody = p_bodyObject.toString().replace("AATT;", "@");

                JsonObject l_replacedBodyObject = getJsonObject(l_replacedBody);
                dbg("end of BusinessService---> replaceSpecialCharcterInResponseBody--->p_jsonObject" + l_replacedBodyObject.toString());
                return l_replacedBodyObject;
            } else {

                return p_bodyObject;
            }

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Parsing Exception" + ex.toString());
        }

    }

    public JsonObject buildSuccessResponse(JsonObject requestJson, Request request, AppDependencyInjection inject, CohesiveSession session, BusinessEJB businessejb) throws BSProcessingException {
        JsonObjectBuilder jsonResponse = Json.createObjectBuilder();
        BusinessService bs = inject.getBusinessService(session);
        try {
            dbg("inside BusinessService--->buildSuccessResponse");
            dbg("BusinessService--->buildSuccessResponse--->I/P--->requestJson" + requestJson);
            String l_msgID;
            String currentDate = bs.getCurrentDate();
            l_msgID = request.getReqHeader().getMsgID();
            IDBTransactionService dbts = inject.getDBTransactionService();
            IDBReadBufferService readBuffer = inject.getDBReadBufferService();
            String l_service = request.getReqHeader().getService();
            String l_operation = request.getReqHeader().getOperation();
            Map<String, String> l_businessEntityMap = request.getReqHeader().getBusinessEntity();
            JsonArrayBuilder l_businessEntityBuilder = Json.createArrayBuilder();
            Iterator<String> keyIterator = l_businessEntityMap.keySet().iterator();
            Iterator<String> valueIterator = l_businessEntityMap.values().iterator();
            while (keyIterator.hasNext() && valueIterator.hasNext()) {
                String l_entityName = keyIterator.next();
                String l_entityValue = valueIterator.next();

                l_businessEntityBuilder.add(Json.createObjectBuilder().add("entityName", l_entityName)
                        .add("entityValue", l_entityValue));
            }
            String l_instituteID = request.getReqHeader().getInstituteID();
            String l_userID = request.getReqHeader().getUserID();
            String l_source = request.getReqHeader().getSource();
            UUID r_msgID = CohesiveSession.dataToUUID("messageout");
            JsonArray errorArray = Json.createArrayBuilder().add(Json.createObjectBuilder().add("errorCode", "SUCCESS")
                    .add("errorMessage", "SUCCESSFULLY PROCESSED")).build();

            JsonObject r_JsonBody = getJsonBody(requestJson, businessejb);
            JsonObject l_auditLog;//Integration changes start
            if (!request.getReqHeader().getService().contains("Search") && !request.getReqHeader().getService().contains("SelectBoxMaster") && !request.getReqHeader().getService().contains("Summary")) {
                l_auditLog = bs.buildAuditLogJson(request);
            } else {
                l_auditLog = Json.createObjectBuilder().build();
            }
            //Integration changes end
            if (l_operation.equals("Delete")) {
                jsonResponse = Json.createObjectBuilder().add("header", Json.createObjectBuilder()
                        .add("msgID", r_msgID.toString())
                        .add("correlationID", l_msgID)
                        .add("service", l_service)
                        .add("operation", l_operation)
                        .add("businessEntity", l_businessEntityBuilder)
                        .add("instituteID", l_instituteID)
                        .add("userID", l_userID)
                        .add("source", l_source)
                        .add("status", "success"))
                        .addNull("body")
                        // .add("auditLog", l_auditLog)
                        .add("audit", l_auditLog) //Integratio change
                        .add("error", errorArray);

            } else {
                jsonResponse = Json.createObjectBuilder().add("header", Json.createObjectBuilder()
                        .add("msgID", r_msgID.toString())
                        .add("correlationID", l_msgID)
                        .add("service", l_service)
                        .add("operation", l_operation)
                        .add("businessEntity", l_businessEntityBuilder)
                        .add("instituteID", l_instituteID)
                        .add("userID", l_userID)
                        .add("source", l_source)
                        .add("status", "success"))
                        .add("body", r_JsonBody)
                        //.add("auditLog", l_auditLog)
                        .add("audit", l_auditLog)//Integration change
                        .add("error", errorArray);

            }

            if (l_operation.equals("Create") || l_operation.equals("Modify") || l_operation.equals("Delete")) {

                if (request.getReqAudit().getAuthStatus().equals("U")) {

                    String servicePkey = getPrimaryKeyOfTheService(request);
                    String entity = getEntityNameandValue(request);
                    String entityName = entity.split("~")[0];
                    String entityValue = entity.split("~")[1];
                    boolean recordExistence = false;
                    String[] l_pkey = {l_service, servicePkey};
                    DBSession dbSession = null;
                    try {

                        dbSession = new DBSession(session);
                        dbSession.createDBsession(session);
                        readBuffer.readRecord("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "UnAuth_" + currentDate, "INSTITUTE", "IVW_UNAUTH_RECORDS", l_pkey, session, dbSession);

                        recordExistence = true;

                    } catch (DBValidationException ex) {

                        if (ex.toString().contains("DB_VAL_000") || ex.toString().contains("DB_VAL_011")) {
                            recordExistence = false;
                            session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                            session.getErrorhandler().removeSessionErrCode("DB_VAL_011");

                        } else {
                            throw ex;
                        }

                    } finally {
                        dbSession.clearSessionObject();
                    }

                    if (!recordExistence) {

                        dbts.createRecord(session, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "UnAuth_" + currentDate, "INSTITUTE", 320, l_service, l_operation, entityName, entityValue, servicePkey);

                    } else if (l_operation.equals("Delete") && request.getReqAudit().getAuthStatus().equals("U") && request.getReqAudit().getVersionNumber().equals(1)) {
                        dbts.deleteRecord("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "UnAuth_" + currentDate, "INSTITUTE", "IVW_UNAUTH_RECORDS", l_pkey, session);

                    }
                }

            } else if (l_operation.equals("Auth") || l_operation.equals("Reject")) {

                String servicePkey = getPrimaryKeyOfTheService(request);

                String[] l_pkey = {l_service, servicePkey};

                boolean recordExistence = false;
                DBSession dbSession = null;
                try {

                    //DBSession dbSession=new DBSession(session);
                    dbSession = new DBSession(session);
                    dbSession.createDBsession(session);

                    readBuffer.readRecord("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "UnAuth_" + currentDate, "INSTITUTE", "IVW_UNAUTH_RECORDS", l_pkey, session, dbSession);

                    recordExistence = true;

                } catch (DBValidationException ex) {

                    if (ex.toString().contains("DB_VAL_000") || ex.toString().contains("DB_VAL_011")) {
                        recordExistence = false;
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");

                    } else {
                        throw ex;
                    }

                } finally {
                    dbSession.clearSessionObject();
                }

                if (recordExistence) {

                    dbts.deleteRecord("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "UnAuth_" + currentDate, "INSTITUTE", "IVW_UNAUTH_RECORDS", l_pkey, session);

                }

            }

            dbg("BusinessService--->buildSuccessResponse--->O/P--->jsonResponse" + jsonResponse);
            dbg("End of BusinessService--->buildSuccessResponse");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        return jsonResponse.build();
    }

    private String getEntityNameandValue(Request request) throws BSProcessingException {

        try {
            dbg("inside getEntityNameandValue");

            Map<String, String> l_businessEntityMap = request.getReqHeader().getBusinessEntity();
            Iterator<String> keyIterator = l_businessEntityMap.keySet().iterator();
            StringBuffer l_entityName = new StringBuffer();
            StringBuffer l_entityValue = new StringBuffer();
            while (keyIterator.hasNext()) {
                String entityName = keyIterator.next();

                l_entityName = l_entityName.append(entityName).append(";");

                String entityValue = l_businessEntityMap.get(entityName);

                l_entityValue = l_entityValue.append(entityValue).append(";");
            }

            String entityNameAndValue = l_entityName + "~" + l_entityValue;

            dbg("end of getEntityNameandValue-->entityNameAndValue" + entityNameAndValue);

            return entityNameAndValue;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    private String getPrimaryKeyOfTheService(Request request) throws BSProcessingException {

        String pkey = new String();

        try {

            String service = request.getReqHeader().getService();
            dbg("service" + service);

            switch (service) {

                case "ClassAttendance":
                    RequestBody<ClassAttendance> classAttendanceBody = request.getReqBody();
                    ClassAttendance classAttendance = classAttendanceBody.get();
                    String l_standard = classAttendance.getStandard();
                    String l_section = classAttendance.getSection();
                    String l_date = classAttendance.getDate();

                    pkey = l_standard + ";" + l_section + ";" + l_date;

                    break;
                case "ClassExamSchedule":
                    RequestBody<ClassExamSchedule> ClassExamScheduleBody = request.getReqBody();
                    ClassExamSchedule classExamSchedule = ClassExamScheduleBody.get();
                    l_standard = classExamSchedule.getStandard();
                    l_section = classExamSchedule.getSection();
                    String l_exam = classExamSchedule.getExam();

                    pkey = l_standard + ";" + l_section + ";" + l_exam;

                    break;
                case "ClassMark":
                    RequestBody<ClassMark> ClassMarkBody = request.getReqBody();
                    ClassMark classMark = ClassMarkBody.get();
                    l_standard = classMark.getStandard();
                    l_section = classMark.getSection();
                    l_exam = classMark.getExam();
                    String l_subjectID = classMark.getSubjectID();

                    pkey = l_standard + ";" + l_section + ";" + l_exam + ";" + l_subjectID;

                    break;
                case "ClassTimeTable":
                    RequestBody<ClassTimeTable> ClassTimeTableBody = request.getReqBody();
                    ClassTimeTable classTimeTable = ClassTimeTableBody.get();
                    l_standard = classTimeTable.getStandard();
                    l_section = classTimeTable.getSection();

                    pkey = l_standard + ";" + l_section;

                    break;

                case "ClassLevelConfiguration":
                    RequestBody<ClassLevelConfiguration> ClassLevelConfigurationBody = request.getReqBody();
                    ClassLevelConfiguration classLevelConfig = ClassLevelConfigurationBody.get();
                    String l_instituteID = classLevelConfig.getInstituteID();
                    l_standard = classLevelConfig.getStandard();
                    l_section = classLevelConfig.getSection();

                    pkey = l_instituteID + ";" + l_standard + ";" + l_section;

                    break;
                case "ECircular":
                    RequestBody<ECircular> ECircularBody = request.getReqBody();
                    ECircular eCircular = ECircularBody.get();
                    l_instituteID = eCircular.getInstituteID();
                    String eCircularID = eCircular.getE_circularID();

                    pkey = l_instituteID + ";" + eCircularID;

                    break;

                case "GeneralLevelConfiguration":
                    RequestBody<GeneralLevelConfiguration> GeneralLevelConfigurationBody = request.getReqBody();
                    GeneralLevelConfiguration generalConfiguration = GeneralLevelConfigurationBody.get();
                    l_instituteID = generalConfiguration.getInstituteID();

                    pkey = l_instituteID;

                    break;

                case "GroupMapping":
                    RequestBody<GroupMapping> GroupMappingBody = request.getReqBody();
                    GroupMapping groupMapping = GroupMappingBody.get();
                    String l_groupID = groupMapping.getGroupID();

                    pkey = l_groupID;

                    break;
                case "HolidayMaintenance":
                    RequestBody<HolidayMaintanence> HolidayMaintenanceBody = request.getReqBody();
                    HolidayMaintanence Holiday = HolidayMaintenanceBody.get();
                    l_instituteID = Holiday.getInstituteID();
                    String year = Holiday.getYear();
                    String month = Holiday.getMonth();

                    pkey = l_instituteID + ";" + year + ";" + month;

                    break;

                case "InstituteAssignment":
                    RequestBody<InstituteAssignment> InstituteAssignmentBody = request.getReqBody();
                    InstituteAssignment assignment = InstituteAssignmentBody.get();
                    l_instituteID = assignment.getInstituteID();
                    String assignmentID = assignment.getAssignmentID();

                    pkey = l_instituteID + ";" + assignmentID;

                    break;

                case "InstituteFeeManagement":
                    RequestBody<InstituteFeeManagement> InstituteFeeManagementBody = request.getReqBody();
                    InstituteFeeManagement fee = InstituteFeeManagementBody.get();
                    l_instituteID = fee.getInstituteID();
                    String feeID = fee.getFeeID();

                    pkey = l_instituteID + ";" + feeID;

                    break;

                case "InstituteOtherActivity":
                    RequestBody<InstituteOtherActivity> InstituteOtherActivityBody = request.getReqBody();
                    InstituteOtherActivity otherActivity = InstituteOtherActivityBody.get();
                    l_instituteID = otherActivity.getInstituteID();
                    String activityID = otherActivity.getActivityID();

                    pkey = l_instituteID + ";" + activityID;

                    break;

                case "InstitutePayment":
                    RequestBody<InstitutePayment> InstitutePaymentBody = request.getReqBody();
                    InstitutePayment payment = InstitutePaymentBody.get();
                    l_instituteID = payment.getInstituteID();
                    String paymentID = payment.getPaymentID();
                    ;

                    pkey = l_instituteID + ";" + paymentID;

                    break;

                case "Notification":
                    RequestBody<Notification> notificationBody = request.getReqBody();
                    Notification notification = notificationBody.get();
                    l_instituteID = notification.getInstituteID();
                    String notificationID = notification.getNotificationID();
                    ;

                    pkey = l_instituteID + ";" + notificationID;

                    break;
                case "StudentLeaveManagement":
                    RequestBody<StudentLeaveManagement> StudentLeaveManagementBody = request.getReqBody();
                    StudentLeaveManagement studentLeave = StudentLeaveManagementBody.get();
                    String l_studentID = studentLeave.getStudentID();
                    String l_from = studentLeave.getFrom();
                    String l_to = studentLeave.getTo();

                    pkey = l_studentID + ";" + l_from + ";" + l_to;

                    break;
                case "StudentOtherActivity":
                    RequestBody<StudentOtherActivity> StudentOtherActivityBody = request.getReqBody();
                    StudentOtherActivity studentOtherActivity = StudentOtherActivityBody.get();
                    l_studentID = studentOtherActivity.getStudentID();
                    activityID = studentOtherActivity.getActivityID();

                    pkey = l_studentID + ";" + activityID;

                    break;
                case "StudentProfile":
                    RequestBody<StudentProfile> StudentProfileBody = request.getReqBody();
                    StudentProfile studentProfile = StudentProfileBody.get();
                    l_studentID = studentProfile.getStudentID();

                    pkey = l_studentID;

                    break;
                case "TeacherLeaveManagement":
                    RequestBody<TeacherLeaveManagement> TeacherLeaveManagementBody = request.getReqBody();
                    TeacherLeaveManagement TeacherLeave = TeacherLeaveManagementBody.get();
                    String l_TeacherID = TeacherLeave.getTeacherID();
                    l_from = TeacherLeave.getFrom();
                    l_to = TeacherLeave.getTo();

                    pkey = l_TeacherID + ";" + l_from + ";" + l_to;

                    break;
                case "TeacherProfile":
                    RequestBody<TeacherProfile> TeacherProfileBody = request.getReqBody();
                    TeacherProfile TeacherProfile = TeacherProfileBody.get();
                    l_TeacherID = TeacherProfile.getTeacherID();

                    pkey = l_TeacherID;

                    break;
                case "UserProfile":
                    RequestBody<UserProfile> UserProfileBody = request.getReqBody();
                    UserProfile userProfile = UserProfileBody.get();
                    String l_userID = userProfile.getUserID();

                    pkey = l_userID;

                    break;
                case "UserRole":
                    RequestBody<UserRole> UserRoleBody = request.getReqBody();
                    UserRole UserRole = UserRoleBody.get();
                    String l_roleID = UserRole.getRoleID();

                    pkey = l_roleID;

                    break;
                case "ClassSoftSkill":
                    RequestBody<ClassSoftSkill> ClassSoftSkillBody = request.getReqBody();
                    ClassSoftSkill classSoftSkill = ClassSoftSkillBody.get();
                    l_standard = classSoftSkill.getStandard();
                    l_section = classSoftSkill.getSection();
                    l_exam = classSoftSkill.getExam();
                    String l_skillID = classSoftSkill.getSkillID();

                    pkey = l_standard + ";" + l_section + ";" + l_exam + ";" + l_skillID;

                    break;
            }

            dbg("pkey-->" + pkey);
            dbg("end of getPrimaryKeyOfTheService");
            return pkey;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }

    private JsonObject getJsonBody(JsonObject p_jsonObject, BusinessEJB businessejb) throws BSProcessingException {
        JsonObject l_jsonBody;
        String l_operation;
        try {
            dbg("BusinessService--->getJsonBody");
            dbg("BusinessService--->getJsonBody--->I/P--->p_jsonObject" + p_jsonObject.toString());
            JsonObject header = p_jsonObject.getJsonObject("header");
            l_jsonBody = p_jsonObject.getJsonObject("body");
            l_operation = header.getString("operation");

            if (l_operation.equals("View") || l_operation.equals("Create-Default")|| l_operation.equals("Payment-Default")) {

                JsonObject l_jsonfromBO = buildJsonfromBO(businessejb);
                return l_jsonfromBO;
            }
            dbg("BusinessService--->getJsonBody--->O/P--->l_jsonBody");
            dbg("Inside BusinessService--->getJsonBody");
        } catch (BSProcessingException ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        return l_jsonBody;
    }

    private JsonObject buildJsonfromBO(BusinessEJB businessejb) throws BSProcessingException {
        JsonObject body = null;
        try {
            dbg("inside BusinessService--->buildJsonFromBO");

            body = businessejb.buildJsonResFromBO();

            dbg("end of  BusinessService--->buildJsonFromBO");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        return body;
    }

    public void businessServiceProcssing(Request request, ExistingAudit exAudit, AppDependencyInjection inject, BusinessEJB businessejb) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {

        try {
            dbg("inside businessServiceProcssing");
            String l_operation = request.getReqHeader().getOperation();
            int l_versionNumber = 0;
            if (!(l_operation.equals("View"))) {
                l_versionNumber = Integer.parseInt(request.getReqAudit().getVersionNumber());
                dbg("l_versionNumber" + l_versionNumber);
            }

            dbg("l_operation" + l_operation);

            int existingVersion = 0;
            if (!(l_operation.equals("Create")) && !(l_operation.equals("View")) && !(l_operation.equals("Create-Default")) && !(l_operation.equals("Payment-Default"))) {

                if (!(l_operation.equals("AutoAuth") && l_versionNumber == 1)) {

                    existingVersion = exAudit.getVersionNumber();
                    dbg("existingVersion" + existingVersion);
                }

            }

            switch (l_operation) {

                case "Create":

                    businessejb.create();

                    if (request.getAutoAuthAccess().equals("Y")) {

                        businessejb.relationshipProcessing();
                    }
//                      businessejb.relationshipProcessing();
                    break;

                case "Auth":
                    businessejb.relationshipProcessing();
                    businessejb.authUpdate();
                    break;

                case "Delete":
                    if (existingVersion == l_versionNumber) {

                        businessejb.delete();
//                       businessejb.relationshipProcessing();

                    } else if (existingVersion + 1 == l_versionNumber) {
                        businessejb.create();

                    }

                    if (request.getAutoAuthAccess().equals("Y")) {

                        businessejb.relationshipProcessing();
                    }

                    break;

                case "View":

                    businessejb.view();
                    break;

                case "Modify":

                    if (existingVersion == l_versionNumber) {

                        businessejb.fullUpdate();

                    }
                    if ((existingVersion + 1) == l_versionNumber) {

                        businessejb.create();

                    }

                    if (request.getAutoAuthAccess().equals("Y")) {

                        businessejb.relationshipProcessing();
                    }

                    break;
                case "Reject":
                    businessejb.authUpdate();
                    break;
                case "AutoAuth":
                    businessejb.create();
                    break;
//                   case "Create-Default": 
//                       dbg("Inside business service processing--->CreateDefault");
//                       businessejb.createDefault();
//                       break;

            }

            dbg("end of businessServiceProcssing");
        } catch (BSValidationException ex) {
            throw ex;
        } catch (DBValidationException ex) {
            throw ex;

        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (BSProcessingException ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public ConvertedDate getYearMonthandDay(String p_date) throws BSProcessingException {

        try {
            dbg("inside BusinessService--->getYearMonthandDay");
            dbg("p_date" + p_date);
            String dateFormat = i_db_properties.getProperty("DATE_FORMAT");
            Date date1 = new SimpleDateFormat(dateFormat).parse(p_date);
            LocalDate localDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int month = localDate.getMonthValue();
            int year = localDate.getYear();
            int day = localDate.getDayOfMonth();

            ConvertedDate convertedDate = new ConvertedDate();
            convertedDate.setYear(Integer.toString(year));
            convertedDate.setMonth(Integer.toString(month));
            convertedDate.setDay(Integer.toString(day));

            dbg("year" + convertedDate.getYear());
            dbg("month" + convertedDate.getMonth());
            dbg("day" + convertedDate.getDay());

            dbg("end of BusinessService--->getYearMonthandDay");
            return convertedDate;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public String getDateFromDateStamp(String p_dateStamp) throws BSProcessingException {

        try {
            dbg("inside BusinessService--->getDateFromDateStamp");
            dbg("p_date" + p_dateStamp);
            String dateFormat = i_db_properties.getProperty("DATE_FORMAT");
            Date date1 = new SimpleDateFormat(dateFormat).parse(p_dateStamp);
            String date = new SimpleDateFormat(dateFormat).format(date1);

            dbg("date" + date);

            dbg("end of BusinessService--->getDateFromDateStamp");
            return date;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public String getDate(String year, String month, String day) throws BSProcessingException {

        try {
            dbg("inside BusinessService--->getDate");
            dbg("year" + year);
            dbg("month" + month);
            dbg("day" + day);
            String dateFormat = i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.parseInt(year));
            cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date date = cal.getTime();

            String formattedDate = formatter.format(date);
            dbg("formattedDate" + formattedDate);
            dbg("end of BusinessService--->getYearMonthandDay");
            return formattedDate;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public EducationPeriod getEducationPeriod(String l_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException {

        try {
            dbg("inside BusinessService--->getEducationPeriod");
            String dateFormat = i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            IPDataService pds = inject.getPdataservice();
            String currentYearMonthDay = getCurrentYearMonth();

            String[] pkey = {l_instituteID};
            ArrayList<String> contractList = pds.readRecordPData(session, dbSession, "APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "CONTRACT_MASTER", pkey);

            String countryCode = contractList.get(13).trim();

            EducationPeriod period = new EducationPeriod();

            if (countryCode.equals("IN")) {

                String year = currentYearMonthDay.split("~")[0];
                String month = currentYearMonthDay.split("~")[1];

                if (Integer.parseInt(month) <= 5) {

                    Calendar from = Calendar.getInstance();
                    from.set(Calendar.YEAR, Integer.parseInt(year) - 1);
                    from.set(Calendar.MONTH, 6);
                    from.set(Calendar.DAY_OF_MONTH, 1);
                    Date fromDate = from.getTime();
                    String fromDateString = formatter.format(fromDate);

                    Calendar to = Calendar.getInstance();
                    to.set(Calendar.YEAR, Integer.parseInt(year));
                    to.set(Calendar.MONTH, 4);
                    to.set(Calendar.DAY_OF_MONTH, 30);
                    Date toDate = to.getTime();
                    String toDateString = formatter.format(toDate);

                    period.setFromDate(fromDateString);
                    period.setToDate(toDateString);

                } else {

                    Calendar from = Calendar.getInstance();
                    from.set(Calendar.YEAR, Integer.parseInt(year));
                    from.set(Calendar.MONTH, 6 - 1);
                    from.set(Calendar.DAY_OF_MONTH, 1);
                    Date fromDate = from.getTime();
                    String fromDateString = formatter.format(fromDate);

                    Calendar to = Calendar.getInstance();
                    to.set(Calendar.YEAR, Integer.parseInt(year) + 1);
                    to.set(Calendar.MONTH, 4 - 1);
                    to.set(Calendar.DAY_OF_MONTH, 30);
                    Date toDate = to.getTime();
                    String toDateString = formatter.format(toDate);

                    period.setFromDate(fromDateString);
                    period.setToDate(toDateString);

                }

            }

            dbg("fromDate" + period.getFromDate());
            dbg("toDate" + period.getToDate());
            dbg("end of BusinessService--->getEducationPeriod");
            return period;
        } catch (DBValidationException ex) {
            throw ex;
//        }catch(BSValidationException ex){
//            throw ex;    
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public float getNoOfWorkingDaysInEducationYear(String l_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException {

        try {
            EducationPeriod period = getEducationPeriod(l_instituteID, session, dbSession, inject);
            String dateformat = i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
            IPDataService pds = inject.getPdataservice();
            String fromdate = period.getFromDate();
            String todate = period.getToDate();
//           ArrayList<Date>dateList=this.getLeaveDates(fromdate, todate, session, dbSession, inject);
            float workingDayCount = 0.0f;
            ConvertedDate from = this.getYearMonthandDay(fromdate);
            ConvertedDate to = this.getYearMonthandDay(todate);
            int fromYear = Integer.parseInt(from.getYear());
            int fromMonth = Integer.parseInt(from.getMonth());
            int toYear = Integer.parseInt(to.getYear());
            int toMonth = Integer.parseInt(to.getMonth());
            int iteration = 0;

            if (fromYear < toYear) {

                iteration = 12 - fromMonth + toMonth;

            } else if (fromYear == toYear) {

                iteration = toMonth - fromMonth;
            }

            int month = 0;
            int year = 0;

            for (int i = 0; i < iteration; i++) {

//               String l_date=formatter.format(dateList.get(i));
//               ConvertedDate convertedDate=this.getYearMonthandDay(l_date);
                if (i == 0) {

                    month = fromMonth;
                    year = fromYear;
                } else {

                    month = month + 1;

                    if (month > 12) {

                        month = 1;
                        year = year + 1;
                    }
                }

                String[] l_pkey = {Integer.toString(year), Integer.toString(month)};
                ArrayList<String> holidayMaintanenceRecord = null;

                holidayMaintanenceRecord = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_HOLIDAY_MAINTANENCE", l_pkey);

                if (holidayMaintanenceRecord != null) {

                    String holidayString = holidayMaintanenceRecord.get(3);
                    char[] holidayArr = holidayString.toCharArray();

                    for (int j = 0; j < holidayArr.length; j++) {

                        char holidayChar = holidayArr[j];

                        if (holidayChar == 'W') {

                            workingDayCount = workingDayCount + 1;
                        } else if (holidayChar == 'F' || holidayChar == 'A') {

                            workingDayCount = workingDayCount + 0.5f;
                        }
                    }

                }

            }

            return workingDayCount;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }

    public float getNoOfWorkingDaysTillNow(String l_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getNoOfWorkingDaysTillNow");
            EducationPeriod period = getEducationPeriod(l_instituteID, session, dbSession, inject);
            String dateformat = i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
            IPDataService pds = inject.getPdataservice();
            String fromdate = period.getFromDate();
            String todate = this.getCurrentDate();
//           ArrayList<Date>dateList=this.getLeaveDates(fromdate, todate, session, dbSession, inject);
            float workingDayCount = 0.0f;
            ConvertedDate from = this.getYearMonthandDay(fromdate);
            ConvertedDate to = this.getYearMonthandDay(todate);
            int fromYear = Integer.parseInt(from.getYear());
            int fromMonth = Integer.parseInt(from.getMonth());
            int toYear = Integer.parseInt(to.getYear());
            int toMonth = Integer.parseInt(to.getMonth());
            int iteration = 0;
            dbg("fromYear" + fromYear);
            dbg("fromMonth" + fromMonth);
            dbg("toYear" + toYear);
            dbg("toMonth" + toMonth);

            if (fromYear < toYear) {

                iteration = 12 - fromMonth + toMonth;

            } else if (fromYear == toYear) {

                iteration = toMonth - fromMonth;
            }

            dbg("iteration" + iteration);

            int month = 0;
            int year = 0;

            for (int i = 0; i < iteration; i++) {

//               String l_date=formatter.format(dateList.get(i));
//               ConvertedDate convertedDate=this.getYearMonthandDay(l_date);
                if (i == 0) {

                    month = fromMonth;
                    year = fromYear;
                } else {

                    month = month + 1;

                    if (month > 12) {

                        month = 1;
                        year = year + 1;
                    }
                }

                dbg("year-->" + year);
                dbg("month-->" + month);

                String monthString = null;

                if (month < 10) {

                    monthString = "0" + Integer.toString(month);

                } else {

                    monthString = Integer.toString(month);
                }

                String[] l_pkey = {l_instituteID, Integer.toString(year), monthString};
                ArrayList<String> holidayMaintanenceRecord = null;

                try {

                    holidayMaintanenceRecord = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_HOLIDAY_MAINTANENCE", l_pkey);

                } catch (DBValidationException ex) {
                    if (ex.toString().contains("DB_VAL_011") || ex.toString().contains("DB_VAL_000")) {
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_060", null);
                        throw new BSValidationException("BSValidationException");
                    } else {

                        throw ex;
                    }
                }

                if (holidayMaintanenceRecord != null) {

                    String holidayString = holidayMaintanenceRecord.get(3);
                    dbg("holidayString" + holidayString);
                    char[] holidayArr = holidayString.toCharArray();

                    for (int j = 0; j < holidayArr.length; j++) {

                        char holidayChar = holidayArr[j];

                        if (holidayChar == 'W') {

                            workingDayCount = workingDayCount + 1;
                        } else if (holidayChar == 'F' || holidayChar == 'A') {

                            workingDayCount = workingDayCount + 0.5f;
                        }
                    }

                }

            }

            dbg("end of getNoOfWorkingDaysTillNow-->" + workingDayCount);
            return workingDayCount;
        } catch (BSValidationException ex) {
            throw ex;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }

    public float getNoOfWorkingDaysInMonth(String l_instituteID, String year, String month, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException {

        try {
            IPDataService pds = inject.getPdataservice();
            float workingDayCount = 0.0f;
            String[] l_pkey = {year, month};
            ArrayList<String> holidayMaintanenceRecord = null;

            holidayMaintanenceRecord = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_HOLIDAY_MAINTANENCE", l_pkey);

            if (holidayMaintanenceRecord != null) {

                String holidayString = holidayMaintanenceRecord.get(3);
                char[] holidayArr = holidayString.toCharArray();

                for (int j = 0; j < holidayArr.length; j++) {

                    char holidayChar = holidayArr[j];

                    if (holidayChar == 'W') {

                        workingDayCount = workingDayCount + 1;
                    } else if (holidayChar == 'F' || holidayChar == 'A') {

                        workingDayCount = workingDayCount + 0.5f;
                    }
                }

            }

            return workingDayCount;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }

    public char getHolidayCharOfTheDay(String l_instituteID, String date, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {
        try {

            BusinessService bs = inject.getBusinessService(session);
            IPDataService pds = inject.getPdataservice();
            ConvertedDate converted = bs.getYearMonthandDay(date);
            String l_year = converted.getYear();
            String l_month = converted.getMonth();
            String l_day = converted.getDay();

            if (Integer.parseInt(l_month) < 10) {

                l_month = "0" + l_month;
            }

            String[] l_pkey = {l_instituteID, l_year, l_month};
            ArrayList<String> holidayMaintanenceRecord = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_HOLIDAY_MAINTANENCE", l_pkey);

            String holidayString = holidayMaintanenceRecord.get(3);
            char[] holidayArr = holidayString.toCharArray();
            char charForTheDay = holidayArr[Integer.parseInt(l_day) - 1];

//                  if(charForTheDay!='W'){
//
//                      holidayStatus=true;
//                  }
//
//              if(noon.equals("F")){
//
//                  if(charForTheDay=='F'){
//
//                      holidayStatus=true;
//                  }
//
//              }else if(noon.equals("A")){
//
//                  if(charForTheDay!='A'){
//
//                      holidayStatus=true;
//                  }
//              }
            return charForTheDay;

//        }catch(BSValidationException ex){
//            throw ex;    
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }

    public String getCurrentYearMonth() throws BSProcessingException {

        try {
            dbg("inside BusinessService--->getCurrentYearMonth");
            Date date = new Date();
            String dateformat = i_db_properties.getProperty("DATE_TIME_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
            String l_dateStamp = formatter.format(date);
            Date currentDate = formatter.parse(l_dateStamp);
            LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String month = Integer.toString(localDate.getMonthValue());
            String year = Integer.toString(localDate.getYear());
            String day = Integer.toString(localDate.getDayOfMonth());

            String yearAndMonth = year + "~" + month + "~" + day;
            dbg("yearAndMonth" + yearAndMonth);
            dbg("end of BusinessService--->getCurrentYearMonth");
            return yearAndMonth;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public String getCurrentDateTime() throws BSProcessingException {

        try {
            dbg("inside BusinessService--->getCurrentDate");
//       Date date = new Date();
            Instant timeStamp = Instant.now();
//    System.out.println("Machine Time Now:" + timeStamp);

            //timeStamp in zone - "America/Los_Angeles"
            ZonedDateTime date = timeStamp.atZone(ZoneId.of("Asia/Kolkata"));
            String dateformat = i_db_properties.getProperty("DATE_TIME_FORMAT");
//       SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
//       String l_dateStamp=formatter.format(date);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateformat);

            String l_dateStamp = date.format(formatter);

            dbg("yearAndMonth" + l_dateStamp);
            dbg("end of BusinessService--->getCurrentDate");
            return l_dateStamp;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public String getCurrentDate() throws BSProcessingException {

        try {
            dbg("inside BusinessService--->getCurrentDate");
//       Date date = new Date();
            String dateformat = i_db_properties.getProperty("DATE_FORMAT");
//       SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
//       String l_dateStamp=formatter.format(date);
            Instant timeStamp = Instant.now();
            ZonedDateTime date = timeStamp.atZone(ZoneId.of("Asia/Kolkata"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateformat);

            String l_dateStamp = date.format(formatter);
            dbg("yearAndMonth" + l_dateStamp);
            dbg("end of BusinessService--->getCurrentDate");
            return l_dateStamp;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public String getPreviousDate() throws BSProcessingException {

        try {
            dbg("inside BusinessService--->getpreviousDate");
            String dateformat = i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String previousDate = formatter.format(cal.getTime());

            dbg("previousDate" + previousDate);
            dbg("end of BusinessService--->getpreviousDate");
            return previousDate;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public String getPreviousYearMonth(String year, String month) throws BSProcessingException {

        try {
            dbg("inside BusinessService--->getPreviousYearMonth");
            dbg("year" + year);
            dbg("month" + month);
            int previousYear;
            int previousMonth;

            if (Integer.parseInt(month) == 1) {

                previousMonth = 12;
            } else {

                previousMonth = Integer.parseInt(month) - 1;
            }

            if (previousMonth == 1) {

                previousYear = Integer.parseInt(year) - 1;
            } else {

                previousYear = Integer.parseInt(year);
            }

            String yearAndMonth = previousYear + "~" + 6;
            dbg("yearAndMonth" + yearAndMonth);
            dbg("end of BusinessService--->getPreviousYearMonth");
            return yearAndMonth;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public int getNo_of_daysInMonth(String year, String month) throws DBProcessingException {

        try {
            dbg("inside getNo_of_daysInMonth ");
            Calendar calendar = Calendar.getInstance();
            int date = 0;
            calendar.set(Integer.parseInt(year), Integer.parseInt(month), date);
            int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            dbg("No_of_days" + days);
            dbg("end of getNo_of_daysInMonth");
            return days;
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        }

    }

    public String getDay(String p_date) throws BSProcessingException {

        try {

            String dateFormat = i_db_properties.getProperty("DATE_FORMAT");
            Date date1 = new SimpleDateFormat(dateFormat).parse(p_date);
            LocalDate localDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String day = localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

            return day;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("DBProcessingException" + ex.toString());
        }

    }

    public String getPKcolumnswithoutVersion(String p_file_type, String p_table_name, CohesiveSession session, AppDependencyInjection inject) throws DBProcessingException, DBValidationException {

        try {
            boolean l_versionStatus = false;
            IMetaDataService mds = inject.getMetadataservice();
            dbg("inside getPKwithoutVersion--->");

            DBTable l_dbtable = mds.getTableMetaData(p_file_type, p_table_name, session);
            String l_pkeyID = l_dbtable.getI_Pkey();
            Map<String, DBColumn> l_columnCollection = l_dbtable.getI_ColumnCollection();
            Iterator columnIterator = l_columnCollection.values().iterator();
            String l_pkIds = null;
            while (columnIterator.hasNext()) {
                DBColumn l_dbcolumn = (DBColumn) columnIterator.next();
                if (l_dbcolumn.getI_ColumnName().equals("VERSION_NUMBER")) {
                    l_versionStatus = true;
                    dbg("version status is true");
                } else {
                    l_versionStatus = false;
                    dbg("version status is false");
                }
                if (l_versionStatus == true) {
                    break;
                }
            }

            if (l_versionStatus) {

                if (l_pkeyID.contains("~")) {

                    l_pkIds = l_pkeyID.substring(0, l_pkeyID.lastIndexOf("~"));

                } else {

                    l_pkIds = l_pkeyID;
                }

            } else {
                l_pkIds = l_pkeyID;
            }

            return l_pkIds;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }
    }

    public String getPkeyOfExistingEvent(int p_lengthOfPkWithoutVersion, String p_previousEvent) throws BSProcessingException {

        try {
            dbg("inside getPkeyOfExistingEvent");
            dbg("p_lengthOfPkWithoutVersion" + p_lengthOfPkWithoutVersion);
            String l_pkOfExistingEvent = new String();
            String[] eventArray = p_previousEvent.split(",");

            if (p_lengthOfPkWithoutVersion == 1) {

                l_pkOfExistingEvent = eventArray[1];
            } else {

                for (int i = 1; i <= p_lengthOfPkWithoutVersion; i++) {

                    l_pkOfExistingEvent = l_pkOfExistingEvent + eventArray[i] + ",";
                }

                l_pkOfExistingEvent = l_pkOfExistingEvent.substring(0, l_pkOfExistingEvent.lastIndexOf(","));
            }

            dbg("l_pkOfExistingEvent" + l_pkOfExistingEvent);
            dbg("end of getPkeyOfExistingEvent");

            return l_pkOfExistingEvent;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public String createMonthEvent(String p_date) throws BSProcessingException {

        try {
            ConvertedDate convertedDate = getYearMonthandDay(p_date);
            String l_year = convertedDate.getYear();
            String l_month = convertedDate.getMonth();
            int no_of_Days = getNo_of_daysInMonth(l_year, l_month);
            dbg("no_of_Days" + no_of_Days);
            String monthAttendanceRecord = "d";

            for (int i = 1; i <= no_of_Days; i++) {

                monthAttendanceRecord = monthAttendanceRecord + i + "//" + " " + "d";

            }
            dbg("monthAttendanceRecord" + monthAttendanceRecord);
            return monthAttendanceRecord;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public String setDayEventinMonthEvent(String p_monthEvent, String oldDayEvent, String p_newDayEvent, String p_day) throws BSProcessingException {

        try {

            dbg("inside setDayEventinMonthEvent");
            dbg("p_monthEvent" + p_monthEvent);
            dbg("oldDayEvent" + oldDayEvent);
            dbg("p_newDayEvent" + p_newDayEvent);
            dbg("p_day" + p_day);
            String newMonthEvent;

            newMonthEvent = p_monthEvent.replace("d" + oldDayEvent, "d" + p_newDayEvent);
            dbg("newMonthEvent" + newMonthEvent);
            return newMonthEvent;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }

    public String getStudentName(String p_studentID, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSValidationException, BSProcessingException, DBProcessingException, DBValidationException {

        try {

            String l_studentName;
            IPDataService pds = inject.getPdataservice();
            String[] pkey = {p_studentID};
            ArrayList<String> studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", pkey);
            l_studentName = studentList.get(1).trim();
            return l_studentName;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public String getUserType(String p_userID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSValidationException, BSProcessingException, DBProcessingException, DBValidationException {

        try {
            dbg("inside getUserType");
            dbg("userID-->" + p_userID);
            IPDataService pds = inject.getPdataservice();

            String[] l_userPkey = {p_userID};
            ArrayList<String> l_userList = pds.readRecordPData(session, dbSession, "USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_USER_PROFILE", l_userPkey);
            String l_userType = l_userList.get(13).trim();

            dbg("l_userType--->" + l_userType);

            dbg("end of getUserType");

            return l_userType;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public String getTeacherIDForTheUser(String p_userID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSValidationException, BSProcessingException, DBProcessingException, DBValidationException {

        try {
            dbg("inside getTeacherIDForTheUser");
            dbg("userID-->" + p_userID);
            IPDataService pds = inject.getPdataservice();

            String[] l_userPkey = {p_userID};
            ArrayList<String> l_userList = pds.readRecordPData(session, dbSession, "USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_TEACHER_ID_MAPPING", l_userPkey);
            String l_teacherID = l_userList.get(1).trim();

            dbg("l_teacherID--->" + l_teacherID);

            dbg("end of getTeacherIDForTheUser");

            return l_teacherID;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public String getTeacherName(String p_teacherID, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSValidationException, BSProcessingException, DBProcessingException, DBValidationException {

        try {
            dbg("inside getTeacherName");
            String l_teacherName;
            IPDataService pds = inject.getPdataservice();
            String[] pkey = {p_teacherID};
            ArrayList<String> teacherList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_TEACHER_MASTER", pkey);
            l_teacherName = teacherList.get(1).trim();

            dbg("end of getTeacherName" + l_teacherName);
            return l_teacherName;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public String getInstituteName(String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSValidationException, BSProcessingException, DBProcessingException, DBValidationException {

        try {
            dbg("inside getInstituteName p_instituteID" + p_instituteID);
            String l_instituteName;
            IPDataService pds = inject.getPdataservice();
            String[] pkey = {p_instituteID};
            ArrayList<String> instituteList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Institute" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Institute", "INSTITUTE", "INSTITUTE_MASTER", pkey);
            l_instituteName = instituteList.get(1).trim();

            dbg("end of getInstituteName l_instituteName" + l_instituteName);
            return l_instituteName;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }
public String getInstituteShortName(String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSValidationException, BSProcessingException, DBProcessingException, DBValidationException {

        try {
            dbg("inside getInstituteShortName p_instituteID" + p_instituteID);
            String l_instituteName;
            IPDataService pds = inject.getPdataservice();
            String[] pkey = {p_instituteID};
            
            
            
            try{
            
            
            ArrayList<String> instituteList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Institute" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Institute", "INSTITUTE", "IVW_INSITUTE_OTHER_DETAILS", pkey);
            l_instituteName = instituteList.get(1).trim();
            
            }catch(DBValidationException ex){
                
                if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                 
                    l_instituteName=this.getInstituteName(p_instituteID, session, dbSession, inject);
                    
                }else{
                    
                    throw ex;
                }
                
            }
            
           
            
            
            

            dbg("end of getInstituteShortName l_instituteName" + l_instituteName);
            return l_instituteName;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }
    public String getSubjectName(String p_subjectID, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSValidationException, BSProcessingException, DBProcessingException, DBValidationException {

        try {

            String l_subjectName;
            IPDataService pds = inject.getPdataservice();
            String[] pkey = {p_instituteID, p_subjectID};
            ArrayList<String> subjectList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_SUBJECT_MASTER", pkey);
            l_subjectName = subjectList.get(2).trim();
            return l_subjectName;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public String getExamDescription(String p_exam, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSValidationException, BSProcessingException, DBProcessingException, DBValidationException {

        try {

            String l_examDescription;
            IPDataService pds = inject.getPdataservice();
            String[] pkey = {p_instituteID, p_exam};
            ArrayList<String> subjectList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_INSTITUTE_EXAM_MASTER", pkey);
            l_examDescription = subjectList.get(2).trim();
            return l_examDescription;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public String getMaxVersionOfTheInstitute(String instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {

        try {

            dbg("inside getMaxVersionOfTheInstitute");

            IPDataService pds = inject.getPdataservice();

            String[] l_pkey = {instituteID};

            ArrayList<String> instituteRecord = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Institute" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Institute", "INSTITUTE", "INSTITUTE_MASTER", l_pkey);

            String masterVersionNumber = instituteRecord.get(9).trim();

            dbg("inside getMaxVersionOfTheInstitute--->masterVersionNumber--->" + masterVersionNumber);

            return masterVersionNumber;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    }

    public String getMaxVersionOfTheUser(String userID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {

        try {

            dbg("inside getMaxVersionOfTheUser");

            IPDataService pds = inject.getPdataservice();

            String[] l_pkey = {userID};

            ArrayList<String> l_profileRecord = pds.readRecordPData(session, dbSession, "USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_USER_PROFILE", l_pkey);

            String masterVersionNumber = l_profileRecord.get(10).trim();

            dbg("inside getMaxVersionOfTheUser--->masterVersionNumber--->" + masterVersionNumber);

            return masterVersionNumber;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    }

    public String getMaxVersionOfTheClass(String instituteID, String standard, String section, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {

        try {

            dbg("inside getMaxVersionOfTheClass");

            IPDataService pds = inject.getPdataservice();

            String[] l_pkey = {instituteID, standard, section};

            ArrayList<String> classConfigMasterRecord = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID, "INSTITUTE", "IVW_STANDARD_MASTER", l_pkey);

            String masterVersionNumber = classConfigMasterRecord.get(4).trim();

            dbg("inside getMaxVersionOfTheClass--->masterVersionNumber--->" + masterVersionNumber);

            return masterVersionNumber;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    }

    public String getVersionNumberFromTheRecord(String tableName, String fileType, ArrayList<String> recordList, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {

        try {

            dbg("inside getVersionNumberFromTheRecord");

            IMetaDataService mds = inject.getMetadataservice();

            int columnID = mds.getColumnMetaData(fileType, tableName, "VERSION_NUMBER", session).getI_ColumnID();

            String versionNumber = recordList.get(columnID - 1).trim();

            dbg("inside getVersionNumberFromTheRecord--->versionNumber--->" + versionNumber);

            return versionNumber;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    }

    public int getVersionIndexOfTheTable(String tableName, String fileType, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {

        try {

            dbg("inside getVersionNumberFromTheRecord");

            IMetaDataService mds = inject.getMetadataservice();

            int columnID = mds.getColumnMetaData(fileType, tableName, "VERSION_NUMBER", session).getI_ColumnID();

            int versionIndex = columnID - 1;

            return versionIndex;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    }

    public String getGrade(String p_mark, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSValidationException, BSProcessingException, DBProcessingException, DBValidationException {

        try {
            dbg("inside get grade");
            dbg("p_mark" + p_mark);
            dbg("p_instituteID" + p_instituteID);
            String l_grade = "";
            IPDataService pds = inject.getPdataservice();
            int mark = Integer.parseInt(p_mark);
            Map<String, ArrayList<String>> gradeList = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_SUBJECT_GRADE_MASTER", session, dbSession);

            dbg("gradeList size" + gradeList.size());

            String masterVersion = getMaxVersionOfTheInstitute(p_instituteID, session, dbSession, inject);
            int versionIndex = getVersionIndexOfTheTable("IVW_SUBJECT_GRADE_MASTER", "INSTITUTE", session, dbSession, inject);

            List<ArrayList<String>> filteredList = gradeList.values().stream().filter(rec -> rec.get(versionIndex).equals(masterVersion)).collect(Collectors.toList());

            Iterator<ArrayList<String>> valueIterator = filteredList.iterator();

            while (valueIterator.hasNext()) {

                ArrayList<String> value = valueIterator.next();
                String grade = value.get(1).trim();
                int from = Integer.parseInt(value.get(2).trim());
                int to = Integer.parseInt(value.get(3).trim());
                dbg("grade" + grade);
                dbg("from" + from);
                dbg("to" + to);

                if (mark >= from && mark <= to) {
                    l_grade = grade;
                }

            }

            dbg("end of getGrade--->l_grade-->" + l_grade);

            return l_grade;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public ArrayList<Date> getLeaveDates(String p_fromDate, String p_toDate, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSValidationException, BSProcessingException, DBProcessingException, DBValidationException {

        try {
            dbg("inside getLeaveDates");
            String dateformat = i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
            ArrayList<Date> dates = new ArrayList();
            Date startDate = (Date) formatter.parse(p_fromDate);
            Date endDate = (Date) formatter.parse(p_toDate);

            long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
            long endTime = endDate.getTime(); // create your endtime here, possibly using Calendar or Date
            long curTime = startDate.getTime();
            while (curTime <= endTime) {
                dates.add(new Date(curTime));
                curTime += interval;
            }
            dbg("end of getLeaveDates");
            return dates;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public ArrayList<String> getStudentFileNames(String p_studentID, Request request, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject, String standard, String section) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getStudentFileNames");
            ArrayList<String> l_fileNames = new ArrayList();
            IPDataService pds = inject.getPdataservice();
//            IDBReadBufferService readBuffer= inject.getDBReadBufferService();
            String l_instituteID = request.getReqHeader().getInstituteID();
            String l_userID = request.getReqHeader().getUserID();
            dbg("p_studentID" + p_studentID);
            String[] l_pkey = {l_userID};
            ArrayList<String> l_userList = pds.readRecordPData(session, dbSession, "USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_USER_PROFILE", l_pkey);
            String l_userType = l_userList.get(13).trim();
            dbg("l_userType" + l_userType);

            if (standard == null || standard.isEmpty() || section == null || section.isEmpty()) {

                if (p_studentID == null || p_studentID.isEmpty()) {

                    session.getErrorhandler().log_app_error("BS_VAL_027", p_studentID);
                    throw new BSValidationException("BSValidationException");

                }

            }

            if (p_studentID != null && !p_studentID.isEmpty()) {
                dbg("student id is empty or null");
                l_fileNames.add(p_studentID);

                if (l_userType.equals("P")) {

                    Map<String, ArrayList<String>> l_parentStudentRoleMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_PARENT_STUDENT_ROLEMAPPING", session, dbSession);
                    dbg("l_parentStudentRoleMap size" + l_parentStudentRoleMap.size());
                    Map<String, List<ArrayList<String>>> l_studentWiseGroup = l_parentStudentRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(l_userID) && rec.get(4).trim().equals(l_instituteID)).collect(Collectors.groupingBy(rec -> rec.get(2).trim()));

                    if (!l_userID.equals("System")) {
                        if (l_studentWiseGroup != null) {

                            if (!l_studentWiseGroup.containsKey(p_studentID)) {

                                session.getErrorhandler().log_app_error("BS_VAL_014", p_studentID);
                                throw new BSValidationException("BSValidationException");
                            }
                        } else {

                            session.getErrorhandler().log_app_error("BS_VAL_014", p_studentID);
                            throw new BSValidationException("BSValidationException");
                        }
                    }
                } else {

                    Map<String, ArrayList<String>> l_classEntityRoleMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_CLASS_ENTITY_ROLEMAPPING", session, dbSession);
                    dbg("l_classEntityRoleMap size" + l_classEntityRoleMap.size());
                    Map<String, List<ArrayList<String>>> l_classWiseGroup = l_classEntityRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(l_userID) && rec.get(5).trim().equals(l_instituteID)).collect(Collectors.groupingBy(rec -> rec.get(2).trim() + "~" + rec.get(3).trim()));
                    dbg("l_classWiseGroup size" + l_classWiseGroup.size());
                    Iterator<String> keyIterator = l_classWiseGroup.keySet().iterator();

                    while (keyIterator.hasNext()) {

                        dbg("standard section" + keyIterator.next());
                    }

                    String[] pkey = {p_studentID};
                    ArrayList<String> l_studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", pkey);
                    String standardOfTheStudent = l_studentList.get(2).trim();
                    String sectionOfTheStudent = l_studentList.get(3).trim();
                    dbg("standardOfTheStudent" + standardOfTheStudent);
                    dbg("sectionOfTheStudent" + sectionOfTheStudent);

                    if (!l_userID.equals("System")) {

                        if (l_classWiseGroup != null || !l_classWiseGroup.isEmpty()) {

                            if (!l_classWiseGroup.containsKey("ALL~ALL")) {

                                dbg("l_classWiseGroup not contains all");

                                if (!l_classWiseGroup.containsKey(standardOfTheStudent + "~" + sectionOfTheStudent)) {

                                    dbg("l_classWiseGroup not contains standardOfTheStudent");

                                    session.getErrorhandler().log_app_error("BS_VAL_014", p_studentID);
                                    throw new BSValidationException("BSValidationException");
                                }

                            }
                        } else {

                            session.getErrorhandler().log_app_error("BS_VAL_014", p_studentID);
                            throw new BSValidationException("BSValidationException");
                        }
                    }
                }

            } else if (standard != null && !standard.isEmpty() && section != null && !section.isEmpty()) {
                dbg("student id is null");
                dbg("l_userID" + l_userID);
//                String[] l_pkey={l_userID};
//                ArrayList<String>l_userList=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User", "USER", "UVW_USER_PROFILE",l_pkey);
//                String l_userType=l_userList.get(13).trim();
//                dbg("l_userType"+l_userType);

                if (l_userType.equals("P")) {

                    Map<String, ArrayList<String>> l_parentStudentRoleMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_PARENT_STUDENT_ROLEMAPPING", session, dbSession);
                    dbg("l_parentStudentRoleMap size" + l_parentStudentRoleMap.size());
                    Map<String, List<ArrayList<String>>> l_studentWiseGroup = l_parentStudentRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(l_userID) && rec.get(4).trim().equals(l_instituteID)).collect(Collectors.groupingBy(rec -> rec.get(2).trim()));
                    dbg("l_studentWiseGroup size" + l_studentWiseGroup.size());

                    Iterator<String> studentIterator = l_studentWiseGroup.keySet().iterator();

                    while (studentIterator.hasNext()) {

                        String studentID = studentIterator.next();
                        dbg("studentID" + studentID);
                        l_fileNames.add(studentID);
                    }

                } else {
                    Map<String, ArrayList<String>> l_classEntityRoleMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_CLASS_ENTITY_ROLEMAPPING", session, dbSession);
                    dbg("l_classEntityRoleMap size" + l_classEntityRoleMap.size());

                    Map<String, List<ArrayList<String>>> l_classWiseGroup = l_classEntityRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(l_userID) && rec.get(5).trim().equals(l_instituteID)).collect(Collectors.groupingBy(rec -> rec.get(2).trim() + "~" + rec.get(3).trim()));
                    dbg("l_classWiseGroup size" + l_classWiseGroup.size());

                    if (!l_userID.equals("System")) {

                        if (l_classWiseGroup != null || !l_classWiseGroup.isEmpty()) {

                            if (!l_classWiseGroup.containsKey("ALL~ALL")) {

                                if (!l_classWiseGroup.containsKey(standard + "~" + standard)) {

                                    session.getErrorhandler().log_app_error("BS_VAL_014", p_studentID);
                                    throw new BSValidationException("BSValidationException");
                                }

                            }
                        } else {

                            session.getErrorhandler().log_app_error("BS_VAL_014", p_studentID);
                            throw new BSValidationException("BSValidationException");
                        }
                    }

//                    Iterator<String>classIterator=l_classWiseGroup.keySet().iterator();
//                    while(classIterator.hasNext()){
//                        
//                        String l_class=classIterator.next();
                    String l_standard = standard;
                    String l_section = section;

                    dbg("l_standard" + l_standard);
                    dbg("l_section" + l_section);

//                        Map<String,DBRecord>l_classStudentMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section,"CLASS","CLASS_STUDENT_MAPPING", session, dbSession);
//                        Map<String,List<DBRecord>>l_studentGroup=l_classStudentMap.values().stream().collect(Collectors.groupingBy(rec->rec.getRecord().get(2).trim()));
                    Map<String, ArrayList<String>> studentMasterMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", session, dbSession);

                    Map<String, List<ArrayList<String>>> l_studentGroup = studentMasterMap.values().stream().filter(rec -> rec.get(2).trim().equals(l_standard) && rec.get(3).trim().equals(l_section)).collect(Collectors.groupingBy(rec -> rec.get(0).trim()));

                    dbg("l_studentGroup size" + l_studentGroup.size());

                    Iterator<String> studentIterator = l_studentGroup.keySet().iterator();

                    while (studentIterator.hasNext()) {
                        String studentID = studentIterator.next();
                        dbg("studentID" + studentID);
                        l_fileNames.add(studentID);
                    }

//                }
                }

            }
            dbg("end of getStudentFileNames");

            if (!l_userID.equals("System")) {
                if (l_fileNames.isEmpty()) {
                    session.getErrorhandler().log_app_error("BS_VAL_014", "Student");
                    throw new BSValidationException("BSValidationException");
                }
            }

            return l_fileNames;
        } catch (BSValidationException ex) {
            throw ex;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getTeacherFileNames(String p_teacherID, Request request, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getTeacherFileNames");
            ArrayList<String> l_fileNames = new ArrayList();
            IPDataService pds = inject.getPdataservice();
            String instituteID = request.getReqHeader().getInstituteID();
            String l_userID = request.getReqHeader().getUserID();
            dbg("p_teacherID" + p_teacherID);

            if (p_teacherID != null && !p_teacherID.isEmpty()) {
                dbg("p_teacherID  is empty or null");
                l_fileNames.add(p_teacherID);

                Map<String, ArrayList<String>> l_teacherEntityRoleMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_TEACHER_ENTITY_ROLEMAPPING", session, dbSession);
                dbg("l_teacherEntityRoleMap size" + l_teacherEntityRoleMap.size());

                Map<String, List<ArrayList<String>>> l_teacherWiseGroup = l_teacherEntityRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(l_userID) && rec.get(2).trim().equals(instituteID)).collect(Collectors.groupingBy(rec -> rec.get(3).trim()));
                dbg("l_teacherWiseGroup size" + l_teacherWiseGroup.size());

                if (!l_userID.equals("System")) {

                    if (l_teacherWiseGroup != null) {

                        if (!(l_teacherWiseGroup.containsKey("ALL") || l_teacherWiseGroup.containsKey(p_teacherID))) {

                            session.getErrorhandler().log_app_error("BS_VAL_014", p_teacherID);
                            throw new BSValidationException("BSValidationException");
                        }
                    } else {

                        session.getErrorhandler().log_app_error("BS_VAL_014", p_teacherID);
                        throw new BSValidationException("BSValidationException");
                    }

                }

            } else {
                dbg("p_teacherID is null");
                dbg("l_userID" + l_userID);

                Map<String, ArrayList<String>> l_teacherEntityRoleMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_TEACHER_ENTITY_ROLEMAPPING", session, dbSession);
                dbg("l_teacherEntityRoleMap size" + l_teacherEntityRoleMap.size());

                Map<String, List<ArrayList<String>>> l_teacherWiseGroup = l_teacherEntityRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(l_userID) && rec.get(2).trim().equals(instituteID)).collect(Collectors.groupingBy(rec -> rec.get(3).trim()));
                dbg("l_teacherWiseGroup size" + l_teacherWiseGroup.size());

                if (l_teacherWiseGroup.containsKey("ALL")) {

                    l_fileNames = getTeachersOfTheInstitute(instituteID, session, dbSession, inject, true);
                } else {

                    Iterator<String> teacherIterator = l_teacherWiseGroup.keySet().iterator();
                    while (teacherIterator.hasNext()) {

                        String l_teacherID = teacherIterator.next();
                        l_fileNames.add(l_teacherID);
                    }
                }
            }

            if (!l_userID.equals("System")) {

                if (l_fileNames.size() == 0) {
                    session.getErrorhandler().log_app_error("BS_VAL_014", "Teacher");
                    throw new BSValidationException("BSValidationException");
                }
            }

            dbg("end of getTeacherFileNames");
            return l_fileNames;
        } catch (BSValidationException ex) {
            throw ex;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getClassFileNames(String p_standard, String l_section, Request request, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getClassFileNames");
            ArrayList<String> l_fileNames = new ArrayList();
            IPDataService pds = inject.getPdataservice();
            String instituteID = request.getReqHeader().getInstituteID();
            String l_userID = request.getReqHeader().getUserID();
            dbg("p_standard" + p_standard);
            dbg("l_section" + l_section);

            if (p_standard != null && !p_standard.isEmpty() && l_section != null && !l_section.isEmpty()) {
                dbg("class not null");
                l_fileNames.add(p_standard + "~" + l_section);

                Map<String, ArrayList<String>> l_classStudenMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_CLASS_ENTITY_ROLEMAPPING", session, dbSession);

                Map<String, List<ArrayList<String>>> filteredInstitutes = l_classStudenMap.values().stream().filter(rec -> rec.get(5).trim().equals(instituteID) && rec.get(0).trim().equals(l_userID)).collect(Collectors.groupingBy(rec -> rec.get(2).trim() + rec.get(3).trim()));

                if (!l_userID.equals("System")) {

                    if (!filteredInstitutes.containsKey("ALLALL")) {

                        dbg("filteredInstitutes not contains ALLALL");

                        if (!filteredInstitutes.containsKey(p_standard + l_section)) {

                            dbg("filteredInstitutes not contains " + p_standard + l_section);

                            session.getErrorhandler().log_app_error("BS_VAL_014", p_standard + l_section);
                            throw new BSValidationException("BSValidationException");
                        }

                    }
                }
            } else {
                dbg("p_teacherID is null");
                dbg("l_userID" + l_userID);

                Map<String, ArrayList<String>> l_classEntityRoleMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_CLASS_ENTITY_ROLEMAPPING", session, dbSession);
                dbg("l_classEntityRoleMap size" + l_classEntityRoleMap.size());

                Iterator<String> keyIterator = l_classEntityRoleMap.keySet().iterator();

                while (keyIterator.hasNext()) {

                    String key = keyIterator.next();

                    dbg("key" + key);

                    ArrayList<String> value = l_classEntityRoleMap.get(key);

                    dbg("value" + value);
                }

                Map<String, List<ArrayList<String>>> l_classWiseGroup = l_classEntityRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(l_userID) && rec.get(5).trim().equals(instituteID)).collect(Collectors.groupingBy(rec -> rec.get(2).trim() + "~" + rec.get(3).trim()));
                dbg("l_classWiseGroup size" + l_classWiseGroup.size());

                if (l_classWiseGroup.size() == 0) {

                    l_classWiseGroup = l_classEntityRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(l_userID) && rec.get(5).trim().equals("ALL")).collect(Collectors.groupingBy(rec -> rec.get(2).trim() + "~" + rec.get(3).trim()));
                }

                if (!l_classWiseGroup.containsKey("ALL~ALL")) {

                    dbg("l_classWiseGroup not contains ALL");

                    Iterator<String> classIterator = l_classWiseGroup.keySet().iterator();
                    while (classIterator.hasNext()) {
                        String l_class = classIterator.next();
                        l_fileNames.add(l_class);

                    }
                } else {

                    dbg("l_classWiseGroup not contains ALL");

                    l_fileNames = getClassesOfTheInstitute(instituteID, session, dbSession, inject);

                }

            }

            dbg("l_fileNames size" + l_fileNames.size());

            if (!l_userID.equals("System")) {
                if (l_fileNames.size() == 0) {
                    session.getErrorhandler().log_app_error("BS_VAL_014", "class");
                    throw new BSValidationException("BSValidationException");
                }

            }
            dbg("end of getClassFileNames");
            return l_fileNames;
        } catch (BSValidationException ex) {
            throw ex;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public String getClassOfTheTeacher(String p_instituteID, String teacherID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            IPDataService pds = inject.getPdataservice();
            IBDProperties i_db_properties = session.getCohesiveproperties();

            String classs = "";

            try {

                Map<String, ArrayList<String>> classMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_STANDARD_MASTER", session, dbSession);
                List<ArrayList<String>> classList = classMap.values().stream().filter(rec -> rec.get(3).trim().equals(teacherID)).collect(Collectors.toList());
                if (classList != null && !classList.isEmpty()) {

                    classs = classList.get(0).get(1).trim() + "/" + classList.get(0).get(2).trim();

                }

            } catch (DBValidationException ex) {
                if (ex.toString().contains("DB_VAL_011") || ex.toString().contains("DB_VAL_000")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                } else {

                    throw ex;
                }
            }

            return classs;
//       }catch(BSValidationException ex){
//            throw ex  ;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getInstituteFileNames(String p_instituteID, Request request, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getInstituteFileNames");
            ArrayList<String> l_fileNames = new ArrayList();
            IPDataService pds = inject.getPdataservice();
            dbg("p_instituteID" + p_instituteID);
            String userID = request.getReqHeader().getUserID();

            if (p_instituteID != null && !p_instituteID.isEmpty()) {
                dbg("p_instituteID  is empty or null");
                l_fileNames.add(p_instituteID);

            } else {

                l_fileNames.add(request.getReqHeader().getInstituteID());

//                dbg("p_instituteID is null");
//                String l_userID=request.getReqHeader().getUserID();
//                dbg("l_userID"+l_userID);
//         
//                    Map<String, ArrayList<String>>l_instituteEntityRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_INSTITUTE_ENTITY_ROLEMAPPING",session,dbSession);
//                    dbg("l_instituteEntityRoleMap size"+l_instituteEntityRoleMap.size());
//                    
//                    
//                    l_instituteEntityRoleMap.values().stream().filter(rec->rec.get(2).trim().equals("ALL"));
//                    
//                    
//                    
//                    Map<String,List<ArrayList<String>>>l_instituteWiseGroup=l_instituteEntityRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(l_userID)).collect(Collectors.groupingBy(rec->rec.get(2).trim()));
//                    dbg("l_instituteWiseGroup size"+l_instituteWiseGroup.size());
//                    
//                    Iterator<String>instituteIterator=l_instituteWiseGroup.keySet().iterator();
//                    while(instituteIterator.hasNext()){
//                        
//                        String l_instituteID=instituteIterator.next();
//                        l_fileNames.add(l_instituteID);
//                    }
//            
            }
            dbg("end of getInstituteFileNames");

            if (!userID.equals("System")) {

                for (int i = 0; i < l_fileNames.size(); i++) {

                    String instituteID = l_fileNames.get(i);
                    dbg("inside access check-->instituteID--->" + instituteID);
                    Map<String, ArrayList<String>> l_instituteEntityRoleMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_INSTITUTE_ENTITY_ROLEMAPPING", session, dbSession);
                    dbg("l_instituteEntityRoleMap size" + l_instituteEntityRoleMap.size());

                    List<ArrayList<String>> filteredInstitutes = l_instituteEntityRoleMap.values().stream().filter(rec -> rec.get(2).trim().equals(instituteID) && rec.get(0).trim().equals(userID)).collect(Collectors.toList());

                    dbg("filteredInstitutes size" + filteredInstitutes.size());

                    if (filteredInstitutes.size() == 0) {
                        session.getErrorhandler().log_app_error("BS_VAL_014", instituteID);
                        throw new BSValidationException("BSValidationException");
                    }

                }
            }

            return l_fileNames;
        } catch (BSValidationException ex) {
            throw ex;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getStudentsOfTheGroup(String p_instituteID, String p_groupID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getStudentsOfTheGroup");
            ArrayList<String> l_studentList = new ArrayList();
//          IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IPDataService pds = inject.getPdataservice();
            //check student id not null all
            String[] l_pkey = {p_groupID};
            ArrayList<String> masterRecord = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_GROUP_MAPPING_MASTER", l_pkey);

            String masterVersion = masterRecord.get(9).trim();

            Map<String, ArrayList<String>> l_groupMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_GROUP_MAPPING_DETAIL", session, dbSession);

            int versionIndex = this.getVersionIndexOfTheTable("IVW_GROUP_MAPPING_DETAIL", "INSTITUTE", session, dbSession, inject);

            List<ArrayList<String>> l_studentWiseList = l_groupMap.values().stream().filter(rec -> rec.get(1).trim().equals(p_groupID) && rec.get(versionIndex).trim().equals(masterVersion)).collect(Collectors.toList());

            for (int i = 0; i < l_studentWiseList.size(); i++) {

                String studentID = l_studentWiseList.get(i).get(4).trim();
                String l_standard = l_studentWiseList.get(i).get(2).trim();
                String l_section = l_studentWiseList.get(i).get(3).trim();

                if (studentID != null && !studentID.equals("dum")) {

                    l_studentList.add(studentID);

                } else {

                    if (!l_standard.equals("dum") && !l_section.equals("dum")) {

                        Map<String, ArrayList<String>> studentMasterMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", session, dbSession);

                        Map<String, List<ArrayList<String>>> l_studentGroup = studentMasterMap.values().stream().filter(rec -> rec.get(2).trim().equals(l_standard) && rec.get(3).trim().equals(l_section)).collect(Collectors.groupingBy(rec -> rec.get(0).trim()));

                        dbg("l_studentGroup size" + l_studentGroup.size());

                        Iterator<String> studentIterator = l_studentGroup.keySet().iterator();

                        while (studentIterator.hasNext()) {
                            String l_studentID = studentIterator.next();
                            dbg("l_studentID" + l_studentID);
                            l_studentList.add(l_studentID);
                        }

                    }
                }
            }

            dbg("end of getStudentsOfTheGroup");
            return l_studentList;

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getClassesOfTheInstitute(String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getClassesOfTheGroup");
            ArrayList<String> l_classList = new ArrayList();
            IPDataService pds = inject.getPdataservice();
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();

            Map<String, ArrayList<String>> l_classMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_STANDARD_MASTER", session, dbSession);

            List<ArrayList<String>> l_classWiseList = l_classMap.values().stream().collect(Collectors.toList());

            for (int i = 0; i < l_classWiseList.size(); i++) {

                String l_standard = l_classWiseList.get(i).get(1).trim();
                String l_section = l_classWiseList.get(i).get(2).trim();

                String classs = l_standard + "~" + l_section;
                l_classList.add(classs);
            }

            dbg("getClassesOfTheInstitute l_classList size" + l_classList.size());
            dbg("end of getClassesOfTheInstitute");
            return l_classList;

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public void getStudentsOfTheClass(String p_instituteID, String p_standard, String p_section, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject, HashSet<String> studentsList, HashSet<String> classList) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getStudentsOfTheClass");
//          ArrayList<String>l_studentList=new ArrayList();
//          IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IPDataService pds = inject.getPdataservice();

            classList.add(p_standard + "~" + p_section);
            Map<String, ArrayList<String>> l_classStudentMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "CLASS" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_standard + p_section + i_db_properties.getProperty("FOLDER_DELIMITER") + p_standard + p_section, "CLASS", "CLASS_STUDENT_MAPPING", session, dbSession);
            dbg("l_classStudentMap size" + l_classStudentMap.size());
            Map<String, List<ArrayList<String>>> l_studentGroup = l_classStudentMap.values().stream().filter(rec -> rec.get(7).trim().equals("O") && rec.get(8).trim().equals("A")).collect(Collectors.groupingBy(rec -> rec.get(2).trim()));
            dbg("l_studentGroup size" + l_studentGroup.size());

            Iterator<String> studentIterator = l_studentGroup.keySet().iterator();

            while (studentIterator.hasNext()) {
                String studentID = studentIterator.next();
                dbg("inside student iteration studentID" + studentID);
                studentsList.add(studentID);
            }

            dbg("end of getStudentsOfTheClass");

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getStudentsOfTheClass(String p_instituteID, String p_standard, String p_section, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getStudentsOfTheClass");
            ArrayList<String> l_studentList = new ArrayList();
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IPDataService pds = inject.getPdataservice();

            Map<String, ArrayList<String>> l_classStudentMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "CLASS" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_standard + p_section + i_db_properties.getProperty("FOLDER_DELIMITER") + p_standard + p_section, "CLASS", "CLASS_STUDENT_MAPPING", session, dbSession);
            dbg("l_classStudentMap size" + l_classStudentMap.size());
            Map<String, List<ArrayList<String>>> l_studentGroup = l_classStudentMap.values().stream().filter(rec -> rec.get(7).trim().equals("O") && rec.get(8).trim().equals("A")).collect(Collectors.groupingBy(rec -> rec.get(2).trim()));
            dbg("l_studentGroup size" + l_studentGroup.size());

            Iterator<String> studentIterator = l_studentGroup.keySet().iterator();

            while (studentIterator.hasNext()) {
                String studentID = studentIterator.next();
                dbg("inside student iteration studentID" + studentID);

                l_studentList.add(studentID);
            }

            dbg("end of getStudentsOfTheClass");
            return l_studentList;

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getSectionsOfTheStandard(String p_instituteID, String p_standard, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getSectionsOfTheStandard");
            dbg("p_instituteID" + p_instituteID);
            dbg("p_standard" + p_standard);
            ArrayList<String> l_sectionsList = new ArrayList();
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IPDataService pds = inject.getPdataservice();

            Map<String, ArrayList<String>> l_classMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_STANDARD_MASTER", session, dbSession);

            List<ArrayList<String>> l_classWiseList = l_classMap.values().stream().filter(rec -> rec.get(1).equals(p_standard) && rec.get(9).equals("O") && rec.get(10).equals("A")).collect(Collectors.toList());
            dbg("l_classWiseList" + l_classWiseList.size());

            for (int i = 0; i < l_classWiseList.size(); i++) {

                String l_section = l_classWiseList.get(i).get(2).trim();
                dbg("l_section" + l_section);

                l_sectionsList.add(l_section);
            }

            dbg("end of getSectionsOfTheStandard");
            return l_sectionsList;

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public HashSet<String> getStandardsOfTheInstitute(String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getStandardsOfTheInstitute");
            HashSet<String> l_standardList = new HashSet();
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IPDataService pds = inject.getPdataservice();

            Map<String, ArrayList<String>> l_classMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_STANDARD_MASTER", session, dbSession);

            List<ArrayList<String>> l_classWiseList = l_classMap.values().stream().collect(Collectors.toList());

            for (int i = 0; i < l_classWiseList.size(); i++) {

                String l_standard = l_classWiseList.get(i).get(1).trim();

                l_standardList.add(l_standard);
            }

            dbg("end of getStandardsOfTheInstitute");
            return l_standardList;

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public HashSet<String> getSectionsOfTheInstitute(String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getSectionsOfTheInstitute");
            HashSet<String> l_sectionList = new HashSet();
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IPDataService pds = inject.getPdataservice();

            Map<String, ArrayList<String>> l_classMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_STANDARD_MASTER", session, dbSession);

            List<ArrayList<String>> l_classWiseList = l_classMap.values().stream().collect(Collectors.toList());

            for (int i = 0; i < l_classWiseList.size(); i++) {

                String l_standard = l_classWiseList.get(i).get(2).trim();

                l_sectionList.add(l_standard);
            }

            dbg("end of getSectionsOfTheInstitute");
            return l_sectionList;

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public int getNoOfStudentsOfTheInstitute(String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getStudentsOfTheInstitute");
//            ArrayList<String>l_classList=new ArrayList();
            IPDataService pds = inject.getPdataservice();

//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            Map<String, ArrayList<String>> l_studentMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", session, dbSession);

            List<ArrayList<String>> l_studentWiseList = l_studentMap.values().stream().filter(rec -> rec.get(8).trim().equals("O")).collect(Collectors.toList());

//           for(int i=0;i<l_studentWiseList.size();i++){
//               
//               String l_sudentID=l_studentWiseList.get(i).get(0).trim();
//               
//               l_classList.add(l_sudentID);
//           }
            dbg("end of getStudentsOfTheInstitute");
            return l_studentWiseList.size();

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public int getNoOfStudentsOfTheClass(String p_instituteID, String standard, String section, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getNoOfStudentsOfTheClass");
//            ArrayList<String>l_classList=new ArrayList();
            IPDataService pds = inject.getPdataservice();

//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            Map<String, ArrayList<String>> l_studentMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", session, dbSession);

            List<ArrayList<String>> l_studentWiseList = l_studentMap.values().stream().filter(rec -> rec.get(8).trim().equals("O") && rec.get(2).trim().equals(standard) && rec.get(3).trim().equals(section)).collect(Collectors.toList());

//           for(int i=0;i<l_studentWiseList.size();i++){
//               
//               String l_sudentID=l_studentWiseList.get(i).get(0).trim();
//               
//               l_classList.add(l_sudentID);
//           }
            dbg("l_studentWiseList size" + l_studentWiseList.size());
            dbg("end of getNoOfStudentsOfTheClass");
            return l_studentWiseList.size();

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getStudentsOfTheInstitute(String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getStudentsOfTheInstitute");
            ArrayList<String> l_classList = new ArrayList();
            IPDataService pds = inject.getPdataservice();

//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            Map<String, ArrayList<String>> l_studentMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", session, dbSession);

            List<ArrayList<String>> l_studentWiseList = l_studentMap.values().stream().filter(rec -> rec.get(8).trim().equals("O") && rec.get(9).trim().equals("A")).collect(Collectors.toList());

            for (int i = 0; i < l_studentWiseList.size(); i++) {

                String l_sudentID = l_studentWiseList.get(i).get(0).trim();

                l_classList.add(l_sudentID);
            }

            dbg("end of getStudentsOfTheInstitute");
            return l_classList;

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getAllInstitutes(CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getAllInstitutes");
            ArrayList<String> l_classList = new ArrayList();
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IPDataService pds = inject.getPdataservice();

            Map<String, ArrayList<String>> instituteMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Institute" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Institute", "INSTITUTE", "INSTITUTE_MASTER", session, dbSession);
            List<ArrayList<String>> l_instituteWiseList = instituteMap.values().stream().filter(rec -> rec.get(7).trim().equals("O") && rec.get(8).trim().equals("A")).collect(Collectors.toList());

            for (int i = 0; i < l_instituteWiseList.size(); i++) {

                String instituteID = l_instituteWiseList.get(i).get(0).trim();

                l_classList.add(instituteID);
            }

            dbg("end of getAllInstitutes");
            return l_classList;

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getAllUsers(CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getAllUsers");
            ArrayList<String> l_usersList = new ArrayList();
            IPDataService pds = inject.getPdataservice();
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();

            Map<String, ArrayList<String>> l_userMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_USER_PROFILE", session, dbSession);

            List<ArrayList<String>> l_userList = l_userMap.values().stream().collect(Collectors.toList());

            for (int i = 0; i < l_userList.size(); i++) {

                String l_userID = l_userList.get(i).get(0).trim();

                l_usersList.add(l_userID);
            }

            dbg("end of getAllUsers");
            return l_usersList;

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public int getNoOfTeachersOfTheInstitute(String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getTeachersOfTheInstitute");
            ArrayList<String> l_teacherList = new ArrayList();
            IPDataService pds = inject.getPdataservice();
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();

            Map<String, ArrayList<String>> l_teacherMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_TEACHER_MASTER", session, dbSession);

            List<ArrayList<String>> l_teacherWiseList = l_teacherMap.values().stream().filter(rec -> rec.get(7).trim().equals("O")).collect(Collectors.toList());

//           if(l_teacherWiseList!=null){
//           
//             dbg("l_teacherWiseList size"+l_teacherWiseList.size()); 
//           }else{
//               
//               dbg("l_teacherWiseList is null");
//           }
//          
//           for(int i=0;i<l_teacherWiseList.size();i++){
//               
//               String l_teacherID=l_teacherWiseList.get(i).get(0).trim();
//               dbg("inside l_teacherWiseList iteration-->l_teacherID-->"+l_teacherID);
//               l_teacherList.add(l_teacherID);
//           }
//           
            dbg("end of getTeachersOfTheInstitute");
            return l_teacherWiseList.size();

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getTeachersOfTheInstitute(String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getTeachersOfTheInstitute");
            ArrayList<String> l_teacherList = new ArrayList();
            IPDataService pds = inject.getPdataservice();
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();

            Map<String, ArrayList<String>> l_teacherMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_TEACHER_MASTER", session, dbSession);

            List<ArrayList<String>> l_teacherWiseList = l_teacherMap.values().stream().filter(rec -> rec.get(7).trim().equals("O") && rec.get(8).trim().equals("A")).collect(Collectors.toList());

            if (l_teacherWiseList != null) {

                dbg("l_teacherWiseList size" + l_teacherWiseList.size());
            } else {

                dbg("l_teacherWiseList is null");
            }

            for (int i = 0; i < l_teacherWiseList.size(); i++) {

                String l_teacherID = l_teacherWiseList.get(i).get(0).trim();
                dbg("inside l_teacherWiseList iteration-->l_teacherID-->" + l_teacherID);
                l_teacherList.add(l_teacherID);
            }

            dbg("end of getTeachersOfTheInstitute");
            return l_teacherList;

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getTeachersOfTheInstitute(String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject, boolean fromSummary) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getTeachersOfTheInstitute");
            ArrayList<String> l_teacherList = new ArrayList();
            IPDataService pds = inject.getPdataservice();
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();

            Map<String, ArrayList<String>> l_teacherMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_TEACHER_MASTER", session, dbSession);

            List<ArrayList<String>> l_teacherWiseList = l_teacherMap.values().stream().filter(rec -> rec.get(7).trim().equals("O")).collect(Collectors.toList());

            if (l_teacherWiseList != null) {

                dbg("l_teacherWiseList size" + l_teacherWiseList.size());
            } else {

                dbg("l_teacherWiseList is null");
            }

            for (int i = 0; i < l_teacherWiseList.size(); i++) {

                String l_teacherID = l_teacherWiseList.get(i).get(0).trim();
                dbg("inside l_teacherWiseList iteration-->l_teacherID-->" + l_teacherID);
                l_teacherList.add(l_teacherID);
            }

            dbg("end of getTeachersOfTheInstitute");
            return l_teacherList;

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public void getTeachersOfTheInstitute(String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject, HashSet<String> l_teacherList) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getTeachersOfTheInstitute");
//            ArrayList<String>l_teacherList=new ArrayList();
//            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IPDataService pds = inject.getPdataservice();

            Map<String, ArrayList<String>> l_teacherMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_TEACHER_MASTER", session, dbSession);

            List<ArrayList<String>> l_teacherWiseList = l_teacherMap.values().stream().filter(rec -> rec.get(9).trim().equals("O") && rec.get(10).trim().equals("A")).collect(Collectors.toList());

            for (int i = 0; i < l_teacherWiseList.size(); i++) {

                String l_teacherID = l_teacherWiseList.get(i).get(0).trim();

                l_teacherList.add(l_teacherID);
            }

            dbg("end of getTeachersOfTheInstitute");
//          return  l_teacherList;

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public boolean compareCheckerDate(String checkerDateStamp, String featureName, String instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException {

        try {
            dbg("inside compareCheckerDate  checkerDateStamp" + checkerDateStamp);
            boolean status = false;

            if (!checkerDateStamp.equals("")) {

                IPDataService pds = inject.getPdataservice();
                String[] l_pkey = {instituteID, featureName};

                ArrayList<String> retentionRecord = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID, "INSTITUTE", "RETENTION_PERIOD", l_pkey);

                int retentionPeriod = Integer.parseInt(retentionRecord.get(2).trim());
                String checkerDate = getDateFromDateStamp(checkerDateStamp);
                String currentDate = getCurrentDate();
                String dateformat = i_db_properties.getProperty("DATE_FORMAT");
                SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
                Date checkDate = formatter.parse(checkerDate);
                Date currDate = formatter.parse(currentDate);

                long diff = currDate.getTime() - checkDate.getTime();
                long diffDays = diff / (24 * 60 * 60 * 1000);

                dbg("diffDays" + diffDays);
                dbg("retentionPeriod" + retentionPeriod);

                if (diffDays > retentionPeriod) {

                    status = true;
                }

            }

            dbg("status" + status);
            dbg("end of compareCheckerDate  checkerDateStamp" + checkerDateStamp);
            return status;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public boolean compareCheckerDateApp(String checkerDateStamp, String featureName, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException {

        try {
            dbg("inside compareCheckerDate  checkerDateStamp" + checkerDateStamp);
            boolean status = false;

            if (!checkerDateStamp.equals("")) {

                IPDataService pds = inject.getPdataservice();
                String[] l_pkey = {featureName};

                ArrayList<String> retentionRecord = pds.readRecordPData(session, dbSession, "APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "APP_RETENTION_PERIOD", l_pkey);

                int retentionPeriod = Integer.parseInt(retentionRecord.get(1).trim());
                String checkerDate = getDateFromDateStamp(checkerDateStamp);
                String currentDate = getCurrentDate();
                String dateformat = i_db_properties.getProperty("DATE_FORMAT");
                SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
                Date checkDate = formatter.parse(checkerDate);
                Date currDate = formatter.parse(currentDate);

                long diff = currDate.getTime() - checkDate.getTime();
                long diffDays = diff / (24 * 60 * 60 * 1000);

                dbg("diffDays" + diffDays);
                dbg("retentionPeriod" + retentionPeriod);

                if (diffDays > retentionPeriod) {

                    status = true;
                }

            }

            dbg("status" + status);
            dbg("end of compareCheckerDate  checkerDateStamp" + checkerDateStamp);
            return status;

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public String getExpiryDate() throws BSProcessingException, DBValidationException, DBProcessingException {

        try {
            dbg("inside getExpiryDate");

            String dateFormat = i_db_properties.getProperty("DATE_FORMAT");
            long expiryDuration = Long.parseLong(i_db_properties.getProperty("PASSWORD_EXPIRY_DURATION"));
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            LocalDate date2 = LocalDate.now().plusMonths(expiryDuration);
            Date date1 = Date.from(date2.atStartOfDay(ZoneId.systemDefault()).toInstant());

            String expiryDate = formatter.format(date1);

            dbg("end of getExpiryDate" + expiryDate);
            return expiryDate;

//         }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());      
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public boolean compareYear(String year, String featureName, String instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException {

        try {
            dbg("inside compareCheckerDate  year" + year);
            boolean status = false;

            if (!year.equals("")) {

                IPDataService pds = inject.getPdataservice();
                String[] l_pkey = {instituteID, featureName};

                ArrayList<String> retentionRecord = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID, "INSTITUTE", "RETENTION_PERIOD", l_pkey);

                int retentionPeriod = Integer.parseInt(retentionRecord.get(2).trim());
                dbg("retentionPeriod" + retentionPeriod);

                String currentYear = getCurrentYearMonth().split("~")[0];
                dbg("currentYear" + currentYear);

                int diff = Integer.parseInt(currentYear) - Integer.parseInt(year);
                dbg("diff" + diff);

                if (diff == retentionPeriod) {

                    status = true;
                }

            }

            dbg("status" + status);
            return status;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getShippedArchivalFiles(String dateAndTime, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException {
        try {

            dbg("inside getShippedArchivalFiles");
            ArrayList<String> shippedFiles = new ArrayList();
            IDBReadBufferService readBuffer = inject.getDBReadBufferService();
            Date date = new Date();
            String dateformat = "yyMMdd";
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
            String currentDate = formatter.format(date);
            dbg("currentDate" + currentDate);

            Map<String, DBRecord> tableMap = readBuffer.readTable("DB" + i_db_properties.getProperty("FOLDER_DELIMITER") + "ARCH" + i_db_properties.getProperty("FOLDER_DELIMITER") + currentDate + i_db_properties.getProperty("FOLDER_DELIMITER") + "Primary", "ARCH", "ARCH_SHIPPING_STATUS", session, dbSession);

            Iterator<DBRecord> valueIterator = tableMap.values().iterator();

            String dateTimeFormat = i_db_properties.getProperty("DATE_TIME_FORMAT");
            SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(dateTimeFormat);

            while (valueIterator.hasNext()) {

                ArrayList<String> value = valueIterator.next().getRecord();
                String shippingTime = value.get(6).trim();
                String fileName = value.get(0).trim();
                Date shipping = dateTimeFormatter.parse(shippingTime);
                Date givenDate = dateTimeFormatter.parse(dateAndTime);

                if (shipping.compareTo(givenDate) >= 0) {

                    shippedFiles.add(fileName);
                }

            }

//            
            dbg("end of getShippedArchivalFiles");
            return shippedFiles;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        } finally {

        }

    }

    public ArrayList<String> getAllFiles() throws BSProcessingException {
        ArrayList<String> allFileNames = new ArrayList();
        try {

            dbg("inside getAllFiles");
//            Path batchFolderPath=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER"));
            Path getUserFolderPath = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH") + "USER" + i_db_properties.getProperty("FOLDER_DELIMITER"));

//            dbg("batchFolderPath"+batchFolderPath.toString());
            dbg("getUserFolderPath" + getUserFolderPath.toString());

//            getFilesOfTheFolder(batchFolderPath.toString(),allFileNames);
            getFilesOfTheFolder(getUserFolderPath.toString(), allFileNames);
            getFilesInsideInstituteFolder(allFileNames);

            dbg("end of getAllFiles");
            return allFileNames;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        } finally {

        }

    }

    private void getFilesInsideInstituteFolder(ArrayList<String> fileNames) throws BSProcessingException {
        DirectoryStream<Path> stream = null;
        DirectoryStream<Path> subStream = null;
        try {

            dbg("inside getFilesInsideInstituteFolder");

            Path folderPath = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH") + "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER"));
            stream = Files.newDirectoryStream(folderPath);
            String fileExtension = i_db_properties.getProperty("FILE_EXTENSION");

            for (Path file : stream) {

                dbg("file" + file.toString());

                subStream = Files.newDirectoryStream(file);

                for (Path subFile : subStream) {

                    String fileName = subFile.toString();
                    dbg("fileName" + fileName);

                    String substring = fileName.substring(fileName.length() - fileExtension.length(), fileName.length());

                    dbg("substring" + substring);

                    if (fileExtension.equals(substring)) {

                        fileNames.add(fileName);
                    } else {

                        if (!fileName.contains("temp")) {

                            getFilesOfTheFolder(fileName, fileNames);

                        }

                    }

                }
                if (subStream != null) {
                    subStream.close();
                }

            }

            dbg("end of getFilesInsideInstituteFolder");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        } finally {
            try {

                if (subStream != null) {
                    subStream.close();
                }
            } catch (IOException ex) {
                throw new BSProcessingException("Exception" + ex.toString());
            }

            try {

                if (stream != null) {
                    stream.close();
                }
            } catch (IOException ex) {
                throw new BSProcessingException("Exception" + ex.toString());
            }

        }

    }

    private void getFilesOfTheFolder(String folderName, ArrayList<String> fileList) throws BSProcessingException {
        DirectoryStream<Path> stream = null;
        DirectoryStream<Path> subStream = null;
        try {

            dbg("inside getFilesOfTheFolder");
            Path folderPath = Paths.get(folderName);
            stream = Files.newDirectoryStream(folderPath);
            String fileExtension = i_db_properties.getProperty("FILE_EXTENSION");

            for (Path file : stream) {

                dbg("file" + file.toString());

                subStream = Files.newDirectoryStream(file);

                for (Path subFile : subStream) {

                    String fileName = subFile.toString();
                    dbg("fileName" + fileName);

                    String substring = fileName.substring(fileName.length() - fileExtension.length(), fileName.length());

                    dbg("substring" + substring);

                    if (fileExtension.equals(substring)) {

                        fileList.add(fileName);
                    }

                }

                if (subStream != null) {
                    subStream.close();
                }

            }

            dbg("end of getFilesOfTheFolder");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        } finally {

            try {

                if (subStream != null) {
                    subStream.close();
                }
            } catch (IOException ex) {
                throw new BSProcessingException("Exception" + ex.toString());
            }

            try {

                if (stream != null) {
                    stream.close();
                }
            } catch (IOException ex) {
                throw new BSProcessingException("Exception" + ex.toString());
            }
        }

    }

    public Role[] getAllRolesOftheUser(String userID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {

            dbg("inside getAllRolesOftheUser");
            dbg("userID" + userID);

            IPDataService pds = inject.getPdataservice();
            Map<String, ArrayList<String>> classEntityMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_CLASS_ENTITY_ROLEMAPPING", session, dbSession);
            Map<String, List<ArrayList<String>>> classRoles = classEntityMap.values().stream().filter(rec -> rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec -> rec.get(1).trim()));
            dbg("classRoles size" + classRoles.size());
            Map<String, ArrayList<String>> teacherEntityMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_TEACHER_ENTITY_ROLEMAPPING", session, dbSession);
            Map<String, List<ArrayList<String>>> teacherRoles = teacherEntityMap.values().stream().filter(rec -> rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec -> rec.get(1).trim()));
            dbg("teacherRoles size" + teacherRoles.size());
            Map<String, ArrayList<String>> instituteEntityMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_INSTITUTE_ENTITY_ROLEMAPPING", session, dbSession);
            Map<String, List<ArrayList<String>>> instituteRoles = instituteEntityMap.values().stream().filter(rec -> rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec -> rec.get(1).trim()));
            dbg("instituteRoles size" + instituteRoles.size());

            Role[] roleArray = new Role[classRoles.keySet().size() + teacherRoles.keySet().size() + instituteRoles.size()];

            Iterator<String> keyIterator = classRoles.keySet().iterator();

            dbg("class iteration starts");
            int i = 0;
            while (keyIterator.hasNext()) {

                String roleID = keyIterator.next();
                dbg("getAllRolesOftheUser-->class iteration--->roleID" + roleID);
                roleArray[i] = new Role();
                roleArray[i].setRoleID(roleID);
                List<ArrayList<String>> values = classRoles.get(roleID);
                dbg("getAllRolesOftheUser-->class iteration--->values size" + values.size());
                Entity[] entity = new Entity[values.size()];
                for (int j = 0; j < values.size(); j++) {

                    String standard = values.get(j).get(2).trim();
                    String section = values.get(j).get(3).trim();
                    dbg("getAllRolesOftheUser-->class iteration--->standard" + standard);
                    dbg("getAllRolesOftheUser-->class iteration--->section" + section);
                    entity[j] = new Entity();
                    entity[j].setStandard(standard);
                    entity[j].setSection(section);
                }
                roleArray[i].setEntity(entity);
                i++;
            }
            dbg("class iteration ends");

            dbg("teacher iteration starts");

            Iterator<String> teacherIterator = teacherRoles.keySet().iterator();

            while (teacherIterator.hasNext()) {

                String roleID = teacherIterator.next();
                dbg("getAllRolesOftheUser-->teacher iteration--->roleID" + roleID);
                roleArray[i] = new Role();
                roleArray[i].setRoleID(roleID);
                List<ArrayList<String>> values = teacherRoles.get(roleID);
                dbg("getAllRolesOftheUser-->teacher iteration--->values size" + values.size());
                Entity[] entity = new Entity[values.size()];

                for (int j = 0; j < values.size(); j++) {
                    String instituteID = values.get(j).get(2).trim();
                    String teacherID = values.get(j).get(3).trim();
                    dbg("getAllRolesOftheUser-->class iteration--->instituteID" + instituteID);
                    dbg("getAllRolesOftheUser-->class iteration--->teacherID" + teacherID);
                    entity[j] = new Entity();
                    entity[j].setInstituteID(instituteID);
                    entity[j].setTeacherID(teacherID);
                }
                roleArray[i].setEntity(entity);
                i++;
            }

            dbg("teacher iteration ends");

            dbg("institute iteration starts");
            Iterator<String> instituteIterator = instituteRoles.keySet().iterator();
            while (instituteIterator.hasNext()) {

                String roleID = instituteIterator.next();
                dbg("getAllRolesOftheUser-->institute iteration--->roleID" + roleID);
                roleArray[i] = new Role();
                roleArray[i].setRoleID(roleID);
                List<ArrayList<String>> values = instituteRoles.get(roleID);
                dbg("getAllRolesOftheUser-->institute iteration--->values size" + values.size());
                Entity[] entity = new Entity[values.size()];
                for (int j = 0; j < values.size(); j++) {

                    String instituteID = values.get(j).get(2).trim();
                    dbg("getAllRolesOftheUser-->class iteration--->instituteID" + instituteID);
                    entity[j] = new Entity();
                    entity[j].setInstituteID(instituteID);
                }
                roleArray[i].setEntity(entity);
                i++;
            }

            dbg("roleList size" + roleArray.length);
            dbg("end of getAllRolesOftheUser");
            return roleArray;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public boolean authAccessValidation(String userID, String service, String operation, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {
        boolean status = false;
        try {

            dbg("inside getAllRolesOftheUser");
            dbg("userID" + userID);

            IPDataService pds = inject.getPdataservice();
            Map<String, ArrayList<String>> classEntityMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_CLASS_ENTITY_ROLEMAPPING", session, dbSession);
            Map<String, List<ArrayList<String>>> classRoles = classEntityMap.values().stream().filter(rec -> rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec -> rec.get(1).trim()));
            dbg("classRoles size" + classRoles.size());
            Map<String, ArrayList<String>> teacherEntityMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_TEACHER_ENTITY_ROLEMAPPING", session, dbSession);
            Map<String, List<ArrayList<String>>> teacherRoles = teacherEntityMap.values().stream().filter(rec -> rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec -> rec.get(1).trim()));
            dbg("teacherRoles size" + teacherRoles.size());
            Map<String, ArrayList<String>> instituteEntityMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_INSTITUTE_ENTITY_ROLEMAPPING", session, dbSession);
            Map<String, List<ArrayList<String>>> instituteRoles = instituteEntityMap.values().stream().filter(rec -> rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec -> rec.get(1).trim()));
            dbg("instituteRoles size" + instituteRoles.size());

            ArrayList<String> roleList = new ArrayList();

            Iterator<String> keyIterator = classRoles.keySet().iterator();

            dbg("class iteration starts");
            while (keyIterator.hasNext()) {

                String roleID = keyIterator.next();

                if (!roleList.contains(roleID)) {

                    roleList.add(roleID);
                }

            }
            dbg("class iteration ends");

            dbg("teacher iteration starts");

            Iterator<String> teacherIterator = teacherRoles.keySet().iterator();

            while (teacherIterator.hasNext()) {

                String roleID = teacherIterator.next();
                if (!roleList.contains(roleID)) {

                    roleList.add(roleID);
                }
            }

            dbg("teacher iteration ends");

            dbg("institute iteration starts");
            Iterator<String> instituteIterator = instituteRoles.keySet().iterator();
            while (instituteIterator.hasNext()) {

                String roleID = instituteIterator.next();
                if (!roleList.contains(roleID)) {

                    roleList.add(roleID);
                }
            }
            dbg("institute iteration ends");
            dbg("roleList size" + roleList.size());

            Map<String, String> serviceOperationMap = new HashMap();

            for (int i = 0; i < roleList.size(); i++) {

                String roleID = roleList.get(i);
                dbg("inside roleList iteration-->roleID-->" + roleID);

                Map<String, ArrayList<String>> roleDetailMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_USER_ROLE_DETAIL", session, dbSession);

                dbg("roleDetailMap size-->" + roleDetailMap.size());

                Map<String, List<ArrayList<String>>> serviceGroup = roleDetailMap.values().stream().filter(rec -> rec.get(0).trim().equals(roleID)).collect(Collectors.groupingBy(rec -> rec.get(1).trim() + "~" + rec.get(6).trim()));

                dbg("serviceGroup size-->" + serviceGroup.size());

                Iterator<String> keyyIterator = serviceGroup.keySet().iterator();

                while (keyyIterator.hasNext()) {

                    String key = keyyIterator.next();
                    String l_service = key.split("~")[0];
                    String l_operation = key.split("~")[1];
                    dbg("l_service-->" + l_service);
                    dbg("l_operation-->" + l_operation);

                    serviceOperationMap.put(l_service, l_operation);
                }

            }

            if (serviceOperationMap.containsKey("ALL")) {

                dbg("serviceOperationMap containsKey ALL");

                if (serviceOperationMap.get("ALL").equals("true")) {

                    dbg("serviceOperationMap containsKey ALL  and true");

                    status = true;
                }
            } else if (serviceOperationMap.containsKey(service)) {

                dbg("serviceOperationMap containsKey-->" + service);

                if (serviceOperationMap.get(service).equals("true")) {

                    dbg("serviceOperationMap containsKey-->" + service + "-->" + true);

                    status = true;
                }

            }

            return status;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getServicesOftheRole(String roleID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject, String operation) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {

            dbg("inside getServicesOftheRole");
            dbg("inside getServicesOftheRole--->operation" + operation);
            ArrayList<String> serviceList = new ArrayList();
            dbg("roleID" + roleID);
            IPDataService pds = inject.getPdataservice();
            IDBReadBufferService readBuffer = inject.getDBReadBufferService();
            Map<String, ArrayList<String>> roleDetailMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_USER_ROLE_DETAIL", session, dbSession);
            Map<String, List<ArrayList<String>>> serviceGroup = null;

            String[] l_pkey = {roleID};
            DBRecord l_masterRecord = readBuffer.readRecord("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_USER_ROLE_MASTER", l_pkey, session, dbSession);

            String masterVersion = l_masterRecord.getRecord().get(8).trim();

            int versionIndex = this.getVersionIndexOfTheTable("UVW_USER_ROLE_DETAIL", "USER", session, dbSession, inject);

            if (operation.equals("CUD")) {

                serviceGroup = roleDetailMap.values().stream().filter(rec -> rec.get(0).trim().equals(roleID) && rec.get(2).trim().equals("true") && rec.get(3).trim().equals("true") && rec.get(4).trim().equals("true") && rec.get(versionIndex).trim().equals(masterVersion)).collect(Collectors.groupingBy(rec -> rec.get(1).trim()));
            } else {

                serviceGroup = roleDetailMap.values().stream().filter(rec -> rec.get(0).trim().equals(roleID) && rec.get(6).trim().equals("true") && rec.get(versionIndex).trim().equals(masterVersion)).collect(Collectors.groupingBy(rec -> rec.get(1).trim()));
            }

            Iterator<String> serviceIterator = serviceGroup.keySet().iterator();

            while (serviceIterator.hasNext()) {

                String service = serviceIterator.next();
                dbg("service" + service);
                serviceList.add(service);

            }

            dbg("serviceList size" + serviceList.size());
            dbg("end of getServicesOftheRole");
            return serviceList;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public Role[] getRolesOfTheUser(String l_userID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject, String operation) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {

            dbg("inside getRolesOfTheUser");
            dbg("l_userID" + l_userID);

            Role[] rolesList = getAllRolesOftheUser(l_userID, session, dbSession, inject);
            dbg("inside getRolesOfTheUser->rolesList size" + rolesList.length);

            if (rolesList.length > 0) {

                dbg("role iteration starts in getRolesOfTheUser");
                for (int i = 0; i < rolesList.length; i++) {

                    String roleID = rolesList[i].getRoleID();
                    ArrayList<String> services = getServicesOftheRole(roleID, session, dbSession, inject, operation);

                    if (services.size() > 0) {

                        Map<String, String> serviceMap = new HashMap();

                        for (int j = 0; j < services.size(); j++) {

                            String service = services.get(j);
                            dbg("inside getRolesOfTheUser  service" + service);
                            if (!serviceMap.containsKey(service)) {

                                if (!service.contains("Summary") && !service.contains("Calender") && !service.contains("DashBoard")) {

                                    if (!service.equals("ALL")) {
                                        String serviceType = getServiceType(service, session, dbSession, inject);
                                        serviceMap.put(service, serviceType);
                                    } else {

                                        getAllServices(serviceMap, session, dbSession, inject);
                                    }
                                }
                            }
                        }

                        rolesList[i].setService(serviceMap);
                    }

                }
            }

            dbg("end of getRolesOfTheUser");
            return rolesList;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    private void getAllServices(Map<String, String> serviceNameMap, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getAllServices");
            IPDataService pds = inject.getPdataservice();
            Map<String, ArrayList<String>> serviceMap = pds.readTablePData("APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "SERVICE_TYPE_MASTER", session, dbSession);
            Iterator<String> serviceIterator = serviceMap.keySet().iterator();
            while (serviceIterator.hasNext()) {
                String service = serviceIterator.next();
                if (!service.contains("Summary") && !service.contains("Calender") && !service.contains("DashBoard")) {

                    String serviceType = getServiceType(service, session, dbSession, inject);
                    dbg("service getting from service map" + service);
                    dbg("serviceType getting from service map" + serviceType);
                    serviceNameMap.put(service, serviceType);

                }
            }

            dbg("end of  getAllServices");
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    private String getServiceType(String serviceName, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getServiceType" + serviceName);
            IPDataService pds = inject.getPdataservice();
            String[] l_pkey = {serviceName};
            ArrayList<String> serviceRecord = pds.readRecordPData(session, dbSession, "APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "SERVICE_TYPE_MASTER", l_pkey);
            String serviceType = serviceRecord.get(1).trim();
            dbg("serviceType from table" + serviceType);

            switch (serviceType) {

                case "S":

                    return "Student";

                case "T":

                    return "Teacher";
                case "I":

                    return "Institute";
                case "U":

                    return "User";
                case "C":

                    return "Class";
            }

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        return null;
    }

    public String getMasterTableOfTheService(String service, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {

            dbg("inside getMasterTableOfTheService-->service");
            String tableName = null;
            switch (service) {

                case "TeacherTimeTable":

                    tableName = "TVW_TEACHER_TIMETABLE_MASTER";

                    break;
                case "TeacherProfile":

                    tableName = "TVW_TEACHER_PROFILE";

                    break;
                case "TeacherAttendance":

                    tableName = "TVW_TEACHER_ATTENDANCE";

                    break;
                case "TeacherLeaveManagement":

                    tableName = "TVW_TEACHER_LEAVE_MANAGEMENT";

                    break;
                case "Payroll":

                    tableName = "TVW_PAYROLL";

                    break;

                case "StudentTimeTable":

                    tableName = "SVW_STUDENT_TIMETABLE_MASTER";

                    break;
                case "StudentProfile":

                    tableName = "SVW_STUDENT_PROFILE";

                    break;
                case "StudentLeaveManagement":

                    tableName = "SVW_STUDENT_LEAVE_MANAGEMENT";

                    break;
                case "StudentAssignment":

                    tableName = "SVW_STUDENT_ASSIGNMENT";

                    break;
                case "StudentPayment":

                    tableName = "SVW_STUDENT_PAYMENT";

                    break;
                case "StudentOtherActivity":

                    tableName = "SVW_STUDENT_OTHER_ACTIVITY";

                    break;

                case "StudentExamSchedule":

                    tableName = "SVW_STUDENT_EXAM_SCHEDULE_MASTER";

                    break;
                case "StudentFeeManagement":

                    tableName = "SVW_STUDENT_FEE_MANAGEMENT";

                    break;
                case "ClassMark":

                    tableName = "CLASS_MARK_ENTRY";

                    break;
                case "ClassAttendance":

                    tableName = "CLASS_ATTENDANCE_MASTER";

                    break;
                case "ClassAssignment":

                    tableName = "CLASS_ASSIGNMENT";

                    break;
                case "ClassFeeManagement":

                    tableName = "CLASS_FEE_MANAGEMENT";

                    break;
                case "ClassExamSchedule":

                    tableName = "CLASS_EXAM_SCHEDULE_MASTER";

                    break;
                case "ClassTimeTable":

                    tableName = "CLASS_TIMETABLE_MASTER";

                    break;
                case "ClassStudentMapping":

                    tableName = "CLASS_STUDENT_MAPPING";

                    break;
                case "StudentMaster":

                    tableName = "IVW_STUDENT_MASTER";

                    break;
                case "TeacherMaster":

                    tableName = "IVW_TEACHER_MASTER";

                    break;
                case "Notification":

                    tableName = "IVW_NOTIFICATION_MASTER";

                    break;
                case "InstituteOtherActivity":

                    tableName = "IVW_OTHER_ACTIVITY";

                    break;
                case "ClassLevelConfiguration":

                    tableName = "IVW_STANDARD_MASTER";

                    break;
                case "GroupMapping":

                    tableName = "IVW_GROUP_MAPPING_MASTER";

                    break;
                case "HolidayMaintenance":

                    tableName = "IVW_HOLIDAY_MAINTANENCE";

                    break;
                case "InstituteAssignment":

                    tableName = "IVW_ASSIGNMENT";

                    break;
                case "InstituteFeeManagement":

                    tableName = "IVW_FEE_MANAGEMENT";

                    break;
                case "InstitutePayment":

                    tableName = "IVW_INSTITUTE_PAYMENT";

                    break;
                case "UserProfile":

                    tableName = "UVW_USER_PROFILE";

                    break;
                case "UserRole":

                    tableName = "UVW_USER_ROLE_MASTER";

                    break;
                case "GeneralLevelConfiguration":

                    tableName = "INSTITUTE_MASTER";

                    break;
                case "StudentAttendance":

                    tableName = "SVW_STUDENT_ATTENDANCE";

                    break;

            }

            dbg("tableName" + tableName);
            dbg("end of getMasterTableOfTheService");
            return tableName;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getAccessibleClasses(String userID, String instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {

            dbg("inside getAccessibleClasses");
            ArrayList<String> classList = new ArrayList();
            dbg("userID" + userID);
            IBDProperties i_db_properties = session.getCohesiveproperties();
            IPDataService pds = inject.getPdataservice();
            Map<String, ArrayList<String>> classRoleMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_CLASS_ENTITY_ROLEMAPPING", session, dbSession);
            dbg("classRoleMap size" + classRoleMap.size());

            int size = classRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(userID) && rec.get(2).trim().equals("ALL")).collect(Collectors.toList()).size();

            if (size > 0) {

                classList = getClassesOfTheInstitute(instituteID, session, dbSession, inject);

            } else {

                Map<String, List<ArrayList<String>>> classGroup = classRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec -> rec.get(2).trim() + "~" + rec.get(3).trim()));
                dbg("classGroup size" + classGroup.size());
                Iterator<String> teacherIterator = classGroup.keySet().iterator();

                while (teacherIterator.hasNext()) {
                    String classs = teacherIterator.next().trim();
                    dbg("classs" + classs);

                    classList.add(classs);

                }
            }

            dbg("end of getAccessibleClasses");
            return classList;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getAccessibleTeachers(String userID, String instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {

            dbg("inside getAccessibleTeachers");
            ArrayList<String> teachersList = new ArrayList();
            dbg("userID" + userID);
            IBDProperties i_db_properties = session.getCohesiveproperties();
            IPDataService pds = inject.getPdataservice();
            Map<String, ArrayList<String>> teacherRoleMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_TEACHER_ENTITY_ROLEMAPPING", session, dbSession);
            dbg("teacherRoleMap size" + teacherRoleMap.size());

            int size = teacherRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(userID) && rec.get(3).trim().equals("ALL")).collect(Collectors.toList()).size();

            if (size > 0) {

                getTeachersOfTheInstitute(instituteID, session, dbSession, inject);
            } else {

                Map<String, List<ArrayList<String>>> teacherGroup = teacherRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec -> rec.get(3).trim()));
                dbg("classGroup size" + teacherGroup.size());
                Iterator<String> teacherIterator = teacherGroup.keySet().iterator();

                while (teacherIterator.hasNext()) {
                    String teacherID = teacherIterator.next().trim();
                    dbg("teacherID" + teacherID);
                    teachersList.add(teacherID);
                }

            }

            dbg("end of getAccessibleTeachers");
            return teachersList;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getAccessibleInstitutes(String userID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {

            dbg("inside getAccessibleInstitutes");
            ArrayList<String> institutesList = new ArrayList();
            dbg("userID" + userID);
            IBDProperties i_db_properties = session.getCohesiveproperties();
            IPDataService pds = inject.getPdataservice();
            Map<String, ArrayList<String>> instituteRoleMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_INSTITUTE_ENTITY_ROLEMAPPING", session, dbSession);
            dbg("instituteRoleMap size" + instituteRoleMap.size());

            int size = instituteRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(userID) && rec.get(3).trim().equals("ALL")).collect(Collectors.toList()).size();

            if (size > 0) {

                institutesList = getAllInstitutes(session, dbSession, inject);
            } else {

                Map<String, List<ArrayList<String>>> instituteGroup = instituteRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(userID)).collect(Collectors.groupingBy(rec -> rec.get(2).trim()));
                dbg("classGroup size" + instituteGroup.size());
                Iterator<String> instituteIterator = instituteGroup.keySet().iterator();

                while (instituteIterator.hasNext()) {
                    String instituteID = instituteIterator.next().trim();
                    dbg("instituteID" + instituteID);
                    institutesList.add(instituteID);
                }
            }

            dbg("end of getAccessibleInstitutes");
            return institutesList;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public ArrayList<String> getAccessibleStudents(String userID, String instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {

            dbg("inside getAccessibleStudents");
            ArrayList<String> allStudentsList = new ArrayList();
            ArrayList<String> classList = getAccessibleClasses(userID, instituteID, session, dbSession, inject);

            for (int i = 0; i < classList.size(); i++) {

                String classs = classList.get(i);
                String standard = classs.split("~")[0];
                String section = classs.split("~")[1];

                ArrayList<String> studentList = getStudentsOfTheClass(instituteID, standard, section, session, dbSession, inject);

                for (int j = 0; j < studentList.size(); j++) {

                    String studentID = studentList.get(j);
                    dbg("studentID" + studentID);
                    allStudentsList.add(studentID);
                }
            }

            dbg("end of getAccessibleStudents");
            return allStudentsList;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public Map<String, String> getMaxVersionAttendanceOftheDay(String p_monthAttendance, String p_day) throws BSProcessingException {

        try {
            dbg("inside getMaxVersionAttendanceOftheDay");
            dbg("p_monthAttendance" + p_monthAttendance);
            dbg("p_day" + p_day);
            String[] attendanceArray = p_monthAttendance.split("d");
            dbg("attendanceArray" + attendanceArray.length);
            ArrayList<String> recordsFor_a_day = new ArrayList();
            String filterkey_dummy = p_day;
            for (int i = 1; i < attendanceArray.length; i++) {
                String dayRecord = attendanceArray[i];
                dbg("dayRecord" + dayRecord);
                String l_day = null;

                String dayAttendance = dayRecord.split(",")[0];

                if (dayAttendance.contains(" ")) {

                    l_day = dayAttendance.split(" ")[0];
                } else if (dayAttendance.contains("p")) {

                    l_day = dayAttendance.split("p")[0];
                }

                dbg("l_day" + l_day);
                if (l_day.equals(p_day)) {

                    dbg("l_day.equals(p_day)");
                    recordsFor_a_day.add(dayRecord);
                }

            }
            dbg("recordsFor_a_day size" + recordsFor_a_day.size());
            Map<String, String> filtermap_dummy = new HashMap();
            if (recordsFor_a_day.size() > 1) {
                int max_vesion = recordsFor_a_day.stream().mapToInt(rec -> Integer.parseInt(rec.split(",")[7])).max().getAsInt();
                recordsFor_a_day.stream().filter(rec -> Integer.parseInt(rec.split(",")[7]) == max_vesion).forEach(rec -> filtermap_dummy.put(filterkey_dummy, rec));
                dbg("max_vesion" + max_vesion);
            } else {

                filtermap_dummy.put(p_day, recordsFor_a_day.get(0).trim());
            }

            dbg("end of  getMaxVersionAttendanceOftheDay");
            return filtermap_dummy;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

    }

    public AuditDetails getAuditDetailsFromDayAttendance(String dayAttendance) throws BSProcessingException {

        try {
            dbg("inside getAuditDetailsFromDayAttendance");
            String[] attArr = dayAttendance.split(",");
            AuditDetails audit = new AuditDetails();
            audit.setMakerID(attArr[1].trim());
            audit.setCheckerID(attArr[2].trim());
            audit.setMakerDateStamp(attArr[3]);
            audit.setCheckerDateStamp(attArr[4]);
            audit.setRecordStatus(attArr[5]);
            audit.setAuthStatus(attArr[6]);
            audit.setVersionNo(attArr[7]);
            audit.setMakerRemarks(attArr[8]);
            audit.setCheckerRemarks(attArr[9]);

            dbg("makerID" + audit.getMakerID());
            dbg("checkerID" + audit.getCheckerID());
            dbg("makerDateStamp" + audit.getMakerDateStamp());
            dbg("checkerDateStamp" + audit.getCheckerDateStamp());
            dbg("RecordStatus" + audit.getRecordStatus());
            dbg("AuthStatus" + audit.getAuthStatus());
            dbg("versionNumber" + audit.getVersionNo());
            dbg("makerRemarks" + audit.getMakerRemarks());
            dbg("checkerRemarks" + audit.getCheckerRemarks());

            dbg("end of getAuditDetailsFromDayAttendance");
            return audit;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("BSProcessingException" + ex.toString());
        }

    }

    private Map<String, String> getMaxVersionAuditOftheDay(String p_monthAudit, String p_day, CohesiveSession session) throws BSProcessingException, BSValidationException {

        try {
            dbg("inside getMaxVersionAttendanceOftheDay");
            dbg("p_monthAttendance" + p_monthAudit);
            dbg("p_day" + p_day);
            String[] attendanceArray = p_monthAudit.split("d");
            dbg("attendanceArray" + attendanceArray.length);
            String audit_filterkey_dummy = p_day;
            ArrayList<String> recordsFor_a_day = new ArrayList();
            for (int i = 1; i < attendanceArray.length; i++) {
                String dayRecord = attendanceArray[i];
                dbg("dayRecord" + dayRecord);

                String l_day = dayRecord.split(",")[0];

                dbg("l_day" + l_day);
                if (l_day.equals(p_day)) {

                    dbg("l_day.equals(p_day)");

                    if (dayRecord.contains(",")) {

                        recordsFor_a_day.add(dayRecord);

                    }
                }

            }
            dbg("recordsFor_a_day size" + recordsFor_a_day.size());
            if (recordsFor_a_day.isEmpty()) {

                session.getErrorhandler().log_app_error("BS_VAL_013", null);
                throw new BSValidationException("BS_VAL_013");
            }
            Map<String, String> audit_filtermap_dummy = new HashMap();
            if (recordsFor_a_day.size() > 1) {
                int max_vesion = recordsFor_a_day.stream().mapToInt(rec -> Integer.parseInt(rec.split(",")[7])).max().getAsInt();
                recordsFor_a_day.stream().filter(rec -> Integer.parseInt(rec.split(",")[7]) == max_vesion).forEach(rec -> audit_filtermap_dummy.put(audit_filterkey_dummy, rec));
                dbg("max_vesion" + max_vesion);
            } else {

                audit_filtermap_dummy.put(p_day, recordsFor_a_day.get(0).trim());
            }

            dbg("end of  getMaxVersionAuditOftheDay");
            return audit_filtermap_dummy;
        } catch (BSValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
    }

    public AuditDetails getClassAuditDetails(String monthAudit, String p_day, CohesiveSession session) throws BSProcessingException, BSValidationException {

        try {
            dbg("inside getAuditDetails");
            Map<String, String> maxAudit = getMaxVersionAuditOftheDay(monthAudit, p_day, session);
            String existingDayAudit = maxAudit.get(p_day);
            String[] attArr = existingDayAudit.split(",");
            AuditDetails audit = new AuditDetails();
            audit.setMakerID(attArr[1]);
            audit.setCheckerID(attArr[2]);
            audit.setMakerDateStamp(attArr[3]);
            audit.setCheckerDateStamp(attArr[4]);
            audit.setRecordStatus(attArr[5]);
            audit.setAuthStatus(attArr[6]);
            audit.setVersionNo(attArr[7]);
            audit.setMakerRemarks(attArr[8]);
            audit.setCheckerRemarks(" ");

            dbg("makerID" + audit.getMakerID());
            dbg("checkerID" + audit.getCheckerID());
            dbg("makerDateStamp" + audit.getMakerDateStamp());
            dbg("checkerDateStamp" + audit.getCheckerDateStamp());
            dbg("RecordStatus" + audit.getRecordStatus());
            dbg("AuthStatus" + audit.getAuthStatus());
            dbg("versionNumber" + audit.getVersionNo());
            dbg("makerRemarks" + audit.getMakerRemarks());
            dbg("checkerRemarks" + audit.getCheckerRemarks());

            dbg("end of getAuditDetails");
            return audit;

        } catch (BSValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("BSProcessingException" + ex.toString());
        }

    }

    public void getStudentListFromClass(String standard, String section, String institutID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject, HashSet<String> studentsList, HashSet<String> classList) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside getStudentListFromClass--->standard" + standard);
            dbg("inside getStudentListFromClass--->section" + section);
            dbg("inside getStudentListFromClass--->institutID" + institutID);
            if (standard != null && !standard.equals("ALL")) {

                if (section != null) {

                    if (!section.equals("ALL")) {

                        getStudentsOfTheClass(institutID, standard, section, session, dbSession, inject, studentsList, classList);

                    } else {

                        ArrayList<String> sectionsList = getSectionsOfTheStandard(institutID, standard, session, dbSession, inject);

                        for (int i = 0; i < sectionsList.size(); i++) {

                            String l_section = sectionsList.get(i);

                            getStudentsOfTheClass(institutID, standard, l_section, session, dbSession, inject, studentsList, classList);

                        }

                    }
                }

            } else {

                if (standard != null) {

                    HashSet<String> standardList = getStandardsOfTheInstitute(institutID, session, dbSession, inject);

//                 for(int i=0;i<standardList.size();i++){
                    Iterator<String> standardIterator = standardList.iterator();

                    while (standardIterator.hasNext()) {

                        String l_standard = standardIterator.next();

                        if (section != null) {

                            if (!section.equals("ALL")) {

                                getStudentsOfTheClass(institutID, l_standard, section, session, dbSession, inject, studentsList, classList);

                            } else {

                                ArrayList<String> sectionsList = getSectionsOfTheStandard(institutID, l_standard, session, dbSession, inject);

                                for (int i = 0; i < sectionsList.size(); i++) {

                                    String l_section = sectionsList.get(i);

                                    getStudentsOfTheClass(institutID, l_standard, l_section, session, dbSession, inject, studentsList, classList);

                                }

                            }
                        }

                    }

                }

            }

            dbg("inside getStudentListFromClass--->classList" + classList.size());
            dbg("inside getStudentListFromClass--->studentsList" + studentsList.size());
//           return  studentsList;

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public String getDayNumber(String day) throws BSProcessingException {

        try {

            dbg("inside getDayNumber" + day);
            switch (day) {

                case "Mon":

                    return "1";
                case "Tue":

                    return "2";

                case "Wed":

                    return "3";
                case "Thu":

                    return "4";
                case "Fri":

                    return "5";
                case "Sat":

                    return "6";
                case "Sun":

                    return "7";

            }

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        return null;
    }

    public void updateEmailUsage(String instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {
        try {

            dbg("inside updateEmailUsage");
            IPDataService pds = inject.getPdataservice();
            IDBTransactionService dbts = inject.getDBTransactionService();

            String[] pkey = {instituteID};
            ArrayList<String> contractList = pds.readRecordPData(session, dbSession, "APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "CONTRACT_MASTER", pkey);

            int emailUsed = Integer.parseInt(contractList.get(5).trim());

            Map<String, String> l_column_to_update = new HashMap();
            l_column_to_update.put("6", Integer.toString(emailUsed + 1));

            dbts.updateColumn("APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "CONTRACT_MASTER", pkey, l_column_to_update, session);

            dbg("End of updateEmailUsage");
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

    }

    public void updateSMSUsage(String instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject, String message) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {
        try {

            dbg("inside updateSMSUsage");
            IPDataService pds = inject.getPdataservice();
            IDBTransactionService dbts = inject.getDBTransactionService();

            String[] pkey = {instituteID};
            ArrayList<String> contractList = pds.readRecordPData(session, dbSession, "APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "CONTRACT_MASTER", pkey);

            int smsUsed = Integer.parseInt(contractList.get(4).trim());

            int increaseCount;

            int bufferLimit = ByteBuffer.wrap(message.getBytes(Charset.forName("UTF-8"))).limit();

            increaseCount = bufferLimit / 140;

            if (bufferLimit % 140 > 0) {

                increaseCount = increaseCount + 1;

            }
            dbg("increase count" + increaseCount);
            Map<String, String> l_column_to_update = new HashMap();
//           l_column_to_update.put("5", Integer.toString(smsUsed+1));
            l_column_to_update.put("5", Integer.toString(smsUsed + increaseCount));
            dbts.updateColumn("APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "CONTRACT_MASTER", pkey, l_column_to_update, session);

            dbg("End of NotificationService--->updateSMSUsage");
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

    }

    public ArrayList<String> getCompletedExams(String instituteID, String standard, String section, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {

        try {
            dbg("inside getCompletedExams");
            IDBReadBufferService readBuffer = inject.getDBReadBufferService();
            ArrayList<String> examList = new ArrayList();
            Map<String, DBRecord> l_examScheduleMap = readBuffer.readTable("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "CLASS" + i_db_properties.getProperty("FOLDER_DELIMITER") + standard + section + i_db_properties.getProperty("FOLDER_DELIMITER") + "ExamSchedules", "CLASS", "CLASS_EXAM_SCHEDULE_DETAIL", session, dbSession);
            String dateformat = i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);

            Map<String, List<DBRecord>> examWiseGroup = l_examScheduleMap.values().stream().collect(Collectors.groupingBy(rec -> rec.getRecord().get(2)));

            Iterator<String> keyIterator = examWiseGroup.keySet().iterator();

            while (keyIterator.hasNext()) {

                String exam = keyIterator.next();
                boolean includeExam = true;
                List<DBRecord> value = examWiseGroup.get(exam);

                for (int i = 0; i < value.size(); i++) {

                    Date examDate = formatter.parse(value.get(i).getRecord().get(4).trim());
                    Date currentDate = formatter.parse(this.getCurrentDate());

                    if (!(examDate.compareTo(currentDate) <= 0)) {
                        includeExam = false;
                    }

                }

                if (includeExam) {

                    dbg("including--->" + exam);
                    examList.add(exam);
                }
            }
            dbg("examList size" + examList.size());
            dbg("END OF getCompletedExams");
            return examList;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    }

    public boolean checkClassExistenceInTheGroup(String instituteID, String standard, String section, String groupID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {

        try {
            dbg("inside getClassExistenceInTheGroup");
            boolean status = false;
            IPDataService pds = inject.getPdataservice();

            Map<String, ArrayList<String>> l_detailMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID, "INSTITUTE", "IVW_GROUP_MAPPING_DETAIL", session, dbSession);
            dbg("l_detailMap" + l_detailMap.size());
            List<ArrayList<String>> filteredList = l_detailMap.values().stream().filter(rec -> rec.get(1).trim().equals(groupID)).collect(Collectors.toList());
            dbg("filteredList" + filteredList.size());

            Iterator<ArrayList<String>> valueIterator = filteredList.iterator();
            while (valueIterator.hasNext()) {

                ArrayList<String> value = valueIterator.next();
                String l_standard = value.get(2).trim();
                String l_section = value.get(3).trim();
                String l_studentID = value.get(4).trim();
                dbg("l_standard" + l_standard);
                dbg("l_section" + l_section);
                dbg("l_studentID" + l_studentID);

                if (l_standard.equals(standard) && l_section.equals(section)) {

                    status = true;

                } else if (!l_studentID.equals("dum")) {

                    String[] studentPkey = {l_studentID};
                    ArrayList<String> l_studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", studentPkey);
                    l_standard = l_studentList.get(2).trim();
                    l_section = l_studentList.get(3).trim();

                    if (l_standard.equals(standard) && l_section.equals(section)) {

                        status = true;

                    }
                }

            }

            dbg("status" + status);
            dbg("END OF getClassExistenceInTheGroup");
            return status;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    }

    public boolean checkStudentExistenceInTheClass(String instituteID, String standard, String section, String l_studentID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {

        try {
            dbg("inside getClassExistenceInTheGroup");
            boolean status = false;
            IPDataService pds = inject.getPdataservice();

            String[] studentPkey = {l_studentID};
            ArrayList<String> l_studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", studentPkey);
            String l_standard = l_studentList.get(2).trim();
            String l_section = l_studentList.get(3).trim();

            if (l_standard.equals(standard) && l_section.equals(section)) {

                status = true;

            }

            dbg("END OF getClassExistenceInTheGroup");
            return status;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    }

    public int getStartDateofTheExam(String instituteID, String standard, String section, String exam, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {

        try {
            dbg("inside getClassExistenceInTheGroup");
//            boolean status=false;
//            IPDataService pds=inject.getPdataservice();

            IDBReadBufferService readBuffer = inject.getDBReadBufferService();

            Map<String, DBRecord> l_examScheduleMap = readBuffer.readTable("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "CLASS" + i_db_properties.getProperty("FOLDER_DELIMITER") + standard + section + i_db_properties.getProperty("FOLDER_DELIMITER") + "ExamSchedules", "CLASS", "CLASS_EXAM_SCHEDULE_DETAIL", session, dbSession);

            List<DBRecord> examWiseList = l_examScheduleMap.values().stream().filter(rec -> rec.getRecord().get(2).trim().equals(exam)).collect(Collectors.toList());
            int[] subjectDateMap = new int[examWiseList.size()];

            for (int i = 0; i < examWiseList.size(); i++) {

                String subjectID = examWiseList.get(i).getRecord().get(3).trim();
                String date = examWiseList.get(i).getRecord().get(4).trim();
                String[] dateArr = date.split("-");
                int dateNumber = Integer.parseInt(dateArr[2] + dateArr[1] + dateArr[0]);
                dbg("dateNumber" + dateNumber);
                subjectDateMap[i] = dateNumber;

            }

            Arrays.sort(subjectDateMap);
            dbg("subjectDateMap[0]" + subjectDateMap[0]);

            dbg("END OF getClassExistenceInTheGroup");
            return subjectDateMap[0];
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    }

    public String getLastPeriod(String instituteID, String standard, String section, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {

        try {

            dbg("inside get last period");

            IPDataService pds = inject.getPdataservice();

            String masterVersion = this.getMaxVersionOfTheClass(instituteID, standard, section, session, dbSession, inject);
            int versionIndex = this.getVersionIndexOfTheTable("IVW_PERIOD_MASTER", "INSTITUTE", session, dbSession, inject);

            Map<String, ArrayList<String>> l_periodList = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID, "INSTITUTE", "IVW_PERIOD_MASTER", session, dbSession);

            Map<Integer, List<ArrayList<String>>> periodList = l_periodList.values().stream().filter(rec -> rec.get(1).trim().equals(standard) && rec.get(2).trim().equals(section) && rec.get(versionIndex).trim().equals(masterVersion)).collect(Collectors.groupingBy(rec -> Integer.parseInt(rec.get(3).trim())));

            List<Integer> periodNumbers = new ArrayList(periodList.keySet());

            Collections.sort(periodNumbers);

            int lastPeriod = periodNumbers.get(periodNumbers.size() - 1);
            dbg("lastPeriod" + lastPeriod);

            dbg("end of get last period");
            return Integer.toString(lastPeriod);
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    }

    public float getBalanceAmount(String l_instituteID, String l_studentID, String l_feeID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException {

        try {
            dbg("Inside getBalanceAmount");
            IDBReadBufferService readBuffer = inject.getDBReadBufferService();

            String[] l_pkey = {l_feeID};

            DBRecord feeManagementRecord = readBuffer.readRecord("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "Fee", "INSTITUTE", "INSTITUTE_FEE_MANAGEMENT", l_pkey, session, dbSession);

            float feeAmount = Float.parseFloat(feeManagementRecord.getRecord().get(5).trim());

            dbg("feeAmount" + feeAmount);

            Map<String, DBRecord> InstituteFeePaymentMap = null;

            try {

                InstituteFeePaymentMap = readBuffer.readTable("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "FEE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_feeID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_feeID, "FEE", "INSTITITUTE_FEE_PAYMENT", session, dbSession);

            } catch (DBValidationException ex) {
                dbg("exception in view operation" + ex);
                if (ex.toString().contains("DB_VAL_011") || ex.toString().contains("DB_VAL_000")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");

                } else {

                    throw ex;
                }
            }
            float totalPaidAmount = 0;
            if (InstituteFeePaymentMap != null) {

                List<DBRecord> FilteredRecords = InstituteFeePaymentMap.values().stream().filter(rec -> rec.getRecord().get(0).trim().equals(l_studentID)).collect(Collectors.toList());

                dbg("FilteredRecords size" + FilteredRecords.size());
                for (int i = 0; i < FilteredRecords.size(); i++) {

                    DBRecord feePaymentRecord = FilteredRecords.get(i);
                    float paidAmount = Float.parseFloat(feePaymentRecord.getRecord().get(3).trim());
                    totalPaidAmount = totalPaidAmount + paidAmount;
                }

            }
            dbg("totalPaidAmount");

            float balanceAmount = feeAmount - totalPaidAmount;

            dbg("End of getBalanceAmount--->" + balanceAmount);
            return balanceAmount;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception".concat(ex.toString()));
        }
    }

    public String studentValidation(String studentID, String studentName, String instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {

            dbg("inside student validation");
            BSValidation bsv = inject.getBsv(session);
            IPDataService pds = inject.getPdataservice();
            boolean status = true;

            if (studentID != null && !studentID.isEmpty()) {

//                if (!bsv.studentIDValidation(studentID, instituteID, session, dbSession, inject)) {
//
//                    status = false;
//                    session.getErrorhandler().log_app_error("BS_VAL_003", "Student ID");
//                    throw new BSValidationException("BSValidationException");
//
//                }

            } else if (studentName != null && !studentName.isEmpty()) {

                Map<String, ArrayList<String>> studentsMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", session, dbSession);

                List<ArrayList<String>> nameFilteredRecords = studentsMap.values().stream().filter(rec -> rec.get(1).trim().equalsIgnoreCase(studentName)).collect(Collectors.toList());

                if (nameFilteredRecords == null || nameFilteredRecords.isEmpty()) {

                    status = false;
                    session.getErrorhandler().log_app_error("BS_VAL_003", "Student Name");
                    throw new BSValidationException("BSValidationException");

                } else if (nameFilteredRecords.size() > 1) {

                    status = false;
                    session.getErrorhandler().log_app_error("BS_VAL_064", null);
                    throw new BSValidationException("BSValidationException");
                } else {

                    studentID = nameFilteredRecords.get(0).get(0).trim();

                    dbg("student id for the name" + studentID);

                }

            }

            dbg("end of student validation");
            return studentID;
        } catch (BSValidationException ex) {
            throw ex;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception".concat(ex.toString()));
        }

    }

    public void setStudentIDInBusinessEntity(String studentID, Request request) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {

            dbg("inside setStudentIDInBusinessEntity");

            request.getReqHeader().getBusinessEntity().replace("studentID", studentID);

            dbg("inside setStudentIDInBusinessEntity");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception".concat(ex.toString()));
        }

    }

    
    public String getActivityType(String activtyTypeChar) throws BSProcessingException {

        try {

            dbg("inside getActivityType" + activtyTypeChar);
            switch (activtyTypeChar) {

                case "S":

                    return "Sports";
                case "C":

                    return "Culturals";

                

            }

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        return "";
    }
    
    
    public String getAssignmentType(String assignmentTypeChar) throws BSProcessingException {

        try {

            dbg("inside getAssignmentType" + assignmentTypeChar);
            switch (assignmentTypeChar) {

                case "H":

                    return "Homework";
                case "T":

                    return "Term/Exam";

                case "P":

                    return "Punishment";
                case "I":

                    return "Improvement";

            }

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        return "";
    }
    
    
    public String getActivityLeavel(String activityLevelChar) throws BSProcessingException {

        try {

            dbg("inside getActivityLeavel" + activityLevelChar);
            switch (activityLevelChar) {

                case "S":

                    return "State";
                case "D":

                    return "District";

                case "I":

                    return "International";
                case "E":

                    return "Internal";

            }

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        return "";
    }
    
    public ArrayList getSortedClasses(Map<String,ArrayList<String>>l_classMap)throws BSProcessingException{
        ArrayList<String>sortedClasses=new ArrayList();
        try{
            dbg("inside getSortedClasses");
            
//             Map<Integer,List<ArrayList<String>>>standardGroup=l_classMap.values().stream().collect(Collectors.groupingBy(rec->Integer.parseInt(rec.get(1).trim())));
               Map<String,List<ArrayList<String>>>standardGroup=l_classMap.values().stream().collect(Collectors.groupingBy(rec->rec.get(1).trim()));
               dbg("standardGroup size");         
               List<Integer>integerClasses=new ArrayList();
               List<String>nonIntegerClasses=new ArrayList();
               Iterator<String>keyIterator=standardGroup.keySet().iterator();
               while(keyIterator.hasNext()){
                   
                   String standard=keyIterator.next();
                        try{
                           
                              Integer.parseInt(standard);
                              dbg("After parseint");
                              integerClasses.add(Integer.parseInt(standard));
                           }catch(NumberFormatException ex){
                 
                               nonIntegerClasses.add(standard);
                           }
                   
                   
               }
               
               dbg("integerClasses size"+integerClasses.size());
               dbg("nonIntegerClasses size"+nonIntegerClasses.size());
             
                       
                       Collections.sort(integerClasses);
                       
                       for(int standard:integerClasses){
                           
                           List<ArrayList<String>>recordsForTheStandard=standardGroup.get(Integer.toString(standard));
                           
                           Map<String,List<ArrayList<String>>>sectionGroup=recordsForTheStandard.stream().collect(Collectors.groupingBy(rec->rec.get(2).trim()));
                           
                           List<String>sectionList=new ArrayList(sectionGroup.keySet());
                       
                           Collections.sort(sectionList);
                           
                           for(String section:sectionList){
                               
                               String classs=standard+"/"+section;
                               sortedClasses.add(classs);
                               
                           }
                           
                           
                       }
            
                       Collections.sort(nonIntegerClasses);
                       
                       for(String standard:nonIntegerClasses){
                           
                           List<ArrayList<String>>recordsForTheStandard=standardGroup.get(standard);
                           
                           Map<String,List<ArrayList<String>>>sectionGroup=recordsForTheStandard.stream().collect(Collectors.groupingBy(rec->rec.get(2).trim()));
                           
                           List<String>sectionList=new ArrayList(sectionGroup.keySet());
                       
                           Collections.sort(sectionList);
                           
                           for(String section:sectionList){
                               
                               String classs=standard+"/"+section;
                               sortedClasses.add(classs);
                               
                           }
                           
                           
                       }
                       
                       
            
            
            dbg("inside getSortedClasses");
            return sortedClasses;
            
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        
    }
    
   public String formatCurrency(String amount,String instituteID,CohesiveSession session,DBSession dbSession,AppDependencyInjection appInject)throws BSProcessingException{
       
       
       try{
           dbg("inside formatCurrency");
           dbg("amount"+amount);
           dbg("instituteID"+instituteID);
           String formattedAmount=null;
           IPDataService pds=appInject.getPdataservice();
           if(amount==null||amount.isEmpty()||amount.equals(" ")){
               
                formattedAmount= amount;              
               
           }else{
               String[] pkey = {instituteID};
            ArrayList<String> contractList = pds.readRecordPData(session, dbSession, "APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "CONTRACT_MASTER", pkey);
            String countryCode = contractList.get(13).trim();
            dbg("country code"+countryCode);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
             formatter.setCurrency(Currency 
                           .getInstance(new Locale("en", countryCode)));
             String moneyString = formatter.format(Float.parseFloat(amount)); 
             
             dbg("moneyString"+moneyString);
             if(countryCode.equals("IN")){
                 
                 
                 if(moneyString.contains("₹")){
                     
                     formattedAmount=  moneyString.substring(1);
                     
                 }else{
                 
               formattedAmount=  moneyString.substring(3);
                 }
               
             }else if(countryCode.equals("GB")){
                 
               formattedAmount=  moneyString.substring(3);
             }else if(countryCode.equals("US")){
                 
               formattedAmount=  moneyString.substring(3);
             }else if(countryCode.equals("AE")){
                 
               formattedAmount=  moneyString.substring(3);
             }
             
             
             
             
           }
           
           
           
           
           dbg("end of formattedAmount-->"+formattedAmount);
           return formattedAmount;
            } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        
           
       }
       
       
       
   }
    
   public String parseCurrency(String amount,String instituteID,CohesiveSession session,DBSession dbSession,AppDependencyInjection appInject)throws BSProcessingException,ParseException{
       
       
       try{
           dbg("inside parseCurrency");
           dbg("amount"+amount);
           dbg("instituteID"+instituteID);
           String formattedAmount=null;
           IPDataService pds=appInject.getPdataservice();
           if(amount==null||amount.isEmpty()||amount.equals(" ")){
               
                formattedAmount= amount;              
               
           }else{
               String[] pkey = {instituteID};
            ArrayList<String> contractList = pds.readRecordPData(session, dbSession, "APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "CONTRACT_MASTER", pkey);
            String countryCode = contractList.get(13).trim();
            dbg("country code"+countryCode);
            NumberFormat formatter = NumberFormat.getNumberInstance();
             formatter.setCurrency(Currency 
                           .getInstance(new Locale("en", countryCode)));
             
             
             float amountFloat = formatter.parse(amount).floatValue(); 
             
             
             
             
             dbg("amountFloat"+amountFloat);
            
             formattedAmount=Float.toString(amountFloat);
             
             
           }
           
           
           
           
           dbg("end of formattedAmount-->"+formattedAmount);
           return formattedAmount;
           } catch (ParseException ex) {
            dbg(ex);
            throw ex;
            } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        
           
       }
       
       
       
   }  
   
   
     public boolean checkStudentExistenceInTheGroup(String instituteID, String p_studentID, String groupID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {

        try {
            dbg("inside checkStudentExistenceInTheGroup");
            boolean status = false;
            IPDataService pds = inject.getPdataservice();
            String[] Pkey = {p_studentID};
            ArrayList<String>studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", Pkey);
            String standard = studentList.get(2).trim();
            String section = studentList.get(3).trim();

            Map<String, ArrayList<String>> l_detailMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID, "INSTITUTE", "IVW_GROUP_MAPPING_DETAIL", session, dbSession);
            dbg("l_detailMap" + l_detailMap.size());
            List<ArrayList<String>> filteredList = l_detailMap.values().stream().filter(rec -> rec.get(1).trim().equals(groupID)).collect(Collectors.toList());
            dbg("filteredList" + filteredList.size());

            Iterator<ArrayList<String>> valueIterator = filteredList.iterator();
            while (valueIterator.hasNext()) {

                ArrayList<String> value = valueIterator.next();
                String l_standard = value.get(2).trim();
                String l_section = value.get(3).trim();
                String l_studentID = value.get(4).trim();
                dbg("l_standard" + l_standard);
                dbg("l_section" + l_section);
                dbg("l_studentID" + l_studentID);

                if (l_standard.equals(standard) && l_section.equals(section)) {

                    status = true;

                } else if (!l_studentID.equals("dum")) {

                    String[] studentPkey = {l_studentID};
                    ArrayList<String> l_studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", studentPkey);
                    l_standard = l_studentList.get(2).trim();
                    l_section = l_studentList.get(3).trim();

                    if (l_standard.equals(standard) && l_section.equals(section)) {

                        status = true;

                    }
                }

            }

            dbg("status" + status);
            dbg("END OF checkStudentExistenceInTheGroup");
            return status;
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    }
    

   
   
   
   
   
    public void dbg(String p_Value) {

        this.debug.dbg(p_Value);

    }

    public void dbg(Exception ex) {

        this.debug.exceptionDbg(ex);

    }
}
