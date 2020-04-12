/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;

/**
 *
 * @author DELL
 */
public interface IInstituteDataSetBusiness {
    public String MarkComparison_DataSet(String p_instanceID,String standard,String userID)throws DBProcessingException,DBValidationException;
    public String CLASS_MARK_REPORT_DataSet(String p_instanceID,String standard,String userID)throws DBProcessingException,DBValidationException;
public String getFeeSummaryBusinessDataSet(String p_standard,String p_section,String p_studentID,String p_feeID,String p_instanceID)throws DBProcessingException,DBValidationException;
//public String getFeePaymentBusinessDataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException;
public String FeeDetailBusinessDataSet(String standard,String section,String studentID,String feeID,String p_instanceID)throws DBProcessingException,DBValidationException;
public String getFeePaymentBusinessDataSet(String p_standard,String p_section,String studentID,String feeID,String fromDate,String toDate,String p_instanceID)throws DBProcessingException,DBValidationException;
public String NotificationDetailBusinessDataSet(String p_instanceID,String standard,String section,String studentID,String fromDate,String toDate,String notificationType)throws DBProcessingException,DBValidationException;
public String StudentDetailsDataSet(String p_instanceID,String status,String standard,String section,String fromDate,String toDate)throws DBProcessingException,DBValidationException;
 public String BusinessReportParamsDataSet(String studentID,String standard,String section,String fromDate,String toDate,String feeID,String notificationType,String studentStatus,String instituteID)throws DBProcessingException,DBValidationException;
}
