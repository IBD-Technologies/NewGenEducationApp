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
public class StudentPaymentBO {
    StudentPaymentFilter filter;
    StudentPaymentResult result[];

    public StudentPaymentFilter getFilter() {
        return filter;
    }

    public void setFilter(StudentPaymentFilter filter) {
        this.filter = filter;
    }

    public StudentPaymentResult[] getResult() {
        return result;
    }

    public void setResult(StudentPaymentResult[] result) {
        this.result = result;
    }
    
    
}
