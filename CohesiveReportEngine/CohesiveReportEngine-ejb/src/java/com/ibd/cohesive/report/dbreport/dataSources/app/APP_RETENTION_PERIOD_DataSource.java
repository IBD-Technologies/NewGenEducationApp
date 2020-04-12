/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.app;

import com.ibd.businessViews.IAppDataSet;
import com.ibd.cohesive.report.dbreport.dataModels.app.APP_RETENTION_PERIOD;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ibdtech
 */
public class APP_RETENTION_PERIOD_DataSource extends AppDataSource<APP_RETENTION_PERIOD>{

    String instanceID;
    
    
    @Override
      public List<APP_RETENTION_PERIOD> fetch()
	{
	
            try
            {
                //parseParameters();
                //parseParameters();
                instanceID =this.getInstituteId();
                ArrayList<APP_RETENTION_PERIOD> resultset=new ArrayList();
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IAppDataSet appDataSet=preProcessor.getAppDataset();
                   
                  String result= appDataSet.getAPP_RETENTION_PERIOD_DataSet(instanceID);
                  
                  APP_RETENTION_PERIOD errorMaster=new APP_RETENTION_PERIOD();
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
