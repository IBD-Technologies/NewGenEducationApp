/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.institute;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_MARK_REPORT;
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
 * @author DELL
 */
public class CLASS_MARK_REPORT_DATASET {
     public ArrayList<CLASS_MARK_REPORT> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject,String userID)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside CLASS_MARK_REPORT_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        ArrayList<CLASS_MARK_REPORT>dataset=new ArrayList();
        
        
        try{
            
            Map<String,DBRecord>l_studentProfileMap=readBuffer.readTable("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+userID+i_db_properties.getProperty("FOLDER_DELIMITER")+"REPORT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport","REPORT", "CLASS_MARK_REPORT", session, dbSession);
            
            
            dataset=this.convertDBtoReportObject(l_studentProfileMap, session,dbSession,appInject,p_instanceID);
            
        }catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
               
            }else{
                
                throw ex;
            }
            
            
        }
        
        if(dataset.isEmpty()){

        
            CLASS_MARK_REPORT studentProfile=new CLASS_MARK_REPORT();
            studentProfile.setStandard(" ");
            studentProfile.setSection(" ");
            studentProfile.setSubjectID(" ");
            studentProfile.setSubjectName(" ");
            studentProfile.setExam(" ");
            studentProfile.setTopMark(" ");
            studentProfile.setLowMark(" ");
            studentProfile.setAveragreMark(" ");
   
            dataset.add(studentProfile);
        
        }
       
        dbg("end  CLASS_MARK_REPORT_DATASET--->getTableObject",session); 
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
    
    
    
    
    private ArrayList<CLASS_MARK_REPORT> convertDBtoReportObject(Map<String,DBRecord>p_studentProfileList,CohesiveSession session,DBSession dbSession,AppDependencyInjection appInject,String p_instanceID)throws DBProcessingException{
    
        ArrayList<CLASS_MARK_REPORT>dataset=new ArrayList();
        try{
            
            
            dbg("inside CLASS_MARK_REPORT convertDBtoReportObject",session);
            BusinessService bs=appInject.getBusinessService(session);
            
            dbg("p_studentProfileList size"+p_studentProfileList.size(),session);
            
            if(!(p_studentProfileList.isEmpty())){
                
                 dbg("p_studentProfileList is not empty",session);
                
                 int i=0;
                 Iterator<DBRecord>valueIterator=p_studentProfileList.values().iterator();
                 while(valueIterator.hasNext()){
                    
                    DBRecord l_profileRecords=valueIterator.next();
                    CLASS_MARK_REPORT studentProfile=new CLASS_MARK_REPORT();
                    studentProfile.setStandard(l_profileRecords.getRecord().get(0).trim());
                    studentProfile.setSection(l_profileRecords.getRecord().get(1).trim());
                    studentProfile.setSubjectID(l_profileRecords.getRecord().get(2).trim());
                    studentProfile.setSubjectName(bs.getSubjectName(studentProfile.getSubjectID(), p_instanceID, session, dbSession, appInject));
                    studentProfile.setExam(l_profileRecords.getRecord().get(3).trim());
                    studentProfile.setAveragreMark(l_profileRecords.getRecord().get(4).trim());
                    studentProfile.setTopMark(l_profileRecords.getRecord().get(5).trim());
                    studentProfile.setLowMark(l_profileRecords.getRecord().get(6).trim());
                    
                    dataset.add(studentProfile);
                    
                }
            
            }
            dbg("end of CLASS_MARK_REPORT_DATASET convertDBtoReportObject",session);
            
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
