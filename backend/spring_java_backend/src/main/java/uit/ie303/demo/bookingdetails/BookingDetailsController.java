package uit.ie303.demo.bookingdetails;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = { "http://localhost:9090", "null" }) // allow access locally
@RestController
@RequestMapping("/api/booking_details")
public class BookingDetailsController {

    private final BookingDetailsRepositoy repository;

    public BookingDetailsController(BookingDetailsRepositoy repo) {
        this.repository = repo;

    }

    @GetMapping
    public List<BookingDetails> getAllBookingDetails() {
        return repository.findAll();
    }

    @PostMapping
    public BookingDetails createBookingDetails(@RequestBody BookingDetails item) {
        if (repository.existsById(item.getBooking_detail_id())) {
            throw new RuntimeException("Booking detail ID already exists");
        }
        return repository.save(item);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDetails> getBookingDetailById(@PathVariable Long id){

        BookingDetails details = repository.findById(id).orElseThrow(() -> new RuntimeException("Booking detail not found"));
        return ResponseEntity.ok(details);
    }
}
