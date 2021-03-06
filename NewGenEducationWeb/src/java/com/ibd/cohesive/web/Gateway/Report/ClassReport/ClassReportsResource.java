/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.web.Gateway.Report.ClassReport;

import javax.json.JsonObject;
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
@Path("/ClassReport")
public class ClassReportsResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ClassReportsResource
     */
    public ClassReportsResource() {
    }

    /**
     * Retrieves representation of an instance of com.ibd.cohesive.web.Gateway.Report.ClassReport.ClassReportsResource
     * @return an instance of javax.json.JsonObject
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * Sub-resource locator method for {ReportName}
     */
    @Path("{ReportName}")
    public ClassReportResource getClassReportResource(@PathParam("ReportName") String ReportName)throws NamingException  {
        return ClassReportResource.getInstance(ReportName);
    }
}
