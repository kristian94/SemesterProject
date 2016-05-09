package entity;

import entity.Passenger;
import entity.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-08T18:51:29")
=======
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-08T14:26:13")
>>>>>>> dbdfa81509086248af3ec095cb99553889940d01
@StaticMetamodel(Booking.class)
public class Booking_ { 

    public static volatile ListAttribute<Booking, Passenger> passengers;
    public static volatile SingularAttribute<Booking, Integer> flightTimeInMinutes;
    public static volatile SingularAttribute<Booking, Long> bookingID;
    public static volatile SingularAttribute<Booking, String> origin;
    public static volatile SingularAttribute<Booking, String> reserveeName;
    public static volatile SingularAttribute<Booking, String> flightNumber;
    public static volatile SingularAttribute<Booking, String> travelDate;
    public static volatile SingularAttribute<Booking, User> user;
    public static volatile SingularAttribute<Booking, String> destination;

}