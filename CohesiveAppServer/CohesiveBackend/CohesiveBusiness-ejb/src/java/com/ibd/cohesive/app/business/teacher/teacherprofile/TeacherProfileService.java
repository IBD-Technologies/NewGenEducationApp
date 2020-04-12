/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.teacherprofile;

import com.ibd.businessViews.ITeacherMasterService;
import com.ibd.businessViews.ITeacherProfileService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.util.BusinessEJB;
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
import com.ibd.cohesive.db.transaction.IDBTransactionService;
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
import java.util.Date;
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
//@Local(ITeacherProfileService.class)
@Remote(ITeacherProfileService.class)
@Stateless
public class TeacherProfileService implements ITeacherProfileService {

    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;

    public TeacherProfileService() {
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
        com.ibd.cohesive.app.business.util.BusinessService bs;
        Parsing parser;
        ExceptionHandler exHandler;
        JsonObject jsonResponse = null;
        JsonObject clonedResponse = null;
        JsonObject clonedJson = null;
        String l_lockKey = null;
        IBusinessLockService businessLock = null;
        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now = session.isI_session_created_now();
            ErrorHandler errhandler = session.getErrorhandler();
            BSValidation bsv = inject.getBsv(session);
            bs = inject.getBusinessService(session);
            ITransactionControlService tc = inject.getTransactionControlService();
            businessLock = inject.getBusinessLockService();
            dbg("inside TeacherProfileService--->processing");
            dbg("TeacherProfileService--->Processing--->I/P--->requestJson" + requestJson.toString());
            clonedJson = bs.cloneRequestJsonObject(requestJson);
            dbg("cloned json" + clonedJson.toString());
            request = new Request();
            parser = inject.getParser(session);
            parser.parseRequest(request, clonedJson);
            bs.requestlogging(request, clonedJson, inject, session, dbSession);
            buildBOfromReq(clonedJson);
            RequestBody<TeacherProfile> reqBody = request.getReqBody();
            TeacherProfile teacherProfile = reqBody.get();
            l_lockKey = teacherProfile.getTeacherID();
            if (!businessLock.getBusinessLock(request, l_lockKey, session)) {
                l_validation_status = false;
                throw new BSValidationException("BSValidationException");
            }
            BusinessEJB<ITeacherProfileService> teacherProfileEJB = new BusinessEJB();
            teacherProfileEJB.set(this);

            exAudit = bs.getExistingAudit(teacherProfileEJB);

            if (!(bsv.businessServiceValidation(clonedJson, exAudit, request, errhandler, inject, session, dbSession))) {
                l_validation_status = false;
                throw new BSValidationException("BSValidationException");
            }
            if (!businessValidation(errhandler)) {
                l_validation_status = false;
                throw new BSValidationException("BSValidationException");
            }

            bs.businessServiceProcssing(request, exAudit, inject, teacherProfileEJB);

            if (l_session_created_now) {
                jsonResponse = bs.buildSuccessResponse(clonedJson, request, inject, session, teacherProfileEJB);
                tc.commit(session, dbSession);
                dbg("commit is called in teacher profile");
            }
        } catch (NamingException ex) {
            dbg(ex);
            exHandler = inject.getErrorHandle(session);
            jsonResponse = exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson, "BSProcessingException");
        } catch (BSValidationException ex) {
            exHandler = inject.getErrorHandle(session);
            jsonResponse = exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson, "BSValidationException");
        } catch (DBValidationException ex) {
            exHandler = inject.getErrorHandle(session);
            jsonResponse = exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson, "DBValidationException");
        } catch (DBProcessingException ex) {
            dbg(ex);
            exHandler = inject.getErrorHandle(session);
            jsonResponse = exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson, "DBProcessingException");
        } catch (BSProcessingException ex) {
            dbg(ex);
            exHandler = inject.getErrorHandle(session);
            jsonResponse = exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson, "BSProcessingException");
        } finally {
            exAudit = null;
            bs = inject.getBusinessService(session);
            dbg("before calling remove businessLock");

            if (l_lockKey != null) {
                businessLock.removeBusinessLock(request, l_lockKey, session);
            }
            request = null;
            if (l_session_created_now) {
                bs.responselogging(jsonResponse, inject, session, dbSession);
                dbg("Response" + jsonResponse.toString());
                clonedResponse = bs.cloneResponseJsonObject(jsonResponse);
                BSValidation bsv = inject.getBsv(session);
                /*if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                   BSProcessingException ex=new BSProcessingException("response contains special characters");
                   dbg(ex);
                   session.clearSessionObject();
                   dbSession.clearSessionObject();
                   throw ex;
                }*/
                clonedResponse = bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes   
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
        TeacherProfile teacherProfile = new TeacherProfile();
        RequestBody<TeacherProfile> reqBody = new RequestBody<TeacherProfile>();

        try {
            dbg("inside teacher profile--->buildBOfromReq");
            JsonObject l_body = p_request.getJsonObject("body");
            String l_operation = request.getReqHeader().getOperation();
            teacherProfile.setTeacherID(l_body.getString("teacherID"));

            if (!l_operation.equals("View")) {
                teacherProfile.setTeacherName(l_body.getString("teacherName"));
                teacherProfile.setProfileImgPath(l_body.getString("profileImgPath"));
                JsonObject l_general = l_body.getJsonObject("general");
                teacherProfile.gen = new General();
                teacherProfile.gen.setQualification(l_general.getString("qualification"));
                dbg(l_general.getString("dob"));
                teacherProfile.gen.setDob(l_general.getString("dob"));
                teacherProfile.gen.setContactNo(l_general.getString("contactNo"));
                teacherProfile.gen.setEmailID(l_general.getString("emailID"));
                teacherProfile.gen.setShortName(l_general.getString("shortName"));
                teacherProfile.gen.setBloodGroup(l_general.getString("bloodGroup"));
                teacherProfile.gen.setGender(l_general.getString("gender"));
                JsonObject l_address = l_general.getJsonObject("address");
                teacherProfile.gen.address = new Address();
                teacherProfile.gen.address.setAddressLine1(l_address.getString("addressLine1"));
                teacherProfile.gen.address.setAddressLine2(l_address.getString("addressLine2"));
                teacherProfile.gen.address.setAddressLine3(l_address.getString("addressLine3"));
                teacherProfile.gen.address.setAddressLine4(l_address.getString("addressLine4"));
//       teacherProfile.gen.address.setAddressLine5(l_address.getString("addressLine5"));
                teacherProfile.gen.address.setAddressLine5(" ");
                JsonObject l_emergency = l_body.getJsonObject("emergency");
                teacherProfile.emer = new Emergency();

                if (l_emergency.containsKey("existingMedicalDetails")) {

                    teacherProfile.emer.setExistingMedicalDetail(l_emergency.getString("existingMedicalDetails"));
                }

                JsonArray l_cpArray = l_emergency.getJsonArray("contactPerson");
                teacherProfile.emer.cp = new ContactPerson[l_cpArray.size()];
                int j = 0;
                for (int i = 0; i < l_cpArray.size(); i++) {
                    teacherProfile.emer.cp[i] = new ContactPerson();
                    JsonObject l_cpObject = l_cpArray.getJsonObject(i);
//           if(l_operation.equals("Create")){ 
                    j = j + 1;
                    teacherProfile.emer.cp[i].setCp_ID(Integer.toString(j));
//           }else{
//               teacherProfile.emer.cp[i].setCp_ID(l_cpObject.getString("cp_ID"));
//           }
                    teacherProfile.emer.cp[i].setCp_name(l_cpObject.getString("cp_Name"));
                    teacherProfile.emer.cp[i].setCp_relationship(l_cpObject.getString("cp_relationship"));
                    teacherProfile.emer.cp[i].setCp_occupation(l_cpObject.getString("cp_occupation"));
                    teacherProfile.emer.cp[i].setCp_mailID(l_cpObject.getString("cp_emailID"));
                    teacherProfile.emer.cp[i].setCp_contactNo(l_cpObject.getString("cp_contactNo"));
                    teacherProfile.emer.cp[i].setCp_imgPath(l_cpObject.getString("cp_imgPath"));
                }
            }
            reqBody.set(teacherProfile);
            request.setReqBody(reqBody);
            dbg("End of teacher profile--->build bo from request");

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }

    }

    public void create() throws BSProcessingException, DBValidationException, DBProcessingException {
        try {
            dbg("inside teacher profile create");
            RequestBody<TeacherProfile> reqBody = request.getReqBody();
            TeacherProfile teacherProfile = reqBody.get();
            IDBTransactionService dbts = inject.getDBTransactionService();
            IBDProperties i_db_properties = session.getCohesiveproperties();
            String l_instituteID = request.getReqHeader().getInstituteID();
            String l_makerID = request.getReqAudit().getMakerID();
            String l_checkerID = request.getReqAudit().getCheckerID();
            String l_makerDateStamp = request.getReqAudit().getMakerDateStamp();
            String l_checkerDateStamp = request.getReqAudit().getCheckerDateStamp();
            String l_recordStatus = request.getReqAudit().getRecordStatus();
            String l_authStatus = request.getReqAudit().getAuthStatus();
            String l_versionNumber = request.getReqAudit().getVersionNumber();
            String l_makerRemarks = request.getReqAudit().getMakerRemarks();
            String l_checkerRemarks = request.getReqAudit().getCheckerRemarks();
            String l_teacherID = teacherProfile.getTeacherID();
            String l_teacherName = teacherProfile.getTeacherName();
            String l_qualifiction = teacherProfile.gen.getQualification();
//        String l_standard=teacherProfile.gen.classs.getStandard();
//        String l_section=teacherProfile.gen.classs.getSection();
            String l_dob = teacherProfile.gen.getDob();
            String l_contactNo = teacherProfile.gen.getContactNo();
            String l_emailID = teacherProfile.gen.getEmailID();
            String l_shortName = teacherProfile.gen.getShortName();
            String l_bloodGroup = teacherProfile.gen.getBloodGroup();
            String l_gender = teacherProfile.gen.getGender();
            String l_addressLine1 = teacherProfile.gen.address.getAddressLine1();
            String l_addressLine2 = teacherProfile.gen.address.getAddressLine2();
            String l_addressLine3 = teacherProfile.gen.address.getAddressLine3();
            String l_addressLine4 = teacherProfile.gen.address.getAddressLine4();
            String l_addressLine5 = teacherProfile.gen.address.getAddressLine5();
            String l_profileImgPath = teacherProfile.getProfileImgPath();
            String l_existingMedicalDetail = teacherProfile.emer.getExistingMedicalDetail();

            dbts.createRecord(session, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "TEACHER" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID, "TEACHER", 8, l_teacherID, l_teacherName, l_qualifiction, l_dob, l_contactNo, l_emailID, l_bloodGroup, l_addressLine1, l_addressLine2, l_addressLine3, l_addressLine4, l_addressLine5, l_profileImgPath, l_makerID, l_checkerID, l_makerDateStamp, l_checkerDateStamp, l_recordStatus, l_authStatus, l_versionNumber, l_makerRemarks, l_checkerRemarks, l_shortName, l_existingMedicalDetail, l_gender);

            dbts.createRecord(session, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", 21, l_teacherID, l_teacherName, l_shortName, l_makerID, l_checkerID, l_makerDateStamp, l_checkerDateStamp, l_recordStatus, l_authStatus, l_versionNumber, l_makerRemarks, l_checkerRemarks);

            for (int j = 0; j < teacherProfile.emer.cp.length; j++) {
                String l_cpID = teacherProfile.emer.cp[j].getCp_ID();
                String l_cpName = teacherProfile.emer.cp[j].getCp_name();
                String l_cpRelationship = teacherProfile.emer.cp[j].getCp_relationship();
                String l_cpOccupation = teacherProfile.emer.cp[j].getCp_occupation();
                String l_cpEmailID = teacherProfile.emer.cp[j].getCp_mailID();
                String l_cpcontactNo = teacherProfile.emer.cp[j].getCp_contactNo();
                String l_cpImgPath = teacherProfile.emer.cp[j].getCp_imgPath();

                dbts.createRecord(session, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "TEACHER" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID, "TEACHER", 32, l_teacherID, l_cpID, l_cpName, l_cpRelationship, l_cpOccupation, l_cpEmailID, l_cpcontactNo, l_cpImgPath, l_versionNumber);

            }

            dbg("end of teacherprofile create");

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

    public void authUpdate() throws DBValidationException, DBProcessingException, BSProcessingException {

        try {
            dbg("inside institute profile authUpdate");
            RequestBody<TeacherProfile> reqBody = request.getReqBody();
            TeacherProfile teacherProfile = reqBody.get();
            IDBTransactionService dbts = inject.getDBTransactionService();
            IBDProperties i_db_properties = session.getCohesiveproperties();
            String l_instituteID = request.getReqHeader().getInstituteID();
            String l_authStatus = request.getReqAudit().getAuthStatus();
            String l_checkerID = request.getReqAudit().getCheckerID();
            String l_checkerDateStamp = request.getReqAudit().getCheckerDateStamp();
            String l_checkerRemarks = request.getReqAudit().getCheckerRemarks();
            String l_teacherID = teacherProfile.getTeacherID();
            String[] l_primaryKey = {l_teacherID};

            Map<String, String> l_column_to_update = new HashMap();
            l_column_to_update.put("15", l_checkerID);
            l_column_to_update.put("17", l_checkerDateStamp);
            l_column_to_update.put("19", l_authStatus);
            l_column_to_update.put("22", l_checkerRemarks);

            dbts.updateColumn("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "TEACHER" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID, "TEACHER", "TVW_TEACHER_PROFILE", l_primaryKey, l_column_to_update, session);
            l_column_to_update = new HashMap();
            l_column_to_update.put("5", l_checkerID);
            l_column_to_update.put("7", l_checkerDateStamp);
            l_column_to_update.put("9", l_authStatus);
            l_column_to_update.put("12", l_checkerRemarks);

            dbts.updateColumn("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_TEACHER_MASTER", l_primaryKey, l_column_to_update, session);

            dbg("end of institute profile authUpdate");
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public void fullUpdate() throws BSProcessingException, DBValidationException, DBProcessingException {

        Map<String, String> l_column_to_update;

        try {
            dbg("inside institute profile fullUpdate");
            RequestBody<TeacherProfile> reqBody = request.getReqBody();
            TeacherProfile teacherProfile = reqBody.get();
            IDBTransactionService dbts = inject.getDBTransactionService();
            IBDProperties i_db_properties = session.getCohesiveproperties();
            String l_instituteID = request.getReqHeader().getInstituteID();
            String l_makerID = request.getReqAudit().getMakerID();
            String l_checkerID = request.getReqAudit().getCheckerID();
            String l_makerDateStamp = request.getReqAudit().getMakerDateStamp();
            String l_checkerDateStamp = request.getReqAudit().getCheckerDateStamp();
            String l_recordStatus = request.getReqAudit().getRecordStatus();
            String l_authStatus = request.getReqAudit().getAuthStatus();
            String l_versionNumber = request.getReqAudit().getVersionNumber();
            String l_makerRemarks = request.getReqAudit().getMakerRemarks();
            String l_checkerRemarks = request.getReqAudit().getCheckerRemarks();
            String l_teacherID = teacherProfile.getTeacherID();
            String l_teacherName = teacherProfile.getTeacherName();
            String l_qualifiction = teacherProfile.gen.getQualification();
//            String l_standard=teacherProfile.gen.classs.getStandard();
//            String l_section=teacherProfile.gen.classs.getSection();
            String l_dob = teacherProfile.gen.getDob();
            String l_contactNo = teacherProfile.gen.getContactNo();
            String l_emailID = teacherProfile.gen.getEmailID();
            String l_shortName = teacherProfile.gen.getShortName();
            String l_bloodGroup = teacherProfile.gen.getBloodGroup();
            String l_profileImgPath = teacherProfile.getProfileImgPath();
            String l_addressLine1 = teacherProfile.gen.address.getAddressLine1();
            String l_addressLine2 = teacherProfile.gen.address.getAddressLine2();
            String l_addressLine3 = teacherProfile.gen.address.getAddressLine3();
            String l_addressLine4 = teacherProfile.gen.address.getAddressLine4();
            String l_addressLine5 = teacherProfile.gen.address.getAddressLine5();
            String l_existingMedicalDetail = teacherProfile.emer.getExistingMedicalDetail();
            String l_gender = teacherProfile.gen.getGender();
            l_column_to_update = new HashMap();
            l_column_to_update.put("1", l_teacherID);
            l_column_to_update.put("2", l_teacherName);
            l_column_to_update.put("3", l_qualifiction);
//            l_column_to_update.put("4", l_standard);
//            l_column_to_update.put("5", l_section);
            l_column_to_update.put("4", l_dob);
            l_column_to_update.put("5", l_contactNo);
            l_column_to_update.put("6", l_emailID);
            l_column_to_update.put("7", l_bloodGroup);
            l_column_to_update.put("8", l_addressLine1);
            l_column_to_update.put("9", l_addressLine2);
            l_column_to_update.put("10", l_addressLine3);
            l_column_to_update.put("11", l_addressLine4);
            l_column_to_update.put("12", l_addressLine5);
            l_column_to_update.put("13", l_profileImgPath);
            l_column_to_update.put("14", l_makerID);
            l_column_to_update.put("15", l_checkerID);
            l_column_to_update.put("16", l_makerDateStamp);
            l_column_to_update.put("17", l_checkerDateStamp);
            l_column_to_update.put("18", l_recordStatus);
            l_column_to_update.put("19", l_authStatus);
            l_column_to_update.put("20", l_versionNumber);
            l_column_to_update.put("21", l_makerRemarks);
            l_column_to_update.put("22", l_checkerRemarks);
            l_column_to_update.put("23", l_shortName);
            l_column_to_update.put("24", l_existingMedicalDetail);
            l_column_to_update.put("25", l_gender);
            String[] l_primaryKey = {l_teacherID};

            dbts.updateColumn("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "TEACHER" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID, "TEACHER", "TVW_TEACHER_PROFILE", l_primaryKey, l_column_to_update, session);

            l_column_to_update = new HashMap();
            l_column_to_update.put("2", l_teacherName);
//        l_column_to_update.put("3", l_standard);
//        l_column_to_update.put("4", l_section);
            l_column_to_update.put("3", l_shortName);
            l_column_to_update.put("4", l_makerID);
            l_column_to_update.put("5", l_checkerID);
            l_column_to_update.put("6", l_makerDateStamp);
            l_column_to_update.put("7", l_checkerDateStamp);
            l_column_to_update.put("8", l_recordStatus);
            l_column_to_update.put("9", l_authStatus);
            l_column_to_update.put("10", l_versionNumber);
            l_column_to_update.put("11", l_makerRemarks);
            l_column_to_update.put("12", l_checkerRemarks);

            dbts.updateColumn("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_TEACHER_MASTER", l_primaryKey, l_column_to_update, session);

            IDBReadBufferService readBuffer = inject.getDBReadBufferService();
            Map<String, DBRecord> l_contactPersonMap = readBuffer.readTable("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "TEACHER" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID, "TEACHER", "TVW_CONTACT_PERSON_DETAILS", session, dbSession);
            setOperationsOfTheRecord("TVW_CONTACT_PERSON_DETAILS", l_contactPersonMap);

            for (int j = 0; j < teacherProfile.emer.cp.length; j++) {
                String l_cpID = teacherProfile.emer.cp[j].getCp_ID();
                String l_cpName = teacherProfile.emer.cp[j].getCp_name();
                String l_cpRelationship = teacherProfile.emer.cp[j].getCp_relationship();
                String l_cpOccupation = teacherProfile.emer.cp[j].getCp_occupation();
                String l_cpEmailID = teacherProfile.emer.cp[j].getCp_mailID();
                String l_cpcontactNo = teacherProfile.emer.cp[j].getCp_contactNo();
                String l_cpImgPath = teacherProfile.emer.cp[j].getCp_imgPath();

                if (teacherProfile.emer.cp[j].getOperation().equals("U")) {

                    l_column_to_update = new HashMap();
                    l_column_to_update.put("1", l_teacherID);
                    l_column_to_update.put("2", l_cpID);
                    l_column_to_update.put("3", l_cpName);
                    l_column_to_update.put("4", l_cpRelationship);
                    l_column_to_update.put("5", l_cpOccupation);
                    l_column_to_update.put("6", l_cpEmailID);
                    l_column_to_update.put("7", l_cpcontactNo);
                    l_column_to_update.put("8", l_cpImgPath);
                    l_column_to_update.put("9", l_versionNumber);
                    String[] l_cpPKey = {l_cpID};

                    dbts.updateColumn("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "TEACHER" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID, "TEACHER", "TVW_CONTACT_PERSON_DETAILS", l_cpPKey, l_column_to_update, session);

                } else {

                    dbts.createRecord(session, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "TEACHER" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID, "TEACHER", 32, l_teacherID, l_cpID, l_cpName, l_cpRelationship, l_cpOccupation, l_cpEmailID, l_cpcontactNo, l_cpImgPath, l_versionNumber);

                }
            }

            ArrayList<String> cpList = getRecordsToDelete("TVW_CONTACT_PERSON_DETAILS", l_contactPersonMap);

            for (int i = 0; i < cpList.size(); i++) {
                String pkey = cpList.get(i);
                deleteRecordsInTheList("TVW_CONTACT_PERSON_DETAILS", pkey);

            }

            dbg("end of institute profile fullUpdate");
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

    private void setOperationsOfTheRecord(String tableName, Map<String, DBRecord> tableMap) throws DBValidationException, DBProcessingException, BSProcessingException {

        try {
            dbg("inside getOperationsOfTheRecord");
            RequestBody<TeacherProfile> reqBody = request.getReqBody();
            TeacherProfile teacherProfile = reqBody.get();
            dbg("tableName" + tableName);

            switch (tableName) {

                case "TVW_CONTACT_PERSON_DETAILS":

                    for (int i = 0; i < teacherProfile.emer.cp.length; i++) {
                        String l_contactPersonID = teacherProfile.emer.cp[i].cp_ID;
                        String l_pkey = l_contactPersonID;
                        if (tableMap.containsKey(l_pkey)) {

                            teacherProfile.emer.cp[i].setOperation("U");
                        } else {

                            teacherProfile.emer.cp[i].setOperation("C");
                        }
                    }
                    break;

            }

            dbg("end of getOperationsOfTheRecord");

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

    }

    private ArrayList<String> getRecordsToDelete(String tableName, Map<String, DBRecord> tableMap) throws DBValidationException, DBProcessingException, BSProcessingException {

        try {

            dbg("inside getRecordsToDelete");
            RequestBody<TeacherProfile> reqBody = request.getReqBody();
            TeacherProfile teacherProfile = reqBody.get();
            ArrayList<String> recordsToDelete = new ArrayList();
            String teacherID = teacherProfile.getTeacherID();
//           Iterator<String>keyIterator=tableMap.keySet().iterator();
            List<DBRecord> filteredRecords = tableMap.values().stream().filter(rec -> rec.getRecord().get(0).trim().equals(teacherID)).collect(Collectors.toList());

            dbg("tableName" + tableName);
            switch (tableName) {

                case "TVW_CONTACT_PERSON_DETAILS":

//                   while(keyIterator.hasNext()){
                    for (int j = 0; j < filteredRecords.size(); j++) {
                        String contactPersonID = filteredRecords.get(j).getRecord().get(1).trim();
                        String tablePkey = contactPersonID;
                        dbg("tablePkey" + tablePkey);
                        boolean recordExistence = false;

                        for (int i = 0; i < teacherProfile.emer.cp.length; i++) {
                            String l_contactPersonID = teacherProfile.emer.cp[i].cp_ID;
                            String l_requestPkey = l_contactPersonID;
                            dbg("l_requestPkey" + l_requestPkey);
                            if (tablePkey.equals(l_requestPkey)) {
                                recordExistence = true;
                            }
                        }
                        if (!recordExistence) {
                            recordsToDelete.add(tablePkey);
                        }

                    }
                    break;

            }

            dbg("records to delete" + recordsToDelete.size());
            dbg("end of getRecordsToDelete");
            return recordsToDelete;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
    }

    private void deleteRecordsInTheList(String tableName, String pkey) throws DBValidationException, DBProcessingException, BSProcessingException {

        try {
            RequestBody<TeacherProfile> reqBody = request.getReqBody();
            TeacherProfile teacherProfile = reqBody.get();
            String l_instituteID = request.getReqHeader().getInstituteID();
            String l_teacherID = teacherProfile.getTeacherID();
            IBDProperties i_db_properties = session.getCohesiveproperties();
            IDBTransactionService dbts = inject.getDBTransactionService();
            String[] pkArr = pkey.split("~");

            dbts.deleteRecord("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "TEACHER" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID, "TEACHER", tableName, pkArr, session);

        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

    }

    public void delete() throws DBValidationException, DBProcessingException, BSProcessingException {

        try {
            dbg("inside teacher profile delete");
            RequestBody<TeacherProfile> reqBody = request.getReqBody();
            TeacherProfile teacherProfile = reqBody.get();
            IDBTransactionService dbts = inject.getDBTransactionService();
            IBDProperties i_db_properties = session.getCohesiveproperties();
            String l_instituteID = request.getReqHeader().getInstituteID();
            String l_teacherID = teacherProfile.getTeacherID();
            String[] l_primaryKey = {l_teacherID};

            dbts.deleteRecord("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "TEACHER" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID, "TEACHER", "TVW_TEACHER_PROFILE", l_primaryKey, session);

            dbts.deleteRecord("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_TEACHER_MASTER", l_primaryKey, session);

            for (int j = 0; j < teacherProfile.emer.cp.length; j++) {
                String l_cpID = teacherProfile.emer.cp[j].getCp_ID();
                String[] l_contactPersonPK = {l_cpID};

                dbts.deleteRecord("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "TEACHER" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID, "TEACHER", "TVW_CONTACT_PERSON_DETAILS", l_contactPersonPK, session);

            }
            dbg("end of teacher profile delete");
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

    }

    public void view() throws DBValidationException, DBProcessingException, BSProcessingException, BSValidationException {

        try {
            dbg("inside teacherProfile--->view");
            RequestBody<TeacherProfile> reqBody = request.getReqBody();
            TeacherProfile teacherProfile = reqBody.get();
            IDBReadBufferService readBuffer = inject.getDBReadBufferService();
            IBDProperties i_db_properties = session.getCohesiveproperties();
            String l_instituteID = request.getReqHeader().getInstituteID();
            String l_teacherID = teacherProfile.getTeacherID();
            String[] l_pkey = {l_teacherID};
            DBRecord profileRec = null;
            Map<String, DBRecord> l_contactPersonMap = null;
            IPDataService pds = inject.getPdataservice();

            try {

                profileRec = readBuffer.readRecord("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "TEACHER" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID, "TEACHER", "TVW_TEACHER_PROFILE", l_pkey, session, dbSession);
                l_contactPersonMap = readBuffer.readTable("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "TEACHER" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID, "TEACHER", "TVW_CONTACT_PERSON_DETAILS", session, dbSession);

            } catch (DBValidationException ex) {
                dbg("exception in view operation" + ex);
                if (ex.toString().contains("DB_VAL_011") || ex.toString().contains("DB_VAL_000")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                    session.getErrorhandler().log_app_error("BS_VAL_013", l_teacherID);
                    throw new BSValidationException("BSValidationException");

                } else {

                    throw ex;
                }
            }

            String classs = "";
            try {

                Map<String, ArrayList<String>> classMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_STANDARD_MASTER", session, dbSession);

                List<ArrayList<String>> classList = classMap.values().stream().filter(rec -> rec.get(3).trim().equals(l_teacherID)).collect(Collectors.toList());

                dbg("classList size" + classList);
                if (classList != null && !classList.isEmpty()) {

                    classs = classList.get(0).get(1).trim() + "/" + classList.get(0).get(2).trim();

                }

            } catch (DBValidationException ex) {
                if (ex.toString().contains("DB_VAL_011") || ex.toString().contains("DB_VAL_000")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                    session.getErrorhandler().log_app_error("BS_VAL_013", l_teacherID);
                } else {

                    throw ex;
                }
            }

            buildBOfromDB(profileRec, l_contactPersonMap, classs);

            dbg("end of teacher profile--->view");
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

    private void buildBOfromDB(DBRecord p_profileRecord, Map<String, DBRecord> p_contactPersonMap, String classs) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {
        try {

            dbg("inside buildBOfromDB");
            RequestBody<TeacherProfile> reqBody = request.getReqBody();
            TeacherProfile teacherProfile = reqBody.get();
            ArrayList<String> l_teacherProfileList = p_profileRecord.getRecord();

            if (l_teacherProfileList != null && !l_teacherProfileList.isEmpty()) {
                teacherProfile.setTeacherName(l_teacherProfileList.get(1).trim());
                teacherProfile.gen = new General();
                teacherProfile.gen.setQualification(l_teacherProfileList.get(2).trim());
                teacherProfile.gen.setClasss(classs);
//            teacherProfile.gen.classs=new Class();
//            teacherProfile.gen.classs.setStandard(l_teacherProfileList.get(3).trim());
//            teacherProfile.gen.classs.setSection(l_teacherProfileList.get(4).trim());
                teacherProfile.gen.setDob(l_teacherProfileList.get(3).trim());
                teacherProfile.gen.setContactNo(l_teacherProfileList.get(4).trim());
                teacherProfile.gen.setEmailID(l_teacherProfileList.get(5).trim());
                teacherProfile.gen.setBloodGroup(l_teacherProfileList.get(6).trim());
                teacherProfile.gen.setShortName(l_teacherProfileList.get(22));
                teacherProfile.gen.setGender(l_teacherProfileList.get(24));
                teacherProfile.gen.address = new Address();
                teacherProfile.gen.address.setAddressLine1(l_teacherProfileList.get(7).trim());
                teacherProfile.gen.address.setAddressLine2(l_teacherProfileList.get(8).trim());
                teacherProfile.gen.address.setAddressLine3(l_teacherProfileList.get(9).trim());
                teacherProfile.gen.address.setAddressLine4(l_teacherProfileList.get(10).trim());
                teacherProfile.gen.address.setAddressLine5(l_teacherProfileList.get(10).trim());
                teacherProfile.setProfileImgPath(l_teacherProfileList.get(12).trim());
                request.getReqAudit().setMakerID(l_teacherProfileList.get(13).trim());
                request.getReqAudit().setCheckerID(l_teacherProfileList.get(14).trim());
                request.getReqAudit().setMakerDateStamp(l_teacherProfileList.get(15).trim());
                request.getReqAudit().setCheckerDateStamp(l_teacherProfileList.get(16).trim());
                request.getReqAudit().setRecordStatus(l_teacherProfileList.get(17).trim());
                request.getReqAudit().setAuthStatus(l_teacherProfileList.get(18).trim());
                request.getReqAudit().setVersionNumber(l_teacherProfileList.get(19).trim());
                request.getReqAudit().setMakerRemarks(l_teacherProfileList.get(20).trim());
                request.getReqAudit().setCheckerRemarks(l_teacherProfileList.get(21).trim());

            }

            teacherProfile.emer = new Emergency();
            teacherProfile.emer.setExistingMedicalDetail(l_teacherProfileList.get(23).trim());

            if (p_contactPersonMap != null && !p_contactPersonMap.isEmpty()) {

//               Iterator<DBRecord> valueIterator= p_contactPersonMap.values().iterator();
                List<DBRecord> contactPersonList = p_contactPersonMap.values().stream().filter(rec -> rec.getRecord().get(8).trim().equals(request.getReqAudit().getVersionNumber())).collect(Collectors.toList());
                teacherProfile.emer.cp = new ContactPerson[contactPersonList.size()];
//                int i=0;
//                while(valueIterator.hasNext()){
                for (int i = 0; i < contactPersonList.size(); i++) {
//                    DBRecord l_cpRecords=valueIterator.next();
                    DBRecord l_cpRecords = contactPersonList.get(i);
                    teacherProfile.emer.cp[i] = new ContactPerson();
                    teacherProfile.emer.cp[i].setCp_ID(l_cpRecords.getRecord().get(1).trim());
                    teacherProfile.emer.cp[i].setCp_name(l_cpRecords.getRecord().get(2).trim());
                    teacherProfile.emer.cp[i].setCp_relationship(l_cpRecords.getRecord().get(3).trim());
                    teacherProfile.emer.cp[i].setCp_occupation(l_cpRecords.getRecord().get(4).trim());
                    teacherProfile.emer.cp[i].setCp_mailID(l_cpRecords.getRecord().get(5).trim());
                    teacherProfile.emer.cp[i].setCp_contactNo(l_cpRecords.getRecord().get(6).trim());
                    teacherProfile.emer.cp[i].setCp_imgPath(l_cpRecords.getRecord().get(7).trim());
//                    i++;
                }
            }

            dbg("end of  buildBOfromDB");

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    }

    public JsonObject buildJsonResFromBO() throws BSProcessingException {
        JsonObject body;
        JsonObject general;
        JsonObject emergency;
        try {
            dbg("inside teacher profile buildJsonResFromBO");
            RequestBody<TeacherProfile> reqBody = request.getReqBody();
            TeacherProfile teacherProfile = reqBody.get();
            JsonArrayBuilder cpArr = Json.createArrayBuilder();

            for (int j = 0; j < teacherProfile.emer.cp.length; j++) {
                cpArr.add(Json.createObjectBuilder().add("cp_ID", teacherProfile.emer.cp[j].getCp_ID())
                        .add("cp_Name", teacherProfile.emer.cp[j].getCp_name())
                        .add("cp_relationship", teacherProfile.emer.cp[j].getCp_relationship())
                        .add("cp_occupation", teacherProfile.emer.cp[j].getCp_occupation())
                        .add("cp_emailID", teacherProfile.emer.cp[j].getCp_mailID())
                        .add("cp_contactNo", teacherProfile.emer.cp[j].getCp_contactNo())
                        .add("cp_imgPath", teacherProfile.emer.cp[j].getCp_imgPath()));
            }

            emergency = Json.createObjectBuilder().add("existingMedicalDetails", teacherProfile.emer.getExistingMedicalDetail())
                    .add("contactPerson", cpArr).build();

            general = Json.createObjectBuilder().add("qualification", teacherProfile.gen.getQualification())
                    .add("class", teacherProfile.gen.getClasss())
                    .add("dob", teacherProfile.gen.getDob())
                    .add("contactNo", teacherProfile.gen.getContactNo())
                    .add("emailID", teacherProfile.gen.getEmailID())
                    .add("bloodGroup", teacherProfile.gen.getBloodGroup())
                    .add("gender", teacherProfile.gen.getGender())
                    .add("shortName", teacherProfile.gen.getShortName())
                    .add("address", Json.createObjectBuilder().add("addressLine1", teacherProfile.gen.address.getAddressLine1()).add("addressLine2", teacherProfile.gen.address.getAddressLine2()).add("addressLine3", teacherProfile.gen.address.getAddressLine3()).add("addressLine4", teacherProfile.gen.address.getAddressLine4()).add("addressline5", teacherProfile.gen.address.getAddressLine5()))
                    .build();

            body = Json.createObjectBuilder().add("teacherID", teacherProfile.getTeacherID())
                    .add("teacherName", teacherProfile.getTeacherName())
                    .add("profileImgPath", teacherProfile.getProfileImgPath())
                    .add("general", general)
                    .add("emergency", emergency).build();

            dbg("end of teacher profile buildJsonResFromBO");
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        return body;
    }

    private boolean businessValidation(ErrorHandler errhandler) throws BSProcessingException, BSValidationException, DBValidationException, DBProcessingException {
        boolean status = true;

        try {
            dbg("inside teacher profile--->businessValidation");
            String l_operation = request.getReqHeader().getOperation();

            if (!masterMandatoryValidation(errhandler)) {
                status = false;
            }

            if (!(l_operation.equals("View")) && !l_operation.equals("Create-Default") && !l_operation.equals("Delete")) {

                if (!detailMandatoryValidation(errhandler)) {

                    status = false;
                } else {

                    if (!detailDataValidation(errhandler)) {

                        status = false;
                    }

                }

            }
            dbg("end of teacher profile--->businessValidation");
        } catch (BSProcessingException ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());

        } catch (BSValidationException ex) {
            throw ex;

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
        return status;
    }

    private boolean masterMandatoryValidation(ErrorHandler errhandler) throws BSProcessingException, BSValidationException {

        boolean status = true;

        try {

            dbg("inside teacher profile master mandatory validation");
            RequestBody<TeacherProfile> reqBody = request.getReqBody();
            TeacherProfile teacherProfile = reqBody.get();
            String operation = request.getReqHeader().getOperation();

            if (teacherProfile.getTeacherID() == null || teacherProfile.getTeacherID().isEmpty()) {
                status = false;
                errhandler.log_app_error("BS_VAL_002", "teacherID");
            }

            if (!operation.equals("View")) {

                if (teacherProfile.getTeacherName() == null || teacherProfile.getTeacherName().isEmpty()) {
                    status = false;
                    errhandler.log_app_error("BS_VAL_002", "teacherName");
                }

            }

            dbg("end of teacher profile master mandatory validation");

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

        return status;

    }

    private boolean detailMandatoryValidation(ErrorHandler errhandler) throws BSProcessingException, BSValidationException {
        boolean status = true;
        try {
            dbg("inside teacher profile detail mandatory validation");
            RequestBody<TeacherProfile> reqBody = request.getReqBody();
            TeacherProfile teacherProfile = reqBody.get();

//            if(teacherProfile.getProfileImgPath()==null||teacherProfile.getProfileImgPath().isEmpty()){
//               status=false;  
//               errhandler.log_app_error("BS_VAL_002","Image Path");  
//            } 
            if (teacherProfile.getGen() == null) {
                status = false;
                errhandler.log_app_error("BS_VAL_002", "General");
            } else {

//                if(teacherProfile.getGen().getQualification()==null||teacherProfile.getGen().getQualification().isEmpty()){
//                    status=false;
//                    errhandler.log_app_error("BS_VAL_002","Qualification");
//                }
//                if(teacherProfile.getGen().getClasss()==null){
//                    status=false;
//                    errhandler.log_app_error("BS_VAL_002","Class");
//                }else{
//                    if(teacherProfile.getGen().getClasss().getStandard()==null||teacherProfile.getGen().getClasss().getStandard().isEmpty()){
//                        status=false;
//                        errhandler.log_app_error("BS_VAL_002","Standard");
//                    }
//                    if(teacherProfile.getGen().getClasss().getSection()==null||teacherProfile.getGen().getClasss().getSection().isEmpty()){
//                        status=false;
//                        errhandler.log_app_error("BS_VAL_002","Section");
//                    }
//                }
//                if(teacherProfile.getGen().getDob()==null||teacherProfile.getGen().getDob().isEmpty()){
//                    status=false;
//                    errhandler.log_app_error("BS_VAL_002","Date of birth");
//                }                
//                if (teacherProfile.getGen().getContactNo() == null || teacherProfile.getGen().getContactNo().isEmpty()) {
//                    status = false;
//                    errhandler.log_app_error("BS_VAL_002", "ContactNo");
//                }

//                if(teacherProfile.getGen().getEmailID()==null||teacherProfile.getGen().getEmailID().isEmpty()){
//                    status=false;
//                    errhandler.log_app_error("BS_VAL_002","EmailID");
//                }  
//                
//                if(teacherProfile.getGen().getBloodGroup()==null||teacherProfile.getGen().getBloodGroup().isEmpty()){
//                    status=false;
//                    errhandler.log_app_error("BS_VAL_002","BloodGroup");
//                } 
//                
//                if(teacherProfile.getGen().getGender()==null||teacherProfile.getGen().getGender().isEmpty()){
//                    status=false;
//                    errhandler.log_app_error("BS_VAL_002","BloodGroup");
//                } 
//                if (teacherProfile.getGen().getAddress() == null) {
//                    status = false;
//                    errhandler.log_app_error("BS_VAL_002", "Address");
//                } else {
//
////                    if(teacherProfile.getGen().getAddress().getAddressLine1()==null||teacherProfile.getGen().getAddress().getAddressLine1().isEmpty()){
////                       status=false;
////                       errhandler.log_app_error("BS_VAL_002","AddressLine1");
////                    }
//                    if (teacherProfile.getGen().getAddress().getAddressLine2() == null || teacherProfile.getGen().getAddress().getAddressLine2().isEmpty()) {
//                        status = false;
//                        errhandler.log_app_error("BS_VAL_002", "AddressLine2");
//                    }
//                    if (teacherProfile.getGen().getAddress().getAddressLine3() == null || teacherProfile.getGen().getAddress().getAddressLine3().isEmpty()) {
//                        status = false;
//                        errhandler.log_app_error("BS_VAL_002", "AddressLine3");
//                    }
//                    if (teacherProfile.getGen().getAddress().getAddressLine4() == null || teacherProfile.getGen().getAddress().getAddressLine4().isEmpty()) {
//                        status = false;
//                        errhandler.log_app_error("BS_VAL_002", "AddressLine4");
//                    }
//
//                }
            }
//            if(teacherProfile.getEmer()==null){
//                status=false;
//                errhandler.log_app_error("BS_VAL_002","Emergency");
//            }else{
//                
//                for(int i=0;i<teacherProfile.getEmer().getCp().length;i++){
//                    
//                    if(teacherProfile.getEmer().getCp()[i].getCp_name()==null||teacherProfile.getEmer().getCp()[i].getCp_name().isEmpty()){
//                        status=false;
//                        errhandler.log_app_error("BS_VAL_002","Contact person's name");
//                    }
//                    
//                    if(teacherProfile.getEmer().getCp()[i].getCp_relationship()==null||teacherProfile.getEmer().getCp()[i].getCp_relationship().isEmpty()){
//                        status=false;
//                        errhandler.log_app_error("BS_VAL_002","Contact person's relationship");
//                    }
//                    
////                    if(teacherProfile.getEmer().getCp()[i].getCp_occupation()==null||teacherProfile.getEmer().getCp()[i].getCp_occupation().isEmpty()){
////                        status=false;
////                        errhandler.log_app_error("BS_VAL_002","Contact person's occupation");
////                    }
//                    
//                    if(teacherProfile.getEmer().getCp()[i].getCp_mailID()==null||teacherProfile.getEmer().getCp()[i].getCp_mailID().isEmpty()){
//                        status=false;
//                        errhandler.log_app_error("BS_VAL_002","Contact person's emailId");
//                    }
//                    
//                    if(teacherProfile.getEmer().getCp()[i].getCp_contactNo()==null||teacherProfile.getEmer().getCp()[i].getCp_contactNo().isEmpty()){
//                        status=false;
//                        errhandler.log_app_error("BS_VAL_002","Contact person's contactNo");
//                    }
//                }
//                
//            }

            dbg("end of teacher profile detail mandatory validation");

        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }

        return status;

    }

    private boolean detailDataValidation(ErrorHandler errhandler) throws BSProcessingException, DBProcessingException, DBValidationException, BSValidationException {
        boolean status = true;

        try {
            dbg("indside teacherProfile detailDataValidation");
            BSValidation bsv = inject.getBsv(session);
            RequestBody<TeacherProfile> reqBody = request.getReqBody();
            TeacherProfile teacherProfile = reqBody.get();
//            String l_standard=teacherProfile.getGen().getClasss().getStandard();
//            String l_section=teacherProfile.getGen().getClasss().getSection();
            String l_dob = teacherProfile.getGen().getDob();
            String l_contactNo = teacherProfile.getGen().getContactNo();
            String l_emailID = teacherProfile.getGen().getEmailID();
//            String l_instituteID=request.getReqHeader().getInstituteID();
            String l_bloodGroup = teacherProfile.getGen().getBloodGroup();
            String l_gender = teacherProfile.getGen().getGender();

//            if(!bsv.standardSectionValidation(l_standard,l_section, l_instituteID, session, dbSession, inject)){
//                 status=false;
//                 errhandler.log_app_error("BS_VAL_003","Class");
//            }
            if (l_dob != null && !l_dob.isEmpty()) {
                if (!bsv.dateFormatValidation(l_dob, session, dbSession, inject)) {
                    status = false;
                    errhandler.log_app_error("BS_VAL_003", "Date of birth");
                }
            }

            if (l_contactNo != null && !l_contactNo.isEmpty()) {
                if (!bsv.contactNoValidation(l_contactNo, session, dbSession, inject)) {
                    status = false;
                    errhandler.log_app_error("BS_VAL_003", "ContactNo ");
                }

            }

            if (l_emailID != null && !l_emailID.isEmpty()) {

                if (!bsv.emailValidation(l_emailID, session, dbSession, inject)) {
                    status = false;
                    errhandler.log_app_error("BS_VAL_003", "Email ID");
                }

            }

            if (l_bloodGroup != null && !l_bloodGroup.isEmpty()) {

                if (!bsv.bloodGroupValidation(l_bloodGroup, session, dbSession, inject)) {
                    status = false;
                    errhandler.log_app_error("BS_VAL_003", "Blood group");
                }

            }

            if (l_gender != null && !l_gender.isEmpty()) {

                if ((!(l_gender.equals("M") || l_gender.equals("F") || l_gender.equals("O")))) {

                    status = false;
                    errhandler.log_app_error("BS_VAL_003", "Gender");
                }
            }
            for (int i = 0; i < teacherProfile.getEmer().getCp().length; i++) {

                String l_cp_contactNo = teacherProfile.getEmer().getCp()[i].getCp_contactNo();
                String l_cpEmailID = teacherProfile.getEmer().getCp()[i].getCp_mailID();

                if (l_cp_contactNo != null && !l_cp_contactNo.isEmpty()) {

                    if (!bsv.contactNoValidation(l_cp_contactNo, session, dbSession, inject)) {
                        status = false;
                        errhandler.log_app_error("BS_VAL_003", "Contact person's contactNo ");
                    }

                }

                if (l_cpEmailID != null && !l_cpEmailID.isEmpty()) {

                    if (!bsv.emailValidation(l_cpEmailID, session, dbSession, inject)) {
                        status = false;
                        errhandler.log_app_error("BS_VAL_003", "Contact person's email Id");
                    }

                }
            }

            dbg("end of teacherProfile detailDataValidation");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (DBValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        return status;

    }

    public ExistingAudit getExistingAudit() throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {
        ExistingAudit exAudit = new ExistingAudit();
        try {
            dbg("inside TeacherProfile--->getExistingAudit");
            exAudit = new ExistingAudit();
            IBDProperties i_db_properties = session.getCohesiveproperties();
            IDBReadBufferService readBuffer = inject.getDBReadBufferService();
            String l_operation = request.getReqHeader().getOperation();
            String l_service = request.getReqHeader().getService();
            String l_instituteID = request.getReqHeader().getInstituteID();
            dbg("l_operation" + l_operation);
            dbg("l_service" + l_service);
            dbg("l_instituteID" + l_instituteID);
            if (!(l_operation.equals("Create") || l_operation.equals("View"))) {

                dbg("inside UserProfile--->getExistingAudit--->Service--->UserProfile");
                RequestBody<TeacherProfile> reqBody = request.getReqBody();
                TeacherProfile teacherProfile = reqBody.get();
                String l_teacherID = teacherProfile.getTeacherID();
                String[] l_pkey = {l_teacherID};
                DBRecord l_teacherProfileRecord = readBuffer.readRecord("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "TEACHER" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_teacherID, "TEACHER", "TVW_TEACHER_PROFILE", l_pkey, session, dbSession);
                exAudit.setAuthStatus(l_teacherProfileRecord.getRecord().get(18).trim());
                exAudit.setMakerID(l_teacherProfileRecord.getRecord().get(13).trim());
                exAudit.setRecordStatus(l_teacherProfileRecord.getRecord().get(17).trim());
                exAudit.setVersionNumber(Integer.parseInt(l_teacherProfileRecord.getRecord().get(19).trim()));

                dbg("exAuthStatus" + exAudit.getAuthStatus());
                dbg("exMakerID" + exAudit.getMakerID());
                dbg("exRecordStatus" + exAudit.getRecordStatus());
                dbg("exVersionNumber" + exAudit.getVersionNumber());

                dbg("end of TeacherProfile--->getExistingAudit");
            } else {
                return null;
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
        return exAudit;
    }

    public void relationshipProcessing() throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

//     try{
//        dbg("inside teacher profile relationshipProcessing") ;
//        RequestBody<TeacherProfile> reqBody = request.getReqBody();
//        TeacherProfile teacherProfile =reqBody.get();
//        String l_teacherID=teacherProfile.getTeacherID();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        String l_instituteID=request.getReqHeader().getInstituteID();
//        String  versionNumber=null;
//        String[] l_pkey={l_teacherID};
//        DBRecord teacherMasterRec;
//        try{
//        
//            teacherMasterRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_TEACHER_MASTER", l_pkey, session,dbSession,true);
//            versionNumber=teacherMasterRec.getRecord().get(9);
//            dbg("versionNumber"+versionNumber);
//        }catch(DBValidationException ex){
//            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
//                
//               session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
//               session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
//               exAudit.setRelatioshipOperation("C");
//                
//            }
//        }
//        
//        if(exAudit.getRelatioshipOperation().equals("C")){
//
//             buildRequestAndCallTeacherMaster(1,"O");
//        }else if(exAudit.getRelatioshipOperation().equals("M")){
//            
//            buildRequestAndCallTeacherMaster(Integer.parseInt(versionNumber)+1,"O");
//        }else if(exAudit.getRelatioshipOperation().equals("D")){
//            
//            buildRequestAndCallTeacherMaster(Integer.parseInt(versionNumber)+1,"D");
//        }
//        
//   
//         dbg("end of teacher profile relationshipProcessing");
//         }catch(DBValidationException ex){
//             throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//             throw new DBProcessingException(ex.toString());
//        }catch(BSValidationException ex){
//            throw ex;
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//     
//        }
    }

    private void buildRequestAndCallTeacherMaster(int p_versionNumber, String p_recordStatus) throws BSProcessingException, DBValidationException, DBProcessingException, BSValidationException {

        try {
            dbg("inside buildRequestAndCallTeacherMaster");
            dbg("versionNumber" + p_versionNumber);
            dbg("p_recordStatus" + p_recordStatus);
            RequestBody<TeacherProfile> reqBody = request.getReqBody();
            TeacherProfile teacherProfile = reqBody.get();
            IBDProperties i_db_properties = session.getCohesiveproperties();
            ITeacherMasterService tms = inject.getTeacherMasterService();
            String l_instituteID = request.getReqHeader().getInstituteID();
            JsonObject teacherMaster;
            String l_msgID = request.getReqHeader().getMsgID();
            String l_correlationID = " ";
            String l_service = "TeacherMaster";
            String l_operation = "AutoAuth";
            JsonArray l_businessEntity = Json.createArrayBuilder().add(Json.createObjectBuilder().add("entityName", "instituteID")
                    .add("entityValue", l_instituteID)).build();
            String l_userID = request.getReqHeader().getUserID();
            String l_source = "cohesive_backend";
            String l_status = " ";
            String l_makerID = l_userID;
            String l_checkerID = l_userID;
            String dateformat = i_db_properties.getProperty("DATE_TIME_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
            Date date = new Date();
            String l_makerDateStamp = formatter.format(date);
            String l_checkerDateStamp = formatter.format(date);
            String l_authStatus = "A";
            String l_makerRemarks = request.getReqAudit().getMakerRemarks();
            String l_checkerRemarks = request.getReqAudit().getCheckerRemarks();
            String l_teacherID = teacherProfile.getTeacherID();
            String l_teacherName = teacherProfile.getTeacherName();
//        String l_standard=teacherProfile.gen.classs.getStandard();
//        String l_section=teacherProfile.gen.classs.getSection();
            String l_shortName = teacherProfile.gen.getShortName();

            teacherMaster = Json.createObjectBuilder().add("header", Json.createObjectBuilder()
                    .add("msgID", l_msgID)
                    .add("correlationID", l_correlationID)
                    .add("service", l_service)
                    .add("operation", l_operation)
                    .add("businessEntity", l_businessEntity)
                    .add("instituteID", l_instituteID)
                    .add("status", l_status)
                    .add("source", l_source)
                    .add("userID", l_userID))
                    .add("body", Json.createObjectBuilder()
                            .add("teacherID", l_teacherID)
                            .add("teacherName", l_teacherName)
                            //                                                        .add("standard", l_standard)
                            //                                                        .add("section", l_section)
                            .add("shortName", l_shortName))
                    .add("auditLog", Json.createObjectBuilder()
                            .add("makerID", l_makerID)
                            .add("checkerID", l_checkerID)
                            .add("makerDateStamp", l_makerDateStamp)
                            .add("checkerDateStamp", l_checkerDateStamp)
                            .add("recordStatus", p_recordStatus)
                            .add("authStatus", l_authStatus)
                            .add("versionNumber", Integer.toString(p_versionNumber))
                            .add("makerRemarks", l_makerRemarks)
                            .add("checkerRemarks", l_checkerRemarks)).build();
            dbg("before  teachermaster call");
            tms.processing(teacherMaster, session);
            dbg("after teacher call");

            dbg("end of buildRequestAndCallTeacherMaster");
        } catch (DBValidationException ex) {
            throw ex;
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (BSValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }

    }

    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
}
