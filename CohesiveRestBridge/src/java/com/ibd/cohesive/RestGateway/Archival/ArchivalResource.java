/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.RestGateway.Archival;

import com.ibd.businessViews.IArchApplyStatusUpdate;
import com.ibd.businessViews.IArchShippingStatusUpdate;
import com.ibd.cohesive.RestGateway.util.DependencyInjection;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;
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
 * @author DELL
 */
public class ArchivalResource {

    private String ServiceName;
    private CohesiveSession session;
    private DependencyInjection inject;

    /**
     * Creates a new instance of ArchivalResource
     */
    private ArchivalResource(String ServiceName)throws NamingException {
        this.ServiceName = ServiceName;
        session = new CohesiveSession();
        inject = new DependencyInjection();
    }

    /**
     * Get instance of the ArchivalResource
     */
    public static ArchivalResource getInstance(String ServiceName)throws NamingException {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of ArchivalResource class.
        return new ArchivalResource(ServiceName);
    }

    /**
     * Retrieves representation of an instance of com.ibd.cohesive.RestGateway.Archival.ArchivalResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ArchivalResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String archivalGateway(String content) {
        
         try {
             session.createSessionObject();
             dbg("inside archivalGateway ");
             if(ServiceName.equals("ArchApplyStatusUpdate")){
                 
                 if(content.split("~").length!=3){
                     
                     dbg("length validation failed for ArchApplyStatusUpdate");
                     throw new DBProcessingException("Archival update failed");
                 }
                 
                 
                IArchApplyStatusUpdate applyUpdate= inject.getIArchApplyStatusUpdate();
                 
                return applyUpdate.statusUpdate(content);
                 
             }else if(ServiceName.equals("ArchShippingStatusUpdate")){
                 
                if(content.split("~").length!=3){
                     
                    dbg("length validation failed for ArchShippingStatusUpdate");
                     throw new DBProcessingException("Archival update failed");
                 }
                 
                 IArchShippingStatusUpdate shippingUpdate= inject.getIArchShippingStatusUpdate();
                 
                 shippingUpdate.statusUpdate(content);
                 return "success";
             }else{
                 
                 dbg("invalid service name in archival update");
                 throw new DBProcessingException("Archival update failed");
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
        
    }
    
    private void dbg(String p_value){
        session.getDebug().dbg(p_value);
    }
     private void dbg(Exception ex){
        session.getDebug().exceptionDbg(ex);
    }

    /**
     * DELETE method for resource ArchivalResource
     */
    @DELETE
    public void delete() {
        throw new UnsupportedOperationException();
    }
}
