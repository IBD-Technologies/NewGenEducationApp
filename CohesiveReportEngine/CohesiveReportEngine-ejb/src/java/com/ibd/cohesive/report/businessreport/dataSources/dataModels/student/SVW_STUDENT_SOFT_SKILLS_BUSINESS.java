/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.dataModels.student;

import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class SVW_STUDENT_SOFT_SKILLS_BUSINESS {
    String STUDENT_ID;
    String EXAM;
    String SKILL_ID;
    String CATEGORY;
    String TEACHER_FEEDBACK;
    String VERSION_NUMBER;
    int CATEGORY_VALUE;

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
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

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }


    public String getTEACHER_FEEDBACK() {
        return TEACHER_FEEDBACK;
    }

    public void setTEACHER_FEEDBACK(String TEACHER_FEEDBACK) {
        this.TEACHER_FEEDBACK = TEACHER_FEEDBACK;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }

    public int getCATEGORY_VALUE() {
        return CATEGORY_VALUE;
    }

    public void setCATEGORY_VALUE(int CATEGORY_VALUE) {
        this.CATEGORY_VALUE = CATEGORY_VALUE;
    }
    
    
    
    public ArrayList<SVW_STUDENT_SOFT_SKILLS_BUSINESS>convertStringToArrayList(String result){
        
          ArrayList<SVW_STUDENT_SOFT_SKILLS_BUSINESS> SVW_STUDENT_SOFT_SKILLS_BUSINESSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              SVW_STUDENT_SOFT_SKILLS_BUSINESS appStatus=new SVW_STUDENT_SOFT_SKILLS_BUSINESS();
              
              appStatus.setEXAM(record.split("~")[0]);
              appStatus.setCATEGORY(record.split("~")[1]);
              appStatus.setSTUDENT_ID(record.split("~")[2]);
              appStatus.setSKILL_ID(record.split("~")[3]);
              appStatus.setTEACHER_FEEDBACK(record.split("~")[4]);
              appStatus.setVERSION_NUMBER(record.split("~")[5]);
              appStatus.setCATEGORY_VALUE(Integer.parseInt(record.split("~")[6]));
              
              SVW_STUDENT_SOFT_SKILLS_BUSINESSList.add(appStatus);
          }
          
          
          return SVW_STUDENT_SOFT_SKILLS_BUSINESSList;
      
      }
}
