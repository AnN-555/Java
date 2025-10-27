package uit.ie303.demo.customers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://127.0.0.1:3000") //allow access locally
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

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer){
        if(customerRepo.existsById(customer.getId())){
            throw new RuntimeException("Customer ID already exists");
        }
        return customerRepo.save(customer);
    }
}
