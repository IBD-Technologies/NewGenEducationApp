/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentleavemanagement;

/**
 *
 * @author DELL
 */
public class StudentLeaveDetail {
    String studentID;
    boolean foreNoonLeave;
    boolean afterNoonLeave;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public boolean isForeNoonLeave() {
        return foreNoonLeave;
    }

    public void setForeNoonLeave(boolean foreNoonLeave) {
        this.foreNoonLeave = foreNoonLeave;
    }

    public boolean isAfterNoonLeave() {
        return afterNoonLeave;
    }

    public void setAfterNoonLeave(boolean afterNoonLeave) {
        this.afterNoonLeave = afterNoonLeave;
    }
    
}
