/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import deployment.ServerDeployment;
import entity.Booking;
import entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Kristian Nielsen
 */
public class BookingFacade {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(ServerDeployment.PU_NAME);
    
    
//    public Booking addBooking(Booking booking) {
//        EntityManager em = emf.createEntityManager();
//        Booking res = null;
//        try{
//            em.getTransaction().begin();
//            em.persist(booking);
//            em.getTransaction().commit();
//        }finally{
//            res = em.find(Booking.class, booking.getBookingID());
//            em.close();
//        }
//        return res;
//    }

    
//    public Booking getBooking(int bookingID) {
//        EntityManager em = emf.createEntityManager();
//        Booking res = null;
//        try{
//            res = em.find(Booking.class, bookingID);
//        }finally{
//            em.close();
//        }
//        return res;
//    }

    
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
    
    public void addBooking(User u, Booking b) {
        EntityManager em = emf.createEntityManager();
        try{
            u.addBooking(b);
            em.getTransaction().begin();
            em.merge(u);
            em.getTransaction().commit();
        }catch(Exception e){
            System.out.println("Error adding");
            System.out.println("Booking: " + b.getFlightNumber() + ", " + b.getOrigin() + " to " + b.getDestination());
            System.out.println("To User: " + u.getUserName());
            e.printStackTrace();
        }
        finally{
            em.close();
        }
    }
    
    public boolean removeBooking(String id) {
        EntityManager em = emf.createEntityManager();
        Long bookingID = Long.parseLong(id);
        Booking b = em.find(Booking.class, bookingID);
        if (b == null) {
            System.out.println("Could not find booking");
            return false;
        }
        User u = b.getUser();
        try{
            em.getTransaction().begin();
            u.removeBooking(b);
            em.remove(b);
            em.getTransaction().commit();
        }catch(Exception e){
            System.out.println("Error Deleting");
            System.out.println("Booking: " + b.getFlightNumber() + ", " + b.getOrigin() + " to " + b.getDestination());
            e.printStackTrace();
            return false;
        }
        finally{
            em.close();
        }
        return true;
    }
    
//    public List<Booking> getBookingsByFlight(int flightID) {
//        EntityManager em = emf.createEntityManager();
//        List<Booking> res = null;
//        try{
//            res = em.createNamedQuery("Booking.FindByFlightID").setParameter("flightID", flightID).getResultList();
//        }finally{
//            em.close();
//        }
//        return res;
//    }

    
//    public boolean deleteBooking(int bookingID) {
//        EntityManager em = emf.createEntityManager();
//        boolean res = false;
//        try{
//            em.remove(em.find(Booking.class, bookingID));
//            if(em.find(Booking.class, bookingID) == null) res = true;
//        }finally{
//            em.close();
//        }
//        return res;
//    }
    
}
