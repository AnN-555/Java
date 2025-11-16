package uit.ie303.demo.bookingdetails;

import org.springframework.stereotype.Service;

import uit.ie303.demo.booking.BookingRepository;
import uit.ie303.demo.booking.BookingService;

@Service
public class BookingDetailsService {
    private final BookingRepository repository;

    public BookingDetailsService(BookingRepository repository){
        this.repository = repository;
    }

    
}
