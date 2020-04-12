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
public class UVW_USER_ROLE_INSTITUTE {
    String USER_ID;
    String INSTITUTE_ID;
    String VERSION_NUMBER;

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }
    
    
     public ArrayList<UVW_USER_ROLE_INSTITUTE>convertStringToArrayList(String result){
        
          ArrayList<UVW_USER_ROLE_INSTITUTE> UVW_USER_ROLE_INSTITUTEList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              UVW_USER_ROLE_INSTITUTE appStatus=new UVW_USER_ROLE_INSTITUTE();
              
              appStatus.setINSTITUTE_ID(record.split("~")[0]);
              appStatus.setUSER_ID(record.split("~")[1]);
              appStatus.setVERSION_NUMBER(record.split("~")[2]);
              
             
              
              
              
           UVW_USER_ROLE_INSTITUTEList.add(appStatus);
          }
          
        return UVW_USER_ROLE_INSTITUTEList;
           
      
}
    
    
    
    
    
    
    
    
    
}
