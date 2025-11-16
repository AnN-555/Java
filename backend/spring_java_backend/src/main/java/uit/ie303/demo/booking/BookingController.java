package uit.ie303.demo.booking;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uit.ie303.demo.email.EmailService;

// @CrossOrigin(origins = {"http://localhost:9090", "null"}) //allow access locally
@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService service;

    public BookingController(BookingService service){
        this.service = service;

    }

    @GetMapping
    public List<Booking> getAllBooking(){
        return service.getAllBooking();
    }

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<?> createBooking (@RequestBody Booking booking){
        try{
            Booking saveBooking = service.createBooking(booking);
            return ResponseEntity.ok(saveBooking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Optional<Booking> getBookingById(@PathVariable Long id){
        return service.getBookingById(id);
    }

    @PutMapping("/{id}")
    public Booking updateBooking (@PathVariable Long id, @RequestBody Booking booking){
        return service.updateBooking(id, booking);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id){
        service.deleteBooking(id);
    }
    
}
