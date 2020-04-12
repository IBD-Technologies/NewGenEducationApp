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
public class NotificationMaster {
    String notificationType;
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

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
