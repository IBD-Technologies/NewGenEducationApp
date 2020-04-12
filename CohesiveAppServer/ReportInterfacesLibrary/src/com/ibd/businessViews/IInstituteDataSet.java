/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.report.dbreport.dataModels.institute.CLASS_LEAVE_MANAGEMENT;
import com.ibd.cohesive.report.dbreport.dataModels.institute.FEE_BATCH_INDICATOR;
import com.ibd.cohesive.report.dbreport.dataModels.institute.INSTITITUTE_FEE_PAYMENT;
import com.ibd.cohesive.report.dbreport.dataModels.institute.INSTITUTE_CURRENT_DATE;
import com.ibd.cohesive.report.dbreport.dataModels.institute.INSTITUTE_OTHER_ACTIVITY_TRACKING;
import com.ibd.cohesive.report.dbreport.dataModels.institute.INSTITUTE_OTP_STATUS;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_CONTENT_TYPE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_E_CIRCULAR;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_FEE_TYPE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_GROUP_MAPPING_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_GROUP_MAPPING_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_HOLIDAY_MAINTANENCE;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_INSTITUTE_EXAM_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_LEAVE_TYPE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_NOTIFICATION_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_NOTIFICATION_TYPE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_PERIOD_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_SKILL_GRADE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_SKILL_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_SOFT_SKILL_CONFIGURATION_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_STANDARD_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_STUDENT_LEAVE_DETAILS;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_STUDENT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_SUBJECT_GRADE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_SUBJECT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_TEACHER_LEAVE_DETAILS;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_TEACHER_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_UNAUTH_RECORDS;
import com.ibd.cohesive.report.dbreport.dataModels.institute.NOTIFICATION_BATCH_INDICATOR;
import com.ibd.cohesive.report.dbreport.dataModels.institute.OTHER_ACTIVITY_BATCH_INDICATOR;
import com.ibd.cohesive.report.dbreport.dataModels.institute.PAYMENT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.STUDENT_ASSIGNMENT_STATUS;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author IBD Technologies
 */

@Remote
public interface IInstituteDataSet {
    public String getIVW_CONTENT_TYPE_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getIVW_FEE_TYPE_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getIVW_INSTITUTE_EXAM_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getIVW_LEAVE_TYPE_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getIVW_NOTIFICATION_TYPE_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;
     
    public String getIVW_PERIOD_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getIVW_STANDARD_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getIVW_STUDENT_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getIVW_SUBJECT_GRADE_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getIVW_SUBJECT_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getIVW_TEACHER_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getIVW_STANDARD_MASTER_DataSet(String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;

    
    public String getSTUDENT_ASSIGNMENT_STATUS_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;

    public String getRETENTION_PERIOD_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;

    public String getPAYMENT_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;

    public String getOTHER_ACTIVITY_BATCH_INDICATOR_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;

    public String getNOTIFICATION_BATCH_INDICATOR_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;

    public String getIVW_UNAUTH_RECORDS_DataSet(String p_instanceID,String p_date)throws DBProcessingException,DBValidationException;

    public String getIVW_TEACHER_LEAVE_DETAILS_DataSet(String p_instanceID,String p_date)throws DBProcessingException,DBValidationException;

    public String getIVW_STUDENT_LEAVE_DETAILS_DataSet(String p_instanceID,String p_date)throws DBProcessingException,DBValidationException;

    public String getIVW_SOFT_SKILL_CONFIGURATION_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getIVW_SKILL_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getIVW_SKILL_GRADE_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;

    public String getIVW_NOTIFICATION_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;

    public String getIVW_HOLIDAY_MAINTANENCE_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;

    public String getIVW_GROUP_MAPPING_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;

    public String getIVW_GROUP_MAPPING_DETAIL_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getIVW_E_CIRCULAR_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getINSTITUTE_OTP_STATUS_DataSet(String p_instanceID,String p_date)throws DBProcessingException,DBValidationException;
 
    public String getCLASS_LEAVE_MANAGEMENT_DataSet(String p_instanceID,String p_standard,String p_section)throws DBProcessingException,DBValidationException;

    public String getFEE_BATCH_INDICATOR_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;

    
    public String getINSTITITUTE_FEE_PAYMENT_DataSet(String p_instanceID,String p_feeID)throws DBProcessingException,DBValidationException;
    
    public String getINSTITUTE_CURRENT_DATE_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getINSTITUTE_OTHER_ACTIVITY_TRACKING_DataSet(String p_instanceID,String p_activityID)throws DBProcessingException,DBValidationException;
    
    public String getTODAY_NOTIFICATION_DataSet(String p_instanceID,String p_date)throws DBProcessingException,DBValidationException;











}
