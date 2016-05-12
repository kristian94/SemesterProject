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
import com.google.gson.stream.MalformedJsonException;
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

    private AirlineFacade af = new AirlineFacade();
    private UserFacade uf = new UserFacade();
    private JsonParser parser = new JsonParser();
    private DateFormat df;
    private DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static JsonArray errors;
    private static boolean hasDest;
    private static final String[] flightProperties = {"origin", "destination", "date", "numberOfSeats"};
    private static final String[] bookingProperties = {"airline", "flightID", "reservePhone", "reserveeEmail", "passengers"};
    private static final String[] userProperties = {"firstName", "lastName", "userName", "email", "phone", "password"};
    private static final String[] passengerProperties = {"firstName", "lastName"};
    private static final char[] validPhoneChars = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '(', ')', '+'};

    public static void main(String[] args) {
        JsonValidator jv = new JsonValidator();

//        JsonObject flight = new JsonObject();
//        
//        flight.addProperty("origin", "CPH");
//        flight.addProperty("date", "2016-05-13T00:00:00.000Z");
//        flight.addProperty("numberOfSeats", 2);
//        
//        System.out.println(jv.validateFlightRequest(flight.toString()).toString());
//        JsonObject booking = new JsonObject();
//        JsonArray passengers = new JsonArray();
//        JsonObject p1 = new JsonObject();
//        JsonObject p2 = new JsonObject();
//
//        p1.addProperty("firstName", "Bob");
//        p1.addProperty("lastName", "Ross");
//        p2.addProperty("firstName", "Jane");
//        p2.addProperty("lastName", "Ross");
//
//        passengers.add(p1);
//        passengers.add(p2);
//
//        booking.addProperty("airline", "Bonier");
//        booking.addProperty("flightID", "asdsa");
//        booking.addProperty("numberOfSeats", 1);
//        booking.addProperty("userName", "user");
//        booking.addProperty("reservePhone", "1231a3");
//        booking.addProperty("reserveeEmail", "mail@mail.com");
//        booking.add("passengers", passengers);
//
//        System.out.println(jv.validateBookingRequest(booking.toString()));
        JsonObject user = new JsonObject();

        user.addProperty("firstName", "Bob");
        user.addProperty("lastName", "Ross");
        user.addProperty("userName", "bobross2");
        user.addProperty("email", "bob@ross.dk");
        user.addProperty("phone", "1234-333");
        user.addProperty("password", "secretbob");

        System.out.println(jv.validateUser(user.toString()).toString());

    }

    private void reset() {
        errors = new JsonArray();
        hasDest = true;
    }

    private JsonObject addProperties(String[] propertyNames, JsonObject input) {
        JsonObject output = new JsonObject();

        for (String s : propertyNames) {
            try {
                if (s.equals("passengers")) {
                    output.add(s, input.get(s).getAsJsonArray());
                } else if (s.equals("numberOfSeats")) {
                    output.addProperty(s, input.get(s).getAsInt());
                } else {
                    output.addProperty(s, input.get(s).getAsString());
                }
            } catch (NullPointerException np) {
                if (s.equals("destination")) {
                    hasDest = false;
                } else {
                    errors.add("Property missing (" + s + ")");
                }
            } catch (Exception e) {
                errors.add("Error parsing property (" + s + ")");
            }

        }

        return output;
    }

    // Returns an empty String (not null) if invalid FlightRequest
    public JsonObject validateFlightRequest(String input) {
        reset();
        JsonObject result = new JsonObject();
        JsonObject output = addProperties(flightProperties, (JsonObject) parser.parse(input));

        if (errors.size() > 0) {
            result.add("flightRequest", output);
            result.add("errors", errors);
            return result;
        }

        validateIATA(output.get("origin").getAsString());
        validateDate(output.get("date").getAsString());
        validateNumberOfSeats(output.get("numberOfSeats").getAsInt());
        if (hasDest) {
            validateIATA(output.get("destination").getAsString());
        }

        result.add("flightRequest", output);
        result.add("errors", errors);

        return result;
    }

    public JsonObject validateBookingRequest(String input) {
        reset();
        JsonObject result = new JsonObject();
        JsonObject output = addProperties(bookingProperties, (JsonObject) parser.parse(input));
        
        if (errors.size() > 0) {
            result.add("booking", output);
            result.add("errors", errors);
            return result;
        }

        validateAirline(output.get("airline").getAsString());
        validateFlightID(output.get("flightID").getAsString());
        validatePhone(output.get("reservePhone").getAsString());
        validateEmail(output.get("reserveeEmail").getAsString());
        validatePassengerArray(output.get("passengers").getAsJsonArray());

        result.add("booking", output);
        result.add("errors", errors);

        return result;
    }

    public JsonObject validateUser(String input) {
        reset();
        JsonObject result = new JsonObject();
        JsonObject output = addProperties(userProperties, (JsonObject) parser.parse(input));

        if (errors.size() > 0) {
            result.add("user", output);
            result.add("errors", errors);
            return result;
        }

        validateName(output.get("firstName").getAsString());
        validateName(output.get("lastName").getAsString());
        validateNewUserName(output.get("userName").getAsString());
        validateEmail(output.get("email").getAsString());
        validatePhone(output.get("phone").getAsString());
        validatePassword(output.get("password").getAsString());

        result.add("user", output);
        result.add("errors", errors);

        return result;
    }

    private void validateIATA(String IATA) {
        if (IATA.length() == 0) {
            errors.add("Airport/IATA missing");
        } else if (IATA.length() != 3) {
            errors.add("Invalid Airport Code (" + IATA + ")");
        }
    }

    private void validateNumberOfSeats(int nos) {
        if (nos < 1) {
            errors.add("Ìnvalid number of seats (" + nos + ")");
        }
    }

    private void validateDate(String date) {
        try {

            Date d = sdfISO.parse(date);
            if (d.before(getYesterdaysDate())) {
                errors.add("Invalid date (" + date + "). Unfortunately, we do not support time travel");
            }
        } catch (ParseException e) {
            errors.add("Unparseable date (" + date + "). Make sure your date is in the correct format (yyyy-MM-dd'T'HH:mm:ss.SSS'Z')");
        }
    }

    private void validateAirline(String airline) {
        try {
            af.getAirlineByName(airline);
        } catch (NoResultException nre) {

            errors.add("The airline entered (" + airline + ") does not exist in our database");
        }
    }

    private void validateFlightID(String flightID) {
        if (flightID.length() < 1) {
            errors.add("flightID missing");
        }
    }

    private void validateNewUserName(String userName) {
        if (userName.length() < 1) {
            errors.add("Username missing");
        }
    }

    private void validateExistingUserName(String userName) {
        if (userName.length() < 1) {
            errors.add("Username missing");
        } else if (uf.getUserByUserName(userName) == null) {
            errors.add("User (" + userName + ") does not exist in our database. Make sure you input an existing user");
        }
    }

    private void validatePhone(String phone) {
        if (phone.length() < 1) {
            errors.add("Phone number missing");
        } else if (!phoneHasOnlyValidChars(phone)) {
            errors.add("Phone number (" + phone + ") contains invalid characters. Phone numbers can only contain: " + charArrayToString(validPhoneChars));
        }
    }

    private void validateEmail(String email) {
        if (email.length() == 0) {
            errors.add("Email missing");
        } else if (!email.contains("@")) {
            errors.add("Invalid email (" + email + "). An email needs to contain both of the following characters: '@' '.'");
        } else if (!email.contains(".")) {
            errors.add("Invalid email (" + email + "). An email needs to contain both of the following characters: '@' '.'");
        }
    }

    private void validateName(String name) {
        if (name.length() < 1) {
            errors.add("Name missing");
        }
    }

    private void validatePassengerName(String name) {
        if (name.length() < 1) {
            errors.add("Name missing (Passenger)");
        }
    }

    private void validatePassengerArray(JsonArray passengers) {

        if (passengers.size() == 0) {
            errors.add("No passengers given. Make sure you enter at least one passenger.");
        } else {
            for (JsonElement e : passengers) {
                validatePassenger(e.getAsJsonObject());
            }
        }
    }

    private void validatePassword(String password) {
        if (password.length() < 6) {
            errors.add("Password too short. Make sure the password you enter is at least 6 characters.");
        }
    }

    private JsonObject validatePassenger(JsonObject input) {
        JsonObject output = addProperties(passengerProperties, input);

        if (errors.size() == 0) {
            validatePassengerName(output.get("firstName").getAsString());
            validatePassengerName(output.get("lastName").getAsString());
        }

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

    private boolean phoneHasOnlyValidChars(String phone) {

        for (int i = 0; i < phone.length(); i++) {
            boolean valid = false;
            char c = phone.charAt(i);
            for (char phoneChar : validPhoneChars) {
                if (c == phoneChar) {
                    valid = true;
                }
            }
            if (!valid) {
                return false;
            }
        }
        return true;
    }

    private String charArrayToString(char[] chars) {
        String result = "";
        for (int i = 0; i < chars.length; i++) {
            result += chars[i] + "  ";
        }
        return result;
    }

}
