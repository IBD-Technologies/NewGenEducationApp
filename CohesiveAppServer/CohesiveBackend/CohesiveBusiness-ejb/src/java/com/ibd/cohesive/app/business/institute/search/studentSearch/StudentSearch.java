/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.studentSearch;

/**
 *
 * @author DELL
 */
public class StudentSearch {
    String searchFilter;
    StudentSearchResult result[];

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public StudentSearchResult[] getResult() {
        return result;
    }

    public void setResult(StudentSearchResult[] result) {
        this.result = result;
    }
    
    
}
