/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.holidaymaintenance;

/**
 *
 * @author DELL
 */
public class HolidayMaintenanceBO {
    HolidayMaintenanceFilter filter;
    HolidayMaintenanceResult result[];

    public HolidayMaintenanceFilter getFilter() {
        return filter;
    }

    public void setFilter(HolidayMaintenanceFilter filter) {
        this.filter = filter;
    }

    public HolidayMaintenanceResult[] getResult() {
        return result;
    }

    public void setResult(HolidayMaintenanceResult[] result) {
        this.result = result;
    }
    
    
}
