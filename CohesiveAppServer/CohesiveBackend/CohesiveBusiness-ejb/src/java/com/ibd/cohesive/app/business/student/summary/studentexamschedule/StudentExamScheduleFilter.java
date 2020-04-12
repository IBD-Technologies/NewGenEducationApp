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
public class StudentExamScheduleFilter {
    String studentID;
    String exam;
    String studentName;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    

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

   
    
}
