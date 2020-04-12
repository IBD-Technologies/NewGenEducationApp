/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.summary.user;

import com.ibd.businessViews.IUserSummaryService;
import com.ibd.businessViews.IInstituteAssignmentSummary;
import com.ibd.businessViews.IInstituteOtherActivitySummary;
import com.ibd.businessViews.IUserProfileSummary;
import com.ibd.businessViews.IUserRoleSummary;
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
@Local(IUserSummaryService.class)
@Stateless
public class UserSummaryService implements IUserSummaryService{
    ReportDependencyInjection inject;
    CohesiveSession session;
    
    public UserSummaryService(){
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
             
             
              case "UserProfileSummary":  
                  
                  IUserProfileSummary userProfileSummary=inject.getUserProfileSummary();
                  response = userProfileSummary.EJBprocessing(requestJson);
              break;
             
              case "UserRoleSummary":  
                  
                  IUserRoleSummary userRoleSummary=inject.getUserRoleSummary();
                  response = userRoleSummary.EJBprocessing(requestJson);
              break;
              
          }
          
      }catch(Exception e){ 
         dbg(e);
         throw new EJBException(e);
      }finally{
          
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
