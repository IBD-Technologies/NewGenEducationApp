/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.summary.student;

import com.ibd.businessViews.IStudentLeaveManagementSummary;
import com.ibd.businessViews.IStudentOtherActivitySummary;
import com.ibd.businessViews.IStudentSummaryService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.businessViews.IStudentAssignmentSummary;
import com.ibd.businessViews.IStudentAttendanceSummary;
import com.ibd.businessViews.IStudentExamScheduleSummary;
import com.ibd.businessViews.IStudentFeeManagementSummary;
import com.ibd.businessViews.IStudentPaymentSummary;
import com.ibd.businessViews.IStudentProfileSummary;
import com.ibd.businessViews.IStudentProgressCardSummary;
import com.ibd.businessViews.IStudentTimeTableSummary;
import com.ibd.cohesive.db.session.DBSession;
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
@Local(IStudentSummaryService.class)
@Stateless
public class StudentSummaryService implements IStudentSummaryService{
    ReportDependencyInjection inject;
    CohesiveSession session;
    
    public StudentSummaryService(){
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
      try{
          
          switch(service){
              
              
              case "StudentAssignmentSummary":  
                  
                  IStudentAssignmentSummary assignmentSummary=inject.getStudentAssignmentSummary();
                  response = assignmentSummary.EJBprocessing(requestJson);
              break;
              case "StudentProfileSummary":  
                  
                  IStudentProfileSummary profileSummary=inject.getStudentProfileSummary();
                  response = profileSummary.EJBprocessing(requestJson);
              break;
              case "StudentTimeTableSummary":  
                  
                  IStudentTimeTableSummary timeTableSummary=inject.getStudentTimeTableSummary();
                  response = timeTableSummary.EJBprocessing(requestJson);
              break;
              case "StudentAttendanceSummary":  
                  
                  IStudentAttendanceSummary attendancesummary=inject.getStudentAttendanceSummaryService();
                  response = attendancesummary.EJBprocessing(requestJson);
              break;
              case "StudentExamScheduleSummary":  
                  
                  IStudentExamScheduleSummary examScheduleSummary=inject.getStudentExamScheduleSummary();
                  response = examScheduleSummary.EJBprocessing(requestJson);
              break;
              case "StudentFeeManagementSummary":  
                  
                  IStudentFeeManagementSummary feeSummary=inject.getStudentFeeManagementSummary();
                  response = feeSummary.EJBprocessing(requestJson);
              break;
              case "StudentLeaveManagementSummary":  
                  
                  IStudentLeaveManagementSummary leaveSummary=inject.getStudentLeaveManagementSummary();
                  response = leaveSummary.EJBprocessing(requestJson);
              break;
              case "StudentOtherActivitySummary":  
                  
                  IStudentOtherActivitySummary otherActivitySummary=inject.getStudentOtherActivitySummary();
                  response = otherActivitySummary.EJBprocessing(requestJson);
              break;
              case "StudentPaymentSummary":  
                  
                  IStudentPaymentSummary paymentSummary=inject.getStudentPaymentSummary();
                  response = paymentSummary.EJBprocessing(requestJson);
              break;
              case "StudentProgressCardSummary":  
                  
                  IStudentProgressCardSummary progressSummary=inject.getStudentProgressCardSummary();
                  response = progressSummary.EJBprocessing(requestJson);
              break;
          }
          
          
          
          
          
      }
      catch(Exception e)
      { 
         dbg(e);
         throw new EJBException(e);
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
