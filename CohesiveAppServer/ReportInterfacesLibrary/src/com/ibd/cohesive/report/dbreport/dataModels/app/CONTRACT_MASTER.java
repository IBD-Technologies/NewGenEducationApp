/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.app;

import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class CONTRACT_MASTER {
    String INSTITUTE_ID;
    String CONTRACT_ID;
    String SMS_LIMIT;
    String EMAIL_LIMIT;
    String SMS_USED;
    String EMAIL_USED;
    String SMS_VENDOR;
    String EMAIL_VENDOR;
    String EMAIL_ID;
    String CONTACT_EMAIL;
    String CONTACT_MOBILE_NO;
    String COMMUNICATION_MODE;
    String COMMUNICATION_LANGUAGE;
    String COUNTRY_CODE;
    String PLAN;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
    }

    public String getCONTRACT_ID() {
        return CONTRACT_ID;
    }

    public void setCONTRACT_ID(String CONTRACT_ID) {
        this.CONTRACT_ID = CONTRACT_ID;
    }

    public String getSMS_LIMIT() {
        return SMS_LIMIT;
    }

    public void setSMS_LIMIT(String SMS_LIMIT) {
        this.SMS_LIMIT = SMS_LIMIT;
    }

    public String getEMAIL_LIMIT() {
        return EMAIL_LIMIT;
    }

    public void setEMAIL_LIMIT(String EMAIL_LIMIT) {
        this.EMAIL_LIMIT = EMAIL_LIMIT;
    }

    public String getSMS_USED() {
        return SMS_USED;
    }

    public void setSMS_USED(String SMS_USED) {
        this.SMS_USED = SMS_USED;
    }

    public String getEMAIL_USED() {
        return EMAIL_USED;
    }

    public void setEMAIL_USED(String EMAIL_USED) {
        this.EMAIL_USED = EMAIL_USED;
    }

    public String getSMS_VENDOR() {
        return SMS_VENDOR;
    }

    public void setSMS_VENDOR(String SMS_VENDOR) {
        this.SMS_VENDOR = SMS_VENDOR;
    }

    public String getEMAIL_VENDOR() {
        return EMAIL_VENDOR;
    }

    public void setEMAIL_VENDOR(String EMAIL_VENDOR) {
        this.EMAIL_VENDOR = EMAIL_VENDOR;
    }

    public String getEMAIL_ID() {
        return EMAIL_ID;
    }

    public void setEMAIL_ID(String EMAIL_ID) {
        this.EMAIL_ID = EMAIL_ID;
    }

    public String getCONTACT_EMAIL() {
        return CONTACT_EMAIL;
    }

    public void setCONTACT_EMAIL(String CONTACT_EMAIL) {
        this.CONTACT_EMAIL = CONTACT_EMAIL;
    }

    public String getCONTACT_MOBILE_NO() {
        return CONTACT_MOBILE_NO;
    }

    public void setCONTACT_MOBILE_NO(String CONTACT_MOBILE_NO) {
        this.CONTACT_MOBILE_NO = CONTACT_MOBILE_NO;
    }

    public String getCOMMUNICATION_MODE() {
        return COMMUNICATION_MODE;
    }

    public void setCOMMUNICATION_MODE(String COMMUNICATION_MODE) {
        this.COMMUNICATION_MODE = COMMUNICATION_MODE;
    }

    public String getCOMMUNICATION_LANGUAGE() {
        return COMMUNICATION_LANGUAGE;
    }

    public void setCOMMUNICATION_LANGUAGE(String COMMUNICATION_LANGUAGE) {
        this.COMMUNICATION_LANGUAGE = COMMUNICATION_LANGUAGE;
    }

    public String getCOUNTRY_CODE() {
        return COUNTRY_CODE;
    }

    public void setCOUNTRY_CODE(String COUNTRY_CODE) {
        this.COUNTRY_CODE = COUNTRY_CODE;
    }

    public String getPLAN() {
        return PLAN;
    }

    public void setPLAN(String PLAN) {
        this.PLAN = PLAN;
    }
    
    
    public ArrayList<CONTRACT_MASTER>convertStringToArrayList(String result){
        
          ArrayList<CONTRACT_MASTER> CONTRACT_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              CONTRACT_MASTER appStatus=new CONTRACT_MASTER();
              
              appStatus.setCOMMUNICATION_LANGUAGE(record.split("~")[0]);
              appStatus.setCOMMUNICATION_MODE(record.split("~")[1]);
              appStatus.setCONTACT_EMAIL(record.split("~")[2]);
              appStatus.setCONTACT_MOBILE_NO(record.split("~")[3]);
              appStatus.setCONTRACT_ID(record.split("~")[4]);
              appStatus.setCOUNTRY_CODE(record.split("~")[5]);
              appStatus.setEMAIL_ID(record.split("~")[6]);
              appStatus.setEMAIL_LIMIT(record.split("~")[7]);
              appStatus.setEMAIL_USED(record.split("~")[8]);
              appStatus.setEMAIL_VENDOR(record.split("~")[9]);
              appStatus.setINSTITUTE_ID(record.split("~")[10]);
              appStatus.setPLAN(record.split("~")[11]);
              appStatus.setSMS_LIMIT(record.split("~")[12]);
              appStatus.setSMS_USED(record.split("~")[13]);
              appStatus.setSMS_VENDOR(record.split("~")[14]);
              
                           
                           
              
              
           CONTRACT_MASTERList.add(appStatus);
          }
          
        return CONTRACT_MASTERList;
           
      
}
    
    
            
            
            
            
            
            
            
}
