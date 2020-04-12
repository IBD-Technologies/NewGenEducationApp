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
public class IVW_UNAUTH_RECORDS {
    
    String SERVICE;
    String OPERATION;
    String ENTITY_NAME;
    String ENTITY_VALUE;
    String PRIMARY_KEY;

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

    public String getENTITY_NAME() {
        return ENTITY_NAME;
    }

    public void setENTITY_NAME(String ENTITY_NAME) {
        this.ENTITY_NAME = ENTITY_NAME;
    }

    public String getENTITY_VALUE() {
        return ENTITY_VALUE;
    }

    public void setENTITY_VALUE(String ENTITY_VALUE) {
        this.ENTITY_VALUE = ENTITY_VALUE;
    }

    public String getPRIMARY_KEY() {
        return PRIMARY_KEY;
    }

    public void setPRIMARY_KEY(String PRIMARY_KEY) {
        this.PRIMARY_KEY = PRIMARY_KEY;
    }
    
    
  public ArrayList<IVW_UNAUTH_RECORDS>convertStringToArrayList(String result){
        
          ArrayList<IVW_UNAUTH_RECORDS> IVW_UNAUTH_RECORDSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_UNAUTH_RECORDS appStatus=new IVW_UNAUTH_RECORDS();
              
              appStatus.setENTITY_NAME(record.split("~")[0]);
              appStatus.setENTITY_VALUE(record.split("~")[1]);
              appStatus.setOPERATION(record.split("~")[2]);
              appStatus.setPRIMARY_KEY(record.split("~")[3]);
              appStatus.setSERVICE(record.split("~")[4]);
              
              
              
           IVW_UNAUTH_RECORDSList.add(appStatus);
          }
          
        return IVW_UNAUTH_RECORDSList;
           
      
}





 }