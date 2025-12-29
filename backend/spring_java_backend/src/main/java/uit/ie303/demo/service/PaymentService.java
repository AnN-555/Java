package uit.ie303.demo.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public void insertPayment(Connection conn, String bookingId, double amount) throws SQLException {
        String sql = "INSERT INTO payments (booking_id, payment_method, amount, payment_status) VALUES (?, 'QR', ?, 'Completed')";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bookingId);
            ps.setBigDecimal(2, new java.math.BigDecimal(amount));
            ps.executeUpdate();
        }
    }
}