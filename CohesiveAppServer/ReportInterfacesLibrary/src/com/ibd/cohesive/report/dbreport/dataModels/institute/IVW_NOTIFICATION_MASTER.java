/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.institute;

import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class IVW_NOTIFICATION_MASTER {
    String INSTITUTE_ID;
    String NOTIFICATION_ID;
    String NOTIFICATION_TYPE;
    String NOTIFICATION_FREQUENCY;
    String DATE;
    String DAY;
    String MONTH;
    String MESSAGE;
    String MEDIA_COMMUNICATION;
    String ASSIGNEE;
    String MAKER_ID;
    String CHECKER_ID;
    String MAKER_DATE_STAMP;
    String CHECKER_DATE_STAMP;
    String RECORD_STATUS;
    String AUTH_STATUS;
    String VERSION_NUMBER;
    String MAKER_REMARKS;
    String CHECKER_REMARKS;
    String INSTANT;
    String EMAIL;
    String MOBILE_NO;

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

    public String getNOTIFICATION_TYPE() {
        return NOTIFICATION_TYPE;
    }

    public void setNOTIFICATION_TYPE(String NOTIFICATION_TYPE) {
        this.NOTIFICATION_TYPE = NOTIFICATION_TYPE;
    }

    public String getNOTIFICATION_FREQUENCY() {
        return NOTIFICATION_FREQUENCY;
    }

    public void setNOTIFICATION_FREQUENCY(String NOTIFICATION_FREQUENCY) {
        this.NOTIFICATION_FREQUENCY = NOTIFICATION_FREQUENCY;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getDAY() {
        return DAY;
    }

    public void setDAY(String DAY) {
        this.DAY = DAY;
    }

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getMEDIA_COMMUNICATION() {
        return MEDIA_COMMUNICATION;
    }

    public void setMEDIA_COMMUNICATION(String MEDIA_COMMUNICATION) {
        this.MEDIA_COMMUNICATION = MEDIA_COMMUNICATION;
    }

    public String getASSIGNEE() {
        return ASSIGNEE;
    }

    public void setASSIGNEE(String ASSIGNEE) {
        this.ASSIGNEE = ASSIGNEE;
    }

    public String getMAKER_ID() {
        return MAKER_ID;
    }

    public void setMAKER_ID(String MAKER_ID) {
        this.MAKER_ID = MAKER_ID;
    }

    public String getCHECKER_ID() {
        return CHECKER_ID;
    }

    public void setCHECKER_ID(String CHECKER_ID) {
        this.CHECKER_ID = CHECKER_ID;
    }

    public String getMAKER_DATE_STAMP() {
        return MAKER_DATE_STAMP;
    }

    public void setMAKER_DATE_STAMP(String MAKER_DATE_STAMP) {
        this.MAKER_DATE_STAMP = MAKER_DATE_STAMP;
    }

    public String getCHECKER_DATE_STAMP() {
        return CHECKER_DATE_STAMP;
    }

    public void setCHECKER_DATE_STAMP(String CHECKER_DATE_STAMP) {
        this.CHECKER_DATE_STAMP = CHECKER_DATE_STAMP;
    }

    public String getRECORD_STATUS() {
        return RECORD_STATUS;
    }

    public void setRECORD_STATUS(String RECORD_STATUS) {
        this.RECORD_STATUS = RECORD_STATUS;
    }

    public String getAUTH_STATUS() {
        return AUTH_STATUS;
    }

    public void setAUTH_STATUS(String AUTH_STATUS) {
        this.AUTH_STATUS = AUTH_STATUS;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }

    public String getMAKER_REMARKS() {
        return MAKER_REMARKS;
    }

    public void setMAKER_REMARKS(String MAKER_REMARKS) {
        this.MAKER_REMARKS = MAKER_REMARKS;
    }

    public String getCHECKER_REMARKS() {
        return CHECKER_REMARKS;
    }

    public void setCHECKER_REMARKS(String CHECKER_REMARKS) {
        this.CHECKER_REMARKS = CHECKER_REMARKS;
    }

    public String getINSTANT() {
        return INSTANT;
    }

    public void setINSTANT(String INSTANT) {
        this.INSTANT = INSTANT;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getMOBILE_NO() {
        return MOBILE_NO;
    }

    public void setMOBILE_NO(String MOBILE_NO) {
        this.MOBILE_NO = MOBILE_NO;
    }
    
    
    
    
     public ArrayList<IVW_NOTIFICATION_MASTER>convertStringToArrayList(String result){
        
          ArrayList<IVW_NOTIFICATION_MASTER> IVW_NOTIFICATION_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_NOTIFICATION_MASTER appStatus=new IVW_NOTIFICATION_MASTER();
              
              appStatus.setASSIGNEE(record.split("~")[0]);
              appStatus.setAUTH_STATUS(record.split("~")[1]);
              appStatus.setCHECKER_DATE_STAMP(record.split("~")[2]);
              appStatus.setCHECKER_ID(record.split("~")[3]);
              appStatus.setCHECKER_REMARKS(record.split("~")[4]);
              appStatus.setDATE(record.split("~")[5]);
              appStatus.setDAY(record.split("~")[6]);
              appStatus.setEMAIL(record.split("~")[7]);
              appStatus.setINSTANT(record.split("~")[8]);
              appStatus.setINSTITUTE_ID(record.split("~")[9]);
              appStatus.setMAKER_DATE_STAMP(record.split("~")[10]);
              appStatus.setMAKER_ID(record.split("~")[11]);
              appStatus.setMAKER_REMARKS(record.split("~")[12]);
              appStatus.setMEDIA_COMMUNICATION(record.split("~")[13]);
              appStatus.setMESSAGE(record.split("~")[14]);
              appStatus.setMOBILE_NO(record.split("~")[15]);
              appStatus.setMONTH(record.split("~")[16]);
              appStatus.setNOTIFICATION_FREQUENCY(record.split("~")[17]);
              appStatus.setNOTIFICATION_ID(record.split("~")[18]);
              appStatus.setNOTIFICATION_TYPE(record.split("~")[19]);
              appStatus.setRECORD_STATUS(record.split("~")[20]);
              appStatus.setVERSION_NUMBER(record.split("~")[21]);
              
              
              
              
              
           IVW_NOTIFICATION_MASTERList.add(appStatus);
          }
          
        return IVW_NOTIFICATION_MASTERList;
           
      
}
            
            
            
            
            
            
}
