/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentprogresscard;

import com.ibd.cohesive.app.business.classentity.classmark.GradeDescription;

/**
 *
 * @author DELL
 */
public class StudentProgressCard {
   String studentID;
   String studentName;
   String exam;
   Marks mark[];
   String total;
   String rank;
   GradeDescription gradeDescription[];
   
   public StudentProgressCard(){
       
   }

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

    public Marks[] getMark() {
        return mark;
    }

    public void setMark(Marks[] mark) {
        this.mark = mark;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
