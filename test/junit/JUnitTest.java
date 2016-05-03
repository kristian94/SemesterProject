/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package junit;

import entity.Booking;
import entity.Passenger;
import entity.SearchEntity;
import facade.BookingFacade;
import facade.SearchFacade;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kristian Nielsen
 */
public class JUnitTest {

    public JUnitTest() {
    }

    @Test
    public void searchFacadeTest() {
        SearchFacade sf = new SearchFacade();
        SearchEntity s = new SearchEntity();

        Date now = Calendar.getInstance().getTime();
        Date travelDate = Calendar.getInstance().getTime();
        travelDate.setTime(now.getTime() + (1000 * 60 * 60 * 24 * 7));
        
        Date findDate = Calendar.getInstance().getTime();
        findDate.setTime(now.getTime() + (1000 * 60));
        
        s.setSearchDate(now);

        s.setTravelDate(travelDate);

        s.setDestination("CPH");
        s.setOrigin("STN");
        s.setNumberOfSeats(1);

        sf.addSearch(s);
        
        System.out.println(sf.getSearches().get(0).getTravelDate());
        
        assertNotNull(sf.getSearches());
        assertNotNull(sf.getSearchesByDate(findDate));
        assertNotNull(sf.getSearchesByDesination("CPH"));
        assertNotNull(sf.getSearchesByOrigin("STN"));

    }
    

    @BeforeClass
    public static void setUpClass() {

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
