/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studenteCircularSummary;

/**
 *
 * @author DELL
 */
public class StudentECircularBO {
    StudentECircularFilter filter;
    StudentECircularResult result[];

    public StudentECircularFilter getFilter() {
        return filter;
    }

    public void setFilter(StudentECircularFilter filter) {
        this.filter = filter;
    }

    public StudentECircularResult[] getResult() {
        return result;
    }

    public void setResult(StudentECircularResult[] result) {
        this.result = result;
    }
    
    
}
