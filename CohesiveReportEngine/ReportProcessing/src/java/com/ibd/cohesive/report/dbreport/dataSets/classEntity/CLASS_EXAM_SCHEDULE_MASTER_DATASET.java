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
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_ASSIGNMENT;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_EXAM_SCHEDULE_MASTER;
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
public class CLASS_EXAM_SCHEDULE_MASTER_DATASET {
//    ArrayList<CLASS_EXAM_SCHEDULE_MASTER> dataset;
    
    
    public ArrayList<CLASS_EXAM_SCHEDULE_MASTER> getTableObject(String p_standard,String p_section,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside CLASS_EXAM_SCHEDULE_MASTER_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
        Map<String,DBRecord>l_examScheduleMap=null;
        try
        {
        l_examScheduleMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS", "CLASS_EXAM_SCHEDULE_MASTER", session, dbSession);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
        
        
         dbg("end of CLASS_EXAM_SCHEDULE_MASTER_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_examScheduleMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<CLASS_EXAM_SCHEDULE_MASTER> convertDBtoReportObject(Map<String,DBRecord>p_examScheduleMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<CLASS_EXAM_SCHEDULE_MASTER>dataset=new ArrayList();
        try{
            
            
            dbg("inside CLASS_EXAM_SCHEDULE_MASTER_DATASET convertDBtoReportObject",session);
            
            if(p_examScheduleMap!=null&&!p_examScheduleMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_examScheduleMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_examScheduleRecords=recordIterator.next();
                    CLASS_EXAM_SCHEDULE_MASTER examSchedule=new CLASS_EXAM_SCHEDULE_MASTER();
                    examSchedule.setSTANDARD(l_examScheduleRecords.getRecord().get(0).trim());
                    examSchedule.setSECTION(l_examScheduleRecords.getRecord().get(1).trim());
                    examSchedule.setEXAM(l_examScheduleRecords.getRecord().get(2).trim());
                    examSchedule.setMAKER_ID(l_examScheduleRecords.getRecord().get(3).trim());
                    examSchedule.setCHECKER_ID(l_examScheduleRecords.getRecord().get(4).trim());
                    examSchedule.setMAKER_DATE_STAMP(l_examScheduleRecords.getRecord().get(5).trim());
                    examSchedule.setCHECKER_DATE_STAMP(l_examScheduleRecords.getRecord().get(6).trim());
                    examSchedule.setRECORD_STATUS(l_examScheduleRecords.getRecord().get(7).trim());
                    examSchedule.setAUTH_STATUS(l_examScheduleRecords.getRecord().get(8).trim());
                    examSchedule.setVERSION_NUMBER(l_examScheduleRecords.getRecord().get(9).trim());
                    examSchedule.setMAKER_REMARKS(l_examScheduleRecords.getRecord().get(10).trim());
                    examSchedule.setCHECKER_REMARKS(l_examScheduleRecords.getRecord().get(11).trim()); 
                    
                    dataset.add(examSchedule);
                    
                }
            
            }
            
                 else
            {
                CLASS_EXAM_SCHEDULE_MASTER service=new CLASS_EXAM_SCHEDULE_MASTER();
                 
                    service.setSTANDARD(" ");
                    service.setSECTION(" ");
                    service.setEXAM(" ");
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
            
            
            dbg("end of CLASS_EXAM_SCHEDULE_MASTER_DATASET convertDBtoReportObject",session);
            
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
