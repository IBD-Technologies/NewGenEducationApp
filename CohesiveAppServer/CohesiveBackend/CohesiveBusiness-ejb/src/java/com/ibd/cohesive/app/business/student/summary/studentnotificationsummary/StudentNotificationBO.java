/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentnotificationsummary;

/**
 *
 * @author DELL
 */
public class StudentNotificationBO {
    StudentNotificationFilter filter;
    StudentNotificationResult result[];

    public StudentNotificationFilter getFilter() {
        return filter;
    }

    public void setFilter(StudentNotificationFilter filter) {
        this.filter = filter;
    }

    public StudentNotificationResult[] getResult() {
        return result;
    }

    public void setResult(StudentNotificationResult[] result) {
        this.result = result;
    }
    
    
    
}
