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
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_USER_PROFILE;
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
public class UVW_USER_PROFILE_DATASET {
//      ArrayList<UVW_USER_PROFILE> dataset;
    
    
    public ArrayList<UVW_USER_PROFILE> getTableObject(String p_userID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside UVW_USER_PROFILE_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        Map<String,DBRecord>l_userProfileMap=null;
        try
        {
        l_userProfileMap=readBuffer.readTable("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER", "UVW_USER_PROFILE", session, dbSession);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of UVW_USER_PROFILE_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_userProfileMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<UVW_USER_PROFILE> convertDBtoReportObject(Map<String,DBRecord>p_userProfileMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<UVW_USER_PROFILE>dataset=new ArrayList();
        try{
            
            
            dbg("inside UVW_USER_PROFILE convertDBtoReportObject",session);
            
            if(p_userProfileMap!=null&&!p_userProfileMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_userProfileMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_profileRecords=recordIterator.next();
                    UVW_USER_PROFILE userProfile=new UVW_USER_PROFILE();
                    userProfile.setUSER_ID(l_profileRecords.getRecord().get(0).trim());
                    userProfile.setUSER_NAME(l_profileRecords.getRecord().get(1).trim());
                    userProfile.setEMAIL_ID(l_profileRecords.getRecord().get(2).trim());
                    userProfile.setMOBILE_NO(l_profileRecords.getRecord().get(3).trim());
                    userProfile.setMAKER_ID(l_profileRecords.getRecord().get(4).trim());
                    userProfile.setCHECKER_ID(l_profileRecords.getRecord().get(5).trim());
                    userProfile.setMAKER_DATE_STAMP(l_profileRecords.getRecord().get(6).trim());
                    userProfile.setCHECKER_DATE_STAMP(l_profileRecords.getRecord().get(7).trim());
                    userProfile.setRECORD_STATUS(l_profileRecords.getRecord().get(8).trim());
                    userProfile.setAUTH_STATUS(l_profileRecords.getRecord().get(9).trim());
                    userProfile.setVERSION_NUMBER(l_profileRecords.getRecord().get(10).trim());
                    userProfile.setMAKER_REMARKS(l_profileRecords.getRecord().get(11).trim());
                    userProfile.setCHECKER_REMARKS(l_profileRecords.getRecord().get(12).trim()); 

               
                    dataset.add(userProfile);
                    
                }
            
            }
            
                else
            {
                UVW_USER_PROFILE service=new UVW_USER_PROFILE();
                 
                    service.setUSER_ID(" ");
                    service.setUSER_NAME(" ");
                    service.setEMAIL_ID(" ");
                    service.setMOBILE_NO(" ");
                    service.setMAKER_ID(" ");
                    service.setCHECKER_ID(" ");
                    service.setMAKER_DATE_STAMP(" ");
                    service.setCHECKER_DATE_STAMP(" ");
                    service.setRECORD_STATUS(" ");
                    service.setAUTH_STATUS(" ");
                    service.setVERSION_NUMBER(" ");
                    service.setMAKER_REMARKS(" ");
                    service.setCHECKER_REMARKS(" ");
                 
                    
 dataset.add(service);
                
            }
            
            
            dbg("end of UVW_USER_PROFILE_DATASET convertDBtoReportObject",session);
            
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
