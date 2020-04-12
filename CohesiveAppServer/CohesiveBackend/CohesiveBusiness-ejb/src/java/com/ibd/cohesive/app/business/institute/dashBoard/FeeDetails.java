/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.dashBoard;

/**
 *
 * @author DELL
 */
public class FeeDetails {
    String feeID;
    String feeType;
    
    String feeAmount;
    String amountCollected;
    String amountPending;
    String amountOverDue;

    public String getFeeID() {
        return feeID;
    }

    public void setFeeID(String feeID) {
        this.feeID = feeID;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getAmountCollected() {
        return amountCollected;
    }

    public void setAmountCollected(String amountCollected) {
        this.amountCollected = amountCollected;
    }

    public String getAmountPending() {
        return amountPending;
    }

    public void setAmountPending(String amountPending) {
        this.amountPending = amountPending;
    }

    public String getAmountOverDue() {
        return amountOverDue;
    }

    public void setAmountOverDue(String amountOverDue) {
        this.amountOverDue = amountOverDue;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }
    
    
}
