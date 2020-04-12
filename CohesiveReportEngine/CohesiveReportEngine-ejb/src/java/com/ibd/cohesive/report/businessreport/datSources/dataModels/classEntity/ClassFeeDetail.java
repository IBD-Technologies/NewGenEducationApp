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
public class ClassFeeDetail {
    String feeID;
    String feeType;
    String feeAmount;
    String dueDate;
    String paidAmount;
    String balanceAmount;
    String feeDescription;

    public String getFeeDescription() {
        return feeDescription;
    }

    public void setFeeDescription(String feeDescription) {
        this.feeDescription = feeDescription;
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
    
    
    
     public ArrayList<ClassFeeDetail>convertStringToArrayList(String result){
        
          ArrayList<ClassFeeDetail> ClassFeeDetailList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              ClassFeeDetail appStatus=new ClassFeeDetail();
              
              appStatus.setBalanceAmount(record.split("~")[0]);
              appStatus.setDueDate(record.split("~")[1]);
              appStatus.setFeeAmount(record.split("~")[2]);
              appStatus.setFeeID(record.split("~")[3]);
              appStatus.setFeeType(record.split("~")[4]);
              appStatus.setPaidAmount(record.split("~")[5]);
              appStatus.setFeeDescription(record.split("~")[6]);
             
              
              
              
           ClassFeeDetailList.add(appStatus);
          }
          
        return ClassFeeDetailList;
           
      
}
    
    
    
    
}
