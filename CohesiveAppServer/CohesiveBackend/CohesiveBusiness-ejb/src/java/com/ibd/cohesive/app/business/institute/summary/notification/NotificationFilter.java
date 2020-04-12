
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.notification;

/**
 *
 * @author DELL
 */
public class NotificationFilter {
   String notificationType;
   String mediaCommunication;
   String authStatus;
   String creationDate;


    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }


    public String getMediaCommunication() {
        return mediaCommunication;
    }

    public void setMediaCommunication(String mediaCommunication) {
        this.mediaCommunication = mediaCommunication;
    }


    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }


    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
   
   
    
}
