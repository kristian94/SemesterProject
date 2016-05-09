package entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-08T18:51:29")
=======
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-08T14:26:13")
>>>>>>> dbdfa81509086248af3ec095cb99553889940d01
@StaticMetamodel(SearchEntity.class)
public class SearchEntity_ { 

    public static volatile SingularAttribute<SearchEntity, Long> searchID;
    public static volatile SingularAttribute<SearchEntity, String> origin;
    public static volatile SingularAttribute<SearchEntity, Date> searchDate;
    public static volatile SingularAttribute<SearchEntity, Date> travelDate;
    public static volatile SingularAttribute<SearchEntity, Integer> numberOfSeats;
    public static volatile SingularAttribute<SearchEntity, String> destination;

}