/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;

/**
 * REST Web Service
 *
 * @author Kristian Nielsen
 */
@Path("test")
public class Test {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Test
     */
    public Test() {
    }

    /**
     * Retrieves representation of an instance of rest.Test
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getJson() {
        return "(Bodyless) Hello from the API";
    }

    
}
