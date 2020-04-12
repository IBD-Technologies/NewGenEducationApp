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
public class FeeSummaryBusiness {
    String feeID;
    String feeDescription;
    String dueDate;
    String feeType;
    String totalFeeAmount;
    String totalPaidAmount;
    String totalBalanceAmount;
String serialNumber;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
  
    public String getFeeID() {
        return feeID;
    }

    public void setFeeID(String feeID) {
        this.feeID = feeID;
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
    

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getTotalFeeAmount() {
        return totalFeeAmount;
    }

    public void setTotalFeeAmount(String totalFeeAmount) {
        this.totalFeeAmount = totalFeeAmount;
    }

    public String getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(String totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public String getTotalBalanceAmount() {
        return totalBalanceAmount;
    }

    public void setTotalBalanceAmount(String totalBalanceAmount) {
        this.totalBalanceAmount = totalBalanceAmount;
    }
    
    
    
    
     public ArrayList<FeeSummaryBusiness>convertStringToArrayList(String result){
        
          ArrayList<FeeSummaryBusiness> FeeSummaryBusinessList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              FeeSummaryBusiness appStatus=new FeeSummaryBusiness();
              
              appStatus.setFeeType(record.split("~")[0]);
              appStatus.setTotalBalanceAmount(record.split("~")[1]);
              appStatus.setTotalFeeAmount(record.split("~")[2]);
              appStatus.setTotalPaidAmount(record.split("~")[3]);
              appStatus.setDueDate(record.split("~")[4]);
              appStatus.setFeeDescription(record.split("~")[5]);
              appStatus.setFeeID(record.split("~")[6]);
              appStatus.setSerialNumber(record.split("~")[7]);
           FeeSummaryBusinessList.add(appStatus);
          }
          
        return FeeSummaryBusinessList;
           
      
}
    
}
