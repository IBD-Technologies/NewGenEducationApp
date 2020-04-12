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
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_TEACHER_PROFILE;
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
public class TVW_TEACHER_PROFILE_DATASET {
//      ArrayList<TVW_TEACHER_PROFILE> dataset;
    
    
    public ArrayList<TVW_TEACHER_PROFILE> getTableObject(String p_teacherID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside TVW_TEACHER_PROFILE_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        Map<String,DBRecord>l_profileMap=null;
        try
        {
           
        l_profileMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_teacherID,"TEACHER", "TVW_TEACHER_PROFILE", session, dbSession);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of TVW_TEACHER_PROFILE_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_profileMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<TVW_TEACHER_PROFILE> convertDBtoReportObject(Map<String,DBRecord>p_profileMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<TVW_TEACHER_PROFILE>dataset=new ArrayList();
        try{
            
            
            dbg("inside TVW_TEACHER_PROFILE_DATASET convertDBtoReportObject",session);
            
            if(p_profileMap!=null&&!p_profileMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_profileMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_teacherRecords=recordIterator.next();
                    TVW_TEACHER_PROFILE teacherProfile=new TVW_TEACHER_PROFILE();
                    teacherProfile.setTEACHER_ID(l_teacherRecords.getRecord().get(0).trim());
                    teacherProfile.setTEACHER_NAME(l_teacherRecords.getRecord().get(1).trim());
                    teacherProfile.setQUALIFICATION(l_teacherRecords.getRecord().get(2).trim());
                    teacherProfile.setSTANDARD(l_teacherRecords.getRecord().get(3).trim());
                    teacherProfile.setSECTION(l_teacherRecords.getRecord().get(4).trim());
                    teacherProfile.setDOB(l_teacherRecords.getRecord().get(5).trim());
                    teacherProfile.setCONTACT_NO(l_teacherRecords.getRecord().get(6).trim());
                    teacherProfile.setEMAIL_ID(l_teacherRecords.getRecord().get(7).trim());
                    teacherProfile.setBLOOD_GROUP(l_teacherRecords.getRecord().get(8).trim());
                    teacherProfile.setADDRESSLINE1(l_teacherRecords.getRecord().get(9).trim());
                    teacherProfile.setADDRESSLINE2(l_teacherRecords.getRecord().get(10).trim());
                    teacherProfile.setADDRESSLINE3(l_teacherRecords.getRecord().get(11).trim());
                    teacherProfile.setADDRESSLINE4(l_teacherRecords.getRecord().get(12).trim());
                    teacherProfile.setADDRESSLINE5(l_teacherRecords.getRecord().get(13).trim());
                    teacherProfile.setIMAGE_PATH(l_teacherRecords.getRecord().get(14).trim());
                    teacherProfile.setMAKER_ID(l_teacherRecords.getRecord().get(15).trim());
                    teacherProfile.setCHECKER_ID(l_teacherRecords.getRecord().get(16).trim());
                    teacherProfile.setMAKER_DATE_STAMP(l_teacherRecords.getRecord().get(17).trim());
                    teacherProfile.setCHECKER_DATE_STAMP(l_teacherRecords.getRecord().get(18).trim());
                    teacherProfile.setRECORD_STATUS(l_teacherRecords.getRecord().get(19).trim());
                    teacherProfile.setAUTH_STATUS(l_teacherRecords.getRecord().get(20).trim());
                    teacherProfile.setVERSION_NUMBER(l_teacherRecords.getRecord().get(21).trim());
                    teacherProfile.setMAKER_REMARKS(l_teacherRecords.getRecord().get(22).trim());
                    teacherProfile.setCHECKER_REMARKS(l_teacherRecords.getRecord().get(23).trim()); 
                    
                
                    dbg("teacherID"+teacherProfile.getTEACHER_ID() ,session);
                    dbg("teacherName"+teacherProfile.getTEACHER_NAME() ,session); 
                    dbg("qualification"+teacherProfile.getQUALIFICATION() ,session); 
                    dbg("standard"+teacherProfile.getSTANDARD() ,session); 
                    dbg("section"+teacherProfile.getSECTION() ,session); 
                    dbg("dob"+teacherProfile.getDOB() ,session); 
                    dbg("contactNo"+teacherProfile.getCONTACT_NO() ,session); 
                    dbg("emailID"+teacherProfile.getEMAIL_ID() ,session); 
                    dbg("bloodGroup"+teacherProfile.getBLOOD_GROUP() ,session); 
                    dbg("addressLine1"+teacherProfile.getADDRESSLINE1() ,session); 
                    dbg("addressLine2"+teacherProfile.getADDRESSLINE2() ,session); 
                    dbg("addressLine3"+teacherProfile.getADDRESSLINE3() ,session); 
                    dbg("addressLine4"+teacherProfile.getADDRESSLINE4() ,session); 
                    dbg("addressLine5"+teacherProfile.getADDRESSLINE5() ,session); 
                    dbg("imagepath"+teacherProfile.getIMAGE_PATH(),session); 
                    dbg("makerID"+teacherProfile.getMAKER_ID() ,session);
                    dbg("checkerID"+teacherProfile.getCHECKER_ID() ,session);
                    dbg("makerDateStamp"+teacherProfile.getMAKER_DATE_STAMP() ,session);
                    dbg("checkerDateStamp"+teacherProfile.getCHECKER_DATE_STAMP() ,session);
                    dbg("recordStatus"+ teacherProfile.getRECORD_STATUS(),session);
                    dbg("authStatus"+teacherProfile.getAUTH_STATUS(),session);
                    dbg("versionNumber"+teacherProfile.getVERSION_NUMBER(),session);
                    dbg("makerRemarks"+teacherProfile.getMAKER_REMARKS(),session);
                    dbg("checkerRemarks"+teacherProfile.getCHECKER_REMARKS(),session);
                   
                    
                    dataset.add(teacherProfile);
                    
                }
            
            }
                else
            {
                TVW_TEACHER_PROFILE service=new TVW_TEACHER_PROFILE();
                 
                    service.setTEACHER_ID(" ");
                    service.setTEACHER_NAME(" ");
                    service.setQUALIFICATION(" ");
                    service.setSTANDARD(" ");
                    service.setSECTION(" ");
                    service.setDOB(" ");
                    service.setCONTACT_NO(" ");
                    service.setEMAIL_ID(" ");
                    service.setBLOOD_GROUP(" ");
                    service.setADDRESSLINE1(" ");
                    service.setADDRESSLINE2(" ");
                    service.setADDRESSLINE3(" ");
                    service.setADDRESSLINE4(" ");
                    service.setADDRESSLINE5(" ");
                    service.setIMAGE_PATH(" ");
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
            
            
            dbg("end of TVW_TEACHER_PROFILE_DATASET convertDBtoReportObject",session);
            
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
