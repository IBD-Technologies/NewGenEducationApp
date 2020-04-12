/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.dataModels.institute;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class MarkComparison {
    String standard;
    String section;
    String exam;
    String subjectID;
    String topMark;
    String lowMark;
    String averageMark;
    String grade;
    String no_of_Students;
    String gradePercentage;
    int orderNo;
    String subjectName;

    public String getGradePercentage() {
        return gradePercentage;
    }

    public void setGradePercentage(String gradePercentage) {
        this.gradePercentage = gradePercentage;
    }
    

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getNo_of_Students() {
        return no_of_Students;
    }

    public void setNo_of_Students(String no_of_Students) {
        this.no_of_Students = no_of_Students;
    }
    
    

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getTopMark() {
        return topMark;
    }

    public void setTopMark(String topMark) {
        this.topMark = topMark;
    }

    public String getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(String averageMark) {
        this.averageMark = averageMark;
    }

    public String getLowMark() {
        return lowMark;
    }

    public void setLowMark(String lowMark) {
        this.lowMark = lowMark;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
     public ArrayList<MarkComparison>convertStringToArrayList(String result){
        
          ArrayList<MarkComparison> MarkComparisonList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              MarkComparison appStatus=new MarkComparison();
              
              appStatus.setAverageMark(record.split("~")[0]);
              appStatus.setExam(record.split("~")[1]);
              appStatus.setLowMark(record.split("~")[2]);
              appStatus.setSection(record.split("~")[3]);
              appStatus.setStandard(record.split("~")[4]);
              appStatus.setSubjectID(record.split("~")[5]);             
              appStatus.setTopMark(record.split("~")[6]);
              appStatus.setGrade(record.split("~")[7]);
              appStatus.setGradePercentage(record.split("~")[8]);
              appStatus.setNo_of_Students(record.split("~")[9]);
              appStatus.setOrderNo(Integer.parseInt(record.split("~")[10]));
              appStatus.setSubjectName(record.split("~")[11]);
              
           MarkComparisonList.add(appStatus);
          }
          
        return MarkComparisonList;
           
      
}
}
