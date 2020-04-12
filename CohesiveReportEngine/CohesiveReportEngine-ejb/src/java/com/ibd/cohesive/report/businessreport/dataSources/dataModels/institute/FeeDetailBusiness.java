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
public class FeeDetailBusiness {
  String feeID;
  String feeType;
  String studentID;
  String studentName;
  String feeAmount;
  String paidAmount;
  String balanceAmount;
  String feeDescription;
  String dueDate;
  String standard;
  String section;
  String serialNumber;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
  

    public String getFeeDescription() {
        return feeDescription;
    }

    public void setFeeDescription(String feeDescription) {
        this.feeDescription = feeDescription;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
  
  

    public String getFeeID() {
        return feeID;
    }

    public void setFeeID(String feeID) {
        this.feeID = feeID;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
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

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
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
  
    
  public ArrayList<FeeDetailBusiness>convertStringToArrayList(String result){
        
          ArrayList<FeeDetailBusiness> FeeDetailBusinessList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              FeeDetailBusiness appStatus=new FeeDetailBusiness();
              
              appStatus.setBalanceAmount(record.split("~")[0]);
              appStatus.setDueDate(record.split("~")[1]);
              appStatus.setFeeAmount(record.split("~")[2]);
              appStatus.setFeeDescription(record.split("~")[3]);
              appStatus.setFeeID(record.split("~")[4]);
              appStatus.setFeeType(record.split("~")[5]);
              appStatus.setPaidAmount(record.split("~")[6]);
              appStatus.setStudentID(record.split("~")[7]);
              appStatus.setStudentName(record.split("~")[8]);
              appStatus.setStandard(record.split("~")[9]);
              appStatus.setSection(record.split("~")[10]);
              appStatus.setSerialNumber(record.split("~")[11]);
           FeeDetailBusinessList.add(appStatus);
          }
          
        return FeeDetailBusinessList;
           
      
}

}
