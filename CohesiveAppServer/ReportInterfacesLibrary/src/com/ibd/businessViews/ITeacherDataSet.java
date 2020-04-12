/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_CONTACT_PERSON_DETAILS;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_EXISTING_MEDICAL_DETAILS;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_PAYROLL;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_TEACHER_ATTENDANCE_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_TEACHER_ATTENDANCE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_TEACHER_CALENDER;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_TEACHER_LEAVE_MANAGEMENT;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_TEACHER_PROFILE;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_TEACHER_TIMETABLE_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_TEACHER_TIMETABLE_MASTER;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author IBD Technologies
 */
@Remote
public interface ITeacherDataSet {
    public String getTVW_CONTACT_PERSON_DETAILS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
//    public String getTVW_EXISTING_MEDICAL_DETAILS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
//    
//    public String getTVW_PAYROLL_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
//    
//    public String getTVW_TEACHER_ATTENDANCE_DETAIL_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
//    
//    public String getTVW_TEACHER_ATTENDANCE_MASTER_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
//    
//    public String getTVW_TEACHER_CALENDER_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
//    
    public String getTVW_TEACHER_LEAVE_MANAGEMENT_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
     
    public String getTVW_TEACHER_PROFILE_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
//    public String getTVW_TEACHER_TIMETABLE_DETAIL_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
//    public String getTVW_TEACHER_TIMETABLE_MASTER_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
    
//    public String getTVW_TEACHER_ATTENDANCE_DETAIL_DataSet(String p_fileName,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;
    
//    public String getTVW_TEACHER_TIMETABLE_DETAIL_DataSet(String p_fileName,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;
}
