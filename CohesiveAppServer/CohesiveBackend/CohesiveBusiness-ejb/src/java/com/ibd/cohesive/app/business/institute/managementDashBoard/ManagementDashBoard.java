/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.managementDashBoard;

import com.ibd.cohesive.app.business.teacher.teacherDashBoard.Pending;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class ManagementDashBoard {
    String userID;
    String totalStudents;
    String noOfStudentsPresent;
    String totalTeachers;
    String noOfTeachersPresent;
    String totalFeeAmount;
    String totalPaidAmount;
    String totalBalanceAmount;
    String totalOverDueAmount;
    
    ArrayList<Pending>unAuthPendingList;
    ArrayList<Pending>entryPendingList;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ArrayList<Pending> getUnAuthPendingList() {
        return unAuthPendingList;
    }

    public void setUnAuthPendingList(ArrayList<Pending> unAuthPendingList) {
        this.unAuthPendingList = unAuthPendingList;
    }

    public ArrayList<Pending> getEntryPendingList() {
        return entryPendingList;
    }

    public void setEntryPendingList(ArrayList<Pending> entryPendingList) {
        this.entryPendingList = entryPendingList;
    }

    public String getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(String totalStudents) {
        this.totalStudents = totalStudents;
    }

    public String getNoOfStudentsPresent() {
        return noOfStudentsPresent;
    }

    public void setNoOfStudentsPresent(String noOfStudentsPresent) {
        this.noOfStudentsPresent = noOfStudentsPresent;
    }

    public String getTotalTeachers() {
        return totalTeachers;
    }

    public void setTotalTeachers(String totalTeachers) {
        this.totalTeachers = totalTeachers;
    }

    public String getNoOfTeachersPresent() {
        return noOfTeachersPresent;
    }

    public void setNoOfTeachersPresent(String noOfTeachersPresent) {
        this.noOfTeachersPresent = noOfTeachersPresent;
    }

    public String getTotalFeeAmount() {
        return totalFeeAmount;
    }

    public void setTotalFeeAmount(String totalFeeAmount) {
        this.totalFeeAmount = totalFeeAmount;
    }

    public String getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(String totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public String getTotalBalanceAmount() {
        return totalBalanceAmount;
    }

    public void setTotalBalanceAmount(String totalBalanceAmount) {
        this.totalBalanceAmount = totalBalanceAmount;
    }

    public String getTotalOverDueAmount() {
        return totalOverDueAmount;
    }

    public void setTotalOverDueAmount(String totalOverDueAmount) {
        this.totalOverDueAmount = totalOverDueAmount;
    }
    
    
    
}
