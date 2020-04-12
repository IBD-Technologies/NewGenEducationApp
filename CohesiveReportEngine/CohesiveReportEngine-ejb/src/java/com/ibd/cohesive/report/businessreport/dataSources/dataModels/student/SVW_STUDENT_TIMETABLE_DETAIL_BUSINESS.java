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
public class SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS {
    String STUDENT_ID;
    String DAY;
    String PERIOD_NO;
    String SUBJECT_ID;
    String TEACHER_ID;
    String VERSION_NUMBER;
    String startTime;
    String endTime;

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getDAY() {
        return DAY;
    }

    public void setDAY(String DAY) {
        this.DAY = DAY;
    }

    public String getPERIOD_NO() {
        return PERIOD_NO;
    }

    public void setPERIOD_NO(String PERIOD_NO) {
        this.PERIOD_NO = PERIOD_NO;
    }

    public String getSUBJECT_ID() {
        return SUBJECT_ID;
    }

    public void setSUBJECT_ID(String SUBJECT_ID) {
        this.SUBJECT_ID = SUBJECT_ID;
    }


    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }

    public String getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(String TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    
    public ArrayList<SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS>convertStringToArrayList(String result){
        
          ArrayList<SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS> SVW_STUDENT_TIMETABLE_DETAIL_BUSINESSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS appStatus=new SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS();
              
              appStatus.setDAY(record.split("~")[0]);
              appStatus.setEndTime(record.split("~")[1]);
              appStatus.setPERIOD_NO(record.split("~")[2]);
              appStatus.setSTUDENT_ID(record.split("~")[3]);
              appStatus.setSUBJECT_ID(record.split("~")[4]);
              appStatus.setStartTime(record.split("~")[5]);
              appStatus.setTEACHER_ID(record.split("~")[6]);
              appStatus.setVERSION_NUMBER(record.split("~")[7]);
              
              
              SVW_STUDENT_TIMETABLE_DETAIL_BUSINESSList.add(appStatus);
          }
          
          
          return SVW_STUDENT_TIMETABLE_DETAIL_BUSINESSList;
      
      }
    
    
    
}
