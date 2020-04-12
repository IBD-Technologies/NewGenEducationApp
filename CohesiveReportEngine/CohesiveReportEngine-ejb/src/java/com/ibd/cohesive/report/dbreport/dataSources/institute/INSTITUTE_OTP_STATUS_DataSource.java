/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.institute;

import com.ibd.businessViews.IInstituteDataSet;
import com.ibd.cohesive.report.dbreport.dataModels.institute.INSTITUTE_OTP_STATUS;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ibdtech
 */
public class INSTITUTE_OTP_STATUS_DataSource extends InstituteDataSource<INSTITUTE_OTP_STATUS>{
 
//    String fileName;
    //String tableName;
    String instanceID;
    String date;
    
    @Override
      public List<INSTITUTE_OTP_STATUS> fetch()
	{
	
            try
            {
                //parseParameters();
                instanceID =this.getInstituteId();
                date=this.getDate();
                
                ArrayList<INSTITUTE_OTP_STATUS> resultset=new ArrayList();
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IInstituteDataSet appDataSet=preProcessor.getInstituteDataset();
                   
                  String result= appDataSet.getINSTITUTE_OTP_STATUS_DataSet(instanceID,this.getDate());
                  
                  INSTITUTE_OTP_STATUS errorMaster=new INSTITUTE_OTP_STATUS();
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
