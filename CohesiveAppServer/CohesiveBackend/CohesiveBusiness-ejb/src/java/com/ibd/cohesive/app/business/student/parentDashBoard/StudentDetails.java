/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.parentDashBoard;

/**
 *
 * @author DELL
 */
public class StudentDetails {
    String studentID;
    String attendance;
    ExamDetails examdetails[];
    WeeklyCalender weeklyCalender[];

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public ExamDetails[] getExamdetails() {
        return examdetails;
    }

    public void setExamdetails(ExamDetails[] examdetails) {
        this.examdetails = examdetails;
    }

    public WeeklyCalender[] getWeeklyCalender() {
        return weeklyCalender;
    }

    public void setWeeklyCalender(WeeklyCalender[] weeklyCalender) {
        this.weeklyCalender = weeklyCalender;
    }
    
    
    
}
