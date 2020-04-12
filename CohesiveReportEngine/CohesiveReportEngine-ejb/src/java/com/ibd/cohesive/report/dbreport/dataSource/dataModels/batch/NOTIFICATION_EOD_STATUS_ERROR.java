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
public class NOTIFICATION_EOD_STATUS_ERROR {
    String INSTITUTE_ID;
    String NOTIFICATION_ID;
    String BUSINESS_DATE;
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
  public ArrayList<NOTIFICATION_EOD_STATUS_ERROR>convertStringToArrayList(String result){
        
          ArrayList<NOTIFICATION_EOD_STATUS_ERROR> NOTIFICATION_EOD_STATUSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              NOTIFICATION_EOD_STATUS_ERROR appStatus=new NOTIFICATION_EOD_STATUS_ERROR();
              
              appStatus.setBUSINESS_DATE(record.split("~")[0]);
              appStatus.setERROR(record.split("~")[1]);
              appStatus.setINSTITUTE_ID(record.split("~")[2]);
              appStatus.setNOTIFICATION_ID(record.split("~")[3]);
              
              
              NOTIFICATION_EOD_STATUSList.add(appStatus);
          }
          
          
          return NOTIFICATION_EOD_STATUSList;
      }
        
    
}
