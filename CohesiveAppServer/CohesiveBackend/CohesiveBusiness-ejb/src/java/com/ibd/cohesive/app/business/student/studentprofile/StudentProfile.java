/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentprofile;

/**
 *
 * @author DELL
 */
public class StudentProfile {
    String StudentID;
    String StudentName;
    String profileImagePath;
    General gen;
    Emergency emer;
    Family fam[];
    LoginMapping loginMapping[];
    public Family[] getFam() {
        return fam;
    }

    public void setFam(Family[] fam) {
        this.fam = fam;
    }
    String note;
public StudentProfile(){
   
}
    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String StudentID) {
        this.StudentID = StudentID;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String StudentName) {
        this.StudentName = StudentName;
    }

    public General getGen() {
        return gen;
    }

    public void setGen(General gen) {
        this.gen = gen;
    }

    public Emergency getEmer() {
        return emer;
    }

    public void setEmer(Emergency emer) {
        this.emer = emer;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public LoginMapping[] getLoginMapping() {
        return loginMapping;
    }

    public void setLoginMapping(LoginMapping[] loginMapping) {
        this.loginMapping = loginMapping;
    }

   

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
