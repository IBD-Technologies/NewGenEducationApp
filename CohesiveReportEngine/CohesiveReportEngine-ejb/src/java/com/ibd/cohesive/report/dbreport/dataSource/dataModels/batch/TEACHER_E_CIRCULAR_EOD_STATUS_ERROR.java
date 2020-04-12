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
public class TEACHER_E_CIRCULAR_EOD_STATUS_ERROR {
    String INSTITUTE_ID;
    String E_CIRCULAR_ID;
    String TEACHER_ID;
    String BUSINESS_DATE;
    String ERROR;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
    }

    public String getE_CIRCULAR_ID() {
        return E_CIRCULAR_ID;
    }

    public void setE_CIRCULAR_ID(String E_CIRCULAR_ID) {
        this.E_CIRCULAR_ID = E_CIRCULAR_ID;
    }

    

    public String getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(String TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
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
    public ArrayList<TEACHER_E_CIRCULAR_EOD_STATUS_ERROR>convertStringToArrayList(String result){
        
          ArrayList<TEACHER_E_CIRCULAR_EOD_STATUS_ERROR> TEACHER_E_CIRCULAR_EOD_STATUS_ERRORList=new ArrayList();
          
         
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              TEACHER_E_CIRCULAR_EOD_STATUS_ERROR appStatus=new TEACHER_E_CIRCULAR_EOD_STATUS_ERROR();
              
              appStatus.setE_CIRCULAR_ID(record.split("~")[0]);
              appStatus.setBUSINESS_DATE(record.split("~")[1]);
              appStatus.setERROR(record.split("~")[2]);
              appStatus.setINSTITUTE_ID(record.split("~")[3]);
              appStatus.setTEACHER_ID(record.split("~")[4]);
              
              
              TEACHER_E_CIRCULAR_EOD_STATUS_ERRORList.add(appStatus);
          }
          
          
          return TEACHER_E_CIRCULAR_EOD_STATUS_ERRORList;
      
      }
}
