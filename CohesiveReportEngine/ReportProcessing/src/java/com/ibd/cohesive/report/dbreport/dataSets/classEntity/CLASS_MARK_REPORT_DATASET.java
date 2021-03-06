/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.classEntity;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_MARK_REPORT;
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
public class CLASS_MARK_REPORT_DATASET {
    
    public ArrayList<CLASS_MARK_REPORT> getTableObject(String userID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside CLASS_MARK_REPORT_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
         Map<String,DBRecord>l_classMarkEntryMap=null;
         try
         {
        l_classMarkEntryMap=readBuffer.readTable("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+userID+i_db_properties.getProperty("FOLDER_DELIMITER")+"REPORT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport","REPORT", "CLASS_MARK_REPORT", session, dbSession);
         }
           catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
        
        
         dbg("end of CLASS_MARK_REPORT_DATASET--->getTableObject",session);  
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
    
    
    
    
    private ArrayList<CLASS_MARK_REPORT> convertDBtoReportObject(Map<String,DBRecord>p_markMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<CLASS_MARK_REPORT>dataset=new ArrayList();
        try{
            
            
            dbg("inside CLASS_MARK_REPORT_DATASET convertDBtoReportObject",session);
            
            if(p_markMap!=null&&!p_markMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_markMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_markRecords=recordIterator.next();
                    CLASS_MARK_REPORT classMarkEntry=new CLASS_MARK_REPORT();
                    classMarkEntry.setStandard(l_markRecords.getRecord().get(0).trim());
                    classMarkEntry.setSection(l_markRecords.getRecord().get(1).trim());
                    classMarkEntry.setSubjectID(l_markRecords.getRecord().get(2).trim());
                    classMarkEntry.setExam(l_markRecords.getRecord().get(3).trim());
                    classMarkEntry.setAveragreMark(l_markRecords.getRecord().get(4).trim());
                    classMarkEntry.setTopMark(l_markRecords.getRecord().get(5).trim());
                    classMarkEntry.setLowMark(l_markRecords.getRecord().get(6).trim());
                   
                    
                    
                    dataset.add(classMarkEntry);
                    
                }
            
            }
            
                 else
            {
                CLASS_MARK_REPORT service=new CLASS_MARK_REPORT();
                 
                    service.setStandard(" ");
                    service.setSection(" ");
                    service.setSubjectID(" ");
                    service.setExam(" ");
                    service.setAveragreMark(" ");
                    service.setTopMark(" ");
                    service.setLowMark(" ");
                   
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            dbg("end of CLASS_MARK_REPORT_DATASET convertDBtoReportObject",session);
            
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
