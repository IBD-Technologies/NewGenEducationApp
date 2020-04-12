/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentsoftskill;

/**
 *
 * @author DELL
 */
public class StudentSoftSkillBO {
    StudentSoftSkillFilter filter;
    StudentSoftSkillResult result[];

    public StudentSoftSkillFilter getFilter() {
        return filter;
    }

    public void setFilter(StudentSoftSkillFilter filter) {
        this.filter = filter;
    }

    public StudentSoftSkillResult[] getResult() {
        return result;
    }

    public void setResult(StudentSoftSkillResult[] result) {
        this.result = result;
    }
    
    
}
