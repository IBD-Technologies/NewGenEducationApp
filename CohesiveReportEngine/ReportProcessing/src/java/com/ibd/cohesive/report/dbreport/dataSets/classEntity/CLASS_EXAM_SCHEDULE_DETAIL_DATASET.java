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
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_EXAM_SCHEDULE_DETAIL;
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
public class CLASS_EXAM_SCHEDULE_DETAIL_DATASET {
//    ArrayList<CLASS_EXAM_SCHEDULE_DETAIL> dataset;
    
    
    public ArrayList<CLASS_EXAM_SCHEDULE_DETAIL> getTableObject(String p_standard,String p_section,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside CLASS_EXAM_SCHEDULE_DETAIL_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
         Map<String,DBRecord>l_classExamScheduleMap=null;
         try
         {
        l_classExamScheduleMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS", "CLASS_EXAM_SCHEDULE_DETAIL", session, dbSession);
         }
           catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
        
         dbg("end of CLASS_EXAM_SCHEDULE_DETAIL_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_classExamScheduleMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<CLASS_EXAM_SCHEDULE_DETAIL> convertDBtoReportObject(Map<String,DBRecord>p_examScheduleMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<CLASS_EXAM_SCHEDULE_DETAIL>dataset=new ArrayList();
        try{
            
            
            dbg("inside CLASS_EXAM_SCHEDULE_DETAIL_DATASET convertDBtoReportObject",session);
            
            if(p_examScheduleMap!=null&&!p_examScheduleMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_examScheduleMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_examScheduleRecords=recordIterator.next();
                    CLASS_EXAM_SCHEDULE_DETAIL classExamSchedule=new CLASS_EXAM_SCHEDULE_DETAIL();
                    classExamSchedule.setSTANDARD(l_examScheduleRecords.getRecord().get(0).trim());
                    classExamSchedule.setSECTION(l_examScheduleRecords.getRecord().get(1).trim());
                    classExamSchedule.setEXAM(l_examScheduleRecords.getRecord().get(2).trim());
                    classExamSchedule.setSUBJECT_ID(l_examScheduleRecords.getRecord().get(3).trim());
                    classExamSchedule.setDATE(l_examScheduleRecords.getRecord().get(4).trim());
                    classExamSchedule.setTIME(l_examScheduleRecords.getRecord().get(5).trim());
                    classExamSchedule.setHALL(l_examScheduleRecords.getRecord().get(6).trim());
                    classExamSchedule.setVERSION_NUMBER(l_examScheduleRecords.getRecord().get(7).trim());

                    dataset.add(classExamSchedule);
                    
                }
            
            }
            
                 else
            {
                CLASS_EXAM_SCHEDULE_DETAIL service=new CLASS_EXAM_SCHEDULE_DETAIL();
                 
                    service.setSTANDARD(" ");
                    service.setSECTION(" ");
                    service.setEXAM(" ");
                    service.setSUBJECT_ID(" ");
                    service.setDATE(" ");
                    service.setTIME(" ");
                    service.setHALL(" ");
                    service.setVERSION_NUMBER(" ");
                    
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            
            dbg("end of CLASS_EXAM_SCHEDULE_DETAIL_DATASET convertDBtoReportObject",session);
            
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
