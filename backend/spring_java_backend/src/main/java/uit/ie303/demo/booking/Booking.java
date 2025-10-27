package uit.ie303.demo.booking;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Booking {
    @Id
    private int Id;
    private int customerid;
    private String checking_date;
    private String checkout_date;
    private int num_guess;
    private double total_price;
    private String spec_request;
    private String booking_status;

    @ManyToOne
    @JoinColumn(name = "customers", referencedColumnName = "id")
    private int customer_id;
}
