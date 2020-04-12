/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentexamschedule;

/**
 *
 * @author DELL
 */
public class StudentExamSchedule  {
    String studentID;
    String studentName;
    String exam;
    public Schedule schedule[];

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public Schedule[] getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule[] schedule) {
        this.schedule = schedule;
    }
    
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }


    
    
}
