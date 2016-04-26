/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deployment;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Kristian Nielsen
 */
public class ServerDeployment implements ServletContextListener {

    public static String PU_NAME = "PU-Local";
    public static String[] AIRLINE_URLS = {"http://angularairline-plaul.rhcloud.com/api/flightinfo","http://angularairline-plaul.rhcloud.com/api/flightinfo"};
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
