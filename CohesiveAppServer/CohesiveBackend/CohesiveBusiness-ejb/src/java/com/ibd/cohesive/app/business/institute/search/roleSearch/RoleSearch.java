/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.roleSearch;


/**
 *
 * @author DELL
 */
public class RoleSearch {
    String searchFilter;
    RoleSearchResult result[];

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public RoleSearchResult[] getResult() {
        return result;
    }

    public void setResult(RoleSearchResult[] result) {
        this.result = result;
    }
}
