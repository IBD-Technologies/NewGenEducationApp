/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.summary.teachertimetable;

/**
 *
 * @author DELL
 */
public class TeacherTimeTableBO {
    TeacherTimeTableFilter filter;
    TeacherTimeTableResult result[];

    public TeacherTimeTableFilter getFilter() {
        return filter;
    }

    public void setFilter(TeacherTimeTableFilter filter) {
        this.filter = filter;
    }

    public TeacherTimeTableResult[] getResult() {
        return result;
    }

    public void setResult(TeacherTimeTableResult[] result) {
        this.result = result;
    }
    
}
