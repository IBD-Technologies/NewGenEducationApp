/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dependencyinjection;

import com.ibd.businessViews.IAppDataSet;
import com.ibd.businessViews.IBatchDataset;
import com.ibd.businessViews.IClassDataSet;
import com.ibd.businessViews.IClassDataSetBusiness;
import com.ibd.businessViews.IInstituteDataSet;
import com.ibd.businessViews.IInstituteDataSetBusiness;
import com.ibd.businessViews.IStudentDataSet;
import com.ibd.businessViews.IStudentDataSetBusiness;
import com.ibd.businessViews.ITeacherDataSet;
import com.ibd.businessViews.ITeacherDataSetBusiness;
import com.ibd.businessViews.ITokenValidationService;
import com.ibd.businessViews.IUserDataSet;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import com.ibd.cohesive.report.util.validation.ReportValidation;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
public class ReportDependencyInjection {
//     private static InitialContext remoteContxt;
    
     static  Context remoteContxt;
    
     Properties props = new Properties();
     ReportValidation rv;
     private static InitialContext contxt;
     
     
     
     
      public ReportDependencyInjection()throws NamingException{
          
//          remoteContxt = new InitialContext();
          contxt = new InitialContext();
         
//        if(reportUtil==null){
//            reportUtil=new ReportUtil();
//        }
//        
     if(rv==null){
            
            rv=new ReportValidation();
        }
          
      }
     
    public ReportDependencyInjection(String serviceType) throws NamingException  {
        
//        remoteContxt = new InitialContext();
       contxt = new InitialContext();
       props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
         props.put("jboss.naming.client.ejb.context", "true");
         
         remoteContxt= new InitialContext(props);
         
//        if(reportUtil==null){
//            reportUtil=new ReportUtil();
//        }
//        
        if(rv==null){
            
            rv=new ReportValidation();
        }
        
       
    }
    

//    public static InitialContext getContxt() {
//        return remoteContxt;
//    }

//    public static void setContxt(InitialContext remoteContxt) {
//        new  ReportDependencyInjection().remoteContxt = remoteContxt;
//    }


 
     
     public  IPreProcessor getPreProcessor()throws NamingException{
        IPreProcessor preProcessor=(IPreProcessor)
        
                
         contxt.lookup("java:app/CohesiveReportEngine-ejb/PreProcessor!com.ibd.cohesive.report.preprocessor.IPreProcessor");
      
        
        
        return preProcessor;
    }
     
       public ReportValidation getReportValidation(CohesiveSession session) {
        rv.setDebug(session.getDebug());
        rv.setI_db_properties(session.getCohesiveproperties());
        return rv;
    }

     public ITokenValidationService getTokenValidationService() throws NamingException{
        //EJB Integration change
        //return dbcoreservice;
       // InitialContext a =new InitialContext();
    ITokenValidationService authTokenValiation = (ITokenValidationService)
        // remoteContxt.lookup("java:app/CohesiveBusiness-ejb/TokenValidateService!com.ibd.businessViews.ITokenValidationService");
 remoteContxt.lookup("ejb:CohesiveBackend/CohesiveBusiness-ejb/TokenValidateService!com.ibd.businessViews.ITokenValidationService");

            
            return authTokenValiation;
    }
     
     
     public  IStudentDataSet getStudentDataset()throws NamingException{
        IStudentDataSet studentDataSet=(IStudentDataSet)
          
         remoteContxt.lookup("ejb:CohesiveReportEngine/ReportProcessing/StudentDataSet!com.ibd.businessViews.IStudentDataSet");
        return studentDataSet;
    }
    
    public  ITeacherDataSet getTeacherDataset()throws NamingException{
        ITeacherDataSet teacherDataSet=(ITeacherDataSet)
          
         remoteContxt.lookup("ejb:CohesiveReportEngine/ReportProcessing/TeacherDataSet!com.ibd.businessViews.ITeacherDataSet");
        return teacherDataSet;
    }
    
    public  IClassDataSet getClassDataset()throws NamingException{
        IClassDataSet classDataSet=(IClassDataSet)
          
         remoteContxt.lookup("ejb:CohesiveReportEngine/ReportProcessing/ClassDataSet!com.ibd.businessViews.IClassDataSet");
        return classDataSet;
    }
    
    public  IInstituteDataSet getInstituteDataset()throws NamingException{
        IInstituteDataSet instituteDataSet=(IInstituteDataSet)
          
         remoteContxt.lookup("ejb:CohesiveReportEngine/ReportProcessing/InstituteDataSet!com.ibd.businessViews.IInstituteDataSet");
        return instituteDataSet;
    }
    
     public  IUserDataSet getUserDataset()throws NamingException{
        IUserDataSet userDataSet=(IUserDataSet)
          
         remoteContxt.lookup("ejb:CohesiveReportEngine/ReportProcessing/UserDataSet!com.ibd.businessViews.user.IUserDataSet");
        return userDataSet;
    }
     
      public  IAppDataSet getAppDataset()throws NamingException{
        IAppDataSet appDataSet=(IAppDataSet)
          
//         remoteContxt.lookup("ejb/CohesiveReportEngine/ReportProcessing/AppDataSet!com.ibd.cohesive.report.dbreport.dataSets.app.IAppDataSet");
           remoteContxt.lookup("ejb:CohesiveReportEngine/ReportProcessing/AppDataSet!com.ibd.businessViews.IAppDataSet");
        return appDataSet;
    }
      
      public  IClassDataSetBusiness getClassDataSetBusiness() throws NamingException{
          IClassDataSetBusiness classDataSetBusiness=(IClassDataSetBusiness)
                remoteContxt.lookup("ejb:CohesiveReportEngine/ReportProcessing/ClassDataSetBusiness!com.ibd.businessViews.IClassDataSetBusiness");
        return classDataSetBusiness;  
          
      }
      public  IStudentDataSetBusiness getStudentDataSetBusiness() throws NamingException{
          IStudentDataSetBusiness studentDataSetBusiness=(IStudentDataSetBusiness)
                remoteContxt.lookup("ejb:CohesiveReportEngine/ReportProcessing/StudentDataSetBusiness!com.ibd.businessViews.IStudentDataSetBusiness");
        return studentDataSetBusiness;  
          
      }
     public  ITeacherDataSetBusiness getTeacherDataSetBusiness() throws NamingException{
          ITeacherDataSetBusiness teacherDataSetBusiness=(ITeacherDataSetBusiness)
                remoteContxt.lookup("ejb:CohesiveReportEngine/ReportProcessing/TeacherDataSetBusiness!com.ibd.businessViews.ITeacherDataSetBusiness");
        return teacherDataSetBusiness;  
          
      }
     
     public  IBatchDataset getBatchDataset()throws NamingException{
        IBatchDataset batchDataSet=(IBatchDataset)
          
         remoteContxt.lookup("ejb:CohesiveReportEngine/ReportProcessing/BatchDataset!com.ibd.businessViews.IBatchDataset");
        return batchDataSet;
    }
     
//    public IStudentDataSet getStudentExistinMedicalDetailTable()throws NamingException{
//        IStudentDataSet studentDataSet=(IStudentDataSet)
//          
//         remoteContxt.lookup("ejb/CohesiveReportEngine/ReportProcessing/StudentDataSet!com.ibd.cohesive.report.dbreport.dataSets.student.IStudentDataSet");
//        return studentDataSet;
//    }
    
     
     public  IInstituteDataSetBusiness getInstituteDataSetBusiness() throws NamingException{
          IInstituteDataSetBusiness instituteDataSetBusiness=(IInstituteDataSetBusiness)
                remoteContxt.lookup("ejb:CohesiveReportEngine/ReportProcessing/InstituteDataSetBusiness!com.ibd.businessViews.IInstituteDataSetBusiness");
        return instituteDataSetBusiness;  
          
      }
     
     
     
     
     
     
}
