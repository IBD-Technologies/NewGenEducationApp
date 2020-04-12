/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.teacher;

import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class TVW_EXISTING_MEDICAL_DETAILS {
    String TEACHER_ID;
    String MEDICAL_DETAIL_ID;
    String MEDICAL_DETAILS;
    String VERSION_NUMBER;

    public String getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(String TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
    }

    public String getMEDICAL_DETAIL_ID() {
        return MEDICAL_DETAIL_ID;
    }

    public void setMEDICAL_DETAIL_ID(String MEDICAL_DETAIL_ID) {
        this.MEDICAL_DETAIL_ID = MEDICAL_DETAIL_ID;
    }

    public String getMEDICAL_DETAILS() {
        return MEDICAL_DETAILS;
    }

    public void setMEDICAL_DETAILS(String MEDICAL_DETAILS) {
        this.MEDICAL_DETAILS = MEDICAL_DETAILS;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }
    
    
     public ArrayList<TVW_EXISTING_MEDICAL_DETAILS>convertStringToArrayList(String result){
        
          ArrayList<TVW_EXISTING_MEDICAL_DETAILS> TVW_EXISTING_MEDICAL_DETAILSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              TVW_EXISTING_MEDICAL_DETAILS appStatus=new TVW_EXISTING_MEDICAL_DETAILS();
              
              appStatus.setMEDICAL_DETAILS(record.split("~")[0]);
              appStatus.setMEDICAL_DETAIL_ID(record.split("~")[1]);
              appStatus.setTEACHER_ID(record.split("~")[2]);
              appStatus.setVERSION_NUMBER(record.split("~")[3]);
                             
              
              
              
              
           TVW_EXISTING_MEDICAL_DETAILSList.add(appStatus);
          }
          
        return TVW_EXISTING_MEDICAL_DETAILSList;
           
      
}
     
}
