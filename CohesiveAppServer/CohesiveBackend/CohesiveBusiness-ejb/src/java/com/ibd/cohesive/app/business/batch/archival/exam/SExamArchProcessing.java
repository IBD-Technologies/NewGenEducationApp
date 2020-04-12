/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.archival.exam;

import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Stateless
public class SExamArchProcessing implements ISExamArchProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public SExamArchProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
  public void processing (String studentID,String instituteID,String businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       BatchUtil batchUtil=null;
       boolean l_session_created_now=false;
       
       try{
             session.createSessionObject();
             dbSession.createDBsession(session);
             l_session_created_now=session.isI_session_created_now();   
             IDBReadBufferService readBuffer=inject.getDBReadBufferService();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IDBTransactionService dbts=inject.getDBTransactionService();
             BusinessService bs=inject.getBusinessService(session);
             batchUtil=inject.getBatchUtil(session);
             IMetaDataService mds=inject.getMetadataservice();
             ITransactionControlService tc=inject.getTransactionControlService();
             String currentDate=bs.getCurrentDate();
             int tableId=mds.getTableMetaData("STUDENT", "SVW_STUDENT_EXAM_SCHEDULE_MASTER", session).getI_Tableid();
             Map<String,DBRecord> studentExamMap=null;
             boolean tableExistence=true;
                    String startTime=bs.getCurrentDateTime();
                    String[] l_primaryKey={instituteID,studentID,businessDate};
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update=new HashMap();
                    column_to_Update.put("5", startTime);
                    dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", "STUDENT_EXAM_ARCH_BATCH_STATUS", l_primaryKey, column_to_Update,session); 
                    tc.commit(session, dbSession);
             
             
             
             int checkerDateColId=mds.getColumnMetaData("STUDENT", "SVW_STUDENT_EXAM_SCHEDULE_MASTER", "CHECKER_DATE_STAMP", session).getI_ColumnID();
             dbg("checkerDateColId"+checkerDateColId);       
             
             try{
                 
                 
               studentExamMap=  readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_EXAM_SCHEDULE_MASTER", session, dbSession, "Yes");
                 
                 
             }catch(DBValidationException ex){
                 
                 if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                     
                     tableExistence=false;
                 }else{
                     throw ex;
                 }
             }
             dbg("tableExistence"+tableExistence); 
             
             if(tableExistence){
                 
                Iterator<String>keyIterator= studentExamMap.keySet().iterator();
                
                while(keyIterator.hasNext()){
                    
                    String key=keyIterator.next();
                    ArrayList<String>value=studentExamMap.get(key).getRecord();
                    String checkerDateStamp=value.get(checkerDateColId-1).trim();
                    dbg("key"+key);
                    dbg("checkerDateStamp"+checkerDateStamp);
                    
                    if(bs.compareCheckerDate(checkerDateStamp,"Exam",instituteID,session,dbSession,inject)){
                        
                        String[] l_pkey=key.split("~");
                        
                        dbg("before delete record call for key"+key);
                        
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT","SVW_STUDENT_EXAM_SCHEDULE_MASTER", l_pkey, session);
                        dbg("after delete record call for key"+key);
                     
                        String[] recordValues=getRecordValues(value);
                        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH_"+studentID+"_"+currentDate,"STUDENT",tableId, recordValues);
                        
                        detailTableProcessing(instituteID,studentID,l_pkey);
                        
                    }
                    
                }
                 
             }
             
             tc.commit(session, dbSession);
             dbg("end of student wise processing");
        batchUtil.studentExamArchProcessingSuccessHandler(instituteID, businessDate, studentID, inject, session, dbSession);
        }catch(DBValidationException ex){
          batchUtil.studentExamArchProcessingErrorHandler(instituteID, businessDate, studentID, ex, inject, session, dbSession);
//        }catch(BSValidationException ex){
//         batchUtil.studentExamArchProcessingErrorHandler(instituteID, businessDate, studentID, ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.studentExamArchProcessingErrorHandler(instituteID, businessDate, studentID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.studentExamArchProcessingErrorHandler(instituteID, businessDate, studentID, ex, inject, session, dbSession);
        }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
      
  }
  
  private String[] getRecordValues(ArrayList<String> recordList)throws BSProcessingException{
      
      try{
          dbg("inside getRecordValues");
          
          
          String[] recordValues={recordList.get(0),recordList.get(1),recordList.get(2),recordList.get(3),recordList.get(4),recordList.get(5),recordList.get(6),recordList.get(7),recordList.get(8),recordList.get(9),recordList.get(10)};
          
          
          
          dbg("end of getRecordValues");
          return recordValues;
      }catch(Exception ex){
           dbg(ex);
        throw new BSProcessingException(ex.toString());   
      }
      
  }
  
  private void detailTableProcessing(String instituteID,String studentID,String[] pkey)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
      try{
          dbg("inside detailTableProcessing");
          IDBReadBufferService readBuffer=inject.getDBReadBufferService();
          IBDProperties i_db_properties=session.getCohesiveproperties();
          IDBTransactionService dbts=inject.getDBTransactionService();
          BusinessService bs=inject.getBusinessService(session);
          IMetaDataService mds=inject.getMetadataservice();
          String l_exam=pkey[1];
          String currentDate=bs.getCurrentDate();
          int tableId=mds.getTableMetaData("STUDENT", "SVW_STUDENT_EXAM_SCHEDULE_DETAIL", session).getI_Tableid();
          dbg("l_exam"+l_exam);
          dbg("tableId"+tableId);
          
          
          Map<String,DBRecord>studentExamDetailMap=  readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_EXAM_SCHEDULE_DETAIL", session, dbSession);
          
          List<DBRecord> examWiseList=studentExamDetailMap.values().stream().filter(rec->rec.getRecord().get(1).trim().equals(l_exam)).collect(Collectors.toList());
          
          for(int i=0;i<examWiseList.size();i++){
              
              ArrayList<String>examList=examWiseList.get(i).getRecord();
              String l_subjectID=examList.get(2).trim();
              dbg("l_subjectID"+l_subjectID);
              String[] l_pkey={studentID,l_exam,l_subjectID};
              
              dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT","SVW_STUDENT_EXAM_SCHEDULE_DETAIL", l_pkey, session);
              
              String[] recordValues=getDetailRecordValues(examList);
              dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH_"+studentID+"_"+currentDate,"STUDENT",tableId, recordValues);
              
          }
          
          dbg("end of detailTableProcessing");
      }catch(DBValidationException ex){
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
     }
      
  }
   private String[] getDetailRecordValues(ArrayList<String> recordList)throws BSProcessingException{
      
      try{
          dbg("inside getDetailRecordValues");
          
          
          String[] recordValues={recordList.get(0),recordList.get(1),recordList.get(2),recordList.get(3),recordList.get(4),recordList.get(5),recordList.get(6),recordList.get(7),recordList.get(8),recordList.get(9)};
          
          
          
          dbg("end of getDetailRecordValues");
          return recordValues;
      }catch(Exception ex){
           dbg(ex);
        throw new BSProcessingException(ex.toString());   
      }
      
  }
  
   public void processing(String studentID,String instituteID,String businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(studentID,instituteID,businessDate);
       
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
   public Future<String> parallelProcessing(String studentID,String instituteID,String businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    try{   
    
        processing(studentID,instituteID,businessDate);
        
              return new AsyncResult<String>("Success");

       
        }catch(Exception ex){
           dbg(ex);
           return new AsyncResult<String>("Fail");
     }
    
}
  
  public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
}
