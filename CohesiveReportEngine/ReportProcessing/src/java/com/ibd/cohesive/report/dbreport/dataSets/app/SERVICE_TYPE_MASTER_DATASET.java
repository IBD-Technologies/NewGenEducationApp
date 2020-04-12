/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.app;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.app.CONTRACT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.app.SERVICE_TYPE_MASTER;
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
 * @author IBD Technologies
 */
public class SERVICE_TYPE_MASTER_DATASET {
//     ArrayList<SERVICE_TYPE_MASTER> dataset;
    
    
    public ArrayList<SERVICE_TYPE_MASTER> getTableObject(String p_instituteID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside SERVICE_TYPE_MASTER_DATASET--->getTableObject",session);    
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
         
        l_serviceMasterMap=readBuffer.readTable("APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive","APP", "SERVICE_TYPE_MASTER", session, dbSession,ismaxVersionRequired);
         }
           catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
        
         dbg("end of SERVICE_TYPE_MASTER_DATASET--->getTableObject",session);  
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
    
    
    
    
    private ArrayList<SERVICE_TYPE_MASTER> convertDBtoReportObject(Map<String,DBRecord>p_serviceMasterMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<SERVICE_TYPE_MASTER>dataset=new ArrayList();
        try{
            
            
            dbg("inside SERVICE_TYPE_MASTER_DATASET convertDBtoReportObject",session);
            
            if(p_serviceMasterMap!=null&&!p_serviceMasterMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_serviceMasterMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_serviceTypeRecords=recordIterator.next();
                    SERVICE_TYPE_MASTER service=new SERVICE_TYPE_MASTER();
                 
                    service.setSERVICE_NAME(l_serviceTypeRecords.getRecord().get(0).trim());
                    service.setSERVICE_TYPE(l_serviceTypeRecords.getRecord().get(1).trim());

                    dataset.add(service);
                    
                }
            
            }
            
                 else
            {
                SERVICE_TYPE_MASTER service=new SERVICE_TYPE_MASTER();
                 
                    service.setSERVICE_NAME(" ");
                    service.setSERVICE_TYPE(" ");
                                                    
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            dbg("end of SERVICE_TYPE_MASTER_DATASET convertDBtoReportObject",session);
            
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
