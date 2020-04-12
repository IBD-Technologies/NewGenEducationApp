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
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_LEAVE_MANAGEMENT;
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
public class SVW_STUDENT_LEAVE_MANAGEMENT_DATASET {
//    ArrayList<SVW_STUDENT_LEAVE_MANAGEMENT> dataset;
    
    
    public ArrayList<SVW_STUDENT_LEAVE_MANAGEMENT> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside SVW_STUDENT_LEAVE_MANAGEMENT_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
         boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
        Map<String,DBRecord>l_studentLeaveManagementMap=null;
        try
        {
        l_studentLeaveManagementMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID,"STUDENT", "SVW_STUDENT_LEAVE_MANAGEMENT", session, dbSession,ismaxVersionRequired);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of SVW_STUDENT_LEAVE_MANAGEMENT_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_studentLeaveManagementMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<SVW_STUDENT_LEAVE_MANAGEMENT> convertDBtoReportObject(Map<String,DBRecord>p_studentLeaveManagementMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<SVW_STUDENT_LEAVE_MANAGEMENT>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_STUDENT_LEAVE_MANAGEMENT convertDBtoReportObject",session);
            
            if(p_studentLeaveManagementMap!=null&&!p_studentLeaveManagementMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_studentLeaveManagementMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_leaveRecords=recordIterator.next();
                    SVW_STUDENT_LEAVE_MANAGEMENT leaveManagement=new SVW_STUDENT_LEAVE_MANAGEMENT();
                    leaveManagement.setSTUDENT_ID(l_leaveRecords.getRecord().get(0).trim());
                    leaveManagement.setFROM(l_leaveRecords.getRecord().get(1).trim());
                    leaveManagement.setTO(l_leaveRecords.getRecord().get(2).trim());
                    leaveManagement.setTYPE(l_leaveRecords.getRecord().get(3).trim());
                    leaveManagement.setREASON(l_leaveRecords.getRecord().get(4).trim());
                    leaveManagement.setSTATUS(l_leaveRecords.getRecord().get(5).trim());
                    leaveManagement.setREMARKS(l_leaveRecords.getRecord().get(6).trim());
                    leaveManagement.setMAKER_ID(l_leaveRecords.getRecord().get(7).trim());
                    leaveManagement.setCHECKER_ID(l_leaveRecords.getRecord().get(8).trim());
                    leaveManagement.setMAKER_DATE_STAMP(l_leaveRecords.getRecord().get(9).trim());
                    leaveManagement.setCHECKER_DATE_STAMP(l_leaveRecords.getRecord().get(10).trim());
                    leaveManagement.setRECORD_STATUS(l_leaveRecords.getRecord().get(11).trim());
                    leaveManagement.setAUTH_STATUS(l_leaveRecords.getRecord().get(12).trim());
                    leaveManagement.setVERSION_NUMBER(l_leaveRecords.getRecord().get(13).trim());
                    leaveManagement.setMAKER_REMARKS(l_leaveRecords.getRecord().get(14).trim());
                    leaveManagement.setCHECKER_REMARKS(l_leaveRecords.getRecord().get(15).trim()); 
                    
                    
                    dbg("studentID"+leaveManagement.getSTUDENT_ID() ,session);
                    dbg("from"+leaveManagement.getFROM() ,session);
                    dbg("to"+leaveManagement.getTO(),session);
                    dbg("type"+leaveManagement.getTYPE(),session);
                    dbg("reason"+leaveManagement.getREASON(),session);
                    dbg("status"+leaveManagement.getSTATUS(),session);
                    dbg("remarks"+leaveManagement.getREMARKS(),session);
                    dbg("makerID"+leaveManagement.getMAKER_ID(),session);
                    dbg("checkerID"+leaveManagement.getCHECKER_ID() ,session);
                    dbg("makerDateStamp"+leaveManagement.getMAKER_DATE_STAMP() ,session);
                    dbg("checkerDateStamp"+leaveManagement.getCHECKER_DATE_STAMP() ,session);
                    dbg("recordStatus"+ leaveManagement.getRECORD_STATUS(),session);
                    dbg("authStatus"+leaveManagement.getAUTH_STATUS(),session);
                    dbg("versionNumber"+leaveManagement.getVERSION_NUMBER(),session);
                    dbg("makerRemarks"+leaveManagement.getMAKER_REMARKS(),session);
                    dbg("checkerRemarks"+leaveManagement.getCHECKER_REMARKS(),session);
                    
                    
                    
                    dataset.add(leaveManagement);
                    
                }
            
            }
            
            
            
            
                else
            {
                SVW_STUDENT_LEAVE_MANAGEMENT service=new SVW_STUDENT_LEAVE_MANAGEMENT();
                 
                    service.setSTUDENT_ID(" ");
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
            
            
            dbg("end of SVW_STUDENT_LEAVE_MANAGEMENT_DATASET convertDBtoReportObject",session);
            
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
