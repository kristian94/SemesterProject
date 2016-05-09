/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import entity.Booking;
import entity.User;
import facade.BookingFacade;
import facade.UserFacade;
import forwarding.RequestForwarder;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import utility.JsonHelper;
import utility.JsonValidator;

/**
 * REST Web Service
 *
 * @author Kristian Nielsen
 */
@Path("booking")
public class BookingService {

    RequestForwarder rf = new RequestForwarder();
    BookingFacade bf = new BookingFacade();
    UserFacade uf = new UserFacade();
    JsonHelper jh = new JsonHelper();
    JsonValidator jv = new JsonValidator();
    Gson gson = new Gson();
    
    @Context
    private UriInfo context;

    public BookingService() {
    }
    
    
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response postBooking(String content){
        content = jv.validateBookingRequest(content);
        if(content.equals("")) 
            return Response
                    .status(Status.BAD_REQUEST)
                    .entity(jh.buildBadBookingRequest().toString())
                    .build();
        
        User u = uf.getUserByUserName(jh.getUserNameFromJson(content));
        content = jh.addReserveeName(content, u);
        String result;
        try{result = rf.bookingRequest(content).toString();
        }catch(Exception e){
            return Response
                    .status(Status.NOT_FOUND)
                    .entity(jh.getNoFlightOrTicekts().toString())
                    .build();
        }
        Booking b = jh.jsonToBooking(result);
        uf.addBooking(u, b);
        return Response
                .status(Status.OK)
                .entity(result)
                .build();
    }
    
    @RolesAllowed("Admin")
    @GET
    @Produces("application/json")
    public Response getBookings(){
        
        List<Booking> bookings = bf.getBookings();
        JsonArray array = jh.bookingListToJson(bookings);
        if(array.size() == 0){
            return Response.status(Status.NOT_FOUND).entity(jh.getNoBookings().toString()).build();
        }
        
        return Response.status(Status.OK).entity(array.toString()).build();
    }
    
    @GET
    @Produces("application/json")
    @Path("/{userName}")
    public Response getBookingByUser(@PathParam("userName") String userName){
        User u = uf.getUserByUserName(userName);
        List<Booking> bookings = u.getBookings();
        JsonArray array = jh.bookingListToJson(bookings);
        if(array.size() == 0){
            return Response.status(Status.NOT_FOUND).entity(jh.getNoBookings().toString()).build();
        }
        
        return Response.status(Status.OK).entity(array.toString()).build();
    }
    
    
    
}
