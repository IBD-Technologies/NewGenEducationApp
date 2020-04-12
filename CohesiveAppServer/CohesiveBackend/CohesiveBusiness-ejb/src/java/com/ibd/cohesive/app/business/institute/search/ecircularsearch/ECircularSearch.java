/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.ecircularsearch;

/**
 *
 * @author DELL
 */
public class ECircularSearch {
    String searchFilter;
    ECircularSearchResult result[];

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public ECircularSearchResult[] getResult() {
        return result;
    }

    public void setResult(ECircularSearchResult[] result) {
        this.result = result;
    }
    
    
    
}
