/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.app;

import com.ibd.businessViews.IAppDataSet;
import com.ibd.cohesive.report.dbreport.dataModels.app.ARCH_SHIPPING_STATUS;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ibdtech
 */
public class ARCH_SHIPPING_STATUS_DataSource extends AppDataSource<ARCH_SHIPPING_STATUS>{

    String instanceID;
    String currdate;
    
    
    @Override
      public List<ARCH_SHIPPING_STATUS> fetch()
	{
	
            try
            {
                //parseParameters();
                //parseParameters();
                instanceID =this.getInstituteId();
                currdate=this.getCurrdate();
                ArrayList<ARCH_SHIPPING_STATUS> resultset=new ArrayList();
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IAppDataSet appDataSet=preProcessor.getAppDataset();
                   
                  String result= appDataSet.getARCH_SHIPPING_STATUS_DataSet(instanceID,currdate);
                  
                  ARCH_SHIPPING_STATUS errorMaster=new ARCH_SHIPPING_STATUS();
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
