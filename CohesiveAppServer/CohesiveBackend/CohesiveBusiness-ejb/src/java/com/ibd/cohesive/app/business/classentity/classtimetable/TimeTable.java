/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.classtimetable;




/**
 *
 * @author DELL
 */
public class TimeTable {
    String day;
    public Period[] period;
    
    public TimeTable(){
        
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setPeriod(Period[] period) {
        this.period = period;
    }

    public String getDay() {
        return day;
    }

    public Period[] getPeriod() {
        return period;
    }
}
