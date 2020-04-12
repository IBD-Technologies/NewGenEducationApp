/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.summary.teacherprofile;

/**
 *
 * @author DELL
 */
public class TeacherProfileBO {
    TeacherProfileFilter filter;
    TeacherProfileResult result[];

    public TeacherProfileFilter getFilter() {
        return filter;
    }

    public void setFilter(TeacherProfileFilter filter) {
        this.filter = filter;
    }

    public TeacherProfileResult[] getResult() {
        return result;
    }

    public void setResult(TeacherProfileResult[] result) {
        this.result = result;
    }
    
}
