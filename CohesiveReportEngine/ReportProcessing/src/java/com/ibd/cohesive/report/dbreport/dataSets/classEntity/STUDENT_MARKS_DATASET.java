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
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.STUDENT_MARKS;
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
 * @author IBDTechnologies
 */
public class STUDENT_MARKS_DATASET {
//    ArrayList<STUDENT_MARKS> dataset;
    
    
    public ArrayList<STUDENT_MARKS> getTableObject(String p_standard,String p_section,String p_instanceID,String p_exam,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside STUDENT_MARKS_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
        Map<String,DBRecord>l_marksMap=null;
        try
        {
        l_marksMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+p_exam,"CLASS", "STUDENT_MARKS", session, dbSession);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
       
        
         dbg("end of STUDENT_MARKS_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_marksMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<STUDENT_MARKS> convertDBtoReportObject(Map<String,DBRecord>p_marksMap,CohesiveSession session)throws DBProcessingException{
    
         ArrayList<STUDENT_MARKS>dataset=new ArrayList();
        try{
            
            
            dbg("inside STUDENT_MARKS_DATASET convertDBtoReportObject",session);
            
            if(p_marksMap!=null&&!p_marksMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_marksMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord markRecords=recordIterator.next();
                    STUDENT_MARKS classAssignment=new STUDENT_MARKS();
                    classAssignment.setSTANDARD(markRecords.getRecord().get(0).trim());
                    classAssignment.setSECTION(markRecords.getRecord().get(1).trim());
                    classAssignment.setEXAM(markRecords.getRecord().get(2).trim());
                    classAssignment.setSUBJECT_ID(markRecords.getRecord().get(3).trim());
                    classAssignment.setSTUDENT_ID(markRecords.getRecord().get(4).trim());
                    classAssignment.setGRADE(markRecords.getRecord().get(5).trim());
                    classAssignment.setMARK(markRecords.getRecord().get(6).trim());
                    classAssignment.setFEEDBACK(markRecords.getRecord().get(7).trim());
                    classAssignment.setVERSION_NUMBER(markRecords.getRecord().get(8).trim());
                    
                    dataset.add(classAssignment);
                    
                }
            
            }
                 else
            {
                STUDENT_MARKS service=new STUDENT_MARKS();
                 
                    service.setSTANDARD(" ");
                    service.setSECTION(" ");
                    service.setEXAM(" ");
                    service.setSUBJECT_ID(" ");
                    service.setSTUDENT_ID(" ");
                    service.setGRADE(" ");
                    service.setMARK(" ");
                    service.setFEEDBACK(" ");
                    service.setVERSION_NUMBER(" ");
                  
                                  
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            dbg("end of STUDENT_MARKS_DATASET convertDBtoReportObject",session);
            
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
