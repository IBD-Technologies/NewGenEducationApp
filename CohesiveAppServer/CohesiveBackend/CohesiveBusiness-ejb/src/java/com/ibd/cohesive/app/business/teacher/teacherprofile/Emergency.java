/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.teacherprofile;

/**
 *
 * @author DELL
 */
public class Emergency {
    String existingMedicalDetail;
    ContactPerson cp[];
    
    public Emergency(){
        
    }

    public String getExistingMedicalDetail() {
        return existingMedicalDetail;
    }

    public void setExistingMedicalDetail(String existingMedicalDetail) {
        this.existingMedicalDetail = existingMedicalDetail;
    }

    

    public ContactPerson[] getCp() {
        return cp;
    }

    public void setCp(ContactPerson[] cp) {
        this.cp = cp;
    }
}
