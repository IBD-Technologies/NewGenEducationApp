/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.summary.classtimetable;

/**
 *
 * @author DELL
 */
public class ClassTimeTableBO {
    ClassTimeTableFilter filter;
    ClassTimeTableResult result[];

    public ClassTimeTableFilter getFilter() {
        return filter;
    }

    public void setFilter(ClassTimeTableFilter filter) {
        this.filter = filter;
    }

    public ClassTimeTableResult[] getResult() {
        return result;
    }

    public void setResult(ClassTimeTableResult[] result) {
        this.result = result;
    }
    
}
