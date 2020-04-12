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
 * @author DELL
 */
@Remote
public interface IStudentDataSetBusiness {
     public String getStudentAttendanceSummary_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
      public String getStudentFeeDetails_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
      public String getStudentFeeDetails_DataSet(String p_fileName,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;
      public String getSVW_FAMILY_DETAILS_BUSINESS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
      public String getSVW_STUDENT_MARKS_BUSINESS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
      public String getSVW_STUDENT_PROFILE_BUSINESS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
      public String getSVW_STUDENT_TIMETABLE_DETAIL_BUSINESS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
      public String getSVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException; 
      public String getStudentRank_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
       public String getStudentOtherActivityDetail_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
public String getStudentOtherActivityDetail_DataSet(String p_fileName,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;
public String getSVW_STUDENT_SOFT_SKILLS_BUSINESS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException;
}
