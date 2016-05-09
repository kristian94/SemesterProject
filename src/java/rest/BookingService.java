/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import entity.Booking;
import entity.User;
import facade.BookingFacade;
import facade.UserFacade;
import forwarding.RequestForwarder;
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
    Gson gson = new Gson();
    
    @Context
    private UriInfo context;

    public BookingService() {
    }
    
    
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String postBooking(String content){
        System.out.println(content);
        User u = uf.getUserByUserName(jh.getUserNameFromJson(content));
        content = jh.addReserveeName(content, u);
        String result = rf.bookingRequest(content).toString();
        Booking b = jh.jsonToBooking(result);
        uf.addBooking(u, b);
        return result;
    }
    
    @RolesAllowed("Admin")
    @GET
    @Produces("application/json")
    public String getBookings(){
        return jh.bookingListToJson(bf.getBookings());
    }
    
}
