/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import security.PasswordStorage;

/**
 * REST Web Service
 *
 * @author ChristopherBorum
 */
@Path("user")
public class UserEndpoint {
    
    UserFacade uf = new UserFacade();
    Gson gson = new Gson();
    
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
            JsonObject jo = new JsonObject();
            jo.addProperty("username", u.getUserName());
            jo.addProperty("firstName", u.getFirstName());
            jo.addProperty("lastName", u.getLastName());
            jo.addProperty("email", u.getEmail());
            ja.add(jo);
        }
        return gson.toJson(ja);
    }
    
    @RolesAllowed({"Admin", "User"})
    @GET
    @Path("/{username}")
    @Produces("application/json")
    public String getUser(@PathParam("username") String username) {
        User u = uf.getUserByUserName(username);
        JsonObject jo = new JsonObject();
        jo.addProperty("firstName", u.getFirstName());
        jo.addProperty("lastName", u.getLastName());
        jo.addProperty("email", u.getEmail());
        return gson.toJson(jo);
        // todo throw error 404
    }
    
}
