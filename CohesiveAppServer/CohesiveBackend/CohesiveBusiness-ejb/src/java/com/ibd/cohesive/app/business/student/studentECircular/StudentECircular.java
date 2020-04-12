/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentECircular;

/**
 *
 * @author DELL
 */
public class StudentECircular {
    String studentID;
    String studentName;
    String eCircularID;
    String eCircularDescription;
    String contentPath;
    String circularDate;
    String circularType;
    String message;
    String signStatus;

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }
    
    

    public String getCircularDate() {
        return circularDate;
    }

    public void setCircularDate(String circularDate) {
        this.circularDate = circularDate;
    }

    public String getCircularType() {
        return circularType;
    }

    public void setCircularType(String circularType) {
        this.circularType = circularType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String geteCircularID() {
        return eCircularID;
    }

    public void seteCircularID(String eCircularID) {
        this.eCircularID = eCircularID;
    }

    public String geteCircularDescription() {
        return eCircularDescription;
    }

    public void seteCircularDescription(String eCircularDescription) {
        this.eCircularDescription = eCircularDescription;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    
    
}
