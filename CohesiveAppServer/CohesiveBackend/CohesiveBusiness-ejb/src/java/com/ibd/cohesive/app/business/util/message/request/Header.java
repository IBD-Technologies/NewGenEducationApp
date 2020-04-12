/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.util.message.request;

import java.util.Map;

/**
 *
 * @author IBD Technologies
 */
public class Header {
String msgID; 
String service;
String source;
String operation;
Map<String,String>businessEntity;

//String businessEntity;
String instituteID;
String userID;
//String key;
String Token;

   /* public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }*/

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }
    public Header()
    {
        
        
    }       
    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Map<String, String> getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(Map<String, String> businessEntity) {
        this.businessEntity = businessEntity;
    }
//    public String getBusinessEntity() {
//        return businessEntity;
//    }
//
//    public void setBusinessEntity(String businessEntity) {
//        this.businessEntity = businessEntity;
//    }

    public String getInstituteID() {
        return instituteID;
    }

    public void setInstituteID(String instituteID) {
        this.instituteID = instituteID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    
    
    
    
}
