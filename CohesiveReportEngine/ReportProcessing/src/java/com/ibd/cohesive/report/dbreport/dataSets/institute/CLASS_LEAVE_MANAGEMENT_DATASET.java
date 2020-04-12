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
import com.ibd.cohesive.report.dbreport.dataModels.institute.CLASS_LEAVE_MANAGEMENT;
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
public class CLASS_LEAVE_MANAGEMENT_DATASET {
    
    
      public ArrayList<CLASS_LEAVE_MANAGEMENT> getTableObject(String p_instanceID,String p_standard,String p_section,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside CLASS_LEAVE_MANAGEMENT_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
        Map<String,DBRecord>l_contentTypeMap=null;
        try
        {
        l_contentTypeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE", "CLASS_LEAVE_MANAGEMENT", session, dbSession);
        }  
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of CLASS_LEAVE_MANAGEMENT_DATASET--->getTableObject",session);  
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
    
    
    
    
    private ArrayList<CLASS_LEAVE_MANAGEMENT> convertDBtoReportObject(Map<String,DBRecord>p_contentTypeMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<CLASS_LEAVE_MANAGEMENT>dataset=new ArrayList();
        try{
            
            
            dbg("inside CLASS_LEAVE_MANAGEMENT_DATASET convertDBtoReportObject",session);
            
            if(p_contentTypeMap!=null&&!p_contentTypeMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_contentTypeMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_contentTypeRecords=recordIterator.next();
                    CLASS_LEAVE_MANAGEMENT contentType=new CLASS_LEAVE_MANAGEMENT();
                 
                    contentType.setSTUDENT_ID(l_contentTypeRecords.getRecord().get(0).trim());
                    contentType.setFROM(l_contentTypeRecords.getRecord().get(1).trim());
                    contentType.setTO(l_contentTypeRecords.getRecord().get(2).trim());
                    contentType.setFROM_NOON(l_contentTypeRecords.getRecord().get(3).trim());
                    contentType.setTO_NOON(l_contentTypeRecords.getRecord().get(4).trim());
                    
                    
                    

                    dataset.add(contentType);
                    
                }
            
            }
            
                 else
            {
                CLASS_LEAVE_MANAGEMENT service=new CLASS_LEAVE_MANAGEMENT();
                 
                    service.setSTUDENT_ID(" ");
                    service.setFROM(" ");
                    service.setTO(" ");
                    service.setFROM_NOON(" ");
                    service.setTO_NOON(" ");
                   
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            dbg("end of CLASS_LEAVE_MANAGEMENT_DATASET convertDBtoReportObject",session);
            
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
