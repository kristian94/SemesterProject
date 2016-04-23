/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import deployment.ServerDeployment;
import entity.Booking;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Kristian Nielsen
 */
public class BookingFacade implements IBookingFacade {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(ServerDeployment.PU_NAME);
    
    @Override
    public Booking addBooking(Booking booking) {
        EntityManager em = emf.createEntityManager();
        Booking res = null;
        try{
            em.getTransaction().begin();
            em.persist(booking);
            em.getTransaction().commit();
        }finally{
            res = em.find(Booking.class, booking.getBookingID());
            em.close();
        }
        return res;
    }

    @Override
    public Booking getBooking(int bookingID) {
        EntityManager em = emf.createEntityManager();
        Booking res = null;
        try{
            res = em.find(Booking.class, bookingID);
        }finally{
            em.close();
        }
        return res;
    }

    @Override
    public List<Booking> getBookings() {
        EntityManager em = emf.createEntityManager();
        List<Booking> res = null;
        try{
            res = em.createNamedQuery("Booking.FindAll").getResultList();
        }finally{
            em.close();
        }
        return res;
    }

    @Override
    public List<Booking> getBookingsByFlight(int flightID) {
        EntityManager em = emf.createEntityManager();
        List<Booking> res = null;
        try{
            res = em.createNamedQuery("Booking.FindByFlightID").setParameter("flightID", flightID).getResultList();
        }finally{
            em.close();
        }
        return res;
    }

    @Override
    public boolean deleteBooking(int bookingID) {
        EntityManager em = emf.createEntityManager();
        boolean res = false;
        try{
            em.remove(em.find(Booking.class, bookingID));
            if(em.find(Booking.class, bookingID) == null) res = true;
        }finally{
            em.close();
        }
        return res;
    }
    
}
