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
public class SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS {
    String STUDENT_ID;
    String YEAR;
    String LEVEL;
    String RESULT_TYPE;
    String COUNT;

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

    public String getLEVEL() {
        return LEVEL;
    }

    public void setLEVEL(String LEVEL) {
        this.LEVEL = LEVEL;
    }

    public String getRESULT_TYPE() {
        return RESULT_TYPE;
    }

    public void setRESULT_TYPE(String RESULT_TYPE) {
        this.RESULT_TYPE = RESULT_TYPE;
    }

    public String getCOUNT() {
        return COUNT;
    }

    public void setCOUNT(String COUNT) {
        this.COUNT = COUNT;
    }
    
    
     public ArrayList<SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS>convertStringToArrayList(String result){
        
          ArrayList<SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS> SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS appStatus=new SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS();
              
              appStatus.setCOUNT(record.split("~")[0]);
              appStatus.setLEVEL(record.split("~")[1]);
              appStatus.setRESULT_TYPE(record.split("~")[2]);
              appStatus.setSTUDENT_ID(record.split("~")[3]);
              appStatus.setYEAR(record.split("~")[4]);
              
              SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESSList.add(appStatus);
          }
          
          
          return SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESSList;
      
      }
    
    
}
