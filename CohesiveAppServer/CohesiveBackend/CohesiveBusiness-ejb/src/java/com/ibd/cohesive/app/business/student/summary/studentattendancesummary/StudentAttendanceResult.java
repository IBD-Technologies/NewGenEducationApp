/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentattendancesummary;

/**
 *
 * @author DELL
 */
public class StudentAttendanceResult {
 String studentID;
 String date;
 String attendance;
 DateAttendance dateAttendance[];

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
 

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public DateAttendance[] getDateAttendance() {
        return dateAttendance;
    }

    public void setDateAttendance(DateAttendance[] dateAttendance) {
        this.dateAttendance = dateAttendance;
    }

    
 
 
}
