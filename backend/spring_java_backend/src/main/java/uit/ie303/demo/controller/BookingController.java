package uit.ie303.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uit.ie303.demo.email.EmailService;
import uit.ie303.demo.model.Booking;
import uit.ie303.demo.model.BookingDetails;
import uit.ie303.demo.model.Customer;
import uit.ie303.demo.service.BookingService;
import uit.ie303.demo.service.CustomerService;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService service;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Booking> create(@RequestBody Booking item) {

        Optional<Customer> mCustomer = java.util.Optional.empty();

        try {
            mCustomer = customerService.findByEmail(item.getCustomer().getEmail());
            item.setCustomer(mCustomer.get());
        } catch (NoSuchElementException e) {
            customerService.save(item.getCustomer());
            mCustomer = customerService.findByEmail(item.getCustomer().getEmail());
            item.setCustomer(mCustomer.get());
        }

        
        emailService.sendMail(mCustomer.get().getEmail(), "Hotel Diamon - Booking Confirmed", "Dear " + mCustomer.get().getCustomerName() + " , your booking is placed.");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(item));
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Booking> update(@RequestBody Booking item) {
        return service.findById(item.getId())
                .map(existing -> {
                    existing.setBookingStatus(item.getBookingStatus());
                    existing.setCheckinDate(item.getCheckinDate());
                    existing.setCheckoutDate(item.getCheckoutDate());

                    existing.setNumGuests(item.getNumGuests());
                    existing.setSpecialRequest(item.getSpecialRequest());
                    existing.setTotalPrice(item.getTotalPrice());

                    Optional<Customer> mCustomer = customerService.findByEmail(item.getCustomer().getEmail());

                    existing.setCustomer(mCustomer.get());

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
