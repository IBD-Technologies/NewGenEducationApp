/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentassignmentsummary;

/**
 *
 * @author DELL
 */
public class StudentAssignmentBO {
    StudentAssignmentFilter filter;
    StudentAssignmentResult result[];

    public StudentAssignmentFilter getFilter() {
        return filter;
    }

    public void setFilter(StudentAssignmentFilter filter) {
        this.filter = filter;
    }

    public StudentAssignmentResult[] getResult() {
        return result;
    }

    public void setResult(StudentAssignmentResult[] result) {
        this.result = result;
    }
    
}
