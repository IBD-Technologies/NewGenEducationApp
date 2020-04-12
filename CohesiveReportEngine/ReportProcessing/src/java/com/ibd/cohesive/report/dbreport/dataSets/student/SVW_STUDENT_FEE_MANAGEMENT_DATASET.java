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
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_FEE_MANAGEMENT;
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
public class SVW_STUDENT_FEE_MANAGEMENT_DATASET {
//      ArrayList<SVW_STUDENT_FEE_MANAGEMENT> dataset;
    
    
    public ArrayList<SVW_STUDENT_FEE_MANAGEMENT> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside SVW_STUDENT_FEE_MANAGEMENT_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
         boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
         Map<String,DBRecord>l_studentFeeManagementMap=null;
         try
         {
        l_studentFeeManagementMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID,"STUDENT", "SVW_STUDENT_FEE_MANAGEMENT", session, dbSession,ismaxVersionRequired);
         }
           catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of SVW_STUDENT_FEE_MANAGEMENT_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_studentFeeManagementMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<SVW_STUDENT_FEE_MANAGEMENT> convertDBtoReportObject(Map<String,DBRecord>p_feeManagementMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<SVW_STUDENT_FEE_MANAGEMENT>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_STUDENT_FEE_MANAGEMENT convertDBtoReportObject",session);
            
            if(p_feeManagementMap!=null&&!p_feeManagementMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_feeManagementMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_feeRecords=recordIterator.next();
                    SVW_STUDENT_FEE_MANAGEMENT studentFeeManagement=new SVW_STUDENT_FEE_MANAGEMENT();
                    studentFeeManagement.setSTUDENT_ID(l_feeRecords.getRecord().get(0).trim());
                    studentFeeManagement.setFEE_ID(l_feeRecords.getRecord().get(1).trim());
                    studentFeeManagement.setFEE_TYPE(l_feeRecords.getRecord().get(2).trim());
                    studentFeeManagement.setAMOUNT(l_feeRecords.getRecord().get(3).trim());
                    studentFeeManagement.setDUE_DATE(l_feeRecords.getRecord().get(4).trim());
                    studentFeeManagement.setSTATUS(l_feeRecords.getRecord().get(5).trim());
                    studentFeeManagement.setMAKER_ID(l_feeRecords.getRecord().get(6).trim());
                    studentFeeManagement.setCHECKER_ID(l_feeRecords.getRecord().get(7).trim());
                    studentFeeManagement.setMAKER_DATE_STAMP(l_feeRecords.getRecord().get(8).trim());
                    studentFeeManagement.setCHECKER_DATE_STAMP(l_feeRecords.getRecord().get(9).trim());
                    studentFeeManagement.setRECORD_STATUS(l_feeRecords.getRecord().get(10).trim());
                    studentFeeManagement.setAUTH_STATUS(l_feeRecords.getRecord().get(11).trim());
                    studentFeeManagement.setVERSION_NUMBER(l_feeRecords.getRecord().get(12).trim());
                    studentFeeManagement.setMAKER_REMARKS(l_feeRecords.getRecord().get(13).trim());
                    studentFeeManagement.setCHECKER_REMARKS(l_feeRecords.getRecord().get(14).trim());
                    studentFeeManagement.setFEE_PAID(l_feeRecords.getRecord().get(15).trim());
                    studentFeeManagement.setOUTSTANDING(l_feeRecords.getRecord().get(16).trim());
                    studentFeeManagement.setPAID_DATE(l_feeRecords.getRecord().get(17).trim());
                    
                    
                    dbg("studentID"+studentFeeManagement.getSTUDENT_ID() ,session);
                    dbg("feeID"+studentFeeManagement.getFEE_ID() ,session);
                    dbg("feeType"+studentFeeManagement.getFEE_TYPE(),session);
                    dbg("amount"+studentFeeManagement.getAMOUNT(),session);
                    dbg("dueDate"+studentFeeManagement.getDUE_DATE(),session);
                    dbg("status"+studentFeeManagement.getDUE_DATE(),session);
                    dbg("makerID"+studentFeeManagement.getMAKER_ID(),session);
                    dbg("checkerID"+studentFeeManagement.getCHECKER_ID() ,session);
                    dbg("makerDateStamp"+studentFeeManagement.getMAKER_DATE_STAMP() ,session);
                    dbg("checkerDateStamp"+studentFeeManagement.getCHECKER_DATE_STAMP() ,session);
                    dbg("recordStatus"+ studentFeeManagement.getRECORD_STATUS(),session);
                    dbg("authStatus"+studentFeeManagement.getAUTH_STATUS(),session);
                    dbg("versionNumber"+studentFeeManagement.getVERSION_NUMBER(),session);
                    dbg("makerRemarks"+studentFeeManagement.getMAKER_REMARKS(),session);
                    dbg("checkerRemarks"+studentFeeManagement.getCHECKER_REMARKS(),session);
                    
                    
                    
                    dataset.add(studentFeeManagement);
                    
                }
            
            }
                else
            {
                SVW_STUDENT_FEE_MANAGEMENT service=new SVW_STUDENT_FEE_MANAGEMENT();
                 
                    service.setSTUDENT_ID(" ");
                    service.setFEE_ID(" ");
                    service.setFEE_TYPE(" ");
                    service.setAMOUNT(" ");
                    service.setDUE_DATE(" ");
                    service.setSTATUS(" ");
                    service.setMAKER_ID(" ");
                    service.setCHECKER_ID(" ");
                    service.setMAKER_DATE_STAMP(" ");
                    service.setCHECKER_DATE_STAMP(" ");
                    service.setRECORD_STATUS(" ");
                    service.setAUTH_STATUS(" ");
                    service.setVERSION_NUMBER(" ");
                    service.setMAKER_REMARKS(" ");
                    service.setCHECKER_REMARKS(" ");
                    service.setFEE_PAID(" ");
                    service.setOUTSTANDING(" ");
                    service.setPAID_DATE(" ");
                                  
                                  
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            dbg("end of SVW_STUDENT_FEE_MANAGEMENT_DATASET convertDBtoReportObject",session);
            
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
