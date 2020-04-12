/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_ASSIGNMENT;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_ATTENDANCE_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_ATTENDANCE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_EXAM_RANK;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_EXAM_SCHEDULE_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_EXAM_SCHEDULE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_FEE_MANAGEMENT;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_FEE_STATUS_REPORT;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_GRADE_REPORT;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_MARK_ENTRY;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_MARK_REPORT;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_OTHER_ACTIVITY_REPORT;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_SKILL_ENTRY;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_STUDENT_MAPPING;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_TIMETABLE_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_TIMETABLE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.STUDENT_MARKS;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.STUDENT_SKILLS;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import javax.ejb.Local;
import javax.ejb.Remote;

/**
 *
 * @author IBD Technologies
 */
@Remote
public interface IClassDataSet {
    
    public String getCLASS_ASSIGNMENT_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getCLASS_ATTENDANCE_DETAIL_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getCLASS_ATTENDANCE_DETAIL_DataSet(String p_standard,String p_section,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;
    
    public String getCLASS_ATTENDANCE_MASTER_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
      
    public String getCLASS_EXAM_SCHEDULE_DETAIL_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getCLASS_EXAM_SCHEDULE_MASTER_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getCLASS_FEE_MANAGEMENT_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getCLASS_MARK_ENTRY_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
          
    public String getCLASS_STUDENT_MAPPING_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getCLASS_STUDENT_MAPPING_DataSet(String p_standard,String p_section,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;
     
    public String getCLASS_TIMETABLE_DETAIL_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getCLASS_TIMETABLE_MASTER_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getSTUDENT_MARKS_DataSet(String p_standard,String p_section,String p_instanceID,String p_exam)throws DBProcessingException,DBValidationException;
    
    public String getSTUDENT_MARKS_DataSet(String p_standard,String p_section,String p_instanceID,String p_exam,CohesiveSession session)throws DBProcessingException,DBValidationException;
    
    public String getCLASS_TIMETABLE_DETAIL_DataSet(String p_standard,String p_section,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;
    
    public String getCLASS_EXAM_RANK_DataSet(String p_standard,String p_section,String p_instanceID,String p_exam)throws DBProcessingException,DBValidationException;
    
//    public String getCLASS_EXAM_RANK_DataSet(String p_standard,String p_section,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;
    public String getCLASS_FEE_AMOUNT_REPORT_DataSet(String p_standard,String p_section,String p_instanceID,String p_userID)throws DBProcessingException,DBValidationException;
    
     public String getCLASS_ATTENDANCE_REPORT_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
     
     public String getSTUDENT_SKILLS_DataSet(String p_standard,String p_section,String p_instanceID,String p_exam)throws DBProcessingException,DBValidationException;
     
    public String getCLASS_OTHER_ACTIVITY_REPORT_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getCLASS_SKILL_ENTRY_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    
    public String getCLASS_GRADE_REPORT_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
    
    public String getCLASS_FEE_STATUS_REPORT_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
    
  
    
     
     
     
     
     
     
     
}
