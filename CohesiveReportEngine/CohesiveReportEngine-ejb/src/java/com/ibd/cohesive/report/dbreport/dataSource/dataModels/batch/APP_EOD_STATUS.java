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
public class APP_EOD_STATUS {
    String BUSINESS_DATE;
    String START_TIME;
    String END_TIME;
    String EOD_STATUS;
    String ERROR;
    String NO_OF_SUCCESS;
    String NO_OF_FAILURES;
    String SEQUENCE_NO;

    public String getSEQUENCE_NO() {
        return SEQUENCE_NO;
    }

    public void setSEQUENCE_NO(String SEQUENCE_NO) {
        this.SEQUENCE_NO = SEQUENCE_NO;
    }

    
    
    
    public String getBUSINESS_DATE() {
        return BUSINESS_DATE;
    }

    public void setBUSINESS_DATE(String BUSINESS_DATE) {
        this.BUSINESS_DATE = BUSINESS_DATE;
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

    public String getEOD_STATUS() {
        return EOD_STATUS;
    }

    public void setEOD_STATUS(String EOD_STATUS) {
        this.EOD_STATUS = EOD_STATUS;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }

    public String getNO_OF_SUCCESS() {
        return NO_OF_SUCCESS;
    }

    public void setNO_OF_SUCCESS(String NO_OF_SUCCESS) {
        this.NO_OF_SUCCESS = NO_OF_SUCCESS;
    }

    public String getNO_OF_FAILURES() {
        return NO_OF_FAILURES;
    }

    public void setNO_OF_FAILURES(String NO_OF_FAILURES) {
        this.NO_OF_FAILURES = NO_OF_FAILURES;
    }
    
   public ArrayList<APP_EOD_STATUS>convertStringToArrayList(String result){
        
          ArrayList<APP_EOD_STATUS> APP_EOD_STATUSList=new ArrayList();
          
          try{
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];
              String BUSINESS_DATE=record.split("~")[0];
              String START_TIME=record.split("~")[1];
              String END_TIME=record.split("~")[2];
              String EOD_STATUS=record.split("~")[3];
              String ERROR=record.split("~")[4];
              String NO_OF_SUCCESS=record.split("~")[5];
              String NO_OF_FAILURES=record.split("~")[6];
              String SEQUENCE_NO=record.split("~")[7];
              
              
              APP_EOD_STATUS appStatus=new APP_EOD_STATUS();
              
              appStatus.setBUSINESS_DATE(BUSINESS_DATE);
              appStatus.setEND_TIME(END_TIME);
              appStatus.setEOD_STATUS(EOD_STATUS);
              appStatus.setERROR(ERROR);
              appStatus.setNO_OF_FAILURES(NO_OF_FAILURES);
              appStatus.setNO_OF_SUCCESS(NO_OF_SUCCESS);
              appStatus.setSEQUENCE_NO(SEQUENCE_NO);
              appStatus.setSTART_TIME(START_TIME);
              
              APP_EOD_STATUSList.add(appStatus);
          }
          
          
          return APP_EOD_STATUSList;
      }catch(Exception ex){
         //throw new DBProcessingException("DBProcessingException"+ex.toString());
        return null;
     }
      }
        
    
}
