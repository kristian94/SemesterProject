/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forwarding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
            case "get":
                return forwardGetRequest(url);
                
            case "post":
                return forwardPostRequest(url, body);
        }
        return null;
    }
    
    
    private String forwardGetRequest(String urlString) {
        String result = null;
        try {
            URLConnection urlc = new URL(urlString).openConnection();
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
    
    private String forwardPostRequest(String urlString, String body) {
        String result = null;
        try {
            URLConnection urlc = new URL(urlString).openConnection();
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
