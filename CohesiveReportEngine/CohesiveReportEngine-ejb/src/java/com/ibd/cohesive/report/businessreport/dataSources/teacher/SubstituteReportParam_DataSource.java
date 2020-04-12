/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.teacher;

import com.ibd.businessViews.ITeacherDataSetBusiness;
import com.ibd.cohesive.report.businessreport.dataSources.dataModels.teacher.SubstituteReportParam;
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
public class SubstituteReportParam_DataSource extends TeacherDataSource<SubstituteReportParam>{
     String teacherID;
    String instanceID;
    String userID;
    String date;
    
    @Override
      public List<SubstituteReportParam> fetch()
	{
	
            try
            {
                //parseParameters();
                teacherID = this.getTeacherId();
                instanceID =this.getLoginInstitute();
                userID=this.getUserID();
                date=this.getLeaveDate();
//                
//                IStudentDataSetBusiness studentDataSetBusiness= new ReportDependencyInjection().getStudentDataSetBusiness();
//                return studentDataSetBusiness.getSubstituteReportParam_DataSet(fileName, instanceID);
        
                
                ArrayList<SubstituteReportParam> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   ITeacherDataSetBusiness teacherDataSet=preProcessor.getTeacherDataSetBusiness();
                   
                  String result= teacherDataSet.getSubstituteReportParam_DataSet(teacherID, instanceID, date);
                  SubstituteReportParam obj=new SubstituteReportParam();
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
