/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.batch;

import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY;
import com.ibd.businessViews.IBatchDataset;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DataSource extends BatchDataSource<INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY>{

    String businessDate;
    
    @Override
      public List<INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY> fetch()
	{
	
            try
            {

                businessDate =this.getBusinessDate();
                
                System.out.print("business date inside INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DataSource"+businessDate);
                
                IBatchDataset batchDataSet=new ReportDependencyInjection().getBatchDataset();
                
                System.out.print("After gettting batch data set"+businessDate);
                
                return batchDataSet.getINSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DataSet(businessDate);  
        
            
            
       } catch(NamingException ex){
           return null;
       } catch(Exception ex){
           return null;
              
    }
        }     
}
