/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.otherActivitySearch;


/**
 *
 * @author DELL
 */
public class OtherActivitySearch {
    String searchFilter;
    OtherActivitySearchResult result[];

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public OtherActivitySearchResult[] getResult() {
        return result;
    }

    public void setResult(OtherActivitySearchResult[] result) {
        this.result = result;
    }
}
