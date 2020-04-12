/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.archival.profile;

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
public class TProfileArchProcessing implements ITProfileArchProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public TProfileArchProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
  public void processing (String teacherID,String instituteID,String businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
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
             dbg("teacher Profile arch processing");
             String currentDate=bs.getCurrentDate();
             int tableId=mds.getTableMetaData("TEACHER", "TVW_TEACHER_PROFILE", session).getI_Tableid();
             Map<String,DBRecord> teacherProfileMap=null;
             boolean tableExistence=true;
                    String startTime=bs.getCurrentDateTime();
                    String[] l_primaryKey={instituteID,teacherID,businessDate};
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update=new HashMap();
                    column_to_Update.put("5", startTime);
                    dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", "TEACHER_PROFILE_ARCH_BATCH_STATUS", l_primaryKey, column_to_Update,session); 
                    tc.commit(session, dbSession);
             
             
             
             int checkerDateColId=mds.getColumnMetaData("TEACHER", "TVW_TEACHER_PROFILE", "CHECKER_DATE_STAMP", session).getI_ColumnID();
             dbg("checkerDateColId"+checkerDateColId);       
             
             int recStatColId=mds.getColumnMetaData("TEACHER", "TVW_TEACHER_PROFILE", "RECORD_STATUS", session).getI_ColumnID();
             dbg("recStatColId"+recStatColId);
             
             try{
                 
                 
               teacherProfileMap=  readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID,"TEACHER", "TVW_TEACHER_PROFILE", session, dbSession, "Yes");
                 
                 
             }catch(DBValidationException ex){
                 
                 if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                     
                     tableExistence=false;
                 }else{
                     throw ex;
                 }
             }
             dbg("tableExistence"+tableExistence); 
             
             if(tableExistence){
                 
                Iterator<String>keyIterator= teacherProfileMap.keySet().iterator();
                
                while(keyIterator.hasNext()){
                    
                    String key=keyIterator.next();
                    ArrayList<String>value=teacherProfileMap.get(key).getRecord();
                    String checkerDateStamp=value.get(checkerDateColId-1).trim();
                    String recStatus=value.get(recStatColId-1).trim();
                    dbg("key"+key);
                    dbg("checkerDateStamp"+checkerDateStamp);
                    dbg("recStatus"+recStatus);
                    
                    if(recStatus.equals("D")){
                    
                    if(bs.compareCheckerDate(checkerDateStamp,"Profile",instituteID,session,dbSession,inject)){
                        
                        String[] l_pkey=key.split("~");
                        
                        dbg("before delete record call for key"+key);
                        
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID,"TEACHER","TVW_TEACHER_PROFILE", l_pkey, session);
                        dbg("after delete record call for key"+key);
                     
                        String[] recordValues=getRecordValues(value);
                        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH_"+teacherID+"_"+currentDate,"TEACHER",tableId, recordValues);
                        
                        contactPersonDetailTableProcessing(instituteID,teacherID);
                    }
                    
                }
                }
             }
             
             tc.commit(session, dbSession);
             dbg("end of teacher wise processing");
        batchUtil.teacherProfileArchProcessingSuccessHandler(instituteID, businessDate, teacherID, inject, session, dbSession);
        }catch(DBValidationException ex){
          batchUtil.teacherProfileArchProcessingErrorHandler(instituteID, businessDate, teacherID, ex, inject, session, dbSession);
//        }catch(BSValidationException ex){
//         batchUtil.teacherProfileArchProcessingErrorHandler(instituteID, businessDate, teacherID, ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.teacherProfileArchProcessingErrorHandler(instituteID, businessDate, teacherID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.teacherProfileArchProcessingErrorHandler(instituteID, businessDate, teacherID, ex, inject, session, dbSession);
        }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
      
  }
  
  
  private void contactPersonDetailTableProcessing(String instituteID,String teacherID)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
      try{
          dbg("inside contactPersonDetailTableProcessing");
          IDBReadBufferService readBuffer=inject.getDBReadBufferService();
          IBDProperties i_db_properties=session.getCohesiveproperties();
          IDBTransactionService dbts=inject.getDBTransactionService();
          BusinessService bs=inject.getBusinessService(session);
          IMetaDataService mds=inject.getMetadataservice();
          String currentDate=bs.getCurrentDate();
          int tableId=mds.getTableMetaData("TEACHER", "TVW_CONTACT_PERSON_DETAILS", session).getI_Tableid();
          
          
          Map<String,DBRecord>teacherMarkDetailMap=  readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID,"TEACHER", "TVW_CONTACT_PERSON_DETAILS", session, dbSession);
          
          List<DBRecord> contactPersonList=teacherMarkDetailMap.values().stream().collect(Collectors.toList());
          
          for(int i=0;i<contactPersonList.size();i++){
              
              ArrayList<String>cpList=contactPersonList.get(i).getRecord();
              String l_cpID=cpList.get(1).trim();
              dbg("l_cpID"+l_cpID);
              String[] l_pkey={l_cpID};
              
              dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID,"TEACHER","TVW_CONTACT_PERSON_DETAILS", l_pkey, session);
              
              String[] recordValues=getContactPersonDetailRecordValues(cpList);
              dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH_"+teacherID+"_"+currentDate,"TEACHER",tableId, recordValues);
              
          }
          
          dbg("end of contactPersonDetailTableProcessing");
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
  
  private String[] getRecordValues(ArrayList<String> recordList)throws BSProcessingException{
      
      try{
          dbg("inside getRecordValues");
          
          
String[] recordValues={recordList.get(0),recordList.get(1),recordList.get(2),recordList.get(3),recordList.get(4),recordList.get(5),recordList.get(6),recordList.get(7),recordList.get(8),recordList.get(9),recordList.get(10),recordList.get(11),recordList.get(12),recordList.get(13),recordList.get(14),recordList.get(15),recordList.get(16),recordList.get(17),recordList.get(18),recordList.get(19),recordList.get(20),recordList.get(21),recordList.get(22),recordList.get(23),recordList.get(24),recordList.get(25)};          
          
          
          dbg("end of getRecordValues");
          return recordValues;
      }catch(Exception ex){
           dbg(ex);
        throw new BSProcessingException(ex.toString());   
      }
      
  }
  
   private String[] getContactPersonDetailRecordValues(ArrayList<String> recordList)throws BSProcessingException{
      
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
  
   public void processing(String teacherID,String instituteID,String businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(teacherID,instituteID,businessDate);
       
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
   public Future<String> parallelProcessing(String teacherID,String instituteID,String businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    try{   
    
        processing(teacherID,instituteID,businessDate);
        
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
