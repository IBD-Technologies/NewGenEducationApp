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
public class APP_EOD_STATUS_ERROR {
    String BUSINESS_DATE;
    String ERROR;

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
    

      public ArrayList<APP_EOD_STATUS_ERROR>convertStringToArrayList(String result){
        
          ArrayList<APP_EOD_STATUS_ERROR> APP_EOD_STATUS_ERRORList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];
//              String BUSINESS_DATE=record.split("~")[0];
//              String START_TIME=record.split("~")[1];
//              String END_TIME=record.split("~")[2];
//              String EOD_STATUS=record.split("~")[3];
//              String ERROR=record.split("~")[4];
//              String NO_OF_SUCCESS=record.split("~")[5];
//              String NO_OF_FAILURES=record.split("~")[6];
//              String SEQUENCE_NO=record.split("~")[7];
              
              
              APP_EOD_STATUS_ERROR appStatus=new APP_EOD_STATUS_ERROR();
              
              appStatus.setBUSINESS_DATE(record.split("~")[0]);
              appStatus.setERROR(record.split("~")[1]);
              
              APP_EOD_STATUS_ERRORList.add(appStatus);
          }
          
          
          return APP_EOD_STATUS_ERRORList;
      
      }    
}
