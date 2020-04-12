/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataModels.student;

/**
 *
 * @author DELL
 */
public class StudentAttendanceSummary {
    String studentID;
    String year;
    String month;
    String no_of_working_Days;
    String no_of_Days_Present;
    String no_of_Days_Absent;
    String no_of_Days_Leave;
    String percentage;
    int monthNumber;

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }
    
    

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
    
    

    public String getNo_of_working_Days() {
        return no_of_working_Days;
    }

    public void setNo_of_working_Days(String no_of_working_Days) {
        this.no_of_working_Days = no_of_working_Days;
    }


    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
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

    public String getNo_of_Days_Absent() {
        return no_of_Days_Absent;
    }

    public void setNo_of_Days_Absent(String no_of_Days_Absent) {
        this.no_of_Days_Absent = no_of_Days_Absent;
    }

    public String getNo_of_Days_Leave() {
        return no_of_Days_Leave;
    }

    public void setNo_of_Days_Leave(String no_of_Days_Leave) {
        this.no_of_Days_Leave = no_of_Days_Leave;
    }
    
    
}
