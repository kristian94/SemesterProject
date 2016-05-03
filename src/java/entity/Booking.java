/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Booking.FindAll", query = "Select b from Booking b"),
    @NamedQuery(name = "Booking.FindByFlightNumber", query = "Select b from Booking b where b.flightNumber = :flightNumber"),
    @NamedQuery(name = "Booking.FindByReserveeName", query = "Select b from Booking b where b.reserveeName = :reserveeName")
})

@Entity
public class Booking implements Serializable {
    @ManyToOne
    private User user;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOKING_ID")
    private Long bookingID;
    private int flightNumber;
    @Temporal(TemporalType.TIMESTAMP)
    private Date travelDate;
    private String origin;
    private String destination;
    private String reserveeName;
    private int flightTimeInMinutes;
    
//    @OneToMany(cascade = CascadeType.PERSIST)
    @ElementCollection
    @CollectionTable(
            name = "PASSENGER",
            joinColumns = @JoinColumn(name = "BOOKING_ID")
    )
    private List<Passenger> passengers = new ArrayList();

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

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
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

    public int getFlightTimeInMinutes() {
        return flightTimeInMinutes;
    }

    public void setFlightTimeInMinutes(int flightTimeInMinutes) {
        this.flightTimeInMinutes = flightTimeInMinutes;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void addPassenger(Passenger passenger) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
