/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentattendanceservice;

/**
 *
 * @author IBD Tecnologies
 */
public class DayAttendance {
   float present;
   float absent;
   float leave;

    public float getPresent() {
        return present;
    }

    public void setPresent(float present) {
        this.present = present;
    }

    public float getAbsent() {
        return absent;
    }

    public void setAbsent(float absent) {
        this.absent = absent;
    }

    public float getLeave() {
        return leave;
    }

    public void setLeave(float leave) {
        this.leave = leave;
    }
   
}
