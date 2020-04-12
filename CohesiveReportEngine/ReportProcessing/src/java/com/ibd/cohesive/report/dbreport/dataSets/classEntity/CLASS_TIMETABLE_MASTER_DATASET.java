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
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_TIMETABLE_MASTER;
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
public class CLASS_TIMETABLE_MASTER_DATASET {
//    ArrayList<CLASS_TIMETABLE_MASTER> dataset;
    
    
    public ArrayList<CLASS_TIMETABLE_MASTER> getTableObject(String p_standard,String p_section,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside CLASS_TIMETABLE_MASTER_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
        Map<String,DBRecord>l_classTimeTableMap=null;
        try
        {
        l_classTimeTableMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS", "CLASS_TIMETABLE_MASTER", session, dbSession);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
        
         dbg("end of CLASS_TIMETABLE_MASTER_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_classTimeTableMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<CLASS_TIMETABLE_MASTER> convertDBtoReportObject(Map<String,DBRecord>p_timeTableMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<CLASS_TIMETABLE_MASTER>dataset=new ArrayList();
        try{
            
            
            dbg("inside CLASS_TIMETABLE_MASTER_DATASET convertDBtoReportObject",session);
            
            if(p_timeTableMap!=null&&!p_timeTableMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_timeTableMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord timeTableRecords=recordIterator.next();
                    CLASS_TIMETABLE_MASTER timeTable=new CLASS_TIMETABLE_MASTER();
                    timeTable.setSTANDARD(timeTableRecords.getRecord().get(0).trim());
                    timeTable.setSECTION(timeTableRecords.getRecord().get(1).trim());
                    timeTable.setMAKER_ID(timeTableRecords.getRecord().get(2).trim());
                    timeTable.setCHECKER_ID(timeTableRecords.getRecord().get(3).trim());
                    timeTable.setMAKER_DATE_STAMP(timeTableRecords.getRecord().get(4).trim());
                    timeTable.setCHECKER_DATE_STAMP(timeTableRecords.getRecord().get(5).trim());
                    timeTable.setRECORD_STATUS(timeTableRecords.getRecord().get(6).trim());
                    timeTable.setAUTH_STATUS(timeTableRecords.getRecord().get(7).trim());
                    timeTable.setVERSION_NUMBER(timeTableRecords.getRecord().get(8).trim());
                    timeTable.setMAKER_REMARKS(timeTableRecords.getRecord().get(9).trim());
                    timeTable.setCHECKER_REMARKS(timeTableRecords.getRecord().get(10).trim()); 
                    
                    dataset.add(timeTable);
                    
                }
            
            }
                 else
            {
                CLASS_TIMETABLE_MASTER service=new CLASS_TIMETABLE_MASTER();
                 
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
            
            dbg("end of CLASS_TIMETABLE_MASTER_DATASET convertDBtoReportObject",session);
            
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
