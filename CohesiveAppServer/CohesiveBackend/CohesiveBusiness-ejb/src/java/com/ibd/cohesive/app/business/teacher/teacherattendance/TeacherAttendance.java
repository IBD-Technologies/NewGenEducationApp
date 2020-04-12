/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.teacherattendance;

/**
 *
 * @author DELL
 */
public class TeacherAttendance {
    String teacherID;
    String teacherName;
    String date;
    PeriodAttendance periodAttendance[];

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public PeriodAttendance[] getPeriodAttendance() {
        return periodAttendance;
    }

    public void setPeriodAttendance(PeriodAttendance[] periodAttendance) {
        this.periodAttendance = periodAttendance;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    
    
}
