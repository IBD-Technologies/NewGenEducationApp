/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataModels.classEntity;

/**
 *
 * @author DELL
 */
public class ClassAttendanceDetail {
    
    String year;
    String Month;
    String standard;
    String section;
    String studentID;
    String no_of_DaysPresent;
    String no_of_DaysAbsent;
    String no_ofDaysLeave;
    String no_of_workingDays;
    String percentage;
    String studentName;
    String monthString;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMonthString() {
        return monthString;
    }

    public void setMonthString(String monthString) {
        this.monthString = monthString;
    }
    
    

    public String getNo_of_workingDays() {
        return no_of_workingDays;
    }

    public void setNo_of_workingDays(String no_of_workingDays) {
        this.no_of_workingDays = no_of_workingDays;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
    
    

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String Month) {
        this.Month = Month;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getNo_of_DaysPresent() {
        return no_of_DaysPresent;
    }

    public void setNo_of_DaysPresent(String no_of_DaysPresent) {
        this.no_of_DaysPresent = no_of_DaysPresent;
    }

    public String getNo_of_DaysAbsent() {
        return no_of_DaysAbsent;
    }

    public void setNo_of_DaysAbsent(String no_of_DaysAbsent) {
        this.no_of_DaysAbsent = no_of_DaysAbsent;
    }

    public String getNo_ofDaysLeave() {
        return no_ofDaysLeave;
    }

    public void setNo_ofDaysLeave(String no_ofDaysLeave) {
        this.no_ofDaysLeave = no_ofDaysLeave;
    }
    
    
    
}
