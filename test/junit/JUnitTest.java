/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package junit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import deployment.ServerDeployment;
import entity.User;
import facade.AirlineFacade;
import facade.BookingFacade;
import facade.SearchFacade;
import facade.UserFacade;
import forwarding.RequestForwarder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.*;
import security.PasswordStorage;
import utility.JsonHelper;
import utility.JsonValidator;

/**
 *
 * @author Kristian Nielsen
 */
public class JUnitTest {

    RequestForwarder rf = new RequestForwarder();
    AirlineFacade af = new AirlineFacade();
    BookingFacade bf = new BookingFacade();
    SearchFacade sf = new SearchFacade();
    UserFacade uf = new UserFacade();
    JsonHelper jh = new JsonHelper();
    
    public JUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }
    
    @Test
    public void testFlightForwardingWithoutDestination(){
        JsonObject rj = new JsonObject();
        rj.addProperty("origin", "CPH");
        rj.addProperty("date", "2016-05-23T00:00:00.000Z");
        rj.addProperty("numberOfSeats", 2);
        
        JsonArray flights = rf.flightRequest(rj.toString());
        
        Assert.assertTrue(flights.size() > 0);
        
    }
    
    @Test
    public void testFlightForwardingWithDestination(){
        JsonObject rj = new JsonObject();
        rj.addProperty("origin", "CPH");
        rj.addProperty("destination", "STN");
        rj.addProperty("date", "2016-05-23T00:00:00.000Z");
        rj.addProperty("numberOfSeats", 2);
        
        JsonArray flights = rf.flightRequest(rj.toString());
        
        Assert.assertTrue(flights.size() > 0);
        
    }
    
    @Test
    public void testUserFacade() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(ServerDeployment.PU_NAME);
        EntityManager em = emf.createEntityManager();
        
        String userName = "testUser";
        
        User uu = uf.getUserByUserName(userName);
        if(uu != null){
            uf.deleteUser(userName);
        }
        
        User u = new User();
        u.setFirstName("Michael");
        u.setLastName("Brookes");
        u.setEmail("mb@mail.com");
        try {
            u.setPassword(PasswordStorage.createHash("password"));
        } catch (PasswordStorage.CannotPerformOperationException ex) {
            Logger.getLogger(JUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        u.setUserName(userName);
        
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        
        Assert.assertNotNull(uf.getUserByUserName(userName));
        
    }
    
    @Test
    public void flightValidatorTest(){
        JsonValidator jv = new JsonValidator();

        JsonObject j = new JsonObject();
        j.addProperty("origin", "CPH");
        j.addProperty("date", "2016-05-09T00:00:00.000Z");
        j.addProperty("numberOfSeats", 1);
        j.addProperty("destination", "CPH");

        System.out.println(jv.validateFlightRequest(j.toString()));
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
