/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.teacherleavemanagement;

/**
 *
 * @author DELL
 */
public class TeacherLeaveManagement {
    String teacherID;
    String teacherName;
    String from;
    String to;
    String type;
    String reason;
//    String status;
//    String remarks;
    String fromNoon;
    String toNoon;
    
    public TeacherLeaveManagement(){
        
    }
            

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
