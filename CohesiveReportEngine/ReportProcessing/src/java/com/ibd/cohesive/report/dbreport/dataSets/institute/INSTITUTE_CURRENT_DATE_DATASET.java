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
import com.ibd.cohesive.report.dbreport.dataModels.institute.INSTITUTE_CURRENT_DATE;
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
public class INSTITUTE_CURRENT_DATE_DATASET {
    
    public ArrayList<INSTITUTE_CURRENT_DATE> getTableObject(String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside INSTITUTE_CURRENT_DATE_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
         Map<String,DBRecord>l_contentTypeMap=null;
         try
         {
        l_contentTypeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID,"INSTITUTE", "INSTITUTE_CURRENT_DATE", session, dbSession);
         }
           catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
        
         dbg("end of INSTITUTE_CURRENT_DATE_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_contentTypeMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<INSTITUTE_CURRENT_DATE> convertDBtoReportObject(Map<String,DBRecord>p_contentTypeMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<INSTITUTE_CURRENT_DATE>dataset=new ArrayList();
        try{
            
            
            dbg("inside INSTITUTE_CURRENT_DATE_DATASET convertDBtoReportObject",session);
            
            if(p_contentTypeMap!=null&&!p_contentTypeMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_contentTypeMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_contentTypeRecords=recordIterator.next();
                    INSTITUTE_CURRENT_DATE contentType=new INSTITUTE_CURRENT_DATE();
                 
                    contentType.setINSTITUTE_ID(l_contentTypeRecords.getRecord().get(0).trim());
                    contentType.setCURRENT_DATE(l_contentTypeRecords.getRecord().get(1).trim());

                    dataset.add(contentType);
                    
                }
            
            }
            
                 else
            {
                INSTITUTE_CURRENT_DATE service=new INSTITUTE_CURRENT_DATE();
                 
                    service.setINSTITUTE_ID(" ");
                    service.setCURRENT_DATE(" ");
                   
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            dbg("end of INSTITUTE_CURRENT_DATE_DATASET convertDBtoReportObject",session);
            
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
