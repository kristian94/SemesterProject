/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import facade.BookingFacade;
import facade.SearchFacade;
import forwarding.BookingForwarder;
import forwarding.FlightForwarder;
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

    SearchFacade sf = new SearchFacade();
    BookingFacade bf = new BookingFacade();
    FlightForwarder fw = new FlightForwarder();
    BookingForwarder bfw = new BookingForwarder();
    
    JsonHelper jh = new JsonHelper();
    
    
    
    /**
     * Creates a new instance of Flight
     */
    public Flight() {
        
        
        
    }

    
    
    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public String getFlights(String content) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of Flight
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public void submitBooking(String content) {
        
    }
}
