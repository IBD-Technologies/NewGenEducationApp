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
public class ParentDashBoard {
    String userID;
    StudentDetails studentDetails[];

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public StudentDetails[] getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(StudentDetails[] studentDetails) {
        this.studentDetails = studentDetails;
    }
    
    
    
}
