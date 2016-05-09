/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Kristian Nielsen
 */
public class ResponseBuilder {
    
    // Currently does nothing
    
    public static Response jsonToResponse(JsonObject json){
        
        Status s = codeToStatus(json.get("code").getAsInt());
        String body = json.get("body").getAsString();
        
        return Response.status(s).entity(body).build();
    }
    
    private static Status codeToStatus(int code) {
        Status s = Response.Status.OK;
        
        switch(code){
            case 404:
                s = Response.Status.NOT_FOUND;
                break;
            case 500:
                s = Response.Status.INTERNAL_SERVER_ERROR;
                break;
        }
        
        
        return s;
    }
    
}
