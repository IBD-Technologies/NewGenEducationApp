/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.student;

import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class SVW_STUDENT_ATTENDANCE_REPORT {
    String STUDENT_ID; 
    String YEAR;
    String MONTH;
    String TOTAL_DAYS;
    String PRESENT_DAYS;
    String ABSENT_DAYS;
    String LEAVE_DAYS;
    String PERCENTAGE;

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

    public String getTOTAL_DAYS() {
        return TOTAL_DAYS;
    }

    public void setTOTAL_DAYS(String TOTAL_DAYS) {
        this.TOTAL_DAYS = TOTAL_DAYS;
    }

    public String getPRESENT_DAYS() {
        return PRESENT_DAYS;
    }

    public void setPRESENT_DAYS(String PRESENT_DAYS) {
        this.PRESENT_DAYS = PRESENT_DAYS;
    }

    public String getABSENT_DAYS() {
        return ABSENT_DAYS;
    }

    public void setABSENT_DAYS(String ABSENT_DAYS) {
        this.ABSENT_DAYS = ABSENT_DAYS;
    }

    public String getLEAVE_DAYS() {
        return LEAVE_DAYS;
    }

    public void setLEAVE_DAYS(String LEAVE_DAYS) {
        this.LEAVE_DAYS = LEAVE_DAYS;
    }

    public String getPERCENTAGE() {
        return PERCENTAGE;
    }

    public void setPERCENTAGE(String PERCENTAGE) {
        this.PERCENTAGE = PERCENTAGE;
    }
    
     public ArrayList<SVW_STUDENT_ATTENDANCE_REPORT>convertStringToArrayList(String result){
        
          ArrayList<SVW_STUDENT_ATTENDANCE_REPORT> SVW_STUDENT_ATTENDANCE_REPORTList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              SVW_STUDENT_ATTENDANCE_REPORT appStatus=new SVW_STUDENT_ATTENDANCE_REPORT();
              
              appStatus.setABSENT_DAYS(record.split("~")[0]);
              appStatus.setLEAVE_DAYS(record.split("~")[1]);
              appStatus.setMONTH(record.split("~")[2]);
              appStatus.setPERCENTAGE(record.split("~")[3]);
              appStatus.setPRESENT_DAYS(record.split("~")[4]);
              appStatus.setSTUDENT_ID(record.split("~")[5]);
              appStatus.setTOTAL_DAYS(record.split("~")[6]);
              appStatus.setYEAR(record.split("~")[7]);
              
              
              
              
              
           SVW_STUDENT_ATTENDANCE_REPORTList.add(appStatus);
          }
          
        return SVW_STUDENT_ATTENDANCE_REPORTList;
           
      
}
    
    
}
