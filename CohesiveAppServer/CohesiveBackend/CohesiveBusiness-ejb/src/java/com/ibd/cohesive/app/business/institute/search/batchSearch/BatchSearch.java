/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.batchSearch;



/**
 *
 * @author DELL
 */
public class BatchSearch {
    String searchFilter;
    BatchSearchResult result[];

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public BatchSearchResult[] getResult() {
        return result;
    }

    public void setResult(BatchSearchResult[] result) {
        this.result = result;
    }
}
