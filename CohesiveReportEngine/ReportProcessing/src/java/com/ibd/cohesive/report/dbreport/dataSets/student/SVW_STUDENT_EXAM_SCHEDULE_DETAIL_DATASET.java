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
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_EXAM_SCHEDULE_DETAIL;
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
public class SVW_STUDENT_EXAM_SCHEDULE_DETAIL_DATASET {
//    ArrayList<SVW_STUDENT_EXAM_SCHEDULE_DETAIL> dataset;
    
    
    public ArrayList<SVW_STUDENT_EXAM_SCHEDULE_DETAIL> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside SSVW_STUDENT_EXAM_SCHEDULE_DETAIL_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
         boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        Map<String,DBRecord>l_studentExamScheduleDetailMap=null;
        try
        {
        
        l_studentExamScheduleDetailMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID,"STUDENT", "SVW_STUDENT_EXAM_SCHEDULE_DETAIL", session, dbSession,ismaxVersionRequired);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of SVW_STUDENT_EXAM_SCHEDULE_DETAIL_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_studentExamScheduleDetailMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<SVW_STUDENT_EXAM_SCHEDULE_DETAIL> convertDBtoReportObject(Map<String,DBRecord>p_eaxmScheduleDetailMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<SVW_STUDENT_EXAM_SCHEDULE_DETAIL>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_STUDENT_EXAM_SCHEDULE_DETAIL convertDBtoReportObject",session);
            
            if(p_eaxmScheduleDetailMap!=null&&!p_eaxmScheduleDetailMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_eaxmScheduleDetailMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_examScheduleRecords=recordIterator.next();
                    SVW_STUDENT_EXAM_SCHEDULE_DETAIL examSchedule=new SVW_STUDENT_EXAM_SCHEDULE_DETAIL();
                    examSchedule.setSTUDENT_ID(l_examScheduleRecords.getRecord().get(0).trim());
                    examSchedule.setEXAM(l_examScheduleRecords.getRecord().get(1).trim());
                    examSchedule.setSUBJECT_ID(l_examScheduleRecords.getRecord().get(2).trim());
                    examSchedule.setDATE(l_examScheduleRecords.getRecord().get(3).trim());
                    examSchedule.setHALL(l_examScheduleRecords.getRecord().get(4).trim());
                    examSchedule.setVERSION_NUMBER(l_examScheduleRecords.getRecord().get(5).trim());
                    examSchedule.setSTART_TIME_HOUR(l_examScheduleRecords.getRecord().get(6).trim());
                    examSchedule.setSTART_TIME_MIN(l_examScheduleRecords.getRecord().get(7).trim());
                    examSchedule.setEND_TIME_HOUR(l_examScheduleRecords.getRecord().get(8).trim());
                    examSchedule.setEND_TIME_MIN(l_examScheduleRecords.getRecord().get(9).trim());
                    
                    dbg("studentID"+examSchedule.getSTUDENT_ID(),session);
                    dbg("exam"+examSchedule.getEXAM() ,session);
                    dbg("subjectID"+examSchedule.getSUBJECT_ID(),session);
                    dbg("date"+examSchedule.getDATE(),session);
                    dbg("hall"+examSchedule.getHALL(),session);
                    dbg("versionNumber"+examSchedule.getVERSION_NUMBER(),session);
                    
                    dataset.add(examSchedule);
                    
                }
            
            }
            
            
                else
            {
                SVW_STUDENT_EXAM_SCHEDULE_DETAIL service=new SVW_STUDENT_EXAM_SCHEDULE_DETAIL();
                 
                    service.setSTUDENT_ID(" ");
                    service.setEXAM(" ");
                    service.setSUBJECT_ID(" ");
                    service.setDATE(" ");
                    service.setHALL(" ");
                    service.setVERSION_NUMBER(" ");
                    service.setSTART_TIME_HOUR(" ");
                    service.setSTART_TIME_MIN(" ");
                    service.setEND_TIME_HOUR(" ");
                    service.setEND_TIME_MIN(" ");
                 
                    
 dataset.add(service);
                
            }
            
            dbg("end of SVW_STUDENT_EXAM_SCHEDULE_DETAIL convertDBtoReportObject",session);
            
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
