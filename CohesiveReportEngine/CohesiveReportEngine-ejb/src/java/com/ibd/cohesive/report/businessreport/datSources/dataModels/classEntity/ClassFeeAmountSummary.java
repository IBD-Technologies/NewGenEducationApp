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
public class ClassFeeAmountSummary {
    String feeType;
    String totalFeeAmount;
    String totalPaidAmount;
    String totalBalanceAmount;

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
    
    
       public ArrayList<ClassFeeAmountSummary>convertStringToArrayList(String result){
        
          ArrayList<ClassFeeAmountSummary> ClassFeeAmountSummaryList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              ClassFeeAmountSummary appStatus=new ClassFeeAmountSummary();
              
              appStatus.setFeeType(record.split("~")[0]);
              appStatus.setTotalBalanceAmount(record.split("~")[1]);
              appStatus.setTotalFeeAmount(record.split("~")[2]);
              appStatus.setTotalPaidAmount(record.split("~")[3]);
              
              
              
           ClassFeeAmountSummaryList.add(appStatus);
          }
          
        return ClassFeeAmountSummaryList;
           
      
}
    
    
}
