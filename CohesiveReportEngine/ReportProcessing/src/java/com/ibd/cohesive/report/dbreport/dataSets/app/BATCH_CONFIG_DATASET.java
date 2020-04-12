/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.app;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.app.APP_SUPPORT;
import com.ibd.cohesive.report.dbreport.dataModels.app.BATCH_CONFIG;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author ibdtech
 */
public class BATCH_CONFIG_DATASET {
    
     public ArrayList<BATCH_CONFIG> getTableObject(String p_instituteID,String p_businessDate,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside BATCH_CONFIG_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
         Map<String,DBRecord>l_serviceMasterMap=null;
         try
         {
        l_serviceMasterMap=readBuffer.readTable("APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "BATCH_CONFIG", session, dbSession,ismaxVersionRequired);
         }
         catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
        
        
        
         dbg("end of BATCH_CONFIG_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_serviceMasterMap,session) ;

    
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
    
    
    
    
    private ArrayList<BATCH_CONFIG> convertDBtoReportObject(Map<String,DBRecord>p_serviceMasterMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<BATCH_CONFIG>dataset=new ArrayList();
        try{
            
            
            dbg("inside BATCH_CONFIG_DATASET convertDBtoReportObject",session);
            
            if(p_serviceMasterMap!=null&&!p_serviceMasterMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_serviceMasterMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_serviceTypeRecords=recordIterator.next();
                    BATCH_CONFIG service=new BATCH_CONFIG();
                 
                    service.setBATCH_NAME(l_serviceTypeRecords.getRecord().get(0).trim());
                    service.setLAYER(l_serviceTypeRecords.getRecord().get(1).trim());
                    service.setEXECUTION_FREQUENCY(l_serviceTypeRecords.getRecord().get(2).trim());
                    service.setSTART_TIME(l_serviceTypeRecords.getRecord().get(3).trim());
                    service.setEOD(l_serviceTypeRecords.getRecord().get(4).trim());
                    service.setINTRA_DAY_BATCH(l_serviceTypeRecords.getRecord().get(5).trim());
                    service.setREC_FAIL(l_serviceTypeRecords.getRecord().get(6).trim());
                    service.setNO_OF_THREADS(l_serviceTypeRecords.getRecord().get(7).trim());
                    service.setCHILD_NO_OF_THREADS(l_serviceTypeRecords.getRecord().get(8).trim());
                    service.setMAKER_ID(l_serviceTypeRecords.getRecord().get(9).trim());
                    service.setCHECKER_ID(l_serviceTypeRecords.getRecord().get(10).trim());
                    service.setMAKER_DATE_STAMP(l_serviceTypeRecords.getRecord().get(11).trim());
                    service.setCHECKER_DATE_STAMP(l_serviceTypeRecords.getRecord().get(12).trim());
                    service.setRECORD_STATUS(l_serviceTypeRecords.getRecord().get(13).trim());
                    service.setAUTH_STATUS(l_serviceTypeRecords.getRecord().get(14).trim());
                    service.setVERSION_NUMBER(l_serviceTypeRecords.getRecord().get(15).trim());
                    service.setMAKER_REMARKS(l_serviceTypeRecords.getRecord().get(16).trim());
                    service.setCHECKER_REMARKS(l_serviceTypeRecords.getRecord().get(17).trim());

                    dataset.add(service);
                    
                }
            
            }
            
            else
            {
                BATCH_CONFIG service=new BATCH_CONFIG();
                 
                    service.setBATCH_NAME(" ");
                    service.setLAYER(" ");
                    service.setEXECUTION_FREQUENCY(" ");
                    service.setSTART_TIME(" ");
                    service.setEOD(" ");
                    service.setINTRA_DAY_BATCH(" ");
                    service.setREC_FAIL(" ");
                    service.setNO_OF_THREADS(" ");
                    service.setCHILD_NO_OF_THREADS(" ");
                    service.setMAKER_ID(" ");
                    service.setCHECKER_ID(" ");
                    service.setMAKER_DATE_STAMP(" ");
                    service.setCHECKER_DATE_STAMP(" ");
                    service.setRECORD_STATUS(" ");
                    service.setAUTH_STATUS(" ");
                    service.setVERSION_NUMBER(" ");
                    service.setMAKER_REMARKS(" ");
                    service.setCHECKER_REMARKS(" ");
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
 dataset.add(service);
                
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
