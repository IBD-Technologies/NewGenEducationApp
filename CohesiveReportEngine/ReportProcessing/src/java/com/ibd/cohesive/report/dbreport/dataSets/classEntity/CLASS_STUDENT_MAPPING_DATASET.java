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
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_STUDENT_MAPPING;
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
public class CLASS_STUDENT_MAPPING_DATASET {
//    ArrayList<CLASS_STUDENT_MAPPING> dataset;
    
    
    public ArrayList<CLASS_STUDENT_MAPPING> getTableObject(String p_standard,String p_section,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside CLASS_STUDENT_MAPPING_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
        Map<String,DBRecord>l_classStudentMap=null;
        try
        {
        l_classStudentMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section,"CLASS", "CLASS_STUDENT_MAPPING", session, dbSession);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
        
         dbg("end of CLASS_STUDENT_MAPPING_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_classStudentMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<CLASS_STUDENT_MAPPING> convertDBtoReportObject(Map<String,DBRecord>p_classStudentMap,CohesiveSession session)throws DBProcessingException{
    
       ArrayList<CLASS_STUDENT_MAPPING> dataset=new ArrayList();
        try{
            
            
            dbg("inside CLASS_STUDENT_MAPPING_DATASET convertDBtoReportObject",session);
            
            if(p_classStudentMap!=null&&!p_classStudentMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_classStudentMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_mappingRecords=recordIterator.next();
                    CLASS_STUDENT_MAPPING studentMapping=new CLASS_STUDENT_MAPPING();
                    studentMapping.setSTANDARD(l_mappingRecords.getRecord().get(0).trim());
                    studentMapping.setSECTION(l_mappingRecords.getRecord().get(1).trim());
                    studentMapping.setSTUDENT_ID(l_mappingRecords.getRecord().get(2).trim());
                    studentMapping.setMAKER_ID(l_mappingRecords.getRecord().get(3).trim());
                    studentMapping.setCHECKER_ID(l_mappingRecords.getRecord().get(4).trim());
                    studentMapping.setMAKER_DATE_STAMP(l_mappingRecords.getRecord().get(5).trim());
                    studentMapping.setCHECKER_DATE_STAMP(l_mappingRecords.getRecord().get(6).trim());
                    studentMapping.setRECORD_STATUS(l_mappingRecords.getRecord().get(7).trim());
                    studentMapping.setAUTH_STATUS(l_mappingRecords.getRecord().get(8).trim());
                    studentMapping.setVERSION_NUMBER(l_mappingRecords.getRecord().get(9).trim());
                    studentMapping.setMAKER_REMARKS(l_mappingRecords.getRecord().get(10).trim());
                    studentMapping.setCHECKER_REMARKS(l_mappingRecords.getRecord().get(11).trim()); 
                    
                    dataset.add(studentMapping);
                    
                }
            
            }
            
            
            
                 else
            {
                CLASS_STUDENT_MAPPING service=new CLASS_STUDENT_MAPPING();
                 
                    service.setSTANDARD(" ");
                    service.setSECTION(" ");
                    service.setSTUDENT_ID(" ");
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
            
            
            
            dbg("end of CLASS_STUDENT_MAPPING_DATASET convertDBtoReportObject",session);
            
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
