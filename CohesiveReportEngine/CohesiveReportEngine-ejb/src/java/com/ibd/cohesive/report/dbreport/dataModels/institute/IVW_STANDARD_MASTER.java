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
public class IVW_STANDARD_MASTER {
    String STANDARD;
    String SECTION;
    String TEACHER_ID;

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

    public String getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(String TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
    }
    
    
    public ArrayList<IVW_STANDARD_MASTER>convertStringToArrayList(String result){
        
          ArrayList<IVW_STANDARD_MASTER> IVW_STANDARD_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_STANDARD_MASTER appStatus=new IVW_STANDARD_MASTER();
              
              appStatus.setSECTION(record.split("~")[0]);
              appStatus.setSTANDARD(record.split("~")[1]);
              appStatus.setTEACHER_ID(record.split("~")[2]);
              

              
              
              
           IVW_STANDARD_MASTERList.add(appStatus);
          }
          
        return IVW_STANDARD_MASTERList;
           
      
}
    
    
    
}
