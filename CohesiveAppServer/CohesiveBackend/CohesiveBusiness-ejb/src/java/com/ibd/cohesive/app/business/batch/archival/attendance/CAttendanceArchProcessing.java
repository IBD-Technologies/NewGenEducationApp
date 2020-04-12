/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.archival.attendance;

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
public class CAttendanceArchProcessing implements ICAttendanceArchProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public CAttendanceArchProcessing() throws NamingException {
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
             dbg("inside class timeTable arch batch processing");
             String currentDate=bs.getCurrentDate();
             int tableId=mds.getTableMetaData("CLASS", "CLASS_ATTENDANCE_MASTER", session).getI_Tableid();
             Map<String,DBRecord> classAttendanceMap=null;
             boolean tableExistence=true;
                    String startTime=bs.getCurrentDateTime();
                    String[] l_primaryKey={instituteID,standard,section,businessDate};
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update=new HashMap();
                    column_to_Update.put("6", startTime);
                    dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", "CLASS_ATTENDANCE_ARCH_BATCH_STATUS", l_primaryKey, column_to_Update,session); 
                    tc.commit(session, dbSession);
             
             
             
             int yearColId=mds.getColumnMetaData("CLASS", "CLASS_ATTENDANCE_MASTER", "YEAR", session).getI_ColumnID();
             dbg("yearColId"+yearColId);       
             
             try{
                 
                 
               classAttendanceMap=  readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section,"CLASS", "CLASS_ATTENDANCE_MASTER", session, dbSession, "Yes");
                 
                 
             }catch(DBValidationException ex){
                 
                 if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                     
                     tableExistence=false;
                 }else{
                     throw ex;
                 }
             }
             dbg("tableExistence"+tableExistence); 
             
             if(tableExistence){
                 
                Iterator<String>keyIterator= classAttendanceMap.keySet().iterator();
                
                while(keyIterator.hasNext()){
                    
                    String key=keyIterator.next();
                    ArrayList<String>value=classAttendanceMap.get(key).getRecord();
                    String year=value.get(yearColId-1).trim();
                    dbg("key"+key);
                    dbg("year"+year);
                    
                    if(bs.compareYear(year,"Attendance",instituteID,session,dbSession,inject)){
                        
                        String[] l_pkey=key.split("~");
                        
                        dbg("before delete record call for key"+key);
                        
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section,"CLASS","CLASS_ATTENDANCE_MASTER", l_pkey, session);
                        dbg("after delete record call for key"+key);
                     
                        String[] recordValues=getRecordValues(value);
                        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH_"+standard+section+"_"+currentDate,"CLASS",tableId, recordValues);
                        
                        detailTableProcessing(instituteID,standard,section,l_pkey);
                        
                    }
                    
                }
                 
             }
             tc.commit(session, dbSession);
             
             dbg("end of class wise processing");
        batchUtil.classAttendanceArchProcessingSuccessHandler(instituteID, businessDate, standard,section, inject, session, dbSession);
        }catch(DBValidationException ex){
          batchUtil.classAttendanceArchProcessingErrorHandler(instituteID, businessDate, standard,section, ex, inject, session, dbSession);
//        }catch(BSValidationException ex){
//         batchUtil.classAttendanceArchProcessingErrorHandler(instituteID, businessDate, classID, ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.classAttendanceArchProcessingErrorHandler(instituteID, businessDate, standard,section, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.classAttendanceArchProcessingErrorHandler(instituteID, businessDate, standard,section, ex, inject, session, dbSession);
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
          
          
          String[] recordValues={recordList.get(0),recordList.get(1),recordList.get(2),recordList.get(3),recordList.get(4)};
          
          
          
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
          String currentDate=bs.getCurrentDate();
          int tableId=mds.getTableMetaData("CLASS", "CLASS_ATTENDANCE_DETAIL", session).getI_Tableid();
          dbg("tableId"+tableId);
          String year=pkey[2];
          String month=pkey[3];
          
          String l_refID=standard+"*"+section+"*"+year+"*"+month;
          
          Map<String,DBRecord>classAttendanceDetailMap=  readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section,"CLASS", "CLASS_ATTENDANCE_DETAIL", session, dbSession);
          
          List<DBRecord> attList=classAttendanceDetailMap.values().stream().filter(rec->rec.getRecord().get(0).trim().equals(l_refID)).collect(Collectors.toList());
          
          for(int i=0;i<attList.size();i++){
              
              ArrayList<String>detailRecords=attList.get(i).getRecord();
              String studentID=detailRecords.get(1).trim();
              String[] l_pkey={l_refID,studentID};
              
              dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section,"CLASS","CLASS_ATTENDANCE_DETAIL", l_pkey, session);
              
              String[] recordValues=getDetailRecordValues(detailRecords);
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
          
          
          String[] recordValues={recordList.get(0),recordList.get(1),recordList.get(2)};
          
          
          
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
