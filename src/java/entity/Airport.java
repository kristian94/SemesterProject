/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author matskruger
 */
@Entity
@Table(name = "AIRPORT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Airport.findAll", query = "SELECT a FROM Airport a"),
    @NamedQuery(name = "Airport.findAllByQuery", query = "SELECT a FROM Airport a WHERE a.iata LIKE :query OR a.name LIKE :query OR a.city LIKE :query OR a.country LIKE :query"),
    @NamedQuery(name = "Airport.findByAirportID", query = "SELECT a FROM Airport a WHERE a.airportID = :airportID"),
    @NamedQuery(name = "Airport.findByLatitude", query = "SELECT a FROM Airport a WHERE a.latitude = :latitude"),
    @NamedQuery(name = "Airport.findByLongitude", query = "SELECT a FROM Airport a WHERE a.longitude = :longitude"),
    @NamedQuery(name = "Airport.findByAltitude", query = "SELECT a FROM Airport a WHERE a.altitude = :altitude"),
    @NamedQuery(name = "Airport.findByTimezone", query = "SELECT a FROM Airport a WHERE a.timezone = :timezone")})
public class Airport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AirportID")
    private Integer airportID;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "City")
    private String city;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "Country")
    private String country;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "IATA")
    private String iata;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "ICAO")
    private String icao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Latitude")
    private double latitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Longitude")
    private double longitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Altitude")
    private int altitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Timezone")
    private int timezone;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "DST")
    private String dst;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "TzDatabaseTimeZone")
    private String tzdatabasetimezone;

    public Airport() {
    }

    public Airport(Integer airportID) {
        this.airportID = airportID;
    }

    public Airport(Integer airportID, String name, String city, String country, String iata, String icao, double latitude, double longitude, int altitude, int timezone, String dst, String tzdatabasetimezone) {
        this.airportID = airportID;
        this.name = name;
        this.city = city;
        this.country = country;
        this.iata = iata;
        this.icao = icao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.timezone = timezone;
        this.dst = dst;
        this.tzdatabasetimezone = tzdatabasetimezone;
    }

    public Integer getAirportID() {
        return airportID;
    }

    public void setAirportID(Integer airportID) {
        this.airportID = airportID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getTzdatabasetimezone() {
        return tzdatabasetimezone;
    }

    public void setTzdatabasetimezone(String tzdatabasetimezone) {
        this.tzdatabasetimezone = tzdatabasetimezone;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (airportID != null ? airportID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Airport)) {
            return false;
        }
        Airport other = (Airport) object;
        if ((this.airportID == null && other.airportID != null) || (this.airportID != null && !this.airportID.equals(other.airportID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Airport[ airportID=" + airportID + " ]";
    }
    
}
