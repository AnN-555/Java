package uit.ie303.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uit.ie303.demo.model.BookingDetails;

public interface BookingDetailsRepositoy extends JpaRepository<BookingDetails, Long> {
}
