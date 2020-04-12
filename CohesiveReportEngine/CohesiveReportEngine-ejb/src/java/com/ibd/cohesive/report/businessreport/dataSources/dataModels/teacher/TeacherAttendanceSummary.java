/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.dataModels.teacher;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class TeacherAttendanceSummary {
    String teacherID;
    String year;
    String month;
    String no_of_Days_Present;
    String no_of_Days_Leave;
    String no_of_WorkingDays;
    String percentage;

    public String getNo_of_WorkingDays() {
        return no_of_WorkingDays;
    }

    public void setNo_of_WorkingDays(String no_of_WorkingDays) {
        this.no_of_WorkingDays = no_of_WorkingDays;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
    
    

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getNo_of_Days_Present() {
        return no_of_Days_Present;
    }

    public void setNo_of_Days_Present(String no_of_Days_Present) {
        this.no_of_Days_Present = no_of_Days_Present;
    }

  

    public String getNo_of_Days_Leave() {
        return no_of_Days_Leave;
    }

    public void setNo_of_Days_Leave(String no_of_Days_Leave) {
        this.no_of_Days_Leave = no_of_Days_Leave;
    }
    
    public ArrayList<TeacherAttendanceSummary>convertStringToArrayList(String result){
        
          ArrayList<TeacherAttendanceSummary> TeacherAttendanceSummaryList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              TeacherAttendanceSummary appStatus=new TeacherAttendanceSummary();
              
              appStatus.setMonth(record.split("~")[0]);
              appStatus.setNo_of_Days_Leave(record.split("~")[1]);
              appStatus.setNo_of_Days_Present(record.split("~")[2]);
              appStatus.setTeacherID(record.split("~")[3]);
              appStatus.setYear(record.split("~")[4]);
              appStatus.setNo_of_WorkingDays(record.split("~")[5]);
              appStatus.setPercentage(record.split("~")[6]);
              
              TeacherAttendanceSummaryList.add(appStatus);
          }
          
          
          return TeacherAttendanceSummaryList;
      
      }
    
}
