/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.generallevelconfiguration;

/**
 *
 * @author DELL
 */
public class ExamMaster {
    String examType;
    String description;
    String Operation;
    String otherLanguageDescription;

    public String getOtherLanguageDescription() {
        return otherLanguageDescription;
    }

    public void setOtherLanguageDescription(String otherLanguageDescription) {
        this.otherLanguageDescription = otherLanguageDescription;
    }

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String Operation) {
        this.Operation = Operation;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
