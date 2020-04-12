/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.batch;

import com.ibd.businessViews.IBatchDataset;
import com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch.FEE_NOTIFICATION_EOD_STATUS;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ibdtech
 */
public class FEE_NOTIFICATION_EOD_STATUS_DataSource extends BatchDataSource<FEE_NOTIFICATION_EOD_STATUS>{

    String businessDate;
    String instituteID;
    
    @Override
      public List<FEE_NOTIFICATION_EOD_STATUS> fetch()
	{
	
            try
            {
          businessDate =this.getBusinessDate();
                instituteID=this.getLoginInstitute();
                
//                IBatchDataset batchDataSet=new ReportDependencyInjection().getBatchDataset();
//                return batchDataSet.getNOTIFICATION_EOD_STATUS_DataSet(businessDate);  
        
                List<FEE_NOTIFICATION_EOD_STATUS> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IBatchDataset batchDataSet=preProcessor.getBatchDataset();
                   
                  String result= batchDataSet.getFEE_NOTIFICATION_EOD_STATUS_DataSet(businessDate,instituteID);
                  FEE_NOTIFICATION_EOD_STATUS obj=new FEE_NOTIFICATION_EOD_STATUS();
                  resultset= obj.convertStringToArrayList(result);
                  
 
                   return resultset;
                }else{
                    return null;
                }
            
       } catch(NamingException ex){
           System.out.println(ex);
           return null;
       } catch(Exception ex){
           System.out.println(ex);
           return null;
              
    }
        }
}

    
