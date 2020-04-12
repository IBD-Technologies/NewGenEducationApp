/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.classSoftSkill;

import com.ibd.cohesive.app.business.classentity.classmark.GradeDescription;

/**
 *
 * @author DELL
 */
public class ClassSoftSkill {
    String standard;
    String section;
    String exam;
    String skillID;
//    String skillName;
    public Skills skills[];
//    public  GradeDescription gradeDescription[];

//    public GradeDescription[] getGradeDescription() {
//        return gradeDescription;
//    }
//
//    public void setGradeDescription(GradeDescription[] gradeDescription) {
//        this.gradeDescription = gradeDescription;
//    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getSkillID() {
        return skillID;
    }

    public void setSkillID(String skillID) {
        this.skillID = skillID;
    }



    public Skills[] getSkills() {
        return skills;
    }

    public void setSkills(Skills[] skills) {
        this.skills = skills;
    }
    
    
}
