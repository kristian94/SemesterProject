package facade;

import deployment.ServerDeployment;
import entity.Booking;
import entity.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import security.IUser;
import security.IUserFacade;
import security.PasswordStorage;

public class UserFacade implements IUserFacade {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(ServerDeployment.PU_NAME);

    public UserFacade() {

    }

    public void persistUser(User u) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(u);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public User getUserByUserName(String userName) {
        EntityManager em = emf.createEntityManager();
        User u = null;
        try{
            u = em.find(User.class, userName);
        }finally{
            em.close();
        }
        return u;
    }

    public List<User> getUsers() {
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("User.findAll").getResultList();
    }

    public User deleteUser(String id) {
        EntityManager em = emf.createEntityManager();
        User u = em.find(User.class, id);
        try {
            em.getTransaction().begin();
            em.remove(u);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return u;
    }
    /*
     Return the Roles if users could be authenticated, otherwise null
     */

    
    /*
     Return the Roles if users could be authenticated, otherwise null
     */
    public List<String> authenticateUser(String userName, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            User user = em.find(User.class, userName);
            if (user == null) {
                return null;
            }

            boolean authenticated;
            try {
                authenticated = PasswordStorage.verifyPassword(password, user.getPassword());
                return authenticated ? user.getRolesAsStrings() : null;
            } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException ex) {
                Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } finally {
            em.close();
        }
    }

    @Override
    public IUser getUserByUserId(String id) {
        EntityManager em = emf.createEntityManager();
        IUser u = null;
        try{
            u = em.find(User.class, id);
        }finally{
            em.close();
        }
        return u;
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

}
