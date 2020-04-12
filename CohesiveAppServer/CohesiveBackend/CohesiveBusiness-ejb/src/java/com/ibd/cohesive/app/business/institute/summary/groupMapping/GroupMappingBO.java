/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.groupMapping;

/**
 *
 * @author DELL
 */
public class GroupMappingBO {
    GroupMappingFilter filter;
    GroupMappingResult result[];

    public GroupMappingFilter getFilter() {
        return filter;
    }

    public void setFilter(GroupMappingFilter filter) {
        this.filter = filter;
    }

    public GroupMappingResult[] getResult() {
        return result;
    }

    public void setResult(GroupMappingResult[] result) {
        this.result = result;
    }
    
    
    
}
