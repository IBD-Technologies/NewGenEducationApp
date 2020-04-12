/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentattendancesummary;

import com.ibd.cohesive.db.readbuffer.DBRecord;

/**
 *
 * @author DELL
 */
public class MonthRecords {
    String year;
    String month;
    DBRecord monthRecord;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public DBRecord getMonthRecord() {
        return monthRecord;
    }

    public void setMonthRecord(DBRecord monthRecord) {
        this.monthRecord = monthRecord;
    }
    
    
}
