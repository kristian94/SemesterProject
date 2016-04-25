/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.SearchEntity;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kristian Nielsen
 */
public class JsonHelper {

    Gson gson = new Gson();
    
    public SearchEntity toSearch(String content) {
        
        JsonObject json = gson.fromJson(content, JsonObject.class);
        SearchEntity s = new SearchEntity();
        DateFormat df = DateFormat.getInstance();
        Calendar c = Calendar.getInstance();
        
        s.setOrigin(json.get("origin").getAsString());
        
        String dest = json.get("destination").getAsString();
        if(dest != null) s.setDestination(dest);
        
        s.setNumberOfSeats(json.get("numberOfSeats").getAsInt());
        
        try {
            s.setTravelDate(df.parse(json.get("date").getAsString()));
        } catch (ParseException ex) {
            Logger.getLogger(JsonHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        s.setSearchDate(c.getTime());

        return s;
    }
    
}
