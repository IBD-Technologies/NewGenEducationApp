/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.institute;

import com.ibd.businessViews.IInstituteDataSet;
import com.ibd.cohesive.report.dbreport.dataModels.institute.CLASS_LEAVE_MANAGEMENT;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ibdtech
 */
public class CLASS_LEAVE_MANAGEMENT_DataSource extends InstituteDataSource<CLASS_LEAVE_MANAGEMENT>{
 
//    String fileName;
    //String tableName;
    String instanceID;
    
    @Override
      public List<CLASS_LEAVE_MANAGEMENT> fetch()
	{
	
            try
            {
                //parseParameters();
                instanceID =this.getInstituteId();
                String standard=this.getStandard();
                String section=this.getSection();
                
                ArrayList<CLASS_LEAVE_MANAGEMENT> resultset=new ArrayList();
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IInstituteDataSet appDataSet=preProcessor.getInstituteDataset();
                   
                  String result= appDataSet.getCLASS_LEAVE_MANAGEMENT_DataSet(instanceID,standard,section);
                  
                  CLASS_LEAVE_MANAGEMENT errorMaster=new CLASS_LEAVE_MANAGEMENT();
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
