/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.classlevelconfiguration;

/**
 *
 * @author DELL
 */
public class PeriodTimings {
//    String standard;
//    String section;
    String periodNumber;
    String startTimeHour;
    String endTimeHour;
    String startTimeMin;
    String endTimeMin;
    String noon;
    String operation;

    public String getNoon() {
        return noon;
    }

    public void setNoon(String noon) {
        this.noon = noon;
    }

    
    
//    public String getStandard() {
//        return standard;
//    }
//
//    public void setStandard(String standard) {
//        this.standard = standard;
//    }
//
//    public String getSection() {
//        return section;
//    }
//
//    public void setSection(String section) {
//        this.section = section;
//    }

    public String getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(String periodNumber) {
        this.periodNumber = periodNumber;
    }

    public String getStartTimeHour() {
        return startTimeHour;
    }

    public void setStartTimeHour(String startTimeHour) {
        this.startTimeHour = startTimeHour;
    }

    public String getEndTimeHour() {
        return endTimeHour;
    }

    public void setEndTimeHour(String endTimeHour) {
        this.endTimeHour = endTimeHour;
    }

    public String getStartTimeMin() {
        return startTimeMin;
    }

    public void setStartTimeMin(String startTimeMin) {
        this.startTimeMin = startTimeMin;
    }

    public String getEndTimeMin() {
        return endTimeMin;
    }

    public void setEndTimeMin(String endTimeMin) {
        this.endTimeMin = endTimeMin;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
    
    
}
