/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.notificationSearch;


/**
 *
 * @author DELL
 */
public class NotificationSearch {
    String searchFilter;
    NotificationSearchResult result[];

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public NotificationSearchResult[] getResult() {
        return result;
    }

    public void setResult(NotificationSearchResult[] result) {
        this.result = result;
    }
}
