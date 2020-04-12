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
public class APP_SUPPORT {
    String NAME;
    String EMAIL_ID;
    String MOBILE_NO;

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getEMAIL_ID() {
        return EMAIL_ID;
    }

    public void setEMAIL_ID(String EMAIL_ID) {
        this.EMAIL_ID = EMAIL_ID;
    }

    public String getMOBILE_NO() {
        return MOBILE_NO;
    }

    public void setMOBILE_NO(String MOBILE_NO) {
        this.MOBILE_NO = MOBILE_NO;
    }
    
     public ArrayList<APP_SUPPORT>convertStringToArrayList(String result){
        
          ArrayList<APP_SUPPORT> APP_SUPPORTList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              APP_SUPPORT appStatus=new APP_SUPPORT();
              
              appStatus.setEMAIL_ID(record.split("~")[0]);
              appStatus.setMOBILE_NO(record.split("~")[1]);
              appStatus.setNAME(record.split("~")[2]);
              
                          
              
              
              
              
              
           APP_SUPPORTList.add(appStatus);
          }
          
        return APP_SUPPORTList;
           
      
}
    
    
}
