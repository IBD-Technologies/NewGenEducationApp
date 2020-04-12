/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.assignmentSearch;


/**
 *
 * @author DELL
 */
public class AssignmentSearch {
    String searchFilter;
    AssignmentSearchResult result[];

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public AssignmentSearchResult[] getResult() {
        return result;
    }

    public void setResult(AssignmentSearchResult[] result) {
        this.result = result;
    }
}
