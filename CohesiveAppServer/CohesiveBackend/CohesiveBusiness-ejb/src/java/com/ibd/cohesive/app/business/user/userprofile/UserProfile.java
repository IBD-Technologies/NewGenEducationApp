/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.user.userprofile;

/**
 *
 * @author DELL
 */
public class UserProfile {
    String userName;
    String userID;
    String userType;
    String status;
    String homeInstitue;
    String homeInstituteName;
    String teacherID;
    String teacherName;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    
    

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    
    

    public String getHomeInstituteName() {
        return homeInstituteName;
    }

    public void setHomeInstituteName(String homeInstituteName) {
        this.homeInstituteName = homeInstituteName;
    }
    
    

    public String getHomeInstitue() {
        return homeInstitue;
    }

    public void setHomeInstitue(String homeInstitue) {
        this.homeInstitue = homeInstitue;
    }
    
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    String emailID;
    String mobileNo;
    String password;
    ParentStudentRoleMapping parentStudentMapping[];
    ClassEntityRoleMapping classEntityRoleMapping[];
    TeacherEntityRoleMapping teacherEntityMapping[];
    InstituteEntityRoleMapping instituteEntityMapping[];

    public ParentStudentRoleMapping[] getParentStudentMapping() {
        return parentStudentMapping;
    }

    public void setParentStudentMapping(ParentStudentRoleMapping[] parentStudentMapping) {
        this.parentStudentMapping = parentStudentMapping;
    }

    public ClassEntityRoleMapping[] getClassEntityRoleMapping() {
        return classEntityRoleMapping;
    }

    public void setClassEntityRoleMapping(ClassEntityRoleMapping[] classEntityRoleMapping) {
        this.classEntityRoleMapping = classEntityRoleMapping;
    }

    public TeacherEntityRoleMapping[] getTeacherEntityMapping() {
        return teacherEntityMapping;
    }

    public void setTeacherEntityMapping(TeacherEntityRoleMapping[] teacherEntityMapping) {
        this.teacherEntityMapping = teacherEntityMapping;
    }

    public InstituteEntityRoleMapping[] getInstituteEntityMapping() {
        return instituteEntityMapping;
    }

    public void setInstituteEntityMapping(InstituteEntityRoleMapping[] instituteEntityMapping) {
        this.instituteEntityMapping = instituteEntityMapping;
    }

    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    String expiryDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }


    

}
