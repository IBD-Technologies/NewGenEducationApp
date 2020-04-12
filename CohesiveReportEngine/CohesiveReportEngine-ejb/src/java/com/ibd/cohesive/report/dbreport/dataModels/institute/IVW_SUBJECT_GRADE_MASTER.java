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
public class IVW_SUBJECT_GRADE_MASTER {
    String SUBJECT_ID;
    String GRADE;
    String DESCRIPTION;

    public String getSUBJECT_ID() {
        return SUBJECT_ID;
    }

    public void setSUBJECT_ID(String SUBJECT_ID) {
        this.SUBJECT_ID = SUBJECT_ID;
    }

    public String getGRADE() {
        return GRADE;
    }

    public void setGRADE(String GRADE) {
        this.GRADE = GRADE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }
    
    
    public ArrayList<IVW_SUBJECT_GRADE_MASTER>convertStringToArrayList(String result){
        
          ArrayList<IVW_SUBJECT_GRADE_MASTER> IVW_SUBJECT_GRADE_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_SUBJECT_GRADE_MASTER appStatus=new IVW_SUBJECT_GRADE_MASTER();
              
              appStatus.setDESCRIPTION(record.split("~")[0]);
              appStatus.setGRADE(record.split("~")[1]);
              appStatus.setSUBJECT_ID(record.split("~")[2]);
             

              
              
              
           IVW_SUBJECT_GRADE_MASTERList.add(appStatus);
          }
          
        return IVW_SUBJECT_GRADE_MASTERList;
           
      
}
    
}
