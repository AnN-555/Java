package uit.ie303.demo.payments;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:9090", "null"}) //allow access locally
@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
    
    private final PaymentsRepository repository;

    public PaymentsController(PaymentsRepository repo){
        this.repository = repo;

    }

    @GetMapping
    public List<Payments> getAllPayments(){
        return repository.findAll();
    }

    @PostMapping
    public Payments createPayments(@RequestBody Payments item){
        if(repository.existsById(item.getPayment_id())){
            throw new RuntimeException("Payment ID already exists");
        }
        return repository.save(item);
    }
}
