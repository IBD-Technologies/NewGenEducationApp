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
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_TIMETABLE_DETAIL;
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
public class CLASS_TIMETABLE_DETAIL_DATASET {
//    ArrayList<CLASS_TIMETABLE_DETAIL> dataset;
    
    
    public ArrayList<CLASS_TIMETABLE_DETAIL> getTableObject(String p_standard,String p_section,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside CLASS_TIMETABLE_DETAIL_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
        Map<String,DBRecord>l_classTimeTabletMap=null;
        try
        {
        l_classTimeTabletMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS", "CLASS_TIMETABLE_DETAIL", session, dbSession);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of CLASS_TIMETABLE_DETAIL_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_classTimeTabletMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<CLASS_TIMETABLE_DETAIL> convertDBtoReportObject(Map<String,DBRecord>p_timeTableMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<CLASS_TIMETABLE_DETAIL>dataset=new ArrayList();
        try{
            
            
            dbg("inside CLASS_TIMETABLE_DETAIL_DATASET convertDBtoReportObject",session);
            
            if(p_timeTableMap!=null&&!p_timeTableMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_timeTableMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord timeTableRecords=recordIterator.next();
                    CLASS_TIMETABLE_DETAIL timeTable=new CLASS_TIMETABLE_DETAIL();
                    timeTable.setSTANDARD(timeTableRecords.getRecord().get(0).trim());
                    timeTable.setSECTION(timeTableRecords.getRecord().get(1).trim());
                    timeTable.setDAY(timeTableRecords.getRecord().get(2).trim());
                    timeTable.setPERIOD_NO(timeTableRecords.getRecord().get(3).trim());
                    timeTable.setSUBJECT_ID(timeTableRecords.getRecord().get(4).trim());
                    timeTable.setTEACHER_SHORT_NAME(timeTableRecords.getRecord().get(5).trim());
                    timeTable.setVERSION_NUMBER(timeTableRecords.getRecord().get(6).trim());
                    timeTable.setDAY_NUMBER(timeTableRecords.getRecord().get(7).trim());
                    timeTable.setTEACHER_ID(timeTableRecords.getRecord().get(8).trim());
                    dataset.add(timeTable);
                    
                }
            
            }
            
                 else
            {
                CLASS_TIMETABLE_DETAIL service=new CLASS_TIMETABLE_DETAIL();
                 
                    service.setSTANDARD(" ");
                    service.setSECTION(" ");
                    service.setDAY(" ");
                    service.setPERIOD_NO(" ");
                    service.setSUBJECT_ID(" ");
                    service.setTEACHER_SHORT_NAME(" ");
                    service.setVERSION_NUMBER(" ");
                    service.setDAY_NUMBER(" ");
                    service.setTEACHER_ID(" ");
                   
                                  
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            
            dbg("end of CLASS_TIMETABLE_DETAIL_DATASET convertDBtoReportObject",session);
            
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
