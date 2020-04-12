/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.batch;

import com.ibd.businessViews.IBatchDataset;
import com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch.BATCH_STATUS;
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
public class BATCH_STATUS_DataSource extends BatchDataSource<BATCH_STATUS>{

    String businessDate;
    String instituteID;
    String batchName;
    
    @Override
      public List<BATCH_STATUS> fetch()
	{
	
            try
            {

                businessDate =this.getBusinessDate();
                instituteID=this.getLoginInstitute();
                batchName=this.getBatchName();
                
               ArrayList<BATCH_STATUS> resultset=null;
               ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IBatchDataset batchDataSet=preProcessor.getBatchDataset();
                   
                  String result= batchDataSet.getBATCH_STATUS_DataSet(businessDate,instituteID,batchName);
                  BATCH_STATUS obj=new BATCH_STATUS();
                  resultset= obj.convertStringToArrayList(result);
                  
 
                   return resultset;
                }else{
                    return null;
                }
        
            
            
       } catch(NamingException ex){
           return null;
//            throw new EJBException(ex.toString());
       } catch(Exception ex){
           return null;
//           throw new EJBException(ex.toString());
              
    }
        }     
      
      
      
}
