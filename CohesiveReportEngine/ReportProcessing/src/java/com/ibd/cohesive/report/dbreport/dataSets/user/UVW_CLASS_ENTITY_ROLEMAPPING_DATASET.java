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
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_CLASS_ENTITY_ROLEMAPPING;
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
public class UVW_CLASS_ENTITY_ROLEMAPPING_DATASET {
//     ArrayList<UVW_CLASS_ENTITY_ROLEMAPPING> dataset;
    
    
    public ArrayList<UVW_CLASS_ENTITY_ROLEMAPPING> getTableObject(String p_userID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside UVW_CLASS_ENTITY_ROLEMAPPING_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
         Map<String,DBRecord>l_userClassEntityMap=null;
         try
         {
        l_userClassEntityMap=readBuffer.readTable("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER", "UVW_CLASS_ENTITY_ROLEMAPPING", session, dbSession);
         }
         
           catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
         
         dbg("end of UVW_CLASS_ENTITY_ROLEMAPPING_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_userClassEntityMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<UVW_CLASS_ENTITY_ROLEMAPPING> convertDBtoReportObject(Map<String,DBRecord>p_userClassEntityMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<UVW_CLASS_ENTITY_ROLEMAPPING>dataset=new ArrayList();
        try{
            
            
            dbg("inside UVW_CLASS_ENTITY_ROLEMAPPING convertDBtoReportObject",session);
            
            if(p_userClassEntityMap!=null&&!p_userClassEntityMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_userClassEntityMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_classRoleRecords=recordIterator.next();
                    UVW_CLASS_ENTITY_ROLEMAPPING userClassEntity=new UVW_CLASS_ENTITY_ROLEMAPPING();
                    userClassEntity.setUSER_ID(l_classRoleRecords.getRecord().get(0).trim());
                    userClassEntity.setROLE_ID(l_classRoleRecords.getRecord().get(1).trim());
                    userClassEntity.setSTANDARD(l_classRoleRecords.getRecord().get(2).trim());
                    userClassEntity.setSECTION(l_classRoleRecords.getRecord().get(3).trim());
                    userClassEntity.setVERSION_NUMBER(l_classRoleRecords.getRecord().get(4).trim());
                    userClassEntity.setINSTITUTE_ID(l_classRoleRecords.getRecord().get(5).trim());
                    dataset.add(userClassEntity);
                    
                }
            
            }
            
                else
            {
                UVW_CLASS_ENTITY_ROLEMAPPING service=new UVW_CLASS_ENTITY_ROLEMAPPING();
                 
                    service.setUSER_ID(" ");
                    service.setROLE_ID(" ");
                    service.setSTANDARD(" ");
                    service.setSECTION(" ");
                    service.setVERSION_NUMBER(" ");
                    service.setINSTITUTE_ID(" ");
                   
                    
 dataset.add(service);
                
            }
            
            
            dbg("end of UVW_CLASS_ENTITY_ROLEMAPPING_DATASET convertDBtoReportObject",session);
            
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
