/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.ASSIGNMENT_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.ASSIGNMENT_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.ASSIGNMENT_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.ATTENDANCE_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.ATTENDANCE_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.ATTENDANCE_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.EXAM_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.EXAM_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.EXAM_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ASSIGNMENT_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ASSIGNMENT_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ATTENDANCE_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ATTENDANCE_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ATTENDANCE_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_EXAM_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_EXAM_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_EXAM_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_MARK_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_MARK_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_MARK_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_TIMETABLE_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_TIMETABLE_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_TIMETABLE_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.TIMETABLE_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.TIMETABLE_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.TIMETABLE_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author DELL
 */
@Remote
public interface IBatchDataset {
//    public ArrayList<APP_EOD_STATUS>getAPP_EOD_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<APP_EOD_STATUS_ERROR>getAPP_EOD_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<APP_EOD_STATUS_HISTORY>getAPP_EOD_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<BATCH_CONFIG>getBATCH_CONFIG_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<BATCH_STATUS>getBATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<BATCH_STATUS_ERROR>getBATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<BATCH_STATUS_HISTORY>getBATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<INSTITUTE_EOD_STATUS>getINSTITUTE_EOD_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<INSTITUTE_EOD_STATUS_ERROR>getINSTITUTE_EOD_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<INSTITUTE_EOD_STATUS_HISTORY>getINSTITUTE_EOD_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<STUDENT_ASSIGNMENT_EOD_STATUS>getSTUDENT_ASSIGNMENT_EOD_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<STUDENT_ASSIGNMENT_EOD_STATUS_ERROR>getSTUDENT_ASSIGNMENT_EOD_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY>getSTUDENT_ASSIGNMENT_EOD_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<OTHER_ACTIVITY_EOD_STATUS>getOTHER_ACTIVITY_EOD_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<OTHER_ACTIVITY_EOD_STATUS_ERROR>getOTHER_ACTIVITY_EOD_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<OTHER_ACTIVITY_EOD_STATUS_HISTORY>getOTHER_ACTIVITY_EOD_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<STUDENT_OTHER_ACTIVITY_EOD_STATUS>getSTUDENT_OTHER_ACTIVITY_EOD_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<STUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR>getSTUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<STUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY>getSTUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<FEE_EOD_STATUS>getFEE_EOD_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<FEE_EOD_STATUS_ERROR>getFEE_EOD_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<FEE_EOD_STATUS_HISTORY>getFEE_EOD_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<STUDENT_FEE_EOD_STATUS>getSTUDENT_FEE_EOD_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<STUDENT_FEE_EOD_STATUS_ERROR>getSTUDENT_FEE_EOD_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<STUDENT_FEE_EOD_STATUS_HISTORY>getSTUDENT_FEE_EOD_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<NOTIFICATION_EOD_STATUS>getNOTIFICATION_EOD_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<NOTIFICATION_EOD_STATUS_ERROR>getNOTIFICATION_EOD_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<NOTIFICATION_EOD_STATUS_HISTORY>getNOTIFICATION_EOD_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<STUDENT_NOTIFICATION_EOD_STATUS>getSTUDENT_NOTIFICATION_EOD_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<STUDENT_NOTIFICATION_EOD_STATUS_ERROR>getSTUDENT_NOTIFICATION_EOD_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<STUDENT_NOTIFICATION_EOD_STATUS_HISTORY>getSTUDENT_NOTIFICATION_EOD_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<TIMETABLE_BATCH_STATUS>getTIMETABLE_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<TIMETABLE_BATCH_STATUS_ERROR>getTIMETABLE_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<TIMETABLE_BATCH_STATUS_HISTORY>getTIMETABLE_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<STUDENT_TIMETABLE_BATCH_STATUS>getSTUDENT_TIMETABLE_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<STUDENT_TIMETABLE_BATCH_STATUS_ERROR>getSTUDENT_TIMETABLE_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<STUDENT_TIMETABLE_BATCH_STATUS_HISTORY>getSTUDENT_TIMETABLE_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<ATTENDANCE_BATCH_STATUS>getATTENDANCE_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<ATTENDANCE_BATCH_STATUS_ERROR>getATTENDANCE_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<ATTENDANCE_BATCH_STATUS_HISTORY>getATTENDANCE_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<STUDENT_ATTENDANCE_BATCH_STATUS>getSTUDENT_ATTENDANCE_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<STUDENT_ATTENDANCE_BATCH_STATUS_ERROR>getSTUDENT_ATTENDANCE_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<STUDENT_ATTENDANCE_BATCH_STATUS_HISTORY>getSTUDENT_ATTENDANCE_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<EXAM_BATCH_STATUS>getEXAM_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<EXAM_BATCH_STATUS_ERROR>getEXAM_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<EXAM_BATCH_STATUS_HISTORY>getEXAM_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<STUDENT_EXAM_BATCH_STATUS>getSTUDENT_EXAM_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<STUDENT_EXAM_BATCH_STATUS_ERROR>getSTUDENT_EXAM_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<STUDENT_EXAM_BATCH_STATUS_HISTORY>getSTUDENT_EXAM_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<MARK_BATCH_STATUS>getMARK_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<MARK_BATCH_STATUS_ERROR>getMARK_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
//    public ArrayList<MARK_BATCH_STATUS_HISTORY>getMARK_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<STUDENT_MARK_BATCH_STATUS>getSTUDENT_MARK_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<STUDENT_MARK_BATCH_STATUS_ERROR>getSTUDENT_MARK_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<STUDENT_MARK_BATCH_STATUS_HISTORY>getSTUDENT_MARK_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS>getSTUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY>getSTUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR>getSTUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS>getINSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY>getINSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public ArrayList<INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR>getINSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    
    
    public String getAPP_EOD_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public String  getAPP_EOD_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public String  getAPP_EOD_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public String  getBATCH_STATUS_DataSet(String p_businessDate,String p_instituteID,String batchName)throws DBProcessingException,DBValidationException;
    public String  getBATCH_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID,String batchName)throws DBProcessingException,DBValidationException;
    public String  getBATCH_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID,String batchName)throws DBProcessingException,DBValidationException;
    public String  getINSTITUTE_EOD_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public String  getINSTITUTE_EOD_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public String  getINSTITUTE_EOD_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException;
    public String  getNOTIFICATION_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String  getNOTIFICATION_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String  getNOTIFICATION_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String  getSTUDENT_NOTIFICATION_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String  getSTUDENT_NOTIFICATION_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String  getSTUDENT_NOTIFICATION_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getSTUDENT_NOTIFICATION_EMAIL_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getSTUDENT_NOTIFICATION_SMS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;


    public String getFEE_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getFEE_NOTIFICATION_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;

    public String getFEE_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
   
    public String getFEE_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getFEE_NOTIFICATION_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getFEE_NOTIFICATION_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getSTUDENT_FEE_NOTIFICATION_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;

    public String getSTUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    
    public String getSTUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    
    
    public String getSTUDENT_FEE_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getSTUDENT_FEE_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getSTUDENT_FEE_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;


    public String getOTHER_ACTIVITY_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getOTHER_ACTIVITY_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getOTHER_ACTIVITY_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getSTUDENT_OTHER_ACTIVITY_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getSTUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getSTUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    
    public String getASSIGNMENT_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getASSIGNMENT_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getASSIGNMENT_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getSTUDENT_ASSIGNMENT_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getSTUDENT_ASSIGNMENT_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getSTUDENT_ASSIGNMENT_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    
    public String getSTUDENT_EVENT_NOTIFICATION_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;

public String getSTUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;


public String getSTUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    
    
    
    
    public String getMARK_BATCH_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getMARK_BATCH_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getMARK_BATCH_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;


    public String getE_CIRCULAR_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getE_CIRCULAR_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getE_CIRCULAR_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getSTUDENT_E_CIRCULAR_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getSTUDENT_E_CIRCULAR_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getSTUDENT_E_CIRCULAR_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    
    public String getTEACHER_E_CIRCULAR_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getTEACHER_E_CIRCULAR_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;
    public String getTEACHER_E_CIRCULAR_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException;







}
