/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.core.metadata;
import com.ibd.cohesive.util.IBDProperties;
import java.io.IOException;
/**
 *
 * @author IBD Technologies

***********Change History******** ****************

* Created by :Suriya
  Created-on:17-02-2018
  Create-Phase:cohesive1_Development
  Description: This Java file contains configuration of the database

* Changed by :Isac
  Changed-on:17-08-2018
  Change-Reason:Development
  Change-Description: New column Remarks is added to the table IVW_INSTITUTE_PROFILE.
  Search Tag: Cohesive1_Dev_1
  
  
 *  Change Description :Changing names of rolemapping tables
 *  Changed by   :Isacmanojkumar
 *  Changed on   :27/02/2019
 *  Search Tag   :Cohesive1_UnitTest_4
 
 *  Change Description :Including student id in UVW_PARENT_STUDENT_ROLEMAPPING
 *  Changed by   :Isacmanojkumar
 *  Changed on   :27/02/2019
 *  Search Tag   :Cohesive1_UnitTest_5

*  Change Description :changing primary key of UVW_PARENT_STUDENT_ROLEMAPPING
 *  Changed by   :Isacmanojkumar
 *  Changed on   :27/02/2019
 *  Search Tag   :Cohesive1_UnitTest_6
 * 
 *  Change Description :changing columns of UVW_USER_ROLE_DETAIL
 *  Changed by   :Isacmanojkumar
 *  Changed on   :27/02/2019
 *  Search Tag   :Cohesive1_UnitTest_8
*/
public class DBConstants {

    
    
    
    
    
    
    
     public static  int FILE_NO_OF_PROPERTY;//Each file has 4 properties filename,fileid
    
    public static  int TABLE_NO_OF_PROPERTY; //Each Table has 6 properties TableName,Tableid,primary key etc.,

    public static  int COLUMN_NO_OF_PROPERTY ; //Each column has 5 properties Name,id,datatype,length,relationship
    public static  String[][] FILE_DETAILS; 
    public static  String[][] ARCH        ;
    public static  String[][] BATCH;
    public static  String[][] FEE;
    public static  String[][] PAYMENT;
    public static  String[][] ASSIGNMENT;
    public static  String[][] OTHER_ACTIVITY;
    public static  String[][] STUDENT_ASSIGNMENT_STATUS;
    public static  String[][] INSTITITUTE_FEE_PAYMENT;        
    public static  String[][] BATCH_CONFIG;
    public static  String[][] BATCH_STATUS;
    public static  String[][] BATCH_STATUS_HISTORY;
    public static  String[][] BATCH_STATUS_ERROR;
    public static  String[][] INSTITUTE_EOD_STATUS;
    public static  String[][] INSTITUTE_EOD_STATUS_HISTORY;
    public static  String[][] INSTITUTE_EOD_STATUS_ERROR;
    public static  String[][] APP_EOD_STATUS;
    public static  String[][] APP_EOD_STATUS_HISTORY;
    public static  String[][] APP_EOD_STATUS_ERROR;
    public static  String[][] ASSIGNMENT_EOD_STATUS;
    public static  String[][] ASSIGNMENT_EOD_STATUS_HISTORY;
    public static  String[][] ASSIGNMENT_EOD_STATUS_ERROR;
    public static  String[][] STUDENT_ASSIGNMENT_EOD_STATUS;
    public static  String[][] STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY;
    public static  String[][] STUDENT_ASSIGNMENT_EOD_STATUS_ERROR;
    public static  String[][] OTHER_ACTIVITY_EOD_STATUS;
    public static  String[][] OTHER_ACTIVITY_EOD_STATUS_HISTORY;
    public static  String[][] OTHER_ACTIVITY_EOD_STATUS_ERROR;
    public static  String[][] STUDENT_OTHER_ACTIVITY_EOD_STATUS;
    public static  String[][] STUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY;
    public static  String[][] STUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR;
    public static  String[][] FEE_EOD_STATUS;
    public static  String[][] FEE_EOD_STATUS_HISTORY;
    public static  String[][] FEE_EOD_STATUS_ERROR;
    public static  String[][] STUDENT_FEE_EOD_STATUS;
    public static  String[][] STUDENT_FEE_EOD_STATUS_HISTORY;
    public static  String[][] STUDENT_FEE_EOD_STATUS_ERROR;
    public static  String[][] NOTIFICATION_EOD_STATUS;
    public static  String[][] NOTIFICATION_EOD_STATUS_HISTORY;
    public static  String[][] NOTIFICATION_EOD_STATUS_ERROR;
    public static  String[][] STUDENT_NOTIFICATION_EOD_STATUS;
    public static  String[][] STUDENT_NOTIFICATION_EOD_STATUS_HISTORY;
    public static  String[][] STUDENT_NOTIFICATION_EOD_STATUS_ERROR;
    public static  String[][] TIMETABLE_BATCH_STATUS;
    public static  String[][] TIMETABLE_BATCH_STATUS_HISTORY;
    public static  String[][] TIMETABLE_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_TIMETABLE_BATCH_STATUS;
    public static  String[][] STUDENT_TIMETABLE_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_TIMETABLE_BATCH_STATUS_ERROR;
    public static  String[][] EXAM_BATCH_STATUS;
    public static  String[][] EXAM_BATCH_STATUS_HISTORY;
    public static  String[][] EXAM_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_EXAM_BATCH_STATUS;
    public static  String[][] STUDENT_EXAM_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_EXAM_BATCH_STATUS_ERROR;
    public static  String[][] MARK_BATCH_STATUS;
    public static  String[][] MARK_BATCH_STATUS_HISTORY;
    public static  String[][] MARK_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_MARK_BATCH_STATUS;
    public static  String[][] STUDENT_MARK_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_MARK_BATCH_STATUS_ERROR;
    public static  String[][] ATTENDANCE_BATCH_STATUS;
    public static  String[][] ATTENDANCE_BATCH_STATUS_HISTORY;
    public static  String[][] ATTENDANCE_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_ATTENDANCE_BATCH_STATUS;
    public static  String[][] STUDENT_ATTENDANCE_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_ATTENDANCE_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS;
    public static  String[][] STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS;
    public static  String[][] INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS;
    public static  String[][] STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS;
    public static  String[][] INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_FEE_ARCH_BATCH_STATUS;
    public static  String[][] STUDENT_FEE_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_FEE_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] INSTITUTE_FEE_ARCH_BATCH_STATUS;
    public static  String[][] INSTITUTE_FEE_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] INSTITUTE_FEE_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_NOTIFICATION_ARCH_BATCH_STATUS;
    public static  String[][] STUDENT_NOTIFICATION_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_NOTIFICATION_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS;
    public static  String[][] INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_MARK_ARCH_BATCH_STATUS;
    public static  String[][] STUDENT_MARK_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_MARK_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] CLASS_MARK_ARCH_BATCH_STATUS;
    public static  String[][] CLASS_MARK_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] CLASS_MARK_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_EXAM_ARCH_BATCH_STATUS;
    public static  String[][] STUDENT_EXAM_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_EXAM_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] CLASS_EXAM_ARCH_BATCH_STATUS;
    public static  String[][] CLASS_EXAM_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] CLASS_EXAM_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_TIME_TABLE_ARCH_BATCH_STATUS;
    public static  String[][] STUDENT_TIME_TABLE_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_TIME_TABLE_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] CLASS_TIME_TABLE_ARCH_BATCH_STATUS;
    public static  String[][] CLASS_TIME_TABLE_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] CLASS_TIME_TABLE_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_ATTENDANCE_ARCH_BATCH_STATUS;
    public static  String[][] STUDENT_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_ATTENDANCE_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] CLASS_ATTENDANCE_ARCH_BATCH_STATUS;
    public static  String[][] CLASS_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] CLASS_ATTENDANCE_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_LEAVE_ARCH_BATCH_STATUS;
    public static  String[][] STUDENT_LEAVE_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_LEAVE_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] TEACHER_LEAVE_ARCH_BATCH_STATUS;
    public static  String[][] TEACHER_LEAVE_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] TEACHER_LEAVE_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_CALENDER_ARCH_BATCH_STATUS;
    public static  String[][] STUDENT_CALENDER_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_CALENDER_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] TEACHER_CALENDER_ARCH_BATCH_STATUS;
    public static  String[][] TEACHER_CALENDER_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] TEACHER_CALENDER_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] TEACHER_PAYROLL_ARCH_BATCH_STATUS;
    public static  String[][] TEACHER_PAYROLL_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] TEACHER_PAYROLL_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_PAYMENT_ARCH_BATCH_STATUS;
    public static  String[][] STUDENT_PAYMENT_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_PAYMENT_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] INSTITUTE_PAYMENT_ARCH_BATCH_STATUS;
    public static  String[][] INSTITUTE_PAYMENT_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] INSTITUTE_PAYMENT_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] TEACHER_ATTENDANCE_ARCH_BATCH_STATUS;
    public static  String[][] TEACHER_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] TEACHER_ATTENDANCE_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] TEACHER_TIME_TABLE_ARCH_BATCH_STATUS;
    public static  String[][] TEACHER_TIME_TABLE_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] TEACHER_TIME_TABLE_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_PROFILE_ARCH_BATCH_STATUS;
    public static  String[][] STUDENT_PROFILE_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_PROFILE_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] TEACHER_PROFILE_ARCH_BATCH_STATUS;
    public static  String[][] TEACHER_PROFILE_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] TEACHER_PROFILE_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] USER_PROFILE_ARCH_BATCH_STATUS;
    public static  String[][] USER_PROFILE_ARCH_BATCH_STATUS_HISTORY;
    public static  String[][] USER_PROFILE_ARCH_BATCH_STATUS_ERROR;
    public static  String[][] APP;
    public static  String[][] ERROR_MASTER;
    public static  String[][] INSTITUTE_MASTER;
    public static  String[][] SERVICE_TYPE_MASTER;
    public static  String[][] CLASS;
    public static  String[][] CLASS_MARK_ENTRY;
    public static  String[][] STUDENT_MARKS;
    public static  String[][] CLASS_TIMETABLE_MASTER;
    public static  String[][] CLASS_TIMETABLE_DETAIL;
    public static  String[][] CLASS_ATTENDANCE_MASTER;
    public static  String[][] CLASS_ATTENDANCE_DETAIL;
    public static  String[][] CLASS_ASSIGNMENT;
    public static  String[][] CLASS_FEE_MANAGEMENT;
    public static  String[][] CLASS_EXAM_SCHEDULE_MASTER;
    public static  String[][] CLASS_EXAM_SCHEDULE_DETAIL;
    public static  String[][] CLASS_STUDENT_MAPPING;
    public static  String[][] STUDENT;
    public static  String[][] SVW_STUDENT_PROFILE;
    public static  String[][] SVW_EXISTING_MEDICAL_DETAILS;
    public static  String[][] SVW_CONTACT_PERSON_DETAILS;
    public static  String[][] SVW_FAMILY_DETAILS;
    public static  String[][] SVW_STUDENT_ATTENDANCE;
    public static  String[][] SVW_STUDENT_MARKS;
    public static  String[][] SVW_STUDENT_PRORESS_CARD;
    public static  String[][] SVW_STUDENT_LEAVE_MANAGEMENT;
    public static  String[][] SVW_STUDENT_ASSIGNMENT;
    public static  String[][] SVW_TEACHER_PROFILE;
    public static  String[][] SVW_USER_PROFILE_MAPPING;
    public static  String[][] SVW_STUDENT_TIMETABLE_MASTER;
    public static  String[][] SVW_STUDENT_TIMETABLE_DETAIL;
    public static  String[][] STUDENT_FEE_MANAGEMENT;
    public static  String[][] STUDENT_PAYMENT;
    public static  String[][] SVW_STUDENT_OTHER_ACTIVITY;
    public static  String[][] SVW_STUDENT_EXAM_SCHEDULE_MASTER;
    public static  String[][] SVW_STUDENT_EXAM_SCHEDULE_DETAIL;
    public static  String[][] SVW_STUDENT_CALENDER;
    public static  String[][] SVW_STUDENT_NOTIFICATION;
    public static  String[][] SVW_STUDENT_LOGIN_MAPPING;
    public static  String[][] USER;
    public static  String[][] UVW_USER_MASTER;
    public static  String[][] UVW_USER_PROFILE_MAPPING;
    public static  String[][] UVW_STUDENT_PROFILE;
    public static  String[][] UVW_USER_PROFILE;
    public static  String[][] UVW_USER_CREDENTIALS;
    public static  String[][] UVW_IN_LOG;
    public static  String[][] UVW_OUT_LOG;
    public static  String[][] UVW_USER_ROLE_MASTER;
    public static  String[][] UVW_USER_ROLE_DETAIL;
    public static  String[][] UVW_USER_ROLE_MAPPING;
    public static  String[][] UVW_PARENT_STUDENT_ROLEMAPPING;
    public static  String[][] UVW_CLASS_ENTITY_ROLEMAPPING;
    public static  String[][] UVW_TEACHER_ENTITY_ROLEMAPPING;
    public static  String[][] UVW_INSTITUTE_ENTITY_ROLEMAPPING;
    public static  String[][] TEACHER;
    public static  String[][] TVW_TEACHER_PROFILE;
    public static  String[][] TVW_TEACHER_EXAM_DETAILS;
    public static  String[][] TVW_TEACHER_TIMETABLE_MASTER;
    public static  String[][] TVW_TEACHER_TIMETABLE_DETAIL;
    public static  String[][] TVW_EXISTING_MEDICAL_DETAILS;
    public static  String[][] TVW_CONTACT_PERSON_DETAILS;
    public static  String[][] TVW_TEACHER_CALENDER;
    public static  String[][] TVW_TEACHER_ATTENDANCE;
    public static  String[][] TVW_TEACHER_LEAVE_MANAGEMENT;
    public static  String[][] TVW_PAYROLL;
    public static  String[][] TVW_INSTITUTE_PROFILE;
    public static  String[][] MANAGEMENT;
    public static  String[][] MVW_MANAGEMENT_EXAM_DETAILS;
    public static  String[][] MVW_STUDENT_PROFILE;
    public static  String[][] MVW_TEACHER_PROFILE;
    public static  String[][] INSTITUTE;
    public static  String[][] IVW_INSTITUTE_PROFILE;
    public static  String[][] IVW_INSTITUTE_EXAM_DETAILS;
    public static  String[][] IVW_INSTITUTE_EXAM_MASTER;
    public static  String[][] IVW_STANDARD_MASTER;
    public static  String[][] IVW_SUBJECT_DETAILS;
    public static  String[][] IVW_SUBJECT_MASTER;
    public static  String[][] IVW_SUBJECT_GRADE_MASTER;
    public static  String[][] IVW_TEACHER_MASTER;
    public static  String[][] IVW_STUDENT_MASTER;
    public static  String[][] IVW_STUDENT_LOGIN_MAPPING;
    public static  String[][] IVW_PERIOD_MASTER;
    public static  String[][] IVW_CONTENT_TYPE_MASTER;
    public static  String[][] IVW_LEAVE_TYPE_MASTER;
    public static  String[][] IVW_FEE_TYPE_MASTER;
    public static  String[][] IVW_NOTIFICATION_TYPE_MASTER;
    public static  String[][] IVW_STUDENT_PROFILE;
    public static  String[][] IVW_CLASS_LEVEL_CONFIGURATION_MASTER;
    public static  String[][] IVW_HOLIDAY_MAINTANENCE;
    public static  String[][] IVW_NOTIFICATION_MASTER;
    public static  String[][] INSTITUTE_PAYMENT;
    public static  String[][] IVW_GROUP_MAPPING_MASTER;
    public static  String[][] IVW_GROUP_MAPPING_DETAIL;
    public static  String[][] IVW_OTHER_ACTIVITY;
    public static  String[][] INSTITUTE_FEE_MANAGEMENT;
    public static  String[][] IVW_ASSIGNMENT;
    public static  String[][] INSTITUTE_CURRENT_DATE;
    public static  String[][] RETENTION_PERIOD;
    public static  String[][] APP_BATCH_STATUS;
    public static  String[][] APP_BATCH_STATUS_HISTORY;
    public static  String[][] APP_BATCH_STATUS_ERROR;
    public static  String[][] APP_RETENTION_PERIOD;
    public static  String[][] DEFRAGMENTATION_BATCH_STATUS;
    public static  String[][] DEFRAGMENTATION_BATCH_STATUS_HISTORY;
    public static  String[][] DEFRAGMENTATION_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_ATTENDANCE_REPORT_BATCH_STATUS;
    public static  String[][] STUDENT_ATTENDANCE_REPORT_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_ATTENDANCE_REPORT_BATCH_STATUS_ERROR;
    public static  String[][] STUDENT_OTHER_ACTIVITY_REPORT_BATCH_STATUS;
    public static  String[][] STUDENT_OTHER_ACTIVITY_REPORT_BATCH_STATUS_HISTORY;
    public static  String[][] STUDENT_OTHER_ACTIVITY_REPORT_BATCH_STATUS_ERROR;
    public static  String[][] SVW_STUDENT_ATTENDANCE_REPORT;
    public static  String[][] SVW_STUDENT_OTHER_ACTIVITY_REPORT;
    public static  String[][] CLASS_ATTENDANCE_REPORT_BATCH_STATUS;
    public static  String[][] CLASS_ATTENDANCE_REPORT_BATCH_STATUS_HISTORY;
    public static  String[][] CLASS_ATTENDANCE_REPORT_BATCH_STATUS_ERROR;
    public static  String[][] CLASS_ATTENDANCE_REPORT;
    public static  String[][] CLASS_OTHER_ACTIVITY_REPORT_BATCH_STATUS;
    public static  String[][] CLASS_OTHER_ACTIVITY_REPORT_BATCH_STATUS_HISTORY;
    public static  String[][] CLASS_OTHER_ACTIVITY_REPORT_BATCH_STATUS_ERROR;
    public static  String[][] CLASS_OTHER_ACTIVITY_REPORT;    
    public static  String[][] CLASS_FEE_AMOUNT_REPORT_BATCH_STATUS;
    public static  String[][] CLASS_FEE_AMOUNT_REPORT_BATCH_STATUS_HISTORY;
    public static  String[][] CLASS_FEE_AMOUNT_REPORT_BATCH_STATUS_ERROR;
    public static  String[][] CLASS_FEE_AMOUNT_REPORT;
    public static  String[][] CLASS_FEE_STATUS_REPORT_BATCH_STATUS;
    public static  String[][] CLASS_FEE_STATUS_REPORT_BATCH_STATUS_HISTORY;
    public static  String[][] CLASS_FEE_STATUS_REPORT_BATCH_STATUS_ERROR;
    public static  String[][] CLASS_FEE_STATUS_REPORT;
    public static  String[][] CLASS_MARK_REPORT_BATCH_STATUS;
    public static  String[][] CLASS_MARK_REPORT_BATCH_STATUS_HISTORY;
    public static  String[][] CLASS_MARK_REPORT_BATCH_STATUS_ERROR;
    public static  String[][] CLASS_MARK_REPORT;
    public static  String[][] CLASS_GRADE_REPORT_BATCH_STATUS;
    public static  String[][] CLASS_GRADE_REPORT_BATCH_STATUS_HISTORY;
    public static  String[][] CLASS_GRADE_REPORT_BATCH_STATUS_ERROR;
    public static  String[][] CLASS_GRADE_REPORT;
    public static  String[][] TEACHER_ATTENDANCE_REPORT_BATCH_STATUS;
    public static  String[][] TEACHER_ATTENDANCE_REPORT_BATCH_STATUS_HISTORY;
    public static  String[][] TEACHER_ATTENDANCE_REPORT_BATCH_STATUS_ERROR;
    public static  String[][] TVW_TEACHER_ATTENDANCE_REPORT;
    public static  String[][] TEACHER_MARK_REPORT_BATCH_STATUS;
    public static  String[][] TEACHER_MARK_REPORT_BATCH_STATUS_HISTORY;
    public static  String[][] TEACHER_MARK_REPORT_BATCH_STATUS_ERROR;
    public static  String[][] TVW_TEACHER_MARK_REPORT;
    public static  String[][] TEACHER_GRADE_REPORT_BATCH_STATUS;
    public static  String[][] TEACHER_GRADE_REPORT_BATCH_STATUS_HISTORY;
    public static  String[][] TEACHER_GRADE_REPORT_BATCH_STATUS_ERROR;
    public static  String[][] TVW_TEACHER_GRADE_REPORT;
    public static  String[][] BATCH_SERVICES;
    public static  String[][] IVW_E_CIRCULAR;
    public static  String[][] E_CIRCULAR_EOD_STATUS;
    public static  String[][] E_CIRCULAR_EOD_STATUS_HISTORY;
    public static  String[][] E_CIRCULAR_EOD_STATUS_ERROR;
    public static  String[][] STUDENT_E_CIRCULAR_EOD_STATUS;
    public static  String[][] STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY;
    public static  String[][] STUDENT_E_CIRCULAR_EOD_STATUS_ERROR;
    public static  String[][] SVW_STUDENT_E_CIRCULAR;  
    public static  String[][] ARCH_SHIPPING_STATUS;  
    public static  String[][] ARCH_APPLY_STATUS;  
    public static  String[][] ARCHIVAL_RECOVERY_BATCH_STATUS;
    public static  String[][] ARCHIVAL_RECOVERY_BATCH_STATUS_HISTORY;
    public static  String[][] ARCHIVAL_RECOVERY_BATCH_STATUS_ERROR;
    public static  String[][] UVW_USER_CONTRACT_MASTER;
    public static  String[][] UVW_USER_ROLE_INSTITUTE;
    public static  String[][] CONTRACT_MASTER;
    public static  String[][] STUDENT_NOTIFICATION_EMAIL_ERROR;
    public static  String[][] STUDENT_NOTIFICATION_SMS_ERROR;
    public static  String[][] STUDENT_NOTIFICATION_STATUS;
    public static  String[][] FEE_BATCH_INDICATOR;        
    public static  String[][] NOTIFICATION_BATCH_INDICATOR;
    public static  String[][] PAYMENT_MASTER;
    public static  String[][] INSTITUTE_OTHER_ACTIVITY_TRACKING;     
    public static  String[][] OTHER_ACTIVITY_BATCH_INDICATOR;  
    public static  String[][] LEAVE;
    public static  String[][] CLASS_LEAVE_MANAGEMENT;
    public static  String[][] APP_SUPPORT;
    public static  String[][] CLASS_EXAM_RANK;
    public static  String[][] IVW_UNAUTH_RECORDS;
    public static  String[][] IVW_STUDENT_LEAVE_DETAILS;
    public static  String[][] IVW_TEACHER_LEAVE_DETAILS;
    public static  String[][] STUDENT_OTP_STATUS;
    public static  String[][] INSTITUTE_OTP_STATUS;
    public static  String[][] IVW_SOFT_SKILL_CONFIGURATION_MASTER;
    public static  String[][] IVW_SKILL_MASTER;
    public static  String[][] IVW_SKILL_GRADE_MASTER;
    public static  String[][] CLASS_SKILL_ENTRY;
    public static  String[][] STUDENT_SKILLS;
    public static  String[][] OTP;        
    public static  String[][] REPORT;        
    public static  String[][] SUBSTITUTE_AVAIL_SAME_CLASS;        
    public static  String[][] SMS_TERMS_TRANSALATOR;
    public static  String[][] FEE_NOTIFICATION_EOD_STATUS;
    public static  String[][] FEE_NOTIFICATION_EOD_STATUS_HISTORY;
    public static  String[][] FEE_NOTIFICATION_EOD_STATUS_ERROR;
    public static  String[][] STUDENT_FEE_NOTIFICATION_EOD_STATUS;
    public static  String[][] STUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY;
    public static  String[][] STUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR;
    public static  String[][] TODAY_NOTIFICATION;
    public static  String[][] STUDENT_EVENT_NOTIFICATION_EOD_STATUS;
    public static  String[][] STUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY;
    public static  String[][] STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR;
    public static  String[][]  STUDENT_PROGRESS_CARD_SIGNATURE;
    public static  String[][]  UVW_TEACHER_ID_MAPPING;
    public static  String[][]  CLASS_ATTENDANCE_MASTER_HISTORY;
    public static  String[][]  CLASS_ATTENDANCE_DETAIL_HISTORY;
    public static  String[][] STUDENT_SOFT_SKILL_SIGNATURE;
    public static  String[][] UVW_USER_OTP_STATUS;
    public static  String[][] TVW_TEACHER_E_CIRCULAR;
    public static  String[][] TEACHER_E_CIRCULAR_EOD_STATUS;
    public static  String[][] TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY;
    public static  String[][] TEACHER_E_CIRCULAR_EOD_STATUS_ERROR;
    public static  String[][] IVW_STUDENT_E_CIRCULAR_SIGNATURE;
    public static  String[][] IVW_TEACHER_E_CIRCULAR_SIGNATURE;
    public static  String[][] IVW_INSITUTE_OTHER_DETAILS;
    public static  String[][] INSTITUTE_PAYMENT_MASTER;
    public static  String[][] STUDENT_NOTIFICATION_MESSAGE;
    public static  String[][] IVW_MESSAGE_TEMPLATE_MASTER;
    public static  String[][] IVW_MESSAGE_TEMPLATE_DETAIL;
    
    public static void setDBConstants() throws IOException{
        
        try{
            IBDProperties ibdproperties=new IBDProperties();
            ibdproperties.loadProperties("TableProperties.properties");
        
            FILE_NO_OF_PROPERTY=Integer.parseInt(ibdproperties.getProperty("FILE_NO_OF_PROPERTY"));
            
            
            TABLE_NO_OF_PROPERTY=Integer.parseInt(ibdproperties.getProperty("TABLE_NO_OF_PROPERTY")); 

            COLUMN_NO_OF_PROPERTY =Integer.parseInt(ibdproperties.getProperty("COLUMN_NO_OF_PROPERTY")); 
            
            
            FILE_DETAILS=getArray("FILE_DETAILS",ibdproperties);    
            ARCH=getArray("ARCH",ibdproperties);    
            BATCH=getArray("BATCH",ibdproperties);
            FEE=getArray("FEE",ibdproperties);    
            PAYMENT=getArray("PAYMENT",ibdproperties);
	    INSTITITUTE_FEE_PAYMENT=getArray("INSTITITUTE_FEE_PAYMENT",ibdproperties);
            BATCH_CONFIG=getArray("BATCH_CONFIG",ibdproperties);
            BATCH_STATUS=getArray("BATCH_STATUS",ibdproperties);
            BATCH_STATUS_HISTORY=getArray("BATCH_STATUS_HISTORY",ibdproperties);
            BATCH_STATUS_ERROR=getArray("BATCH_STATUS_ERROR",ibdproperties);
            INSTITUTE_EOD_STATUS=getArray("INSTITUTE_EOD_STATUS",ibdproperties);
            INSTITUTE_EOD_STATUS_HISTORY=getArray("INSTITUTE_EOD_STATUS_HISTORY",ibdproperties);
            INSTITUTE_EOD_STATUS_ERROR=getArray("INSTITUTE_EOD_STATUS_ERROR",ibdproperties);
            APP_EOD_STATUS=getArray("APP_EOD_STATUS",ibdproperties);
            APP_EOD_STATUS_HISTORY=getArray("APP_EOD_STATUS_HISTORY",ibdproperties);
            APP_EOD_STATUS_ERROR=getArray("APP_EOD_STATUS_ERROR",ibdproperties);
            ASSIGNMENT_EOD_STATUS=getArray("ASSIGNMENT_EOD_STATUS",ibdproperties);
            ASSIGNMENT_EOD_STATUS_HISTORY=getArray("ASSIGNMENT_EOD_STATUS_HISTORY",ibdproperties);
            ASSIGNMENT_EOD_STATUS_ERROR=getArray("ASSIGNMENT_EOD_STATUS_ERROR",ibdproperties);
            STUDENT_ASSIGNMENT_EOD_STATUS=getArray("STUDENT_ASSIGNMENT_EOD_STATUS",ibdproperties);
            STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY=getArray("STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY",ibdproperties);
            STUDENT_ASSIGNMENT_EOD_STATUS_ERROR=getArray("STUDENT_ASSIGNMENT_EOD_STATUS_ERROR",ibdproperties);
            OTHER_ACTIVITY_EOD_STATUS=getArray("OTHER_ACTIVITY_EOD_STATUS",ibdproperties);
            OTHER_ACTIVITY_EOD_STATUS_HISTORY=getArray("OTHER_ACTIVITY_EOD_STATUS_HISTORY",ibdproperties);
            OTHER_ACTIVITY_EOD_STATUS_ERROR=getArray("OTHER_ACTIVITY_EOD_STATUS_ERROR",ibdproperties);
            STUDENT_OTHER_ACTIVITY_EOD_STATUS=getArray("STUDENT_OTHER_ACTIVITY_EOD_STATUS",ibdproperties);
            STUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY=getArray("STUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY",ibdproperties);
            STUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR=getArray("STUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR",ibdproperties);
            FEE_EOD_STATUS=getArray("FEE_EOD_STATUS",ibdproperties);
            FEE_EOD_STATUS_HISTORY=getArray("FEE_EOD_STATUS_HISTORY",ibdproperties);
            FEE_EOD_STATUS_ERROR=getArray("FEE_EOD_STATUS_ERROR",ibdproperties);
            STUDENT_FEE_EOD_STATUS=getArray("STUDENT_FEE_EOD_STATUS",ibdproperties);
            STUDENT_FEE_EOD_STATUS_HISTORY=getArray("STUDENT_FEE_EOD_STATUS_HISTORY",ibdproperties);
            STUDENT_FEE_EOD_STATUS_ERROR=getArray("STUDENT_FEE_EOD_STATUS_ERROR",ibdproperties);
            NOTIFICATION_EOD_STATUS=getArray("NOTIFICATION_EOD_STATUS",ibdproperties);
            NOTIFICATION_EOD_STATUS_HISTORY=getArray("NOTIFICATION_EOD_STATUS_HISTORY",ibdproperties);
            NOTIFICATION_EOD_STATUS_ERROR=getArray("NOTIFICATION_EOD_STATUS_ERROR",ibdproperties);
            STUDENT_NOTIFICATION_EOD_STATUS=getArray("STUDENT_NOTIFICATION_EOD_STATUS",ibdproperties);
            STUDENT_NOTIFICATION_EOD_STATUS_HISTORY=getArray("STUDENT_NOTIFICATION_EOD_STATUS_HISTORY",ibdproperties);
            STUDENT_NOTIFICATION_EOD_STATUS_ERROR=getArray("STUDENT_NOTIFICATION_EOD_STATUS_ERROR",ibdproperties);
            TIMETABLE_BATCH_STATUS=getArray("TIMETABLE_BATCH_STATUS",ibdproperties);
            TIMETABLE_BATCH_STATUS_HISTORY=getArray("TIMETABLE_BATCH_STATUS_HISTORY",ibdproperties);
            TIMETABLE_BATCH_STATUS_ERROR=getArray("TIMETABLE_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_TIMETABLE_BATCH_STATUS=getArray("STUDENT_TIMETABLE_BATCH_STATUS",ibdproperties);
            STUDENT_TIMETABLE_BATCH_STATUS_HISTORY=getArray("STUDENT_TIMETABLE_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_TIMETABLE_BATCH_STATUS_ERROR=getArray("STUDENT_TIMETABLE_BATCH_STATUS_ERROR",ibdproperties);
            EXAM_BATCH_STATUS=getArray("EXAM_BATCH_STATUS",ibdproperties);
            EXAM_BATCH_STATUS_HISTORY=getArray("EXAM_BATCH_STATUS_HISTORY",ibdproperties);
            EXAM_BATCH_STATUS_ERROR=getArray("EXAM_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_EXAM_BATCH_STATUS=getArray("EXAM_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_EXAM_BATCH_STATUS_HISTORY=getArray("STUDENT_EXAM_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_EXAM_BATCH_STATUS_ERROR=getArray("STUDENT_EXAM_BATCH_STATUS_ERROR",ibdproperties);
            MARK_BATCH_STATUS=getArray("MARK_BATCH_STATUS",ibdproperties);
            MARK_BATCH_STATUS_HISTORY=getArray("MARK_BATCH_STATUS_HISTORY",ibdproperties);
            MARK_BATCH_STATUS_ERROR=getArray("MARK_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_MARK_BATCH_STATUS=getArray("STUDENT_MARK_BATCH_STATUS",ibdproperties);
            STUDENT_MARK_BATCH_STATUS_HISTORY=getArray("STUDENT_MARK_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_MARK_BATCH_STATUS_ERROR=getArray("STUDENT_MARK_BATCH_STATUS_ERROR",ibdproperties);
            ATTENDANCE_BATCH_STATUS=getArray("ATTENDANCE_BATCH_STATUS",ibdproperties);
            ATTENDANCE_BATCH_STATUS_HISTORY=getArray("ATTENDANCE_BATCH_STATUS_HISTORY",ibdproperties);
            ATTENDANCE_BATCH_STATUS_ERROR=getArray("ATTENDANCE_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_ATTENDANCE_BATCH_STATUS=getArray("STUDENT_ATTENDANCE_BATCH_STATUS",ibdproperties);
            STUDENT_ATTENDANCE_BATCH_STATUS_HISTORY=getArray("STUDENT_ATTENDANCE_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_ATTENDANCE_BATCH_STATUS_ERROR=getArray("STUDENT_ATTENDANCE_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS=getArray("STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS",ibdproperties);
            STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY=getArray("STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR=getArray("STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS=getArray("INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS",ibdproperties);
            INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY=getArray("INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR=getArray("INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS=getArray("STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS",ibdproperties);
            STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS_HISTORY=getArray("STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS_ERROR=getArray("STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS=getArray("INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS",ibdproperties);
            INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS_HISTORY=getArray("INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS_ERROR=getArray("INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_FEE_ARCH_BATCH_STATUS=getArray("STUDENT_FEE_ARCH_BATCH_STATUS",ibdproperties);
            STUDENT_FEE_ARCH_BATCH_STATUS_HISTORY=getArray("STUDENT_FEE_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_FEE_ARCH_BATCH_STATUS_ERROR=getArray("STUDENT_FEE_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            INSTITUTE_FEE_ARCH_BATCH_STATUS=getArray("INSTITUTE_FEE_ARCH_BATCH_STATUS",ibdproperties);
            INSTITUTE_FEE_ARCH_BATCH_STATUS_HISTORY=getArray("INSTITUTE_FEE_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            INSTITUTE_FEE_ARCH_BATCH_STATUS_ERROR=getArray("INSTITUTE_FEE_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_NOTIFICATION_ARCH_BATCH_STATUS=getArray("STUDENT_NOTIFICATION_ARCH_BATCH_STATUS",ibdproperties);
            STUDENT_NOTIFICATION_ARCH_BATCH_STATUS_HISTORY=getArray("STUDENT_NOTIFICATION_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_NOTIFICATION_ARCH_BATCH_STATUS_ERROR=getArray("STUDENT_NOTIFICATION_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS=getArray("INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS",ibdproperties);
            INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS_HISTORY=getArray("INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS_ERROR=getArray("INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_MARK_ARCH_BATCH_STATUS=getArray("STUDENT_MARK_ARCH_BATCH_STATUS",ibdproperties);
            STUDENT_MARK_ARCH_BATCH_STATUS_HISTORY=getArray("STUDENT_MARK_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_MARK_ARCH_BATCH_STATUS_ERROR=getArray("STUDENT_MARK_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            CLASS_MARK_ARCH_BATCH_STATUS=getArray("CLASS_MARK_ARCH_BATCH_STATUS",ibdproperties);
            CLASS_MARK_ARCH_BATCH_STATUS_HISTORY=getArray("CLASS_MARK_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            CLASS_MARK_ARCH_BATCH_STATUS_ERROR=getArray("CLASS_MARK_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_EXAM_ARCH_BATCH_STATUS=getArray("STUDENT_EXAM_ARCH_BATCH_STATUS",ibdproperties);
            STUDENT_EXAM_ARCH_BATCH_STATUS_HISTORY=getArray("STUDENT_EXAM_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_EXAM_ARCH_BATCH_STATUS_ERROR=getArray("STUDENT_EXAM_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            CLASS_EXAM_ARCH_BATCH_STATUS=getArray("CLASS_EXAM_ARCH_BATCH_STATUS",ibdproperties);
            CLASS_EXAM_ARCH_BATCH_STATUS_HISTORY=getArray("CLASS_EXAM_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            CLASS_EXAM_ARCH_BATCH_STATUS_ERROR=getArray("CLASS_EXAM_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_TIME_TABLE_ARCH_BATCH_STATUS=getArray("STUDENT_TIME_TABLE_ARCH_BATCH_STATUS",ibdproperties);
            STUDENT_TIME_TABLE_ARCH_BATCH_STATUS_HISTORY=getArray("STUDENT_TIME_TABLE_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_TIME_TABLE_ARCH_BATCH_STATUS_ERROR=getArray("STUDENT_TIME_TABLE_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            CLASS_TIME_TABLE_ARCH_BATCH_STATUS=getArray("CLASS_TIME_TABLE_ARCH_BATCH_STATUS",ibdproperties);
            CLASS_TIME_TABLE_ARCH_BATCH_STATUS_HISTORY=getArray("CLASS_TIME_TABLE_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            CLASS_TIME_TABLE_ARCH_BATCH_STATUS_ERROR=getArray("CLASS_TIME_TABLE_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_ATTENDANCE_ARCH_BATCH_STATUS=getArray("STUDENT_ATTENDANCE_ARCH_BATCH_STATUS",ibdproperties);
            STUDENT_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY=getArray("STUDENT_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_ATTENDANCE_ARCH_BATCH_STATUS_ERROR=getArray("STUDENT_ATTENDANCE_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            CLASS_ATTENDANCE_ARCH_BATCH_STATUS=getArray("CLASS_ATTENDANCE_ARCH_BATCH_STATUS",ibdproperties);
            CLASS_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY=getArray("CLASS_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            CLASS_ATTENDANCE_ARCH_BATCH_STATUS_ERROR=getArray("CLASS_ATTENDANCE_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_LEAVE_ARCH_BATCH_STATUS=getArray("STUDENT_LEAVE_ARCH_BATCH_STATUS",ibdproperties);
            STUDENT_LEAVE_ARCH_BATCH_STATUS_HISTORY=getArray("STUDENT_LEAVE_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_LEAVE_ARCH_BATCH_STATUS_ERROR=getArray("STUDENT_LEAVE_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            TEACHER_LEAVE_ARCH_BATCH_STATUS=getArray("TEACHER_LEAVE_ARCH_BATCH_STATUS",ibdproperties);
            TEACHER_LEAVE_ARCH_BATCH_STATUS_HISTORY=getArray("TEACHER_LEAVE_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            TEACHER_LEAVE_ARCH_BATCH_STATUS_ERROR=getArray("TEACHER_LEAVE_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_CALENDER_ARCH_BATCH_STATUS=getArray("STUDENT_CALENDER_ARCH_BATCH_STATUS",ibdproperties);
            STUDENT_CALENDER_ARCH_BATCH_STATUS_HISTORY=getArray("STUDENT_CALENDER_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_CALENDER_ARCH_BATCH_STATUS_ERROR=getArray("STUDENT_CALENDER_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            TEACHER_CALENDER_ARCH_BATCH_STATUS=getArray("TEACHER_CALENDER_ARCH_BATCH_STATUS",ibdproperties);
            TEACHER_CALENDER_ARCH_BATCH_STATUS_HISTORY=getArray("TEACHER_CALENDER_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            TEACHER_CALENDER_ARCH_BATCH_STATUS_ERROR=getArray("TEACHER_CALENDER_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            TEACHER_PAYROLL_ARCH_BATCH_STATUS=getArray("TEACHER_PAYROLL_ARCH_BATCH_STATUS",ibdproperties);
            TEACHER_PAYROLL_ARCH_BATCH_STATUS_HISTORY=getArray("TEACHER_PAYROLL_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            TEACHER_PAYROLL_ARCH_BATCH_STATUS_ERROR=getArray("TEACHER_PAYROLL_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_PAYMENT_ARCH_BATCH_STATUS=getArray("STUDENT_PAYMENT_ARCH_BATCH_STATUS",ibdproperties);
            STUDENT_PAYMENT_ARCH_BATCH_STATUS_HISTORY=getArray("STUDENT_PAYMENT_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_PAYMENT_ARCH_BATCH_STATUS_ERROR=getArray("STUDENT_PAYMENT_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            INSTITUTE_PAYMENT_ARCH_BATCH_STATUS=getArray("INSTITUTE_PAYMENT_ARCH_BATCH_STATUS",ibdproperties);
            INSTITUTE_PAYMENT_ARCH_BATCH_STATUS_HISTORY=getArray("INSTITUTE_PAYMENT_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            INSTITUTE_PAYMENT_ARCH_BATCH_STATUS_ERROR=getArray("INSTITUTE_PAYMENT_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            TEACHER_ATTENDANCE_ARCH_BATCH_STATUS=getArray("TEACHER_ATTENDANCE_ARCH_BATCH_STATUS",ibdproperties);
            TEACHER_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY=getArray("TEACHER_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            TEACHER_ATTENDANCE_ARCH_BATCH_STATUS_ERROR=getArray("TEACHER_ATTENDANCE_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            TEACHER_TIME_TABLE_ARCH_BATCH_STATUS=getArray("TEACHER_TIME_TABLE_ARCH_BATCH_STATUS",ibdproperties);
            TEACHER_TIME_TABLE_ARCH_BATCH_STATUS_HISTORY=getArray("STUDENT_ATTENDANCE_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            TEACHER_TIME_TABLE_ARCH_BATCH_STATUS_ERROR=getArray("STUDENT_ATTENDANCE_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_PROFILE_ARCH_BATCH_STATUS=getArray("STUDENT_PROFILE_ARCH_BATCH_STATUS",ibdproperties);
            STUDENT_PROFILE_ARCH_BATCH_STATUS_HISTORY=getArray("STUDENT_PROFILE_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_PROFILE_ARCH_BATCH_STATUS_ERROR=getArray("STUDENT_PROFILE_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            TEACHER_PROFILE_ARCH_BATCH_STATUS=getArray("TEACHER_PROFILE_ARCH_BATCH_STATUS",ibdproperties);
            TEACHER_PROFILE_ARCH_BATCH_STATUS_HISTORY=getArray("TEACHER_PROFILE_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            TEACHER_PROFILE_ARCH_BATCH_STATUS_ERROR=getArray("TEACHER_PROFILE_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            USER_PROFILE_ARCH_BATCH_STATUS=getArray("USER_PROFILE_ARCH_BATCH_STATUS",ibdproperties);
            USER_PROFILE_ARCH_BATCH_STATUS_HISTORY=getArray("USER_PROFILE_ARCH_BATCH_STATUS_HISTORY",ibdproperties);
            USER_PROFILE_ARCH_BATCH_STATUS_ERROR=getArray("USER_PROFILE_ARCH_BATCH_STATUS_ERROR",ibdproperties);
            APP=getArray("APP",ibdproperties);
            ERROR_MASTER=getArray("ERROR_MASTER",ibdproperties);
            INSTITUTE_MASTER=getArray("INSTITUTE_MASTER",ibdproperties);
            SERVICE_TYPE_MASTER=getArray("SERVICE_TYPE_MASTER",ibdproperties);
            CLASS=getArray("CLASS",ibdproperties);
            CLASS_MARK_ENTRY=getArray("CLASS_MARK_ENTRY",ibdproperties);
            STUDENT_MARKS=getArray("STUDENT_MARKS",ibdproperties);
            CLASS_TIMETABLE_MASTER=getArray("CLASS_TIMETABLE_MASTER",ibdproperties);
            CLASS_TIMETABLE_DETAIL=getArray("CLASS_TIMETABLE_DETAIL",ibdproperties);
            CLASS_ATTENDANCE_MASTER=getArray("CLASS_ATTENDANCE_MASTER",ibdproperties);
            CLASS_ATTENDANCE_DETAIL=getArray("CLASS_ATTENDANCE_DETAIL",ibdproperties);
            CLASS_ASSIGNMENT=getArray("CLASS_ASSIGNMENT",ibdproperties);
            CLASS_FEE_MANAGEMENT=getArray("CLASS_FEE_MANAGEMENT",ibdproperties);
            CLASS_EXAM_SCHEDULE_MASTER=getArray("CLASS_EXAM_SCHEDULE_MASTER",ibdproperties);
            CLASS_EXAM_SCHEDULE_DETAIL=getArray("CLASS_EXAM_SCHEDULE_DETAIL",ibdproperties);
            CLASS_STUDENT_MAPPING=getArray("CLASS_STUDENT_MAPPING",ibdproperties);
            STUDENT=getArray("STUDENT",ibdproperties);
            SVW_STUDENT_PROFILE=getArray("SVW_STUDENT_PROFILE",ibdproperties);
            SVW_EXISTING_MEDICAL_DETAILS=getArray("SVW_EXISTING_MEDICAL_DETAILS",ibdproperties);
            SVW_CONTACT_PERSON_DETAILS=getArray("SVW_CONTACT_PERSON_DETAILS",ibdproperties);
            SVW_FAMILY_DETAILS=getArray("SVW_FAMILY_DETAILS",ibdproperties);
            SVW_STUDENT_ATTENDANCE=getArray("SVW_STUDENT_ATTENDANCE",ibdproperties);
            SVW_STUDENT_MARKS=getArray("SVW_STUDENT_MARKS",ibdproperties);
            SVW_STUDENT_PRORESS_CARD=getArray("SVW_STUDENT_PRORESS_CARD",ibdproperties);
            SVW_STUDENT_LEAVE_MANAGEMENT=getArray("SVW_STUDENT_LEAVE_MANAGEMENT",ibdproperties);
            SVW_STUDENT_ASSIGNMENT=getArray("SVW_STUDENT_ASSIGNMENT",ibdproperties);
            SVW_TEACHER_PROFILE=getArray("SVW_TEACHER_PROFILE",ibdproperties);
            SVW_USER_PROFILE_MAPPING=getArray("SVW_USER_PROFILE_MAPPING",ibdproperties);
            SVW_STUDENT_TIMETABLE_MASTER=getArray("SVW_STUDENT_TIMETABLE_MASTER",ibdproperties);
            SVW_STUDENT_TIMETABLE_DETAIL=getArray("SVW_STUDENT_TIMETABLE_DETAIL",ibdproperties);
            STUDENT_FEE_MANAGEMENT=getArray("STUDENT_FEE_MANAGEMENT",ibdproperties);
            STUDENT_PAYMENT=getArray("STUDENT_PAYMENT",ibdproperties);
            SVW_STUDENT_OTHER_ACTIVITY=getArray("SVW_STUDENT_OTHER_ACTIVITY",ibdproperties);
            SVW_STUDENT_EXAM_SCHEDULE_MASTER=getArray("SVW_STUDENT_EXAM_SCHEDULE_MASTER",ibdproperties);
            SVW_STUDENT_EXAM_SCHEDULE_DETAIL=getArray("SVW_STUDENT_EXAM_SCHEDULE_DETAIL",ibdproperties);
            SVW_STUDENT_CALENDER=getArray("SVW_STUDENT_CALENDER",ibdproperties);
            SVW_STUDENT_NOTIFICATION=getArray("SVW_STUDENT_NOTIFICATION",ibdproperties);
            SVW_STUDENT_LOGIN_MAPPING=getArray("SVW_STUDENT_LOGIN_MAPPING",ibdproperties);
            USER=getArray("USER",ibdproperties);
            UVW_USER_MASTER=getArray("UVW_USER_MASTER",ibdproperties);
            UVW_USER_PROFILE_MAPPING=getArray("UVW_USER_PROFILE_MAPPING",ibdproperties);
            UVW_STUDENT_PROFILE=getArray("UVW_STUDENT_PROFILE",ibdproperties);
            UVW_USER_PROFILE=getArray("UVW_USER_PROFILE",ibdproperties);
            UVW_USER_CREDENTIALS=getArray("UVW_USER_CREDENTIALS",ibdproperties);
            UVW_IN_LOG=getArray("UVW_IN_LOG",ibdproperties);
            UVW_OUT_LOG=getArray("UVW_OUT_LOG",ibdproperties);
            UVW_USER_ROLE_MASTER=getArray("UVW_USER_ROLE_MASTER",ibdproperties);
            UVW_USER_ROLE_DETAIL=getArray("UVW_USER_ROLE_DETAIL",ibdproperties);
            UVW_USER_ROLE_MAPPING=getArray("UVW_USER_ROLE_MAPPING",ibdproperties);
            UVW_PARENT_STUDENT_ROLEMAPPING=getArray("UVW_PARENT_STUDENT_ROLEMAPPING",ibdproperties);
            UVW_CLASS_ENTITY_ROLEMAPPING=getArray("UVW_CLASS_ENTITY_ROLEMAPPING",ibdproperties);
            UVW_TEACHER_ENTITY_ROLEMAPPING=getArray("UVW_TEACHER_ENTITY_ROLEMAPPING",ibdproperties);
            UVW_INSTITUTE_ENTITY_ROLEMAPPING=getArray("UVW_INSTITUTE_ENTITY_ROLEMAPPING",ibdproperties);
            TEACHER=getArray("TEACHER",ibdproperties);
            TVW_TEACHER_PROFILE=getArray("TVW_TEACHER_PROFILE",ibdproperties);
            TVW_TEACHER_EXAM_DETAILS=getArray("TVW_TEACHER_EXAM_DETAILS",ibdproperties);
            TVW_TEACHER_TIMETABLE_MASTER=getArray("TVW_TEACHER_TIMETABLE_MASTER",ibdproperties);
            TVW_TEACHER_TIMETABLE_DETAIL=getArray("TVW_TEACHER_TIMETABLE_DETAIL",ibdproperties);
            TVW_EXISTING_MEDICAL_DETAILS=getArray("TVW_EXISTING_MEDICAL_DETAILS",ibdproperties);
            TVW_CONTACT_PERSON_DETAILS=getArray("TVW_CONTACT_PERSON_DETAILS",ibdproperties);
            TVW_TEACHER_CALENDER=getArray("TVW_TEACHER_CALENDER",ibdproperties);
            TVW_TEACHER_ATTENDANCE=getArray("TVW_TEACHER_ATTENDANCE",ibdproperties);
            TVW_TEACHER_LEAVE_MANAGEMENT=getArray("TVW_TEACHER_LEAVE_MANAGEMENT",ibdproperties);
            TVW_PAYROLL=getArray("TVW_PAYROLL",ibdproperties);
            TVW_INSTITUTE_PROFILE=getArray("TVW_INSTITUTE_PROFILE",ibdproperties);
            MANAGEMENT=getArray("MANAGEMENT",ibdproperties);
            MVW_MANAGEMENT_EXAM_DETAILS=getArray("MVW_MANAGEMENT_EXAM_DETAILS",ibdproperties);
            MVW_STUDENT_PROFILE=getArray("MVW_STUDENT_PROFILE",ibdproperties);
            MVW_TEACHER_PROFILE=getArray("MVW_TEACHER_PROFILE",ibdproperties);
            INSTITUTE=getArray("INSTITUTE",ibdproperties);
            IVW_INSTITUTE_PROFILE=getArray("IVW_INSTITUTE_PROFILE",ibdproperties);
            IVW_INSTITUTE_EXAM_DETAILS=getArray("IVW_INSTITUTE_EXAM_DETAILS",ibdproperties);
            IVW_INSTITUTE_EXAM_MASTER=getArray("IVW_INSTITUTE_EXAM_MASTER",ibdproperties);
            IVW_STANDARD_MASTER=getArray("IVW_STANDARD_MASTER",ibdproperties);
            IVW_SUBJECT_DETAILS=getArray("IVW_SUBJECT_DETAILS",ibdproperties);
            IVW_SUBJECT_MASTER=getArray("IVW_SUBJECT_MASTER",ibdproperties);
            IVW_SUBJECT_GRADE_MASTER=getArray("IVW_SUBJECT_GRADE_MASTER",ibdproperties);
            IVW_TEACHER_MASTER=getArray("IVW_TEACHER_MASTER",ibdproperties);
            IVW_STUDENT_MASTER=getArray("IVW_STUDENT_MASTER",ibdproperties);
            IVW_STUDENT_LOGIN_MAPPING=getArray("IVW_STUDENT_LOGIN_MAPPING",ibdproperties);
            IVW_PERIOD_MASTER=getArray("IVW_PERIOD_MASTER",ibdproperties);
            IVW_CONTENT_TYPE_MASTER=getArray("IVW_CONTENT_TYPE_MASTER",ibdproperties);
            IVW_LEAVE_TYPE_MASTER=getArray("IVW_LEAVE_TYPE_MASTER",ibdproperties);
            IVW_FEE_TYPE_MASTER=getArray("IVW_FEE_TYPE_MASTER",ibdproperties);
            IVW_NOTIFICATION_TYPE_MASTER=getArray("IVW_NOTIFICATION_TYPE_MASTER",ibdproperties);
            IVW_STUDENT_PROFILE=getArray("IVW_STUDENT_PROFILE",ibdproperties);
            IVW_CLASS_LEVEL_CONFIGURATION_MASTER=getArray("IVW_CLASS_LEVEL_CONFIGURATION_MASTER",ibdproperties);
            IVW_HOLIDAY_MAINTANENCE=getArray("IVW_HOLIDAY_MAINTANENCE",ibdproperties);
            IVW_NOTIFICATION_MASTER=getArray("IVW_NOTIFICATION_MASTER",ibdproperties);
            INSTITUTE_PAYMENT=getArray("INSTITUTE_PAYMENT",ibdproperties);
            IVW_GROUP_MAPPING_MASTER=getArray("IVW_GROUP_MAPPING_MASTER",ibdproperties);
            IVW_GROUP_MAPPING_DETAIL=getArray("IVW_GROUP_MAPPING_DETAIL",ibdproperties);
            IVW_OTHER_ACTIVITY=getArray("IVW_OTHER_ACTIVITY",ibdproperties);
            INSTITUTE_FEE_MANAGEMENT=getArray("INSTITUTE_FEE_MANAGEMENT",ibdproperties);
            IVW_ASSIGNMENT=getArray("IVW_ASSIGNMENT",ibdproperties);
            INSTITUTE_CURRENT_DATE=getArray("INSTITUTE_CURRENT_DATE",ibdproperties);
            RETENTION_PERIOD=getArray("RETENTION_PERIOD",ibdproperties);
            APP_BATCH_STATUS=getArray("APP_BATCH_STATUS",ibdproperties);
            APP_BATCH_STATUS_HISTORY=getArray("APP_BATCH_STATUS_HISTORY",ibdproperties);
            APP_BATCH_STATUS_ERROR=getArray("APP_BATCH_STATUS_ERROR",ibdproperties);
            APP_RETENTION_PERIOD=getArray("APP_RETENTION_PERIOD",ibdproperties);
            DEFRAGMENTATION_BATCH_STATUS=getArray("DEFRAGMENTATION_BATCH_STATUS",ibdproperties);
            DEFRAGMENTATION_BATCH_STATUS_HISTORY=getArray("DEFRAGMENTATION_BATCH_STATUS_HISTORY",ibdproperties);
            DEFRAGMENTATION_BATCH_STATUS_ERROR=getArray("DEFRAGMENTATION_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_ATTENDANCE_REPORT_BATCH_STATUS=getArray("STUDENT_ATTENDANCE_REPORT_BATCH_STATUS",ibdproperties);
            STUDENT_ATTENDANCE_REPORT_BATCH_STATUS_HISTORY=getArray("STUDENT_ATTENDANCE_REPORT_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_ATTENDANCE_REPORT_BATCH_STATUS_ERROR=getArray("STUDENT_ATTENDANCE_REPORT_BATCH_STATUS_ERROR",ibdproperties);
            STUDENT_OTHER_ACTIVITY_REPORT_BATCH_STATUS=getArray("STUDENT_OTHER_ACTIVITY_REPORT_BATCH_STATUS",ibdproperties);
            STUDENT_OTHER_ACTIVITY_REPORT_BATCH_STATUS_HISTORY=getArray("STUDENT_OTHER_ACTIVITY_REPORT_BATCH_STATUS_HISTORY",ibdproperties);
            STUDENT_OTHER_ACTIVITY_REPORT_BATCH_STATUS_ERROR=getArray("STUDENT_OTHER_ACTIVITY_REPORT_BATCH_STATUS_ERROR",ibdproperties);
            SVW_STUDENT_ATTENDANCE_REPORT=getArray("SVW_STUDENT_ATTENDANCE_REPORT",ibdproperties);
            SVW_STUDENT_OTHER_ACTIVITY_REPORT=getArray("SVW_STUDENT_OTHER_ACTIVITY_REPORT",ibdproperties);
            CLASS_ATTENDANCE_REPORT_BATCH_STATUS=getArray("CLASS_ATTENDANCE_REPORT_BATCH_STATUS",ibdproperties);
            CLASS_ATTENDANCE_REPORT_BATCH_STATUS_HISTORY=getArray("CLASS_ATTENDANCE_REPORT_BATCH_STATUS_HISTORY",ibdproperties);
            CLASS_ATTENDANCE_REPORT_BATCH_STATUS_ERROR=getArray("CLASS_ATTENDANCE_REPORT_BATCH_STATUS_ERROR",ibdproperties);
            CLASS_ATTENDANCE_REPORT=getArray("CLASS_ATTENDANCE_REPORT",ibdproperties);
            CLASS_OTHER_ACTIVITY_REPORT_BATCH_STATUS=getArray("CLASS_OTHER_ACTIVITY_REPORT_BATCH_STATUS",ibdproperties);
            CLASS_OTHER_ACTIVITY_REPORT_BATCH_STATUS_HISTORY=getArray("CLASS_OTHER_ACTIVITY_REPORT_BATCH_STATUS_HISTORY",ibdproperties);
            CLASS_OTHER_ACTIVITY_REPORT_BATCH_STATUS_ERROR=getArray("CLASS_OTHER_ACTIVITY_REPORT_BATCH_STATUS_ERROR",ibdproperties);
            CLASS_OTHER_ACTIVITY_REPORT=getArray("CLASS_OTHER_ACTIVITY_REPORT",ibdproperties);
            CLASS_FEE_AMOUNT_REPORT_BATCH_STATUS=getArray("CLASS_FEE_AMOUNT_REPORT_BATCH_STATUS",ibdproperties);
            CLASS_FEE_AMOUNT_REPORT_BATCH_STATUS_HISTORY=getArray("CLASS_FEE_AMOUNT_REPORT_BATCH_STATUS_HISTORY",ibdproperties);
            CLASS_FEE_AMOUNT_REPORT_BATCH_STATUS_ERROR=getArray("CLASS_FEE_AMOUNT_REPORT_BATCH_STATUS_ERROR",ibdproperties);
            CLASS_FEE_AMOUNT_REPORT=getArray("CLASS_FEE_AMOUNT_REPORT",ibdproperties);
            CLASS_FEE_STATUS_REPORT_BATCH_STATUS=getArray("CLASS_FEE_STATUS_REPORT_BATCH_STATUS",ibdproperties);
            CLASS_FEE_STATUS_REPORT_BATCH_STATUS_HISTORY=getArray("CLASS_FEE_STATUS_REPORT_BATCH_STATUS_HISTORY",ibdproperties);
            CLASS_FEE_STATUS_REPORT_BATCH_STATUS_ERROR=getArray("CLASS_FEE_STATUS_REPORT_BATCH_STATUS_ERROR",ibdproperties);
            CLASS_FEE_STATUS_REPORT=getArray("CLASS_FEE_STATUS_REPORT",ibdproperties);
            CLASS_MARK_REPORT_BATCH_STATUS=getArray("CLASS_MARK_REPORT_BATCH_STATUS",ibdproperties);
            CLASS_MARK_REPORT_BATCH_STATUS_HISTORY=getArray("CLASS_MARK_REPORT_BATCH_STATUS_HISTORY",ibdproperties);
            CLASS_MARK_REPORT_BATCH_STATUS_ERROR=getArray("CLASS_MARK_REPORT_BATCH_STATUS_ERROR",ibdproperties);
            CLASS_MARK_REPORT=getArray("CLASS_MARK_REPORT",ibdproperties);
            CLASS_GRADE_REPORT_BATCH_STATUS=getArray("CLASS_GRADE_REPORT_BATCH_STATUS",ibdproperties);
            CLASS_GRADE_REPORT_BATCH_STATUS_HISTORY=getArray("CLASS_GRADE_REPORT_BATCH_STATUS_HISTORY",ibdproperties);
            CLASS_GRADE_REPORT_BATCH_STATUS_ERROR=getArray("CLASS_GRADE_REPORT_BATCH_STATUS_ERROR",ibdproperties);
            CLASS_GRADE_REPORT=getArray("CLASS_GRADE_REPORT",ibdproperties);
            TEACHER_ATTENDANCE_REPORT_BATCH_STATUS=getArray("TEACHER_ATTENDANCE_REPORT_BATCH_STATUS",ibdproperties);
            TEACHER_ATTENDANCE_REPORT_BATCH_STATUS_HISTORY=getArray("TEACHER_ATTENDANCE_REPORT_BATCH_STATUS_HISTORY",ibdproperties);
            TEACHER_ATTENDANCE_REPORT_BATCH_STATUS_ERROR=getArray("TEACHER_ATTENDANCE_REPORT_BATCH_STATUS_ERROR",ibdproperties);
            TVW_TEACHER_ATTENDANCE_REPORT=getArray("TVW_TEACHER_ATTENDANCE_REPORT",ibdproperties);
            TEACHER_MARK_REPORT_BATCH_STATUS=getArray("TEACHER_MARK_REPORT_BATCH_STATUS",ibdproperties);
            TEACHER_MARK_REPORT_BATCH_STATUS_HISTORY=getArray("TEACHER_MARK_REPORT_BATCH_STATUS_HISTORY",ibdproperties);
            TEACHER_MARK_REPORT_BATCH_STATUS_ERROR=getArray("TEACHER_MARK_REPORT_BATCH_STATUS_ERROR",ibdproperties);
            TVW_TEACHER_MARK_REPORT=getArray("TVW_TEACHER_MARK_REPORT",ibdproperties);
            TEACHER_GRADE_REPORT_BATCH_STATUS=getArray("TEACHER_GRADE_REPORT_BATCH_STATUS",ibdproperties);
            TEACHER_GRADE_REPORT_BATCH_STATUS_HISTORY=getArray("TEACHER_GRADE_REPORT_BATCH_STATUS_HISTORY",ibdproperties);
            TEACHER_GRADE_REPORT_BATCH_STATUS_ERROR=getArray("TEACHER_GRADE_REPORT_BATCH_STATUS_ERROR",ibdproperties);
            TVW_TEACHER_GRADE_REPORT=getArray("TVW_TEACHER_GRADE_REPORT",ibdproperties);
            BATCH_SERVICES=getArray("BATCH_SERVICES",ibdproperties);
            IVW_E_CIRCULAR=getArray("IVW_E_CIRCULAR",ibdproperties);
            E_CIRCULAR_EOD_STATUS=getArray("E_CIRCULAR_EOD_STATUS",ibdproperties);
            E_CIRCULAR_EOD_STATUS_HISTORY=getArray("E_CIRCULAR_EOD_STATUS_HISTORY",ibdproperties);
            E_CIRCULAR_EOD_STATUS_ERROR=getArray("E_CIRCULAR_EOD_STATUS_ERROR",ibdproperties);
            STUDENT_E_CIRCULAR_EOD_STATUS=getArray("STUDENT_E_CIRCULAR_EOD_STATUS",ibdproperties);
            STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY=getArray("STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY",ibdproperties);
            STUDENT_E_CIRCULAR_EOD_STATUS_ERROR=getArray("STUDENT_E_CIRCULAR_EOD_STATUS_ERROR",ibdproperties);
            SVW_STUDENT_E_CIRCULAR=getArray("SVW_STUDENT_E_CIRCULAR",ibdproperties);  
            ARCH_SHIPPING_STATUS=getArray("ARCH_SHIPPING_STATUS",ibdproperties);  
            ARCH_APPLY_STATUS=getArray("ARCH_APPLY_STATUS",ibdproperties);  
            ARCHIVAL_RECOVERY_BATCH_STATUS=getArray("ARCHIVAL_RECOVERY_BATCH_STATUS",ibdproperties);  
            ARCHIVAL_RECOVERY_BATCH_STATUS_HISTORY=getArray("ARCHIVAL_RECOVERY_BATCH_STATUS_HISTORY",ibdproperties);  
            ARCHIVAL_RECOVERY_BATCH_STATUS_ERROR=getArray("ARCHIVAL_RECOVERY_BATCH_STATUS_ERROR",ibdproperties);  
            UVW_USER_CONTRACT_MASTER=getArray("UVW_USER_CONTRACT_MASTER",ibdproperties);  
            UVW_USER_ROLE_INSTITUTE=getArray("UVW_USER_ROLE_INSTITUTE",ibdproperties);  
            CONTRACT_MASTER=getArray("CONTRACT_MASTER",ibdproperties);
            STUDENT_NOTIFICATION_EMAIL_ERROR=getArray("STUDENT_NOTIFICATION_EMAIL_ERROR",ibdproperties);
            STUDENT_NOTIFICATION_SMS_ERROR=getArray("STUDENT_NOTIFICATION_SMS_ERROR",ibdproperties);
            STUDENT_NOTIFICATION_STATUS=getArray("STUDENT_NOTIFICATION_STATUS",ibdproperties);
            ASSIGNMENT=getArray("ASSIGNMENT",ibdproperties);
            OTHER_ACTIVITY=getArray("OTHER_ACTIVITY",ibdproperties);
            STUDENT_ASSIGNMENT_STATUS=getArray("STUDENT_ASSIGNMENT_STATUS",ibdproperties);
            FEE_BATCH_INDICATOR=getArray("FEE_BATCH_INDICATOR",ibdproperties);
            NOTIFICATION_BATCH_INDICATOR=getArray("NOTIFICATION_BATCH_INDICATOR",ibdproperties); 
            PAYMENT_MASTER=getArray("PAYMENT_MASTER",ibdproperties); 
            INSTITUTE_OTHER_ACTIVITY_TRACKING=getArray("INSTITUTE_OTHER_ACTIVITY_TRACKING",ibdproperties);
            OTHER_ACTIVITY_BATCH_INDICATOR=getArray("OTHER_ACTIVITY_BATCH_INDICATOR",ibdproperties);
            LEAVE=getArray("LEAVE",ibdproperties);
            CLASS_LEAVE_MANAGEMENT=getArray("CLASS_LEAVE_MANAGEMENT",ibdproperties);
            APP_SUPPORT=getArray("APP_SUPPORT",ibdproperties);
            CLASS_EXAM_RANK=getArray("CLASS_EXAM_RANK",ibdproperties);
            IVW_UNAUTH_RECORDS=getArray("IVW_UNAUTH_RECORDS",ibdproperties);
            IVW_STUDENT_LEAVE_DETAILS=getArray("IVW_STUDENT_LEAVE_DETAILS",ibdproperties);
            STUDENT_OTP_STATUS=getArray("STUDENT_OTP_STATUS",ibdproperties);
            INSTITUTE_OTP_STATUS=getArray("INSTITUTE_OTP_STATUS",ibdproperties);
            IVW_SOFT_SKILL_CONFIGURATION_MASTER=getArray("IVW_SOFT_SKILL_CONFIGURATION_MASTER",ibdproperties);
            IVW_SKILL_MASTER=getArray("IVW_SKILL_MASTER",ibdproperties);
            IVW_SKILL_GRADE_MASTER=getArray("IVW_SKILL_GRADE_MASTER",ibdproperties);
            CLASS_SKILL_ENTRY=getArray("CLASS_SKILL_ENTRY",ibdproperties);
            STUDENT_SKILLS=getArray("STUDENT_SKILLS",ibdproperties);
            IVW_TEACHER_LEAVE_DETAILS=getArray("IVW_TEACHER_LEAVE_DETAILS",ibdproperties);
            OTP=getArray("OTP",ibdproperties);
            REPORT=getArray("REPORT",ibdproperties);        
            SUBSTITUTE_AVAIL_SAME_CLASS=getArray("SUBSTITUTE_AVAIL_SAME_CLASS",ibdproperties);        
            FEE_NOTIFICATION_EOD_STATUS=getArray("FEE_NOTIFICATION_EOD_STATUS",ibdproperties);
            FEE_NOTIFICATION_EOD_STATUS_HISTORY=getArray("FEE_NOTIFICATION_EOD_STATUS_HISTORY",ibdproperties);
            FEE_NOTIFICATION_EOD_STATUS_ERROR=getArray("FEE_NOTIFICATION_EOD_STATUS_ERROR",ibdproperties);
            STUDENT_FEE_NOTIFICATION_EOD_STATUS=getArray("STUDENT_FEE_NOTIFICATION_EOD_STATUS",ibdproperties);
            STUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY=getArray("STUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY",ibdproperties);
            STUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR=getArray("STUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR",ibdproperties);
            STUDENT_EVENT_NOTIFICATION_EOD_STATUS=getArray("STUDENT_EVENT_NOTIFICATION_EOD_STATUS",ibdproperties);
            STUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY=getArray("STUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY",ibdproperties);
            STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR=getArray("STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR",ibdproperties);
            TODAY_NOTIFICATION=getArray("TODAY_NOTIFICATION",ibdproperties);
            SMS_TERMS_TRANSALATOR=getArray("SMS_TERMS_TRANSALATOR",ibdproperties);
            STUDENT_PROGRESS_CARD_SIGNATURE=getArray("STUDENT_PROGRESS_CARD_SIGNATURE",ibdproperties);
            UVW_TEACHER_ID_MAPPING=getArray("UVW_TEACHER_ID_MAPPING",ibdproperties);
            CLASS_ATTENDANCE_MASTER_HISTORY=getArray("CLASS_ATTENDANCE_MASTER_HISTORY",ibdproperties);
            CLASS_ATTENDANCE_DETAIL_HISTORY=getArray("CLASS_ATTENDANCE_DETAIL_HISTORY",ibdproperties);
            STUDENT_SOFT_SKILL_SIGNATURE=getArray("STUDENT_SOFT_SKILL_SIGNATURE",ibdproperties);
            UVW_USER_OTP_STATUS=getArray("UVW_USER_OTP_STATUS",ibdproperties);
            TVW_TEACHER_E_CIRCULAR=getArray("TVW_TEACHER_E_CIRCULAR",ibdproperties);
            TEACHER_E_CIRCULAR_EOD_STATUS=getArray("TEACHER_E_CIRCULAR_EOD_STATUS",ibdproperties);
            TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY=getArray("TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY",ibdproperties);
            TEACHER_E_CIRCULAR_EOD_STATUS_ERROR=getArray("TEACHER_E_CIRCULAR_EOD_STATUS_ERROR",ibdproperties);
            IVW_STUDENT_E_CIRCULAR_SIGNATURE=getArray("IVW_STUDENT_E_CIRCULAR_SIGNATURE",ibdproperties);
            IVW_TEACHER_E_CIRCULAR_SIGNATURE=getArray("IVW_TEACHER_E_CIRCULAR_SIGNATURE",ibdproperties);
            IVW_INSITUTE_OTHER_DETAILS=getArray("IVW_INSITUTE_OTHER_DETAILS",ibdproperties);
            INSTITUTE_PAYMENT_MASTER=getArray("INSTITUTE_PAYMENT_MASTER",ibdproperties);
            STUDENT_NOTIFICATION_MESSAGE=getArray("STUDENT_NOTIFICATION_MESSAGE",ibdproperties);
            IVW_MESSAGE_TEMPLATE_MASTER=getArray("IVW_MESSAGE_TEMPLATE_MASTER",ibdproperties);
            IVW_MESSAGE_TEMPLATE_DETAIL=getArray("IVW_MESSAGE_TEMPLATE_DETAIL",ibdproperties);
       }catch(Exception ex){
            throw ex;
        }    
        
        
    }
    
    
    public static void resetDBConstants(){
        
        try{
    
        FILE_DETAILS=null; 
        BATCH=null;
        BATCH_CONFIG=null;
        BATCH_STATUS=null;
        BATCH_STATUS_HISTORY=null;
        BATCH_STATUS_ERROR=null;
        INSTITUTE_EOD_STATUS=null;
        INSTITUTE_EOD_STATUS_HISTORY=null;
        INSTITUTE_EOD_STATUS_ERROR=null;
        APP_EOD_STATUS=null;
        APP_EOD_STATUS_HISTORY=null;
        APP_EOD_STATUS_ERROR=null;
        ASSIGNMENT_EOD_STATUS=null;
        ASSIGNMENT_EOD_STATUS_HISTORY=null;
        ASSIGNMENT_EOD_STATUS_ERROR=null;
        STUDENT_ASSIGNMENT_EOD_STATUS=null;
        STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY=null;
        STUDENT_ASSIGNMENT_EOD_STATUS_ERROR=null;
        OTHER_ACTIVITY_EOD_STATUS=null;
        OTHER_ACTIVITY_EOD_STATUS_HISTORY=null;
        OTHER_ACTIVITY_EOD_STATUS_ERROR=null;
        STUDENT_OTHER_ACTIVITY_EOD_STATUS=null;
        STUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY=null;
        STUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR=null;
        FEE_EOD_STATUS=null;
        FEE_EOD_STATUS_HISTORY=null;
        FEE_EOD_STATUS_ERROR=null;
        STUDENT_FEE_EOD_STATUS=null;
        STUDENT_FEE_EOD_STATUS_HISTORY=null;
        STUDENT_FEE_EOD_STATUS_ERROR=null;
        NOTIFICATION_EOD_STATUS=null;
        NOTIFICATION_EOD_STATUS_HISTORY=null;
        NOTIFICATION_EOD_STATUS_ERROR=null;
        STUDENT_NOTIFICATION_EOD_STATUS=null;
        STUDENT_NOTIFICATION_EOD_STATUS_HISTORY=null;
        STUDENT_NOTIFICATION_EOD_STATUS_ERROR=null;
        TIMETABLE_BATCH_STATUS=null;
        TIMETABLE_BATCH_STATUS_HISTORY=null;
        TIMETABLE_BATCH_STATUS_ERROR=null;
        STUDENT_TIMETABLE_BATCH_STATUS=null;
        STUDENT_TIMETABLE_BATCH_STATUS_HISTORY=null;
        STUDENT_TIMETABLE_BATCH_STATUS_ERROR=null;
        EXAM_BATCH_STATUS=null;
        EXAM_BATCH_STATUS_HISTORY=null;
        EXAM_BATCH_STATUS_ERROR=null;
        STUDENT_EXAM_BATCH_STATUS=null;
        STUDENT_EXAM_BATCH_STATUS_HISTORY=null;
        STUDENT_EXAM_BATCH_STATUS_ERROR=null;
        MARK_BATCH_STATUS=null;
        MARK_BATCH_STATUS_HISTORY=null;
        MARK_BATCH_STATUS_ERROR=null;
        STUDENT_MARK_BATCH_STATUS=null;
        STUDENT_MARK_BATCH_STATUS_HISTORY=null;
        STUDENT_MARK_BATCH_STATUS_ERROR=null;
        ATTENDANCE_BATCH_STATUS=null;
        ATTENDANCE_BATCH_STATUS_HISTORY=null;
        ATTENDANCE_BATCH_STATUS_ERROR=null;
        STUDENT_ATTENDANCE_BATCH_STATUS=null;
        STUDENT_ATTENDANCE_BATCH_STATUS_HISTORY=null;
        STUDENT_ATTENDANCE_BATCH_STATUS_ERROR=null;
        STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS=null;
        STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY=null;
        STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR=null;
        INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS=null;
        INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY=null;
        INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR=null;
        STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS=null;
        STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS_HISTORY=null;
        STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS_ERROR=null;
        INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS=null;
        INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS_HISTORY=null;
        INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS_ERROR=null;
        STUDENT_FEE_ARCH_BATCH_STATUS=null;
        STUDENT_FEE_ARCH_BATCH_STATUS_HISTORY=null;
        STUDENT_FEE_ARCH_BATCH_STATUS_ERROR=null;
        INSTITUTE_FEE_ARCH_BATCH_STATUS=null;
        INSTITUTE_FEE_ARCH_BATCH_STATUS_HISTORY=null;
        INSTITUTE_FEE_ARCH_BATCH_STATUS_ERROR=null;
        STUDENT_NOTIFICATION_ARCH_BATCH_STATUS=null;
        STUDENT_NOTIFICATION_ARCH_BATCH_STATUS_HISTORY=null;
        STUDENT_NOTIFICATION_ARCH_BATCH_STATUS_ERROR=null;
        INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS=null;
        INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS_HISTORY=null;
        INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS_ERROR=null;
        STUDENT_MARK_ARCH_BATCH_STATUS=null;
        STUDENT_MARK_ARCH_BATCH_STATUS_HISTORY=null;
        STUDENT_MARK_ARCH_BATCH_STATUS_ERROR=null;
        CLASS_MARK_ARCH_BATCH_STATUS=null;
        CLASS_MARK_ARCH_BATCH_STATUS_HISTORY=null;
        CLASS_MARK_ARCH_BATCH_STATUS_ERROR=null;
        STUDENT_EXAM_ARCH_BATCH_STATUS=null;
        STUDENT_EXAM_ARCH_BATCH_STATUS_HISTORY=null;
        STUDENT_EXAM_ARCH_BATCH_STATUS_ERROR=null;
        CLASS_EXAM_ARCH_BATCH_STATUS=null;
        CLASS_EXAM_ARCH_BATCH_STATUS_HISTORY=null;
        CLASS_EXAM_ARCH_BATCH_STATUS_ERROR=null;
        STUDENT_TIME_TABLE_ARCH_BATCH_STATUS=null;
        STUDENT_TIME_TABLE_ARCH_BATCH_STATUS_HISTORY=null;
        STUDENT_TIME_TABLE_ARCH_BATCH_STATUS_ERROR=null;
        CLASS_TIME_TABLE_ARCH_BATCH_STATUS=null;
        CLASS_TIME_TABLE_ARCH_BATCH_STATUS_HISTORY=null;
        CLASS_TIME_TABLE_ARCH_BATCH_STATUS_ERROR=null;
        STUDENT_ATTENDANCE_ARCH_BATCH_STATUS=null;
        STUDENT_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY=null;
        STUDENT_ATTENDANCE_ARCH_BATCH_STATUS_ERROR=null;
        CLASS_ATTENDANCE_ARCH_BATCH_STATUS=null;
        CLASS_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY=null;
        CLASS_ATTENDANCE_ARCH_BATCH_STATUS_ERROR=null;
        STUDENT_LEAVE_ARCH_BATCH_STATUS=null;
        STUDENT_LEAVE_ARCH_BATCH_STATUS_HISTORY=null;
        STUDENT_LEAVE_ARCH_BATCH_STATUS_ERROR=null;
        TEACHER_LEAVE_ARCH_BATCH_STATUS=null;
        TEACHER_LEAVE_ARCH_BATCH_STATUS_HISTORY=null;
        TEACHER_LEAVE_ARCH_BATCH_STATUS_ERROR=null;
        STUDENT_CALENDER_ARCH_BATCH_STATUS=null;
        STUDENT_CALENDER_ARCH_BATCH_STATUS_HISTORY=null;
        STUDENT_CALENDER_ARCH_BATCH_STATUS_ERROR=null;
        TEACHER_CALENDER_ARCH_BATCH_STATUS=null;
        TEACHER_CALENDER_ARCH_BATCH_STATUS_HISTORY=null;
        TEACHER_CALENDER_ARCH_BATCH_STATUS_ERROR=null;
        TEACHER_PAYROLL_ARCH_BATCH_STATUS=null;
        TEACHER_PAYROLL_ARCH_BATCH_STATUS_HISTORY=null;
        TEACHER_PAYROLL_ARCH_BATCH_STATUS_ERROR=null;
        STUDENT_PAYMENT_ARCH_BATCH_STATUS=null;
        STUDENT_PAYMENT_ARCH_BATCH_STATUS_HISTORY=null;
        STUDENT_PAYMENT_ARCH_BATCH_STATUS_ERROR=null;
        INSTITUTE_PAYMENT_ARCH_BATCH_STATUS=null;
        INSTITUTE_PAYMENT_ARCH_BATCH_STATUS_HISTORY=null;
        INSTITUTE_PAYMENT_ARCH_BATCH_STATUS_ERROR=null;
        TEACHER_ATTENDANCE_ARCH_BATCH_STATUS=null;
        TEACHER_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY=null;
        TEACHER_ATTENDANCE_ARCH_BATCH_STATUS_ERROR=null;
        TEACHER_TIME_TABLE_ARCH_BATCH_STATUS=null;
        TEACHER_TIME_TABLE_ARCH_BATCH_STATUS_HISTORY=null;
        TEACHER_TIME_TABLE_ARCH_BATCH_STATUS_ERROR=null;
        STUDENT_PROFILE_ARCH_BATCH_STATUS=null;
        STUDENT_PROFILE_ARCH_BATCH_STATUS_HISTORY=null;
        STUDENT_PROFILE_ARCH_BATCH_STATUS_ERROR=null;
        TEACHER_PROFILE_ARCH_BATCH_STATUS=null;
        TEACHER_PROFILE_ARCH_BATCH_STATUS_HISTORY=null;
        TEACHER_PROFILE_ARCH_BATCH_STATUS_ERROR=null;
        USER_PROFILE_ARCH_BATCH_STATUS=null;
        USER_PROFILE_ARCH_BATCH_STATUS_HISTORY=null;
        USER_PROFILE_ARCH_BATCH_STATUS_ERROR=null;
        APP=null;
        ERROR_MASTER=null;
        INSTITUTE_MASTER=null;
        SERVICE_TYPE_MASTER=null;
        CLASS=null;
        CLASS_MARK_ENTRY=null;
        STUDENT_MARKS=null;
        CLASS_TIMETABLE_MASTER=null;
        CLASS_TIMETABLE_DETAIL=null;
        CLASS_ATTENDANCE_MASTER=null;
        CLASS_ATTENDANCE_DETAIL=null;
        CLASS_ASSIGNMENT=null;
        CLASS_FEE_MANAGEMENT=null;
        CLASS_EXAM_SCHEDULE_MASTER=null;
        CLASS_EXAM_SCHEDULE_DETAIL=null;
        CLASS_STUDENT_MAPPING=null;
        STUDENT=null;
        SVW_STUDENT_PROFILE=null;
        SVW_EXISTING_MEDICAL_DETAILS=null;
        SVW_CONTACT_PERSON_DETAILS=null;
        SVW_FAMILY_DETAILS=null;
        SVW_STUDENT_ATTENDANCE=null;
        SVW_STUDENT_MARKS=null;
        SVW_STUDENT_PRORESS_CARD=null;
        SVW_STUDENT_LEAVE_MANAGEMENT=null;
        SVW_STUDENT_ASSIGNMENT=null;
        SVW_TEACHER_PROFILE=null;
        SVW_USER_PROFILE_MAPPING=null;
        SVW_STUDENT_TIMETABLE_MASTER=null;
        SVW_STUDENT_TIMETABLE_DETAIL=null;
        STUDENT_FEE_MANAGEMENT=null;
        STUDENT_PAYMENT=null;
        SVW_STUDENT_OTHER_ACTIVITY=null;
        SVW_STUDENT_EXAM_SCHEDULE_MASTER=null;
        SVW_STUDENT_EXAM_SCHEDULE_DETAIL=null;
        SVW_STUDENT_CALENDER=null;
        SVW_STUDENT_NOTIFICATION=null;
        SVW_STUDENT_LOGIN_MAPPING=null;
        USER=null;
        UVW_USER_MASTER=null;
        UVW_USER_PROFILE_MAPPING=null;
        UVW_STUDENT_PROFILE=null;
        UVW_USER_PROFILE=null;
        UVW_USER_CREDENTIALS=null;
        UVW_IN_LOG=null;
        UVW_OUT_LOG=null;
        UVW_USER_ROLE_MASTER=null;
        UVW_USER_ROLE_DETAIL=null;
        UVW_USER_ROLE_MAPPING=null;
        UVW_PARENT_STUDENT_ROLEMAPPING=null;
        UVW_CLASS_ENTITY_ROLEMAPPING=null;
        UVW_TEACHER_ENTITY_ROLEMAPPING=null;
        UVW_INSTITUTE_ENTITY_ROLEMAPPING=null;
        TEACHER=null;
        TVW_TEACHER_PROFILE=null;
        TVW_TEACHER_EXAM_DETAILS=null;
        TVW_TEACHER_TIMETABLE_MASTER=null;
        TVW_TEACHER_TIMETABLE_DETAIL=null;
        TVW_EXISTING_MEDICAL_DETAILS=null;
        TVW_CONTACT_PERSON_DETAILS=null;
        TVW_TEACHER_CALENDER=null;
        TVW_TEACHER_ATTENDANCE=null;
        TVW_TEACHER_LEAVE_MANAGEMENT=null;
        TVW_PAYROLL=null;
        TVW_INSTITUTE_PROFILE=null;
        MANAGEMENT=null;
        MVW_MANAGEMENT_EXAM_DETAILS=null;
        MVW_STUDENT_PROFILE=null;
        MVW_TEACHER_PROFILE=null;
        INSTITUTE=null;
        IVW_INSTITUTE_PROFILE=null;
        IVW_INSTITUTE_EXAM_DETAILS=null;
        IVW_INSTITUTE_EXAM_MASTER=null;
        IVW_STANDARD_MASTER=null;
        IVW_SUBJECT_DETAILS=null;
        IVW_SUBJECT_MASTER=null;
        IVW_SUBJECT_GRADE_MASTER=null;
        IVW_TEACHER_MASTER=null;
        IVW_STUDENT_MASTER=null;
        IVW_STUDENT_LOGIN_MAPPING=null;
        IVW_PERIOD_MASTER=null;
        IVW_CONTENT_TYPE_MASTER=null;
        IVW_LEAVE_TYPE_MASTER=null;
        IVW_FEE_TYPE_MASTER=null;
        IVW_NOTIFICATION_TYPE_MASTER=null;
        IVW_STUDENT_PROFILE=null;
        IVW_CLASS_LEVEL_CONFIGURATION_MASTER=null;
        IVW_HOLIDAY_MAINTANENCE=null;
        IVW_NOTIFICATION_MASTER=null;
        INSTITUTE_PAYMENT=null;
        IVW_GROUP_MAPPING_MASTER=null;
        IVW_GROUP_MAPPING_DETAIL=null;
        IVW_OTHER_ACTIVITY=null;
        INSTITUTE_FEE_MANAGEMENT=null;
        IVW_ASSIGNMENT=null;
        INSTITUTE_CURRENT_DATE=null;
        RETENTION_PERIOD=null;
        APP_BATCH_STATUS=null;
        APP_BATCH_STATUS_HISTORY=null;
        APP_BATCH_STATUS_ERROR=null;    
        APP_RETENTION_PERIOD=null;    
        DEFRAGMENTATION_BATCH_STATUS=null;    
        DEFRAGMENTATION_BATCH_STATUS_HISTORY=null;    
        DEFRAGMENTATION_BATCH_STATUS_ERROR=null;   
        STUDENT_ATTENDANCE_REPORT_BATCH_STATUS=null;
        STUDENT_ATTENDANCE_REPORT_BATCH_STATUS_HISTORY=null;
        STUDENT_ATTENDANCE_REPORT_BATCH_STATUS_ERROR=null;
        STUDENT_OTHER_ACTIVITY_REPORT_BATCH_STATUS=null;
        STUDENT_OTHER_ACTIVITY_REPORT_BATCH_STATUS_HISTORY=null;
        STUDENT_OTHER_ACTIVITY_REPORT_BATCH_STATUS_ERROR=null;
        SVW_STUDENT_ATTENDANCE_REPORT=null;
        SVW_STUDENT_OTHER_ACTIVITY_REPORT=null;
        CLASS_ATTENDANCE_REPORT_BATCH_STATUS=null;
        CLASS_ATTENDANCE_REPORT_BATCH_STATUS_HISTORY=null;
        CLASS_ATTENDANCE_REPORT_BATCH_STATUS_ERROR=null;
        CLASS_ATTENDANCE_REPORT=null;
        CLASS_OTHER_ACTIVITY_REPORT_BATCH_STATUS=null;
        CLASS_OTHER_ACTIVITY_REPORT_BATCH_STATUS_HISTORY=null;
        CLASS_OTHER_ACTIVITY_REPORT_BATCH_STATUS_ERROR=null;
        CLASS_OTHER_ACTIVITY_REPORT=null;
        CLASS_FEE_AMOUNT_REPORT_BATCH_STATUS=null;
        CLASS_FEE_AMOUNT_REPORT_BATCH_STATUS_HISTORY=null;
        CLASS_FEE_AMOUNT_REPORT_BATCH_STATUS_ERROR=null;
        CLASS_FEE_AMOUNT_REPORT=null;
        CLASS_FEE_STATUS_REPORT_BATCH_STATUS=null;
        CLASS_FEE_STATUS_REPORT_BATCH_STATUS_HISTORY=null;
        CLASS_FEE_STATUS_REPORT_BATCH_STATUS_ERROR=null;
        CLASS_FEE_STATUS_REPORT=null;
        CLASS_MARK_REPORT_BATCH_STATUS=null;
        CLASS_MARK_REPORT_BATCH_STATUS_HISTORY=null;
        CLASS_MARK_REPORT_BATCH_STATUS_ERROR=null;
        CLASS_MARK_REPORT=null;
        CLASS_GRADE_REPORT_BATCH_STATUS=null;
        CLASS_GRADE_REPORT_BATCH_STATUS_HISTORY=null;
        CLASS_GRADE_REPORT_BATCH_STATUS_ERROR=null;
        CLASS_GRADE_REPORT=null;
        TEACHER_ATTENDANCE_REPORT_BATCH_STATUS=null;
        TEACHER_ATTENDANCE_REPORT_BATCH_STATUS_HISTORY=null;
        TEACHER_ATTENDANCE_REPORT_BATCH_STATUS_ERROR=null;
        TVW_TEACHER_ATTENDANCE_REPORT=null;
        TEACHER_MARK_REPORT_BATCH_STATUS=null;
        TEACHER_MARK_REPORT_BATCH_STATUS_HISTORY=null;
        TEACHER_MARK_REPORT_BATCH_STATUS_ERROR=null;
        TVW_TEACHER_MARK_REPORT=null;
        TEACHER_GRADE_REPORT_BATCH_STATUS=null;
        TEACHER_GRADE_REPORT_BATCH_STATUS_HISTORY=null;
        TEACHER_GRADE_REPORT_BATCH_STATUS_ERROR=null;
        TVW_TEACHER_GRADE_REPORT=null;
        BATCH_SERVICES=null;
        IVW_E_CIRCULAR=null;
        E_CIRCULAR_EOD_STATUS=null;
        E_CIRCULAR_EOD_STATUS_HISTORY=null;
        E_CIRCULAR_EOD_STATUS_ERROR=null;
        STUDENT_E_CIRCULAR_EOD_STATUS=null;
        STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY=null;
        STUDENT_E_CIRCULAR_EOD_STATUS_ERROR=null;
        ARCH_SHIPPING_STATUS=null;
        ARCH_APPLY_STATUS=null;
        ARCH=null;
        ARCHIVAL_RECOVERY_BATCH_STATUS=null;
        ARCHIVAL_RECOVERY_BATCH_STATUS_HISTORY=null;
        ARCHIVAL_RECOVERY_BATCH_STATUS_ERROR=null;
        UVW_USER_CONTRACT_MASTER=null;
        UVW_USER_ROLE_INSTITUTE=null;
        CONTRACT_MASTER=null;
        STUDENT_NOTIFICATION_EMAIL_ERROR=null;
        STUDENT_NOTIFICATION_SMS_ERROR=null;
        STUDENT_NOTIFICATION_STATUS=null;
        FEE=null;    
        PAYMENT=null;
	INSTITITUTE_FEE_PAYMENT=null;
        ASSIGNMENT=null;    
        OTHER_ACTIVITY=null;
	INSTITITUTE_FEE_PAYMENT=null;
        FEE_BATCH_INDICATOR=null;
        NOTIFICATION_BATCH_INDICATOR=null;
        PAYMENT_MASTER=null;
        INSTITUTE_OTHER_ACTIVITY_TRACKING=null;
        OTHER_ACTIVITY_BATCH_INDICATOR=null;
        LEAVE=null;
        CLASS_LEAVE_MANAGEMENT=null;
        APP_SUPPORT=null;
        CLASS_EXAM_RANK=null;
        IVW_UNAUTH_RECORDS=null;
        IVW_STUDENT_LEAVE_DETAILS=null;
        IVW_TEACHER_LEAVE_DETAILS=null;
        STUDENT_OTP_STATUS=null;
        INSTITUTE_OTP_STATUS=null;
        IVW_SOFT_SKILL_CONFIGURATION_MASTER=null;
        IVW_SKILL_MASTER=null;
        IVW_SKILL_GRADE_MASTER=null;
        CLASS_SKILL_ENTRY=null;
        STUDENT_SKILLS=null;
        OTP=null;
        REPORT=null;
        SUBSTITUTE_AVAIL_SAME_CLASS=null;
        SMS_TERMS_TRANSALATOR=null;
        FEE_NOTIFICATION_EOD_STATUS=null;
        FEE_NOTIFICATION_EOD_STATUS_HISTORY=null;
        FEE_NOTIFICATION_EOD_STATUS_ERROR=null;
        STUDENT_FEE_NOTIFICATION_EOD_STATUS=null;
        STUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY=null;
        STUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR=null;
        STUDENT_EVENT_NOTIFICATION_EOD_STATUS=null;
        STUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY=null;
        STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR=null;
        TODAY_NOTIFICATION=null;
        STUDENT_PROGRESS_CARD_SIGNATURE=null;
        UVW_TEACHER_ID_MAPPING=null;
        CLASS_ATTENDANCE_MASTER_HISTORY=null;
        CLASS_ATTENDANCE_DETAIL_HISTORY=null;
        STUDENT_SOFT_SKILL_SIGNATURE=null;
        UVW_USER_OTP_STATUS=null;
        TVW_TEACHER_E_CIRCULAR=null;
        TEACHER_E_CIRCULAR_EOD_STATUS=null;
        TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY=null;
        TEACHER_E_CIRCULAR_EOD_STATUS_ERROR=null;
        IVW_STUDENT_E_CIRCULAR_SIGNATURE=null;
        IVW_TEACHER_E_CIRCULAR_SIGNATURE=null;
        IVW_INSITUTE_OTHER_DETAILS=null;
        INSTITUTE_PAYMENT_MASTER=null;
        STUDENT_NOTIFICATION_MESSAGE=null;
        IVW_MESSAGE_TEMPLATE_MASTER=null;
        IVW_MESSAGE_TEMPLATE_DETAIL=null;
    }catch(Exception ex){
            throw ex;
        }    
        
        
    }
    private static String[][] getArray(String arrayName,IBDProperties ibdproperties){
        
        try{
                String[][] twoDArray;
//                 System.out.print("arrayName"+arrayName);
                String arrayValue=ibdproperties.getProperty(arrayName);
                
//                System.out.println("arrayValue"+arrayValue);
                
                int firstIdx=arrayValue.indexOf("{");
                int lastIdx=arrayValue.lastIndexOf("}");
                
//                System.out.println("firstIdx"+firstIdx);
//                System.out.println("lastIdx"+lastIdx);
                
                String  tableSub=arrayValue.substring(firstIdx+1,lastIdx).trim();
                
                String withoutOpen=tableSub.replace("{","");
                
                String[] tableArray=withoutOpen.split("},");
                
                twoDArray=new String[tableArray.length][];

                
                for(int i=0;i<tableArray.length;i++){
                    
                    
                    String[] columnArray=tableArray[i].split(",");
                    
                    twoDArray[i]=new String[columnArray.length];
                    for(int j=0;j<columnArray.length;j++){
                        
                        twoDArray[i][j]=columnArray[j].replace("\"", "").trim();
                        
                    }
                }
            
            return twoDArray;
        }catch(Exception ex){
            throw ex;
        }  
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
//    public static final int FILE_NO_OF_PROPERTY = 4;//Each file has 4 properties filename,fileid
//    
//    public static final int TABLE_NO_OF_PROPERTY = 6; //Each Table has 6 properties TableName,Tableid,primary key etc.,
//
//    //public static final int COLUMN_NO_OF_PROPERTY = 4; //Each column has 4 properties Name,id,datatype,length
//    public static final int COLUMN_NO_OF_PROPERTY = 5; //Each column has 5 properties Name,id,datatype,length,relationship
//    public static final String[][] FILE_DETAILS = {{"STUDENT", "USER", "TEACHER", "INSTITUTE","APP","CLASS","BATCH"},//file Type
//    {"1", "2", "3", "4","5","6","7"},//file id
//    {"NO", "NO", "NO", "NO","NO","NO","NO"},//indexing required or not
//    {"NO", "NO", "NO", "YES","NO","NO","NO"}//ONLINE_INDEXING
//    };
//
//     public static final String[][] BATCH={
//         {"BATCH_CONFIG","BATCH_STATUS","INSTITUTE_EOD_STATUS","APP_EOD_STATUS","ASSIGNMENT_EOD_STATUS","STUDENT_ASSIGNMENT_EOD_STATUS","BATCH_STATUS_HISTORY","INSTITUTE_EOD_STATUS_HISTORY","APP_EOD_STATUS_HISTORY","ASSIGNMENT_EOD_STATUS_HISTORY","STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY","BATCH_STATUS_ERROR","INSTITUTE_EOD_STATUS_ERROR","APP_EOD_STATUS_ERROR","ASSIGNMENT_EOD_STATUS_ERROR","STUDENT_ASSIGNMENT_EOD_STATUS_ERROR","OTHER_ACTIVITY_EOD_STATUS","OTHER_ACTIVITY_EOD_STATUS_HISTORY","OTHER_ACTIVITY_EOD_STATUS_ERROR","STUDENT_OTHER_ACTIVITY_EOD_STATUS","STUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY","STUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR","FEE_EOD_STATUS","FEE_EOD_STATUS_HISTORY","FEE_EOD_STATUS_ERROR","STUDENT_FEE_EOD_STATUS","STUDENT_FEE_EOD_STATUS_HISTORY","STUDENT_FEE_EOD_STATUS_ERROR","NOTIFICATION_EOD_STATUS","NOTIFICATION_EOD_STATUS_HISTORY","NOTIFICATION_EOD_STATUS_ERROR","STUDENT_NOTIFICATION_EOD_STATUS","STUDENT_NOTIFICATION_EOD_STATUS_HISTORY","STUDENT_NOTIFICATION_EOD_STATUS_ERROR","TIMETABLE_BATCH_STATUS","TIMETABLE_BATCH_STATUS_HISTORY","TIMETABLE_BATCH_STATUS_ERROR","STUDENT_TIMETABLE_BATCH_STATUS","STUDENT_TIMETABLE_BATCH_STATUS_HISTORY","STUDENT_TIMETABLE_BATCH_STATUS_ERROR","EXAM_BATCH_STATUS","EXAM_BATCH_STATUS_HISTORY","EXAM_BATCH_STATUS_ERROR","STUDENT_EXAM_BATCH_STATUS","STUDENT_EXAM_BATCH_STATUS_HISTORY","STUDENT_EXAM_BATCH_STATUS_ERROR","MARK_BATCH_STATUS","MARK_BATCH_STATUS_HISTORY","MARK_BATCH_STATUS_ERROR","STUDENT_MARK_BATCH_STATUS","STUDENT_MARK_BATCH_STATUS_HISTORY","STUDENT_MARK_BATCH_STATUS_ERROR","ATTENDANCE_BATCH_STATUS","ATTENDANCE_BATCH_STATUS_HISTORY","ATTENDANCE_BATCH_STATUS_ERROR","STUDENT_ATTENDANCE_BATCH_STATUS","STUDENT_ATTENDANCE_BATCH_STATUS_HISTORY","STUDENT_ATTENDANCE_BATCH_STATUS_ERROR","STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS","STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY","STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR","INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS","INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY","INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR","STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS","STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS_HISTORY","STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS_ERROR","INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS","INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS_HISTORY","INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS_ERROR","STUDENT_FEE_ARCH_BATCH_STATUS","STUDENT_FEE_ARCH_BATCH_STATUS_HISTORY","STUDENT_FEE_ARCH_BATCH_STATUS_ERROR","INSTITUTE_FEE_ARCH_BATCH_STATUS","INSTITUTE_FEE_ARCH_BATCH_STATUS_HISTORY","INSTITUTE_FEE_ARCH_BATCH_STATUS_ERROR","STUDENT_NOTIFICATION_ARCH_BATCH_STATUS","STUDENT_NOTIFICATION_ARCH_BATCH_STATUS_HISTORY","STUDENT_NOTIFICATION_ARCH_BATCH_STATUS_ERROR","INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS","INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS_HISTORY","INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS_ERROR","STUDENT_MARK_ARCH_BATCH_STATUS","STUDENT_MARK_ARCH_BATCH_STATUS_HISTORY","STUDENT_MARK_ARCH_BATCH_STATUS_ERROR","CLASS_MARK_ARCH_BATCH_STATUS","CLASS_MARK_ARCH_BATCH_STATUS_HISTORY","CLASS_MARK_ARCH_BATCH_STATUS_ERROR","STUDENT_EXAM_ARCH_BATCH_STATUS","STUDENT_EXAM_ARCH_BATCH_STATUS_HISTORY","STUDENT_EXAM_ARCH_BATCH_STATUS_ERROR","CLASS_MARK_ARCH_BATCH_STATUS","CLASS_MARK_ARCH_BATCH_STATUS_HISTORY","CLASS_MARK_ARCH_BATCH_STATUS_ERROR","STUDENT_TIME_TABLE_ARCH_BATCH_STATUS","STUDENT_TIME_TABLE_ARCH_BATCH_STATUS_HISTORY","STUDENT_TIME_TABLE_ARCH_BATCH_STATUS_ERROR","CLASS_TIME_TABLE_ARCH_BATCH_STATUS","CLASS_TIME_TABLE_ARCH_BATCH_STATUS_HISTORY","CLASS_TIME_TABLE_ARCH_BATCH_STATUS_ERROR","STUDENT_ATTENDANCE_ARCH_BATCH_STATUS","STUDENT_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY","STUDENT_ATTENDANCE_ARCH_BATCH_STATUS_ERROR","CLASS_ATTENDANCE_ARCH_BATCH_STATUS","CLASS_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY","CLASS_ATTENDANCE_ARCH_BATCH_STATUS_ERROR","STUDENT_LEAVE_ARCH_BATCH_STATUS","STUDENT_LEAVE_ARCH_BATCH_STATUS_HISTORY","STUDENT_LEAVE_ARCH_BATCH_STATUS_ERROR","TEACHER_LEAVE_ARCH_BATCH_STATUS","TEACHER_LEAVE_ARCH_BATCH_STATUS_HISTORY","TEACHER_LEAVE_ARCH_BATCH_STATUS_ERROR","STUDENT_CALENDER_ARCH_BATCH_STATUS","STUDENT_CALENDER_ARCH_BATCH_STATUS_HISTORY
//","STUDENT_CALENDER_ARCH_BATCH_STATUS_ERROR","TEACHER_CALENDER_ARCH_BATCH_STATUS","TEACHER_CALENDER_ARCH_BATCH_STATUS_HISTORY","TEACHER_CALENDER_ARCH_BATCH_STATUS_ERROR","TEACHER_PAYROLL_ARCH_BATCH_STATUS","TEACHER_PAYROLL_ARCH_BATCH_STATUS_HISTORY","TEACHER_PAYROLL_ARCH_BATCH_STATUS_ERROR","STUDENT_PAYMENT_ARCH_BATCH_STATUS","STUDENT_PAYMENT_ARCH_BATCH_STATUS_HISTORY","STUDENT_PAYMENT_ARCH_BATCH_STATUS_ERROR","INSTITUTE_PAYMENT_ARCH_BATCH_STATUS","INSTITUTE_PAYMENT_ARCH_BATCH_STATUS_HISTORY","INSTITUTE_PAYMENT_ARCH_BATCH_STATUS_ERROR","TEACHER_ATTENDANCE_ARCH_BATCH_STATUS","TEACHER_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY","TEACHER_ATTENDANCE_ARCH_BATCH_STATUS_ERROR","TEACHER_TIME_TABLE_ARCH_BATCH_STATUS","TEACHER_TIME_TABLE_ARCH_BATCH_STATUS_HISTORY","TEACHER_TIME_TABLE_ARCH_BATCH_STATUS_ERROR","STUDENT_PROFILE_ARCH_BATCH_STATUS","STUDENT_PROFILE_ARCH_BATCH_STATUS_HISTORY","STUDENT_PROFILE_ARCH_BATCH_STATUS_ERROR","TEACHER_PROFILE_ARCH_BATCH_STATUS","TEACHER_PROFILE_ARCH_BATCH_STATUS_HISTORY","TEACHER_PROFILE_ARCH_BATCH_STATUS_ERROR","USER_PROFILE_ARCH_BATCH_STATUS","USER_PROFILE_ARCH_BATCH_STATUS_HISTORY","USER_PROFILE_ARCH_BATCH_STATUS_ERROR"},
//        {"94","95","96","97","99","100","101","102","103","104","105","106","107","108","109","110","111","112","113","114","115","116","117","118","119","120","121","122","124","125","126","127","128","129","130","131","132","133","134","135","136","137","138","139","140","141","142","143","144","145","146","147","148","149","150","151","152","153","154","155","156","157","158","159","160","161","162","163","164","165","166","167","168","169","170","171","172","173","174","175","176","177","178","179","180","181","182","183","184","185","186","187","188","189","190","191","192","193","194","195","196","197","198","199","200","201","202","203","204","205","206","207","209","210","211","212","213","214","215","216","217","218","219","220","221","222","223","224","225","226","227","228","229","230","231","232","233","234","235","236","237","238"},
//        {"1~16","1~2~3","1~2","1","1~2~3","1~2~3~4","1~2~3~10","1~2~9","1~8","1~2~3~11","1~2~3~4~9","1~2~3","1~2","1","1~2~3","1~2~3~4","1~2~3","1~2~3~11","1~2~3","1~2~3~4","1~2~3~4~9","1~2~3~4","1~2~3","1~2~3~11","1~2~3","1~2~3~4","1~2~3~4~9","1~2~3~4","1~2~3","1~2~3~11","1~2~3","1~2~3~4","1~2~3~4~9","1~2~3~4","1~2~3~4","1~2~3~4~10","1~2~3~4","1~2~3~4~5","1~2~3~4~5~9","1~2~3~4~5","1~2~3~4~5","1~2~3~4~5~11","1~2~3~4~5","1~2~3~4~5~6","1~2~3~4~5~6~10","1~2~3~4~5~6","1~2~3~4~5~6","1~2~3~4~5~6~12","1~2~3~4~5~6","1~2~3~4~5~6~7","1~2~3~4~5~6~7~11","1~2~3~4~5~6~7","1~2~3~4","1~2~3~4~10","1~2~3~4","1~2~3~4~5","1~2~3~4~5~9","1~2~3~4~5","1~2~3","1~2~3~7","1~2~3","1~2","1~2~6","1~2","1~2~3","1~2~3~7","1~2~3","1~2","1~2~6","1~2","1~2~3","1~2~3~7","1~2~3","1~2","1~2~6","1~2","1~2~3","1~2~3~7","1~2~3","1~2","1~2~6","1~2","1~2~3","1~2~3~7","1~2~3","1~2~3~4","1~2~3~4~8","1~2~3~4","1~2~3","1~2~3~7","1~2~3","1~2~3~4","1~2~3~4~8","1~2~3~4","1~2~3","1~2~3~7","1~2~3","1~2~3~4","1~2~3~4~8","1~2~3~4","1~2~3","1~2~3~7","1~2~3","1~2~3~4","1~2~3~4~8","1~2~3~4","1~2~3","1~2~3~7","1~2~3","1~2~3","1~2~3~7","1~2~3","1~2~3","1~2~3~7","1~2~3","1~2~3","1~2~3~7","1~2~3","1~2~3","1~2~3~7","1~2~3","1~2~3","1~2~3~7","1~2~3","1~2","1~2~6","1~2","1~2~3","1~2~3~7","1~2~3","1~2~3","1~2~3~7","1~2~3","1~2~3","1~2~3~7","1~2~3","1~2~3","1~2~3~7","1~2~3","1~2~3","1~2~3~7","1~2~3"},
//        {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},
//        {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},
//        {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},    
//    };
//     
//     public static final String[][] BATCH_CONFIG={
//        {"BATCH_NAME","LAYER","EXECUTION_FREQUENCY","START_TIME","EOD","INTRA_DAY_BATCH","REC_FAIL","NO_OF_THREADS","CHILD_NO_OF_THREADS","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//        {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"},
//        {"string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string"},
//        {"20","1","1","15","1","1","1","2","3","20","20","25","25","2","2","3","30","30"},
//        {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},
//    };
//    
//    public static final String[][] BATCH_STATUS={
//        {"INSTITUTE_ID","BATCH_NAME","BUSINESS_DATE","START_TIME","END_TIME","EOD_STATUS","NO_OF_SUCCESS","NO_FAILURES","ERROR"},
//        {"1","2","3","4","5","6","7","8","9"},
//        {"string","string","string","string","string","string","string","string","string"},
//        {"20","30","20","20","20","5","2","15","1"},
//        {"null","null","null","null","null","null","null","null","null","null"},
//    };
//    
//    public static final String[][] BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","BATCH_NAME","BUSINESS_DATE","START_TIME","END_TIME","EOD_STATUS","NO_OF_SUCCESS","NO_FAILURES","ERROR","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9","10"},
//        {"string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","20","20","20","1","20"},
//        {"null","null","null","null","null","null","null","null","null","null","null"},
//    };
//    
//    public static final String[][] BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","BATCH_NAME","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    
//    public static final String[][] INSTITUTE_EOD_STATUS={
//        {"INSTITUTE_ID","BUSINESS_DATE","EOD_STATUS","START_TIME","END_TIME","NO_OF_SUCCESS","NO_OF_FAILURES","ERROR"},
//        {"1","2","3","4","5","6","7","8"},
//        {"string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","20","20","1"},
//        {"null","null","null","null","null","null","null","null"},
//    };
//    
//     public static final String[][] INSTITUTE_EOD_STATUS_HISTORY={
//        {"INSTITUTE_ID","BUSINESS_DATE","EOD_STATUS","START_TIME","END_TIME","NO_OF_SUCCESS","NO_OF_FAILURES","ERROR","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9"},
//        {"string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","20","20","1","20"},
//        {"null","null","null","null","null","null","null","null","null"},
//    };
//     
//     public static final String[][] INSTITUTE_EOD_STATUS_ERROR={
//        {"INSTITUTE_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3"},
//        {"string","string","string"},
//        {"20","20","2000"},
//        {"null","null","null"},
//    }; 
//    
//    public static final String[][] APP_EOD_STATUS={
//        {"BUSINESS_DATE","START_TIME","END_TIME","EOD_STATUS","ERROR","NO_OF_SUCCESS","NO_OF_FAILURES"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    
//    public static final String[][] APP_EOD_STATUS_HISTORY={
//        {"BUSINESS_DATE","START_TIME","END_TIME","EOD_STATUS","ERROR","NO_OF_SUCCESS","NO_OF_FAILURES","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8"},
//        {"string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null","null"},
//    };
//    
//     public static final String[][] APP_EOD_STATUS_ERROR={
//        {"BUSINESS_DATE","ERROR"},
//        {"1","2"},
//        {"string","string"},
//        {"20","2000"},
//        {"null","null"},
//    };
//    
//    public static final String[][] ASSIGNMENT_EOD_STATUS={
//        {"INSTITUTE_ID","ASSIGNMENT_ID","BUSINESS_DATE","STATUS","ERROR","NO_OF_SUCCESS","NO_FAILURES","GROUP_ID","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7","8","9","10"},
//        {"string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null","null"},
//    };
//    
//     public static final String[][] ASSIGNMENT_EOD_STATUS_HISTORY={
//        {"INSTITUTE_ID","ASSIGNMENT_ID","BUSINESS_DATE","STATUS","ERROR","NO_OF_SUCCESS","NO_FAILURES","GROUP_ID","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9","10","11"},
//        {"string","string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20","20","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null","null","null"},
//    };
//     
//    public static final String[][] ASSIGNMENT_EOD_STATUS_ERROR={
//        {"INSTITUTE_ID","ASSIGNMENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    }; 
//    
//    public static final String[][] STUDENT_ASSIGNMENT_EOD_STATUS={
//        {"INSTITUTE_ID","ASSIGNMENT_ID","STUDENT_ID","BUSINESS_DATE","STATUS","ERROR","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7","8"},
//        {"string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","1","20","20"},
//        {"null","null","null","null","null","null","null","null"},
//    };
//    
//    public static final String[][] STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY={
//        {"INSTITUTE_ID","ASSIGNMENT_ID","STUDENT_ID","BUSINESS_DATE","STATUS","ERROR","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9"},
//        {"string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null"},
//    };
//    
//     public static final String[][] STUDENT_ASSIGNMENT_EOD_STATUS_ERROR={
//        {"INSTITUTE_ID","ASSIGNMENT_ID","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","20","20","2000"},
//        {"null","null","null","null","null"},
//    };
//    
//    public static final String[][] OTHER_ACTIVITY_EOD_STATUS={
//        {"INSTITUTE_ID","ACTIVITY_ID","BUSINESS_DATE","STATUS","ERROR","NO_OF_SUCCESS","NO_FAILURES","GROUP_ID","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7","8","9","10"},
//        {"string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] OTHER_ACTIVITY_EOD_STATUS_HISTORY={
//        {"INSTITUTE_ID","ACTIVITY_ID","BUSINESS_DATE","STATUS","ERROR","NO_OF_SUCCESS","NO_FAILURES","GROUP_ID","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9","10","11"},
//        {"string","string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20","20","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] OTHER_ACTIVITY_EOD_STATUS_ERROR={
//        {"INSTITUTE_ID","ACTIVITY_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    }; 
//     public static final String[][] STUDENT_OTHER_ACTIVITY_EOD_STATUS={
//        {"INSTITUTE_ID","ACTIVITY_ID","STUDENT_ID","BUSINESS_DATE","STATUS","ERROR","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7","8"},
//        {"string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","1","20","20"},
//        {"null","null","null","null","null","null","null","null"},
//    };
//    
//    public static final String[][] STUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY={
//        {"INSTITUTE_ID","ACTIVITY_ID","STUDENT_ID","BUSINESS_DATE","STATUS","ERROR","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9"},
//        {"string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null"},
//    };
//    
//     public static final String[][] STUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR={
//        {"INSTITUTE_ID","ACTIVITY_ID","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","20","20","2000"},
//        {"null","null","null","null","null"},
//    };
//      public static final String[][] FEE_EOD_STATUS={
//        {"INSTITUTE_ID","FEE_ID","BUSINESS_DATE","STATUS","ERROR","NO_OF_SUCCESS","NO_FAILURES","GROUP_ID","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7","8","9","10"},
//        {"string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] FEE_EOD_STATUS_HISTORY={
//        {"INSTITUTE_ID","FEE_ID","BUSINESS_DATE","STATUS","ERROR","NO_OF_SUCCESS","NO_FAILURES","GROUP_ID","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9","10","11"},
//        {"string","string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20","20","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] FEE_EOD_STATUS_ERROR={
//        {"INSTITUTE_ID","FEE_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    }; 
//     public static final String[][] STUDENT_FEE_EOD_STATUS={
//        {"INSTITUTE_ID","FEE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","ERROR","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7","8"},
//        {"string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","1","20","20"},
//        {"null","null","null","null","null","null","null","null"},
//    };
//    
//    public static final String[][] STUDENT_FEE_EOD_STATUS_HISTORY={
//        {"INSTITUTE_ID","FEE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","ERROR","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9"},
//        {"string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null"},
//    };
//    
//     public static final String[][] STUDENT_FEE_EOD_STATUS_ERROR={
//        {"INSTITUTE_ID","FEE_ID","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","20","20","2000"},
//        {"null","null","null","null","null"},
//    };
//     
//     public static final String[][] NOTIFICATION_EOD_STATUS={
//        {"INSTITUTE_ID","NOTIFICATION_ID","BUSINESS_DATE","STATUS","ERROR","NO_OF_SUCCESS","NO_FAILURES","GROUP_ID","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7","8","9","10"},
//        {"string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] NOTIFICATION_EOD_STATUS_HISTORY={
//        {"INSTITUTE_ID","NOTIFICATION_ID","BUSINESS_DATE","STATUS","ERROR","NO_OF_SUCCESS","NO_FAILURES","GROUP_ID","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9","10","11"},
//        {"string","string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20","20","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] NOTIFICATION_EOD_STATUS_ERROR={
//        {"INSTITUTE_ID","NOTIFICATION_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    }; 
//     public static final String[][] STUDENT_NOTIFICATION_EOD_STATUS={
//        {"INSTITUTE_ID","NOTIFICATION_ID","STUDENT_ID","BUSINESS_DATE","STATUS","ERROR","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7","8"},
//        {"string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","1","20","20"},
//        {"null","null","null","null","null","null","null","null"},
//    };
//    
//    public static final String[][] STUDENT_NOTIFICATION_EOD_STATUS_HISTORY={
//        {"INSTITUTE_ID","NOTIFICATION_ID","STUDENT_ID","BUSINESS_DATE","STATUS","ERROR","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9"},
//        {"string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null"},
//    };
//    
//     public static final String[][] STUDENT_NOTIFICATION_EOD_STATUS_ERROR={
//        {"INSTITUTE_ID","NOTIFICATION_ID","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","20","20","2000"},
//        {"null","null","null","null","null"},
//    };
//    
//    public static final String[][] TIMETABLE_BATCH_STATUS={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","STATUS","NO_OF_SUCCESS","NO_FAILURES","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7","8","9"},
//        {"string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] TIMETABLE_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","STATUS","NO_OF_SUCCESS","NO_FAILURES","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9","10"},
//        {"string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] TIMETABLE_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","20","20","2000"},
//        {"null","null","null","null","null"},
//    };
//    
//    public static final String[][] STUDENT_TIMETABLE_BATCH_STATUS={
//        {"INSTITUTE_ID","STANDARD","SECTION","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7","8"},
//        {"string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","1","20","20"},
//        {"null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_TIMETABLE_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STANDARD","SECTION","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9"},
//        {"string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_TIMETABLE_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STANDARD","SECTION","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","20","20","2000"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] EXAM_BATCH_STATUS={
//        {"INSTITUTE_ID","STANDARD","SECTION","EXAM","BUSINESS_DATE","STATUS","NO_OF_SUCCESS","NO_FAILURES","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7","8","9","10"},
//        {"string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","1","20","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] EXAM_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STANDARD","SECTION","EXAM","BUSINESS_DATE","STATUS","NO_OF_SUCCESS","NO_FAILURES","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9","10","11"},
//        {"string","string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","1","20","20","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] EXAM_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STANDARD","SECTION","EXAM","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","20","20","2000"},
//        {"null","null","null","null","null","null"},
//    };
//    
//    public static final String[][] STUDENT_EXAM_BATCH_STATUS={
//        {"INSTITUTE_ID","STANDARD","SECTION","EXAM","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7","8","9"},
//        {"string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","20","1","20","20"},
//        {"null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_EXAM_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STANDARD","SECTION","EXAM","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9","10"},
//        {"string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_EXAM_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STANDARD","SECTION","EXAM","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","20","2000"},
//        {"null","null","null","null","null","null","null"},
//    };
//    
//    public static final String[][] MARK_BATCH_STATUS={
//        {"INSTITUTE_ID","STANDARD","SECTION","EXAM","SUBJECT_ID","BUSINESS_DATE","STATUS","NO_OF_SUCCESS","NO_FAILURES","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7","8","9","10","11"},
//        {"string","string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","20","1","20","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] MARK_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STANDARD","SECTION","EXAM","SUBJECT_ID","BUSINESS_DATE","STATUS","NO_OF_SUCCESS","NO_FAILURES","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9","10","11","12"},
//        {"string","string","string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","20","1","20","20","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] MARK_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STANDARD","SECTION","EXAM","SUBJECT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","20","2000"},
//        {"null","null","null","null","null","null","null"},
//    };
//    
//    public static final String[][] STUDENT_MARK_BATCH_STATUS={
//        {"INSTITUTE_ID","STANDARD","SECTION","EXAM","SUBJECT_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7","8","9","10"},
//        {"string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","20","20","1","20","20"},
//        {"null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_MARK_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STANDARD","SECTION","EXAM","SUBJECT_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9","10","11"},
//        {"string","string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_MARK_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STANDARD","SECTION","EXAM","SUBJECT_ID","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4","5","6","7","8"},
//        {"string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","20","20","2000"},
//        {"null","null","null","null","null","null","null","null"},
//    };
//     public static final String[][] ATTENDANCE_BATCH_STATUS={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","STATUS","NO_OF_SUCCESS","NO_FAILURES","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7","8","9"},
//        {"string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] ATTENDANCE_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","STATUS","NO_OF_SUCCESS","NO_FAILURES","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9","10"},
//        {"string","string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] ATTENDANCE_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","20","20","2000"},
//        {"null","null","null","null","null"},
//    };
//    
//    public static final String[][] STUDENT_ATTENDANCE_BATCH_STATUS={
//        {"INSTITUTE_ID","STANDARD","SECTION","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7","8"},
//        {"string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","1","20","20"},
//        {"null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_ATTENDANCE_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STANDARD","SECTION","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8","9"},
//        {"string","string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_ATTENDANCE_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STANDARD","SECTION","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","20","20","2000"},
//        {"null","null","null","null","null","null"},
//    };
//    
//     public static final String[][] STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    
//    
//    public static final String[][] INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","1","20","20"},
//        {"null","null","null","null","null"},
//    };
//    public static final String[][] INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","1","20","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3"},
//        {"string","string","string"},
//        {"20","20","2000"},
//        {"null","null","null"},
//    };
//    public static final String[][] STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_OTHER_ACTIVITY_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    
//    
//    public static final String[][] INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","1","20","20"},
//        {"null","null","null","null","null"},
//    };
//    public static final String[][] INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","1","20","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] INSTITUTE_OTHER_ACTIVITY_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3"},
//        {"string","string","string"},
//        {"20","20","2000"},
//        {"null","null","null"},
//    };
//    public static final String[][] STUDENT_FEE_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_FEE_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_FEE_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    
//    
//    public static final String[][] INSTITUTE_FEE_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","1","20","20"},
//        {"null","null","null","null","null"},
//    };
//    public static final String[][] INSTITUTE_FEE_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","1","20","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] INSTITUTE_FEE_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3"},
//        {"string","string","string"},
//        {"20","20","2000"},
//        {"null","null","null"},
//    };
//    public static final String[][] STUDENT_NOTIFICATION_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_NOTIFICATION_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_NOTIFICATION_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    
//    
//    public static final String[][] INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","1","20","20"},
//        {"null","null","null","null","null"},
//    };
//    public static final String[][] INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","1","20","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] INSTITUTE_NOTIFICATION_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3"},
//        {"string","string","string"},
//        {"20","20","2000"},
//        {"null","null","null"},
//    };
//    public static final String[][] STUDENT_MARK_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_MARK_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_MARK_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    
//    public static final String[][] CLASS_MARK_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] CLASS_MARK_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8"},
//        {"string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] CLASS_MARK_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","20","20","2000"},
//        {"null","null","null","null","null"},
//    };
//    
//    public static final String[][] STUDENT_EXAM_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_EXAM_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_EXAM_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    
//    public static final String[][] CLASS_EXAM_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] CLASS_EXAM_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8"},
//        {"string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] CLASS_EXAM_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","20","20","2000"},
//        {"null","null","null","null","null"},
//    };
//    
//    public static final String[][] STUDENT_TIME_TABLE_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_TIME_TABLE_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_TIME_TABLE_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    
//    public static final String[][] CLASS_TIME_TABLE_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] CLASS_TIME_TABLE_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8"},
//        {"string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] CLASS_TIME_TABLE_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","20","20","2000"},
//        {"null","null","null","null","null"},
//    };
//     public static final String[][] STUDENT_ATTENDANCE_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_ATTENDANCE_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    
//    public static final String[][] CLASS_ATTENDANCE_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] CLASS_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7","8"},
//        {"string","string","string","string","string","string","string","string"},
//        {"20","20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] CLASS_ATTENDANCE_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STANDARD","SECTION","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","20","20","2000"},
//        {"null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_LEAVE_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_LEAVE_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_LEAVE_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    public static final String[][] TEACHER_LEAVE_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] TEACHER_LEAVE_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] TEACHER_LEAVE_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    
//     public static final String[][] STUDENT_CALENDER_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_CALENDER_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_CALENDER_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    public static final String[][] TEACHER_CALENDER_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] TEACHER_CALENDER_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] TEACHER_CALENDER_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    public static final String[][] TEACHER_PAYROLL_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] TEACHER_PAYROLL_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] TEACHER_PAYROLL_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    public static final String[][] STUDENT_PAYMENT_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_PAYMENT_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_PAYMENT_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    
//    
//    public static final String[][] INSTITUTE_PAYMENT_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","1","20","20"},
//        {"null","null","null","null","null"},
//    };
//    public static final String[][] INSTITUTE_PAYMENT_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","1","20","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] INSTITUTE_PAYMENT_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3"},
//        {"string","string","string"},
//        {"20","20","2000"},
//        {"null","null","null"},
//    };
//    public static final String[][] TEACHER_ATTENDANCE_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] TEACHER_ATTENDANCE_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] TEACHER_ATTENDANCE_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    public static final String[][] TEACHER_TIME_TABLE_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] TEACHER_TIME_TABLE_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] TEACHER_TIME_TABLE_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    
//    public static final String[][] STUDENT_PROFILE_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_PROFILE_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] STUDENT_PROFILE_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","STUDENT_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    public static final String[][] TEACHER_PROFILE_ARCH_BATCH_STATUS={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","20","1","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] TEACHER_PROFILE_ARCH_BATCH_STATUS_HISTORY={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","20","20","1","20","20","20"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] TEACHER_PROFILE_ARCH_BATCH_STATUS_ERROR={
//        {"INSTITUTE_ID","TEACHER_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","2000"},
//        {"null","null","null","null"},
//    };
//    public static final String[][] USER_PROFILE_ARCH_BATCH_STATUS={
//        {"USER_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","1","20","20"},
//        {"null","null","null","null","null"},
//    };
//    public static final String[][] USER_PROFILE_ARCH_BATCH_STATUS_HISTORY={
//        {"USER_ID","BUSINESS_DATE","STATUS","START_TIME","END_TIME","SEQUENCE_NO"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"20","20","1","20","20","20"},
//        {"null","null","null","null","null","null"},
//    };
//    public static final String[][] USER_PROFILE_ARCH_BATCH_STATUS_ERROR={
//        {"USER_ID","BUSINESS_DATE","ERROR"},
//        {"1","2","3"},
//        {"string","string","string"},
//        {"20","20","2000"},
//        {"null","null","null"},
//    };
//   
//    
//    public static final String[][] APP={
//        {"ERROR_MASTER","INSTITUTE_MASTER","SERVICE_TYPE_MASTER"},
//        {"25","51","79"},
//        {"1","1~10","1"},
//        {"null","null","null"},
//        {"null","null","null"},
//        {"null","null","null"},    
//    };
//     public static final String[][] ERROR_MASTER={
//        {"ERROR_CODE","ERROR_MESSAGE"},
//        {"1","2"},
//        {"string","string"},
//        {"50","65"},
//        {"null","null"},
//    };
//     
//    public static final String[][] INSTITUTE_MASTER={
//         {"INSTITUTE_ID","INSTITUTE_NAME","IMAGE_PATH","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//         {"1","2","3","4","5","6","7","8","9","10","11","12"},
//         {"string","string","string","string", "string","string","string","string","string","string","string","string","string","string","string"},
//         {"20","30","30","20","20","25","25","2","2","3","30","30"},
//         {"null","null","null","null","null","null","null","null","null","null","null","null"},
//     };
//    
//   
////     public static final String[][] INSTITUTE_MASTER={
////         {"INSTITUTE_ID","INSTITUTE_NAME"},
////         {"1","2"},
////         {"string","string"},
////         {"20","30"},
////         {"null","null"},
////     };
//     public static final String[][] SERVICE_TYPE_MASTER={
//         {"SERVICE_NAME","SERVICE_TYPE"},
//         {"1","2"},
//         {"string","string"},
//         {"30","25"},
//         {"null","null"},
//     };
//
//    public static final String[][] CLASS={
//        {"CLASS_TIMETABLE_MASTER","CLASS_STUDENT_MAPPING","CLASS_MARK_ENTRY","STUDENT_MARKS","CLASS_TIMETABLE_DETAIL","CLASS_ATTENDANCE_MASTER","CLASS_ATTENDANCE_DETAIL","CLASS_ASSIGNMENT","CLASS_FEE_MANAGEMENT","CLASS_EXAM_SCHEDULE_MASTER","CLASS_EXAM_SCHEDULE_DETAIL"},
//        {"29","30","43","46","48","52","53","57","61","69","70"},
//        {"1~2~9","1~2~3~10","1~2~3~4~11","1~2~3~4~5~9","1~2~3~4~7","1~2~3~4","1~2","1~2~3~4~16","1~2~3~13","1~2~3~10","1~2~3~4~7"},
//        {"1~24","null","null","1~2","1~49","null","null","null","null","1~71","1~72"},
//        {"30~3","null","null","5","30~3","null","null","null","null","30~3","30~3"},
//        {"null","null","null","null","null","null","null","null","null","null","null"},
//     };
//    public static final String[][] CLASS_MARK_ENTRY={
//        {"STANDARD","SECTION","EXAM","SUBJECT_ID","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//        {"1","2","3","4","5","6","7","8","9","10","11","12","13"},
//        {"string","string", "string","string","string","string","string","string","string","string","string","string","string"},
//        {"3","2","15","15","20","20","25","25","2","2","3","30","30"},
//        {"null","null","null","null","null","null","null","null","null","null","null","null","null"},
//    };
//    
//    public static final String[][] STUDENT_MARKS={
//        {"STANDARD","SECTION","EXAM","SUBJECT_ID","STUDENT_ID","GRADE","MARK","FEEDBACK","VERSION_NUMBER"},
//        {"1","2","3","4","5","6","7","8","9"},
//        {"string","string", "string","string","string","string","string","string","string"},
//        {"3","2","15","15","20","2","3","20","3"},
//        {"null","null","2","3","1","4","5","6","7"},
//    };
//    public static final String[][] CLASS_TIMETABLE_MASTER = {
//        {"STANDARD","SECTION","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//        {"1","2","3","4","5","6","7","8","9","10","11"},
//        {"string","string","string","string", "string","string","string","string","string","string","string"},
//        {"3","2","20","20","25","25","2","2","3","30","30"},
//        {"1","2","3","4","5","6","7","8","9","10","11"}
//    };
//    public static final String[][] CLASS_TIMETABLE_DETAIL={
//        {"STANDARD","SECTION","DAY","PERIOD_NO","SUBJECT_ID","TEACHER_ID","VERSION_NUMBER"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string", "string","string","string"},
//        {"3", "1", "5","2","5","30","3"},
//        {"1","2","3","4","5","6","7"},      
//    };
//    public static final String[][] CLASS_ATTENDANCE_MASTER={
//        {"STANDARD","SECTION","YEAR","MONTH","AUDIT_DETAILS"},
//        {"1","2","3","4","5"},
//        {"string","string", "string","string","string"},
//        {"3","2","4","3","500"},
//        {"null","null","null","null","null"},
//    };
//   public static final String[][] CLASS_ATTENDANCE_DETAIL={
//       {"REFERENCE_NO","STUDENT_ID","ATTENDANCE"},
//       {"1","2","3"},
//       {"string","string", "string"},
//       {"80","20","500"},
//       {"null","null","null"},
//   };
//   public static final String[][] CLASS_ASSIGNMENT={
//       {"STANDARD","SECTION","SUBJECT_ID","ASSIGNMENT_ID","ASSIGNMENT_TOPIC","DUE_DATE","TEACHER_COMMENT","CONTENT_TYPE","CONTENT_PATH","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//       {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"},
//       {"string","string", "string","string","string","string","string","string","string","string","string", "string","string","string","string","string","string","string"},
//       {"3","2","10","30","25","25","30","50","50","20","20","25","25","2","2","3","30","30"},
//       {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},
//   };
//   public static final String[][] CLASS_FEE_MANAGEMENT={
//       {"STANDARD","SECTION","FEE_ID","FEE_TYPE","AMOUNT","DUE_DATE","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//       {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"},
//       {"string","string", "string","string","string","string","string","string","string","string","string", "string","string","string","string"},
//       {"3","2","25","15","10","15","20","20","25","25","2","2","3","30","30"},
//       {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"}
//   };
//  public static final String[][]  CLASS_EXAM_SCHEDULE_MASTER={
//       {"STANDARD","SECTION","EXAM","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},  
//       {"1","2","3","4","5","6","7","8","9","10","11","12"},
//       {"string","string", "string","string","string","string","string","string","string","string","string","string"},
//       {"3","2","15","20","20","25","25","2","2","3","30","30"},
//       {"1","2","3","4","5","6","7","8","9","10","11","12"},
//  };
//  public static final String[][]  CLASS_EXAM_SCHEDULE_DETAIL={
//      {"STANDARD","SECTION","EXAM","SUBJECT_ID","DATE","HALL","VERSION_NUMBER","START_TIME_HOUR","START_TIME_MIN","END_TIME_HOUR","END_TIME_MIN"},
//      {"1","2","3","4","5","6","7","8","9","10","11"},
//      {"string","string", "string","string","string","string","string","string","string","string","string"},
//      {"3","2","15","5","15","15","3","3","3","3","3"},
//      {"null","null","null","null","null","null","null","null","null","null","null"},
//  };
//   
////   public static final String[][] STUDENT_TIMETABLE = {
////        {"STANDARD","SECTION","DAY","PERIOD_NO","SUBJECT_ID","TEACHER_SHORT_NAME","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"}, //Columnn Names ,
////        {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"}, //Column Ids 
////        {"string","string", "string","string","string","string","string","string","string","string","string","string","string","string","string"}, //Column Datatypes 
////        {"3", "1", "5","2","5","30","20","20","25","25","2","2","3","30","30"}, // Column Lengths
////        {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"}//Column Level relationship
////    }; 
//     public static final String[][] CLASS_STUDENT_MAPPING={
//        {"STANDARD","SECTION","STUDENT_ID","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//        {"1","2","3","4","5","6","7","8","9","10","11","12"},
//        {"string","string","string","string", "string","string","string","string","string","string","string","string"},
//        {"3","2","20","20","20","25","25","2","2","3","30","30"},
//        {"null","null","null","null","null","null","null","null","null","null","null","null"},
//    };
//    
//    
//    
//    
//    
//    
//    public static final String[][] STUDENT = {
//        {"SVW_STUDENT_PROFILE", "SVW_STUDENT_MARKS", "SVW_TEACHER_PROFILE", "SVW_USER_PROFILE_MAPPING","SVW_STUDENT_TIMETABLE_MASTER","SVW_EXISTING_MEDICAL_DETAILS","SVW_CONTACT_PERSON_DETAILS","SVW_FAMILY_DETAILS","SVW_STUDENT_ATTENDANCE","SVW_STUDENT_PRORESS_CARD","SVW_STUDENT_TIMETABLE_DETAIL","SVW_STUDENT_LEAVE_MANAGEMENT","SVW_STUDENT_ASSIGNMENT","SVW_STUDENT_FEE_MANAGEMENT","SVW_STUDENT_PAYMENT","SVW_STUDENT_OTHER_ACTIVITY","SVW_STUDENT_EXAM_SCHEDULE_MASTER","SVW_STUDENT_EXAM_SCHEDULE_DETAIL","SVW_STUDENT_CALENDER","SVW_STUDENT_LOGIN_MAPPING","SVW_STUDENT_NOTIFICATION"},//table name
//        {"1", "2", "3", "4","24","37","38","39","41","45","49","55","60","63","64","65","71","72","73","84","123"},//tableid    
//        {"1~20", "1~2~3~7", "1~2", "1~2~3~4~5","1~8","2~4","2~9","2~9","1~2~3","1~2~11","1~2~3~6","1~2~3~14","3~16","2~13","2~17","2~16","1~2~9","1~2~3~6","1~2~3","1~2~3","2~4~12"},//column ids that forms primary key for the corresponding table
//        {"null", "null", "null", "null","null", "null", "null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},//Relationship .% separates each relationship the tablename have.as an example take 3~8%4~20 here 3 is a fileid and 8 is a table id it means that 10 th table in 3rd file has to be updated similarly 22nd table in 4th filetype has to be updated
//        {"null", "null", "null", "null","null", "null", "null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},//filenames where we have to change columns for the above relationship.and % separates for the each realtionship or each file. institute id and studentid together forms the filename for the student view.instituteid and teacherid together forms the filename for the teacher view tables.instituteid and managementid forms for the management view
//        {"null", "null", "null", "null","null", "null", "null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},//ON-LINE change is required or not for the above relationship.yes means online is required and % separates for the each relationship
//    };
//
//    public static final String[][] SVW_STUDENT_PROFILE = {
//       {"STUDENT_ID","STUDENT_NAME", "STANDARD", "SECTION", "DOB", "BLOODGROUP","ADDRESSLINE1","ADDRESSLINE2","ADDRESSLINE3","ADDRESSLINE4","ADDRESSLINE5","IMAGE_PATH","NOTES","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS","EXISTING_MEDICAL_DETAIL"}, //Columnn Names ,
//        {"1", "2", "3", "4", "5", "6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"}, //Column Ids 
//        {"string", "string", "string", "string", "string", "string", "string", "string", "string", "string", "string", "string", "string", "string", "string", "string", "string", "string", "string", "string", "string","string","string"}, //Column Datatypes 
//        {"20","25", "3", "2", "25", "5","30","30","30","30","30","60","100","20","20","25","25","2","2","3","30","30","20"}, // Column Lengths
//        {"3","null","1","2","null","null","null","null","null","null","null","null","null","4","5","6","7","8","9","10","11","12","NULL"}//Column Level relationship
//    };
//    
//    //NotUsed
//    public static final String[][] SVW_EXISTING_MEDICAL_DETAILS={
//        {"STUDENT_ID","MEDICAL_DETAIL_ID","MEDICAL_DETAILS","VERSION_NUMBER"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},        
//        {"20","40","30","3"},
//        {"null","null","null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] SVW_CONTACT_PERSON_DETAILS={
//        {"STUDENT_ID","CP_ID","CP_NAME","CP_RELATIONSHIP","CP_OCCUPATION","CP_MAILID","CP_CONTACTNO","IMAGE_PATH","VERSION_NUMBER"},
//        {"1","2","3","4","5","6","7","8","9"},
//        {"string","string","string","string","string","string","string","string","string"},
//        {"20","40","30","15","15","20","15","60","3"},
//        {"null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] SVW_FAMILY_DETAILS={
//        {"STUDENT_ID","MEMBER_ID","MEMBER_NAME","MEMBER_RELATIONSHIP","MEMBER_OCCUPATION","MEMBER_EMAILID","MEMBER_CONTACTNO","IMAGE_PATH","VERSION_NUMBER"},
//        {"1","2","3","4","5","6","7","8","9"},
//        {"string","string","string","string","string","string","string","string","string"},
//        {"20","40","30","15","15","20","15","60","3"},
//        {"null","null","null","null","null","null","null","null","null"},
//    };
//    
//    public static final String[][] SVW_STUDENT_ATTENDANCE={
//        {"STUDENT_ID","YEAR","MONTH","ATTENDANCE"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","4","2","1000"},
//        {"null","null","null","null"},
//    };
//    
//    
//
//    public static final String[][] SVW_STUDENT_MARKS = {
//        {"STUDENT_ID","EXAM","SUBJECT_ID", "GRADE", "MARK","TEACHER_FEEDBACK","VERSION_NUMBER"}, //Columnn Names ,
//        {"1", "2", "3", "4","5","6","7"}, //Column Ids 
//        {"string", "string", "string", "string", "string", "string", "string"}, //Column Datatypes 
//        {"20", "15", "15", "2","3","20","3"}, // Column Lengths
//        {"null","null","null","null","null","null","null"}//Column Level relationship
//    };
//    public static final String[][]  SVW_STUDENT_PRORESS_CARD={
//        {"STUDENT_ID","EXAM","TOTAL","RANK","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//        {"1","2","3","4","5","6","7","8","9","10","11","12","13"},
//        {"string","string","string","string","string","string","string","string","string","string","string","string","string"},
//        {"20","15","4","3","20","20","25","25","2","2","3","30","30"},
//        {"null","null","null","null","null","null","null","null","null","null","null","null","null"}
//    };
//   public static final String[][] SVW_STUDENT_LEAVE_MANAGEMENT={
//       {"STUDENT_ID","FROM","TO","TYPE","REASON","STATUS","REMARKS","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS","FROM_NOON","TO_NOON"},
//       {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"},
//       {"string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string"},
//       {"20","15","15","15","20","15","20","20","20","25","25","2","2","3","30","30","10","10"},
//       {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},
//       
//   };
//   public static final String[][] SVW_STUDENT_ASSIGNMENT={
//       {"STUDENT_ID","SUBJECT_ID","ASSIGNMENT_ID","ASSIGNMENT_DESCRIPTION","DUE_DATE","COMPLETED_DATE","STATUS","TEACHER_COMMENTS","PARENT_COMMENTS","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS","ASSIGNMENT_TYPE"},
//       {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19"},
//       {"string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string"},
//       {"20","20","5","30","15","15","15","30","30","20","20","25","25","2","2","3","30","30","20"},
//       {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},
//   };
//   
//   //Not used
//    public static final String[][] SVW_TEACHER_PROFILE = {
//        {"INSTITUTE_ID", "TEACHER_ID", "TEACHER_NAME", "ADDRESS"}, //Columnn Names ,
//        {"1", "2", "3", "4"}, //Column Ids 
//        {"string", "string", "string", "string"}, //Column Datatypes 
//        {"20", "20", "20", "100"}, // Column Lengths
//        {"null","null","null","null"}//Column Level relationship
//    };
//    
//    //Not used
//    public static final String[][] SVW_USER_PROFILE_MAPPING = {
//        {"USER_NAME", "INSTITUTEID", "STUDENTID", "STANDARD", "SECTION"}, //Columnn Names ,
//        {"1", "2", "3", "4", "5"}, //Column Ids 
//        {"string", "string", "string", "string", "string"}, //Column Datatypes 
//        {"20", "20", "20", "20", "20"}, // Column Lengths
//        {"null","null","null","null","null"}//Column Level relationship
//    };
//     public static final String[][] SVW_STUDENT_TIMETABLE_MASTER = {
//        {"STUDENT_ID","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//        {"1","2","3","4","5","6","7","8","9","10"},
//        {"string","string","string", "string","string","string","string","string","string","string"},
//        {"20","20","20","25","25","2","2","3","30","30"},
//        {"null","null","null","null","null","null","null","null","null","null"}
//    };
//    public static final String[][] SVW_STUDENT_TIMETABLE_DETAIL={
//        {"STUDENT_ID","DAY","PERIOD_NO","SUBJECT_ID","TEACHER_ID","VERSION_NUMBER"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string", "string","string"},
//        {"20", "5","2","5","30","3"},
//        {"null","null","null","null","null","null"},      
//    };
//     public static final String[][] SVW_STUDENT_FEE_MANAGEMENT={
//       {"STUDENT_ID","FEE_ID","FEE_TYPE","AMOUNT","DUE_DATE","STATUS","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS","FEE_PAID","OUTSTANDING","PAID_DATE"},
//       {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"},
//       {"string","string", "string","string","string","string","string","string","string","string","string", "string","string","string","string", "string","string","string"},
//       {"20","25","15","10","15","3","20","20","25","25","2","2","3","30","30","20","20","20"},
//       {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"}
//   };
//     public static final String[][] SVW_STUDENT_PAYMENT={
//         {"STUDENT_ID","PAYMENT_ID","PAYMENT_MODE","PAYMENT_PAID","PAYMENT_DATE","FEE_ID","FEE_TYPE","AMOUNT","OUT_STANDING","REMARKS","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//         {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19"},
//         {"string","string", "string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string"},
//         {"20","20","20","20","20","20","20","20","20","20","20","20","25","25","2","2","3","30","30"},
//         {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},
//     };
//     
//     public static final String[][] SVW_STUDENT_OTHER_ACTIVITY={
//         {"STUDENT_ID","ACTIVITY_ID","ACTIVITY_NAME","ACTIVITY_TYPE","LEVEL","VENUE","DATE","RESULT","INTREST","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//         {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"},
//         {"string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string"},
//         {"20","20","20","20","20","20","20","20","5","20","20","25","25","2","2","3","30","30"},
//         {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},
//     };
//      public static final String[][]  SVW_STUDENT_EXAM_SCHEDULE_MASTER={
//       {"STUDENT_ID","EXAM","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},  
//       {"1","2","3","4","5","6","7","8","9","10","11"},
//       {"string","string", "string","string","string","string","string","string","string","string","string"},
//       {"20","15","20","20","25","25","2","2","3","30","30"},
//       {"null","null","null","null","null","null","null","null","null","null","null"},
//  };
//  public static final String[][]  SVW_STUDENT_EXAM_SCHEDULE_DETAIL={
//      {"STUDENT_ID","EXAM","SUBJECT_ID","DATE","HALL","VERSION_NUMBER","START_TIME_HOUR","START_TIME_MIN","END_TIME_HOUR","END_TIME_MIN"},
//      {"1","2","3","4","5","6","7","8","9","10"},
//      {"string","string", "string","string","string","string","string","string","string","string"},
//      {"20","15","5","15","15","3","3","3","3","3"},
//      {"null","null","null","null","null","null","null","null","null","null"},
//  };
//  
//  public static final String[][]  SVW_STUDENT_CALENDER={
//      {"STUDENT_ID","YEAR","MONTH","EVENTS"},
//      {"1","2","3","4"},
//      {"string","string", "string","string"},
//      {"20","4","2","500"},
//      {"null","null","null","null"},
//  };
//  public static final String[][]  SVW_STUDENT_NOTIFICATION={
//      {"STUDENT_ID","NOTIFICATION_ID","NOTIFICATION_TYPE","DATE","MESSAGE","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//      {"1","2","3","4","5","6","7","8","9","10","11","12","13","14"},
//      {"string","string", "string","string","string", "string","string","string","string","string","string","string","string","string"},
//      {"20","20","25","25","500","20","20","25","25","2","2","3","30","30"},
//      {"null","null","null","null","null","null","null","null","null","null","null","null","null","null"},
//  };
//  
//  //Not Used
//    public static final String[][]  SVW_STUDENT_LOGIN_MAPPING={ 
//      {"STUDENT_ID","USER_ID","VERSION_NUMBER"},
//      {"1","2","3"},
//      {"string","string","string"},
//      {"25","25","4"},
//      {"null","null","null"},
//  };
//
//
//    public static final String[][] USER = {
//        //Cohesive1_UnitTest_4 starts here
//        {"UVW_USER_PROFILE", "UVW_USER_PROFILE_MAPPING", "UVW_STUDENT_PROFILE","UVW_USER_MASTER","UVW_IN_LOG","UVW_OUT_LOG","UVW_USER_ROLE_MASTER","UVW_USER_CREDENTIALS","UVW_USER_ROLE_DETAIL","UVW_USER_ROLE_MAPPING","UVW_PARENT_STUDENT_ROLEMAPPING","UVW_CLASS_ENTITY_ROLEMAPPING","UVW_TEACHER_ENTITY_ROLEMAPPING","UVW_INSTITUTE_ENTITY_ROLEMAPPING"},//table name
////        {"UVW_USER_PROFILE", "UVW_USER_PROFILE_MAPPING", "UVW_STUDENT_PROFILE","UVW_USER_MASTER","UVW_IN_LOG","UVW_OUT_LOG","UVW_USER_ROLE_MASTER","UVW_USER_CREDENTIALS","UVW_USER_ROLE_DETAIL","UVW_USER_ROLE_MAPPING","UVW_PARENT_ROLEMAPPING","UVW_STUDENT_CLASS_ROLEMAPPING","UVW_TEACHER_ROLEMAPPING","UVW_INSTITUTE_ROLEMAPPING"},//table name
//        //Cohesive1_UnitTest_4 ends here
//        {"5", "6", "7","26","27","28","75","76","77","78","80","81","82","83"},//tableid  
//        //Cohesive1_UnitTest_6 starts here  
//        {"1~11", "1~2~3~4~5", "1~2~3~4","1~9","1~7","1","1~9","1~4","1~2~10","1~2~6","1~3~4","1~2~5","1~2~5","1~2~4"},//column ids that forms primary key for the corresponding table
////        {"1~11", "1~2~3~4~5", "1~2~3~4","1~9","1~7","1","1~9","1~4","1~2~16","1~2~6","1~2~3","1~2~5","1~2~5","1~2~4"},//column ids that forms primary key for the corresponding table
//        //Cohesive1_UnitTest_6 ends here
//        {"null", "null", "null","null","null","null","null","null","null","null","null","null","null","null"},//Relationship .% separates each relationship the tablename have
//        {"null", "null","null","null","null","null","null","null","null","null","null","null","null","null"},//filenames where we have to change columns for the above relationship.and % separates for the each realtionship
//        {"null", "null","null","null","null","null","null","null","null","null","null","null","null","null"}//ON-LINE change is required or not for the above relationship.yes means online is required and % separates for the each relationship
//    };
//
//    //Not used
//    public static final String[][] UVW_USER_MASTER = {
//        {"USER_ID", "USER_NAME","MAKER_ID","CHECKER_ID","MAKER_DATESTAMP","CHECKER_DATESTAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"}, //Columnn Names ,
//        {"1", "2","3","4","5","6","7","8","9","10","11"}, //Column Ids 
//        {"string", "string","string","string", "string","string","string","string","string","string","string"}, //Column Datatypes 
//        {"20", "20","20","20","25","25","2","2","3","30","30"}, // Column Lengths
//        {"null","null", "null","null","null","null","null","null","null","null","null"}//Column Level relationship
//    };
//    
//    //Not used
//    public static final String[][] UVW_USER_PROFILE_MAPPING = {
//        {"USER_NAME", "INSTITUTEID", "STUDENTID", "STANDARD", "SECTION"}, //Columnn Names ,
//        {"1", "2", "3", "4", "5"}, //Column Ids 
//        {"string", "string", "string", "string", "string"}, //Column Datatypes 
//        {"20", "20", "20", "20", "20"}, // Column Lengths
//        {"null","null","null","null","null"}//Column Level relationship
//    };
//
//    //Not used
//    public static final String[][] UVW_STUDENT_PROFILE = {
//        {"STUDENT_ID", "STANDARD", "SECTION", "INSTITUTE_ID", "STUDENT_NAME", "ADDRESS"}, //Columnn Names ,
//        {"1", "2", "3", "4", "5", "6"}, //Column Ids 
//        {"string", "string", "string", "string", "string", "string"}, //Column Datatypes 
//        {"20", "20", "20", "20", "20", "100"}, // Column Lengths
//        {"null","null","null","null", "null", "null"}//Column Level relationship
//    };
//         public static final String[][] UVW_USER_PROFILE={
//        {"USER_ID","USER_NAME","EMAIL_ID","MOBILE_NO","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS","USER_TYPE"},
//        {"1","2","3","4","5","6","7","8","9","10","11","12","13","14"},
//        {"string","string","string","string","string","string","string","string","string","string","string","string","string","string"},
//        {"25","20","25","10","20","20","25","25","2","2","3","30","30","4"},
//        {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},
//    };
//     public static final String[][] UVW_USER_CREDENTIALS={
//        {"USER_ID","PASSWORD","EXPIRY_DATE","VERSION_NUMBER"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"25","25","10","3"},
//        {"null","null","null","null"}
//     };
//    public static final String[][] UVW_IN_LOG={
//        {"MESSAGE_ID","SERVICE","OPERATION","BUSINESS_ENTITY","REQUEST_JSON","TIME_STAMP","SOURCE"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"40","30","20","20","5000","25","17"},
//        {"null","null","null","null","null","null","null"},
//    };
//    public static final String[][] UVW_OUT_LOG={
//        {"MESSAGE_ID","CORRELATION_ID","SERVICE","OPERATION","BUSINESS_ENTITY","STATUS","RESPONSE_JSON","TIME_STAMP","SOURCE"},
//        {"1","2","3","4","5","6","7","8","9"},
//        {"string","string","string","string","string","string","string","string","string"},
//        {"40","40","30","15","20","10","5000","25","17"},
//        {"null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] UVW_USER_ROLE_MASTER={
//        {"ROLE_ID","ROLE_DESCRIPTION","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//        {"1","2","3","4","5","6","7","8","9","10","11"},
//        {"string","string", "string", "string", "string","string","string", "string", "string", "string","string"},
//        {"25","25","20","20","25","25","2","2","3","30","30"},
//        {"null","null","null","null","null","null","null","null","null","null","null"},
//    };
//    
//    //Cohesive1_UnitTest_8 starts here
//    public static final String[][] UVW_USER_ROLE_DETAIL={
//        {"ROLE_ID","FUNCTION_ID","CREATE","MODIFY","DELETE","VIEW","AUTH","AUTOAUTH","REJECT","VERSION_NUMBER"},
//        {"1","2","3","4","5","6","7","8","9","10"},
//        {"string","string", "string", "string", "string","string","string", "string", "string", "string"},
//        {"25","25","15","15","15","15","15","15","15","3"},
//        {"null","null","null","null","null","null","null","null","null","null"},
//    };
//
//    
//    //Cohesive1_UnitTest_8 ends here
//    //Not used
//    public static final String[][] UVW_USER_ROLE_MAPPING={
//        {"USER_ID","ROLE_ID","STANDARD","SECTION","INSTITUTE_ID","VERSION_NUMBER"},
//        {"1","2","3","4","5","6"},
//        {"string","string","string","string","string","string"},
//        {"25","25","3","3","20","3"},
//        {"null","null","null","null","null","null"},
//    };
//    
//    //Cohesive1_UnitTest_4 starts here
//     public static final String[][] UVW_PARENT_STUDENT_ROLEMAPPING={
////    public static final String[][] UVW_PARENT_ROLEMAPPING={
//    //Cohesive1_UnitTest_4 ends here     
//       //Cohesive1_UnitTest_5 starts here  
//       {"USER_ID","ROLE_ID","STUDENT_ID","VERSION_NUMBER"},  
////     {"USER_ID","ROLE_ID","VERSION_NUMBER"},
//       ////Cohesive1_UnitTest_5 ends here  
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","3"},
//        {"null","null","null","null"},
//    };
//  // Cohesive1_UnitTest_4  starts here
//     public static final String[][] UVW_CLASS_ENTITY_ROLEMAPPING={
////    public static final String[][] UVW_STUDENT_CLASS_ROLEMAPPING={
//  // Cohesive1_UnitTest_4 ends here       
//        {"USER_ID","ROLE_ID","STANDARD","SECTION","VERSION_NUMBER"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","4","4","4"},
//        {"null","null","null","null","null"},
//    };
// //Cohesive1_UnitTest_4 starts here    
//    public static final String[][] UVW_TEACHER_ENTITY_ROLEMAPPING={ 
////    public static final String[][] UVW_TEACHER_ENTITY_ROLEMAPPING={
//  //Cohesive1_UnitTest_4 ends here      
//        {"USER_ID","ROLE_ID","INSTITUTE_ID","TEACHER_ID","VERSION_NUMBER"},
//        {"1","2","3","4","5"},
//        {"string","string","string","string","string"},
//        {"20","20","20","20","4"},
//        {"null","null","null","null","null"},
//    };
//    
////Cohesive1_UnitTest_4 starts here    
//     public static final String[][] UVW_INSTITUTE_ENTITY_ROLEMAPPING={
////     public static final String[][] UVW_INSTITUTE_ROLEMAPPING={
////Cohesive1_UnitTest_4 ends here         
//        {"USER_ID","ROLE_ID","INSTITUTE_ID","VERSION_NUMBER"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","20","20","4"},
//        {"null","null","null","null"},
//    };
//
//    public static final String[][] TEACHER = {
//        {"TVW_TEACHER_PROFILE", "TVW_TEACHER_EXAM_DETAILS", "TVW_INSTITUTE_PROFILE","TVW_TEACHER_TIMETABLE_MASTER","TVW_EXISTING_MEDICAL_DETAILS","TVW_CONTACT_PERSON_DETAILS","TVW_TEACHER_ATTENDANCE","TVW_TEACHER_TIMETABLE_DETAIL","TVW_TEACHER_LEAVE_MANAGEMENT","TVW_PAYROLL","TVW_TEACHER_CALENDER"},//table name
//        {"8", "9", "11","23","31","32","42","47","56","66","74"},//tableid  
//        {"1~22", "1~2~3~4~5", "1","1~8","2~4","2~9","1~2~3","1~2~3~7","1~2~3~14","1~2~3~11","1~2~3"},//column ids that forms primary key for the corresponding table
//        {"null", "1~2", "4~13","null","null","null","null","null","null","null","null"},//Relationship  separates each relationship the tablename have
//        {"null", "2", "3~6~1","null","null","null","null","null","null","null","null"},//filenames where we have to change columns for the above relationship.and % separates for the each realtionship.tilda seperats table id and columnids.Comma seperates column ids.
//        {"null", "YES", "YES","null","null","null","null","null","null","null","null"}//ON-LINE change is required or not for the above relationship.yes means online is required and % separates for the each relationship
//    };
//
//    public static final String[][] TVW_TEACHER_PROFILE = {
//        {"TEACHER_ID","TEACHER_NAME", "QUALIFICATION","STANDARD","SECTION","DOB","CONTACT_NO","EMAIL_ID","BLOOD_GROUP","ADDRESSLINE1","ADDRESSLINE2","ADDRESSLINE3","ADDRESSLINE4","ADDRESSLINE5","IMAGE_PATH","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS","SHORT_NAME","EXISTING_MEDICAL_DETAIL"}, //Columnn Names ,
//        {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26"}, //Column Ids 
//        {"string","string", "string", "string", "string","string","string", "string", "string", "string","string","string", "string", "string", "string","string","string", "string", "string", "string","string","string", "string","string","string","string"}, //Column Datatypes 
//        {"20","25", "30", "3", "2","25","15","30","5","30","30","30","30","30","60","20","20","25","25","2","2","3","30","30","5","20"}, // Column Lengths
//        {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"}//Column Level relationship
//    };
//    public static final String[][] TVW_TEACHER_EXAM_DETAILS = {
//        {"EXAME_ID", "STUDENTID", "STANDARD", "SECTION", "SUBJECT_ID", "MARK", "GRADE", "DAY"}, //Columnn Names ,
//        {"1", "2", "3", "4", "5", "6", "7", "8"}, //Column Ids 
//        {"string", "string", "string", "string", "string", "string", "string", "string"}, //Column Datatypes 
//        {"20", "20", "20", "20", "20", "10", "20", "20"}, // Column Lengths 
//        {"1","null","null","null","2","3","4","null"}//Column Level relationship
//    };
//
//    public static final String[][] TVW_TEACHER_TIMETABLE_MASTER = {
//        {"TEACHER_ID","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},  
//        {"1","2","3","4","5","6","7","8","9","10"},
//        {"string","string", "string","string","string","string","string","string","string","string"},
//        {"20","20","20","25","25","2","2","3","30","30"},
//        {"null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] TVW_TEACHER_TIMETABLE_DETAIL={
//        {"TEACHER_ID","DAY","PERIOD_NO","SUBJECT_ID","STANDARD","SECTION","VERSION_NUMBER"},
//        {"1","2","3","4","5","6","7"},
//        {"string","string","string","string","string","string","string"},
//        {"20","5", "5","6","5","5","3"},
//        {"null","null","null","null","null","null","null"}
//    };
//    
//
//    public static final String[][] TVW_EXISTING_MEDICAL_DETAILS={
//        {"TEACHER_ID","MEDICAL_DETAIL_ID","MEDICAL_DETAILS","VERSION_NUMBER"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},        
//        {"20","40","30","3"},
//        {"null","null","null","null","null","null","null","null","null","null","null","null"},
//    };
//    public static final String[][] TVW_CONTACT_PERSON_DETAILS={
//        {"TEACHER_ID","CP_ID","CP_NAME","CP_RELATIONSHIP","CP_OCCUPATION","CP_MAILID","CP_CONTACTNO","IMAGE_PATH","VERSION_NUMBER"},
//        {"1","2","3","4","5","6","7","8","9"},
//        {"string","string","string","string","string","string","string","string","string"},
//        {"20","40","30","15","15","20","15","60","3"},
//        {"null","null","null","null","null","null","null","null","null"},
//    };
//
//   public static final String[][]  TVW_TEACHER_CALENDER={
//      {"TEACHER_ID","YEAR","MONTH","EVENTS"},
//      {"1","2","3","4"},
//      {"string","string", "string","string"},
//      {"20","4","2","500"},
//      {"null","null","null","null"},
//  };
//
//
//   public static final String[][] TVW_TEACHER_ATTENDANCE={
//        {"TEACHER_ID","YEAR","MONTH","ATTENDANCE"},
//        {"1","2","3","4"},
//        {"string","string","string","string"},
//        {"20","4","2","1000"},
//        {"null","null","null","null"},
//    };
//
//     public static final String[][] TVW_TEACHER_LEAVE_MANAGEMENT={
//       {"TEACHER_ID","FROM","TO","TYPE","REASON","STATUS","REMARKS","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS","FROM_NOON","TO_NOON"},
//       {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"},
//       {"string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string"},
//       {"20","15","15","15","20","15","20","20","20","25","25","2","2","3","30","30","15","15"},
//       {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},
//       
//   };
//   public static final String[][] TVW_PAYROLL={
//       {"TEACHER_ID","YEAR","MONTH","PATH","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//       {"1","2","3","4","5","6","7","8","9","10","11","12","13"},
//       {"string","string","string","string","string","string","string","string","string","string","string","string","string"},
//       {"20","4","2","30","20","20","25","25","2","2","3","30","30"},
//       {"null","null","null","null","null","null","null","null","null","null","null","null","null"},
//   };  
//
//    public static final String[][] TVW_INSTITUTE_PROFILE = {
//        {"INSTITUTE_ID", "INSTITUTE_NAME", "ADDRESS"}, //Columnn Names ,
//        {"1", "2", "3"}, //Column Ids 
//        {"string", "string", "string"}, //Column Datatypes 
//        {"20", "100", "100"}, // Column Lengths     
//        {"null","null","null"}//Column Level relationship
//    };
//    public static final String[][] MANAGEMENT = {
//        {"MVW_MANAGEMENT_EXAM_DETAILS", "MVW_STUDENT_PROFILE", "MVW_TEACHER_PROFILE"},//table name
//        {"12", "13", "14"},//tableid
//        {"1~2~3~4~5~6", "1~2~3~4", "1~2"},//column ids that forms primary key for the corresponding table   
//        {"null", "null", "null"},//Relationship .% separates each relationship the tablename have
//        {"null", "null", "null"},//filenames where we have to change columns for the above relationship.and % separates for the each realtionship
//        {"null", "null", "null"}//ON-LINE change is required or not for the above relationship.yes means online is required and % separates for the each relationship
//    };
//    public static final String[][] MVW_MANAGEMENT_EXAM_DETAILS = {
//        {"STANDARD", "SECTION", "EXAMID", "SUBJECTID", "TEACHERID", "STUDENTID", "GRADE", "MARK"}, //Columnn Names ,
//        {"1", "2", "3", "4", "5", "6", "7", "8"}, //Column Ids 
//        {"string", "string", "string", "string", "string", "string", "string", "float"}, //Column Datatypes 
//        {"20", "20", "20", "20", "20", "20", "20", "10"},// Column Lengths  
//        {"null","null","null","null","null","null","null","null"}//Column Level relationship
//
//    };
//    public static final String[][] MVW_STUDENT_PROFILE = {
//        {"STUDENT_ID", "STANDARD", "SECTION", "INSTITUTE_ID", "STUDENT_NAME", "ADDRESS"}, //Columnn Names ,
//        {"1", "2", "3", "4", "5", "6"}, //Column Ids 
//        {"string", "string", "string", "string", "string", "string"}, //Column Datatypes 
//        {"20", "20", "20", "20", "20", "100"}, // Column Lengths
//        {"null","null","null","null","null","null"}//Column Level relationship
//    };
//
//    public static final String[][] MVW_TEACHER_PROFILE = {
//        {"INSTITUTE_ID", "TEACHER_ID", "TEACHER_NAME", "ADDRESS"}, //Columnn Names ,
//        {"1", "2", "3", "4"}, //Column Ids 
//        {"string", "string", "string", "string"}, //Column Datatypes 
//        {"20", "20", "20", "100"} ,// Coumn Lengths
//        {"null","null","null","null"}//Column Level relationship
//            
//    };
//
//    public static final String[][] INSTITUTE = {
//        {"IVW_INSTITUTE_PROFILE", "IVW_INSTITUTE_EXAM_DETAILS", "IVW_INSTITUTE_EXAM_MASTER", "IVW_STANDARD_MASTER", "IVW_SUBJECT_DETAILS", "IVW_SUBJECT_MASTER", "IVW_TEACHER_MASTER", "IVW_STUDENT_PROFILE","IVW_STUDENT_MASTER","IVW_SUBJECT_GRADE_MASTER","IVW_PERIOD_MASTER","IVW_CONTENT_TYPE_MASTER","IVW_LEAVE_TYPE_MASTER","IVW_FEE_TYPE_MASTER","IVW_NOTIFICATION_TYPE_MASTER","IVW_STUDENT_LOGIN_MAPPING","IVW_CLASS_LEVEL_CONFIGURATION_MASTER","IVW_HOLIDAY_MAINTANENCE","IVW_NOTIFICATION_MASTER","IVW_INSTITUTE_PAYMENT","IVW_GROUP_MAPPING_MASTER","IVW_GROUP_MAPPING_DETAIL","IVW_OTHER_ACTIVITY","IVW_FEE_MANAGEMENT","IVW_ASSIGNMENT","INSTITUTE_CURRENT_DATE","RETENTION_PERIOD"},//table name
//        {"15", "16", "17", "18", "19", "20", "21", "22","36","44","54","58","59","62","68","85","86","87","67","88","89","90","91","92","93","98","208"},//tableid  
//        {"1", "1~2~3~4", "1~2~4", "1~2~3~5", "1~2~3~4", "1~2~4", "1~12", "1~2~3~4","1~11","1~2~5","1~2~3~4~9","1","1","1~2~4","1~2~4","1~2~3","1~8","1~2~3~11","2~17","3~18","2~10","2~3~4~5~6","3~16","3~14","3~16","1","1~2"},//column ids that forms primary key for the corresponding table
//        {"null", "null", "null", "null", "null", "null", "null", "null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},//Relationship .% separates each relationship the tablename have
//        {"null", "null", "null", "null", "null", "null", "null", "null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},//filenames where we have to change columns for the above relationship.and % separates for the each realtionship
//        {"NO", "null", "null", "null", "null", "null", "null", "YES","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"}//ON-LINE change is required or not for the above relationship.yes means online is required and % separates for the each relationship
//    };
//    public static final String[][] IVW_INSTITUTE_PROFILE = {
//       //Cohesive1_Dev_1 starts
//        /*{"INSTITUTE_ID", "INSTITUTE_NAME", "ADDRESS"}, //Columnn Names
//        {"1", "2", "3"}, //Column Ids 
//        {"string", "string", "string"}, //Column Datatypes 
//        {"20", "100", "100"}, // Column Lengths */
//        //New colummn remarks inroduced
//       {"INSTITUTE_ID", "INSTITUTE_NAME", "ADDRESS","REMARKS"}, //Columnn Names 
//        {"1", "2", "3","4"}, //Column Ids 
//        {"string", "string", "string","string"}, //Column Datatypes 
//        {"20", "100", "100","1000"}, // Column Lengths  
//        {"null","null","null","null"}//Column Level relationship
//    //Cohesive1_Dev_1 ends
//       
//    };
//    public static final String[][] IVW_INSTITUTE_EXAM_DETAILS = {
//        {"EXAM_ID", "SUBJECTID", "STANDARD", "SECTION", "TOTALMARK", "DATEOFEXAM"}, //Columnn Names ,
//        {"1", "2", "3", "4", "5", "6"}, //Column Ids 
//        {"string", "string", "string", "string", "float", "DATE"}, //Column Datatypes 
//        {"20", "20", "20", "20", "20", "20", "10", "20"}, // Column Lengths
//        {"null","null","null","null","null","null","null","null"}    //Column Level relationship
//    };
//    public static final String[][] IVW_INSTITUTE_EXAM_MASTER = {
//        {"INSTITUTE_ID","EXAM","DESCRIPTION","VERSION_NUMBER"}, //Columnn Names ,
//        {"1","2","3","4"}, //Column Ids 
//        {"string","string","string","string"}, //Column Datatypes 
//        {"20","20","20","5"}, // Column Lengths
//        {"null","null","null","null"}//Column Level relationship
//    };
//
//    public static final String[][] IVW_STANDARD_MASTER = {
//        {"INSTITUTE_ID","STANDARD", "SECTION", "TEACHER_ID","VERSION_NUMBER"}, //Columnn Names ,
//        {"1", "2", "3","4","5"}, //Column Ids 
//        {"string", "string", "string", "string", "string"}, //Column Datatypes 
//        {"20", "20", "20","20","5"}, // Column Lengths
//        {"null","null","null","null","null"}    //Column Level relationship
//    };
//    
//
//
//    public static final String[][] IVW_SUBJECT_DETAILS = {
//        {"SUBJECT", "STANDARD", "SECTION", "TEACHERID", "PERIOD", "DAY"}, //Columnn Names ,
//        {"1", "2", "3", "4", "5", "6"}, //Column Ids 
//        {"string", "string", "string", "string", "string", "string"}, //Column Datatypes 
//        {"20", "20", "20", "20", "20", "20"}, // Column Lengths 
//        {"null","null","null","null","null","null"}//Column Level relationship
//            
//            
//    }; 
//    
//     public static final String[][] IVW_SUBJECT_MASTER = {
//        {"INSTITUTE_ID","SUBJECT_ID", "SUBJECT_NAME","VERSION_NUMBER"}, //Columnn Names ,
//        {"1", "2","3","4"}, //Column Ids 
//        {"string", "string","string","string"}, //Column Datatypes 
//        {"20", "20","20","5"}, // Column Lengths
//        {"null","null","null","null"}//Column Level relationship
//    };
//
//    
//     public static final String[][] IVW_SUBJECT_GRADE_MASTER = {
//        {"INSTITUTE_ID","GRADE", "FROM","TO","VERSION_NUMBER"}, //Columnn Names ,
//        {"1", "2","3","4","5"}, //Column Ids 
//        {"string", "string","string", "string","string"}, //Column Datatypes 
//        {"20", "2","20","20","5"}, // Column Lengths
//        {"null","null","null","null","null"}//Column Level relationship
//    };
//
//    public static final String[][] IVW_TEACHER_MASTER = {
//        {"TEACHER_ID", "TEACHER_NAME","STANDARD","SECTION","TEACHER_SHORT_NAME","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"}, //Columnn Names ,
//        {"1", "2","3","4","5","6","7","8","9","10","11","12","13","14"}, //Column Ids 
//        {"string", "string","string","string","string", "string","string","string","string", "string", "string","string", "string", "string"}, //Column Datatypes 
//        {"20", "20","3","2","5","20","20","25","25","2","2","3","30","30"}, // Column Lengths
//        {"null","null","null","null","null","null","null","null","null","null","null","null","null","null"}//Column Level relationship
//    };
//
//    public static final String[][] IVW_STUDENT_MASTER={
//       {"STUDENT_ID","STUDENT_NAME","STANDARD","SECTION","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//       {"1","2","3","4","5","6","7","8","9","10","11","12","13"},
//       {"string", "string","string","string","string", "string", "string","string", "string", "string","string", "string", "string"},
//       {"20", "20","3","2","20","20","25","25","2","2","3","30","30"},
//       {"null","null","null","null","null","null","null","null","null","null","null","null","null"}
//   };
//    public static final String[][]  IVW_STUDENT_LOGIN_MAPPING={ 
//      {"STUDENT_ID","USER_ID","VERSION_NUMBER"},
//      {"1","2","3"},
//      {"string","string","string"},
//      {"25","25","4"},
//      {"null","null","null"},
//  };
//
//    public static final String[][] IVW_PERIOD_MASTER={
//       {"INSTITUTE_ID","STANDARD", "SECTION","PERIOD_NUMBER","START_TIME_HOUR","START_TIME_MIN","END_TIME_HOUR","END_TIME_MIN","VERSION_NUMBER"},
//       {"1","2","3","4","5","6","7","8","9"},
//       {"string", "string","string","string","string","string","string","string","string"},
//       {"20","4","4","2","10","10","10","10","5"},
//       {"null","null","null","null","null","null","null","null","null"},
//   };
//
//   public static final String[][] IVW_CONTENT_TYPE_MASTER={
//       {"CONTENT_TYPE","DESCRIPTION"},
//       {"1","2"},
//       {"string","string"},
//       {"3","15"},
//       {"null","null"},
//   };
//   public static final String[][] IVW_LEAVE_TYPE_MASTER={
//       {"LEAVE_TYPE","DESCRIPTION"},
//       {"1","2"},
//       {"string","string"},
//       {"10","15"},
//       {"null","null"},
//   };
//   public static final String[][] IVW_FEE_TYPE_MASTER={
//       {"INSTITUTE_ID","FEE_TYPE","DESCRIPTION","VERSION_NUMBER"},
//       {"1","2","3","4"},
//       {"string","string","string", "string"},
//       {"20","20","20","4"},
//       {"null","null","null","null"},
//   };
//  public static final String[][]  IVW_NOTIFICATION_TYPE_MASTER={
//       {"INSTITUTE_ID","NOTIFICATION_TYPE","NOTIFICATION_DESCRIPTION","VERSION_NUMBER"},
//       {"1","2","3","4"},
//       {"string","string","string", "string"},
//       {"20","20","20","5"},
//       {"null","null","null","null"},
//  };
//    public static final String[][] IVW_STUDENT_PROFILE = {
//        {"STUDENT_ID", "STANDARD", "SECTION", "INSTITUTE_ID", "STUDENT_NAME", "ADDRESS"}, //Columnn Names ,
//        {"1", "2", "3", "4", "5", "6"}, //Column Ids 
//        {"string", "string", "string", "string", "string", "string"}, //Column Datatypes 
//        {"20", "20", "20", "20", "20", "100"}, // Column Lengths
//        {"null","null","null","null","null","null"}//Column Level relationship
//    };
//    
//     public static final String[][] IVW_CLASS_LEVEL_CONFIGURATION_MASTER={
//       {"INSTITUTE_ID","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//       {"1","2","3","4","5","6","7","8","9","10"},
//       {"string", "string", "string", "string","string", "string", "string","string", "string", "string"},
//       {"20","20","20","25","25","2","2","3","30","30"},
//       {"null","null","null","null","null","null","null","null","null","null"}
//   };
//     
//     public static final String[][] IVW_HOLIDAY_MAINTANENCE={
//       {"INSTITUTE_ID","YEAR","MONTH","HOLIDAY","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//       {"1","2","3","4","5","6","7","8","9","10","11","12","13"},
//       {"string", "string", "string", "string","string", "string", "string","string", "string", "string","string", "string", "string"},
//       {"20","4","3","31","20","20","25","25","2","2","3","30","30"},
//       {"null","null","null","null","null","null","null","null","null","null","null","null","null"}
//   };    
//    public static final String[][] IVW_NOTIFICATION_MASTER={
//        {"INSTITUTE_ID","NOTIFICATION_ID","NOTIFICATION_TYPE","NOTIFICATION_FREQUENCY","DATE","DAY","MONTH","MESSAGE","MEDIA_COMMUNICATION","ASSIGNEE","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//        {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19"},
//        {"string","string","string", "string","string","string","string","string","string","string","string","string", "string","string","string","string","string","string","string"},
//        {"20","20","15","15","15","4","2","50","6","15","20","20","25","25","2","2","3","30","30"},
//        {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},
//    };   
//    public static final String[][] IVW_INSTITUTE_PAYMENT={
//         {"INSTITUTE_ID","STUDENT_ID","PAYMENT_ID","PAYMENT_MODE","PAYMENT_PAID","PAYMENT_DATE","FEE_ID","FEE_TYPE","AMOUNT","OUT_STANDING","REMARKS","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//         {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"},
//         {"string","string", "string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string"},
//         {"20","20","20","20","20","20","20","20","20","20","20","20","20","25","25","2","2","3","30","30"},
//         {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},
//     };
//     public static final String[][] IVW_GROUP_MAPPING_MASTER={
//         {"INSTITUTE_ID","GROUP_ID","GROUP_DESCRIPTION","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//         {"1","2","3","4","5","6","7","8","9","10","11","12"},
//         {"string", "string","string","string","string","string","string","string","string","string","string","string"},
//         {"20","20","20","20","20","25","25","2","2","3","30","30"},
//         {"null","null","null","null","null","null","null","null","null","null","null","null"},
//     };
//      public static final String[][] IVW_GROUP_MAPPING_DETAIL={
//         {"INSTITUTE_ID","GROUP_ID","STANDARD","SECTION","STUDENT_ID","VERSION_NUMBER"},
//         {"1","2","3","4","5","6"},
//         {"string","string", "string","string","string","string"},
//         {"20","20","20","20","20","5"},
//         {"null","null","null","null","null","null"},
//     };
//      
//     public static final String[][] IVW_OTHER_ACTIVITY={
//         {"INSTITUTE_ID","GROUP_ID","ACTIVITY_ID","ACTIVITY_NAME","ACTIVITY_TYPE","LEVEL","VENUE","DATE","DUE_DATE","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//         {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"},
//         {"string","string", "string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string"},
//         {"20","20","20","20","20","20","20","20","20","20","20","25","25","2","2","3","30","30"},
//         {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},
//     };
//     public static final String[][] IVW_FEE_MANAGEMENT={
//         {"INSTITUTE_ID","GROUP_ID","FEE_ID","FEE_TYPE","AMOUNT","DUE_DATE","REMARKS","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//         {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"},
//         {"string","string", "string","string","string","string","string","string","string","string","string","string","string","string","string","string"},
//         {"20","20","20","20","20","20","20","20","20","25","25","2","2","3","30","30"},
//         {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},
//     };
//     public static final String[][] IVW_ASSIGNMENT={
//         {"INSTITUTE_ID","GROUP_ID","ASSIGNMENT_ID","ASSIGNMENT_DESCRIPTION","SUBJECT_ID","ASSIGNMENT_TYPE","DUE_DATE","TEACHER_COMMENTS","CONTENT_PATH","MAKER_ID","CHECKER_ID","MAKER_DATE_STAMP","CHECKER_DATE_STAMP","RECORD_STATUS","AUTH_STATUS","VERSION_NUMBER","MAKER_REMARKS","CHECKER_REMARKS"},
//         {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"},
//         {"string","string", "string","string","string","string","string","string","string","string","string","string","string","string","string","string","string","string"},
//         {"20","20","20","20","20","20","20","20","20","20","20","25","25","2","2","3","30","30"},
//         {"null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null","null"},
//     };
//     
//     public static final String[][] INSTITUTE_CURRENT_DATE={
//        {"INSTITUTE_ID","CURRENT_DATE"},
//        {"1","2"},
//        {"string","string"},
//        {"20","20"},
//        {"null","null"},
//    };
//     public static final String[][] RETENTION_PERIOD={
//        {"INSTITUTE_ID","FEATURE_NAME","DAYS"},
//        {"1","2","3"},
//        {"string","string","string"},
//        {"20","20","20"},
//        {"null","null","null"},
//    };
    
}
