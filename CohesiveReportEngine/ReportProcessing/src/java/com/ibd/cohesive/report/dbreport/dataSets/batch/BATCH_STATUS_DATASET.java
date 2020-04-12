/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.batch;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.BATCH_STATUS;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author DELL
 */
public class BATCH_STATUS_DATASET {
//    ArrayList<BATCH_STATUS> dataset;
    
    
    public ArrayList<BATCH_STATUS> getTableObject(String p_businessDate,String p_instituteID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,String batchName)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside BATCH_STATUS_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
        
Map<String,DBRecord>l_tableMap=null;


        try{
        
          l_tableMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+p_businessDate, "BATCH", "BATCH_STATUS", session, dbSession,ismaxVersionRequired);
       
        }catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                ArrayList<BATCH_STATUS>dataset=new ArrayList();
                BATCH_STATUS appEod=new BATCH_STATUS();
                appEod.setINSTITUTE_ID(" ");
                appEod.setBATCH_NAME(" ");
                appEod.setBUSINESS_DATE(" ");
                appEod.setSTART_TIME(" ");
                appEod.setEND_TIME(" ");
                appEod.setEOD_STATUS(" ");
                appEod.setNO_OF_SUCCESS(" ");
                appEod.setNO_FAILURES(" ");
                appEod.setERROR(" ");
                appEod.setSEQUENCE_NO(" ");
                
                dataset.add(appEod);
                
                return dataset;
            }else{
                
                throw ex;
            }
            
            
        }
                   
        
         dbg("end of BATCH_STATUS_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_tableMap,session,batchName) ;

    
    }catch(DBProcessingException ex){
          dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex,session);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
        
    }
    
    
    
    
    private ArrayList<BATCH_STATUS> convertDBtoReportObject(Map<String,DBRecord>l_tableMap,CohesiveSession session,String batchName)throws DBProcessingException{
    
        ArrayList<BATCH_STATUS>dataset=new ArrayList();
        try{
            
            
            dbg("inside BATCH_STATUS_DATASET convertDBtoReportObject",session);
            
            if(l_tableMap!=null&&!l_tableMap.isEmpty()){
                
                List<DBRecord> filteredRecords;
                if(batchName!=null){
                
                  filteredRecords=l_tableMap.values().stream().filter(rec->rec.getRecord().get(1).trim().equals(batchName)).collect(Collectors.toList());
             
                }else{
                    
                    filteredRecords=l_tableMap.values().stream().collect(Collectors.toList());
                }
                
                
                Iterator<DBRecord> recordIterator=filteredRecords.iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_eodRecords=recordIterator.next();
                    BATCH_STATUS eod=new BATCH_STATUS();
                    
                    eod.setINSTITUTE_ID(l_eodRecords.getRecord().get(0).trim());
                    eod.setBATCH_NAME(l_eodRecords.getRecord().get(1).trim());
                    eod.setBUSINESS_DATE(l_eodRecords.getRecord().get(2).trim());
                    eod.setSTART_TIME(l_eodRecords.getRecord().get(3).trim());
                    eod.setEND_TIME(l_eodRecords.getRecord().get(4).trim());
                    eod.setEOD_STATUS(l_eodRecords.getRecord().get(5).trim());
                    eod.setNO_OF_SUCCESS(l_eodRecords.getRecord().get(6).trim());
                    eod.setNO_FAILURES(l_eodRecords.getRecord().get(7).trim());
                    eod.setERROR(l_eodRecords.getRecord().get(8).trim());
                    eod.setSEQUENCE_NO(l_eodRecords.getRecord().get(9).trim());
                    
                    
                    
                    

                    dataset.add(eod);
                    
                }
            
            }
            dbg("end of BATCH_STATUS_DATASET convertDBtoReportObject",session);
            
        }catch(Exception ex){
            dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
       }
        
        return dataset;
        
    }
    
     public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}
