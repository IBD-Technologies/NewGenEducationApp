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
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_ATTENDANCE_REPORT;
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
 * @author ibdtech
 */
public class CLASS_ATTENDANCE_REPORT_DATASET {
    
     public ArrayList<CLASS_ATTENDANCE_REPORT> getTableObject(String p_standard,String p_section,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside CLASS_ATTENDANCE_REPORT_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
        Map<String,DBRecord>l_classMarkEntryMap=null;
        try
        {
       l_classMarkEntryMap=readBuffer.readTable("REPORT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section,"CLASS", "CLASS_ATTENDANCE_REPORT", session, dbSession);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
        
        
         dbg("end of CLASS_ATTENDANCE_REPORT_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_classMarkEntryMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<CLASS_ATTENDANCE_REPORT> convertDBtoReportObject(Map<String,DBRecord>p_markMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<CLASS_ATTENDANCE_REPORT>dataset=new ArrayList();
        try{
            
            
            dbg("inside CLASS_ATTENDANCE_REPORT_DATASET convertDBtoReportObject",session);
            
            if(p_markMap!=null&&!p_markMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_markMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_markRecords=recordIterator.next();
                    CLASS_ATTENDANCE_REPORT classMarkEntry=new CLASS_ATTENDANCE_REPORT();
                    classMarkEntry.setSTANDARD(l_markRecords.getRecord().get(0).trim());
                    classMarkEntry.setSECTION(l_markRecords.getRecord().get(1).trim());
                    classMarkEntry.setYEAR(l_markRecords.getRecord().get(2).trim());
                    classMarkEntry.setMONTH(l_markRecords.getRecord().get(3).trim());
                    classMarkEntry.setPRESENT_AVERAGE(l_markRecords.getRecord().get(4).trim());
                    classMarkEntry.setABSENT_AVERAGE(l_markRecords.getRecord().get(5).trim());
                    classMarkEntry.setLEAVE_AVERAGE(l_markRecords.getRecord().get(6).trim());
                    classMarkEntry.setPRESENT_PERCENTAGE(l_markRecords.getRecord().get(7).trim());
                    classMarkEntry.setABSENT_PERCENTAGE(l_markRecords.getRecord().get(8).trim());
                    classMarkEntry.setLEAVE_PERCENTAGE(l_markRecords.getRecord().get(9).trim());
                    
                    
                    dataset.add(classMarkEntry);
                    
                }
            
            }
            
            
            
                 else
            {
                CLASS_ATTENDANCE_REPORT service=new CLASS_ATTENDANCE_REPORT();
                 
                    service.setSTANDARD(" ");
                    service.setSECTION(" ");
                    service.setYEAR(" ");
                    service.setMONTH(" ");
                    service.setPRESENT_AVERAGE(" ");
                    service.setABSENT_AVERAGE(" ");
                    service.setLEAVE_AVERAGE(" ");
                    service.setPRESENT_PERCENTAGE(" ");
                    service.setABSENT_PERCENTAGE(" ");
                    service.setLEAVE_PERCENTAGE(" ");
                    
                                  
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            
            
            
            dbg("end of CLASS_ATTENDANCE_REPORT_DATASET convertDBtoReportObject",session);
            
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
