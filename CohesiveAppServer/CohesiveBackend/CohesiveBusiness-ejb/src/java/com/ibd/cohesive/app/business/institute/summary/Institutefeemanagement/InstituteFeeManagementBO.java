/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.Institutefeemanagement;

/**
 *
 * @author DELL
 */
public class InstituteFeeManagementBO {
    InstituteFeeManagementFilter filter;
    InstituteFeeManagementResult result[];

    public InstituteFeeManagementFilter getFilter() {
        return filter;
    }

    public void setFilter(InstituteFeeManagementFilter filter) {
        this.filter = filter;
    }

    public InstituteFeeManagementResult[] getResult() {
        return result;
    }

    public void setResult(InstituteFeeManagementResult[] result) {
        this.result = result;
    }
    
    
}
