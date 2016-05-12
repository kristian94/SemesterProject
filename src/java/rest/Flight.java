/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import javax.ws.rs.core.Response;
import utility.JsonHelper;
import utility.JsonValidator;

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
    JsonValidator jv = new JsonValidator();
    
    
    /**
     * Creates a new instance of Flight
     */
    public Flight() {

    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response getFlights(String content) {
        JsonObject flightJson = jv.validateFlightRequest(content);
        JsonArray errors = flightJson.get("errors").getAsJsonArray();
        if(errors.size() > 0) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(flightJson.toString())
                    .build();
        }
        System.out.println(content);
        JsonArray flights = rf.flightRequest(content);
        
        sf.addSearch(jh.toSearch(content)); 
        
        if(flights.size() == 0){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(jh.getFlightNotFound().toString())
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(flights.toString())
                .build();
    }

}
