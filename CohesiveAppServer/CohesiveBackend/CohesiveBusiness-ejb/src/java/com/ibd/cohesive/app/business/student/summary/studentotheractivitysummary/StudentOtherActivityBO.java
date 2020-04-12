/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentotheractivitysummary;

/**
 *
 * @author DELL
 */
public class StudentOtherActivityBO {
    StudentOtherActivityFilter filter;
    StudentOtherActivityResult result[];

    public StudentOtherActivityFilter getFilter() {
        return filter;
    }

    public void setFilter(StudentOtherActivityFilter filter) {
        this.filter = filter;
    }

    public StudentOtherActivityResult[] getResult() {
        return result;
    }

    public void setResult(StudentOtherActivityResult[] result) {
        this.result = result;
    }
}
