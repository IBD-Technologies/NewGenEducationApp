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
public class IVW_INSTITUTE_EXAM_MASTER {
    String EXAM;

    public String getEXAM() {
        return EXAM;
    }

    public void setEXAM(String EXAM) {
        this.EXAM = EXAM;
    }
    
      public ArrayList<IVW_INSTITUTE_EXAM_MASTER>convertStringToArrayList(String result){
        
          ArrayList<IVW_INSTITUTE_EXAM_MASTER> IVW_INSTITUTE_EXAM_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_INSTITUTE_EXAM_MASTER appStatus=new IVW_INSTITUTE_EXAM_MASTER();
              
              appStatus.setEXAM(record.split("~")[0]);
              
              
              
              
              
           IVW_INSTITUTE_EXAM_MASTERList.add(appStatus);
          }
          
        return IVW_INSTITUTE_EXAM_MASTERList;
           
      
}
    
}
