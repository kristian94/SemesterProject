package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import security.IUser;

@Entity
@Table(name = "SystemUser")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
})
public class User implements Serializable, IUser {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "SystemUser_USERROLE", joinColumns = {
        @JoinColumn(name = "userName", referencedColumnName = "userName")}, inverseJoinColumns = {
        @JoinColumn(name = "roleName")})
    private List<Role> roles = new ArrayList();

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    
    
    @Override
    public List<String> getRolesAsStrings() {
        List<String> rolesAsStrings = new ArrayList();
        for (Role role : roles) {
            rolesAsStrings.add(role.getRoleName());
        }
        return rolesAsStrings;
    }

    public void AddRole(Role role) {
        roles.add(role);
        role.addUser(this);
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void addBooking(Booking booking) {
        booking.setUser(this);
        bookings.add(booking);
    }
    
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    
    

}
