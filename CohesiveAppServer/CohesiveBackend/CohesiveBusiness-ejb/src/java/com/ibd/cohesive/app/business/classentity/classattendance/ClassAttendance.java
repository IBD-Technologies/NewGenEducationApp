/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.classattendance;

/**
 *
 * @author IBD Technologies
 */
public class ClassAttendance {
    String standard;
    String section;
    String date;
    public StudentWiseAttendance foreNoonAttendance[];
    public StudentWiseAttendance afterNoonAttendance[];
    
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public StudentWiseAttendance[] getForeNoonAttendance() {
        return foreNoonAttendance;
    }

    public void setForeNoonAttendance(StudentWiseAttendance[] foreNoonAttendance) {
        this.foreNoonAttendance = foreNoonAttendance;
    }

    public StudentWiseAttendance[] getAfterNoonAttendance() {
        return afterNoonAttendance;
    }

    public void setAfterNoonAttendance(StudentWiseAttendance[] afterNoonAttendance) {
        this.afterNoonAttendance = afterNoonAttendance;
    }

    
    
}
