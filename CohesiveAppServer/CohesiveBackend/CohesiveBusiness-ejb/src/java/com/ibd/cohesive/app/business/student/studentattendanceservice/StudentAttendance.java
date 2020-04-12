/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentattendanceservice;

/**
 *
 * @author IBD Technologies
 */
public class StudentAttendance {
    String studentID;
    String studentName;
    String date;
    PeriodAttendance foreNoon[];
    PeriodAttendance afterNoon[];
    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public PeriodAttendance[] getForeNoon() {
        return foreNoon;
    }

    public void setForeNoon(PeriodAttendance[] foreNoon) {
        this.foreNoon = foreNoon;
    }

    public PeriodAttendance[] getAfterNoon() {
        return afterNoon;
    }

    public void setAfterNoon(PeriodAttendance[] afterNoon) {
        this.afterNoon = afterNoon;
    }

    

   
    
}
