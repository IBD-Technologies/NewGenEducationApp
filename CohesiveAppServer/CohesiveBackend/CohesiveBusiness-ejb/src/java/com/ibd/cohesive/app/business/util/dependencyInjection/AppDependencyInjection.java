/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.util.dependencyInjection;

import com.ibd.businessViews.IAmazonEmailService;
import com.ibd.businessViews.IAmazonSMSService;
import com.ibd.businessViews.IHolidayMaintanenceService;
import com.ibd.businessViews.IStudentAssignmentService;
import com.ibd.businessViews.IStudentAttendanceService;
import com.ibd.businessViews.IStudentCalenderService;
import com.ibd.businessViews.IStudentECircularService;
import com.ibd.businessViews.IStudentExamScheduleService;
import com.ibd.businessViews.IStudentFeeManagementService;
import com.ibd.businessViews.IStudentMasterService;
import com.ibd.businessViews.IStudentNotificationService;
import com.ibd.businessViews.IStudentOtherActivityService;
import com.ibd.businessViews.IStudentProgressCardService;
import com.ibd.businessViews.IStudentTimeTableService;
import com.ibd.businessViews.ITeacherAttendanceService;
import com.ibd.businessViews.ITeacherMasterService;
import com.ibd.businessViews.ITokenValidationService;
import com.ibd.businessViews.IUserProfileService;
import com.ibd.cohesive.app.Oauth.AuthServer.IAuthTokenProvider;
import com.ibd.cohesive.app.Oauth.AuthServer.ISecurityManagementService;
import com.ibd.cohesive.app.Oauth.AuthServer.TokenValidateService;
import com.ibd.cohesive.app.Oauth.ResourceServer.IResourceTokenProvider;
import com.ibd.cohesive.app.business.batch.IApplicationEODWrapper;
import com.ibd.cohesive.app.business.batch.IInstituteEODWrapper;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.util.message.request.Parsing;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.JsonUtil;
import com.ibd.cohesive.app.business.util.exception.ExceptionHandler;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.core.IDBCoreService;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.core.relationship.RelationshipService; //// EJB Integration change 
import com.ibd.cohesive.db.index.IndexCoreService; // EJB Integration change
import com.ibd.cohesive.db.index.IndexReadService;
import com.ibd.cohesive.db.read.IDBReadService;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.lock.ILockService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.session.CohesiveSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import com.ibd.cohesive.app.business.batch.IAssignmentBatchProcessing;
import com.ibd.cohesive.app.business.batch.IAssignmentProcessing;
import com.ibd.cohesive.app.business.batch.IOtherActivityBatchProcessing;
import com.ibd.cohesive.app.business.batch.IOtherActivityProcessing;
import com.ibd.cohesive.app.business.batch.IStudentAssignmentProcessing;
import com.ibd.cohesive.app.business.batch.IStudentOtherActivityProcessing;
import com.ibd.cohesive.app.business.batch.archival.assignment.IInstituteAssignmentBatchProcessing;
import com.ibd.cohesive.app.business.batch.attendance.IAttendanceBatchProcessing;
import com.ibd.cohesive.app.business.batch.attendance.IClassAttendanceProcessing;
import com.ibd.cohesive.app.business.batch.attendance.IStudentAttendanceProcessing;
import com.ibd.cohesive.app.business.batch.exam.IClassExamProcessing;
import com.ibd.cohesive.app.business.batch.exam.IExamBatchProcessing;
import com.ibd.cohesive.app.business.batch.exam.IStudentExamProcessing;
import com.ibd.cohesive.app.business.batch.fee.IFeeBatchProcessing;
import com.ibd.cohesive.app.business.batch.fee.IFeeProcessing;
import com.ibd.cohesive.app.business.batch.fee.IStudentFeeProcessing;
import com.ibd.cohesive.app.business.batch.mark.IClassMarkProcessing;
import com.ibd.cohesive.app.business.batch.mark.IMarkBatchProcessing;
import com.ibd.cohesive.app.business.batch.mark.IStudentMarkProcessing;
import com.ibd.cohesive.app.business.batch.notification.INotificationBatchProcessing;
import com.ibd.cohesive.app.business.batch.notification.INotificationProcessing;
import com.ibd.cohesive.app.business.batch.notification.IStudentNotificationProcessing;
import com.ibd.cohesive.app.business.batch.timetable.IClassTimeTableProcessing;
import com.ibd.cohesive.app.business.batch.timetable.IStudentTimeTableProcessing;
import com.ibd.cohesive.app.business.batch.timetable.ITimeTableBatchProcessing;
import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.batch.archival.assignment.ISAssignmentArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.assignment.ISAssignmentArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.attendance.ICAttendanceArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.attendance.ICAttendanceArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.attendance.ISAttendanceArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.attendance.ISAttendanceArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.attendance.ITAttendanceArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.attendance.ITAttendanceArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.calender.ISCalenderArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.calender.ISCalenderArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.calender.ITCalenderArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.calender.ITCalenderArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.exam.ICExamArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.exam.ICExamArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.exam.ISExamArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.exam.ISExamArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.fee.IInsFeeArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.fee.ISFeeArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.fee.ISFeeArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.leave.ISLeaveArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.leave.ISLeaveArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.leave.ITLeaveArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.leave.ITLeaveArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.mark.ICMarkArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.mark.ICMarkArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.mark.ISMarkArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.mark.ISMarkArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.notification.IInsNotificationArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.notification.ISNotificationArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.notification.ISNotificationArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.otheractivity.IInsOtherActivityArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.otheractivity.ISOtherActivityArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.otheractivity.ISOtherActivityArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.payment.IInsPaymentArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.payment.ISPaymentArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.payment.ISPaymentArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.payroll.ITPayrollArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.payroll.ITPayrollArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.profile.ISProfileArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.profile.ISProfileArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.profile.ITProfileArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.profile.ITProfileArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.profile.IUProfileArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.profile.IUProfileArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.timetable.ICTimeTableArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.timetable.ICTimeTableArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.timetable.ISTimeTableArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.timetable.ISTimeTableArchProcessing;
import com.ibd.cohesive.app.business.batch.archival.timetable.ITTimeTableArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.timetable.ITTimeTableArchProcessing;
import com.ibd.cohesive.app.business.batch.archivalRecovery.IArchivalRecoveryBatchProcessing;
import com.ibd.cohesive.app.business.batch.archivalRecovery.IArchivalRecoveryProcessing;
import com.ibd.cohesive.app.business.batch.defragmentation.IDefragmentationBatchProcessing;
import com.ibd.cohesive.app.business.batch.defragmentation.IFileDefragmentationProcessing;
import com.ibd.cohesive.app.business.batch.eCircular.IECircularBatchProcessing;
import com.ibd.cohesive.app.business.batch.eCircular.IECircularProcessing;
import com.ibd.cohesive.app.business.batch.eCircular.IStudentECircularProcessing;
import com.ibd.cohesive.app.business.batch.eCircular.ITeacherECircularProcessing;
import com.ibd.cohesive.app.business.batch.eventNotification.IEventNotificationBatchProcessing;
import com.ibd.cohesive.app.business.batch.eventNotification.IStudentEventNotificationProcessing;
import com.ibd.cohesive.app.business.batch.feeNotification.IFeeNotificationBatchProcessing;
import com.ibd.cohesive.app.business.batch.feeNotification.IFeeNotificationProcessing;
import com.ibd.cohesive.app.business.batch.feeNotification.IStudentFeeNotificationProcessing;
import com.ibd.cohesive.app.business.batch.unAuth.IUnAuthBatchProcessing;
import com.ibd.cohesive.app.business.classentity.classattendance.IAttendanceNotificationService;
import com.ibd.cohesive.app.business.notification.INotificationTransferService;
import com.ibd.cohesive.app.business.util.NotificationUtil;

/**
 *
 * @author IBD Technologies
 */
public class AppDependencyInjection {
    
    private InitialContext contxt;
    private BSValidation bsv;
    private BusinessService bs;
    private JsonUtil jsonUtil;
    private Parsing parser;
    private ExceptionHandler exhandle;
    private BatchUtil batchUtil;
    private NotificationUtil notificationUtil;

   

  
    
    

    public JsonUtil getJsonUtil() {
        return jsonUtil;
    }
      
    public AppDependencyInjection() throws NamingException  {
        
        contxt = new InitialContext();
        if (bsv == null) {
                bsv = new BSValidation();
            }if(bs==null){
                bs=new BusinessService();
               // bs.setI_db_properties(i_db_properties);
                   }
        if(jsonUtil==null)
         jsonUtil=new JsonUtil();   
        if(parser==null) 
         parser=new Parsing();
        if (exhandle==null)
         exhandle=new ExceptionHandler();     
        if(batchUtil==null)
            batchUtil=new BatchUtil();
        if(notificationUtil==null)
            notificationUtil=new NotificationUtil();
    }
    public BSValidation getBsv(CohesiveSession session){
        bsv.setDebug(session.getDebug());
        bsv.setI_db_properties(session.getCohesiveproperties());
        return bsv;
        
    }
    public BusinessService getBusinessService(CohesiveSession session){
        bs.setI_db_properties(session.getCohesiveproperties());
        bs.setDebug(session.getDebug());
        return bs;
    }
     public NotificationUtil getNotificationUtil(CohesiveSession session) {
         
         notificationUtil.setDebug(session.getDebug());
         notificationUtil.setI_db_properties(session.getCohesiveproperties());
        return notificationUtil;
    }
   
    public Parsing getParser(CohesiveSession session){
        parser.setI_db_properties(session.getCohesiveproperties());
        parser.setDebug(session.getDebug());
        return parser;
    }
    
    public ExceptionHandler getErrorHandle(CohesiveSession session){
        exhandle.setDebug(session.getDebug());
        return exhandle;
        
    }

    public BatchUtil getBatchUtil(CohesiveSession session) {
        batchUtil.setI_db_properties(session.getCohesiveproperties());
        batchUtil.setDebug(session.getDebug());
        return batchUtil;
    }

    public void setBatchUtil(BatchUtil batchUtil) {
        this.batchUtil = batchUtil;
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
    public IDBTransactionService getDBTransactionService() throws NamingException{ //EJB Integration change
      IDBTransactionService dbts = (IDBTransactionService)
         contxt.lookup("java:app/CohesiveDatabase/DBTransactionService!com.ibd.cohesive.db.transaction.IDBTransactionService");
        
        return dbts;
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

    public RelationshipService getRelationshipService() throws NamingException{ //EJB Integration change
        RelationshipService relationshipservice=new RelationshipService(); //EJB Integration change
        return relationshipservice;
    }

    public ILockService getLockService() throws NamingException{ //EJB Integration change
      
        //LockService lockservice=dbcoreservice.getI_lockService();
        ILockService lockservice = (ILockService)
         contxt.lookup("java:app/CohesiveDatabase/LockService!com.ibd.cohesive.db.transaction.lock.ILockService");
        return lockservice;
        
    }
    public ITransactionControlService getTransactionControlService() throws NamingException{ //EJB Integration change
    
        ITransactionControlService TransactionControl = (ITransactionControlService)
         contxt.lookup("java:app/CohesiveDatabase/TransactionControlService!com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService");
        return TransactionControl;
        
    }
    public IStudentMasterService getStudentMasterService() throws NamingException { //EJB Integration change
      
      IStudentMasterService studentMaster = (IStudentMasterService)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentMasterService!com.ibd.businessViews.IStudentMasterService");
//         contxt.lookup("java:app/CohesiveBusiness-ejb/StudentMasterService!com.ibd.cohesive.app.business.institute.studentmasterservice.IStudentMasterService");
      return studentMaster;
    }
    public IDBReadBufferService getDBReadBufferService() throws NamingException{ //EJB Integration change
    
        IDBReadBufferService readBuffer = (IDBReadBufferService)
         contxt.lookup("java:app/CohesiveDatabase/DBReadBufferService!com.ibd.cohesive.db.readbuffer.IDBReadBufferService");
        return readBuffer;
        
    }
    public IUserProfileService getUserProfileService() throws NamingException{
        IUserProfileService userProfile = (IUserProfileService)
         contxt.lookup("java:app/CohesiveDatabase/UserProfileService!com.ibd.cohesive.app.business.user.userprofile.IUserProfileService");
        return userProfile;
    }
    
     public IStudentCalenderService getStudentCalenderService() throws NamingException { //EJB Integration change
      
      IStudentCalenderService studentCalender = (IStudentCalenderService)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentCalenderService!com.ibd.businessViews.IStudentCalenderService");
//         contxt.lookup("java:app/CohesiveBusiness-ejb/StudentMasterService!com.ibd.cohesive.app.business.institute.studentmasterservice.IStudentMasterService");
      return studentCalender;
    }
     
      public IBusinessLockService getBusinessLockService() throws NamingException{ //EJB Integration change
    
        IBusinessLockService businessLock = (IBusinessLockService)
         contxt.lookup("java:app/CohesiveBusiness-ejb/BusinessLockService!com.ibd.cohesive.app.business.lock.IBusinessLockService");
        return businessLock;
        
    }
       public ITeacherMasterService getTeacherMasterService() throws NamingException { //EJB Integration change
      
      ITeacherMasterService teacherMaster = (ITeacherMasterService)
              contxt.lookup("java:app/CohesiveBusiness-ejb/TeacherMasterService!com.ibd.businessViews.ITeacherMasterService");
      return teacherMaster;
    }
       
       public ITeacherAttendanceService getTeacherAttendanceService() throws NamingException { //EJB Integration change
      
      ITeacherAttendanceService teacherAttendance = (ITeacherAttendanceService)
              contxt.lookup("java:app/CohesiveBusiness-ejb/TeacherAttendanceService!com.ibd.businessViews.ITeacherAttendanceService");
      return teacherAttendance;
    }
    public IStudentTimeTableService getStudentTimeTableService() throws NamingException { //EJB Integration change
      
      IStudentTimeTableService studentTimeTable = (IStudentTimeTableService)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentTimeTableService!com.ibd.businessViews.IStudentTimeTableService");
      return studentTimeTable;
    }
    public IInstituteEODWrapper getInstituteEODWrapper() throws NamingException { //EJB Integration change
      
      IInstituteEODWrapper instituteWrapper = (IInstituteEODWrapper)
              contxt.lookup("java:app/CohesiveBusiness-ejb/InstituteEODWrapper!com.ibd.cohesive.app.business.batch.IInstituteEODWrapper");
      return instituteWrapper;
    }
    public IAssignmentBatchProcessing getAssignmentBatchProcessing() throws NamingException { //EJB Integration change
      
      IAssignmentBatchProcessing batchProcessing = (IAssignmentBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/AssignmentBatchProcessing!com.ibd.cohesive.app.business.batch.IAssignmentBatchProcessing");
      return batchProcessing;
    }
    
    public IAssignmentProcessing getAssignmentProcessing() throws NamingException { //EJB Integration change
      
      IAssignmentProcessing assignmentProcessing = (IAssignmentProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/AssignmentProcessing!com.ibd.cohesive.app.business.batch.IAssignmentProcessing");
      return assignmentProcessing;
    }
    
    public IStudentAssignmentService getStudentAssignmentService() throws NamingException { //EJB Integration change
      
      IStudentAssignmentService studentAssignment = (IStudentAssignmentService)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentAssignmentService!com.ibd.businessViews.IStudentAssignmentService");
      return studentAssignment;
    }
    public IStudentAssignmentProcessing getStudentAssignmentProcessing() throws NamingException { //EJB Integration change
      
      IStudentAssignmentProcessing assignmentProcessing = (IStudentAssignmentProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentAssignmentProcessing!com.ibd.cohesive.app.business.batch.IStudentAssignmentProcessing");
      return assignmentProcessing;
    }
    public IECircularBatchProcessing getECircularBatchProcessing() throws NamingException { //EJB Integration change
      
      IECircularBatchProcessing batchProcessing = (IECircularBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/ECircularBatchProcessing!com.ibd.cohesive.app.business.batch.eCircular.IECircularBatchProcessing");
      return batchProcessing;
    }
    
    public IECircularProcessing getECircularProcessing() throws NamingException { //EJB Integration change
      
      IECircularProcessing eCircularProcessing = (IECircularProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/ECircularProcessing!com.ibd.cohesive.app.business.batch.eCircular.IECircularProcessing");
      return eCircularProcessing;
    }
    
    public IStudentECircularService getStudentECircularService() throws NamingException { //EJB Integration change
      
      IStudentECircularService studentECircular = (IStudentECircularService)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentECircularService!com.ibd.businessViews.IStudentECircularService");
      return studentECircular;
    }
    public IStudentECircularProcessing getStudentECircularProcessing() throws NamingException { //EJB Integration change
      
      IStudentECircularProcessing eCircularProcessing = (IStudentECircularProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentECircularProcessing!com.ibd.cohesive.app.business.batch.eCircular.IStudentECircularProcessing");
      return eCircularProcessing;
    }
    public ITeacherECircularProcessing getTeacherECircularProcessing() throws NamingException { //EJB Integration change
      
      ITeacherECircularProcessing eCircularProcessing = (ITeacherECircularProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/TeacherECircularProcessing!com.ibd.cohesive.app.business.batch.eCircular.ITeacherECircularProcessing");
      return eCircularProcessing;
    }
         public ISOtherActivityArchBatchProcessing getStudentOtherActivityArchBatch() throws NamingException { //EJB Integration change
      
      ISOtherActivityArchBatchProcessing studentOtherActivity = (ISOtherActivityArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SOtherActivityArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.otheractivity.ISOtherActivityArchBatchProcessing");
      return studentOtherActivity;
    }
     public IInsOtherActivityArchBatchProcessing getInstituteOtherActivityBatch() throws NamingException { //EJB Integration change
      
      IInsOtherActivityArchBatchProcessing instituteOtherActivity = (IInsOtherActivityArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/InsOtherActivityArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.otheractivity.IInsOtherActivityArchBatchProcessing");
      return instituteOtherActivity;
    }
     public ISOtherActivityArchProcessing getStudentOtherActivityArchProcessing() throws NamingException { //EJB Integration change
      
      ISOtherActivityArchProcessing studentOtherActivity = (ISOtherActivityArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SOtherActivityArchProcessing!com.ibd.cohesive.app.business.batch.archival.otheractivity.ISOtherActivityArchProcessing");
      return studentOtherActivity;
    }
     
    public ISFeeArchBatchProcessing getStudentFeeArchBatch() throws NamingException { //EJB Integration change
      
      ISFeeArchBatchProcessing studentFee = (ISFeeArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SFeeArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.fee.ISFeeArchBatchProcessing");
      return studentFee;
    }
     public IInsFeeArchBatchProcessing getInstituteFeeBatch() throws NamingException { //EJB Integration change
      
      IInsFeeArchBatchProcessing instituteFee = (IInsFeeArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/InsFeeArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.fee.IInsFeeArchBatchProcessing");
      return instituteFee;
    }
     public ISFeeArchProcessing getStudentFeeArchProcessing() throws NamingException { //EJB Integration change
      
      ISFeeArchProcessing studentFee = (ISFeeArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SFeeArchProcessing!com.ibd.cohesive.app.business.batch.archival.fee.ISFeeArchProcessing");
      return studentFee;
    } 
     public ISNotificationArchBatchProcessing getStudentNotificationArchBatch() throws NamingException { //EJB Integration change
      
      ISNotificationArchBatchProcessing studentNotification = (ISNotificationArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SNotificationArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.notification.ISNotificationArchBatchProcessing");
      return studentNotification;
    }
     public IInsNotificationArchBatchProcessing getInstituteNotificationBatch() throws NamingException { //EJB Integration change
      
      IInsNotificationArchBatchProcessing instituteNotification = (IInsNotificationArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/InsNotificationArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.notification.IInsNotificationArchBatchProcessing");
      return instituteNotification;
    }
     public ISNotificationArchProcessing getStudentNotificationArchProcessing() throws NamingException { //EJB Integration change
      
      ISNotificationArchProcessing studentNotification = (ISNotificationArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SNotificationArchProcessing!com.ibd.cohesive.app.business.batch.archival.notification.ISNotificationArchProcessing");
      return studentNotification;
    } 
      public ISMarkArchBatchProcessing getStudentMarkArchBatch() throws NamingException { //EJB Integration change
      
      ISMarkArchBatchProcessing studentMark = (ISMarkArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SMarkArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.mark.ISMarkArchBatchProcessing");
      return studentMark;
    }
	
	public ISMarkArchProcessing getStudentMarkArchProcessing() throws NamingException { //EJB Integration change
      
      ISMarkArchProcessing studentMark = (ISMarkArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SMarkArchProcessing!com.ibd.cohesive.app.business.batch.archival.mark.ISMarkArchProcessing");
      return studentMark;
    } 
        
    public ICMarkArchBatchProcessing getClassMarkArchBatch() throws NamingException { //EJB Integration change
      
      ICMarkArchBatchProcessing classMark = (ICMarkArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/CMarkArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.mark.ICMarkArchBatchProcessing");
      return classMark;
    }
	
	public ICMarkArchProcessing getClassMarkArchProcessing() throws NamingException { //EJB Integration change
      
      ICMarkArchProcessing classMark = (ICMarkArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/CMarkArchProcessing!com.ibd.cohesive.app.business.batch.archival.mark.ICMarkArchProcessing");
      return classMark;
    }
        
    public ISExamArchBatchProcessing getStudentExamArchBatch() throws NamingException { //EJB Integration change
      
      ISExamArchBatchProcessing studentExam = (ISExamArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SExamArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.exam.ISExamArchBatchProcessing");
      return studentExam;
    }
	
	public ISExamArchProcessing getStudentExamArchProcessing() throws NamingException { //EJB Integration change
      
      ISExamArchProcessing studentExam = (ISExamArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SExamArchProcessing!com.ibd.cohesive.app.business.batch.archival.exam.ISExamArchProcessing");
      return studentExam;
    } 
        
    public ICExamArchBatchProcessing getClassExamArchBatch() throws NamingException { //EJB Integration change
      
      ICExamArchBatchProcessing classExam = (ICExamArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/CExamArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.exam.ICExamArchBatchProcessing");
      return classExam;
    }
	
	public ICExamArchProcessing getClassExamArchProcessing() throws NamingException { //EJB Integration change
      
      ICExamArchProcessing classExam = (ICExamArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/CExamArchProcessing!com.ibd.cohesive.app.business.batch.archival.exam.ICExamArchProcessing");
      return classExam;
    }    
        
   public ISTimeTableArchBatchProcessing getStudentTimeTableArchBatch() throws NamingException { //EJB Integration change
      
      ISTimeTableArchBatchProcessing studentTimeTable = (ISTimeTableArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/STimeTableArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.timetable.ISTimeTableArchBatchProcessing");
      return studentTimeTable;
    }
	
	public ISTimeTableArchProcessing getStudentTimeTableArchProcessing() throws NamingException { //EJB Integration change
      
      ISTimeTableArchProcessing studentTimeTable = (ISTimeTableArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/STimeTableArchProcessing!com.ibd.cohesive.app.business.batch.archival.timetable.ISTimeTableArchProcessing");
      return studentTimeTable;
    } 
        
    public ICTimeTableArchBatchProcessing getClassTimeTableArchBatch() throws NamingException { //EJB Integration change
      
      ICTimeTableArchBatchProcessing classTimeTable = (ICTimeTableArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/CTimeTableArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.timetable.ICTimeTableArchBatchProcessing");
      return classTimeTable;
    }
	
	public ICTimeTableArchProcessing getClassTimeTableArchProcessing() throws NamingException { //EJB Integration change
      
      ICTimeTableArchProcessing classTimeTable = (ICTimeTableArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/CTimeTableArchProcessing!com.ibd.cohesive.app.business.batch.archival.timetable.ICTimeTableArchProcessing");
      return classTimeTable;
    }     
    public ISAttendanceArchBatchProcessing getStudentAttendanceArchBatch() throws NamingException { //EJB Integration change
      
      ISAttendanceArchBatchProcessing studentAttendance = (ISAttendanceArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SAttendanceArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.attendance.ISAttendanceArchBatchProcessing");
      return studentAttendance;
    }
	
	public ISAttendanceArchProcessing getStudentAttendanceArchProcessing() throws NamingException { //EJB Integration change
      
      ISAttendanceArchProcessing studentAttendance = (ISAttendanceArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SAttendanceArchProcessing!com.ibd.cohesive.app.business.batch.archival.attendance.ISAttendanceArchProcessing");
      return studentAttendance;
    } 
        
    public ICAttendanceArchBatchProcessing getClassAttendanceArchBatch() throws NamingException { //EJB Integration change
      
      ICAttendanceArchBatchProcessing classAttendance = (ICAttendanceArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/CAttendanceArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.attendance.ICAttendanceArchBatchProcessing");
      return classAttendance;
    }
	
	public ICAttendanceArchProcessing getClassAttendanceArchProcessing() throws NamingException { //EJB Integration change
      
      ICAttendanceArchProcessing classAttendance = (ICAttendanceArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/CAttendanceArchProcessing!com.ibd.cohesive.app.business.batch.archival.attendance.ICAttendanceArchProcessing");
      return classAttendance;
    } 
        
    public ISLeaveArchBatchProcessing getStudentLeaveArchBatch() throws NamingException { //EJB Integration change
      
      ISLeaveArchBatchProcessing studentLeave = (ISLeaveArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SLeaveArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.leave.ISLeaveArchBatchProcessing");
      return studentLeave;
    }
	
	public ISLeaveArchProcessing getStudentLeaveArchProcessing() throws NamingException { //EJB Integration change
      
      ISLeaveArchProcessing studentLeave = (ISLeaveArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SLeaveArchProcessing!com.ibd.cohesive.app.business.batch.archival.leave.ISLeaveArchProcessing");
      return studentLeave;
    } 
        
    public ITLeaveArchBatchProcessing getTeacherLeaveArchBatch() throws NamingException { //EJB Integration change
      
      ITLeaveArchBatchProcessing teacherLeave = (ITLeaveArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/TLeaveArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.leave.ITLeaveArchBatchProcessing");
      return teacherLeave;
    }
	
	public ITLeaveArchProcessing getTeacherLeaveArchProcessing() throws NamingException { //EJB Integration change
      
      ITLeaveArchProcessing teacherLeave = (ITLeaveArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/TLeaveArchProcessing!com.ibd.cohesive.app.business.batch.archival.leave.ITLeaveArchProcessing");
      return teacherLeave;
    }     
         public ISCalenderArchBatchProcessing getStudentCalenderArchBatch() throws NamingException { //EJB Integration change
      
      ISCalenderArchBatchProcessing studentCalender = (ISCalenderArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SCalenderArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.calender.ISCalenderArchBatchProcessing");
      return studentCalender;
    }
	
	public ISCalenderArchProcessing getStudentCalenderArchProcessing() throws NamingException { //EJB Integration change
      
      ISCalenderArchProcessing studentCalender = (ISCalenderArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SCalenderArchProcessing!com.ibd.cohesive.app.business.batch.archival.calender.ISCalenderArchProcessing");
      return studentCalender;
    } 
        
    public ITCalenderArchBatchProcessing getTeacherCalenderArchBatch() throws NamingException { //EJB Integration change
      
      ITCalenderArchBatchProcessing teacherCalender = (ITCalenderArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/TCalenderArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.calender.ITCalenderArchBatchProcessing");
      return teacherCalender;
    }
	
	public ITCalenderArchProcessing getTeacherCalenderArchProcessing() throws NamingException { //EJB Integration change
      
      ITCalenderArchProcessing teacherCalender = (ITCalenderArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/TCalenderArchProcessing!com.ibd.cohesive.app.business.batch.archival.calender.ITCalenderArchProcessing");
      return teacherCalender;
    }     
     public ITPayrollArchBatchProcessing getTeacherPayrollArchBatch() throws NamingException { //EJB Integration change
      
      ITPayrollArchBatchProcessing teacherPayroll = (ITPayrollArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/TPayrollArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.payroll.ITPayrollArchBatchProcessing");
      return teacherPayroll;
    }
	
	public ITPayrollArchProcessing getTeacherPayrollArchProcessing() throws NamingException { //EJB Integration change
      
      ITPayrollArchProcessing teacherPayroll = (ITPayrollArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/TPayrollArchProcessing!com.ibd.cohesive.app.business.batch.archival.payroll.ITPayrollArchProcessing");
      return teacherPayroll;
    }     
        
    public ISPaymentArchBatchProcessing getStudentPaymentArchBatch() throws NamingException { //EJB Integration change
      
      ISPaymentArchBatchProcessing studentPayment = (ISPaymentArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SPaymentArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.payment.ISPaymentArchBatchProcessing");
      return studentPayment;
    }
     public IInsPaymentArchBatchProcessing getInstitutePaymentBatch() throws NamingException { //EJB Integration change
      
      IInsPaymentArchBatchProcessing institutePayment = (IInsPaymentArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/InsPaymentArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.payment.IInsPaymentArchBatchProcessing");
      return institutePayment;
    }
     public ISPaymentArchProcessing getStudentPaymentArchProcessing() throws NamingException { //EJB Integration change
      
      ISPaymentArchProcessing studentPayment = (ISPaymentArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SPaymentArchProcessing!com.ibd.cohesive.app.business.batch.archival.payment.ISPaymentArchProcessing");
      return studentPayment;
    }  
     public ITAttendanceArchBatchProcessing getTeacherAttendanceArchBatch() throws NamingException { //EJB Integration change
      
      ITAttendanceArchBatchProcessing teacherAttendance = (ITAttendanceArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/TAttendanceArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.attendance.ITAttendanceArchBatchProcessing");
      return teacherAttendance;
    }
	
	public ITAttendanceArchProcessing getTeacherAttendanceArchProcessing() throws NamingException { //EJB Integration change
      
      ITAttendanceArchProcessing teacherAttendance = (ITAttendanceArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/TAttendanceArchProcessing!com.ibd.cohesive.app.business.batch.archival.attendance.ITAttendanceArchProcessing");
      return teacherAttendance;
    } 
    public ITTimeTableArchBatchProcessing getTeacherTimeTableArchBatch() throws NamingException { //EJB Integration change
      
      ITTimeTableArchBatchProcessing teacherTimeTable = (ITTimeTableArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/TTimeTableArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.timetable.ITTimeTableArchBatchProcessing");
      return teacherTimeTable;
    }
	
	public ITTimeTableArchProcessing getTeacherTimeTableArchProcessing() throws NamingException { //EJB Integration change
      
      ITTimeTableArchProcessing teacherTimeTable = (ITTimeTableArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/TTimeTableArchProcessing!com.ibd.cohesive.app.business.batch.archival.timetable.ITTimeTableArchProcessing");
      return teacherTimeTable;
    } 
        
    public ISProfileArchBatchProcessing getStudentProfileArchBatch() throws NamingException { //EJB Integration change
      
      ISProfileArchBatchProcessing studentProfile = (ISProfileArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SProfileArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.profile.ISProfileArchBatchProcessing");
      return studentProfile;
    }
	
    public ISProfileArchProcessing getStudentProfileArchProcessing() throws NamingException { //EJB Integration change
      
      ISProfileArchProcessing studentProfile = (ISProfileArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SProfileArchProcessing!com.ibd.cohesive.app.business.batch.archival.profile.ISProfileArchProcessing");
      return studentProfile;
    } 
        
    public ITProfileArchBatchProcessing getTeacherProfileArchBatch() throws NamingException { //EJB Integration change
      
      ITProfileArchBatchProcessing teacherProfile = (ITProfileArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/TProfileArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.profile.ITProfileArchBatchProcessing");
      return teacherProfile;
    }
	
    public ITProfileArchProcessing getTeacherProfileArchProcessing() throws NamingException { //EJB Integration change
     
      ITProfileArchProcessing teacherProfile = (ITProfileArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/TProfileArchProcessing!com.ibd.cohesive.app.business.batch.archival.profile.ITProfileArchProcessing");
      return teacherProfile;
    } 
    public IUProfileArchBatchProcessing getUserProfileArchBatch() throws NamingException { //EJB Integration change
      
      IUProfileArchBatchProcessing userProfile = (IUProfileArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/UProfileArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.profile.IUProfileArchBatchProcessing");
      return userProfile;
    }
	
   public IUProfileArchProcessing getUserProfileArchProcessing() throws NamingException { //EJB Integration change
      
      IUProfileArchProcessing userProfile = (IUProfileArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/UProfileArchProcessing!com.ibd.cohesive.app.business.batch.archival.profile.IUProfileArchProcessing");
      return userProfile;
    } 
     public IDefragmentationBatchProcessing getDefragmentationBatch() throws NamingException { //EJB Integration change
      
      IDefragmentationBatchProcessing defragmentation = (IDefragmentationBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/DefragmentationBatchProcessing!com.ibd.cohesive.app.business.batch.defragmentation.IDefragmentationBatchProcessing");
      return defragmentation;
    }     
     
     public IFileDefragmentationProcessing getFileDefragmentationProcessing() throws NamingException { //EJB Integration change
      
      IFileDefragmentationProcessing defragmentation = (IFileDefragmentationProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/FileDefragmentationProcessing!com.ibd.cohesive.app.business.batch.defragmentation.IFileDefragmentationProcessing");
      return defragmentation;
    }  
             
     public IHolidayMaintanenceService getHolidayMaintanenceService() throws NamingException { //EJB Integration change
      
      IHolidayMaintanenceService holidayService = (IHolidayMaintanenceService)
              contxt.lookup("java:app/CohesiveBusiness-ejb/HolidayMaintanenceService!com.ibd.businessViews.IHolidayMaintanenceService");
      return holidayService;
    }
     public IArchivalRecoveryBatchProcessing getArchivalRecoveryBatch() throws NamingException { //EJB Integration change
      
      IArchivalRecoveryBatchProcessing archivalRecovery = (IArchivalRecoveryBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/ArchivalRecoveryBatchProcessing!com.ibd.cohesive.app.business.batch.archivalRecovery.IArchivalRecoveryBatchProcessing");
      return archivalRecovery;
    }     
     
     public IArchivalRecoveryProcessing getArchivalRecoveryProcessing() throws NamingException { //EJB Integration change
      
      IArchivalRecoveryProcessing archivalRecovery = (IArchivalRecoveryProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/ArchivalRecoveryProcessing!com.ibd.cohesive.app.business.batch.archivalRecovery.IArchivalRecoveryProcessing");
      return archivalRecovery;
    }

    public IApplicationEODWrapper getAppEODWrapper() throws NamingException { //EJB Integration change
      
      IApplicationEODWrapper appWrapper = (IApplicationEODWrapper)
              contxt.lookup("java:app/CohesiveBusiness-ejb/ApplicationEODWrapper!com.ibd.cohesive.app.business.batch.IApplicationEODWrapper");
      return appWrapper;
    }
    public IStudentOtherActivityProcessing getStudentOtherActivityProcessing() throws NamingException { //EJB Integration change
      
      IStudentOtherActivityProcessing activityProcessing = (IStudentOtherActivityProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentOtherActivityProcessing!com.ibd.cohesive.app.business.batch.IStudentOtherActivityProcessing");
      return activityProcessing;
    }
    public IStudentOtherActivityService getStudentOtherActivityService() throws NamingException { //EJB Integration change
      
      IStudentOtherActivityService studentOtherActivity = (IStudentOtherActivityService)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentOtherActivityService!com.ibd.businessViews.IStudentOtherActivityService");
      return studentOtherActivity;
    }
    public IOtherActivityBatchProcessing getOtherActivityBatchProcessing() throws NamingException { //EJB Integration change
      
      IOtherActivityBatchProcessing batchProcessing = (IOtherActivityBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/OtherActivityBatchProcessing!com.ibd.cohesive.app.business.batch.IOtherActivityBatchProcessing");
      return batchProcessing;
    }
    public IOtherActivityProcessing getOtherActivityProcessing() throws NamingException { //EJB Integration change
      
      IOtherActivityProcessing batchProcessing = (IOtherActivityProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/OtherActivityProcessing!com.ibd.cohesive.app.business.batch.IOtherActivityProcessing");
      return batchProcessing;
    }
     public IStudentFeeProcessing getStudentFeeProcessing() throws NamingException { //EJB Integration change
      
      IStudentFeeProcessing feeProcessing = (IStudentFeeProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentFeeProcessing!com.ibd.cohesive.app.business.batch.fee.IStudentFeeProcessing");
      return feeProcessing;
    }
     public IFeeNotificationBatchProcessing getFeeNotificationBatchProcessing() throws NamingException { //EJB Integration change
      
      IFeeNotificationBatchProcessing batchProcessing = (IFeeNotificationBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/FeeNotificationBatchProcessing!com.ibd.cohesive.app.business.batch.feeNotification.IFeeNotificationBatchProcessing");
      return batchProcessing;
    }
     public IEventNotificationBatchProcessing getEventNotificationBatchProcessing() throws NamingException { //EJB Integration change
      
      IEventNotificationBatchProcessing batchProcessing = (IEventNotificationBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/EventNotificationBatchProcessing!com.ibd.cohesive.app.business.batch.eventNotification.IEventNotificationBatchProcessing");
      return batchProcessing;
    }
    public IFeeNotificationProcessing getFeeNotificationProcessing() throws NamingException { //EJB Integration change
      
      IFeeNotificationProcessing feeProcessing = (IFeeNotificationProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/FeeNotificationProcessing!com.ibd.cohesive.app.business.batch.feeNotification.IFeeNotificationProcessing");
      return feeProcessing;
    }
	
	public IStudentFeeNotificationProcessing getStudentFeeNotificationProcessing() throws NamingException { //EJB Integration change
      
      IStudentFeeNotificationProcessing feeProcessing = (IStudentFeeNotificationProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentFeeNotificationProcessing!com.ibd.cohesive.app.business.batch.feeNotification.IStudentFeeNotificationProcessing");
      return feeProcessing;
    }
    public IStudentFeeManagementService getStudentFeeManagementService() throws NamingException { //EJB Integration change
      
      IStudentFeeManagementService studentFee = (IStudentFeeManagementService)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentFeeManagementService!com.ibd.businessViews.IStudentFeeManagementService");
      return studentFee;
    }
    public IFeeBatchProcessing getFeeBatchProcessing() throws NamingException { //EJB Integration change
      
      IFeeBatchProcessing batchProcessing = (IFeeBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/FeeBatchProcessing!com.ibd.cohesive.app.business.batch.fee.IFeeBatchProcessing");
      return batchProcessing;
    }
    public IFeeProcessing getFeeProcessing() throws NamingException { //EJB Integration change
      
      IFeeProcessing feeProcessing = (IFeeProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/FeeProcessing!com.ibd.cohesive.app.business.batch.fee.IFeeProcessing");
      return feeProcessing;
    }
     public IStudentNotificationProcessing getStudentNotificationProcessing() throws NamingException { //EJB Integration change
      
      IStudentNotificationProcessing notificationProcessing = (IStudentNotificationProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentNotificationProcessing!com.ibd.cohesive.app.business.batch.notification.IStudentNotificationProcessing");
      return notificationProcessing;
    }
      public IStudentEventNotificationProcessing getStudentEventNotificationProcessing() throws NamingException { //EJB Integration change
      
      IStudentEventNotificationProcessing notificationProcessing = (IStudentEventNotificationProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentEventNotificationProcessing!com.ibd.cohesive.app.business.batch.eventNotification.IStudentEventNotificationProcessing");
      return notificationProcessing;
    }
    public IStudentNotificationService getStudentNotificationService() throws NamingException { //EJB Integration change
      
      IStudentNotificationService studentNotification = (IStudentNotificationService)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentNotificationService!com.ibd.businessViews.IStudentNotificationService");
      return studentNotification;
    }
    public INotificationBatchProcessing getNotificationBatchProcessing() throws NamingException { //EJB Integration change
      
      INotificationBatchProcessing batchProcessing = (INotificationBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/NotificationBatchProcessing!com.ibd.cohesive.app.business.batch.notification.INotificationBatchProcessing");
      return batchProcessing;
    }
    public INotificationProcessing getNotificationProcessing() throws NamingException { //EJB Integration change
      
      INotificationProcessing notificationProcessing = (INotificationProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/NotificationProcessing!com.ibd.cohesive.app.business.batch.notification.INotificationProcessing");
      return notificationProcessing;
    }
     public ITimeTableBatchProcessing getTimeTableBatchProcessing() throws NamingException { //EJB Integration change
      
      ITimeTableBatchProcessing timeTableProcessing = (ITimeTableBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/TimeTableBatchProcessing!com.ibd.cohesive.app.business.batch.timetable.ITimeTableBatchProcessing");
      return timeTableProcessing;
    }
     public IClassTimeTableProcessing getClassTimeTableProcessing() throws NamingException { //EJB Integration change
      
      IClassTimeTableProcessing timeTableProcessing = (IClassTimeTableProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/ClassTimeTableProcessing!com.ibd.cohesive.app.business.batch.timetable.IClassTimeTableProcessing");
      return timeTableProcessing;
    }
     public IStudentTimeTableProcessing getStudentTimeTableProcessing() throws NamingException { //EJB Integration change
      
      IStudentTimeTableProcessing timeTableProcessing = (IStudentTimeTableProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentTimeTableProcessing!com.ibd.cohesive.app.business.batch.timetable.IStudentTimeTableProcessing");
      return timeTableProcessing;
     }
      public IExamBatchProcessing getExamBatchProcessing() throws NamingException { //EJB Integration change
      
      IExamBatchProcessing examProcessing = (IExamBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/ExamBatchProcessing!com.ibd.cohesive.app.business.batch.exam.IExamBatchProcessing");
      return examProcessing;
    }
     public IClassExamProcessing getClassExamProcessing() throws NamingException { //EJB Integration change
      
      IClassExamProcessing examProcessing = (IClassExamProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/ClassExamProcessing!com.ibd.cohesive.app.business.batch.exam.IClassExamProcessing");
      return examProcessing;
    }
     public IStudentExamProcessing getStudentExamProcessing() throws NamingException { //EJB Integration change
      
      IStudentExamProcessing examProcessing = (IStudentExamProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentExamProcessing!com.ibd.cohesive.app.business.batch.exam.IStudentExamProcessing");
      return examProcessing;
     }
     
     public IStudentExamScheduleService getStudentExamScheduleService() throws NamingException { //EJB Integration change
      
      IStudentExamScheduleService studentExamSchedule = (IStudentExamScheduleService)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentExamScheduleService!com.ibd.businessViews.IStudentExamScheduleService");
      return studentExamSchedule;
    }
     
     public IMarkBatchProcessing getMarkBatchProcessing() throws NamingException { //EJB Integration change
      
      IMarkBatchProcessing markProcessing = (IMarkBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/MarkBatchProcessing!com.ibd.cohesive.app.business.batch.mark.IMarkBatchProcessing");
      return markProcessing;
    }
     public IClassMarkProcessing getClassMarkProcessing() throws NamingException { //EJB Integration change
      
      IClassMarkProcessing markProcessing = (IClassMarkProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/ClassMarkProcessing!com.ibd.cohesive.app.business.batch.mark.IClassMarkProcessing");
      return markProcessing;
    }
     public IStudentMarkProcessing getStudentMarkProcessing() throws NamingException { //EJB Integration change
      
      IStudentMarkProcessing markProcessing = (IStudentMarkProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentMarkProcessing!com.ibd.cohesive.app.business.batch.mark.IStudentMarkProcessing");
      return markProcessing;
     }
     
     public IStudentProgressCardService getStudentProgressCardService() throws NamingException { //EJB Integration change
      
      IStudentProgressCardService studentProgress = (IStudentProgressCardService)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentProgressCardService!com.ibd.businessViews.IStudentProgressCardService");
      return studentProgress;
    }
     
     public IAttendanceBatchProcessing getAttendanceBatchProcessing() throws NamingException { //EJB Integration change
      
      IAttendanceBatchProcessing attendanceProcessing = (IAttendanceBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/AttendanceBatchProcessing!com.ibd.cohesive.app.business.batch.attendance.IAttendanceBatchProcessing");
      return attendanceProcessing;
    }
     public IClassAttendanceProcessing getClassAttendanceProcessing() throws NamingException { //EJB Integration change
      
      IClassAttendanceProcessing attendanceProcessing = (IClassAttendanceProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/ClassAttendanceProcessing!com.ibd.cohesive.app.business.batch.attendance.IClassAttendanceProcessing");
      return attendanceProcessing;
    }
     public IStudentAttendanceProcessing getStudentAttendanceProcessing() throws NamingException { //EJB Integration change
      
      IStudentAttendanceProcessing attendanceProcessing = (IStudentAttendanceProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentAttendanceProcessing!com.ibd.cohesive.app.business.batch.attendance.IStudentAttendanceProcessing");
      return attendanceProcessing;
     }
     
     public IStudentAttendanceService getStudentAttendanceService() throws NamingException { //EJB Integration change
      
      IStudentAttendanceService studentAttendance = (IStudentAttendanceService)
              contxt.lookup("java:app/CohesiveBusiness-ejb/StudentAttendanceService!com.ibd.businessViews.IStudentAttendanceService");
      return studentAttendance;
    }
     public ISAssignmentArchBatchProcessing getStudentAssignmentArchBatch() throws NamingException { //EJB Integration change
      
      ISAssignmentArchBatchProcessing studentAssignment = (ISAssignmentArchBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SAssignmentArchBatchProcessing!com.ibd.cohesive.app.business.batch.archival.assignment.ISAssignmentArchBatchProcessing");
      return studentAssignment;
    }
     public IInstituteAssignmentBatchProcessing getInstituteAssignmentBatch() throws NamingException { //EJB Integration change
      
      IInstituteAssignmentBatchProcessing instituteAssignment = (IInstituteAssignmentBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/InstituteAssignmentBatchProcessing!com.ibd.cohesive.app.business.batch.archival.assignment.IInstituteAssignmentBatchProcessing");
      return instituteAssignment;
    }
     public ISAssignmentArchProcessing getStudentAssignmentArchProcessing() throws NamingException { //EJB Integration change
      
      ISAssignmentArchProcessing studentAssignment = (ISAssignmentArchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/SAssignmentArchProcessing!com.ibd.cohesive.app.business.batch.archival.assignment.ISAssignmentArchProcessing");
      return studentAssignment;
    }
     
     public IUnAuthBatchProcessing getUnAuthBatchProcessing() throws NamingException { //EJB Integration change
      
      IUnAuthBatchProcessing batchProcessing = (IUnAuthBatchProcessing)
              contxt.lookup("java:app/CohesiveBusiness-ejb/UnAuthBatchProcessing!com.ibd.cohesive.app.business.batch.unAuth.IUnAuthBatchProcessing");
      return batchProcessing;
    }
     
 public IAuthTokenProvider getAuthTokenProvider() throws NamingException{
        //EJB Integration change
        //return dbcoreservice;
       // InitialContext a =new InitialContext();
    IAuthTokenProvider authTokenProvider = (IAuthTokenProvider)
         contxt.lookup("java:app/CohesiveBusiness-ejb/AuthTokenProvider!com.ibd.cohesive.app.Oauth.AuthServer.IAuthTokenProvider");

            
            return authTokenProvider;
    }
 public ITokenValidationService getTokenValidationService() throws NamingException{
        //EJB Integration change
        //return dbcoreservice;
       // InitialContext a =new InitialContext();
    ITokenValidationService authTokenValiation = (ITokenValidationService)
         contxt.lookup("java:app/CohesiveBusiness-ejb/TokenValidateService!com.ibd.businessViews.ITokenValidationService");

            
            return authTokenValiation;
    }
 
 public TokenValidateService getTokenValidationServiceClass() throws NamingException{
        //EJB Integration change
        //return dbcoreservice;
       // InitialContext a =new InitialContext();
   TokenValidateService authTokenValiation = new TokenValidateService();
            
            return authTokenValiation;
    }
 
 
 public IResourceTokenProvider getTokenProvider() throws NamingException{
        //EJB Integration change
        //return dbcoreservice;
       // InitialContext a =new InitialContext();
    IResourceTokenProvider service = (IResourceTokenProvider)
         contxt.lookup("java:app/CohesiveBusiness-ejb/ResourceTokenProvider!com.ibd.cohesive.app.Oauth.ResourceServer.IResourceTokenProvider");

            
            return service;
    }

 public ISecurityManagementService getSecurityManagementService() throws NamingException{
        //EJB Integration change
        //return dbcoreservice;
       // InitialContext a =new InitialContext();
    ISecurityManagementService securityManagement = (ISecurityManagementService)
         contxt.lookup("java:app/CohesiveBusiness-ejb/SecurityManagementService!com.ibd.cohesive.app.Oauth.AuthServer.ISecurityManagementService");

            
            return securityManagement;
    }
    
  public INotificationTransferService getNotificationTransferService() throws NamingException{
        //EJB Integration change
        //return dbcoreservice;
       // InitialContext a =new InitialContext();
    INotificationTransferService securityManagement = (INotificationTransferService)
         contxt.lookup("java:app/CohesiveBusiness-ejb/NotificationTransferService!com.ibd.cohesive.app.business.notification.INotificationTransferService");

            
            return securityManagement;
    }
  
  
  public IAmazonEmailService getAmazonEmailService() throws NamingException{
    IAmazonEmailService amazonEmail = (IAmazonEmailService)
         contxt.lookup("java:app/CohesiveBusiness-ejb/AmazonEmailService!com.ibd.businessViews.IAmazonEmailService");

            
            return amazonEmail;
    }
  
  public IAmazonSMSService getAmazonSMSService() throws NamingException{
    IAmazonSMSService amazonSMS = (IAmazonSMSService)
         contxt.lookup("java:app/CohesiveBusiness-ejb/AmazonSMSService!com.ibd.businessViews.IAmazonSMSService");

            
            return amazonSMS;
    }
  
  
  public IAttendanceNotificationService getAttendanceNotificationService() throws NamingException{ //EJB Integration change
    
        IAttendanceNotificationService businessLock = (IAttendanceNotificationService)
         contxt.lookup("java:app/CohesiveBusiness-ejb/AttendanceNotificationService!com.ibd.cohesive.app.business.classentity.classattendance.IAttendanceNotificationService");
        return businessLock;
        
    }
  
//  public ITeacherTimeTableService getTeacherTimeTableService() throws NamingException{
//    ITeacherTimeTableService teacherTimeTable = (ITeacherTimeTableService)
//         contxt.lookup("java:app/CohesiveBusiness-ejb/TeacherTimeTableService!com.ibd.businessViews.ITeacherTimeTableService");
//
//            
//            return teacherTimeTable;
//    }
//  
//  public IClassTimeTableService getClassTimeTableService() throws NamingException{
//    IClassTimeTableService ClassTimeTable = (IClassTimeTableService)
//         contxt.lookup("java:app/CohesiveBusiness-ejb/ClassTimeTableService!com.ibd.businessViews.IClassTimeTableService");
//
//            
//            return ClassTimeTable;
//    }
  
//    public IDBTempSegmentService getDBTempSegmentService() throws NamingException{ //EJB Integration change
//    
//        IDBTempSegmentService tempSegment = (IDBTempSegmentService)
//         contxt.lookup("java:app/CohesiveDatabase/DBTempSegmentService!com.ibd.cohesive.db.tempSegment.IDBTempSegmentService");
//        return tempSegment;
//        
//    }
//    public IPhysicalIOService getPhysicalIOService() throws NamingException{
//        IPhysicalIOService io=(IPhysicalIOService)
//         contxt.lookup("java:app/CohesiveDatabase/PhysicalIOService!com.ibd.cohesive.db.io.IPhysicalIOService");        
//         return io;          
//    }
    /*
   EJB integration change starts
    public ITeacherTimeTableService getTeacherTimeTableService(){
        ITeacherTimeTableService teachertimetable=new TeacherTimeTableService();
        return teachertimetable;
    }
    public IStudentTimeTableService getStudentTimeTableService(){
        IStudentTimeTableService studenttimetable=new StudentTimeTableService();
        return studenttimetable;
    }
    public ITeacherProfileService getTeacherProfileService(){
        ITeacherProfileService teacherprofile=new TeacherProfileService();
        return teacherprofile;
    }
    public IStudentProfileService getStudentProfileService(){
        IStudentProfileService Studentprofile=new StudentProfileService();
        return Studentprofile;
    }
    public IStudentAttendanceService getStudentAttendanceService(){
        IStudentAttendanceService studentAttendance=new StudentAttendanceService();
        return studentAttendance;
    }
    public IMessageRouter getRouter(){
        IMessageRouter mr=new MessageRouter();
        return mr;
    }
    public ITeacherAttendanceService getTeacherAttendanceService(){
        ITeacherAttendanceService teacherAttendance=new TeacherAttendanceService();
        return teacherAttendance;
    }
    public IClassMarkService getClassMarkService(){
        IClassMarkService classMark=new ClassMarkService();
        return classMark;
    }
    public IStudentProgressCardService getProgressCardService(){
        IStudentProgressCardService progresscard=new StudentProgressCardService();
        return progresscard;
    }
    public IClassAttendanceService getClassAttendanceService(){
        IClassAttendanceService classAttendance=new ClassAttendanceService();
        return classAttendance;
    }
    public IStudentLeaveManagementService getStudentLeaveManagementService(){
        IStudentLeaveManagementService studentleave=new StudentLeaveManagementService();
        return studentleave;
    }
    public ITeacherLeaveManagementService getTeacherLeaveManagementService(){
        ITeacherLeaveManagementService teacherleave=new TeacherLeaveManagementService();
        return teacherleave;
    }
    public IClassAssignmentService getClassAssignmentService(){
        IClassAssignmentService classAssignment=new ClassAssignmentService();
        return classAssignment;
    }
    public IStudentAssignmentService  getStudentAssignmentService(){
        IStudentAssignmentService studentAssignment=new StudentAssignmentService();
        return studentAssignment;
    }
    public IClassFeeManagementService getFeeManagementService(){
        IClassFeeManagementService feemanagement=new ClassFeeManagementService();
        return feemanagement;
    }
    public IStudentPaymentService getStudentPaymentService(){
        IStudentPaymentService payment=new StudentPaymentService();
        return payment;
    }
    public IStudentOtherActivityService getStudentOtherActivityService(){
        IStudentOtherActivityService activity=new StudentOtherActivityService();
        return activity;
    }
    public IPayrollService getPayrollService(){
        IPayrollService payroll=new PayrollService();
        return payroll;
    }
    public INotificationService getNotificationService(){
        INotificationService notification=new NotificationService();
        return notification;
    }
    public IClassExamScheduleService getClassExamScheduleService(){
        IClassExamScheduleService schedule=new ClassExamScheduleService();
        return schedule;
    }
    public IStudentCalenderService getStudentCalenderService(){
        IStudentCalenderService calender=new StudentCalenderService();
        return calender;
    }
    public IStudentExamScheduleService getStudentExamScheduleService(){
        IStudentExamScheduleService schedule=new StudentExamScheduleService();
        return schedule;
    }
    public IStudentFeeManagementService getStudentFeeManagementService(){
        IStudentFeeManagementService feeManagement=new StudentFeeManagementService();
        return feeManagement;
    }
    public IClassTimeTableService getClassTimeTableService(){
        IClassTimeTableService classtimetable=new ClassTimeTableService();
        return classtimetable;
    }
    public IStudentMasterService getStudentMasterService(){
        IStudentMasterService studentmaster=new StudentMasterService();
        return studentmaster;
    }
    public ITeacherMasterService getTeacherMasterService(){
        ITeacherMasterService teachermaster=new TeacherMasterService();
        return teachermaster;
    }
    public IClassStudentMappingService getClassStudentMappingService(){
        IClassStudentMappingService mapping=new ClassStudentMappingService();
        return mapping;
    }
*/
   /* public static void setDbcoreservice(DBCoreService dbcoreservice) { //EJB Integration change
        DBDependencyInjection.dbcoreservice = dbcoreservice;
    }

    public static void setIndexcoreservice(IndexCoreService indexcoreservice) { //EJB Integration change
        DBDependencyInjection.indexcoreservice = indexcoreservice;
    }

    private void dbg(String p_value){
        dbg.dbg(p_value);
    }
     private void dbg(Exception ex){
        dbg.exceptionDbg(ex);
    }*/
}
