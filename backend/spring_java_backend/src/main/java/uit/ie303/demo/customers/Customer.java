package uit.ie303.demo.customers;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Generated;
import uit.ie303.demo.booking.Booking;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customer_id;

    private String customer_name, email, phone_number, address;

    public Customer(Long id, String name, String email, String phone, String address) {
        this.customer_name = name;
        this.email = email;
        this.phone_number = phone;
        this.address = address;
        this.customer_id = id;
    }

    public Customer(){}

    //booking 1 - n customers
    // @ManyToOne
    // @JoinColumn(name = "booking_id", nullable = false)
    // private Booking booking_on_customer;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();


    // Getters and Setters
    public Long getId() {
        return customer_id;
    }   
    public void setId(Long id) {
        this.customer_id = id;
    }
    public String getName() {
        return customer_name;
    }
    public void setName(String name) {
        this.customer_name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone_number;
    }
    public void setPhone(String phone) {
        this.phone_number = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
}
