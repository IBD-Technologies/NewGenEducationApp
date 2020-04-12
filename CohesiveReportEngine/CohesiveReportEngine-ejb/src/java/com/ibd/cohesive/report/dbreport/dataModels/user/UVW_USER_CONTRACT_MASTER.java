/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.user;

import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class UVW_USER_CONTRACT_MASTER {
    String USER_ID;
    String CONTRACT_ID;

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getCONTRACT_ID() {
        return CONTRACT_ID;
    }

    public void setCONTRACT_ID(String CONTRACT_ID) {
        this.CONTRACT_ID = CONTRACT_ID;
    }
    
    public ArrayList<UVW_USER_CONTRACT_MASTER>convertStringToArrayList(String result){
        
          ArrayList<UVW_USER_CONTRACT_MASTER> UVW_USER_CONTRACT_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              UVW_USER_CONTRACT_MASTER appStatus=new UVW_USER_CONTRACT_MASTER();
              
              appStatus.setCONTRACT_ID(record.split("~")[0]);
              appStatus.setUSER_ID(record.split("~")[1]);
             
              
             
              
              
              
           UVW_USER_CONTRACT_MASTERList.add(appStatus);
          }
          
        return UVW_USER_CONTRACT_MASTERList;
           
      
}
    
    
    
    
    
    
    
    
    
    
    
}
