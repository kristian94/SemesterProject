/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Booking;
import entity.Passenger;
import entity.SearchEntity;
import entity.User;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kristian Nielsen
 */
public class JsonHelper {

    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();

    public SearchEntity toSearch(String content) {

        JsonObject json = gson.fromJson(content, JsonObject.class);
        SearchEntity s = new SearchEntity();
        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        DateFormat df = DateFormat.getInstance();
        Calendar c = Calendar.getInstance();

        s.setOrigin(json.get("origin").getAsString());
        try {
            String dest = json.get("destination").getAsString();
            if (dest != null) {
                s.setDestination(dest);
            }
        } catch (NullPointerException ne) {
            System.out.println(ne);
        }
        s.setNumberOfSeats(json.get("numberOfSeats").getAsInt());

        try {
            s.setTravelDate(sdfISO.parse(json.get("date").getAsString()));
        } catch (ParseException ex) {
            Logger.getLogger(JsonHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        s.setSearchDate(c.getTime());

        return s;
    }

    public Booking jsonToBooking(String content) {
        Booking b = new Booking();
        JsonObject json = (JsonObject) parser.parse(content);
        b.setTravelDate(json.get("date").getAsString());
        b.setOrigin(json.get("origin").getAsString());
        b.setDestination(json.get("destination").getAsString());
        b.setFlightTimeInMinutes(json.get("flightTime").getAsInt());
        b.setReserveeName(json.get("reserveeName").getAsString());
        b.setFlightNumber(json.get("flightNumber").getAsString());
        JsonArray passengers = json.get("passengers").getAsJsonArray();
        for (JsonElement e : passengers) {
            Passenger p = new Passenger();
            p.setFirstName(e.getAsJsonObject().get("firstName").getAsString());
            p.setLastName(e.getAsJsonObject().get("lastName").getAsString());
            b.addPassenger(p);
        }
        System.out.println("Printing Booking as JsonString:");
        System.out.println(gson.toJson(b));
        return b;
    }

    public String getUserNameFromJson(String content) {
        JsonObject jsonObject = (JsonObject) parser.parse(content);
        return jsonObject.get("userName").getAsString();
    }
    
    public String addReserveeName(String content, User u) {
        JsonObject json = (JsonObject) parser.parse(content);
        String fullName = u.getFirstName() + " " + u.getLastName();
        json.addProperty("reserveeName", fullName);
        return json.toString();
    }

    public String bookingListToJson(List<Booking> bookings) {
        JsonArray bookingArray = new JsonArray();
        for(Booking b: bookings){
            bookingArray.add(bookingToJsonObject(b));
            
        }
        return bookingArray.toString();
    }
    
    private JsonObject bookingToJsonObject(Booking b) {
        JsonArray passengerArray = new JsonArray();
        for(Passenger p: b.getPassengers()){
            JsonObject passengerJson = new JsonObject();
            passengerJson.addProperty("firstName", p.getFirstName());
            passengerJson.addProperty("lastName", p.getLastName());
            passengerArray.add(passengerJson);
        }
        
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("date", b.getTravelDate());
        jsonObject.addProperty("destination", b.getDestination());
        jsonObject.addProperty("flightNumber", b.getFlightNumber());
        jsonObject.addProperty("flightTime", b.getFlightTimeInMinutes());
        jsonObject.addProperty("origin", b.getOrigin());
        jsonObject.addProperty("reserveeName", b.getReserveeName());
        jsonObject.addProperty("userName", b.getUser().getUserName());
        jsonObject.add("passengers", passengerArray);
        
        return jsonObject;
    }

}
