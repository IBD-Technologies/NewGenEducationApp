/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.institute;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.institute.FeePaymentBusiness;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author ibdtech
 */
public class FeePaymentBusinessDataSet {

    public ArrayList<FeePaymentBusiness> getFeePaymentBusiness(String standard, String section, String studentID, String feeID, String fromDate, String toDate, String l_instituteID, CohesiveSession session, DBSession dbSession, ReportDependencyInjection inject, AppDependencyInjection appInject) throws DBProcessingException, DBValidationException {
        ArrayList<FeePaymentBusiness> dataset = new ArrayList();

        try {

            IDBReadBufferService readBuffer = inject.getDBReadBufferService();
            IBDProperties i_db_properties = session.getCohesiveproperties();
            BusinessService bs = appInject.getBusinessService(session);
            Map<String, DBRecord> instituteFeeMap = null;
            IPDataService pds = inject.getPdataservice();
            dbg("fromDate"+fromDate,session);
            dbg("toDate"+toDate,session);
            int k=1;

            try {

                instituteFeeMap = readBuffer.readTable("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "Fee", "INSTITUTE", "INSTITUTE_FEE_MANAGEMENT", session, dbSession);

            } catch (DBValidationException ex) {
                dbg("exception in view operation" + ex, session);
                if (ex.toString().contains("DB_VAL_011") || ex.toString().contains("DB_VAL_000")) {
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");

                } else {

                    throw ex;
                }
            }

            if (instituteFeeMap != null) {

                List<DBRecord> filterFeeRecords = this.getFilteredFeeRecords(feeID, standard, section, studentID, l_instituteID, instituteFeeMap, session, appInject, dbSession);

                for (int i = 0; i < filterFeeRecords.size(); i++) {

                    dbg("listForGroup iteration ", session);
                    ArrayList<String> feeList = filterFeeRecords.get(i).getRecord();
                    String l_feeID = feeList.get(2).trim();
                    String amount = feeList.get(5).trim();
                    String dueDate = feeList.get(6).trim();

                    Map<String, DBRecord> paymentMap = null;

                    try {

                        paymentMap = readBuffer.readTable("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + "FEE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_feeID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_feeID, "FEE", "INSTITITUTE_FEE_PAYMENT", session, dbSession);

                    } catch (DBValidationException ex) {
                        dbg("exception in view operation" + ex, session);
                        if (ex.toString().contains("DB_VAL_011") || ex.toString().contains("DB_VAL_000")) {
                            session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                            session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                            //                            session.getErrorhandler().log_app_error("BS_VAL_013", l_feeID);
                            //                            throw new BSValidationException("BSValidationException");

                        } else {

                            throw ex;
                        }
                    }

                    if(paymentMap!=null){
                    
                    
                    List<DBRecord> filteredRecords = this.getFilteredPaymentRecords(l_feeID, standard, section, studentID, l_instituteID, paymentMap, session, appInject, dbSession, fromDate, toDate);

                    for (DBRecord paymentRecord : filteredRecords) {

                        String l_paymentDate = paymentRecord.getRecord().get(2).trim();

                        String l_studentID = paymentRecord.getRecord().get(0).trim();
                        String l_paymentID = paymentRecord.getRecord().get(1).trim();
                        String l_paymentPaid = paymentRecord.getRecord().get(3).trim();

                        FeePaymentBusiness feePayment = new FeePaymentBusiness();
                        feePayment.setStudentID(l_studentID);
                        feePayment.setPaymentID(l_paymentID);
                        feePayment.setPaymentAmount(l_paymentPaid);
                        feePayment.setPaymentDate(l_paymentDate);
                        feePayment.setFeeID(l_feeID);
                        feePayment.setDueDate(dueDate);
                        feePayment.setFeeAmount(amount);
                        String[] studentPkey = {l_studentID};
                        ArrayList<String> l_studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", studentPkey);
                        String l_standard = l_studentList.get(2).trim();
                        String l_section = l_studentList.get(3).trim();
                        feePayment.setStudentName(bs.getStudentName(l_studentID, l_instituteID, session, dbSession, appInject));
                        feePayment.setStandard(l_standard);
                        feePayment.setSection(l_section);
                        feePayment.setSerialNumber(Integer.toString(k));
                        dataset.add(feePayment);
                        k++;
                    }
                    
                    }
                    
                }

            }

            if (dataset.isEmpty()) {

                FeePaymentBusiness feePayment = new FeePaymentBusiness();
                feePayment.setStudentID(" ");
                feePayment.setStudentName(" ");
                feePayment.setPaymentID(" ");
                feePayment.setPaymentAmount(" ");
                feePayment.setPaymentDate(" ");
                feePayment.setFeeID(" ");
                feePayment.setStudentName(" ");
                feePayment.setDueDate(" ");
                feePayment.setFeeAmount(" ");
                feePayment.setStandard(" ");
                feePayment.setSection(" ");
                feePayment.setSerialNumber(" ");
                dataset.add(feePayment);

            }

            dbg("end of getFeePaymentBusinessObject", session);
        } catch (DBProcessingException ex) {
            dbg(ex, session);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (DBValidationException ex) {
            dbg(ex, session);
            throw ex;
        } catch (Exception ex) {
            dbg(ex, session);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        }

        return dataset;
    }

    private List<DBRecord> getFilteredFeeRecords(String feeID, String standard, String section, String studentID, String instituteID, Map<String, DBRecord> instituteFeeMap, CohesiveSession session, AppDependencyInjection appInject, DBSession dbSession) throws DBProcessingException, DBValidationException, BSProcessingException, BSValidationException {

        try {

            BusinessService bs = appInject.getBusinessService(session);
            List<DBRecord> feeRecords = instituteFeeMap.values().stream().filter(rec -> rec.getRecord().get(11).trim().equals("O") && rec.getRecord().get(12).trim().equals("A")).collect(Collectors.toList());

            if (feeID != null && !feeID.isEmpty()) {

                List<DBRecord> l_studentList = feeRecords.stream().filter(rec -> rec.getRecord().get(2).trim().equals(feeID)).collect(Collectors.toList());
                feeRecords = new ArrayList<DBRecord>(l_studentList);
                dbg("l_feeID filter feeRecords size" + feeRecords.size(), session);
            }

            Iterator<DBRecord> feeRecordIterator = feeRecords.iterator();
            List<DBRecord> filteredRecords = new ArrayList();

            while (feeRecordIterator.hasNext()) {

                DBRecord feeRecord = feeRecordIterator.next();
                String groupID = feeRecord.getRecord().get(1).trim();

                if (studentID != null && !studentID.isEmpty()) {

                    if (bs.checkStudentExistenceInTheGroup(instituteID, studentID, groupID, session, dbSession, appInject)) {

                        filteredRecords.add(feeRecord);

                    }

                } else if (standard != null && !standard.isEmpty() && section != null && !section.isEmpty()) {

                    if (bs.checkClassExistenceInTheGroup(instituteID, standard, section, groupID, session, dbSession, appInject)) {

                        filteredRecords.add(feeRecord);

                    }

                } else {

                    filteredRecords.add(feeRecord);
                }

            }

            return filteredRecords;

        } catch (DBProcessingException ex) {
            dbg(ex, session);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (DBValidationException ex) {
            dbg(ex, session);
            throw ex;
        } catch (Exception ex) {
            dbg(ex, session);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        }

    }

//    private ArrayList<String>getStudents(String studentID,String standard,String section,String instituteID,CohesiveSession session,DBSession dbSession,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
//        
//        try{
//            BusinessService bs=appInject.getBusinessService(session);
//            ArrayList<String>studentList=new ArrayList();
//            
//            if(studentID!=null&&!studentID.isEmpty()){
//                
//                studentList.add(studentID);
//            }else if(standard!=null&&!standard.isEmpty()&&section!=null&&!section.isEmpty()){
//            
//                
//                studentList=bs.getStudentsOfTheClass(studentID, standard, section, session, dbSession, appInject);
//        
//            }
//            
//            
//            return studentList;
//        }catch(DBProcessingException ex){
//          dbg(ex,session);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch(DBValidationException ex){
//          dbg(ex,session);
//          throw ex;
//     }catch(Exception ex){
//         dbg(ex,session);
//         throw new DBProcessingException("DBProcessingException"+ex.toString());
//     }
//    }
    private List<DBRecord> getFilteredPaymentRecords(String feeID, String standard, String section, String studentID, String instituteID, Map<String, DBRecord> institutePaymentMap, CohesiveSession session, AppDependencyInjection appInject, DBSession dbSession, String l_fromDate, String l_toDate) throws DBProcessingException, DBValidationException, BSProcessingException, BSValidationException {

        try {
            dbg("inside getFilteredPaymentRecords", session);
            BusinessService bs = appInject.getBusinessService(session);
            List<DBRecord> paymentRecords = institutePaymentMap.values().stream().collect(Collectors.toList());
            IPDataService pds = appInject.getPdataservice();
            IBDProperties i_db_properties = session.getCohesiveproperties();
            String dateFormat = i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

            Iterator<DBRecord> paymentRecordIterator = paymentRecords.iterator();
            List<DBRecord> filteredRecords = new ArrayList();
            dbg("studentID" + studentID, session);
            dbg("standard" + standard, session);
            dbg("section" + section, session);

            while (paymentRecordIterator.hasNext()) {

                DBRecord paymentRecord = paymentRecordIterator.next();
                String l_studentID = paymentRecord.getRecord().get(0).trim();
                dbg("l_studentID" + l_studentID, session);

                if (studentID != null && !studentID.isEmpty()) {

                    if (l_studentID.equals(studentID)) {

                        filteredRecords.add(paymentRecord);

                    }

                } else if (standard != null && !standard.isEmpty() && section != null && !section.isEmpty()) {

                    String[] studentPkey = {l_studentID};
                    ArrayList<String> l_studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", studentPkey);
                    String l_standard = l_studentList.get(2).trim();
                    String l_section = l_studentList.get(3).trim();

                    if (l_standard.equals(standard) && l_section.equals(section)) {

                        filteredRecords.add(paymentRecord);

                    }

                } else {

                    filteredRecords.add(paymentRecord);
                }

            }

            List<DBRecord> dateFilterdRecords = new ArrayList();

            if (l_fromDate != null && !l_fromDate.isEmpty() && l_toDate != null && !l_toDate.isEmpty()) {

                for (DBRecord paymentRecord : filteredRecords) {

                    String l_paymentDate = paymentRecord.getRecord().get(2).trim();
                    Date paymentDate = formatter.parse(l_paymentDate);
                    Date fromDate = formatter.parse(l_fromDate);
                    Date toDate = formatter.parse(l_toDate);

                    if (paymentDate.compareTo(fromDate) >= 0) {

                        if (paymentDate.compareTo(toDate) <= 0) {

                            dateFilterdRecords.add(paymentRecord);

                        }
                    }

                }

            } else {
                dateFilterdRecords = filteredRecords;
            }

            dbg("end of getFilteredPaymentRecords", session);
            return dateFilterdRecords;

        } catch (DBProcessingException ex) {
            dbg(ex, session);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (DBValidationException ex) {
            dbg(ex, session);
            throw ex;
        } catch (Exception ex) {
            dbg(ex, session);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        }

    }

    public void dbg(String p_Value, CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex, CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}
