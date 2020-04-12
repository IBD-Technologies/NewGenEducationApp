/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.exam;


/**
 *
 * @author DELL
 */
public class ClassExamSchedule {
    String standard;
    String section;
    String exam;
    Schedule schedule[];
    String recordStatus;
    String authStatus;
    String makerRemarks;
    String checkerRemarks;
    
    public ClassExamSchedule(){
        
    }

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

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public Schedule[] getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule[] schedule) {
        this.schedule = schedule;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getMakerRemarks() {
        return makerRemarks;
    }

    public void setMakerRemarks(String makerRemarks) {
        this.makerRemarks = makerRemarks;
    }

    public String getCheckerRemarks() {
        return checkerRemarks;
    }

    public void setCheckerRemarks(String checkerRemarks) {
        this.checkerRemarks = checkerRemarks;
    }
    
    
    
}
