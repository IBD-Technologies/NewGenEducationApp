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
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_MARKS;
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
public class SVW_STUDENT_MARKS_DATASET {
//     ArrayList<SVW_STUDENT_MARKS> dataset;
    
    
    public ArrayList<SVW_STUDENT_MARKS> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside SVW_STUDENT_MARKS_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        Map<String,DBRecord>l_studentMarksMap=null;
         boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        try
        {
        l_studentMarksMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID,"STUDENT", "SVW_STUDENT_MARKS", session, dbSession,ismaxVersionRequired);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
        
         dbg("end of SVW_STUDENT_MARKS_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_studentMarksMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<SVW_STUDENT_MARKS> convertDBtoReportObject(Map<String,DBRecord>p_studentMarksMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<SVW_STUDENT_MARKS>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_STUDENT_MARKS convertDBtoReportObject",session);
            
            if(p_studentMarksMap!=null&&!p_studentMarksMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_studentMarksMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_markRecords=recordIterator.next();
                    SVW_STUDENT_MARKS studentMarks=new SVW_STUDENT_MARKS();
                    studentMarks.setSTUDENT_ID(l_markRecords.getRecord().get(0).trim());
                    studentMarks.setEXAM(l_markRecords.getRecord().get(1).trim());
                    studentMarks.setSUBJECT_ID(l_markRecords.getRecord().get(2).trim());
                    studentMarks.setGRADE(l_markRecords.getRecord().get(3).trim());
                    studentMarks.setMARK(l_markRecords.getRecord().get(4).trim());
                    studentMarks.setTEACHER_FEEDBACK(l_markRecords.getRecord().get(5).trim());
                    studentMarks.setVERSION_NUMBER(l_markRecords.getRecord().get(6).trim());
                    
                    
                    
                    dbg("studentID"+studentMarks.getSTUDENT_ID() ,session);
                    dbg("exam"+studentMarks.getEXAM() ,session);
                    dbg("subjectID"+studentMarks.getSUBJECT_ID(),session);
                    dbg("grade"+studentMarks.getGRADE(),session);
                    dbg("mark"+studentMarks.getMARK(),session);
                    dbg("teacherFeedBack"+studentMarks.getTEACHER_FEEDBACK(),session);
                    dbg("versionNumber"+studentMarks.getVERSION_NUMBER(),session);
                    
                    
                    dataset.add(studentMarks);
                    
                }
            
            }
            
                else
            {
                SVW_STUDENT_MARKS service=new SVW_STUDENT_MARKS();
                 
                    service.setSTUDENT_ID(" ");
                    service.setEXAM(" ");
                    service.setSUBJECT_ID(" ");
                    service.setGRADE(" ");
                    service.setMARK(" ");
                    service.setTEACHER_FEEDBACK(" ");
                    service.setVERSION_NUMBER(" ");
                
                    
 dataset.add(service);
                
            }
            
            
            dbg("end of SVW_STUDENT_MARKS_DATASET convertDBtoReportObject",session);
            
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
