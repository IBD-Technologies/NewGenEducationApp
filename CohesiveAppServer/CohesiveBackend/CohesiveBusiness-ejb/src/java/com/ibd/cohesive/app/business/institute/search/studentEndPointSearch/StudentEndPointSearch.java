/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.studentEndPointSearch;

/**
 *
 * @author DELL
 */
public class StudentEndPointSearch {
    String searchFilter;
    StudentEndPointSearchResult result[];

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public StudentEndPointSearchResult[] getResult() {
        return result;
    }

    public void setResult(StudentEndPointSearchResult[] result) {
        this.result = result;
    }
    
    
}
