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
import com.ibd.cohesive.report.dbreport.dataModels.app.CONTRACT_MASTER;
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
public class CONTRACT_MASTER_DATASET {
    
    
    public ArrayList<CONTRACT_MASTER> getTableObject(String p_instituteID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside CONTRACT_MASTER_DATASET--->getTableObject",session);    
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
        l_serviceMasterMap=readBuffer.readTable("APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive","APP", "CONTRACT_MASTER", session, dbSession,ismaxVersionRequired);
        }
         catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
       
        
        
         dbg("end of CONTRACT_MASTER_DATASET--->getTableObject",session);  
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
    
    
    
    
    private ArrayList<CONTRACT_MASTER> convertDBtoReportObject(Map<String,DBRecord>p_serviceMasterMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<CONTRACT_MASTER>dataset=new ArrayList();
        try{
            
            
            dbg("inside CONTRACT_MASTER_DATASET convertDBtoReportObject",session);
            
            if(p_serviceMasterMap!=null&&!p_serviceMasterMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_serviceMasterMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_serviceTypeRecords=recordIterator.next();
                    CONTRACT_MASTER service=new CONTRACT_MASTER();
                 
                    service.setINSTITUTE_ID(l_serviceTypeRecords.getRecord().get(0).trim());
                    service.setCONTRACT_ID(l_serviceTypeRecords.getRecord().get(1).trim());
                    service.setSMS_LIMIT(l_serviceTypeRecords.getRecord().get(2).trim());
                    service.setEMAIL_LIMIT(l_serviceTypeRecords.getRecord().get(3).trim());
                    service.setSMS_USED(l_serviceTypeRecords.getRecord().get(4).trim());
                    service.setEMAIL_USED(l_serviceTypeRecords.getRecord().get(5).trim());
                    service.setSMS_VENDOR(l_serviceTypeRecords.getRecord().get(6).trim());
                    service.setEMAIL_VENDOR(l_serviceTypeRecords.getRecord().get(7).trim());
                    service.setEMAIL_ID(l_serviceTypeRecords.getRecord().get(8).trim());
                    service.setCONTACT_EMAIL(l_serviceTypeRecords.getRecord().get(9).trim());
                    service.setCONTACT_MOBILE_NO(l_serviceTypeRecords.getRecord().get(10).trim());
                    service.setCOMMUNICATION_MODE(l_serviceTypeRecords.getRecord().get(11).trim());
                    service.setCOMMUNICATION_LANGUAGE(l_serviceTypeRecords.getRecord().get(12).trim());
                    service.setCOUNTRY_CODE(l_serviceTypeRecords.getRecord().get(13).trim());
                    service.setPLAN(l_serviceTypeRecords.getRecord().get(14).trim());
                    
                    dataset.add(service);
                    
                }
            
            }
            
              else
            {
                CONTRACT_MASTER service=new CONTRACT_MASTER();
                 
                    service.setINSTITUTE_ID(" ");
                    service.setCONTRACT_ID(" ");
                    service.setSMS_LIMIT(" ");
                    service.setEMAIL_LIMIT(" ");
                    service.setSMS_USED(" ");
                    service.setEMAIL_USED(" ");
                    service.setSMS_VENDOR(" ");
                    service.setEMAIL_VENDOR(" ");
                    service.setEMAIL_ID(" ");
                    service.setCONTACT_EMAIL(" ");
                    service.setCONTACT_MOBILE_NO(" ");
                    service.setCOMMUNICATION_MODE(" ");
                    service.setCOMMUNICATION_LANGUAGE(" ");
                    service.setCOUNTRY_CODE(" ");
                    service.setPLAN(" ");
                                  
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            
            
            
            
            
            
            
            dbg("end of CONTRACT_MASTER_DATASET convertDBtoReportObject",session);
            
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
