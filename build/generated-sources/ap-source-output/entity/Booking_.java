package entity;

import entity.Passenger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-02T22:56:44")
@StaticMetamodel(Booking.class)
public class Booking_ { 

    public static volatile ListAttribute<Booking, Passenger> passengers;
    public static volatile SingularAttribute<Booking, Integer> flightTimeInMinutes;
    public static volatile SingularAttribute<Booking, Long> bookingID;
    public static volatile SingularAttribute<Booking, String> origin;
    public static volatile SingularAttribute<Booking, String> reserveeName;
    public static volatile SingularAttribute<Booking, Integer> flightNumber;
    public static volatile SingularAttribute<Booking, Date> travelDate;
    public static volatile SingularAttribute<Booking, String> destination;

}