/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.institute;

import com.ibd.businessViews.IInstituteDataSetBusiness;
import com.ibd.cohesive.report.businessreport.dataSources.dataModels.institute.FeeSummaryBusiness;
import com.ibd.cohesive.report.dbreport.dataSources.institute.InstituteDataSource;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ibdtech
 */
public class FeeSummaryBusiness_DataSource extends  InstituteDataSource<FeeSummaryBusiness>{
 
    String standard;
    String instanceID;
    String section;
    String studentID;
    String feeID;
    
    
    @Override
      public List<FeeSummaryBusiness> fetch()
	{
	
            try
            {
                System.out.print("inside FeeSummaryBusiness datasource");
                instanceID =this.getLoginInstitute();
                standard=this.getStandard();
                section=this.getSection();
                studentID=this.getStudentID();
                feeID=this.getFeeID();
                
                
                 ArrayList<FeeSummaryBusiness> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IInstituteDataSetBusiness batchDataSet=preProcessor.getInstituteDataSetBusiness();
                   
                  String result= batchDataSet.getFeeSummaryBusinessDataSet(standard, section, studentID, feeID, instanceID);
                  FeeSummaryBusiness obj=new FeeSummaryBusiness();
                 resultset= obj.convertStringToArrayList(result);
                  
                 
                   System.out.print("end of FeeSummaryBusiness datasource");
                   return resultset;
                }else{
                    System.out.print("returning null");
                    return null;
                }
            
            
       } catch(NamingException ex){
           ex.printStackTrace();
           return null;
//             throw new EJBException();
       } catch(Exception ex){
           ex.printStackTrace();
           return null;
              
    }
        }  
}
