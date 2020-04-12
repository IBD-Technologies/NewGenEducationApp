/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentfeemanagement;

/**
 *
 * @author DELL
 */
public class StudentFeeManagementFilter {
    String studentID;
//    String authStatus;
    String feeType;
//    String paidDate;
    String status;
//    String standard;
//    String section;
    String studentName;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    

//    public String getStandard() {
//        return standard;
//    }
//
//    public void setStandard(String standard) {
//        this.standard = standard;
//    }
//
//    public String getSection() {
//        return section;
//    }
//
//    public void setSection(String section) {
//        this.section = section;
//    }
//    
    


    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

//    public String getAuthStatus() {
//        return authStatus;
//    }
//
//    public void setAuthStatus(String authStatus) {
//        this.authStatus = authStatus;
//    }

    
    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

//     public String getPaidDate() {
//        return paidDate;
//    }
//
//    public void setPaidDate(String paidDate) {
//        this.paidDate = paidDate;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
