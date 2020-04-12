/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.batch;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author ibdtech
 */
public class TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY_DATASET {
    public ArrayList<TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY> getTableObject(String p_businessDate,String p_instituteID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        DirectoryStream<Path> stream =null;
        ArrayList<TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY>totalEodList=new ArrayList();
        try{
        
        dbg("inside TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        boolean ismaxVersionRequired=false;
        String fileExtension=i_db_properties.getProperty("FILE_EXTENSION");
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
        
        Map<String,DBRecord>l_tableMap=null; 
        
         Path ArchFolderPath=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular"+i_db_properties.getProperty("FOLDER_DELIMITER"));
        if(Files.notExists(ArchFolderPath)){
                Files.createDirectories(ArchFolderPath);
            }
         

             
            stream = Files.newDirectoryStream(ArchFolderPath);
             
             
            for (Path file: stream) { 

                
            try{    
                
                 if(file.getFileName().toString().endsWith(fileExtension)){

                    String fileNameWithExtension= file.getFileName().toString();
                    String fileNameWithoutExtension=fileNameWithExtension.substring(0, fileNameWithExtension.indexOf(".")) ;
                    l_tableMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ECircular"+i_db_properties.getProperty("FOLDER_DELIMITER")+fileNameWithoutExtension, "BATCH", "TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY", session, dbSession,ismaxVersionRequired);
                    ArrayList<TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY>eodList=convertDBtoReportObject(l_tableMap,session);

                    for(int i=0;i<eodList.size();i++){


                        totalEodList.add(eodList.get(i));
                    }
                 }
             
             
             }catch(DBValidationException ex){
            
                if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){

                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");


                }else{

                    throw ex;
                }
            
            
             } 
             
            }
             
             
                    
         if(totalEodList.isEmpty()){
             
             dbg("student otherActivity eod status history empty",session);
              ArrayList<TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY>dataset=new ArrayList();
                TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY appEod=new TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY();
                appEod.setINSTITUTE_ID(" ");
                appEod.setE_CIRCULAR_ID(" ");
                appEod.setTEACHER_ID(" ");
                appEod.setBUSINESS_DATE(" ");
                appEod.setSTATUS(" ");
                appEod.setERROR(" ");
                appEod.setSTART_TIME(" ");
                appEod.setEND_TIME(" ");
                appEod.setSEQUENCE_NO(" ");
                
                dataset.add(appEod);
                
                return dataset;
         }
         
         
         
         
         
         
         
         
         
         dbg("end of TEACHER_OTHER_ACTIVITY_EOD_STATUS_DATASET--->getTableObject",session);  
        return   totalEodList ;

    
    }catch(DBProcessingException ex){
          dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex,session);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }finally{
            
           try{ 
                if(stream!=null){
                    stream.close();
                }
                
           }catch(Exception ex){
             throw new DBProcessingException("DBProcessingException"+ex.toString());
           }
        }
        
        
    }
    
    
    
    
    private ArrayList<TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY> convertDBtoReportObject(Map<String,DBRecord>l_tableMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY>dataset=new ArrayList();
        try{
            
            
            dbg("inside TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY_DATASET convertDBtoReportObject",session);
            
            if(l_tableMap!=null&&!l_tableMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=l_tableMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_eodRecords=recordIterator.next();
                    TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY eod=new TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY();
                    
                    eod.setINSTITUTE_ID(l_eodRecords.getRecord().get(0).trim());
                    eod.setE_CIRCULAR_ID(l_eodRecords.getRecord().get(1).trim());
                    eod.setTEACHER_ID(l_eodRecords.getRecord().get(2).trim());
                    eod.setBUSINESS_DATE(l_eodRecords.getRecord().get(3).trim());
                    eod.setSTATUS(l_eodRecords.getRecord().get(4).trim());
                    eod.setERROR(l_eodRecords.getRecord().get(5).trim());
                    eod.setSTART_TIME(l_eodRecords.getRecord().get(6).trim());
                    eod.setEND_TIME(l_eodRecords.getRecord().get(7).trim());
                    eod.setSEQUENCE_NO(l_eodRecords.getRecord().get(8).trim());
 
                    dataset.add(eod);
                    
                }
            
            }
            dbg("end of TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY_DATASET convertDBtoReportObject",session);
            
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
