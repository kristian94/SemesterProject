/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forwarding;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import deployment.ServerDeployment;
import entity.Airline;
import facade.AirlineFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kristian Nielsen
 */
public class RequestForwarder {

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").
            setPrettyPrinting().create();

    private JsonParser parser = new JsonParser();
    private AirlineFacade af = new AirlineFacade();
    private ExecutorService ex = Executors.newCachedThreadPool();

    public JsonArray flightRequest(String content) {

        String urlEnd = buildFlightUrl(content);
        JsonArray array = new JsonArray();
        List<Future<String>> results = new ArrayList();
        List<Airline> airlines = af.getAirlines();
        List<String> airlineNames = new ArrayList();
        List<String> airlineUrls = new ArrayList();

        System.out.println(urlEnd + "endd");

        for (Airline a : airlines) {

            airlineUrls.add(a.getUrl());
            String fullUrl = a.getUrl() + urlEnd;
            if (fullUrl.contains("angularairline")) {
                System.out.println("Angular Airline Detected, fixing url");
                fullUrl = fullUrl.replaceAll("flights", "flightinfo");
            }
            try {
                System.out.println("Fetching data from: " + fullUrl);

                results.add(ex.submit(new ForwarderCallable(fullUrl, content, "GET")));
            } catch (Exception ex) {
                Logger.getLogger(RequestForwarder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (Future<String> fut : results) {
            try {
                if (!fut.get().equals("")) {
//                    System.out.println("fut.get yields: " + fut.get());
                    JsonObject json = parser.parse(fut.get()).getAsJsonObject();
//                    System.out.println("json looks like this: " + gson.toJson(json));
                    String airline = json.get("airline").getAsString();
                    airlineNames.add(airline);
                    try {
                        JsonArray flightArray = json.get("flights").getAsJsonArray();
                        for (JsonElement e : flightArray) {
                            JsonObject jo = e.getAsJsonObject();
                            jo.addProperty("airline", airline);
                            array.add(jo);
                        }
                    } catch (Exception e) {
                        System.out.println("Array empty");

                    }

                }

            } catch (InterruptedException ex) {
                Logger.getLogger(RequestForwarder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(RequestForwarder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        updateAirlines(airlineUrls, airlineNames);
        System.out.println("Array looks like this:" + array);
        return array;
    }

    public JsonObject bookingRequest(String content) {
        try {
            StringBuilder fullUrl = new StringBuilder();
            JsonObject json = (JsonObject) parser.parse(content);
            Airline a = af.getAirlineByName(json.get("airline").getAsString());

            String reservation = "/reservation";

            fullUrl.append(a.getUrl());
            if (a.getUrl().contains("angularairline")) {
                System.out.println("Angular Airline Detected, fixing url");
                reservation = "/flightreservation";
                fullUrl.append(reservation);
            } else {
                fullUrl.append(reservation);
                fullUrl.append(("/" + json.get("flightID").getAsString()));

            }


            Future<String> res = ex.submit(new ForwarderCallable(fullUrl.toString(), content, "POST"));
            return parser.parse(res.get()).getAsJsonObject();
        } catch (InterruptedException ex) {
            Logger.getLogger(RequestForwarder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(RequestForwarder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private String buildFlightUrl(String json) {

        StringBuilder urlEnd = new StringBuilder();
        JsonObject jsonOb = (JsonObject) parser.parse(json);
        urlEnd.append("/flights");
        urlEnd.append(("/" + jsonOb.get("origin").getAsString()));
        if (jsonOb.get("destination") != null) {
            urlEnd.append("/" + jsonOb.get("destination").getAsString());
        }

        urlEnd.append(("/" + jsonOb.get("date").getAsString().replace("\"", ""))); // .replace() - the date is sent with quotes for some reason
        System.out.println("Her");
        urlEnd.append(("/" + jsonOb.get("numberOfSeats").getAsString()));

        return urlEnd.toString();
    }

    private void updateAirlines(List<String> airlineUrls, List<String> airlineNames) {
        System.out.println("Updating Airlines (names/urls)");
        int i = 0;
        while (i < airlineUrls.size() && i < airlineNames.size()) {
            af.updateAirline(airlineNames.get(i), airlineUrls.get(i));
            i++;
        }
    }

}
