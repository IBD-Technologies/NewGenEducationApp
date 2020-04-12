/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.classEntity;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_EXAM_RANK;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author DELL
 */
public class CLASS_EXAM_RANK_DATASET {
    public ArrayList<CLASS_EXAM_RANK> getTableObject(String p_standard,String p_section,String p_instanceID,String p_exam,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        ArrayList<CLASS_EXAM_RANK>totalRankList=new ArrayList();
        
        try{
        
        dbg("inside CLASS_EXAM_RANK_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
//        BusinessService bs=appInject.getBusinessService(session);
//        ArrayList<String>completedExams=bs.getCompletedExams(p_instanceID, p_standard, p_section, session, dbSession, appInject);
//        
        
//        for(int i=0;i<completedExams.size();i++){
//        
//           String exam= completedExams.get(i);
           Map<String,DBRecord>rankMap=null;
           
           try{
           
        
                rankMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+p_exam+"_"+"Rank", "CLASS", "CLASS_EXAM_RANK", session, dbSession);
        
           
           }catch(DBValidationException ex){
            
                if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){

                   session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                   session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                }else {
                    
                    throw ex;
                }
            
            
            }
           
           if(rankMap!=null){
           
           
               ArrayList<CLASS_EXAM_RANK>rankList= convertDBtoReportObject(rankMap,session) ;

               rankList.forEach((rankList1) -> {
                   totalRankList.add(rankList1);
                });
        
        
           
//           }
        }
        
        
        if(totalRankList.isEmpty()){
            
            CLASS_EXAM_RANK classAssignment=new CLASS_EXAM_RANK();
            classAssignment.setSTUDENT_ID(" ");
            classAssignment.setEXAM(" ");
            classAssignment.setTOTAL(" ");
            classAssignment.setRANK(" ");
            totalRankList.add(classAssignment);
            
            
        }
        
       
         dbg("end of CLASS_EXAM_RANK_DATASET--->getTableObject",session);  
    
         return totalRankList;
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
    
    
    
    
    private ArrayList<CLASS_EXAM_RANK> convertDBtoReportObject(Map<String,DBRecord>rankMap,CohesiveSession session)throws DBProcessingException{
    
         ArrayList<CLASS_EXAM_RANK>dataset=new ArrayList();
        try{
            
        
            
            dbg("inside CLASS_EXAM_RANK_DATASET convertDBtoReportObject",session);
            
            if(rankMap!=null&&!rankMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=rankMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord markRecords=recordIterator.next();
                    CLASS_EXAM_RANK classAssignment=new CLASS_EXAM_RANK();
                    classAssignment.setSTUDENT_ID(markRecords.getRecord().get(0).trim());
                    classAssignment.setEXAM(markRecords.getRecord().get(1).trim());
                    classAssignment.setTOTAL(markRecords.getRecord().get(2).trim());
                    classAssignment.setRANK(markRecords.getRecord().get(3).trim());
                    dataset.add(classAssignment);
                    
                }
            
            }
            dbg("end of CLASS_EXAM_RANK_DATASET convertDBtoReportObject",session);
            
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