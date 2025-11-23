package uit.ie303.demo.bookingdetails;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class BookingDetailsService {

    private final BookingDetailsRepositoy repository;

    public BookingDetailsService(BookingDetailsRepositoy repo) {
        this.repository = repo;

    }

    public List<BookingDetails> getAllBookingDetails() {
        return this.repository.findAll();
    }

    public Optional<BookingDetails> getBookingDetailsById(Long id) {
        if (null == id)
            return null;
        return this.repository.findById(id);
    }

    public BookingDetails createBookingDetails(BookingDetails item) {

        if (null == item)
            return null; // null safety
        return this.repository.save(item);
    }

    public BookingDetails updateBookingDetails(Long id, BookingDetails item) {
        if (null == id)
            return null;

        BookingDetails mBookingDetails = this.repository.findById(id).orElse(null);

        if (mBookingDetails != null) {
            
            return this.repository.save(mBookingDetails);
        }

        return null;
    }

    public void deleteBookingDetails(Long id) {
        if (null == id)
            return;
        repository.deleteById(id);
    }

}
