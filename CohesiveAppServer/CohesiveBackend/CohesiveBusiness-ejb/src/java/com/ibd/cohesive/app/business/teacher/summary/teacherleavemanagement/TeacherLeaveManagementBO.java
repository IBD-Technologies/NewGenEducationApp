/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.summary.teacherleavemanagement;

/**
 *
 * @author DELL
 */
public class TeacherLeaveManagementBO {
    TeacherLeaveManagementFilter filter;
    TeacherLeaveManagementResult result[];

    public TeacherLeaveManagementFilter getFilter() {
        return filter;
    }

    public void setFilter(TeacherLeaveManagementFilter filter) {
        this.filter = filter;
    }

    public TeacherLeaveManagementResult[] getResult() {
        return result;
    }

    public void setResult(TeacherLeaveManagementResult[] result) {
        this.result = result;
    }
    
}
