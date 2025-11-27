package uit.ie303.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uit.ie303.demo.model.Booking;
import uit.ie303.demo.model.BookingDetails;
import uit.ie303.demo.model.Rooms;
import uit.ie303.demo.repository.BookingRepository;
import uit.ie303.demo.repository.RoomsRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository repository;

    @Autowired
    private RoomsService roomsService;

    @Autowired
    private BookingDetailsService bookingDetailsService;

    public List<Booking> findAll() {
        return this.repository.findAll();
    }

    public List<Rooms> getAvailableRooms(LocalDateTime checkin, LocalDateTime checkout, int requestedRoomType) {
        // Step 1: Find all bookings that overlap with the requested date range
        List<Booking> overlappingBookings = repository.findBookingsInRange(checkin, checkout);

        // Step 2: Get all booking IDs
        Set<Long> bookingIds = overlappingBookings.stream()
                .map(Booking::getId)
                .collect(Collectors.toSet());

        // Step 3: Find all booking details for these bookings
        List<BookingDetails> bookedDetails = bookingDetailsService.findByBookingIdIn(bookingIds);

        // Step 4: Collect all booked room IDs
        Set<Long> bookedRoomIds = bookedDetails.stream()
                .map(BookingDetails::getRoomId)
                .collect(Collectors.toSet());

        // Step 5: Filter rooms by availability and requested type
        return roomsService.findAll().stream()
                .filter(room -> !bookedRoomIds.contains(room.getId())) // not booked
                .filter(room -> room.getRoomTypeId() == requestedRoomType) // matches requested type
                .collect(Collectors.toList());
    }

    public Optional<Booking> findById(Long id) {
        if (null == id)
            return null;
        return this.repository.findById(id);
    }

    public Booking save(Booking booking) {
        if (null == booking)
            return null;
        return this.repository.save(booking);
    }

    public boolean delete(Long id) {
        if (null == id)
            return false;
        if (this.repository.existsById(id)) {
            this.repository.deleteById(id);
            return true;
        }
        return false;
    }

}
