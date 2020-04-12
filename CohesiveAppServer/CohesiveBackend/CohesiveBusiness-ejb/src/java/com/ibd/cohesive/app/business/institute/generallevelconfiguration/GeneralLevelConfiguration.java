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
public class GeneralLevelConfiguration {
    String instituteID;
    String instituteName;
    String profileImgPath;
    SubjectMaster[] subjectMaster;
    GradeMaster[] gradeMaster;
    ExamMaster[] examMaster;
    NotificationMaster[] notificationMaster;
    FeeTypeMaster[] feeMaster;

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getInstituteID() {
        return instituteID;
    }

    public void setInstituteID(String instituteID) {
        this.instituteID = instituteID;
    }

    public String getProfileImgPath() {
        return profileImgPath;
    }

    public void setProfileImgPath(String profileImgPath) {
        this.profileImgPath = profileImgPath;
    }

    public SubjectMaster[] getSubjectMaster() {
        return subjectMaster;
    }

    public void setSubjectMaster(SubjectMaster[] subjectMaster) {
        this.subjectMaster = subjectMaster;
    }

    public GradeMaster[] getGradeMaster() {
        return gradeMaster;
    }

    public void setGradeMaster(GradeMaster[] gradeMaster) {
        this.gradeMaster = gradeMaster;
    }

    public ExamMaster[] getExamMaster() {
        return examMaster;
    }

    public void setExamMaster(ExamMaster[] examMaster) {
        this.examMaster = examMaster;
    }

    public NotificationMaster[] getNotificationMaster() {
        return notificationMaster;
    }

    public void setNotificationMaster(NotificationMaster[] notificationMaster) {
        this.notificationMaster = notificationMaster;
    }

    public FeeTypeMaster[] getFeeMaster() {
        return feeMaster;
    }

    public void setFeeMaster(FeeTypeMaster[] feeMaster) {
        this.feeMaster = feeMaster;
    }
    
}
