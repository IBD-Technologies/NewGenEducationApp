/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.summary.teacherECircularSummary;

/**
 *
 * @author DELL
 */
public class TeacherECircularResult {
     String teacherID;
    String teacherName;
    String circularID;
    String circularDescription;
    String signStatus;

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    

    public String getCircularID() {
        return circularID;
    }

    public void setCircularID(String circularID) {
        this.circularID = circularID;
    }

    public String getCircularDescription() {
        return circularDescription;
    }

    public void setCircularDescription(String circularDescription) {
        this.circularDescription = circularDescription;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }
    
}
