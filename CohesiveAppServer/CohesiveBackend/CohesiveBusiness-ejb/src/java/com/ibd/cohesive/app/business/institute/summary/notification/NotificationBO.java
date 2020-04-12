/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.notification;

/**
 *
 * @author DELL
 */
public class NotificationBO {
    NotificationResult result[];
    NotificationFilter filter;

    public NotificationResult[] getResult() {
        return result;
    }

    public void setResult(NotificationResult[] result) {
        this.result = result;
    }

    public NotificationFilter getFilter() {
        return filter;
    }

    public void setFilter(NotificationFilter filter) {
        this.filter = filter;
    }
    
    
}
