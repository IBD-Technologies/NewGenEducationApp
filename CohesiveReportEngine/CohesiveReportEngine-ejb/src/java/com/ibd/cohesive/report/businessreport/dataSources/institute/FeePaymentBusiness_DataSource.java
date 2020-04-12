/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.institute;

import com.ibd.businessViews.IInstituteDataSetBusiness;
import com.ibd.cohesive.report.businessreport.dataSources.dataModels.institute.FeePaymentBusiness;
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
public class FeePaymentBusiness_DataSource extends  InstituteDataSource<FeePaymentBusiness>{
 
    String standard;
    String section;
    String studentID;
    String feeID;
    String fromDate;
    String toDate;
    String instanceID;
    
    
    @Override
      public List<FeePaymentBusiness> fetch()
	{
	
            try
            {
                System.out.print("inside FeePaymentBusiness datasource");
                instanceID =this.getLoginInstitute();
                standard=this.getStandard();
                section=this.getSection();
                studentID=this.getStudentID();
                feeID=this.getFeeID();
                fromDate=this.getFromDate();
                toDate=this.getToDate();
                
                
                 ArrayList<FeePaymentBusiness> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IInstituteDataSetBusiness batchDataSet=preProcessor.getInstituteDataSetBusiness();
                   
                  String result= batchDataSet.getFeePaymentBusinessDataSet(standard, section, studentID, feeID, fromDate, toDate, instanceID);
                  FeePaymentBusiness obj=new FeePaymentBusiness();
                 resultset= obj.convertStringToArrayList(result);
                  
                 
                   System.out.print("end of FeePaymentBusiness datasource");
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
