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
import com.ibd.cohesive.report.dbreport.dataModels.app.ARCH_APPLY_STATUS;
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
public class ARCH_APPLY_STATUS_DATASET {
    
     public ArrayList<ARCH_APPLY_STATUS> getTableObject(String p_instituteID,String p_date ,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside ARCH_APPLY_STATUS_DATASET--->getTableObject",session);    
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
        
        l_serviceMasterMap=readBuffer.readTable("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_date+i_db_properties.getProperty("FOLDER_DELIMITER")+"Standby","ARCH", "ARCH_APPLY_STATUS", session, dbSession,ismaxVersionRequired);
         }
        catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
        
         dbg("end of ARCH_APPLY_STATUS_DATASET--->getTableObject",session);  
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
    
    
    
    
    private ArrayList<ARCH_APPLY_STATUS> convertDBtoReportObject(Map<String,DBRecord>p_serviceMasterMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<ARCH_APPLY_STATUS>dataset=new ArrayList();
        try{
            
            
            dbg("inside ARCH_APPLY_STATUS_DATASET convertDBtoReportObject",session);
            
            if(p_serviceMasterMap!=null&&!p_serviceMasterMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_serviceMasterMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_serviceTypeRecords=recordIterator.next();
                    ARCH_APPLY_STATUS service=new ARCH_APPLY_STATUS();
                 
                    service.setFILE_NAME(l_serviceTypeRecords.getRecord().get(0).trim());
                    service.setSEQUENCE_NO(l_serviceTypeRecords.getRecord().get(1).trim());
                    service.setCHECKSUM_STATUS(l_serviceTypeRecords.getRecord().get(2).trim());
                    service.setRECEIVE_STATUS(l_serviceTypeRecords.getRecord().get(3).trim());
                    service.setAPPLY_STATUS(l_serviceTypeRecords.getRecord().get(4).trim());
                    service.setERROR(l_serviceTypeRecords.getRecord().get(5).trim());
                    

                    dataset.add(service);
                    
                }
            
            }
             else
            {
                ARCH_APPLY_STATUS service=new ARCH_APPLY_STATUS();
                 
                    service.setFILE_NAME(" ");
                    service.setSEQUENCE_NO(" ");
                    service.setCHECKSUM_STATUS(" ");
                    service.setRECEIVE_STATUS(" ");
                    service.setAPPLY_STATUS(" ");
                    service.setERROR(" ");
 dataset.add(service);
                
            }   
            dbg("end of ARCH_APPLY_STATUS_DATASET convertDBtoReportObject",session);
            
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
