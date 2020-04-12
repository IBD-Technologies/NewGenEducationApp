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
public class ClassAttendanceSummary {
    String year;
    String Month;
    String monthString;
    String standard;
    String section;
    String presentPercentage;
    String absentPercentage;
    String leavePercentage;
    
    String presentAverage;
    String absentAverage;
    String leaveAverage;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonthString() {
        return monthString;
    }

    public void setMonthString(String monthString) {
        this.monthString = monthString;
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

    public String getPresentPercentage() {
        return presentPercentage;
    }

    public void setPresentPercentage(String presentPercentage) {
        this.presentPercentage = presentPercentage;
    }

    public String getAbsentPercentage() {
        return absentPercentage;
    }

    public void setAbsentPercentage(String absentPercentage) {
        this.absentPercentage = absentPercentage;
    }

    public String getLeavePercentage() {
        return leavePercentage;
    }

    public void setLeavePercentage(String leavePercentage) {
        this.leavePercentage = leavePercentage;
    }

    public String getPresentAverage() {
        return presentAverage;
    }

    public void setPresentAverage(String presentAverage) {
        this.presentAverage = presentAverage;
    }

    public String getAbsentAverage() {
        return absentAverage;
    }

    public void setAbsentAverage(String absentAverage) {
        this.absentAverage = absentAverage;
    }

    public String getLeaveAverage() {
        return leaveAverage;
    }

    public void setLeaveAverage(String leaveAverage) {
        this.leaveAverage = leaveAverage;
    }
    
    
}
