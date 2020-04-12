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
public class SERVICE_TYPE_MASTER {
    String SERVICE_NAME;
    String SERVICE_TYPE;

    public String getSERVICE_NAME() {
        return SERVICE_NAME;
    }

    public void setSERVICE_NAME(String SERVICE_NAME) {
        this.SERVICE_NAME = SERVICE_NAME;
    }

    public String getSERVICE_TYPE() {
        return SERVICE_TYPE;
    }

    public void setSERVICE_TYPE(String SERVICE_TYPE) {
        this.SERVICE_TYPE = SERVICE_TYPE;
    }
    
    
     public ArrayList<SERVICE_TYPE_MASTER> convertStringToArrayList(String result){
        
          ArrayList<SERVICE_TYPE_MASTER> SERVICE_TYPE_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              SERVICE_TYPE_MASTER appStatus=new SERVICE_TYPE_MASTER();
              
              appStatus.setSERVICE_NAME(record.split("~")[0]);
              appStatus.setSERVICE_TYPE(record.split("~")[1]);
              
              
              
              
              
              
           SERVICE_TYPE_MASTERList.add(appStatus);
          }
          
        return SERVICE_TYPE_MASTERList;
           
      
}

}
