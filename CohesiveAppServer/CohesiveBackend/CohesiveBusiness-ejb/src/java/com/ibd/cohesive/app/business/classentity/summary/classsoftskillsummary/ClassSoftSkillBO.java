/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.summary.classsoftskillsummary;

/**
 *
 * @author DELL
 */
public class ClassSoftSkillBO {
    ClassSoftSkillFilter filter;
    ClassSoftSkillResult result[];

    public ClassSoftSkillFilter getFilter() {
        return filter;
    }

    public void setFilter(ClassSoftSkillFilter filter) {
        this.filter = filter;
    }

    public ClassSoftSkillResult[] getResult() {
        return result;
    }

    public void setResult(ClassSoftSkillResult[] result) {
        this.result = result;
    }
}
