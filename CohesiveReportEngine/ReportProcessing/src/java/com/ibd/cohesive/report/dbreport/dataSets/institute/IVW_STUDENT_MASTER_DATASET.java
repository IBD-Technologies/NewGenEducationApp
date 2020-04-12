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
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_STUDENT_MASTER;
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
public class IVW_STUDENT_MASTER_DATASET {
//     ArrayList<IVW_STUDENT_MASTER> dataset;
    
    
    public ArrayList<IVW_STUDENT_MASTER> getTableObject(String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside IVW_STUDENT_MASTER_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
        Map<String,DBRecord>l_studentTypeMap=null;
        try
        {
        l_studentTypeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID,"INSTITUTE", "IVW_STUDENT_MASTER", session, dbSession);
        }  
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of IVW_STUDENT_MASTER_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_studentTypeMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<IVW_STUDENT_MASTER> convertDBtoReportObject(Map<String,DBRecord>p_studentTypeMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<IVW_STUDENT_MASTER>dataset=new ArrayList();
        try{
            
            
            dbg("inside IVW_STUDENT_MASTER_DATASET convertDBtoReportObject",session);
            
            if(p_studentTypeMap!=null&&!p_studentTypeMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_studentTypeMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_studentTypeRecords=recordIterator.next();
                    IVW_STUDENT_MASTER studentType=new IVW_STUDENT_MASTER();
                 
                    studentType.setSTUDENT_ID(l_studentTypeRecords.getRecord().get(0).trim());
                    studentType.setSTUDENT_NAME(l_studentTypeRecords.getRecord().get(1).trim());
                    studentType.setSTANDARD(l_studentTypeRecords.getRecord().get(2).trim());
                    studentType.setSECTION(l_studentTypeRecords.getRecord().get(3).trim());
                    studentType.setMAKER_ID(l_studentTypeRecords.getRecord().get(4).trim());
                    studentType.setCHECKER_ID(l_studentTypeRecords.getRecord().get(5).trim());
                    studentType.setMAKER_DATE_STAMP(l_studentTypeRecords.getRecord().get(6).trim());
                    studentType.setCHECKER_DATE_STAMP(l_studentTypeRecords.getRecord().get(7).trim());
                    studentType.setRECORD_STATUS(l_studentTypeRecords.getRecord().get(8).trim());
                    studentType.setAUTH_STATUS(l_studentTypeRecords.getRecord().get(9).trim());
                    studentType.setVERSION_NUMBER(l_studentTypeRecords.getRecord().get(10).trim());
                    studentType.setMAKER_REMARKS(l_studentTypeRecords.getRecord().get(11).trim());
                    studentType.setCHECKER_REMARKS(l_studentTypeRecords.getRecord().get(12).trim()); 
                    dataset.add(studentType);
                    
                }
            
            }
            
                else
            {
                IVW_STUDENT_MASTER service=new IVW_STUDENT_MASTER();
                 
                    service.setSTUDENT_ID(" ");
                    service.setSTUDENT_NAME(" ");
                    service.setSTANDARD(" ");
                    service.setSECTION(" ");
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
            
            
            
            
            
            
            dbg("end of IVW_STUDENT_MASTER_DATASET convertDBtoReportObject",session);
            
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
