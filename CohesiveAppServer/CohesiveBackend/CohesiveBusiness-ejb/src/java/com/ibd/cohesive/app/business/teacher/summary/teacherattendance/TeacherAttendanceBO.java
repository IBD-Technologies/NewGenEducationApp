/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.summary.teacherattendance;

/**
 *
 * @author DELL
 */
public class TeacherAttendanceBO {
    TeacherAttendanceFilter filter;
    TeacherAttendanceResult result[];

    public TeacherAttendanceFilter getFilter() {
        return filter;
    }

    public void setFilter(TeacherAttendanceFilter filter) {
        this.filter = filter;
    }

    public TeacherAttendanceResult[] getResult() {
        return result;
    }

    public void setResult(TeacherAttendanceResult[] result) {
        this.result = result;
    }
    
    
}
