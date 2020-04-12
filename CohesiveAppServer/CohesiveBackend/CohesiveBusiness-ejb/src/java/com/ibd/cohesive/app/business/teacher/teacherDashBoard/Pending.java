/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.teacherDashBoard;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class Pending {
    ArrayList<String> primaryKey;
    String serviceType;
    String serviceName;
    String count;

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public ArrayList<String> getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(ArrayList<String> primaryKey) {
        this.primaryKey = primaryKey;
    }

    
}
