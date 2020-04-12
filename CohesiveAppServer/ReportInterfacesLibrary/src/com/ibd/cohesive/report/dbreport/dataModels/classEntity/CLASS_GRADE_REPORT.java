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
public class CLASS_GRADE_REPORT {
    String STANDARD; 
    String SECTION;
    String SUBJECT_ID;
    String EXAM;
    String GRADE;
    String NO_OF_STUDENTS;

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

    public String getSUBJECT_ID() {
        return SUBJECT_ID;
    }

    public void setSUBJECT_ID(String SUBJECT_ID) {
        this.SUBJECT_ID = SUBJECT_ID;
    }

    public String getEXAM() {
        return EXAM;
    }

    public void setEXAM(String EXAM) {
        this.EXAM = EXAM;
    }

    public String getGRADE() {
        return GRADE;
    }

    public void setGRADE(String GRADE) {
        this.GRADE = GRADE;
    }

    public String getNO_OF_STUDENTS() {
        return NO_OF_STUDENTS;
    }

    public void setNO_OF_STUDENTS(String NO_OF_STUDENTS) {
        this.NO_OF_STUDENTS = NO_OF_STUDENTS;
    }
    
    
    public ArrayList<CLASS_GRADE_REPORT>convertStringToArrayList(String result){
        
          ArrayList<CLASS_GRADE_REPORT> CLASS_GRADE_REPORTList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              CLASS_GRADE_REPORT appStatus=new CLASS_GRADE_REPORT();
              
              appStatus.setEXAM(record.split("~")[0]);
              appStatus.setGRADE(record.split("~")[1]);
              appStatus.setNO_OF_STUDENTS(record.split("~")[2]);
              appStatus.setSECTION(record.split("~")[3]);
              appStatus.setSTANDARD(record.split("~")[4]);
              appStatus.setSUBJECT_ID(record.split("~")[5]);
                          
              
              
              
              
              
              
           CLASS_GRADE_REPORTList.add(appStatus);
          }
          
        return CLASS_GRADE_REPORTList;
           
      
}
    
    
    
    
    
    
    
}
