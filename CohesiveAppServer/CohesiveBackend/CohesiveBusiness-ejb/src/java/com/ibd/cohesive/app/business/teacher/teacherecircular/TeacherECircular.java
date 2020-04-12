/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.teacherecircular;

/**
 *
 * @author DELL
 */
public class TeacherECircular {
    String teacherID;
    String teacherName;
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

    

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    
}
