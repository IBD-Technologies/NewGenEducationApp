/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch;

import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.MessageInput;
import com.ibd.cohesive.app.business.util.NotificationUtil;
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
import java.util.Map;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Stateless
public class StudentAssignmentProcessing implements IStudentAssignmentProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public StudentAssignmentProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
     
      public void processing (String instituteID,String studentID,String assignmentID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       BatchUtil batchUtil=null;
       boolean l_session_created_now=false;
       ITransactionControlService tc=null;
       try{
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();  
        dbg("inside student assignment processing ");
        dbg("instituteID"+instituteID);
        dbg("assignmentID"+assignmentID);
        dbg("l_businessDate"+l_businessDate);
        dbg("studentID"+studentID);
        
        tc=inject.getTransactionControlService();
        BusinessService bs=inject.getBusinessService(session);
        String startTime=bs.getCurrentDateTime();
        batchUtil=inject.getBatchUtil(session);
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBTransactionService dbts=inject.getDBTransactionService();
            

                 //start time update starts
          Map<String,String>column_to_Update=new HashMap();
          column_to_Update.put("7", startTime);
          String[]l_pkey={instituteID,assignmentID,studentID,l_businessDate};
          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment"+i_db_properties.getProperty("FOLDER_DELIMITER")+assignmentID, "BATCH", "STUDENT_ASSIGNMENT_EOD_STATUS", l_pkey, column_to_Update,session); 
          tc.commit(session, dbSession);
          
          
          
          
          
          
          //start time update ends
//          DBRecord studentAssignmentRec;
//          String versionNumber=null;
//          boolean recordExistence=true;
          
//          try{
//        
//            String[]l_primaryKey={assignmentID,l_businessDate};  
//            studentAssignmentRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_ASSIGNMENT", l_primaryKey, session, dbSession,true);
//            versionNumber=studentAssignmentRec.getRecord().get(15).trim();
//            dbg("versionNumber"+versionNumber);
//         }catch(DBValidationException ex){
//            if(ex.toString().contains("DB_VAL_011")){
//                
//                dbg("printing error inside student assignment processing"+ex.toString());
//                recordExistence=false;
//                
//            }else{
//                throw ex;
//            }
//         }catch(Exception ex){
//             
//             dbg("printing error inside student assignment processing"+ex.toString());
//             if(ex.toString().contains("DB_VAL_000")){
//                
//                recordExistence=false;
//                
//            }else{
//                throw ex;
//            }
//             
//             
//         }
          
//         dbg("recordExistence"+recordExistence); 
//          if(!recordExistence){
              
              buildRequestAndCallStudentAssignment(studentID,assignmentID,1,instituteID,l_businessDate);
//          }else{
              
//              buildRequestAndCallStudentAssignment(studentID,assignmentID,Integer.parseInt(versionNumber)+1,instituteID);
//          }
          

         batchUtil.studentAssignmentProcessingSuccessHandler(instituteID, l_businessDate, assignmentID, studentID, inject, session, dbSession);
        
         dbg("end of student assignment processing");
        }catch(DBValidationException ex){
          batchUtil.studentAssignmentProcessingErrorHandler(instituteID, l_businessDate, assignmentID, studentID, ex, inject, session, dbSession);
        }catch(BSValidationException ex){
          batchUtil.studentAssignmentProcessingErrorHandler(instituteID, l_businessDate, assignmentID, studentID, ex, inject, session, dbSession);
      
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.studentAssignmentProcessingErrorHandler(instituteID, l_businessDate, assignmentID, studentID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.studentAssignmentProcessingErrorHandler(instituteID, l_businessDate, assignmentID, studentID, ex, inject, session, dbSession);
     }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
}

    public void processing(String instituteID,String studentID,String assignmentID,String l_businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(instituteID,studentID,assignmentID,l_businessDate);
       
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
   public Future<String> parallelProcessing(String instituteID,String studentID,String assignmentID,String l_businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    try{   
    
        processing(instituteID,studentID,assignmentID,l_businessDate);
        
              return new AsyncResult<String>("Success");

       
        }catch(Exception ex){
           dbg(ex);
           return new AsyncResult<String>("Fail");
     }
    
}
   
    private void buildRequestAndCallStudentAssignment(String l_studentID,String l_assignmentID,int p_versionNumber,String instituteID,String businessDate)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
     ITransactionControlService tc=null;
        try{
        dbg("inside buildRequestAndCallStudentAssignment") ;   
        dbg("versionNumber"+p_versionNumber);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=instituteID;
        IDBTransactionService dbts=inject.getDBTransactionService();
        tc=inject.getTransactionControlService();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        NotificationUtil notificationUtil=inject.getNotificationUtil(session);
        String[] l_primaryKey={l_assignmentID};
        
        
        DBRecord instituteRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT","IVW_ASSIGNMENT", l_primaryKey, session,dbSession);
        
        String dueDate=instituteRecord.getRecord().get(6).trim();
//        String MakerID=instituteRecord.getRecord().get(9).trim();
//        String CheckerID=instituteRecord.getRecord().get(10).trim();
//        String MakerDateStamp=instituteRecord.getRecord().get(11).trim();
//        String CheckerDateStamp=instituteRecord.getRecord().get(12).trim();
//        String RecordStatus=instituteRecord.getRecord().get(13).trim();
//        String AuthStatus=instituteRecord.getRecord().get(14).trim();
//        String VersionNumber=instituteRecord.getRecord().get(15).trim();
//        String MakerRemarks=instituteRecord.getRecord().get(16).trim();
//        String CheckerRemarks=instituteRecord.getRecord().get(17).trim();
        
         MessageInput feemessageInput=notificationUtil.getAssignmentMessageInput(instituteID, l_studentID, l_assignmentID, session, dbSession, inject);
          
          notificationUtil.messageGeneration(instituteID, l_studentID, feemessageInput, session, dbSession, inject);
        
        
        
        
        
        
        
        
           
         try{
              dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ASSIGNMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Assignment","ASSIGNMENT",60,l_studentID,l_assignmentID,dueDate," "," ");
              tc.commit(session, dbSession);
        }catch(DBValidationException ex){
            tc.rollBack(session, dbSession);
            if(!ex.toString().contains("DB_VAL_009")){
                dbg(ex);
                throw ex;
            }
        }
          
   
          
        dbg("end of buildRequestAndCallstudentAssignment");  
        }catch(DBValidationException ex){
            dbg(ex);
            tc.rollBack(session, dbSession);
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            tc.rollBack(session, dbSession);
            throw new DBProcessingException(ex.toString());
//        }catch(BSProcessingException ex){
//            dbg(ex);
//            throw new BSProcessingException(ex.toString());    
//        }catch(BSValidationException ex){
//            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            tc.rollBack(session, dbSession);
            throw new BSProcessingException("Exception" + ex.toString());
        }
          
 }
     
//      public void processing (String instituteID,String studentID,String assignmentID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//       BatchUtil batchUtil=null;
//       boolean l_session_created_now=false;
//       ITransactionControlService tc=null;
//       try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();  
//        dbg("inside student assignment processing ");
//        dbg("instituteID"+instituteID);
//        dbg("assignmentID"+assignmentID);
//        dbg("l_businessDate"+l_businessDate);
//        dbg("studentID"+studentID);
//        
//        tc=inject.getTransactionControlService();
//        BusinessService bs=inject.getBusinessService(session);
//        String startTime=bs.getCurrentDateTime();
//        batchUtil=inject.getBatchUtil(session);
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IDBTransactionService dbts=inject.getDBTransactionService();
//            
//                 //start time update starts
//          Map<String,String>column_to_Update=new HashMap();
//          column_to_Update.put("7", startTime);
//          String[]l_pkey={instituteID,assignmentID,studentID,l_businessDate};
//          dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_ASSIGNMENT_EOD_STATUS", l_pkey, column_to_Update,session); 
//          tc.commit(session, dbSession);
//          //start time update ends
//          DBRecord studentAssignmentRec;
//          String versionNumber=null;
//          boolean recordExistence=true;
//          
//          try{
//        
//            String[]l_primaryKey={assignmentID};  
//            studentAssignmentRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_ASSIGNMENT", l_primaryKey, session, dbSession,true);
//            versionNumber=studentAssignmentRec.getRecord().get(15).trim();
//            dbg("versionNumber"+versionNumber);
//         }catch(DBValidationException ex){
//            if(ex.toString().contains("DB_VAL_011")){
//                
//                dbg("printing error inside student assignment processing"+ex.toString());
//                recordExistence=false;
//                
//            }else{
//                throw ex;
//            }
//         }catch(Exception ex){
//             
//             dbg("printing error inside student assignment processing"+ex.toString());
//             if(ex.toString().contains("DB_VAL_000")){
//                
//                recordExistence=false;
//                
//            }else{
//                throw ex;
//            }
//             
//             
//         }
//          
//         dbg("recordExistence"+recordExistence); 
//          if(!recordExistence){
//              
//              buildRequestAndCallStudentAssignment(studentID,assignmentID,1,instituteID);
//          }else{
//              
//              buildRequestAndCallStudentAssignment(studentID,assignmentID,Integer.parseInt(versionNumber)+1,instituteID);
//          }
//          
//
//         batchUtil.studentAssignmentProcessingSuccessHandler(instituteID, l_businessDate, assignmentID, studentID, inject, session, dbSession);
//        
//         dbg("end of student assignment processing");
//        }catch(DBValidationException ex){
//          batchUtil.studentAssignmentProcessingErrorHandler(instituteID, l_businessDate, assignmentID, studentID, ex, inject, session, dbSession);
//        }catch(BSValidationException ex){
//          batchUtil.studentAssignmentProcessingErrorHandler(instituteID, l_businessDate, assignmentID, studentID, ex, inject, session, dbSession);
//      
//        }catch(DBProcessingException ex){
//          dbg(ex);
//          batchUtil.studentAssignmentProcessingErrorHandler(instituteID, l_businessDate, assignmentID, studentID, ex, inject, session, dbSession);
//        }catch(Exception ex){
//           dbg(ex);
//           batchUtil.studentAssignmentProcessingErrorHandler(instituteID, l_businessDate, assignmentID, studentID, ex, inject, session, dbSession);
//     }finally{
//               if(l_session_created_now){    
//                  dbSession.clearSessionObject();
//                  session.clearSessionObject();
//               }
//           }
//}
//
//    public void processing(String instituteID,String studentID,String assignmentID,String l_businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//      
//       CohesiveSession tempSession = this.session;
//       
//       try{
//           
//           this.session=session;
//           processing(instituteID,studentID,assignmentID,l_businessDate);
//       
//      }catch(DBValidationException ex){
//          throw ex;
//      }catch(BSValidationException ex){
//          throw ex;     
//      }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//      }catch(BSProcessingException ex){
//           dbg(ex);
//           throw new BSProcessingException(ex.toString());
//     }catch(Exception ex){
//           dbg(ex);
//           throw new BSProcessingException(ex.toString());
//     }finally {
//           this.session=tempSession;
//            
//        } 
//   }
//    
//    
//    @Asynchronous
//   public Future<String> parallelProcessing(String instituteID,String studentID,String assignmentID,String l_businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//   
//    try{   
//    
//        processing(instituteID,studentID,assignmentID,l_businessDate);
//        
//              return new AsyncResult<String>("Success");
//
//       
//        }catch(Exception ex){
//           dbg(ex);
//           return new AsyncResult<String>("Fail");
//     }
//    
//}
//   
//    private void buildRequestAndCallStudentAssignment(String l_studentID,String l_assignmentID,int p_versionNumber,String instituteID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
//     
//        try{
//        dbg("inside buildRequestAndCallStudentAssignment") ;   
//        dbg("versionNumber"+p_versionNumber);
//        IStudentAssignmentService sas=inject.getStudentAssignmentService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        String l_instituteID=instituteID;
//        JsonObject studentAssignment;
//        String l_msgID="";
//        String l_correlationID="";
//        String l_service="StudentAssignment";
//        String l_operation="AutoAuth";
//        JsonArray l_businessEntity=Json.createArrayBuilder().add(Json.createObjectBuilder().add("entityName", "studentID")
//                                                                                             .add("entityValue", l_studentID)).build();
//        String l_userID="system";
//        String l_source="cohesive_backend";
//        String l_status=" ";
//        
//        String[]l_pkey={l_assignmentID};
//        ArrayList<String> assignmentList=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_ASSIGNMENT", l_pkey, session,dbSession).getRecord();
//        
//        
//        String l_subjectID=assignmentList.get(4).trim();
//        String l_assignmentDescription=assignmentList.get(3).trim();
//        String l_assignmentType=assignmentList.get(5).trim();
//        String l_dueDate=assignmentList.get(6).trim();
//        String l_completedDate=" ";
//        String l_assignmentStatus=" ";
//        String l_teacherComments=assignmentList.get(7).trim();;
//        String l_parentComment=" ";
//        
//        String l_makerID="";
//        String l_checkerID="";
//        String l_makerDateStamp="";
//        String l_checkerDateStamp="";
//        String l_recordStatus=assignmentList.get(13).trim();
//        String l_authStatus=assignmentList.get(14).trim();
//        String l_makerRemarks=assignmentList.get(15).trim();
//        String l_checkerRemarks=assignmentList.get(16).trim();
//
//
//          studentAssignment=Json.createObjectBuilder().add("header", Json.createObjectBuilder()
//                                                        .add("msgID",l_msgID)
//                                                        .add("correlationID", l_correlationID)
//                                                        .add("service", l_service)
//                                                        .add("operation", l_operation)
//                                                        .add("businessEntity", l_businessEntity)
//                                                        .add("instituteID", l_instituteID)
//                                                        .add("status", l_status)
//                                                        .add("source", l_source)
//                                                        .add("userID",l_userID))
//                                                        .add("body",Json.createObjectBuilder()
//                                                        .add("studentID", l_studentID)
//                                                        .add("subjectID", l_subjectID)
//                                                        .add("assignmentID", l_assignmentID)
//                                                        .add("assignmentDescription", l_assignmentDescription)
//                                                        .add("assignmentType", l_assignmentType)
//                                                        .add("dueDate", l_dueDate)
//                                                        .add("completedDate", l_completedDate)
//                                                        .add("status", l_assignmentStatus)
//                                                        .add("teacherComments", l_teacherComments)
//                                                        .add("parentComment", l_parentComment))
//                                                        .add("auditLog",  Json.createObjectBuilder()
//                                                        .add("makerID", l_makerID)
//                                                        .add("checkerID", l_checkerID)
//                                                        .add("makerDateStamp", l_makerDateStamp)
//                                                        .add("checkerDateStamp", l_checkerDateStamp)
//                                                        .add("recordStatus", l_recordStatus)
//                                                        .add("authStatus", l_authStatus)
//                                                        .add("versionNumber", Integer.toString(p_versionNumber))
//                                                        .add("makerRemarks", l_makerRemarks)
//                                                        .add("checkerRemarks", l_checkerRemarks)).build();
//          
//          dbg("studentAssignmentrequest"+studentAssignment.toString());
//          dbg("before  studentAssignment call");
//          sas.processing(studentAssignment, session);
//          dbg("after studentAssignment call");
//          
//          
//          
//          
//          
//          
//        dbg("end of buildRequestAndCallstudentAssignment");  
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException(ex.toString());
//        }catch(BSProcessingException ex){
//            dbg(ex);
//            throw new BSProcessingException(ex.toString());    
//        }catch(BSValidationException ex){
//            throw ex;
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new BSProcessingException("Exception" + ex.toString());
//        }
//          
// }
   
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
   
}
