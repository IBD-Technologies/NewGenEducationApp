/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.datSources.dataModels.classEntity;

/**
 *
 * @author IBD Technologies
 */
public class ClassFeeStatusSummary {
    String feeType;
    String status;
    String no_of_Students;

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNo_of_Students() {
        return no_of_Students;
    }

    public void setNo_of_Students(String no_of_Students) {
        this.no_of_Students = no_of_Students;
    }
    
    
}
