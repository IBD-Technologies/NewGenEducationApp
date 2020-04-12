/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.classattendance;

/**
 *
 * @author DELL
 */
public class AttendanceValidation {
    boolean p_precence;
    boolean a_precence;
    boolean hyf_precence;

    public AttendanceValidation() {
        this.p_precence = false;
        this.a_precence = false;
        this.hyf_precence = false;
    }
    
    
    
    
    

    public boolean isP_precence() {
        return p_precence;
    }

    public void setP_precence(boolean p_precence) {
        this.p_precence = p_precence;
    }

    public boolean isA_precence() {
        return a_precence;
    }

    public void setA_precence(boolean a_precence) {
        this.a_precence = a_precence;
    }

    public boolean isHyf_precence() {
        return hyf_precence;
    }

    public void setHyf_precence(boolean hyf_precence) {
        this.hyf_precence = hyf_precence;
    }
    
    
}
