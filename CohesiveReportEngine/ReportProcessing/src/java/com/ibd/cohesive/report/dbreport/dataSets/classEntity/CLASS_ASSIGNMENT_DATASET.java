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
public class CLASS_ASSIGNMENT_DATASET {
//     ArrayList<CLASS_ASSIGNMENT> dataset;
    
    
    public ArrayList<CLASS_ASSIGNMENT> getTableObject(String p_standard,String p_section,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside CLASS_ASSIGNMENT_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
        Map<String,DBRecord>l_classAssignmentMap=null;
        try
        {
        
        l_classAssignmentMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section,"CLASS", "CLASS_ASSIGNMENT", session, dbSession);
        } 
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of CLASS_ASSIGNMENT_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_classAssignmentMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<CLASS_ASSIGNMENT> convertDBtoReportObject(Map<String,DBRecord>p_assignmentMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<CLASS_ASSIGNMENT>dataset=new ArrayList();
        try{
            
            
            dbg("inside CLASS_ASSIGNMENT_DATASET convertDBtoReportObject",session);
            
            if(p_assignmentMap!=null&&!p_assignmentMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_assignmentMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_assignmentRecords=recordIterator.next();
                    CLASS_ASSIGNMENT classAssignment=new CLASS_ASSIGNMENT();
                    classAssignment.setSTANDARD(l_assignmentRecords.getRecord().get(0).trim());
                    classAssignment.setSECTION(l_assignmentRecords.getRecord().get(1).trim());
                    classAssignment.setSUBJECT_ID(l_assignmentRecords.getRecord().get(2).trim());
                    classAssignment.setASSIGNMENT_ID(l_assignmentRecords.getRecord().get(3).trim());
                    classAssignment.setASSIGNMENT_TOPIC(l_assignmentRecords.getRecord().get(4).trim());
                    classAssignment.setDUE_DATE(l_assignmentRecords.getRecord().get(5).trim());
                    classAssignment.setTEACHER_COMMENT(l_assignmentRecords.getRecord().get(6).trim());
                    classAssignment.setCONTENT_TYPE(l_assignmentRecords.getRecord().get(7).trim());
                    classAssignment.setCONTENT_PATH(l_assignmentRecords.getRecord().get(8).trim());
                    classAssignment.setMAKER_ID(l_assignmentRecords.getRecord().get(9).trim());
                    classAssignment.setCHECKER_ID(l_assignmentRecords.getRecord().get(10).trim());
                    classAssignment.setMAKER_DATE_STAMP(l_assignmentRecords.getRecord().get(11).trim());
                    classAssignment.setCHECKER_DATE_STAMP(l_assignmentRecords.getRecord().get(12).trim());
                    classAssignment.setRECORD_STATUS(l_assignmentRecords.getRecord().get(13).trim());
                    classAssignment.setAUTH_STATUS(l_assignmentRecords.getRecord().get(14).trim());
                    classAssignment.setVERSION_NUMBER(l_assignmentRecords.getRecord().get(15).trim());
                    classAssignment.setMAKER_REMARKS(l_assignmentRecords.getRecord().get(16).trim());
                    classAssignment.setCHECKER_REMARKS(l_assignmentRecords.getRecord().get(17).trim()); 
                    
                    dataset.add(classAssignment);
                    
                }
            
            }
            
                 else
            {
                CLASS_ASSIGNMENT service=new CLASS_ASSIGNMENT();
                 
                    service.setSTANDARD(" ");
                    service.setSECTION(" ");
                    service.setSUBJECT_ID(" ");
                    service.setASSIGNMENT_ID(" ");
                    service.setASSIGNMENT_TOPIC(" ");
                    service.setDUE_DATE(" ");
                    service.setTEACHER_COMMENT(" ");
                    service.setCONTENT_TYPE(" ");
                    service.setCONTENT_PATH(" ");
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
            
            
            dbg("end of CLASS_ASSIGNMENT_DATASET convertDBtoReportObject",session);
            
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
