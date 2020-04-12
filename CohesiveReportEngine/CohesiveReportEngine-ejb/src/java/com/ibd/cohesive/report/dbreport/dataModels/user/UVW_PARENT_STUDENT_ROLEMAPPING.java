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
public class UVW_PARENT_STUDENT_ROLEMAPPING {
    String USER_ID;
    String ROLE_ID;
    String STUDENT_ID;
    String VERSION_NUMBER;
    String INSTITUTE_ID;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
    }
    
    

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getROLE_ID() {
        return ROLE_ID;
    }

    public void setROLE_ID(String ROLE_ID) {
        this.ROLE_ID = ROLE_ID;
    }

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }
    
    
      public ArrayList<UVW_PARENT_STUDENT_ROLEMAPPING>convertStringToArrayList(String result){
        
          ArrayList<UVW_PARENT_STUDENT_ROLEMAPPING> UVW_PARENT_STUDENT_ROLEMAPPINGList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              UVW_PARENT_STUDENT_ROLEMAPPING appStatus=new UVW_PARENT_STUDENT_ROLEMAPPING();
              
              appStatus.setINSTITUTE_ID(record.split("~")[0]);
              appStatus.setROLE_ID(record.split("~")[1]);
              appStatus.setSTUDENT_ID(record.split("~")[2]);
              appStatus.setUSER_ID(record.split("~")[3]);
              appStatus.setVERSION_NUMBER(record.split("~")[4]);
             
              
              
              
           UVW_PARENT_STUDENT_ROLEMAPPINGList.add(appStatus);
          }
          
        return UVW_PARENT_STUDENT_ROLEMAPPINGList;
           
      
}
    

}
