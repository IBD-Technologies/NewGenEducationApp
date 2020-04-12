/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentprofile;

/**
 *
 * @author DELL
 */
public class ContactPerson {
    String cp_name;
    String cp_relationship;
    String cp_occupation;
    String cp_mailID;
    String cp_ID;
    String cp_contactNo;
    String cp_imgPath;
    String operation;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
    
    
    
    public String getCp_imgPath() {
        return cp_imgPath;
    }

    public void setCp_imgPath(String cp_imgPath) {
        this.cp_imgPath = cp_imgPath;
    }

    public String getCp_contactNo() {
        return cp_contactNo;
    }

    public void setCp_contactNo(String cp_contactNo) {
        this.cp_contactNo = cp_contactNo;
    }
    
    public ContactPerson(){
        
    }

    public String getCp_ID() {
        return cp_ID;
    }

    public void setCp_ID(String cp_ID) {
        this.cp_ID = cp_ID;
    }

    public String getCp_name() {
        return cp_name;
    }

    public void setCp_name(String cp_name) {
        this.cp_name = cp_name;
    }

    public String getCp_relationship() {
        return cp_relationship;
    }

    public void setCp_relationship(String cp_relationship) {
        this.cp_relationship = cp_relationship;
    }

    public String getCp_occupation() {
        return cp_occupation;
    }

    public void setCp_occupation(String cp_occupation) {
        this.cp_occupation = cp_occupation;
    }

    public String getCp_mailID() {
        return cp_mailID;
    }

    public void setCp_mailID(String cp_mailID) {
        this.cp_mailID = cp_mailID;
    }
}
