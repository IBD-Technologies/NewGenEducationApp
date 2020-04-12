/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.institute;

import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class IVW_LEAVE_TYPE_MASTER {
    String LEAVE_TYPE;
    String DESCRIPTION;

    public String getLEAVE_TYPE() {
        return LEAVE_TYPE;
    }

    public void setLEAVE_TYPE(String LEAVE_TYPE) {
        this.LEAVE_TYPE = LEAVE_TYPE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }
    
      public ArrayList<IVW_LEAVE_TYPE_MASTER>convertStringToArrayList(String result){
        
          ArrayList<IVW_LEAVE_TYPE_MASTER> IVW_LEAVE_TYPE_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_LEAVE_TYPE_MASTER appStatus=new IVW_LEAVE_TYPE_MASTER();
              
              appStatus.setDESCRIPTION(record.split("~")[0]);
              appStatus.setLEAVE_TYPE(record.split("~")[1]);
              
              
              
              
           IVW_LEAVE_TYPE_MASTERList.add(appStatus);
          }
          
        return IVW_LEAVE_TYPE_MASTERList;
           
      
}
}
