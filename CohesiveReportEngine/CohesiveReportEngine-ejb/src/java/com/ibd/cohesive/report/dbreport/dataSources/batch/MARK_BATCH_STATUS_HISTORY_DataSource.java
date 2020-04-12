/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.batch;

import com.ibd.businessViews.IBatchDataset;
import com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch.MARK_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class MARK_BATCH_STATUS_HISTORY_DataSource extends BatchDataSource<MARK_BATCH_STATUS_HISTORY>{

    String businessDate;
    String instituteID;
    
    @Override
      public List<MARK_BATCH_STATUS_HISTORY> fetch()
	{
	
            try
            {

                businessDate =this.getBusinessDate();
                instituteID=this.getLoginInstitute();
                
                List<MARK_BATCH_STATUS_HISTORY> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IBatchDataset batchDataSet=preProcessor.getBatchDataset();
                   
                  String result= batchDataSet.getMARK_BATCH_STATUS_HISTORY_DataSet(businessDate,instituteID);
                  MARK_BATCH_STATUS_HISTORY obj=new MARK_BATCH_STATUS_HISTORY();
                  resultset= obj.convertStringToArrayList(result);
                  
 
                    System.out.println("Returning resulset in mark batch history");
                   return resultset;
                   
                }else{
                    System.out.println("Returning null in mark batch history");
                    return null;
                }
        
            
            
       } catch(NamingException ex){
           System.out.println("Error in Mark Batch history"+ex.toString());
           return null;
       } catch(Exception ex){
          System.out.println("Error in Mark Batch history"+ex.toString());
           return null;
              
    }
        }
}
