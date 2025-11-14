package uit.ie303.demo.booking;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @CrossOrigin(origins = {"http://localhost:9090", "null"}) //allow access locally
@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingRepository bookingRepo;

    public BookingController(BookingRepository repo){
        this.bookingRepo = repo;

    }

    @GetMapping
    public List<Booking> getAllBooking(){
        return bookingRepo.findAll();
    }

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking){
        if(bookingRepo.existsById(booking.getId())){
            throw new RuntimeException("Booking ID already exists");
        }
        return bookingRepo.save(booking);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id){
        Booking booking = bookingRepo.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));

        return ResponseEntity.ok(booking);
    }
}
