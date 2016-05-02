/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import facade.BookingFacade;
import facade.SearchFacade;
import forwarding.RequestForwarder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import utility.JsonHelper;

/**
 * REST Web Service
 *
 * @author Kristian Nielsen
 */
@Path("flight")
public class Flight {

    @Context
    private UriInfo context;

    RequestForwarder rf = new RequestForwarder();
    SearchFacade sf = new SearchFacade();
    JsonHelper jh = new JsonHelper();
    
    
    
    /**
     * Creates a new instance of Flight
     */
    public Flight() {
        
        
        
    }

    
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String getFlights(String content) {
//        sf.addSearch(jh.toSearch(content));
        return rf.flightRequest(content);
    }

    
    
    
}
