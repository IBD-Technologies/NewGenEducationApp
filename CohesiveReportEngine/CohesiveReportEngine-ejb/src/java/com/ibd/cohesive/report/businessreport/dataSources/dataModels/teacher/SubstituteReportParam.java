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
public class SubstituteReportParam {
    String teacherID;
    String teacherName;
    String date;

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public ArrayList<SubstituteReportParam>convertStringToArrayList(String result){
        
          ArrayList<SubstituteReportParam> SubstituteReportParamList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              SubstituteReportParam appStatus=new SubstituteReportParam();
              
              appStatus.setDate(record.split("~")[0]);
              appStatus.setTeacherID(record.split("~")[1]);
              appStatus.setTeacherName(record.split("~")[2]);
              
              SubstituteReportParamList.add(appStatus);
          }
          
          
          return SubstituteReportParamList;
      
      }
}
