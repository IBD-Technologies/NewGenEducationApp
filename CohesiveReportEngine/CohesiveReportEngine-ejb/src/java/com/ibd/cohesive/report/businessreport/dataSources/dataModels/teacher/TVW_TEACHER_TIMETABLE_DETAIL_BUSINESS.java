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
public class TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS {
    String TEACHER_ID;
    String DAY;
    String PERIOD_NO;
    String SUBJECT_ID;
    String STANDARD;
    String SECTION;
    String VERSION_NUMBER;
    String DAY_NUMBER;
    String startTime;
    String endTime;

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
    
    

    public String getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(String TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
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

    public String getSTANDARD() {
        return STANDARD;
    }

    public void setSTANDARD(String STANDARD) {
        this.STANDARD = STANDARD;
    }

    public String getSECTION() {
        return SECTION;
    }

    public void setSECTION(String SECTION) {
        this.SECTION = SECTION;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }

    public String getDAY_NUMBER() {
        return DAY_NUMBER;
    }

    public void setDAY_NUMBER(String DAY_NUMBER) {
        this.DAY_NUMBER = DAY_NUMBER;
    }
    
    
    
    public ArrayList<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS>convertStringToArrayList(String result){
        
          ArrayList<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS>TVW_TEACHER_TIMETABLE_DETAIL_BUSINESSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS appStatus=new TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS();
              
              appStatus.setDAY(record.split("~")[0]);
              appStatus.setDAY_NUMBER(record.split("~")[1]);
              appStatus.setEndTime(record.split("~")[2]);
              appStatus.setPERIOD_NO(record.split("~")[3]);
              appStatus.setSECTION(record.split("~")[4]);
              appStatus.setSTANDARD(record.split("~")[5]);
              appStatus.setSUBJECT_ID(record.split("~")[6]);
              appStatus.setStartTime(record.split("~")[7]);
              appStatus.setTEACHER_ID(record.split("~")[8]);
              appStatus.setVERSION_NUMBER(record.split("~")[9]);
              
           TVW_TEACHER_TIMETABLE_DETAIL_BUSINESSList.add(appStatus);
          }
          
        return TVW_TEACHER_TIMETABLE_DETAIL_BUSINESSList;
           
      
}
    
}
