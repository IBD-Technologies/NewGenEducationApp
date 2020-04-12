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
public class FeeTypeMaster {
    String feeType;
    String feeDescription;
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
    
    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFeeDescription() {
        return feeDescription;
    }

    public void setFeeDescription(String feeDescription) {
        this.feeDescription = feeDescription;
    }
    
}
