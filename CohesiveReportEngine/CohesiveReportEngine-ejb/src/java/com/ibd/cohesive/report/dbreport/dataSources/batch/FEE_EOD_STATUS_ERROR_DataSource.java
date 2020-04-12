/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.batch;

import com.ibd.businessViews.IBatchDataset;
import com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch.FEE_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class FEE_EOD_STATUS_ERROR_DataSource extends BatchDataSource<FEE_EOD_STATUS_ERROR>{

    String businessDate;
    String instituteID;
    
    @Override
      public List<FEE_EOD_STATUS_ERROR> fetch()
	{
	
            try
            {

                businessDate =this.getBusinessDate();
                instituteID=this.getLoginInstitute();
                
//                IBatchDataset batchDataSet=new ReportDependencyInjection().getBatchDataset();
//                return batchDataSet.getNOTIFICATION_EOD_STATUS_DataSet(businessDate);  
        
                List<FEE_EOD_STATUS_ERROR> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IBatchDataset batchDataSet=preProcessor.getBatchDataset();
                   
                  String result= batchDataSet.getFEE_EOD_STATUS_ERROR_DataSet(businessDate,instituteID);
                  FEE_EOD_STATUS_ERROR obj=new FEE_EOD_STATUS_ERROR();
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
