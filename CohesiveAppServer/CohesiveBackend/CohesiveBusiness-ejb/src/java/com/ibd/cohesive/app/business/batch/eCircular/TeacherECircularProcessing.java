/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.eCircular;

import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
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
public class TeacherECircularProcessing implements ITeacherECircularProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public TeacherECircularProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
     
     public void processing (String instituteID,String teacherID,String eCircularID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       BatchUtil batchUtil=null;
       boolean l_session_created_now=false;
       ITransactionControlService tc=null;
       try{
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();  
        dbg("inside teacher eCircular processing ");
        dbg("instituteID"+instituteID);
        dbg("eCircularID"+eCircularID);
        dbg("l_businessDate"+l_businessDate);
        dbg("teacherID"+teacherID);
        
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
          String[]l_pkey={instituteID,eCircularID,teacherID,l_businessDate};
          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular"+i_db_properties.getProperty("FOLDER_DELIMITER")+eCircularID, "BATCH", "TEACHER_E_CIRCULAR_EOD_STATUS", l_pkey, column_to_Update,session); 
          tc.commit(session, dbSession);
          
          
       
              buildRequestAndCallTeacherECircular(teacherID,eCircularID,1,instituteID,l_businessDate);

          

         batchUtil.teacherECircularProcessingSuccessHandler(instituteID, l_businessDate, eCircularID, teacherID, inject, session, dbSession);
        
         dbg("end of teacher eCircular processing");
        }catch(DBValidationException ex){
          batchUtil.teacherECircularProcessingErrorHandler(instituteID, l_businessDate, eCircularID, teacherID, ex, inject, session, dbSession);
        }catch(BSValidationException ex){
          batchUtil.teacherECircularProcessingErrorHandler(instituteID, l_businessDate, eCircularID, teacherID, ex, inject, session, dbSession);
      
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.teacherECircularProcessingErrorHandler(instituteID, l_businessDate, eCircularID, teacherID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.teacherECircularProcessingErrorHandler(instituteID, l_businessDate, eCircularID, teacherID, ex, inject, session, dbSession);
     }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
}

    public void processing(String instituteID,String teacherID,String eCircularID,String l_businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(instituteID,teacherID,eCircularID,l_businessDate);
       
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
   public Future<String> parallelProcessing(String instituteID,String teacherID,String eCircularID,String l_businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    try{   
    
        processing(instituteID,teacherID,eCircularID,l_businessDate);
        
              return new AsyncResult<String>("Success");

       
        }catch(Exception ex){
           dbg(ex);
           return new AsyncResult<String>("Fail");
     }
    
}
   
    private void buildRequestAndCallTeacherECircular(String l_teacherID,String l_eCircularID,int p_versionNumber,String instituteID,String businessDate)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
     ITransactionControlService tc=null;
        try{
        dbg("inside buildRequestAndCallTeacherECircular") ;   
        dbg("versionNumber"+p_versionNumber);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=instituteID;
        IDBTransactionService dbts=inject.getDBTransactionService();
        tc=inject.getTransactionControlService();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String[] l_primaryKey={l_eCircularID};
        

        
       
        
        
           
         try{
              dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular","TEACHER",348,l_teacherID,l_eCircularID,"N");
              tc.commit(session, dbSession);
        }catch(DBValidationException ex){
            tc.rollBack(session, dbSession);
            if(!ex.toString().contains("DB_VAL_009")){
                dbg(ex);
                throw ex;
            }
        }
          
   
          
        dbg("end of buildRequestAndCallteacherECircular");  
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
     
     
     
     
//      public void processing (String instituteID,String teacherID,String eCircularID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//       BatchUtil batchUtil=null;
//       boolean l_session_created_now=false;
//       ITransactionControlService tc=null;
//       try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();  
//        dbg("inside teacher eCircular processing ");
//        dbg("instituteID"+instituteID);
//        dbg("eCircularID"+eCircularID);
//        dbg("l_businessDate"+l_businessDate);
//        dbg("teacherID"+teacherID);
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
//          String[]l_pkey={instituteID,eCircularID,teacherID,l_businessDate};
//          dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "TEACHER_E_CIRCULAR_EOD_STATUS", l_pkey, column_to_Update,session); 
//          tc.commit(session, dbSession);
//          //start time update ends
//          DBRecord teacherECircularRec;
//          String versionNumber=null;
//          boolean recordExistence=true;
//          
//          try{
//        
//            String[]l_primaryKey={eCircularID};  
//            teacherECircularRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID,"TEACHER", "SVW_TEACHER_E_CIRCULAR", l_primaryKey, session, dbSession,true);
//            versionNumber=teacherECircularRec.getRecord().get(15).trim();
//            dbg("versionNumber"+versionNumber);
//         }catch(DBValidationException ex){
//            if(ex.toString().contains("DB_VAL_011")){
//                
//                dbg("printing error inside teacher eCircular processing"+ex.toString());
//                recordExistence=false;
//                
//            }else{
//                throw ex;
//            }
//         }catch(Exception ex){
//             
//             dbg("printing error inside teacher eCircular processing"+ex.toString());
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
//              buildRequestAndCallTeacherECircular(teacherID,eCircularID,1,instituteID);
//          }else{
//              
//              buildRequestAndCallTeacherECircular(teacherID,eCircularID,Integer.parseInt(versionNumber)+1,instituteID);
//          }
//          
//
//         batchUtil.teacherECircularProcessingSuccessHandler(instituteID, l_businessDate, eCircularID, teacherID, inject, session, dbSession);
//        
//         dbg("end of teacher eCircular processing");
//        }catch(DBValidationException ex){
//          batchUtil.teacherECircularProcessingErrorHandler(instituteID, l_businessDate, eCircularID, teacherID, ex, inject, session, dbSession);
//        }catch(BSValidationException ex){
//          batchUtil.teacherECircularProcessingErrorHandler(instituteID, l_businessDate, eCircularID, teacherID, ex, inject, session, dbSession);
//      
//        }catch(DBProcessingException ex){
//          dbg(ex);
//          batchUtil.teacherECircularProcessingErrorHandler(instituteID, l_businessDate, eCircularID, teacherID, ex, inject, session, dbSession);
//        }catch(Exception ex){
//           dbg(ex);
//           batchUtil.teacherECircularProcessingErrorHandler(instituteID, l_businessDate, eCircularID, teacherID, ex, inject, session, dbSession);
//     }finally{
//               if(l_session_created_now){    
//                  dbSession.clearSessionObject();
//                  session.clearSessionObject();
//               }
//           }
//}
//
//    public void processing(String instituteID,String teacherID,String eCircularID,String l_businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//      
//       CohesiveSession tempSession = this.session;
//       
//       try{
//           
//           this.session=session;
//           processing(instituteID,teacherID,eCircularID,l_businessDate);
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
//   public Future<String> parallelProcessing(String instituteID,String teacherID,String eCircularID,String l_businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//   
//    try{   
//    
//        processing(instituteID,teacherID,eCircularID,l_businessDate);
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
//    private void buildRequestAndCallTeacherECircular(String l_teacherID,String l_eCircularID,int p_versionNumber,String instituteID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
//     
//        try{
//        dbg("inside buildRequestAndCallTeacherECircular") ;   
//        dbg("versionNumber"+p_versionNumber);
//        ITeacherECircularService sas=inject.getTeacherECircularService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        String l_instituteID=instituteID;
//        JsonObject teacherECircular;
//        String l_msgID="";
//        String l_correlationID="";
//        String l_service="TeacherECircular";
//        String l_operation="AutoAuth";
//        JsonArray l_businessEntity=Json.createArrayBuilder().add(Json.createObjectBuilder().add("entityName", "teacherID")
//                                                                                             .add("entityValue", l_teacherID)).build();
//        String l_userID="system";
//        String l_source="cohesive_backend";
//        String l_status=" ";
//        
//        String[]l_pkey={l_eCircularID};
//        ArrayList<String> eCircularList=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_E_CIRCULAR", l_pkey, session,dbSession).getRecord();
//        
//        
//        String l_eCircularDescription=eCircularList.get(3).trim();
//        String l_contentPath=eCircularList.get(4).trim();
//        
//        String l_makerID="";
//        String l_checkerID="";
//        String l_makerDateStamp="";
//        String l_checkerDateStamp="";
//        String l_recordStatus=eCircularList.get(9).trim();
//        String l_authStatus=eCircularList.get(10).trim();
//        String l_makerRemarks=eCircularList.get(12).trim();
//        String l_checkerRemarks=eCircularList.get(13).trim();
//
//
//          teacherECircular=Json.createObjectBuilder().add("header", Json.createObjectBuilder()
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
//                                                        .add("teacherID", l_teacherID)
//                                                        .add("eCircularID", l_eCircularID)
//                                                        .add("eCircularDescription", l_eCircularDescription)
//                                                        .add("contentPath", l_contentPath))
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
//          dbg("teacherECircularrequest"+teacherECircular.toString());
//          dbg("before  teacherECircular call");
//          sas.processing(teacherECircular, session);
//          dbg("after teacherECircular call");
//          
//          
//          
//          
//          
//          
//        dbg("end of buildRequestAndCallteacherECircular");  
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
