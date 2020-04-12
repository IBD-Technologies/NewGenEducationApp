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
public class MessageInput {
    String title;
    boolean titleAlreadyTranslated;
    String messageType;

    public boolean isTitleAlreadyTranslated() {
        return titleAlreadyTranslated;
    }

    public void setTitleAlreadyTranslated(boolean titleAlreadyTranslated) {
        this.titleAlreadyTranslated = titleAlreadyTranslated;
    }
    
   
    
    
    public MessageAttributes attributes[];

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
    
    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MessageAttributes[] getAttributes() {
        return attributes;
    }

    public void setAttributes(MessageAttributes[] attributes) {
        this.attributes = attributes;
    }
    
    
}
