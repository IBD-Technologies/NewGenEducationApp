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
public class IVW_STUDENT_LEAVE_DETAILS {
    String  STUDENT_ID;
    String STANDARD;
    String SECTION;
    String FULL_DAY;
    String NOON;

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

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

    public String getFULL_DAY() {
        return FULL_DAY;
    }

    public void setFULL_DAY(String FULL_DAY) {
        this.FULL_DAY = FULL_DAY;
    }

    public String getNOON() {
        return NOON;
    }

    public void setNOON(String NOON) {
        this.NOON = NOON;
    }
    
    
    public ArrayList<IVW_STUDENT_LEAVE_DETAILS>convertStringToArrayList(String result){
        
          ArrayList<IVW_STUDENT_LEAVE_DETAILS> IVW_STUDENT_LEAVE_DETAILSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_STUDENT_LEAVE_DETAILS appStatus=new IVW_STUDENT_LEAVE_DETAILS();
              
              appStatus.setFULL_DAY(record.split("~")[0]);
              appStatus.setNOON(record.split("~")[1]);
              appStatus.setSECTION(record.split("~")[2]);
              appStatus.setSTANDARD(record.split("~")[3]);
              appStatus.setSTUDENT_ID(record.split("~")[4]);
              
              
              
           IVW_STUDENT_LEAVE_DETAILSList.add(appStatus);
          }
          
        return IVW_STUDENT_LEAVE_DETAILSList;
           
      
}

    
    
    
}
