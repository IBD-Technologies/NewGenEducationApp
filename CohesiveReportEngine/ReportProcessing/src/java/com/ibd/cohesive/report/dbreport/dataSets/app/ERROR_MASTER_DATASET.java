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
import com.ibd.cohesive.report.dbreport.dataModels.app.ERROR_MASTER;
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
public class ERROR_MASTER_DATASET {
//      ArrayList<ERROR_MASTER> dataset;
    
    
    public ArrayList<ERROR_MASTER> getTableObject(String p_instituteID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside ERROR_MASTER_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
        Map<String,DBRecord>l_errorMasterMap=null;
        try
        {
        l_errorMasterMap=readBuffer.readTable("APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive","APP", "ERROR_MASTER", session, dbSession,ismaxVersionRequired);
        } 
         catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
        
         dbg("end of ERROR_MASTER_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_errorMasterMap,session) ;

    
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
    
    
    
    
    private ArrayList<ERROR_MASTER> convertDBtoReportObject(Map<String,DBRecord>p_errorMasterMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<ERROR_MASTER>dataset=new ArrayList();
        try{
            
            
            dbg("inside ERROR_MASTER_DATASET convertDBtoReportObject",session);
            
             if(p_errorMasterMap!=null&&!p_errorMasterMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_errorMasterMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_errorTypeRecords=recordIterator.next();
                    ERROR_MASTER error=new ERROR_MASTER();
                 
                    error.setERROR_CODE(l_errorTypeRecords.getRecord().get(0).trim());
                    error.setERROR_MESSAGE(l_errorTypeRecords.getRecord().get(1).trim());

                    dataset.add(error);
                    
                }
            
            }
             
             
              else
            {
                ERROR_MASTER service=new ERROR_MASTER();
                 
                    service.setERROR_CODE(" ");
                    service.setERROR_MESSAGE(" ");
                   
 dataset.add(service);
                
            }  
             
             
            dbg("end of ERROR_MASTER_DATASET convertDBtoReportObject",session);
            
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
