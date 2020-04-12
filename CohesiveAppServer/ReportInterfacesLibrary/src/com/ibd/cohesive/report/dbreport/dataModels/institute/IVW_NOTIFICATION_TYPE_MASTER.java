/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.institute;

import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class IVW_NOTIFICATION_TYPE_MASTER {
    String NOTIFICATION_TYPE;

    public String getNOTIFICATION_TYPE() {
        return NOTIFICATION_TYPE;
    }

    public void setNOTIFICATION_TYPE(String NOTIFICATION_TYPE) {
        this.NOTIFICATION_TYPE = NOTIFICATION_TYPE;
    }
    
      public ArrayList<IVW_NOTIFICATION_TYPE_MASTER>convertStringToArrayList(String result){
        
          ArrayList<IVW_NOTIFICATION_TYPE_MASTER> IVW_NOTIFICATION_TYPE_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_NOTIFICATION_TYPE_MASTER appStatus=new IVW_NOTIFICATION_TYPE_MASTER();
              
              appStatus.setNOTIFICATION_TYPE(record.split("~")[0]);
              
              
              
              
              
           IVW_NOTIFICATION_TYPE_MASTERList.add(appStatus);
          }
          
        return IVW_NOTIFICATION_TYPE_MASTERList;
           
      
}
    
    
}
