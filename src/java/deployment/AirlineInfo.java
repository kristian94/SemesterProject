/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deployment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kristian Nielsen
 */
public class AirlineInfo {
    
    private String name;
    private String url;
    
    public AirlineInfo(String name, String url){
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
