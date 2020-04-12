/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.student;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.app.CONTRACT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_EXAM_SCHEDULE_MASTER;
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
public class SVW_STUDENT_EXAM_SCHEDULE_MASTER_DATASET {
//  ArrayList<SVW_STUDENT_EXAM_SCHEDULE_MASTER> dataset;
    
    
    public ArrayList<SVW_STUDENT_EXAM_SCHEDULE_MASTER> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside SVW_STUDENT_EXAM_SCHEDULE_MASTER_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
         boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
          Map<String,DBRecord>l_studentExamScheduleMasterMap=null;
          try
          {
        
       l_studentExamScheduleMasterMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID,"STUDENT", "SVW_STUDENT_EXAM_SCHEDULE_MASTER", session, dbSession,ismaxVersionRequired);
          }
            catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of SVW_STUDENT_EXAM_SCHEDULE_MASTER_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_studentExamScheduleMasterMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<SVW_STUDENT_EXAM_SCHEDULE_MASTER> convertDBtoReportObject(Map<String,DBRecord>p_examScheduleMasterMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<SVW_STUDENT_EXAM_SCHEDULE_MASTER>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_STUDENT_EXAM_SCHEDULE_MASTER convertDBtoReportObject",session);
            
            if(p_examScheduleMasterMap!=null&&!p_examScheduleMasterMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_examScheduleMasterMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_examScheduleRecords=recordIterator.next();
                    SVW_STUDENT_EXAM_SCHEDULE_MASTER examSchedule=new SVW_STUDENT_EXAM_SCHEDULE_MASTER();
                    examSchedule.setSTUDENT_ID(l_examScheduleRecords.getRecord().get(0).trim());
                    examSchedule.setEXAM(l_examScheduleRecords.getRecord().get(1).trim());
                    examSchedule.setMAKER_ID(l_examScheduleRecords.getRecord().get(2).trim());
                    examSchedule.setCHECKER_ID(l_examScheduleRecords.getRecord().get(3).trim());
                    examSchedule.setMAKER_DATE_STAMP(l_examScheduleRecords.getRecord().get(4).trim());
                    examSchedule.setCHECKER_DATE_STAMP(l_examScheduleRecords.getRecord().get(5).trim());
                    examSchedule.setRECORD_STATUS(l_examScheduleRecords.getRecord().get(6).trim());
                    examSchedule.setAUTH_STATUS(l_examScheduleRecords.getRecord().get(7).trim());
                    examSchedule.setVERSION_NUMBER(l_examScheduleRecords.getRecord().get(8).trim());
                    examSchedule.setMAKER_REMARKS(l_examScheduleRecords.getRecord().get(9).trim());
                    examSchedule.setCHECKER_REMARKS(l_examScheduleRecords.getRecord().get(10).trim()); 
                    
                    dbg("studentID"+examSchedule.getSTUDENT_ID(),session);
                    dbg("exam"+examSchedule.getEXAM() ,session);
                    dbg("makerID"+examSchedule.getMAKER_ID(),session);
                    dbg("checkerID"+examSchedule.getCHECKER_ID() ,session);
                    dbg("makerDateStamp"+examSchedule.getMAKER_DATE_STAMP() ,session);
                    dbg("checkerDateStamp"+examSchedule.getCHECKER_DATE_STAMP() ,session);
                    dbg("recordStatus"+ examSchedule.getRECORD_STATUS(),session);
                    dbg("authStatus"+examSchedule.getAUTH_STATUS(),session);
                    dbg("versionNumber"+examSchedule.getVERSION_NUMBER(),session);
                    dbg("makerRemarks"+examSchedule.getMAKER_REMARKS(),session);
                    dbg("checkerRemarks"+examSchedule.getCHECKER_REMARKS(),session);
                    
                    dataset.add(examSchedule);
                    
                }
            
            }
            
                else
            {
                SVW_STUDENT_EXAM_SCHEDULE_MASTER service=new SVW_STUDENT_EXAM_SCHEDULE_MASTER();
                 
                    service.setSTUDENT_ID(" ");
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
            
            
            
            
            
            dbg("end of SVW_STUDENT_EXAM_SCHEDULE_MASTER convertDBtoReportObject",session);
            
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
