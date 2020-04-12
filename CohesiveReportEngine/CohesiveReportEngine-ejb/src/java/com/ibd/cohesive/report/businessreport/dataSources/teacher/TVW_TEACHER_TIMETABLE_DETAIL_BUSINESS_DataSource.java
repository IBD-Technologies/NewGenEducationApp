/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.teacher;

import com.ibd.businessViews.ITeacherDataSetBusiness;
import com.ibd.cohesive.report.businessreport.dataSources.dataModels.teacher.TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS;
import com.ibd.cohesive.report.dbreport.dataSources.teacher.TeacherDataSource;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DataSource extends TeacherDataSource<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS>{
     String fileName;
    String instanceID;
    
    @Override
      public List<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS> fetch()
	{
	
            try
            {
                //parseParameters();
                fileName = this.getTeacherId();
                instanceID =this.getLoginInstitute();
//                
//                IStudentDataSetBusiness studentDataSetBusiness= new ReportDependencyInjection().getStudentDataSetBusiness();
//                return studentDataSetBusiness.getTVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DataSet(fileName, instanceID);
        
                
                ArrayList<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   ITeacherDataSetBusiness batchDataSet=preProcessor.getTeacherDataSetBusiness();
                   
                  String result= batchDataSet.getTVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DataSet(fileName, instanceID);
                  TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS obj=new TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS();
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
