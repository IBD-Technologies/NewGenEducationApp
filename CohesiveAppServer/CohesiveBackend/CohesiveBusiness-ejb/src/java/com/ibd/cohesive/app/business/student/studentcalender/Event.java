/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentcalender;

/**
 *
 * @author DELL
 */
public class Event {
    String eventType;
    String key;
    public EventAttributes eventAttributes[];

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public EventAttributes[] getEventAttributes() {
        return eventAttributes;
    }

    public void setEventAttributes(EventAttributes[] eventAttributes) {
        this.eventAttributes = eventAttributes;
    }
    
    
}
