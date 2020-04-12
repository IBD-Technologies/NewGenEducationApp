/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.institute;

import com.ibd.businessViews.IInstituteDataSet;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_SOFT_SKILL_CONFIGURATION_MASTER;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ibdtech
 */
public class IVW_SOFT_SKILL_CONFIGURATION_MASTER_DataSource extends InstituteDataSource<IVW_SOFT_SKILL_CONFIGURATION_MASTER>{
 
//    String fileName;
    //String tableName;
    String instanceID;
    
    @Override
      public List<IVW_SOFT_SKILL_CONFIGURATION_MASTER> fetch()
	{
	
            try
            {
                //parseParameters();
                instanceID =this.getInstituteId();
                
                ArrayList<IVW_SOFT_SKILL_CONFIGURATION_MASTER> resultset=new ArrayList();
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IInstituteDataSet appDataSet=preProcessor.getInstituteDataset();
                   
                  String result= appDataSet.getIVW_SOFT_SKILL_CONFIGURATION_MASTER_DataSet(instanceID);
                  
                  IVW_SOFT_SKILL_CONFIGURATION_MASTER errorMaster=new IVW_SOFT_SKILL_CONFIGURATION_MASTER();
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