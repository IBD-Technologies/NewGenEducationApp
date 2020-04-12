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
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_OUT_LOG;
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
public class UVW_OUT_LOG_DATASET {
//     ArrayList<UVW_OUT_LOG> dataset;
    
    
    public ArrayList<UVW_OUT_LOG> getTableObject(String p_userID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside UVW_OUT_LOG_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
         Map<String,DBRecord>l_userOutLogMap=null;
         try
         {
        l_userOutLogMap=readBuffer.readTable("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_userID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_userID+"_LOG","USER", "UVW_OUT_LOG", session, dbSession);
         }
           catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of UVW_OUT_LOG_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_userOutLogMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<UVW_OUT_LOG> convertDBtoReportObject(Map<String,DBRecord>p_userOutLogMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<UVW_OUT_LOG>dataset=new ArrayList();
        try{
            
            
            dbg("inside UVW_OUT_LOG convertDBtoReportObject",session);
            
            if(p_userOutLogMap!=null&&!p_userOutLogMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_userOutLogMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_outLogRecords=recordIterator.next();
                    UVW_OUT_LOG userOutLog=new UVW_OUT_LOG();
                    userOutLog.setMESSAGE_ID(l_outLogRecords.getRecord().get(0).trim());
                    userOutLog.setCORRELATION_ID(l_outLogRecords.getRecord().get(1).trim());
                    userOutLog.setSERVICE(l_outLogRecords.getRecord().get(2).trim());
                    userOutLog.setOPERATION(l_outLogRecords.getRecord().get(3).trim());
                    userOutLog.setBUSINESS_ENTITY(l_outLogRecords.getRecord().get(4).trim());
                    userOutLog.setSTATUS(l_outLogRecords.getRecord().get(5).trim());
                    userOutLog.setRESPONSE_JSON(l_outLogRecords.getRecord().get(6).trim());
                    userOutLog.setTIME_STAMP(l_outLogRecords.getRecord().get(7).trim());
                    userOutLog.setSOURCE(l_outLogRecords.getRecord().get(8).trim());
     

               
                    dataset.add(userOutLog);
                    
                }
            
            }
            
                else
            {
                UVW_OUT_LOG service=new UVW_OUT_LOG();
                 
                    service.setMESSAGE_ID(" ");
                    service.setCORRELATION_ID(" ");
                    service.setSERVICE(" ");
                    service.setOPERATION(" ");
                    service.setBUSINESS_ENTITY(" ");
                    service.setSTATUS(" ");
                    service.setRESPONSE_JSON(" ");
                    service.setTIME_STAMP(" ");
                    service.setSOURCE(" ");
                   
 dataset.add(service);
                
            }
            
            dbg("end of UVW_OUT_LOG_DATASET convertDBtoReportObject",session);
            
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
