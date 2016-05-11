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
import java.security.Principal;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import security.UserPrincipal;
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
    public Response postBooking(String content){
        System.out.println(content);
        User u = uf.getUserByUserName(jh.getUserNameFromJson(content));
        content = jh.addReserveeName(content, u);
        String result = rf.bookingRequest(content).toString();
        Booking b = jh.jsonToBooking(result);
        bf.addBooking(u, b);
        return Response
                .status(Status.OK)
                .entity(result)
                .build();
    }
    
    @DELETE
    @Path("/{bookingID}")
    @RolesAllowed({"Admin", "User"})
    public Response deleteBooking(@PathParam("bookingID") String bookingID) {
        System.out.println("booking id = " + bookingID);
        if (bf.removeBooking(bookingID)) {
            return Response.status(Status.OK).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }
    
    @GET
    @Path("/admin/all")
    @Produces("application/json")
    @RolesAllowed("Admin")
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
    @RolesAllowed({"User", "Admin"})
    public Response getBookingByUser(@Context SecurityContext securityContext) {
        UserPrincipal principal = (UserPrincipal) securityContext.getUserPrincipal();
        String username = principal.getName();
        User u = uf.getUserByUserName(username);
        List<Booking> bookings = u.getBookings();
        if(bookings.isEmpty()){
            return Response.status(Status.OK).entity(jh.getNoBookings().toString()).build();
        }
        JsonArray array = jh.bookingListToJson(bookings);
        return Response.status(Status.OK).entity(array.toString()).build();
    }
    
    @GET
    @Produces("application/json")
    @Path("/{username}")
    @RolesAllowed("Admin")
    public Response getBookingAdmin(@PathParam("username") String username) {
        User u = uf.getUserByUserName(username);
        List<Booking> bookings = u.getBookings();
        if(bookings.isEmpty()){
            return Response.status(Status.OK).entity(jh.getNoBookings().toString()).build();
        }
        JsonArray array = jh.bookingListToJson(bookings);
        return Response.status(Status.OK).entity(array.toString()).build();
    }
    
}
