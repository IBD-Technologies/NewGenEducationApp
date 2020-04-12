/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.report.businessreport.dataModels.student.StudentRank;
import com.ibd.cohesive.report.dbreport.dataModels.student.STUDENT_NOTIFICATION_STATUS;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_CONTACT_PERSON_DETAILS;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_FAMILY_DETAILS;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_ASSIGNMENT;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_ATTENDANCE;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_CALENDER;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_EXAM_SCHEDULE_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_EXAM_SCHEDULE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_E_CIRCULAR;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_FEE_MANAGEMENT;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_LEAVE_MANAGEMENT;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_MARKS;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_NOTIFICATION;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_OTHER_ACTIVITY;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_OTHER_ACTIVITY_REPORT;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_PAYMENT;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_PROFILE;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_PRORESS_CARD;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_TIMETABLE_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_TIMETABLE_MASTER;
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
public interface IStudentDataSet {
    
    public String getSVW_CONTACT_PERSON_DETAILS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;

    public String getSVW_FAMILY_DETAILS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_ASSIGNMENT_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_ATTENDANCE_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_ATTENDANCE_DataSet(String p_fileName,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_CALENDER_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_EXAM_SCHEDULE_DETAIL_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_EXAM_SCHEDULE_MASTER_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_FEE_MANAGEMENT_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_LEAVE_MANAGEMENT_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_MARKS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_OTHER_ACTIVITY_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_OTHER_ACTIVITY_DataSet(String p_fileName,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_PAYMENT_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_PROFILE_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_PRORESS_CARD_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_TIMETABLE_DETAIL_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_TIMETABLE_MASTER_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_FEE_MANAGEMENT_DataSet(String p_fileName,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_PAYMENT_DataSet(String p_fileName,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;
    
    public String getSTUDENT_NOTIFICATION_STATUS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_E_CIRCULAR_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSVW_STUDENT_NOTIFICATION_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;

    public String getSVW_STUDENT_OTHER_ACTIVITY_REPORT_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;

    public String getSTUDENT_OTP_STATUS_DataSet(String p_instanceID,String p_studentID)throws DBProcessingException,DBValidationException;
   
}
