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
public class UVW_OUT_LOG {
    String MESSAGE_ID;
    String CORRELATION_ID;
    String SERVICE;
    String OPERATION;
    String BUSINESS_ENTITY;
    String STATUS;
    String RESPONSE_JSON;
    String TIME_STAMP;
    String SOURCE;

    public String getMESSAGE_ID() {
        return MESSAGE_ID;
    }

    public void setMESSAGE_ID(String MESSAGE_ID) {
        this.MESSAGE_ID = MESSAGE_ID;
    }

    public String getCORRELATION_ID() {
        return CORRELATION_ID;
    }

    public void setCORRELATION_ID(String CORRELATION_ID) {
        this.CORRELATION_ID = CORRELATION_ID;
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

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getRESPONSE_JSON() {
        return RESPONSE_JSON;
    }

    public void setRESPONSE_JSON(String RESPONSE_JSON) {
        this.RESPONSE_JSON = RESPONSE_JSON;
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

    
    public ArrayList<UVW_OUT_LOG>convertStringToArrayList(String result){
        
          ArrayList<UVW_OUT_LOG> UVW_OUT_LOGList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              UVW_OUT_LOG appStatus=new UVW_OUT_LOG();
              
              appStatus.setBUSINESS_ENTITY(record.split("~")[0]);
              appStatus.setCORRELATION_ID(record.split("~")[1]);
              appStatus.setMESSAGE_ID(record.split("~")[2]);
              appStatus.setOPERATION(record.split("~")[3]);
              appStatus.setRESPONSE_JSON(record.split("~")[4]);
              appStatus.setSERVICE(record.split("~")[5]);
              appStatus.setSOURCE(record.split("~")[6]);
              appStatus.setSTATUS(record.split("~")[7]);
              appStatus.setTIME_STAMP(record.split("~")[8]);
              
              
              
              
           UVW_OUT_LOGList.add(appStatus);
          }
          
        return UVW_OUT_LOGList;
           
      
}
    
    
}
