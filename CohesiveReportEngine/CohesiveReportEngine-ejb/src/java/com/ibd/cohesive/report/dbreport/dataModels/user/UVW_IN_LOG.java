/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.user;

import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class UVW_IN_LOG {
    String MESSAGE_ID;
    String SERVICE;
    String OPERATION;
    String BUSINESS_ENTITY;
    String REQUEST_JSON;
    String TIME_STAMP;
    String SOURCE;

    public String getMESSAGE_ID() {
        return MESSAGE_ID;
    }

    public void setMESSAGE_ID(String MESSAGE_ID) {
        this.MESSAGE_ID = MESSAGE_ID;
    }

    public String getSERVICE() {
        return SERVICE;
    }

    public void setSERVICE(String SERVICE) {
        this.SERVICE = SERVICE;
    }

    public String getOPERATION() {
        return OPERATION;
    }

    public void setOPERATION(String OPERATION) {
        this.OPERATION = OPERATION;
    }

    public String getBUSINESS_ENTITY() {
        return BUSINESS_ENTITY;
    }

    public void setBUSINESS_ENTITY(String BUSINESS_ENTITY) {
        this.BUSINESS_ENTITY = BUSINESS_ENTITY;
    }

    public String getREQUEST_JSON() {
        return REQUEST_JSON;
    }

    public void setREQUEST_JSON(String REQUEST_JSON) {
        this.REQUEST_JSON = REQUEST_JSON;
    }

    public String getTIME_STAMP() {
        return TIME_STAMP;
    }

    public void setTIME_STAMP(String TIME_STAMP) {
        this.TIME_STAMP = TIME_STAMP;
    }

    public String getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(String SOURCE) {
        this.SOURCE = SOURCE;
    }
   
    
     public ArrayList<UVW_IN_LOG>convertStringToArrayList(String result){
        
          ArrayList<UVW_IN_LOG> UVW_IN_LOGList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              UVW_IN_LOG appStatus=new UVW_IN_LOG();
              
              appStatus.setBUSINESS_ENTITY(record.split("~")[0]);
              appStatus.setMESSAGE_ID(record.split("~")[1]);
              appStatus.setOPERATION(record.split("~")[2]);
              appStatus.setREQUEST_JSON(record.split("~")[3]);
              appStatus.setSERVICE(record.split("~")[4]);
              appStatus.setSOURCE(record.split("~")[5]);
              appStatus.setTIME_STAMP(record.split("~")[6]);
                     
              
              
              
              
           UVW_IN_LOGList.add(appStatus);
          }
          
        return UVW_IN_LOGList;
           
      
}
    

    
}
