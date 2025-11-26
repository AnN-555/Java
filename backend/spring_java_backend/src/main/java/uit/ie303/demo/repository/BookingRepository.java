package uit.ie303.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uit.ie303.demo.model.Booking;


public interface BookingRepository extends JpaRepository<Booking, Long>{
    
}
