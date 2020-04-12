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
public class MARK_BATCH_STATUS_ERROR {
    String INSTITUTE_ID;
    String STANDARD;
    String SECTION;
    String EXAM;
    String BUSINESS_DATE;
    String ERROR;

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

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }
    
    public ArrayList<MARK_BATCH_STATUS_ERROR>convertStringToArrayList(String result){
        
          ArrayList<MARK_BATCH_STATUS_ERROR> MARK_BATCH_STATUS_ERRORList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              MARK_BATCH_STATUS_ERROR appStatus=new MARK_BATCH_STATUS_ERROR();
              
              appStatus.setBUSINESS_DATE(record.split("~")[0]);
              appStatus.setERROR(record.split("~")[1]);
              appStatus.setEXAM(record.split("~")[2]);
              appStatus.setINSTITUTE_ID(record.split("~")[3]);
              appStatus.setSECTION(record.split("~")[4]);
              appStatus.setSTANDARD(record.split("~")[5]);
              
              MARK_BATCH_STATUS_ERRORList.add(appStatus);
          }
          
          
          return MARK_BATCH_STATUS_ERRORList;
      
      }
    
}
