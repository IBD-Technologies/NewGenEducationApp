/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch;

import com.ibd.cohesive.app.business.batch.archival.assignment.IInstituteAssignmentBatchProcessing;
import com.ibd.cohesive.app.business.batch.attendance.IAttendanceBatchProcessing;
import com.ibd.cohesive.app.business.batch.exam.IExamBatchProcessing;
import com.ibd.cohesive.app.business.batch.fee.IFeeBatchProcessing;
import com.ibd.cohesive.app.business.batch.mark.IMarkBatchProcessing;
import com.ibd.cohesive.app.business.batch.notification.INotificationBatchProcessing;
import com.ibd.cohesive.app.business.batch.timetable.ITimeTableBatchProcessing;
import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import com.ibd.cohesive.app.business.batch.archival.assignment.ISAssignmentArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.attendance.ICAttendanceArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.attendance.ISAttendanceArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.attendance.ITAttendanceArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.calender.ISCalenderArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.calender.ITCalenderArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.exam.ICExamArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.exam.ISExamArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.fee.IInsFeeArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.fee.ISFeeArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.leave.ISLeaveArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.leave.ITLeaveArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.mark.ICMarkArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.mark.ISMarkArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.notification.IInsNotificationArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.notification.ISNotificationArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.otheractivity.IInsOtherActivityArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.otheractivity.ISOtherActivityArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.payment.IInsPaymentArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.payment.ISPaymentArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.payroll.ITPayrollArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.profile.ISProfileArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.profile.ITProfileArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.profile.IUProfileArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.timetable.ICTimeTableArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.timetable.ISTimeTableArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.archival.timetable.ITTimeTableArchBatchProcessing;
import com.ibd.cohesive.app.business.batch.eCircular.IECircularBatchProcessing;
import com.ibd.cohesive.app.business.batch.eventNotification.IEventNotificationBatchProcessing;
import com.ibd.cohesive.app.business.batch.feeNotification.IFeeNotificationBatchProcessing;
import com.ibd.cohesive.app.business.batch.unAuth.IUnAuthBatchProcessing;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
@Stateless
public class InstituteEODWrapper implements IInstituteEODWrapper{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public InstituteEODWrapper() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
     
     
 
   public void batchWrapper(String instituteID) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    BatchUtil batchUtil=null; 
    String l_businessDate=null;
    boolean l_session_created_now=false;
    ITransactionControlService tc=null;
    try{   

    session.createSessionObject();
    dbSession.createDBsession(session);
    l_session_created_now=session.isI_session_created_now();
    IBDProperties i_db_properties=session.getCohesiveproperties();
    IDBReadBufferService readBuffer=inject.getDBReadBufferService();
    IPDataService pds=inject.getPdataservice();
    IDBTransactionService dbts=inject.getDBTransactionService();
    IAssignmentBatchProcessing assignmentBatch=inject.getAssignmentBatchProcessing();
    IECircularBatchProcessing eCircularBatch=inject.getECircularBatchProcessing();
    IOtherActivityBatchProcessing otherActivityBatch=inject.getOtherActivityBatchProcessing();
    IFeeBatchProcessing feeBatch=inject.getFeeBatchProcessing();
    IFeeNotificationBatchProcessing feeNotificationBatch=inject.getFeeNotificationBatchProcessing();
    IEventNotificationBatchProcessing eventNotificationBatch=inject.getEventNotificationBatchProcessing();
    INotificationBatchProcessing notificationBatch=inject.getNotificationBatchProcessing();
    ITimeTableBatchProcessing timeTableBatch=inject.getTimeTableBatchProcessing();
    IExamBatchProcessing examBatch=inject.getExamBatchProcessing();
    IMarkBatchProcessing markBatch=inject.getMarkBatchProcessing();
    IAttendanceBatchProcessing attendanceBatch=inject.getAttendanceBatchProcessing();
    ISAssignmentArchBatchProcessing studentAssignmentArch=inject.getStudentAssignmentArchBatch();
    IInstituteAssignmentBatchProcessing instituteAssignmentArch=inject.getInstituteAssignmentBatch();
    ISOtherActivityArchBatchProcessing studentOtherActivityArch=inject.getStudentOtherActivityArchBatch();
    IInsOtherActivityArchBatchProcessing instituteOtherActivityArch=inject.getInstituteOtherActivityBatch();
    ISFeeArchBatchProcessing studentFeeArch=inject.getStudentFeeArchBatch();
    IInsFeeArchBatchProcessing instituteFeeArch=inject.getInstituteFeeBatch();
    ISNotificationArchBatchProcessing studentNotificationArch=inject.getStudentNotificationArchBatch();
    IInsNotificationArchBatchProcessing instituteNotificationArch=inject.getInstituteNotificationBatch();
    ISMarkArchBatchProcessing studentMarkArch=inject.getStudentMarkArchBatch();
    ICMarkArchBatchProcessing classMarkArch=inject.getClassMarkArchBatch();
    ISExamArchBatchProcessing studentExamArch=inject.getStudentExamArchBatch();
    ICExamArchBatchProcessing classExamArch=inject.getClassExamArchBatch();
    ISTimeTableArchBatchProcessing studentTimeTableArch=inject.getStudentTimeTableArchBatch();
    ICTimeTableArchBatchProcessing classTimeTableArch=inject.getClassTimeTableArchBatch();
    ISAttendanceArchBatchProcessing studentAttendanceArch=inject.getStudentAttendanceArchBatch();
    ICAttendanceArchBatchProcessing classAttendanceArch=inject.getClassAttendanceArchBatch();
    ISLeaveArchBatchProcessing studentLeaveArch=inject.getStudentLeaveArchBatch();
    ITLeaveArchBatchProcessing teacherLeaveArch=inject.getTeacherLeaveArchBatch();
    ISCalenderArchBatchProcessing studentCalenderArch=inject.getStudentCalenderArchBatch();
    ITCalenderArchBatchProcessing teacherCalenderArch=inject.getTeacherCalenderArchBatch();
    ITPayrollArchBatchProcessing teacherPayrollArch=inject.getTeacherPayrollArchBatch();
    ISPaymentArchBatchProcessing studentPaymentArch=inject.getStudentPaymentArchBatch();
    IInsPaymentArchBatchProcessing institutePaymentArch=inject.getInstitutePaymentBatch();
    ITAttendanceArchBatchProcessing teacherAttendanceArch=inject.getTeacherAttendanceArchBatch();
    ITTimeTableArchBatchProcessing teacherTimeTableArch=inject.getTeacherTimeTableArchBatch();
    ISProfileArchBatchProcessing studentProfileArch=inject.getStudentProfileArchBatch();
    ITProfileArchBatchProcessing teacherProfileArch=inject.getTeacherProfileArchBatch();
    IUProfileArchBatchProcessing userProfileArch=inject.getUserProfileArchBatch();
    IUnAuthBatchProcessing unAuthBatch=inject.getUnAuthBatchProcessing();
    BusinessService bs=inject.getBusinessService(session);
    tc=inject.getTransactionControlService();
    batchUtil=inject.getBatchUtil(session);
    dbg("inside institute eod wrapper");
    String[] datePkry={instituteID};
//    DBRecord currentDateRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE", "INSTITUTE_CURRENT_DATE",datePkry ,session, dbSession);
//    l_businessDate=currentDateRecord.getRecord().get(1).trim();
      l_businessDate=  bs.getCurrentDate();;
    dbg("l_businessDate"+l_businessDate);
    boolean recordExistence=true;
    DBRecord instituteEODStatusRecord=null;
    String l_eodStatus=new String();
    String startTime=bs.getCurrentDateTime();
    int maxSequence=0;
    dbg("startTime"+startTime);
    
        try{
            
           
            maxSequence=batchUtil.getInstituteMaxSequence(l_businessDate, instituteID, inject, session, dbSession);
            
            String l_eodPkey[]={instituteID,l_businessDate,Integer.toString(maxSequence)};
            instituteEODStatusRecord=readBuffer.readRecord("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "INSTITUTE_EOD_STATUS",l_eodPkey ,session, dbSession,true);
            l_eodStatus=instituteEODStatusRecord.getRecord().get(2).trim();

        }catch(DBValidationException ex){

           if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){

                recordExistence=false;
            }
        }
        
       dbg("l_eodStatus"+l_eodStatus);
       dbg("recordExistence"+recordExistence);
       int sequence=0; 
        
         
        if(recordExistence==true){
            
       
             if(l_eodStatus.equals("F")){

                 int instituteHistMaxSequence=batchUtil.getInstituteHistoryMaxSequence(l_businessDate, instituteID, inject, session, dbSession);
                 
                 if(instituteHistMaxSequence>10){
                     return;
                 }
                 
                 
                sequence=maxSequence;
                Map<String,String>column_to_Update=new HashMap();
                column_to_Update.put("3", "W");
                column_to_Update.put("4", startTime);
                String[] l_pkey={instituteID,l_businessDate,Integer.toString(sequence)};
                dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "INSTITUTE_EOD_STATUS", l_pkey, column_to_Update,session); 
                batchUtil.moveInstituteRecordToHistory(instituteID,l_businessDate,instituteEODStatusRecord, session, dbSession, inject);
       
             }else{

                 dbg("l_eodStatus is not f");
                 sequence=maxSequence+1;
                 dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 96,instituteID, l_businessDate,"W",startTime," "," "," "," ",Integer.toString(sequence));
                 dbg("create record completed if eod f");
            }
         
        }else{
            
            
            sequence=1;
            dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 96,instituteID, l_businessDate,"W",startTime," "," "," "," ",Integer.toString(sequence));
        }
         
         
        tc.commit(session, dbSession);
         
         dbg("before readTable of batch configuration");
         Map<String,ArrayList<String>>batchConfigMap=pds.readTablePData("APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "BATCH_CONFIG", session, dbSession);
         List<ArrayList<String>>batchList=batchConfigMap.values().stream().filter(rec->rec.get(13).trim().equals("O")&&rec.get(4).trim().equals("Y")&&rec.get(14).trim().equals("A")).collect(Collectors.toList());
         dbg("after readTable of batch configuration");
         dbg("batchList"+batchList.size());
         
         for(int i=0;i<batchList.size();i++)
         {
        
           String batchName=batchList.get(i).get(0).trim();
           int no_of_threads= Integer.parseInt(  batchList.get(i).get(7).trim());  
           dbg("inside batch iteration batchName"+batchName);
           dbg("inside batch iteration no_of_threads"+batchName);
              
                switch(batchName){
                    
                    case "Assignment":
                      assignmentBatch.processing(instituteID, batchName,no_of_threads,session);    
                    break;    
                    case "ECircular":
                      eCircularBatch.processing(instituteID, batchName,no_of_threads,session);    
                    break;
                    case "OtherActivity":
                        
                        otherActivityBatch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "FeeManagement":
                        
                        feeBatch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "FeeNotification":
                        
                        feeNotificationBatch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
//                     case "EventNotification":
//                        
//                        eventNotificationBatch.processing(instituteID, batchName, no_of_threads, session);
//                        
//                     break;
                     case "Notification":
                        
                        notificationBatch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "TimeTable":
                        
                        timeTableBatch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "Exam":
                        
                        examBatch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "Mark":
                        
                        markBatch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     
                     case "Attendance":
                        
                        attendanceBatch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     
                      case "StudentAssignmentArchival":
                        
                        studentAssignmentArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "InstituteAssignmentArchival":
                        
                        instituteAssignmentArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "StudentOtherActivityArchival":
                        
                        studentOtherActivityArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "InstituteOtherActivityArchival":
                        
                        instituteOtherActivityArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "StudentFeeArchival":
                        
                        studentFeeArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "InstituteFeeArchival":
                        
                        instituteFeeArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                      case "StudentNotificationArchival":
                        
                        studentNotificationArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "InstituteNotificationArchival":
                        
                        instituteNotificationArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "StudentMarkArchival":
                        
                        studentMarkArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "ClassMarkArchival":
                        
                        classMarkArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     
                     case "StudentExamArchival":
                        
                        studentExamArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "ClassExamArchival":
                        
                        classExamArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "StudentTimeTableArchival":
                        
                        studentTimeTableArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "ClassTimeTableArchival":
                        
                        classTimeTableArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     
                     case "StudentAttendanceArchival":
                        
                        studentAttendanceArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "ClassAttendanceArchival":
                        
                        classAttendanceArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "StudentLeaveArchival":
                        
                        studentLeaveArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "TeacherLeaveArchival":
                        
                        teacherLeaveArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     
                     case "StudentCalenderArchival":
                        
                        studentCalenderArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "TeacherCalenderArchival":
                        
                        teacherCalenderArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "TeacherPayrollArchival":
                        
                        teacherPayrollArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "StudentPaymentArchival":
                        
                        studentPaymentArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "InstitutePaymentArchival":
                        
                        institutePaymentArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "TeacherAttendanceArchival":
                        
                        teacherAttendanceArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                      case "TeacherTimeTableArchival":
                        
                        teacherTimeTableArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     case "StudentProfileArchival":
                        
                        studentProfileArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                    case "TeacherProfileArchival":
                        
                        teacherProfileArch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                     
                    case "UnAuth":
                        
                        unAuthBatch.processing(instituteID, batchName, no_of_threads, session);
                        
                     break;
                }
               
         }
                  
        batchList=batchConfigMap.values().stream().filter(rec->rec.get(13).trim().equals("O")&&rec.get(4).trim().equals("Y")&&rec.get(14).trim().equals("A")&&rec.get(0).trim().equals("EventNotification")).collect(Collectors.toList());                
     
        int no_of_thread=Integer.parseInt(batchList.get(0).get(7).trim());
        
        eventNotificationBatch.processing(instituteID, "EventNotification", no_of_thread, session);
                        
         batchUtil.instituteSucessHandler(instituteID, l_businessDate, inject, session, dbSession);

         
         dbg("end of institute eod wrapper");
        }catch(DBValidationException ex){
          batchUtil.instituteErrorHandler(instituteID, l_businessDate, ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.instituteErrorHandler(instituteID, l_businessDate, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.instituteErrorHandler(instituteID, l_businessDate, ex, inject, session, dbSession);
     }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
    
}
   
   public void batchWrapper(String instituteID,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           batchWrapper(instituteID);
       
      }catch(DBValidationException ex){
          throw ex;
      }catch(BSValidationException ex){
          throw ex;     
      }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(BSProcessingException ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }finally {
           this.session=tempSession;
            
        } 
   }
   
   @Asynchronous
   public Future<String> parellelBatchWrapper(String instituteID) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    try{   
    
        batchWrapper(instituteID);
        
              return new AsyncResult<String>("Success");

       
        }catch(Exception ex){
           dbg(ex);
           return new AsyncResult<String>("Fail");
     }
    
}
   
   private void dbg(String p_Value) {
        session.getDebug().dbg(p_Value);

    }

    private void dbg(Exception ex) {
        session.getDebug().exceptionDbg(ex);

    }
}
