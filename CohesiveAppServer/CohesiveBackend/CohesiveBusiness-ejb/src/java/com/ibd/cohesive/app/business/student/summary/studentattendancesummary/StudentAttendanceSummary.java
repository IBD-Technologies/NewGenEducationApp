/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentattendancesummary;

import com.ibd.businessViews.IStudentAttendanceSummary;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.student.studentattendanceservice.AttendanceDetails;
import com.ibd.cohesive.app.business.student.studentattendanceservice.AuditDetails;
import com.ibd.cohesive.app.business.student.studentattendanceservice.PeriodAttendance;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.ConvertedDate;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
//@Local(IStudentAttendanceSummary.class)
@Remote(IStudentAttendanceSummary.class)
@Stateless
public class StudentAttendanceSummary implements IStudentAttendanceSummary {

    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    ArrayList<String> filterlist_dummy;
    String filterkey_dummy;
    Map<String, String> filtermap_dummy;

    public StudentAttendanceSummary() {
        try {
            inject = new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession = new DBSession(session);
        } catch (NamingException ex) {
            dbg(ex);
            throw new EJBException(ex);
        }

    }

    @Override
    public JsonObject EJBprocessing(JsonObject requestJson) {
        JsonObject response = null;
        try {
            response = processing(requestJson);
        } catch (Exception e) {
            dbg(e);
            throw new EJBException(e);
        }
        return response;
    }

    @Override
    public String EJBprocessing(String request) {
        JsonObject responseJson = null;
        JsonObject requestJson = null;
        String response = null;

        try {
            requestJson = inject.getJsonUtil().getJsonObject(request);
            responseJson = processing(requestJson);
            response = responseJson.toString();

        } catch (Exception e) {
            if (session.getDebug() != null) {
                dbg(e);
            }
            throw new EJBException(e);
        }
        return response;
    }

    public JsonObject processing(JsonObject requestJson) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {
        boolean l_validation_status = true;
        boolean l_session_created_now = false;
        BusinessService bs;
        Parsing parser;
        ExceptionHandler exHandler;
        JsonObject jsonResponse = null;
        JsonObject clonedResponse = null;
        JsonObject clonedJson = null;
        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now = session.isI_session_created_now();
            ErrorHandler errhandler = session.getErrorhandler();
            BSValidation bsv = inject.getBsv(session);
            bs = inject.getBusinessService(session);
            ITransactionControlService tc = inject.getTransactionControlService();
            dbg("inside StudentAttendanceSummary--->processing");
            dbg("StudentAttendanceSummary--->Processing--->I/P--->requestJson" + requestJson.toString());
            clonedJson = bs.cloneRequestJsonObject(requestJson);
            dbg("cloned json" + clonedJson.toString());
            request = new Request();
            parser = inject.getParser(session);
            parser.parseRequest(request, clonedJson);
            bs.requestlogging(request, clonedJson, inject, session, dbSession);
            buildBOfromReq(clonedJson);

            BusinessEJB<IStudentAttendanceSummary> studentAttendanceEJB = new BusinessEJB();
            studentAttendanceEJB.set(this);

            exAudit = bs.getExistingAudit(studentAttendanceEJB);

            if (!(bsv.businessServiceValidation(clonedJson, exAudit, request, errhandler, inject, session, dbSession))) {
                l_validation_status = false;
                throw new BSValidationException("BSValidationException");
            }
            if (!businessValidation(errhandler)) {
                l_validation_status = false;
                throw new BSValidationException("BSValidationException");
            }

            bs.businessServiceProcssing(request, exAudit, inject, studentAttendanceEJB);

            if (l_session_created_now) {
                jsonResponse = bs.buildSuccessResponse(clonedJson, request, inject, session, studentAttendanceEJB);
                tc.commit(session, dbSession);
                dbg("commit is called in student attendance");
            }
        } catch (NamingException ex) {
            dbg(ex);
            exHandler = inject.getErrorHandle(session);
            jsonResponse = exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson, "BSProcessingException");
        } catch (BSValidationException ex) {
            if (l_session_created_now) {
                exHandler = inject.getErrorHandle(session);
                jsonResponse = exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson, "BSValidationException");
            } else {
                throw ex;
            }
        } catch (DBValidationException ex) {
            if (l_session_created_now) {
                exHandler = inject.getErrorHandle(session);
                jsonResponse = exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson, "DBValidationException");
            } else {
                throw ex;
            }
        } catch (DBProcessingException ex) {
            dbg(ex);
            if (l_session_created_now) {
                exHandler = inject.getErrorHandle(session);
                jsonResponse = exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson, "DBProcessingException");
            } else {
                throw ex;
            }
        } catch (BSProcessingException ex) {
            dbg(ex);
            if (l_session_created_now) {
                exHandler = inject.getErrorHandle(session);
                jsonResponse = exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson, "BSProcessingException");
            } else {
                throw ex;
            }
        } finally {
            exAudit = null;
            request = null;
            bs = inject.getBusinessService(session);
            if (l_session_created_now) {
                bs.responselogging(jsonResponse, inject, session, dbSession);
                dbg("Response" + jsonResponse.toString());
                clonedResponse = bs.cloneResponseJsonObject(jsonResponse);
                BSValidation bsv = inject.getBsv(session);
//                if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                clonedResponse = bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes  
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

    public JsonObject processing(JsonObject requestJson, CohesiveSession session) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {
        CohesiveSession tempSession = this.session;
        JsonObject response = null;
        try {
            this.session = session;
            response = processing(requestJson);
        } catch (DBValidationException ex) {

            throw ex;
        } catch (BSValidationException ex) {

            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (BSProcessingException ex) {
            dbg(ex);
            throw new BSProcessingException("BSProcessingException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        } finally {
            this.session = tempSession;
        }
        return response;
    }

    private void buildBOfromReq(JsonObject p_request) throws BSProcessingException, DBProcessingException {
        StudentAttendanceBO studentAttendance = new StudentAttendanceBO();
        RequestBody<StudentAttendanceBO> reqBody = new RequestBody<StudentAttendanceBO>();

        try {
            dbg("inside student attendance buildBOfromReq");
            JsonObject l_body = p_request.getJsonObject("body");
            JsonObject l_filterObject = l_body.getJsonObject("filter");
            studentAttendance.filter = new StudentAttendanceFilter();
            studentAttendance.filter.setStudentID(l_filterObject.getString("studentID"));
            studentAttendance.filter.setStudentName(l_filterObject.getString("studentName"));
//      studentAttendance.filter.setRecordStatus(l_filterObject.getString("recordStat"));
//      studentAttendance.filter.setAuthStatus(l_filterObject.getString("authStat"));
            studentAttendance.filter.setFromDate(l_filterObject.getString("fromDate"));
            studentAttendance.filter.setToDate(l_filterObject.getString("toDate"));

            reqBody.set(studentAttendance);
            request.setReqBody(reqBody);
            dbg("End of build bo from request");

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    }

    public void view() throws DBValidationException, DBProcessingException, BSProcessingException, BSValidationException {

        try {
            dbg("inside student attendance--->view");
            BusinessService bs = inject.getBusinessService(session);
            IPDataService pds = inject.getPdataservice();
            IBDProperties i_db_properties = session.getCohesiveproperties();
            RequestBody<StudentAttendanceBO> reqBody = request.getReqBody();
            String l_instituteID = request.getReqHeader().getInstituteID();
            IDBReadBufferService readBuffer = inject.getDBReadBufferService();
            StudentAttendanceBO studentAttendance = reqBody.get();
            String l_studentID = studentAttendance.getFilter().getStudentID();
            String fromDate = studentAttendance.getFilter().getFromDate();
            String toDate = studentAttendance.getFilter().getToDate();
            ArrayList<Date> dateList = bs.getLeaveDates(fromDate, toDate, session, dbSession, inject);
            String dateformat = i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
//      studentAttendance.result=new StudentAttendanceResult[dateList.size()];
            Map<String, DBRecord> l_attendanceMap = null;
            AttendanceDetails attendanceDetail = new AttendanceDetails();
            String[] pkey = {l_studentID};
            ArrayList<String> studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", pkey);
            String l_standard = studentList.get(2).trim();
            String l_section = studentList.get(3).trim();
            try {

                String[] l_pkey = {l_instituteID, l_standard, l_section};
                ArrayList<String> classConfigList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_STANDARD_MASTER", l_pkey);
                String attendancetype = classConfigList.get(13).trim();
                attendanceDetail.setStandard(l_standard);
                attendanceDetail.setSection(l_section);
                attendanceDetail.setAttendanceType(attendancetype);

                l_attendanceMap = readBuffer.readTable("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "CLASS" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_standard + l_section + i_db_properties.getProperty("FOLDER_DELIMITER") + l_standard + l_section, "CLASS", "CLASS_ATTENDANCE_DETAIL", session, dbSession);
            } catch (DBValidationException ex) {
                dbg("exception in view operation" + ex);
                if (ex.toString().contains("DB_VAL_011") || ex.toString().contains("DB_VAL_000")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                    session.getErrorhandler().log_app_error("BS_VAL_013", null);
                    throw new BSValidationException("BSValidationException");
                } else {

                    throw ex;
                }
            }

            studentAttendance.result = new ArrayList();

            for (int i = 0; i < dateList.size(); i++) {

                try {

                    String l_date = formatter.format(dateList.get(i));
                    ConvertedDate convertedDate = bs.getYearMonthandDay(l_date);
                    String l_year = convertedDate.getYear();
                    String l_month = convertedDate.getMonth();
                    String l_day = convertedDate.getDay();
                    String[] l_masterPkey = {l_standard, l_section, l_year, l_month};
                    DBRecord masterRecord = readBuffer.readRecord("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "CLASS" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_standard + l_section + i_db_properties.getProperty("FOLDER_DELIMITER") + l_standard + l_section, "CLASS", "CLASS_ATTENDANCE_MASTER", l_masterPkey, session, dbSession);
                    AuditDetails auditDetails = null;
                    try {

                        auditDetails = bs.getClassAuditDetails(masterRecord.getRecord().get(4).trim(), l_day, session);

                    } catch (BSValidationException ex) {
                        dbg("record not exist for the day" + ex);
                        if (ex.toString().contains("BS_VAL_013")) {
                            session.getErrorhandler().removeSessionErrCode("BS_VAL_013");
                        } else {

                            throw ex;
                        }
                    }

                    if (auditDetails != null) {

                        if (auditDetails.getAuthStatus().equals("A") && auditDetails.getRecordStatus().equals("O")) {

                            String l_referenceID = attendanceDetail.getStandard() + "*" + attendanceDetail.getSection() + "*" + l_year + "*" + l_month;
                            attendanceDetail.setReferenceID(l_referenceID);

                            buildBOfromDB(l_date, l_attendanceMap, attendanceDetail);

                        }

                    }

                } catch (DBValidationException ex) {
                    if (ex.toString().contains("DB_VAL_011") || ex.toString().contains("DB_VAL_000")) {
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                    } else {

                        throw ex;
                    }
                }

            }

            if (studentAttendance.result.isEmpty()) {

                session.getErrorhandler().log_app_error("BS_VAL_013", null);
                throw new BSValidationException("BSValidationException");
            }

//        ArrayList<String>l_fileNames=bs.getStudentFileNames(l_studentID,request,session,dbSession,inject,l_standard,l_section);
//        dbg("l_fileNames size "+l_fileNames.size());
//        studentAttendance.result=new StudentAttendanceResult[l_fileNames.size()];
//        for(int i=0;i<l_fileNames.size();i++){
//            dbg("inside file name iteration");
//            String studentID=l_fileNames.get(i);
//            String studentName=bs.getStudentName(studentID, l_instituteID, session, dbSession, inject);
//            dbg("studentID"+studentID);
//            
//            if(studentName.equals(studentNameFilter)){
//            
//            String l_date=studentAttendance.getFilter().getDate();
//            dbg("l_date"+l_date);
////            MonthRecords[] monthRecords;
//            ArrayList<MonthRecords> l_monthRecords=new ArrayList();
//            
//               if(l_date!=null&&!l_date.isEmpty()){
//                   
//                     ConvertedDate convertedDate=bs.getYearMonthandDay(l_date);
//                     String l_year=convertedDate.getYear();
//                     String l_month=convertedDate.getMonth();
//                     String[] l_primaryKey={studentID,l_year,l_month};
//                     DBRecord dbmonthRecord=null;
//                     try{
//
//                       dbmonthRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_ATTENDANCE",l_primaryKey, session, dbSession);
//
//                     }catch(DBValidationException ex){
//
//                         if(!(ex.toString().contains("DB_VAL_011"))){
//
//                            throw ex; 
//                         }
//                     }
//                   
//                    if(dbmonthRecord!=null){ 
//                       MonthRecords monthRecord=new MonthRecords();
//                       monthRecord.setYear(l_year);
//                       monthRecord.setMonth(l_month);
//                       monthRecord.setMonthRecord(dbmonthRecord);
//                       l_monthRecords.add(monthRecord);
//                    }   
//                   
//               }else{
//                   
//                   String currentYearMonth=bs.getCurrentYearMonth();
//                   String year=currentYearMonth.split("~")[0];
//                   String month=currentYearMonth.split("~")[1];
//                   String currentDay=currentYearMonth.split("~")[2];
//                   
//                   String[] l_primaryKey={studentID,year,month};
//                   DBRecord dbMonthRecord=null;
//                   
//                   try{
//                   
//                      dbMonthRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_ATTENDANCE",l_primaryKey, session, dbSession);
//                  
//                   }catch(DBValidationException ex){
//                     
//                     if(!(ex.toString().contains("DB_VAL_011"))){
//                         
//                        throw ex; 
//                     }
//                 }
//                   
//                  if(dbMonthRecord!=null){   
//                       MonthRecords monthRecord=new MonthRecords();
//                       monthRecord.setYear(year);
//                       monthRecord.setMonth(month);
//                       monthRecord.setMonthRecord(dbMonthRecord);
//                       l_monthRecords.add(monthRecord);
//                  }
//                   
//                   
//                   if(Integer.parseInt(currentDay)<6){
//                   
//                       String previousYearMonth=bs.getPreviousYearMonth(year, month);
//                       String previousYear=previousYearMonth.split("~")[0];
//                       String previousMonth=previousYearMonth.split("~")[1];
//
//                       String[] l_pkey={studentID,previousYear,previousMonth};
//                       DBRecord previousMonthRecord=null;
//                       
//                       try{
//                        
//                            previousMonthRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_ATTENDANCE",l_pkey, session, dbSession);
//
//                       }catch(DBValidationException ex){
//
//                             if(!(ex.toString().contains("DB_VAL_011"))){
//
//                                throw ex; 
//                             }
//                       }
//                      if(previousMonthRecord!=null){   
//                           MonthRecords monthRecord=new MonthRecords();
//                           monthRecord.setYear(previousYear);
//                           monthRecord.setMonth(previousYear);
//                           monthRecord.setMonthRecord(previousMonthRecord);
//                           l_monthRecords.add(monthRecord);
//                      }
//               
//                   }
//               }
//               
//               dbg("l_monthRecords size"+l_monthRecords.size());
//               for(int k=0;k<l_monthRecords.size();k++){
//            
//                   String l_monthAttendance=l_monthRecords.get(k).getMonthRecord().getRecord().get(3).trim();
//                   dbg("l_monthAttendance"+l_monthAttendance);
//                   String[] l_dayAttendanceArr=l_monthAttendance.split("d");
//                   dbg("l_dayAttendanceArr size"+l_dayAttendanceArr.length);
//                   ArrayList<String>l_dayAttendanceList=new ArrayList();
//                   for(int j=1;j<l_dayAttendanceArr.length;j++){
//                       dbg("l_dayAttendanceArr[j]"+l_dayAttendanceArr[j]);
//                       
//                       if(!l_dayAttendanceArr[j].split(",")[0].contains(" ")){
//                       
//                          l_dayAttendanceList.add(l_dayAttendanceArr[j]);
//                       }
//                       
//                   }
//               
//               buildBOfromDB(l_dayAttendanceList,studentID,i,k,l_monthRecords.get(k).getYear(),l_monthRecords.get(k).getMonth());
//               
//               
//               }
//            }
//        }
//        
//        if(studentAttendance.result==null||studentAttendance.result.length==0){
//                session.getErrorhandler().log_app_error("BS_VAL_013", null);
//               throw new BSValidationException("BSValidationException");
//           }
            dbg("end of  completed student attendance--->view");
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

    private void buildBOfromDB(String date, Map<String, DBRecord> attendanceMap, AttendanceDetails attendanceDetail) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {
        try {
            dbg("inside buildBOfromDB");
            BusinessService bs = inject.getBusinessService(session);
//           IPDataService pds=inject.getPdataservice();
            String l_instituteID = request.getReqHeader().getInstituteID();
//           IBDProperties i_db_properties=session.getCohesiveproperties();
            RequestBody<StudentAttendanceBO> reqBody = request.getReqBody();
            StudentAttendanceBO studentAttendance = reqBody.get();
            String l_studentID = studentAttendance.getFilter().getStudentID();
            ConvertedDate convertedDate = bs.getYearMonthandDay(date);
            String l_day = convertedDate.getDay();
            String attendancetype = attendanceDetail.getAttendanceType();
//           String[] pkey={l_studentID};
//           ArrayList<String>studentList=    pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",pkey);
//           String l_standard=studentList.get(2).trim();
//           String l_section=studentList.get(3).trim();

            Map<String, Map<String, String[]>> l_parsedMap = null;

            try {

                l_parsedMap = parseAttendanceFromBO(attendanceMap, l_day, attendanceDetail);

            } catch (BSValidationException ex) {
                dbg("record not exist for the day" + ex);
                if (ex.toString().contains("BS_VAL_013")) {
                    session.getErrorhandler().removeSessionErrCode("BS_VAL_013");
                    return;
                } else {

                    throw ex;
                }
            }

            studentAttendance.filter.setStudentName(bs.getStudentName(l_studentID, l_instituteID, session, dbSession, inject));

            Map<String, String[]> l_foreNoonMap = l_parsedMap.get("ForeNoon");
            dbg("l_foreNoonMap size" + l_foreNoonMap.size());
            Map<String, String[]> l_afterNoonMap = l_parsedMap.get("AfterNoon");
            dbg("l_afterNoonMap size" + l_afterNoonMap.size());

            String[] foreNoonArray = l_foreNoonMap.get(l_studentID);
            dbg("foreNoonArray length" + foreNoonArray.length);
            String[] afterNoonArray = l_afterNoonMap.get(l_studentID);
//           dbg("afterNoonArray length"+afterNoonArray.length);    
            boolean absent = false;
            boolean leave = false;
            boolean present = false;

            for (int j = 1; j < foreNoonArray.length; j++) {
                String attendance = null;
                if (attendancetype.equals("P")) {

                    attendance = foreNoonArray[j].substring(1);

                } else {

                    attendance = foreNoonArray[j].substring(0, 1);
                }

                if (attendance.equals("A")) {

                    absent = true;
                } else if (attendance.equals("L")) {

                    leave = true;
                } else {
                    present = true;
                }

            }

            if (afterNoonArray != null) {

                for (int j = 1; j < afterNoonArray.length; j++) {
                    String attendance = null;
                    if (attendancetype.equals("P")) {

                        attendance = afterNoonArray[j].substring(1);

                    } else {

                        attendance = afterNoonArray[j].substring(0, 1);
                    }

                    if (attendance.equals("A")) {

                        absent = true;
                    } else if (attendance.equals("L")) {

                        leave = true;
                    } else {
                        present = true;
                    }

                }
            }

            String dayAttendance = null;
            if (absent) {

                dayAttendance = "A";
            } else if (leave) {

                dayAttendance = "L";
            } else {

                dayAttendance = "P";
            }

            StudentAttendanceResult result = new StudentAttendanceResult();
            result.setStudentID(l_studentID);
            result.setDate(date);
            result.setAttendance(dayAttendance);
            studentAttendance.result.add(result);
//              studentAttendance.result[index].setStudentID(l_studentID);
//              studentAttendance.result[index].setDate(date);
//              studentAttendance.result[index].setAttendance(dayAttendance);

            dbg("end of  buildBOfromDB");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (DBValidationException ex) {
            dbg(ex);
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    }

    private Map<String, Map<String, String[]>> parseAttendanceFromBO(Map<String, DBRecord> l_attendanceMap, String l_day, AttendanceDetails attendanceDetail) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {

        try {
            Map<String, Map<String, String[]>> parsedMap = new HashMap();
            Map<String, List<DBRecord>> studentWiseGroup = l_attendanceMap.values().stream().filter(rec -> rec.getRecord().get(0).trim().equals(attendanceDetail.getReferenceID())).collect(Collectors.groupingBy(rec -> rec.getRecord().get(1).trim()));
            dbg("studentWiseGroup size" + studentWiseGroup.size());

            if (studentWiseGroup.isEmpty()) {

                throw new BSValidationException("BS_VAL_013");
            }

            Iterator<String> studentIterator = studentWiseGroup.keySet().iterator();
            Map<String, String[]> studentForeNoonMap = new HashMap();
            Map<String, String[]> studentAfterNoonMap = new HashMap();
//           IPDataService pds=inject.getPdataservice();
//           IBDProperties i_db_properties=session.getCohesiveproperties();
//           String l_instituteID=request.getReqHeader().getInstituteID();
//           RequestBody<StudentAttendanceBO> reqBody = request.getReqBody();
//           StudentAttendanceBO studentAttendance =reqBody.get();
//           String studentID=studentAttendance.getFilter().getStudentID();
//           String[] pkey={studentID};
//           ArrayList<String>studentList=    pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",pkey);
//           String l_standard=studentList.get(2).trim();
//           String l_section=studentList.get(3).trim();
//           String[] l_pkey={l_instituteID,l_standard,l_section};
//           ArrayList<String>classConfigList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
            String attendancetype = attendanceDetail.getAttendanceType();
            while (studentIterator.hasNext()) {

                String l_studentID = studentIterator.next();
                dbg("l_studentID" + l_studentID);
                ArrayList<String> l_studentAttendanceList = studentWiseGroup.get(l_studentID).get(0).getRecord();
                String monthAttendance = l_studentAttendanceList.get(2).trim();
                dbg("monthAttendance" + monthAttendance);

                getMaxVersionAttendanceOftheDay(monthAttendance, l_day);

                String max_version_Attendance = filtermap_dummy.get(l_day);
                dbg("max_version_Attendance" + max_version_Attendance);
                String[] dayArray = max_version_Attendance.split(",");
                String attendanceRecord = dayArray[0];
                dbg("attendanceRecord" + attendanceRecord);
                String l_foreNoonAttendance = attendanceRecord.split("n")[0];
                dbg("l_foreNoonAttendance" + l_foreNoonAttendance);

                String[] foreNoonArray = l_foreNoonAttendance.split("p");
                studentForeNoonMap.put(l_studentID, foreNoonArray);

                if (!attendancetype.equals("D")) {

                    String l_afterNoonAttendance = attendanceRecord.split("n")[1];
                    dbg("l_afterNoonAttendance" + l_afterNoonAttendance);
                    String[] afterNoonArray = l_afterNoonAttendance.split("p");
                    studentAfterNoonMap.put(l_studentID, afterNoonArray);

                }

                dbg("foreNoonArray size" + foreNoonArray.length);
                dbg("period Iteration starts");

            }
            Iterator<String> foreNoonIterator = studentForeNoonMap.keySet().iterator();

            while (foreNoonIterator.hasNext()) {

                String l_studentID = foreNoonIterator.next();
                dbg("fore noon l_studentID" + l_studentID);
                String[] periodArray = studentForeNoonMap.get(l_studentID);

                for (int i = 0; i < periodArray.length; i++) {

                    String periodNo_attendance = periodArray[i];
                    dbg("foreNoon periodNo_attendance" + periodNo_attendance);
                }

            }

            if (!attendancetype.equals("D")) {

                Iterator<String> afterNoonIterator = studentAfterNoonMap.keySet().iterator();

                while (afterNoonIterator.hasNext()) {

                    String l_studentID = afterNoonIterator.next();
                    dbg("after noon l_studentID" + l_studentID);
                    String[] periodArray = studentAfterNoonMap.get(l_studentID);

                    for (int i = 0; i < periodArray.length; i++) {

                        String periodNo_attendance = periodArray[i];
                        dbg("after noon periodNo_attendance" + periodNo_attendance);
                    }

                }

            }

            parsedMap.put("ForeNoon", studentForeNoonMap);
            parsedMap.put("AfterNoon", studentAfterNoonMap);

            return parsedMap;
        } catch (BSValidationException ex) {
            throw ex;
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    private Map<String, String> getMaxVersionAttendanceOftheDay(String p_monthAttendance, String p_day) throws BSProcessingException, BSValidationException {

        try {
            dbg("inside getMaxVersionAttendanceOftheDay");
            dbg("p_monthAttendance" + p_monthAttendance);
            dbg("p_day" + p_day);
            String[] attendanceArray = p_monthAttendance.split("d");
            dbg("attendanceArray" + attendanceArray.length);
            ArrayList<String> recordsFor_a_day = new ArrayList();
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

                    if (dayRecord.contains(",") || dayRecord.contains("p")) {

                        recordsFor_a_day.add(dayRecord);

                    }
                }

            }
            dbg("recordsFor_a_day size" + recordsFor_a_day.size());

            if (recordsFor_a_day.isEmpty()) {

//                session.getErrorhandler().log_app_error("BS_VAL_013", null);
                throw new BSValidationException("BS_VAL_013");
            }
            filtermap_dummy = new HashMap();
            if (recordsFor_a_day.size() > 1) {
                filterkey_dummy = p_day;
                filtermap_dummy = new HashMap();
                int max_vesion = recordsFor_a_day.stream().mapToInt(rec -> Integer.parseInt(rec.split(",")[1])).max().getAsInt();
                recordsFor_a_day.stream().filter(rec -> Integer.parseInt(rec.split(",")[1]) == max_vesion).forEach(rec -> filtermap_dummy.put(filterkey_dummy, rec));
                dbg("max_vesion" + max_vesion);
            } else {

                filtermap_dummy.put(p_day, recordsFor_a_day.get(0).trim());
            }

            dbg("end of  getMaxVersionAttendanceOftheDay");
            return filtermap_dummy;

        } catch (BSValidationException ex) {

            throw ex;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

//  private ArrayList<String> dayFilter(ArrayList<String>monthAttendance,String p_day)throws BSProcessingException{
//        
//        try{
//            dbg("inside getMaxVersionAttendanceOftheDay");
//            dbg("p_day"+p_day);
//            ArrayList<String>recordsFor_a_day=new ArrayList();
//            for(int i=0;i<monthAttendance.size();i++){
//                String dayRecord=monthAttendance.get(i);
//                dbg("dayRecord"+dayRecord);
//                String l_day=null;
//                
//                String dayAttendance=dayRecord.split(",")[0];
//         
//                if(dayAttendance.contains(" ")){
//                    
//                    l_day=dayAttendance.split(" ")[0];
//                }else if(dayAttendance.contains("p")){
//                    
//                    l_day=dayAttendance.split("p")[0];
//                }
//                
//                dbg("l_day"+l_day);
//                if(l_day.equals(p_day)){
//                    
//                    dbg("l_day.equals(p_day)");
//                    recordsFor_a_day.add(dayRecord);
//                }
//                
//            }
//            
////            filterlist_dummy=new ArrayList();
////            if(recordsFor_a_day.size()>1){
////                filterkey_dummy=p_day;
////
////                int max_vesion=recordsFor_a_day.stream().mapToInt(rec->Integer.parseInt(rec.split(",")[7])).max().getAsInt();
////                recordsFor_a_day.stream().filter(rec->Integer.parseInt(rec.split(",")[7])==max_vesion).forEach(rec->filterlist_dummy.add(rec));           
////                dbg("max_vesion"+max_vesion);
////            }else{
////                
////                filterlist_dummy.add(recordsFor_a_day.get(0).trim());
////            }
//            
//            
//            
//            
//            dbg("recordsFor_a_day size"+recordsFor_a_day.size());
//           
//            return recordsFor_a_day;
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//
//        }
//        
//    }  
//    
//  private ArrayList<String> getMaxVersionRecord(ArrayList<String>monthAttendance)throws BSProcessingException{
//      
//      try{
//          dbg("get max version record");
//          Map<String,ArrayList<String>>l_attenceForEachDay=new HashMap();
//          for(int i=0;i<monthAttendance.size();i++){
//              
//              String dayRecord=monthAttendance.get(i);
//                dbg("dayRecord"+dayRecord);
//                String l_day=null;
//                
//                String dayAttendance=dayRecord.split(",")[0];
//         
//                if(dayAttendance.contains(" ")){
//                    
//                    l_day=dayAttendance.split(" ")[0];
//                }else if(dayAttendance.contains("p")){
//                    
//                    l_day=dayAttendance.split("p")[0];
//                }
//                dbg("l_day");
//                if(l_attenceForEachDay.containsKey(l_day)){
//                 
//                   dbg("l_attenceForEachDay contains l_day");
//                   l_attenceForEachDay.get(l_day).add(dayRecord);
//              
//                }else{
//                    dbg("l_attenceForEachDay not contains l_day");
//                    l_attenceForEachDay.put(l_day, new ArrayList());
//                    l_attenceForEachDay.get(l_day).add(dayRecord);
//                }
//              
//          }
//          dbg("l_attenceForEachDay size"+l_attenceForEachDay.size());
//          Iterator<String>dayIterator=l_attenceForEachDay.keySet().iterator();
//          
//          while(dayIterator.hasNext()){
//             
//            String day= dayIterator.next();
//            dbg("day"+day);
//            ArrayList<String>dayAttendanceList=l_attenceForEachDay.get(day);
//            dbg(" dayAttendanceList size"+dayAttendanceList.size());
//            
//            filterlist_dummy=new ArrayList();
//            if(dayAttendanceList.size()>1){
//                filterkey_dummy=day;
//                
//                int max_vesion=dayAttendanceList.stream().mapToInt(rec->Integer.parseInt(rec.split(",")[7])).max().getAsInt();
//                dayAttendanceList.stream().filter(rec->Integer.parseInt(rec.split(",")[7])==max_vesion).forEach(rec->filterlist_dummy.add(rec));           
//                dbg("max_vesion"+max_vesion);
//            }else{
//                
//                filterlist_dummy.add(dayAttendanceList.get(0).trim());
//            }
//              
//          }
//          
//          dbg("filterlist_dummy size"+filterlist_dummy.size());
//          
//          return filterlist_dummy;
//      }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//
//      }
//      
//      
//      
//  }
//  
//  
//    
//    
//    private AuditDetails getAuditDetailsFromDayAttendance(String dayAttendance)throws BSProcessingException{
//    
//    try{
//      dbg("inside getAuditDetailsFromDayAttendance");
//      String[] attArr=  dayAttendance.split(",");
//      AuditDetails audit =new AuditDetails();
//      audit.setMakerID(attArr[1]);
//      audit.setCheckerID(attArr[2]);
//      audit.setMakerDateStamp(attArr[3]);
//      audit.setCheckerDateStamp(attArr[4]);
//      audit.setRecordStatus(attArr[5]);
//      audit.setAuthStatus(attArr[6]);
//      audit.setVersionNo(attArr[7]);
//      audit.setMakerRemarks(attArr[8]);
//      audit.setCheckerRemarks(attArr[9]);
//        
//      dbg("makerID"+audit.getMakerID());
//      dbg("checkerID"+audit.getCheckerID());
//      dbg("makerDateStamp"+audit.getMakerDateStamp());
//      dbg("checkerDateStamp"+audit.getCheckerDateStamp());
//      dbg("RecordStatus"+audit.getRecordStatus());
//      dbg("AuthStatus"+audit.getAuthStatus());
//      dbg("versionNumber"+audit.getVersionNo());
//      dbg("makerRemarks"+audit.getMakerRemarks());
//      dbg("checkerRemarks"+audit.getCheckerRemarks());
//      
//      dbg("end of getAuditDetailsFromDayAttendance");
//      return audit;
//    }catch(Exception ex){
//           dbg(ex);
//           throw new BSProcessingException("BSProcessingException"+ex.toString());
//    }
//    
//    
//}
    public JsonObject buildJsonResFromBO() throws BSProcessingException {
        JsonObject body;
        JsonObject filter;
        try {
            dbg("inside student attendance buildJsonResFromBO");
            RequestBody<StudentAttendanceBO> reqBody = request.getReqBody();
            StudentAttendanceBO studentAttendance = reqBody.get();
            JsonArrayBuilder resultArray = Json.createArrayBuilder();

            for (int i = 0; i < studentAttendance.result.size(); i++) {

                resultArray.add(Json.createObjectBuilder().add("studentID", studentAttendance.result.get(i).getStudentID())
                        .add("date", studentAttendance.result.get(i).getDate())
                        .add("attendance", studentAttendance.result.get(i).getAttendance()));
            }

            filter = Json.createObjectBuilder().add("studentID", studentAttendance.filter.getStudentID())
                    .add("studentName", studentAttendance.filter.getStudentName())
                    .add("fromDate", studentAttendance.filter.getFromDate())
                    .add("toDate", studentAttendance.filter.getToDate())
                    .build();

            body = Json.createObjectBuilder().add("filter", filter)
                    .add("SummaryResult", resultArray)
                    .build();

            dbg(body.toString());
            dbg("end of student attendance buildJsonResFromBO");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        return body;
    }

    private boolean businessValidation(ErrorHandler errhandler) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {
        boolean status = true;

        try {
            dbg("inside student attendance--->businessValidation");
            if (!filterMandatoryValidation(errhandler)) {
                status = false;
            } else {
                if (!filterDataValidation(errhandler)) {
                    status = false;
                }
            }

            dbg("end of student attendance--->businessValidation");
        } catch (BSProcessingException ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        } catch (DBValidationException ex) {
            throw ex;

        } catch (BSValidationException ex) {
            throw ex;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
        return status;
    }

    private boolean filterMandatoryValidation(ErrorHandler errhandler) throws BSProcessingException, BSValidationException {
        boolean status = true;
        try {
            dbg("inside student attendance master mandatory validation");
            RequestBody<StudentAttendanceBO> reqBody = request.getReqBody();
            StudentAttendanceBO studentAttendance = reqBody.get();
            boolean fromDateEmpty = false;
            boolean toDateEmpty = false;
            BusinessService bs = inject.getBusinessService(session);
            String studentID = studentAttendance.getFilter().getStudentID();
            String studentName = studentAttendance.getFilter().getStudentName();
            String instituteID = request.getReqHeader().getInstituteID();
            studentID = bs.studentValidation(studentID, studentName, instituteID, session, dbSession, inject);

            studentAttendance.getFilter().setStudentID(studentID);

            if (studentAttendance.getFilter().getStudentID() == null || studentAttendance.getFilter().getStudentID().isEmpty()) {
                status = false;
                errhandler.log_app_error("BS_VAL_002", "studentID");
            }

            if (studentAttendance.getFilter().getFromDate() == null || studentAttendance.getFilter().getFromDate().isEmpty()) {
                fromDateEmpty = true;
            }

            if (studentAttendance.getFilter().getToDate() == null || studentAttendance.getFilter().getToDate().isEmpty()) {
                toDateEmpty = true;
            }

            if (fromDateEmpty == true) {

                status = false;
                errhandler.log_app_error("BS_VAL_002", "fromDate");
            }

            if (toDateEmpty == true) {

                status = false;
                errhandler.log_app_error("BS_VAL_002", "toDate");
            }

            dbg("end of student attendance master mandatory validation");
        } catch (BSValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

        return status;

    }

    private boolean filterDataValidation(ErrorHandler errhandler) throws BSProcessingException, DBProcessingException, DBValidationException, BSValidationException {
        boolean status = true;

        try {
            dbg("inside student attendance detailDataValidation");
            BSValidation bsv = inject.getBsv(session);
            BusinessService bs = inject.getBusinessService(session);
            RequestBody<StudentAttendanceBO> reqBody = request.getReqBody();
            StudentAttendanceBO studentAttendance = reqBody.get();
            String l_studentID = studentAttendance.getFilter().getStudentID();
            String l_instituteID = request.getReqHeader().getInstituteID();
//             String l_authStatus=studentAttendance.getFilter().getAuthStatus();
            String l_fromDate = studentAttendance.getFilter().getFromDate();
            String l_toDate = studentAttendance.getFilter().getToDate();

            if (l_studentID != null && !l_studentID.isEmpty()) {

                if (!bsv.studentIDValidation(l_studentID, l_instituteID, session, dbSession, inject)) {
                    status = false;
                    errhandler.log_app_error("BS_VAL_003", "studentID");
                }

            }

            if (l_fromDate != null && !l_fromDate.isEmpty() && l_toDate != null && !l_toDate.isEmpty()) {

                if (!bsv.dateFormatValidation(l_fromDate, session, dbSession, inject)) {
                    status = false;
                    errhandler.log_app_error("BS_VAL_003", "from Date");
                    throw new BSValidationException("BSValidationException");
                }

                if (!bsv.dateFormatValidation(l_toDate, session, dbSession, inject)) {
                    status = false;
                    errhandler.log_app_error("BS_VAL_003", "to Date");
                    throw new BSValidationException("BSValidationException");
                }
                if (!bsv.futureDateValidation(l_fromDate, session, dbSession, inject)) {
                    status = false;
                    errhandler.log_app_error("BS_VAL_048", null);
                    throw new BSValidationException("BSValidationException");
                }

                if (!bsv.futureDateValidation(l_toDate, session, dbSession, inject)) {
                    status = false;
                    errhandler.log_app_error("BS_VAL_048", null);
                    throw new BSValidationException("BSValidationException");
                }

                ConvertedDate fromDate = bs.getYearMonthandDay(l_fromDate);
                Calendar start = Calendar.getInstance();

                start.set(Calendar.YEAR, Integer.parseInt(fromDate.getYear()));
                start.set(Calendar.MONTH, Integer.parseInt(fromDate.getMonth()));
                start.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fromDate.getDay()));

                ConvertedDate toDate = bs.getYearMonthandDay(l_toDate);
                Calendar end = Calendar.getInstance();

                end.set(Calendar.YEAR, Integer.parseInt(toDate.getYear()));
                end.set(Calendar.MONTH, Integer.parseInt(toDate.getMonth()));
                end.set(Calendar.DAY_OF_MONTH, Integer.parseInt(toDate.getDay()));

                if (start.after(end)) {

                    status = false;
                    errhandler.log_app_error("BS_VAL_003", "Date range");
                    throw new BSValidationException("BSValidationException");
                }

                int dateSize = bs.getLeaveDates(l_fromDate, l_toDate, session, dbSession, inject).size();

                if (dateSize > 7) {

                    status = false;
                    errhandler.log_app_error("BS_VAL_049", "Date range");
                    throw new BSValidationException("BSValidationException");
                }
            }

            dbg("end of student attendance detailDataValidation");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (DBValidationException ex) {
            throw ex;
        } catch (BSValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

        return status;

    }

    public ExistingAudit getExistingAudit() throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {
        return null;
    }

    public void relationshipProcessing() throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {
    }

    public void create() throws BSProcessingException, DBValidationException, DBProcessingException {
    }

    public void authUpdate() throws DBValidationException, DBProcessingException, BSProcessingException {
    }

    public void fullUpdate() throws BSProcessingException, DBValidationException, DBProcessingException {
    }

    public void delete() throws DBValidationException, DBProcessingException, BSProcessingException {
    }

    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
}
