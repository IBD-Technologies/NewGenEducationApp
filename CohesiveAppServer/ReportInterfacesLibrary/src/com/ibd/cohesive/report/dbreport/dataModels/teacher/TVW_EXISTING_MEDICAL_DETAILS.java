/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.teacher;

/**
 *
 * @author IBD Technologies
 */
public class TVW_EXISTING_MEDICAL_DETAILS {
    String TEACHER_ID;
    String MEDICAL_DETAIL_ID;
    String MEDICAL_DETAILS;
    String VERSION_NUMBER;

    public String getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(String TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
    }

    public String getMEDICAL_DETAIL_ID() {
        return MEDICAL_DETAIL_ID;
    }

    public void setMEDICAL_DETAIL_ID(String MEDICAL_DETAIL_ID) {
        this.MEDICAL_DETAIL_ID = MEDICAL_DETAIL_ID;
    }

    public String getMEDICAL_DETAILS() {
        return MEDICAL_DETAILS;
    }

    public void setMEDICAL_DETAILS(String MEDICAL_DETAILS) {
        this.MEDICAL_DETAILS = MEDICAL_DETAILS;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }
     
}
