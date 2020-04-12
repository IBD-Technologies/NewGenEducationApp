/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.classattendance;

/**
 *
 * @author IBD Technologies
 */
public class StudentWiseAttendance {
    String studentID;
    String studentName;
    public PeriodAttendance periodAttendance[];

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public PeriodAttendance[] getPeriodAttendance() {
        return periodAttendance;
    }

    public void setPeriodAttendance(PeriodAttendance[] periodAttendance) {
        this.periodAttendance = periodAttendance;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    
    
}
