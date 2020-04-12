/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews.businessUtils;

/**
 *
 * @author IBD Technologies
 */
public class ExistingAudit {
    int versionNumber;
    String makerID;
    String recordStatus;
    String authStatus;
    String relatioshipOperation;

    public String getRelatioshipOperation() {
        return relatioshipOperation;
    }

    public void setRelatioshipOperation(String relatioshipOperation) {
        this.relatioshipOperation = relatioshipOperation;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getMakerID() {
        return makerID;
    }

    public void setMakerID(String makerID) {
        this.makerID = makerID;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }
}
