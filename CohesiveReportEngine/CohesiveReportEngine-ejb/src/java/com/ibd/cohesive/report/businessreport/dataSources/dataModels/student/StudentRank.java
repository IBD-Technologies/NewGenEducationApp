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
public class StudentRank {
    String studentID;
    String rank;
    String total;
    String exam;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }
    
    
    public ArrayList<StudentRank>convertStringToArrayList(String result){
        
          ArrayList<StudentRank> StudentRankList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              StudentRank appStatus=new StudentRank();
              
              appStatus.setExam(record.split("~")[0]);
              appStatus.setRank(record.split("~")[1]);
              appStatus.setStudentID(record.split("~")[2]);
              appStatus.setTotal(record.split("~")[3]);
              
              StudentRankList.add(appStatus);
          }
          
          
          return StudentRankList;
      
      }
    
    
    
}
