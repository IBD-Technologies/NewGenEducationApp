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
public class RETENTION_PERIOD {
    String INSTITUTE_ID;
    String FEATURE_NAME;
    String DAYS;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
    }

    public String getFEATURE_NAME() {
        return FEATURE_NAME;
    }

    public void setFEATURE_NAME(String FEATURE_NAME) {
        this.FEATURE_NAME = FEATURE_NAME;
    }

    public String getDAYS() {
        return DAYS;
    }

    public void setDAYS(String DAYS) {
        this.DAYS = DAYS;
    }
    
    
    public ArrayList<RETENTION_PERIOD>convertStringToArrayList(String result){
        
          ArrayList<RETENTION_PERIOD> RETENTION_PERIODList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              RETENTION_PERIOD appStatus=new RETENTION_PERIOD();
              
              appStatus.setDAYS(record.split("~")[0]);
              appStatus.setFEATURE_NAME(record.split("~")[1]);
              appStatus.setINSTITUTE_ID(record.split("~")[2]);
              
              
              
              
           RETENTION_PERIODList.add(appStatus);
          }
          
        return RETENTION_PERIODList;
           
      
}
    
    
}
