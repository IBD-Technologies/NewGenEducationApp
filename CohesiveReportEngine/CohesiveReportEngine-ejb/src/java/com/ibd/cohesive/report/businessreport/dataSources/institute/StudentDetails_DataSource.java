/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.institute;

import com.ibd.businessViews.IInstituteDataSetBusiness;
import com.ibd.cohesive.report.businessreport.dataSources.dataModels.institute.StudentDetails;
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
public class StudentDetails_DataSource extends InstituteDataSource<StudentDetails>{
 
    String studentStatus;
    String instanceID;
    String standard;
    String section;
    String fromDate;
    String toDate;
    
    @Override
      public List<StudentDetails> fetch()
	{
	
            try
            {
                System.out.print("inside StudentDetails datasource");
                instanceID =this.getLoginInstitute();
                studentStatus=this.getStudentStatus();
                standard=this.getStandard();
                section=this.getSection();
                fromDate=this.getFromDate();
                toDate=this.getToDate();
                
                 ArrayList<StudentDetails> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IInstituteDataSetBusiness batchDataSet=preProcessor.getInstituteDataSetBusiness();
                   
                  String result= batchDataSet.StudentDetailsDataSet(instanceID, studentStatus,standard,section,fromDate,toDate);
                  StudentDetails obj=new StudentDetails();
                 resultset= obj.convertStringToArrayList(result);
                  
                 
                   System.out.print("end of StudentDetails datasource");
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
