/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentprogresscard;

/**
 *
 * @author DELL
 */
public class StudentProgressCardBO {
    StudentProgressCardFilter filter;
    StudentProgressCardResult result[];

    public StudentProgressCardFilter getFilter() {
        return filter;
    }

    public void setFilter(StudentProgressCardFilter filter) {
        this.filter = filter;
    }

    public StudentProgressCardResult[] getResult() {
        return result;
    }

    public void setResult(StudentProgressCardResult[] result) {
        this.result = result;
    }
    
    
}
