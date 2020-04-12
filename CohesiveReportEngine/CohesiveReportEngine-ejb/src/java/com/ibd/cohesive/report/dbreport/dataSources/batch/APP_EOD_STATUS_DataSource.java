/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.batch;

import com.ibd.businessViews.IBatchDataset;
import com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch.APP_EOD_STATUS;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class APP_EOD_STATUS_DataSource extends BatchDataSource<APP_EOD_STATUS>{

    String businessDate;
    
    @Override
      public List<APP_EOD_STATUS> fetch()
	{
	
            try
            {

                businessDate =this.getBusinessDate();
                
////                System.out.print("business date inside APP_EOD_STATUS_DataSource"+businessDate);
//                
//                IBatchDataset batchDataSet=new ReportDependencyInjection().getBatchDataset();
//                
////                System.out.print("After gettting batch data set"+businessDate);
                

                ArrayList<APP_EOD_STATUS> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IBatchDataset batchDataSet=preProcessor.getBatchDataset();
                   
                  String result= batchDataSet.getAPP_EOD_STATUS_DataSet(businessDate);
                  APP_EOD_STATUS obj=new APP_EOD_STATUS();
                 resultset= obj.convertStringToArrayList(result);
                  
                 
 
                   return resultset;
                }else{
                    return null;
                }







 


//                return batchDataSet.getAPP_EOD_STATUS_DataSet(businessDate);  
        
            
            
       } catch(NamingException ex){
           return null;
//          throw new EJBException(ex.toString());

       } catch(Exception ex){
           return null;
//          throw new EJBException(ex.toString());
    }
        }  
      
      
      
      
      
}
