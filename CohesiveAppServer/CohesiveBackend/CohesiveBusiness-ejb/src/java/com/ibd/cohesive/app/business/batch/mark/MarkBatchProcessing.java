/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.mark;

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
public class MarkBatchProcessing implements IMarkBatchProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public MarkBatchProcessing() throws NamingException {
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
        dbg("inside mark batch processing");
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
             
               sequence=maxSequence+1;
               dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 95,instituteID,batchName, l_businessDate,startTime," ","W"," "," "," ",Integer.toString(sequence));   
            } 
            
            
        }else{
            
            sequence=1;
            dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 95,instituteID,batchName, l_businessDate,startTime," ","W"," "," "," ",Integer.toString(sequence));   
            
        }
        
            tc.commit(session, dbSession);
            
         classIdentification(instituteID,l_businessDate);
           
           dbg("no_of_threads"+no_of_threads);
           

           
            if(no_of_threads==0){

                sequentialProcessing(instituteID,l_businessDate,batchName);
            }else{

                parallelProcessing(instituteID,l_businessDate,batchName,no_of_threads);
            }
         
         tc.commit(session, dbSession);
         
         
         
//        }    
        dbg("end of mark batch processing");
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
          
           dbg("inside mark batch--->sequentialProcessing");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IClassMarkProcessing markProcessing=inject.getClassMarkProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           batchUtil=inject.getBatchUtil(session);
           tc=inject.getTransactionControlService();
           BusinessService bs=inject.getBusinessService(session);
           
           dbg("before reading mark table");
           Map<String,DBRecord>markEodMap=null;
           
           try{
           
              markEodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "MARK_BATCH_STATUS", session, dbSession);
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
           
           
//           List<DBRecord>filteredMarkList=markEodMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
           dbg("after reading mark table");
//           dbg("filteredMarkList size "+filteredMarkList.size());
           Iterator<DBRecord>markIterator=markEodMap.values().iterator();
           List<DBRecord>markList=new ArrayList();
           
           while(markIterator.hasNext()){
//           for(int i=0;i<filteredMarkList.size();i++){
               
               DBRecord markRecord=markIterator.next();
               String l_standard=markRecord.getRecord().get(1).trim();
               String l_section=markRecord.getRecord().get(2).trim();
               String l_exam=markRecord.getRecord().get(3).trim();
               String l_status=markRecord.getRecord().get(5).trim();
               int lfailCount=0;
               try{
               
                 lfailCount=Integer.parseInt(markRecord.getRecord().get(7).trim());
               
               }catch(NumberFormatException ex){
                   
                   lfailCount=0;
               }
               
               dbg("lfailCount"+lfailCount);
               dbg("l_status"+l_status);
               if(l_status.equals("F")||lfailCount>0){
                   
                    int markHistoryMax=batchUtil.getMarkHistoryMaxSequence(l_businessDate, instituteID, l_standard, l_section, l_exam, inject, session, dbSession);
                    
                    if(markHistoryMax<=10){
                        
                        markList.add(markRecord);
                    }
                   
               }
               else if(l_status.equals("W")){
                   
                   markList.add(markRecord);
               }
               
           
           }
           
           for(int i=0;i<markList.size();i++){
               DBRecord markRecord=markList.get(i);
               String l_standard=markRecord.getRecord().get(1).trim();
               String l_section=markRecord.getRecord().get(2).trim();
               String l_exam=markRecord.getRecord().get(3).trim();
               String l_status=markRecord.getRecord().get(5).trim();
//               String l_sequenceNo=markRecord.getRecord().get(9).trim();
               
               dbg("inside marklist iteration");
               dbg("l_markID"+l_standard);
               dbg("l_groupID"+l_section);
               dbg("l_status"+l_exam);
               
                if(l_status.equals("F")){
            
//                    int markHistoryMax=batchUtil.getMarkHistoryMaxSequence(l_businessDate, instituteID, l_markID, inject, session, dbSession);
//                    
//                    if(markHistoryMax>10){
//                        continue;
//                    }
                    
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update.put("6", "W");
                    String[] l_pkey={instituteID,l_standard,l_section,l_exam,l_businessDate};
                    dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "MARK_BATCH_STATUS", l_pkey, column_to_Update,session); 
                       batchUtil.moveClassMarkRecordToHistory(instituteID,l_standard,l_section,l_exam,l_businessDate,markRecord, session, dbSession, inject);
                    tc.commit(session, dbSession);
                }
                
                markProcessing.processing(instituteID, l_standard, l_section, l_exam, l_businessDate, session);
           }
          dbg("end of mark batch--->sequentialProcessing");  
         batchUtil.batchSucessHandler(instituteID, l_businessDate, batchName, inject, session, dbSession);
        }catch(DBValidationException ex){
          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }catch(BSValidationException ex){
          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }
         
     }
     
     
     
     private void parallelProcessing(String instituteID,String l_businessDate,String batchName,int no_of_threads)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      BatchUtil batchUtil=null;   
      ITransactionControlService tc=null;
      try{
           dbg("inside mark batch parallel Processing");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IClassMarkProcessing markProcessing=inject.getClassMarkProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           tc=inject.getTransactionControlService();
           batchUtil=inject.getBatchUtil(session);
           BusinessService bs=inject.getBusinessService(session);
           
           dbg("before reading mark table");
           Map<String,DBRecord>markEodMap=null;
           try{
           
           
                markEodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "MARK_BATCH_STATUS", session, dbSession);
          
           
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
           
           
//           List<DBRecord>filteredMarkList=markEodMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
           dbg("after reading mark table");
//           dbg("filteredMarkList size "+filteredMarkList.size());
           Iterator<DBRecord>markIterator=markEodMap.values().iterator();
           List<DBRecord>markList=new ArrayList();
           
           while(markIterator.hasNext()){
//           for(int i=0;i<filteredMarkList.size();i++){
               
               DBRecord markRecord=markIterator.next();
               String l_standard=markRecord.getRecord().get(1).trim();
               String l_section=markRecord.getRecord().get(2).trim();
               String l_exam=markRecord.getRecord().get(3).trim();
               String l_status=markRecord.getRecord().get(5).trim();
               int lfailCount=0;
               try{
               
                 lfailCount=Integer.parseInt(markRecord.getRecord().get(7).trim());
               
               }catch(NumberFormatException ex){
                   
                   lfailCount=0;
               }
               
               dbg("lfailCount"+lfailCount);
               dbg("l_status"+l_status);
               if(l_status.equals("F")||lfailCount>0){
                   
                    int markHistoryMax=batchUtil.getMarkHistoryMaxSequence(l_businessDate, instituteID, l_standard, l_section, l_exam, inject, session, dbSession);
                    
                    if(markHistoryMax<=10){
                        
                        markList.add(markRecord);
                    }
                   
               }
               else if(l_status.equals("W")){
                   
                   markList.add(markRecord);
               }
               
           
           }
           
//           Future<String>[] Result=new Future[no_of_threads];
            Future<String>[] Result;
           Map<Integer,WorkDispatch>dispatchedJobs=new HashMap();
           boolean dispatchFailed=false;
           int no_of_execution;
           
           if(markList.size()%no_of_threads==0){
               
               no_of_execution=markList.size()/no_of_threads;
               
           }else{
               
               no_of_execution=(markList.size()/no_of_threads)+1;
           }
           
           dbg("no_of_execution"+no_of_execution);
           
           int executionCount=0;
           
           while(executionCount<no_of_execution){
           
           dbg("inside while(executionCount<no_of_execution)");
           int startIndex=executionCount*no_of_threads;
           int endIndex=startIndex+no_of_threads-1;
           dbg("startIndex"+startIndex);
           dbg("endIndex"+endIndex);
           Result=new  Future[no_of_threads];
           
           //job submission starts
           dbg("job submission starts");
           for(int i=startIndex;i<=endIndex;i++){
               int j=0;
               if(i<markList.size()){
                   if(startIndex==i){
                       j=0;
                   }else{
                       j++;
                   }
               DBRecord markRecord=markList.get(i);
               String l_standard=markRecord.getRecord().get(1).trim();
               String l_section=markRecord.getRecord().get(2).trim();
               String l_exam=markRecord.getRecord().get(3).trim();
               String l_status=markRecord.getRecord().get(5).trim();
//               String l_sequenceNo=markRecord.getRecord().get(9).trim();
               dbg("inside marklist iteration");
               dbg("l_status"+l_status); 
               
               
                
                if(l_status.equals("F")){
            
                   
                    
                    
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update.put("6", "W");
                    String[] l_pkey={instituteID,l_standard,l_section,l_exam,l_businessDate};
//                    dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "MARK_BATCH_STATUS", l_pkey, column_to_Update,session); 
                    dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "MARK_BATCH_STATUS", l_pkey, column_to_Update,session); 
                     batchUtil.moveClassMarkRecordToHistory(instituteID,l_standard,l_section,l_exam,l_businessDate,markRecord, session, dbSession, inject);
                    tc.commit(session, dbSession);
                }
                
                
                
                try{
                    
                  Result[j]= (Future<String>)markProcessing.parallelProcessing(instituteID, l_standard,l_section,l_exam, l_businessDate);
               
                } catch (EJBException ex) {
                   dispatchFailed=true;
                }
                
                
                if( !dispatchFailed){
                    
                     WorkDispatch dispatch=new WorkDispatch(l_standard+l_section+l_exam,"WIP");
                     dispatchedJobs.put(j, dispatch);
                }
                
                dbg("dispatchFailed"+dispatchFailed);
             dispatchFailed=false;   
           
           }
           } 
           dbg("job submission starts");
           //job submission ends
           
             //job status monitoring starts   
             dbg("job status monitoring starts");
              int threadCount=0;
               boolean comeOutLoop=false;
               dbg("dispatchedJobs.size()"+dispatchedJobs.size());
               if(dispatchedJobs.size()>0){
               
               while(comeOutLoop==false){
                    
                 dbg("inside while comeOutLoop==false");  
                 Iterator<Integer> keyIterator=  dispatchedJobs.keySet().iterator();
                 WorkDispatch dispatch=null;
                   
                 
                 while(keyIterator.hasNext()){
                     
                     Integer key= keyIterator.next();
                     dbg("key"+key);
                    
                     dbg("dispatchedJobs.get(key).getResult()"+dispatchedJobs.get(key).getResult());
                     if(dispatchedJobs.get(key).getResult().equals("WIP")){
                     
                     dbg("Result[key].isDone()");    
                     if(Result[key].isDone()){
                         
                            try {
                                  dispatch=  dispatchedJobs.get(key);
                                  String result=Result[key].get();
                                  dispatch.setResult(result);
                                  
                                } catch (ExecutionException ex) {
                                    dbg(ex);
                                  dispatch.setResult("Fail");
                                  
                                  throw ex;
                                  
                                }
                         threadCount++;
                     }
                     }
                 }
                 dbg("threadCount"+threadCount);
                 dbg("no_of_threads"+no_of_threads);
//                 if(threadCount==no_of_threads)
                 if(threadCount==dispatchedJobs.size())
                  comeOutLoop=true;
                 
               }
               //result null
               Result=null;
               dispatchedJobs.clear();
               } 
           executionCount++;
               }
           
         batchUtil.batchSucessHandler(instituteID, l_businessDate, batchName, inject, session, dbSession);
         dbg("end of mark batch--->parallelProcessing");  
        }catch(DBValidationException ex){
          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }catch(BSValidationException ex){
          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }
         
     }
     
     
     
     
     
     
   
  private void classIdentification(String instituteID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       ITransactionControlService tc=null;
       try{
           dbg("inside exam batch feeIdentification");
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IDBTransactionService dbts=inject.getDBTransactionService();
           BusinessService bs=inject.getBusinessService(session);
           tc=inject.getTransactionControlService();
           ArrayList<String>classList=null;
           
           try{
           
              classList=bs.getClassesOfTheInstitute(instituteID, session, dbSession, inject);
           
         }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                 
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_000");  
                          return;
                          
                       }else{
                           throw ex;
                       }
              }
           
           ArrayList<String>examList=new ArrayList();
           
           dbg("classList size"+classList.size());           
           
           for(int i=0;i<classList.size();i++){
               
               String l_class=classList.get(i);
               String l_standard=l_class.split("~")[0];
               String l_section=l_class.split("~")[1];
               Map<String,DBRecord>l_markMap=null;
 
               try{
               
                     l_markMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamMarkMaster","CLASS","CLASS_MARK_ENTRY", session, dbSession);
               
               }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                 
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_000");  
//                          return;
                          
                       }else{
                           throw ex;
                       }
              }
               
               
               if(l_markMap!=null){
                   
                   
                   
               
               Iterator<DBRecord>valueIterator=l_markMap.values().iterator();
               
               while(valueIterator.hasNext()){
                   DBRecord dbRec=valueIterator.next();
                   String exam=dbRec.getRecord().get(2).trim();
                   String checkerDateStamp=dbRec.getRecord().get(7).trim();
                   String recordStatus=dbRec.getRecord().get(8).trim();
                   String authStatus=dbRec.getRecord().get(9).trim();
                   
                   
                    
                   

                    if(recordStatus.equals("O")&&authStatus.equals("A")){
                        
                        
                      if(checkerDateStamp!=null&&!checkerDateStamp.isEmpty()&&!checkerDateStamp.equals(" ")){
                   
                        String checkerDate=bs.getDateFromDateStamp(checkerDateStamp);  
                        
                        
                       if(checkerDate.equals(l_businessDate)) {
                        

                          if(checkMarkEntry(instituteID,l_standard,l_section,exam))  {
                              
                              if(!examList.contains(instituteID+"~"+l_standard+"~"+l_section+"~"+exam)){
                              
                                  examList.add(instituteID+"~"+l_standard+"~"+l_section+"~"+exam);
                              
                              }
                              
                          } 


//                           try{
//
//                             dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 142,instituteID,l_standard,l_section,exam,l_businessDate,"W"," "," "," "," "); 
//                             tc.commit(session, dbSession);
//                           }catch(DBValidationException ex){
//
//                               if(!ex.toString().contains("DB_VAL_009")){
//                                   throw ex;
//                               }
//                           }
                       
                       }
                      }
               }
               }
               
               }
               
           }
           
           
           dbg("examList-->"+examList.size());
           
           for(int i=0;i<examList.size();i++){
               
               String examListValue=examList.get(i);
               
               
               String standard=examListValue.split("~")[1];
               String section=examListValue.split("~")[2];
               String exam=examListValue.split("~")[3];
               
               try{
//
                             dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 142,instituteID,standard,section,exam,l_businessDate,"W"," "," "," "," "); 
                             tc.commit(session, dbSession);
               }catch(DBValidationException ex){
                   tc.rollBack(session, dbSession);
                   if(!ex.toString().contains("DB_VAL_009")){
                       throw ex;
                   }else{
                       session.getErrorhandler().removeSessionErrCode("DB_VAL_009");
                   }
               }
               
           }
           
           
           
           dbg("end of class identification");
        }catch(DBValidationException ex){
          throw ex;
        }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
       
       
   }
     
     
     
     
     
     
     private boolean checkMarkEntry(String instituteID,String l_standard,String l_section,String exam)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
         boolean status=false;
         try{
             
             IDBReadBufferService readBuffer=inject.getDBReadBufferService();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             BusinessService bs=inject.getBusinessService(session);
             
             try{
             
                 String[] l_pkey={l_standard,l_section,exam};
                 DBRecord l_examScheduleRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS","CLASS_EXAM_SCHEDULE_MASTER", l_pkey, session, dbSession);
                 String masterVersion=l_examScheduleRecord.getRecord().get(9).trim();
                 
                 Map<String,DBRecord>l_examScheduleMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS","CLASS_EXAM_SCHEDULE_DETAIL", session, dbSession);

                 int versionIndex=bs.getVersionIndexOfTheTable("CLASS_EXAM_SCHEDULE_DETAIL", "CLASS", session, dbSession, inject);
                 
                 
                 Map<String,List<DBRecord>>examSubjectWiseList= l_examScheduleMap.values().stream().filter(rec->rec.getRecord().get(2).trim().equals(exam)&&rec.getRecord().get(versionIndex).equals(masterVersion)).collect(Collectors.groupingBy(rec->rec.getRecord().get(3).trim()));

                 Map<String,DBRecord>l_markMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamMarkMaster","CLASS","CLASS_MARK_ENTRY", session, dbSession);

                 Map<String,List<DBRecord>>markSubjectWiseList= l_markMap.values().stream().filter(rec->rec.getRecord().get(2).trim().equals(exam)&&rec.getRecord().get(8).trim().equals("O")&&rec.getRecord().get(9).trim().equals("A")).collect(Collectors.groupingBy(rec->rec.getRecord().get(3).trim()));
           
             
                 if(examSubjectWiseList.keySet().size()==markSubjectWiseList.keySet().size()){


                     status=true;
                 }
                 
              }catch(DBValidationException ex){
                       
                       if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                 
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                          session.getErrorhandler().removeSessionErrCode("DB_VAL_000");  
                          status=false;
                          
                       }else{
                           throw ex;
                       }
              }
             
             
         return status;
        }catch(DBValidationException ex){
          throw ex;
        }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
         
     }
     
     
     
     
     
     
     
     
     
  /* public void processing (String instituteID,String batchName,int no_of_threads)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
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
        dbg("inside mark batch processing");
        dbg("instituteID"+instituteID);
        dbg("batchName"+batchName);
        dbg("no_of_threads"+no_of_threads);
        String[] datePkry={instituteID};
        DBRecord currentDateRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE", "INSTITUTE_CURRENT_DATE",datePkry ,session, dbSession);
        l_businessDate=currentDateRecord.getRecord().get(1).trim();
        dbg("l_businessDate"+l_businessDate);
        boolean recordExistence=true;
        DBRecord batchEODStatusRecord=null;
        String l_eodStatus=new String();  
        String startTime=bs.getCurrentDateTime();
        dbg("startTime"+startTime);
           
           try{

           String l_eodPkey[]={instituteID,batchName,l_businessDate};
           batchEODStatusRecord=readBuffer.readRecord("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS",l_eodPkey ,session, dbSession,true);
           l_eodStatus=batchEODStatusRecord.getRecord().get(5).trim();
        }catch(DBValidationException ex){

            if(ex.toString().contains("DB_VAL_011")){

                recordExistence=false;
            }
        }
           
        dbg("l_eodStatus"+l_eodStatus);   
           
        if(recordExistence==false||l_eodStatus.equals("F")){
         
            if(l_eodStatus.equals("F")){
            
                Map<String,String>column_to_Update=new HashMap();
                column_to_Update.put("6", "W");
                column_to_Update.put("4", startTime);
                String[] l_pkey={instituteID,batchName,l_businessDate};
                dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_STATUS", l_pkey, column_to_Update,session); 
                batchUtil.moveBatchRecordToHistory(instituteID,batchName,l_businessDate,batchEODStatusRecord, session, dbSession, inject);
            }else{ 
             
               dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 95,instituteID,batchName, l_businessDate,startTime," ","W"," "," "," ");   
            }   
            tc.commit(session, dbSession);
            
           markIdentification(instituteID,l_businessDate);
           
           dbg("no_of_threads"+no_of_threads);
            if(no_of_threads==0){

                sequentialProcessing(instituteID,l_businessDate,batchName);
            }else{

                parallelProcessing(instituteID,l_businessDate,batchName,no_of_threads);
            }
           
        }    
        dbg("end of other activity batch processing");
        }catch(Exception ex){
           dbg(ex);
           batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
       
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
          
           dbg("inside mark batch--->sequentialProcessing");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IMarkProcessing markProcessing=inject.getMarkProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           batchUtil=inject.getBatchUtil(session);
           tc=inject.getTransactionControlService();
           
           dbg("before reading mark table");
           Map<String,DBRecord>markEodMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "MARK_EOD_STATUS", session, dbSession);
           List<DBRecord>markList=markEodMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
           dbg("after reading mark table");
           dbg("markList size "+markList.size());
           
           for(int i=0;i<markList.size();i++){
               DBRecord markRecord=markList.get(i);
               String l_markID=markRecord.getRecord().get(1).trim();
               String l_groupID=markRecord.getRecord().get(7).trim();
               String l_status=markRecord.getRecord().get(3).trim();
               
               dbg("inside marklist iteration");
               dbg("l_markID"+l_markID);
               dbg("l_groupID"+l_groupID);
               dbg("l_status"+l_status);
               
                if(l_status.equals("F")){
            
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update.put("4", "W");
                    String[] l_pkey={instituteID,l_markID,l_businessDate};
                    dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "MARK_EOD_STATUS", l_pkey, column_to_Update,session); 
                    batchUtil.moveMarkRecordToHistory(instituteID,l_markID,l_businessDate,markRecord, session, dbSession, inject);
                    tc.commit(session, dbSession);
                }
                
                    dbg("Before calling mark processing for "+l_markID);
                markProcessing.processing(instituteID,l_markID,l_groupID,l_businessDate,session);
           }
          dbg("end of mark batch--->sequentialProcessing");  
         batchUtil.batchSucessHandler(instituteID, l_businessDate, batchName, inject, session, dbSession);
        }catch(DBValidationException ex){
          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }catch(BSValidationException ex){
          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }
         
     }
     
     
     
     private void parallelProcessing(String instituteID,String l_businessDate,String batchName,int no_of_threads)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      BatchUtil batchUtil=null;   
      ITransactionControlService tc=null;
      try{
           dbg("inside mark batch parallel Processing");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
            IMarkProcessing markProcessing=inject.getMarkProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           tc=inject.getTransactionControlService();
           batchUtil=inject.getBatchUtil(session);
           
           dbg("before reading mark table");
           Map<String,DBRecord>markEodMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "MARK_EOD_STATUS", session, dbSession);
           List<DBRecord>markList=markEodMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals("F")||rec.getRecord().get(3).trim().equals("W")).collect(Collectors.toList());
           dbg("after reading mark table");
           dbg("markList size "+markList.size());
//           Future<String>[] Result=new Future[no_of_threads];
            Future<String>[] Result;
           Map<Integer,WorkDispatch>dispatchedJobs=new HashMap();
           boolean dispatchFailed=false;
           int no_of_execution;
           
           if(markList.size()%no_of_threads==0){
               
               no_of_execution=markList.size()/no_of_threads;
               
           }else{
               
               no_of_execution=(markList.size()/no_of_threads)+1;
           }
           
           dbg("no_of_execution"+no_of_execution);
           
           int executionCount=0;
           
           while(executionCount<no_of_execution){
           
           dbg("inside while(executionCount<no_of_execution)");
           int startIndex=executionCount*no_of_threads;
           int endIndex=startIndex+no_of_threads-1;
           dbg("startIndex"+startIndex);
           dbg("endIndex"+endIndex);
           Result=new  Future[no_of_threads];
           
           //job submission starts
           dbg("job submission starts");
           for(int i=startIndex;i<=endIndex;i++){
               int j=0;
               if(i<markList.size()){
                   if(startIndex==i){
                       j=0;
                   }else{
                       j++;
                   }
               DBRecord markRecord=markList.get(i);
               String l_markID=markRecord.getRecord().get(1).trim();
               String l_groupID=markRecord.getRecord().get(7).trim();
               String l_status=markRecord.getRecord().get(3).trim();
               dbg("inside marklist iteration");
               dbg("l_markID"+l_markID);
               dbg("l_groupID"+l_groupID);
               dbg("l_status"+l_status); 
               
               
                
                if(l_status.equals("F")){
            
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update.put("4", "W");
                    String[] l_pkey={instituteID,l_markID,l_businessDate};
                    dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "MARK_EOD_STATUS", l_pkey, column_to_Update,session); 
                    batchUtil.moveMarkRecordToHistory(instituteID,l_markID,l_businessDate,markRecord, session, dbSession, inject);
                    tc.commit(session, dbSession);
                }
                
                
                
                try{
                    
                  Result[j]= (Future<String>)markProcessing.parallelProcessing(instituteID, l_markID, l_groupID, l_businessDate);
               
                } catch (EJBException ex) {
                   dispatchFailed=true;
                }
                
                
                if( !dispatchFailed){
                    
                     WorkDispatch dispatch=new WorkDispatch(l_markID,"WIP");
                     dispatchedJobs.put(j, dispatch);
                }
                
                dbg("dispatchFailed"+dispatchFailed);
             dispatchFailed=false;   
           
           }
           } 
           dbg("job submission starts");
           //job submission ends
           
             //job status monitoring starts   
             dbg("job status monitoring starts");
              int threadCount=0;
               boolean comeOutLoop=false;
               dbg("dispatchedJobs.size()"+dispatchedJobs.size());
               if(dispatchedJobs.size()>0){
               
               while(comeOutLoop==false){
                    
                 dbg("inside while comeOutLoop==false");  
                 Iterator<Integer> keyIterator=  dispatchedJobs.keySet().iterator();
                 WorkDispatch dispatch=null;
                   
                 
                 while(keyIterator.hasNext()){
                     
                     Integer key= keyIterator.next();
                     dbg("key"+key);
                    
                     dbg("dispatchedJobs.get(key).getResult()"+dispatchedJobs.get(key).getResult());
                     if(dispatchedJobs.get(key).getResult().equals("WIP")){
                     
                     dbg("Result[key].isDone()");    
                     if(Result[key].isDone()){
                         
                            try {
                                  dispatch=  dispatchedJobs.get(key);
                                  String result=Result[key].get();
                                  dispatch.setResult(result);
                                  
                                } catch (ExecutionException ex) {
                                    dbg(ex);
                                  dispatch.setResult("Fail");
                                  
                                  throw ex;
                                  
                                }
                         threadCount++;
                     }
                     }
                 }
                 dbg("threadCount"+threadCount);
                 dbg("no_of_threads"+no_of_threads);
//                 if(threadCount==no_of_threads)
                 if(threadCount==dispatchedJobs.size())
                  comeOutLoop=true;
                 
               }
               //result null
               Result=null;
               dispatchedJobs.clear();
               } 
           executionCount++;
               }
           
         batchUtil.batchSucessHandler(instituteID, l_businessDate, batchName, inject, session, dbSession);
         dbg("end of mark batch--->parallelProcessing");  
        }catch(DBValidationException ex){
          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }catch(BSValidationException ex){
          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.batchErrorHandler(instituteID, l_businessDate, ex, batchName, inject, session, dbSession);
        }
         
     }
     
     
     
     
     
     
   
   private void markIdentification(String instituteID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       ITransactionControlService tc=null;
       try{
           dbg("inside mark batch markIdentification");
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IDBTransactionService dbts=inject.getDBTransactionService();
           BusinessService bs=inject.getBusinessService(session);
           tc=inject.getTransactionControlService();
           Map<String,DBRecord>l_markMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE","IVW_MARK", session, dbSession);
           dbg("l_markMap size"+l_markMap.size());
           List<DBRecord>l_markRecords=l_markMap.values().stream().filter(rec->rec.getRecord().get(13).trim().equals("O")&&rec.getRecord().get(14).trim().equals("A")).collect(Collectors.toList());
           dbg("l_markRecords size"+l_markRecords.size());           
           
           for(int i=0;i<l_markRecords.size();i++){
               
               String markID=l_markRecords.get(i).getRecord().get(2).trim();
               String checkerDateStamp=l_markRecords.get(i).getRecord().get(12).trim();
               String checkerDate=bs.getDateFromDateStamp(checkerDateStamp);
               String groupID=l_markRecords.get(i).getRecord().get(1).trim();
               
               dbg("inside mark records iteration");
               dbg("markID"+markID);
               dbg("checkerDateStamp"+checkerDateStamp);
               dbg("checkerDate"+checkerDate);
               dbg("groupID"+groupID);
               
               if(l_businessDate.equals(checkerDate)){
               
               try{
                   
                 dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 111,instituteID,markID,l_businessDate,"W"," "," "," ",groupID," "," "); 
                 tc.commit(session, dbSession);
               }catch(DBValidationException ex){
                   
                   if(!ex.toString().contains("DB_VAL_009")){
                       throw ex;
                   }
               }
           }
           }
           
           
           
           dbg("end of mark identification");
        }catch(DBValidationException ex){
          throw ex;
        }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
       
       
   }*/
   
  
   
   
  
   
   
   
     public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
}
