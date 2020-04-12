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
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_USER_CREDENTIALS;
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
public class UVW_USER_CREDENTIALS_DATASET {
//     ArrayList<UVW_USER_CREDENTIALS> dataset;
    
    
    public ArrayList<UVW_USER_CREDENTIALS> getTableObject(String p_userID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside UVW_USER_CREDENTIALS_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
         Map<String,DBRecord>l_userCreadentialMap=null;
                 try
                 {
                     l_userCreadentialMap=readBuffer.readTable("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER", "UVW_USER_CREDENTIALS", session, dbSession);
                 }
                   catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of UVW_USER_CREDENTIALS_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_userCreadentialMap,session) ;
        

    
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
    
    
    
    
    private ArrayList<UVW_USER_CREDENTIALS> convertDBtoReportObject(Map<String,DBRecord>p_userCreadentialMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<UVW_USER_CREDENTIALS>dataset=new ArrayList();
        try{
            
            
            dbg("inside UVW_USER_CREDENTIALS convertDBtoReportObject",session);
            
            if(p_userCreadentialMap!=null&&!p_userCreadentialMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_userCreadentialMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_creadentialRecords=recordIterator.next();
                    UVW_USER_CREDENTIALS userCreadential=new UVW_USER_CREDENTIALS();
                    userCreadential.setUSER_ID(l_creadentialRecords.getRecord().get(0).trim());
                    userCreadential.setPASSWORD(l_creadentialRecords.getRecord().get(1).trim());
                    userCreadential.setEXPIRY_DATE(l_creadentialRecords.getRecord().get(2).trim());
                    userCreadential.setVERSION_NUMBER(l_creadentialRecords.getRecord().get(3).trim());
                    dataset.add(userCreadential);
                    
                }
            
            }
            
                else
            {
                UVW_USER_CREDENTIALS service=new UVW_USER_CREDENTIALS();
                 
                    service.setUSER_ID(" ");
                    service.setPASSWORD(" ");
                    service.setEXPIRY_DATE(" ");
                    service.setVERSION_NUMBER(" ");
                  
 dataset.add(service);
                
            }
            
            
            dbg("end of UVW_USER_CREDENTIALS_DATASET convertDBtoReportObject",session);
            
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
