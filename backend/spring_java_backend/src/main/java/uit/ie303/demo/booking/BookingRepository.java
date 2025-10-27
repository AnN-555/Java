package uit.ie303.demo.booking;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking, Long>{
    
}
