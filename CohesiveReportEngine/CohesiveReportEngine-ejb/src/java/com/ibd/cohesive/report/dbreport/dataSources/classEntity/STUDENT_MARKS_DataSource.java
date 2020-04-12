/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.classEntity;

import com.ibd.cohesive.report.dbreport.dataModels.classEntity.STUDENT_MARKS;
import com.ibd.businessViews.IClassDataSet;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.STUDENT_MARKS;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
public class STUDENT_MARKS_DataSource extends ClassDataSource<STUDENT_MARKS>{
 
    String standard;
    String instanceID;
    String section;
    
    
    @Override
      public List<STUDENT_MARKS> fetch()
	{
	
            try
            {
               standard = this.getStandard();
                section=this.getSection();
                instanceID =this.getInstituteID();
                
                ArrayList<STUDENT_MARKS> resultset=new ArrayList();
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IClassDataSet appDataSet=preProcessor.getClassDataset();
                   
                  String result= appDataSet.getSTUDENT_MARKS_DataSet(standard,section,instanceID,this.getExam());
                  
                  STUDENT_MARKS errorMaster=new STUDENT_MARKS();
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
