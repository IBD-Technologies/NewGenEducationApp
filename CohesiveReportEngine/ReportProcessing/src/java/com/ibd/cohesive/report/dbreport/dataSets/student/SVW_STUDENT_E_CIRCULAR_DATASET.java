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
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_E_CIRCULAR;
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
public class SVW_STUDENT_E_CIRCULAR_DATASET {
    
    
    public ArrayList<SVW_STUDENT_E_CIRCULAR> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside SVW_STUDENT_E_CIRCULAR_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
         boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
         Map<String,DBRecord>l_contactPersonMap=null;
         try
         {
        
        l_contactPersonMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID,"STUDENT", "SVW_STUDENT_E_CIRCULAR", session, dbSession,ismaxVersionRequired);
         }
         
           catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of SVW_STUDENT_E_CIRCULAR_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_contactPersonMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<SVW_STUDENT_E_CIRCULAR> convertDBtoReportObject(Map<String,DBRecord>p_contactPersonMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<SVW_STUDENT_E_CIRCULAR>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_STUDENT_E_CIRCULAR_DATASET convertDBtoReportObject",session);
            
            if(p_contactPersonMap!=null&&!p_contactPersonMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_contactPersonMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_cpRecords=recordIterator.next();
                    SVW_STUDENT_E_CIRCULAR studentContactPersonTable=new SVW_STUDENT_E_CIRCULAR();
                    studentContactPersonTable.setSTUDENT_ID(l_cpRecords.getRecord().get(0).trim());
                    studentContactPersonTable.setE_CIRCULAR_ID(l_cpRecords.getRecord().get(1).trim());
                    studentContactPersonTable.setDESCRIPTION(l_cpRecords.getRecord().get(2).trim());
                    studentContactPersonTable.setCONTENT_PATH(l_cpRecords.getRecord().get(3).trim());
                    studentContactPersonTable.setMAKER_ID(l_cpRecords.getRecord().get(4).trim());
                    studentContactPersonTable.setCHECKER_ID(l_cpRecords.getRecord().get(5).trim());
                    studentContactPersonTable.setMAKER_DATE_STAMP(l_cpRecords.getRecord().get(6).trim());
                    studentContactPersonTable.setCHECKER_DATE_STAMP(l_cpRecords.getRecord().get(7).trim());
                    studentContactPersonTable.setRECORD_STATUS(l_cpRecords.getRecord().get(8).trim());
                    studentContactPersonTable.setAUTH_STATUS(l_cpRecords.getRecord().get(9).trim());
                    studentContactPersonTable.setVERSION_NUMBER(l_cpRecords.getRecord().get(10).trim());
                    studentContactPersonTable.setMAKER_REMARKS(l_cpRecords.getRecord().get(11).trim());
                    studentContactPersonTable.setCHECKER_REMARKS(l_cpRecords.getRecord().get(12).trim());
                    studentContactPersonTable.setURL(l_cpRecords.getRecord().get(13).trim());
                    
                    
//                    dbg("studentID"+studentContactPersonTable.getSTUDENT_ID() ,session);
//                    dbg("contactPersonID"+studentContactPersonTable.getCP_ID() ,session);
//                    dbg("contactPersonName"+studentContactPersonTable.getCP_NAME() ,session);
//                    dbg("contactPersonRelationship"+studentContactPersonTable.getCP_RELATIONSHIP() ,session);
//                    dbg("occupation "+studentContactPersonTable.getCP_OCCUPATION() ,session);
//                    dbg("mail id"+studentContactPersonTable.getCP_MAILID() ,session);
//                    dbg("contact no"+studentContactPersonTable.getCP_CONTACTNO() ,session);
//                    dbg("image path"+ studentContactPersonTable.getIMAGE_PATH(),session);
//                    dbg("vaersion number"+studentContactPersonTable.getVERSION_NUMBER() ,session);
                    
                    dataset.add(studentContactPersonTable);
                    
                }
            
            }
            
                else
            {
                SVW_STUDENT_E_CIRCULAR service=new SVW_STUDENT_E_CIRCULAR();
                 
                    service.setSTUDENT_ID(" ");
                    service.setE_CIRCULAR_ID(" ");
                    service.setDESCRIPTION(" ");
                    service.setCONTENT_PATH(" ");
                    service.setMAKER_ID(" ");
                    service.setCHECKER_ID(" ");
                    service.setMAKER_DATE_STAMP(" ");
                    service.setCHECKER_DATE_STAMP(" ");
                    service.setRECORD_STATUS(" ");
                    service.setAUTH_STATUS(" ");
                    service.setVERSION_NUMBER(" ");
                    service.setMAKER_REMARKS(" ");
                    service.setCHECKER_REMARKS(" ");
                    service.setURL(" ");
                 
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            
            dbg("end of SVW_STUDENT_E_CIRCULAR_DATASET convertDBtoReportObject",session);
            
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
