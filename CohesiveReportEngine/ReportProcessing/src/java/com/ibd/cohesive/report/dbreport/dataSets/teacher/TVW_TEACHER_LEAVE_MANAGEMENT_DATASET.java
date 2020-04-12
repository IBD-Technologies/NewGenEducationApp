/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.teacher;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.app.CONTRACT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_TEACHER_LEAVE_MANAGEMENT;
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
public class TVW_TEACHER_LEAVE_MANAGEMENT_DATASET {
//     ArrayList<TVW_TEACHER_LEAVE_MANAGEMENT> dataset;
    
    
    public ArrayList<TVW_TEACHER_LEAVE_MANAGEMENT> getTableObject(String p_teacherID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside TVW_TEACHER_LEAVE_MANAGEMENT_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        Map<String,DBRecord>l_leaveManagementMap=null;
        try
        {
        l_leaveManagementMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_teacherID,"TEACHER", "TVW_TEACHER_LEAVE_MANAGEMENT", session, dbSession);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of TVW_TEACHER_LEAVE_MANAGEMENT_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_leaveManagementMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<TVW_TEACHER_LEAVE_MANAGEMENT> convertDBtoReportObject(Map<String,DBRecord>p_leaveManagementMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<TVW_TEACHER_LEAVE_MANAGEMENT>dataset=new ArrayList();
        try{
            
            
            dbg("inside TVW_TEACHER_LEAVE_MANAGEMENT_DATASET convertDBtoReportObject",session);
            
            if(p_leaveManagementMap!=null&&!p_leaveManagementMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_leaveManagementMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_teacherRecords=recordIterator.next();
                    TVW_TEACHER_LEAVE_MANAGEMENT teacherLeave=new TVW_TEACHER_LEAVE_MANAGEMENT();
                    teacherLeave.setTEACHER_ID(l_teacherRecords.getRecord().get(0).trim());
                    teacherLeave.setFROM(l_teacherRecords.getRecord().get(1).trim());
                    teacherLeave.setTO(l_teacherRecords.getRecord().get(2).trim());
                    teacherLeave.setTYPE(l_teacherRecords.getRecord().get(3).trim());
                    teacherLeave.setREASON(l_teacherRecords.getRecord().get(4).trim());
                    teacherLeave.setSTATUS(l_teacherRecords.getRecord().get(5).trim());
                    teacherLeave.setREMARKS(l_teacherRecords.getRecord().get(6).trim());
                    teacherLeave.setMAKER_ID(l_teacherRecords.getRecord().get(7).trim());
                    teacherLeave.setCHECKER_ID(l_teacherRecords.getRecord().get(8).trim());
                    teacherLeave.setMAKER_DATE_STAMP(l_teacherRecords.getRecord().get(9).trim());
                    teacherLeave.setCHECKER_DATE_STAMP(l_teacherRecords.getRecord().get(10).trim());
                    teacherLeave.setRECORD_STATUS(l_teacherRecords.getRecord().get(11).trim());
                    teacherLeave.setAUTH_STATUS(l_teacherRecords.getRecord().get(12).trim());
                    teacherLeave.setVERSION_NUMBER(l_teacherRecords.getRecord().get(13).trim());
                    teacherLeave.setMAKER_REMARKS(l_teacherRecords.getRecord().get(14).trim());
                    teacherLeave.setCHECKER_REMARKS(l_teacherRecords.getRecord().get(15).trim()); 
                    
                
                    dbg("teacherID"+teacherLeave.getTEACHER_ID() ,session);
                    dbg("from"+teacherLeave.getFROM() ,session);
                    dbg("to"+teacherLeave.getTO() ,session);
                    dbg("type"+teacherLeave.getTYPE() ,session);
                    dbg("reason"+teacherLeave.getREASON() ,session);
                    dbg("status"+teacherLeave.getSTATUS() ,session);
                    dbg("remarks"+teacherLeave.getREMARKS() ,session);
                    dbg("makerID"+teacherLeave.getMAKER_ID() ,session);
                    dbg("checkerID"+teacherLeave.getCHECKER_ID() ,session);
                    dbg("makerDateStamp"+teacherLeave.getMAKER_DATE_STAMP() ,session);
                    dbg("checkerDateStamp"+teacherLeave.getCHECKER_DATE_STAMP() ,session);
                    dbg("recordStatus"+ teacherLeave.getRECORD_STATUS(),session);
                    dbg("authStatus"+teacherLeave.getAUTH_STATUS(),session);
                    dbg("versionNumber"+teacherLeave.getVERSION_NUMBER(),session);
                    dbg("makerRemarks"+teacherLeave.getMAKER_REMARKS(),session);
                    dbg("checkerRemarks"+teacherLeave.getCHECKER_REMARKS(),session);
                   
                    
                    dataset.add(teacherLeave);
                    
                }
            
            }
            
                else
            {
                TVW_TEACHER_LEAVE_MANAGEMENT service=new TVW_TEACHER_LEAVE_MANAGEMENT();
                 
                    service.setTEACHER_ID(" ");
                    service.setFROM(" ");
                    service.setTO(" ");
                    service.setTYPE(" ");
                    service.setREASON(" ");
                    service.setSTATUS(" ");
                    service.setREMARKS(" ");
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
            
            
            dbg("end of TVW_TEACHER_LEAVE_MANAGEMENT_DATASET convertDBtoReportObject",session);
            
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
