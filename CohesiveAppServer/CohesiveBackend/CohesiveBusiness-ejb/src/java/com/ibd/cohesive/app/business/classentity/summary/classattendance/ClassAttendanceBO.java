/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.summary.classattendance;

/**
 *
 * @author DELL
 */
public class ClassAttendanceBO {
    ClassAttendanceFilter filter;
    ClassAttendanceResult result[];

    public ClassAttendanceFilter getFilter() {
        return filter;
    }

    public void setFilter(ClassAttendanceFilter filter) {
        this.filter = filter;
    }

    public ClassAttendanceResult[] getResult() {
        return result;
    }

    public void setResult(ClassAttendanceResult[] result) {
        this.result = result;
    }
    
    
}
