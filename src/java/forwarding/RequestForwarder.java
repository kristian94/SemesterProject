/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forwarding;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import deployment.ServerDeployment;
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

    Gson gson = new Gson();
    JsonParser parser = new JsonParser();
    
    ExecutorService ex = Executors.newCachedThreadPool();
    
    public static void main(String[] args) {
        RequestForwarder rf = new RequestForwarder();
        rf.test();
    }

    public void test() {
        String text = "Some text";
        System.out.println("Input: " + text);
        String url = "http://localhost:8080/SemesterProject/api/test";
//        String output = forwardRequest(url, text);
//        System.out.println("Output: " + output);

    }
    
    public String flightRequest(String content){
        StringBuilder urlEnd = new StringBuilder();
        JsonObject json = (JsonObject) parser.parse(content);
        System.out.println(json.toString());
        urlEnd.append(("/" + json.get("origin").getAsString()));
        try{
            String dest = "/" + json.get("destination").getAsString();
        if(dest != null) urlEnd.append(dest);
        }catch(NullPointerException ne){
            
        }
        urlEnd.append(("/" + json.get("date").getAsString()));
        urlEnd.append(("/" + json.get("numberOfSeats").getAsString()));
        
        
        
        StringBuilder sb = new StringBuilder();
        JsonArray array = new JsonArray();
        List<Future<String>> results = new ArrayList();
        for(String url: ServerDeployment.AIRLINE_URLS){
            String fullUrl = url + urlEnd;
            try {
                System.out.println(fullUrl);
                results.add(ex.submit(new ForwarderCallable(fullUrl, content, "get")));
            } catch (Exception ex) {
                Logger.getLogger(RequestForwarder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for(Future<String> fut: results){
            try {
                
                array.add(new JsonPrimitive(fut.get()));
            } catch (InterruptedException ex) {
                Logger.getLogger(RequestForwarder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(RequestForwarder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return array.toString();
    }
    
    
    

    

}
