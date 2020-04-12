/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassAttendanceDetail;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassAttendanceSummary;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassFeeAmountSummary;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassFeeDetail;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassFeeStatusSummary;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassMarksDetail;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassMarksSummary;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassOtherActivityDetail;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassOtherActivitySummary;
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

public interface IClassDataSetBusiness {
    public String getClassOtherActivityDetail_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
//    public String getClassOtherActivityDetail_DataSet(String p_standard,String p_section,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;
    public String getClassAttendanceDetail_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
//    public String getClassAttendanceDetail_DataSet(String p_standard,String p_section,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;
    public String getClassOtherActivitySummary_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
    public String getClassAttendanceSummary_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
//    public String getClassMarksDetail(String p_standard,String p_section,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;
    public String getClassMarksDetail(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
    public String ClassMarksSummary_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
    public String getClassFeeDetail_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
//    public String getClassFeeDetail_DataSet(String p_standard,String p_section,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException;
    public String getClassFeeAmountSummary_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
    public String getClassDetails_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
}
