/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.attendance;

import com.ibd.cohesive.app.business.classentity.classattendance.StudentWiseAttendance;
import com.ibd.cohesive.app.business.student.studentattendanceservice.PeriodAttendance;

/**
 *
 * @author DELL
 */
public class ClassAttendance {
    String recordStatus;
    String authStatus;
    String makerRemarks;
    String checkerRemarks;
    String versionNo;
    
    PeriodAttendance foreNoonAttendance[];
    PeriodAttendance afterNoonAttendance[];

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getMakerRemarks() {
        return makerRemarks;
    }

    public void setMakerRemarks(String makerRemarks) {
        this.makerRemarks = makerRemarks;
    }

    public String getCheckerRemarks() {
        return checkerRemarks;
    }

    public void setCheckerRemarks(String checkerRemarks) {
        this.checkerRemarks = checkerRemarks;
    }

    public PeriodAttendance[] getForeNoonAttendance() {
        return foreNoonAttendance;
    }

    public void setForeNoonAttendance(PeriodAttendance[] foreNoonAttendance) {
        this.foreNoonAttendance = foreNoonAttendance;
    }

    public PeriodAttendance[] getAfterNoonAttendance() {
        return afterNoonAttendance;
    }

    public void setAfterNoonAttendance(PeriodAttendance[] afterNoonAttendance) {
        this.afterNoonAttendance = afterNoonAttendance;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    
   
    
    

}
