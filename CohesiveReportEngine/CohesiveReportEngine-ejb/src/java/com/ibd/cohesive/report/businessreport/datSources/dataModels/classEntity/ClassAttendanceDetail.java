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
public class ClassAttendanceDetail {
    
    String year;
    String Month;
    String standard;
    String section;
    String studentID;
    String no_of_DaysPresent;
    String no_of_DaysAbsent;
    String no_ofDaysLeave;
    String no_of_workingDays;
    String percentage;
    String studentName;
    String monthString;

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

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getNo_of_DaysPresent() {
        return no_of_DaysPresent;
    }

    public void setNo_of_DaysPresent(String no_of_DaysPresent) {
        this.no_of_DaysPresent = no_of_DaysPresent;
    }

    public String getNo_of_DaysAbsent() {
        return no_of_DaysAbsent;
    }

    public void setNo_of_DaysAbsent(String no_of_DaysAbsent) {
        this.no_of_DaysAbsent = no_of_DaysAbsent;
    }

    public String getNo_ofDaysLeave() {
        return no_ofDaysLeave;
    }

    public void setNo_ofDaysLeave(String no_ofDaysLeave) {
        this.no_ofDaysLeave = no_ofDaysLeave;
    }

    public String getNo_of_workingDays() {
        return no_of_workingDays;
    }

    public void setNo_of_workingDays(String no_of_workingDays) {
        this.no_of_workingDays = no_of_workingDays;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMonthString() {
        return monthString;
    }

    public void setMonthString(String monthString) {
        this.monthString = monthString;
    }
    
    
    
    public ArrayList<ClassAttendanceDetail>convertStringToArrayList(String result){
        
          ArrayList<ClassAttendanceDetail> ClassAttendanceDetailList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              ClassAttendanceDetail appStatus=new ClassAttendanceDetail();
              
              appStatus.setMonth(record.split("~")[0]);
              appStatus.setNo_ofDaysLeave(record.split("~")[1]);
              appStatus.setNo_of_DaysAbsent(record.split("~")[2]);
              appStatus.setNo_of_DaysPresent(record.split("~")[3]);
              appStatus.setSection(record.split("~")[4]);
              appStatus.setStandard(record.split("~")[5]);
              appStatus.setStudentID(record.split("~")[6]);
              appStatus.setYear(record.split("~")[7]);
              appStatus.setStudentName(record.split("~")[8]);
              appStatus.setMonthString(record.split("~")[9]);
              appStatus.setNo_of_workingDays(record.split("~")[10]);
              appStatus.setPercentage(record.split("~")[11]);
              
              
           ClassAttendanceDetailList.add(appStatus);
          }
          
        return ClassAttendanceDetailList;
           
      
}
    
}
