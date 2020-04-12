/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentexamschedule;

/**
 *
 * @author DELL
 */
public class Schedule {
    String subjectID;
    String subjectName;
    String date;
    String startTimeHour;
    String startTimeMin;
    String endTimeHour;
    String endTimeMin;
    String hall;
    String operation;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    
    

    public Schedule(){
        
    }
    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTimeHour() {
        return startTimeHour;
    }

    public void setStartTimeHour(String startTimeHour) {
        this.startTimeHour = startTimeHour;
    }

    public String getStartTimeMin() {
        return startTimeMin;
    }

    public void setStartTimeMin(String startTimeMin) {
        this.startTimeMin = startTimeMin;
    }

    public String getEndTimeHour() {
        return endTimeHour;
    }

    public void setEndTimeHour(String endTimeHour) {
        this.endTimeHour = endTimeHour;
    }

    public String getEndTimeMin() {
        return endTimeMin;
    }

    public void setEndTimeMin(String endTimeMin) {
        this.endTimeMin = endTimeMin;
    }

   

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }
}
