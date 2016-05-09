/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Kristian Nielsen
 */
@NamedQueries({
    @NamedQuery(name="Search.FindAll", query="Select s from SearchEntity s"),
    @NamedQuery(name="Search.FindByDate", query="Select s from SearchEntity s where s.travelDate = :travelDate"),
    @NamedQuery(name="Search.FindByOrigin", query="Select s from SearchEntity s where s.origin = :origin"),
    @NamedQuery(name="Search.FindByDestination", query="Select s from SearchEntity s where s.destination = :destination")
})

@Entity
public class SearchEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long searchID;
    private String searchDate;
    private String travelDate;
    private String origin;
    private String destination;
    private int numberOfSeats;

    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (searchID != null ? searchID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the searchID fields are not set
        if (!(object instanceof SearchEntity)) {
            return false;
        }
        SearchEntity other = (SearchEntity) object;
        if ((this.searchID == null && other.searchID != null) || (this.searchID != null && !this.searchID.equals(other.searchID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Search[ id=" + searchID + " ]";
    }
    
    

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Long getSearchID() {
        return searchID;
    }

    public void setSearchID(Long searchID) {
        this.searchID = searchID;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }
    
}
