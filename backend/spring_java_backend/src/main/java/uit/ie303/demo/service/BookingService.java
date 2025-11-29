package uit.ie303.demo.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import uit.ie303.demo.model.ReservationDTO;

@Service
public class BookingService {

    public void insertBooking(Connection conn, String bookingId, int customerId,
                               Timestamp checkIn, Timestamp checkOut, ReservationDTO dto) throws SQLException {
        String sql = """
            INSERT INTO booking 
            (booking_id, customer_id, checkin_date, checkout_date, num_guests, total_price, special_request, booking_status)
            VALUES (?, ?, ?, ?, ?, ?, ?, 'Paid')
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bookingId);
            ps.setInt(2, customerId);
            ps.setTimestamp(3, checkIn);
            ps.setTimestamp(4, checkOut);
            ps.setInt(5, parseNumGuests(dto.getTotalGuests()));
            ps.setBigDecimal(6, new java.math.BigDecimal(dto.getTotalPrice()));
            ps.setString(7, dto.getRequest() != null && !dto.getRequest().isEmpty() ? dto.getRequest() : null);
            ps.executeUpdate();
        }
    }

    public void insertBookingDetails(Connection conn, String bookingId, List<Integer> roomIds) throws SQLException {
        String sql = "INSERT INTO booking_details (booking_id, room_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Integer roomId : roomIds) {
                ps.setString(1, bookingId);
                ps.setInt(2, roomId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private int parseNumGuests(String totalGuests) {
        if (totalGuests == null) return 1;
        try {
            if (totalGuests.contains("-")) {
                String part = totalGuests.split("-")[1].trim();
                return Integer.parseInt(part.replaceAll("\\D+", ""));
            }
            return Integer.parseInt(totalGuests.replaceAll("\\D+", ""));
        } catch (Exception e) {
            return 1;
        }
    }
}