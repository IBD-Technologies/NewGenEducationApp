/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.classEntity;

import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class STUDENT_SKILLS {
    String STANDARD; 
    String SECTION;
    String EXAM;
    String SKILL_ID;
    String STUDENT_ID;
    String GRADE;
    String FEEDBACK;
    String VERSION_NUMBER;

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

    public String getEXAM() {
        return EXAM;
    }

    public void setEXAM(String EXAM) {
        this.EXAM = EXAM;
    }

    public String getSKILL_ID() {
        return SKILL_ID;
    }

    public void setSKILL_ID(String SKILL_ID) {
        this.SKILL_ID = SKILL_ID;
    }

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getGRADE() {
        return GRADE;
    }

    public void setGRADE(String GRADE) {
        this.GRADE = GRADE;
    }

    public String getFEEDBACK() {
        return FEEDBACK;
    }

    public void setFEEDBACK(String FEEDBACK) {
        this.FEEDBACK = FEEDBACK;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }
    
    public ArrayList<STUDENT_SKILLS>convertStringToArrayList(String result){
        
          ArrayList<STUDENT_SKILLS> STUDENT_SKILLSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              STUDENT_SKILLS appStatus=new STUDENT_SKILLS();
              
              appStatus.setEXAM(record.split("~")[0]);
              appStatus.setFEEDBACK(record.split("~")[1]);
              appStatus.setGRADE(record.split("~")[2]);
              appStatus.setSECTION(record.split("~")[3]);
              appStatus.setSKILL_ID(record.split("~")[4]);
              appStatus.setSTANDARD(record.split("~")[5]);
              appStatus.setSTUDENT_ID(record.split("~")[6]);
              appStatus.setVERSION_NUMBER(record.split("~")[7]);
                           
              
              
           STUDENT_SKILLSList.add(appStatus);
          }
          
        return STUDENT_SKILLSList;
           
      
}
    
    
    
    
    
    
    
    
    
    
    
    
}
