/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import forwarding.RequestForwarder;
import java.util.Calendar;

/**
 *
 * @author Kristian Nielsen
 */
public class Tester {
    public static void main(String[] args) {
        Gson gson = new Gson();
//        String jsonText = "{\"name\":\"Phil\"}";
//        JsonObject json = gson.fromJson(jsonText, JsonObject.class);
//        System.out.println(json.get("name"));
//        
        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime());
        RequestForwarder rf = new RequestForwarder();
        
        String date = "2016-04-06T00:00:00.000Z";
        String origin = "CPH";
        int tickets = 2;
        
        JsonObject json = new JsonObject();
        json.add("date", new JsonPrimitive(date));
        json.add("origin", new JsonPrimitive(origin));
        json.add("numberOfSeats", new JsonPrimitive(tickets));
        
        System.out.println(json.toString());
        
        System.out.println(rf.flightRequest(json.toString()));
        
    }
}
