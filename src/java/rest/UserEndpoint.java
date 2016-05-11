/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.Role;
import entity.User;
import facade.UserFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import security.PasswordStorage;
import security.UserPrincipal;
import utility.JsonHelper;

/**
 * REST Web Service
 *
 * @author ChristopherBorum
 */
@Path("user")
public class UserEndpoint {
    
    UserFacade uf = new UserFacade();
    Gson gson = new Gson();
    JsonHelper jh = new JsonHelper();
    
    @Context
    private UriInfo context;

    public UserEndpoint() {
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createUser(String content) {
        User u = gson.fromJson(content, User.class);
        Role userRole = new Role("User");
        u.AddRole(userRole);
        if (uf.getUserByUserName(u.getUserName()) != null) {
            return "userName already exists";
        }
        try {
            u.setPassword(PasswordStorage.createHash(u.getPassword()));
        } catch (PasswordStorage.CannotPerformOperationException ex) {
            Logger.getLogger(UserEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            return "password storage error";
        }
        //check if user already exist
        uf.persistUser(u);
        return "ok"; //return json instead?
    }
       
    @RolesAllowed("Admin")
    @GET
    @Path("/all")
    @Produces("application/json")
    public String getAllUsers() {
        List<User> ul = uf.getUsers();
        JsonArray ja = new JsonArray();
        for (User u : ul) {
            ja.add(jh.userToJsonObject(u));
        }
        return gson.toJson(ja);
    }
    
    @RolesAllowed({"Admin", "User"})
    @PUT
    @Path("/update")
    @Produces("application/json")
    public Response updateUser(@Context SecurityContext securityContext, String content) {
        UserPrincipal principal = (UserPrincipal) securityContext.getUserPrincipal();
        User updatedUser = gson.fromJson(content, User.class);
        User u = uf.updateUser(updatedUser, principal.getName());
        return Response.status(Response.Status.OK).entity(gson.toJson(jh.userToJsonObject(u))).build();
    }
    
    @RolesAllowed({"Admin", "User"})
    @GET
    @Produces("application/json")
    public Response getUser(@Context SecurityContext securityContext) {
        UserPrincipal principal = (UserPrincipal) securityContext.getUserPrincipal();
        User u = uf.getUserByUserName(principal.getName());
        return Response.status(Response.Status.OK).entity(gson.toJson(jh.userToJsonObject(u))).build();
    }
    
}
