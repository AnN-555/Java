package uit.ie303.demo.booking;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import uit.ie303.demo.customers.Customer;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    private Long Id;

    private String checking_date;
    private String checkout_date;
    private int num_guess;
    private double total_price;
    private String spec_request;
    private String booking_status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer", referencedColumnName = "id", nullable = false)
    private Customer customer;

    public Booking(Long id, String in_date, String out_date, int numOfGuess, double total, String requires, String bookStt){
        this.Id = id;
        this.checking_date = in_date;
        this.checkout_date = out_date;
        this.num_guess = numOfGuess;
        this.total_price = total;
        this.spec_request = requires;
        this.booking_status = bookStt;
    }

    public Long getId(){
        return this.Id;
    }

}
