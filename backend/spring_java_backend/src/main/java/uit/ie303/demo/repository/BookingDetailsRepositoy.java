package uit.ie303.demo.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import uit.ie303.demo.model.BookingDetails;

public interface BookingDetailsRepositoy extends JpaRepository<BookingDetails, Long> {
    List<BookingDetails> findByBookingIdIn(Set<Long> bookingIds);
}
