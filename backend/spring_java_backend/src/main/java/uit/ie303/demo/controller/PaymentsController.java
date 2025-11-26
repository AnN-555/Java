package uit.ie303.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uit.ie303.demo.model.Booking;
import uit.ie303.demo.model.Payments;
import uit.ie303.demo.model.Rooms;
import uit.ie303.demo.service.BookingService;
import uit.ie303.demo.service.PaymentsService;

// @CrossOrigin(origins = {"http://localhost:9090", "null"}) //allow access locally
@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    @Autowired
    private PaymentsService service;

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Payments> create(@RequestBody Payments item) {

        Optional<Booking> mBooking = bookingService.findById(item.getBooking().getId());
        item.setBooking(mBooking.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(item));
    }

    @GetMapping
    public ResponseEntity<List<Payments>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payments> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Payments> update(@RequestBody Payments item) {
        return service.findById(item.getId())
                .map(existing -> {
                    existing.setAmount(item.getAmount());
                    existing.setPaymentDate(item.getPaymentDate());
                    existing.setPaymentMethod(item.getPaymentMethod());
                    existing.setPaymentStatus(item.getPaymentStatus());
                    existing.setTransactionCode(item.getTransactionCode());

                    Optional<Booking> mBooking = bookingService.findById(item.getBooking().getId());
                    existing.setBooking(mBooking.get());
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
