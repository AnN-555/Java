package uit.ie303.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uit.ie303.demo.model.Customer;
import uit.ie303.demo.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Optional<Customer> findById(Long id) {
        if(null == id ) return null;
        return repository.findById(id);
    }

    public Optional<Customer> findByEmail(String email){
        if(email.equals(null)) return null;

        return repository.findByEmail(email);
    }

    public Customer save(Customer item) {
        if (null == item)
            return null;
        return repository.save(item);
    }

    public boolean delete(Long id) {
        if (null != id && repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
