/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.student;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.student.SVW_STUDENT_PROFILE_BUSINESS;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author DELL
 */
public class SVW_STUDENT_PROFILE_BUSINESS_DATASET {
    public ArrayList<SVW_STUDENT_PROFILE_BUSINESS> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside SVW_STUDENT_PROFILE_BUSINESS_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        ArrayList<SVW_STUDENT_PROFILE_BUSINESS>dataset=new ArrayList();
        
        
        
        try{
            
            Map<String,DBRecord>l_studentProfileMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID,"STUDENT", "SVW_STUDENT_PROFILE", session, dbSession);
            
            List<DBRecord>authorizedProfiles= l_studentProfileMap.values().stream().filter(rec->rec.getRecord().get(17).trim().equals("O")&&rec.getRecord().get(18).trim().equals("A")).collect(Collectors.toList());
            
            dataset=this.convertDBtoReportObject(authorizedProfiles, session);
            
        }catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
               
            }else{
                
                throw ex;
            }
            
            
        }
        
        if(dataset.isEmpty()){

        
            SVW_STUDENT_PROFILE_BUSINESS studentProfile=new SVW_STUDENT_PROFILE_BUSINESS();
            studentProfile.setSTUDENT_ID(" ");
            studentProfile.setSTUDENT_NAME(" ");
            studentProfile.setSTANDARD(" ");
            studentProfile.setSECTION(" ");
            studentProfile.setDOB(" ");
            studentProfile.setBLOODGROUP(" ");
            studentProfile.setADDRESSLINE1(" ");
            studentProfile.setADDRESSLINE2(" ");
            studentProfile.setADDRESSLINE3(" ");
            studentProfile.setADDRESSLINE4(" ");
            studentProfile.setADDRESSLINE5(" ");
            studentProfile.setNOTES(" ");
            studentProfile.setMAKER_ID(" ");
            studentProfile.setCHECKER_ID(" ");
            studentProfile.setMAKER_DATE_STAMP(" ");
            studentProfile.setCHECKER_DATE_STAMP(" ");
            studentProfile.setRECORD_STATUS(" ");
            studentProfile.setAUTH_STATUS(" ");
            studentProfile.setVERSION_NUMBER(" ");
            studentProfile.setMAKER_REMARKS(" ");
            studentProfile.setCHECKER_REMARKS(" "); 
            studentProfile.setEXISTING_MEDICAL_DETAIL(" ");
            dataset.add(studentProfile);
        
        }
       
        dbg("end  SVW_STUDENT_PROFILE_BUSINESS_DATASET--->getTableObject",session); 
      return dataset;
    }catch(DBProcessingException ex){
          dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex,session);
          throw ex;
     }catch(Exception ex){
         dbg(ex,session);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
        
    }
    
    
    
    
    private ArrayList<SVW_STUDENT_PROFILE_BUSINESS> convertDBtoReportObject(List<DBRecord>p_studentProfileList,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<SVW_STUDENT_PROFILE_BUSINESS>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_STUDENT_PROFILE_BUSINESS convertDBtoReportObject",session);
            
            dbg("p_studentProfileList size"+p_studentProfileList.size(),session);
            
            if(!(p_studentProfileList.isEmpty())){
                
                 dbg("p_studentProfileList is not empty",session);
                
                 for(int i=0;i<p_studentProfileList.size();i++){   
                    
                    DBRecord l_profileRecords=p_studentProfileList.get(i);
                    SVW_STUDENT_PROFILE_BUSINESS studentProfile=new SVW_STUDENT_PROFILE_BUSINESS();
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
                    String imagePath;
                    if(session.getCohesiveproperties().getProperty("TEST").equals("YES"))
                            
                     imagePath="https://cohesivetest.ibdtechnologies.com"+l_profileRecords.getRecord().get(11).trim();
                    
                    else
                        
                     imagePath="https://cohesive.ibdtechnologies.com"+l_profileRecords.getRecord().get(11).trim();
                        
                        
                    studentProfile.setIMAGE_PATH(imagePath);
                    dbg("image path"+studentProfile.getIMAGE_PATH(),session);
                    studentProfile.setNOTES(l_profileRecords.getRecord().get(12).trim());
                    studentProfile.setMAKER_ID(l_profileRecords.getRecord().get(13).trim());
                    studentProfile.setCHECKER_ID(l_profileRecords.getRecord().get(14).trim());
                    studentProfile.setMAKER_DATE_STAMP(l_profileRecords.getRecord().get(15).trim());
                    studentProfile.setCHECKER_DATE_STAMP(l_profileRecords.getRecord().get(16).trim());
                    studentProfile.setRECORD_STATUS(l_profileRecords.getRecord().get(17).trim());
                    studentProfile.setAUTH_STATUS(l_profileRecords.getRecord().get(18).trim());
                    studentProfile.setVERSION_NUMBER(l_profileRecords.getRecord().get(19).trim());
                    studentProfile.setMAKER_REMARKS(l_profileRecords.getRecord().get(20).trim());
                    studentProfile.setCHECKER_REMARKS(l_profileRecords.getRecord().get(21).trim()); 
                    studentProfile.setEXISTING_MEDICAL_DETAIL(l_profileRecords.getRecord().get(22).trim());
              

                    dataset.add(studentProfile);
                    
                }
            
            }
            dbg("end of SVW_STUDENT_PROFILE_BUSINESS_DATASET convertDBtoReportObject",session);
            
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
