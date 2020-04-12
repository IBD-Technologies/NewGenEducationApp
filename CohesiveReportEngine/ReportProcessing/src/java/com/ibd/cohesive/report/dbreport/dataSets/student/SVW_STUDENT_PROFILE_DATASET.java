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
public class SVW_STUDENT_PROFILE_DATASET {
//      ArrayList<SVW_STUDENT_PROFILE> dataset;
    
    
    public ArrayList<SVW_STUDENT_PROFILE> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside SVW_STUDENT_PROFILE_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
         boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
          Map<String,DBRecord>l_studentProfileMap=null;
          try
          {
        l_studentProfileMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID,"STUDENT", "SVW_STUDENT_PROFILE", session, dbSession,ismaxVersionRequired);
          }
            catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of SVW_STUDENT_PROFILE_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_studentProfileMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<SVW_STUDENT_PROFILE> convertDBtoReportObject(Map<String,DBRecord>p_studentProfileMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<SVW_STUDENT_PROFILE>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_STUDENT_PROFILE convertDBtoReportObject",session);
            
            if(p_studentProfileMap!=null&&!p_studentProfileMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_studentProfileMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_profileRecords=recordIterator.next();
                    SVW_STUDENT_PROFILE studentProfile=new SVW_STUDENT_PROFILE();
                    studentProfile.setSTUDENT_ID(l_profileRecords.getRecord().get(0).trim());
                    studentProfile.setSTUDENT_NAME(l_profileRecords.getRecord().get(1).trim());
                    studentProfile.setSTANDARD(l_profileRecords.getRecord().get(2).trim());
                    studentProfile.setSECTION(l_profileRecords.getRecord().get(3).trim());
                    studentProfile.setDOB(l_profileRecords.getRecord().get(4).trim());
                    studentProfile.setBLOODGROUP(l_profileRecords.getRecord().get(5).trim());
                    studentProfile.setADDRESSLINE1(l_profileRecords.getRecord().get(6).trim());
                    studentProfile.setADDRESSLINE2(l_profileRecords.getRecord().get(7).trim());
                    studentProfile.setADDRESSLINE3(l_profileRecords.getRecord().get(8).trim());
                    studentProfile.setADDRESSLINE4(l_profileRecords.getRecord().get(9).trim());
                    studentProfile.setADDRESSLINE5(l_profileRecords.getRecord().get(10).trim());
//                    studentProfile.setIMAGE_PATH("file:///D:/ARCH_HOME/INSTITUTE/I001/STUDENT/S009/img/profile.jpg");
                  
                    String imagePath="https://cohesive.ibdtechnologies.com/"+l_profileRecords.getRecord().get(11).trim();
                    studentProfile.setIMAGE_PATH(imagePath);
                    studentProfile.setMAKER_ID(l_profileRecords.getRecord().get(12).trim());
                    studentProfile.setCHECKER_ID(l_profileRecords.getRecord().get(13).trim());
                    studentProfile.setMAKER_DATE_STAMP(l_profileRecords.getRecord().get(14).trim());
                    studentProfile.setCHECKER_DATE_STAMP(l_profileRecords.getRecord().get(15).trim());
                    studentProfile.setRECORD_STATUS(l_profileRecords.getRecord().get(16).trim());
                    studentProfile.setAUTH_STATUS(l_profileRecords.getRecord().get(17).trim());
                    studentProfile.setVERSION_NUMBER(l_profileRecords.getRecord().get(18).trim());
                    studentProfile.setMAKER_REMARKS(l_profileRecords.getRecord().get(19).trim());
                    studentProfile.setCHECKER_REMARKS(l_profileRecords.getRecord().get(20).trim()); 
                    studentProfile.setEXISTING_MEDICAL_DETAIL(l_profileRecords.getRecord().get(21).trim());
              
                    dataset.add(studentProfile);
                    
                }
            
            }
            
                else
            {
                SVW_STUDENT_PROFILE service=new SVW_STUDENT_PROFILE();
                 
                    service.setSTUDENT_ID(" ");
                    service.setSTUDENT_NAME(" ");
                    service.setSTANDARD(" ");
                    service.setSECTION(" ");
                    service.setDOB(" ");
                    service.setBLOODGROUP(" ");
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
                    service.setEXISTING_MEDICAL_DETAIL(" ");
                                  
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            
            
            
            
            
            
            dbg("end of SVW_STUDENT_PROFILE_DATASET convertDBtoReportObject",session);
            
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
