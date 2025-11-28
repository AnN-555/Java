package uit.ie303.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uit.ie303.demo.model.BookingLookupDTO;
import uit.ie303.demo.service.BookingLookupService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") 
public class BookingLookupController {

    @Autowired
    private BookingLookupService bookingLookupService;

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<BookingLookupDTO> getBooking(@PathVariable String bookingId) {
        BookingLookupDTO result = bookingLookupService.findByBookingId(bookingId);

        if (result == null) {
            return ResponseEntity.notFound().build(); 
        }

        return ResponseEntity.ok(result); 
    }
}