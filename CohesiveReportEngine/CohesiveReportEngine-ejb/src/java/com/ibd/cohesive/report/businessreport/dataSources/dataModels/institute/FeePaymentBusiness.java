/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.dataModels.institute;

import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class FeePaymentBusiness {
  String studentID;
  String studentName;
  String paymentID;
  String feeID;
  String paymentAmount;
  String paymentDate;
  String standard;
  String section;
  String feeAmount;
  String dueDate;
String serialNumber;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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
  
  

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getFeeID() {
        return feeID;
    }

    public void setFeeID(String feeID) {
        this.feeID = feeID;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
  
  
  public ArrayList<FeePaymentBusiness>convertStringToArrayList(String result){
        
          ArrayList<FeePaymentBusiness> FeePaymentBusinessList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              FeePaymentBusiness appStatus=new FeePaymentBusiness();
              
              appStatus.setFeeID(record.split("~")[0]);
              appStatus.setPaymentAmount(record.split("~")[1]);
              appStatus.setPaymentDate(record.split("~")[2]);
              appStatus.setPaymentID(record.split("~")[3]);
              appStatus.setStudentID(record.split("~")[4]);
              appStatus.setStudentName(record.split("~")[5]);
              appStatus.setDueDate(record.split("~")[6]);
              appStatus.setFeeAmount(record.split("~")[7]);
              appStatus.setStandard(record.split("~")[8]);
              appStatus.setSection(record.split("~")[9]);
              appStatus.setSerialNumber(record.split("~")[10]);
           FeePaymentBusinessList.add(appStatus);
          }
          
        return FeePaymentBusinessList;
           
      
}

}
