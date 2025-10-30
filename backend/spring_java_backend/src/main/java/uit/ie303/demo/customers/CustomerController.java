package uit.ie303.demo.customers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uit.ie303.demo.email.EmailService;

@CrossOrigin(origins = {"http://localhost:9090", "null"}) //allow access locally

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerRepository customerRepo;

    public CustomerController(CustomerRepository customerRepo){
        this.customerRepo = customerRepo;
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerRepo.findAll();
    }

    // @Autowired
    // private CustomerService customerService;

    // @PostMapping
    // public Customer createCustomer(@RequestBody Customer customer){
    //     if(customerRepo.existsById(customer.getId())){
    //         throw new RuntimeException("Customer ID already exists");
    //     }
    //     return customerRepo.save(customer);
    // }

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        Customer savedCustomer = customerRepo.save(customer);

        String subject = "Welcome " + savedCustomer.getName();
        String msg = "Enjoy your journey";

        emailService.sendMail(savedCustomer.getEmail(), subject, msg);

        return ResponseEntity.ok(savedCustomer);
    }
    
}
