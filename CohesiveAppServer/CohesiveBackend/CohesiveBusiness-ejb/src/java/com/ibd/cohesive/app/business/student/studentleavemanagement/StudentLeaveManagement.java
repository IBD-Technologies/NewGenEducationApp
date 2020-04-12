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
public class StudentLeaveManagement {
    String studentID;
    String studentName;
    String from;
    String to;
    String type;
    String reason;
//    String status;
//    String remarks;
    String fromNoon;
    String toNoon;
    
    public StudentLeaveManagement(){
        
    }

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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


//    public String getRemarks() {
//        return remarks;
//    }
//
//    public void setRemarks(String remarks) {
//        this.remarks = remarks;
//    }

    public String getFromNoon() {
        return fromNoon;
    }

    public void setFromNoon(String fromNoon) {
        this.fromNoon = fromNoon;
    }

    public String getToNoon() {
        return toNoon;
    }

    public void setToNoon(String toNoon) {
        this.toNoon = toNoon;
    }
    
    
}
