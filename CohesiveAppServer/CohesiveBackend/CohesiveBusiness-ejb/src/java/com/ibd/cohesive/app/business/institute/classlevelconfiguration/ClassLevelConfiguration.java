/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.classlevelconfiguration;

/**
 *
 * @author DELL
 */
public class ClassLevelConfiguration {
    String instituteID;
    String instituteName;
    String standard;
    String section;
    String teacherID;
    String teacherNAme;
    String attendance;

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
    
    

    public String getTeacherNAme() {
        return teacherNAme;
    }

    public void setTeacherNAme(String teacherNAme) {
        this.teacherNAme = teacherNAme;
    }
    
    
//    StandardMaster standardMaster[];
    PeriodTimings periodTimings[];    

    public String getInstituteID() {
        return instituteID;
    }

    public void setInstituteID(String instituteID) {
        this.instituteID = instituteID;
    }

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

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }



    public PeriodTimings[] getPeriodTimings() {
        return periodTimings;
    }

    public void setPeriodTimings(PeriodTimings[] periodTimings) {
        this.periodTimings = periodTimings;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }
    
    
    
}
