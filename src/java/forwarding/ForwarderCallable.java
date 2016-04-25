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
public class ForwarderCallable implements Callable {
    
    private String url;
    private String body;
    
    protected ForwarderCallable(String url, String body){
        this.url = url;
        this.body = body;
    }
    
    
    @Override
    public Object call() throws Exception {
        return forwardRequest(url, body);
    }
    
    
    private String forwardRequest(String urlString, String body) {
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
