/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dependencyinjection;

import com.ibd.businessViews.IClassExamScheduleSummary;
import com.ibd.businessViews.IClassMarkSummary;
import com.ibd.businessViews.IClassTimeTableSummary;
import com.ibd.businessViews.IInstituteAssignmentSummary;
import com.ibd.businessViews.IInstituteOtherActivitySummary;
import com.ibd.businessViews.IInstituteFeeManagementSummary;
import com.ibd.businessViews.IInstitutePaymentSummary;
import com.ibd.businessViews.IGroupMappingSummary;
import com.ibd.businessViews.IHolidayMaintenanceSummary;
import com.ibd.businessViews.INotificationSummary;
import com.ibd.businessViews.IStudentLeaveManagementSummary;
import com.ibd.businessViews.IStudentOtherActivitySummary;
import com.ibd.businessViews.IStudentAssignmentSummary;
import com.ibd.businessViews.IStudentAttendanceSummary;
import com.ibd.businessViews.IStudentExamScheduleSummary;
import com.ibd.businessViews.IStudentFeeManagementSummary;
import com.ibd.businessViews.IStudentPaymentSummary;
import com.ibd.businessViews.IStudentProfileSummary;
import com.ibd.businessViews.IStudentProgressCardSummary;
import com.ibd.businessViews.IStudentTimeTableSummary;
import com.ibd.businessViews.ITeacherAttendanceSummary;
import com.ibd.businessViews.ITeacherLeaveManagementSummary;
import com.ibd.businessViews.ITeacherProfileSummary;
import com.ibd.businessViews.ITeacherTimeTableSummary;
import com.ibd.businessViews.ITokenValidationService;
import com.ibd.businessViews.IUserProfileSummary;
import com.ibd.businessViews.IUserRoleSummary;
import com.ibd.cohesive.db.core.IDBCoreService;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.index.IndexCoreService;
import com.ibd.cohesive.db.index.IndexReadService;
import com.ibd.cohesive.db.read.IDBReadService;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.report.businessreport.dataSets.classEntity.ClassAttendanceDetail_DataSet;
import com.ibd.cohesive.report.businessreport.dataSets.classEntity.ClassAttendanceSummary_DataSet;
import com.ibd.cohesive.report.businessreport.dataSets.classEntity.ClassFeeAmountSummary_DataSet;
import com.ibd.cohesive.report.businessreport.dataSets.classEntity.ClassFeeDetail_DataSet;
import com.ibd.cohesive.report.businessreport.dataSets.classEntity.ClassMarksDetail_DataSet;
import com.ibd.cohesive.report.businessreport.dataSets.classEntity.ClassMarksSummary_DataSet;
import com.ibd.cohesive.report.businessreport.dataSets.classEntity.ClassOtherActivityDetail_DataSet;
import com.ibd.cohesive.report.businessreport.dataSets.classEntity.ClassOtherActivitySummary_DataSet;
import com.ibd.businessViews.IClassDataSetBusiness;
import com.ibd.businessViews.IStudentDataSetBusiness;
import com.ibd.cohesive.report.businessreport.dataSets.student.StudentAttendanceSummary_DataSet;
import com.ibd.cohesive.report.businessreport.dataSets.student.StudentFeeDetails_DataSet;
import com.ibd.businessViews.ITeacherDataSetBusiness;
import com.ibd.cohesive.report.businessreport.dataSets.teacher.SubstituteAvailabilityInOtherClass_DataSet;
import com.ibd.cohesive.report.businessreport.dataSets.teacher.SubstituteAvailabilityInSameClass_DataSet;
import com.ibd.cohesive.report.businessreport.dataSets.teacher.TeacherAttendanceSummary_DataSet;
import com.ibd.cohesive.report.businessreport.dataSets.teacher.TeacherMarksDetail_DataSet;
import com.ibd.cohesive.report.businessreport.dataSets.teacher.TeacherMarksSummary_DataSet;
import com.ibd.cohesive.report.dbreport.dataSets.app.ERROR_MASTER_DATASET;
import com.ibd.businessViews.IAppDataSet;
import com.ibd.cohesive.report.dbreport.dataSets.app.INSTITUTE_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.app.SERVICE_TYPE_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.APP_EOD_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.APP_EOD_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.APP_EOD_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.ASSIGNMENT_EOD_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.ASSIGNMENT_EOD_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.ASSIGNMENT_EOD_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.ATTENDANCE_BATCH_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.ATTENDANCE_BATCH_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.ATTENDANCE_BATCH_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.app.BATCH_CONFIG_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.BATCH_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.BATCH_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.BATCH_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.EXAM_BATCH_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.EXAM_BATCH_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.EXAM_BATCH_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.FEE_EOD_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.FEE_EOD_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.FEE_EOD_STATUS_HISTORY_DATASET;
import com.ibd.businessViews.IBatchDataset;
import com.ibd.cohesive.report.dbreport.dataSets.batch.INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.INSTITUTE_EOD_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.INSTITUTE_EOD_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.INSTITUTE_EOD_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.MARK_BATCH_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.MARK_BATCH_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.MARK_BATCH_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.NOTIFICATION_EOD_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.NOTIFICATION_EOD_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.NOTIFICATION_EOD_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.OTHER_ACTIVITY_EOD_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.OTHER_ACTIVITY_EOD_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.OTHER_ACTIVITY_EOD_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_ASSIGNMENT_EOD_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_ASSIGNMENT_EOD_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_ATTENDANCE_BATCH_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_ATTENDANCE_BATCH_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_ATTENDANCE_BATCH_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_EXAM_BATCH_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_EXAM_BATCH_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_EXAM_BATCH_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_FEE_EOD_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_FEE_EOD_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_FEE_EOD_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_MARK_BATCH_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_MARK_BATCH_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_MARK_BATCH_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_NOTIFICATION_EOD_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_NOTIFICATION_EOD_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_NOTIFICATION_EOD_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_OTHER_ACTIVITY_EOD_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_TIMETABLE_BATCH_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_TIMETABLE_BATCH_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_TIMETABLE_BATCH_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.TIMETABLE_BATCH_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.TIMETABLE_BATCH_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.TIMETABLE_BATCH_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_ASSIGNMENT_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_ATTENDANCE_DETAIL_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_ATTENDANCE_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_EXAM_SCHEDULE_DETAIL_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_EXAM_SCHEDULE_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_FEE_MANAGEMENT_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_MARK_ENTRY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_STUDENT_MAPPING_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_TIMETABLE_DETAIL_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_TIMETABLE_MASTER_DATASET;
import com.ibd.businessViews.IClassDataSet;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_SUBJECT_GRADE_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.STUDENT_MARKS_DATASET;
import com.ibd.businessViews.IInstituteDataSet;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_CONTENT_TYPE_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_FEE_TYPE_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_INSTITUTE_EXAM_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_LEAVE_TYPE_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_NOTIFICATION_TYPE_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_PERIOD_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_STANDARD_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_STUDENT_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_SUBJECT_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_TEACHER_MASTER_DATASET;
import com.ibd.businessViews.IStudentDataSet;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_CONTACT_PERSON_DETAILS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_FAMILY_DETAILS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_STUDENT_ASSIGNMENT_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_STUDENT_ATTENDANCE_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_STUDENT_CALENDER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_STUDENT_EXAM_SCHEDULE_DETAIL_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_STUDENT_EXAM_SCHEDULE_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_STUDENT_FEE_MANAGEMENT_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_STUDENT_LEAVE_MANAGEMENT_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_STUDENT_MARKS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_STUDENT_OTHER_ACTIVITY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_STUDENT_PAYMENT_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_STUDENT_PROFILE_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_STUDENT_PRORESS_CARD_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_STUDENT_TIMETABLE_DETAIL_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_STUDENT_TIMETABLE_MASTER_DATASET;
import com.ibd.businessViews.ITeacherDataSet;
import com.ibd.cohesive.report.dbreport.dataSets.teacher.TVW_CONTACT_PERSON_DETAILS_DATASET;

import com.ibd.cohesive.report.dbreport.dataSets.teacher.TVW_TEACHER_LEAVE_MANAGEMENT_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.teacher.TVW_TEACHER_PROFILE_DATASET;

import com.ibd.businessViews.IUserDataSet;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.report.businessreport.dataSets.classEntity.ClassDetails_DataSet;
import com.ibd.cohesive.report.businessreport.dataSets.institute.BusinessReportParamsDataSet;
import com.ibd.cohesive.report.businessreport.dataSets.institute.CLASS_MARK_REPORT_DATASET;
import com.ibd.cohesive.report.businessreport.dataSets.institute.FeeDetailBusinessDataSet;
import com.ibd.cohesive.report.businessreport.dataSets.institute.FeePaymentBusinessDataSet;
import com.ibd.cohesive.report.businessreport.dataSets.institute.FeeSummaryBusinessDataSet;
import com.ibd.cohesive.report.businessreport.dataSets.institute.MarkComparison_DataSet;
import com.ibd.cohesive.report.businessreport.dataSets.institute.NotificationDetailBusinessDataSet;
import com.ibd.cohesive.report.businessreport.dataSets.institute.StudentDetailsDataSet;
import com.ibd.cohesive.report.businessreport.dataSets.student.SVW_FAMILY_DETAILS_BUSINESS_DATASET;
import com.ibd.cohesive.report.businessreport.dataSets.student.SVW_STUDENT_MARKS_BUSINESS_DATASET;
import com.ibd.cohesive.report.businessreport.dataSets.student.SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS_DATASET;
import com.ibd.cohesive.report.businessreport.dataSets.student.SVW_STUDENT_PROFILE_BUSINESS_DATASET;
import com.ibd.cohesive.report.businessreport.dataSets.student.SVW_STUDENT_SOFT_SKILLS_BUSINESS_DATASET;
import com.ibd.cohesive.report.businessreport.dataSets.student.SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS_DATASET;
import com.ibd.cohesive.report.businessreport.dataSets.student.StudentOtherActivityDetail_DataSet;
import com.ibd.cohesive.report.businessreport.dataSets.student.StudentRankDataSet;
import com.ibd.cohesive.report.businessreport.dataSets.teacher.SubstituteReportParam_DataSet;
import com.ibd.cohesive.report.businessreport.dataSets.teacher.TVW_CONTACT_PERSON_DETAILS_BUSINESS_DATASET;
import com.ibd.cohesive.report.businessreport.dataSets.teacher.TVW_TEACHER_PROFILE_BUSINESS_DATASET;
import com.ibd.cohesive.report.businessreport.dataSets.teacher.TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DATASET;
import com.ibd.cohesive.report.businessreport.dataSets.teacher.TeacherTimeTableReport_DataSet;
import com.ibd.cohesive.report.dbreport.dataSets.app.APP_RETENTION_PERIOD_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.app.APP_SUPPORT_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.app.ARCH_APPLY_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.app.ARCH_SHIPPING_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.app.BATCH_SERVICES_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.app.CONTRACT_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.E_CIRCULAR_EOD_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.E_CIRCULAR_EOD_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.E_CIRCULAR_EOD_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.FEE_NOTIFICATION_EOD_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.FEE_NOTIFICATION_EOD_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.FEE_NOTIFICATION_EOD_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_EVENT_NOTIFICATION_EOD_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_E_CIRCULAR_EOD_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_E_CIRCULAR_EOD_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_FEE_NOTIFICATION_EOD_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_NOTIFICATION_EMAIL_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.STUDENT_NOTIFICATION_SMS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.TEACHER_E_CIRCULAR_EOD_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.TEACHER_E_CIRCULAR_EOD_STATUS_ERROR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.batch.TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_ATTENDANCE_REPORT_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_EXAM_RANK_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_FEE_AMOUNT_REPORT_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_FEE_STATUS_REPORT_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_GRADE_REPORT_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_OTHER_ACTIVITY_REPORT_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_SKILL_ENTRY_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.STUDENT_SKILLS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.CLASS_LEAVE_MANAGEMENT_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.FEE_BATCH_INDICATOR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.INSTITITUTE_FEE_PAYMENT_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.INSTITUTE_CURRENT_DATE_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.INSTITUTE_OTHER_ACTIVITY_TRACKING_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.INSTITUTE_OTP_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_E_CIRCULAR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_GROUP_MAPPING_DETAIL_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_GROUP_MAPPING_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_HOLIDAY_MAINTANENCE_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_NOTIFICATION_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_SKILL_GRADE_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_SKILL_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_SOFT_SKILL_CONFIGURATION_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_STUDENT_LEAVE_DETAILS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_TEACHER_LEAVE_DETAILS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.IVW_UNAUTH_RECORDS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.NOTIFICATION_BATCH_INDICATOR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.OTHER_ACTIVITY_BATCH_INDICATOR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.PAYMENT_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.RETENTION_PERIOD_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.STUDENT_ASSIGNMENT_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.STUDENT_OTP_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.institute.TODAY_NOTIFICATION_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.STUDENT_NOTIFICATION_STATUS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_STUDENT_E_CIRCULAR_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_STUDENT_NOTIFICATION_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.student.SVW_STUDENT_OTHER_ACTIVITY_REPORT_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.user.UVW_CLASS_ENTITY_ROLEMAPPING_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.user.UVW_INSTITUTE_ENTITY_ROLEMAPPING_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.user.UVW_IN_LOG_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.user.UVW_OUT_LOG_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.user.UVW_PARENT_STUDENT_ROLEMAPPING_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.user.UVW_TEACHER_ENTITY_ROLEMAPPING_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.user.UVW_USER_CONTRACT_MASTER_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.user.UVW_USER_CREDENTIALS_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.user.UVW_USER_PROFILE_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.user.UVW_USER_ROLE_DETAIL_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.user.UVW_USER_ROLE_INSTITUTE_DATASET;
import com.ibd.cohesive.report.dbreport.dataSets.user.UVW_USER_ROLE_MASTER_DATASET;
import com.ibd.cohesive.report.util.ReportUtil;
import com.ibd.cohesive.report.util.validation.ReportValidation;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
public class ReportDependencyInjection {
     private static InitialContext contxt;
     final Context remoteContxt;
     Properties props = new Properties();
     private SVW_CONTACT_PERSON_DETAILS_DATASET studentContactPersonDataSet;
     private SVW_FAMILY_DETAILS_DATASET studentFamilyDetailsDataSet;
     private SVW_STUDENT_ASSIGNMENT_DATASET studentAssignmentDataSet;
     private SVW_STUDENT_ATTENDANCE_DATASET studentAttendanceDataSet;
     private SVW_STUDENT_CALENDER_DATASET   studentCalenderDataSet; 
     private SVW_STUDENT_EXAM_SCHEDULE_DETAIL_DATASET studentExamScheduleDetailDataSet;
     private SVW_STUDENT_EXAM_SCHEDULE_MASTER_DATASET studentExamScheduleMasterDataSet;
     private SVW_STUDENT_FEE_MANAGEMENT_DATASET studentFeeManagementDataSet;
     private SVW_STUDENT_LEAVE_MANAGEMENT_DATASET studentLeaveManagementDataSet;
     private SVW_STUDENT_MARKS_DATASET studentMarksDataSet;
     private SVW_STUDENT_OTHER_ACTIVITY_DATASET studentOtherActivityDataSet;
     private SVW_STUDENT_PAYMENT_DATASET studentPaymentDataSet;
     private SVW_STUDENT_PROFILE_DATASET studentProfileDataSet;
     private SVW_STUDENT_PRORESS_CARD_DATASET studentProgressCardDataSet;
     private SVW_STUDENT_TIMETABLE_DETAIL_DATASET studentTimeTableDetailDataSet;
     private SVW_STUDENT_TIMETABLE_MASTER_DATASET studentTimeTableMasterDataSet;
     private TVW_CONTACT_PERSON_DETAILS_DATASET teacherContactPersonDataSet;
     private TVW_TEACHER_LEAVE_MANAGEMENT_DATASET teacherLeaveManagementDataSet;
     private TVW_TEACHER_PROFILE_DATASET teacherProfileDataSet;
     private CLASS_ASSIGNMENT_DATASET classAssignmentDataSet;
     private CLASS_ATTENDANCE_DETAIL_DATASET classAttendanceDetailDataSet;
     private CLASS_ATTENDANCE_MASTER_DATASET classAttendanceMasterDataSet;
     private CLASS_EXAM_SCHEDULE_DETAIL_DATASET classExamScheduleDetail;
     private CLASS_EXAM_SCHEDULE_MASTER_DATASET classExamScheduleMaster;
     private CLASS_FEE_MANAGEMENT_DATASET classFeeManagementDataSet;
     private CLASS_MARK_ENTRY_DATASET classMarkEntryDataSet;
     private CLASS_STUDENT_MAPPING_DATASET classStudentMappingDataSet;
     private CLASS_TIMETABLE_DETAIL_DATASET classTimeTableDetailDataset;
     private CLASS_TIMETABLE_MASTER_DATASET classTimeTableMasterDataSet;
     private STUDENT_MARKS_DATASET studentMarks;
     private IVW_CONTENT_TYPE_MASTER_DATASET contentTypeMasterDataSet;
     private IVW_FEE_TYPE_MASTER_DATASET feeTypeMasterDataSet; 
     private IVW_INSTITUTE_EXAM_MASTER_DATASET examTypeMasterDataSet; 
     private IVW_LEAVE_TYPE_MASTER_DATASET leaveTypeMasterDataSet; 
     private IVW_NOTIFICATION_TYPE_MASTER_DATASET notificationTypeMasterDataSet; 
     private IVW_PERIOD_MASTER_DATASET periodMasterDataSet; 
     private IVW_STANDARD_MASTER_DATASET standardMasterDataSet; 
     private IVW_STUDENT_MASTER_DATASET studentMasterDataSet;
     private IVW_SUBJECT_GRADE_MASTER_DATASET gradeMasterDataSet; 
     private IVW_SUBJECT_MASTER_DATASET subjectMasterDataSet; 
     private IVW_TEACHER_MASTER_DATASET teacherMasterDataSet; 
     private UVW_CLASS_ENTITY_ROLEMAPPING_DATASET classRoleMappingDataSet;
     private UVW_INSTITUTE_ENTITY_ROLEMAPPING_DATASET instituteRoleMappingDataSet;
     private UVW_IN_LOG_DATASET inLogDataSet;
     private UVW_OUT_LOG_DATASET outLogDataSet;
     private UVW_PARENT_STUDENT_ROLEMAPPING_DATASET parentRoleMappingDataSet;
     private UVW_TEACHER_ENTITY_ROLEMAPPING_DATASET teacherRoleMappingDataSet;
     private UVW_USER_CREDENTIALS_DATASET userCredentialsDataSet;
     private UVW_USER_PROFILE_DATASET userProfileDataSet;
     private UVW_USER_ROLE_DETAIL_DATASET userRoleDetailDataSet;
     private UVW_USER_ROLE_MASTER_DATASET userRoleMasterDataSet;
     private ERROR_MASTER_DATASET errorMasterDataSet;
     private INSTITUTE_MASTER_DATASET instituteMasterDataSet;
     private SERVICE_TYPE_MASTER_DATASET serviceMasterDataSet;
     private ClassOtherActivityDetail_DataSet classOtherActivityDetail;
     private ClassAttendanceDetail_DataSet classAttendanceBusinessDataSet;
     private ClassOtherActivitySummary_DataSet classOtherActivitySummaryDataSet;
     private ReportUtil reportUtil;
     private ClassAttendanceSummary_DataSet classAttendanceSummary;
     private StudentAttendanceSummary_DataSet studentAttendanceSummary;
     private ClassMarksDetail_DataSet classMarksDetail;
     private TeacherMarksDetail_DataSet teacherMarksDetail;
     private ClassMarksSummary_DataSet classMarkSummary; 
     private MarkComparison_DataSet markComparison;
     private TeacherAttendanceSummary_DataSet teacherAttendanceDataset;
     private TeacherMarksSummary_DataSet teacherMarksSummary;
     private StudentFeeDetails_DataSet studentFeeDetails;
     private ClassFeeDetail_DataSet classFeeDetail;
     private ClassFeeAmountSummary_DataSet classFeeAmountSummary;
     private SubstituteAvailabilityInOtherClass_DataSet SubstituteAvailabilityInOtherClass;
     private SubstituteAvailabilityInSameClass_DataSet SubstituteAvailabilityInSameClass;
     private ReportValidation rv;
     private APP_EOD_STATUS_DATASET appEodDatset;
     private APP_EOD_STATUS_ERROR_DATASET appEodErrorDataset;
     private APP_EOD_STATUS_HISTORY_DATASET appEodHistoryDataset;
     private ASSIGNMENT_EOD_STATUS_DATASET assignmentEodDataSet;
     private ASSIGNMENT_EOD_STATUS_ERROR_DATASET assignmentEodErrorDataset;
     private ASSIGNMENT_EOD_STATUS_HISTORY_DATASET assignmentEodHistoryDataset;
     private BATCH_CONFIG_DATASET batchConfig;
     private BATCH_STATUS_DATASET batchEodDataset;
     private BATCH_STATUS_ERROR_DATASET batchErrorDataset;
     private BATCH_STATUS_HISTORY_DATASET batchHistoryDataset;
     private INSTITUTE_EOD_STATUS_DATASET instituteEodDataset;
     private INSTITUTE_EOD_STATUS_ERROR_DATASET instituteErrorDataset;
     private INSTITUTE_EOD_STATUS_HISTORY_DATASET instituteEodHistoryDataset;
     private STUDENT_ASSIGNMENT_EOD_STATUS_DATASET studentAssignmentEodDatset;
     private STUDENT_ASSIGNMENT_EOD_STATUS_ERROR_DATASET studentAssignmentErrorDataset;
     private STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY_DATASET studentAssignmentHistoryDataSet;
     private ATTENDANCE_BATCH_STATUS_DATASET attendanceBatchDataSet;
     private ATTENDANCE_BATCH_STATUS_ERROR_DATASET attendanceBatchErrorDataSet;
     private ATTENDANCE_BATCH_STATUS_HISTORY_DATASET attendanceBatchStatusHistoryDataSet;
     private EXAM_BATCH_STATUS_DATASET examBatchDataSet;
     private EXAM_BATCH_STATUS_ERROR_DATASET examBatchErrorDataSet;
     private EXAM_BATCH_STATUS_HISTORY_DATASET examBatchHistoryDataSet;
     private FEE_EOD_STATUS_DATASET feeBatchDataSet;
     private FEE_EOD_STATUS_ERROR_DATASET feeBatchErrorDataSet;
     private FEE_EOD_STATUS_HISTORY_DATASET feeBatchHistoryDataSet;
     private MARK_BATCH_STATUS_DATASET markBatchDataSet;
     private MARK_BATCH_STATUS_ERROR_DATASET markBatchErrorDataSet;
     private MARK_BATCH_STATUS_HISTORY_DATASET markBatchHistoryDataSet;
     private NOTIFICATION_EOD_STATUS_DATASET notificationBatchDataSet;
     private NOTIFICATION_EOD_STATUS_ERROR_DATASET notificationBatcherrorDataSet;
     private NOTIFICATION_EOD_STATUS_HISTORY_DATASET notificationBatchHistoryDataSet;
     private OTHER_ACTIVITY_EOD_STATUS_DATASET otherActivityBatchDataSet;
     private OTHER_ACTIVITY_EOD_STATUS_ERROR_DATASET otherActivityBatchErrorDataSet;
     private OTHER_ACTIVITY_EOD_STATUS_HISTORY_DATASET otherActivityBatchHistoryDataSet;
     private STUDENT_ATTENDANCE_BATCH_STATUS_DATASET studentAttBatchDataSet;
     private STUDENT_ATTENDANCE_BATCH_STATUS_ERROR_DATASET studentAttendanceBatchErrorDataSet;
     private STUDENT_ATTENDANCE_BATCH_STATUS_HISTORY_DATASET studentAttendanceBatchHistoryDataSet;
     private STUDENT_EXAM_BATCH_STATUS_DATASET studentExamBatchDataSet;
     private STUDENT_EXAM_BATCH_STATUS_ERROR_DATASET studentExamBatchErrorDataSet;
     private STUDENT_EXAM_BATCH_STATUS_HISTORY_DATASET studentExamBatchHsitoryDataSet;
     private STUDENT_FEE_EOD_STATUS_DATASET studentFeeBatchDataSet;
     private STUDENT_FEE_EOD_STATUS_ERROR_DATASET studentFeeBatchErrorDataSet;
     private STUDENT_FEE_EOD_STATUS_HISTORY_DATASET studentFeeBatchHistoryDataSet;
     private STUDENT_MARK_BATCH_STATUS_DATASET studentMarkBatchDataSet;
     private STUDENT_MARK_BATCH_STATUS_ERROR_DATASET studentMarkBatchErrorDataSet;
     private STUDENT_MARK_BATCH_STATUS_HISTORY_DATASET studentMarkBatchHistoryDataSet;
     private STUDENT_NOTIFICATION_EOD_STATUS_DATASET sttudentNotificationBatchDataSet;
     private STUDENT_NOTIFICATION_EOD_STATUS_ERROR_DATASET studentNotificationBatchErrorDataSet;
     private STUDENT_NOTIFICATION_EOD_STATUS_HISTORY_DATASET studentNotificationBatchHistoryDataSet;
     private STUDENT_OTHER_ACTIVITY_EOD_STATUS_DATASET studentOtherActivityBatchDataSet;
     private STUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR_DATASET studentOtherActivityBatchErrorDataSet;
     private STUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY_DATASET studentOtherActivityBAtchhistoryDataSet;
     private STUDENT_TIMETABLE_BATCH_STATUS_DATASET studentTimeTableBatchDataSet;
     private STUDENT_TIMETABLE_BATCH_STATUS_ERROR_DATASET studentTimeTableBatchErrorDataSet;
     private STUDENT_TIMETABLE_BATCH_STATUS_HISTORY_DATASET studentTimeTableBatchHistoryDataSet;
     private TIMETABLE_BATCH_STATUS_DATASET timeTableBatchDataSet;
     private TIMETABLE_BATCH_STATUS_ERROR_DATASET timeTableBatchErrorDataSet;
     private TIMETABLE_BATCH_STATUS_HISTORY_DATASET timeTableBatchHistoryDataSet;
     private STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_DATASET studentAssignmentArchDataSet;
     private STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DATASET studentAssignmentArchErrorDataSet;
     private STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DATASET studentAssignmentArchHistoryDataSet;
     private INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_DATASET instituteAssignmentArchDataSet;
     private INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DATASET instituteAssignmentArchErrorDataSet;
     private INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DATASET instituteAssignmentArchHistoryDataSet;
     private STUDENT_NOTIFICATION_SMS_ERROR_DATASET studentNotificationSmsDataSet;
     private STUDENT_NOTIFICATION_EMAIL_ERROR_DATASET studentNotificationEmailDataSet;
     private CLASS_EXAM_RANK_DATASET classRank;
     private StudentRankDataSet studentRank;
     private StudentOtherActivityDetail_DataSet otherActivityDetail;
     private SVW_FAMILY_DETAILS_BUSINESS_DATASET studentFamilyDetailsBusiness;
     private SVW_STUDENT_MARKS_BUSINESS_DATASET studentMarksBusiness;
     private SVW_STUDENT_SOFT_SKILLS_BUSINESS_DATASET studentSoftSkillsBusiness;
     private SVW_STUDENT_PROFILE_BUSINESS_DATASET studentProfileBusiness;
     private SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS_DATASET studentTimeTableBusiness;
     private SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS_DATASET studentOtherActivityBusiness;
//     private StudentDataSet studentDataSet;
//     private StudentDataSetBusiness studentDatasetBusiness;
//     private ClassDataSet classDataSet;
//     private TeacherDataSetBusiness teacherDatasetBusiness;
//     private ClassDataSetBusiness classDatasetBusiness;
     private TVW_CONTACT_PERSON_DETAILS_BUSINESS_DATASET teacherContactPersoonBusiness;
     private TVW_TEACHER_PROFILE_BUSINESS_DATASET teacherProfileBusiness;
     private TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DATASET teacherTimeTableDetail;
     private ClassDetails_DataSet classDetailsDataSet;
     private APP_RETENTION_PERIOD_DATASET appRetentionPeriodDataSet;
     private APP_SUPPORT_DATASET appSupportDataSet;
     private ARCH_SHIPPING_STATUS_DATASET appShippingStatus;
     private ARCH_APPLY_STATUS_DATASET appApplyStatusDataset;
     private BATCH_CONFIG_DATASET batchConfigDataset;
     private BATCH_SERVICES_DATASET batchServiceDataset;
     private CONTRACT_MASTER_DATASET contractMasterDataset;
     private STUDENT_NOTIFICATION_STATUS_DATASET studentNotificationDataset;
     private SVW_STUDENT_E_CIRCULAR_DATASET studentEcircularDataset;
     private SVW_STUDENT_NOTIFICATION_DATASET svwStudentNotificationDataset;
     private SVW_STUDENT_OTHER_ACTIVITY_REPORT_DATASET studentOtherActivityReport;
     private UVW_USER_CONTRACT_MASTER_DATASET userContractMaster;
     private UVW_USER_ROLE_INSTITUTE_DATASET userRoleInstituteDataset;
     private STUDENT_ASSIGNMENT_STATUS_DATASET studentAssignmentstatus;
     private INSTITITUTE_FEE_PAYMENT_DATASET instituteFeePaymentDataset;
     private STUDENT_OTP_STATUS_DATASET studentOtpStatus;
     private INSTITUTE_OTP_STATUS_DATASET instituteOtpStatus;
     private CLASS_LEAVE_MANAGEMENT_DATASET classLeaveManagementDataset;
     private INSTITUTE_OTHER_ACTIVITY_TRACKING_DATASET instituteOtherActivityTrackingDataset;
     private PAYMENT_MASTER_DATASET paymentMasterDataset;
     private FEE_BATCH_INDICATOR_DATASET feeBatchIndicatorDataset;
     private INSTITUTE_CURRENT_DATE_DATASET instituteCurrentDateDataset;
     private IVW_E_CIRCULAR_DATASET ivwEcircularDataset; 
     private IVW_GROUP_MAPPING_DETAIL_DATASET ivwGroupMappingDetailDataset;
     private IVW_GROUP_MAPPING_MASTER_DATASET ivwGroupMappingMasterDataset;
     private IVW_HOLIDAY_MAINTANENCE_DATASET ivwHolidayMaintanenceDataset;
     private IVW_NOTIFICATION_MASTER_DATASET ivwNotificationMasterDataset;
     private IVW_SKILL_GRADE_MASTER_DATASET ivwSkillGradeMasterDataset;
     private IVW_SKILL_MASTER_DATASET ivwSkillMasterDataset;
     private IVW_SOFT_SKILL_CONFIGURATION_MASTER_DATASET ivwSoftSkillConfigurationMasterDataSet;
     private IVW_STUDENT_LEAVE_DETAILS_DATASET ivwStudentLeaveDetailsDataset;
     private IVW_TEACHER_LEAVE_DETAILS_DATASET ivwTeacherLeaveDetailDataset;
     private IVW_UNAUTH_RECORDS_DATASET ivwUnauthRecords;
     private NOTIFICATION_BATCH_INDICATOR_DATASET notificationBatchIndicatorDataset;
     private OTHER_ACTIVITY_BATCH_INDICATOR_DATASET otherActivityBatchIndicatorDataset;
     private RETENTION_PERIOD_DATASET retentionPeriodDataset;
     private CLASS_ATTENDANCE_REPORT_DATASET classAttendanceReportDataset;
     private CLASS_EXAM_RANK_DATASET classExamRankDataset;
     private CLASS_FEE_AMOUNT_REPORT_DATASET classFeeAmountReportDataset;
     private CLASS_FEE_STATUS_REPORT_DATASET classFeeStatusReport;
     private CLASS_GRADE_REPORT_DATASET classGradeReportDataset;
     private CLASS_MARK_REPORT_DATASET classMarkReportDataset;
     private CLASS_OTHER_ACTIVITY_REPORT_DATASET classOtherActivityReportDataset;
     private CLASS_SKILL_ENTRY_DATASET classSkillEntryDataset;
     private STUDENT_SKILLS_DATASET studentSkillDataset;
     private CLASS_MARK_REPORT_DATASET classMark;
     private TeacherTimeTableReport_DataSet teacherTimeTableReport;
     private SubstituteReportParam_DataSet substituteReportparam;
     private TODAY_NOTIFICATION_DATASET todaynotificationDataset;
     private FEE_NOTIFICATION_EOD_STATUS_DATASET feeNotificationEodStatusDataset;
     private FEE_NOTIFICATION_EOD_STATUS_HISTORY_DATASET feeNotificationEodStatusHistoryDataset;
     private FEE_NOTIFICATION_EOD_STATUS_ERROR_DATASET feeNotificationEodStatusErrorDataset;
     private STUDENT_FEE_NOTIFICATION_EOD_STATUS_DATASET studentFeeNotificationEodStatus;
     private STUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY_DATASET studentFeeNotificationEodStatusHistory;
     private STUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR_DATASET studentFeeNotificationEodStatusError;
     private STUDENT_EVENT_NOTIFICATION_EOD_STATUS_DATASET studentEventNotificationEodStatus;
     private STUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY_DATASET studentEventNotificationEodStatusHistory;
     private STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR_DATASET studentEventNotificationEodStatusError;
     private E_CIRCULAR_EOD_STATUS_DATASET eCircularEodDataSet;
     private E_CIRCULAR_EOD_STATUS_ERROR_DATASET eCircularEodErrorDataset;
     private E_CIRCULAR_EOD_STATUS_HISTORY_DATASET eCircularEodHistoryDataset;
     private STUDENT_E_CIRCULAR_EOD_STATUS_DATASET studentECircularEodDatset;
     private STUDENT_E_CIRCULAR_EOD_STATUS_ERROR_DATASET studentECircularErrorDataset;
     private STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY_DATASET studentECircularHistoryDataSet;
     private TEACHER_E_CIRCULAR_EOD_STATUS_DATASET teacherECircularEodDatset;
     private TEACHER_E_CIRCULAR_EOD_STATUS_ERROR_DATASET teacherECircularErrorDataset;
     private TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY_DATASET teacherECircularHistoryDataSet;
     private FeeDetailBusinessDataSet feeDetailBusinessDataSet;
     private FeeSummaryBusinessDataSet feeSummaryBusinessDataSet;
     private FeePaymentBusinessDataSet feePaymentBusinessDataSet;
     private NotificationDetailBusinessDataSet notificationDetailBusinessDataSet;
     private StudentDetailsDataSet studentDetailsDataSet;
     private BusinessReportParamsDataSet businessReportParams;
     
      public ReportDependencyInjection()throws NamingException{
          
          contxt = new InitialContext();
          
         props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
         props.put("jboss.naming.client.ejb.context", "true");
         
         remoteContxt= new InitialContext(props);
        
        if(reportUtil==null){
            reportUtil=new ReportUtil();
        }
        
        if(rv==null){
            
            rv=new ReportValidation();
        }
          
      }
     
    public ReportDependencyInjection(String serviceType) throws NamingException  {
        
        contxt = new InitialContext();
        remoteContxt=null;
        
        if(reportUtil==null){
            reportUtil=new ReportUtil();
        }
        
        if(rv==null){
            
            rv=new ReportValidation();
        }
        
        if(serviceType.equals("STUDENT")){
        
        if(studentContactPersonDataSet==null){
            
            studentContactPersonDataSet=new SVW_CONTACT_PERSON_DETAILS_DATASET();
        }
        
        
        
        if(studentFamilyDetailsDataSet==null){
            
            studentFamilyDetailsDataSet=new SVW_FAMILY_DETAILS_DATASET();
        }
        
        if(studentAssignmentDataSet==null){
            
            studentAssignmentDataSet=new SVW_STUDENT_ASSIGNMENT_DATASET();
        }
        
        if(studentAttendanceDataSet==null){
            
            studentAttendanceDataSet=new SVW_STUDENT_ATTENDANCE_DATASET();
        }
        
         if(studentCalenderDataSet==null){
            
            studentCalenderDataSet=new SVW_STUDENT_CALENDER_DATASET();
        }
         
        if(studentExamScheduleDetailDataSet==null){
            
            studentExamScheduleDetailDataSet=new SVW_STUDENT_EXAM_SCHEDULE_DETAIL_DATASET();
        }
        if(studentExamScheduleMasterDataSet==null){
            
            studentExamScheduleMasterDataSet=new SVW_STUDENT_EXAM_SCHEDULE_MASTER_DATASET();
        }
        if(studentFeeManagementDataSet==null){
            
            studentFeeManagementDataSet=new SVW_STUDENT_FEE_MANAGEMENT_DATASET();
        }
        
        if(studentLeaveManagementDataSet==null){
            
            studentLeaveManagementDataSet=new SVW_STUDENT_LEAVE_MANAGEMENT_DATASET();
        }
        if(studentMarksDataSet==null){
            
            studentMarksDataSet=new SVW_STUDENT_MARKS_DATASET();
        }
        if(studentOtherActivityDataSet==null){
            
            studentOtherActivityDataSet=new SVW_STUDENT_OTHER_ACTIVITY_DATASET();
        }
        if(studentPaymentDataSet==null){
            
            studentPaymentDataSet=new SVW_STUDENT_PAYMENT_DATASET();
        }
        if(studentProfileDataSet==null){
            
            studentProfileDataSet=new SVW_STUDENT_PROFILE_DATASET();
        }
        if(studentProgressCardDataSet==null){
            
            studentProgressCardDataSet=new SVW_STUDENT_PRORESS_CARD_DATASET();
        }
        if(studentTimeTableDetailDataSet==null){
            
            studentTimeTableDetailDataSet=new SVW_STUDENT_TIMETABLE_DETAIL_DATASET();
        }
        if(studentTimeTableMasterDataSet==null){
            
            studentTimeTableMasterDataSet=new SVW_STUDENT_TIMETABLE_MASTER_DATASET();
        }
        if(studentNotificationDataset==null){
            
            studentNotificationDataset=new STUDENT_NOTIFICATION_STATUS_DATASET();
        }
         if(studentEcircularDataset==null){
            
            studentEcircularDataset=new SVW_STUDENT_E_CIRCULAR_DATASET();
        }
         if(svwStudentNotificationDataset==null){
            
            svwStudentNotificationDataset=new SVW_STUDENT_NOTIFICATION_DATASET();
        }
          if(studentOtherActivityReport==null){
            
            studentOtherActivityReport=new SVW_STUDENT_OTHER_ACTIVITY_REPORT_DATASET();
        }
        
        
        
        }else if(serviceType.equals("TEACHER")){
        
        
        
         if(teacherContactPersonDataSet==null){
            
            teacherContactPersonDataSet=new TVW_CONTACT_PERSON_DETAILS_DATASET();
        }
        if(teacherLeaveManagementDataSet==null){
            
            teacherLeaveManagementDataSet=new TVW_TEACHER_LEAVE_MANAGEMENT_DATASET();
        }
          if(teacherProfileDataSet==null){
            
            teacherProfileDataSet=new TVW_TEACHER_PROFILE_DATASET();
        }
           
           
        }else if(serviceType.equals("CLASS")){
        
          if(classAssignmentDataSet==null){
            
            classAssignmentDataSet=new CLASS_ASSIGNMENT_DATASET();
        }
        if(classAttendanceDetailDataSet==null){
            
            classAttendanceDetailDataSet=new CLASS_ATTENDANCE_DETAIL_DATASET();
        }
        if(classAttendanceMasterDataSet==null){
            
            classAttendanceMasterDataSet=new CLASS_ATTENDANCE_MASTER_DATASET();
        }    
        if(classExamScheduleDetail==null){
            
            classExamScheduleDetail=new CLASS_EXAM_SCHEDULE_DETAIL_DATASET();
        }
         if(classExamScheduleMaster==null){
            
            classExamScheduleMaster=new CLASS_EXAM_SCHEDULE_MASTER_DATASET();
        }
         if(classFeeManagementDataSet==null){
            
            classFeeManagementDataSet=new CLASS_FEE_MANAGEMENT_DATASET();
        }
         if(classMarkEntryDataSet==null){
            
            classMarkEntryDataSet=new CLASS_MARK_ENTRY_DATASET();
        }
         if(classStudentMappingDataSet==null){
            
            classStudentMappingDataSet=new CLASS_STUDENT_MAPPING_DATASET();
        }
         if(classTimeTableDetailDataset==null){
            
            classTimeTableDetailDataset=new CLASS_TIMETABLE_DETAIL_DATASET();
        }
         if(classTimeTableMasterDataSet==null){
            
            classTimeTableMasterDataSet=new CLASS_TIMETABLE_MASTER_DATASET();
        }
         if(studentMarks==null){
            
            studentMarks=new STUDENT_MARKS_DATASET();
        }
         
         if(classRank==null){
            
            classRank=new CLASS_EXAM_RANK_DATASET();
        }
         
          if(studentRank==null){
            
            studentRank=new StudentRankDataSet();
        }
            if(classAttendanceReportDataset==null){
            
            classAttendanceReportDataset=new CLASS_ATTENDANCE_REPORT_DATASET();
        }
          if(classExamRankDataset==null){
            
            classExamRankDataset=new CLASS_EXAM_RANK_DATASET();
        } 
           if(classFeeAmountReportDataset==null){
            
            classFeeAmountReportDataset=new CLASS_FEE_AMOUNT_REPORT_DATASET();
        } 
            if(classFeeStatusReport==null){
            
            classFeeStatusReport=new CLASS_FEE_STATUS_REPORT_DATASET();
        } 
           if(classGradeReportDataset==null){
            
            classGradeReportDataset=new CLASS_GRADE_REPORT_DATASET();
        } 
           if(classMarkReportDataset==null){
            
            classMarkReportDataset=new CLASS_MARK_REPORT_DATASET();
        } 
         
            if(classOtherActivityReportDataset==null){
            
            classOtherActivityReportDataset=new CLASS_OTHER_ACTIVITY_REPORT_DATASET();
        } 
           if(classSkillEntryDataset==null){
            
            classSkillEntryDataset=new CLASS_SKILL_ENTRY_DATASET();
        } 
           if(studentSkillDataset==null){
            
            studentSkillDataset=new STUDENT_SKILLS_DATASET();
        } 
         
           
           
           
           
           
           
         
        }else if(serviceType.equals("INSTITUTE")){
          if(contentTypeMasterDataSet==null){
            
            contentTypeMasterDataSet=new IVW_CONTENT_TYPE_MASTER_DATASET();
        }
         if(feeTypeMasterDataSet==null){
            
            feeTypeMasterDataSet=new IVW_FEE_TYPE_MASTER_DATASET();
        }
         if(examTypeMasterDataSet==null){
           
            examTypeMasterDataSet=new IVW_INSTITUTE_EXAM_MASTER_DATASET();
        }
         if(leaveTypeMasterDataSet==null){
            
            leaveTypeMasterDataSet=new IVW_LEAVE_TYPE_MASTER_DATASET();
        }
         if(notificationTypeMasterDataSet==null){
            
            notificationTypeMasterDataSet=new IVW_NOTIFICATION_TYPE_MASTER_DATASET();
        }
          if(periodMasterDataSet==null){
            
            periodMasterDataSet=new IVW_PERIOD_MASTER_DATASET();
        }
          if(standardMasterDataSet==null){
            
            standardMasterDataSet=new IVW_STANDARD_MASTER_DATASET();
        }
           if(studentMasterDataSet==null){
            
            studentMasterDataSet=new IVW_STUDENT_MASTER_DATASET();
        }
           if(gradeMasterDataSet==null){
            
            gradeMasterDataSet=new IVW_SUBJECT_GRADE_MASTER_DATASET();
        }
          if(subjectMasterDataSet==null){
         
            subjectMasterDataSet=new IVW_SUBJECT_MASTER_DATASET();
        }
         if(teacherMasterDataSet==null){
            
            teacherMasterDataSet=new IVW_TEACHER_MASTER_DATASET();
        }
         if(studentAssignmentstatus==null){
            
            studentAssignmentstatus=new STUDENT_ASSIGNMENT_STATUS_DATASET();
        }
         if(instituteFeePaymentDataset==null){
            
            instituteFeePaymentDataset=new INSTITITUTE_FEE_PAYMENT_DATASET();
        }
         if(studentOtpStatus==null){
            
            studentOtpStatus=new STUDENT_OTP_STATUS_DATASET();
        }
         
         if(instituteOtpStatus==null){
            
            instituteOtpStatus=new INSTITUTE_OTP_STATUS_DATASET();
        }
          if(classLeaveManagementDataset==null){
            
            classLeaveManagementDataset=new CLASS_LEAVE_MANAGEMENT_DATASET();
        }
           if(instituteOtherActivityTrackingDataset==null){
            
            instituteOtherActivityTrackingDataset=new INSTITUTE_OTHER_ACTIVITY_TRACKING_DATASET();
        }
           if(paymentMasterDataset==null){
            
            paymentMasterDataset=new PAYMENT_MASTER_DATASET();
        }  
          if(feeBatchIndicatorDataset==null){
            

              feeBatchIndicatorDataset=new FEE_BATCH_INDICATOR_DATASET();
        }  
          if(instituteCurrentDateDataset==null){
            

              instituteCurrentDateDataset=new INSTITUTE_CURRENT_DATE_DATASET();
        }  
          if(ivwEcircularDataset==null){
            

              ivwEcircularDataset=new IVW_E_CIRCULAR_DATASET();
        }  
           if(ivwGroupMappingDetailDataset==null){
            

              ivwGroupMappingDetailDataset=new IVW_GROUP_MAPPING_DETAIL_DATASET();
        }  
           if(ivwGroupMappingMasterDataset==null){
            

              ivwGroupMappingMasterDataset=new IVW_GROUP_MAPPING_MASTER_DATASET();
        }  
          if(ivwHolidayMaintanenceDataset==null){
            

              ivwHolidayMaintanenceDataset=new IVW_HOLIDAY_MAINTANENCE_DATASET();
        }  
            if(ivwNotificationMasterDataset==null){
            

              ivwNotificationMasterDataset=new IVW_NOTIFICATION_MASTER_DATASET();
        }  
            if(ivwSkillGradeMasterDataset==null){
            

              ivwSkillGradeMasterDataset=new IVW_SKILL_GRADE_MASTER_DATASET();
        }  
           if(ivwSkillMasterDataset==null){
            

              ivwSkillMasterDataset=new IVW_SKILL_MASTER_DATASET();
        }  
          if(ivwSoftSkillConfigurationMasterDataSet==null){
            

              ivwSoftSkillConfigurationMasterDataSet=new IVW_SOFT_SKILL_CONFIGURATION_MASTER_DATASET();
        } 
           if(ivwStudentLeaveDetailsDataset==null){
            

              ivwStudentLeaveDetailsDataset=new IVW_STUDENT_LEAVE_DETAILS_DATASET();
        } 
           if(ivwTeacherLeaveDetailDataset==null){
            

              ivwTeacherLeaveDetailDataset=new IVW_TEACHER_LEAVE_DETAILS_DATASET();
        } 
           if(ivwUnauthRecords==null){
            

              ivwUnauthRecords=new IVW_UNAUTH_RECORDS_DATASET();
        }  
            if(notificationBatchIndicatorDataset==null){
            

              notificationBatchIndicatorDataset=new NOTIFICATION_BATCH_INDICATOR_DATASET();
        }  
         if(otherActivityBatchIndicatorDataset==null){
            

              otherActivityBatchIndicatorDataset=new OTHER_ACTIVITY_BATCH_INDICATOR_DATASET();
        }  
          if(retentionPeriodDataset==null){
            

              retentionPeriodDataset=new RETENTION_PERIOD_DATASET();
        }     
           
           
           
           
           if(todaynotificationDataset==null){
            

              todaynotificationDataset=new TODAY_NOTIFICATION_DATASET();
        } 
           
           
         
        }else if(serviceType.equals("USER")){
         
         
         if(classRoleMappingDataSet==null){
            
            classRoleMappingDataSet=new UVW_CLASS_ENTITY_ROLEMAPPING_DATASET();
        }
        if(instituteRoleMappingDataSet==null){
            
            instituteRoleMappingDataSet=new UVW_INSTITUTE_ENTITY_ROLEMAPPING_DATASET();
        }
        if(inLogDataSet==null){
            
            inLogDataSet=new UVW_IN_LOG_DATASET();
        }
        if(outLogDataSet==null){
            
            outLogDataSet=new UVW_OUT_LOG_DATASET();
        }
        if(parentRoleMappingDataSet==null){
            
            parentRoleMappingDataSet=new UVW_PARENT_STUDENT_ROLEMAPPING_DATASET();
        }
        if(teacherRoleMappingDataSet==null){
            
            teacherRoleMappingDataSet=new UVW_TEACHER_ENTITY_ROLEMAPPING_DATASET();
        }
        if(userCredentialsDataSet==null){
            
            userCredentialsDataSet=new UVW_USER_CREDENTIALS_DATASET();
        }
        if(userProfileDataSet==null){
            
            userProfileDataSet=new UVW_USER_PROFILE_DATASET();
        }
        if(userRoleDetailDataSet==null){
            
            userRoleDetailDataSet=new UVW_USER_ROLE_DETAIL_DATASET();
        }
        if(userContractMaster==null){
            
            userContractMaster=new UVW_USER_CONTRACT_MASTER_DATASET();
        }
         if(userRoleInstituteDataset==null){
            
            userRoleInstituteDataset=new UVW_USER_ROLE_INSTITUTE_DATASET();
        }
        
        
        
        
        }else if(serviceType.equals("APP")){
        if(errorMasterDataSet==null){
            
            errorMasterDataSet=new ERROR_MASTER_DATASET();
        }
        if(instituteMasterDataSet==null){
            
            instituteMasterDataSet=new INSTITUTE_MASTER_DATASET();
        }
        if(serviceMasterDataSet==null){
            
            serviceMasterDataSet=new SERVICE_TYPE_MASTER_DATASET();
        }
        if(appRetentionPeriodDataSet==null){
            
            appRetentionPeriodDataSet=new APP_RETENTION_PERIOD_DATASET();
        }
        if(appSupportDataSet==null){
            
           appSupportDataSet
                  =new APP_SUPPORT_DATASET();
        }
         if(appShippingStatus==null){
            
            appShippingStatus=new ARCH_SHIPPING_STATUS_DATASET();
        }
         if(appApplyStatusDataset==null){
            
            appApplyStatusDataset=new ARCH_APPLY_STATUS_DATASET();
        }
          if(batchConfigDataset==null){
            
            batchConfigDataset=new BATCH_CONFIG_DATASET();
        }
           if(batchServiceDataset==null){
            
            batchServiceDataset=new BATCH_SERVICES_DATASET();
        }
         if(contractMasterDataset==null){
            
            contractMasterDataset=new CONTRACT_MASTER_DATASET();
        }
        
        
        
        
        
        }else if(serviceType.equals("CLASS_BU")){
        
            
         if(classFeeAmountSummary==null){
            
                   classFeeAmountSummary=new ClassFeeAmountSummary_DataSet();
            }   
            
        if(classAttendanceDetailDataSet==null){
            
            classAttendanceDetailDataSet=new CLASS_ATTENDANCE_DETAIL_DATASET();
        }
        
        if(classOtherActivityDetail==null){
            
            classOtherActivityDetail=new ClassOtherActivityDetail_DataSet();
        }
        
       if(classAttendanceBusinessDataSet==null){
            
            classAttendanceBusinessDataSet=new ClassAttendanceDetail_DataSet();
        }
       
       if(classOtherActivitySummaryDataSet==null){
            
            classOtherActivitySummaryDataSet=new ClassOtherActivitySummary_DataSet();
        }
       
        if(classAttendanceSummary==null){
            
            classAttendanceSummary=new ClassAttendanceSummary_DataSet();
        }
       
        if(classMarksDetail==null){
            
            classMarksDetail=new ClassMarksDetail_DataSet();
        }

        if(classMarkSummary==null){
            
            classMarkSummary=new ClassMarksSummary_DataSet();
        }

        if(classFeeDetail==null){
            
            classFeeDetail=new ClassFeeDetail_DataSet();
        }
        
//       if(classDatasetBusiness==null){
//            
//            classDatasetBusiness=new ClassDataSetBusiness();
//        }
        if(markComparison==null){
            
            markComparison=new MarkComparison_DataSet();
        }
        
        if(classDetailsDataSet==null){
            
            classDetailsDataSet=new ClassDetails_DataSet();
        }
        
        
        
        }else if(serviceType.equals("INSTITUTE_BU")){
            
            if(markComparison==null){
            
            markComparison=new MarkComparison_DataSet();
        }
            
             if(classMarksDetail==null){
            
            classMarksDetail=new ClassMarksDetail_DataSet();
        }
             if(classMark==null){
            
            classMark=new CLASS_MARK_REPORT_DATASET();
        }
             
        if(feeDetailBusinessDataSet==null){
            
            feeDetailBusinessDataSet=new FeeDetailBusinessDataSet();
        }
        if(feeSummaryBusinessDataSet==null){
            
            feeSummaryBusinessDataSet=new FeeSummaryBusinessDataSet();
        }
             
        if(feePaymentBusinessDataSet==null){
            
            feePaymentBusinessDataSet=new FeePaymentBusinessDataSet();
        }     
           if(notificationDetailBusinessDataSet==null){
            
            notificationDetailBusinessDataSet=new NotificationDetailBusinessDataSet();
        }   
                
           if(studentDetailsDataSet==null){
            
            studentDetailsDataSet=new StudentDetailsDataSet();
        } 
                if(businessReportParams==null){
            
            businessReportParams=new BusinessReportParamsDataSet();
        }
        }else if(serviceType.equals("STUDENT_BU")){
            
            if(classAttendanceDetailDataSet==null){
            
            classAttendanceDetailDataSet=new CLASS_ATTENDANCE_DETAIL_DATASET();
        }
            
            if(studentAttendanceSummary==null){
            
                   studentAttendanceSummary=new StudentAttendanceSummary_DataSet();
            }
               
        
            if(studentFamilyDetailsBusiness==null){

                studentFamilyDetailsBusiness=new SVW_FAMILY_DETAILS_BUSINESS_DATASET();
            }
            if(studentFeeDetails==null){
            
                   studentFeeDetails=new StudentFeeDetails_DataSet();
            }
            if(studentProfileBusiness==null){
            
                   studentProfileBusiness=new SVW_STUDENT_PROFILE_BUSINESS_DATASET();
            }
             if(studentTimeTableBusiness==null){
            
                   studentTimeTableBusiness=new SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS_DATASET();
            }       
             if(studentOtherActivityBusiness==null){
            
                   studentOtherActivityBusiness=new SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS_DATASET();
            }
             if(classRank==null){
            
            classRank=new CLASS_EXAM_RANK_DATASET();
        }
         
          if(studentRank==null){
            
            studentRank=new StudentRankDataSet();
        }
          if(otherActivityDetail==null){
            
            otherActivityDetail=new StudentOtherActivityDetail_DataSet();
        }
          if(studentMarksBusiness==null){
            
                   studentMarksBusiness=new SVW_STUDENT_MARKS_BUSINESS_DATASET();
            }
if(studentSoftSkillsBusiness==null){
            
                   studentSoftSkillsBusiness=new SVW_STUDENT_SOFT_SKILLS_BUSINESS_DATASET();
            }
//            if(studentDataSet==null){
//                
//                studentDataSet=new StudentDataSet();
//            }
//            if(studentDatasetBusiness==null){
//                
//                studentDatasetBusiness=new StudentDataSetBusiness();
//            }
//            if(classDataSet==null){
//                
//                classDataSet=new ClassDataSet();
//            }
            
            
        }else if(serviceType.equals("TEACHER_BU")){
            
            if(teacherMarksDetail==null){
            
                   teacherMarksDetail=new TeacherMarksDetail_DataSet();
            }
            if(teacherAttendanceDataset==null){
            
                   teacherAttendanceDataset=new TeacherAttendanceSummary_DataSet();
            }

            if(teacherMarksSummary==null){
            
                   teacherMarksSummary=new TeacherMarksSummary_DataSet();
            }
            if(classFeeAmountSummary==null){
            
                   classFeeAmountSummary=new ClassFeeAmountSummary_DataSet();
            }
            if(SubstituteAvailabilityInOtherClass==null){
            
                   SubstituteAvailabilityInOtherClass=new SubstituteAvailabilityInOtherClass_DataSet();
            }
            if(SubstituteAvailabilityInSameClass==null){
            
                   SubstituteAvailabilityInSameClass=new SubstituteAvailabilityInSameClass_DataSet();
            }
            
            
            if(studentMarksBusiness==null){
            
                   studentMarksBusiness=new SVW_STUDENT_MARKS_BUSINESS_DATASET();
            }
//            if(teacherDatasetBusiness==null){
//            
//                   teacherDatasetBusiness=new TeacherDataSetBusiness();
//            }
            
            if(teacherContactPersoonBusiness==null){
                
                
                teacherContactPersoonBusiness=new TVW_CONTACT_PERSON_DETAILS_BUSINESS_DATASET();
            }
            
            if(teacherProfileBusiness==null){
                
                
                teacherProfileBusiness=new TVW_TEACHER_PROFILE_BUSINESS_DATASET();
            }
            if(teacherTimeTableDetail==null){
                
                
                teacherTimeTableDetail=new TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DATASET();
            }
            if(teacherTimeTableReport==null){
            
            teacherTimeTableReport=new TeacherTimeTableReport_DataSet();
        }
        
        if(substituteReportparam==null){
            
            substituteReportparam=new SubstituteReportParam_DataSet();
        }
            
        }else if(serviceType.equals("BATCH")){
            
            if(appEodDatset==null){
            
                   appEodDatset=new APP_EOD_STATUS_DATASET();
            }
            if(appEodErrorDataset==null){
            
                   appEodErrorDataset=new APP_EOD_STATUS_ERROR_DATASET();
            }
            if(appEodHistoryDataset==null){
            
                   appEodHistoryDataset=new APP_EOD_STATUS_HISTORY_DATASET();
            }
            if(assignmentEodDataSet==null){
            
                   assignmentEodDataSet=new ASSIGNMENT_EOD_STATUS_DATASET();
            }
            if(assignmentEodErrorDataset==null){
            
                   assignmentEodErrorDataset=new ASSIGNMENT_EOD_STATUS_ERROR_DATASET();
            }
            if(assignmentEodHistoryDataset==null){
            
                   assignmentEodHistoryDataset=new ASSIGNMENT_EOD_STATUS_HISTORY_DATASET();
            }
            if(eCircularEodDataSet==null){
            
                   eCircularEodDataSet=new E_CIRCULAR_EOD_STATUS_DATASET();
            }
            if(eCircularEodErrorDataset==null){
            
                   eCircularEodErrorDataset=new E_CIRCULAR_EOD_STATUS_ERROR_DATASET();
            }
            if(eCircularEodHistoryDataset==null){
            
                   eCircularEodHistoryDataset=new E_CIRCULAR_EOD_STATUS_HISTORY_DATASET();
            }
            if(batchConfig==null){
            
                   batchConfig=new BATCH_CONFIG_DATASET();
            }
            if(batchEodDataset==null){
            
                   batchEodDataset=new BATCH_STATUS_DATASET();
            }
            if(batchErrorDataset==null){
            
                   batchErrorDataset=new BATCH_STATUS_ERROR_DATASET();
            }
            if(batchHistoryDataset==null){
            
                   batchHistoryDataset=new BATCH_STATUS_HISTORY_DATASET();
            }
            if(instituteEodDataset==null){
            
                   instituteEodDataset=new INSTITUTE_EOD_STATUS_DATASET();
            }if(instituteErrorDataset==null){
            
                   instituteErrorDataset=new INSTITUTE_EOD_STATUS_ERROR_DATASET();
            }
            if(instituteEodHistoryDataset==null){
            
                   instituteEodHistoryDataset=new INSTITUTE_EOD_STATUS_HISTORY_DATASET();
            }
            if(studentAssignmentEodDatset==null){
            
                   studentAssignmentEodDatset=new STUDENT_ASSIGNMENT_EOD_STATUS_DATASET();
            }
            if(studentAssignmentErrorDataset==null){
            
                   studentAssignmentErrorDataset=new STUDENT_ASSIGNMENT_EOD_STATUS_ERROR_DATASET();
            }
            if(studentAssignmentHistoryDataSet==null){
            
                   studentAssignmentHistoryDataSet=new STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY_DATASET();
            }
            if(studentECircularEodDatset==null){
            
                   studentECircularEodDatset=new STUDENT_E_CIRCULAR_EOD_STATUS_DATASET();
            }
            if(studentECircularErrorDataset==null){
            
                   studentECircularErrorDataset=new STUDENT_E_CIRCULAR_EOD_STATUS_ERROR_DATASET();
            }
            if(studentECircularHistoryDataSet==null){
            
                   studentECircularHistoryDataSet=new STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY_DATASET();
            }
            
            if(teacherECircularEodDatset==null){
            
                   teacherECircularEodDatset=new TEACHER_E_CIRCULAR_EOD_STATUS_DATASET();
            }
            if(teacherECircularErrorDataset==null){
            
                   teacherECircularErrorDataset=new TEACHER_E_CIRCULAR_EOD_STATUS_ERROR_DATASET();
            }
            if(teacherECircularHistoryDataSet==null){
            
                   teacherECircularHistoryDataSet=new TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY_DATASET();
            }
            
            
            if(attendanceBatchDataSet==null){
            
                   attendanceBatchDataSet=new ATTENDANCE_BATCH_STATUS_DATASET();
            }
            if(attendanceBatchErrorDataSet==null){
            
                   attendanceBatchErrorDataSet=new ATTENDANCE_BATCH_STATUS_ERROR_DATASET();
            }
            if(attendanceBatchStatusHistoryDataSet==null){
            
                   attendanceBatchStatusHistoryDataSet=new ATTENDANCE_BATCH_STATUS_HISTORY_DATASET();
            }
            if(examBatchDataSet==null){
            
                   examBatchDataSet=new EXAM_BATCH_STATUS_DATASET();
            }
            if(examBatchErrorDataSet==null){
            
                   examBatchErrorDataSet=new EXAM_BATCH_STATUS_ERROR_DATASET();
            }
            if(examBatchHistoryDataSet==null){
            
                   examBatchHistoryDataSet=new EXAM_BATCH_STATUS_HISTORY_DATASET();
            }
            if(feeBatchDataSet==null){
            
                   feeBatchDataSet=new FEE_EOD_STATUS_DATASET();
            }
            if(feeBatchErrorDataSet==null){
            
                   feeBatchErrorDataSet=new FEE_EOD_STATUS_ERROR_DATASET();
            }
            if(feeBatchHistoryDataSet==null){
            
                   feeBatchHistoryDataSet=new FEE_EOD_STATUS_HISTORY_DATASET();
            }
            if(markBatchDataSet==null){
            
                   markBatchDataSet=new MARK_BATCH_STATUS_DATASET();
            }
            if(markBatchErrorDataSet==null){
            
                   markBatchErrorDataSet=new MARK_BATCH_STATUS_ERROR_DATASET();
            }
            if(markBatchHistoryDataSet==null){
            
                   markBatchHistoryDataSet=new MARK_BATCH_STATUS_HISTORY_DATASET();
            }
            if(notificationBatchDataSet==null){
            
                   notificationBatchDataSet=new NOTIFICATION_EOD_STATUS_DATASET();
            }
            if(notificationBatcherrorDataSet==null){
            
                   notificationBatcherrorDataSet=new NOTIFICATION_EOD_STATUS_ERROR_DATASET();
            }
            if(notificationBatchHistoryDataSet==null){
            
                   notificationBatchHistoryDataSet=new NOTIFICATION_EOD_STATUS_HISTORY_DATASET();
            }
            if(otherActivityBatchDataSet==null){
            
                   otherActivityBatchDataSet=new OTHER_ACTIVITY_EOD_STATUS_DATASET();
            }
            if(otherActivityBatchErrorDataSet==null){
            
                   otherActivityBatchErrorDataSet=new OTHER_ACTIVITY_EOD_STATUS_ERROR_DATASET();
            }
            if(otherActivityBatchHistoryDataSet==null){
            
                   otherActivityBatchHistoryDataSet=new OTHER_ACTIVITY_EOD_STATUS_HISTORY_DATASET();
            }
            if(studentAttBatchDataSet==null){
            
                   studentAttBatchDataSet=new STUDENT_ATTENDANCE_BATCH_STATUS_DATASET();
            }
            if(studentAttendanceBatchErrorDataSet==null){
            
                   studentAttendanceBatchErrorDataSet=new STUDENT_ATTENDANCE_BATCH_STATUS_ERROR_DATASET();
            }
            if(studentAttendanceBatchHistoryDataSet==null){
            
                   studentAttendanceBatchHistoryDataSet=new STUDENT_ATTENDANCE_BATCH_STATUS_HISTORY_DATASET();
            }
            if(studentExamBatchDataSet==null){
            
                   studentExamBatchDataSet=new STUDENT_EXAM_BATCH_STATUS_DATASET();
            }
            
            if(studentExamBatchErrorDataSet==null){
            
                   studentExamBatchErrorDataSet=new STUDENT_EXAM_BATCH_STATUS_ERROR_DATASET();
            }
            if(studentExamBatchHsitoryDataSet==null){
            
                   studentExamBatchHsitoryDataSet=new STUDENT_EXAM_BATCH_STATUS_HISTORY_DATASET();
            }
            if(studentFeeBatchDataSet==null){
            
                   studentFeeBatchDataSet=new STUDENT_FEE_EOD_STATUS_DATASET();
            }
            if(studentFeeBatchErrorDataSet==null){
            
                   studentFeeBatchErrorDataSet=new STUDENT_FEE_EOD_STATUS_ERROR_DATASET();
            }
            if(studentFeeBatchHistoryDataSet==null){
            
                   studentFeeBatchHistoryDataSet=new STUDENT_FEE_EOD_STATUS_HISTORY_DATASET();
            }
            if(studentMarkBatchDataSet==null){
            
                   studentMarkBatchDataSet=new STUDENT_MARK_BATCH_STATUS_DATASET();
            }
            if(studentMarkBatchErrorDataSet==null){
            
                   studentMarkBatchErrorDataSet=new STUDENT_MARK_BATCH_STATUS_ERROR_DATASET();
            }
            if(studentMarkBatchHistoryDataSet==null){
            
                   studentMarkBatchHistoryDataSet=new STUDENT_MARK_BATCH_STATUS_HISTORY_DATASET();
            }if(sttudentNotificationBatchDataSet==null){
            
                   sttudentNotificationBatchDataSet=new STUDENT_NOTIFICATION_EOD_STATUS_DATASET();
            }
            if(studentNotificationBatchErrorDataSet==null){
            
                   studentNotificationBatchErrorDataSet=new STUDENT_NOTIFICATION_EOD_STATUS_ERROR_DATASET();
            }
            
            if(studentNotificationBatchHistoryDataSet==null){
            
                   studentNotificationBatchHistoryDataSet=new STUDENT_NOTIFICATION_EOD_STATUS_HISTORY_DATASET();
            }
            if(studentOtherActivityBatchDataSet==null){
            
                   studentOtherActivityBatchDataSet=new STUDENT_OTHER_ACTIVITY_EOD_STATUS_DATASET();
            }
            if(studentOtherActivityBatchErrorDataSet==null){
            
                   studentOtherActivityBatchErrorDataSet=new STUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR_DATASET();
            }
            if(studentOtherActivityBAtchhistoryDataSet==null){
            
                   studentOtherActivityBAtchhistoryDataSet=new STUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY_DATASET();
            }
            if(studentTimeTableBatchDataSet==null){
            
                   studentTimeTableBatchDataSet=new STUDENT_TIMETABLE_BATCH_STATUS_DATASET();
            }
            if(studentTimeTableBatchErrorDataSet==null){
            
                   studentTimeTableBatchErrorDataSet=new STUDENT_TIMETABLE_BATCH_STATUS_ERROR_DATASET();
            }
            if(studentTimeTableBatchHistoryDataSet==null){
            
                   studentTimeTableBatchHistoryDataSet=new STUDENT_TIMETABLE_BATCH_STATUS_HISTORY_DATASET();
            }
            if(timeTableBatchDataSet==null){
            
                   timeTableBatchDataSet=new TIMETABLE_BATCH_STATUS_DATASET();
            }
            if(timeTableBatchErrorDataSet==null){
            
                   timeTableBatchErrorDataSet=new TIMETABLE_BATCH_STATUS_ERROR_DATASET();
            }
            if(timeTableBatchHistoryDataSet==null){
            
                   timeTableBatchHistoryDataSet=new TIMETABLE_BATCH_STATUS_HISTORY_DATASET();
            }
            if(studentAssignmentArchDataSet==null){
            
                   studentAssignmentArchDataSet=new STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_DATASET();
            }
            if(studentAssignmentArchErrorDataSet==null){
            
                   studentAssignmentArchErrorDataSet=new STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DATASET();
            }
            if(studentAssignmentArchHistoryDataSet==null){
            
                   studentAssignmentArchHistoryDataSet=new STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DATASET();
            }
            if(instituteAssignmentArchDataSet==null){
            
                   instituteAssignmentArchDataSet=new INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_DATASET();
            }
            if(instituteAssignmentArchErrorDataSet==null){
            
                   instituteAssignmentArchErrorDataSet=new INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DATASET();
            }
            if(studentNotificationSmsDataSet==null){
            
                   studentNotificationSmsDataSet=new STUDENT_NOTIFICATION_SMS_ERROR_DATASET();
            }
            
            if(studentNotificationEmailDataSet==null){
            
                   studentNotificationEmailDataSet=new STUDENT_NOTIFICATION_EMAIL_ERROR_DATASET();
            }
            
             if(feeNotificationEodStatusDataset==null){
            
                   feeNotificationEodStatusDataset=new FEE_NOTIFICATION_EOD_STATUS_DATASET();
            }
              if(feeNotificationEodStatusHistoryDataset==null){
            
                   feeNotificationEodStatusHistoryDataset=new FEE_NOTIFICATION_EOD_STATUS_HISTORY_DATASET();
            }
               if(feeNotificationEodStatusErrorDataset==null){
            
                   feeNotificationEodStatusErrorDataset=new FEE_NOTIFICATION_EOD_STATUS_ERROR_DATASET();
            }
                if(studentFeeNotificationEodStatus==null){
            
                   studentFeeNotificationEodStatus=new STUDENT_FEE_NOTIFICATION_EOD_STATUS_DATASET();
            }
                 if(studentFeeNotificationEodStatusHistory==null){
            
                   studentFeeNotificationEodStatusHistory=new STUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY_DATASET();
            }
              if(studentFeeNotificationEodStatusError==null){
            
                   studentFeeNotificationEodStatusError=new STUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR_DATASET();
            }
            
            
            if(studentEventNotificationEodStatus==null){
            
                   studentEventNotificationEodStatus=new STUDENT_EVENT_NOTIFICATION_EOD_STATUS_DATASET();
            }
                 if(studentEventNotificationEodStatusHistory==null){
            
                   studentEventNotificationEodStatusHistory=new STUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY_DATASET();
            }
              if(studentEventNotificationEodStatusError==null){
            
                   studentEventNotificationEodStatusError=new STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR_DATASET();
            }
            
            
        }
        
    }

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public E_CIRCULAR_EOD_STATUS_DATASET geteCircularEodDataSet() {
        return eCircularEodDataSet;
    }

    public void seteCircularEodDataSet(E_CIRCULAR_EOD_STATUS_DATASET eCircularEodDataSet) {
        this.eCircularEodDataSet = eCircularEodDataSet;
    }

    public E_CIRCULAR_EOD_STATUS_ERROR_DATASET geteCircularEodErrorDataset() {
        return eCircularEodErrorDataset;
    }

    public void seteCircularEodErrorDataset(E_CIRCULAR_EOD_STATUS_ERROR_DATASET eCircularEodErrorDataset) {
        this.eCircularEodErrorDataset = eCircularEodErrorDataset;
    }

    public E_CIRCULAR_EOD_STATUS_HISTORY_DATASET geteCircularEodHistoryDataset() {
        return eCircularEodHistoryDataset;
    }

    public void seteCircularEodHistoryDataset(E_CIRCULAR_EOD_STATUS_HISTORY_DATASET eCircularEodHistoryDataset) {
        this.eCircularEodHistoryDataset = eCircularEodHistoryDataset;
    }

    public STUDENT_E_CIRCULAR_EOD_STATUS_DATASET getStudentECircularEodDatset() {
        return studentECircularEodDatset;
    }

    public void setStudentECircularEodDatset(STUDENT_E_CIRCULAR_EOD_STATUS_DATASET studentECircularEodDatset) {
        this.studentECircularEodDatset = studentECircularEodDatset;
    }

    public STUDENT_E_CIRCULAR_EOD_STATUS_ERROR_DATASET getStudentECircularErrorDataset() {
        return studentECircularErrorDataset;
    }

    public void setStudentECircularErrorDataset(STUDENT_E_CIRCULAR_EOD_STATUS_ERROR_DATASET studentECircularErrorDataset) {
        this.studentECircularErrorDataset = studentECircularErrorDataset;
    }

    public STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY_DATASET getStudentECircularHistoryDataSet() {
        return studentECircularHistoryDataSet;
    }

    public void setStudentECircularHistoryDataSet(STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY_DATASET studentECircularHistoryDataSet) {
        this.studentECircularHistoryDataSet = studentECircularHistoryDataSet;
    }

    public TEACHER_E_CIRCULAR_EOD_STATUS_DATASET getTeacherECircularEodDatset() {
        return teacherECircularEodDatset;
    }

    public void setTeacherECircularEodDatset(TEACHER_E_CIRCULAR_EOD_STATUS_DATASET teacherECircularEodDatset) {
        this.teacherECircularEodDatset = teacherECircularEodDatset;
    }

    public TEACHER_E_CIRCULAR_EOD_STATUS_ERROR_DATASET getTeacherECircularErrorDataset() {
        return teacherECircularErrorDataset;
    }

    public void setTeacherECircularErrorDataset(TEACHER_E_CIRCULAR_EOD_STATUS_ERROR_DATASET teacherECircularErrorDataset) {
        this.teacherECircularErrorDataset = teacherECircularErrorDataset;
    }

    public TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY_DATASET getTeacherECircularHistoryDataSet() {
        return teacherECircularHistoryDataSet;
    }

    public void setTeacherECircularHistoryDataSet(TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY_DATASET teacherECircularHistoryDataSet) {
        this.teacherECircularHistoryDataSet = teacherECircularHistoryDataSet;
    }
    
    
    
    

    public TODAY_NOTIFICATION_DATASET getTodaynotificationDataset() {
        return todaynotificationDataset;
    }

    public void setTodaynotificationDataset(TODAY_NOTIFICATION_DATASET todaynotificationDataset) {
        this.todaynotificationDataset = todaynotificationDataset;
    }

    public STUDENT_EVENT_NOTIFICATION_EOD_STATUS_DATASET getStudentEventNotificationEodStatus() {
        return studentEventNotificationEodStatus;
    }

    public void setStudentEventNotificationEodStatus(STUDENT_EVENT_NOTIFICATION_EOD_STATUS_DATASET studentEventNotificationEodStatus) {
        this.studentEventNotificationEodStatus = studentEventNotificationEodStatus;
    }

    public STUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY_DATASET getStudentEventNotificationEodStatusHistory() {
        return studentEventNotificationEodStatusHistory;
    }

    public void setStudentEventNotificationEodStatusHistory(STUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY_DATASET studentEventNotificationEodStatusHistory) {
        this.studentEventNotificationEodStatusHistory = studentEventNotificationEodStatusHistory;
    }

    public STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR_DATASET getStudentEventNotificationEodStatusError() {
        return studentEventNotificationEodStatusError;
    }

    public void setStudentEventNotificationEodStatusError(STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR_DATASET studentEventNotificationEodStatusError) {
        this.studentEventNotificationEodStatusError = studentEventNotificationEodStatusError;
    }

    
    
    
    public CLASS_OTHER_ACTIVITY_REPORT_DATASET getClassOtherActivityReportDataset() {
        return classOtherActivityReportDataset;
    }

    public void setClassOtherActivityReportDataset(CLASS_OTHER_ACTIVITY_REPORT_DATASET classOtherActivityReportDataset) {
        this.classOtherActivityReportDataset = classOtherActivityReportDataset;
    }

    public OTHER_ACTIVITY_BATCH_INDICATOR_DATASET getOtherActivityBatchIndicatorDataset() {
        return otherActivityBatchIndicatorDataset;
    }

    public void setOtherActivityBatchIndicatorDataset(OTHER_ACTIVITY_BATCH_INDICATOR_DATASET otherActivityBatchIndicatorDataset) {
        this.otherActivityBatchIndicatorDataset = otherActivityBatchIndicatorDataset;
    }

    public INSTITUTE_CURRENT_DATE_DATASET getInstituteCurrentDateDataset() {
        return instituteCurrentDateDataset;
    }

    public void setInstituteCurrentDateDataset(INSTITUTE_CURRENT_DATE_DATASET instituteCurrentDateDataset) {
        this.instituteCurrentDateDataset = instituteCurrentDateDataset;
    }

    public FEE_BATCH_INDICATOR_DATASET getFeeBatchIndicatorDataset() {
        return feeBatchIndicatorDataset;
    }

    public void setFeeBatchIndicatorDataset(FEE_BATCH_INDICATOR_DATASET feeBatchIndicatorDataset) {
        this.feeBatchIndicatorDataset = feeBatchIndicatorDataset;
    }

    public PAYMENT_MASTER_DATASET getPaymentMasterDataset() {
        return paymentMasterDataset;
    }

    public void setPaymentMasterDataset(PAYMENT_MASTER_DATASET paymentMasterDataset) {
        this.paymentMasterDataset = paymentMasterDataset;
    }

    public INSTITUTE_OTHER_ACTIVITY_TRACKING_DATASET getInstituteOtherActivityTrackingDataset() {
        return instituteOtherActivityTrackingDataset;
    }

    public void setInstituteOtherActivityTrackingDataset(INSTITUTE_OTHER_ACTIVITY_TRACKING_DATASET instituteOtherActivityTrackingDataset) {
        this.instituteOtherActivityTrackingDataset = instituteOtherActivityTrackingDataset;
    }

    public CLASS_LEAVE_MANAGEMENT_DATASET getClassLeaveManagementDataset() {
        return classLeaveManagementDataset;
    }

    public void setClassLeaveManagementDataset(CLASS_LEAVE_MANAGEMENT_DATASET classLeaveManagementDataset) {
        this.classLeaveManagementDataset = classLeaveManagementDataset;
    }

    public INSTITUTE_OTP_STATUS_DATASET getInstituteOtpStatus() {
        return instituteOtpStatus;
    }

    public void setInstituteOtpStatus(INSTITUTE_OTP_STATUS_DATASET instituteOtpStatus) {
        this.instituteOtpStatus = instituteOtpStatus;
    }

    public STUDENT_OTP_STATUS_DATASET getStudentOtpStatus() {
        return studentOtpStatus;
    }

    public void setStudentOtpStatus(STUDENT_OTP_STATUS_DATASET studentOtpStatus) {
        this.studentOtpStatus = studentOtpStatus;
    }

    public INSTITITUTE_FEE_PAYMENT_DATASET getInstituteFeePaymentDataset() {
        return instituteFeePaymentDataset;
    }

    public void setInstituteFeePaymentDataset(INSTITITUTE_FEE_PAYMENT_DATASET instituteFeePaymentDataset) {
        this.instituteFeePaymentDataset = instituteFeePaymentDataset;
    }

    public UVW_USER_ROLE_INSTITUTE_DATASET getUserRoleInstituteDataset() {
        return userRoleInstituteDataset;
    }

    public void setUserRoleInstituteDataset(UVW_USER_ROLE_INSTITUTE_DATASET userRoleInstituteDataset) {
        this.userRoleInstituteDataset = userRoleInstituteDataset;
    }

    public UVW_USER_CONTRACT_MASTER_DATASET getUserContractMaster() {
        return userContractMaster;
    }

    public void setUserContractMaster(UVW_USER_CONTRACT_MASTER_DATASET userContractMaster) {
        this.userContractMaster = userContractMaster;
    }

    public SVW_STUDENT_OTHER_ACTIVITY_REPORT_DATASET getStudentOtherActivityReport() {
        return studentOtherActivityReport;
    }

    public void setStudentOtherActivityReport(SVW_STUDENT_OTHER_ACTIVITY_REPORT_DATASET studentOtherActivityReport) {
        this.studentOtherActivityReport = studentOtherActivityReport;
    }

    public SVW_STUDENT_NOTIFICATION_DATASET getSvwStudentNotificationDataset() {
        return svwStudentNotificationDataset;
    }

    public void setSvwStudentNotificationDataset(SVW_STUDENT_NOTIFICATION_DATASET svwStudentNotificationDataset) {
        this.svwStudentNotificationDataset = svwStudentNotificationDataset;
    }

    public SVW_STUDENT_E_CIRCULAR_DATASET getStudentEcircularDataset() {
        return studentEcircularDataset;
    }

    public void setStudentEcircularDataset(SVW_STUDENT_E_CIRCULAR_DATASET studentEcircularDataset) {
        this.studentEcircularDataset = studentEcircularDataset;
    }

    public STUDENT_NOTIFICATION_STATUS_DATASET getStudentNotificationDataset() {
        return studentNotificationDataset;
    }

    public void setStudentNotificationDataset(STUDENT_NOTIFICATION_STATUS_DATASET studentNotificationDataset) {
        this.studentNotificationDataset = studentNotificationDataset;
    }

    public CONTRACT_MASTER_DATASET getContractMasterDataset() {
        return contractMasterDataset;
    }

    public void setContractMasterDataset(CONTRACT_MASTER_DATASET contractMasterDataset) {
        this.contractMasterDataset = contractMasterDataset;
    }

    public BATCH_CONFIG_DATASET getBatchConfigDataset() {
        return batchConfigDataset;
    }

    public void setBatchConfigDataset(BATCH_CONFIG_DATASET batchConfigDataset) {
        this.batchConfigDataset = batchConfigDataset;
    }

    
    
    public BATCH_SERVICES_DATASET getBatchServiceDataset() {
        return batchServiceDataset;
    }

    public void setBatchServiceDataset(BATCH_SERVICES_DATASET batchServiceDataset) {
        this.batchServiceDataset = batchServiceDataset;
    }

    public APP_SUPPORT_DATASET getAppSupportDataSet() {
        return appSupportDataSet;
    }

    public void setAppSupportDataSet(APP_SUPPORT_DATASET appSupportDataSet) {
        this.appSupportDataSet = appSupportDataSet;
    }
    
    public APP_RETENTION_PERIOD_DATASET getAppRetentionPeriodDataSet() {
        return appRetentionPeriodDataSet;
    }

    public void setAppRetentionPeriodDataSet(APP_RETENTION_PERIOD_DATASET appRetentionPeriodDataSet) {
        this.appRetentionPeriodDataSet = appRetentionPeriodDataSet;
    }
    
    public SVW_CONTACT_PERSON_DETAILS_DATASET getStudentContactPersonDataSet() {
        return studentContactPersonDataSet;
    }

    public STUDENT_NOTIFICATION_SMS_ERROR_DATASET getStudentNotificationSmsDataSet() {
        return studentNotificationSmsDataSet;
    }

    public void setStudentNotificationSmsDataSet(STUDENT_NOTIFICATION_SMS_ERROR_DATASET studentNotificationSmsDataSet) {
        this.studentNotificationSmsDataSet = studentNotificationSmsDataSet;
    }

    public STUDENT_NOTIFICATION_EMAIL_ERROR_DATASET getStudentNotificationEmailDataSet() {
        return studentNotificationEmailDataSet;
    }

    public void setStudentNotificationEmailDataSet(STUDENT_NOTIFICATION_EMAIL_ERROR_DATASET studentNotificationEmailDataSet) {
        this.studentNotificationEmailDataSet = studentNotificationEmailDataSet;
    }

//    public ClassDataSetBusiness getClassDatasetBusiness() {
//        return classDatasetBusiness;
//    }
//
//    public void setClassDatasetBusiness(ClassDataSetBusiness classDatasetBusiness) {
//        this.classDatasetBusiness = classDatasetBusiness;
//    }

    public SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS_DATASET getStudentTimeTableBusiness() {
        return studentTimeTableBusiness;
    }

    public void setStudentTimeTableBusiness(SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS_DATASET studentTimeTableBusiness) {
        this.studentTimeTableBusiness = studentTimeTableBusiness;
    }

    public MarkComparison_DataSet getMarkComparison() {
        return markComparison;
    }

    public void setMarkComparison(MarkComparison_DataSet markComparison) {
        this.markComparison = markComparison;
    }

    

    public SVW_FAMILY_DETAILS_DATASET getStudentFamilyDetailsDataSet() {
        return studentFamilyDetailsDataSet;
    }

    public SVW_STUDENT_ASSIGNMENT_DATASET getStudentAssignmentDataSet() {
        return studentAssignmentDataSet;
    }

    public SVW_STUDENT_ATTENDANCE_DATASET getStudentAttendanceDataSet() {
        return studentAttendanceDataSet;
    }

    public SVW_STUDENT_MARKS_BUSINESS_DATASET getStudentMarksBusiness() {
        return studentMarksBusiness;
    }

    public void setStudentMarksBusiness(SVW_STUDENT_MARKS_BUSINESS_DATASET studentMarksBusiness) {
        this.studentMarksBusiness = studentMarksBusiness;
    }

    public TVW_CONTACT_PERSON_DETAILS_BUSINESS_DATASET getTeacherContactPersoonBusiness() {
        return teacherContactPersoonBusiness;
    }

    public void setTeacherContactPersoonBusiness(TVW_CONTACT_PERSON_DETAILS_BUSINESS_DATASET teacherContactPersoonBusiness) {
        this.teacherContactPersoonBusiness = teacherContactPersoonBusiness;
    }

    public TVW_TEACHER_PROFILE_BUSINESS_DATASET getTeacherProfileBusiness() {
        return teacherProfileBusiness;
    }

    public void setTeacherProfileBusiness(TVW_TEACHER_PROFILE_BUSINESS_DATASET teacherProfileBusiness) {
        this.teacherProfileBusiness = teacherProfileBusiness;
    }

    public TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DATASET getTeacherTimeTableDetail() {
        return teacherTimeTableDetail;
    }

    public void setTeacherTimeTableDetail(TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DATASET teacherTimeTableDetail) {
        this.teacherTimeTableDetail = teacherTimeTableDetail;
    }

    
    
    public SVW_STUDENT_CALENDER_DATASET getStudentCalenderDataSet() {
        return studentCalenderDataSet;
    }

    public SVW_STUDENT_EXAM_SCHEDULE_DETAIL_DATASET getStudentExamScheduleDetailDataSet() {
        return studentExamScheduleDetailDataSet;
    }

    public SVW_STUDENT_EXAM_SCHEDULE_MASTER_DATASET getStudentExamScheduleMasterDataSet() {
        return studentExamScheduleMasterDataSet;
    }

    public SVW_STUDENT_FEE_MANAGEMENT_DATASET getStudentFeeManagementDataSet() {
        return studentFeeManagementDataSet;
    }

    public SVW_STUDENT_LEAVE_MANAGEMENT_DATASET getStudentLeaveManagementDataSet() {
        return studentLeaveManagementDataSet;
    }

    public SVW_STUDENT_MARKS_DATASET getStudentMarksDataSet() {
        return studentMarksDataSet;
    }

    public CLASS_EXAM_RANK_DATASET getClassRank() {
        return classRank;
    }

    public void setClassRank(CLASS_EXAM_RANK_DATASET classRank) {
        this.classRank = classRank;
    }

    public StudentRankDataSet getStudentRank() {
        return studentRank;
    }

    public void setStudentRank(StudentRankDataSet studentRank) {
        this.studentRank = studentRank;
    }

    public StudentOtherActivityDetail_DataSet getOtherActivityDetail() {
        return otherActivityDetail;
    }

    public void setOtherActivityDetail(StudentOtherActivityDetail_DataSet otherActivityDetail) {
        this.otherActivityDetail = otherActivityDetail;
    }
    

    public SVW_STUDENT_OTHER_ACTIVITY_DATASET getStudentOtherActivityDataSet() {
        return studentOtherActivityDataSet;
    }

    public SVW_STUDENT_PAYMENT_DATASET getStudentPaymentDataSet() {
        return studentPaymentDataSet;
    }

    public SVW_STUDENT_PROFILE_DATASET getStudentProfileDataSet() {
        return studentProfileDataSet;
    }

    public SVW_STUDENT_PRORESS_CARD_DATASET getStudentProgressCardDataSet() {
        return studentProgressCardDataSet;
    }

    public SVW_STUDENT_TIMETABLE_DETAIL_DATASET getStudentTimeTableDetailDataSet() {
        return studentTimeTableDetailDataSet;
    }

    public SVW_STUDENT_TIMETABLE_MASTER_DATASET getStudentTimeTableMasterDataSet() {
        return studentTimeTableMasterDataSet;
    }


    public TVW_CONTACT_PERSON_DETAILS_DATASET getTeacherContactPersonDataSet() {
        return teacherContactPersonDataSet;
    }

   
    public TVW_TEACHER_LEAVE_MANAGEMENT_DATASET getTeacherLeaveManagementDataSet() {
        return teacherLeaveManagementDataSet;
    }

    public TVW_TEACHER_PROFILE_DATASET getTeacherProfileDataSet() {
        return teacherProfileDataSet;
    }

    
    public CLASS_ASSIGNMENT_DATASET getClassAssignmentDataSet() {
        return classAssignmentDataSet;
    }

    public CLASS_ATTENDANCE_DETAIL_DATASET getClassAttendanceDetailDataSet() {
        return classAttendanceDetailDataSet;
    }

    public CLASS_ATTENDANCE_MASTER_DATASET getClassAttendanceMasterDataSet() {
        return classAttendanceMasterDataSet;
    }

    public CLASS_EXAM_SCHEDULE_DETAIL_DATASET getClassExamScheduleDetail() {
        return classExamScheduleDetail;
    }

    public CLASS_EXAM_SCHEDULE_MASTER_DATASET getClassExamScheduleMaster() {
        return classExamScheduleMaster;
    }

    public CLASS_FEE_MANAGEMENT_DATASET getClassFeeManagementDataSet() {
        return classFeeManagementDataSet;
    }

    public CLASS_MARK_ENTRY_DATASET getClassMarkEntryDataSet() {
        return classMarkEntryDataSet;
    }

    public CLASS_STUDENT_MAPPING_DATASET getClassStudentMappingDataSet() {
        return classStudentMappingDataSet;
    }

    public CLASS_TIMETABLE_DETAIL_DATASET getClassTimeTableDetailDataset() {
        return classTimeTableDetailDataset;
    }

    public CLASS_TIMETABLE_MASTER_DATASET getClassTimeTableMasterDataSet() {
        return classTimeTableMasterDataSet;
    }

    public STUDENT_MARKS_DATASET getStudentMarks() {
        return studentMarks;
    }

    public IVW_CONTENT_TYPE_MASTER_DATASET getContentTypeMasterDataSet() {
        return contentTypeMasterDataSet;
    }

    public void setContentTypeMasterDataSet(IVW_CONTENT_TYPE_MASTER_DATASET contentTypeMasterDataSet) {
        this.contentTypeMasterDataSet = contentTypeMasterDataSet;
    }

    public IVW_FEE_TYPE_MASTER_DATASET getFeeTypeMasterDataSet() {
        return feeTypeMasterDataSet;
    }

    public void setFeeTypeMasterDataSet(IVW_FEE_TYPE_MASTER_DATASET feeTypeMasterDataSet) {
        this.feeTypeMasterDataSet = feeTypeMasterDataSet;
    }

    public IVW_INSTITUTE_EXAM_MASTER_DATASET getExamTypeMasterDataSet() {
        return examTypeMasterDataSet;
    }

    public void setExamTypeMasterDataSet(IVW_INSTITUTE_EXAM_MASTER_DATASET examTypeMasterDataSet) {
        this.examTypeMasterDataSet = examTypeMasterDataSet;
    }

    public IVW_LEAVE_TYPE_MASTER_DATASET getLeaveTypeMasterDataSet() {
        return leaveTypeMasterDataSet;
    }

    public void setLeaveTypeMasterDataSet(IVW_LEAVE_TYPE_MASTER_DATASET leaveTypeMasterDataSet) {
        this.leaveTypeMasterDataSet = leaveTypeMasterDataSet;
    }

    public IVW_NOTIFICATION_TYPE_MASTER_DATASET getNotificationTypeMasterDataSet() {
        return notificationTypeMasterDataSet;
    }

    public void setNotificationTypeMasterDataSet(IVW_NOTIFICATION_TYPE_MASTER_DATASET notificationTypeMasterDataSet) {
        this.notificationTypeMasterDataSet = notificationTypeMasterDataSet;
    }

    public IVW_PERIOD_MASTER_DATASET getPeriodMasterDataSet() {
        return periodMasterDataSet;
    }

    public void setPeriodMasterDataSet(IVW_PERIOD_MASTER_DATASET periodMasterDataSet) {
        this.periodMasterDataSet = periodMasterDataSet;
    }

    public IVW_STANDARD_MASTER_DATASET getStandardMasterDataSet() {
        return standardMasterDataSet;
    }

    public void setStandardMasterDataSet(IVW_STANDARD_MASTER_DATASET standardMasterDataSet) {
        this.standardMasterDataSet = standardMasterDataSet;
    }

    public IVW_STUDENT_MASTER_DATASET getStudentMasterDataSet() {
        return studentMasterDataSet;
    }

    public void setStudentMasterDataSet(IVW_STUDENT_MASTER_DATASET studentMasterDataSet) {
        this.studentMasterDataSet = studentMasterDataSet;
    }

    public IVW_SUBJECT_GRADE_MASTER_DATASET getGradeMasterDataSet() {
        return gradeMasterDataSet;
    }

    public void setGradeMasterDataSet(IVW_SUBJECT_GRADE_MASTER_DATASET gradeMasterDataSet) {
        this.gradeMasterDataSet = gradeMasterDataSet;
    }

    public IVW_SUBJECT_MASTER_DATASET getSubjectMasterDataSet() {
        return subjectMasterDataSet;
    }

    public void setSubjectMasterDataSet(IVW_SUBJECT_MASTER_DATASET subjectMasterDataSet) {
        this.subjectMasterDataSet = subjectMasterDataSet;
    }

    public IVW_TEACHER_MASTER_DATASET getTeacherMasterDataSet() {
        return teacherMasterDataSet;
    }

    public void setTeacherMasterDataSet(IVW_TEACHER_MASTER_DATASET teacherMasterDataSet) {
        this.teacherMasterDataSet = teacherMasterDataSet;
    }

    public UVW_CLASS_ENTITY_ROLEMAPPING_DATASET getClassRoleMappingDataSet() {
        return classRoleMappingDataSet;
    }

    public UVW_INSTITUTE_ENTITY_ROLEMAPPING_DATASET getInstituteRoleMappingDataSet() {
        return instituteRoleMappingDataSet;
    }

    public UVW_IN_LOG_DATASET getInLogDataSet() {
        return inLogDataSet;
    }

    public UVW_OUT_LOG_DATASET getOutLogDataSet() {
        return outLogDataSet;
    }

    public UVW_PARENT_STUDENT_ROLEMAPPING_DATASET getParentRoleMappingDataSet() {
        return parentRoleMappingDataSet;
    }

    public UVW_TEACHER_ENTITY_ROLEMAPPING_DATASET getTeacherRoleMappingDataSet() {
        return teacherRoleMappingDataSet;
    }

    public UVW_USER_CREDENTIALS_DATASET getUserCredentialsDataSet() {
        return userCredentialsDataSet;
    }

    public UVW_USER_PROFILE_DATASET getUserProfileDataSet() {
        return userProfileDataSet;
    }

    public UVW_USER_ROLE_DETAIL_DATASET getUserRoleDetailDataSet() {
        return userRoleDetailDataSet;
    }

    public UVW_USER_ROLE_MASTER_DATASET getUserRoleMasterDataSet() {
        return userRoleMasterDataSet;
    }

    public ERROR_MASTER_DATASET getErrorMasterDataSet() {
        return errorMasterDataSet;
    }

    public SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS_DATASET getStudentOtherActivityBusiness() {
        return studentOtherActivityBusiness;
    }

    public void setStudentOtherActivityBusiness(SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS_DATASET studentOtherActivityBusiness) {
        this.studentOtherActivityBusiness = studentOtherActivityBusiness;
    }
    
    

    public INSTITUTE_MASTER_DATASET getInstituteMasterDataSet() {
        return instituteMasterDataSet;
    }

    public SERVICE_TYPE_MASTER_DATASET getServiceMasterDataSet() {
        return serviceMasterDataSet;
    }

    public ClassOtherActivityDetail_DataSet getClassOtherActivityDetail() {
        return classOtherActivityDetail;
    }

    public ClassAttendanceDetail_DataSet getClassAttendanceBusinessDataSet() {
        return classAttendanceBusinessDataSet;
    }

    public ClassOtherActivitySummary_DataSet getClassOtherActivitySummaryDataSet() {
        return classOtherActivitySummaryDataSet;
    }

    public ReportUtil getReportUtil(CohesiveSession session) {
        reportUtil.setDebug(session.getDebug());
        reportUtil.setI_db_properties(session.getCohesiveproperties());
        return reportUtil;
    }

    public ClassAttendanceSummary_DataSet getClassAttendanceSummary() {
        return classAttendanceSummary;
    }

    public StudentAttendanceSummary_DataSet getStudentAttendanceSummary() {
        return studentAttendanceSummary;
    }

    public ClassMarksDetail_DataSet getClassMarksDetail() {
        return classMarksDetail;
    }

    public TeacherMarksDetail_DataSet getTeacherMarksDetail() {
        return teacherMarksDetail;
    }


    public ClassMarksSummary_DataSet getClassMarkSummary() {
        return classMarkSummary;
    }

    public TeacherAttendanceSummary_DataSet getTeacherAttendanceDataset() {
        return teacherAttendanceDataset;
    }



    public TeacherMarksSummary_DataSet getTeacherMarksSummary() {
        return teacherMarksSummary;
    }

    public StudentFeeDetails_DataSet getStudentFeeDetails() {
        return studentFeeDetails;
    }


    public ClassFeeDetail_DataSet getClassFeeDetail() {
        return classFeeDetail;
    }

    public ClassFeeAmountSummary_DataSet getClassFeeAmountSummary() {
        return classFeeAmountSummary;
    }

    public void setClassFeeAmountSummary(ClassFeeAmountSummary_DataSet classFeeAmountSummary) {
        this.classFeeAmountSummary = classFeeAmountSummary;
    }

    public SubstituteAvailabilityInOtherClass_DataSet getSubstituteAvailabilityInOtherClass() {
        return SubstituteAvailabilityInOtherClass;
    }


    public SubstituteAvailabilityInSameClass_DataSet getSubstituteAvailabilityInSameClass() {
        return SubstituteAvailabilityInSameClass;
    }

    public ReportValidation getReportValidation(CohesiveSession session) {
        rv.setDebug(session.getDebug());
        rv.setI_db_properties(session.getCohesiveproperties());
        return rv;
    }

    public APP_EOD_STATUS_DATASET getAppEodDatset() {
        return appEodDatset;
    }

    public void setAppEodDatset(APP_EOD_STATUS_DATASET appEodDatset) {
        this.appEodDatset = appEodDatset;
    }

    public APP_EOD_STATUS_ERROR_DATASET getAppEodErrorDataset() {
        return appEodErrorDataset;
    }

    public void setAppEodErrorDataset(APP_EOD_STATUS_ERROR_DATASET appEodErrorDataset) {
        this.appEodErrorDataset = appEodErrorDataset;
    }

    public APP_EOD_STATUS_HISTORY_DATASET getAppEodHistoryDataset() {
        return appEodHistoryDataset;
    }

    public void setAppEodHistoryDataset(APP_EOD_STATUS_HISTORY_DATASET appEodHistoryDataset) {
        this.appEodHistoryDataset = appEodHistoryDataset;
    }

    public ASSIGNMENT_EOD_STATUS_DATASET getAssignmentEodDataSet() {
        return assignmentEodDataSet;
    }

    public void setAssignmentEodDataSet(ASSIGNMENT_EOD_STATUS_DATASET assignmentEodDataSet) {
        this.assignmentEodDataSet = assignmentEodDataSet;
    }

    public ASSIGNMENT_EOD_STATUS_ERROR_DATASET getAssignmentEodErrorDataset() {
        return assignmentEodErrorDataset;
    }

    public void setAssignmentEodErrorDataset(ASSIGNMENT_EOD_STATUS_ERROR_DATASET assignmentEodErrorDataset) {
        this.assignmentEodErrorDataset = assignmentEodErrorDataset;
    }

    public ASSIGNMENT_EOD_STATUS_HISTORY_DATASET getAssignmentEodHistoryDataset() {
        return assignmentEodHistoryDataset;
    }

    public void setAssignmentEodHistoryDataset(ASSIGNMENT_EOD_STATUS_HISTORY_DATASET assignmentEodHistoryDataset) {
        this.assignmentEodHistoryDataset = assignmentEodHistoryDataset;
    }

    public BATCH_CONFIG_DATASET getBatchConfig() {
        return batchConfig;
    }

    public void setBatchConfig(BATCH_CONFIG_DATASET batchConfig) {
        this.batchConfig = batchConfig;
    }

    public BATCH_STATUS_DATASET getBatchEodDataset() {
        return batchEodDataset;
    }

    public void setBatchEodDataset(BATCH_STATUS_DATASET batchEodDataset) {
        this.batchEodDataset = batchEodDataset;
    }

    public BATCH_STATUS_ERROR_DATASET getBatchErrorDataset() {
        return batchErrorDataset;
    }

    public void setBatchErrorDataset(BATCH_STATUS_ERROR_DATASET batchErrorDataset) {
        this.batchErrorDataset = batchErrorDataset;
    }

    public BATCH_STATUS_HISTORY_DATASET getBatchHistoryDataset() {
        return batchHistoryDataset;
    }

//    public TeacherDataSetBusiness getTeacherDatasetBusiness() {
//        return teacherDatasetBusiness;
//    }
//
//    public void setTeacherDatasetBusiness(TeacherDataSetBusiness teacherDatasetBusiness) {
//        this.teacherDatasetBusiness = teacherDatasetBusiness;
//    }

    public void setBatchHistoryDataset(BATCH_STATUS_HISTORY_DATASET batchHistoryDataset) {
        this.batchHistoryDataset = batchHistoryDataset;
    }

    public INSTITUTE_EOD_STATUS_DATASET getInstituteEodDataset() {
        return instituteEodDataset;
    }

    public void setInstituteEodDataset(INSTITUTE_EOD_STATUS_DATASET instituteEodDataset) {
        this.instituteEodDataset = instituteEodDataset;
    }

    public INSTITUTE_EOD_STATUS_ERROR_DATASET getInstituteErrorDataset() {
        return instituteErrorDataset;
    }

    public void setInstituteErrorDataset(INSTITUTE_EOD_STATUS_ERROR_DATASET instituteErrorDataset) {
        this.instituteErrorDataset = instituteErrorDataset;
    }

    public INSTITUTE_EOD_STATUS_HISTORY_DATASET getInstituteEodHistoryDataset() {
        return instituteEodHistoryDataset;
    }

    public void setInstituteEodHistoryDataset(INSTITUTE_EOD_STATUS_HISTORY_DATASET instituteEodHistoryDataset) {
        this.instituteEodHistoryDataset = instituteEodHistoryDataset;
    }

    public STUDENT_ASSIGNMENT_EOD_STATUS_DATASET getStudentAssignmentEodDatset() {
        return studentAssignmentEodDatset;
    }

    public void setStudentAssignmentEodDatset(STUDENT_ASSIGNMENT_EOD_STATUS_DATASET studentAssignmentEodDatset) {
        this.studentAssignmentEodDatset = studentAssignmentEodDatset;
    }

    public STUDENT_ASSIGNMENT_EOD_STATUS_ERROR_DATASET getStudentAssignmentErrorDataset() {
        return studentAssignmentErrorDataset;
    }

    public void setStudentAssignmentErrorDataset(STUDENT_ASSIGNMENT_EOD_STATUS_ERROR_DATASET studentAssignmentErrorDataset) {
        this.studentAssignmentErrorDataset = studentAssignmentErrorDataset;
    }

    public STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY_DATASET getStudentAssignmentHistoryDataSet() {
        return studentAssignmentHistoryDataSet;
    }

    public void setStudentAssignmentHistoryDataSet(STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY_DATASET studentAssignmentHistoryDataSet) {
        this.studentAssignmentHistoryDataSet = studentAssignmentHistoryDataSet;
    }

    public static InitialContext getContxt() {
        return contxt;
    }

    public static void setContxt(InitialContext contxt) {
        ReportDependencyInjection.contxt = contxt;
    }



    public void setReportUtil(ReportUtil reportUtil) {
        this.reportUtil = reportUtil;
    }

    public ReportValidation getRv() {
        return rv;
    }

    public void setRv(ReportValidation rv) {
        this.rv = rv;
    }

    public ATTENDANCE_BATCH_STATUS_DATASET getAttendanceBatchDataSet() {
        return attendanceBatchDataSet;
    }

    public void setAttendanceBatchDataSet(ATTENDANCE_BATCH_STATUS_DATASET attendanceBatchDataSet) {
        this.attendanceBatchDataSet = attendanceBatchDataSet;
    }

    public ATTENDANCE_BATCH_STATUS_ERROR_DATASET getAttendanceBatchErrorDataSet() {
        return attendanceBatchErrorDataSet;
    }

    public void setAttendanceBatchErrorDataSet(ATTENDANCE_BATCH_STATUS_ERROR_DATASET attendanceBatchErrorDataSet) {
        this.attendanceBatchErrorDataSet = attendanceBatchErrorDataSet;
    }

    public ClassDetails_DataSet getClassDetailsDataSet() {
        return classDetailsDataSet;
    }

    public void setClassDetailsDataSet(ClassDetails_DataSet classDetailsDataSet) {
        this.classDetailsDataSet = classDetailsDataSet;
    }

    
    
    
    public ATTENDANCE_BATCH_STATUS_HISTORY_DATASET getAttendanceBatchStatusHistoryDataSet() {
        return attendanceBatchStatusHistoryDataSet;
    }

    public void setAttendanceBatchStatusHistoryDataSet(ATTENDANCE_BATCH_STATUS_HISTORY_DATASET attendanceBatchStatusHistoryDataSet) {
        this.attendanceBatchStatusHistoryDataSet = attendanceBatchStatusHistoryDataSet;
    }

    public EXAM_BATCH_STATUS_DATASET getExamBatchDataSet() {
        return examBatchDataSet;
    }

    public void setExamBatchDataSet(EXAM_BATCH_STATUS_DATASET examBatchDataSet) {
        this.examBatchDataSet = examBatchDataSet;
    }

    public EXAM_BATCH_STATUS_ERROR_DATASET getExamBatchErrorDataSet() {
        return examBatchErrorDataSet;
    }

    public void setExamBatchErrorDataSet(EXAM_BATCH_STATUS_ERROR_DATASET examBatchErrorDataSet) {
        this.examBatchErrorDataSet = examBatchErrorDataSet;
    }

    public EXAM_BATCH_STATUS_HISTORY_DATASET getExamBatchHistoryDataSet() {
        return examBatchHistoryDataSet;
    }

    public void setExamBatchHistoryDataSet(EXAM_BATCH_STATUS_HISTORY_DATASET examBatchHistoryDataSet) {
        this.examBatchHistoryDataSet = examBatchHistoryDataSet;
    }

    public FEE_EOD_STATUS_DATASET getFeeBatchDataSet() {
        return feeBatchDataSet;
    }

    public void setFeeBatchDataSet(FEE_EOD_STATUS_DATASET feeBatchDataSet) {
        this.feeBatchDataSet = feeBatchDataSet;
    }

    public FEE_EOD_STATUS_ERROR_DATASET getFeeBatchErrorDataSet() {
        return feeBatchErrorDataSet;
    }

    public void setFeeBatchErrorDataSet(FEE_EOD_STATUS_ERROR_DATASET feeBatchErrorDataSet) {
        this.feeBatchErrorDataSet = feeBatchErrorDataSet;
    }

    public FEE_EOD_STATUS_HISTORY_DATASET getFeeBatchHistoryDataSet() {
        return feeBatchHistoryDataSet;
    }

    public void setFeeBatchHistoryDataSet(FEE_EOD_STATUS_HISTORY_DATASET feeBatchHistoryDataSet) {
        this.feeBatchHistoryDataSet = feeBatchHistoryDataSet;
    }

    public MARK_BATCH_STATUS_DATASET getMarkBatchDataSet() {
        return markBatchDataSet;
    }

    public void setMarkBatchDataSet(MARK_BATCH_STATUS_DATASET markBatchDataSet) {
        this.markBatchDataSet = markBatchDataSet;
    }

    public MARK_BATCH_STATUS_ERROR_DATASET getMarkBatchErrorDataSet() {
        return markBatchErrorDataSet;
    }

    public void setMarkBatchErrorDataSet(MARK_BATCH_STATUS_ERROR_DATASET markBatchErrorDataSet) {
        this.markBatchErrorDataSet = markBatchErrorDataSet;
    }

    public MARK_BATCH_STATUS_HISTORY_DATASET getMarkBatchHistoryDataSet() {
        return markBatchHistoryDataSet;
    }

    public void setMarkBatchHistoryDataSet(MARK_BATCH_STATUS_HISTORY_DATASET markBatchHistoryDataSet) {
        this.markBatchHistoryDataSet = markBatchHistoryDataSet;
    }

    public NOTIFICATION_EOD_STATUS_DATASET getNotificationBatchDataSet() {
        return notificationBatchDataSet;
    }

    public void setNotificationBatchDataSet(NOTIFICATION_EOD_STATUS_DATASET notificationBatchDataSet) {
        this.notificationBatchDataSet = notificationBatchDataSet;
    }

    public NOTIFICATION_EOD_STATUS_ERROR_DATASET getNotificationBatcherrorDataSet() {
        return notificationBatcherrorDataSet;
    }

    public void setNotificationBatcherrorDataSet(NOTIFICATION_EOD_STATUS_ERROR_DATASET notificationBatcherrorDataSet) {
        this.notificationBatcherrorDataSet = notificationBatcherrorDataSet;
    }

    public NOTIFICATION_EOD_STATUS_HISTORY_DATASET getNotificationBatchHistoryDataSet() {
        return notificationBatchHistoryDataSet;
    }

    public void setNotificationBatchHistoryDataSet(NOTIFICATION_EOD_STATUS_HISTORY_DATASET notificationBatchHistoryDataSet) {
        this.notificationBatchHistoryDataSet = notificationBatchHistoryDataSet;
    }

    public OTHER_ACTIVITY_EOD_STATUS_DATASET getOtherActivityBatchDataSet() {
        return otherActivityBatchDataSet;
    }

    public NotificationDetailBusinessDataSet getNotificationDetailBusinessDataSet() {
        return notificationDetailBusinessDataSet;
    }

    public void setNotificationDetailBusinessDataSet(NotificationDetailBusinessDataSet notificationDetailBusinessDataSet) {
        this.notificationDetailBusinessDataSet = notificationDetailBusinessDataSet;
    }
    

    public void setOtherActivityBatchDataSet(OTHER_ACTIVITY_EOD_STATUS_DATASET otherActivityBatchDataSet) {
        this.otherActivityBatchDataSet = otherActivityBatchDataSet;
    }

    public OTHER_ACTIVITY_EOD_STATUS_ERROR_DATASET getOtherActivityBatchErrorDataSet() {
        return otherActivityBatchErrorDataSet;
    }

    public void setOtherActivityBatchErrorDataSet(OTHER_ACTIVITY_EOD_STATUS_ERROR_DATASET otherActivityBatchErrorDataSet) {
        this.otherActivityBatchErrorDataSet = otherActivityBatchErrorDataSet;
    }

    public OTHER_ACTIVITY_EOD_STATUS_HISTORY_DATASET getOtherActivityBatchHistoryDataSet() {
        return otherActivityBatchHistoryDataSet;
    }

    public void setOtherActivityBatchHistoryDataSet(OTHER_ACTIVITY_EOD_STATUS_HISTORY_DATASET otherActivityBatchHistoryDataSet) {
        this.otherActivityBatchHistoryDataSet = otherActivityBatchHistoryDataSet;
    }

    public STUDENT_ATTENDANCE_BATCH_STATUS_DATASET getStudentAttBatchDataSet() {
        return studentAttBatchDataSet;
    }

    public void setStudentAttBatchDataSet(STUDENT_ATTENDANCE_BATCH_STATUS_DATASET studentAttBatchDataSet) {
        this.studentAttBatchDataSet = studentAttBatchDataSet;
    }

    public STUDENT_ATTENDANCE_BATCH_STATUS_ERROR_DATASET getStudentAttendanceBatchErrorDataSet() {
        return studentAttendanceBatchErrorDataSet;
    }

    public void setStudentAttendanceBatchErrorDataSet(STUDENT_ATTENDANCE_BATCH_STATUS_ERROR_DATASET studentAttendanceBatchErrorDataSet) {
        this.studentAttendanceBatchErrorDataSet = studentAttendanceBatchErrorDataSet;
    }

    public STUDENT_ATTENDANCE_BATCH_STATUS_HISTORY_DATASET getStudentAttendanceBatchHistoryDataSet() {
        return studentAttendanceBatchHistoryDataSet;
    }

    public void setStudentAttendanceBatchHistoryDataSet(STUDENT_ATTENDANCE_BATCH_STATUS_HISTORY_DATASET studentAttendanceBatchHistoryDataSet) {
        this.studentAttendanceBatchHistoryDataSet = studentAttendanceBatchHistoryDataSet;
    }

    public SVW_STUDENT_PROFILE_BUSINESS_DATASET getStudentProfileBusiness() {
        return studentProfileBusiness;
    }

    public void setStudentProfileBusiness(SVW_STUDENT_PROFILE_BUSINESS_DATASET studentProfileBusiness) {
        this.studentProfileBusiness = studentProfileBusiness;
    }

//    public StudentDataSet getStudentDataSet() {
//        return studentDataSet;
//    }
//
//    public void setStudentDataSet(StudentDataSet studentDataSet) {
//        this.studentDataSet = studentDataSet;
//    }
//
//    public StudentDataSetBusiness getStudentDatasetBusiness() {
//        return studentDatasetBusiness;
//    }
//
//    public void setStudentDatasetBusiness(StudentDataSetBusiness studentDatasetBusiness) {
//        this.studentDatasetBusiness = studentDatasetBusiness;
//    }
//    
//    
//
//    public ClassDataSet getClassDataSet() {
//        return classDataSet;
//    }
//
//    public void setClassDataSet(ClassDataSet classDataSet) {
//        this.classDataSet = classDataSet;
//    }
//    
    
    

    public STUDENT_EXAM_BATCH_STATUS_DATASET getStudentExamBatchDataSet() {
        return studentExamBatchDataSet;
    }

    public void setStudentExamBatchDataSet(STUDENT_EXAM_BATCH_STATUS_DATASET studentExamBatchDataSet) {
        this.studentExamBatchDataSet = studentExamBatchDataSet;
    }

    public STUDENT_EXAM_BATCH_STATUS_ERROR_DATASET getStudentExamBatchErrorDataSet() {
        return studentExamBatchErrorDataSet;
    }

    public void setStudentExamBatchErrorDataSet(STUDENT_EXAM_BATCH_STATUS_ERROR_DATASET studentExamBatchErrorDataSet) {
        this.studentExamBatchErrorDataSet = studentExamBatchErrorDataSet;
    }

    public STUDENT_EXAM_BATCH_STATUS_HISTORY_DATASET getStudentExamBatchHsitoryDataSet() {
        return studentExamBatchHsitoryDataSet;
    }

    public void setStudentExamBatchHsitoryDataSet(STUDENT_EXAM_BATCH_STATUS_HISTORY_DATASET studentExamBatchHsitoryDataSet) {
        this.studentExamBatchHsitoryDataSet = studentExamBatchHsitoryDataSet;
    }

    public STUDENT_FEE_EOD_STATUS_DATASET getStudentFeeBatchDataSet() {
        return studentFeeBatchDataSet;
    }

    public void setStudentFeeBatchDataSet(STUDENT_FEE_EOD_STATUS_DATASET studentFeeBatchDataSet) {
        this.studentFeeBatchDataSet = studentFeeBatchDataSet;
    }

    public STUDENT_FEE_EOD_STATUS_ERROR_DATASET getStudentFeeBatchErrorDataSet() {
        return studentFeeBatchErrorDataSet;
    }

    public void setStudentFeeBatchErrorDataSet(STUDENT_FEE_EOD_STATUS_ERROR_DATASET studentFeeBatchErrorDataSet) {
        this.studentFeeBatchErrorDataSet = studentFeeBatchErrorDataSet;
    }

    public STUDENT_FEE_EOD_STATUS_HISTORY_DATASET getStudentFeeBatchHistoryDataSet() {
        return studentFeeBatchHistoryDataSet;
    }

    public void setStudentFeeBatchHistoryDataSet(STUDENT_FEE_EOD_STATUS_HISTORY_DATASET studentFeeBatchHistoryDataSet) {
        this.studentFeeBatchHistoryDataSet = studentFeeBatchHistoryDataSet;
    }

    public STUDENT_MARK_BATCH_STATUS_DATASET getStudentMarkBatchDataSet() {
        return studentMarkBatchDataSet;
    }

    public void setStudentMarkBatchDataSet(STUDENT_MARK_BATCH_STATUS_DATASET studentMarkBatchDataSet) {
        this.studentMarkBatchDataSet = studentMarkBatchDataSet;
    }

    public STUDENT_MARK_BATCH_STATUS_ERROR_DATASET getStudentMarkBatchErrorDataSet() {
        return studentMarkBatchErrorDataSet;
    }

    public void setStudentMarkBatchErrorDataSet(STUDENT_MARK_BATCH_STATUS_ERROR_DATASET studentMarkBatchErrorDataSet) {
        this.studentMarkBatchErrorDataSet = studentMarkBatchErrorDataSet;
    }

    public STUDENT_MARK_BATCH_STATUS_HISTORY_DATASET getStudentMarkBatchHistoryDataSet() {
        return studentMarkBatchHistoryDataSet;
    }

    public void setStudentMarkBatchHistoryDataSet(STUDENT_MARK_BATCH_STATUS_HISTORY_DATASET studentMarkBatchHistoryDataSet) {
        this.studentMarkBatchHistoryDataSet = studentMarkBatchHistoryDataSet;
    }

    public STUDENT_NOTIFICATION_EOD_STATUS_DATASET getSttudentNotificationBatchDataSet() {
        return sttudentNotificationBatchDataSet;
    }

    public void setSttudentNotificationBatchDataSet(STUDENT_NOTIFICATION_EOD_STATUS_DATASET sttudentNotificationBatchDataSet) {
        this.sttudentNotificationBatchDataSet = sttudentNotificationBatchDataSet;
    }

    public STUDENT_NOTIFICATION_EOD_STATUS_ERROR_DATASET getStudentNotificationBatchErrorDataSet() {
        return studentNotificationBatchErrorDataSet;
    }

    public void setStudentNotificationBatchErrorDataSet(STUDENT_NOTIFICATION_EOD_STATUS_ERROR_DATASET studentNotificationBatchErrorDataSet) {
        this.studentNotificationBatchErrorDataSet = studentNotificationBatchErrorDataSet;
    }

    public STUDENT_NOTIFICATION_EOD_STATUS_HISTORY_DATASET getStudentNotificationBatchHistoryDataSet() {
        return studentNotificationBatchHistoryDataSet;
    }

    public void setStudentNotificationBatchHistoryDataSet(STUDENT_NOTIFICATION_EOD_STATUS_HISTORY_DATASET studentNotificationBatchHistoryDataSet) {
        this.studentNotificationBatchHistoryDataSet = studentNotificationBatchHistoryDataSet;
    }

    public STUDENT_OTHER_ACTIVITY_EOD_STATUS_DATASET getStudentOtherActivityBatchDataSet() {
        return studentOtherActivityBatchDataSet;
    }

    public void setStudentOtherActivityBatchDataSet(STUDENT_OTHER_ACTIVITY_EOD_STATUS_DATASET studentOtherActivityBatchDataSet) {
        this.studentOtherActivityBatchDataSet = studentOtherActivityBatchDataSet;
    }

    public STUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR_DATASET getStudentOtherActivityBatchErrorDataSet() {
        return studentOtherActivityBatchErrorDataSet;
    }

    public void setStudentOtherActivityBatchErrorDataSet(STUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR_DATASET studentOtherActivityBatchErrorDataSet) {
        this.studentOtherActivityBatchErrorDataSet = studentOtherActivityBatchErrorDataSet;
    }

    public STUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY_DATASET getStudentOtherActivityBAtchhistoryDataSet() {
        return studentOtherActivityBAtchhistoryDataSet;
    }

    public void setStudentOtherActivityBAtchhistoryDataSet(STUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY_DATASET studentOtherActivityBAtchhistoryDataSet) {
        this.studentOtherActivityBAtchhistoryDataSet = studentOtherActivityBAtchhistoryDataSet;
    }

    public STUDENT_TIMETABLE_BATCH_STATUS_DATASET getStudentTimeTableBatchDataSet() {
        return studentTimeTableBatchDataSet;
    }

    public void setStudentTimeTableBatchDataSet(STUDENT_TIMETABLE_BATCH_STATUS_DATASET studentTimeTableBatchDataSet) {
        this.studentTimeTableBatchDataSet = studentTimeTableBatchDataSet;
    }

    public STUDENT_TIMETABLE_BATCH_STATUS_ERROR_DATASET getStudentTimeTableBatchErrorDataSet() {
        return studentTimeTableBatchErrorDataSet;
    }

    public void setStudentTimeTableBatchErrorDataSet(STUDENT_TIMETABLE_BATCH_STATUS_ERROR_DATASET studentTimeTableBatchErrorDataSet) {
        this.studentTimeTableBatchErrorDataSet = studentTimeTableBatchErrorDataSet;
    }

    public STUDENT_TIMETABLE_BATCH_STATUS_HISTORY_DATASET getStudentTimeTableBatchHistoryDataSet() {
        return studentTimeTableBatchHistoryDataSet;
    }

    public void setStudentTimeTableBatchHistoryDataSet(STUDENT_TIMETABLE_BATCH_STATUS_HISTORY_DATASET studentTimeTableBatchHistoryDataSet) {
        this.studentTimeTableBatchHistoryDataSet = studentTimeTableBatchHistoryDataSet;
    }

    public TIMETABLE_BATCH_STATUS_DATASET getTimeTableBatchDataSet() {
        return timeTableBatchDataSet;
    }

    public void setTimeTableBatchDataSet(TIMETABLE_BATCH_STATUS_DATASET timeTableBatchDataSet) {
        this.timeTableBatchDataSet = timeTableBatchDataSet;
    }

    public TIMETABLE_BATCH_STATUS_ERROR_DATASET getTimeTableBatchErrorDataSet() {
        return timeTableBatchErrorDataSet;
    }

    public void setTimeTableBatchErrorDataSet(TIMETABLE_BATCH_STATUS_ERROR_DATASET timeTableBatchErrorDataSet) {
        this.timeTableBatchErrorDataSet = timeTableBatchErrorDataSet;
    }

    public TIMETABLE_BATCH_STATUS_HISTORY_DATASET getTimeTableBatchHistoryDataSet() {
        return timeTableBatchHistoryDataSet;
    }

    public void setTimeTableBatchHistoryDataSet(TIMETABLE_BATCH_STATUS_HISTORY_DATASET timeTableBatchHistoryDataSet) {
        this.timeTableBatchHistoryDataSet = timeTableBatchHistoryDataSet;
    }

    public STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_DATASET getStudentAssignmentArchDataSet() {
        return studentAssignmentArchDataSet;
    }

    public void setStudentAssignmentArchDataSet(STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_DATASET studentAssignmentArchDataSet) {
        this.studentAssignmentArchDataSet = studentAssignmentArchDataSet;
    }

    public STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DATASET getStudentAssignmentArchErrorDataSet() {
        return studentAssignmentArchErrorDataSet;
    }

    public void setStudentAssignmentArchErrorDataSet(STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DATASET studentAssignmentArchErrorDataSet) {
        this.studentAssignmentArchErrorDataSet = studentAssignmentArchErrorDataSet;
    }

    public STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DATASET getStudentAssignmentArchHistoryDataSet() {
        return studentAssignmentArchHistoryDataSet;
    }

    public void setStudentAssignmentArchHistoryDataSet(STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DATASET studentAssignmentArchHistoryDataSet) {
        this.studentAssignmentArchHistoryDataSet = studentAssignmentArchHistoryDataSet;
    }

    public INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_DATASET getInstituteAssignmentArchDataSet() {
        return instituteAssignmentArchDataSet;
    }

    public void setInstituteAssignmentArchDataSet(INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_DATASET instituteAssignmentArchDataSet) {
        this.instituteAssignmentArchDataSet = instituteAssignmentArchDataSet;
    }

    public INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DATASET getInstituteAssignmentArchErrorDataSet() {
        return instituteAssignmentArchErrorDataSet;
    }

    public void setInstituteAssignmentArchErrorDataSet(INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DATASET instituteAssignmentArchErrorDataSet) {
        this.instituteAssignmentArchErrorDataSet = instituteAssignmentArchErrorDataSet;
    }

    public INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DATASET getInstituteAssignmentArchHistoryDataSet() {
        return instituteAssignmentArchHistoryDataSet;
    }

    public void setInstituteAssignmentArchHistoryDataSet(INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DATASET instituteAssignmentArchHistoryDataSet) {
        this.instituteAssignmentArchHistoryDataSet = instituteAssignmentArchHistoryDataSet;
    }

    
    public SVW_FAMILY_DETAILS_BUSINESS_DATASET getStudentFamilyDetailsBusiness() {
        return studentFamilyDetailsBusiness;
    }

    public void setStudentFamilyDetailsBusiness(SVW_FAMILY_DETAILS_BUSINESS_DATASET studentFamilyDetailsBusiness) {
        this.studentFamilyDetailsBusiness = studentFamilyDetailsBusiness;
    }


    
    
    public IDBCoreService getDbcoreservice() throws NamingException{
        //EJB Integration change
        //return dbcoreservice;
       // InitialContext a =new InitialContext();
    IDBCoreService dbcoreservice = (IDBCoreService)
         contxt.lookup("java:app/CohesiveDatabase/DBCoreService!com.ibd.cohesive.db.core.IDBCoreService");
    return dbcoreservice;
    }
   
    public  IndexCoreService getIndexcoreservice() throws NamingException { 
        IndexCoreService service = new IndexCoreService();
        return service;
    }
    public IDBReadService getDbreadservice() throws NamingException { //EJB Integration change
      
      IDBReadService dbreadservice = (IDBReadService)
         contxt.lookup("java:app/CohesiveDatabase/DBReadService!com.ibd.cohesive.db.read.IDBReadService");
      return dbreadservice;
    }


    public IMetaDataService getMetadataservice() throws NamingException { //EJB Integration change
IMetaDataService metadataservice = (IMetaDataService)
         contxt.lookup("java:app/CohesiveDatabase/MetaDataService!com.ibd.cohesive.db.core.metadata.IMetaDataService");
      return metadataservice;
    }
 
    public IndexReadService getIndexreadservice() throws NamingException {//EJB Integration change
        IndexReadService indexreadservice = new IndexReadService();//EJB Integration change
        return indexreadservice;
    }

 
public IPDataService getPdataservice() throws NamingException { //EJB Integration change

        IPDataService pdataservice = (IPDataService)
         contxt.lookup("java:app/CohesiveDatabase/PDataService!com.ibd.cohesive.db.core.pdata.IPDataService");
      return pdataservice;
    }






    public IDBReadBufferService getDBReadBufferService() throws NamingException{ //EJB Integration change
    
        IDBReadBufferService readBuffer = (IDBReadBufferService)
         contxt.lookup("java:app/CohesiveDatabase/DBReadBufferService!com.ibd.cohesive.db.readbuffer.IDBReadBufferService");
        return readBuffer;
        
    }
    
    public  IStudentDataSet getStudentDataset()throws NamingException{
        IStudentDataSet studentDataSet=(IStudentDataSet)
          
         contxt.lookup("java:global/CohesiveReportEngine/ReportProcessing/StudentDataSet");
        return studentDataSet;
    }
    
    public static ITeacherDataSet getTeacherDataset()throws NamingException{
        ITeacherDataSet teacherDataSet=(ITeacherDataSet)
          
         contxt.lookup("java:app/CohesiveReportEngine-ejb/TeacherDataSet!com.ibd.cohesive.report.dbreport.dataSets.teacher.ITeacherDataSet");
        return teacherDataSet;
    }
    
    public  IClassDataSet getClassDataset()throws NamingException{
        IClassDataSet classDataSet=(IClassDataSet)
          
         contxt.lookup("java:global/CohesiveReportEngine/ReportProcessing/ClassDataset");
        return classDataSet;
    }
    
    public static IInstituteDataSet getInstituteDataset()throws NamingException{
        IInstituteDataSet instituteDataSet=(IInstituteDataSet)
          
         contxt.lookup("java:app/CohesiveReportEngine-ejb/InstituteDataSet!com.ibd.cohesive.report.dbreport.dataSets.institute.IInstituteDataSet");
        return instituteDataSet;
    }
    
     public static IUserDataSet getUserDataset()throws NamingException{
        IUserDataSet userDataSet=(IUserDataSet)
          
         contxt.lookup("java:app/CohesiveReportEngine-ejb/UserDataSet!com.ibd.cohesive.report.dbreport.dataSets.user.IUserDataSet");
        return userDataSet;
    }
     
      public  IAppDataSet getAppDataset()throws NamingException{
        IAppDataSet appDataSet=(IAppDataSet)
          
//         contxt.lookup("java:app/CohesiveReportEngine-ejb/AppDataSet!com.ibd.cohesive.report.dbreport.dataSets.app.IAppDataSet");
           remoteContxt.lookup("ejb:CohesiveReportEngine/CohesiveReportEngine-ejb/AppDataSet!com.ibd.cohesive.report.dbreport.dataSets.app.IAppDataSet");
        return appDataSet;
    }
      
      public static IClassDataSetBusiness getClassDataSetBusiness() throws NamingException{
          IClassDataSetBusiness classDataSetBusiness=(IClassDataSetBusiness)
                contxt.lookup("java:app/CohesiveReportEngine-ejb/ClassDataSetBusiness!com.ibd.cohesive.report.businessreport.dataSets.classEntity.IClassDataSetBusiness");
        return classDataSetBusiness;  
          
      }
      public static IStudentDataSetBusiness getStudentDataSetBusiness() throws NamingException{
          IStudentDataSetBusiness studentDataSetBusiness=(IStudentDataSetBusiness)
                contxt.lookup("java:app/CohesiveReportEngine-ejb/StudentDataSetBusiness!com.ibd.cohesive.report.businessreport.dataSets.student.IStudentDataSetBusiness");
        return studentDataSetBusiness;  
          
      }
     public static ITeacherDataSetBusiness getTeacherDataSetBusiness() throws NamingException{
          ITeacherDataSetBusiness teacherDataSetBusiness=(ITeacherDataSetBusiness)
                contxt.lookup("java:app/CohesiveReportEngine-ejb/TeacherDataSetBusiness!com.ibd.cohesive.report.businessreport.dataSets.teacher.ITeacherDataSetBusiness");
        return teacherDataSetBusiness;  
          
      }
     
     public IStudentAssignmentSummary getStudentAssignmentSummary() throws NamingException { //EJB Integration change

        IStudentAssignmentSummary assignmentSummary = (IStudentAssignmentSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/StudentAssignmentSummary!com.ibd.cohesive.app.business.student.summary.studentassignmentsummary.IStudentAssignmentSummary");
      return assignmentSummary;
    }
     public IStudentProfileSummary getStudentProfileSummary() throws NamingException { //EJB Integration change

        IStudentProfileSummary profileSummary = (IStudentProfileSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/StudentProfileSummary!com.ibd.cohesive.app.business.student.summary.studentprofile.IStudentProfileSummary");
      return profileSummary;
    }
     public IStudentTimeTableSummary getStudentTimeTableSummary() throws NamingException { //EJB Integration change

        IStudentTimeTableSummary timeTableSummary = (IStudentTimeTableSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/StudentTimeTableSummary!com.ibd.cohesive.app.business.student.summary.studenttimetablesummary.IStudentTimeTableSummary");
      return timeTableSummary;
    }
     public IStudentAttendanceSummary getStudentAttendanceSummaryService() throws NamingException { //EJB Integration change

        IStudentAttendanceSummary attendanceSummary = (IStudentAttendanceSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/StudentAttendanceSummary!com.ibd.cohesive.app.business.student.summary.studentattendancesummary.IStudentAttendanceSummary");
      return attendanceSummary;
    }
     public IStudentExamScheduleSummary getStudentExamScheduleSummary() throws NamingException { //EJB Integration change

        IStudentExamScheduleSummary examScheduleSummary = (IStudentExamScheduleSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/StudentExamScheduleSummary!com.ibd.cohesive.app.business.student.summary.studentexamschedule.IStudentExamScheduleSummary");
      return examScheduleSummary;
    }
    
    public IStudentFeeManagementSummary getStudentFeeManagementSummary() throws NamingException { //EJB Integration change

        IStudentFeeManagementSummary feeManagementSummary = (IStudentFeeManagementSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/StudentFeeManagementSummary!com.ibd.cohesive.app.business.student.summary.studentfeemanagement.IStudentFeeManagementSummary");
      return feeManagementSummary;
    } 
    public IStudentLeaveManagementSummary getStudentLeaveManagementSummary() throws NamingException { //EJB Integration change

        IStudentLeaveManagementSummary leaveManagementSummary = (IStudentLeaveManagementSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/StudentLeaveManagementSummary!com.ibd.cohesive.app.business.student.summary.studentleavemanagementsummary.IStudentLeaveManagementSummary");
      return leaveManagementSummary;
    }
    public IStudentOtherActivitySummary getStudentOtherActivitySummary() throws NamingException { //EJB Integration change

        IStudentOtherActivitySummary otherActivitySummary = (IStudentOtherActivitySummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/StudentOtherActivitySummary!com.ibd.cohesive.app.business.student.summary.studentotheractivitysummary.IStudentOtherActivitySummary");
      return otherActivitySummary;
    }
     public IStudentPaymentSummary getStudentPaymentSummary() throws NamingException { //EJB Integration change

        IStudentPaymentSummary paymentSummary = (IStudentPaymentSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/StudentPaymentSummary!com.ibd.cohesive.app.business.student.summary.studentpaymentsummary.IStudentPaymentSummary");
      return paymentSummary;
    }
     public IStudentProgressCardSummary getStudentProgressCardSummary() throws NamingException { //EJB Integration change

        IStudentProgressCardSummary Summary = (IStudentProgressCardSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/StudentProgressCardSummary!com.ibd.cohesive.app.business.student.summary.studentprogresscard.IStudentProgressCardSummary");
      return Summary;
    }
     public ITeacherAttendanceSummary getTeacherAttendanceSummaryService() throws NamingException { //EJB Integration change

        ITeacherAttendanceSummary attendanceSummary = (ITeacherAttendanceSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/TeacherAttendanceSummary!com.ibd.cohesive.app.business.teacher.summary.teacherattendance.ITeacherAttendanceSummary");
      return attendanceSummary;
    }
    public ITeacherLeaveManagementSummary getTeacherLeaveManagementSummary() throws NamingException { //EJB Integration change

        ITeacherLeaveManagementSummary leaveManagementSummary = (ITeacherLeaveManagementSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/TeacherLeaveManagementSummary!com.ibd.cohesive.app.business.teacher.summary.teacherleavemanagement.ITeacherLeaveManagementSummary");
      return leaveManagementSummary;
    }
    public ITeacherProfileSummary getTeacherProfileSummary() throws NamingException { //EJB Integration change

        ITeacherProfileSummary profileSummary = (ITeacherProfileSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/TeacherProfileSummary!com.ibd.cohesive.app.business.teacher.summary.teacherprofile.ITeacherProfileSummary");
      return profileSummary;
    }
    public ITeacherTimeTableSummary getTeacherTimeTableSummary() throws NamingException { //EJB Integration change

        ITeacherTimeTableSummary timeTableSummary = (ITeacherTimeTableSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/TeacherTimeTableSummary!com.ibd.cohesive.app.business.teacher.summary.teachertimetable.ITeacherTimeTableSummary");
      return timeTableSummary;
    }
    public IClassTimeTableSummary getClassTimeTableSummary() throws NamingException { //EJB Integration change

        IClassTimeTableSummary timeTableSummary = (IClassTimeTableSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/ClassTimeTableSummary!com.ibd.cohesive.app.business.classentity.summary.classtimetable.IClassTimeTableSummary");
      return timeTableSummary;
    }
    public IClassExamScheduleSummary getClassExamScheduleSummary() throws NamingException { //EJB Integration change

        IClassExamScheduleSummary examScheduleSummary = (IClassExamScheduleSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/ClassExamScheduleSummary!com.ibd.cohesive.app.business.classentity.summary.classexamschedule.IClassExamScheduleSummary");
      return examScheduleSummary;
    }
    public IClassMarkSummary getClassMarkSummaryService() throws NamingException { //EJB Integration change

        IClassMarkSummary markSummarySummary = (IClassMarkSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/ClassMarkSummary!com.ibd.cohesive.app.business.classentity.summary.classexamschedule.IClassMarkSummary");
      return markSummarySummary;
    }
    public IInstituteAssignmentSummary getInstituteAssignmentSummary() throws NamingException { //EJB Integration change

        IInstituteAssignmentSummary summary = (IInstituteAssignmentSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/InstituteAssignmentSummary!com.ibd.cohesive.app.business.institute.summary.InstituteAssignment.IInstituteAssignmentSummary");
      return summary;
    }
    public IInstituteOtherActivitySummary getInstituteOtherActivitySummary() throws NamingException { //EJB Integration change

        IInstituteOtherActivitySummary summary = (IInstituteOtherActivitySummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/InstituteOtherActivitySummary!com.ibd.cohesive.app.business.institute.summary.InstituteOtherActivity.IInstituteOtherActivitySummary");
      return summary;
    }
    public IInstituteFeeManagementSummary getInstituteFeeManagementSummary() throws NamingException { //EJB Integration change

        IInstituteFeeManagementSummary summary = (IInstituteFeeManagementSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/InstituteFeeManagementSummary!com.ibd.cohesive.app.business.institute.summary.Institutefeemanagement.IInstituteFeeManagementSummary");
      return summary;
    }
    public IInstitutePaymentSummary getInstitutePaymentSummary() throws NamingException { //EJB Integration change

        IInstitutePaymentSummary summary = (IInstitutePaymentSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/InstitutePaymentSummary!com.ibd.cohesive.app.business.institute.summary.Institutepayment.IInstitutePaymentSummary");
      return summary;
    }
    public IGroupMappingSummary getGroupMappingSummary() throws NamingException { //EJB Integration change

        IGroupMappingSummary summary = (IGroupMappingSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/GroupMappingSummary!com.ibd.cohesive.app.business.institute.summary.groupMapping.IGroupMappingSummary");
      return summary;
    }
    public IHolidayMaintenanceSummary getHolidayMaintenanceSummary() throws NamingException { //EJB Integration change

        IHolidayMaintenanceSummary summary = (IHolidayMaintenanceSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/HolidayMaintenanceSummary!com.ibd.cohesive.app.business.institute.summary.holidaymaintenance.IHolidayMaintenanceSummary");
      return summary;
    }
    public INotificationSummary getNotificationSummary() throws NamingException { //EJB Integration change

        INotificationSummary summary = (INotificationSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/NotificationSummary!com.ibd.cohesive.app.business.institute.summary.notification.INotificationSummary");
      return summary;
    }
    
    public IUserProfileSummary getUserProfileSummary() throws NamingException { //EJB Integration change

        IUserProfileSummary summary = (IUserProfileSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/UserProfileSummary!com.ibd.cohesive.app.business.user.summary.userprofile.IUserProfileSummary");
      return summary;
    }
    
    public IUserRoleSummary getUserRoleSummary() throws NamingException { //EJB Integration change

        IUserRoleSummary summary = (IUserRoleSummary)
         contxt.lookup("java:app/CohesiveBusiness-ejb/UserRoleSummary!com.ibd.cohesive.app.business.user.summary.userrole.IUserRoleSummary");
      return summary;
    }
    
     public  IBatchDataset getBatchDataset()throws NamingException{
        IBatchDataset batchDataSet=(IBatchDataset)
          
         contxt.lookup("java:app/CohesiveReportEngine-ejb/BatchDataset!com.ibd.cohesive.report.dbreport.dataSets.batch.IBatchDataset");
        return batchDataSet;
    }
     
//     public  IPreProcessor getPreProcessor()throws NamingException{
//        IPreProcessor preProcessor=(IPreProcessor)
//          
//         contxt.lookup("java:app/CohesiveReportEngine-ejb/PreProcessor!com.ibd.cohesive.report.preprocessor.IPreProcessor");
//        return preProcessor;
//    }
     
     
     public ITokenValidationService getTokenValidationService() throws NamingException{
        //EJB Integration change
        //return dbcoreservice;
       // InitialContext a =new InitialContext();
    ITokenValidationService authTokenValiation = (ITokenValidationService)
        // contxt.lookup("java:app/CohesiveBusiness-ejb/TokenValidateService!com.ibd.businessViews.ITokenValidationService");
 remoteContxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/TokenValidateService!com.ibd.businessViews.ITokenValidationService");

            
            return authTokenValiation;
    }
     
//    public IStudentDataSet getStudentExistinMedicalDetailTable()throws NamingException{
//        IStudentDataSet studentDataSet=(IStudentDataSet)
//          
//         contxt.lookup("java:app/CohesiveReportEngine-ejb/StudentDataSet!com.ibd.cohesive.report.dbreport.dataSets.student.IStudentDataSet");
//        return studentDataSet;
//    }
    public ARCH_SHIPPING_STATUS_DATASET getAppShippingStatus() {
        return appShippingStatus;
    }

    public void setAppShippingStatus(ARCH_SHIPPING_STATUS_DATASET appShippingStatus) {
        this.appShippingStatus = appShippingStatus;
    }
    
    public ARCH_APPLY_STATUS_DATASET getAppApplyStatusDataset() {
        return appApplyStatusDataset;
    }

    public void setAppApplyStatusDataset(ARCH_APPLY_STATUS_DATASET appApplyStatusDataset) {
        this.appApplyStatusDataset = appApplyStatusDataset;
    }
     
    
     public STUDENT_ASSIGNMENT_STATUS_DATASET getStudentAssignmentstatus() {
        return studentAssignmentstatus;
    }

    public void setStudentAssignmentstatus(STUDENT_ASSIGNMENT_STATUS_DATASET studentAssignmentstatus) {
        this.studentAssignmentstatus = studentAssignmentstatus;
    }
     
       public IVW_E_CIRCULAR_DATASET getIvwEcircularDataset() {
        return ivwEcircularDataset;
    }

    public void setIvwEcircularDataset(IVW_E_CIRCULAR_DATASET ivwEcircularDataset) {
        this.ivwEcircularDataset = ivwEcircularDataset;
    }
     public IVW_GROUP_MAPPING_DETAIL_DATASET getIvwGroupMappingDetailDataset() {
        return ivwGroupMappingDetailDataset;
    }

    public void setIvwGroupMappingDetailDataset(IVW_GROUP_MAPPING_DETAIL_DATASET ivwGroupMappingDetailDataset) {
        this.ivwGroupMappingDetailDataset = ivwGroupMappingDetailDataset;
    }
      public IVW_GROUP_MAPPING_MASTER_DATASET getIvwGroupMappingMasterDataset() {
        return ivwGroupMappingMasterDataset;
    }

    public void setIvwGroupMappingMasterDataset(IVW_GROUP_MAPPING_MASTER_DATASET ivwGroupMappingMasterDataset) {
        this.ivwGroupMappingMasterDataset = ivwGroupMappingMasterDataset;
    }
      public IVW_HOLIDAY_MAINTANENCE_DATASET getIvwHolidayMaintanenceDataset() {
        return ivwHolidayMaintanenceDataset;
    }

    public void setIvwHolidayMaintanenceDataset(IVW_HOLIDAY_MAINTANENCE_DATASET ivwHolidayMaintanenceDataset) {
        this.ivwHolidayMaintanenceDataset = ivwHolidayMaintanenceDataset;
    }
     public IVW_NOTIFICATION_MASTER_DATASET getIvwNotificationMasterDataset() {
        return ivwNotificationMasterDataset;
    }

    public void setIvwNotificationMasterDataset(IVW_NOTIFICATION_MASTER_DATASET ivwNotificationMasterDataset) {
        this.ivwNotificationMasterDataset = ivwNotificationMasterDataset;
    }

   public IVW_SKILL_GRADE_MASTER_DATASET getIvwSkillGradeMasterDataset() {
        return ivwSkillGradeMasterDataset;
    }

    public void setIvwSkillGradeMasterDataset(IVW_SKILL_GRADE_MASTER_DATASET ivwSkillGradeMasterDataset) {
        this.ivwSkillGradeMasterDataset = ivwSkillGradeMasterDataset;
    }
       public IVW_SKILL_MASTER_DATASET getIvwSkillMasterDataset() {
        return ivwSkillMasterDataset;
    }

    public void setIvwSkillMasterDataset(IVW_SKILL_MASTER_DATASET ivwSkillMasterDataset) {
        this.ivwSkillMasterDataset = ivwSkillMasterDataset;
    }      
    
    public IVW_SOFT_SKILL_CONFIGURATION_MASTER_DATASET getIvwSoftSkillConfigurationMasterDataSet() {
        return ivwSoftSkillConfigurationMasterDataSet;
    }

    public void setIvwSoftSkillConfigurationMasterDataSet(IVW_SOFT_SKILL_CONFIGURATION_MASTER_DATASET ivwSoftSkillConfigurationMasterDataSet) {
        this.ivwSoftSkillConfigurationMasterDataSet = ivwSoftSkillConfigurationMasterDataSet;
    } 
    public IVW_STUDENT_LEAVE_DETAILS_DATASET getIvwStudentLeaveDetailsDataset() {
        return ivwStudentLeaveDetailsDataset;
    }

    public void setIvwStudentLeaveDetailsDataset(IVW_STUDENT_LEAVE_DETAILS_DATASET ivwStudentLeaveDetailsDataset) {
        this.ivwStudentLeaveDetailsDataset = ivwStudentLeaveDetailsDataset;
    }
      public IVW_TEACHER_LEAVE_DETAILS_DATASET getIvwTeacherLeaveDetailDataset() {
        return ivwTeacherLeaveDetailDataset;
    }

    public void setIvwTeacherLeaveDetailDataset(IVW_TEACHER_LEAVE_DETAILS_DATASET ivwTeacherLeaveDetailDataset) {
        this.ivwTeacherLeaveDetailDataset = ivwTeacherLeaveDetailDataset;
    }
       public IVW_UNAUTH_RECORDS_DATASET getIvwUnauthRecords() {
        return ivwUnauthRecords;
    }

    public void setIvwUnauthRecords(IVW_UNAUTH_RECORDS_DATASET ivwUnauthRecords) {
        this.ivwUnauthRecords = ivwUnauthRecords;
    }
    
   
    public NOTIFICATION_BATCH_INDICATOR_DATASET getNotificationBatchIndicatorDataset() {
        return notificationBatchIndicatorDataset;
    }

    public void setNotificationBatchIndicatorDataset(NOTIFICATION_BATCH_INDICATOR_DATASET notificationBatchIndicatorDataset) {
        this.notificationBatchIndicatorDataset = notificationBatchIndicatorDataset;
    }  
     public RETENTION_PERIOD_DATASET getRetentionPeriodDataset() {
        return retentionPeriodDataset;
    }

    public void setRetentionPeriodDataset(RETENTION_PERIOD_DATASET retentionPeriodDataset) {
        this.retentionPeriodDataset = retentionPeriodDataset;
    }
       public CLASS_ATTENDANCE_REPORT_DATASET getClassAttendanceReportDataset() {
        return classAttendanceReportDataset;
    }

    public void setClassAttendanceReportDataset(CLASS_ATTENDANCE_REPORT_DATASET classAttendanceReportDataset) {
        this.classAttendanceReportDataset = classAttendanceReportDataset;
    }
    
    public CLASS_EXAM_RANK_DATASET getClassExamRankDataset() {
        return classExamRankDataset;
    }

    public void setClassExamRankDataset(CLASS_EXAM_RANK_DATASET classExamRankDataset) {
        this.classExamRankDataset = classExamRankDataset;
    }
     public CLASS_FEE_AMOUNT_REPORT_DATASET getClassFeeAmountReportDataset() {
        return classFeeAmountReportDataset;
    }

    public void setClassFeeAmountReportDataset(CLASS_FEE_AMOUNT_REPORT_DATASET classFeeAmountReportDataset) {
        this.classFeeAmountReportDataset = classFeeAmountReportDataset;
    }
   public CLASS_FEE_STATUS_REPORT_DATASET getClassFeeStatusReport() {
        return classFeeStatusReport;
    }

    public void setClassFeeStatusReport(CLASS_FEE_STATUS_REPORT_DATASET classFeeStatusReport) {
        this.classFeeStatusReport = classFeeStatusReport;
    }
     
    public CLASS_GRADE_REPORT_DATASET getClassGradeReportDataset() {
        return classGradeReportDataset;
    }

    public void setClassGradeReportDataset(CLASS_GRADE_REPORT_DATASET classGradeReportDataset) {
        this.classGradeReportDataset = classGradeReportDataset;
    }
      public CLASS_MARK_REPORT_DATASET getClassMarkReportDataset() {
        return classMarkReportDataset;
    }

    public void setClassMarkReportDataset(CLASS_MARK_REPORT_DATASET classMarkReportDataset) {
        this.classMarkReportDataset = classMarkReportDataset;
    }
    
      public CLASS_SKILL_ENTRY_DATASET getClassSkillEntryDataset() {
        return classSkillEntryDataset;
    }

    public void setClassSkillEntryDataset(CLASS_SKILL_ENTRY_DATASET classSkillEntryDataset) {
        this.classSkillEntryDataset = classSkillEntryDataset;
    }
    public STUDENT_SKILLS_DATASET getStudentSkillDataset() {
        return studentSkillDataset;
    }

    public void setStudentSkillDataset(STUDENT_SKILLS_DATASET studentSkillDataset) {
        this.studentSkillDataset = studentSkillDataset;
    }
    
    public IDBTransactionService getDBTransactionService() throws NamingException{ //EJB Integration change
      IDBTransactionService dbts = (IDBTransactionService)
         contxt.lookup("java:app/CohesiveDatabase/DBTransactionService!com.ibd.cohesive.db.transaction.IDBTransactionService");
        
        return dbts;
    }
    public ITransactionControlService getTransactionControlService() throws NamingException{ //EJB Integration change
    
        ITransactionControlService TransactionControl = (ITransactionControlService)
         contxt.lookup("java:app/CohesiveDatabase/TransactionControlService!com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService");
        return TransactionControl;
        
    }

    public CLASS_MARK_REPORT_DATASET getClassMark() {
        return classMark;
    }

    public void setClassMarkReportDataSet(CLASS_MARK_REPORT_DATASET classMarkReportDataSet) {
        this.classMark = classMarkReportDataSet;
    }

    public TeacherTimeTableReport_DataSet getTeacherTimeTableReport() {
        return teacherTimeTableReport;
    }

    public void setTeacherTimeTableReport(TeacherTimeTableReport_DataSet teacherTimeTableReport) {
        this.teacherTimeTableReport = teacherTimeTableReport;
    }

    public SubstituteReportParam_DataSet getSubstituteReportparam() {
        return substituteReportparam;
    }

    public void setSubstituteReportparam(SubstituteReportParam_DataSet substituteReportparam) {
        this.substituteReportparam = substituteReportparam;
    }

    public FEE_NOTIFICATION_EOD_STATUS_DATASET getFeeNotificationEodStatusDataset() {
        return feeNotificationEodStatusDataset;
    }

    public void setFeeNotificationEodStatusDataset(FEE_NOTIFICATION_EOD_STATUS_DATASET feeNotificationEodStatusDataset) {
        this.feeNotificationEodStatusDataset = feeNotificationEodStatusDataset;
    }

    public FEE_NOTIFICATION_EOD_STATUS_HISTORY_DATASET getFeeNotificationEodStatusHistoryDataset() {
        return feeNotificationEodStatusHistoryDataset;
    }

    public void setFeeNotificationEodStatusHistoryDataset(FEE_NOTIFICATION_EOD_STATUS_HISTORY_DATASET feeNotificationEodStatusHistoryDataset) {
        this.feeNotificationEodStatusHistoryDataset = feeNotificationEodStatusHistoryDataset;
    }

    public FEE_NOTIFICATION_EOD_STATUS_ERROR_DATASET getFeeNotificationEodStatusErrorDataset() {
        return feeNotificationEodStatusErrorDataset;
    }

    public void setFeeNotificationEodStatusErrorDataset(FEE_NOTIFICATION_EOD_STATUS_ERROR_DATASET feeNotificationEodStatusErrorDataset) {
        this.feeNotificationEodStatusErrorDataset = feeNotificationEodStatusErrorDataset;
    }

    public STUDENT_FEE_NOTIFICATION_EOD_STATUS_DATASET getStudentFeeNotificationEodStatus() {
        return studentFeeNotificationEodStatus;
    }

    public void setStudentFeeNotificationEodStatus(STUDENT_FEE_NOTIFICATION_EOD_STATUS_DATASET studentFeeNotificationEodStatus) {
        this.studentFeeNotificationEodStatus = studentFeeNotificationEodStatus;
    }

    public STUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY_DATASET getStudentFeeNotificationEodStatusHistory() {
        return studentFeeNotificationEodStatusHistory;
    }

    public void setStudentFeeNotificationEodStatusHistory(STUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY_DATASET studentFeeNotificationEodStatusHistory) {
        this.studentFeeNotificationEodStatusHistory = studentFeeNotificationEodStatusHistory;
    }

    public STUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR_DATASET getStudentFeeNotificationEodStatusError() {
        return studentFeeNotificationEodStatusError;
    }

    public void setStudentFeeNotificationEodStatusError(STUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR_DATASET studentFeeNotificationEodStatusError) {
        this.studentFeeNotificationEodStatusError = studentFeeNotificationEodStatusError;
    }

    public SVW_STUDENT_SOFT_SKILLS_BUSINESS_DATASET getStudentSoftSkillsBusiness() {
        return studentSoftSkillsBusiness;
    }

    public void setStudentSoftSkillsBusiness(SVW_STUDENT_SOFT_SKILLS_BUSINESS_DATASET studentSoftSkillsBusiness) {
        this.studentSoftSkillsBusiness = studentSoftSkillsBusiness;
    }

    public FeeDetailBusinessDataSet getFeeDetailBusinessDataSet() {
        return feeDetailBusinessDataSet;
    }

    public void setFeeDetailBusinessDataSet(FeeDetailBusinessDataSet feeDetailBusinessDataSet) {
        this.feeDetailBusinessDataSet = feeDetailBusinessDataSet;
    }

    public FeeSummaryBusinessDataSet getFeeSummaryBusinessDataSet() {
        return feeSummaryBusinessDataSet;
    }

    public void setFeeSummaryBusinessDataSet(FeeSummaryBusinessDataSet feeSummaryBusinessDataSet) {
        this.feeSummaryBusinessDataSet = feeSummaryBusinessDataSet;
    }

    public FeePaymentBusinessDataSet getFeePaymentBusinessDataSet() {
        return feePaymentBusinessDataSet;
    }

    public void setFeePaymentBusinessDataSet(FeePaymentBusinessDataSet feePaymentBusinessDataSet) {
        this.feePaymentBusinessDataSet = feePaymentBusinessDataSet;
    }

    public StudentDetailsDataSet getStudentDetailsDataSet() {
        return studentDetailsDataSet;
    }

    public void setStudentDetailsDataSet(StudentDetailsDataSet studentDetailsDataSet) {
        this.studentDetailsDataSet = studentDetailsDataSet;
    }

    public BusinessReportParamsDataSet getBusinessReportParams() {
        return businessReportParams;
    }

    public void setBusinessReportParams(BusinessReportParamsDataSet businessReportParams) {
        this.businessReportParams = businessReportParams;
    }
    
    
    
    
    
}
