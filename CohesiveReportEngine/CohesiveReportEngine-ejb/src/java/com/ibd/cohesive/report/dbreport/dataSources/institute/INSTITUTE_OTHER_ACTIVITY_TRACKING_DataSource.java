/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.institute;

import com.ibd.businessViews.IInstituteDataSet;
import com.ibd.cohesive.report.dbreport.dataModels.institute.INSTITUTE_OTHER_ACTIVITY_TRACKING;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ibdtech
 */
public class INSTITUTE_OTHER_ACTIVITY_TRACKING_DataSource extends InstituteDataSource<INSTITUTE_OTHER_ACTIVITY_TRACKING>{
 
//    String fileName;
    //String tableName;
    String instanceID;
    
    @Override
      public List<INSTITUTE_OTHER_ACTIVITY_TRACKING> fetch()
	{
	
            try
            {
                //parseParameters();
                instanceID =this.getInstituteId();
                String activityID=this.getActivityID();
                
                ArrayList<INSTITUTE_OTHER_ACTIVITY_TRACKING> resultset=new ArrayList();
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IInstituteDataSet appDataSet=preProcessor.getInstituteDataset();
                   
                  String result= appDataSet.getINSTITUTE_OTHER_ACTIVITY_TRACKING_DataSet(instanceID,activityID);
                  
                  INSTITUTE_OTHER_ACTIVITY_TRACKING errorMaster=new INSTITUTE_OTHER_ACTIVITY_TRACKING();
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
