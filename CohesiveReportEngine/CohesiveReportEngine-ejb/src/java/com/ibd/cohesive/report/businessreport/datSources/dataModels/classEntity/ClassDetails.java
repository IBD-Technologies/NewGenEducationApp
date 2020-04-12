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
public class ClassDetails {
    String totalNoOfStudents;
    String classTeacher;
    String classs;

    public String getTotalNoOfStudents() {
        return totalNoOfStudents;
    }

    public void setTotalNoOfStudents(String totalNoOfStudents) {
        this.totalNoOfStudents = totalNoOfStudents;
    }

    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }
    
    
     public ArrayList<ClassDetails>convertStringToArrayList(String result){
        
          ArrayList<ClassDetails> ClassDetailsList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              ClassDetails appStatus=new ClassDetails();
              
              appStatus.setClassTeacher(record.split("~")[0]);
              appStatus.setClasss(record.split("~")[1]);
              appStatus.setTotalNoOfStudents(record.split("~")[2]);
              
              ClassDetailsList.add(appStatus);
          }
          
          
          return ClassDetailsList;
      
      }
    
    
}
