/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studenttimetablesummary;

/**
 *
 * @author DELL
 */
public class StudentTimeTableBO {
    StudentTimeTableFilter filter;
    StudentTimeTableResult result[];

    public StudentTimeTableFilter getFilter() {
        return filter;
    }

    public void setFilter(StudentTimeTableFilter filter) {
        this.filter = filter;
    }

    public StudentTimeTableResult[] getResult() {
        return result;
    }

    public void setResult(StudentTimeTableResult[] result) {
        this.result = result;
    }
    
}
