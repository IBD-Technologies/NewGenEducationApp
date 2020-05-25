/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.web.Gateway.util;

import com.ibd.businessViews.IAssigneeSearchService;
import com.ibd.businessViews.IAssignmentSearchService;
import com.ibd.businessViews.IBatchSearchService;
import com.ibd.businessViews.IClassAttendanceService;
import com.ibd.businessViews.IClassAttendanceSummary;
import com.ibd.businessViews.IClassExamScheduleService;
import com.ibd.businessViews.IClassExamScheduleSummary;
import com.ibd.businessViews.IClassLevelConfigurationService;
import com.ibd.businessViews.IClassMarkService;
import com.ibd.businessViews.IClassMarkSummary;
import com.ibd.businessViews.IClassSoftSkillService;
import com.ibd.businessViews.IClassSoftSkillSummary;
import com.ibd.businessViews.IClassStudentMappingService;
import com.ibd.businessViews.IClassSummary;
import com.ibd.businessViews.IClassSummaryService;
import com.ibd.businessViews.IClassTimeTableService;
import com.ibd.businessViews.IClassTimeTableSummary;
import com.ibd.businessViews.IDashBoardService;
import com.ibd.businessViews.IECircularSearchService;
import com.ibd.businessViews.IECircularService;
import com.ibd.businessViews.IECircularSummary;
import com.ibd.businessViews.IFeeSearchService;
import com.ibd.businessViews.IFileUploadService;
import com.ibd.businessViews.IGeneralLevelConfigurationService;
import com.ibd.businessViews.IGroupMappingService;
import com.ibd.businessViews.IGroupMappingSummary;
import com.ibd.businessViews.IHolidayMaintanenceService;
import com.ibd.businessViews.IHolidayMaintenanceSummary;
import com.ibd.businessViews.IInstituteAssignmentService;
import com.ibd.businessViews.IInstituteAssignmentSummary;
import com.ibd.businessViews.IInstituteFeeManagementService;
import com.ibd.businessViews.IInstituteFeeManagementSummary;
import com.ibd.businessViews.IInstituteOtherActivityService;
import com.ibd.businessViews.IInstituteOtherActivitySummary;
import com.ibd.businessViews.IInstitutePaymentService;
import com.ibd.businessViews.IInstitutePaymentSummary;
import com.ibd.businessViews.IInstituteSearchService;
import com.ibd.businessViews.IInstituteSummaryService;
import com.ibd.businessViews.IInstituteUserSearchService;
import com.ibd.businessViews.IManagementDashBoardService;
import com.ibd.businessViews.INotificationSearchService;
import com.ibd.businessViews.INotificationService;
import com.ibd.businessViews.INotificationSummary;
import com.ibd.businessViews.IOTPService;
import com.ibd.businessViews.IOtherActivitySearchService;
import com.ibd.businessViews.IParentDashBoardService;
import com.ibd.businessViews.IPaymentSearchService;
import com.ibd.businessViews.IPayrollService;
import com.ibd.businessViews.IRoleSearchService;
import com.ibd.businessViews.ISelectBoxMasterService;
import com.ibd.businessViews.ISoftSkillConfigurationService;
import com.ibd.businessViews.IStudentAssignmentService;
import com.ibd.businessViews.IStudentAssignmentSummary;
import com.ibd.businessViews.IStudentAttendanceService;
import com.ibd.businessViews.IStudentAttendanceSummary;
import com.ibd.businessViews.IStudentCalenderService;
import com.ibd.businessViews.IStudentECircularService;
import com.ibd.businessViews.IStudentECircularSummary;
import com.ibd.businessViews.IStudentExamScheduleService;
import com.ibd.businessViews.IStudentExamScheduleSummary;
import com.ibd.businessViews.IStudentFeeManagementService;
import com.ibd.businessViews.IStudentFeeManagementSummary;
import com.ibd.businessViews.IStudentLeaveManagementService;
import com.ibd.businessViews.IStudentLeaveManagementSummary;
import com.ibd.businessViews.IStudentMasterService;
import com.ibd.businessViews.IStudentNotificationService;
import com.ibd.businessViews.IStudentNotificationSummary;
import com.ibd.businessViews.IStudentOtherActivityService;
import com.ibd.businessViews.IStudentOtherActivitySummary;
import com.ibd.businessViews.IStudentPaymentService;
import com.ibd.businessViews.IStudentPaymentSummary;
import com.ibd.businessViews.IStudentProfileService;
import com.ibd.businessViews.IStudentProfileSummary;
import com.ibd.businessViews.IStudentProgressCardService;
import com.ibd.businessViews.IStudentProgressCardSummary;
import com.ibd.businessViews.IStudentSearchService;
import com.ibd.businessViews.IStudentSoftSkillService;
import com.ibd.businessViews.IStudentSoftSkillSummary;
import com.ibd.businessViews.IStudentSummaryService;
import com.ibd.businessViews.IStudentTimeTableService;
import com.ibd.businessViews.IStudentTimeTableSummary;
import com.ibd.businessViews.ITeacherAttendanceService;
import com.ibd.businessViews.ITeacherAttendanceSummary;
import com.ibd.businessViews.ITeacherCalenderService;
import com.ibd.businessViews.ITeacherDashBoardService;
import com.ibd.businessViews.ITeacherECircularService;
import com.ibd.businessViews.ITeacherECircularSummary;
import com.ibd.businessViews.ITeacherLeaveManagementService;
import com.ibd.businessViews.ITeacherLeaveManagementSummary;
import com.ibd.businessViews.ITeacherMasterService;
import com.ibd.businessViews.ITeacherProfileService;
import com.ibd.businessViews.ITeacherProfileSummary;
import com.ibd.businessViews.ITeacherSearchService;
import com.ibd.businessViews.ITeacherSummaryService;
import com.ibd.businessViews.ITeacherTimeTableService;
import com.ibd.businessViews.ITeacherTimeTableSummary;
import com.ibd.businessViews.IUserProfileService;
import com.ibd.businessViews.IUserProfileSummary;
import com.ibd.businessViews.IUserRoleService;
import com.ibd.businessViews.IUserRoleSummary;
import com.ibd.businessViews.IUserSearchService;
import com.ibd.businessViews.IUserSummaryService;
import com.ibd.cohesive.util.debugger.Debug;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import com.ibd.cohesive.web.Gateway.util.WebDI.DependencyInjection;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.WRITE;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import javax.ejb.EJBException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.Context;
import javax.naming.NamingException;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author IBD Technologies
 */
public class CohesiveBeans {
    
    private Debug dbg;

    public Debug getDbg() {
        return dbg;
    }

    public void setDbg(Debug dbg) {
        this.dbg = dbg;
    }
    
    public JsonObject invokeBean(String ServiceName,JsonObject requestJson,DependencyInjection inject,WebUtility webutil,CohesiveSession session) throws NamingException
    {
         String response=null;
         String request = requestJson.toString();
         
       try{
                      switch(ServiceName)
            {   
                case "GeneralLevelConfiguration":
             {
                 IGeneralLevelConfigurationService service= inject.getGeneralLevelConfigurationService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "SoftSkillConfiguration":
             {
                 ISoftSkillConfigurationService service= inject.getSoftSkillConfigurationService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
         
                case "InstituteSearchService":
             {
                 IInstituteSearchService service= inject.getInstituteSerachService(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
         
             
             case "InstituteUserSearchService":
             {
                 IInstituteUserSearchService service= inject.getInstituteUserSerachService(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
                case "SelectBoxMasterService":
             {
              // if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT_SERVER").equals("YES"))
               //{  
                 ISelectBoxMasterService service= inject.getSelectBoxMasterService(session);
                
                 response= service.EJBprocessing(request);
               //}
               
               
                 break;
             }
             
             case "DashBoardService":
             {
              // if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT_SERVER").equals("YES"))
               //{  
                 IDashBoardService service= inject.getDashBoardService(session);
                
                 response= service.EJBprocessing(request);
               //}
               
               
                 break;
             }
             
              case "ClassAttendance":
             {
                 IClassAttendanceService service= inject.getClassAttendanceService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "ClassExamSchedule":
             {
                 IClassExamScheduleService service= inject.getClassExamScheduleService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             
             case "ClassMark":
             {
                 IClassMarkService service= inject.getClassMarkService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "ClassSoftSkill":
             {
                 IClassSoftSkillService service= inject.getClassSoftSkillService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "ClassTimeTable":
             {
                 IClassTimeTableService service= inject.getClassTimeTableService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "ClassLevelConfiguration":
             {
                 IClassLevelConfigurationService service= inject.getClassLevelConfigurationService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "ECircular":
             {
                 IECircularService service= inject.getECircularService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "GroupMapping":
             {
                 IGroupMappingService service= inject.getGroupMappingService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "HolidayMaintenance":
             {
                 IHolidayMaintanenceService service= inject.getHolidayMaintanenceService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "InstituteAssignment":
             {
                 IInstituteAssignmentService service= inject.getInstituteAssignmentService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "InstituteFeeManagement":
             {
                 IInstituteFeeManagementService service= inject.getInstituteFeeManagementService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "InstituteOtherActivity":
             {
                 IInstituteOtherActivityService service= inject.getInstituteOtherActivityService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "InstitutePayment":
             {
                 IInstitutePaymentService service= inject.getInstitutePaymentService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "Notification":
             {
                 INotificationService service= inject.getNotificationService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "StudentECircular":
             {
                 IStudentECircularService service= inject.getStudentECircularService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "TeacherECircular":
             {
                 ITeacherECircularService service= inject.getTeacherECircularService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "StudentAssignment":
             {
                 IStudentAssignmentService service= inject.getStudentAssignmentService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             
             case "StudentAttendance":
             {
                 IStudentAttendanceService service= inject.getStudentAttendanceService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             
             case "StudentCalender":
             {
                 IStudentCalenderService service= inject.getStudentCalenderService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             
             case "StudentExamSchedule":
             {
                 IStudentExamScheduleService service= inject.getStudentExamScheduleService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             
             case "StudentFeeManagement":
             {
                 IStudentFeeManagementService service= inject.getStudentFeeManagementService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             
             case "StudentLeaveManagement":
             {
                 IStudentLeaveManagementService service= inject.getStudentLeaveManagementService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             
             case "StudentNotification":
             {
                 IStudentNotificationService service= inject.getStudentNotificationService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             
             case "StudentOtherActivity":
             {
                 IStudentOtherActivityService service= inject.getStudentOtherActivityService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             
             case "StudentPayment":
             {
                 IStudentPaymentService service= inject.getStudentPaymentService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             
             case "StudentProfile":
             {
                 IStudentProfileService service= inject.getStudentProfileService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             
             case "StudentProgressCard":
             {
                 IStudentProgressCardService service= inject.getStudentProgressCardService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
              case "StudentSoftSkill":
             {
                 IStudentSoftSkillService service= inject.getStudentSoftSkillService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             
             case "StudentTimeTable":
             {
                 IStudentTimeTableService service= inject.getStudentTimeTableService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "Payroll":
             {
                 IPayrollService service= inject.getPayrollService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "ChangePwd":
             {
                 IOTPService service= inject.getOTPService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "TeacherAttendance":
             {
                 ITeacherAttendanceService service= inject.getTeacherAttendanceService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "TeacherCalender":
             {
                 ITeacherCalenderService service= inject.getTeacherCalenderService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
             case "TeacherLeaveManagement":
             {
                 ITeacherLeaveManagementService service= inject.getTeacherLeaveManagementService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
			 
             case "TeacherProfile":
             {
                 ITeacherProfileService service= inject.getTeacherProfileService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
			 
            case "TeacherTimeTable":
             {
                 ITeacherTimeTableService service= inject.getTeacherTimeTableService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
			 
		case "UserProfile":
             {
                 IUserProfileService service= inject.getUserProfileService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
			 
			 
		case "UserRole":
             {
                 IUserRoleService service= inject.getUserRoleService();
                
                 response= service.EJBprocessing(request);
                
                 break;
             }
			 
			 
	     case "ClassExamScheduleSummary":
             {
                 IClassExamScheduleSummary service= inject.getClassExamScheduleSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }		
             
             case "ClassAttendanceSummary":
             {
                 IClassAttendanceSummary service= inject.getClassAttendanceSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
				 
            case "ClassMarkSummary":
             {
                 IClassMarkSummary service= inject.getClassMarkSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             
             case "ClassSoftSkillSummary":
             {
                 IClassSoftSkillSummary service= inject.getClassSoftSkillSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             case "ClassSummary":
             {
                 IClassSummary service= inject.getClassSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             case "ClassTimeTableSummary":
             {
                 IClassTimeTableSummary service= inject.getClassTimeTableSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
		
             case "InstituteAssignmentSummary":
             {
                 IInstituteAssignmentSummary service= inject.getInstituteAssignmentSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
              case "ECircularSummary":
             {
                 IECircularSummary service= inject.getECircularSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             case "InstituteOtherActivitySummary":
             {
                 IInstituteOtherActivitySummary service= inject.getInstituteOtherActivitySummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             
             case "InstituteFeeManagementSummary":
             {
                 IInstituteFeeManagementSummary service= inject.getInstituteFeeManagementSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             
             case "InstitutePaymentSummary":
             {
                 IInstitutePaymentSummary service= inject.getInstitutePaymentSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             case "GroupMappingSummary":
             {
                 IGroupMappingSummary service= inject.getGroupMappingSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "HolidayMaintenanceSummary":
             {
                 IHolidayMaintenanceSummary service= inject.getHolidayMaintenanceSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "NotificationSummary":
             {
                 INotificationSummary service= inject.getNotificationSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "StudentAssignmentSummary":
             {
                 IStudentAssignmentSummary service= inject.getStudentAssignmentSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             case "StudentECircularSummary":
             {
                 IStudentECircularSummary service= inject.getStudentECircularSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             
             case "TeacherECircularSummary":
             {
                 ITeacherECircularSummary service= inject.getTeacherECircularSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "StudentAttendanceSummary":
             {
                 IStudentAttendanceSummary service= inject.getStudentAttendanceSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             case "StudentNotificationSummary":
             {
                 IStudentNotificationSummary service= inject.getStudentNotificationSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             case "StudentExamScheduleSummary":
             {
                 IStudentExamScheduleSummary service= inject.getStudentExamScheduleSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             
             case "StudentFeeManagementSummary":
             {
                 IStudentFeeManagementSummary service= inject.getStudentFeeManagementSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "StudentLeaveManagementSummary":
             {
                 IStudentLeaveManagementSummary service= inject.getStudentLeaveManagementSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "StudentOtherActivitySummary":
             {
                 IStudentOtherActivitySummary service= inject.getStudentOtherActivitySummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             
             case "StudentPaymentSummary":
             {
                 IStudentPaymentSummary service= inject.getStudentPaymentSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             case "StudentProfileSummary":
             {
                 IStudentProfileSummary service= inject.getStudentProfileSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             
             case "StudentProgressCardSummary":
             {
                 IStudentProgressCardSummary service= inject.getStudentProgressCardSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             case "StudentSoftSkillSummary":
             {
                 IStudentSoftSkillSummary service= inject.getStudentSoftSkillSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "StudentTimeTableSummary":
             {
                 IStudentTimeTableSummary service= inject.getStudentTimeTableSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             
             case "TeacherAttendanceSummary":
             {
                 ITeacherAttendanceSummary service= inject.getTeacherAttendanceSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             
             case "TeacherLeaveManagementSummary":
             {
                 ITeacherLeaveManagementSummary service= inject.getTeacherLeaveManagementSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             
             case "TeacherProfileSummary":
             {
                 ITeacherProfileSummary service= inject.getTeacherProfileSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             
             case "TeacherTimeTableSummary":
             {
                 ITeacherTimeTableSummary service= inject.getTeacherTimeTableSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "UserProfileSummary":
             {
                 IUserProfileSummary service= inject.getUserProfileSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             case "UserRoleSummary":
             {
                 IUserRoleSummary service= inject.getUserRoleSummary(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }	
             
             case "GroupIDSearchService":
             {
                 IAssigneeSearchService service= inject.getAssigneeSerachService(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "ClassAssignmentSearchService":
             {
                 IAssignmentSearchService service= inject.getAssignmentSerachService(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
              case "ECircularSearchService":
             {
                 IECircularSearchService service= inject.getECircularSerachService(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             case "BatchSearchService":
             {
                 IBatchSearchService service= inject.getBatchSerachService(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "FeeIDSearchService":
             {
                 IFeeSearchService service= inject.getFeeSerachService(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "NotificationSearchService":
             {
                 INotificationSearchService service= inject.getNotificationSerachService(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "OtherActivitySearchService":
             {
                 IOtherActivitySearchService service= inject.getOtherActivitySerachService(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "PaymentSearchService":
             {
                 IPaymentSearchService service= inject.getPaymentSerachService(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             case "UserRoleSearchService":
             {
                 IRoleSearchService service= inject.getRoleSerachService(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "StudentSearchService":
             {
                 IStudentSearchService service= inject.getStudentSerachService(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "TeacherNameSearchService":
             {
                 ITeacherSearchService service= inject.getTeacherSerachService(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "UserSearchService":
             {
                 IUserSearchService service= inject.getUserSerachService(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             case "ManagementDashBoard":
             {
                 IManagementDashBoardService service= inject.getManagementDashBoardService(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "ParentDashBoard":
             {
                 IParentDashBoardService service= inject.getParentDashBoardService(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             
             case "TeacherDashBoard":
             {
                 ITeacherDashBoardService service= inject.getTeacherDashBoardService(session);
                
                 response= service.EJBprocessing(request);
                
                 break;
         
             }
             /*  case "FileUploadService" :
               {
                   IFileUploadService service= inject.getFileUploadService();
                   response= service.EJBprocessing(request);
                
                 break;
             
                      }*/
                  }     
       return webutil.getJsonObject(response) ;
      }
       catch(EJBException e){
           dbg(e);
            throw e;
       }
       catch(Exception e){
           dbg(e);
            throw new RuntimeException(e);
        }
       
       
    }
    public JsonObject invokeReportBean(String ReportName,JsonObject request,WebUtility webutil,CohesiveSession session,DependencyInjection inject) throws IOException, NamingException, BSProcessingException
    {
     InputStream inputStream =null;
     HttpURLConnection connection=null;
    FileChannel fc=null;
     try{
        String reportIP=session.getCohesiveproperties().getProperty("REPORTIP");
        String reportport=session.getCohesiveproperties().getProperty("REPORTPORT");
        IFileUploadService fileUpld= inject.getFileUploadService();
        String hostName=session.getCohesiveproperties().getProperty("WEB_HOST_NAME");
        StringBuilder urlBuilder = new StringBuilder()
        //.append("http://"+reportIP+":"+reportport+"/CohesiveReportWeb/frameset");        
//      .append("https://cohesive.ibdtechnologies.com/CohesiveReportWeb/frameset");
       .append("https://"+hostName+"/CohesiveReportWeb/frameset");          
 JsonObject responseJson = null;
     JsonObject header=request.getJsonObject("header");
        String token=header.getString("token");
        String userid=header.getString("userID");
        String serviceName=header.getString("service");
        String instid=header.getString("instituteID");
        //String fileName="dummy.pdf";
    
//      String validation= fileUpld.processing(token,userid,serviceName,instid,fileName);
      
        String response=  fileUpld.EJBprocessing(request.toString());
      
             responseJson= webutil.getJsonObject(response) ; 
     
    String fileName =null;
    byte[] bytes =null;
    String uploadPath =null;
    String folderDelimiter=session.getCohesiveproperties().getProperty("FOLDER_DELIMITER");
  //if (serviceName.equals("GeneralLevelConfiguration"))
   boolean generateReport =false;
   Path filePath=null;
   String instituteID=request.getJsonObject("header").getString("instituteID");
    
     if(responseJson.getJsonObject("header").getString("status").equals("success")){
     
//http://localhost:8080/CohesiveReportWeb/frameset?__report=Student360Degree.rptdesign&teacherId=S001&instituteId=I001
//    try{
        
        switch(ReportName)
        {
            case "Student360DegreeReport":
            {
                //urlBuilder.append("?__report=Student360Degree.rptdesign&__format=pdf"); 
                String studentID =responseJson.getJsonObject("body").getJsonObject("Master").getString("studentID");
                urlBuilder.append("?__report=Student360Degree.rptdesign&__format=pdf"); 
                urlBuilder.append("&nokotser="+token);
                urlBuilder.append("&userID="+userid);
                urlBuilder.append("&loginInstitute="+instid);
                urlBuilder.append("&service="+serviceName);
                urlBuilder.append("&studentId="+studentID);
                
                                   
//              String studentID =responseJson.getJsonObject("body").getJsonObject("Master").getString("studentID");
              fileName=studentID.concat(".pdf");
              uploadPath=folderDelimiter+"reports" +folderDelimiter+instituteID +folderDelimiter+studentID;
              filePath=Paths.get(session.getCohesiveproperties().getProperty("UPLOAD_HOME_PATH") + uploadPath+folderDelimiter+fileName);
              if(Files.exists(filePath))
              {
               FileTime fileTime = Files.getLastModifiedTime(filePath);
               String dateFormatString=session.getCohesiveproperties().getProperty("DATE_FORMAT");
               DateFormat dateFormat= new SimpleDateFormat(dateFormatString);
               Date currentDate=new Date();
               String fileDateString=dateFormat.format(fileTime.toMillis());
               Date fileDate=dateFormat.parse(fileDateString);
               
               if(fileDate.compareTo(currentDate)==0){
                generateReport=false;   
               }
               else
               {
                  generateReport=true;
               }  
               
//               String =formatter.format(currentDate);
               
               
              }
              else
               {
                  generateReport=true;
               } 
              
            if(generateReport)
            {   
               //urlBuilder.append('&').append("studentId").append('=').append(studentID).append('&').append("instituteId").append('=').append(instituteID);
               dbg("urlBuilder-->"+urlBuilder.toString());
               URL requestUrl = new URL(urlBuilder.toString());
               connection = (HttpURLConnection) requestUrl.openConnection();
               connection.setRequestMethod("GET");
               connection.setDoInput(true);
               connection.connect();
               inputStream=connection.getInputStream();   
               bytes= IOUtils.toByteArray(inputStream);
            }
               
                break;
            }  
            
        case "Teacher360DegreeReport":
            {
                //urlBuilder.append("?__report=Teacher360Degree.rptdesign&__format=pdf"); 
                String teacherID =responseJson.getJsonObject("body").getJsonObject("Master").getString("teacherID");
                urlBuilder.append("?__report=Teacher360Degree.rptdesign&__format=pdf"); 
                urlBuilder.append("&nokotser="+token);
                urlBuilder.append("&userID="+userid);
                urlBuilder.append("&loginInstitute="+instid);
                urlBuilder.append("&service="+serviceName);
                urlBuilder.append("&teacherId="+teacherID);
                
                                   
//              String teacherID =responseJson.getJsonObject("body").getJsonObject("Master").getString("teacherID");
              fileName=teacherID.concat(".pdf");
              uploadPath=folderDelimiter+"reports" +folderDelimiter+instituteID +folderDelimiter+teacherID;
              filePath=Paths.get(session.getCohesiveproperties().getProperty("UPLOAD_HOME_PATH") + uploadPath+folderDelimiter+fileName);
              if(Files.exists(filePath))
              {
               FileTime fileTime = Files.getLastModifiedTime(filePath);
               String dateFormatString=session.getCohesiveproperties().getProperty("DATE_FORMAT");
               DateFormat dateFormat= new SimpleDateFormat(dateFormatString);
               Date currentDate=new Date();
               String fileDateString=dateFormat.format(fileTime.toMillis());
               Date fileDate=dateFormat.parse(fileDateString);
               
               if(fileDate.compareTo(currentDate)==0){
                generateReport=false;   
               }
               else
               {
                  generateReport=true;
               }  
               
//               String =formatter.format(currentDate);
               
               
              }
              else
               {
                  generateReport=true;
               } 
              
            if(generateReport)
            {   
               //urlBuilder.append('&').append("teacherId").append('=').append(teacherID).append('&').append("instituteId").append('=').append(instituteID);
               dbg("urlBuilder-->"+urlBuilder.toString());
               URL requestUrl = new URL(urlBuilder.toString());
               connection = (HttpURLConnection) requestUrl.openConnection();
               connection.setRequestMethod("GET");
               connection.setDoInput(true);
               connection.connect();
               inputStream=connection.getInputStream();   
               bytes= IOUtils.toByteArray(inputStream);
            }
               
                break;
            }    
            
            case "Class360DegreeReport":
            {
                //urlBuilder.append("?__report=Class360Degree.rptdesign&__format=pdf"); 
                String classs1 =responseJson.getJsonObject("body").getJsonObject("Master").getString("class");
                String instituteId =responseJson.getJsonObject("body").getJsonObject("Master").getString("instituteID");
                String classs=classs1.replace("/", "");
                urlBuilder.append("?__report=Class360Degree.rptdesign&__format=pdf"); 
                urlBuilder.append("&nokotser="+token);
                urlBuilder.append("&userID="+userid);
                urlBuilder.append("&loginInstitute="+instid);
                urlBuilder.append("&service="+serviceName);
                urlBuilder.append("&class="+classs1);
                urlBuilder.append("&instituteId="+instituteId);
                
                                   
//              String teacherID =responseJson.getJsonObject("body").getJsonObject("Master").getString("teacherID");
              fileName=classs.concat(".pdf");
              uploadPath=folderDelimiter+"reports" +folderDelimiter+instituteID +folderDelimiter+classs;
              filePath=Paths.get(session.getCohesiveproperties().getProperty("UPLOAD_HOME_PATH") + uploadPath+folderDelimiter+fileName);
              if(Files.exists(filePath))
              {
               FileTime fileTime = Files.getLastModifiedTime(filePath);
               String dateFormatString=session.getCohesiveproperties().getProperty("DATE_FORMAT");
               DateFormat dateFormat= new SimpleDateFormat(dateFormatString);
               Date currentDate=new Date();
               String fileDateString=dateFormat.format(fileTime.toMillis());
               Date fileDate=dateFormat.parse(fileDateString);
               
               if(fileDate.compareTo(currentDate)==0){
                generateReport=false;   
               }
               else
               {
                  generateReport=true;
               }  
               
//               String =formatter.format(currentDate);
               
               
              }
              else
               {
                  generateReport=true;
               } 
              
            if(generateReport)
            {   
               //urlBuilder.append('&').append("teacherId").append('=').append(teacherID).append('&').append("instituteId").append('=').append(instituteID);
               dbg("urlBuilder-->"+urlBuilder.toString());
               URL requestUrl = new URL(urlBuilder.toString());
               connection = (HttpURLConnection) requestUrl.openConnection();
               connection.setRequestMethod("GET");
               connection.setDoInput(true);
               connection.connect();
               inputStream=connection.getInputStream();   
               bytes= IOUtils.toByteArray(inputStream);
            }
               
                break;
            }    
            
            case "GradeComparison":
            {
                //urlBuilder.append("?__report=Class360Degree.rptdesign&__format=pdf"); 
                String classs1 =responseJson.getJsonObject("body").getJsonObject("Master").getString("class");//class refers to standard
                String insID =responseJson.getJsonObject("body").getJsonObject("Master").getString("instituteID");
                
                String classs=classs1.replace("/", "");
                urlBuilder.append("?__report=GradeComparison.rptdesign&__format=pdf"); 
                urlBuilder.append("&nokotser="+token);
                urlBuilder.append("&userID="+userid);
                urlBuilder.append("&loginInstitute="+instid);
                urlBuilder.append("&service="+serviceName);
                urlBuilder.append("&standard="+classs1);
                urlBuilder.append("&instituteId="+insID);
                                   
//              String teacherID =responseJson.getJsonObject("body").getJsonObject("Master").getString("teacherID");
              fileName=classs.concat(".pdf");
              uploadPath=folderDelimiter+"reports" +folderDelimiter+instituteID +folderDelimiter+classs;
              filePath=Paths.get(session.getCohesiveproperties().getProperty("UPLOAD_HOME_PATH") + uploadPath+folderDelimiter+fileName);
              if(Files.exists(filePath))
              {
               FileTime fileTime = Files.getLastModifiedTime(filePath);
               String dateFormatString=session.getCohesiveproperties().getProperty("DATE_FORMAT");
               DateFormat dateFormat= new SimpleDateFormat(dateFormatString);
               Date currentDate=new Date();
               String fileDateString=dateFormat.format(fileTime.toMillis());
               Date fileDate=dateFormat.parse(fileDateString);
               
               if(fileDate.compareTo(currentDate)==0){
                generateReport=false;   
               }
               else
               {
                  generateReport=true;
               }  
               
//               String =formatter.format(currentDate);
               
               
              }
              else
               {
                  generateReport=true;
               } 
              
            if(generateReport)
            {   
               //urlBuilder.append('&').append("teacherId").append('=').append(teacherID).append('&').append("instituteId").append('=').append(instituteID);
               dbg("urlBuilder-->"+urlBuilder.toString());
               URL requestUrl = new URL(urlBuilder.toString());
               connection = (HttpURLConnection) requestUrl.openConnection();
               connection.setRequestMethod("GET");
               connection.setDoInput(true);
               connection.connect();
               inputStream=connection.getInputStream();   
               bytes= IOUtils.toByteArray(inputStream);
            }
               
                break;
            }   
            
        case "SubstituteReport":
            {
                //urlBuilder.append("?__report=Teacher360Degree.rptdesign&__format=pdf"); 
                String teacherID =responseJson.getJsonObject("body").getJsonObject("Master").getString("teacherID");
                String leaveDate =responseJson.getJsonObject("body").getJsonObject("Master").getString("date");
                urlBuilder.append("?__report=SubstituteReport.rptdesign&__format=pdf"); 
                urlBuilder.append("&nokotser="+token);
                urlBuilder.append("&userID="+userid);
                urlBuilder.append("&loginInstitute="+instid);
                urlBuilder.append("&service="+serviceName);
                urlBuilder.append("&teacherId="+teacherID);
                urlBuilder.append("&leaveDate="+leaveDate);
                
                                   
//              String teacherID =responseJson.getJsonObject("body").getJsonObject("Master").getString("teacherID");
              fileName=teacherID.concat(".pdf");
              uploadPath=folderDelimiter+"reports" +folderDelimiter+instituteID +folderDelimiter+teacherID;
              filePath=Paths.get(session.getCohesiveproperties().getProperty("UPLOAD_HOME_PATH") + uploadPath+folderDelimiter+fileName);
              if(Files.exists(filePath))
              {
               FileTime fileTime = Files.getLastModifiedTime(filePath);
               String dateFormatString=session.getCohesiveproperties().getProperty("DATE_FORMAT");
               DateFormat dateFormat= new SimpleDateFormat(dateFormatString);
               Date currentDate=new Date();
               String fileDateString=dateFormat.format(fileTime.toMillis());
               Date fileDate=dateFormat.parse(fileDateString);
               
               if(fileDate.compareTo(currentDate)==0){
                generateReport=false;   
               }
               else
               {
                  generateReport=true;
               }  
               
//               String =formatter.format(currentDate);
               
               
              }
              else
               {
                  generateReport=true;
               } 
              
            if(generateReport)
            {   
               //urlBuilder.append('&').append("teacherId").append('=').append(teacherID).append('&').append("instituteId").append('=').append(instituteID);
               dbg("urlBuilder-->"+urlBuilder.toString());
               URL requestUrl = new URL(urlBuilder.toString());
               connection = (HttpURLConnection) requestUrl.openConnection();
               connection.setRequestMethod("GET");
               connection.setDoInput(true);
               connection.connect();
               inputStream=connection.getInputStream();   
               bytes= IOUtils.toByteArray(inputStream);
            }
               
                break;
            }        
            
             case "FeeBusinessReport":
            {
                //urlBuilder.append("?__report=Teacher360Degree.rptdesign&__format=pdf"); 
                String studentID =responseJson.getJsonObject("body").getJsonObject("Master").getString("studentID");
                String class1 =responseJson.getJsonObject("body").getJsonObject("Master").getString("class");
                
                if(class1.equals("Select option")){
                    class1="";
                }
                
                String feeID =responseJson.getJsonObject("body").getJsonObject("Master").getString("feeID");
                urlBuilder.append("?__report=FeeBusinessReport.rptdesign&__format=pdf"); 
                urlBuilder.append("&nokotser="+token);
                urlBuilder.append("&userID="+userid);
                urlBuilder.append("&loginInstitute="+instid);
                urlBuilder.append("&service="+serviceName);
                urlBuilder.append("&studentID="+studentID);
                urlBuilder.append("&class="+class1);
                urlBuilder.append("&feeID="+feeID);
                                   
//              String teacherID =responseJson.getJsonObject("body").getJsonObject("Master").getString("teacherID");
              fileName="FeeBusinessReport".concat(".pdf");
              uploadPath=folderDelimiter+"reports" +folderDelimiter+instituteID +folderDelimiter+"FeeBusinessReport";
              filePath=Paths.get(session.getCohesiveproperties().getProperty("UPLOAD_HOME_PATH") + uploadPath+folderDelimiter+fileName);
              if(Files.exists(filePath))
              {
               FileTime fileTime = Files.getLastModifiedTime(filePath);
               String dateFormatString=session.getCohesiveproperties().getProperty("DATE_FORMAT");
               DateFormat dateFormat= new SimpleDateFormat(dateFormatString);
               Date currentDate=new Date();
               String fileDateString=dateFormat.format(fileTime.toMillis());
               Date fileDate=dateFormat.parse(fileDateString);
               
               if(fileDate.compareTo(currentDate)==0){
                generateReport=false;   
               }
               else
               {
                  generateReport=true;
               }  
               
//               String =formatter.format(currentDate);
               
               
              }
              else
               {
                  generateReport=true;
               } 
              
            if(generateReport)
            {   
               //urlBuilder.append('&').append("teacherId").append('=').append(teacherID).append('&').append("instituteId").append('=').append(instituteID);
               dbg("urlBuilder-->"+urlBuilder.toString());
               URL requestUrl = new URL(urlBuilder.toString());
               connection = (HttpURLConnection) requestUrl.openConnection();
               connection.setRequestMethod("GET");
               connection.setDoInput(true);
               connection.connect();
               inputStream=connection.getInputStream();   
               bytes= IOUtils.toByteArray(inputStream);
            }
               
                break;
            }
            
            
            case "PaymentBusinessReport":
            {
                //urlBuilder.append("?__report=Teacher360Degree.rptdesign&__format=pdf"); 
                String studentID =responseJson.getJsonObject("body").getJsonObject("Master").getString("studentID");
                String class1 =responseJson.getJsonObject("body").getJsonObject("Master").getString("class");
                if(class1.equals("Select option")){
                    class1="";
                }
                String feeID =responseJson.getJsonObject("body").getJsonObject("Master").getString("feeID");
                String fromDate =responseJson.getJsonObject("body").getJsonObject("Master").getString("fromDate");
                String toDate =responseJson.getJsonObject("body").getJsonObject("Master").getString("toDate");
                urlBuilder.append("?__report=PaymentBusinessReport.rptdesign&__format=pdf"); 
                urlBuilder.append("&nokotser="+token);
                urlBuilder.append("&userID="+userid);
                urlBuilder.append("&loginInstitute="+instid);
                urlBuilder.append("&service="+serviceName);
                urlBuilder.append("&studentID="+studentID);
                urlBuilder.append("&class="+class1);
                urlBuilder.append("&feeID="+feeID);
                urlBuilder.append("&fromDate="+fromDate);
                urlBuilder.append("&toDate="+toDate);
                                   
//              String teacherID =responseJson.getJsonObject("body").getJsonObject("Master").getString("teacherID");
              fileName="PaymentBusinessReport".concat(".pdf");
              uploadPath=folderDelimiter+"reports" +folderDelimiter+instituteID +folderDelimiter+"PaymentBusinessReport";
              filePath=Paths.get(session.getCohesiveproperties().getProperty("UPLOAD_HOME_PATH") + uploadPath+folderDelimiter+fileName);
              if(Files.exists(filePath))
              {
               FileTime fileTime = Files.getLastModifiedTime(filePath);
               String dateFormatString=session.getCohesiveproperties().getProperty("DATE_FORMAT");
               DateFormat dateFormat= new SimpleDateFormat(dateFormatString);
               Date currentDate=new Date();
               String fileDateString=dateFormat.format(fileTime.toMillis());
               Date fileDate=dateFormat.parse(fileDateString);
               
               if(fileDate.compareTo(currentDate)==0){
                generateReport=false;   
               }
               else
               {
                  generateReport=true;
               }  
               
//               String =formatter.format(currentDate);
               
               
              }
              else
               {
                  generateReport=true;
               } 
              
            if(generateReport)
            {   
               //urlBuilder.append('&').append("teacherId").append('=').append(teacherID).append('&').append("instituteId").append('=').append(instituteID);
               dbg("urlBuilder-->"+urlBuilder.toString());
               URL requestUrl = new URL(urlBuilder.toString());
               connection = (HttpURLConnection) requestUrl.openConnection();
               connection.setRequestMethod("GET");
               connection.setDoInput(true);
               connection.connect();
               inputStream=connection.getInputStream();   
               bytes= IOUtils.toByteArray(inputStream);
            }
               
                break;
            }
            case "NotificationBusinessReport":
            {
                //urlBuilder.append("?__report=Teacher360Degree.rptdesign&__format=pdf"); 
                String studentID =responseJson.getJsonObject("body").getJsonObject("Master").getString("studentID");
                String class1 =responseJson.getJsonObject("body").getJsonObject("Master").getString("class");
                if(class1.equals("Select option")){
                    class1="";
                }
                
                String notificationType =responseJson.getJsonObject("body").getJsonObject("Master").getString("notificationType");
                if(notificationType.equals("Select option")){
                    notificationType="";
                }
                
                if(notificationType.contains(" ")){
                    notificationType=notificationType.replace(" ", "SSPPAA");
                }
                
                
                String fromDate =responseJson.getJsonObject("body").getJsonObject("Master").getString("fromDate");
                String toDate =responseJson.getJsonObject("body").getJsonObject("Master").getString("toDate");
                urlBuilder.append("?__report=NotificationBusinessReport.rptdesign&__format=pdf"); 
                urlBuilder.append("&nokotser="+token);
                urlBuilder.append("&userID="+userid);
                urlBuilder.append("&loginInstitute="+instid);
                urlBuilder.append("&service="+serviceName);
                urlBuilder.append("&studentID="+studentID);
                urlBuilder.append("&class="+class1);
                urlBuilder.append("&notificationType="+notificationType);
                urlBuilder.append("&fromDate="+fromDate);
                urlBuilder.append("&toDate="+toDate);
                                   
//              String teacherID =responseJson.getJsonObject("body").getJsonObject("Master").getString("teacherID");
              fileName="NotificationBusinessReport".concat(".pdf");
              uploadPath=folderDelimiter+"reports" +folderDelimiter+instituteID +folderDelimiter+"NotificationBusinessReport";
              filePath=Paths.get(session.getCohesiveproperties().getProperty("UPLOAD_HOME_PATH") + uploadPath+folderDelimiter+fileName);
              if(Files.exists(filePath))
              {
               FileTime fileTime = Files.getLastModifiedTime(filePath);
               String dateFormatString=session.getCohesiveproperties().getProperty("DATE_FORMAT");
               DateFormat dateFormat= new SimpleDateFormat(dateFormatString);
               Date currentDate=new Date();
               String fileDateString=dateFormat.format(fileTime.toMillis());
               Date fileDate=dateFormat.parse(fileDateString);
               
               if(fileDate.compareTo(currentDate)==0){
                generateReport=false;   
               }
               else
               {
                  generateReport=true;
               }  
               
//               String =formatter.format(currentDate);
               
               
              }
              else
               {
                  generateReport=true;
               } 
              
            if(generateReport)
            {   
               //urlBuilder.append('&').append("teacherId").append('=').append(teacherID).append('&').append("instituteId").append('=').append(instituteID);
               dbg("urlBuilder-->"+urlBuilder.toString());
               URL requestUrl = new URL(urlBuilder.toString());
               connection = (HttpURLConnection) requestUrl.openConnection();
               connection.setRequestMethod("GET");
               connection.setDoInput(true);
               connection.connect();
               inputStream=connection.getInputStream();   
               bytes= IOUtils.toByteArray(inputStream);
            }
               
                break;
            }
            case "StudentDetailsReport":
            {
                //urlBuilder.append("?__report=Teacher360Degree.rptdesign&__format=pdf"); 
                String studentStatus =responseJson.getJsonObject("body").getJsonObject("Master").getString("studentStatus");
                String class1 =responseJson.getJsonObject("body").getJsonObject("Master").getString("class");
                if(class1.equals("Select option")){
                    class1="";
                }
                String fromDate =responseJson.getJsonObject("body").getJsonObject("Master").getString("fromDate");
                String toDate =responseJson.getJsonObject("body").getJsonObject("Master").getString("toDate");
                urlBuilder.append("?__report=StudentDetailsReport.rptdesign&__format=pdf"); 
                urlBuilder.append("&nokotser="+token);
                urlBuilder.append("&userID="+userid);
                urlBuilder.append("&loginInstitute="+instid);
                urlBuilder.append("&service="+serviceName);
                urlBuilder.append("&studentStatus="+studentStatus);
                urlBuilder.append("&class="+class1);
                urlBuilder.append("&fromDate="+fromDate);
                urlBuilder.append("&toDate="+toDate);
                                   
//              String teacherID =responseJson.getJsonObject("body").getJsonObject("Master").getString("teacherID");
              fileName="StudentDetailsReport".concat(".pdf");
              uploadPath=folderDelimiter+"reports" +folderDelimiter+instituteID +folderDelimiter+"StudentDetailsReport";
              filePath=Paths.get(session.getCohesiveproperties().getProperty("UPLOAD_HOME_PATH") + uploadPath+folderDelimiter+fileName);
              if(Files.exists(filePath))
              {
               FileTime fileTime = Files.getLastModifiedTime(filePath);
               String dateFormatString=session.getCohesiveproperties().getProperty("DATE_FORMAT");
               DateFormat dateFormat= new SimpleDateFormat(dateFormatString);
               Date currentDate=new Date();
               String fileDateString=dateFormat.format(fileTime.toMillis());
               Date fileDate=dateFormat.parse(fileDateString);
               
               if(fileDate.compareTo(currentDate)==0){
                generateReport=false;   
               }
               else
               {
                  generateReport=true;
               }  
               
//               String =formatter.format(currentDate);
               
               
              }
              else
               {
                  generateReport=true;
               } 
              
            if(generateReport)
            {   
               //urlBuilder.append('&').append("teacherId").append('=').append(teacherID).append('&').append("instituteId").append('=').append(instituteID);
               dbg("urlBuilder-->"+urlBuilder.toString());
               URL requestUrl = new URL(urlBuilder.toString());
               connection = (HttpURLConnection) requestUrl.openConnection();
               connection.setRequestMethod("GET");
               connection.setDoInput(true);
               connection.connect();
               inputStream=connection.getInputStream();   
               bytes= IOUtils.toByteArray(inputStream);
            }
               
                break;
            }
         case "TableReport":
            {
                
               
                
                //urlBuilder.append("?__report=Student360Degree.rptdesign&__format=html"); 
                                 
               String tableName =responseJson.getJsonObject("body").getJsonObject("Master").getString("tableName");
               String queryParam =responseJson.getJsonObject("body").getJsonObject("Master").getString("queryParam");
                
               urlBuilder.append("?__report="+tableName+".rptdesign&__format=pdf"); 
                
               fileName=tableName.concat(".pdf");
              uploadPath="reports" +folderDelimiter+instituteID +folderDelimiter+tableName;
              filePath=Paths.get(session.getCohesiveproperties().getProperty("UPLOAD_HOME_PATH") + uploadPath+folderDelimiter+fileName);
              urlBuilder.append(queryParam);
               //urlBuilder.append('&').append("studentId").append('=').append(studentID).append('&').append("instituteId").append('=').append(instituteID);
               dbg("urlBuilder-->"+urlBuilder.toString());
               URL requestUrl = new URL(urlBuilder.toString());
               connection = (HttpURLConnection) requestUrl.openConnection();
               connection.setRequestMethod("GET");
               connection.setDoInput(true);
               connection.connect();
               inputStream=connection.getInputStream();   
               bytes= IOUtils.toByteArray(inputStream);
            
               
                break;
            }   
        
        }   
        
    
     
   if(bytes!=null && bytes.length >0)
   {   
  
   
  
  
  dbg("uploadPath -->"+uploadPath);
  Path dirPath = Paths.get(session.getCohesiveproperties().getProperty("UPLOAD_HOME_PATH") + uploadPath);
              
  if (!Files.exists(dirPath))
     Files.createDirectories(dirPath);
   filePath=Paths.get(session.getCohesiveproperties().getProperty("UPLOAD_HOME_PATH") + uploadPath+folderDelimiter+fileName);
  if (Files.exists(filePath))
    Files.delete(filePath);
  Files.createFile(filePath);
             
  
  //using PosixFilePermission to set file permissions 755
       Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
       //add owners permission
       perms.add(PosixFilePermission.OWNER_READ);
       perms.add(PosixFilePermission.OWNER_WRITE);
       //perms.add(PosixFilePermission.OWNER_EXECUTE);
       //add group permissions
       //perms.add(PosixFilePermission.GROUP_READ);
       //perms.add(PosixFilePermission.GROUP_EXECUTE);
       //add others permissions
       //perms.add(PosixFilePermission.OTHERS_READ);
       //perms.add(PosixFilePermission.OTHERS_EXECUTE);
       
       Files.setPosixFilePermissions(filePath, perms);
  
  fc = FileChannel.open(filePath, WRITE);
  
  fc.write(ByteBuffer.wrap(bytes));

   } 
      
   
   String reportPath=uploadPath+folderDelimiter+fileName;
   dbg("reportPath-->"+reportPath);
    responseJson=Json.createObjectBuilder().add("header", responseJson.getJsonObject("header"))
                                           .add("body", Json.createObjectBuilder()
                                           .add("Master", responseJson.getJsonObject("body").getJsonObject("Master"))
                                           .add("ReportPath", reportPath))
                                           .add("audit", "{}")
                                           .add("error", "{}").build();
                                          
     
     } 
     
     return responseJson;
     }
       catch(EJBException e){
           dbg(e);
            throw e;
        }
        
catch (Exception e) {

    dbg(e);
    throw new RuntimeException(e);
}
        
finally
{
 if(inputStream!=null)
   inputStream.close();
 if (fc !=null &&fc.isOpen())
      fc.close();
 if(connection!=null)
 connection.disconnect();
}        
//     return null;
    }
    
   
    
    
    public JsonObject invokeBean(String ServiceName,JsonObject request) throws NamingException
    {
       JsonObject response=null;
       try{
        final Hashtable<String,String> props = new Hashtable<String,String>();
        // setup the ejb: namespace URL factory
            //props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            props.put("java.naming.factory.initial", "org.jboss.as.naming.InitialContextFactory");
            // create the InitialContext
            final Context context = new javax.naming.InitialContext(props);
            final String appName="CohesiveBackend";
            final String moduleName = "CohesiveBusiness-ejb";
            final String distinctName="";
            
            switch(ServiceName)
            {   
                case "StudentMasterService" :
                 {       
            // Lookup the Greeter bean using the ejb: namespace syntax which is explained here https://docs.jboss.org/author/display/AS71/EJB+invocations+from+a+remote+client+using+JNDI
                //final String beanName=StudentMasterService.class.getSimpleName();
                //final String viewClassName=IStudentMasterService.class.getSimpleName();
                //IStudentMasterService service=(IStudentMasterService) context.lookup("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);
                 IStudentMasterService service=(IStudentMasterService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/StudentMasterService!com.ibd.businessViews.IStudentMasterService");
                  // invoke on the bean
                  response= service.EJBprocessing(request);
                  break;
                 }   
             case "StudentProfileService" :
                 {       
            // Lookup the Greeter bean using the ejb: namespace syntax which is explained here https://docs.jboss.org/author/display/AS71/EJB+invocations+from+a+remote+client+using+JNDI
                //final String beanName=StudentProfileService.class.getSimpleName();
                //final String viewClassName=IStudentProfileService.class.getSimpleName();
                //IStudentProfileService service=(IStudentProfileService) context.lookup("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);
               IStudentProfileService service=(IStudentProfileService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/StudentProfileService!com.ibd.businessViews.IStudentProfileService");     
              // invoke on the bean
                   response= service.EJBprocessing(request);
                 break;
                 }
             case "TestService":
             { 
                JsonObjectBuilder jsonResponse=Json.createObjectBuilder();
                jsonResponse=Json.createObjectBuilder().add("Name","Rajkumar");
                response =jsonResponse.build();
                break;
              }
             case "UserProfileService":
             {
                 IUserProfileService service=(IUserProfileService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/UserProfileService!com.ibd.businessViews.IUserProfileService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "UserRoleService":
             {
                 IUserRoleService service=(IUserRoleService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/UserRoleService!com.ibd.businessViews.IUserRoleService");
                response= service.EJBprocessing(request);
                 break;
             }
              case "TeacherProfileService":
             {
                 ITeacherProfileService service=(ITeacherProfileService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/TeacherProfileService!com.ibd.businessViews.ITeacherProfileService");
                response= service.EJBprocessing(request);
                 break;
             }
              case "StudentAssignmentService":
             {
                 IStudentAssignmentService service=(IStudentAssignmentService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/StudentAssignmentService!com.ibd.businessViews.IStudentAssignmentService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "StudentAttendanceService":
             {
                 IStudentAttendanceService service=(IStudentAttendanceService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/StudentAttendanceService!com.ibd.businessViews.IStudentAttendanceService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "StudentFeeManagementService":
             {
                 IStudentFeeManagementService service=(IStudentFeeManagementService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/StudentFeeManagementService!com.ibd.businessViews.IStudentFeeManagementService");
                response= service.EJBprocessing(request);
                 break;
             }
              case "StudentOtherActivityService":
             {
                 IStudentOtherActivityService service=(IStudentOtherActivityService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/StudentOtherActivityService!com.ibd.businessViews.IStudentOtherActivityService");
                response= service.EJBprocessing(request);
                 break;
             }
              case "StudentExamScheduleService":
             {
                 IStudentExamScheduleService service=(IStudentExamScheduleService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/StudentExamScheduleService!com.ibd.businessViews.IStudentExamScheduleService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "StudentLeaveManagementService":
             {
                 IStudentLeaveManagementService service=(IStudentLeaveManagementService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/StudentLeaveManagementService!com.ibd.businessViews.IStudentLeaveManagementService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "StudentProgressCardService":
             {
                 IStudentProgressCardService service=(IStudentProgressCardService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/StudentProgressCardService!com.ibd.businessViews.IStudentProgressCardService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "StudentPaymentService":
             {
                 IStudentPaymentService service=(IStudentPaymentService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/StudentPaymentService!com.ibd.businessViews.IStudentPaymentService");
                response= service.EJBprocessing(request);
                 break;
             }
              case "StudentTimeTableService":
             {
                 IStudentTimeTableService service=(IStudentTimeTableService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/StudentTimeTableService!com.ibd.businessViews.IStudentTimeTableService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "StudentCalenderService":
             {
                 IStudentCalenderService service=(IStudentCalenderService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/StudentCalenderService!com.ibd.businessViews.IStudentCalenderService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "PayrollService":
             {
                 IPayrollService service=(IPayrollService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/PayrollService!com.ibd.businessViews.IPayrollService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "TeacherAttendanceService":
             {
                 ITeacherAttendanceService service=(ITeacherAttendanceService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/TeacherAttendanceService!com.ibd.businessViews.ITeacherAttendanceService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "TeacherCalenderService":
             {
                 ITeacherCalenderService service=(ITeacherCalenderService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/TeacherCalenderService!com.ibd.businessViews.ITeacherCalenderService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "TeacherLeaveManagementService":
             {
                 ITeacherLeaveManagementService service=(ITeacherLeaveManagementService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/TeacherLeaveManagementService!com.ibd.businessViews.ITeacherLeaveManagementService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "TeacherTimeTableService":
             {
                 ITeacherTimeTableService service=(ITeacherTimeTableService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/TeacherTimeTableService!com.ibd.businessViews.ITeacherTimeTableService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "TeacherMasterService":
             {
                 ITeacherMasterService service=(ITeacherMasterService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/TeacherMasterService!com.ibd.businessViews.ITeacherMasterService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "ClassAttendanceService":
             {
                 IClassAttendanceService service=(IClassAttendanceService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/ClassAttendanceService!com.ibd.businessViews.IClassAttendanceService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "ClassExamScheduleService":
             {
                 IClassExamScheduleService service=(IClassExamScheduleService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/ClassExamScheduleService!com.ibd.businessViews.IClassExamScheduleService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "ClassStudentMappingService":
             {
                 IClassStudentMappingService service=(IClassStudentMappingService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/ClassStudentMappingService!com.ibd.businessViews.IClassStudentMappingService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "ClassMarkService":
             {
                 IClassMarkService service=(IClassMarkService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/ClassMarkService!com.ibd.businessViews.IClassMarkService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "ClassSoftSkillService":
             {
                 IClassSoftSkillService service=(IClassSoftSkillService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/ClassSoftSkillService!com.ibd.businessViews.IClassSoftSkillService");
                response= service.EJBprocessing(request);
                 break;
             }
              case "ClassTimeTableService":
             {
                 IClassTimeTableService service=(IClassTimeTableService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/ClassTimeTableService!com.ibd.businessViews.IClassTimeTableService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "GeneralLevelConfigurationService":
             {
                 IGeneralLevelConfigurationService service=(IGeneralLevelConfigurationService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/GeneralLevelConfigurationService!com.ibd.businessViews.IGeneralLevelConfigurationService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "ClassLevelConfigurationService":
             {
                 IClassLevelConfigurationService service=(IClassLevelConfigurationService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/ClassLevelConfigurationService!com.ibd.businessViews.IClassLevelConfigurationService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "GroupMappingService":
             {
                 IGroupMappingService service=(IGroupMappingService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/GroupMappingService!com.ibd.businessViews.IGroupMappingService");
                response= service.EJBprocessing(request);
                 break;
             }
              case "HolidayMaintanenceService":
             {
                 IHolidayMaintanenceService service=(IHolidayMaintanenceService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/HolidayMaintanenceService!com.ibd.businessViews.IHolidayMaintanenceService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "InstituteAssignmentService":
             {
                 IInstituteAssignmentService service=(IInstituteAssignmentService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/InstituteAssignmentService!com.ibd.businessViews.IInstituteAssignmentService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "InstituteFeeManagementService":
             {
                 IInstituteFeeManagementService service=(IInstituteFeeManagementService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/InstituteFeeManagementService!com.ibd.businessViews.IInstituteFeeManagementService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "InstituteOtherActivityService":
             {
                 IInstituteOtherActivityService service=(IInstituteOtherActivityService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/InstituteOtherActivityService!com.ibd.businessViews.IInstituteOtherActivityService");
                response= service.EJBprocessing(request);
                 break;
             }
              case "InstitutePaymentService":
             {
                 IInstitutePaymentService service=(IInstitutePaymentService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/InstitutePaymentService!com.ibd.businessViews.IInstitutePaymentService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "NotificationService":
             {
                 INotificationService service=(INotificationService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/NotificationService!com.ibd.businessViews.INotificationService");
                response= service.EJBprocessing(request);
                 break;
             }
//             case "StudentProfileSummary":
//             {
//                 IStudentProfileSummary service=(IStudentProfileSummary) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/StudentProfileSummary!com.ibd.businessViews.IStudentProfileSummary");
//                response= service.EJBprocessing(request);
//                 break;
//             }
             case "StudentAssignmentSummary":
             {
                 IStudentSummaryService service=(IStudentSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/StudentSummaryService!com.ibd.businessViews.IStudentSummaryService");
                response= service.EJBprocessing(request,"StudentAssignmentSummary");
                 break;
             }
             case "StudentProfileSummary":
             {
                 IStudentSummaryService service=(IStudentSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/StudentSummaryService!com.ibd.businessViews.IStudentSummaryService");
                response= service.EJBprocessing(request,"StudentProfileSummary");
                 break;
             }
             case "StudentTimeTableSummary":
             {
                  IStudentSummaryService service=(IStudentSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/StudentSummaryService!com.ibd.businessViews.IStudentSummaryService");
                response= service.EJBprocessing(request,"StudentTimeTableSummary");
                 break;
             }
             case "StudentExamScheduleSummary":
             {
                  IStudentSummaryService service=(IStudentSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/StudentSummaryService!com.ibd.businessViews.IStudentSummaryService");
                response= service.EJBprocessing(request,"StudentExamScheduleSummary");
                 break;
             }
             case "StudentAttendanceSummary":
             {
                  IStudentSummaryService service=(IStudentSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/StudentSummaryService!com.ibd.businessViews.IStudentSummaryService");
                response= service.EJBprocessing(request,"StudentAttendanceSummary");
                 break;
             }
             case "StudentFeeManagementSummary":
             {
                  IStudentSummaryService service=(IStudentSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/StudentSummaryService!com.ibd.businessViews.IStudentSummaryService");
                response= service.EJBprocessing(request,"StudentFeeManagementSummary");
                 break;
             }
             case "StudentLeaveManagementSummary":
             {
                  IStudentSummaryService service=(IStudentSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/StudentSummaryService!com.ibd.businessViews.IStudentSummaryService");
                response= service.EJBprocessing(request,"StudentLeaveManagementSummary");
                 break;
             }
             case "StudentOtherActivitySummary":
             {
                  IStudentSummaryService service=(IStudentSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/StudentSummaryService!com.ibd.businessViews.IStudentSummaryService");
                response= service.EJBprocessing(request,"StudentOtherActivitySummary");
                 break;
             }
             case "StudentPaymentSummary":
             {
                  IStudentSummaryService service=(IStudentSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/StudentSummaryService!com.ibd.businessViews.IStudentSummaryService");
                response= service.EJBprocessing(request,"StudentPaymentSummary");
                 break;
             }
             case "StudentProgressCardSummary":
             {
                  IStudentSummaryService service=(IStudentSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/StudentSummaryService!com.ibd.businessViews.IStudentSummaryService");
                response= service.EJBprocessing(request,"StudentProgressCardSummary");
                 break;
             }
             case "TeacherAttendanceSummary":
             {
                  ITeacherSummaryService service=(ITeacherSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/TeacherSummaryService!com.ibd.businessViews.ITeacherSummaryService");
                response= service.EJBprocessing(request,"TeacherAttendanceSummary");
                 break;
             }
             case "TeacherLeaveManagementSummary":
             {
                  ITeacherSummaryService service=(ITeacherSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/TeacherSummaryService!com.ibd.businessViews.ITeacherSummaryService");
                response= service.EJBprocessing(request,"TeacherLeaveManagementSummary");
                 break;
             }
             case "TeacherProfileSummary":
             {
                  ITeacherSummaryService service=(ITeacherSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/TeacherSummaryService!com.ibd.businessViews.ITeacherSummaryService");
                response= service.EJBprocessing(request,"TeacherProfileSummary");
                 break;
             }
             case "TeacherTimeTableSummary":
             {
                  ITeacherSummaryService service=(ITeacherSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/TeacherSummaryService!com.ibd.businessViews.ITeacherSummaryService");
                response= service.EJBprocessing(request,"TeacherTimeTableSummary");
                 break;
             }
             case "ClassExamScheduleSummary":
             {
                  IClassSummaryService service=(IClassSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/ClassSummaryService!com.ibd.businessViews.IClassSummaryService");
                 response= service.EJBprocessing(request,"ClassExamScheduleSummary");
                 break;
             }
             case "ClassMarkSummary":
             {
                  IClassSummaryService service=(IClassSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/ClassSummaryService!com.ibd.businessViews.IClassSummaryService");
                 response= service.EJBprocessing(request,"ClassMarkSummary");
                 break;
             }
             case "ClassTimeTableSummary":
             {
                  IClassSummaryService service=(IClassSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/ClassSummaryService!com.ibd.businessViews.IClassSummaryService");
                 response= service.EJBprocessing(request,"ClassTimeTableSummary");
                 break;
             }
             case "InstituteAssignmentSummary":
             {
                  IInstituteSummaryService service=(IInstituteSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/InstituteSummaryService!com.ibd.businessViews.IInstituteSummaryService");
                 response= service.EJBprocessing(request,"InstituteAssignmentSummary");
                 break;
             }
             case "InstituteOtherActivitySummary":
             {
                  IInstituteSummaryService service=(IInstituteSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/InstituteSummaryService!com.ibd.businessViews.IInstituteSummaryService");
                 response= service.EJBprocessing(request,"InstituteOtherActivitySummary");
                 break;
             }
             case "InstituteFeeManagementSummary":
             {
                  IInstituteSummaryService service=(IInstituteSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/InstituteSummaryService!com.ibd.businessViews.IInstituteSummaryService");
                 response= service.EJBprocessing(request,"InstituteFeeManagementSummary");
                 break;
             }
             case "InstitutePaymentSummary":
             {
                  IInstituteSummaryService service=(IInstituteSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/InstituteSummaryService!com.ibd.businessViews.IInstituteSummaryService");
                 response= service.EJBprocessing(request,"InstitutePaymentSummary");
                 break;
             }
             case "GroupMappingSummary":
             {
                  IInstituteSummaryService service=(IInstituteSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/InstituteSummaryService!com.ibd.businessViews.IInstituteSummaryService");
                 response= service.EJBprocessing(request,"GroupMappingSummary");
                 break;
             }
             case "HolidayMaintenanceSummary":
             {
                  IInstituteSummaryService service=(IInstituteSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/InstituteSummaryService!com.ibd.businessViews.IInstituteSummaryService");
                 response= service.EJBprocessing(request,"HolidayMaintenanceSummary");
                 break;
             }
             case "NotificationSummary":
             {
                  IInstituteSummaryService service=(IInstituteSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/InstituteSummaryService!com.ibd.businessViews.IInstituteSummaryService");
                 response= service.EJBprocessing(request,"NotificationSummary");
                 break;
             }
             case "UserProfileSummary":
             {
                  IUserSummaryService service=(IUserSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/UserSummaryService!com.ibd.businessViews.IUserSummaryService");
                 response= service.EJBprocessing(request,"UserProfileSummary");
                 break;
             }
             case "UserRoleSummary":
             {
                  IUserSummaryService service=(IUserSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/UserSummaryService!com.ibd.businessViews.IUserSummaryService");
                 response= service.EJBprocessing(request,"UserRoleSummary");
                 break;
             }
             case "StudentNotificationService":
             {
                 IStudentNotificationService service=(IStudentNotificationService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/StudentNotificationService!com.ibd.businessViews.IStudentNotificationService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "ParentDashBoardService":
             {
                 IStudentSummaryService service=(IStudentSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/StudentSummaryService!com.ibd.businessViews.IStudentSummaryService");
                response= service.EJBprocessing(request,"ParentDashBoardService");
                 break;
             }
             case "TeacherDashBoardService":
             {
                 ITeacherSummaryService service=(ITeacherSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/TeacherSummaryService!com.ibd.businessViews.ITeacherSummaryService");
                response= service.EJBprocessing(request,"TeacherDashBoardService");
                 break;
             }
             case "ManagementDashBoardService":
             {
                  IInstituteSummaryService service=(IInstituteSummaryService) context.lookup("java:global/CohesiveReportEngine/CohesiveReportEngine-ejb/InstituteSummaryService!com.ibd.businessViews.IInstituteSummaryService");
                 response= service.EJBprocessing(request,"ManagementDashBoardService");
                 break;
             }
             case "StudentSearchService":
             {
                 IStudentSearchService service=(IStudentSearchService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/StudentSearchService!com.ibd.businessViews.IStudentSearchService");
                response= service.EJBprocessing(request);
                 break;
             }
              case "TeacherSearchService":
             {
                 ITeacherSearchService service=(ITeacherSearchService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/TeacherSearchService!com.ibd.businessViews.ITeacherSearchService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "InstituteSearchService":
             {
                 IInstituteSearchService service=(IInstituteSearchService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/InstituteSearchService!com.ibd.businessViews.IInstituteSearchService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "UserSearchService":
             {
                 IUserSearchService service=(IUserSearchService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/UserSearchService!com.ibd.businessViews.IUserSearchService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "RoleSearchService":
             {
                 IRoleSearchService service=(IRoleSearchService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/RoleSearchService!com.ibd.businessViews.IRoleSearchService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "FeeSearchService":
             {
                 IFeeSearchService service=(IFeeSearchService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/FeeSearchService!com.ibd.businessViews.IFeeSearchService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "AssignmentSearchService":
             {
                 IAssignmentSearchService service=(IAssignmentSearchService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/AssignmentSearchService!com.ibd.businessViews.IAssignmentSearchService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "PaymentSearchService":
             {
                 IPaymentSearchService service=(IPaymentSearchService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/PaymentSearchService!com.ibd.businessViews.IPaymentSearchService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "NotificationSearchService":
             {
                 INotificationSearchService service=(INotificationSearchService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/NotificationSearchService!com.ibd.businessViews.INotificationSearchService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "OtherActivitySearchService":
             {
                 IOtherActivitySearchService service=(IOtherActivitySearchService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/OtherActivitySearchService!com.ibd.businessViews.IOtherActivitySearchService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "BatchSearchService":
             {
                 IBatchSearchService service=(IBatchSearchService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/BatchSearchService!com.ibd.businessViews.IBatchSearchService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "AssigneeSearchService":
             {
                 IAssigneeSearchService service=(IAssigneeSearchService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/AssigneeSearchService!com.ibd.businessViews.IAssigneeSearchService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "ECircularService":
             {
                 IECircularService service=(IECircularService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/ECircularService!com.ibd.businessViews.IECircularService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "StudentECircularService":
             {
                 IStudentECircularService service=(IStudentECircularService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/StudentECircularService!com.ibd.businessViews.IStudentECircularService");
                response= service.EJBprocessing(request);
                 break;
             }
             case "SelectBoxMasterService":
             {
                 ISelectBoxMasterService service=(ISelectBoxMasterService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/SelectBoxMasterService!com.ibd.businessViews.ISelectBoxMasterService");
                response= service.EJBprocessing(request);
                 break;
             }
       }
       }  catch(Exception e){
           dbg(e);
            throw new RuntimeException(e);
        }
       return response;
        }
    
    private void dbg(String p_value){
        dbg.dbg(p_value);
    }
     private void dbg(Exception ex){
        dbg.exceptionDbg(ex);
    }

    
    
      }
