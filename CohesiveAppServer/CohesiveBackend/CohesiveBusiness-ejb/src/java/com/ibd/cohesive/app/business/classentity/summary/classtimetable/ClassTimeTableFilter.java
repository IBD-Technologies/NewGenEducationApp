/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.summary.classtimetable;

/**
 *
 * @author DELL
 */
public class ClassTimeTableFilter {
    String standard;
    String section;
    String authStatus;
//    String recordStatus;

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

//    public String getRecordStatus() {
//        return recordStatus;
//    }
//
//    public void setRecordStatus(String recordStatus) {
//        this.recordStatus = recordStatus;
//    }
}
