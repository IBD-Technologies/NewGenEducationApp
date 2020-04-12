/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.batch;

import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.TIMETABLE_BATCH_STATUS;
import com.ibd.businessViews.IBatchDataset;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class TIMETABLE_BATCH_STATUS_DataSource extends BatchDataSource<TIMETABLE_BATCH_STATUS>{

    String businessDate;
    
    @Override
      public List<TIMETABLE_BATCH_STATUS> fetch()
	{
	
            try
            {

                businessDate =this.getBusinessDate();
                
                IBatchDataset batchDataSet=new ReportDependencyInjection().getBatchDataset();
                return batchDataSet.getTIMETABLE_BATCH_STATUS_DataSet(businessDate);  
        
            
            
       } catch(NamingException ex){
           return null;
       } catch(Exception ex){
           return null;
              
    }
        }
}
