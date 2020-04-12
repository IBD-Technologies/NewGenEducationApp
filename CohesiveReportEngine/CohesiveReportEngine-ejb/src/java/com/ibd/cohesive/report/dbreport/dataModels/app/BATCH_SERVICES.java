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
public class BATCH_SERVICES {
    String BATCH_NAME;
    String BATCH_DESCRIPTION;

    public String getBATCH_NAME() {
        return BATCH_NAME;
    }

    public void setBATCH_NAME(String BATCH_NAME) {
        this.BATCH_NAME = BATCH_NAME;
    }

    public String getBATCH_DESCRIPTION() {
        return BATCH_DESCRIPTION;
    }

    public void setBATCH_DESCRIPTION(String BATCH_DESCRIPTION) {
        this.BATCH_DESCRIPTION = BATCH_DESCRIPTION;
    }
    
     public ArrayList<BATCH_SERVICES>convertStringToArrayList(String result){
        
          ArrayList<BATCH_SERVICES> BATCH_SERVICESList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              BATCH_SERVICES appStatus=new BATCH_SERVICES();
              
              appStatus.setBATCH_DESCRIPTION(record.split("~")[0]);
              appStatus.setBATCH_NAME(record.split("~")[1]);
              
              
              
              
              
              
           BATCH_SERVICESList.add(appStatus);
          }
          
        return BATCH_SERVICESList;
           
      
}
    
    
    
}
