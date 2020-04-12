/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.InstituteOtherActivity;

/**
 *
 * @author DELL
 */
public class InstituteOtherActivityBO {
    InstituteOtherActivityFilter filter;
    InstituteOtherActivityResult result[];

    public InstituteOtherActivityFilter getFilter() {
        return filter;
    }

    public void setFilter(InstituteOtherActivityFilter filter) {
        this.filter = filter;
    }

    public InstituteOtherActivityResult[] getResult() {
        return result;
    }

    public void setResult(InstituteOtherActivityResult[] result) {
        this.result = result;
    }
    
    
    
}
