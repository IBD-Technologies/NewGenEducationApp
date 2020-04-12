/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.classEntity;

import com.ibd.businessViews.IClassDataSetBusiness;
import com.ibd.cohesive.report.businessreport.datSources.dataModels.classEntity.ClassFeeStatusSummary;
import com.ibd.cohesive.report.dbreport.dataSources.classEntity.ClassDataSource;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class ClassFeeStatusSummary_DataSource extends ClassDataSource<ClassFeeStatusSummary>{
 
    String standard;
    String instanceID;
    String section;
    
    
    @Override
      public List<ClassFeeStatusSummary> fetch()
	{
	
            try
            {
                standard = this.getStandard();
                section=this.getSection();
                instanceID =this.getLoginInstitute();
                
                IClassDataSetBusiness classDataSetbusiness= new ReportDependencyInjection().getClassDataSetBusiness();
//                return classDataSetbusiness.getClassFeeStatusSummary_DataSet(standard,section,instanceID);  
        return null;
            
            
       } catch(NamingException ex){
           return null;
       } catch(Exception ex){
           return null;
              
    }
        }    
}
