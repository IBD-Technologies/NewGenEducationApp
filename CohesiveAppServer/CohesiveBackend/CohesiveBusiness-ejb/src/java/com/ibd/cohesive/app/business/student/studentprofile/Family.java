/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studentprofile;

/**
 *
 * @author DELL
 */
public class Family {
    String memberID;
    String memberName;
    String memberRelationship;
    String memberOccupation;
    String memberImgPath;
    String operation;
    String notificationRequired;

    public String getNotificationRequired() {
        return notificationRequired;
    }

    public void setNotificationRequired(String notificationRequired) {
        this.notificationRequired = notificationRequired;
    }
    
    

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
    
    

    public String getMemberImgPath() {
        return memberImgPath;
    }

    public void setMemberImgPath(String memberImgPath) {
        this.memberImgPath = memberImgPath;
    }

    public String getMemberOccupation() {
        return memberOccupation;
    }

    public void setMemberOccupation(String memberOccupation) {
        this.memberOccupation = memberOccupation;
    }
    String memberEmailID;
    String memberContactNo;

    public Family(){
        
    }
    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberRelationship() {
        return memberRelationship;
    }

    public void setMemberRelationship(String memberRelationship) {
        this.memberRelationship = memberRelationship;
    }

    public String getMemberEmailID() {
        return memberEmailID;
    }

    public void setMemberEmailID(String memberEmailID) {
        this.memberEmailID = memberEmailID;
    }

    public String getMemberContactNo() {
        return memberContactNo;
    }

    public void setMemberContactNo(String memberContactNo) {
        this.memberContactNo = memberContactNo;
    }
}
