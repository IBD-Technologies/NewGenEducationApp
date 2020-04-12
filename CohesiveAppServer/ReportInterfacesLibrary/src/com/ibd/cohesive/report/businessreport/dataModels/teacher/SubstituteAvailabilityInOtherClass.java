/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataModels.teacher;

/**
 *
 * @author DELL
 */
public class SubstituteAvailabilityInOtherClass {
   String TeacherID;
    String teacherName;
    String classs;
    String freeStartTime;
    String freeEndTime;
    int startTimeNo;

    public int getStartTimeNo() {
        return startTimeNo;
    }

    public void setStartTimeNo(int startTimeNo) {
        this.startTimeNo = startTimeNo;
    }
    
    

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public String getFreeStartTime() {
        return freeStartTime;
    }

    public void setFreeStartTime(String freeStartTime) {
        this.freeStartTime = freeStartTime;
    }

    public String getFreeEndTime() {
        return freeEndTime;
    }

    public void setFreeEndTime(String freeEndTime) {
        this.freeEndTime = freeEndTime;
    }
    
    

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    
    
    public String getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(String TeacherID) {
        this.TeacherID = TeacherID;
    }

    
    
}
