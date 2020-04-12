/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.student;

import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_OTHER_ACTIVITY;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_PAYMENT;
import com.ibd.businessViews.IStudentDataSet;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_PAYMENT;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
public class SVW_STUDENT_PAYMENT_DataSource extends StudentDataSource<SVW_STUDENT_PAYMENT>{
      String fileName;
    String instanceID;
    
    @Override
      public List<SVW_STUDENT_PAYMENT> fetch()
	{
	
            try
            {
                //parseParameters();
                fileName = this.getStudentId();
                instanceID =this.getInstituteID();
                
                ArrayList<SVW_STUDENT_PAYMENT> resultset=new ArrayList();
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IStudentDataSet appDataSet=preProcessor.getStudentDataset();
                   
                  String result= appDataSet.getSVW_STUDENT_PAYMENT_DataSet(fileName, instanceID);
                  
                  SVW_STUDENT_PAYMENT errorMaster=new SVW_STUDENT_PAYMENT();
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
