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
public class IVW_PERIOD_MASTER {
    String STANDARD;
    String SECTION;
    String PERIOD_NUMBER;
    String STARTING_TIME;
    String ENDING_TIME;

    public String getSTANDARD() {
        return STANDARD;
    }

    public void setSTANDARD(String STANDARD) {
        this.STANDARD = STANDARD;
    }

    public String getSECTION() {
        return SECTION;
    }

    public void setSECTION(String SECTION) {
        this.SECTION = SECTION;
    }

    public String getPERIOD_NUMBER() {
        return PERIOD_NUMBER;
    }

    public void setPERIOD_NUMBER(String PERIOD_NUMBER) {
        this.PERIOD_NUMBER = PERIOD_NUMBER;
    }

    public String getSTARTING_TIME() {
        return STARTING_TIME;
    }

    public void setSTARTING_TIME(String STARTING_TIME) {
        this.STARTING_TIME = STARTING_TIME;
    }

    public String getENDING_TIME() {
        return ENDING_TIME;
    }

    public void setENDING_TIME(String ENDING_TIME) {
        this.ENDING_TIME = ENDING_TIME;
    }
    
      public ArrayList<IVW_PERIOD_MASTER>convertStringToArrayList(String result){
        
          ArrayList<IVW_PERIOD_MASTER> IVW_PERIOD_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_PERIOD_MASTER appStatus=new IVW_PERIOD_MASTER();
              
              appStatus.setENDING_TIME(record.split("~")[0]);
              appStatus.setPERIOD_NUMBER(record.split("~")[1]);
              appStatus.setSECTION(record.split("~")[2]);
              appStatus.setSTANDARD(record.split("~")[3]);
              appStatus.setSTARTING_TIME(record.split("~")[4]);

              
              
              
           IVW_PERIOD_MASTERList.add(appStatus);
          }
          
        return IVW_PERIOD_MASTERList;
           
      
}
    
    
    
}
