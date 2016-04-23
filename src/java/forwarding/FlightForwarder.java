/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forwarding;

import com.google.gson.Gson;
import deployment.ServerDeployment;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Kristian Nielsen
 */
public class FlightForwarder {
    
    Gson gson = new Gson();
    
    ExecutorService ex = Executors.newCachedThreadPool();
    
    public String getFlights(String content){
//        for (int i = 0; i < ServerDeployment.AIRLINE_URLS.length; i++) {
//            
//        }
        return "test";
    }
    
    Runnable r = new Runnable() {

        @Override
        public void run() {
            
        }
    };
}
