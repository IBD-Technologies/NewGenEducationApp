/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.batch;

import com.ibd.businessViews.IBatchDataset;
import com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch.STUDENT_NOTIFICATION_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class STUDENT_NOTIFICATION_EOD_STATUS_ERROR_DataSource extends BatchDataSource<STUDENT_NOTIFICATION_EOD_STATUS_ERROR>{

    String businessDate;
    String instituteID;
    
    @Override
      public List<STUDENT_NOTIFICATION_EOD_STATUS_ERROR> fetch()
	{
	
            try
            {

                businessDate =this.getBusinessDate();
                instituteID=this.getLoginInstitute();
                
//                IBatchDataset batchDataSet=new ReportDependencyInjection().getBatchDataset();
//                return batchDataSet.getSTUDENT_NOTIFICATION_EOD_STATUS_ERROR_DataSet(businessDate);  
        
                ArrayList<STUDENT_NOTIFICATION_EOD_STATUS_ERROR> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IBatchDataset batchDataSet=preProcessor.getBatchDataset();
                   
                  String result= batchDataSet.getSTUDENT_NOTIFICATION_EOD_STATUS_ERROR_DataSet(businessDate,instituteID);
                  STUDENT_NOTIFICATION_EOD_STATUS_ERROR obj = new STUDENT_NOTIFICATION_EOD_STATUS_ERROR();
                  resultset= obj.convertStringToArrayList(result);
                  
 
                   return resultset;
                }else{
                    return null;
                }
            
       } catch(NamingException ex){
           return null;
       } catch(Exception ex){
           return null;
              
    }
        }
      
      
      
      
}
