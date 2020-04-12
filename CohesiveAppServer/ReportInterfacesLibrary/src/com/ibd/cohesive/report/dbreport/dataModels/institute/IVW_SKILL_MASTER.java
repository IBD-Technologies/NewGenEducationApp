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
public class IVW_SKILL_MASTER {
    String INSTITUTE_ID;
    String SKILL_ID;
    String SKILL_NAME;
    String VERSION_NUMBER;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
    }

    public String getSKILL_ID() {
        return SKILL_ID;
    }

    public void setSKILL_ID(String SKILL_ID) {
        this.SKILL_ID = SKILL_ID;
    }

    public String getSKILL_NAME() {
        return SKILL_NAME;
    }

    public void setSKILL_NAME(String SKILL_NAME) {
        this.SKILL_NAME = SKILL_NAME;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }
    
    
    
    
    
    
     public ArrayList<IVW_SKILL_MASTER>convertStringToArrayList(String result){
        
          ArrayList<IVW_SKILL_MASTER> IVW_SKILL_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_SKILL_MASTER appStatus=new IVW_SKILL_MASTER();
              
              appStatus.setINSTITUTE_ID(record.split("~")[0]);
              appStatus.setSKILL_ID(record.split("~")[1]);
              appStatus.setSKILL_NAME(record.split("~")[2]);
              appStatus.setVERSION_NUMBER(record.split("~")[3]);
              
              
              
              
           IVW_SKILL_MASTERList.add(appStatus);
          }
          
        return IVW_SKILL_MASTERList;
           
      
}
   
    
    
    
    
    
}
