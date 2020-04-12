/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.classEntity;

import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassAttendanceDetail;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassAttendanceSummary;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.util.ReportUtil;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author IBD Technologies
 */
public class ClassAttendanceSummary_DataSet {
    
     public ArrayList<ClassAttendanceSummary> getClassAttendanceSummaryObject(String p_standard,String p_section,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        
        ArrayList<ClassAttendanceSummary>dataset=new ArrayList();
        try{
        
        dbg("inside ClassAttendance_DataSet--->getTableObject",session);  
//        ClassDataSetBusiness classDataSetBusiness= inject.getClassDatasetBusiness();
        ClassAttendanceDetail_DataSet classAttendance=inject.getClassAttendanceBusinessDataSet();
        ReportUtil reportUtil=inject.getReportUtil(session);
        
        DecimalFormat df = new DecimalFormat("###.##");
        ArrayList<ClassAttendanceDetail>classAttendanceDetail=  classAttendance.getClassAttendanceDetailObject(p_standard,p_section, p_instanceID, session, dbSession, inject,appInject);
        
        Map<String,List<ClassAttendanceDetail>>yearAndMonthWiseMap=classAttendanceDetail.stream().collect(Collectors.groupingBy(rec->rec.getYear()+"y"+rec.getMonth()));
        dbg("yearAndMonthWiseMap"+yearAndMonthWiseMap.size(),session); 
        Iterator<String>yearMonthIterator=yearAndMonthWiseMap.keySet().iterator();
        
         while(yearMonthIterator.hasNext()){
             ClassAttendanceSummary summary=new ClassAttendanceSummary();
             String yearAndMonth=yearMonthIterator.next();
             String year=yearAndMonth.split("y")[0];
             String month=yearAndMonth.split("y")[1];
             dbg("year"+year,session);
             dbg("month"+month,session);
             summary.setYear(year);
             summary.setMonth(month);
             summary.setMonthString(reportUtil.getMonthValueInString(month));
             
             summary.setStandard(p_standard);
             summary.setSection(p_section);
             
             List<ClassAttendanceDetail>yearMonthList=yearAndMonthWiseMap.get(yearAndMonth);
             dbg("yearMonthList size"+yearMonthList.size(),session);
             
             Double averagePresentDays=yearMonthList.stream().mapToDouble(rec->Double.parseDouble(rec.getNo_of_DaysPresent())).average().getAsDouble();
           
             summary.setPresentAverage(df.format(Math.round(averagePresentDays)));
             Double averageAbsentDays=yearMonthList.stream().mapToDouble(rec->Double.parseDouble(rec.getNo_of_DaysAbsent())).average().getAsDouble();
             summary.setAbsentAverage(df.format(Math.round(averageAbsentDays)));
             Double leaveAverage=yearMonthList.stream().mapToDouble(rec->Double.parseDouble(rec.getNo_ofDaysLeave())).average().getAsDouble();
             summary.setLeaveAverage(df.format(Math.round(leaveAverage)));
             
             int noOfWorkingDays=reportUtil.getNo_of_workingDays(year, month);
             
                Double presentPercentage=(averagePresentDays/noOfWorkingDays)*100;
                Double absentPercentage=(averageAbsentDays/noOfWorkingDays)*100;
                Double leavePercentage=(leaveAverage/noOfWorkingDays)*100;
                dbg("presentPercentage"+presentPercentage,session);
                dbg("absentPercentage"+presentPercentage,session);
                dbg("leavePercentage"+presentPercentage,session);
                
                summary.setPresentPercentage(df.format(Math.round(presentPercentage)));
                summary.setAbsentPercentage(df.format(Math.round(absentPercentage)));
                summary.setLeavePercentage(df.format(Math.round(leavePercentage)));
                
                dataset.add(summary);
         
         }
         
         
         if(dataset.isEmpty()){
             
             ClassAttendanceSummary summary=new ClassAttendanceSummary();
             summary.setYear(" ");
             summary.setMonth(" ");
             summary.setStandard(" ");
             summary.setSection(" ");
             summary.setPresentAverage(" ");
             summary.setAbsentAverage(" ");
             summary.setLeaveAverage(" ");
             summary.setPresentAverage(" ");
             summary.setAbsentAverage(" ");
             summary.setLeavePercentage(" ");
         }
         
         
         
        
       dbg("end of ClassAttendance_DataSet--->getTableObject",session);
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
        
       return dataset;
    }
    
    
     
     
      public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}
