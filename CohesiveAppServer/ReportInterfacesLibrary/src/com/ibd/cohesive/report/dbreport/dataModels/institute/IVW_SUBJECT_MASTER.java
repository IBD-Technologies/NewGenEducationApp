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
public class IVW_SUBJECT_MASTER {
    String SUBJECT_ID;
    String SUBJECT_NAME;

    public String getSUBJECT_ID() {
        return SUBJECT_ID;
    }

    public void setSUBJECT_ID(String SUBJECT_ID) {
        this.SUBJECT_ID = SUBJECT_ID;
    }

    public String getSUBJECT_NAME() {
        return SUBJECT_NAME;
    }

    public void setSUBJECT_NAME(String SUBJECT_NAME) {
        this.SUBJECT_NAME = SUBJECT_NAME;
    }
    
    
    
      public ArrayList<IVW_SUBJECT_MASTER>convertStringToArrayList(String result){
        
          ArrayList<IVW_SUBJECT_MASTER> IVW_SUBJECT_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_SUBJECT_MASTER appStatus=new IVW_SUBJECT_MASTER();
              
              appStatus.setSUBJECT_ID(record.split("~")[0]);
              appStatus.setSUBJECT_NAME(record.split("~")[1]);
              
              
              
              
           IVW_SUBJECT_MASTERList.add(appStatus);
          }
          
        return IVW_SUBJECT_MASTERList;
           
      
}
  
    
    
    
    
    
    
    
}
