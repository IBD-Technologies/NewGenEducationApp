/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.util;

import java.io.Serializable;

/**
 *
 * @author IBD Technologies
 */
public class JWEInput implements Serializable {
    private static final long serialversionUID = 
                                 6785576786686344889L; 
    String token;
    String userid;
    String instid;
    boolean expired;
    String secKey;
    boolean InstituteChanged;

    public boolean isInstituteChanged() {
        return InstituteChanged;
    }

    public void setInstituteChanged(boolean InstituteChanged) {
        this.InstituteChanged = InstituteChanged;
    }
    public String getSecKey() {
        return secKey;
    }

    public void setSecKey(String secKey) {
        this.secKey = secKey;
    }
    public JWEInput(String token,String userid,String instid,String secKey)
    {
        this.token=token;
        this.userid=userid;
        this.instid=instid;
        this.secKey=secKey;
        this.InstituteChanged=false;
    }     
    
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getInstid() {
        return instid;
    }

    public void setInstid(String instid) {
        this.instid = instid;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
    
}
