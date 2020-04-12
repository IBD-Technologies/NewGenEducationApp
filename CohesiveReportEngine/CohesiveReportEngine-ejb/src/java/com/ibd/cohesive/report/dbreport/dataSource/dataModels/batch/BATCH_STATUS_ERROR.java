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
public class BATCH_STATUS_ERROR {
    String INSTITUTE_ID;
    String BATCH_NAME;
    String BUSINESS_DATE;
    String ERROR;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
    }

    public String getBATCH_NAME() {
        return BATCH_NAME;
    }

    public void setBATCH_NAME(String BATCH_NAME) {
        this.BATCH_NAME = BATCH_NAME;
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
    
      public ArrayList<BATCH_STATUS_ERROR>convertStringToArrayList(String result){
        
          ArrayList<BATCH_STATUS_ERROR> BATCH_STATUS_ERRORList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              BATCH_STATUS_ERROR appStatus=new BATCH_STATUS_ERROR();
              
              appStatus.setBATCH_NAME(record.split("~")[0]);
              appStatus.setBUSINESS_DATE(record.split("~")[1]);
              appStatus.setERROR(record.split("~")[2]);
              appStatus.setINSTITUTE_ID(record.split("~")[3]);
              
              BATCH_STATUS_ERRORList.add(appStatus);
          }
          
          
          return BATCH_STATUS_ERRORList;
      
      }
      
    
}
