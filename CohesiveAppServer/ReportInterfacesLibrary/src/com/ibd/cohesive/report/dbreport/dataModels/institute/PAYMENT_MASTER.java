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
public class PAYMENT_MASTER {
    String PAYMENT_ID;
    String PAYMENT_DATE;
    String AUTH_STATUS;

    public String getPAYMENT_ID() {
        return PAYMENT_ID;
    }

    public void setPAYMENT_ID(String PAYMENT_ID) {
        this.PAYMENT_ID = PAYMENT_ID;
    }

    public String getPAYMENT_DATE() {
        return PAYMENT_DATE;
    }

    public void setPAYMENT_DATE(String PAYMENT_DATE) {
        this.PAYMENT_DATE = PAYMENT_DATE;
    }

    public String getAUTH_STATUS() {
        return AUTH_STATUS;
    }

    public void setAUTH_STATUS(String AUTH_STATUS) {
        this.AUTH_STATUS = AUTH_STATUS;
    }
    
     public ArrayList<PAYMENT_MASTER>convertStringToArrayList(String result){
        
          ArrayList<PAYMENT_MASTER> PAYMENT_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              PAYMENT_MASTER appStatus=new PAYMENT_MASTER();
              
              appStatus.setAUTH_STATUS(record.split("~")[0]);
              appStatus.setPAYMENT_DATE(record.split("~")[1]);
              appStatus.setPAYMENT_ID(record.split("~")[2]);
            
              
              
              
              
           PAYMENT_MASTERList.add(appStatus);
          }
          
        return PAYMENT_MASTERList;
           
      
}
    
    
    
    
    
    
    
    
    
    
}
