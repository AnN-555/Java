package uit.ie303.demo.bookingdetails;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking_details")
public class BookingDetailsController {

    private final BookingDetailsService service;

    public BookingDetailsController(BookingDetailsService service) {
        this.service = service;

    }

    @GetMapping
    public List<BookingDetails> getAllBookingDetails() {
        return service.getAllBookingDetails();
    }

    @PostMapping
    public ResponseEntity<?> createBookingDetails(@RequestBody BookingDetails item) {
        try {
            BookingDetails details = service.createBookingDetails(item);
            return ResponseEntity.ok(details);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public Optional<BookingDetails> getBookingDetailsById(@PathVariable Long id){
        return service.getBookingDetailsById(id);
    }

    @PutMapping("/{id}")
    public BookingDetails updateBooking (@PathVariable Long id, @RequestBody BookingDetails booking){
        return service.updateBookingDetails(id, booking);
    }

    @DeleteMapping("/{id}")
    public void deleteBookingDetails(@PathVariable Long id){
        service.deleteBookingDetails(id);
    }
}
