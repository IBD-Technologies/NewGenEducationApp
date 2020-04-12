/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.user.summary.userprofile;

/**
 *
 * @author DELL
 */
public class UserProfileBO {
    UserProfileFilter filter;
    UserProfileResult result[];

    public UserProfileFilter getFilter() {
        return filter;
    }

    public void setFilter(UserProfileFilter filter) {
        this.filter = filter;
    }

    public UserProfileResult[] getResult() {
        return result;
    }

    public void setResult(UserProfileResult[] result) {
        this.result = result;
    }
    
}
