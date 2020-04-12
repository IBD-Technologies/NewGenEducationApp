/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.batch;

import com.ibd.businessViews.IBatchDataset;
import com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch.E_CIRCULAR_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ibdtech
 */
public class E_CIRCULAR_EOD_STATUS_HISTORY_DataSource extends BatchDataSource<E_CIRCULAR_EOD_STATUS_HISTORY>{

    String businessDate;
    String instituteID;
    
    @Override
      public List<E_CIRCULAR_EOD_STATUS_HISTORY> fetch()
	{
	
            try
            {

                businessDate =this.getBusinessDate();
                 instituteID=this.getLoginInstitute();
                
//                IBatchDataset batchDataSet=new ReportDependencyInjection().getBatchDataset();
//                return batchDataSet.getE_CIRCULAR_EOD_STATUS_HISTORY_DataSet(businessDate);  
                  ArrayList<E_CIRCULAR_EOD_STATUS_HISTORY> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IBatchDataset batchDataSet=preProcessor.getBatchDataset();
                   
                  String result= batchDataSet.getE_CIRCULAR_EOD_STATUS_HISTORY_DataSet(businessDate, instituteID);
                  E_CIRCULAR_EOD_STATUS_HISTORY obj=new E_CIRCULAR_EOD_STATUS_HISTORY();
                 resultset= obj.convertStringToArrayList(result);
                 
                 
                 return resultset;
                }else{
                    return null;
                }
            
//            return null;
       } catch(NamingException ex){
           return null;
       } catch(Exception ex){
           return null;
              
    }
        }     
}
