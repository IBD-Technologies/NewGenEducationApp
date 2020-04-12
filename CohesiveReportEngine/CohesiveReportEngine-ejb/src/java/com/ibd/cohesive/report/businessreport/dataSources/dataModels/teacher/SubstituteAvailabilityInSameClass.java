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
public class SubstituteAvailabilityInSameClass {
    String TeacherID;
    String teacherName;
    String classs;
    String freeStartTime;
    String freeEndTime;
    int startTimeNo;

    public int getStartTimeNo() {
        return startTimeNo;
    }

    public void setStartTimeNo(int startTimeNo) {
        this.startTimeNo = startTimeNo;
    }
    
    

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public String getFreeStartTime() {
        return freeStartTime;
    }

    public void setFreeStartTime(String freeStartTime) {
        this.freeStartTime = freeStartTime;
    }

    public String getFreeEndTime() {
        return freeEndTime;
    }

    public void setFreeEndTime(String freeEndTime) {
        this.freeEndTime = freeEndTime;
    }
    
    

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    
    
    public String getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(String TeacherID) {
        this.TeacherID = TeacherID;
    }
      public ArrayList<SubstituteAvailabilityInSameClass>convertStringToArrayList(String result){
        
          ArrayList<SubstituteAvailabilityInSameClass> SubstituteAvailabilityInSameClassList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              SubstituteAvailabilityInSameClass appStatus=new SubstituteAvailabilityInSameClass();
              
              appStatus.setClasss(record.split("~")[0]);
              appStatus.setFreeEndTime(record.split("~")[1]);
              appStatus.setFreeStartTime(record.split("~")[2]);
              appStatus.setStartTimeNo(Integer.parseInt(record.split("~")[3]));
              appStatus.setTeacherID(record.split("~")[4]);
              appStatus.setTeacherName(record.split("~")[5]);
              
              SubstituteAvailabilityInSameClassList.add(appStatus);
          }
          
          
          return SubstituteAvailabilityInSameClassList;
      
      }
    
}
