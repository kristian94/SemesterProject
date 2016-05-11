/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import facade.AirlineFacade;
import facade.UserFacade;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.NoResultException;

/**
 *
 * @author Kristian
 */
public class JsonValidator {

    AirlineFacade af = new AirlineFacade();
    UserFacade uf = new UserFacade();
    JsonParser parser = new JsonParser();
    DateFormat df;
    DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static void main(String[] args) {
        JsonValidator jv = new JsonValidator();

        JsonObject booking = new JsonObject();
        JsonArray passengers = new JsonArray();
        JsonObject p1 = new JsonObject();
        JsonObject p2 = new JsonObject();

        p1.addProperty("firstName", "Bob");
        p1.addProperty("lastName", "Ross");
        p2.addProperty("firstName", "Jane");
        p2.addProperty("lastName", "Ross");

        passengers.add(p1);
        passengers.add(p2);

        booking.addProperty("airline", "Bonier");
        booking.addProperty("flightID", "345871472357924");
        booking.addProperty("numberOfSeats", 2);
        booking.addProperty("userName", "user");
        booking.addProperty("reservePhone", "1231231");
        booking.addProperty("reserveeEmail", "mail@mail.com");
        booking.add("passengers", passengers);

        System.out.println(jv.validateBookingRequest(booking.toString()));
    }

    // Returns an empty String (not null) if invalid FlightRequest
    public String validateFlightRequest(String json) {
        try {
            JsonObject input = (JsonObject) parser.parse(json);
            JsonObject output = new JsonObject();
            boolean isValid = true;

            String origin = input.get("origin").getAsString();
            if (isValidIATA(origin)) {
                output.addProperty("origin", origin);
            } else {
                isValid = false;
            }

            String date = input.get("date").getAsString();
            if (isValidDate(date)) {
                output.addProperty("date", date);
            } else {
                isValid = false;
            }

            int numberOfSeats = input.get("numberOfSeats").getAsInt();
            if (isValidNOS(numberOfSeats)) {
                output.addProperty("numberOfSeats", numberOfSeats);
            } else {
                isValid = false;
            }

            try {
                String destination = input.get("destination").getAsString();
                if (isValidIATA(destination) && !destination.equals(origin)) {
                    output.addProperty("destination", destination);
                } else {
                    isValid = false;
                }
            } catch (NullPointerException e) {
                System.out.println("No Destination. Proceeding...");
            }

            if (isValid) {
                System.out.println("Flight request validated");
                return output.toString();
            }
            return "";
        } catch (Exception e) {
            System.out.println("Flight Request validation failed");
            e.printStackTrace();
            return "";
        }
    }

    public String validateBookingRequest(String json) {
        try {
            JsonObject input = (JsonObject) parser.parse(json);
            JsonObject output = new JsonObject();
            boolean isValid = true;

            String airline = input.get("airline").getAsString();
            if (isValidAirline(airline)) {
                output.addProperty("airline", airline);
            } else {
                isValid = false;
            }

            String flightID = input.get("flightID").getAsString();
            if (isValidFlightID(flightID)) {
                output.addProperty("flightID", flightID);
            } else {
                isValid = false;
            }

            int numberOfSeats = input.get("numberOfSeats").getAsInt();
            if (isValidNOS(numberOfSeats)) {
                output.addProperty("numberOfSeats", numberOfSeats);
            } else {
                isValid = false;
            }

            String userName = input.get("userName").getAsString();
            if (isValidExistingUserName(userName)) {
                output.addProperty("userName", userName);
            } else {
                isValid = false;
            }

            String reservePhone = input.get("reservePhone").getAsString();
            if (isValidPhone(reservePhone)) {
                output.addProperty("reservePhone", reservePhone);
            } else {
                isValid = false;
            }

            String reserveeEmail = input.get("reserveeEmail").getAsString();
            if (isValidEmail(reserveeEmail)) {
                output.addProperty("reserveeEmail", reserveeEmail);
            } else {
                isValid = false;
            }

            JsonArray passengers = input.get("passengers").getAsJsonArray();
            if (isValidPassengerArray(passengers)) {
                JsonArray validatedArray = new JsonArray();
                for (JsonElement j : passengers) {
                    JsonObject jo = j.getAsJsonObject();
                    jo = validatePassenger(jo);
                    validatedArray.add(jo);
                }
                output.add("passengers", validatedArray);
            } else {
                isValid = false;
            }

            if (isValid) {
                System.out.println("Booking validated");
                return output.toString();
            } else {
                return "";
            }
        } catch (Exception e) {
            System.out.println("Booking validation failed");
            e.printStackTrace();
            return "";
        }
    }

    public String validateUser(String content) {
        try {
            boolean isValid = true;
            JsonObject input = (JsonObject) parser.parse(content);
            JsonObject output = new JsonObject();

            String userName = input.get("userName").getAsString();
            String firstName = input.get("firstName").getAsString();
            String lastName = input.get("lastName").getAsString();
            String phone = input.get("phone").getAsString();
            String email = input.get("email").getAsString();
            String password = input.get("password").getAsString();

            if (isValidNewUserName(userName)) {
                output.addProperty("userName", userName);
            } else {
                isValid = false;
            }

            if (isValidName(firstName)) {
                output.addProperty("firstName", firstName);
            } else {
                isValid = false;
            }

            if (isValidName(lastName)) {
                output.addProperty("lastName", lastName);
            } else {
                isValid = false;
            }

            if (isValidPhone(phone)) {
                output.addProperty("phone", phone);
            } else {
                isValid = false;
            }

            if (isValidEmail(email)) {
                output.addProperty("email", email);
            } else {
                isValid = false;
            }

            if (isValidPassword(password)) {
                output.addProperty("password", password);
            } else {
                isValid = false;
            }

            if (!isValid) {
                System.out.println("Validation failed");
                return "";
            }

            System.out.println("User validated");
            System.out.println(output.toString());
            return output.toString();
        } catch(NullPointerException npe){
            System.out.println("NullPointer thrown - input missing");
            return "";
        } 
        
        catch (Exception e) {
            System.out.println("Validation failed (an exception was thrown)");
            System.out.println(e.getMessage());
            return "";
        }

    }

    private boolean isValidIATA(String IATA) {
        boolean isValid = true;
        if (IATA.length() != 3) {
            return false;
        }
        System.out.println("IATA valid: " + isValid);
        return isValid;
    }

    private boolean isValidNOS(int nos) {
        boolean isValid = true;
        if (nos < 1) {
            isValid = false;
        }

        System.out.println("NOS valid: " + isValid);
        return isValid;
    }

    private boolean isValidDate(String date) {
        boolean isValid = true;
        try {

            Date d = sdfISO.parse(date);
            if (d.before(getYesterdaysDate())) {
                isValid = false;
            }

        } catch (ParseException e) {
            isValid = false;
        }
        System.out.println("Date valid: " + isValid);
        return isValid;
    }

    private boolean isValidAirline(String airline) {
        boolean isValid = true;
        try {
            af.getAirlineByName(airline);
        } catch (NoResultException nre) {

            isValid = false;
        }
        System.out.println("Airline valid: " + isValid);
        return isValid;

//        boolean isValid = true;
//        if (af.findAirlineByAirlineName(airline) == null) {
//            isValid = false;
//        }
//        System.out.println("Airline valid: " + isValid);
//        return isValid;
    }

    private boolean isValidFlightID(String flightID) {
        boolean isValid = true;
        if (flightID.length() < 1) {
            isValid = false;
        }
        System.out.println("FlightID valid: " + isValid);
        return isValid;
    }

    private boolean isValidNewUserName(String userName) {
        boolean isValid = true;
        if (userName.length() < 1) {
            isValid = false;
        }
        System.out.println("UserName valid: " + isValid);
        return isValid;
    }

    private boolean isValidExistingUserName(String userName) {
        boolean isValid = true;
        if (uf.getUserByUserName(userName) == null) {
            isValid = false;
        }
        System.out.println("userName valid: " + isValid);
        return isValid;
    }

    private boolean isValidPhone(String phone) {
        boolean isValid = true;
        if (phone.length() < 1) {
            isValid = false;
        }
        System.out.println("Phone valid: " + isValid);
        return isValid;
    }

    private boolean isValidEmail(String email) {
        boolean isValid = true;
        if (!email.contains("@")) {
            isValid = false;
        }
        if (!email.contains(".")) {
            isValid = false;
        }
        System.out.println("Email valid: " + isValid);
        return isValid;
    }

    private boolean isValidName(String name) {
        boolean isValid = true;
        if (name.length() < 1) {
            isValid = false;
        }
        System.out.println("Name valid: " + isValid);
        return isValid;
    }

    private boolean isValidPassengerArray(JsonArray passengers) {
        boolean isValid = true;
        if (passengers.size() == 0) {
            isValid = false;
        }
        System.out.println("Passengers(array) valid: " + isValid);
        return isValid;
    }

    private boolean isValidPassword(String password) {
        boolean isValid = true;
        if (password.length() < 7) {
            isValid = false;
        }
        System.out.println("Password valid: " + isValid);
        return isValid;
    }

    private JsonObject validatePassenger(JsonObject input) {
        JsonObject output = new JsonObject();
        String firstName = input.get("firstName").getAsString();
        String lastName = input.get("lastName").getAsString();

        output.addProperty("firstName", firstName);
        output.addProperty("lastName", lastName);

        return output;
    }

    private Date getYesterdaysDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        System.out.println(c.getTime());
        return c.getTime();
    }

}
