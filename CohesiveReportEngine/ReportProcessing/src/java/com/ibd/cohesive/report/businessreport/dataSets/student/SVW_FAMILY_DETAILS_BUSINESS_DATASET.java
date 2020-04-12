/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.student;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.student.SVW_FAMILY_DETAILS_BUSINESS;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author DELL
 */
public class SVW_FAMILY_DETAILS_BUSINESS_DATASET {
    public ArrayList<SVW_FAMILY_DETAILS_BUSINESS> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        ArrayList<SVW_FAMILY_DETAILS_BUSINESS>dataset=new ArrayList();
        
        try{
        dbg("inside SVW_FAMILY_DETAILS_BUSINESS_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();

        try{
        
        
                Map<String,DBRecord>l_contactPersonMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID,"STUDENT", "SVW_FAMILY_DETAILS", session, dbSession);

                Map<String,DBRecord>l_studentProfileMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID,"STUDENT", "SVW_STUDENT_PROFILE", session, dbSession);

                Map<String,List<DBRecord>>authorizedStudents= l_studentProfileMap.values().stream().filter(rec->rec.getRecord().get(17).trim().equals("O")&&rec.getRecord().get(18).trim().equals("A")).collect(Collectors.groupingBy(rec->rec.getRecord().get(0).trim()));

                dataset=convertDBtoReportObject(l_contactPersonMap,session,authorizedStudents);
        
        }catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
               
            }else{
                
                throw ex;
            }
            
            
        }
        
        if(dataset.isEmpty()){
            
             dbg("SVW_FAMILY_DETAILS_BUSINESS-->dataSet is empty",session);
             SVW_FAMILY_DETAILS_BUSINESS appEod=new SVW_FAMILY_DETAILS_BUSINESS();
             appEod.setIMAGE_PATH(" ");
             appEod.setMEMBER_CONTACTNO(" ");
             appEod.setMEMBER_EMAILID(" ");
             appEod.setMEMBER_ID(" ");
             appEod.setMEMBER_NAME(" ");
             appEod.setMEMBER_OCCUPATION(" ");
             appEod.setMEMBER_RELATIONSHIP(" ");
             appEod.setSTUDENT_ID(" ");
             appEod.setVERSION_NUMBER(" ");
                
             dataset.add(appEod);
            
        }
        
        
        
        
         dbg("end of SVW_FAMILY_DETAILS_BUSINESS_DATASET--->getTableObject",session);  
        
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
    
    
    
    
    private ArrayList<SVW_FAMILY_DETAILS_BUSINESS> convertDBtoReportObject(Map<String,DBRecord>p_familyDetailsMap,CohesiveSession session,Map<String,List<DBRecord>>authorizedStudents)throws DBProcessingException{
    
        ArrayList<SVW_FAMILY_DETAILS_BUSINESS>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_FAMILY_DETAILS_BUSINESS_DATASET convertDBtoReportObject",session);
            dbg("p_familyDetailsMap size"+p_familyDetailsMap.size(),session);
            
            if(!(p_familyDetailsMap.isEmpty())){
                
             
                Iterator<DBRecord> recordIterator=p_familyDetailsMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_familyRecords=recordIterator.next();
                    SVW_FAMILY_DETAILS_BUSINESS familyDetailTable=new SVW_FAMILY_DETAILS_BUSINESS();
                    
                    String studentID=l_familyRecords.getRecord().get(0).trim();
                    
                    if(authorizedStudents.containsKey(studentID)){
                        familyDetailTable.setSTUDENT_ID(l_familyRecords.getRecord().get(0).trim());
                        familyDetailTable.setMEMBER_ID(l_familyRecords.getRecord().get(1).trim());
                        familyDetailTable.setMEMBER_NAME(l_familyRecords.getRecord().get(2).trim());
                        familyDetailTable.setMEMBER_RELATIONSHIP(l_familyRecords.getRecord().get(3).trim());
                        familyDetailTable.setMEMBER_OCCUPATION(l_familyRecords.getRecord().get(4).trim());
                        String emailID=l_familyRecords.getRecord().get(5).trim();
                        String replacedEmail=emailID.replace("AATT;", "@");
                        
                        familyDetailTable.setMEMBER_EMAILID(replacedEmail);
                        familyDetailTable.setMEMBER_CONTACTNO(l_familyRecords.getRecord().get(6).trim());
                        String imagePath="https://cohesive.ibdtechnologies.com/"+l_familyRecords.getRecord().get(7).trim();
                        familyDetailTable.setIMAGE_PATH(imagePath);
                        familyDetailTable.setVERSION_NUMBER(l_familyRecords.getRecord().get(8).trim());

                        dbg("studentID"+familyDetailTable.getSTUDENT_ID() ,session);
                        dbg("memberID"+familyDetailTable.getMEMBER_ID(),session);
                        dbg("memberName"+familyDetailTable.getMEMBER_NAME() ,session);
                        dbg("memberRelationship"+familyDetailTable.getMEMBER_RELATIONSHIP() ,session);
                        dbg("memberOccupation "+familyDetailTable.getMEMBER_OCCUPATION() ,session);
                        dbg("memberMailID"+familyDetailTable.getMEMBER_EMAILID() ,session);
                        dbg("member contact no"+familyDetailTable.getMEMBER_CONTACTNO() ,session);
                        dbg("member image path"+ familyDetailTable.getIMAGE_PATH(),session);
                        dbg("member vaersion number"+familyDetailTable.getVERSION_NUMBER() ,session);
                    
                    dataset.add(familyDetailTable);
                    }
                }
            
            }
            dbg("end of SVW_FAMILY_DETAILS_BUSINESS_DATASET convertDBtoReportObject",session);
            
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
