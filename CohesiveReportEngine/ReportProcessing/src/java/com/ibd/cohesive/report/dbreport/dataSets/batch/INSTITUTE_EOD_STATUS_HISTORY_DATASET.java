/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.batch;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.INSTITUTE_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author DELL
 */
public class INSTITUTE_EOD_STATUS_HISTORY_DATASET {
//    ArrayList<INSTITUTE_EOD_STATUS_HISTORY> dataset;
    
    
    public ArrayList<INSTITUTE_EOD_STATUS_HISTORY> getTableObject(String p_businessDate,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside INSTITUTE_EOD_STATUS_HISTORY_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
Map<String,DBRecord>l_tableMap=null;
        


        try{

          l_tableMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+p_businessDate, "BATCH", "INSTITUTE_EOD_STATUS_HISTORY", session, dbSession,ismaxVersionRequired);
          
          }catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                ArrayList<INSTITUTE_EOD_STATUS_HISTORY>dataset=new ArrayList();
                INSTITUTE_EOD_STATUS_HISTORY appEod=new INSTITUTE_EOD_STATUS_HISTORY();
                appEod.setINSTITUTE_ID(" ");
                appEod.setBUSINESS_DATE(" ");
                appEod.setEOD_STATUS(" ");
                appEod.setSTART_TIME(" ");
                appEod.setEND_TIME(" ");
                appEod.setNO_OF_SUCCESS(" ");
                appEod.setNO_OF_FAILURES(" ");
                appEod.setERROR(" ");
                appEod.setSEQUENCE_NO(" ");
                
                dataset.add(appEod);
                
                return dataset;
            }else{
                
                throw ex;
            }
            
            
        }              
        
         dbg("end of INSTITUTE_EOD_STATUS_HISTORY_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_tableMap,session) ;

    
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
    
    
    
    
    private ArrayList<INSTITUTE_EOD_STATUS_HISTORY> convertDBtoReportObject(Map<String,DBRecord>l_tableMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<INSTITUTE_EOD_STATUS_HISTORY>dataset=new ArrayList();
        try{
            
            
            dbg("inside INSTITUTE_EOD_STATUS_HISTORY_DATASET convertDBtoReportObject",session);
            
            if(l_tableMap!=null&&!l_tableMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=l_tableMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_eodRecords=recordIterator.next();
                    INSTITUTE_EOD_STATUS_HISTORY eod=new INSTITUTE_EOD_STATUS_HISTORY();
                    
                    eod.setINSTITUTE_ID(l_eodRecords.getRecord().get(0).trim());
                    eod.setBUSINESS_DATE(l_eodRecords.getRecord().get(1).trim());
                    eod.setEOD_STATUS(l_eodRecords.getRecord().get(2).trim());
                    eod.setSTART_TIME(l_eodRecords.getRecord().get(3).trim());
                    eod.setEND_TIME(l_eodRecords.getRecord().get(4).trim());
                    eod.setNO_OF_SUCCESS(l_eodRecords.getRecord().get(5).trim());
                    eod.setNO_OF_FAILURES(l_eodRecords.getRecord().get(6).trim());
                    eod.setERROR(l_eodRecords.getRecord().get(7).trim());
                    eod.setSEQUENCE_NO(l_eodRecords.getRecord().get(8).trim());
                    
                    
                    

                    dataset.add(eod);
                    
                }
            
            }
            dbg("end of INSTITUTE_EOD_STATUS_HISTORY_DATASET convertDBtoReportObject",session);
            
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
