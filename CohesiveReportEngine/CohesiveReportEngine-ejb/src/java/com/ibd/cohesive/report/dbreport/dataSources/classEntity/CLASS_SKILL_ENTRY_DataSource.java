/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.classEntity;

import com.ibd.businessViews.IClassDataSet;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_SKILL_ENTRY;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ibdtech
 */
public class CLASS_SKILL_ENTRY_DataSource extends ClassDataSource<CLASS_SKILL_ENTRY>{
 
    String standard;
    String instanceID;
    String section;
    
    
    @Override
      public List<CLASS_SKILL_ENTRY> fetch()
	{
	
            try
            {
                standard = this.getStandard();
                section=this.getSection();
                instanceID =this.getInstituteID();
                
                ArrayList<CLASS_SKILL_ENTRY> resultset=new ArrayList();
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IClassDataSet appDataSet=preProcessor.getClassDataset();
                   
                  String result= appDataSet.getCLASS_SKILL_ENTRY_DataSet(standard,section,instanceID);
                  
                  CLASS_SKILL_ENTRY errorMaster=new CLASS_SKILL_ENTRY();
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
