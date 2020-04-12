/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.classEntity;

import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class CLASS_ATTENDANCE_REPORT {
    String STANDARD;
    String SECTION;
    String YEAR;
    String MONTH;
    String PRESENT_AVERAGE;
    String ABSENT_AVERAGE;
    String LEAVE_AVERAGE;
    String PRESENT_PERCENTAGE;
    String ABSENT_PERCENTAGE;
    String LEAVE_PERCENTAGE;

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

    public String getPRESENT_AVERAGE() {
        return PRESENT_AVERAGE;
    }

    public void setPRESENT_AVERAGE(String PRESENT_AVERAGE) {
        this.PRESENT_AVERAGE = PRESENT_AVERAGE;
    }

    public String getABSENT_AVERAGE() {
        return ABSENT_AVERAGE;
    }

    public void setABSENT_AVERAGE(String ABSENT_AVERAGE) {
        this.ABSENT_AVERAGE = ABSENT_AVERAGE;
    }

    public String getLEAVE_AVERAGE() {
        return LEAVE_AVERAGE;
    }

    public void setLEAVE_AVERAGE(String LEAVE_AVERAGE) {
        this.LEAVE_AVERAGE = LEAVE_AVERAGE;
    }

    public String getPRESENT_PERCENTAGE() {
        return PRESENT_PERCENTAGE;
    }

    public void setPRESENT_PERCENTAGE(String PRESENT_PERCENTAGE) {
        this.PRESENT_PERCENTAGE = PRESENT_PERCENTAGE;
    }

    public String getABSENT_PERCENTAGE() {
        return ABSENT_PERCENTAGE;
    }

    public void setABSENT_PERCENTAGE(String ABSENT_PERCENTAGE) {
        this.ABSENT_PERCENTAGE = ABSENT_PERCENTAGE;
    }

    public String getLEAVE_PERCENTAGE() {
        return LEAVE_PERCENTAGE;
    }

    public void setLEAVE_PERCENTAGE(String LEAVE_PERCENTAGE) {
        this.LEAVE_PERCENTAGE = LEAVE_PERCENTAGE;
    }
    
     public ArrayList<CLASS_ATTENDANCE_REPORT>convertStringToArrayList(String result){
        
          ArrayList<CLASS_ATTENDANCE_REPORT> CLASS_ATTENDANCE_REPORTList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              CLASS_ATTENDANCE_REPORT appStatus=new CLASS_ATTENDANCE_REPORT();
              
              appStatus.setABSENT_AVERAGE(record.split("~")[0]);
              appStatus.setABSENT_PERCENTAGE(record.split("~")[1]);
              appStatus.setLEAVE_AVERAGE(record.split("~")[2]);
              appStatus.setLEAVE_PERCENTAGE(record.split("~")[3]);
              appStatus.setMONTH(record.split("~")[4]);
              appStatus.setPRESENT_AVERAGE(record.split("~")[5]);
              appStatus.setPRESENT_PERCENTAGE(record.split("~")[6]);
              appStatus.setSECTION(record.split("~")[7]);
              appStatus.setSTANDARD(record.split("~")[8]);
              appStatus.setYEAR(record.split("~")[9]);
              
              
              
              
              
           CLASS_ATTENDANCE_REPORTList.add(appStatus);
          }
          
        return CLASS_ATTENDANCE_REPORTList;
           
      
}
    
    
    
    
    
    
    
}
