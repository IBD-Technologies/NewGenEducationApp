/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.instituteSearch;


/**
 *
 * @author DELL
 */
public class InstituteSearch {
    String searchFilter;
    InstituteSearchResult result[];

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public InstituteSearchResult[] getResult() {
        return result;
    }

    public void setResult(InstituteSearchResult[] result) {
        this.result = result;
    }
}
