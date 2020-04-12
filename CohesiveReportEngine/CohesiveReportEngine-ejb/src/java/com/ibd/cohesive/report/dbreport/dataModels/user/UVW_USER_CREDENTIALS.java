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
public class UVW_USER_CREDENTIALS {
    String USER_ID;
    String PASSWORD;
    String EXPIRY_DATE;
    String VERSION_NUMBER;

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getEXPIRY_DATE() {
        return EXPIRY_DATE;
    }

    public void setEXPIRY_DATE(String EXPIRY_DATE) {
        this.EXPIRY_DATE = EXPIRY_DATE;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }

    
      public ArrayList<UVW_USER_CREDENTIALS>convertStringToArrayList(String result){
        
          ArrayList<UVW_USER_CREDENTIALS> UVW_USER_CREDENTIALSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              UVW_USER_CREDENTIALS appStatus=new UVW_USER_CREDENTIALS();
              
              appStatus.setEXPIRY_DATE(record.split("~")[0]);
              appStatus.setPASSWORD(record.split("~")[1]);
              appStatus.setUSER_ID(record.split("~")[2]);
              appStatus.setVERSION_NUMBER(record.split("~")[3]);
             
              
              
              
           UVW_USER_CREDENTIALSList.add(appStatus);
          }
          
        return UVW_USER_CREDENTIALSList;
           
      
}
    
    
    
    
}
