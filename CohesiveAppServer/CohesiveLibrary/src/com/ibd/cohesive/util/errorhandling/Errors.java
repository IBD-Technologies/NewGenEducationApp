/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.util.errorhandling;

/**
 *
 * @author IBD Technologies
 */
public class Errors {
    
    String error_code;
    String error_param;

    public String getError_param() {
        return error_param;
    }

    public void setError_param(String error_param) {
        this.error_param = error_param;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    
    
    
    public Errors(){
        
    }
}
