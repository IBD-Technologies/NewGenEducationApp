/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.Institutepayment;

/**
 *
 * @author DELL
 */
public class InstitutePaymentFilter {
    String paymentDate;
//    String feeType;
    String authStatus;
    String paymentAmount;

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
//
//    public String getFeeType() {
//        return feeType;
//    }
//
//    public void setFeeType(String feeType) {
//        this.feeType = feeType;
//    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    
    
    
}
