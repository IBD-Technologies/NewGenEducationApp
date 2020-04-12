/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.teacher;

import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class TVW_TEACHER_ATTENDANCE_DETAIL {
    String TEACHER_ID;
    String DATE;
    String SUBJECT_ID;
    String STANDARD;
    String ATTENDANCE;
    String VERSION_NUMBER;
    String SECTION;
    String PERIOD_NO;

    public String getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(String TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
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

    public String getATTENDANCE() {
        return ATTENDANCE;
    }

    public void setATTENDANCE(String ATTENDANCE) {
        this.ATTENDANCE = ATTENDANCE;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }

    public String getSECTION() {
        return SECTION;
    }

    public void setSECTION(String SECTION) {
        this.SECTION = SECTION;
    }

    public String getPERIOD_NO() {
        return PERIOD_NO;
    }

    public void setPERIOD_NO(String PERIOD_NO) {
        this.PERIOD_NO = PERIOD_NO;
    }
    
     public ArrayList<TVW_TEACHER_ATTENDANCE_DETAIL>convertStringToArrayList(String result){
        
          ArrayList<TVW_TEACHER_ATTENDANCE_DETAIL> TVW_TEACHER_ATTENDANCE_DETAILList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              TVW_TEACHER_ATTENDANCE_DETAIL appStatus=new TVW_TEACHER_ATTENDANCE_DETAIL();
              
              appStatus.setATTENDANCE(record.split("~")[0]);
              appStatus.setDATE(record.split("~")[1]);
              appStatus.setPERIOD_NO(record.split("~")[2]);
              appStatus.setSECTION(record.split("~")[3]);
              appStatus.setSTANDARD(record.split("~")[4]);
              appStatus.setSUBJECT_ID(record.split("~")[5]);
              appStatus.setTEACHER_ID(record.split("~")[6]);             
              appStatus.setVERSION_NUMBER(record.split("~")[7]);
              
              
              
              
              
           TVW_TEACHER_ATTENDANCE_DETAILList.add(appStatus);
          }
          
        return TVW_TEACHER_ATTENDANCE_DETAILList;
           
      
}
    
}
