/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.classEntity;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.app.CONTRACT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.STUDENT_SKILLS;
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
public class STUDENT_SKILLS_DATASET {
    
    public ArrayList<STUDENT_SKILLS> getTableObject(String p_standard,String p_section,String p_instanceID,String p_exam,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside STUDENT_SKILLS_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
//        BusinessService bs=appInject.getBusinessService(session);
//        ArrayList<String>completedExams=bs.getCompletedExams(p_instanceID, p_standard, p_section, session, dbSession, appInject);
//        
        
        Map<String,DBRecord>l_marksMap=null;
        try
        {
        l_marksMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+p_exam,"CLASS", "STUDENT_SKILLS", session, dbSession);
        } 
         catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of STUDENT_SKILLS_DATASET--->getTableObject",session);  
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
    
    
    
    
    private ArrayList<STUDENT_SKILLS> convertDBtoReportObject(Map<String,DBRecord>p_marksMap,CohesiveSession session)throws DBProcessingException{
    
         ArrayList<STUDENT_SKILLS>dataset=new ArrayList();
        try{
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
//             DBRecord l_examScheduleRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS","CLASS_EXAM_SCHEDULE_MASTER", l_pkey, session, dbSession);
//                 String masterVersion=l_examScheduleRecord.getRecord().get(9).trim();
//                 
//                 Map<String,DBRecord>l_examScheduleMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"ExamSchedules","CLASS","CLASS_EXAM_SCHEDULE_DETAIL", session, dbSession);
//
//                 int versionIndex=bs.getVersionIndexOfTheTable("CLASS_EXAM_SCHEDULE_DETAIL", "CLASS", session, dbSession, inject);
//                 
//                 
//                 Map<String,List<DBRecord>>examSubjectWiseList= l_examScheduleMap.values().stream().filter(rec->rec.getRecord().get(2).trim().equals(exam)&&rec.getRecord().get(versionIndex).equals(masterVersion)).collect(Collectors.groupingBy(rec->rec.getRecord().get(3).trim()));
            
            
            dbg("inside STUDENT_SKILLS_DATASET convertDBtoReportObject",session);
            
            if(p_marksMap!=null&&!p_marksMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_marksMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord markRecords=recordIterator.next();
                    STUDENT_SKILLS classAssignment=new STUDENT_SKILLS();
                    classAssignment.setSTANDARD(markRecords.getRecord().get(0).trim());
                    classAssignment.setSECTION(markRecords.getRecord().get(1).trim());
                    classAssignment.setEXAM(markRecords.getRecord().get(2).trim());
                    classAssignment.setSKILL_ID(markRecords.getRecord().get(3).trim());
                    classAssignment.setSTUDENT_ID(markRecords.getRecord().get(4).trim());
                    classAssignment.setGRADE(markRecords.getRecord().get(5).trim());
                    classAssignment.setFEEDBACK(markRecords.getRecord().get(6).trim());
                    classAssignment.setVERSION_NUMBER(markRecords.getRecord().get(7).trim());
                    
                    
                    dataset.add(classAssignment);
                    
                }
            
            }
            
                 else
            {
                STUDENT_SKILLS service=new STUDENT_SKILLS();
                 
                    service.setSTANDARD(" ");
                    service.setSECTION(" ");
                    service.setEXAM(" ");
                    service.setSKILL_ID(" ");
                    service.setSTUDENT_ID(" ");
                    service.setGRADE(" ");
                    service.setFEEDBACK(" ");
                    service.setVERSION_NUMBER(" ");
                    
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            dbg("end of STUDENT_SKILLS_DATASET convertDBtoReportObject",session);
            
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
