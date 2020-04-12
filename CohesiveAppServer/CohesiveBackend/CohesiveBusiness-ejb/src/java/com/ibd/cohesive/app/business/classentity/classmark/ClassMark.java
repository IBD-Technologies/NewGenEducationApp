/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.classmark;

/**
 *
 * @author DELL
 */
public class ClassMark {
    String standard;
    String section;
    String exam;
    String subjectID;
    String subjectName;
    public Marks mark[];

    public Marks[] getMark() {
        return mark;
    }

    public void setMark(Marks[] mark) {
        this.mark = mark;
    }
    
    public ClassMark(){
        
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

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
    
    
    GradeDescription gradeDescription[];

    public GradeDescription[] getGradeDescription() {
        return gradeDescription;
    }

    public void setGradeDescription(GradeDescription[] gradeDescription) {
        this.gradeDescription = gradeDescription;
    }
    
    
    
}
