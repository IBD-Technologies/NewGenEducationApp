/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.RestGateway.Archival;

import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author DELL
 */
@Path("/Archival")
public class ArchivalsResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ArchivalsResource
     */
    public ArchivalsResource() {
    }

    /**
     * Retrieves representation of an instance of com.ibd.cohesive.RestGateway.Archival.ArchivalsResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * Sub-resource locator method for {ServiceName}
     */
    @Path("{ServiceName}")
    public ArchivalResource getArchivalResource(@PathParam("ServiceName") String ServiceName)throws NamingException {
        return ArchivalResource.getInstance(ServiceName);
    }
}
