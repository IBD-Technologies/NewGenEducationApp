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
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_TEACHER_MASTER;
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
public class IVW_TEACHER_MASTER_DATASET {
//     ArrayList<IVW_TEACHER_MASTER> dataset;
    
    
    public ArrayList<IVW_TEACHER_MASTER> getTableObject(String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside IVW_TEACHER_MASTER_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
         Map<String,DBRecord>l_teacherTypeMap=null;
         try
         {
        l_teacherTypeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID,"INSTITUTE", "IVW_TEACHER_MASTER", session, dbSession);
         }
           catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of IVW_TEACHER_MASTER_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_teacherTypeMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<IVW_TEACHER_MASTER> convertDBtoReportObject(Map<String,DBRecord>p_teacherTypeMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<IVW_TEACHER_MASTER>dataset=new ArrayList();
        try{
            
            
            dbg("inside IVW_TEACHER_MASTER_DATASET convertDBtoReportObject",session);
            
            if(p_teacherTypeMap!=null&&!p_teacherTypeMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_teacherTypeMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_teacherTypeRecords=recordIterator.next();
                    IVW_TEACHER_MASTER teacherType=new IVW_TEACHER_MASTER();
                 
                    teacherType.setTEACHER_ID(l_teacherTypeRecords.getRecord().get(0).trim());
                    teacherType.setTEACHER_NAME(l_teacherTypeRecords.getRecord().get(1).trim());
                    teacherType.setSTANDARD(l_teacherTypeRecords.getRecord().get(2).trim());
                    teacherType.setSECTION(l_teacherTypeRecords.getRecord().get(3).trim());
                    teacherType.setTEACHER_SHORT_NAME(l_teacherTypeRecords.getRecord().get(4).trim());
                    teacherType.setMAKER_ID(l_teacherTypeRecords.getRecord().get(5).trim());
                    teacherType.setCHECKER_ID(l_teacherTypeRecords.getRecord().get(6).trim());
                    teacherType.setMAKER_DATE_STAMP(l_teacherTypeRecords.getRecord().get(7).trim());
                    teacherType.setCHECKER_DATE_STAMP(l_teacherTypeRecords.getRecord().get(8).trim());
                    teacherType.setRECORD_STATUS(l_teacherTypeRecords.getRecord().get(9).trim());
                    teacherType.setAUTH_STATUS(l_teacherTypeRecords.getRecord().get(10).trim());
                    teacherType.setVERSION_NUMBER(l_teacherTypeRecords.getRecord().get(11).trim());
                    teacherType.setMAKER_REMARKS(l_teacherTypeRecords.getRecord().get(12).trim());
                    teacherType.setCHECKER_REMARKS(l_teacherTypeRecords.getRecord().get(13).trim()); 
                    dataset.add(teacherType);
                    
                }
            
            }
                else
            {
                IVW_TEACHER_MASTER service=new IVW_TEACHER_MASTER();
                 
                    service.setTEACHER_ID(" ");
                    service.setTEACHER_NAME(" ");
                    service.setSTANDARD(" ");
                    service.setSECTION(" ");
                    service.setTEACHER_SHORT_NAME(" ");
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
            
            
            dbg("end of IVW_TEACHER_MASTER_DATASET convertDBtoReportObject",session);
            
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
