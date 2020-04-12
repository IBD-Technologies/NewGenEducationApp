/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.classEntity;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.app.CONTRACT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_FEE_MANAGEMENT;
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
public class CLASS_FEE_MANAGEMENT_DATASET {
//    ArrayList<CLASS_FEE_MANAGEMENT> dataset;
    
    
    public ArrayList<CLASS_FEE_MANAGEMENT> getTableObject(String p_standard,String p_section,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside CLASS_FEE_MANAGEMENT_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
         Map<String,DBRecord>l_feeManagementMap=null;
         try
         {
        l_feeManagementMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section,"CLASS", "CLASS_FEE_MANAGEMENT", session, dbSession);
         }
           catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
        
         dbg("end of CLASS_FEE_MANAGEMENT_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_feeManagementMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<CLASS_FEE_MANAGEMENT> convertDBtoReportObject(Map<String,DBRecord>p_feeMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<CLASS_FEE_MANAGEMENT>dataset=new ArrayList();
        try{
            
            
            dbg("inside CLASS_FEE_MANAGEMENT_DATASET convertDBtoReportObject",session);
            
            if(p_feeMap!=null&&!p_feeMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_feeMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_feeRecords=recordIterator.next();
                    CLASS_FEE_MANAGEMENT feeManagement=new CLASS_FEE_MANAGEMENT();
                    feeManagement.setSTANDARD(l_feeRecords.getRecord().get(0).trim());
                    feeManagement.setSECTION(l_feeRecords.getRecord().get(1).trim());
                    feeManagement.setFEE_ID(l_feeRecords.getRecord().get(2).trim());
                    feeManagement.setFEE_TYPE(l_feeRecords.getRecord().get(3).trim());
                    feeManagement.setAMOUNT(l_feeRecords.getRecord().get(4).trim());
                    feeManagement.setDUE_DATE(l_feeRecords.getRecord().get(5).trim());
                    feeManagement.setMAKER_ID(l_feeRecords.getRecord().get(6).trim());
                    feeManagement.setCHECKER_ID(l_feeRecords.getRecord().get(7).trim());
                    feeManagement.setMAKER_DATE_STAMP(l_feeRecords.getRecord().get(8).trim());
                    feeManagement.setCHECKER_DATE_STAMP(l_feeRecords.getRecord().get(9).trim());
                    feeManagement.setRECORD_STATUS(l_feeRecords.getRecord().get(10).trim());
                    feeManagement.setAUTH_STATUS(l_feeRecords.getRecord().get(11).trim());
                    feeManagement.setVERSION_NUMBER(l_feeRecords.getRecord().get(12).trim());
                    feeManagement.setMAKER_REMARKS(l_feeRecords.getRecord().get(13).trim());
                    feeManagement.setCHECKER_REMARKS(l_feeRecords.getRecord().get(14).trim()); 
                    
                    dataset.add(feeManagement);
                    
                }
            
            }
                 else
            {
                CLASS_FEE_MANAGEMENT service=new CLASS_FEE_MANAGEMENT();
                 
                    service.setSTANDARD(" ");
                    service.setSECTION(" ");
                    service.setFEE_ID(" ");
                    service.setFEE_TYPE(" ");
                    service.setAMOUNT(" ");
                    service.setDUE_DATE(" ");
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
            
            
            dbg("end of CLASS_FEE_MANAGEMENT_DATASET convertDBtoReportObject",session);
            
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
