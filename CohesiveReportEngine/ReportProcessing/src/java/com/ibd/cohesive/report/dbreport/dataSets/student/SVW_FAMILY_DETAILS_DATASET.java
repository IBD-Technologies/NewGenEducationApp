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
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_FAMILY_DETAILS;
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
public class SVW_FAMILY_DETAILS_DATASET {
//    ArrayList<SVW_FAMILY_DETAILS> dataset;
    
    
    public ArrayList<SVW_FAMILY_DETAILS> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside SVW_FAMILY_DETAILS_DATASET--->getTableObject",session);    
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
        
        
        l_contactPersonMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID,"STUDENT", "SVW_FAMILY_DETAILS", session, dbSession,ismaxVersionRequired);
         }
           catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of SVW_FAMILY_DETAILS_DATASET--->getTableObject",session);  
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
    
    
    
    
    private ArrayList<SVW_FAMILY_DETAILS> convertDBtoReportObject(Map<String,DBRecord>p_familyDetailsMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<SVW_FAMILY_DETAILS>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_FAMILY_DETAILS_DATASET convertDBtoReportObject",session);
            
            if(p_familyDetailsMap!=null&&!p_familyDetailsMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_familyDetailsMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_familyRecords=recordIterator.next();
                    SVW_FAMILY_DETAILS familyDetailTable=new SVW_FAMILY_DETAILS();
                    
                    familyDetailTable.setSTUDENT_ID(l_familyRecords.getRecord().get(0).trim());
                    familyDetailTable.setMEMBER_ID(l_familyRecords.getRecord().get(1).trim());
                    familyDetailTable.setMEMBER_NAME(l_familyRecords.getRecord().get(2).trim());
                    familyDetailTable.setMEMBER_RELATIONSHIP(l_familyRecords.getRecord().get(3).trim());
                    familyDetailTable.setMEMBER_OCCUPATION(l_familyRecords.getRecord().get(4).trim());
                    familyDetailTable.setMEMBER_EMAILID(l_familyRecords.getRecord().get(5).trim());
                    familyDetailTable.setMEMBER_CONTACTNO(l_familyRecords.getRecord().get(6).trim());
//                    familyDetailTable.setIMAGE_PATH(l_familyRecords.getRecord().get(7).trim());
                    familyDetailTable.setIMAGE_PATH("file:///D:/ARCH_HOME/INSTITUTE/I001/STUDENT/S009/img/profile.jpg");
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
                else
            {
                SVW_FAMILY_DETAILS service=new SVW_FAMILY_DETAILS();
                 
                    service.setSTUDENT_ID(" ");
                    service.setMEMBER_ID(" ");
                    service.setMEMBER_NAME(" ");
                    service.setMEMBER_RELATIONSHIP(" ");
                    service.setMEMBER_OCCUPATION(" ");
                    service.setMEMBER_EMAILID(" ");
                    service.setMEMBER_CONTACTNO(" ");
                    service.setIMAGE_PATH(" ");
                    service.setVERSION_NUMBER(" ");
                                                      
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            dbg("end of SVW_FAMILY_DETAILS_DATASET convertDBtoReportObject",session);
            
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
