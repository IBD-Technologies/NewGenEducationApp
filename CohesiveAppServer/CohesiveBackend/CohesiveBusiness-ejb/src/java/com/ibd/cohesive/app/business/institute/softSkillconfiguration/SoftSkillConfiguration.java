/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.softSkillconfiguration;

/**
 *
 * @author DELL
 */
public class SoftSkillConfiguration {
    String instituteID;
    String instituteName;
    GradeMaster gradeMaster[];
    SkillMaster skillMaster[];

    public String getInstituteID() {
        return instituteID;
    }

    public void setInstituteID(String instituteID) {
        this.instituteID = instituteID;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public GradeMaster[] getGradeMaster() {
        return gradeMaster;
    }

    public void setGrademaster(GradeMaster[] gradeMaster) {
        this.gradeMaster = gradeMaster;
    }

    public SkillMaster[] getSkillMaster() {
        return skillMaster;
    }

    public void setSkillMaster(SkillMaster[] skillMaster) {
        this.skillMaster = skillMaster;
    }

   
    
    
}
