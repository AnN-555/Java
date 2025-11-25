package uit.ie303.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import uit.ie303.demo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phone);
    Optional<Customer> findByEmail(String email);
}
