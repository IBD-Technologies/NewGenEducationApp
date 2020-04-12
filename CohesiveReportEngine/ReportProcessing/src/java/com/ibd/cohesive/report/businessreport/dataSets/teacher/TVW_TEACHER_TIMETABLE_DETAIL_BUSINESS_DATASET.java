/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.teacher;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.util.ReportUtil;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author DELL
 */
public class TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DATASET {
    public ArrayList<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS> getTableObject(String p_teacherID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        ArrayList<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS>dataSet=new ArrayList();
        
        try{
        
        dbg("inside TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
        try{
        
                Map<String,DBRecord>timeTableMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TimeTable","INSTITUTE","IVW_SUBJECT_DETAILS",session, dbSession);
                dbg("timeTableMap size"+timeTableMap.size(),session);
                List<DBRecord>filteredRecords= timeTableMap.values().stream().filter(rec->rec.getRecord().get(5).trim().equals(p_teacherID)).collect(Collectors.toList()); 
                dbg("filteredRecords size"+filteredRecords.size(),session);
                
                dataSet=convertDBtoReportObject(filteredRecords,session,inject,p_instanceID,dbSession,appInject) ;
                dbg("dataSet size"+dataSet.size(),session);
        
        
        }catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
               
            }else{
                
                throw ex;
            }
            
            
        }
        
        
        if(dataSet.isEmpty()){
            
            TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS teacherTimeTable=new TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS();
            teacherTimeTable.setTEACHER_ID(" ");
            teacherTimeTable.setDAY(" ");
            teacherTimeTable.setPERIOD_NO(" ");
            teacherTimeTable.setSUBJECT_ID(" ");
            teacherTimeTable.setSTANDARD(" ");
            teacherTimeTable.setSECTION(" ");
            teacherTimeTable.setStartTime(" ");
            teacherTimeTable.setEndTime(" ");
            dataSet.add(teacherTimeTable);
            
            
        }
        
        
        
        
        
        
        
        dbg("end of TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DATASET--->getTableObject",session);  
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
    
    
    
    
    private ArrayList<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS> convertDBtoReportObject(List<DBRecord> p_timetableList,CohesiveSession session,ReportDependencyInjection inject,String instituteID,DBSession dbSession,AppDependencyInjection appInject)throws DBProcessingException{
    
        ArrayList<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS>dataset=new ArrayList();
        try{
            
            
            dbg("inside TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DATASET convertDBtoReportObject",session);
            dbg("p_timetableList size"+p_timetableList.size(),session);
            ReportUtil reportUtil=inject.getReportUtil(session);
            IPDataService pds=inject.getPdataservice();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            BusinessService bs=appInject.getBusinessService(session);
            Map<Integer,ArrayList<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS>>dayWiseMap=new HashMap();
            if(!(p_timetableList.isEmpty())){
                dbg("p_timetableList not empty",session);
             
                Iterator<DBRecord> recordIterator=p_timetableList.iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_teacherRecords=recordIterator.next();
                    dbg("onside record iteraton",session);
                    
                    TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS teacherTimeTable=new TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS();
                    teacherTimeTable.setTEACHER_ID(l_teacherRecords.getRecord().get(5).trim());
                    dbg("teacherID"+teacherTimeTable.getTEACHER_ID(),session);
                    teacherTimeTable.setDAY(l_teacherRecords.getRecord().get(3).trim());
                    dbg("day"+teacherTimeTable.getDAY(),session);
                    Integer day=reportUtil.getDayNumber(l_teacherRecords.getRecord().get(3).trim());
                    teacherTimeTable.setPERIOD_NO(l_teacherRecords.getRecord().get(2).trim());
                    dbg("periodNo"+teacherTimeTable.getPERIOD_NO(),session);
                    String subjectID=l_teacherRecords.getRecord().get(4).trim();
                    dbg("subjectID"+subjectID,session);
                    String subjectName=bs.getSubjectName(subjectID, instituteID, session, dbSession, appInject);
                    dbg("subjectName"+subjectName,session);
                    teacherTimeTable.setSUBJECT_ID(subjectName);
                    String standard=l_teacherRecords.getRecord().get(0).trim();
                    teacherTimeTable.setSTANDARD(standard);
                    String section=l_teacherRecords.getRecord().get(1).trim();
                    teacherTimeTable.setSECTION(section);
                    dbg("standard"+standard,session);
                    dbg("section"+section,session);
                    String periodNumber=teacherTimeTable.getPERIOD_NO();
                    String[] l_pkey={instituteID,standard,section,periodNumber};
                    ArrayList<String>periodMaster=pds.readRecordPData(session, dbSession, "INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID, "INSTITUTE", "IVW_PERIOD_MASTER", l_pkey);
                    String startTimeHour=periodMaster.get(4).trim();
                    String startTimeMin=periodMaster.get(5).trim();
                    String endTimeHour=periodMaster.get(6).trim();
                    String endTimeMin=periodMaster.get(7).trim();
                    String startTime=startTimeHour+":"+startTimeMin;
                    String endTime=endTimeHour+":"+endTimeMin;
                    dbg("startTime"+startTime,session);
                    dbg("endTime"+endTime,session);
                    
                    teacherTimeTable.setStartTime(startTime);
                    teacherTimeTable.setEndTime(endTime);
                    
                    dbg("teacherID"+teacherTimeTable.getTEACHER_ID(),session);
                    dbg("day"+teacherTimeTable.getDAY(),session);
                    dbg("periodNo"+teacherTimeTable.getPERIOD_NO(),session);
                    dbg("subjectID"+teacherTimeTable.getSUBJECT_ID(),session);
                    dbg("standard"+teacherTimeTable.getSTANDARD(),session);
                    dbg("section"+teacherTimeTable.getSECTION(),session);
                    dbg("startTime"+startTime,session);
                    dbg("endTime"+endTime,session);
                    
                    if(dayWiseMap.containsKey(day)){
                       
                       dayWiseMap.get(day).add(teacherTimeTable);
                   }else{
                       dayWiseMap.put(day, new ArrayList());
                       dayWiseMap.get(day).add(teacherTimeTable);
                   }
//                    dataset.add(teacherTimeTable);
                    }
            }
            
            dbg("sorting starts",session);
            List<Integer> sortedDays=new ArrayList(dayWiseMap.keySet());
            Collections.sort(sortedDays);
            for(Integer day : sortedDays) {
                dbg("day"+day,session);
                ArrayList<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS>timeTableList=dayWiseMap.get(day);
                
                Map<Integer,List<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS>>periodWiseMap=timeTableList.stream().collect(Collectors.groupingBy(rec->Integer.parseInt(rec.getPERIOD_NO())));
                
                List<Integer> sortedPeriods=new ArrayList(periodWiseMap.keySet());
                
                Collections.sort(sortedPeriods);
                
                for(Integer period:sortedPeriods){
                    
                    dbg("period"+period,session);
                    dbg("periodList size"+periodWiseMap.get(period).size(),session);
                    dataset.add(periodWiseMap.get(period).get(0));
                }
                
            }

            dbg("end of TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DATASET convertDBtoReportObject",session);
            
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
