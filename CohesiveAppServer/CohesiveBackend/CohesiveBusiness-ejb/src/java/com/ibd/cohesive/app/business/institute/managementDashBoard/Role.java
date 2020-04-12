/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.managementDashBoard;

import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author DELL
 */
public class Role {
    String roleID;
    Map<String,String>service;
    Entity[] entity;
    public HashSet<String> studentIds;
    public HashSet<String> teacherIds;
    public HashSet<String> classes;
    public HashSet<String> institutes;

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public Entity[] getEntity() {
        return entity;
    }

    public void setEntity(Entity[] entity) {
        this.entity = entity;
    }

    public Map<String, String> getService() {
        return service;
    }

    public void setService(Map<String, String> service) {
        this.service = service;
    }

    public HashSet<String> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(HashSet<String> studentIds) {
        this.studentIds = studentIds;
    }

    public HashSet<String> getTeacherIds() {
        return teacherIds;
    }

    public void setTeacherIds(HashSet<String> teacherIds) {
        this.teacherIds = teacherIds;
    }

    public HashSet<String> getClasses() {
        return classes;
    }

    public void setClasses(HashSet<String> classes) {
        this.classes = classes;
    }

    public HashSet<String> getInstitutes() {
        return institutes;
    }

    public void setInstitutes(HashSet<String> institutes) {
        this.institutes = institutes;
    }

    

    

    
    
    
    
    
}
