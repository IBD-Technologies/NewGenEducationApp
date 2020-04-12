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
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_CONTACT_PERSON_DETAILS;
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
public class TVW_CONTACT_PERSON_DETAILS_DATASET {
//    ArrayList<TVW_CONTACT_PERSON_DETAILS> dataset;
    
    
    public ArrayList<TVW_CONTACT_PERSON_DETAILS> getTableObject(String p_teacherID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside TVW_CONTACT_PERSON_DETAILS_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        Map<String,DBRecord>l_contactPersonMap=null;
        try
        {
        l_contactPersonMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_teacherID,"TEACHER", "TVW_CONTACT_PERSON_DETAILS", session, dbSession);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of TVW_CONTACT_PERSON_DETAILS_DATASET--->getTableObject",session);  
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
    
    
    
    
    private ArrayList<TVW_CONTACT_PERSON_DETAILS> convertDBtoReportObject(Map<String,DBRecord>p_contactPersonMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<TVW_CONTACT_PERSON_DETAILS>dataset=new ArrayList();
        try{
            
            
            dbg("inside TVW_CONTACT_PERSON_DETAILS_DATASET convertDBtoReportObject",session);
            
            if(p_contactPersonMap!=null&&!p_contactPersonMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_contactPersonMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_cpRecords=recordIterator.next();
                    TVW_CONTACT_PERSON_DETAILS teacherContactPersonTable=new TVW_CONTACT_PERSON_DETAILS();
                    teacherContactPersonTable.setTEACHER_ID(l_cpRecords.getRecord().get(0).trim());
                    teacherContactPersonTable.setCP_ID(l_cpRecords.getRecord().get(1).trim());
                    teacherContactPersonTable.setCP_NAME(l_cpRecords.getRecord().get(2).trim());
                    teacherContactPersonTable.setCP_RELATIONSHIP(l_cpRecords.getRecord().get(3).trim());
                    teacherContactPersonTable.setCP_OCCUPATION(l_cpRecords.getRecord().get(4).trim());
                    teacherContactPersonTable.setCP_MAILID(l_cpRecords.getRecord().get(5).trim());
                    teacherContactPersonTable.setCP_CONTACTNO(l_cpRecords.getRecord().get(6).trim());
                    teacherContactPersonTable.setIMAGE_PATH(l_cpRecords.getRecord().get(7).trim());
                    teacherContactPersonTable.setVERSION_NUMBER(l_cpRecords.getRecord().get(8).trim());
                    
                    dbg("teacherID"+teacherContactPersonTable.getTEACHER_ID() ,session);
                    dbg("contactPersonID"+teacherContactPersonTable.getCP_ID() ,session);
                    dbg("contactPersonName"+teacherContactPersonTable.getCP_NAME() ,session);
                    dbg("contactPersonRelationship"+teacherContactPersonTable.getCP_RELATIONSHIP() ,session);
                    dbg("occupation "+teacherContactPersonTable.getCP_OCCUPATION() ,session);
                    dbg("mail id"+teacherContactPersonTable.getCP_MAILID() ,session);
                    dbg("contact no"+teacherContactPersonTable.getCP_CONTACTNO() ,session);
                    dbg("image path"+ teacherContactPersonTable.getIMAGE_PATH(),session);
                    dbg("vaersion number"+teacherContactPersonTable.getVERSION_NUMBER() ,session);
                    
                    dataset.add(teacherContactPersonTable);
                    
                }
            
            }
            
                else
            {
                TVW_CONTACT_PERSON_DETAILS service=new TVW_CONTACT_PERSON_DETAILS();
                 
                    service.setTEACHER_ID(" ");
                    service.setCP_ID(" ");
                    service.setCP_NAME(" ");
                    service.setCP_RELATIONSHIP(" ");
                    service.setCP_OCCUPATION(" ");
                    service.setCP_MAILID(" ");
                    service.setCP_CONTACTNO(" ");
                    service.setIMAGE_PATH(" ");
                    service.setVERSION_NUMBER(" ");
                 
                    
 dataset.add(service);
                
            }
            
            
            dbg("end of TVW_CONTACT_PERSON_DETAILS_DATASET convertDBtoReportObject",session);
            
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
