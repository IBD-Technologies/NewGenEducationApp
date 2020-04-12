/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.teacher;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.SubstituteReportParam;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class SubstituteReportParam_DataSet {
     public ArrayList<SubstituteReportParam> getSubstituteReportParam_DataSet(String teacherID,String date,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        ArrayList<SubstituteReportParam>classDetailsDataSet=new ArrayList();
        
        try{
            
            try{
            
               dbg("inside getClassOtherActivity",session);
               BusinessService bs=appInject.getBusinessService(session);
               String teacherName=bs.getTeacherName(teacherID, p_instanceID, session, dbSession, appInject);
               dbg("teacherName"+teacherName,session);
               
               SubstituteReportParam param=new SubstituteReportParam();
               
               param.setDate(date);
               param.setTeacherID(teacherID);
               param.setTeacherName(teacherName);
               
               classDetailsDataSet.add(param);
               
         }catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
            }else{
                throw ex;
            }
            
        }
            
            if(classDetailsDataSet.isEmpty()){
                SubstituteReportParam param=new SubstituteReportParam();
                param.setDate(date);
               param.setTeacherID(teacherID);
               param.setTeacherName(" ");
               
               classDetailsDataSet.add(param);
                
            }
            
            
        
               dbg("end of getClassOtherActivity",session);
       return classDetailsDataSet;
    }catch(DBProcessingException ex){
          dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex,session);
          throw ex;
     }catch(Exception ex){
         dbg(ex,session);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
    
    
    
    
    public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}
