/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.summary.teacherattendance;

import com.ibd.cohesive.app.business.student.summary.studentattendancesummary.DateAttendance;

/**
 *
 * @author DELL
 */
public class TeacherAttendanceResult {
    String teacherID;
    DateAttendance dateAttendance[];

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }
 

    public DateAttendance[] getDateAttendance() {
        return dateAttendance;
    }

    public void setDateAttendance(DateAttendance[] dateAttendance) {
        this.dateAttendance = dateAttendance;
    }

}
