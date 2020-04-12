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
public class IVW_TEACHER_LEAVE_DETAILS {
    String TEACHER_ID;
    String FULL_DAY;        
    String NOON;

    public String getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(String TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
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
    
      public ArrayList<IVW_TEACHER_LEAVE_DETAILS>convertStringToArrayList(String result){
        
          ArrayList<IVW_TEACHER_LEAVE_DETAILS> IVW_TEACHER_LEAVE_DETAILSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_TEACHER_LEAVE_DETAILS appStatus=new IVW_TEACHER_LEAVE_DETAILS();
              
              appStatus.setFULL_DAY(record.split("~")[0]);
              appStatus.setNOON(record.split("~")[1]);
              appStatus.setTEACHER_ID(record.split("~")[2]);
              
              
              
              
           IVW_TEACHER_LEAVE_DETAILSList.add(appStatus);
          }
          
        return IVW_TEACHER_LEAVE_DETAILSList;
           
      
}

    
    
}
