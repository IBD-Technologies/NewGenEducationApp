/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.classEntity;

import com.ibd.businessViews.IClassDataSetBusiness;
import com.ibd.cohesive.report.businessreport.datSources.dataModels.classEntity.ClassAttendanceSummary;
import com.ibd.cohesive.report.businessreport.datSources.dataModels.classEntity.ClassAttendanceSummary;
import com.ibd.cohesive.report.dbreport.dataSources.classEntity.ClassDataSource;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class ClassAttendanceSummary_DataSource extends ClassDataSource<ClassAttendanceSummary>{
 
    String standard;
    String instanceID;
    String section;
    
    
    @Override
      public List<ClassAttendanceSummary> fetch()
	{
	
            try
            {
                standard = this.getStandard();
                section=this.getSection();
                instanceID =this.getLoginInstitute();
                
                 ArrayList<ClassAttendanceSummary> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IClassDataSetBusiness batchDataSet=preProcessor.getClassDataSetBusiness();
                   
                  String result= batchDataSet.getClassAttendanceSummary_DataSet(standard, section, instanceID);
                  ClassAttendanceSummary obj=new ClassAttendanceSummary();
                 resultset= obj.convertStringToArrayList(result);
                  
                 
 
                   return resultset;
                }else{
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
