/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.teacher;

import com.ibd.businessViews.ITeacherDataSet;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_CONTACT_PERSON_DETAILS;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
public class TVW_CONTACT_PERSON_DETAILS_DataSource extends TeacherDataSource<TVW_CONTACT_PERSON_DETAILS>{
      String fileName;
    String instanceID;
    
    @Override
      public List<TVW_CONTACT_PERSON_DETAILS> fetch()
	{
	
            try
            {
                fileName = this.getTeacherId();
                instanceID =this.getLoginInstitute();
                
                ArrayList<TVW_CONTACT_PERSON_DETAILS> resultset=new ArrayList();
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   ITeacherDataSet appDataSet=preProcessor.getTeacherDataset();
                   
                  String result= appDataSet.getTVW_CONTACT_PERSON_DETAILS_DataSet(fileName, instanceID);
                  
                  TVW_CONTACT_PERSON_DETAILS errorMaster=new TVW_CONTACT_PERSON_DETAILS();
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
