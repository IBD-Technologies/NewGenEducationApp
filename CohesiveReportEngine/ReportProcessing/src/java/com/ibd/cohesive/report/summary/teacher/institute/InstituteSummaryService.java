/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.summary.teacher.institute;

import com.ibd.businessViews.IInstituteSummaryService;
import com.ibd.businessViews.ITeacherSummaryService;
import com.ibd.businessViews.IInstituteAssignmentSummary;
//import com.ibd.cohesive.app.business.institute.summary.InstituteAssignment.InstituteAssignmentSummary;
import com.ibd.businessViews.IInstituteOtherActivitySummary;
import com.ibd.businessViews.IInstituteFeeManagementSummary;
import com.ibd.businessViews.IInstitutePaymentSummary;
import com.ibd.businessViews.IGroupMappingSummary;
import com.ibd.businessViews.IHolidayMaintenanceSummary;
import com.ibd.businessViews.INotificationSummary;
import com.ibd.businessViews.ITeacherAttendanceSummary;
import com.ibd.businessViews.ITeacherLeaveManagementSummary;
import com.ibd.businessViews.ITeacherProfileSummary;
import com.ibd.businessViews.ITeacherTimeTableSummary;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.session.CohesiveSession;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Local(IInstituteSummaryService.class)
@Stateless
public class InstituteSummaryService implements IInstituteSummaryService{
     ReportDependencyInjection inject;
    CohesiveSession session;
    
    public InstituteSummaryService(){
        try {
            inject=new ReportDependencyInjection();
            session = new CohesiveSession();
        } catch (NamingException ex) {
            dbg(ex);
            throw new EJBException(ex);
        }
        
    }
    @Override
    public JsonObject EJBprocessing(JsonObject requestJson,String service)
    {     
        JsonObject response =null;
        boolean l_session_created_now=false;
      try{
          session.createSessionObject();
          l_session_created_now=session.isI_session_created_now();
          switch(service){
              
              
             
             
              case "InstituteAssignmentSummary":  
                  
                  IInstituteAssignmentSummary assignmentSummary=inject.getInstituteAssignmentSummary();
                  response = assignmentSummary.EJBprocessing(requestJson);
              break;
             
              case "InstituteOtherActivitySummary":  
                  
                  IInstituteOtherActivitySummary otherActivitySummary=inject.getInstituteOtherActivitySummary();
                  response = otherActivitySummary.EJBprocessing(requestJson);
              break;
              case "InstituteFeeManagementSummary":  
                  
                  IInstituteFeeManagementSummary feeSummary=inject.getInstituteFeeManagementSummary();
                  response = feeSummary.EJBprocessing(requestJson);
              break;
              case "InstitutePaymentSummary":  
                  
                  IInstitutePaymentSummary paymentSummary=inject.getInstitutePaymentSummary();
                  response = paymentSummary.EJBprocessing(requestJson);
              break;
              case "GroupMappingSummary":  
                  
                  IGroupMappingSummary groupSummary=inject.getGroupMappingSummary();
                  response = groupSummary.EJBprocessing(requestJson);
              break;
              case "HolidayMaintenanceSummary":  
                  
                  IHolidayMaintenanceSummary holidaySummary=inject.getHolidayMaintenanceSummary();
                  response = holidaySummary.EJBprocessing(requestJson);
              break;
              case "NotificationSummary":  
                  
                  INotificationSummary notificationSummary=inject.getNotificationSummary();
                  response = notificationSummary.EJBprocessing(requestJson);
              break;
             
          }
          
          
          
          
          
      }
      catch(Exception e)
      { 
         dbg(e);
         throw new EJBException(e);
      }  finally{
          
          if(l_session_created_now){
              session.clearSessionObject();
          }
          
      }
      return response;
     }
    
    
    
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }  
}
