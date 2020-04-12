/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.summary.classexamschedule;

/**
 *
 * @author DELL
 */
public class ClassExamScheduleBO {
    ClassExamScheduleFilter filter;
    ClassExamScheduleResult result[];

    public ClassExamScheduleFilter getFilter() {
        return filter;
    }

    public void setFilter(ClassExamScheduleFilter filter) {
        this.filter = filter;
    }

    public ClassExamScheduleResult[] getResult() {
        return result;
    }

    public void setResult(ClassExamScheduleResult[] result) {
        this.result = result;
    }
    
    
}
