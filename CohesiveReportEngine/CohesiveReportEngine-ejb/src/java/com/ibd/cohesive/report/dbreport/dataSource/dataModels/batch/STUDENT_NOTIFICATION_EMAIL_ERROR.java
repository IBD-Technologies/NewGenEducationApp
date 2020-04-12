/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class STUDENT_NOTIFICATION_EMAIL_ERROR {
    String INSTITUTE_ID;
    String NOTIFICATION_ID;
    String STUDENT_ID;
    String BUSINESS_DATE;
    String EMAIL_ID;
    String ERROR;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
    }

    public String getNOTIFICATION_ID() {
        return NOTIFICATION_ID;
    }

    public void setNOTIFICATION_ID(String NOTIFICATION_ID) {
        this.NOTIFICATION_ID = NOTIFICATION_ID;
    }

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getBUSINESS_DATE() {
        return BUSINESS_DATE;
    }

    public void setBUSINESS_DATE(String BUSINESS_DATE) {
        this.BUSINESS_DATE = BUSINESS_DATE;
    }

    public String getEMAIL_ID() {
        return EMAIL_ID;
    }

    public void setEMAIL_ID(String EMAIL_ID) {
        this.EMAIL_ID = EMAIL_ID;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }
    
    
    
    
     public ArrayList<STUDENT_NOTIFICATION_EMAIL_ERROR>convertStringToArrayList(String result){
        
          ArrayList<STUDENT_NOTIFICATION_EMAIL_ERROR> STUDENT_NOTIFICATION_EOD_STATUSList=new ArrayList();
          
         
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              STUDENT_NOTIFICATION_EMAIL_ERROR appStatus=new STUDENT_NOTIFICATION_EMAIL_ERROR();
              
              appStatus.setBUSINESS_DATE(record.split("~")[0]);
              appStatus.setEMAIL_ID(record.split("~")[1]);
              appStatus.setERROR(record.split("~")[2]);
              appStatus.setINSTITUTE_ID(record.split("~")[3]);
              appStatus.setNOTIFICATION_ID(record.split("~")[4]);
              appStatus.setSTUDENT_ID(record.split("~")[5]);
              
              
              STUDENT_NOTIFICATION_EOD_STATUSList.add(appStatus);
          }
          
          
          return STUDENT_NOTIFICATION_EOD_STATUSList;
      
      }
    
    
    
    
    
}
