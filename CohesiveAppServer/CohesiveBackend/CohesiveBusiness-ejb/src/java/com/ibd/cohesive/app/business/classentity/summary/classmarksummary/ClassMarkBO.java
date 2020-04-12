/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.summary.classmarksummary;

/**
 *
 * @author DELL
 */
public class ClassMarkBO {
    ClassMarkFilter filter;
    ClassMarkResult result[];

    public ClassMarkFilter getFilter() {
        return filter;
    }

    public void setFilter(ClassMarkFilter filter) {
        this.filter = filter;
    }

    public ClassMarkResult[] getResult() {
        return result;
    }

    public void setResult(ClassMarkResult[] result) {
        this.result = result;
    }
    
    
}
