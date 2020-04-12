/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentpaymentsummary;

/**
 *
 * @author DELL
 */
public class StudentPaymentResult {
    String studentID;
    String paymentID;
    String paymentDate;
    String paymentPaid;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

   
   
    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

   

    public String getPaymentPaid() {
        return paymentPaid;
    }

    public void setPaymentPaid(String paymentPaid) {
        this.paymentPaid = paymentPaid;
    }

    
    
}
