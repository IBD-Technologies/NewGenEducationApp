/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.util;

/**
 *
 * @author DELL
 */
public class MessageAttributes {
    String attributeName;
    String attributeValue;
    boolean ID;

    public boolean isID() {
        return ID;
    }

    public void setID(boolean ID) {
        this.ID = ID;
    }
    
    

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
    
}
