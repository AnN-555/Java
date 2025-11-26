package uit.ie303.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uit.ie303.demo.model.Booking;
import uit.ie303.demo.model.BookingDetails;
import uit.ie303.demo.model.Rooms;
import uit.ie303.demo.service.BookingDetailsService;
import uit.ie303.demo.service.BookingService;
import uit.ie303.demo.service.RoomsService;

@RestController
@RequestMapping("/api/booking_details")
public class BookingDetailsController {

    @Autowired
    private BookingDetailsService service;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomsService roomsService;

    @PostMapping
    public ResponseEntity<BookingDetails> create(@RequestBody BookingDetails item) {

        Optional<Booking> mBooking = bookingService.findById(item.getBooking().getId());
        Optional<Rooms> mRoom = roomsService.findByNumber(item.getRoom().getRoomNumber());

        item.setBooking(mBooking.get());
        item.setRoom(mRoom.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(item));
    }

    @GetMapping
    public ResponseEntity<List<BookingDetails>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDetails> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<BookingDetails> update(@RequestBody BookingDetails item) {
        return service.findById(item.getId())
                .map(existing -> {
                    Optional<Booking> mBooking = bookingService.findById(item.getBooking().getId());
                    Optional<Rooms> mRoom = roomsService.findByNumber(item.getRoom().getRoomNumber());

                    existing.setBooking(mBooking.get());
                    existing.setRoom(mRoom.get());
                    return ResponseEntity.ok(service.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
