/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.student;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.student.SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
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
public class SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS_DATASET {
    public ArrayList<SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        ArrayList<SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS>dataSet=new ArrayList();
        
        try{
        
        dbg("inside SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IPDataService pds=inject.getPdataservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        Map<String,DBRecord>l_studentTimetabledetailMap=null;
         boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
        try{
        
            String[] pkey={p_studentID};
            ArrayList<String>studentMaster=pds.readRecordPData(session, dbSession, "INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID, "INSTITUTE", "IVW_STUDENT_MASTER", pkey);
            String standard=studentMaster.get(2).trim();
            String section=studentMaster.get(3).trim();

            l_studentTimetabledetailMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS","CLASS_TIMETABLE_DETAIL", session, dbSession);

            Map<String,DBRecord>l_studentTimetableMasterMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS", "CLASS_TIMETABLE_MASTER", session, dbSession,ismaxVersionRequired); 

            Map<String,List<DBRecord>>authorizedClasses= l_studentTimetableMasterMap.values().stream().filter(rec->rec.getRecord().get(6).trim().equals("O")&&rec.getRecord().get(7).trim().equals("A")).collect(Collectors.groupingBy(rec->rec.getRecord().get(0).trim()+rec.getRecord().get(1).trim()));

            dataSet=   convertDBtoReportObject(l_studentTimetabledetailMap,session,authorizedClasses,inject,dbSession,p_instanceID,p_studentID,appInject,standard,section) ;
        
        }catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
               
            }else{
                
                throw ex;
            }
            
            
        }
        
        
        if(dataSet.isEmpty()){
            
            SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS studentTimeTable=new SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS();
            studentTimeTable.setDAY(" ");
            studentTimeTable.setEndTime(" ");
            studentTimeTable.setPERIOD_NO(" ");
            studentTimeTable.setSTUDENT_ID(" ");
            studentTimeTable.setSUBJECT_ID(" ");
            studentTimeTable.setStartTime(" ");
            studentTimeTable.setTEACHER_ID(" ");
            studentTimeTable.setVERSION_NUMBER(" ");
            dataSet.add(studentTimeTable);
        }
        
        
        
       
     dbg("end of SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS_DATASET--->getTableObject",session); 
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
    
    
    
    
    private ArrayList<SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS> convertDBtoReportObject(Map<String,DBRecord>p_studentTimeTableMap,CohesiveSession session,Map<String,List<DBRecord>>authorizedClasses,ReportDependencyInjection inject,DBSession dbSession,String instituteID,String p_studentID,AppDependencyInjection appInject,String standard,String section)throws DBProcessingException{
    
        ArrayList<SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS convertDBtoReportObject",session);
            IPDataService pds=inject.getPdataservice();
            BusinessService bs=appInject.getBusinessService(session);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            Map<Integer,ArrayList<SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS>>dayWiseMap=new HashMap();
            
            
            if(!(p_studentTimeTableMap.isEmpty())){
                
             
                Iterator<DBRecord> recordIterator=p_studentTimeTableMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_timetableRecords=recordIterator.next();
                    
                    
                    if(authorizedClasses.containsKey(standard+section)){
                    
                    
                        SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS studentTimeTable=new SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS();
                        studentTimeTable.setSTUDENT_ID(p_studentID);
                        studentTimeTable.setDAY(l_timetableRecords.getRecord().get(2).trim());
                        Integer day=Integer.parseInt(bs.getDayNumber(l_timetableRecords.getRecord().get(2).trim()));
                        studentTimeTable.setPERIOD_NO(l_timetableRecords.getRecord().get(3).trim());
                        String subjectID=l_timetableRecords.getRecord().get(4).trim();
                        String subjectName=bs.getSubjectName(subjectID, instituteID, session, dbSession, appInject);
                        studentTimeTable.setSUBJECT_ID(subjectName);
                        String teacherID=l_timetableRecords.getRecord().get(5).trim();
                        String teacherName=bs.getTeacherName(teacherID, instituteID, session, dbSession, appInject);
                        studentTimeTable.setTEACHER_ID(teacherName);
                        studentTimeTable.setVERSION_NUMBER(l_timetableRecords.getRecord().get(6).trim());
                        String periodNumber=l_timetableRecords.getRecord().get(3).trim();
                        String[] l_pkey={instituteID,standard,section,periodNumber};


                        ArrayList<String>periodMaster=pds.readRecordPData(session, dbSession, "INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID, "INSTITUTE", "IVW_PERIOD_MASTER", l_pkey);

                        String startTimeHour=periodMaster.get(4).trim();
                        String startTimeMin=periodMaster.get(5).trim();
                        String endTimeHour=periodMaster.get(6).trim();
                        String endTimeMin=periodMaster.get(7).trim();

                        String startTime=startTimeHour+":"+startTimeMin;
                        String endTime=endTimeHour+":"+endTimeMin;
                        studentTimeTable.setStartTime(startTime);
                        studentTimeTable.setEndTime(endTime);

                        dbg("studentID"+studentTimeTable.getSTUDENT_ID(),session);
                        dbg("day"+studentTimeTable.getDAY(),session);
                        dbg("periodNo"+studentTimeTable.getPERIOD_NO(),session);
                        dbg("subjectID"+studentTimeTable.getSUBJECT_ID(),session);
                        dbg("teacherID"+studentTimeTable.getTEACHER_ID(),session);
                        dbg("versionNo"+studentTimeTable.getVERSION_NUMBER(),session);
                        dbg("startTime"+startTime,session);
                        dbg("endTime"+endTime,session);

                       if(dayWiseMap.containsKey(day)){

                           dayWiseMap.get(day).add(studentTimeTable);
                       }else{
                           dayWiseMap.put(day, new ArrayList());
                           dayWiseMap.get(day).add(studentTimeTable);
                       }
//                    dataset.add(studentTimeTable);
                  }
                }
            }
            
          dbg("sorting starts",session);
            List<Integer> sortedDays=new ArrayList(dayWiseMap.keySet());
            Collections.sort(sortedDays);
            for(Integer day : sortedDays) {
                dbg("day"+day,session);
                ArrayList<SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS>timeTableList=dayWiseMap.get(day);
                
                Map<Integer,List<SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS>>periodWiseMap=timeTableList.stream().collect(Collectors.groupingBy(rec->Integer.parseInt(rec.getPERIOD_NO())));
                
                List<Integer> sortedPeriods=new ArrayList(periodWiseMap.keySet());
                
                Collections.sort(sortedPeriods);
                
                for(Integer period:sortedPeriods){
                    
                    dbg("period"+period,session);
                    dbg("periodList size"+periodWiseMap.get(period).size(),session);
                    dataset.add(periodWiseMap.get(period).get(0));
                }
                
            }
            
            
            dbg("end of SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS_DATASET convertDBtoReportObject",session);
            
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
