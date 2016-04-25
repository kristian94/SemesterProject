/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.Calendar;

/**
 *
 * @author Kristian Nielsen
 */
public class Tester {
    public static void main(String[] args) {
//        Gson gson = new Gson();
//        String jsonText = "{\"name\":\"Phil\"}";
//        JsonObject json = gson.fromJson(jsonText, JsonObject.class);
//        System.out.println(json.get("name"));
//        
        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime());
        
    }
}
