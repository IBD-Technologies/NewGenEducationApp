/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.teacher;

import com.ibd.cohesive.app.business.teacher.teachertimetable.TeacherTimeTable;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.TeacherTimeTableReport;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.util.ReportUtil;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class TeacherTimeTableReport_DataSet {
    public ArrayList<TeacherTimeTableReport> getTeacherTimeTableReport_DataSet(String p_teacherID,String p_instituteID,String p_date,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject,String userID)throws DBProcessingException,DBValidationException{
        
        try{
            
        dbg("inside getTeacherTimeTableReport_DataSet",session);    
            
        
        
        
        
            
            return this.getTimeTableForTheDay(p_teacherID, p_instituteID, p_date, session, dbSession, inject, appInject);
        
        
        
        
            
//         dbg("end of getTeacherTimeTableReport_DaWtaSet",session);
     }catch(DBProcessingException ex){
          dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex,session);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
//       return dataset;
    }
    
    
    
    public ArrayList<TeacherTimeTableReport>getTimeTableForTheDay(String p_teacherID,String p_instituteID,String p_date,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        
        try{
            dbg("inside getTimeTableForTheDay",session);
            dbg("p_date"+p_date,session);
            ReportUtil reportUtil=inject.getReportUtil(session);
            String day=reportUtil.getDay(p_date);
            dbg("day"+day,session);
            ArrayList<TeacherTimeTableReport>timeTableList=new ArrayList();
            IPDataService pds=inject.getPdataservice();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            SubstituteAvailabilityInSameClass_DataSet sameClassObj=inject.getSubstituteAvailabilityInSameClass();
            TeacherTimeTable teacherTT=sameClassObj.teacherTimeTableDBView(p_instituteID, p_teacherID, session, dbSession, inject, appInject);
            dbg("after teacherTimeTableDBView",session);
            
            for(int i=0;i<teacherTT.timeTable.length;i++){
                
                String l_day=teacherTT.timeTable[i].getDay();
                dbg("l_day"+l_day,session);
                
                if(l_day.equals(day)){
                    
                    dbg("l_day equals pday"+l_day,session);
                    
                    for(int j=0;j<teacherTT.timeTable[i].period.length;j++){
                        
                        dbg("inside periodIteration",session);
                        String standard=teacherTT.timeTable[i].period[j].getStandard();
                        String section=teacherTT.timeTable[i].period[j].getSection();
                        String periodNo=teacherTT.timeTable[i].period[j].getPeriodNumber();
                        String subjectName=teacherTT.timeTable[i].period[j].getSubjectName();
                        String[] nextpkey={p_instituteID,standard,section,periodNo};
                        ArrayList<String>nextperiodlist= pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID, "INSTITUTE", "IVW_PERIOD_MASTER",nextpkey);
                        
                        String startTimeHour=nextperiodlist.get(4).trim();
                        String startTimeMin=nextperiodlist.get(5).trim();
                        String startTime=startTimeHour+":"+startTimeMin;
                        
                        String endTimeHour=nextperiodlist.get(6).trim();
                        String endTimeMin=nextperiodlist.get(7).trim();
                        String endTime=endTimeHour+":"+endTimeMin;
                        
                        TeacherTimeTableReport timeTableReport=new TeacherTimeTableReport();
                        timeTableReport.setDAY(l_day);
                        timeTableReport.setEndTime(endTime);
                        timeTableReport.setPERIOD_NO(periodNo);
                        timeTableReport.setSECTION(section);
                        timeTableReport.setSTANDARD(standard);
                        timeTableReport.setSUBJECT_NAME(subjectName);
                        timeTableReport.setStartTime(startTime);
                        timeTableList.add(timeTableReport);
                        
                    }
                    
                    
                    
                    
                }
                
                
            }
            
            
            if(timeTableList.isEmpty()){
                
                
                 TeacherTimeTableReport timeTableReport=new TeacherTimeTableReport();
                 timeTableReport.setDAY(" ");
                 timeTableReport.setEndTime(" ");
                 timeTableReport.setPERIOD_NO(" ");
                 timeTableReport.setSECTION(" ");
                 timeTableReport.setSTANDARD(" ");
                 timeTableReport.setSUBJECT_NAME(" ");
                 timeTableReport.setStartTime(" ");
                 timeTableList.add(timeTableReport);
                
            }
            
            
            dbg("end of getTimeTableForTheDay",session);
            
            return timeTableList;
            
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
    
    
    
    public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
    
    
    
    
    
}
