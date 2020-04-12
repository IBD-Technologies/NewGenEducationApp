/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.institute;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.app.CONTRACT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_STANDARD_MASTER;
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
public class IVW_STANDARD_MASTER_DATASET {
//     ArrayList<IVW_STANDARD_MASTER> dataset;
    
    
    public ArrayList<IVW_STANDARD_MASTER> getTableObject(String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside IVW_STANDARD_MASTER_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
        Map<String,DBRecord>l_standardTypeMap=null;
        try
        {
           
      
      l_standardTypeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID,"INSTITUTE", "IVW_STANDARD_MASTER", session, dbSession);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
       
         
        
         dbg("end of IVW_STANDARD_MASTER_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_standardTypeMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<IVW_STANDARD_MASTER> convertDBtoReportObject(Map<String,DBRecord>p_standardTypeMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<IVW_STANDARD_MASTER>dataset=new ArrayList();
        try{
            
            
            dbg("inside IVW_STANDARD_MASTER_DATASET convertDBtoReportObject",session);
            
            if(p_standardTypeMap!=null&&!p_standardTypeMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_standardTypeMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_standardTypeRecords=recordIterator.next();
                    IVW_STANDARD_MASTER standardType=new IVW_STANDARD_MASTER();
                 
                    standardType.setSTANDARD(l_standardTypeRecords.getRecord().get(0).trim());
                    standardType.setSECTION(l_standardTypeRecords.getRecord().get(1).trim());
                    standardType.setTEACHER_ID(l_standardTypeRecords.getRecord().get(2).trim());
                    dataset.add(standardType);
                    
                }
            
            }
            
                else
            {
                IVW_STANDARD_MASTER service=new IVW_STANDARD_MASTER();
                 
                    service.setSTANDARD(" ");
                    service.setSECTION(" ");
                    service.setTEACHER_ID(" ");
                  
                    
                    
                    
 dataset.add(service);
                
            }
            
            dbg("end of IVW_STANDARD_MASTER_DATASET convertDBtoReportObject",session);
            
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
