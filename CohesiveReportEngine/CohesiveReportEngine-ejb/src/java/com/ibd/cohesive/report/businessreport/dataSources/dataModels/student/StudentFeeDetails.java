        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.dataModels.student;

import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class StudentFeeDetails {
    String studentID;
    String feeID;
    String feeType;
    String feeAmount;
    String dueDate;
    String paidAmount;
    String balanceAmount;

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }


    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }


    public String getFeeID() {
        return feeID;
    }

    public void setFeeID(String feeID) {
        this.feeID = feeID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    
     public ArrayList<StudentFeeDetails>convertStringToArrayList(String result){
        
          ArrayList<StudentFeeDetails> StudentFeeDetailsList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              StudentFeeDetails appStatus=new StudentFeeDetails();
              
              appStatus.setBalanceAmount(record.split("~")[0]);
              appStatus.setDueDate(record.split("~")[1]);
              appStatus.setFeeAmount(record.split("~")[2]);
              appStatus.setFeeID(record.split("~")[3]);
              appStatus.setFeeType(record.split("~")[4]);
              appStatus.setPaidAmount(record.split("~")[5]);
              appStatus.setStudentID(record.split("~")[6]);
              
              StudentFeeDetailsList.add(appStatus);
          }
          
          
          return StudentFeeDetailsList;
      
      }
}
