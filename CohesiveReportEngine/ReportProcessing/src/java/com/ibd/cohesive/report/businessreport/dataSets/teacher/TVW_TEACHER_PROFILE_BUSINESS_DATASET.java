/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.teacher;

import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.TVW_TEACHER_PROFILE_BUSINESS;
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
public class TVW_TEACHER_PROFILE_BUSINESS_DATASET {
    public ArrayList<TVW_TEACHER_PROFILE_BUSINESS> getTableObject(String p_teacherID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        ArrayList<TVW_TEACHER_PROFILE_BUSINESS>dataSet=new ArrayList();
        
        try{
        
        dbg("inside TVW_TEACHER_PROFILE_BUSINESS_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
        
        try{
        
        
            Map<String,DBRecord>l_profileMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_teacherID,"TEACHER", "TVW_TEACHER_PROFILE", session, dbSession);

            List<DBRecord>filteredList= l_profileMap.values().stream().filter(rec->rec.getRecord().get(17).trim().equals("O")&&rec.getRecord().get(18).trim().equals("A")).collect(Collectors.toList());
            dbg("filteredList size"+filteredList.size(),session);

            dataSet=   convertDBtoReportObject(l_profileMap,session,filteredList,inject,p_instanceID,dbSession) ;
        
        
        }catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
               
            }else{
                
                throw ex;
            }
            
            
        }
        
        
        if(dataSet.isEmpty()){
            
             TVW_TEACHER_PROFILE_BUSINESS teacherProfile=new TVW_TEACHER_PROFILE_BUSINESS();
                    teacherProfile.setTEACHER_ID(" ");
                    teacherProfile.setTEACHER_NAME(" ");
                    teacherProfile.setQUALIFICATION(" ");
                    teacherProfile.setDOB(" ");
                    teacherProfile.setCONTACT_NO(" ");
                    teacherProfile.setEMAIL_ID(" ");
                    teacherProfile.setBLOOD_GROUP(" ");
                    teacherProfile.setADDRESSLINE1(" ");
                    teacherProfile.setADDRESSLINE2(" ");
                    teacherProfile.setADDRESSLINE3(" ");
                    teacherProfile.setADDRESSLINE4(" ");
                    teacherProfile.setADDRESSLINE5(" ");
                    teacherProfile.setIMAGE_PATH(" ");
                    teacherProfile.setMAKER_ID(" ");
                    teacherProfile.setCHECKER_ID(" ");
                    teacherProfile.setMAKER_DATE_STAMP(" ");
                    teacherProfile.setCHECKER_DATE_STAMP(" ");
                    teacherProfile.setRECORD_STATUS(" ");
                    teacherProfile.setAUTH_STATUS(" ");
                    teacherProfile.setVERSION_NUMBER(" ");
                    teacherProfile.setMAKER_REMARKS(" ");
                    teacherProfile.setCHECKER_REMARKS(" "); 
                    teacherProfile.setSHORT_NAME(" "); 
                    teacherProfile.setEXISTING_MEDICAL_DETAIL(" "); 
                    teacherProfile.setCLASS(" ");
                    dataSet.add(teacherProfile);
        }
        
        
        
        
        
        
         dbg("end of TVW_TEACHER_PROFILE_BUSINESS_DATASET--->getTableObject",session);  
         return dataSet;
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
    
    
    
    
    private ArrayList<TVW_TEACHER_PROFILE_BUSINESS> convertDBtoReportObject(Map<String,DBRecord>p_profileMap,CohesiveSession session,List<DBRecord>filteredList,ReportDependencyInjection inject,String p_instituteID,DBSession dbSession)throws DBProcessingException{
    
        ArrayList<TVW_TEACHER_PROFILE_BUSINESS>dataset=new ArrayList();
        try{
            IPDataService pds=inject.getPdataservice();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            
            dbg("inside TVW_TEACHER_PROFILE_BUSINESS_DATASET convertDBtoReportObject",session);
            
            if(!(filteredList.isEmpty())){
                
             
                
                 for(int i=0;i<filteredList.size();i++){   
                    
                    DBRecord l_teacherRecords=filteredList.get(i);
                    TVW_TEACHER_PROFILE_BUSINESS teacherProfile=new TVW_TEACHER_PROFILE_BUSINESS();
                    teacherProfile.setTEACHER_ID(l_teacherRecords.getRecord().get(0).trim());
                    teacherProfile.setTEACHER_NAME(l_teacherRecords.getRecord().get(1).trim());
                    teacherProfile.setQUALIFICATION(l_teacherRecords.getRecord().get(2).trim());
                    teacherProfile.setDOB(l_teacherRecords.getRecord().get(3).trim());
                    teacherProfile.setCONTACT_NO(l_teacherRecords.getRecord().get(4).trim());
                    String emailID=l_teacherRecords.getRecord().get(5).trim();
                    String replacedEmail=emailID.replace("AATT;", "@");
                    teacherProfile.setEMAIL_ID(replacedEmail);
                    teacherProfile.setBLOOD_GROUP(l_teacherRecords.getRecord().get(6).trim());
                    teacherProfile.setADDRESSLINE1(l_teacherRecords.getRecord().get(7).trim());
                    teacherProfile.setADDRESSLINE2(l_teacherRecords.getRecord().get(8).trim());
                    teacherProfile.setADDRESSLINE3(l_teacherRecords.getRecord().get(9).trim());
                    teacherProfile.setADDRESSLINE4(l_teacherRecords.getRecord().get(10).trim());
                    teacherProfile.setADDRESSLINE5(l_teacherRecords.getRecord().get(11).trim());
                    String imagePath;
                    if(session.getCohesiveproperties().getProperty("TEST").equals("YES"))
                     imagePath="https://cohesivetest.ibdtechnologies.com"+l_teacherRecords.getRecord().get(12).trim();
                    else
                     imagePath="https://cohesive.ibdtechnologies.com"+l_teacherRecords.getRecord().get(12).trim();
                      
                    teacherProfile.setIMAGE_PATH(imagePath);
                    dbg("image path"+teacherProfile.getIMAGE_PATH(),session);
                    
                    teacherProfile.setMAKER_ID(l_teacherRecords.getRecord().get(13).trim());
                    teacherProfile.setCHECKER_ID(l_teacherRecords.getRecord().get(14).trim());
                    teacherProfile.setMAKER_DATE_STAMP(l_teacherRecords.getRecord().get(15).trim());
                    teacherProfile.setCHECKER_DATE_STAMP(l_teacherRecords.getRecord().get(16).trim());
                    teacherProfile.setRECORD_STATUS(l_teacherRecords.getRecord().get(17).trim());
                    teacherProfile.setAUTH_STATUS(l_teacherRecords.getRecord().get(18).trim());
                    teacherProfile.setVERSION_NUMBER(l_teacherRecords.getRecord().get(19).trim());
                    teacherProfile.setMAKER_REMARKS(l_teacherRecords.getRecord().get(20).trim());
                    teacherProfile.setCHECKER_REMARKS(l_teacherRecords.getRecord().get(21).trim()); 
                    teacherProfile.setSHORT_NAME(l_teacherRecords.getRecord().get(22).trim()); 
                    teacherProfile.setEXISTING_MEDICAL_DETAIL(l_teacherRecords.getRecord().get(23).trim()); 
                    
                    String classs="";
                    
                    
                    try{

                        Map<String,ArrayList<String>>classMap= pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID, "INSTITUTE", "IVW_STANDARD_MASTER", session, dbSession);

                        List<ArrayList<String>>classList=classMap.values().stream().filter(rec->rec.get(3).trim().equals(teacherProfile.getTEACHER_ID())).collect(Collectors.toList());

                        dbg("classList size"+classList,session);
                        if(classList!=null&&!classList.isEmpty()){

                         classs= classList.get(0).get(1).trim()+"/"+classList.get(0).get(2).trim();

                        }


                    }catch(DBValidationException ex){
                        if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                            }else{

                                throw ex;
                            }
                    }
                    
                    teacherProfile.setCLASS(classs);
                    
                    
                    dbg("teacherID"+teacherProfile.getTEACHER_ID() ,session);
                    dbg("teacherName"+teacherProfile.getTEACHER_NAME() ,session); 
                    dbg("qualification"+teacherProfile.getQUALIFICATION() ,session); 
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
            dbg("end of TVW_TEACHER_PROFILE_BUSINESS_DATASET convertDBtoReportObject",session);
            
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
