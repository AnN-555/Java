package uit.ie303.demo.booking;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import uit.ie303.demo.customers.Customer;
import uit.ie303.demo.customers.CustomerRepository;

@Service
public class BookingService {
    private final BookingRepository repository;
    private final CustomerRepository customerRepository;

    public BookingService(BookingRepository repo, CustomerRepository customerRepository) {
        this.repository = repo;
        this.customerRepository = customerRepository;
    }

    public List<Booking> getAllBooking() {
        return this.repository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        if (null == id)
            return null;
        return this.repository.findById(id);
    }

    public Booking createBooking(Booking  booking) {
        if (null == booking)
            return null;

            Customer customer = customerRepository.findById(booking.getCustomerId()).orElse(null);
            Booking mBooking = new Booking(booking);


            mBooking.setCustomer(customer);
        
        return this.repository.save(mBooking);
    }

    public Booking updateBooking(Long id, Booking booking) {
        if (null == id)
            return null;

        Booking mBooking = this.repository.findById(id).orElse(null);

        if (null != mBooking) {

            mBooking.setBooking_status(booking.getBooking_status());
            mBooking.setChecking_date(booking.getChecking_date());
            mBooking.setCheckout_date(booking.getCheckout_date());
            mBooking.setNum_guess(booking.getNum_guess());
            mBooking.setSpecial_request(booking.getSpecial_request());
            mBooking.setTotal_price(booking.getTotal_price());
            return this.repository.save(mBooking);

        }

        return null;
    }

    public void deleteBooking(Long id) {
        if (null == id)
            return;
        this.repository.deleteById(id);
    }

}
