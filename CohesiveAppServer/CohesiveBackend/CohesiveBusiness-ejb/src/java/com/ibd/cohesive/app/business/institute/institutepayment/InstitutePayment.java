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
public class InstitutePayment {
    String instituteID;
    String instituteName;
    String studentID;
    String studentName;
    String paymentID;
    String paymentMode;
    String paymentpaid;
    String paymentDate;
    Payments payments[];
    String balanceAmount;

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
    
    

    public Payments[] getPayments() {
        return payments;
    }

    public void setPayments(Payments[] payments) {
        this.payments = payments;
    }
    
    
    
    public String getInstituteID() {
        return instituteID;
    }

    public void setInstituteID(String instituteID) {
        this.instituteID = instituteID;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    
    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentpaid() {
        return paymentpaid;
    }

    public void setPaymentpaid(String paymentpaid) {
        this.paymentpaid = paymentpaid;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    



    

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    
    
}
