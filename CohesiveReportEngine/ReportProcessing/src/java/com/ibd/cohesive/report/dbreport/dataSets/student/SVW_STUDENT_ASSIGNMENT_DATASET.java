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
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_ASSIGNMENT;
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
public class SVW_STUDENT_ASSIGNMENT_DATASET {
//      ArrayList<SVW_STUDENT_ASSIGNMENT> dataset;
    
    
    public ArrayList<SVW_STUDENT_ASSIGNMENT> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside SVW_STUDENT_ASSIGNMENT_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        Map<String,DBRecord>l_studentAssignmentMap=null;
         boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
        try
        {
        
        
        
        l_studentAssignmentMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID,"STUDENT", "SVW_STUDENT_ASSIGNMENT", session, dbSession,ismaxVersionRequired);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
         
             
        
         dbg("end of SVW_STUDENT_ASSIGNMENT_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_studentAssignmentMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<SVW_STUDENT_ASSIGNMENT> convertDBtoReportObject(Map<String,DBRecord>p_studentAssignmentMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<SVW_STUDENT_ASSIGNMENT> dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_STUDENT_ASSIGNMENT_DATASET convertDBtoReportObject",session);
            
            if(p_studentAssignmentMap!=null&&!p_studentAssignmentMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_studentAssignmentMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_studentAssignmentRecords=recordIterator.next();
                    SVW_STUDENT_ASSIGNMENT studentAssignment=new SVW_STUDENT_ASSIGNMENT();
                    studentAssignment.setSTUDENT_ID(l_studentAssignmentRecords.getRecord().get(0).trim());
                    studentAssignment.setSUBJECT_ID(l_studentAssignmentRecords.getRecord().get(1).trim());
                    studentAssignment.setASSIGNMENT_ID(l_studentAssignmentRecords.getRecord().get(2).trim());
                    studentAssignment.setASSIGNMENT_DESCRIPTION(l_studentAssignmentRecords.getRecord().get(3).trim());
                    studentAssignment.setDUE_DATE(l_studentAssignmentRecords.getRecord().get(4).trim());
                    studentAssignment.setCOMPLETED_DATE(l_studentAssignmentRecords.getRecord().get(5).trim());
                    studentAssignment.setSTATUS(l_studentAssignmentRecords.getRecord().get(6).trim());
                    studentAssignment.setTEACHER_COMMENTS(l_studentAssignmentRecords.getRecord().get(7).trim());
                    studentAssignment.setPARENT_COMMENTS(l_studentAssignmentRecords.getRecord().get(8).trim());
                    studentAssignment.setMAKER_ID(l_studentAssignmentRecords.getRecord().get(9).trim());
                    studentAssignment.setCHECKER_ID(l_studentAssignmentRecords.getRecord().get(10).trim());
                    studentAssignment.setMAKER_DATE_STAMP(l_studentAssignmentRecords.getRecord().get(11).trim());
                    studentAssignment.setCHECKER_DATE_STAMP(l_studentAssignmentRecords.getRecord().get(12).trim());
                    studentAssignment.setRECORD_STATUS(l_studentAssignmentRecords.getRecord().get(13).trim());
                    studentAssignment.setAUTH_STATUS(l_studentAssignmentRecords.getRecord().get(14).trim());
                    studentAssignment.setVERSION_NUMBER(l_studentAssignmentRecords.getRecord().get(15).trim());
                    studentAssignment.setMAKER_REMARKS(l_studentAssignmentRecords.getRecord().get(16).trim());
                    studentAssignment.setCHECKER_REMARKS(l_studentAssignmentRecords.getRecord().get(17).trim());
                    
                    dbg("studentID"+studentAssignment.getSTUDENT_ID(),session);
                    dbg("subject id"+studentAssignment.getSUBJECT_ID() ,session);
                    dbg("assignment id"+studentAssignment.getASSIGNMENT_ID() ,session);
                    dbg("assignment Description"+studentAssignment.getASSIGNMENT_DESCRIPTION() ,session);
                    dbg("duedate "+studentAssignment.getDUE_DATE(),session);
                    dbg("Completed date"+studentAssignment.getCOMPLETED_DATE() ,session);
                    dbg("status"+studentAssignment.getCOMPLETED_DATE(),session);
                    dbg("teacher comments"+ studentAssignment.getTEACHER_COMMENTS(),session);
                    dbg("parent comments"+studentAssignment.getPARENT_COMMENTS() ,session);
                    dbg("makerID"+studentAssignment.getMAKER_ID(),session);
                    dbg("checkerID"+studentAssignment.getCHECKER_ID() ,session);
                    dbg("makerDateStamp"+studentAssignment.getMAKER_DATE_STAMP() ,session);
                    dbg("checkerDateStamp"+studentAssignment.getCHECKER_DATE_STAMP() ,session);
                    dbg("recordStatus"+ studentAssignment.getRECORD_STATUS(),session);
                    dbg("authStatus"+studentAssignment.getAUTH_STATUS(),session);
                    dbg("versionNumber"+studentAssignment.getVERSION_NUMBER(),session);
                    dbg("makerRemarks"+studentAssignment.getMAKER_REMARKS(),session);
                    dbg("checkerRemarks"+studentAssignment.getCHECKER_REMARKS(),session);
                    
                    dataset.add(studentAssignment);
                    
                }
            
            }
            
            
            
            
                else
            {
                SVW_STUDENT_ASSIGNMENT service=new SVW_STUDENT_ASSIGNMENT();
                 
                    service.setSTUDENT_ID(" ");
                    service.setSUBJECT_ID(" ");
                    service.setASSIGNMENT_ID(" ");
                    service.setASSIGNMENT_DESCRIPTION(" ");
                    service.setDUE_DATE(" ");
                    service.setCOMPLETED_DATE(" ");
                    service.setSTATUS(" ");
                    service.setTEACHER_COMMENTS(" ");
                    service.setPARENT_COMMENTS(" ");
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
            
            
            
            
            
            
            dbg("end of SVW_STUDENT_ASSIGNMENT_DATASET convertDBtoReportObject",session);
            
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
