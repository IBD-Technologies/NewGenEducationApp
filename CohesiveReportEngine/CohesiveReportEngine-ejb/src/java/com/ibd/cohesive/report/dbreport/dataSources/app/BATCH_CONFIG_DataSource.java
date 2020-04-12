/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.app;

import com.ibd.businessViews.IAppDataSet;
import com.ibd.cohesive.report.dbreport.dataModels.app.BATCH_CONFIG;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ibdtech
 */
public class BATCH_CONFIG_DataSource extends AppDataSource<BATCH_CONFIG>{

    String instanceID;
    String businessDate;
    
    
    @Override
      public List<BATCH_CONFIG> fetch()
	{
	
            try
            {
                //parseParameters();
                //parseParameters();
                instanceID =this.getInstituteId();
                ArrayList<BATCH_CONFIG> resultset=new ArrayList();
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IAppDataSet appDataSet=preProcessor.getAppDataset();
                   
                  String result= appDataSet.getBATCH_CONFIG_DataSet(instanceID,businessDate);
                  
                  BATCH_CONFIG errorMaster=new BATCH_CONFIG();
                 resultset= errorMaster.convertStringToArrayList(result);
                  
 
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
