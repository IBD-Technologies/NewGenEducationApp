/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.util.dependencyInjection;

import com.ibd.businessViews.IAmazonEmailService;
import com.ibd.businessViews.IAmazonSMSService;
import com.ibd.businessViews.IArchApplyStatusUpdate;
import com.ibd.businessViews.IArchShippingStatusUpdate;
import com.ibd.cohesive.db.archivalapply.IArchivalApplyService;
import com.ibd.cohesive.db.archivalshipping.IArchivalShippingService;
import com.ibd.cohesive.db.core.IDBCoreService;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.core.relationship.RelationshipService; //// EJB Integration change 
import com.ibd.cohesive.db.index.IndexCoreService; // EJB Integration change
import com.ibd.cohesive.db.index.IndexReadService;
import com.ibd.cohesive.db.io.IPhysicalIOService;
import com.ibd.cohesive.db.io.IWaitWriteService;
import com.ibd.cohesive.db.read.IDBReadService;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.scheduler.IArchApplyWorkerBean;
import com.ibd.cohesive.db.scheduler.IArchShippingWorkerBean;
import com.ibd.cohesive.db.tempSegment.IDBTempSegmentService;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.lock.ILockService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import com.ibd.cohesive.db.write.IDBWriteBufferService;
import com.ibd.cohesive.db.scheduler.IDBWriteWorkerBean;
import com.ibd.cohesive.db.scheduler.IWaitWriteWorkerBean;
import com.ibd.cohesive.db.waitmonitor.IDBWaitBuffer;
import java.util.Properties;
import javax.naming.Context;

/**
 *
 * @author IBD Technologies
 */
public class DBDependencyInjection {
    
    private InitialContext contxt;
    final Context remoteContxt;
    Properties props = new Properties();
     
    
    public DBDependencyInjection() throws NamingException  {
        
        contxt = new InitialContext();
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
         props.put("jboss.naming.client.ejb.context", "true"); 
         remoteContxt= new InitialContext(props);
        
       }

   
    public IDBCoreService getDbcoreservice() throws NamingException{
        //EJB Integration change
        //return dbcoreservice;
       // InitialContext a =new InitialContext();
    IDBCoreService dbcoreservice = (IDBCoreService)
         contxt.lookup("java:module/DBCoreService!com.ibd.cohesive.db.core.IDBCoreService");
    return dbcoreservice;
    }

    public  IndexCoreService getIndexcoreservice() throws NamingException { 
        IndexCoreService service = new IndexCoreService();
        return service;
    }
    public IDBReadService getDbreadservice() throws NamingException { //EJB Integration change
      
      IDBReadService dbreadservice = (IDBReadService)
         contxt.lookup("java:module/DBReadService!com.ibd.cohesive.db.read.IDBReadService");
      return dbreadservice;
    }
    public IDBTransactionService getDBTransactionService() throws NamingException{ //EJB Integration change
      IDBTransactionService dbts = (IDBTransactionService)
         contxt.lookup("java:module/DBTransactionService!com.ibd.cohesive.db.transaction.IDBTransactionService");
        
        return dbts;
    }

    public IMetaDataService getMetadataservice() throws NamingException { //EJB Integration change
IMetaDataService metadataservice = (IMetaDataService)
         contxt.lookup("java:module/MetaDataService!com.ibd.cohesive.db.core.metadata.IMetaDataService");
      return metadataservice;
    }
 
    public IndexReadService getIndexreadservice() throws NamingException {//EJB Integration change
        IndexReadService indexreadservice = new IndexReadService();//EJB Integration change
        return indexreadservice;
    }

 
public IPDataService getPdataservice() throws NamingException { //EJB Integration change

        IPDataService pdataservice = (IPDataService)
         contxt.lookup("java:module/PDataService!com.ibd.cohesive.db.core.pdata.IPDataService");
      return pdataservice;
    }

    public RelationshipService getRelationshipService() throws NamingException{ //EJB Integration change
        RelationshipService relationshipservice=new RelationshipService(); //EJB Integration change
        return relationshipservice;
    }

    public ILockService getLockService() throws NamingException{ //EJB Integration change
      
        //LockService lockservice=dbcoreservice.getI_lockService();
        ILockService lockservice = (ILockService)
         contxt.lookup("java:module/LockService!com.ibd.cohesive.db.transaction.lock.ILockService");
        return lockservice;
        
    }
    public ITransactionControlService getTransactionControlService() throws NamingException{ //EJB Integration change
    
        ITransactionControlService TransactionControl = (ITransactionControlService)
         contxt.lookup("java:module/TransactionControlService!com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService");
        return TransactionControl;
        
    }
    public IDBReadBufferService getDBReadBufferService() throws NamingException{ //EJB Integration change
    
        IDBReadBufferService readBuffer = (IDBReadBufferService)
         contxt.lookup("java:module/DBReadBufferService!com.ibd.cohesive.db.readbuffer.IDBReadBufferService");
        return readBuffer;
        
    }
    public IDBTempSegmentService getDBTempSegmentService() throws NamingException{ //EJB Integration change
    
        IDBTempSegmentService tempSegment = (IDBTempSegmentService)
         contxt.lookup("java:module/DBTempSegmentService!com.ibd.cohesive.db.tempSegment.IDBTempSegmentService");
        return tempSegment;
        
    }
    public IDBWriteBufferService getDBWriteService() throws NamingException{
        IDBWriteBufferService write=(IDBWriteBufferService)
        contxt.lookup("java:module/DBWriteBufferService!com.ibd.cohesive.db.write.IDBWriteBufferService");        
         return write;       
    }
    
    public IPhysicalIOService getPhysicalIOService() throws NamingException{
        IPhysicalIOService io=(IPhysicalIOService)
         contxt.lookup("java:module/PhysicalIOService!com.ibd.cohesive.db.io.IPhysicalIOService");        
         return io;          
    }
    
    public IDBWriteWorkerBean getDBWriteWorkBeanService() throws NamingException{
        IDBWriteWorkerBean workerBean=(IDBWriteWorkerBean)
                contxt.lookup("java:module/DBWriteWorkerBean!com.ibd.cohesive.db.scheduler.IDBWriteWorkerBean");
//         contxt.lookup("java:module/WorkerBean!com.ibd.cohesive.db.scheduler.IWorkerBean");
        return workerBean;
    }
     public IDBWaitBuffer getWaitBufferService() throws NamingException{
        IDBWaitBuffer waitBuffer=(IDBWaitBuffer)
                contxt.lookup("java:module/DBWaitBuffer!com.ibd.cohesive.db.waitmonitor.IDBWaitBuffer");
//         contxt.lookup("java:module/WorkerBean!com.ibd.cohesive.db.scheduler.IWorkerBean");
        return waitBuffer;
    }
     public IWaitWriteWorkerBean getWaitWriteWorkerBean() throws NamingException{
         IWaitWriteWorkerBean waitWorker=(IWaitWriteWorkerBean)
                 contxt.lookup("java:module/WaitWriteWorkerBean!com.ibd.cohesive.db.scheduler.IWaitWriteWorkerBean");
         return waitWorker;
     }
     public IWaitWriteService getWaitWriteService() throws NamingException{
         IWaitWriteService waitWrite=(IWaitWriteService)
                 contxt.lookup("java:module/WaitWriteService!com.ibd.cohesive.db.io.IWaitWriteService");
         return waitWrite;
     }
     public IArchivalShippingService getArchivalShippingService() throws NamingException{
         IArchivalShippingService archShipping=(IArchivalShippingService)
                 contxt.lookup("java:module/ArchivalShippingService!com.ibd.cohesive.db.archivalshipping.IArchivalShippingService");
         return archShipping;
     }
     public IArchShippingWorkerBean getArchShippingWorkerBean() throws NamingException{
         IArchShippingWorkerBean archShippingWorker=(IArchShippingWorkerBean)
                 contxt.lookup("java:module/ArchShippingWorkerBean!com.ibd.cohesive.db.scheduler.IArchShippingWorkerBean");
         return archShippingWorker;
     }
     
     public IArchivalApplyService getArchivalApplyService() throws NamingException{
         IArchivalApplyService archApply=(IArchivalApplyService)
                 contxt.lookup("java:module/ArchivalApplyService!com.ibd.cohesive.db.archivalapply.IArchivalApplyService");
         return archApply;
     }
     
     public IArchApplyWorkerBean getArchApplyWorkerBean() throws NamingException{
         IArchApplyWorkerBean archApplyWorker=(IArchApplyWorkerBean)
                 contxt.lookup("java:module/ArchApplyWorkerBean!com.ibd.cohesive.db.scheduler.IArchApplyWorkerBean");
         return archApplyWorker;
     }
     public IArchApplyStatusUpdate getIArchApplyStatusUpdate() throws NamingException{
         IArchApplyStatusUpdate archApply=(IArchApplyStatusUpdate)
                 remoteContxt.lookup("ejb:CohesiveReportEngine/CohesiveDatabase/ArchApplyStatusUpdate!com.ibd.businessViews.IArchApplyStatusUpdate");
         return archApply;
     }
     public IArchShippingStatusUpdate getIArchShippingStatusUpdate() throws NamingException{
         IArchShippingStatusUpdate archShipping=(IArchShippingStatusUpdate)
                 remoteContxt.lookup("ejb:CohesiveBackend/CohesiveDatabase/ArchShippingStatusUpdate!com.ibd.businessViews.IArchShippingStatusUpdate");
         return archShipping;
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
