/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.teacherSearch;


/**
 *
 * @author DELL
 */
public class TeacherSearch {
    String searchFilter;
    TeacherSearchResult result[];

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public TeacherSearchResult[] getResult() {
        return result;
    }

    public void setResult(TeacherSearchResult[] result) {
        this.result = result;
    }
}
