/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentsoftskill;

/**
 *
 * @author DELL
 */
public class StudentSoftSkill {
      String studentID;
   String studentName;
   String exam;
   Skills skill[];

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public Skills[] getSkill() {
        return skill;
    }

    public void setSkill(Skills[] skill) {
        this.skill = skill;
    }
   
   
   
}
