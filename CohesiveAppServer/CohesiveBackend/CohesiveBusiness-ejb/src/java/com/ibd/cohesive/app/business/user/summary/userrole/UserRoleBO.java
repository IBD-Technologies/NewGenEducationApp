/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.user.summary.userrole;

/**
 *
 * @author DELL
 */
public class UserRoleBO {
    UserRoleFilter filter;
    UserRoleResult result[];

    public UserRoleFilter getFilter() {
        return filter;
    }

    public void setFilter(UserRoleFilter filter) {
        this.filter = filter;
    }

    public UserRoleResult[] getResult() {
        return result;
    }

    public void setResult(UserRoleResult[] result) {
        this.result = result;
    }
    
    
}
