/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.studentmasterservice;



/**
 *
 * @author DELL
 */

public class StudentMaster {
    String studentID;
    String studentName;
    String standard;
    String section;
//    LoginMapping[] loginMapping;

//    public LoginMapping[] getLoginMapping() {
//        return loginMapping;
//    }
//
//    public void setLoginMapping(LoginMapping[] loginMapping) {
//        this.loginMapping = loginMapping;
//    }

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

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
