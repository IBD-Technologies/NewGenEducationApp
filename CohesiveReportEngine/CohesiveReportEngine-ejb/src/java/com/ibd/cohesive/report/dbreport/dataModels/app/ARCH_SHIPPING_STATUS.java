/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.app;


import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class ARCH_SHIPPING_STATUS {
    String FILE_NAME;
    String SEQUENCE_NO;
    String CHECKSUM_STATUS;
    String SENT_STATUS;
    String APPLY_STATUS;
    String ERROR;

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public String getSEQUENCE_NO() {
        return SEQUENCE_NO;
    }

    public void setSEQUENCE_NO(String SEQUENCE_NO) {
        this.SEQUENCE_NO = SEQUENCE_NO;
    }

    public String getCHECKSUM_STATUS() {
        return CHECKSUM_STATUS;
    }

    public void setCHECKSUM_STATUS(String CHECKSUM_STATUS) {
        this.CHECKSUM_STATUS = CHECKSUM_STATUS;
    }

    public String getSENT_STATUS() {
        return SENT_STATUS;
    }

    public void setSENT_STATUS(String SENT_STATUS) {
        this.SENT_STATUS = SENT_STATUS;
    }

    public String getAPPLY_STATUS() {
        return APPLY_STATUS;
    }

    public void setAPPLY_STATUS(String APPLY_STATUS) {
        this.APPLY_STATUS = APPLY_STATUS;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }
    
     public ArrayList<ARCH_SHIPPING_STATUS>convertStringToArrayList(String result){
        
          ArrayList<ARCH_SHIPPING_STATUS> ARCH_SHIPPING_STATUSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              ARCH_SHIPPING_STATUS appStatus=new ARCH_SHIPPING_STATUS();
              
              appStatus.setAPPLY_STATUS(record.split("~")[0]);
              appStatus.setCHECKSUM_STATUS(record.split("~")[1]);
              appStatus.setERROR(record.split("~")[2]);
              appStatus.setFILE_NAME(record.split("~")[3]);
              appStatus.setSENT_STATUS(record.split("~")[4]);
              appStatus.setSEQUENCE_NO(record.split("~")[5]);
                          
              
              
              
              
           ARCH_SHIPPING_STATUSList.add(appStatus);
          }
          
        return ARCH_SHIPPING_STATUSList;
           
      
}    
    
    
    
    
    
    
}
