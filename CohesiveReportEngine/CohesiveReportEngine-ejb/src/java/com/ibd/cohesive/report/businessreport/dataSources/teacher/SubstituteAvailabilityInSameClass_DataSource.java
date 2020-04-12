/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.teacher;

import com.ibd.businessViews.ITeacherDataSetBusiness;
import com.ibd.cohesive.report.businessreport.dataSources.dataModels.teacher.SubstituteAvailabilityInSameClass;
import com.ibd.cohesive.report.businessreport.dataSources.dataModels.teacher.SubstituteAvailabilityInSameClass;
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
public class SubstituteAvailabilityInSameClass_DataSource extends TeacherDataSource<SubstituteAvailabilityInSameClass>{
     String teacherID;
    String instanceID;
    String userID;
    String date;
    
    @Override
      public List<SubstituteAvailabilityInSameClass> fetch()
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
//                return studentDataSetBusiness.getSubstituteAvailabilityInSameClass_DataSet(fileName, instanceID);
        
                
                ArrayList<SubstituteAvailabilityInSameClass> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   ITeacherDataSetBusiness teacherDataSet=preProcessor.getTeacherDataSetBusiness();
                   
                  String result= teacherDataSet.getSubstituteAvailabilityInSameClass_DataSet(teacherID, instanceID, date, userID);
                  SubstituteAvailabilityInSameClass obj=new SubstituteAvailabilityInSameClass();
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
