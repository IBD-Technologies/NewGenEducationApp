/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.groupmapping;

/**
 *
 * @author DELL
 */
public class Group {
    String standard;
    String section;
    String studentID;
    String studentNAme;
    String operation;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
    
    
    

    public String getStudentNAme() {
        return studentNAme;
    }

    public void setStudentNAme(String studentNAme) {
        this.studentNAme = studentNAme;
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

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    
}
