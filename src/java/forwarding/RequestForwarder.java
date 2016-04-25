/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forwarding;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
        JsonObject json = gson.fromJson(content, JsonObject.class);
        urlEnd.append(("/" + json.get("origin")));
        String dest = "/" + json.get("destination").getAsString();
        if(dest != null) urlEnd.append(dest);
        urlEnd.append(("/" + json.get("date").getAsString()));
        urlEnd.append(("/" + json.get("tickets").getAsString()));
        
        
        
        StringBuilder sb = new StringBuilder();
        List<Future> results = new ArrayList();
        for(String url: ServerDeployment.AIRLINE_URLS){
            String fullUrl = url + urlEnd;
            try {
                results.add(ex.submit(new ForwarderCallable(fullUrl, content)));
            } catch (Exception ex) {
                Logger.getLogger(RequestForwarder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(Future fut: results){
            try {
                sb.append(fut.get());
            } catch (InterruptedException ex) {
                Logger.getLogger(RequestForwarder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(RequestForwarder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sb.toString();
    }
    
    
    

    

}
