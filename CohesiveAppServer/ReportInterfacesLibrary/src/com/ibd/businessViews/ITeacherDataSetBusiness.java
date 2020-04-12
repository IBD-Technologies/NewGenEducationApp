/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import javax.ejb.Remote;

/**
 *
 * @author IBD Technologies
 */
@Remote
public interface ITeacherDataSetBusiness {
    public String getTeacherMarksDetail_DataSet(String p_teacherID,String p_instanceID)throws DBProcessingException,DBValidationException;
    public String getTeacherMarksDetail_DataSet(String p_teacherID,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;
    public String getTeacherAttendanceSummary_DataSet(String p_teacherID,String p_instanceID)throws DBProcessingException,DBValidationException;
    public String getTeacherMarksSummary_DataSet(String p_teacherID,String p_instanceID)throws DBProcessingException,DBValidationException;
    public String getSubstituteAvailabilityInOtherClass_DataSet(String p_teacherID,String p_instanceID,String p_date,String userID)throws DBProcessingException,DBValidationException;
    public String getSubstituteAvailabilityInSameClass_DataSet(String p_teacherID,String p_instanceID,String p_date,String userID)throws DBProcessingException,DBValidationException; 
    public String getTVW_TEACHER_PROFILE_BUSINESS_DataSet(String p_teacherID,String p_instanceID)throws DBProcessingException,DBValidationException;
    public String getTVW_CONTACT_PERSON_DETAILS_BUSINESS_DataSet(String p_teacherID,String p_instanceID)throws DBProcessingException,DBValidationException;
    public String getTVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DataSet(String p_teacherID,String p_instanceID)throws DBProcessingException,DBValidationException;
    public String getTeacherTimeTableReport_DataSet(String p_teacherID,String p_instanceID,String p_date,String userID)throws DBProcessingException,DBValidationException;
    public String getSubstituteReportParam_DataSet(String p_teacherID,String p_instanceID,String p_date)throws DBProcessingException,DBValidationException;




}
