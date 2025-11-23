package uit.ie303.demo.booking;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import uit.ie303.demo.bookingdetails.BookingDetails;
import uit.ie303.demo.customers.Customer;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("booking_id")
    private Long booking_id;

    @JsonProperty("checkin_date")
    private String checkin_date;

    private String checkout_date;

    // @Column(name = "num_guests")
    @JsonProperty("num_guests")
    private int num_guests;

    private double total_price;

    // @Column(name = "special_request")
    @JsonProperty("special_request")
    private String special_request;

    private String booking_status;

    public Booking(Long id, String in_date, String out_date, int numOfGuess, double total, String requires,
            String bookStt) {
        // this.booking_id = id;
        this.checkin_date = in_date;
        this.checkout_date = out_date;
        this.num_guests = numOfGuess;
        this.total_price = total;
        this.special_request = requires;
        this.booking_status = bookStt;
    }

    public Booking(Booking booking) {
        this.booking_id = booking.getId();
        this.checkin_date = booking.getChecking_date();
        this.checkout_date = booking.getCheckout_date();
        this.num_guests = booking.getNum_guess();
        this.total_price = booking.getTotal_price();
        this.special_request = booking.getSpecial_request();
        this.booking_status = booking.getBooking_status();
    }

    public Booking() {
    }

    // Shadow FK field to expose customer_id
    @Column(name = "customer_id", insertable = false, updatable = false)
    @JsonProperty("customer_id")
    private Long customerId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnoreProperties({ "bookings" }) // prevent infinite loop
    private Customer customer;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private BookingDetails details;

    // getter and setter

    public Long getCustomerId() {
        return customer.getId();
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return this.booking_id;
    }

    public void setId(Long id) {
        this.booking_id = id;
    }

    public String getChecking_date() {
        return checkin_date;
    }

    public void setChecking_date(String checking_date) {
        this.checkin_date = checking_date;
    }

    public String getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(String checkout_date) {
        this.checkout_date = checkout_date;
    }

    public int getNum_guess() {
        return num_guests;
    }

    public void setNum_guess(int num_guess) {
        this.num_guests = num_guess;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getSpecial_request() {
        return special_request;
    }

    public void setSpecial_request(String special_request) {
        this.special_request = special_request;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }

}
