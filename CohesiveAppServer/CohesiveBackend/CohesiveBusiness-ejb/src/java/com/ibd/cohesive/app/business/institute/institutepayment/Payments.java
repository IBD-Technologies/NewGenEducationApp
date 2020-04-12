/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.institutepayment;

/**
 *
 * @author DELL
 */
public class Payments {
    String feeID;
    String feeDescription;
    String outStanding;
    String paymentFortheFee;
    String feeAmount;
    String dueDate;

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

    public String getOutStanding() {
        return outStanding;
    }

    public void setOutStanding(String outStanding) {
        this.outStanding = outStanding;
    }

    public String getPaymentFortheFee() {
        return paymentFortheFee;
    }

    public void setPaymentFortheFee(String paymentFortheFee) {
        this.paymentFortheFee = paymentFortheFee;
    }

    
    
    
    
}
