/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deployment;

import entity.Airline;
import facade.AirlineFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

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
