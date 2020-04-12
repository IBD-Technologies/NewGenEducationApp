/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.batch;

import com.ibd.businessViews.IBatchDataset;
import com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch.INSTITUTE_EOD_STATUS;
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
public class INSTITUTE_EOD_STATUS_DataSource extends BatchDataSource<INSTITUTE_EOD_STATUS>{

    String businessDate;
    
    @Override
      public List<INSTITUTE_EOD_STATUS> fetch()
	{
	
            try
            {

                businessDate =this.getBusinessDate();
                
//                IBatchDataset batchDataSet=new ReportDependencyInjection().getBatchDataset();
//                return batchDataSet.getINSTITUTE_EOD_STATUS_DataSet(businessDate);  
         
                ArrayList<INSTITUTE_EOD_STATUS> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IBatchDataset batchDataSet=preProcessor.getBatchDataset();
                   
                  String result= batchDataSet.getINSTITUTE_EOD_STATUS_DataSet(businessDate);
                  INSTITUTE_EOD_STATUS obj=new INSTITUTE_EOD_STATUS();
                 resultset= obj.convertStringToArrayList(result);
                  
 
                   return resultset;
                }else{
                    return null;
                }
            
       } catch(NamingException ex){
           return null;
//          throw new EJBException(ex.toString());
       } catch(Exception ex){
           return null;
//              throw new EJBException(ex.toString());
    }
        }     
      
      
      
      
}
