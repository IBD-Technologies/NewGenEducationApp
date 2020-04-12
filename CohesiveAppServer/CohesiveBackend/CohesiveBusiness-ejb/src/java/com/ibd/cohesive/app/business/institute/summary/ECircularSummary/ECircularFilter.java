/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.ECircularSummary;

/**
 *
 * @author DELL
 */
public class ECircularFilter {
    String circularType;
    String fromDate;
    String toDate;
    String authStatus;

    public String getCircularType() {
        return circularType;
    }

    public void setCircularType(String circularType) {
        this.circularType = circularType;
    }


    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }
    
    
}
