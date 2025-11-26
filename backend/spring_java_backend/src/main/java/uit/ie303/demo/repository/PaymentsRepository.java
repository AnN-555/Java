package uit.ie303.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uit.ie303.demo.model.Payments;


public interface PaymentsRepository extends JpaRepository<Payments, Long>{
    
}
