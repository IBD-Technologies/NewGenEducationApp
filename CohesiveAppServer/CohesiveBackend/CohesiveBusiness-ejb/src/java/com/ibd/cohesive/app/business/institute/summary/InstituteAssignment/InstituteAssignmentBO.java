/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.InstituteAssignment;

/**
 *
 * @author DELL
 */
public class InstituteAssignmentBO {
    InstituteAssignmentFilter filter;
    InstituteAssignmentResult result[];

    public InstituteAssignmentFilter getFilter() {
        return filter;
    }

    public void setFilter(InstituteAssignmentFilter filter) {
        this.filter = filter;
    }

    public InstituteAssignmentResult[] getResult() {
        return result;
    }

    public void setResult(InstituteAssignmentResult[] result) {
        this.result = result;
    }
    
    
}
