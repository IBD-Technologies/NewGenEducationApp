/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.teacher;

import com.ibd.businessViews.ITeacherDataSetBusiness;
import com.ibd.cohesive.report.businessreport.dataSources.dataModels.teacher.TeacherTimeTableReport;
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
public class TeacherTimeTableReport_DataSource extends TeacherDataSource<TeacherTimeTableReport>{
     String teacherID;
    String instanceID;
    String date;
    String userID;
    
    @Override
      public List<TeacherTimeTableReport> fetch()
	{
	
            try
            {
                //parseParameters();
                teacherID = this.getTeacherId();
                instanceID =this.getLoginInstitute();
                date=this.getLeaveDate();
                userID=this.getUserID();
//                
//                IStudentDataSetBusiness studentDataSetBusiness= new ReportDependencyInjection().getStudentDataSetBusiness();
//                return studentDataSetBusiness.getTeacherTimeTableReport_DataSet(fileName, instanceID);
        
                
                ArrayList<TeacherTimeTableReport> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   ITeacherDataSetBusiness batchDataSet=preProcessor.getTeacherDataSetBusiness();
                   
                  String result= batchDataSet.getTeacherTimeTableReport_DataSet(teacherID, instanceID, date, userID);
                  TeacherTimeTableReport obj=new TeacherTimeTableReport();
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
