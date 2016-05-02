/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Kristian Nielsen
 */
@NamedQueries({
    @NamedQuery(name="Airline.FindAll", query="Select a from Airline a"),
    @NamedQuery(name="Airline.FindByUrl", query="Select a from Airline a where a.url = :url"),
    @NamedQuery(name="Airline.FindByName", query="Select a from Airline a where a.name = :name")
})

@Entity
public class Airline implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String url;
    private String name;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
