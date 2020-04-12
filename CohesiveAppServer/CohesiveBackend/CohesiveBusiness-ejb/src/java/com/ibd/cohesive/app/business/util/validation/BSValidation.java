/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.util.validation;

import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.Oauth.AuthServer.TokenValidateService;
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
import com.ibd.cohesive.app.business.institute.notification.Notification;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.student.studentECircular.StudentECircular;
import com.ibd.cohesive.app.business.student.studentassignment.StudentAssignment;
import com.ibd.cohesive.app.business.student.studentattendanceservice.StudentAttendance;
import com.ibd.cohesive.app.business.student.studentexamschedule.StudentExamSchedule;
import com.ibd.cohesive.app.business.student.studentfeemanagement.StudentFeeManagement;
import com.ibd.cohesive.app.business.student.studentleavemanagement.StudentLeaveManagement;
import com.ibd.cohesive.app.business.student.studentnotification.StudentNotification;
import com.ibd.cohesive.app.business.student.studentotheractivity.StudentOtherActivity;
import com.ibd.cohesive.app.business.student.studentpayment.StudentPayment;
import com.ibd.cohesive.app.business.student.studentprofile.StudentProfile;
import com.ibd.cohesive.app.business.student.studentprogresscard.StudentProgressCard;
import com.ibd.cohesive.app.business.student.studenttimetable.StudentTimeTable;
import com.ibd.cohesive.app.business.teacher.payroll.Payroll;
import com.ibd.cohesive.app.business.teacher.teacherattendance.TeacherAttendance;
import com.ibd.cohesive.app.business.teacher.teacherleavemanagement.TeacherLeaveManagement;
import com.ibd.cohesive.app.business.teacher.teacherprofile.TeacherProfile;
import com.ibd.cohesive.app.business.teacher.teachertimetable.TeacherTimeTable;
import com.ibd.cohesive.app.business.user.userprofile.UserProfile;
import com.ibd.cohesive.app.business.user.userrole.UserRole;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
//import com.ibd.cohesive.db.read.IDBReadService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.JWEInput;
import com.ibd.cohesive.util.debugger.Debug;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author IBD Technologies
 */
public class BSValidation {

    Debug debug;
    IBDProperties i_db_properties;
    Map<String, ArrayList<String>> filtermap_dummy;
    String filterkey_dummy;
    int maxversion_dummy;
    String studentID_dummy;
    String standard_dummy;
    String section_dummy;
    String teacherID_dummy;
    static int keyIterator;

    public Debug getDebug() {
        return debug;
    }

    public void setDebug(Debug debug) {
        this.debug = debug;
    }

    public IBDProperties getI_db_properties() {
        return i_db_properties;
    }

    public void setI_db_properties(IBDProperties i_db_properties) {
        this.i_db_properties = i_db_properties;
    }

    public boolean businessServiceValidation(JsonObject requestJson, ExistingAudit exAudit, Request request, ErrorHandler errhandler, AppDependencyInjection inject, CohesiveSession session, DBSession dbSession) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {
        boolean status = true;
        try {
            dbg("inside businessServiceValidation");
            IBusinessLockService businessLock = inject.getBusinessLockService();
            String operation = request.getReqHeader().getOperation();
            String l_userID = request.getReqHeader().getUserID();
            String l_service = request.getReqHeader().getService();

            if (!specialCharacterValidation(requestJson.toString(), errhandler)) {
                status = false;
                throw new BSValidationException("BSValidationException");
            }
            if (!headerValidation(request, errhandler, inject, session, dbSession)) {
                status = false;
                throw new BSValidationException("BSValidationException");
            }

            if (!auditLogMandatoryValidation(request, errhandler, inject, session)) {
                status = false;
                throw new BSValidationException("BSValidationException");
            }

            if (!(l_userID.equals("System") || l_userID.equals("Admin") || l_userID.equals("Teacher") || l_userID.equals("Parent"))) {

                if (!(l_service.contains("Summary") || l_service.contains("Search") || l_service.contains("SelectBox"))) {

                    if (!userAccessValidation(request, session, dbSession, inject)) {
                        status = false;
                        throw new BSValidationException("BSValidationException");
                    }

                } else {

                    if (l_service.contains("Summary") && l_service.contains("Student")) {

                        studentAccessValidation(request, session, dbSession, inject);

                    }

                }
            } else {

                if (l_userID.equals("System")) {

                    if (!(request.getReqHeader().getOperation().equals("Create-Default")||request.getReqHeader().getOperation().equals("Payment-Default") || (request.getReqHeader().getOperation().equals("Auth")) || (request.getReqHeader().getOperation().equals("Reject")))) {
                        request.setAutoAuthAccess("Y");
                        request.getReqAudit().setAuthStatus("A");
                        String makerID = request.getReqAudit().getMakerID();
                        String makerDateStamp = request.getReqAudit().getMakerDateStamp();
                        request.getReqAudit().setCheckerID(makerID);
                        request.getReqAudit().setCheckerDateStamp(makerDateStamp);
                    } else {

                        request.setAutoAuthAccess("N");
                    }

                } else {
                    request.setAutoAuthAccess("N");
                }
            }

            if (!auditLogDataValidation(request, exAudit, errhandler, inject, session, dbSession)) {
                status = false;
                throw new BSValidationException("BSValidationException");
            }

            if (!(operation.equals("Create-Default")||operation.equals("Payment-Default") || operation.equals("View"))) {
                lengthValidation(request, session, dbSession, inject);
            }

            dbg("end of businessServiceValidation");
        } catch (DBValidationException ex) {
            throw ex;
        } catch (BSValidationException ex) {
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
        } finally {

            filtermap_dummy = null;
            filterkey_dummy = null;
            maxversion_dummy = 0;
            studentID_dummy = null;
            standard_dummy = null;
            section_dummy = null;
            teacherID_dummy = null;

        }
        return status;

    }

    public boolean headerValidation(Request request, ErrorHandler errhandler, AppDependencyInjection inject, CohesiveSession session, DBSession dbSession) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {
        boolean status = true;
        try {
            dbg("inside BSValidation--->headerValidation");
            //JsonObject header=p_jsonObject.getJsonObject("header");
            String l_operation = request.getReqHeader().getOperation();
            dbg("operation is" + l_operation);
            String l_service = request.getReqHeader().getService();
            String l_userID = request.getReqHeader().getUserID();
            String l_instituteID = request.getReqHeader().getInstituteID();
            //String l_key=request.getReqHeader().getKey();
            String l_token = request.getReqHeader().getToken();

            if (!operationValidation(l_operation, errhandler, l_service)) {

                status = false;

            }
            if (!serviceValidation(l_service, errhandler, inject, session, dbSession)) {

                status = false;
            }
            if (!userIDValidation(l_userID, errhandler, l_instituteID, inject, session, dbSession)) {

                status = false;

            }

            if (!(l_userID.equals("System") || l_userID.equals("Admin") || l_userID.equals("Teacher") || l_userID.equals("Parent"))) {

                if (!l_service.equals("GeneralLevelConfiguration")) {

                    if (!instituteIDValidation(l_instituteID, errhandler, inject, session, dbSession)) {

                        status = false;
                    }

                }

            }

            if (!ResourceTokenValidation(l_token, l_userID, l_instituteID, l_service, errhandler, inject, session)) {

                status = false;
            }

            dbg("BSValidation--->headerValidation--->O/P--->status" + status);
            dbg("End of BSValidation--->headerValidation");
        } catch (DBValidationException ex) {

            throw ex;
        } catch (DBProcessingException ex) {

            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
        return status;
    }

    public boolean operationValidation(String p_operation, ErrorHandler errhandler, String l_service) throws BSProcessingException {
        boolean status = true;

        try {

            dbg("inside BSValidation--->opearationValidation");

            if (l_service.contains("Summary")) {

                if (!p_operation.equals("View")) {

                    status = false;
                    errhandler.log_app_error("BS_VAL_000", null);
                }

            } else {

                if (!(p_operation.equals("Create") || p_operation.equals("Delete") || p_operation.equals("View") || p_operation.equals("Modify") || p_operation.equals("Auth") || p_operation.equals("Reject") || p_operation.equals("AutoAuth") || p_operation.equals("Create-Default")|| p_operation.equals("Payment-Default"))) {

                    status = false;
                    errhandler.log_app_error("BS_VAL_000", null);
                }

            }

            dbg("BSValidation--->opearationValidation--->O/P--->status" + status);
            dbg("End of BSValidation--->opearationValidation");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

        return status;

    }

    public boolean specialCharacterValidation(String p_request, ErrorHandler errhandler) throws BSProcessingException {
        boolean status = true;

        try {
            dbg("inside BSValidation--->specialCharacterValidation");
            if (p_request.contains("~") || p_request.contains("#") || p_request.contains("@") || p_request.contains("^") || p_request.contains("<") || p_request.contains(">")) {

                status = false;
                errhandler.log_app_error("BS_VAL_001", null);
            }
            dbg("BSValidation--->specialCharacterValidation--->O/P--->status" + status);
            dbg("End of BSValidation--->specialCharacterValidation");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

        return status;

    }

    private JsonObject getJsonObject(String p_request) throws BSProcessingException {

        try (InputStream is = new ByteArrayInputStream(p_request.getBytes(Charset.forName("UTF-8")));
                JsonReader jsonReader = Json.createReader(is);) {

            JsonObject jsonObject = jsonReader.readObject();

            return jsonObject;

        } catch (IOException ex) {

            throw new BSProcessingException("IOException" + ex.toString());
        }
    }

    public JsonObject responseSpecialCharacterValidation(JsonObject p_response) throws BSProcessingException {
        boolean status = true;

        try {
            dbg("inside BSValidation--->responseSpecialCharacterValidation");
            String l_responseString = p_response.toString();
            if (l_responseString.contains("<") || l_responseString.contains(">")) {

                l_responseString.replace("<>", " ");
                return getJsonObject(l_responseString);

                // status=false;
            }
            dbg("BSValidation--->responseSpecialCharacterValidation--->O/P--->status" + status);
            dbg("End of BSValidation--->responseSpecialCharacterValidation");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

        return p_response;

    }

    public boolean serviceValidation(String p_service, ErrorHandler errhandler, AppDependencyInjection inject, CohesiveSession session, DBSession dbSession) throws BSProcessingException {
        boolean status = true;

        try {
            dbg("inside BSValidation--->serviceValidation");
            IPDataService pds = inject.getPdataservice();
            String[] l_pkey = {p_service};
            try {
                pds.readRecordPData(session, dbSession, "APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "SERVICE_TYPE_MASTER", l_pkey);
            } catch (DBValidationException ex) {
                //if(ex.toString().equals("DB_VAL_011")){
                if (ex.toString().contains("DB_VAL_011")) { //Integration changes
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    status = false;
                    errhandler.log_app_error("BS_VAL_004", null);
                } else {
                    throw ex;
                }
            }

//             if(!(p_service.equals("TeacherTimeTable")||p_service.equals("StudentTimeTable")||p_service.equals("TeacherProfile")||p_service.equals("StudentProfile")||p_service.equals("StudentAttendance")||p_service.equals("TeacherAttendance")||p_service.equals("ClassMark")||p_service.equals("StudentProgessCard")||p_service.equals("ClassAttendance")||p_service.equals("StudentLeaveManagement")||p_service.equals("TeacherLeaveManagement")||p_service.equals("ClassAssignment")||p_service.equals("StudentAssignment")||p_service.equals("ClassFeeManagement")||p_service.equals("StudentPayment")||p_service.equals("StudentOtherActivity")||p_service.equals("Payroll")||p_service.equals("Notification")||p_service.equals("ClassExamSchedule")||p_service.equals("StudentCalender")||p_service.equals("StudentExamSchedule")||p_service.equals("StudentFeeManagement")||p_service.equals("ClassTimeTable")||p_service.equals("StudentMaster")||p_service.equals("TeacherMaster")||p_service.equals("ClassStudentMapping")||p_service.equals("TeacherCalender")||p_service.equals("UserProfile")||p_service.equals("UserRole")||p_service.equals("GeneralLevelConfiguration")||p_service.equals("HolidayMaintenance")||p_service.equals("Notification")||p_service.equals("ClassLevelConfiguration")||p_service.equals("GroupMapping")||p_service.equals("InstituteFeeManagement")||p_service.equals("InstituteAssignment")||p_service.equals("InstituteOtherActivity")||p_service.equals("InstitutePayment")||p_service.equals("StudentProfileSummary")||p_service.equals("StudentTimeTableSummary"))){
//                
//                status=false;
//                errhandler.log_app_error("BS_VAL_004", null);
//            }
            dbg("BSValidation--->serviceValidation--->O/P--->status" + status);
            dbg("End of BSValidation--->serviceValidation");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

        return status;

    }

    public boolean userIDValidation(String p_userID, ErrorHandler errhandler, String p_instituteID, AppDependencyInjection inject, CohesiveSession session, DBSession dbSession) throws BSProcessingException, DBValidationException, DBProcessingException {
        boolean status = true;
        ArrayList<String> l_userList = new ArrayList();
        try {
            dbg("inside BSValidation--->userIDValidation");
            dbg("BSValidation--->userIDValidation--->I/P--->p_userID" + p_userID);
            dbg("BSValidation--->userIDValidation--->I/P--->p_instituteID" + p_instituteID);
            IPDataService pds = inject.getPdataservice();
            IBDProperties i_db_properties = session.getCohesiveproperties();
//        IDBReadService dbts=inject.getDbreadservice();

            String[] pkey = {p_userID};
            try {
                l_userList = pds.readRecordPData(session, dbSession, "USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_USER_PROFILE", pkey);
                dbg("l_userList size" + l_userList.size());
            } catch (DBValidationException ex) {
//            dbg("ex.toString()"+ex.toString());
                if (ex.toString().equals("DB_VAL_011")) {
                    status = false;
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    errhandler.log_app_error("BS_VAL_005", null);
                } else {
                    throw ex;
                }
            }
//              Map<String, ArrayList<String>> l_tableMap=dbts.readTable("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_userID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_userID,"USER", "UVW_USER_PROFILE", p_dbdi);
//              Map<String,List<ArrayList<String>>>l_userCollect=l_tableMap.values().stream().filter(rec->rec.get(0).trim().equals(p_userID)).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
//            if(!(l_userCollect.isEmpty())){
//            Iterator iterator1=l_userCollect.keySet().iterator();
//            Iterator<List<ArrayList<String>>> iterator2=l_userCollect.values().iterator();
//            filtermap_dummy=new HashMap();
//            while(iterator1.hasNext()&&iterator2.hasNext()){
//            filterkey_dummy= (String)iterator1.next();
//            dbg(filterkey_dummy);
//            List<ArrayList<String>>user_list_for_filter=iterator2.next();  
//            maxversion_dummy= user_list_for_filter.stream().mapToInt(rec->Integer.parseInt(rec.get(8).trim())).max().getAsInt();
//            dbg("maxversion_user"+maxversion_dummy);   
//            }
//            }else{
//                status=false;
//                errhandler.log_app_error("BS_VAL_005", null);
//           }
//            String l_pkey=p_userID+"~"+maxversion_dummy;
//            ArrayList<String> l_userList=l_tableMap.get(l_pkey);
//             dbg("BSValidation--->userIDValidation--->recordStatus"+l_userList.get(8).trim());
//             dbg("BSValidation--->userIDValidation--->authStatus"+l_userList.get(9).trim());
            if (status) {
                if (!(l_userList.get(8).trim().equals("O"))) {
                    status = false;
                    errhandler.log_app_error("BS_VAL_005", null);
                }
            }
            if (status) {
                if (!(l_userList.get(9).trim().equals("A"))) {
                    status = false;
                    errhandler.log_app_error("BS_VAL_005", null);
                }
            }
            dbg("BSValidation--->userIDValidation--->O/P--->status" + status);
            dbg("End of BSValidation--->userIDValidation");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

        return status;

    }

    public boolean instituteIDValidation(String p_instituteID, ErrorHandler errhandler, AppDependencyInjection inject, CohesiveSession session, DBSession dbSession) throws BSProcessingException, DBValidationException, DBProcessingException {
        boolean status = true;

        try {
            dbg("inside BSValidation--->instituteIDValidation");
            dbg("BSValidation--->instituteIDValidation--->I/P--->p_instituteID" + p_instituteID);
            IPDataService pds = inject.getPdataservice();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IDBReadService dbts=inject.getDbreadservice();
            IMetaDataService mds = inject.getMetadataservice();
            int authStatusColID = mds.getColumnMetaData("INSTITUTE", "INSTITUTE_MASTER", "AUTH_STATUS", session).getI_ColumnID();

            String[] l_pkey = {p_instituteID};

            try {
                ArrayList<String> l_instituteRecord = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Institute" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Institute", "INSTITUTE", "INSTITUTE_MASTER", l_pkey);

                if (!l_instituteRecord.get(authStatusColID - 1).trim().equals("A")) {

                    status = false;
                    errhandler.log_app_error("BS_VAL_012", null);

                }

            } catch (DBValidationException ex) {
                if (ex.toString().equals("DB_VAL_011")) {
                    status = false;
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    errhandler.log_app_error("BS_VAL_012", null);
                } else {
                    throw ex;
                }
            }

            dbg("BSValidation--->instituteIDValidation--->O/P--->status" + status);
            dbg("End of BSValidation--->instituteIDValidation");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

        return status;

    }

    public boolean auditLogMandatoryValidation(Request request, ErrorHandler errhandler, AppDependencyInjection inject, CohesiveSession session) throws BSProcessingException {
        boolean status = true;
        try {
            dbg("inside BSValidation--->auditLogMandatoryValidation");
//      IBDProperties i_db_properties=session.getCohesiveproperties();
            //JsonObject l_auditLog_Object=p_jsonObject.getJsonObject("auditLog");
            //JsonObject l_header=p_jsonObject.getJsonObject("header");
//       String l_userID=request.getReqHeader().getUserID();
            String l_operation = request.getReqHeader().getOperation();

            /* if(!(l_auditLog_Object.containsKey("makerDateStamp"))){
           status=false;
           errhandler.log_app_error("BS_VAL_008", null);
       }else{
           if(l_auditLog_Object.getString("makerDateStamp")==null){
               status=false;
               errhandler.log_app_error("BS_VAL_008", null);
               
           }else{
             */
//           if(request.getReqAudit().getMakerDateStamp() != null)
//           {     
//           try{
//           String l_checkerDateStamp=request.getReqAudit().getMakerDateStamp();
//           String dateformat=i_db_properties.getProperty("DATE_TIME_FORMAT");
//           SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
//           sdf.setLenient(false);
//           sdf.parse(l_checkerDateStamp);
//           }catch(ParseException ex){
//               
//               status=false;
//               errhandler.log_app_error("BS_VAL_008", null);
//           }
//           }
//          dbg("BSValidation--->auditLogMandatoryValidation--->status after MakerDateStamp "+status);
//       /*
//       if(!(l_auditLog_Object.containsKey("checkerDateStamp"))){
//           status=false;
//           errhandler.log_app_error("BS_VAL_008", null);
//       }else{
//           if(l_auditLog_Object.getString("checkerDateStamp")==null){
//               status=false;
//               errhandler.log_app_error("BS_VAL_008", null);
//               
//           }else{
//         */  
//        if(request.getReqAudit().getCheckerDateStamp()!= null)
//           {     
//         try{
//           String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
//           dbg("BSValidation--->auditLogMandatory--->l_checkerDateStamp"+l_checkerDateStamp);
//           String dateformat=i_db_properties.getProperty("DATE_TIME_FORMAT");
//           SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
//           sdf.setLenient(false);
//           sdf.parse(l_checkerDateStamp);
//           }catch(ParseException ex){
//               
//               status=false;
//               errhandler.log_app_error("BS_VAL_008", null);
//           }
//           }
//          dbg("BSValidation--->auditLogMandatoryValidation--->status after CheckerDateStamp "+status); 
            //}
            /*if(!l_auditLog_Object.containsKey("recordStatus")){
           status=false;
           errhandler.log_app_error("BS_VAL_009", null);
       }else{*/
            if (!(l_operation.equals("View"))) {
                if (request.getReqAudit().getRecordStatus() == null) {
                    status = false;
                    errhandler.log_app_error("BS_VAL_009", null);
                } else {
                    if (!(request.getReqAudit().getRecordStatus().equals("O") || request.getReqAudit().getRecordStatus().equals("D"))) {
                        status = false;
                        errhandler.log_app_error("BS_VAL_009", null);
                    }
                }

                dbg("BSValidation--->auditLogMandatoryValidation--->status after RecordStatus " + status);
                //}
                //}

                /* if(!request.getReqAudit().getAuthStatus()){
           dbg("auth status not exist");
           status=false;
           errhandler.log_app_error("BS_VAL_010", null);
       }else{
                 */
                if (request.getReqAudit().getAuthStatus() == null) {
                    dbg("auth status is null");
                    status = false;
                    errhandler.log_app_error("BS_VAL_010", null);
                } else {

                    if (!(request.getReqAudit().getAuthStatus().equals("U") || request.getReqAudit().getAuthStatus().equals("A") || request.getReqAudit().getAuthStatus().equals("R"))) {
                        dbg("auth status not equals");
                        status = false;
                        errhandler.log_app_error("BS_VAL_010", null);
                    }
                }
                dbg("BSValidation--->auditLogMandatoryValidation--->status after AuthStatus " + status);
                /*
       if(!l_auditLog_Object.containsKey("versionNumber")){
           status=false;
           errhandler.log_app_error("BS_VAL_011", null);
       }else{
            if(l_auditLog_Object.getString("versionNumber")==null){
                 status=false;
                 errhandler.log_app_error("BS_VAL_011", null);
             }else{
                 */
                if (request.getReqAudit().getVersionNumber() != null) {
                    if (!(request.getReqAudit().getVersionNumber().matches("[0-9]+"))) {
                        status = false;
                        errhandler.log_app_error("BS_VAL_011", null);

                    }
                }
                dbg("BSValidation--->auditLogMandatoryValidation--->status after AuthStatus " + status);
                dbg("BSValidation--->auditLogMandatoryValidation--->O/P--->status" + status);
                dbg("End of BSValidation--->auditLogMandatoryValidation");
            }
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

        return status;
    }

    public boolean auditLogDataValidation(Request request, ExistingAudit p_exAudit, ErrorHandler errhandler, AppDependencyInjection inject, CohesiveSession session, DBSession dbSession) throws BSProcessingException, DBValidationException, BSValidationException, DBProcessingException {
        boolean status = true;

        try {
            dbg("inside BSValidation--->auditLogDataValidation");
            BusinessService bs = inject.getBusinessService(session);
            IBDProperties i_db_properties = session.getCohesiveproperties();
            //JsonObject l_auditLog_Object=p_jsonObject.getJsonObject("auditLog");
            //JsonObject l_header=p_jsonObject.getJsonObject("header");
            String l_userID = request.getReqHeader().getUserID();
            String l_operation = request.getReqHeader().getOperation();
            String l_authStatus = request.getReqAudit().getAuthStatus();
            String l_recordStatus = request.getReqAudit().getRecordStatus();
            String l_versionNumber = request.getReqAudit().getVersionNumber();
            String l_makerID = request.getReqAudit().getMakerID();

            dbg("l_authStatus" + l_authStatus);
            dbg("l_recordStatus" + l_recordStatus);
            dbg("l_versionNumber" + l_versionNumber);
//       bs.buildPreviousAudit(request,requestJson,inject,session,dbSession);
            int existingVersion;
            String existingAuthStatus;
            String existingMakerID;
            String existingRecordStatus;

            switch (l_operation) {

                case "Create":

                    if (!request.getAutoAuthAccess().equals("Y")) {

                        if (!(l_authStatus.equals("U"))) {
                            status = false;
                            errhandler.log_app_error("BS_VAL_010", null);
                        }
                    }

                    if (!(l_versionNumber.equals("1"))) {
                        status = false;
                        errhandler.log_app_error("BS_VAL_011", null);
                    }
                    if (!(l_recordStatus.equals("O"))) {
                        status = false;
                        errhandler.log_app_error("BS_VAL_009", null);
                    }

                    break;
                case "Create-Default":

                    if (!request.getAutoAuthAccess().equals("Y")) {
                        if (!(l_authStatus.equals("U"))) {
                            status = false;
                            errhandler.log_app_error("BS_VAL_010", null);
                        }
                    }

                    if (!(l_versionNumber.equals("1"))) {
                        status = false;
                        errhandler.log_app_error("BS_VAL_011", null);
                    }
                    if (!(l_recordStatus.equals("O"))) {
                        status = false;
                        errhandler.log_app_error("BS_VAL_009", null);
                    }
                    break;
                  case "Payment-Default":

                    if (!request.getAutoAuthAccess().equals("Y")) {
                        if (!(l_authStatus.equals("U"))) {
                            status = false;
                            errhandler.log_app_error("BS_VAL_010", null);
                        }
                    }

                    if (!(l_versionNumber.equals("1"))) {
                        status = false;
                        errhandler.log_app_error("BS_VAL_011", null);
                    }
                    if (!(l_recordStatus.equals("O"))) {
                        status = false;
                        errhandler.log_app_error("BS_VAL_009", null);
                    }
                    break;
                case "Auth":
                    existingVersion = p_exAudit.getVersionNumber();
                    existingAuthStatus = p_exAudit.getAuthStatus();
                    existingRecordStatus = p_exAudit.getRecordStatus();
                    if (!(l_authStatus.equals("A"))) {
                        status = false;
                        errhandler.log_app_error("BS_VAL_010", null);
                    }
                    if (!(existingAuthStatus.equals("U"))) {
                        status = false;
                        errhandler.log_app_error("BS_VAL_010", null);

                    } else {
                        switch (existingRecordStatus) {

                            case "D":
                                p_exAudit.setRelatioshipOperation("D");
                                break;
                            case "O":
                                if (existingVersion == 1) {
                                    p_exAudit.setRelatioshipOperation("C");
                                } else if (existingVersion > 1) {
                                    p_exAudit.setRelatioshipOperation("M");
                                }
                                break;
                        }
                    }

                    if (existingVersion != Integer.parseInt(l_versionNumber)) {
                        status = false;
                        errhandler.log_app_error("BS_VAL_011", null);
                    }

                    break;
                case "Reject":
                    existingVersion = p_exAudit.getVersionNumber();
                    existingAuthStatus = p_exAudit.getAuthStatus();
                    if (!(l_authStatus.equals("R"))) {
                        status = false;
                        errhandler.log_app_error("BS_VAL_010", null);
                    }
                    if (!(existingAuthStatus.equals("U"))) {
                        status = false;
                        errhandler.log_app_error("BS_VAL_010", null);
                    }

                    if (existingVersion != Integer.parseInt(l_versionNumber)) {
                        status = false;
                        errhandler.log_app_error("BS_VAL_011", null);
                    }

                    break;
                case "Delete":
                    existingVersion = p_exAudit.getVersionNumber();
                    existingAuthStatus = p_exAudit.getAuthStatus();
                    existingMakerID = p_exAudit.getMakerID();
                    existingRecordStatus = p_exAudit.getRecordStatus();
                    if (existingVersion == Integer.parseInt(l_versionNumber)) {

                        if (!request.getAutoAuthAccess().equals("Y")) {
                            if (!(l_authStatus.equals("U"))) {
                                status = false;
                                errhandler.log_app_error("BS_VAL_010", null);
                            }
                        }
                        if (!(existingAuthStatus.equals("U"))) {
                            status = false;
                            errhandler.log_app_error("BS_VAL_010", null);
                        }
                        if (!(l_userID.equals(existingMakerID))) {
                            status = false;
                            errhandler.log_app_error("BS_VAL_006", null);
                        }
                        if (!(l_recordStatus.equals("D"))) {

                            status = false;
                            errhandler.log_app_error("BS_VAL_009", null);
                        }
                        if (!(existingRecordStatus.equals("O"))) {

                            status = false;
                            errhandler.log_app_error("BS_VAL_009", null);
                        }

                    } else if (existingVersion + 1 == Integer.parseInt(l_versionNumber)) {

                        if (!(l_recordStatus.equals("D"))) {
                            status = false;
                            errhandler.log_app_error("BS_VAL_009", null);
                        }
                        if (!(existingRecordStatus.equals("O"))) {

                            status = false;
                            errhandler.log_app_error("BS_VAL_009", null);
                        }

                        if (!request.getAutoAuthAccess().equals("Y")) {

                            if (!(l_authStatus.equals("U"))) {

                                status = false;
                                errhandler.log_app_error("BS_VAL_010", null);
                            }
                        }
               if(!(existingAuthStatus.equals("A")||existingAuthStatus.equals("R"))){
                   status=false;
                   errhandler.log_app_error("BS_VAL_010", null);
               }   
//                        if ((existingAuthStatus.equals("A") || existingAuthStatus.equals("R"))) {
//                            status = false;
//                            errhandler.log_app_error("BS_VAL_063", null);
//                        }

                    } else {
                        status = false;
                        errhandler.log_app_error("BS_VAL_011", null);
                    }

                    break;
                case "AutoAuth":
                    if (!(l_authStatus.equals("A"))) {
                        status = false;
                        errhandler.log_app_error("BS_VAL_010", null);
                    }
//               if(!(l_recordStatus.equals("O"))){
//                   
//                   status=false;
//                   errhandler.log_app_error("BS_VAL_009", null);
//               }

                    break;

                case "Modify":
                    existingVersion = p_exAudit.getVersionNumber();
                    existingAuthStatus = p_exAudit.getAuthStatus();
                    existingMakerID = p_exAudit.getMakerID();
                    existingRecordStatus = p_exAudit.getRecordStatus();
                    if (Integer.parseInt(l_versionNumber) == existingVersion) {

                        if (!(existingAuthStatus.equals("U"))) {
                            status = false;
                            errhandler.log_app_error("BS_VAL_010", null);
                        }

                        if (!request.getAutoAuthAccess().equals("Y")) {
                            if (!(l_authStatus.equals("U"))) {
                                status = false;
                                errhandler.log_app_error("BS_VAL_010", null);
                            }
                        }
                        if (!(l_makerID.equals(existingMakerID))) {
                            status = false;
                            errhandler.log_app_error("BS_VAL_006", null);
                        }
                        if (!(l_recordStatus.equals("O"))) {

                            status = false;
                            errhandler.log_app_error("BS_VAL_009", null);
                        }
                        if (!(existingRecordStatus.equals("O"))) {

                            status = false;
                            errhandler.log_app_error("BS_VAL_009", null);
                        }

                    } else if (Integer.parseInt(l_versionNumber) == existingVersion + 1) {
                        if (!(existingAuthStatus.equals("A") || existingAuthStatus.equals("R"))) {
                            status = false;
                            errhandler.log_app_error("BS_VAL_010", null);
                        }
                        if (!request.getAutoAuthAccess().equals("Y")) {
                            if (!(l_authStatus.equals("U"))) {
                                status = false;
                                errhandler.log_app_error("BS_VAL_010", null);
                            }
                        }
                        if (!(l_recordStatus.equals("O"))) {

                            status = false;
                            errhandler.log_app_error("BS_VAL_009", null);
                        }
                        if (!(existingRecordStatus.equals("O"))) {

                            status = false;
                            errhandler.log_app_error("BS_VAL_009", null);
                        }
                    } else {
                        status = false;
                        errhandler.log_app_error("BS_VAL_011", null);
                    }
                    break;

            }

            if (l_operation.equals("Reject")) {
                if (l_recordStatus.equals("D")) {

//                   l_recordStatus="O";
                    request.getReqAudit().setRecordStatus("O");
                    request.getReqAudit().setAuthStatus("A");
//                   l_authStatus="A";

                }

            }
            dbg("BSValidation--->auditDataValidation--->O/P--->status" + status);
            dbg("End of BSValidation--->auditDataValidation");

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

        return status;

    }

    public boolean userAccessValidation(Request request, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {
        boolean status = true;
        try {
            dbg("inside userAccessValidation");

            if (request.getReqHeader().getService().equals("ParentDashBoard")) {
                String l_userID = request.getReqHeader().getUserID();
                IPDataService pds = inject.getPdataservice();
                ErrorHandler errHandler = session.getErrorhandler();
                String[] l_pkey = {l_userID};
                ArrayList<String> l_userList = pds.readRecordPData(session, dbSession, "USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_USER_PROFILE", l_pkey);
                String l_userType = l_userList.get(13).trim();
                dbg("l_userType" + l_userType);

                if (!l_userType.equals("P")) {
                    status = false;
                    errHandler.log_app_error("BS_VAL_014", "ParentDashBoard");
                }

            } else {

                Map<String, ArrayList<String>> l_roleMap = null;
                l_roleMap = entityAccessValidation(request, session, dbSession, inject);
                if (l_roleMap == null || l_roleMap.isEmpty()) {
                    status = false;
                }

                if (status == true) {

                    if (!operationAccessValidation(request, l_roleMap, session, dbSession, inject)) {

                        status = false;
                        session.getErrorhandler().log_app_error("BS_VAL_014", request.getReqHeader().getService() + " " + request.getReqHeader().getOperation());
                    }

                }

            }
            dbg("end of userAccessValidation");

        } catch (DBValidationException ex) {
            throw ex;
        } catch (BSValidationException ex) {
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

        return status;
    }

    private Map<String, ArrayList<String>> entityAccessValidation(Request request, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {
        Map<String, ArrayList<String>> l_filterMap = new HashMap();
        boolean status = true;
        try {
            dbg("inside entity access validation");
            ErrorHandler errhandler = session.getErrorhandler();
            IBDProperties i_db_properties = session.getCohesiveproperties();
            IPDataService pds = inject.getPdataservice();
            String l_userID = request.getReqHeader().getUserID();
            String l_service = request.getReqHeader().getService();
            String l_instituteID = request.getReqHeader().getInstituteID();
            Map<String, String> l_businessEntity = request.getReqHeader().getBusinessEntity();

            String[] l_pkey = {l_userID};
            ArrayList<String> l_userList = pds.readRecordPData(session, dbSession, "USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_USER_PROFILE", l_pkey);
            String l_userType = l_userList.get(13).trim();

            String[] l_servicePKey = {l_service};
            ArrayList<String> l_serviceList = pds.readRecordPData(session, dbSession, "APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "SERVICE_TYPE_MASTER", l_servicePKey);
            String l_serviceType = l_serviceList.get(1).trim();
            dbg("l_userType" + l_userType);
            dbg("l_serviceType" + l_serviceType);

            if (l_userType.equals("P")) {

                Iterator<String> keyIterator = l_businessEntity.keySet().iterator();
                while (keyIterator.hasNext()) {
                    String l_entityName = keyIterator.next();
                    if (l_entityName.equals("studentID")) {
                        studentID_dummy = l_businessEntity.get(l_entityName);
                    }
                }
                dbg("userType P--->studentID_dummy" + studentID_dummy);
                String[] l_userPKey = {l_userID, studentID_dummy, l_instituteID};
                ArrayList<String> l_parentRoleMappingList = new ArrayList();

                try {
                    l_parentRoleMappingList = pds.readRecordPData(session, dbSession, "USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_PARENT_STUDENT_ROLEMAPPING", l_userPKey);

                } catch (DBValidationException ex) {
                    if (ex.toString().contains("DB_VAL_011")) {
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        status = false;
                    }
                }

                if (status == true) {

                    l_filterMap.put(l_userID, l_parentRoleMappingList);
                }

            }

            if (l_userType.equals("T") || l_userType.equals("A")) {

                if (l_serviceType.equals("C")) {

                    Iterator<String> keyIterator = l_businessEntity.keySet().iterator();
                    while (keyIterator.hasNext()) {
                        String l_entityName = keyIterator.next();
                        String l_class = null;

//                        if(l_entityName.equals("standard")){
//                          standard_dummy=l_businessEntity.get(l_entityName);
//                         }
//                        if(l_entityName.equals("section")){
//                           section_dummy=l_businessEntity.get(l_entityName);
//                         }
                        if (l_entityName.equals("class")) {
                            l_class = l_businessEntity.get(l_entityName);
                        }

                        standard_dummy = l_class.split("/")[0];
                        section_dummy = l_class.split("/")[1];

                    }
                    dbg("userType T--->serviceType--->C--->standard_dummy" + standard_dummy);
                    dbg("userType T--->serviceType--->C--->section_dummy" + section_dummy);
                    l_filterMap = getFilterMap(request, session, dbSession, inject);
                    if (l_filterMap == null || l_filterMap.isEmpty()) {
                        errhandler.log_app_error("BS_VAL_014", standard_dummy + section_dummy);
                    }
                }

                if (l_serviceType.equals("S")) {
                    Iterator<String> keyIterator = l_businessEntity.keySet().iterator();
                    while (keyIterator.hasNext()) {
                        String l_entityName = keyIterator.next();
                        if (l_entityName.equals("studentID")) {
                            studentID_dummy = l_businessEntity.get(l_entityName);
                        }
                    }

                    dbg("userType T--->serviceType--->S--->studentID_dummy" + studentID_dummy);

                    if (request.getReqHeader().getService().equals("StudentProfile") && !request.getReqHeader().getOperation().equals("View")) {

                        RequestBody<StudentProfile> body = request.getReqBody();

                        standard_dummy = body.get().getGen().getStandard();
                        section_dummy = body.get().getGen().getSection();
                    } else {

                        String[] pkey = {studentID_dummy};
                        ArrayList<String> l_studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", pkey);
                        standard_dummy = l_studentList.get(2).trim();
                        section_dummy = l_studentList.get(3).trim();

                    }

                    dbg("userType T--->serviceType--->S--->standard_dummy" + standard_dummy);
                    dbg("userType T--->serviceType--->S--->section_dummy" + section_dummy);

                    l_filterMap = getFilterMap(request, session, dbSession, inject);

                    if (l_filterMap == null || l_filterMap.isEmpty()) {
                        errhandler.log_app_error("BS_VAL_014", studentID_dummy);
                    }
                }

                if (l_serviceType.equals("T")) {
                    Iterator<String> keyIterator = l_businessEntity.keySet().iterator();
                    while (keyIterator.hasNext()) {
                        String l_entityName = keyIterator.next();
                        if (l_entityName.equals("teacherID")) {
                            teacherID_dummy = l_businessEntity.get(l_entityName);
                        }
                    }
                    dbg("userType T--->serviceType--->T--->teacherID_dummy" + teacherID_dummy);

                    l_filterMap = getFilterMap(request, session, dbSession, inject);

                    if (l_filterMap == null || l_filterMap.isEmpty()) {
                        errhandler.log_app_error("BS_VAL_014", teacherID_dummy);
                    }

                }

                if (l_serviceType.equals("I")) {
                    l_filterMap = getFilterMap(request, session, dbSession, inject);

                    if (l_filterMap == null || l_filterMap.isEmpty()) {
                        errhandler.log_app_error("BS_VAL_014", l_instituteID);
                    }

                }

                if (l_serviceType.equals("U")) {
                    l_filterMap = getFilterMap(request, session, dbSession, inject);

                    if (l_filterMap == null || l_filterMap.isEmpty()) {
                        errhandler.log_app_error("BS_VAL_014", l_instituteID);
                    }

                }
            }

        } catch (DBValidationException ex) {
            throw ex;
        } catch (BSValidationException ex) {
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

        return l_filterMap;

    }

    private Map<String, ArrayList<String>> getFilterMap(Request request, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBProcessingException, DBValidationException, BSProcessingException, BSValidationException {
        Map<String, ArrayList<String>> l_filterMap = new HashMap();
        try {
            dbg("inside getFilterMap");
            IBDProperties i_db_properties = session.getCohesiveproperties();
            IPDataService pds = inject.getPdataservice();
            Map<String, ArrayList<String>> l_userFilterMap = new HashMap();
            BusinessService bs = inject.getBusinessService(session);

            String l_userID = request.getReqHeader().getUserID();
            String userType = bs.getUserType(l_userID, session, dbSession, inject);
            String l_service = request.getReqHeader().getService();
            String l_instituteID = request.getReqHeader().getInstituteID();

            String[] l_servicePKey = {l_service};
            ArrayList<String> l_serviceList = pds.readRecordPData(session, dbSession, "APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "SERVICE_TYPE_MASTER", l_servicePKey);
            String l_serviceType = l_serviceList.get(1).trim();

            if (l_serviceType.equals("S") || l_serviceType.equals("C")) {
                /*
                Algorithm used to filter the class student map
             
          1.Checking the exact match of standardSection.If found retun the filter map.
          2.If exact match of standardSection not found go to the standard validation
          3.Inside standard validation 
                  i.Checking the exact match of standard.If found retun the filter map.
                 ii.If exact match of standard not found check "ALL" in standard.retun the filter map.
          4.If the filter map return by standard validation is empty retun the filter map.
          5.If the filter map return by standard validation is not empty go to section validation
                  i.Checking the exact match of section..If found retun the filter map.
                 ii.If exact match of standard not found check "ALL" in standard.retun the filter map.
          6.The filter map return by getFilterMap is empty throw error is in the calling place getFilterMap. 
                 */
                Map<String, ArrayList<String>> l_classStudenMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_CLASS_ENTITY_ROLEMAPPING", session, dbSession);
                l_userFilterMap = l_classStudenMap.values().stream().filter(rec -> rec.get(0).trim().equals(l_userID)).collect(Collectors.toMap(BSValidation::getKey, BSValidation::getValue));
                keyIterator = 0;

                l_userFilterMap = l_userFilterMap.values().stream().filter(rec -> rec.get(5).trim().equals(l_instituteID)).collect(Collectors.toMap(BSValidation::getKey, BSValidation::getValue));
                keyIterator = 0;

                dbg("getFilterMap--->filtration by userID--->l_userFilterMap.size" + l_userFilterMap.size());
                l_filterMap = l_userFilterMap.values().stream().filter(rec -> rec.get(2).trim().equals(standard_dummy) && rec.get(3).trim().equals(section_dummy)).collect(Collectors.toMap(BSValidation::getKey, BSValidation::getValue));
                keyIterator = 0;
                dbg("getFilterMap--->filtration by exact match of standard and section--->l_filterMap.size" + l_filterMap.size());

                if (l_filterMap == null || l_filterMap.isEmpty()) {

                    l_filterMap = standardValidation(l_userFilterMap);
                    if (l_filterMap == null || l_filterMap.isEmpty()) {
                        return l_filterMap;
                    } else {
                        l_filterMap = sectionValidation(l_filterMap);
                        return l_filterMap;
                    }
                } else {
                    return l_filterMap;
                }
            }

            if (l_serviceType.equals("T")) {

                Map<String, ArrayList<String>> l_teacherRoleMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_TEACHER_ENTITY_ROLEMAPPING", session, dbSession);
                l_userFilterMap = l_teacherRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(l_userID)).collect(Collectors.toMap(BSValidation::getKey, BSValidation::getValue));
                keyIterator = 0;
                dbg("userType T--->serviceType--->T--->fitration by userID--->p_filterMap.size" + l_userFilterMap.size());

                l_filterMap = l_userFilterMap.values().stream().filter(rec -> rec.get(2).trim().equals(l_instituteID) && rec.get(3).trim().equals(teacherID_dummy)).collect(Collectors.toMap(BSValidation::getKey, BSValidation::getValue));
                keyIterator = 0;
                dbg("getFilterMap--->filtration by exact match of instituteID and teacherID--->l_filterMap.size" + l_filterMap.size());

                if (l_filterMap == null || l_filterMap.isEmpty()) {

                    l_filterMap = instituteValidation(l_userFilterMap, l_instituteID);
                    if (l_filterMap == null || l_filterMap.isEmpty()) {
                        return l_filterMap;
                    } else {

//                if(!(request.getReqHeader().getService().equals("TeacherProfile")||request.getReqHeader().getService().equals("Create")))   
                        if (!(request.getReqHeader().getService().equals("TeacherProfile") && request.getReqHeader().getOperation().equals("Create"))) {
                            l_filterMap = teacherValidation(l_filterMap);
                        }
                        return l_filterMap;

                    }
                } else {
                    return l_filterMap;
                }

            }

            if (l_serviceType.equals("I")) {
                dbg("inside service type I");
                Map<String, ArrayList<String>> l_instituteRoleMap = null;

//          if(request.getReqHeader().getService().contains("DashBoard")&&userType.equals("T")){
//          
//          l_instituteRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_TEACHER_ENTITY_ROLEMAPPING",session,dbSession);  
//              
//           
//      
//          }else {
                l_instituteRoleMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_INSTITUTE_ENTITY_ROLEMAPPING", session, dbSession);
//          }

                l_userFilterMap = l_instituteRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(l_userID)).collect(Collectors.toMap(BSValidation::getKey, BSValidation::getValue));
                keyIterator = 0;
                dbg("userType T--->serviceType--->I--->fitration by userID--->p_filterMap.size" + l_userFilterMap.size());
                return instituteValidation(l_userFilterMap, l_instituteID);

            }

            if (l_serviceType.equals("U")) {
                dbg("inside service type U");
                Map<String, ArrayList<String>> l_instituteRoleMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_INSTITUTE_ENTITY_ROLEMAPPING", session, dbSession);
                l_userFilterMap = l_instituteRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(l_userID)).collect(Collectors.toMap(BSValidation::getKey, BSValidation::getValue));
                keyIterator = 0;
                dbg("userType T--->serviceType--->I--->fitration by userID--->p_filterMap.size" + l_userFilterMap.size());
                return instituteValidation(l_userFilterMap, l_instituteID);

            }
            dbg("end of getFilterMap");
        } catch (DBValidationException ex) {

            throw ex;

        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        return l_filterMap;
    }

    private static String getKey(ArrayList<String> l_valueList) {
        String l_key;

        l_key = l_valueList.get(0).trim() + keyIterator;
        keyIterator++;

        return l_key;
    }

    private static ArrayList<String> getValue(ArrayList<String> l_valueList) {

        return l_valueList;
    }

    private Map<String, ArrayList<String>> standardValidation(Map<String, ArrayList<String>> p_userFilterMap) throws BSProcessingException {
        Map<String, ArrayList<String>> l_filterMap = new HashMap();
        try {
            l_filterMap = p_userFilterMap.values().stream().filter(rec -> rec.get(2).trim().equals(standard_dummy)).collect(Collectors.toMap(BSValidation::getKey, BSValidation::getValue));
            keyIterator = 0;

            dbg("standardValidation--->filtration by standard--->l_filterMap.size" + l_filterMap.size());
            if (l_filterMap == null || l_filterMap.isEmpty()) {//There is no exact match for standard so we need to look for 'ALL' option in the standard 

                l_filterMap = p_userFilterMap.values().stream().filter(rec -> rec.get(2).trim().equals("ALL")).collect(Collectors.toMap(BSValidation::getKey, BSValidation::getValue));
                keyIterator = 0;
                dbg("standardValidation--->filtration by standard ALL--->l_filterMap.size" + l_filterMap.size());

            }

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        return l_filterMap;
    }

    private Map<String, ArrayList<String>> sectionValidation(Map<String, ArrayList<String>> p_standardFilterMap) throws BSProcessingException {
        Map<String, ArrayList<String>> l_filterMap = new HashMap();
        try {
            l_filterMap = p_standardFilterMap.values().stream().filter(rec -> rec.get(3).trim().equals(section_dummy)).collect(Collectors.toMap(BSValidation::getKey, BSValidation::getValue));
            keyIterator = 0;

            dbg("sectionValidation--->filtration by section--->l_filterMap.size" + l_filterMap.size());
            if (l_filterMap == null || l_filterMap.isEmpty()) {
                l_filterMap = p_standardFilterMap.values().stream().filter(rec -> rec.get(3).trim().equals("ALL")).collect(Collectors.toMap(BSValidation::getKey, BSValidation::getValue));
                keyIterator = 0;
                dbg("sectionValidation--->filtration by section ALL--->l_filterMap.size" + l_filterMap.size());

            }

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        return l_filterMap;
    }

    private Map<String, ArrayList<String>> instituteValidation(Map<String, ArrayList<String>> p_userFilterMap, String p_instituteID) throws BSProcessingException {
        Map<String, ArrayList<String>> l_filterMap = new HashMap();
        try {
            dbg("inside instituteValidation");
            l_filterMap = p_userFilterMap.values().stream().filter(rec -> rec.get(2).trim().equals(p_instituteID)).collect(Collectors.toMap(BSValidation::getKey, BSValidation::getValue));
            keyIterator = 0;
            dbg("instituteValidation--->fitration by instituteID--->p_filterMap.size" + l_filterMap.size());

            if (l_filterMap == null || l_filterMap.isEmpty()) {

                l_filterMap = p_userFilterMap.values().stream().filter(rec -> rec.get(2).trim().equals("ALL")).collect(Collectors.toMap(BSValidation::getKey, BSValidation::getValue));
                keyIterator = 0;
                dbg("instituteValidation--->fitration by instituteID ALL--->p_filterMap.size" + l_filterMap.size());

            }

            dbg("end of instituteValidation");

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        return l_filterMap;
    }

    private Map<String, ArrayList<String>> teacherValidation(Map<String, ArrayList<String>> p_instituteFilterMap) throws BSProcessingException {
        Map<String, ArrayList<String>> l_filterMap = new HashMap();
        try {
            l_filterMap = p_instituteFilterMap.values().stream().filter(rec -> rec.get(3).trim().equals(teacherID_dummy)).collect(Collectors.toMap(BSValidation::getKey, BSValidation::getValue));
            keyIterator = 0;

            dbg("teacherValidation--->filtration by teacherID--->l_filterMap.size" + l_filterMap.size());
            if (l_filterMap == null || l_filterMap.isEmpty()) {
                l_filterMap = p_instituteFilterMap.values().stream().filter(rec -> rec.get(3).trim().equals("ALL")).collect(Collectors.toMap(BSValidation::getKey, BSValidation::getValue));
                keyIterator = 0;
                dbg("teacherValidation--->filtration by teacherID ALL--->l_filterMap.size" + l_filterMap.size());

            }

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        return l_filterMap;
    }

    private boolean operationAccessValidation(Request request, Map<String, ArrayList<String>> p_roleMap, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {
        boolean status = true;
        try {

            ErrorHandler errhandler = session.getErrorhandler();
            IBDProperties i_db_properties = session.getCohesiveproperties();
            IPDataService pds = inject.getPdataservice();
            IMetaDataService mds = inject.getMetadataservice();
            dbg("inside operation access validation");
            String l_service = request.getReqHeader().getService();
            String l_operation = request.getReqHeader().getOperation();
            Iterator<ArrayList<String>> valueIterator = p_roleMap.values().iterator();
            while (valueIterator.hasNext()) {
                ArrayList<String> l_roleFunctionsList = new ArrayList();
                ArrayList<String> roleMapList = valueIterator.next();
                String roleID = roleMapList.get(1).trim();
                dbg("roleID" + roleID);
                try {
                    String[] l_pkey = {roleID, l_service};
                    l_roleFunctionsList = pds.readRecordPData(session, dbSession, "USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_USER_ROLE_DETAIL", l_pkey);
                    status = true;

                } catch (DBValidationException ex) {
                    if (ex.toString().contains("DB_VAL_011")) {
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");//Integration changes
                        try {
                            String[] l_pkey = {roleID, "ALL"};
                            l_roleFunctionsList = pds.readRecordPData(session, dbSession, "USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_USER_ROLE_DETAIL", l_pkey);
                            status = true;
                        } catch (DBValidationException ex1) {
                            if (ex1.toString().contains("DB_VAL_011")) {
                                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");//Integration changes
                                status = false;
                                dbg("service not in role");
//                         errhandler.log_app_error("BS_VAL_014", l_service);
                            }
                        }

                    } else {
                        throw ex;
                    }
                }

                if (status) {
                    dbg("l_roleFunctionsList size" + l_roleFunctionsList.size());

                    if (l_operation.equals("Create-Default")||l_operation.equals("Payment-Default")) {
                        l_operation = "Create";
                    }

                    int l_opearationColID = mds.getColumnMetaData("USER", "UVW_USER_ROLE_DETAIL", l_operation.toUpperCase(), session).getI_ColumnID();
                    dbg("l_opearationColID" + l_opearationColID);
                    for (int i = 0; i < l_roleFunctionsList.size(); i++) {
                        dbg("l_roleFunctionsList" + l_roleFunctionsList.get(i));
                    }
                    if (!(l_roleFunctionsList.get(l_opearationColID - 1).trim().equals("true"))) {
                        status = false;
                        dbg("operation not in role");
//                errhandler.log_app_error("BS_VAL_014", l_operation);
                    }

                    if (!(request.getReqHeader().getOperation().equals("Create-Default")|| (request.getReqHeader().getOperation().equals("Payment-Default")) || (request.getReqHeader().getOperation().equals("Auth")) || (request.getReqHeader().getOperation().equals("Reject")))) {

                        int l_autoAuthColID = mds.getColumnMetaData("USER", "UVW_USER_ROLE_DETAIL", "AUTOAUTH", session).getI_ColumnID();

                        if (l_roleFunctionsList.get(l_autoAuthColID - 1).trim().equals("true")) {

                            request.setAutoAuthAccess("Y");
                            request.getReqAudit().setAuthStatus("A");
                            String makerID = request.getReqAudit().getMakerID();
                            String makerDateStamp = request.getReqAudit().getMakerDateStamp();
                            request.getReqAudit().setCheckerID(makerID);
                            request.getReqAudit().setCheckerDateStamp(makerDateStamp);

                        } else {

                            String l_userID = request.getReqHeader().getUserID();
                            String[] l_pkey = {l_userID};
                            ArrayList<String> l_userList = pds.readRecordPData(session, dbSession, "USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_USER_PROFILE", l_pkey);
                            String l_userType = l_userList.get(13).trim();

                            if (l_userType.equals("P") && (request.getReqHeader().getService().equals("StudentOtherActivity") || request.getReqHeader().getService().equals("StudentProgressCard") || request.getReqHeader().getService().equals("StudentSoftSkill") || request.getReqHeader().getService().equals("StudentECircular")) && request.getReqHeader().getOperation().equals("Modify")) {

                                request.setAutoAuthAccess("Y");
                                request.getReqAudit().setAuthStatus("A");
                                String makerID = request.getReqAudit().getMakerID();
                                String makerDateStamp = request.getReqAudit().getMakerDateStamp();
                                request.getReqAudit().setCheckerID(makerID);
                                request.getReqAudit().setCheckerDateStamp(makerDateStamp);

                            } else if (l_userType.equals("T") && request.getReqHeader().getService().equals("TeacherECircular") && request.getReqHeader().getOperation().equals("Modify")) {

                                request.setAutoAuthAccess("Y");
                                request.getReqAudit().setAuthStatus("A");
                                String makerID = request.getReqAudit().getMakerID();
                                String makerDateStamp = request.getReqAudit().getMakerDateStamp();
                                request.getReqAudit().setCheckerID(makerID);
                                request.getReqAudit().setCheckerDateStamp(makerDateStamp);

                            } else {

                                request.setAutoAuthAccess("N");

                            }
                        }

                    } else {

                        request.setAutoAuthAccess("N");
                    }

                }

                if (status == true) {
                    return true;
                }

            }

            dbg("end of operation access validation");
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
        return status;

    }

    public boolean standardSectionValidation(String standard, String section, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {
            dbg("standard" + standard);
            dbg("section" + section);
            IPDataService pds = inject.getPdataservice();
            try {
                String[] pkey = {p_instituteID, standard, section};
                ArrayList<String> classRecord = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_STANDARD_MASTER", pkey);

                String recStatus = classRecord.get(9).trim();
                String authStatus = classRecord.get(10).trim();
                dbg("recStatus" + recStatus);
                dbg("authStatus" + authStatus);

                if (!recStatus.equals("O")) {

                    status = false;
                }
                if (!authStatus.equals("A")) {

                    status = false;
                }

            } catch (DBValidationException ex) {
                if (ex.toString().contains("DB_VAL_011")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    status = false;
                } else {
                    throw ex;
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
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean studentIDValidation(String studentID, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {
            dbg("inside studentid validation studentID" + studentID);
            dbg("inside studentid validation p_instituteID" + p_instituteID);
            IPDataService pds = inject.getPdataservice();
            try {
                String[] pkey = {studentID};
                ArrayList<String> studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", pkey);

                String recStatus = studentList.get(8).trim();
                String authStatus = studentList.get(9).trim();

                if (!recStatus.equals("O")) {

                    status = false;
                }

                if (!authStatus.equals("A")) {

                    status = false;
                }

            } catch (DBValidationException ex) {
                if (ex.toString().contains("DB_VAL_011")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    status = false;
                } else {
                    throw ex;
                }
            }
            dbg("end of studentid validation status" + status);
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

    public boolean feeIDValidation(String feeID, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {
            dbg("inside feeIDValidation studentID" + feeID);
            dbg("inside feeIDValidation p_instituteID" + p_instituteID);
//           IPDataService pds=inject.getPdataservice();
            IDBReadBufferService readBuffer = inject.getDBReadBufferService();
            try {
                String[] pkey = {feeID};
                DBRecord feeList = readBuffer.readRecord("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "Fee", "INSTITUTE", "INSTITUTE_FEE_MANAGEMENT", pkey, session, dbSession);

                String recStatus = feeList.getRecord().get(11).trim();
                String authStatus = feeList.getRecord().get(12).trim();
                dbg("recStatus" + recStatus);
                dbg("authStatus" + authStatus);

                if (!recStatus.equals("O")) {

                    status = false;
                }

                if (!authStatus.equals("A")) {

                    status = false;
                }

            } catch (DBValidationException ex) {
                if (ex.toString().contains("DB_VAL_011") || ex.toString().contains("DB_VAL_000")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    status = false;
                } else {
                    throw ex;
                }
            }
            dbg("end of studentid validation status" + status);
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

    public void studentAccessValidation(Request request, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException, BSValidationException {
        boolean status = true;
        try {

            String p_instituteID = request.getReqHeader().getInstituteID();
            String l_userID = request.getReqHeader().getUserID();
            Map<String, String> l_businessEntity = request.getReqHeader().getBusinessEntity();
            String p_studentID = null;
            Iterator<String> studentIterator = l_businessEntity.keySet().iterator();
            while (studentIterator.hasNext()) {
                String l_entityName = studentIterator.next();
                if (l_entityName.equals("studentID")) {
                    p_studentID = l_businessEntity.get(l_entityName);
                }
            }

            if (p_studentID != null && !p_studentID.isEmpty()) {

                dbg("inside studentid validation studentID" + p_studentID);
                dbg("inside studentid validation p_instituteID" + p_instituteID);
                IPDataService pds = inject.getPdataservice();
                String[] l_pkey = {l_userID};
                ArrayList<String> l_userList = pds.readRecordPData(session, dbSession, "USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_USER_PROFILE", l_pkey);
                String l_userType = l_userList.get(13).trim();
                if (l_userType.equals("P")) {

                    Map<String, ArrayList<String>> l_parentStudentRoleMap = pds.readTablePData("USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_PARENT_STUDENT_ROLEMAPPING", session, dbSession);
                    dbg("l_parentStudentRoleMap size" + l_parentStudentRoleMap.size());
                    Map<String, List<ArrayList<String>>> l_studentWiseGroup = l_parentStudentRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(l_userID) && rec.get(4).trim().equals(p_instituteID)).collect(Collectors.groupingBy(rec -> rec.get(2).trim()));

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
                    Map<String, List<ArrayList<String>>> l_classWiseGroup = l_classEntityRoleMap.values().stream().filter(rec -> rec.get(0).trim().equals(l_userID) && rec.get(5).trim().equals(p_instituteID)).collect(Collectors.groupingBy(rec -> rec.get(2).trim() + "~" + rec.get(3).trim()));
                    dbg("l_classWiseGroup size" + l_classWiseGroup.size());
                    Iterator<String> keyIterator = l_classWiseGroup.keySet().iterator();

                    String[] pkey = {p_studentID};
                    ArrayList<String> l_studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", pkey);
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

            }
            dbg("end of studentid validation status" + status);
        } catch (BSValidationException ex) {
            throw ex;
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

    public boolean groupIDValidation(String groupID, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {
            dbg("inside groupIDValidation groupID" + groupID);
            dbg("inside groupIDValidation p_instituteID" + p_instituteID);
            IPDataService pds = inject.getPdataservice();
            try {
                String[] pkey = {groupID};
                ArrayList<String> studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_GROUP_MAPPING_MASTER", pkey);

                String recStatus = studentList.get(7).trim();
                String authStatus = studentList.get(8).trim();

                if (!recStatus.equals("O")) {

                    status = false;
                }

                if (!authStatus.equals("A")) {

                    status = false;
                }

            } catch (DBValidationException ex) {
                if (ex.toString().contains("DB_VAL_011")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    status = false;
                } else {
                    throw ex;
                }
            }
            dbg("end of groupIDValidation validation status" + status);
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

    public boolean subjectIDValidation(String subjectID, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {
            dbg("inside subjectIDValidation--->subjectID" + subjectID);
            IPDataService pds = inject.getPdataservice();
            BusinessService bs = inject.getBusinessService(session);
            try {
                String[] pkey = {p_instituteID, subjectID};
                ArrayList<String> subjectList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_SUBJECT_MASTER", pkey);

                String masterVersion = bs.getMaxVersionOfTheInstitute(p_instituteID, session, dbSession, inject);
                String recordVersion = bs.getVersionNumberFromTheRecord("IVW_SUBJECT_MASTER", "INSTITUTE", subjectList, session, dbSession, inject);

                if (!masterVersion.equals(recordVersion)) {

                    status = false;
                }

            } catch (DBValidationException ex) {
                if (ex.toString().contains("DB_VAL_011")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    status = false;
                } else {
                    throw ex;
                }
            }
            dbg("end of subjectIDValidation--->status" + status);
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

    public boolean skillIDValidation(String skillID, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {
            dbg("inside skillIDValidation--->skillID" + skillID);
            IPDataService pds = inject.getPdataservice();
            BusinessService bs = inject.getBusinessService(session);
            try {
                String[] pkey = {p_instituteID, skillID};
                ArrayList<String> skillList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_SKILL_MASTER", pkey);

                String masterVersion = bs.getMaxVersionOfTheInstitute(p_instituteID, session, dbSession, inject);
                String recordVersion = bs.getVersionNumberFromTheRecord("IVW_SKILL_MASTER", "INSTITUTE", skillList, session, dbSession, inject);

                if (!masterVersion.equals(recordVersion)) {

                    status = false;
                }

            } catch (DBValidationException ex) {
                if (ex.toString().contains("DB_VAL_011")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    status = false;
                } else {
                    throw ex;
                }
            }
            dbg("end of skillIDValidation--->status" + status);
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

    public boolean classSubjectIDValidation(String subjectID, String standard, String section, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {
            dbg("inside subjectIDValidation--->subjectID" + subjectID);
            dbg("inside subjectIDValidation--->standard" + standard);
            dbg("inside subjectIDValidation--->section" + section);
            dbg("inside subjectIDValidation--->p_instituteID" + p_instituteID);
            IDBReadBufferService readBuffer = inject.getDBReadBufferService();
            BusinessService bs = inject.getBusinessService(session);

            try {
                String[] l_pkey = {standard, section};
                DBRecord timeTableRec = readBuffer.readRecord("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "CLASS" + i_db_properties.getProperty("FOLDER_DELIMITER") + standard + section + i_db_properties.getProperty("FOLDER_DELIMITER") + "Timetable", "CLASS", "CLASS_TIMETABLE_MASTER", l_pkey, session, dbSession);

                String masterVersion = timeTableRec.getRecord().get(8).trim();

                Map<String, DBRecord> detailMap = readBuffer.readTable("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "CLASS" + i_db_properties.getProperty("FOLDER_DELIMITER") + standard + section + i_db_properties.getProperty("FOLDER_DELIMITER") + "Timetable", "CLASS", "CLASS_TIMETABLE_DETAIL", session, dbSession);
                dbg("detailMap size" + detailMap.size());

                int versionIndex = bs.getVersionIndexOfTheTable("CLASS_TIMETABLE_DETAIL", "CLASS", session, dbSession, inject);

                Map<String, List<DBRecord>> subjectGroup = detailMap.values().stream().filter(rec -> rec.getRecord().get(versionIndex).trim().equals(masterVersion)).collect(Collectors.groupingBy(rec -> rec.getRecord().get(4).trim()));
                dbg("subjectGroup size" + subjectGroup.size());

                if (!subjectGroup.containsKey(subjectID)) {

                    dbg("subjectGroup contains  " + subjectID);
                    status = false;
                }

            } catch (DBValidationException ex) {
                if (ex.toString().contains("DB_VAL_011") || ex.toString().contains("DB_VAL_000")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                    status = false;
                } else {
                    throw ex;
                }
            }
            dbg("end of subjectIDValidation--->status" + status);
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

    public boolean dateFormatValidation(String p_date, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {
            dbg("inside dateFormatValidation-->" + p_date);
            try {
                String dateformat = i_db_properties.getProperty("DATE_FORMAT");
                SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
                sdf.setLenient(false);
                sdf.parse(p_date);
            } catch (ParseException ex) {

                status = false;
            }
            dbg("end of dateFormatValidation-->" + p_date);
            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean feeTypeValidation(String feeType, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {
            dbg("inside feeTypeValidation");
            dbg("feeType" + feeType);
            dbg("p_instituteID" + p_instituteID);
            IPDataService pds = inject.getPdataservice();
            BusinessService bs = inject.getBusinessService(session);
            try {
                String[] pkey = {p_instituteID, feeType};
                ArrayList<String> feeList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_FEE_TYPE_MASTER", pkey);

                String masterVersion = bs.getMaxVersionOfTheInstitute(p_instituteID, session, dbSession, inject);

                String recordVersion = bs.getVersionNumberFromTheRecord("IVW_FEE_TYPE_MASTER", "INSTITUTE", feeList, session, dbSession, inject);

                if (!masterVersion.equals(recordVersion)) {
                    status = false;
                }

            } catch (DBValidationException ex) {
                if (ex.toString().contains("DB_VAL_011")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    status = false;
                } else {
                    throw ex;
                }
            }
            dbg("end of feeTypeValidation status" + status);
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

    public boolean notificationTypeValidation(String notificationType, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            IPDataService pds = inject.getPdataservice();
            BusinessService bs = inject.getBusinessService(session);
            try {
                String[] pkey = {p_instituteID, notificationType};
                ArrayList<String> notificationList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_NOTIFICATION_TYPE_MASTER", pkey);

                String masterVersion = bs.getMaxVersionOfTheInstitute(p_instituteID, session, dbSession, inject);

                String recordVersion = bs.getVersionNumberFromTheRecord("IVW_NOTIFICATION_TYPE_MASTER", "INSTITUTE", notificationList, session, dbSession, inject);

                if (!masterVersion.equals(recordVersion)) {
                    status = false;
                }

            } catch (DBValidationException ex) {
                if (ex.toString().contains("DB_VAL_011")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    status = false;
                } else {
                    throw ex;
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
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean paymentModeValidation(String feeType, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {

        boolean status = true;
        try {

            if (!(feeType.equals("C") || feeType.equals("Q") || feeType.equals("N") || feeType.equals("O"))) {
                status = false;
            }

            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean amountValidation(String p_amount, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            if (!(p_amount.matches("^[0-9]*\\.?[0-9]+$"))) {
                status = false;
            }

            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean feeStatusValidation(String p_feeStatus, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            if (!(p_feeStatus.equals("O") || p_feeStatus.equals("P") || p_feeStatus.equals("C"))) {
                status = false;
            }

            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean examValidation(String examType, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            IPDataService pds = inject.getPdataservice();
            BusinessService bs = inject.getBusinessService(session);
            try {
                String[] pkey = {p_instituteID, examType};
                ArrayList<String> examList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_INSTITUTE_EXAM_MASTER", pkey);

                String masterVersion = bs.getMaxVersionOfTheInstitute(p_instituteID, session, dbSession, inject);

                String recordVersion = bs.getVersionNumberFromTheRecord("IVW_INSTITUTE_EXAM_MASTER", "INSTITUTE", examList, session, dbSession, inject);

                if (!masterVersion.equals(recordVersion)) {
                    status = false;
                }

            } catch (DBValidationException ex) {
                if (ex.toString().contains("DB_VAL_011")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    status = false;
                } else {
                    throw ex;
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
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean gradeValidation(String p_subjectID, String p_grade, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {
            dbg("inside gradeValidation--->subjectID" + p_subjectID);
            dbg("inside gradeValidation--->p_grade" + p_grade);
            IPDataService pds = inject.getPdataservice();
            BusinessService bs = inject.getBusinessService(session);
            try {
                String[] pkey = {p_instituteID, p_grade};

                ArrayList<String> gradeList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_SUBJECT_GRADE_MASTER", pkey);

                String masterVersion = bs.getMaxVersionOfTheInstitute(p_instituteID, session, dbSession, inject);

                String recordVersion = bs.getVersionNumberFromTheRecord("IVW_SUBJECT_GRADE_MASTER", "INSTITUTE", gradeList, session, dbSession, inject);

                if (!masterVersion.equals(recordVersion)) {
                    status = false;
                }

            } catch (DBValidationException ex) {
                if (ex.toString().contains("DB_VAL_011")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    status = false;
                } else {
                    throw ex;
                }
            }
            dbg("end of gradeValidation--->status" + status);
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

    public boolean skillGradeValidation(String p_skillGrade, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {
            dbg("inside skillGradeValidation--->p_skillGrade" + p_skillGrade);
            IPDataService pds = inject.getPdataservice();
            BusinessService bs = inject.getBusinessService(session);
            try {
                String[] pkey = {p_instituteID, p_skillGrade};

                ArrayList<String> skillGradeList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_SKILL_GRADE_MASTER", pkey);

                String masterVersion = bs.getMaxVersionOfTheInstitute(p_instituteID, session, dbSession, inject);

                String recordVersion = bs.getVersionNumberFromTheRecord("IVW_SKILL_GRADE_MASTER", "INSTITUTE", skillGradeList, session, dbSession, inject);

                if (!masterVersion.equals(recordVersion)) {
                    status = false;
                }

            } catch (DBValidationException ex) {
                if (ex.toString().contains("DB_VAL_011")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    status = false;
                } else {
                    throw ex;
                }
            }
            dbg("end of skillGradeValidation--->status" + status);
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

    public boolean markValidation(String p_mark, String l_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            if (!(p_mark.matches("[0-9]+"))) {
                return false;
            }

            IPDataService pds = inject.getPdataservice();
            BusinessService bs = inject.getBusinessService(session);

            Map<String, ArrayList<String>> l_gradeMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_SUBJECT_GRADE_MASTER", session, dbSession);

            String masterVersion = bs.getMaxVersionOfTheInstitute(l_instituteID, session, dbSession, inject);

            int versionIndex = bs.getVersionIndexOfTheTable("IVW_SUBJECT_GRADE_MASTER", "INSTITUTE", session, dbSession, inject);

            int maxMark = l_gradeMap.values().stream().filter(rec -> rec.get(versionIndex).trim().equals(masterVersion)).mapToInt(rec -> Integer.parseInt(rec.get(3).trim())).max().getAsInt();

            if (Integer.parseInt(p_mark) > maxMark) {

                return false;
            }

            if (Integer.parseInt(p_mark) < 0) {

                return false;
            }

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

    public boolean timeTableSizeValidation(int p_size, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            if (p_size > 5) {
                status = false;
            }

            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean periodSizeValidation(int p_size, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            if (p_size > 8) {
                status = false;
            }

            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean dayValidation(String p_day, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            if (!(p_day.equals("Mon") || p_day.equals("Tue") || p_day.equals("Wed") || p_day.equals("Thu") || p_day.equals("Fri"))) {

                status = false;
            }

            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean dayNumberValidation(String dayNumber, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            if (Integer.parseInt(dayNumber) > 5) {
                status = false;
            }

            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean periodNumberValidation(String p_periodNumber, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            if (!(p_periodNumber.equals("1") || p_periodNumber.equals("2") || p_periodNumber.equals("3") || p_periodNumber.equals("4") || p_periodNumber.equals("5") || p_periodNumber.equals("6") || p_periodNumber.equals("7") || p_periodNumber.equals("8"))) {
                status = false;
            }

            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean teacherIDValidation(String teacherID, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {
            dbg("inside teacher id validation");
            dbg("teacherID" + teacherID);
            dbg("p_instituteID" + p_instituteID);
            IPDataService pds = inject.getPdataservice();
            try {
                String[] pkey = {teacherID};
                ArrayList<String> teacherList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + p_instituteID, "INSTITUTE", "IVW_TEACHER_MASTER", pkey);

                String recStatus = teacherList.get(7).trim();
                String authStatus = teacherList.get(8).trim();

                dbg("recStatus" + recStatus);

                dbg("authStatus" + authStatus);

                if (!recStatus.equals("O")) {

                    status = false;
                }

                if (!authStatus.equals("A")) {

                    status = false;
                }

            } catch (DBValidationException ex) {
                if (ex.toString().contains("DB_VAL_011")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    status = false;
                } else {
                    throw ex;
                }
            }

            dbg("end of teacher id validation--->status--->" + status);
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

    public boolean leaveDateValidation(String from, String to, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        try {

            String dateFormat = i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            Date fromDate = formatter.parse(from);
            Date toDate = formatter.parse(to);

            if (fromDate.compareTo(toDate) > 1) {

                return false;

            } else {

                return true;
            }

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean pastDateValidation(String p_date, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        try {
            dbg("inside pastDateValidation");
            String dateFormat = i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            Date dateForCheck = formatter.parse(p_date);
            dbg("dateForCheck" + dateForCheck);

            Date date1 = new Date();
            String l_currentDateString = formatter.format(date1);
            Date l_currentDate = formatter.parse(l_currentDateString);
            dbg("l_currentDate" + dateForCheck);
            dbg("dateForCheck.compareTo(l_currentDate" + dateForCheck.compareTo(l_currentDate));
            if (dateForCheck.compareTo(l_currentDate) < 0) {

                return false;

            } else {

                return true;
            }

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean futureDateValidation(String p_date, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        try {
            dbg("inside pastDateValidation");
            String dateFormat = i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            Date dateForCheck = formatter.parse(p_date);
            dbg("dateForCheck" + dateForCheck);

            Date date1 = new Date();
            String l_currentDateString = formatter.format(date1);
            Date l_currentDate = formatter.parse(l_currentDateString);
            dbg("l_currentDate" + dateForCheck);
            dbg("dateForCheck.compareTo(l_currentDate" + dateForCheck.compareTo(l_currentDate));
            if (dateForCheck.compareTo(l_currentDate) > 0) {

                return false;

            } else {

                return true;
            }

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean leaveTypeValidation(String leaveType, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            if (!(leaveType.equals("S") || leaveType.equals("P") || leaveType.equals("C"))) {
                status = false;
            }
            return status;
//       }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean leaveNoonValidation(String noon, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            if (!(noon.equals("F") || noon.equals("D") || noon.equals("A"))) {
                status = false;
            }
            return status;
//       }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean hourValidation(String p_hour, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {

        boolean status = true;
        try {

            try {

                if (Integer.parseInt(p_hour) > 24 || Integer.parseInt(p_hour) < 1) {
                    status = false;
                }

            } catch (NumberFormatException ex) {
                status = false;
            }

            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean minValidation(String p_min, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {

        boolean status = true;
        try {

            try {

                if (Integer.parseInt(p_min) > 59 || Integer.parseInt(p_min) < 0) {
                    status = false;
                }

            } catch (NumberFormatException ex) {
                status = false;
            }

            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean examHallValidation(String hall, String p_instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {

        return true;

    }

    public boolean dateOfMonthValidation(String p_year, String p_month, String p_date, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            if (Integer.parseInt(p_date) > 31 || Integer.parseInt(p_date) < 1) {
                status = false;
            }

            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean yearValidation(String p_year, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

//              int year = LocalDate.now().getYear();   
//              if(Integer.parseInt(p_year)<year){
//                 status=false;
//              }
            if (p_year.length() != 4) {

                status = false;
            }

            if (!(p_year.matches("[0-9]+"))) {
                status = false;
            }

            //if Integer.parseInt(p_year)() 
            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean monthValidation(String p_month, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            if (Integer.parseInt(p_month) > 12 || Integer.parseInt(p_month) < 1) {
                status = false;
            }

            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean attendanceCharachterValidation(String p_attendanceCharachter, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {
            dbg("inside attendanceCharachterValidation-->" + p_attendanceCharachter);
            if (!(p_attendanceCharachter.equals("A") || p_attendanceCharachter.equals("P") || p_attendanceCharachter.equals("L") || p_attendanceCharachter.equals("-"))) {
                status = false;
            }

            dbg("status--->" + status);
            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean emailValidation(String p_emailID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            String l_replacedCpEmail = p_emailID.replace("AATT;", "@");
//            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
//                    + "[a-zA-Z0-9_+&*-]+)*@"
//                    + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
//                    + "A-Z]{2,7}$";
             String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                    + "[a-zA-Z0-9_+&*-]+)*@"
                    + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                    + "A-Z]{2,10}$";
            Pattern pat = Pattern.compile(emailRegex);
            if (!pat.matcher(l_replacedCpEmail).matches()) {
                status = false;
            }
            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean contactNoValidation(String p_contactNo, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

//             if(!p_contactNo.matches("^\\+(?:[0-9] ?){6,14}[0-9]$")){
//                 status=false;
//             }
            dbg("inside contactNoValidation");
            dbg("mobileNo" + p_contactNo);
            if (p_contactNo.contains("+")) {

                if (!p_contactNo.matches("^\\+(?:[0-9] ?){6,14}[0-9]$")) {

                    status = false;
                }
            } else {

                if (p_contactNo.length() != 10) {

                    status = false;
                }

                if (!(p_contactNo.matches("[0-9]+"))) {
                    status = false;
                }
            }
            dbg("end of contactNoValidation");
            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean smsUsageValidation(String instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            dbg("inside smsUsageValidation");
            dbg("instituteID" + instituteID);
            IPDataService pds = inject.getPdataservice();

            String[] pkey = {instituteID};
            ArrayList<String> contractList = pds.readRecordPData(session, dbSession, "APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "CONTRACT_MASTER", pkey);

            int smsLimit = Integer.parseInt(contractList.get(2).trim());
            int smsUsed = Integer.parseInt(contractList.get(4).trim());

            if (smsLimit - smsUsed < 1) {

                status = false;
            }

            dbg("end of smsUsageValidation");
            return status;
        } catch (DBValidationException ex) {
            dbg(ex);
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean emailUsageValidation(String instituteID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            dbg("inside emailUsageValidation");
            dbg("instituteID" + instituteID);
            IPDataService pds = inject.getPdataservice();

            String[] pkey = {instituteID};
            ArrayList<String> contractList = pds.readRecordPData(session, dbSession, "APP" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive" + i_db_properties.getProperty("FOLDER_DELIMITER") + "Cohesive", "APP", "CONTRACT_MASTER", pkey);

            int emailLimit = Integer.parseInt(contractList.get(3).trim());
            int emailUsed = Integer.parseInt(contractList.get(5).trim());

            if (emailLimit - emailUsed < 1) {

                status = false;
            }

            dbg("end of emailUsageValidation");
            return status;
        } catch (DBValidationException ex) {
            dbg(ex);
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean bloodGroupValidation(String p_bloodGroup, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            if (!p_bloodGroup.matches("[A|B|AB|O][\\+|\\-]")) {
                status = false;
            }

            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean notificationFrequencyValidation(String p_notificationFrequency, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            if (!(p_notificationFrequency.equals("Daily") || p_notificationFrequency.equals("Weekly") || p_notificationFrequency.equals("Fortnightly") || p_notificationFrequency.equals("Monthly") || p_notificationFrequency.equals("Quarterly") || p_notificationFrequency.equals("Halfyearly") || p_notificationFrequency.equals("Yearly"))) {
                status = false;
            }

            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean recordStatusValidation(String p_recordStatus, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            if (!(p_recordStatus.equals("O") || p_recordStatus.equals("D"))) {

                status = false;
            }

            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean authStatusValidation(String p_authStatus, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {

            if (!(p_authStatus.equals("A") || p_authStatus.equals("U") || p_authStatus.equals("R"))) {

                status = false;
            }

            return status;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean roleIDValidation(String l_roleID, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws DBValidationException, DBProcessingException, BSProcessingException {
        boolean status = true;
        try {
            dbg("roleID" + l_roleID);
            IPDataService pds = inject.getPdataservice();
            if (!(l_roleID.equals("ALL"))) {

                try {
                    String[] pkey = {l_roleID};
                    pds.readRecordPData(session, dbSession, "USER" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User" + i_db_properties.getProperty("FOLDER_DELIMITER") + "User", "USER", "UVW_USER_ROLE_MASTER", pkey);

                } catch (DBValidationException ex) {
                    if (ex.toString().contains("DB_VAL_011")) {
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        status = false;
                    } else {
                        throw ex;
                    }
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
            throw new BSProcessingException(ex.toString());
        }

    }

    public boolean ResourceTokenValidation(String token, String userid, String instid, String service, ErrorHandler errhandler, AppDependencyInjection inject, CohesiveSession session) throws BSProcessingException {
        boolean status = false;

        try {
            JWEInput jweinput = new JWEInput(token, userid, instid, "");
            TokenValidateService tknval = inject.getTokenValidationServiceClass();

            if (tknval.validateRestToken(jweinput, service, session)) {
                status = true;
            } else if (jweinput.isExpired()) {

                errhandler.log_app_error("BS_VAL_026", null);

            } else {
                errhandler.log_app_error("BS_VAL_101", null);
            }
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

        return status;
    }

    public void classValidation(String classs, CohesiveSession session) throws BSProcessingException, BSValidationException {

        try {
            dbg("inside class validation");
            dbg("classs" + classs);
            ErrorHandler errhandler = session.getErrorhandler();

            if (classs == null || classs.isEmpty()) {

                errhandler.log_app_error("BS_VAL_002", "class");
                throw new BSValidationException("BSValidationException");
            } else if (!classs.contains("/")) {

                errhandler.log_app_error("BS_VAL_003", "class");
                throw new BSValidationException("BSValidationException");
            }

        } catch (BSValidationException ex) {
            throw ex;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public void lengthValidation(Request request, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {
        try {

            dbg("inside length validation");
            String serviceName = request.getReqHeader().getService();
            dbg("serviceName" + serviceName);
            IMetaDataService mds = inject.getMetadataservice();
            ErrorHandler errHandler = session.getErrorhandler();
            int idLength = 10;
            int nameLength = 50;
            int textFieldLength = 105;
            int dateLength = 10;
            int periodNoLength = 1;
            int attendanceLength = 1;
            int standardLength = 3;
            int sectionLength = 3;
            int hourLength = 2;
            int minLength = 2;
            int dayLength = 3;
            int feeTypeLength = 25;
            int yearLength = 4;
            int monthLength = 2;
            int amountLength = 13;
            int contactNoLength = 14;
            int emailLength = 50;

            switch (serviceName) {

                case "ClassAttendance":

                    RequestBody<ClassAttendance> classAttendanceBody = request.getReqBody();
                    ClassAttendance classAttendance = classAttendanceBody.get();

                    if (classAttendance.getStandard().length() > standardLength) {

                        errHandler.log_app_error("BS_VAL_025", "Standard" + "," + standardLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (classAttendance.getSection().length() > sectionLength) {

                        errHandler.log_app_error("BS_VAL_025", "Section" + "," + sectionLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    for (int i = 0; i < classAttendance.foreNoonAttendance.length; i++) {

                        if (classAttendance.foreNoonAttendance[i].getStudentID().length() > idLength) {

                            errHandler.log_app_error("BS_VAL_025", "Student id" + "," + idLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        for (int j = 0; j < classAttendance.foreNoonAttendance[i].periodAttendance.length; j++) {

                            if (classAttendance.foreNoonAttendance[i].periodAttendance[j].getPeriodNumber().length() > periodNoLength) {

                                errHandler.log_app_error("BS_VAL_025", "Period number" + "," + periodNoLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (classAttendance.foreNoonAttendance[i].periodAttendance[j].getAttendance().length() > attendanceLength) {

                                errHandler.log_app_error("BS_VAL_025", "Attendance" + "," + attendanceLength);
                                throw new BSValidationException("BSValidationException");
                            }
                        }

                    }

                    if (classAttendance.afterNoonAttendance != null) {

                        for (int i = 0; i < classAttendance.afterNoonAttendance.length; i++) {

                            if (classAttendance.afterNoonAttendance[i].getStudentID().length() > idLength) {

                                errHandler.log_app_error("BS_VAL_025", "Student id" + "," + idLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            for (int j = 0; j < classAttendance.afterNoonAttendance[i].periodAttendance.length; j++) {

                                if (classAttendance.afterNoonAttendance[i].periodAttendance[j].getPeriodNumber().length() > periodNoLength) {

                                    errHandler.log_app_error("BS_VAL_025", "Period number" + "," + periodNoLength);
                                    throw new BSValidationException("BSValidationException");
                                }

                                if (classAttendance.afterNoonAttendance[i].periodAttendance[j].getAttendance().length() > attendanceLength) {

                                    errHandler.log_app_error("BS_VAL_025", "Attendance" + "," + attendanceLength);
                                    throw new BSValidationException("BSValidationException");
                                }
                            }

                        }

                    }
                    break;

                case "ClassExamSchedule":
                    int hallLength = mds.getColumnMetaData("CLASS", "CLASS_EXAM_SCHEDULE_DETAIL", "HALL", session).getI_ColumnLength();
                    RequestBody<ClassExamSchedule> classExamScheduleBody = request.getReqBody();
                    ClassExamSchedule classExamSchedule = classExamScheduleBody.get();

                    if (classExamSchedule.getStandard().length() > standardLength) {

                        errHandler.log_app_error("BS_VAL_025", "Standard" + "," + standardLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (classExamSchedule.getSection().length() > sectionLength) {

                        errHandler.log_app_error("BS_VAL_025", "Section" + "," + sectionLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (classExamSchedule.getExam().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Exam" + "," + idLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    for (int i = 0; i < classExamSchedule.schedule.length; i++) {

                        if (classExamSchedule.schedule[i].getSubjectID().length() > idLength) {

                            errHandler.log_app_error("BS_VAL_025", "Subject id" + "," + idLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (classExamSchedule.schedule[i].getDate().length() > dateLength) {

                            errHandler.log_app_error("BS_VAL_025", "Date" + "," + dateLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (classExamSchedule.schedule[i].getStartTimeHour().length() > hourLength) {

                            errHandler.log_app_error("BS_VAL_025", "Hour" + "," + hourLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (classExamSchedule.schedule[i].getStartTimeMin().length() > minLength) {

                            errHandler.log_app_error("BS_VAL_025", "Minute" + "," + minLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (classExamSchedule.schedule[i].getEndTimeHour().length() > hourLength) {

                            errHandler.log_app_error("BS_VAL_025", "Hour" + "," + hourLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (classExamSchedule.schedule[i].getEndTimeMin().length() > minLength) {

                            errHandler.log_app_error("BS_VAL_025", "Minute" + "," + minLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (classExamSchedule.schedule[i].getHall().length() > hallLength) {

                            errHandler.log_app_error("BS_VAL_025", "Hall" + "," + hallLength);
                            throw new BSValidationException("BSValidationException");
                        }

                    }
                    break;

                case "ClassMark":
                    int markLength = mds.getColumnMetaData("CLASS", "STUDENT_MARKS", "MARK", session).getI_ColumnLength();
                    int gradeLength = mds.getColumnMetaData("CLASS", "STUDENT_MARKS", "GRADE", session).getI_ColumnLength();
                    RequestBody<ClassMark> classMarkBody = request.getReqBody();
                    ClassMark classMark = classMarkBody.get();

                    if (classMark.getStandard().length() > standardLength) {

                        errHandler.log_app_error("BS_VAL_025", "Standard" + "," + standardLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (classMark.getSection().length() > sectionLength) {

                        errHandler.log_app_error("BS_VAL_025", "Section" + "," + sectionLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (classMark.getExam().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Exam" + "," + idLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (classMark.getSubjectID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Subject id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    for (int i = 0; i < classMark.mark.length; i++) {

                        if (classMark.getMark()[i].getStudentID().length() > idLength) {

                            errHandler.log_app_error("BS_VAL_025", "Student id" + "," + idLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (classMark.getMark()[i].getMark().length() > markLength) {

                            errHandler.log_app_error("BS_VAL_025", "Student id" + "," + markLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (classMark.getMark()[i].getGrade().length() > gradeLength) {

                            errHandler.log_app_error("BS_VAL_025", "Grade" + "," + gradeLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (classMark.getMark()[i].getTeacherFeedback().length() > textFieldLength) {

                            errHandler.log_app_error("BS_VAL_025", "Teacher feedback" + "," + textFieldLength);
                            throw new BSValidationException("BSValidationException");
                        }
                    }

                    break;

                case "ClassTimeTable":

                    RequestBody<ClassTimeTable> classTimeTableBody = request.getReqBody();
                    ClassTimeTable classTimeTable = classTimeTableBody.get();

                    if (classTimeTable.getStandard().length() > standardLength) {

                        errHandler.log_app_error("BS_VAL_025", "Standard" + "," + standardLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (classTimeTable.getSection().length() > sectionLength) {

                        errHandler.log_app_error("BS_VAL_025", "Section" + "," + sectionLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    for (int i = 0; i < classTimeTable.getTimeTable().length; i++) {

                        if (classTimeTable.getTimeTable()[i].getDay().length() > dayLength) {

                            errHandler.log_app_error("BS_VAL_025", "Day" + "," + dayLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        for (int j = 0; j < classTimeTable.getTimeTable()[i].getPeriod().length; j++) {

                            if (classTimeTable.getTimeTable()[i].getPeriod()[j].getPeriodNumber().length() > periodNoLength) {

                                errHandler.log_app_error("BS_VAL_025", "Period number" + "," + periodNoLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (classTimeTable.getTimeTable()[i].getPeriod()[j].getSubjectID().length() > idLength) {

                                errHandler.log_app_error("BS_VAL_025", "Subject Id" + "," + idLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (classTimeTable.getTimeTable()[i].getPeriod()[j].getTeacherID().length() > idLength) {

                                errHandler.log_app_error("BS_VAL_025", "Teacher Id" + "," + idLength);
                                throw new BSValidationException("BSValidationException");
                            }
                        }

                    }

                    break;

                case "ClassLevelConfiguration":
                    RequestBody<ClassLevelConfiguration> classLevelConfigurationBody = request.getReqBody();
                    ClassLevelConfiguration classLevelConfiguration = classLevelConfigurationBody.get();

                    if (classLevelConfiguration.getInstituteID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Institute id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }
                    if (classLevelConfiguration.getStandard().length() > standardLength) {

                        errHandler.log_app_error("BS_VAL_025", "Standard" + "," + standardLength);
                        throw new BSValidationException("BSValidationException");

                    }
                    if (classLevelConfiguration.getSection().length() > sectionLength) {

                        errHandler.log_app_error("BS_VAL_025", "Section" + "," + sectionLength);
                        throw new BSValidationException("BSValidationException");
                    }

//                 for(int i=0;i<classLevelConfiguration.getStandardMaster().length;i++){
//            
//                     if(classLevelConfiguration.getStandardMaster()[i].getStandard().length()>standardLength){
//
//                         errHandler.log_app_error("BS_VAL_025", "Standard"+","+standardLength);
//                         throw new BSValidationException("BSValidationException");
//
//                     }
//                     if(classLevelConfiguration.getStandardMaster()[i].getSection().length()>sectionLength){
//
//                         errHandler.log_app_error("BS_VAL_025", "Section"+","+sectionLength);
//                         throw new BSValidationException("BSValidationException");
//                     }
//
//   
//                     if(classLevelConfiguration.getStandardMaster()[i].getTeacherID().length()>idLength){
//
//                         errHandler.log_app_error("BS_VAL_025", "Teacher id"+","+idLength);
//                         throw new BSValidationException("BSValidationException");
//                     }
//                 
//                 }
                    for (int i = 0; i < classLevelConfiguration.getPeriodTimings().length; i++) {

//                     if(classLevelConfiguration.getPeriodTimings()[i].getStandard().length()>standardLength){
//
//                         errHandler.log_app_error("BS_VAL_025", "Standard"+","+standardLength);
//                         throw new BSValidationException("BSValidationException");
//
//                     }
//                     if(classLevelConfiguration.getPeriodTimings()[i].getSection().length()>sectionLength){
//
//                         errHandler.log_app_error("BS_VAL_025", "Section"+","+sectionLength);
//                         throw new BSValidationException("BSValidationException");
//                     }
                        if (classLevelConfiguration.getPeriodTimings()[i].getStartTimeHour().length() > hourLength) {

                            errHandler.log_app_error("BS_VAL_025", "Hour" + "," + hourLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (classLevelConfiguration.getPeriodTimings()[i].getStartTimeMin().length() > minLength) {

                            errHandler.log_app_error("BS_VAL_025", "Minute" + "," + minLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (classLevelConfiguration.getPeriodTimings()[i].getEndTimeHour().length() > hourLength) {

                            errHandler.log_app_error("BS_VAL_025", "Hour" + "," + hourLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (classLevelConfiguration.getPeriodTimings()[i].getEndTimeMin().length() > minLength) {

                            errHandler.log_app_error("BS_VAL_025", "Minute" + "," + minLength);
                            throw new BSValidationException("BSValidationException");
                        }

                    }

                    break;

                case "ECircular":
                    RequestBody<ECircular> cCircularBody = request.getReqBody();
                    ECircular eCircular = cCircularBody.get();
                    int contentPathLength = mds.getColumnMetaData("INSTITUTE", "IVW_E_CIRCULAR", "CONTENT_PATH", session).getI_ColumnLength();
//                 int urlLength=mds.getColumnMetaData("INSTITUTE", "IVW_E_CIRCULAR", "URL", session).getI_ColumnLength();

                    if (eCircular.getInstituteID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Institute id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (eCircular.getGroupID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Group id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (eCircular.getE_circularID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Circular id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (eCircular.getDescription().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Descrption" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (eCircular.getContentPath().length() > contentPathLength) {

                        errHandler.log_app_error("BS_VAL_025", "Content path" + "," + contentPathLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (eCircular.getMessage().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Message" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    break;

                case "GeneralLevelConfiguration":
                    int markLength1 = mds.getColumnMetaData("INSTITUTE", "IVW_SUBJECT_GRADE_MASTER", "FROM", session).getI_ColumnLength();
                    int gradeLength1 = mds.getColumnMetaData("INSTITUTE", "IVW_SUBJECT_GRADE_MASTER", "GRADE", session).getI_ColumnLength();
                    int notTypeLength = mds.getColumnMetaData("INSTITUTE", "IVW_NOTIFICATION_TYPE_MASTER", "NOTIFICATION_TYPE", session).getI_ColumnLength();
                    RequestBody<GeneralLevelConfiguration> generalLevelConfigurationBody = request.getReqBody();
                    GeneralLevelConfiguration generalLevelConfiguration = generalLevelConfigurationBody.get();

                    if (generalLevelConfiguration.getInstituteID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Institute id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    for (int i = 0; i < generalLevelConfiguration.getSubjectMaster().length; i++) {

                        if (generalLevelConfiguration.getSubjectMaster()[i].getSubjectID().length() > idLength) {

                            errHandler.log_app_error("BS_VAL_025", "Subject id" + "," + idLength);
                            throw new BSValidationException("BSValidationException");

                        }
                        if (generalLevelConfiguration.getSubjectMaster()[i].getSubjectName().length() > nameLength) {

                            errHandler.log_app_error("BS_VAL_025", "Subject name" + "," + nameLength);
                            throw new BSValidationException("BSValidationException");
                        }

                    }

                    for (int i = 0; i < generalLevelConfiguration.getGradeMaster().length; i++) {

                        if (generalLevelConfiguration.getGradeMaster()[i].getFrom().length() > markLength1) {

                            errHandler.log_app_error("BS_VAL_025", "From" + "," + markLength1);
                            throw new BSValidationException("BSValidationException");

                        }
                        if (generalLevelConfiguration.getGradeMaster()[i].getTo().length() > markLength1) {

                            errHandler.log_app_error("BS_VAL_025", "To" + "," + markLength1);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (generalLevelConfiguration.getGradeMaster()[i].getGrade().length() > gradeLength1) {

                            errHandler.log_app_error("BS_VAL_025", "Grade" + "," + gradeLength1);
                            throw new BSValidationException("BSValidationException");
                        }

                    }

                    for (int i = 0; i < generalLevelConfiguration.getExamMaster().length; i++) {

                        if (generalLevelConfiguration.getExamMaster()[i].getExamType().length() > idLength) {

                            errHandler.log_app_error("BS_VAL_025", "examType" + "," + markLength1);
                            throw new BSValidationException("BSValidationException");

                        }
                        if (generalLevelConfiguration.getExamMaster()[i].getDescription().length() > textFieldLength) {

                            errHandler.log_app_error("BS_VAL_025", "Description" + "," + textFieldLength);
                            throw new BSValidationException("BSValidationException");
                        }

                    }

                    for (int i = 0; i < generalLevelConfiguration.getNotificationMaster().length; i++) {

                        if (generalLevelConfiguration.getNotificationMaster()[i].getNotificationType().length() > notTypeLength) {

                            errHandler.log_app_error("BS_VAL_025", "notificationType" + "," + notTypeLength);
                            throw new BSValidationException("BSValidationException");

                        }
                        if (generalLevelConfiguration.getNotificationMaster()[i].getDescription().length() > textFieldLength) {

                            errHandler.log_app_error("BS_VAL_025", "Description" + "," + textFieldLength);
                            throw new BSValidationException("BSValidationException");
                        }

                    }

                    for (int i = 0; i < generalLevelConfiguration.getFeeMaster().length; i++) {

                        if (generalLevelConfiguration.getFeeMaster()[i].getFeeType().length() > feeTypeLength) {

                            errHandler.log_app_error("BS_VAL_025", "feeType" + "," + feeTypeLength);
                            throw new BSValidationException("BSValidationException");

                        }
                        if (generalLevelConfiguration.getFeeMaster()[i].getFeeDescription().length() > textFieldLength) {

                            errHandler.log_app_error("BS_VAL_025", "Description" + "," + textFieldLength);
                            throw new BSValidationException("BSValidationException");
                        }

                    }

                    break;

                case "GroupMapping":
                    RequestBody<GroupMapping> groupMappingBody = request.getReqBody();
                    GroupMapping groupMapping = groupMappingBody.get();

                    if (groupMapping.getInstituteID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Institute id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    for (int i = 0; i < groupMapping.getGroup().length; i++) {

                        if (groupMapping.getGroup()[i].getStandard().length() > standardLength) {

                            errHandler.log_app_error("BS_VAL_025", "Standard" + "," + standardLength);
                            throw new BSValidationException("BSValidationException");

                        }
                        if (groupMapping.getGroup()[i].getSection().length() > sectionLength) {

                            errHandler.log_app_error("BS_VAL_025", "Section" + "," + sectionLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (groupMapping.getGroup()[i].getStudentID().length() > idLength) {

                            errHandler.log_app_error("BS_VAL_025", "Student id" + "," + idLength);
                            throw new BSValidationException("BSValidationException");
                        }

                    }

                    break;

                case "HolidayMaintenance":
                    RequestBody<HolidayMaintanence> holidayBody = request.getReqBody();
                    HolidayMaintanence holiday = holidayBody.get();

                    if (holiday.getInstituteID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Institute id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (holiday.getYear().length() > yearLength) {

                        errHandler.log_app_error("BS_VAL_025", "Year" + "," + yearLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (holiday.getMonth().length() > monthLength) {

                        errHandler.log_app_error("BS_VAL_025", "Month" + "," + monthLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (holiday.getHoliday().length() > 31) {

                        errHandler.log_app_error("BS_VAL_025", "Holiday" + "," + 31);
                        throw new BSValidationException("BSValidationException");

                    }

                    break;

                case "StudentECircular":
                    RequestBody<StudentECircular> studentECircularBody = request.getReqBody();
                    StudentECircular studentECircular = studentECircularBody.get();
//                 int contentPathLength1=mds.getColumnMetaData("STUDENT", "SVW_STUDENT_E_CIRCULAR", "CONTENT_PATH", session).getI_ColumnLength();
//                 int urlLength1=mds.getColumnMetaData("STUDENT", "SVW_STUDENT_E_CIRCULAR", "URL", session).getI_ColumnLength();

                    if (studentECircular.getStudentID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Student id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (studentECircular.geteCircularID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Circular id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (studentECircular.geteCircularDescription().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Descrption" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }

//                 if(studentECircular.getContentPath().length()>contentPathLength1){
//                     
//                     errHandler.log_app_error("BS_VAL_025", "Content path"+","+contentPathLength1);
//                     throw new BSValidationException("BSValidationException");
//                 }
//                 
//                 if(studentECircular.getUrl().length()>urlLength1){
//                     
//                     errHandler.log_app_error("BS_VAL_025", "URL"+","+urlLength1);
//                     throw new BSValidationException("BSValidationException");
//                 }
                    break;

                case "StudentAssignment":
                    RequestBody<StudentAssignment> studentAssignmentBody = request.getReqBody();
                    StudentAssignment studentAssignment = studentAssignmentBody.get();
                    int contentPathLength2 = mds.getColumnMetaData("STUDENT", "SVW_STUDENT_ASSIGNMENT", "CONTENT_PATH", session).getI_ColumnLength();
                    int urlLength2 = mds.getColumnMetaData("STUDENT", "SVW_STUDENT_ASSIGNMENT", "URL", session).getI_ColumnLength();
                    int assignmentTypeLength = mds.getColumnMetaData("STUDENT", "SVW_STUDENT_ASSIGNMENT", "ASSIGNMENT_TYPE", session).getI_ColumnLength();
                    int assignmentStatusLength = mds.getColumnMetaData("STUDENT", "SVW_STUDENT_ASSIGNMENT", "STATUS", session).getI_ColumnLength();

                    if (studentAssignment.getStudentID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Student id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (studentAssignment.getSubjectID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Subject id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (studentAssignment.getAssignmentID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Assignment id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (studentAssignment.getAssignmentDescription().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Descrption" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentAssignment.getDueDate().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "Due date" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentAssignment.getAssignentType().length() > assignmentTypeLength) {

                        errHandler.log_app_error("BS_VAL_025", "Assignment type" + "," + assignmentTypeLength);
                        throw new BSValidationException("BSValidationException");
                    }

//                 if(studentAssignment.getCompletedDate().length()>dateLength){
//                     
//                     errHandler.log_app_error("BS_VAL_025", "Completed date"+","+dateLength);
//                     throw new BSValidationException("BSValidationException");
//                 }
//                 
//                 if(studentAssignment.getStatus().length()>assignmentStatusLength){
//                     
//                     errHandler.log_app_error("BS_VAL_025", "Status"+","+assignmentStatusLength);
//                     throw new BSValidationException("BSValidationException");
//                 }
                    if (studentAssignment.getTeacherComment().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "TeacherComment" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }

//                 if(studentAssignment.getParentComment().length()>textFieldLength){
//                     
//                     errHandler.log_app_error("BS_VAL_025", "Parent comment"+","+textFieldLength);
//                     throw new BSValidationException("BSValidationException");
//                 }
//                 
//                 if(studentAssignment.getContentPath().length()>idLength){
//                     
//                     errHandler.log_app_error("BS_VAL_025", "Content path"+","+contentPathLength2);
//                     throw new BSValidationException("BSValidationException");
//                 }
                    if (studentAssignment.getUrl().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "URL" + "," + urlLength2);
                        throw new BSValidationException("BSValidationException");
                    }
                    break;

                case "StudentAttendance":

                    RequestBody<StudentAttendance> studentAttendanceBody = request.getReqBody();
                    StudentAttendance studentAttendance = studentAttendanceBody.get();

                    if (studentAttendance.getStudentID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Student id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (studentAttendance.getDate().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "Date" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }

//                 for(int i=0;i<studentAttendance.getPeriodAttendance().length;i++){
//                     
//                     if(studentAttendance.getPeriodAttendance()[i].getPeriodNumber().length()>periodNoLength){
//                         
//                        errHandler.log_app_error("BS_VAL_025", "Period number"+","+periodNoLength);
//                        throw new BSValidationException("BSValidationException");
//                     }
//                     
//                     if(studentAttendance.getPeriodAttendance()[i].getAttendance().length()>attendanceLength){
//                         
//                        errHandler.log_app_error("BS_VAL_025", "Attendance"+","+attendanceLength);
//                        throw new BSValidationException("BSValidationException");
//                     }
//              
//                     
//                 }
                    break;

                case "StudentExamSchedule":
                    int hallLength1 = mds.getColumnMetaData("STUDENT", "SVW_STUDENT_EXAM_SCHEDULE_DETAIL", "HALL", session).getI_ColumnLength();
                    RequestBody<StudentExamSchedule> studentExamScheduleBody = request.getReqBody();
                    StudentExamSchedule studentExamSchedule = studentExamScheduleBody.get();

                    if (studentExamSchedule.getStudentID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Student id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (studentExamSchedule.getExam().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Exam" + "," + idLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    for (int i = 0; i < studentExamSchedule.schedule.length; i++) {

                        if (studentExamSchedule.schedule[i].getSubjectID().length() > idLength) {

                            errHandler.log_app_error("BS_VAL_025", "Subject id" + "," + idLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (studentExamSchedule.schedule[i].getDate().length() > dateLength) {

                            errHandler.log_app_error("BS_VAL_025", "Date" + "," + dateLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (studentExamSchedule.schedule[i].getStartTimeHour().length() > hourLength) {

                            errHandler.log_app_error("BS_VAL_025", "Hour" + "," + hourLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (studentExamSchedule.schedule[i].getStartTimeMin().length() > minLength) {

                            errHandler.log_app_error("BS_VAL_025", "Minute" + "," + minLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (studentExamSchedule.schedule[i].getEndTimeHour().length() > hourLength) {

                            errHandler.log_app_error("BS_VAL_025", "Hour" + "," + hourLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (studentExamSchedule.schedule[i].getEndTimeMin().length() > minLength) {

                            errHandler.log_app_error("BS_VAL_025", "Minute" + "," + minLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (studentExamSchedule.schedule[i].getHall().length() > hallLength1) {

                            errHandler.log_app_error("BS_VAL_025", "Hall" + "," + hallLength1);
                            throw new BSValidationException("BSValidationException");
                        }

                    }
                    break;

                case "StudentFeeManagement":
                    int feeStatusLength = mds.getColumnMetaData("STUDENT", "SVW_STUDENT_FEE_MANAGEMENT", "STATUS", session).getI_ColumnLength();
                    RequestBody<StudentFeeManagement> studentFeeManagementBody = request.getReqBody();
                    StudentFeeManagement studentFeeManagement = studentFeeManagementBody.get();

                    if (studentFeeManagement.getStudentID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Student id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (studentFeeManagement.getFeeID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Fee id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");
                    }

//                 if(studentFeeManagement.getFeeType().length()>feeTypeLength){
//                     
//                     errHandler.log_app_error("BS_VAL_025", "Fee Type"+","+feeTypeLength);
//                     throw new BSValidationException("BSValidationException");
//                 }
                    if (studentFeeManagement.getAmount().length() > amountLength) {

                        errHandler.log_app_error("BS_VAL_025", "Amount" + "," + amountLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (studentFeeManagement.getDueDate().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "Due date" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (studentFeeManagement.getStatus().length() > feeStatusLength) {

                        errHandler.log_app_error("BS_VAL_025", "Status" + "," + feeStatusLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentFeeManagement.getFeePaid().length() > amountLength) {

                        errHandler.log_app_error("BS_VAL_025", "Fee paid" + "," + amountLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentFeeManagement.getOutStanding().length() > amountLength) {

                        errHandler.log_app_error("BS_VAL_025", "Out standing" + "," + amountLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (studentFeeManagement.getPaidDate().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "Paid date" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    break;
                case "StudentLeaveManagement":
                    int fromNoonLength = mds.getColumnMetaData("LEAVE", "SVW_STUDENT_LEAVE_MANAGEMENT", "FROM_NOON", session).getI_ColumnLength();
                    int toNoonLength = mds.getColumnMetaData("LEAVE", "SVW_STUDENT_LEAVE_MANAGEMENT", "TO_NOON", session).getI_ColumnLength();
                    int leaveTypeLength = mds.getColumnMetaData("LEAVE", "SVW_STUDENT_LEAVE_MANAGEMENT", "TYPE", session).getI_ColumnLength();
                    int reasonLength = mds.getColumnMetaData("LEAVE", "SVW_STUDENT_LEAVE_MANAGEMENT", "REASON", session).getI_ColumnLength();
                    RequestBody<StudentLeaveManagement> studentLeaveManagementBody = request.getReqBody();
                    StudentLeaveManagement studentLeaveManagement = studentLeaveManagementBody.get();

                    if (studentLeaveManagement.getStudentID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Student id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (studentLeaveManagement.getFrom().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "From " + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentLeaveManagement.getTo().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "To" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentLeaveManagement.getFromNoon().length() > fromNoonLength) {

                        errHandler.log_app_error("BS_VAL_025", "From noon" + "," + fromNoonLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (studentLeaveManagement.getToNoon().length() > toNoonLength) {

                        errHandler.log_app_error("BS_VAL_025", "To noon" + "," + toNoonLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (studentLeaveManagement.getType().length() > leaveTypeLength) {

                        errHandler.log_app_error("BS_VAL_025", "Leave type" + "," + leaveTypeLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentLeaveManagement.getReason().length() > reasonLength) {

                        errHandler.log_app_error("BS_VAL_025", "Reason" + "," + reasonLength);
                        throw new BSValidationException("BSValidationException");
                    }

//                 if(studentLeaveManagement.getStatus().length()>leaveStatusLength){
//                     
//                     errHandler.log_app_error("BS_VAL_025", "Status"+","+leaveStatusLength);
//                     throw new BSValidationException("BSValidationException");
//                 }
//                  if(studentLeaveManagement.getRemarks().length()>textFieldLength){
//                     
//                     errHandler.log_app_error("BS_VAL_025", "Remarks"+","+textFieldLength);
//                     throw new BSValidationException("BSValidationException");
//                 }
                    break;

                case "StudentNotification":
                    RequestBody<StudentNotification> studentNotificationBody = request.getReqBody();
                    StudentNotification studentNotification = studentNotificationBody.get();
                    int notificationType = mds.getColumnMetaData("STUDENT", "SVW_STUDENT_NOTIFICATION", "NOTIFICATION_TYPE", session).getI_ColumnLength();
                    int notificationMessageLength = mds.getColumnMetaData("STUDENT", "SVW_STUDENT_NOTIFICATION", "MESSAGE", session).getI_ColumnLength();

                    if (studentNotification.getStudentID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Student id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (studentNotification.getNotificationID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Notification id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (studentNotification.getNotificationType().length() > notificationType) {

                        errHandler.log_app_error("BS_VAL_025", "Notification type" + "," + notificationType);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentNotification.getMessage().length() > notificationMessageLength) {

                        errHandler.log_app_error("BS_VAL_025", "Message" + "," + notificationMessageLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    break;

                case "StudentOtherActivity":
                    RequestBody<StudentOtherActivity> studentOtherActivityBody = request.getReqBody();
                    StudentOtherActivity studentOtherActivity = studentOtherActivityBody.get();
//                 int activityTypeLength=mds.getColumnMetaData("OTHER_ACTIVITY", "SVW_STUDENT_OTHER_ACTIVITY", "ACTIVITY_TYPE", session).getI_ColumnLength();
//                 int activityLevelLength=mds.getColumnMetaData("OTHER_ACTIVITY", "SVW_STUDENT_OTHER_ACTIVITY", "LEVEL", session).getI_ColumnLength();
//                 int activityVenueLength=mds.getColumnMetaData("OTHER_ACTIVITY", "SVW_STUDENT_OTHER_ACTIVITY", "VENUE", session).getI_ColumnLength();
                    int activityResultLength = mds.getColumnMetaData("OTHER_ACTIVITY", "SVW_STUDENT_OTHER_ACTIVITY", "RESULT", session).getI_ColumnLength();
                    int activityParticipateLength = mds.getColumnMetaData("OTHER_ACTIVITY", "SVW_STUDENT_OTHER_ACTIVITY", "PARTICIPATION_STATUS", session).getI_ColumnLength();

                    if (studentOtherActivity.getStudentID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Student id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (studentOtherActivity.getActivityID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Activity id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (studentOtherActivity.getActivityName().length() > nameLength) {

                        errHandler.log_app_error("BS_VAL_025", "Activity name" + "," + nameLength);
                        throw new BSValidationException("BSValidationException");

                    }

//                 if(studentOtherActivity.getActivityType().length()>activityTypeLength){
//                     
//                     errHandler.log_app_error("BS_VAL_025", "Activity type"+","+activityTypeLength);
//                     throw new BSValidationException("BSValidationException");
//                 }
//                 
//                 if(studentOtherActivity.getLevel().length()>activityLevelLength){
//                     
//                     errHandler.log_app_error("BS_VAL_025", "Activity level"+","+activityLevelLength);
//                     throw new BSValidationException("BSValidationException");
//                 }
//                 
//                 if(studentOtherActivity.getVenue().length()>activityVenueLength){
//                     
//                     errHandler.log_app_error("BS_VAL_025", "Activity venue"+","+activityVenueLength);
//                     throw new BSValidationException("BSValidationException");
//                 }
                    if (studentOtherActivity.getResult().length() > activityResultLength) {

                        errHandler.log_app_error("BS_VAL_025", "Activity result" + "," + activityResultLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentOtherActivity.getIntrest().length() > activityParticipateLength) {

                        errHandler.log_app_error("BS_VAL_025", "Activity participation" + "," + activityParticipateLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentOtherActivity.getDueDate().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "Due date" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentOtherActivity.getDate().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "Date" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    break;

                case "StudentPayment":
                    int paymentModeLength = mds.getColumnMetaData("STUDENT", "SVW_STUDENT_PAYMENT", "PAYMENT_MODE", session).getI_ColumnLength();
                    RequestBody<StudentPayment> studentPaymentBody = request.getReqBody();
                    StudentPayment studentPayment = studentPaymentBody.get();

                    if (studentPayment.getStudentID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Student id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (studentPayment.getPaymentID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Payment id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentPayment.getPaymentMode().length() > paymentModeLength) {

                        errHandler.log_app_error("BS_VAL_025", "Payment mode" + "," + paymentModeLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentPayment.getPaymentpaid().length() > amountLength) {

                        errHandler.log_app_error("BS_VAL_025", "Payment paid" + "," + amountLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (studentPayment.getPaymentDate().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "Payment date" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    break;

                case "StudentProgressCard":
                    int markLength2 = mds.getColumnMetaData("STUDENT", "SVW_STUDENT_MARKS", "MARK", session).getI_ColumnLength();
                    int gradeLength2 = mds.getColumnMetaData("STUDENT", "SVW_STUDENT_MARKS", "GRADE", session).getI_ColumnLength();
                    RequestBody<StudentProgressCard> studentProgressCardBody = request.getReqBody();
                    StudentProgressCard studentProgressCard = studentProgressCardBody.get();

                    if (studentProgressCard.getStudentID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Student id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (studentProgressCard.getExam().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Exam" + "," + idLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    for (int i = 0; i < studentProgressCard.getMark().length; i++) {

                        if (studentProgressCard.getMark()[i].getSubjectID().length() > idLength) {

                            errHandler.log_app_error("BS_VAL_025", "Subject id" + "," + idLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (studentProgressCard.getMark()[i].getMark().length() > markLength2) {

                            errHandler.log_app_error("BS_VAL_025", "Student id" + "," + markLength2);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (studentProgressCard.getMark()[i].getGrade().length() > gradeLength2) {

                            errHandler.log_app_error("BS_VAL_025", "Grade" + "," + gradeLength2);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (studentProgressCard.getMark()[i].getTeacherFeedback().length() > textFieldLength) {

                            errHandler.log_app_error("BS_VAL_025", "Teacher feedback" + "," + textFieldLength);
                            throw new BSValidationException("BSValidationException");
                        }

                    }

                    break;

                case "StudentTimeTable":

                    RequestBody<StudentTimeTable> studentTimeTableBody = request.getReqBody();
                    StudentTimeTable studentTimeTable = studentTimeTableBody.get();

                    if (studentTimeTable.getStudentID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Student id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    for (int i = 0; i < studentTimeTable.getTimeTable().length; i++) {

                        if (studentTimeTable.getTimeTable()[i].getDay().length() > dayLength) {

                            errHandler.log_app_error("BS_VAL_025", "Day" + "," + dayLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        for (int j = 0; j < studentTimeTable.getTimeTable()[i].getPeriod().length; j++) {

                            if (studentTimeTable.getTimeTable()[i].getPeriod()[j].getPeriodNumber().length() > periodNoLength) {

                                errHandler.log_app_error("BS_VAL_025", "Period number" + "," + periodNoLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (studentTimeTable.getTimeTable()[i].getPeriod()[j].getSubjectID().length() > idLength) {

                                errHandler.log_app_error("BS_VAL_025", "Subject Id" + "," + idLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (studentTimeTable.getTimeTable()[i].getPeriod()[j].getTeacherID().length() > idLength) {

                                errHandler.log_app_error("BS_VAL_025", "Teacher Id" + "," + idLength);
                                throw new BSValidationException("BSValidationException");
                            }
                        }

                    }

                    break;

                case "Payroll":
                    RequestBody<Payroll> teacherPayrollBody = request.getReqBody();
                    Payroll teacherPayroll = teacherPayrollBody.get();
                    int pathLength = mds.getColumnMetaData("TEACHER", "TVW_PAYROLL", "PATH", session).getI_ColumnLength();

                    if (teacherPayroll.getTeacherID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Teacher id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (teacherPayroll.getYear().length() > yearLength) {

                        errHandler.log_app_error("BS_VAL_025", "Year" + "," + yearLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (teacherPayroll.getMonth().length() > monthLength) {

                        errHandler.log_app_error("BS_VAL_025", "Month" + "," + monthLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (teacherPayroll.getPath().length() > pathLength) {

                        errHandler.log_app_error("BS_VAL_025", "Path" + "," + pathLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    break;

                case "TeacherAttendance":

                    RequestBody<TeacherAttendance> teacherAttendanceBody = request.getReqBody();
                    TeacherAttendance teacherAttendance = teacherAttendanceBody.get();

                    if (teacherAttendance.getTeacherID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Teacher id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (teacherAttendance.getDate().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "Date" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    for (int i = 0; i < teacherAttendance.getPeriodAttendance().length; i++) {

                        if (teacherAttendance.getPeriodAttendance()[i].getPeriodNumber().length() > periodNoLength) {

                            errHandler.log_app_error("BS_VAL_025", "Period number" + "," + periodNoLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (teacherAttendance.getPeriodAttendance()[i].getAttendance().length() > attendanceLength) {

                            errHandler.log_app_error("BS_VAL_025", "Attendance" + "," + attendanceLength);
                            throw new BSValidationException("BSValidationException");
                        }

                    }

                    break;

                case "TeacherLeaveManagement":
                    int fromNoonLength1 = mds.getColumnMetaData("LEAVE", "TVW_TEACHER_LEAVE_MANAGEMENT", "FROM_NOON", session).getI_ColumnLength();
                    int toNoonLength1 = mds.getColumnMetaData("LEAVE", "TVW_TEACHER_LEAVE_MANAGEMENT", "TO_NOON", session).getI_ColumnLength();
                    int leaveTypeLength1 = mds.getColumnMetaData("LEAVE", "TVW_TEACHER_LEAVE_MANAGEMENT", "TYPE", session).getI_ColumnLength();
                    int reasonLength1 = mds.getColumnMetaData("LEAVE", "TVW_TEACHER_LEAVE_MANAGEMENT", "REASON", session).getI_ColumnLength();
                    RequestBody<TeacherLeaveManagement> teacherLeaveManagementBody = request.getReqBody();
                    TeacherLeaveManagement teacherLeaveManagement = teacherLeaveManagementBody.get();

                    if (teacherLeaveManagement.getTeacherID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Teacher id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (teacherLeaveManagement.getFrom().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "From " + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (teacherLeaveManagement.getTo().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "To" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (teacherLeaveManagement.getFromNoon().length() > fromNoonLength1) {

                        errHandler.log_app_error("BS_VAL_025", "From noon" + "," + fromNoonLength1);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (teacherLeaveManagement.getToNoon().length() > toNoonLength1) {

                        errHandler.log_app_error("BS_VAL_025", "To noon" + "," + toNoonLength1);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (teacherLeaveManagement.getType().length() > leaveTypeLength1) {

                        errHandler.log_app_error("BS_VAL_025", "Leave type" + "," + leaveTypeLength1);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (teacherLeaveManagement.getReason().length() > reasonLength1) {

                        errHandler.log_app_error("BS_VAL_025", "Reason" + "," + reasonLength1);
                        throw new BSValidationException("BSValidationException");
                    }

                    break;

                case "TeacherTimeTable":

                    RequestBody<TeacherTimeTable> teacherTimeTableBody = request.getReqBody();
                    TeacherTimeTable teacherTimeTable = teacherTimeTableBody.get();

                    if (teacherTimeTable.getTeacherID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Teacher id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    for (int i = 0; i < teacherTimeTable.timeTable.length; i++) {

                        if (teacherTimeTable.getTimeTable()[i].getDay().length() > dayLength) {

                            errHandler.log_app_error("BS_VAL_025", "Day" + "," + dayLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        for (int j = 0; j < teacherTimeTable.getTimeTable()[i].getPeriod().length; j++) {

                            if (teacherTimeTable.getTimeTable()[i].getPeriod()[j].getPeriodNumber().length() > periodNoLength) {

                                errHandler.log_app_error("BS_VAL_025", "Period number" + "," + periodNoLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (teacherTimeTable.getTimeTable()[i].getPeriod()[j].getSubjectID().length() > idLength) {

                                errHandler.log_app_error("BS_VAL_025", "Subject Id" + "," + idLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (teacherTimeTable.getTimeTable()[i].getPeriod()[j].getStandard().length() > standardLength) {

                                errHandler.log_app_error("BS_VAL_025", "Standard" + "," + standardLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (teacherTimeTable.getTimeTable()[i].getPeriod()[j].getSection().length() > sectionLength) {

                                errHandler.log_app_error("BS_VAL_025", "Section" + "," + sectionLength);
                                throw new BSValidationException("BSValidationException");
                            }
                        }

                    }

                    break;

                case "InstituteAssignment":
                    RequestBody<InstituteAssignment> instituteAssignmentBody = request.getReqBody();
                    InstituteAssignment instituteAssignment = instituteAssignmentBody.get();
                    int urlLength3 = mds.getColumnMetaData("ASSIGNMENT", "IVW_ASSIGNMENT", "URL", session).getI_ColumnLength();
                    int assignmentTypeLength1 = mds.getColumnMetaData("ASSIGNMENT", "IVW_ASSIGNMENT", "ASSIGNMENT_TYPE", session).getI_ColumnLength();

                    if (instituteAssignment.getInstituteID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Institute id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (instituteAssignment.getGroupID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Group id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (instituteAssignment.getSubjectID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Subject id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (instituteAssignment.getAssignmentID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Assignment id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (instituteAssignment.getAssignmentDescription().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Descrption" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (instituteAssignment.getDueDate().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "Due date" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (instituteAssignment.getAssignmentType().length() > assignmentTypeLength1) {

                        errHandler.log_app_error("BS_VAL_025", "Assignment type" + "," + assignmentTypeLength1);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (instituteAssignment.getTeacherComments().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "TeacherComments" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (instituteAssignment.getUrl().length() > urlLength3) {

                        errHandler.log_app_error("BS_VAL_025", "URL" + "," + urlLength3);
                        throw new BSValidationException("BSValidationException");
                    }
                    break;

                case "InstituteFeeManagement":
                    RequestBody<InstituteFeeManagement> instituteFeeManagementBody = request.getReqBody();
                    InstituteFeeManagement instituteFeeManagement = instituteFeeManagementBody.get();

                    if (instituteFeeManagement.getInstituteID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Institute id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (instituteFeeManagement.getFeeID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Fee id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (instituteFeeManagement.getFeeType().length() > feeTypeLength) {

                        errHandler.log_app_error("BS_VAL_025", "Fee Type" + "," + feeTypeLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (instituteFeeManagement.getAmount().length() > amountLength) {

                        errHandler.log_app_error("BS_VAL_025", "Amount" + "," + amountLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (instituteFeeManagement.getDueDate().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "Due date" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    break;

                case "InstituteOtherActivity":
                    RequestBody<InstituteOtherActivity> instituteOtherActivityBody = request.getReqBody();
                    InstituteOtherActivity instituteOtherActivity = instituteOtherActivityBody.get();
                    int activityTypeLength1 = mds.getColumnMetaData("OTHER_ACTIVITY", "IVW_OTHER_ACTIVITY", "ACTIVITY_TYPE", session).getI_ColumnLength();
                    int activityLevelLength1 = mds.getColumnMetaData("OTHER_ACTIVITY", "IVW_OTHER_ACTIVITY", "LEVEL", session).getI_ColumnLength();
                    int activityVenueLength1 = mds.getColumnMetaData("OTHER_ACTIVITY", "IVW_OTHER_ACTIVITY", "VENUE", session).getI_ColumnLength();

                    if (instituteOtherActivity.getInstituteID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Institute id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (instituteOtherActivity.getActivityID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Activity id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (instituteOtherActivity.getActivityName().length() > nameLength) {

                        errHandler.log_app_error("BS_VAL_025", "Activity name" + "," + nameLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (instituteOtherActivity.getActivityType().length() > activityTypeLength1) {

                        errHandler.log_app_error("BS_VAL_025", "Activity type" + "," + activityTypeLength1);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (instituteOtherActivity.getLevel().length() > activityLevelLength1) {

                        errHandler.log_app_error("BS_VAL_025", "Activity level" + "," + activityLevelLength1);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (instituteOtherActivity.getVenue().length() > activityVenueLength1) {

                        errHandler.log_app_error("BS_VAL_025", "Activity venue" + "," + activityVenueLength1);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (instituteOtherActivity.getDueDate().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "Due date" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (instituteOtherActivity.getDate().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "Date" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    break;

                case "InstitutePayment":
                    int paymentModeLength1 = mds.getColumnMetaData("PAYMENT", "INSTITUTE_PAYMENT_MASTER", "PAYMENT_MODE", session).getI_ColumnLength();
                    RequestBody<InstitutePayment> institutePaymentBody = request.getReqBody();
                    InstitutePayment institutePayment = institutePaymentBody.get();

                    if (institutePayment.getInstituteID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Institute id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (institutePayment.getPaymentID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Payment id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (institutePayment.getPaymentMode().length() > paymentModeLength1) {

                        errHandler.log_app_error("BS_VAL_025", "Payment mode" + "," + paymentModeLength1);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (institutePayment.getPaymentpaid().length() > amountLength) {

                        errHandler.log_app_error("BS_VAL_025", "Payment paid" + "," + amountLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (institutePayment.getPaymentDate().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "Payment date" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    
//                 if(institutePayment.getOutStanding().length()>amountLength){
//                     
//                     errHandler.log_app_error("BS_VAL_025", "Out standing"+","+amountLength);
//                     throw new BSValidationException("BSValidationException");
//                 }
                    break;

                case "Notification":
                    RequestBody<Notification> instituteNotificationBody = request.getReqBody();
                    Notification instituteNotification = instituteNotificationBody.get();
                    int notificationType1 = mds.getColumnMetaData("INSTITUTE", "IVW_NOTIFICATION_MASTER", "NOTIFICATION_TYPE", session).getI_ColumnLength();
                    int notificationMessageLength1 = mds.getColumnMetaData("INSTITUTE", "IVW_NOTIFICATION_MASTER", "MESSAGE", session).getI_ColumnLength();
                    int notificationFrequencyLength = mds.getColumnMetaData("INSTITUTE", "IVW_NOTIFICATION_MASTER", "NOTIFICATION_FREQUENCY", session).getI_ColumnLength();
                    int mediaCommunicationLength = mds.getColumnMetaData("INSTITUTE", "IVW_NOTIFICATION_MASTER", "MEDIA_COMMUNICATION", session).getI_ColumnLength();

                    if (instituteNotification.getInstituteID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Institute id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (instituteNotification.getNotificationID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Notification id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (instituteNotification.getDate().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "date" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (instituteNotification.getNotificationType().length() > notificationType1) {

                        errHandler.log_app_error("BS_VAL_025", "Notification type" + "," + notificationType1);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (instituteNotification.getMessage().length() > notificationMessageLength1) {

                        errHandler.log_app_error("BS_VAL_025", "Enlish Message" + "," + notificationMessageLength1);
                        throw new BSValidationException("BSValidationException");
                    }

                    ByteBuffer copy = ByteBuffer.wrap(instituteNotification.getOtherLanguageMessage().getBytes(Charset.forName("UTF-8")));

                    if (copy.limit() > notificationMessageLength1) {

                        errHandler.log_app_error("BS_VAL_025", "Other Language Message" + "," + notificationMessageLength1);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (instituteNotification.getNotificationFrequency().length() > notificationFrequencyLength) {

                        errHandler.log_app_error("BS_VAL_025", "Notification frequency" + "," + notificationFrequencyLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (instituteNotification.getDay().length() > dayLength) {

                        errHandler.log_app_error("BS_VAL_025", "day" + "," + dayLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (instituteNotification.getMonth().length() > monthLength) {

                        errHandler.log_app_error("BS_VAL_025", "month" + "," + monthLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (instituteNotification.getMediaCommunication().length() > mediaCommunicationLength) {

                        errHandler.log_app_error("BS_VAL_025", "Media communication" + "," + mediaCommunicationLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (instituteNotification.getAssignee().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Assignee" + "," + idLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    break;

                case "StudentProfile":
                    RequestBody<StudentProfile> studentProfileBody = request.getReqBody();
                    StudentProfile studentProfile = studentProfileBody.get();
                    int bloodGroupLength = mds.getColumnMetaData("STUDENT", "SVW_STUDENT_PROFILE", "BLOODGROUP", session).getI_ColumnLength();
                    int imagePathLength = mds.getColumnMetaData("STUDENT", "SVW_STUDENT_PROFILE", "IMAGE_PATH", session).getI_ColumnLength();
                    int occupationLength = mds.getColumnMetaData("STUDENT", "SVW_CONTACT_PERSON_DETAILS", "CP_OCCUPATION", session).getI_ColumnLength();
                    int relationshipLength = mds.getColumnMetaData("STUDENT", "SVW_CONTACT_PERSON_DETAILS", "CP_RELATIONSHIP", session).getI_ColumnLength();

                    if (studentProfile.getStudentID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Student id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (studentProfile.getStudentName().length() > nameLength) {

                        errHandler.log_app_error("BS_VAL_025", "Student name" + "," + nameLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (studentProfile.getGen().getStandard().length() > standardLength) {

                        errHandler.log_app_error("BS_VAL_025", "Standard" + "," + standardLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentProfile.getGen().getSection().length() > sectionLength) {

                        errHandler.log_app_error("BS_VAL_025", "Section" + "," + sectionLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentProfile.getGen().getDob().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "DOB" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentProfile.getGen().getDob().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "DOB" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentProfile.getGen().getBloodGroup().length() > bloodGroupLength) {

                        errHandler.log_app_error("BS_VAL_025", "Blood group" + "," + bloodGroupLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentProfile.getGen().getAddress().getAddressLine1().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Address line 1" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (studentProfile.getGen().getAddress().getAddressLine2().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Address line 2" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (studentProfile.getGen().getAddress().getAddressLine3().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Address line 3" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (studentProfile.getGen().getAddress().getAddressLine4().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Address line 4" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (studentProfile.getGen().getAddress().getAddressLine5().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Address line 5" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentProfile.getProfileImagePath().length() > imagePathLength) {

                        errHandler.log_app_error("BS_VAL_025", "Profile image path" + "," + imagePathLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentProfile.getNote().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Notes" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (studentProfile.getEmer().getExistingMedicalDetails().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Existing medical detail" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }

//                 for(int i=0;i<studentProfile.getEmer().getCp().length;i++){
//                     
//                     if(studentProfile.getEmer().getCp()[i].getCp_ID().length()>idLength){
//                     
//                        errHandler.log_app_error("BS_VAL_025", "Contact person id"+","+idLength);
//                        throw new BSValidationException("BSValidationException");
//                     }
//                     
//                     if(studentProfile.getEmer().getCp()[i].getCp_name().length()>nameLength){
//                     
//                        errHandler.log_app_error("BS_VAL_025", "Contact person name"+","+nameLength);
//                        throw new BSValidationException("BSValidationException");
//                     }
//                     
//                     if(studentProfile.getEmer().getCp()[i].getCp_occupation().length()>occupationLength){
//                     
//                        errHandler.log_app_error("BS_VAL_025", "Contact person occupation"+","+occupationLength);
//                        throw new BSValidationException("BSValidationException");
//                     }
//                     
//                     if(studentProfile.getEmer().getCp()[i].getCp_relationship().length()>relationshipLength){
//                     
//                        errHandler.log_app_error("BS_VAL_025", "Contact person relationship"+","+relationshipLength);
//                        throw new BSValidationException("BSValidationException");
//                     }
//                     
//                     if(studentProfile.getEmer().getCp()[i].getCp_mailID().length()>emailLength){
//                      
//                        errHandler.log_app_error("BS_VAL_025", "Contact person email id"+","+emailLength);
//                        throw new BSValidationException("BSValidationException");
//                     }
//                     
//                     if(studentProfile.getEmer().getCp()[i].getCp_contactNo().length()>contactNoLength){
//                      
//                        errHandler.log_app_error("BS_VAL_025", "Contact person contact no"+","+contactNoLength);
//                        throw new BSValidationException("BSValidationException");
//                     }
//                     
//                     if(studentProfile.getEmer().getCp()[i].getCp_imgPath().length()>imagePathLength){
//                      
//                        errHandler.log_app_error("BS_VAL_025", "Contact person image path"+","+imagePathLength);
//                        throw new BSValidationException("BSValidationException");
//                     }
//                 }
                    for (int i = 0; i < studentProfile.getFam().length; i++) {

                        if (studentProfile.getFam()[i].getMemberID().length() > idLength) {

                            errHandler.log_app_error("BS_VAL_025", "Family member id" + "," + idLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (studentProfile.getFam()[i].getMemberName().length() > nameLength) {

                            errHandler.log_app_error("BS_VAL_025", "Family member name" + "," + nameLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (studentProfile.getFam()[i].getMemberOccupation().length() > occupationLength) {

                            errHandler.log_app_error("BS_VAL_025", "Family member occupation" + "," + occupationLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (studentProfile.getFam()[i].getMemberRelationship().length() > relationshipLength) {

                            errHandler.log_app_error("BS_VAL_025", "Family member relationship" + "," + relationshipLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (studentProfile.getFam()[i].getMemberEmailID().length() > emailLength) {

                            errHandler.log_app_error("BS_VAL_025", "Family member email id" + "," + emailLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (studentProfile.getFam()[i].getMemberContactNo().length() > contactNoLength) {

                            errHandler.log_app_error("BS_VAL_025", "Family member contact no" + "," + contactNoLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (studentProfile.getFam()[i].getMemberImgPath().length() > imagePathLength) {

                            errHandler.log_app_error("BS_VAL_025", "Family member image path" + "," + imagePathLength);
                            throw new BSValidationException("BSValidationException");
                        }
                    }
                    break;

                case "TeacherProfile":
                    RequestBody<TeacherProfile> teacherProfileBody = request.getReqBody();
                    TeacherProfile teacherProfile = teacherProfileBody.get();
                    int bloodGroupLength1 = mds.getColumnMetaData("TEACHER", "TVW_TEACHER_PROFILE", "BLOOD_GROUP", session).getI_ColumnLength();
                    int imagePathLength1 = mds.getColumnMetaData("TEACHER", "TVW_TEACHER_PROFILE", "IMAGE_PATH", session).getI_ColumnLength();
                    int occupationLength1 = mds.getColumnMetaData("TEACHER", "TVW_CONTACT_PERSON_DETAILS", "CP_OCCUPATION", session).getI_ColumnLength();
                    int relationshipLength1 = mds.getColumnMetaData("TEACHER", "TVW_CONTACT_PERSON_DETAILS", "CP_RELATIONSHIP", session).getI_ColumnLength();
                    int qualificationLength = mds.getColumnMetaData("TEACHER", "TVW_TEACHER_PROFILE", "QUALIFICATION", session).getI_ColumnLength();

                    if (teacherProfile.getTeacherID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Teacher id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (teacherProfile.getTeacherName().length() > nameLength) {

                        errHandler.log_app_error("BS_VAL_025", "Teacher name" + "," + nameLength);
                        throw new BSValidationException("BSValidationException");

                    }

//                 if(teacherProfile.getGen().getClasss().getStandard().length()>standardLength){
//                     
//                     errHandler.log_app_error("BS_VAL_025", "Standard"+","+standardLength);
//                     throw new BSValidationException("BSValidationException");
//                 }
//                 if(teacherProfile.getGen().getClasss().getSection().length()>sectionLength){
//                     
//                     errHandler.log_app_error("BS_VAL_025", "Section"+","+sectionLength);
//                     throw new BSValidationException("BSValidationException");
//                 }
//                 if(teacherProfile.getGen().getDob().length()>dateLength){
//                     
//                     errHandler.log_app_error("BS_VAL_025", "DOB"+","+dateLength);
//                     throw new BSValidationException("BSValidationException");
//                 }
                    if (teacherProfile.getGen().getDob().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "DOB" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (teacherProfile.getGen().getEmailID().length() > emailLength) {

                        errHandler.log_app_error("BS_VAL_025", "Email id" + "," + emailLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (teacherProfile.getGen().getContactNo().length() > contactNoLength) {

                        errHandler.log_app_error("BS_VAL_025", "Contact no" + "," + contactNoLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (teacherProfile.getGen().getBloodGroup().length() > bloodGroupLength1) {

                        errHandler.log_app_error("BS_VAL_025", "Blood group" + "," + bloodGroupLength1);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (teacherProfile.getGen().getAddress().getAddressLine1().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Address line 1" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (teacherProfile.getGen().getAddress().getAddressLine2().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Address line 2" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (teacherProfile.getGen().getAddress().getAddressLine3().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Address line 3" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (teacherProfile.getGen().getAddress().getAddressLine4().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Address line 4" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }
                    if (teacherProfile.getGen().getAddress().getAddressLine5().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Address line 5" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (teacherProfile.getProfileImgPath().length() > imagePathLength1) {

                        errHandler.log_app_error("BS_VAL_025", "Profile image path" + "," + imagePathLength1);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (teacherProfile.getGen().getQualification().length() > qualificationLength) {

                        errHandler.log_app_error("BS_VAL_025", "Qualification" + "," + qualificationLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (teacherProfile.getEmer().getExistingMedicalDetail().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Existing medical detail" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    for (int i = 0; i < teacherProfile.getEmer().getCp().length; i++) {

                        if (teacherProfile.getEmer().getCp()[i].getCp_ID().length() > idLength) {

                            errHandler.log_app_error("BS_VAL_025", "Contact person id" + "," + idLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (teacherProfile.getEmer().getCp()[i].getCp_name().length() > nameLength) {

                            errHandler.log_app_error("BS_VAL_025", "Contact person name" + "," + nameLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (teacherProfile.getEmer().getCp()[i].getCp_occupation().length() > occupationLength1) {

                            errHandler.log_app_error("BS_VAL_025", "Contact person occupation" + "," + occupationLength1);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (teacherProfile.getEmer().getCp()[i].getCp_relationship().length() > relationshipLength1) {

                            errHandler.log_app_error("BS_VAL_025", "Contact person relationship" + "," + relationshipLength1);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (teacherProfile.getEmer().getCp()[i].getCp_mailID().length() > emailLength) {

                            errHandler.log_app_error("BS_VAL_025", "Contact person email id" + "," + emailLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (teacherProfile.getEmer().getCp()[i].getCp_contactNo().length() > contactNoLength) {

                            errHandler.log_app_error("BS_VAL_025", "Contact person contact no" + "," + contactNoLength);
                            throw new BSValidationException("BSValidationException");
                        }

                        if (teacherProfile.getEmer().getCp()[i].getCp_imgPath().length() > imagePathLength1) {

                            errHandler.log_app_error("BS_VAL_025", "Contact person image path" + "," + imagePathLength1);
                            throw new BSValidationException("BSValidationException");
                        }
                    }

                    break;

                case "UserProfile":
                    RequestBody<UserProfile> userProfileBody = request.getReqBody();
                    UserProfile userProfile = userProfileBody.get();
                    int userStatus = mds.getColumnMetaData("USER", "UVW_USER_PROFILE", "STATUS", session).getI_ColumnLength();
                    int userTypeLength = mds.getColumnMetaData("USER", "UVW_USER_PROFILE", "USER_TYPE", session).getI_ColumnLength();

                    if (userProfile.getUserID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "User id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (userProfile.getUserName().length() > nameLength) {

                        errHandler.log_app_error("BS_VAL_025", "User name" + "," + nameLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (userProfile.getEmailID().length() > emailLength) {

                        errHandler.log_app_error("BS_VAL_025", "Email id" + "," + emailLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (userProfile.getMobileNo().length() > contactNoLength) {

                        errHandler.log_app_error("BS_VAL_025", "Mobile no" + "," + contactNoLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (userProfile.getExpiryDate().length() > dateLength) {

                        errHandler.log_app_error("BS_VAL_025", "Expiry date" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }

//                 if(userProfile.getExpiryDate().length()>dateLength){
//                     
//                     errHandler.log_app_error("BS_VAL_025", "Expiry date"+","+dateLength);
//                     throw new BSValidationException("BSValidationException");
//                 }
                    if (userProfile.getUserType().length() > userTypeLength) {

                        errHandler.log_app_error("BS_VAL_025", "User type" + "," + dateLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (userProfile.getStatus().length() > userStatus) {

                        errHandler.log_app_error("BS_VAL_025", "User status" + "," + userStatus);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (userProfile.getHomeInstitue().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Home institute" + "," + idLength);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (userProfile.getPassword().length() < 8 || userProfile.getPassword().length() > 10) {

                        errHandler.log_app_error("BS_VAL_033", null);
                        throw new BSValidationException("BSValidationException");
                    }

                    if (userProfile.getParentStudentMapping() != null) {

                        for (int i = 0; i < userProfile.getParentStudentMapping().length; i++) {

                            if (userProfile.getParentStudentMapping()[i].getRoleID().length() > idLength) {

                                errHandler.log_app_error("BS_VAL_025", "Role id" + "," + idLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (userProfile.getParentStudentMapping()[i].getStudentID().length() > idLength) {

                                errHandler.log_app_error("BS_VAL_025", "Student id" + "," + idLength);
                                throw new BSValidationException("BSValidationException");
                            }
                        }

                    }

                    if (userProfile.getClassEntityRoleMapping() != null) {

                        for (int i = 0; i < userProfile.getClassEntityRoleMapping().length; i++) {

                            if (userProfile.getClassEntityRoleMapping()[i].getRoleID().length() > idLength) {

                                errHandler.log_app_error("BS_VAL_025", "Role id" + "," + idLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (userProfile.getClassEntityRoleMapping()[i].getStandard().length() > standardLength) {

                                errHandler.log_app_error("BS_VAL_025", "Standard" + "," + standardLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (userProfile.getClassEntityRoleMapping()[i].getSection().length() > sectionLength) {

                                errHandler.log_app_error("BS_VAL_025", "Section" + "," + sectionLength);
                                throw new BSValidationException("BSValidationException");
                            }
                        }
                    }

                    if (userProfile.getTeacherEntityMapping() != null) {

                        for (int i = 0; i < userProfile.getTeacherEntityMapping().length; i++) {

                            if (userProfile.getTeacherEntityMapping()[i].getRoleID().length() > idLength) {

                                errHandler.log_app_error("BS_VAL_025", "Role id" + "," + idLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (userProfile.getTeacherEntityMapping()[i].getTeacherID().length() > idLength) {

                                errHandler.log_app_error("BS_VAL_025", "Teacher id" + "," + idLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (userProfile.getTeacherEntityMapping()[i].getInstituteID().length() > idLength) {

                                errHandler.log_app_error("BS_VAL_025", "Institute id" + "," + idLength);
                                throw new BSValidationException("BSValidationException");
                            }
                        }
                    }

                    if (userProfile.getInstituteEntityMapping() != null) {

                        for (int i = 0; i < userProfile.getInstituteEntityMapping().length; i++) {

                            if (userProfile.getInstituteEntityMapping()[i].getRoleID().length() > idLength) {

                                errHandler.log_app_error("BS_VAL_025", "Role id" + "," + idLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (userProfile.getInstituteEntityMapping()[i].getInstituteID().length() > idLength) {

                                errHandler.log_app_error("BS_VAL_025", "Institute id" + "," + idLength);
                                throw new BSValidationException("BSValidationException");
                            }
                        }
                    }
                    break;
                case "UserRole":
                    RequestBody<UserRole> userRoleBody = request.getReqBody();
                    UserRole userRole = userRoleBody.get();
                    int serviceLength = mds.getColumnMetaData("USER", "UVW_USER_ROLE_DETAIL", "FUNCTION_ID", session).getI_ColumnLength();
                    int operationLength = mds.getColumnMetaData("USER", "UVW_USER_ROLE_DETAIL", "CREATE", session).getI_ColumnLength();

                    if (userRole.getRoleID().length() > idLength) {

                        errHandler.log_app_error("BS_VAL_025", "Role id" + "," + idLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (userRole.getRoleDescription().length() > textFieldLength) {

                        errHandler.log_app_error("BS_VAL_025", "Role description" + "," + textFieldLength);
                        throw new BSValidationException("BSValidationException");

                    }

                    if (!request.getReqHeader().getOperation().equals("View")) {

                        for (int i = 0; i < userRole.getFunctions().length; i++) {

                            if (userRole.getFunctions()[i].getFunctionID().length() > serviceLength) {

                                errHandler.log_app_error("BS_VAL_025", "Function id" + "," + serviceLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (userRole.getFunctions()[i].getCreate().length() > operationLength) {

                                errHandler.log_app_error("BS_VAL_025", "Create" + "," + operationLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (userRole.getFunctions()[i].getModify().length() > operationLength) {

                                errHandler.log_app_error("BS_VAL_025", "Modify" + "," + operationLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (userRole.getFunctions()[i].getAuth().length() > operationLength) {

                                errHandler.log_app_error("BS_VAL_025", "Auth" + "," + operationLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (userRole.getFunctions()[i].getAutoAuth().length() > operationLength) {

                                errHandler.log_app_error("BS_VAL_025", "Auto auth" + "," + operationLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (userRole.getFunctions()[i].getDelete().length() > operationLength) {

                                errHandler.log_app_error("BS_VAL_025", "Delete" + "," + operationLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (userRole.getFunctions()[i].getReject().length() > operationLength) {

                                errHandler.log_app_error("BS_VAL_025", "Reject" + "," + operationLength);
                                throw new BSValidationException("BSValidationException");
                            }

                            if (userRole.getFunctions()[i].getView().length() > operationLength) {

                                errHandler.log_app_error("BS_VAL_025", "View" + "," + operationLength);
                                throw new BSValidationException("BSValidationException");
                            }
                        }

                    }
                    break;
            }

            dbg("end of length validation");
        } catch (DBValidationException ex) {
            throw ex;
        } catch (BSValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
//      }catch(BSProcessingException ex){
//           dbg(ex);
//           throw new BSProcessingException(ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    }

    public void auditColumnLengthValidation(Request request, CohesiveSession session, DBSession dbSession, AppDependencyInjection inject) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {
        boolean status = true;
        try {

            dbg("inside length validation");
            ErrorHandler errHandler = session.getErrorhandler();
            int idColumnLength = 10;
            int dateStampLength = 19;
            int statusLength = 1;
            int remarksLength = 105;
            int versionLength = 2;

            String l_makerID = request.getReqAudit().getMakerID();
            String l_checkerID = request.getReqAudit().getCheckerID();
            String l_makerDateStamp = request.getReqAudit().getMakerDateStamp();
            String l_checkerDateStamp = request.getReqAudit().getCheckerDateStamp();
            String l_recordStatus = request.getReqAudit().getRecordStatus();
            String l_authStatus = request.getReqAudit().getAuthStatus();
            String l_versionNumber = request.getReqAudit().getVersionNumber();
            String l_makerRemarks = request.getReqAudit().getMakerRemarks();
            String l_checkerRemarks = request.getReqAudit().getCheckerRemarks();

            if (l_makerID.length() > idColumnLength) {

                status = false;
                errHandler.log_app_error("BS_VAL_025", "Maker id" + "," + idColumnLength);

            }

            if (l_checkerID.length() > idColumnLength) {

                status = false;
                errHandler.log_app_error("BS_VAL_025", "Checker id" + "," + idColumnLength);
            }

            if (l_makerDateStamp.length() > dateStampLength) {

                status = false;
                errHandler.log_app_error("BS_VAL_025", "Maker date stamp" + "," + dateStampLength);
            }

            if (l_checkerDateStamp.length() > dateStampLength) {

                status = false;
                errHandler.log_app_error("BS_VAL_025", "Checker date stamp" + "," + dateStampLength);
            }

            if (l_recordStatus.length() > statusLength) {

                status = false;
                errHandler.log_app_error("BS_VAL_025", "Record status" + "," + statusLength);
            }

            if (l_authStatus.length() > statusLength) {

                status = false;
                errHandler.log_app_error("BS_VAL_025", "Auth status" + "," + statusLength);
            }
            if (l_versionNumber.length() > versionLength) {

                status = false;
                errHandler.log_app_error("BS_VAL_025", "Version number" + "," + versionLength);
            }
            if (l_makerRemarks.length() > remarksLength) {

                status = false;
                errHandler.log_app_error("BS_VAL_025", "Maker remarks" + "," + remarksLength);
            }
            if (l_checkerRemarks.length() > remarksLength) {

                status = false;
                errHandler.log_app_error("BS_VAL_025", "Checker remarks" + "," + remarksLength);
            }

            if (status == false) {

                throw new BSValidationException("BSValidationException");

            }

            dbg("end of length validation");
//     }catch(DBValidationException ex){
//          throw ex;
        } catch (BSValidationException ex) {
            throw ex;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//      }catch(BSProcessingException ex){
//           dbg(ex);
//           throw new BSProcessingException(ex.toString());
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
