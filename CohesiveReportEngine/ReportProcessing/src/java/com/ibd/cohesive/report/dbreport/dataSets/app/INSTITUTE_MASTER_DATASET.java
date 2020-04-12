/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.app;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.app.INSTITUTE_MASTER;
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
public class INSTITUTE_MASTER_DATASET {
//     ArrayList<INSTITUTE_MASTER> dataset;
    
    
    public ArrayList<INSTITUTE_MASTER> getTableObject(String p_instituteID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,String businessReport)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside INSTITUTE_MASTER_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
        Map<String,DBRecord>l_instituteMasterMap=null;
        try
        {
        l_instituteMasterMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Institute","INSTITUTE", "INSTITUTE_MASTER", session, dbSession,ismaxVersionRequired);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
         
        
         dbg("end of INSTITUTE_MASTER_DATASET--->getTableObject",session); 
         dbg("l_instituteMasterMap size"+l_instituteMasterMap,session);
        return   convertDBtoReportObject(l_instituteMasterMap,session,businessReport,p_instituteID) ;

    
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
    
    
    
    
    private ArrayList<INSTITUTE_MASTER> convertDBtoReportObject(Map<String,DBRecord>p_instituteMasterMap,CohesiveSession session,String businessReport,String instituteID)throws DBProcessingException{
    
        ArrayList<INSTITUTE_MASTER>dataset=new ArrayList();
        try{
            
            
            dbg("inside INSTITUTE_MASTER_DATASET convertDBtoReportObject",session);
            
            if(p_instituteMasterMap!=null&&!p_instituteMasterMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_instituteMasterMap.values().iterator();
                
  
                
                while(recordIterator.hasNext()){
                    
                    
                    
                    DBRecord l_instituteTypeRecords=recordIterator.next();
                    INSTITUTE_MASTER institute=new INSTITUTE_MASTER();
                 
                    
                    if(businessReport==null||businessReport.isEmpty()){
                        
                        if(instituteID.equals(l_instituteTypeRecords.getRecord().get(0).trim())){
                            
                            institute.setINSTITUTE_ID(l_instituteTypeRecords.getRecord().get(0).trim());
                           institute.setINSTITUTE_NAME(l_instituteTypeRecords.getRecord().get(1).trim());
                            String imagePath;

                    
                    if(session.getCohesiveproperties().getProperty("TEST").equals("YES"))
                        
                        imagePath="https://cohesivetest.ibdtechnologies.com/"+l_instituteTypeRecords.getRecord().get(2).trim();
                    else
                        
                         imagePath="https://cohesive.ibdtechnologies.com/"+l_instituteTypeRecords.getRecord().get(2).trim();
                    
                    institute.setIMAGE_PATH(imagePath);
                    dataset=null;
                    dataset=new ArrayList();
                    dataset.add(institute);
                    dbg("dataset size"+dataset.size(),session);
            dbg("end of INSTITUTE_MASTER_DATASET convertDBtoReportObject",session);
                         return dataset;
                            
                        }
                        
                         
                    }else{
                    
                    institute.setINSTITUTE_ID(l_instituteTypeRecords.getRecord().get(0).trim());
                    institute.setINSTITUTE_NAME(l_instituteTypeRecords.getRecord().get(1).trim());
                    String imagePath;

                    
                    if(session.getCohesiveproperties().getProperty("TEST").equals("YES"))
                        
                        imagePath="https://cohesivetest.ibdtechnologies.com/"+l_instituteTypeRecords.getRecord().get(2).trim();
                    else
                        
                         imagePath="https://cohesive.ibdtechnologies.com/"+l_instituteTypeRecords.getRecord().get(2).trim();
                    
                    institute.setIMAGE_PATH(imagePath);
                     dataset.add(institute);
                    }
                    
                    
                    
                    
                   
                    
                }
            
            }
            
            
                 else
            {
                INSTITUTE_MASTER service=new INSTITUTE_MASTER();
                 
                    service.setINSTITUTE_ID(" ");
                    service.setINSTITUTE_NAME(" ");
                                            
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            
            
            
            
            dbg("dataset size"+dataset.size(),session);
            dbg("end of INSTITUTE_MASTER_DATASET convertDBtoReportObject",session);
            
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
