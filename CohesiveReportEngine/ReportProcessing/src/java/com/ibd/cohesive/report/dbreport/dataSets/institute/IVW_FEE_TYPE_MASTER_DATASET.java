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
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_FEE_TYPE_MASTER;
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
public class IVW_FEE_TYPE_MASTER_DATASET {
//      ArrayList<IVW_FEE_TYPE_MASTER> dataset;
    
    
    public ArrayList<IVW_FEE_TYPE_MASTER> getTableObject(String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside IVW_FEE_TYPE_MASTER_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
         Map<String,DBRecord>l_feeTypeTypeMap=null;
         try
         {
        l_feeTypeTypeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID,"INSTITUTE", "IVW_FEE_TYPE_MASTER", session, dbSession);
         }  
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
         dbg("end of IVW_FEE_TYPE_MASTER_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_feeTypeTypeMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<IVW_FEE_TYPE_MASTER> convertDBtoReportObject(Map<String,DBRecord>p_feeTypeMap,CohesiveSession session)throws DBProcessingException{
    
       ArrayList<IVW_FEE_TYPE_MASTER> dataset=new ArrayList();
        try{
            
            
            dbg("inside IVW_FEE_TYPE_MASTER_DATASET convertDBtoReportObject",session);
            
            if(p_feeTypeMap!=null&&!p_feeTypeMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_feeTypeMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_feeTypeRecords=recordIterator.next();
                    IVW_FEE_TYPE_MASTER feeType=new IVW_FEE_TYPE_MASTER();
                 
                    feeType.setFEE_TYPE(l_feeTypeRecords.getRecord().get(0).trim());

                    dataset.add(feeType);
                    
                }
            
            }
                 else
            {
                IVW_FEE_TYPE_MASTER service=new IVW_FEE_TYPE_MASTER();
                 
                    service.setFEE_TYPE(" ");
                    
                                  
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            dbg("end of IVW_FEE_TYPE_MASTER_DATASET convertDBtoReportObject",session);
            
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
