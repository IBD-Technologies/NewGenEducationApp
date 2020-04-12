/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.unAuth;

import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.scheduler.WorkDispatch;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Stateless
public class UnAuthBatchProcessing implements IUnAuthBatchProcessing{
    
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public UnAuthBatchProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
     
     
     public void processing (String instituteID,String batchName,int no_of_threads)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
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
        IDBTransactionService dbts=inject.getDBTransactionService();
        batchUtil=inject.getBatchUtil(session);
        BusinessService bs=inject.getBusinessService(session);   
        tc=inject.getTransactionControlService();
        dbg("inside unAuth batch processing");
        dbg("instituteID"+instituteID);
        dbg("batchName"+batchName);
        dbg("no_of_threads"+no_of_threads);
        l_businessDate=bs.getCurrentDate();
        dbg("l_businessDate"+l_businessDate);
        boolean recordExistence=true;
        DBRecord batchEODStatusRecord=null;
        String l_eodStatus=new String();  
        String startTime=bs.getCurrentDateTime();
        int maxSequence=0;
        dbg("startTime"+startTime);
           
           try{
               
           
               maxSequence= batchUtil.getBatchMaxSequence(l_businessDate, instituteID, batchName, inject, session, dbSession);
               

               String l_eodPkey[]={instituteID,batchName,l_businessDate,Integer.toString(maxSequence)};
               batchEODStatusRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS",l_eodPkey ,session, dbSession,true);
               l_eodStatus=batchEODStatusRecord.getRecord().get(5).trim();
        }catch(DBValidationException ex){

            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){

                recordExistence=false;
            }
        }
           
        dbg("l_eodStatus"+l_eodStatus);   
           
//        if(recordExistence==false||l_eodStatus.equals("F")){
          int sequence=0; 
          
        if(recordExistence==true){   
          
            if(l_eodStatus.equals("F")){
            
                int batchHistoryMaxSequence=batchUtil.getBatchHistoryMaxSequence(l_businessDate, instituteID, batchName, inject, session, dbSession);
                
                if(batchHistoryMaxSequence>10){
                    return;
                }
                
                sequence=maxSequence;
                Map<String,String>column_to_Update=new HashMap();
                column_to_Update.put("6", "W");
                column_to_Update.put("4", startTime);
                String[] l_pkey={instituteID,batchName,l_businessDate,Integer.toString(sequence)};
                dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS", l_pkey, column_to_Update,session); 
                batchUtil.moveBatchRecordToHistory(instituteID,batchName,l_businessDate,batchEODStatusRecord, session, dbSession, inject);
            }else{ 
             
//                int batchMaxSequence=batchUtil.getBatchMaxSequence(l_businessDate, instituteID, batchName, inject, session, dbSession);
//                          
//                String[] l_pkey={instituteID,batchName,l_businessDate,Integer.toString(batchMaxSequence)};
//                String endTime=bs.getCurrentDateTime();
//                Map<String,String>column_to_Update=new HashMap();
//                column_to_Update.put("5", endTime);
//                column_to_Update.put("6", "S");
//                column_to_Update.put("7", Integer.toString(0));
//                column_to_Update.put("8", Integer.toString(0));
//
//                dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS", l_pkey, column_to_Update,session); 
//
//                tc.commit(session, dbSession);
                return;  
                
                
            } 
            
            
        }else{
            
            sequence=1;
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 95,instituteID,batchName, l_businessDate,startTime," ","W"," "," "," ",Integer.toString(sequence));   
            
        }
        
            tc.commit(session, dbSession);
            
           
           

           

           sequentialProcessing(instituteID,l_businessDate,batchName);
         
           tc.commit(session, dbSession);
         
         
         
//        }    
        dbg("end of unAuth batch processing");
        }catch(Exception ex){
           dbg(ex);
           batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
//       
   }
   
   
   
   
     public void processing(String instituteID,String batchName,int no_of_threads,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(instituteID,batchName,no_of_threads);
       
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
     
     
     private void sequentialProcessing(String instituteID,String l_businessDate,String batchName)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      BatchUtil batchUtil=null; 
      ITransactionControlService tc=null;
      try{
          
           dbg("inside unAuth batch--->sequentialProcessing");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBTransactionService dbts=inject.getDBTransactionService();
           batchUtil=inject.getBatchUtil(session);
           tc=inject.getTransactionControlService();
           BusinessService bs=inject.getBusinessService(session);
           String previousDate=bs.getPreviousDate();
           String currentDate=bs.getCurrentDate();
           dbg("before reading unAuth table");
           Map<String,DBRecord>unAuthEodMap=null;
           
           try{
           
              unAuthEodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"UnAuth_"+previousDate,"INSTITUTE", "IVW_UNAUTH_RECORDS", session, dbSession);
   
           
           }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                 
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_000");  
                          int batchMaxSequence=batchUtil.getBatchMaxSequence(l_businessDate, instituteID, batchName, inject, session, dbSession);
                          
                          String[] l_pkey={instituteID,batchName,l_businessDate,Integer.toString(batchMaxSequence)};
                          String endTime=bs.getCurrentDateTime();
                          Map<String,String>column_to_Update=new HashMap();
                          column_to_Update.put("5", endTime);
                          column_to_Update.put("6", "S");
                          column_to_Update.put("7", Integer.toString(0));
                          column_to_Update.put("8", Integer.toString(0));
             
                          dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS", l_pkey, column_to_Update,session); 
                          
                          tc.commit(session, dbSession);
                          return;  
                        }else{

                              throw ex;
                        }
                       
                   }
           
           
//           List<DBRecord>filteredUnAuthList=unAuthEodMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
           dbg("after reading unAuth table");
//           dbg("filteredUnAuthList size "+filteredUnAuthList.size());
           dbg("unAuthEodMap size"+unAuthEodMap.size()); 
           Iterator<DBRecord>unAuthIterator=unAuthEodMap.values().iterator();
           
           while(unAuthIterator.hasNext()){
               
               ArrayList<String>value=unAuthIterator.next().getRecord();
               
               String service=value.get(0).trim();
               String operation=value.get(1).trim();
               String entityName=value.get(2).trim();
               String entityValue=value.get(3).trim();
               String primaryKey=value.get(4).trim();
               
               
               try{
               
                 dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"UnAuth_"+currentDate,"INSTITUTE",320,service,operation,entityName,entityValue,primaryKey);
       
                 tc.commit(session, dbSession);
               }catch(DBValidationException ex){
                   
                    if(ex.toString().contains("DB_VAL_009")){
                        tc.rollBack(session, dbSession);
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_009");
                        
                    }else{
                        
                        throw ex;
                    }
               }
           
           }
           
           
           
           
           
 
          dbg("end of unAuth batch--->sequentialProcessing");  
         batchUtil.batchSucessHandler(instituteID, l_businessDate, batchName, inject, session, dbSession);
        }catch(DBValidationException ex){
          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
//        }catch(BSValidationException ex){
//          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }
         
     }
     

   
     public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
    
}
