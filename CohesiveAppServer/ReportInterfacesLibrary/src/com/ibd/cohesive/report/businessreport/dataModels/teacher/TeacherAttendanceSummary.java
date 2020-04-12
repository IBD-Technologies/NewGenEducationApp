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
public class TeacherAttendanceSummary {
    String teacherID;
    String year;
    String month;
    String no_of_Days_Present;
    String no_of_Days_Leave;
    String no_of_WorkingDays;
    String percentage;

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
    
    
    
    
    public String getNo_of_WorkingDays() {
        return no_of_WorkingDays;
    }

    public void setNo_of_WorkingDays(String no_of_WorkingDays) {
        this.no_of_WorkingDays = no_of_WorkingDays;
    }
    
    

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getNo_of_Days_Present() {
        return no_of_Days_Present;
    }

    public void setNo_of_Days_Present(String no_of_Days_Present) {
        this.no_of_Days_Present = no_of_Days_Present;
    }

   

    public String getNo_of_Days_Leave() {
        return no_of_Days_Leave;
    }

    public void setNo_of_Days_Leave(String no_of_Days_Leave) {
        this.no_of_Days_Leave = no_of_Days_Leave;
    }
    
    
    
}
