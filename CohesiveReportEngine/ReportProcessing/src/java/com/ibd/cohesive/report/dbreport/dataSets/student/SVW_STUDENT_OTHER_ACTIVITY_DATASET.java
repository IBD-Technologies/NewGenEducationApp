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
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_OTHER_ACTIVITY;
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
public class SVW_STUDENT_OTHER_ACTIVITY_DATASET {
//     ArrayList<SVW_STUDENT_OTHER_ACTIVITY> dataset;
    
    
    public ArrayList<SVW_STUDENT_OTHER_ACTIVITY> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside SVW_STUDENT_OTHER_ACTIVITY_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        Map<String,DBRecord>l_studentOtherActivityMap=null;
         boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        try
        {
        l_studentOtherActivityMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID,"STUDENT", "SVW_STUDENT_OTHER_ACTIVITY", session, dbSession,ismaxVersionRequired);
        }
        
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
       
        
         dbg("end of SVW_STUDENT_OTHER_ACTIVITY_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_studentOtherActivityMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<SVW_STUDENT_OTHER_ACTIVITY> convertDBtoReportObject(Map<String,DBRecord>p_studentOtherActivityMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<SVW_STUDENT_OTHER_ACTIVITY>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_STUDENT_OTHER_ACTIVITY convertDBtoReportObject",session);
            
            if(p_studentOtherActivityMap!=null&&!p_studentOtherActivityMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_studentOtherActivityMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_markRecords=recordIterator.next();
                    SVW_STUDENT_OTHER_ACTIVITY studentOtherActivity=new SVW_STUDENT_OTHER_ACTIVITY();
                    studentOtherActivity.setSTUDENT_ID(l_markRecords.getRecord().get(0).trim());
                    studentOtherActivity.setACTIVITY_ID(l_markRecords.getRecord().get(1).trim());
                    studentOtherActivity.setACTIVITY_NAME(l_markRecords.getRecord().get(2).trim());
                    studentOtherActivity.setACTIVITY_TYPE(l_markRecords.getRecord().get(3).trim());
                    studentOtherActivity.setLEVEL(l_markRecords.getRecord().get(4).trim());
                    studentOtherActivity.setVENUE(l_markRecords.getRecord().get(5).trim());
                    studentOtherActivity.setDATE(l_markRecords.getRecord().get(6).trim());
                    studentOtherActivity.setRESULT(l_markRecords.getRecord().get(7).trim());
                    studentOtherActivity.setINTREST(l_markRecords.getRecord().get(8).trim());
                    studentOtherActivity.setMAKER_ID(l_markRecords.getRecord().get(9).trim());
                    studentOtherActivity.setCHECKER_ID(l_markRecords.getRecord().get(10).trim());
                    studentOtherActivity.setMAKER_DATE_STAMP(l_markRecords.getRecord().get(11).trim());
                    studentOtherActivity.setCHECKER_DATE_STAMP(l_markRecords.getRecord().get(12).trim());
                    studentOtherActivity.setRECORD_STATUS(l_markRecords.getRecord().get(13).trim());
                    studentOtherActivity.setAUTH_STATUS(l_markRecords.getRecord().get(14).trim());
                    studentOtherActivity.setVERSION_NUMBER(l_markRecords.getRecord().get(15).trim());
                    studentOtherActivity.setMAKER_REMARKS(l_markRecords.getRecord().get(16).trim());
                    studentOtherActivity.setCHECKER_REMARKS(l_markRecords.getRecord().get(17).trim()); 
                    
         
                    
                    
                    dataset.add(studentOtherActivity);
                    
                }
            
            }
            
                else
            {
                SVW_STUDENT_OTHER_ACTIVITY service=new SVW_STUDENT_OTHER_ACTIVITY();
                 
                    service.setSTUDENT_ID(" ");
                    service.setACTIVITY_ID(" ");
                    service.setACTIVITY_NAME(" ");
                    service.setACTIVITY_TYPE(" ");
                    service.setLEVEL(" ");
                    service.setVENUE(" ");
                    service.setDATE(" ");
                    service.setRESULT(" ");
                    service.setINTREST(" ");
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
            
            
            dbg("end of SVW_STUDENT_OTHER_ACTIVITY_DATASET convertDBtoReportObject",session);
            
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
