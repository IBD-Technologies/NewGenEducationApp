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
public class NOTIFICATION_BATCH_INDICATOR {
    
    String NOTIFICATION_ID;
    String STATUS;

    public String getNOTIFICATION_ID() {
        return NOTIFICATION_ID;
    }

    public void setNOTIFICATION_ID(String NOTIFICATION_ID) {
        this.NOTIFICATION_ID = NOTIFICATION_ID;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
    
     public ArrayList<NOTIFICATION_BATCH_INDICATOR>convertStringToArrayList(String result){
        
          ArrayList<NOTIFICATION_BATCH_INDICATOR> NOTIFICATION_BATCH_INDICATORList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              NOTIFICATION_BATCH_INDICATOR appStatus=new NOTIFICATION_BATCH_INDICATOR();
              
              appStatus.setNOTIFICATION_ID(record.split("~")[0]);
              appStatus.setSTATUS(record.split("~")[1]);
              
              
              
              
           NOTIFICATION_BATCH_INDICATORList.add(appStatus);
          }
          
        return NOTIFICATION_BATCH_INDICATORList;
           
      
}
   
    
    
    
}
