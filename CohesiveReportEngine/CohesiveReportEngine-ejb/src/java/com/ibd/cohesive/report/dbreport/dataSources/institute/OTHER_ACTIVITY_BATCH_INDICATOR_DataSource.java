/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.institute;

import com.ibd.businessViews.IInstituteDataSet;
import com.ibd.cohesive.report.dbreport.dataModels.institute.OTHER_ACTIVITY_BATCH_INDICATOR;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ibdtech
 */
public class OTHER_ACTIVITY_BATCH_INDICATOR_DataSource extends InstituteDataSource<OTHER_ACTIVITY_BATCH_INDICATOR>{
 
//    String fileName;
    //String tableName;
    String instanceID;
    
    @Override
      public List<OTHER_ACTIVITY_BATCH_INDICATOR> fetch()
	{
	
            try
            {
                //parseParameters();
                instanceID =this.getInstituteId();
                
                ArrayList<OTHER_ACTIVITY_BATCH_INDICATOR> resultset=new ArrayList();
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IInstituteDataSet appDataSet=preProcessor.getInstituteDataset();
                   
                  String result= appDataSet.getOTHER_ACTIVITY_BATCH_INDICATOR_DataSet(instanceID);
                  
                  OTHER_ACTIVITY_BATCH_INDICATOR errorMaster=new OTHER_ACTIVITY_BATCH_INDICATOR();
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
