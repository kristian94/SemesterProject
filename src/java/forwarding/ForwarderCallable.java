/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forwarding;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kristian Nielsen
 */
public class ForwarderCallable implements Callable<String> {
    
    private String url;
    private String body;
    private String method;
    
    protected ForwarderCallable(String url, String body, String method){
        this.url = url;
        this.body = body;
        this.method = method;
    }
    
    
    @Override
    public String call() throws Exception {
        switch(method){
            case "GET":
//                return forwardGetRequest(url);
                return unirestGet();
            case "POST":
//                return forwardPostRequest();
                return unirestPost();
        }
        return null;
    }
    
    public String unirestGet(){
        
        try {
            HttpResponse<JsonNode> response = Unirest.get(url).asJson();
            int code = response.getStatus();
            if(code < 400 && code >= 200){
                return response.getBody().toString();
            }
            else{
                return response.getStatusText();
            }
        } catch (UnirestException ex) {
            Logger.getLogger(ForwarderCallable.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return "";
    }
    
    public String unirestPost(){
        System.out.println("Posting to " + url);
        System.out.println("Body: " + body);
        Map<String, String> headers = new HashMap();
        headers.put("content-type", "application/json");
//        headers.put("method", "POST");
        headers.put("accept", "application/json");
        try {
            HttpResponse<JsonNode> response = Unirest.post(url)
                    .headers(headers)
                    .body(body).asJson();
            int code = response.getStatus();
            if(code < 400 && code >= 200){
                
                return response.getBody().toString();
            }
            else{
                return response.getBody().toString();
//                return response.getStatusText();
            }
            
        } catch (UnirestException ex) {
            Logger.getLogger(ForwarderCallable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    private String forwardGetRequest() {
        String result = null;
        try {
            
            
            
            
            
            URLConnection urlc = new URL(url).openConnection();
            
            InputStream is = urlc.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(RequestForwarder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RequestForwarder.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        return result;
    }
    
    private String forwardPostRequest() {
        String result = null;
        try {
            URLConnection urlc = new URL(url).openConnection();
            urlc.setDoOutput(true);
            OutputStream os = urlc.getOutputStream();
            os.write(body.getBytes());
            InputStream is = urlc.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(RequestForwarder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RequestForwarder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    

    
}
