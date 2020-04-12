/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.userSearch;

/**
 *
 * @author DELL
 */
public class UserSearch {
    String searchFilter;
    UserSearchResult result[];

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public UserSearchResult[] getResult() {
        return result;
    }

    public void setResult(UserSearchResult[] result) {
        this.result = result;
    }
}
