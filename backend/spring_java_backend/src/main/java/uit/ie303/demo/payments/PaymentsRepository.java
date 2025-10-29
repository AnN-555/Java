package uit.ie303.demo.payments;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentsRepository extends JpaRepository<Payments, Long>{
    
}
