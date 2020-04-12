/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentfeemanagement;

/**
 *
 * @author DELL
 */
public class StudentFeeManagementBO {
    StudentFeeManagementFilter filter;
    StudentFeeManagementResult result[];

    public StudentFeeManagementFilter getFilter() {
        return filter;
    }

    public void setFilter(StudentFeeManagementFilter filter) {
        this.filter = filter;
    }

    public StudentFeeManagementResult[] getResult() {
        return result;
    }

    public void setResult(StudentFeeManagementResult[] result) {
        this.result = result;
    }
    
    
}
