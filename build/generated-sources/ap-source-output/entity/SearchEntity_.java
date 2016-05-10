package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-09T18:33:01")
@StaticMetamodel(SearchEntity.class)
public class SearchEntity_ { 

    public static volatile SingularAttribute<SearchEntity, Long> searchID;
    public static volatile SingularAttribute<SearchEntity, String> origin;
    public static volatile SingularAttribute<SearchEntity, String> searchDate;
    public static volatile SingularAttribute<SearchEntity, String> travelDate;
    public static volatile SingularAttribute<SearchEntity, Integer> numberOfSeats;
    public static volatile SingularAttribute<SearchEntity, String> destination;

}