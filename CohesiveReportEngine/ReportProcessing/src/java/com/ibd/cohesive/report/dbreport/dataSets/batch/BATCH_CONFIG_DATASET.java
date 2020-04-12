/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.batch;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.BATCH_CONFIG;
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
public class BATCH_CONFIG_DATASET {
//    ArrayList<BATCH_CONFIG> dataset;
    
    
    public ArrayList<BATCH_CONFIG> getTableObject(String p_businessDate,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside BATCH_CONFIG_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
        
        
        Map<String,DBRecord>l_tableMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+p_businessDate, "BATCH", "BATCH_CONFIG", session, dbSession,ismaxVersionRequired);
          
                    
        
         dbg("end of BATCH_CONFIG_DATASET--->getTableObject",session);  
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
    
    
    
    
    private ArrayList<BATCH_CONFIG> convertDBtoReportObject(Map<String,DBRecord>l_tableMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<BATCH_CONFIG>dataset=new ArrayList();
        try{
            
            
            dbg("inside BATCH_CONFIG_DATASET convertDBtoReportObject",session);
            
            if(l_tableMap!=null&&!l_tableMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=l_tableMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_eodRecords=recordIterator.next();
                    BATCH_CONFIG eod=new BATCH_CONFIG();
                    
                    eod.setBATCH_NAME(l_eodRecords.getRecord().get(0).trim());
                    eod.setLAYER(l_eodRecords.getRecord().get(1).trim());
                    eod.setEXECUTION_FREQUENCY(l_eodRecords.getRecord().get(2).trim());
                    eod.setSTART_TIME(l_eodRecords.getRecord().get(3).trim());
                    eod.setEOD(l_eodRecords.getRecord().get(4).trim());
                    eod.setINTRA_DAY_BATCH(l_eodRecords.getRecord().get(5).trim());
                    eod.setREC_FAIL(l_eodRecords.getRecord().get(6).trim());
                    eod.setNO_OF_THREADS(l_eodRecords.getRecord().get(7).trim());
                    eod.setCHILD_NO_OF_THREADS(l_eodRecords.getRecord().get(8).trim());
                    eod.setMAKER_ID(l_eodRecords.getRecord().get(9).trim());
                    eod.setCHECKER_ID(l_eodRecords.getRecord().get(10).trim());
                    eod.setMAKER_DATE_STAMP(l_eodRecords.getRecord().get(11).trim());
                    eod.setCHECKER_DATE_STAMP(l_eodRecords.getRecord().get(12).trim());
                    eod.setRECORD_STATUS(l_eodRecords.getRecord().get(13).trim());
                    eod.setAUTH_STATUS(l_eodRecords.getRecord().get(14).trim());
                    eod.setVERSION_NUMBER(l_eodRecords.getRecord().get(15).trim());
                    eod.setMAKER_REMARKS(l_eodRecords.getRecord().get(16).trim());
                    eod.setCHECKER_REMARKS(l_eodRecords.getRecord().get(17).trim()); 
                    
                    
                    

                    dataset.add(eod);
                    
                }
            
            }
            dbg("end of BATCH_CONFIG_DATASET convertDBtoReportObject",session);
            
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
