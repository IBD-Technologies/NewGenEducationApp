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
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_SUBJECT_MASTER;
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
public class IVW_SUBJECT_MASTER_DATASET {
//     ArrayList<IVW_SUBJECT_MASTER> dataset;
    
    
    public ArrayList<IVW_SUBJECT_MASTER> getTableObject(String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside IVW_SUBJECT_MASTER_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        Map<String,DBRecord>l_subjectTypeMap=null;
        try
        {
        l_subjectTypeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID,"INSTITUTE", "IVW_SUBJECT_MASTER", session, dbSession);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of IVW_SUBJECT_MASTER_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_subjectTypeMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<IVW_SUBJECT_MASTER> convertDBtoReportObject(Map<String,DBRecord>p_subjectTypeMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<IVW_SUBJECT_MASTER>dataset=new ArrayList();
        try{
            
            
            dbg("inside IVW_SUBJECT_MASTER_DATASET convertDBtoReportObject",session);
            
            if(p_subjectTypeMap!=null&&!p_subjectTypeMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_subjectTypeMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_subjectTypeRecords=recordIterator.next();
                    IVW_SUBJECT_MASTER subjectType=new IVW_SUBJECT_MASTER();
                 
                    subjectType.setSUBJECT_ID(l_subjectTypeRecords.getRecord().get(0).trim());
                    subjectType.setSUBJECT_NAME(l_subjectTypeRecords.getRecord().get(1).trim());

                    dataset.add(subjectType);
                    
                }
            
            }
            
            
            
            
                else
            {
                IVW_SUBJECT_MASTER service=new IVW_SUBJECT_MASTER();
                 
                    service.setSUBJECT_ID(" ");
                    service.setSUBJECT_NAME(" ");
                   
                                  
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            
            
            
            dbg("end of IVW_SUBJECT_MASTER_DATASET convertDBtoReportObject",session);
            
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
