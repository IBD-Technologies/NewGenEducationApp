/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.teacherprofile;

/**
 *
 * @author DELL
 */
public class TeacherProfile {
    
    String teacherID;
    General gen;
    Emergency emer;
    String teacherName;
    String profileImgPath;
    
    public TeacherProfile(){
        
    }

   
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

    public General getGen() {
        return gen;
    }

    public void setGen(General gen) {
        this.gen = gen;
    }

    public Emergency getEmer() {
        return emer;
    }

    public void setEmer(Emergency emer) {
        this.emer = emer;
    }

    public String getProfileImgPath() {
        return profileImgPath;
    }

    public void setProfileImgPath(String profileImgPath) {
        this.profileImgPath = profileImgPath;
    }
    
    
    
}
