/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.classEntity;

import com.ibd.businessViews.IClassDataSet;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_GRADE_REPORT;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ibdtech
 */
public class CLASS_GRADE_REPORT_DataSource extends ClassDataSource<CLASS_GRADE_REPORT>{
 
    String standard;
    String instanceID;
    String section;
    
    
    @Override
      public List<CLASS_GRADE_REPORT> fetch()
	{
	
            try
            {
                standard = this.getStandard();
                section=this.getSection();
                instanceID =this.getInstituteID();
                
                ArrayList<CLASS_GRADE_REPORT> resultset=new ArrayList();
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IClassDataSet appDataSet=preProcessor.getClassDataset();
                   
                  String result= appDataSet.getCLASS_GRADE_REPORT_DataSet(standard,section,instanceID);
                  
                  CLASS_GRADE_REPORT errorMaster=new CLASS_GRADE_REPORT();
                 resultset= errorMaster.convertStringToArrayList(result);  
                }
            
            return resultset;
            
            
       } catch(NamingException ex){
           return null;
       } catch(Exception ex){
           return null;
              
    }
        }    
    
    
    
    
}
