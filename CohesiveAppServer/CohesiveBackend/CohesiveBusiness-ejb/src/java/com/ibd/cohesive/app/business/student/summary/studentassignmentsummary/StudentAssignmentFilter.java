/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentassignmentsummary;

/**
 *
 * @author DELL
 */
public class StudentAssignmentFilter {
//    String assignmentID;
    String studentID;
    String studentName;
//    String subjectID;
//    String authStatus;
//    String assignmentType;
//    String standard;
//    String section;
//    String status;
    String fromDate;
    String toDate;

 
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

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

   
    
}
