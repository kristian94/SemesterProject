/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deployment;

import entity.Airline;
import entity.Role;
import entity.User;
import facade.AirlineFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import security.PasswordStorage;

/**
 *
 * @author Kristian Nielsen
 */
@WebListener
public class ServerDeployment implements ServletContextListener {

    public static String PU_NAME = "PU-Local";
    
    private AirlineFacade af = new AirlineFacade();
    
    private static String[] AIRLINE_NAMES = {"Angular something"};
    private static String[] AIRLINE_URLS = {"http://angularairline-plaul.rhcloud.com/api"};
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU_NAME);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Context Init");
        Map<String, String> env = System.getenv();
        if (env.keySet().contains("OPENSHIFT_MYSQL_DB_HOST")) {
            PU_NAME = "PU_OPENSHIFT";
        }
        try {
            ServletContext context = sce.getServletContext();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(ServerDeployment.PU_NAME);
            EntityManager em = emf.createEntityManager();
            //This flag is set in Web.xml -- Make sure to disable for a REAL system
//            boolean makeTestUsers = context.getInitParameter("makeTestUsers").toLowerCase().equals("true");
            boolean makeTestUsers = true;
            if (!makeTestUsers || (em.find(User.class, "user") != null && em.find(User.class, "admin") != null && em.find(User.class, "user_admin") != null)) {
                return;
            }
            Role userRole = new Role("User");
            Role adminRole = new Role("Admin");
            

            User user = new User("user", PasswordStorage.createHash("test"));
            User admin = new User("admin", PasswordStorage.createHash("test"));
            User both = new User("user_admin", PasswordStorage.createHash("test"));
            user.AddRole(userRole);
            admin.AddRole(adminRole);
            both.AddRole(userRole);
            both.AddRole(adminRole);

            try {
                em.getTransaction().begin();
                em.persist(userRole);
                em.persist(adminRole);

                em.persist(user);
                em.persist(admin);
                em.persist(both);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        } catch (PasswordStorage.CannotPerformOperationException ex) {
            Logger.getLogger(ServerDeployment.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateAirlineDB();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    private void updateAirlineDB() {
        System.out.println("Updating Airlines...");
        
            List<Airline> airlines = makeAirlines(AIRLINE_NAMES, AIRLINE_URLS);
            for (Airline a : airlines) {
                af.addAirlineNoOverwrite(a);
                
            }
            

    }

    private static List<Airline> makeAirlines(String[] names, String[] urls) {
        System.out.println("Making airlines...");
        List<Airline> res = new ArrayList();
        int i = 0;
        while (i < names.length && i < urls.length) {
            Airline a = new Airline();
            a.setName(names[i]);
            a.setUrl(urls[i]);
            res.add(a);
            ++i;
        }
        System.out.println("Index: "+i);
        return res;
    }

}
