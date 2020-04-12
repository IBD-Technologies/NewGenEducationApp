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
public class ClassOtherActivitySummary {
    String Year;
    String month;
    String activityType;
    String no_of_students_Participated;
    String resultType;
    String no_of_StudentsOntheResult;
    
    public String getYear() {
        return Year;
    }

    public void setYear(String Year) {
        this.Year = Year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


    public String getNo_of_students_Participated() {
        return no_of_students_Participated;
    }

    public void setNo_of_students_Participated(String no_of_students_Participated) {
        this.no_of_students_Participated = no_of_students_Participated;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getNo_of_StudentsOntheResult() {
        return no_of_StudentsOntheResult;
    }

    public void setNo_of_StudentsOntheResult(String no_of_StudentsOntheResult) {
        this.no_of_StudentsOntheResult = no_of_StudentsOntheResult;
    }

    
 public ArrayList<ClassOtherActivitySummary>convertStringToArrayList(String result){
        
          ArrayList<ClassOtherActivitySummary> ClassOtherActivitySummaryList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              ClassOtherActivitySummary appStatus=new ClassOtherActivitySummary();
              
              appStatus.setActivityType(record.split("~")[0]);
              appStatus.setMonth(record.split("~")[1]);
              appStatus.setNo_of_StudentsOntheResult(record.split("~")[2]);
              appStatus.setNo_of_students_Participated(record.split("~")[3]);
              appStatus.setResultType(record.split("~")[4]);
              appStatus.setYear(record.split("~")[5]);
              
              
              
             
              
              
              
        ClassOtherActivitySummaryList.add(appStatus);
          }
          
        return ClassOtherActivitySummaryList;
           
      
}
  
    
    
    
}
