/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.institute;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.institute.BusinessReportParams;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class BusinessReportParamsDataSet {
    public ArrayList<BusinessReportParams> getBusinessReportParams_DataSet(String studentID,String standard,String section,String fromDate,String toDate,String feeID,String notificationType,String studentStatus,String instituteID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        ArrayList<BusinessReportParams>classDetailsDataSet=new ArrayList();
        
        try{
            
            
               dbg("inside getClassOtherActivity",session);
               BusinessService bs=appInject.getBusinessService(session);
               BusinessReportParams param=new BusinessReportParams();
               
               if(studentID!=null&&!studentID.isEmpty()){
                   param.setStudentID(studentID);
                   param.setStudentName(bs.getStudentName(studentID, instituteID, session, dbSession, appInject));
               }else{
                   
                   param.setStudentID(" ");
                   param.setStudentName(" ");
               }
               
               if(standard!=null&&!standard.isEmpty()){
                   param.setStandard(standard);
               }else{
                   
                   param.setStandard(" ");
               }
               if(section!=null&&!section.isEmpty()){
                   param.setSection(section);
               }else{
                   
                   param.setSection(" ");
               }
               if(fromDate!=null&&!fromDate.isEmpty()){
                   param.setFromDate(fromDate);
               }else{
                   
                   param.setFromDate(" ");
               }
               if(toDate!=null&&!toDate.isEmpty()){
                   param.setToDate(toDate);
               }else{
                   
                   param.setToDate(" ");
               }
               if(feeID!=null&&!feeID.isEmpty()){
                   param.setFeeID(feeID);
               }else{
                   
                   param.setFeeID(" ");
               }
               if(notificationType!=null&&!notificationType.isEmpty()){
                   param.setNotificationType(notificationType);
               }else{
                   
                   param.setNotificationType(" ");
               }
               
               if(studentStatus!=null&&!studentStatus.isEmpty()){
                   param.setStudentStatus(studentStatus);
               }else{
                   
                   param.setStudentStatus(" ");
               }
               
               
               classDetailsDataSet.add(param);
               
         
            
           
            
            
        
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
