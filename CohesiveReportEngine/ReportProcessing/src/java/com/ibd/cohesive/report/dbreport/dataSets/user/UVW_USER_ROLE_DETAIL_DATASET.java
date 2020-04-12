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
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_USER_ROLE_DETAIL;
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
public class UVW_USER_ROLE_DETAIL_DATASET {
//     ArrayList<UVW_USER_ROLE_DETAIL> dataset;
    
    
    public ArrayList<UVW_USER_ROLE_DETAIL> getTableObject(String p_userID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside UVW_USER_ROLE_DETAIL_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        Map<String,DBRecord>l_userRoleDetailMap=null;
        try
        {
        l_userRoleDetailMap=readBuffer.readTable("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER", "UVW_USER_ROLE_DETAIL", session, dbSession);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of UVW_USER_ROLE_DETAIL_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_userRoleDetailMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<UVW_USER_ROLE_DETAIL> convertDBtoReportObject(Map<String,DBRecord>p_userRoleDetailMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<UVW_USER_ROLE_DETAIL>dataset=new ArrayList();
        try{
            
            
            dbg("inside UVW_USER_ROLE_DETAIL convertDBtoReportObject",session);
            
           if(p_userRoleDetailMap!=null&&!p_userRoleDetailMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_userRoleDetailMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_roleRecords=recordIterator.next();
                    UVW_USER_ROLE_DETAIL userRole=new UVW_USER_ROLE_DETAIL();
                    userRole.setROLE_ID(l_roleRecords.getRecord().get(0).trim());
                    userRole.setFUNCTION_ID(l_roleRecords.getRecord().get(1).trim());
                    userRole.setCREATE(l_roleRecords.getRecord().get(2).trim());
                    userRole.setMODIFY(l_roleRecords.getRecord().get(3).trim());
                    userRole.setDELETE(l_roleRecords.getRecord().get(4).trim());
                    userRole.setVIEW(l_roleRecords.getRecord().get(5).trim());
                    userRole.setAUTH(l_roleRecords.getRecord().get(6).trim());
                    userRole.setAUTOAUTH(l_roleRecords.getRecord().get(7).trim());
                    userRole.setREJECT(l_roleRecords.getRecord().get(8).trim());
                    userRole.setVERSION_NUMBER(l_roleRecords.getRecord().get(9).trim());

               
                    dataset.add(userRole);
                    
                }
            
            }
           
               else
            {
                UVW_USER_ROLE_DETAIL service=new UVW_USER_ROLE_DETAIL();
                 
                    service.setROLE_ID(" ");
                    service.setFUNCTION_ID(" ");
                    service.setCREATE(" ");
                    service.setMODIFY(" ");
                    service.setDELETE(" ");
                    service.setVIEW(" ");
                    service.setAUTH(" ");
                    service.setAUTOAUTH(" ");
                    service.setREJECT(" ");
                    service.setVERSION_NUMBER(" ");
                 
                    
 dataset.add(service);
                
            }
           
            dbg("end of UVW_USER_ROLE_DETAIL_DATASET convertDBtoReportObject",session);
            
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
