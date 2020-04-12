/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.web.Gateway.ImageUpload;

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
 * @author IBD Technologies
 */
@Path("/image")
public class ImageUploadsResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ImageUploadsResource
     */
    public ImageUploadsResource() {
    }

    /**
     * Retrieves representation of an instance of com.ibd.cohesive.web.Filter.ImageUploadsResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * Sub-resource locator method for {image}
     */
    @Path("{image}")
    public ImageUploadResource getImageUploadResource(@PathParam("image") String image) throws NamingException {
        return ImageUploadResource.getInstance(image);
    }
}
