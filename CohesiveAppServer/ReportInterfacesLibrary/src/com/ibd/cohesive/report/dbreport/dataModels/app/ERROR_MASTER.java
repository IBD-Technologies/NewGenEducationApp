/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.app;

import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class ERROR_MASTER {
    String ERROR_CODE;
    String ERROR_MESSAGE;

    public String getERROR_CODE() {
        return ERROR_CODE;
    }

    public void setERROR_CODE(String ERROR_CODE) {
        this.ERROR_CODE = ERROR_CODE;
    }

    public String getERROR_MESSAGE() {
        return ERROR_MESSAGE;
    }

    public void setERROR_MESSAGE(String ERROR_MESSAGE) {
        this.ERROR_MESSAGE = ERROR_MESSAGE;
    }
    
    public ArrayList<ERROR_MASTER> convertStringToArrayList(String result){
        
          ArrayList<ERROR_MASTER> ERROR_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              ERROR_MASTER appStatus=new ERROR_MASTER();
              
              appStatus.setERROR_CODE(record.split("~")[0]);
              appStatus.setERROR_MESSAGE(record.split("~")[1]);
              
              
              
              
              
              
           ERROR_MASTERList.add(appStatus);
          }
          
        return ERROR_MASTERList;
           
      
}
    
    
    
}
