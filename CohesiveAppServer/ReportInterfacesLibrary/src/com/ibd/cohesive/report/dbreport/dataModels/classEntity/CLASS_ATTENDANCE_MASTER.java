/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.classEntity;

import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class CLASS_ATTENDANCE_MASTER {
  String STANDARD;
    String SECTION;
    String YEAR;
    String MONTH;
    String  AUDIT_DETAILS;

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

    public String getAUDIT_DETAILS() {
        return AUDIT_DETAILS;
    }

    public void setAUDIT_DETAILS(String AUDIT_DETAILS) {
        this.AUDIT_DETAILS = AUDIT_DETAILS;
    }
    

    
    public ArrayList<CLASS_ATTENDANCE_MASTER>convertStringToArrayList(String result){
        
          ArrayList<CLASS_ATTENDANCE_MASTER> CLASS_ATTENDANCE_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              CLASS_ATTENDANCE_MASTER appStatus=new CLASS_ATTENDANCE_MASTER();
              
              appStatus.setAUDIT_DETAILS(record.split("~")[0]);
              appStatus.setMONTH(record.split("~")[1]);
              appStatus.setSECTION(record.split("~")[2]);
              appStatus.setSTANDARD(record.split("~")[3]);
              appStatus.setYEAR(record.split("~")[4]);
            
              
              
              
           CLASS_ATTENDANCE_MASTERList.add(appStatus);
          }
          
        return CLASS_ATTENDANCE_MASTERList;
           
      
}
    
   
}
