/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import entity.Airport;
import facade.AirportFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import utility.JsonHelper;

/**
 *
 * @author matskruger
 */
@Path("airport")
public class AirportFacadeREST {
    private static final AirportFacade af = new AirportFacade();
    private static final JsonHelper jh = new JsonHelper();
    
    

    public AirportFacadeREST() {
        
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response find(@PathParam("id") Integer id) {
        Airport a = af.getAirport(id);
        if (a != null) {
            return Response
                    .status(Response.Status.OK)
//                    .header("Access-Control-Allow-Origin", "*")
//                    .header("Access-Control-Allow-Methods", "GET")
                    .entity(jh.airportToJson(a))
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("No airport found with ID of: " + id).build();
    }

    @GET
    
    @Produces("application/json")
    public Response findAll(@QueryParam("q") String q) {
        if (q != null) {
            return Response
                .status(Response.Status.OK)
                .entity(jh.airportsToJson(af.getAirportsByQuery(q)))
                .build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("No airport found with Query of: " + q).build();
        //return super.findAll();
    }
    
}
