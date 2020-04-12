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
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_PROFILE;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_PRORESS_CARD;
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
public class SVW_STUDENT_PRORESS_CARD_DATASET {
//     ArrayList<SVW_STUDENT_PRORESS_CARD> dataset;
    
    
    public ArrayList<SVW_STUDENT_PRORESS_CARD> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside SVW_STUDENT_PRORESS_CARD_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
         boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
         Map<String,DBRecord>l_studentProgressCardMap=null;
         try
         {
        l_studentProgressCardMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID,"STUDENT", "SVW_STUDENT_PRORESS_CARD", session, dbSession,ismaxVersionRequired);
         }
           catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of SVW_STUDENT_PRORESS_CARD_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_studentProgressCardMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<SVW_STUDENT_PRORESS_CARD> convertDBtoReportObject(Map<String,DBRecord>p_studentProgressCardMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<SVW_STUDENT_PRORESS_CARD>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_STUDENT_PRORESS_CARD convertDBtoReportObject",session);
            
            if(p_studentProgressCardMap!=null&&!p_studentProgressCardMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_studentProgressCardMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_progressRecords=recordIterator.next();
                    SVW_STUDENT_PRORESS_CARD studentProgress=new SVW_STUDENT_PRORESS_CARD();
                    studentProgress.setSTUDENT_ID(l_progressRecords.getRecord().get(0).trim());
                    studentProgress.setEXAM(l_progressRecords.getRecord().get(1).trim());
                    studentProgress.setTOTAL(l_progressRecords.getRecord().get(2).trim());
                    studentProgress.setRANK(l_progressRecords.getRecord().get(3).trim());
                    studentProgress.setMAKER_ID(l_progressRecords.getRecord().get(4).trim());
                    studentProgress.setCHECKER_ID(l_progressRecords.getRecord().get(5).trim());
                    studentProgress.setMAKER_DATE_STAMP(l_progressRecords.getRecord().get(6).trim());
                    studentProgress.setCHECKER_DATE_STAMP(l_progressRecords.getRecord().get(7).trim());
                    studentProgress.setRECORD_STATUS(l_progressRecords.getRecord().get(8).trim());
                    studentProgress.setAUTH_STATUS(l_progressRecords.getRecord().get(9).trim());
                    studentProgress.setVERSION_NUMBER(l_progressRecords.getRecord().get(10).trim());
                    studentProgress.setMAKER_REMARKS(l_progressRecords.getRecord().get(11).trim());
                    studentProgress.setCHECKER_REMARKS(l_progressRecords.getRecord().get(12).trim()); 
                    
              
                    dataset.add(studentProgress);
                    
                }
            
            }
            
                else
            {
                SVW_STUDENT_PRORESS_CARD service=new SVW_STUDENT_PRORESS_CARD();
                 
                    service.setSTUDENT_ID(" ");
                    service.setEXAM(" ");
                    service.setTOTAL(" ");
                    service.setRANK(" ");
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
            
            dbg("end of SSVW_STUDENT_PRORESS_CARD_DATASET convertDBtoReportObject",session);
            
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
