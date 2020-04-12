/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.summary.teacher;

import com.ibd.businessViews.ITeacherSummaryService;
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
@Local(ITeacherSummaryService.class)
@Stateless
public class TeacherSummaryService implements ITeacherSummaryService{
    ReportDependencyInjection inject;
    CohesiveSession session;
    
    public TeacherSummaryService(){
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
              
              
             
             
              case "TeacherAttendanceSummary":  
                  
                  ITeacherAttendanceSummary attendancesummary=inject.getTeacherAttendanceSummaryService();
                  response = attendancesummary.EJBprocessing(requestJson);
              break;
             
              case "TeacherLeaveManagementSummary":  
                  
                  ITeacherLeaveManagementSummary leaveSummary=inject.getTeacherLeaveManagementSummary();
                  response = leaveSummary.EJBprocessing(requestJson);
              break;
              case "TeacherTimeTableSummary":  
                  
                  ITeacherTimeTableSummary timeTableSummary=inject.getTeacherTimeTableSummary();
                  response = timeTableSummary.EJBprocessing(requestJson);
              break;
              case "TeacherProfileSummary":  
                  
                  ITeacherProfileSummary profileSummary=inject.getTeacherProfileSummary();
                  response = profileSummary.EJBprocessing(requestJson);
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
