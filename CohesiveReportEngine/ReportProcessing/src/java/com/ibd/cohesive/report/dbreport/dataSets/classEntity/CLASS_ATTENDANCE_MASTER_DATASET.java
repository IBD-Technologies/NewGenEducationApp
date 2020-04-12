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
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_ATTENDANCE_MASTER;
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
public class CLASS_ATTENDANCE_MASTER_DATASET {
//    ArrayList<CLASS_ATTENDANCE_MASTER> dataset;
    
    
    public ArrayList<CLASS_ATTENDANCE_MASTER> getTableObject(String p_standard,String p_section,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside CLASS_ATTENDANCE_MASTER_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
        Map<String,DBRecord>l_classAttendanceMap=null;
        try
        {
        l_classAttendanceMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section,"CLASS", "CLASS_ATTENDANCE_MASTER", session, dbSession);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
        
         dbg("end of CLASS_ATTENDANCE_MASTER_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_classAttendanceMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<CLASS_ATTENDANCE_MASTER> convertDBtoReportObject(Map<String,DBRecord>p_attendanceMap,CohesiveSession session)throws DBProcessingException{
    
       ArrayList<CLASS_ATTENDANCE_MASTER> dataset=new ArrayList();
        try{
            
            
            dbg("inside CLASS_ATTENDANCE_MASTER_DATASET convertDBtoReportObject",session);
            
            if(p_attendanceMap!=null&&!p_attendanceMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_attendanceMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_attendanceRecords=recordIterator.next();
                    CLASS_ATTENDANCE_MASTER classAttendance=new CLASS_ATTENDANCE_MASTER();
                    classAttendance.setSTANDARD(l_attendanceRecords.getRecord().get(0).trim());
                    classAttendance.setSECTION(l_attendanceRecords.getRecord().get(1).trim());
                    classAttendance.setYEAR(l_attendanceRecords.getRecord().get(2).trim());
                    classAttendance.setMONTH(l_attendanceRecords.getRecord().get(3).trim());
                    classAttendance.setAUDIT_DETAILS(l_attendanceRecords.getRecord().get(4).trim());
                   
                    
                    dataset.add(classAttendance);
                    
                }
            
            }
                 else
            {
                CLASS_ATTENDANCE_MASTER service=new CLASS_ATTENDANCE_MASTER();
                 
                    service.setSTANDARD(" ");
                    service.setSECTION(" ");
                    service.setYEAR(" ");
                    service.setMONTH(" ");
                    service.setAUDIT_DETAILS(" ");
                    
                   
                                  
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            dbg("end of CLASS_ATTENDANCE_MASTER_DATASET convertDBtoReportObject",session);
            
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
