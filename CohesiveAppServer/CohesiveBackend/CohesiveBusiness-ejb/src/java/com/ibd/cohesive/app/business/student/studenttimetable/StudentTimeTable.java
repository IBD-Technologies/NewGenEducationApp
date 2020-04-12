/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studenttimetable;

/**
 *
 * @author DELL
 */
public class StudentTimeTable {
     String studentID;
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
    
    TimeTable timeTable[];
    

   
   

    public TimeTable[] getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(TimeTable[] timeTable) {
        this.timeTable = timeTable;
    }
}
