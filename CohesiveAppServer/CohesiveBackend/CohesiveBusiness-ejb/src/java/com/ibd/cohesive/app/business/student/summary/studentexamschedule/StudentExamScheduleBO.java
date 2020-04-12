/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentexamschedule;

/**
 *
 * @author DELL
 */
public class StudentExamScheduleBO {
    StudentExamScheduleFilter filter;
    StudentExamScheduleResult result[];

    public StudentExamScheduleFilter getFilter() {
        return filter;
    }

    public void setFilter(StudentExamScheduleFilter filter) {
        this.filter = filter;
    }

    public StudentExamScheduleResult[] getResult() {
        return result;
    }

    public void setResult(StudentExamScheduleResult[] result) {
        this.result = result;
    }
    
    
}
