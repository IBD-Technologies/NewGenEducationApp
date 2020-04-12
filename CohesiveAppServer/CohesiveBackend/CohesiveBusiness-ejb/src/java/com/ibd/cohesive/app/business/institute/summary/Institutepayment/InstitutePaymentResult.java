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
public class InstitutePaymentResult {
    String paymentID;
    String paymentPaid;
    String paymentDate;
    String studentID;
    String studentName;

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
    
    
    

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    


    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }


   
    public String getPaymentPaid() {
        return paymentPaid;
    }

    public void setPaymentPaid(String paymentPaid) {
        this.paymentPaid = paymentPaid;
    }

   


   
     
}
