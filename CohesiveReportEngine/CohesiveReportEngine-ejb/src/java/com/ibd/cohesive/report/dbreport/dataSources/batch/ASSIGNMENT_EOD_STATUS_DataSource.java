/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.batch;

import com.ibd.businessViews.IBatchDataset;
import com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch.ASSIGNMENT_EOD_STATUS;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class ASSIGNMENT_EOD_STATUS_DataSource extends BatchDataSource<ASSIGNMENT_EOD_STATUS>{

    String businessDate;
    String instituteID;
    
    @Override
      public List<ASSIGNMENT_EOD_STATUS> fetch()
	{
	
            try
            {

                businessDate =this.getBusinessDate();
                instituteID=this.getLoginInstitute();
                
//                IBatchDataset batchDataSet=new ReportDependencyInjection().getBatchDataset();
//                return batchDataSet.getASSIGNMENT_EOD_STATUS_DataSet(businessDate, businessDate);  
                   ArrayList<ASSIGNMENT_EOD_STATUS> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IBatchDataset batchDataSet=preProcessor.getBatchDataset();
                   
                  String result= batchDataSet.getASSIGNMENT_EOD_STATUS_DataSet(businessDate, instituteID);
                  ASSIGNMENT_EOD_STATUS obj=new ASSIGNMENT_EOD_STATUS();
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
