/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch;

import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR {
    String INSTITUTE_ID;
    String STUDENT_ID;
    String BUSINESS_DATE;
    String ERROR;

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }
  
    
    

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
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

    
    
      public ArrayList<STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR>convertStringToArrayList(String result){
        
          ArrayList<STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR> STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERRORList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR appStatus=new STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR();
              
              appStatus.setBUSINESS_DATE(record.split("~")[0]);
              appStatus.setINSTITUTE_ID(record.split("~")[1]);
              appStatus.setSTUDENT_ID(record.split("~")[2]);
              appStatus.setERROR(record.split("~")[3]);
       
              STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERRORList.add(appStatus);
          }
          
          
          return STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERRORList;
      }
}
