package uit.ie303.demo.customers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> getAllCustomers() {
        return this.repository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        if(null == id) return null;
        return this.repository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        
        if(repository.existsByEmail(customer.getEmail())){
            throw new IllegalArgumentException("Email exists");
        }

        return this.repository.save(customer);

    }

    public Customer updateCustomer(Long id, Customer customerDetail) {
        if(null == id) return null;
        
        Customer customer = this.repository.findById(id).orElse(null);

        if (customer != null) {
            customer.setAddress(customerDetail.getAddress());
            customer.setPhone(customerDetail.getPhone());
            customer.setEmail(customerDetail.getEmail());
            customer.setName(customerDetail.getName());
            // customer.setId(customerDetail.getId());

            return this.repository.save(customer);
        }

        return null;
    }

    public void deleteCustomer(Long id) {
        if(null == id) return;
        repository.deleteById(id);
    }
}
