/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.dataModels.student;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class SVW_STUDENT_MARKS_BUSINESS {
    String STUDENT_ID;
    String EXAM;
    String SUBJECT_ID;
    String GRADE;
    String MARK;
    String TEACHER_FEEDBACK;
    String VERSION_NUMBER;
    int gradeNumericValue;

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

    public String getMARK() {
        return MARK;
    }

    public void setMARK(String MARK) {
        this.MARK = MARK;
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

    public int getGradeNumericValue() {
        return gradeNumericValue;
    }

    public void setGradeNumericValue(int gradeNumericValue) {
        this.gradeNumericValue = gradeNumericValue;
    }
    
    public ArrayList<SVW_STUDENT_MARKS_BUSINESS>convertStringToArrayList(String result){
        
          ArrayList<SVW_STUDENT_MARKS_BUSINESS> SVW_STUDENT_MARKS_BUSINESSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              SVW_STUDENT_MARKS_BUSINESS appStatus=new SVW_STUDENT_MARKS_BUSINESS();
              
              appStatus.setEXAM(record.split("~")[0]);
              appStatus.setGRADE(record.split("~")[1]);
              appStatus.setMARK(record.split("~")[2]);
              appStatus.setSTUDENT_ID(record.split("~")[3]);
              appStatus.setSUBJECT_ID(record.split("~")[4]);
              appStatus.setTEACHER_FEEDBACK(record.split("~")[5]);
              appStatus.setVERSION_NUMBER(record.split("~")[6]);
              
              SVW_STUDENT_MARKS_BUSINESSList.add(appStatus);
          }
          
          
          return SVW_STUDENT_MARKS_BUSINESSList;
      
      }
    
    
}
