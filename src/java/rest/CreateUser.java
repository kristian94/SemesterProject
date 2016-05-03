package rest;

import com.google.gson.Gson;
import entity.Role;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import entity.User;
import facade.UserFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.POST;
import security.PasswordStorage;

/**
 * REST Web Service
 *
 * @author ChristopherBorum
 */
@Path("createuser")
public class CreateUser {

    @Context
    private UriInfo context;
    private Gson gson;
    private UserFacade uf;

    public CreateUser() {
        gson = new Gson();
        uf = new UserFacade();
    }
    
    @GET
    @Produces
    public String getJson() {
        return "it works";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String putJson(String content) {
        User u = gson.fromJson(content, User.class);
        Role userRole = new Role("User");
        u.AddRole(userRole);
        if (uf.getUserByUserName(u.getUserName()) != null) {
            return "userName already exists";
        }
        try {
            u.setPassword(PasswordStorage.createHash(u.getPassword()));
        } catch (PasswordStorage.CannotPerformOperationException ex) {
            Logger.getLogger(CreateUser.class.getName()).log(Level.SEVERE, null, ex);
            return "password storage error";
        }
        //check if user already exist
        uf.persistUser(u);
        return "ok"; //return json instead?
    }
}
