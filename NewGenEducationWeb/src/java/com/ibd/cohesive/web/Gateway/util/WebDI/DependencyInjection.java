/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.web.Gateway.util.WebDI;

import com.ibd.businessViews.IAssigneeSearchService;
import com.ibd.businessViews.IAssignmentSearchService;
import com.ibd.businessViews.IAuthTokenBridge;
import com.ibd.businessViews.IAuthenticateService;
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
import com.ibd.businessViews.IClassSummary;
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
import com.ibd.businessViews.IResourceTokenBridge;
import com.ibd.businessViews.IResourceTokenService;
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
import com.ibd.businessViews.ITeacherProfileService;
import com.ibd.businessViews.ITeacherProfileSummary;
import com.ibd.businessViews.ITeacherSearchService;
import com.ibd.businessViews.ITeacherTimeTableService;
import com.ibd.businessViews.ITeacherTimeTableSummary;
import com.ibd.businessViews.ITokenValidationService;
import com.ibd.businessViews.IUserProfileService;
import com.ibd.businessViews.IUserProfileSummary;
import com.ibd.businessViews.IUserRoleService;
import com.ibd.businessViews.IUserRoleSummary;
import com.ibd.businessViews.IUserSearchService;
import com.ibd.cohesive.util.session.CohesiveSession;
import com.ibd.cohesive.web.Gateway.util.CohesiveBeans;
import com.ibd.cohesive.web.Gateway.util.WebUtility;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
public class DependencyInjection {
    
    private CohesiveBeans beans;
    private WebUtility webutil;
     //private InitialContext contxt;
     final Context contxt;  
     //final Hashtable props = new Hashtable();
     Properties props = new Properties();
    //final Context contxt;  
    //final Hashtable reportProps = new Hashtable();
  
   /*public static Properties getCtxProperties() {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        props.put(Context.PROVIDER_URL, "remote+http://10.0.0.4:8080");
        props.put(Context.SECURITY_PRINCIPAL, "cohesiveejbadmin");
        props.put(Context.SECURITY_CREDENTIALS, "Birthday@310181");
        return props;
    }*/
    
    public DependencyInjection() throws NamingException
    {
        
   //  try
     //{
            // setup the ejb: namespace URL factory
         props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
         props.put("jboss.naming.client.ejb.context", "true");    
       //: props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming.remote.client.InitialContextFactory");
            // create the InitialContext
         contxt= new InitialContext(props);
        //contxt = new InitialContext();
        if(beans==null){
                beans=new CohesiveBeans();
             
        }
     if (webutil==null)
        {        
                webutil= new WebUtility();
        }
     
       // Properties props = new Properties();
      
     //reportProps.put("remote.connections", "default");
       // reportProps.put("remote.connection.default.username", "cohesiveejbadmin");
        //reportProps.put("remote.connection.default.password", "QmlydGhkYXlAMzEwMTgx");
        //reportProps.put("remote.connection.default.host", "10.0.2.2");
         //reportProps.put("remote.connection.default.port", "8080");        
        
        //Implies "org.jboss.xnio" module dependency
       // reportProps.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false"); 
        //reportProps.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");	
        //reportProps.put("remote.connection.default.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS", "JBOSS-LOCAL-USER");
        //reportProps.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
        //reportProps.put("org.jboss.ejb.client.scoped.context","true");
        //reportProps.put("javax.security.sasl.policy.noplaintext", "false");
        //reportProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming"); 
        
   //contxt = (Context) new InitialContext(reportProps); 



//}
     /*catch(NamingException ex) {
           //dbg(ex);
           throw new RuntimeException(ex);
           }*/
     //}
    }
    
   public CohesiveBeans getCohesiveBean(CohesiveSession session){
        beans.setDbg(session.getDebug());
        return beans;
        
    } 
   
   public WebUtility getWebUtil(CohesiveSession session){
        webutil.setDbg(session.getDebug());
        return webutil;
        
    }
 public ITokenValidationService getTokenValidationService() throws NamingException{
        //EJB Integration change
        //return dbcoreservice;
       // InitialContext a =new InitialContext();
    ITokenValidationService authTokenValiation = (ITokenValidationService)
        // contxt.lookup("java:app/CohesiveBusiness-ejb/TokenValidateService!com.ibd.businessViews.ITokenValidationService");
 contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/TokenValidateService!com.ibd.businessViews.ITokenValidationService");

            
            return authTokenValiation;
    }

 public IAuthenticateService getAuthenticateService() throws NamingException{
  
 
      IAuthenticateService service=(IAuthenticateService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/AuthenticateService!com.ibd.businessViews.IAuthenticateService");     
              
            return service;
    }
public IResourceTokenService getResourceTokenService() throws NamingException{
  
     
      IResourceTokenService service=(IResourceTokenService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ResourceTokenService!com.ibd.businessViews.IResourceTokenService");     
              
            return service;
    }

public IStudentMasterService getStudentMasterService() throws NamingException{
  
     
     IStudentMasterService service=(IStudentMasterService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentMasterService!com.ibd.businessViews.IStudentMasterService");
                           
            return service;
    }

public IStudentProfileService getStudentProfileService() throws NamingException{
  
     
     IStudentProfileService service=(IStudentProfileService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentProfileService!com.ibd.businessViews.IStudentProfileService");
                           
            return service;
    }

public IUserProfileService getUserProfileService() throws NamingException{
  
     
     IUserProfileService service=(IUserProfileService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/UserProfileService!com.ibd.businessViews.IUserProfileService");
                           
            return service;
    }

public IUserRoleService getUserRoleService() throws NamingException{
  
     
     IUserRoleService service=(IUserRoleService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/UserRoleService!com.ibd.businessViews.IUserRoleService");
                           
            return service;
    }
public IGeneralLevelConfigurationService getGeneralLevelConfigurationService() throws NamingException{
  
     
     IGeneralLevelConfigurationService service=(IGeneralLevelConfigurationService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/GeneralLevelConfigurationService!com.ibd.businessViews.IGeneralLevelConfigurationService");
                           
            return service;
    }
public ISoftSkillConfigurationService getSoftSkillConfigurationService() throws NamingException{
  
     
     ISoftSkillConfigurationService service=(ISoftSkillConfigurationService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/SoftSkillConfigurationService!com.ibd.businessViews.ISoftSkillConfigurationService");
                           
            return service;
    }

public IFileUploadService getFileUploadService() throws NamingException{
  
     
     IFileUploadService service=(IFileUploadService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/FileUploadService!com.ibd.businessViews.IFileUploadService");
                           
            return service;
    }
public IInstituteSearchService getInstituteSerachService(CohesiveSession session) throws NamingException{
  
//      if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
//      {
//          bridge();
//        IInstituteSearchService service=(IInstituteSearchService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/InstituteSearchService!com.ibd.businessViews.IInstituteSearchService");
//                         
//            return service;
//      } 
//       else
//   {
       IInstituteSearchService service=(IInstituteSearchService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/InstituteSearchService!com.ibd.businessViews.IInstituteSearchService");
                         
            return service;
       
      
//      }
}

public IInstituteUserSearchService getInstituteUserSerachService(CohesiveSession session) throws NamingException{
  

       IInstituteUserSearchService service=(IInstituteUserSearchService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/InstituteUserSearchService!com.ibd.businessViews.IInstituteUserSearchService");
                         
            return service;
       

}
public ISelectBoxMasterService getSelectBoxMasterService(CohesiveSession session) throws NamingException{
  
   if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     ISelectBoxMasterService service=(ISelectBoxMasterService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/SelectBoxMasterService!com.ibd.businessViews.ISelectBoxMasterService");
                           
            return service;
    }
    else
   { 
     ISelectBoxMasterService service=(ISelectBoxMasterService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/SelectBoxMasterService!com.ibd.businessViews.ISelectBoxMasterService");
                           
            return service;  
       
   }
       
       

}


public IDashBoardService getDashBoardService(CohesiveSession session) throws NamingException{
  
   if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IDashBoardService service=(IDashBoardService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/DashBoardService!com.ibd.businessViews.IDashBoardService");
                           
            return service;
    }
    else
   { 
     IDashBoardService service=(IDashBoardService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/DashBoardService!com.ibd.businessViews.IDashBoardService");
                           
            return service;  
       
   }
       
       

}



public IStudentProfileSummary getStudentProfileSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IStudentProfileSummary service=(IStudentProfileSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/StudentProfileSummary!com.ibd.businessViews.IStudentProfileSummary");
                           
            return service;
    }
    else
   { 
     IStudentProfileSummary service=(IStudentProfileSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentProfileSummary!com.ibd.businessViews.IStudentProfileSummary");
                           
            return service;
   } 
   }

    public IClassAttendanceService getClassAttendanceService() throws NamingException{
  
     
     IClassAttendanceService service=(IClassAttendanceService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ClassAttendanceService!com.ibd.businessViews.IClassAttendanceService");
                           
            return service;
    }

    public IClassExamScheduleService getClassExamScheduleService() throws NamingException{
     
     IClassExamScheduleService service=(IClassExamScheduleService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ClassExamScheduleService!com.ibd.businessViews.IClassExamScheduleService");
                           
            return service;
    }
    
    public IClassMarkService getClassMarkService() throws NamingException{
     
     IClassMarkService service=(IClassMarkService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ClassMarkService!com.ibd.businessViews.IClassMarkService");
                           
            return service;
    }
    
    public IClassSoftSkillService getClassSoftSkillService() throws NamingException{
     
     IClassSoftSkillService service=(IClassSoftSkillService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ClassSoftSkillService!com.ibd.businessViews.IClassSoftSkillService");
                           
            return service;
    }
    
    public IClassTimeTableService getClassTimeTableService() throws NamingException{
  
     
     IClassTimeTableService service=(IClassTimeTableService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ClassTimeTableService!com.ibd.businessViews.IClassTimeTableService");
                           
            return service;
    }
    
    public IClassLevelConfigurationService getClassLevelConfigurationService() throws NamingException{
  
     
     IClassLevelConfigurationService service=(IClassLevelConfigurationService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ClassLevelConfigurationService!com.ibd.businessViews.IClassLevelConfigurationService");
                           
            return service;
    }
    
    public IECircularService getECircularService() throws NamingException{
  
     
     IECircularService service=(IECircularService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ECircularService!com.ibd.businessViews.IECircularService");
                           
            return service;
    }
    public IGroupMappingService getGroupMappingService() throws NamingException{
  
     
     IGroupMappingService service=(IGroupMappingService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/GroupMappingService!com.ibd.businessViews.IGroupMappingService");
                           
            return service;
    }
    
    public IHolidayMaintanenceService getHolidayMaintanenceService() throws NamingException{
  
     
     IHolidayMaintanenceService service=(IHolidayMaintanenceService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/HolidayMaintanenceService!com.ibd.businessViews.IHolidayMaintanenceService");
                           
            return service;
    }
    
    public IInstituteAssignmentService getInstituteAssignmentService() throws NamingException{
  
     
     IInstituteAssignmentService service=(IInstituteAssignmentService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/InstituteAssignmentService!com.ibd.businessViews.IInstituteAssignmentService");
                           
            return service;
    }
    
    public IInstituteFeeManagementService getInstituteFeeManagementService() throws NamingException{
  
     
     IInstituteFeeManagementService service=(IInstituteFeeManagementService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/InstituteFeeManagementService!com.ibd.businessViews.IInstituteFeeManagementService");
                           
            return service;
    }
    public IInstituteOtherActivityService getInstituteOtherActivityService() throws NamingException{
  
     
     IInstituteOtherActivityService service=(IInstituteOtherActivityService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/InstituteOtherActivityService!com.ibd.businessViews.IInstituteOtherActivityService");
                           
            return service;
    }
    
    public IInstitutePaymentService getInstitutePaymentService() throws NamingException{
  
     
     IInstitutePaymentService service=(IInstitutePaymentService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/InstitutePaymentService!com.ibd.businessViews.IInstitutePaymentService");
                           
            return service;
    }
    
    public INotificationService getNotificationService() throws NamingException{
  
     
     INotificationService service=(INotificationService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/NotificationService!com.ibd.businessViews.INotificationService");
                           
            return service;
    }
    
    public IStudentECircularService getStudentECircularService() throws NamingException{
  
     
     IStudentECircularService service=(IStudentECircularService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentECircularService!com.ibd.businessViews.IStudentECircularService");
                           
            return service;
    }
    public ITeacherECircularService getTeacherECircularService() throws NamingException{
  
     
     ITeacherECircularService service=(ITeacherECircularService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/TeacherECircularService!com.ibd.businessViews.ITeacherECircularService");
                           
            return service;
    }
    public IStudentAssignmentService getStudentAssignmentService() throws NamingException{
  
     
     IStudentAssignmentService service=(IStudentAssignmentService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentAssignmentService!com.ibd.businessViews.IStudentAssignmentService");
                           
            return service;
    }
    
    public IStudentAttendanceService getStudentAttendanceService() throws NamingException{
  
     
     IStudentAttendanceService service=(IStudentAttendanceService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentAttendanceService!com.ibd.businessViews.IStudentAttendanceService");
                           
            return service;
    }
    public IStudentCalenderService getStudentCalenderService() throws NamingException{
  
     
     IStudentCalenderService service=(IStudentCalenderService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentCalenderService!com.ibd.businessViews.IStudentCalenderService");
                           
            return service;
    }
    
    public IStudentExamScheduleService getStudentExamScheduleService() throws NamingException{
  
     
     IStudentExamScheduleService service=(IStudentExamScheduleService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentExamScheduleService!com.ibd.businessViews.IStudentExamScheduleService");
                           
            return service;
    }
    
    public IStudentFeeManagementService getStudentFeeManagementService() throws NamingException{
  
     
     IStudentFeeManagementService service=(IStudentFeeManagementService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentFeeManagementService!com.ibd.businessViews.IStudentFeeManagementService");
                           
            return service;
    }
    
    public IStudentLeaveManagementService getStudentLeaveManagementService() throws NamingException{
  
     
     IStudentLeaveManagementService service=(IStudentLeaveManagementService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentLeaveManagementService!com.ibd.businessViews.IStudentLeaveManagementService");
                           
            return service;
    }
    
    public IStudentNotificationService getStudentNotificationService() throws NamingException{
  
     
     IStudentNotificationService service=(IStudentNotificationService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentNotificationService!com.ibd.businessViews.IStudentNotificationService");
                           
            return service;
    }
    public IStudentOtherActivityService getStudentOtherActivityService() throws NamingException{
  
     
     IStudentOtherActivityService service=(IStudentOtherActivityService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentOtherActivityService!com.ibd.businessViews.IStudentOtherActivityService");
                           
            return service;
    }
    public IStudentPaymentService getStudentPaymentService() throws NamingException{
  
     
     IStudentPaymentService service=(IStudentPaymentService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentPaymentService!com.ibd.businessViews.IStudentPaymentService");
                           
            return service;
    }
    public IStudentProgressCardService getStudentProgressCardService() throws NamingException{
  
     
     IStudentProgressCardService service=(IStudentProgressCardService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentProgressCardService!com.ibd.businessViews.IStudentProgressCardService");
                           
            return service;
    }
    public IStudentSoftSkillService getStudentSoftSkillService() throws NamingException{
  
     
     IStudentSoftSkillService service=(IStudentSoftSkillService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentSoftSkillService!com.ibd.businessViews.IStudentSoftSkillService");
                           
            return service;
    }
    public IStudentTimeTableService getStudentTimeTableService() throws NamingException{
  
     
     IStudentTimeTableService service=(IStudentTimeTableService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentTimeTableService!com.ibd.businessViews.IStudentTimeTableService");
                           
            return service;
    }
    
    public IPayrollService getPayrollService() throws NamingException{
  
     
     IPayrollService service=(IPayrollService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/PayrollService!com.ibd.businessViews.IPayrollService");
                           
            return service;
    }
    public IOTPService getOTPService() throws NamingException{
  
     
     IOTPService service=(IOTPService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/OTPService!com.ibd.businessViews.IOTPService");
                           
            return service;
    }
    public ITeacherAttendanceService getTeacherAttendanceService() throws NamingException{
  
     
     ITeacherAttendanceService service=(ITeacherAttendanceService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/TeacherAttendanceService!com.ibd.businessViews.ITeacherAttendanceService");
                           
            return service;
    }
    
     public ITeacherCalenderService getTeacherCalenderService() throws NamingException{
  
     
     ITeacherCalenderService service=(ITeacherCalenderService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/TeacherCalenderService!com.ibd.businessViews.ITeacherCalenderService");
                           
            return service;
    }
    
    public ITeacherLeaveManagementService getTeacherLeaveManagementService() throws NamingException{
  
     
     ITeacherLeaveManagementService service=(ITeacherLeaveManagementService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/TeacherLeaveManagementService!com.ibd.businessViews.ITeacherLeaveManagementService");
                           
            return service;
    }
    public ITeacherProfileService getTeacherProfileService() throws NamingException{
  
     
     ITeacherProfileService service=(ITeacherProfileService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/TeacherProfileService!com.ibd.businessViews.ITeacherProfileService");
                           
            return service;
    }
    
    public ITeacherTimeTableService getTeacherTimeTableService() throws NamingException{
  
     
     ITeacherTimeTableService service=(ITeacherTimeTableService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/TeacherTimeTableService!com.ibd.businessViews.ITeacherTimeTableService");
                           
            return service;
    }
    
    public IClassExamScheduleSummary getClassExamScheduleSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IClassExamScheduleSummary service=(IClassExamScheduleSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/ClassExamScheduleSummary!com.ibd.businessViews.IClassExamScheduleSummary");
                           
            return service;
    }
    else
   { 
     IClassExamScheduleSummary service=(IClassExamScheduleSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ClassExamScheduleSummary!com.ibd.businessViews.IClassExamScheduleSummary");
                           
            return service;
   } 
   }
    
    public IClassAttendanceSummary getClassAttendanceSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IClassAttendanceSummary service=(IClassAttendanceSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/ClassAttendanceSummary!com.ibd.businessViews.IClassAttendanceSummary");
                           
            return service;
    }
    else
   { 
     IClassAttendanceSummary service=(IClassAttendanceSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ClassAttendanceSummary!com.ibd.businessViews.IClassAttendanceSummary");
                           
            return service;
   } 
   }
    
    
    public IClassMarkSummary getClassMarkSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IClassMarkSummary service=(IClassMarkSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/ClassMarkSummary!com.ibd.businessViews.IClassMarkSummary");
                           
            return service;
    }
    else
   { 
     IClassMarkSummary service=(IClassMarkSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ClassMarkSummary!com.ibd.businessViews.IClassMarkSummary");
                           
            return service;
   } 
   }
    
    
    public IClassSoftSkillSummary getClassSoftSkillSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IClassSoftSkillSummary service=(IClassSoftSkillSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/ClassSoftSkillSummary!com.ibd.businessViews.IClassSoftSkillSummary");
                           
            return service;
    }
    else
   { 
     IClassSoftSkillSummary service=(IClassSoftSkillSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ClassSoftSkillSummary!com.ibd.businessViews.IClassSoftSkillSummary");
                           
            return service;
   } 
   }
    
    
    
    
    
    
    public IClassSummary getClassSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IClassSummary service=(IClassSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/ClassSummary!com.ibd.businessViews.IClassSummary");
                           
            return service;
    }
    else
   { 
     IClassSummary service=(IClassSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ClassSummary!com.ibd.businessViews.IClassSummary");
                           
            return service;
   } 
   }
    
    public IClassTimeTableSummary getClassTimeTableSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IClassTimeTableSummary service=(IClassTimeTableSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/ClassTimeTableSummary!com.ibd.businessViews.IClassTimeTableSummary");
                           
            return service;
    }
    else
   { 
     IClassTimeTableSummary service=(IClassTimeTableSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ClassTimeTableSummary!com.ibd.businessViews.IClassTimeTableSummary");
                           
            return service;
   } 
   }
    
    
    public IInstituteAssignmentSummary getInstituteAssignmentSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IInstituteAssignmentSummary service=(IInstituteAssignmentSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/InstituteAssignmentSummary!com.ibd.businessViews.IInstituteAssignmentSummary");
                           
            return service;
    }
    else
   { 
     IInstituteAssignmentSummary service=(IInstituteAssignmentSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/InstituteAssignmentSummary!com.ibd.businessViews.IInstituteAssignmentSummary");
                           
            return service;
   } 
   }
    public IECircularSummary getECircularSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IECircularSummary service=(IECircularSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/ECircularSummary!com.ibd.businessViews.IECircularSummary");
                           
            return service;
    }
    else
   { 
     IECircularSummary service=(IECircularSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ECircularSummary!com.ibd.businessViews.IECircularSummary");
                           
            return service;
   } 
   }
    
    public IInstituteOtherActivitySummary getInstituteOtherActivitySummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IInstituteOtherActivitySummary service=(IInstituteOtherActivitySummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/InstituteOtherActivitySummary!com.ibd.businessViews.IInstituteOtherActivitySummary");
                           
            return service;
    }
    else
   { 
     IInstituteOtherActivitySummary service=(IInstituteOtherActivitySummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/InstituteOtherActivitySummary!com.ibd.businessViews.IInstituteOtherActivitySummary");
                           
            return service;
   } 
   }
    
    
    public IInstituteFeeManagementSummary getInstituteFeeManagementSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IInstituteFeeManagementSummary service=(IInstituteFeeManagementSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/InstituteFeeManagementSummary!com.ibd.businessViews.IInstituteFeeManagementSummary");
                           
            return service;
    }
    else
   { 
     IInstituteFeeManagementSummary service=(IInstituteFeeManagementSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/InstituteFeeManagementSummary!com.ibd.businessViews.IInstituteFeeManagementSummary");
                           
            return service;
   } 
   }
    
    
    public IInstitutePaymentSummary getInstitutePaymentSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IInstitutePaymentSummary service=(IInstitutePaymentSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/InstitutePaymentSummary!com.ibd.businessViews.IInstitutePaymentSummary");
                           
            return service;
    }
    else
   { 
     IInstitutePaymentSummary service=(IInstitutePaymentSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/InstitutePaymentSummary!com.ibd.businessViews.IInstitutePaymentSummary");
                           
            return service;
   } 
   }
    
    public IGroupMappingSummary getGroupMappingSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IGroupMappingSummary service=(IGroupMappingSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/GroupMappingSummary!com.ibd.businessViews.IGroupMappingSummary");
                           
            return service;
    }
    else
   { 
     IGroupMappingSummary service=(IGroupMappingSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/GroupMappingSummary!com.ibd.businessViews.IGroupMappingSummary");
                           
            return service;
   } 
   }
    
    
    public IHolidayMaintenanceSummary getHolidayMaintenanceSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IHolidayMaintenanceSummary service=(IHolidayMaintenanceSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/HolidayMaintenanceSummary!com.ibd.businessViews.IHolidayMaintenanceSummary");
                           
            return service;
    }
    else
   { 
     IHolidayMaintenanceSummary service=(IHolidayMaintenanceSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/HolidayMaintenanceSummary!com.ibd.businessViews.IHolidayMaintenanceSummary");
                           
            return service;
   } 
   }
    
    
    public INotificationSummary getNotificationSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     INotificationSummary service=(INotificationSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/NotificationSummary!com.ibd.businessViews.INotificationSummary");
                           
            return service;
    }
    else
   { 
     INotificationSummary service=(INotificationSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/NotificationSummary!com.ibd.businessViews.INotificationSummary");
                           
            return service;
   } 
   }
    
    
    
    public IStudentAssignmentSummary getStudentAssignmentSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IStudentAssignmentSummary service=(IStudentAssignmentSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/StudentAssignmentSummary!com.ibd.businessViews.IStudentAssignmentSummary");
                           
            return service;
    }
    else
   { 
     IStudentAssignmentSummary service=(IStudentAssignmentSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentAssignmentSummary!com.ibd.businessViews.IStudentAssignmentSummary");
                           
            return service;
   } 
   }
    public IStudentECircularSummary getStudentECircularSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IStudentECircularSummary service=(IStudentECircularSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/StudentECircularSummary!com.ibd.businessViews.IStudentECircularSummary");
                           
            return service;
    }
    else
   { 
     IStudentECircularSummary service=(IStudentECircularSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentECircularSummary!com.ibd.businessViews.IStudentECircularSummary");
                           
            return service;
   } 
   }
    
    public ITeacherECircularSummary getTeacherECircularSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     ITeacherECircularSummary service=(ITeacherECircularSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/TeacherECircularSummary!com.ibd.businessViews.ITeacherECircularSummary");
                           
            return service;
    }
    else
   { 
     ITeacherECircularSummary service=(ITeacherECircularSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/TeacherECircularSummary!com.ibd.businessViews.ITeacherECircularSummary");
                           
            return service;
   } 
   }
    
    public IStudentAttendanceSummary getStudentAttendanceSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IStudentAttendanceSummary service=(IStudentAttendanceSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/StudentAttendanceSummary!com.ibd.businessViews.IStudentAttendanceSummary");
                           
            return service;
    }
    else
   { 
     IStudentAttendanceSummary service=(IStudentAttendanceSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentAttendanceSummary!com.ibd.businessViews.IStudentAttendanceSummary");
                           
            return service;
   } 
   }
    
    
    public IStudentNotificationSummary getStudentNotificationSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IStudentNotificationSummary service=(IStudentNotificationSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/StudentNotificationSummary!com.ibd.businessViews.IStudentNotificationSummary");
                           
            return service;
    }
    else
   { 
     IStudentNotificationSummary service=(IStudentNotificationSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentNotificationSummary!com.ibd.businessViews.IStudentNotificationSummary");
                           
            return service;
   } 
   }
    
    
    
    
    
    public IStudentExamScheduleSummary getStudentExamScheduleSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IStudentExamScheduleSummary service=(IStudentExamScheduleSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/StudentExamScheduleSummary!com.ibd.businessViews.IStudentExamScheduleSummary");
                           
            return service;
    }
    else
   { 
     IStudentExamScheduleSummary service=(IStudentExamScheduleSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentExamScheduleSummary!com.ibd.businessViews.IStudentExamScheduleSummary");
                           
            return service;
   } 
   }
    
    
    public IStudentFeeManagementSummary getStudentFeeManagementSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IStudentFeeManagementSummary service=(IStudentFeeManagementSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/StudentFeeManagementSummary!com.ibd.businessViews.IStudentFeeManagementSummary");
                           
            return service;
    }
    else
   { 
     IStudentFeeManagementSummary service=(IStudentFeeManagementSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentFeeManagementSummary!com.ibd.businessViews.IStudentFeeManagementSummary");
                           
            return service;
   } 
   }
    
    public IStudentLeaveManagementSummary getStudentLeaveManagementSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IStudentLeaveManagementSummary service=(IStudentLeaveManagementSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/StudentLeaveManagementSummary!com.ibd.businessViews.IStudentLeaveManagementSummary");
                           
            return service;
    }
    else
   { 
     IStudentLeaveManagementSummary service=(IStudentLeaveManagementSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentLeaveManagementSummary!com.ibd.businessViews.IStudentLeaveManagementSummary");
                           
            return service;
   } 
   }
    
    public IStudentOtherActivitySummary getStudentOtherActivitySummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IStudentOtherActivitySummary service=(IStudentOtherActivitySummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/StudentOtherActivitySummary!com.ibd.businessViews.IStudentOtherActivitySummary");
                           
            return service;
    }
    else
   { 
     IStudentOtherActivitySummary service=(IStudentOtherActivitySummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentOtherActivitySummary!com.ibd.businessViews.IStudentOtherActivitySummary");
                           
            return service;
   } 
   }
    
    
    public IStudentPaymentSummary getStudentPaymentSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IStudentPaymentSummary service=(IStudentPaymentSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/StudentPaymentSummary!com.ibd.businessViews.IStudentPaymentSummary");
                           
            return service;
    }
    else
   { 
     IStudentPaymentSummary service=(IStudentPaymentSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentPaymentSummary!com.ibd.businessViews.IStudentPaymentSummary");
                           
            return service;
   } 
   }
    
    
    public IStudentProgressCardSummary getStudentProgressCardSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IStudentProgressCardSummary service=(IStudentProgressCardSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/StudentProgressCardSummary!com.ibd.businessViews.IStudentProgressCardSummary");
                           
            return service;
    }
    else
   { 
     IStudentProgressCardSummary service=(IStudentProgressCardSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentProgressCardSummary!com.ibd.businessViews.IStudentProgressCardSummary");
                           
            return service;
   } 
   }
    public IStudentSoftSkillSummary getStudentSoftSkillSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IStudentSoftSkillSummary service=(IStudentSoftSkillSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/StudentSoftSkillSummary!com.ibd.businessViews.IStudentSoftSkillSummary");
                           
            return service;
    }
    else
   { 
     IStudentSoftSkillSummary service=(IStudentSoftSkillSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentSoftSkillSummary!com.ibd.businessViews.IStudentSoftSkillSummary");
                           
            return service;
   } 
   }
    public IStudentTimeTableSummary getStudentTimeTableSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IStudentTimeTableSummary service=(IStudentTimeTableSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/StudentTimeTableSummary!com.ibd.businessViews.IStudentTimeTableSummary");
                           
            return service;
    }
    else
   { 
     IStudentTimeTableSummary service=(IStudentTimeTableSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentTimeTableSummary!com.ibd.businessViews.IStudentTimeTableSummary");
                           
            return service;
   } 
   }
    
    public ITeacherAttendanceSummary getTeacherAttendanceSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     ITeacherAttendanceSummary service=(ITeacherAttendanceSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/TeacherAttendanceSummary!com.ibd.businessViews.ITeacherAttendanceSummary");
                           
            return service;
    }
    else
   { 
     ITeacherAttendanceSummary service=(ITeacherAttendanceSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/TeacherAttendanceSummary!com.ibd.businessViews.ITeacherAttendanceSummary");
                           
            return service;
   } 
   }
    
    public ITeacherLeaveManagementSummary getTeacherLeaveManagementSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     ITeacherLeaveManagementSummary service=(ITeacherLeaveManagementSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/TeacherLeaveManagementSummary!com.ibd.businessViews.ITeacherLeaveManagementSummary");
                           
            return service;
    }
    else
   { 
     ITeacherLeaveManagementSummary service=(ITeacherLeaveManagementSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/TeacherLeaveManagementSummary!com.ibd.businessViews.ITeacherLeaveManagementSummary");
                           
            return service;
   } 
   }
    
    public ITeacherProfileSummary getTeacherProfileSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     ITeacherProfileSummary service=(ITeacherProfileSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/TeacherProfileSummary!com.ibd.businessViews.ITeacherProfileSummary");
                           
            return service;
    }
    else
   { 
     ITeacherProfileSummary service=(ITeacherProfileSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/TeacherProfileSummary!com.ibd.businessViews.ITeacherProfileSummary");
                           
            return service;
   } 
   }
    
    public ITeacherTimeTableSummary getTeacherTimeTableSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     ITeacherTimeTableSummary service=(ITeacherTimeTableSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/TeacherTimeTableSummary!com.ibd.businessViews.ITeacherTimeTableSummary");
                           
            return service;
    }
    else
   { 
     ITeacherTimeTableSummary service=(ITeacherTimeTableSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/TeacherTimeTableSummary!com.ibd.businessViews.ITeacherTimeTableSummary");
                           
            return service;
   } 
   }
    
    
    public IUserProfileSummary getUserProfileSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IUserProfileSummary service=(IUserProfileSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/UserProfileSummary!com.ibd.businessViews.IUserProfileSummary");
                           
            return service;
    }
    else
   { 
     IUserProfileSummary service=(IUserProfileSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/UserProfileSummary!com.ibd.businessViews.IUserProfileSummary");
                           
            return service;
   } 
   }
    
    public IUserRoleSummary getUserRoleSummary(CohesiveSession session) throws NamingException{
  
    if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
   {
       bridge();
     IUserRoleSummary service=(IUserRoleSummary) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/UserRoleSummary!com.ibd.businessViews.IUserRoleSummary");
                           
            return service;
    }
    else
   { 
     IUserRoleSummary service=(IUserRoleSummary) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/UserRoleSummary!com.ibd.businessViews.IUserRoleSummary");
                           
            return service;
   } 
   }
    
    
   public IAssigneeSearchService getAssigneeSerachService(CohesiveSession session) throws NamingException{
  
//      if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
//      {
//          bridge();
//        IAssigneeSearchService service=(IAssigneeSearchService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/AssigneeSearchService!com.ibd.businessViews.IAssigneeSearchService");
//                         
//            return service;
//      } 
//       else
//   {
       IAssigneeSearchService service=(IAssigneeSearchService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/AssigneeSearchService!com.ibd.businessViews.IAssigneeSearchService");
                         
            return service;
       
      
//      }
} 
   
  public IAssignmentSearchService getAssignmentSerachService(CohesiveSession session) throws NamingException{
  
//      if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
//      {
//          bridge();
//        IAssignmentSearchService service=(IAssignmentSearchService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/AssignmentSearchService!com.ibd.businessViews.IAssignmentSearchService");
//                         
//            return service;
//      } 
//       else
//   {
       IAssignmentSearchService service=(IAssignmentSearchService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/AssignmentSearchService!com.ibd.businessViews.IAssignmentSearchService");
                         
            return service;
       
      
//      }
}
  public IECircularSearchService getECircularSerachService(CohesiveSession session) throws NamingException{
  
//      if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
//      {
//          bridge();
//        IECircularSearchService service=(IECircularSearchService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/ECircularSearchService!com.ibd.businessViews.IECircularSearchService");
//                         
//            return service;
//      } 
//       else
//   {
       IECircularSearchService service=(IECircularSearchService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ECircularSearchService!com.ibd.businessViews.IECircularSearchService");
                         
            return service;
       
      
//      }
}
  
  public IBatchSearchService getBatchSerachService(CohesiveSession session) throws NamingException{
  
//      if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
//      {
//          bridge();
//        IBatchSearchService service=(IBatchSearchService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/BatchSearchService!com.ibd.businessViews.IBatchSearchService");
//                         
//            return service;
//      } 
//       else
//   {
       IBatchSearchService service=(IBatchSearchService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/BatchSearchService!com.ibd.businessViews.IBatchSearchService");
                         
            return service;
       
      
//      }
}
  
  
  public IFeeSearchService getFeeSerachService(CohesiveSession session) throws NamingException{
  
//      if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
//      {
//          bridge();
//        IFeeSearchService service=(IFeeSearchService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/FeeSearchService!com.ibd.businessViews.IFeeSearchService");
//                         
//            return service;
//      } 
//       else
//   {
       IFeeSearchService service=(IFeeSearchService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/FeeSearchService!com.ibd.businessViews.IFeeSearchService");
                         
            return service;
       
      
//      }
}
  
  
  public INotificationSearchService getNotificationSerachService(CohesiveSession session) throws NamingException{
  
//      if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
//      {
//          bridge();
//        INotificationSearchService service=(INotificationSearchService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/NotificationSearchService!com.ibd.businessViews.INotificationSearchService");
//                         
//            return service;
//      } 
//       else
//   {
       INotificationSearchService service=(INotificationSearchService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/NotificationSearchService!com.ibd.businessViews.INotificationSearchService");
                         
            return service;
       
      
//      }
}
  
  public IOtherActivitySearchService getOtherActivitySerachService(CohesiveSession session) throws NamingException{
  
//      if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
//      {
//          bridge();
//        IOtherActivitySearchService service=(IOtherActivitySearchService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/OtherActivitySearchService!com.ibd.businessViews.IOtherActivitySearchService");
//                         
//            return service;
//      } 
//       else
//   {
       IOtherActivitySearchService service=(IOtherActivitySearchService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/OtherActivitySearchService!com.ibd.businessViews.IOtherActivitySearchService");
                         
            return service;
       
      
//      }
}
  
  public IPaymentSearchService getPaymentSerachService(CohesiveSession session) throws NamingException{
  
//      if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
//      {
//          bridge();
//        IPaymentSearchService service=(IPaymentSearchService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/PaymentSearchService!com.ibd.businessViews.IPaymentSearchService");
//                         
//            return service;
//      } 
//       else
//   {
       IPaymentSearchService service=(IPaymentSearchService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/PaymentSearchService!com.ibd.businessViews.IPaymentSearchService");
                         
            return service;
       
      
//      }
}
  
  public IRoleSearchService getRoleSerachService(CohesiveSession session) throws NamingException{
  
//      if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
//      {
//          bridge();
//        IRoleSearchService service=(IRoleSearchService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/RoleSearchService!com.ibd.businessViews.IRoleSearchService");
//                         
//            return service;
//      } 
//       else
//   {
       IRoleSearchService service=(IRoleSearchService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/RoleSearchService!com.ibd.businessViews.IRoleSearchService");
                         
            return service;
       
      
//      }
}
  
  public IStudentSearchService getStudentSerachService(CohesiveSession session) throws NamingException{
//  
//      if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
//      {
//          bridge();
//        IStudentSearchService service=(IStudentSearchService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/StudentSearchService!com.ibd.businessViews.IStudentSearchService");
//                         
//            return service;
//      } 
//       else
//   {
       IStudentSearchService service=(IStudentSearchService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/StudentSearchService!com.ibd.businessViews.IStudentSearchService");
                         
            return service;
       
      
//      }
}
  
  public ITeacherSearchService getTeacherSerachService(CohesiveSession session) throws NamingException{
  
//      if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
//      {
//          bridge();
//        ITeacherSearchService service=(ITeacherSearchService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/TeacherSearchService!com.ibd.businessViews.ITeacherSearchService");
//                         
//            return service;
//      } 
//       else
//   {
       ITeacherSearchService service=(ITeacherSearchService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/TeacherSearchService!com.ibd.businessViews.ITeacherSearchService");
                         
            return service;
       
      
//      }
}
  
  public IUserSearchService getUserSerachService(CohesiveSession session) throws NamingException{
  
//      if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
//      {
//          bridge();
//        IUserSearchService service=(IUserSearchService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/UserSearchService!com.ibd.businessViews.IUserSearchService");
//                         
//            return service;
//      } 
//       else
//   {
       IUserSearchService service=(IUserSearchService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/UserSearchService!com.ibd.businessViews.IUserSearchService");
                         
            return service;
       
      
//      }
}
  
  public IManagementDashBoardService getManagementDashBoardService(CohesiveSession session) throws NamingException{
  
      if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
      {
          bridge();
        IManagementDashBoardService service=(IManagementDashBoardService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/ManagementDashBoardService!com.ibd.businessViews.IManagementDashBoardService");
                         
            return service;
      } 
       else
   {
       IManagementDashBoardService service=(IManagementDashBoardService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ManagementDashBoardService!com.ibd.businessViews.IManagementDashBoardService");
                         
            return service;
       
      
      }
}
  
  public IParentDashBoardService getParentDashBoardService(CohesiveSession session) throws NamingException{
  
      if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
      {
          bridge();
        IParentDashBoardService service=(IParentDashBoardService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/ParentDashBoardService!com.ibd.businessViews.IParentDashBoardService");
                         
            return service;
      } 
       else
   {
       IParentDashBoardService service=(IParentDashBoardService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ParentDashBoardService!com.ibd.businessViews.IParentDashBoardService");
                         
            return service;
       
      
      }
}
  
  public ITeacherDashBoardService getTeacherDashBoardService(CohesiveSession session) throws NamingException{
  
      if (session.getCohesiveproperties().getProperty("QUERY_FROM_REPORT").equals("YES"))
      {
          bridge();
        ITeacherDashBoardService service=(ITeacherDashBoardService) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/TeacherDashBoardService!com.ibd.businessViews.ITeacherDashBoardService");
                         
            return service;
      } 
       else
   {
       ITeacherDashBoardService service=(ITeacherDashBoardService) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/TeacherDashBoardService!com.ibd.businessViews.ITeacherDashBoardService");
                         
            return service;
       
      
      }
}
  
  private void bridge() throws NamingException
{
    IResourceTokenBridge appBridge=(IResourceTokenBridge) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/ResourceTokenBridge!com.ibd.businessViews.IResourceTokenBridge");
    IResourceTokenBridge reportBridge=(IResourceTokenBridge) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/ResourceTokenBridge!com.ibd.businessViews.IResourceTokenBridge");
    reportBridge.setKey(appBridge.getKey());
    reportBridge.setSharedSecret(appBridge.getSharedSecret());
    IAuthTokenBridge appBridge1=(IAuthTokenBridge) contxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/AuthTokenBridge!com.ibd.businessViews.IAuthTokenBridge");
    IAuthTokenBridge reportBridge1=(IAuthTokenBridge) contxt.lookup("ejb:CohesiveReportEngine/CohesiveBusiness-ejb/AuthTokenBridge!com.ibd.businessViews.IAuthTokenBridge");
    reportBridge1.setKey(appBridge1.getKey());
    reportBridge1.setSharedSecret(appBridge1.getSharedSecret());
        
}
  
}
