package uit.ie303.demo.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import uit.ie303.demo.model.ReservationDTO;


@Service
public class ReservationService {

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=diamondhotel;encrypt=false;trustServerCertificate=true;";
    private static final String DB_USER = "diamondhotel";
    private static final String DB_PASS = "diamond";
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public String saveReservationToDb(ReservationDTO dto) throws Exception {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            conn.setAutoCommit(false);

            String bookingId = dto.getBookingId(); 
            if (bookingId == null || bookingId.trim().isEmpty()) {
                throw new IllegalArgumentException("Booking ID từ frontend không được để trống!");
            }

            // 1. check customer's existence in db by phone or mail, create new if not
            Integer customerId = findExistingCustomer(conn, dto.getEmail(), dto.getPhone());
            if (customerId == null) {
                customerId = insertCustomer(conn, dto);
            }

            // 2. parse date
            LocalDate checkInDate = LocalDate.parse(dto.getCheckIn(), DTF);
            LocalDate checkOutDate = LocalDate.parse(dto.getCheckOut(), DTF);
            Timestamp checkInTs = Timestamp.valueOf(checkInDate.atStartOfDay());
            Timestamp checkOutTs = Timestamp.valueOf(checkOutDate.atStartOfDay());

            // 3. call findAvailableRooms by type, view, date
            List<Integer> availableRoomIds = findAvailableRooms(conn,
                    dto.getRoomName(), dto.getRoomView(), dto.getRoomsSelected(), checkInTs, checkOutTs);

            if (availableRoomIds.size() < dto.getRoomsSelected()) {
                throw new IllegalStateException("Không đủ phòng trống! Chỉ còn " + availableRoomIds.size() +
                        " phòng " + dto.getRoomName() + " - " + dto.getRoomView());
            }

            // 4. Select rooms to book
            List<Integer> roomsToBook = availableRoomIds.subList(0, dto.getRoomsSelected());

            // 5. Insert booking
            insertBooking(conn, bookingId, customerId, checkInTs, checkOutTs, dto);

            // 6. Insert booking_detail linking booking_id and room
            insertBookingDetails(conn, bookingId, roomsToBook);

            // 7. Insert payment
            insertPayment(conn, bookingId, dto.getTotalPrice());

            conn.commit();
            return bookingId; 

        } catch (Exception ex) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException e) { e.printStackTrace(); }
            }
            throw ex;
        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    private Integer findExistingCustomer(Connection conn, String email, String phone) throws SQLException {
        String sql = "SELECT customer_id FROM customers WHERE email = ? OR phone_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email != null ? email : "");
            ps.setString(2, phone != null ? phone : "");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return null;
    }

    private Integer insertCustomer(Connection conn, ReservationDTO dto) throws SQLException {
        String sql = "INSERT INTO customers (customer_name, phone_number, email, address) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, dto.getCustomerName());
            ps.setString(2, dto.getPhone());
            ps.setString(3, dto.getEmail());
            ps.setString(4, dto.getAddress());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new SQLException("Tạo khách hàng thất bại");
    }

    private List<Integer> findAvailableRooms(Connection conn, String roomTypeName, String roomView,
                                             int needed, Timestamp checkIn, Timestamp checkOut) throws SQLException {
        List<Integer> result = new ArrayList<>();
        String sql = """
            SELECT TOP (?) r.room_id
            FROM rooms r
            JOIN roomtype t ON r.type_id = t.type_id
            WHERE t.type_name = ?
              AND r.room_view = ?
              AND NOT EXISTS (
                SELECT 1 FROM booking_details bd
                JOIN booking b ON bd.booking_id = b.booking_id
                WHERE bd.room_id = r.room_id
                  AND NOT (b.checkout_date <= ? OR b.checkin_date >= ?)
              )
            ORDER BY r.room_id
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, needed);
            ps.setString(2, roomTypeName);
            ps.setString(3, roomView);
            ps.setTimestamp(4, checkIn);
            ps.setTimestamp(5, checkOut);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getInt(1));
                }
            }
        }
        return result;
    }
   
    private void insertBooking(Connection conn, String bookingId, int customerId,
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

    private void insertBookingDetails(Connection conn, String bookingId, List<Integer> roomIds) throws SQLException {
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

    private void insertPayment(Connection conn, String bookingId, double amount) throws SQLException {
        String sql = "INSERT INTO payments (booking_id, payment_method, amount, payment_status) VALUES (?, 'QR', ?, 'Completed')";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bookingId);
            ps.setBigDecimal(2, new java.math.BigDecimal(amount));
            ps.executeUpdate();
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