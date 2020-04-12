/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.institute;

import com.ibd.businessViews.IInstituteDataSetBusiness;
import com.ibd.cohesive.report.businessreport.dataSources.dataModels.institute.BusinessReportParams;
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
public class BusinessReportParams_DataSource extends InstituteDataSource<BusinessReportParams>{
 
    String standard;
    String instituteID;
    String section;
    String studentID;
    String notificationType;
    String fromDate;
    String toDate;
    String feeID;
    String studentStatus;
    
    @Override
      public List<BusinessReportParams> fetch()
	{
	
            try
            {
                System.out.print("inside BusinessReportParams datasource");
                instituteID =this.getLoginInstitute();
                standard=this.getStandard();
                section=this.getSection();
                studentID=this.getStudentID();
                notificationType=this.getNotificationType();
                fromDate=this.getFromDate();
                toDate=this.getToDate();
                feeID=this.getFeeID();
                studentStatus=this.getStudentStatus();
                
                
                 ArrayList<BusinessReportParams> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IInstituteDataSetBusiness batchDataSet=preProcessor.getInstituteDataSetBusiness();
                   
                  String result= batchDataSet.BusinessReportParamsDataSet(studentID, standard, section, fromDate, toDate, feeID, notificationType, studentStatus, instituteID);
                  BusinessReportParams obj=new BusinessReportParams();
                 resultset= obj.convertStringToArrayList(result);
                  
                 
                   System.out.print("end of BusinessReportParams datasource");
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
