/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.datSources.dataModels.classEntity;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class ClassMarksDetail {
    String STANDARD;
    String SECTION;
    String EXAM;
    String SUBJECT_ID;
    String STUDENT_ID;
    String GRADE;
    String MARK;
    String studentName;
    String subjectName;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
    

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

    public String getSUBJECT_ID() {
        return SUBJECT_ID;
    }

    public void setSUBJECT_ID(String SUBJECT_ID) {
        this.SUBJECT_ID = SUBJECT_ID;
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

    public String getMARK() {
        return MARK;
    }

    public void setMARK(String MARK) {
        this.MARK = MARK;
    }
    
    
    public ArrayList<ClassMarksDetail>convertStringToArrayList(String result){
        
          ArrayList<ClassMarksDetail> ClassMarksDetailList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              ClassMarksDetail appStatus=new ClassMarksDetail();
              
              appStatus.setEXAM(record.split("~")[0]);
              appStatus.setGRADE(record.split("~")[1]);
              appStatus.setMARK(record.split("~")[2]);
              appStatus.setSECTION(record.split("~")[3]);
              appStatus.setSTANDARD(record.split("~")[4]);
              appStatus.setSTUDENT_ID(record.split("~")[5]);
              appStatus.setSUBJECT_ID(record.split("~")[6]);
              appStatus.setStudentName(record.split("~")[7]);
              appStatus.setSubjectName(record.split("~")[8]);
              
              
              
        ClassMarksDetailList.add(appStatus);
          }
          
        return ClassMarksDetailList;
           
      
}
    
    
    
    
}
