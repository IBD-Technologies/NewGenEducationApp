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
public class IVW_SKILL_GRADE_MASTER {
    String INSTITUTE_ID;
    String GRADE;
    String DESCRIPTION;
    String VERSION_NUMBER;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
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

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }
    
    
      public ArrayList<IVW_SKILL_GRADE_MASTER>convertStringToArrayList(String result){
        
          ArrayList<IVW_SKILL_GRADE_MASTER> IVW_SKILL_GRADE_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_SKILL_GRADE_MASTER appStatus=new IVW_SKILL_GRADE_MASTER();
              
              appStatus.setDESCRIPTION(record.split("~")[0]);
              appStatus.setGRADE(record.split("~")[1]);
              appStatus.setINSTITUTE_ID(record.split("~")[2]);
              appStatus.setVERSION_NUMBER(record.split("~")[3]);
              
              
              
              
           IVW_SKILL_GRADE_MASTERList.add(appStatus);
          }
          
        return IVW_SKILL_GRADE_MASTERList;
           
      
}
    
    
    
   
    
    
    
}
