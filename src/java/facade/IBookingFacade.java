/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Booking;
import java.util.List;

/**
 *
 * @author Kristian Nielsen
 */
public interface IBookingFacade {
    public Booking addBooking(Booking booking);
    public Booking getBooking(int bookingID);
    public List<Booking> getBookings();
    public List<Booking> getBookingsByFlight(int flightID);
    public boolean deleteBooking(int bookingID);
}
