/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.archival.mark;

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
public class CMarkArchProcessing implements ICMarkArchProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public CMarkArchProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
  public void processing (String standard,String section,String instituteID,String businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
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
             dbg("inside class mark arch batch processing");
             String currentDate=bs.getCurrentDate();
             int tableId=mds.getTableMetaData("CLASS", "CLASS_MARK_ENTRY", session).getI_Tableid();
             Map<String,DBRecord> classMarkMap=null;
             boolean tableExistence=true;
                    String startTime=bs.getCurrentDateTime();
                    String[] l_primaryKey={instituteID,standard,section,businessDate};
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update=new HashMap();
                    column_to_Update.put("6", startTime);
                    dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", "CLASS_MARK_ARCH_BATCH_STATUS", l_primaryKey, column_to_Update,session); 
                    tc.commit(session, dbSession);
             
             
             
             int checkerDateColId=mds.getColumnMetaData("CLASS", "CLASS_MARK_ENTRY", "CHECKER_DATE_STAMP", session).getI_ColumnID();
             dbg("checkerDateColId"+checkerDateColId);       
             
             try{
                 
                 
               classMarkMap=  readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section,"CLASS", "CLASS_MARK_ENTRY", session, dbSession, "Yes");
                 
                 
             }catch(DBValidationException ex){
                 
                 if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                     
                     tableExistence=false;
                 }else{
                     throw ex;
                 }
             }
             dbg("tableExistence"+tableExistence); 
             
             if(tableExistence){
                 
                Iterator<String>keyIterator= classMarkMap.keySet().iterator();
                
                while(keyIterator.hasNext()){
                    
                    String key=keyIterator.next();
                    ArrayList<String>value=classMarkMap.get(key).getRecord();
                    String checkerDateStamp=value.get(checkerDateColId-1).trim();
                    dbg("key"+key);
                    dbg("checkerDateStamp"+checkerDateStamp);
                    
                    if(bs.compareCheckerDate(checkerDateStamp,"Mark",instituteID,session,dbSession,inject)){
                        
                        String[] l_pkey=key.split("~");
                        
                        dbg("before delete record call for key"+key);
                        
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section,"CLASS","CLASS_MARK_ENTRY", l_pkey, session);
                        dbg("after delete record call for key"+key);
                     
                        String[] recordValues=getRecordValues(value);
                        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH_"+standard+section+"_"+currentDate,"CLASS",tableId, recordValues);
                        
                        detailTableProcessing(instituteID,standard,section,l_pkey);
                        
                    }
                    
                }
                 
             }
             tc.commit(session, dbSession);
             
             dbg("end of class wise processing");
        batchUtil.classMarkArchProcessingSuccessHandler(instituteID, businessDate, standard,section, inject, session, dbSession);
        }catch(DBValidationException ex){
          batchUtil.classMarkArchProcessingErrorHandler(instituteID, businessDate, standard,section, ex, inject, session, dbSession);
//        }catch(BSValidationException ex){
//         batchUtil.classMarkArchProcessingErrorHandler(instituteID, businessDate, classID, ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.classMarkArchProcessingErrorHandler(instituteID, businessDate, standard,section, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.classMarkArchProcessingErrorHandler(instituteID, businessDate, standard,section, ex, inject, session, dbSession);
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
          
          
          String[] recordValues={recordList.get(0),recordList.get(1),recordList.get(2),recordList.get(3),recordList.get(4),recordList.get(5),recordList.get(6),recordList.get(7),recordList.get(8),recordList.get(9),recordList.get(10),recordList.get(11),recordList.get(12)};
          
          
          
          dbg("end of getRecordValues");
          return recordValues;
      }catch(Exception ex){
           dbg(ex);
        throw new BSProcessingException(ex.toString());   
      }
      
  }
  
  private void detailTableProcessing(String instituteID,String standard,String section,String[] pkey)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
      try{
          dbg("inside detailTableProcessing");
          IDBReadBufferService readBuffer=inject.getDBReadBufferService();
          IBDProperties i_db_properties=session.getCohesiveproperties();
          IDBTransactionService dbts=inject.getDBTransactionService();
          BusinessService bs=inject.getBusinessService(session);
          IMetaDataService mds=inject.getMetadataservice();
          String l_exam=pkey[2];
          String l_subjectID=pkey[3];
          String currentDate=bs.getCurrentDate();
          int tableId=mds.getTableMetaData("CLASS", "STUDENT_MARKS", session).getI_Tableid();
          dbg("l_exam"+l_exam);
          dbg("tableId"+tableId);
          
          
          Map<String,DBRecord>classMarkDetailMap=  readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section,"CLASS", "STUDENT_MARKS", session, dbSession);
          
          List<DBRecord> examWiseList=classMarkDetailMap.values().stream().filter(rec->rec.getRecord().get(2).trim().equals(l_exam)&&rec.getRecord().get(3).trim().equals(l_subjectID)).collect(Collectors.toList());
          
          for(int i=0;i<examWiseList.size();i++){
              
              ArrayList<String>markList=examWiseList.get(i).getRecord();
              String l_studentID=markList.get(4).trim();
              dbg("l_studentID"+l_studentID);
              String[] l_pkey={standard,section,l_exam,l_subjectID,l_studentID};
              
              dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section,"CLASS","STUDENT_MARKS", l_pkey, session);
              
              String[] recordValues=getDetailRecordValues(markList);
              dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH_"+standard+section+"_"+currentDate,"CLASS",tableId, recordValues);
              
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
          
          
          String[] recordValues={recordList.get(0),recordList.get(1),recordList.get(2),recordList.get(3),recordList.get(4),recordList.get(5),recordList.get(6),recordList.get(7),recordList.get(8)};
          
          
          
          dbg("end of getDetailRecordValues");
          return recordValues;
      }catch(Exception ex){
           dbg(ex);
        throw new BSProcessingException(ex.toString());   
      }
      
  }
  
   public void processing(String standard,String section,String instituteID,String businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(standard,section,instituteID,businessDate);
       
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
   public Future<String> parallelProcessing(String standard,String section,String instituteID,String businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    try{   
    
        processing(standard,section,instituteID,businessDate);
        
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
