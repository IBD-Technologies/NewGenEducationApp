/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.user;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.app.CONTRACT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_INSTITUTE_ENTITY_ROLEMAPPING;
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
public class UVW_INSTITUTE_ENTITY_ROLEMAPPING_DATASET {
//     ArrayList<UVW_INSTITUTE_ENTITY_ROLEMAPPING> dataset;
    
    
    public ArrayList<UVW_INSTITUTE_ENTITY_ROLEMAPPING> getTableObject(String p_userID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside UVW_INSTITUTE_ENTITY_ROLEMAPPING_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
         Map<String,DBRecord>l_userInstituteEntityMap=null;
         try
         {
        l_userInstituteEntityMap=readBuffer.readTable("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER", "UVW_INSTITUTE_ENTITY_ROLEMAPPING", session, dbSession);
         }
           catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of UVW_INSTITUTE_ENTITY_ROLEMAPPING_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_userInstituteEntityMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<UVW_INSTITUTE_ENTITY_ROLEMAPPING> convertDBtoReportObject(Map<String,DBRecord>p_userInstituteEntityMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<UVW_INSTITUTE_ENTITY_ROLEMAPPING>dataset=new ArrayList();
        try{
            
            
            dbg("inside UVW_INSTITUTE_ENTITY_ROLEMAPPING convertDBtoReportObject",session);
            
           if(p_userInstituteEntityMap!=null&&!p_userInstituteEntityMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_userInstituteEntityMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_userRecords=recordIterator.next();
                    UVW_INSTITUTE_ENTITY_ROLEMAPPING userInstituteEntity=new UVW_INSTITUTE_ENTITY_ROLEMAPPING();
                    userInstituteEntity.setUSER_ID(l_userRecords.getRecord().get(0).trim());
                    userInstituteEntity.setROLE_ID(l_userRecords.getRecord().get(1).trim());
                    userInstituteEntity.setINSTITUTE_ID(l_userRecords.getRecord().get(2).trim());
                    userInstituteEntity.setVERSION_NUMBER(l_userRecords.getRecord().get(3).trim());
               
                    dataset.add(userInstituteEntity);
                    
                }
            
            }
           
               else
            {
                UVW_INSTITUTE_ENTITY_ROLEMAPPING service=new UVW_INSTITUTE_ENTITY_ROLEMAPPING();
                 
                    service.setUSER_ID(" ");
                    service.setROLE_ID(" ");
                    service.setINSTITUTE_ID(" ");
                    service.setVERSION_NUMBER(" ");
               
                    
                    
                    
 dataset.add(service);
                
            }
           
            dbg("end of UVW_INSTITUTE_ENTITY_ROLEMAPPING_DATASET convertDBtoReportObject",session);
            
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
