/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.ECircularSummary;

/**
 *
 * @author DELL
 */
public class ECircularBO {
    ECircularFilter filter;
    ECircularResult result[];

    public ECircularFilter getFilter() {
        return filter;
    }

    public void setFilter(ECircularFilter filter) {
        this.filter = filter;
    }

    public ECircularResult[] getResult() {
        return result;
    }

    public void setResult(ECircularResult[] result) {
        this.result = result;
    }
    
    
}
