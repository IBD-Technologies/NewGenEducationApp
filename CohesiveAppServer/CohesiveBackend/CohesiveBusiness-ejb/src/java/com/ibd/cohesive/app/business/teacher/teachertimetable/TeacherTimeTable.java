/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.teachertimetable;

/**
 *
 * @author IBD Technologies
 */
public class TeacherTimeTable {

    String teacherID;
    String teacherName;
    public TimeTable timeTable[];

    public TimeTable[] getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(TimeTable[] timeTable) {
        this.timeTable = timeTable;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    
    
    

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }


    public String getTeacherID() {
        return teacherID;
    }
}
