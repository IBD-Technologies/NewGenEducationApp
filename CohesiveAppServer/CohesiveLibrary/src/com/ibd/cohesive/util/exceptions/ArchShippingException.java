/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.util.exceptions;

/**
 *
 * @author DELL
 */
public class ArchShippingException extends Exception{
    String message;
    
    public ArchShippingException(String message){
        this.message = message;
    }
    public String toString(){
        return this.message;
    }
}
