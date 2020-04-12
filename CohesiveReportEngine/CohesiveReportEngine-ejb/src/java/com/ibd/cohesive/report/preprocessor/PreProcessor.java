/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.preprocessor;

//import com.ibd.cohesive.db.session.DBSession;
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
import com.ibd.businessViews.IUserDataSet;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.util.validation.ReportValidation;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Local(IPreProcessor.class)
@Stateless
public class PreProcessor implements IPreProcessor{
    ReportDependencyInjection inject;
    CohesiveSession session;
    //DBSession dbSession;
    
    public PreProcessor(){
        try {
            inject=new ReportDependencyInjection("REMOTE");
            session = new CohesiveSession("gateway.properties");
           // dbSession = new DBSession(session);
        } catch (NamingException ex) {
          dbg(ex);
          throw new EJBException(ex);
        }
        
    }
    
    public boolean preProcessing(String token,String userID,String instituteID,String service)throws DBValidationException,BSValidationException,DBProcessingException,BSProcessingException{
        boolean status=false;
        boolean l_session_created_now=false;
        try{
            session.createSessionObject();
            dbg("inside preProcessing");
            dbg("userID--->"+userID);
            dbg("instituteID--->"+instituteID);
            dbg("service--->"+service);
            
            l_session_created_now=session.isI_session_created_now();
            ReportValidation reportValidation=inject.getReportValidation(session);
            
            status= (reportValidation.ResourceTokenValidation(token, userID, instituteID, service, inject));
                
                
                
            dbg("end of  preProcessing status--->"+status);
      
     }catch(BSValidationException ex){
          throw ex;     
     
      }catch(BSProcessingException ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
    }finally{
            if(l_session_created_now){
                
                session.clearSessionObject();
            }
            
        }
    return status;
    }
    
    public  IAppDataSet getAppDataset()throws NamingException{
        IAppDataSet appDataSet= inject.getAppDataset();
        return appDataSet;
    }
      
     public  IStudentDataSet getStudentDataset()throws NamingException{
        IStudentDataSet studentDataSet=inject.getStudentDataset();
          
         return studentDataSet;
    }
    
    public  ITeacherDataSet getTeacherDataset()throws NamingException{
        ITeacherDataSet teacherDataSet=inject.getTeacherDataset();
          
        
                return teacherDataSet;
    }
    
    public  IClassDataSet getClassDataset()throws NamingException{
        IClassDataSet classDataSet=inject.getClassDataset();
          
        return classDataSet;
    }
    
    public  IInstituteDataSet getInstituteDataset()throws NamingException{
        IInstituteDataSet instituteDataSet=inject.getInstituteDataset();
                return instituteDataSet;
    }
    
     public  IUserDataSet getUserDataset()throws NamingException{
        IUserDataSet userDataSet=inject.getUserDataset();
                return userDataSet;
    }
     
    
      public  IClassDataSetBusiness getClassDataSetBusiness() throws NamingException{
          IClassDataSetBusiness classDataSetBusiness=inject.getClassDataSetBusiness();
                  return classDataSetBusiness;  
          
      }
      public  IStudentDataSetBusiness getStudentDataSetBusiness() throws NamingException{
          IStudentDataSetBusiness studentDataSetBusiness=inject.getStudentDataSetBusiness();
                  return studentDataSetBusiness;  
          
      }
     public  ITeacherDataSetBusiness getTeacherDataSetBusiness() throws NamingException{
          ITeacherDataSetBusiness teacherDataSetBusiness=inject.getTeacherDataSetBusiness();
                  return teacherDataSetBusiness;  
          
      }
     public  IInstituteDataSetBusiness getInstituteDataSetBusiness() throws NamingException{
          IInstituteDataSetBusiness InstituteDataSetBusiness=inject.getInstituteDataSetBusiness();
                  return InstituteDataSetBusiness;  
          
      }
     
     public  IBatchDataset getBatchDataset()throws NamingException{
        IBatchDataset batchDataSet=inject.getBatchDataset();
                return batchDataSet;
    }
    
    
    
    
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
}
