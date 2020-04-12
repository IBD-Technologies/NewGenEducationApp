/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.ClassSummary;

/**
 *
 * @author DELL
 */
public class ClassBO {
    ClassFilter filter;
    ClassResult result[];

    public ClassFilter getFilter() {
        return filter;
    }

    public void setFilter(ClassFilter filter) {
        this.filter = filter;
    }

    public ClassResult[] getResult() {
        return result;
    }

    public void setResult(ClassResult[] result) {
        this.result = result;
    }
    
    
}
