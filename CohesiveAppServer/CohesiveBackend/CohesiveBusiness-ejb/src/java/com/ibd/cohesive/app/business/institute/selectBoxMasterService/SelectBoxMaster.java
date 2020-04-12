/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.selectBoxMasterService;

import java.util.Map;

/**
 *
 * @author DELL
 */
public class SelectBoxMaster {
    String master;
    Map<String,String>inputMap;
    ClassMaster classMaster[];
    StandardMaster standardMaster[];
    SectionMaster sectionMaster[];
    SubjectMaster subjectMaster[];
    ExamMaster examMaster[];
    NotificationMaster notificationMaster[];
    FeeMaster feeMaster[];
    PeriodMaster periodmaster[];

    public PeriodMaster[] getPeriodmaster() {
        return periodmaster;
    }

    public void setPeriodmaster(PeriodMaster[] periodmaster) {
        this.periodmaster = periodmaster;
    }
    
    

    public FeeMaster[] getFeeMaster() {
        return feeMaster;
    }

    public void setFeeMaster(FeeMaster[] feeMaster) {
        this.feeMaster = feeMaster;
    }
    
    

    public NotificationMaster[] getNotificationMaster() {
        return notificationMaster;
    }

    public void setNotificationMaster(NotificationMaster[] notificationMaster) {
        this.notificationMaster = notificationMaster;
    }
    
    

    public ExamMaster[] getExamMaster() {
        return examMaster;
    }

    public void setExamMaster(ExamMaster[] examMaster) {
        this.examMaster = examMaster;
    }
    
    

    public SubjectMaster[] getSubjectMaster() {
        return subjectMaster;
    }

    public void setSubjectMaster(SubjectMaster[] subjectMaster) {
        this.subjectMaster = subjectMaster;
    }
    
    

    public SectionMaster[] getSectionMaster() {
        return sectionMaster;
    }

    public void setSectionMaster(SectionMaster[] sectionMaster) {
        this.sectionMaster = sectionMaster;
    }
    
    

    public StandardMaster[] getStandardMaster() {
        return standardMaster;
    }

    public void setStandardMaster(StandardMaster[] standardMaster) {
        this.standardMaster = standardMaster;
    }
    
    

    public ClassMaster[] getClassMaster() {
        return classMaster;
    }

    public void setClassMaster(ClassMaster[] classMaster) {
        this.classMaster = classMaster;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public Map<String, String> getInputMap() {
        return inputMap;
    }

    public void setInputMap(Map<String, String> inputMap) {
        this.inputMap = inputMap;
    }

    
    
    
}
