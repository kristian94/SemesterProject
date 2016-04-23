/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Kristian Nielsen
 */
@NamedQueries({
    @NamedQuery(name="Booking.FindAll", query="Select b from Booking b"),
    @NamedQuery(name="Booking.FindByFlightID", query="Select b from Booking b where b.flightID = :flightID")
})

@Entity
public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingID;
    private int flightID;
    @Temporal(TemporalType.TIMESTAMP)
    private Date travelDate;
    private String origin;
    private String destination;
    private String reserveeName;
    private String reserveePhone;
    private String reserveeEmail;
    private int flightTimeInMinutes;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Passenger> passengers;
    
    


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookingID != null ? bookingID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the bookingID fields are not set
        if (!(object instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) object;
        if ((this.bookingID == null && other.bookingID != null) || (this.bookingID != null && !this.bookingID.equals(other.bookingID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Booking[ id=" + bookingID + " ]";
    }

    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
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

    public String getReserveeName() {
        return reserveeName;
    }

    public void setReserveeName(String reserveeName) {
        this.reserveeName = reserveeName;
    }

    public String getReserveePhone() {
        return reserveePhone;
    }

    public void setReserveePhone(String reserveePhone) {
        this.reserveePhone = reserveePhone;
    }

    public String getReserveeEmail() {
        return reserveeEmail;
    }

    public void setReserveeEmail(String reserveeEmail) {
        this.reserveeEmail = reserveeEmail;
    }

    public int getFlightTimeInMinutes() {
        return flightTimeInMinutes;
    }

    public void setFlightTimeInMinutes(int flightTimeInMinutes) {
        this.flightTimeInMinutes = flightTimeInMinutes;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }
    
    public void addPassenger(Passenger passenger){
        this.passengers.add(passenger);
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Long getBookingID() {
        return bookingID;
    }

    public void setBookingID(Long bookingID) {
        this.bookingID = bookingID;
    }
    
}
