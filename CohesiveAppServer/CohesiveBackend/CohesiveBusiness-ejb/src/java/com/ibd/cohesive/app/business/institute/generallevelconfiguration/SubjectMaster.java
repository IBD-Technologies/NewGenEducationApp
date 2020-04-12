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
public class SubjectMaster {
  String subjectID;
  String subjectName;
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

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
}
