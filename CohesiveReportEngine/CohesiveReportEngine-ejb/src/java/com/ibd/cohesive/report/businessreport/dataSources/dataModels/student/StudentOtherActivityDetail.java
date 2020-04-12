/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.dataModels.student;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class StudentOtherActivityDetail {
    String activityID;
    String activityName;
    String activityType;
    String result;
    String level;
    String venue;
    String date;

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public ArrayList<StudentOtherActivityDetail>convertStringToArrayList(String result){
        
          ArrayList<StudentOtherActivityDetail> StudentFeeDetailsList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              StudentOtherActivityDetail appStatus=new StudentOtherActivityDetail();
              
              appStatus.setActivityID(record.split("~")[0]);
              appStatus.setActivityName(record.split("~")[1]);
              appStatus.setActivityType(record.split("~")[2]);
              appStatus.setDate(record.split("~")[3]);
              appStatus.setLevel(record.split("~")[4]);
              appStatus.setResult(record.split("~")[5]);
              appStatus.setVenue(record.split("~")[6]);
              
              StudentFeeDetailsList.add(appStatus);
          }
          
          
          return StudentFeeDetailsList;
      
      }
}
