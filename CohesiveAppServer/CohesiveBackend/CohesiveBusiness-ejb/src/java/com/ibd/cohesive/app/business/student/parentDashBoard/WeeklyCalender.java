/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.parentDashBoard;

import com.ibd.cohesive.app.business.student.studentcalender.Event;

/**
 *
 * @author DELL
 */
public class WeeklyCalender {
    String date;
    public Event event[];

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Event[] getEvent() {
        return event;
    }

    public void setEvent(Event[] event) {
        this.event = event;
    }
     
     
}
