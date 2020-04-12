/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.app;


import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class APP_RETENTION_PERIOD {
    String FEATURE_NAME;
    String DAYS;

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
    
     public ArrayList<APP_RETENTION_PERIOD> convertStringToArrayList(String result){
        
          ArrayList<APP_RETENTION_PERIOD> APP_RETENTION_PERIODList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              APP_RETENTION_PERIOD appStatus=new APP_RETENTION_PERIOD();
              
              appStatus.setDAYS(record.split("~")[0]);
              appStatus.setFEATURE_NAME(record.split("~")[1]);
              
              
              
              
              
              
           APP_RETENTION_PERIODList.add(appStatus);
          }
          
        return APP_RETENTION_PERIODList;
           
      
}
    
    
    
}
