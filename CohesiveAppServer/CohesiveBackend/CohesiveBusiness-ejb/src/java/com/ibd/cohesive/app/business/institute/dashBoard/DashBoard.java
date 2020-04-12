/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.dashBoard;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class DashBoard {
    String totalNoOfTeachers;
    String totalNoOfStudents;
    String teacherAttendance;
    String studentAttendance;
    String teacherWorkingDays;
    String teacherLeaveDays;
    String classOfTheTeacher;
    
    String smsLimit;
    String currentSMSBalance;
    FeeResult feeResult[]; 
    ArrayList<UnAuthPending> pending;
    ClassOverDue overDue[];

    public ClassOverDue[] getOverDue() {
        return overDue;
    }

    public void setOverDue(ClassOverDue[] overDue) {
        this.overDue = overDue;
    }
    
    

    public FeeResult[] getFeeResult() {
        return feeResult;
    }

    public void setFeeResult(FeeResult[] feeResult) {
        this.feeResult = feeResult;
    }
    

    public String getTotalNoOfTeachers() {
        return totalNoOfTeachers;
    }

    public void setTotalNoOfTeachers(String totalNoOfTeachers) {
        this.totalNoOfTeachers = totalNoOfTeachers;
    }

    public String getTotalNoOfStudents() {
        return totalNoOfStudents;
    }

    public void setTotalNoOfStudents(String totalNoOfStudents) {
        this.totalNoOfStudents = totalNoOfStudents;
    }
    
    

    public String getTeacherAttendance() {
        return teacherAttendance;
    }

    public void setTeacherAttendance(String teacherAttendance) {
        this.teacherAttendance = teacherAttendance;
    }

    public String getStudentAttendance() {
        return studentAttendance;
    }

    public void setStudentAttendance(String studentAttendance) {
        this.studentAttendance = studentAttendance;
    }

    public String getTeacherWorkingDays() {
        return teacherWorkingDays;
    }

    public void setTeacherWorkingDays(String teacherWorkingDays) {
        this.teacherWorkingDays = teacherWorkingDays;
    }

    public String getTeacherLeaveDays() {
        return teacherLeaveDays;
    }

    public void setTeacherLeaveDays(String teacherLeaveDays) {
        this.teacherLeaveDays = teacherLeaveDays;
    }

    

  

    public String getSmsLimit() {
        return smsLimit;
    }

    public void setSmsLimit(String smsLimit) {
        this.smsLimit = smsLimit;
    }

    public String getCurrentSMSBalance() {
        return currentSMSBalance;
    }

    public void setCurrentSMSBalance(String currentSMSBalance) {
        this.currentSMSBalance = currentSMSBalance;
    }

    public ArrayList<UnAuthPending> getPending() {
        return pending;
    }

    public void setPending(ArrayList<UnAuthPending> pending) {
        this.pending = pending;
    }

    public String getClassOfTheTeacher() {
        return classOfTheTeacher;
    }

    public void setClassOfTheTeacher(String classOfTheTeacher) {
        this.classOfTheTeacher = classOfTheTeacher;
    }

  
    
    
    
}
