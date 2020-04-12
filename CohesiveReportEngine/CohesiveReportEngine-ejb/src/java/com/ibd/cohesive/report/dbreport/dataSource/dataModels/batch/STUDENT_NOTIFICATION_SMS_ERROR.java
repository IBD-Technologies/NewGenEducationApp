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
public class STUDENT_NOTIFICATION_SMS_ERROR {
    String INSTITUTE_ID;
    String NOTIFICATION_ID;
    String STUDENT_ID;
    String BUSINESS_DATE;
    String MOBILE_NO;
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

    public String getMOBILE_NO() {
        return MOBILE_NO;
    }

    public void setMOBILE_NO(String MOBILE_NO) {
        this.MOBILE_NO = MOBILE_NO;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }
    
    
     public ArrayList<STUDENT_NOTIFICATION_SMS_ERROR>convertStringToArrayList(String result){
        
          ArrayList<STUDENT_NOTIFICATION_SMS_ERROR> STUDENT_NOTIFICATION_EOD_STATUSList=new ArrayList();
          
         
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              STUDENT_NOTIFICATION_SMS_ERROR appStatus=new STUDENT_NOTIFICATION_SMS_ERROR();
              
              appStatus.setBUSINESS_DATE(record.split("~")[0]);
              appStatus.setERROR(record.split("~")[1]);
              appStatus.setINSTITUTE_ID(record.split("~")[2]);
              appStatus.setMOBILE_NO(record.split("~")[3]);
              appStatus.setNOTIFICATION_ID(record.split("~")[4]);
              appStatus.setSTUDENT_ID(record.split("~")[5]);
              
              
              STUDENT_NOTIFICATION_EOD_STATUSList.add(appStatus);
          }
          
          
          return STUDENT_NOTIFICATION_EOD_STATUSList;
      
      }
    
}
