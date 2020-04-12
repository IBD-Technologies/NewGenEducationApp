/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.feeSearch;


/**
 *
 * @author DELL
 */
public class FeeSearch {
    String searchFilter;
    FeeSearchResult result[];

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public FeeSearchResult[] getResult() {
        return result;
    }

    public void setResult(FeeSearchResult[] result) {
        this.result = result;
    }
}
