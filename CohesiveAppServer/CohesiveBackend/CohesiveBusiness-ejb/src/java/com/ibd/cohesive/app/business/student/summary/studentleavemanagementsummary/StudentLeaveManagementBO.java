/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentleavemanagementsummary;

/**
 *
 * @author DELL
 */
public class StudentLeaveManagementBO {
    StudentLeaveManagementFilter filter;
    StudentLeaveManagementResult result[];

    public StudentLeaveManagementFilter getFilter() {
        return filter;
    }

    public void setFilter(StudentLeaveManagementFilter filter) {
        this.filter = filter;
    }

    public StudentLeaveManagementResult[] getResult() {
        return result;
    }

    public void setResult(StudentLeaveManagementResult[] result) {
        this.result = result;
    }
    
    
}
