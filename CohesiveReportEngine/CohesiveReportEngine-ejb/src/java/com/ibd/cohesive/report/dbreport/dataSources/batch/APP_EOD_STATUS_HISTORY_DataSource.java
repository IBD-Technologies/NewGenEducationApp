/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.batch;

import com.ibd.businessViews.IBatchDataset;
import com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch.APP_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class APP_EOD_STATUS_HISTORY_DataSource extends BatchDataSource<APP_EOD_STATUS_HISTORY>{

    String businessDate;
    
    @Override
      public List<APP_EOD_STATUS_HISTORY> fetch()
	{
	
            try
            {

                businessDate =this.getBusinessDate();
                
//                IBatchDataset batchDataSet=new ReportDependencyInjection().getBatchDataset();
//                return batchDataSet.getAPP_EOD_STATUS_HISTORY_DataSet(businessDate);  
//        
                  ArrayList<APP_EOD_STATUS_HISTORY> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IBatchDataset batchDataSet=preProcessor.getBatchDataset();
                   
                  String result= batchDataSet.getAPP_EOD_STATUS_HISTORY_DataSet(businessDate);
                  APP_EOD_STATUS_HISTORY obj=new APP_EOD_STATUS_HISTORY();
                 resultset= obj.convertStringToArrayList(result);
                  
 
                   return resultset;
                }else{
                    return null;
                }
            
       } catch(NamingException ex){
           return null;

//throw new EJBException(ex.toString());
       } catch(Exception ex){
           return null;
//        throw new EJBException(ex.toString());      
    }
        }  
      
      
      
      
      
      
}
