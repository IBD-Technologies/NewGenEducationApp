/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.instituteUserSearch;

/**
 *
 * @author DELL
 */
public class InstituteUserSearch {
    String searchFilter;
    InstituteUserSearchResult result[];

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public InstituteUserSearchResult[] getResult() {
        return result;
    }

    public void setResult(InstituteUserSearchResult[] result) {
        this.result = result;
    }
}
