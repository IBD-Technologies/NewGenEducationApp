/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.report.dbreport.dataModels.app.ERROR_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.app.INSTITUTE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.app.SERVICE_TYPE_MASTER;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author IBD Technologies
 */
@Remote
public interface IAppDataSet {
    public String getERROR_MASTER_DataSet(String p_instituteID)throws DBProcessingException,DBValidationException;
    
    public String getINSTITUTE_MASTER_DataSet(String p_instituteID,String businessReport)throws DBProcessingException,DBValidationException;
    
    public String getSERVICE_TYPE_MASTER_DataSet(String p_instituteID)throws DBProcessingException,DBValidationException;
    
    public String getAPP_RETENTION_PERIOD_DataSet(String p_instituteID)throws DBProcessingException,DBValidationException;
    
    public String getAPP_SUPPORT_DataSet(String p_instituteID)throws DBProcessingException,DBValidationException;
    
    public String getARCH_APPLY_STATUS_DataSet(String p_instituteID,String p_date)throws DBProcessingException,DBValidationException;
    
     public String getARCH_SHIPPING_STATUS_DataSet(String p_instituteID,String p_date)throws DBProcessingException,DBValidationException;
     
     public String getBATCH_CONFIG_DataSet(String p_instituteID,String p_businessDate)throws DBProcessingException,DBValidationException;
     
     public String getBATCH_SERVICES_DataSet(String p_instituteID)throws DBProcessingException,DBValidationException;
     
     public String getCONTRACT_MASTER_DataSet(String p_instituteID)throws DBProcessingException,DBValidationException;
      
      
    
    
}