package uit.ie303.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import uit.ie303.demo.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.checkinDate < :checkout AND b.checkoutDate > :checkin")
    List<Booking> findBookingsInRange(@Param("checkin") LocalDateTime checkin,
            @Param("checkout") LocalDateTime checkout);

}
