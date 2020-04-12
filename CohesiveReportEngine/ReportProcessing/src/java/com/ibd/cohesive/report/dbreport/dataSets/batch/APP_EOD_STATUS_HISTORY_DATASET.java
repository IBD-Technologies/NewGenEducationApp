/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.batch;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.APP_EOD_STATUS_HISTORY;
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
public class APP_EOD_STATUS_HISTORY_DATASET {
//    ArrayList<APP_EOD_STATUS_HISTORY> dataset;
    
    
    public ArrayList<APP_EOD_STATUS_HISTORY> getTableObject(String p_businessDate,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside APP_EOD_STATUS_HISTORY_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
        
 Map<String,DBRecord>l_appStatusMap=new HashMap();
        
 
 
       try{
 
        l_appStatusMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+p_businessDate, "BATCH", "APP_EOD_STATUS_HISTORY", session, dbSession,ismaxVersionRequired);
          
                
        
        }catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                ArrayList<APP_EOD_STATUS_HISTORY>dataset=new ArrayList();
                APP_EOD_STATUS_HISTORY appEod=new APP_EOD_STATUS_HISTORY();
                appEod.setBUSINESS_DATE(" ");
                appEod.setSTART_TIME(" ");
                appEod.setEND_TIME(" ");
                appEod.setEOD_STATUS(" ");
                appEod.setERROR(" ");
                appEod.setNO_OF_SUCCESS(" ");
                appEod.setNO_OF_FAILURES(" ");
                appEod.setSEQUENCE_NO(" ");
                dataset.add(appEod);
                
                return dataset;
            }else{
                
                throw ex;
            }
            
            
        }
        
        
        
         dbg("end of APP_EOD_STATUS_HISTORY_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_appStatusMap,session) ;

    
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
    
    
    
    
    private ArrayList<APP_EOD_STATUS_HISTORY> convertDBtoReportObject(Map<String,DBRecord>p_appEodMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<APP_EOD_STATUS_HISTORY>dataset=new ArrayList();
        try{
            
            
            dbg("inside APP_EOD_STATUS_HISTORY_DATASET convertDBtoReportObject",session);
            
            if(p_appEodMap!=null&&!p_appEodMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_appEodMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_appEodRecords=recordIterator.next();
                    APP_EOD_STATUS_HISTORY appEod=new APP_EOD_STATUS_HISTORY();
                 
                    appEod.setBUSINESS_DATE(l_appEodRecords.getRecord().get(0).trim());
                    appEod.setSTART_TIME(l_appEodRecords.getRecord().get(1).trim());
                    appEod.setEND_TIME(l_appEodRecords.getRecord().get(2).trim());
                    appEod.setEOD_STATUS(l_appEodRecords.getRecord().get(3).trim());
                    appEod.setERROR(l_appEodRecords.getRecord().get(4).trim());
                    appEod.setNO_OF_SUCCESS(l_appEodRecords.getRecord().get(5).trim());
                    appEod.setNO_OF_FAILURES(l_appEodRecords.getRecord().get(6).trim());
                    appEod.setSEQUENCE_NO(l_appEodRecords.getRecord().get(7).trim());

                    dataset.add(appEod);
                    
                }
            
            }
            dbg("end of APP_EOD_STATUS_HISTORY_DATASET convertDBtoReportObject",session);
            
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
