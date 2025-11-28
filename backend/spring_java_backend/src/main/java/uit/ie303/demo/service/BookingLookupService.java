package uit.ie303.demo.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import uit.ie303.demo.model.BookingLookupDTO;

@Service
public class BookingLookupService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BookingLookupDTO findByBookingId(String bookingId) {
        String sql = """
            SELECT 
                b.booking_id,
                c.customer_name,
                c.email,
                b.checkin_date,
                b.checkout_date,
                b.total_price,
                rt.type_name,
                rt.image_url
            FROM booking b
            JOIN customers c ON b.customer_id = c.customer_id
            JOIN booking_details bd ON b.booking_id = bd.booking_id
            JOIN rooms r ON bd.room_id = r.room_id
            JOIN roomtype rt ON r.type_id = rt.type_id
            WHERE b.booking_id = ?
            GROUP BY b.booking_id, c.customer_name, c.email, b.checkin_date, b.checkout_date, 
                     b.total_price, rt.type_name, rt.image_url
            """;

        return jdbcTemplate.query(sql, new Object[]{bookingId}, rs -> {
            if (rs.next()) {
                LocalDate checkIn = rs.getDate("checkin_date").toLocalDate();
                LocalDate checkOut = rs.getDate("checkout_date").toLocalDate();
                int nights = (int) ChronoUnit.DAYS.between(checkIn, checkOut);

                return new BookingLookupDTO(
                    rs.getString("booking_id"),
                    rs.getString("customer_name"),
                    rs.getString("email"),
                    checkIn,
                    checkOut,
                    nights,
                    rs.getDouble("total_price"),
                    rs.getString("type_name") + " Room",
                    rs.getString("image_url")
                );
            }
            return null; 
        });
    }
}