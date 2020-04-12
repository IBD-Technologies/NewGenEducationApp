/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.datSources.dataModels.classEntity;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class ClassAttendanceSummary {
    String year;
    String Month;
    String standard;
    String section;
    String presentPercentage;
    String absentPercentage;
    String leavePercentage;
    
    String presentAverage;
    String absentAverage;
    String leaveAverage;
    String monthString;

    public String getMonthString() {
        return monthString;
    }

    public void setMonthString(String monthString) {
        this.monthString = monthString;
    }
    
    

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String Month) {
        this.Month = Month;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getPresentPercentage() {
        return presentPercentage;
    }

    public void setPresentPercentage(String presentPercentage) {
        this.presentPercentage = presentPercentage;
    }

    public String getAbsentPercentage() {
        return absentPercentage;
    }

    public void setAbsentPercentage(String absentPercentage) {
        this.absentPercentage = absentPercentage;
    }

    public String getLeavePercentage() {
        return leavePercentage;
    }

    public void setLeavePercentage(String leavePercentage) {
        this.leavePercentage = leavePercentage;
    }

    public String getPresentAverage() {
        return presentAverage;
    }

    public void setPresentAverage(String presentAverage) {
        this.presentAverage = presentAverage;
    }

    public String getAbsentAverage() {
        return absentAverage;
    }

    public void setAbsentAverage(String absentAverage) {
        this.absentAverage = absentAverage;
    }

    public String getLeaveAverage() {
        return leaveAverage;
    }

    public void setLeaveAverage(String leaveAverage) {
        this.leaveAverage = leaveAverage;
    }
    
    public ArrayList<ClassAttendanceSummary>convertStringToArrayList(String result){
        
          ArrayList<ClassAttendanceSummary> ClassAttendanceSummaryList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              ClassAttendanceSummary appStatus=new ClassAttendanceSummary();
              
              appStatus.setAbsentAverage(record.split("~")[0]);
              appStatus.setAbsentPercentage(record.split("~")[1]);
              appStatus.setLeaveAverage(record.split("~")[2]);
              appStatus.setLeavePercentage(record.split("~")[3]);
              appStatus.setMonth(record.split("~")[4]);
              appStatus.setPresentAverage(record.split("~")[5]);
              appStatus.setPresentPercentage(record.split("~")[6]);
              appStatus.setSection(record.split("~")[7]);
              appStatus.setStandard(record.split("~")[8]);
              appStatus.setYear(record.split("~")[9]);
              appStatus.setMonthString(record.split("~")[10]);
              
           ClassAttendanceSummaryList.add(appStatus);
          }
          
        return ClassAttendanceSummaryList;
           
      
}
}
