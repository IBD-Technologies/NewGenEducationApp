/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.web.Gateway.Teacher;

import com.ibd.cohesive.util.session.CohesiveSession;
import com.ibd.cohesive.web.Gateway.util.CohesiveBeans;
import com.ibd.cohesive.web.Gateway.util.WebDI.DependencyInjection;
import com.ibd.cohesive.web.Gateway.util.WebUtility;
import javax.ejb.EJBException;
import javax.json.JsonObject;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author DELL
 */
public class TeacherResource {

    private String ServiceName;
    private CohesiveSession session;
    private DependencyInjection inject;
    private WebUtility webutil;

    /**
     * Creates a new instance of TeacherResource
     */
    private TeacherResource(String ServiceName) throws NamingException {
        this.ServiceName = ServiceName;
        session = new CohesiveSession("gateway.properties");
        inject = new DependencyInjection();
        webutil=new WebUtility();
    }

    /**
     * Get instance of the TeacherResource
     */
    public static TeacherResource getInstance(String ServiceName) throws NamingException {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of TeacherResource class.
        return new TeacherResource(ServiceName);
    }

    /**
     * Retrieves representation of an instance of com.ibd.cohesive.web.Gateway.Teacher.TeacherResource
     * @return an instance of javax.json.JsonObject
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of TeacherResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject StudentGateway(JsonObject content) {
        JsonObject response =null;
        try {
            session.createGatewaySessionObject();
            dbg("Inside Student Gateway Session ID:"+session.getI_session_identifier());
            dbg("Request: " + content.toString());
            boolean comeOutLoop = false;
            int iteratonCount = 0;
            while (comeOutLoop == false) {
                try {
                    iteratonCount = iteratonCount + 1;
            
            CohesiveBeans bean =inject.getCohesiveBean(session);
            if (session.getCohesiveproperties().getProperty("REMOTEEJB").equals("YES"))
               response = bean.invokeBean(this.ServiceName,content,inject,webutil,session);
            else
               response = bean.invokeBean(this.ServiceName,content);
            dbg("Response: "+response);
            comeOutLoop = true;
                } catch (EJBException ex) {

                    if (iteratonCount <= 10) {

                        inject = null;
                        Thread.sleep(3000);
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
    public void putJson(JsonObject content) {
    }

    /**
     * DELETE method for resource TeacherResource
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
