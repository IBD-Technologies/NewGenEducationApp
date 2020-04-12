/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.assigneeSearch;


/**
 *
 * @author DELL
 */
public class AssigneeSearch {
   String searchFilter;
    AssigneeSearchResult result[];

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public AssigneeSearchResult[] getResult() {
        return result;
    }

    public void setResult(AssigneeSearchResult[] result) {
        this.result = result;
    }
}
