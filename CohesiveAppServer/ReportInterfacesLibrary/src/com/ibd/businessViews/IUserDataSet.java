/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_CLASS_ENTITY_ROLEMAPPING;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_INSTITUTE_ENTITY_ROLEMAPPING;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_IN_LOG;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_OUT_LOG;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_PARENT_STUDENT_ROLEMAPPING;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_TEACHER_ENTITY_ROLEMAPPING;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_USER_CONTRACT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_USER_CREDENTIALS;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_USER_PROFILE;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_USER_ROLE_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_USER_ROLE_MASTER;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author IBD Technologies
 */
@Remote
public interface IUserDataSet {
     public String getUVW_INSTITUTE_ENTITY_ROLEMAPPING_DataSet(String p_userID)throws DBProcessingException,DBValidationException;
     
     public String getUVW_IN_LOG_DataSet(String p_userID)throws DBProcessingException,DBValidationException;
     
     public String getUVW_OUT_LOG_DataSet(String p_userID)throws DBProcessingException,DBValidationException;
     
     public String getUVW_PARENT_STUDENT_ROLEMAPPING_DataSet(String p_userID)throws DBProcessingException,DBValidationException;
     
     public String getUVW_TEACHER_ENTITY_ROLEMAPPING_DataSet(String p_userID)throws DBProcessingException,DBValidationException;
     
     public String getUVW_USER_CREDENTIALS_DataSet(String p_userID)throws DBProcessingException,DBValidationException;
     
     public String getUVW_USER_PROFILE_DataSet(String p_userID)throws DBProcessingException,DBValidationException;
     
     public String getUVW_USER_ROLE_DETAIL_DataSet(String p_userID)throws DBProcessingException,DBValidationException;
     
     public String getUVW_USER_ROLE_MASTER_DataSet(String p_userID)throws DBProcessingException,DBValidationException;
     
     public String getUVW_CLASS_ENTITY_ROLEMAPPING_DataSet(String p_userID)throws DBProcessingException,DBValidationException;
     
     public String getUVW_USER_CONTRACT_MASTER_DataSet(String p_userID)throws DBProcessingException,DBValidationException;
     
      public String getUVW_USER_ROLE_INSTITUTE_DataSet(String p_userID)throws DBProcessingException,DBValidationException;
     
}
