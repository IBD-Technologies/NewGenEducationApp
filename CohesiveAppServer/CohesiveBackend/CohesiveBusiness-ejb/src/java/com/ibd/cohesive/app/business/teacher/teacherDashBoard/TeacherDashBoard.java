/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.teacherDashBoard;

import com.ibd.cohesive.app.business.student.parentDashBoard.WeeklyCalender;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class TeacherDashBoard {
    String userID;
    String selfAttendance;
    ClassWiseAttendance classWiseAttendance[];
    WeeklyCalender weeklyCalender[];
    ArrayList<Pending>unAuthPendingList;
    ArrayList<Pending>entryPendingList;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSelfAttendance() {
        return selfAttendance;
    }

    public void setSelfAttendance(String selfAttendance) {
        this.selfAttendance = selfAttendance;
    }

    public ClassWiseAttendance[] getClassWiseAttendance() {
        return classWiseAttendance;
    }

    public void setClassWiseAttendance(ClassWiseAttendance[] classWiseAttendance) {
        this.classWiseAttendance = classWiseAttendance;
    }

    public WeeklyCalender[] getWeeklyCalender() {
        return weeklyCalender;
    }

    public void setWeeklyCalender(WeeklyCalender[] weeklyCalender) {
        this.weeklyCalender = weeklyCalender;
    }

    public ArrayList<Pending> getPendingList() {
        return unAuthPendingList;
    }

    public void setPendingList(ArrayList<Pending> unAuthPendingList) {
        this.unAuthPendingList = unAuthPendingList;
    }

    public ArrayList<Pending> getEntryPendingList() {
        return entryPendingList;
    }

    public void setEntryPendingList(ArrayList<Pending> entryPendingList) {
        this.entryPendingList = entryPendingList;
    }

    
    
    
}
