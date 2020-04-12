/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentprofile;

/**
 *
 * @author DELL
 */
public class StudentProfileBO {
    StudentProfileFilter filter;
    StudentProfileResult result[];

    public StudentProfileFilter getFilter() {
        return filter;
    }

    public void setFilter(StudentProfileFilter filter) {
        this.filter = filter;
    }

    public StudentProfileResult[] getResult() {
        return result;
    }

    public void setResult(StudentProfileResult[] result) {
        this.result = result;
    }
    
}
