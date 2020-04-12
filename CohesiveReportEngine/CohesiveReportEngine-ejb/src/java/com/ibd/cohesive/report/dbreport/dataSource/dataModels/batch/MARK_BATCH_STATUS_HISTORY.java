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
public class MARK_BATCH_STATUS_HISTORY {
    String INSTITUTE_ID;
    String STANDARD;
    String SECTION;
    String EXAM;
    String BUSINESS_DATE;
    String STATUS;
    String NO_OF_SUCCESS;
    String NO_FAILURES;
    String START_TIME;
    String END_TIME;
    String SEQUENCE_NO;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
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

    public String getEXAM() {
        return EXAM;
    }

    public void setEXAM(String EXAM) {
        this.EXAM = EXAM;
    }

    public String getBUSINESS_DATE() {
        return BUSINESS_DATE;
    }

    public void setBUSINESS_DATE(String BUSINESS_DATE) {
        this.BUSINESS_DATE = BUSINESS_DATE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getNO_OF_SUCCESS() {
        return NO_OF_SUCCESS;
    }

    public void setNO_OF_SUCCESS(String NO_OF_SUCCESS) {
        this.NO_OF_SUCCESS = NO_OF_SUCCESS;
    }

    public String getNO_FAILURES() {
        return NO_FAILURES;
    }

    public void setNO_FAILURES(String NO_FAILURES) {
        this.NO_FAILURES = NO_FAILURES;
    }

    public String getSTART_TIME() {
        return START_TIME;
    }

    public void setSTART_TIME(String START_TIME) {
        this.START_TIME = START_TIME;
    }

    public String getEND_TIME() {
        return END_TIME;
    }

    public void setEND_TIME(String END_TIME) {
        this.END_TIME = END_TIME;
    }

    public String getSEQUENCE_NO() {
        return SEQUENCE_NO;
    }

    public void setSEQUENCE_NO(String SEQUENCE_NO) {
        this.SEQUENCE_NO = SEQUENCE_NO;
    }
    
    
    
     public ArrayList<MARK_BATCH_STATUS_HISTORY>convertStringToArrayList(String result){
        
          ArrayList<MARK_BATCH_STATUS_HISTORY> MARK_BATCH_STATUS_HISTORYList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              MARK_BATCH_STATUS_HISTORY appStatus=new MARK_BATCH_STATUS_HISTORY();
              
              appStatus.setBUSINESS_DATE(record.split("~")[0]);
              appStatus.setEND_TIME(record.split("~")[1]);
              appStatus.setEXAM(record.split("~")[2]);
              appStatus.setINSTITUTE_ID(record.split("~")[3]);
              appStatus.setNO_FAILURES(record.split("~")[4]);
              appStatus.setNO_OF_SUCCESS(record.split("~")[5]);
              appStatus.setSECTION(record.split("~")[6]);
              appStatus.setSEQUENCE_NO(record.split("~")[7]);
              appStatus.setSTANDARD(record.split("~")[8]);
              appStatus.setSTART_TIME(record.split("~")[9]);
              appStatus.setSTATUS(record.split("~")[10]);
              
              
              MARK_BATCH_STATUS_HISTORYList.add(appStatus);
          }
          
          
          return MARK_BATCH_STATUS_HISTORYList;
      
      }
}
