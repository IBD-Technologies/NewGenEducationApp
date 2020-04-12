/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.feeNotification;

import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.MessageInput;
import com.ibd.cohesive.app.business.util.NotificationUtil;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
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
public class StudentFeeNotificationProcessing implements IStudentFeeNotificationProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public StudentFeeNotificationProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
     
        public void processing (String instituteID,String studentID,String feeID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       BatchUtil batchUtil=null;
       boolean l_session_created_now=false;
       ITransactionControlService tc=null;
       try{
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();  
        dbg("inside student fee processing ");
        dbg("instituteID"+instituteID);
        dbg("feeID"+feeID);
        dbg("l_businessDate"+l_businessDate);
        dbg("studentID"+studentID);
        
        tc=inject.getTransactionControlService();
        BusinessService bs=inject.getBusinessService(session);
        String startTime=bs.getCurrentDateTime();
        batchUtil=inject.getBatchUtil(session);
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBTransactionService dbts=inject.getDBTransactionService();
        NotificationUtil notificationUtil =inject.getNotificationUtil(session);

                 //start time update starts
          Map<String,String>column_to_Update=new HashMap();
          column_to_Update.put("7", startTime);
          String[]l_pkey={instituteID,feeID,studentID,l_businessDate};
          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"FeeNotification"+i_db_properties.getProperty("FOLDER_DELIMITER")+feeID, "BATCH", "STUDENT_FEE_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
          tc.commit(session, dbSession);
          
          
          MessageInput feemessageInput=notificationUtil.getFeeMessageInput(instituteID, studentID, feeID, session, dbSession, inject);
          
          notificationUtil.messageGeneration(instituteID, studentID, feemessageInput, session, dbSession, inject);
          
          
          
         batchUtil.studentFeeNotificationProcessingSuccessHandler(instituteID, l_businessDate, feeID, studentID, inject, session, dbSession);
        
         dbg("end of student fee processing");
        }catch(DBValidationException ex){
          batchUtil.studentFeeNotificationProcessingErrorHandler(instituteID, l_businessDate, feeID, studentID, ex, inject, session, dbSession);
        }catch(BSValidationException ex){
          batchUtil.studentFeeNotificationProcessingErrorHandler(instituteID, l_businessDate, feeID, studentID, ex, inject, session, dbSession);
      
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.studentFeeNotificationProcessingErrorHandler(instituteID, l_businessDate, feeID, studentID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.studentFeeNotificationProcessingErrorHandler(instituteID, l_businessDate, feeID, studentID, ex, inject, session, dbSession);
     }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
}

    public void processing(String instituteID,String studentID,String feeID,String l_businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(instituteID,studentID,feeID,l_businessDate);
       
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
   public Future<String> parallelProcessing(String instituteID,String studentID,String feeID,String l_businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    try{   
    
        processing(instituteID,studentID,feeID,l_businessDate);
        
              return new AsyncResult<String>("Success");

       
        }catch(Exception ex){
           dbg(ex);
           return new AsyncResult<String>("Fail");
     }
    
}
   
//    private void buildRequestAndCallStudentFee(String l_studentID,String l_feeID,int p_versionNumber,String instituteID,String businessDate)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
//     ITransactionControlService tc=null;
//        try{
//        dbg("inside buildRequestAndCallStudentFee") ;   
//        dbg("versionNumber"+p_versionNumber);
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        String l_instituteID=instituteID;
//        IDBTransactionService dbts=inject.getDBTransactionService();
//        tc=inject.getTransactionControlService();
//        BusinessService bs=inject.getBusinessService(session);
//        String currentDate=bs.getCurrentDate();
//           
//         try{
//              dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"FEE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","FEE",63,l_studentID,l_feeID);
//       
//              
//              
//              
////              bs.updateRecordInTodayNotification(instituteID, l_studentID, l_feeID, l_instituteID, businessDate, session, dbSession, inject);
//              
//            
//              
//              
//              
//              tc.commit(session, dbSession);
//        }catch(DBValidationException ex){
//            tc.rollBack(session, dbSession);
//            if(!ex.toString().contains("DB_VAL_009")){
//                dbg(ex);
//                throw ex;
//            }
//        }
//          
//   
//          
//        dbg("end of buildRequestAndCallstudentFee");  
//        }catch(DBValidationException ex){
//            dbg(ex);
//            tc.rollBack(session, dbSession);
//            throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            tc.rollBack(session, dbSession);
//            throw new DBProcessingException(ex.toString());
////        }catch(BSProcessingException ex){
////            dbg(ex);
////            throw new BSProcessingException(ex.toString());    
////        }catch(BSValidationException ex){
////            throw ex;
//        }catch (Exception ex) {
//            dbg(ex);
//            tc.rollBack(session, dbSession);
//            throw new BSProcessingException("Exception" + ex.toString());
//        }
//          
// }
     
     
//      public void processing (String instituteID,String studentID,String feeID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//       BatchUtil batchUtil=null;
//       boolean l_session_created_now=false;
//       ITransactionControlService tc=null;
//       try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();  
//        dbg("inside student fee processing ");
//        dbg("instituteID"+instituteID);
//        dbg("feeID"+feeID);
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
//          String[]l_pkey={instituteID,feeID,studentID,l_businessDate};
//          dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_FEE_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
//          tc.commit(session, dbSession);
//          //start time update ends
//          DBRecord studentFeeRec;
//          String versionNumber=null;
//          boolean recordExistence=true;
//          
//          try{
//        
//            String[]l_primaryKey={feeID};  
//            studentFeeRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_FEE_MANAGEMENT", l_primaryKey, session, dbSession,true);
//            versionNumber=studentFeeRec.getRecord().get(15).trim();
//            dbg("versionNumber"+versionNumber);
//         }catch(DBValidationException ex){
//            if(ex.toString().contains("DB_VAL_011")){
//                
//                dbg("printing error inside student fee processing"+ex.toString());
//                recordExistence=false;
//                
//            }else{
//                throw ex;
//            }
//         }catch(Exception ex){
//             
//             dbg("printing error inside student fee processing"+ex.toString());
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
//              buildRequestAndCallStudentFee(studentID,feeID,1,instituteID);
//          }else{
//              
//              buildRequestAndCallStudentFee(studentID,feeID,Integer.parseInt(versionNumber)+1,instituteID);
//          }
//          
//
//         batchUtil.studentfeeNotificationProcessingSuccessHandler(instituteID, l_businessDate, feeID, studentID, inject, session, dbSession);
//        
//         dbg("end of student fee processing");
//        }catch(DBValidationException ex){
//          batchUtil.studentfeeNotificationProcessingErrorHandler(instituteID, l_businessDate, feeID, studentID, ex, inject, session, dbSession);
//        }catch(BSValidationException ex){
//          batchUtil.studentfeeNotificationProcessingErrorHandler(instituteID, l_businessDate, feeID, studentID, ex, inject, session, dbSession);
//      
//        }catch(DBProcessingException ex){
//          dbg(ex);
//          batchUtil.studentfeeNotificationProcessingErrorHandler(instituteID, l_businessDate, feeID, studentID, ex, inject, session, dbSession);
//        }catch(Exception ex){
//           dbg(ex);
//           batchUtil.studentfeeNotificationProcessingErrorHandler(instituteID, l_businessDate, feeID, studentID, ex, inject, session, dbSession);
//     }finally{
//               if(l_session_created_now){    
//                  dbSession.clearSessionObject();
//                  session.clearSessionObject();
//               }
//           }
//}
//
//    public void processing(String instituteID,String studentID,String feeID,String l_businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//      
//       CohesiveSession tempSession = this.session;
//       
//       try{
//           
//           this.session=session;
//           processing(instituteID,studentID,feeID,l_businessDate);
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
//   public Future<String> parallelProcessing(String instituteID,String studentID,String feeID,String l_businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//   
//    try{   
//    
//        processing(instituteID,studentID,feeID,l_businessDate);
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
//    private void buildRequestAndCallStudentFee(String l_studentID,String l_feeID,int p_versionNumber,String instituteID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
//     
//        try{
//        dbg("inside buildRequestAndCallStudentFee") ;   
//        dbg("versionNumber"+p_versionNumber);
//        IStudentFeeManagementService sfms=inject.getStudentFeeManagementService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        String l_instituteID=instituteID;
//        JsonObject studentFee;
//        String l_msgID="";
//        String l_correlationID="";
//        String l_service="StudentFeeManagement";
//        String l_operation="AutoAuth";
//        JsonArray l_businessEntity=Json.createArrayBuilder().add(Json.createObjectBuilder().add("entityName", "studentID")
//                                                                                             .add("entityValue", l_studentID)).build();
//        String l_userID="system";
//        String l_source="cohesive_backend";
//        String l_status=" ";
//        
//        String[]l_pkey={l_feeID};
//        ArrayList<String> feeList=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_FEE_MANAGEMENT", l_pkey, session,dbSession).getRecord();
//        
//        
//        String l_feeType=feeList.get(3).trim();
//        String l_amount=feeList.get(4).trim();
//        String l_dueDate=feeList.get(5).trim();
//        String l_feestatus="U";
//        String l_feePaid=" ";
//        String l_outStanding=" ";
//        String l_paidDate=" ";
//        String l_paymentMode=" ";
//        
//        String l_makerID="";
//        String l_checkerID="";
//        String l_makerDateStamp="";
//        String l_checkerDateStamp="";
//        String l_recordStatus=feeList.get(11).trim();
//        String l_authStatus=feeList.get(12).trim();
//        String l_makerRemarks=feeList.get(14).trim();
//        String l_checkerRemarks=feeList.get(15).trim();
//
//
//          studentFee=Json.createObjectBuilder().add("header", Json.createObjectBuilder()
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
//                                                        .add("feeID", l_feeID)
//                                                        .add("feeType", l_feeType)
//                                                        .add("amount", l_amount)        
//                                                        .add("dueDate", l_dueDate)
//                                                        .add("status", l_feestatus)
//                                                        .add("feePaid", l_feePaid)
//                                                        .add("outStanding", l_outStanding)
//                                                        .add("paidDate", l_paidDate)
//                                                        .add("paymentMode", l_paymentMode))
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
//          dbg("studentFeerequest"+studentFee.toString());
//          dbg("before  studentFee call");
//          sfms.processing(studentFee, session);
//          dbg("after studentFee call");
//          
//          
//          
//          
//          
//          
//        dbg("end of buildRequestAndCallstudentFee");  
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
