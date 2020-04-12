/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.institute;

import com.ibd.businessViews.IInstituteDataSetBusiness;
import com.ibd.cohesive.report.businessreport.dataSources.dataModels.institute.MarkComparison;
import com.ibd.cohesive.report.dbreport.dataSources.institute.InstituteDataSource;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class MarkComparison_DataSource extends  InstituteDataSource<MarkComparison>{
 
    String standard;
    String instanceID;
    String userID;
    
    
    @Override
      public List<MarkComparison> fetch()
	{
	
            try
            {
                System.out.print("inside MarkComparison datasource");
                instanceID =this.getInstituteId();
                standard=this.getStandard();
                userID=this.getUserID();
                
                
                 ArrayList<MarkComparison> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IInstituteDataSetBusiness batchDataSet=preProcessor.getInstituteDataSetBusiness();
                   
                  String result= batchDataSet.MarkComparison_DataSet(instanceID,standard,userID);
                  MarkComparison obj=new MarkComparison();
                 resultset= obj.convertStringToArrayList(result);
                  
                 
                   System.out.print("end of MarkComparison datasource");
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
