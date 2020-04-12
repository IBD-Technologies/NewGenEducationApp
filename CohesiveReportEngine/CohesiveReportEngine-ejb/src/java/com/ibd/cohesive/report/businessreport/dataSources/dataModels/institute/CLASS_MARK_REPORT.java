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
public class CLASS_MARK_REPORT {
    String standard;
    String section;
    String exam;
    String subjectID;
    String subjectName;
    String topMark;
    String lowMark;
    String averagreMark;

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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTopMark() {
        return topMark;
    }

    public void setTopMark(String topMark) {
        this.topMark = topMark;
    }

    public String getLowMark() {
        return lowMark;
    }

    public void setLowMark(String lowMark) {
        this.lowMark = lowMark;
    }

    public String getAveragreMark() {
        return averagreMark;
    }

    public void setAveragreMark(String averagreMark) {
        this.averagreMark = averagreMark;
    }
    
    
    public ArrayList<CLASS_MARK_REPORT>convertStringToArrayList(String result){
        
          ArrayList<CLASS_MARK_REPORT> CLASS_MARK_REPORTList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              CLASS_MARK_REPORT appStatus=new CLASS_MARK_REPORT();
              
              appStatus.setAveragreMark(record.split("~")[0]);
              appStatus.setExam(record.split("~")[1]);
              appStatus.setLowMark(record.split("~")[2]);
              appStatus.setSection(record.split("~")[3]);
              appStatus.setStandard(record.split("~")[4]);
              appStatus.setSubjectID(record.split("~")[5]);             
              appStatus.setTopMark(record.split("~")[6]);
              appStatus.setSubjectName(record.split("~")[7]);
              
           CLASS_MARK_REPORTList.add(appStatus);
          }
          
        return CLASS_MARK_REPORTList;
           
      
}
    
    
    
}
