/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.batch;

import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.ATTENDANCE_BATCH_STATUS_ERROR;
import com.ibd.businessViews.IBatchDataset;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class ATTENDANCE_BATCH_STATUS_ERROR_DataSource extends BatchDataSource<ATTENDANCE_BATCH_STATUS_ERROR>{

    String businessDate;
    
    @Override
      public List<ATTENDANCE_BATCH_STATUS_ERROR> fetch()
	{
	
            try
            {

                businessDate =this.getBusinessDate();
                
                IBatchDataset batchDataSet=new ReportDependencyInjection().getBatchDataset();
                return batchDataSet.getATTENDANCE_BATCH_STATUS_ERROR_DataSet(businessDate);  
        
            
            
       } catch(NamingException ex){
           return null;
       } catch(Exception ex){
           return null;
              
    }
        }
}
