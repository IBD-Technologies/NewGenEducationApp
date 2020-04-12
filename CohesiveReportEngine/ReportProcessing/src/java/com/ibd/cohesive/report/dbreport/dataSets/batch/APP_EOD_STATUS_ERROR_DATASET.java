/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.batch;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.APP_EOD_STATUS_ERROR;
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
public class APP_EOD_STATUS_ERROR_DATASET {
//    ArrayList<APP_EOD_STATUS_ERROR> dataset;
    
    
    public ArrayList<APP_EOD_STATUS_ERROR> getTableObject(String p_businessDate,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside APP_EOD_STATUS_ERROR_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        boolean ismaxVersionRequired=false;
        Map<String,DBRecord>l_appStatusMap=new HashMap();
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
        try{
        
           l_appStatusMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+p_businessDate, "BATCH", "APP_EOD_STATUS_ERROR", session, dbSession,ismaxVersionRequired);
          
        }catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                ArrayList<APP_EOD_STATUS_ERROR>dataset=new ArrayList();
                APP_EOD_STATUS_ERROR appEod=new APP_EOD_STATUS_ERROR();
                 
                appEod.setBUSINESS_DATE(" ");
                appEod.setERROR(" ");

                dataset.add(appEod);
                
                return dataset;
            }else{
                
                throw ex;
            }
            
            
        }
        
         dbg("end of APP_EOD_STATUS_ERROR_DATASET--->getTableObject",session);  
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
    
    
    
    
    private ArrayList<APP_EOD_STATUS_ERROR> convertDBtoReportObject(Map<String,DBRecord>p_appEodMap,CohesiveSession session)throws DBProcessingException{
    
       ArrayList<APP_EOD_STATUS_ERROR> dataset=new ArrayList();
        try{
            
            
            dbg("inside APP_EOD_STATUS_ERROR_DATASET convertDBtoReportObject",session);
            
            if(p_appEodMap!=null&&!p_appEodMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_appEodMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_appEodRecords=recordIterator.next();
                    APP_EOD_STATUS_ERROR appEod=new APP_EOD_STATUS_ERROR();
                 
                    appEod.setBUSINESS_DATE(l_appEodRecords.getRecord().get(0).trim());
                    appEod.setERROR(l_appEodRecords.getRecord().get(1).trim());

                    dataset.add(appEod);
                    
                }
            
            }
            dbg("end of APP_EOD_STATUS_ERROR_DATASET convertDBtoReportObject",session);
            
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
