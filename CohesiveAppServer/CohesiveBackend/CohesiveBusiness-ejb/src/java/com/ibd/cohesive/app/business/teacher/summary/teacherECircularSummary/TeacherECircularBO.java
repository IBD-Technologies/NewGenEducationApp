/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.summary.teacherECircularSummary;

/**
 *
 * @author DELL
 */
public class TeacherECircularBO {
    TeacherECircularFilter filter;
    TeacherECircularResult result[];

    public TeacherECircularFilter getFilter() {
        return filter;
    }

    public void setFilter(TeacherECircularFilter filter) {
        this.filter = filter;
    }

    public TeacherECircularResult[] getResult() {
        return result;
    }

    public void setResult(TeacherECircularResult[] result) {
        this.result = result;
    }
    
    
}
