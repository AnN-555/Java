package uit.ie303.demo.payments;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @CrossOrigin(origins = {"http://localhost:9090", "null"}) //allow access locally
@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
    
    private final PaymentsService service;

    public PaymentsController(PaymentsService service){
        this.service = service;

    }

    @GetMapping
    public List<Payments> getAllPayments(){
        return service.getAllPayments();
    }

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody Payments payments){
        try {
            Payments savedPayments = service.createPayments(payments);
            return ResponseEntity.ok(savedPayments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Optional<Payments> getPaymentById(@PathVariable Long id){
        return this.service.getPaymentById(id);
    }

    @PutMapping("/{id}")
    public Payments updatePayments(@PathVariable Long id, @RequestBody Payments payments){
        return service.updatePayments(id, payments);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id){
        this.service.deletePayment(id);
    }

    // @GetMapping
    // public List<Payments> getAllPayments(){
    //     return repository.findAll();
    // }

    // @PostMapping
    // public Payments createPayments(@RequestBody Payments item){
    //     if(repository.existsById(item.getPayment_id())){
    //         throw new RuntimeException("Payment ID already exists");
    //     }
    //     return repository.save(item);
    // }
}
