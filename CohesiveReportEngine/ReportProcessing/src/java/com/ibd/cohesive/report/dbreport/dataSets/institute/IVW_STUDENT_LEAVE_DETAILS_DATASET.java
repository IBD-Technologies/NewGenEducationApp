/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.institute;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.app.CONTRACT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_STUDENT_LEAVE_DETAILS;
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
public class IVW_STUDENT_LEAVE_DETAILS_DATASET {
    
    public ArrayList<IVW_STUDENT_LEAVE_DETAILS> getTableObject(String p_instanceID,String p_date,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside IVW_STUDENT_LEAVE_DETAILS_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
//        BusinessService bs=appInject.getBusinessService(session);
//        String currentDate=bs.getCurrentDate();
//        
          Map<String,DBRecord>l_contentTypeMap=null;
          try
          {
        l_contentTypeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_date,"INSTITUTE", "IVW_STUDENT_LEAVE_DETAILS", session, dbSession);
          }
            catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
        
         dbg("end of IVW_STUDENT_LEAVE_DETAILS_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_contentTypeMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<IVW_STUDENT_LEAVE_DETAILS> convertDBtoReportObject(Map<String,DBRecord>p_contentTypeMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<IVW_STUDENT_LEAVE_DETAILS>dataset=new ArrayList();
        try{
            
            
            dbg("inside IVW_STUDENT_LEAVE_DETAILS_DATASET convertDBtoReportObject",session);
            
            if(p_contentTypeMap!=null&&!p_contentTypeMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_contentTypeMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_contentTypeRecords=recordIterator.next();
                    IVW_STUDENT_LEAVE_DETAILS contentType=new IVW_STUDENT_LEAVE_DETAILS();
                 
                    contentType.setSTUDENT_ID(l_contentTypeRecords.getRecord().get(0).trim());
                    contentType.setSTANDARD(l_contentTypeRecords.getRecord().get(1).trim());
                    contentType.setSECTION(l_contentTypeRecords.getRecord().get(2).trim());
                    contentType.setFULL_DAY(l_contentTypeRecords.getRecord().get(3).trim());
                    contentType.setNOON(l_contentTypeRecords.getRecord().get(4).trim());
                    
                    
                    
                    

                    dataset.add(contentType);
                    
                }
            
            }
            
                else
            {
                IVW_STUDENT_LEAVE_DETAILS service=new IVW_STUDENT_LEAVE_DETAILS();
                 
                    service.setSTUDENT_ID(" ");
                    service.setSTANDARD(" ");
                    service.setSECTION(" ");
                    service.setFULL_DAY(" ");
                    service.setNOON(" ");
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            
            dbg("end of IVW_STUDENT_LEAVE_DETAILS_DATASET convertDBtoReportObject",session);
            
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
