/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.web.Gateway.Report.InstituteReport;

import com.ibd.cohesive.util.session.CohesiveSession;
import com.ibd.cohesive.web.Gateway.util.CohesiveBeans;
import com.ibd.cohesive.web.Gateway.util.WebDI.DependencyInjection;
import com.ibd.cohesive.web.Gateway.util.WebUtility;
import javax.ejb.EJBException;
import javax.json.JsonObject;
import javax.naming.NamingException;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author IBD Technologies
 */
public class InstituteReportResource {
    // private String ServiceName;
    private CohesiveSession session;
    private DependencyInjection inject;
    private WebUtility webutil;
    private String ReportName;

    /**
     * Creates a new instance of InstituteReportResource
     */
    private InstituteReportResource(String ReportName) throws NamingException {
        this.ReportName = ReportName;
         session = new CohesiveSession("gateway.properties");
        inject = new DependencyInjection();
        webutil=new WebUtility();
    
    }

    /**
     * Get instance of the InstituteReportResource
     */
    public static InstituteReportResource getInstance(String ReportName) throws NamingException {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of InstituteReportResource class.
        return new InstituteReportResource(ReportName);
    }

    /**
     * Retrieves representation of an instance of com.ibd.cohesive.web.Gateway.Report.InstituteReport.InstituteReportResource
     * @return an instance of javax.json.JsonObject
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of InstituteReportResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject InstituteReport(JsonObject content) {
         JsonObject response =null;
        try {
            session.createGatewaySessionObject();
            dbg("Inside Institute Gateway Session ID:"+session.getI_session_identifier());
            dbg("Request: " + content.toString());
            boolean comeOutLoop = false;
            int iteratonCount = 0;
            while (comeOutLoop == false) {
                try {
                    iteratonCount = iteratonCount + 1;
            CohesiveBeans bean =inject.getCohesiveBean(session);
           /* if (session.getCohesiveproperties().getProperty("REMOTEEJB").equals("YES"))
               response = bean.invokeBean(this.ReportName,content,inject,webutil,session);
            else*/
//            response = bean.invokeBean(this.ReportName,content);
             response=bean.invokeReportBean(ReportName, content, webutil, session, inject);
            dbg("Response: "+response);
             comeOutLoop = true;
                } catch (EJBException ex) {

                    if (iteratonCount <= 5) {

                        inject = null;
                        Thread.sleep(500);
                        inject = new DependencyInjection();

                    } else {
                        throw ex;
                    }
                }
//                catch (Exception ex) {
//
//                    if (iteratonCount <= 10) {
//                        inject = null;
//                        Thread.sleep(3000);
//                        inject = new DependencyInjection();
//
//                    } else {
//                        throw ex;
//                    }
//                }

            }
            
        } catch(NamingException ex) {
           dbg(ex);
           throw new RuntimeException(ex);
           }
          catch(Exception e)
          {
             dbg(e);
           throw new RuntimeException(e); 
          }
          
         finally{
            session.clearSessionObject();
           }         
        return response;
        
        
        
    }

    /**
     * DELETE method for resource InstituteReportResource
     */
    @DELETE
    public void delete() {
        throw new UnsupportedOperationException();
    }
    
    private void dbg(String p_value){
        session.getDebug().dbg(p_value);
    }
     private void dbg(Exception ex){
        session.getDebug().exceptionDbg(ex);
    }    
    
    
}
